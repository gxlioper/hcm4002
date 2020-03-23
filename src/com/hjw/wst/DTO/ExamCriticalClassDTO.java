package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class ExamCriticalClassDTO{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	
	private long id;
	private String critical_class_name;
	private long critical_class_level;
	private long parent_id;
	private long creater;
	private String create_time;
	private String state = "closed";
	private long seq_code;
	private String remark;
	private List<ExamCriticalClassDTO> children = new ArrayList<ExamCriticalClassDTO>();
	private String info;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCritical_class_name() {
		return critical_class_name;
	}
	public void setCritical_class_name(String critical_class_name) {
		this.critical_class_name = critical_class_name;
	}
	public long getCritical_class_level() {
		return critical_class_level;
	}
	public void setCritical_class_level(long critical_class_level) {
		this.critical_class_level = critical_class_level;
	}
	public long getParent_id() {
		return parent_id;
	}
	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public long getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<ExamCriticalClassDTO> getChildren() {
		return children;
	}
	public void setChildren(List<ExamCriticalClassDTO> children) {
		this.children = children;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
