package com.hjw.charge.DTO;

public class CashierDailyAccInvoiceDTO {

	private String charging_way;
	private long charging_way_id;
	private String invoice_num;
	private String invoice_status;
	private String invoice_statuss;
	private double invoice_amount;
	private String invoice_time;
	private String user_name;
	private String title_info;
	
	public String getTitle_info() {
		return title_info;
	}
	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public long getCharging_way_id() {
		return charging_way_id;
	}
	public void setCharging_way_id(long charging_way_id) {
		this.charging_way_id = charging_way_id;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getInvoice_status() {
		return invoice_status;
	}
	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
		if("Y".equals(invoice_status)){
			this.invoice_statuss = "开票";
		}else if("N".equals(invoice_status)){
			this.invoice_statuss = "退票";
		}else if("B".equals(invoice_status)){
			this.invoice_statuss = "废票";
		}else{
			this.invoice_statuss = "未知";
		}
	}
	public double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
	public String getInvoice_statuss() {
		return invoice_statuss;
	}
	public void setInvoice_statuss(String invoice_statuss) {
		this.invoice_statuss = invoice_statuss;
	}
	public String getInvoice_time() {
		return invoice_time;
	}
	public void setInvoice_time(String invoice_time) {
		this.invoice_time = invoice_time;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
}
