package com.hjw.wst.model;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.DTO
 * @Description: 疾病逻辑检查项目关系
 * @author: zr
 * @date: 2016年12月7日 上午9:18:18
 * @version V2.0.0.0
 */
public class DiseaseLogicExamItemModel  implements  java.io.Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 5021487558337839072L;
	private long id;
	private long exam_item_id;
	private long disease_logic_id;
	private long condition_value;
	private String condition;
	private String andOrNo;
	private String logic_index;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private long charging_item_id;

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

	public long getCondition_value() {
		return condition_value;
	}

	public void setCondition_value(long condition_value) {
		this.condition_value = condition_value;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getAndOrNo() {
		return andOrNo;
	}

	public void setAndOrNo(String andOrNo) {
		this.andOrNo = andOrNo;
	}

	public String getLogic_index() {
		return logic_index;
	}

	public void setLogic_index(String logic_index) {
		this.logic_index = logic_index;
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

}
