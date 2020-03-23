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
     * @Description: 结收导简单 
     * @author: zf    
     * @date:   2016年10月17日 下午3:39:44   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "examinfo_charging_item")
public class ExaminfoChargingItem implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private long id;

		@Column(name = "examinfo_id")
		private long examinfo_id;
		
		@Column(name = "charge_item_id")
		private long charge_item_id;
		
		@Column(name = "exam_indicator")
		private String exam_indicator;
		
		@Column(name = "item_amount")
		private double item_amount;
		
		@Column(name = "discount")
		private double discount;
		
		@Column(name = "amount")
		private double amount;
		
		@Column(name = "isActive")
		private String isActive;
		
		@Column(name = "final_exam_date")
		private Date final_exam_date;
		
		@Column(name = "pay_status")
		private String pay_status;
		
		@Column(name = "exam_status")
		private String exam_status;
		
		@Column(name = "is_new_added")
		private long is_new_added;
		
		@Column(name = "exam_date")
		private Date exam_date;
		
		@Column(name = "creater")
		private long creater;
		
		@Column(name = "create_time")
		private Date create_time;
		
		@Column(name = "updater")
		private long updater;
		
		@Column(name = "update_time")
		private Date update_time;
		
		@Column(name = "check_status")
		private long check_status;
		
		@Column(name = "exam_doctor_id")
		private long exam_doctor_id;
		
		@Column(name = "exam_doctor_name")
		private String exam_doctor_name;
		
		@Column(name = "add_status")
		private String add_status;
		
		@Column(name = "calculation_amount")
		private double calculation_amount;
		
		@Column(name = "calculation_rate")
		private int calculation_rate;
		
		@Column(name = "is_application")
		private String is_application;
		
		@Column(name = "change_item")
		private String change_item;
		
		@Column(name = "team_pay")
		private double team_pay;
		
		@Column(name = "personal_pay")
		private double personal_pay;
		
		@Column(name = "team_pay_status")
		private String team_pay_status;
		
		@Column(name = "his_req_status")
		private String his_req_status;
		
		@Column(name = "app_type")
		private String app_type="1";//1 表示普通体检，2表示职业病体检	
		
		@Column(name = "itemnum")
		private int itemnum=1;//项目个数，1 表示1项，不能为0
		
		@Column(name = "introducer")
		private int introducer;//项目介绍人
		
		@Column(name = "tixing_flag")
		private int tixing_flag;
		
		@Column(name="inputter")
		private long inputter;
		
		@Column(name="exam_num")
	    private String exam_num;
		
		@Column(name="tj_charge_amount")
		private double tj_charge_amount;
		
		@Column(name="tj_charge_status")
		private String tj_charge_status;
		
		@Column(name="his_charge_amount")
		private double his_charge_amount;
		
		@Column(name="his_charge_status")
		private String his_charge_status;
		
		@Column(name="pay_mode")
		private String pay_mode;
		
		@Column(name="charging_item_code")
		private String charging_item_code;
	    
		@Column(name="center_num")
		private String center_num;
		
		@Column(name="exam_center_num")
		private String exam_center_num;
		
	    public String getCharging_item_code() {
			return charging_item_code;
		}

		public void setCharging_item_code(String charging_item_code) {
			this.charging_item_code = charging_item_code;
		}

		public String getExam_num() {
			return exam_num;
		}

		public void setExam_num(String exam_num) {
			this.exam_num = exam_num;
		}

	    public int getItemnum() {
			return itemnum;
		}

		public void setItemnum(int itemnum) {
			this.itemnum = itemnum;
		}
	    
		public String getApp_type() {
			return app_type;
		}

		public void setApp_type(String app_type) {
			this.app_type = app_type;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public long getExaminfo_id() {
			return examinfo_id;
		}

		public void setExaminfo_id(long examinfo_id) {
			this.examinfo_id = examinfo_id;
		}

		public long getCharge_item_id() {
			return charge_item_id;
		}

		public void setCharge_item_id(long charge_item_id) {
			this.charge_item_id = charge_item_id;
		}

		public String getExam_indicator() {
			return exam_indicator;
		}

		public void setExam_indicator(String exam_indicator) {
			this.exam_indicator = exam_indicator;
		}

		public double getItem_amount() {
			return item_amount;
		}

		public void setItem_amount(double item_amount) {
			this.item_amount = item_amount;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
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

		public Date getFinal_exam_date() {
			return final_exam_date;
		}

		public void setFinal_exam_date(Date final_exam_date) {
			this.final_exam_date = final_exam_date;
		}

		public String getPay_status() {
			return pay_status;
		}

		public void setPay_status(String pay_status) {
			this.pay_status = pay_status;
		}

		public String getExam_status() {
			return exam_status;
		}

		public void setExam_status(String exam_status) {
			this.exam_status = exam_status;
		}

		public long getIs_new_added() {
			return is_new_added;
		}

		public void setIs_new_added(long is_new_added) {
			this.is_new_added = is_new_added;
		}

		public Date getExam_date() {
			return exam_date;
		}

		public void setExam_date(Date exam_date) {
			this.exam_date = exam_date;
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

		public long getCheck_status() {
			return check_status;
		}

		public void setCheck_status(long check_status) {
			this.check_status = check_status;
		}

		public long getExam_doctor_id() {
			return exam_doctor_id;
		}

		public void setExam_doctor_id(long exam_doctor_id) {
			this.exam_doctor_id = exam_doctor_id;
		}

		public String getExam_doctor_name() {
			return exam_doctor_name;
		}

		public void setExam_doctor_name(String exam_doctor_name) {
			this.exam_doctor_name = exam_doctor_name;
		}

		public String getAdd_status() {
			return add_status;
		}

		public void setAdd_status(String add_status) {
			this.add_status = add_status;
		}

		public double getCalculation_amount() {
			return calculation_amount;
		}

		public void setCalculation_amount(double calculation_amount) {
			this.calculation_amount = calculation_amount;
		}

		public String getIs_application() {
			return is_application;
		}

		public void setIs_application(String is_application) {
			this.is_application = is_application;
		}

		public String getChange_item() {
			return change_item;
		}

		public void setChange_item(String change_item) {
			this.change_item = change_item;
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

		public String getTeam_pay_status() {
			return team_pay_status;
		}

		public void setTeam_pay_status(String team_pay_status) {
			this.team_pay_status = team_pay_status;
		}

		public String getHis_req_status() {
			return his_req_status;
		}

		public void setHis_req_status(String his_req_status) {
			this.his_req_status = his_req_status;
		}

		public int getIntroducer() {
			return introducer;
		}

		public int getTixing_flag() {
			return tixing_flag;
		}

		public long getInputter() {
			return inputter;
		}

		public void setIntroducer(int introducer) {
			this.introducer = introducer;
		}

		public void setTixing_flag(int tixing_flag) {
			this.tixing_flag = tixing_flag;
		}

		public void setInputter(long inputter) {
			this.inputter = inputter;
		}

		public int getCalculation_rate() {
			return calculation_rate;
		}

		public void setCalculation_rate(int calculation_rate) {
			this.calculation_rate = calculation_rate;
		}

		public double getTj_charge_amount() {
			return tj_charge_amount;
		}

		public void setTj_charge_amount(double tj_charge_amount) {
			this.tj_charge_amount = tj_charge_amount;
		}

		public String getTj_charge_status() {
			return tj_charge_status;
		}

		public void setTj_charge_status(String tj_charge_status) {
			this.tj_charge_status = tj_charge_status;
		}

		public double getHis_charge_amount() {
			return his_charge_amount;
		}

		public void setHis_charge_amount(double his_charge_amount) {
			this.his_charge_amount = his_charge_amount;
		}

		public String getHis_charge_status() {
			return his_charge_status;
		}

		public void setHis_charge_status(String his_charge_status) {
			this.his_charge_status = his_charge_status;
		}

		public String getPay_mode() {
			return pay_mode;
		}

		public void setPay_mode(String pay_mode) {
			this.pay_mode = pay_mode;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}

		public String getCenter_num() {
			return center_num;
		}

		public String getExam_center_num() {
			return exam_center_num;
		}

		public void setCenter_num(String center_num) {
			this.center_num = center_num;
		}

		public void setExam_center_num(String exam_center_num) {
			this.exam_center_num = exam_center_num;
		}
	}