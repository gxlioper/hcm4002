package com.hjw.zyb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "zyb_exam_summary_result")
public class ZybExamSummaryResult implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name="exam_info_id")
	 private long exam_info_id;
	 
	 @Column(name="resultID")
	 private String resultID;
	 
	 @Column(name="exam_result")
	 private String exam_result;
	 
	 @Column(name="remark")
	 private String remark;
	 
	 @Column(name="creater")
	 private long creater;
	 
	 @Column(name="create_time")
	 private Date create_time;
	 
	 @Column(name="exam_num")
     private String exam_num;
    
     public String getExam_num() {
		return exam_num;
	 }

	 public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	 }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public String getResultID() {
		return resultID;
	}

	public void setResultID(String resultID) {
		this.resultID = resultID;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
