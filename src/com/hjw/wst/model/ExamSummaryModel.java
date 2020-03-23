package com.hjw.wst.model;

import java.util.Date;
import java.util.List;

import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.zyb.DTO.ZybExamOccuhazardfactorsDTO;

public class ExamSummaryModel implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private String exam_num;
	private long exam_info_id;
	private long item_id;
	private String final_exam_result;
	private String examinfoDiseases;
	private String approve_status;
	private List<ExaminfoDiseaseDTO> examinfoDisease;
	private List<ExaminfoDiseaseDTO> dxexaminfoDiseases;
	private String sug_flag;//N表示加载阳性发现信息,已总检过的加载,未总检过的匹配疾病逻辑。Y表示直接匹配疾病逻辑
	
	private String q;
	
	private long disease_id;
	private long age;
	private String sex;
	
	private String time1;
	private String time2;
	private String final_time1;
	private String final_time2;
	private String seven_time;
	private long company_id;
	private String user_name;
	private String status;
	private String employeeID;
	private String arch_num;
	private String id_num;
	private String exam_status;
	private String exam_doctor;
	private long batch_id;
	
	private String webResource;//资源
	private String isExamSummaryEdit;
	private String report_print_type;
	private String zyb_report_print_type;
	private String customer_type;
	private String isDiseaseMerge;
	private String examResultStyle;
	private String isExamSummaryNew;
	private String isExamSuggest;
	private String exam_guidance;
	private String examSummaryPacsUrl;
	private String set_name;
	private String company;
	private String occusectorid;
	private String occusector;
	private String occutypeofworkid;
	private String occutypeofwork;
	private long employeeage;
	private long damage;
	private String joinDatetime;
	private String tijianleixin;
	private String carPdf_button;
	private String examSummaryCheckDefault;
	private String isExamSummaryNewDiseasePageShow;
	
	private String is_guide_back;
	private long customer_type_id;
	
	private String app_type;
	private String examOccuhazardfactorsLists;
	private List<ZybExamOccuhazardfactorsDTO> examOccuhazardfactorsList;
	
	private String resultID;//检查结果ID
	private String occudiseaseIDorcontraindicationID;//职业病和禁忌症ID
	private String remark;//备注
	private String exam_result;//
	
	private String customer_type_special;//信息显示为特殊颜色的人员类别id，逗号隔开（登记台、科室体检、总检）
	
	private String customer_type_special_color;//特殊人员类别信息显示的颜色（登记台、科室体检、总检）
	
	private String censoring_status;//终审状态
	private long censoring_doc;//终审医生
	private Date censoring_time;//终审时间
	private long final_worknum;//总检工作量
	private long approve_worknum;//审核工作量
	private long censoring_worknum;//终审工作量
	private String operation_type;//1总检、2审核、3终审
	private long report_class;
	private String t_wbeResource;
	private long cancel_type;//操作类型 1 一键取消，0 一键恢复
	private String cancel_wbeResource; //总检一键取消、恢复资源
	private int report_sms_notice;//拥有修改单位报告领取是否短信通知的功能      1是 / 0否
	private String is_exam_result_canfinal;//总检审核获取方式配置
	private String dxExamDisease;//单项阳性
	private String is_save_dx_disease;
	private String is_update_critical;//是否有删除修改危急值权限
	private String userid;//是否有删除修改危急值权限

	private int dammonth; //接害工龄 月
	private int employeemonth; //工龄 月
    private String apptype;
    private String occuphyexaclass_name; //职业体检类型   岗前 、、、
    private String is_final_history_show;

	public String getIs_final_history_show() {
		return is_final_history_show;
	}

	public void setIs_final_history_show(String is_final_history_show) {
		this.is_final_history_show = is_final_history_show;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}

	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}

	public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public int getEmployeemonth() {
		return employeemonth;
	}

	public void setEmployeemonth(int employeemonth) {
		this.employeemonth = employeemonth;
	}

	public int getDammonth() {

		return dammonth;
	}

	public void setDammonth(int dammonth) {
		this.dammonth = dammonth;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getIs_update_critical() {
		return is_update_critical;
	}

	public void setIs_update_critical(String is_update_critical) {
		this.is_update_critical = is_update_critical;
	}
	
	public String getIs_exam_result_canfinal() {
		return is_exam_result_canfinal;
	}

	public void setIs_exam_result_canfinal(String is_exam_result_canfinal) {
		this.is_exam_result_canfinal = is_exam_result_canfinal;
	}

	public String getResultID() {
		return resultID;
	}

	public void setResultID(String resultID) {
		this.resultID = resultID;
	}

	public String getOccudiseaseIDorcontraindicationID() {
		return occudiseaseIDorcontraindicationID;
	}

	public void setOccudiseaseIDorcontraindicationID(String occudiseaseIDorcontraindicationID) {
		this.occudiseaseIDorcontraindicationID = occudiseaseIDorcontraindicationID;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}

	public long getCustomer_type_id() {
		return customer_type_id;
	}

	public void setCustomer_type_id(long customer_type_id) {
		this.customer_type_id = customer_type_id;
	}

	public String getIs_guide_back() {
		return is_guide_back;
	}

	public void setIs_guide_back(String is_guide_back) {
		this.is_guide_back = is_guide_back;
	}

	public String getCarPdf_button() {
		return carPdf_button;
	}

	public void setCarPdf_button(String carPdf_button) {
		this.carPdf_button = carPdf_button;
	}

	public String getTijianleixin() {
		return tijianleixin;
	}

	public void setTijianleixin(String tijianleixin) {
		this.tijianleixin = tijianleixin;
	}

	public String getApp_type() {
		return app_type;
	}

	public void setApp_type(String app_type) {
		this.app_type = app_type;
	}
	
	public String getExamSummaryPacsUrl() {
		return examSummaryPacsUrl;
	}

	public void setExamSummaryPacsUrl(String examSummaryPacsUrl) {
		this.examSummaryPacsUrl = examSummaryPacsUrl;
	}

	public String getIsExamSummaryNew() {
		return isExamSummaryNew;
	}

	public void setIsExamSummaryNew(String isExamSummaryNew) {
		this.isExamSummaryNew = isExamSummaryNew;
	}

	public String getIsExamSuggest() {
		return isExamSuggest;
	}

	public void setIsExamSuggest(String isExamSuggest) {
		this.isExamSuggest = isExamSuggest;
	}

	public String getExam_doctor() {
		return exam_doctor;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public String getFinal_time1() {
		return final_time1;
	}

	public void setFinal_time1(String final_time1) {
		this.final_time1 = final_time1;
	}

	public String getFinal_time2() {
		return final_time2;
	}

	public void setFinal_time2(String final_time2) {
		this.final_time2 = final_time2;
	}

	public String getWebResource() {
		return webResource;
	}

	public void setWebResource(String webResource) {
		this.webResource = webResource;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public long getItem_id() {
		return item_id;
	}

	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}

	public String getFinal_exam_result() {
		return final_exam_result;
	}

	public void setFinal_exam_result(String final_exam_result) {
		this.final_exam_result = final_exam_result;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getExaminfoDiseases() {
		return examinfoDiseases;
	}

	public void setExaminfoDiseases(String examinfoDiseases) {
		this.examinfoDiseases = examinfoDiseases;
	}

	public List<ExaminfoDiseaseDTO> getExaminfoDisease() {
		return examinfoDisease;
	}

	public void setExaminfoDisease(List<ExaminfoDiseaseDTO> examinfoDisease) {
		this.examinfoDisease = examinfoDisease;
	}

	public String getSug_flag() {
		return sug_flag;
	}

	public void setSug_flag(String sug_flag) {
		this.sug_flag = sug_flag;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public long getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getApprove_status() {
		return approve_status;
	}

	public void setApprove_status(String approve_status) {
		this.approve_status = approve_status;
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

	public String getSeven_time() {
		return seven_time;
	}

	public void setSeven_time(String seven_time) {
		this.seven_time = seven_time;
	}

	public String getIsExamSummaryEdit() {
		return isExamSummaryEdit;
	}

	public void setIsExamSummaryEdit(String isExamSummaryEdit) {
		this.isExamSummaryEdit = isExamSummaryEdit;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public String getIsDiseaseMerge() {
		return isDiseaseMerge;
	}

	public void setIsDiseaseMerge(String isDiseaseMerge) {
		this.isDiseaseMerge = isDiseaseMerge;
	}

	public String getExamResultStyle() {
		return examResultStyle;
	}

	public void setExamResultStyle(String examResultStyle) {
		this.examResultStyle = examResultStyle;
	}

	public String getExam_guidance() {
		return exam_guidance;
	}

	public void setExam_guidance(String exam_guidance) {
		this.exam_guidance = exam_guidance;
	}

	public String getExamSummaryCheckDefault() {
		return examSummaryCheckDefault;
	}

	public void setExamSummaryCheckDefault(String examSummaryCheckDefault) {
		this.examSummaryCheckDefault = examSummaryCheckDefault;
	}

	public String getIsExamSummaryNewDiseasePageShow() {
		return isExamSummaryNewDiseasePageShow;
	}

	public void setIsExamSummaryNewDiseasePageShow(String isExamSummaryNewDiseasePageShow) {
		this.isExamSummaryNewDiseasePageShow = isExamSummaryNewDiseasePageShow;
	}

	public String getSet_name() {
		return set_name;
	}

	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public String getOccutypeofworkid() {
		return occutypeofworkid;
	}

	public void setOccutypeofworkid(String occutypeofworkid) {
		this.occutypeofworkid = occutypeofworkid;
	}

	public String getOccutypeofwork() {
		return occutypeofwork;
	}

	public void setOccutypeofwork(String occutypeofwork) {
		this.occutypeofwork = occutypeofwork;
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

	public String getJoinDatetime() {
		return joinDatetime;
	}

	public void setJoinDatetime(String joinDatetime) {
		this.joinDatetime = joinDatetime;
	}

	public String getExamOccuhazardfactorsLists() {
		return examOccuhazardfactorsLists;
	}

	public void setExamOccuhazardfactorsLists(String examOccuhazardfactorsLists) {
		this.examOccuhazardfactorsLists = examOccuhazardfactorsLists;
	}

	public List<ZybExamOccuhazardfactorsDTO> getExamOccuhazardfactorsList() {
		return examOccuhazardfactorsList;
	}

	public void setExamOccuhazardfactorsList(List<ZybExamOccuhazardfactorsDTO> examOccuhazardfactorsList) {
		this.examOccuhazardfactorsList = examOccuhazardfactorsList;
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

	public String getCensoring_status() {
		return censoring_status;
	}

	public void setCensoring_status(String censoring_status) {
		this.censoring_status = censoring_status;
	}

	public long getCensoring_doc() {
		return censoring_doc;
	}

	public void setCensoring_doc(long censoring_doc) {
		this.censoring_doc = censoring_doc;
	}

	public Date getCensoring_time() {
		return censoring_time;
	}

	public void setCensoring_time(Date censoring_time) {
		this.censoring_time = censoring_time;
	}

	public long getFinal_worknum() {
		return final_worknum;
	}

	public void setFinal_worknum(long final_worknum) {
		this.final_worknum = final_worknum;
	}

	public long getApprove_worknum() {
		return approve_worknum;
	}

	public void setApprove_worknum(long approve_worknum) {
		this.approve_worknum = approve_worknum;
	}

	public long getCensoring_worknum() {
		return censoring_worknum;
	}

	public void setCensoring_worknum(long censoring_worknum) {
		this.censoring_worknum = censoring_worknum;
	}

	public String getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}

	public long getReport_class() {
		return report_class;
	}

	public void setReport_class(long report_class) {
		this.report_class = report_class;
	}

	public String getT_wbeResource() {
		return t_wbeResource;
	}

	public void setT_wbeResource(String t_wbeResource) {
		this.t_wbeResource = t_wbeResource;
	}

	public long getCancel_type() {
		return cancel_type;
	}

	public void setCancel_type(long cancel_type) {
		this.cancel_type = cancel_type;
	}

	public String getCancel_wbeResource() {
		return cancel_wbeResource;
	}

	public void setCancel_wbeResource(String cancel_wbeResource) {
		this.cancel_wbeResource = cancel_wbeResource;
	}

	public int getReport_sms_notice() {
		return report_sms_notice;
	}

	public void setReport_sms_notice(int report_sms_notice) {
		this.report_sms_notice = report_sms_notice;
	}

	public String getDxExamDisease() {
		return dxExamDisease;
	}

	public String getIs_save_dx_disease() {
		return is_save_dx_disease;
	}

	public void setDxExamDisease(String dxExamDisease) {
		this.dxExamDisease = dxExamDisease;
	}

	public void setIs_save_dx_disease(String is_save_dx_disease) {
		this.is_save_dx_disease = is_save_dx_disease;
	}

	public List<ExaminfoDiseaseDTO> getDxexaminfoDiseases() {
		return dxexaminfoDiseases;
	}

	public void setDxexaminfoDiseases(List<ExaminfoDiseaseDTO> dxexaminfoDiseases) {
		this.dxexaminfoDiseases = dxexaminfoDiseases;
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
