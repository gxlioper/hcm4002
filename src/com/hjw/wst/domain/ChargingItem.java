package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: 收费项目    
     * @date:   2016年9月25日 下午4:08:22   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "charging_item")
public class ChargingItem implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "dep_id")
		private long dep_id;
		
		@Column(name = "sam_demo_id")
		private Long sam_demo_id;
		
		@Column(name = "sam_report_demo_id")
		private Long sam_report_demo_id;
		
		@Column(name = "item_code")
		private String item_code;
		
		@Column(name = "item_name")
		private String item_name;
		
		@Column(name = "item_pinyin")
		private String item_pinyin;
		
		@Column(name = "item_category")
		private String item_category;
		
		@Column(name = "sex")
		private String sex;
		
		@Column(name = "amount")
		private Double amount;
		
		@Column(name = "dep_category")
		private String dep_category;
		
		@Column(name = "isOnlyApplyOrReport")
		private String isOnlyApplyOrReport;
		
		@Column(name = "item_seq")
		private Long item_seq;
		
		@Column(name = "guide_category")
		private String guide_category;
		
		@Column(name = "his_num")
		private String his_num;
		
		@Column(name = "exam_num")
		private String exam_num;
		
		@Column(name = "view_num")
		private String view_num;
		
		@Column(name = "isActive")
		private String isActive;
		
		@Column(name = "calculation_amount")
		private Double calculation_amount;
		
		@Column(name = "interface_flag")
		private String interface_flag;
		
		@Column(name = "item_type")
		private String item_type;
		
		@Column(name = "charge_inter_num")
		private String charge_inter_num;
		
		@Column(name = "item_abbreviation")
		private String item_abbreviation;
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "create_time")
		private Date create_time;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private Date update_time;
		
		@Column(name = "notices")
		private String notices;//检查项目描述	
		
		@Column(name="item_note")
		private String item_note;//项目描述	新增字段
		
		@Column(name="perform_dept")
		private String perform_dept;//执行科室编码   新增字段
		
		@Column(name="item_class")
		private String item_class;//his诊疗项目类别
		
		@Column(name="remark")
		private String remark;
		
		@Column(name="charging_item_number")//限制次数
		private long charging_item_number;
		
		
		@Column(name="finance_class")//财务类别
		private long finance_class;
		
		@Column(name="app_type")//应用类型
	    private int app_type;
		
		@Column(name="item_discount")
		private int item_discount; //项目折扣率
		
		@Column(name="calculation_rate")
		private int calculation_rate;

		@Column(name="hiscodeClass")
		private String hiscodeClass;//1：价表项目，2：诊疗项目 和his对接时候用

        @Column(name="limit_num")
        private int limit_num;

    public int getLimit_num() {
        return limit_num;
    }

    public void setLimit_num(int limit_num) {
        this.limit_num = limit_num;
    }

    public int getApp_type() {
			return app_type;
		}

		public void setApp_type(int app_type) {
			this.app_type = app_type;
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

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getItem_class() {
			return item_class;
		}

		public void setItem_class(String item_class) {
			this.item_class = item_class;
		}

		public String getNotices() {
			return notices;
		}

		public void setNotices(String notices) {
			this.notices = notices;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public Double getAmount() {
			return amount;
		}

		public void setAmount(Double amount) {
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

		public Long getSam_demo_id() {
			return sam_demo_id;
		}

		public void setSam_demo_id(Long sam_demo_id) {
			this.sam_demo_id = sam_demo_id;
		}

		public Long getSam_report_demo_id() {
			return sam_report_demo_id;
		}

		public void setSam_report_demo_id(Long sam_report_demo_id) {
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

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
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

		public int getItem_discount() {
			return item_discount;
		}

		public void setItem_discount(int item_discount) {
			this.item_discount = item_discount;
		}

		public int getCalculation_rate() {
			return calculation_rate;
		}

		public void setCalculation_rate(int calculation_rate) {
			this.calculation_rate = calculation_rate;
		}

		public String getHiscodeClass() {
			return hiscodeClass;
		}

		public void setHiscodeClass(String hiscodeClass) {
			this.hiscodeClass = hiscodeClass;
		}	
		
	}