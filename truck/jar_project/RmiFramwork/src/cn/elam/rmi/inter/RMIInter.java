package cn.elam.rmi.inter;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInter extends Remote {
	
     public String setRequest(String hostName)throws RemoteException;
	
	 
}
