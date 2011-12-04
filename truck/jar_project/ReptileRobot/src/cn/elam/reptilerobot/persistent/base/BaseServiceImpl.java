package cn.elam.reptilerobot.persistent.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import cn.elam.reptilerobot.persistent.JedisPooler;

/**
 * 
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 * @param <V>
 *
 */
public abstract class BaseServiceImpl<V extends Serializable> implements
		IBaseService<V> {

	private JedisPool pool = JedisPooler.getPool();

	
	public V get(String key) {
		Jedis jedis = pool.getResource();
		byte[] val = jedis.get(getKey(key));
		pool.returnResource(jedis);
		return byte2Object(val);
	}

	public void save(String key, V value) {
		Jedis jedis = pool.getResource();
		jedis.set(getKey(key), object2Bytes(value));
		pool.returnResource(jedis);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.elam.reptilerobot.persistent.base.IBaseService#remove(java.lang.String)
	 */
	public void remove(String key) {
		Jedis jedis = pool.getResource();
		jedis.del(getKey(key));
		pool.returnResource(jedis);
	}

	/*
	 * (non-Javadoc)
	 * @see cn.elam.reptilerobot.persistent.base.IBaseService#getStr(java.lang.String)
	 */
	public String getStr(String key) {
		Jedis jedis = pool.getResource();
		String val =  jedis.get(key);
		pool.returnResource(jedis);
		return val;
	}
    
	/*
	 * (non-Javadoc)
	 * @see cn.elam.reptilerobot.persistent.base.IBaseService#saveStr(java.lang.String, java.lang.String)
	 */
	public void saveStr(String key, String value) {
		Jedis jedis = pool.getResource();
		jedis.set(key, value);
		pool.returnResource(jedis);
	}

	public void updateStr(String key, String value) {
		saveStr(key, value);
	}

	public List<String> find(int pageNum, int pageSize) {
		return null;
	}

	public void removeStr(String key) {
		Jedis jedis = pool.getResource();
		jedis.del(key);
		pool.returnResource(jedis);
	}

	private byte[] getKey(String key) {
		return key.getBytes();
	}

	/**
	 * 从变量 +1 操作
	 */
	public Long incr(String key) {
		Jedis jedis = pool.getResource();
		Long val =  jedis.incr(key);
		pool.returnResource(jedis);
		return val;
	}

	/**
	 * 
	 * 方法：从列表的头部添加值
	 * 
	 * @param key
	 * @param oneValue
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void addHeadList(String key, String oneValue) {
		Jedis jedis = pool.getResource();
		jedis.lpush(key, oneValue);
		pool.returnResource(jedis);
	}

	
	/**
	 * 
	 * 方法：从列表的尾部添加值
	 * 
	 * @param key
	 * @param oneValue
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void addTailList(String key, String oneValue) {
		Jedis jedis = pool.getResource();
		jedis.rpush(key, oneValue);
		pool.returnResource(jedis);
	}
	
	/**
	 * 
	 * 方法：返回列表的长度
	 * 
	 * @param key
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public long getListLength(String key) {
		Jedis jedis = pool.getResource();
		long length = jedis.llen(key);
		pool.returnResource(jedis);
		return length;
	}
	
	/**
	 * 
	 * 方法：返回名称为key的list中start至end之间的元素
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public List<String> ListRange(String key,int start,int end) {
		Jedis jedis = pool.getResource();
		List<String> values = jedis.lrange(key, start, end);
		pool.returnResource(jedis);
		return values;
	}
	
	/**
	 * 
	 * 方法：分页加载对应的 List中的  值
	 * 
	 * @param listKey
	 * @param idKey
	 * @param page
	 * @param pageSize
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	protected List<V> loadListByPageIds(String listKey,String idKey,int page,int pageSize){
		Jedis jedis = pool.getResource();
        long allRecords = jedis.llen(listKey);
        int start = (page-1)*pageSize;
        int end = start+pageSize-1 ;
        if(start>allRecords)
        	return null;
        
        end = (int) (end>allRecords?allRecords:end);
		List<String> ids = jedis.lrange(listKey, start, end);
		List<V> resultList = new ArrayList<V>();
		if(ids!=null){
           for(String id:ids){
        	   resultList.add(get(String.format(idKey,id)));
           }			
		}
		pool.returnResource(jedis);
		return resultList;
	}
	
	
	/**
	 * 
	 * 方法：在Set中添加值
	 * 
	 * @param key
	 * @param member
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void addMemberToSet(String key, String member) {
		Jedis jedis = pool.getResource();
		jedis.sadd(key, member);
		pool.returnResource(jedis);
	}
	
	
	/**
	 * 
	 * 方法：在Set中删除值
	 * 
	 * @param key
	 * @param member
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public void removeSetMember(String key, String member) {
		Jedis jedis = pool.getResource();
		jedis.srem(key, member);
		pool.returnResource(jedis);
	}
	
	
	/**
	 * 
	 * 方法：格式化字符串
	 * 
	 * @param formatStr
	 * @param vals
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	public String getFormatKeyStr(String formatStr,Object... vals){
		return String.format(formatStr, vals);
	}
	
	
	/**
	 * 
	 * 方法：对象序列化逆向
	 * 
	 * @param bytes
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	@SuppressWarnings("unchecked")
	private V byte2Object(byte[] bytes) {
		if (bytes == null || bytes.length == 0)
			return null;

		try {
			ObjectInputStream inputStream;
			inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object obj = inputStream.readObject();

			return (V) obj;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 
	 * 方法：对象序列化
	 * 
	 * @param value
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-12-4
	 */
	private byte[] object2Bytes(V value) {
		if (value == null)
			return null;

		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream outputStream;
		try {
			outputStream = new ObjectOutputStream(arrayOutputStream);

			outputStream.writeObject(value);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				arrayOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return arrayOutputStream.toByteArray();
	}
}