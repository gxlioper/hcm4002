package com.hjw.wst.model;

public class DataMigrationModel {

	private String exam_nums;
	
	private String database_url;// IP地址
	private String database_port;// 端口号
	private String database_uame;// 数据库名称
	private String username; //  登录名称
	private String password; //  登录密码
	private long type; //配置类型 0表示数据库配置、1表示ftp配置
	
	private String is_out_reg;
	
	private String arch_num;
	private String id_num;
	private String exam_num;
	private String user_name;
	private String dep_category;
	private String s_join_date;
	private String e_join_date;
	private String employeeID;
	private String exam_type;
	private String phone;
	private String status;
	private String sex;
	private String level;
	private long company_id;
	private String exam_export;
	private String exam_import;

	public String getExam_export() {
		return exam_export;
	}

	public void setExam_export(String exam_export) {
		this.exam_export = exam_export;
	}

	public String getExam_import() {
		return exam_import;
	}

	public void setExam_import(String exam_import) {
		this.exam_import = exam_import;
	}

	public String getIs_out_reg() {
		return is_out_reg;
	}

	public void setIs_out_reg(String is_out_reg) {
		this.is_out_reg = is_out_reg;
	}

	public String getExam_nums() {
		return exam_nums;
	}

	public void setExam_nums(String exam_nums) {
		this.exam_nums = exam_nums;
	}

	public String getDatabase_url() {
		return database_url;
	}

	public void setDatabase_url(String database_url) {
		this.database_url = database_url;
	}

	public String getDatabase_port() {
		return database_port;
	}

	public void setDatabase_port(String database_port) {
		this.database_port = database_port;
	}

	public String getDatabase_uame() {
		return database_uame;
	}

	public void setDatabase_uame(String database_uame) {
		this.database_uame = database_uame;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
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

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDep_category() {
		return dep_category;
	}

	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
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

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
}
