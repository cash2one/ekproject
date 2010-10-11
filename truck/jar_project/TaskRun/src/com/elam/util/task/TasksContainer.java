package com.elam.util.task;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.file.xml.XmlHandler;

public class TasksContainer<T extends Task> {

    List<T> tasks = null;
	 
    public TasksContainer(){
       BaseTask t ;    	
    }
	
    void initialize(){
    	Document doc = XmlHandler.loadXML("TaskConfig.xml");
    	if(doc==null)
    		return;
    	tasks = new ArrayList<T>();
		Element element = XmlHandler.getElement(doc, "global");
		List<Element> taskItems = XmlHandler.getElements(doc, "tasks/task");
		if(taskItems!=null){
			for(Element item:taskItems){
				
			}
		}
		
    }
    
    void isConfigChange(){
    	
    }
	
    
}
