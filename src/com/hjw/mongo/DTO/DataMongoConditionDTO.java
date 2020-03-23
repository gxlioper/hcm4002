package com.hjw.mongo.DTO;

public class DataMongoConditionDTO {
	private long charging_id;
	private String charging_name;
	private String dep_category;
	private long item_id;
	private String item_name;
	private String dcondition;
	private String condition;
	private String result;
	
	public String getDcondition() {
		return dcondition;
	}
	public void setDcondition(String dcondition) {
		this.dcondition = dcondition;
	}
	public String getCharging_name() {
		return charging_name;
	}
	public void setCharging_name(String charging_name) {
		this.charging_name = charging_name;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
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
}
