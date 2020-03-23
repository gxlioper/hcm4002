package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fr.function.TRIM;
import com.hjw.crm.DTO.CrmSalesCostDTO;
import com.hjw.crm.DTO.CrmSalesCostTrDTO;
import com.hjw.crm.domain.CrmSalesCost;
import com.hjw.crm.model.CrmSalesCostModel;
import com.hjw.crm.service.CrmSalesCostService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmSalesCostServiceImpl implements CrmSalesCostService{
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
	public PageReturnDTO getCrmSalesCostList(CrmSalesCostModel model, int page, int pageSize)
			throws ServiceException, SQLException {
		String sql=" select s.id "//id
				+ " ,s.sales_id "//销售员id
				+ " ,u.chi_name as username "//销售员id
				+ " ,s.cost_amount"//金额
				+ " , CONVERT(varchar(100),s.cost_date, 20) as cost_date"//日期 CONVERT(varchar(100), GETDATE(), 23)
				+ " ,s.cost_type"//费用类型
				+ " ,s.cost_type as cost_type_code"//费用类型编码
				+ " ,s.batch_num"//合同编号
				+ " ,b.sign_name as batch"//合同
				+ " ,c.com_name as company_name"//公司名称
				+ " ,d.data_name as payment_type"//报销款项类型
				+ " ,s.remark "//备注
				+ " from crm_sales_cost s "
				+ " left join user_usr u on s.sales_id=u.id "
				+ " left join crm_sign_bill_plan b on s.batch_num=b.sign_num"
				+ " left join company_info c on b.company_id=c.id"
				+ " left join data_dictionary d on  d.id=s.payment_type ";
		List<String> strlist=new ArrayList<>();
		if(model.getSales_id()!=null){
			strlist.add(" s.sales_id='"+model.getSales_id()+"' ");
		}
		if(model.getBatch_id()!=null&&model.getBatch_id().length()!=0){
			strlist.add(" s.batch_num='"+model.getBatch_id()+"' ");
		}
		if((model.getStart_date()!=null&&model.getStart_date().length()!=0)&&(model.getEnd_date()!=null&&model.getEnd_date().length()!=0)){
			strlist.add(" s.cost_date>='"+model.getStart_date().trim()+"' and s.cost_date<='"+model.getEnd_date().trim()+"'");
		}else if((model.getStart_date()!=null&&model.getStart_date().length()!=0)&&(model.getEnd_date()==null||model.getEnd_date().length()==0)){
			strlist.add(" s.cost_date>='"+model.getStart_date().trim()+"' ");
		}else if((model.getStart_date()==null&&model.getStart_date().length()==0)&&(model.getEnd_date()!=null||model.getEnd_date().length()!=0)){
			strlist.add("  s.cost_date<='"+model.getEnd_date().trim()+"' ");
		}else{
			strlist.add(" s.cost_date between dateadd(mm,-1,getDate()) and getDate() ");
		}
		if(model.getCost_type()!=null&&model.getCost_type().length()!=0){
			strlist.add(" s.cost_type='"+model.getCost_type().trim()+"'");
		}
		sql+=" where "+strlist.get(0);
		for(int i=1;i<strlist.size();i++){
			sql+=" and "+strlist.get(i);
		}
		sql+=" order by cost_date desc";
		System.out.println(sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmSalesCostDTO.class);
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
	public CrmSalesCost getCrmSalesCost(String id) throws ServiceException {
		String sql="From CrmSalesCost c where c.id='"+id+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (CrmSalesCost) list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public String deleteCrmSalesCost(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="delete crm_sales_cost where id in('"+ids+"')";
			int rs = connection.createStatement().executeUpdate(sql);
			System.out.println(sql);
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
	public void addCrmSalesCost(CrmSalesCost crmSalesCost) throws ServiceException {
		persistenceManager.save(crmSalesCost);
		
	}

	@Override
	public void updateCrmSalesCost(CrmSalesCost crmSalesCost) throws ServiceException {
		persistenceManager.update(crmSalesCost);
		
	}

	@Override
	public List<CrmSalesCostTrDTO> getCrmSalesCostTr(String center_num) throws ServiceException, SQLException {
		String sql="select b.id as id,b.batch_name+'('+c.company_name+')' as batchname from batch as b left join contract as c on b.id=c.batch_id where b.center_num='"+center_num+"'";
		Connection connection = null;
		Statement statement = null;
		List<CrmSalesCostTrDTO> list = new ArrayList<CrmSalesCostTrDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				CrmSalesCostTrDTO jd = new CrmSalesCostTrDTO();
				jd.setBatch_num(rs.getString("id"));
				jd.setBatch(rs.getString("batchname"));
				list.add(jd);
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
	public List<CrmSalesCostTrDTO> getCrmSalesCostBX() throws ServiceException, SQLException {
		String sql="select d.id as id,d.data_name as name from data_dictionary d where data_code='BXKLX'";
		Connection connection = null;
		Statement statement = null;
		List<CrmSalesCostTrDTO> list = new ArrayList<CrmSalesCostTrDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				CrmSalesCostTrDTO jd = new CrmSalesCostTrDTO();
				jd.setPayment_type(rs.getString("id"));
				jd.setPayment_type_name(rs.getString("name"));
				list.add(jd);
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

}
