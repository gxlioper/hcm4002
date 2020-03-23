package com.hjw.wst.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="health_risk_examinfo_item")
public class HealthRiskExaminfoItem implements java.io.Serializable{
	
	private static final long serialVersionUID = -97502163798576023L;
	@Id
	@GenericGenerator(name = "idGenerator", strategy = "identity")
	@GeneratedValue(generator = "idGenerator")
	private long id;
	
	@Column(name="risk_examinfo_id")
	private long risk_examinfo_id;
	
	@Column(name="risk_item_code")
	private String risk_item_code;
	
	@Column(name="point")
	private long point;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRisk_examinfo_id() {
		return risk_examinfo_id;
	}

	public void setRisk_examinfo_id(long risk_examinfo_id) {
		this.risk_examinfo_id = risk_examinfo_id;
	}

	public String getRisk_item_code() {
		return risk_item_code;
	}

	public void setRisk_item_code(String risk_item_code) {
		this.risk_item_code = risk_item_code;
	}

	public long getPoint() {
		return point;
	}

	public void setPoint(long point) {
		this.point = point;
	}
}
