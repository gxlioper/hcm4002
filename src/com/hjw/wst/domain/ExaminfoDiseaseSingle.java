package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="examinfo_disease_single")  //单项阳性表
public class ExaminfoDiseaseSingle {
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="disease_num")
	private String disease_num;

	@Column(name="dep_num")
	private String dep_num;
	
	@Column(name="charging_item_code")
	private String charging_item_code;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="item_code")
	private String item_code;
	
	@Column(name="disease_name")
	private String disease_name;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	public String getId() {
		return id;
	}

	public String getDisease_num() {
		return disease_num;
	}

	public String getCharging_item_code() {
		return charging_item_code;
	}

	public String getExam_num() {
		return exam_num;
	}

	public String getItem_code() {
		return item_code;
	}

	public String getDisease_name() {
		return disease_name;
	}

	public long getCreater() {
		return creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDisease_num(String disease_num) {
		this.disease_num = disease_num;
	}

	public void setCharging_item_code(String charging_item_code) {
		this.charging_item_code = charging_item_code;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getDep_num() {
		return dep_num;
	}

	public void setDep_num(String dep_num) {
		this.dep_num = dep_num;
	}
	
}
