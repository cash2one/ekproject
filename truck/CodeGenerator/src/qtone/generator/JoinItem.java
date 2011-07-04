package qtone.generator;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class JoinItem {

	String table="${areaAbb}_XJ_STU_CLASS"; 
	String joinType="LEFT";
	String primaryTableKey="STU_SEQUENCE";
	String joinTableKey="STU_SEQUENCE";
	
    private List<FieldItem> items = null;
	
    private List<JoinItem> joinItems = null;
	
    
	public JoinItem(Element _root){
	    this.items = NodeUtil.createItems(_root);	
		this.joinItems = NodeUtil.createJoinItems(_root);
	}
	
	
	public String getTable() {
		return table;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
	
	public String getJoinType() {
		return joinType;
	}
	
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	
	public String getPrimaryTableKey() {
		return primaryTableKey;
	}
	
	public void setPrimaryTableKey(String primaryTableKey) {
		this.primaryTableKey = primaryTableKey;
	}
	
	public String getJoinTableKey() {
		return joinTableKey;
	}
	
	public void setJoinTableKey(String joinTableKey) {
		this.joinTableKey = joinTableKey;
	}


	public List<FieldItem> getItems() {
		return items;
	}


	public List<JoinItem> getJoinItems() {
		return joinItems;
	}
	
	
	public void pfield(){
		 NodeUtil.pfields(items);
		 for(JoinItem join:joinItems){
    		 NodeUtil.pfields(join.getItems());
    	}
	}
	
	
}
