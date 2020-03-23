package com.hjw.wst.DTO;

import java.util.Date;

import javax.persistence.Column;

public class ExamInfoExtDTO implements java.io.Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 7311059897146311125L;

	private String exam_num;
	 
	 private String arch_num;
	 
	 private String occutypeofworkid="";
	 
	 private String occusectorid="";
	 
	 private String occusector="";
	 
	 private String occutypeofwork="";
	 
	 private String joinDatetime;
	 
	 private int employeeage;
	 
	 private int damage;
	 
	 private String remark="";
	 
	 private long creater;
	 
	 private String create_time;
	 
	 private long updater;
	 
	 private String update_time;
	 
	 private String bunk="";//床位
	 
	 private String allocationdate;//分配时间
	 
	 private long up_arch_num_person;
	 
	 private String old_arch_num="";
	 
	 private String up_arch_num_time="";
	 
	 private int is_staff;
	 
	 private int political_status;
	 
	 private int treatment;
	 
	 private int src_funds;
	 
	 private String ti_name; 
		
	 private String ti_sex;
		
	 private int ti_age;
		
	 private String ti_id_num;
	 

	 public String getTi_name() {
		return ti_name;
	}

	public void setTi_name(String ti_name) {
		this.ti_name = ti_name;
	}

	public String getTi_sex() {
		return ti_sex;
	}

	public void setTi_sex(String ti_sex) {
		this.ti_sex = ti_sex;
	}

	public int getTi_age() {
		return ti_age;
	}

	public void setTi_age(int ti_age) {
		this.ti_age = ti_age;
	}

	public String getTi_id_num() {
		return ti_id_num;
	}

	public void setTi_id_num(String ti_id_num) {
		this.ti_id_num = ti_id_num;
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

	public String getBunk() {
		return bunk;
	}

	public void setBunk(String bunk) {
		this.bunk = bunk;
	}

	

	public String getAllocationdate() {
		return allocationdate;
	}

	public void setAllocationdate(String allocationdate) {
		this.allocationdate = allocationdate;
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

	public int getIs_staff() {
		return is_staff;
	}

	public void setIs_staff(int is_staff) {
		this.is_staff = is_staff;
	}

	public int getPolitical_status() {
		return political_status;
	}

	public void setPolitical_status(int political_status) {
		this.political_status = political_status;
	}

	public int getTreatment() {
		return treatment;
	}

	public void setTreatment(int treatment) {
		this.treatment = treatment;
	}

	public int getSrc_funds() {
		return src_funds;
	}

	public void setSrc_funds(int src_funds) {
		this.src_funds = src_funds;
	}

}
