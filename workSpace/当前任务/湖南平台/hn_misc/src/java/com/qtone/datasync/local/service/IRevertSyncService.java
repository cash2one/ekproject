package com.qtone.datasync.local.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author ���ڷ� 2009-7-6
 * 
 */
public interface IRevertSyncService extends Remote{
	/**
	 * ͬ��һ������������
	 * 
	 * @param areaAbb	������д
	 */
	public void sync(String areaAbb)throws RemoteException ;
}
