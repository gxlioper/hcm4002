package com.hjw.wst.model;

import java.util.Date;
import java.util.List;

import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.wst.DTO.CashierDailyAccPaywayDTO;

public class CashierDailyAccModel implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private String daily_acc_num; //日结单号
	private long userId; //日结人
	private String daily_acc_date1; //日结时间
	private String daily_acc_date2;
	private double daily_acc_amount; // 日结总金额
	private String is_Active; //日结状态
	private String pos_code;
	private String trans_code;
	private String pay_way;
	
	private List<CashierDailyAccListDTO> gdailyList;
	private String gdailyLists;
	
	private List<CashierDailyAccListDTO> tdailyList;
	private String tdailyLists;
	
	private List<CashierDailyAccPaywayDTO> dailyPayway;
	private String dailyPayways;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDaily_acc_num() {
		return daily_acc_num;
	}
	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getDaily_acc_date1() {
		return daily_acc_date1;
	}
	public void setDaily_acc_date1(String daily_acc_date1) {
		this.daily_acc_date1 = daily_acc_date1;
	}
	public String getDaily_acc_date2() {
		return daily_acc_date2;
	}
	public void setDaily_acc_date2(String daily_acc_date2) {
		this.daily_acc_date2 = daily_acc_date2;
	}
	public double getDaily_acc_amount() {
		return daily_acc_amount;
	}
	public void setDaily_acc_amount(double daily_acc_amount) {
		this.daily_acc_amount = daily_acc_amount;
	}
	public String getIs_Active() {
		return is_Active;
	}
	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}
	
	public List<CashierDailyAccPaywayDTO> getDailyPayway() {
		return dailyPayway;
	}
	public void setDailyPayway(List<CashierDailyAccPaywayDTO> dailyPayway) {
		this.dailyPayway = dailyPayway;
	}
	public String getDailyPayways() {
		return dailyPayways;
	}
	public void setDailyPayways(String dailyPayways) {
		this.dailyPayways = dailyPayways;
	}
	public List<CashierDailyAccListDTO> getGdailyList() {
		return gdailyList;
	}
	public void setGdailyList(List<CashierDailyAccListDTO> gdailyList) {
		this.gdailyList = gdailyList;
	}
	public String getGdailyLists() {
		return gdailyLists;
	}
	public void setGdailyLists(String gdailyLists) {
		this.gdailyLists = gdailyLists;
	}
	public List<CashierDailyAccListDTO> getTdailyList() {
		return tdailyList;
	}
	public void setTdailyList(List<CashierDailyAccListDTO> tdailyList) {
		this.tdailyList = tdailyList;
	}
	public String getTdailyLists() {
		return tdailyLists;
	}
	public void setTdailyLists(String tdailyLists) {
		this.tdailyLists = tdailyLists;
	}
	public String getPos_code() {
		return pos_code;
	}
	public String getTrans_code() {
		return trans_code;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPos_code(String pos_code) {
		this.pos_code = pos_code;
	}
	public void setTrans_code(String trans_code) {
		this.trans_code = trans_code;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	
}