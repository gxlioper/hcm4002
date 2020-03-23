package com.hjw.wst.domain;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="exam_summary_review_item")
public class ExamSummaryReviewItem implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_num")
	private String exam_num;//体检号

	@Column(name="charging_item_id")
	private long charging_item_id;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

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

	public long getCharging_item_id() {
		return charging_item_id;
	}

	public void setCharging_item_id(long charging_item_id) {
		this.charging_item_id = charging_item_id;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
}
