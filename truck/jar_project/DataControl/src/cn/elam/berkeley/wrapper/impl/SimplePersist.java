package cn.elam.berkeley.wrapper.impl;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import cn.elam.berkeley.DBEnvironment;
import cn.elam.berkeley.wrapper.ISimplePersist;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 简单维护 索引 ---值 （KEY - VALUE）的关系 索引与值是一一对应的,不是一对多的关系
 * 新增记录时，能覆盖原来的的值
 * @author Ethan.Lam 2011-3-31
 */
public class SimplePersist<DATA> implements ISimplePersist<DATA> {

	protected String configPath = "";
	protected String dataBaseName = "";
	protected final String CLAZZ_DB = "DataClazzDB";
	
	protected Class<DATA> persistentClass = null;
	
	public SimplePersist(String configPath, String dataBaseName) {
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
	protected boolean persist(String aKey, DATA aData,
			PersistOperation operation) {
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myDatabase = envir.openDataBase(dataBaseName);
		Database myClazzDatabase = envir.openDataBase(CLAZZ_DB);
		DatabaseEntry theKey = null;
		DatabaseEntry theData = null;
		DatabaseEntry theClassName = null;
		try {
			
			 theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
             theData = new DatabaseEntry();
            
             EntryBinding myBinding  =  TupleBinding.getPrimitiveBinding(aData.getClass());
             myBinding.objectToEntry(aData, theData);
			 OperationStatus t = myDatabase.put(null, theKey, theData);
			
			 theClassName = new DatabaseEntry(aData.getClass().getName().getBytes("UTF-8"));
			 myClazzDatabase.put(null, theKey, theClassName);
			
			return t == OperationStatus.KEYEXIST ? false : true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} finally {
			theKey = null;
			theData = null;
			myDatabase.close();
			myClazzDatabase.close();
			envir.close();
		}
	}

	public boolean persist(String aKey, DATA aData) {
		return persist(aKey, aData, PersistOperation.CREATE_DATA);
	}

	
	
	@SuppressWarnings("unchecked")  
	public DATA getValue(String aKey) {
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myDatabase = envir.openDataBase(dataBaseName);
		Database myClazzDatabase = envir.openDataBase(CLAZZ_DB);
		DATA foundData = null;
		try {
			Class dataClazz  = getDataClass(aKey);
			if(dataClazz==null)
				return null;
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();			
	        EntryBinding myBinding = TupleBinding.getPrimitiveBinding(dataClazz);
	        OperationStatus retVal = myDatabase.get(null, theKey, theData,LockMode.DEFAULT);
	        String retKey = null;
	        if (retVal == OperationStatus.SUCCESS) {
	        	foundData = (DATA) myBinding.entryToObject(theData);
	        } 
			
		} catch (Exception e) {
           e.printStackTrace();
		} finally {
			myDatabase.close();
			myClazzDatabase.close();
			envir.close();
		}
		return foundData;
	}

	
	private Class getDataClass(String aKey){
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myClazzDatabase = envir.openDataBase(CLAZZ_DB);
		try {
		    DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
		    DatabaseEntry theData = new DatabaseEntry();
		    if (myClazzDatabase.get(null, theKey, theData, LockMode.DEFAULT) ==OperationStatus.SUCCESS) {
		        byte[] retData = theData.getData();
		        String dataClazz = new String(retData, "UTF-8");
		        return getClass().forName(dataClazz);
		    } 
		} catch (Exception e) {
		}finally {
			myClazzDatabase.close();
			envir.close();
		}
		return null;
	}
	
	
	public boolean update(String aKey, DATA aData) {
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
