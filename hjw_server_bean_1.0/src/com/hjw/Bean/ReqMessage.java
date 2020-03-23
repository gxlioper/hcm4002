package com.hjw.Bean;

public class ReqMessage {
	private String version="";//版本号
	private String thridcode="";//介入第三方代码
	private String messagetype="";//消息类型
	private String messagesign="";//消息签名
	private String centernum="";//体检中心编号
	private Object body="";//加密以后的数据	
	
	public String getCenternum() {
		return centernum;
	}
	public void setCenternum(String centernum) {
		this.centernum = centernum;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getThridcode() {
		return thridcode;
	}
	public void setThridcode(String thridcode) {
		this.thridcode = thridcode;
	}
	public String getMessagetype() {
		return messagetype;
	}
	public void setMessagetype(String messagetype) {
		this.messagetype = messagetype;
	}
	public String getMessagesign() {
		return messagesign;
	}
	public void setMessagesign(String messagesign) {
		this.messagesign = messagesign;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}

	
}
