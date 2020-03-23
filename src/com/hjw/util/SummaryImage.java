package com.hjw.util;

public class SummaryImage {
	public SummaryImage(String name, Double value) {
		super();
		this.name = name;
		this.value = value;
	}
	private String name;
	private Double value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

}
