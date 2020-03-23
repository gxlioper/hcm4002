package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.ExamPrintTmpDTO;

public class ExamPrintTmpModel {

	private String examPrintTmpLists;
	private List<ExamPrintTmpDTO> examPrintTmpList;
	
	public String getExamPrintTmpLists() {
		return examPrintTmpLists;
	}
	public void setExamPrintTmpLists(String examPrintTmpLists) {
		this.examPrintTmpLists = examPrintTmpLists;
	}
	public List<ExamPrintTmpDTO> getExamPrintTmpList() {
		return examPrintTmpList;
	}
	public void setExamPrintTmpList(List<ExamPrintTmpDTO> examPrintTmpList) {
		this.examPrintTmpList = examPrintTmpList;
	}
}
