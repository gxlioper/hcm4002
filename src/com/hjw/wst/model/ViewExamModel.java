package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.ViewExamDetailDTO;

public class ViewExamModel {

	private String exam_num;
	private long examinfo_id;
	private String viewExams;
	private List<ViewExamDetailDTO> viewExamList;
	
	private String time1;
	private String time2;
	private long company_id;
	private String user_name;
	private String status;
	private String employeeID;
	private String arch_num;
	private String id_num;
	private String exam_status;
	
	private long sample_id;
	
	private String doctor_name;
	private String exam_date1;
	private String exam_date2;
	
	private String isViewExamImageShow;
	private String sex;
	private long age;
	private String com_name = "";
	private String join_date = "";
	private String company;// 公司信息
	private String past_medical_history;// 既往史
	private String customer_type;// 人员类型
	private String set_name;
	private String type_name;
	
	private String occutypeofworkid;
	private String occusectorid;
	private String occusector;
	private String occutypeofwork;
	private String joinDatetime;
	private long employeeage;
	private long damage;
	
	private String app_type;
	private String picture_path;
	private String report_print_type;
	private String zyb_report_print_type;
	
	private String exam_type;
	private long inputter;
	private long dep_id;
	private String dep_num;
	private String exam_type_code;
	private String charging_item_code;
	private String is_custom_identification;//特殊人员
	private String exam_result;
	private String is_update_critical;
	private String exam_doctor;

	public String getExam_doctor() {
		return exam_doctor;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public String getIs_update_critical() {
		return is_update_critical;
	}

	public void setIs_update_critical(String is_update_critical) {
		this.is_update_critical = is_update_critical;
	}
	
	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getExam_type_code() {
		return exam_type_code;
	}

	public void setExam_type_code(String exam_type_code) {
		this.exam_type_code = exam_type_code;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getDep_num() {
		return dep_num;
	}

	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}

	public long getInputter() {
		return inputter;
	}

	public void setInputter(long inputter) {
		this.inputter = inputter;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	
	public String getPicture_path() {
		return picture_path;
	}
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public long getExaminfo_id() {
		return examinfo_id;
	}

	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
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

	public String getViewExams() {
		return viewExams;
	}

	public void setViewExams(String viewExams) {
		this.viewExams = viewExams;
	}

	public List<ViewExamDetailDTO> getViewExamList() {
		return viewExamList;
	}

	public void setViewExamList(List<ViewExamDetailDTO> viewExamList) {
		this.viewExamList = viewExamList;
	}

	public long getSample_id() {
		return sample_id;
	}

	public void setSample_id(long sample_id) {
		this.sample_id = sample_id;
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

	public String getIsViewExamImageShow() {
		return isViewExamImageShow;
	}

	public void setIsViewExamImageShow(String isViewExamImageShow) {
		this.isViewExamImageShow = isViewExamImageShow;
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

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
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

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public String getIs_custom_identification() {
		return is_custom_identification;
	}

	public void setIs_custom_identification(String is_custom_identification) {
		this.is_custom_identification = is_custom_identification;
	}

	public String getReport_print_type() {
		return report_print_type;
	}

	public String getZyb_report_print_type() {
		return zyb_report_print_type;
	}

	public void setReport_print_type(String report_print_type) {
		this.report_print_type = report_print_type;
	}

	public void setZyb_report_print_type(String zyb_report_print_type) {
		this.zyb_report_print_type = zyb_report_print_type;
	}
	
}
