package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="view_common_words")
public class ViewCommonWords implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="sample_id")
	private long sample_id;
	
	@Column(name="exam_desc")
	private String exam_desc;
	
	@Column(name="exam_result")
	private String exam_result;
	
	@Column(name="isActive")
	private String isActive;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="seq_code")
	private Integer seq_code;
	
	@Column(name="is_default")
	private long is_default;
	
	public long getIs_default() {
		return is_default;
	}
	public void setIs_default(long is_default) {
		this.is_default = is_default;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSample_id() {
		return sample_id;
	}
	public void setSample_id(long sample_id) {
		this.sample_id = sample_id;
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
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	public Integer getSeq_code() {
		return seq_code;
	}
	public void setSeq_code(Integer seq_code) {
		this.seq_code = seq_code;
	}
}
