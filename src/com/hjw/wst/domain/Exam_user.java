package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "exam_user")
public class Exam_user {
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	@Column(name = "user_id")
	private Long user_id;
	
	@Column(name = "exam_center_id")
	private Long exam_center_id;
	
	@Column(name = "center_num")
	private String center_num;	
	
	@Column(name = "is_default")
	private String is_default="0";	

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getCenter_num() {
		return center_num;
	}

	public void setCenter_num(String center_num) {
		this.center_num = center_num;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getExam_center_id() {
		return exam_center_id;
	}

	public void setExam_center_id(Long exam_center_id) {
		this.exam_center_id = exam_center_id;
	}

	
	
	
}
