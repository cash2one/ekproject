package cn.elamzs.common.eimport;

/**
 * 
 * @author Ethan.Lam   2011-3-6
 *
 */
public class EImportConfigurator {

	public static String configureXml = "configs/EImportCfg.xml";

	public static void configure(String cfgXmlLocation) {
		if (cfgXmlLocation != null && !"".equals(cfgXmlLocation))
			configureXml = cfgXmlLocation;
	}

}
