package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.crm.DTO.HuoVsHopDTO;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.HuoVsHop;
import com.hjw.crm.model.HuoVsHopModel;
import com.hjw.crm.service.HuoVsHopService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class HuoVsHopServiceImpl implements HuoVsHopService{
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
	public PageReturnDTO getHuoVsHopList(HuoVsHopModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql=" select hh.id,hh.data_type,hh.hos_data_code,hh.hos_data_name,hh.huo_data_code,hh.huo_data_name,hh.hos_piny,"
				+ "u.chi_name as creaters,convert(varchar(50),hh.create_time,23) as create_time,u1.chi_name as updaters,convert(varchar(50),hh.update_time,23) as update_time from huo_vs_hop hh"
				+ " left join user_usr u on hh.creater=u.id "
				+ " left join user_usr u1 on hh.updater=u1.id";
		List<String> strlist=new ArrayList<String>();
		if(model.getHuo_data_code()!=null&&model.getHuo_data_code().length()>0){
			strlist.add(" hh.huo_data_code='"+model.getHuo_data_code()+"'");
		}
		if(model.getHuo_data_name()!=null&&model.getHuo_data_name().length()>0){
			strlist.add(" hh.huo_data_name like '%"+model.getHuo_data_name()+"%'");
		}
		if(model.getHos_data_code()!=null&&model.getHos_data_code().length()>0){
			strlist.add(" hh.hos_data_code='"+model.getHos_data_code()+"'");
		}
		if(model.getHos_data_name()!=null&&model.getHos_data_name().length()>0){
			strlist.add(" hh.hos_data_name like'%"+model.getHos_data_name()+"%'");
		}
		if(model.getData_type()!=null&&model.getData_type().length()>0){
			strlist.add(" hh.data_type='"+model.getData_type()+"'");
		}
		if(strlist!=null&&strlist.size()>0){
			sql+=" where ";
			if(strlist.size()>1){
				sql+=strlist.get(0);
				for(int i =1;i<=strlist.size()-1;i++){
					sql+=" and "+strlist.get(i);
				}
			}else{
				sql+=strlist.get(0);
			}
		}
		
		sql+=" order by hh.create_time desc";
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,HuoVsHopDTO.class);
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
	public PageReturnDTO getHuoLisProject(HuoVsHopModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select distinct ei.item_num as huo_data_code,ei.item_name as huo_data_name,ei.item_pinyin as item_pinyin,ei.create_time  "
				+ "from examination_item ei "
				+ "left join charging_item_exam_item ciei on ei.item_num=ciei.item_code "
				+ "left join charging_item ci on ci.item_code=ciei.charging_item_code "
				+ "where  ei.is_Active='Y' and  ci.isActive='Y' and ci.dep_category='131'";
		if(model.getHos_data_code()!=null&&model.getHos_data_code().length()>0){
			sql+=" and ei.item_num='"+model.getHos_data_code()+"'";
		}
		if(model.getHos_data_name()!=null&&model.getHos_data_name().length()>0){
			sql+=" and ei.item_name like'%"+model.getHos_data_name()+"%'";
		}
		if(model.getItem_pinyin()!=null&&model.getItem_pinyin().length()>0){
			sql+=" and ei.item_pinyin like '%"+model.getItem_pinyin()+"%'";
		}
		sql+=" order by ei.create_time desc";
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,HuoVsHopDTO.class);
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
	public PageReturnDTO getHuoPacsProject(HuoVsHopModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select ei.item_code as huo_data_code ,ei.item_name as huo_data_name,ei.item_pinyin as item_pinyin from charging_item ei where ei.isActive='Y' and dep_category='21'";
		if(model.getHos_data_code()!=null&&model.getHos_data_code().length()>0){
			sql+=" and ei.item_num='"+model.getHos_data_code()+"'";
		}
		if(model.getHos_data_name()!=null&&model.getHos_data_name().length()>0){
			sql+=" and ei.item_name like'%"+model.getHos_data_name()+"%'";
		}
		if(model.getItem_pinyin()!=null&&model.getItem_pinyin().length()>0){
			sql+=" and ei.item_pinyin like '%"+model.getItem_pinyin()+"%'";
		}
		sql+=" order by ei.create_time desc";
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,HuoVsHopDTO.class);
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
	public PageReturnDTO getHuoCommonProject(HuoVsHopModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select distinct ei.item_num as huo_data_code,ei.item_name as huo_data_name,ei.item_pinyin as item_pinyin,ei.create_time  "
				+ "from examination_item ei "
				+ "left join charging_item_exam_item ciei on ei.item_num=ciei.item_code "
				+ "left join charging_item ci on ci.item_code=ciei.charging_item_code "
				+ "where  ei.is_Active='Y' and  ci.isActive='Y' and ci.dep_category='17'";
		if(model.getHos_data_code()!=null&&model.getHos_data_code().length()>0){
			sql+=" and ei.item_num='"+model.getHos_data_code()+"'";
		}
		if(model.getHos_data_name()!=null&&model.getHos_data_name().length()>0){
			sql+=" and ei.item_name like'%"+model.getHos_data_name()+"%'";
		}
		if(model.getItem_pinyin()!=null&&model.getItem_pinyin().length()>0){
			sql+=" and ei.item_pinyin like '%"+model.getItem_pinyin()+"%'";
		}
		sql+=" order by ei.create_time desc";
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,HuoVsHopDTO.class);
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
	public HuoVsHop getHuoVsHop(String id) throws ServiceException, SQLException {
		String sql="From HuoVsHop c where c.id='"+id+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (HuoVsHop) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String saveHuoVsHop(HuoVsHop data) throws ServiceException, SQLException {
		try {
			this.persistenceManager.save(data);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String updateHuoVsHop(HuoVsHop data) throws ServiceException, SQLException {
		try {
			this.persistenceManager.update(data);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String deleteHuoVsHop(String id) throws ServiceException, SQLException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="delete huo_vs_hop where id in('"+id+"')";
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


}
