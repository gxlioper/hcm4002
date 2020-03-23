package com.hjw.Bean.HYTnew;

public class UserInfo {

	private int gender;//0：不限；1：男；2：女
	private int marital;//0：不限；1：未婚；2：已婚
	private String genders;//0：不限；1：男；2：女
	private String maritals;//0：不限；1：未婚；2：已婚
	private String name = "";
	private String mobile = "";
	private String age = "";
	private String identity = "";
	
	public int getGender() {
		return gender;
	}
	public int getMarital() {
		return marital;
	}
	public String getName() {
		return name;
	}
	public String getMobile() {
		return mobile;
	}
	public String getAge() {
		return age;
	}
	public String getIdentity() {
		return identity;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public void setMarital(int marital) {
		this.marital = marital;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getGenders() {
		if( 0 == gender ) {
			this.setGenders("不限");
		} else if( 1 == gender ) {
			this.setGenders("男");
		} else if( 2 == gender ) {
			this.setGenders("女");
		}
		return genders;
	}
	public String getMaritals() {
		if( 0 == marital ) {
			this.setMaritals("不限");
		} else if( 1 == marital ) {
			this.setMaritals("未婚");
		} else if( 2 == marital ) {
			this.setMaritals("已婚");
		}
		return maritals;
	}
	public void setGenders(String genders) {
		this.genders = genders;
	}
	public void setMaritals(String maritals) {
		this.maritals = maritals;
	}
	
}
