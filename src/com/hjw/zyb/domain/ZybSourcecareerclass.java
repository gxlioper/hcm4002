package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_source_career_class")
public class ZybSourcecareerclass implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "sc_classid", unique = true, nullable = false,length = 50)
     private String sc_classid;
	 
	 @Column(name = "sourceid")
     private String sourceid;
	 
	 @Column(name = "sc_classcode")
     private String sc_classcode;
	
	 @Column(name = "sc_classname")
	 private String sc_classname;

	public String getSc_classid() {
		return sc_classid;
	}

	public void setSc_classid(String sc_classid) {
		this.sc_classid = sc_classid;
	}

	public String getSourceid() {
		return sourceid;
	}

	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	public String getSc_classcode() {
		return sc_classcode;
	}

	public void setSc_classcode(String sc_classcode) {
		this.sc_classcode = sc_classcode;
	}

	public String getSc_classname() {
		return sc_classname;
	}

	public void setSc_classname(String sc_classname) {
		this.sc_classname = sc_classname;
	}
	public ZybSourcecareerclass() {
		// TODO Auto-generated constructor stub
	}

			}