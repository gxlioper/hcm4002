package com.hjw.wst.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "company_info_vs_center")
public class CompanyInfoVsCenter implements Serializable{
	
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	@Id
	private CompanyInfoVsCenterPk companyInfoVsCenterPk;
	@Column(name = "is_owner")
	private String is_owner;
	@Column(name = "creater")
	private long creater;
	@Column(name = "create_time")
	private Date create_time;
	
	public CompanyInfoVsCenterPk getCompanyInfoVsCenterPk() {
		return companyInfoVsCenterPk;
	}
	public void setCompanyInfoVsCenterPk(CompanyInfoVsCenterPk companyInfoVsCenterPk) {
		this.companyInfoVsCenterPk = companyInfoVsCenterPk;
	}
	public String getIs_owner() {
		return is_owner;
	}
	public void setIs_owner(String is_owner) {
		this.is_owner = is_owner;
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
