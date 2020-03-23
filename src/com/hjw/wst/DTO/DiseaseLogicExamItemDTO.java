package com.hjw.wst.DTO;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.DTO
 * @Description: 疾病逻辑检查项目关系
 * @author: zr
 * @date: 2016年12月7日 上午9:18:18
 * @version V2.0.0.0
 */
public class DiseaseLogicExamItemDTO {
	private long id;
	private long exam_item_id;
	private long disease_logic_id;
	private long logic_index;
	private String condition_value;
	private String condition;
	private String andOrNo;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private long charging_item_id;
	
	private long ch_id;
	private String ch_name;
	private long e_id;
	private String e_name;
	
	public long getCh_id() {
		return ch_id;
	}

	public void setCh_id(long ch_id) {
		this.ch_id = ch_id;
	}

	public String getCh_name() {
		return ch_name;
	}

	public void setCh_name(String ch_name) {
		this.ch_name = ch_name;
	}

	public long getE_id() {
		return e_id;
	}

	public void setE_id(long e_id) {
		this.e_id = e_id;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public long getDisease_logic_id() {
		return disease_logic_id;
	}

	public void setDisease_logic_id(long disease_logic_id) {
		this.disease_logic_id = disease_logic_id;
	}

	public long getLogic_index() {
		return logic_index;
	}

	public void setLogic_index(long logic_index) {
		this.logic_index = logic_index;
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

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public long getCharging_item_id() {
		return charging_item_id;
	}

	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}

	public String getAndOrNo() {
		return andOrNo;
	}

	public void setAndOrNo(String andOrNo) {
		this.andOrNo = andOrNo;
	}

}
