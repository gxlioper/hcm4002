package com.hjw.wst.model;

public class UserInvoiceModel implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private long user_id;
	private String user_name;
	private String invoice_num_min;//最小发票号
	private String invoice_num_max;//最大发票号
	private String invoice_num_used;//已使用最大发票号
	private String invoice_class;//发票类型 数据字典 FPLX
	private String is_active;//启用那种发票类型号段 Y表示启用，N表示未启用
	private String is_use_all;//是否启用共用号段 Y表示启用，N表示未启用
	private String user_invoice_num_length;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getInvoice_num_min() {
		return invoice_num_min;
	}
	public void setInvoice_num_min(String invoice_num_min) {
		this.invoice_num_min = invoice_num_min;
	}
	public String getInvoice_num_max() {
		return invoice_num_max;
	}
	public void setInvoice_num_max(String invoice_num_max) {
		this.invoice_num_max = invoice_num_max;
	}
	public String getInvoice_num_used() {
		return invoice_num_used;
	}
	public void setInvoice_num_used(String invoice_num_used) {
		this.invoice_num_used = invoice_num_used;
	}
	public String getInvoice_class() {
		return invoice_class;
	}
	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getIs_use_all() {
		return is_use_all;
	}
	public void setIs_use_all(String is_use_all) {
		this.is_use_all = is_use_all;
	}
	public String getUser_invoice_num_length() {
		return user_invoice_num_length;
	}
	public void setUser_invoice_num_length(String user_invoice_num_length) {
		this.user_invoice_num_length = user_invoice_num_length;
	}
}
