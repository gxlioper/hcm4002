package com.hjw.wst.domain;

import java.io.Serializable;

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
     * @Description:  
     * @author: zr     
     * @date:   2019年7月29日 上午9:33:34   
     * @version V2.0.0.0
 */
@Entity
@Table(name = "critical_visit_plan")
public class CriticalVisitPlan implements Serializable{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -4419046566302060835L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	//健康计划编码
	@Column(name="plan_visit_num")
	private String plan_visit_num;
	//危急值id
	@Column(name="critical_id")
	private long critical_id;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPlan_visit_num() {
		return plan_visit_num;
	}
	public void setPlan_visit_num(String plan_visit_num) {
		this.plan_visit_num = plan_visit_num;
	}
	public long getCritical_id() {
		return critical_id;
	}
	public void setCritical_id(long critical_id) {
		this.critical_id = critical_id;
	}
	
}
