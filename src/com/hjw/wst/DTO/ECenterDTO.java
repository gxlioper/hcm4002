package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

import com.synjones.framework.aop.SystemType;

public class ECenterDTO {
	private long id;
	private String center_num;
	private String center_name;
	private List<SystemType> syslist=new ArrayList<SystemType>();
	
	public List<SystemType> getSyslist() {
		return syslist;
	}
	public void setSyslist(List<SystemType> syslist) {
		this.syslist = syslist;
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
	
}