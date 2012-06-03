package qtone.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;

import qtone.generator.util.NodeUtil;

import cn.elam.util.file.xml.XmlHandler;

public class Templater {

	private List<FieldItem> items=null; //对应的主表字段

	private List<JoinItem> joinItems = null;  //从表信息

    private FieldItem primaryKeyItem = null; //主键信息
	
	public List<FieldItem> getItems() {
		return items;
	}

	public Templater(Document _doc,String primaryKeyName){
		Element _root = XmlHandler.getElement(_doc, "items");
		this.items = NodeUtil.createItems(_root,_doc.getRootElement().attributeValue("tableAlias"));
		this.joinItems = NodeUtil.createJoinItems(_root);
		
		//找出主键
		for(FieldItem field :this.items){
		    if(field.getName().toLowerCase().equals(primaryKeyName)){
		    	this.primaryKeyItem = field;
		    	break;
		    }
		}
		
	}
	
	public static void main(String...arg){
		Document doc = XmlHandler.loadXML("src/qtone/generator/demo.xml");
		Templater t = new Templater(doc,"id");
//	    t.printAttuributes();
		
		System.out.println("从表信息-------");
		List<JoinItem> joins = NodeUtil.getJoinTables(t.joinItems);
		for(JoinItem join:joins){
			System.out.println(" "+join.getJoinTableKey()+"  "+join.getTable()+" "+join.getAlias());
    	}
		
		System.out.println("从表属性-------");
		List<FieldItem> subFields = NodeUtil.getAllFieldsOfJoinTables(t.joinItems);
		for(FieldItem item :subFields ){
			System.out.println(item.getName()+" "+item.getSourceField()+"  "+item.getDescript()+" "+item.getTableAlias());
		}
	}
	
	
	/**
	 * 返回连所有接字段
	 * @return
	 */
	public List<FieldItem> getJoinFields(){
		return NodeUtil.getAllFieldsOfJoinTables(joinItems);
	}
	
	
	/**
	 * 返回连所有接的表
	 * @return
	 */
	public List<JoinItem> getJoinTables(){
		return NodeUtil.getJoinTables(joinItems);
	}
	
	
	public List<JoinItem> getJoinItems() {
		return joinItems;
	}

	/**
	 * 主键信息
	 * @return
	 */
	public FieldItem getPrimaryKeyItem() {
		return primaryKeyItem;
	}
 
	
	
}

