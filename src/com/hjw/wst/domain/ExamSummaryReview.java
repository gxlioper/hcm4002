package com.hjw.wst.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 总检复查设定表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dq     
     * @date:   2018年3月6日 上午9:48:45   
     * @version V2.0.0.0
 */
@Entity
@Table(name="exam_summary_review")
public class ExamSummaryReview implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_num")
	private String exam_num;//体检号
	
	@Column(name="review_status")
	private String review_status;//复查状态 1表示未通知、2表示已通知、3表示作废
	
	@Column(name="review_title")
	private String review_title;//复查主题
	
	@Column(name="review_date")
	private Date review_date; //复查日期
	
	@Column(name="review_user")
	private long review_user; //复查设定医生
	
	@Column(name="review_time")
	private Date review_time;//复查设定时间
	
	@Column(name="notice_user")
	private long notice_user;//通知人
	
	@Column(name="notice_time")
	private Date notice_time;//通知时间
	
	@Column(name="notice_type")
	private String notice_type;//通知方式 1表示短信通知、2表示电话通知、3表示email通知

	@Column(name="apptype")
	private String apptype; //  体检类型 1健康体检  2 职业病


	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

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
	}

	public String getReview_title() {
		return review_title;
	}

	public void setReview_title(String review_title) {
		this.review_title = review_title;
	}

	public Date getReview_date() {
		return review_date;
	}

	public void setReview_date(Date review_date) {
		this.review_date = review_date;
	}

	public long getReview_user() {
		return review_user;
	}

	public void setReview_user(long review_user) {
		this.review_user = review_user;
	}

	public Date getReview_time() {
		return review_time;
	}

	public void setReview_time(Date review_time) {
		this.review_time = review_time;
	}

	public long getNotice_user() {
		return notice_user;
	}

	public void setNotice_user(long notice_user) {
		this.notice_user = notice_user;
	}

	public Date getNotice_time() {
		return notice_time;
	}

	public void setNotice_time(Date notice_time) {
		this.notice_time = notice_time;
	}

	public String getNotice_type() {
		return notice_type;
	}

	public void setNotice_type(String notice_type) {
		this.notice_type = notice_type;
	}
}
