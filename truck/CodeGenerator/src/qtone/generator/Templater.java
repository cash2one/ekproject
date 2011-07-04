package qtone.generator;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.file.xml.XmlHandler;

public class Templater {

	private List<FieldItem> items=null;

	private List<JoinItem> joinItems = null;

	public Templater(Document _doc){
		Element _root = XmlHandler.getElement(_doc, "items");
		this.items = NodeUtil.createItems(_root);
		this.joinItems = NodeUtil.createJoinItems(_root);
	}
	
	public  static void main(String...arg){
		Document doc = XmlHandler.loadXML("src/qtone/generator/demo.xml");
		
		Templater t = new Templater(doc);
	    t.p();
	    
	}
	
    public void p(){
    	System.out.println("主表属性-------");
    	NodeUtil.pfields(items);
    	System.out.println("从表属性-------");
    	for(JoinItem join:joinItems){
    		 join.pfield();
    	}
    	
    }
    
}

/**
 * 
 * @author Ethan.Lam  2011-7-4
 *
 */
class NodeUtil{
	
	private static String itemName="item";
	
	private static String joinName="joinOneItem";
	
	
    public static void pfields(List<FieldItem> items){
	     for(FieldItem item:items){
	    	  System.out.println(item.getName()+" "+item.getType());
	     }
	}	

	
	/**
	 * 生成Item 节点信息
	 * @return
	 */
	public static List<FieldItem> createItems(Element _root){
         List<FieldItem> items = new ArrayList<FieldItem>();
         List<Element> itemSet = _root.selectNodes(itemName);
         FieldItem field = null;
         if(itemSet!=null){
        	 for(Element node:itemSet){
        		 field = new FieldItem();
        		 field.setDescript(node.attributeValue("descript"));
        		 field.setIsReadonly((node.attributeValue("isReadonly")!=null&&"true".equals(node.attributeValue("isReadonly")))?true:false);
        		 field.setName(node.attributeValue("name"));
        		 field.setSourceField(node.attributeValue("sourceField"));
        		 field.setSourceType(node.attributeValue("sourceType"));
        		 field.setType(node.attributeValue("type"));
        		 items.add(field);
        	 }
         }
		 return items;
	}
	
	
	/**
	 * 生成joinItem 节点信息
	 * @param _root
	 * @return
	 */
	public static List<JoinItem> createJoinItems(Element _root){
		List<JoinItem> joinItems = new ArrayList<JoinItem>();
        List<Element> joinSet = _root.selectNodes(joinName);
        JoinItem join = null;
        if(joinSet!=null){
       	 for(Element node:joinSet){
       		join = new JoinItem(node);
       		join.setJoinTableKey(node.attributeValue("joinTableKey"));
       		join.setJoinType(node.attributeValue("joinType"));
       		join.setPrimaryTableKey(node.attributeValue("primaryTableKey"));
       		join.setTable(node.attributeValue("table"));
       		joinItems.add(join);
       	 }
        }
		 return joinItems;
	}
	

}