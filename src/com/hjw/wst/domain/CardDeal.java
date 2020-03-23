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
     * @Title:  火箭蛙体检管理系统   (交易流水表)
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月14日 下午3:39:36   
     * @version V2.0.0.0
 */
@Entity
@Table(name="card_deal")
public class CardDeal implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="card_num")
	private String card_num;
	
	@Column(name="examinfo_id")
	private Long examinfo_id;
	
	@Column(name="deal_type")
	private String deal_type;
	
	@Column(name="amount")
	private Double amount;
	
	@Column(name="old_amount")
	private Double old_amount;
	
	@Column(name="creater")
	private Long creater;
	
	@Column(name="deal_time")
	private Date deal_time;
	
	@Column(name="deal_date")
	private String deal_date;
	
	@Column(name="card_count")
	private Long card_count;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="summary_id")
	private long summary_id;
	
	@Column(name="trancode")
	private String trancode;//交易类型 001 表示卡操作类 002 表示发票交易类
	
	@Column(name="invoice_class")
	private String invoice_class;
	
	@Column(name="exam_num")
    private String exam_num;
    
    public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getTrancode() {
		return trancode;
	}

	public void setTrancode(String trancode) {
		this.trancode = trancode;
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
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getOld_amount() {
		return old_amount;
	}

	public void setOld_amount(Double old_amount) {
		this.old_amount = old_amount;
	}

	public Long getCreater() {
		return creater;
	}

	public void setCreater(Long creater) {
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

	public long getSummary_id() {
		return summary_id;
	}

	public void setSummary_id(long summary_id) {
		this.summary_id = summary_id;
	}

	public String getInvoice_class() {
		return invoice_class;
	}

	public void setInvoice_class(String invoice_class) {
		this.invoice_class = invoice_class;
	}
}
