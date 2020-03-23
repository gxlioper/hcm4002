package com.hjw.wst.DTO;

public class ExamSummaryReviewDTO implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	
	private long id;
	private String exam_num;//体检号
	private String review_status;//复查状态 1表示未通知、2表示已通知、3表示作废
	private String review_statuss;
	private String review_title;//复查主题
	private String review_date; //复查日期
	private String review_user; //复查设定医生
	private String review_time;//复查设定时间
	private String notice_user;//通知人
	private String notice_time;//通知时间
	private String notice_type;//通知方式 1表示短信通知、2表示电话通知、3表示email通知
	private String notice_types;
	
	private String arch_num;
	private String user_name;
	private String sex;
	private String phone;
	private long age;
	private String birthday;
	private String company;
	private String join_date;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getReview_status() {
		return review_status;
	}

	public void setReview_status(String review_status) {
		this.review_status = review_status;
		if("1".equals(review_status)){
			this.review_statuss = "未通知";
		}else if("2".equals(review_status)){
			this.review_statuss = "已通知";
		}else if("3".equals(review_status)){
			this.review_statuss = "作废";
		}else if("4".equals(review_status)){
			this.review_statuss = "取消复查";
		}else{
			this.review_statuss = "未知";
		}
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public String getReview_date() {
		return review_date;
	}

	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}

	public String getReview_user() {
		return review_user;
	}

	public void setReview_user(String review_user) {
		this.review_user = review_user;
	}

	public String getReview_time() {
		return review_time;
	}

	public void setReview_time(String review_time) {
		this.review_time = review_time;
	}

	public String getNotice_user() {
		return notice_user;
	}

	public void setNotice_user(String notice_user) {
		this.notice_user = notice_user;
	}

	public String getNotice_time() {
		return notice_time;
	}

	public void setNotice_time(String notice_time) {
		this.notice_time = notice_time;
	}

	public String getNotice_type() {
		return notice_type;
	}

	public void setNotice_type(String notice_type) {
		this.notice_type = notice_type;
		if("1".equals(notice_type)){
			this.notice_types = "短信";
		}else if("2".equals(notice_type)){
			this.notice_types = "电话";
		}else if("3".equals(notice_type)){
			this.notice_types = "email";
		}
	}

	public String getReview_statuss() {
		return review_statuss;
	}

	public void setReview_statuss(String review_statuss) {
		this.review_statuss = review_statuss;
	}

	public String getNotice_types() {
		return notice_types;
	}

	public void setNotice_types(String notice_types) {
		this.notice_types = notice_types;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getJoin_date() {
		return join_date;
	}

	public void setJoin_date(String join_date) {
		this.join_date = join_date;
	}
}
