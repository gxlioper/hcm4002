package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "zyb_areacode")
public class ZybAreaCode implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
     private String areacode_codeID;
	 
	 @Column(name = "areacode_code")
     private String areacode_code;
	 
	 @Column(name = "areacode_name")
     private String areacode_name;
	
	 @Column(name = "parentID")
     private String parentID;
	 
	 @Column(name = "adminarea_code")
     private String adminarea_code;
	 

	public String getAreacode_codeID() {
		return areacode_codeID;
	}

	public void setAreacode_codeID(String areacode_codeID) {
		this.areacode_codeID = areacode_codeID;
	}

	public String getAreacode_code() {
		return areacode_code;
	}

	public void setAreacode_code(String areacode_code) {
		this.areacode_code = areacode_code;
	}

	public String getAreacode_name() {
		return areacode_name;
	}

	public void setAreacode_name(String areacode_name) {
		this.areacode_name = areacode_name;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

	public String getAdminarea_code() {
		return adminarea_code;
	}

	public void setAdminarea_code(String adminarea_code) {
		this.adminarea_code = adminarea_code;
	}

	
	 
	

}