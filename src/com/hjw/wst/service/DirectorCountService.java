package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.synjones.framework.exception.ServiceException;



public interface DirectorCountService {
	public List<JobDTO> getZongjianyisheng() throws ServiceException ;
	
	public PageReturnDTO getExamInfoList(AcceptanceCheckModel model, String center_num, int rows, int page,String sort,String order) throws ServiceException ;
	
	public List<DepExamResultDTO> getDirectorDiseaseList(long exam_num, String center_num) throws ServiceException ;
}
