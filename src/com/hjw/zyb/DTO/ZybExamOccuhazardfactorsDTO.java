package com.hjw.zyb.DTO;

import java.io.Serializable;

public class ZybExamOccuhazardfactorsDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	 
	private String exam_num;

	private String arch_num;

	private String hazard_code;
	
	private String hazard_name;

	private String occuphyexaclassid;
	
	private String occuphyexaclass_name;

	private String isActive;

	private long creater;

	private String create_time;

	private String remark;	
	
	private int hazard_year;
	
	private String hazard_desc;
	
	private String deffect;
	
	private String hazardfactorsID;


	public int getHazard_year() {
		return hazard_year;
	}

	public void setHazard_year(int hazard_year) {
		this.hazard_year = hazard_year;
	}

	public String getHazard_desc() {
		return hazard_desc;
	}

	public void setHazard_desc(String hazard_desc) {
		this.hazard_desc = hazard_desc;
	}

	public String getDeffect() {
		return deffect;
	}

	public void setDeffect(String deffect) {
		this.deffect = deffect;
	}

	public String getHazard_name() {
		return hazard_name;
	}

	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}

	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}

	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getHazard_code() {
		return hazard_code;
	}

	public void setHazard_code(String hazard_code) {
		this.hazard_code = hazard_code;
	}

	public String getOccuphyexaclassid() {
		return occuphyexaclassid;
	}

	public void setOccuphyexaclassid(String occuphyexaclassid) {
		this.occuphyexaclassid = occuphyexaclassid;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHazardfactorsID() {
		return hazardfactorsID;
	}

	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
}
