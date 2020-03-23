package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="crm_member_private_doctor")
//会员私人医生表
public class CrmMemberPrivateDoctor {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="arch_num",nullable=false,length=45)
	private String arch_num;
	@Column(name="doctor_id",nullable=false)
	private Long doctor_id;
	@Column(name="allot_person",nullable=false)
	private Long allot_person;
	@Column(name="allot_date",nullable=false)
	private Date allot_date;
	@Column(name="exam_num")
	private String exam_num;
	@Column(name="flag")
	private String flag;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public Long getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(Long doctor_id) {
		this.doctor_id = doctor_id;
	}
	public Long getAllot_person() {
		return allot_person;
	}
	public void setAllot_person(Long allot_person) {
		this.allot_person = allot_person;
	}
	public Date getAllot_date() {
		return allot_date;
	}
	public void setAllot_date(Date allot_date) {
		this.allot_date = allot_date;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
}
