package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "pos_detail")
public class PosDetailCharge {
	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name = "pay_way")
	 private int pay_way;  //收费方式
	 
	 @Column(name = "peis_trade_code")
	 private String peis_trade_code; //体检交易流水号
	 
	 @Column(name = "amount")
	 private double amount;  // 交易金额
	 
	 @Column(name = "daily_status")
	 private  String daily_status;  // 日结状态 0 未日结 、1以日结 
	 
	 @Column(name = "daily_time")
	 private Date daily_time;   //日结日期
	 
	 @Column(name = "pos_type")
	 private  int  pos_type;   // pos类型 1 银联支付、 2 社保支付
	 
	 @Column(name="trans_code")
	 private String trans_code;   //00 收费 02 退费
	 
	 
	 @Column(name = "pos_daily_summary_id")  // 总表id
	 private long pos_daily_summary_id;   
	 
	 @Column(name = "trade_no")  // 参考号
	 private String trade_no;
	 
	 @Column(name = "voucher_no")  // 凭证号
	 private String voucher_no;
	 
	public long getId() {
		return id;
	}

	public int getPay_way() {
		return pay_way;
	}

	public String getPeis_trade_code() {
		return peis_trade_code;
	}

	public double getAmount() {
		return amount;
	}

	public String getDaily_status() {
		return daily_status;
	}

	public int getPos_type() {
		return pos_type;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setPay_way(int pay_way) {
		this.pay_way = pay_way;
	}

	public void setPeis_trade_code(String peis_trade_code) {
		this.peis_trade_code = peis_trade_code;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDaily_time() {
		return daily_time;
	}

	public void setDaily_time(Date daily_time) {
		this.daily_time = daily_time;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}

	public void setPos_type(int pos_type) {
		this.pos_type = pos_type;
	}

	public long getPos_daily_summary_id() {
		return pos_daily_summary_id;
	}

	public void setPos_daily_summary_id(long pos_daily_summary_id) {
		this.pos_daily_summary_id = pos_daily_summary_id;
	}

	public String getTrans_code() {
		return trans_code;
	}

	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}

	public String getTrade_no() {
		return trade_no;
	}

	public String getVoucher_no() {
		return voucher_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public void setVoucher_no(String voucher_no) {
		this.voucher_no = voucher_no;
	}
	
}
