package com.hjw.wst.model;

import java.util.List;

public class WhlgModel {
	private long account;//账号
	private String phone;//手机号码
	private String password;//查询密码
	private String modLimite;//消费限额
	private List accList;
	private String captcha;
	public long getAccount() {
		return account;
	}
	public void setAccount(long account) {
		this.account = account;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getModLimite() {
		return modLimite;
	}
	public void setModLimite(String modLimite) {
		this.modLimite = modLimite;
	}
	public List getAccList() {
		return accList;
	}
	public void setAccList(List accList) {
		this.accList = accList;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
}
