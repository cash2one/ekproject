package websrcdeployrc;

import getfile.ui.WebDeployComposite;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {
	public View() {
	}
	public static final String ID = "WebSrcDeployRc.view";

	private TableViewer viewer;
 
	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout());
		WebDeployComposite composite = new WebDeployComposite(parent, SWT.MULTI
				| SWT.H_SCROLL | SWT.V_SCROLL);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
	}
}