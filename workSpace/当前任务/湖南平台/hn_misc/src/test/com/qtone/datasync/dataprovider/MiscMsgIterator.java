package com.qtone.datasync.dataprovider;

import java.util.Iterator;

public class MiscMsgIterator implements Iterator<String[]> {
	/**
	 * 总共要生成多少测试数据
	 */
	private int totalTestTimes = 0;

	/**
	 * 当前已经生成多少测试数据
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
	 * 随机生成Misc模拟报文
	 * 
	 * @return
	 */
	private String[] generateMiscMsg() {
		return new String[]{"123测试数据"+currTestTimes};
	}

	public void remove() {
		throw new UnsupportedOperationException("自定义的Iterator，不支持remove操作");
	}
}
