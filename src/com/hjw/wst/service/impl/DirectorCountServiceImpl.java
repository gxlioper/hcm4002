package com.hjw.wst.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.service.DirectorCountService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class DirectorCountServiceImpl  implements DirectorCountService{
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

	@Override
	public List<JobDTO> getZongjianyisheng() throws ServiceException {
		String sql = "select u.chi_name as id,u.chi_name as name from user_usr u left join dep_user d on u.id=d.user_id  where dep_id='54'";
		List<JobDTO> list = this.jqm.getList(sql, JobDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getExamInfoList(AcceptanceCheckModel model, String center_num, int rows, int page, String sort, String order)
			throws ServiceException {
		int count = 0;
		String sql =" select e.status,e.id,e.exam_num,e.age,c.user_name,c.sex,e.phone,e.address,e.introducer,convert(varchar(50),e.final_date,23) as final_date,e.final_doctor,"
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,e.is_guide_back,"
				   +" e.is_need_guide,e.is_need_barcode,e.counter_check,convert(varchar(50),e.register_date,23) as register_date,"
				   +" dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay,dbo.GetMinExamDateByExamId(e.exam_num) as exam_date"
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" where e.customer_id = c.id and e.is_Active = 'Y'";
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
			sql += " and e.id in (select distinct ec.examinfo_id  from examinfo_charging_item ec where 1=1 and ec.center_num = '"+center_num+"' ";
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
				sql += " and e.join_date < '" + model.getE_join_date() + "'";
				count ++;
			} 
		}
		if (model.getZongjianyisheng() != null && "".equals(model.getZongjianyisheng())) {// 工号
			sql += " and e.final_doctor='" + model.getZongjianyisheng().trim() + "'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			sql += " and e.exam_type ='" + model.getExam_type() + "'";
		}
		if (model.getZongjiandate() != null && !"".equals(model.getZongjiandate())){
			sql += " and e.final_date = '" + model.getZongjiandate() +" 23:59:59'";
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
		
		if("1".equals(model.getDep_category())){
			sql = "select * from ("+sql+") e where 1=1";
			if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
				sql += " and e.exam_date >= '" + model.getS_join_date() + "'";
			} 
			if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
				sql += " and e.exam_date < '" + model.getE_join_date() + "'";
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

	@Override
	public List<DepExamResultDTO> getDirectorDiseaseList(long exam_num, String center_num) throws ServiceException {
		List<DepExamResultDTO> list = new ArrayList<DepExamResultDTO>();
		
		String com_sql = " select * from (select eci.exam_status,dd.dep_name as dep,dd.dep_name,e.item_name,e.id as item_id,ei.id,dd.seq_code,"
					   + " e.seq_code as e_seq_code,dd.dep_category,'' as exam_result, '' as exam_doctor,eci.exam_date"
					   + " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd,"
					   + " charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
					   + " eci.charging_item_code = ci.item_code and ci.dep_id = dd.id "
					   + " and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num and eci.pay_status <> 'M' "
					   + " and eci.isActive = 'Y' and eci.center_num = '"+center_num+"'  and ei.is_Active = 'Y' and dd.dep_category='17' "
					   + " and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"'"
					   + " union all"
					   + " select a.exam_num,a.exam_status,a.dep_name as dep,a.dep_name,a.item_name,a.item_id,a.id,a.seq_code,a.e_seq_code,a.dep_category,"
					   + " e.exam_result_summary as exam_result,e.exam_doctor,e.update_time from (select distinct eci.exam_status,dd.id as dep_id,dd.dep_name,"
					   + " '科室结论' as item_name,0 as item_id,ei.id,dd.seq_code,100000 as e_seq_code,dd.dep_category,ei.exam_num"
					   + " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where "
					   + " ei.exam_num = eci.exam_num and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and "
					   + " eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.center_num = '"+center_num+"'  and ei.is_Active = 'Y'"
					   + " and dd.dep_category='17' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a "
					   + " left join exam_dep_result e on a.dep_id = e.dep_id and a.exam_num = e.exam_num) a order by a.seq_code,a.e_seq_code";
		
		List<DepExamResultDTO> commonList = this.jqm.getList(com_sql, DepExamResultDTO.class);
		for(DepExamResultDTO common : commonList){
			if(common.getItem_id() != 0){
				String sql = " select c.id,c.health_level,c.exam_result,c.exam_doctor from common_exam_detail c "
						   + " where c.exam_info_id = "+common.getId()+" and c.exam_item_id = "+ common.getItem_id();
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
		
		String view_sql="select * from (select a.exam_status,a.dep,a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,"
				+ " v.exam_desc as exam_result,v.exam_doctor,a.exam_date,1 as code,a.item_seq from (select eci.exam_status,ci.item_name as dep_name,"
				+ " '检查描述' as item_name,ci.sam_demo_id as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date,dd.dep_name as dep "
				+ " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ " and eci.charge_item_id = ci.id and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.center_num = '"+center_num+"' "
				+ "  and ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a"
				+ " left join pacs_summary p on a.exam_num = p.examinfo_num left join "
				+ " view_exam_detail v on v.pacs_id = p.id "
				+ " union all "
				+ " select a.exam_status,a.dep,a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,v.exam_result as exam_result,"
				+ " v.exam_doctor,a.exam_date,2 as code,a.item_seq from (select eci.exam_status,ci.item_name as dep_name,'检查结论' as item_name,dd.dep_name as dep ,ci.sam_demo_id "
				+ " as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date from exam_info ei,examinfo_charging_item eci,"
				+ " charging_item ci,department_dep dd where ei.exam_num = eci.exam_num and eci.charge_item_id = ci.id and "
				+ " ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.center_num = '"+center_num+"'  and "
				+ " ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a left join pacs_summary p "
				+ " on a.exam_num = p.examinfo_num left join view_exam_detail v on "
				+ " v.pacs_id = p.id "
				+ " union all "
				+ " select a.exam_status,a.dep,a.dep_name,a.item_name,v.id as item_id,a.id,a.seq_code,a.dep_category,(case when v.id is null then null "
				+ " else 'image_path' end) as exam_result,v.exam_doctor,a.exam_date,3 as code,a.item_seq from (select eci.exam_status,ci.item_name as dep_name,dd.dep_name as dep,"
				+ " '图片' as item_name,ci.sam_demo_id as item_id,ei.id,ei.exam_num,dd.seq_code,dd.dep_category,ci.item_seq,eci.exam_date "
				+ " from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ " and eci.charge_item_id = ci.id and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.center_num = '"+center_num+"' and "
				+ "  ei.is_Active = 'Y' and dd.dep_category='21' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a "
				+ " left join pacs_summary p on  a.exam_num = p.examinfo_num left join "
				+ " view_exam_detail v on v.pacs_id = p.id) a order by a.seq_code,a.item_seq,a.code";
		
		List<DepExamResultDTO> viewList = this.jqm.getList(view_sql, DepExamResultDTO.class);
		
		list.addAll(viewList);//影像科室检查结果
		
		String exam_sql = "select ei.exam_num,a.item_num,a.exam_status,a.dep,a.dep_name,a.item_name,a.item_id,a.id,a.dep_category,e.exam_result+' ('+e.ref_value+'  '"
				+ "+e.item_unit+')' as exam_result,e.exam_doctor,a.exam_date,e.ref_indicator as health_level from (select dd.dep_name as dep,eci.exam_status,ci.item_name as dep_name,e.item_name,e.id as item_id,"
				+ "ei.id,e.seq_code,ci.item_seq,dd.dep_category,eci.exam_date from exam_info ei,examinfo_charging_item eci,charging_item ci,"
				+ "department_dep dd,charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
				+ "eci.charge_item_id = ci.id and ci.dep_id = dd.id and ci.id = cit.charging_item_id and cit.item_code = e.item_num "
				+ "and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.center_num = '"+center_num+"'  and ei.is_Active = 'Y' and "
				+ "dd.dep_category='131' and ci.item_category != '耗材类型' and ei.exam_num = '"+exam_num+"') a left join exam_result_detail e on e.exam_num = a.exam_num "
				+ "and e.item_code = a.item_num order by a.item_seq,a.seq_code";
		
		List<DepExamResultDTO> examList = this.jqm.getList(exam_sql, DepExamResultDTO.class);
		
		list.addAll(examList);//检验科室检查结果
		return list;
	}
}
