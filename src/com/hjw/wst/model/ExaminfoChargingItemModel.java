package com.hjw.wst.model;

import java.util.Date;


public class ExaminfoChargingItemModel implements java.io.Serializable {

	private static final long serialVersionUID = -8329309504856348803L;
	private long id;
	private long examinfo_id;
	private long charge_item_id;
	private String exam_indicator;
	private double item_amount;
	private double discount;
	private double amount;
	private String isActive;
	private Date final_exam_date;
	private String pay_status;
	private String exam_status;
	private long is_new_added;
	private Date exam_date;
	private long creater;
	private Date create_time;
	private long updater;
	private Date update_time;
	private long check_status;
	private long exam_doctor_id;
	private String exam_doctor_name;
	private String add_status;
	private double calculation_amount;
	private String is_application;
	private String change_item;
	private double team_pay;
	private double personal_pay;
	private String team_pay_status;
	private String exam_num;
	private String examtype;
	private String time1;
	private String time2;
	private long company_id;
	private String user_name;
	private String status;
	private String employeeID;
	private String arch_num;
	private String id_num;	
	
	public String getExamtype() {
		return examtype;
	}
	public void setExamtype(String examtype) {
		this.examtype = examtype;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExaminfo_id() {
		return examinfo_id;
	}
	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}
	public long getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(long charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	public String getExam_indicator() {
		return exam_indicator;
	}
	public void setExam_indicator(String exam_indicator) {
		this.exam_indicator = exam_indicator;
	}
	public double getItem_amount() {
		return item_amount;
	}
	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public Date getFinal_exam_date() {
		return final_exam_date;
	}
	public void setFinal_exam_date(Date final_exam_date) {
		this.final_exam_date = final_exam_date;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public long getIs_new_added() {
		return is_new_added;
	}
	public void setIs_new_added(long is_new_added) {
		this.is_new_added = is_new_added;
	}
	public Date getExam_date() {
		return exam_date;
	}
	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public long getCheck_status() {
		return check_status;
	}
	public void setCheck_status(long check_status) {
		this.check_status = check_status;
	}
	public long getExam_doctor_id() {
		return exam_doctor_id;
	}
	public void setExam_doctor_id(long exam_doctor_id) {
		this.exam_doctor_id = exam_doctor_id;
	}
	public String getExam_doctor_name() {
		return exam_doctor_name;
	}
	public void setExam_doctor_name(String exam_doctor_name) {
		this.exam_doctor_name = exam_doctor_name;
	}
	public String getAdd_status() {
		return add_status;
	}
	public void setAdd_status(String add_status) {
		this.add_status = add_status;
	}
	public double getCalculation_amount() {
		return calculation_amount;
	}
	public void setCalculation_amount(double calculation_amount) {
		this.calculation_amount = calculation_amount;
	}
	public String getIs_application() {
		return is_application;
	}
	public void setIs_application(String is_application) {
		this.is_application = is_application;
	}
	public String getChange_item() {
		return change_item;
	}
	public void setChange_item(String change_item) {
		this.change_item = change_item;
	}
	public double getTeam_pay() {
		return team_pay;
	}
	public void setTeam_pay(double team_pay) {
		this.team_pay = team_pay;
	}
	public double getPersonal_pay() {
		return personal_pay;
	}
	public void setPersonal_pay(double personal_pay) {
		this.personal_pay = personal_pay;
	}
	public String getTeam_pay_status() {
		return team_pay_status;
	}
	public void setTeam_pay_status(String team_pay_status) {
		this.team_pay_status = team_pay_status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
}
