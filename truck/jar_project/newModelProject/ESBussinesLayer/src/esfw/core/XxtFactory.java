package esfw.core;

import org.apache.log4j.xml.DOMConfigurator;


public class XxtFactory {
	
//    private static IPurviewInfo purview;
//
//	public static IPurviewInfo getPurview() {
//		return purview;
//	}
	
	
	/**
	 * 校讯通工厂
	 * @param purviewclass
	 * @param lo4j
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void init(String purviewclass,String lo4j) throws Exception{
		System.out.println("log4j已经配置...");
		DOMConfigurator.configure(lo4j);//初始化log4j
		Class classinfo=Class.forName(purviewclass);
//		purview=(IPurviewInfo)classinfo.newInstance();//生成权限对象
	
	}
}
