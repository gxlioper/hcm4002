package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.interfaces.util.StringUtil;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;

public class WebserviceConfigurationServiceImpl implements WebserviceConfigurationService{

	private JdbcQueryManager jdbcQueryManager;
	
	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}

	/**
	 * 根据config_key查询到多条时的获取顺序：
	 * 1，webservice_configuration表中center_num和入参中center_num匹配的
	 * 2，webservice_configuration表中center_num为000的
	 * 3，webservice_configuration表中center_num为空串或null的
	 */
	@SuppressWarnings("unchecked")
	@Override
	public WebserviceConfigurationDTO getWebServiceConfig(String configKey, String center_num) throws ServiceException {
		String sql ="select * from webservice_configuration where config_key = '"+configKey+"' order by center_num desc ";
		List<WebserviceConfigurationDTO> list = this.jdbcQueryManager.getList(sql, WebserviceConfigurationDTO.class);
		if(list == null || list.size() == 0) {
			return null;
		}
		if(center_num == null){
			return list.get(0);
		}
		for(WebserviceConfigurationDTO wcd : list) {
			if(center_num.equals(wcd.getCenter_num())) {
				return wcd;
			} else if("000".equals(wcd.getCenter_num())) {
				return wcd;
			} else if(StringUtil.isEmpty(wcd.getCenter_num())) {
				return wcd;
			}
		}
		return null;
	}

}
