package com.hjw.wst.DTO;

public class CompanyInfoVsCenterDTO{
	
	private long company_id;
	private String center_num;
	private String is_owner;
	private long creater;
	private String create_time;
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getIs_owner() {
		return is_owner;
	}
	public void setIs_owner(String is_owner) {
		this.is_owner = is_owner;
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
