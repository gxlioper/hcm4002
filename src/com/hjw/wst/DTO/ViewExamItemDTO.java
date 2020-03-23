package com.hjw.wst.DTO;

import javax.persistence.Column;

public class ViewExamItemDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private long id;
	private long view_exam_detail_id;
	private long exam_item_id;
	private String exam_result;
	private String item_name;
    private String exam_num;
    private String item_num;
    
    private String exam_date;
	private String exam_doctor;
	private String item_category;
	private String dep_name;
	private double ref_Mmax;
	private double ref_Mmin;
	private double ref_Fmin;
	private double ref_Fmax;
	private String remark;
	private String item_unit;
    
    public String getItem_num() {
		return item_num;
	}

	public void setItem_num(String item_num) {
		this.item_num = item_num;
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
	public long getView_exam_detail_id() {
		return view_exam_detail_id;
	}
	public void setView_exam_detail_id(long view_exam_detail_id) {
		this.view_exam_detail_id = view_exam_detail_id;
	}
	public long getExam_item_id() {
		return exam_item_id;
	}
	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public String getExam_result() {
		return exam_result;
	}
	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getExam_date() {
		return exam_date;
	}

	public String getExam_doctor() {
		return exam_doctor;
	}

	public String getItem_category() {
		return item_category;
	}

	public String getDep_name() {
		return dep_name;
	}

	public double getRef_Mmax() {
		return ref_Mmax;
	}

	public double getRef_Mmin() {
		return ref_Mmin;
	}

	public double getRef_Fmin() {
		return ref_Fmin;
	}

	public double getRef_Fmax() {
		return ref_Fmax;
	}

	public String getRemark() {
		return remark;
	}

	public void setExam_date(String exam_date) {
		this.exam_date = exam_date;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}

	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}

	public void setRef_Mmax(double ref_Mmax) {
		this.ref_Mmax = ref_Mmax;
	}

	public void setRef_Mmin(double ref_Mmin) {
		this.ref_Mmin = ref_Mmin;
	}

	public void setRef_Fmin(double ref_Fmin) {
		this.ref_Fmin = ref_Fmin;
	}

	public void setRef_Fmax(double ref_Fmax) {
		this.ref_Fmax = ref_Fmax;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getItem_unit() {
		return item_unit;
	}

	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	
}
