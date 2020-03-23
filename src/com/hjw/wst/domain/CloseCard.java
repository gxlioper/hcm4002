package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="close_card")
public class CloseCard implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="card_num")
	private String card_num;
	
	@Column(name="member_id")
	private String member_id;
	
	@Column(name="deadline")
	private Date deadline;
	
	@Column(name="card_type")
	private String card_type;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="face_amount")
	private Double face_amount;
	
	@Column(name="card_level")
	private String card_level;
	
	@Column(name="card_pwd")
	private String card_pwd;
	
	@Column(name="remark")
	private String remark; 
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFace_amount() {
		return face_amount;
	}

	public void setFace_amount(Double face_amount) {
		this.face_amount = face_amount;
	}

	public String getCard_level() {
		return card_level;
	}

	public void setCard_level(String card_level) {
		this.card_level = card_level;
	}

	public String getCard_pwd() {
		return card_pwd;
	}

	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
