package com.hjw.wst.DTO;

import java.util.List;

import com.hjw.wst.domain.ScaleDictQuestion;

public class QuestionOptionDTO implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private ScaleDictQuestion question;
	
	private List<ScaleDictOptionDTO> options;

	public ScaleDictQuestion getQuestion() {
		return question;
	}

	public void setQuestion(ScaleDictQuestion question) {
		this.question = question;
	}

	public List<ScaleDictOptionDTO> getOptions() {
		return options;
	}

	public void setOptions(List<ScaleDictOptionDTO> options) {
		this.options = options;
	}
}