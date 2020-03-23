package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class ExamInfoListBean {
	private List<ExamInfoBean> examlist= new ArrayList<ExamInfoBean>();

	public List<ExamInfoBean> getExamlist() {
		return examlist;
	}

	public void setExamlist(List<ExamInfoBean> examlist) {
		this.examlist = examlist;
	}
}
