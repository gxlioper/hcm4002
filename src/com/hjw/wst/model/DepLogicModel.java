package com.hjw.wst.model;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 科室逻辑
 * @author: zr
 * @date: 2016年12月13日 上午9:13:11
 * @version V2.0.0.0
 */
@SuppressWarnings("serial")
public class DepLogicModel implements java.io.Serializable {

	// 科室逻辑主表
	private long id;
	private long dep_id;
	private String conclusion_word;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	// 科室逻辑项目关系
	private long logic_id;
	private String e_item_name;
	private long exam_item_id;
	private long logic_index;
	private String condition_value;
	private String condition;
	private String andOrNo;
	private String sex;
	
	private  String ids;
	
	private String li;
	
	

	public String getLi() {
		return li;
	}

	public void setLi(String li) {
		this.li = li;
	}

	public String getE_item_name() {
		return e_item_name;
	}

	public void setE_item_name(String e_item_name) {
		this.e_item_name = e_item_name;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getConclusion_word() {
		return conclusion_word;
	}

	public void setConclusion_word(String conclusion_word) {
		this.conclusion_word = conclusion_word;
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

	public long getLogic_id() {
		return logic_id;
	}

	public void setLogic_id(long logic_id) {
		this.logic_id = logic_id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
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

	public String getAndOrNo() {
		return andOrNo;
	}

	public void setAndOrNo(String andOrNo) {
		this.andOrNo = andOrNo;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
