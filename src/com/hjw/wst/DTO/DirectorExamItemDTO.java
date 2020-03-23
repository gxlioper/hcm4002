package com.hjw.wst.DTO;

public class DirectorExamItemDTO {

	private long id;
	private String exam_num;
	private String status;
	private String statuss;
	private String join_date;
	private long age;
	private String phone;
	private String company;
	private String user_name;
	private String sex;
	private String set_name;
	
	private long charge_item_id;
	private String item_name;
	private String pay_status;//结算状态 
	private String pay_statuss;

	private String exam_status;//检查状态
	private String exam_statuss;
	private String exam_date;
	private String exam_doctor_name;
	
	private String dep_category;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
		if ("Y".equals(status)){
			this.setStatuss("Y预约");
		}else if ("D".equals(status)){
			this.setStatuss("D登记");
		}else if ("J".equals(status)){
			this.setStatuss("J检查中");
		}else if ("Z".equals(status)){
			this.setStatuss("Z已终检");
		}else{
			this.setStatuss("未知");
		}
	}
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
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
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
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
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public long getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(long charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
		//个人（N未付费，Y 已付费）团体（R 预付费）
		if(pay_status==null){
			this.setPay_statuss("未知");
		}else if("Y".equals(pay_status)){
			this.setPay_statuss("已付费");
		}else if("R".equals(pay_status)){
			this.setPay_statuss("预付费");
		}else if("N".equals(pay_status)){
			this.setPay_statuss("未付费");
		}else {
			this.setPay_statuss("未知");
		}
	}
	public String getPay_statuss() {
		return pay_statuss;
	}
	public void setPay_statuss(String pay_statuss) {
		this.pay_statuss = pay_statuss;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
		//N未检查 Y已检查 G弃检 C已登记，D延期
		if(exam_status==null){
			this.setExam_statuss("未知");
		}else if("Y".equals(exam_status)){
			this.setExam_statuss("已检查");
		}else if("G".equals(exam_status)){
			this.setExam_statuss("弃检");
		}else if("C".equals(exam_status)){
			this.setExam_statuss("已登记");
		}else if("D".equals(exam_status)){
			this.setExam_statuss("延期");
		}else if("N".equals(exam_status)){
			this.setExam_statuss("未检查");
		}else {
			this.setExam_statuss("未知");
		}
	}
	public String getExam_statuss() {
		return exam_statuss;
	}
	public void setExam_statuss(String exam_statuss) {
		this.exam_statuss = exam_statuss;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getExam_doctor_name() {
		return exam_doctor_name;
	}
	public void setExam_doctor_name(String exam_doctor_name) {
		this.exam_doctor_name = exam_doctor_name;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
}
