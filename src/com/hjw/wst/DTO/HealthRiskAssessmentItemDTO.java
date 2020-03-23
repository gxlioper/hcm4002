package com.hjw.wst.DTO;

public class HealthRiskAssessmentItemDTO {

	private long id;
	private long health_risk_id;
	private String item_code;
	private String item_name;
	private long item_type;
	private String sex;
	private long item_seq;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getHealth_risk_id() {
		return health_risk_id;
	}
	public void setHealth_risk_id(long health_risk_id) {
		this.health_risk_id = health_risk_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getItem_type() {
		return item_type;
	}
	public void setItem_type(long item_type) {
		this.item_type = item_type;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getItem_seq() {
		return item_seq;
	}
	public void setItem_seq(long item_seq) {
		this.item_seq = item_seq;
	}
}
