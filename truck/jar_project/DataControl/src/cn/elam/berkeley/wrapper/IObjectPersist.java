package cn.elam.berkeley.wrapper;

/**
 * 
 * @author Ethan.Lam 2011-3-31
 */
public interface IObjectPersist {
	public boolean persist(String aKey, Object aData);

	public Object getValue(String aKey,Class objectClass);

	public boolean delete(String aKey);

	public boolean update(String aKey, Object aData);
}
