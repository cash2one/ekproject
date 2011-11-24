package getfile.ui;

import getfile.util.CopyFileUtil;
import getfile.util.zipHelper;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

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
		setLayout(new GridLayout(8, false));
		
		lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setText("  \u5DF2\u7F16\u8BD1\u7684WebRoot\u8DEF\u5F84\uFF1A");
		
		rootPathTxt = new Text(this, SWT.BORDER);
		rootPathTxt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 6, 1));
		rootPathTxt.setToolTipText("\u5DF2\u7F16\u8BD1\u6210\u529F\uFF0C\u5F85\u53D1\u5E03\u4EE3\u7801\u6E90\u6587\u4EF6\u7684\u6839\u76EE\u5F55");
		rootPathTxt.setText(this.getSetting(WebDeployComposite.ROOT_DIR));
	    this.rootPath = this.getSetting(WebDeployComposite.ROOT_DIR);
	    this.rootPath = this.rootPath.replace("\\", "/");
	    this.outputDir = "".equals(this.getSetting(WebDeployComposite.OUTPUT_DIR))?(rootPath.length()>0?rootPath.substring(0,rootPath.lastIndexOf("/")):""):this.getSetting(WebDeployComposite.OUTPUT_DIR);
	    this.serverWebRoot = this.getSetting(WebDeployComposite.SERVER_WEB_ROOT_DIR);
		
		Button srcPathBtnBrowser = new Button(this, SWT.NONE);
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
		srcPathBtnBrowser.setText("\u6D4F \u89C8");
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblNewLabel_1.setText("  \u7C98\u8D34\u9700\u8981\u62F7\u8D1D\u76EE\u6807\u6587\u4EF6");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	    
	    
		text = new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1);
		gd_text.heightHint = 84;
		text.setLayoutData(gd_text);
		text.setToolTipText("\u5728\u6B64\u7C98\u8D34\u9700\u8981\u63D0\u53D6\u7684\u6587\u4EF6\u8BE6\u7EC6\u5217\u8868\uFF1B\u6240\u6709\u88AB\u63D0\u53D6\u7684\u6587\u4EF6\u7684\u8DEF\u5F84\u9700\u76F8\u5BF9\u4E8E\u201C\u5DF2\u7F16\u8BD1\u7684webRoot\u8DEF\u5F84\u201D\uFF01");
		
		Label label = new Label(this, SWT.NONE);
		label.setText("  \u6587\u4EF6\u63D0\u53D6\u7ED3\u679C");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		
		resultText= new Text(this, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		resultText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 8, 1));
		resultText.setToolTipText("\u6587\u4EF6\u63D0\u53D6\u7ED3\u679C");
		resultText.setEditable(false);
		
		
		Label label_1 = new Label(this, SWT.NONE);
		label_1.setText("  \u5B9A\u4E49\u53D1\u5E03\u540D\u79F0\uFF1A");
		
		outputFileTxt = new Text(this, SWT.BORDER);
		GridData gd_outputFileTxt = new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1);
		gd_outputFileTxt.widthHint = 78;
		outputFileTxt.setLayoutData(gd_outputFileTxt);
		outputFileTxt.setToolTipText("\u5B9A\u4E49\u672C\u6B64\u7684\u53D1\u5E03\u64CD\u4F5C\u540D\u79F0\uFF0C\u6B64\u6B21\u64CD\u4F5C\u76F8\u5173\u7684 \u6587\u4EF6\uFF0C\u5C06\u81EA\u52A8\u5907\u4EFD\u5230\u5F53\u524D\u7684\u76EE\u5F55\u4E0B");
		outputFileTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			        
				String outputT = outputDir+"/"+("".equals(outputFileTxt.getText().trim())?"output":outputFileTxt.getText().trim());
				outputT = outputT.replace("\\","/");
				if(outputPathTxt!=null)
				  outputPathTxt.setText(outputT);
				outputPath = outputT;
				outputFile = ("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				saveSetting(WebDeployComposite.OUTPUT_DIR,outputDir);
				saveSetting(WebDeployComposite.OUTPUT_FILENAME,("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText()));
			}
		});
		outputFileTxt.setText(this.getSetting(WebDeployComposite.OUTPUT_FILENAME)==""?"output":this.getSetting(WebDeployComposite.OUTPUT_FILENAME));
		
		Button outputBtnButton = new Button(this, SWT.NONE);
		outputBtnButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
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
		outputBtnButton.setText("\u6D4F \u89C8");
		
		label_2 = new Label(this, SWT.NONE);
		label_2.setToolTipText("\u6307\u5B9A\u751F\u6210\u53D1\u5E03\u66F4\u65B0\u6587\u4EF6\u7684\u6839\u8DEF\u5F84");
		label_2.setText("  \u672C\u5730\u5B58\u653E\u4F4D\u7F6E\uFF1A");
		
		
		outputPathTxt = new Text(this, SWT.BORDER);
		outputPathTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 7, 1));
		outputPathTxt.setEditable(false);
		
		String tempPath = this.getSetting(WebDeployComposite.OUTPUT_DIR)+"/"+(this.getSetting(WebDeployComposite.OUTPUT_FILENAME)==""?"output":this.getSetting(WebDeployComposite.OUTPUT_FILENAME));
		outputPathTxt.setText(tempPath);
		
		
		Label lblwebroot = new Label(this, SWT.NONE);
		lblwebroot.setText("  \u7EBF\u4E0AwebRoot:");
		serverWebRootTxt = new Text(this, SWT.BORDER);
		serverWebRootTxt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 5, 1));
		serverWebRootTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				serverWebRoot = serverWebRootTxt.getText().trim();
				serverWebRoot = serverWebRoot.replace("\\", "/");
				serverWebRoot = (serverWebRoot.lastIndexOf("/")==serverWebRoot.length()-1)?serverWebRoot.substring(0, serverWebRoot.lastIndexOf("/")):serverWebRoot;
				saveSetting(WebDeployComposite.SERVER_WEB_ROOT_DIR,serverWebRoot);
			}
		});
		
		serverWebRootTxt.setToolTipText("\u586B\u5199\u7EBF\u4E0A\u7248\u672C\u7684 webRoot \u6839\u8DEF\u5F84\uFF0C\u5982 /data/zjxxt/");
		serverWebRootTxt.setText(serverWebRoot);
		System.out.println("cfg: ROOT_DIR -> "+this.getSetting(WebDeployComposite.ROOT_DIR));
		System.out.println("cfg: OUTPUT_DIR -> "+this.getSetting(WebDeployComposite.OUTPUT_DIR));
		System.out.println("cfg: OUTPUT_FILENAME->"+this.getSetting(WebDeployComposite.OUTPUT_FILENAME));
		
		serverWebRoot = getSetting(WebDeployComposite.SERVER_WEB_ROOT_DIR);
		this.outputPath = tempPath;
		
		
		actionBtn = new Button(this, SWT.TOGGLE);
		GridData gd_actionBtn = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_actionBtn.widthHint = 61;
		actionBtn.setLayoutData(gd_actionBtn);
		actionBtn.setText("\u6267\u884C");
		actionBtn.setSize(new Point(50, 50));
		actionBtn.setBounds(10, 10, 40, 18);
		
		actionBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				///processBusi();
			}

			public void widgetSelected(SelectionEvent e) {
				processBusi();
			}
		});
		
		return "";
	}

	
	
	/**
	 * 抽取目标文件
	 */
	protected void processBusi() {
		if(this.text.getText()==null||rootPath==null)
			return;
		
		//执行文本清理操作
		backFilesScript.delete(0, backFilesScript.length());
		filesRollBackScript.delete(0, filesRollBackScript.length());

		actionBtn.setText("提取中....");
		actionBtn.setEnabled(false);
		File dirFile = new File(rootPath);
		int length = this.text.getText().split("\r\n").length;
		String[] filesPaths  = this.text.getText().split("\r\n");
		this.resultText.setText("1、将要拷贝"+length+"个文件....\r\n");
		doAction(rootPath,filesPaths);
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
	   
	   try {
		   actionBtn.setText("文件打包...");
		   //执行文件压缩打包操作
		   String tempZipFileName = this.outputPath+".zip";
		   String inputDirName = this.outputPath;
		   System.out.println("执行文件打包操作...zipFileName:"+tempZipFileName+",inputDirName:"+inputDirName);
		   zipHelper.zip(tempZipFileName, inputDirName);
		   
		   String newZipFile  = this.outputPath+"/"+this.outputFile+".zip";
		   CopyFileUtil.copyFile(tempZipFileName, newZipFile, true);
           new File(tempZipFileName).delete();
           
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	}
	
	
	/**
	 * 检查发布的文件路径
	 * @param src
	 * @return
	 */
	String changeFilePathOrName(String src){
		src = src.replace("\\", "/");
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
			if(!fileArry.contains(file.trim()))
				fileArry.add(file.trim());
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
   StringBuffer filesRollBackScript = new StringBuffer();
   
   
   /**
    * 创建文件备份脚本内容
    * @param filePath
    */
   void appendBakFileInfoToShScript(String filePath){
	    backUpItemName = this.outputFile;
	    backUpItemName=("".equals(backUpItemName)||backUpItemName==null)?"backup":backUpItemName;
	    filePath = filePath.replace("\\", "/");
	    filePath = filePath.indexOf("/")!=0?("/"+filePath):filePath;
	    String bakDir = "${curdir}\"/"+backUpItemName+"%FILE_PATH%\"";
	    bakDir = bakDir.replace("%FILE_PATH%",filePath.substring(0,filePath.lastIndexOf("/")));
	    //假如未创建目录，就先创建
	    backFilesScript.append("mkdir -p ").append(bakDir).append(CRLF);
	    
	    //拷贝需要备份的文件到 备份目录中
	    String deployRootPath = this.serverWebRoot;
	    String targetFileFullPath = (deployRootPath + filePath).replace("\\", "/");
	    backFilesScript.append("echo \"正在备份文件: ").append(targetFileFullPath).append("  To:  \"").append(bakDir).append(CRLF);
	    backFilesScript.append("cp -f ").append(" "+targetFileFullPath).append("   "+bakDir).append(CRLF);
	    
	    
	    //创建对应的回滚脚本
	    String bfileFullPath = bakDir.substring(0, bakDir.length()-1)+filePath.substring(filePath.lastIndexOf("/"),filePath.length()); 
	    String rfileFullPath = targetFileFullPath.substring(0, targetFileFullPath.lastIndexOf("/"));
	    filesRollBackScript.append("echo \"正在回滚文件:  \"").append(bfileFullPath).append("  To:  ").append(rfileFullPath).append("\"").append(CRLF);
	    
	    if(!isDirecotor(targetFileFullPath))
	        filesRollBackScript.append("rm -f ").append(targetFileFullPath).append(CRLF);
	    filesRollBackScript.append("cp -f ").append(bfileFullPath).append("\"  ").append(rfileFullPath).append(CRLF);
        	    
   }
	
   
   /**
    * 判断其是否属于文件夹
    * @param filePath
    * @return
    */
   boolean isDirecotor(String filePath){
       String lastSurfixxName = filePath.substring(filePath.lastIndexOf("/"),filePath.length()); 
	   if(lastSurfixxName.indexOf(".")>0)
		   return false;
	   return true;
   }
   
   
   /**
    * 生成备份与发布 脚本
    * @throws Exception
    */
   void createBackUpFileShellScript() throws Exception{
	      //生成发布脚本
	      String scritpName = "".equals(this.outputFile.trim())?"new_":this.outputFile.trim();
		  String fileName =  this.outputDir+"/"+outputFile+"/"+scritpName+"_deploy.sh";
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
		  outputScript(bw,"echo '将执行文件更新发布操作....'");
		  outputScript(bw,"unzip -uo "+this.outputFile+".zip -d "+this.serverWebRootTxt.getText()+"/");
		  outputScript(bw,"echo '已经完成文件更新发布操作！'");  
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
	      String scritpName = "".equals(this.outputFile.trim())?"new_":this.outputFile.trim();
		  String fileName =  this.outputDir+"/"+outputFile+"/"+scritpName+"_rollback.sh";
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
