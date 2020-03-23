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
@Table(name = "Web_Xtgncd")
public class WebXtgncd implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;

	@Column(name = "xtgn_id")
	private String xtgn_id;

	@Column(name = "remark")
	private String remark;

	@Column(name = "father_id")
	private String father_id;

	@Column(name = "urltype")
	private String url_type;

	@Column(name = "icon_url")
	private String icon_url;

	@Column(name = "datetime")
	private String datetime;

	@Column(name = "ry")
	private String ry;

	@Column(name = "usertype")
	private String usertype;

	@Column(name = "name")
	private String name;

	@Column(name = "levels")
	private long levels;

	@Column(name = "other_url")
	private String other_url;

	@Column(name = "indexid")
	private String indexid;

	@Column(name = "ispop")
	private long ispop;

	public String getId() {
		return id;
	}

	public String getXtgn_id() {
		return xtgn_id;
	}

	public void setXtgn_id(String xtgn_id) {
		this.xtgn_id = xtgn_id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFather_id() {
		return father_id;
	}

	public void setFather_id(String father_id) {
		this.father_id = father_id;
	}

	public String getUrl_type() {
		return url_type;
	}

	public void setUrl_type(String url_type) {
		this.url_type = url_type;
	}

	public String getIcon_url() {
		return icon_url;
	}

	public void setIcon_url(String icon_url) {
		this.icon_url = icon_url;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getRy() {
		return ry;
	}

	public void setRy(String ry) {
		this.ry = ry;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getLevels() {
		return levels;
	}

	public void setLevels(long levels) {
		this.levels = levels;
	}

	public String getOther_url() {
		return other_url;
	}

	public void setOther_url(String other_url) {
		this.other_url = other_url;
	}

	public String getIndexid() {
		return indexid;
	}

	public void setIndexid(String indexid) {
		this.indexid = indexid;
	}

	public long getIspop() {
		return ispop;
	}

	public void setIspop(long ispop) {
		this.ispop = ispop;
	}

	public void setId(String id) {
		this.id = id;
	}

}