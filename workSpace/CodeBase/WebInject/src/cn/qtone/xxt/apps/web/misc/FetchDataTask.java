package cn.qtone.xxt.apps.web.misc;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import cn.qtone.xxt.apps.web.HttpClientUtil;

import com.elam.util.task.BaseTask;
import com.elam.util.task.TaskItem;

/**
 * ����miscϵͳҳ�棬��ȡ Ͷ�� ����
 * ���Ѷ�Ӧ�����ݲ��뵽УѶͨ��ϵͳ
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
		
	}

	@Override
	protected void release() {
		
	}

	
	
	/**
	 * ѭ��ִ��
	 */
	protected void task() {
		String LOGON_SITE = "http://admin.zj.monternet.com:8080/sp/index.jsp"; //��ҳ
		int LOGON_PORT = 8080;
		String loginReq = "http://admin.zj.monternet.com:8080/sp/SPLogin";    //��֤����ҳ��
		String UserAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)";  
		String hrefUrl = "/sp/index.jsp";  //��¼�����תҳ��
		HttpClient client = null;
		PostMethod post = null;
		try {
			
			//�½�һ��httpClient,��ʼʵ��ע�����
			client = HttpClientUtil.newHttpClient(LOGON_SITE, LOGON_PORT);
			post = HttpClientUtil.newPostMethod(loginReq, UserAgent, hrefUrl,
					new String[][] { { "selectAccount", "SPPREREG" },
							{ "USER", "qtone" }, { "PASSWORD", "qtone2010" } });
			client.executeMethod(post);
			Cookie[] cookies = HttpClientUtil.addCookies(client);
			post.releaseConnection();
			
			//ץȡҳ�棬����ȡҳ������
			List<ComplaintItem> items = startToFetchComplaintListDatas(client);
			System.out.println("���ԣ��µ�������:"+(items!=null?items.size():"0"));
			
			fetchComplaintDetail(client,items);
//			printDatas(items);
			
			items = null;
			cookies = null;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			post.releaseConnection();
			client = null;
		}	
		
	}

	/**
	 * ��ȡҳ���Ӧ���ݣ������з�װ
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
						 item.setReplyUrl(fetchReplyUrl(tdNodes[0]));
						 ComplaintItemList.add(item);
					 }
				}
			}
		}
		return ComplaintItemList;
	}
	
	
	

	/**
	 * ��ȡ�ظ���URL��ַ
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
	 * 1����ȡԭʼҳ��
	 * @return
	 */
	String fetchSrcDataHtmlPage(HttpClient client) {
		try {
			Cookie[] cookies = HttpClientUtil.addCookies(client);
			String dataUrl = "http://admin.zj.monternet.com:8080/sp/indict/queryIndictICD.jsp?subsId=&userName=&status=1&queryTime=0&fromDate=2010-02-01&toDate=2011-02-15&pageNum=1&currentPageNo=1&pageSize=100&navigatePage_toPageSize=100&navigatePage_toPageNum=1";
			GetMethod get = HttpClientUtil.newGetMethod(dataUrl, cookies);
			client.executeMethod(get);
			cookies = null;
			return get.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			client = null;
		}
	}
	
	
	/**
	 * 
	 * ��ȡ��Ӧ��Ͷ����ϸ��Ϣ
	 * @return
	 */
	void fetchComplaintDetail(HttpClient client,List<ComplaintItem> items){
		if(items==null||items.size()==0)
			return;
        for(ComplaintItem item : items){
             try{
            	 getComplaintDetailInfos(client,item);
             }catch(Exception e){
                e.printStackTrace();	 
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
		
		//���⴦��  ��������
		dataUrl = dataUrl.substring(0,dataUrl.indexOf("&cityName="))+dataUrl.substring(dataUrl.indexOf("&servType="));
		GetMethod get = HttpClientUtil.newGetMethod(dataUrl, cookies);
		client.executeMethod(get);
		String content = get.getResponseBodyAsString();
		get.releaseConnection();
		
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode node = cleaner.clean(content);
		Object[] trs = node.getElementsByName("tr", true);

		//��ȡ��ӦͶ�������ı�
		if (trs.length != 0) {
			TagNode trNode = null;
			TagNode[] tdNodes = null;
			for (Object tr : trs) {
				trNode = (TagNode) tr;
				tdNodes = trNode.getElementsByName("td",true);
				if (tdNodes!=null&&tdNodes.length==2) {
					 if(tdNodes[0]!=null&&tdNodes[0].getText().toString().trim().startsWith("Ͷ������")){
						 content = tdNodes[1].getText().toString().trim();
						 item.setContent(content);
						 break;
					 }
				}
				tdNodes = null;
				trNode =null;
			}
		}
		node = null;
		cleaner = null;
		get = null;
		cookies = null;
	}
	
	
	
	/**
	 * ��⴦��
	 */
	void insertIntoXxtDB(){
		
		
		
	}
	
	/**
	 * ���Դ�ӡ��ȡ��������Ϣ
	 * @param items
	 */
	void printDatas(List<ComplaintItem> items ){
		for(ComplaintItem i : items)
			   System.out.println(i.getId()+","+i.getUser()+","+i.getBrand()+","+i.getArea()+","+i.getContent());
	    items = null;
	}
	
	

}
