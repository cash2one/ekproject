package cn.elam.rmi.client;

import java.rmi.Naming;
import java.rmi.RemoteException;

import cn.elam.rmi.adapter.RemoteConfig;
import cn.elam.rmi.adapter.RemoteInvoker;
import cn.elam.rmi.inter.RMIInter;

public class ClientExc {
	
	public static void main(String...art) throws RemoteException{
		for(int num = 1;num<20;num++){
	       new Thread(new ExcThread(String.valueOf(num))).start();		
		}
	}
	
}


class ExcThread implements Runnable{

	String name = "Thread_";
	
	public ExcThread(String name){
		this.name += name;
	}
	
	
    void invoke(String invokeName) throws RemoteException{
    	RemoteInvoker invoker = new RemoteInvoker(new RemoteConfig("localhost",1111));
		Object obj  = invoker.getRemoteObject("Service");
		if(obj==null)
			System.out.println("找不到对应的远程服务接口！");
		else
			((RMIInter)obj).setRequest(invokeName);
    }	
	
	@Override
	public void run() {
		try {
			invoke(name);
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
	}
	
}