package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: 收费项目    
     * @date:   2016年9月25日 下午4:08:22   
     * @version V2.0.0.0
 */

public class ExamSetBean implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 private long id;
	 private long synid;
	 private String isActive="Y";
	 private String set_num="";
	 private String set_pinyin="";
	 private String set_name="";
	 private String sex="";
	 private double set_discount;
	 private double set_amount;
	 private String create_time="";
	 private String update_time="";
	 private String apptype="1";
	 private List<SetChargingItemBean> list=new ArrayList<SetChargingItemBean>();	 
	 
	public long getSynid() {
		return synid;
	}
	public void setSynid(long synid) {
		this.synid = synid;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getApptype() {
		return apptype;
	}
	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	public List<SetChargingItemBean> getList() {
		return list;
	}
	public void setList(List<SetChargingItemBean> list) {
		this.list = list;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSet_num() {
		return set_num;
	}
	public void setSet_num(String set_num) {
		this.set_num = set_num;
	}
	public String getSet_pinyin() {
		return set_pinyin;
	}
	public void setSet_pinyin(String set_pinyin) {
		this.set_pinyin = set_pinyin;
	}
	public String getSet_name() {
		return set_name;
	}
	public void setSet_name(String set_name) {
		this.set_name = set_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getSet_discount() {
		return set_discount;
	}
	public void setSet_discount(double set_discount) {
		this.set_discount = set_discount;
	}
	public double getSet_amount() {
		return set_amount;
	}
	public void setSet_amount(double set_amount) {
		this.set_amount = set_amount;
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