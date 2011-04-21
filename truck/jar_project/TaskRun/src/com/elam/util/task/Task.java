package com.elam.util.task;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * ����ӿ�
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public interface Task {

	/**
	 * �ж������Ƿ�ʱ�����������ʱ������Ҫ�����������̣߳��˷�������ѯʹ��
	 * 
	 * @return
	 */
	public boolean isOverTime();

	/**
	 * �ж�����
	 */
	public void interrupt();

	/**
	 * ��������
	 */
	public void reStart();

	/**
	 * ��ȡ����������ã�����
	 * 
	 * @return
	 */
	public TaskItem getTaskItem();

	
	/**
	 * ��ȡ��ǰ״̬
	 * @return
	 */
	public TaskStatus getState();

	

	
}
