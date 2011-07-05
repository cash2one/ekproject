package qtone.generator;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

public class JoinItem {

	String table="${areaAbb}_XJ_STU_CLASS"; 
	String joinType="LEFT";
	String primaryTableKey="STU_SEQUENCE";
	String joinTableKey="STU_SEQUENCE";
	String alias = ""; 
	
    private List<FieldItem> items = null;
	
    private List<JoinItem> joinItems = null;
	
    
	public JoinItem(Element _root){
	    this.items = NodeUtil.createItems(_root,_root.attributeValue("tableAlias"));	
	    this.setAlias(_root.attributeValue("tableAlias"));
		this.joinItems = NodeUtil.createJoinItems(_root);
	}
	
	
	public String getAlias() {
		return alias;
	}


	public void setAlias(String alias) {
		this.alias = alias;
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
 
	
}
