package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="exam_summary_reject_lib")
public class FinalRejection  implements java.io.Serializable{
	private static final long serialVersionUID = 5817368272190984692L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private    long  id;
	
	@Column(name="reject_context")
	private    String  reject_context;
	
	@Column(name="is_active")
	private    String  is_Active;
	
	@Column(name="creater")
	private    long  creater;
	
	@Column(name="create_time")
	private    Date  create_time;
	
	@Column(name="updater")
	private    long  updater;
	
	@Column(name="update_time")
	private    Date  update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getReject_context() {
		return reject_context;
	}

	public void setReject_context(String reject_context) {
		this.reject_context = reject_context;
	}

	public String getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(String is_Active) {
		this.is_Active = is_Active;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
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
