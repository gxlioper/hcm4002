package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  系统功能菜单表
     * @author: yangm     
     * @date:   2016年6月30日 下午2:37:06   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "Web_Xtgnb")
public class WebXtgnb implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;

	@Column(name = "url")
	private String url;

	@Column(name = "remark")
	private String remark;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;
	
	@Column(name = "fatheraction")
	private String fatheraction;
	
	@Column(name = "actiontype")
	private String actiontype;
	
	@Column(name = "admintype")
	private String admintype;
	
	@Column(name = "apptype")
	private String apptype="";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFatheraction() {
		return fatheraction;
	}

	public void setFatheraction(String fatheraction) {
		this.fatheraction = fatheraction;
	}

	public String getActiontype() {
		return actiontype;
	}

	public void setActiontype(String actiontype) {
		this.actiontype = actiontype;
	}

	public String getAdmintype() {
		return admintype;
	}

	public void setAdmintype(String admintype) {
		this.admintype = admintype;
	}

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	
	

}