package com.hjw.wst.DTO;

public class TeamExaminfoChargingCEDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String user_name="";
	private String sex="";
	private long age;
	private String id_num;
	private String item_code="";
	private String item_name="";
	private long charging_item_id;
    private long chargingcount;
    private double amount;
    private String exam_num="";   
    private String com_name="";    
    private long id;
    
   	public long getId() {
   		return id;
   	}
   	public void setId(long id) {
   		this.id = id;
   	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
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
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
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
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public long getChargingcount() {
		return chargingcount;
	}
	public void setChargingcount(long chargingcount) {
		this.chargingcount = chargingcount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}

