package com.hjw.charge.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hjw.charge.DTO.PosTransListDTO;
import com.hjw.charge.service.PosTransListChargeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class PosTransListChargeServiceImpl implements PosTransListChargeService{  
	private static final Integer ITEM_ID = 347;// 影像科室固定检查细项ID
	private QueryManager qm;
	private JdbcQueryManager jqm;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<PosTransListDTO> getPeisTradeCode(String peis_trade_code) throws ServiceException {
		List<PosTransListDTO> posTransList = new ArrayList<PosTransListDTO>();
		String posTransSql="select trade_no,voucher_no,original_trade_date from pos_trans_list where peis_trade_code='"+peis_trade_code+"'";
		posTransList = this.jqm.getList(posTransSql, PosTransListDTO.class);
		return posTransList;
	}

	
}
