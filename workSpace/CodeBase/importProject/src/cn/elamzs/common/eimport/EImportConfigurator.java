package cn.elamzs.common.eimport;

/**
 * ��� ����� web ����ʱ����Ҫָ�������ļ���ȫ·��
 * @author Ethan.Lam   2011-3-6
 *
 */
public class EImportConfigurator {

	public static String configureXml = "configs/EImportCfg.xml";

	/**
	 * �����ļ���ȫ·��
	 * @param cfgXmlLocation
	 */
	public static void configure(String cfgXmlLocation) {
		if (cfgXmlLocation != null && !"".equals(cfgXmlLocation))
			configureXml = cfgXmlLocation;
	}

}
