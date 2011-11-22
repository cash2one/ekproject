package getfile.ui;

import getfile.util.CopyFileUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;

public class WebDeployComposite  extends Composite {

 
	public WebDeployComposite(Composite parent, int style) {
		super(parent, style);
		open(parent);
		// TODO Auto-generated constructor stub
	}


	Text text;
	Text resultText;
	Button actionBtn;
	Text srcText;
	Text outputText;
	Button isActionDeployBtn;
	boolean isActionDeploy = false;
	
	private Text rootPathTxt;
	private Label lblNewLabel;
	private Text outputFileTxt;
	private Label label_2;
	private Text outputPathTxt;
	
	private String rootPath;
	private String outputPath;
	private String outputDir;
	private String outputFile;
	private String serverWebRoot;
	
	static String ROOT_DIR="ROOT_DIR";
	static String OUTPUT_DIR="OUTPUT_DIR";
	static String OUTPUT_FILENAME="OUTPUT_FILENAME";
	static String SERVER_WEB_ROOT_DIR="SERVER_WEB_ROOT_DIR";
	
	public String open(final Composite parent ) {
		
		setLayout(new FormLayout());
		
		lblNewLabel = new Label(this, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.left = new FormAttachment(0, 15);
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("\u5DF2\u7F16\u8BD1\u7684WebRoot\u8DEF\u5F84\uFF1A");
		
		rootPathTxt = new Text(this, SWT.BORDER);
		rootPathTxt.setToolTipText("\u5DF2\u7F16\u8BD1\u6210\u529F\uFF0C\u5F85\u53D1\u5E03\u4EE3\u7801\u6E90\u6587\u4EF6\u7684\u6839\u76EE\u5F55");
		fd_lblNewLabel.right = new FormAttachment(rootPathTxt, -16);
		FormData fd_rootPathTxt = new FormData();
		fd_rootPathTxt.left = new FormAttachment(0, 154);
		fd_rootPathTxt.top = new FormAttachment(0, 7);
		rootPathTxt.setLayoutData(fd_rootPathTxt);
		rootPathTxt.setText(this.getSetting(WebDeployComposite.ROOT_DIR));
	    this.rootPath = this.getSetting(WebDeployComposite.ROOT_DIR);
	    this.rootPath = this.rootPath.replace("\\", "/");
	    this.outputDir = "".equals(this.getSetting(WebDeployComposite.OUTPUT_DIR))?(rootPath.length()>0?rootPath.substring(0,rootPath.lastIndexOf("/")):""):this.getSetting(WebDeployComposite.OUTPUT_DIR);
	    this.serverWebRoot = this.getSetting(WebDeployComposite.SERVER_WEB_ROOT_DIR);
	    
	    
		text = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		text.setToolTipText("\u5728\u6B64\u7C98\u8D34\u9700\u8981\u63D0\u53D6\u7684\u6587\u4EF6\u8BE6\u7EC6\u5217\u8868\uFF1B\u6240\u6709\u88AB\u63D0\u53D6\u7684\u6587\u4EF6\u7684\u8DEF\u5F84\u9700\u76F8\u5BF9\u4E8E\u201C\u5DF2\u7F16\u8BD1\u7684webRoot\u8DEF\u5F84\u201D\uFF01");
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(0, 15);
		fd_text.right = new FormAttachment(100, -15);
		text.setLayoutData(fd_text);
		
		
		resultText= new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		resultText.setToolTipText("\u6587\u4EF6\u63D0\u53D6\u7ED3\u679C");
		resultText.setEditable(false);
		FormData fd_resultText = new FormData();
		fd_resultText.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_resultText.bottom = new FormAttachment(0, 434);
		fd_resultText.left = new FormAttachment(0, 15);
		resultText.setLayoutData(fd_resultText);
		
		
		actionBtn = new Button(this, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		FormData fd_actionBtn = new FormData();
		actionBtn.setLayoutData(fd_actionBtn);
		actionBtn.setText("执行提取");
		actionBtn.setSize(new Point(50, 50));
		actionBtn.setBounds(10, 10, 40, 18);
		
		Button srcPathBtnBrowser = new Button(this, SWT.NONE);
		fd_rootPathTxt.right = new FormAttachment(srcPathBtnBrowser, -18);
		srcPathBtnBrowser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				org.eclipse.swt.widgets.DirectoryDialog dirDig = new DirectoryDialog(getShell(), SWT.CLOSE);
				String dir = dirDig.open();
				if(dir == null)
					return;
				rootPathTxt.setText(dir);
				rootPath = dir;
				dir = dir.replace("\\","/");
				outputDir = dir.substring(0, dir.lastIndexOf("/"));
				outputPath =dir.substring(0, dir.lastIndexOf("/"))+"/"+("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				outputPathTxt.setText(outputPath);
				saveSetting(WebDeployComposite.ROOT_DIR,rootPath);
				saveSetting(WebDeployComposite.OUTPUT_DIR,outputDir);
				saveSetting(WebDeployComposite.OUTPUT_FILENAME,("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText()));
			}
			
		});
		
		FormData fd_srcPathBtnBrowser = new FormData();
		fd_srcPathBtnBrowser.left = new FormAttachment(100, -60);
		fd_srcPathBtnBrowser.top = new FormAttachment(0, 5);
		fd_srcPathBtnBrowser.right = new FormAttachment(100, -10);
		srcPathBtnBrowser.setLayoutData(fd_srcPathBtnBrowser);
		srcPathBtnBrowser.setText("\u6D4F \u89C8");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		fd_text.top = new FormAttachment(lblNewLabel_1, 3);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.right = new FormAttachment(lblNewLabel, 29, SWT.RIGHT);
		fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 6);
		fd_lblNewLabel_1.left = new FormAttachment(0, 15);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("\u7C98\u8D34\u9700\u8981\u62F7\u8D1D\u76EE\u6807\u6587\u4EF6");
		
		Label label = new Label(this, SWT.NONE);
		fd_text.bottom = new FormAttachment(label, -6);
		fd_resultText.top = new FormAttachment(label, 6);
		FormData fd_label = new FormData();
		fd_label.left = new FormAttachment(0, 15);
		fd_label.top = new FormAttachment(0, 220);
		label.setLayoutData(fd_label);
		label.setText("\u6587\u4EF6\u63D0\u53D6\u7ED3\u679C");
		
		outputFileTxt = new Text(this, SWT.BORDER);
		outputFileTxt.setToolTipText("\u4EE5\u6B64\u76EE\u5F55\u4F5C\u4E3A\u63D0\u53D6\u51FA\u6765\u7684\u6587\u4EF6\u7684\u5B58\u653E\u5730\u5740");
		outputFileTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			        
				String outputT = outputDir+"/"+("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				outputT = outputT.replace("\\","/");
				if(outputPathTxt!=null)
				  outputPathTxt.setText(outputT);
				outputPath = outputT;
				outputFile = ("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				saveSetting(WebDeployComposite.OUTPUT_DIR,outputDir);
				saveSetting(WebDeployComposite.OUTPUT_FILENAME,("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText()));
			}
		});
		
		FormData fd_outputFileTxt = new FormData();
		fd_outputFileTxt.top = new FormAttachment(resultText, 29);
		outputFileTxt.setLayoutData(fd_outputFileTxt);
		outputFileTxt.setText(this.getSetting(WebDeployComposite.OUTPUT_FILENAME)==""?"output":this.getSetting(WebDeployComposite.OUTPUT_FILENAME));
	    
		
		Label label_1 = new Label(this, SWT.NONE);
		fd_outputFileTxt.left = new FormAttachment(0, 125);
		FormData fd_label_1 = new FormData();
		fd_label_1.bottom = new FormAttachment(outputFileTxt, 12);
		fd_label_1.top = new FormAttachment(outputFileTxt, 0, SWT.TOP);
		fd_label_1.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_label_1.right = new FormAttachment(outputFileTxt, -6);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u8F93\u51FA\u6587\u4EF6\u5939\u540D\u79F0\uFF1A");
		
		Button outputBtnButton = new Button(this, SWT.NONE);
		fd_outputFileTxt.right = new FormAttachment(outputBtnButton, -16);
		fd_actionBtn.right = new FormAttachment(outputBtnButton, 0, SWT.RIGHT);
		outputBtnButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				org.eclipse.swt.widgets.DirectoryDialog dirDig = new DirectoryDialog(getShell(), SWT.CLOSE);
				String dir = dirDig.open();
				outputDir = dir;
				String outputT = outputDir+"/"+("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				outputT = outputT.replace("\\","/");
				outputPathTxt.setText(outputT);
				outputPath = outputT;
				saveSetting(WebDeployComposite.OUTPUT_DIR,dir);
			}
		});
		
		FormData fd_outputBtnButton = new FormData();
		fd_outputBtnButton.left = new FormAttachment(0, 315);
		outputBtnButton.setLayoutData(fd_outputBtnButton);
		outputBtnButton.setText("\u6D4F \u89C8");
		
		label_2 = new Label(this, SWT.NONE);
		label_2.setToolTipText("\u6307\u5B9A\u751F\u6210\u53D1\u5E03\u66F4\u65B0\u6587\u4EF6\u7684\u6839\u8DEF\u5F84");
		FormData fd_label_2 = new FormData();
		fd_label_2.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u7ED3\u679C\u4F4D\u7F6E\uFF1A");
		
		outputPathTxt = new Text(this, SWT.BORDER);
		fd_outputBtnButton.bottom = new FormAttachment(outputPathTxt);
		fd_label_2.top = new FormAttachment(0, 486);
		outputPathTxt.setEditable(false);
		FormData fd_outputPathTxt = new FormData();
		fd_outputPathTxt.top = new FormAttachment(0, 483);
		fd_outputPathTxt.right = new FormAttachment(100, -78);
		fd_outputPathTxt.left = new FormAttachment(label_2, 50);
		outputPathTxt.setLayoutData(fd_outputPathTxt);
		System.out.println("cfg: ROOT_DIR -> "+this.getSetting(WebDeployComposite.ROOT_DIR));
		System.out.println("cfg: OUTPUT_DIR -> "+this.getSetting(WebDeployComposite.OUTPUT_DIR));
		System.out.println("cfg: OUTPUT_FILENAME->"+this.getSetting(WebDeployComposite.OUTPUT_FILENAME));
		String tempPath = this.getSetting(WebDeployComposite.OUTPUT_DIR)+"/"+(this.getSetting(WebDeployComposite.OUTPUT_FILENAME)==""?"output":this.getSetting(WebDeployComposite.OUTPUT_FILENAME));
		outputPathTxt.setText(tempPath);
		
		serverWebRoot = getSetting(WebDeployComposite.SERVER_WEB_ROOT_DIR);
		serverWebRootTxt = new Text(this, SWT.BORDER);
		serverWebRootTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				serverWebRoot = serverWebRootTxt.getText();
				saveSetting(WebDeployComposite.SERVER_WEB_ROOT_DIR,serverWebRoot);
			}
		});
		
		serverWebRootTxt.setToolTipText("\u586B\u5199\u7EBF\u4E0A\u7248\u672C\u7684 webRoot \u6839\u8DEF\u5F84\uFF0C\u5982 /data/zjxxt/");
		FormData fd_serverWebRootTxt = new FormData();
		fd_serverWebRootTxt.top = new FormAttachment(outputPathTxt, 6);
		fd_serverWebRootTxt.left = new FormAttachment(outputFileTxt, 0, SWT.LEFT);
		fd_serverWebRootTxt.right = new FormAttachment(100, -322);
		serverWebRootTxt.setLayoutData(fd_serverWebRootTxt);
		serverWebRootTxt.setText(serverWebRoot);
		
		
		Label lblwebroot = new Label(this, SWT.NONE);
		lblwebroot.setText("\u7EBF\u4E0AwebRoot:");
		FormData fd_lblwebroot = new FormData();
		fd_lblwebroot.top = new FormAttachment(serverWebRootTxt, 3, SWT.TOP);
		fd_lblwebroot.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		lblwebroot.setLayoutData(fd_lblwebroot);
		
		backUpItemTxt = new Text(this, SWT.BORDER);
		fd_actionBtn.top = new FormAttachment(backUpItemTxt, -4, SWT.TOP);
		backUpItemTxt.setToolTipText("\u81EA\u5B9A\u4E49\u201C\u76EE\u5F55\u540D\u79F0\u201D\uFF0C\u4F5C\u4E3A\u5907\u4EFD\u811A\u672C\u5B58\u653E\u201C\u5907\u4EFD\u6587\u4EF6\u7684\u201D\u76EE\u6807\u76EE\u5F55\uFF01");
		FormData fd_backUpItemTxt = new FormData();
		fd_backUpItemTxt.top = new FormAttachment(serverWebRootTxt, 6);
		fd_backUpItemTxt.left = new FormAttachment(outputFileTxt, 0, SWT.LEFT);
		backUpItemTxt.setLayoutData(fd_backUpItemTxt);
		
		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		lblNewLabel_2.setToolTipText("\u53EA\u80FD\u586B\u5199\u5B57\u6BCD\u8DDF\u6570\u5B57\u7684\u7EC4\u5408\u4F5C\u4E3A\u6587\u4EF6\u76EE\u5F55\u540D");
		FormData fd_lblNewLabel_2 = new FormData();
		fd_lblNewLabel_2.top = new FormAttachment(lblwebroot, 16);
		fd_lblNewLabel_2.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		lblNewLabel_2.setLayoutData(fd_lblNewLabel_2);
		lblNewLabel_2.setText("\u5907\u4EFD\u76EE\u5F55\u540D\u79F0\uFF1A");
		
		isActionDeployBtn = new Button(this, SWT.CHECK);
		isActionDeployBtn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				isActionDeploy = isActionDeployBtn.getSelection();
			}
		});
		isActionDeployBtn.setToolTipText("\u5047\u5982\u5C06\u6B64\u9009\u9879\u52FE\u9009\u4E0A\uFF0C\u5219\u4F1A\u81EA\u52A8\u6267\u884C unzip \u547D\u4EE4\uFF0C\u5B9E\u73B0\u81EA\u52A8\u66F4\u65B0\u6216\u8986\u76D6\u76EE\u6807\u7A0B\u5E8F\u3002");
		FormData fd_isActionDeploy = new FormData();
		fd_isActionDeploy.top = new FormAttachment(actionBtn, 5, SWT.TOP);
		fd_isActionDeploy.right = new FormAttachment(actionBtn, -9);
		isActionDeployBtn.setLayoutData(fd_isActionDeploy);
		isActionDeployBtn.setText("\u81EA\u52A8\u53D1\u5E03");
		this.outputPath = tempPath;
		
		actionBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				///processBusi();
			}

			public void widgetSelected(SelectionEvent e) {
				processBusi();
			}
		});
		
		
//		parent.open();
//		parent.layout();
//		Display display = getParent().getDisplay();
//		while (!parent.isDisposed()) {
//			if (!display.readAndDispatch()) {
//				display.sleep();
//			}
//		}
		
		return "";
	}

	
	
	/**
	 * 抽取目标文件
	 */
	protected void processBusi() {
		if(this.text.getText()==null||rootPath==null)
			return;
		backFilesScript.delete(0, backFilesScript.length());
		actionBtn.setText("提取中....");
		actionBtn.setEnabled(false);
		System.out.println("run processBusi.."+rootPath);
		File dirFile = new File(rootPath);
		int length = this.text.getText().split("\r\n").length;
		String[] filesPath  = this.text.getText().split("\r\n");
		this.resultText.setText("1、将要拷贝"+length+"个文件....\r\n");
		doAction(rootPath,filesPath);
	    try {
			createBackUpFileShellScript();
			createRollBackFileShellScript();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		actionBtn.setText("执行提取");
		actionBtn.setEnabled(true);
	}

	
	/**
	 * 执行文件提取
	 * @param root
	 * @param filesPath
	 */
	void doAction(String root,String[]filesPath){
	   root = root.replace("\\", "/");
	   String outPutDir =  this.outputPath;
	   String srcFileName = null;
	   String destFileName = null;
	   int s = 0;
	   int f = 0;
	   
	   List<String> tfilesPath =  difFiles(filesPath);
	   
	   for(String targetFile:tfilesPath){
		   if("".equals(targetFile.trim()))
			   continue;
		   targetFile = changeFilePathOrName(targetFile);
		   srcFileName=root+"/"+targetFile;
		   destFileName = outPutDir +"/"+targetFile; 
		   destFileName = destFileName.replace("\\", "/");
		   if(CopyFileUtil.copyFile(srcFileName, destFileName, true)){
		     resultText.append(""+srcFileName+" 提取成功。\r\n");
		     appendBakFileInfoToShScript(targetFile);
		     s++;
		   } 
		   else{
			 resultText.append(""+srcFileName+" 提取失败。\r\n");
			 f++;
		   }
	   }
	   this.resultText.append("文件已经拷贝完成！成功提取:"+s+"；提取失败："+f+"，共输入"+this.allFileNum+"个文件，其中有"+this.sameFileNum+"个重复。");
	   this.allFileNum = 0;
	   this.sameFileNum = 0;
	}
	
	String changeFilePathOrName(String src){
		if(src.indexOf(".java")>=0)
		    src = src.replace(".java", ".class");
		if(src.indexOf("src/")>=0)
			src = src.replace(src.substring(0,src.indexOf("src/")+4), "WEB-INF/classes/");
		if(isHtmPageSrc(src)){
			//找出web 根目录,形成相对路径
		}
		return src;
	}
	
	
	boolean isHtmPageSrc(String src){
	    boolean isOrNot = false;
		if(src.indexOf(".htm")>0||src.indexOf(".htm")>0){
			isOrNot = true;
		}else if(src.indexOf(".htm")>0||src.indexOf(".html")>0){
			isOrNot = true;
		}else if(src.indexOf(".jsp")>0||src.indexOf(".js")>0||src.indexOf(".swf")>0){
			isOrNot = true;
		}else if(src.indexOf(".gif")>0||src.indexOf(".png")>0||src.indexOf(".jpg")>0||src.indexOf(".GIF")>0||src.indexOf(".PNG")>0||src.indexOf(".JPG")>0){
			isOrNot = true;
		}
		return isOrNot;
	}
	
	
	int sameFileNum = 0;
	int allFileNum = 0;
	
	/**
	 * 
	 * 方法：去重
	 * 
	 * @param files
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-10-29
	 */
	List<String> difFiles(String[] files){
	    List<String> fileArry = new ArrayList<String>();
		for(String file:files){
			if(file==null||"".equals(file.trim()))
			   continue;
			allFileNum++;
			if(!fileArry.contains(file))
				fileArry.add(file);
			else
				sameFileNum++;
	    }
		return fileArry;
	}
	
	/**
	 * 
	 * 方法：保存参数设置
	 * 
	 * @param key
	 * @param value
	 *  
	 *    Add By Ethan Lam  At 2011-10-29
	 */
	void saveSetting(String key,String value){
		try {
			System.out.println("test: "+key+"  "+value+"   : "+serverWebRoot );
			File file = cfgFile();
		    Properties properties = new Properties();
		    FileOutputStream fos = new FileOutputStream(file); 
		    properties.setProperty(WebDeployComposite.OUTPUT_DIR,this.outputDir==null?"":this.outputDir);
		    properties.setProperty(WebDeployComposite.OUTPUT_FILENAME,("".equals(outputFile)?"output":outputFile));
		    properties.setProperty(WebDeployComposite.ROOT_DIR,this.rootPath==null?"":this.rootPath);
		    properties.setProperty(WebDeployComposite.SERVER_WEB_ROOT_DIR,serverWebRoot==null?"":serverWebRoot);
		    properties.setProperty(key,value==null?"":value);
     		properties.store(fos, "config.properties");
     		fos.close();
		} catch (IOException e) {
			e.printStackTrace();
    	}
	}
	
   
	File cfgFile() throws IOException{
		 File file = new File("config.properties");
		 if(!file.exists()){
		    file.createNewFile();
		 }
		 return file;
	}
	
	
	/**
	 * 
	 * 方法：获取设置
	 * 
	 * @param key
	 * @return
	 * @throws IOException
	 *  
	 *    Add By Ethan Lam  At 2011-10-29
	 */
	String getSetting(String key){
	     try {
	    	File file = cfgFile();
	    	Properties properties = new Properties();
	    	FileInputStream fin = new FileInputStream(file); 
			properties.load(fin);
			return properties.getProperty(key,"");
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
   String backUpItemName = "";//本次备份指定的文件名称，最好用数字或英文字母组成
   StringBuffer backFilesScript = new StringBuffer();
   String CRLF=" CRLF ";
   private Text serverWebRootTxt;
   private Text backUpItemTxt;
 StringBuffer filesRollBackScript = new StringBuffer();
   
   
   /**
    * 创建文件备份脚本内容
    * @param filePath
    */
   void appendBakFileInfoToShScript(String filePath){
	    backUpItemName = this.backUpItemTxt.getText();
	    backUpItemName=("".equals(backUpItemName)||backUpItemName==null)?"backup":backUpItemName;
	    filePath = filePath.replace("\\", "/");
	    filePath = filePath.indexOf("/")!=0?("/"+filePath):filePath;
	    String bakDir = "${curdir}\"/"+backUpItemName+"%FILE_PATH%\"";
	    bakDir = bakDir.replace("%FILE_PATH%",filePath.substring(0,filePath.lastIndexOf("/")));
	    //假如未创建目录，就先创建
	    backFilesScript.append("mkdir -p ").append(bakDir).append(CRLF);
	    //拷贝需要备份的文件到 备份目录中
	    String deployRootPath = this.serverWebRootTxt.getText();
	    String targetFileFullPath = (deployRootPath + filePath).replace("\\", "/");
	    backFilesScript.append("echo \"正在备份文件:\"").append(targetFileFullPath).append("  To:  ").append(bakDir).append(CRLF);
	    backFilesScript.append("cp ").append(" "+targetFileFullPath).append(" "+bakDir).append(CRLF);
	    
	    String bfileFullPath = bakDir+filePath.substring(filePath.lastIndexOf("/"),filePath.length()); 
	    String rfileFullPath = targetFileFullPath.substring(0, targetFileFullPath.lastIndexOf("/"));
	    filesRollBackScript.append("echo \"正在回滚文件:\"").append(bfileFullPath).append("  To:  ").append(rfileFullPath).append(CRLF);
	    filesRollBackScript.append("cp ").append(bfileFullPath).append("  ").append(rfileFullPath).append(CRLF);
        	    
   }
	
   
   
   /**
    * 生成备份与发布 脚本
    * @throws Exception
    */
   void createBackUpFileShellScript() throws Exception{
	      //生成发布脚本
		  String fileName =  this.outputDir+"/"+outputFile+"/deploy.sh";
		  File _file = new File(fileName);
		  if(!_file.exists())
		     _file.createNewFile();
		  
		  FileWriter fw=new FileWriter(_file);
		  BufferedWriter bw=new BufferedWriter(fw); 
		  String files = backFilesScript.toString();
		  
		  //打印必要的信息
		  outputScript(bw,"echo '正在执行文件备份操作...'");
		  outputScript(bw,"curdir=$(pwd)");
		  outputScript(bw,"echo \"当前备份操作所在的根路径：\"${curdir}");
		
		  //输出需要备份的文件
		  if(!files.equals("")){
			  String[] backFiles =  files.split(CRLF);
			  int line = 0;
			  while(line<backFiles.length){
				  System.out.println(backFiles[line]);
				  outputScript(bw,backFiles[line]);
			      line ++;
			  }
		  }
		  outputScript(bw,"echo '文件备份操作已经完成！'");
		  if(isActionDeploy){
			  outputScript(bw,"echo '将执行文件更新发布操作....'");
			  outputScript(bw,"unzip -uo "+this.outputFile+".zip -d "+this.serverWebRootTxt.getText()+"/");
			  outputScript(bw,"echo '已经完成文件更新发布操作！'");  
		  }
		  bw.close();
		  fw.close();
   }
   
   
   /**
    * 
    * 方法：创建回滚操作的脚本
    * 
    * @throws Exception
    *  
    *    Add By Ethan Lam  At 2011-11-22
    */
   void createRollBackFileShellScript() throws Exception{
		  String fileName =  this.outputDir+"/"+outputFile+"/rollback.sh";
		  File _file = new File(fileName);
		  if(!_file.exists())
		     _file.createNewFile();
		  
		  FileWriter fw=new FileWriter(_file);
		  BufferedWriter bw=new BufferedWriter(fw); 
		  String files = filesRollBackScript.toString();
		  
		  //打印必要的信息
		  outputScript(bw,"echo '正在执行回滚操作...'");
		  outputScript(bw,"curdir=$(pwd)");
		  outputScript(bw,"echo \"当前回滚操作所在的根路径：\"${curdir}");
		
		  //输出需要备份的文件
		  if(!files.equals("")){
			  String[] backFiles =  files.split(CRLF);
			  int line = 0;
			  while(line<backFiles.length){
				  outputScript(bw,backFiles[line]);
			      line ++;
			  }
		  }
		  outputScript(bw,"echo '文件回滚操作已经完成！'");
		  bw.close();
		  fw.close();
}
   
   /**
    * 
    * 方法：输出一行
    * 
    * @param bw
    * @param content
    * @throws IOException
    *  
    *    Add By Ethan Lam  At 2011-11-21
    */
   void outputScript(BufferedWriter bw,String content) throws IOException{
	      bw.write(content); 
	      bw.newLine();//断行
   }
}
