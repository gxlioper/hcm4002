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
     * @Description:  普通科室结论日志表
     * @author: dq     
     * @date:   2018年6月7日 上午11:43:50   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "exam_dep_result_log")
public class ExamdepResultLog implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "exam_info_id")
	private long exam_info_id;

	@Column(name = "dep_id")
	private long dep_id;
	
	@Column(name = "exam_doctor")
	private String exam_doctor;
	
	@Column(name="exam_date")
	private Date exam_date;

	@Column(name = "exam_result_summary")
	private String exam_result_summary;

	@Column(name = "suggestion")
	private String suggestion;

	@Column(name = "creater")
    private long creater;
    
    @Column(name = "create_time")
    private Date create_time;

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

	public long getDep_id() {
		return dep_id;
	}

	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
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

	public String getExam_result_summary() {
		return exam_result_summary;
	}

	public void setExam_result_summary(String exam_result_summary) {
		this.exam_result_summary = exam_result_summary;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
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
