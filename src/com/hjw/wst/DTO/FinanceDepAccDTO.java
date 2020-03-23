package com.hjw.wst.DTO;

public class FinanceDepAccDTO {

	private double fd_acc_amount;
	private double way_amount;
	private long charging_way;
	private String data_name;
	private String fd_acc_num;
	private long userId;
	private String fd_acc_date;
	private String user_name;
	private double invoice_amount;
	
	public double getFd_acc_amount() {
		return fd_acc_amount;
	}
	public void setFd_acc_amount(double fd_acc_amount) {
		this.fd_acc_amount = fd_acc_amount;
	}
	public double getWay_amount() {
		return way_amount;
	}
	public void setWay_amount(double way_amount) {
		this.way_amount = way_amount;
	}
	public long getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(long charging_way) {
		this.charging_way = charging_way;
	}
	public String getData_name() {
		return data_name;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public String getFd_acc_num() {
		return fd_acc_num;
	}
	public void setFd_acc_num(String fd_acc_num) {
		this.fd_acc_num = fd_acc_num;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getFd_acc_date() {
		return fd_acc_date;
	}
	public void setFd_acc_date(String fd_acc_date) {
		this.fd_acc_date = fd_acc_date;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
}
