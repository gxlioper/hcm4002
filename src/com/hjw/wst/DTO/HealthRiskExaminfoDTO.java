package com.hjw.wst.DTO;

import java.util.List;

public class HealthRiskExaminfoDTO {

	private long id;
	private String exam_num;
	private long health_risk_id;
	private String disease_name;
	private String disease_type_name;
	private String bar_graph_titel;
	private String disease_type;
	private long points;
	private String picture_path;
	private long is_success;
	private String is_successs;
	private String cause_failure;
	private String creater;
	private String create_time;
	private Long reality_morbidity;
	private Double average_morbidity;
	private Double hard_morbidity;
	private Double low_morbidity;
	private String reality_morbiditys = "/";
	private String average_morbiditys = "/";
	private String hard_morbiditys = "/";
	private String low_morbiditys = "/";
	private String pointss;
	private List<HealthRiskExaminfoItemDTO> itemList;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
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
		if(points == -1000){
			this.pointss = "/";
		}else{
			this.pointss = points + "";
		}
	}
	public String getPicture_path() {
		return picture_path;
	}
	public void setPicture_path(String picture_path) {
		this.picture_path = picture_path;
	}
	public long getIs_success() {
		return is_success;
	}
	public void setIs_success(long is_success) {
		this.is_success = is_success;
		if(is_success == 0){
			this.is_successs = "评估成功";
		}else{
			this.is_successs = "评估失败";
		}
	}
	public String getIs_successs() {
		return is_successs;
	}
	public void setIs_successs(String is_successs) {
		this.is_successs = is_successs;
	}
	public String getCause_failure() {
		return cause_failure;
	}
	public void setCause_failure(String cause_failure) {
		this.cause_failure = cause_failure;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public List<HealthRiskExaminfoItemDTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<HealthRiskExaminfoItemDTO> itemList) {
		this.itemList = itemList;
	}
	public Long getReality_morbidity() {
		return reality_morbidity;
	}
	public void setReality_morbidity(Long reality_morbidity) {
		this.reality_morbidity = reality_morbidity;
		if(this.reality_morbidity == null){
			this.reality_morbiditys = "/";
		}else{
			this.reality_morbiditys = reality_morbidity + "%";
		}
	}
	public Double getAverage_morbidity() {
		return average_morbidity;
	}
	public void setAverage_morbidity(Double average_morbidity) {
		this.average_morbidity = average_morbidity;
		if(this.average_morbidity == null){
			this.average_morbiditys = "/";
		}else{
			this.average_morbiditys = average_morbidity + "%";
		}
	}
	public Double getHard_morbidity() {
		return hard_morbidity;
	}
	public void setHard_morbidity(Double hard_morbidity) {
		this.hard_morbidity = hard_morbidity;
		if(this.hard_morbidity == null){
			this.hard_morbiditys = "/";
		}else{
			this.hard_morbiditys = hard_morbidity + "%";
		}
	}
	public Double getLow_morbidity() {
		return low_morbidity;
	}
	public void setLow_morbidity(Double low_morbidity) {
		this.low_morbidity = low_morbidity;
		if(this.low_morbidity == null){
			this.low_morbiditys = "/";
		}else{
			this.low_morbiditys = low_morbidity + "%";
		}
	}
	public String getBar_graph_titel() {
		return bar_graph_titel;
	}
	public void setBar_graph_titel(String bar_graph_titel) {
		this.bar_graph_titel = bar_graph_titel;
	}
	public String getDisease_type() {
		return disease_type;
	}
	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
	}
	public String getDisease_name() {
		return disease_name;
	}
	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	public String getDisease_type_name() {
		return disease_type_name;
	}
	public void setDisease_type_name(String disease_type_name) {
		this.disease_type_name = disease_type_name;
	}
	public String getReality_morbiditys() {
		return reality_morbiditys;
	}
	public void setReality_morbiditys(String reality_morbiditys) {
		this.reality_morbiditys = reality_morbiditys;
	}
	public String getAverage_morbiditys() {
		return average_morbiditys;
	}
	public void setAverage_morbiditys(String average_morbiditys) {
		this.average_morbiditys = average_morbiditys;
	}
	public String getHard_morbiditys() {
		return hard_morbiditys;
	}
	public void setHard_morbiditys(String hard_morbiditys) {
		this.hard_morbiditys = hard_morbiditys;
	}
	public String getLow_morbiditys() {
		return low_morbiditys;
	}
	public void setLow_morbiditys(String low_morbiditys) {
		this.low_morbiditys = low_morbiditys;
	}
	public String getPointss() {
		return pointss;
	}
	public void setPointss(String pointss) {
		this.pointss = pointss;
	}
}
