package com.hjw.crm.service.impl;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.model.CrmSignBillPlanModel;
import com.hjw.crm.service.CrmSignBillPlanZhuangdanService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Contract;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmSignBillPlanZhuangDanServiceImpl implements CrmSignBillPlanZhuangdanService{
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
	public PageReturnDTO getSignBillPlanZhuangDanList(CrmSignBillPlanModel model, int page, int rows)
			throws ServiceException, SQLException, UnsupportedEncodingException {	
		String sign_names=model.getSign_names();
		String sql =" select cs.id,c.com_name,cs.company_id,cs.sign_num,cs.sign_name,cs.sign_pingying,cs.sign_year,ds.data_name as sign_type,"
				   +" do.data_name as old_new_type,dc.data_name as customer_type,cs.sign_count,"
				   + "cast(cs.sign_persion as varchar(10)) sign_persion,cast(cs.sign_amount as varchar(10)) sign_amount,"
				   +" CONVERT(varchar(50),cs.sign_date,23) as sign_date,CONVERT(varchar(50),cs.end_date,23) as end_date,"
				   +" cs.track_progress,cs.track_time,cs.sign_status,CONVERT(varchar(50),cs.protect_date,23) protect_date,CONVERT(varchar(50),cs.abort_date,23)  as abort_date,u.chi_name as creater,cs.create_time"
				   +" from company_info c,crm_sign_bill_plan cs left join user_usr u on cs.creater = u.id "
				   +" left join data_dictionary ds on ds.id = cs.sign_type"
				   +" left join data_dictionary do on do.id = cs.old_new_type"
				   +" left join data_dictionary dc on dc.id = cs.customer_type"
				   +" where cs.company_id = c.id ";
		if(sign_names!=null&&sign_names.length()>0){
			sql+=" and cs.sign_name like '%"+sign_names+"%'";
		}
		sql += " order by cs.create_time desc";
		System.out.println(sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,CrmSignBillPlanDTO.class);
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
	public String updateSignBillPlanZhuangDan(String id,String status) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update crm_sign_bill_plan set sign_status='"+status+"'  where id='"+id+"'";
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
	public String updateSignBillPlanZhuangDanProcess(String id,String status) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update crm_sign_bill_plan set track_progress='"+status+"'  where id='"+id+"'";
			System.out.println(sql);
			int rs = connection.createStatement().executeUpdate(sql);
			return "跟踪状态更新成功";
		} catch (SQLException e) {
			e.printStackTrace();
			return "跟踪状态更新失败";
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
	public PageReturnDTO getAllSignBillPlanZhuangDanList(CrmSignBillPlanModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sign_name=model.getSign_name();
		String sql =" select c.com_name,cs.company_id,cs.sign_num,cs.sign_name,cs.sign_pingying,cs.sign_year,ds.data_name as sign_type,"
				   +" do.data_name as old_new_type,dc.data_name as customer_type,cs.sign_count,"
				   + "cast(cs.sign_persion as varchar(10)) sign_persion,cast(cs.sign_amount as varchar(10)) sign_amount,"
				   +" CONVERT(varchar(50),cs.sign_date,23) as sign_date,CONVERT(varchar(50),cs.end_date,23) as end_date,"
				   +" cs.track_progress,cs.track_time,cs.sign_status,cs.protect_date,cs.abort_date,u.chi_name as creater,cs.create_time"
				   +" from company_info c,crm_sign_bill_plan cs left join user_usr u on cs.creater = u.id "
				   +" left join data_dictionary ds on ds.id = cs.sign_type"
				   +" left join data_dictionary do on do.id = cs.old_new_type"
				   +" left join data_dictionary dc on dc.id = cs.customer_type"
				   +" where cs.company_id = c.id ";
		if(sign_name!=null&&sign_name.length()>0){
			sql+=" and cs.sign_name like '%"+sign_name+"%'";
		}
		sql += " order by cs.create_time desc";
		System.out.println(sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,CrmSignBillPlanDTO.class);
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
	public String updateSignBillPlanZhuangDanProcessBySignNum(String sign_num, String status) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update crm_sign_bill_plan set track_progress='"+status+"'  where sign_num='"+sign_num+"'";
			System.out.println(sql);
			int rs = connection.createStatement().executeUpdate(sql);
			return "跟踪状态更新成功";
		} catch (SQLException e) {
			e.printStackTrace();
			return "跟踪状态更新失败";
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
	public List<CompanyInfo> getCompanyInfoFromZhuangdan(String id) throws ServiceException {
		String sql="select s.com_name,d1.data_name as com_type,d3.economicclass_name as economiccode,d2.scale_name as comsizecode,"
				+ " s.address,d4.industry_name as industrycode,d5.areacode_name as areacode from company_info s "
				+ " left join data_dictionary d1 on s.com_type=d1.id  "
				+ " left join zyb_enterprisescale d2 on s.comsizecode=d2.scale_code "
				+ " left join zyb_economicclass d3 on s.economiccode=d3.economicclass_code "
				+ " left join zyb_economicindustry d4 on s.industrycode=d4.industry_code "
				+ " left join zyb_areacode d5 on s.areacode=d5.areacode_code "
				+ "  where s.id='"+id+"'";
		Connection connection = null;
		Statement statement = null;
		List<CompanyInfo> list = new ArrayList<CompanyInfo>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				CompanyInfo jd = new CompanyInfo();
				jd.setCom_Name(rs.getString("com_name"));
				jd.setCom_Type(rs.getString("com_type"));
				jd.setEconomicidcode(rs.getString("economiccode"));
				jd.setComsizecode(rs.getString("comsizecode"));
				jd.setAddress(rs.getString("address"));
				jd.setIndustrycode(rs.getString("industrycode"));
				jd.setAreacode(rs.getString("areacode"));
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
	public List<CrmSignBillPlanDTO> getCrmSignBillPlanFromZhuangdan(String id) throws ServiceException {
		String sql=" select c.company_id,c.sign_year,c.id,CONVERT(varchar(50), c.abort_date, 23) as abort_date ,"
				+ " CONVERT(varchar(50), c.create_time, 23) as create_time,u.chi_name as creater,d1.data_name as customer_type,"
				+ " CONVERT(varchar(50), c.end_date, 23) as end_date,d2.data_name as old_new_type,"
				+ " CONVERT(varchar(50), c.protect_date, 23) as protect_date,cast(c.sign_amount as varchar(10)) as sign_amount,"
				+ " c.sign_count,CONVERT(varchar(50), c.sign_date, 23) as sign_date,c.sign_name,c.sign_pingying ,"
				+ " c.sign_num,cast(c.sign_persion as varchar(10)) as sign_persion,c.sign_status,d3.data_name as sign_type,c.track_progress,"
				+ " CONVERT(varchar(50), c.track_time, 23) as track_time "
				+ " from crm_sign_bill_plan c "
				+ " left join user_usr u on c.creater=u.id "
				+ " left join data_dictionary d1 on c.customer_type=d1.id"
				+ " left join data_dictionary d2 on c.old_new_type=d2.id"
				+ " left join data_dictionary d3 on c.sign_type=d3.id "
				+ "  where c.id='"+id+"' order by create_time desc";
		List<CrmSignBillPlanDTO> list=this.jdbcQueryManager.getList(sql, CrmSignBillPlanDTO.class);
		return list;
	}

	public String updateAbortDate(String ids, String abort_date) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update crm_sign_bill_plan set abort_date='"+abort_date+"'  where id='"+ids+"'";
			System.out.println(sql);
			int rs = connection.createStatement().executeUpdate(sql);
			return "保护截止日期更新成功";
		} catch (SQLException e) {
			e.printStackTrace();
			return "保护截止日期更新失败";
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
