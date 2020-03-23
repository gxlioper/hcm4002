package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * NFixcardId generated by MyEclipse - Hibernate Tools
 */

public class WebXtgncdDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private String id;

	private String xtgn_id;

	private String remark;

	private String father_id;

	private String url_type;
	
	private String icon_url;

	private String datetime;

	private String ry;

	private String usertype;

	private String name;

	private long levels;

	private String other_url;

	private String indexid;

	private long ispop;
	
	private List<WebXtgncdDTO> list = new ArrayList<WebXtgncdDTO>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<WebXtgncdDTO> getList() {
		return list;
	}

	public void setList(List<WebXtgncdDTO> list) {
		this.list = list;
	}
}