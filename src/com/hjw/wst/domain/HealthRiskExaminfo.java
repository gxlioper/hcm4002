package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="health_risk_examinfo")
public class HealthRiskExaminfo implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="exam_num")
	private String exam_num;
	
	@Column(name="health_risk_id")
	private long health_risk_id;
	
	@Column(name="points")
	private long points;
	
	@Column(name="picture_path")
	private String picture_path;
	
	@Column(name="is_success")
	private long is_success;
	
	@Column(name="cause_failure")
	private String cause_failure;
	
	@Column(name="creater")
	private long creater;
	
	@Column(name="create_time")
	private Date create_time;

	@Column(name="reality_morbidity")
	private Long reality_morbidity;
	
	@Column(name="average_morbidity")
	private Double average_morbidity;
	
	@Column(name="hard_morbidity")
	private Double hard_morbidity;
	
	@Column(name="low_morbidity")
	private Double low_morbidity;
	
	@Column(name="is_active")
	private String is_active;
	
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

	public long getHealth_risk_id() {
		return health_risk_id;
	}

	public void setHealth_risk_id(long health_risk_id) {
		this.health_risk_id = health_risk_id;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public String getPicture_path() {
		return picture_path;
	}

	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}

	public long getIs_success() {
		return is_success;
	}

	public void setIs_success(long is_success) {
		this.is_success = is_success;
	}

	public String getCause_failure() {
		return cause_failure;
	}

	public void setCause_failure(String cause_failure) {
		this.cause_failure = cause_failure;
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

	public Long getReality_morbidity() {
		return reality_morbidity;
	}

	public void setReality_morbidity(Long reality_morbidity) {
		this.reality_morbidity = reality_morbidity;
	}

	public Double getAverage_morbidity() {
		return average_morbidity;
	}

	public void setAverage_morbidity(Double average_morbidity) {
		this.average_morbidity = average_morbidity;
	}

	public Double getHard_morbidity() {
		return hard_morbidity;
	}

	public void setHard_morbidity(Double hard_morbidity) {
		this.hard_morbidity = hard_morbidity;
	}

	public Double getLow_morbidity() {
		return low_morbidity;
	}

	public void setLow_morbidity(Double low_morbidity) {
		this.low_morbidity = low_morbidity;
	}

	public String getIs_active() {
		return is_active;
	}

	public void setIs_active(String is_active) {
		this.is_active = is_active;
	}
}
