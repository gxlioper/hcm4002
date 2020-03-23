package com.hjw.wst.model;

public class AcceptanceCheckModel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	private String arch_num;
	private String exam_num;
	private String id_num;
	private String sex;
	private String phone;
	private String com_id;
	private String batch_id;
	private String group_id;
	private String s_join_date;
	private String e_join_date;
	private String exam_type;
	private String user_name;
	private String employeeID;
	private String status;
	private long company_id;
	private long examinfo_id;
	private long charging_item_id;
	private String charging_item_ids;
	private String pacs_req_code;
	
	private long view_imange_id;
	
	private String isAcceptanceCheck;
	private long acceptance_check;
	
	private String dep_category;
	
	private long dep_id;
	private long set_id;
	private String exam_status;
	private String pay_status;
	private String level;
	private String arch_com_ids;
	private String str_arch_num;
	private long customer_id;
	private String customer_type;
	
	private String zongjianyisheng;
	private String zongjiandate;
	private String report_print_type;
	private String zyb_report_print_type;
	
	private String is_guide_back;
	private String chkItem;
	private String isVip;
	private Integer wxzj;
	
	private String notices;
	private String resource;
	private int apptype;
	private long userId;
	private long exam_id;
	
	public String getPacs_req_code() {
		return pacs_req_code;
	}
	public void setPacs_req_code(String pacs_req_code) {
		this.pacs_req_code = pacs_req_code;
	}
	public long getExam_id() {
		return exam_id;
	}
	public void setExam_id(long exam_id) {
		this.exam_id = exam_id;
	}
	public String getNotices() {
		return notices;
	}
	public void setNotices(String notices) {
		this.notices = notices;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public int getApptype() {
		return apptype;
	}
	public void setApptype(int apptype) {
		this.apptype = apptype;
	}
	private int customer_type_id;
	
	public String getZongjianyisheng() {
		return zongjianyisheng;
	}
	public void setZongjianyisheng(String zongjianyisheng) {
		this.zongjianyisheng = zongjianyisheng;
	}
	public String getZongjiandate() {
		return zongjiandate;
	}
	public void setZongjiandate(String zongjiandate) {
		this.zongjiandate = zongjiandate;
	}
	public String getCustomer_type() {
		return customer_type;
	}
	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public String getArch_com_ids() {
		return arch_com_ids;
	}
	public void setArch_com_ids(String arch_com_ids) {
		this.arch_com_ids = arch_com_ids;
	}
	public String getStr_arch_num() {
		return str_arch_num;
	}
	public void setStr_arch_num(String str_arch_num) {
		this.str_arch_num = str_arch_num;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCom_id() {
		return com_id;
	}
	public void setCom_id(String com_id) {
		this.com_id = com_id;
	}
	public String getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(String batch_id) {
		this.batch_id = batch_id;
	}
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getS_join_date() {
		return s_join_date;
	}
	public void setS_join_date(String s_join_date) {
		this.s_join_date = s_join_date;
	}
	public String getE_join_date() {
		return e_join_date;
	}
	public void setE_join_date(String e_join_date) {
		this.e_join_date = e_join_date;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public long getExaminfo_id() {
		return examinfo_id;
	}
	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public long getView_imange_id() {
		return view_imange_id;
	}
	public void setView_imange_id(long view_imange_id) {
		this.view_imange_id = view_imange_id;
	}
	public String getIsAcceptanceCheck() {
		return isAcceptanceCheck;
	}
	public void setIsAcceptanceCheck(String isAcceptanceCheck) {
		this.isAcceptanceCheck = isAcceptanceCheck;
	}
	public long getAcceptance_check() {
		return acceptance_check;
	}
	public void setAcceptance_check(long acceptance_check) {
		this.acceptance_check = acceptance_check;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public String getEmployeeID() {
		return employeeID;
	}
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public long getSet_id() {
		return set_id;
	}
	public void setSet_id(long set_id) {
		this.set_id = set_id;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public String getPay_status() {
		return pay_status;
	}
	public void setPay_status(String pay_status) {
		this.pay_status = pay_status;
	}
	public String getCharging_item_ids() {
		return charging_item_ids;
	}
	public void setCharging_item_ids(String charging_item_ids) {
		this.charging_item_ids = charging_item_ids;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getIs_guide_back() {
		return is_guide_back;
	}
	public void setIs_guide_back(String is_guide_back) {
		this.is_guide_back = is_guide_back;
	}
	public String getChkItem() {
		return chkItem;
	}
	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
	}
	public String getIsVip() {
		return isVip;
	}
	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}
	public Integer getWxzj() {
		return wxzj;
	}
	public void setWxzj(Integer wxzj) {
		this.wxzj = wxzj;
	}
	public int getCustomer_type_id() {
		return customer_type_id;
	}
	public void setCustomer_type_id(int customer_type_id) {
		this.customer_type_id = customer_type_id;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
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
