package com.qtone.datasync.util;

/**
 * @author ���ڷ�	2009-10-30 
 * �û���¼��״̬
 */
public enum RecordStatus {
	NOTSYNC(2),//δͬ��
	SYNC_SUCC(1),//ͬ���ɹ�
	SYNC_FAIL(3),//ͬ��ʧ��
	;
	
	private int status = 0;
	
	private RecordStatus(int status){
		this.status = status;
	}
	
	public int value(){
		return this.status;
	}
}
