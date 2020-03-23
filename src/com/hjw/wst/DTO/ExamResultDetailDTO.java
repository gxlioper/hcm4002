package com.hjw.wst.DTO;

import java.util.List;

public class ExamResultDetailDTO {

	private long id;
	private long exam_info_id;
	private long exam_item_id;
	private String item_name;
	private String exam_result;
	private String ref_indicator;
	private String item_unit;
	private String ref_value;
	
	private String demo_name;
	private String item_code;
	private String exam_num;
	private String exam_status;
	private String exam_date;
	private String exam_doctor;
	private long exam_doctor_id;
	private long approver_id;
	private String item_category;
	private String approver;
	private String approve_date;
	
	private double ref_Fmin;
	private double ref_Fmax;
	private double ref_Mmin;
	private double ref_Mmax;
	
	private String exam_desc;
	private long pace_id;
	private String data_name;
	private String positive_find;
	private String user_name;
	private String sex;
	private int age;
	private String join_date;
	private String dep_num;
	private String dep_name;
	private String c_item_name;
	private String c_item_code;
	private long d_seq_code;
	private long c_seq_code;
	private long e_seq_code;
	private String create_time;
	private long charging_item_id;
	private long inputter;
	private String inputters;
	private String item_num;
	
	private String update_time="";
	private long dep_id;
	private long seq_code;	
	private String charging_item_name="";
	
	public long getExam_doctor_id() {
		return exam_doctor_id;
	}
	public void setExam_doctor_id(long exam_doctor_id) {
		this.exam_doctor_id = exam_doctor_id;
	}
	public long getApprover_id() {
		return approver_id;
	}
	public void setApprover_id(long approver_id) {
		this.approver_id = approver_id;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
	}
	public String getInputters() {
		return inputters;
	}
	public void setInputters(String inputters) {
		this.inputters = inputters;
	}
	public long getInputter() {
		return inputter;
	}
	public void setInputter(long inputter) {
		this.inputter = inputter;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	
	private String item_name_1;
	private String item_name_2;
	private String item_name_3;
	private String item_name_4;
	private String item_name_5;
	private String item_name_6;
	
	private String biaoshi;
	private int num;
	private String charging_item_code;
	
	public String getCharging_item_code() {
		return charging_item_code;
	}
	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public long getPace_id() {
		return pace_id;
	}
	public void setPace_id(long pace_id) {
		this.pace_id = pace_id;
	}
	public String getData_name() {
		return data_name;
	}
	public void setData_name(String data_name) {
		this.data_name = data_name;
	}
	public String getPositive_find() {
		return positive_find;
	}
	public void setPositive_find(String positive_find) {
		this.positive_find = positive_find;
	}
	private List<examItemRefandDangDTO> itemRefList; 
	
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getExam_item_id() {
		return exam_item_id;
	}
	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
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
	public String getRef_indicator() {
		return ref_indicator;
	}
	public void setRef_indicator(String ref_indicator) {
		this.ref_indicator = ref_indicator;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public String getRef_value() {
		return ref_value;
	}
	public void setRef_value(String ref_value) {
		this.ref_value = ref_value;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public long getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public String getDemo_name() {
		return demo_name;
	}
	public void setDemo_name(String demo_name) {
		this.demo_name = demo_name;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getExam_doctor() {
		return exam_doctor;
	}
	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public double getRef_Fmin() {
		return ref_Fmin;
	}
	public void setRef_Fmin(double ref_Fmin) {
		this.ref_Fmin = ref_Fmin;
	}
	public double getRef_Fmax() {
		return ref_Fmax;
	}
	public void setRef_Fmax(double ref_Fmax) {
		this.ref_Fmax = ref_Fmax;
	}
	public double getRef_Mmin() {
		return ref_Mmin;
	}
	public void setRef_Mmin(double ref_Mmin) {
		this.ref_Mmin = ref_Mmin;
	}
	public double getRef_Mmax() {
		return ref_Mmax;
	}
	public void setRef_Mmax(double ref_Mmax) {
		this.ref_Mmax = ref_Mmax;
	}
	public List<examItemRefandDangDTO> getItemRefList() {
		return itemRefList;
	}
	public void setItemRefList(List<examItemRefandDangDTO> itemRefList) {
		this.itemRefList = itemRefList;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getItem_name_1() {
		return item_name_1;
	}
	public void setItem_name_1(String item_name_1) {
		this.item_name_1 = item_name_1;
	}
	public String getItem_name_2() {
		return item_name_2;
	}
	public void setItem_name_2(String item_name_2) {
		this.item_name_2 = item_name_2;
	}
	public String getItem_name_3() {
		return item_name_3;
	}
	public void setItem_name_3(String item_name_3) {
		this.item_name_3 = item_name_3;
	}
	public String getItem_name_4() {
		return item_name_4;
	}
	public void setItem_name_4(String item_name_4) {
		this.item_name_4 = item_name_4;
	}
	public String getItem_name_5() {
		return item_name_5;
	}
	public void setItem_name_5(String item_name_5) {
		this.item_name_5 = item_name_5;
	}
	public String getItem_name_6() {
		return item_name_6;
	}
	public void setItem_name_6(String item_name_6) {
		this.item_name_6 = item_name_6;
	}
	public String getBiaoshi() {
		return biaoshi;
	}
	public void setBiaoshi(String biaoshi) {
		this.biaoshi = biaoshi;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getApprove_date() {
		return approve_date;
	}
	public void setApprove_date(String approve_date) {
		this.approve_date = approve_date;
	}
	public String getDep_num() {
		return dep_num;
	}
	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public String getC_item_name() {
		return c_item_name;
	}
	public void setC_item_name(String c_item_name) {
		this.c_item_name = c_item_name;
	}
	public String getC_item_code() {
		return c_item_code;
	}
	public void setC_item_code(String c_item_code) {
		this.c_item_code = c_item_code;
	}
	public long getD_seq_code() {
		return d_seq_code;
	}
	public void setD_seq_code(long d_seq_code) {
		this.d_seq_code = d_seq_code;
	}
	public long getC_seq_code() {
		return c_seq_code;
	}
	public void setC_seq_code(long c_seq_code) {
		this.c_seq_code = c_seq_code;
	}
	public long getE_seq_code() {
		return e_seq_code;
	}
	public void setE_seq_code(long e_seq_code) {
		this.e_seq_code = e_seq_code;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public long getDep_id() {
		return dep_id;
	}
	public long getSeq_code() {
		return seq_code;
	}
	public String getCharging_item_name() {
		return charging_item_name;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}
	public void setCharging_item_name(String charging_item_name) {
		this.charging_item_name = charging_item_name;
	}
}
