package com.hjw.wst.DTO;

public class ExamSummaryLogDTO {

	private long id;
	private long operation_type;
	private String operation_types;
	private String exam_doctor;
	private String exam_date;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOperation_type() {
		return operation_type;
	}
	public void setOperation_type(long operation_type) {
		this.operation_type = operation_type;
		if(operation_type == 1){
			this.operation_types = "总检";
		}else if(operation_type == 2){
			this.operation_types = "审核";
		}else if(operation_type == 3){
			this.operation_types = "复审";
		}else if(operation_type == 4){
			this.operation_types = "一键取消恢复";
		}
	}
	public String getOperation_types() {
		return operation_types;
	}
	public void setOperation_types(String operation_types) {
		this.operation_types = operation_types;
	}
	public String getExam_doctor() {
		return exam_doctor;
	}
	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
}
