package com.hjw.zyb.model;





public class ZybOcctemplateModel {
	
	private String Id;
	private int exam_type;
	private String exam_type_name;
	private String occuphyexaclassid;
	private String occuphyexaclass_name;
	private String template;
	private String remark;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getTemplate() {
		return template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getExam_type_name() {
		return exam_type_name;
	}
	public void setExam_type_name(String exam_type_name) {
		this.exam_type_name = exam_type_name;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public int getExam_type() {
		return exam_type;
	}
	public void setExam_type(int exam_type) {
		this.exam_type = exam_type;
	}
	public String getOccuphyexaclassid() {
		return occuphyexaclassid;
	}
	public void setOccuphyexaclassid(String occuphyexaclassid) {
		this.occuphyexaclassid = occuphyexaclassid;
	}
}
