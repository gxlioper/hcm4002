package com.hjw.mongo.DTO;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.PageDTO;

public class DataMongoDTO {
	List<String> datas = new ArrayList<String>();
	PageDTO pageDTO= new PageDTO();
	public List<String> getDatas() {
		return datas;
	}
	public void setDatas(List<String> datas) {
		this.datas = datas;
	}
	public PageDTO getPageDTO() {
		return pageDTO;
	}
	public void setPageDTO(PageDTO pageDTO) {
		this.pageDTO = pageDTO;
	}
	
}
