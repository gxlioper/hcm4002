package com.hjw.wst.service;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.SensitiveWordsLib;
import com.hjw.wst.model.SensitiveWordsLibModel;
import com.synjones.framework.exception.ServiceException;

public interface SensitiveWordsLibService {

	/**
	 * 分页查询敏感词信息
	     * @Title: getSensitiveWordsLibList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getSensitiveWordsLibList(SensitiveWordsLibModel model, int rows, int page) throws ServiceException;
	
	/**
	 * 通过ID查询敏感词信息
	     * @Title: getSensitiveWordsLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return      
	     * @return: SensitiveWordsLib      
	     * @throws
	 */
	public SensitiveWordsLib getSensitiveWordsLib(String id) throws ServiceException; 
	
	/**
	 * 新增保存敏感词信息
	     * @Title: saveSensitiveWordsLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sensitiveWordsLib
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SensitiveWordsLib      
	     * @throws
	 */
	public SensitiveWordsLib saveSensitiveWordsLib(SensitiveWordsLib sensitiveWordsLib) throws ServiceException; 
	
	/**
	 * 修改敏感词信息
	     * @Title: updateSensitiveWordsLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sensitiveWordsLib
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SensitiveWordsLib      
	     * @throws
	 */
	public SensitiveWordsLib updateSensitiveWordsLib(SensitiveWordsLib sensitiveWordsLib) throws ServiceException; 
	
	/**
	 * 校验性别敏感词
	     * @Title: checkSensitiveWordsSex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String checkSensitiveWordsSex(SensitiveWordsLibModel model) throws ServiceException; 
	
	/**
	 * 校验性别敏感词
	     * @Title: checkSensitiveWordsSex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String checkSensitiveWord(SensitiveWordsLibModel model) throws ServiceException; 
}
