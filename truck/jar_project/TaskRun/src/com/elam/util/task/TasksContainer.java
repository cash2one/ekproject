package com.elam.util.task;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.file.xml.XmlHandler;

public class TasksContainer {

    List<BaseTask> tasks = null;
	 
    public TasksContainer(){
    	
    	
    }
	
    void initialize(){
    	Document doc = XmlHandler.loadXML("TaskConfig.xml");
    	if(doc==null)
    		return;
    	
    	tasks = new ArrayList<BaseTask>();
		Element element = XmlHandler.getElement(doc, "global");
		List<Element> taskItems = XmlHandler.getElements(doc, "tasks/task");
		if(taskItems!=null){
			for(Element item:taskItems){
				
			}
		}
		
    }
	
    
}
