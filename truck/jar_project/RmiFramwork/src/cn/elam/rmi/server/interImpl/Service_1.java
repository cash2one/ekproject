package cn.elam.rmi.server.interImpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

import cn.elam.rmi.inter.RMIInter;

public class Service_1 extends UnicastRemoteObject implements RMIInter {

	/**
	 * 
	 * 
	*/
	private static final long serialVersionUID = 1L;

	
	int count = 1;
	
	public Service_1() throws RemoteException {
		super();
	}
	
	
	private String finishRequest() {
	    try{
	    	Thread.currentThread().sleep((long) (Math.random()*20000)); 
	    	count++;
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return "result :"+count+" ,date:"+new Date(System.currentTimeMillis()).toGMTString();
	}

	
	@Override
	public String setRequest(String hostName) {
		System.out.println(" 请求服务 [ Host:"+hostName+" date :"+ new Date(System.currentTimeMillis()).toGMTString()+"]");
		System.out.println(" 完成           [ Host: "+hostName+"] infos :  "+finishRequest());
		return null;
	}
	
    
}
