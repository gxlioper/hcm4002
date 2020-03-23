package com.hjw.wst.DTO;

import java.util.List;

import net.sf.json.JSONArray;

public class DiseaseLogicItemDTO {

	private String id;
	private String logic_single_id;
	private String logic_composite_id;
	private String logic_item_name;
	private long logic_index;
	private long critical_flag;
	private int disease_count;

	private JSONArray itemConditions;
	private List<DiseaseLogicItemConditionDTO> itemCondition;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogic_single_id() {
		return logic_single_id;
	}
	public void setLogic_single_id(String logic_single_id) {
		this.logic_single_id = logic_single_id;
	}
	public String getLogic_composite_id() {
		return logic_composite_id;
	}
	public void setLogic_composite_id(String logic_composite_id) {
		this.logic_composite_id = logic_composite_id;
	}
	public String getLogic_item_name() {
		return logic_item_name;
	}
	public void setLogic_item_name(String logic_item_name) {
		this.logic_item_name = logic_item_name;
	}
	public long getLogic_index() {
		return logic_index;
	}
	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
	}
	public long getCritical_flag() {
		return critical_flag;
	}
	public void setCritical_flag(long critical_flag) {
		this.critical_flag = critical_flag;
	}
	public JSONArray getItemConditions() {
		return itemConditions;
	}
	public void setItemConditions(JSONArray itemConditions) {
		this.itemConditions = itemConditions;
	}
	public List<DiseaseLogicItemConditionDTO> getItemCondition() {
		return itemCondition;
	}
	public void setItemCondition(List<DiseaseLogicItemConditionDTO> itemCondition) {
		this.itemCondition = itemCondition;
	}

    public void setDisease_count(int disease_count) {
        this.disease_count = disease_count;
    }
}
