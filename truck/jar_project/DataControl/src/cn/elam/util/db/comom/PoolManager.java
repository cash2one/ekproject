package cn.elam.util.db.comom;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.struts.util.GenericDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.file.xml.XmlHandler;

/**
 * 数据连接池
 * 
 * @author ethanlam
 * 
 */
class PoolManager {

	int connectionPoolSize = 20;
	int minConnectionPoolSize = 10;

	static Properties conf = null;

	static Map<String, GenericDataSource> DB_POOL = new HashMap<String, GenericDataSource>();
	private static PoolManager poolInstance = new PoolManager();

	static {
		loadDBConfigXml();
	}

	private PoolManager() {

	}

	public static PoolManager getPoolManager() {
		if (poolInstance == null)
			poolInstance = new PoolManager();
		return poolInstance;
	}

	/**
	 * 获取对应的数据源
	 * 
	 * @param poolName
	 * @return
	 * @throws DaoException
	 */
	public GenericDataSource getDBPool(String poolName) throws DaoException {
		if (!DB_POOL.containsKey(poolName)) {
			throw new DaoException("找不到对应的数据连接池对象。");
		}
		return DB_POOL.get(poolName);

	}

	static void loadDBConfigXml() {
		Document doc = XmlHandler.loadXML("PoolConfig.xml");
		Element element = XmlHandler.getElement(doc, "enable");
		String poolNames = element.getTextTrim();
		Element poolObject = null;
		DB_POOL.clear();
		GenericDataSource db ;
		for (String pool : poolNames.split(",")) {
			poolObject = XmlHandler.getElement(doc, "pools/" + pool);
			if (poolObject == null) {
				System.out.println("找不到 " + pool + "这个配置项！");
				continue;
			}
			db = new GenericDataSource();
			try {
				db.setAutoCommit(true);
				db.setDescription("");
				db.setDriverClass(poolObject.attributeValue("driver"));
				int max = Checker.isNull(poolObject
						.attributeValue("connectionPoolSize")) ? 20 : Trans
						.StringToInt(poolObject
								.attributeValue("connectionPoolSize"));
				int min = Checker.isNull(poolObject
						.attributeValue("minConnectionPoolSize")) ? 20 : Trans
						.StringToInt(poolObject
								.attributeValue("minConnectionPoolSize"));
				db.setMaxCount(max);
				db.setMinCount(min);
				db.setUrl(poolObject.selectSingleNode("url").getText().trim());
				db.setPassword(poolObject.selectSingleNode("pwd").getText()
						.trim());
				db.setUser(poolObject.selectSingleNode("user").getText().trim());
			} catch (Exception e) {
				e.printStackTrace();
			}
			DB_POOL.put(pool, db);
		}
	}

	public static void main(String... srt) {
		loadDBConfigXml();
	}
}
