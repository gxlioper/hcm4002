package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.CrmVisitRecordDTO;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.model.CrmVisitRecordModel;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.WebUserInfo;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmVisitRecordServiceImpl implements CrmVisitRecordService{
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
	public PageReturnDTO getCrmVisitRecordList(CrmVisitRecordModel model, int page, int pageSize)
			throws ServiceException, SQLException {
		String sql="select c.id"
				+ ",c.arch_num"
				+ ",c.customer_feedback"
				+ ",c.health_suggest"
				+ ",c.visit_type"
				+ ",c.visit_doctor_id"
				+ ", convert(varchar(10), c.visit_date, 20) as visit_date "
				+ ",c.visit_num "
				+ ",i.user_name as name "
				+ ",u.chi_name as doctorname ,uu.chi_name as actual_doctor_name ,convert(varchar(20), c.actual_date, 20)  as actual_date "
				+ " from  user_usr  uu , crm_visit_record c "
				+ " left join customer_info i on c.arch_num=i.arch_num "
				+ " left join user_usr u on c.visit_doctor_id=u.id "
				+ " where  uu.id = c.actual_doctor_id ";
		if("1".equals(model.getType())){//跟踪只查询自己的
			sql += " and c.visit_doctor_id='"+model.getVisit_doctor_id()+"' and c.customer_feedback != ''  and c.customer_feedback is not null ";
			
		}
//				+ " where c.visit_doctor_id='"+model.getVisit_doctor_id()+"'";只能查询自己的回访结果内容
		if(model.getVisit_num()!=null&&model.getVisit_num().length()>0){
			sql+=" and c.visit_num='"+model.getVisit_num()+"'";
		}
		if(model.getStartTime() != null && model.getStartTime().length() != 0){
			sql+=" and c.visit_date>='"+model.getStartTime()+" 00:00:00.000' ";
		}
		if(model.getEndTime() != null && model.getEndTime().length() != 0){
			sql+=" and c.visit_date<'"+model.getEndTime()+" 23:59:59.999' ";
		}
		if(model.getVisit_date()!=null&&model.getVisit_date().length()>0){
			sql+=" and c.visit_date='"+model.getVisit_date()+" 23:59:59'";
		}
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmVisitRecordDTO.class);
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
	public CrmVisitRecord getCrmVisitRecord(String id) throws ServiceException {
		String sql="From CrmVisitRecord c where c.id='"+id+"'";
		List c=queryManager.find(sql);
		if(c.size()>0){
			return (CrmVisitRecord) c.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String deleteCrmVisitRecord(String id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="delete crm_visit_record where id in("+id+")";
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
	public void addCrmVisitRecord(CrmVisitRecord crmVisitRecord) throws ServiceException {
		this.persistenceManager.save(crmVisitRecord);
		
	}

	@Override
	public void updateCrmVisitRecord(CrmVisitRecord crmVisitRecord) throws ServiceException {
		this.persistenceManager.update(crmVisitRecord);
		
	}

	@Override
	public String updateCrimVisitPlanStatus(String id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update crm_visit_plan  set visit_status='2' where id='"+id+"'";
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
	public List<CrmVisitRecord> getCrmVisitRecordListByVisitNum(String visit_num) throws ServiceException {
		String sql = "select  * from  crm_visit_record  where visit_num = '"+visit_num+"'";
		List<CrmVisitRecord> list = this.jdbcQueryManager.getList(sql, CrmVisitRecord.class);
		return list;
	}


}
