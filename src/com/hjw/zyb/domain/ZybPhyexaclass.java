package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_physicalexaminationclass")
public class ZybPhyexaclass implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	/* @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")*/
	 @Id
	    @GeneratedValue(generator = "paymentableGenerator")
	    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
		@Column(name = "phyexaclassID", unique = true, nullable = false,length = 50)
	 private String phyexaclassID;
	 
	 @Column(name = "phyexaclass_code")
     private String phyexaclass_code;
	 
	 @Column(name = "phyexaclass_name")
     private String phyexaclass_name;
	 
	 @Column(name = "remark")
     private String remark;
	 
	 @Column(name = "isprintcard")
     private String isprintcard;
	 
	 @Column(name = "center_num")
     private String center_num;
	 
	 @Column(name = "isupload")
     private String isupload;
	 
	 @Column(name = "showorder")
     private long showorder;
	 
	 public long getShoworder() {
		return showorder;
	}

	public void setShoworder(long showorder) {
		this.showorder = showorder;
	}

	public ZybPhyexaclass() {
		// TODO Auto-generated constructor stub
	}

	public String getPhyexaclassID() {
		return phyexaclassID;
	}

	public void setPhyexaclassID(String phyexaclassID) {
		this.phyexaclassID = phyexaclassID;
	}

	public String getPhyexaclass_code() {
		return phyexaclass_code;
	}

	public void setPhyexaclass_code(String phyexaclass_code) {
		this.phyexaclass_code = phyexaclass_code;
	}

	public String getPhyexaclass_name() {
		return phyexaclass_name;
	}

	public void setPhyexaclass_name(String phyexaclass_name) {
		this.phyexaclass_name = phyexaclass_name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getIsprintcard() {
		return isprintcard;
	}

	public void setIsprintcard(String isprintcard) {
		this.isprintcard = isprintcard;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getIsupload() {
		return isupload;
	}

	public void setIsupload(String isupload) {
		this.isupload = isupload;
	}


			}