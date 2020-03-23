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
     * @Description:  收费员日结收费明细
     * @author: dangqi     
     * @date:   2017年11月2日 下午12:02:21   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "cashier_daily_acc_list")
public class CashierDailyAccList implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;
	
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;

	@Column(name = "daily_acc_num")
	private String daily_acc_num;
	
	@Column(name = "exam_num")
	private String exam_num;
	
	@Column(name = "summary_id")
	private long summary_id;
	
	@Column(name = "exam_type")
	private String exam_type;
	
	@Column(name = "team_acc_num")
	private String team_acc_num;
	
	@Column(name = "create_time")
	private Date create_time;
	
	@Column(name = "creater")
	private long creater;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDaily_acc_num() {
		return daily_acc_num;
	}

	public void setDaily_acc_num(String daily_acc_num) {
		this.daily_acc_num = daily_acc_num;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public long getSummary_id() {
		return summary_id;
	}

	public void setSummary_id(long summary_id) {
		this.summary_id = summary_id;
	}

	public String getExam_type() {
		return exam_type;
	}

	public void setExam_type(String exam_type) {
		this.exam_type = exam_type;
	}

	public String getTeam_acc_num() {
		return team_acc_num;
	}

	public void setTeam_acc_num(String team_acc_num) {
		this.team_acc_num = team_acc_num;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public long getCreater() {
		return creater;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}
}
