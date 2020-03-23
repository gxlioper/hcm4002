package com.hjw.wst.DTO;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 项目知识库
 * @author: zr
 * @date: 2016年10月26日 上午10:45:52
 * @version V2.0.0.0
 */
public class ItemResultLibDTO implements java.io.Serializable {
	private static final long serialVersionUID = -2246054263316406307L;

	private long id;
	
	private long ids;//列表2
	
	private long exam_item_id;

	private long dep_id;

	private String exam_result;
	
	private String exam_results;//列表2

	private String result_num;

	private String isActive;

	private long   creater;

	private String creaters;

	private String create_time;

	private long   updater;

	private String updaters;

	private String update_time;

	private long seq_code;
	
	private String seq_codes;

	private String common_words;
	
	private String item_num;//检查项目编码
	
	private String dep_name;//科室名称
	
	private String item_name;//项目名称
	
	private long default_value;
	
	private String default_value_s;
	
	private String exam_conclusion;
	private String exam_conclusions;

	public String getExam_conclusions() {
		return exam_conclusions;
	}

	public void setExam_conclusions(String exam_conclusions) {
		this.exam_conclusions = exam_conclusions;
	}

	public String getExam_conclusion() {
		return exam_conclusion;
	}

	public void setExam_conclusion(String exam_conclusion) {
		this.exam_conclusion = exam_conclusion;
	}

	public String getDefault_value_s() {
		return default_value_s;
	}

	public void setDefault_value_s(String default_value_s) {
		this.default_value_s = default_value_s;
	}

	public long getDefault_value() {
		return default_value;
	}

	public void setDefault_value(long default_value) {
		this.default_value = default_value;
		if(default_value>0){
			this.setDefault_value_s(default_value+"");
		}
	}

	public long getIds() {
		return ids;
	}

	public void setIds(long ids) {
		this.ids = ids;
	}

	public String getExam_results() {
		return exam_results;
	}

	public void setExam_results(String exam_results) {
		this.exam_results = exam_results;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		String aa=seq_code+"";
		this.setSeq_codes(aa);
	}

	public String getCommon_words() {
		return common_words;
	}

	public void setCommon_words(String common_words) {
		this.common_words = common_words;
	}

	public String getCreaters() {
		return creaters;
	}

	public void setCreaters(String creaters) {
		this.creaters = creaters;
	}

	public String getUpdaters() {
		return updaters;
	}

	public void setUpdaters(String updaters) {
		this.updaters = updaters;
	}

	public String getItem_num() {
		return item_num;
	}

	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getSeq_codes() {
		return seq_codes;
	}

	public void setSeq_codes(String seq_codes) {
		this.seq_codes = seq_codes;
	}
	
}
