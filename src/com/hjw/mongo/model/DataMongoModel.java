package com.hjw.mongo.model;

import java.util.List;

import com.hjw.mongo.DTO.DataMongoConditionDTO;

public class DataMongoModel {

	private String exam_num;
	private String user_name;
	private String name;
	private String sex;
	private long max_age;
	private long min_age;
	private String exam_type;
	private String time1;
	private String time2;
	
	private long company_id;
	private long charging_id;
	private String q;
	private long add_i;
	private String conditions;
	private String searchType="com";
	private String searchlimit="limit";
	private List<DataMongoConditionDTO> condition;
	private String conditions1;
	private List<DataMongoConditionDTO> condition1;
	
	public String getSearchlimit() {
		return searchlimit;
	}
	public void setSearchlimit(String searchlimit) {
		this.searchlimit = searchlimit;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getMax_age() {
		return max_age;
	}
	public void setMax_age(long max_age) {
		this.max_age = max_age;
	}
	public long getMin_age() {
		return min_age;
	}
	public void setMin_age(long min_age) {
		this.min_age = min_age;
	}
	public String getExam_type() {
		return exam_type;
	}
	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}
	public String getTime1() {
		return time1;
	}
	public void setTime1(String time1) {
		this.time1 = time1;
	}
	public String getTime2() {
		return time2;
	}
	public void setTime2(String time2) {
		this.time2 = time2;
	}
	public long getCompany_id() {
		return company_id;
	}
	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}
	public long getCharging_id() {
		return charging_id;
	}
	public void setCharging_id(long charging_id) {
		this.charging_id = charging_id;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public long getAdd_i() {
		return add_i;
	}
	public void setAdd_i(long add_i) {
		this.add_i = add_i;
	}
	public String getConditions() {
		return conditions;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public List<DataMongoConditionDTO> getCondition() {
		return condition;
	}
	public void setCondition(List<DataMongoConditionDTO> condition) {
		this.condition = condition;
	}
	public List<DataMongoConditionDTO> getCondition1() {
		return condition1;
	}
	public void setCondition1(List<DataMongoConditionDTO> condition1) {
		this.condition1 = condition1;
	}
	public String getConditions1() {
		return conditions1;
	}
	public void setConditions1(String conditions1) {
		this.conditions1 = conditions1;
	}
}
