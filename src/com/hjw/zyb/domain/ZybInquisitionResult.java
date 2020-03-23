package com.hjw.zyb.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 问诊项目结果表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.domain   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年12月11日 上午10:19:44   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_inquisition_result")
public class ZybInquisitionResult implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;

	 @Id
	 @GenericGenerator(name = "idGenerator", strategy = "identity")
	 @GeneratedValue(generator = "idGenerator")
	 private long id;
	 
	 @Column(name="examinfo_id")
	 private long examinfo_id;
	 
	 @Column(name="item_id")
	 private String item_id;
	 
	 @Column(name="result")
	 private String result;
	 
	 @Column(name="item_type")
	 private String item_type;
	 
	 @Column(name="exam_doctor")
	 private String exam_doctor;
	 
	 @Column(name="exam_date")
	 private Date exam_date;
	 
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

	public long getExaminfo_id() {
		return examinfo_id;
	}

	public void setExaminfo_id(long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}

	public String getItem_id() {
		return item_id;
	}

	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getItem_type() {
		return item_type;
	}

	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}

	public String getExam_doctor() {
		return exam_doctor;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public Date getExam_date() {
		return exam_date;
	}

	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
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
