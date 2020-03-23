package com.hjw.wst.DTO;

public class DiversInfoDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String code;//设备编号
	private String com_name;//设备厂家名称
	private String com_type;//设备类型 1 表示身份证读卡器
	private String com_type_name;//设备类型名称
	private String com_ocx_name;//ocx名称
	private String is_active;
	private String remark;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCom_name() {
		return com_name;
	}
	public void setCom_name(String com_name) {
		this.com_name = com_name;
	}
	public String getCom_type() {
		return com_type;
	}
	public void setCom_type(String com_type) {
		this.com_type = com_type;
	}
	public String getCom_type_name() {
		return com_type_name;
	}
	public void setCom_type_name(String com_type_name) {
		this.com_type_name = com_type_name;
	}
	public String getCom_ocx_name() {
		return com_ocx_name;
	}
	public void setCom_ocx_name(String com_ocx_name) {
		this.com_ocx_name = com_ocx_name;
	}
	public String getIs_active() {
		return is_active;
	}
	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
