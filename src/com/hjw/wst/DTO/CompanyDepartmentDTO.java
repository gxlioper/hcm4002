package com.hjw.wst.DTO;

import java.util.Date;

import com.hjw.util.Timeutils;

public class CompanyDepartmentDTO {

	private long id;

	private int company_Id;

	private String dep_Name;

	private int creater;

	private Date create_Time;
	
	private String create_Times;

	private int updater;

	private Date update_Time;
	
	private String update_Times;	

	public String getCreate_Times() {
		return create_Times;
	}

	public void setCreate_Times(String create_Times) {
		this.create_Times = create_Times;
	}

	public String getUpdate_Times() {
		return update_Times;
	}

	public void setUpdate_Times(String update_Times) {
		this.update_Times = update_Times;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(int company_Id) {
		this.company_Id = company_Id;
	}

	public String getDep_Name() {
		return dep_Name;
	}

	public void setDep_Name(String dep_Name) {
		this.dep_Name = dep_Name;
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public Date getCreate_Time() {
		return create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		this.create_Time = create_Time;
		if(create_Time!=null){
			this.setCreate_Times(Timeutils.dateToStrLong(create_Time));
		}
	}

	public int getUpdater() {
		return updater;
	}

	public void setUpdater(int updater) {
		this.updater = updater;
	}

	public Date getUpdate_Time() {
		return update_Time;
	}

	public void setUpdate_Time(Date update_Time) {
		this.update_Time = update_Time;
		if(update_Time!=null){
			this.setUpdate_Times(Timeutils.dateToStrLong(update_Time));
		}
	}	
}
