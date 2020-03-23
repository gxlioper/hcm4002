package com.hjw.crm.DTO;



public class CrmVisitPlanDTO {
	private String id;
	private String arch_num;
	private Long plan_doctor_id;
	private String plan_visit_date;
	private String visit_content;
	private String visit_num;
	private String visit_status;
	private Long creater;
	private String create_time;
	private String personname;
	private String username;
	private String sname;
	private String exam_num;
	private String visit_important;
	private String fujianflag;
	private String phone;
	private String cvr_id;
	private String health_suggest;
	private String customer_feedback;
	private String visit_type;
	private int tactics_type ;//策略类型
	private String notices;//策略描述
	private String rmark;//策略说明
	private String tactics_type_s;
	private String record_status;
	private double personal_pay;
	
	public String getFujianflag() {
		return fujianflag;
	}
	public void setFujianflag(String fujianflag) {
		this.fujianflag = fujianflag;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public Long getPlan_doctor_id() {
		return plan_doctor_id;
	}
	public void setPlan_doctor_id(Long plan_doctor_id) {
		this.plan_doctor_id = plan_doctor_id;
	}
	public String getPlan_visit_date() {
		return plan_visit_date;
	}
	public void setPlan_visit_date(String plan_visit_date) {
		this.plan_visit_date = plan_visit_date;
	}
	public String getVisit_content() {
		return visit_content;
	}
	public void setVisit_content(String visit_content) {
		this.visit_content = visit_content;
	}
	public String getVisit_num() {
		return visit_num;
	}
	public void setVisit_num(String visit_num) {
		this.visit_num = visit_num;
	}
	public String getVisit_status() {
		return visit_status;
	}
	public void setVisit_status(String visit_status) {
		this.visit_status = visit_status;
		if("1".equals(this.visit_status)){
			this.setVisit_status("计划回访");
		}else if("2".equals(this.visit_status)){
			this.setVisit_status("开始回访");
		}else if("3".equals(this.visit_status)){
			this.setVisit_status("结束回访");
		}
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getVisit_important() {
		return visit_important;
	}
	public void setVisit_important(String visit_important) {
		this.visit_important = visit_important;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCvr_id() {
		return cvr_id;
	}
	public void setCvr_id(String cvr_id) {
		this.cvr_id = cvr_id;
	}
	public String getHealth_suggest() {
		return health_suggest;
	}
	public String getCustomer_feedback() {
		return customer_feedback;
	}
	public void setHealth_suggest(String health_suggest) {
		this.health_suggest = health_suggest;
	}
	public void setCustomer_feedback(String customer_feedback) {
		this.customer_feedback = customer_feedback;
	}
	public String getVisit_type() {
		return visit_type;
	}
	public void setVisit_type(String visit_type) {
		this.visit_type = visit_type;
	}
	public int getTactics_type() {
		return tactics_type;
	}
	public String getNotices() {
		return notices;
	}
	public String getRmark() {
		return rmark;
	}
	public void setTactics_type(int tactics_type) {
		this.tactics_type = tactics_type;  //策略类型  表示：1 慢病、2复查、3危机值、4vip回访，5特殊回访
		if(tactics_type == 1){
			this.setTactics_type_s("慢病");
		}else if(tactics_type == 2){
			this.setTactics_type_s("复查");
		}else if(tactics_type == 3){
			this.setTactics_type_s("危机值");
		}else if(tactics_type == 4){
			this.setTactics_type_s("vip回访");
		}else if(tactics_type == 5){
			this.setTactics_type_s("特殊回访");
		}
	}
	public void setNotices(String notices) {
		this.notices = notices;
	}
	public void setRmark(String rmark) {
		this.rmark = rmark;
	}
	public String getTactics_type_s() {
		return tactics_type_s;
	}
	public void setTactics_type_s(String tactics_type_s) {
		this.tactics_type_s = tactics_type_s;
	}
	public String getRecord_status() {
		return record_status;
	}
	public void setRecord_status(String record_status) {
		this.record_status = record_status;
	}
	public double getPersonal_pay() {
		return personal_pay;
	}
	public void setPersonal_pay(double personal_pay) {
		this.personal_pay = personal_pay;
	}
	
}
