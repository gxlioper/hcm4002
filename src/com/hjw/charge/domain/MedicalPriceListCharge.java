package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 价表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  
     * @author: zr     
     * @date:   2019年10月30日 上午11:12:46   
     * @version V2.0.0.0
 */
@Entity
@Table(name="medical_price_list")
public class MedicalPriceListCharge {
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private	long		id;
	private	String		item_class;
	private	String		item_name;
	private	String		item_spec;
	private	String		units;
	private	double		price;
	private	double		prefer_price;
	private	String		performed_by;
	private	String		input_code;
	private	String		class_on_inp_rcpt;
	private	String		class_on_outp_rcpt;
	private	String		class_on_reckoning;
	private	String		subj_code;
	private	String		memo;
	private	Date		start_date;
	private	Date		stop_date;
	private	long		creater;
	private	Date		create_date;
	private	long		updater;
	private	Date		update_date;
	private	String		is_active;
	private	String		expand1;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getItem_class() {
		return item_class;
	}
	public void setItem_class(String item_class) {
		this.item_class = item_class;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_spec() {
		return item_spec;
	}
	public void setItem_spec(String item_spec) {
		this.item_spec = item_spec;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPrefer_price() {
		return prefer_price;
	}
	public void setPrefer_price(double prefer_price) {
		this.prefer_price = prefer_price;
	}
	public String getPerformed_by() {
		return performed_by;
	}
	public void setPerformed_by(String performed_by) {
		this.performed_by = performed_by;
	}
	public String getInput_code() {
		return input_code;
	}
	public void setInput_code(String input_code) {
		this.input_code = input_code;
	}
	public String getClass_on_inp_rcpt() {
		return class_on_inp_rcpt;
	}
	public void setClass_on_inp_rcpt(String class_on_inp_rcpt) {
		this.class_on_inp_rcpt = class_on_inp_rcpt;
	}
	public String getClass_on_outp_rcpt() {
		return class_on_outp_rcpt;
	}
	public void setClass_on_outp_rcpt(String class_on_outp_rcpt) {
		this.class_on_outp_rcpt = class_on_outp_rcpt;
	}
	public String getClass_on_reckoning() {
		return class_on_reckoning;
	}
	public void setClass_on_reckoning(String class_on_reckoning) {
		this.class_on_reckoning = class_on_reckoning;
	}
	public String getSubj_code() {
		return subj_code;
	}
	public void setSubj_code(String subj_code) {
		this.subj_code = subj_code;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getStop_date() {
		return stop_date;
	}
	public void setStop_date(Date stop_date) {
		this.stop_date = stop_date;
	}
	public long getCreater() {
		return creater;
	}
	public void setCreater(long creater) {
		this.creater = creater;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	public long getUpdater() {
		return updater;
	}
	public void setUpdater(long updater) {
		this.updater = updater;
	}
	public Date getUpdate_date() {
		return update_date;
	}
	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	
}
