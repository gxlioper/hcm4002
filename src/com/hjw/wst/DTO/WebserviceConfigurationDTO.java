package com.hjw.wst.DTO;

public class WebserviceConfigurationDTO implements java.io.Serializable{

	private static final long serialVersionUID = -97502163798576023L;
	private String center_num="";
	private String config_key="";
	private String config_url="";
	private String config_method="";
	private String config_value="";
	private String config_remark="";
	
	public String getConfig_value() {
		return config_value;
	}
	public void setConfig_value(String config_value) {
		this.config_value = config_value;
	}
	public String getConfig_key() {
		return config_key;
	}
	public void setConfig_key(String config_key) {
		this.config_key = config_key;
	}
	public String getConfig_url() {
		return config_url;
	}
	public void setConfig_url(String config_url) {
		this.config_url = config_url;
	}
	public String getConfig_method() {
		return config_method;
	}
	public void setConfig_method(String config_method) {
		this.config_method = config_method;
	}
	public String getConfig_remark() {
		return config_remark;
	}
	public void setConfig_remark(String config_remark) {
		this.config_remark = config_remark;
	}
	public String getCenter_num() {
		return center_num;
	}
	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}
}
