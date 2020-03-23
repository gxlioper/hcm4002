package com.hjw.zyb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="zyb_ask_diagnosis_sample")
public class ZybAskDiagnosisSample implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name="name")
	private String name;
	
	@Column(name="sub_name")
	private String sub_name;
	
	@Column(name="seq_code")
	private long seq_code; 
	
	@Column(name="type")
	private String type;
	
	@Column(name="temp_content")
	private String temp_content;
	
	@Column(name="is_active")
	private String is_active; 
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="creare_time")
	private Date creare_time;
	
	@Column(name = "updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="sex")
	private String sex;

	
	
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	public long getSeq_code() {
		return seq_code;
	}

	public void setSeq_code(long seq_code) {
		this.seq_code = seq_code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTemp_content() {
		return temp_content;
	}

	public void setTemp_content(String temp_content) {
		this.temp_content = temp_content;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreare_time() {
		return creare_time;
	}

	public void setCreare_time(Date creare_time) {
		this.creare_time = creare_time;
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
}
