package com.hjw.wst.DTO;

public class DataAnalysisConditionDTO {
	private long charging_id;
	private String dep_category;
	private long sample_id;
	private long item_id;
	private String condition;
	private String result;
	private String table_name;
	private String table_colnum;
	
	public long getCharging_id() {
		return charging_id;
	}
	public void setCharging_id(long charging_id) {
		this.charging_id = charging_id;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
	public long getSample_id() {
		return sample_id;
	}
	public void setSample_id(long sample_id) {
		this.sample_id = sample_id;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getTable_colnum() {
		return table_colnum;
	}
	public void setTable_colnum(String table_colnum) {
		this.table_colnum = table_colnum;
	}
}
