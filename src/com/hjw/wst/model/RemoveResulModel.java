package com.hjw.wst.model;

import java.util.List;

import com.hjw.wst.DTO.ExaminfoChargingItemDTO;

public class RemoveResulModel implements java.io.Serializable{
	private static final long serialVersionUID = -97502163798576023L;
	
	private String exam_num;
	private long id;
	private String itemLists;
	private List<ExaminfoChargingItemDTO> itemList;
	
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public String getItemLists() {
		return itemLists;
	}
	public void setItemLists(String itemLists) {
		this.itemLists = itemLists;
	}
	public List<ExaminfoChargingItemDTO> getItemList() {
		return itemList;
	}
	public void setItemList(List<ExaminfoChargingItemDTO> itemList) {
		this.itemList = itemList;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
