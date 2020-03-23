package com.hjw.wst.DTO;

public class HealthRiskExaminfoItemDTO {
	private long id;
	private long risk_examinfo_id;
	private String risk_item_code;
	private long point;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRisk_examinfo_id() {
		return risk_examinfo_id;
	}
	public void setRisk_examinfo_id(long risk_examinfo_id) {
		this.risk_examinfo_id = risk_examinfo_id;
	}
	public String getRisk_item_code() {
		return risk_item_code;
	}
	public void setRisk_item_code(String risk_item_code) {
		this.risk_item_code = risk_item_code;
	}
	public long getPoint() {
		return point;
	}
	public void setPoint(long point) {
		this.point = point;
	}
}
