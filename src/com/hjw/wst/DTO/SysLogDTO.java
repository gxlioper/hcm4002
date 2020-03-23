package com.hjw.wst.DTO;

public class SysLogDTO implements java.io.Serializable{
	/**
	* 注释内容
	*/
	private static final long serialVersionUID = 4456268124096194726L;

	private String id="";

	private String userid=""; //操作人员id
	
	private String username=""; //操作人员姓名

	private String xtgnid="";  //系统功能表主功能id
	
	private String xtgnname="";

	private String xtgnid2="";  //系统功能表子功能id
	
	private String xtgnname2="";

	private String oper_type="";  //0 查询，1插入 2修改 3 删除 4导入  9 其他
	
	private String oper_types="";  // 0 查询，1插入 2修改 3 删除 4导入  9 其他

	private String createdate="";  //创建时间
	
	private String center_num="";

	private String explain="";

	private String remark="";

	private String remark1="";

	private String remark2="";
	
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
		this.oper_type = oper_type;//0 查询，1插入 2修改 3 删除 4导入
		if("0".equals(oper_type)){
			this.setOper_types("查询");
		}else if("1".equals(oper_type)){
			this.setOper_types("插入");
		}else if("2".equals(oper_type)){
			this.setOper_types("修改");
		}else if("3".equals(oper_type)){
			this.setOper_types("删除");
		}else if("4".equals(oper_type)){
			this.setOper_types("导入");
		}else if("5".equals(oper_type)){
			this.setOper_types("登录");
		}else if("6".equals(oper_type)){
			this.setOper_types("报表查询");
		}else if("9".equals(oper_type)){
			this.setOper_types("其他");
		}else{
			this.setOper_types("未知");
		}
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOper_types() {
		return oper_types;
	}
	public void setOper_types(String oper_types) {
		this.oper_types = oper_types;
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
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
	public String getXtgnname() {
		return xtgnname;
	}
	public void setXtgnname(String xtgnname) {
		this.xtgnname = xtgnname;
	}
	public String getXtgnname2() {
		return xtgnname2;
	}
	public void setXtgnname2(String xtgnname2) {
		this.xtgnname2 = xtgnname2;
	}
	
	
}
