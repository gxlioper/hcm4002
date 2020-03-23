package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_sign_bill_plan")
public class CrmSignBillPlan implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="company_id")
	private long company_id;//单位ID
	
	@Column(name="sign_num")
	private String sign_num;//签单计划编号
	
	@Column(name="sign_name")
	private String sign_name;//签单计划名称
	
	@Column(name="sign_pingying")
	private String sign_pingying;//拼音助记码
	
	@Column(name="sign_year")
	private String sign_year;//年度
	
	@Column(name="sign_type")
	private String sign_type;//签单类型
	
	@Column(name="old_new_type")
	private String old_new_type;//新旧分类
	
	@Column(name="customer_type")
	private String customer_type;//客户分类
	
	@Column(name="sign_count")
	private long sign_count;//签单数量
	
	@Column(name="sign_date")
	private Date sign_date;//预计签单日期
	
	@Column(name="sign_persion")
	private Long sign_persion;//预计体检人数
	
	@Column(name="sign_amount")
	private Double sign_amount;//预计体检金额
	
	@Column(name="end_date")
	private Date end_date;//预计体检结束日期
	
	@Column(name="track_progress")
	private String track_progress;//跟踪进度
	
	@Column(name="track_time")
	private Date track_time;//跟踪进度变化时间
	
	@Column(name="sign_status")
	private String sign_status;//签单计划状态
	
	@Column(name="protect_date")
	private Date protect_date;//保护日期
	
	@Column(name="abort_date")
	private Date abort_date;//保护截止日期
	
	@Column(name="create_time")
	private Date create_time;//申请时间
	
	@Column(name="creater")
	private long creater;//申请人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getSign_num() {
		return sign_num;
	}

	public void setSign_num(String sign_num) {
		this.sign_num = sign_num;
	}

	public String getSign_name() {
		return sign_name;
	}

	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}

	public String getSign_year() {
		return sign_year;
	}

	public void setSign_year(String sign_year) {
		this.sign_year = sign_year;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getOld_new_type() {
		return old_new_type;
	}

	public void setOld_new_type(String old_new_type) {
		this.old_new_type = old_new_type;
	}

	public String getCustomer_type() {
		return customer_type;
	}

	public void setCustomer_type(String customer_type) {
		this.customer_type = customer_type;
	}

	public long getSign_count() {
		return sign_count;
	}

	public void setSign_count(long sign_count) {
		this.sign_count = sign_count;
	}

	public Date getSign_date() {
		return sign_date;
	}

	public void setSign_date(Date sign_date) {
		this.sign_date = sign_date;
	}

	public Long getSign_persion() {
		return sign_persion;
	}

	public void setSign_persion(Long sign_persion) {
		this.sign_persion = sign_persion;
	}

	public Double getSign_amount() {
		return sign_amount;
	}

	public void setSign_amount(Double sign_amount) {
		this.sign_amount = sign_amount;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getTrack_progress() {
		return track_progress;
	}

	public void setTrack_progress(String track_progress) {
		this.track_progress = track_progress;
	}

	public Date getTrack_time() {
		return track_time;
	}

	public void setTrack_time(Date track_time) {
		this.track_time = track_time;
	}

	public String getSign_status() {
		return sign_status;
	}

	public void setSign_status(String sign_status) {
		this.sign_status = sign_status;
	}

	public Date getProtect_date() {
		return protect_date;
	}

	public void setProtect_date(Date protect_date) {
		this.protect_date = protect_date;
	}

	public Date getAbort_date() {
		return abort_date;
	}

	public void setAbort_date(Date abort_date) {
		this.abort_date = abort_date;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	
	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public String getSign_pingying() {
		return sign_pingying;
	}

	public void setSign_pingying(String sign_pingying) {
		this.sign_pingying = sign_pingying;
	}
}
