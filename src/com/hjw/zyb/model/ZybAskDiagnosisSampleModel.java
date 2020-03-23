package com.hjw.zyb.model;

public class ZybAskDiagnosisSampleModel {
	private long id;
	private String name;
	private String sub_name;
	private long seq_code; 
	private String type;
	private String temp_content;
	private String is_active;
	private String sex;
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	public long getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTemp_content() {
		return temp_content;
	}
	public void setTemp_content(String temp_content) {
		this.temp_content = temp_content;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	} 
}
