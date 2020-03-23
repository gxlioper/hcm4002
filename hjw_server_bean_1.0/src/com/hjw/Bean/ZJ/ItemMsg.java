package com.hjw.Bean.ZJ;

public class ItemMsg {
	private String organ_id="";//":"所属的组织id",  
	private String organ_code="";//":"组织编码",
	private String organ_name="";//":"组织名称",
	private long   dept_id;
	private String department_name="";//" : "科室名称",
	private String item_id="";//":"项目id",
	private String item_code="";//项目编码
	private String item_name="";//":"项目名称",
	private long item_order;//":项目显示顺序, 
	private String item_unit="";//":"项目单位名称",
	private String item_result="";//":"项目结果",
	private int item_resultType;//":项目结果类型, 1=定量(数值型) 	2=定性(结果值编号化:无排列顺序) 3=定性(結果值编号化:有排列顺序) 4=定性(文字说明)
	private String tem_dec_digits="";//":项目小数点位数, 
	private String item_low="";//":下限,
	private String item_hight="";//":上限,
	private String item_range="";//": 用于保存不规则的上下限,用于记录不规则上限或下限，如：	上下限为：>=5等，用该保存
	private String exam_time="";//":"检查时间", 格式yyyy-mm-dd hh:mm:ss
	private String doctor="";//":"检查医生",
	private int is_normal=1;//是否正常 1：正常	2：统称异常(有序和无序枚举值有异常使用该值)	3：低于下限4：高于上限
	private int item_type=1;// 1表示普通检查类，2表示检验类、3表示影像类
	private String health_level="";
	private String dang_min="";//	危机最大值
	private String dang_max="";//	危机最小值
	private String ref_min="";//	参考最大值
	private String ref_max="";//	参考最小值
    private String ref_value="";//参考范围
    private String approver="";//审核医生
    private String approve_date="";//审核日期
    private String exam_desc="";//项目描述
    private String pacs_id="";//pacsid 申请id      
    private String exam_item_id="";//检查项目id
    private String exam_item_code="";//检查项目编码
    private String exam_item_name="";//检查项目名称
    private long pacsimageid;      
	
	public long getPacsimageid() {
		return pacsimageid;
	}
	public void setPacsimageid(long pacsimageid) {
		this.pacsimageid = pacsimageid;
	}
	public String getExam_item_id() {
		return exam_item_id;
	}
	public void setExam_item_id(String exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public String getExam_item_code() {
		return exam_item_code;
	}
	public void setExam_item_code(String exam_item_code) {
		this.exam_item_code = exam_item_code;
	}
	public String getExam_item_name() {
		return exam_item_name;
	}
	public void setExam_item_name(String exam_item_name) {
		this.exam_item_name = exam_item_name;
	}
	public String getOrgan_code() {
		return organ_code;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public void setOrgan_code(String organ_code) {
		this.organ_code = organ_code;
	}
	
	public String getExam_desc() {
		return exam_desc;
	}
	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}
	public String getPacs_id() {
		return pacs_id;
	}
	public void setPacs_id(String pacs_id) {
		this.pacs_id = pacs_id;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApprove_date() {
		return approve_date;
	}
	public void setApprove_date(String approve_date) {
		this.approve_date = approve_date;
	}
	public String getRef_value() {
		return ref_value;
	}
	public void setRef_value(String ref_value) {
		this.ref_value = ref_value;
	}
	public String getDang_min() {
		return dang_min;
	}
	public void setDang_min(String dang_min) {
		this.dang_min = dang_min;
	}
	public String getDang_max() {
		return dang_max;
	}
	public void setDang_max(String dang_max) {
		this.dang_max = dang_max;
	}
	public String getRef_min() {
		return ref_min;
	}
	public void setRef_min(String ref_min) {
		this.ref_min = ref_min;
	}
	public String getRef_max() {
		return ref_max;
	}
	public void setRef_max(String ref_max) {
		this.ref_max = ref_max;
	}
	public String getHealth_level() {
		return health_level;
	}
	public void setHealth_level(String health_level) {
		this.health_level = health_level;
	}
	public long getDept_id() {
		return dept_id;
	}
	public void setDept_id(long dept_id) {
		this.dept_id = dept_id;
	}
	public int getItem_type() {
		return item_type;
	}
	public void setItem_type(int item_type) {
		this.item_type = item_type;
	}
	public String getOrgan_id() {
		return organ_id;
	}
	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}
	public String getOrgan_name() {
		return organ_name;
	}
	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}
	public String getDepartment_name() {
		return department_name;
	}
	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public long getItem_order() {
		return item_order;
	}
	public void setItem_order(long item_order) {
		this.item_order = item_order;
	}
	public String getItem_unit() {
		return item_unit;
	}
	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	public String getItem_result() {
		return item_result;
	}
	public void setItem_result(String item_result) {
		this.item_result = item_result;
	}
	public int getItem_resultType() {
		return item_resultType;
	}
	public void setItem_resultType(int item_resultType) {
		this.item_resultType = item_resultType;
	}
	public String getTem_dec_digits() {
		return tem_dec_digits;
	}
	public void setTem_dec_digits(String tem_dec_digits) {
		this.tem_dec_digits = tem_dec_digits;
	}
	public String getItem_low() {
		return item_low;
	}
	public void setItem_low(String item_low) {
		this.item_low = item_low;
	}
	public String getItem_hight() {
		return item_hight;
	}
	public void setItem_hight(String item_hight) {
		this.item_hight = item_hight;
	}
	public String getItem_range() {
		return item_range;
	}
	public void setItem_range(String item_range) {
		this.item_range = item_range;
	}
	public String getExam_time() {
		return exam_time;
	}
	public void setExam_time(String exam_time) {
		this.exam_time = exam_time;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public int getIs_normal() {
		return is_normal;
	}
	public void setIs_normal(int is_normal) {
		this.is_normal = is_normal;
	}

	
}
