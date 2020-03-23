package com.hjw.zyb.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 职业禁忌证
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.zyb.domain
 * @Description:
 * @author: zr
 * @date: 2017年4月12日 下午3:45:17
 * @version V2.0.0.0
 */
@Entity
@Table(name = "zyb_occucontraindication")
public class Zyboccucontraindication implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "contraindicationID", unique = true, nullable = false, length = 50)
	private String contraindicationID;

	@Column(name = "contraindication_name")
	private String contraindication_name;

	@Column(name = "tremexplain")
	private String tremexplain;

	public String getContraindicationID() {
		return contraindicationID;
	}

	public void setContraindicationID(String contraindicationID) {
		this.contraindicationID = contraindicationID;
	}

	public String getContraindication_name() {
		return contraindication_name;
	}

	public void setContraindication_name(String contraindication_name) {
		this.contraindication_name = contraindication_name;
	}

	public String getTremexplain() {
		return tremexplain;
	}

	public void setTremexplain(String tremexplain) {
		this.tremexplain = tremexplain;
	}

}
