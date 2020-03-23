package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_sms_base_template")
public class CrmSmsBaseTemplate   implements java.io.Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -4218917339573625859L;

	 @Id
	 @GeneratedValue(generator = "paymentableGenerator")
	 @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	 @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name = "sms_category")
	private String	   sms_category;
	
	@Column(name = "sms_name")
	private String	   sms_name;
	
	@Column(name = "sms_note")
	private String	   sms_note;
	
	@Column(name = "creater")
	private long	   creater;
	
	@Column(name = "create_time")
	private Date	   create_time;
	
	@Column(name = "updater")
	private long	   updater;
	
	@Column(name = "update_time")
	private Date	   update_time;
	
	@Column(name = "sms_state")
	private String	   sms_state;
	
	
	
	public CrmSmsBaseTemplate() {
		super();
	}
	public CrmSmsBaseTemplate(String id, String sms_category, String sms_name,
			String sms_note, long creater, Date create_time, long updater,
			Date update_time,String sms_state) {
		super();
		this.id = id;
		this.sms_category = sms_category;
		this.sms_name = sms_name;
		this.sms_note = sms_note;
		this.creater = creater;
		this.create_time = create_time;
		this.updater = updater;
		this.update_time = update_time;
		this.sms_state = sms_state;
	}
	
	public String getSms_state() {
		return sms_state;
	}
	public void setSms_state(String sms_state) {
		this.sms_state = sms_state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getSms_category() {
		return sms_category;
	}
	public void setSms_category(String sms_category) {
		this.sms_category = sms_category;
	}
	public String getSms_name() {
		return sms_name;
	}
	public void setSms_name(String sms_name) {
		this.sms_name = sms_name;
	}
	public String getSms_note() {
		return sms_note;
	}
	public void setSms_note(String sms_note) {
		this.sms_note = sms_note;
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
