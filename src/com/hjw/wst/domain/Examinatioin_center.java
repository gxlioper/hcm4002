package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name = "examinatioin_center")
public class Examinatioin_center implements java.io.Serializable{
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	@Column(name = "center_num")
	private String center_num;
	
	@Column(name = "center_name")
	private String center_name;
	
	@Column(name = "is_active")
	private String is_active;
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_time")
	private Date create_time;
	
	@Column(name = "updater")
	private long updater;
	
	@Column(name = "update_time")
	private Date update_time;

	@Column(name = "photo_function_status")
	private String photo_function_status;
	
	@Column(name = "limit_count")
	private long  limit_count;

	@Column(name="parent_id")
	private long parent_id;
	
	@Column(name="notices")
	private String notices;
	
	public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}
	
	public long getLimit_count() {
		return limit_count;
	}

	public void setLimit_count(long limit_count) {
		this.limit_count = limit_count;
	}

	public Examinatioin_center() {
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getCenter_name() {
		return center_name;
	}

	public void setCenter_name(String center_name) {
		this.center_name = center_name;
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

	public String getPhoto_function_status() {
		return photo_function_status;
	}

	public void setPhoto_function_status(String photo_function_status) {
		this.photo_function_status = photo_function_status;
	}

	public long getParent_id() {
		return parent_id;
	}

	public void setParent_id(long parent_id) {
		this.parent_id = parent_id;
	}
}
