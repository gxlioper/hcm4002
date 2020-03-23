package com.hjw.crm.DTO;

public class CrmMemberPrivateDoctorDTO {
	private long id;
	private Long c_id;
	private String arch_num;
	private Long doctor_id;
	private Long allot_person;
	private String allot_date;
	private String user_name;
	private String sex;
	private String doctorname;
	private String phone;
	private String allot_person_name;
	private String exam_num;
	
	private String id_num;
	private long age;
	private String status;
	private String statuss;
	private String join_date;
	private String exam_type;
	private String exam_types;
	private String set_name;
	private String company;
	private double team_pay;
	private double personal_pay;
	private int cvp_count;//策略总数
	private int cvr_count; //计划总数
	
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getAllot_person_name() {
		return allot_person_name;
	}
	public void setAllot_person_name(String allot_person_name) {
		this.allot_person_name = allot_person_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Long getC_id() {
		return c_id;
	}
	public void setC_id(Long c_id) {
		this.c_id = c_id;
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
	public String getDoctorname() {
		return doctorname;
	}
	public void setDoctorname(String doctorname) {
		this.doctorname = doctorname;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public Long getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(Long doctor_id) {
		this.doctor_id = doctor_id;
	}
	public Long getAllot_person() {
		return allot_person;
	}
	public void setAllot_person(Long allot_person) {
		this.allot_person = allot_person;
	}
	public String getAllot_date() {
		return allot_date;
	}
	public void setAllot_date(String allot_date) {
		this.allot_date = allot_date;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public long getAge() {
		return age;
	}
	public void setAge(long age) {
		this.age = age;
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
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
		if("G".equals(exam_type)){
			this.exam_types = "个检";
		}else if("T".equals(exam_type)){
			this.exam_types = "团检";
		}
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
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	public String getExam_types() {
		return exam_types;
	}
	public void setExam_types(String exam_types) {
		this.exam_types = exam_types;
	}
	public int getCvp_count() {
		return cvp_count;
	}
	public int getCvr_count() {
		return cvr_count;
	}
	public void setCvp_count(int cvp_count) {
		this.cvp_count = cvp_count;
	}
	public void setCvr_count(int cvr_count) {
		this.cvr_count = cvr_count;
	}
}
