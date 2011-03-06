package cn.elamzs.common.eimport.config;

/**
 * 
 * @author Ethan.Lam   2011-3-6
 *
 */
public class DOMConfigurator {

	public static String configureXml = "configs/EImportCfg.xml";

	public static void configure(String cfgXmlLocation) {
		if (cfgXmlLocation != null && !"".equals(cfgXmlLocation))
			configureXml = cfgXmlLocation;
	}

}
