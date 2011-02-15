package cn.elam.util.db.comom;

public class DaoException extends Exception {

	public DaoException(String message, Throwable t) {
		super(message, t);
	}

	public DaoException(String message) {
		super(message);
	}
}
