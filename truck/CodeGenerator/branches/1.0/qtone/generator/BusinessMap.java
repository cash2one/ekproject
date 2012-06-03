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
	
	String voNamespace="vo";
	
    String entityNamespace="entity";
	
	String mapperNamespace="imp";
	
    String table="${areaAbb}_XJ_STUDENT";
    String tableAlias = "";
    String sequence="";
    String primaryKey="id";
	String name;
    
	boolean isTransactionOff = false; // ������ 
    
    boolean isAreaDeal =false; //�ֱ���
    
    String version;
    String author;
    String description;
    
    
    private Templater templator = null;
    
    
    
    
    public boolean isTransactionOff() {
		return isTransactionOff;
	}

	public void setTransactionOff(boolean isTransactionOff) {
		this.isTransactionOff = isTransactionOff;
	}

	
	public String getVoNamespace() {
		return voNamespace;
	}

	public void setVoNamespace(String voNamespace) {
		this.voNamespace = voNamespace;
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

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

	 

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "imp";
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


	public String getTableAlias() {
		return tableAlias;
	}

	private void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
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


	public boolean isAreaDeal() {
		return isAreaDeal;
	}

	public void setAreaDeal(boolean isAreaDeal) {
		this.isAreaDeal = isAreaDeal;
	}


	String bussinessMapCfg ="";
    private Document _doc = null;
    
    
    
	public BusinessMap(String bussinessMapCfgPath){
		bussinessMapCfg = bussinessMapCfgPath==null?"src/qtone/generator/demo.xml":bussinessMapCfgPath;
		_doc = XmlHandler.loadXML(bussinessMapCfg);
		reBuilderMap( );
	}
	
	
	/**
	 * �������ļ�������Ϣ
	 */
	void reBuilderMap( ){
		Element _root =  _doc.getRootElement();
		this.setNamespace(_root.attributeValue("namespace"));
	    this.setBusinessNamespace(_root.attributeValue("businessNamespace"));
		this.setClazz(_root.attributeValue("class"));
		this.setDaoNamespace(_root.attributeValue("daoNamespace"));
		this.setEntityNamespace(_root.attributeValue("entityNamespace"));
		this.setMapperNamespace(_root.attributeValue("mapperNamespace"));
		this.setTable(_root.attributeValue("table"));
		this.setPrimaryKey(_root.attributeValue("primaryKey"));
		this.setSequence(_root.attributeValue("sequence"));
		this.setAreaDeal(_root.attributeValue("isAreaDeal")!=null&&"true".equals(_root.attributeValue("isAreaDeal").toLowerCase())?true:false);
		this.setTableAlias(_root.attributeValue("tableAlias"));
		this.setName(this.getClazz()+"Info");
		this.setVersion(XmlHandler.getElement(_doc,"descript/version").getText());
		this.setAuthor(XmlHandler.getElement(_doc,"descript/author").getText());
		this.setDescription(XmlHandler.getElement(_doc,"descript/memo").getText());
		templator = new Templater(_doc,this.getPrimaryKey());
	}
	
	
	/**
	 * �õ���Ҫ���ֶ���Ϣ
	 * @return
	 */
	public List<FieldItem> getMainFields(){
		return this.templator.getItems();
	}
	
	/**
	 * ���������н��ֶ�
	 * @return
	 */
	public List<FieldItem> getJoinFields(){
		return this.templator.getJoinFields();
	}
	
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @return
	 */
	public FieldItem getPrimaryKeyItem(){
		return this.templator.getPrimaryKeyItem();
	}
	
	
	
	/**
	 * ���������нӵı�
	 * @return
	 */
	public List<JoinItem> getJoinTable(){
		return this.templator.getJoinTables();
	}
	
	
	
	/**
	 * ����ֱ�����������ӵı����
	 * @return
	 */
	public List<JoinItem> getTopJoinTable(){
		return this.templator.getJoinItems();
	}
	
	
	public static void main(String...arg){
		BusinessMap map = new BusinessMap(null);
		System.out.println("��������-------");
		List<FieldItem> fields = map.getMainFields();
		for(FieldItem item :fields ){
			System.out.println(item.getName()+" "+item.getSourceField()+"  "+item.getDescript()+" "+item.getTableAlias());
		}
		
		
		System.out.println("�ӱ�����-------");
		fields = map.getJoinFields();
		for(FieldItem item :fields ){
			System.out.println(item.getName()+" "+item.getSourceField()+"  "+item.getDescript()+" "+item.getTableAlias());
		}
		
		System.out.println("�ӱ���Ϣ-------");
		List<JoinItem> joins = map.getJoinTable();
		for(JoinItem join:joins){
			System.out.println(" "+join.getJoinTableKey()+"  "+join.getTable()+" "+join.getAlias());
    	}
		
	}
	
	
	
	
	
}
