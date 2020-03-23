package com.hjw.wst.DTO;

public class CashierDailyAccListDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private String user_name;
	private String sex;
	private String id_num;
	private String exam_num;
	private long age;
	private String phone;
	private String address;
	private double amount1;
	private double amount2;
	private String charging_way;
	private long charging_way_id;
	private double way_amount;
	
	private String com_name;
	private String batch_name;
	
	private String exam_type;
	private String exam_types;
	private String charging_status;
	private String charging_statuss;
	private String cash_date;
	private String cashier;
	private String daily_status;
	private String daily_statuss;
	private double invoice_amount;
	private String req_num;
	private double amount;
	
	public String getReq_num() {
		return req_num;
	}
	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getAmount1() {
		return amount1;
	}
	public void setAmount1(double amount1) {
		this.amount1 = amount1;
	}
	public double getAmount2() {
		return amount2;
	}
	public void setAmount2(double amount2) {
		this.amount2 = amount2;
	}
	public String getCharging_way() {
		return charging_way;
	}
	public void setCharging_way(String charging_way) {
		this.charging_way = charging_way;
	}
	public double getWay_amount() {
		return way_amount;
	}
	public void setWay_amount(double way_amount) {
		this.way_amount = way_amount;
	}
	public long getCharging_way_id() {
		return charging_way_id;
	}
	public void setCharging_way_id(long charging_way_id) {
		this.charging_way_id = charging_way_id;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
		if("G".equals(exam_type)){
			this.exam_types = "个人";
		}else if("T".equals(exam_type)){
			this.exam_types = "团体";
		}
	}
	public String getExam_types() {
		return exam_types;
	}
	public void setExam_types(String exam_types) {
		this.exam_types = exam_types;
	}
	public String getCharging_status() {
		return charging_status;
	}
	public void setCharging_status(String charging_status) {
		this.charging_status = charging_status;
		if("Y".equals(charging_status)){
			this.charging_statuss = "收费";
		}else if("M".equals(charging_status)){
			this.charging_statuss = "退费";
		}else if("N".equals(charging_status)){
			this.charging_statuss = "撤销收费";
		}else if("6".equals(charging_status) || "002".equals(charging_status)){
			this.charging_statuss = "消费";
		}else if("7".equals(charging_status) || "004".equals(charging_status)){
			this.charging_statuss = "撤销消费";
		}else if("001".equals(charging_status)){
			this.charging_statuss = "收费";
		}else if("003".equals(charging_status)){
			this.charging_statuss = "撤销收费";
		}
	}
	public String getCharging_statuss() {
		return charging_statuss;
	}
	public void setCharging_statuss(String charging_statuss) {
		this.charging_statuss = charging_statuss;
	}
	public String getCash_date() {
		return cash_date;
	}
	public void setCash_date(String cash_date) {
		this.cash_date = cash_date;
	}
	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	public String getDaily_status() {
		return daily_status;
	}
	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
		if("0".equals(daily_status)){
			this.daily_statuss = "未日结";
		}else if("1".equals(daily_status)){
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
	public double getInvoice_amount() {
		return invoice_amount;
	}
	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}
}
