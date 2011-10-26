package getfile.ui;

//import org.eclipse.jface.dialogs.Dialog;

import getfile.util.CopyFileUtil;

import java.io.File;

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

public class ChooseDialog extends Dialog {

	private String srcDir;

	public ChooseDialog(Shell parent) {
		super(parent);

	}

	Shell shell;
	Text text;
	Text resultText;
	Button actionBtn;
	Text srcText;
	Text outputText;
	
	
	public String open() {
		
		
		Shell parent = this.getParent();
		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setSize(800, 500);
		shell.setText("文件提取");
		shell.setLayout(new GridLayout(1, false));
	    
		text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
//		text.setFont(SWTResourceManager.getFont("宋体", 12, SWT.NORMAL));
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_text.heightHint = 122;
		gd_text.widthHint = 296;
		text.setLayoutData(gd_text);
		
		
		resultText= new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		resultText.setEnabled(false);
		GridData gd_resultText = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_resultText.heightHint = 142;
		resultText.setLayoutData(gd_resultText);
		
		
//		srcText= new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
//		srcText.setEnabled(false);
//		GridData gd_srcText = new GridData(SWT.FILL, SWT.CENTER,false,false, 1, 1);
//		gd_srcText.heightHint = 10;
//		resultText.setLayoutData(gd_srcText);
		
		
		actionBtn = new Button(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
		actionBtn.setText("执行提取");
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
		
		
		
//		
//		Button setSrcBtn = new Button(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
//		setSrcBtn.setText("设置路径");
//		setSrcBtn.setSize(new Point(50, 50));
//		setSrcBtn.setBounds(10, 10, 40, 18);
//		
//		setSrcBtn.addSelectionListener(new SelectionListener() {
//
//			public void widgetDefaultSelected(SelectionEvent e) {
//				///processBusi();
//			}
//
//			public void widgetSelected(SelectionEvent e) {
//				org.eclipse.swt.widgets.DirectoryDialog dirDig = new DirectoryDialog(shell,SWT.CLOSE);
//				srcDir = dirDig.open();
//				srcText.setText(srcDir);
//			}
//		});
		
		
		
		
		
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
		actionBtn.setText("提取中....");
		actionBtn.setEnabled(false);
		System.out.println("run processBusi.."+srcDir);
		File dirFile = new File(srcDir);
//		System.out.println("输入的文本内容："+this.text.getText());
		int length = this.text.getText().split("\r\n").length;
		String[] filesPath  = this.text.getText().split("\r\n");
		this.resultText.setText("1、将要拷贝"+length+"个文件....\r\n");
		doAction(this.srcDir,filesPath);
		this.resultText.append("文件已经拷贝完成！");
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
	   String  outPutDir =  root.substring(0, root.lastIndexOf("/"))+"/output";
	   String srcFileName = null;
	   String destFileName = null;
	   for(String targetFile:filesPath){
		   if("".equals(targetFile.trim()))
			   continue;
		   srcFileName=root+"/"+targetFile;
		   destFileName = outPutDir +"/"+targetFile; 
		   destFileName = destFileName.replace("\\", "/");
		   if(CopyFileUtil.copyFile(srcFileName, destFileName, true))
		     resultText.append(""+srcFileName+" 提取成功。\r\n");
		   else
			 resultText.append(""+srcFileName+" 提取失败。\r\n");
	   }
	}
	

	public void setSrcPath(String dir) {
		this.srcDir = dir;
		// TODO Auto-generated method stub
	}

}
