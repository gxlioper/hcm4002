package com.hjw.wst.service;

import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.synjones.framework.exception.ServiceException;

public interface WebserviceConfigurationService {

	/**
	 * 查询webservice地址
	     * @Title: getWebServiceConfig   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param configKey
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebserviceConfigurationDTO      
	     * @throws
	 */
	public WebserviceConfigurationDTO getWebServiceConfig(String configKey, String center_num) throws ServiceException;
}
