package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_economicclass")
public class ZybEconomicClass implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 /*@Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")*/
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
     @Column(name = "economicID", unique = true, nullable = false,length = 50)
	 private String economicID;
	 
	 @Column(name = "economicclass_code")
     private String economicclass_code;
	 
	 @Column(name = "economicclass_name")
     private String economicclass_name;
	 
	 @Column(name = "parentID")
     private String parentID;
	 
	 public ZybEconomicClass() {
		// TODO Auto-generated constructor stub
	}

	public String getEconomicID() {
		return economicID;
	}

	public void setEconomicID(String economicID) {
		this.economicID = economicID;
	}

	public String getEconomicclass_code() {
		return economicclass_code;
	}

	public void setEconomicclass_code(String economicclass_code) {
		this.economicclass_code = economicclass_code;
	}

	public String getEconomicclass_name() {
		return economicclass_name;
	}

	public void setEconomicclass_name(String economicclass_name) {
		this.economicclass_name = economicclass_name;
	}

	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}
	 
		}