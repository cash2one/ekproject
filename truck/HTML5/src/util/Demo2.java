package util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Demo2 {

	private static JedisPool pool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxActive(100);
		config.setMaxIdle(20);
		config.setMaxWait(1000);
		config.setTestOnBorrow(true);
		pool = new JedisPool(config, "localhost");
	}

	private void initInsert() {
		Jedis jedis = pool.getResource();
		for (int i = 0; i < 20000; i++)
			jedis.set(String.valueOf(i), String.valueOf(i));
		pool.returnResource(jedis);
	}

}
