package com.hjw.wst.service.impl;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hjw.crm.DTO.CrmVisitRecordDTO;
import com.hjw.crm.domain.CrmPlanTactics;
import com.hjw.crm.domain.CrmPlanTacticsDetail;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Critical;
import com.hjw.wst.domain.CriticalVisitPlan;
import com.hjw.wst.domain.ExamCriticalLog;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.model.CriticalModel;
import com.hjw.wst.service.CriticalService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamFlowService;
import com.hjw.wst.service.examInfoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
import com.twelvemonkeys.lang.StringUtil;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;

public class CriticalServiceImpl implements CriticalService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
	private examInfoService examInfoService;
	private CustomerInfoService customerInfoService;
	private CrmVisitRecordService crmVisitRecordService;
	private CrmVisitPlanService crmVisitPlanService;

	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setCrmVisitRecordService(CrmVisitRecordService crmVisitRecordService) {
		this.crmVisitRecordService = crmVisitRecordService;
	}

	public void setCrmVisitPlanService(CrmVisitPlanService crmVisitPlanService) {
		this.crmVisitPlanService = crmVisitPlanService;
	}

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

	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jpm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}
	@Override
	public PageReturnDTO getCriticalLis(CriticalModel model,int pageno, int pagesize,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		String sql =" select distinct dbo.fun_CharToStar(e.id,'id_num',t.id_num,'"+isprivateflag+"') as id_num,c.id,t.arch_num,e.exam_num,dbo.fun_CharToStar(e.id,'name',t.user_name,'"+isprivateflag+"') as user_name,d.dep_name,i.item_name, "
				+" c.check_date,c.note,u.chi_name as check_doctor,c.exam_result,c.done_flag,c.done_date ,ei.item_name as examination_item_name , "
				+ "uu.chi_name as creater_name , c.create_time,dkl.disease_name ,t.sex,e.age,dbo.fun_CharToStar(e.id,'phone',e.phone,'"+isprivateflag+"') as phone,e.company,"
				+" exam_cl_z.critical_class_name,exam_cl.critical_class_name as  parent_critical_class_name,dd.data_name,e.id as exam_info_id"
				+" from customer_info t,exam_Critical_detail c "
				+" left join exam_info e on c.exam_info_id=e.id "
				+" left join department_dep d on c.dept_id=d.id "
				+" left join user_usr u on c.userid=u.id "
				+" left join user_usr uu on c.creater=uu.id "
				+" left join charging_item i on c.charging_item_id=i.id "
				+" left join examination_item  ei on ei.id = c.exam_item_id"
				+" left join disease_knowloedge_lib dkl on dkl.id = c.disease_id"
				+" left join exam_Critical_class exam_cl on  exam_cl.id = c.critical_class_parent_id  and  exam_cl.critical_class_level=1"
				+" left join exam_Critical_class exam_cl_z on  exam_cl_z.id = c.critical_class_id   and  exam_cl_z.critical_class_level=2"
				+" left join data_dictionary dd on  dd.id = c.critical_class_level  and dd.data_code='WJZDJ' and dd.isActive='Y'"
				+" where e.customer_id=t.id and c.is_active = 'Y'";
		if(model.getDone_flag() != 3){
			sql += " and done_flag= '"+model.getDone_flag()+"'";
		}
		if (model.getStartCheckDate() != null && !"".equals(model.getStartCheckDate())) {// 体检开始日期
			sql += " and c.check_date >= '" + model.getStartCheckDate() + " 00:00:00.000'";
		}
		if (model.getEndCheckDate() != null && !"".equals(model.getEndCheckDate())) {// 体检结束日期
			sql += " and c.check_date < '" + model.getEndCheckDate() + " 23:59:59.999'";
		}
		if (!"".equals(model.getCheck_doctor()) && model.getCheck_doctor() != null) {
			sql += " and u.chi_name like '%"+model.getCheck_doctor()+"%'";
		}
		if (!"".equals(model.getData_source()) && model.getData_source() != null) {
			sql += " and c.data_source = '"+model.getData_source()+"'";
		}
		if (!"".equals(model.getDep_category()) && model.getDep_category() != null) {
			sql+=" and d.dep_category  = '"+model.getDep_category()+"'";
		}
		if (!"".equals(model.getStartDone_date()) && model.getStartDone_date() != null) {
			sql+=" and c.done_date >= '" + model.getStartDone_date() + " 00:00:00.000'";
		}
		if (!"".equals(model.getEndDone_date()) && model.getEndDone_date() != null) {
			sql+=" and c.done_date < '" + model.getEndDone_date() + " 23:59:59.999'";
		}
		if (!"".equals(model.getExam_result()) && model.getExam_result() != null) {
			sql+=" and c.exam_result like '%"+model.getExam_result()+"%'";
		}
		if (!"".equals(model.getExam_num()) && model.getExam_num()!= null) {
			sql+=" and e.exam_num = '"+model.getExam_num().trim()+"'";
		}
		if(!StringUtil.isEmpty(model.getExam_name())) {
			sql+=" and t.user_name like'%"+model.getExam_name()+"%'";
		}
		if(!StringUtil.isEmpty(model.getStart_date())) {
			sql+=" and e.join_date>'"+model.getStart_date()+" 00:00:00'";
		}
		if(!StringUtil.isEmpty(model.getEnd_dta())) {
			sql+=" and e.join_date<'"+model.getEnd_dta()+" 23:59:59'";
		}
		if(model.getCritical_class_level()>0) {
			sql+=" and c.critical_class_level="+model.getCritical_class_level();
		}
		if(!StringUtils.isEmpty(model.getStart_create_time())) {
			sql+=" and c.create_time>'"+model.getStart_create_time()+" 00:00:00'";
		}
		if(!StringUtils.isEmpty(model.getEnd_create_time())) {
			sql+=" and c.create_time<'"+model.getEnd_create_time()+" 23:59:59'";
		}
		if(model.getBatch_id()>0){
			sql+=" and e.batch_id ="+model.getBatch_id();
		}
		if(model.getCom_id()>0){
			sql+=" and e.company_id="+model.getCom_id();
		}
		sql += " order by c.check_date desc";
		PageSupport map =  this.jqm.getList(sql,pagesize,pageno,CriticalDTO.class);
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
	public Critical updateCritical(Critical c) throws ServiceException {
		this.pm.update(c);
		return  c;
	}
	
	@Override
	public void saveCritical(Critical c) throws ServiceException {
		this.pm.save(c);
	}

	@Override
	public Critical queryById(long id) throws ServiceException {
		return (Critical)this.qm.load(Critical.class, id);
	}

	@Override
	public void updaterCritical(long id, UserDTO user)throws ServiceException {

		String sql="";
		Connection connection = null;
		try {
			sql+=" update exam_Critical_detail set note='?',done_date=getdate(),userid='"+user.getUserid()+"' where id=? ";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	
	}

	@Override
	public long getgetCriticalNotice() throws ServiceException {
		String sql = "SELECT c.exam_info_id FROM exam_Critical_detail c where c.done_flag = 0 and is_active = 'Y'";
		List<CriticalDTO> list = this.jqm.getList(sql, CriticalDTO.class);
		return list.size();
	}

	@Override
	public String addCritical(Long examinfo_id, Long item_id, String dep_category,long userId, String center_num) throws ServiceException {
		String sql = "select id from exam_Critical_detail where  exam_info_id = "+examinfo_id+" and  exam_item_id = "+item_id+"  and is_active = 'Y'";
		long vid = item_id;
		if (dep_category.equals("21") ) {
			String sq = "select  ci.id as charging_item_id from view_exam_detail   ved , pacs_summary  p , "
					+ "charging_item ci where p.examinfo_sampleId = ci.sam_demo_id and p.id = ved.pacs_id and ved.id = "+item_id;
			List<CriticalDTO> list = this.jqm.getList(sq, CriticalDTO.class);
			if (list.size() > 0) {
				item_id = list.get(0).getCharging_item_id();
				 sql = "select id from exam_Critical_detail where  exam_info_id = "+examinfo_id+" and  charging_item_id = "+item_id+"  and is_active = 'Y'";
			}else{
				return "error-操作失败 ！！";
			}
		}
		
		List<CriticalDTO> list = this.jqm.getList(sql, CriticalDTO.class);
		if (list.size() > 0) {
			return "error-记录已存在，请不要重复操作！！";
		}else{
			if (dep_category.equals("17")) {
			   String  comsql = "select  c.id as charging_item_id ,ei.id as item_code,ced.exam_result,c.dep_id as dept_id , e.join_date as check_date from  common_exam_detail ced , exam_info e ,"
			   					+" charging_item c ,charging_item_exam_item  ciei , examination_item ei"
		                        +" where   ciei.charging_item_code = c.item_code and ciei.item_code = ei.item_num and e.exam_num = ced.exam_num  "
		                        +" and ced.item_code = ei.item_num  and ced.exam_item_id = "+item_id+" and ced.exam_info_id = "+examinfo_id;
			   List<CriticalDTO> li = this.jqm.getList(comsql, CriticalDTO.class);
			   if (li.size() > 0) {
				   CriticalDTO cr = li.get(0);
				   Critical critical = new Critical();
				   critical.setCharging_item_id(cr.getCharging_item_id()+"");
				   critical.setDept_id(cr.getDept_id());
				   critical.setExam_result(cr.getExam_result());
				   critical.setExam_info_id(examinfo_id);
				   critical.setCreater(userId);
				   critical.setCreate_time(DateTimeUtil.parse());
				   critical.setData_source(1);
				   critical.setDone_flag(0);
				   critical.setCheck_date(DateTimeUtil.parse(cr.getCheck_date()));
				   critical.setExam_item_id(item_id);
				   critical.setItem_code(cr.getItem_code()+"");
				   critical.setIs_active("Y");
				   pm.save(critical);
				   return "ok-成功";
			   	}else{
			   		return "error-操作失败 ！！";
			   	}
			}else if (dep_category.equals("21")) {
				String vsql="select  ci.id as charging_item_id ,ci.dep_id as dept_id ,v.exam_result,"
						+ "e.join_date as check_date,v.exam_item_id  from  exam_info  e ,view_exam_detail  v, "
							+"examinfo_charging_item  eci , charging_item ci ,pacs_summary p " 
							+"where v.exam_num = e.exam_num and eci.exam_num = e.exam_num " 
							+"and ci.id = eci.charge_item_id  and v.pacs_id = p.id and eci.center_num = '"+center_num+"' "
							+"and   p.examinfo_sampleId = ci.sam_demo_id and v.id="+vid;
				List<CriticalDTO> li = this.jqm.getList(vsql, CriticalDTO.class);
				if (li.size() > 0) {
					   CriticalDTO cr = li.get(0);
					   Critical critical = new Critical();
					   critical.setCharging_item_id(cr.getCharging_item_id()+"");
					   critical.setDept_id(cr.getDept_id());
					   critical.setExam_result(cr.getExam_result());
					   critical.setExam_info_id(examinfo_id);
					   critical.setCreater(userId);
					   critical.setCreate_time(DateTimeUtil.parse());
					   critical.setData_source(1);
					   critical.setDone_flag(0);
					   critical.setCheck_date(DateTimeUtil.parse(cr.getCheck_date()));
					   critical.setExam_item_id(cr.getExam_item_id());
					   critical.setIs_active("Y");
					   pm.save(critical);
					   return "ok-成功";
				   	}else{
				   		return "error-操作失败 ！！";
				   	}
				
			}else if (dep_category.equals("131")) {
				String lissql = "select ci.id as charging_item_id ,erd.exam_result , e.join_date as check_date ,ci.dep_id as dept_id from exam_result_detail  erd , charging_item ci  , exam_info e  where   "
							+" ci.item_code = erd.charging_item_code  and e.exam_num = erd.exam_num  and erd.exam_info_id = "+examinfo_id+" and erd.exam_item_id ="+item_id ;
				List<CriticalDTO> li = this.jqm.getList(lissql, CriticalDTO.class);
				if (li.size() > 0) {
					   CriticalDTO cr = li.get(0);
					   Critical critical = new Critical();
					   critical.setCharging_item_id(cr.getCharging_item_id()+"");
					   critical.setDept_id(cr.getDept_id());
					   critical.setExam_result(cr.getExam_result());
					   critical.setExam_info_id(examinfo_id);
					   critical.setCreater(userId);
					   critical.setCreate_time(DateTimeUtil.parse());
					   critical.setData_source(1);
					   critical.setDone_flag(0);
					   critical.setCheck_date(DateTimeUtil.parse(cr.getCheck_date()));
					   critical.setExam_item_id(item_id);
					   critical.setIs_active("Y");
					   this.pm.save(critical);
					   return "ok-成功";
				   	}else{
				   		return "error-操作失败 ！！";
				   	}
			}
			return "ok-成功";
		}
		
		
	}

	@Override
	public String delCritical(Long examinfo_id, Long item_id, String dep_category, long userid) throws ServiceException {
		String sql = "select * from exam_Critical_detail where  exam_info_id = "+examinfo_id+" and  exam_item_id = "+item_id+"  and is_active = 'Y'";
		if (dep_category.equals("21") ) {
			String sq = "select  ci.id as charging_item_id from view_exam_detail   ved , pacs_summary  p , "
					+ "charging_item ci where p.examinfo_sampleId = ci.sam_demo_id and p.id = ved.pacs_id and ved.id = "+item_id;
			List<CriticalDTO> list = this.jqm.getList(sq, CriticalDTO.class);
			if (list.size() > 0) {
				item_id = list.get(0).getCharging_item_id();
				 sql = "select * from exam_Critical_detail where  exam_info_id = "+examinfo_id+" and  charging_item_id = "+item_id+"  and is_active = 'Y'";
			}else{
				return "error-操作失败 ！！";
			}
		}
		List<Critical> list = this.jqm.getList(sql, Critical.class);
		if (list.size() <= 0 ) {
			return "error-此记录不存在请核实！！";
		}else{
			Critical critical= (Critical) list.get(0);
			if (critical.getData_source() == 1) {
				if (critical.getDone_flag() == 0) {
					critical.setIs_active("N");
					this.pm.update(critical);
					return "ok-撤销成功！！";
				}else{
					return "error-已处理不能撤销！！";
				}
				
			}else{
				return "error-系统生成的危机值不能撤销！！";
			}
		}
		
	}
    
	@Override
	public void saveExamCriticalLog(ExamCriticalLog criticalLog) throws ServiceException {
		 this.pm.save(criticalLog);
	}
	
	@Override
	public PageReturnDTO criticalLogList(CriticalModel model, int rows, int page) throws ServiceException {
		String sql ="select  e.id ,e.create_time as check_date ,u.chi_name as user_name ,e.note , e.status from  exam_Critical_log e ,user_usr u  where  e.creater = u.id and   e.exam_Critical_id = "+model.getId()+" and e.isActive = 'Y' order by e.create_time";
		PageSupport map = (PageSupport) this.jqm.getList(sql,page,rows,CriticalDTO.class);
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
	public String addCriticalDBGJ(UserDTO user, CriticalModel model) throws ServiceException {
		String  sql = "select id , exam_num,join_date  from exam_info where  is_Active = 'Y' and  exam_num = '"+model.getExam_num()+"'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		if(list.size() > 0){
			Critical critical = new Critical();
			critical.setExam_info_id(list.get(0).getId());
			critical.setExam_num(list.get(0).getExam_num());
			critical.setExam_result(model.getNote());
			critical.setCreater(user.getUserid());
			critical.setCreate_time(DateTimeUtil.parse());
			critical.setData_source(0);
			critical.setDone_flag(0);
			critical.setCheck_date(DateTimeUtil.parse(list.get(0).getJoin_date()));
			critical.setCritical_type(model.getCritical_type());
			critical.setIs_active("Y");;
			this.pm.save(critical);
			return "ok-添加成功。。。";
		}else{
			return "error-体检信息不存在 ,请重新选择。。";
		}
		
	}

	@Override
	public List<Critical> getCriticalListByExamNum(String exam_num) throws ServiceException {
		String sql = " select e.id,exam_result,u.chi_name as creater ,CONVERT(varchar(20),e.create_time,20)  as create_time  ,e.done_flag ,"
				    +" CONVERT(varchar(20),e.done_date,20) as done_date , e.note ,e.critical_type, dd.data_name as critical_type_s  from exam_Critical_detail  e "
				    +" left join user_usr u on u.id = e.creater  "
				    +" left join data_dictionary dd on dd.id = e.critical_type "
				    + " where e.exam_num = '"+exam_num+"'  and e.is_active = 'Y'";
		List<Critical> list = this.jqm.getList(sql, CriticalDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getCriticalList(CriticalModel model, int pageno, int pagesize, UserDTO user)
			throws ServiceException {

		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私资源  Y 有 N 无
		String sql =" select distinct c.id,t.arch_num,e.exam_num,dbo.fun_CharToStar(e.exam_num,'name',t.user_name,'"+isprivateflag+"') as user_name,d.dep_name,i.item_name, "
				 +" c.check_date,c.note,u.chi_name as check_doctor,c.exam_result,c.done_flag,c.done_date ,ei.item_name as examination_item_name , "
				 + "uu.chi_name as creater , c.create_time,dkl.disease_name ,t.sex,e.age,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.company,c.critical_type, dd.data_name as critical_type_s  "   
				 +" from customer_info t,exam_Critical_detail c " 
				 +" left join exam_info e on c.exam_num=e.exam_num "
				 +" left join department_dep d on c.dept_id=d.id "
				 +" left join department_vs_center de on c.dept_id=de.dep_id "
				 + "left join user_usr u on c.userid=u.id "
				 + "left join user_usr uu on c.creater=uu.id "
				 +" left join charging_item i on c.charging_item_code=i.item_code "
				 +" left join examination_item  ei on ei.item_num = c.item_code"
				 +" left join disease_knowloedge_lib dkl on dkl.charging_item_code = c.charging_item_code"
				 +" left join data_dictionary dd on dd.id = c.critical_type "
				 +" where e.customer_id=t.id and c.is_active = 'Y'  and de.center_num ='"+user.getCenter_num()+"'";
			if(model.getDone_flag() != 3){
				sql += " and done_flag= '"+model.getDone_flag()+"'";
			}
			if (model.getStartCheckDate() != null && !"".equals(model.getStartCheckDate())) {// 体检开始日期
				sql += " and c.check_date >= '" + model.getStartCheckDate() + " 00:00:00.000'";
			} 
			if (model.getEndCheckDate() != null && !"".equals(model.getEndCheckDate())) {// 体检结束日期
				sql += " and c.check_date < '" + model.getEndCheckDate() + " 23:59:59.999'";
			}
			if (!"".equals(model.getCheck_doctor()) && model.getCheck_doctor() != null) {
				sql += " and u.chi_name like '%"+model.getCheck_doctor()+"%'";
			}
			if (!"".equals(model.getData_source()) && model.getData_source() != null) {
				sql += " and c.data_source = '"+model.getData_source()+"'";
			}
			if (!"".equals(model.getDep_category()) && model.getDep_category() != null) {
				sql+=" and d.dep_category  = '"+model.getDep_category()+"'";
			}
			if (!"".equals(model.getStartDone_date()) && model.getStartDone_date() != null) {
				sql+=" and c.done_date >= '" + model.getStartDone_date() + " 00:00:00.000'";
			}
			if (!"".equals(model.getEndDone_date()) && model.getEndDone_date() != null) {
				sql+=" and c.done_date < '" + model.getEndDone_date() + " 23:59:59.999'";
			}
			if (!"".equals(model.getExam_result()) && model.getExam_result() != null) {
				sql+=" and c.exam_result like '%"+model.getExam_result()+"%'";
			}
			if (!"".equals(model.getExam_num()) && model.getExam_num()!= null) {
				sql+="and e.exam_num = '"+model.getExam_num().trim()+"'";
			}
			if(!"".equals(model.getCritical_type()) && model.getCritical_type()!= null){
				sql+="and c.critical_type = '"+model.getCritical_type()+"'";
			}
			sql += " order by c.check_date desc";
		PageSupport map = (PageSupport) this.jqm.getList(sql,pagesize,pageno,CriticalDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
	
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
			for (int i = 0; i < map.getList().size(); i++) {
				CriticalDTO criticalDTO  = (CriticalDTO) map.getList().get(i);
				model.setExam_num(criticalDTO.getExam_num());
				String old_results="";
				List<CrmVisitRecordDTO> criticalHandleListByExamNum = getCriticalHandleListByExamNum(criticalDTO.getExam_num(),model.getCritical_tactics_num());
				if (criticalHandleListByExamNum.size() > 0) {
					for (int y = 0; y < criticalHandleListByExamNum.size(); y++) {
						CrmVisitRecordDTO crmVisitRecordDTO = (CrmVisitRecordDTO) criticalHandleListByExamNum.get(y);
						old_results+=crmVisitRecordDTO.getDoctorname()+"--"+crmVisitRecordDTO.getVisit_date()+"--"+crmVisitRecordDTO.getCustomer_feedback()+"    , ";
					}
				}
				criticalDTO.setOld_results(old_results);
				map.getList().set(i, criticalDTO);
			}
		}
		return webrole;
	
	}

	@Override
	public List<Critical> getCriticalByExamNum(String exam_num) throws ServiceException {
		String sql = "select  * from exam_Critical_detail  where exam_num = '"+exam_num+"'  and is_active = 'Y'  and done_flag = '0'";
		List<Critical> list = this.jqm.getList(sql, Critical.class);
		return list;
	}

	@Override
	public PageReturnDTO getCriticalHandleListByExamNum(CriticalModel model, int rows, int page)
			throws ServiceException {
		
		String sql ="select  customer_feedback,u.chi_name as doctorname , CONVERT(varchar(20),cvr.visit_date,20) as visit_date "
					+"	from crm_visit_record cvr  "
					+ "left join user_usr u on u.id = cvr.visit_doctor_id "
					+ "	left join crm_visit_plan cvp on cvp.visit_num = cvr.visit_num  "
					+ "where cvr.exam_num = '"+model.getExam_num()+"'";
		if(!"".equals(model.getCritical_tactics_num())  && model.getCritical_tactics_num() != null){
			sql += " and cvp.tactics_num= '"+model.getCritical_tactics_num()+"'" ; 
		}
		PageSupport map = (PageSupport) this.jqm.getList(sql,page,rows,CrmVisitRecordDTO.class);
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
	public String delCriticalById(long id,UserDTO user) throws ServiceException {
		List<Critical> list = this.qm.find("from Critical where id = "+id);
		if(list.size() > 0){
			Critical  critical = list.get(0);
			if(critical.getDone_flag() == 1){
				return "error-已处理不能删除!";
			}else{
				if(user.getUserid() != critical.getCreater()){
					return "error-只能删除本人添加的，操作失败!";
				}
				critical.setIs_active("N");
				this.pm.update(critical);
				return "ok-删除成功!";
			}
		}else{
			return "error-操作错误!";	
		}
	}
	
	@Override
	public List<CrmVisitRecordDTO> getCriticalHandleListByExamNum(String  exam_num, String  critical_tactics_num)
			throws ServiceException {
		
		String sql ="select  customer_feedback,u.chi_name as doctorname , CONVERT(varchar(20),cvr.visit_date,20) as visit_date "
					+"	from crm_visit_record cvr  "
					+ "left join user_usr u on u.id = cvr.visit_doctor_id "
					+ "	left join crm_visit_plan cvp on cvp.visit_num = cvr.visit_num  "
					+ "where cvr.exam_num = '"+exam_num+"'";
		if(!"".equals(critical_tactics_num)  && critical_tactics_num != null){
			sql += " and cvp.tactics_num= '"+critical_tactics_num+"'" ; 
		}
		
		List<CrmVisitRecordDTO> list = this.jqm.getList(sql,CrmVisitRecordDTO.class);
		return list;
	}
	
	@Override
	public int getExamIdByExamNum(String exam_num) throws ServiceException {
		String sql="";
		int id=0;
		Connection connection = null;
		Statement statement = null;
		sql=" select id from exam_info where exam_num= '"+exam_num+"' ";
		
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				id=rs.getInt("id");
			}
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
	@Override
	public Critical queryCritialById(int id) throws ServiceException {
		String sql="";
		Connection connection = null;
		Statement statement = null;
		Critical cri = new Critical();
		sql=" select * from exam_Critical_detail where id= '"+id+"' ";
		
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				cri.setId(rs.getInt("id"));
				cri.setExam_result(rs.getString("exam_result"));
				cri.setDisease_id(rs.getInt("disease_id"));
				cri.setData_source(1);
				cri.setCritical_class_parent_id(rs.getInt("critical_class_parent_id"));
				cri.setCritical_class_id(rs.getInt("critical_class_id"));
				cri.setCritical_class_level(rs.getInt("critical_class_level"));
				cri.setCritical_suggestion(rs.getString("critical_suggestion"));
				cri.setDisease_num(rs.getString("disease_num"));
			}
		} catch (SQLException e){
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return cri;
	}
	
	@Override
	public void delCriticalDetail(Critical critical) throws ServiceException {
		this.pm.remove(critical);
	}
	
	@Override
	public int getCountByClass(String exam_num, long critical_class_parent_id, long critical_class_id,
			long critical_class_level,long critical_id) throws ServiceException {
		int counts=0;
		String sql=" select ecd.id from exam_Critical_detail ecd where ecd.exam_num = '"+exam_num+"' and ecd.critical_class_parent_id='"+critical_class_parent_id+"' and ecd.critical_class_id='"+critical_class_id+"' and ecd.critical_class_level='"+critical_class_level+"' ";
		List<CriticalDTO> list = this.jqm.getList(sql, CriticalDTO.class);
		if(list.size() > 0){
			if(critical_id > 0){
				if(critical_id != list.get(0).getId()){
					counts = 1;
				}
			}else{
				counts = 1;
			}
		}
		return counts;
	}
	
	@Override
	public PageReturnDTO queryPageCritrical(CriticalModel model, int pageno, int pagesize,String sort,String order) {
		String sql= " select e.exam_num,c.user_name,ecd.exam_result,convert(varchar(50),ecd.done_date,23) as done_date,ecd.done_flag,ecd.note,ecd.create_time, "
		   +" ci.com_name as company_name,u.chi_name as chi_name from exam_Critical_detail ecd left join exam_info e on ecd.exam_info_id=e.id left join "
		   +"customer_info c on e.customer_id=c.id left join company_info ci on e.company_id=ci.id left join user_usr u on ecd.creater=u.id where 1=1 ";
		if(!"".equals(model.getExam_num())){
			sql += " and e.exam_num = '"+model.getExam_num()+"'";
		}
		if(!"".equals(model.getUser_name())){
			sql += " and c.user_name like '"+model.getUser_name()+"%'";
		}
		if(!"".equals(model.getDone_flag())){
			sql += " and ecd.done_flag = '"+model.getDone_flag()+"'";
		}
		if(!"".equals(model.getTime1())){
			sql += " and ecd.done_date >= '"+model.getTime1()+" 00:00:00.000'";
		}
		if(!"".equals(model.getTime2())){
			sql += " and ecd.done_date <= '"+model.getTime2()+" 23:59:59.000'";
		}
		if(!"".equals(model.getTime3())){
			sql += " and ecd.create_time >= '"+model.getTime3()+" 00:00:00.000'";
		}
		if(!"".equals(model.getTime4())){
			sql += " and ecd.create_time <= '"+model.getTime4()+" 23:59:59.000'";
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}
		System.out.println(sql);
		PageSupport map = this.jqm.getList(sql,pagesize, pageno,CriticalDTO.class);
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
	public CriticalDTO getCritricalById(long id) throws ServiceException{
		String sql = " SELECT e.exam_info_id,dd.data_code_children,e.exam_num FROM exam_Critical_detail e left join data_dictionary dd on dd.id = e.critical_class_level  AND  e.is_active='Y' WHERE e.id="+id;
		List<CriticalDTO> li = this.jqm.getList(sql,CriticalDTO.class);
		if(li!=null && li.size()>0) {
			return li.get(0);
		} else {
			return null;
		}
	}

	@Override
	public String updateCriticalVisitPlan(CriticalModel model, UserDTO user) throws ServiceException {
		String msg = "";
		//危急值处理批量
		List<Critical> cli = new ArrayList<Critical>();
		Critical c = new Critical();
		Set<Integer> set = new HashSet<Integer>();
		if(!StringUtils.isEmpty(model.getIds())) {
			String[] ids = model.getIds().split(",");
			for(int i = 0 ; i < ids.length ; i++) {
				Critical c1 = new Critical();
				c1 = this.queryById(Integer.parseInt(ids[i]));
				c1.setNote(model.getNote());
				c1.setDone_date(DateTimeUtil.parse());
				c1.setUserid(user.getUserid());
				c1.setDone_flag(model.getDone_flag());
				c1.setGive_notice_type(model.getGive_notice_type());
				this.updateCritical(c1);
				model.setId(Integer.parseInt(ids[0]));
				cli.add(c1);
				set.add(c1.getCritical_class_level());
			}
		} else {
			c=this.queryById(model.getId());
			c = this.queryById(model.getId());
			c.setNote(model.getNote());
			c.setDone_date(DateTimeUtil.parse());
			c.setUserid(user.getUserid());
			c.setDone_flag(model.getDone_flag());
			c.setGive_notice_type(model.getGive_notice_type());
			this.updateCritical(c);
			set.add(c.getCritical_class_level());
		}
		ExamCriticalLog criticalLog = new ExamCriticalLog();//危急值处理结果记录
		Critical ccl = new Critical();
		if(cli.size() > 0){
			ccl = cli.get(0);
		}else{
			ccl = c;
		}
		if(ccl != null){
			criticalLog.setExam_Critical_id(ccl.getId());
			criticalLog.setCreater(user.getUserid());
			criticalLog.setCreate_time(DateTimeUtil.parse());
			criticalLog.setNote(model.getNote());
			criticalLog.setIsActive("Y");
			criticalLog.setStatus(1);
			this.saveExamCriticalLog(criticalLog);
		}
		
		//危急值根据策略生成计划
		CriticalDTO cri = this.getCritricalById(model.getId());
		ExamInfo examInfo = this.examInfoService.findExamInfo(cri.getExam_num());
		
		try {
			for (Integer level : set) {
				 List<CrmPlanTactics> li = this.qm.find("from CrmPlanTactics  where level = "+level+" and  tactics_num not  in (select  tactics_num from CrmVisitPlan where exam_num = '"+examInfo.getExam_num()+"' )");//是否已存在计划
				if (li.size() > 0) {
					for (CrmPlanTactics crmPlanTactics : li) {
						 List<CrmPlanTacticsDetail> list = this.qm.find("from CrmPlanTacticsDetail where tactics_num = '"+crmPlanTactics.getTactics_num()+"'");
						 ExamInfoDTO examInfoForexamNum = customerInfoService.getExamInfoForexamNum(examInfo.getExam_num());
						 String visit_num = GetNumContral.getInstance().getParamNum("visit_num",user.getCenter_num());
						if(list.size() > 0){
							CrmVisitPlan crmVisitPlan = new CrmVisitPlan();//计划主表
							crmVisitPlan.setExam_num(examInfo.getExam_num());
							crmVisitPlan.setCreate_time(DateTimeUtil.parse());
							crmVisitPlan.setArch_num(examInfoForexamNum.getArch_num());
							crmVisitPlan.setTactics_num(crmPlanTactics.getTactics_num());
							crmVisitPlan.setVisit_status("1");
							crmVisitPlan.setVisit_num(visit_num);
							crmVisitPlan.setPlan_doctor_id(0L);
							crmVisitPlan.setPlan_visit_date(new Date());
							crmVisitPlan.setVisit_content("");
							this.crmVisitPlanService.addCrmVisitPlan(crmVisitPlan);
							
							for (CrmPlanTacticsDetail crmPlanTacticsDetail : list) {
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date  currdate = format.parse(examInfo.getJoin_date());
										Calendar ca = Calendar.getInstance();
										ca.setTime(currdate);
								        ca.add(Calendar.DATE, crmPlanTacticsDetail.getDistancedate());// num为增加的天数，可以改变的
								        currdate = ca.getTime();
								        ca.setTime(currdate);
								        if(ca.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
								        	 ca.add(Calendar.DATE, 2);
								        }else if(ca.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
								        	ca.add(Calendar.DATE, 1);
								        }
								        currdate = ca.getTime();
								         
										CrmVisitRecord crmVisitRecord = new CrmVisitRecord();
										crmVisitRecord.setArch_num(examInfoForexamNum.getArch_num());
										crmVisitRecord.setExam_num(examInfo.getExam_num());
										crmVisitRecord.setVisit_num(visit_num);
										crmVisitRecord.setVisit_type("");
										crmVisitRecord.setVisit_notices(crmPlanTacticsDetail.getNotices());
										crmVisitRecord.setVisit_doctor_id(crmPlanTacticsDetail.getPlan_doctor_id());
										crmVisitRecord.setVisit_date(currdate);
										crmVisitRecord.setTactics_detail_id(crmPlanTacticsDetail.getId());
										crmVisitRecord.setHealth_suggest("");
										crmVisitRecord.setCustomer_feedback("");
										crmVisitRecord.setRecord_status("0");
										this.crmVisitRecordService.addCrmVisitRecord(crmVisitRecord);
										//批量维护
										if(cli!=null && cli.size()>0) {
											for( int i = 0 ; i < cli.size() ; i++) {
												CriticalVisitPlan cvp = new CriticalVisitPlan();
												cvp.setCritical_id(cli.get(i).getId());
												cvp.setPlan_visit_num(crmVisitRecord.getId());
												pm.save(cvp);
											}
										//单个危急值维护
										} else {
											//危急值处理与计划关系
											CriticalVisitPlan cvp = new CriticalVisitPlan();
											cvp.setCritical_id(c.getId());
											cvp.setPlan_visit_num(crmVisitRecord.getId());
											pm.save(cvp);
										}
							}
							msg =  "策略计划添加成功。。";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("生成计划失败") ;
		}
		return msg;
	}

	@Override
	public CriticalDTO getCritricalExamInfoById(CriticalModel model) throws ServiceException {
		StringBuffer sql = new StringBuffer(" SELECT ecd.id,ecd.give_notice_type,ecd.done_flag,ecd.note,ci.user_name,e.exam_num,");
		sql.append(" ci.sex,e.phone,e.age FROM exam_Critical_detail ecd  inner join  exam_info  e  on  e.id = ecd.exam_info_id ");
		sql.append(" inner join customer_info  ci  on ci.id = e.customer_id  where   e.is_active='Y' and ");
		sql.append(" ecd.is_active='Y' and ci.is_Active='Y' AND ecd.id="+model.getId());
		List<CriticalDTO> li = this.jqm.getList(sql.toString(), CriticalDTO.class);
		if(li!=null && li.size()>0) {
			return li.get(0);
		}
		return null;
	}
}
