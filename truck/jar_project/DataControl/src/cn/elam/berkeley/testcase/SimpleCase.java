package cn.elam.berkeley.testcase;

import cn.elam.berkeley.wrapper.ISimplePersist;
import cn.elam.berkeley.wrapper.NOverwriteSimplePersist;
import cn.elam.berkeley.wrapper.SimplePersist;

public class SimpleCase {

	static String config = "export/dbEnv";
	static String dataBase = "sampleDatabase";

	public static void main(String... args) {
		String aKey = "test_number";
		String aData = "value_one";
		Integer number = 10;
		
		ISimplePersist dao = new NOverwriteSimplePersist<String>(config, dataBase);
		System.out.println(dao.query(aKey));
		
//		System.out.println(dao.persist(aKey, number));
//		System.out.println(dao.query(aKey));
//		System.out.println(dao.update(aKey, number+1000));
//		System.out.println(dao.query(aKey));
		
//		dao = new SimplePersist<String>(config, dataBase);
//		System.out.println(dao.persist(aKey, aData));
//		System.out.println(dao.query(aKey));
//		System.out.println(dao.update(aKey, "value_two"));
//		System.out.println(dao.query(aKey));
	}

}
