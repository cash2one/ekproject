package cn.elam.rmi.server;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import cn.elam.rmi.inter.RMIInter;

public class RMIServer {

	final static int port = 1111;
	
	/**
	 * 创建一个Registry对象
	 * 
	 * @return 返回一个Registry对象
	 */
	private static Registry createRegistry() {
		Registry registry = null;
		try {
			registry = LocateRegistry.getRegistry(port);
			registry.list();
			System.out.println("Register the exist server!");
		} catch (final Exception e) {
			try {
				registry = LocateRegistry.createRegistry(port);
				System.out.println("Register the exist server!port=" + port);
			} catch (final Exception ee) {
				ee.printStackTrace();
			}
		}
		return registry;
	}

	
	/**
	 * 
	 * 将服务对象注册到rmi服务器上
	 * @param servcieID     服务ID 唯一
	 * @param servcieClazz  服务实体类全路径
	 */
	public void publishRemoteServieInter(String servcieID, String servcieClazz) {
		Registry registry  = createRegistry();
		try {
			ClassLoader loader = this.getClass().getClassLoader();
			if(loader==null){
				System.out.println("RMIServic Service Registe fail! [ ClassLoader is NULL ]");
				return;
		     }
			Class clazz = loader.loadClass(servcieClazz);
			if (clazz != null
					&& checkRemoteInterface(clazz)) {
				Object serviceImp = clazz.newInstance();
				if (serviceImp != null) {
					registry.rebind(servcieID, (Remote) serviceImp);
					System.out
							.println("[ "
									+ servcieID
									+ " ] has been add to this RMI ServiceContext and start  ["+servcieID+"] !");
				}
			}else{
				System.out.println("can't create service instance ["+servcieID+"]:"+servcieClazz+", this reason is this service is not implement the java.rmi.Remote interface! ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * 判断该对象是否实现 Remote 接口
	 * @param clazz
	 * @return
	 */
	private boolean checkRemoteInterface(Class clazz){
		Class[] inters = clazz.getInterfaces();
		if(inters==null)
			return false;
		for(Class inter:inters){
			if(inter.getName().equals("java.rmi.Remote"))
				return true;
			else{
				if(checkRemoteInterface(inter))
					return true;
			}
		}
		inters = null;
		return false;
	}

	
	
	public static void main(String...srt){
		RMIServer server = new RMIServer();
		server.publishRemoteServieInter("Service", "cn.elam.rmi.server.interImpl.Service_1");
	}
	
}
