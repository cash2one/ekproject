package cn.elam.util.db.comom;

/**
 * 数据访问错误类型
 * @author Ethan.Lam   2011-2-26
 *
 */
public class DaoException extends Exception {

	public DaoException(String message, Throwable t) {
		super(message, t);
	}

	public DaoException(String message) {
		super(message);
	}
}
