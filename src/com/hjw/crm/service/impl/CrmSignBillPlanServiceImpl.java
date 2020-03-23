package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.crm.DTO.BatchPlanLogDTO;
import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmCompanyContactsDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.DTO.CrmVisitPlanDTO;
import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.domain.CrmCompanyContacts;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.model.CrmSignBillPlanModel;
import com.hjw.crm.model.CrmVisitPlanModel;
import com.hjw.crm.service.CrmSignBillPlanService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.PinyinUtil;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.WebUserInfo;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmSignBillPlanServiceImpl implements CrmSignBillPlanService {
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
	public PageReturnDTO getCrmSignBillPlanList(CrmSignBillPlanModel model,long userid, int page, int pageSize)
			throws ServiceException, SQLException {
		
		String sql =" select cs.id,c.com_name,cs.company_id,cs.sign_num,cs.sign_name,cs.sign_pingying,cs.sign_year,ds.data_name as sign_type,"
				   +" do.data_name as old_new_type,dc.data_name as customer_type,cs.sign_count,"
				   + "cast(cs.sign_persion as varchar(10)) sign_persion,cast(cs.sign_amount as varchar(10)) sign_amount,"
				   +" CONVERT(varchar(50),cs.sign_date,23) as sign_date,CONVERT(varchar(50),cs.end_date,23) as end_date,"
				   +" cs.track_progress,CONVERT(varchar(50),cs.track_time,23) as track_time,cs.sign_status,CONVERT(varchar(50),cs.protect_date,23) as protect_date,CONVERT(varchar(50),cs.abort_date,23) as abort_date,u.chi_name as creater,"
				   + " CONVERT(varchar(50),cs.create_time,23) as create_time"
				   +" from company_info c,crm_sign_bill_plan cs left join user_usr u on cs.creater = u.id "
				   +" left join data_dictionary ds on ds.id = cs.sign_type"
				   +" left join data_dictionary do on do.id = cs.old_new_type"
				   +" left join data_dictionary dc on dc.id = cs.customer_type"
				   +" where cs.company_id = c.id "
			       + " and cs.creater in (select u.id from user_usr u,exam_user eu,examinatioin_center e"
			       + " where u.id = eu.user_id and eu.center_num = e.center_num and (u.id = "+userid
			       + " or ( e.parent_id in (select eu.exam_center_id from user_usr u,exam_user eu where u.id = eu.user_id "
			       + " and u.id = "+userid+"))))";
		if(model.getSign_year() != null && !"".equals(model.getSign_year())){
			sql += " and cs.sign_year = '"+model.getSign_year()+"'";
		}
		if(model.getSign_name() != null && !"".equals(model.getSign_name())){
			sql += " and (cs.sign_name like '%"+model.getSign_name()+"%' or cs.sign_pingying like '%"+model.getSign_name()+"%')";
		}
		sql += " order by cs.create_time desc";
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmSignBillPlanDTO.class);
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
	@SuppressWarnings("unchecked")
	public List<CrmCompanyContactsDTO> getCompanyContactsList(long comid) throws ServiceException, SQLException {
		String sql = " select c.*,di.data_name as important_levels,dp.data_name as positions from crm_company_contacts c "
				   + " left join data_dictionary di on c.important_level = di.id "
				   + " left join data_dictionary dp on c.position = dp.id  where c.company_id = "+ comid;
		List<CrmCompanyContactsDTO> list = this.jdbcQueryManager.getList(sql, CrmCompanyContactsDTO.class);
		return list;
	}

	@Override
	public List<CompanyInfoDTO> getCompanyInfolist(String comname) throws ServiceException, SQLException {
		StringBuffer sqltext= new StringBuffer();
        if(StringUtil.isEmpty(comname)){
        	  sqltext.append("with cte as ( select top 200 * from company_info c where  c.is_Active='Y' order by c.id desc "
        			  +" union all "
        			  +" select c.* FROM company_info c,cte b where c.id  = b.parent_com_id and c.is_Active='Y') "
        			  +" select distinct c.id,c.parent_com_id as _parentId,c.com_name,c.address,c.com_type,dt.data_name as com_types "
        			  +" ,c.economiccode,de.economicclass_name as economiccodes,c.areacode,da.areacode_name as areacodes"
        			  +" ,c.industrycode,di.industry_name as industrycodes,c.comsizecode,dc.scale_name as comsizecodes"
        			  +" from cte c"
        			  +" left join data_dictionary dt on dt.id = c.com_type"
        			  +" left join zyb_economicclass de on de.economicclass_code = c.economiccode"
        			  +" left join zyb_areacode da on da.areacode_code = c.areacode"
        			  +" left join zyb_economicindustry di on di.industry_code = c.industrycode"
        			  +" left join zyb_enterprisescale dc on dc.scale_code = c.comsizecode order by id desc");
        }else{
		    sqltext.append("with cte as ( "+
				 " select top 200 * from company_info c where  (c.com_name like '%"+comname+"%' or c.name_pinyin like '%"+comname+"%') and c.is_Active='Y' order by c.id desc"+
				 " union all "+
				 " select c.* FROM company_info c,cte b "+
				 " where c.id  = b.parent_com_id and c.is_Active='Y') "
				+" select distinct c.id,c.parent_com_id as _parentId,c.com_name,c.address,c.com_type,dt.data_name as com_types "
   			  	+" ,c.economiccode,de.economicclass_name as economiccodes,c.areacode,da.areacode_name as areacodes"
   			  	+" ,c.industrycode,di.industry_name as industrycodes,c.comsizecode,dc.scale_name as comsizecodes"
   			  	+" from cte c"
   			  	+" left join data_dictionary dt on dt.id = c.com_type"
   			  	+" left join zyb_economicclass de on de.economicclass_code = c.economiccode"
   			  	+" left join zyb_areacode da on da.areacode_code = c.areacode"
   			  	+" left join zyb_economicindustry di on di.industry_code = c.industrycode"
   			  	+" left join zyb_enterprisescale dc on dc.scale_code = c.comsizecode order by id desc");
	    }
        System.out.println(sqltext.toString());
        @SuppressWarnings("unchecked")
        List<CompanyInfoDTO> list = this.jdbcQueryManager.getList(sqltext.toString(), CompanyInfoDTO.class);
		return list;
	}

	@Override
	public String saveSignBillPlan(CrmSignBillPlanModel model,UserDTO user) throws ServiceException, SQLException, ParseException {
		
		CompanyInfo companyInfo = null;
		if(model.getCompany_id() > 0){//新增、修改单位
			companyInfo = (CompanyInfo) this.queryManager.load(CompanyInfo.class, model.getCompany_id());
			if(!model.getCom_name().equals(companyInfo.getCom_Name())){
				return "error-录入的单位名称与选择的单位名称不一致!";
			}
			companyInfo.setAddress(model.getAddress());
			companyInfo.setCom_Type(model.getCustomer_type());
			companyInfo.setComsizecode(model.getComsizecode());
			companyInfo.setAreacode(model.getAreacode());
			companyInfo.setIndustrycode(model.getIndustrycode());
			companyInfo.setEconomicidcode(model.getEconomiccode());
			companyInfo.setUpdater(user.getUserid());
			companyInfo.setUpdate_Time(DateTimeUtil.parse());
			this.persistenceManager.update(companyInfo);
		}else{
			@SuppressWarnings("unchecked")
			List<CompanyInfo> cList = this.queryManager.find("from CompanyInfo c where c.com_Name = '"+model.getCom_name()+"'");
			if(cList.size() != 0){
				return "error-录入的单位名称已存在,请先选择单位!";
			}
			companyInfo = new CompanyInfo();
			companyInfo.setCom_Name(model.getCom_name());
			companyInfo.setName_pinyin(PinyinUtil.getTheFirstMathedPinYin(model.getCom_name()));
			companyInfo.setCom_Level(1);
			companyInfo.setAddress(model.getAddress());
			companyInfo.setCom_Type(model.getCustomer_type());
			companyInfo.setComsizecode(model.getComsizecode());
			companyInfo.setAreacode(model.getAreacode());
			companyInfo.setIndustrycode(model.getIndustrycode());
			companyInfo.setEconomicidcode(model.getEconomiccode());
			companyInfo.setIs_Active("Y");
			companyInfo.setCreater(user.getUserid());
			companyInfo.setCreate_Time(DateTimeUtil.parse());
			companyInfo.setUpdater(user.getUserid());
			companyInfo.setUpdate_Time(DateTimeUtil.parse());
			this.persistenceManager.save(companyInfo);
		}
		
		CrmSignBillPlan crms = new CrmSignBillPlan();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		crms.setCompany_id(companyInfo.getId());
		crms.setSign_num(GetNumContral.getInstance().getParamNum("sign_num", user.getCenter_num()));
		crms.setSign_name(model.getSign_name());
		crms.setSign_pingying(model.getSign_pingying());
		crms.setSign_year(model.getSign_year());
		crms.setSign_type(model.getSign_type());
		crms.setOld_new_type(model.getOld_new_type());
		crms.setCustomer_type(model.getCustomer_type());
		crms.setSign_count(model.getSign_count());
		if(model.getSign_date()!=null&&model.getSign_date().length()>0){
			crms.setSign_date(df.parse(model.getSign_date()));
		}
		crms.setSign_persion(model.getSign_persion());
		crms.setSign_amount(model.getSign_amount());
		if(model.getEnd_date()!=null&&model.getEnd_date().length()>0){
			crms.setEnd_date(df.parse(model.getEnd_date()));	
		}
		crms.setTrack_progress("1");
		crms.setTrack_time(DateTimeUtil.parse());
		crms.setSign_status("1");
		crms.setCreater(user.getUserid());
		crms.setCreate_time(DateTimeUtil.parse());
		crms.setProtect_date(new Date());
		 Calendar c = Calendar.getInstance();
		 c.setTime(new Date());
		 c.add(Calendar.MONTH, 2);
		 crms.setAbort_date(c.getTime());
		this.persistenceManager.save(crms);
		//保存日志
		BatchPlanLog batchPlanLog=new BatchPlanLog();
		batchPlanLog.setCreater(String.valueOf(user.getUserid()));
		batchPlanLog.setCreater_time(new Date());
		batchPlanLog.setProject_id(crms.getSign_num());
		batchPlanLog.setProject_name(crms.getSign_name());
		batchPlanLog.setProject_status("签单计划申请成功");
		batchPlanLog.setProject_type("18");
		batchPlanLog.setType("1");
		saveBatchPlanLog(batchPlanLog);
		@SuppressWarnings("unchecked")
		List<CrmCompanyContacts> oldlist = this.queryManager.find("from CrmCompanyContacts c where c.company_id = "+companyInfo.getId());
		for(CrmCompanyContacts contacts : oldlist){
			this.persistenceManager.remove(contacts);
		}
		
		List<CrmCompanyContacts> list = model.getContactsList();
		for(CrmCompanyContacts contacts : list){
			contacts.setCompany_id(companyInfo.getId());
			this.persistenceManager.save(contacts);
		}
		
		return "ok-签单计划申请成功!";
	}
	
	@Override
	public List<CrmSignBillPlanDTO> getSignBillPlanByName(String sign_name,long user_id) throws ServiceException,SQLException{
		String sql ="select s.sign_num,s.sign_name,s.company_id from crm_sign_bill_plan s where s.sign_status = 3  ";
		if(user_id > 0){
			sql += " and s.creater in (select u.id from user_usr u,exam_user eu,examinatioin_center e"
				 + " where u.id = eu.user_id and eu.exam_center_id = e.id and (u.id = "+user_id
				 +" or ( e.parent_id in (select eu.exam_center_id from user_usr u,exam_user eu where u.id = eu.user_id "
				 + "and u.id = "+user_id+"))))";
		}
		if(sign_name != null && !"".equals(sign_name)){
			sql += " and (s.sign_name like '%"+sign_name+"%' or s.sign_pingying like '%"+sign_name+"%') ";
		}
		sql += " order by s.create_time desc";
		
		List<CrmSignBillPlanDTO> list = this.jdbcQueryManager.getList(sql, CrmSignBillPlanDTO.class);
		return list;
	}

	@Override
	public CrmSignBillPlan getSignBillPlanByNum(String sing_num) throws ServiceException,SQLException{
		String sql = "from CrmSignBillPlan c where c.sign_num = '"+sing_num+"'";
		List<CrmSignBillPlan> list = this.queryManager.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public CrmSignBillPlan updateSignBillPlan(CrmSignBillPlan crmSignBillPlan) throws ServiceException,SQLException{
		this.persistenceManager.update(crmSignBillPlan);
		return crmSignBillPlan;
	}
	@Override
	public CompanyInfoDTO getCompanyInfoById(long comId) throws ServiceException, SQLException {
		String sql = " select distinct c.id,c.parent_com_id as _parentId,c.com_name,c.address,c.com_type,dt.data_name as com_types "
   			  	+" ,c.economiccode,de.economicclass_name as economiccodes,c.areacode,da.areacode_name as areacodes"
   			  	+" ,c.industrycode,di.industry_name as industrycodes,c.comsizecode,dc.scale_name as comsizecodes"
   			  	+" from company_info c"
   			  	+" left join data_dictionary dt on dt.id = c.com_type"
   			  	+" left join zyb_economicclass de on de.economicclass_code = c.economiccode"
   			  	+" left join zyb_areacode da on da.areacode_code = c.areacode"
   			  	+" left join zyb_economicindustry di on di.industry_code = c.industrycode"
   			  	+" left join zyb_enterprisescale dc on dc.scale_code = c.comsizecode where c.id = "+comId;
		List<CompanyInfoDTO> list = this.jdbcQueryManager.getList(sql, CompanyInfoDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<CrmSignBillPlan> getCrmSignBill() throws ServiceException, SQLException {
		String sql="select s.sign_num,s.sign_name from CrmSignBillPlan s ";
		List<CrmSignBillPlan> list =queryManager.find(sql);
		return list;
	}


	@Override
	public List<CrmSignBillPlan> getCrmSignBill(String id) throws ServiceException, SQLException {
		String sql = "from CrmSignBillPlan c where c.id = '"+id+"'";
		List<CrmSignBillPlan> list = this.queryManager.find(sql);
		if(list.size() != 0){
			return list;
		}
		return null;
	}

	@Override
	public String editCreater(String id, String createrid) throws ServiceException, SQLException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update crm_sign_bill_plan set creater='"+createrid+"' where id='"+id+"'";
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
	public List<UserDTO> getUserDepList(long userId) throws ServiceException, SQLException {
		String sql =" select u.id userid,u.chi_name+'('+e.center_name+')' name from user_usr u,exam_user eu,examinatioin_center e"
				   +" where u.id = eu.user_id and eu.center_num = e.center_num and (u.id = "+userId
				   +" or ( e.parent_id in (select eu.exam_center_id from user_usr u,exam_user eu where "
				   +" u.id = eu.user_id and u.id = "+userId+")))";
		List<UserDTO> list = this.jdbcQueryManager.getList(sql, UserDTO.class);
		return list;
	}

	@Override
	public String saveBatchPlanLog(BatchPlanLog batchPlanLog) throws ServiceException, SQLException {
		try {
			this.persistenceManager.save(batchPlanLog);
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}

	@Override
	public PageReturnDTO getBatchPlanDTOList(String id,String type, int page, int rows)
			throws ServiceException, SQLException {

		String sql="select b.id,b.project_id,b.project_status,b.project_reason as project_reson,u.chi_name as creater,CONVERT(varchar(100), b.creater_time, 20) as "
				+ " creater_time,b.project_type,b.project_name from batch_plan_log b "
				+ " left join user_usr u on b.creater=u.id "
				+ " where b.project_id='"+id+"' and b.type='"+type+"' order by b.creater_time desc";
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,BatchPlanLogDTO.class);
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
	public String deleteCrmSignBillPlan(CrmSignBillPlan crmSignBillPlan) throws ServiceException, SQLException {
		try {
			this.persistenceManager.remove(crmSignBillPlan);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
	}

	@Override
	public String checkExamUser(String userid) throws ServiceException, SQLException {
		String sql=" select * from exam_user eu left join user_usr uu on eu.user_id=uu.id "
				+ "  where uu.id='"+userid+"' ";
		Connection connection = null;
		Statement statement = null;
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
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
		return flag;
	}

	@Override
	public PageReturnDTO getCountSignBillPlanList(CrmSignBillPlanModel model,long userid, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select convert(varchar,COUNT(case when c.track_progress='1' then 1 end)) as weipaichuzhuangdancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='2' then 1 end)) as kaishigenzongcount,"
				+ "convert(varchar,COUNT(case when c.track_progress='3' then 1 end)) as zhizuofangancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='4' then 1 end)) as shengchenghetongcount,"
				+ "convert(varchar,COUNT(case when c.track_progress='5' then 1 end)) as yibeidancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='6' then 1 end)) as tijianbinghuikuancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='7' then 1 end)) as yipaichuzhuangdancount,"
				+ "convert(varchar,sum(c.sign_amount)) as gusuanzongjine,"
				+ "convert(varchar,sum(c.sign_persion)) as gusuanzongrenshu,"
				+ "convert(varchar,COUNT(1)) as counts,"
				+ "convert(varchar,c.creater) as creater,"
				+ "u.chi_name as name "
				+ "from crm_sign_bill_plan c left join"
				+ " user_usr u on c.creater=u.id ";
		List<String> strlist=new ArrayList<String>();
		if(model.getCreater()!=null&&model.getCreater().length()>0){
			strlist.add(" c.creater='"+model.getCreater()+"'");
		}else{
			List<UserDTO> list=this.getUserDepList(userid);
			String str="'"+String.valueOf(list.get(0).getUserid())+"'";
			for(int i=1;i<list.size()-1;i++){
				str+=",'"+String.valueOf(list.get(i).getUserid())+"'";
			}
			strlist.add(" c.creater in ("+str+")");
		}
		if(model.getSignstartTime()!=null&&model.getSignstartTime().length()>0){
			strlist.add(" c.create_time>='"+model.getSignstartTime()+"'");
		}
		if(model.getSignendTime()!=null&&model.getSignendTime().length()>0){
			strlist.add("  c.create_time>='"+model.getSignstartTime()+"'");
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
		sql+=" group by c.creater ,u.chi_name ";
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
	public PageReturnDTO countSignBillPLanDetailList(CrmSignBillPlanModel model, long userid, int page, int rows)
			throws ServiceException, SQLException {
		String sql =" select cs.id,c.com_name,cs.company_id,cs.sign_num,cs.sign_name,cs.sign_pingying,cs.sign_year,ds.data_name as sign_type,"
				   +" do.data_name as old_new_type,dc.data_name as customer_type,cs.sign_count,"
				   + "cast(cs.sign_persion as varchar(10)) sign_persion,cast(cs.sign_amount as varchar(10)) sign_amount,"
				   +" CONVERT(varchar(50),cs.sign_date,23) as sign_date,CONVERT(varchar(50),cs.end_date,23) as end_date,"
				   +" cs.track_progress,CONVERT(varchar(50),cs.track_time,23) as track_time,cs.sign_status,CONVERT(varchar(50),cs.protect_date,23) as protect_date,CONVERT(varchar(50),cs.abort_date,23) as abort_date,u.chi_name as creater,"
				   + " CONVERT(varchar(50),cs.create_time,23) as create_time"
				   +" from company_info c,crm_sign_bill_plan cs left join user_usr u on cs.creater = u.id "
				   +" left join data_dictionary ds on ds.id = cs.sign_type"
				   +" left join data_dictionary do on do.id = cs.old_new_type"
				   +" left join data_dictionary dc on dc.id = cs.customer_type"
				   +" where cs.company_id = c.id "
			       + " and cs.creater='"+userid+"'";
		if(model.getSign_year() != null && !"".equals(model.getSign_year())){
			sql += " and cs.sign_year = '"+model.getSign_year()+"'";
		}
		if(model.getSign_name() != null && !"".equals(model.getSign_name())){
			sql += " and (cs.sign_name like '%"+model.getSign_name()+"%' or cs.sign_pingying like '%"+model.getSign_name()+"%')";
		}
		if(model.getSignstartTime()!=null&&model.getSignstartTime().length()>0){
			sql += " and c.create_time>='"+model.getSignstartTime()+"'";
		}
		if(model.getSignendTime()!=null&&model.getSignendTime().length()>0){
			sql += " and c.create_time>='"+model.getSignstartTime()+"'";
		}
		if(model.getTrack_progress()!=null&&model.getTrack_progress().length()>0){
			sql += " and c.track_progress='"+model.getTrack_progress()+"'";
		}
		if(model.getSign_status()!=null&&model.getSign_status().length()>0){
			sql += " and c.sign_status='"+model.getSign_status()+"'";
		}
		sql += " order by cs.create_time desc";
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

	private List<String> getCreateTime(String startTime,String endTime,String creater){
		String sql=" select distinct CONVERT(varchar(50),e.create_time,23) as create_time from crm_sign_bill_plan e ";
		List<String> strlist=new ArrayList<String>();
		if(startTime.trim().length()>0){
			strlist.add(" e.create_time>='"+startTime+" 00:00:00'");
		}
		if(endTime.trim().length()>0){
			strlist.add(" e.create_time<='"+endTime+" 23:59:59'");
		}
		if(creater.trim().length()>0){
			strlist.add(" e.creater='"+creater+"'");
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
		sql+=" order by create_time desc ";
		Connection connection = null;
		Statement statement = null;
		List<String> str1=new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString("create_time");
				str1.add(str);
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
		return str1;
	}
	public List<CrmSignBillPlanDTO> tuSignBillPLanList(CrmSignBillPlanModel model, int page, int rows)
			throws ServiceException, SQLException {
		List<String> createTimeList=this.getCreateTime(model.getSignstartTime(), model.getSignendTime(),model.getCreater());
		List<CrmSignBillPlanDTO> dtoList=new ArrayList<CrmSignBillPlanDTO>();
		if(createTimeList!=null&&createTimeList.size()>0){
			for(String createTime:createTimeList){
				CrmSignBillPlanDTO dto=this.getCrmSignBillPlanDTOList(createTime, model.getCreater());
				dtoList.add(dto);
			}
		}
		return dtoList;
	}
	private CrmSignBillPlanDTO getCrmSignBillPlanDTOList(String createTime,String creater){
		String sql="select convert(varchar,COUNT(case when c.track_progress='1' then 1 end)) as weipaichuzhuangdancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='2' then 1 end)) as kaishigenzongcount,"
				+ "convert(varchar,COUNT(case when c.track_progress='3' then 1 end)) as zhizuofangancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='4' then 1 end)) as shengchenghetongcount,"
				+ "convert(varchar,COUNT(case when c.track_progress='5' then 1 end)) as yibeidancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='6' then 1 end)) as tijianbinghuikuancount,"
				+ "convert(varchar,COUNT(case when c.track_progress='7' then 1 end)) as yipaichuzhuangdancount,"
				+ "convert(varchar,sum(c.sign_amount)) as gusuanzongjine,"
				+ "convert(varchar,sum(c.sign_persion)) as gusuanzongrenshu,"
				+ "convert(varchar,COUNT(1)) as counts,"
				+ "'"+createTime+"' as create_time "
				+ "from crm_sign_bill_plan c left join"
				+ " user_usr u on c.creater=u.id ";
		List<String> strlist=new ArrayList<String>();
		if(creater!=null&&creater.length()>0){
			strlist.add(" c.creater='"+creater+"'");
		}
		if(createTime!=null&&createTime.length()>0){
			strlist.add(" c.create_time<='"+createTime+" 23:59:59'");
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
		CrmSignBillPlanDTO dto=new CrmSignBillPlanDTO();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				dto.setWeipaichuzhuangdancount(rs.getString("weipaichuzhuangdancount"));
				dto.setKaishigenzongcount(rs.getString("kaishigenzongcount"));
				dto.setZhizuofangancount(rs.getString("zhizuofangancount"));
				dto.setShengchenghetongcount(rs.getString("shengchenghetongcount"));
				dto.setYibeidancount(rs.getString("yibeidancount"));
				dto.setTijianbinghuikuancount(rs.getString("tijianbinghuikuancount"));
				dto.setYipaichuzhuangdancount(rs.getString("yipaichuzhuangdancount"));
				dto.setGusuanzongjine(rs.getString("gusuanzongjine"));
				dto.setGusuanzongrenshu(rs.getString("gusuanzongrenshu"));
				dto.setCounts(rs.getString("counts"));
				dto.setCreate_time(rs.getString("create_time"));
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
		return dto;
	}

}
