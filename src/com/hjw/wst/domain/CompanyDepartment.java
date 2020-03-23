package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Company_Department")
public class CompanyDepartment {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name = "company_Id")
	private long company_Id;
	
	@Column(name = "dep_Name")
	private String dep_Name;
	
	@Column(name = "creater")
	private Long creater;
	
	@Column(name = "create_Time")
	private Date create_Time;
	
	@Column(name = "updater")
	private Long updater;
	
	@Column(name = "update_Time")
	private Date update_Time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(long company_Id) {
		this.company_Id = company_Id;
	}

	public String getDep_Name() {
		return dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		this.dep_Name = dep_Name;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Date getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		this.create_Time = create_Time;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdate_Time() {
		return update_Time;
	}

	public void setUpdate_Time(Date update_Time) {
		this.update_Time = update_Time;
	}	
}
