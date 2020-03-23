package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 体检与职业危害因素关系表
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月10日 上午10:08:06
 * @version V2.0.0.0
 */
@Entity
@Table(name = "Zyb_Exam_Occuhazardfactors")
public class ZybExamOccuhazardfactors implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	 @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	 
	 @Column(name = "exam_num")
	private String exam_num;
	 
	 @Column(name = "arch_num")
	private String arch_num;
	 
	 @Column(name = "hazard_code")
	private String hazard_code;
	 
	 @Column(name = "occuphyexaclassid")
	private String occuphyexaclassid;
		 
	@Column(name = "isActive")
	private String isActive;
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_time")
	private String create_time;
	
	@Column(name = "remark")
	private String remark;

	@Column(name = "hazard_year")
	private int hazard_year;

	public int getHazard_year() {
		return hazard_year;
	}

	public void setHazard_year(int hazard_year) {
		this.hazard_year = hazard_year;
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
	 
	
}
