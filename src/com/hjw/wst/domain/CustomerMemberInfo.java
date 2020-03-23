package com.hjw.wst.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 	档案会员信息表
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.domain   
     * @Description:  
     * @author: dq     
     * @date:   2017年3月20日 下午12:00:32   
     * @version V2.0.0.0
 */
@Entity
@Table(name="customer_member_info")
public class CustomerMemberInfo implements java.io.Serializable {

	private static final long serialVersionUID = -97502163798576023L;

	@Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", unique = true, nullable = false,length = 50)
	private String id;
	
	@Column(name="arch_num")
	private String arch_num;
	
	@Column(name="level")
	private long level;
	
	@Column(name="integral")
	private long integral;
	
	@Column(name="totalamt")
	private Double totalamt;
	
	@Column(name="totaltimes")
	private long totaltimes;
	
	@Column(name="prelevel")
	private long prelevel;
	
	@Column(name="preintegral")
	private long preintegral;
	
	@Column(name="leveltime")
	private Date leveltime;
	
	@Column(name="integeraltime")
	private Date integeraltime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArch_num() {
		return arch_num;
	}

	public void setArch_num(String arch_num) {
		this.arch_num = arch_num;
	}

	public long getLevel() {
		return level;
	}

	public void setLevel(long level) {
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

	public long getPrelevel() {
		return prelevel;
	}

	public void setPrelevel(long prelevel) {
		this.prelevel = prelevel;
	}

	public long getPreintegral() {
		return preintegral;
	}

	public void setPreintegral(long preintegral) {
		this.preintegral = preintegral;
	}

	public Date getLeveltime() {
		return leveltime;
	}

	public void setLeveltime(Date leveltime) {
		this.leveltime = leveltime;
	}

	public Date getIntegeraltime() {
		return integeraltime;
	}

	public void setIntegeraltime(Date integeraltime) {
		this.integeraltime = integeraltime;
	}
}
