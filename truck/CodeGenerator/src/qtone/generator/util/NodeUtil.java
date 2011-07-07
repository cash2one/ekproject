package qtone.generator.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import qtone.generator.FieldItem;
import qtone.generator.JoinItem;

/**
 * 
 * @author Ethan.Lam  2011-7-4
 *
 */
public class NodeUtil{
	
	private static String itemName="item";
	
	private static String joinName="joinOneItem";
	
	public static Map<String,String> dataTypeSet = new HashMap<String,String>();
	
	static{
			//java 基本类型
			dataTypeSet.put("long", "long");
	        dataTypeSet.put("int","int");
	        dataTypeSet.put("date","Date");
	        dataTypeSet.put("datetime","Date");
	        dataTypeSet.put("string","String");
	       
	        //数据库 映射到 java 基本类型
			dataTypeSet.put("decimal", "long");
	        dataTypeSet.put("VARCHAR","String");
	        dataTypeSet.put("VARCHAR2","String");
	        dataTypeSet.put("date","Date");
	        dataTypeSet.put("datetime","Date");
	}
	
	/**
	 * 数据库类型 转义
	 * @param cfgType
	 * @return
	 */
	public static String toJavaDataType(String cfgType){
		if(cfgType!=null&&dataTypeSet.containsKey(cfgType.toLowerCase())){
			return dataTypeSet.get(cfgType.toLowerCase());
		}else
			return "String";
	}
	
	
    public static void pfields(List<FieldItem> items){
	     for(FieldItem item:items){
	    	  System.out.println(item.getName()+" "+item.getType());
	     }
	}	

    
    /**
     * 
     * 返回所有表连接的字段属性信息。
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
     * 返回所有表连接对象
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
	 * 生成Item 节点信息
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
        		 field.setIsReadonly((node.attributeValue("isReadonly")!=null&&"true".equals(node.attributeValue("isReadonly").toLowerCase()))?true:false);
        		 field.setName(node.attributeValue("name"));
        		 field.setSourceField(node.attributeValue("sourceField"));
        		 field.setSourceType(node.attributeValue("sourceType"));
        		 field.setType(toJavaDataType(node.attributeValue("type")));
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
        debug("createJoinItems->getPath():"+_root.getPath()+" :size():"+joinSet.size());
        JoinItem join = null;
        if(joinSet!=null){
       	 for(Element node:joinSet){
       		join = new JoinItem(node);
       		join.setJoinTableKey(node.attributeValue("joinTableKey"));
       		join.setJoinType(node.attributeValue("joinType").toLowerCase());
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