package com.hjw.wst.DTO;

public class ViewCommonWordsDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	private long sample_id;
	private String sample_name;
	private String exam_desc;
	private String exam_result;
	private String exam_descs;
	private String exam_results;
	private String isActive;
	private String creater;
	private String create_time;
	private String updater;
	private String update_time;
	private String seq_code;
	private String dep_name;
	private long is_default;
	private String is_defaults;
	
	public String getExam_descs() {
		return exam_descs;
	}
	public void setExam_descs(String exam_descs) {
		this.exam_descs = exam_descs;
	}
	public String getExam_results() {
		return exam_results;
	}
	public void setExam_results(String exam_results) {
		this.exam_results = exam_results;
	}
	public long getIs_default() {
		return is_default;
	}
	public void setIs_default(long is_default) {
		this.is_default = is_default;
		if(is_default == 1){
			this.is_defaults = "是";
		}else{
			this.is_defaults = "否";
		}
	}
	public String getIs_defaults() {
		return is_defaults;
	}
	public void setIs_defaults(String is_defaults) {
		this.is_defaults = is_defaults;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSample_id() {
		return sample_id;
	}
	public void setSample_id(long sample_id) {
		this.sample_id = sample_id;
	}
	public String getSample_name() {
		return sample_name;
	}
	public void setSample_name(String sample_name) {
		this.sample_name = sample_name;
	}
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(String seq_code) {
		this.seq_code = seq_code;
	}
}
