package qtone.generator;


/**
 * 
 * 表字段信息
 * @author Ethan.Lam  2011-7-4
 *
 */
public class FieldItem {

	String name = "id";
	String type = "long";
	String sourceType = "Decimal";
	boolean isReadonly = false;
	String sourceField = "ID";
	String descript = "主键id";
	String tableAlias = "";
	
	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public boolean getIsReadonly() {
		return isReadonly;
	}

	public void setIsReadonly(boolean isReadonly) {
		this.isReadonly = isReadonly;
	}

	public String getSourceField() {
		return sourceField;
	}

	public void setSourceField(String sourceField) {
		this.sourceField = sourceField;
	}

	public String getDescript() {
		return descript;
	}

	public void setDescript(String descript) {
		this.descript = descript;
	}

}
