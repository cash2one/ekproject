package esfw.core.exception;


/**
 * 
 * 数据访问层的错误提示
 * @author Ethan.Lam  2011-10-9
 *
 */
public class DaoAccessException extends Exception{

	 public DaoAccessException(String message,Throwable t){
		 super(message,t);
	 }
	 
}
