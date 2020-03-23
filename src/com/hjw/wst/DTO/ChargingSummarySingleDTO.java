package com.hjw.wst.DTO;

import java.util.List;

public class ChargingSummarySingleDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	private long exam_id;
	private long invoice_id;
	private String charging_status;
	private String charging_status_y;
	private Double amount1;
	private Double amount;
	private Double discount;
	private String is_print_recepit;
	private String cashier;
	private String cash_date;
	private String req_num;
	private String is_active;
	private String creater;
	private String create_time;
	private String updater;
	private String update_time;
	private List<ChargingWayDTO> chargingWay;
	private List<CardDealDTO> card_deal;
	private int item_num;
	
	public int getItem_num() {
		return item_num;
	}
	public void setItem_num(int item_num) {
		this.item_num = item_num;
	}
	public List<CardDealDTO> getCard_deal() {
		return card_deal;
	}
	public void setCard_deal(List<CardDealDTO> card_deal) {
		this.card_deal = card_deal;
	}
	public List<ChargingWayDTO> getChargingWay() {
		return chargingWay;
	}
	public void setChargingWay(List<ChargingWayDTO> chargingWay) {
		this.chargingWay = chargingWay;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExam_id() {
		return exam_id;
	}
	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
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
		if(charging_status.equals("M")){
			this.setCharging_status_y("退费");
		}else if(charging_status.equals("Y")){
			this.setCharging_status_y("收费");
		}else if(charging_status.equals("N")){
			this.setCharging_status_y("撤销收费");
		}else if(charging_status.equals("R")){
			this.setCharging_status_y("预付费");
		}
	}
	public String getCharging_status_y() {
		return charging_status_y;
	}
	public void setCharging_status_y(String charging_status_y) {
		this.charging_status_y = charging_status_y;
	}
	public Double getAmount1() {
		return amount1;
	}
	public void setAmount1(Double amount1) {
		this.amount1 = amount1;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getDiscount() {
		return discount;
	}
	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	public String getIs_print_recepit() {
		return is_print_recepit;
	}
	public void setIs_print_recepit(String is_print_recepit) {
		this.is_print_recepit = is_print_recepit;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	public String getCash_date() {
		return cash_date;
	}
	public void setCash_date(String cash_date) {
		this.cash_date = cash_date;
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
	public String getReq_num() {
		return req_num;
	}
	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
}
