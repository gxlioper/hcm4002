package com.hjw.Bean.ZJ;

public class AdviceMsg {
	private String summary;//小结	string	
	private String advice;//建议指导	string	
	private String doctor_begin;//初检医生	string	
	private String begin_time;//初检时间	string	
	private String doctor_last;//终检医生	string	
	private String last_time;//终检时间	string	
	private String medDate;//报告时间	string	格式yyyy-mm-dd hh:mm:ss
	
	public String getBegin_time() {
		return begin_time;
	}
	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}
	public String getLast_time() {
		return last_time;
	}
	public void setLast_time(String last_time) {
		this.last_time = last_time;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public String getDoctor_begin() {
		return doctor_begin;
	}
	public void setDoctor_begin(String doctor_begin) {
		this.doctor_begin = doctor_begin;
	}
	public String getDoctor_last() {
		return doctor_last;
	}
	public void setDoctor_last(String doctor_last) {
		this.doctor_last = doctor_last;
	}
	public String getMedDate() {
		return medDate;
	}
	public void setMedDate(String medDate) {
		this.medDate = medDate;
	}
	
	

}
