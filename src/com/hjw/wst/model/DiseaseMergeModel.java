package com.hjw.wst.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

/**
 * 疾病合并
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.domain
 * @Description:
 * @author: yangm
 * @date: 2017年5月5日 下午2:22:14
 * @version V2.0.0.0
 */
public class DiseaseMergeModel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	
	private static final long serialVersionUID = 1L;
	private long id;
	
	private String before_disease_id;
	
	private long later_disease_id;
	
	private String name;
	
	private long creater;
	
	private String create_time;
	
	private String disease_name;
	

	public String getDisease_name() {
		return disease_name;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBefore_disease_id() {
		return before_disease_id;
	}

	public void setBefore_disease_id(String before_disease_id) {
		this.before_disease_id = before_disease_id;
	}

	public long getLater_disease_id() {
		return later_disease_id;
	}

	public void setLater_disease_id(long later_disease_id) {
		this.later_disease_id = later_disease_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
}
