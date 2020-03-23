package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.ViewCommonWordsDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.DTO.ViewExamItemDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.domain.ViewExamItem;
import com.hjw.wst.model.ViewExamModel;
import com.hjw.wst.service.ViewExamService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ViewExamServiceImpl implements ViewExamService{  
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	
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
	public List<ViewExamDetailDTO> getViewExamDetail(String exam_num, long depId, String center_num,String app_type) throws ServiceException {
		List<ViewExamDetailDTO> viewExamList = new ArrayList<ViewExamDetailDTO>();
		String examsql="select id from exam_info where exam_num='"+exam_num+"' ";
		List<ExamInfoDTO> infoList = this.jqm.getList(examsql, ExamInfoDTO.class);
		if(infoList!=null&&infoList.size()>0){
		String viewExamSql=" select distinct v.id,p.id as pacs_id,p.pacs_req_code,v.exam_desc,v.exam_result,v.exam_doctor,v.exam_date,"
				+ "p.examinfo_sampleId as sample_id, v.report_picture_path from "
				+ "pacs_summary p left join view_exam_detail v on p.pacs_req_code = v.pacs_req_code "
				+ "where p.examinfo_num = '"+exam_num+"' and  p.id in (select distinct ps.id "
				+ "from pacs_summary ps "
				+ "left join view_exam_detail v on v.pacs_req_code=ps.pacs_req_code "
				+ ",pacs_detail pd "
				+ "where ps.examinfo_num='"+exam_num+"' "
				+ "and ps.pacs_req_code=pd.pacs_req_code "
				+ "and pd.chargingItem_num  in (select ci.item_code from examinfo_charging_item eci,charging_item ci "
				+ "where eci.exam_num='"+exam_num+"' and ci.dep_id='"+depId+"' and eci.pay_status in ('R','Y','N') "
						+ "and eci.charging_item_code = ci.item_code "
				+ "and eci.pay_status <> 'M'  and eci.isActive = 'Y' and eci.center_num = '"+center_num+"'))"
				+ "order by p.id";
		viewExamList = this.jqm.getList(viewExamSql, ViewExamDetailDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("检查科室体检录结果页面-体检者体检结果查询", viewExamSql);
		
		for(ViewExamDetailDTO viewExamDetailDTO : viewExamList){
			viewExamDetailDTO.setDep_id(depId);
			if(viewExamDetailDTO.getExam_result() == null || viewExamDetailDTO.getExam_result().equals("")){
				String sqlm = "select v.exam_result,v.exam_desc from view_common_words v where v.sample_id = '"+viewExamDetailDTO.getSample_id()+"' and v.is_default = 1";
				List<ViewExamDetailDTO> listm = this.jqm.getList(sqlm, ViewExamDetailDTO.class);
				if(listm.size() > 0){
					viewExamDetailDTO.setExam_desc(listm.get(0).getExam_desc());
					viewExamDetailDTO.setExam_result(listm.get(0).getExam_result());
				}
			}else{
				String sqli = "select eci.inputter,u.chi_name as inputters from examinfo_charging_item eci left join user_usr u on u.id = eci.inputter,charging_item ci,pacs_detail pd "
						+ "where eci.charging_item_code = ci.item_code and ci.item_code = pd.chargingItem_num "
						+ "and pd.pacs_req_code = '"+viewExamDetailDTO.getPacs_req_code()+"' and eci.exam_num = '"+exam_num+"' and eci.center_num = '"+center_num+"'";
				List<ViewExamDetailDTO> listi = this.jqm.getList(sqli, ViewExamDetailDTO.class);
				if(listi.size() > 0){
					viewExamDetailDTO.setInputter(listi.get(0).getInputter());
					viewExamDetailDTO.setInputters(listi.get(0).getInputters());
				}
			}
			
			String chargingItemsql = " select p.chargingItem_name as item_name,p.chargingItem_num as item_code,c.id "
					               + " from pacs_detail p,charging_item c where p.chargingItem_num = c.item_code "
					               + "and p.pacs_req_code = '" + viewExamDetailDTO.getPacs_req_code()+"'";
			
			List<ChargingItemDTO> citemList = this.jqm.getList(chargingItemsql, ChargingItemDTO.class);
			
			String item_name = "";
			String item_codes = "";
			for(ChargingItemDTO chargingItemDTO : citemList){
				item_name += chargingItemDTO.getItem_name()+",";
				item_codes += "'"+chargingItemDTO.getItem_code()+"',";
			}
			
			viewExamDetailDTO.setItem_name(item_name.substring(0,item_name.length()-1));
			
			String itemsql = " select v.id,e.item_name,e.item_num,e.id as exam_item_id,e.seq_code, "
						   + " (case when v.exam_result IS NULL then il.exam_result else v.exam_result end) as exam_result"
						   + " ,(select top 1 r.val_info from exam_item_RefandDang r where r.item_code = e.item_num and r.is_ReforDang = 'R') refvalue"
						   + " from charging_item_exam_item ce,examination_item e "
						   + " LEFT JOIN item_result_lib il ON il.id=e.default_value and il.isActive='Y'"
						   + " left join view_exam_item v on e.item_num = v.item_code and "
						   + " v.view_exam_detail_id ="+viewExamDetailDTO.getId() 
						   + " where ce.item_code = e.item_num  "
						   + " and ce.charging_item_code in ("+item_codes.substring(0,item_codes.length()-1)+") order by e.seq_code";
			
			List<ViewExamItemDTO> itemList = this.jqm.getList(itemsql, ViewExamItemDTO.class);
			
			viewExamDetailDTO.setViewItem(itemList);
			
			List<ViewExamDetailDTO> last_resutl = new ArrayList<ViewExamDetailDTO>();
			for(int i = 2;i<5;i++){
				String sql = "select v.exam_result,CONVERT(varchar(50),v.exam_date,23) exam_date,v.exam_desc,v.exam_doctor,v.id from ("
						+ "select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.id,e.exam_num "
						+ "from exam_info e, (select customer_id, id, join_date from exam_info a where a.exam_num='"+exam_num+"' "
						+ "and a.is_Active='Y') b where e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' "
						+ "and counter_check='N') as A, view_exam_detail as v, pacs_detail p "
						+ "where A.row="+i+" and v.exam_num=A.exam_num  and v.pacs_req_code=p.pacs_req_code "
						+ "and p.chargingItem_num in ("+item_codes.substring(0,item_codes.length()-1)+")";
				List<ViewExamDetailDTO> result_list = this.jqm.getList(sql, ViewExamDetailDTO.class);
				last_resutl.addAll(result_list);
			}
			viewExamDetailDTO.setLastExamResult(last_resutl);
		}
		}
		return viewExamList;
	}

	@Override
	public PageReturnDTO getExamInfoList(ViewExamModel model, UserDTO user, int rows, int page,String sort,String order)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select distinct  e.status,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,"
				   +" ec.exam_status,e.freeze, "
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company, "
				   +" dbo.GetExamDoctorNameByExamId(e.exam_num,"+user.getDep_id()+") as exam_times ,e.customer_type_id "
					+" , case when tael.isPrePay is null and b.overflag='1' then 'N' else 'Y' end as canExam "
					+" from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num "
				   +" left join user_usr u on u.id = es.check_doc "
					+ "left join team_account_exam_list tael on e.exam_num = tael.exam_num"
					+" left join batch b on e.batch_id = b.id and b.is_Active = 'Y'"
				   +" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code  "
				   +" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) and ec.center_num = '"+user.getCenter_num()+"' "
				   +" and e.is_Active = 'Y' and ec.pay_status <> 'M' and ec.exam_status <> 'G'"
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
			if("N".equals(model.getExam_status())){
				sql += " and ec.exam_status in ('N','C','D')";//未检人员
			}else if("Y".equals(model.getExam_status())){
				sql += " and ec.exam_status = '"+model.getExam_status()+"'";//部门已检
				  
			}else if("M".equals(model.getExam_status())){
				sql += " and dbo.GetNECIByExamIdAndDepid(ec.exam_num,ci.dep_id)=0 ";     //全部已检
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
		if(model.getCustomer_type()!=null && !"".equals(model.getCustomer_type())){
			sql += " and customer_type ='" + model.getCustomer_type() + "'";
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
			sql += " order by ec.exam_status,e.join_date desc";
		}
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoUserDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("检查科室体检首页-体检人员列表", sql);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveViewExamDetail(ViewExamModel model, UserDTO user) throws ServiceException {
		
		List<ViewExamDetailDTO> list = model.getViewExamList();
		for (ViewExamDetailDTO viewExam : list) {  //保存影像科室总表结果
			List<ExamInfo> listexam = this.qm.find("from ExamInfo e where e.exam_num = '" + viewExam.getExam_num()+"'");
			ExamInfo exam = null;
			if(listexam.size() > 0){
				exam = listexam.get(0);
				if("Z".equals(exam.getStatus())){
					return "error-该用户已终检，不能再检查！";
				}
			}else{
				return "error-该用户不存在！";
			}
			
			List<ViewExamDetail> listview = this.qm.find("from ViewExamDetail v where v.exam_num = '"+viewExam.getExam_num()+"' and v.pacs_req_code = '"+viewExam.getPacs_req_code()+"'");
			ViewExamDetail viewExamDetail = null;
			if(listview.size() != 0){
				viewExamDetail = listview.get(0);
				if(!viewExamDetail.getExam_doctor().equals(user.getName())){
					return "error-您不是该项目的检查医生，不能修改检查结果！";
				}
				
				viewExamDetail.setExam_desc(viewExam.getExam_desc());
				viewExamDetail.setExam_result(viewExam.getExam_result());
				viewExamDetail.setUpdater(user.getUserid());
				viewExamDetail.setUpdate_time(DateTimeUtil.parse());
				
				this.pm.update(viewExamDetail);
			}else{
				viewExamDetail = new ViewExamDetail();
				
				viewExamDetail.setExam_info_id(viewExam.getExam_info_id());
				viewExamDetail.setExam_item_id(0);
				viewExamDetail.setExam_doctor(user.getName());
				viewExamDetail.setExam_desc(viewExam.getExam_desc());
				viewExamDetail.setExam_result(viewExam.getExam_result());
				viewExamDetail.setExam_date(DateTimeUtil.parse());
				viewExamDetail.setCenter_num(user.getCenter_num());
				viewExamDetail.setPacs_id(viewExam.getPacs_id());
				viewExamDetail.setCreater(user.getUserid());
				viewExamDetail.setCreate_time(DateTimeUtil.parse());
				viewExamDetail.setUpdater(user.getUserid());
				viewExamDetail.setUpdate_time(DateTimeUtil.parse());
				viewExamDetail.setExam_num(viewExam.getExam_num());
				viewExamDetail.setPacs_req_code(viewExam.getPacs_req_code());
				
				this.pm.save(viewExamDetail);
			}
			List<ViewExamItemDTO> viewItemList = viewExam.getViewItem();
			
			for(ViewExamItemDTO viewItem : viewItemList){//保存分项目结果
				List<ViewExamItem> listItem = this.qm.find("from ViewExamItem v where v.view_exam_detail_id = " +viewExamDetail.getId() +" and v.item_code = '" + viewItem.getItem_num()+"'");
				ViewExamItem viewExamItem = null;
				if(listItem.size() != 0){
					viewExamItem = listItem.get(0);
					viewExamItem.setExam_result(viewItem.getExam_result());
					viewExamItem.setUpdater(user.getUserid());
					viewExamItem.setUpdate_time(DateTimeUtil.parse());
					
					this.pm.update(viewExamItem);
				}else{
					viewExamItem = new ViewExamItem();
					
					viewExamItem.setView_exam_detail_id(viewExamDetail.getId());
					viewExamItem.setExam_item_id(viewItem.getExam_item_id());
					viewExamItem.setExam_result(viewItem.getExam_result());
					viewExamItem.setItem_code(viewItem.getItem_num());
					viewExamItem.setCreater(user.getUserid());
					viewExamItem.setCreate_time(DateTimeUtil.parse());
					viewExamItem.setUpdater(user.getUserid());
					viewExamItem.setUpdate_time(DateTimeUtil.parse());
					
					this.pm.save(viewExamItem);
				}
			}
			//更新检查状态
			String sql = " from ExaminfoChargingItem ec where ec.exam_num = "+viewExamDetail.getExam_num()
					   + " and ec.center_num = '"+user.getCenter_num()+"' and ec.charging_item_code in (select p.chargingItem_num from Pacsdetail p "
					   + "where p.pacs_req_code = '"+viewExamDetail.getPacs_req_code()+"')";
			List<ExaminfoChargingItem> charingitemList = this.qm.find(sql);
			
			for(ExaminfoChargingItem eItem : charingitemList){
				eItem.setInputter(model.getInputter());
				if(!"Y".equals(eItem.getExam_status())){
					eItem.setExam_status("Y");
					eItem.setExam_date(DateTimeUtil.parse());
					eItem.setExam_doctor_id(user.getUserid());
					eItem.setExam_doctor_name(user.getName());
				}
				this.pm.update(eItem);
			}
			
			List<PacsSummary> listsummary = this.qm.find("from PacsSummary p where p.pacs_req_code = '"+ viewExamDetail.getPacs_req_code()+"'");
			if(listsummary.size() > 0){
				PacsSummary pacsSummary = listsummary.get(0);
				pacsSummary.setExam_status("Y");
				this.pm.update(pacsSummary);
			}
			
			
			exam.setStatus("J");
			if(exam.getJoin_date() == null){
				exam.setJoin_date(DateTimeUtil.getDateTime());
			}
			this.pm.update(listexam.get(0));
		}
		return "ok-保存成功!";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewCommonWordsDTO> getViewExamWords(long sample_id,String cyc) throws ServiceException {
		String sql = "select v.exam_desc,v.exam_result from view_common_words v where v.sample_id = "+sample_id+" and v.isActive='Y'";
		if (cyc != null && !"".equals(cyc)) {
			sql += " and v.exam_result like '%" + cyc.trim() + "%'";
		}
		sql+=" order by v.seq_code";
		List<ViewCommonWordsDTO> list = this.jqm.getList(sql, ViewCommonWordsDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getAllViewExamInfoList(ViewExamModel model,
			UserDTO user, int rows, int page, String sort, String order)
			throws ServiceException {
		int count = 0;
		String sql =" select distinct  e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,"
				   +" ec.exam_status,e.freeze, "
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company "
				  // +" dbo.GetExamDoctorNameByExamId(e.exam_num,ci.dep_id) as exam_times"
				   +" from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num  and es.center_num = '"+user.getCenter_num()+"'   "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code  "
				   +" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) and ec.center_num = '"+user.getCenter_num()+"' "
				   +" and e.is_Active = 'Y' and ec.pay_status <> 'M' and ec.exam_status <> 'G' and ec.exam_status='Y' "
				  // +" and ci.dep_id = "+user.getDep_id();
				   +"  and ci.dep_category='21' ";
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
		
		if (model.getExam_date1() != null && !"".equals(model.getExam_date1())) {// 检查开始日期
			sql += " and ec.exam_date >= '" + model.getExam_date1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getExam_date2() != null && !"".equals(model.getExam_date2())) {// 检查结束日期
			sql += " and ec.exam_date < '" + model.getExam_date2() + " 23:59:59.999'";
			count ++;
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by ec.exam_status,e.join_date desc";
		}
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public void updateViewexamdetail(ViewExamDetail v) throws ServiceException {
		this.pm.update(v);
	}
	
	
	@Override
	public DepartmentDepDTO getDepartMentDtoByid(long dep_id) throws ServiceException {
		String sql = "select d.view_result_type as seq_code,d.dep_num from department_dep d where d.id ="+dep_id;
		List<DepartmentDepDTO> list = this.jqm.getList(sql, DepartmentDepDTO.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return new DepartmentDepDTO();
	}
	
	@Override
	public String saveViewExamDetailDepNum(ViewExamModel model, UserDTO user) throws ServiceException {
		List<ViewExamDetailDTO> list = model.getViewExamList();
		for (ViewExamDetailDTO viewExam : list) {  //保存影像科室总表结果
			List<ExamInfo> listexam = this.qm.find("from ExamInfo e where e.exam_num = '" + viewExam.getExam_num()+"'");
			ExamInfo exam = null;
			if(listexam.size() > 0){
				exam = listexam.get(0);
				if("Z".equals(exam.getStatus())){
					return "error-该用户已终检，不能再检查！";
				}
			}else{
				return "error-该用户信息不存在！";
			}
			
			
			List<ViewExamDetail> listview = this.qm.find("from ViewExamDetail v where v.exam_num = '"+viewExam.getExam_num()+"' and v.dept_num = '"+viewExam.getDep_num()+"'");
			ViewExamDetail viewExamDetail = null;
			if(listview.size() != 0){
				viewExamDetail = listview.get(0);
				if(!viewExamDetail.getExam_doctor().equals(user.getName())){
					return "error-您不是该项目的检查医生，不能修改检查结果！";
				}
				
				viewExamDetail.setExam_desc(viewExam.getExam_desc());
				viewExamDetail.setExam_result(viewExam.getExam_result());
				viewExamDetail.setUpdater(user.getUserid());
				viewExamDetail.setUpdate_time(DateTimeUtil.parse());
				
				this.pm.update(viewExamDetail);
			}else{
				viewExamDetail = new ViewExamDetail();
				
				viewExamDetail.setExam_info_id(viewExam.getExam_info_id());
				viewExamDetail.setExam_item_id(0);
				viewExamDetail.setExam_doctor(user.getName());
				viewExamDetail.setExam_desc(viewExam.getExam_desc());
				viewExamDetail.setExam_result(viewExam.getExam_result());
				viewExamDetail.setExam_date(DateTimeUtil.parse());
				viewExamDetail.setCenter_num(user.getCenter_num());
				viewExamDetail.setPacs_id(0);
				viewExamDetail.setDept_num(viewExam.getDep_num());
				viewExamDetail.setCreater(user.getUserid());
				viewExamDetail.setCreate_time(DateTimeUtil.parse());
				viewExamDetail.setUpdater(user.getUserid());
				viewExamDetail.setUpdate_time(DateTimeUtil.parse());
				viewExamDetail.setExam_num(viewExam.getExam_num());
				viewExamDetail.setPacs_req_code(viewExam.getPacs_req_code());
				
				this.pm.save(viewExamDetail);
			}
			//更新检查状态
			String sql = " from ExaminfoChargingItem ec where ec.exam_num = '"+viewExamDetail.getExam_num()+"' and ec.isActive = 'Y' "//and ec.exam_status <> 'G'
					   + " and ec.pay_status <> 'M' and ec.center_num = '"+user.getCenter_num()+"' and ec.charging_item_code in (select c.item_code from ChargingItem c where c.dep_id = "+user.getDep_id()+" and c.item_category != '耗材类型')";
			List<ExaminfoChargingItem> charingitemList = this.qm.find(sql);
			
			for(ExaminfoChargingItem eItem : charingitemList){
				if(!"Y".equals(eItem.getExam_status())){
					eItem.setExam_status("Y");
					eItem.setExam_date(DateTimeUtil.parse());
					eItem.setExam_doctor_id(user.getUserid());
					eItem.setExam_doctor_name(user.getName());
					this.pm.update(eItem);
				}
			}
			
			exam.setStatus("J");
			if(exam.getJoin_date() == null){
				exam.setJoin_date(DateTimeUtil.getDateTime());
			}
			this.pm.update(exam);
		}
		return "ok-保存成功!";
	}

	@Override
	public List<DepExamResultDTO> getExamResultDTOList(long pacs_id) {
		String sql = "select distinct ci.id as charging_item_id,ci.item_code "
				+ " from pacs_detail p,charging_item ci where "
				+ " p.chargingItem_num = ci.item_code "
				+"  and p.summary_id = '"+pacs_id+"'";
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}
	
	@Override
	public String saveViewExamDetailjdbc(ViewExamModel model, UserDTO user) throws ServiceException {
		List<ViewExamDetailDTO> list = model.getViewExamList();
		for (ViewExamDetailDTO viewExam : list) {  //保存影像科室总表结果
			String sql = "select e.id,e.exam_num,e.status from exam_info e where e.exam_num = '"+ viewExam.getExam_num()+"'";
			List<ExamInfoDTO> examlist = this.jqm.getList(sql, ExamInfoDTO.class);
			if(examlist.size() <= 0){
				return "error-该体检客户不存在！";
			}
			ExamInfoDTO exam = examlist.get(0);
			if("Z".equals(exam.getStatus())){
				return "error-该用户已终检，不能再检查！";
			}
			sql = "select v.id,v.exam_doctor from view_exam_detail v where v.exam_num ='"+viewExam.getExam_num()+"' and v.pacs_req_code = '"+viewExam.getPacs_req_code()+"'";
			List<ViewExamDetailDTO> listview = this.jqm.getList(sql, ViewExamDetailDTO.class);
			ViewExamDetailDTO viewExamDetail = null;
			if(listview.size() != 0){
				viewExamDetail = listview.get(0);
				if(!viewExamDetail.getExam_doctor().equals(user.getName())){
					return "error-您不是该项目的检查医生，不能修改检查结果！";
				}
			}
			Connection connection = null;
			// 插入数据库
			try {
				// 获取数据库连接
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				//保存检查描述与结论
				if(viewExamDetail == null){//新增检查结果
					viewExamDetail = new ViewExamDetailDTO();
					String insertsql = "insert into view_exam_detail(exam_info_id,exam_item_id,exam_doctor,exam_desc,exam_result,"
							+ "exam_date,center_num,creater,create_time,updater,update_time,pacs_id,exam_num,pacs_req_code) "
							+ "values('"+viewExam.getExam_info_id()+"','0','"+user.getName()+"','"+viewExam.getExam_desc()+"'"
							+ ",'"+viewExam.getExam_result()+"','"+DateTimeUtil.getDateTime()+"','"+user.getCenter_num()+"','"+user.getUserid()+"'"
							+ ",'"+DateTimeUtil.getDateTime()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+viewExam.getPacs_id()+"','"+viewExam.getExam_num()+"','"+viewExam.getPacs_req_code()+"')";
					connection.createStatement().executeUpdate(insertsql);
					ResultSet executeQuery = connection.createStatement().executeQuery("select SCOPE_IDENTITY() as id ");//主键id
					if (executeQuery.next()) {
						viewExamDetail.setId(executeQuery.getInt("id"));
					}
				}else{
					String updatesql = "update view_exam_detail set exam_desc = '"+viewExam.getExam_desc()+"',exam_result = '"+viewExam.getExam_result()+"',"
							+ "updater = '"+user.getUserid()+"',update_time = '"+DateTimeUtil.getDateTime()+"' where id = '"+viewExamDetail.getId()+"'";
					connection.createStatement().executeUpdate(updatesql);
				}
				List<ViewExamItemDTO> viewItemList = viewExam.getViewItem();
				for(ViewExamItemDTO viewItem : viewItemList){//保存分项目结果
					
					String selsql = "select i.id from view_exam_item i where i.view_exam_detail_id = '"+viewExamDetail.getId()+"' and i.item_code = '"+viewItem.getItem_num()+"'";
					ResultSet rs = connection.createStatement().executeQuery(selsql);
					if(rs.next()){
						String upsql = "update view_exam_item set exam_result = '"+viewItem.getExam_result()+"',updater = '"+user.getUserid()+"',update_time = '"+DateTimeUtil.getDateTime()+"' where id = '"+rs.getInt("id")+"'";
						connection.createStatement().executeUpdate(upsql);
					}else{
						String insql = "insert into view_exam_item(view_exam_detail_id,exam_item_id,exam_result,creater,create_time,updater,update_time,item_code) "
								+ "values ('"+viewExamDetail.getId()+"','"+viewItem.getExam_item_id()+"','"+viewItem.getExam_result()+"'"
								+ ",'"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+viewItem.getItem_num()+"')";
						connection.createStatement().executeUpdate(insql);
					}
				}
				
				String upexamsql = "update exam_info set status = 'J' where exam_num = '"+viewExam.getExam_num()+"' and status in ('Y','D')";
				connection.createStatement().executeUpdate(upexamsql);
				String upexamsql1 = "update exam_info set join_date = '"+DateTimeUtil.getDateTime()+"' where exam_num = '"+viewExam.getExam_num()+"' and join_date is null";
				connection.createStatement().executeUpdate(upexamsql1);
				
				String upitemsql =" update examinfo_charging_item set exam_status='Y',exam_date='"+DateTimeUtil.getDateTime()+"',"
						   + "exam_doctor_id="+user.getUserid()+",exam_doctor_name='"+user.getName()+"', exam_center_num = '"+user.getCenter_num()+"' "
						   + " where isActive = 'Y' and pay_status <> 'M' and exam_status in('N','D') and center_num = '"+user.getCenter_num()+"' and exam_num = '"+viewExam.getExam_num()+"' and charging_item_code in "
						   + "(select d.chargingItem_num from pacs_detail d where d.pacs_req_code = '"+viewExam.getPacs_req_code()+"')";
				connection.createStatement().executeUpdate(upitemsql);
				if(model.getInputter() <= 0){
					model.setInputter(user.getUserid());
				}
				String upitemsql1 =" update examinfo_charging_item set inputter='"+model.getInputter()+"' "
						   + " where isActive = 'Y' and pay_status <> 'M' and center_num = '"+user.getCenter_num()+"' "
						   + " and exam_num = '"+viewExam.getExam_num()+"' and charging_item_code in "
						   + "(select d.chargingItem_num from pacs_detail d where d.pacs_req_code = '"+viewExam.getPacs_req_code()+"')";
				connection.createStatement().executeUpdate(upitemsql1);
				
				String uppacssql = "update pacs_summary set exam_status = 'Y' where pacs_req_code = '"+viewExamDetail.getPacs_req_code()+"'";
				connection.createStatement().executeUpdate(uppacssql);
				
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
		}
		return "ok-保存成功!";
	}

	@Override
	public String saveViewExamDetailDepNumjdbc(ViewExamModel model, UserDTO user) throws ServiceException {
		List<ViewExamDetailDTO> list = model.getViewExamList();
		
		for (ViewExamDetailDTO viewExam : list) {  //保存影像科室总表结果
			String sql = "select e.id,e.exam_num,e.status from exam_info e where e.exam_num = '"+ viewExam.getExam_num()+"'";
			List<ExamInfoDTO> examlist = this.jqm.getList(sql, ExamInfoDTO.class);
			if(examlist.size() <= 0){
				return "error-该体检客户不存在！";
			}
			ExamInfoDTO exam = examlist.get(0);
			if("Z".equals(exam.getStatus())){
				return "error-该用户已终检，不能再检查！";
			}
			sql = "select v.id,v.exam_doctor from view_exam_detail v where v.exam_num ='"+viewExam.getExam_num()+"' and v.dept_num = '"+viewExam.getDep_num()+"'";
			List<ViewExamDetailDTO> listview = this.jqm.getList(sql, ViewExamDetailDTO.class);
			ViewExamDetailDTO viewExamDetail = null;
			if(listview.size() != 0){
				viewExamDetail = listview.get(0);
				if(!viewExamDetail.getExam_doctor().equals(user.getName())){
					return "error-您不是该项目的检查医生，不能修改检查结果！";
				}
			}
			Connection connection = null;
			// 插入数据库
			try {
				// 获取数据库连接
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				//保存检查描述与结论
				if(viewExamDetail == null){//新增检查结果
					viewExamDetail = new ViewExamDetailDTO();
					String insertsql = "insert into view_exam_detail(exam_info_id,exam_item_id,exam_doctor,exam_desc,exam_result,"
							+ "exam_date,center_num,creater,create_time,updater,update_time,pacs_id,exam_num,dept_num) "
							+ "values('"+viewExam.getExam_info_id()+"','0','"+user.getName()+"','"+viewExam.getExam_desc()+"'"
							+ ",'"+viewExam.getExam_result()+"','"+DateTimeUtil.getDateTime()+"','"+user.getCenter_num()+"','"+user.getUserid()+"'"
							+ ",'"+DateTimeUtil.getDateTime()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','0','"+viewExam.getExam_num()+"','"+viewExam.getDep_num()+"')";
					connection.createStatement().executeUpdate(insertsql);
				}else{
					String updatesql = "update view_exam_detail set exam_desc = '"+viewExam.getExam_desc()+"',exam_result = '"+viewExam.getExam_result()+"',"
							+ "updater = '"+user.getUserid()+"',update_time = '"+DateTimeUtil.getDateTime()+"' where id = '"+viewExamDetail.getId()+"'";
					connection.createStatement().executeUpdate(updatesql);
				}
				
				String upexamsql = "update exam_info set status = 'J' where exam_num = '"+viewExam.getExam_num()+"' and status in ('Y','D')";
				connection.createStatement().executeUpdate(upexamsql);
				String upexamsql1 = "update exam_info set join_date = '"+DateTimeUtil.getDateTime()+"' where exam_num = '"+viewExam.getExam_num()+"' and join_date is null";
				connection.createStatement().executeUpdate(upexamsql1);
				
				String upitemsql =" update examinfo_charging_item set exam_status='Y',exam_date='"+DateTimeUtil.getDateTime()+"',"
						   + "exam_doctor_id="+user.getUserid()+",exam_doctor_name='"+user.getName()+"', exam_center_num = '"+user.getCenter_num()+"' "
						   + " where isActive = 'Y' and pay_status <> 'M' and exam_status in('N','D') and center_num = '"+user.getCenter_num()+"' and exam_num = '"+viewExam.getExam_num()+"' and charging_item_code in (select c.item_code from charging_item "
						   + "c where c.dep_id = "+user.getDep_id()+" and c.item_category != '耗材类型')";
				connection.createStatement().executeUpdate(upitemsql);
				if(model.getInputter() <= 0){
					model.setInputter(user.getUserid());
				}
				String upitemsql1 =" update examinfo_charging_item set inputter='"+model.getInputter()+"' "
						   + " where isActive = 'Y' and pay_status <> 'M' and center_num = '"+user.getCenter_num()+"' "
						   + " and exam_num = '"+viewExam.getExam_num()+"' and charging_item_code in(select c.item_code from charging_item "
						   + "c where c.dep_id = "+user.getDep_id()+" and c.item_category != '耗材类型')";
				connection.createStatement().executeUpdate(upitemsql1);
				
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
		}
		return "ok-保存成功!";
	}
	
	@Override
	public String queryDeptCharingItemMsg(ViewExamModel model, UserDTO user) throws ServiceException {
		String result = "ok-NoItem";
		String sql = "select distinct e.id,ec.exam_status,ec.pay_status,e.is_after_pay from exam_info e,examinfo_charging_item ec,charging_item ci "
				   + "where ec.exam_num = e.exam_num and ec.charging_item_code = ci.item_code AND ec.isActive = 'Y' AND e.is_Active = 'Y' AND "
				   + "ec.exam_status <> 'G' and ec.pay_status <> 'M' "
				   + "AND ci.dep_id = '"+user.getDep_id()+"' "
				   + "AND e.exam_num = '"+model.getExam_num().trim()+"'";
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		if(list.size()>0) {
			//前付费 还没有交费
			if("N".equalsIgnoreCase(list.get(0).getIs_after_pay())) {
				if("N".equalsIgnoreCase(list.get(0).getPay_status())) {
					result = "ok-NoPayCharing";
				}
			}
		}
		return result;
	} 
}
