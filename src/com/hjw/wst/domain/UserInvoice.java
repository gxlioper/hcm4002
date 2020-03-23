package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 用户发票号段维护表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dq     
     * @date:   2017年3月2日 上午11:21:33   
     * @version V2.0.0.0
 */
@Entity
@Table( name = "user_invoice")
public class UserInvoice implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="user_id")
	private long user_id;
	
	@Column(name="invoice_num_min")
	private String invoice_num_min;//最小发票号
	
	@Column(name="invoice_num_max")
	private String invoice_num_max;//最大发票号
	
	@Column(name="invoice_num_used")
	private String invoice_num_used;//已使用最大发票号
	
	@Column(name = "invoice_class")
	private String invoice_class;//发票类型 数据字典 FPLX
	
	@Column(name = "is_active")
	private String is_active;//启用那种发票类型号段 Y表示启用，N表示未启用
	
	@Column(name = "is_use_all")
	private String is_use_all;//启用共用号段还是个人号段 Y表示启用，N表示未启用
	
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

	public String getInvoice_num_min() {
		return invoice_num_min;
	}

	public void setInvoice_num_min(String invoice_num_min) {
		this.invoice_num_min = invoice_num_min;
	}

	public String getInvoice_num_max() {
		return invoice_num_max;
	}

	public void setInvoice_num_max(String invoice_num_max) {
		this.invoice_num_max = invoice_num_max;
	}

	public String getInvoice_num_used() {
		return invoice_num_used;
	}

	public void setInvoice_num_used(String invoice_num_used) {
		this.invoice_num_used = invoice_num_used;
	}

	public String getInvoice_class() {
		return invoice_class;
	}

	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}

	public String getIs_use_all() {
		return is_use_all;
	}

	public void setIs_use_all(String is_use_all) {
		this.is_use_all = is_use_all;
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
