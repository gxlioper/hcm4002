package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 报告接收信息表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年9月1日 上午10:27:24   
     * @version V2.0.0.0
 */
@Entity
@Table(name="report_receive")
public class ReportReceive implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="receive_type")
	private String receive_type;
	
	@Column(name="receive_name")
	private String receive_name;
	
	@Column(name="receive_phone")
	private String receive_phone;
	
	@Column(name="receive_address")
	private String receive_address;
	
	@Column(name="receive_postcode")
	private String receive_postcode;
	
	@Column(name="receive_date")
	private Date receive_date;
	
	@Column(name="receive_remark")
	private String receive_remark;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private long updater;
	
	@Column(name="undate_time")
	private Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getReceive_type() {
		return receive_type;
	}

	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}

	public String getReceive_name() {
		return receive_name;
	}

	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}

	public String getReceive_phone() {
		return receive_phone;
	}

	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	public String getReceive_address() {
		return receive_address;
	}

	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}

	public String getReceive_postcode() {
		return receive_postcode;
	}

	public void setReceive_postcode(String receive_postcode) {
		this.receive_postcode = receive_postcode;
	}

	public Date getReceive_date() {
		return receive_date;
	}

	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
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

	public String getReceive_remark() {
		return receive_remark;
	}

	public void setReceive_remark(String receive_remark) {
		this.receive_remark = receive_remark;
	}
}
