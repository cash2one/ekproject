package cn.elam.util.db;

/**
 * 
 * @author Ethan.Lam   2011-3-6
 *
 */
public class DBDOMConfigurator {

	public static String configureXml = "configs/PoolConfig.xml";

	public static void configure(String cfgXmlLocation) {
		if (cfgXmlLocation != null && !"".equals(cfgXmlLocation))
			configureXml = cfgXmlLocation;
	}

}
