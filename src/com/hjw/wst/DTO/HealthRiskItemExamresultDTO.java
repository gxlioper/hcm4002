package com.hjw.wst.DTO;

public class HealthRiskItemExamresultDTO {

	private long id;
	private String exam_num;
	private String risk_item_code;
	private String exam_result;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getRisk_item_code() {
		return risk_item_code;
	}
	public void setRisk_item_code(String risk_item_code) {
		this.risk_item_code = risk_item_code;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
}
