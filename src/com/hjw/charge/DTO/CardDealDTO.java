package com.hjw.charge.DTO;

import java.util.Date;


public class CardDealDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
		
	private String id;
	private String card_num;
	private Long examinfo_id;
	private String deal_type;
	private String deal_type_y;
	private Double amount;
	private String creater;
	private Date deal_time;
	private String deal_date;
	private Long card_count;
	private String remark;
	
	private String exam_num;
    
    public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCard_num() {
		return card_num;
	}
	public void setCard_num(String card_num) {
		this.card_num = card_num;
	}
	public Long getExaminfo_id() {
		return examinfo_id;
	}
	public void setExaminfo_id(Long examinfo_id) {
		this.examinfo_id = examinfo_id;
	}
	public String getDeal_type() {
		return deal_type;
	}
	public void setDeal_type(String deal_type) {
		this.deal_type = deal_type;
		if(deal_type.equals("1")){
			this.setDeal_type_y("恢复");
		}else if(deal_type.equals("2")){
			this.setDeal_type_y("锁定");
		}else if(deal_type.equals("3")){
			this.setDeal_type_y("挂失");
		}else if(deal_type.equals("4")){
			this.setDeal_type_y("作废");
		}else if(deal_type.equals("5")){
			this.setDeal_type_y("充值");
		}else if(deal_type.equals("6")){
			this.setDeal_type_y("消费");
		}else if(deal_type.equals("7")){
			this.setDeal_type_y("退费");
		}
	}
	public String getDeal_type_y() {
		return deal_type_y;
	}
	public void setDeal_type_y(String deal_type_y) {
		this.deal_type_y = deal_type_y;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public Date getDeal_time() {
		return deal_time;
	}
	public void setDeal_time(Date deal_time) {
		this.deal_time = deal_time;
	}
	public String getDeal_date() {
		return deal_date;
	}
	public void setDeal_date(String deal_date) {
		this.deal_date = deal_date;
	}
	public Long getCard_count() {
		return card_count;
	}
	public void setCard_count(Long card_count) {
		this.card_count = card_count;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}