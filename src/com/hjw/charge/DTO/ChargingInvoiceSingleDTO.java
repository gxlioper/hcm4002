package com.hjw.charge.DTO;

public class ChargingInvoiceSingleDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	
	private String title_info;
	
	private String invoice_type;
	
	private String invoice_num;
	
	private Double invoice_amount;
	
	private String invoice_status;
	
	private String invoice_statuss;
	
	private String invoice_maker;
	
	private String invoice_time;
	
	private String canceller;
	
	private String cancel_time;
	
	private String creater;
	
	private String create_time;
	
	private String updater;
	
	private String update_time;
	
	private String exam_type;
	
	private String exam_types;
	
	private String invoice_class;
	
	private String account_num;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle_info() {
		return title_info;
	}

	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}

	public String getInvoice_type() {
		return invoice_type;
	}

	public void setInvoice_type(String invoice_type) {
		this.invoice_type = invoice_type;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public Double getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(Double invoice_amount) {
		this.invoice_amount = invoice_amount;
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

	public String getInvoice_maker() {
		return invoice_maker;
	}

	public void setInvoice_maker(String invoice_maker) {
		this.invoice_maker = invoice_maker;
	}

	public String getInvoice_time() {
		return invoice_time;
	}

	public void setInvoice_time(String invoice_time) {
		this.invoice_time = invoice_time;
	}

	public String getCanceller() {
		return canceller;
	}

	public void setCanceller(String canceller) {
		this.canceller = canceller;
	}

	public String getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdater() {
		return updater;
	}

	public void setUpdater(String updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getInvoice_statuss() {
		return invoice_statuss;
	}

	public void setInvoice_statuss(String invoice_statuss) {
		this.invoice_statuss = invoice_statuss;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
		if("G".equals(exam_type)){
			this.exam_types = "个人";
		}else if("T".equals(exam_type)){
			this.exam_types = "团体";
		}else if("R".equals(exam_type)){
			this.exam_types = "商户预结";
		}else{
			this.exam_types = "";
		}
	}

	public String getExam_types() {
		return exam_types;
	}

	public void setExam_types(String exam_types) {
		this.exam_types = exam_types;
	}

	public String getInvoice_class() {
		return invoice_class;
	}

	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	
}
