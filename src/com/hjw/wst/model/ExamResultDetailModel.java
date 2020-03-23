package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.ExamResultDetailDTO;

public class ExamResultDetailModel implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	private String exam_num;
	private long examinfo_id;
	private long charging_id;
	private String exam_status;
	
	private String examResulLists;
	private List<ExamResultDetailDTO> examResulList;
	
	private String time1;
	private String time2;
	private long company_id;
	private String user_name;
	private String status;
	private String employeeID;
	private String arch_num;
	private String id_num;
	private String doctor_name;
	private String exam_date1;
	private String exam_date2;
	private long dep_id;
	private String dep_num;
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
	private long pace_id;
	
	private String exam_id_1;//条件项目
	private String exam_id_2;
	private String exam_id_3;
	private String exam_id_4;
	private String exam_id_5;
	private String exam_id_6;
	private String s1;//比较
	private String s2;
	private String s3;
	private String s4;
	private String s5;
	private String s6;
	
	private String value_1;//值
	private String value_2;
	private String value_3;
	private String value_4;
	private String value_5;
	private String value_6;
	private long id;
	private String positive_find;
	
	private String app_type;
	private String report_print_type;
	private String zyb_report_print_type;
	
	private String picture_path;
	private String isExamResultDetailDoctorPageShow;
	
	private String exam_type;
	private long inputter;
	private String charging_item_code;
	private String is_custom_identification;
	private String is_update_critical;
	
	public String getIs_update_critical() {
		return is_update_critical;
	}

	public void setIs_update_critical(String is_update_critical) {
		this.is_update_critical = is_update_critical;
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
	
	public String getPositive_find() {
		return positive_find;
	}
	public void setPositive_find(String positive_find) {
		this.positive_find = positive_find;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getPace_id() {
		return pace_id;
	}
	public void setPace_id(long pace_id) {
		this.pace_id = pace_id;
	}
	public String getPicture_path() {
		return picture_path;
	}
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
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
	public List<ExamResultDetailDTO> getExamResulList() {
		return examResulList;
	}
	public void setExamResulList(List<ExamResultDetailDTO> examResulList) {
		this.examResulList = examResulList;
	}
	public long getCharging_id() {
		return charging_id;
	}
	public void setCharging_id(long charging_id) {
		this.charging_id = charging_id;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public String getExamResulLists() {
		return examResulLists;
	}
	public void setExamResulLists(String examResulLists) {
		this.examResulLists = examResulLists;
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
	public String getApp_type() {
		return app_type;
	}
	public void setApp_type(String app_type) {
		this.app_type = app_type;
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
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getIsExamResultDetailDoctorPageShow() {
		return isExamResultDetailDoctorPageShow;
	}
	public void setIsExamResultDetailDoctorPageShow(String isExamResultDetailDoctorPageShow) {
		this.isExamResultDetailDoctorPageShow = isExamResultDetailDoctorPageShow;
	}
	public String getExam_id_1() {
		return exam_id_1;
	}
	public void setExam_id_1(String exam_id_1) {
		this.exam_id_1 = exam_id_1;
	}
	public String getExam_id_2() {
		return exam_id_2;
	}
	public void setExam_id_2(String exam_id_2) {
		this.exam_id_2 = exam_id_2;
	}
	public String getExam_id_3() {
		return exam_id_3;
	}
	public void setExam_id_3(String exam_id_3) {
		this.exam_id_3 = exam_id_3;
	}
	public String getExam_id_4() {
		return exam_id_4;
	}
	public void setExam_id_4(String exam_id_4) {
		this.exam_id_4 = exam_id_4;
	}
	public String getExam_id_5() {
		return exam_id_5;
	}
	public void setExam_id_5(String exam_id_5) {
		this.exam_id_5 = exam_id_5;
	}
	public String getExam_id_6() {
		return exam_id_6;
	}
	public void setExam_id_6(String exam_id_6) {
		this.exam_id_6 = exam_id_6;
	}
	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}
	public String getS2() {
		return s2;
	}
	public void setS2(String s2) {
		this.s2 = s2;
	}
	public String getS3() {
		return s3;
	}
	public void setS3(String s3) {
		this.s3 = s3;
	}
	public String getS4() {
		return s4;
	}
	public void setS4(String s4) {
		this.s4 = s4;
	}
	public String getS5() {
		return s5;
	}
	public void setS5(String s5) {
		this.s5 = s5;
	}
	public String getS6() {
		return s6;
	}
	public void setS6(String s6) {
		this.s6 = s6;
	}
	public String getValue_1() {
		return value_1;
	}
	public void setValue_1(String value_1) {
		this.value_1 = value_1;
	}
	public String getValue_2() {
		return value_2;
	}
	public void setValue_2(String value_2) {
		this.value_2 = value_2;
	}
	public String getValue_3() {
		return value_3;
	}
	public void setValue_3(String value_3) {
		this.value_3 = value_3;
	}
	public String getValue_4() {
		return value_4;
	}
	public void setValue_4(String value_4) {
		this.value_4 = value_4;
	}
	public String getValue_5() {
		return value_5;
	}
	public void setValue_5(String value_5) {
		this.value_5 = value_5;
	}
	public String getValue_6() {
		return value_6;
	}
	public void setValue_6(String value_6) {
		this.value_6 = value_6;
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
