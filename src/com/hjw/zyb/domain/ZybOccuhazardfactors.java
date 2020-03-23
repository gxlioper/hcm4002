package com.hjw.zyb.domain;

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
@Entity
@Table(name = "zyb_occuhazardfactors")
public class ZybOccuhazardfactors implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "hazardfactorsID", unique = true, nullable = false, length = 50)
	private String hazardfactorsID;
	
	 @Column(name="hazardclassID")
	private String hazardclassID;
	 
	 @Column(name="hazard_code")
	private String hazard_code;
	 
	 @Column(name="hazard_name")
	private String hazard_name;
	 
	 @Column(name="hazard_year")
	private long hazard_year;
	 
	 @Column(name="hazard_desc")
	private String hazard_desc;
	 
	 @Column(name="[order]")
	private long order;
	 
	 @Column(name="deffect")
	private String deffect;
	 
	 @Column(name="remark")
	private String remark;
	 
	 @Column(name="pycode")
	private String pycode;
	
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
