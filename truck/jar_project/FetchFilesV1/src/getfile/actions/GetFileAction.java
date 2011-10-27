package getfile.actions;

import getfile.ui.ChooseDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;


/**
 * Our sample action implements workbench action delegate. The action proxy will
 * be created by the workbench and shown in the UI. When the user tries to use
 * the action, this delegate will be created and execution will be delegated to
 * it.
 * 
 * @see IWorkbenchWindowActionDelegate
 */
public class GetFileAction implements IWorkbenchWindowActionDelegate {
	private IWorkbenchWindow window;

	/**
	 * The constructor.
	 */
	public GetFileAction() {
	}

	/**
	 * The action has been activated. The argument of the method represents the
	 * 'real' action sitting in the workbench UI.
	 * 
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

		org.eclipse.swt.widgets.DirectoryDialog dirDig = new DirectoryDialog(this.window.getShell(), SWT.CLOSE);
		String srcdir = dirDig.open();
		
		try {
			    File f = new File("."); 
			    String absolutePath = f.getAbsolutePath();
			    Properties properties = new Properties();
				properties.load(GetFileAction.class.getResourceAsStream("/config.properties"));
				if(srcdir==null||"".equals(srcdir)){
					//���浽�����ļ���
				    srcdir = properties.getProperty("root", "");		
				}else{
					String cfgPath ="config.properties";
					f = new File(cfgPath); 
				    absolutePath = f.getAbsolutePath();
					properties.setProperty("root", srcdir);
					properties.store(new FileOutputStream(cfgPath), "config.properties");
				}
				 System.out.println("��ǰ��·������Ϊ��"+srcdir);
			} catch (IOException e) {
				e.printStackTrace();
	    	}
		
		ChooseDialog dig = new ChooseDialog(window.getShell());
		dig.setSrcPath(srcdir);
		dig.open();

//		MessageDialog.openInformation(window.getShell(), "ѡ���·����", srcdir);
	}

	/**
	 * Selection in the workbench has been changed. We can change the state of
	 * the 'real' action here if we want, but this can only happen after the
	 * delegate has been created.
	 * 
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system resources we previously
	 * allocated.
	 * 
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to be able to provide parent shell
	 * for the message dialog.
	 * 
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}
}