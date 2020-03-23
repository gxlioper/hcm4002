package com.hjw.wst.model;

import java.util.Date;

public class CriticalModel implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	 private long id;
	 private String startCheckDate;
	 private String endCheckDate;
	 private String exam_result;
	 private String note="";
	 private String check_date; 
	 private String exam_num;
	 private String user_name;
	 private String arch_num;
	 private String check_doctor;
	 private String dep_name;
	 private String item_name;
	 private long done_flag;
	 private Date done_date;
	 private Integer data_source;//生成危急值标识  0系统生成     1手动添加 
	 
	 private Long examinfo_id ;
     private String dep_category;
     private Long item_id;
     private String startDone_date;
     private String endDone_date;
     private String flag;
     private String critical_type;  
	 private String critical_tactics_num;//策略编码
	 private String exam_name;
     private String start_date;
     private String end_dta;
     private int critical_class_level;//危急值等级
     private String done_flag_s;
     //通知方式  1未通知，2已通知,3未联系上
     private long give_notice_type;
	 private long critical_id1;
     private long critical_class_parent_name;//大类名称
	 private long critical_class_name;//子类名称	 
	 private long disease_id;
	 private String disease_num;
	 private String critical_suggestion;
	 private String chkItem;
	 private String time1;
	 private String time2;
	 private String create_time;
	 private String time3;
	 private String time4;
	 private String sex;
	 private int age;
	 private String phone;
	 private String start_create_time;
	 private String end_create_time;
	 private String ids;
	 private String type;
	 private long com_id;
	 private long creater;
	private long batch_id;

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public String getExam_name() {
		return exam_name;
	}

	public void setExam_name(String exam_name) {
		this.exam_name = exam_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_dta() {
		return end_dta;
	}

	public void setEnd_dta(String end_dta) {
		this.end_dta = end_dta;
	}

	public String getDone_flag_s() {
		return done_flag_s;
	}

	public void setDone_flag_s(String done_flag_s) {
		this.done_flag_s = done_flag_s;
	}

	public long getGive_notice_type() {
		return give_notice_type;
	}

	public void setGive_notice_type(long give_notice_type) {
		this.give_notice_type = give_notice_type;
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

	public String getStart_create_time() {
		return start_create_time;
	}

	public void setStart_create_time(String start_create_time) {
		this.start_create_time = start_create_time;
	}

	public String getEnd_create_time() {
		return end_create_time;
	}

	public void setEnd_create_time(String end_create_time) {
		this.end_create_time = end_create_time;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public long getCom_id() {
		return com_id;
	}

	public void setCom_id(long com_id) {
		this.com_id = com_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	 
	 public CriticalModel() {
		// TODO Auto-generated constructor stub
	}
	 
	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public long getCritical_id1() {
		return critical_id1;
	}

	public void setCritical_id1(long critical_id1) {
		this.critical_id1 = critical_id1;
	}

	public long getCritical_class_parent_name() {
		return critical_class_parent_name;
	}

	public void setCritical_class_parent_name(long critical_class_parent_name) {
		this.critical_class_parent_name = critical_class_parent_name;
	}

	public long getCritical_class_name() {
		return critical_class_name;
	}

	public void setCritical_class_name(long critical_class_name) {
		this.critical_class_name = critical_class_name;
	}

	public int getCritical_class_level() {
		return critical_class_level;
	}

	public void setCritical_class_level(int critical_class_level) {
		this.critical_class_level = critical_class_level;
	}

	public long getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(long disease_id) {
		this.disease_id = disease_id;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}

	public String getCritical_suggestion() {
		return critical_suggestion;
	}

	public void setCritical_suggestion(String critical_suggestion) {
		this.critical_suggestion = critical_suggestion;
	}

	public String getChkItem() {
		return chkItem;
	}

	public void setChkItem(String chkItem) {
		this.chkItem = chkItem;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getTime3() {
		return time3;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getTime4() {
		return time4;
	}

	public void setTime4(String time4) {
		this.time4 = time4;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStartCheckDate() {
		return startCheckDate;
	}

	public void setStartCheckDate(String startCheckDate) {
		this.startCheckDate = startCheckDate;
	}

	public String getEndCheckDate() {
		return endCheckDate;
	}

	public void setEndCheckDate(String endCheckDate) {
		this.endCheckDate = endCheckDate;
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

	public void setCheck_date(String check_date) {
		this.check_date = check_date;
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

	public long getDone_flag() {
		return done_flag;
	}

	public void setDone_flag(long done_flag) {
		this.done_flag = done_flag;
	}

	public Date getDone_date() {
		return done_date;
	}

	public void setDone_date(Date done_date) {
		this.done_date = done_date;
	}

	public Integer getData_source() {
		return data_source;
	}

	public void setData_source(Integer data_source) {
		this.data_source = data_source;
	}

	public Long getExaminfo_id() {
		return examinfo_id;
	}

	public void setExaminfo_id(Long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}

	public String getDep_category() {
		return dep_category;
	}

	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}

	public Long getItem_id() {
		return item_id;
	}

	public void setItem_id(Long item_id) {
		this.item_id = item_id;
	}

	public String getStartDone_date() {
		return startDone_date;
	}

	public void setStartDone_date(String startDone_date) {
		this.startDone_date = startDone_date;
	}

	public String getEndDone_date() {
		return endDone_date;
	}

	public void setEndDone_date(String endDone_date) {
		this.endDone_date = endDone_date;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCritical_type() {
		return critical_type;
	}

	public void setCritical_type(String critical_type) {
		this.critical_type = critical_type;
	}

	public String getCritical_tactics_num() {
		return critical_tactics_num;
	}

	public void setCritical_tactics_num(String critical_tactics_num) {
		this.critical_tactics_num = critical_tactics_num;
	}

	
}
