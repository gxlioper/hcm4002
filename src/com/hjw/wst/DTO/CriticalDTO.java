package com.hjw.wst.DTO;


public class CriticalDTO implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 private long id;
	 private String exam_result;
	 private String note;
	 private String check_date;
	 private String exam_num;
	 private String user_name;
	 private String arch_num;
	 private String check_doctor;
	 private String dep_name;
	 private String item_name;
	 private long done_flag;
	 private String done_flag_s;
	 private String done_date;
	 private String examination_item_name;
	 private long userid;
	 private long dept_id;
	 private long charging_item_id;
	 private int data_source;
	 private long creater;
	 private String creater_name;
	 private String create_time;
	 private String disease_name;
	 private String sex;
	 private int age;
	 private String phone;
	 private String company;
	 private int status;
	 private long item_code;
	 private long exam_item_id;
	 private String critical_type;
	 private String critical_type_s;
	 private String old_results;//历史保存结果
	 private String parent_critical_class_name;
	 private String data_name;
	 private String critical_class_parent_name;//大类名称
	 private String critical_class_name;//子类名称
	 private String critical_class_level;//危急值等级
	 private int critical_level_id;//危急值等级
	 private String data_code_children;
	 private long exam_info_id;
	 private String visit_num;//计划编码
	 private String customer_feedback;//处理内容
	 private String visit_date;//处理时间
	 private String chi_name;//处理人
	 private int give_notice_type;
	 private String company_name;

	public String getCreater_name() {
		return creater_name;
	}

	public void setCreater_name(String creater_name) {
		this.creater_name = creater_name;
	}

	public String getParent_critical_class_name() {
		return parent_critical_class_name;
	}

	public void setParent_critical_class_name(String parent_critical_class_name) {
		this.parent_critical_class_name = parent_critical_class_name;
	}

	public String getData_name() {
		return data_name;
	}

	public void setData_name(String data_name) {
		this.data_name = data_name;
	}

	public int getCritical_level_id() {
		return critical_level_id;
	}

	public void setCritical_level_id(int critical_level_id) {
		this.critical_level_id = critical_level_id;
	}


	public String getData_code_children() {
		return data_code_children;
	}


	public void setData_code_children(String data_code_children) {
		this.data_code_children = data_code_children;
	}


	public long getExam_info_id() {
		return exam_info_id;
	}


	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}


	public String getVisit_num() {
		return visit_num;
	}


	public void setVisit_num(String visit_num) {
		this.visit_num = visit_num;
	}


	public String getCustomer_feedback() {
		return customer_feedback;
	}


	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
	}


	public String getVisit_date() {
		return visit_date;
	}


	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}


	public int getGive_notice_type() {
		return give_notice_type;
	}


	public void setGive_notice_type(int give_notice_type) {
		this.give_notice_type = give_notice_type;
	}


	public String getCompany_name() {
		return company_name;
	}


	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}


	public String getChi_name() {
		return chi_name;
	}


	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}

	public CriticalDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getCritical_class_parent_name() {
		return critical_class_parent_name;
	}

	public void setCritical_class_parent_name(String critical_class_parent_name) {
		this.critical_class_parent_name = critical_class_parent_name;
	}

	public String getCritical_class_name() {
		return critical_class_name;
	}

	public void setCritical_class_name(String critical_class_name) {
		this.critical_class_name = critical_class_name;
	}

	public String getCritical_class_level() {
		return critical_class_level;
	}

	public void setCritical_class_level(String critical_class_level) {
		this.critical_class_level = critical_class_level;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}
	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public String getDone_date() {
		return done_date;
	}
	public void setDone_date(String done_date) {
		this.done_date = done_date;
	}
	public long getDone_flag() {
		return done_flag;
	}
	public void setDone_flag(long done_flag) {
		//this.done_flag = done_flag;
		if(done_flag==0){
			this.done_flag_s = "未通知";
		}else if(done_flag==1){
			this.done_flag_s = "已通知";
		}else{
			this.done_flag_s = "未联系上";
		}
	}
	public String getDone_flag_s() {
		return done_flag_s;
	}
	public void setDone_flag_s(String done_flag_s) {
		this.done_flag_s = done_flag_s;
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
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getCheck_doctor() {
		return check_doctor;
	}
	public void setCheck_doctor(String check_doctor) {
		this.check_doctor = check_doctor;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCheck_date() {
		return check_date;
	}
	public void setCheck_date(String  check_date) {
		this.check_date = check_date;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getExamination_item_name() {
		return examination_item_name;
	}
	public void setExamination_item_name(String examination_item_name) {
		this.examination_item_name = examination_item_name;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public long getDept_id() {
		return dept_id;
	}
	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public int getData_source() {
		return data_source;
	}
	public void setData_source(int data_source) {
		this.data_source = data_source;
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
	public String getDisease_name() {
		return disease_name;
	}
	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getItem_code() {
		return item_code;
	}
	public void setItem_code(long item_code) {
		this.item_code = item_code;
	}
	public String getCritical_type() {
		return critical_type;
	}
	public void setCritical_type(String critical_type) {
		this.critical_type = critical_type;
	}
	public String getCritical_type_s() {
		return critical_type_s;
	}
	public void setCritical_type_s(String critical_type_s) {
		this.critical_type_s = critical_type_s;
	}
	public String getOld_results() {
		return old_results;
	}
	public void setOld_results(String old_results) {
		this.old_results = old_results;
	}

	}