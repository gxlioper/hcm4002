package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SYS_LOG")
public class SysLog implements java.io.Serializable{
	/**
	* 注释内容
	*/
	private static final long serialVersionUID = 4456268124096194726L;
	
	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id", unique = true, nullable = false,length = 50)
	private String id="";
	
	@Column(name = "userid")
	private String userid=""; //操作人员id
	
	@Column(name = "xtgnid")
	private String xtgnid="";  // -- 系统主功能id   可以为空
	
	@Column(name = "xtgnid2")
	private String xtgnid2="";  //系统子功能id
	
	@Column(name = "OPER_TYPE")
	private String oper_type="";  // 0 查询，1插入 2修改 3 删除 4导入  9 其他
	
	@Column(name = "createdate")
	private String createdate="";  //创建时间
	
	@Column(name = "center_num")
	private String center_num;
	
	@Column(name = "explain")
	private String explain="";
	
	@Column(name = "remark")
	private String remark="";
	
	@Column(name = "remark1")
	private String remark1="";
	
	@Column(name = "remark2")
	private String remark2="";	
	
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}	
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getXtgnid() {
		return xtgnid;
	}
	public void setXtgnid(String xtgnid) {
		this.xtgnid = xtgnid;
	}
	public String getXtgnid2() {
		return xtgnid2;
	}
	public void setXtgnid2(String xtgnid2) {
		this.xtgnid2 = xtgnid2;
	}
	public String getOper_type() {
		return oper_type;
	}
	public void setOper_type(String oper_type) {
		this.oper_type = oper_type;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
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
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
	
}
