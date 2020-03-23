package com.hjw.charge.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="invoice_information")
public class InvoiceInformationCharge implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	/**购方名称  **/
	@Column(name="infoClientName")
	private String infoClientName;
	
	/**购方税号   **/
	@Column(name="infoClientTaxCode")
	private String infoClientTaxCode;
	
	/**购方开户行及账号  **/
	@Column(name="infoClientBankAccount")
	private String infoClientBankAccount;
	
	/**购方地址电话  **/
	@Column(name="infoClientAddressPhone")
	private String infoClientAddressPhone;
	
	/**发票类型(0:增值税发票;2:增值税普通发票)  **/
	@Column(name="infoKind")
	private String infoKind;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getInfoClientName() {
		return infoClientName;
	}

	public void setInfoClientName(String infoClientName) {
		this.infoClientName = infoClientName;
	}

	public String getInfoClientTaxCode() {
		return infoClientTaxCode;
	}

	public void setInfoClientTaxCode(String infoClientTaxCode) {
		this.infoClientTaxCode = infoClientTaxCode;
	}

	public String getInfoClientBankAccount() {
		return infoClientBankAccount;
	}

	public void setInfoClientBankAccount(String infoClientBankAccount) {
		this.infoClientBankAccount = infoClientBankAccount;
	}

	public String getInfoClientAddressPhone() {
		return infoClientAddressPhone;
	}

	public void setInfoClientAddressPhone(String infoClientAddressPhone) {
		this.infoClientAddressPhone = infoClientAddressPhone;
	}

	public String getInfoKind() {
		return infoKind;
	}

	public void setInfoKind(String infoKind) {
		this.infoKind = infoKind;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

}
