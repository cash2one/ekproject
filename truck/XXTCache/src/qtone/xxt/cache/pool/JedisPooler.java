package qtone.xxt.cache.pool;

import java.io.File;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis 连接池实例
 * @author Ethan.Lam  
 * @createTime 2011-12-3
 *
 */
public class JedisPooler {

	 private static JedisPool pool;
     private static final String REDIS_HOST;
 		
	 static {
		   Properties prop = new Properties();
	 		try {
	 			//
	 			File file = new File("config.properties");
//	 			FileInputStream fin = new FileInputStream(file); 
//	 			prop.load(fin);
//	 			prop.load(JedisPooler.class.getResourceAsStream("config.properties"));
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 			throw new NullPointerException("/config.propertis is not found !");
	 		}
//	 		REDIS_HOST = prop.getProperty("REDIS_HOST");
	 		
	 		REDIS_HOST = "192.168.4.39";
	        JedisPoolConfig config = new JedisPoolConfig();
	        config.setMaxActive(100);
	        config.setMaxIdle(20);
	        config.setMaxWait(1000);
	        config.setTestOnBorrow(true);
	        pool = new JedisPool(config, REDIS_HOST);
	  }

	 public static JedisPool getPool() {
	        return pool;
	 }

	 public static void setPool(JedisPool pool) {
	    	JedisPooler.pool = pool;
	 }

	 public static void close(Jedis jedis){
		 if(jedis!=null)
		   pool.returnResource(jedis);
	 }
	 
}
