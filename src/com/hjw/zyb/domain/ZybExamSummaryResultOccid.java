package com.hjw.zyb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "zyb_exam_summary_result_occid")
public class ZybExamSummaryResultOccid implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name="result_id")
	 private long result_id;
	 
	 @Column(name="occudiseaseIDorcontraindicationID")
	 private String occudiseaseIDorcontraindicationID;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getResult_id() {
		return result_id;
	}

	public void setResult_id(long result_id) {
		this.result_id = result_id;
	}

	public String getOccudiseaseIDorcontraindicationID() {
		return occudiseaseIDorcontraindicationID;
	}

	public void setOccudiseaseIDorcontraindicationID(String occudiseaseIDorcontraindicationID) {
		this.occudiseaseIDorcontraindicationID = occudiseaseIDorcontraindicationID;
	}
}
