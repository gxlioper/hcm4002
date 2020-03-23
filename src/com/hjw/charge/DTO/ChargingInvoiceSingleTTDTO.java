package com.hjw.charge.DTO;

public class ChargingInvoiceSingleTTDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	
	private String title_info;
	
	private String invoice_type;
	
	private String invoice_num;
	
	private double invoice_amount;
	
	private String invoice_status;
	
	private int invoice_maker;
	
	private String invoice_time;
	
	private int canceller;
	
	private String cancel_time;
	
	private int creater;
	
	private String create_time;
	
	private int updater;
	
	private String update_time;
	
	private long barch_id;
	
	private String account_num="";
	
	private String exam_type;
	
	private String center_num;
	
	private String contract_num;	
	
	private String creater_name;
	
	private long invoice_id;
	
	public long getBarch_id() {
		return barch_id;
	}

	public void setBarch_id(long barch_id) {
		this.barch_id = barch_id;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getContract_num() {
		return contract_num;
	}

	public void setContract_num(String contract_num) {
		this.contract_num = contract_num;
	}

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

	public double getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
	}

	public int getInvoice_maker() {
		return invoice_maker;
	}

	public void setInvoice_maker(int invoice_maker) {
		this.invoice_maker = invoice_maker;
	}

	public String getInvoice_time() {
		return invoice_time;
	}

	public void setInvoice_time(String invoice_time) {
		this.invoice_time = invoice_time;
	}

	public int getCanceller() {
		return canceller;
	}

	public void setCanceller(int canceller) {
		this.canceller = canceller;
	}

	public String getCancel_time() {
		return cancel_time;
	}

	public void setCancel_time(String cancel_time) {
		this.cancel_time = cancel_time;
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getUpdater() {
		return updater;
	}

	public void setUpdater(int updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getCreater_name() {
		return creater_name;
	}

	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}
	
}
