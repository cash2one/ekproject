package cn.elam.berkeley.testcase;

import cn.elam.berkeley.wrapper.ISimplePersist;
import cn.elam.berkeley.wrapper.NOverwriteSimplePersist;
import cn.elam.berkeley.wrapper.SimplePersist;

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
		System.out.println(dao.getValue(aKey));
		System.out.println(dao.update(aKey, aData_new));
		System.out.println(dao.getValue(aKey));
		
		dao = new NOverwriteSimplePersist(config, dataBase);
		System.out.println(dao.persist(aKey, aData));
		System.out.println(dao.getValue(aKey));
		System.out.println(dao.update(aKey, aData_new));
		System.out.println(dao.getValue(aKey));
	}

}
