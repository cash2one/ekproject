package cn.elam.berkeley.testcase;

import cn.elam.berkeley.sample.MyData;
import cn.elam.berkeley.wrapper.IObjectPersist;
import cn.elam.berkeley.wrapper.ISimplePersist;
import cn.elam.berkeley.wrapper.impl.ObjectPersist;
import cn.elam.berkeley.wrapper.impl.SimplePersist;

public class SimpleCase {

	static String config = "export/dbEnv";
	static String dataBase = "sampleDatabase";

	public static void main(String... args) {
		String aKey = "tkey_1";
		String aData = "OldValue";
		String aData_new = "NewValue";
		Integer number = 10;
		
		ISimplePersist dao = new SimplePersist(config, dataBase);
		System.out.println(dao.persist(aKey, aData));
		System.out.println(aData.equals(dao.getValue(aKey)));
		System.out.println(dao.update(aKey, aData_new));
		System.out.println(aData_new.equals(dao.getValue(aKey)));
        System.out.println("测试通过");
//	
        
        IObjectPersist daoObj = new ObjectPersist(config, dataBase);
        MyData data  = new MyData();
        data.setDescription("test");
        data.setDouble(10000);
        data.setLong(10000);
        aKey = aKey+"_A";
		System.out.println(daoObj.persist(aKey, data));
		
		MyData old = (MyData) daoObj.getValue(aKey, MyData.class); 
        System.out.println("测试通过");
        
	}


}
