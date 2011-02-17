package cn.qtone.xxt.apps.web.misc;

import org.apache.log4j.xml.DOMConfigurator;

import com.elam.util.task.TasksContainer;

public class Main {
	
	public static void main(String... args) {
		DOMConfigurator.configure("configs/log4j.xml");
		//启动对应的任务
		TasksContainer container = new TasksContainer();
	}
	
	
}
