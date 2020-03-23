package com.hjw.wst.model;

import java.util.Date;

import javax.persistence.Column;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.model   
     * @Description:  收费项目
     * @author: 张瑞    
     * @date:   2016年9月25日 下午5:43:35   
     * @version V2.0.0.0
 */
public class ChargingItemModel implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	private long id;

	private long dep_id;
	
	private long sam_demo_id;
	
	private Long sam_report_demo_id;
	
	private String item_code;
	
	private String item_name;
	
	private String item_pinyin;
	
	private String item_category;
	
	private String sex;
	
	private Double amount;
	
	private String dep_category;
	
	private String isOnlyApplyOrReport;
	
	private Long item_seq;
	
	private String guide_category;
	
	private String his_num;
	
	private String exam_num;
	
	private String view_num;
	
	private String isActive;
	
	private Double calculation_amount;
	
	private String interface_flag;
	
	private String item_type;
	
	private String charge_inter_num;
	
	private String item_abbreviation;
	
	private long creater;
	
	private Date create_time;
	
	private long updater;
	
	private Date update_time;
	
	private String notices;//检查项目描述	
	
	private String item_note;//项目描述	新增字段
	
	private String perform_dept;//执行科室编码   新增字段
	
	private String charge_item_code;//对应价表项目代码
	
	private String item_code_c; //诊疗项目代码
	
	private String item_name_c; //诊疗项目名称
	
	private String item_class_c;//诊疗项目类别
	
	private String item_class_cs;//
	
	private String item_class;//HIS诊疗项目类别
	private String item_code_s;
	
	private String mccf="";//项目名称 重复
	private String baogaochongfu;//重复报告
	
	private  long   limit_count;//项目上限
	private  String   limit_count_s;//项目上限
	private String remark;
	
	private String startStop; //是否显示停用/启用
	
	private int demo_type;
	private double price;
	
	/**
	 * 系统发送申请
	 */
	private String  config_key;//系统表申请键
	private String  hisStatus;//值
	private String  lisStatus;//
	private String  pacsStatus;//
	private String lisid = "";
	private String lisclassname = "";
	private String create_time2 = "";
	
	private String Schedule_time;
	private String jid;
	
	private String item_name_s;
	private long charging_item_number;
	private long finance_class;
	private long schedule_number;
	
	private int user_type;
	private int app_type;
	private String d_app;
	private int calculation_rate;	
	private String his_num_show="";
	private int item_discount; //项目折扣率
    private String center_num;

    public String getCenter_num() {
        return center_num;
    }

    public void setCenter_num(String center_num) {
        this.center_num = center_num;
    }

    public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getItem_code_s() {
		return item_code_s;
	}

	public void setItem_code_s(String item_code_s) {
		this.item_code_s = item_code_s;
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
	}

	public int getUser_type() {
		return user_type;
	}

	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}

	public long getSchedule_number() {
		return schedule_number;
	}

	public void setSchedule_number(long schedule_number) {
		this.schedule_number = schedule_number;
	}

	public long getFinance_class() {
		return finance_class;
	}

	public void setFinance_class(long finance_class) {
		this.finance_class = finance_class;
	}



	public long getCharging_item_number() {
		return charging_item_number;
	}

	public void setCharging_item_number(long charging_item_number) {
		this.charging_item_number = charging_item_number;
	}

	public String getItem_name_s() {
		return item_name_s;
	}

	public void setItem_name_s(String item_name_s) {
		this.item_name_s = item_name_s;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getSchedule_time() {
		return Schedule_time;
	}

	public void setSchedule_time(String schedule_time) {
		Schedule_time = schedule_time;
	}

	public String getLisid() {
		return lisid;
	}

	public void setLisid(String lisid) {
		this.lisid = lisid;
	}

	public String getLisclassname() {
		return lisclassname;
	}

	public void setLisclassname(String lisclassname) {
		this.lisclassname = lisclassname;
	}

	public String getCreate_time2() {
		return create_time2;
	}

	public void setCreate_time2(String create_time2) {
		this.create_time2 = create_time2;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public String getItem_class_c() {
		return item_class_c;
	}

	public void setItem_class_c(String item_class_c) {
		this.item_class_c = item_class_c;
	}

	public String getBaogaochongfu() {
		return baogaochongfu;
	}

	public void setBaogaochongfu(String baogaochongfu) {
		this.baogaochongfu = baogaochongfu;
	}

	public String getHisStatus() {
		return hisStatus;
	}

	public void setHisStatus(String hisStatus) {
		this.hisStatus = hisStatus;
	}

	public String getLisStatus() {
		return lisStatus;
	}

	public void setLisStatus(String lisStatus) {
		this.lisStatus = lisStatus;
	}

	public String getPacsStatus() {
		return pacsStatus;
	}

	public void setPacsStatus(String pacsStatus) {
		this.pacsStatus = pacsStatus;
	}

	public String getConfig_key() {
		return config_key;
	}

	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}


	public String getMccf() {
		return mccf;
	}

	public void setMccf(String mccf) {
		this.mccf = mccf;
	}

	public String getItem_code_c() {
		return item_code_c;
	}

	public void setItem_code_c(String item_code_c) {
		this.item_code_c = item_code_c;
	}

	public String getItem_name_c() {
		return item_name_c;
	}

	public void setItem_name_c(String item_name_c) {
		this.item_name_c = item_name_c;
	}

	public String getCharge_item_code() {
		return charge_item_code;
	}

	public void setCharge_item_code(String charge_item_code) {
		this.charge_item_code = charge_item_code;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Double getCalculation_amount() {
		return calculation_amount;
	}

	public void setCalculation_amount(Double calculation_amount) {
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

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
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

	public String getHis_num_show() {
		return his_num_show;
	}

	public void setHis_num_show(String his_num_show) {
		this.his_num_show = his_num_show;
	}

	public int getItem_discount() {
		return item_discount;
	}

	public void setItem_discount(int item_discount) {
		this.item_discount = item_discount;
	}

	public long getSam_demo_id() {
		return sam_demo_id;
	}

	public void setSam_demo_id(long sam_demo_id) {
		this.sam_demo_id = sam_demo_id;
	}

	public Long getSam_report_demo_id() {
		return sam_report_demo_id;
	}

	public void setSam_report_demo_id(Long sam_report_demo_id) {
		this.sam_report_demo_id = sam_report_demo_id;
	}
	
}
