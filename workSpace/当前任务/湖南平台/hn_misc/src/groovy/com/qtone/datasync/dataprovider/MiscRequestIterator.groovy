package com.qtone.datasync.dataprovider;

import java.util.Iterator;
import com.qtone.datasync.xxt.server.bean.MiscRequestBean
import com.qtone.datasync.misc.*
import org.testng.annotations.DataProvider;

/**
 * ��������Դ�ṩ���ߣ����е������ṩ���߶�Ӧ�ü̳и��࣬��ʵ��provider�����Է����ʵ���
 * ֵ
 */
public class AbstractDataProvider implements Iterator<Object> {
	//['msgType':'','transactionID':'','phoneFee':'phoneUse':'','action':,'actionReason':,'spId':,'spServiceId':]
	def dataSet = [['msgType':'SyncOrderRelationReq','transactionID':'1','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Qtone','spServiceId':'528400'],
	['msgType':'SyncOrderRelationReq','transactionID':'1','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Qtone','spServiceId':'528400'],
	['msgType':'SyncOrderRelationReq','transactionID':'1','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Qtone','spServiceId':'528400'],
	['msgType':'SyncOrderRelationReq','transactionID':'1','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Qtone','spServiceId':'528400'],
	['msgType':'SyncOrderRelationReq','transactionID':'1','phoneFee':'13800138000','phoneUse':'13800138000','action':1,'actionReason':1,'spId':'Qtone','spServiceId':'528400']]
	
	//���ݳ���
	def len = 0
	def currIdx = 0
	
	public MisRequestIterator(Object[] dataSet){
		this.dataSet = dataSet
		
		len = dataSet.size()
	}
	
	public boolean hasNext() {
		return currIdx < len;
	}
	
	public Object next() {
		def oldIdx = currIdx
		currIdx++
		
		def params = dataSet.get(oldIdx)	
		
		return provide(params)
	}
	
	/**
	 * �������Map����ֵ��֯���ʵ��ĸ�ʽ������
	 */
	public abstract Object provide(Map params);
	
	public void remove() {
		// TODO Auto-generated method stub
	}
}
