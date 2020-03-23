package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class ChargingItemBean {
	private long id;
	private long dep_id;	
	private long synid;
	private String item_code;
	private String item_name;
	private String item_pinyin;
	private String item_category;
	private String sex;
	private double amount;
	private String create_time;
	private String update_time;
	private String item_note; // 项目描述 
	private String  isActive="Y";
	private String dep_category;
	private List<ChargingExamItemBean> list=new ArrayList<ChargingExamItemBean>();
	
	private long exam_type_id;//收费项目类型
	
	private String exam_type_name;//收费项目类型名称		
	
    public long getExam_type_id() {
		return exam_type_id;
	}

	public void setExam_type_id(long exam_type_id) {
		this.exam_type_id = exam_type_id;
	}

	public String getExam_type_name() {
		return exam_type_name;
	}

	public void setExam_type_name(String exam_type_name) {
		this.exam_type_name = exam_type_name;
	}
	
	public long getSynid() {
		return synid;
	}
	public void setSynid(long synid) {
		this.synid = synid;
	}
	public List<ChargingExamItemBean> getList() {
		return list;
	}
	public void setList(List<ChargingExamItemBean> list) {
		this.list = list;
	}
	public String getDep_category() {
		return dep_category;
	}
	public void setDep_category(String dep_category) {
		this.dep_category = dep_category;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getDep_id() {
		return dep_id;
	}
	public void setDep_id(long dep_id) {
		this.dep_id = dep_id;
	}
	public String getItem_code() {
		return item_code;
	}
	public void setItem_code(String item_code) {
		this.item_code = item_code;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getItem_pinyin() {
		return item_pinyin;
	}
	public void setItem_pinyin(String item_pinyin) {
		this.item_pinyin = item_pinyin;
	}
	public String getItem_category() {
		return item_category;
	}
	public void setItem_category(String item_category) {
		this.item_category = item_category;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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
	public String getItem_note() {
		return item_note;
	}
	public void setItem_note(String item_note) {
		this.item_note = item_note;
	}

	
}
