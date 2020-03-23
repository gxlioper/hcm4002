package com.hjw.zyb.DTO;

public class ZybInquisitionResultDTO {

	private long id;
	private long examinfo_id;
	private String item_id;
	private String result;
	private String item_type;
	private String item_types;
	private String exam_doctor;
	private String exam_date;
	private String exam_num;
    
    public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExaminfo_id() {
		return examinfo_id;
	}
	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getItem_type() {
		return item_type;
	}
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	public String getItem_types() {
		return item_types;
	}
	public void setItem_types(String item_types) {
		this.item_types = item_types;
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
