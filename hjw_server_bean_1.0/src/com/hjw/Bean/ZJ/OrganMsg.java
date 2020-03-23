package com.hjw.Bean.ZJ;

public class OrganMsg {
	private String organ_type="";//组织类型"
	private int organ_order;//":"组织显示顺序", 
	private String organ_id="";//":"组织id",
	private String organ_name="";//":"组织名称",
	private String parent="";//":"父组织id",
	private String parent_name="";//":"父组织名称"	
	public String getOrgan_type() {
		return organ_type;
	}
	public void setOrgan_type(String organ_type) {
		this.organ_type = organ_type;
	}
	public int getOrgan_order() {
		return organ_order;
	}
	public void setOrgan_order(int organ_order) {
		this.organ_order = organ_order;
	}
	public String getOrgan_id() {
		return organ_id;
	}
	public void setOrgan_id(String organ_id) {
		this.organ_id = organ_id;
	}
	public String getOrgan_name() {
		return organ_name;
	}
	public void setOrgan_name(String organ_name) {
		this.organ_name = organ_name;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	
	

}
