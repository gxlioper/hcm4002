package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  总检审核复审一键恢复结论日志
     * @author: dq     
     * @date:   2018年6月7日 下午6:00:42   
     * @version V2.0.0.0
 */
@Entity
@Table(name="exam_summary_log")
public class ExamSummaryLog implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_doctor_id")
	private long exam_doctor_id;
	
	@Column(name="exam_info_id")
	private long exam_info_id;
	
	@Column(name="exam_date")
	private Date exam_date;
	
	@Column(name="final_exam_result")
	private String final_exam_result;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="operation_type")
	private long operation_type; //操作类型 1总检保存 2审核保存 3复审保存 4一键取消恢复保存
	
	@Column(name="exam_num")
	private String exam_num;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getExam_doctor_id() {
		return exam_doctor_id;
	}

	public void setExam_doctor_id(long exam_doctor_id) {
		this.exam_doctor_id = exam_doctor_id;
	}

	public long getExam_info_id() {
		return exam_info_id;
	}

	public void setExam_info_id(long exam_info_id) {
		this.exam_info_id = exam_info_id;
	}

	public Date getExam_date() {
		return exam_date;
	}

	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
	}

	public String getFinal_exam_result() {
		return final_exam_result;
	}

	public void setFinal_exam_result(String final_exam_result) {
		this.final_exam_result = final_exam_result;
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

	public long getOperation_type() {
		return operation_type;
	}

	public void setOperation_type(long operation_type) {
		this.operation_type = operation_type;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
}
