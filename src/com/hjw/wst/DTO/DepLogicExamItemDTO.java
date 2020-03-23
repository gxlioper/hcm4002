package com.hjw.wst.DTO;


/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 科室疾病项目关系表
 * @author: zr
 * @date: 2016年12月13日 上午9:23:36
 * @version V2.0.0.0
 */
public class DepLogicExamItemDTO implements java.io.Serializable {


	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 4154520843905147408L;

	private long id;

	private long logic_id;

	private long exam_item_id;

	private long logic_index;

	private String condition_value;

	private String condition;

	private String andOrNo;

	private long creater;

	private String create_time;

	private long updater;

	private String update_time;
	
	//检查项目
	private String  item_name;
	
	private  String  e_name;
	
	private  long e_id;
	

	
	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public long getE_id() {
		return e_id;
	}

	public void setE_id(long e_id) {
		this.e_id = e_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

}
