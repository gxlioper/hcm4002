package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.domain.ChargingItemExamItem;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.service.ChargingItemExamItemService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class ChargingItemExamItemImpl implements ChargingItemExamItemService {
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	
	@Override
	public ChargingItemExamItem getItemId(long id) throws ServiceException {
		List ls = qm.find("From ChargingItemExamItem  Where  exam_item_id ='" +id+ "'");
		if (ls.size() > 0)
			return (ChargingItemExamItem) ls.get(0);
		else
			return null;
	}
}
