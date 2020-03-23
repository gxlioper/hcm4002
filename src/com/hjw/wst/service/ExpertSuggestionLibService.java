package com.hjw.wst.service;

import com.hjw.wst.DTO.ExpertSuggestionLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExpertSuggestionLib;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  专家建议知识库
     * @author: zr   
     * @date:   2016年11月2日 下午2:44:34   
     * @version V2.0.0.0
 */
public interface ExpertSuggestionLibService {
	/**
	 * 
	     * @Title: queryExpertSuggestionLib   
	     * @Description: TODO(获取专家建议知识库列表)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryExpertSuggestionLib(ExpertSuggestionLibDTO li,int page,int pageSize) throws  ServiceException;
	/**
	 * 
	     * @Title: deleteExpertSuggestionLib   
	     * @Description: TODO(删除专家建议)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteExpertSuggestionLib(String ids) throws ServiceException;
	/**
	 * 
	     * @Title: updateExpertSuggestionLib   
	     * @Description: TODO(修改专家建议)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateExpertSuggestionLib(ExpertSuggestionLib exp) throws ServiceException;
	/**
	 * 
	     * @Title: addExpertSuggestionLib   
	     * @Description: TODO(添加专家建议知识库)   
	     * @param: @param exp
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addExpertSuggestionLib(ExpertSuggestionLib exp) throws ServiceException;
	/**
	 * 
	     * @Title: findExpertSuggestionLib   
	     * @Description: TODO(查找专家建议知识库)   
	     * @param: @param id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public  ExpertSuggestionLib findExpertSuggestionLib(long id)throws ServiceException;
}
