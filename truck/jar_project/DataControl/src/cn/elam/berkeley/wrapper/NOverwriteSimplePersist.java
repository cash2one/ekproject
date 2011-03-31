package cn.elam.berkeley.wrapper;

import java.io.UnsupportedEncodingException;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.tuple.TupleBinding;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import cn.elam.berkeley.DBEnvironment;
import cn.elam.berkeley.wrapper.SimplePersist.PersistOperation;

/**
 * 简单维护 索引 ---值 （KEY - VALUE）的关系 索引与值是一一对应的,不是一对多的关系
 * 新增记录时，不能覆盖原来的的值
 * @author Ethan.Lam 2011-3-31
 */
public class NOverwriteSimplePersist<DATA> extends SimplePersist<DATA> {

	public NOverwriteSimplePersist(String configPath, String dataBaseName) {
		super(configPath, dataBaseName);
	}

	/**
	 * 新增记录时，不能覆盖原来的的值
	 */
	protected boolean persist(String aKey, DATA aData, PersistOperation operation) {
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
	            
			OperationStatus t = null;
			if (operation == PersistOperation.UPDATE_DATA) {
				t = myDatabase.put(null, theKey, theData);
				return true;
			} else {
				t = myDatabase.putNoOverwrite(null, theKey, theData);
				return t == OperationStatus.KEYEXIST ? false : true;
			}
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


}
