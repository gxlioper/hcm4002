package com.hjw.Bean.HYT;

public class Questionnaire {

	private int marital;//0：不限；1：未婚；2：已婚
	private int gender;//0：不限；1：男；2：女
	private long createTime;//
	private String name = "";//
	private String mobile = "";//
	private String identity = "";//
	private String answers = "";//问卷答案
	private String recommends = "";//体检推荐
	
	public int getMarital() {
		return marital;
	}
	public int getGender() {
		return gender;
	}
	public long getCreateTime() {
		return createTime;
	}
	public String getName() {
		return name;
	}
	public String getMobile() {
		return mobile;
	}
	public String getIdentity() {
		return identity;
	}
	public String getAnswers() {
		return answers;
	}
	public String getRecommends() {
		return recommends;
	}
	public void setMarital(int marital) {
		this.marital = marital;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public void setRecommends(String recommends) {
		this.recommends = recommends;
	}
}
