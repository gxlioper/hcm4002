package com.hjw.wst.DTO;

public class HealthRiskMorbidityDTO {

	private long id;
	private long health_risk_id;
	private long points;
	private String sex;
	private long morbidity;
	private String condition;
	private Double average_morbidity;
	private Double hard_morbidity;
	private Double low_morbidity;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public long getMorbidity() {
		return morbidity;
	}
	public void setMorbidity(long morbidity) {
		this.morbidity = morbidity;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
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
}
