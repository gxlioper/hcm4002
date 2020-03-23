package com.hjw.zyb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_ext_typeofocc")
public class ExamExtTypeofocc implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "id", unique = true, nullable = false,length = 50)
     private String id;
	 
	 @Column(name = "exam_num")
	 private String exam_num;
	 
	 @Column(name = "arch_num")
	 private String arch_num;
	 
	 @Column(name = "sc_classcode")
	 private String sc_classcode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getSc_classcode() {
		return sc_classcode;
	}

	public void setSc_classcode(String sc_classcode) {
		this.sc_classcode = sc_classcode;
	}
	 
}