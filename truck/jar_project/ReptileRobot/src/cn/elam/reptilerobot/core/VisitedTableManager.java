package cn.elam.reptilerobot.core;

import java.util.HashMap;
import java.util.Map;

import cn.elam.reptilerobot.utils.MD5;

/**
 * 
 * 判断是否已经访问过
 * @author Ethan.Lam  
 * @createTime 2011-11-17
 *
 */
public class VisitedTableManager {


	public static VisitedTableManager instance = new VisitedTableManager();
	
	public Map<String,Long> VisitedTable = new HashMap<String,Long>();
	
	VisitedTableManager(){
		
	}
	
	
	public static VisitedTableManager getManager(){
		return instance;
	}
	
	
	/**
	 * 
	 * 方法：是否已经访问过
	 * 
	 * @param url
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-11-23
	 */
	public boolean hasVisited(String url){
		url = url.trim();
		url = MD5.generateMD5Str(url);
		if(VisitedTable.containsKey(url)){
			VisitedTable.put(url, VisitedTable.get(url)+1);
		    return true;
		}else{
			VisitedTable.put(url, (long) 1);
			return false;
		}
	}
	
	
}
