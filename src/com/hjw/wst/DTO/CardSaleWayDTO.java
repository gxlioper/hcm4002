package com.hjw.wst.DTO;


public class CardSaleWayDTO {
	private String id;
	private String sale_trade_num;//售卡交易主表流水号
	private String charging_way;//收费方式ID
	private Double amount;//金额
	
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
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
