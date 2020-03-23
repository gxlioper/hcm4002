package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;

public class GroupItemSetDTO {
	private List<GroupChargingItemDTO> listitem  = new ArrayList<GroupChargingItemDTO>();
	private List<ExamSetDTO> listset= new ArrayList<ExamSetDTO>();
	private String message="";	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<GroupChargingItemDTO> getListitem() {
		return listitem;
	}
	public void setListitem(List<GroupChargingItemDTO> listitem) {
		this.listitem = listitem;
	}
	public List<ExamSetDTO> getListset() {
		return listset;
	}
	public void setListset(List<ExamSetDTO> listset) {
		this.listset = listset;
	}
	
	
}
