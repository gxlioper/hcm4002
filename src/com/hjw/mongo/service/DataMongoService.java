package com.hjw.mongo.service;

import java.util.List;

import com.hjw.mongo.DTO.DataMongoDTO;
import com.hjw.mongo.DTO.SQLColumDTO;
import com.hjw.mongo.DTO.SearchScientificFactor;
import com.hjw.mongo.model.DataMongoModel;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface DataMongoService {
	
	/**
	 * 获取查询条件
	     * @Title: getResearchDataconditions   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchScientificFactor>      
	     * @throws
	 */
	public List<SearchScientificFactor> getResearchDataconditions(UserDTO user) throws ServiceException;
	
	/**
	 * 获取查询条件
	     * @Title: getResearchDataconditions   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchScientificFactor>      
	     * @throws
	 */
	public SearchScientificFactor getResearchDataconditionsId(long id) throws ServiceException;
	
	public String getResearchDatadel(long id) throws ServiceException;
	
	public String getResearchDatasave(DataMongoModel model,UserDTO user) throws ServiceException;
	
	public DataMongoDTO getResearchDataList(DataMongoModel model,UserDTO user , int pagesize, int pageno,String sort,String order,String searchtype) throws Exception;
		
	public List<ChargingItemDTO> getChargingItemListByq(String q,long add_i) throws ServiceException;
	
	public List<ExaminationItemDTO> getExaminationItemListByq(long charging_id,String q,long add_i) throws ServiceException;
	
	public List<TreeDTO> getCompanyForNameIsactive(String name,UserDTO user)throws ServiceException;
	
	public SQLColumDTO examinfoItemForCode(long itemid);
	
	/**
	 * 获取收费项目编码
	     * @Title: chargingItemForCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param chargingid
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public SQLColumDTO chargingItemForCode(long chargingid);
}
