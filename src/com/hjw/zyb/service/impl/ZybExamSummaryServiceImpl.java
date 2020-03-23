package com.hjw.zyb.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hjw.wst.domain.*;
import com.hjw.wst.service.CustomerInfoService;
import org.apache.commons.lang.StringUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.StringUtil;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.DiseaseLogicExamItemDTO;
import com.hjw.wst.DTO.DiseaseLogicItemConditionDTO;
import com.hjw.wst.DTO.DiseaseLogicItemDTO;
import com.hjw.wst.DTO.DiseaseMergeDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamSummaryModel;
import com.hjw.zyb.DTO.ZybCheckcriterionDTO;
import com.hjw.zyb.DTO.ZybExamOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybExamSummaryResultDTO;
import com.hjw.zyb.DTO.ZybExamSummaryResultOccidDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.DTO.ZyboccucontraindicationDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseDTO;
import com.hjw.zyb.domain.ZybExamSummaryResult;
import com.hjw.zyb.domain.ZybExamSummaryResultOccid;
import com.hjw.zyb.service.ZybExamSummaryService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class ZybExamSummaryServiceImpl implements ZybExamSummaryService{
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
	@SuppressWarnings("unchecked")
	public CustomerExamDTO getCustomerInfo(String examNum,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		String sql = "select c.arch_num,e.is_guide_back,e.id,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,e.age,e.company,ct.type_name as customer_type,dbo.GetExamSetNameByExamID(e.exam_num) as set_name, "
				   + "e.past_medical_history,convert(varchar(50),e.join_date,23) as join_date,e.status from customer_info c,exam_info e left join customer_type ct on ct.id = e.customer_type_id "
				   + "where e.customer_id = c.id and e.is_Active='Y' and e.exam_num = '"+examNum+"'";
		List<CustomerExamDTO> list = this.jqm.getList(sql, CustomerExamDTO.class);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<ExaminfoDiseaseDTO> createZybExamInfoDisease(String exam_num,String isExamSuggest,String center_num,String sug_center) throws ServiceException {
		String emInfoSql = " select e.id,c.sex,e.age from exam_info e,customer_info c "
				+ " where e.customer_id = c.id and e.exam_num = '" + exam_num + "'";

		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if (infoDtoList.size() == 0) {
			return null;
		} else {
			examInfo = infoDtoList.get(0);
		}

		String sql = " select c.exam_item_id as item_id,c.item_code,c.exam_result from common_exam_detail c "
				+ "where c.exam_num = '"+exam_num+"' "
				+ "union all "
				+ "select ed.exam_item_id as item_id,ed.item_code,ed.exam_result from exam_result_detail ed "
				+ "where ed.exam_num = '"+exam_num+"'";
		List<DepExamResultDTO> ptAndHyResultList = this.jqm.getList(sql, DepExamResultDTO.class);// 查询普通科室与检验科的检查结果

		Map<Long, DepExamResultDTO> resultMap = new HashMap<Long, DepExamResultDTO>();
		for (DepExamResultDTO resultDto : ptAndHyResultList) { // 将普通科室与检验科的检查结果放入MAP中
			resultMap.put(resultDto.getItem_id(), resultDto);
		}

		String viewSql = " select v.exam_item_id as item_id,v.exam_result "
				+ "from view_exam_detail v,exam_info e,examinfo_charging_item ec,pacs_summary p,charging_item c "
				       + " where v.exam_num = e.exam_num and e.exam_num = '"+exam_num+"' and e.exam_num = ec.exam_num "
				       		+ "and ec.charging_item_code = c.item_code and v.pacs_req_code = p.pacs_req_code "
				       + "and c.sam_demo_id = p.examinfo_sampleId and ec.app_type = '2' and ec.center_num = '"+center_num+"' ";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);// 查询影像科室检查结果


		String logcSql = "select distinct l.id, l.disease_id, l.sex,l.logic_type,l.age_min,l.age_max "
				+ "from disease_logic l,disease_logic_exam_item dl, v_exam_result_new v,examinfo_charging_item ec where l.id=dl.disease_logic_id "
				+ "and dl.item_code=v.item_num and l.isActive='Y' and v.exam_num= '"+exam_num
				+ "' and ec.charging_item_code = v.item_code and v.exam_num = ec.exam_num and ec.center_num = '"+center_num+"' ";
		List<DiseaseLogicDTO> dislogicList = this.jqm.getList(logcSql, DiseaseLogicDTO.class);

		List<Long> list = new ArrayList<Long>();// 疾病ID

		for (DiseaseLogicDTO diseaseLogicDTO : dislogicList) {// 循环疾病逻辑
			if (!diseaseLogicDTO.getSex().equals(examInfo.getSex()) && !diseaseLogicDTO.getSex().equals("全部")) {
				continue;
			}
			if ((diseaseLogicDTO.getAge_max() != null && examInfo.getAge() > Long.valueOf(diseaseLogicDTO.getAge_max()))
					|| (diseaseLogicDTO.getAge_min() != null
							&& examInfo.getAge() < Long.valueOf(diseaseLogicDTO.getAge_min()))) {
				continue;
			}
			int count = 0;// 符合条件的疾病逻辑计数器
			boolean and_flag = false;

			String logicItemSql = "select d.andOrNo,d.condition,d.condition_value,d.exam_item_id,d.disease_logic_id "
					+ "from disease_logic_exam_item d where d.disease_logic_id = " + diseaseLogicDTO.getId()
					+ " order by d.logic_index";

			List<DiseaseLogicExamItemDTO> logicItemList = this.jqm.getList(logicItemSql, DiseaseLogicExamItemDTO.class);

			for (DiseaseLogicExamItemDTO logicItem : logicItemList) {

				if ("and".equals(logicItem.getAndOrNo())) {
					and_flag = true;
				}

				if (347 == logicItem.getExam_item_id()) {
					int view_count = 0; // 符合条件的疾病逻辑计数器 影像科室的
					for (DepExamResultDTO viewResult : viewList) {
						if (convertLogic(viewResult, logicItem)) {
							view_count++;
						}
					}
					if (logicItem.getCondition().trim().equals("in") && view_count > 0) {
						count++;
					} else if (logicItem.getCondition().trim().equals("not in") && view_count == viewList.size()) {
						count++;
					}
				} else {
					DepExamResultDTO resultDto2 = resultMap.get(logicItem.getExam_item_id());
					if (resultDto2 == null) {
						continue;
					}
					if (convertLogic(resultDto2, logicItem)) {
						count++;
					}
				}
			}
			// =============所有条件都符合就加进去
			if (and_flag) {
				if (logicItemList.size() != 0 && logicItemList.size() == count) {
					list.add(diseaseLogicDTO.getDisease_id());
				}
			} else {
				if (count > 0) {
					list.add(diseaseLogicDTO.getDisease_id());
				}
			}
		}

		if (list.size() == 0) {
			return new ArrayList<ExaminfoDiseaseDTO>();
		}

		// 去除重复疾病ID
		Set<Long> set = new HashSet<Long>(list);
		list = new ArrayList<Long>(set);

		// 合并阳性发现
		String diseaseMergeSql = "select d.before_disease_id,d.later_disease_id from disease_merge d";
		List<DiseaseMergeDTO> mergeList = this.jqm.getList(diseaseMergeSql, DiseaseMergeDTO.class);

		for (DiseaseMergeDTO mergeDto : mergeList) {
			String[] beforeId = mergeDto.getBefore_disease_id().split(",");

			int count = 0;
			List<Long> temp = new ArrayList<Long>();
			for (int i = 0; i < beforeId.length; i++) {
				for (Long diseaId : list) {
					if (beforeId[i].equals(diseaId.toString())) {
						count++;
						temp.add(diseaId);
					}
				}
			}

			if (count != 0 && count == beforeId.length) {
				list.removeAll(temp);// 移除合并的疾病
				list.add(mergeDto.getLater_disease_id());
			}
		}

		Long[] arr = (Long[]) list.toArray(new Long[list.size()]);
		String diseaseIds = StringUtils.join(arr, ",");

		String diseaseSql = " select distinct d.id as disease_id,d.disease_name,d.disease_type,dd.seq_code as disease_index,d.icd_10,d.disease_num,0 as data_source "
				+ " from disease_knowloedge_lib d left join data_dictionary dd on dd.id = d.disease_level where d.id "
				+ " in (" + diseaseIds + ") order by dd.seq_code";
		List<ExaminfoDiseaseDTO> diseaseList = this.jqm.getList(diseaseSql, ExaminfoDiseaseDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("总检室总检页面-右侧阳性发现列表", diseaseSql);
		int dis_count = 0;
		for (ExaminfoDiseaseDTO disese : diseaseList) {

			String sugsql = " select d.default_value,d.disease_suggestion from disease_suggestion_lib d "
					+ " where d.disease_id = " + disese.getDisease_id() + " and d.is_active = 'Y'" + " and d.minAge <= "
					+ examInfo.getAge() + " and d.maxAge >= " + examInfo.getAge() + " and (d.sex = '"
					+ examInfo.getSex() + "' or d.sex = '全部')";
			if("Y".equals(sug_center)){
				sugsql += " and d.center_num = '"+center_num+"'";
			}
			List<DiseaseKnowloedgeDTO> sugList = this.jqm.getList(sugsql, DiseaseKnowloedgeDTO.class);
			disese.setDisease_index(dis_count);
			if (sugList.size() == 1) {
				if ("Y".equals(isExamSuggest)) {
					disese.setSuggest(disese.getDisease_name() + "： " + sugList.get(0).getDisease_suggestion());
				} else {
					disese.setSuggest(sugList.get(0).getDisease_suggestion());
				}
			} else {
				for (DiseaseKnowloedgeDTO dkLib : sugList) {
					if ("是".equals(dkLib.getDefault_value())) {
						if ("Y".equals(isExamSuggest)) {
							disese.setSuggest(disese.getDisease_name() + "： " + dkLib.getDisease_suggestion());
						} else {
							disese.setSuggest(dkLib.getDisease_suggestion());
						}
					}
				}
			}
			String descSql = "select d.disease_description from disease_knowloedge_lib d where d.id="
					+ disese.getDisease_id();
			List<ExaminfoDiseaseDTO> descList = this.jqm.getList(descSql, ExaminfoDiseaseDTO.class);
			if (descList.size() > 0) {
				disese.setDisease_description(descList.get(0).getDisease_description());
			}
			dis_count++;
		}
		return diseaseList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExaminfoDiseaseDTO> createZybExamInfoDiseaseDep(String exam_num, String center_num,String sug_center) throws ServiceException {
		String emInfoSql = " select e.id,c.sex,e.age from exam_info e,customer_info c "
			     + " where e.customer_id = c.id and e.exam_num = '"+exam_num+"'";

		List<ExamInfoDTO> infoDtoList = this.jqm.getList(emInfoSql, ExamInfoDTO.class);
		ExamInfoDTO examInfo = null;
		if(infoDtoList.size() == 0){
			return null;
		}else{
			examInfo = infoDtoList.get(0);
		}
		String sql = " select c.exam_item_id as item_id,c.exam_result from common_exam_detail c,exam_info e"
				   + " where c.exam_num = e.exam_num and e.exam_num = '"+exam_num+"'"
				   + " union all"
				   + " select ed.exam_item_id as item_id,ed.exam_result from exam_result_detail ed,exam_info e "
				   + " where ed.exam_num = e.exam_num and e.exam_num = '"+exam_num+"'";
		List<DepExamResultDTO> ptAndHyResultList = this.jqm.getList(sql, DepExamResultDTO.class);//查询普通科室与检验科的检查结果
		
		Map<Long,DepExamResultDTO> resultMap = new HashMap<Long, DepExamResultDTO>();
		for(DepExamResultDTO resultDto : ptAndHyResultList){             //将普通科室与检验科的检查结果放入MAP中
			resultMap.put(resultDto.getItem_id(), resultDto);
		}
		
		String viewSql = " select v.exam_item_id as item_id,v.exam_result from view_exam_detail v,exam_info e,examinfo_charging_item ec,pacs_summary p,charging_item c "
			       	   + " where v.exam_num = e.exam_num and e.exam_num = '"+exam_num+"' and e.exam_num = ec.exam_num and ec.charge_item_id = c.id and v.pacs_id = p.id "
			       	   + " and c.sam_demo_id = p.examinfo_sampleId and ec.app_type = '2' and ec.center_num = '"+center_num+"'";
		List<DepExamResultDTO> viewList = this.jqm.getList(viewSql, DepExamResultDTO.class);//查询影像科室检查结果
		
		String logcSql = " select distinct l.id,l.disease_id,l.sex,l.logic_type,l.age_min,l.age_max"
					   + " from disease_logic l, disease_logic_exam_item dl, v_exam_result v,disease_dep dd,examinfo_charging_item ec"
					   + " where l.id=dl.disease_logic_id and dl.item_code=v.item_num "
					   + " and l.isActive='Y' and v.exam_info_id= "+examInfo.getId()+" and ec.exam_num = v.exam_num and ec.charge_item_id = v.charging_id"
					   + " and l.disease_id = dd.disease_id and v.charging_id in (select str2table from StrToTable(dd.charging_item_ids))"
					   + " and v.exam_item_id in (select str2table from StrToTable(dd.exam_item_ids)) and ec.app_type='2' and ec.center_num = '"+center_num+"'";
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
			int view_count = 0;  //符合条件的疾病逻辑计数器  影像科室的
			
			int notin_count = 0;
			
			boolean and_flag = false;
			
			String logicItemSql = "select d.andOrNo,d.condition,d.condition_value,d.exam_item_id,d.disease_logic_id "
					+"from disease_logic_exam_item d where d.disease_logic_id = "+diseaseLogicDTO.getId()+" order by d.logic_index";
			
			List<DiseaseLogicExamItemDTO> logicItemList = this.jqm.getList(logicItemSql, DiseaseLogicExamItemDTO.class);
			
			for(DiseaseLogicExamItemDTO logicItem : logicItemList){
				
				if("and".equals(logicItem.getAndOrNo())){
					and_flag = true;
				}
				
				if(347 == logicItem.getExam_item_id()){
					for(DepExamResultDTO viewResult : viewList){
						if(convertLogic(viewResult,logicItem)){
							view_count ++;
						}
					}
					if(logicItem.getCondition().trim().equals("not in")){
						notin_count ++;
					}
				}else{
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
			}
			// =============所有条件都符合就加进去
			if(and_flag){
				if ((logicItemList.size() != 0 && logicItemList.size() == count)||(logicItemList.size() != 0 && logicItemList.size() - notin_count <= view_count - viewList.size() * notin_count)) {
					list.add(diseaseLogicDTO.getDisease_id());
				}
			}else{
				if(count > 0 || (view_count - viewList.size() * notin_count) > 0){
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
		
		String diseaseSql2 = "select k.id as disease_id,k.disease_name,k.disease_type,k.icd_10,ci.item_name as item_source,ci.id as cid,"
				+ " ei.item_name+v.exam_result+v.item_unit as diagnosis_source,ei.id as eid,ci.dep_category,k.disease_num,0 as data_source from disease_knowloedge_lib k,v_exam_result v,"
				+ " disease_dep dd,department_dep dep,charging_item ci,examination_item ei where v.charging_id = ci.id "
				+ " and v.exam_item_id = ei.id and ci.dep_id = dep.id and k.id = dd.disease_id and k.isActive='Y' and "
				+ " v.exam_info_id= "+examInfo.getId()+" and v.charging_id in (select str2table from StrToTable(dd.charging_item_ids)) "
				+ " and v.exam_item_id in (select str2table from StrToTable(dd.exam_item_ids)) and k.id in ("+diseaseIds+") "
				+ " order by dep.seq_code,ci.item_seq,ei.seq_code";
		List<ExaminfoDiseaseDTO> diseaseList = this.jqm.getList(diseaseSql2, ExaminfoDiseaseDTO.class);
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
				disese.setSuggest(disese.getDisease_name() +"： "+sugList.get(0).getDisease_suggestion());
			}else{
				for(DiseaseKnowloedgeDTO dkLib : sugList){
					if("是".equals(dkLib.getDefault_value())){
						disese.setSuggest(disese.getDisease_name() +"： "+dkLib.getDisease_suggestion());
					}
				}
			}
			dis_count ++;
		}
		diseaseList.removeAll(tempList);
		return diseaseList;
	}
	
	public List<ExaminfoDiseaseDTO> createNewZybExamInfoDiseaseSingle(String exam_num,String isExamSuggest, String center_num,String sug_center) throws ServiceException{
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

	@Override
	public List<ZybexaminationresultDTO> getZybExaminationResultList(String result) throws ServiceException {
		String sql = "select * from zyb_examinationresult z where z.result_name like '%"+result+"%' order by seq_code";
		List<ZybexaminationresultDTO> list = this.jqm.getList(sql, ZybexaminationresultDTO.class);
		return list;
	}

	@Override
	public List<ZyboccucontraindicationDTO> getExamSummaryOccucontraindicationList(ExamSummaryModel model) throws ServiceException {
		if(model.getExamOccuhazardfactorsList().size() <= 0){
			return new ArrayList<ZyboccucontraindicationDTO>();
		}
		
		String sql = "select o.* from zyb_occucontraindication o,zyb_occuhazardfactors_occucontraindication c "
				   + "where o.contraindicationID = c.contraindicationID and (";
		for(int i=0;i<model.getExamOccuhazardfactorsList().size();i++){
			ZybExamOccuhazardfactorsDTO dto = model.getExamOccuhazardfactorsList().get(i);
			if(i == model.getExamOccuhazardfactorsList().size()-1){
				sql += "(c.hazardfactorsID = '"+dto.getHazardfactorsID()+"' and c.occuphyexaclassID = '"+dto.getOccuphyexaclassid()+"')";
			}else{
				sql += "(c.hazardfactorsID = '"+dto.getHazardfactorsID()+"' and c.occuphyexaclassID = '"+dto.getOccuphyexaclassid()+"') or ";
			}
		}
		sql += ") order by c.DISORDER";
		List<ZyboccucontraindicationDTO> list = this.jqm.getList(sql, ZyboccucontraindicationDTO.class);
		return list;
	}

	@Override
	public List<ZyboccudiseaseDTO> getExamSummaryOccudisease(ExamSummaryModel model) throws ServiceException {
		if(model.getExamOccuhazardfactorsList().size() <= 0){
			return new ArrayList<ZyboccudiseaseDTO>();
		}
		String sql = "select o.*,l.diseaseclass_name from zyb_occudisease o,zyb_occudiseaseclass l,zyb_occuhazardfactors_occudisease c "
				   + "where o.occudiseaseID =  c.occudiseaseID and o.diseaseclassID = l.diseaseclassID and (";
		for(int i=0;i<model.getExamOccuhazardfactorsList().size();i++){
			ZybExamOccuhazardfactorsDTO dto = model.getExamOccuhazardfactorsList().get(i);
			if(i == model.getExamOccuhazardfactorsList().size()-1){
				sql += "(c.hazardfactorsID = '"+dto.getHazardfactorsID()+"' and c.occuphyexaclassID = '"+dto.getOccuphyexaclassid()+"')";
			}else{
				sql += "(c.hazardfactorsID = '"+dto.getHazardfactorsID()+"' and c.occuphyexaclassID = '"+dto.getOccuphyexaclassid()+"') or ";
			}
		}
		sql += ") order by c.DISORDER";
		List<ZyboccudiseaseDTO> list = this.jqm.getList(sql, ZyboccudiseaseDTO.class);
		return list;
	}

	@Override
	public List<ZybCheckcriterionDTO> getExamSummaryCheckcriterionList(ExamSummaryModel model) throws ServiceException {
		String sql = "select o.criterionID,o.criterion_name from zyb_checkcriterion o,zyb_occuhazardfactors_checkcriterion c where "
				   + "o.criterionID = c.criterionID and c.hazardfactorsID = '"+model.getOccusectorid()+"' and c.occuphyexaclassID = '"+model.getOccutypeofworkid()+"'";
		List<ZybCheckcriterionDTO> list = this.jqm.getList(sql, ZybCheckcriterionDTO.class);
		return list;
	}

	@Override
	public String saveZybExamSummaryResult(ExamSummaryModel model, long userid) throws ServiceException {
		
		List<ZybExamSummaryResult> list = this.qm.find("from ZybExamSummaryResult z where z.exam_num ='"+model.getExam_num()+"'");
		if(list.size() == 0){
			ZybExamSummaryResult result = new ZybExamSummaryResult();
			result.setExam_info_id(model.getExam_info_id());
			result.setExam_num(model.getExam_num());
			result.setExam_result(model.getExam_result());
			result.setResultID(model.getResultID());
			result.setRemark(model.getRemark());
			result.setCreater(userid);
			result.setCreate_time(DateTimeUtil.parse());
			result.setExam_num(model.getExam_num());
			this.pm.save(result);
			
			if(!"".equals(model.getOccudiseaseIDorcontraindicationID())){
				String[] ids = model.getOccudiseaseIDorcontraindicationID().split(",");
				for (int i = 0; i < ids.length; i++) {
					ZybExamSummaryResultOccid occid = new ZybExamSummaryResultOccid();
					occid.setResult_id(result.getId());
					occid.setOccudiseaseIDorcontraindicationID(ids[i]);
					this.pm.save(occid);
				}
			}
		}else{
			ZybExamSummaryResult result = list.get(0);
			
			result.setExam_info_id(model.getExam_info_id());
			result.setExam_num(model.getExam_num());
			result.setExam_result(model.getExam_result());
			result.setResultID(model.getResultID());
			result.setRemark(model.getRemark());
			result.setCreater(userid);
			result.setCreate_time(DateTimeUtil.parse());
			this.pm.update(result);
			
			List<ZybExamSummaryResultOccid> occidlist = this.qm.find("from ZybExamSummaryResultOccid o where o.result_id = '"+result.getId()+"'");
			for(ZybExamSummaryResultOccid occid : occidlist){
				this.pm.remove(occid);
			}
			if(!"".equals(model.getOccudiseaseIDorcontraindicationID())){
				String[] ids = model.getOccudiseaseIDorcontraindicationID().split(",");
				for (int i = 0; i < ids.length; i++) {
					ZybExamSummaryResultOccid occid = new ZybExamSummaryResultOccid();
					occid.setResult_id(result.getId());
					occid.setOccudiseaseIDorcontraindicationID(ids[i]);
					this.pm.save(occid);
				}
			}
		}
		return "ok-保存成功!";
	}

	@Override
	public ZybExamSummaryResultDTO getZybExamSummaryResult(String exam_num) throws ServiceException {
		String sql = "select z.resultID,z.exam_result,z.remark,z.id from zyb_exam_summary_result z where z.exam_num = '"+exam_num+"'";
		List<ZybExamSummaryResultDTO> list = this.jqm.getList(sql, ZybExamSummaryResultDTO.class);
		if(list.size() > 0){
			ZybExamSummaryResultDTO resultdto = list.get(0);
			String sql1 = "select c.occudiseaseIDorcontraindicationID from zyb_exam_summary_result_occid c where c.result_id = " + resultdto.getId();
			List<ZybExamSummaryResultOccidDTO> occidDto = this.jqm.getList(sql1, ZybExamSummaryResultOccidDTO.class);
			resultdto.setOccidList(occidDto);
			return resultdto;
		}
		return new ZybExamSummaryResultDTO();
	}
	
	private ExamInfo getExamInfoByExamNum(String exam_num) throws ServiceException {
		List<ExamInfo> list = this.qm.find("from ExamInfo e where e.exam_num = '"+exam_num+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveZybExamSummary(ExamSummaryModel model, ExamSummary examSummary, UserDTO user, String flag)
			throws ServiceException {
		ExamInfo examInfo = this.getExamInfoByExamNum(model.getExam_num());
		if(examInfo == null){
			return "error-该体检信息不存在";
		}
		if("Y".equals(flag)){
			examInfo.setStatus("Z");
			examInfo.setFinal_doctor(user.getName());
			examInfo.setFinal_date(DateTimeUtil.getDateTime());
			examInfo.setUpdate_time(DateTimeUtil.getDateTime());
			examInfo.setUpdater(user.getUserid());
			this.pm.update(examInfo);
		}
		List<ExaminfoDiseaseDTO> list = model.getExaminfoDisease();
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
                examSummary.setRead_status1(0);
                examSummary.setRead_status2(0);
                examSummary.setRead_status3(0);
				examSummary.setFinal_status("Z");
				examSummary.setFinal_time(DateTimeUtil.parse());
			}else{
				examSummary.setFinal_status("J");
			}
			this.pm.save(examSummary);
		}else{
			if(!"".equals(flag)){
				examSummary.setExam_doctor_id(user.getUserid());
			}
			examSummary.setFinal_exam_result(model.getFinal_exam_result());
			examSummary.setRead_status(0);
			examSummary.setApprove_status("B");
			examSummary.setUpdater(user.getUserid());
			examSummary.setUpdate_time(DateTimeUtil.parse());
			examSummary.setExam_guidance(model.getExam_guidance());
			examSummary.setFinal_worknum(list.size());
			if("Y".equals(flag)){
			    examSummary.setRead_status1(0);
			    examSummary.setRead_status2(0);
			    examSummary.setRead_status3(0);
				examSummary.setFinal_status("Z");
				examSummary.setFinal_time(DateTimeUtil.parse());
			}
			this.pm.update(examSummary);
		}
		List<ExaminfoDisease> oldDis = this.qm.find("from ExaminfoDisease e where e.exam_info_id = "+examInfo.getId()+" and e.app_type='"+model.getApp_type()+"'");
		for(ExaminfoDisease examinfoDisease : oldDis){
			this.pm.remove(examinfoDisease);
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
			examinfoDisease.setExam_num(model.getExam_num());
			this.pm.save(examinfoDisease);
			count ++;
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
		return "ok-保存职业病总检成功!";
	}
}
