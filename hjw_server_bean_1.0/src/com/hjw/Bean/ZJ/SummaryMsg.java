package com.hjw.Bean.ZJ;

public class SummaryMsg {
	private String organ_id="";//所属的组织id	string	
	private String organ_code="";//
	private String organ_name="";//组织名称	string	
	private String summary="";//小结内容	string	
	private String doctor="";//小结医生	string	
	private String exam_time="";//检查时间	string	格式yyyy-mm-dd hh:mm:ss
	private String rev_doctor="";//审核医生		string	
	private String rev_time="";//审核时间	string	格式yyyy-mm-dd hh:mm:ss
	
	public String getOrgan_code() {
		return organ_code;
	}
	public void setOrgan_code(String organ_code) {
		this.organ_code = organ_code;
	}
	public String getOrgan_id() {
		return organ_id;
	}
	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}
	public String getOrgan_name() {
		return organ_name;
	}
	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getExam_time() {
		return exam_time;
	}
	public void setExam_time(String exam_time) {
		this.exam_time = exam_time;
	}
	public String getRev_doctor() {
		return rev_doctor;
	}
	public void setRev_doctor(String rev_doctor) {
		this.rev_doctor = rev_doctor;
	}
	public String getRev_time() {
		return rev_time;
	}
	public void setRev_time(String rev_time) {
		this.rev_time = rev_time;
	}
	
	

}
