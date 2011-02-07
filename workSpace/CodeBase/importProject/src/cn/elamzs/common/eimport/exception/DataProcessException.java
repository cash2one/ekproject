package cn.elamzs.common.eimport.exception;

/**
 * 
 * @author Ethan.Lam   2011-2-7
 *
 */
public class DataProcessException extends Exception {

	public DataProcessException(String errorMsg) {
		super(errorMsg);
	}

	public DataProcessException(String errorMsg, Throwable throwable) {
		super(errorMsg, throwable);
	}

}
