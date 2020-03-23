package com.hjw.wst.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.WebConfigDTO;
import com.hjw.wst.domain.WebConfig;
import com.hjw.wst.service.WebConfigService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class WebConfigServiceImpl implements WebConfigService 
{
	@SuppressWarnings("unused")
	private JdbcPersistenceManager jdbcPersistenceManager;
	private JdbcQueryManager jdbcQueryManager;
	private PersistenceManager persistenceManager;
	private QueryManager queryManager;
	static final Log log = LogFactory.getLog(WebConfigServiceImpl.class);
	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) 
	{
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}
	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) 
	{
		this.jdbcQueryManager = jdbcQueryManager;
	}
	public void setPersistenceManager(PersistenceManager persistenceManager) 
	{
		this.persistenceManager = persistenceManager;
	}

	public void setQueryManager(QueryManager queryManager) 
	{
		this.queryManager = queryManager;
	}
	public PageReturnDTO findConfigList(int currentPage, int pageSize)
	{
        String  sql = "select code,name,types,remark,memo from web_config";    
	    PageSupport map = this.jdbcQueryManager.getList(sql,currentPage,pageSize, WebConfigDTO.class);
	    PageReturnDTO term = new PageReturnDTO();
	    term.setPage(currentPage);
	    term.setRp(pageSize);
	    term.setTotal(map.getRecTotal());
	    term.setRows(map.getList());
	    return term;
	}
    public WebConfig findWebConfig(String code) throws ServiceException 
    {
    	WebConfig webConfig =(WebConfig)this.queryManager.get(WebConfig.class, code);
        return webConfig;
    }
	public void updateConfig(WebConfig termInfoConfig)
	{
		this.persistenceManager.update(termInfoConfig);
	}
}
