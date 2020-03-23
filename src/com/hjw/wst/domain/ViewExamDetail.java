package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="view_exam_detail")
public class ViewExamDetail implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_info_id")
	private long exam_info_id;
	
	@Column(name="exam_item_id")
	private long exam_item_id;
	
	@Column(name="exam_doctor")
	private String exam_doctor;
	
	@Column(name="report_picture_path")
	private String report_picture_path;
	
	@Column(name="exam_desc")
	private String exam_desc;
	
	@Column(name="exam_result")
	private String exam_result;
	
	@Column(name="exam_date")
	private Date exam_date;
	
	@Column(name="center_num")
	private String center_num;
	
	@Column(name="approver")
	private String approver;
	
	@Column(name="approve_date")
	private Date approve_date;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="pacs_id")
	private long pacs_id;

	@Column(name="positive_find")
	private String positive_find;
	
	@Column(name="marker")
	private long marker;
	
	@Column(name="mark_time")
	private Date mark_time;
	
	@Column(name = "dept_num")
	private String dept_num;
	
	@Column(name="exam_num")
    private String exam_num;
	
	@Column(name="pacs_req_code")
	private String pacs_req_code;
    
	
	
    public String getPacs_req_code() {
		return pacs_req_code;
	}

	public void setPacs_req_code(String pacs_req_code) {
		this.pacs_req_code = pacs_req_code;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	
	public String getDept_num() {
		return dept_num;
	}

	public void setDept_num(String dept_num) {
		this.dept_num = dept_num;
	}

	public String getPositive_find() {
		return positive_find;
	}

	public void setPositive_find(String positive_find) {
		this.positive_find = positive_find;
	}

	public long getMarker() {
		return marker;
	}

	public void setMarker(long marker) {
		this.marker = marker;
	}

	public Date getMark_time() {
		return mark_time;
	}

	public void setMark_time(Date mark_time) {
		this.mark_time = mark_time;
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

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public String getExam_doctor() {
		return exam_doctor;
	}

	public void setExam_doctor(String exam_doctor) {
		this.exam_doctor = exam_doctor;
	}

	public String getReport_picture_path() {
		return report_picture_path;
	}

	public void setReport_picture_path(String report_picture_path) {
		this.report_picture_path = report_picture_path;
	}

	public String getExam_desc() {
		return exam_desc;
	}

	public void setExam_desc(String exam_desc) {
		this.exam_desc = exam_desc;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
	}

	public Date getExam_date() {
		return exam_date;
	}

	public void setExam_date(Date exam_date) {
		this.exam_date = exam_date;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public Date getApprove_date() {
		return approve_date;
	}

	public void setApprove_date(Date approve_date) {
		this.approve_date = approve_date;
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

	public long getUpdater() {
		return updater;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public long getPacs_id() {
		return pacs_id;
	}

	public void setPacs_id(long pacs_id) {
		this.pacs_id = pacs_id;
	}
}
