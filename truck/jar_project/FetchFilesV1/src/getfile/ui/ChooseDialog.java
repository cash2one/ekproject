package getfile.ui;

//import org.eclipse.jface.dialogs.Dialog;

import getfile.actions.GetFileAction;
import getfile.util.CopyFileUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class ChooseDialog extends Dialog {

	public ChooseDialog(Shell parent) {
		super(parent);

	}

	Shell shell;
	Text text;
	Text resultText;
	Button actionBtn;
	Text srcText;
	Text outputText;
	
	private Text rootPathTxt;
	private Label lblNewLabel;
	private Text outputFileTxt;
	private Label label_2;
	private Text outputPathTxt;
	
	private String rootPath;
	private String outputPath;
	private String outputDir;
	
	
	static String ROOT_DIR="ROOT_DIR";
	static String OUTPUT_DIR="OUTPUT_DIR";
	static String OUTPUT_FILENAME="OUTPUT_FILENAME";
	
	public String open() {
		
		Shell parent = this.getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(824, 572);
		shell.setText("文件提取");
		shell.setLayout(new FormLayout());
		
		lblNewLabel = new Label(shell, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.left = new FormAttachment(0, 15);
		fd_lblNewLabel.top = new FormAttachment(0, 10);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("\u5F53\u524D\u8DEF\u5F84\uFF1A");
		
		rootPathTxt = new Text(shell, SWT.BORDER);
		fd_lblNewLabel.right = new FormAttachment(rootPathTxt, -6);
		FormData fd_rootPathTxt = new FormData();
		fd_rootPathTxt.left = new FormAttachment(0, 85);
		fd_rootPathTxt.top = new FormAttachment(0, 7);
		rootPathTxt.setLayoutData(fd_rootPathTxt);
		rootPathTxt.setText(this.getSetting(ChooseDialog.ROOT_DIR));
	    this.rootPath = this.getSetting(ChooseDialog.ROOT_DIR);
		
		text = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_text.right = new FormAttachment(100, -15);
		text.setLayoutData(fd_text);
		
		
		resultText= new Text(shell, SWT.BORDER | SWT.WRAP | SWT.V_SCROLL | SWT.MULTI);
		FormData fd_resultText = new FormData();
		fd_resultText.right = new FormAttachment(text, 0, SWT.RIGHT);
		fd_resultText.bottom = new FormAttachment(0, 434);
		fd_resultText.left = new FormAttachment(0, 15);
		resultText.setLayoutData(fd_resultText);
		resultText.setEnabled(false);
		
		
		actionBtn = new Button(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		FormData fd_actionBtn = new FormData();
		fd_actionBtn.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		actionBtn.setLayoutData(fd_actionBtn);
		actionBtn.setText("执行提取");
		actionBtn.setSize(new Point(50, 50));
		actionBtn.setBounds(10, 10, 40, 18);
		
		Button srcPathBtnBrowser = new Button(shell, SWT.NONE);
		fd_rootPathTxt.right = new FormAttachment(srcPathBtnBrowser, -18);
		srcPathBtnBrowser.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				org.eclipse.swt.widgets.DirectoryDialog dirDig = new DirectoryDialog(shell, SWT.CLOSE);
				String dir = dirDig.open();
				if(dir == null)
					return;
				rootPathTxt.setText(dir);
				rootPath = dir;
				dir = dir.replace("\\","/");
				outputDir = dir.substring(0, dir.lastIndexOf("/"));
				outputPath =dir.substring(0, dir.lastIndexOf("/"))+"/"+("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				outputPathTxt.setText(outputPath);
				saveSetting(ChooseDialog.ROOT_DIR,rootPath);
				saveSetting(ChooseDialog.OUTPUT_DIR,outputDir);
				saveSetting(ChooseDialog.OUTPUT_FILENAME,("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText()));
			}
			
		});
		
		FormData fd_srcPathBtnBrowser = new FormData();
		fd_srcPathBtnBrowser.left = new FormAttachment(100, -60);
		fd_srcPathBtnBrowser.top = new FormAttachment(0, 5);
		fd_srcPathBtnBrowser.right = new FormAttachment(100, -10);
		srcPathBtnBrowser.setLayoutData(fd_srcPathBtnBrowser);
		srcPathBtnBrowser.setText("\u6D4F \u89C8");
		
		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		fd_text.top = new FormAttachment(lblNewLabel_1, 3);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 6);
		fd_lblNewLabel_1.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_lblNewLabel_1.right = new FormAttachment(0, 91);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("\u7C98\u8D34\u76EE\u6807\u6587\u4EF6");
		
		Label label = new Label(shell, SWT.NONE);
		fd_text.bottom = new FormAttachment(label, -6);
		fd_resultText.top = new FormAttachment(label, 6);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 220);
		fd_label.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		label.setLayoutData(fd_label);
		label.setText("\u6587\u4EF6\u63D0\u53D6\u7ED3\u679C");
		
		outputFileTxt = new Text(shell, SWT.BORDER);
		outputFileTxt.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
			        
				String outputT = outputDir+"/"+("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				outputT = outputT.replace("\\","/");
				if(outputPathTxt!=null)
				  outputPathTxt.setText(outputT);
				outputPath = outputT;
				saveSetting(ChooseDialog.OUTPUT_DIR,outputDir);
				saveSetting(ChooseDialog.OUTPUT_FILENAME,("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText()));
			}
		});
		
		FormData fd_outputFileTxt = new FormData();
		fd_outputFileTxt.top = new FormAttachment(resultText, 6);
		outputFileTxt.setLayoutData(fd_outputFileTxt);
		outputFileTxt.setText(this.getSetting(ChooseDialog.OUTPUT_FILENAME)==""?"output":this.getSetting(ChooseDialog.OUTPUT_FILENAME));
	    
		
		Label label_1 = new Label(shell, SWT.NONE);
		fd_outputFileTxt.left = new FormAttachment(label_1);
		FormData fd_label_1 = new FormData();
		fd_label_1.top = new FormAttachment(outputFileTxt, 3, SWT.TOP);
		fd_label_1.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
		fd_label_1.right = new FormAttachment(100, -707);
		label_1.setLayoutData(fd_label_1);
		label_1.setText("\u8F93\u51FA\u6587\u4EF6\u5939\u540D\u79F0\uFF1A");
		
		Button outputBtnButton = new Button(shell, SWT.NONE);
		fd_outputFileTxt.right = new FormAttachment(100, -571);
		outputBtnButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
				org.eclipse.swt.widgets.DirectoryDialog dirDig = new DirectoryDialog(shell, SWT.CLOSE);
				String dir = dirDig.open();
				outputDir = dir;
				String outputT = outputDir+"/"+("".equals(outputFileTxt.getText())?"output":outputFileTxt.getText());
				outputT = outputT.replace("\\","/");
				outputPathTxt.setText(outputT);
				outputPath = outputT;
				saveSetting(ChooseDialog.OUTPUT_DIR,dir);
			}
		});
		
		FormData fd_outputBtnButton = new FormData();
		fd_outputBtnButton.right = new FormAttachment(outputFileTxt, 56, SWT.RIGHT);
		fd_outputBtnButton.top = new FormAttachment(resultText, 6);
		fd_outputBtnButton.left = new FormAttachment(0, 253);
		outputBtnButton.setLayoutData(fd_outputBtnButton);
		outputBtnButton.setText("\u6D4F \u89C8");
		
		label_2 = new Label(shell, SWT.NONE);
		fd_actionBtn.top = new FormAttachment(label_2, 25);
		FormData fd_label_2 = new FormData();
		fd_label_2.top = new FormAttachment(label_1, 12);
		fd_label_2.right = new FormAttachment(lblNewLabel, 0, SWT.RIGHT);
		label_2.setLayoutData(fd_label_2);
		label_2.setText("\u7ED3\u679C\u4F4D\u7F6E\uFF1A");
		
		outputPathTxt = new Text(shell, SWT.BORDER);
		outputPathTxt.setEditable(false);
		FormData fd_outputPathTxt = new FormData();
		fd_outputPathTxt.top = new FormAttachment(outputBtnButton, 2);
		fd_outputPathTxt.left = new FormAttachment(label_2, 32);
		fd_outputPathTxt.right = new FormAttachment(100, -92);
		outputPathTxt.setLayoutData(fd_outputPathTxt);
		String tempPath = this.getSetting(ChooseDialog.OUTPUT_DIR)+"/"+(this.getSetting(ChooseDialog.OUTPUT_FILENAME)==""?"output":this.getSetting(ChooseDialog.OUTPUT_FILENAME));
		outputPathTxt.setText(tempPath);
		this.outputPath = tempPath;
		
		actionBtn.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				///processBusi();
			}

			public void widgetSelected(SelectionEvent e) {
				processBusi();
			}
		});
		
		
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		return "";
	}

	
	
	/**
	 * 抽取目标文件
	 */
	protected void processBusi() {
		if(this.text.getText()==null||rootPath==null)
			return;
		actionBtn.setText("提取中....");
		actionBtn.setEnabled(false);
		System.out.println("run processBusi.."+rootPath);
		File dirFile = new File(rootPath);
		int length = this.text.getText().split("\r\n").length;
		String[] filesPath  = this.text.getText().split("\r\n");
		this.resultText.setText("1、将要拷贝"+length+"个文件....\r\n");
		doAction(rootPath,filesPath);
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
	   for(String targetFile:filesPath){
		  
		   if("".equals(targetFile.trim()))
			   continue;
		   
		   targetFile = changeFilePathOrName(targetFile);
		   srcFileName=root+"/"+targetFile;
		   destFileName = outPutDir +"/"+targetFile; 
		   destFileName = destFileName.replace("\\", "/");
		   if(CopyFileUtil.copyFile(srcFileName, destFileName, true)){
		     resultText.append(""+srcFileName+" 提取成功。\r\n");
		     s++;
		   } 
		   else{
			 resultText.append(""+srcFileName+" 提取失败。\r\n");
			 f++;
		   }
	   }
	   this.resultText.append("文件已经拷贝完成！成功提取:"+s+"；提取失败："+f);
	}
	
	String changeFilePathOrName(String src){
		if(src.indexOf(".java")>=0)
		    src = src.replace(".java", ".class");
		if(src.indexOf("src/")>=0)
			src = src.replace(src.substring(0,src.indexOf("src/")+4), "WEB-INF/classes/");
		return src;
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
		    Properties properties = new Properties();
		    FileOutputStream fos = new FileOutputStream("config.properties"); 
			properties.setProperty(key,value==null?"":value);
     		properties.store(fos, "config.properties");
     		fos.close();
		} catch (IOException e) {
			e.printStackTrace();
    	}
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
	    	Properties properties = new Properties();
			properties.load(GetFileAction.class.getResourceAsStream("/config.properties"));
			return properties.getProperty(key,"");
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
}
