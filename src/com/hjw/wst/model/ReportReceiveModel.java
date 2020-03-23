package com.hjw.wst.model;

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
     * @Description:  报告领取方式
     * @author: zr     
     * @date:   2016年9月1日 上午10:27:24   
     * @version V2.0.0.0
 */
public class ReportReceiveModel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;

	private long id;
	
	private String exam_num;
	
	private String receive_type;
	
	private String receive_name;
	
	private String receive_phone;
	
	private String receive_address;
	
	private String receive_postcode;
	
	private Date receive_date;
	
	private String receive_remark;
	
	private long creater;
	
	private String create_time;
	
	private long updater;
	
	private String update_time;

	public long getId() {
		return id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public String getReceive_type() {
		return receive_type;
	}

	public String getReceive_name() {
		return receive_name;
	}

	public String getReceive_phone() {
		return receive_phone;
	}

	public String getReceive_address() {
		return receive_address;
	}

	public String getReceive_postcode() {
		return receive_postcode;
	}

	public Date getReceive_date() {
		return receive_date;
	}

	public String getReceive_remark() {
		return receive_remark;
	}

	public long getCreater() {
		return creater;
	}

	public String getCreate_time() {
		return create_time;
	}

	public long getUpdater() {
		return updater;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public void setReceive_type(String receive_type) {
		this.receive_type = receive_type;
	}

	public void setReceive_name(String receive_name) {
		this.receive_name = receive_name;
	}

	public void setReceive_phone(String receive_phone) {
		this.receive_phone = receive_phone;
	}

	public void setReceive_address(String receive_address) {
		this.receive_address = receive_address;
	}

	public void setReceive_postcode(String receive_postcode) {
		this.receive_postcode = receive_postcode;
	}

	public void setReceive_date(Date receive_date) {
		this.receive_date = receive_date;
	}

	public void setReceive_remark(String receive_remark) {
		this.receive_remark = receive_remark;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
