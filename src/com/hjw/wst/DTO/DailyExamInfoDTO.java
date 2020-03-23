package com.hjw.wst.DTO;

public class DailyExamInfoDTO {

	private String name;
	private long id;
	private Long _parentId;
	private long person_count;
	private long man_count;
	private long woman_count;
	private long y_huishou;
	private long w_huishou;
	private long y_zongjian;
	private long w_zongjian;
	private long y_report;
	private long w_report;
	private double personal_pay;
	private double team_pay;
	private double amount;
	
	private String batch_name;
	private String com_name;
	private long batch_id;
	private long tjrs;
	private long jsrs;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long get_parentId() {
		return _parentId;
	}
	public void set_parentId(Long _parentId) {
		this._parentId = _parentId;
	}
	public long getPerson_count() {
		return person_count;
	}
	public void setPerson_count(long person_count) {
		this.person_count = person_count;
	}
	public long getMan_count() {
		return man_count;
	}
	public void setMan_count(long man_count) {
		this.man_count = man_count;
	}
	public long getWoman_count() {
		return woman_count;
	}
	public void setWoman_count(long woman_count) {
		this.woman_count = woman_count;
	}
	public long getY_huishou() {
		return y_huishou;
	}
	public void setY_huishou(long y_huishou) {
		this.y_huishou = y_huishou;
	}
	public long getW_huishou() {
		return w_huishou;
	}
	public void setW_huishou(long w_huishou) {
		this.w_huishou = w_huishou;
	}
	public long getY_zongjian() {
		return y_zongjian;
	}
	public void setY_zongjian(long y_zongjian) {
		this.y_zongjian = y_zongjian;
	}
	public long getW_zongjian() {
		return w_zongjian;
	}
	public void setW_zongjian(long w_zongjian) {
		this.w_zongjian = w_zongjian;
	}
	public long getY_report() {
		return y_report;
	}
	public void setY_report(long y_report) {
		this.y_report = y_report;
	}
	public long getW_report() {
		return w_report;
	}
	public void setW_report(long w_report) {
		this.w_report = w_report;
	}
	public double getPersonal_pay() {
		return personal_pay;
	}
	public void setPersonal_pay(double personal_pay) {
		this.personal_pay = personal_pay;
	}
	public double getTeam_pay() {
		return team_pay;
	}
	public void setTeam_pay(double team_pay) {
		this.team_pay = team_pay;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public long getBatch_id() {
		return batch_id;
	}
	public void setBatch_id(long batch_id) {
		this.batch_id = batch_id;
	}
	public long getTjrs() {
		return tjrs;
	}
	public void setTjrs(long tjrs) {
		this.tjrs = tjrs;
	}
	public long getJsrs() {
		return jsrs;
	}
	public void setJsrs(long jsrs) {
		this.jsrs = jsrs;
	}
}
