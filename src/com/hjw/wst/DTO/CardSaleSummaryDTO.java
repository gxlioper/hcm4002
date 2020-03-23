package com.hjw.wst.DTO;

public class CardSaleSummaryDTO {

	private String id;
	private String sale_trade_num;//售卡交易流水号
	private long sale_status;//交易状态 1售卡、0预售卡 注：预售卡在操作售卡后修改此状态为1
	private String sale_statuss;
	private long invoice_id;//所开发票ID
	private String invoice_num;
	private String is_print_recepit;//是否开发票 N未开、Y已开
	private Double amount;//卡内总金额
	private Double sale_amount;//售卡总金额
	private long sale_type;//交易类型 1售卡、0预售卡 注：预售卡在操作售卡后不修改此状态
	private String sale_types;
	private String sale_user;//售卡人
	private String sale_time;//售卡时间
	private String advance_sale_user;//预售卡人  直接售卡操作不需要填写
	private String advance_sale_time;//预售卡时间
	private long daily_status;//日结状态 0未日结、1已日结
	private String daily_statuss;
	private String charging_way;
	private long card_info_count;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSale_trade_num() {
		return sale_trade_num;
	}
	public void setSale_trade_num(String sale_trade_num) {
		this.sale_trade_num = sale_trade_num;
	}
	public long getSale_status() {
		return sale_status;
	}
	public void setSale_status(long sale_status) {
		this.sale_status = sale_status;
		if(sale_status == 1){
			this.sale_statuss = "售卡";
		}else if(sale_status == 0){
			this.sale_statuss = "预售卡";
		}
	}
	public String getSale_statuss() {
		return sale_statuss;
	}
	public void setSale_statuss(String sale_statuss) {
		this.sale_statuss = sale_statuss;
	}
	public long getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}
	public String getInvoice_num() {
		return invoice_num;
	}
	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}
	public String getIs_print_recepit() {
		return is_print_recepit;
	}
	public void setIs_print_recepit(String is_print_recepit) {
		this.is_print_recepit = is_print_recepit;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getSale_amount() {
		return sale_amount;
	}
	public void setSale_amount(Double sale_amount) {
		this.sale_amount = sale_amount;
	}
	public long getSale_type() {
		return sale_type;
	}
	public void setSale_type(long sale_type) {
		this.sale_type = sale_type;
		if(sale_type == 1){
			this.sale_types = "售卡";
		}else if(sale_type == 0){
			this.sale_types = "预售卡";
		}
	}
	public String getSale_types() {
		return sale_types;
	}
	public void setSale_types(String sale_types) {
		this.sale_types = sale_types;
	}
	public String getSale_user() {
		return sale_user;
	}
	public void setSale_user(String sale_user) {
		this.sale_user = sale_user;
	}
	public String getSale_time() {
		return sale_time;
	}
	public void setSale_time(String sale_time) {
		this.sale_time = sale_time;
	}
	public String getAdvance_sale_user() {
		return advance_sale_user;
	}
	public void setAdvance_sale_user(String advance_sale_user) {
		this.advance_sale_user = advance_sale_user;
	}
	public String getAdvance_sale_time() {
		return advance_sale_time;
	}
	public void setAdvance_sale_time(String advance_sale_time) {
		this.advance_sale_time = advance_sale_time;
	}
	public long getDaily_status() {
		return daily_status;
	}
	public void setDaily_status(long daily_status) {
		this.daily_status = daily_status;
		if(daily_status == 1){
			this.daily_statuss = "已日结";
		}else{
			this.daily_statuss = "未日结";
		}
	}
	public String getDaily_statuss() {
		return daily_statuss;
	}
	public void setDaily_statuss(String daily_statuss) {
		this.daily_statuss = daily_statuss;
	}
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public long getCard_info_count() {
		return card_info_count;
	}
	public void setCard_info_count(long card_info_count) {
		this.card_info_count = card_info_count;
	}
}
