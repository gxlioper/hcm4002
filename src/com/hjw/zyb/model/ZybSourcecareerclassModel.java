package com.hjw.zyb.model;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.model   
     * @Description:  
     * @author: zt    
     * @date:   2017年4月18日 上午10:35:52   
     * @version V2.0.0.0
 */
public class ZybSourcecareerclassModel implements java.io.Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	private String sc_classid;
	private String sourceid;
	private String sc_classcode;
	private String sc_classname;
	private String source_name;
	
	
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

	public ZybSourcecareerclassModel() {
		// TODO Auto-generated constructor stub
	}
}
