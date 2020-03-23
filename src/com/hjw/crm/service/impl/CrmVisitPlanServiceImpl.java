package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.crm.DTO.CrmVisitPlanDTO;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.model.CrmVisitPlanModel;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.util.IsPrivateCenterConfig;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmVisitPlanServiceImpl implements CrmVisitPlanService{
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
	public PageReturnDTO getCrmVisitPlanList(CrmVisitPlanModel model, int page, int rows)
			throws ServiceException ,SQLException{
		String sql="select c.id, cvr.id as cvr_id "//id 
				+ ",c.arch_num "//档案号 
				+ ",c.exam_num "//体检号
				+ ",cvr.visit_doctor_id as plan_doctor_id "//回访医生Id 
				+ ",convert(varchar(10), cvr.visit_date, 20)as plan_visit_date"//回访时间 
				+ ",cvr.visit_notices as visit_content "//回访内容
				+ ",c.visit_num "//健康计划编码
				+ ",c.visit_status "//回访状态
				+ ",c.creater"//创建人
				+ ",c.create_time "//创建时间
				+ ",u.chi_name as username"//医生姓名
				+ ",i.user_name as personname "//患者姓名
				+ ",s.chi_name as sname"//创建者姓名
				+ ",d.data_name as visit_important"
				+",i.phone ,cvr.customer_feedback ,cvr.health_suggest ,cvr.visit_type "
				+" ,cvr.visit_type  ,cpt.tactics_type , cpt.notices,cpt.rmark ,cvr.record_status"
				+" ,(dbo.GetTeamPayByExamId(e.exam_num) + dbo.GetPersonalPayByExamId(e.exam_num)) personal_pay "
				+ " from crm_plan_tactics cpt , crm_visit_plan c "
				+" left join exam_info  e on e.exam_num = c.exam_num "
				+ " left join crm_visit_record cvr  on cvr.visit_num = c.visit_num "
				+ " left join user_usr u on u.id = cvr.visit_doctor_id "
				+ " left join customer_info i on c.arch_num=i.arch_num "
				+ " left join user_usr s on c.creater=s.id "
				+ " left join data_dictionary d on d.id = c.visit_important"
				+ " where cpt.tactics_num = c.tactics_num ";
		
		if(model.getArch_num() != null && model.getArch_num().length() != 0){
			sql+=" and c.arch_num='"+model.getArch_num()+"' ";
		}
		if(model.getExam_num() != null && model.getExam_num().length() != 0){
			sql+=" and c.exam_num='"+model.getExam_num()+"'";
		}
		if(model.getName() != null && model.getName().length() != 0){
			sql+=" and i.user_name='"+model.getName()+"' ";
		}
		if(model.getStartTime() != null && model.getStartTime().length() != 0){
			sql+=" and cvr.visit_date>='"+model.getStartTime()+" 00:00:00.000"+"' ";
		}
		if(model.getEndTime() != null && model.getEndTime().length() != 0){
			sql+=" and cvr.visit_date<'"+model.getEndTime()+" 23:59:59.999"+"' ";
		}
		if(model.getVisit_status() != null && model.getVisit_status().length() != 0){
			sql+=" and c.visit_status='"+model.getVisit_status()+"' ";
		}
		if(model.getVisit_important()!=null&&model.getVisit_important().length()!=0){
			sql+=" and c.visit_important='"+model.getVisit_important()+"' ";
		}
		if(model.getPlan_visit_date()!=null&&model.getPlan_visit_date().length()!=0){
			sql+=" and convert(varchar(10), cvr.visit_date, 20)='"+model.getPlan_visit_date()+"' ";
		}
		if(model.getFujianflag()!=null&&model.getFujianflag().length()>0){
			sql+=" and fujianflag='"+model.getFujianflag()+"' ";
		}
		if(!"".equals(model.getPlan_doctor_id()) && model.getPlan_doctor_id() != null && model.getPlan_doctor_id() > 0){
			sql+=" and   cvr.visit_doctor_id = " +model.getPlan_doctor_id() ;
		}
		sql+=" order by c.visit_status asc,cvr.visit_date asc , c.plan_visit_date desc";
//		System.out.println("=-------------------------=="+sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,CrmVisitPlanDTO.class);
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
	public CrmVisitPlan getCrmVisitPlan(String id) throws ServiceException {
		String sql="From CrmVisitPlan c where c.id='"+id+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (CrmVisitPlan) list.get(0);
		}else{
			return null;
		}
	}

	public String deleteCrmVisitPlan(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="delete crm_visit_plan where id in('"+ids+"')";
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
	public void addCrmVisitPlan(CrmVisitPlan crmVisitPlan) throws ServiceException {
		persistenceManager.save(crmVisitPlan);
		
	}

	@Override
	public void updateCrmVisitPlan(CrmVisitPlan crmVisitPlan) throws ServiceException {
		persistenceManager.update(crmVisitPlan);
		
	}

	@Override
	public List<WebUserInfo> getCrmVisitPlanUserList() throws ServiceException, SQLException {
		String sql="select s.id,s.chi_Name from WebUserInfo s";
		List<WebUserInfo> list =queryManager.find(sql);
		return list;
	}

	@Override
	public String updateCrmVisitPlanEndStatus(String ids,String visit_status) throws ServiceException {
		List<CrmVisitPlan> list = this.queryManager.find("from CrmVisitPlan c where c.id in('"+ids+"')");
		for (int i = 0; i < list.size(); i++) {
			CrmVisitPlan crmVisitPlan = list.get(i);
			crmVisitPlan.setVisit_status(visit_status);
			this.persistenceManager.update(crmVisitPlan);
		}
		if("2".equals(visit_status)){
			return "ok-取消结束回访成功!";
		}else if("3".equals(visit_status)){
			return "ok-结束回访成功!";
		}else{
			return "ok-成功!";
		}
	}

	@Override
	public PageReturnDTO getCrmAllVisitPlanList(CrmVisitPlanModel model, int page, int rows,UserDTO user)
			throws ServiceException, SQLException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限 Y有 N无
		String sql="select c.id "//id 
				+ ",c.arch_num "//档案号 
				+ ",c.exam_num"//体检号
				+ ",c.plan_doctor_id "//回访医生Id 
				+ ",convert(varchar(10), c.plan_visit_date, 20) as plan_visit_date"//回访时间 
				+ ",cvr.visit_notices as visit_content "//回访内容
				+ ",c.visit_num "//健康计划编码
				+ ",c.visit_status "//回访状态
				+ ",c.creater"//创建人
				+ ",c.create_time "//创建时间
				+ ",u.chi_name as username"//医生姓名
				+ ",dbo.fun_CustomerToStar(i.id,'name',i.user_name,'"+isprivateflag+"') as personname "//患者姓名
				+ ",s.chi_name as sname"//创建者姓名
				+ ",d.data_name as visit_important ,cvr.record_status,i.phone "
				+" ,(dbo.GetTeamPayByExamId(e.exam_num) + dbo.GetPersonalPayByExamId(e.exam_num)) personal_pay "
				+ " from crm_visit_plan c "
				+"  left join exam_info  e on e.exam_num = c.exam_num "
				+"  left join crm_visit_record cvr on c.visit_num = cvr.visit_num "
			    +"  left  join user_usr  u on u.id = cvr.visit_doctor_id "
				+ " left join customer_info i on c.arch_num=i.arch_num "
				+ " left join user_usr s on c.creater=s.id"
				+ " left join data_dictionary d on d.id = c.visit_important where 1=1 ";
		if(model.getArch_num() != null && model.getArch_num().length() != 0){
			sql+=" and c.arch_num='"+model.getArch_num()+"' ";
		}
		if(model.getExam_num() != null && model.getExam_num().length() != 0){
			sql+=" and c.exam_num='"+model.getExam_num()+"'";
		}
		if(model.getName() != null && model.getName().length() != 0){
			sql+=" and i.user_name='"+model.getName()+"' ";
		}
		if(model.getStartTime() != null && model.getStartTime().length() != 0){
			sql+=" and c.plan_visit_date>='"+model.getStartTime()+"' ";
		}
		if(model.getEndTime() != null && model.getEndTime().length() != 0){
			sql+=" and c.plan_visit_date<='"+model.getEndTime()+"' ";
		}
		if(model.getVisit_status() != null && model.getVisit_status().length() != 0){
			sql+=" and c.visit_status='"+model.getVisit_status()+"' ";
		}
		if(model.getVisit_important()!=null&&model.getVisit_important().length()!=0){
			sql+=" and c.visit_important='"+model.getVisit_important()+"' ";
		}
		if(model.getPlan_visit_date()!=null&&model.getPlan_visit_date().length()!=0){
			sql+=" and convert(varchar(10), c.plan_visit_date, 20)='"+model.getPlan_visit_date()+"' ";
		}
		sql+=" order by c.plan_visit_date desc";
//		System.out.println("-----"+sql);
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,CrmVisitPlanDTO.class);
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
	public ExamInfo getIdByExamInfoExamNum(String exam_num) throws ServiceException, SQLException {
		String sql="From ExamInfo c where c.exam_num='"+exam_num+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			ExamInfo e=(ExamInfo) list.get(0);
			return e;
		}else{
			return null;
		}
	}

	@Override
	public List<ExamInfo> getExamInfoByArchNum(String arch_num) throws ServiceException, SQLException {
		String sql="select e.exam_num,e.id,convert(varchar(10), e.join_date, 20) as join_date "
				+ "from exam_info e left join customer_info c on e.customer_id=c.id where c.arch_num='"+arch_num+"'"
						+ " order by join_date desc";
		Connection connection = null;
		Statement statement = null;
		List<ExamInfo> list = new ArrayList<ExamInfo>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				ExamInfo e = new ExamInfo();
				e.setId(Long.valueOf(rs.getString("id")));
				e.setExam_num(rs.getString("exam_num"));
				e.setJoin_date(rs.getString("join_date"));
				list.add(e);
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
			return list;
		}else{
			return null;
		}
	}

	@Override
	public ExamInfo getExamInfoByArchNumAndJoinDate(String arch_num, String join_date)
			throws ServiceException, SQLException {
		String sql="select e.exam_num,e.id "
				+ "from exam_info e left join customer_info c on e.customer_id=c.id where c.arch_num='"+arch_num+"'"
				+ " and convert(varchar(10), join_date, 20)='"+join_date+"'";
		Connection connection = null;
		Statement statement = null;
		List<ExamInfo> ee=new ArrayList<ExamInfo>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				ExamInfo e=new ExamInfo();
				e.setId(Long.valueOf(rs.getString("id")));
				e.setExam_num(rs.getString("exam_num"));
				ee.add(e);
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
			return ee.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 根据计划医生计划回访时间计算健康计划个数
	     * <p>Title: getCrmVisitPlanCount</p>   
	     * <p>Description: </p>   
	     * @param userid
	     * @param plan_visit_date
	     * @return
	     * @throws ServiceException
	     * @throws SQLException   
	     * @see com.hjw.crm.service.CrmVisitPlanService#getCrmVisitPlanCount(java.lang.String, java.lang.String)
	 */
	@Override
	public String getCrmVisitPlanCount(String userid,String plan_visit_date) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_visit_record c ";
		List<String> strlist=new ArrayList<String>();
		if(userid!=null&&userid.length()>0){
			strlist.add(" c.visit_doctor_id='"+userid+"'");
		}
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			strlist.add(" convert(varchar(10), c.visit_date, 20)='"+plan_visit_date+"' ");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getCrmVisitPlanCount(String userid, String plan_visit_date, String visit_important)
			throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_visit_record c , crm_visit_plan cv  ";
		List<String> strlist=new ArrayList<String>();
		strlist.add("c.visit_num = cv.visit_num");
		if(userid!=null&&userid.length()>0){
			strlist.add(" c.visit_doctor_id='"+userid+"'");
		}
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			strlist.add(" convert(varchar(10),  c.visit_date, 20)='"+plan_visit_date+"' ");
		}
		if(visit_important!=null&&visit_important.length()>0){
			strlist.add(" cv.visit_important='"+visit_important+"'");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getCrmVisitPlanCount(String userid, int visit_status) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_visit_plan c ";
		List<String> strlist=new ArrayList<String>();
		if(userid!=null&&userid.length()>0){
			strlist.add(" c.plan_doctor_id='"+userid+"'");
		}
		if(visit_status!=0){
			strlist.add(" c.visit_status='"+visit_status+"' ");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getCrmVisitRecordCount(String userid, String plan_visit_date) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_visit_record c ";
		List<String> strlist=new ArrayList<String>();
		if(userid!=null&&userid.length()>0){
			strlist.add(" c.visit_doctor_id='"+userid+"'  and c.record_status = '1' ");
		}
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			strlist.add(" convert(varchar(10), c.visit_date, 20)='"+plan_visit_date+"' ");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getKeHuCount(String userid, String plan_visit_date) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_member_private_doctor c ";
		List<String> strlist=new ArrayList<String>();
		if(userid!=null&&userid.length()>0){
			strlist.add(" c.doctor_id='"+userid+"'");
		}
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			strlist.add(" convert(varchar(10), c.allot_date, 20)='"+plan_visit_date+"' ");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}
	public String getKeHuCount(String userid, String plan_visit_date,String flag) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_member_private_doctor  cr  ";
		List<String> strlist=new ArrayList<String>();
		if(userid!=null&&userid.length()>0){
			strlist.add(" cr.doctor_id='"+userid+"'");
		}
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			strlist.add(" convert(varchar(10), cr.allot_date, 20)='"+plan_visit_date+"' ");
		}
		if(flag!=null&&flag.length()>0){
			strlist.add(" cr.flag='"+flag+"' ");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag1="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
				flag1="1";
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
		if(flag1=="1"){
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getLostCrm(String userid, String plan_visit_date) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from  crm_visit_lost cv";
		List<String> strlist=new ArrayList<String>();
		if(userid!=null&&userid.length()>0){
			strlist.add(" cv.doctor_id='"+userid+"'");
		}
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			strlist.add(" convert(varchar(10), cv.create_time, 20)='"+plan_visit_date+"' ");
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
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getCrmVisitPlanFujianCount(String userid,String plan_visit_date) throws ServiceException, SQLException {
		String sql=" select count(*) as counts from crm_visit_plan c , crm_visit_record cvr where c.visit_num = cvr.visit_num and c.fujianflag='1' and  cvr.visit_doctor_id='"+userid+"' ";
		if(plan_visit_date!=null&&plan_visit_date.length()>0){
			sql+=" and convert(varchar(10), cvr.visit_date, 20)='"+plan_visit_date+"' ";
		}
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public String getDataDict(String name) throws ServiceException, SQLException {
		String sql=" select id from data_dictionary where data_code='JHZYJB' and data_name='"+name+"' ";
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("id");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public void deleteCrmVisitPlanByNum(String visit_num) throws ServiceException, SQLException {
		String sql = "delete  from  crm_visit_plan  where visit_num = '"+visit_num+"'";
		this.jdbcPersistenceManager.execSql(sql);
	}

	@Override
	public String getCrmVisitRecordCountByDate(long userid, String plan_visit_date)
			throws ServiceException, SQLException {
		if("".equals(plan_visit_date)||plan_visit_date==null){
			return "0";
		}
		
		String sql = "select COUNT (*) as counts from crm_visit_record  cvr , crm_visit_plan cvp where  cvp.visit_num = cvr.visit_num and   visit_doctor_id = "+userid+"   and cvr.record_status = '1'"
				+" and convert(varchar(10), cvr.visit_date, 20)='"+plan_visit_date+"' and cvp.visit_status = '2' ";
		
		Connection connection = null;
		Statement statement = null;
		String str = "0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				str=rs.getString("counts");
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
		return str;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDTO> getVisitDoctorList() throws ServiceException, SQLException {
		String sql = "select  distinct  u.chi_name  as username, u.id from crm_visit_record  c  ,user_usr  u where u.id = c.visit_doctor_id ";
		List<UserDTO>  list = this.jdbcQueryManager.getList(sql, UserDTO.class);
		return list;
	}

	@Override
	public String getShengrikehuCount(String birthday) throws ServiceException, SQLException {
		
		
		String sql="select COUNT(*) as counts from " + 
				"(select distinct c.arch_num,c.user_name,c.id_num,c.birthday from customer_info c " + 
				"left join exam_info e on e.customer_id = c.id " + 
				"where e.vipflag = 1 ";
		
		if(birthday!=null&&birthday.length()>0) {
			String month = birthday.substring(5,7);
			String day = birthday.substring(8,10);
			sql += " and datepart(mm,c.birthday) = '"+month+"' and datepart(dd,c.birthday) = '"+day+"' ";
		}
		sql +=") as a ";
		Connection connection = null;
		Statement statement = null;
		List<String> ee=new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("counts");
				ee.add(str);
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
			return ee.get(0);
		}else{
			return "0";
		}
	}

	@Override
	public PageReturnDTO getShengrikehuList(CrmVisitPlanModel model, int page, int rows)
			throws ServiceException, SQLException {
		String sql = "select distinct c.arch_num,c.user_name,c.id_num,convert(varchar(20),c.birthday,23)as birthday,c.phone,c.sex  from customer_info c " + 
				"left join exam_info e on e.customer_id = c.id where e.vipflag = 1 ";
		if(model.getPlan_visit_date()!=null&&model.getPlan_visit_date().length()>0) {
			String month = model.getPlan_visit_date().substring(5,7);
			String day = model.getPlan_visit_date().substring(8,10);
			sql += " and datepart(mm,c.birthday) = '"+month+"' and datepart(dd,c.birthday) = '"+day+"' ";
		}
		if(model.getArch_num()!=null && model.getArch_num().length()>0) {
			sql += "and c.arch_num= '"+model.getArch_num()+"' ";
		}
		if(model.getName()!=null && model.getName().length()>0) {
			sql += "and c.user_name = '"+model.getName()+"'";
		}
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,CustomerInfoDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(rows);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}


}
