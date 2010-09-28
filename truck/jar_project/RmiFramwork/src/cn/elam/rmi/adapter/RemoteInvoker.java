package cn.elam.rmi.adapter;

import java.rmi.Naming;
import java.rmi.Remote;

public class RemoteInvoker {

	RemoteConfig config;
	
	public RemoteInvoker(RemoteConfig config){
		 this.config = config;
	}
    
	
	/**
	 * 返回远程对象
	 * @param serviceID
	 * @return
	 */
	public Remote getRemoteObject(String serviceID) {
		try {
			if(this.config==null)
				return null;
			return (Remote) Naming.lookup(config.toRemotUrl()+serviceID);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
