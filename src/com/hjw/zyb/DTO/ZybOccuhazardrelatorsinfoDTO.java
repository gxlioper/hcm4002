package com.hjw.zyb.DTO;

import java.io.Serializable;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月13日 上午9:24:55   
     * @version V2.0.0.0
 */
public class ZybOccuhazardrelatorsinfoDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String hazardfactorsID;//职业危害因素id
	private String occuphyexaclassID;//职业体检类别id
	private String hazardclassID;//职业危害类别
	private String diseaseandtaboo;
	private String checkcontent;
	private String checkcriterion;
	private String followdisease;
	private String examperiod;
	private String remark;
	private String occuphyexaclass_name;//职业体检类别名称
	private String hazard_name;//职业危害名称
	private String hazardclass_name;//职业危害类别
	private String hazard_code;
	private int hazard_year;
	
	public String getHazardfactorsID() {
		return hazardfactorsID;
	}
	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
	public String getOccuphyexaclassID() {
		return occuphyexaclassID;
	}
	public void setOccuphyexaclassID(String occuphyexaclassID) {
		this.occuphyexaclassID = occuphyexaclassID;
	}
	public String getHazardclassID() {
		return hazardclassID;
	}
	public void setHazardclassID(String hazardclassID) {
		this.hazardclassID = hazardclassID;
	}
	public String getHazardclass_name() {
		return hazardclass_name;
	}
	public void setHazardclass_name(String hazardclass_name) {
		this.hazardclass_name = hazardclass_name;
	}
	public String getOccuphyexaclass_name() {
		return occuphyexaclass_name;
	}
	public void setOccuphyexaclass_name(String occuphyexaclass_name) {
		this.occuphyexaclass_name = occuphyexaclass_name;
	}
	public String getHazard_name() {
		return hazard_name;
	}
	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDiseaseandtaboo() {
		return diseaseandtaboo;
	}
	public void setDiseaseandtaboo(String diseaseandtaboo) {
		this.diseaseandtaboo = diseaseandtaboo;
	}
	public String getCheckcontent() {
		return checkcontent;
	}
	public void setCheckcontent(String checkcontent) {
		this.checkcontent = checkcontent;
	}
	public String getCheckcriterion() {
		return checkcriterion;
	}
	public void setCheckcriterion(String checkcriterion) {
		this.checkcriterion = checkcriterion;
	}
	public String getFollowdisease() {
		return followdisease;
	}
	public void setFollowdisease(String followdisease) {
		this.followdisease = followdisease;
	}
	public String getExamperiod() {
		return examperiod;
	}
	public void setExamperiod(String examperiod) {
		this.examperiod = examperiod;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHazard_code() {
		return hazard_code;
	}

	public void setHazard_code(String hazard_code) {
		this.hazard_code = hazard_code;
	}

	public int getHazard_year() {
		return hazard_year;
	}

	public void setHazard_year(int hazard_year) {
		this.hazard_year = hazard_year;
	}
}
