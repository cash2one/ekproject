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
		shell.setText("�ļ���ȡ");
		shell.setLayout(new GridLayout(1, false));
	    
		text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
//		text.setFont(SWTResourceManager.getFont("����", 12, SWT.NORMAL));
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
		actionBtn.setText("ִ����ȡ");
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
//		setSrcBtn.setText("����·��");
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
	 * ��ȡĿ���ļ�
	 */
	protected void processBusi() {
		actionBtn.setText("��ȡ��....");
		actionBtn.setEnabled(false);
		System.out.println("run processBusi.."+srcDir);
		File dirFile = new File(srcDir);
//		System.out.println("������ı����ݣ�"+this.text.getText());
		int length = this.text.getText().split("\r\n").length;
		String[] filesPath  = this.text.getText().split("\r\n");
		this.resultText.setText("1����Ҫ����"+length+"���ļ�....\r\n");
		doAction(this.srcDir,filesPath);
		this.resultText.append("�ļ��Ѿ�������ɣ�");
		actionBtn.setText("ִ����ȡ");
		actionBtn.setEnabled(true);
	}

	
	/**
	 * ִ���ļ���ȡ
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
		     resultText.append(""+srcFileName+" ��ȡ�ɹ���\r\n");
		   else
			 resultText.append(""+srcFileName+" ��ȡʧ�ܡ�\r\n");
	   }
	}
	

	public void setSrcPath(String dir) {
		this.srcDir = dir;
		// TODO Auto-generated method stub
	}

}
