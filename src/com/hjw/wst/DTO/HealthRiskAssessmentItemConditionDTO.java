package com.hjw.wst.DTO;

public class HealthRiskAssessmentItemConditionDTO {

	private long id;
	private long health_item_id;
	private long points;
	private Double min_value;
	private Double max_value;
	private String yes_or_not;
	private Double min_value1;
	private Double max_value1;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getHealth_item_id() {
		return health_item_id;
	}
	public void setHealth_item_id(long health_item_id) {
		this.health_item_id = health_item_id;
	}
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
	public Double getMin_value() {
		return min_value;
	}
	public void setMin_value(Double min_value) {
		this.min_value = min_value;
	}
	public Double getMax_value() {
		return max_value;
	}
	public void setMax_value(Double max_value) {
		this.max_value = max_value;
	}
	public String getYes_or_not() {
		return yes_or_not;
	}
	public void setYes_or_not(String yes_or_not) {
		this.yes_or_not = yes_or_not;
	}
	public Double getMin_value1() {
		return min_value1;
	}
	public void setMin_value1(Double min_value1) {
		this.min_value1 = min_value1;
	}
	public Double getMax_value1() {
		return max_value1;
	}
	public void setMax_value1(Double max_value1) {
		this.max_value1 = max_value1;
	}
}
