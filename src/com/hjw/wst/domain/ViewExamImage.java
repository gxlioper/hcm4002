package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="view_exam_image")
public class ViewExamImage implements java.io.Serializable {
	
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="view_exam_id")
	private long view_exam_id;
	
	@Column(name="image_path")
	private String image_path;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="pacs_req_code")
	private String pacs_req_code;
	
	@Column(name="seq_no")
	private int seq_no;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getView_exam_id() {
		return view_exam_id;
	}

	public void setView_exam_id(long view_exam_id) {
		this.view_exam_id = view_exam_id;
	}

	public String getImage_path() {
		return image_path;
	}

	public void setImage_path(String image_path) {
		this.image_path = image_path;
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

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getPacs_req_code() {
		return pacs_req_code;
	}

	public void setPacs_req_code(String pacs_req_code) {
		this.pacs_req_code = pacs_req_code;
	}

	public int getSeq_no() {
		return seq_no;
	}

	public void setSeq_no(int seq_no) {
		this.seq_no = seq_no;
	}
	
}
