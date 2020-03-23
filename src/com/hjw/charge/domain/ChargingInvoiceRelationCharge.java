package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//--收费记录与发票关系表
@Entity
@Table(name = "charging_invoice_relation")
public class ChargingInvoiceRelationCharge  implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	 @Column(name = "account_num")//--结算单号
	 private String account_num;
	 
	 @Column(name = "invoice_id") //--发票ID
	 private long invoice_id;
	 
	 @Column(name = "exam_type") //--结算类型  G个人收费、T团检结账、C会员卡销售、R单位账户充值
	 private String exam_type;
	 
	 @Column(name = "creater")
	 private long creater;
	 
	 @Column(name = "create_time")
	 private Date create_time;

	public long getId() {
		return id;
	}

	public String getAccount_num() {
		return account_num;
	}

	public long getInvoice_id() {
		return invoice_id;
	}

	public String getExam_type() {
		return exam_type;
	}

	public long getCreater() {
		return creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setAccount_num(String account_num) {
		this.account_num = account_num;
	}

	public void setInvoice_id(long invoice_id) {
		this.invoice_id = invoice_id;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	 
	 

}
