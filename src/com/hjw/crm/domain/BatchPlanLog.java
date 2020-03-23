package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="batch_plan_log")
public class BatchPlanLog {
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="project_id")
	private String project_id;
	@Column(name="project_status")
	private String project_status;
	@Column(name="project_reason")
	private String project_reson;
	@Column(name="creater")
	private String creater;
	@Column(name="creater_time")
	private Date creater_time;
	@Column(name="project_name")
	private String project_name;
	@Column(name="project_type")
	private String project_type;
	@Column(name="type")
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject_id() {
		return project_id;
	}
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}
	public String getProject_status() {
		return project_status;
	}
	public void setProject_status(String project_status) {
		this.project_status = project_status;
	}

	public String getProject_reson() {
		return project_reson;
	}
	public void setProject_reson(String project_reson) {
		this.project_reson = project_reson;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getCreater_time() {
		return creater_time;
	}
	public void setCreater_time(Date creater_time) {
		this.creater_time = creater_time;
	}
	
	

}
