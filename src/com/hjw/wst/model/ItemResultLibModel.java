package com.hjw.wst.model;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description: 项目知识库 
     * @author: zr     
     * @date:   2016年10月26日 上午10:45:52   
     * @version V2.0.0.0
 */
public class ItemResultLibModel  implements java.io.Serializable{
	private static final long serialVersionUID = -2246054263316406307L;
	
	private   long      id;
	
	private   long      exam_item_id;
	
	private   long      dep_id=(long)0;
	
	private   String    exam_result;
	
	private   String    result_num;
	
	private   String    isActive;
	
	private   long      creater;
	
	private   String      create_time;
	
	private   long      updater;
	
	private   String      update_time;
	
	private   long      seq_code;
	
	private   String   common_words;
	
	private   String   default_value;//是否默认值
	
	private String exam_conclusion;

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
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

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}

	public String getResult_num() {
		return result_num;
	}

	public void setResult_num(String result_num) {
		this.result_num = result_num;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}

	public String getCommon_words() {
		return common_words;
	}

	public void setCommon_words(String common_words) {
		this.common_words = common_words;
	}

	public String getExam_conclusion() {
		return exam_conclusion;
	}

	public void setExam_conclusion(String exam_conclusion) {
		this.exam_conclusion = exam_conclusion;
	}
	
}
