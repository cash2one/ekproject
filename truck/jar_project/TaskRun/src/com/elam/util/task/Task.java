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
	public void interrupt()throws Exception;

	/**
	 * ��������
	 */
	public void reStart()throws Exception;

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

	
    /**
     * �˳�����
     * @throws Exception
     */
	public void exit() throws Exception;
	
}
