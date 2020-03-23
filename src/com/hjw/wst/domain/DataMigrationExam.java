package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="data_migration_exam")
public class DataMigrationExam implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name="exam_num")
	 private String exam_num;
	 
	 @Column(name="exam_num_bar")
	 private String exam_num_bar;
	 
	 @Column(name="arch_num")
	 private String arch_num;
	 
	 @Column(name="arch_num_bar")
	 private String arch_num_bar;
	 
	 @Column(name="creater")
	 private long creater; 
	 
	 @Column(name="create_time")
	 private Date create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getExam_num_bar() {
		return exam_num_bar;
	}

	public void setExam_num_bar(String exam_num_bar) {
		this.exam_num_bar = exam_num_bar;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public String getArch_num_bar() {
		return arch_num_bar;
	}

	public void setArch_num_bar(String arch_num_bar) {
		this.arch_num_bar = arch_num_bar;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
