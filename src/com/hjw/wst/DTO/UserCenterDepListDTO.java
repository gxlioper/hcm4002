package com.hjw.wst.DTO;

import java.util.List;

public class UserCenterDepListDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = -3565567339406922273L;
	
	private long center_id;
	private String center_num;
	private String center_name;
	private String chi_name;
	private String dep_name;
	private String current_date;
	private String user_uuid;
	private List<DepUserDTO> depUserList;
	
	
	public long getCenter_id() {
		return center_id;
	}
	public void setCenter_id(long center_id) {
		this.center_id = center_id;
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
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
	public String getDep_name() {
		return dep_name;
	}
	public void setDep_name(String dep_name) {
		this.dep_name = dep_name;
	}
	public List<DepUserDTO> getDepUserList() {
		return depUserList;
	}
	public void setDepUserList(List<DepUserDTO> depUserList) {
		this.depUserList = depUserList;
	}
	public String getCurrent_date() {
		return current_date;
	}
	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}
	public String getUser_uuid() {
		return user_uuid;
	}
	public void setUser_uuid(String user_uuid) {
		this.user_uuid = user_uuid;
	}
}
