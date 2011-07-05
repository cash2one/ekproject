package qtone.generator;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

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
		this.items = NodeUtil.createItems(_root,_root.attributeValue("tableAlias"));
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

	/**
	 * ������Ϣ
	 * @return
	 */
	public FieldItem getPrimaryKeyItem() {
		return primaryKeyItem;
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
     * 
     * �������б����ӵ��ֶ�������Ϣ��
     * @param joinItems
     * @return
     */
    public static List<FieldItem> getAllFieldsOfJoinTables(List <JoinItem> joinItems){
    	List<FieldItem> temp = new ArrayList<FieldItem>();
//    	debug("dsfsdf");
    	for(JoinItem join:joinItems){
            if(join.getItems()!=null){
                for(FieldItem de:join.getItems()){
            	    temp.add(de);
                }
                if(join.getJoinItems()!=null){
                    for(FieldItem de:getAllFieldsOfJoinTables(join.getJoinItems())){
                 	      temp.add(de);
                    }
                } 
            }    		
    	}
    	return temp;
    }
    
    
    
    
    /**
     * �������б����Ӷ���
     * @param joinItems
     * @return
     */
    public static List<JoinItem> getJoinTables(List <JoinItem> joinItems){
    	List<JoinItem> temp = new ArrayList<JoinItem>();
//    	debug("dsfsdf");
    	for(JoinItem join:joinItems){
    		debug(" "+join.getJoinTableKey()+"  "+join.getTable());
    		temp.add(join);
            if(join.getJoinItems()!=null){
              for(JoinItem de:getJoinTables(join.getJoinItems())){
            	  temp.add(de);
              }
            }    		
    	}
    	return temp;
    }
    
	
	/**
	 * ����Item �ڵ���Ϣ
	 * @return
	 */
	public static List<FieldItem> createItems(Element _root,String tableAlias){
         List<FieldItem> items = new ArrayList<FieldItem>();
         List<Element> itemSet = _root.selectNodes(itemName);
         debug("createItems->getPath():"+_root.getPath()+" :size():"+itemSet.size());
         FieldItem field = null;
         if(itemSet!=null){
        	 for(Element node:itemSet){
        		 field = new FieldItem();
        		 field.setTableAlias(tableAlias);
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
	 * ����joinItem �ڵ���Ϣ
	 * @param _root
	 * @return
	 */
	public static List<JoinItem> createJoinItems(Element _root){
		List<JoinItem> joinItems = new ArrayList<JoinItem>();
        List<Element> joinSet = _root.selectNodes(joinName);
        debug("createJoinItems->getPath():"+_root.getPath()+" :size():"+joinSet.size());
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
	
	
	private static void debug(String arg){
//		System.out.println(arg);
	}
	

}