package cn.elam.berkeley.wrapper;

/**
 * 简单维护 索引 ---值 （KEY - VALUE）的接口
 * 
 * @author Ethan.Lam 2011-3-31
 */
public interface ISimplePersist<DATA> {
	public boolean persist(String aKey, DATA aData);

	public DATA query(String aKey);

	public boolean delete(String aKey);

	public boolean update(String aKey, DATA aData);
}
