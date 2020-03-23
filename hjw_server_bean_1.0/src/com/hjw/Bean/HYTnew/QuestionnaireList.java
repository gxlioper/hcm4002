package com.hjw.Bean.HYTnew;

public class QuestionnaireList {

	private int id;
	private String code = "";
	private String name = "";
	private String categoryCode = "";
	private String institutionCode = "";
	private String required = "";//0-非必填，1-必填
	private String type = "";//1-单选，2-多选，3-文本，4-数字
	private String valueName = "";
	
	public int getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public String getInstitutionCode() {
		return institutionCode;
	}
	public String getRequired() {
		return required;
	}
	public String getType() {
		return type;
	}
	public String getValueName() {
		return valueName;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}
	public void setRequired(String required) {
		this.required = required;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setValueName(String valueName) {
		this.valueName = valueName;
	}
}
