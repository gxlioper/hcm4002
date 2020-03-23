package com.hjw.crm.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.crm.DTO.CrmPlanTacticsDTO;
import com.hjw.crm.DTO.CrmPlanTacticsDetailDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.DTO.CrmVisitLostDTO;
import com.hjw.crm.domain.CrmPlanTactics;
import com.hjw.crm.domain.CrmPlanTacticsDetail;
import com.hjw.crm.domain.CrmVisitLost;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.model.CrmVisitLostModel;
import com.hjw.crm.service.CrmVisitLostService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmVisitLostServiceImpl implements CrmVisitLostService{
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
	public PageReturnDTO getCrmVisitLostList(CrmVisitLostModel model, long userid, int page, int pageSize)
			throws ServiceException, SQLException {
		String sql="select cv.exam_num, cv.doctor_id,uu.chi_name,cv.arch_num,ci.user_name,cv.flag, convert(varchar(10), cv.create_time, 20) as create_time,"
				+ " cv.id from crm_visit_lost cv "
				+ " left join crm_member_private_doctor cm on cv.arch_num=cm.arch_num "
				+ " left join customer_info ci on cv.arch_num=ci.arch_num "
				+ " left join user_usr uu on cv.doctor_id=uu.id ";
		List<String> strlist=new ArrayList<String>();
		if(model.getArch_num()!=null&&model.getArch_num().length()>0){
			strlist.add(" cv.arch_num='"+model.getArch_num()+"'");
		}
		if(model.getUser_name()!=null&&model.getUser_name().length()>0){
			strlist.add(" ci.user_name='"+model.getUser_name()+"'");
		}
		if(userid!=0){
			strlist.add(" cv.doctor_id='"+userid+"' ");
		}
		if(model.getCreate_time()!=null&&model.getCreate_time().length()>0){
			strlist.add("  cv.create_time='"+model.getCreate_time()+" 23:59:59'");
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
		System.out.println(sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmVisitLostDTO.class);
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
	public String addCrmVisitLost(CrmVisitLost crmVisitLost) throws ServiceException, SQLException {
		try {
			this.persistenceManager.save(crmVisitLost);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public List<CrmPlanTactics> getPlanTacticsByNum(String tactics_num) throws ServiceException, SQLException {
		String sql = "select  * from crm_plan_tactics  where  tactics_num = '"+tactics_num+"'";
		List<CrmPlanTactics> list = this.jdbcQueryManager.getList(sql, CrmPlanTactics.class);
		return list;
	}

	@Override
	public String savePlanTactics(CrmPlanTactics crmPlanTactics) throws ServiceException, SQLException {
		this.persistenceManager.save(crmPlanTactics);
		return "ok-保存成功。。";
	}

	@Override
	public PageReturnDTO getPlantacTicsList(CrmVisitLostModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql = " select  c.id,tactics_num,notices,tactics_type,rmark,u.chi_name as creater ,uu.chi_name as updater,convert(varchar(50), c.create_date, 20) as create_date ,convert(varchar(50), c.update_date, 20) as update_date"
					+" from crm_plan_tactics c"
					+" left join user_usr u on c.creater = u.id"
					+" left join user_usr uu on c.updater = uu.id"
					+" order by c.create_date desc";
		PageSupport map = this.jdbcQueryManager.getList(sql,page,rows,CrmPlanTacticsDTO.class);
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
	public String updatePlanTactics(CrmPlanTactics crmPlanTactics) throws ServiceException, SQLException {
		this.persistenceManager.update(crmPlanTactics);
		return "ok-修改成功。。";
	}

	@Override
	public String savePlanTacticsDetail(CrmPlanTacticsDetail crmPlanTacticsDetail)
			throws ServiceException, SQLException {
		this.persistenceManager.save(crmPlanTacticsDetail);
	   return "ok-保存成功。。";
	}

	@Override
	public PageReturnDTO getPlantacTicsDetailList(CrmVisitLostModel model, int page, int rows) throws SQLException {
		String sql = "select  c.id,c.tactics_num,c.notices,c.distancedate,uuu.chi_name as plan_doctor ,u.chi_name as creater"
					+" ,convert(varchar(50), c.create_date, 20) as create_date,uu.chi_name as updater ,convert(varchar(50), c.update_date, 20) as update_date,c.plan_doctor_id ,c.plan_doctor_id"
					+" from crm_plan_tactics_detail  c "
					+" left join user_usr u on u.id = c.creater "
					+" left join user_usr uu on uu.id = c.updater "
					+" left join user_usr uuu on uuu.id = c.plan_doctor_id "
					+" where c.tactics_num = '"+model.getTactics_num()+"'"
					+" order by c.distancedate";
		PageSupport map = this.jdbcQueryManager.getList(sql,page,rows,CrmPlanTacticsDetailDTO.class);
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
	public CrmPlanTacticsDetail getPlanTacticsDetailById(long tactics_detail_id) {
		CrmPlanTacticsDetail object = (CrmPlanTacticsDetail) this.queryManager.get(CrmPlanTacticsDetail.class, tactics_detail_id);
		return object;
	}

	@Override
	public String updatePlanTacticsDetail(CrmPlanTacticsDetail crmPlan) throws SQLException {
		this.persistenceManager.update(crmPlan);
		return "ok-修改成功。。。";
	}

	@Override
	public String delPlanTacticsDetail(String tactics_detail_ids) throws SQLException {
		String sql ="delete from crm_plan_tactics_detail  where id in("+tactics_detail_ids+")";
		this.jdbcPersistenceManager.execSql(sql);
		return "ok-删除成功。。。。";
	}

	@Override
	public String delPlanTactics(String tactics_nums) throws SQLException {
		String sql = "delete from crm_plan_tactics_detail where tactics_num in("+tactics_nums+")";
	    this.jdbcPersistenceManager.execSql(sql);
	    
	    String sq = "delete from crm_plan_tactics where tactics_num in("+tactics_nums+")";
	    int execSql = this.jdbcPersistenceManager.execSql(sq);
	    return "ok-删除成功。。。。";
		
	}

	@Override
	public List<CrmPlanTacticsDTO> getCrmVisitPlanTacticsType() throws SQLException {
		String sql = "select distinct  tactics_type from   crm_plan_tactics  ";
		List<CrmPlanTacticsDTO> list = this.jdbcQueryManager.getList(sql, CrmPlanTacticsDTO.class);
		return list;
	}

	@Override
	public List<CrmPlanTacticsDTO> getTacticsNoticesList(int tactics_type) throws SQLException {
		String sql = "select  tactics_num , notices from  crm_plan_tactics  where  tactics_type = "+tactics_type;
		List<CrmPlanTacticsDTO> list = this.jdbcQueryManager.getList(sql, CrmPlanTacticsDTO.class);
		return list;
	}

}
