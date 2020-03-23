package com.hjw.Bean.ZJ;

public class ResMsg {	
	private String result="";//是否成功	boolean	0-成功/其他-失败
	private String errMsg="";//错误消息	string	当result!=0时，有此属性

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

}
