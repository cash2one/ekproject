package com.qtone.datasync.util;

/**
 * @author 杨腾飞	2009-10-30 
 * 用户记录的状态
 */
public enum RecordStatus {
	NOTSYNC(2),//未同步
	SYNC_SUCC(1),//同步成功
	SYNC_FAIL(3),//同步失败
	;
	
	private int status = 0;
	
	private RecordStatus(int status){
		this.status = status;
	}
	
	public int value(){
		return this.status;
	}
}
