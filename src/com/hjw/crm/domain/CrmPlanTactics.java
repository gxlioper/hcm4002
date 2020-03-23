package com.hjw.crm.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "crm_plan_tactics")//健康计划策略表crm_plan_tactics
public class CrmPlanTactics {
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="tactics_num")
	private String tactics_num;//策略编码
	
	@Column(name="notices")
	private String notices;//策略描述  
	
	@Column(name="tactics_type")
	private int tactics_type;//策略类型  表示：1 慢病、2复查、3危机值、4vip回访，5特殊回访
	
	@Column(name="rmark")
	private String rmark;//对应策略说明
	
	@Column(name = "creater")
	private long creater;
	
	@Column(name = "create_date")
	private Date create_date;
	
 	@Column(name = "updater")
 	private long updater;
	    
    @Column(name = "update_date")
    private Date update_date;
    
    @Column(name = "level")
    private long  level;      //子类型   字典表取值

	public long getId() {
		return id;
	}

	public String getTactics_num() {
		return tactics_num;
	}

	public String getNotices() {
		return notices;
	}

	public int getTactics_type() {
		return tactics_type;
	}

	public String getRmark() {
		return rmark;
	}

	public long getCreater() {
		return creater;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public long getUpdater() {
		return updater;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setTactics_num(String tactics_num) {
		this.tactics_num = tactics_num;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

	public void setTactics_type(int tactics_type) {
		this.tactics_type = tactics_type;
	}

	public void setRmark(String rmark) {
		this.rmark = rmark;
	}

	public void setCreater(long creater) {
		this.creater = creater;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public void setUpdater(long updater) {
		this.updater = updater;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
		this.level = level;
	}

	


}
