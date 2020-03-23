package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hjw.crm.DTO.CrmSalesCostDTO;
import com.hjw.crm.DTO.CrmSalesCostTrDTO;
import com.hjw.crm.DTO.CrmSignTrackingDTO;
import com.hjw.crm.domain.CrmSignTracking;
import com.hjw.crm.model.CrmSignTrackingModel;
import com.hjw.crm.service.CrmSignTrackingService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmSignTrackingServiceImpl implements CrmSignTrackingService{
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private PersistenceManager persistenceManager;
	
	
	public QueryManager getQueryManager() {
		return queryManager;
	}


	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}


	public JdbcQueryManager getJdbcQueryManager() {
		return jdbcQueryManager;
	}


	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}


	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jdbcPersistenceManager;
	}


	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}


	public PersistenceManager getPersistenceManager() {
		return persistenceManager;
	}


	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}


	@Override
	public PageReturnDTO getCrmSignTrackingList(CrmSignTrackingModel model,long userid,int page, int pageSize)
			throws ServiceException, SQLException {
		String sql=" select s.sign_num,p.sign_name,p.sign_status,p.track_progress "
				+ " ,s.tracking_date "
				+ " ,s.contact_name "
				+ " ,s.phone "
				+ " ,s.tracking_content "
				+ " ,remark "
				+ " from crm_sign_tracking s,crm_sign_bill_plan p where s.sign_num = p.sign_num and p.creater = "+ userid;
		if(model.getContact_name()!=null && model.getContact_name().length()!=0){
			sql +=" and s.contact_name like '%"+model.getContact_name()+"%'";
		}
		if(model.getStart_date()!=null && model.getStart_date().length()!=0){
			sql +=" and s.tracking_date >= '"+model.getStart_date().trim()+" 00:00:00.000'";
		}
		if(model.getEnd_date()!=null && model.getEnd_date().length()!=0){
			sql +=" and s.tracking_date < '"+model.getEnd_date().trim()+" 23:59:59.999'";
		}
		if(model.getSign_num() != null && model.getSign_num().length() != 0){
			sql +=" and s.sign_num = '"+model.getSign_num()+"'";
		}
		sql+=" order by tracking_date ";
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmSignTrackingDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}


	@Override
	public List<String> getCrmSignTrackingTime() {
		String sql="select distinct CONVERT(varchar(100), s.tracking_date, 23) as  tracking_date from crm_sign_tracking s ";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("tracking_date");
				list.add(str);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}


	@Override
	public void addCrmSignTracking(CrmSignTracking crmSignTracking) {
		persistenceManager.save(crmSignTracking);
		
	}

}
