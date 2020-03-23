package com.hjw.wst.DTO;

public class MultiSignSearch {
	
	private String error="";// null,   错误提示
	private String status="";// “SUCCESS”, 正确返回SUCCESS，错误返回 ERROR
	private String msg="";//消息 “姓名,床号$体检编号:123456” 
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
