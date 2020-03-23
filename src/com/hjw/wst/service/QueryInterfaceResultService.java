package com.hjw.wst.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleDemo;
import com.hjw.wst.model.QueryInterfaceResultModel;
import com.synjones.framework.exception.ServiceException;

public interface QueryInterfaceResultService {
	public PageReturnDTO getInterResultList(QueryInterfaceResultModel model, String center_num,int page,int rows) throws ServiceException,SQLException;
	public String againApply(QueryInterfaceResultModel model,UserDTO user) throws ServiceException,SQLException;
	public String againAll(QueryInterfaceResultModel model,UserDTO user) throws ServiceException,SQLException;
	public List<SampleDemoDTO> getSampleTypeList();
	public PageReturnDTO getSampleResultList(QueryInterfaceResultModel model, String center_num,int page,int rows) throws ServiceException,SQLException;
	/**
	 * 
	     * @Title: getSampleResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param rows
	     * @param: @return查询采样页面数量统计
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public String getSampleResultListShuliang(QueryInterfaceResultModel model, String center_num,int page,int rows) throws ServiceException,SQLException;
	public String queryCenter(String userid) throws ServiceException,SQLException;
	
	public String againGuahao(String exam_num,UserDTO user);
}
