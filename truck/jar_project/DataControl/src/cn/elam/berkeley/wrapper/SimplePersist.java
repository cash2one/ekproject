package cn.elam.berkeley.wrapper;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import cn.elam.berkeley.DBEnvironment;

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

	private Class<DATA> persistentClass = null;
	
	public SimplePersist(String configPath, String dataBaseName) {
		this.configPath = configPath;
		this.dataBaseName = dataBaseName;
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
		DatabaseEntry theKey = null;
		DatabaseEntry theData = null;
		try {
			theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
            theData = new DatabaseEntry();
            EntryBinding myBinding = null;
            if(aData instanceof Long)
            	myBinding =  TupleBinding.getPrimitiveBinding(Long.class);
            else if(aData instanceof Double)
            	myBinding =  TupleBinding.getPrimitiveBinding(Double.class);
            else if(aData instanceof Float)
           	    myBinding =  TupleBinding.getPrimitiveBinding(Float.class);
            else if(aData instanceof Short)
           	    myBinding =  TupleBinding.getPrimitiveBinding(Short.class);
            else if(aData instanceof Integer)
           	    myBinding =  TupleBinding.getPrimitiveBinding(Integer.class);
            else if(aData instanceof Byte)
           	    myBinding =  TupleBinding.getPrimitiveBinding(Byte.class);
            else if(aData instanceof String)
           	    myBinding =  TupleBinding.getPrimitiveBinding(String.class);
            myBinding.objectToEntry(aData, theData);
            
			OperationStatus t = myDatabase.put(null, theKey, theData);
			return t == OperationStatus.KEYEXIST ? false : true;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} finally {
			theKey = null;
			theData = null;
			myDatabase.close();
			envir.close();
		}
	}

	public boolean persist(String aKey, DATA aData) {
		return persist(aKey, aData, PersistOperation.CREATE_DATA);
	}

	public DATA query(String aKey) {
		DBEnvironment envir = new DBEnvironment(configPath);
		Database myDatabase = envir.openDataBase(dataBaseName);
		DATA foundData = null;
		try {
			
			DatabaseEntry theKey = new DatabaseEntry(aKey.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
//			if (myDatabase.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
//				byte[] retData = theData.getData();
//				foundData = new String(retData, "UTF-8");
//			}
			this.persistentClass = checkClazz();
	        EntryBinding myBinding = TupleBinding.getPrimitiveBinding(persistentClass);
	        OperationStatus retVal = myDatabase.get(null, theKey, theData,LockMode.DEFAULT);
	        String retKey = null;
	        if (retVal == OperationStatus.SUCCESS) {
	        	foundData = (DATA) myBinding.entryToObject(theData);
	        } 
			
		} catch (Exception e) {
           e.printStackTrace();
		} finally {
			myDatabase.close();
			envir.close();
		}
		return foundData;
	}

	
	public Class checkClazz(){
	   	try {
			Type type  = this.getClass().getMethod("query", String.class).getGenericReturnType();
			return type.getClass();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}
	
	
	static Class getClass(Type type, int i) {     
        if (type instanceof ParameterizedType) { // 处理泛型类型     
            return getGenericClass((ParameterizedType) type, i);     
        } else if (type instanceof TypeVariable) {     
            return (Class) getClass(((TypeVariable) type).getBounds()[0], 0); // 处理泛型擦拭对象     
        } else {// class本身也是type，强制转型     
            return (Class) type;     
        }     
    }     
	
	static Class getGenericClass(ParameterizedType parameterizedType, int i) {     
	        Object genericClass = parameterizedType.getActualTypeArguments()[i];     
	        if (genericClass instanceof ParameterizedType) { // 处理多级泛型     
	            return (Class) ((ParameterizedType) genericClass).getRawType();     
	        } else if (genericClass instanceof GenericArrayType) { // 处理数组泛型     
	            return (Class) ((GenericArrayType) genericClass).getGenericComponentType();     
	        } else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象     
	            return (Class) getClass(((TypeVariable) genericClass).getBounds()[0], 0);     
	        } else {     
	            return (Class) genericClass;     
	        }     
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
