package com.hjw.Bean;

public class OrderExamStatusBean {
	private String orderid="";//订单编号
	private String exam_num="";//姓名	
	private String status="";//y预约d登记j检查中z已终检
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
