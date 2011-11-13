package service.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import redis.clients.jedis.Jedis;

/**
 * 
 * @author yongboy
 * @date 2011-4-4
 * @version 1.0
 * @param <V>
 */
public abstract class BaseServiceImpl<V extends Serializable> implements
		IBaseService<V> {
	private static final String REDIS_HOST;
	static {
		Properties prop = new Properties();
		try {
			prop.load(BaseServiceImpl.class
					.getResourceAsStream("/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new NullPointerException("/config.propertis is not found !");
		}
		
		REDIS_HOST = prop.getProperty("redis");
	}

	public Jedis jedis = new Jedis(REDIS_HOST);

	public V get(String key) {
		return byte2Object(jedis.get(getKey(key)));
	}

	public void save(String key, V value) {
		jedis.set(getKey(key), object2Bytes(value));
	}

	public void remove(String key) {
		jedis.del(getKey(key));
	}

	public String getStr(String key) {
		return this.jedis.get(key);
	}

	public void saveStr(String key, String value) {
		this.jedis.set(key, value);
	}

	public void updateStr(String key, String value) {
		saveStr(key, value);
	}

	public List<String> find(int pageNum, int pageSize) {
		return null;
	}

	public void removeStr(String key) {
		this.jedis.del(key);
	}

	private byte[] getKey(String key) {
		return key.getBytes();
	}

	public Long incr(String key) {
		return this.jedis.incr(key);
	}

	public void addHeadList(String key, String oneValue) {
		this.jedis.lpush(key, oneValue);
	}

	
	public void addMemberToSet(String key, String member) {
		this.jedis.sadd(key, member);
	}
	
	public void removeSetMember(String key, String member) {
		this.jedis.srem(key, member);
	}
	
	
	/**
	 * 
	 * 方法：KEY 的 处理
	 * 
	 * @param formatStr
	 * @param vals
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-11-13
	 */
	public String getFormatKeyStr(String formatStr,Object... vals){
		return String.format(formatStr, vals);
	}
	
	
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