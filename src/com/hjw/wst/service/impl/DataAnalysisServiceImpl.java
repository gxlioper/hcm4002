package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DataAnalysisConditionDTO;
import com.hjw.wst.DTO.DataAnalysisDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.DataAnalysisModel;
import com.hjw.wst.service.DataAnalysisService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.support.PageSupport;

public class DataAnalysisServiceImpl implements DataAnalysisService {

	private JdbcQueryManager jqm;
	
	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	@Override
	public PageReturnDTO getResearchDataList(DataAnalysisModel model, UserDTO user, int pagesize, int pageno, String sort,
			String order) throws ServiceException {
		List<DataAnalysisConditionDTO> conditions = model.getCondition();		
		String sql = "select c.user_name,c.sex,c.id_num,e.exam_num,e.exam_type,e.age,e.phone,e.join_date,com.com_name as company";
		String tiaojian = "";
		for (int i = 0; i < conditions.size(); i++) {
			DataAnalysisConditionDTO condition = conditions.get(i);
			if(condition.getDep_category().equals("21")){
				sql += ",(select distinct v.exam_result from v_exam_result v where v.exam_num = e.exam_num and v.sam_demo_id = '"+condition.getSample_id()+"') as " +condition.getTable_colnum();
				if(condition.getCondition().equals("in")){
					tiaojian += "select v.exam_num from v_exam_result v,exam_info ei,customer_info ci where v.exam_info_id = ei.id and ei.customer_id = ci.id and ei.center_num='"+user.getCenter_num()+"' "
							+ "and v.sam_demo_id = '"+condition.getSample_id()+"' and charindex('"+condition.getResult()+"',v.exam_result) > 0";
				}else if(condition.getCondition().equals("not in")){
					tiaojian += "select v.exam_num from v_exam_result v,exam_info ei,customer_info ci where v.exam_info_id = ei.id and ei.customer_id = ci.id and ei.center_num='"+user.getCenter_num()+"' "
							+ "and v.sam_demo_id = '"+condition.getSample_id()+"' and not charindex('"+condition.getResult()+"',v.exam_result) > 0";
				}else{
					tiaojian += "select v.exam_num from v_exam_result v,exam_info ei,customer_info ci where v.exam_info_id = ei.id and ei.customer_id = ci.id and ei.center_num='"+user.getCenter_num()+"' "
							+ "and v.sam_demo_id = '"+condition.getSample_id()+"' and convert(decimal(18,4) ,v.exam_result) "+condition.getCondition()+" '"+condition.getResult()+"'";
				}
			}else{
				sql += ",(select distinct v.exam_result from v_exam_result v where v.exam_num = e.exam_num and v.charging_id = '"+condition.getCharging_id()+"'"
					+ " and v.exam_item_id = '"+condition.getItem_id()+"' ) as "+condition.getTable_colnum();
				if(condition.getCondition().equals("in")){
					tiaojian += "select v.exam_num from v_exam_result v,exam_info ei,customer_info ci where v.exam_info_id = ei.id and ei.customer_id = ci.id and ei.center_num='"+user.getCenter_num()+"' and "
							 + "v.charging_id = '"+condition.getCharging_id()+"' and v.exam_item_id = '"+condition.getItem_id()+"' and charindex('"+condition.getResult()+"',v.exam_result) > 0";
				}else if(condition.getCondition().equals("not in")){
					tiaojian += "select v.exam_num from v_exam_result v,exam_info ei,customer_info ci where v.exam_info_id = ei.id and ei.customer_id = ci.id and ei.center_num='"+user.getCenter_num()+"' and "
							 + "v.charging_id = '"+condition.getCharging_id()+"' and v.exam_item_id = '"+condition.getItem_id()+"' and not charindex('"+condition.getResult()+"',v.exam_result) > 0";
				}else{
					tiaojian += "select v.exam_num from v_exam_result v,exam_info ei,customer_info ci where v.exam_info_id = ei.id and ei.customer_id = ci.id and ei.center_num='"+user.getCenter_num()+"' and "
							 + "v.charging_id = '"+condition.getCharging_id()+"' and v.exam_item_id = '"+condition.getItem_id()+"' and convert(decimal(18,4) ,v.exam_result) "+condition.getCondition()+" '"+condition.getResult()+"'";
				}
			}
			
			if(model.getExam_num() != null && !"".equals(model.getExam_num())){
				tiaojian += " and ei.exam_num = '"+model.getExam_num()+"'";
			}
			if(model.getUser_name() != null && !"".equals(model.getUser_name())){
				tiaojian += " and ci.user_name = '"+model.getUser_name()+"'";
			}
			if(model.getTime1() != null && !"".equals(model.getTime1())){
				tiaojian += " and ei.join_date >= '"+model.getTime1()+" 00:00:00'";
			}
			if(model.getTime2() != null && !"".equals(model.getTime2())){
				tiaojian += " and ei.join_date <= '"+model.getTime2()+" 23:59:59'";
			}
			if(model.getMin_age() >= 0){
				tiaojian += " and ei.age >= '"+model.getMin_age()+"'";
			}
			if(model.getMax_age() >= 0){
				tiaojian += " and ei.age <= '"+model.getMax_age()+"'";
			}
			if(model.getExam_type() != null && !"".equals(model.getExam_type())){
				tiaojian += " and ei.exam_type = '"+model.getExam_type()+"'";
			}
			if(model.getSex() != null && !"".equals(model.getSex())){
				tiaojian += " and ci.sex = '"+model.getSex()+"'";
			}
			if(model.getCompany_id() > 0){
				tiaojian += " and ei.company_id = '"+model.getCompany_id()+"'";
			}
			
			if(i < conditions.size()-1){
				tiaojian += " intersect ";
			}
		}
		sql += " from exam_info e left join company_info com on com.id = e.company_id,customer_info c where e.customer_id = c.id and e.exam_num in("+tiaojian+")";
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO pageDto = new PageReturnDTO();
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, DataAnalysisDTO.class);
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

	@Override
	public List<ChargingItemDTO> getChargingItemListByq(String q,long add_i) throws ServiceException {
		if(q == null){
			q="";
		}else{
			q = q.replaceAll("'", "");
		}
		String sql = "select c.id,c.item_name,"+add_i+" as item_seq,c.dep_category,c.sam_demo_id from charging_item c,department_dep d where c.dep_id = d.id "
				+ "and c.isActive = 'Y' and (c.item_name like '"+q+"%' or c.item_pinyin like '"+q+"%') order by d.seq_code,c.item_seq";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		return list;
	}

	@Override
	public List<ExaminationItemDTO> getExaminationItemListByq(long charging_id,String q,long add_i) throws ServiceException {
		if(q == null){
			q="";
		}else{
			q = q.replaceAll("'", "");
		}
		String sql = "select e.id,e.item_name,e.item_category,"+add_i+" as seq_code from examination_item e,charging_item_exam_item ec where e.id = ec.exam_item_id and e.is_Active = 'Y' "
				+ "and ec.charging_item_id = '"+charging_id+"' and (e.item_name like '"+q+"%' or e.item_pinyin like '"+q+"%') order by e.seq_code";
		List<ExaminationItemDTO> list = this.jqm.getList(sql, ExaminationItemDTO.class);
		return list;
	}
	
}
