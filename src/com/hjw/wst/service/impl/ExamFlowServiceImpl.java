package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.sql.RowSet;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.crm.domain.CrmPlanTactics;
import com.hjw.crm.domain.CrmPlanTacticsDetail;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.ExamFlowLogDTO;
import com.hjw.wst.DTO.ExamFlowRemakDTO;
import com.hjw.wst.DTO.ExamFlowTotalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Customer_Type;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.ExamFlow;
import com.hjw.wst.domain.ExamFlowConfig;
import com.hjw.wst.domain.ExamFlowLog;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamInfoRecyclingGuid;
import com.hjw.wst.domain.ExamSummary;
import com.hjw.wst.domain.ExamSummaryReject;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.SystemInforms;
import com.hjw.wst.domain.SystemInforms_user;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.model.ChargingItemModel;
import com.hjw.wst.model.ExamFlowModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.Customer_TypeService;
import com.hjw.wst.service.ExamFlowService;
import com.hjw.wst.service.ExamInfoRecyclingGuidService;
import com.hjw.wst.service.examInfoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ExamFlowServiceImpl implements ExamFlowService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private examInfoService examInfoService;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private ExamInfoRecyclingGuidService examInfoRecyclingGuidService;
	private CustomerInfoService customerInfoService; 
	private CrmVisitPlanService crmVisitPlanService;
	private CrmVisitRecordService crmVisitRecordService;

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

	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}

	public void setExamInfoRecyclingGuidService(ExamInfoRecyclingGuidService examInfoRecyclingGuidService) {
		this.examInfoRecyclingGuidService = examInfoRecyclingGuidService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setCrmVisitPlanService(CrmVisitPlanService crmVisitPlanService) {
		this.crmVisitPlanService = crmVisitPlanService;
	}

	public void setCrmVisitRecordService(CrmVisitRecordService crmVisitRecordService) {
		this.crmVisitRecordService = crmVisitRecordService;
	}

	@Override
	public ExamFlow saveExamFlow(ExamFlow ctms) throws ServiceException {
		this.pm.save(ctms);
		return ctms;
	}

	/**
	 * 
	     * @Title: getExamFlowForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public PageReturnDTO getExamFlowForNum(String exam_num,String username,String time1,String time2,long fromacc,String types,String center_num,int pagesize, int pageno,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,"
				+ "dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,a.arch_num,"
						+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,"
								+ "c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,c.others,c.status,c.exam_type"
				+ ",c.employeeID,c.register_date,c.join_date,c.exam_times,fromusr.chi_name as batch_name"
				+ ",tousr.chi_name as picture_Path,flow.types as actiontype,flow.remark as remarke,"
				+ "flow.fromacc_date as counter_check,flow.toacc_date as guide_nurse ");
		sb.append(" from customer_info a ,exam_info c,exam_flow flow  ");
		sb.append(" left join user_usr fromusr on fromusr.id=flow.fromacc ");
		sb.append(" left join user_usr tousr on tousr.id=flow.toacc  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.id=flow.exam_id ");		
		sb.append(" and c.is_Active='Y' ");	
		sb.append(" and c.center_num = '"+center_num+"' ");	
		if(!StringUtil.isEmpty(exam_num)){
			sb.append(" and c.exam_num = '" + exam_num + "'");
		}
		if(!StringUtil.isEmpty(username)){
			sb.append(" and a.user_name = '" + username + "'");
		}
		if((!StringUtil.isEmpty(time1))&&(!StringUtil.isEmpty(time2))){
			sb.append(" and ((flow.fromacc_date>='"
		    +time1+" 00:00:00' and flow.fromacc_date<='"
			+time2+" 23:59:59') or (flow.toacc_date>='"
		    +time1+" 00:00:00' and flow.toacc_date<='"+time2+" 23:59:59')) ");
		}
		
		if("9".equals(types)){
			if(fromacc>0){
				sb.append(" and (( flow.fromacc="+fromacc+" and flow.types='0' ) or ( flow.toacc="+fromacc+" and flow.types='1' ) ) ");
			}	
		}else if("0".equals(types)){
			if(fromacc>0){
				sb.append(" and flow.fromacc="+fromacc+" and flow.types='0'  ");
			}else{
				sb.append(" and flow.types='0'  ");
			}
		}else if("1".equals(types)){
			if(fromacc>0){
				sb.append(" and flow.toacc="+fromacc+" and flow.types='1'  ");
			}else{
				sb.append(" and flow.types='1'  ");
			}
		}

		sb.append(" and flow.center_num='"+center_num+"' order by  flow.fromacc_date,flow.toacc_date,flow.id ");	

		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		List<ExamInfoUserDTO> eulist = new ArrayList<ExamInfoUserDTO>();
		if((map!=null)&&(map.getList().size()>0)){
			webrole.setTotal(map.getRecTotal());
			for(int i=0;i<map.getList().size();i++){
			ExamInfoUserDTO eu = new ExamInfoUserDTO();
			eu= (ExamInfoUserDTO)map.getList().get(i);
			if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){				
				eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
				if(eu.getRegister_date().indexOf("1900-01-01")>=0)
				{
					eu.setRegister_date("");
				}
			}
			
			if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
				eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
				if(eu.getJoin_date().indexOf("1900-01-01")>=0)
				{
					eu.setJoin_date("");
				}
			}
			eulist.add(eu);
			}
			webrole.setRows(eulist);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamFlowInfoForNum(String exam_num,String typess,long userId,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限 Y 有 N 无
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,c.others,c.status,c.exam_type"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,"
				+ "c.register_date,c.join_date,c.exam_times,e.toacc_date as remarke,u.chi_name ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_num = c.exam_num  ");
		sb.append(" left join exam_flow e on e.center_num = '"+user.getCenter_num()+"' AND e.exam_id = c.id and e.toacc = "+userId+" "
				+ "left join user_usr u on e.toacc = u.id");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");		
		sb.append(" and c.exam_num = '" + exam_num + "' order by e.toacc_date desc ");	

		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, ExamInfoUserDTO.class);
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if((map!=null)&&(map.getList().size()>0)){
			eu= (ExamInfoUserDTO)map.getList().get(0);
			if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
				eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
				if(eu.getRegister_date().indexOf("1900-01-01")>=0)
				{
					eu.setRegister_date("");
				}
			}
			
			if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
				eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
				if(eu.getJoin_date().indexOf("1900-01-01")>=0)
				{
					eu.setJoin_date("");
				}
			}
		}
		return eu;
	}
	
	
	/**
	 * 
	     * @Title: getExamFlowForNumTotal   
	     * @Description: 统计个人  
	     * @param: @param userid
	     * @param: @param startdate
	     * @param: @param enddate
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamFlowTotalDTO>      
	     * @throws
	 */
	public PageReturnDTO getExamFlowForNumTotal(long userid,String actiontype,String startdate,String enddate,UserDTO user) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		if("0".equals(actiontype)){
			sb.append("select f.datetimes,COUNT(*) as counts from (  select CONVERT(varchar(100),a.fromacc_date, 23) as datetimes,"
				+ "a.exam_id from exam_flow a where a.center_num = '"+user.getCenter_num()+"' and a.fromacc="+userid+" and a.types='"+actiontype+"' and  ((a.fromacc_date>='"+startdate+" 00:00:00' and a.fromacc_date<='"+enddate+" 23:59:59') or (a.toacc_date>='"+startdate+" 00:00:00' and a.toacc_date<='"+enddate+" 23:59:59')) "
				+ "group by CONVERT(varchar(100),a.fromacc_date, 23),a.exam_id) f group by f.datetimes");
		}else if("1".equals(actiontype)){
			sb.append("select f.datetimes,COUNT(*) as counts from ( select CONVERT(varchar(100),a.toacc_date, 23) as datetimes,"
				+ "a.exam_id from exam_flow a where a.center_num = '"+user.getCenter_num()+"'  and a.toacc="+userid+" and a.types='"+actiontype+"'  and  ((a.fromacc_date>='"+startdate+" 00:00:00' and a.fromacc_date<='"+enddate+" 23:59:59') or (a.toacc_date>='"+startdate+" 00:00:00' and a.toacc_date)<='"+enddate+" 23:59:59')) "
				+ "group by CONVERT(varchar(100),a.toacc_date, 23),a.exam_id) f group by f.datetimes");
		}		

		PageSupport map = this.jqm.getList(sb.toString(),1, 100000, ExamFlowTotalDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getExamFlowForNumTotalall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param startdate
	     * @param: @param enddate
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public int getExamFlowForNumTotalall(long userid,String actiontype,String startdate,String enddate,UserDTO user) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		
		if("0".equals(actiontype)){
			sb.append("select count(*) as counts from (select distinct a.exam_id from exam_flow a where a.center_num = '"+user.getCenter_num()+"' and a.fromacc="+userid+" and a.types='"+actiontype+"' "
					+ "and ((a.fromacc_date>='"+startdate+" 00:00:00' and a.fromacc_date<='"+enddate+" 23:59:59') or (a.toacc_date>='"+startdate+" 00:00:00' and a.toacc_date<='"+enddate+" 23:59:59'))  )f ");	
		}else if("1".equals(actiontype)){
			sb.append("select count(*) as counts from (select distinct a.exam_id from exam_flow a where a.center_num = '"+user.getCenter_num()+"' and a.toacc="+userid+" and a.types='"+actiontype+"' "
					+ "and ((a.fromacc_date>='"+startdate+" 00:00:00' and a.fromacc_date<='"+enddate+" 23:59:59') or (a.toacc_date>='"+startdate+" 00:00:00' and a.toacc_date<='"+enddate+" 23:59:59'))  )f ");	
		}
		
        int counts=0;
        Connection connection = null;
        try {
			connection = this.jqm.getConnection();	
			ResultSet rs = connection.createStatement().executeQuery(sb.toString());
			if(rs.next()){
				// 读取记录数
				counts=rs.getInt("counts");					
			}
			rs.close();
        }catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return counts;
	}
	
	
	/**
	 * 
	 * @Title: queryExamInfo @Description: 获取所有收费项目信息 @param: @param
	 * examNum @param: @return @param: @throws ServiceException @return:
	 * List<ExaminfoChargingItemDTO> @throws
	 */
	public List<ExaminfoChargingItemDTO> queryExamInfo(String examNum,String types, String center_num) throws ServiceException {
		String sql = " select ec.id,e.exam_num,c.id as charge_item_id,c.item_code,c.item_name,d.dep_num,d.dep_name,ec.exam_status, ec.tixing_flag, "
				+ " CONVERT(varchar(100),ec.final_exam_date,23) as create_time,c.sam_demo_id as sample_id,ec.examinfo_id,d.dep_category,ec.calculation_amount "
				+ " from exam_info e,charging_item c,department_dep d,examinfo_charging_item ec left join user_usr u on u.id = ec.creater "
				+ " where ec.exam_num = e.exam_num and ec.charge_item_id = c.id   and   c.item_category <> '耗材类型' and c.dep_id = d.id and ec.pay_status <> 'M' "
				+ "  and ec.isActive = 'Y' and ec.change_item <> 'C' and ec.center_num = '"+center_num+"' and e.is_Active='Y' and e.exam_num = '" + examNum+ "' "; 
		if(!"2".equals(types)){
			sql += " and ec.exam_status in ('N','C') ";
		}
		sql += " order by ec.exam_status,d.seq_code,c.item_seq ";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);

		for (ExaminfoChargingItemDTO examinfoitem : list) {
			if ("131".equals(examinfoitem.getDep_category())) {
				String sql1 = "select * from sample_exam_detail s where s.exam_num = '"
						+ examinfoitem.getExam_num()+ "' and s.sample_id = " + examinfoitem.getSample_id();
				List<SampleExamDetailDTO> listsample = this.jqm.getList(sql1, SampleExamDetailDTO.class);
				if (listsample.size() > 0) {
					examinfoitem.setSample_status(listsample.get(0).getStatus());
					examinfoitem.setSample_statuss(listsample.get(0).getStatus_y());
				}
			}
		}
		return list;
	}
	
	/**
	 * 
	     * @Title: flowupdateplyq   
	     * @Description: 导检单回收 项目延期 
	     * @param: @param examid
	     * @param: @param dates
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowupdateplyq(String exam_num,long itemid, UserDTO user) throws ServiceException {		
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			ExaminfoChargingItem exif = this.examInfoService.loadExamInfo(itemid);
			exif.setExam_status("D");
			//exif.setFinal_exam_date(DateTimeUtil.parse0(dates));
			this.examInfoService.deleteExam(exif);
			message = "ok-操作完成";
		}
	
		return message;
	}
	
	/**
	 * 
	     * @Title: flowupdateExam   
	     * @Description: 恢复
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowupdateExam(String exam_num,long itemid, UserDTO user) throws ServiceException {
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			ExaminfoChargingItem exif = this.examInfoService.loadExamInfo(itemid);
			exif.setExam_status("N");
			this.examInfoService.deleteExam(exif);
			message = "ok-操作完成";
		}
		return message;
	}
	/**
	 * 
	     * @Title: insertExamdelete   
	     * @Description: 弃检   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowdeleteExam(String exam_num,long itemid, UserDTO user) throws ServiceException {
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			ExaminfoChargingItem exif = this.examInfoService.loadExamInfo(itemid);
			exif.setExam_status("G");
			this.examInfoService.deleteExam(exif);
			message = "ok-操作完成";
		}
		return message;
	}
	
	public List<WebUserInfo> getZDSDoctors(long itemid, String center_num) throws ServiceException {
		String sql = "select distinct uu.chi_name,uu.id  from  "
				+ "examinfo_charging_item eci,charging_item ci, "
				+ "dep_user du,user_usr uu "
				+ "where eci.charge_item_id=ci.id and ci.dep_id=du.dep_id "
				+ "and du.user_id=uu.id and eci.id='"+itemid+"' and eci.center_num = '"+center_num+"'";
		List<WebUserInfo> list = this.jqm.getList(sql, WebUserInfo.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getZDSDoctors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param itemid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebUserInfo>      
	     * @throws
	 */
	public String saveMessage(long fromid,long toid,String notice) throws ServiceException{
		SystemInforms si= new SystemInforms();
		si.setCreater(fromid);
		si.setCreate_time(DateTimeUtil.parse());
		si.setInform_content(notice);
		si.setIs_active("Y");
		si.setUpdate_time(DateTimeUtil.parse());
		si.setUpdater(fromid);
		this.pm.save(si);
		
		SystemInforms_user us=new SystemInforms_user();
		us.setCreate_time(DateTimeUtil.parse());
		us.setCreater(fromid);
		us.setInforms_id(si.getId());
		us.setUser_id(toid);
		us.setUpdater(fromid);
		us.setUpdate_time(DateTimeUtil.parse());
		us.setReader_flag(0);
		this.pm.save(us);
		return "ok-操作成功";
	}
	/**
	 * @Title: flowupdatetx
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param examid
	 * @param: @param dates
	 * @param: @param user
	 * @param: @return
	 * @param: @throws ServiceException      
	 * @return: String      
	 * @throws
	 */
	public String flowupdatetx(String exam_num,long itemid, UserDTO user) throws ServiceException {		
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			ExaminfoChargingItem exif = this.examInfoService.loadExamInfo(itemid);
			exif.setTixing_flag(1);
			this.examInfoService.updateExam(exif);
			message = "ok-操作完成";
		}
		return message;
	}
	
	//导检单核收  无需总检
	@Override
	public String guidewuxuzongjian(String exam_num) throws ServiceException {
		String sql = "update  exam_info  set wuxuzongjian=1 where exam_num='"+exam_num+"'";
		this.jdbcPersistenceManager.execSql(sql);
		return "ok-"+exam_num+"-";
	}
	
	//导检单核收  需要总检
	@Override
	public String guidexuyaozongjian(String exam_num) throws ServiceException {
		String sql = "update  exam_info  set wuxuzongjian=0 where exam_num='"+exam_num+"'";
		this.jdbcPersistenceManager.execSql(sql);
		return "ok-"+exam_num+"-";
	}
	
	@Override
	public void upd_getReportWay(ExamFlowModel model, UserDTO user) throws ServiceException {
		String sql = "update exam_info set getReportWay = '" +model.getGetReportWay()+ "' where exam_num="+model.getExam_num();
		this.jdbcPersistenceManager.execSql(sql);
	}
	@Override
	public void upd_emailAddress(ExamFlowModel model, UserDTO user) throws ServiceException {
		String sql = "update exam_info set email = '" +model.getEmail()+ "' where exam_num="+model.getExam_num();
		this.jdbcPersistenceManager.execSql(sql);
	}
	@Override
	public void upd_reportAddress(ExamFlowModel model, UserDTO user) throws ServiceException {
		String sql = "update exam_info set reportAddress = '" +model.getReportAddress()+ "' where exam_num='"+model.getExam_num()+"'";
		this.jdbcPersistenceManager.execSql(sql);
	}
	/**
	 * 
	     * @Title: flowexamLogForExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<ExamFlowLogDTO> flowexamLogForExamNum(String exam_num,UserDTO user)throws ServiceException{
		String sql=" select a.acccreater,a.sendcreater,a.senddate,a.flow_type,b.flow_name,x.chi_name as senduname,"
				+ "y.chi_name as accuname, a.flow_code from exam_flow_log a left join flow_config b on b.flow_code=a.flow_code "
				+ "left join user_usr x on x.id=a.sendcreater left join user_usr y on y.id=a.acccreater "
				+ "where a.exam_num='"+exam_num+"' and a.center_num = '"+user.getCenter_num()+"' order by a.senddate desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExamFlowLogDTO.class);
		List<ExamFlowLogDTO> eu = new ArrayList<ExamFlowLogDTO>();
		if ((map != null) && (map.getList().size() > 0)) {
			eu=map.getList();
		}
		return eu;
	}
	/**
	 * 回收导检单
	     * <p>Title: insertExamflowRecovery</p>   
	     * <p>Description: </p>   
	     * @param examid
	     * @param user
	     * @return 
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.ExamFlowService#insertExamflowRecovery(long, com.hjw.wst.DTO.UserDTO)
	 */
	public String insertExamflowRecovery(String exam_num, UserDTO user) throws ServiceException {
		String dep_flow = this.customerInfoService.getCenterconfigByKey("IS_REPORT_DEP_FLOW", user.getCenter_num()).getConfig_value();
		String message = "";

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			int countno = 0;
			// int countno = getExamChargitemNjc(examinfo.getExam_num());
			if (countno > 0) {
				message = "error-有未检出项目，导检单不能回收！";
			} else {
				if("N".equals(examinfo.getIs_guide_back())){
					examinfo.setIs_guide_back("Y");
					examinfo.setIs_guide_user(user.getUserid());
					examinfo.setIs_guide_date(DateTimeUtil.getDateTime());
					if (examinfo.getJoin_date() == null || "".equals(examinfo.getJoin_date())) {
						examinfo.setJoin_date(DateTimeUtil.getDateTime());
						examinfo.setStatus("J");
					}
					examInfoService.receiveExamInfo(examinfo);
					ExamInfoRecyclingGuid guid = new ExamInfoRecyclingGuid();// 添加导简人
					guid.setCreater(user.getUserid());// 创建人
					guid.setCreate_time(DateTimeUtil.parse());// 创建时间
					guid.setExam_num(examinfo.getExam_num());
					this.examInfoRecyclingGuidService.addExamInfoRecyclingGuidService(guid);

				}
				
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
				if (invoiceList.size() <= 0) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis.setExam_num(examinfo.getExam_num());
					cis.setH0(1);
					cis.setH0date(DateTimeUtil.parse());
					cis.setH0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					if(dep_flow != null && "2".equals(dep_flow)){
						cis.setH1(1);
						cis.setH1date(DateTimeUtil.parse());
						cis.setH1creater(user.getUserid());
					}
					this.pm.save(cis);
					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("h0");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				} else {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis = invoiceList.get(0);
					if (cis.getH0() == 1) {
						message = "error-重复提交,操作不能继续";
					} else if (cis.getH1() == 1) {
						message = "error-重复提交，操作不能继续";
					} else {
						cis.setH0(1);
						cis.setH0date(DateTimeUtil.parse());
						cis.setH0creater(user.getUserid());
						cis.setCenter_num(user.getCenter_num());
						if(dep_flow != null && "2".equals(dep_flow)){
							cis.setH1(1);
							cis.setH1date(DateTimeUtil.parse());
							cis.setH1creater(user.getUserid());
						}
						this.pm.update(cis);
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(examinfo.getExam_num());
						efl.setFlow_code("h0");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
						
						message = "ok-操作完成";
					}
				}
			}
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: revocationDYD   
	     * @Description: 撤销回收导检单 
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String revocationDYD(String exam_num, UserDTO user) throws ServiceException {
		String dep_flow = this.customerInfoService.getCenterconfigByKey("IS_REPORT_DEP_FLOW", user.getCenter_num()).getConfig_value();
		String message = "";

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if (cis.getH0() == 0) {
					message = "error-体检未核收，操作不能继续！";
				} else if (cis.getH1() == 1 && !"2".equals(dep_flow)) {
					message = "error-体检核收已经结束，操作不能继续！";
				} else {
					cis = invoiceList.get(0);
					cis.setH0(0);
					cis.setH0date(DateTimeUtil.parse());
					cis.setH0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					if(dep_flow != null && "2".equals(dep_flow)){
						cis.setH1(0);
						cis.setH1date(DateTimeUtil.parse());
						cis.setH1creater(user.getUserid());
					}
					pm.update(cis);
					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("h0");
					efl.setFlow_type(0);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					
					examinfo.setIs_guide_back("N");
					examinfo.setIs_guide_user(0);
					examinfo.setIs_guide_date(null);
					this.pm.update(examinfo);						
					String hql = "FROM  ExamInfoRecyclingGuid   WHERE  exam_num='"+exam_num+"'";
					List<ExamInfoRecyclingGuid>   list = qm.find(hql);
					if( list.size()>0 && list!=null ){
						for (ExamInfoRecyclingGuid examInfoRecyclingGuid : list) {
							pm.remove(examInfoRecyclingGuid);
						}
					}
					message = "ok-操作完成";
				}
			} else {
				examinfo.setIs_guide_back("N");
				this.pm.update(examinfo);						
				String hql = "FROM  ExamInfoRecyclingGuid   WHERE  exam_num='"+exam_num+"'";
				List<ExamInfoRecyclingGuid>   list = qm.find(hql);
				if( list.size()>0 && list!=null ){
					for (ExamInfoRecyclingGuid examInfoRecyclingGuid : list) {
						pm.remove(examInfoRecyclingGuid);
					}
				}
				message = "ok-操作完成";
			}
		}
		return message;
	}
	
	@Override
	public ExamFlowRemakDTO getFlowRemakJilu(ExamFlowModel model)
			throws ServiceException {
		String sql = "   SELECT id,exam_num,dep_id,remark,remark_user FROM    exam_flow_remak   e    "
				+ "   where   e.dep_id='"+model.getProcess()+"'   and   e.exam_num='"+model.getExam_num().trim()+"'";
		List<ExamFlowRemakDTO> li = this.jqm.getList(sql, ExamFlowRemakDTO.class);
		if(li!=null && li.size()>0){
			return li.get(0);
		} else {
			return new ExamFlowRemakDTO();
		}
	}
	
	@Override
	public PageReturnDTO queryFlowRemak(ExamFlowModel model,int page,int pageSize)
			throws ServiceException {
		String sql = " SELECT e.id,e.exam_num,e.dep_id as process,e.remark,e.remark_user,e.remark_time "
					+",us.chi_name  FROM   "
					+" exam_flow_remak  e,user_usr us "
					+" WHERE    e.remark_user = us.id   ";
					if(model.getExam_num()!=null && !"".equals(model.getExam_num())){
					sql+= "and  e.exam_num='"+model.getExam_num().trim()+"'  ";
					} else {
					sql+= "and  e.exam_num='"+model.getExam_num()+"'  ";	
					}
					sql+=" ORDER  BY   e.remark_time ";
		PageSupport map=jqm.getList(sql,pageSize,page,ExamFlowRemakDTO.class);
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
	public ExamInfoDTO getNumExamInfo(ExamFlowModel model)
			throws ServiceException {
		String sql = " SELECT id,exam_num FROM   exam_info   ";
				if(model.getExam_num()!=null && !"".equals(model.getExam_num())){
				sql+= " where  exam_num='"+model.getExam_num().trim()+"'   ";
				} else {
				sql+= " where  exam_num='"+model.getExam_num()+"'   ";
				}
		List<ExamInfoDTO> li =	this.jqm.getList(sql, ExamInfoDTO.class);
		if(li!=null && li.size()>0){
			return  li.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void updateFlowRemak(ExamFlowModel model)  throws ServiceException{
		Connection connection = null;
		PreparedStatement p = null;
		String sql = "";
		try {
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String c_sql = " SELECT id FROM   exam_flow_remak   WHERE   dep_id=?   and   exam_num=?  ";
			p = connection.prepareStatement(c_sql);
			p.setLong(1,model.getProcess());
			p.setString(2,model.getExam_num().trim());
			ResultSet res = p.executeQuery();
			if(res.next()){
				sql = "  UPDATE exam_flow_remak  SET   remark=?,remark_user=?,remark_time=?  where  id=?  ";
				p = connection.prepareStatement(sql);
				p.setString(1,model.getRemark());
				p.setLong(2,model.getUser_id());
				p.setTimestamp(3,new Timestamp(DateTimeUtil.parse().getTime()));
				p.setLong(4,model.getId());
				p.executeUpdate();
			} else {
				sql = " INSERT   INTO    exam_flow_remak(exam_num,dep_id,remark,remark_user,remark_time) "
					     +"   VALUES(?,?,?,?,?)  ";
						p = connection.prepareStatement(sql);
						p.setString(1,model.getExam_num().trim());
						p.setLong(2,model.getProcess());
						p.setString(3,model.getRemark());
						p.setLong(4,model.getUser_id());
						p.setTimestamp(5,new Timestamp(new Date().getTime()));
						p.executeUpdate();
			}
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}  
			e.printStackTrace();
		}finally{
			try {
				if(p!=null){
					p.close();
				}
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void saveFlowRemak(ExamFlowModel model) throws ServiceException {
		Connection connection = null;
		PreparedStatement p = null;
		String sql = "";
			try {
				sql = " INSERT   INTO    exam_flow_remak(exam_num,dep_id,remark,remark_user,remark_time) "
				     +"   VALUES(?,?,?,?,?)  ";
					connection = this.jqm.getConnection();
					connection.setAutoCommit(false);
					p = connection.prepareStatement(sql);
					p.setString(1,model.getExam_num().trim());
					p.setLong(2,model.getProcess());
					p.setString(3,model.getRemark());
					p.setLong(4,model.getUser_id());
					p.setTimestamp(5,new Timestamp(new Date().getTime()));
					p.executeUpdate();
					connection.commit();
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}  
				e.printStackTrace();
			}finally{
				try {
					if(p!=null){
						p.close();
					}
					if(connection!=null){
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	}
	/**
	 * 
	     * @Title: getFlowExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param ptype
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFlowExamInfoUserList(ExamFlowModel model, long userid, String centernum, int pagesize, int pageno,UserDTO user)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限 Y 有 N 无
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");

		
		sb.append(" order by h0date desc,h1date desc ");

		sb.append(") AS ROWID,* FROM (");

		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.h0,d.h1,d.h0date,d.h1date ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and j.center_num='"+user.getCenter_num()+"'  ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num  and d.center_num = '"+user.getCenter_num()+"' ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
        
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			if(userid>0){
				sb.append(" and (d.h0creater="+userid+" or d.h1creater="+userid+") ");
				sb1.append(" and (d.h0creater="+userid+" or d.h1creater="+userid+") ");
			}
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.h0creater in ("+userids+") or d.h1creater in ("+userids+") ) ");  
			sb1.append(" and (d.h0creater in ("+userids+") or d.h1creater in ("+userids+") ) ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getUsername())) {
			sb.append(" and a.user_name like '%" + model.getUsername() + "%' ");
			sb1.append(" and a.user_name like '%" + model.getUsername() + "%' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (model.getBatch_id() > 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (model.getGroup_id() > 0) {
			sb.append(" and c.group_id = " + model.getGroup_id() + " ");
			sb1.append(" and c.group_id = " + model.getGroup_id() + " ");
		}
		
		if (model.getSet_id() > 0) {
			sb.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.exam_num=c.exam_num and es.isActive='Y') ");
			sb1.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.exam_num=c.exam_num and es.isActive='Y') ");
		}
		
		if(model.getPtype()==0){
			sb.append(" and d.h0 = 1 ");
			sb1.append(" and d.h0 = 1 ");
		}else if(model.getPtype()==1){
			sb.append(" and d.h1 = 0 and d.h0 = 1 ");
			sb1.append(" and d.h1 = 0 and d.h0 = 1  ");
		}else if(model.getPtype()==2){
			sb.append(" and d.h0 = 1 and d.h1 = 1  ");
			sb1.append(" and d.h0 = 1 and d.h1 = 1  ");
		}		

		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and ((d.h0date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.h0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb.append(" or (d.h1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.h1date <= '" + model.getTime2() + " 23:59:59')) ");


			sb1.append(" and ((d.h0date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.h0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb1.append(" or (d.h1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.h1date <= '" + model.getTime2() + " 23:59:59')) ");

		}		

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String set_names = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						set_names = set_names + "," + es.getSet_name();
					}
					if (set_names.length() > 1)
						set_names = set_names.trim().substring(1, set_names.trim().length());
					eu.setSet_name(set_names);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	/**
	 * 
	     * @Title: flowexamh1insert   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamh1insert(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			int count = 0;
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getH0()==0){
						message="error-导检单未核收，操作不能继续";
					}else if(cis.getH1()==1){
						message="error-导检单已上传，操作不能继续";
					}else{
						cis.setH1(1);
						cis.setH1creater(user.getUserid());
						cis.setH1date(DateTimeUtil.parse());
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						count++;
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("h1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
					}
				}
			}
			message="ok-成功上传" + count + "份";
		}
		return message;
	}
	//导检单取消上传
	public String flowexamh1delete(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getH0()==0){
						message="error-导检单还未接收,操作不能继续";
					}else if(cis.getH1()==0){
						message="error-导检单还未上传，操作不能继续";
					}else if((cis.getS0()==1)||(cis.getC()==1)||(cis.getZ0()==1)){
						message="error-导检单已下总检或审核或终审，操作不能继续";
					}else{
						cis.setH1(0);
						cis.setH1creater(0);
						cis.setH1date(null);
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("-h1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						pm.save(efl);
						message="ok-操作成功";
					}
				}
			}
			
		}
		return message;
	}
	
	public List<UserDTO> getflowUser(ExamFlowModel model, String centernum)
			throws ServiceException {
		String sql="";
		if ("p".equals(model.getCreater_state())) {
			sql = "select distinct u.id,u.chi_name  as  username    from  exam_info  e ,user_usr u  "
					+ "where e.Report_Print_UserId = u.id and  e.Report_Print_UserId is not  null   ";
		}else{
			 sql = " SELECT  DISTINCT u.id,u.chi_name  as  username  FROM   exam_flow_config  e,user_usr   u "
					+ " WHERE  center_num='"+centernum+"'   ";
					
					if("v".equals(model.getCreater_state())){
					sql+=" and   (u.id = e.vcreater   or    u.id=e.vtcreater ) ";
					} else if("h1".equals(model.getCreater_state())){//导检单科室
					sql+="	and   (u.id = e.h1creater   or    u.id=h0creater ) ";
					} else if("s1".equals(model.getCreater_state())){//整单科室
					sql+="	and   (u.id = e.s1creater  or u.id = e.s0creater)";	
					} else if("p1".equals(model.getCreater_state())){
						sql+="	and   (p0creater=u.id   OR   p1creater=u.id ) ";	
					} else if("e1".equals(model.getCreater_state())){
						sql+="	and   (e0creater=u.id   OR   e1creater=u.id)";	
					} else if("m".equals(model.getCreater_state())){
						sql+="	and   (u.id = e.mcreater  or u.id = e.mcreater)";	
					}else if("z".equals(model.getCreater_state())){
						sql+="	and zcreater=u.id ";
					}
			}
		
			List<UserDTO> li = new ArrayList<UserDTO>();
			UserDTO user = new UserDTO();
			user.setUsername("所有人员");
			li.add(user);
			li.addAll(this.jqm.getList(sql,UserDTO.class));
		return li;
	}
	
	/**
	 * 
	     * @Title: getFlowExamInfoUserLists   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param ptype
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFlowExamInfoUserLists(ExamFlowModel model, long userid, String centernum, int pagesize, int pageno,UserDTO user)throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");
		sb.append(" order by s desc,sdate asc,s0date,s1date ");
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.s,d.s0,d.s1,d.sdate,d.s0date,d.s1date,(SELECT count(ech.id) FROM examinfo_charging_item ech LEFT JOIN  charging_item  ci  ON   ech.charge_item_id=ci.id  where   ci.item_category != '耗材类型' and ech.examinfo_id = c.id"
				+ " and ech.exam_status !='G' and ech.exam_status !='Y' and ech.isActive='Y' and ech.pay_status <> 'M' and ech.center_num = '"+user.getCenter_num()+"')  as item_number ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and  j.center_num = '"+user.getCenter_num()+"' ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num  and d.center_num = '"+user.getCenter_num()+"' ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		if(model.getRemark() != null && !"".equals(model.getRemark()) && !"0".equals(model.getRemark())) {
			String userids = model.getRemark();
			sb.append(" and (d.s0creater="+userids+" or d.s1creater="+userids+") ");  
			sb1.append(" and (d.s0creater="+userids+" or d.s1creater="+userids+") ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getUsername())) {
			sb.append(" and a.user_name like '%" + model.getUsername() + "%' ");
			sb1.append(" and a.user_name like '%" + model.getUsername() + "%' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}

		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (model.getBatch_id() > 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (model.getGroup_id() > 0) {
			sb.append(" and c.group_id = " + model.getGroup_id() + " ");
			sb1.append(" and c.group_id = " + model.getGroup_id() + " ");
		}
		
		if (model.getSet_id() > 0) {
			sb.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
			sb1.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
		}
		
		if(model.getPtype()==0){
			sb.append(" and d.s0 = 1 ");
			sb1.append(" and d.s0 = 1 ");
		}else if(model.getPtype()==1){
			sb.append(" and d.s0 = 1 and d.s1 = 0 ");
			sb1.append(" and d.s0 = 1 and d.s1 = 0  ");
		}else if(model.getPtype()==2){
			sb.append(" and d.s0 = 1 and d.s1 = 1  ");
			sb1.append(" and d.s0 = 1 and d.s1 = 1  ");
		}		

		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and ((d.s0date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.s0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb.append(" or (d.s1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.s1date <= '" + model.getTime2() + " 23:59:59')) ");


			sb1.append(" and ((d.s0date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.s0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb1.append(" or (d.s1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.s1date <= '" + model.getTime2() + " 23:59:59)) ");

		}		

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: insertExamflows0   
	     * @Description: 整单室核收   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String insertExamflows0(String exam_num, UserDTO user) throws ServiceException {
		String message = "";

		String sql = " select id,data_name from data_dictionary dd where dd.data_code = 'TJLX' and dd.id in (42,321,271,223,325) ";
		List<DataDictionary> zg_list = this.jqm.getList(sql, DataDictionary.class);
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else if(zg_list.size() > 0 && "42,321,271,223,325".indexOf(examinfo.getCustomer_type()) > -1) {
			for (DataDictionary dataDictionary : zg_list) {
				if(examinfo.getCustomer_type().equals(""+dataDictionary.getId())){
					message = "tishi-"+dataDictionary.getData_name()+"，整单室不能操作！";
					break;
				}
			}
		} else if(examinfo.getWuxuzongjian() == 1) {
			message = "error-无需总检，整单室不能操作！";
		}else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if ((cis.getH0() == 0) || (cis.getH1() == 0) )
				{
					message = "error-导检单未被核收，整单室不能操作";
				}else if(cis.getS0() == 1 && cis.getS0creater() == user.getUserid()){
//					ExamFlowLog efl = new ExamFlowLog();
//					efl.setExam_num(examinfo.getExam_num());
//					efl.setFlow_code("zd");
//					efl.setFlow_type(1);
//					efl.setSendcreater(user.getUserid());
//					efl.setCenter_num(user.getCenter_num());
//					efl.setSenddate(DateTimeUtil.parse());
//					this.pm.save(efl);
					message = "error-整单室已经确认过了，操作不能继续";
				}else if(cis.getS1() == 1){
					message = "error-整单室已经上传了，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setS0(1);
					cis.setS0date(DateTimeUtil.parse());
					cis.setS0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);
					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("s0");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					
					String IS_FLOW_S0_S1="N";
					try{
						IS_FLOW_S0_S1=this.customerInfoService.getCenterconfigByKey("IS_FLOW_S0_S1", user.getCenter_num()).getConfig_value().trim();
					}catch(Exception ex){}
					if("Y".equals(IS_FLOW_S0_S1)){
						if(getEciNForExamId(exam_num, user.getCenter_num())){
							cis = invoiceList.get(0);
							cis.setS1(1);
							cis.setS1date(DateTimeUtil.parse());
							cis.setS1creater(user.getUserid());
							cis.setCenter_num(user.getCenter_num());
							this.pm.update(cis);
							efl = new ExamFlowLog();
							efl.setExam_num(examinfo.getExam_num());
							efl.setFlow_code("s1");
							efl.setFlow_type(1);
							efl.setSendcreater(user.getUserid());
							efl.setCenter_num(user.getCenter_num());
							efl.setSenddate(DateTimeUtil.parse());
							this.pm.save(efl);
						}							
					}						
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: getEciNForExamId   
	     * @Description: 如果存在未检查项目，则返回false   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	private boolean getEciNForExamId(String exam_num, String center_num) throws ServiceException {
		Connection tjtmpconnect = null;
		boolean flag = true;
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "select ec.id from charging_item c,examinfo_charging_item ec "
					+ "where ec.charge_item_id = c.id  "
					+ "and   c.item_category <> '耗材类型' "
					+ "and   ec.pay_status <> 'M' "
					+ "and ec.exam_status <> 'G'  "
					+ "and ec.exam_status <> 'Y'  "
					+ "and ec.isActive = 'Y' "
					+ "and ec.change_item <> 'C'"
					+ "  and ec.exam_num='" + exam_num	+ "' and ec.center_num = '"+center_num+"' ";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			if (rs1.next()) {
				flag = false;
			}
			rs1.close();
		} catch (SQLException ex) {
		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return flag;
	}
	/**
	 * 
	     * <p>Title: insertExamflows0un</p>   
	     * <p>Description: 整单室取消接收  
	     * @param examid
	     * @param user
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.ExamFlowService#insertExamflows0un(long, com.hjw.wst.DTO.UserDTO)
	 */
	public String insertExamflows0un(String exam_num, UserDTO user) throws ServiceException {
		String message = "";

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if ((cis.getH0() == 0) || (cis.getH1() == 0) )
				{
					message = "error-导检单未被核收，整单室不能操作";
				}else if(cis.getS0() == 0){
					message = "error-整单室还没有接收，操作不能继续";
				}else if(cis.getS1() == 1){
					message = "error-整单室已经上传了，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setS0(0);
					cis.setS0date(DateTimeUtil.parse());
					cis.setS0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("s0");
					efl.setFlow_type(0);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
	// 整单室提示 未检项目
	@Override
	@SuppressWarnings("unchecked")
	public String getWJitemList(String wjItem_dep, String exam_num, String center_num) throws ServiceException {
		String message = "";
		String sql = "select  cc.item_name  from examinfo_charging_item   eci , charging_item cc ,exam_info e "
				+" where  e.id = eci.examinfo_id and  eci.charge_item_id = cc.id and  e.exam_num = '"+exam_num+"' and "
				+" eci.exam_status in ('N','C') and eci.isActive = 'Y' and eci.pay_status <> 'M' and eci.center_num = '"+center_num+"' and cc.item_category <> '耗材类型'";
		if (wjItem_dep != null && !"".equals(wjItem_dep) ) {
			sql+="and eci.charge_item_id not  in (select  ci.id from charging_item ci , department_dep  d where d.id = ci.dep_id and d.id in ("+wjItem_dep+"))";
		}
		List<ChargingItemModel> list = jqm.getList(sql, ChargingItemModel.class);
		if (list.size() > 0) {
			for (ChargingItemModel chargingItemModel : list) {
				message+=chargingItemModel.getItem_name()+" ,";
			}
		}
		return message;
	}
    
	//整单上传
	@SuppressWarnings("unchecked")
	@Override
	public String uploadFlow(UserDTO user, String ids)  throws ServiceException {
		String message = "" ;
		String[] split = ids.split(",");
		for (String exam_num : split) {
			ExamInfoDTO exam = customerInfoService.getExamInfoForexamNum(exam_num);
			if (exam == null) {
				message =  "error-无效记录";
			}else{
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"+exam_num+"'" );
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis = invoiceList.get(0);
					 if(cis.getS0() != 1){
						message = "error-整单室未回收，操作不能继续";
					}else if(cis.getS1() == 1){
						message = "error-整单室已回收，操作不能继续";
					}else {
						cis.setS(1);
						cis.setSdate(DateTimeUtil.parse());
						cis.setS1(1);
						cis.setS1creater(user.getUserid());
						cis.setS1date(DateTimeUtil.parse());
						cis.setZ(1);
						cis.setZdate(DateTimeUtil.parse());
						cis.setZcreater(user.getUserid());
						cis.setC0(1);
						cis.setC0creater(user.getUserid());
						cis.setC0date(DateTimeUtil.parse());
						this.pm.update(cis);

						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("s1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
						message = "ok-操作完成";
					}
				} else {
					message = "error-核收表无此记录";
				}
			}
		}
		return message;
	}
	//取消上传
	@Override
	public String examUploadFlowDel(UserDTO user, String ids) throws ServiceException {
		String message = "" ;
		String[] split = ids.split(",");
		for (String exam_num : split) {
			ExamInfoDTO exam = customerInfoService.getExamInfoForexamNum(exam_num);
			if (exam == null) {
				message =  "error-无效记录";
			}else{
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"+exam_num+"'" );
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis = invoiceList.get(0);
					 if(cis.getZ0() == 1){
						message = "error-已经总检，操作不能继续";
					}else {
						cis.setS(0);
						cis.setSdate(null);
						cis.setS1(0);
						cis.setS1creater(0);
						cis.setS1date(null);
						cis.setZ(0);
						cis.setZdate(null);
						cis.setZcreater(0);
						cis.setC0(0);
						cis.setC0creater(0);
						cis.setC0date(null);
						this.pm.update(cis);

						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("-s1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
						message = "ok-操作完成";
					}
				} else {
					message = "error-核收表无此记录";
				}
			}
		}
		return message;
	}
	
	@Override
	public String jieshoukeshangchuan(String exam_num,long userid,String centernum)throws WebException {	
		String sql = " update  exam_flow_config  set s=1,sdate=getdate() where exam_num='"+exam_num+"' and center_num='" + centernum + "' ";
		this.jdbcPersistenceManager.executeSql(sql);
		return "ok-"+exam_num+"-";
		}
	
	@Override
	public String quxiaoshangchuan(String exam_num,long userid,String centernum)throws WebException {	
		String sql = " update  exam_flow_config  set s=0,sdate=null where exam_num='"+exam_num+"' and center_num='" + centernum + "' ";
		this.jdbcPersistenceManager.executeSql(sql);
		return "ok-"+exam_num+"-";
	}
	
	@Override
	public PageReturnDTO flowexampuploadingLists(ExamFlowModel model, long userid, String centernum,int pagesize, int pageno) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");
		
		sb.append(" order by s desc,sdate asc,s0date desc,s1date desc ");

		sb.append(") AS ROWID,* FROM (");

		sb.append(" select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.s,d.s0,d.s1,d.sdate,d.s0date,d.s1date ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and j.center_num = '"+centernum+"' ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num and d.center_num = '"+centernum+"' ");
		sb.append(" and c.is_Active='Y' ");
		
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
		  
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		//exam_flow_config  s  1表示可上传   0不可上传
		sb.append(" and d.s = 1 ");
		sb1.append(" and d.s = 1 ");
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			sb.append(" and (d.s0creater="+userid+" or d.s1creater="+userid+") ");  
			sb1.append(" and (d.s0creater="+userid+" or d.s1creater="+userid+") ");
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.s0creater in ("+userids+") or d.s1creater in ("+userids+") ) ");  
			sb1.append(" and (d.s0creater in ("+userids+") or d.s1creater in ("+userids+") ) ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getUsername())) {
			sb.append(" and a.user_name like '%" + model.getUsername() + "%' ");
			sb1.append(" and a.user_name like '%" + model.getUsername() + "%' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if (model.getAge() > 0) {
			sb.append(" and c.age = " + model.getAge() + " ");
			sb1.append(" and c.age = " + model.getAge() + " ");
		}

		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (model.getBatch_id() > 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (model.getGroup_id() > 0) {
			sb.append(" and c.group_id = " + model.getGroup_id() + " ");
			sb1.append(" and c.group_id = " + model.getGroup_id() + " ");
		}
		
		if (model.getSet_id() > 0) {
			sb.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
			sb1.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
		}
		
		if(model.getPtype()==0){
			sb.append(" and d.s0 = 1 ");
			sb1.append(" and d.s0 = 1 ");
		}else if(model.getPtype()==1){
			sb.append(" and d.s0 = 1 and d.s1 = 0 ");
			
			sb1.append(" and d.s0 = 1 and d.s1 = 0  ");
		}else if(model.getPtype()==2){
			sb.append(" and d.s0 = 1 and d.s1 = 1  ");
			sb1.append(" and d.s0 = 1 and d.s1 = 1  ");
		}		

		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and ((d.s0date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.s0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb.append(" or (d.s1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.s1date <= '" + model.getTime2() + " 23:59:59')) ");

			sb1.append(" and ((d.s0date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.s0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb1.append(" or (d.s1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.s1date <= '" + model.getTime2() + " 23:59:59')) ");
		}		

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");
		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}
				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}
				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	//整单室批量上传 到下一步
	@SuppressWarnings("unchecked")
	public String flowexams1insert(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			int count = 0;
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getS0()==0){
						message="error-导检单未确认接受，操作不能继续";
					}else if(cis.getS0creater()!=user.getUserid()){
						message="error-导检单不是本人确认接受，操作不能继续";
					}else if(cis.getS1()==1){
						message="error-导检单已经上传，操作不能继续";
					}else{
						cis.setS1(1);
						cis.setS1creater(user.getUserid());
						cis.setS1date(DateTimeUtil.parse());
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						count++;
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("s1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
					}
				}
			}
			message="ok-成功上传" + count + "份";
		}
		return message;
	}
  //整单室取消上传
	@SuppressWarnings("unchecked")
	public String flowexams1delete(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getS0()==0){
						message="error-整单室未确认接受，操作不能继续";
					}else if(cis.getS1()==0){
						message="error-整单室未确认上传，操作不能继续";
					}else if ((cis.getC()==1)||(cis.getZ0()==1)){
						message="error-已经审核或已终审，操作不能继续";
					}else{
						cis.setS1(0);
						cis.setS1creater(0);
						cis.setS1date(null);
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("-s1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						pm.save(efl);
						message="ok-操作成功";
					}
				}
			}
		}
		return message;
	}
	//导检单转移
	@SuppressWarnings("unchecked")
	@Override
	public String flowexamtransfer(String ids,String code, long userid, long newUserid, String center_num)throws ServiceException {
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + center_num + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					String flow_code = "";
					String undo_flow_code = "";
					switch(code) {
					case "s" :
						cis.setS0(1);
						cis.setS0creater(newUserid);
						cis.setS0date(DateTimeUtil.parse());
						flow_code = "s0";
						undo_flow_code = "-s0";
						break;
					case "z" :
						cis.setZ(1);
						cis.setZcreater(newUserid);
						cis.setZdate(DateTimeUtil.parse());
						flow_code = "z";
						undo_flow_code = "-z";
						break;
					case "p" :
						cis.setP0(1);
						cis.setP0creater(newUserid);
						cis.setP0date(DateTimeUtil.parse());
						flow_code = "p0";
						undo_flow_code = "-p0";
						break;
					}
					cis.setCenter_num(center_num);
					pm.update(cis);
					
					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(exam_num);
					efl.setFlow_code(undo_flow_code);
					efl.setFlow_type(1);
					efl.setSendcreater(userid);
					efl.setCenter_num(center_num);
					efl.setSenddate(new Date(System.currentTimeMillis()-1000));
					this.pm.save(efl);
					
					efl = new ExamFlowLog();
					efl.setExam_num(exam_num);
					efl.setFlow_code(flow_code);
					efl.setFlow_type(1);
					efl.setSendcreater(newUserid);
					efl.setCenter_num(center_num);
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
				}
			}
			message="ok-操作成功";
		}
		return message;
	}
	
	@Override
	public PageReturnDTO getPre_receive_zongjian(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");		
		sb.append(" order by s1date desc ");
		sb.append(") AS ROWID,* FROM (");
		
		sb.append(" select count(*) as num,d.s1date,uu.chi_name as s0creater,uu2.chi_name as s1creater ");
		sb.append(" from exam_flow_config d ");
		sb.append(" left join user_usr uu on d.s0creater = uu.id ");
		sb.append(" left join user_usr uu2 on d.s1creater = uu2.id ");
		sb.append(" where d.s0 =1 and d.s1=1 and d.z=0 ");
		sb.append(" and d.center_num='" + centernum + "' ");
        
		sb1.append("select count(tmp.num) as counts from (");
		
		sb1.append(" select count(*) as num,d.s1date,uu.chi_name as s0creater,uu2.chi_name as s1creater ");
		sb1.append(" from exam_flow_config d ");
		sb1.append(" left join user_usr uu on d.s0creater = uu.id ");
		sb1.append(" left join user_usr uu2 on d.s1creater = uu2.id ");
		sb1.append(" where d.s0 =1 and d.s1=1 and d.z=0 ");
		sb1.append(" and d.center_num='" + centernum + "'  ");
		
		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and d.s1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.s1date <= '" + model.getTime2() + " 23:59:59' ");

			sb1.append(" and d.s1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.s1date <= '" + model.getTime2() + " 23:59:59' ");
		}
		
		sb.append("  group by d.s1date,uu.chi_name, uu2.chi_name ");
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");
		
		sb1.append("  group by d.s1date,uu.chi_name, uu2.chi_name ");
		sb1.append(") tmp ");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO getPre_receive_details_zongjian(ExamFlowModel model, long userid, String centernum, int pagesize, int pageno)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");

		
		sb.append(" order by s0date desc,s1date desc ");

		sb.append(") AS ROWID,* FROM (");

		sb.append(" select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.s0,d.s1,d.s0date,d.s1date ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and  j.center_num = '"+centernum+"'  ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num   and  d.center_num = '"+centernum+"' ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
//        sb.append(" and (d.p0creater="+userid+" or d.p1creater="+userid+") ");
        sb.append(" and d.s0 =1 and d.s1=1 and d.z=0 ");
        
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		sb1.append(" and d.s0 =1 and d.s1=1 and d.z=0 ");

		if (!StringUtil.isEmpty(model.getTime1())) {
			sb.append(" and d.s1date= '" + model.getTime1() + "' ");
		}		

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}
				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}
				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: insertExamflowz   
	     * @Description: 报告室批量核收   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String insertExamflowz(String exam_num, UserDTO user) throws ServiceException {
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				 if(cis.getS1() == 0){
					message = "error-整单室未确认，操作不能继续";
				}else if(cis.getZ() == 1){
					message = "error-终检室已经确认过了，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setZ(1);
					cis.setZdate(DateTimeUtil.parse());
					cis.setZcreater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("z");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
	/**
	 * 报告室 --- 查询
	 */
	public PageReturnDTO getFlowExamInfoUserListz(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno,UserDTO user)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");		
		sb.append(" order by z0date desc,z1date desc,cdate desc,fdate desc ");
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.z0,d.z1,d.z0date,d.z1date,d.c,d.cdate,d.f,d.fdate,"
				+ "z0user.chi_name as z0user,z1user.chi_name as z1user,cuser.chi_name as cuser,fuser.chi_name as fuser ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");		
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and j.center_num = '"+centernum+"'  ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" left join user_usr z0user on z0user.id=d.z0creater ");
		sb.append(" left join user_usr z1user on z1user.id=d.z1creater ");
		sb.append(" left join user_usr cuser on cuser.id=d.ccreater ");
		sb.append(" left join user_usr fuser on fuser.id=d.fcreater ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
        //sb.append(" and (d.p0creater="+userid+" or d.p1creater="+userid+") ");        
        
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			sb.append(" and (d.zcreater=" +userid + " or d.z0creater="+userid+" or d.z1creater="+userid+") ");
			sb1.append(" and (d.zcreater=" +userid + " or d.z0creater="+userid+" or d.z1creater="+userid+") ");
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.zcreater in ("+userids+") or d.z0creater in ("+userids+") or d.z1creater in ("+userids+") ) ");  
			sb1.append(" and (d.zcreater in ("+userids+") or d.z0creater in ("+userids+") or d.z1creater in ("+userids+") ) ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getUsername())) {
			sb.append(" and a.user_name like '%" + model.getUsername() + "%' ");
			sb1.append(" and a.user_name like '%" + model.getUsername() + "%' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (model.getBatch_id() > 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (model.getGroup_id() > 0) {
			sb.append(" and c.group_id = " + model.getGroup_id() + " ");
			sb1.append(" and c.group_id = " + model.getGroup_id() + " ");
		}
		
		if (model.getSet_id() > 0) {
			sb.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
			sb1.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
		}
		
		if(model.getPtype()==1){
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1 ");
			
			sb.append(" and d.z0 = 1 and d.z1=1");
			sb1.append(" and d.z0 = 1 and d.z1=1 ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and ((d.z0date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.z0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb.append(" or (d.z1date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.z1date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb.append(" or (d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59')) ");


				sb1.append(" and ((d.z0date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.z0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" or (d.z1date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.z1date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" or (d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59')) ");

			}	
			
		}else if(model.getPtype()==2){
			sb.append(" and d.z = 1 and d.z1=0");
			sb1.append(" and d.z = 1 and d.z1=0 ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and ((d.z0date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.z0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb.append(" or (d.z1date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.z1date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb.append(" or (d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59')) ");


				sb1.append(" and ((d.z0date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.z0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" or (d.z1date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.z1date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" or (d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59')) ");

			}	
		}else if(model.getPtype()==3){
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1 ");
			
			sb.append(" and d.c = 1 ");
			sb1.append(" and d.c = 1  ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and (d.cdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.cdate <= '" + model.getTime2() + " 23:59:59') ");				
				
				sb1.append(" and (d.cdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.cdate <= '" + model.getTime2() + " 23:59:59') ");
				

			}	
		}else if(model.getPtype()==4){
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1 ");
			
			sb.append(" and d.c = 0 and d.z1=1");
			sb1.append(" and d.c = 0 and d.z1=1 ");			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and (d.z1date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.z1date <= '" + model.getTime2() + " 23:59:59') ");				
				
				sb1.append(" and (d.z1date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.z1date <= '" + model.getTime2() + " 23:59:59') ");	
			}	
		}else if(model.getPtype()==5){
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1 ");
			
			sb.append(" and d.f = 1 ");
			sb1.append(" and d.f = 1  ");			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and (d.fdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.fdate <= '" + model.getTime2() + " 23:59:59') ");				
				
				sb1.append(" and (d.fdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.fdate <= '" + model.getTime2() + " 23:59:59') ");
				}					
		}else if(model.getPtype()==6){
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1 ");
			
			sb.append(" and d.f = 0 and d.c=1 ");
			sb1.append(" and d.f=0 and d.c=1 ");			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and (d.cdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.cdate <= '" + model.getTime2() + " 23:59:59') ");				
				
				sb1.append(" and (d.cdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.cdate <= '" + model.getTime2() + " 23:59:59') ");
				}					
		}else if(model.getPtype()==7){
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1 ");
			
			sb.append(" and c.wuxuzongjian=1 ");
			sb1.append(" and c.wuxuzongjian=1 ");			
		}else {
			sb.append(" and d.z = 1 ");
			sb1.append(" and d.z = 1  ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59' ");

				sb1.append(" and d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59' ");
			}	
		}
			

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: flowexameListzjbg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param docid
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public List<ExamInfoUserDTO> flowexameListzjbg(long docid,String exam_num,long userid, String centernum,UserDTO user)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(es.exam_doctor_id) as final_doctor,c.final_date,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(es.check_doc) as check_doctor");
		sb.append(" ,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.z0,d.z1,d.z0date,d.z1date,d.c,d.cdate,d.f,d.fdate,(dbo.GetTeamPayByExamId(c.exam_num) + dbo.GetPersonalPayByExamId(c.exam_num)) as pay_amount,");
		sb.append("(case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=c.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name ");
		sb.append(" from customer_info a,exam_info c,exam_flow_config d,exam_summary es ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and es.exam_info_id=c.id   and es.center_num = '"+centernum+"' ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");          
		sb.append(" and d.c1=0 and d.z1=1 and c.customer_type not in (42,321,271,223,325) ");
		sb.append(" and not exists ( select * from exam_summary_reject r where r.done_status = 0 and r.examinfo_id = c.id) ");
		if(exam_num!=null && exam_num.trim().length()>0){
			sb.append(" and c.exam_num='"+exam_num+"' ");
		}
		if(docid>0){
			sb.append(" and es.exam_doctor_id='"+docid+"' ");		
		}
		sb.append(" order by c.final_date");
		List<ExamInfoUserDTO> list = this.jqm.getList(sb.toString(), ExamInfoUserDTO.class);	
		return list;
	}
	/**
	 * 
	     * <p>Title: getFlowExamInfoUserListz_dbgj</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @param userid
	     * @param centernum
	     * @param pagesize
	     * @param pageno
	     * @param user
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.ExamFlowService#getFlowExamInfoUserListz_dbgj(com.hjw.wst.model.ExamFlowModel, long, java.lang.String, int, int, com.hjw.wst.DTO.UserDTO)
	 */
	public PageReturnDTO getFlowExamInfoUserListz_dbgj(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno,UserDTO user)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");		
		sb.append(" order by zdate desc ");
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.final_date,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.z0,d.z1,d.z0date,d.z1date,d.c,d.cdate,d.f,d.fdate,d.zdate,"
				+ "z0user.chi_name as z0user,z1user.chi_name as z1user,cuser.chi_name as cuser,fuser.chi_name as fuser, ");
		sb.append("(case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=c.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");		
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and j.center_num = '"+centernum+"' ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" left join user_usr z0user on z0user.id=d.z0creater ");
		sb.append(" left join user_usr z1user on z1user.id=d.z1creater ");
		sb.append(" left join user_usr cuser on cuser.id=d.ccreater ");
		sb.append(" left join user_usr fuser on fuser.id=d.fcreater ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
        //sb.append(" and (d.p0creater="+userid+" or d.p1creater="+userid+") ");        
        
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
    
    	sb.append(" and d.c1 = 1 ");
		sb1.append(" and d.c1 = 1 ");

			
      if(model.getDoctor_id()>0){
    	  sb.append(" and d.zcreater = '"+model.getDoctor_id()+"' ");
		  sb1.append(" and d.zcreater = '"+model.getDoctor_id()+"' ");
      }
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59' ");
				
				sb1.append(" and d.zdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.zdate <= '" + model.getTime2() + " 23:59:59' ");
				
			}		

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: insertExamflowzun   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String insertExamflowzun(String exam_num, UserDTO user) throws ServiceException {
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if (((cis.getC() == 1)|| (cis.getF() == 1)) ){
					message = "error-已经终审或终检，操作不能继续";
				}else if(cis.getZ() == 0){
					message = "error-终检室还没有接收，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setZ(0);
					cis.setZdate(DateTimeUtil.parse());
					cis.setZcreater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("z");
					efl.setFlow_type(0);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
	//报告批量发送给审核
	@SuppressWarnings("unchecked")
	public String flowexamsendchk(String ids, UserDTO user, long c0createrid) throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			int count = 0;
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					List<ExamInfo> examInfoList = this.qm.find("from ExamInfo where exam_num = '"
							+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
					ExamInfo examInfo = examInfoList.get(0);
					List<ExamSummaryReject> list = this.qm.find("from ExamSummaryReject e where e.done_status = 0 and e.examinfo_id = " + examInfo.getId());
					if(examInfo.getWuxuzongjian() == 2) {
						message="error-此报告无需总检，操作不能继续";
					}else if(cis.getC1() == 1){
						message="error-已发送给审核医生，操作不能继续";
					}else if(list.size() > 0){
						message="error-已驳回给主检医生，主检医生未处理，操作不能继续";
					}else {
						cis.setZ(1);						
						cis.setZcreater(user.getUserid());
						cis.setZdate(DateTimeUtil.parse());
						cis.setC0(1);						
						cis.setC0creater(c0createrid);
						cis.setC0date(DateTimeUtil.parse());
						cis.setC1(1);		
						cis.setC1creater(c0createrid);
						cis.setC1date(DateTimeUtil.parse());
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						count++;
					
						ExamFlowLog efl= new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("c0");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
						message="ok-成功上传1份";
					}
				}
			}
			if(exam_nums.length > 1){
				message="ok-成功上传" + count + "份";
			}
		}
		return message;
	}
	
	
	public boolean checkHealthDepId(long exam_id, String deptids, String center_num) throws ServiceException {
		Connection tjtmpconnect = null;
		boolean flag = false;
		try {
			tjtmpconnect = this.jqm.getConnection();
			//安排部门对应健管报告
			/*String sb1 = "select ci.dep_id from examinfo_charging_item eci,charging_item ci "
					+ "where eci.charge_item_id=ci.id and eci.isActive='Y' and ci.isActive='Y' "
					+ "and eci.pay_status <>'M' and ci.dep_id in(" + deptids + ") and eci.examinfo_id='" + exam_id
					+ "' ";*/
			//按照收费项目对应健管报告
			String sb1 = "select eci.id from examinfo_charging_item eci "
					+ "where eci.charge_item_id in(" + deptids + ") and eci.isActive='Y' and eci.pay_status <>'M' and eci.center_num = '"+center_num+"'"
							+ " and eci.examinfo_id='" + exam_id
					+ "' ";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			if (rs1.next()) {
				flag = true;
			}
			rs1.close();
		} catch (SQLException ex) {
		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return flag;
	}

	//打印室接收未审核的导检单
	@SuppressWarnings("unchecked")
	@Override
	public String insertExamflowp(String exam_num, UserDTO user, String upload_flow) throws ServiceException {
		String message = "";

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
					cis = invoiceList.get(0);
					cis.setP0(1);
					cis.setP0date(DateTimeUtil.parse());
					cis.setP0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("p0");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				} else {
				message = "error-核收表无此记录";
			}
			
		}
		return message;
	}
	@SuppressWarnings("unchecked")
	public String insertExamflowp0(String exam_num, UserDTO user) throws ServiceException {
		String message = "";

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if ((cis.getZ1() == 0) && (cis.getF() == 0))
				{	
//					ExamFlowLog efl = new ExamFlowLog();
//					efl.setExam_num(examinfo.getExam_num());
//					efl.setFlow_code("dy");
//					efl.setFlow_type(1);
//					efl.setSendcreater(user.getUserid());
//					efl.setCenter_num(user.getCenter_num());
//					efl.setSenddate(DateTimeUtil.parse());
//					this.pm.save(efl);
					message = "error-导检单未总检，打印室不能操作";
				}else if(cis.getP0() == 1){
					message = "error-打印室已经确认过了，操作不能继续";
				}else if(cis.getC() == 0){
//					ExamFlowLog efl = new ExamFlowLog();
//					efl.setExam_num(examinfo.getExam_num());
//					efl.setFlow_code("dy");
//					efl.setFlow_type(1);
//					efl.setSendcreater(user.getUserid());
//					efl.setCenter_num(user.getCenter_num());
//					efl.setSenddate(DateTimeUtil.parse());
//					this.pm.save(efl);
					message = "error-导检单未审核";	
				}else if(cis.getF0() == 1 && cis.getF() == 0){
					message = "error-导检单未复审";
				}else if(cis.getP0() == 1){
					message = "error-打印室已经确认过了，操作不能继续";
				}else if(cis.getP1() == 1){
					message = "error-打印室已经上传了，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setP0(1);
					cis.setP0date(DateTimeUtil.parse());
					cis.setP0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("p0");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
    //查询报告是否打印
	public String check_print(String exam_num) throws ServiceException {
		String message = "操作失败";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else if(!StringUtil.isEmpty(examinfo.getIs_report_print())){
			message = "ok-"+examinfo.getIs_report_print();
		}
		return message;
	}
  //打印室 	取消接收
	@SuppressWarnings("unchecked")
	public String insertExamflowp0un(String exam_num, UserDTO user) throws ServiceException {
		String message = "";

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if(cis.getP0() == 0){
					message = "error-打印室还没有接收，操作不能继续";
				}else if(cis.getP1() == 1){
					message = "error-打印室已经上传了，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setP0(0);
					cis.setP0date(DateTimeUtil.parse());
					cis.setP0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("p0");
					efl.setFlow_type(0);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
	//打印室 查询人员列表
	public PageReturnDTO getFlowExamInfoUserListp(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno, String sort, String order,UserDTO user)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限  Y有 N无
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);
		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");
		if(sort != null && !"".equals(sort)){
			sb.append(" order by "+sort+" "+order);
		}else{
			sb.append(" order by p0date desc,p1date desc ");
		}
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.p0,d.p1,d.p0date,d.p1date,d.t ");
		sb.append(" ,case when hf.exam_id> 0 then '有' else '无' end as marker, uu.chi_name as printerName,c.Report_Print_Date as printdate");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr uu on uu.id=c.Report_Print_UserId  ");		
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and j.center_num = '"+centernum+"'  ");
		sb.append(" left join exam_health hf on hf.exam_id = c.id ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
		sb1.append("select count(*) as counts from customer_info a ,exam_info c left join exam_health hf on hf.exam_id = c.id,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			if(userid>0){
				sb.append(" and (d.p0creater="+userid+" or d.p1creater="+userid+") ");     
				sb1.append(" and (d.p0creater="+userid+" or d.p1creater="+userid+") "); 
			}
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.p0creater in ("+userids+") or d.p1creater in ("+userids+") ) ");  
			sb1.append(" and (d.p0creater in ("+userids+") or d.p1creater in ("+userids+") ) ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getUsername())) {
			sb.append(" and a.user_name like '%" + model.getUsername() + "%' ");
			sb1.append(" and a.user_name like '%" + model.getUsername() + "%' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if(model.getIsVip() != null && !"".equals(model.getIsVip())){
			if("Y".equals(model.getIsVip())){
				sb.append(" and c.vipflag in ('2','3')");
				sb1.append(" and c.vipflag in ('2','3')");
			}else{
				sb.append(" and (c.vipflag = '1' or c.vipflag is null)");
				sb1.append(" and (c.vipflag = '1' or c.vipflag is null)");
			}
		}
		if(model.getHealth_report() != null && !"".equals(model.getHealth_report())){
			if("Y".equals(model.getHealth_report())){
				sb.append(" and hf.exam_id> 0 ");
				sb1.append(" and hf.exam_id> 0 ");
			}else{
				sb.append(" and hf.exam_id is null ");
				sb1.append(" and hf.exam_id is null ");
			}
		}
		
		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (model.getBatch_id() > 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (model.getGroup_id() > 0) {
			sb.append(" and c.group_id = " + model.getGroup_id() + " ");
			sb1.append(" and c.group_id = " + model.getGroup_id() + " ");
		}
		
		if (model.getSet_id() > 0) {
			sb.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
			sb1.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
		}
		if (model.getPrinter() > 0) {
			sb.append("and c.Report_Print_UserId = '"+model.getPrinter()+"'");
			sb1.append("and c.Report_Print_UserId = '"+model.getPrinter()+"'");
		}
		
		if (model.getPrintFlag() == 1) {
			sb.append("and (c.Report_Print_UserId  is null or c.Report_Print_UserId = '' )");
			sb1.append("and (c.Report_Print_UserId  is null or c.Report_Print_UserId = '' )");
		}
		if (model.getPrintFlag() == 2) {
			sb.append(" and (c.Report_Print_UserId  is not null and c.Report_Print_UserId != '' )");
			sb1.append(" and (c.Report_Print_UserId  is not null and c.Report_Print_UserId != '' )");
		}
		if ((!StringUtil.isEmpty(model.getPrintTime1())) && (!StringUtil.isEmpty(model.getPrintTime2()))) {
			sb.append(" and (c.Report_Print_Date >= '" + model.getPrintTime1() + " 00:00:00' ");
			sb.append(" and c.Report_Print_Date <= '" + model.getPrintTime2() + " 23:59:59') ");
			
			sb1.append(" and (c.Report_Print_Date >= '" + model.getPrintTime1() + " 00:00:00' ");
			sb1.append(" and c.Report_Print_Date <= '" + model.getPrintTime2() + " 23:59:59') ");
		}
		
		if(model.getPtype()==0){
			sb.append(" and d.p0 = 1 ");
			sb1.append(" and d.p0 = 1 ");
		}else if(model.getPtype()==1){
			sb.append(" and d.p0 = 1 and d.p1 = 0 ");
			sb1.append(" and d.p0 = 1 and d.p1 = 0  ");
		}else if(model.getPtype()==2){
			sb.append(" and d.p0 = 1 and d.p1 = 1 ");
			sb1.append(" and d.p0 = 1 and d.p1 = 1 ");
		}		

		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and ((d.p0date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.p0date <= '" + model.getTime2() + " 23:59:59') ");
			sb.append(" or (d.p1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.p1date <= '" + model.getTime2() + " 23:59:59')) ");
			sb1.append(" and ((d.p0date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.p0date <= '" + model.getTime2() + " 23:59:59') ");
			sb1.append(" or (d.p1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.p1date <= '" + model.getTime2() + " 23:59:59')) ");
		}		
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

//		System.out.println("----" + sb.toString());
//		System.out.println("==========="+sb1.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	//打印室 上传信息
	@SuppressWarnings("unchecked")
	public String flowexamp1insert(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			int count = 0;
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getP0()==0){
						message="error-导检单未确认接受，操作不能继续";
					}else if(cis.getP1()==1){
						message="error-导检单已经上传，操作不能继续";
					}else{
						cis.setP1(1);
						cis.setP1creater(user.getUserid());
						cis.setP1date(DateTimeUtil.parse());
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						count++;
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("p1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
					}
				}
			}
			message="ok-成功上传" + count + "份";
		}
		return message;
	}
	//打印室-取消上传
	@SuppressWarnings("unchecked")
	public String flowexamp1delete(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getP0()==0){
						message="error-打印室未确认接受，操作不能继续";
					}else if(cis.getP1()==0){
						message="error-打印室未确认上传，操作不能继续";
					}else if ((cis.getE1()==1)||(cis.getE0()==1)){
						message="error-体检报告已经发出，操作不能继续";
					}else{
						cis.setP1(0);
						cis.setP1creater(0);
						cis.setP1date(null);
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("-p1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						pm.save(efl);
						message="ok-操作成功";
					}
				}
			}
			
		}
		return message;
	}
	
	@Override
	public PageReturnDTO getPre_receive(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");		
		sb.append(" order by p1date desc ");
		sb.append(") AS ROWID,* FROM (");
		
		sb.append(" select count(*) as num,d.P1date,uu.chi_name as P0creater,uu2.chi_name as p1creater ");
		sb.append(" from exam_flow_config d ");
		sb.append(" left join user_usr uu on d.p0creater = uu.id ");
		sb.append(" left join user_usr uu2 on d.p1creater = uu2.id ");
		sb.append(" where d.p0 =1 and d.p1=1 and d.e0=0 ");
		sb.append(" and d.center_num='" + centernum + "' ");
        
		sb1.append("select count(tmp.num) as counts from (");
		
		sb1.append(" select count(*) as num,d.P1date,uu.chi_name as P0creater,uu2.chi_name as p1creater ");
		sb1.append(" from exam_flow_config d ");
		sb1.append(" left join user_usr uu on d.p0creater = uu.id ");
		sb1.append(" left join user_usr uu2 on d.p1creater = uu2.id ");
		sb1.append(" where d.p0 =1 and d.p1=1 and d.e0=0 ");
		sb1.append(" and d.center_num='" + centernum + "' ");
		
		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and d.p1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.p1date <= '" + model.getTime2() + " 23:59:59' ");

			sb1.append(" and d.p1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.p1date <= '" + model.getTime2() + " 23:59:59' ");
		}
		
		sb.append("  group by d.P1date,uu.chi_name, uu2.chi_name ");
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");
		sb1.append("  group by d.P1date,uu.chi_name, uu2.chi_name ");
		sb1.append(") tmp ");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO getPre_receive_details(ExamFlowModel model, long userid, String centernum, int pagesize, int pageno)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");

		
		sb.append(" order by p0date desc,p1date desc ");

		sb.append(") AS ROWID,* FROM (");

		sb.append(" select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,m.dep_name,c.company,c.getReportWay,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.p0,d.p1,d.p0date,d.p1date ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and j.center_num = '"+centernum+"' ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
//        sb.append(" and (d.p0creater="+userid+" or d.p1creater="+userid+") ");
        sb.append(" and d.p0 =1 and d.p1=1 and d.e0=0 ");
        
		sb1.append("select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		sb1.append(" and d.p0 =1 and d.p1=1 and d.e0=0 ");

		if (!StringUtil.isEmpty(model.getTime1())) {
			sb.append(" and d.p1date= '" + model.getTime1() + "' ");
		}		

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	@SuppressWarnings("unchecked")
	public JobDTO insertExamflowe0(String exam_num, UserDTO user) throws ServiceException {
		JobDTO message = new JobDTO();

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message.setId("error"); 
            message.setName("error-无效体检编号！");
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if ((cis.getZ1() == 0)||((cis.getC() == 0) && (cis.getF() == 0)) )
				{
					message.setId("error"); 
		            message.setName("error-导检单未审核，收发室不能操作");
				}else if(cis.getP1() == 0){
					message.setId("error"); 
		            message.setName("error-打印室未确认，操作不能继续");
				}else if(cis.getE0() == 1){
					message.setId("error"); 
		            message.setName("error-发送室已经确认过了，操作不能继续");
				}else if(cis.getE1() == 1){
					message.setId("error"); 
		            message.setName("error-发送室已经上传了，操作不能继续");
				}else {
					cis = invoiceList.get(0);
					cis.setE0(1);
					cis.setE0date(DateTimeUtil.parse());
					cis.setE0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("e0");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					
					UUID uuid = UUID.randomUUID();
					String pccode=uuid.toString();
					if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_PRINT_SMS", user.getCenter_num()).getConfig_value()
							.trim())) {
						//短信
					}
					message.setId("ok"); 
					message.setRemark(pccode);
		            message.setName("ok-接收成功！！");
				}
			} else {
				message.setId("error"); 
	            message.setName("error-核收表无此记录");
			}
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: insertExamflowe0   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO insertExamflowe0_dbgj(String exam_num, UserDTO user) throws ServiceException {
		JobDTO message = new JobDTO();

		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message.setId("error"); 
            message.setName("error-无效体检编号！");
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				 if(cis.getE0() == 1){
					message.setId("error"); 
		            message.setName("error-发送室已经确认过了，操作不能继续");
				}else if(cis.getE1() == 1){
					message.setId("error"); 
		            message.setName("error-发送室已经上传了，操作不能继续");
				}else {
					cis = invoiceList.get(0);
					
					cis.setP0(1);
					cis.setP0date(DateTimeUtil.parse());
					cis.setP0creater(user.getUserid());
					
					cis.setP1(1);
					cis.setP1date(DateTimeUtil.parse());
					cis.setP1creater(user.getUserid());
					
					cis.setE0(1);
					cis.setE0date(DateTimeUtil.parse());
					cis.setE0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("e0");
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					
					UUID uuid = UUID.randomUUID();
					String pccode=uuid.toString();
					if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_PRINT_SMS", user.getCenter_num()).getConfig_value()
							.trim())) {
						//短信
					}
					message.setId("ok"); 
					message.setRemark(pccode);
		            message.setName("ok-接收成功！！");
				}
			} else {
				message.setId("error"); 
	            message.setName("error-核收表无此记录");
			}
		}
		return message;
	}
	
	//收发室取消接收
	@SuppressWarnings("unchecked")
	public String insertExamflowe0un(String exam_num, UserDTO user) throws ServiceException {
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				if(cis.getE0() == 0){
					message = "error-发送室还没有接收，操作不能继续";
				}else if(cis.getE1() == 1){
					message = "error-发送室已经上传了，操作不能继续";
				}else {
					cis = invoiceList.get(0);
					cis.setE0(0);
					cis.setE0date(DateTimeUtil.parse());
					cis.setE0creater(user.getUserid());
					cis.setCenter_num(user.getCenter_num());
					this.pm.update(cis);

					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code("e0");
					efl.setFlow_type(0);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-核收表无此记录";
			}
		}
		return message;
	}
	//收发室 查询
	public PageReturnDTO getFlowExamInfoUserListe(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno,UserDTO user)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");		
		sb.append(" order by e0date desc,e1date desc ");
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor,CONVERT(varchar(1),j.report_class) as report_class,cev.vip_name as vipflag");
		sb.append(" ,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,c.getReportWay,"
				+ "c.register_date,c.join_date,c.exam_times,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.edesc,d.e0,d.e1,d.e0date,xx.chi_name as e1creater,d.e1date,d.t ");
		sb.append(" ,c.is_report_print,c.is_report_tidy, (case when r.receive_type is null then '0' else r.receive_type end) as receive_type1, "
				+ " r.receive_address,r.receive_remark,r.receive_name,r.receive_date,r.receive_type ");
		sb.append(" ,dbo.GetItemNameByExamIdAndDepid(c.exam_num,'CT') as ctname,dbo.GetItemNameByExamIdAndDepid(c.id,'MR') as mrname,dbo.GetItemNameByExamIdAndDepid(c.id,'DR') as drname ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join report_receive r on r.exam_num = c.exam_num ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and j.center_num = '"+centernum+"' ");
		sb.append(" left join config_exam_vip cev on cev.id=c.vipflag ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" left join user_usr xx on xx.id=d.e1creater ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
        
		sb1.append("select count(*) as counts from customer_info a ,exam_info c ");
		sb1.append(" left join report_receive r on r.exam_num = c.exam_num ");
		sb1.append(" left join exam_summary  j on j.exam_info_id = c.id  and j.center_num = '"+centernum+"' ");
		sb1.append(" ,exam_flow_config d ");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		if(model.getReceive_type() != null && !"".equals(model.getReceive_type())){
			if("0".equals(model.getReceive_type())){
				sb.append(" and r.receive_type is null ");
				sb1.append(" and r.receive_type is null ");
			}else if("1&2".equals(model.getReceive_type())){
				sb.append(" and (r.receive_type = 1 or r.receive_type = 2) ");
				sb1.append(" and (r.receive_type = 1 or r.receive_type = 2) ");
			}else{
				sb.append(" and r.receive_type = " + model.getReceive_type());
				sb1.append(" and r.receive_type = " + model.getReceive_type());
			}
		}
		if(model.getReceive_date() != null && !"".equals(model.getReceive_date())){
			sb.append(" and r.receive_date = '"+model.getReceive_date()+"'" );
			sb1.append(" and r.receive_date = '"+model.getReceive_date()+"'" );
		}
		if(model.getReceive_name() != null && !"".equals(model.getReceive_name())){
			sb.append(" and r.receive_name = '"+model.getReceive_name()+"'" );
			sb1.append(" and r.receive_name = '"+model.getReceive_name()+"'" );
		}
		
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			if(userid>0){
				sb.append(" and (d.e0creater="+userid+" or d.e1creater="+userid+") "); 
				sb1.append(" and (d.e0creater="+userid+" or d.e1creater="+userid+") "); 
			}
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.e0creater in ("+userids+") or d.e1creater in ("+userids+") ) ");  
			sb1.append(" and (d.e0creater in ("+userids+") or d.e1creater in ("+userids+") ) ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' "
						+ "or a.arch_num = '" + model.getExam_num() + "' "
						+ "or a.user_name = '" + model.getExam_num() + "' "
						+ "or c.phone = '" + model.getExam_num() + "' "
						+ "or a.id_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' "
						+ "or a.arch_num = '" + model.getExam_num() + "' "
						+ "or a.user_name = '" + model.getExam_num() + "' "
						+ "or c.phone = '" + model.getExam_num() + "' "
						+ "or a.id_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getGetReportWay())) {
			sb.append(" and c.getReportWay = '" + model.getGetReportWay() + "' ");
			sb1.append(" and c.getReportWay = '" + model.getGetReportWay() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getReport_class())) {
			sb.append(" and j.report_class = '" + model.getReport_class() + "' ");
			sb1.append(" and j.report_class = '" + model.getReport_class() + "' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getRemark1())) {
			sb.append(" and d.t = '" + model.getRemark1() + "' ");
			sb1.append(" and d.t = '" + model.getRemark1() + "' ");
		}
		
		if (model.getDep_id() > 0) {
			sb.append(" and c._level = " + model.getDep_id() + " ");
			sb1.append(" and c._level = " + model.getDep_id() + " ");
		}
		
		if(model.getPtype()==0){
			sb.append(" and d.e0 = 1 ");
			sb1.append(" and d.e0 = 1 ");
		}else if(model.getPtype()==1){
			sb.append(" and d.e0 = 1 and d.e1 = 0 ");
			sb1.append(" and d.e0 = 1 and d.e1 = 0  ");
		}else if(model.getPtype()==2){
			sb.append(" and d.e0 = 1 and d.e1 = 1  ");
			sb1.append(" and d.e0 = 1 and d.e1 = 1  ");
		}
		
		if (!StringUtil.isEmpty(model.getVipflag())) {
			sb.append(" and c.vipflag = '" + model.getVipflag() + "'");
			sb1.append(" and c.vipflag = '" + model.getVipflag() + "'");
		}

		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and ((d.e0date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.e0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb.append(" or (d.e1date >= '" + model.getTime1() + " 00:00:00' ");
			sb.append(" and d.e1date <= '" + model.getTime2() + " 23:59:59')) ");


			sb1.append(" and ((d.e0date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.e0date <= '" + model.getTime2() + " 23:59:59') ");
			
			sb1.append(" or (d.e1date >= '" + model.getTime1() + " 00:00:00' ");
			sb1.append(" and d.e1date <= '" + model.getTime2() + " 23:59:59')) ");
		}		
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");
		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	//收发室 全局查询
	public PageReturnDTO flowexameListe_allflow(ExamFlowModel model, long userid, String centernum, int pagesize, int pageno,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);
		
		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");		
		sb.append(" order by e0date desc,e1date desc ");
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor,CONVERT(varchar(1),j.report_class) as report_class,cev.vip_name as vipflag");
		sb.append(" ,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.edesc,d.e0,d.e1,d.e0date,xx.chi_name as e1creater,d.e1date,d.t ");
		sb.append(" ,c.is_report_print,c.is_report_tidy, (case when r.receive_type is null then '0' else r.receive_type end) as receive_type1, "
				+ " r.receive_address,r.receive_remark,r.receive_name,r.receive_date ");
		
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join report_receive r on r.exam_num = c.exam_num ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and j.center_num = '"+centernum+"' ");
		sb.append(" left join config_exam_vip cev on cev.id=c.vipflag ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" left join user_usr xx on xx.id=d.e1creater ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
		
		sb1.append("select count(*) as counts from customer_info a ,exam_info c ");
		sb1.append(" left join report_receive r on r.exam_num = c.exam_num ");
		sb1.append(" left join exam_summary  j on j.exam_info_id = c.id  ");
		sb1.append(" ,exam_flow_config d ");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		/*if(model.getReceive_type() != null && !"".equals(model.getReceive_type())){
			if("0".equals(model.getReceive_type())){
				sb.append(" and r.receive_type is null ");
				sb1.append(" and r.receive_type is null ");
			}else if("1&2".equals(model.getReceive_type())){
				sb.append(" and (r.receive_type = 1 or r.receive_type = 2) ");
				sb1.append(" and (r.receive_type = 1 or r.receive_type = 2) ");
			}else{
				sb.append(" and r.receive_type = " + model.getReceive_type());
				sb1.append(" and r.receive_type = " + model.getReceive_type());
			}
		}
		if(model.getReceive_date() != null && !"".equals(model.getReceive_date())){
			sb.append(" and r.receive_date = '"+model.getReceive_date()+"'" );
			sb1.append(" and r.receive_date = '"+model.getReceive_date()+"'" );
		}
		if(model.getReceive_name() != null && !"".equals(model.getReceive_name())){
			sb.append(" and r.receive_name = '"+model.getReceive_name()+"'" );
			sb1.append(" and r.receive_name = '"+model.getReceive_name()+"'" );
		}
		
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			if(userid>0){
				sb.append(" and (d.e0creater="+userid+" or d.e1creater="+userid+") "); 
				sb1.append(" and (d.e0creater="+userid+" or d.e1creater="+userid+") "); 
			}
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.e0creater in ("+userids+") or d.e1creater in ("+userids+") ) ");  
			sb1.append(" and (d.e0creater in ("+userids+") or d.e1creater in ("+userids+") ) ");
		}*/
		
		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' "
					+ "or a.arch_num = '" + model.getExam_num() + "' "
					+ "or a.user_name = '" + model.getExam_num() + "' "
					+ "or c.phone = '" + model.getExam_num() + "' "
					+ "or a.id_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' "
					+ "or a.arch_num = '" + model.getExam_num() + "' "
					+ "or a.user_name = '" + model.getExam_num() + "' "
					+ "or c.phone = '" + model.getExam_num() + "' "
					+ "or a.id_num = '" + model.getExam_num() + "') ");
		}
		
		/*if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getReport_class())) {
			sb.append(" and j.report_class = '" + model.getReport_class() + "' ");
			sb1.append(" and j.report_class = '" + model.getReport_class() + "' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getRemark1())) {
			sb.append(" and d.t = '" + model.getRemark1() + "' ");
			sb1.append(" and d.t = '" + model.getRemark1() + "' ");
		}
		
		if (model.getDep_id() > 0) {
			sb.append(" and c._level = " + model.getDep_id() + " ");
			sb1.append(" and c._level = " + model.getDep_id() + " ");
		}
		
		if(model.getPtype()==0){
//			sb.append(" and d.e0 = 1 ");
//			sb1.append(" and d.e0 = 1 ");
		}else if(model.getPtype()==1){
			sb.append(" and d.e0 = 1 and d.e1 = 0 ");
			sb1.append(" and d.e0 = 1 and d.e1 = 0  ");
		}else if(model.getPtype()==2){
			sb.append(" and d.e0 = 1 and d.e1 = 1  ");
			sb1.append(" and d.e0 = 1 and d.e1 = 1  ");
		}
		
		if (!StringUtil.isEmpty(model.getVipflag())) {
			sb.append(" and c.vipflag = '" + model.getVipflag() + "'");
			sb1.append(" and c.vipflag = '" + model.getVipflag() + "'");
		}
		
		if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
			sb.append(" and ((CONVERT(varchar(50),d.e0date,23) >= '" + model.getTime1() + "' ");
			sb.append(" and CONVERT(varchar(50),d.e0date,23) <= '" + model.getTime2() + "') ");
			
			sb.append(" or (CONVERT(varchar(50),d.e1date,23) >= '" + model.getTime1() + "' ");
			sb.append(" and CONVERT(varchar(50),d.e1date,23) <= '" + model.getTime2() + "')) ");
			
			
			sb1.append(" and ((CONVERT(varchar(50),d.e0date,23) >= '" + model.getTime1() + "' ");
			sb1.append(" and CONVERT(varchar(50),d.e0date,23) <= '" + model.getTime2() + "') ");
			
			sb1.append(" or (CONVERT(varchar(50),d.e1date,23) >= '" + model.getTime1() + "' ");
			sb1.append(" and CONVERT(varchar(50),d.e1date,23) <= '" + model.getTime2() + "')) ");
			
		}*/		
		
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");
		
		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				
				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}
				
				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}
				
				try {
					String sql = "select top 1 fc.flow_name from exam_flow_log efl "
							+ " left join flow_config fc on fc.flow_code = efl.flow_code and fc.is_active = 'Y' "
							+ " where exam_num =  '"+eu.getExam_num()+"' and efl.center_num='"+user.getCenter_num()+"'  order by efl.id desc";
					RowSet rs = this.jqm.getRowSet(sql);
					if(rs.next()) {
						String flow_name = rs.getString("flow_name");
						eu.setFlow_name(flow_name);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	@SuppressWarnings("unchecked")
	public List<ExamInfoDTO> queryExamInfoHasCTMR(String exam_num, String center_num) throws ServiceException {
		String sql = " select ei.*, ci.arch_num from exam_info ei, customer_info ci where ci.id = ei.customer_id and ei.id in ( "
				+ " select distinct examinfo_id from examinfo_charging_item eci,charging_item ci,department_dep dd "
				+ " where eci.charge_item_id = ci.id and ci.dep_id = dd.id and eci.exam_status = 'Y' and eci.center_num = '"+center_num+"' and dd.dep_num in ('CT','MR') "
				+ " ) and ei.exam_num in ('"+exam_num.replaceAll(",", "','")+"')";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveEdesc(String exam_num, String edesc, UserDTO user) throws ServiceException {
		String message="";
		List<ExamFlowConfig> list = this.qm.find("from ExamFlowConfig where exam_num = '"
				+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
		if ((list != null) && (list.size() > 0)) {
			ExamFlowConfig efc = new ExamFlowConfig();
			efc=list.get(0);
			efc.setEdesc(edesc);
			pm.update(efc);
		}
		message="ok-操作成功";
		return message;
	}
	//邮寄更改流程
	@SuppressWarnings("unchecked")
	public String flowexame1insert(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			int count = 0;
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getE0()==0){
						message="error-导检单" +exam_num+ "未确认接受，操作不能继续";
					}else if(cis.getE1()==1){
						message="error-导检单" +exam_num+ "已经上传，操作不能继续";
					}else{
						cis.setE1(1);
						cis.setE1creater(user.getUserid());
						cis.setE1date(DateTimeUtil.parse());
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						count++;
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("e1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						this.pm.save(efl);
						message="ok-成功上传" + count + "份";
					}
				}
			}
		}
		return message;
	}
	
	//撤销邮寄/自取
	@SuppressWarnings("unchecked")
	public String flowexame1delete(String ids,UserDTO user)throws ServiceException{
		String message="";
		if(StringUtil.isEmpty(ids)){
			message="error-无效记录";
		}else{
			String[] exam_nums=ids.split(",");
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];
				List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
						+ exam_num + "' and center_num='" + user.getCenter_num() + "'");
				if ((invoiceList != null) && (invoiceList.size() > 0)) {
					ExamFlowConfig cis = new ExamFlowConfig();
					cis=invoiceList.get(0);
					if(cis.getE0()==0){
						message="error-【"+exam_num+"】发送室未确认接收，操作不能继续";
						return message;
					}else if(cis.getE1()==0){
						message="error-【"+exam_num+"】发送室未确认上传，操作不能继续";
						return message;
					}else if(cis.getM()==1){
						message="error-【"+exam_num+"】解读室已解读，操作不能继续";
						return message;
					}else{
						cis.setE1(0);
						cis.setE1creater(0);
						cis.setE1date(null);
						cis.setCenter_num(user.getCenter_num());
						pm.update(cis);
						
						ExamFlowLog efl = new ExamFlowLog();
						efl.setExam_num(exam_num);
						efl.setFlow_code("-e1");
						efl.setFlow_type(1);
						efl.setSendcreater(user.getUserid());
						efl.setCenter_num(user.getCenter_num());
						efl.setSenddate(DateTimeUtil.parse());
						pm.save(efl);
						message="ok-操作成功";
					}
				}
			}
			
		}
		return message;
	}
	
	public PageReturnDTO getFlowExamInfoUserListm(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");
		
		sb.append(" order by mdate desc ");
		sb.append(") AS ROWID,* FROM (");
		sb.append(" select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.update_time,c.introducer,"
				+ "d.m,d.mdate ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and center_num = '"+centernum+"' ");
		sb.append(" ,exam_flow_config d ");
		sb.append(" where c.customer_id=a.id and d.exam_num=c.exam_num ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");
		sb.append(" and d.center_num='" + centernum + "' ");
        
        
		sb1.append(" select count(*) as counts from customer_info a ,exam_info c,exam_flow_config d");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' and d.exam_num=c.exam_num ");
		sb1.append(" and  c.center_num='" + centernum + "' and d.center_num='" + centernum + "' ");
		
		if(model.getRemark().indexOf(",") < 0) {
			if(!"".equals(model.getRemark())) {
				userid=Long.valueOf(model.getRemark().replaceAll("'", "")).longValue();
			}
			if(userid>0){
				sb.append(" and d.mcreater="+userid+" ");
				sb1.append(" and d.mcreater="+userid+" ");
			}
		} else {
			String userids = model.getRemark();
			sb.append(" and (d.mcreater in ("+userids+") ) ");  
			sb1.append(" and (d.mcreater in ("+userids+") ) ");
		}

		if (!StringUtil.isEmpty(model.getExam_num())) {
			sb.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
			sb1.append(" and (c.exam_num = '" + model.getExam_num() + "' or a.arch_num = '" + model.getExam_num() + "') ");
		}
		
		if (!StringUtil.isEmpty(model.getExam_type()) && !"A".equals(model.getExam_type())) {
			sb.append(" and c.exam_type = '" + model.getExam_type() + "' ");
			sb1.append(" and c.exam_type = '" + model.getExam_type() + "' ");
		}
		
		if (!StringUtil.isEmpty(model.getUsername())) {
			sb.append(" and a.user_name like '%" + model.getUsername() + "%' ");
			sb1.append(" and a.user_name like '%" + model.getUsername() + "%' ");
		}
		
		if ("F".equals(model.getSex())) {
			sb.append(" and a.sex = '女' ");
			sb1.append(" and a.sex = '女' ");
		} else if ("M".equals(model.getSex())) {
			sb.append(" and a.sex = '男' ");
			sb1.append(" and a.sex = '男' ");
		}
		
		if (!StringUtil.isEmpty(model.getCompany())) {
			sb.append(" and c.company = '" + model.getCompany() + "' ");
			sb1.append(" and c.company = '" + model.getCompany() + "' ");
		}
		
		if (model.getBatch_id() > 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (model.getGroup_id() > 0) {
			sb.append(" and c.group_id = " + model.getGroup_id() + " ");
			sb1.append(" and c.group_id = " + model.getGroup_id() + " ");
		}
		
		if (model.getSet_id() > 0) {
			sb.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
			sb1.append(" and " + model.getSet_id() + " in (select exam_set_id from examinfo_set es where es.examinfo_id=c.id and es.isActive='Y') ");
		}
		
		if(model.getPtype()==0){
			sb.append(" and (d.e0 = 1 or d.e1=1) ");
			sb1.append(" and (d.e0 = 1 or d.e1=1) ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and ((d.e0date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.e0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb.append(" or (d.e1date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.e1date <= '" + model.getTime2() + " 23:59:59')) ");

				sb1.append(" and ((d.e0date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.e0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" or (d.e1date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.e1date <= '" + model.getTime2() + " 23:59:59')) ");

			}	
			
		}else if(model.getPtype()==1){
			sb.append(" and (d.e0 = 1 or d.e1=1) and d.m = 0 ");
			sb1.append("  and  (d.e0 = 1 or d.e1=1) and d.m = 0  ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and ((d.e0date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.e0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb.append(" or (d.e1date >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.e1date <= '" + model.getTime2() + " 23:59:59')) ");


				sb1.append(" and ((d.e0date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.e0date <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" or (d.e1date >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.e1date <= '" + model.getTime2() + " 23:59:59')) ");

			}	
			
		}else if(model.getPtype()==2){
			sb.append(" and (d.e0 = 1 or d.e1=1) and d.m = 1   ");
			sb1.append(" and (d.e0 = 1 or d.e1=1) and d.m = 1   ");
			
			if ((!StringUtil.isEmpty(model.getTime1())) && (!StringUtil.isEmpty(model.getTime2()))) {
				sb.append(" and (d.mdate >= '" + model.getTime1() + " 00:00:00' ");
				sb.append(" and d.mdate <= '" + model.getTime2() + " 23:59:59') ");
				
				sb1.append(" and (d.mdate >= '" + model.getTime1() + " 00:00:00' ");
				sb1.append(" and d.mdate <= '" + model.getTime2() + " 23:59:59') ");
			}	
		}	
		
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		System.out.println("----" + sb.toString());
		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getFlowExamDoc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param mtype
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<JobDTO> getFlowExamDoc(UserDTO user)throws ServiceException{
		Connection connection = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		String datestr = DateTimeUtil.getPastDate(150);
		try {
			connection = this.jqm.getConnection();
			//总个数
			String sql  = " select distinct es.exam_doctor_id,uu.chi_name from exam_summary es,"
					+ "user_usr uu where es.app_type=1 and es.approve_status='B' "
					+ "and es.create_time>'"+datestr+"'  and es.center_num = '"+user.getCenter_num()+"'  "
					+ "and uu.id=es.exam_doctor_id  order by uu.chi_name ";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				JobDTO j=new JobDTO();
				j.setId(rs.getString("exam_doctor_id"));
				j.setName(rs.getString("chi_name"));		
				list.add(j);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 
	     * <p>Title: flowmmessageshow</p>   
	     * <p>Description: </p>   
	     * @param exam_num
	     * @param mtype
	     * @param user
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.ExamFlowService#flowmmessageshow(java.lang.String, java.lang.String, com.hjw.wst.DTO.UserDTO)
	 */
	public String flowmmessageshow(String exam_num,String mtype,UserDTO user)throws ServiceException{
		String message="";
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			//总个数
			String sql  = "select notes from exam_flow_log "
					+ "where exam_num='"+exam_num+"' "
					+ "and flow_code='"+mtype+"' "
					+ "and sendcreater='"+user.getUserid()+"' "
					+ "and flow_type=1 "
					+ "and center_num='"+user.getCenter_num()+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				message = rs.getString("notes");							
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: flowmmessageUpdate   
	     * @Description:报告解读保存内容    
	     * @param: @param exam_num
	     * @param: @param mtype
	     * @param: @param notes
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowmmessageUpdate(String exam_num, String mtype, String notes, UserDTO user) throws ServiceException {
		String message = "";
		ExamInfo examinfo = this.examInfoService.findExamInfo(exam_num);
		if ((examinfo == null) || (examinfo.getId() <= 0)) {
			message = "error-无效体检编号！";
		} else {
			List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '"
					+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if ((invoiceList != null) && (invoiceList.size() > 0)) {
				ExamFlowConfig cis = new ExamFlowConfig();
				cis = invoiceList.get(0);
				cis = invoiceList.get(0);
				cis.setM(1);
				cis.setMdate(DateTimeUtil.parse());
				cis.setMcreater(user.getUserid());
				cis.setCenter_num(user.getCenter_num());
				this.pm.update(cis);

				List<ExamFlowLog> eflList = this.qm.find("from ExamFlowLog where exam_num = '"
						+ examinfo.getExam_num() + "' and center_num='" + user.getCenter_num() + "' and flow_code='"
						+ mtype + "'  and sendcreater='" + user.getUserid() + "' ");
				if ((eflList != null) && (eflList.size() > 0)) {
					ExamFlowLog efl = new ExamFlowLog();
					efl = eflList.get(0);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					efl.setFlow_type(1);
					efl.setNotes(notes);
					this.pm.update(efl);
					message = "ok-操作完成";
				} else {
					ExamFlowLog efl = new ExamFlowLog();
					efl.setExam_num(examinfo.getExam_num());
					efl.setFlow_code(mtype);
					efl.setFlow_type(1);
					efl.setSendcreater(user.getUserid());
					efl.setCenter_num(user.getCenter_num());
					efl.setSenddate(DateTimeUtil.parse());
					efl.setNotes(notes);
					this.pm.save(efl);
					message = "ok-操作完成";
				}
			} else {
				message = "error-无效流程数据！";
			}
		}
		return message;
	}
	
/**
 * 
     * @Title: saveAcceptanceExamInfoFlow   
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @param examid
     * @param: @param exam_num
     * @param: @param acceptance_check
     * @param: @param user
     * @param: @return
     * @param: @throws ServiceException      
     * @return: String      
     * @throws
 */
	public String saveAcceptanceExamInfoFlow(String exam_num, long acceptance_check, UserDTO user)
			throws ServiceException {
		Connection connection = null;
		String message = "error-操作错误";
		try {
			connection = this.jqm.getConnection();
			String sql = "select exam_info_id from Exam_Summary where app_type = '1' and exam_num ='" + exam_num
					+ "' ";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			boolean exist = false;
			if (rs.next()) {
				exist = true;
			}
			rs.close();
			if (exist) {
				if (acceptance_check == 0) {
					sql = "select c from exam_flow_config where center_num='"+user.getCenter_num()+"' and exam_num ='" + exam_num + "' ";
					rs = connection.createStatement().executeQuery(sql);
					exist = true;
					if (rs.next()) {
						if (rs.getInt("c") == 1) {
							exist = false;
						}
					}
					rs.close();
					if (exist) {
						sql = "update Exam_Summary set acceptance_check='0',acceptance_doctor='" + user.getUserid()
								+ "',acceptance_date='" + DateTimeUtil.getDateTime()
								+ "' where app_type = '1' and exam_num ='" + exam_num + "'";
						connection.createStatement().execute(sql);
						sql = "update exam_flow_config set z='0',zcreater='" + user.getUserid() + "',zdate='"
								+ DateTimeUtil.getDateTime() + "' where exam_num ='" + exam_num + "' "
										+ " and center_num = '"+user.getCenter_num()+"'";
						connection.createStatement().execute(sql);
						message = "ok-取消核收完成";
					} else {
						message = "error-报告已经审核，无法取消核收";
					}
				} else {
					sql = "update Exam_Summary set acceptance_check='1',acceptance_doctor='" + user.getUserid()
							+ "',acceptance_date='" + DateTimeUtil.getDateTime()
							+ "' where app_type = '1' and exam_num ='" + exam_num + "'";
					connection.createStatement().execute(sql);
					sql = "update exam_flow_config set z='1',zcreater='" + user.getUserid() + "',zdate='"
							+ DateTimeUtil.getDateTime() + "' where exam_num ='" + exam_num + "'  AND   center_num='"+user.getCenter_num()+"'";
					connection.createStatement().execute(sql);
					message = "ok-核收完成";
				}

			} else {
				message = "error-无此从总检查报告或者属于职业病报告";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return message;
	}

	@Override
	public String flowexambackchk(String exam_num, UserDTO user) throws ServiceException {
		String message="";
		List<ExamFlowConfig> invoiceList = this.qm.find("from ExamFlowConfig where exam_num = '" + exam_num + "' and center_num='" + user.getCenter_num() + "'");
		if ((invoiceList != null) && (invoiceList.size() > 0)) {
			ExamFlowConfig cis = invoiceList.get(0);
			List<ExamInfo> examInfoList = this.qm.find("from ExamInfo where exam_num = '" + exam_num + "' and center_num='" + user.getCenter_num() + "'");
			ExamInfo examInfo = examInfoList.get(0);
			if(examInfo.getWuxuzongjian() == 2) {
				message="error-此报告无需总检，操作不能继续";
			}else if(cis.getC() == 1){
				message="error-此报告已经审核，操作不能继续";
			}else {
				cis.setZ(0);						
				cis.setZcreater(0);
				cis.setZdate(null);
				cis.setC0(0);		
				cis.setC0creater(0);
				cis.setC0date(null);
				cis.setC1(0);		
				cis.setC1creater(0);
				cis.setC1date(null);
				pm.update(cis);
						
				ExamFlowLog efl = new ExamFlowLog();
				efl.setExam_num(exam_num);
				efl.setFlow_code("-c0");
				efl.setFlow_type(1);
				efl.setSendcreater(user.getUserid());
				efl.setCenter_num(user.getCenter_num());
				efl.setSenddate(DateTimeUtil.parse());
				this.pm.save(efl);
				message = "ok-取消发送成功";
			}
		}
		return message;
	}

	@Override
	public ExamSummaryDTO getFlowExamPreviewCount(UserDTO user) throws ServiceException {
		String sql = "select * from (select count(e.id) wz_count from exam_info e,exam_flow_config f where e.exam_num = f.exam_num "
				+ " AND f.center_num = '"+user.getCenter_num()+"'  and f.z1 = 1 and f.c1 = 0 and e.customer_type not in (42,321,271,223,325) "
				+ "and not exists ( select * from exam_summary_reject r where r.done_status = 0 and r.examinfo_id = e.id)) a,"
				+ "(select COUNT(f.id) yz_count from exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' AND f.z = 1 and f.zdate "
				+ "= '"+DateTimeUtil.getDate2()+" 23:59:59' ) b,(select COUNT(f.id) zj_count from exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' AND f.z = 1 and f.zdate = '"+DateTimeUtil.getDate2()+" 23:59:59' and f.zcreater = '"+user.getUserid()+"' ) c";
		List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String addTactics(ExamInfo examInfo, UserDTO user, String tactics_num) throws ServiceException, ParseException {
		 List<CrmVisitPlan> li = this.qm.find("from CrmVisitPlan where tactics_num = '"+tactics_num+"' and exam_num = '"+examInfo.getExam_num()+"'");//是否已存在计划
		 if(li.size() > 0){//已存在
			 return "";
		 }
		 List<CrmPlanTacticsDetail> list = this.qm.find("from CrmPlanTacticsDetail where tactics_num = '"+tactics_num+"'");
		 ExamInfoDTO examInfoForexamNum = customerInfoService.getExamInfoForexamNum(examInfo.getExam_num());
		 String visit_num = GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num());
		if(list.size() > 0){
			CrmVisitPlan crmVisitPlan = new CrmVisitPlan();//计划主表
			crmVisitPlan.setExam_num(examInfo.getExam_num());
			crmVisitPlan.setCreate_time(DateTimeUtil.parse());
			crmVisitPlan.setArch_num(examInfoForexamNum.getArch_num());
//			crmVisitPlan.setCreater(user.getUserid());
			crmVisitPlan.setTactics_num(tactics_num);
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
						
			}
			return "策略计划添加成功。。";
		}else{
			return "没找到关联"+tactics_num+"的计划,请查看配置是否正确。。";
		}
	}

}
