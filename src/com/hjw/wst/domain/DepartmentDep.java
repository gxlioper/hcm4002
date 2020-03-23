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
     * @Description:  
     * @date:   2016年8月23日 上午11:55:59   
     * @version V2.0.0.0
 */
@Entity
@Table(name="department_dep")
public class DepartmentDep implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="dep_num")
	private String dep_num;
	
	@Column(name="dep_name")
	private String dep_name;
	
	@Column(name="dep_category")
	private String dep_category;
	
	@Column(name="sex")
	private String sex;
	
	@Column(name="seq_code")
	private int seq_code;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="dep_link")
	private String dep_link;
	
	@Column(name="isActive")
	private String isActive;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="isPrint_Barcode")
	private int isPrint_Barcode;
	
	@Column(name="dep_inter_num")
	private String dep_inter_num;
	
	@Column(name="calculation_rate")
	private int calculation_rate;
	
	@Column(name="teshu")
	private String teshu;	

	public DepartmentDep() {
		// TODO Auto-generated constructor stub
	}

	
	public String getTeshu() {
		return teshu;
	}


	public void setTeshu(String teshu) {
		this.teshu = teshu;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
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
