package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "zyb_checkcriterion")
public class ZybCheckcriterion implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "criterionID", unique = true, nullable = false,length = 50)
     private String criterionID;
	 
	 @Column(name = "criterion_name")
     private String criterion_name;
	 
	
	 public String getCriterionID() {
		return criterionID;
	}


	public void setCriterionID(String criterionID) {
		this.criterionID = criterionID;
	}


	public String getCriterion_name() {
		return criterion_name;
	}


	public void setCriterion_name(String criterion_name) {
		this.criterion_name = criterion_name;
	}


	public ZybCheckcriterion() {
		// TODO Auto-generated constructor stub
	}
	}