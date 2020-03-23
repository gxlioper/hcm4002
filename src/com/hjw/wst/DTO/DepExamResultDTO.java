package com.hjw.wst.DTO;

public class DepExamResultDTO {

	private String dep_name;
	private String dep_num;
	private String item_name;
	private long item_id;
	private long id;
	private String exam_result;
	private String health_level;
	private String exam_doctor;
	private String exam_date;
	private String dep_category;
	private String ref_value;
	private String item_unit;
	private long sam_demo_id;
	private String exam_desc;
	private long critical_id;
	private int data_source;
	private long dep_id;
	private long charging_item_id;
	private long exam_item_id;
	private String item_code;
	private String item_num = "";
	
	private String dep="";
	private long exam_info_id;
	private String exam_status="";
	private String exam_status_y="";
	private String arch_num="";
	private String exam_num="";
	private String join_date="";
	private String req_id="";
	private String charge_item_id="";
	private long result_type; // 0表示普通、检验检查结果  2表示影像检查结果
	
	public long getResult_type() {
		return result_type;
	}
	public void setResult_type(long result_type) {
		this.result_type = result_type;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public long getCharging_item_id() {
		return charging_item_id;
	}
	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}
	public long getExam_item_id() {
		return exam_item_id;
	}
	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_num() {
		return item_num;
	}
	public void setItem_num(String item_num) {
		this.item_num = item_num;
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
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getItem_id() {
		return item_id;
	}
	public void setItem_id(long item_id) {
		this.item_id = item_id;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getHealth_level() {
		return health_level;
	}
	public void setHealth_level(String health_level) {
		this.health_level = health_level;
	}
	public String getExam_doctor() {
		return exam_doctor;
	}
	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}
	public String getExam_date() {
		return exam_date;
	}
	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
	public String getRef_value() {
		return ref_value;
	}
	public void setRef_value(String ref_value) {
		this.ref_value = ref_value;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public long getSam_demo_id() {
		return sam_demo_id;
	}
	public void setSam_demo_id(long sam_demo_id) {
		this.sam_demo_id = sam_demo_id;
	}
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public long getCritical_id() {
		return critical_id;
	}
	public void setCritical_id(long critical_id) {
		this.critical_id = critical_id;
	}
	public int getData_source() {
		return data_source;
	}
	public void setData_source(int data_source) {
		this.data_source = data_source;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public long getExam_info_id() {
		return exam_info_id;
	}
	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}
	public String getExam_status() {
		return exam_status;
	}
	public void setExam_status(String exam_status) {
		this.exam_status = exam_status;
		if("N".equals(exam_status)){
			this.setExam_status_y("未检");
		}else if("Y".equals(exam_status)){
			this.setExam_status_y("已检");
		}else if("G".equals(exam_status)){
			this.setExam_status_y("弃检");
		}else if("D".equals(exam_status)){
			this.setExam_status_y("延期");
		}else if("C".equals(exam_status)){
			this.setExam_status_y("已登记");
		}
	}
	public String getExam_status_y() {
		return exam_status_y;
	}
	public void setExam_status_y(String exam_status_y) {
		this.exam_status_y = exam_status_y;
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
	public String getJoin_date() {
		return join_date;
	}
	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public String getCharge_item_id() {
		return charge_item_id;
	}
	public void setCharge_item_id(String charge_item_id) {
		this.charge_item_id = charge_item_id;
	}
	
}
