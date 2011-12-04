package cn.elam.reptilerobot.core;

import cn.elam.reptilerobot.base.Node;
import cn.elam.reptilerobot.utils.MD5;

/**
 * 
 * �ж��Ƿ��Ѿ����ʹ�
 * 
 * @author Ethan.Lam
 * @createTime 2011-11-17
 * 
 */
public class VisitedTableManager {

	public static VisitedTableManager instance = new VisitedTableManager();

	SimpleBloomFilter filter = new SimpleBloomFilter();

	VisitedTableManager() {

	}

	public static VisitedTableManager getManager() {
		return instance;
	}

	
	/**
	 * 
	 * �������Ƿ��Ѿ����ʹ�
	 * 
	 * @param url
	 * @return
	 * 
	 *         Add By Ethan Lam At 2011-11-23
	 */
	public boolean hasVisited(Node node) {
		if (!filter.contains(node)) {
			filter.add(node);
			return false;
		}
		return true;
	}

}
