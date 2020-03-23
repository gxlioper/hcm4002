package com.hjw.zyb.DTO;

import java.io.Serializable;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.DTO   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月18日 上午10:36:24   
     * @version V2.0.0.0
 */
public class ZybSourcecareerclassDTO implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;
	
	private String sc_classid;
	private String sourceid;
	private String sc_classcode;
	private String sc_classname;
	private String source_name;
	private String source_id;
	
	
	public String getSource_id() {
		return source_id;
	}
	public void setSource_id(String source_id) {
		this.source_id = source_id;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getSc_classid() {
		return sc_classid;
	}
	public void setSc_classid(String sc_classid) {
		this.sc_classid = sc_classid;
	}
	public String getSourceid() {
		return sourceid;
	}
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}
	public String getSc_classcode() {
		return sc_classcode;
	}
	public void setSc_classcode(String sc_classcode) {
		this.sc_classcode = sc_classcode;
	}
	public String getSc_classname() {
		return sc_classname;
	}
	public void setSc_classname(String sc_classname) {
		this.sc_classname = sc_classname;
	}
	
}
