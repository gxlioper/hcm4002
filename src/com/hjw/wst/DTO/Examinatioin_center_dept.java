package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class Examinatioin_center_dept {
	private long id;
	private String text;
	private String center_name;
	private String check;
	private List<Examinatioin_center_dept> children=new ArrayList<Examinatioin_center_dept>();
	

	public List<Examinatioin_center_dept> getChildren() {
		return children;
	}
	public void setChildren(List<Examinatioin_center_dept> children) {
		this.children = children;
	}
	
	public String getCenter_name() {
		return center_name;
	}
	public void setCenter_name(String center_name) {
		this.center_name = center_name;
	}
	public String getCheck() {
		return check;
	}
	public void setCheck(String check) {
		this.check = check;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

}
