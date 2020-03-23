package com.hjw.wst.DTO;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WebSocketPosSendDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private String inter_class;//接口类型(01:银联POS通;02:市医保;03:省医保)
	
	private String trade_class; //交易类型(00:银行卡;01:POS通)
	
	private String trade_op_code; //POS交易操作类型(05:签到)
	private String pay_class;//支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
	private String peis_trade_code;//体检交易流水号
	private long operator_id;//操作员ID
	private String operator_code; //操作员工号
	private double amount;//金额
	private String trade_date;//原交易日期(yyyymmdd格式，退货时用，其他交易空格)
	private String trade_no="            ";//原交易参考号
	private String voucher_no="      ";//原凭证号
	private String qrcode;   //POS通串码(微信、支付宝码)
	public String getInter_class() {
		return inter_class;
	}
	public void setInter_class(String inter_class) {
		this.inter_class = inter_class;
	}
	public String getTrade_class() {
		return trade_class;
	}
	public void setTrade_class(String trade_class) {
		this.trade_class = trade_class;
	}
	public String getTrade_op_code() {
		return trade_op_code;
	}
	public void setTrade_op_code(String trade_op_code) {
		this.trade_op_code = trade_op_code;
	}
	public String getPay_class() {
		return pay_class;
	}
	public void setPay_class(String pay_class) {
		this.pay_class = pay_class;
	}
	public String getPeis_trade_code() {
		return peis_trade_code;
	}
	public void setPeis_trade_code(String peis_trade_code) {
		this.peis_trade_code = peis_trade_code;
	}
	public long getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(long operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperator_code() {
		return operator_code;
	}
	public void setOperator_code(String operator_code) {
		this.operator_code = operator_code;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	public String getVoucher_no() {
		return voucher_no;
	}
	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}
	public String getQrcode() {
		return qrcode;
	}
	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}
	
}
	

