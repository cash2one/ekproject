package esfw.core.framework.exception;

/**
 * 业务逻辑层中，验证器   Exception
 * @author Ethan.Lam  2011-8-16
 *
 */
public class ValidateException extends BusinessException {

	/**
	 * 
	 * @param msg 验证出错信息
	 */
	public ValidateException(String msg) {
		super(msg,"ValidateException....");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param msg 验证出错信息
	 * @param throwable
	 */
	public ValidateException(String msg,Throwable throwable){
		super(msg,"ValidateException....",throwable);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
}
