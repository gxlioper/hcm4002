package com.hjw.zyb.model;

import java.util.List;

import com.hjw.zyb.DTO.ZybInquisitionResultDTO;

public class ZybInquisitionResultModel {
	
	private long id;
	private long examinfo_id;
	private String exam_num;
	
	private String time1;
	private String time2;
	private long company_id;
	private String user_name;
	private String status;
	private String employeeID;
	private String arch_num;
	private String id_num;
	private String exam_status;
	private String doctor_name;
	private String exam_date1;
	private String exam_date2;
	private String customer_type;// 人员类型
	
	private String sex;
	private long age;
	private String com_name = "";
	private String join_date = "";
	private String company;// 公司信息
	private String past_medical_history;// 既往史
	private String set_name;
	private String type_name;
	private String occutypeofworkid;
	private String occusectorid;
	private String occusector;
	private String occutypeofwork;
	private String joinDatetime;
	private long employeeage;
	private long damage;
	private String picture_path;
	private String data_code_children;
	
	private List<ZybInquisitionResultDTO> inquisitionResultList;
	private String inquisitionResults;
	private String barcode_print_type;
	private String zyb_barcode_print_type;
	//==================职业史===============
	private String  workshop;
	private String  worktype;
	private String  measure;
	private String  harmname;
	private String  startdate;
	private String  enddate;
	private String concentrations;
	
	
	public String getBarcode_print_type() {
		return barcode_print_type;
	}
	public void setBarcode_print_type(String barcode_print_type) {
		this.barcode_print_type = barcode_print_type;
	}
	public String getZyb_barcode_print_type() {
		return zyb_barcode_print_type;
	}
	public void setZyb_barcode_print_type(String zyb_barcode_print_type) {
		this.zyb_barcode_print_type = zyb_barcode_print_type;
	}
	public String getWorkshop() {
		return workshop;
	}
	public void setWorkshop(String workshop) {
		this.workshop = workshop;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getMeasure() {
		return measure;
	}
	public void setMeasure(String measure) {
		this.measure = measure;
	}
	public String getHarmname() {
		return harmname;
	}
	public void setHarmname(String harmname) {
		this.harmname = harmname;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getConcentrations() {
		return concentrations;
	}
	public void setConcentrations(String concentrations) {
		this.concentrations = concentrations;
	}
	public String getData_code_children() {
		return data_code_children;
	}
	public void setData_code_children(String data_code_children) {
		this.data_code_children = data_code_children;
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
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
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
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public String getDoctor_name() {
		return doctor_name;
	}
	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}
	public String getExam_date1() {
		return exam_date1;
	}
	public void setExam_date1(String exam_date1) {
		this.exam_date1 = exam_date1;
	}
	public String getExam_date2() {
		return exam_date2;
	}
	public void setExam_date2(String exam_date2) {
		this.exam_date2 = exam_date2;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public List<ZybInquisitionResultDTO> getInquisitionResultList() {
		return inquisitionResultList;
	}
	public void setInquisitionResultList(List<ZybInquisitionResultDTO> inquisitionResultList) {
		this.inquisitionResultList = inquisitionResultList;
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
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPast_medical_history() {
		return past_medical_history;
	}
	public void setPast_medical_history(String past_medical_history) {
		this.past_medical_history = past_medical_history;
	}
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getOccutypeofworkid() {
		return occutypeofworkid;
	}
	public void setOccutypeofworkid(String occutypeofworkid) {
		this.occutypeofworkid = occutypeofworkid;
	}
	public String getOccusectorid() {
		return occusectorid;
	}
	public void setOccusectorid(String occusectorid) {
		this.occusectorid = occusectorid;
	}
	public String getOccusector() {
		return occusector;
	}
	public void setOccusector(String occusector) {
		this.occusector = occusector;
	}
	public String getOccutypeofwork() {
		return occutypeofwork;
	}
	public void setOccutypeofwork(String occutypeofwork) {
		this.occutypeofwork = occutypeofwork;
	}
	public String getJoinDatetime() {
		return joinDatetime;
	}
	public void setJoinDatetime(String joinDatetime) {
		this.joinDatetime = joinDatetime;
	}
	public long getEmployeeage() {
		return employeeage;
	}
	public void setEmployeeage(long employeeage) {
		this.employeeage = employeeage;
	}
	public long getDamage() {
		return damage;
	}
	public void setDamage(long damage) {
		this.damage = damage;
	}
	public String getPicture_path() {
		return picture_path;
	}
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
	public String getInquisitionResults() {
		return inquisitionResults;
	}
	public void setInquisitionResults(String inquisitionResults) {
		this.inquisitionResults = inquisitionResults;
	}
}
