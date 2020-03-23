package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="view_exam_item")
public class ViewExamItem implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="view_exam_detail_id")
	private long view_exam_detail_id;
	
	@Column(name="exam_item_id")
	private long exam_item_id;
	
	@Column(name="exam_result")
	private String exam_result;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="item_code")
	private String item_code;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getView_exam_detail_id() {
		return view_exam_detail_id;
	}

	public void setView_exam_detail_id(long view_exam_detail_id) {
		this.view_exam_detail_id = view_exam_detail_id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
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

	public String getItem_code() {
		return item_code;
	}

	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	
	
}
