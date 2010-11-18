package com.qtone.datasync.dataprovider;

import java.util.Iterator;

public class MiscMsgIterator implements Iterator<String[]> {
	/**
	 * �ܹ�Ҫ���ɶ��ٲ�������
	 */
	private int totalTestTimes = 0;

	/**
	 * ��ǰ�Ѿ����ɶ��ٲ�������
	 */
	private int currTestTimes = 0;

	public MiscMsgIterator(int totalTestTimes) {
		this.totalTestTimes = totalTestTimes <= 0 ? 1 : totalTestTimes;
	}

	public boolean hasNext() {
		return currTestTimes < totalTestTimes;
	}

	public String[] next() {
		currTestTimes++;

		return generateMiscMsg();
	}

	/**
	 * �������Miscģ�ⱨ��
	 * 
	 * @return
	 */
	private String[] generateMiscMsg() {
		return new String[]{"123��������"+currTestTimes};
	}

	public void remove() {
		throw new UnsupportedOperationException("�Զ����Iterator����֧��remove����");
	}
}
