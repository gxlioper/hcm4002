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
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description: 科室逻辑
 * @author: zr
 * @date: 2016年12月13日 上午9:13:11
 * @version V2.0.0.0
 */
@Entity
@Table(name = "dep_logic")
public class DepLogic implements java.io.Serializable {

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "dep_id")
	private long dep_id;

	@Column(name = "conclusion_word")
	private String conclusion_word;

	@Column(name = "creater")
	private long creater;

	@Column(name = "create_time")
	private Date create_time;

	@Column(name = "updater")
	private long updater;

	@Column(name = "update_time")
	private Date update_time;
	
	@Column(name="sex")
	private String sex;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}

	public String getConclusion_word() {
		return conclusion_word;
	}

	public void setConclusion_word(String conclusion_word) {
		this.conclusion_word = conclusion_word;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
}
