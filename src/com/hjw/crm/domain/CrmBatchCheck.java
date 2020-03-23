package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_batch_check")
public class CrmBatchCheck {

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name="batch_id")
	private long batch_id;
	@Column(name="check_type")
	private String check_type;
	@Column(name="check_status")
	private String check_status;
	@Column(name="checkuser")
	private long checkuser;
	@Column(name="checkdate")
	private Date checkdate;
	@Column(name="checknotice")
	private String checknotice;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public String getCheck_type() {
		return check_type;
	}
	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}
	public String getCheck_status() {
		return check_status;
	}
	public void setCheck_status(String check_status) {
		this.check_status = check_status;
	}
	public long getCheckuser() {
		return checkuser;
	}
	public void setCheckuser(long checkuser) {
		this.checkuser = checkuser;
	}
	public Date getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public String getChecknotice() {
		return checknotice;
	}
	public void setChecknotice(String checknotice) {
		this.checknotice = checknotice;
	}
	

}
