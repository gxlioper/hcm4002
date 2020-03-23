package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customer_vip_prompt")
public class CustomerVipPrompt implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="user_name")
	private String user_name;
	
	@Column(name="com_name")
	private String com_name;
	
	@Column(name="prompt_status")
	private String prompt_status;
	
	@Column(name="prompt_user")
	private long prompt_user;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getCom_name() {
		return com_name;
	}

	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}

	public String getPrompt_status() {
		return prompt_status;
	}

	public void setPrompt_status(String prompt_status) {
		this.prompt_status = prompt_status;
	}

	public long getPrompt_user() {
		return prompt_user;
	}

	public void setPrompt_user(long prompt_user) {
		this.prompt_user = prompt_user;
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
