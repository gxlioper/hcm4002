package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_occutypeofwork")
public class ZybOccutypeofwork implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 /*@Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")*/
	 @Id
	    @GeneratedValue(generator = "paymentableGenerator")
	    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
		@Column(name = "typeofworkID", unique = true, nullable = false,length = 50)
     private String typeofworkID;
	 
	 @Column(name = "typeofwork_code")
     private String typeofwork_code;
	 
	 @Column(name = "typeofwork_name")
     private String typeofwork_name;
	 
	 @Column(name = "center_num")
     private String center_num;
	 
	 @Column(name = "showorder")
     private int showorder;
	 
	 
	 public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public int getShoworder() {
		return showorder;
	}

	public void setShoworder(int showorder) {
		this.showorder = showorder;
	}

	public ZybOccutypeofwork() {
		// TODO Auto-generated constructor stub
	}

	public String getTypeofworkID() {
		return typeofworkID;
	}

	public void setTypeofworkID(String typeofworkID) {
		this.typeofworkID = typeofworkID;
	}

	public String getTypeofwork_code() {
		return typeofwork_code;
	}

	public void setTypeofwork_code(String typeofwork_code) {
		this.typeofwork_code = typeofwork_code;
	}

	public String getTypeofwork_name() {
		return typeofwork_name;
	}

	public void setTypeofwork_name(String typeofwork_name) {
		this.typeofwork_name = typeofwork_name;
	}
	 
	

			}