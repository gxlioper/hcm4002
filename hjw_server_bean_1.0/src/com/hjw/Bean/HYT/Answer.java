package com.hjw.Bean.HYT;

import java.util.ArrayList;
import java.util.List;

public class Answer {

	private String id = "";
	private String createTime = "";//创建时间
	private int type;//	0:单选类型,1:多选类型,2:文本类型,3:数字类型
	private int required;	//0:必填，1:非必填
	private String question	="";//类型名称
	private String value = "";//文本类型和数字类型的值
	private String category = "";
	private String institutionCode = "";
	private int sn;	//
	private int col;	//
	private List<Option> options = new ArrayList<>();//单选和多选的选项
	
	public String getId() {
		return id;
	}
	public String getCreateTime() {
		return createTime;
	}
	public int getType() {
		return type;
	}
	public int getRequired() {
		return required;
	}
	public String getQuestion() {
		return question;
	}
	public String getValue() {
		return value;
	}
	public String getCategory() {
		return category;
	}
	public String getInstitutionCode() {
		return institutionCode;
	}
	public int getSn() {
		return sn;
	}
	public int getCol() {
		return col;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setType(int type) {
		this.type = type;
	}
	public void setRequired(int required) {
		this.required = required;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setInstitutionCode(String institutionCode) {
		this.institutionCode = institutionCode;
	}
	public void setSn(int sn) {
		this.sn = sn;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public List<Option> getOptions() {
		return options;
	}
	public void setOptions(List<Option> options) {
		this.options = options;
	}
}
