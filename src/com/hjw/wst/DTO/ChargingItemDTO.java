package com.hjw.wst.DTO;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.DTO
 * @Description: 收费项目
 * @author: 张瑞
 * @date: 2016年9月27日 上午11:23:01
 * @version V2.0.0.0
 */
public class ChargingItemDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private long id;

	private long dep_id;

	private long sam_demo_id;

	private long sam_report_demo_id;

	private String item_code;

	private String item_name;

	private String item_pinyin;

	private String item_category;

	private String sex;

	private double amount;

	private String dep_category;

	private String isOnlyApplyOrReport;

	private Long item_seq;

	private String guide_category;

	private String his_num;

	private String exam_num;

	private String view_num;

	private String isActive;

	private double calculation_amount;

	private String interface_flag;

	private String item_type;

	private String charge_inter_num;

	private String item_abbreviation;

	private long creater;

	private String create_time;

	private long updater;

	private String update_time;

	private String dep_name;

	private double item_amount;

	private String notices; // 检查项目描述

	private String d_name; // 科室名称---所属科室

	private String s_name; // 检验样本名称--项目名称

	private String report_name; // 报告样本名称

	private String dname; // 科室类型-统计科室

	private String item_note; // 项目描述 新增字段

	private String perform_dept; // 执行科室编码 新增字段

	private String dept_code; // 执行科室编码
	private String dept_name; // 执行科室名称
	private String dept_class; // 执行科室类型
	private String input_code; // 执行科室拼音

	private double amounts;// 折后金额
	private double set_discountss;// 折扣
	private String item_class;//HIS诊疗项目类别
	private String item_class_cs;//HIS诊疗项目显示
	
	private String mccf="";//项目名称重复
	private String baogaochongfu="";//收费项目重复报告
	
	private long  limit_count;//项目上限
	private String limit_count_s;//项目上限
	private int itemnum;//
	private String startStop; //是否显示停用/启用
	private int demo_type;  //样本类型
	private int calculation_rate;
	private int limit_num;
	private double center_price;
	
	/**
	 * 系统发送申请
	 */
	private String  config_key;//系统表申请键
	private String  config_value;//值
	
	private String exam_status="已登记";
	private long eid;
	
	private String item_name_s;
	
	private String ischosen;
	private double price;
	
	private int app_type;
	private String d_app;
	private String app_type_s;
	
	private int item_discount; //项目折扣率
	private int charging_item_number;
	
	private String charging_item_code;
	
	private String center_num;

	private String center_name;


    public String getCenter_name() {
        return center_name;
    }

    public void setCenter_name(String center_name) {
        this.center_name = center_name;
    }

    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public int getCharging_item_number() {
		return charging_item_number;
	}

	public void setCharging_item_number(int charging_item_number) {
		this.charging_item_number = charging_item_number;
	}

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public String getApp_type_s() {
		return app_type_s;
	}

	public void setApp_type_s(String app_type_s) {
		this.app_type_s = app_type_s;
	}

	public String getD_app() {
		return d_app;
	}

	public void setD_app(String d_app) {
		this.d_app = d_app;
	}

	public int getApp_type() {
		return app_type;
	}

	public void setApp_type(int app_type) {
		this.app_type = app_type;
		if(app_type==1){
			this.setApp_type_s("健康体检");
		} else if(app_type==2){
			this.setApp_type_s("职业病体检");
		} else {
			this.setApp_type_s("通用");
		}
	}

	public String getIschosen() {
		return ischosen;
	}

	public void setIschosen(String ischosen) {
		this.ischosen = ischosen;
	}

	public String getItem_name_s() {
		return item_name_s;
	}

	public void setItem_name_s(String item_name_s) {
		this.item_name_s = item_name_s;
	}

	public long getEid() {
		return eid;
	}

	public void setEid(long eid) {
		this.eid = eid;
	}

	public String getExam_status() {
		return exam_status;
	}

	public void setExam_status(String exam_status) {
		this.exam_status ="已登记";
	}

	public int getItemnum() {
		return itemnum;
	}

	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}

	public String getLimit_count_s() {
		return limit_count_s;
	}

	public void setLimit_count_s(String limit_count_s) {
		this.limit_count_s = limit_count_s;
	}

	public long getLimit_count() {
		return limit_count;
	}

	public void setLimit_count(long limit_count) {
		this.limit_count = limit_count;
		this.setLimit_count_s(limit_count+"");
	}

	public String getItem_class_cs() {
		return item_class_cs;
	}

	public void setItem_class_cs(String item_class_cs) {
		this.item_class_cs = item_class_cs;
	}

	public String getItem_class() {
		return item_class;
	}

	public void setItem_class(String item_class) {
		this.item_class = item_class;
	}

	public String getBaogaochongfu() {
		return baogaochongfu;
	}

	public void setBaogaochongfu(String baogaochongfu) {
		this.baogaochongfu = baogaochongfu;
	}

	public String getConfig_key() {
		return config_key;
	}

	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}

	public String getConfig_value() {
		return config_value;
	}

	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}

	public String getMccf() {
		return mccf;
	}

	public void setMccf(String mccf) {
		this.mccf = mccf;
	}

	public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public double getItem_amount() {
		return item_amount;
	}

	public void setItem_amount(double item_amount) {
		this.item_amount = item_amount;
	}

	public String getDep_name() {
		return dep_name;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public long getSam_demo_id() {
		return sam_demo_id;
	}

	public void setSam_demo_id(long sam_demo_id) {
		this.sam_demo_id = sam_demo_id;
	}

	public long getSam_report_demo_id() {
		return sam_report_demo_id;
	}

	public void setSam_report_demo_id(long sam_report_demo_id) {
		this.sam_report_demo_id = sam_report_demo_id;
	}

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_pinyin() {
		return item_pinyin;
	}

	public void setItem_pinyin(String item_pinyin) {
		this.item_pinyin = item_pinyin;
	}

	public String getItem_category() {
		return item_category;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getDep_category() {
		return dep_category;
	}

	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}

	public String getIsOnlyApplyOrReport() {
		return isOnlyApplyOrReport;
	}

	public void setIsOnlyApplyOrReport(String isOnlyApplyOrReport) {
		this.isOnlyApplyOrReport = isOnlyApplyOrReport;
	}

	public Long getItem_seq() {
		return item_seq;
	}

	public void setItem_seq(Long item_seq) {
		this.item_seq = item_seq;
	}

	public String getGuide_category() {
		return guide_category;
	}

	public void setGuide_category(String guide_category) {
		this.guide_category = guide_category;
	}

	public String getHis_num() {
		return his_num;
	}

	public void setHis_num(String his_num) {
		this.his_num = his_num;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getView_num() {
		return view_num;
	}

	public void setView_num(String view_num) {
		this.view_num = view_num;
	}

	public double getCalculation_amount() {
		return calculation_amount;
	}

	public void setCalculation_amount(double calculation_amount) {
		this.calculation_amount = calculation_amount;
	}

	public String getInterface_flag() {
		return interface_flag;
	}

	public void setInterface_flag(String interface_flag) {
		this.interface_flag = interface_flag;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getCharge_inter_num() {
		return charge_inter_num;
	}

	public void setCharge_inter_num(String charge_inter_num) {
		this.charge_inter_num = charge_inter_num;
	}

	public String getItem_abbreviation() {
		return item_abbreviation;
	}

	public void setItem_abbreviation(String item_abbreviation) {
		this.item_abbreviation = item_abbreviation;
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

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getD_name() {
		return d_name;
	}

	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	public String getS_name() {
		return s_name;
	}

	public void setS_name(String s_name) {
		this.s_name = s_name;
	}

	public String getReport_name() {
		return report_name;
	}

	public void setReport_name(String report_name) {
		this.report_name = report_name;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getDept_class() {
		return dept_class;
	}

	public void setDept_class(String dept_class) {
		this.dept_class = dept_class;
	}

	public String getInput_code() {
		return input_code;
	}

	public void setInput_code(String input_code) {
		this.input_code = input_code;
	}

	public String getItem_note() {
		return item_note;
	}

	public void setItem_note(String item_note) {
		this.item_note = item_note;
	}

	public String getPerform_dept() {
		return perform_dept;
	}

	public void setPerform_dept(String perform_dept) {
		this.perform_dept = perform_dept;
	}

	public double getAmounts() {
		return amounts;
	}

	public void setAmounts(double amounts) {
		this.amounts = amounts;
	}

	public double getSet_discountss() {
		return set_discountss;
	}

	public void setSet_discountss(double set_discountss) {
		this.set_discountss = set_discountss;
	}

	public String getStartStop() {
		return startStop;
	}

	public void setStartStop(String startStop) {
		this.startStop = startStop;
	}

	public int getDemo_type() {
		return demo_type;
	}

	public void setDemo_type(int demo_type) {
		this.demo_type = demo_type;
	}

	public int getCalculation_rate() {
		return calculation_rate;
	}

	public void setCalculation_rate(int calculation_rate) {
		this.calculation_rate = calculation_rate;
	}

	public int getItem_discount() {
		return item_discount;
	}

	public void setItem_discount(int item_discount) {
		this.item_discount = item_discount;
	}

	public int getLimit_num() {
		return limit_num;
	}

	public void setLimit_num(int limit_num) {
		this.limit_num = limit_num;
	}

	public double getCenter_price() {
		return center_price;
	}

	public void setCenter_price(double center_price) {
		this.center_price = center_price;
	}

}