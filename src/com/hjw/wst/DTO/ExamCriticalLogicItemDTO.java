package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;


public class ExamCriticalLogicItemDTO{
	private String id;
	private String logic_id;
	private String logic_item_name;
	private long logic_index;
	private List<ExamCriticalLogicItemConditionDTO> itemConditions = new ArrayList<ExamCriticalLogicItemConditionDTO>();
	public final String[] condition_name = new String[]{"条件一","条件二","条件三","条件四","条件五","条件六","条件七","条件八","条件九","条件十","条件十一","条件十二","条件十三","条件十四","条件十五","条件十六","条件十七","条件十八"};
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLogic_id() {
		return logic_id;
	}
	public void setLogic_id(String logic_id) {
		this.logic_id = logic_id;
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
	
	  public List<ExamCriticalLogicItemConditionDTO> getItemConditions() { return
	  itemConditions; } public void
	  setItemConditions(List<ExamCriticalLogicItemConditionDTO> itemConditions) {
	  this.itemConditions = itemConditions; }
	 
	
	
	
}
