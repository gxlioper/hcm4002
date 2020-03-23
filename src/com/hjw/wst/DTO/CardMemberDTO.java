package com.hjw.wst.DTO;

public class CardMemberDTO implements java.io.Serializable {
	
	private static final long serialVersionUID = -97502163798576023L;

	private String id;
	private long customer_id;
	private String arch_num;
	private String user_name;
	private String id_num;
	private String sex;
	private String birthday;
	private String phone;
	private String photo;
	private String email;
	private String nation;
	private String address;
	private String level;
	private long integral;
	private Double totalamt;
	private long totaltimes;
	private String prelevel;
	private long preintegral;
	private String leveltime;
	private String integeraltime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getArch_num() {
		return arch_num;
	}
	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public long getIntegral() {
		return integral;
	}
	public void setIntegral(long integral) {
		this.integral = integral;
	}
	public Double getTotalamt() {
		return totalamt;
	}
	public void setTotalamt(Double totalamt) {
		this.totalamt = totalamt;
	}
	public long getTotaltimes() {
		return totaltimes;
	}
	public void setTotaltimes(long totaltimes) {
		this.totaltimes = totaltimes;
	}
	public String getPrelevel() {
		return prelevel;
	}
	public void setPrelevel(String prelevel) {
		this.prelevel = prelevel;
	}
	public long getPreintegral() {
		return preintegral;
	}
	public void setPreintegral(long preintegral) {
		this.preintegral = preintegral;
	}
	public String getLeveltime() {
		return leveltime;
	}
	public void setLeveltime(String leveltime) {
		this.leveltime = leveltime;
	}
	public String getIntegeraltime() {
		return integeraltime;
	}
	public void setIntegeraltime(String integeraltime) {
		this.integeraltime = integeraltime;
	}
}
