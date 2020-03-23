package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExamSuggestionLogDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamSuggestionLog;
import com.hjw.wst.domain.ExamSummary;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.service.AcceptanceCheckService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.zyb.domain.ExamInfoExt;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class AcceptanceCheckServiceImpl implements AcceptanceCheckService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm; 
	private CustomerInfoService customerInfoService;

	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	
	@Override
	public PageReturnDTO getExamInfoList(AcceptanceCheckModel model, int pagesize, int pageno,String sort,String order,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuilder sqlstr = new StringBuilder();
		sqlstr.append(" select dbo.fun_CharToStar(ei.exam_num,'id_num',ci.id_num,'"+isprivateflag+"') as id_num,ei.exam_num,dbo.fun_CharToStar(ei.exam_num,'name',ci.user_name,'"+isprivateflag+"') as user_name,ci.sex,ei.age,dbo.fun_CharToStar(ei.exam_num,'phone',ei.phone,'"+isprivateflag+"') as phone,dbo.GetExamSetNameByExamID(ei.exam_num) as set_name,"
					 +" ei.exam_type,v.com_name as company,ci.arch_num,convert(varchar(100),ei.join_date,23) as join_date,ei.status,ei.final_doctor,"
					 +" uu.chi_name as app_doctor,ei.id,ct.type_name as customer_type,convert(varchar(100),ei.final_date,23) as final_date,t.old_arch_num,t.up_arch_num_time "
					 +" from exam_info ei "
					 + " left join company_info v on v.id=ei.company_id "
					 + "left join customer_info ci  on ci.id = ei.customer_id "
					 +" left join exam_summary su on su.exam_num = ei.exam_num and su.center_num = '"+user.getCenter_num()+"'  "
					 +" left join user_usr uu on uu.id = su.check_doc "
					 +" left join exam_ext_info t on ei.exam_num=t.exam_num "
					 +" left join customer_type ct on ei.customer_type_id = ct.id where ei.is_Active = 'Y' ");

		int count = 0;
		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {
			sqlstr.append(" and ci.arch_num = '"+model.getArch_num()+"'");
			count ++;
		}
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {
			sqlstr.append(" and ei.exam_num = '"+model.getExam_num()+"'");
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {
			sqlstr.append(" and ci.user_name like '"+model.getUser_name()+"%'");
			count ++;
		}
		if (model.getSex() != null && !"".equals(model.getSex())) {
			sqlstr.append(" and ci.sex = '"+model.getSex()+"'");
		}
		if (model.getCom_id() != null && !"".equals(model.getCom_id())) {
			sqlstr.append(" and ei.company_id = "+ model.getCom_id());
			count ++;
		}
		if (!"".equals(model.getBatch_id())){
			sqlstr.append(" and ei.batch_id = "+model.getBatch_id());
		}
		if (!"".equals(model.getGroup_id())) {
			sqlstr.append(" and ei.group_id = "+model.getGroup_id());
		}
		if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {
			sqlstr.append(" and ei.join_date >= '"+model.getS_join_date() +" 00:00:00.000'");
			count ++;
		}
		if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {
			sqlstr.append(" and ei.join_date < '"+model.getE_join_date()+" 23:59:59.999'");
			count ++;
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())) {
			sqlstr.append(" and ei.exam_type = '"+model.getExam_type()+"'");
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())) {
			sqlstr.append(" and ei.phone = '"+model.getPhone()+"'");
			count ++;
		}
		if(model.getCustomer_type() != null && !"".equals(model.getCustomer_type())){
			sqlstr.append(" and ei.customer_type = '"+model.getCustomer_type()+"'");
			count ++;
		}
		if(sort != null && !"".equals(sort)){
			sqlstr.append(" order by "+sort+" "+order);
		}else{
			sqlstr.append(" order by ei.exam_num desc");
		}
		PageReturnDTO pageDto = new PageReturnDTO();
		if(count == 0){
			return pageDto;
		}
		PageSupport map = this.jqm.getList(sqlstr.toString(), pageno, pagesize, ExamInfoDTO.class);
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreeDTO> getDepAndItemTree(String exam_num, String center_num) throws ServiceException {
		
		String sql =" select a.id,a.item_name as text,a.pid as attributes,a.dep_category as state"
				   +" from (select distinct '0' as pid,convert(varchar(20),d.id) as id,d.dep_name as item_name,"
				   +" d.seq_code,1000 as item_seq,d.dep_category from examinfo_charging_item ec,charging_item c,department_dep d"
				   +" where ec.charging_item_code = c.item_code and c.dep_id = d.id and ec.exam_num = '"+exam_num+"' and ec.isActive='Y'" 
				   +" and ec.pay_status <> 'M' and ec.change_item <> 'C' and ec.exam_status <> 'G' and c.item_category != '耗材类型' and ec.center_num = '"+center_num+"'"
				   +" union all"
				   +" select convert(varchar(20),c.dep_id) as pid,'c'+convert(varchar(20),c.item_code) as id,c.item_name,1000 as seq_code,c.item_seq,d.dep_category from examinfo_charging_item ec,"
				   +" charging_item c,department_dep d where ec.charging_item_code = c.item_code and c.dep_id = d.id and ec.exam_num = '"+exam_num+"' and ec.isActive='Y'"
				   +" and ec.pay_status <> 'M' and ec.change_item <> 'C' and ec.exam_status <> 'G' and c.item_category != '耗材类型' and ec.center_num = '"+center_num+"') as a "
				   +" order by a.seq_code,a.item_seq";
		List<TreeDTO> list = this.jqm.getList(sql, TreeDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommonExamDetailDTO> getPtResultList(String exam_num, String item_code) throws ServiceException{
		String sql =" select c.id,ei.item_name,c.health_level,c.exam_result from common_exam_detail c,"
				   +" examination_item ei,charging_item_exam_item ce where c.item_code = ei.item_num "
				   +" and ei.item_num = ce.item_code and c.exam_num = '"+exam_num+"' and ce.charging_item_code = '"+item_code
				   +"' order by ei.seq_code";
		List<CommonExamDetailDTO> list = this.jqm.getList(sql, CommonExamDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamResultDetailDTO> getHyResultList(String exam_num,String item_code) throws ServiceException{
		String sql =" select e.id,ei.item_name,e.exam_result,e.ref_indicator,e.item_unit,e.ref_value "
				   +" from exam_result_detail e,examination_item ei,charging_item_exam_item ce"
				   +" where e.item_code = ei.item_num and ei.item_num = ce.item_code and e.exam_num = '"+exam_num
				   +"' and ce.charging_item_code = '"+item_code+"' order by ei.seq_code";
		List<ExamResultDetailDTO> list = this.jqm.getList(sql, ExamResultDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewExamDetailDTO> getYxResultList(String exam_num,String item_code) throws ServiceException{
		String sql = " select v.id,p.pacs_req_code,v.exam_desc,v.exam_result,v.exam_doctor,CONVERT(varchar(100),v.exam_date,23) as exam_date "
				   + " from view_exam_detail v,pacs_summary p,sample_demo s,charging_item c"
				   + " where v.pacs_req_code = p.pacs_req_code and p.examinfo_sampleId = s.id and s.id = c.sam_demo_id"
				   + " and v.exam_num = '"+exam_num+"' and c.item_code = '"+item_code+"'";
		List<ViewExamDetailDTO> list = this.jqm.getList(sql, ViewExamDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExamDepResultDTO getExamDoctorAndDepResult(String exam_num, String item_code) throws ServiceException {
		String sql = " select CONVERT(varchar(100),ec.exam_date,23) as exam_date,ec.exam_doctor_name as exam_doctor,er.exam_result_summary from examinfo_charging_item ec" 
				   + " left join charging_item c on ec.charging_item_code = c.item_code"
				   + " left join exam_dep_result er on ec.exam_num = er.exam_num and c.dep_id = er.dep_id "
				   + " where ec.charging_item_code = '"+item_code+"' and ec.exam_num = '"+exam_num+"'";
		
		List<ExamDepResultDTO> list = this.jqm.getList(sql, ExamDepResultDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExamSummaryDTO getFinalSummaryResult(String exam_num,UserDTO user) throws ServiceException {
		String sql = "select e.final_status,u.chi_name user_name,e.final_time,e.final_exam_result,e.acceptance_check,"
				+ "e.approve_status,CONVERT(varchar(100),e.check_time,23) as check_time from exam_summary e "
				+ "left join user_usr u on u.id = e.exam_doctor_id "
				+ "where e.app_type = '1' and e.exam_num ='"+exam_num+"'  and e.center_num = '"+user.getCenter_num()+"'";
		List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoDiseaseDTO> getExamDiseaseResult(String exam_num) throws ServiceException {
		String sql =" select e.disease_id,e.id,e.disease_index,e.disease_name,e.suggest,e.disease_type "
				   +" from examinfo_disease e where e.exam_num = '"+exam_num+"' and e.app_type = '1' order by e.disease_index";
		List<ExaminfoDiseaseDTO> list = this.jqm.getList(sql, ExaminfoDiseaseDTO.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ViewExamDetailDTO> getViewExamImagePath(String pacs_req_code,String exam_num) throws ServiceException {
		String sesql = "select distinct v.exam_num,ci.dep_id,d.dep_name,v.pacs_id,v.pacs_req_code from view_exam_detail v,pacs_detail pd,charging_item ci,department_dep d "
				+ "where v.pacs_req_code = pd.pacs_req_code and pd.chargingItem_num = ci.item_code and ci.dep_id = d.id and d.view_result_type = 0 and v.pacs_req_code = '"+pacs_req_code+"'";
		List<ViewExamDetailDTO> examlist = this.jqm.getList(sesql, ViewExamDetailDTO.class);
//		ViewExamDetailDTO examInfo = null;
//		if(examlist.size() > 0){
//			examInfo = examlist.get(0);
//		}else{
//			sesql = " select distinct v.exam_num,d.id as dep_id,d.dep_name from view_exam_detail v,department_dep d "
//					+ "where v.dept_num = d.dep_num and d.view_result_type = 1 and v.id = "+view_detail_id;
//			examlist = this.jqm.getList(sesql, ViewExamDetailDTO.class);
//			if(examlist.size() > 0){
//				examInfo = examlist.get(0);
//			}else{
//				return new ArrayList<ViewExamDetailDTO>();
//			}
//		}
		String sql = "select distinct * from v_examinfo_image_path v where v.exam_num = '"+exam_num+"' order by v.seq_code";
		List<ViewExamDetailDTO> list = this.jqm.getList(sql, ViewExamDetailDTO.class);
		for (ViewExamDetailDTO viewExamDetailDTO : list) {
			if(examlist.size() > 0){
				viewExamDetailDTO.setId(examlist.get(0).getDep_id());
				viewExamDetailDTO.setItem_code(examlist.get(0).getPacs_req_code());
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveAcceptanceExamInfo(AcceptanceCheckModel model,UserDTO user) throws ServiceException {
		
		List<ExamSummary> list = this.qm.find("from ExamSummary where app_type = '1' and exam_num = '" + model.getExam_num()+"'");
		
		if(list.size() != 0){
			ExamSummary examSummary = list.get(0);
			examSummary.setAcceptance_check(model.getAcceptance_check());
			examSummary.setAcceptance_doctor(user.getUserid());
			examSummary.setAcceptance_date(DateTimeUtil.parse());
			this.pm.update(examSummary);
			return "ok";
		}else{
			return "no";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExamSuggestionLog serchExamSuggestionInfo(String exam_num,long userId) throws ServiceException {
		String sql ="select * from exam_suggestion_log where exam_num = '" + exam_num + "' and creater = '" + userId + "'";
		List<ExamSuggestionLog> list = this.jqm.getList(sql, ExamSuggestionLog.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamSuggestionLogDTO> serchExamSuggestionInfo(String exam_num) throws ServiceException {
		String sql ="select CONVERT(varchar(50),e.create_date,20) as persiondate,e.*,u.chi_name as chi_name from exam_suggestion_log e left join user_usr u on e.creater = u.id  where exam_num = '" + exam_num + "'";
		List<ExamSuggestionLogDTO> list = this.jqm.getList(sql, ExamSuggestionLogDTO.class);
		if(list.size() != 0){
			return list;
		}
		return null;
	}
	
	@Override
	public String validateZJ(String exam_num) throws ServiceException {
		String status = "";
		Connection connection = null;
		try {
			String sql = "select status from exam_info  where exam_num = '" + exam_num + "'";
			connection = this.jqm.getConnection();
			ResultSet rs1 = connection.createStatement().executeQuery(sql);
			if (rs1.next()) {
				status=rs1.getString("status");
			}
			rs1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return status;
	}
	
	@Override
	public ExamSuggestionLog saveExamSuggestionInfo(ExamSuggestionLog examSuggestionLog) throws ServiceException {
		this.pm.save(examSuggestionLog);
		return examSuggestionLog;
	}
	
	@SuppressWarnings("null")
	@Override
	public void editExamSuggestionInfo(ExamSuggestionLog examSuggestionLog,long id) throws ServiceException {
		Connection con=null;
		try {
			String sql=" update  exam_suggestion_log set notices='"+examSuggestionLog.getNotices()+"', update_date= '"+examSuggestionLog.getUpdate_date()+"' where id='"+id+"' ";
			con.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delExamSuggestion(long id) throws ServiceException {
		Connection con=null;
		try {
			String sql=" delete from exam_suggestion_log where id='"+id+"'";
			con = this.jqm.getConnection();
			con.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getAcceptanceItemResult(String exam_num,String IS_EXAM_RESULT_CITEM) throws ServiceException {
		
		List<DepExamResultDTO> list = new ArrayList<DepExamResultDTO>();
		
		String com_sql = " select * from (select case when eci.exam_status = 'D' then dd.dep_name+'(延期)' when eci.exam_status <> 'D' then dd.dep_name end as dep_name,e.item_name,e.item_num,e.id as item_id,ei.id,dd.seq_code,"
					   + " e.seq_code as e_seq_code,dd.dep_category,'' as exam_result, '' as exam_doctor,eci.exam_date"
					   + " from customer_info c,exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd,"
					   + " charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
					   + " eci.charging_item_code = ci.item_code and ci.dep_id = dd.id "
					   + " and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num and eci.pay_status <> 'M' "
					   + " and eci.isActive = 'Y' and eci.exam_status <> 'G' and ei.is_Active = 'Y' and dd.dep_category='17' "
					   + " and c.id = ei.customer_id and (c.sex = e.sex or e.sex = '全部') "
					   + " and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"'"
					   + " union all"
					   + " select a.dep_name,a.item_name,a.exam_num,a.item_id,a.id,a.seq_code,a.e_seq_code,a.dep_category,"
					   + " e.exam_result_summary as exam_result,e.exam_doctor,e.update_time from (select distinct dd.id as dep_id,case when eci.exam_status = 'D' then dd.dep_name+'(延期)' when eci.exam_status <> 'D' then dd.dep_name end as dep_name,"
					   + " '科室结论' as item_name,0 as item_id,ei.id,dd.seq_code,100000 as e_seq_code,dd.dep_category,ei.exam_num"
					   + " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where "
					   + " ei.exam_num = eci.exam_num and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and "
					   + " eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and ei.is_Active = 'Y'"
					   + " and dd.dep_category='17' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a "
					   + " left join exam_dep_result e on a.dep_id = e.dep_id and a.exam_num = e.exam_num) a order by a.seq_code,a.e_seq_code";
		
		List<DepExamResultDTO> commonList = this.jqm.getList(com_sql, DepExamResultDTO.class);
		for(DepExamResultDTO common : commonList){
			if( !StringUtils.isEmpty(common.getItem_num()) ){
				String sql = " select c.id,c.health_level,c.exam_result,c.exam_doctor from common_exam_detail c "
						   + " where c.exam_num = '"+exam_num+"' and c.item_code = '"+ common.getItem_num()+"'";
				List<CommonExamDetailDTO> comdetailList = this.jqm.getList(sql, CommonExamDetailDTO.class);
				if(comdetailList.size() != 0){
					common.setHealth_level(comdetailList.get(0).getHealth_level());
					common.setExam_result(comdetailList.get(0).getExam_result());
					common.setExam_doctor(comdetailList.get(0).getExam_doctor());
				}else{
					common.setExam_date("");
				}
				String WJsql = "select  id  ,data_source from exam_Critical_detail  where exam_num = '"+exam_num+"'  and   item_code = '"+common.getItem_num()+"' and is_active = 'Y' ";
				List<CriticalDTO> criticalList = this.jqm.getList(WJsql, CriticalDTO.class);
				if(criticalList.size() != 0){
					common.setCritical_id(criticalList.get(0).getId());
					common.setData_source(criticalList.get(0).getData_source());
				}
			}
		}
		
		list.addAll(commonList);//一般科室结果
		String view_sql="select * from (select a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,"
				+ " v.exam_desc as exam_result,v.exam_doctor,a.exam_date,1 as code,a.item_seq,p.pacs_req_code as req_id from (select  case when eci.exam_status = 'D' then ci.item_name+'(延期)' when eci.exam_status <> 'D' then ci.item_name end as dep_name,"
				+ " '检查描述' as item_name,ci.sam_demo_id as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date "
				+ " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ " and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' "
				+ " and eci.exam_status <> 'G' and ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a"
				+ " left join pacs_summary p on a.item_id = p.examinfo_sampleId and a.exam_num = p.examinfo_num left join "
				+ " view_exam_detail v on v.pacs_req_code = p.pacs_req_code "
				+ " union all "
				+ " select a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,v.exam_result as exam_result,"
				+ " v.exam_doctor,a.exam_date,2 as code,a.item_seq,p.pacs_req_code as req_id from (select  case when eci.exam_status = 'D' then ci.item_name+'(延期)' when eci.exam_status <> 'D' then ci.item_name end  as dep_name,'检查结论' as item_name,ci.sam_demo_id "
				+ " as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date from exam_info ei,examinfo_charging_item eci,"
				+ " charging_item ci,department_dep dd where ei.exam_num = eci.exam_num and eci.charging_item_code = ci.item_code and "
				+ " ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and "
				+ " ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a left join pacs_summary p "
				+ " on a.item_id = p.examinfo_sampleId and a.exam_num = p.examinfo_num left join view_exam_detail v on "
				+ " v.pacs_req_code = p.pacs_req_code "
				+ " union all "
				+ " select a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,(case when v.id is null then null "
				+ " else 'image_path' end) as exam_result,v.exam_doctor,a.exam_date,3 as code,a.item_seq,p.pacs_req_code as req_id from (select  case when eci.exam_status = 'D' then ci.item_name+'(延期)' when eci.exam_status <> 'D' then ci.item_name end  as dep_name,"
				+ " '图片' as item_name,ci.sam_demo_id as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date "
				+ " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ " and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and "
				+ " eci.exam_status <> 'G' and ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a "
				+ " left join pacs_summary p on a.item_id = p.examinfo_sampleId and a.exam_num = p.examinfo_num left join "
				+ " view_exam_detail v on v.pacs_req_code = p.pacs_req_code) a order by a.seq_code,a.item_seq,a.dep_name,a.code";
		
		List<DepExamResultDTO> viewList1 = this.jqm.getList(view_sql, DepExamResultDTO.class);
		
		String viewsql1 = "select * from (select d.dep_name,'检查描述' as item_name,v.exam_desc exam_result,v.exam_date,"
				+ "v.exam_doctor,v.id item_id,d.seq_code,1 as code from view_exam_detail v,exam_info e,department_dep d where "
				+ "v.exam_num = e.exam_num and v.dept_num = d.dep_num and e.exam_num = '"+exam_num+"' and d.view_result_type = '1' "
				+ "union all "
				+ "select d.dep_name,'检查结论' as item_name,v.exam_result,v.exam_date,v.exam_doctor,v.id item_id,d.seq_code,2 as code "
				+ "from view_exam_detail v,exam_info e,department_dep d where v.exam_num = e.exam_num and v.dept_num = d.dep_num "
				+ "and e.exam_num = '"+exam_num+"' and d.view_result_type = '1' "
				+ "union all "
				+ "select d.dep_name,'图片' as item_name,'image_path' as exam_result,v.exam_date,v.exam_doctor,v.id item_id,d.seq_code,3 as code "
				+ "from view_exam_detail v,exam_info e,department_dep d where v.exam_num = e.exam_num and v.dept_num = d.dep_num "
				+ "and e.exam_num = '"+exam_num+"' and d.view_result_type = '1' "
				+ ") a order by a.seq_code,a.item_id,a.code";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewsql1, DepExamResultDTO.class);
		viewList.addAll(viewList1);
		
		if (viewList1.size() > 0) {
			for (DepExamResultDTO vList : viewList1) {
				if ("检查结论".equals(vList.getItem_name())) {
					String WJsql = "select ecd.id  ,ecd.data_source from  charging_item ci,pacs_summary p ,view_exam_detail v ,exam_Critical_detail  ecd "
								+" where p.examinfo_sampleId = ci.sam_demo_id  and v.pacs_req_code = p.pacs_req_code  and   ecd.exam_num = v.exam_num  "
								+" and ecd.charging_item_code = ci.item_code and v.pacs_req_code = '"+vList.getReq_id()+"'  and ecd.exam_num ='"+vList.getExam_num()+"' and ecd.is_active = 'Y' ";
					List<CriticalDTO> criticalList = this.jqm.getList(WJsql, CriticalDTO.class);
					if(criticalList.size() != 0){
						vList.setCritical_id(criticalList.get(0).getId());
						vList.setData_source(criticalList.get(0).getData_source());
					}
				}
			}
		}
		list.addAll(viewList);//影像科室检查结果
		String exam_sql;
		if("Y".equals(IS_EXAM_RESULT_CITEM)){
			exam_sql = "select a.exam_num,a.dep_name,a.item_name,a.item_num,a.item_id,a.id,a.dep_category,e.exam_result+' ('+(case when e.ref_value is null then '' else e.ref_value end)+'  '"
					+ "+(case when e.item_unit is null then '' else e.item_unit end)+')' as exam_result,e.exam_doctor,a.exam_date,e.ref_indicator as health_level from (select case when eci.exam_status = 'D' then ci.item_name+'(延期)' when eci.exam_status <> 'D' then ci.item_name end  as dep_name,ci.id as c_id,e.item_name,e.id as item_id,"
					+ "ei.id,e.item_num,ei.exam_num,e.seq_code,ci.item_seq,dd.dep_category,eci.exam_date,ci.item_code from exam_info ei,examinfo_charging_item eci,charging_item ci,"
					+ "department_dep dd,charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
					+ "eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num "
					+ "and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and ei.is_Active = 'Y' and "
					+ "dd.dep_category='131' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a left join exam_result_detail e on e.exam_num = a.exam_num and e.charging_item_code = a.item_code"
					+ " and e.item_code = a.item_num order by a.item_seq,a.seq_code";
		}else{
			exam_sql = "select a.item_num,a.exam_num,a.dep_name,a.item_name,a.item_id,a.id,a.dep_category,e.exam_result+' ('+(case when e.ref_value is null then '' else e.ref_value end)+'  '"
					+ "+(case when e.item_unit is null then '' else e.item_unit end)+')' as exam_result,e.exam_doctor,a.exam_date,e.ref_indicator as health_level from (select case when eci.exam_status = 'D' then ci.item_name+'(延期)' when eci.exam_status <> 'D' then ci.item_name end  as dep_name,e.item_name,e.id as item_id,"
					+ "ei.id,e.item_num,ei.exam_num,e.seq_code,ci.item_seq,dd.dep_category,eci.exam_date from exam_info ei,examinfo_charging_item eci,charging_item ci,"
					+ "department_dep dd,charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
					+ "eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num "
					+ "and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and ei.is_Active = 'Y' and "
					+ "dd.dep_category='131' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a left join exam_result_detail e on e.exam_num = a.exam_num "
					+ "and e.item_code = a.item_num order by  a.item_seq,a.seq_code";
		}
		
		List<DepExamResultDTO> examList = this.jqm.getList(exam_sql, DepExamResultDTO.class);
		if (examList.size() > 0) {
			for (DepExamResultDTO lislist : examList) {
				String WJsql = "select  id  ,data_source from exam_Critical_detail  where exam_num = '"+lislist.getExam_num()+"'  and   item_code = '"+lislist.getItem_num()+"' and is_active = 'Y' ";
				List<CriticalDTO> criticalList = this.jqm.getList(WJsql, CriticalDTO.class);
				if(criticalList.size() != 0){
					lislist.setCritical_id(criticalList.get(0).getId());
					lislist.setData_source(criticalList.get(0).getData_source());
				}
			}
		}
		
		list.addAll(examList);//检验科室检查结果
		return list;
	}

	@Override
	public ExamInfo getExamInfoByid(String exam_num) throws ServiceException {
		List<ExamInfo> list = this.qm.find("from ExamInfo e where e.exam_num = '"+exam_num+"'");
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void editExam_ext_info(String arch_combine_ids, String str_arch_num,UserDTO user) throws Exception, ServiceException {
		Connection con=null;
		con=this.jqm.getConnection();
		String[] arch_ids=arch_combine_ids.split(",");
		String[] str_arch=str_arch_num.split(",");
		int i=0;
		StringBuffer str=new StringBuffer();
		for(i=0;i<arch_ids.length;i++){
			if(i==arch_ids.length-1){
				str.append("'"+arch_ids[i]+"'");
			}else{
				str.append("'"+arch_ids[i]+"'"+",");
			}
		}
		String strs=str.toString();
		String sql=" select ei.customer_id,ei.exam_num,ci.user_name,ci.arch_num,ci.create_time "
				+" from exam_info ei left join customer_info ci on ci.id = ei.customer_id  "
				+" where ei.is_Active = 'Y'  and exam_num in( "+strs+") and ci.center_num='"+user.getCenter_num()+"' "
				+" order by create_time desc ";
		List<ExamInfoDTO> l=this.jqm.getList(sql, ExamInfoDTO.class);
		ExamInfoDTO e=l.get(0);
			for(int j=0;j<str_arch.length;j++){
				if(!str_arch[j].equals(e.getArch_num())){
					ExamInfoDTO ef=this.queryexamInfomations(arch_ids[j]);
					ExamInfoExt ex=this.customerInfoService.querybyexam_num(ef.getExam_num());
					String sql1=" update  exam_info set customer_id='"+e.getCustomer_id()+"' where exam_num='"+ef.getExam_num()+"' ";
					con.createStatement().executeUpdate(sql1);
						try {
							if(!"".equals(ex) && ex!=null){
								e.setOld_arch_num(ef.getArch_num());
								e.setExam_num(ef.getExam_num());
								e.setUp_arch_num_person(user.getUserid());
								//e.setUp_arch_num_time(DateTimeUtil.getDateTime());
								this.updateExam_ext_info(con,e);
							}else{
								e.setOld_arch_num(ef.getArch_num());
								e.setExam_num(ef.getExam_num());
								e.setUp_arch_num_person(user.getUserid());
								//e.setUp_arch_num_time(DateTimeUtil.getDateTime());
								this.addExamInfoExt(con, e);
							}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				}
		}
		
		
		
	}

	@Override
	public List<ExamInfoDTO> getselectedarch(String ids,String center_num)throws ServiceException {
		String[] arch_ids=ids.split(",");
		StringBuffer str=new StringBuffer();
		for(int i=0;i<arch_ids.length;i++){
			if(i==arch_ids.length-1){
				str.append("'"+arch_ids[i]+"'");
			}else{
				str.append("'"+arch_ids[i]+"'"+",");
			}
		}
		String strs=str.toString();
			String sql=" select ei.customer_id ,ei.exam_num,ci.user_name,ci.arch_num,ci.create_time "
					+" from exam_info ei left join customer_info ci on ci.id = ei.customer_id  "
					+" where ei.is_Active = 'Y'  and exam_num in("+strs+") and ci.center_num='"+center_num+"'";
			 List<ExamInfoDTO> li = jqm.getList(sql,ExamInfoDTO.class);
		return li;
	}

	@Override
	public void updateArchCombine(String arch_combine_ids, String arch_num,long customer_id, UserDTO user) throws ServiceException,
			SQLException {
		
		Connection con=null;
		con=this.jqm.getConnection();
		String[] arch_ids=arch_combine_ids.split(",");
		for(int i=0;i<arch_ids.length;i++){
			
			String sql=" select ei.customer_id,ei.exam_num,ci.arch_num from exam_info ei left join customer_info ci on ci.id = ei.customer_id  "
					+" where ei.is_Active = 'Y' and ci.center_num='"+user.getCenter_num()+"' and exam_num ='"+arch_ids[i]+"' ";
			List<ExamInfoDTO> l =this.jqm.getList(sql, ExamInfoDTO.class);
			ExamInfoDTO e=l.get(0);
			if(!arch_num.equals(e.getArch_num())){
				String sql1=" update  exam_info set customer_id='"+customer_id+"' where exam_num='"+e.getExam_num()+"' ";
				con.createStatement().executeUpdate(sql1);
				ExamInfoExt ex=this.customerInfoService.querybyexam_num(e.getExam_num());
				if(!"".equals(ex) && ex!=null){
					e.setOld_arch_num(e.getArch_num());
					e.setExam_num(e.getExam_num());
					e.setUp_arch_num_person(user.getUserid());
					//e.setUp_arch_num_time(DateTimeUtil.getDateTime());
					this.updateExam_ext_info(con,e);
				}else{
					e.setOld_arch_num(e.getArch_num());
					e.setExam_num(e.getExam_num());
					e.setUp_arch_num_person(user.getUserid());
					//e.setUp_arch_num_time(DateTimeUtil.getDateTime());
					try {
						this.addExamInfoExt(con, e);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		
	}

	@Override
	public ExamInfoDTO queryexamInfomations(String arch_num) throws Exception,
			ServiceException {
		String sql=" select ei.exam_num,ci.user_name,ci.arch_num,ci.create_time "
				+" from exam_info ei left join customer_info ci on ci.id = ei.customer_id  "
				+" where ei.is_Active = 'Y'  and exam_num ='"+arch_num+"' ";
		List<ExamInfoDTO> ll=this.jqm.getList(sql, ExamInfoDTO.class);
		if(ll.size()>0){
			ExamInfoDTO e=ll.get(0);
			return e;
		}
		
		return null;
	}

	@Override
	public void updateExam_ext_info(Connection con, ExamInfoDTO dahb)
			throws ServiceException {
		String sql="update exam_ext_info  set old_arch_num='"+dahb.getOld_arch_num()+"',up_arch_num_time='"+DateTimeUtil.getDateTime()+"',up_arch_num_person='"+dahb.getUp_arch_num_person()+"' where exam_num='"+dahb.getExam_num()+"' ";
		try {
			con.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void addExamInfoExt(Connection connection, ExamInfoDTO dahb)
			throws Exception, ServiceException {
		String sql = "insert into exam_ext_info(exam_num,arch_num,up_arch_num_time,old_arch_num,up_arch_num_person,create_time,creater) "
				+ "values ('" +dahb.getExam_num() + "','" + dahb.getArch_num() + "','" + DateTimeUtil.getDateTime()
				+ "','" + dahb.getOld_arch_num() + "','" + dahb.getUp_arch_num_person() + "','"+DateTimeUtil.getDateTime()+"',"+dahb.getUp_arch_num_person()+")";
		connection.createStatement().executeUpdate(sql);
	} 
}
