package qtone.generator;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import cn.elam.util.file.xml.XmlHandler;


/**
 * 
 * @author Ethan.Lam  2011-7-4
 *
 */
public class BusinessMap {

	String namespace="edu";  
	String clazz="Student";
	String businessNamespace="business";
    String daoNamespace="dao";
	String entityNamespace="entity";
	String mapperNamespace="mapper";
    String table="${areaAbb}_XJ_STUDENT";
    String sequence="${areaAbb}_xj_student_seq";
    String primaryKey="id";
	
    
    String version;
    String author;
    String demo;
    
    
    public String getVersion() {
		return version;
	}

    private void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	private void setAuthor(String author) {
		this.author = author;
	}

	public String getDemo() {
		return demo;
	}

	private void setDemo(String demo) {
		this.demo = demo;
	}

	public String getNamespace() {
		return namespace;
	}

	private void setNamespace(String namespace) {
		this.namespace = namespace;
	}


	public String getClazz() {
		return clazz;
	}


	private void setClazz(String clazz) {
		this.clazz = clazz;
	}


	public String getBusinessNamespace() {
		return businessNamespace;
	}


	private void setBusinessNamespace(String businessNamespace) {
		this.businessNamespace = businessNamespace;
	}


	public String getDaoNamespace() {
		return daoNamespace;
	}


	private void setDaoNamespace(String daoNamespace) {
		this.daoNamespace = daoNamespace;
	}


	public String getEntityNamespace() {
		return entityNamespace;
	}


	private void setEntityNamespace(String entityNamespace) {
		this.entityNamespace = entityNamespace;
	}


	public String getMapperNamespace() {
		return mapperNamespace;
	}


	private void setMapperNamespace(String mapperNamespace) {
		this.mapperNamespace = mapperNamespace;
	}


	public String getTable() {
		return table;
	}


	private void setTable(String table) {
		this.table = table;
	}


	public String getSequence() {
		return sequence;
	}


	private void setSequence(String sequence) {
		this.sequence = sequence;
	}


	public String getPrimaryKey() {
		return primaryKey;
	}

	
	private void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}


	String bussinessMapCfg ="";
    private Document _doc = null;
    
	public BusinessMap(String bussinessMapCfg){
		this.bussinessMapCfg = bussinessMapCfg;
		_doc = XmlHandler.loadXML("configs/sysCfg.xml");
		reBuilderMap( );
	}
	
	
	void reBuilderMap( ){
		Element _root =  _doc.getRootElement();
		this.setNamespace(_root.attributeValue("namespace"));
	    this.setBusinessNamespace(_root.attributeValue("businessNamespace"));
		this.setClazz(_root.attributeValue(""));
		this.setDaoNamespace(_root.attributeValue("daoNamespace"));
		this.setEntityNamespace(_root.attributeValue("entityNamespace"));
		this.setMapperNamespace(_root.attributeValue("mapperNamespace"));
		this.setTable(_root.attributeValue("table"));
		this.setPrimaryKey(_root.attributeValue("primaryKey"));
		this.setSequence(_root.attributeValue("sequence"));
		
		this.setVersion(XmlHandler.getElement(_doc,"descript/version").getText());
		this.setAuthor(XmlHandler.getElement(_doc,"descript/author").getText());
		this.setDemo(XmlHandler.getElement(_doc,"descript/memo").getText());
	
		List <Element> itemsElement =  XmlHandler.getElements(_doc, "items/item");
		
		
	}
	
	
	
}
