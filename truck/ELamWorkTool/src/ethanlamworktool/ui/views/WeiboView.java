package ethanlamworktool.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class WeiboView extends ViewPart {
	public WeiboView() {
	}

	private Browser browser;
	
	
	@Override
	public void createPartControl(Composite parent) {
		// TODO Auto-generated method stub

		 browser = new Browser(parent, SWT.NONE);
         browser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
         browser.setUrl("http://www.weibo.com");
         
         browser.addProgressListener(new ProgressListener() {

              public void changed(ProgressEvent arg0) {
                   // TODO Auto-generated method stub

              }

              public void completed(ProgressEvent arg0) {
            	  
//            	   String script="return STK.core.dom.isNode('loginname');";
            	  
                  String acscript=" STK.core.dom.sizzle(\"[node-type='loginname']\")[0].value = 'ethanlamzs@sina.cn';";
                  acscript+=" STK.core.dom.sizzle(\"[node-type='password']\")[0].value = '889276'; ";
                  
                  String js = FileUtils.readFileToString(new File(basepath+"\\loadJquery.txt"),"UTF-8");

                  
                  browser.execute(acscript);
                  System.out.println("脚本执行了............");
                  
            	  String script="return STK.core.dom.sizzle(\"[node-type='loginname']\")[0].value;";
            	  //System.out.println("obj==:" + browser.getText());
            	  Object ob = browser.evaluate(script);
                  System.out.println("脚本执行的结果:" + ob);
//                   browser.execute(script);
                   
              }
              
         });
		
		
	}
	
	

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
