package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.DTO.CenterConfigurationDTO;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserUseDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.DTO.examItemRefandDangDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.Critical;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExamResultDetail;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.model.ExamResultDetailModel;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamResultDetailService;
import com.hjw.wst.service.ExaminationItemService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ExamResultDetailServiceImpl implements ExamResultDetailService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm; 
	
	private ChargingItemService chargingItemService;
	private DepInspectServiceImpl depInspectServiceImpl;
	private CustomerInfoService customerInfoService;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public DepInspectServiceImpl getDepInspectServiceImpl() {
		return depInspectServiceImpl;
	}

	public void setDepInspectServiceImpl(DepInspectServiceImpl depInspectServiceImpl) {
		this.depInspectServiceImpl = depInspectServiceImpl;
	}

	public ChargingItemService getChargingItemService() {
		return chargingItemService;
	}

	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
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
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamResultDetailDTO> getCharingItemByExamNum(String exam_num,long dep_id,String app_type, String center_num) throws ServiceException {
		String sql = " select s.demo_name,c.item_code,c.item_name,c.exam_num,c.id,ec.exam_status,convert(varchar(50),ec.exam_date,23)"
				   + " as exam_date,ec.exam_doctor_name as exam_doctor,ec.inputter,u.chi_name as inputters "
				   + "from exam_info e,examinfo_charging_item ec left join user_usr u on ec.inputter = u.id,charging_item c,"
				   + " sample_demo s where e.exam_num = ec.exam_num and ec.charging_item_code = c.item_code and c.sam_demo_id = s.id and "
				   + " e.exam_num = '"+exam_num+"' and c.dep_id = "+dep_id+" and ec.exam_status <> 'G' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"'"
				   + " and ec.isActive = 'Y' order by s.print_seq,c.item_seq";
		
		List<ExamResultDetailDTO> list = this.jqm.getList(sql, ExamResultDetailDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamResultDetailDTO> getExamResultDetail(String exam_num, String item_code) throws ServiceException {
		//根据收费项目查询检查项目
		String sql = " select es.id,e.id as exam_item_id,e.item_name,e.item_num,e.exam_num,es.ref_indicator,es.ref_value,"
				   + " case when es.exam_result is null then il.exam_result else es.exam_result end exam_result,"
				   + " (case when es.item_unit is null then e.item_unit else es.item_unit end) item_unit,e.item_category,"
				   + " cast (e.ref_Fmin as varchar(50)) ref_Fmin,cast (e.ref_Fmax as varchar(50)) ref_Fmax,"
				   + " cast (e.ref_Mmin as varchar(50)) ref_Mmin,cast (e.ref_Mmax as varchar(50)) ref_Mmax"
				   + " ,es.exam_doctor,es.exam_date "
				   + " from charging_item_exam_item ce,examination_item e"
				   + " left join exam_result_detail es on es.exam_num = '"+exam_num+"' and e.item_num = es.item_code "
				   + " left join item_result_lib il on il.id=e.default_value and il.isActive='Y' "
				   + " where ce.item_code = e.item_num and ce.charging_item_code ='"+item_code+"' order by e.seq_code";
		List<ExamResultDetailDTO> list = this.jqm.getList(sql, ExamResultDetailDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("检验科室体检录结果页面-体检者体检结果查询", sql);
		for(ExamResultDetailDTO eDetailDTO : list){
			if(!"数字型".equals(eDetailDTO.getItem_category())){
				String sql2 = " select e.val_info from exam_item_RefandDang e where e.is_ReforDang = 'R' and"
						    + " e.item_code = '"+eDetailDTO.getItem_num()+"' order by e.val_index";
				List<examItemRefandDangDTO> listDto = this.jqm.getList(sql2, examItemRefandDangDTO.class);
				eDetailDTO.setItemRefList(listDto);
				
			}
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveExamResultDetail(ExamResultDetailModel model, UserDTO user) throws ServiceException {
		
		List<ExamResultDetailDTO> list = model.getExamResulList();
		
		List<ExamInfo> listexam = this.qm.find("from ExamInfo e where e.exam_num = '" + model.getExam_num()+"'");
		ExamInfo exam = null;
		if(list.size() > 0){
			exam = listexam.get(0);
			if("Z".equals(exam.getStatus())){
				return "error-该用户已终检，不能再检查！";
			}
		}else{
			return "error-体检信息不存在！";
		}
		
		for(ExamResultDetailDTO eDto : list){
			
			ExamResultDetail examResultDetail = null;
			if(eDto.getId() > 0){
				
				examResultDetail = (ExamResultDetail) this.qm.load(ExamResultDetail.class, eDto.getId());
				//examResultDetail = (ExamResultDetail) this.qm.get(ExamResultDetail.class, eDto.getId());
//				if(!examResultDetail.getExam_doctor().equals(user.getName())){
//					return "error-您不是该项目的检查医生，不能修改检查结果！";
//				}
				
				examResultDetail.setExam_result(eDto.getExam_result());
				examResultDetail.setRef_indicator(eDto.getRef_indicator());
				examResultDetail.setUpdater(user.getUserid());
				examResultDetail.setUpdate_time(DateTimeUtil.parse());
				examResultDetail.setApprover(eDto.getApprover());
				examResultDetail.setExam_doctor(eDto.getExam_doctor());
				this.pm.update(examResultDetail);
			}else{
				examResultDetail = new ExamResultDetail();
				
				examResultDetail.setExam_info_id(eDto.getExam_info_id());
				examResultDetail.setExam_item_id(eDto.getExam_item_id());
				examResultDetail.setExam_doctor(eDto.getExam_doctor());
				examResultDetail.setExam_date(DateTimeUtil.parse());
				examResultDetail.setExam_result(eDto.getExam_result());
				examResultDetail.setRef_min("");
				examResultDetail.setRef_indicator(eDto.getRef_indicator());
				examResultDetail.setCenter_num(user.getCenter_num());
				examResultDetail.setRef_value(eDto.getRef_value());
				examResultDetail.setItem_unit(eDto.getItem_unit());
				examResultDetail.setCreater(user.getUserid());
				examResultDetail.setCreate_time(DateTimeUtil.parse());
				examResultDetail.setUpdater(user.getUserid());
				examResultDetail.setUpdate_time(DateTimeUtil.parse());
				examResultDetail.setApprover(eDto.getApprover());
				examResultDetail.setExam_num(model.getExam_num());
				examResultDetail.setItem_code(eDto.getItem_num());
				examResultDetail.setCharging_item_code(eDto.getItem_code());
				examResultDetail.setCharging_item_id(eDto.getCharging_item_id());
				this.pm.save(examResultDetail);
				
				List<Critical> crilist = this.qm.find("from Critical c where c.exam_num = '"+model.getExam_num()+"' and c.item_code = '"+eDto.getItem_num() +"' and c.charging_item_code = '"+eDto.getItem_code()+"'");
				if(crilist.size() > 0){
					if(!"4".equals(eDto.getRef_indicator())){
						if(crilist.get(0).getDisease_num() == null){
							this.pm.remove(crilist.get(0));
						}
					}
				}else{
					if("4".equals(eDto.getRef_indicator())){
						Critical critical = new Critical();
						critical.setExam_info_id(model.getExaminfo_id());
						critical.setDept_id(model.getDep_id());
						critical.setCharging_item_id(eDto.getCharging_item_id()+"");
						critical.setExam_item_id(eDto.getExam_item_id());
						critical.setExam_result(eDto.getExam_result());
						critical.setCheck_date(DateTimeUtil.parse());
						critical.setDone_flag(0);
						critical.setData_source(0);
						critical.setIs_active("Y");
						critical.setCreater(user.getUserid());
						critical.setCreate_time(DateTimeUtil.parse());
						critical.setExam_num(model.getExam_num());
						critical.setItem_code(eDto.getItem_num());
						critical.setCharging_item_code(eDto.getItem_code());
						this.pm.save(critical);
					}
				}
			}
		}
		
		if(!"Y".equals(model.getExam_status())){//未检的项目更新为已检
			List<ExaminfoChargingItem> itemList = this.qm.find("from ExaminfoChargingItem e where e.exam_num = '"+model.getExam_num()
			 +"' and e.charging_item_code ='" +model.getCharging_item_code() +"' and e.isActive = 'Y' and e.pay_status <> 'M' and e.center_num = '"+user.getCenter_num()+"'");
			if(itemList.size() != 0){
				itemList.get(0).setExam_status("Y");
				itemList.get(0).setExam_date(DateTimeUtil.parse());
				String config = this.customerInfoService.getCenterconfigByKey("IS_EXAMRESULTDETAIL_DOCTORPAGE_SHOW", user.getCenter_num()).getConfig_value().trim();
				if("Y".equals(config)){
					itemList.get(0).setExam_doctor_name(list.get(0).getExam_doctor());
					itemList.get(0).setExam_doctor_id(list.get(0).getExam_info_id());
				} else {
					itemList.get(0).setExam_doctor_id(user.getUserid());
					itemList.get(0).setExam_doctor_name(user.getUsername());
				}
				itemList.get(0).setInputter(model.getInputter());
				itemList.get(0).setExam_center_num(user.getCenter_num());
				this.pm.update(itemList.get(0));
			}
		}
		exam.setStatus("J");
		if(exam.getJoin_date() == null){
			exam.setJoin_date(DateTimeUtil.getDateTime());
		}
		this.pm.update(exam);
		return "ok-保存成功!";
	}

	@Override
	public PageReturnDTO getExamInfoList(ExamResultDetailModel model, UserDTO user, int rows, int page,String sort,String order)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select distinct e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,"
				+ "dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.freeze, "
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,e.customer_type_id "
				   +" from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num   and es.center_num = '"+user.getCenter_num()+"'  "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code  "
				   +" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) "
				   +" and e.is_Active = 'Y' and ec.pay_status <> 'M' and ec.exam_status <> 'G' and ec.center_num = '"+user.getCenter_num()+"' ";
		if(model.getDep_id() > 0){
			sql +=" and ci.dep_id = "+model.getDep_id();
		}else{
			sql +=" and ci.dep_id = "+user.getDep_id();
		}		   
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
			if("N".equals(model.getExam_status())){
				sql += " and ec.exam_status in ('N','C','D')";
			}else{
				sql += " and ec.exam_status = '"+model.getExam_status()+"' ";
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
		if (model.getExam_type()!=null && !"".equals(model.getExam_type())) {
			sql += " and e.exam_type = '" + model.getExam_type() + "' ";
			count ++;
		}
		
		if (model.getCharging_item_code()!=null && !"".equals(model.getCharging_item_code())) {
			sql += " and  ci.item_code in ("+model.getCharging_item_code()+") ";
		}
		
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoUserDTO.class);
		
		TranLogTxt.liswriteEror_to_txt_single("检验科室体检首页-体检人员列表", sql);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public List<ExaminfoChargingItemDTO> getInfoItemByIdAndStatus(String exam_num, long dep_id, String exam_status, String center_num)
			throws ServiceException {
		
		String sql = " select e.id,e.exam_num as dep_name,c.item_code,c.item_name,c.dep_category,ec.pay_status,ec.exam_status,c.sam_demo_id as sample_id" 
				   + " from exam_info e,examinfo_charging_item ec,charging_item c where ec.charging_item_code = c.item_code "
				   + " and e.exam_num = ec.exam_num and ec.exam_num = '"+exam_num+"' and ec.exam_status = '"+exam_status+"' "
				   		+ "and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
				   + " and c.dep_id = "+dep_id+" order by c.item_seq";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		for(ExaminfoChargingItemDTO item : list){
			if("21".equals(item.getDep_category())){
				String sql1 =" select v.id,v.report_picture_path,0 as aa,v.pacs_req_code from view_exam_detail v where v.id = "
						   +" (select v.id from view_exam_detail v,pacs_summary p where p.pacs_req_code = v.pacs_req_code and p.examinfo_num = '"+item.getDep_name()+"' and p.examinfo_sampleId = "+item.getSample_id()+") "
						   +" and v.report_picture_path is not null and v.report_picture_path <> ''"
						   +" union all" 
						   +" select v.view_exam_id as id,v.image_path as report_picture_path,v.seq_no as aa,v.pacs_req_code from view_exam_image v where v.pacs_req_code = "
						   +" (select v.pacs_req_code from view_exam_detail v,pacs_summary p where p.pacs_req_code = v.pacs_req_code and p.examinfo_num = '"+item.getDep_name()+"' and p.examinfo_sampleId = "+item.getSample_id()+") order by aa desc ";
				List<ViewExamDetailDTO> list_detail = this.jqm.getList(sql1, ViewExamDetailDTO.class);
				
				if(list_detail.size() != 0){
					item.setDep_num(list_detail.get(0).getPacs_req_code());
				}
			}
		}
		return list;
	}

	@Override
	public DepartmentDepDTO getDepId( String center_num ) throws ServiceException {
		String sql = "select d.id,d.dep_name from department_dep d left join department_vs_center de on de.dep_id = d.id   where d.dep_num = '009' and de.center_num ='"+center_num+"'";
		List<DepartmentDepDTO> list = this.jqm.getList(sql, DepartmentDepDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ExamResultDetailDTO> getViewExamCharingItem(String exam_num, String center_num)
			throws ServiceException {
		 String sql = " select  s.demo_name,pd.chargingItem_name as item_name,v.id,v.exam_doctor,v.exam_date, " 
					+" v.exam_result,v.exam_desc,d.data_name  as positive_find "
					+" from exam_info e,examinfo_charging_item ec,charging_item c,sample_demo s,"
					+" pacs_summary p,pacs_detail pd ,view_exam_detail  v  left join data_dictionary d on  v.positive_find=d.id "
					+" where e.exam_num = ec.exam_num and ec.charging_item_code = c.item_code and  e.exam_num=v.exam_num "
					+" and v.pacs_req_code=p.pacs_req_code  and p.id=pd.summary_id and pd.examinfo_sampleId=s.id and c.dep_category='21' "
					+" and c.sam_demo_id=s.id and e.exam_num = '"+exam_num+"' and ec.center_num = '"+center_num+"' ";
			 
			List<ExamResultDetailDTO> list = this.jqm.getList(sql, ExamResultDetailDTO.class);
			return list;
		}

	@Override
	public List<ExamResultDetailDTO> getViewExamResult(long pace_id,
			long charging_id) throws ServiceException {
		String sql = " select e.item_name,v.exam_result,v.exam_desc,v.id,d.data_name  as positive_find " 
				 +" from charging_item c,charging_item_exam_item ci,examination_item e,view_exam_detail v "
				 +" left join data_dictionary d on  v.positive_find=d.id "
				 +" where  c.item_code = ci.charging_item_code  and   e.item_num=ci.item_code "  
				 +" and   c.id='"+charging_id+"'  AND v.exam_item_id=e.id  AND  v.pacs_id='"+pace_id+"' ";
			List<ExamResultDetailDTO> list = this.jqm.getList(sql, ExamResultDetailDTO.class);
			return list;
		}

	@Override
	public List<ExamResultDetailDTO> getPositiveFindList()
			throws ServiceException {
		String sql="  select d.id,d.data_name from  data_dictionary d  where d.data_code='YXBJ' ";
		List<ExamResultDetailDTO> list = this.jqm.getList(sql, ExamResultDetailDTO.class);
		return list;
	}

	@Override
	public ViewExamDetail getPositiveById(long id) throws ServiceException {
		String hql =" From ViewExamDetail where id='"+id+"' ";
	    List<ViewExamDetail> l=this.qm.find(hql);
	    if(l.size()>0){
	    	return l.get(0);
	    }
		return null;
	}
	@Override
	public List<UserUseDTO> getDoctor( String center_num ) throws ServiceException {
		String sql = " SELECT distinct uu.id,uu.chi_name FROM  user_usr  uu,department_dep d,department_vs_center de,dep_user u WHERE "
				 +"u.dep_id=d.id  AND   d.dep_num='009'  AND   uu.id=u.user_id and de.dep_id = d.id   and   u.apptype='1' "
				+" AND uu.is_active='Y'  AND  d.isActive='Y' and de.center_num ='"+center_num+"' ";
		return	this.jqm.getList(sql,UserUseDTO.class);
	}
}
