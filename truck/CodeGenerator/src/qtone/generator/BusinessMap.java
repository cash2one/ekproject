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
    String tableAlias = "";
    String sequence="";
    String primaryKey="id";
	String name;
    
	boolean isTransactionOff = false; // 事务开启 
    
    boolean isAreaDeal =false; //分表处理
    
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
	 * 从配置文件加载信息
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
	 * 得到主要的字段信息
	 * @return
	 */
	public List<FieldItem> getMainFields(){
		return this.templator.getItems();
	}
	
	/**
	 * 返回连所有接字段
	 * @return
	 */
	public List<FieldItem> getJoinFields(){
		return this.templator.getJoinFields();
	}
	
	
	/**
	 * 获取主键详细信息
	 * @return
	 */
	public FieldItem getPrimaryKeyItem(){
		return this.templator.getPrimaryKeyItem();
	}
	
	
	
	/**
	 * 返回连所有接的表
	 * @return
	 */
	public List<JoinItem> getJoinTable(){
		return this.templator.getJoinTables();
	}
	
	
	
	/**
	 * 返回直接与主表连接的表对象
	 * @return
	 */
	public List<JoinItem> getTopJoinTable(){
		return this.templator.getJoinItems();
	}
	
	
	public static void main(String...arg){
		BusinessMap map = new BusinessMap(null);
		System.out.println("主表属性-------");
		List<FieldItem> fields = map.getMainFields();
		for(FieldItem item :fields ){
			System.out.println(item.getName()+" "+item.getSourceField()+"  "+item.getDescript()+" "+item.getTableAlias());
		}
		
		
		System.out.println("从表属性-------");
		fields = map.getJoinFields();
		for(FieldItem item :fields ){
			System.out.println(item.getName()+" "+item.getSourceField()+"  "+item.getDescript()+" "+item.getTableAlias());
		}
		
		System.out.println("从表信息-------");
		List<JoinItem> joins = map.getJoinTable();
		for(JoinItem join:joins){
			System.out.println(" "+join.getJoinTableKey()+"  "+join.getTable()+" "+join.getAlias());
    	}
		
	}
	
	
	
	
	
}
