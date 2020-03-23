package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  普通科室细项结果日志
     * @author: dq     
     * @date:   2018年6月7日 上午11:44:21   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "common_exam_detail_log")
public class CommonExamDetailLog implements java.io.Serializable {
	private static final long serialVersionUID = 6349207076481074480L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "dep_result_id")
	private long dep_result_id;

	@Column(name = "exam_item_id")
	private long exam_item_id;

	@Column(name = "health_level")
	private String health_level;

	@Column(name = "exam_result")
	private String exam_result;

	@Column(name = "creater")
	private long creater;

	@Column(name = "create_time")
	private Date create_time;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getDep_result_id() {
		return dep_result_id;
	}

	public void setDep_result_id(long dep_result_id) {
		this.dep_result_id = dep_result_id;
	}

	public long getExam_item_id() {
		return exam_item_id;
	}

	public void setExam_item_id(long exam_item_id) {
		this.exam_item_id = exam_item_id;
	}

	public String getHealth_level() {
		return health_level;
	}

	public void setHealth_level(String health_level) {
		this.health_level = health_level;
	}

	public String getExam_result() {
		return exam_result;
	}

	public void setExam_result(String exam_result) {
		this.exam_result = exam_result;
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
