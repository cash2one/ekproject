package cn.qtone.xxt.apps.web.misc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import cn.elam.util.common.MailBoxService;
import cn.qtone.xxt.apps.web.AppLoger;
import cn.qtone.xxt.apps.web.HttpClientUtil;

import com.elam.util.task.BaseTask;
import com.elam.util.task.TaskItem;

/**
 * 分析misc系统页面，提取 投诉 数据
 * 并把对应的数据插入到校讯通的系统
 * 
 * @author Ethan.Lam  2011-2-15
 *
 */
public class FetchDataTask extends BaseTask {

	
	public FetchDataTask(TaskItem taskItem) {
		super(taskItem);
	}

	@Override
	protected void initialize() {
		AppLoger.getRuningLogger().info("初始化并启动 MISC 数据抽取程序。");
		
		
	}

	@Override
	protected void release() {
		AppLoger.getRuningLogger().info("MISC 数据抽取程序关闭");
	}

	
	
	/**
	 * 循环执行
	 */
	protected void task() {
		AppLoger.getRuningLogger().info("开始执行数据抽取任务");
		String LOGON_SITE = "http://admin.zj.monternet.com:8080/sp/index.jsp"; //首页
		int LOGON_PORT = 8080;
		String loginReq = "http://admin.zj.monternet.com:8080/sp/SPLogin";    //验证请求页面
		String UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)";  
		String hrefUrl = "/sp/index.jsp";  //登录后的跳转页面
		HttpClient client = null;
		PostMethod post = null;
		try {
			
			//新建一个httpClient,开始实现注入控制
			client = HttpClientUtil.newHttpClient(LOGON_SITE, LOGON_PORT);
			post = HttpClientUtil.newPostMethod(loginReq, UserAgent, hrefUrl,
					new String[][] { { "selectAccount", "SPPREREG" },
							{ "USER", "qtone" }, { "PASSWORD", "qtone2010" } });
			client.executeMethod(post);
			Cookie[] cookies = HttpClientUtil.addCookies(client);
			post.releaseConnection();
			
			//抓取页面，并提取页面数据
			List<ComplaintItem> items = startToFetchComplaintListDatas(client);
			AppLoger.getRuningLogger().info("获取到今天的投诉记录数据数 :"+(items!=null?items.size():"0")+"条");
			
			fetchComplaintDetail(client,items);
//			printDatas(items);
			
			XxtComplaintYwDao dao = new XxtComplaintYwDao();
			dao.insert(items);
			
			
			//调用邮件服务接口
			String messageBody = dao.wrapperEMailMessageContent();
			if(messageBody!=null&&!"".equals(messageBody)){
				
				MailBoxService mailBox = new MailBoxService(SysCfg.EMAIL_FROM_ADDRESS,
						SysCfg.EMAIL_STMP_HOST, SysCfg.EMAIL_USER_NAME, SysCfg.EMAIL_USER_PWD, SysCfg.EMAIL_AUTH,false);
				mailBox.sendMail(SysCfg.EMAIL_TO_ADDRESS, SysCfg.EMAIL_TITLE_SET+"_"+new Date(), messageBody);
				mailBox = null;
			}
			dao = null;
			items = null;
			cookies = null;
		}catch(Exception e){
			e.printStackTrace();
			AppLoger.getSimpleErrorLogger().info(e.getMessage());
		}finally{
			post.releaseConnection();
			client = null;
		}	
		AppLoger.getRuningLogger().info("结束执行数据抽取任务");
		
	}

	/**
	 * 抽取页面对应数据，并进行封装
	 * @return
	 */
	List<ComplaintItem> startToFetchComplaintListDatas(HttpClient client) {
		HtmlCleaner cleaner = new HtmlCleaner();
		String targetPage = fetchSrcDataHtmlPage(client);
		TagNode node = cleaner.clean(targetPage);
		Object[] trs = node.getElementsByName("tr", true);
		List<ComplaintItem> ComplaintItemList = new ArrayList<ComplaintItem>();
		if (trs.length != 0) {
			TagNode trNode = null;
			TagNode[] tdNodes = null;
			ComplaintItem item = null;
			for (Object tr : trs) {
				trNode = (TagNode) tr;
				if ("11".equals(trNode.getAttributeByName("height"))) {
					 tdNodes = trNode.getElementsByName("td",true);
					 if(tdNodes!=null){
						 item = new ComplaintItem();
						 item.setId(tdNodes[0].getText().toString());
						 item.setUser(tdNodes[1].getText().toString());
						 item.setBrand(tdNodes[2].getText().toString());
						 item.setArea(tdNodes[3].getText().toString());
						 item.setRank(tdNodes[4].getText().toString());
						 item.setDealState(tdNodes[5].getText().toString());
						 item.setCreateTime(tdNodes[6].getText().toString());
						 item.setDeadline(tdNodes[7].getText().toString());
						 item.setReplyUrl(fetchReplyUrl(tdNodes[8]));
						 ComplaintItemList.add(item);
					 }
				}
			}
		}
		return ComplaintItemList;
	}
	
	
	

	/**
	 * 获取回复的URL地址
	 * @param td
	 * @return
	 */
	String fetchReplyUrl(TagNode td){
		TagNode[] aTags = td.getElementsByName("a",true);
		 String url = "";
        if(aTags!=null){
            url = aTags[0].getAttributeByName("onClick");
            url = url.substring("MM_openBrWindow('".length());
            url = url.substring(0,url.indexOf("','"));
        }
		return url;
	}
	
	
	/**
	 * 1、获取原始页面
	 * @return
	 */
	String fetchSrcDataHtmlPage(HttpClient client) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			String queryTimePer = df.format(new Date());
			Cookie[] cookies = HttpClientUtil.addCookies(client);
			String dataUrl = "http://admin.zj.monternet.com:8080/sp/indict/queryIndictICD.jsp?subsId=&userName=&status=1&queryTime=0&fromDate="+queryTimePer+"&toDate="+queryTimePer+"&pageNum=1&currentPageNo=1&pageSize=100&navigatePage_toPageSize=100&navigatePage_toPageNum=1";
			GetMethod get = HttpClientUtil.newGetMethod(dataUrl, cookies);
			client.executeMethod(get);
			cookies = null;
			return get.getResponseBodyAsString();
		} catch (Exception e) {
			AppLoger.getSimpleErrorLogger().info(e.getMessage());
			return "";
		} finally {
			client = null;
		}
	}
	
	
	/**
	 * 
	 * 获取对应的投诉详细信息
	 * @return
	 */
	void fetchComplaintDetail(HttpClient client,List<ComplaintItem> items){
		if(items==null||items.size()==0)
			return;
        for(ComplaintItem item : items){
             try{
            	 getComplaintDetailInfos(client,item);
             }catch(Exception e){
            	 AppLoger.getSimpleErrorLogger().info(e.getMessage());	 
             }        
        }
		
	}
	
	
	/**
	 * 
	 * @param client
	 * @param item
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	void getComplaintDetailInfos(HttpClient client,ComplaintItem item) throws HttpException, IOException{
		Cookie[] cookies = HttpClientUtil.addCookies(client);
		String dataUrl = "http://admin.zj.monternet.com:8080/sp/indict/"+item.getReplyUrl();
		
		//特殊处理  中文问题
		dataUrl = dataUrl.substring(0,dataUrl.indexOf("&cityName="))+dataUrl.substring(dataUrl.indexOf("&servType="));
		GetMethod get = HttpClientUtil.newGetMethod(dataUrl, cookies);
		client.executeMethod(get);
		String content = get.getResponseBodyAsString();
		get.releaseConnection();
		
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode nodes = cleaner.clean(content);
		Object[] trs = nodes.getElementsByName("tr", true);
		
		//提取对应投诉内容文本
		if (trs.length != 0) {
			TagNode trNode = null;
			TagNode[] tdNodes = null;
			for (Object tr : trs) {
				trNode = (TagNode) tr;
				tdNodes = trNode.getElementsByName("td",true);
				
				//获取投诉内容
				if (tdNodes!=null&&tdNodes.length==2) {
					 if(tdNodes[0]!=null&&tdNodes[0].getText().toString().trim().startsWith("投诉内容")){
						 content = tdNodes[1].getText().toString().trim();
						 item.setContent(content);
						 break;
					 }
				}
				tdNodes = null;
				trNode =null;
			}
		}
		
		String hasReOpenReplyMsg = getReOpenComplaintText(nodes);
		if(hasReOpenReplyMsg!=null&&hasReOpenReplyMsg.length()>0)
			item.setContent(hasReOpenReplyMsg);
		
		
		nodes = null;
		cleaner = null;
		get = null;
		cookies = null;
	}
	
	
	
	/**
	 * 
	 * 获取重新打开贴的最新投诉内容
	 * @param nodes
	 * @return
	 */
    String getReOpenComplaintText(TagNode nodes ){
    	String newestReply = "";
    	try{
			Object[] tables = nodes.getElementsByName("table", true);
			boolean isComplaintReply = false;
			TagNode tableNode = null;
			TagNode[] trNodes = null;
			TagNode[] tdNodes = null;
			if (tables.length != 0) {
				for (Object table : tables) {
					tableNode = (TagNode) table;
					//回复的帖子
					if("center".equals(tableNode.getAttributeByName("align"))){
						trNodes = tableNode.getElementsByName("tr", true);	
						tdNodes = trNodes[0].getElementsByName("td", true);
						if(tdNodes!=null&&tdNodes.length==4&&tdNodes[3].getText().toString().trim().indexOf("KF")>=0){
							isComplaintReply = true;
						}
						
						if(isComplaintReply){
							tdNodes = trNodes[1].getElementsByName("td", true);
							if(tdNodes!=null&&tdNodes.length==2&&tdNodes[0].getText().toString().trim().indexOf("帖子的内容")>=0){
								newestReply = tdNodes[1].getText().toString().trim();
								isComplaintReply = false;
							}
						}
						isComplaintReply = false;
					}
				}
			}
    	}catch(Exception e){
    		AppLoger.getSimpleErrorLogger().info(e.getMessage()+":获取重新打开的投诉贴的内容失败！");
    	}
    	return newestReply;
    }
	
	
	
	/**
	 * 测试打印获取的数据信息
	 * @param items
	 */
	void printDatas(List<ComplaintItem> items ){
		for(ComplaintItem i : items)
			 System.out.println(i.getId()+","+i.getUser()+","+i.getBrand()+","+i.getArea()+","+i.getContent());
	    items = null;
	}

	
	/**
	 * 判断流水号是否已经加入系统中
	 * @param eventId
	 * @return
	 */
	boolean isNeedCollect(ComplaintItem item){
        return ItemsCheckHelper.getHelper().isNewItemJudgeFromDB(item);
	}
	
	

}
