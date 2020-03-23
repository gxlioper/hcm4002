package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 就诊卡号维护
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2017年10月20日 下午4:47:37   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "customer_visit_card")
public class CustomerVisitCard implements java.io.Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 6395481105468119332L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	public long id;
	
	 @Column(name = "customer_id")
	public long	customer_id;
	 
	 @Column(name = "visit_no")
	public String	visit_no;
	 
	 @Column(name = "visit_date")
	public Date	    visit_date;
	 
	 @Column(name = "visit_status")
	public String   visit_status;
	 
	 @Column(name = "creater")
	public long      creater;
	 
	 @Column(name = "updater")
	public long updater;
	 
	 @Column(name = "create_time")
	public Date create_time;
	 
	 @Column(name = "update_time")
	public Date update_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getVisit_no() {
		return visit_no;
	}

	public void setVisit_no(String visit_no) {
		this.visit_no = visit_no;
	}

	public Date getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(Date visit_date) {
		this.visit_date = visit_date;
	}

	public String getVisit_status() {
		return visit_status;
	}

	public void setVisit_status(String visit_status) {
		this.visit_status = visit_status;
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

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
	
}
