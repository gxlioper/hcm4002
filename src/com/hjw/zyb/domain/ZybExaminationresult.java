package com.hjw.zyb.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "zyb_examinationresult")
public class ZybExaminationresult implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 
	 
	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "resultID", unique = true, nullable = false,length = 50)
     private String resultID;
	 
	 @Column(name = "result_name")
     private String result_name;
	 
	 @Column(name="seq_code")
	 private Long seq_code;
	 
	public String getResultID() {
		return resultID;
	}


	public void setResultID(String resultID) {
		this.resultID = resultID;
	}


	public String getResult_name() {
		return result_name;
	}


	public void setResult_name(String result_name) {
		this.result_name = result_name;
	}


	public Long getSeq_code() {
		return seq_code;
	}


	public void setSeq_code(Long seq_code) {
		this.seq_code = seq_code;
	}


	public ZybExaminationresult() {
		// TODO Auto-generated constructor stub
	}

			}