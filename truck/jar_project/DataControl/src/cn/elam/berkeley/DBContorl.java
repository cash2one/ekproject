package cn.elam.berkeley;

import java.io.File;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class DBContorl {

	Environment myDbEnvironment = null;
	Database myDatabase = null;
	private boolean immediatelySync = true;

	public static void main(String... arg) {

		DBContorl run = new DBContorl();
		run.openDataBase("export/dbEnv", "sampleDatabase");
		// run.insert("test", "测试测试测试");
		run.readData("key");
		run.readData("test");
		run.delete("key");
		run.closeDataBase();
		// System.out.println("测试");
	}

	/**
	 * 打开database环境
	 * 
	 * JE要求在任何DATABASE操作前，要先打开数据库环境，就像我们要使用数据库的话必须得先建立连接一样。
	 * 你可以通过数据库环境来创建和打开database，或者更改database名称和删除database.
	 * 可以通过Environments对象来打开环境，打开环境的时候设置的目录必须是已经存在的目录，否则会出错误。
	 * 默认情况下，如果指定的database不存在则不会自动创建一个新的detabase,但可以通过设置setAllowCreate来改变这一情况
	 */
	public void openDataBase(String configPath, String dataBaseName) {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);// 如果不存在则创建一个
		File file = new File(configPath);
		if (!file.exists())
			file.mkdirs();
		myDbEnvironment = new Environment(file, envConfig);

		// 打开一个数据库，如果数据库不存在则创建一个
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		// 打开一个数据库，数据库名为
		// sampleDatabase,数据库的配置为dbConfig
		myDatabase = myDbEnvironment.openDatabase(null, dataBaseName, dbConfig);

	}

	/**
	 * 关闭 database环境
	 * 可以通过Environment.close()这个方法来关闭database环境，当你完成数据库操作后一定要关闭数据库环境
	 */
	public void closeDataBase() {
		try {
			if (myDatabase != null) {
				myDatabase.close();
			}
			if (myDbEnvironment != null) {
				myDbEnvironment.close();
			}
		} catch (DatabaseException dbe) {
		}
	}

	/**
	 * 通常在关闭数据库连接的时候，有必要清理下日志，用以释放更多的磁盘空间。我们可以在Environment.close前执行下Environment.
	 * cleanLog()来达到此目的。
	 */
	public void cleanLog() {
		try {
			if (myDbEnvironment != null) {
				myDbEnvironment.cleanLog(); // 在关闭环境前清理下日志
				myDbEnvironment.close();
			}
		} catch (DatabaseException dbe) {
			// Exception handling goes here
		}
	}

	/**
	 * 当你对database进行了写操作的时候，你的修改不一定马上就能生效，有的时候他仅仅是缓存在RAM中，如果想让你的修改立即生效，
	 * 则可以使用Environment.sync()方法来把数据同步到磁盘中去。
	 */
	void immediatelySycUpdate() {
		if (immediatelySync) {
			if (myDbEnvironment != null)
				myDbEnvironment.sync();
		}
	}

	public void insert(String aKey, String aData) {
		try {
			// 设置key/value,注意DatabaseEntry内使用的是bytes数组
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry(aData.getBytes("UTF-8"));
			myDatabase.put(null, theKey, theData);
			immediatelySycUpdate();
		} catch (Exception e) {
			// 错误处理
		}
	}

	public void readData(String aKey) {
		try {
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();

			if (myDatabase.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				String foundData = new String(retData, "UTF-8");
				System.out.println("For key: '" + aKey + "' found data: '"
						+ foundData + "'.");
			} else {
				System.out.println("No record found for key '" + aKey + "'.");
			}
		} catch (Exception e) {
			// Exception handling goes here
		}
	}

	public void delete(String aKey) {
		try {
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			myDatabase.delete(null, theKey);
			immediatelySycUpdate();
		} catch (Exception e) {
		}
	}

}
