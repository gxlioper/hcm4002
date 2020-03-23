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
     * @Title:  火箭蛙体检管理系统   体检卡信息表
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月14日 下午3:30:32   
     * @version V2.0.0.0
 */
@Entity
@Table(name="card_info")
public class CardInfo implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="is_set_status")
	private Long is_set_status;

	@Column(name="physical_num")
	private String physical_num;
	
	@Column(name="card_num")
	private String card_num;
	
	@Column(name="member_id")
	private String member_id;
	
	@Column(name="status")
	private String status;
	
	@Column(name="deadline")
	private Date deadline;
	
	@Column(name="card_type")
	private String card_type;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="face_amount")
	private Double face_amount;
	
	@Column(name="card_level")
	private String card_level;
	
	@Column(name="card_count")
	private long card_count;
	
	@Column(name="limit_card_count")
	private Long limit_card_count;
	
	@Column(name="card_pwd")
	private String card_pwd;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="creater")
	private Long creater;
	
	@Column(name="create_time")
	private Date create_time;
	
	@Column(name="updater")
	private Long updater;
	
	@Column(name="update_time")
	private Date update_time;
	
	@Column(name="send_status")
	private String send_status;
	
	@Column(name="active_status")
	private String active_status;
	
	@Column(name="discount")
	private double discount;
	
	@Column(name="company")
	private String company;
	
	
	@Column(name="sale_amount")
	private double sale_amount; //售卡金额
	
	@Column(name="hair_card_status")
	private long hair_card_status;//发卡状态 0未发卡 1表示已发卡
	
	@Column(name="hair_card_creater")
	private long hair_card_creater;//发卡人
	
	@Column(name="hair_card_create_time")
	private Date hair_card_create_time; //发卡
	
	@Column(name="sale_status")
	private long sale_status = 0; //售卡状态
	
	@Column(name="company_id")
	private long company_id = 0; //售卡状态
	
	@Column(name="center_num")
	private String center_num; //体检中心编码
	
	
	public Long getIs_set_status() {
		return is_set_status;
	}

	public void setIs_set_status(Long is_set_status) {
		this.is_set_status = is_set_status;
	}

	public long getSale_status() {
		return sale_status;
	}

	public void setSale_status(long sale_status) {
		this.sale_status = sale_status;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhysical_num() {
		return physical_num;
	}

	public void setPhysical_num(String physical_num) {
		this.physical_num = physical_num;
	}

	public String getCard_num() {
		return card_num;
	}

	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFace_amount() {
		return face_amount;
	}

	public void setFace_amount(Double face_amount) {
		this.face_amount = face_amount;
	}

	public String getCard_level() {
		return card_level;
	}

	public void setCard_level(String card_level) {
		this.card_level = card_level;
	}

	public long getCard_count() {
		return card_count;
	}

	public void setCard_count(long card_count) {
		this.card_count = card_count;
	}

	public Long getLimit_card_count() {
		return limit_card_count;
	}

	public void setLimit_card_count(Long limit_card_count) {
		this.limit_card_count = limit_card_count;
	}

	public String getCard_pwd() {
		return card_pwd;
	}

	public void setCard_pwd(String card_pwd) {
		this.card_pwd = card_pwd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Long getUpdater() {
		return updater;
	}

	public void setUpdater(Long updater) {
		this.updater = updater;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

	public String getSend_status() {
		return send_status;
	}

	public void setSend_status(String send_status) {
		this.send_status = send_status;
	}

	public String getActive_status() {
		return active_status;
	}

	public void setActive_status(String active_status) {
		this.active_status = active_status;
	}

	public double getSale_amount() {
		return sale_amount;
	}

	public void setSale_amount(double sale_amount) {
		this.sale_amount = sale_amount;
	}

	public long getHair_card_status() {
		return hair_card_status;
	}

	public void setHair_card_status(long hair_card_status) {
		this.hair_card_status = hair_card_status;
	}

	public long getHair_card_creater() {
		return hair_card_creater;
	}

	public void setHair_card_creater(long hair_card_creater) {
		this.hair_card_creater = hair_card_creater;
	}

	public Date getHair_card_create_time() {
		return hair_card_create_time;
	}

	public void setHair_card_create_time(Date hair_card_create_time) {
		this.hair_card_create_time = hair_card_create_time;
	}

	public long getCompany_id() {
		return company_id;
	}

	public void setCompany_id(long company_id) {
		this.company_id = company_id;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	
}
