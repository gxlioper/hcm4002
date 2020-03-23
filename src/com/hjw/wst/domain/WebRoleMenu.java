package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * NFixcardId generated by MyEclipse - Hibernate Tools
 */

@Entity
@Table(name = "Web_RoleMenu")
public class WebRoleMenu implements java.io.Serializable {
	 private static final long serialVersionUID = -97502163798576023L;
	 @Id
	    @GeneratedValue(generator = "paymentableGenerator")
	    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;

		@Column(name = "role_id")
	private String role_id;

		@Column(name = "gncd_id")
	private String gncd_id;

		@Column(name = "remark")
	private String remark;

		@Column(name = "apptype")
		private String apptype="";	

		public String getApptype() {
				return apptype;
			}

			public void setApptype(String apptype) {
				this.apptype = apptype;
			}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getGncd_id() {
		return gncd_id;
	}

	public void setGncd_id(String gncd_id) {
		this.gncd_id = gncd_id;
	}

}