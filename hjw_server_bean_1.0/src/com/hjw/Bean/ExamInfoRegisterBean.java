package com.hjw.Bean;

public class ExamInfoRegisterBean {
	private String ordernum="";//编号
	private String ordertype="";//1 表示体检编号 2表示订单编号
	private String register_date="";//预约日期	register_data
	private String register_times="";//预约时间段	register_times
		
	public String getOrdernum() {
		return ordernum;
	}
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getRegister_date() {
		return register_date;
	}
	public void setRegister_date(String register_date) {
		this.register_date = register_date;
	}
	public String getRegister_times() {
		return register_times;
	}
	public void setRegister_times(String register_times) {
		this.register_times = register_times;
	}

	
}
