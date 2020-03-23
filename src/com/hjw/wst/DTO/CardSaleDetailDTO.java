package com.hjw.wst.DTO;


public class CardSaleDetailDTO {
	private String id;
	private String sale_trade_num;//售卡交易主表流水号
	private String card_num;//卡号
	private Double amount;//卡内金额
	private Double sale_amount;//售卡金额
	
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
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
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
}
