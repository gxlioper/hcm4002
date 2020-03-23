package com.hjw.wst.DTO;

public class TeamExaminfoChargingItemDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private long charging_item_id;
	private String prePay;
	private String prePays;
    private double acc_charge;
	private String acc_num="";
	private String center_num="";
	private long creater;
	private String discard;
	private String discards;
	private String exam_num="";
	private String in_date="";
	private double personal_pay;
	private double price;
	private long dep_id;
	private String dep_name="";
	private String item_code="";
	private String item_name="";
	private double rate;	
	private double dec_charges;	
	
	public double getDec_charges() {
		return dec_charges;
	}
	public void setDec_charges(double dec_charges) {
		this.dec_charges = dec_charges;
	}
	public String getPrePays() {
		return prePays;
	}
	public void setPrePays(String prePays) {
		this.prePays = prePays;
	}
	public String getDiscards() {
		return discards;
	}
	public void setDiscards(String discards) {
		this.discards = discards;
	}
	public String getPrePay() {
		return prePay;
	}
	public void setPrePay(String prePay) {
		this.prePay = prePay;
		if("Y".equals(prePay)){
			this.setPrePays("预结");
		}else if("N".equals(prePay)){
			this.setPrePays("非预结");
		}
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public String getDiscard() {
		return discard;
	}
	public void setDiscard(String discard) {
		this.discard = discard;
		if("Y".equals(discard)){
			this.setDiscards("弃检");
		}else if("N".equals(discard)){
			this.setDiscards("非弃检");
		}
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	
	public double getAcc_charge() {
		return acc_charge;
	}
	public void setAcc_charge(double acc_charge) {
		this.acc_charge = acc_charge;
	}
	public String getAcc_num() {
		return acc_num;
	}
	public void setAcc_num(String acc_num) {
		this.acc_num = acc_num;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getIn_date() {
		return in_date;
	}
	public void setIn_date(String in_date) {
		this.in_date = in_date;
	}
	public double getPersonal_pay() {
		return personal_pay;
	}
	public void setPersonal_pay(double personal_pay) {
		this.personal_pay = personal_pay;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

}

