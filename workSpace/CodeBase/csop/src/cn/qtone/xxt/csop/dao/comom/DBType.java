package cn.qtone.xxt.csop.dao.comom;

public enum DBType {

	ORACLE(0, "Oracle"),
	MYSQL(1, "MySQL"),
	SQLSERVER(2,"SqlServer"),
	UNKNOWN(3, "Unknown");

	DBType(int type, String name) {
		this.type = type;
		this.name = name;
	}

	private int type;
	private String name;

}