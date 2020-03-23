package com.hjw.echartsSearch.DTO;

import java.util.ArrayList;
import java.util.List;

public class SearchTotalAllDTO {

	private List<SearchTotalDTO> slist=new ArrayList<SearchTotalDTO>();
	private List<SearchTotalDTO> slist1=new ArrayList<SearchTotalDTO>();
	public List<SearchTotalDTO> getSlist() {
		return slist;
	}
	public void setSlist(List<SearchTotalDTO> slist) {
		this.slist = slist;
	}
	public List<SearchTotalDTO> getSlist1() {
		return slist1;
	}
	public void setSlist1(List<SearchTotalDTO> slist1) {
		this.slist1 = slist1;
	}
	
	
}
