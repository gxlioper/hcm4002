package com.hjw.wst.DTO;

public class FinanceVsCashierDailyAccDTO {

	private String fd_acc_num;
	private String daily_acc_num;
	private String is_active;
	private String user_name;
	private String daily_acc_date;
	private double daily_acc_amount;
	private double invoice_amount;
	
	public String getFd_acc_num() {
		return fd_acc_num;
	}
	public void setFd_acc_num(String fd_acc_num) {
		this.fd_acc_num = fd_acc_num;
	}
	public String getDaily_acc_num() {
		return daily_acc_num;
	}
	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDaily_acc_date() {
		return daily_acc_date;
	}
	public void setDaily_acc_date(String daily_acc_date) {
		this.daily_acc_date = daily_acc_date;
	}
	public double getDaily_acc_amount() {
		return daily_acc_amount;
	}
	public void setDaily_acc_amount(double daily_acc_amount) {
		this.daily_acc_amount = daily_acc_amount;
	}
	public double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
}
