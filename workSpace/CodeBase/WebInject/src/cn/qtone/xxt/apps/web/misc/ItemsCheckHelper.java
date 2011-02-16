package cn.qtone.xxt.apps.web.misc;

import java.util.HashMap;
import java.util.Map;

public class ItemsCheckHelper {

	public static ItemsCheckHelper checker = new ItemsCheckHelper();
	Map<String, String> itemsSet = new HashMap<String, String>();

	private ItemsCheckHelper() {

	}

	public static ItemsCheckHelper getHelper() {
		return checker;
	}

	/**
	 * 
	 * �Ƿ����µ�����
	 * @param item
	 * @return
	 * 
	 */
	public boolean isNewItem(ComplaintItem item){
        if(itemsSet.containsKey(item.getId())){
        	if(item.getCreateTime().equals(itemsSet.get(item.getId())))
		        return false;
            else
            	itemsSet.put(item.getId(), item.getCreateTime()); //����ʱ��
        }else
        	itemsSet.put(item.getId(), item.getCreateTime()); //����ʱ���ʶ
        return true;
	}
	
}
