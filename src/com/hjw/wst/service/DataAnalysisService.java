package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.DataAnalysisModel;
import com.synjones.framework.exception.ServiceException;

public interface DataAnalysisService {

	public PageReturnDTO getResearchDataList(DataAnalysisModel model,UserDTO user , int pagesize, int pageno,String sort,String order) throws ServiceException;
	
	public List<ChargingItemDTO> getChargingItemListByq(String q,long add_i) throws ServiceException;
	
	public List<ExaminationItemDTO> getExaminationItemListByq(long charging_id,String q,long add_i) throws ServiceException;
}
