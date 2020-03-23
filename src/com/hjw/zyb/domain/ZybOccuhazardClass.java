package com.hjw.zyb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/***
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description: 职业危害类别管理
 * @author: zr
 * @date: 2017年3月29日 下午4:45:32
 * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_occuhazardclass")
public class ZybOccuhazardClass implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "hazardclassID", unique = true, nullable = false, length = 50)
	private String hazardclassID;

	@Column(name = "hazardclass_code")
	private String hazardclass_code;

	@Column(name = "hazardclass_name")
	private String hazardclass_name;

	@Column(name = "[order]")
	private long order;

	@Column(name = "remark")
	private String remark;

	public String getHazardclassID() {
		return hazardclassID;
	}

	public String getHazardclass_code() {
		return hazardclass_code;
	}

	public String getHazardclass_name() {
		return hazardclass_name;
	}

	public long getOrder() {
		return order;
	}

	public String getRemark() {
		return remark;
	}

	public void setHazardclassID(String hazardclassID) {
		this.hazardclassID = hazardclassID;
	}

	public void setHazardclass_code(String hazardclass_code) {
		this.hazardclass_code = hazardclass_code;
	}

	public void setHazardclass_name(String hazardclass_name) {
		this.hazardclass_name = hazardclass_name;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
