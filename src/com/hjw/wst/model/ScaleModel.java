package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.QuestionOptionDTO;
import com.hjw.wst.domain.ScaleDictAppraise;

public class ScaleModel implements java.io.Serializable {
	private static final long serialVersionUID = -97502163798576023L;

	private String scale_code;
	
	private String quest_code;
	
	private String nameCn;
	
	private String nameEn;
	
	private int full_score;
	
	private String content;
	
	private List<QuestionOptionDTO> questionOptionList;
	
	private List<ScaleDictAppraise> appraiseList;

	private String exam_num;
	
	private int score;
	
	private String creater;
	
	private String scale_result_list;
	
	private boolean finish;
	
	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public List<QuestionOptionDTO> getQuestionOptionList() {
		return questionOptionList;
	}

	public void setQuestionOptionList(List<QuestionOptionDTO> questionOptionList) {
		this.questionOptionList = questionOptionList;
	}

	public String getScale_code() {
		return scale_code;
	}

	public void setScale_code(String scale_code) {
		this.scale_code = scale_code;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<ScaleDictAppraise> getAppraiseList() {
		return appraiseList;
	}

	public void setAppraiseList(List<ScaleDictAppraise> appraiseList) {
		this.appraiseList = appraiseList;
	}

	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getScale_result_list() {
		return scale_result_list;
	}

	public void setScale_result_list(String scale_result_list) {
		this.scale_result_list = scale_result_list;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public String getQuest_code() {
		return quest_code;
	}

	public void setQuest_code(String quest_code) {
		this.quest_code = quest_code;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public int getFull_score() {
		return full_score;
	}

	public void setFull_score(int full_score) {
		this.full_score = full_score;
	}
}
