package com.hjw.wst.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class CompanyInfoVsCenterPk implements Serializable{
	
	private int company_id;
	private String center_num;
	public int getCompany_id() {
		return company_id;
	}
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	
	
}
