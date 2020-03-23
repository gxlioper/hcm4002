package com.hjw.Bean.ZJ;

public class ReqMsgHeader {
	private String auth = "1";// 是否需要认证",
	private String med_id = "";// 中心id",
	private String ciyun_id = "EFBBBFE68588E4BA91E5BEAEE4BFA1";// 慈云平台ID
	private String key = "";// 私钥或公钥加密的DES秘钥KEY",

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getMed_id() {
		return med_id;
	}

	public void setMed_id(String med_id) {
		this.med_id = med_id;
	}

	public String getCiyun_id() {
		return ciyun_id;
	}

	public void setCiyun_id(String ciyun_id) {
		this.ciyun_id = ciyun_id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
