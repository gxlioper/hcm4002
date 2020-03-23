package com.hjw.wst.model;

public class UserInfoModel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;

	private String id;//       VARCHAR2(50) not null,

	private String username;// USERNAME VARCHAR2(30) not null,

	private String passwd;//  IDENTIFY VARCHAR2(30) not null,

	private int userlock;//  USERLOCK NUMBER(2) default 1 not null,

	private String lastip;//LASTIP   VARCHAR2(30),

	private String lastdate;// LASTDATE DATE,

	private long loginnum;// LOGINNUM NUMBER(6) default 0 not null,

	private String cdate;// CDATE    DATE not null,

	private int usertype;//USERTYPE NUMBER(2) not null,
	
	private String susertype;

	private String name;// NAME     VARCHAR2(50) not null,

	private String pid;// PID      VARCHAR2(50) not null,

	private String tel1;//TEL1     VARCHAR2(50),

	private String tel2;//	  TEL2     VARCHAR2(50),

	private String remark1;//  REMARK1  VARCHAR2(100),

	private String remark2;// REMARK2  VARCHAR2(100),
	
    private String sex;
    
    private String email;
    private String chi_Name;
    private String log_Name;
    private String center_name;
    private String phone_num;
    private String work_num;
    private String dep_name;
    private String role_name;
    private String exam_center_parent_id;
    private String startStop;
    private String user_signature;
    private String user_notices;
    private String others;
    private String user_pic_path;
    private String user_pic_path_edit;
    private String exam_num;
    
	
	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getUser_pic_path_edit() {
		return user_pic_path_edit;
	}

	public void setUser_pic_path_edit(String user_pic_path_edit) {
		this.user_pic_path_edit = user_pic_path_edit;
	}

	public String getUser_pic_path() {
		return user_pic_path;
	}

	public void setUser_pic_path(String user_pic_path) {
		this.user_pic_path = user_pic_path;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getUser_notices() {
		return user_notices;
	}

	public void setUser_notices(String user_notices) {
		this.user_notices = user_notices;
	}

	public String getStartStop() {
		return startStop;
	}

	public void setStartStop(String startStop) {
		this.startStop = startStop;
	}

	public String getExam_center_parent_id() {
		return exam_center_parent_id;
	}

	public void setExam_center_parent_id(String exam_center_parent_id) {
		this.exam_center_parent_id = exam_center_parent_id;
	}

	public String getUser_signature() {
		return user_signature;
	}

	public void setUser_signature(String user_signature) {
		this.user_signature = user_signature;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	public String getWork_num() {
		return work_num;
	}

	public void setWork_num(String work_num) {
		this.work_num = work_num;
	}

	public String getPhone_num() {
		return phone_num;
	}

	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}

	public String getCenter_name() {
		return center_name;
	}

	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}

	public String getLog_Name() {
		return log_Name;
	}

	public void setLog_Name(String log_Name) {
		this.log_Name = log_Name;
	}

	private boolean login;//true 表示登录成功，否者 登录失败
	
	

	public String getChi_Name() {
		return chi_Name;
	}

	public void setChi_Name(String chi_Name) {
		this.chi_Name = chi_Name;
	}

	public String getSusertype() {
		return susertype;
	}

	public void setSusertype(String susertype) {
		this.susertype = susertype;
	}

	public boolean isLogin() {
		return login;
	}

	public void setLogin(boolean login) {
		this.login = login;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserlock() {
		return userlock;
	}

	public void setUserlock(int userlock) {
		this.userlock = userlock;
	}

	public String getLastip() {
		return lastip;
	}

	public void setLastip(String lastip) {
		this.lastip = lastip;
	}

	public String getLastdate() {
		return lastdate;
	}

	public void setLastdate(String lastdate) {
		this.lastdate = lastdate;
	}

	public long getLoginnum() {
		return loginnum;
	}

	public void setLoginnum(long loginnum) {
		this.loginnum = loginnum;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public int getUsertype() {
		return usertype;
	}

	public void setUsertype(int usertype) {
		this.usertype = usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


}
