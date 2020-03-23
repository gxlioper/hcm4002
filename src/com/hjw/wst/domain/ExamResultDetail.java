package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="exam_result_detail")
public class ExamResultDetail implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_info_id")
	private long exam_info_id;
	
	@Column(name="exam_item_id")
	private long exam_item_id;
	
	@Column(name="exam_category")
	private String exam_category;
	
	@Column(name="exam_item_category")
	private String exam_item_category;
	
	@Column(name="exam_doctor")
	private String exam_doctor;
	
	@Column(name="exam_date")
	private Date exam_date;
	
	@Column(name="exam_result")
	private String exam_result;
	
	@Column(name="dang_min")
	private String dang_min;
	
	@Column(name="dang_max")
	private String dang_max;
	
	@Column(name="ref_min")
	private String ref_min;
	
	@Column(name="ref_max")
	private String ref_max;
	
	@Column(name="ref_indicator")
	private String ref_indicator;
	
	@Column(name="center_num")
	private String center_num;
	
	@Column(name="approver")
	private String approver;
	
	@Column(name="approve_date")
	private Date approve_date;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="item_unit")
	private String item_unit;
	
	@Column(name="ref_value")
	private String ref_value;
	
	@Column(name="exam_num")
    private String exam_num;
	
	@Column(name="item_code")
    private String item_code;
    
	@Column(name="charging_item_code")
    private String charging_item_code;
	
	@Column(name="charging_item_id")
	private long charging_item_id;
	
    public long getCharging_item_id() {
		return charging_item_id;
	}

	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public String getExam_category() {
		return exam_category;
	}

	public void setExam_category(String exam_category) {
		this.exam_category = exam_category;
	}

	public String getExam_item_category() {
		return exam_item_category;
	}

	public void setExam_item_category(String exam_item_category) {
		this.exam_item_category = exam_item_category;
	}

	public String getExam_doctor() {
		return exam_doctor;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public Date getExam_date() {
		return exam_date;
	}

	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
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

	public String getRef_indicator() {
		return ref_indicator;
	}

	public void setRef_indicator(String ref_indicator) {
		this.ref_indicator = ref_indicator;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApprove_date() {
		return approve_date;
	}

	public void setApprove_date(Date approve_date) {
		this.approve_date = approve_date;
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

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}
	
	
}
