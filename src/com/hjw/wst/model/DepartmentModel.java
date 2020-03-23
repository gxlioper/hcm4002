package com.hjw.wst.model;

import java.util.Date;
import java.util.List;

public class DepartmentModel implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	private int id;
	private String dep_num;
	private String dep_name;
	private String dep_category;
	private String sex;
	private int seq_code;
	private String remark;
	private String dep_link;
	private String isActive;
	private long creater;
	private Date create_time;
	private long updater;
	private Date update_time;
	private int isPrint_Barcode;
	private String dep_inter_num;
	private int calculation_rate;
	private String dep_ids;
	private String c_center_num;
	private String synchro;
	private String synchrodate;
	private int view_result_type;
	private String remark1;
	private String remark2;
	private String dep_address;
	private String isPrint_Barcode_c;
	private String vs_view_result_type;
	private String exam_num;
	private long exam_info_id;
	private String center_num;
	private String centerNums;


    public String getCenterNums() {
        return centerNums;
    }

    public void setCenterNums(String centerNums) {
        this.centerNums = centerNums;
    }

    public String getCenter_num() {
        return center_num;
    }

    public void setCenter_num(String center_num) {
        this.center_num = center_num;
    }

    public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getVs_view_result_type() {
		return vs_view_result_type;
	}

	public void setVs_view_result_type(String vs_view_result_type) {
		this.vs_view_result_type = vs_view_result_type;
	}

	public String getIsPrint_Barcode_c() {
		return isPrint_Barcode_c;
	}

	public void setIsPrint_Barcode_c(String isPrint_Barcode_c) {
		this.isPrint_Barcode_c = isPrint_Barcode_c;
	}

	public String getDep_address() {
		return dep_address;
	}

	public void setDep_address(String dep_address) {
		this.dep_address = dep_address;
	}

	public String getSynchro() {
		return synchro;
	}

	public void setSynchro(String synchro) {
		this.synchro = synchro;
	}

	public String getSynchrodate() {
		return synchrodate;
	}

	public void setSynchrodate(String synchrodate) {
		this.synchrodate = synchrodate;
	}

	public int getView_result_type() {
		return view_result_type;
	}

	public void setView_result_type(int view_result_type) {
		this.view_result_type = view_result_type;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public String getC_center_num() {
		return c_center_num;
	}

	public void setC_center_num(String c_center_num) {
		this.c_center_num = c_center_num;
	}

	public String getDep_ids() {
		return dep_ids;
	}

	public void setDep_ids(String dep_ids) {
		this.dep_ids = dep_ids;
	}

	public DepartmentModel() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getDep_category() {
		return dep_category;
	}

	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(int seq_code) {
		this.seq_code = seq_code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDep_link() {
		return dep_link;
	}

	public void setDep_link(String dep_link) {
		this.dep_link = dep_link;
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

	public int getIsPrint_Barcode() {
		return isPrint_Barcode;
	}

	public void setIsPrint_Barcode(int isPrint_Barcode) {
		this.isPrint_Barcode = isPrint_Barcode;
	}

	public String getDep_inter_num() {
		return dep_inter_num;
	}

	public void setDep_inter_num(String dep_inter_num) {
		this.dep_inter_num = dep_inter_num;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getCalculation_rate() {
		return calculation_rate;
	}

	public void setCalculation_rate(int calculation_rate) {
		this.calculation_rate = calculation_rate;
	}

}
