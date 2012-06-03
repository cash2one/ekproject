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

	private List<FieldItem> items=null; //��Ӧ�������ֶ�

	private List<JoinItem> joinItems = null;  //�ӱ���Ϣ

    private FieldItem primaryKeyItem = null; //������Ϣ
	
	public List<FieldItem> getItems() {
		return items;
	}

	public Templater(Document _doc,String primaryKeyName){
		Element _root = XmlHandler.getElement(_doc, "items");
		this.items = NodeUtil.createItems(_root,_doc.getRootElement().attributeValue("tableAlias"));
		this.joinItems = NodeUtil.createJoinItems(_root);
		
		//�ҳ�����
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
		
		System.out.println("�ӱ���Ϣ-------");
		List<JoinItem> joins = NodeUtil.getJoinTables(t.joinItems);
		for(JoinItem join:joins){
			System.out.println(" "+join.getJoinTableKey()+"  "+join.getTable()+" "+join.getAlias());
    	}
		
		System.out.println("�ӱ�����-------");
		List<FieldItem> subFields = NodeUtil.getAllFieldsOfJoinTables(t.joinItems);
		for(FieldItem item :subFields ){
			System.out.println(item.getName()+" "+item.getSourceField()+"  "+item.getDescript()+" "+item.getTableAlias());
		}
	}
	
	
	/**
	 * ���������н��ֶ�
	 * @return
	 */
	public List<FieldItem> getJoinFields(){
		return NodeUtil.getAllFieldsOfJoinTables(joinItems);
	}
	
	
	/**
	 * ���������нӵı�
	 * @return
	 */
	public List<JoinItem> getJoinTables(){
		return NodeUtil.getJoinTables(joinItems);
	}
	
	
	public List<JoinItem> getJoinItems() {
		return joinItems;
	}

	/**
	 * ������Ϣ
	 * @return
	 */
	public FieldItem getPrimaryKeyItem() {
		return primaryKeyItem;
	}
 
	
	
}

