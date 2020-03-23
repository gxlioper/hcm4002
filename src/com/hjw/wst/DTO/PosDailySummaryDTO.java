package com.hjw.wst.DTO;

public class PosDailySummaryDTO {
	private String pos_code;
	
	private String trans_code;
	
	private String data_name;
	
	private double pos_charge_amount;
	
	private int pay_way;
	
	private String peis_trade_code;
	
	private double trade_amount;
	
	private String create_time;
	
	private String trade_no;
	
	private String  voucher_no;
	
	private String pay_class;//支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
	
	private String creater;
	
	private String pay_class_s;
	
	private String original_trade_no;//交易流水号
	
	private String original_voucher_no;//交易凭证号
	
	private String empower_code;//授权码
	
	private int pos_type;
	
	private String daily_status;//日结状态
	
	
	private double amount;
	
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}

	public int getPos_type() {
		return pos_type;
	}

	public void setPos_type(int pos_type) {
		this.pos_type = pos_type;
	}

	public String getOriginal_trade_no() {
		return original_trade_no;
	}

	public void setOriginal_trade_no(String original_trade_no) {
		this.original_trade_no = original_trade_no;
	}

	public String getOriginal_voucher_no() {
		return original_voucher_no;
	}

	public void setOriginal_voucher_no(String original_voucher_no) {
		this.original_voucher_no = original_voucher_no;
	}

	public String getEmpower_code() {
		return empower_code;
	}

	public void setEmpower_code(String empower_code) {
		this.empower_code = empower_code;
	}

	public String getPay_class_s() {
		return pay_class_s;
	}

	public void setPay_class_s(String pay_class_s) {
		this.pay_class_s = pay_class_s;
	}

	public String getPeis_trade_code() {
		return peis_trade_code;
	}

	public double getTrade_amount() {
		return trade_amount;
	}

	public String getCreate_time() {
		return create_time;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public String getVoucher_no() {
		return voucher_no;
	}

	public void setPeis_trade_code(String peis_trade_code) {
		this.peis_trade_code = peis_trade_code;
	}

	public void setTrade_amount(double trade_amount) {
		this.trade_amount = trade_amount;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}

	public int getPay_way() {
		return pay_way;
	}

	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	public String getTrans_code() {
		return trans_code;
	}

	public String getData_name() {
		return data_name;
	}

	public double getPos_charge_amount() {
		return pos_charge_amount;
	}

	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}

	public void setData_name(String data_name) {
		this.data_name = data_name;
	}

	public void setPos_charge_amount(double pos_charge_amount) {
		this.pos_charge_amount = pos_charge_amount;
	}

	public String getPos_code() {
		return pos_code;
	}

	public void setPos_code(String pos_code) {
		this.pos_code = pos_code;
	}

	public String getPay_class() {
		return pay_class;
	}

	public void setPay_class(String pay_class) {//01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
		this.pay_class = pay_class;
		if ("01".equals(pay_class)) {
			this.setPay_class_s("个检缴费");
		}else if("02".equals(pay_class)){
			this.setPay_class_s("卡充值");
		}else if("03".equals(pay_class)){
			this.setPay_class_s("团体预付费");
		}else if("04".equals(pay_class)){
			this.setPay_class_s("团体缴费");
		}
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}
	

}
