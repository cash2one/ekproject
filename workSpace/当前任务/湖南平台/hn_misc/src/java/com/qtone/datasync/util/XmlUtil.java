package com.qtone.datasync.util;

import java.io.IOException;
import java.io.StringWriter;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	public static String toXmlStr(Document doc) {
		StringWriter out = new StringWriter();
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding("GBK");
		XMLWriter writer = new XMLWriter(out, outformat);
		try {
			writer.write(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toString();
	}
}
