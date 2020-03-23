package com.hjw.wst.DTO;

public class ExamDepResultDTO {

	private long id;
	private long exam_info_id;
	private String exam_doctor;
	private String exam_date;
	private String exam_result_summary;
	private String suggestion;
	private String exam_result;
	private String join_date;
	private String item_name;
	private String commentary;
	private String exam_num;
	
	private String dep_num="";
	private String dep_name;
	private long check_count;
	private String dep_category;

	public String getDep_category() {
		return dep_category;
	}

	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	
	public String getCommentary() {
		return commentary;
	}
	public void setCommentary(String commentary) {
		this.commentary = commentary;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
		
		if(exam_result.equals("1")){
			this.commentary = "偏高";
		}else if(exam_result.equals("2")){
			this.commentary = "偏低";
		}
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
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
	public String getExam_result_summary() {
		return exam_result_summary;
	}
	public void setExam_result_summary(String exam_result_summary) {
		this.exam_result_summary = exam_result_summary;
	}
	public String getSuggestion() {
		return suggestion;
	}
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
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

	public long getCheck_count() {
		return check_count;
	}

	public void setCheck_count(long check_count) {
		this.check_count = check_count;
	}
	
}
