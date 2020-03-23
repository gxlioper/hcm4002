package com.hjw.mongo.DTO;

import com.hjw.wst.DTO.PageDTO;

public class DataGruid {
	PageDTO pageDTO=new PageDTO();
    String str="";  
    private boolean searchflag=false;    
	
	public boolean isSearchflag() {
		return searchflag;
	}
	public void setSearchflag(boolean searchflag) {
		this.searchflag = searchflag;
	}
	public PageDTO getPageDTO() {
		return pageDTO;
	}
	public void setPageDTO(PageDTO pageDTO) {
		this.pageDTO = pageDTO;
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	
	
}
