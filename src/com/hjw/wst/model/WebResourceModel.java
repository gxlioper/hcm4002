package com.hjw.wst.model;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description:  资源管理
     * @author: zr     
     * @date:   2016年12月15日 下午4:29:44   
     * @version V2.0.0.0
 */
public class WebResourceModel  implements java.io.Serializable {
	private  String code;
	private  String  name;
	private  String  type;
	private  String  data_type;
	private  String  notice;
	private  String  example;
	private  String  examplenote;
	private  String  remark;
	private  String  remark1;
	
	private  String iid;
	private  long   id; 
	private  String  res_code;
	private  String  res_type;
	private  String  datavalue;
	
	private String li;
	
	
	
	public String getLi() {
		return li;
	}
	public void setLi(String li) {
		this.li = li;
	}
	public String getIid() {
		return iid;
	}
	public void setIid(String iid) {
		this.iid = iid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRes_code() {
		return res_code;
	}
	public void setRes_code(String res_code) {
		this.res_code = res_code;
	}
	public String getRes_type() {
		return res_type;
	}
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}
	public String getDatavalue() {
		return datavalue;
	}
	public void setDatavalue(String datavalue) {
		this.datavalue = datavalue;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getNotice() {
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}
	public String getExample() {
		return example;
	}
	public void setExample(String example) {
		this.example = example;
	}
	public String getExamplenote() {
		return examplenote;
	}
	public void setExamplenote(String examplenote) {
		this.examplenote = examplenote;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	
}
