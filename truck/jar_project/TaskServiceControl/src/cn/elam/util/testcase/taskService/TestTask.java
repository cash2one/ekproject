package cn.elam.util.testcase.taskService;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.elam.util.persistence.mybatis.MyBatisSeesionFactory;
import cn.elam.util.task.BaseTask;
import cn.elam.util.task.TaskItem;
import cn.elam.util.task.TaskLog;

public class TestTask extends BaseTask {

	public TestTask(TaskItem taskItem) {
		super(taskItem);
	}

	@Override
	protected void initialize() {
		TaskLog.info(getTaskItem().getName(), " initialize....");
	}

	@Override
	protected void release() {
		// TODO Auto-generated method stub
		TaskLog.info(getTaskItem().getName(), " release....");
	}

	@Override
	protected void task() {
		// TODO Auto-generated method stub
		try {
			SqlSession session = MyBatisSeesionFactory.getSeesionFactory()
					.createSession();
			TestMapper test = session.getMapper(TestMapper.class);
			List<HashMap> areas = test.queryAreas();
			for (HashMap columns : areas) {
                System.out.println(columns.get("NAME"));
			}
			session.close();
			int st = (int) (Math.random() * 10);
			if (st > 0)
				Thread.sleep((st * 3) * 1000);
			TaskLog.info(getTaskItem().getName(), "故意耗时" + (st * 3) + "秒。");
		} catch (Exception e) {
			TaskLog.error(getTaskItem().getName(), "发生异常", e);
		}
		TaskLog.info(getTaskItem().getName(), " execute TestTask....");
	}

}
