package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "work_arrangement")
public class Scheduling implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
		@Id
		@GenericGenerator(name = "idGenerator", strategy = "identity")
		@GeneratedValue(generator = "idGenerator")
		private int id;

	    @Column(name = "user_id")
		private int user_id;

	    @Column(name = "working_date")
		private Date working_date;

	    @Column(name = "working_type")
	    private String working_type;
	    
	    @Column(name = "creater")
	    private long creater;
	    
	    @Column(name = "create_time")
	    private Date create_time;
	    
	    @Column(name = "updater")
	    private long updater;
	    
	    @Column(name = "update_time")
	    private Date update_time;
	    
	   public Scheduling() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getWorking_date() {
		return working_date;
	}

	public void setWorking_date(Date working_date) {
		this.working_date = working_date;
	}

	public String getWorking_type() {
		return working_type;
	}

	public void setWorking_type(String working_type) {
		this.working_type = working_type;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		

	}