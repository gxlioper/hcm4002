package com.hjw.wst.DTO;

public class DiseaseLogicItemConditionDTO {
	private String id;
	private String logic_single_item_id;
	private String logic_composite_item_id;
	private String diseaseOrItem_num;
	private String item_name;
	private long condition_type;
	private String condition_value;
	private String condition;
	private long logic_index;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogic_single_item_id() {
		return logic_single_item_id;
	}
	public void setLogic_single_item_id(String logic_single_item_id) {
		this.logic_single_item_id = logic_single_item_id;
	}
	public String getLogic_composite_item_id() {
		return logic_composite_item_id;
	}
	public void setLogic_composite_item_id(String logic_composite_item_id) {
		this.logic_composite_item_id = logic_composite_item_id;
	}
	public String getDiseaseOrItem_num() {
		return diseaseOrItem_num;
	}
	public void setDiseaseOrItem_num(String diseaseOrItem_num) {
		this.diseaseOrItem_num = diseaseOrItem_num;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getCondition_type() {
		return condition_type;
	}
	public void setCondition_type(long condition_type) {
		this.condition_type = condition_type;
	}
	public String getCondition_value() {
		return condition_value;
	}
	public void setCondition_value(String condition_value) {
		this.condition_value = condition_value;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public long getLogic_index() {
		return logic_index;
	}
	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
	}
}
