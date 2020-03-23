package com.hjw.util;

import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.util   
     * @Description:  
     * @author: zr     
     * @date:   2019年7月12日 下午2:25:02   
     * @version V1.0
 */
public  class BaseServiceImpl {
	
	public QueryManager qm;
	public JdbcQueryManager jqm;
	public PersistenceManager pm;
	public JdbcPersistenceManager jpm;
	
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	
	

}
