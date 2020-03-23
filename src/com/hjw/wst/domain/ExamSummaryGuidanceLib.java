package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="exam_summary_guidance_lib")
public class ExamSummaryGuidanceLib implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="guidance_word")
	private String guidance_word;
	
	@Column(name="guidance_pinyin")
	private String guidance_pinyin;
	
	@Column(name="guidance_content")
	private String guidance_content;
	
	@Column(name="is_active")
	private String is_active;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name = "update_time")
	private Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGuidance_word() {
		return guidance_word;
	}

	public void setGuidance_word(String guidance_word) {
		this.guidance_word = guidance_word;
	}

	public String getGuidance_pinyin() {
		return guidance_pinyin;
	}

	public void setGuidance_pinyin(String guidance_pinyin) {
		this.guidance_pinyin = guidance_pinyin;
	}

	public String getGuidance_content() {
		return guidance_content;
	}

	public void setGuidance_content(String guidance_content) {
		this.guidance_content = guidance_content;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
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
}
