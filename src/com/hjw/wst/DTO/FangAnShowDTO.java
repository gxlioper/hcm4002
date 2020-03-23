package com.hjw.wst.DTO;

import java.util.ArrayList;
import java.util.List;

public class FangAnShowDTO {
	private List<GroupSetDTO> groupSetList = new ArrayList<GroupSetDTO>();//套餐 中文名称
	
	private List<GroupInfoDTO>	groupItemList= new ArrayList<GroupInfoDTO>();
	
	private List<GroupChargingItemDTO> itemList= new ArrayList<GroupChargingItemDTO>();
	
	private List<BatchProPlanDTO> bppList = new ArrayList<BatchProPlanDTO>();	

	public List<BatchProPlanDTO> getBppList() {
		return bppList;
	}

	public void setBppList(List<BatchProPlanDTO> bppList) {
		this.bppList = bppList;
	}

	public List<GroupSetDTO> getGroupSetList() {
		return groupSetList;
	}

	public void setGroupSetList(List<GroupSetDTO> groupSetList) {
		this.groupSetList = groupSetList;
	}

	public List<GroupInfoDTO> getGroupItemList() {
		return groupItemList;
	}

	public void setGroupItemList(List<GroupInfoDTO> groupItemList) {
		this.groupItemList = groupItemList;
	}

	public List<GroupChargingItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(List<GroupChargingItemDTO> itemList) {
		this.itemList = itemList;
	}		

}
