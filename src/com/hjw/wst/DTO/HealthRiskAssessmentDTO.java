package com.hjw.wst.DTO;

public class HealthRiskAssessmentDTO {

	private long id;
	private String disease_name;
	private String disease_type;
	private String disease_type_name;
	private String bar_graph_titel;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDisease_name() {
		return disease_name;
	}
	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	public String getDisease_type() {
		return disease_type;
	}
	public void setDisease_type(String disease_type) {
		this.disease_type = disease_type;
	}
	public String getDisease_type_name() {
		return disease_type_name;
	}
	public void setDisease_type_name(String disease_type_name) {
		this.disease_type_name = disease_type_name;
	}
	public String getBar_graph_titel() {
		return bar_graph_titel;
	}
	public void setBar_graph_titel(String bar_graph_titel) {
		this.bar_graph_titel = bar_graph_titel;
	}
}
