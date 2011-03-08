package cn.elamzs.common.eimport;

/**
 * 针对 如果是 web 部署时，需要指定配置文件的全路径
 * @author Ethan.Lam   2011-3-6
 *
 */
public class EImportConfigurator {

	public static String configureXml = "configs/EImportCfg.xml";

	/**
	 * 配置文件的全路径
	 * @param cfgXmlLocation
	 */
	public static void configure(String cfgXmlLocation) {
		if (cfgXmlLocation != null && !"".equals(cfgXmlLocation))
			configureXml = cfgXmlLocation;
	}

}
