package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="notes_data")
public class NoteData implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="user_id")
	private long  user_id;
	
	@Column(name="notes_date")
	private Date notes_date;
	
	@Column(name="notes_content")
	private String notes_content;
	
	@Column(name="creater")
	private long creater;

	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;

	@Column(name="update_time")
	private Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public Date getNotes_date() {
		return notes_date;
	}

	public void setNotes_date(Date notes_date) {
		this.notes_date = notes_date;
	}

	public String getNotes_content() {
		return notes_content;
	}

	public void setNotes_content(String notes_content) {
		this.notes_content = notes_content;
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
