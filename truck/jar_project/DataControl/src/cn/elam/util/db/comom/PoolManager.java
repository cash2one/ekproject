package cn.elam.util.db.comom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InitialContext;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.common.Checker;
import cn.elam.util.common.Trans;
import cn.elam.util.db.DBDOMConfigurator;
import cn.elam.util.file.xml.XmlHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * 数据连接池  管理器
 * 
 * @author ethanlam
 * 
 */
class PoolManager {

	int connectionPoolSize = 20;
	int minConnectionPoolSize = 10;

	static Properties conf = null;

	static Map<String, ComboPooledDataSource> DB_POOL = new HashMap<String, ComboPooledDataSource>();
	
	private static PoolManager poolInstance = new PoolManager();

	static {
		loadDBConfigXml();
	}

	private PoolManager() {

	}

	/**
	 * 
	 * 方法：返回数据池实例
	 * 
	 * @return
	 *  
	 *    Add By Ethan Lam  At 2011-9-25
	 */
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
	public ComboPooledDataSource getDBPool(String poolName) throws DaoException {
		if (!DB_POOL.containsKey(poolName)) {
			throw new DaoException("找不到对应的数据连接池对象。");
		}
		return DB_POOL.get(poolName);

	}

	/**
	 * 
	 * 方法：加载PoolConfig.xml 文件
	 * 
	 *  
	 *    Add By Ethan Lam  At 2011-9-25
	 */
	static void loadDBConfigXml() {
		
		Document doc = XmlHandler.loadXML(DBDOMConfigurator.configureXml);
		Element element = XmlHandler.getElement(doc, "enable");
		String poolNames = element.getTextTrim();
		Element poolObject = null;
		
		DB_POOL.clear();
		
		ComboPooledDataSource db ;
		
		for (String pool : poolNames.split(",")) {
			
			poolObject = XmlHandler.getElement(doc, "pools/" + pool);
			if (poolObject == null) {
				System.out.println("找不到 " + pool + "这个配置项！");
				continue;
			}
			System.out.println("【PoolConfig-"+pool+"】-正在初始化... ");
			
			db = new ComboPooledDataSource();
			try {
				
				db.setDataSourceName("java:comp/env/jdbc/"+pool);
				db.setDriverClass(poolObject.attributeValue("driver"));
				db.setJdbcUrl(poolObject.selectSingleNode("url").getText().trim());
				db.setPassword(poolObject.selectSingleNode("pwd").getText().trim());
				db.setUser(poolObject.selectSingleNode("user").getText().trim());
				
				int max = Checker.isNull(poolObject
						.attributeValue("connectionPoolSize")) ? 20 : Trans
						.StringToInt(poolObject
								.attributeValue("connectionPoolSize"));
				
				int min = Checker.isNull(poolObject
						.attributeValue("minConnectionPoolSize")) ? 10 : Trans
						.StringToInt(poolObject
								.attributeValue("minConnectionPoolSize"));
				
				int checkoutTimeout = Checker.isNull(poolObject
						.attributeValue("CheckoutTimeout")) ? 30000 : Trans
								.StringToInt(poolObject
										.attributeValue("CheckoutTimeout"));
				
				int acquireRetryAttempts = Checker.isNull(poolObject
						.attributeValue("AcquireRetryAttempts")) ? 2 : Trans
								.StringToInt(poolObject
										.attributeValue("AcquireRetryAttempts"));
				
				db.setMinPoolSize(min);
				db.setMaxPoolSize(max);
				db.setInitialPoolSize(min);
				
				db.setCheckoutTimeout(checkoutTimeout);//当连接池用完时客户端调用getConnection()后等待获取新连接的时间
				db.setIdleConnectionTestPeriod(60);//隔多少秒检查所有连接池中的空闲连接
				db.setAcquireRetryAttempts(acquireRetryAttempts);//定义在从数据库获取新连接失败后重复尝试获取的次数
				db.setAcquireRetryDelay(1000);//两次连接中间隔时间，单位毫秒，默认为1000
				
//			    Element propertiesEles = XmlHandler.getElement(doc, "pools/" + pool+"/Properties");
//			    List<Element> params = propertiesEles.selectNodes("Param");
//			    if(params!=null&&params.size()>0){
//				    Properties properties = new Properties();
//					for(Element param:params){
//						if(Checker.isNull(param.attributeValue("name"))||Checker.isNull(param.getTextTrim()))
//							continue;
//						System.out.println("【PoolConfig-"+pool+"】-setProperties: "+param.attributeValue("name")+" = "+param.getTextTrim());
//						properties.setProperty(param.attributeValue("name"), param.getTextTrim());
//					}
//				  	db.setProperties(properties);
//				}
			    
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
