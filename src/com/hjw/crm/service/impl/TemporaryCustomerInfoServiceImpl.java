package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.TemporaryCustomerInfoDTO;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.TemporaryCustomerInfo;
import com.hjw.crm.model.TemporaryCustomerInfoModel;
import com.hjw.crm.service.TemporaryCustomerInfoService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.domain.ImpCustomerInfo;

import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class TemporaryCustomerInfoServiceImpl implements TemporaryCustomerInfoService{
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
	public PageReturnDTO getTemporaryCustomerInfoList(TemporaryCustomerInfoModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql=" select s.id "
				+ " ,s.user_name "
				+ " ,s.id_num "
				+ " ,s.sex "
				+ " ,s.birthday "
				+ " ,s.nation "
				+ " ,d1.data_name as nation_name "
				+ " ,s.phone "
				+ " ,s.address "
				+ " ,s.email "
				+ " ,s.flag "
				+ " ,s.level "
				+ " ,d2.data_name as level_name "
				+ " ,s.integral"
				+ " ,s.notices "
				+ " from temporary_customer_info s "
				+ " left join data_dictionary d1 on d1.id=s.nation "
				+ " left join data_dictionary d2 on d2.id=s.level ";
		List<String> strlist=new ArrayList<>();
		if(model.getUser_name()!=null&&model.getUser_name().length()!=0){
			strlist.add(" s.user_name='"+model.getUser_name()+"' ");
		}
		for(int i=0;i<strlist.size();i++){
			if(i==0){
				sql+=" where "+strlist.get(0);
			}else{
				sql+=" and "+strlist.get(i);
			}
		}
		System.out.println(sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,TemporaryCustomerInfoDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(rows);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public String deleteTemporaryCustomerInfo(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="delete temporary_customer_info where id in("+ids+")";
			System.out.println(sql);
			int rs = connection.createStatement().executeUpdate(sql);
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}

	@Override
	public TemporaryCustomerInfo getTemporaryCustomerInfo(String id) throws ServiceException {
		String sql="From TemporaryCustomerInfo c where c.id='"+id+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (TemporaryCustomerInfo) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void updateTemporaryCustomerInfo(TemporaryCustomerInfo temporaryCustomerInfo) throws ServiceException {
		persistenceManager.update(temporaryCustomerInfo);
	}

	@Override
	public void saveTemporaryCustomerInfo(TemporaryCustomerInfo temporaryCustomerInfo) throws ServiceException {
		persistenceManager.save(temporaryCustomerInfo);
	}

	@Override
	public void saveTemporaryCustomerInfoForList(List<TemporaryCustomerInfo> list) throws ServiceException {
		for (TemporaryCustomerInfo ii : list) {
			persistenceManager.save(ii);
		}
		
	}

	@Override
	public String getDataId(String data_name) throws ServiceException {
		String sql="select d.id from data_dictionary d where d.data_name='"+data_name+"'";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1);
				list.add(str);
				flag="1";
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
		if(flag=="1"){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String saveCustomerInfo(CustomerInfo customerInfo) throws ServiceException {
		try {
			persistenceManager.save(customerInfo);
			return "ok";
		} catch (Exception e) {
			return "error";
		}
	}

	@Override
	public String saveCustomerMemberInfo(CustomerMemberInfo customerMemberInfo) throws ServiceException {
		try {
			persistenceManager.save(customerMemberInfo);
			return "ok";
		} catch (Exception e) {
			return "error";
		}
	}

	@Override
	public String updateCustomerInfo(CustomerInfo customerInfo) throws ServiceException {
		try {
			persistenceManager.update(customerInfo);
			return "ok";
		} catch (Exception e) {
			return "error";
		}
	}

	@Override
	public CustomerInfo getCustomerInfoTemporary(String id_num) throws ServiceException {
		String sql="from CustomerInfo c where c.id_num='"+id_num+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (CustomerInfo) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<TemporaryCustomerInfo> getTemporaryCustomerInfoList() throws ServiceException {
		String sql="from TemporaryCustomerInfo ";
		List<TemporaryCustomerInfo> list=queryManager.find(sql);
		return list;
	}

	@Override
	public List<TemporaryCustomerInfo> getTemporaryCustomerInfoByIdBum(String id_num) throws ServiceException {
		String sql="from TemporaryCustomerInfo where id_num='"+id_num+"'";
		List<TemporaryCustomerInfo> list=queryManager.find(sql);
		return list;
	}

	@Override
	public List<CustomerInfo> getCustomerInfoByIdBum(String id_num) throws ServiceException {
		String sql="from CustomerInfo where  id_num='"+id_num+"'";
		List<CustomerInfo> list=queryManager.find(sql);
		return list;
	}

	@Override
	public List<CustomerMemberInfo> getCustomerMemberInfoByIdBum(String arch_num) throws ServiceException {
		String sql="from CustomerMemberInfo  where  arch_num='"+arch_num+"'";
		List<CustomerMemberInfo> list=queryManager.find(sql);
		return list;
	}

	@Override
	public List<TemporaryCustomerInfoDTO> getNation() throws ServiceException {
		String sql=" select d.id as nation,d.data_name as nation_name from DataDictionary d where d.data_code='MZLX'";
		List<TemporaryCustomerInfoDTO> list=queryManager.find(sql);
		return list;
	}

	@Override
	public List<TemporaryCustomerInfoDTO> getLevelName() throws ServiceException {
		String sql=" select d.id as level,d.data_name as level_name from DataDictionary d where d.data_code='HYJB'";
		List<TemporaryCustomerInfoDTO> list=queryManager.find(sql);
		return list;
	}

	@Override
	public String getNationByName(String name) throws ServiceException {
		String sql="select d.id from data_dictionary d where d.data_name='"+name+"'";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1);
				list.add(str);
				flag="1";
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
		if(flag=="1"){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
