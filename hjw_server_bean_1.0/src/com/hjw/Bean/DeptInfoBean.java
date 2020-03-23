package com.hjw.Bean;

public class DeptInfoBean {
	private long dep_id;	
	private String dep_num;
	private String dep_name;
	private String dep_category;
	private String sex;
	private String seq_code;
	private String dep_link;
	private String remark;
	private String create_time;
	private String update_time;
	private String  isActive="Y";
	private long synid;	
	
	public long getSynid() {
		return synid;
	}
	public void setSynid(long synid) {
		this.synid = synid;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public String getDep_num() {
		return dep_num;
	}
	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(String seq_code) {
		this.seq_code = seq_code;
	}
	public String getDep_link() {
		return dep_link;
	}
	public void setDep_link(String dep_link) {
		this.dep_link = dep_link;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}	
}
