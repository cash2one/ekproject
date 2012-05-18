package esfw.core.framework.exception;

/**
 * 业务逻辑层的  Exception 接口
 * @author Ethan.Lam  2011-7-11
 *
 */
public class BusinessException extends Exception {

	/**
	 *  用户操作出错提示 信息
	 */
	private String userOperateExMsg; 
 
	public String getUserOperateExMsg() {
		return userOperateExMsg;
	}

	public void setUserOperateExMsg(String userOperateExMsg) {
		this.userOperateExMsg = userOperateExMsg;
	}

	/**
	 * 请选用其他构造函数，此构造函数已抛弃使用
	 * @param debugSysMsg 系统内部出错提示信息
    */
	@Deprecated
	public BusinessException(String debugSysMsg){
		super(debugSysMsg);
	}

	/**
	 * 请选用其他构造函数，此构造函数已抛弃使用
	 * @param debugSysMsg  系统内部出错提示信息
	 * @param throwable 
	 */
	@Deprecated
	public BusinessException(String debugSysMsg,Throwable throwable){
		super(debugSysMsg,throwable);
	}
	
	
	
	/**
	 * 
	 * @param userOperateExMsg  设置 返回给用户的消息
	 * @param debugSysMsg  设置系统出错提示信息
	 * @param throwable    
	 */
	public BusinessException(String userOperateExMsg,String debugSysMsg){
		super(userOperateExMsg+"....."+debugSysMsg);
		this.setUserOperateExMsg(userOperateExMsg);
	}
	
	/**
	 * 
	 * @param userOperateExMsg  设置 返回给用户的消息
	 * @param debugSysMsg  设置系统出错提示信息
	 * @param throwable    
	 */
	public BusinessException(String userOperateExMsg,String debugSysMsg,Throwable throwable){
		super(userOperateExMsg+"....."+debugSysMsg,throwable);
		this.setUserOperateExMsg(userOperateExMsg);
	}
	
}
