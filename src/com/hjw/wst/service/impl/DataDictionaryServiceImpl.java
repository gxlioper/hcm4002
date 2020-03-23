package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.Examinatioin_centerDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.service.DataDictionaryService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class DataDictionaryServiceImpl implements DataDictionaryService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	
	public QueryManager getQueryManager() {
		return qm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}


	
	@Override
	public DataDictionary addDaDt(DataDictionary ddt) throws ServiceException {
		this.pm.save(ddt);
		return ddt;
	}

	@Override
	public void deleteDaDt(DataDictionary ddt) throws ServiceException {
		this.pm.remove(ddt);
	}

	@Override
	public DataDictionary updateDaDt(DataDictionary ddt) throws ServiceException {
		this.pm.update(ddt);
		return ddt;
	}

	@Override
	public DataDictionary loadDaDt(long id) throws ServiceException {
		return (DataDictionary)this.qm.get(DataDictionary.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataDictionary queryByNum(String data_code) throws ServiceException {
		String sql = "from DataDictionary where data_code = '"+data_code.trim()+"'";
		List<DataDictionary> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageReturnDTO queryPageDaDt(String data_code, String data_name, String data_type, int pageno, int pagesize) {
		String sql="select d.id,data_type, data_code,data_name,d.remark,d.isActive, u.chi_name as creater ,"
				+" d.create_time,u1.chi_name as updater, d.update_time,d.seq_code,d.data_code_children,d.data_class "
				+" from data_dictionary d left join user_usr u on d.creater = u.id left join user_usr u1 on d.updater = u1.id  "
				+" where  1=1 ";
		if(!StringUtil.isEmpty(data_code)){
			sql=sql+" and data_code = '"+data_code+"'";
		}
		
		if(!StringUtil.isEmpty(data_name)){
			sql=sql+" AND data_name LIKE '%"+data_name+"%' ";
		}		 
		
		if(!"".equals(data_type)){
			sql += " and data_type='"+data_type+"'";
		}
		sql += " order by data_type, seq_code";
		PageSupport map = this.jqm.getList(sql,pagesize, pageno, DataDictionaryDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DataDictionaryDTO> queryallDaDt( ) throws ServiceException {
		String sql = "select distinct data_code, data_type "
				+" from data_dictionary dd"
				+" where 1=1 order by data_type";
		List<DataDictionaryDTO> list = jqm.getList(sql, DataDictionaryDTO.class);		
		return list;
	}

	@Override
	public DataDictionary getDataCode(String data_type) throws ServiceException {
		String hql="from DataDictionary where data_type='"+data_type+"'";
		List<DataDictionary> list=this.qm.find(hql);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public DataDictionary queryByData_name(String data_code, String data_type, String data_name, long id) {
		String hql="from DataDictionary where data_code = '"+data_code+"' and data_type='"+data_type+"' and data_name = '"+data_name+"' and id !=" + id;
		List<DataDictionary> list=this.qm.find(hql);
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageReturnDTO centerConfigList(String center_type,String common, String center_num, int pageno, int pagesize) throws ServiceException {
		String sql="select cc.center_num,cc.center_name,cc.config_key,cc.config_value,cc.is_active,cc.common,cc.center_type,dd.data_name "
				+ "from center_configuration cc left join  data_dictionary dd on cc.center_type=dd.data_code_children and dd.data_code='XTPZLX' "
				+ "where 1=1 ";
		if(!StringUtil.isEmpty(center_type)){
			sql += " and cc.center_type='"+center_type+"'";
		}
		if(!StringUtil.isEmpty(center_num)){
			sql += " and cc.center_num='"+center_num+"'";
		}
		if(!"".equals(common) && common != null){
			sql += " and (cc.common like '%"+common+"%' or (cc.config_key='"+common+"')) ";
		}
		
		sql =sql + " order by cc.config_key,cc.center_type";
		
		PageSupport map = this.jqm.getList(sql,pagesize, pageno, CenterConfigurationDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public boolean savacenterconfig(String center_name, String config_key, String config_value, String isActive,
			String common, String center_type, String center_num) throws ServiceException {
			boolean message=false;
			try {
				Connection connection = this.jqm.getConnection();
				String sql="insert into center_configuration ([center_name],[config_key],[config_value],[is_active],[common],[center_type],[center_num]) "
						+ " values('"+center_name+"','"+config_key+"','"+config_value+"','"+isActive+"','"+common+"','"+center_type+"','"+center_num+"')";
				int UpdateCenter = connection.createStatement().executeUpdate(sql);
				if(UpdateCenter>0){
					message=true;
				}else{
					message=false;
				}
				
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}
			
			return message;
	}

	@Override
	public boolean updateCenterConfigEdit(String center_name, String config_key, String config_value, String isActive,
			String common, String center_type, String center_num) throws ServiceException {
			boolean message=false;
			try {
				Connection connection = this.jqm.getConnection();
				String sql="update center_configuration set config_value='"+config_value+"',is_active='"+isActive+"',center_type='"+center_type+"' where config_key='"+config_key+"' and center_num = '"+center_num+"'";

				int UpdateCenter = connection.createStatement().executeUpdate(sql);
				if(UpdateCenter>0){
					message=true;
				}else{
					message=false;
				}
				
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				
			}
			
			return message;
	}
	
}
