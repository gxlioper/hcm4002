package com.hjw.zyb.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exam_ext_info")
public class ExamInfoExt implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 @Id  
	 @GeneratedValue(strategy = GenerationType.IDENTITY)  
	 private String exam_num;
	 
	 @Column(name = "arch_num")
	 private String arch_num;
	 
	 @Column(name = "occutypeofworkid")
	 private String occutypeofworkid;
	 
	 @Column(name = "occusectorid")
	 private String occusectorid;
	 
	 @Column(name = "occusector")
	 private String occusector;
	 
	 @Column(name = "occutypeofwork")
	 private String occutypeofwork;
	 
	 @Column(name = "joinDatetime")
	 private String joinDatetime;
	 
	 @Column(name = "employeeage")
	 private int employeeage;
	 
	 @Column(name = "damage")
	 private int damage;
	 
	 @Column(name = "remark")
	 private String remark;
	 
	 @Column(name = "creater")
	 private long creater;
	 
	 @Column(name = "create_time")
	 private String create_time;
	 
	 @Column(name = "updater")
	 private long updater;
	 
	 @Column(name = "update_time")
	 private String update_time;
	 
	 @Column(name = "bunk")
	 private String bunk;//床位
	 
	 @Column(name = "allocationdate")
	 private Date allocationdate;//分配时间
	 
	 @Column(name = "up_arch_num_person")
	 private long up_arch_num_person;
	 
	 @Column(name = "old_arch_num")
	 private String old_arch_num;
	 
	 @Column(name = "up_arch_num_time")
	 private String up_arch_num_time;
	 
	 @Column(name = "political_status")//政治面貌
	 private int political_status;
	 
	 @Column(name = "employeemonth")
	 private int employeemonth;
	 
	 @Column(name = "dammonth")
	 private int dammonth;
	 
	 
	public int getPolitical_status() {
		return political_status;
	}

	public void setPolitical_status(int political_status) {
		this.political_status = political_status;
	}

	public long getUp_arch_num_person() {
		return up_arch_num_person;
	}

	public void setUp_arch_num_person(long up_arch_num_person) {
		this.up_arch_num_person = up_arch_num_person;
	}

	public String getOld_arch_num() {
		return old_arch_num;
	}

	public void setOld_arch_num(String old_arch_num) {
		this.old_arch_num = old_arch_num;
	}

	public String getUp_arch_num_time() {
		return up_arch_num_time;
	}

	public void setUp_arch_num_time(String up_arch_num_time) {
		this.up_arch_num_time = up_arch_num_time;
	}

	public String getBunk() {
		return bunk;
	}

	public void setBunk(String bunk) {
		this.bunk = bunk;
	}

	public Date getAllocationdate() {
		return allocationdate;
	}

	public void setAllocationdate(Date allocationdate) {
		this.allocationdate = allocationdate;
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

	public String getOccutypeofworkid() {
		return occutypeofworkid;
	}

	public void setOccutypeofworkid(String occutypeofworkid) {
		this.occutypeofworkid = occutypeofworkid;
	}

	public String getOccusectorid() {
		return occusectorid;
	}

	public void setOccusectorid(String occusectorid) {
		this.occusectorid = occusectorid;
	}

	public String getOccusector() {
		return occusector;
	}

	public void setOccusector(String occusector) {
		this.occusector = occusector;
	}

	public String getOccutypeofwork() {
		return occutypeofwork;
	}

	public void setOccutypeofwork(String occutypeofwork) {
		this.occutypeofwork = occutypeofwork;
	}

	public String getJoinDatetime() {
		return joinDatetime;
	}

	public void setJoinDatetime(String joinDatetime) {
		this.joinDatetime = joinDatetime;
	}

	public int getEmployeeage() {
		return employeeage;
	}

	public void setEmployeeage(int employeeage) {
		this.employeeage = employeeage;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public int getEmployeemonth() {
		return employeemonth;
	}

	public void setEmployeemonth(int employeemonth) {
		this.employeemonth = employeemonth;
	}

	public int getDammonth() {
		return dammonth;
	}

	public void setDammonth(int dammonth) {
		this.dammonth = dammonth;
	}
	 
	 
	}