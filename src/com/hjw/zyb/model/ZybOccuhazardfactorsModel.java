package com.hjw.zyb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 职业危害因素管理
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月1日 上午11:22:07
 * @version V2.0.0.0
 */
public class ZybOccuhazardfactorsModel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String hazardfactorsID;
	
	private String hazardclassID;
	 
	private String hazard_code;
	 
	private String hazard_name;
	 
	private long hazard_year;
	 
	private String hazard_desc;
	 
	private long order;
	 
	private String deffect;
	 
	private String remark;
	 
	private String pycode;
	
	private String hazardclass_name;
	
	private String order2;
	
	
	
	public String getOrder2() {
		return order2;
	}
	public void setOrder2(String order2) {
		this.order2 = order2;
	}
	public String getHazardclass_name() {
		return hazardclass_name;
	}
	public void setHazardclass_name(String hazardclass_name) {
		this.hazardclass_name = hazardclass_name;
	}
	public String getHazardfactorsID() {
		return hazardfactorsID;
	}
	public String getHazardclassID() {
		return hazardclassID;
	}
	public String getHazard_code() {
		return hazard_code;
	}
	public String getHazard_name() {
		return hazard_name;
	}
	public long getHazard_year() {
		return hazard_year;
	}
	public String getHazard_desc() {
		return hazard_desc;
	}
	public long getOrder() {
		return order;
	}
	public String getDeffect() {
		return deffect;
	}
	public String getRemark() {
		return remark;
	}
	public String getPycode() {
		return pycode;
	}
	public void setHazardfactorsID(String hazardfactorsID) {
		this.hazardfactorsID = hazardfactorsID;
	}
	public void setHazardclassID(String hazardclassID) {
		this.hazardclassID = hazardclassID;
	}
	public void setHazard_code(String hazard_code) {
		this.hazard_code = hazard_code;
	}
	public void setHazard_name(String hazard_name) {
		this.hazard_name = hazard_name;
	}
	public void setHazard_year(long hazard_year) {
		this.hazard_year = hazard_year;
	}
	public void setHazard_desc(String hazard_desc) {
		this.hazard_desc = hazard_desc;
	}
	public void setOrder(long order) {
		this.order = order;
	}
	public void setDeffect(String deffect) {
		this.deffect = deffect;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setPycode(String pycode) {
		this.pycode = pycode;
	}
	
	
}
