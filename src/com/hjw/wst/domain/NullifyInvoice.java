package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  作废发票表
     * @author: dangqi     
     * @date:   2017年10月31日 上午9:23:33   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "nullify_invoice")
public class NullifyInvoice implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "invoice_num")
	private String invoice_num; //发票号
	
	@Column(name = "invoice_class")
	private String invoice_class;//发票类型
	
	@Column(name = "invoice_status")
	private String invoice_status;//发票状态  空白票作废、已开票作废
	
	@Column(name = "creater")
	private long creater; 
	
	@Column(name = "create_time")
	private Date create_time;
	
	@Column(name = "daily_status")
	private String daily_status;
	
	@Column(name = "account_num")
	private String account_num;
	
	@Column(name = "batch_id")
	private long batch_id;
	
	@Column(name="center_num")
	private String center_num;
	
	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public long getBatch_id() {
		return batch_id;
	}

	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInvoice_num() {
		return invoice_num;
	}

	public void setInvoice_num(String invoice_num) {
		this.invoice_num = invoice_num;
	}

	public String getInvoice_class() {
		return invoice_class;
	}

	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}

	public String getInvoice_status() {
		return invoice_status;
	}

	public void setInvoice_status(String invoice_status) {
		this.invoice_status = invoice_status;
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

	public String getDaily_status() {
		return daily_status;
	}

	public void setDaily_status(String daily_status) {
		this.daily_status = daily_status;
	}

	public String getAccount_num() {
		return account_num;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}
}
