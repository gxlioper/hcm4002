package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.*;
import com.hjw.wst.service.CustomerInfoService;
import org.apache.commons.lang.StringUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.StringUtil;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.model.ExamSummaryModel;
import com.hjw.wst.service.ExamSummaryService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ExamSummaryServiceImpl implements ExamSummaryService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
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
	
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getPtResultList(String exam_num,String app_type, String center_num) throws ServiceException {
		
		String str = "";
		String str1 = "";
		if("2".equals(app_type)){
			str = " and eci.app_type = '"+app_type+"'";
			str1 = " and e.app_type='"+app_type+"'";
		}
		String com_sql = " select * from (select a.dep_name,a.item_name,a.item_id,a.id,a.seq_code,a.e_seq_code,"
				+ "a.dep_category,cd.exam_result,cd.exam_doctor,a.exam_date,cd.health_level from ("
				+ "select dd.dep_name,e.item_name,e.id as item_id,ei.id,dd.seq_code,e.seq_code as e_seq_code,dd.dep_category,"
				+ "'' as exam_result, '' as exam_doctor,eci.exam_date,e.item_num,ci.item_code from customer_info c,exam_info ei,"
				+ "examinfo_charging_item eci,charging_item ci,department_dep dd,charging_item_exam_item cit,examination_item e "
				+ "where ei.exam_num = eci.exam_num and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and "
				+ "ci.item_code = cit.charging_item_code and cit.item_code = e.item_num and eci.pay_status <> 'M' and "
				+ "eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='17' and "
				+ "c.id = ei.customer_id and (c.sex = e.sex or e.sex = '全部') and ci.item_category != '耗材类型' and "
				+ "ei.exam_num = '"+exam_num+"' "+str+" ) a left join common_exam_detail cd on cd.item_code = a.item_num and "
				+ "cd.charging_item_code = a.item_code and cd.exam_num = '"+exam_num+"' "
				+ "union all "
				+ "select a.dep_name,a.item_name,a.item_id,a.id,a.seq_code,a.e_seq_code,a.dep_category,e.exam_result_summary "
				+ "as exam_result,e.exam_doctor,e.update_time,'' as health_level from (select distinct dd.id as dep_id,dd.dep_name,"
				+ "'科室结论' as item_name,0 as item_id,ei.id,dd.seq_code,100000 as e_seq_code,dd.dep_category,dd.dep_num "
				+ "from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ "and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' "
				+ "and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='17' and ci.item_category != '耗材类型' and "
				+ "ei.exam_num = '"+exam_num+"') a left join exam_dep_result e on a.dep_num = e.dep_num and e.exam_num = '"+exam_num+"' "+str1
				+ " union all "
				+ "select a.dep_name,a.item_name,a.item_id,a.id,a.seq_code,a.e_seq_code,a.dep_category,e.suggestion as exam_result,"
				+ "e.exam_doctor,e.update_time,'' as health_level from (select distinct dd.id as dep_id,dd.dep_name,"
				+ "'科室建议' as item_name,-1 as item_id,ei.id,dd.seq_code,10000 as e_seq_code,dd.dep_category,dd.dep_num  "
				+ "from exam_info ei,examinfo_charging_item eci,charging_item ci,department_dep dd where ei.exam_num = eci.exam_num "
				+ "and eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and eci.pay_status <> 'M' and eci.isActive = 'Y' "
				+ "and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and dd.dep_category='17' and ci.item_category != '耗材类型' "
				+ "and ei.exam_num = '"+exam_num+"') a left join exam_dep_result e on a.dep_num = e.dep_num "
				+ "and e.exam_num = '"+exam_num+"' "+str1+" ) a order by a.seq_code,a.e_seq_code";
	
		List<DepExamResultDTO> commonList = this.jqm.getList(com_sql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧普通科结果", com_sql);
//		for(DepExamResultDTO common : commonList){
//			if(StringUtils.isEmpty(common.getExam_num())){
//				String sql = " select c.id,c.health_level,c.exam_result,c.exam_doctor from common_exam_detail c "
//						   + " where c.exam_info_id = "+common.getId()+" and c.exam_item_id = '"+ common.getItem_id()+"'";
//				List<CommonExamDetailDTO> comdetailList = this.jqm.getList(sql, CommonExamDetailDTO.class);
//				if(comdetailList.size() != 0){
//					common.setHealth_level(comdetailList.get(0).getHealth_level());
//					common.setExam_result(comdetailList.get(0).getExam_result());
//					common.setExam_doctor(comdetailList.get(0).getExam_doctor());
//				}
//				String WJsql = "select  id  ,data_source from exam_Critical_detail  where exam_num = '"+common.getExam_num()+"'  and   exam_item_id = "+common.getItem_id()+" and is_active = 'Y' ";
//				List<CriticalDTO> criticalList = this.jqm.getList(WJsql, CriticalDTO.class);
//				if(criticalList.size() != 0){
//					common.setCritical_id(criticalList.get(0).getId());
//					common.setData_source(criticalList.get(0).getData_source());
//				}
//			}
//		}
		return commonList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getHyResultList(String exam_num,String examResultStyle,String app_type, String center_num) throws ServiceException {
		
		String str = "";
		if("2".equals(app_type)){
			str = " and eci.app_type = '"+app_type+"'";
		}
		String tiaojian = "";
		if("Y".equals(examResultStyle)){
			tiaojian = " and e.charging_item_code = a.item_code ";
		}
		String exam_sql = "select a.exam_num,a.dep_name,a.item_num,a.item_name,a.item_id,a.id,a.dep_category,e.exam_result,e.ref_value,"
				+ "e.item_unit,e.exam_doctor,e.ref_indicator as health_level,a.exam_date from (select ci.item_name as dep_name,e.item_name,e.id as item_id,ei.exam_num,e.item_num,"
				+ "ei.id,e.seq_code,ci.item_seq,dd.dep_category,eci.exam_date,ci.item_code "
				+ "from exam_info ei,examinfo_charging_item eci,charging_item ci,"
				+ "department_dep dd,charging_item_exam_item cit,examination_item e where ei.exam_num = eci.exam_num and "
				+ "eci.charging_item_code = ci.item_code and ci.dep_id = dd.id and ci.item_code = cit.charging_item_code and cit.item_code = e.item_num "
				+ "and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' and ei.is_Active = 'Y' and "
				+ "dd.dep_category='131' and ei.exam_num = '"+exam_num+"' "+str+") a left join exam_result_detail e on e.exam_num = a.exam_num "+tiaojian
				+ "and e.item_code = a.item_num order by a.item_seq,a.seq_code";
		List<DepExamResultDTO> examList = this.jqm.getList(exam_sql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧检验科结果", exam_sql);
//		if (examList.size() > 0) {
//			for (DepExamResultDTO lislist : examList) {
//				String WJsql = "select  id  ,data_source from exam_Critical_detail  where exam_num = '"+lislist.getExam_num()+"'  and   item_code = '"+lislist.getItem_code()+"' and is_active = 'Y' ";
//				List<CriticalDTO> criticalList = this.jqm.getList(WJsql, CriticalDTO.class);
//				if(criticalList.size() != 0){
//					lislist.setCritical_id(criticalList.get(0).getId());
//					lislist.setData_source(criticalList.get(0).getData_source());
//				}
//			}
//		}
		return examList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getYxResultList(String exam_num,String app_type,String isShowSamplename, String center_num) throws ServiceException {
		List<DepExamResultDTO> viewList =new ArrayList<DepExamResultDTO>();
		String str = "";
		if("2".equals(app_type)){
			str = " and eci.app_type = '"+app_type+"'";
		}
		String dep_seq = "c.item_seq";
		String a_seq = "a.item_seq";
		if("Y".equals(isShowSamplename)){
			dep_seq = "s.print_seq ";
			a_seq = "a.print_seq";
		}
		String view_sql = "select * from ("
				+ "select distinct ps.pacs_req_code,dbo.fun_GetItemNameBySummaryId(ps.id) as dep_name,'检查描述' as item_name,1 as code,v.id as item_id,ps.pacs_req_code as req_id,v.exam_desc as exam_result,v.exam_doctor,v.exam_date,c.sam_demo_id	"
				+ ",d.seq_code,"+dep_seq+" from pacs_summary ps "
				+ "left join view_exam_detail v on v.pacs_req_code=ps.pacs_req_code ,pacs_detail pd,charging_item c,department_dep d,sample_demo s "
				+ "where ps.examinfo_num='"+exam_num+"' "
				+ "and ps.id=pd.summary_id and c.item_code = pd.chargingItem_num and c.dep_id = d.id and s.id = c.sam_demo_id "
				+ "and pd.chargingItem_num  in (select ci.item_code from examinfo_charging_item eci,charging_item ci "
				+ "where eci.exam_num=v.exam_num and eci.charging_item_code = ci.item_code and eci.pay_status <> 'M' "
				+ "and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' "+str+") "
				+ "union all  "
				+ "select distinct ps.pacs_req_code,dbo.fun_GetItemNameBySummaryId(ps.id) as dep_name,'检查结论' as item_name,2 as code,v.id as item_id,ps.pacs_req_code as req_id,v.exam_result,v.exam_doctor,v.exam_date,c.sam_demo_id "
				+ ",d.seq_code,"+dep_seq+" from pacs_summary ps "
				+ "left join view_exam_detail v on v.pacs_req_code=ps.pacs_req_code ,pacs_detail pd,charging_item c,department_dep d,sample_demo s "
				+ "where ps.examinfo_num='"+exam_num+"' "
				+ "and ps.id=pd.summary_id and c.item_code = pd.chargingItem_num and c.dep_id = d.id and s.id = c.sam_demo_id "
				+ "and pd.chargingItem_num  in (select ci.item_code from examinfo_charging_item eci,charging_item ci "
				+ "where eci.exam_num=v.exam_num and eci.charging_item_code = ci.item_code and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' "+str+") "
				+ "union all "
				+ "select distinct ps.pacs_req_code,dbo.fun_GetItemNameBySummaryId(ps.id) as dep_name,'图片' as item_name,3 as code,v.id as item_id,ps.pacs_req_code as req_id,"
				+ "(case when v.id is null then null  else 'image_path' end) as exam_result,v.exam_doctor,v.exam_date,c.sam_demo_id,d.seq_code,"+dep_seq+" "
				+ "from pacs_summary ps "
				+ " left join view_exam_detail v on v.pacs_req_code=ps.pacs_req_code ,pacs_detail pd,charging_item c,department_dep d,sample_demo s "
				+ "where ps.examinfo_num='"+exam_num+"' "
				+ "and ps.id=pd.summary_id and c.item_code = pd.chargingItem_num and c.dep_id = d.id and s.id = c.sam_demo_id "
				+ "and pd.chargingItem_num  in (select ci.item_code from examinfo_charging_item eci,charging_item ci "
				+ "where eci.exam_num=v.exam_num and eci.charging_item_code = ci.item_code and eci.pay_status <> 'M' and eci.isActive = 'Y' and eci.exam_status <> 'G' and eci.center_num = '"+center_num+"' "+str+")"
				+ ") a "
				+ "order by a.seq_code,"+a_seq+",a.dep_name,a.code";
	
		List<DepExamResultDTO> list = this.jqm.getList(view_sql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧影像科结果", view_sql);
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
		viewList = this.jqm.getList(viewsql1, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧影像科结果-1", viewsql1);
		viewList.addAll(list);
		
//		if (viewList.size() > 0) {
//			for (DepExamResultDTO vList : viewList) {
//				if ("检查结论".equals(vList.getItem_name())) {
//					String WJsql = "select ecd.id  ,ecd.data_source from  charging_item ci,pacs_summary p ,view_exam_detail v ,exam_Critical_detail  ecd ,exam_info e"
//								+" where p.examinfo_sampleId = ci.sam_demo_id  and v.pacs_id = p.id  and   ecd.exam_info_id = v.exam_info_id and ecd.exam_num = e.exam_num "
//								+" and ecd.charging_item_id = ci.id and v.id = "+vList.getItem_id()+"  and e.exam_num = '"+exam_num+"' and ecd.is_active = 'Y' ";
//					List<CriticalDTO> criticalList = this.jqm.getList(WJsql, CriticalDTO.class);
//					if(criticalList.size() != 0){
//						vList.setCritical_id(criticalList.get(0).getId());
//						vList.setData_source(criticalList.get(0).getData_source());
//					}
//				}
//			}
//		}
		return viewList;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoDiseaseDTO> getExamInfoDisease(String exam_num,String app_type) throws ServiceException {
		String sql = " select ed.id,ed.exam_info_id,ed.disease_id,ed.disease_name,ed.disease_index,ed.disease_type,ed.suggest,ed.icd_10,ed.disease_description,ed.disease_class" 
				   + " ,d.data_name as disease_classs,ed.disease_num,ed.data_source from examinfo_disease ed left join data_dictionary d on d.id = ed.disease_class "
				   + " where ed.exam_num = '"+exam_num+"' and ed.app_type ='"+app_type+"' order by disease_index";
		List<ExaminfoDiseaseDTO> list = this.jqm.getList(sql, ExaminfoDiseaseDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoDiseaseDTO> createExamInfoDisease(String exam_num,String isExamSuggest, String center_num,String sug_center) throws ServiceException {
		
		String emInfoSql = " select e.id,c.sex,e.age,e.exam_num from exam_info e,customer_info c "
					     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";
		
		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if(infoDtoList.size() == 0){
			return null;
		}else{
			examInfo = infoDtoList.get(0);
		}
		
		String sql = " select c.exam_item_id as item_id,c.item_code,c.exam_result from common_exam_detail c "
				+ "where c.exam_num = '"+exam_num+"' "
				+ "union all "
				+ "select ed.exam_item_id as item_id,ed.item_code,ed.exam_result from exam_result_detail ed "
				+ "where ed.exam_num = '"+exam_num+"'";
		List<DepExamResultDTO> ptAndHyResultList = this.jqm.getList(sql, DepExamResultDTO.class);//查询普通科室与检验科的检查结果
		
		Map<Long,DepExamResultDTO> resultMap = new HashMap<Long, DepExamResultDTO>();
		for(DepExamResultDTO resultDto : ptAndHyResultList){             //将普通科室与检验科的检查结果放入MAP中
			resultMap.put(resultDto.getItem_id(), resultDto);
		}
		
		String viewSql = " select v.exam_item_id as item_id,v.exam_result "
				+ "from view_exam_detail v,exam_info e,examinfo_charging_item ec,pacs_summary p,charging_item c "
				       + " where v.exam_num = e.exam_num and e.exam_num = '"+exam_num+"' and e.exam_num = ec.exam_num "
				       		+ "and ec.charging_item_code = c.item_code and v.pacs_req_code = p.pacs_req_code "
				       + "and c.sam_demo_id = p.examinfo_sampleId and ec.center_num = '"+center_num+"' ";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);//查询影像科室检查结果
		
//		String logcSql = "select d.id,d.disease_id,d.logic_type,d.sex,d.age_max,d.age_min from disease_logic d where d.isActive = 'Y'";
		String logcSql = "select distinct l.id, l.disease_id, l.sex,l.logic_type,l.age_min,l.age_max "
				+ "from disease_logic l,disease_logic_exam_item dl, v_exam_result_new v,examinfo_charging_item ec where l.id=dl.disease_logic_id "
				+ "and dl.item_code=v.item_num and l.isActive='Y' and v.exam_num= '"+exam_num
				+ "' and ec.charging_item_code = v.item_code and v.exam_num = ec.exam_num and ec.center_num = '"+center_num+"' ";
		List<DiseaseLogicDTO> dislogicList = this.jqm.getList(logcSql, DiseaseLogicDTO.class);
		
		
		List<Long> list = new ArrayList<Long>();//疾病ID
		
		for(DiseaseLogicDTO diseaseLogicDTO : dislogicList){//循环疾病逻辑
			if (!diseaseLogicDTO.getSex().equals(examInfo.getSex()) && !diseaseLogicDTO.getSex().equals("全部")) {
				continue;
			}
			if((diseaseLogicDTO.getAge_max() != null && examInfo.getAge() > Long.valueOf(diseaseLogicDTO.getAge_max())) 
					|| (diseaseLogicDTO.getAge_min() != null && examInfo.getAge() < Long.valueOf(diseaseLogicDTO.getAge_min()))){
				continue;
			}
			int count = 0;// 符合条件的疾病逻辑计数器
			boolean and_flag = false;
			
			String logicItemSql = "select d.andOrNo,d.condition,d.condition_value,d.exam_item_id,d.disease_logic_id "
					+"from disease_logic_exam_item d where d.disease_logic_id = "+diseaseLogicDTO.getId()+" order by d.logic_index";
			
			List<DiseaseLogicExamItemDTO> logicItemList = this.jqm.getList(logicItemSql, DiseaseLogicExamItemDTO.class);
			
			for(DiseaseLogicExamItemDTO logicItem : logicItemList){
				
				if("and".equals(logicItem.getAndOrNo())){
					and_flag = true;
				}
				
				if(347 == logicItem.getExam_item_id()){
					int view_count = 0;  //符合条件的疾病逻辑计数器  影像科室的
					for(DepExamResultDTO viewResult : viewList){
						if(convertLogic(viewResult,logicItem)){
							view_count ++;
						}
					}
					if(logicItem.getCondition().trim().equals("in") && view_count > 0){
						count ++;
					}else if(logicItem.getCondition().trim().equals("not in") && view_count == viewList.size()){
						count ++;
					}
				}else{
					DepExamResultDTO resultDto2 = resultMap.get(logicItem.getExam_item_id());
					if (resultDto2 == null) {
						continue;
					}
					if(convertLogic(resultDto2,logicItem)){
						count ++;
					}
				}
			}
			// =============所有条件都符合就加进去
			if(and_flag){
				if (logicItemList.size() != 0 && logicItemList.size() == count) {
					list.add(diseaseLogicDTO.getDisease_id());
				}
			}else{
				if(count > 0){
					list.add(diseaseLogicDTO.getDisease_id());
				}
			}
		}
		
		if(list.size() == 0){
			return new ArrayList<ExaminfoDiseaseDTO>();
		}
		
		//去除重复疾病ID
		Set<Long> set=new HashSet<Long>(list);
		list = new ArrayList<Long>(set);
		
		//合并阳性发现
		String diseaseMergeSql = "select d.before_disease_id,d.later_disease_id from disease_merge d";
		List<DiseaseMergeDTO> mergeList = this.jqm.getList(diseaseMergeSql, DiseaseMergeDTO.class);
		
		for(DiseaseMergeDTO mergeDto : mergeList){
			String[] beforeId = mergeDto.getBefore_disease_id().split(",");
			
			int count = 0;
			List<Long> temp = new ArrayList<Long>();
			for (int i = 0; i < beforeId.length; i++) {
				for(Long diseaId : list){
					if(beforeId[i].equals(diseaId.toString())){
						count++;
						temp.add(diseaId);
					}
				}
			}
			
			if(count != 0 && count == beforeId.length){
				list.removeAll(temp);//移除合并的疾病
				list.add(mergeDto.getLater_disease_id());
			}
		}
		
		Long[] arr = (Long[]) list.toArray(new Long[list.size()]);
		String diseaseIds = StringUtils.join(arr, ",");
		
		String diseaseSql = " select distinct d.id as disease_id,d.disease_name,d.disease_type,dd.seq_code as disease_index,d.icd_10,d.disease_num,0 as data_source "
						  + " from disease_knowloedge_lib d left join data_dictionary dd on dd.id = d.disease_level where d.id "
						  + " in ("+diseaseIds+") order by dd.seq_code";
		List<ExaminfoDiseaseDTO> diseaseList = this.jqm.getList(diseaseSql, ExaminfoDiseaseDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表", diseaseSql);
		int dis_count = 0;
		for(ExaminfoDiseaseDTO disese : diseaseList){
			
			String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
						  + " where d.disease_id = "+ disese.getDisease_id() +" and d.is_active = 'Y'"
						  + " and d.minAge <= "+examInfo.getAge()+" and d.maxAge >= "+examInfo.getAge()
						  + " and (d.sex = '"+examInfo.getSex()+"' or d.sex = '全部')";
			if("Y".equals(sug_center)){
				sugsql += " and d.center_num = '"+center_num+"'";
			}
			List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
			disese.setDisease_index(dis_count);
			if(sugList.size() == 1){
				if("Y".equals(isExamSuggest)){
					disese.setSuggest(disese.getDisease_name() +"： "+sugList.get(0).getDisease_suggestion());
				}else{
					disese.setSuggest(sugList.get(0).getDisease_suggestion());
				}
			}else{
				for(DiseaseKnowloedgeDTO dkLib : sugList){
					if("是".equals(dkLib.getDefault_value())){
						if("Y".equals(isExamSuggest)){
							disese.setSuggest(disese.getDisease_name() +"： "+dkLib.getDisease_suggestion());
						}else{
							disese.setSuggest(dkLib.getDisease_suggestion());
						}
					}
				}
			}
			String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="+disese.getDisease_id();
			List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
			if(descList.size() > 0){
				disese.setDisease_description(descList.get(0).getDisease_description());
			}
			dis_count ++;
		}
		return diseaseList;
	}
	
	public List<ExaminfoDiseaseDTO> createExamInfoDiseaseDep(String exam_num,String siExamDiseaseSeq,String isExamDiseaseItem,String isExamSuggest, String center_num,String sug_center) throws ServiceException{
		String emInfoSql = " select e.id,c.sex,e.age,e.exam_num from exam_info e,customer_info c "
			     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";

		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if(infoDtoList.size() == 0){
			return null;
		}else{
			examInfo = infoDtoList.get(0);
		}
		String sql = "select v.exam_result,v.exam_item_id as item_id,v.charging_id as sam_demo_id from v_exam_result v where v.exam_num = '"+exam_num+"'";
		List<DepExamResultDTO> resultList = this.jqm.getList(sql, DepExamResultDTO.class);//查询该体检人全部检查结果
		Map<Long,Map<Long, DepExamResultDTO>> resultMap = new HashMap<Long, Map<Long, DepExamResultDTO>>();
		
		for(DepExamResultDTO resultDto : resultList){
			if(resultMap.get(resultDto.getSam_demo_id()) == null){
				Map<Long, DepExamResultDTO> childrenMap = new HashMap<Long, DepExamResultDTO>();
				childrenMap.put(resultDto.getItem_id(), resultDto);
				resultMap.put(resultDto.getSam_demo_id(), childrenMap);
			}else{
				Map<Long, DepExamResultDTO> childrenMap = resultMap.get(resultDto.getSam_demo_id());
				childrenMap.put(resultDto.getItem_id(), resultDto);
				resultMap.put(resultDto.getSam_demo_id(), childrenMap);
			}
		}
		String logcSql = " select distinct l.id,l.disease_id,l.sex,l.logic_type,l.age_min,l.age_max"
					   + " from disease_logic l, disease_logic_exam_item dl, v_exam_result v,disease_dep dd,examinfo_charging_item ec"
					   + " where l.id=dl.disease_logic_id and dl.item_code=v.item_num "
					   + " and l.isActive='Y' and v.exam_num= '"+examInfo.getExam_num()+"' and ec.exam_num = v.exam_num and ec.charge_item_id = v.charging_id"
					   + " and l.disease_id = dd.disease_id and v.charging_id in (select str2table from StrToTable(dd.charging_item_ids))"
					   + " and v.exam_item_id in (select str2table from StrToTable(dd.exam_item_ids)) and ec.center_num = '"+center_num+"' ";
		List<DiseaseLogicDTO> dislogicList = this.jqm.getList(logcSql, DiseaseLogicDTO.class);
		
		
		List<Long> list = new ArrayList<Long>();//疾病ID
		for(DiseaseLogicDTO diseaseLogicDTO : dislogicList){//循环疾病逻辑
			if (!diseaseLogicDTO.getSex().equals(examInfo.getSex()) && !diseaseLogicDTO.getSex().equals("全部")) {
				continue;
			}
			if((diseaseLogicDTO.getAge_max() != null && examInfo.getAge() > Long.valueOf(diseaseLogicDTO.getAge_max())) 
					|| (diseaseLogicDTO.getAge_min() != null && examInfo.getAge() < Long.valueOf(diseaseLogicDTO.getAge_min()))){
				continue;
			}
			int count = 0;// 符合条件的疾病逻辑计数器
			boolean and_flag = false;
			
			String logicItemSql = "select d.andOrNo,d.condition,d.condition_value,d.exam_item_id,d.disease_logic_id,d.charging_item_id "
					+"from disease_logic_exam_item d where d.disease_logic_id = "+diseaseLogicDTO.getId()+" order by d.logic_index";
			
			List<DiseaseLogicExamItemDTO> logicItemList = this.jqm.getList(logicItemSql, DiseaseLogicExamItemDTO.class);
			
			for(DiseaseLogicExamItemDTO logicItem : logicItemList){
				
				if("and".equals(logicItem.getAndOrNo())){
					and_flag = true;
				}
				Map<Long, DepExamResultDTO> childrenMap = resultMap.get(logicItem.getCharging_item_id());
				if(childrenMap == null){
					continue;
				}
				DepExamResultDTO resultDto2 = childrenMap.get(logicItem.getExam_item_id());
				if (resultDto2 == null) {
					continue;
				}
				if(convertLogic(resultDto2,logicItem)){
					count ++;
				}
			}
			// =============所有条件都符合就加进去
			if(and_flag){
				if (logicItemList.size() != 0 && logicItemList.size() == count) {
					list.add(diseaseLogicDTO.getDisease_id());
				}
			}else{
				if(count > 0){
					list.add(diseaseLogicDTO.getDisease_id());
				}
			}
		}
		
		if(list.size() == 0){
			return new ArrayList<ExaminfoDiseaseDTO>();
		}
		
		//去除重复疾病ID
		Set<Long> set=new HashSet<Long>(list);
		list = new ArrayList<Long>(set);
		
		//合并阳性发现
		String diseaseMergeSql = "select d.before_disease_id,d.later_disease_id from disease_merge d";
		List<DiseaseMergeDTO> mergeList = this.jqm.getList(diseaseMergeSql, DiseaseMergeDTO.class);
		
		for(DiseaseMergeDTO mergeDto : mergeList){
			String[] beforeId = mergeDto.getBefore_disease_id().split(",");
			
			int count = 0;
			List<Long> temp = new ArrayList<Long>();
			for (int i = 0; i < beforeId.length; i++) {
				for(Long diseaId : list){
					if(beforeId[i].equals(diseaId.toString())){
						count++;
						temp.add(diseaId);
					}
				}
			}
			
			if(count != 0 && count == beforeId.length){
				list.removeAll(temp);//移除合并的疾病
				list.add(mergeDto.getLater_disease_id());
			}
		}
		
		Long[] arr = (Long[]) list.toArray(new Long[list.size()]);
		String diseaseIds = StringUtils.join(arr, ",");
		String diseaseSql1 = " select distinct d.id as disease_id,d.disease_name,d.disease_type,dd.seq_code as disease_index,d.icd_10,d.disease_num,0 as data_source "
						  + " from disease_knowloedge_lib d left join data_dictionary dd on dd.id = d.disease_level where d.id "
						  + " in ("+diseaseIds+") order by dd.seq_code";
		
		String diseaseSql2 = "select k.id as disease_id,k.disease_name,k.disease_type,k.icd_10,d.disease_num,0 as data_source,ci.item_name,ci.id as cid,"
				+ " ei.item_name as eitem_name,ei.id as eid,ci.dep_category from disease_knowloedge_lib k,v_exam_result v,"
				+ " disease_dep dd,department_dep dep,charging_item ci,examination_item ei where v.charging_id = ci.id "
				+ " and v.exam_item_id = ei.id and ci.dep_id = dep.id and k.id = dd.disease_id and k.isActive='Y' and "
				+ " v.exam_info_id= "+examInfo.getId()+" and v.charging_id in (select str2table from StrToTable(dd.charging_item_ids)) "
				+ " and v.exam_item_id in (select str2table from StrToTable(dd.exam_item_ids)) and k.id in ("+diseaseIds+") "
				+ " order by dep.seq_code,ci.item_seq,ei.seq_code";
		List<ExaminfoDiseaseDTO> diseaseList = null;
		if("Y".equals(siExamDiseaseSeq)){
			diseaseList = this.jqm.getList(diseaseSql2, ExaminfoDiseaseDTO.class);
			TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表", diseaseSql2);
		}else{
			diseaseList = this.jqm.getList(diseaseSql1, ExaminfoDiseaseDTO.class);
			TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表", diseaseSql1);
		}
		int dis_count = 0;
		Map<Long,ExaminfoDiseaseDTO> tempMap = new HashMap<Long,ExaminfoDiseaseDTO>();
		List<ExaminfoDiseaseDTO> tempList = new ArrayList<ExaminfoDiseaseDTO>();
		for(ExaminfoDiseaseDTO disese : diseaseList){
			
			if(tempMap.get(disese.getDisease_id()) != null){
				tempList.add(disese);
				continue;
			}
			tempMap.put(disese.getDisease_id(), disese);
			//匹配疾病建议
			String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
						  + " where d.disease_id = "+ disese.getDisease_id() +" and d.is_active = 'Y'"
						  + " and d.minAge <= "+examInfo.getAge()+" and d.maxAge >= "+examInfo.getAge()
						  + " and (d.sex = '"+examInfo.getSex()+"' or d.sex = '全部')";
			if("Y".equals(sug_center)){
				sugsql += " and d.center_num = '"+center_num+"'";
			}
			List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
			disese.setDisease_index(dis_count);
			if(sugList.size() == 1){
				if("Y".equals(isExamSuggest)){
					disese.setSuggest(disese.getDisease_name() +"： "+sugList.get(0).getDisease_suggestion());
				}else{
					disese.setSuggest(sugList.get(0).getDisease_suggestion());
				}
			}else{
				for(DiseaseKnowloedgeDTO dkLib : sugList){
					if("是".equals(dkLib.getDefault_value())){
						if("Y".equals(isExamSuggest)){
							disese.setSuggest(disese.getDisease_name() +"： "+dkLib.getDisease_suggestion());
						}else{
							disese.setSuggest(dkLib.getDisease_suggestion());
						}
					}
				}
			}
			String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="+disese.getDisease_id();
			List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
			if(descList.size() > 0){
				disese.setDisease_description(descList.get(0).getDisease_description());
			}
			dis_count ++;
			
			//阳性名称添加项目名称，结果值
			if("Y".equals(isExamDiseaseItem)){
				if("17".equals(disese.getDep_category())){
					disese.setDisease_name(disese.getItem_name()+"："+disese.getDisease_name());
				}else if("21".equals(disese.getDep_category())){
					disese.setDisease_name(disese.getItem_name()+"："+disese.getDisease_name());
				}else if("131".equals(disese.getDep_category())){
					String sqls = "select e.exam_result,e.item_unit,e.ref_indicator as health_level from exam_result_detail e where e.exam_info_id = "+examInfo.getId()+" and e.exam_item_id = "+disese.getEid();
					List<DepExamResultDTO> examList = this.jqm.getList(sqls, DepExamResultDTO.class);
					if(examList.size() != 0){
						if("1".equals(examList.get(0).getHealth_level())){
							disese.setDisease_name(disese.getItem_name()+"： "+disese.getDisease_name() + " (" + examList.get(0).getExam_result() + " " + examList.get(0).getItem_unit() + ")  ↑ ");
						}else if("2".equals(examList.get(0).getHealth_level())){
							disese.setDisease_name(disese.getItem_name()+"： "+disese.getDisease_name() + " (" + examList.get(0).getExam_result() + " " + examList.get(0).getItem_unit() + ")  ↓ ");
						}else if("3".equals(examList.get(0).getHealth_level())){
							disese.setDisease_name(disese.getItem_name()+"： "+disese.getDisease_name() + " (" + examList.get(0).getExam_result() + " " + examList.get(0).getItem_unit() + ") ");
						}else if("4".equals(examList.get(0).getHealth_level())){
							disese.setDisease_name(disese.getItem_name()+"： "+disese.getDisease_name() + " (" + examList.get(0).getExam_result() + " " + examList.get(0).getItem_unit() + ")  危机 ");
						}
					}else{
						disese.setDisease_name(disese.getItem_name()+"： "+disese.getDisease_name());
					}
				}
			}
			
		}
		diseaseList.removeAll(tempList);
		return diseaseList;
	}
	
	@Override
	public List<ExaminfoDiseaseDTO> createNewExamInfoDisease(String exam_num,String isExamSuggest,String isUseCompositeLogic, String center_num,String sug_center) throws ServiceException{
		String emInfoSql = " select e.id,c.sex,e.age from exam_info e,customer_info c "
			     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";
		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if(infoDtoList.size() == 0){
			return null;
		}else{
			examInfo = infoDtoList.get(0);
		}
		List<ExaminfoDiseaseDTO> diseaseList = null;
		if(isUseCompositeLogic != null && "Y".equals(isUseCompositeLogic.trim())){//生成复合逻辑列表
			
		}else{//只生成单项阳性发现列表
			/*String singleSql = "select distinct l.id as disease_id,l.disease_name,l.disease_type,l.icd_10,l.disease_num,0 as data_source, "
					+ "ed.dep_num,d.dep_name,ed.charging_item_code,ci.item_name,ed.item_code,e.item_name,d.seq_code,ci.item_seq,e.seq_code "
					+ "from examinfo_disease_single ed left join disease_knowloedge_lib l on l.disease_num = ed.disease_num "
					+ "left join department_dep d on ed.dep_num = d.dep_num and d.isActive = 'Y' "
					+ "left join charging_item ci on ed.charging_item_code = ci.item_code and ci.isActive = 'Y' "
					+ "left join examination_item e on ed.item_code = e.item_num and e.is_Active = 'Y' "
					+ "where ed.exam_num = '"+exam_num+"' order by d.seq_code,ci.item_seq,e.seq_code";*/
			
			
			String singleSql = "select distinct dkl.id as disease_id ,dkl.disease_type,ced.exam_result_back as exam_result ,ced.item_code ,dd.seq_code,dkl.disease_num,dkl.disease_name,ci.item_name as charging_item_name ,ei.item_name,dd.dep_num,ced.charging_item_code from "		
							+"	department_dep dd , charging_item ci, examination_item ei, common_exam_detail    ced  "
							+"	left  join examinfo_disease_single eds on eds.charging_item_code = ced.charging_item_code and eds.item_code = ced.item_code and	eds.exam_num = ced.exam_num  "
							+"	left  join  disease_knowloedge_lib   dkl on dkl.disease_num = eds.disease_num "
							+"	where  ced.charging_item_code = ci.item_code "
							+"	and ci.dep_id = dd.id  and ced.exam_num = '"+exam_num+"'"
							+"	and ced.health_level = 'Y'  and ei.item_num = ced.item_code"
							+"	order by dd.seq_code";
			diseaseList = this.jqm.getList(singleSql, ExaminfoDiseaseDTO.class);
			TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表-普通", singleSql);
			String  yxSql="select distinct dd.dep_num,dd.dep_name,c.item_code as charging_item_code,ei.item_num as item_code,c.item_name as charging_item_name,ei.item_name,eds.disease_name,eds.disease_num,dd.seq_code,dkl.id as disease_id,v.exam_result ,dd.dep_num "
						 +"	from view_exam_detail v, "
						 +"	exam_info e,pacs_summary p,"
						 +"	charging_item c,"
						 +"	examinfo_charging_item ec,"
						 +"	department_dep dd,"
						 +"	sample_demo s ,"
						 +"	examination_item ei,"
						 +"	charging_item_exam_item  ciei"
						 +"	left join examinfo_disease_single  eds  on eds.charging_item_code = ciei.charging_item_code and eds.item_code = ciei.item_code"
						 +" left join disease_knowloedge_lib dkl  on dkl.disease_num = eds.disease_num "
						 +"	where s.id = c.sam_demo_id  and  ciei.charging_item_code = c.item_code and ciei.item_code = ei.item_num"
						 +"	and ec.charging_item_code = c.item_code and e.exam_num = ec.exam_num"
						 +"	and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
						 +"	and v.exam_num = e.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = e.exam_num and dd.id = c.dep_id"
						 +"	and p.examinfo_sampleId = c.sam_demo_id and e.exam_num='"+exam_num+"' order by dd.seq_code";
			List<ExaminfoDiseaseDTO> yxdiseaseList	 = this.jqm.getList(yxSql, ExaminfoDiseaseDTO.class);	
			TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表-检查", yxSql);
			if (yxdiseaseList.size() > 0) {
				for (ExaminfoDiseaseDTO examinfoDiseaseDTO : yxdiseaseList) {
					diseaseList.add(examinfoDiseaseDTO);
				}
			}
			String jySql = "select distinct ei.item_name,ei.item_num as item_code,c.item_name as charging_item_name,c.item_code as charging_item_code,dkl.id as disease_id,dkl.disease_name ,er.exam_result, c.item_seq,ei.seq_code,dd.dep_num "  
					     +"  from exam_info e,examination_item ei,charging_item c,charging_item_exam_item ci,department_dep dd,examinfo_charging_item eci ,exam_result_detail er"
					     +"  left join examinfo_disease_single  eds  on eds.charging_item_code = er.charging_item_code and eds.item_code = er.item_code"
					     +"  left join disease_knowloedge_lib dkl  on dkl.disease_num = eds.disease_num "
					     +"  where dd.id = c.dep_id and er.exam_num = e.exam_num and er.item_code = ei.item_num and e.exam_num='"+exam_num+"'"
					     +"  and ei.item_num = ci.item_code and ci.charging_item_code = c.item_code and eci.isActive ='Y' and eci.pay_status <> 'M' and eci.center_num = '"+center_num+"' "
					     +"  and eci.charging_item_code = c.item_code and eci.exam_num = e.exam_num and er.ref_indicator <> '0'"
					     +"  order by c.item_seq,ei.seq_code ";
			List<ExaminfoDiseaseDTO> jydiseaseList	 = this.jqm.getList(jySql, ExaminfoDiseaseDTO.class);
			TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表-检验", jySql);
			
			if (jydiseaseList.size() > 0) {
				for (ExaminfoDiseaseDTO examinfoDiseaseDTO : jydiseaseList) {
					diseaseList.add(examinfoDiseaseDTO);
				}
			}
			
			int dis_count = 0;
			if(diseaseList.size() > 0){
				for(ExaminfoDiseaseDTO disese : diseaseList){
					String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
								  + " where d.disease_id = "+ disese.getDisease_id() +" and d.is_active = 'Y'"
								  + " and d.minAge <= "+examInfo.getAge()+" and d.maxAge >= "+examInfo.getAge()
								  + " and (d.sex = '"+examInfo.getSex()+"' or d.sex = '全部')";
					if("Y".equals(sug_center)){
						sugsql += " and d.center_num = '"+center_num+"'";
					}
					List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
					disese.setDisease_index(dis_count);
					if(sugList.size() == 1){
						if("Y".equals(isExamSuggest)){
							disese.setSuggest(disese.getDisease_name() +"： "+sugList.get(0).getDisease_suggestion());
						}else{
							disese.setSuggest(sugList.get(0).getDisease_suggestion());
						}
					}else{
						for(DiseaseKnowloedgeDTO dkLib : sugList){
							if("是".equals(dkLib.getDefault_value())){
								if("Y".equals(isExamSuggest)){
									disese.setSuggest(disese.getDisease_name() +"： "+dkLib.getDisease_suggestion());
								}else{
									disese.setSuggest(dkLib.getDisease_suggestion());
								}
							}
						}
					}
					String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="+disese.getDisease_id();
					List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
					if(descList.size() > 0){
						disese.setDisease_description(descList.get(0).getDisease_description());
					}
					dis_count ++;
				}
			}
		}
		return diseaseList;
	}
	
	public List<ExaminfoDiseaseDTO> createNewExamInfoDiseaseSingle(String exam_num,String isExamSuggest, String center_num,String sug_center) throws ServiceException{
		String emInfoSql = " select e.id,c.sex,e.age from exam_info e,customer_info c "
			     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";

		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if(infoDtoList.size() == 0){
			return null;
		}else{
			examInfo = infoDtoList.get(0);
		}
		
		String sql = "select v.exam_result,v.item_num,v.item_code from v_exam_result_new v where v.exam_num = '"+exam_num+"'";
		List<DepExamResultDTO> ptAndHyResultList = this.jqm.getList(sql, DepExamResultDTO.class);//查询科室检查结果
		
		Map<String,DepExamResultDTO> resultMap = new HashMap<String, DepExamResultDTO>();
		for(DepExamResultDTO resultDto : ptAndHyResultList){             //将科室检查结果放入MAP中
			if("".equals(resultDto.getItem_num())){
				resultMap.put(resultDto.getItem_code(), resultDto);
			}else{
				resultMap.put(resultDto.getItem_num(), resultDto);
			}
		}
		
		
		String logcSql = "select d.id as ids,dl.id as disease_id,d.item_code as item_num from disease_logic_single d,v_exam_result_new v,"
				+ "examination_item e,disease_knowloedge_lib dl where d.logic_class = 0 and d.item_code = e.item_num and v.item_num = e.item_num "
				+ "and dl.disease_num = d.disease_num and d.isActive = 'Y' and d.sex in ('全部','"+examInfo.getSex()+"') "
				+ "and '"+examInfo.getAge()+"' > d.age_min and '"+examInfo.getAge()+"' < d.age_max and v.exam_num = '"+exam_num+"'";
		List<DiseaseLogicDTO> dislogicList = this.jqm.getList(logcSql, DiseaseLogicDTO.class);
		
		String logcSql_c = "select d.id as ids,dl.id as disease_id,d.item_code as item_num from disease_logic_single d,v_exam_result_new v,"
				+ "disease_knowloedge_lib dl where d.logic_class = 2 and v.item_code = d.item_code "
				+ "and dl.disease_num = d.disease_num and d.isActive = 'Y' and d.sex in ('全部','"+examInfo.getSex()+"') "
				+ "and '"+examInfo.getAge()+"' > d.age_min and '"+examInfo.getAge()+"' < d.age_max and v.exam_num = '"+exam_num+"'";
		List<DiseaseLogicDTO> dislogicList_c = this.jqm.getList(logcSql_c, DiseaseLogicDTO.class);
		
		dislogicList.addAll(dislogicList_c);
		List<Long> list = new ArrayList<Long>();//疾病ID
		
		for(DiseaseLogicDTO diseaseLogicDTO : dislogicList){//循环疾病逻辑
			//先判断危机条件
			String itemsql = "select d.logic_item_name,d.critical_flag,d.logic_index,d.id from disease_logic_single_item d "
					+ "where d.logic_single_id = '"+diseaseLogicDTO.getIds()+"' and d.isActive = 'Y' order by d.critical_flag desc";
			List<DiseaseLogicItemDTO> itemlist = this.jqm.getList(itemsql, DiseaseLogicItemDTO.class);
			for (DiseaseLogicItemDTO diseaseLogicItemDTO : itemlist) {
				String consql = "select d.condition,d.condition_value from disease_logic_single_item_condition d "
						+ "where d.logic_single_item_id = '"+diseaseLogicItemDTO.getId()+"' order by d.logic_index";
				List<DiseaseLogicItemConditionDTO> conlist = this.jqm.getList(consql, DiseaseLogicItemConditionDTO.class);
				int count = 0;
				for (DiseaseLogicItemConditionDTO diseaseLogicItemConditionDTO : conlist) {
					DiseaseLogicExamItemDTO itemdto = new DiseaseLogicExamItemDTO();
					itemdto.setCondition(diseaseLogicItemConditionDTO.getCondition());
					itemdto.setCondition_value(diseaseLogicItemConditionDTO.getCondition_value());
					if(convertLogic(resultMap.get(diseaseLogicDTO.getItem_num()),itemdto)){
						count ++;
					}
				}
				if(count > 0 && count == conlist.size()){//满足条件生成的疾病放入MAP中，结束该逻辑循环，进行下一条逻辑循环
					list.add(diseaseLogicDTO.getDisease_id());
					break;
				}
			}
			
		}
		
		if(list.size() == 0){
			return new ArrayList<ExaminfoDiseaseDTO>();
		}
		
		//去除重复疾病ID
		Set<Long> set=new HashSet<Long>(list);
		list = new ArrayList<Long>(set);
		
		//合并阳性发现
		String diseaseMergeSql = "select d.before_disease_id,d.later_disease_id from disease_merge d";
		List<DiseaseMergeDTO> mergeList = this.jqm.getList(diseaseMergeSql, DiseaseMergeDTO.class);
		
		for(DiseaseMergeDTO mergeDto : mergeList){
			String[] beforeId = mergeDto.getBefore_disease_id().split(",");
			
			int count = 0;
			List<Long> temp = new ArrayList<Long>();
			for (int i = 0; i < beforeId.length; i++) {
				for(Long diseaId : list){
					if(beforeId[i].equals(diseaId.toString())){
						count++;
						temp.add(diseaId);
					}
				}
			}
			
			if(count != 0 && count == beforeId.length){
				list.removeAll(temp);//移除合并的疾病
				list.add(mergeDto.getLater_disease_id());
			}
		}
		
		Long[] arr = (Long[]) list.toArray(new Long[list.size()]);
		String diseaseIds = StringUtils.join(arr, ",");
		
		String diseaseSql = "select * from (select d.id as disease_id,d.disease_name,d.disease_type,d.icd_10,d.disease_num,0 as data_source,v.dep_seq,v.item_seq,v.seq_code,"
				+ "ROW_NUMBER() over(partition by d.id order by v.dep_seq,v.item_seq,v.seq_code) AS rn from disease_knowloedge_lib d,"
				+ "disease_logic_single l,v_exam_result_new v where d.id in ("+diseaseIds+") and d.disease_num = l.disease_num "
				+ "and l.logic_class = 0 and l.item_code = v.item_num and l.isActive = 'Y' and v.exam_num = '"+exam_num+"'"
				+"union all "
				+ "select d.id as disease_id,d.disease_name,d.disease_type,d.icd_10,d.disease_num,0 as data_source,v.dep_seq,v.item_seq,v.seq_code,"
				+ "ROW_NUMBER() over(partition by d.id order by v.dep_seq,v.item_seq,v.seq_code) AS rn from disease_knowloedge_lib d,"
				+ "disease_logic_single l,v_exam_result_new v where d.id in ("+diseaseIds+") and d.disease_num = l.disease_num "
				+ "and l.logic_class = 2 and l.item_code = v.item_code and l.isActive = 'Y' and v.exam_num = '"+exam_num+"'"
				+ ") as u where u.rn = 1 order by u.dep_seq,u.item_seq,u.seq_code";
		List<ExaminfoDiseaseDTO> diseaseList = this.jqm.getList(diseaseSql, ExaminfoDiseaseDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表", diseaseSql);
		int dis_count = 0;
		for(ExaminfoDiseaseDTO disese : diseaseList){
			
			String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
						  + " where d.disease_id = "+ disese.getDisease_id() +" and d.is_active = 'Y'"
						  + " and d.minAge <= "+examInfo.getAge()+" and d.maxAge >= "+examInfo.getAge()
						  + " and (d.sex = '"+examInfo.getSex()+"' or d.sex = '全部')";
			if("Y".equals(sug_center)){
				sugsql += " and d.center_num = '"+center_num+"'";
			}
			List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
			disese.setDisease_index(dis_count);
			if(sugList.size() == 1){
				if("Y".equals(isExamSuggest)){
					disese.setSuggest(disese.getDisease_name() +"： "+sugList.get(0).getDisease_suggestion());
				}else{
					disese.setSuggest(sugList.get(0).getDisease_suggestion());
				}
			}else{
				for(DiseaseKnowloedgeDTO dkLib : sugList){
					if("是".equals(dkLib.getDefault_value())){
						if("Y".equals(isExamSuggest)){
							disese.setSuggest(disese.getDisease_name() +"： "+dkLib.getDisease_suggestion());
						}else{
							disese.setSuggest(dkLib.getDisease_suggestion());
						}
					}
				}
			}
			String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="+disese.getDisease_id();
			List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
			if(descList.size() > 0){
				disese.setDisease_description(descList.get(0).getDisease_description());
			}
			dis_count ++;
		}
		return diseaseList;
	}
	
	public boolean convertLogic(DepExamResultDTO resultDto2,DiseaseLogicExamItemDTO logicItem){
		String con = logicItem.getCondition();// 当前疾病逻辑的当前条件关系
		// =======条件判断
		if (con.trim().equals("=")) {
			if (logicItem.getCondition_value().equals(resultDto2.getExam_result())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals(">")) {
			if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) > Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals(">=")) {
			if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) >= Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("<")) {
			if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
				return false;
			}
			if (Double.parseDouble(resultDto2.getExam_result()) < Double.parseDouble(logicItem.getCondition_value())) {
				return true;
			}
		}

		// =======条件判断
		if (con.trim().equals("<=")) {
			if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
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
	public String createFinalExamResult(String exam_num,String isExamSummary,String isShowRefvalue,String isShowSamplename,String examResultShowItemId,String examSummaryResultIn,String app_type, String center_num) throws ServiceException {
		String app1 = "";
		String app2 = "";
		String app3 = "";
		if("2".equals(app_type)){
			app1 = " and ed.app_type = '"+app_type+"'";
			app2 = " and ec.app_type='"+app_type+"'";
			app3 = " and eci.app_type='"+app_type+"'";
		}
		String examSummaryStr = "";
		String comSql = " select ed.exam_result_summary as exam_result,dd.dep_name from exam_dep_result ed,department_dep dd"
				      + " where ed.dep_id = dd.id and ed.exam_num ='"+exam_num+"' "
				      + app1 +" order by dd.seq_code";
		
		List<DepExamResultDTO> comList = this.jqm.getList(comSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-普通", comSql);
		for(DepExamResultDTO cDto : comList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += cDto.getDep_name() + ":";
				String kouge = " ";
				String[] examstr = cDto.getExam_result().split("\r\n");

				for (int j = 0; j < examstr.length; j++) {
					if (j == 0) {
						examSummaryStr += examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
					} else {
						int zongshu = StringUtil.getStrLength(cDto.getDep_name()) + 1;
						String zongkongge = "";
						for (int k = 0; k < zongshu; k++) {
							zongkongge += kouge;
						}
						examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
					}
				}
				examSummaryStr += "\n";
			}
		}
		
		String depName = "c.item_name";
		if("Y".equals(isShowSamplename)){
			depName = "s.demo_name";
		}
		String viewSql =" select distinct v.exam_result,"+depName+" as dep_name,dd.seq_code from view_exam_detail v,exam_info e,pacs_summary p,charging_item c,examinfo_charging_item ec,"
					   +" department_dep dd,sample_demo s where s.id = c.sam_demo_id and ec.charging_item_code = c.item_code "
					   + "and e.exam_num = ec.exam_num and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' " 
					   +" and v.exam_num = e.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = e.exam_num and dd.id = c.dep_id"
					   +" and p.examinfo_sampleId = c.sam_demo_id and e.exam_num = '"+exam_num+"' "+app2+" order by dd.seq_code";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检查", viewSql);
		for(DepExamResultDTO cDto : viewList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += cDto.getDep_name() + ":";
				String kouge = " ";
				String[] examstr = cDto.getExam_result().split("\r\n");
				List<String> tmp = new ArrayList<String>();
		        for(String str:examstr){
		            if(str!=null && str.length()!=0){
		                tmp.add(str);
		            }
		        }
				for (int j = 0; j < tmp.size(); j++) {
					if (j == 0) {
						examSummaryStr += tmp.get(j).replace("\n", "").replace("\r", "") + "\r\n";
					} else {
						int zongshu = StringUtil.getStrLength(cDto.getDep_name()) + 1;
						String zongkongge = "";
						for (int k = 0; k < zongshu; k++) {
							zongkongge += kouge;
						}
						examSummaryStr += zongkongge + tmp.get(j).replace("\n", "").replace("\r", "") + "\r\n";
					}
				}
				examSummaryStr += "\n";
			}
		}
		
		String examSql = "select distinct c.item_name as dep_name,ei.item_name,ei.id as item_id,er.exam_result,er.item_unit,er.ref_indicator as health_level,c.item_seq,ei.seq_code,er.ref_value from exam_result_detail er,"
			           + "exam_info e,examination_item ei,charging_item c,charging_item_exam_item ci,examinfo_charging_item eci "
			           + "where er.exam_num = e.exam_num and er.item_code = ei.item_num and e.exam_num = '"+exam_num+"' " 
			           + "and ei.item_num = ci.item_code and ci.charging_item_code = c.item_code and eci.isActive ='Y' and eci.pay_status <> 'M' and eci.center_num = '"+center_num+"' "
			           + "and eci.charging_item_code = c.item_code and eci.exam_num = e.exam_num "+app3+" order by c.item_seq,ei.seq_code ";
		List<DepExamResultDTO> examList = this.jqm.getList(examSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检验", examSql);
		Map<String,String> itemidMap = new HashMap<String,String>();
		if(examResultShowItemId != null && !"".equals(examResultShowItemId)){
			String[] itemids = examResultShowItemId.split(",");
			for (int i = 0; i < itemids.length; i++) {
				itemidMap.put(itemids[i], itemids[i]);
			}
		}
		String dep_name = "";
		for(DepExamResultDTO cDto : examList){
			if(!dep_name.equals(cDto.getDep_name())){
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += cDto.getDep_name() +": "+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  ↑ ";
					dep_name = cDto.getDep_name();
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += cDto.getDep_name() +": "+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  ↓ ";
					dep_name = cDto.getDep_name();
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += cDto.getDep_name() +": "+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					dep_name = cDto.getDep_name();
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += cDto.getDep_name() +": "+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  危机 ";
					dep_name = cDto.getDep_name();
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += cDto.getDep_name() +": "+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						dep_name = cDto.getDep_name();
					}
				}
			}else{
				String kouge = " ";
				int zongshu = StringUtil.getStrLength(cDto.getDep_name()) + 2;
				String zongkongge = "";
				for (int k = 0; k < zongshu; k++) {
					zongkongge += kouge;
				}
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  ↑ ";
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  ↓ ";
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  ";
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")  危机 ";
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge+cDto.getItem_name() + ": (" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}
				}
			}
			if(!"0".equals(cDto.getHealth_level()) || itemidMap.get(cDto.getItem_id()+"") != null){
				if("Y".equals(isShowRefvalue)){
					examSummaryStr += " 参考值:"+cDto.getRef_value() + "\n\n";
				}else{
					examSummaryStr += "\n\n";
				}
			}
		}
		
		return examSummaryStr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String createFinalExamResultZY2(String exam_num,String isExamSummary,String isShowRefvalue,String isShowSamplename,String examResultShowItemId,String examSummaryResultIn,String app_type, String center_num) throws ServiceException {
		String app1 = "";
		String app2 = "";
		String app3 = "";
		if("2".equals(app_type)){
			app1 = " and ed.app_type = '"+app_type+"'";
			app2 = " and ec.app_type='"+app_type+"'";
			app3 = " and eci.app_type'"+app_type+"'";
		}
		String examSummaryStr = "";
		String comSql = " select ed.exam_result_summary as exam_result,dd.dep_name,dd.dep_num from exam_dep_result ed,department_dep dd"
				      + " where ed.dep_id = dd.id and ed.exam_num ='"+exam_num+"' "
				      + app1 +" order by dd.seq_code";
		
		List<DepExamResultDTO> comList = this.jqm.getList(comSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-普通", comSql);
		int count = 1;
		String kouge = " ";
		int zongshu = StringUtil.getStrLength("1.一般");
		String zongkongge = "";
		for (int k = 0; k < zongshu; k++) {
			zongkongge += kouge;
		}
		for(DepExamResultDTO cDto : comList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				if("007".equals(cDto.getDep_num())){
					List<String> list = new ArrayList<String>();
					boolean xyshow = false;
					for (int j = 0; j < examstr.length; j++) {
						if(examstr[j].indexOf("收缩压") != -1 || examstr[j].indexOf("舒张压") != -1){
							if(!xyshow){
								String str = "(1)血压:";
								String sql = " select cd.exam_result from common_exam_detail cd where "
										   + " cd.item_code = 'WL004' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> ssy = this.jqm.getList(sql, DepExamResultDTO.class);
								if(ssy.size() != 0){
									str += ssy.get(0).getExam_result();
								}
								String sql1 = " select cd.exam_result from common_exam_detail cd where "
										   + " cd.item_code = 'WL005' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> szy = this.jqm.getList(sql1, DepExamResultDTO.class);
								if(szy.size() != 0){
									str += "/"+szy.get(0).getExam_result()+"mmHg";
								}
								list.add(str);
								xyshow = true;
							}
						}else{
							list.add(examstr[j]);
						}
					}
					String reg = "\\(\\d{1,2}\\)";
					Pattern r = Pattern.compile(reg);
					for(int j=0;j<list.size();j++){
						Matcher m = r.matcher(list.get(j));
						examSummaryStr += zongkongge + m.replaceAll("("+(j+1)+")") + "\r\n";
					}
				}else{
					for (int j = 0; j < examstr.length; j++) {
						examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
					}
				}
				count ++;
			}
		}
		
		String depName = "c.item_name";
		if("Y".equals(isShowSamplename)){
			depName = "s.demo_name";
		}
		String viewSql =" select distinct v.exam_result,"+depName+" as dep_name,dd.seq_code from view_exam_detail v,exam_info e,pacs_summary p,charging_item c,examinfo_charging_item ec,"
					   +" department_dep dd,sample_demo s where s.id = c.sam_demo_id and ec.charging_item_code = c.item_code and e.exam_num = ec.exam_num "
					   + "and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' " 
					   +" and v.exam_num = e.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = e.exam_num and dd.id = c.dep_id"
					   +" and p.examinfo_sampleId = c.sam_demo_id and e.exam_num = '"+exam_num+"' "+app2+" order by dd.seq_code";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检查", viewSql);
		for(DepExamResultDTO cDto : viewList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr +=count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				for (int j = 0; j < examstr.length; j++) {
					if("".equals(examstr[j].replace("\n", "").replace("\r", "").replaceAll(" ", ""))){
						continue;
					}
					examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "").trim() + "\r\n";
				}
				count ++;
			}
		}
		
		String examSql = "select distinct c.item_name as dep_name,ei.item_name,ei.id as item_id,er.exam_result,er.item_unit,er.ref_indicator as health_level,"
				+ "c.item_seq,ei.seq_code,er.ref_value from exam_result_detail er,"
					    +"exam_info e,examination_item ei,charging_item c,charging_item_exam_item ci,examinfo_charging_item eci "
					    +"where er.exam_num = e.exam_num and er.item_code = ei.item_num and e.exam_num = '"+exam_num+"' " 
					    +"and ei.item_num = ci.item_code and ci.charging_item_code = c.item_code and eci.isActive ='Y' and eci.pay_status <> 'M' "
					    + "and eci.center_num = '"+center_num+"' "
					    +"and eci.charging_item_code = c.item_code and eci.exam_num = e.exam_num "+app3+" order by c.item_seq,ei.seq_code ";
		List<DepExamResultDTO> examList = this.jqm.getList(examSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检验", examSql);
		Map<String,String> itemidMap = new HashMap<String,String>();
		if(examResultShowItemId != null && !"".equals(examResultShowItemId)){
			String[] itemids = examResultShowItemId.split(",");
			for (int i = 0; i < itemids.length; i++) {
				itemidMap.put(itemids[i], itemids[i]);
			}
		}
		
		int maxLength = 0;
		for(DepExamResultDTO cDto : examList){
			String str = "";
			if("1".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
			}else if("2".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
			}else if("3".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
			}else if("4".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
			}else{
				if(itemidMap.get(cDto.getItem_id()+"") != null){
					str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}
			}
			if(maxLength < StringUtil.getStrLength(str)){
				maxLength = StringUtil.getStrLength(str);
			}
		}
		if(maxLength == 0){
			return examSummaryStr;
		}
		examSummaryStr +=count+"." + "检验结果:\n";
		
		String dep_name = "";
		for(DepExamResultDTO cDto : examList){
			
			if(!dep_name.equals(cDto.getDep_name())){
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					dep_name = cDto.getDep_name();
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					dep_name = cDto.getDep_name();
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					dep_name = cDto.getDep_name();
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					dep_name = cDto.getDep_name();
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						dep_name = cDto.getDep_name();
					}
				}
			}else{
				
				int zongshu1 = zongshu + 2;
				String zongkongge1 = "";
				for (int k = 0; k < zongshu1; k++) {
					zongkongge1 += kouge;
				}
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}
				}
			}
			if(!"0".equals(cDto.getHealth_level()) || itemidMap.get(cDto.getItem_id()+"") != null){
				if("Y".equals(isShowRefvalue)){
					String str = "";
					if("1".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					}else if("2".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					}else if("3".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}else if("4".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					}else{
						if(itemidMap.get(cDto.getItem_id()+"") != null){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						}
					}
					int zongshu1 = maxLength - StringUtil.getStrLength(str);
					String zongkongge1 = "";
					String cankaozhi = "";
					String maxstr = "";
					for (int k = 0; k < zongshu1; k++) {
						zongkongge1 += kouge;
					}
					for (int k = 0; k < StringUtil.getStrLength("参考值:  "); k++) {
						cankaozhi += kouge;
					}
					for (int k = 0; k < maxLength; k++) {
						maxstr += kouge;
					}
					
					String[] refs = cDto.getRef_value().split("\n");
					for (int i = 0; i < refs.length; i++) {
						if(i == 0){
							examSummaryStr += zongkongge1 + "参考值:"+refs[i] + "\n";
						}else{
							examSummaryStr += zongkongge+maxstr + cankaozhi+refs[i] + "\n";
						}
					}
				}else{
					examSummaryStr += "\n";
				}
			}
		}
		
		return examSummaryStr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String createFinalExamResultJGYY(String exam_num,String isExamSummary,String isShowRefvalue,String isShowSamplename,String examResultShowItemId,String examSummaryResultIn,String app_type, String center_num) throws ServiceException {
		String app1 = "";
		String app2 = "";
		String app3 = "";
		if("2".equals(app_type)){
			app1 = " and ed.app_type = '"+app_type+"'";
			app2 = " and ec.app_type='"+app_type+"'";
			app3 = " and eci.app_type='"+app_type+"'";
		}
		String examSummaryStr = "";
		String comSql = " select ed.exam_result_summary as exam_result,dd.dep_name,dd.dep_num from exam_dep_result ed,department_dep dd"
			          + " where ed.dep_id = dd.id and ed.exam_num ='"+exam_num+"' "
			          + app1 +" order by dd.seq_code";
		
		List<DepExamResultDTO> comList = this.jqm.getList(comSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-普通", comSql);
		int count = 1;
		String kouge = " ";
		int zongshu = StringUtil.getStrLength("1.一般");
		String zongkongge = "";
		for (int k = 0; k < zongshu; k++) {
			zongkongge += kouge;
		}
		for(DepExamResultDTO cDto : comList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				if("007".equals(cDto.getDep_num())){
					List<String> list = new ArrayList<String>();
					boolean xyshow = false;
					for (int j = 0; j < examstr.length; j++) {
						if(examstr[j].indexOf("收缩压") != -1 || examstr[j].indexOf("舒张压") != -1){
							if(!xyshow){
								String str = "(1)血压:";
								String sql = " select cd.exam_result from common_exam_detail cd where "
										   + " cd.item_code = 'WL004' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> ssy = this.jqm.getList(sql, DepExamResultDTO.class);
								if(ssy.size() != 0){
									str += ssy.get(0).getExam_result();
								}
								String sql1 = " select cd.exam_result from common_exam_detail cd where "
										   + " cd.item_code = 'WL005' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> szy = this.jqm.getList(sql1, DepExamResultDTO.class);
								if(szy.size() != 0){
									str += "/"+szy.get(0).getExam_result()+"mmHg";
								}
								list.add(str);
								xyshow = true;
							}
						}else{
							list.add(examstr[j]);
						}
					}
					String reg = "\\(\\d{1,2}\\)";
					Pattern r = Pattern.compile(reg);
					for(int j=0;j<list.size();j++){
						Matcher m = r.matcher(list.get(j));
						examSummaryStr += zongkongge + m.replaceAll("("+(j+1)+")") + "\r\n";
						if(j == list.size()-1){
							examSummaryStr += "\n";
						}
					}
				}else{
					for (int j = 0; j < examstr.length; j++) {
						examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
						if(j == examstr.length-1){
							examSummaryStr += "\n";
						}
					}
				}
				count ++;
			}
		}
		
		String depName = "c.item_name";
		if("Y".equals(isShowSamplename)){
			depName = "s.demo_name";
		}
		String viewSql =" select distinct v.exam_result,"+depName+" as dep_name,dd.seq_code from view_exam_detail v,exam_info e,pacs_summary p,charging_item c,examinfo_charging_item ec,"
					   +" department_dep dd,sample_demo s where s.id = c.sam_demo_id and ec.charging_item_code = c.item_code and e.exam_num = ec.exam_num and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' " 
					   +" and v.exam_num = e.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = e.exam_num and dd.id = c.dep_id"
					   +" and p.examinfo_sampleId = c.sam_demo_id and e.exam_num = '"+exam_num+"' "+app2+" order by dd.seq_code";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检查", viewSql);
		for(DepExamResultDTO cDto : viewList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr +=count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				for (int j = 0; j < examstr.length; j++) {
					if("".equals(examstr[j].replace("\n", "").replace("\r", "").replaceAll(" ", ""))){
						continue;
					}
					examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "").trim() + "\r\n";
					if(j == examstr.length-1){
						examSummaryStr += "\n";
					}
				}
				count ++;
			}
		}
		
		String examSql = "select distinct c.item_name as dep_name,ei.item_name,ei.id as item_id,er.exam_result,er.item_unit,er.ref_indicator as health_level,c.item_seq,ei.seq_code,er.ref_value from exam_result_detail er,"
					    +"exam_info e,examination_item ei,charging_item c,charging_item_exam_item ci,examinfo_charging_item eci "
					    +"where er.exam_num = e.exam_num and er.item_code = ei.item_num and e.exam_num = '"+exam_num+"' " 
					    +"and ei.item_num = ci.item_code and ci.charging_item_code = c.item_code and eci.isActive ='Y' and eci.pay_status <> 'M' and eci.center_num = '"+center_num+"' "
					    +"and eci.charging_item_code = c.item_code and eci.exam_num = e.exam_num "+app3+" order by c.item_seq,ei.seq_code ";
		List<DepExamResultDTO> examList = this.jqm.getList(examSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检验", examSql);
		Map<String,String> itemidMap = new HashMap<String,String>();
		if(examResultShowItemId != null && !"".equals(examResultShowItemId)){
			String[] itemids = examResultShowItemId.split(",");
			for (int i = 0; i < itemids.length; i++) {
				itemidMap.put(itemids[i], itemids[i]);
			}
		}
		
		int maxLength = 0;
		for(DepExamResultDTO cDto : examList){
			String str = "";
			if("1".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
			}else if("2".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
			}else if("3".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
			}else if("4".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
			}else{
				if(itemidMap.get(cDto.getItem_id()+"") != null){
					str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}
			}
			if(maxLength < StringUtil.getStrLength(str)){
				maxLength = StringUtil.getStrLength(str);
			}
		}
		if(maxLength == 0){
			return examSummaryStr;
		}
		examSummaryStr +=count+"." + "检验结果:\n";
		
		String dep_name = "";
		for(DepExamResultDTO cDto : examList){
			
			if(!dep_name.equals(cDto.getDep_name())){
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					dep_name = cDto.getDep_name();
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					dep_name = cDto.getDep_name();
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					dep_name = cDto.getDep_name();
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					dep_name = cDto.getDep_name();
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						dep_name = cDto.getDep_name();
					}
				}
			}else{
				
				int zongshu1 = zongshu + 2;
				String zongkongge1 = "";
				for (int k = 0; k < zongshu1; k++) {
					zongkongge1 += kouge;
				}
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}
				}
			}
			if(!"0".equals(cDto.getHealth_level()) || itemidMap.get(cDto.getItem_id()+"") != null){
				if("Y".equals(isShowRefvalue)){
					String str = "";
					if("1".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					}else if("2".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					}else if("3".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}else if("4".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					}else{
						if(itemidMap.get(cDto.getItem_id()+"") != null){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						}
					}
					int zongshu1 = maxLength - StringUtil.getStrLength(str);
					String zongkongge1 = "";
					String cankaozhi = "";
					String maxstr = "";
					for (int k = 0; k < zongshu1; k++) {
						zongkongge1 += kouge;
					}
					for (int k = 0; k < StringUtil.getStrLength("参考值:  "); k++) {
						cankaozhi += kouge;
					}
					for (int k = 0; k < maxLength; k++) {
						maxstr += kouge;
					}
					
					String[] refs = cDto.getRef_value().split("\n");
					for (int i = 0; i < refs.length; i++) {
						if(i == 0){
							examSummaryStr += zongkongge1 + "参考值:"+refs[i] + "\n";
						}else{
							examSummaryStr += zongkongge+maxstr + cankaozhi+refs[i] + "\n";
						}
					}
				}else{
					examSummaryStr += "\n";
				}
			}
		}
		
		return examSummaryStr;
	}
	
	/**
	 * 验证结论是否包含正常值
	 */

	public static boolean isResultNormal(String result, String isExamSummary, String depNum,String examSummaryResultIn) {
		String[] tiaojian = examSummaryResultIn.split(",");
		if ("N".equals(isExamSummary)) {
			if ("CT".equals(depNum) || "HC".equals(depNum) || "FS".equals(depNum)) {
				return false;
			} else if (result != null) {
				if(result.indexOf("正常") != -1 || result.indexOf("未见") != -1 || result.indexOf("阴性") != -1){
					for (int i = 0; i < tiaojian.length; i++) {
						if(!"".equals(tiaojian[i]) && result.indexOf(tiaojian[i]) != -1){
							return false;
						}
					}
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExamSummaryDTO getFinalExamResult(String exam_num,String app_type,UserDTO user) throws ServiceException {
		String sql = "select es.final_exam_result,es.approve_status,es.id,es.exam_guidance,"
				+ " es.censoring_status,es.report_class,u.chi_name report_class_user,es.report_class_date,u1.chi_name exam_doctor,"
				+ " u2.chi_name app_doctor,es.check_time,e.final_date,c.t,es.cancel_type, "
				+ " case when e.apptype = '2' then e.zyb_final_status else e.status end as exam_status,"
				+ " case when e.apptype = '2' then e.status else e.zyb_final_status end as zyb_final_status"
				+ " from exam_info e "
				+ " left join exam_summary es on e.exam_num = es.exam_num and es.app_type ='"+app_type+"'"
				+ " left join user_usr u on u.id=es.report_class_user"
				+ " left join user_usr u1 on u1.id = es.exam_doctor_id"
				+ " left join user_usr u2 on u2.id = es.check_doc"
				+ " left join exam_flow_config c on e.exam_num = c.exam_num"
				+ " where e.exam_num = '"+exam_num+"'  AND  e.center_num='"+user.getCenter_num()+"'";
		List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiseaseKnowloedgeDTO> serchDiseaseList(String disease_name) throws ServiceException {
		String sql = "select d.disease_name,d.id,d.disease_num from disease_knowloedge_lib d where d.isActive = 'Y'"
				+" and (d.disease_name like '%"+disease_name+"%' or d.disease_pinyin like '%"+disease_name+"%')";
		List<DiseaseKnowloedgeDTO> list = this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoDiseaseDTO> serchDiseaseSuggestionList(long disease_id, long age, String sex,String center_num,String sug_center)
			throws ServiceException {
		String sugsql = " select ds.disease_id,dk.disease_name,dk.disease_type,ds.disease_suggestion as suggest,dk.icd_10,dk.disease_num,dk.disease_description "
				      + " from disease_suggestion_lib ds,disease_knowloedge_lib dk"
				      + " where ds.disease_id = dk.id "
				      + " and ds.disease_id = "+ disease_id +" and ds.is_active = 'Y'"
				      + " and ds.minAge <= "+age+" and ds.maxAge >= "+age
				      + " and (ds.sex = '"+sex+"' or ds.sex = '全部')";
		if("Y".equals(sug_center)){
			sugsql += " and ds.center_num = '"+center_num+"'";
		}
		List<ExaminfoDiseaseDTO> sugList = this.jqm.getList(sugsql, ExaminfoDiseaseDTO.class);
		return sugList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveExamSummary(ExamSummaryModel model,ExamSummary examSummary,UserDTO user,String flag) throws ServiceException {
		
		ExamInfo examInfo = this.getExamInfoByExamNum(model.getExam_num());
		if(examInfo == null){
			return "error-该体检信息不存在";
		}
		if("Y".equals(flag)){//职业病健康总检 修改 zyb_final_status 总检状态
			if(examInfo.getApptype().equals("2")){
				examInfo.setZyb_final_status("Z");
				examInfo.setZyb_final_doctor(user.getName());
				examInfo.setZyb_final_time(DateTimeUtil.parse());
			}else{  //健康体检修改 status 总检状态
				examInfo.setStatus("Z");
				examInfo.setFinal_doctor(user.getName());
				examInfo.setFinal_date(DateTimeUtil.getDateTime());
			}
			examInfo.setUpdate_time(DateTimeUtil.getDateTime());
			examInfo.setUpdater(user.getUserid());
			this.pm.update(examInfo);
			
			List<ExamSummaryReject> list = this.qm.find("from ExamSummaryReject e where e.done_status = 0 and e.examinfo_id = " + examInfo.getId());
			if(list.size() > 0){
				ExamSummaryReject examSummaryReject = list.get(0);
				examSummaryReject.setDone_status(1);
				examSummaryReject.setDone_time(DateTimeUtil.parse());
				examSummaryReject.setDone_doctor(user.getUserid());
				this.pm.update(examSummaryReject);
			}
		}
		List<ExaminfoDiseaseDTO> list = model.getExaminfoDisease();
		List<ExaminfoDiseaseDTO> dxlist = model.getDxexaminfoDiseases();
		if(examSummary == null){
			examSummary = new ExamSummary();
			examSummary.setExam_doctor_id(user.getUserid());
			examSummary.setExam_info_id(examInfo.getId());
			examSummary.setExam_num(model.getExam_num());
			examSummary.setFinal_exam_result(model.getFinal_exam_result());
			examSummary.setResult_D("");
			examSummary.setResult_Y("");
			examSummary.setSuggest("");
			examSummary.setCenter_num(user.getCenter_num());
			examSummary.setApprove_status("B");
			examSummary.setCreater(user.getUserid());
			examSummary.setCreate_time(DateTimeUtil.parse());
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			examSummary.setRead_status(0);
			examSummary.setRead_time(DateTimeUtil.parse());
			examSummary.setExam_guidance(model.getExam_guidance());
			examSummary.setCensoring_status("0");
			examSummary.setFinal_worknum(list.size());
			examSummary.setApp_type(model.getApp_type());
			if("Y".equals(flag)){
				examSummary.setFinal_status("Z");
				examSummary.setFinal_time(DateTimeUtil.parse());
                examSummary.setRead_status1(0);
                examSummary.setRead_status2(0);
                examSummary.setRead_status3(0);
			}else{
				examSummary.setFinal_status("J");
			}
			this.pm.save(examSummary);
		}else{
			if(!"".equals(flag)){
				examSummary.setExam_doctor_id(user.getUserid());
			}
//			examSummary.setExam_info_id(examInfo.getId());
			examSummary.setFinal_exam_result(model.getFinal_exam_result());
//			examSummary.setCenter_num(user.getCenter_num());
			examSummary.setRead_status(0);
			examSummary.setApprove_status("B");
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			examSummary.setExam_guidance(model.getExam_guidance());
			examSummary.setFinal_worknum(list.size());
			if("Y".equals(flag)){
				examSummary.setFinal_status("Z");
				examSummary.setFinal_time(DateTimeUtil.parse());
                examSummary.setRead_status1(0);
                examSummary.setRead_status2(0);
                examSummary.setRead_status3(0);
			}
			this.pm.update(examSummary);
		}
		
		ExamSummaryLog summarylog = null;
		if("Y".equals(flag)){
			summarylog = new ExamSummaryLog();
			summarylog.setExam_doctor_id(user.getUserid());
			summarylog.setExam_info_id(examInfo.getId());
			summarylog.setExam_date(DateTimeUtil.parse());
			summarylog.setFinal_exam_result(model.getFinal_exam_result());
			summarylog.setCreater(user.getUserid());
			summarylog.setCreate_time(DateTimeUtil.parse());
			summarylog.setOperation_type(1);
			summarylog.setExam_num(model.getExam_num());
			
			this.pm.save(summarylog);
		}
		
		List<ExaminfoDisease> oldDis = this.qm.find("from ExaminfoDisease e where e.exam_num = '"+examInfo.getExam_num()+"' and e.app_type='"+model.getApp_type()+"'");
		for(ExaminfoDisease examinfoDisease : oldDis){
			this.pm.remove(examinfoDisease);
		}
		
		//单项疾病
				List<ExaminfoDiseaseSingle> disList = this.qm.find("from ExaminfoDiseaseSingle e where e.exam_num = '"+examInfo.getExam_num()+"'");
				for(ExaminfoDiseaseSingle examinfoDisease : disList){
					this.pm.remove(examinfoDisease);
				}
				Set<String> disnum = new HashSet<>();
				for(ExaminfoDiseaseDTO disDto : list){
					disnum.add(disDto.getDisease_num());
				}
				
				if (dxlist.size() > 0) {
					for (ExaminfoDiseaseDTO disDto : dxlist) {
						if (disDto != null) {
							//单项阳性结果是否保存
							if (!"".equals(model.getIs_save_dx_disease()) && "Y".equals(model.getIs_save_dx_disease())) {
								if (!disnum.contains(disDto.getDisease_num())) {
									list.add(disDto);
								}
							}
							
							ExaminfoDiseaseSingle diseaseSingle = new ExaminfoDiseaseSingle();
							diseaseSingle.setCharging_item_code(disDto.getCharging_item_code());
							diseaseSingle.setItem_code(disDto.getItem_code());
							diseaseSingle.setExam_num(examInfo.getExam_num());
							diseaseSingle.setDep_num(disDto.getDep_num());
							diseaseSingle.setDisease_num(disDto.getDisease_num());
							diseaseSingle.setDisease_name(disDto.getDisease_name());
							diseaseSingle.setCreater(user.getUserid());
							diseaseSingle.setCreate_time(DateTimeUtil.parse());
							this.pm.save(diseaseSingle);
						}
					}
				}
		
		int count = 1;
		for(ExaminfoDiseaseDTO disDto : list){
			ExaminfoDisease  examinfoDisease = new ExaminfoDisease();
			
			examinfoDisease.setExam_info_id(examInfo.getId());
			examinfoDisease.setDisease_id(disDto.getDisease_id());
			examinfoDisease.setDisease_name(disDto.getDisease_name());
			examinfoDisease.setDisease_index(count);
			examinfoDisease.setIsActive("Y");
			examinfoDisease.setCreater(user.getUserid());
			examinfoDisease.setCreate_time(DateTimeUtil.parse());
			examinfoDisease.setUpdater(user.getUserid());
			examinfoDisease.setUpdate_time(DateTimeUtil.parse());
			examinfoDisease.setDisease_type(disDto.getDisease_type());
			examinfoDisease.setIcd_10(disDto.getIcd_10());
			examinfoDisease.setFinal_doc_num(user.getName());
			examinfoDisease.setSuggest(disDto.getSuggest());
			examinfoDisease.setDisease_class(disDto.getDisease_class());
			examinfoDisease.setApp_type(model.getApp_type());
			examinfoDisease.setDisease_description(disDto.getDs());
			examinfoDisease.setDisease_num(disDto.getDisease_num());
			examinfoDisease.setData_source(disDto.getData_source());
//			examinfoDisease.setDiagnosis_source(disDto.getDiagnosis_source());
//			examinfoDisease.setItem_source(disDto.getItem_source());
//			examinfoDisease.setExam_result(disDto.getExam_result());
//			examinfoDisease.setCareer_hazards(disDto.getCareer_hazards());
//			examinfoDisease.setCareer_suggest(disDto.getCareer_suggest());
//			examinfoDisease.setResultID(disDto.getResultID());
//			examinfoDisease.setOccudiseaseIDorcontraindicationID(disDto.getOccudiseaseIDorcontraindicationID());
			examinfoDisease.setExam_num(model.getExam_num());
			this.pm.save(examinfoDisease);
			if("Y".equals(flag)){
				ExaminfoDiseaseLog diseaselog = new ExaminfoDiseaseLog();
				diseaselog.setSummary_id(summarylog.getId());
				diseaselog.setDisease_id(disDto.getDisease_id());
				diseaselog.setDisease_name(disDto.getDisease_name());
				diseaselog.setDisease_index(count);
				diseaselog.setCreater(user.getUserid());
				diseaselog.setCreate_time(DateTimeUtil.parse());
				diseaselog.setDisease_type(disDto.getDisease_type());
				diseaselog.setIcd_10(disDto.getIcd_10());
				diseaselog.setSuggest(disDto.getSuggest());
				diseaselog.setDisease_class(disDto.getDisease_class());
				diseaselog.setDisease_num(disDto.getDisease_num());
//				diseaselog.setRemarke(disDto.getFinal_remarke());
				this.pm.save(diseaselog);
			}
			count ++;
		}
		List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+model.getExam_num()+"'");
		if(configlist.size()>0 && "Y".equals(flag)){
			ExamFlowConfig config = configlist.get(0);
			config.setZ1(1);
			config.setZ1creater(user.getUserid());
			config.setZ1date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			
			log.setExam_num(config.getExam_num());
			log.setFlow_code("z1");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			
			this.pm.save(log);
		}
        String val = this.customerInfoService.getCenterconfigByKey("INSERT_report_pdf", user.getCenter_num()).getConfig_value().trim();
        if("ZJ".equals(val) || "ZJ_SH".equals(val)) {
            List<CustomerInfo> cli = this.qm.find("from CustomerInfo  where id =" + examInfo.getCustomer_id());
            ReportPdf r = new ReportPdf();
            r.setApptype(model.getApp_type());
            r.setExam_num(examInfo.getExam_num());
            r.setArch_num(cli.get(0).getArch_num());
            r.setReport_year(Integer.parseInt(examInfo.getJoin_date().substring(0, 4)));
            r.setIs_finished(1);
            r.setCreate_time(DateTimeUtil.parse());
            this.pm.save(r);
        }
		return "ok-保存总检成功!";
	}
	@Override
	public ExamInfo updateExamInfo(ExamInfo examinfo) throws ServiceException {
		this.pm.update(examinfo);
		return examinfo;
	}

	@Override
	public ExamSummary updateExamSummary(ExamSummary examSummary) throws ServiceException {
		this.pm.update(examSummary);
		return examSummary;
	}

    @Override
    public void saveReportPdf(ReportPdfDTO rp,UserDTO user,String app_type) throws ServiceException {
        String val = this.customerInfoService.getCenterconfigByKey("INSERT_report_pdf", user.getCenter_num()).getConfig_value().trim();
        if( "ZJ_SH".equals(val) ) {
            ReportPdf r = new ReportPdf();
            r.setApptype(app_type);
            r.setExam_num(rp.getExam_num());
            r.setArch_num(rp.getArch_num());
            r.setReport_year(rp.getReport_year());
            r.setIs_finished(1);
            r.setCreate_time(DateTimeUtil.parse());
            this.pm.save(r);
        }
    }

    @Override
	public ExamSummary getExamSummaryById(String exam_num,String app_type) throws ServiceException {
//		ExamSummary examSummary = (ExamSummary) this.qm.load(ExamSummary.class, id);
		List<ExamSummary> list = this.qm.find("from ExamSummary e where e.exam_num= '"+exam_num+"' and e.app_type = '"+app_type+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ExamInfo getExamInfoByExamNum(String exam_num) throws ServiceException {
		List<ExamInfo> list = this.qm.find("from ExamInfo e where e.exam_num = '"+exam_num+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageReturnDTO getExamInfoList(ExamSummaryModel model, UserDTO user,String app_type, int rows, int page,String sort,String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select r.receive_type,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.exam_type,e.freeze,"
				   +" convert(varchar(100),e.join_date,20) as join_date,convert(varchar(100),e.final_date,20) as final_date, "
				   +" e.final_doctor,u.chi_name as check_doctor,e.status,convert(varchar(100), es.check_time,20) as check_time, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,ct.type_name as customer_type,"
				   +" (case when (es.exam_doctor_id = 0 or es.exam_doctor_id is null) then '未保存' else u1.chi_name+'已保存' end) as remarke,"
				   +" (dbo.GetTeamPayByExamId(e.exam_num) + dbo.GetPersonalPayByExamId(e.exam_num)) personal_pay,"
				   +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese  where  ese.disease_name  like '★%'   and  ese.exam_num=e.exam_num   and   ese.isActive='Y' )  then '★'  else ''  end) as disease_name,"
				   + "(SELECT count(ech.id) FROM examinfo_charging_item ech LEFT JOIN  charging_item  c  ON   ech.charging_item_code=c.item_code  where   c.item_category != '耗材类型' and ech.exam_num = e.exam_num"
				   + " and ech.exam_status !='G' and ech.exam_status !='Y' and ech.isActive='Y' and ech.pay_status <> 'M' and ech.center_num = '"+user.getCenter_num()+"')  as item_number  "
				   + ", case when (select top(1) ep.id from examinfo_print_report ep where ep.exam_num = e.exam_num and ep.rep_type = 'P' order by ep.print_time desc ) = 0 then 'N' else 'Y' end is_print_report "
				   + ",(select max(eci.exam_date) from examinfo_charging_item eci where eci.exam_num = e.exam_num and eci.exam_status = 'Y' and eci.isActive = 'Y' and eci.center_num = '"+user.getCenter_num()+"')as exam_times"
				   +" , e.zyb_final_doctor , e.zyb_final_status,convert(varchar(100), e.zyb_final_time,20) as zyb_final_time , e.wuxuzongjian,dd.data_name  "
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.app_type='"+app_type+"' and es.center_num='"+user.getCenter_num()+"'  "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join user_usr u1 on u1.id = es.exam_doctor_id "
				   +" left join report_receive r on r.exam_num = e.exam_num "
				   +" left join customer_type ct on ct.id = e.customer_type_id"
				   +" left join data_dictionary dd on e.customer_type=dd.id"
				   +" where e.customer_id = c.id and e.is_Active = 'Y' and e.apptype ='"+app_type+"' AND e.center_num = '"+user.getCenter_num()+"' ";
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
		if (model.getEmployeeID() != null && !"".equals(model.getEmployeeID())) {// 工号
			sql += " and e.employeeID='" + model.getEmployeeID().trim() + "'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		} 
		
		if (model.getBatch_id() > 0){
			sql += " and e.batch_id='" + model.getBatch_id() + "'";
			count ++;
		}
		
		if (model.getExam_status() != null && !"".equals(model.getExam_status())) {// 已检未检
			sql += " and e.status = '"+model.getExam_status()+"'";
		}
		if (model.getFinal_time1() != null && !"".equals(model.getFinal_time1())) {// 总检开始日期
			sql += " and e.final_date >= '" + model.getFinal_time1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getFinal_time2() != null && !"".equals(model.getFinal_time2())) {// 总检结束日期
			sql += " and e.final_date < '" + model.getFinal_time2() + " 23:59:59.999'";
			count ++;
		}
		if (model.getExam_doctor() != null && !"".equals(model.getExam_doctor())){
			sql += " and es.exam_doctor_id = "+model.getExam_doctor();
			count ++;
		}
		if(model.getCustomer_type() != null && !"".equals(model.getCustomer_type())){
			sql +=" and e.customer_type = '"+model.getCustomer_type()+"'";
		}
		if ("A".equals(model.getApprove_status())){
			sql += " and es.approve_status = 'A'";
		}else if("B".equals(model.getApprove_status())){
			sql += " and (es.approve_status = 'B' or es.approve_status is null)";
		}
		if(model.getTijianleixin()!=null && !"".equals(model.getTijianleixin())){
			sql += " and  e.exam_type = '"+model.getTijianleixin()+"'   ";
			count ++;
		}
		if(model.getIs_guide_back()!=null && !"".equals(model.getIs_guide_back())){
			sql += " and  e.is_guide_back = '"+model.getIs_guide_back()+"'   ";
			count ++;
		}
		if(model.getCustomer_type_id() > 0){
			sql += " and e.customer_type = "+model.getCustomer_type_id();
			count ++;
		}

		if(StringUtils.isNotBlank(model.getOccuphyexaclass_name())){
			sql += " and dbo.fun_GetOccupationByExamnum(e.exam_num) = '"+model.getOccuphyexaclass_name()+"'";
			count ++;
		}

		if(sort != null && !"".equals(sort)){
			if(sort.equals("item_num_s")){
				sql += " order by item_number "+ order;
			}else{
				sql += " order by "+sort+" "+order;
			}
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		
		if(count == 0){
			return webrole;
		}
//		System.out.println("sql=========="+sql);
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室首页-体检人员列表", sql);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO getExamInfoList2(ExamSummaryModel model,String app_type, UserDTO user, int rows, int page,String sort,String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		int count = 0;
		String sql =" select * from (select r.receive_type,e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,e.customer_type_id,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.exam_type,"
				   +" convert(varchar(50),e.join_date,23) as join_date, convert(varchar(50),e.final_date,23) as final_date,"
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,"
				   +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese  where  ese.disease_name  like '★%'   and  ese.exam_num=e.exam_num   and   ese.isActive='Y' )  then '★'  else ''  end) as disease_name,e.apptype , e.wuxuzongjian  "
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.app_type='"+app_type+"'   and es.center_num='"+user.getCenter_num()+"'  "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join report_receive r on r.exam_num = e.exam_num "
				   +" where e.customer_id = c.id and e.freeze='0' and e.is_Active = 'Y' and e.status != 'Z' and e.apptype='"+app_type+"' and e.center_num = '"+user.getCenter_num()+"' ";
		if (model.getTime1() != null && !"".equals(model.getTime1())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getTime1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 体检结束日期
			sql += " and e.join_date < '" + model.getTime2() + " 23:59:59.999'";
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
		sql += ") a where (not exists (select * from examinfo_charging_item ec where ec.isActive = 'Y' and "
			   +" ec.exam_num = a.exam_num and ec.exam_status in ('N','C','D') and ec.pay_status != 'M' and ec.center_num = '"+user.getCenter_num()+"') "
			   +"or exists (select * from exam_summary esu where esu.center_num='"+user.getCenter_num()+"'  AND esu.exam_num = a.exam_num and esu.exam_doctor_id <> 0))";
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by a.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargingItemDTO> getNotExamItem(String exam_num, String center_num) throws ServiceException {
		String sql =" select a.item_name from( select ci.item_name,ci.item_seq,dd.seq_code,"
				   +" (case when dd.dep_category = '21' then 2 when dd.dep_category = '131' then 1 else 3 end) as code"
				   +" from exam_info e,examinfo_charging_item ec,charging_item ci,department_dep dd"
				   +" where e.exam_num = ec.exam_num and ec.charging_item_code = ci.item_code and dd.id = ci.dep_id and ci.item_category <> '耗材类型' and"
				   +" ec.isActive = 'Y' and ec.exam_status in('N','D','C') and ec.pay_status != 'M' and ec.center_num = '"+center_num+"' "
				   		+ "and e.exam_num = '"+exam_num+"') a"
				   +" order by a.code,a.seq_code,a.item_seq";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamSummaryDTO> getResultsHistoryList(String exam_num,String app_type,UserDTO user) throws ServiceException {
		
		String sql = " select convert(varchar(50),A.join_date,23) as acceptance_date,s.final_exam_result,s.exam_num "
				+ "from (select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.exam_num,e.join_date "
				+ "from exam_info e, (select customer_id, id, join_date from exam_info a where a.exam_num='"+exam_num+"' and a.is_Active='Y') b "
				+ "where e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N') as A,"
				+ "exam_summary s where s.center_num = '"+user.getCenter_num()+"'  AND A.exam_num = s.exam_num "
						+ "and s.app_type = '"+app_type+"' order by A.join_date desc";
		List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getPtItemResultsHistory(String exam_num, long item_id) throws ServiceException {
		String sql = " select A.exam_num,c.exam_result,convert(varchar(50),c.exam_date,23) as exam_date,c.health_level "
				+ "from (select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.id,e.exam_num from exam_info e,"
				+ "(select customer_id, id, join_date from exam_info a where a.exam_num='"+exam_num+"' and a.is_Active='Y') b where "
				+ "e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N') as A,"
				+ "common_exam_detail c where A.row in (2,3,4) and c.exam_num = A.exam_num and "
				+ "c.exam_item_id = "+item_id+" order by c.exam_date desc";
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DepExamResultDTO> getHyItemResultsHistory(String exam_num, long item_id) throws ServiceException {
		String sql = " select A.exam_num,d.exam_result,convert(varchar(50),d.exam_date,23) as exam_date,d.ref_indicator as health_level,d.item_unit,d.ref_value "
				+ "from (select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.id,e.exam_num from exam_info e, "
				+ "(select a.exam_num,customer_id, id, join_date from exam_info a where a.exam_num='"+exam_num+"' and a.is_Active='Y') b where "
				+ "e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N') as A,"
				+ "exam_result_detail d where A.row in (2,3,4) and d.exam_num = a.exam_num and "
				+ "d.exam_item_id = "+item_id+" order by d.exam_date desc";
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}
	
	@Override
	public List<DepExamResultDTO> getYxItemResultsHistory(String exam_num, long sample_id) throws ServiceException {
		String sql = " select convert(varchar(50),v.exam_date,23) as exam_date,v.exam_result,v.id as item_id,v.exam_desc from "
				+ "(select row=ROW_NUMBER() over (partition by e.customer_id order by e.join_date desc), e.id,e.exam_num from exam_info e,"
				+ "(select customer_id, id, join_date from exam_info a where a.exam_num='"+exam_num+"' and a.is_Active='Y') b where "
				+ "e.customer_id=b.customer_id and e.join_date<=b.join_date and e.is_Active='Y' and counter_check='N') as A,"
				+ "pacs_summary p,view_exam_detail v where A.row in (2,3,4) and p.id = v.pacs_id and v.exam_num = A.exam_num "
				+ "and p.examinfo_sampleId = "+sample_id+" order by v.exam_date desc";
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getExamInfoAppType(String exam_num, String center_num) throws ServiceException {
		String sql = " select distinct ec.app_type from examinfo_charging_item ec"
				   + " where ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.exam_status <> 'G' and ec.center_num = '"+center_num+"'"
				   + " and ec.exam_num = '"+exam_num+"'";
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
	public String getBatchAudit(ExamSummaryModel model, UserDTO user) throws ServiceException {
		String sql = "SELECT distinct e.join_date , e.status,c.user_name,e.id,e.exam_num,e.zyb_final_status,"
			      +" s.approve_status"
			      +"  FROM  customer_info c,exam_info e "  
				  +"	LEFT  JOIN  exam_summary s ON  s.exam_num=e.exam_num   AND s.center_num = '"+user.getCenter_num()+"'   "
				  +"	WHERE   e.is_Active='Y' and  c.id = e.customer_id " 
				  +"	 and   e.exam_num in("+model.getExam_num()+") and e.apptype = '"+model.getApp_type()+"' order by join_date desc" ;
		List<ExamSummaryDTO> li = this.jqm.getList(sql,ExamSummaryDTO.class);
		String str = "";
		for (int i = 0; i < li.size(); i++) {
		    if(!"Z".equals(li.get(i).getStatus())){        				//沒有总检不能审核
				if(i>2){
					str+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+li.get(i).getExam_num()+li.get(i).getUser_name()+"--没有总检<br/>";
				}else{
					str+= li.get(i).getExam_num()+li.get(i).getUser_name()+"--没有总检<br/>";
				}
			} else if("A".equals(li.get(i).getApprove_status())){
					//不能重复总检
				if(i>2){
					str+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+li.get(i).getExam_num()+li.get(i).getUser_name()+"--重复审核<br/>";
				} else {
					str+= li.get(i).getExam_num()+li.get(i).getUser_name()+"--重复审核<br/>";
				}
			}
		}
		if("".equals(str)){
			String hql = " FROM ExamSummary x   where x.exam_num in ("+model.getExam_num()+") and x.app_type = '"+model.getApp_type()+"'";
			List<ExamSummary> list = this.qm.find(hql);
			String val = "N";
			try {
				val= this.customerInfoService.getCenterconfigByKey("INSERT_report_pdf", user.getCenter_num()).getConfig_value().trim();
			}catch (Exception e){
				System.out.println("缺少配置 INSERT_report_pdf  ");
			}
            for (int j = 0; j < list.size(); j++) {
				list.get(j).setApprove_status("A");
				list.get(j).setCheck_doc(user.getUserid());
				list.get(j).setCheck_time(DateTimeUtil.parse());
                list.get(j).setRead_status1(0);
                list.get(j).setRead_status2(0);
                list.get(j).setRead_status3(0);
				this.pm.update(list.get(j));
                if("ZJ_SH".equals(val)) {
                    ExamInfoDTO ei = this.customerInfoService.getCustExamInfoForexamId(list.get(j).getExam_num());
                    ReportPdf r = new ReportPdf();
                    r.setApptype(model.getApp_type());
                    r.setExam_num(ei.getExam_num());
                    r.setArch_num(ei.getArch_num());
                    r.setReport_year(Integer.parseInt(ei.getJoin_date().substring(0,4)));
                    r.setIs_finished(1);
                    r.setCreate_time(DateTimeUtil.parse());
                    this.pm.save(r);
                }
			}
		}
		return str;
	}

    @Override
	public String saveExamDiseaseList(String exam_num,String app_type,List<ExaminfoDiseaseDTO> listdisease, UserDTO user) throws ServiceException {
		List<ExaminfoDisease> oldDis = this.qm.find("from ExaminfoDisease e where e.exam_num = '"+exam_num+"' and e.app_type='"+app_type+"'");
		for(ExaminfoDisease examinfoDisease : oldDis){
			this.pm.remove(examinfoDisease);
		}
		int count = 1;
		for(ExaminfoDiseaseDTO disDto : listdisease){
			ExaminfoDisease  examinfoDisease = new ExaminfoDisease();
			
//			examinfoDisease.setExam_info_id(examinfo_id);
			examinfoDisease.setDisease_id(disDto.getDisease_id());
			examinfoDisease.setDisease_name(disDto.getDisease_name());
			examinfoDisease.setDisease_index(count);
			examinfoDisease.setIsActive("Y");
			examinfoDisease.setCreater(user.getUserid());
			examinfoDisease.setCreate_time(DateTimeUtil.parse());
			examinfoDisease.setUpdater(user.getUserid());
			examinfoDisease.setUpdate_time(DateTimeUtil.parse());
			examinfoDisease.setDisease_type(disDto.getDisease_type());
			examinfoDisease.setIcd_10(disDto.getIcd_10());
			examinfoDisease.setFinal_doc_num(user.getName());
			examinfoDisease.setSuggest(disDto.getSuggest());
			examinfoDisease.setDisease_class(disDto.getDisease_class());
			examinfoDisease.setApp_type(app_type);
			examinfoDisease.setDisease_description(disDto.getDs());
			examinfoDisease.setDiagnosis_source(disDto.getDiagnosis_source());
			examinfoDisease.setItem_source(disDto.getItem_source());
			examinfoDisease.setExam_result(disDto.getExam_result());
			examinfoDisease.setCareer_hazards(disDto.getCareer_hazards());
			examinfoDisease.setCareer_suggest(disDto.getCareer_suggest());
			examinfoDisease.setResultID(disDto.getResultID());
			examinfoDisease.setOccudiseaseIDorcontraindicationID(disDto.getOccudiseaseIDorcontraindicationID());
			examinfoDisease.setExam_num(exam_num);
			examinfoDisease.setDisease_num(disDto.getDisease_num());
			examinfoDisease.setData_source(disDto.getData_source());
			this.pm.save(examinfoDisease);
			count ++;
		}
		return "ok-保存成功!";
	}
	
	public List<DepExamResultDTO> getSymptomsAndHistoryList(String exam_num) throws ServiceException{
		String sql = "select l.questResult exam_result,q.ItemName item_name from quest_exam_List l,quest_dict_Item q "
				+ "where l.resultId_itemID = q.ItemId and q.ItemId in ('42','43','13','376') and l.peId = '"+exam_num+"' "
				+ "union all "
				+ "select l.questResult,q.ItemName from quest_exam_List l,quest_dict_Item q where l.resultId_itemID = q.ItemId "
				+ "and l.questResult = '+' and l.peId = '"+exam_num+"'";
		List<DepExamResultDTO> list = this.jqm.getList(sql, DepExamResultDTO.class);
		return list;
	}
	
	@Override
	public PageReturnDTO getExamSummaryRejectList(ExamSummaryModel model, UserDTO user, int rows, int page, String sort,
			String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select c.arch_num,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,e.age,c.sex,e.exam_num,r.examinfo_id,e.join_date,e.final_date,e.final_doctor,"
				+ "e.exam_type,dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,u.chi_name reject_doctor,"
				+ "r.reject_time,r.reject_context,r.done_status,r.done_time,e.freeze from customer_info c,exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				+ ",exam_summary s,exam_summary_reject r left join user_usr u on u.id = r.reject_doctor "
				+ "where c.id = e.customer_id and e.exam_num = s.exam_num and e.id = r.examinfo_id and s.app_type ='1'"
				+ " and s.center_num = '"+user.getCenter_num()+"' and s.exam_doctor_id = "+ user.getUserid();
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name like '" + model.getUser_name().trim() + "%'";
			count ++;
		}  
		if (model.getTime1() != null && !"".equals(model.getTime1())) {//驳回日期
			sql += " and r.reject_time >= '" + model.getTime1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 驳回结束
			sql += " and r.reject_time < '" + model.getTime2() + " 23:59:59.999'";
			count ++;
		}
		if(model.getExam_status() != null && !"".equals(model.getExam_status())){
			sql += " and r.done_status = " + model.getExam_status();
			count ++;
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
		PageSupport map = this.jqm.getList(sql, page, rows, ExamSummaryRejectDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public List<UserDTO> getFinalDoctorList(String type, String center_num ) throws ServiceException {
		String sql = "select distinct u.id,u.chi_name name from user_usr u,department_dep d,department_vs_center de,dep_user du "
				+ "where u.id = du.user_id  and de.dep_id = d.id  and de.center_num ='"+center_num+"'  and d.id = du.dep_id and du.apptype = '1' ";
		if("1".equals(type)){//总检医生
			sql += "and d.dep_num = '015'";
		}else if("2".equals(type)){//审核医生
			sql += "and d.dep_num = 'SHKS'";
		}else if("3".equals(type)){//复审医生
			sql += "and d.dep_num = 'FSKS'";
		}else if("4".equals(type)){
			sql += "and d.dep_num = '000'";
		}
		sql += " order by u.chi_name";
		List<UserDTO> list = this.jqm.getList(sql, UserDTO.class);
		return list;
	}
	
	
	@Override
	public PageReturnDTO getHasFinalExamInfoList(ExamSummaryModel model, UserDTO user, int rows, int page, String sort,
			String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		int count = 0;
		String sql =" select  e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,"
					+ " e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,e.freeze,"
				   +" convert(varchar(50),e.join_date,23) as join_date,convert(varchar(50),e.final_date,23) as final_date, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,ct.type_name as customer_type,"
				   +" (case when (es.exam_doctor_id = 0 or es.exam_doctor_id is null) then '未保存' else u1.chi_name+'已保存' end) as remarke,"
				   +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=e.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name "
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.app_type='1'  and  es.center_num = '"+user.getCenter_num()+"' "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join user_usr u1 on u1.id = es.exam_doctor_id "
				   +" left join customer_type ct on ct.id = e.customer_type_id"
				   +" where e.customer_id = c.id and e.is_Active = 'Y' "
				   +" and e.status = 'Z' and e.apptype ='1' AND e.center_num = '"+user.getCenter_num()+"' ";//and f.z1creater = "+user.getUserid();
//		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
//			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
//			count ++;
//		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='"+model.getExam_num().trim()+"')";
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
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		} 
		if (model.getFinal_time1() != null && !"".equals(model.getFinal_time1())) {// 总检开始日期
			sql += " and e.final_date >= '" + model.getFinal_time1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getFinal_time2() != null && !"".equals(model.getFinal_time2())) {// 总检结束日期
			sql += " and e.final_date < '" + model.getFinal_time2() + " 23:59:59.999'";
			count ++;
		}
		if(model.getCustomer_type_id()>0){
			sql +=" and e.customer_type_id = '"+model.getCustomer_type_id()+"'";
			count ++;
		}
		if(model.getCustomer_type()!=null && !"".equals(model.getCustomer_type())){
			sql +=" and e.customer_type = '"+model.getCustomer_type()+"'";
			count ++;
		}
		if(model.getExam_doctor() != null && !"".equals(model.getExam_doctor())){
			sql +=" and es.exam_doctor_id in ("+model.getExam_doctor()+")";
		}
		if ("A".equals(model.getApprove_status())){
			sql += " and es.approve_status = 'A'";
		}else if("B".equals(model.getApprove_status())){
			sql += " and (es.approve_status = 'B' or es.approve_status is null)";
		}
		if(model.getTijianleixin()!=null && !"".equals(model.getTijianleixin())){
			sql += " and  e.exam_type = '"+model.getTijianleixin()+"'   ";
			count ++;
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.final_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("180总检室首页-体检人员列表", sql);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	
	@Override
	public List<ExamInfoUserDTO> getFinalExamInfoList(UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		String sql =" select e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,"
				+ "e.exam_num,e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,"
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,"
				   +" convert(varchar(50),e.join_date,23) as join_date,dd.data_name "
				   +" from customer_info c,exam_flow_config f ,exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				   + " left join data_dictionary dd on e.customer_type=dd.id "
				   +" where f.center_num = '"+user.getCenter_num()+"' and e.customer_id = c.id and e.is_Active = 'Y' "
				   +" and e.exam_num = f.exam_num and f.z0 = 1 and f.z1 = 0 and f.z0creater = "+user.getUserid();
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		return list;
	}
	
	@Override
	public ExamSummaryDTO getNotFinalCount(String type,UserDTO user,String IS_EXAM_RESULT_CANFINAL) throws ServiceException {
		ExamSummaryDTO examSummaryDTO = null;
		if("1".equals(type)){//总检工作量
			String sql;
			if("2".equals(IS_EXAM_RESULT_CANFINAL)){
				sql = "select * from (select count(e.id) wz_count from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.center_num = '"+user.getCenter_num()+"' "
					+ "and f.z0 = 0 and e.wuxuzongjian = 0 and (f.s1 = 1 or (f.h1 = 1 and not exists ( select * from examinfo_charging_item ec,charging_item c  "
					+ "where ec.exam_num = e.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.exam_status = 'N' and c.item_code = ec.charging_item_code and c.item_category = '普通类型' and ec.center_num = '"+user.getCenter_num()+"') and (select count(ec.id) "
					+ "from examinfo_charging_item ec where ec.exam_num = e.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+user.getCenter_num()+"') > 0 ))) a, "
					+ "(select count(e.id) yz_count from exam_info e,exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num and f.z1 = 1 and "
					+ "f.z1date = '"+DateTimeUtil.getDate2()+" 23:59:59' ) b,"
					+ "(select count(e.id) zj_count from exam_info e,exam_flow_config f where e.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num and f.z1 = 1 and "
					+ "f.z1date = '"+DateTimeUtil.getDate2()+" 23:59:59' and f.z1creater = "+user.getUserid()+") c";
			}else{
				sql = "select * from (select count(e.id) t from exam_info e,exam_flow_config f where e.exam_num = f.exam_num  and f.center_num = '"+user.getCenter_num()+"' "
					+ "and f.z0 = 0 and e.wuxuzongjian = 0 and f.s1 = 1 and f.z1 = 0) a, "
					+ "(select count(e.id) wz_count from exam_info e,exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num "
					+ "and e.wuxuzongjian = 0 and f.s1 = 1 and f.z1 = 0) w,"
					+ "(select count(e.id) yz_count from exam_info e,exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num and f.z1 = 1 and "
					+ "f.z1date = '"+DateTimeUtil.getDate2()+" 23:59:59' ) b,"
					+ "(select count(e.id) zj_count from exam_info e,exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num and f.z1 = 1 and "
					+ "f.z1date = '"+DateTimeUtil.getDate2()+" 23:59:59' and f.z1creater = "+user.getUserid()+") c";
			}
			List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
			examSummaryDTO = list.get(0);
		}else if("2".equals(type)){
			String sql = "select * from (select count(e.id) wz_count from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.center_num = '"+user.getCenter_num()+"' "
					+ "and f.c = 0 and f.z1 = 1 and f.c1 = 1) a,(select count(e.id) yz_count from exam_info e,exam_flow_config f where e.exam_num = f.exam_num  and f.center_num = '"+user.getCenter_num()+"' "
					+ "and f.c = 1 and f.cdate = '"+DateTimeUtil.getDate2()+" 23:59:59' ) b,"
					+ "(select count(e.id) zj_count from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.c = 1  and f.center_num = '"+user.getCenter_num()+"' "
					+ "and f.cdate = '"+DateTimeUtil.getDate2()+" 23:59:59' and f.ccreater = "+user.getUserid()+") c,"
					+"(select count(e.id) t from exam_info e,exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num and f.c = 0 "
					+ "and f.z1 = 1 and f.c1 = 1 and f.c1creater = '"+user.getUserid()+"') m";
			List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
			examSummaryDTO = list.get(0);
		}
		return examSummaryDTO;
	}
	@Override
	public String canFinalExamInfo(String num,UserDTO user) throws ServiceException {
		String sql = "select top "+num+" f.id,f.exam_num from exam_info e,exam_flow_config f where f.center_num = '"+user.getCenter_num()+"' and e.exam_num = f.exam_num and f.z0 = 0 "
				   + "and e.wuxuzongjian = 0 and (f.s1 = 1 or (f.h1 = 1 and not exists ( select * from examinfo_charging_item ec,charging_item c where "
				   + "ec.exam_num = e.exam_num and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.exam_status = 'N' and ec.center_num = '"+user.getCenter_num()+"' and c.item_code = ec.charging_item_code and c.item_category = '普通类型') and "
				   + "(select count(ec.id) from examinfo_charging_item ec where ec.exam_num = e.exam_num and ec.isActive = 'Y' "
				   + "and ec.pay_status <> 'M' and ec.center_num = '"+user.getCenter_num()+"') > 0 )) order by e.join_date,e.exam_num";
		
		List<ExamFlowConfig> list = this.jqm.getList(sql, ExamFlowConfig.class);
		if(list.size() == 0){
			return "error-获取失败，没有可总检体检信息!";
		}
		for (ExamFlowConfig examFlowConfig : list) {
			ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
			config.setZ0(1);
			config.setZ0creater(user.getUserid());
			config.setZ0date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			
			log.setExam_num(config.getExam_num());
			log.setFlow_code("z0");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			
			this.pm.save(log);
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	@Override
	public String canFinalExamInfo2(String num,UserDTO user) throws ServiceException {
		String sql = "select top "+num+" f.id,f.exam_num,e.exam_type ,e.join_date,(select count(*) from exam_Critical_detail ecd where ecd.exam_num = e.exam_num) as w_count "
				+ "from exam_info e left join exam_flow_config f  on e.exam_num = f.exam_num" 
				+ " where f.center_num = '"+user.getCenter_num()+"' and  f.z0 = 0 and e.wuxuzongjian = 0 and f.s1 = 1 order by w_count desc ,e.exam_type, e.join_date,e.exam_num";
		
		List<ExamFlowConfig> list = this.jqm.getList(sql, ExamFlowConfig.class);
		if(list.size() == 0){
			return "error-获取失败，没有可总检体检信息!";
		}
		for (ExamFlowConfig examFlowConfig : list) {
			ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
			config.setZ0(1);
			config.setZ0creater(user.getUserid());
			config.setZ0date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			
			log.setExam_num(config.getExam_num());
			log.setFlow_code("z0");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			
			this.pm.save(log);
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	@Override
	public String canFinalExamInfoDbgj(String num,UserDTO user) throws ServiceException {
		int nums = Integer.parseInt(num);
		List<ExamFlowConfig> list = new ArrayList<ExamFlowConfig>();
		String sql = null;

		//获取总检贵宾客户权限资源
		String resource = null;
		List<WebResrelAtionship>  web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS050")){
					resource = web.get(i).getDatavalue();
					break;
				}
			}
		}
		//是否拥有总检贵宾客户权限
		if(resource != null && "1".equals(resource)){
			//贵宾体检最优先 金额大于5000 判断条件 vipflag = 1
			sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 "
				+ "and f.center_num = '"+user.getCenter_num()+"' and e.wuxuzongjian = 0 and e.vipflag = 1 and f.s1 = 1 and f.z1 = 0 order by e.join_date,e.exam_num";
			List<ExamFlowConfig> gblist = this.jqm.getList(sql, ExamFlowConfig.class);
			list.addAll(gblist);
			nums = nums - gblist.size();//获取总数减去以获取人数
		}
		//判断存在ttm项目的由TTM检查医生做主检
		String dep_num = "EO";//TTM科室编码
		if(resource != null && "2".equals(resource)){
			if(nums > 0){
				sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 "
						+ "and e.wuxuzongjian = 0 and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 and f.center_num = '"+user.getCenter_num()+"'  "
						+ "and exists (select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where "
						+ "ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_num = '"+dep_num+"' and ec.exam_status = 'Y' "
						+ "and ec.exam_doctor_name = '"+user.getName()+"' and e.exam_num = ec.exam_num and ec.center_num = '"+user.getCenter_num()+"') order by e.join_date,e.exam_num";
				List<ExamFlowConfig> ttmlist = this.jqm.getList(sql, ExamFlowConfig.class);
				list.addAll(ttmlist);
				nums = nums - ttmlist.size();//获取总数减去以获取人数
			}
		}
		//判断个人体检由开单医生做主检
		if(resource != null && "3".equals(resource)){
			if(nums > 0){
				sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 "
						+ "and e.wuxuzongjian = 0 and e.exam_type = 'G' and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 and f.center_num = '"+user.getCenter_num()+"'  "
						+ "and not exists (select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where "
						+ "ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_num = '"+dep_num+"' "
						+ "and e.exam_num = ec.exam_num and ec.center_num = '"+user.getCenter_num()+"')  order by e.join_date,e.exam_num";
				List<ExamFlowConfig> grlist = this.jqm.getList(sql, ExamFlowConfig.class);
				list.addAll(grlist);
				nums = nums - grlist.size();//获取总数减去以获取人数
			}
		}
		
		//判断团检体检按上限自由获取
		if(resource == null || "1".equals(resource) || "2".equals(resource)){
			if(nums > 0){
				sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 "
						+ "and e.wuxuzongjian = 0 and e.exam_type = 'T' and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 and f.center_num = '"+user.getCenter_num()+"' "
						+ "and not exists (select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where "
						+ "ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_num = '"+dep_num+"' "
						+ "and e.exam_num = ec.exam_num and ec.center_num = '"+user.getCenter_num()+"') order by e.join_date,e.exam_num";
				List<ExamFlowConfig> ttlist = this.jqm.getList(sql, ExamFlowConfig.class);
				list.addAll(ttlist);
				nums = nums - ttlist.size();//获取总数减去以获取人数
			}
		}
		if(list.size() == 0){
			return "error-获取失败，没有可总检体检信息!";
		}
		for (ExamFlowConfig examFlowConfig : list) {
			ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
			config.setZ0(1);
			config.setZ0creater(user.getUserid());
			config.setZ0date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			
			log.setExam_num(config.getExam_num());
			log.setFlow_code("z0");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			
			this.pm.save(log);
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	
	@Override
	public long finalGainCount(UserDTO user) throws ServiceException{
		String sql = "select count(e.id) as wz_count from exam_flow_config f ,exam_info e where e.is_Active = 'Y'  and f.center_num = '"+user.getCenter_num()+"' "
				+ "and e.exam_num = f.exam_num and f.z0 = 1 and f.z1 = 0 and f.z0creater = " + user.getUserid();
		List<ExamSummaryDTO> list = this.jqm.getList(sql, ExamSummaryDTO.class);
		if(list.size() > 0){
			return list.get(0).getWz_count();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String createFinalExamResultXYYY(String exam_num, String isExamSummary, String isShowRefvalue,String isShowSamplename, String examResultShowItemId, String examSummaryResultIn, String app_type, String center_num) throws ServiceException {
		String app1 = "";
		String app2 = "";
		String app3 = "";
		if("2".equals(app_type)){
			app1 = " and ed.app_type = '"+app_type+"'";
			app2 = " and ec.app_type='"+app_type+"'";
			app3 = " and eci.app_type='"+app_type+"'";
		}
		
		String examSummaryStr = "";
		String comSql = " select ed.exam_result_summary as exam_result,dd.dep_name,dd.dep_num from exam_dep_result ed,department_dep dd"
				      + " where ed.dep_id = dd.id and ed.exam_num ='"+exam_num+"' "
				      + app1+ " order by dd.seq_code";
		
		List<DepExamResultDTO> comList = this.jqm.getList(comSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-普通", comSql);
		int count = 1;
		String kouge = " ";
		int zongshu = StringUtil.getStrLength("1.一般");
		String zongkongge = "";
		for (int k = 0; k < zongshu; k++) {
			zongkongge += kouge;
		}
		for(DepExamResultDTO cDto : comList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				if("007".equals(cDto.getDep_num())){
					List<String> list = new ArrayList<String>();
					boolean xyshow = false;
					for (int j = 0; j < examstr.length; j++) {
						if(examstr[j].indexOf("收缩压") != -1 || examstr[j].indexOf("舒张压") != -1){
							if(!xyshow){
								String str = "(1)血压:";
								String sql = " select cd.exam_result from common_exam_detail cd where "
										   + " cd.item_code = 'WL004' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> ssy = this.jqm.getList(sql, DepExamResultDTO.class);
								if(ssy.size() != 0){
									str += ssy.get(0).getExam_result();
								}
								String sql1 = " select cd.exam_result from common_exam_detail cd where "
										    + " cd.item_code = 'WL005' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> szy = this.jqm.getList(sql1, DepExamResultDTO.class);
								if(szy.size() != 0){
									str += "/"+szy.get(0).getExam_result()+"mmHg";
								}
								list.add(str);
								xyshow = true;
							}
						}else{
							list.add(examstr[j]);
						}
					}
					String reg = "\\(\\d{1,2}\\)";
					Pattern r = Pattern.compile(reg);
					for(int j=0;j<list.size();j++){
						Matcher m = r.matcher(list.get(j));
						examSummaryStr += zongkongge + m.replaceAll("("+(j+1)+")") + "\r\n";
						if(j == list.size()-1){
							examSummaryStr += "\n";
						}
					}
				}else{
					for (int j = 0; j < examstr.length; j++) {
						examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
						if(j == examstr.length-1){
							examSummaryStr += "\n";
						}
					}
				}
				count ++;
			}
		}
		
		String depName = "c.item_name";
		String dep_seq = "c.item_seq";
		if("Y".equals(isShowSamplename)){
			depName = "s.demo_name";
			dep_seq = "s.print_seq ";
		}
				
		String viewSql =" select distinct v.exam_result,"+depName+" as dep_name,dd.seq_code,"+dep_seq+" "
				+ "from view_exam_detail v,pacs_summary p,charging_item c,examinfo_charging_item ec,"
					   +" department_dep dd,pacs_detail pd,sample_demo s "
					   + "where ec.charging_item_code = c.item_code and s.id = c.sam_demo_id "
					   + "and ec.exam_num='"+exam_num+"' and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"'" 
					   +" and v.exam_num = ec.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = '"+exam_num+"' "
					   + "and dd.id = c.dep_id"
					   +" and p.id=pd.summary_id and pd.chargingItem_num=c.item_code "+app2+" order by dd.seq_code,"+dep_seq;
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检查", viewSql);
		for(DepExamResultDTO cDto : viewList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr +=count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				for (int j = 0; j < examstr.length; j++) {
					if("".equals(examstr[j].replace("\n", "").replace("\r", "").replaceAll(" ", ""))){
						continue;
					}
					examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "").trim() + "\r\n";
				}
				examSummaryStr += "\n";
				count ++;
			}
		}
		
		String examSql = "select distinct c.item_name as dep_name,ei.item_name,ei.id as item_id,"
				+ "er.exam_result,er.item_unit,er.ref_indicator as health_level,c.item_seq,ei.seq_code,er.ref_value "
				+ "from exam_result_detail er,examination_item ei,charging_item c,charging_item_exam_item ci,examinfo_charging_item eci "
					    +"where er.exam_num = '"+exam_num+"' "
					    + "and er.item_code = ei.item_num "
					    + "and er.charging_item_code = c.item_code " 
					    +"and ei.item_num = ci.item_code "
					    + "and ci.charging_item_code = c.item_code "
					    + "and eci.isActive ='Y' "
					    + "and eci.pay_status <> 'M' "
					    + "and eci.exam_status = 'Y' and eci.center_num = '"+center_num+"' "
					    +"and eci.charging_item_code = c.item_code "
					    + "and eci.exam_num = '"+exam_num+"'"+app3+" order by c.item_seq,ei.seq_code ";
		List<DepExamResultDTO> examList = this.jqm.getList(examSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检验", examSql);
		Map<String,String> itemidMap = new HashMap<String,String>();
		if(examResultShowItemId != null && !"".equals(examResultShowItemId)){
			String[] itemids = examResultShowItemId.split(",");
			for (int i = 0; i < itemids.length; i++) {
				itemidMap.put(itemids[i], itemids[i]);
			}
		}
		
		int maxLength = 0;
		for(DepExamResultDTO cDto : examList){
			String str = "";
			if("1".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
			}else if("2".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
			}else if("3".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
			}else if("4".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
			}else if("5".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
			}else if("6".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
			}else{
				if(itemidMap.get(cDto.getItem_id()+"") != null){
					str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}
			}
			if(maxLength < StringUtil.getStrLength(str)){
				maxLength = StringUtil.getStrLength(str);
			}
		}
		if(maxLength == 0){
			return examSummaryStr;
		}
		examSummaryStr +=count+"." + "检验结果:\n";
		
		String dep_name = "";
		for(DepExamResultDTO cDto : examList){
			if(!dep_name.equals(cDto.getDep_name())){
				if("1".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					dep_name = cDto.getDep_name();
				}else if("2".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					dep_name = cDto.getDep_name();
				}else if("3".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					dep_name = cDto.getDep_name();
				}else if("4".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					dep_name = cDto.getDep_name();
				}else if("5".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
					dep_name = cDto.getDep_name();
				}else if("6".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
					examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
					dep_name = cDto.getDep_name();
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						if(!"".equals(dep_name)){
							examSummaryStr += "\n";
						}
						examSummaryStr += zongkongge + cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						dep_name = cDto.getDep_name();
					}
				}
			}else{
				
				int zongshu1 = zongshu + 2;
				String zongkongge1 = "";
				for (int k = 0; k < zongshu1; k++) {
					zongkongge1 += kouge;
				}
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
				}else if("5".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
				}else if("6".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}
				}
			}
			if(!"0".equals(cDto.getHealth_level()) || itemidMap.get(cDto.getItem_id()+"") != null){
				if("Y".equals(isShowRefvalue)){
					String str = "";
					if("1".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					}else if("2".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					}else if("3".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}else if("4".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					}else if("5".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
					}else if("6".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
					}else{
						if(itemidMap.get(cDto.getItem_id()+"") != null){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						}
					}
					int zongshu1 = maxLength - StringUtil.getStrLength(str);
					String zongkongge1 = "";
					String cankaozhi = "";
					String maxstr = "";
					for (int k = 0; k < zongshu1; k++) {
						zongkongge1 += kouge;
					}
					for (int k = 0; k < StringUtil.getStrLength("参考值:  "); k++) {
						cankaozhi += kouge;
					}
					for (int k = 0; k < maxLength; k++) {
						maxstr += kouge;
					}
					
					String[] refs = cDto.getRef_value().split("\n");
					for (int i = 0; i < refs.length; i++) {
						if(i == 0){
							examSummaryStr += zongkongge1 + "参考值:"+refs[i] + "\n";
						}else{
							examSummaryStr += zongkongge+maxstr + cankaozhi+refs[i] + "\n";
						}
					}
				}else{
					examSummaryStr += "\n";
				}
			}
		}
		return examSummaryStr;
	}
	public String createFinalExamResultNHFY(String exam_num, String isExamSummary, String isShowRefvalue,String isShowSamplename, String examResultShowItemId, String examSummaryResultIn, String app_type, String center_num) throws ServiceException{
		String app1 = "";
		String app2 = "";
		String app3 = "";
		if("2".equals(app_type)){
			app1 = " and ed.app_type = '"+app_type+"'";
			app2 = " and ec.app_type='"+app_type+"'";
			app3 = " and eci.app_type='"+app_type+"'";
		}
		
		String examSummaryStr = "";
		String comSql = " select ed.exam_result_summary as exam_result,dd.dep_name,dd.dep_num from exam_dep_result ed,department_dep dd"
				      + " where ed.dep_id = dd.id and ed.exam_num ='"+exam_num+"' "
				      + app1+" order by dd.seq_code";
		
		List<DepExamResultDTO> comList = this.jqm.getList(comSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-普通", comSql);
		int count = 1;
		String kouge = " ";
		int zongshu = StringUtil.getStrLength("1.一般");
		String zongkongge = "";
		for (int k = 0; k < zongshu; k++) {
			zongkongge += kouge;
		}
		for(DepExamResultDTO cDto : comList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				if("007".equals(cDto.getDep_num())){
					List<String> list = new ArrayList<String>();
					boolean xyshow = false;
					for (int j = 0; j < examstr.length; j++) {
						if(examstr[j].indexOf("收缩压") != -1 || examstr[j].indexOf("舒张压") != -1){
							if(!xyshow){
								String str = "(1)血压:";
								String sql = "select cd.exam_result from common_exam_detail cd where "
										   + "cd.item_code = 'WL004' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> ssy = this.jqm.getList(sql, DepExamResultDTO.class);
								if(ssy.size() != 0){
									str += ssy.get(0).getExam_result();
								}
								String sql1 = "select cd.exam_result from common_exam_detail cd where "
										    + "cd.item_code = 'WL005' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> szy = this.jqm.getList(sql1, DepExamResultDTO.class);
								if(szy.size() != 0){
									str += "/"+szy.get(0).getExam_result()+"mmHg";
								}
								list.add(str);
								xyshow = true;
							}
						}else{
							list.add(examstr[j]);
						}
					}
					String reg = "\\(\\d{1,2}\\)";
					Pattern r = Pattern.compile(reg);
					for(int j=0;j<list.size();j++){
						Matcher m = r.matcher(list.get(j));
						examSummaryStr += zongkongge + m.replaceAll("("+(j+1)+")") + "\r\n";
					}
				}else{
					for (int j = 0; j < examstr.length; j++) {
						examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
					}
				}
				count ++;
			}
		}
	
		String examSql = "select distinct c.item_name as dep_name,ei.item_name,ei.id as item_id,"
				+ "er.exam_result,er.item_unit,er.ref_indicator as health_level,c.item_seq,ei.seq_code,er.ref_value "
				+ "from exam_result_detail er,examination_item ei,charging_item c,charging_item_exam_item ci,examinfo_charging_item eci "
			    +"where er.exam_num = '"+exam_num+"' "
			    + "and er.item_code = ei.item_num "
			    +"and ei.item_num = ci.item_code "
			    + "and ci.charging_item_code = c.item_code "
			    + "and eci.isActive ='Y' "
			    + "and eci.pay_status <> 'M' "
			    + "and eci.exam_status = 'Y' and eci.center_num = '"+center_num+"' "
			    +"and eci.charging_item_code = c.item_code "
			    + "and eci.exam_num =  '"+exam_num+"' "+app3+" order by c.item_seq,ei.seq_code ";
		List<DepExamResultDTO> examList = this.jqm.getList(examSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检验", examSql);
		Map<String,String> itemidMap = new HashMap<String,String>();
		if(examResultShowItemId != null && !"".equals(examResultShowItemId)){
			String[] itemids = examResultShowItemId.split(",");
			for (int i = 0; i < itemids.length; i++) {
				itemidMap.put(itemids[i], itemids[i]);
			}
		}
		
		int maxLength = 0;
		for(DepExamResultDTO cDto : examList){
			String str = "";
			if("1".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
			}else if("2".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
			}else if("3".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
			}else if("4".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
			}else{
				if(itemidMap.get(cDto.getItem_id()+"") != null){
					str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}
			}
			if(maxLength < StringUtil.getStrLength(str)){
				maxLength = StringUtil.getStrLength(str);
			}
		}
		if(maxLength > 0){
			examSummaryStr +=count+"." + "检验结果:\n";
			String dep_name = "";
			int zcount = 1;
			for(DepExamResultDTO cDto : examList){
				if(!dep_name.equals(cDto.getDep_name())){
					if("1".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge +"（"+zcount+"）"+ cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
						dep_name = cDto.getDep_name();
						zcount ++;
					}else if("2".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge +"（"+zcount+"）"+ cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
						dep_name = cDto.getDep_name();
						zcount ++;
					}else if("3".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge +"（"+zcount+"）"+ cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						dep_name = cDto.getDep_name();
						zcount ++;
					}else if("4".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge +"（"+zcount+"）"+ cDto.getDep_name()+": \n";
						examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
						dep_name = cDto.getDep_name();
						zcount ++;
					}else{
						if(itemidMap.get(cDto.getItem_id()+"") != null){
							examSummaryStr += zongkongge +"（"+zcount+"）"+ cDto.getDep_name()+": \n";
							examSummaryStr += zongkongge +"  "+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
							dep_name = cDto.getDep_name();
							zcount ++;
						}
					}
				}else{
					
					int zongshu1 = zongshu + 2;
					String zongkongge1 = "";
					for (int k = 0; k < zongshu1; k++) {
						zongkongge1 += kouge;
					}
					if("1".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					}else if("2".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					}else if("3".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}else if("4".equals(cDto.getHealth_level())){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					}else{
						if(itemidMap.get(cDto.getItem_id()+"") != null){
							examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						}
					}
				}
				if(!"0".equals(cDto.getHealth_level()) || itemidMap.get(cDto.getItem_id()+"") != null){
					if("Y".equals(isShowRefvalue)){
						String str = "";
						if("1".equals(cDto.getHealth_level())){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
						}else if("2".equals(cDto.getHealth_level())){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
						}else if("3".equals(cDto.getHealth_level())){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						}else if("4".equals(cDto.getHealth_level())){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
						}else{
							if(itemidMap.get(cDto.getItem_id()+"") != null){
								str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
							}
						}
						int zongshu1 = maxLength - StringUtil.getStrLength(str);
						String zongkongge1 = "";
						String cankaozhi = "";
						String maxstr = "";
						for (int k = 0; k < zongshu1; k++) {
							zongkongge1 += kouge;
						}
						for (int k = 0; k < StringUtil.getStrLength("参考值:  "); k++) {
							cankaozhi += kouge;
						}
						for (int k = 0; k < maxLength; k++) {
							maxstr += kouge;
						}
						
						String[] refs = cDto.getRef_value().split("\n");
						for (int i = 0; i < refs.length; i++) {
							if(i == 0){
								examSummaryStr += zongkongge1 + "参考值:"+refs[i] + "\n";
							}else{
								examSummaryStr += zongkongge+maxstr + cankaozhi+refs[i] + "\n";
							}
						}
					}else{
						examSummaryStr += "\n";
					}
				}
			}
			count ++;
		}
				
		String depName = "c.item_name";
		String dep_seq = "c.item_seq";
		if("Y".equals(isShowSamplename)){
			depName = "s.demo_name";
			dep_seq = "s.print_seq ";
		}
				
		String viewSql =" select distinct v.exam_result,"+depName+" as dep_name,dd.seq_code,"+dep_seq+" "
				+ "from view_exam_detail v,pacs_summary p,charging_item c,examinfo_charging_item ec,"
					   +" department_dep dd,pacs_detail pd,sample_demo s "
					   + "where ec.charging_item_code = c.item_code and s.id = c.sam_demo_id "
					   + "and ec.exam_num='"+exam_num+"' and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"'" 
					   +" and v.exam_num = ec.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = '"+exam_num+"' "
					   + "and dd.id = c.dep_id"
					   +" and p.id=pd.summary_id and pd.chargingItem_num=c.item_code "+app2+" order by dd.seq_code,"+dep_seq;
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检查", viewSql);
		for(DepExamResultDTO cDto : viewList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr +=count+"." + cDto.getDep_name() + ":\n";
				String[] examstr = cDto.getExam_result().split("\n");
				for (int j = 0; j < examstr.length; j++) {
					if("".equals(examstr[j].replace("\n", "").replace("\r", "").replaceAll(" ", ""))){
						continue;
					}
					examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "").trim() + "\r\n";
				}
				count ++;
			}
		}
		return examSummaryStr;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String createFinalExamResultERYUAN(String exam_num, String isExamSummary, String isShowRefvalue,
			String isShowSamplename, String examResultShowItemId, String examSummaryResultIn, String app_type, String center_num) throws ServiceException {
		
		String app1 = "";
		String app2 = "";
		String app3 = "";
		if("2".equals(app_type)){
			app1 = " and ed.app_type = '"+app_type+"'";
			app2 = " and ec.app_type='"+app_type+"'";
			app3 = " and eci.app_type='"+app_type+"'";
		}
		
		String examSummaryStr = "";
		String comSql = " select ed.exam_result_summary as exam_result,dd.dep_name,dd.dep_num from exam_dep_result ed,department_dep dd"
				      + " where ed.dep_id = dd.id and ed.exam_num ='"+exam_num+"' "
				      + app1+" order by dd.seq_code";
		
		List<DepExamResultDTO> comList = this.jqm.getList(comSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-普通", comSql);
		int count = 1;
		String kouge = " ";
		int zongshu = StringUtil.getStrLength("1.一般");
		String zongkongge = "";
		for (int k = 0; k < zongshu; k++) {
			zongkongge += kouge;
		}
		for(DepExamResultDTO cDto : comList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				examSummaryStr += count+"." + cDto.getDep_name() + ":";
				zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name() + ":");
				zongkongge = "";
				for (int k = 0; k < zongshu; k++) {
					zongkongge += kouge;
				}
				String[] examstr = cDto.getExam_result().split("\n");
				if("007".equals(cDto.getDep_num())){
					List<String> list = new ArrayList<String>();
					boolean xyshow = false;
					for (int j = 0; j < examstr.length; j++) {
						if(examstr[j].indexOf("收缩压") != -1 || examstr[j].indexOf("舒张压") != -1){
							if(!xyshow){
								String str = "(1)血压:";
								String sql = " select cd.exam_result from common_exam_detail cd where "
										   + " cd.item_code = 'WL004' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> ssy = this.jqm.getList(sql, DepExamResultDTO.class);
								if(ssy.size() != 0){
									str += ssy.get(0).getExam_result();
								}
								String sql1 = " select cd.exam_result from common_exam_detail cd where "
										    + " cd.item_code = 'WL005' and cd.exam_num = '"+exam_num+"'";
								List<DepExamResultDTO> szy = this.jqm.getList(sql1, DepExamResultDTO.class);
								if(szy.size() != 0){
									str += "/"+szy.get(0).getExam_result()+"mmHg";
								}
								list.add(str);
								xyshow = true;
							}
						}else{
							list.add(examstr[j]);
						}
					}
					String reg = "\\(\\d{1,2}\\)";
					Pattern r = Pattern.compile(reg);
					for(int j=0;j<list.size();j++){
						Matcher m = r.matcher(list.get(j));
						examSummaryStr += zongkongge + m.replaceAll("("+(j+1)+")") + "\r\n";
						if(j == list.size()-1){
							examSummaryStr += "\n";
						}
					}
				}else{
					for (int j = 0; j < examstr.length; j++) {
						if(j == 0){
							examSummaryStr += examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
						}else{
							examSummaryStr += zongkongge + examstr[j].replace("\n", "").replace("\r", "") + "\r\n";
						}
					}
					examSummaryStr += "\r\n";
				}
				count ++;
			}
		}
		
		String depName = "c.item_name";
		String dep_seq = "c.item_seq";
		if("Y".equals(isShowSamplename)){
			depName = "s.demo_name";
			dep_seq = "s.print_seq ";
		}
		String viewSql =" select distinct v.exam_result,"+depName+" as dep_name,dd.seq_code ,c.dep_id as id from view_exam_detail v,exam_info e,pacs_summary p,charging_item c,examinfo_charging_item ec,"
					   +" department_dep dd,sample_demo s where s.id = c.sam_demo_id and ec.charging_item_code = c.item_code and e.exam_num = ec.exam_num and ec.isActive ='Y' and ec.pay_status <> 'M' and ec.exam_status = 'Y' and ec.center_num = '"+center_num+"' " 
					   +" and v.exam_num = e.exam_num and v.pacs_req_code = p.pacs_req_code and p.examinfo_num = e.exam_num and dd.id = c.dep_id"
					   +" and p.examinfo_sampleId = c.sam_demo_id and e.exam_num = '"+exam_num+"' "+app2+" order by dd.seq_code";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检查", viewSql);
		String vsql = "select c.item_name ,c.dep_id ,eci.id from examinfo_charging_item eci , charging_item c , exam_info e ,department_dep dd where dd.id = c.dep_id and e.exam_num = eci.exam_num and "
						+" c.item_code = eci.charging_item_code and e.exam_num = '"+exam_num+"' and e.is_Active = 'Y'  and eci.isActive = 'Y' and exam_status  not in ('D','G') and eci.center_num = '"+center_num+"' order by dd.seq_code ";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(vsql, ExaminfoChargingItemDTO.class);
		String x = "";
		String mr = "";
		String ct = "";
		if (list.size() > 0) {
			for (ExaminfoChargingItemDTO examinfoChargingItemDTO : list) {
				if (examinfoChargingItemDTO.getDep_id() == 24) {
					ct+= examinfoChargingItemDTO.getItem_name().replace(" ", "")+"+";
				}else if (examinfoChargingItemDTO.getDep_id() == 25){
					mr+= examinfoChargingItemDTO.getItem_name().replace(" ", "")+"+";
				}else if (examinfoChargingItemDTO.getDep_id() == 14){
					x+= examinfoChargingItemDTO.getItem_name().replace(" ", "")+"+";
				}
			}
		}
		for(DepExamResultDTO cDto : viewList){
			if(!isResultNormal(cDto.getExam_result(), isExamSummary, "",examSummaryResultIn)){
				if(cDto.getId() == 14 ){
					examSummaryStr +=count+"."+ x.substring(0,x.length()-1) + ":";
				}else if(cDto.getId() == 25 ){
					examSummaryStr +=count+"."+ mr.substring(0,mr.length()-1) + ":";
				}else if(cDto.getId() == 24 ){
					examSummaryStr +=count+"."+ ct.substring(0,ct.length()-1) + ":";
				}else{
					examSummaryStr +=count+"." + cDto.getDep_name() + ":";
				}
				
				zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name() + ":");
				zongkongge = "";
				for (int k = 0; k < zongshu; k++) {
					zongkongge += kouge;
				}
				String[] examstr = cDto.getExam_result().split("\n");
				int zcount = 0;
				for (int j = 0; j < examstr.length; j++) {
					if("".equals(examstr[j].replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", ""))){
						continue;
					}
					if(zcount == 0){
						examSummaryStr += examstr[j].replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "")+ "\r\n";
					}else{
						examSummaryStr += zongkongge + examstr[j].replaceAll("\n", "").replaceAll("\r", "").replaceAll(" ", "")+ "\r\n";
					}
					zcount ++;
				}
				examSummaryStr += "\r\n";
				count ++;
			}
		}
		
		String examSql = "select distinct c.item_name as dep_name,ei.item_name,ei.id as item_id,er.exam_result,er.item_unit,er.ref_indicator as health_level,c.item_seq,ei.seq_code,er.ref_value from exam_result_detail er,"
					    +"exam_info e,examination_item ei,charging_item c,charging_item_exam_item ci,examinfo_charging_item eci "
					    +"where er.exam_num = e.exam_num and er.item_code = ei.item_num and e.exam_num = '"+exam_num+"' and er.charging_item_code = c.item_code " 
					    +"and ei.item_num = ci.item_code and ci.charging_item_code = c.item_code and eci.isActive ='Y' and eci.pay_status <> 'M' and eci.exam_status = 'Y' and eci.center_num = '"+center_num+"' "
					    +"and eci.charging_item_code = c.item_code and eci.exam_num = e.exam_num "+app3+"order by c.item_seq,ei.seq_code ";
		List<DepExamResultDTO> examList = this.jqm.getList(examSql, DepExamResultDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-左侧结论-检验", examSql);
		Map<String,String> itemidMap = new HashMap<String,String>();
		if(examResultShowItemId != null && !"".equals(examResultShowItemId)){
			String[] itemids = examResultShowItemId.split(",");
			for (int i = 0; i < itemids.length; i++) {
				itemidMap.put(itemids[i], itemids[i]);
			}
		}
		zongshu = StringUtil.getStrLength("1.一般");
		zongkongge = "";
		for (int k = 0; k < zongshu; k++) {
			zongkongge += kouge;
		}
		int maxLength = 0;
		for(DepExamResultDTO cDto : examList){
			String str = "";
			if("1".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
			}else if("2".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
			}else if("3".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
			}else if("4".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
			}else if("5".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
			}else if("6".equals(cDto.getHealth_level())){
				str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
			}else{
				if(itemidMap.get(cDto.getItem_id()+"") != null){
					str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}
			}
			if(maxLength < StringUtil.getStrLength(str)){
				maxLength = StringUtil.getStrLength(str);
			}
		}
		if(maxLength == 0){
			return examSummaryStr;
		}
//		examSummaryStr +=count+"." + "检验结果:\n";
		
		String dep_name = "";
		for(DepExamResultDTO cDto : examList){
			if(!dep_name.equals(cDto.getDep_name())){
				if("1".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += count+"." + cDto.getDep_name()+":";
					zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
					examSummaryStr += cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					count ++;
					dep_name = cDto.getDep_name();
				}else if("2".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += count+"." + cDto.getDep_name()+":";
					zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
					examSummaryStr += cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					count ++;
					dep_name = cDto.getDep_name();
				}else if("3".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += count+"." + cDto.getDep_name()+":";
					zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
					examSummaryStr += cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					count ++;
					dep_name = cDto.getDep_name();
				}else if("4".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += count+"." + cDto.getDep_name()+":";
					zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
					examSummaryStr +=cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					count ++;
					dep_name = cDto.getDep_name();
				}else if("5".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += count+"." + cDto.getDep_name()+":";
					zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
					examSummaryStr += cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
					count ++;
					dep_name = cDto.getDep_name();
				}else if("6".equals(cDto.getHealth_level())){
					if(!"".equals(dep_name)){
						examSummaryStr += "\n";
					}
					examSummaryStr += count+"." + cDto.getDep_name()+":";
					zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
					examSummaryStr += cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
					count ++;
					dep_name = cDto.getDep_name();
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						if(!"".equals(dep_name)){
							examSummaryStr += "\n";
						}
						examSummaryStr += count+"." + cDto.getDep_name()+":";
						zongshu = StringUtil.getStrLength(count+"." + cDto.getDep_name()+":");
						examSummaryStr += cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						count ++;
						dep_name = cDto.getDep_name();
					}
				}
			}else{
				String zongkongge1 = "";
				for (int k = 0; k < zongshu; k++) {
					zongkongge1 += kouge;
				}
				if("1".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
				}else if("2".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
				}else if("3".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
				}else if("4".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
				}else if("5".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
				}else if("6".equals(cDto.getHealth_level())){
					examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
				}else{
					if(itemidMap.get(cDto.getItem_id()+"") != null){
						examSummaryStr += zongkongge1+cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}
				}
			}
			if(!"0".equals(cDto.getHealth_level()) || itemidMap.get(cDto.getItem_id()+"") != null){
				if("Y".equals(isShowRefvalue)){
					String str = "";
					if("1".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑ ";
					}else if("2".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓ ";
					}else if("3".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
					}else if("4".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")危机 ";
					}else if("5".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↑↑ ";
					}else if("6".equals(cDto.getHealth_level())){
						str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ")↓↓ ";
					}else{
						if(itemidMap.get(cDto.getItem_id()+"") != null){
							str = cDto.getItem_name() + ":(" + cDto.getExam_result() + " " + cDto.getItem_unit() + ") ";
						}
					}
					int zongshu1 = maxLength - StringUtil.getStrLength(str);
					String zongkongge1 = "";
					String cankaozhi = "";
					String maxstr = "";
					for (int k = 0; k < zongshu1; k++) {
						zongkongge1 += kouge;
					}
					for (int k = 0; k < StringUtil.getStrLength("参考值:  "); k++) {
						cankaozhi += kouge;
					}
					for (int k = 0; k < maxLength; k++) {
						maxstr += kouge;
					}
					
					String[] refs = cDto.getRef_value().split("\n");
					for (int i = 0; i < refs.length; i++) {
						if(i == 0){
							examSummaryStr += zongkongge1 + "参考值:"+refs[i] + "\n";
						}else{
							examSummaryStr += zongkongge+maxstr + cankaozhi+refs[i] + "\n";
						}
					}
				}else{
					examSummaryStr += "\n";
				}
			}
		}
		
		return examSummaryStr;
	}
	
	@Override
	public String cancelExamSummary(ExamInfo examInfo, ExamSummary examSummary, UserDTO user) throws ServiceException {
		if(examInfo.getApptype().equals("2")){//职业病健康总检取消总检
			examInfo.setZyb_final_status("N");// 
			examInfo.setZyb_final_doctor(null);
			examInfo.setZyb_final_time(null);
		}else { //健康体检取消总检
			examInfo.setStatus("J");// 将状态设置成检查中
			examInfo.setFinal_date(null);
			examInfo.setFinal_doctor(null);
		}
		this.pm.update(examInfo);
		
		examSummary.setExam_doctor_id(0);
		examSummary.setFinal_status("J");
		examSummary.setFinal_time(null);
//		examSummary.setFinal_worknum(0);
		this.pm.update(examSummary);
		
		List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+examInfo.getExam_num()+"'");
		if(configlist.size()>0){
			ExamFlowConfig config = configlist.get(0);
			config.setZ1(0);
			config.setZ1creater(user.getUserid());
			config.setZ1date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			
			log.setExam_num(config.getExam_num());
			log.setFlow_code("-z1");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			
			this.pm.save(log);
		}
		return "ok-取消总检成功!";
	}
	
	@Override
	public String canFinalExamInfoByExamNum(ExamSummaryModel model, UserDTO user) throws ServiceException {
		String sql = "select f.id,f.h1,f.z0,e.exam_num,e.wuxuzongjian as z1,e.apptype as edesc from customer_info c,exam_info e left join "
				+ "exam_flow_config f on e.exam_num = f.exam_num where e.customer_id = c.id and f.center_num = '"+user.getCenter_num()+"' and "
				+ "e.is_Active = 'Y' and c.is_Active = 'Y' and (e.exam_num = '"+model.getExam_num()+"' or "
				+ "c.arch_num = '"+model.getExam_num()+"')";
		List<ExamFlowConfig> list = this.jqm.getList(sql, ExamFlowConfig.class);
		if(list.size() == 0){
			return "error-该体检信息不存在!";
		}else {
			List<ExamFlowConfig> newlist = new ArrayList<ExamFlowConfig>();
			for (ExamFlowConfig examFlowConfig : list) {
				if(examFlowConfig.getH1() == 0 || examFlowConfig.getZ0() == 1 || examFlowConfig.getZ1() == 2){
					newlist.add(examFlowConfig);
				}
			}
			if(list.size() == newlist.size()){
				for (ExamFlowConfig examFlowConfig : newlist) {
//					if(examFlowConfig.getEdesc().equals("2")){
//						return "error-该体检信息为职业病检查，不能获取!";
//					}
					if(examFlowConfig.getZ1() == 2){
						return "error-该体检信息无需总检，不能获取!";
					}
					if(examFlowConfig.getH1() == 0){
						return "error-该体检信息导检单未上传，不能获取!";
					}
					if(examFlowConfig.getZ0() == 1){
						return "error-该体检信息已被获取，不能继续获取!";
					}
				}
			}else{
				list.removeAll(newlist);
			}
		}
		for (ExamFlowConfig examFlowConfig : list) {
			ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
			config.setZ0(1);
			config.setZ0creater(user.getUserid());
			config.setZ0date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			log.setExam_num(config.getExam_num());
			log.setFlow_code("z0");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			this.pm.save(log);
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	
	@Override
	public String canFinalExamInfoByExamNumDbgj(ExamSummaryModel model, UserDTO user) throws ServiceException {
		String sql = "select f.id,f.h1,f.z0,f.z0creater,e.exam_num,e.wuxuzongjian as z1,e.apptype as edesc from customer_info c,exam_info e left join "
				+ "exam_flow_config f on e.exam_num = f.exam_num where e.customer_id = c.id and  f.center_num = '"+user.getCenter_num()+"' and "
				+ "e.is_Active = 'Y' and c.is_Active = 'Y' and e.exam_num = '"+model.getExam_num()+"'";
		List<ExamFlowConfig> list = this.jqm.getList(sql, ExamFlowConfig.class);
		if(list.size() == 0){
			return "error-该体检信息不存在!";
		}
		for (ExamFlowConfig examFlowConfig : list) {
			if(examFlowConfig.getId() == 0){
				return "error-该体检信息未回收导检单，请先回收导检单!";
			}else if(examFlowConfig.getZ0() == 1 && examFlowConfig.getZ0creater() == user.getUserid()){
				return "ok-该体检信息本人已获取，查询信息成功!";
			}else if(examFlowConfig.getZ0() == 1 && examFlowConfig.getZ0creater() != user.getUserid()){
				return "error-该体检信息已被获取，不能继续获取!";
			}
			ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
			config.setZ0(1);
			config.setZ0creater(user.getUserid());
			config.setZ0date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			log.setExam_num(config.getExam_num());
			log.setFlow_code("z0");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			this.pm.save(log);
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String canExaminedExamInfo(String num, UserDTO user) throws ServiceException {
		String sql = " select  top "+num+" f.id,f.exam_num from  exam_info e,exam_flow_config f  "
				+" where e.exam_num = f.exam_num   and  f.z1 = 1  and f.c = 0 and f.c1 = 0  and f.center_num = '"+user.getCenter_num()+"' "
				+" order by e.final_date,e.exam_indicator asc";
		List<ExamFlowConfig> list = this.jqm.getList(sql, ExamFlowConfig.class);
		if (list.size() <= 0) {
			return "error-获取失败，没有可总检体检信息!";
		}
		for (ExamFlowConfig examFlowConfig : list) {
			ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
			config.setC1(1);
			config.setC1creater(user.getUserid());
			config.setC1date(DateTimeUtil.parse());
			this.pm.update(config);
			
			ExamFlowLog log = new ExamFlowLog();
			
			log.setExam_num(config.getExam_num());
			log.setFlow_code("c1");
			log.setSendcreater(user.getUserid());
			log.setSenddate(DateTimeUtil.parse());
			log.setAcccreater(0);
			log.setFlow_type(1);
			log.setCenter_num(user.getCenter_num());
			
			this.pm.save(log);
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamInfoUserDTO> getExaminedExamInfoList(String exam_num,UserDTO user,String sort,
			String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		String  sql = "select e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,"
					+ "e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,"
					  +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,"
					  +" convert(varchar(50),e.join_date,23) as join_date,"
					  +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=e.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name "
					  +" from customer_info c,exam_flow_config f,exam_info e "
					   + " left join company_info v on v.id=e.company_id "
					  +" where e.customer_id = c.id and e.is_Active = 'Y' "
					  +" and e.exam_num = f.exam_num and f.c1 = 1 and f.center_num = '"+user.getCenter_num()+"' and f.c = 0 and f.c1creater = "+user.getUserid() 
					  +" and not exists ( select * from exam_summary_reject r where r.done_status = 0 and r.examinfo_id = e.id)";
		if(exam_num != null && !"".equals(exam_num)){
			sql += " and e.exam_num = '"+exam_num+"'";
		}
		if(sort != null && !"".equals(sort)){
			if("exam_num".equals(sort)){
				sql += " order by e.exam_num "+order;
			}else{
				sql += " order by "+sort+" "+order;
			}
		}else{
			sql += " order by e.join_date";
		}
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String canExaminedExamInfoByExamNum(ExamSummaryModel model, UserDTO user) throws ServiceException {
		String sql = "select f.id , e.id ,f.c1,f.z1,f.c ,e.exam_num from   exam_info  e ,exam_flow_config f "
					+" where e.exam_num = f.exam_num and e.is_Active = 'Y' and f.center_num = '"+user.getCenter_num()+"' and e.exam_num = '"+model.getExam_num()+"'";
		List<ExamFlowConfig> list = this.jqm.getList(sql, ExamFlowConfig.class);
		if(list.size() == 0){
			return "error-该体检信息不存在!";
		}else {
			for (ExamFlowConfig examFlowConfig : list) {
				if (examFlowConfig.getZ1() == 0) {
					return "error-该体检信息未总检，不能获取!";
				}else if (examFlowConfig.getC1() == 1) {
					return "error-该体检信息已被获取，不能继续获取!";
				}else if (examFlowConfig.getC() == 1) {
					return "error-该体检信息已审核，不能继续获取!";
				}
				ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
				config.setC1(1);
				config.setC1creater(user.getUserid());
				config.setC1date(DateTimeUtil.parse());
				this.pm.update(config);
				
				ExamFlowLog log = new ExamFlowLog();
				
				log.setExam_num(config.getExam_num());
				log.setFlow_code("c1");
				log.setSendcreater(user.getUserid());
				log.setSenddate(DateTimeUtil.parse());
				log.setAcccreater(0);
				log.setFlow_type(1);
				log.setCenter_num(user.getCenter_num());
				
				this.pm.save(log);
			}
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	@SuppressWarnings("unchecked")
	@Override
	public String canExaminedExamInfoByExamNumDbgj(ExamSummaryModel model, UserDTO user) throws ServiceException {
		String sql = "select f.id,e.status,es.approve_status as app_doctor,f.c1 as ren_type,f.c1creater as creater,e.exam_num from exam_info e left join exam_flow_config f on f.exam_num = e.exam_num  and f.center_num = '"+user.getCenter_num()+"' "
				+ "left join exam_summary es on es.center_num='"+user.getCenter_num()+"'  and es.exam_num = e.exam_num where e.exam_num = '"+model.getExam_num()+"'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		if(list.size() == 0){
			return "error-该体检信息不存在!";
		}else {
			for (ExamInfoDTO examInfoDto : list) {
				if (!examInfoDto.getStatus().equals("Z")) {
					return "error-该体检信息未总检，不能获取!";
				}else if (examInfoDto.getRen_type() == 1 && examInfoDto.getCreater() == user.getUserid()) {
					return "ok-该体检信本人以获取，息查询成功!";
				}else if (examInfoDto.getRen_type() == 1 && examInfoDto.getCreater() != user.getUserid()) {
					return "error-该体检信息已被获取，不能继续获取!";
				}else if (examInfoDto.getApp_doctor().equals("A")) {
					return "error-该体检信息已审核，不能继续获取!";
				}
				ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examInfoDto.getId());
				config.setC1(1);
				config.setC1creater(user.getUserid());
				config.setC1date(DateTimeUtil.parse());
				this.pm.update(config);
				
				ExamFlowLog log = new ExamFlowLog();
				
				log.setExam_num(config.getExam_num());
				log.setFlow_code("c1");
				log.setSendcreater(user.getUserid());
				log.setSenddate(DateTimeUtil.parse());
				log.setAcccreater(0);
				log.setFlow_type(1);
				log.setCenter_num(user.getCenter_num());
				
				this.pm.save(log);
			}
		}
		return "ok-获取成功，获取了"+list.size()+"条体检信息!";
	}
	@Override
	public PageReturnDTO getExamSummaryApproveList(ExamSummaryModel model, UserDTO user, int rows, int page,
			String sort, String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		int count = 0;
		String sql =" select  e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,"
				+ "e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,e.freeze,"
				   +" convert(varchar(50),e.join_date,23) as join_date,convert(varchar(50),e.final_date,23) as final_date, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,ct.type_name as customer_type,"
				   +" (case when (es.exam_doctor_id = 0 or es.exam_doctor_id is null) then '未保存' else u1.chi_name+'已保存' end) as remarke,"
				   +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=e.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name "
				   +" from customer_info c,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.app_type = '1'  and  es.center_num = '"+user.getCenter_num()+"' "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join user_usr u1 on u1.id = es.exam_doctor_id "
				   +" left join customer_type ct on ct.id = e.customer_type_id"
				   +" where e.customer_id = c.id and e.is_Active = 'Y' "
				   +" and es.approve_status = 'A' ";//and f.ccreater = "+user.getUserid();
//		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
//			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
//			count ++;
//		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='"+model.getExam_num().trim()+"')";
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
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		} 
		if (model.getFinal_time1() != null && !"".equals(model.getFinal_time1())) {// 审核开始日期
			sql += " and es.check_time >= '" + model.getFinal_time1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getFinal_time2() != null && !"".equals(model.getFinal_time2())) {// 审核结束日期
			sql += " and es.check_time < '" + model.getFinal_time2() + " 23:59:59.999'";
			count ++;
		}
		if(model.getCustomer_type_id()>0){
			sql +=" and e.customer_type_id = '"+model.getCustomer_type_id()+"'";
			count ++;
		}
		if(model.getCustomer_type()!=null && !"".equals(model.getCustomer_type())){
			sql +=" and e.customer_type = '"+model.getCustomer_type()+"'";
			count ++;
		}
		if(model.getTijianleixin()!=null && !"".equals(model.getTijianleixin())){
			sql += " and  e.exam_type = '"+model.getTijianleixin()+"'   ";
			count ++;
		}
		if(model.getExam_doctor() != null && !"".equals(model.getExam_doctor())){
			sql +=" and es.check_doc in ("+model.getExam_doctor()+")";
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by es.check_time desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public ExamFlowConfig getExamFlogConfig(String exam_num) throws ServiceException {
		List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+exam_num+"'");
		if(configlist.size()>0){
			return configlist.get(0);
		}
		return null;
	}
	@Override
	public String isCensoringExamSummary(ExamFlowConfig config, UserDTO user) throws ServiceException {
		config.setF0(1);
		config.setF0creater(user.getUserid());
		config.setF0date(DateTimeUtil.parse());
		this.pm.update(config);
		ExamFlowLog log = new ExamFlowLog();
		
		log.setExam_num(config.getExam_num());
		log.setFlow_code("f0");
		log.setSendcreater(user.getUserid());
		log.setSenddate(DateTimeUtil.parse());
		log.setAcccreater(0);
		log.setFlow_type(1);
		log.setCenter_num(user.getCenter_num());
		this.pm.save(log);
		return "ok-保存成功!";
	}
	@Override
	public String approveExamSummary(ExamSummaryModel model, ExamSummary examSummary, UserDTO user)
			throws ServiceException {
		if("B".equals(model.getApprove_status())){//审核 将B改为 A
			ExamSummaryLog summarylog = new ExamSummaryLog();
			summarylog.setExam_doctor_id(user.getUserid());
			summarylog.setExam_info_id(examSummary.getExam_info_id());
			summarylog.setExam_date(DateTimeUtil.parse());
			summarylog.setFinal_exam_result(examSummary.getFinal_exam_result());
			summarylog.setCreater(user.getUserid());
			summarylog.setCreate_time(DateTimeUtil.parse());
			summarylog.setOperation_type(2);
			summarylog.setExam_num(model.getExam_num());
			
			this.pm.save(summarylog);
			
			List<ExaminfoDisease> oldDis = this.qm.find("from ExaminfoDisease e where e.exam_num = '"+examSummary.getExam_num() + "' and e.app_type = '"+model.getApp_type()+"'");
			for(ExaminfoDisease examinfoDisease : oldDis){
				this.pm.remove(examinfoDisease);
			}

			List<ExaminfoDiseaseDTO> listDto = model.getExaminfoDisease();
			List<ExaminfoDiseaseDTO> dxlist = model.getDxexaminfoDiseases();
			//单项疾病
			List<ExaminfoDiseaseSingle> disList = this.qm.find("from ExaminfoDiseaseSingle e where e.exam_num = '"+model.getExam_num()+"'");
			for(ExaminfoDiseaseSingle examinfoDisease : disList){
				this.pm.remove(examinfoDisease);
			}
			Set<String> disnum = new HashSet<>();
			for(ExaminfoDiseaseDTO disDto : listDto){
				disnum.add(disDto.getDisease_num());
			}
			
			if (dxlist.size() > 0) {
				for (ExaminfoDiseaseDTO dis : dxlist) {
					if (dis != null) {
						//单项阳性结果是否保存
						if (!"".equals(model.getIs_save_dx_disease()) && "Y".equals(model.getIs_save_dx_disease())) {
							if (!disnum.contains(dis.getDisease_num())) {
								listDto.add(dis);
							}
						}
						
						ExaminfoDiseaseSingle diseaseSingle = new ExaminfoDiseaseSingle();
						diseaseSingle.setCharging_item_code(dis.getCharging_item_code());
						diseaseSingle.setItem_code(dis.getItem_code());
						diseaseSingle.setExam_num(model.getExam_num());
						diseaseSingle.setDep_num(dis.getDep_num());
						diseaseSingle.setDisease_num(dis.getDisease_num());
						diseaseSingle.setDisease_name(dis.getDisease_name());
						diseaseSingle.setCreater(user.getUserid());
						diseaseSingle.setCreate_time(DateTimeUtil.parse());
						this.pm.save(diseaseSingle);
					}
				}
			}
			
			
			int count = 1;
			for(ExaminfoDiseaseDTO disDto : listDto){
				ExaminfoDisease  examinfoDisease = new ExaminfoDisease();
				
				examinfoDisease.setExam_info_id(examSummary.getExam_info_id());
				examinfoDisease.setDisease_id(disDto.getDisease_id());
				examinfoDisease.setDisease_name(disDto.getDisease_name());
				examinfoDisease.setDisease_index(count);
				examinfoDisease.setIsActive("Y");
				examinfoDisease.setCreater(user.getUserid());
				examinfoDisease.setCreate_time(DateTimeUtil.parse());
				examinfoDisease.setUpdater(user.getUserid());
				examinfoDisease.setUpdate_time(DateTimeUtil.parse());
				examinfoDisease.setDisease_type(disDto.getDisease_type());
				examinfoDisease.setIcd_10(disDto.getIcd_10());
				examinfoDisease.setFinal_doc_num(user.getName());
				examinfoDisease.setSuggest(disDto.getSuggest());
				examinfoDisease.setDisease_class(disDto.getDisease_class());
				examinfoDisease.setExam_num(model.getExam_num());
				examinfoDisease.setApp_type(model.getApp_type());
				examinfoDisease.setDisease_description(disDto.getDs());
				examinfoDisease.setDisease_num(disDto.getDisease_num());
				examinfoDisease.setData_source(disDto.getData_source());
//				examinfoDisease.setFinal_remarke(disDto.getFinal_remarke());
//				examinfoDisease.setApprove_remarke(disDto.getApprove_remarke());
				this.pm.save(examinfoDisease);
				
				ExaminfoDiseaseLog diseaselog = new ExaminfoDiseaseLog();
				diseaselog.setSummary_id(summarylog.getId());
				diseaselog.setDisease_id(disDto.getDisease_id());
				diseaselog.setDisease_name(disDto.getDisease_name());
				diseaselog.setDisease_index(count);
				diseaselog.setCreater(user.getUserid());
				diseaselog.setCreate_time(DateTimeUtil.parse());
				diseaselog.setDisease_type(disDto.getDisease_type());
				diseaselog.setIcd_10(disDto.getIcd_10());
				diseaselog.setSuggest(disDto.getSuggest());
				diseaselog.setDisease_class(disDto.getDisease_class());
				diseaselog.setDisease_num(disDto.getDisease_num());
//				diseaselog.setRemarke(disDto.getApprove_remarke());
				this.pm.save(diseaselog);
				
				count ++;
			}
			examSummary.setApprove_status("A");
			examSummary.setCheck_doc(user.getUserid());
			examSummary.setCheck_time(DateTimeUtil.parse());
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			
			if(examSummary.getFinal_worknum() > listDto.size()){
				examSummary.setApprove_worknum(examSummary.getFinal_worknum());
				examSummary.setFinal_worknum(listDto.size());
			}else{
				examSummary.setApprove_worknum(listDto.size());
			}
			
			this.pm.update(examSummary);
			
			List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+model.getExam_num()+"'");
			if(configlist.size()>0){
				ExamFlowConfig config = configlist.get(0);
				config.setC(1);
				config.setCcreater(user.getUserid());
				config.setCdate(DateTimeUtil.parse());
				this.pm.update(config);
				
				ExamFlowLog log = new ExamFlowLog();
				
				log.setExam_num(config.getExam_num());
				log.setFlow_code("c");
				log.setSendcreater(user.getUserid());
				log.setSenddate(DateTimeUtil.parse());
				log.setAcccreater(0);
				log.setFlow_type(1);
				log.setCenter_num(user.getCenter_num());
				
				this.pm.save(log);
			}
			return "ok-审核成功";
		}else if("A".equals(model.getApprove_status())){//取消审核
			
			List<ExamSummaryLog> finallog = this.qm.find("from ExamSummaryLog e where e.operation_type = '1' and e.exam_num = '"+examSummary.getExam_num()+"' order by e.id desc");
			List<ExaminfoDiseaseLog> diseasilog = new ArrayList<ExaminfoDiseaseLog>();
			if(finallog.size() > 0){
				diseasilog = this.qm.find("from ExaminfoDiseaseLog e where e.summary_id = "+finallog.get(0).getId());
			}
			
			examSummary.setApprove_status("B");
			examSummary.setCheck_doc(0);
			examSummary.setCheck_time(null);
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			examSummary.setApprove_worknum(0);
			examSummary.setFinal_worknum(diseasilog.size());
			this.pm.update(examSummary);
			
			List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+model.getExam_num()+"'");
			if(configlist.size()>0){
				ExamFlowConfig config = configlist.get(0);
				config.setC(0);
				config.setCcreater(user.getUserid());
				config.setCdate(DateTimeUtil.parse());
				this.pm.update(config);
				
				ExamFlowLog log = new ExamFlowLog();
				
				log.setExam_num(config.getExam_num());
				log.setFlow_code("-c");
				log.setSendcreater(user.getUserid());
				log.setSenddate(DateTimeUtil.parse());
				log.setAcccreater(0);
				log.setFlow_type(1);
				log.setCenter_num(user.getCenter_num());
				
				this.pm.save(log);
			}
			return "ok-取消审核成功";
		}
		return "ok-成功";
	}
	
	@Override
	public String saveExamInfoDiseaseList(ExamSummaryModel model,ExamSummary examSummary, UserDTO user) throws ServiceException {
		List<ExaminfoDisease> oldDis = this.qm.find("from ExaminfoDisease e where e.exam_num = '"+examSummary.getExam_num() +"' and e.app_type = '"+model.getApp_type()+"'");
		for(ExaminfoDisease examinfoDisease : oldDis){
			this.pm.remove(examinfoDisease);
		}
		
		List<ExaminfoDiseaseDTO> listDto = model.getExaminfoDisease();
		List<ExaminfoDiseaseDTO> dxlist = model.getDxexaminfoDiseases();
		//单项疾病
		List<ExaminfoDiseaseSingle> disList = this.qm.find("from ExaminfoDiseaseSingle e where e.exam_num = '"+model.getExam_num()+"'");
		for(ExaminfoDiseaseSingle examinfoDisease : disList){
			this.pm.remove(examinfoDisease);
		}
		Set<String> disnum = new HashSet<>();
		for(ExaminfoDiseaseDTO disDto : listDto){
			disnum.add(disDto.getDisease_num());
		}
		
		if (dxlist.size() > 0) {
			for (ExaminfoDiseaseDTO dis : dxlist) {
				if (dis != null) {
					//单项阳性结果是否保存
					if (!"".equals(model.getIs_save_dx_disease()) && "Y".equals(model.getIs_save_dx_disease())) {
						if (!disnum.contains(dis.getDisease_num())) {
							listDto.add(dis);
						}
					}
					
					ExaminfoDiseaseSingle diseaseSingle = new ExaminfoDiseaseSingle();
					diseaseSingle.setCharging_item_code(dis.getCharging_item_code());
					diseaseSingle.setItem_code(dis.getItem_code());
					diseaseSingle.setExam_num(model.getExam_num());
					diseaseSingle.setDep_num(dis.getDep_num());
					diseaseSingle.setDisease_num(dis.getDisease_num());
					diseaseSingle.setDisease_name(dis.getDisease_name());
					diseaseSingle.setCreater(user.getUserid());
					diseaseSingle.setCreate_time(DateTimeUtil.parse());
					this.pm.save(diseaseSingle);
				}
			}
		}

		
		int count = 1;
		for(ExaminfoDiseaseDTO disDto : listDto){
			ExaminfoDisease  examinfoDisease = new ExaminfoDisease();
			
			examinfoDisease.setExam_info_id(examSummary.getExam_info_id());
			examinfoDisease.setDisease_id(disDto.getDisease_id());
			examinfoDisease.setDisease_name(disDto.getDisease_name());
			examinfoDisease.setDisease_index(count);
			examinfoDisease.setIsActive("Y");
			examinfoDisease.setCreater(user.getUserid());
			examinfoDisease.setCreate_time(DateTimeUtil.parse());
			examinfoDisease.setUpdater(user.getUserid());
			examinfoDisease.setUpdate_time(DateTimeUtil.parse());
			examinfoDisease.setDisease_type(disDto.getDisease_type());
			examinfoDisease.setIcd_10(disDto.getIcd_10());
			examinfoDisease.setFinal_doc_num(user.getName());
			examinfoDisease.setSuggest(disDto.getSuggest());
			examinfoDisease.setDisease_class(disDto.getDisease_class());
			examinfoDisease.setExam_num(model.getExam_num());
			examinfoDisease.setDisease_description(disDto.getDs());
			examinfoDisease.setApp_type(model.getApp_type());
			examinfoDisease.setDisease_num(disDto.getDisease_num());
			examinfoDisease.setData_source(disDto.getData_source());
//			examinfoDisease.setFinal_remarke(disDto.getFinal_remarke());
//			examinfoDisease.setApprove_remarke(disDto.getApprove_remarke());
//			examinfoDisease.setCensoring_remarke(disDto.getCensoring_remarke());
			this.pm.save(examinfoDisease);
			count ++;
		}
		if("1".equals(model.getExam_status())){
			examSummary.setCheck_doc(user.getUserid());
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
		}else if("2".equals(model.getExam_status())){
			examSummary.setCensoring_doc(user.getUserid());
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			this.pm.update(examSummary);
		}
		return "ok-保存成功!";
	}
	
	@Override
	public PageReturnDTO getExamSummaryCensoringList(ExamSummaryModel model, UserDTO user, int rows, int page,
			String sort, String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		int count = 0;
		String sql =" select  e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,"
				+ "e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,e.freeze,"
				   +" convert(varchar(50),e.join_date,23) as join_date,convert(varchar(50),e.final_date,23) as final_date, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,ct.type_name as customer_type,"
				   +" (case when (es.exam_doctor_id = 0 or es.exam_doctor_id is null) then '未保存' else u1.chi_name+'已保存' end) as remarke ,ur.chi_name as fuser,"
				   +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=e.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name "
				   +" from customer_info c,exam_flow_config f,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.app_type = '1'   AND  es.center_num='"+user.getCenter_num()+"'  "
				   +" left join user_usr ur on ur.id = es.censoring_doc "
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join user_usr u1 on u1.id = es.exam_doctor_id "
				   +" left join customer_type ct on ct.id = e.customer_type_id"
				   +" where f.center_num = '"+user.getCenter_num()+"' and e.customer_id = c.id and e.is_Active = 'Y' "
				   +" and e.exam_num = f.exam_num and f.f = 1 ";//and f.fcreater = "+user.getUserid();
//		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
//			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
//			count ++;
//		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='"+model.getExam_num().trim()+"')";
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
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		} 
		if (model.getFinal_time1() != null && !"".equals(model.getFinal_time1())) {// 终审开始日期
			sql += " and es.censoring_time >= '" + model.getFinal_time1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getFinal_time2() != null && !"".equals(model.getFinal_time2())) {// 终审结束日期
			sql += " and es.censoring_time < '" + model.getFinal_time2() + " 23:59:59.999'";
			count ++;
		}
		if(model.getCustomer_type_id()>0){
			sql +=" and e.customer_type_id = '"+model.getCustomer_type_id()+"'";
			count ++;
		}
		if(model.getCustomer_type()!=null && !"".equals(model.getCustomer_type())){
			sql +=" and e.customer_type = '"+model.getCustomer_type()+"'";
			count ++;
		}
		if(model.getTijianleixin()!=null && !"".equals(model.getTijianleixin())){
			sql += " and  e.exam_type = '"+model.getTijianleixin()+"'   ";
			count ++;
		}
		if(model.getExam_doctor() != null && !"".equals(model.getExam_doctor())){
			sql +=" and es.censoring_doc in ("+model.getExam_doctor()+")";
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
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO getExamSummaryCensoringWList(ExamSummaryModel model, String is_exam_result_canfinal,UserDTO user,int rows,int page,String sort,String order)throws ServiceException{
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		String sql =" select  e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.exam_num,"
				+ "e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,e.freeze,"
				   +" convert(varchar(50),e.join_date,23) as join_date,convert(varchar(50),e.final_date,23) as final_date, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,ct.type_name as customer_type,"
				   +" (case when (es.exam_doctor_id = 0 or es.exam_doctor_id is null) then '未保存' else u1.chi_name+'已保存' end) as remarke,"
				   +" (case when 0<(select COUNT(ese.id) from examinfo_disease ese where ese.disease_name like '★%' and ese.exam_num=e.exam_num and ese.isActive='Y') then '★' else '' end) as disease_name "
				   +" from customer_info c,exam_flow_config f,exam_info e "
				   + " left join company_info v on v.id=e.company_id "
				   +" left join exam_summary es on e.exam_num = es.exam_num and es.app_type = '1'  and  es.center_num = '"+user.getCenter_num()+"'"
				   +" left join user_usr u on u.id = es.check_doc "
				   +" left join user_usr u1 on u1.id = es.exam_doctor_id "
				   +" left join customer_type ct on ct.id = e.customer_type_id"
				   +" where e.customer_id = c.id and e.is_Active = 'Y'  "
				   +" and e.exam_num = f.exam_num and f.center_num = '"+user.getCenter_num()+"' and f.f = 0 ";//and f.fcreater = "+user.getUserid();
		if(is_exam_result_canfinal != null && "3".equals(is_exam_result_canfinal)){
			sql += " and es.approve_status = 'A'";
			if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
				sql += " and c.id_num='" + model.getId_num() + "'";
			}  
			if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
				sql += " and (e.exam_num='" + model.getExam_num().trim() + "' or c.arch_num='"+model.getExam_num().trim()+"')";
			}
			if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
				sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
			}  
			if (model.getTime1() != null && !"".equals(model.getTime1())) {// 体检开始日期
				sql += " and e.join_date >= '" + model.getTime1() + " 00:00:00.000'";
			} 
			if (model.getTime2() != null && !"".equals(model.getTime2())) {// 体检结束日期
				sql += " and e.join_date < '" + model.getTime2() + " 23:59:59.999'";
			} 
			if (model.getCompany_id() > 0) {
				sql += " and e.company_id='" + model.getCompany_id() + "'";
			} 
			if (model.getFinal_time1() != null && !"".equals(model.getFinal_time1())) {// 审核开始日期
				sql += " and es.check_time >= '" + model.getFinal_time1() + " 00:00:00.000'";
			} 
			if (model.getFinal_time2() != null && !"".equals(model.getFinal_time2())) {// 审核结束日期
				sql += " and es.check_time < '" + model.getFinal_time2() + " 23:59:59.999'";
			}
			if(model.getCustomer_type_id()>0){
				sql +=" and e.customer_type_id = '"+model.getCustomer_type_id()+"'";
			}
			if(model.getCustomer_type()!=null && !"".equals(model.getCustomer_type())){
				sql +=" and e.customer_type = '"+model.getCustomer_type()+"'";
			}
			if(model.getTijianleixin()!=null && !"".equals(model.getTijianleixin())){
				sql += " and  e.exam_type = '"+model.getTijianleixin()+"'   ";
			}
			if(model.getExam_doctor() != null && !"".equals(model.getExam_doctor())){
				sql +=" and es.check_doc in ("+model.getExam_doctor()+")";
			}
		}else{
			sql += " and f.f0 = 1";
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public String censoringExamSummary(ExamSummaryModel model, ExamSummary examSummary, UserDTO user)
			throws ServiceException {
		if("0".equals(model.getCensoring_status())){//终审
			ExamSummaryLog summarylog = new ExamSummaryLog();
			summarylog.setExam_doctor_id(user.getUserid());
			summarylog.setExam_info_id(examSummary.getExam_info_id());
			summarylog.setExam_date(DateTimeUtil.parse());
			summarylog.setFinal_exam_result(examSummary.getFinal_exam_result());
			summarylog.setCreater(user.getUserid());
			summarylog.setCreate_time(DateTimeUtil.parse());
			summarylog.setOperation_type(3);
			summarylog.setExam_num(model.getExam_num());
			this.pm.save(summarylog);
			
			List<ExaminfoDisease> oldDis = this.qm.find("from ExaminfoDisease e where e.exam_num = '"+examSummary.getExam_num() + "' and e.app_type = '"+model.getApp_type()+"'");
			for(ExaminfoDisease examinfoDisease : oldDis){
				this.pm.remove(examinfoDisease);
			}
			List<ExaminfoDiseaseDTO> listDto = model.getExaminfoDisease();
			List<ExaminfoDiseaseDTO> dxlist = model.getDxexaminfoDiseases();//单项逻辑
			
			//单项疾病
			List<ExaminfoDiseaseSingle> disList = this.qm.find("from ExaminfoDiseaseSingle e where e.exam_num = '"+model.getExam_num()+"'");
			for(ExaminfoDiseaseSingle examinfoDisease : disList){
				this.pm.remove(examinfoDisease);
			}
			Set<String> disnum = new HashSet<>();
			for(ExaminfoDiseaseDTO disDto : listDto){
				disnum.add(disDto.getDisease_num());
			}
			
			if (dxlist.size() > 0) {
				for (ExaminfoDiseaseDTO disDto : dxlist) {
					if (disDto != null) {
						//单项阳性结果是否保存
						if (!"".equals(model.getIs_save_dx_disease()) && "Y".equals(model.getIs_save_dx_disease())) {
							if (!disnum.contains(disDto.getDisease_num())) {
								listDto.add(disDto);
							}
						}
						
						ExaminfoDiseaseSingle diseaseSingle = new ExaminfoDiseaseSingle();
						diseaseSingle.setCharging_item_code(disDto.getCharging_item_code());
						diseaseSingle.setItem_code(disDto.getItem_code());
						diseaseSingle.setExam_num(model.getExam_num());
						diseaseSingle.setDep_num(disDto.getDep_num());
						diseaseSingle.setDisease_num(disDto.getDisease_num());
						diseaseSingle.setDisease_name(disDto.getDisease_name());
						diseaseSingle.setCreater(user.getUserid());
						diseaseSingle.setCreate_time(DateTimeUtil.parse());
						this.pm.save(diseaseSingle);
					}
				}
			}
			
			int count = 1;
			for(ExaminfoDiseaseDTO disDto : listDto){
				ExaminfoDisease  examinfoDisease = new ExaminfoDisease();
				
				examinfoDisease.setExam_info_id(examSummary.getExam_info_id());
				examinfoDisease.setDisease_id(disDto.getDisease_id());
				examinfoDisease.setDisease_name(disDto.getDisease_name());
				examinfoDisease.setDisease_index(count);
				examinfoDisease.setIsActive("Y");
				examinfoDisease.setCreater(user.getUserid());
				examinfoDisease.setCreate_time(DateTimeUtil.parse());
				examinfoDisease.setUpdater(user.getUserid());
				examinfoDisease.setUpdate_time(DateTimeUtil.parse());
				examinfoDisease.setDisease_type(disDto.getDisease_type());
				examinfoDisease.setIcd_10(disDto.getIcd_10());
				examinfoDisease.setFinal_doc_num(user.getName());
				examinfoDisease.setSuggest(disDto.getSuggest());
				examinfoDisease.setDisease_class(disDto.getDisease_class());
				examinfoDisease.setDisease_description(disDto.getDs());
				examinfoDisease.setExam_num(model.getExam_num());
				examinfoDisease.setApp_type(model.getApp_type());
				examinfoDisease.setDisease_num(disDto.getDisease_num());
				examinfoDisease.setData_source(disDto.getData_source());
//				examinfoDisease.setFinal_remarke(disDto.getFinal_remarke());
//				examinfoDisease.setApprove_remarke(disDto.getApprove_remarke());
//				examinfoDisease.setCensoring_remarke(disDto.getCensoring_remarke());
				this.pm.save(examinfoDisease);
				
				ExaminfoDiseaseLog diseaselog = new ExaminfoDiseaseLog();
				diseaselog.setSummary_id(summarylog.getId());
				diseaselog.setDisease_id(disDto.getDisease_id());
				diseaselog.setDisease_name(disDto.getDisease_name());
				diseaselog.setDisease_index(count);
				diseaselog.setCreater(user.getUserid());
				diseaselog.setCreate_time(DateTimeUtil.parse());
				diseaselog.setDisease_type(disDto.getDisease_type());
				diseaselog.setIcd_10(disDto.getIcd_10());
				diseaselog.setSuggest(disDto.getSuggest());
				diseaselog.setDisease_class(disDto.getDisease_class());
				diseaselog.setDisease_num(disDto.getDisease_num());
//				diseaselog.setRemarke(disDto.getCensoring_remarke());
				this.pm.save(diseaselog);
				
				count ++;
			}
			examSummary.setCensoring_status("1");
			examSummary.setCensoring_doc(user.getUserid());
			examSummary.setCensoring_time(DateTimeUtil.parse());
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			
			if(examSummary.getApprove_worknum() > listDto.size()){
				examSummary.setCensoring_worknum(examSummary.getApprove_worknum());
				examSummary.setApprove_worknum(listDto.size());
			}else{
				examSummary.setCensoring_worknum(listDto.size());
			}
			
			this.pm.update(examSummary);
			
			List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+model.getExam_num()+"'");
			if(configlist.size()>0){
				ExamFlowConfig config = configlist.get(0);
				config.setF(1);
				config.setFcreater(user.getUserid());
				config.setFdate(DateTimeUtil.parse());
				this.pm.update(config);
				
				ExamFlowLog log = new ExamFlowLog();
				
				log.setExam_num(config.getExam_num());
				log.setFlow_code("f");
				log.setSendcreater(user.getUserid());
				log.setSenddate(DateTimeUtil.parse());
				log.setAcccreater(0);
				log.setFlow_type(1);
				log.setCenter_num(user.getCenter_num());
				
				this.pm.save(log);
			}
			return "ok-终审成功";
		}else if("1".equals(model.getCensoring_status())){//取消审核
			List<ExamSummaryLog> finallog = this.qm.find("from ExamSummaryLog e where e.operation_type = '1' and e.exam_num = '"+examSummary.getExam_num()+"' order by e.id desc");
			List<ExaminfoDiseaseLog> finallist = new ArrayList<ExaminfoDiseaseLog>();
			if(finallog.size() > 0){
				finallist = this.qm.find("from ExaminfoDiseaseLog e where e.summary_id = "+finallog.get(0).getId());
			}
			
			List<ExamSummaryLog> applog = this.qm.find("from ExamSummaryLog e where e.operation_type = '2' and e.exam_num = '"+examSummary.getExam_num()+"' order by e.id desc");
			List<ExaminfoDiseaseLog> applist = new ArrayList<ExaminfoDiseaseLog>();
			if(applog.size() > 0){
				applist = this.qm.find("from ExaminfoDiseaseLog e where e.summary_id = "+applog.get(0).getId());
			}
			
			examSummary.setCensoring_status("0");
			examSummary.setCensoring_doc(0);
			examSummary.setCensoring_time(null);
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			examSummary.setCensoring_worknum(0);
			if(finallist.size() > applist.size()){
				examSummary.setApprove_worknum(finallist.size());
			}else{
				examSummary.setApprove_worknum(applist.size());
			}
			this.pm.update(examSummary);
			
			List<ExamFlowConfig> configlist = this.qm.find("from ExamFlowConfig where exam_num = '"+model.getExam_num()+"'");
			if(configlist.size()>0){
				ExamFlowConfig config = configlist.get(0);
				config.setF(0);
				config.setFcreater(user.getUserid());
				config.setFdate(DateTimeUtil.parse());
				this.pm.update(config);
				
				ExamFlowLog log = new ExamFlowLog();
				
				log.setExam_num(config.getExam_num());
				log.setFlow_code("-f");
				log.setSendcreater(user.getUserid());
				log.setSenddate(DateTimeUtil.parse());
				log.setAcccreater(0);
				log.setFlow_type(1);
				log.setCenter_num(user.getCenter_num());
				
				this.pm.save(log);
			}
			return "ok-取消终审成功";
		}
		return "ok-成功";
	}
	
	@Override
	public String saveExamSummaryCancel(long summary_id,String exam_num,long cancel_type,UserDTO user) throws ServiceException{
		ExamSummary examSummary = (ExamSummary) this.qm.load(ExamSummary.class, summary_id);
		ExamInfo examInfo = (ExamInfo) this.qm.load(ExamInfo.class, examSummary.getExam_info_id());
		if(cancel_type == 1){			
			ExamSummaryCancel examSummaryCancel = new ExamSummaryCancel();
			examSummaryCancel.setExam_num(exam_num);
			examSummaryCancel.setCancel_type(1);
			examSummaryCancel.setFinal_status(examInfo.getStatus());
			examSummaryCancel.setApprove_status(examSummary.getApprove_status());
			examSummaryCancel.setCensoring_status(examSummary.getCensoring_status());
			examSummaryCancel.setCreater(user.getUserid());
			examSummaryCancel.setCreate_time(DateTimeUtil.parse());
			this.pm.save(examSummaryCancel);
			
			examInfo.setStatus("J");
			this.pm.update(examInfo);
			
			examSummary.setCancel_type(1);
			examSummary.setApprove_status("B");
			examSummary.setCensoring_status("0");
			this.pm.update(examSummary);
			
			return "ok-总检一键取消状态成功!";
		}else{//一键恢复
			List<ExamSummaryCancel> listCancel = this.qm.find("from ExamSummaryCancel e where e.exam_num = '"+exam_num+"' order by e.create_time desc");
			ExamSummaryCancel examSummaryCancel = new ExamSummaryCancel();
			examSummaryCancel.setExam_num(exam_num);
			examSummaryCancel.setCancel_type(0);
			examSummaryCancel.setFinal_status(examInfo.getStatus());
			examSummaryCancel.setApprove_status(examSummary.getApprove_status());
			examSummaryCancel.setCensoring_status(examSummary.getCensoring_status());
			examSummaryCancel.setCreater(user.getUserid());
			examSummaryCancel.setCreate_time(DateTimeUtil.parse());
			this.pm.save(examSummaryCancel);
			ExamSummaryCancel oldCancel = listCancel.get(0);
			examInfo.setStatus(oldCancel.getFinal_status());
			this.pm.update(examInfo);
			examSummary.setCancel_type(0);
			examSummary.setApprove_status(oldCancel.getApprove_status());
			examSummary.setCensoring_status(oldCancel.getCensoring_status());
			this.pm.update(examSummary);
			ExamSummaryLog summarylog = new ExamSummaryLog();
			summarylog.setExam_doctor_id(user.getUserid());
			summarylog.setExam_info_id(examSummary.getExam_info_id());
			summarylog.setExam_date(DateTimeUtil.parse());
			summarylog.setFinal_exam_result(examSummary.getFinal_exam_result());
			summarylog.setCreater(user.getUserid());
			summarylog.setCreate_time(DateTimeUtil.parse());
			summarylog.setOperation_type(4);
			summarylog.setExam_num(examSummary.getExam_num());
			this.pm.save(summarylog);
			List<ExaminfoDisease> diseaseList = this.qm.find("from ExaminfoDisease e where e.exam_num = '"+examInfo.getExam_num()+"' and e.app_type='1' order by e.disease_index");
			
			for(ExaminfoDisease examinfoDisease : diseaseList){
				ExaminfoDiseaseLog diseaselog = new ExaminfoDiseaseLog();
				diseaselog.setSummary_id(summarylog.getId());
				diseaselog.setDisease_id(examinfoDisease.getDisease_id());
				diseaselog.setDisease_name(examinfoDisease.getDisease_name());
				diseaselog.setDisease_index(examinfoDisease.getDisease_index());
				diseaselog.setCreater(user.getUserid());
				diseaselog.setCreate_time(DateTimeUtil.parse());
				diseaselog.setDisease_type(examinfoDisease.getDisease_type());
				diseaselog.setIcd_10(examinfoDisease.getIcd_10());
				diseaselog.setSuggest(examinfoDisease.getSuggest());
				diseaselog.setDisease_class(examinfoDisease.getDisease_class());
				diseaselog.setRemarke("");
				diseaselog.setDisease_num(examinfoDisease.getDisease_num());
				this.pm.save(diseaselog);
			}
			
			return "ok-总检一键恢复状态成功!";
		}
	}
	
	@Override
	public List<ExaminfoDiseaseDTO> getDefalutDiseaseList(String exam_num,String isExamSuggest,String defalutDiseaseId,String center_num,String sug_center) throws ServiceException {
		String emInfoSql = " select e.id,c.sex,e.age from exam_info e,customer_info c "
			     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";

		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if(infoDtoList.size() == 0){
			return null;
		}else{
			examInfo = infoDtoList.get(0);
		}
		List list = StringUtil.getCheckStringInt(defalutDiseaseId);
		String[] arr = (String[]) list.toArray(new String[list.size()]);
		String diseaseIds = StringUtils.join(arr, ",");
		String diseaseSql = " select distinct d.id as disease_id,d.disease_name,d.disease_type,dd.seq_code as disease_index,d.icd_10 "
						  + " from disease_knowloedge_lib d left join data_dictionary dd on dd.id = d.disease_level where d.id "
						  + " in ("+diseaseIds+") order by dd.seq_code";
		List<ExaminfoDiseaseDTO> diseaseList = this.jqm.getList(diseaseSql, ExaminfoDiseaseDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表", diseaseSql);
		int dis_count = 0;
		for(ExaminfoDiseaseDTO disese : diseaseList){
			
			String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
						  + " where d.disease_id = "+ disese.getDisease_id() +" and d.is_active = 'Y'"
						  + " and d.minAge <= "+examInfo.getAge()+" and d.maxAge >= "+examInfo.getAge()
						  + " and (d.sex = '"+examInfo.getSex()+"' or d.sex = '全部')";
			if("Y".equals(sug_center)){
				sugsql += " and d.center_num = '"+center_num+"'";
			}
			List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
			disese.setDisease_index(dis_count);
			if(sugList.size() == 1){
				if("Y".equals(isExamSuggest)){
					disese.setSuggest(disese.getDisease_name() +"： "+sugList.get(0).getDisease_suggestion());
				}else{
					disese.setSuggest(sugList.get(0).getDisease_suggestion());
				}
			}else{
				for(DiseaseKnowloedgeDTO dkLib : sugList){
					if("是".equals(dkLib.getDefault_value())){
						if("Y".equals(isExamSuggest)){
							disese.setSuggest(disese.getDisease_name() +"： "+dkLib.getDisease_suggestion());
						}else{
							disese.setSuggest(dkLib.getDisease_suggestion());
						}
					}
				}
			}
			
			String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="+disese.getDisease_id();
			List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
			if(descList.size() > 0){
				disese.setDisease_description(descList.get(0).getDisease_description());
			}
			dis_count ++;
		}
		return diseaseList;
	}
	@Override
	public String saveExamSummaryRehectInfo(ExamSummaryModel model, UserDTO user) throws ServiceException {
		List<ExamSummaryReject> list = this.qm.find("from ExamSummaryReject e where e.done_status = 0 and e.examinfo_id = " + model.getExam_info_id());
		ExamSummary examSummary = this.getExamSummaryById(model.getExam_num(),"1");
		if("1".equals(model.getOperation_type())){
			List<ExamFlowConfig> flowList = this.qm.find("from ExamFlowConfig where exam_num = '" + model.getExam_num() + "' and center_num='" + user.getCenter_num() + "'");
			if(flowList.size() > 0){
				ExamFlowConfig examFlowConfig = flowList.get(0);
				if(examFlowConfig.getC1() == 1){
					return "error-已发送给审核医生，不能继续驳回！";
				}
			}
		}
		if(list.size() > 0){
			return "error-该总检已驳回，总检医生未处理，不能继续驳回！";
		}
		ExamSummaryReject examSummaryReject = new ExamSummaryReject();
		examSummaryReject.setExaminfo_id(model.getExam_info_id());
		examSummaryReject.setReject_context(model.getFinal_exam_result());
		examSummaryReject.setReject_doctor(user.getUserid());
		examSummaryReject.setReject_time(DateTimeUtil.parse());
		examSummaryReject.setDone_status(0);
		this.pm.save(examSummaryReject);
		
		SystemInforms informs = new SystemInforms();
		String inform_content = "您有一份已总检病例被驳回请及时处理。体检号为：【"+model.getExam_num()+"】，驳回原因："+model.getFinal_exam_result()+"。";
		informs.setInform_content(inform_content);
		informs.setIs_active("Y");
		informs.setCreater(user.getUserid());
		informs.setCreate_time(DateTimeUtil.parse());
		informs.setUpdater(user.getUserid());
		informs.setUpdate_time(DateTimeUtil.parse());
		this.pm.save(informs);
		SystemInforms_user informs_user = new SystemInforms_user();
		informs_user.setInforms_id(informs.getId());
		informs_user.setUser_id(examSummary.getExam_doctor_id());
		informs_user.setReader_flag(0);
		informs_user.setCreater(user.getUserid());
		informs_user.setCreate_time(DateTimeUtil.parse());
		informs_user.setUpdater(user.getUserid());
		informs_user.setUpdate_time(DateTimeUtil.parse());
		this.pm.save(informs_user);
		return "ok-驳回总检成功!";
	} 
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExamInfoDTO> gettjbgList(String exam_num,String app_type,String isShowSamplename) throws ServiceException {
		String sql = "select DJD_path from exam_info where exam_num='"+exam_num+"'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getAlreadyRejectList(ExamSummaryModel model, UserDTO user, int rows, int page, String sort,
			String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		int count = 0;
		String sql =" select c.arch_num,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,e.age,c.sex,e.exam_num,r.examinfo_id,e.join_date,e.final_date,e.final_doctor,"
				+ "e.exam_type,dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,u.chi_name reject_doctor,"
				+ "r.reject_time,r.reject_context,r.done_status,r.done_time,e.freeze from customer_info c,exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				+ ",exam_summary s,exam_summary_reject r left join user_usr u on u.id = r.reject_doctor "
				+ "where c.id = e.customer_id and e.exam_num = s.exam_num  and  s.center_num='"+user.getCenter_num()+"' "
						+ "and e.id = r.examinfo_id and s.app_type ='1'"
				+ " and r.reject_doctor = "+ user.getUserid();
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name like '" + model.getUser_name().trim() + "%'";
			count ++;
		}  
		if (model.getTime1() != null && !"".equals(model.getTime1())) {//驳回日期
			sql += " and r.reject_time >= '" + model.getTime1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 驳回结束
			sql += " and r.reject_time < '" + model.getTime2() + " 23:59:59.999'";
			count ++;
		}
		if(model.getExam_status() != null && !"".equals(model.getExam_status())){
			sql += " and r.done_status = " + model.getExam_status();
			count ++;
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by r.reject_time desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql, page, rows, ExamSummaryRejectDTO.class);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO getExamSummaryDistributionList(ExamSummaryModel model,UserDTO user, int rows, int page, String sort,String order) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断是否有隐私资源  Y有 N无
		String sql =" select e.id,c.arch_num,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,"
				+ "e.exam_num,e.age,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,e.exam_type,"
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,"
				   +" convert(varchar(50),e.join_date,23) as join_date"
				   +" from customer_info c,exam_flow_config f ,exam_info e"
				   + " left join company_info v on v.id=e.company_id "
				   +" where e.customer_id = c.id and e.is_Active = 'Y'  and f.center_num = '"+user.getCenter_num()+"' "
				   +" and e.exam_num = f.exam_num "
				   +"and f.z0 = 0 and e.wuxuzongjian = 0 and e.exam_type = 'T' and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 and not exists "
				   + "(select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where ec.charging_item_code = c.item_code "
				   + "and c.dep_id = d.id and d.dep_num = 'EO' and e.exam_num = ec.exam_num and ec.center_num = '"+user.getCenter_num()+"')";
		
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}else{
			sql += " order by e.join_date desc";
		}
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows, ExamInfoUserDTO.class);
		
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public String saveExamSummaryDistribution(String exam_nums, long doctorid, UserDTO user) throws ServiceException {
		if(doctorid <= 0){
			return "error-未选择分配医生，操作不能继续";
		}
		int fpcount = 0;
		int yhcount = 0;
		String[] exam_num = exam_nums.split(",");
		for (int i = 0; i < exam_num.length; i++) {
			List<ExamFlowConfig> cofiglist = this.qm.find("from ExamFlowConfig f where f.exam_num = '"+exam_num[i]+"'");
			if(cofiglist.size() > 0){
				ExamFlowConfig config = cofiglist.get(0);
				if(config.getZ0() == 1){
					yhcount ++;
				}else{
					config.setZ0(1);
					config.setZ0creater(doctorid);
					config.setZ0date(DateTimeUtil.parse());
					this.pm.update(config);
					
					ExamFlowLog log = new ExamFlowLog();
					
					log.setExam_num(config.getExam_num());
					log.setFlow_code("z0");
					log.setSendcreater(doctorid);
					log.setSenddate(DateTimeUtil.parse());
					log.setAcccreater(0);
					log.setFlow_type(1);
					log.setCenter_num(user.getCenter_num());
					
					this.pm.save(log);
					fpcount ++;
				}
			}
		}
		return "ok-成功分配"+fpcount+"人，已被获取"+yhcount+"人!";
	}
	
	
	/**
	 * 
	 * @Title: getRejectList @Description: 得到驳回意见列表  @throws ServiceException @return:
	 *         TreeDTO @throws
	 */
	public List<FinalRejectionDTO> getRejectList() throws ServiceException {
		String sql = "select id,reject_context From  exam_summary_reject_lib where is_active='Y'";
		Connection connection = null;
		Statement statement = null;
		List<FinalRejectionDTO> list = new ArrayList<FinalRejectionDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			while (rs.next()) {
				FinalRejectionDTO jd = new FinalRejectionDTO();
				jd.setId(rs.getLong("id"));
				jd.setReject_context(rs.getString("reject_context"));
				list.add(jd);
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
		return list;
	}

	@Override
	public String saveExamSummaryDistributionByDoctor(String userids, long counts, UserDTO user)
			throws ServiceException {
		String[] userid = userids.split(",");
		for (int i = 0; i < userid.length; i++) {
			List<ExamFlowConfig> list = new ArrayList<ExamFlowConfig>();
			//获取总检贵宾客户权限资源
			String resource = null;
			String sql = "select w.datavalue from WEB_RESRELATIONSHIP w where w.res_code = 'RS050' and userorroleid = '"+userid[i]+"'";
			List<WebResrelAtionship> web = this.jqm.getList(sql, WebResrelAtionship.class);
			if(web.size() > 0){
				resource = web.get(0).getDatavalue();
			}
			long nums = counts;
			//是否拥有总检贵宾客户权限
			if(resource != null && "1".equals(resource)){
				//贵宾体检最优先 金额大于5000 判断条件 vipflag = 1
				sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 and f.center_num = '"+user.getCenter_num()+"' "
					+ "and f.center_num = '"+user.getCenter_num()+"' and e.wuxuzongjian = 0 and e.vipflag = 1 and f.s1 = 1 and f.z1 = 0 order by e.join_date,e.exam_num";
				List<ExamFlowConfig> gblist = this.jqm.getList(sql, ExamFlowConfig.class);
				list.addAll(gblist);
				nums = nums - gblist.size();//获取总数减去以获取人数
			}
			//判断存在ttm项目的由TTM检查医生做主检
			String dep_num = "EO";//TTM科室编码
			if(resource != null && "2".equals(resource)){
				if(nums > 0){
					sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 "
							+ "and e.wuxuzongjian = 0 and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 "
							+ "and exists (select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where "
							+ "ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_num = '"+dep_num+"' and ec.exam_status = 'Y' and ec.center_num = '"+user.getCenter_num()+"' "
							+ "and ec.exam_doctor_name = '"+user.getName()+"' and e.exam_num = ec.exam_num) order by e.join_date,e.exam_num";
					List<ExamFlowConfig> ttmlist = this.jqm.getList(sql, ExamFlowConfig.class);
					list.addAll(ttmlist);
					nums = nums - ttmlist.size();//获取总数减去以获取人数
				}
			}
			//判断个人体检由开单医生做主检
			if(resource != null && "3".equals(resource)){
				if(nums > 0){
					sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0  and f.center_num = '"+user.getCenter_num()+"'  "
							+ "and e.wuxuzongjian = 0 and e.exam_type = 'G' and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 "
							+ "and not exists (select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where "
							+ "ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_num = '"+dep_num+"' "
							+ "and e.exam_num = ec.exam_num and ec.center_num = '"+user.getCenter_num()+"') and e.creater = '"+user.getUserid()+"' order by e.join_date,e.exam_num";
					List<ExamFlowConfig> grlist = this.jqm.getList(sql, ExamFlowConfig.class);
					list.addAll(grlist);
					nums = nums - grlist.size();//获取总数减去以获取人数
				}
			}
			
			//判断团检体检按上限自由获取
			if(resource == null || "1".equals(resource) || "2".equals(resource)){
				if(nums > 0){
					sql = "select top "+nums+" f.id,f.exam_num from exam_info e,exam_flow_config f where e.exam_num = f.exam_num and f.z0 = 0 and f.center_num = '"+user.getCenter_num()+"' "
							+ "and e.wuxuzongjian = 0 and e.exam_type = 'T' and e.vipflag = 0 and f.s1 = 1 and f.z1 = 0 "
							+ "and not exists (select ec.* from examinfo_charging_item ec ,charging_item c,department_dep d where "
							+ "ec.charging_item_code = c.item_code and c.dep_id = d.id and d.dep_num = '"+dep_num+"' "
							+ "and e.exam_num = ec.exam_num and ec.center_num = '"+user.getCenter_num()+"') order by e.join_date,e.exam_num";
					List<ExamFlowConfig> ttlist = this.jqm.getList(sql, ExamFlowConfig.class);
					list.addAll(ttlist);
					nums = nums - ttlist.size();//获取总数减去以获取人数
				}
			}
			
			for (ExamFlowConfig examFlowConfig : list) {
//				ExamFlowConfig config = (ExamFlowConfig) this.qm.load(ExamFlowConfig.class, examFlowConfig.getId());
//				config.setZ0(1);
//				config.setZ0creater(Long.valueOf(userid[i]));
//				config.setZ0date(DateTimeUtil.parse());
//				this.pm.update(config);
				sql = "update exam_flow_config set z0=1,z0creater="+userid[i]+",z0date='"+DateTimeUtil.getDateTime()+"' where f.center_num = '"+user.getCenter_num()+"' and id = " + examFlowConfig.getId();
				this.jpm.executeSql(sql);
//				ExamFlowLog log = new ExamFlowLog();
//				
//				log.setExam_num(config.getExam_num());
//				log.setFlow_code("z0");
//				log.setSendcreater(user.getUserid());
//				log.setSenddate(DateTimeUtil.parse());
//				log.setAcccreater(0);
//				log.setFlow_type(1);
//				log.setCenter_num(user.getCenter_num());
//				this.pm.save(log);
				
				sql = "insert into exam_flow_log (exam_num,flow_code,sendcreater,senddate,acccreater,flow_type,center_num) "
					+ "values('"+examFlowConfig.getExam_num()+"','z0','"+userid[i]+"','"+DateTimeUtil.getDateTime()+"','0','1','"+user.getCenter_num()+"')";
				this.jpm.executeSql(sql);
			}
		}
		return "ok-主动派发成功!";
	}
	
	
	//生成复合疾病逻辑
		@SuppressWarnings("all")
		@Override
		public List<ExaminfoDiseaseDTO> createExamInfoCompositeDisease(String exam_num,String center_num,String sug_center) throws ServiceException {
			
			String emInfoSql = " select e.id,c.sex,e.age from exam_info e,customer_info c "
						     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";
			
			List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
			ExamInfoDTO examInfo = null;
			if(infoDtoList.size() == 0){
				return null;
			}else{
				examInfo = infoDtoList.get(0);
			}
			
			String sql = "select c.item_code ,c.exam_result from common_exam_detail c " 
					   +" where c.exam_num = '"+exam_num+"'" 
					   +" union all "
					   +" select ed.item_code,ed.exam_result from exam_result_detail ed "
					   +" where ed.exam_num = '"+exam_num+"'"
					   +" union all"
					   +" select ei.item_num as item_code,v.exam_result "
					   +" from view_exam_detail v,pacs_summary ps , charging_item ci , charging_item_exam_item  ciei , examination_item ei "
					   +" where ci.item_code = ciei.charging_item_code and ei.item_num = ciei.item_code and "
					   +" v.pacs_req_code = ps.pacs_req_code  and ps.charging_item_code = ci.item_code and v.exam_num = '"+exam_num+"'";	
			List<DepExamResultDTO> ptAndHyResultList = this.jqm.getList(sql, DepExamResultDTO.class);//查询普通科室与检验科的检查结果
			//查询 已经生成的单项阳性
			String  oldDisease = "select  disease_num from  examinfo_disease_single where exam_num = '"+exam_num+"'";
			List<DiseaseLogicDTO> oldList = this.jqm.getList(oldDisease, DiseaseLogicDTO.class);
			
			//获取所有的逻辑列表
		/*	String  ljsql = "select  dc.id,dc.logic_composite_item_id,dc.diseaseOrItem_num,dc.condition_type,dc.logic_index,dc.condition_value,dc.condition "
					      + "from  disease_logic_composite_item   d , disease_logic_composite_item_condition dc  where d.id = dc.logic_composite_item_id   and d.isActive = 'Y'";
			
			List<DiseaseLogicCompositeItemCondition> dlccList = this.jqm.getList(ljsql,DiseaseLogicCompositeItemCondition.class);
			
			Set<String> set=new HashSet<>();
			if (dlccList.size() > 0) {
				for (DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition : dlccList) {
					if(ptAndHyResultList.size() > 0){
						for (DepExamResultDTO depExamResultDTO : ptAndHyResultList) {
							if(!"".equals(depExamResultDTO.getItem_code()) && depExamResultDTO.getItem_code() != null && depExamResultDTO.getItem_code().trim().equals(diseaseLogicCompositeItemCondition.getDiseaseOrItem_num().trim())){
								if(convertLogic(depExamResultDTO, diseaseLogicCompositeItemCondition)){
									set.add(diseaseLogicCompositeItemCondition.getLogic_composite_item_id());
								}
							}
						}
					}
					if(oldList.size() > 0){
						for (DiseaseLogicDTO diseaseLogicDTO : oldList) {
							if(diseaseLogicDTO.getDisease_num().equals(diseaseLogicCompositeItemCondition.getDiseaseOrItem_num())){
								set.add(diseaseLogicCompositeItemCondition.getLogic_composite_item_id());
							}
						}
					}
					
				}
			}
			Set<String> dset=new HashSet<>();
			//查找复合逻辑
			if(set.size() > 0){
				for (String num : set) {
					String sqllist = "select  * from disease_logic_composite_item_condition  where logic_composite_item_id = '"+num+"'"; 
					List<DiseaseLogicCompositeItemCondition> list = this.jqm.getList(sqllist, DiseaseLogicCompositeItemCondition.class);
					if (list.size() > 0) {
						int count = 0;
						for (DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition : list) {
							if(ptAndHyResultList.size() > 0){
								for (DepExamResultDTO depExamResultDTO : ptAndHyResultList) {
									if(depExamResultDTO.getItem_code().equals(diseaseLogicCompositeItemCondition.getDiseaseOrItem_num())){
										if(convertLogic(depExamResultDTO, diseaseLogicCompositeItemCondition)){
											count++;
										}
									}
								}
							}
							if (oldList.size() > 0) {
								for (DiseaseLogicDTO diseaseLogicDTO : oldList) {
									if(diseaseLogicDTO.getDisease_num().equals(diseaseLogicCompositeItemCondition.getDiseaseOrItem_num())){
										count++;
									}
								}
							}
							
							if(list.size() == count ){
								String dsql = "select  d.*  from disease_logic_composite  d ,disease_logic_composite_item dc  "
										    + "where dc.logic_composite_id = d.id and d.isActive = 'Y' and dc.id = '"+diseaseLogicCompositeItemCondition.getLogic_composite_item_id()+"'";
								List<DiseaseLogicComposite> list2 = this.jqm.getList(dsql, DiseaseLogicComposite.class);
								if (list2.size()> 0) {
									for (DiseaseLogicComposite diseaseLogicComposite : list2) {
										if (!diseaseLogicComposite.getSex().equals(examInfo.getSex()) && !diseaseLogicComposite.getSex().equals("全部")) {
											continue;
										}
										if(( examInfo.getAge() > Long.valueOf(diseaseLogicComposite.getAge_max())) 
												|| ( examInfo.getAge() < Long.valueOf(diseaseLogicComposite.getAge_min()))){
											continue;
										}
										dset.add(diseaseLogicComposite.getDisease_num());
									}
								}
							}
						}
					}
				}
			}*/
			
			
			
			//获取所有满足主表的 num
			String logicsql = "select d.disease_num,d.id  from disease_logic_composite d where "
					+ "d.isActive = 'Y' "
					+ "and d.sex in ('全部','"+examInfo.getSex()+"') and d.age_min <= '"+examInfo.getAge()+"' and d.age_max >= '"+examInfo.getAge()+"'";
			List<DiseaseLogicComposite> logiclist = this.jqm.getList(logicsql, DiseaseLogicComposite.class);
			
			//
			Set<String> dset=new HashSet<>();
			if(logiclist.size() > 0){
				for (DiseaseLogicComposite diseaseLogicComposite : logiclist) {
					String sql1 = "select  * from disease_logic_composite_item where logic_composite_id = '"+diseaseLogicComposite.getId()+"' and  isActive = 'Y'";
					List<DiseaseLogicCompositeItem> logiclist1 = this.jqm.getList(sql1, DiseaseLogicCompositeItem.class);
					if (logiclist1.size() > 0) {
						for (DiseaseLogicCompositeItem diseaseLogicCompositeItem : logiclist1) {
							String sql2 = "select * from  disease_logic_composite_item_condition    where   logic_composite_item_id = '"+diseaseLogicCompositeItem.getId()+"'";
							List<DiseaseLogicCompositeItemCondition> logiclist2 = this.jqm.getList(sql2, DiseaseLogicCompositeItemCondition.class);
							int count = 0;
							if (logiclist2.size() > 0) {
								for (DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition2 : logiclist2) {
									for (DepExamResultDTO depExamResultDTO : ptAndHyResultList) {
										if (1 == diseaseLogicCompositeItemCondition2.getCondition_type() && depExamResultDTO.getItem_code().trim().equals(diseaseLogicCompositeItemCondition2.getDiseaseOrItem_num().trim())) {
											if (convertLogic(depExamResultDTO, diseaseLogicCompositeItemCondition2)) {
												count++;
											}
										}
									}
									if (oldList.size() > 0) {
										for (DiseaseLogicDTO diseaseLogicDTO : oldList) {
											if (0 == diseaseLogicCompositeItemCondition2.getCondition_type() && diseaseLogicDTO.getDisease_num().trim().equals(diseaseLogicCompositeItemCondition2.getDiseaseOrItem_num().trim())) {
												count++;
											}
										}
									}
									
								}
								if (count > 0 && count ==logiclist2.size()) {
									dset.add(diseaseLogicComposite.getDisease_num());
								}
							}
						}
						
					}
				}
			}
			
			
			
			
			
			//查询疾病 和建议 
			String diseaseIds = "";
			for (String disnum : dset) {
				diseaseIds += "'"+disnum+"',";
			}
//			System.out.println("-------"+diseaseIds+"-------");
			if (!"".equals(diseaseIds) ) {
				diseaseIds=diseaseIds.substring(0, diseaseIds.length()-1);
				String diseaseSql = " select distinct d.id as disease_id,d.disease_name,d.disease_type,dd.seq_code as disease_index,d.icd_10,d.disease_num,0 as data_source "
						  + " from disease_knowloedge_lib d left join data_dictionary dd on dd.id = d.disease_level where d.disease_num "
						  + " in ("+diseaseIds+") order by dd.seq_code";
				List<ExaminfoDiseaseDTO> diseaseList = this.jqm.getList(diseaseSql, ExaminfoDiseaseDTO.class);
				int dis_count = 0;
				for(ExaminfoDiseaseDTO disese : diseaseList){
					
					String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
								  + " where d.disease_id = "+ disese.getDisease_id() +" and d.is_active = 'Y'"
								  + " and d.minAge <= "+examInfo.getAge()+" and d.maxAge >= "+examInfo.getAge()
								  + " and (d.sex = '"+examInfo.getSex()+"' or d.sex = '全部')";
					if("Y".equals(sug_center)){
						sugsql += " and d.center_num = '"+center_num+"'";
					}
					List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
					disese.setDisease_index(dis_count);
					if(sugList.size() == 1){
							disese.setSuggest(sugList.get(0).getDisease_suggestion());
					}else{
						for(DiseaseKnowloedgeDTO dkLib : sugList){
							disese.setSuggest(dkLib.getDisease_suggestion());
							}
						}
					String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="+disese.getDisease_id();
					List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
					if(descList.size() > 0){
						disese.setDisease_description(descList.get(0).getDisease_description());
					}
					dis_count ++;
				}
				return diseaseList;
			}else{
				return null;
			}
		
		}
		
		public boolean convertLogic(DepExamResultDTO resultDto2,DiseaseLogicCompositeItemCondition logicItem){
			String con = logicItem.getCondition();// 当前疾病逻辑的当前条件关系
			// =======条件判断
			if (con.trim().equals("=")) {
				if (logicItem.getCondition_value().equals(resultDto2.getExam_result())) {
					return true;
				}
			}

			// =======条件判断
			if (con.trim().equals(">")) {
				if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
					return false;
				}
				if (Double.parseDouble(resultDto2.getExam_result()) > Double.parseDouble(logicItem.getCondition_value())) {
					return true;
				}
			}

			// =======条件判断
			if (con.trim().equals(">=")) {
				if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
					return false;
				}
				if (Double.parseDouble(resultDto2.getExam_result()) >= Double.parseDouble(logicItem.getCondition_value())) {
					return true;
				}
			}

			// =======条件判断
			if (con.trim().equals("<")) {
				if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
					return false;
				}
				if (Double.parseDouble(resultDto2.getExam_result()) < Double.parseDouble(logicItem.getCondition_value())) {
					return true;
				}
			}

			// =======条件判断
			if (con.trim().equals("<=")) {
				if (resultDto2.getExam_result() == null || !StringUtil.isDouble(resultDto2.getExam_result()) || !StringUtil.isDouble(logicItem.getCondition_value())) {
					return false;
				}
				if (Double.parseDouble(resultDto2.getExam_result()) <= Double.parseDouble(logicItem.getCondition_value())) {
					return true;
				}
			}

			// =======条件判断
			if (con.trim().equals("in")) {
				if (resultDto2.getExam_result().contains(logicItem.getCondition_value())) {
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
		
		@Override
		public List<CriticalDTO> getExamCriticalList(String exam_num) throws ServiceException {
			String sql = " select ecd.id,(select critical_class_name from exam_Critical_class where id = ecd.critical_class_parent_id) as "
					+"critical_class_parent_name,(select critical_class_name from exam_Critical_class where id = ecd.critical_class_id) as "
					+"critical_class_name,ecd.exam_result,ecd.check_date,dd.data_name as critical_class_level,ecd.done_flag,ecd.creater,ecd.data_source  "
					+"from exam_Critical_detail ecd left join exam_info ei on ecd.exam_num=ei.exam_num left join data_dictionary dd "
					+"on ecd.critical_class_level=dd.id where ei.exam_num='"+exam_num+"'";
			List<CriticalDTO> infoDtoList = this.jqm.getList(sql, CriticalDTO.class);
			return infoDtoList;
		}

		@Override
		public String getBatchRetrial(String exam_num, UserDTO user,String apptype) throws ServiceException {
			String sql = "SELECT e.status,c.user_name,e.id,e.exam_num,"
				      +" s.approve_status,censoring_status "
				      +"  FROM  customer_info c,exam_info e "  
					  +"	LEFT  JOIN  exam_summary s ON  s.exam_num=e.exam_num   AND s.center_num = '"+user.getCenter_num()+"'   "
					  +"	WHERE   e.is_Active='Y' and  c.id = e.customer_id " 
					  +"	 and   e.exam_num in("+exam_num+") and e.apptype = '"+apptype+"' order by join_date desc" ;
			List<ExamSummaryDTO> li = this.jqm.getList(sql,ExamSummaryDTO.class);
			String str = "";
			for (int i = 0; i < li.size(); i++) {
				if(!"A".equals(li.get(i).getApprove_status())){
					//沒有总检不能审核
					if(i>2){
						str+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+li.get(i).getExam_num()+li.get(i).getUser_name()+"--没有总检<br/>";
					}else{
						str+= li.get(i).getExam_num()+li.get(i).getUser_name()+"--没有审核<br/>";
					}
				} else if("1".equals(li.get(i).getCensoring_status())){
						//不能重复总检
					if(i>2){
						str+= "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+li.get(i).getExam_num()+li.get(i).getUser_name()+"--重复审核<br/>";
					} else {
						str+= li.get(i).getExam_num()+li.get(i).getUser_name()+"--重复审核<br/>";
					}
				}
			}
			if("".equals(str)){
				String hql = " FROM ExamSummary x   where x.exam_num in ("+exam_num+") and x.app_type = '"+apptype+"'";
				List<ExamSummary> list = this.qm.find(hql);
				for (int j = 0; j < list.size(); j++) {
					list.get(j).setCensoring_status("1");
					list.get(j).setCensoring_doc(user.getUserid());
					list.get(j).setCensoring_time(DateTimeUtil.parse());
					list.get(j).setUpdater(user.getUserid());
					list.get(j).setUpdate_time(DateTimeUtil.parse());
					this.pm.update(list.get(j));
				}
			}
			return str;
		}
}
