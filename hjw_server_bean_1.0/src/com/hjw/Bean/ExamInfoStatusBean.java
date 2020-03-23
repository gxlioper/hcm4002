package com.hjw.Bean;

public class ExamInfoStatusBean {
	private String exam_num="";//
	private String orderid="";	
	private String status="";
	private long synid;
	
	public long getSynid() {
		return synid;
	}
	public void setSynid(long synid) {
		this.synid = synid;
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
}
