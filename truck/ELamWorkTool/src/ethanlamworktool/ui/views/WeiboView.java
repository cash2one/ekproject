package ethanlamworktool.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class WeiboView extends ViewPart {

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
            	  
                   System.out.println("obj==:" + browser.getText());
                   //Object ob = browser.evaluate("var  data=document.frames['main'].document.body;\n" +
                   ////          "  return data;");
                   ////System.out.println("data:" + ob);
              }
         });
		
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
