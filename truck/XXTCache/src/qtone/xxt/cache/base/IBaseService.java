package qtone.xxt.cache.base;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 * @param <V>
 *
 */
public interface IBaseService<V extends Serializable> {

	/**
	 * 获取Key对应的值
	 * @param key
	 * @return
	 */
	String getStr(String key);

	/**
	 * 设置Key的值（字符串）
	 * @param key
	 * @param value
	 */
	void saveStr(String key, String value);

	/**
	 * 更新Key的值
	 * @param key
	 * @param value
	 */
	void updateStr(String key, String value);

	/**
	 * 移除一个Key
	 * @param key
	 */
	void remove(String key);

	
	/**
	 * 
	 * @param key
	 * @return
	 */
	V get(String key);

	
	/**
	 * 
	 * @param key
	 * @param value
	 */
	void save(String key, V value);

	
	/**
	 * 
	 * @param key
	 */
	void removeStr(String key);

	/**
	 * 
	 * @param key
	 * @return
	 */
	Long incr(String key);

}