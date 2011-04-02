package cn.elam.berkeley.wrapper.impl;

import java.io.UnsupportedEncodingException;

import cn.elam.berkeley.DBEnvironment;
import cn.elam.berkeley.sample.MyData;
import cn.elam.berkeley.wrapper.IObjectPersist;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 简单维护 索引 ---值 （KEY - VALUE）的关系 索引与值是一一对应的,不是一对多的关系
 * 新增记录时，能覆盖原来的的值
 * @author Ethan.Lam 2011-3-31
 */
public class ObjectPersist implements IObjectPersist {

	protected String configPath = "";
	protected String dataBaseName = "";
	protected final String CLAZZ_DB = "DataClazzDB";
	
	protected Class<?> persistentClass = null;
	
	public ObjectPersist(String configPath, String dataBaseName) {
		this.configPath = configPath;
		this.dataBaseName = dataBaseName;
//		persistentClass = (Class<DATA>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];  
	}

	
	/**
	 * 新增记录时，能覆盖原来的的值
	 * @param aKey
	 * @param aData
	 * @param operation
	 * @return
	 */
	protected boolean persist(String aKey, Object aData,
			PersistOperation operation) {
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myDatabase = envir.openDataBase(dataBaseName);
		Database myClassDb = envir.openDataBase(CLAZZ_DB);
		DatabaseEntry theKey = null;
		DatabaseEntry theData = null;
		DatabaseEntry theClassName = null;
		try {
			    // 3）创建catalog
			    StoredClassCatalog classCatalog = new StoredClassCatalog(myClassDb);
			   // 4）绑定数据和类
			    EntryBinding dataBinding = new SerialBinding(classCatalog, Object.class);
			    theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			   // 向DatabaseEntry里写数据
			    theData = new DatabaseEntry();
			    dataBinding.objectToEntry(aData, theData);
			    OperationStatus t = myDatabase.put(null, theKey, theData);
			
			return t == OperationStatus.KEYEXIST ? false : true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} finally {
			theKey = null;
			theData = null;
			myDatabase.close();
			myClassDb.close();
			envir.close();
		}
	}

	public boolean persist(String aKey, Object aData) {
		return persist(aKey, aData, PersistOperation.CREATE_DATA);
	}

	
	
	@SuppressWarnings("unchecked")  
	public Object getValue(String aKey,Class objectClass) {
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myDatabase = envir.openDataBase(dataBaseName);
		Database myClazzDatabase = envir.openDataBase(CLAZZ_DB);
		try {
			 // 实例化catalog
		    StoredClassCatalog classCatalog = new StoredClassCatalog(myClazzDatabase);
		    // 创建绑定对象
		    EntryBinding dataBinding = new SerialBinding(classCatalog,objectClass);
		    DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
		    DatabaseEntry theData = new DatabaseEntry();
		    OperationStatus retVal =  myDatabase.get(null, theKey, theData, LockMode.DEFAULT);
		    // Recreate the MyData object from the retrieved DatabaseEntry using
		    // 根据存储的类信息还原数据
	        if (retVal == OperationStatus.SUCCESS) {
	        	 return dataBinding.entryToObject(theData);
	        }else
			     return null;
		} catch (Exception e) {
           e.printStackTrace();
           return null;
		} finally {
			myDatabase.close();
			myClazzDatabase.close();
			envir.close();
		}
	}

	public boolean update(String aKey, Object aData) {
		return persist(aKey, aData, PersistOperation.UPDATE_DATA);
	}

	public boolean delete(String aKey) {
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myDatabase = envir.openDataBase(dataBaseName);
		DatabaseEntry theKey = null;
		try {
			theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			myDatabase.delete(null, theKey);
			return true;
		} catch (Exception e) {
		} finally {
			myDatabase.close();
			envir.close();
		}
		return false;
	}

	public enum PersistOperation {
		UPDATE_DATA, CREATE_DATA;
	}

}
