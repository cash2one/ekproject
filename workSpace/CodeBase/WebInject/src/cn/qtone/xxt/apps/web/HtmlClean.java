package cn.qtone.xxt.apps.web;

import java.io.File;
import java.io.IOException;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.TagNode;

public class HtmlClean {
	public void cleanHtml(String htmlurl, String xmlurl) {
		try {
			long start = System.currentTimeMillis();

			HtmlCleaner cleaner = new HtmlCleaner();
			CleanerProperties props = cleaner.getProperties();
			props.setUseCdataForScriptAndStyle(true);
			props.setRecognizeUnicodeChars(true);
			props.setUseEmptyElementTags(true);
			props.setAdvancedXmlEscape(true);
			props.setTranslateSpecialEntities(true);
			props.setBooleanAttributeValues("empty");

			TagNode node = cleaner.clean(new File(htmlurl));

			System.out.println("vreme:" + (System.currentTimeMillis() - start));

			new PrettyXmlSerializer(props).writeXmlToFile(node, xmlurl);

			System.out.println("vreme:" + (System.currentTimeMillis() - start));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
