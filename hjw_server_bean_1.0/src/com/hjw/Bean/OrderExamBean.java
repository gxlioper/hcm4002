package com.hjw.Bean;

import java.util.ArrayList;
import java.util.List;

public class OrderExamBean {
	private String exam_num;//订单编号	
	private List<ListitemBean> listitem= new ArrayList<ListitemBean>();
	private List<ListsetBean> listset= new ArrayList<ListsetBean>();
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public List<ListitemBean> getListitem() {
		return listitem;
	}
	public void setListitem(List<ListitemBean> listitem) {
		this.listitem = listitem;
	}
	public List<ListsetBean> getListset() {
		return listset;
	}
	public void setListset(List<ListsetBean> listset) {
		this.listset = listset;
	}	
	
	
}
