package cn.elam.berkeley;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;

/***
 * 
 * Berkeley DB 运行环境
 * 
 * @author Ethan.Lam 2011-3-31
 * 
 */
public class DBEnvironment {

	Environment environment = null;

	private boolean immediatelySync = false;

	public DBEnvironment(String configPath) {
		environment = init(configPath);
	}

	public DBEnvironment(String configPath, boolean immediatelySync) {
		this(configPath);
		this.immediatelySync = immediatelySync;
	}

	/**
	 * 打开database环境 JE要求在任何DATABASE操作前，要先打开数据库环境，就像我们要使用数据库的话必须得先建立连接一样。
	 * 你可以通过数据库环境来创建和打开database，或者更改database名称和删除database.
	 * 可以通过Environments对象来打开环境，打开环境的时候设置的目录必须是已经存在的目录，否则会出错误。
	 * 默认情况下，如果指定的database不存在则不会自动创建一个新的detabase,但可以通过设置setAllowCreate来改变这一情况
	 * 
	 * @return
	 */
	public Database openDataBase(String dataBaseName) {
		// 打开一个数据库，如果数据库不存在则创建一个
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		return environment.openDatabase(null, dataBaseName, dbConfig);
	}

	/**
	 * 环境初始化
	 * 
	 * @param configPath
	 * @param dataBaseName
	 * @return
	 */
	public Environment init(String configPath) {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);// 如果不存在则创建一个
		File file = new File(configPath);
		if (!file.exists())
			file.mkdirs();
		return new Environment(file, envConfig);
	}

	/**
	 * 在关闭环境前清理下日志
	 */
	void cleanLog() {
		try {
			if (environment != null) {
				environment.cleanLog();
			}
		} catch (DatabaseException e) {

		}
	}

	/**
	 * 当你对database进行了写操作的时候，你的修改不一定马上就能生效，有的时候他仅仅是缓存在RAM中，如果想让你的修改立即生效，
	 * 则可以使用Environment.sync()方法来把数据同步到磁盘中去。
	 */
	public void immediatelySync() {
		if (environment != null)
			environment.sync();
	}

	/**
	 * 关闭数据库环境
	 */
	public void close() {
		if (this.immediatelySync)
			immediatelySync();
		
		cleanLog();
		
		if (environment != null)
			environment.close();
	}

}
