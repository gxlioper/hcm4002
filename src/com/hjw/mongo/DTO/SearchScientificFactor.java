package com.hjw.mongo.DTO;

public class SearchScientificFactor {
	private long id;
	private String title;
	private long creater;
	private String create_time;
	private String remark;
	private String conditions;
	private String conditions1;
	private String center_num;	
	
	public String getConditions1() {
		return conditions1;
	}
	public void setConditions1(String conditions1) {
		this.conditions1 = conditions1;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	
	
}
