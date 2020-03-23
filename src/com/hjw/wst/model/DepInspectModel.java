package com.hjw.wst.model;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.BSRCorrectionValueDTO;
import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.DepExamResultDTO;

public class DepInspectModel implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private long company_id;
	private String exam_num;
	private String time1;
	private String time2;
	private String custname;
	private String chkItem = "";
	private String update_time = "";
	private String status;
	private String employeeID;
	private String arch_num;
	private String id_num;
	private String sex;
	private long age;
	private String com_name = "";
	private String join_date = "";
	private String company;// 公司信息
	private String exam_status;// 检查状态
	private String past_medical_history;// 既往史
	private String customer_type;// 人员类型
	private String item_category;// 项目类型
	private String doctor_name;
	private String exam_date1;
	private String exam_date2;
	private String exam_type_code;//体检类型编码
	private String isCheckSensvitiveWord;

	// 新增
	private String user_name;
	private String phone;
	private String set_name;
	private String final_doctor;
	private String approve_doctor;
	private String exam_result;// 重用词

	// -----------检查科室结论保存-----
	private long exam_info_id;
	private String exam_doctor;
	private long dep_id;
	private String exam_result_summary;
	private String suggestion;
	private String center_num;
	private long approver;
	private String approve_date;
	private long creater;
	private String create_time;
	private long updater;
	private String result_update_time;
	private String Special_setup;
	private String exam_item_id;
	private String type_name;
	
	private String charing_ids;
	
	private long c_id;
	
	private String resultLists;
	private List<DepExamResultDTO> resultList;
	private String exam_num_x;
	
	private String picture_path;
	private String is_departinspect_summary_edit;
	private String is_dep_edit_questionnaire;
	private String app_type;
	private String report_print_type;
	private String zyb_report_print_type;
	
	private String customer_type_special;//信息显示为特殊颜色的人员类别id，逗号隔开（登记台、科室体检、总检）
	
	private String customer_type_special_color;//特殊人员类别信息显示的颜色（登记台、科室体检、总检）
	
	private String occutypeofworkid;
	private String occusectorid;
	private String occusector;
	private String occutypeofwork;
	private String joinDatetime;
	private long employeeage;
	private long damage;
	private String is_depinspect_checked;
	private String item_num;
	
	private List<CommonExamDetailDTO> detailList = new ArrayList<CommonExamDetailDTO>();
	
	private String data_code_children;   //体检类型
	
	private String exam_type;
	
	private long inputter;
	
	private int vipsigin;
	
	private String dep_num;
	
	private String type;
	
	private String charging_item_code;//收费项目编码
	private String is_custom_identification;//人员类型配置
	private String is_update_critical;
	private String item_code;
	private String carPdf_button;
	private String isExamResultDetailDoctorPageShow;

	public String getCarPdf_button() {
		return carPdf_button;
	}

	public void setCarPdf_button(String carPdf_button) {
		this.carPdf_button = carPdf_button;
	}

	public String getIsExamResultDetailDoctorPageShow() {
		return isExamResultDetailDoctorPageShow;
	}

	public void setIsExamResultDetailDoctorPageShow(String isExamResultDetailDoctorPageShow) {
		this.isExamResultDetailDoctorPageShow = isExamResultDetailDoctorPageShow;
	}

	BSRCorrectionValueDTO correctionValue;
	
	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getIs_update_critical() {
		return is_update_critical;
	}

	public void setIs_update_critical(String is_update_critical) {
		this.is_update_critical = is_update_critical;
	}

	public String getItem_num() {
		return item_num;
	}

	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}

	public String getIs_depinspect_checked() {
		return is_depinspect_checked;
	}

	public void setIs_depinspect_checked(String is_depinspect_checked) {
		this.is_depinspect_checked = is_depinspect_checked;
	}

	public String getDep_num() {
		return dep_num;
	}

	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}

	public int getVipsigin() {
		return vipsigin;
	}

	public void setVipsigin(int vipsigin) {
		this.vipsigin = vipsigin;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	
	public long getInputter() {
		return inputter;
	}

	public void setInputter(long inputter) {
		this.inputter = inputter;
	}
	
	public String getIs_dep_edit_questionnaire() {
		return is_dep_edit_questionnaire;
	}

	public void setIs_dep_edit_questionnaire(String is_dep_edit_questionnaire) {
		this.is_dep_edit_questionnaire = is_dep_edit_questionnaire;
	}
    
    public String getData_code_children() {
		return data_code_children;
	}

	public void setData_code_children(String data_code_children) {
		this.data_code_children = data_code_children;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
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

	public String getExam_num_x() {
		return exam_num_x;
	}

	public void setExam_num_x(String exam_num_x) {
		this.exam_num_x = exam_num_x;
	}

	public long getC_id() {
		return c_id;
	}

	public void setC_id(long c_id) {
		this.c_id = c_id;
	}

	public String getResultLists() {
		return resultLists;
	}

	public void setResultLists(String resultLists) {
		this.resultLists = resultLists;
	}

	public List<DepExamResultDTO> getResultList() {
		return resultList;
	}

	public void setResultList(List<DepExamResultDTO> resultList) {
		this.resultList = resultList;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(String exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
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

	public String getCustname() {
		return custname;
	}

	public void setCustname(String custname) {
		this.custname = custname;
	}

	public String getChkItem() {
		return chkItem;
	}

	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
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

	public String getExam_status() {
		return exam_status;
	}

	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
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

	public String getIsCheckSensvitiveWord() {
		return isCheckSensvitiveWord;
	}

	public void setIsCheckSensvitiveWord(String isCheckSensvitiveWord) {
		this.isCheckSensvitiveWord = isCheckSensvitiveWord;
	}

	public String getItem_category() {
		return item_category;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSet_name() {
		return set_name;
	}

	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}

	public String getFinal_doctor() {
		return final_doctor;
	}

	public void setFinal_doctor(String final_doctor) {
		this.final_doctor = final_doctor;
	}

	public String getApprove_doctor() {
		return approve_doctor;
	}

	public void setApprove_doctor(String approve_doctor) {
		this.approve_doctor = approve_doctor;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public String getExam_doctor() {
		return exam_doctor;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getExam_result_summary() {
		return exam_result_summary;
	}

	public void setExam_result_summary(String exam_result_summary) {
		this.exam_result_summary = exam_result_summary;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public long getApprover() {
		return approver;
	}

	public void setApprover(long approver) {
		this.approver = approver;
	}

	public String getApprove_date() {
		return approve_date;
	}

	public void setApprove_date(String approve_date) {
		this.approve_date = approve_date;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public String getResult_update_time() {
		return result_update_time;
	}

	public void setResult_update_time(String result_update_time) {
		this.result_update_time = result_update_time;
	}

	public String getSpecial_setup() {
		return Special_setup;
	}

	public void setSpecial_setup(String special_setup) {
		Special_setup = special_setup;
	}

	public String getCharing_ids() {
		return charing_ids;
	}

	public void setCharing_ids(String charing_ids) {
		this.charing_ids = charing_ids;
	}

	public List<CommonExamDetailDTO> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<CommonExamDetailDTO> detailList) {
		this.detailList = detailList;
	}

	public String getExam_type_code() {
		return exam_type_code;
	}

	public void setExam_type_code(String exam_type_code) {
		this.exam_type_code = exam_type_code;
	}

	public String getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}

	public String getIs_departinspect_summary_edit() {
		return is_departinspect_summary_edit;
	}

	public void setIs_departinspect_summary_edit(String is_departinspect_summary_edit) {
		this.is_departinspect_summary_edit = is_departinspect_summary_edit;
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

	public String getCustomer_type_special() {
		return customer_type_special;
	}

	public void setCustomer_type_special(String customer_type_special) {
		this.customer_type_special = customer_type_special;
	}

	public String getCustomer_type_special_color() {
		return customer_type_special_color;
	}

	public void setCustomer_type_special_color(String customer_type_special_color) {
		this.customer_type_special_color = customer_type_special_color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public BSRCorrectionValueDTO getCorrectionValue() {
		return correctionValue;
	}

	public void setCorrectionValue(BSRCorrectionValueDTO correctionValue) {
		this.correctionValue = correctionValue;
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
