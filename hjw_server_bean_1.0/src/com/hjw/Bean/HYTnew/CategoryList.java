package com.hjw.Bean.HYTnew;

import java.util.List;

public class CategoryList {

	private int id;
	private String code = "";
	private String name = "";
	private List<QuestionnaireList> questionnaireList;
	
	public int getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public List<QuestionnaireList> getQuestionnaireList() {
		return questionnaireList;
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
	public void setQuestionnaireList(List<QuestionnaireList> questionnaireList) {
		this.questionnaireList = questionnaireList;
	}
}
