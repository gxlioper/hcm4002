package com.hjw.wst.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DailyExamInfoDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.DirectorExamItemDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.service.DirectorDecisionService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class DirectorDecisionServiceImpl implements DirectorDecisionService{
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
	public List<ExaminfoChargingItemDTO> getDirectorItemStatus(String exam_num, String center_num) throws ServiceException {
		
		String sql = "select c.item_name,ec.charge_item_id,c.item_code,ec.exam_status,ec.pay_status,ec.exam_indicator,"
				+ "d.dep_name,ec.exam_doctor_name,ec.exam_date,d.dep_category,ec.personal_pay,ec.team_pay,"
				+ "ec.calculation_rate,ec.calculation_amount from examinfo_charging_item ec,"
				+ "charging_item c,department_dep d where ec.charging_item_code = c.item_code and c.dep_id = d.id "
				+ "and ec.exam_num = '"+exam_num+"' and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
				+ "order  by ec.exam_status , d.seq_code,c.item_seq";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getExamInfoList(AcceptanceCheckModel model, int rows, int page,String sort,String order,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select e.status,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,"
				+ "dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,"
						+ "dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.address,e.introducer,"
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,e.is_guide_back,"
				   +" e.is_need_guide,e.is_need_barcode,e.counter_check,convert(varchar(50),e.register_date,23) as register_date,"
				   +" dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date"
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" where e.customer_id = c.id and e.is_Active = 'Y' and  e.exam_center_num='"+user.getCenter_num()+"' ";
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
		if("1".equals(model.getDep_category())){
			sql += " and e.id in (select distinct ec.examinfo_id  from examinfo_charging_item ec where 1=1 and ec.center_num = '"+user.getCenter_num()+"' ";
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and ec.exam_date >= '" + model.getS_join_date() + "'";
				count ++;
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and ec.exam_date <= '" + model.getE_join_date() + "'";
				count ++;
			} 
			sql += ")";
			
		}else{
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and e.join_date >= '" + model.getS_join_date() + "'";
				count ++;
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and e.join_date <= '" + model.getE_join_date() + "'";
				count ++;
			} 
		}
		if (model.getEmployeeID() != null && "".equals(model.getEmployeeID())) {// 工号
			sql += " and e.employeeID='" + model.getEmployeeID().trim() + "'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			sql += " and e.exam_type ='" + model.getExam_type() + "'";
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())){
			sql += " and e.phone = '" + model.getPhone() +"'";
			count ++;
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getSex() != null && !"".equals(model.getSex())){
			sql += " and c.sex = '"+model.getSex()+"' ";
		}
		if(model.getLevel() != null && !"".equals(model.getLevel())){
			sql += " and e._level = '"+model.getLevel()+"'";
			count ++;
		}

		if(model.getApptype() > 0){
			sql += " and e.apptype = '"+model.getApptype()+"'";
			count ++;
		}
		
		if("1".equals(model.getDep_category())){
			sql = "select * from ("+sql+") e where 1=1";
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and  e.exam_date >= '" + model.getS_join_date() + "'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and  e.exam_date  <='" + model.getE_join_date() + "'";
			} 
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
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamSetDTO> getDirectorExamSetList(String center_num) throws ServiceException {
		String sql = "select e.id,e.set_name from exam_set e where e.is_Active = 'Y' and e.center_num='"+center_num+"' order by e.create_time desc";
		List<ExamSetDTO> list = this.jqm.getList(sql, ExamSetDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentDepDTO> getDirectorDepList( String center_num ) throws ServiceException {
		String sql = "select d.id,d.dep_name from department_dep d where d.isActive = 'Y' and d.dep_category in ('21','17','131') order by d.dep_name";
		List<DepartmentDepDTO> list = this.jqm.getList(sql, DepartmentDepDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargingItemDTO> getDirectorItemList(long dep_id) throws ServiceException {
		String sql = "select c.id,c.item_name from charging_item c where c.isActive = 'Y'";
		if(dep_id != 0){
			sql += " and c.dep_id = " + dep_id;
		}
		sql += " order by c.item_seq";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getDirectorExamItemList(AcceptanceCheckModel model,int rows, int page,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限  Y有 N 无
		String sql = "select distinct e.join_date,e.exam_num,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,"
				+ "e.age,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.status,"
				+ "ec.pay_status,ec.exam_status,ci.item_name,ec.exam_date,ec.exam_doctor_name,e.id,"
				+ "ec.charge_item_id,d.dep_category from customer_info c,examinfo_charging_item ec,"
				+ "charging_item ci,department_dep d,exam_info e left join examinfo_set es on "
				+ "es.exam_num = e.exam_num "
				+ "where e.exam_num = ec.exam_num and e.customer_id = c.id and ec.charge_item_id = ci.id "
				+ "and ci.dep_id = d.id and e.is_Active = 'Y' and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+user.getCenter_num()+"' ";
		
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name like '" + model.getUser_name().trim() + "%'";
		}  
		if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getS_join_date() + " 00:00:00.000'";
		} 
		if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
			sql += " and e.join_date <= '" + model.getE_join_date() + " 23:59:59.999'";
		}
		if("1".equals(model.getDep_category())){
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and ec.exam_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and ec.exam_date <= '" + model.getE_join_date() + " 23:59:59.999'";
			}
		}else{
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and e.join_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and e.join_date <= '" + model.getE_join_date() + " 23:59:59.999'";
			}
		}
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
		}
		if (model.getSet_id() > 0) {
			sql += " and es.exam_set_id = " + model.getSet_id();
		}
		if (model.getDep_id() > 0) {
			sql += " and ci.dep_id = " + model.getDep_id();
		}
		if (model.getCharging_item_ids() != null && !"".equals(model.getCharging_item_ids()) && !"0".equals(model.getCharging_item_ids())) {
			sql += " and ci.id in (" + model.getCharging_item_ids()+")";
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())) {
			sql += " and ec.exam_status = '" + model.getExam_status() + "'";
		}
		if (model.getPay_status() != null && !"".equals(model.getPay_status())) {
			sql += " and ec.pay_status = '" + model.getPay_status()+ "'";
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		
		sql += " order by e.join_date desc";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql,page, rows,DirectorExamItemDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public List<DailyExamInfoDTO> getDilyExamInfoList(AcceptanceCheckModel model, String center_num) throws ServiceException {
		
		int count = 0;
		String tiaojian = " select e.id,c.sex,e.is_guide_back,e.status,e.is_report_print,dbo.GetTeamPayByExamId(e.exam_num) as team_pay,"
				   +" dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date,"
				   +" v.com_name,e.company_id,e.exam_type "
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" where e.customer_id = c.id and e.is_Active = 'Y'  AND e.exam_center_num='"+center_num+"' ";
		
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			tiaojian += " and c.id_num='" + model.getId_num() + "' ";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			tiaojian += " and e.exam_num='" + model.getExam_num().trim() + "' ";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			tiaojian += " and c.user_name like '" + model.getUser_name().trim() + "%' ";
			count ++;
		}
		if("1".equals(model.getDep_category())){
			tiaojian += " and e.id in (select distinct ec.examinfo_id  from examinfo_charging_item ec where 1=1 and ec.center_num = '"+center_num+"' ";
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				tiaojian += " and ec.exam_date >= '" + model.getS_join_date()+"'";
				count ++;
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				tiaojian += " and ec.exam_date <= '" + model.getE_join_date()+"'";
				count ++;
			} 
			tiaojian += ")";
		}else{
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				tiaojian += " and e.join_date >= '" + model.getS_join_date()+"'";
				count ++;
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				tiaojian += " and e.join_date <= '" + model.getE_join_date()+"'";
				count ++;
			} 
		}
		if (model.getCompany_id() > 0) {
			tiaojian += " and e.company_id='" + model.getCompany_id() + "' ";
			count ++;
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			tiaojian += " and e.exam_type ='" + model.getExam_type() + "' ";
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())){
			tiaojian += " and e.phone = '" + model.getPhone() +"' ";
			count ++;
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			tiaojian += " and e.status = '"+model.getStatus()+"' ";
		}
		if (model.getSex() != null && !"".equals(model.getSex())){
			tiaojian += " and c.sex = '"+model.getSex()+"' ";
		}
		if(model.getLevel() != null && !"".equals(model.getLevel())){
			tiaojian += " and e._level = '"+model.getLevel()+"' ";
			count ++;
		}
		if(model.getApptype() > 0){
			tiaojian += " and e.apptype = '"+model.getApptype()+"' ";
			count ++;
		}
		String examDatet = "";
		if("1".equals(model.getDep_category())){
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				examDatet += " and e.exam_date >= '" + model.getS_join_date() + "'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				examDatet += " and e.exam_date <= '" + model.getE_join_date() + "'";
			} 
		}
		
		String sql = "select '全部体检者' as name,count(e.id) person_count,  "
				   +"sum(case when e.sex = '男' then 1 else 0 end) as man_count, "
				   +"sum(case when e.sex = '女' then 1 else 0 end) as woman_count, "
				   +"sum(case when e.is_guide_back = 'Y' then 1 else 0 end) as y_huishou, "
				   +"sum(case when e.is_guide_back = 'N' then 1 else 0 end) as w_huishou, "
				   +"sum(case when e.status = 'Z' then 1 else 0 end) as y_zongjian, "
				   +"sum(case when e.status != 'Z' then 1 else 0 end) as w_zongjian, "
				   +"sum(case when e.is_report_print = 'Y' then 1 else 0 end) as y_report, "
				   +"sum(case when e.is_report_print = 'N' then 1 else 0 end) as w_report, "
				   +"sum(e.team_pay) as team_pay,sum(e.personal_pay) as personal_pay,sum(e.team_pay+e.personal_pay) as amount, "
				   + "-3 as id,null as _parentId "
				   +"from ("+tiaojian+") e where 1=1 "+examDatet
				   +"union all "
				   +"select '全部个人' as name,count(e.id) person_count, "
				   +"sum(case when e.sex = '男' then 1 else 0 end) as man_count, "
				   +"sum(case when e.sex = '女' then 1 else 0 end) as woman_count, "
				   +"sum(case when e.is_guide_back = 'Y' then 1 else 0 end) as y_huishou, "
				   +"sum(case when e.is_guide_back = 'N' then 1 else 0 end) as w_huishou, "
				   +"sum(case when e.status = 'Z' then 1 else 0 end) as y_zongjian, "
				   +"sum(case when e.status != 'Z' then 1 else 0 end) as w_zongjian, "
				   +"sum(case when e.is_report_print = 'Y' then 1 else 0 end) as y_report, "
				   +"sum(case when e.is_report_print = 'N' then 1 else 0 end) as w_report, "
				   +"sum(e.team_pay) as team_pay,sum(e.personal_pay) as personal_pay,sum(e.team_pay+e.personal_pay) as amount, "
				   + "-2 as id,-3 as _parentId "
				   +"from ("+tiaojian+") e where 1=1 and e.exam_type = 'G' "+examDatet
				   +"union all "
				   +"select '全部单位' as name,count(e.id) person_count, "
				   +"sum(case when e.sex = '男' then 1 else 0 end) as man_count, "
				   +"sum(case when e.sex = '女' then 1 else 0 end) as woman_count, "
				   +"sum(case when e.is_guide_back = 'Y' then 1 else 0 end) as y_huishou, "
				   +"sum(case when e.is_guide_back = 'N' then 1 else 0 end) as w_huishou, "
				   +"sum(case when e.status = 'Z' then 1 else 0 end) as y_zongjian, "
				   +"sum(case when e.status != 'Z' then 1 else 0 end) as w_zongjian, "
				   +"sum(case when e.is_report_print = 'Y' then 1 else 0 end) as y_report, "
				   +"sum(case when e.is_report_print = 'N' then 1 else 0 end) as w_report, "
				   +"sum(e.team_pay) as team_pay,sum(e.personal_pay) as personal_pay,sum(e.team_pay+e.personal_pay) as amount, "
				   + "-1 as id,-3 as _parentId "
				   +"from ("+tiaojian+") e where 1=1 and e.exam_type = 'T' "+examDatet
				   +"union all "
				   +"select e.com_name as name,count(e.id) person_count, "
				   +"sum(case when e.sex = '男' then 1 else 0 end) as man_count, "
				   +"sum(case when e.sex = '女' then 1 else 0 end) as woman_count, "
				   +"sum(case when e.is_guide_back = 'Y' then 1 else 0 end) as y_huishou, "
				   +"sum(case when e.is_guide_back = 'N' then 1 else 0 end) as w_huishou, "
				   +"sum(case when e.status = 'Z' then 1 else 0 end) as y_zongjian, "
				   +"sum(case when e.status != 'Z' then 1 else 0 end) as w_zongjian, "
				   +"sum(case when e.is_report_print = 'Y' then 1 else 0 end) as y_report, "
				   +"sum(case when e.is_report_print = 'N' then 1 else 0 end) as w_report, "
				   +"sum(e.team_pay) as team_pay,sum(e.personal_pay) as personal_pay,sum(e.team_pay+e.personal_pay) as amount, "
				   + "e.company_id as id,-1 as _parentId "
				   +"from ("+tiaojian+") e where 1=1 and e.exam_type = 'T' "+examDatet
				   +" group by e.com_name,e.company_id ";
		if(count == 0){
			return new ArrayList<DailyExamInfoDTO>();
		}
		List<DailyExamInfoDTO> list = this.jqm.getList(sql, DailyExamInfoDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getTeamAccountList(AcceptanceCheckModel model, String center_num,int rows, int page) throws ServiceException {
		String sql = "select e.batch_id,b.batch_name,v.com_name,(select count(*) from exam_info e1 "
				+ "where e1.is_Active='Y' and e1.batch_id = e.batch_id) as tjrs,"
				+" (select count(*) from team_account t,team_account_exam_list te where te.acc_num = t.acc_num and t.batchid = e.batch_id)as jsrs"
				+" from exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				   + " left join batch b on b.id=e.batch_id "
				+ " where e.join_date >= '" + model.getS_join_date() + " 00:00:00.000'"
				+" and e.join_date <= '" + model.getE_join_date() + " 23:59:59.999' and e.exam_type = 'T' and e.is_Active = 'Y' ";
		if(model.getCompany_id() > 0){
			sql += " and e.company_id = "+model.getCompany_id();
		}		
		sql +=" group by e.batch_id,b.batch_name,v.com_name,e.company_id order by e.company_id,e.batch_id";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql,page, rows,DailyExamInfoDTO.class);
		if ((map != null) && (map.getList() != null)) {
			List<DailyExamInfoDTO> gilist= new ArrayList<DailyExamInfoDTO>();
			for (int i = 0; i < map.getList().size(); i++) {
				DailyExamInfoDTO dto = (DailyExamInfoDTO) map.getList().get(i);
				String sql1 = "select count(*) person_count from exam_info e where e.batch_id ="+dto.getBatch_id()+"  and "
						+" not exists(select * from examinfo_charging_item ec "
						+" where ec.exam_num = e.exam_num and ec.isActive = 'Y' and ec.exam_indicator = 'T' and ec.center_num = '"+center_num+"') "
						+" and exists(select * from examinfo_charging_item ec where ec.exam_num= e.exam_num "
						+" and ec.isActive = 'Y' and ec.pay_status = 'Y' and ec.exam_indicator = 'G' and ec.center_num = '"+center_num+"')";
				List<DailyExamInfoDTO> list = this.jqm.getList(sql1, DailyExamInfoDTO.class);
				dto.setJsrs(dto.getJsrs() + list.get(0).getPerson_count());
				gilist.add(dto);
			}
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(gilist);
		}
		return webrole;
	}

	@Override
	public PageReturnDTO getTeamSettlementExamList(AcceptanceCheckModel model, String center_num, int rows, int page, String sort,
			String order) throws ServiceException {
		String sql = "select c.user_name,e.id,e.exam_num,convert(varchar(50),e.join_date,23) join_date,e.status,c.sex,e.age,"
				+" e.phone,e.is_marriage,tl.isPrePay,tl.isnotPay,e.is_guide_back,"
				+" dbo.fun_GetSetNameByExamInfoId(e.id) as set_name,"
				+" case when tl.isPrePay is not null or g.exam_num is not null then '已结算' else '未结算' end pay_status,"
				+" case when tl.isPrePay is not null then a.totalamt when g.exam_num is not null then g.totalamt else 0 end totalamt"
				+" from customer_info c,exam_info e"
				+" left join team_account_exam_list tl on tl.exam_num = e.exam_num "
				+" left join company_department cd on cd.id = e._level"
				+" left join user_usr u on e.creater = u.id"
				+" left join (select sum(m.acc_charge ) as totalamt,m.exam_num from team_account_item_list m "
				+" where m.acc_num in(select t.acc_num from team_account t where t.batchid = "+model.getBatch_id()+") group by m.exam_num ) a "
				+" on a.exam_num = e.exam_num"
				+" left join (select e.exam_num,e.id,(select sum(ec.item_amount) from examinfo_charging_item ec where "
				+" ec.exam_num = e.exam_num and ec.isActive = 'Y' and ec.pay_status = 'Y' and ec.center_num = '"+center_num+"') as totalamt from exam_info e where "
				+" e.batch_id ="+model.getBatch_id()+" and not exists(select * from examinfo_charging_item ec where ec.exam_num = e.exam_num and "
				+" ec.isActive = 'Y' and ec.exam_indicator = 'T' and ec.center_num = '"+center_num+"') and exists(select * from examinfo_charging_item ec where "
				+" ec.exam_num= e.exam_num and ec.isActive = 'Y' and ec.pay_status = 'Y' and ec.exam_indicator = 'G' and ec.center_num = '"+center_num+"')) g on g.id = e.id "
				+" where c.id = e.customer_id and e.is_Active = 'Y' and e.exam_type = 'T' and e.batch_id = " + model.getBatch_id();
		if("Y".equals(model.getPay_status())){
			sql += " and (tl.ID is not null or g.exam_num is not null)";
		}else if("N".equals(model.getPay_status())){
			sql += " and tl.ID is null and g.exam_num is null";
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by pay_status desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(!"0".equals(model.getBatch_id())){
			PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoDTO.class);
			if ((map != null) && (map.getList() != null)) {
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
			}
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO getExamComprehenList(AcceptanceCheckModel model, String center_num, int rows, int page, String sort, String order)
			throws ServiceException {
		String sql = "select distinct e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,"
				+ "convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, dbo.GetExamSetNameByExamID(e.exam_num) "
				+ "as set_name,v.com_name as company,e.is_guide_back,e.is_need_guide,e.is_need_barcode,e.counter_check,"
				+ "convert(varchar(50),e.register_date,23) as register_date,dbo.GetTeamPayByExamId(e.exam_num) as team_pay,"
				+ "dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date "
				+ ",case when f.flow_name is null then '导检单未核收' else f.flow_name end flow_name,f.senddate,e.wuxuzongjian,m.t "
				+ " ,dd.data_name as customer_type,pc.print_count,dbo.GetYECIByExamId(e.exam_num) yijian,dbo.GetNECIByExamId(e.exam_num) weijian ,"
				+" e.final_doctor,e.final_date,uu.chi_name as check_name,ess.check_time,ur.chi_name as Report_Print_UserName, e.Report_Print_Date ,us.chi_name as fs_creater ,ess.censoring_time as fs_date ,e.DJD_path ,"
				+" (select  u.chi_name from  user_usr  u  where  e.DJD_image_creater = u.id) as DJD_image_creater "
				+ "from customer_info c,exam_info e"
				+ " left join company_info v on v.id=e.company_id "
				+ " left join examinfo_charging_item ec "
				+ "on e.exam_num = ec.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"'  "
				+ "left join exam_summary ess on  ess.exam_num = e.exam_num  and ess.center_num='"+center_num+"'  "
				+ "left join user_usr us on  ess.censoring_doc = us.id "
				+ "left join user_usr uu on  ess.check_doc = uu.id "
				+ "left join user_usr ur on  e.Report_Print_UserId = ur.id "
				+ "left join charging_item ci on ci.id = ec.charge_item_id "
				+ "left join examinfo_set es on es.exam_num = e.exam_num "
				+ "left join v_exam_flow_log f on f.exam_num = e.exam_num "
				+ " left join exam_flow_config m on m.exam_num=e.exam_num  and  m.center_num = '"+center_num+"'  "
				+ " left join data_dictionary dd on e.customer_type=dd.id "
				+ " left join (select examinfo_id , COUNT(distinct print_doctor) print_count from examinfo_print_report where rep_type = 'G' group by examinfo_id) pc on e.id=pc.examinfo_id "
				+ "where e.customer_id = c.id and e.is_Active = 'Y' and ci.item_category = '普通类型' ";
		boolean flags=false;
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='" + model.getExam_num().trim() + "')";
			flags=true;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name like '" + model.getUser_name().trim() + "%'";
			flags=true;
		}
//		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
//			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
		//flags=true;
//		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			flags=true;
		}
		if("1".equals(model.getDep_category())){
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				flags=true;
				sql += " and ec.exam_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				flags=true;
				sql += " and ec.exam_date < '" + model.getE_join_date() + " 23:59:59.999'";
			}
		}else{
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				flags=true;
				sql += " and e.join_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				flags=true;
				sql += " and e.join_date < '" + model.getE_join_date() + " 23:59:59.999'";
			}
		}
		if (model.getCompany_id() > 0) {
			flags=true;
			sql += " and e.company_id='" + model.getCompany_id() + "'";
		}
		if (model.getSet_id() > 0) {
			flags=true;
			sql += " and es.exam_set_id = " + model.getSet_id();
		}
		if (model.getDep_id() > 0) {
			flags=true;
			sql += " and ci.dep_id = " + model.getDep_id();
		}
		if (model.getCharging_item_ids() != null && !"".equals(model.getCharging_item_ids()) && !"0".equals(model.getCharging_item_ids())) {
			sql += " and ci.id in (" + model.getCharging_item_ids()+")";
			flags=true;
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())) {
			sql += " and ec.exam_status in (" + model.getExam_status() + ")";
			flags=true;
		}		
		
		if (model.getPay_status() != null && !"".equals(model.getPay_status())) {
			sql += " and ec.pay_status in (" + model.getPay_status()+ ")";
			flags=true;
		}
		
		if (model.getWxzj()>=0) {
			sql += " and e.wuxuzongjian =" +model.getWxzj()+ "";
			flags=true;
		}
		
		if("tstz".equals(model.getArch_com_ids())){
			sql+= " and m.t=1 ";
			flags=true;
		}
		
		
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status in ("+model.getStatus()+")";
			flags=true;
		}
			
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			sql += " and e.exam_type ='" + model.getExam_type() + "'";
			flags=true;
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())){
			sql += " and e.phone = '" + model.getPhone() +"'";
			flags=true;
		}
		if(model.getIs_guide_back() != null && !"".equals(model.getIs_guide_back())){
			sql += " and e.is_guide_back = '"+model.getIs_guide_back()+"'";
			flags=true;
		}
		if(model.getSex() != null && !"".equals(model.getSex())){
			sql += " and c.sex = '"+model.getSex()+"'";
			flags=true;
		}
		if(model.getIsVip() != null && !"".equals(model.getIsVip())){
			if("Y".equals(model.getIsVip())){
				sql += " and e.vipflag in ('2','3')";
			}else{
				sql += " and (e.vipflag = '1' or e.vipflag is null)";
			}
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
//		System.out.println("---------------"+sql);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(flags){
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
	}
		return webrole;
	}
	
	
	@Override
	public PageReturnDTO getExamComprehenListWX(AcceptanceCheckModel model, String center_num, int rows, int page, String sort, String order)
			throws ServiceException {
		String sql = "select distinct e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,"
				+ "convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, dbo.GetExamSetNameByExamID(e.exam_num) "
				+ "as set_name,v.com_name as company,e.is_guide_back,e.is_need_guide,e.is_need_barcode,e.counter_check,"
				+ "convert(varchar(50),e.register_date,23) as register_date,dbo.GetTeamPayByExamId(e.exam_num) as team_pay,"
				+ "dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date "
				+ ",case when f.flow_name is null then '导检单未核收' else f.flow_name end flow_name,f.senddate,e.wuxuzongjian,m.t "
				+ " ,dd.data_name as customer_type,pc.print_count,dbo.GetYECIByExamId(e.exam_num) yijian,dbo.GetNECIByExamId(e.exam_num) weijian "
				+ "from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				+ "left join examinfo_charging_item ec "
				+ "on e.exam_num = ec.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"'  "
				+ "left join charging_item ci on ci.id = ec.charge_item_id "
				+ "left join examinfo_set es on es.exam_num = e.exam_num "
				+ "left join v_exam_flow_log f on f.exam_num = e.exam_num "
				+ " left join exam_flow_config m on m.exam_num=e.exam_num  and m.center_num = '"+center_num+"' "
				+ " left join data_dictionary dd on e.customer_type=dd.id "
				+ " left join (select examinfo_id , COUNT(distinct print_doctor) print_count from examinfo_print_report where rep_type = 'G' group by examinfo_id) pc on e.id=pc.examinfo_id "
				+ "where e.customer_id = c.id and e.is_Active = 'Y' and e.wuxuzongjian='1' ";
				
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='" + model.getExam_num().trim() + "')";
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name like '" + model.getUser_name().trim() + "%'";
		}
//		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
//			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
//		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
		}
		if("1".equals(model.getDep_category())){
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and ec.exam_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and ec.exam_date < '" + model.getE_join_date() + " 23:59:59.999'";
			}
		}else{
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and e.join_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and e.join_date < '" + model.getE_join_date() + " 23:59:59.999'";
			}
		}
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
		}
		if (model.getSet_id() > 0) {
			sql += " and es.exam_set_id = " + model.getSet_id();
		}
		if (model.getDep_id() > 0) {
			sql += " and ci.dep_id = " + model.getDep_id();
		}
		if (model.getCharging_item_ids() != null && !"".equals(model.getCharging_item_ids()) && !"0".equals(model.getCharging_item_ids())) {
			sql += " and ci.id in (" + model.getCharging_item_ids()+")";
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())) {
			sql += " and ec.exam_status in (" + model.getExam_status() + ")";
		}
		if (model.getPay_status() != null && !"".equals(model.getPay_status())) {
			sql += " and ec.pay_status in (" + model.getPay_status()+ ")";
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status in ("+model.getStatus()+")";
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			sql += " and e.exam_type ='" + model.getExam_type() + "'";
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())){
			sql += " and e.phone = '" + model.getPhone() +"'";
		}
		if(model.getIs_guide_back() != null && !"".equals(model.getIs_guide_back())){
			sql += " and e.is_guide_back = '"+model.getIs_guide_back()+"'";
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public PageReturnDTO getExamComprehenListWD(AcceptanceCheckModel model, String center_num,int days, int rows, int page, String sort,
			String order) throws ServiceException {
		String sql = "select distinct e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,"
				+ "convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, dbo.GetExamSetNameByExamID(e.exam_num) "
				+ "as set_name,v.com_name as company,e.is_guide_back,e.is_need_guide,e.is_need_barcode,e.counter_check,"
				+ "convert(varchar(50),e.register_date,23) as register_date,dbo.GetTeamPayByExamId(e.exam_num) as team_pay,"
				+ "dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date,e.wuxuzongjian,m.t "
				+ ",case when f.flow_name is null then '导检单未核收' else f.flow_name end flow_name,CONVERT(varchar(10),datediff(day,f.senddate,GETDATE()))+'(天)' as senddate "
				+ " ,dd.data_name as customer_type,pc.print_count,dbo.GetYECIByExamId(e.exam_num) yijian,dbo.GetNECIByExamId(e.exam_num) weijian "
				+ "from customer_info c,exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				+ " left join examinfo_charging_item ec "
				+ "on e.exam_num = ec.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
				+ "left join charging_item ci on ci.id = ec.charge_item_id "
				+ "left join v_exam_flow_log f on f.exam_num = e.exam_num "
				+ " left join exam_flow_config m on m.exam_num=e.exam_num and m.center_num = '"+center_num+"' "
				+ " left join data_dictionary dd on e.customer_type=dd.id "
				+ " left join (select examinfo_id , COUNT(distinct print_doctor) print_count from examinfo_print_report where rep_type = 'G' group by examinfo_id) pc on e.id=pc.examinfo_id "
				+ "where e.customer_id = c.id and e.is_Active = 'Y' and ci.item_category = '普通类型' "
				+ "and e.join_date < '"+DateTimeUtil.getdateAddDay(days, DateTimeUtil.getDate2())+"'"
				+ " and e.status in ('J','D') and ec.exam_status = 'N' and e.wuxuzongjian!='1' ";
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public PageReturnDTO getExamComprehenListWP(AcceptanceCheckModel model, String center_num,int days, int rows, int page, String sort,
			String order) throws ServiceException {
		String sql ="select distinct e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,"
				+ "e.introducer,convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				+ "dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,e.is_guide_back,e.is_need_guide,"
				+ "e.is_need_barcode,e.counter_check,convert(varchar(50),e.register_date,23) as register_date,"
				+ "dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,e.wuxuzongjian,m.t,"
				+ "dbo.GetMinExamDateByExamId(e.exam_num) as exam_date,f.flow_name,CONVERT(varchar(10),datediff(day,f.senddate,GETDATE()))+'(天)' as senddate "
				+ " ,dd.data_name as customer_type,pc.print_count,dbo.GetYECIByExamId(e.exam_num) yijian,dbo.GetNECIByExamId(e.exam_num) weijian "
				+ "from customer_info c,exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				+ " left join exam_flow_config m on m.exam_num=e.exam_num  AND m.center_num = '"+center_num+"'"
				+ " left join data_dictionary dd on e.customer_type=dd.id "
				+ " left join (select examinfo_id , COUNT(distinct print_doctor) print_count from examinfo_print_report where rep_type = 'G' group by examinfo_id) pc on e.id=pc.examinfo_id "
				+ ",v_exam_flow_log f "
				+ "where f.flow_code not in('e1','fe1','m','v','vt','-vt') "
				+ "and datediff(day,f.senddate,GETDATE())>"+days+" and f.exam_num=e.exam_num and "
				+ "e.customer_id = c.id and e.is_Active = 'Y' and e.status in ('J','D') and e.wuxuzongjian!='1' ";
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public PageReturnDTO getExamComprehenListWC(AcceptanceCheckModel model, String center_num,int days, int rows, int page, String sort,
			String order) throws ServiceException {
		String sql = "select distinct e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,"
				+ "convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, dbo.GetExamSetNameByExamID(e.exam_num) "
				+ "as set_name,v.com_name as company,e.is_guide_back,e.is_need_guide,e.is_need_barcode,e.counter_check,"
				+ "convert(varchar(50),e.register_date,23) as register_date,dbo.GetTeamPayByExamId(e.exam_num) as team_pay,e.wuxuzongjian,m.t,"
				+ "dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date "
				+ ",case when f.flow_name is null then '导检单未核收' else f.flow_name end flow_name,CONVERT(varchar(10),datediff(day,f.senddate,GETDATE()))+'(天)' as senddate "
				+ " ,dd.data_name as customer_type,pc.print_count,dbo.GetYECIByExamId(e.exam_num) yijian,dbo.GetNECIByExamId(e.exam_num) weijian ,e.DJD_path ,"
				+" (select  u.chi_name from  user_usr  u  where  e.DJD_image_creater = u.id) as DJD_image_creater, "
				+" e.final_doctor,e.final_date,uu.chi_name as check_name,ess.check_time,ur.chi_name as Report_Print_UserName, e.Report_Print_Date ,us.chi_name as fs_creater ,ess.censoring_time as fs_date "
				+ " from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				+ " left join exam_summary ess on  ess.exam_info_id = e.id "
				+ " left join user_usr us on  ess.censoring_doc = us.id "
				+ " left join user_usr uu on  ess.check_doc = uu.id "
				+ " left join user_usr ur on  e.Report_Print_UserId = ur.id "
				+ " left join exam_flow_config m on m.exam_num=e.exam_num  and m.center_num = '"+center_num+"' "
				+ " left join v_exam_flow_log f on f.exam_num = e.exam_num "
				+ " left join data_dictionary dd on e.customer_type=dd.id "
				+ " left join (select examinfo_id , COUNT(distinct print_doctor) print_count from examinfo_print_report where rep_type = 'G' group by examinfo_id) pc on e.id=pc.examinfo_id "
				+ ",exam_summary es "
				+ "where e.customer_id = c.id and e.is_Active = 'Y'  and  es.center_num='"+center_num+"' "
				+ "and e.join_date < '"+DateTimeUtil.getdateAddDay(days, DateTimeUtil.getDate2())+"'"
				+ "and e.is_report_print = 'N'   and es.exam_info_id=e.id and es.approve_status='B'  and e.wuxuzongjian!='1' ";
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamInfoDTO> printflowlist(long exam_id) throws ServiceException {
		String sql = "select print_doctor , COUNT(*) print_count from examinfo_print_report where rep_type = 'P' and examinfo_id = " + exam_id + " group by print_doctor";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		return list;
	}
	
	@Override
	public List<ChargingItemDTO> getPacsExamResultList(String exam_num, String s_join_date, String e_join_date)
			throws ServiceException {
		String sql = "select item_name ,pacs_item_code ,clinic_diagnose , clinic_symptom as notices, check_doc ,audit_doc ,check_date,audit_date ,p.status ,note,trans_date from   pacs_result p , exam_info  e   "
				+ " where e.exam_num  = p.exam_num  and  p.exam_num = '"+exam_num+"'";
		if (s_join_date != null && !"".equals(s_join_date)) {// 体检开始日期
			sql += " and e.join_date >= '" + s_join_date + " 00:00:00.000'";
		} 
		if (e_join_date != null && !"".equals(e_join_date)) {// 体检结束日期
			sql += " and e.join_date < '" + e_join_date + " 23:59:59.999'";
		}
		sql += "order by pacs_item_code ";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		return list;
	}

	@Override
	public List<DepExamResultDTO> getLisItemList(String exam_num,String s_join_date, String e_join_date) throws ServiceException {
		String sql = "select  lis.lis_item_name as dep_name , lis.report_item_name as item_name , lis.item_result+' ('+ lis.ref+'  '+(case when  lis.item_unit is null then '' else  lis.item_unit end)+')' as exam_result ,"
					+" convert(varchar(50),lis.exam_date,20)as exam_date, lis.doctor as exam_doctor , lis.read_flag, lis.note "
					+" from  lis_result lis,exam_info e  where  lis.exam_num=e.exam_num and lis.exam_num = '"+exam_num+"'";
				if (s_join_date != null && !"".equals(s_join_date)) {// 体检开始日期
					sql += " and e.join_date >= '" + s_join_date + " 00:00:00.000'";
				} 
				if (e_join_date != null && !"".equals(e_join_date)) {// 体检结束日期
					sql += " and e.join_date < '" + e_join_date + " 23:59:59.999'";
				}	
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}
	
}
