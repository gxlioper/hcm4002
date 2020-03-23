package com.hjw.wst.DTO;

import java.util.Date;

/**
 * 就诊卡号维护
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  
     * @author: yangm     
     * @date:   2017年10月20日 下午4:47:37   
     * @version V2.0.0.0
 */
public class CustomerVisitCardDTO implements java.io.Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 6395481105468119332L;
	
	public long id;
	public long	customer_id;
	public String	visit_no;
	public Date	    visit_date;
	public String   visit_status;
	public long      creater;
	public long updater;
	public String create_time;
	public String update_time;
	public String chi_name;
	
	
	public String getChi_name() {
		return chi_name;
	}
	public void setChi_name(String chi_name) {
		this.chi_name = chi_name;
	}
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
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
		  
	
}
