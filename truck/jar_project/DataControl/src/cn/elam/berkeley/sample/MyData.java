package cn.elam.berkeley.sample;

import java.io.Serializable;

public class MyData implements Serializable {

	private long longData;
	private double doubleData;
	private String description;

	public MyData() {
		longData = 0;
		doubleData = 0.0;
		description = null;
	}

	public void setLong(long data) {
		longData = data;
	}

	public void setDouble(double data) {
		doubleData = data;
	}

	public void setDescription(String data) {
		description = data;
	}

	public long getLong() {
		return longData;
	}

	public double getDouble() {
		return doubleData;
	}

	public String getDescription() {
		return description;
	}

}
