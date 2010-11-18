package com.qtone.datasync.local.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author 杨腾飞 2009-7-6
 * 
 */
public interface IRevertSyncService extends Remote{
	/**
	 * 同步一个地区的数据
	 * 
	 * @param areaAbb	地区缩写
	 */
	public void sync(String areaAbb)throws RemoteException ;
}
