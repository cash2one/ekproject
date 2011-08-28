package cn.elam.util.task;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 任务接口
 * 
 * @author Ethan.Lam 2011-2-15
 * 
 */
public interface Task {

	/**
	 * 判断任务是否超时，如果发生超时，则需要重启该任务线程，此方法供轮询使用
	 * 
	 * @return
	 */
	public boolean isOverTime();

	/**
	 * 中断任务
	 */
	public void interrupt()throws Exception;

	/**
	 * 重启任务
	 */
	public void reStart()throws Exception;

	/**
	 * 获取任务对象（设置）属性
	 * 
	 * @return
	 */
	public TaskItem getTaskItem();

	
	/**
	 * 获取当前状态
	 * @return
	 */
	public TaskStatus getState();

	
    /**
     * 退出任务
     * @throws Exception
     */
	public void exit() throws Exception;
	
}
