package cn.qtone.xxt.csop.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.qtone.xxt.csop.dao.comom.BaseDao;
import cn.qtone.xxt.csop.dao.comom.DBConnector;
import cn.qtone.xxt.csop.util.Checker;

/**
 * ������Ϣ
 * 
 * @author linhansheng
 * 
 */
public class AreaUtil {

	private static AreaUtil util = new AreaUtil();

	// ���һ�������� ҵ������,
	private static Map<String, String> areasMap;

	// //���ڻ���ҵ��ĵ���
	// private static Map<String,Boolean> baseTransMap;

	// �����ײ͵ĵ���
	private static Map<String, Boolean> isPackageTransMap;

	private AreaUtil() {
	   try {
			initAreasMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static AreaUtil getService() {
		return util;
	}

	/**
	 * ��ʼ������������Ϣ
	 * 
	 * @throws Exception
	 */
	synchronized void initAreasMap() throws Exception {
		areasMap = new HashMap<String, String>();
		isPackageTransMap = new HashMap<String, Boolean>();
		BaseDao dao = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnection("xxt");
			dao = new BaseDao(conn);
			rs = dao.query(" select id,abb,is_package from area ");
			while (rs != null && rs.next()) {
				areasMap.put(rs.getString("abb") + "_abb", rs.getString("id"));
				areasMap.put(rs.getString("id") + "_id", rs.getString("abb"));
				if (!Checker.isNull(rs.getString("is_package"))
						&& !"0".equals(rs.getString("is_package").trim())) {
					isPackageTransMap.put(rs.getString("abb"), Boolean.TRUE);
					isPackageTransMap.put(rs.getString("id"), Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			rs.close();
			rs = null;
			dao.close();
			if (!conn.isClosed())
				conn.close();
			conn = null;
			dao = null;
		}
	}

	public String getAreaAbbById(int areaId) {
		return returnAreaInfo(true, String.valueOf(areaId));
	}

	public int getAreaIdByAbb(String abb) {
		String id = returnAreaInfo(false, abb);
		return id != null ? Integer.parseInt(id) : -1;
	}

	String returnAreaInfo(boolean isId, String key) {
		if (areasMap == null)
			try {
				initAreasMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
		;
		key += (isId ? "_id" : "_abb");
		if (areasMap.containsKey(key))
			return areasMap.get(key);
		else
			return null;
	}

	/**
	 * �жϸõ����Ƿ����ײ͵���
	 * 
	 * @param abb
	 *            �����д
	 * @return
	 */
	public boolean isPackageArea(String abb) {
		if (isPackageTransMap == null)
			try {
				initAreasMap();
			} catch (Exception e) {
				e.printStackTrace();
			}
		if (isPackageTransMap.containsKey(abb))
			return true;
		else
			return false;
	}

	/**
	 * �жϸõ����Ƿ����ײ͵���
	 * 
	 * @param areaId
	 *            ����ID
	 * @return
	 */
	public boolean isPackageArea(int areaId) {
		return isPackageArea(String.valueOf(areaId));
	}

	/**
	 * �������е����ļ��
	 * @return
	 */
	public List<String> listAreaAbbs() {
		List<String> areaAbbs = new ArrayList<String>();
		for (String key : areasMap.keySet()) {
			if (key.indexOf("_abb") > 0)
				areaAbbs.add(key.substring(0, key.indexOf("_abb")));
		}
		return areaAbbs;
	}

	public static void main(String... s) {
		for (String area : AreaUtil.getService().listAreaAbbs()) {
			System.out.println(area + "�Ƿ����ײ͵�����"+ AreaUtil.getService().isPackageArea(area));
		}
	}

}
