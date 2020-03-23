package com.hjw.wst.DTO;

public class ChargingSummaryGroupDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	private long batch_id;
	private long invoice_id;
	private String charging_status;
	private String charging_status_y;
	private double discount;
	private String is_print_recepit;
	private String is_print_recepits;
	private long cashier;
	private String cash_date;
	private String account_num;
	private String is_active;
	private long creater;
	private String create_time;
	private long updater;
	private String update_time;
	private String title_info;
	private String invoice_num;
	private double amount1;
	private double amount2;
	private double additional;
	private String contract_num="";
    private int receiv_status;//确认收款 0表示未确认，1表示确认,2表示his申请已发，
    private String receiv_statuss="";
	private long receiver;//
	private String receiv_date="";
	
	public int getReceiv_status() {
		return receiv_status;
	}
	public void setReceiv_status(int receiv_status) {
		this.receiv_status = receiv_status;
		if(receiv_status==1){
			this.setReceiv_statuss("已确认");
		}else if(receiv_status==2){
			this.setReceiv_statuss("已发his");
		}else{
			this.setReceiv_statuss("未确认");
		}
	}
	public String getReceiv_statuss() {
		return receiv_statuss;
	}
	public void setReceiv_statuss(String receiv_statuss) {
		this.receiv_statuss = receiv_statuss;
	}
	public long getReceiver() {
		return receiver;
	}
	public void setReceiver(long receiver) {
		this.receiver = receiver;
	}
	public String getReceiv_date() {
		return receiv_date;
	}
	public void setReceiv_date(String receiv_date) {
		this.receiv_date = receiv_date;
	}
	public double getAdditional() {
		return additional;
	}
	public void setAdditional(double additional) {
		this.additional = additional;
	}
	public String getIs_print_recepits() {
		return is_print_recepits;
	}
	public void setIs_print_recepits(String is_print_recepits) {
		this.is_print_recepits = is_print_recepits;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public String getAccount_num() {
		return account_num;
	}
	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
	public String getTitle_info() {
		return title_info;
	}
	public void setTitle_info(String title_info) {
		this.title_info = title_info;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public double getAmount2() {
		return amount2;
	}
	public void setAmount2(double amount2) {
		this.amount2 = amount2;
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
	
	public long getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}
	public String getCharging_status() {
		return charging_status;
	}
	public void setCharging_status(String charging_status) {
		this.charging_status = charging_status;
		if(charging_status.equals("Y")){
			this.setCharging_status_y("有效");
		}else if(charging_status.equals("N")){
			this.setCharging_status_y("无效");
		}
	}
	public String getCharging_status_y() {
		return charging_status_y;
	}
	public void setCharging_status_y(String charging_status_y) {
		this.charging_status_y = charging_status_y;
	}
	public double getAmount1() {
		return amount1;
	}
	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}
	
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public String getIs_print_recepit() {
		return is_print_recepit;
	}
	public void setIs_print_recepit(String is_print_recepit) {
		this.is_print_recepit = is_print_recepit;
		if(is_print_recepit.equals("Y")){
			this.setIs_print_recepits("已打印");
		}else if(is_print_recepit.equals("N")){
			this.setIs_print_recepits("未打印");
		}
	}
	public long getCashier() {
		return cashier;
	}
	public void setCashier(long cashier) {
		this.cashier = cashier;
	}
	public String getCash_date() {
		return cash_date;
	}
	public void setCash_date(String cash_date) {
		this.cash_date = cash_date;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
}
