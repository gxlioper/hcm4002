package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "occtemplate")
public class ZybOcctemplate implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576024L;
	 
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "id", unique = true, nullable = false,length = 50)
     private String id;
	 
	 @Column(name = "exam_type")
     private int exam_type;
	 
	 @Column(name = "occuphyexaclassid")
	 private String occuphyexaclassid;
	 
	 @Column(name = "template")
     private String template;
	
	 @Column(name = "remark")
	 private String remark;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getExam_type() {
		return exam_type;
	}

	public void setExam_type(int exam_type) {
		this.exam_type = exam_type;
	}

	public String getOccuphyexaclassid() {
		return occuphyexaclassid;
	}

	public void setOccuphyexaclassid(String occuphyexaclassid) {
		this.occuphyexaclassid = occuphyexaclassid;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}