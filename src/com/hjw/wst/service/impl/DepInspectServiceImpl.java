package com.hjw.wst.service.impl;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hjw.wst.domain.*;
import org.apache.commons.lang.StringUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.BSRCorrectionValueDTO;
import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepInspectExamIntionDTO;
import com.hjw.wst.DTO.DepLogicDTO;
import com.hjw.wst.DTO.DepLogicExamItemDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExpertSuggestionLibDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.examItemRefandDangDTO;
import com.hjw.wst.model.DepInspectModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepInspectService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class DepInspectServiceImpl implements DepInspectService {

	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
    private CustomerInfoService customerInfoService;

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
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
	public PageReturnDTO getDepExamInfoUserLis(DepInspectModel model,UserDTO user, int page, int pagesize,String sort,String order)throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select distinct  e.status,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,"
				+ "dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,"
				   +" dbo.GetNECIByExamIdAndDepid(e.exam_num,ci.dep_id) as weijian,e.freeze, "
				   +" e.join_date,e.exam_type,  "//ec.exam_status,
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company, "
				   +" dbo.GetExamDoctorNameByExamId(e.exam_num,"+user.getDep_id()+") as exam_times,e.apptype,e.customer_type_id"
				   +" , case when tael.isPrePay is null and b.overflag='1' then 'N' else 'Y' end as canExam "
				   +" from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e "
				   +" left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.center_num = '"+user.getCenter_num()+"' "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join team_account_exam_list tael on e.exam_num = tael.exam_num"
				   +" left join batch b on e.batch_id = b.id and b.is_Active = 'Y'"
				   +" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code "
				   +" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) and ec.center_num = '"+user.getCenter_num()+"' "
				   +" and e.is_Active = 'Y' and ec.pay_status <> 'M' and ci.item_category = '普通类型' "  //去掉弃检条件  and ec.exam_status <> 'G'
				   +" and ci.dep_id = "+user.getDep_id();
		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
			count ++;
		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
			count ++;
		}  
		if (model.getSex() != null && !"".equals(model.getSex())) {// 性别
			if(model.getSex().equals("0")){
				sql += " and c.sex   like '" + '女' + "%'";
				count ++;
			}else{
				sql += " and c.sex   like '" + '男' + "%'";
				count ++;
			}
			
			
		} 
		if (model.getTime1() != null && !"".equals(model.getTime1())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getTime1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 体检结束日期
			sql += " and e.join_date < '" + model.getTime2() + " 23:59:59.999'";
			count ++;
		} 
		if (model.getEmployeeID() != null && "".equals(model.getEmployeeID())) {// 工号
			sql += " and e.employeeID='" + model.getEmployeeID().trim() + "'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())){
			//已检 Y 未检 N 弃检 G 延期 D Z 检查中
			if("Z".equals(model.getExam_status())){
				sql += " AND ec.exam_status = 'Y' and dbo.GetNECIByExamIdAndDepid(e.exam_num,ci.dep_id) > 0 ";//检查中
			}else{
				sql += " AND ec.exam_status = '"+model.getExam_status()+"'";// 
			}
		}
		if(model.getDoctor_name() != null && !"".equals(model.getDoctor_name())){
			sql += " and ec.exam_doctor_name = '"+model.getDoctor_name()+"'";
			count ++;
		}
		if (model.getExam_date1() != null && !"".equals(model.getExam_date1())) {// 检查开始日期
			sql += " and ec.exam_date >= '" + model.getExam_date1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getExam_date2() != null && !"".equals(model.getExam_date2())) {// 检查结束日期
			sql += " and ec.exam_date < '" + model.getExam_date2() + " 23:59:59.999'";
			count ++;
		} 
		if(model.getCustomer_type() !=null && !"".equals(model.getCustomer_type())){
			sql += " and e.customer_type='"+model.getCustomer_type()+"'";
			count ++;
		}
		
		if (model.getExam_type()!=null && !"".equals(model.getExam_type())) {
			sql += " and e.exam_type = '" + model.getExam_type() + "' ";
			count ++;
		}
		
		if (model.getCharging_item_code()!=null && !"".equals(model.getCharging_item_code())) {
			sql += " and ci.item_code in  ("+model.getCharging_item_code()+")";
			count ++;
		}
		
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by weijian desc,e.join_date desc";
		}
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if(count == 0){
			return new PageReturnDTO();
		}
		
		PageSupport map = (PageSupport) this.jqm.getList(sql,page,pagesize,ExamInfoUserDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("普通科室体检首页-体检人员列表", sql);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamDepResultDTO> getCustomerInfo(long id,UserDTO user)throws ServiceException {
		String sql = "select a.exam_num,a.id exam_info_id,r.exam_result_summary,r.exam_doctor,r.suggestion,r.create_time as join_date from ("
				+ "select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.id, e.exam_num "
				+ "from exam_info e, (select exam_num,customer_id, id, join_date from exam_info a where a.id='"+id+"' and a.is_Active='Y') b "
				+ "where e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N' ) as A left join "
				+ "exam_dep_result as r on r.exam_num=A.exam_num and r.dep_id = "+user.getDep_id()+" where A.row=2";
		List<ExamDepResultDTO> list = this.jqm.getList(sql,ExamDepResultDTO.class);
		return  list;
	}

	@Override
	public ExamInfoUserDTO getExamInfoUserDTO(String exam_num, UserDTO user)throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		String sql="";
		ExamInfoUserDTO dto = new  ExamInfoUserDTO();
		/*Connection connection = null;
		      try {*/
				sql+=" select  c.id  as  c_id,ty.type_name,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,"
						+ "dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex"
								+ ",dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.past_medical_history,e.join_date,"
					   +" convert(varchar(50),e.join_date,23) as join_date,e.picture_path, "
					   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
					   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
					   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,dd.data_code_children as exam_types,e.customer_type_id"
					   +"   from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e"
					   + " left join company_info v on v.id=e.company_id "
					   +"   left join exam_summary es on e.exam_num = es.exam_num  and  es.center_num = '"+user.getCenter_num()+"'  "
					   +"   left join user_usr u on u.id = es.check_doc "
					   + "  left join  customer_type  ty  on  e.customer_type_id=ty.id"
					   +" left join data_dictionary dd on e.customer_type = dd.id "
					   +" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code "
					   +" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) and ec.center_num = '"+user.getCenter_num()+"' "
					   +" and e.is_Active = 'Y' "
					   +" and ci.dep_id = "+user.getDep_id()+"";
		               if(!exam_num.equals("")){//体检号
		            	   sql+="  and    e.exam_num='"+exam_num+"'";	   
		               }
              List<ExamInfoUserDTO>  li = jqm.getList(sql,ExamInfoUserDTO.class);
              if(li.size()>0){
            	  ExamInfoUserDTO ce=new ExamInfoUserDTO();
      			ce=li.get(0);
      			try{
      				String IS_CUSTOM_IDENTIFICATION = this.customerInfoService.getCenterconfigByKey("IS_CUSTOM_IDENTIFICATION", user.getCenter_num()).getConfig_value();
      				int vipsingin = com.hjw.util.StringUtil.getcheckStr(IS_CUSTOM_IDENTIFICATION,ce.getCustomer_type_id()+"");
      				ce.setVipsigin(vipsingin);
      			}catch(Exception ex){}
      			return ce;
              }else{
            	  return dto;
              }
	}

	@Override
	public List<DepInspectExamIntionDTO> getDepInspectExamIntion(long id,long dep_id,String app_type,String exam_num, String center_num) throws ServiceException {
		String sql=" select a.exam_doctor_name,a.exam_date,a.exam_status,a.inputter,a.c_id,a.item_code,a.s_id,a.s_name,a.item_category,a.id,a.item_name,a.exam_result as default_value,(case when cd.exam_result IS NULL "
				  +" then a.exam_result else cd.exam_result end) as exam_result,cd.health_level,a.brief_mark,a.brief,a.item_num"
				  +" ,convert(varchar(50),a.ref_Fmax) ref_Fmax,convert(varchar(50),a.ref_Fmin) ref_Fmin"
				  +" ,convert(varchar(50),a.ref_Mmax) ref_Mmax,convert(varchar(50),a.ref_Mmin) ref_Mmin"
				  +" ,convert(varchar(50),a.dang_Fmax) dang_Fmax,convert(varchar(50),a.dang_Fmin) dang_Fmin"
				  +" ,convert(varchar(50),a.dang_Mmax) dang_Mmax,convert(varchar(50),a.dang_Mmin) dang_Mmin"
				  +" from( SELECT eci.exam_doctor_name,convert(varchar(50),eci.exam_date,20) as exam_date,eci.exam_status,eci.inputter,ci.id as c_id,eit.brief_mark,eit.brief,eit.item_num,ci.item_code,"
				  +" s.id as s_id,s.demo_name as s_name,s.print_seq,eit.item_category,eit.id,eit.item_name,eit.default_value,il.exam_result,eit.seq_code "
				  +" ,eit.ref_Fmax,eit.ref_Fmin,eit.ref_Mmax,eit.ref_Mmin,eit.dang_Fmax,eit.dang_Fmin,eit.dang_Mmax,eit.dang_Mmin"
				  +" FROM  exam_info e,customer_info c,examinfo_charging_item eci,charging_item ci left join sample_demo s on ci.sam_demo_id = s.id,charging_item_exam_item cii,"
				  +" examination_item eit LEFT JOIN item_result_lib il ON il.id=eit.default_value and il.isActive='Y' "
				  +" where eci.charging_item_code=ci.item_code and cii.charging_item_code=ci.item_code "
				  +" and cii.item_code=eit.item_num "
				  +" and eci.exam_num= '"+exam_num+"' and eci.isActive='Y' and ci.dep_id="+dep_id
				  +" and e.exam_num = eci.exam_num and e.customer_id = c.id and (eit.sex = c.sex or eit.sex = '全部') "//and eci.exam_status <> 'G'
				  +" and eci.pay_status <> 'M' and eci.app_type = '"+app_type+"' and eci.center_num = '"+center_num+"') a "
				  +" left join common_exam_detail cd on cd.item_code = a.item_num and a.item_code = cd.charging_item_code and cd.exam_num ='"+exam_num
				  +"' order by a.print_seq,a.seq_code";  	
		  List<DepInspectExamIntionDTO>  li=jqm.getList(sql,DepInspectExamIntionDTO.class);
		  TranLogTxt.liswriteEror_to_txt_single("普通科室体检录结果页面-体检者体检结果查询", sql);
		  for(DepInspectExamIntionDTO item : li){
			  if(!"数字型".equals(item.getItem_category())){
				  String sql1 = "select e.is_ReforDang,e.val_info,e.val_index from exam_item_RefandDang e where e.item_code = '"+item.getItem_num()+"'";
				  List<examItemRefandDangDTO> list = this.jqm.getList(sql1, examItemRefandDangDTO.class);
				  item.setItemRefDang(list);
			  }
		  }
		return li;
	}	
	public List<DepInspectExamIntionDTO> getDepInspectExamIntion_BSR(long id,long dep_id,String exam_num, String center_num) throws ServiceException {
		String sql=" select a.exam_doctor_name,a.exam_date,a.exam_status,a.inputter,a.c_id,a.item_code,a.s_id,a.s_name,a.item_category,a.id,a.item_name,a.exam_result as default_value,(case when cd.exam_result IS NULL "
				+" then a.exam_result else cd.exam_result end) as exam_result,cd.health_level,a.brief_mark,a.brief,a.item_num"
				+" ,convert(varchar(50),a.ref_Fmax) ref_Fmax,convert(varchar(50),a.ref_Fmin) ref_Fmin"
				+" ,convert(varchar(50),a.ref_Mmax) ref_Mmax,convert(varchar(50),a.ref_Mmin) ref_Mmin"
				+" ,convert(varchar(50),a.dang_Fmax) dang_Fmax,convert(varchar(50),a.dang_Fmin) dang_Fmin"
				+" ,convert(varchar(50),a.dang_Mmax) dang_Mmax,convert(varchar(50),a.dang_Mmin) dang_Mmin"
				+" from( SELECT eci.exam_doctor_name,convert(varchar(50),eci.exam_date,20) as exam_date,eci.exam_status,eci.inputter,ci.id as c_id,eit.brief_mark,eit.brief,eit.item_num,ci.item_code,"
				+" s.id as s_id,s.demo_name as s_name,s.print_seq,eit.item_category,eit.id,eit.item_name,eit.default_value,il.exam_result,eit.seq_code "
				+" ,eit.ref_Fmax,eit.ref_Fmin,eit.ref_Mmax,eit.ref_Mmin,eit.dang_Fmax,eit.dang_Fmin,eit.dang_Mmax,eit.dang_Mmin"
				+" FROM  exam_info e,customer_info c,examinfo_charging_item eci,charging_item ci left join sample_demo s on ci.sam_demo_id = s.id,charging_item_exam_item cii,"
				+" examination_item eit LEFT JOIN item_result_lib il ON il.id=eit.default_value and il.isActive='Y' "
				+" where eci.charging_item_code=ci.item_code and cii.charging_item_code=ci.item_code "
				+" and cii.item_code=eit.item_num "
				+" and eci.exam_num= '"+exam_num+"' and eci.isActive='Y' and ci.dep_id="+dep_id
				+" and e.exam_num = eci.exam_num and e.customer_id = c.id and (eit.sex = c.sex or eit.sex = '全部') "//and eci.exam_status <> 'G'
				+" and eci.pay_status <> 'M' and eci.center_num = '"+center_num+"') a "
				+" left join common_exam_detail cd on cd.item_code = a.item_num and a.item_code = cd.charging_item_code and cd.exam_num ='"+exam_num
				+"' order by a.print_seq,a.seq_code";  	
		List<DepInspectExamIntionDTO>  li=jqm.getList(sql,DepInspectExamIntionDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("电测听室体检录结果页面-体检者体检结果查询", sql);
		
		for(DepInspectExamIntionDTO item : li){
			if(!"数字型".equals(item.getItem_category())){
				String sql1 = "select e.is_ReforDang,e.val_info,e.val_index from exam_item_RefandDang e where e.item_code = '"+item.getItem_num()+"'";
				List<examItemRefandDangDTO> list = this.jqm.getList(sql1, examItemRefandDangDTO.class);
				item.setItemRefDang(list);
			}
		}
		return li;
	}	
	public List<ExamDepResultDTO> getHistoryResult(String exam_num, UserDTO user)throws ServiceException {
		List<ExamDepResultDTO> resultlist = new ArrayList<ExamDepResultDTO>();
		for(int i=2;i<5;i++){
			String sql = "select A.exam_num,r.exam_info_id,r.exam_result_summary,r.exam_doctor,r.suggestion,r.create_time as join_date from ("
					+ "select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.id, e.exam_num from exam_info e, "
					+ "(select exam_num,customer_id, id, join_date from exam_info a where a.exam_num='"+exam_num+"' and a.is_Active='Y') b where "
					+ "e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N') as A, "
					+ "exam_dep_result as r where A.row="+i+" and r.exam_num=A.exam_num and r.dep_id = "+user.getDep_id();
			List<ExamDepResultDTO> list = this.jqm.getList(sql,ExamDepResultDTO.class);
			resultlist.addAll(list);
		}
		return  resultlist;
	}

	@Override
	public List<ItemResultLibDTO> getItemResultLibcyc(String item_code,long dep_id,String exam_status) throws ServiceException {
		String sql="SELECT id,exam_result,exam_conclusion FROM item_result_lib where isActive='Y'  "
				+ " and  item_code='"+item_code+"' and dep_id="+dep_id; 
				if(!"N".equals(exam_status)){
					sql += " and common_words ='Y'";
				}
			   sql+=" order by seq_code";
		List<ItemResultLibDTO>   li=jqm.getList(sql,ItemResultLibDTO.class);
		return li;
	}

	@Override
	public List<ItemResultLibDTO> getItemeditLibS(String name,String item_code,long dep_id,String IS_DEPINSPECT_WORD) throws ServiceException {
		String sql="SELECT id,exam_result,exam_conclusion FROM item_result_lib where isActive='Y'  and  item_code='"+item_code+"'"
				+ " and dep_id="+dep_id;
				if("N".equals(IS_DEPINSPECT_WORD)){
					sql +=" and common_words ='N' ";
				}
				if(!"".equals(name)&&name!=null){
					sql+="  and  exam_result  like  '%"+name.trim()+"%'";
				}
				sql +=" order by seq_code";
		List<ItemResultLibDTO>   li=jqm.getList(sql,ItemResultLibDTO.class);
		return li;
	}

	@Override
	public int getItemintionReference(DepInspectModel  dto) throws ServiceException {
		int  p=0;
		if("数字型".equals(dto.getItem_category())){
			String sqlll="SELECT id FROM  examination_item  where  id='"+dto.getId()+"'  and  ";
					/**
					 * 男
					 */
					if("男".equals(dto.getSex())){
						  sqlll	+=" ref_Mmin<="+dto.getExam_result().trim()+"    and    ref_Mmax>="+dto.getExam_result().trim();
						  List li=jqm.getList(sqlll,DepInspectExamIntionDTO.class);
						  //正常值
						  if(li.size()>0){
							  p=1;
						  //判断危机值
						  }else{
							  String sqlu="SELECT id FROM  examination_item  where  id='"+dto.getId()+"' "
									  + "  and   ("+dto.getExam_result().trim()+">dang_Mmax  or   "+dto.getExam_result().trim()+"<dang_Mmin)";
							  List ii=jqm.getList(sqlu,DepInspectExamIntionDTO.class);
							 //危机值
							 if(ii.size()>0){
								 p=2;
							 //异常
							 }else{
								 p=3;
							 }
						  }
					/**
					 * 女
					 */
					}else{
						  sqlll+=" ref_Fmin<="+dto.getExam_result().trim()+"    and    ref_Fmax>="+dto.getExam_result().trim();
						  List li=jqm.getList(sqlll,DepInspectExamIntionDTO.class);
						  //正常值
						  if(li.size()>0){
							  p=1;
						  //判断危机值
						  }else{
							  String sqlil="SELECT id FROM  examination_item  where  id='"+dto.getId()+"'  and   "
							  		+ "("+dto.getExam_result().trim()+">dang_Fmax  or   "+dto.getExam_result().trim()+">dang_Fmin )";
							  List ea=jqm.getList(sqlil,DepInspectExamIntionDTO.class);
							  if(ea.size()>0){
								  p=2;
							  }else{
								  p=3;
							  }
						  }
					}
		//短文本型
		}else{
			String sql="SELECT * FROM exam_item_RefandDang where   is_ReforDang='R' and   item_code='"+dto.getItem_num()+"'  "
					+ "and   val_info='"+dto.getExam_result().trim()+"'";
			List li=jqm.getList(sql,DepInspectExamIntionDTO.class);
			if(li.size()>0){//正常
				p=1;
			}else{//危机值
				String sqll="SELECT * FROM exam_item_RefandDang where   is_ReforDang='D' and   item_code='"+dto.getItem_num()+"'  "
					+ "and   val_info='"+dto.getExam_result().trim()+"'";
				List lis=jqm.getList(sqll,DepInspectExamIntionDTO.class);
				if(lis.size()>0){//危机
					p=2;
				}else{//异常
					p=3;
				}
			}
		}
		return p;
	}

	@Override
	public void addDepInspect(ExamdepResult exam) throws ServiceException {
			this.pm.save(exam);
	}

	@Override
	public void addCommonExamDetail(CommonExamDetail de) throws ServiceException {
		this.pm.save(de);
	}
	
	@Override
	public CommonExamDetail deleteCommonExamDetail(CommonExamDetail commonExamDetail) throws ServiceException {
		this.pm.remove(commonExamDetail);
		return commonExamDetail;
	}

	@Override
	public List<ExamdepResult> findExamDepResultDTO(String exam_num,Long dep_id,String app_type) throws ServiceException {
		String hql="from   ExamdepResult   WHERE    exam_num='"+exam_num+"'   and   dep_id="+dep_id +" and app_type = '"+app_type+"'";
		List<ExamdepResult>  li = qm.find(hql);
		return  li;
	}

	@Override
	public void updateExamDepResult(ExamdepResult exam) throws ServiceException {
		this.pm.update(exam);
	}
	
	@Override
	public void updateExamInfo(long examinfo_id) throws ServiceException{
		
		ExamInfo examInfo = (ExamInfo) qm.load(ExamInfo.class, examinfo_id);
		
		if(examInfo.getJoin_date() == null){
			examInfo.setJoin_date(DateTimeUtil.getDateTime());
		}
		examInfo.setStatus("J");
		this.pm.update(examInfo);
	}

	@Override
	public List<CommonExamDetail> findCommonExamDetail(String  exam_num,Long exam_item) throws ServiceException {
		String hql="from   CommonExamDetail  WHERE  exam_num='"+exam_num+"'  and   exam_item_id="+exam_item;
		List<CommonExamDetail> li = qm.find(hql);
		return li;
	}

	@Override
	public void updateCommonExamDetail(CommonExamDetail co) throws ServiceException {
		this.pm.update(co);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> queryweijizhi(String exam_num,String app_type) throws ServiceException {
		String sqlcom = "select dd.dep_name,e.item_name,e.id as item_id,ce.exam_item_id as id,dd.seq_code,"
				+ "e.seq_code as e_seq_code,dd.dep_category,ce.exam_result,ce.exam_doctor,ce.exam_date,ce.health_level from "
				+ "common_exam_detail ce,department_dep dd,examination_item e,charging_item_exam_item cie,charging_item c "
				+ "where dd.id = c.dep_id and c.item_code = cie.charging_item_code and e.item_num = cie.item_code and ce.item_code = e.item_num "
				+ "and ce.exam_num = '"+exam_num+"' and ce.health_level = 'W'";
	    List<DepExamResultDTO> list = this.jqm.getList(sqlcom, DepExamResultDTO.class);
	    
	    String sqllis = "select ci.item_name as dep_name,e.item_name,e.id as item_id,er.exam_info_id as id,e.seq_code,ci.item_seq,"
	    		+ "er.exam_date,er.exam_result+' ('+er.ref_value+'  '+(case when er.item_unit is null then '' else er.item_unit end)+')' "
	    		+ "as exam_result,er.exam_doctor,er.exam_date,er.ref_indicator as health_level from charging_item ci, charging_item_exam_item cit,"
	    		+ "examination_item e,exam_result_detail er where er.item_code = e.item_num and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num "

	    		+ "and er.exam_num = '"+exam_num+"' and er.ref_indicator in(4,5,6) order by ci.item_seq,e.seq_code";
	    List<DepExamResultDTO> listlis = this.jqm.getList(sqllis, DepExamResultDTO.class);

//	    String sql2 = "select  distinct ci.item_name as dep_name,ei.item_name ,ei.id as item_id,ecd.exam_result as exam_result ,ci.item_seq  from exam_Critical_detail   ecd " +
//					" left join department_dep dd  on dd.id = ecd.dept_id  " +
//					" left join charging_item ci on ci.item_code = ecd.charging_item_code " +
//					" left join examination_item ei on ei.item_num = ecd.item_code " +
//					" where 1=1 and ecd.exam_num = '"+exam_num+"'"+
//					" order by ci.item_seq";
//
//		List<DepExamResultDTO> list2 = this.jqm.getList(sql2, DepExamResultDTO.class);
	    list.addAll(listlis);
//		list.addAll(list2);

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> queryYichang(String exam_num,String app_type, String center_num) throws ServiceException {
		String sqlcom = "select dd.dep_name,e.item_name,e.id as item_id,ce.exam_item_id as id,dd.seq_code,"
				+ "e.seq_code as e_seq_code,dd.dep_category,ce.exam_result,ce.exam_doctor,ce.exam_date,ce.health_level from "
				+ "common_exam_detail ce,department_dep dd,examination_item e,charging_item_exam_item cie,charging_item c "
				+ "where dd.id = c.dep_id and c.item_code = cie.charging_item_code and e.item_num = cie.item_code and ce.item_code = e.item_num "
				+ "and ce.exam_num = '"+exam_num+"' and ce.health_level <> 'Z'";
	    List<DepExamResultDTO> list = this.jqm.getList(sqlcom, DepExamResultDTO.class);
	    
	    String sqllis = "select ci.item_name as dep_name,e.item_name,e.id as item_id,ei.id,e.seq_code,ci.item_seq,dd.dep_category,"
	    		+ "eci.exam_date,er.exam_result+' ('+er.ref_value+'  '+(case when er.item_unit is null then '' else er.item_unit end)+')' as exam_result,er.exam_doctor,"
	    		+ "er.exam_date,er.ref_indicator as health_level from exam_info ei,examinfo_charging_item eci,charging_item ci, "
				+ "department_dep dd,charging_item_exam_item cit,examination_item e,exam_result_detail er where ei.exam_num = eci.exam_num "
				+ "and er.exam_num = ei.exam_num and er.item_code = e.item_num and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and "
				+ "ci.item_code = cit.charging_item_code and cit.item_code = e.item_num and eci.pay_status <> 'M' and eci.isActive = 'Y' and "
				+ "eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='131' and ci.item_category != '耗材类型' "
				+ "and ei.exam_num = '"+exam_num+"' and er.ref_indicator <> '0' order by ci.item_seq,e.seq_code";
	    List<DepExamResultDTO> listlis = this.jqm.getList(sqllis, DepExamResultDTO.class);
	    list.addAll(listlis);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> queryAll(String exam_num,String app_type, String center_num) throws ServiceException {
		List<DepExamResultDTO> list = new ArrayList<DepExamResultDTO>();
		
		String com_sql = " select * from (select ei.exam_num,dd.dep_name,e.item_name,e.id as item_id,ei.id,dd.seq_code,"
					   + " e.seq_code as e_seq_code,dd.dep_category,'' as exam_result, '' as exam_doctor,eci.exam_date,e.item_num"
					   + " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd,"
					   + " charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
					   + " eci.charging_item_code = ci.item_code and ci.dep_id = dd.id "
					   + " and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num and eci.pay_status <> 'M' "
					   + " and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='17' "
					   + " and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"' "
					   + " union all"
					   + " select a.exam_num,a.dep_name,a.item_name,a.item_id,a.id,a.seq_code,a.e_seq_code,a.dep_category,"
					   + " e.exam_result_summary as exam_result,e.exam_doctor,e.update_time,'' as item_num from (select distinct ei.exam_num,dd.id as dep_id,dd.dep_name,"
					   + " '科室结论' as item_name,0 as item_id,ei.id,dd.seq_code,100000 as e_seq_code,dd.dep_category"
					   + " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where "
					   + " ei.exam_num = eci.exam_num and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and "
					   + " eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y'"
					   + " and dd.dep_category='17' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a "
					   + " left join exam_dep_result e on a.dep_id = e.dep_id and a.exam_num = e.exam_num) a order by a.seq_code,a.e_seq_code";
		
		List<DepExamResultDTO> commonList = this.jqm.getList(com_sql, DepExamResultDTO.class);
		for(DepExamResultDTO common : commonList){
			if(!common.getExam_num().equals("")){
				String sql = " select c.id,c.health_level,c.exam_result,c.exam_doctor from common_exam_detail c "
						   + " where c.exam_num = '"+common.getExam_num()+"' and c.item_code = '"+ common.getItem_num()+"'";
				List<CommonExamDetailDTO> comdetailList = this.jqm.getList(sql, CommonExamDetailDTO.class);
				if(comdetailList.size() != 0){
					common.setHealth_level(comdetailList.get(0).getHealth_level());
					common.setExam_result(comdetailList.get(0).getExam_result());
					common.setExam_doctor(comdetailList.get(0).getExam_doctor());
				}else{
					common.setExam_date("");
				}
			}
		}
		
		list.addAll(commonList);//一般科室结果
		
		String view_sql="select * from (select a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,"
				+ " v.exam_desc as exam_result,v.exam_doctor,a.exam_date,1 as code,a.item_seq,p.id as pacsid,p.pacs_req_code as req_id from (select ci.item_name as dep_name,"
				+ " '检查描述' as item_name,ci.sam_demo_id as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date "
				+ " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ " and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' "
				+ " and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='21' "
						+ "and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a"
				+ " left join pacs_summary p on a.exam_num = p.examinfo_num and a.item_id = p.examinfo_sampleId left join "
				+ " view_exam_detail v on v.pacs_req_code = p.pacs_req_code "
				+ " union all "
				+ " select a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,v.exam_result as exam_result,"
				+ " v.exam_doctor,a.exam_date,2 as code,a.item_seq,p.id as pacsid,p.pacs_req_code as req_id from (select ci.item_name as dep_name,'检查结论' as item_name,ci.sam_demo_id "
				+ " as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date from exam_info ei,examinfo_charging_item eci,"
				+ " charging_item ci,department_dep dd where ei.exam_num = eci.exam_num and eci.charging_item_code = ci.item_code and "
				+ " ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and "
				+ " ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a left join pacs_summary p "
				+ " on a.exam_num = p.examinfo_num and a.item_id = p.examinfo_sampleId left join view_exam_detail v on "
				+ " v.pacs_req_code = p.pacs_req_code "
				+ " union all "
				+ " select a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,(case when v.id is null then null "
				+ " else 'image_path' end) as exam_result,v.exam_doctor,a.exam_date,3 as code,a.item_seq,p.id as pacsid,p.pacs_req_code as req_id from (select ci.item_name as dep_name,"
				+ " '图片' as item_name,ci.sam_demo_id as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date "
				+ " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ " and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and "
				+ " eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='21' "
						+ "and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a "
				+ " left join pacs_summary p on a.exam_num = p.examinfo_num and a.item_id = p.examinfo_sampleId left join "
				+ " view_exam_detail v on v.pacs_req_code = p.pacs_req_code) a order by a.seq_code,a.item_seq,a.pacsid,a.code";
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
		
		list.addAll(viewList);//影像科室检查结果
		
		String exam_sql = "select a.dep_name,a.item_name,a.item_id,a.id,a.dep_category,e.exam_result+' ('+e.ref_value+'  '+(case when e.item_unit is null then '' else e.item_unit end)+')'" 
				+" as exam_result,e.exam_doctor,a.exam_date,e.ref_indicator as health_level from ("
				+" select ei.exam_num,ci.item_name as dep_name,e.item_name,e.id as item_id,e.item_num as item_num,ei.id,e.seq_code,ci.item_seq,dd.dep_category,eci.exam_date ,ci.id as ciid,ci.item_code"
				+" from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd,charging_item_exam_item cit,examination_item e "
				+" where ei.exam_num = eci.exam_num "
				+" and eci.charging_item_code = ci.item_code "
				+" and ci.dep_id = dd.id "
				+" and ci.item_code = cit.charging_item_code "
				+" and cit.item_code = e.item_num "
				+" and eci.pay_status <> 'M' "
				+" and eci.isActive = 'Y' "
				+" and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' "
				+" and ei.is_Active = 'Y' "
				+" and dd.dep_category='131' "
				+" and ci.item_category != '耗材类型' "
				+" and ei.exam_num = '"+exam_num+"') a left join exam_result_detail e on e.exam_num = a.exam_num" 
				+" and e.item_code = a.item_num  and e.charging_item_code = a.item_code"
				+" order by a.item_seq,a.seq_code";
		List<DepExamResultDTO> examList = this.jqm.getList(exam_sql, DepExamResultDTO.class);
		list.addAll(examList);//检验科室检查结果
		return list;
	}

	@Override
	public List<ExamInfoUserDTO> queryAllDepartmentDep()
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteResult(DepInspectModel model,UserDTO user) throws ServiceException {
		String hql="from   CommonExamDetail  WHERE  exam_num  ='"+model.getExam_num()+"'  and  exam_doctor='"+user.getName()+"'";
		List<CommonExamDetail>	com = qm.find(hql);
		if(com.size()>0){
			for (CommonExamDetail l : com) {
				this.pm.remove(l);
			}
		}
		String hqll="from ExamdepResult  WHERE exam_num='"+model.getExam_num()+"'   and   dep_id="+user.getDep_id();
		List<ExamdepResult>  li = qm.find(hqll);
		if(li.size()>0){
			for (ExamdepResult em : li) {
				this.pm.remove(em);
			}
		}
	}

	@Override
	public void updateExaminfoChargingItem(String exam_num,String ids,UserDTO user) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+=" update examinfo_charging_item set exam_status='Y',exam_date='"+DateTimeUtil.getDateTime()+"',"
			   + "exam_doctor_id="+user.getUserid()+",exam_doctor_name='"+user.getName()+"', exam_center_num = '"+user.getCenter_num()+"' "
			   + " where isActive = 'Y' and pay_status <> 'M' and exam_status in('N','D')"
			   + " and exam_num = '"+exam_num+"' and charge_item_id in ("+ids+") and center_num = '"+user.getCenter_num()+"'";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
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
	public ExamdepResult getExamdepresultResult(long id, UserDTO user,String app_type) throws ServiceException {
		String hql="from ExamdepResult  WHERE exam_info_id="+id+"  "
				+ " and   dep_id="+user.getDep_id()+" and app_type = '"+app_type+"'";
		List<ExamdepResult> li = this.qm.find(hql);
		if(li.size()>0){
			return  li.get(0);
		}else{
			return  null;
		}
	}

	@Override
	public ExaminfoChargingItem getExamdepresulStatus(String exam_num, String ids, String center_num) throws ServiceException {
		String hql="from ExaminfoChargingItem  WHERE charge_item_id in("+ids+") "
				+ " and center_num = '"+center_num+"' and   exam_num='"+exam_num+"'";
		List<ExaminfoChargingItem> li = this.qm.find(hql);
		return li.size()>0?li.get(0):null;
	}

	@Override
	public ExamInfo getStatuss(String exam_num, UserDTO user) throws ServiceException {
		String hql="from ExamInfo   where exam_num='"+exam_num+"'";
		List<ExamInfo> list = this.qm.find(hql);
		return list.size()>0?list.get(0):null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getMateDepLogic(List<DepExamResultDTO> depResultList,long dep_id,String sex) throws ServiceException {
		
		Map<Long,DepExamResultDTO> resultMap = new HashMap<Long, DepExamResultDTO>();
		for(DepExamResultDTO resultDto : depResultList){             //将普通科室检查结果放入MAP中
			resultMap.put(resultDto.getItem_id(), resultDto);
		}
		
		String sql = "select d.id,d.conclusion_word from dep_logic d where d.dep_id = "+dep_id + " and d.sex in('全部','"+sex+"')";
		List<DepLogicDTO> depLogicList = this.jqm.getList(sql, DepLogicDTO.class);
		
		List<Long> list = new ArrayList<Long>();//疾病ID
		
		for(DepLogicDTO depLogic : depLogicList){
			int count = 0;// 符合条件的疾病逻辑计数器
			boolean and_flag = false;
			String sql1 = "select d.exam_item_id,d.condition_value,d.condition,d.andOrNo from dep_logic_exam_item d "
					+ "where d.logic_id = "+depLogic.getId()+" order by d.logic_index";
			
			List<DepLogicExamItemDTO> logicItemList = this.jqm.getList(sql1, DepLogicExamItemDTO.class);
			for(DepLogicExamItemDTO logicItem : logicItemList){
				if("and".equals(logicItem.getAndOrNo())){
					and_flag = true;
				}
				DepExamResultDTO resultDto2 = resultMap.get(logicItem.getExam_item_id());
				if (resultDto2 == null) {
					break;
				}
				if(convertLogic(resultDto2,logicItem)){
					count ++;
				}else{
					break;
				}
			}
			// =============所有条件都符合就加进去
			if(and_flag){
				if (logicItemList.size() != 0 && logicItemList.size() == count) {
					list.add(depLogic.getId());
				}
			}else{
				if(count > 0){
					list.add(depLogic.getId());
				}
			}
		}
		
		if(list.size() == 0){
			return null;
		}
		Long[] arr = (Long[]) list.toArray(new Long[list.size()]);
		String logicIds = StringUtils.join(arr, ",");
		String sql3 = " select distinct d.conclusion_word exam_result,de.exam_item_id item_id from dep_logic d,dep_logic_exam_item de "
				    + " where d.id = de.logic_id and d.id in ("+logicIds+")";
		
		List<DepExamResultDTO> resultList = this.jqm.getList(sql3, DepExamResultDTO.class);
		return resultList;
	}
	
	public List<DepLogicDTO> getDepLpgic(long dep_id,String sex) throws ServiceException{
		String sql = "select d.id,d.conclusion_word from dep_logic d where d.dep_id = "+dep_id+ " and d.sex in('全部','"+sex+"')";
		List<DepLogicDTO> depLogicList = this.jqm.getList(sql, DepLogicDTO.class);
		for(DepLogicDTO depLogic : depLogicList){
			String sql1 = "select d.exam_item_id,d.condition_value,d.condition,d.andOrNo from dep_logic_exam_item d "
					+ "where d.logic_id = "+depLogic.getId()+" order by d.logic_index";
			List<DepLogicExamItemDTO> logicItemList = this.jqm.getList(sql1, DepLogicExamItemDTO.class);
			depLogic.setLogic_item(logicItemList);
		}
		return depLogicList;
	}
	
	private boolean convertLogic(DepExamResultDTO resultDto2,DepLogicExamItemDTO logicItem){
		String con = logicItem.getCondition();// 当前疾病逻辑的当前条件关系
		// =======条件判断
		if (con.trim().equals("=")) {
			if (logicItem.getCondition_value().equals(resultDto2.getExam_result())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals(">")) {
			if ("".equals(resultDto2.getExam_result()) || resultDto2.getExam_result() == null || (!resultDto2.getExam_result().matches("^(-?\\d+)(\\.\\d+)?$") && !"0".equals(resultDto2.getExam_result()) && !resultDto2.getExam_result().matches("[0-9]*[1-9][0-9]*$"))) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) > Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals(">=")) {
			if ("".equals(resultDto2.getExam_result()) || resultDto2.getExam_result() == null || (!resultDto2.getExam_result().matches("^(-?\\d+)(\\.\\d+)?$") && !"0".equals(resultDto2.getExam_result()) && !resultDto2.getExam_result().matches("[0-9]*[1-9][0-9]*$"))) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) >= Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("<")) {
			if ("".equals(resultDto2.getExam_result()) || resultDto2.getExam_result() == null || (!resultDto2.getExam_result().matches("^(-?\\d+)(\\.\\d+)?$") && !"0".equals(resultDto2.getExam_result()) && !resultDto2.getExam_result().matches("[0-9]*[1-9][0-9]*$"))) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) < Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("<=")) {
			if ("".equals(resultDto2.getExam_result()) || resultDto2.getExam_result() == null || (!resultDto2.getExam_result().matches("^(-?\\d+)(\\.\\d+)?$") && !"0".equals(resultDto2.getExam_result()) && !resultDto2.getExam_result().matches("[0-9]*[1-9][0-9]*$"))) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) <= Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("in")) {
			if (resultDto2.getExam_result().indexOf(logicItem.getCondition_value()) != -1) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("not in")) {
			if (resultDto2.getExam_result().indexOf(logicItem.getCondition_value()) == -1) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("like")) {
			if (resultDto2.getExam_result().indexOf(logicItem.getCondition_value()) != -1) {
				return true;
			}
		}
		
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getDepResult(long id, long dep_id) throws ServiceException {
		String sql = "select distinct c.exam_item_id as item_id,c.exam_result from common_exam_detail c,charging_item ci,"
				+ "charging_item_exam_item ce,examination_item e where c.exam_info_id = "+id+" and c.item_code = e.item_num "
				+ "and ci.item_code = ce.charging_item_code and ce.item_code = e.item_num and e.brief_mark = '1' and e.brief = '1' "
				+ "and ci.dep_id = "+dep_id;
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfoDTO> getDepuserBydepId(UserDTO user,String type,String apptype) throws ServiceException {
		String sql = "select u.id,u.chi_name from user_usr u,dep_user d ,examinatioin_center  ec "
				   + "where ec.id = u.exam_center_id and  u.id = d.user_id and d.dep_id = "+user.getDep_id()+" and d.apptype = '"+apptype+"' and ec.center_num = '"+user.getCenter_num()+"'  order by u.chi_name ";
		if("1".equals(type)){//查询所有人员
			 sql = "select u.id,u.chi_name from user_usr u ,examinatioin_center ec "
					   + "where ec.id = u.exam_center_id and u.is_active = 'Y' and ec.center_num = '"+user.getCenter_num()+"' order by u.chi_name";
		}
		List<UserInfoDTO> list = this.jqm.getList(sql, UserInfoDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DepInspectExamIntionDTO getOldCommonDetailResult(long id, long item_id) throws ServiceException {
		String sql = "select e.item_name,c.exam_result from examination_item e left join common_exam_detail c "
				+ "on c.item_code = e.item_num and c.exam_info_id = "+id+"  where e.id ="+item_id;
		List<DepInspectExamIntionDTO> list = this.jqm.getList(sql, DepInspectExamIntionDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveDepResult(DepInspectModel model, UserDTO user,String is_depitem_save) throws ServiceException {

		List<ExamdepResult> exm = this.findExamDepResultDTO(model.getExam_num(), user.getDep_id(),model.getApp_type());
		if (exm.size() > 0) {// 修改
			exm.get(0).setExam_info_id(model.getExam_info_id());// 体检信息Id
			exm.get(0).setExam_doctor(user.getName());// 检查医生
			exm.get(0).setDep_id(user.getDep_id());// 检查科室
			exm.get(0).setExam_result_summary(model.getExam_result_summary());// 科室检查结果
			exm.get(0).setSuggestion(model.getSuggestion());// 科室建议
			exm.get(0).setCenter_num(user.getCenter_num());// 体检中心
			exm.get(0).setSpecial_setup(model.getSpecial_setup());// 特殊设置
			exm.get(0).setExam_num(model.getExam_num());
			exm.get(0).setUpdater(user.getUserid());// 修改人
			exm.get(0).setUpdate_time(DateTimeUtil.parse());// 修改时间
			this.updateExamDepResult(exm.get(0));
		} else {
			// 保存结论
			ExamdepResult m = new ExamdepResult();
			m.setExam_info_id(model.getExam_info_id());// 体检信息Id
			m.setExam_doctor(user.getName());// 检查医生
			m.setDep_id(user.getDep_id());// 检查科室
			m.setExam_result_summary(model.getExam_result_summary());// 科室检查结果
			m.setSuggestion(model.getSuggestion());// 科室建议
			m.setCenter_num(user.getCenter_num());// 体检中心
			m.setSpecial_setup(model.getSpecial_setup());// 特殊设置
			m.setCreater(user.getUserid());// 创建人
			m.setCreate_time(DateTimeUtil.parse());// 创建时间
			m.setApp_type(model.getApp_type());
			this.addDepInspect(m);

		}
		
		//保存科室结论日志
				ExamdepResultLog deplog = new ExamdepResultLog();
				deplog.setExam_info_id(model.getExam_info_id());// 体检信息Id
				deplog.setExam_doctor(user.getName());// 检查医生
				deplog.setExam_date(DateTimeUtil.parse());//检查时间
				deplog.setDep_id(user.getDep_id());// 检查科室
				deplog.setExam_result_summary(model.getExam_result_summary());// 科室检查结果
				deplog.setSuggestion(model.getSuggestion());// 科室建议
				deplog.setCreater(user.getUserid());// 创建人
				deplog.setCreate_time(DateTimeUtil.parse());// 创建时间
				this.pm.save(deplog);
		// 保存检查结果细项
		List<CommonExamDetailDTO> lis = model.getDetailList();
		for(int i=0;i<lis.size();i++){
			List<CommonExamDetail> examli=this.findCommonExamDetail(lis.get(i).getExam_num(),lis.get(i).getExam_item_id());
			if(examli.size()>0&&examli!=null){
				if("".equals(lis.get(i).getExam_result()) && "N".equals(is_depitem_save)){
					this.deleteCommonExamDetail(examli.get(0));
				}else{
					examli.get(0).setExam_info_id(lis.get(i).getExam_info_id());//体检信息
					examli.get(0).setExam_item_id(lis.get(i).getExam_item_id());//检查项目
					examli.get(0).setExam_doctor(user.getName());//检查医生
					examli.get(0).setExam_result(lis.get(i).getExam_result());//检查结果
					examli.get(0).setHealth_level(lis.get(i).getHealth_level());//健康指数
					examli.get(0).setCenter_num(user.getCenter_num());//体检中心
					examli.get(0).setUpdater(user.getUserid());//修改
					examli.get(0).setUpdate_time(DateTimeUtil.parse());//修改时间
					String item_num = this.getexaminationforid(lis.get(i).getExam_item_id());
					examli.get(0).setItem_code(item_num);
					this.updateCommonExamDetail(examli.get(0));
				}
			}else{
				if(!"".equals(lis.get(i).getExam_result()) || "Y".equals(is_depitem_save)){
					CommonExamDetail ai = new CommonExamDetail();
					ai.setExam_info_id(lis.get(i).getExam_info_id());//体检信息
					ai.setExam_item_id(lis.get(i).getExam_item_id());//检查项目
					ai.setExam_doctor(user.getName());//检查医生
					ai.setExam_result(lis.get(i).getExam_result());//检查结果
					ai.setExam_date(DateTimeUtil.parse());	//检查日期	
					ai.setHealth_level(lis.get(i).getHealth_level());//健康指数
					ai.setCenter_num(user.getCenter_num());//体检中心
					ai.setCreater(user.getUserid());//记录创建者
					ai.setCreate_time(DateTimeUtil.parse());//创建时间
					ai.setExam_num(lis.get(i).getExam_num());
					String item_num = this.getexaminationforid(lis.get(i).getExam_item_id());
					String ids = model.getCharing_ids();
					
					String item_code = this.getchargingitemcodefromid(ids);
					
					ai.setItem_code(item_num);
					ai.setCharging_item_code(item_code);
					this.addCommonExamDetail(ai);
				}
			}
			//保存细项检查结果日志
			if(!"".equals(lis.get(i).getExam_result())){
				CommonExamDetailLog comlog = new CommonExamDetailLog();
				comlog.setDep_result_id(deplog.getId());//体检信息
				comlog.setExam_item_id(lis.get(i).getExam_item_id());//检查项目
				comlog.setExam_result(lis.get(i).getExam_result());//检查结果
				comlog.setHealth_level(lis.get(i).getHealth_level());//健康指数
				comlog.setCreater(user.getUserid());//记录创建者
				comlog.setCreate_time(DateTimeUtil.parse());//创建时间
				this.pm.save(comlog);
			}
		}
		//修改检查状态
		this.updateExamInfo(model.getExam_info_id());
		this.updateExaminfoChargingItem(model.getExam_num(),model.getCharing_ids(),user);
		this.updateExaminfoChargingItemoninputter(model.getExam_num(),model.getCharing_ids(),model.getInputter());
		
		//人员类型 vip的客户
		String sql = "select c.type_name,c.type_code from customer_type c,exam_info e "
				+ "where e.customer_type_id = c.id and e.exam_num = '"+model.getExam_num()+"'";
		List<Customer_TypeDTO> typeList = this.jqm.getList(sql, Customer_TypeDTO.class);
		if(typeList.size() != 0){
			if("LXVIP".equals(typeList.get(0).getType_code())){
				String sqlVip = " select p.id,e.exam_num,c.user_name,(case when e.exam_type = 'G' then '个人' else v.com_name end) "
						+ " as com_name from customer_info c,exam_info e "
						 + " left join company_info v on v.id=e.company_id "
						+ " left join customer_vip_prompt p on e.exam_num = p.exam_num where c.id = e.customer_id "
						+ " and e.exam_num = '"+model.getExam_num()+"'";
				List<CustomerVipPrompt> cvipList = this.jqm.getList(sqlVip, CustomerVipPrompt.class);
				if(cvipList.size() != 0){
					if(cvipList.get(0).getId() == null || "".equals(cvipList.get(0).getId())){
						
						String usersql = " select distinct u.id userid,u.chi_name from WEB_RESRELATIONSHIP r,WEB_ROLE l,Web_Userjsb s,user_usr u"
								       + " where r.userorroleid = l.ID and l.ID = s.role_id and s.user_id = u.id "
								       + "and r.res_code = 'RS011' and r.datavalue = '1'";
						
						List<UserDTO> userList = this.jqm.getList(usersql, UserDTO.class);
						
						for(UserDTO userDto : userList){
						
							CustomerVipPrompt customerVipPrompt = new CustomerVipPrompt();
							
							customerVipPrompt.setExam_num(cvipList.get(0).getExam_num());
							customerVipPrompt.setUser_name(cvipList.get(0).getUser_name());
							customerVipPrompt.setCom_name(cvipList.get(0).getCom_name());
							customerVipPrompt.setPrompt_status("0");
							customerVipPrompt.setPrompt_user(userDto.getUserid());
							customerVipPrompt.setCreater(user.getUserid());
							customerVipPrompt.setCreate_time(DateTimeUtil.parse());
							
							this.pm.save(customerVipPrompt);
						}
					}
				}
			}
		}
		return "保存成功";
	}

	

	private void updateExaminfoChargingItemoninputter(String exam_num,String charging_ids, long inputter) {
		String sql="";
		Connection connection = null;
		try {
			sql+=" update examinfo_charging_item set inputter="+inputter+" "			 
			   + " where isActive = 'Y' and pay_status <> 'M' and exam_status <> 'G' and exam_status <> 'D'"
			   + " and exam_num = '"+exam_num+"' and charge_item_id in ("+charging_ids+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
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
	public List<CustomerVipPrompt> updateCustomerVipPrompt(long userid) throws ServiceException {
		String sql = "from CustomerVipPrompt c where c.prompt_status = '0' and c.prompt_user = "+userid;
		List<CustomerVipPrompt> list = this.qm.find(sql);
		for(CustomerVipPrompt c : list){
			c.setPrompt_status("1");
			this.pm.update(c);
		}
		return list;
	}
	
	@Override
	public List<ExpertSuggestionLibDTO> getDepSuggestionList(String sugg_word,long dep_id) throws ServiceException{
		String sql = "SELECT id,sugg_content FROM expert_suggestion_lib where dep_id =  " + dep_id;
		if(sugg_word != null && !"".equals(sugg_word)){
			sql += " and '"+sugg_word+"' like '%'+sugg_word+'%'";
		}
		List<ExpertSuggestionLibDTO> list = this.jqm.getList(sql, ExpertSuggestionLibDTO.class);
		return list;
	}

	@Override
	public ExaminationItem getExamationItem(String item_num) throws ServiceException {
		String sql = "from ExaminationItem c where c.item_num = '"+item_num+"'";
		List<ExaminationItem> list = this.qm.find(sql);
		ExaminationItem item=new ExaminationItem();
		if(list!=null&&list.size()>0){
			item= list.get(0);
		}
		return item;
	}
	
	@Override
	public String checkDepExaminfoItem(String exam_num,long dep_id, String center_num) throws ServiceException{
		
		String sql = "select ec.id,ec.pay_status,ec.exam_status from examinfo_charging_item ec,charging_item ci where "
				+ "ec.exam_num = '"+exam_num+"' and ec.charging_item_code = ci.item_code "
				+ "and ci.dep_id = '"+dep_id+"' and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"'";
//		String sql = "select ec.id,ec.pay_status,ec.exam_status from "
//			+ "exam_info e,examinfo_charging_item ec,charging_item ci where e.exam_num = ec.exam_num and "
//			+ "ec.charging_item_code = ci.item_code and ci.dep_id = "+dep_id+" and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
//			+ "and e.id in (select a.id from (select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), "
//			+ "e.id from exam_info e, (select customer_id, a.id, join_date from exam_info a,customer_info b where "
//			+ "a.customer_id = b.id and a.is_Active='Y' and (a.exam_num = '"+exam_num+"' or b.arch_num = '"+exam_num+"')) b where "
//			+ "e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N') as A where "
//			+ "a.row = 1)";
		List<ExaminfoChargingItemDTO> eclist = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		if(eclist.size() == 0){
			return "error-此人没有本科室的检查项目!";
		}else{
			for(ExaminfoChargingItemDTO dto : eclist){
				if("N".equals(dto.getExam_status()) || "D".equals(dto.getExam_status())){
					return "error-此人本科室的存在未检查项目!";
				}
			}
		}
		return "ok-此人本科室所有项目已检查!";
	}

	@Override
	public String saveDepResultJdbc(DepInspectModel model, UserDTO user,String is_depitem_save) throws ServiceException{
		
		List<CustomerVipPrompt> cvipList = null;
		List<UserDTO> userList = null;
		//人员类型 vip的客户
		String vipsql = "select c.type_name,c.type_code from customer_type c,exam_info e "
				+ "where e.customer_type_id = c.id and e.exam_num = '"+model.getExam_num()+"'";
		List<Customer_TypeDTO> typeList = this.jqm.getList(vipsql, Customer_TypeDTO.class);
		if(typeList.size() != 0){
			if("LXVIP".equals(typeList.get(0).getType_code())){
				String sqlVip = " select p.id,e.exam_num,c.user_name,(case when e.exam_type = 'G' then '个人' else v.com_name end) "
						+ " as com_name from customer_info c,exam_info e "
						 + " left join company_info v on v.id=e.company_id "
						+ " left join customer_vip_prompt p on e.exam_num = p.exam_num where c.id = e.customer_id "
						+ "  and e.exam_num = '"+model.getExam_num()+"'";
				cvipList = this.jqm.getList(sqlVip, CustomerVipPrompt.class);
				if(cvipList.size() != 0){
					if(cvipList.get(0).getId() == null || "".equals(cvipList.get(0).getId())){
						
						String usersql = " select distinct u.id userid,u.chi_name from WEB_RESRELATIONSHIP r,WEB_ROLE l,Web_Userjsb s,user_usr u"
								       + " where r.userorroleid = l.ID and l.ID = s.role_id and s.user_id = u.id "
								       + "and r.res_code = 'RS011' and r.datavalue = '1'";
						
						userList = this.jqm.getList(usersql, UserDTO.class);
					}
				}
			}
		}
		
		Connection connection = null;
		// 插入数据库
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			//保存科室结论日志
			String  deplogsql = "insert into exam_dep_result_log(exam_info_id , dep_id , exam_doctor ,exam_date , exam_result_summary,suggestion,creater,create_time,exam_num) "
							+ " values('"+model.getExam_info_id()+"','"+model.getDep_id()+"','"+user.getName()+"','"+DateTimeUtil.getDateTime()+"','"+model.getExam_result_summary()+"',"
							+ "'"+model.getSuggestion()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+model.getExam_num()+"')";
			int deplog_id = 0;
			connection.createStatement().executeUpdate(deplogsql);
			ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
			if (executeQuery.next()) {
				deplog_id = executeQuery.getInt("id");
			}
			String dselsql = "select e.id from exam_dep_result e where e.exam_num = '"+model.getExam_num()+"' "
					+ "and e.dep_num = '"+model.getDep_num()+"' and e.app_type = "+model.getApp_type();
			ResultSet drs = connection.createStatement().executeQuery(dselsql);
			if(drs.next()){
				String updepsql = "update exam_dep_result set exam_doctor = '"+user.getName()+"',exam_result_summary = '"+model.getExam_result_summary()+"',suggestion = '"+model.getSuggestion()+"',"
						+ "Special_setup = '"+model.getSpecial_setup()+"',updater = '"+user.getUserid()+"',update_time = '"+DateTimeUtil.getDateTime()+"' where id='"+drs.getInt("id")+"'";
				connection.createStatement().executeUpdate(updepsql);
			}else{
				String indepsql = "insert into exam_dep_result(exam_info_id,dep_id,exam_doctor,exam_result_summary,suggestion,Special_setup,"
						+ "center_num,creater,create_time,updater,update_time,exam_num,dep_num,app_type) values('"+model.getExam_info_id()+"','"+model.getDep_id()+"',"
						+ "'"+user.getName()+"','"+model.getExam_result_summary()+"','"+model.getSuggestion()+"','"+model.getSpecial_setup()+"',"
						+ "'"+user.getCenter_num()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"'"
						+ ",'"+model.getExam_num()+"','"+model.getDep_num()+"','"+model.getApp_type()+"')";
				connection.createStatement().executeUpdate(indepsql);
			}
			List<CommonExamDetailDTO> lis = model.getDetailList();
			for(int i=0;i<lis.size();i++){
				CommonExamDetailDTO dto = lis.get(i); 
				String selsql = "select c.id from common_exam_detail c where c.exam_num = '"+model.getExam_num()+"' and c.item_code = '"+dto.getItem_num()+"' and c.charging_item_code = '"+dto.getItem_code()+"'";
				ResultSet rs = connection.createStatement().executeQuery(selsql);
				if(rs.next()){
					if("".equals(lis.get(i).getExam_result()) && "N".equals(is_depitem_save)){
						String delsql = "delete common_exam_detail where id = '"+rs.getInt("id")+"'";
						connection.createStatement().executeUpdate(delsql);
					}else{
						String upsql = "update common_exam_detail set exam_doctor = '"+user.getName()+"',exam_result = '"+dto.getExam_result()+"',health_level = '"+dto.getHealth_level()+"',"
								+ "updater = '"+user.getUserid()+"',update_time = '"+DateTimeUtil.getDateTime()+"',charging_item_id='"+dto.getCharging_item_id()+"',exam_result_back='"+dto.getExam_result_back()+"' where id = '"+rs.getInt("id")+"'";
						connection.createStatement().executeUpdate(upsql);
					}
				}else{
					String insql = "insert into common_exam_detail (exam_info_id,exam_item_id,exam_doctor,exam_date,exam_result,health_level,center_num,creater,create_time,updater,update_time,charging_item_id,exam_num,item_code,charging_item_code,exam_result_back) "
							+ "values('"+model.getExam_info_id()+"','"+dto.getExam_item_id()+"','"+user.getName()+"','"+DateTimeUtil.getDateTime()+"','"+dto.getExam_result()+"','"+dto.getHealth_level()+"',"
									+ "'"+user.getCenter_num()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+dto.getCharging_item_id()+"','"+model.getExam_num()+"','"+dto.getItem_num()+"','"+dto.getItem_code()+"','"+dto.getExam_result_back()+"')";
					connection.createStatement().executeUpdate(insql);
				}
				
				String sql = "select c.id from exam_Critical_detail c where c.exam_num = '"+model.getExam_num()+"' and c.item_code = '"+dto.getItem_num() +"' and c.charging_item_code = '"+dto.getItem_code()+"'";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					if(!dto.getHealth_level().equals("W")){
						String cridelsql = "delete exam_Critical_detail where id = "+rs.getInt("id") + " and disease_num is null";
						connection.createStatement().executeUpdate(cridelsql);
					}
				}else{
					if(dto.getHealth_level().equals("W")){
						String criinsql = "insert into exam_Critical_detail(exam_info_id,dept_id,charging_item_id,exam_item_id,exam_result,check_date,is_active,creater,create_time,exam_num,item_code,charging_item_code) "
								+ "values('"+model.getExam_info_id()+"','"+model.getDep_id()+"','"+dto.getCharging_item_id()+"','"+dto.getExam_item_id()+"','"+dto.getExam_result()+"'"
										+ ",'"+DateTimeUtil.getDateTime()+"','Y','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+model.getExam_num()+"','"+dto.getItem_num()+"','"+dto.getItem_code()+"')";
						connection.createStatement().executeUpdate(criinsql);
					}
				}
				rs.close();
				
				//保存细项检查结果日志
				if((!"".equals(lis.get(i).getExam_result()) || "Y".equals(is_depitem_save)) && deplog_id > 0){
					String  comlogsql = " insert into  common_exam_detail_log(dep_result_id , exam_item_id , health_level , exam_result ,creater ,create_time,item_num) "
                                        +" values('"+deplog_id+"','"+lis.get(i).getExam_item_id()+"','"+lis.get(i).getHealth_level()+"','"+lis.get(i).getExam_result()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+dto.getItem_num()+"')";
					connection.createStatement().executeUpdate(comlogsql);
				}
				
			}
			
			String upexamsql = "update exam_info set status = 'J' where exam_num = '"+model.getExam_num()+"' and status in ('Y','D')";
			connection.createStatement().executeUpdate(upexamsql);
			String upexamsql1 = "update exam_info set join_date = '"+DateTimeUtil.getDateTime()+"' where exam_num = '"+model.getExam_num()+"' and join_date is null";
			connection.createStatement().executeUpdate(upexamsql1);
			
			String upitemsql =" update examinfo_charging_item set exam_status='Y',exam_date='"+DateTimeUtil.getDateTime()+"',"
					   + "exam_doctor_id="+user.getUserid()+",exam_doctor_name='"+user.getName()+"', exam_center_num = '"+user.getCenter_num()+"' "
					   + " where isActive = 'Y' and pay_status <> 'M' and exam_status in('N','D','G')"
					   + " and exam_num = '"+model.getExam_num()+"' and charging_item_code in ("+model.getCharing_ids()+") and center_num = '"+user.getCenter_num()+"'";
			connection.createStatement().executeUpdate(upitemsql);
			
			if(model.getInputter() <= 0){
				model.setInputter(user.getUserid());
			}
			String upitemsql1 =" update examinfo_charging_item set inputter='"+model.getInputter()+"' "
					   + " where isActive = 'Y' and pay_status <> 'M' "
					   + " and exam_num = '"+model.getExam_num()+"' and charging_item_code in ("+model.getCharing_ids()+") and center_num = '"+user.getCenter_num()+"'";
			connection.createStatement().executeUpdate(upitemsql1);
			
			if(cvipList != null && userList != null){
				for(UserDTO userDto : userList){
					String insql = "insert into customer_vip_prompt (id,exam_num,user_name,com_name,prompt_status,prompt_user,creater,create_time) "
							+ "values('"+UUID.randomUUID().toString().replaceAll("-", "")+"','"+cvipList.get(0).getExam_num()+"','"+cvipList.get(0).getUser_name()+"','"+cvipList.get(0).getCom_name()+"',"
									+ "'0','"+userDto.getUserid()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"')";
					connection.createStatement().executeUpdate(insql);
				}
			}
			connection.commit();
		} catch (Exception ex) {
			try {
				connection.rollback();

			} catch (SQLException e) {

			}
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
		return "保存成功";
	}
	@Override
	public String getExamItemAppType(String exam_num, long dep_id, String center_num) throws ServiceException {
		String sql = " select distinct ec.app_type from examinfo_charging_item ec,charging_item c "
				   + " where ec.charging_item_code = c.item_code "
				   + " and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "//and ec.exam_status <> 'G'
				   + " and c.dep_id = '"+dep_id+"' and ec.exam_num = '"+exam_num+"'";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		if(list.size() == 2){
			return "3";//职业病与健康体检都存在
		}else if(list.size() == 1){
			if("1".equals(list.get(0).getApp_type())){
				return "1";//只存在健康体检
			}else if("2".equals(list.get(0).getApp_type())){
				return "2";//只存在职业病体检
			}
		}
		return null;
	}

	@Override
	public ExamInfoUserDTO queryIsTiJianType(ExamInfoUserDTO euo) throws ServiceException {
		String sql = " select data_code_children from data_dictionary "
				+ " where id = (select exam_info.customer_type from exam_info "
				+ " where exam_num = '"+euo.getExam_num()+"') ";
				
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		if(list.size() !=0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public DepInspectExamIntionDTO queryCountTypeUser(DepInspectModel model, UserDTO user) throws ServiceException {
		String sql ="SELECT sum((case when( a.yijian > 0) then 1 else 0 end))AS  yijian,"
				+ "sum((case when(a.weijian > 0) then 1 else 0 end))AS weijian,  "
				+ "sum((case when(a.yijian > 0 AND a.weijian > 0) then 1 else 0 end))AS jiancha, "
				+ "sum((case when(a.qijian> 0) then 1 else 0 end))AS qijian,  "
				+ "sum((case when(a.yanqi > 0) then 1 else 0 end))AS yanqi  from "
				+ "(select e.exam_num,dbo.GetNECIByExamIdAndDepid(e.exam_num,ci.dep_id) as weijian,"
				+ "count(ec.id) as zcount,sum((case when( ec.exam_status='Y') then 1 else 0 end))AS  yijian,"
				+ "sum((case when(ec.exam_status='G') then 1 else 0 end))AS qijian,  "
				+ "sum((case when(ec.exam_status='D') then 1 else 0 end))AS yanqi "
				+" from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e "
				+" left join exam_summary es on e.exam_num = es.exam_num and es.center_num='"+user.getCenter_num()+"' "
				+" left join user_usr u on u.id = es.check_doc "
				+" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code "
				+" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) "
				+" and e.is_Active = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+user.getCenter_num()+"' "  //去掉弃检条件  and ec.exam_status <> 'G'
				+" and ci.dep_id = "+user.getDep_id();
		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
		}  
		if (model.getSex() != null && !"".equals(model.getSex())) {// 性别
			if(model.getSex().equals("0")){
				sql += " and c.sex   like '" + '女' + "%'";
			}else{
				sql += " and c.sex   like '" + '男' + "%'";
			}
			
		} 
		if (model.getTime1() != null && !"".equals(model.getTime1())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getTime1() + " 00:00:00.000'";
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 体检结束日期
			sql += " and e.join_date < '" + model.getTime2() + " 23:59:59.999'";
		} 
		if (model.getEmployeeID() != null && "".equals(model.getEmployeeID())) {// 工号
			sql += " and e.employeeID='" + model.getEmployeeID().trim() + "'";
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())){
			if("N".equals(model.getExam_status())){
				sql += " and ec.exam_status in ('N','C','D')";//未检人员
			}else if("Y".equals(model.getExam_status())){
				sql += " and ec.exam_status = '"+model.getExam_status()+"'";//部分已检
				
			}else if("M".equals(model.getExam_status())){
				sql += " and dbo.GetNECIByExamIdAndDepid(ec.exam_num,ci.dep_id)=0  "; //全部已检
			}
		}
		if(model.getDoctor_name() != null && !"".equals(model.getDoctor_name())){
			sql += " and ec.exam_doctor_name = '"+model.getDoctor_name()+"'";
		}
		if (model.getExam_date1() != null && !"".equals(model.getExam_date1())) {// 检查开始日期
			sql += " and ec.exam_date >= '" + model.getExam_date1() + " 00:00:00.000'";
		} 
		if (model.getExam_date2() != null && !"".equals(model.getExam_date2())) {// 检查结束日期
			sql += " and ec.exam_date < '" + model.getExam_date2() + " 23:59:59.999'";
		} 
		if(model.getCustomer_type() !=null && !"".equals(model.getCustomer_type())){
			sql += " and e.customer_type='"+model.getCustomer_type()+"'";
		}
		
		if (model.getExam_type()!=null && !"".equals(model.getExam_type())) {
			sql += " and e.exam_type = '" + model.getExam_type() + "' ";
		}
		sql += " group by e.exam_num,ci.dep_id) a";
		List<DepInspectExamIntionDTO> list = this.jqm.getList(sql,DepInspectExamIntionDTO.class);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
	public String getexaminationforid(Long exam_info_id){
		String sql="select * from examination_item where id='" + exam_info_id + "'" ;
		List<ExaminationItem> list = this.jqm.getList(sql,ExaminationItem.class);
		return list.get(0).getItem_num();
		
	}
	
	private String getchargingitemcodefromid(String ids) {
		String sql = "select item_code from charging_item where id in("+ids+")";
		List<ChargingItem> list = this.jqm.getList(sql, ChargingItem.class);
		return list.get(0).getItem_code();
	}
	
	
	@Override
	public void createExamCritical(String exam_num,String charging_item_code, String item_num)
			throws ServiceException {
		Connection con = null;
		try {
			con = this.jqm.getConnection();
			CallableStatement c = con.prepareCall("{call pro_exam_critical_num(?,?,?)}");
			c.setString(1, exam_num);
			c.setString(2, charging_item_code);
			c.setString(3, item_num);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqle4) {
				System.out.println("close connection exception: " + sqle4.getMessage());
			}
		}
	}
	
	@Override
	public BSRCorrectionValueDTO getBSRCorrectionValueDTO(long age, String sex) throws ServiceException {
		String sql2 = "select * from BSR_correction_value where age = "+age+" and sex = '"+sex+"'";
		List<BSRCorrectionValueDTO>  bcvli=jqm.getList(sql2,BSRCorrectionValueDTO.class);
		if(bcvli.size()>0){
			return  bcvli.get(0);
		}else{
			return  new BSRCorrectionValueDTO();
		}
	}

	public DepartmentDep getDepBydepId(long id )throws ServiceException{
		return (DepartmentDep)this.qm.load(DepartmentDep.class,id);
	}
}
