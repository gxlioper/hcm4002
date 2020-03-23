package com.hjw.wst.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.HealthRiskAssessmentDTO;
import com.hjw.wst.DTO.HealthRiskAssessmentItemConditionDTO;
import com.hjw.wst.DTO.HealthRiskAssessmentItemDTO;
import com.hjw.wst.DTO.HealthRiskExaminfoDTO;
import com.hjw.wst.DTO.HealthRiskExaminfoItemDTO;
import com.hjw.wst.DTO.HealthRiskItemExamresultDTO;
import com.hjw.wst.DTO.HealthRiskMorbidityDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.HealthRiskExaminfo;
import com.hjw.wst.domain.HealthRiskExaminfoItem;
import com.hjw.wst.service.HealthRiskAssessmentService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class HealthRiskAssessmentServiceImpl implements HealthRiskAssessmentService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	
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

	@Override
	public List<HealthRiskItemExamresultDTO> saveHealthRiskItemExamresult(String exam_num) throws ServiceException {
		String sql = "select e.exam_num,'AGE' as risk_item_code,convert(varchar(100),e.age) as exam_result "
				+ "from exam_info e where e.exam_num = '"+exam_num+"' "
				+ "union all "
				+ "select v.exam_num,h.risk_item_code,v.exam_result from health_risk_item_contrast h,v_exam_result v "
				+ "where h.item_code = v.item_num and v.exam_num = '"+exam_num+"'";
		List<HealthRiskItemExamresultDTO> itemlist = this.jqm.getList(sql, HealthRiskItemExamresultDTO.class);
		String deletesql = "delete health_risk_item_examresult where exam_num = '"+exam_num+"'";
		this.jpm.executeSql(deletesql);
		for(HealthRiskItemExamresultDTO dto : itemlist){
			String insertsql = "insert into health_risk_item_examresult values('"+dto.getExam_num()+"','"+dto.getRisk_item_code()+"','"+dto.getExam_result()+"')";
			this.jpm.executeSql(insertsql);
		}
		return itemlist;
	} 

	@Override
	public List<HealthRiskExaminfoDTO> createHealthRiskExaminfo(String exam_num, String sex,
			List<HealthRiskItemExamresultDTO> itemlist) throws ServiceException {
		List<HealthRiskExaminfoDTO> riskExaminfoList = new ArrayList<HealthRiskExaminfoDTO>();
		//将项目结果放入map中
		Map<String,HealthRiskItemExamresultDTO> itemMap = new HashMap<String,HealthRiskItemExamresultDTO>();
		for(HealthRiskItemExamresultDTO dto : itemlist){
			itemMap.put(dto.getRisk_item_code(), dto);
		}
		//查询生成风险评估规则
		String sql = "select h.id,h.disease_name,h.disease_type,h.disease_type_name,h.bar_graph_titel from health_risk_assessment h";
		List<HealthRiskAssessmentDTO> riskAsList = this.jqm.getList(sql, HealthRiskAssessmentDTO.class);
		
		for(HealthRiskAssessmentDTO riskDto : riskAsList){
			sql = "select i.id,i.health_risk_id,i.item_code,i.item_name,i.item_type,i.sex,i.item_seq from "
				+ "health_risk_assessment_item i where i.health_risk_id = '"+riskDto.getId()+"' and i.sex "
				+ "in ('全部','"+sex+"') order by i.item_seq";
			List<HealthRiskAssessmentItemDTO> riskItemlist = this.jqm.getList(sql, HealthRiskAssessmentItemDTO.class);
			
			long points = 0;
			long is_success = 0;
			String cause_failure = "健康分析评估完成!";
			
			boolean bpflag = true;//糖尿病血压和服药史判断一项  或的关系
			List<HealthRiskExaminfoItemDTO> riskExaminfoItemList = new ArrayList<HealthRiskExaminfoItemDTO>();
			for(HealthRiskAssessmentItemDTO riskItemDto : riskItemlist){
				
				HealthRiskItemExamresultDTO resultDto = itemMap.get(riskItemDto.getItem_code());
				if((resultDto != null && !"".equals(resultDto.getExam_result())) || riskItemDto.getItem_code().equals("BP")
						|| (riskItemDto.getItem_code().equals("HTM") && bpflag && resultDto != null && !"".equals(resultDto.getExam_result()))){
					if(riskItemDto.getItem_type() == 1){
						if(!StringUtil.isDouble(resultDto.getExam_result())){
							is_success = 1;
							cause_failure = "健康风险评估失败，项目"+riskItemDto.getItem_name()+"检查结果不是数字，不能评估!";
							break;
						}
					}else if(riskItemDto.getItem_type() == 3){
						if(itemMap.get("DBP") == null || !StringUtil.isDouble(itemMap.get("DBP").getExam_result()) ||
								itemMap.get("SBP") == null || !StringUtil.isDouble(itemMap.get("SBP").getExam_result())){
							is_success = 1;
							cause_failure = "健康风险评估失败，项目"+riskItemDto.getItem_name()+"检查结果不存在在或不是数字，不能评估!";
							break;
						}
					}
					sql = "select c.id,c.health_item_id,c.points,c.min_value,c.max_value,c.yes_or_not,c.min_value1,c.max_value1 "
						+ "from health_risk_assessment_item_condition c where c.health_item_id = '"+riskItemDto.getId()+"'";
					List<HealthRiskAssessmentItemConditionDTO> itemCoditionList = this.jqm.getList(sql, HealthRiskAssessmentItemConditionDTO.class);
					
					HealthRiskExaminfoItemDTO riskExaminfoitem = new HealthRiskExaminfoItemDTO();
					riskExaminfoitem.setRisk_item_code(riskItemDto.getItem_code());
					for(HealthRiskAssessmentItemConditionDTO conDto : itemCoditionList){
						if(riskItemDto.getItem_type() == 1){
							if(conDto.getMin_value() == null && conDto.getMax_value() != null){
								if(Double.parseDouble(resultDto.getExam_result()) <= conDto.getMax_value()){
									points += conDto.getPoints();
									riskExaminfoitem.setPoint(conDto.getPoints());
									riskExaminfoItemList.add(riskExaminfoitem);
									break;
								}
							}else if(conDto.getMin_value() != null && conDto.getMax_value() == null){
								if(Double.parseDouble(resultDto.getExam_result()) >= conDto.getMin_value()){
									points += conDto.getPoints();
									riskExaminfoitem.setPoint(conDto.getPoints());
									riskExaminfoItemList.add(riskExaminfoitem);
									break;
								}
							}else if(conDto.getMin_value() != null && conDto.getMax_value() != null){
								if(Double.parseDouble(resultDto.getExam_result()) >= conDto.getMin_value() && Double.parseDouble(resultDto.getExam_result()) <= conDto.getMax_value()){
									points += conDto.getPoints();
									riskExaminfoitem.setPoint(conDto.getPoints());
									riskExaminfoItemList.add(riskExaminfoitem);
									break;
								}
							}
						}else if(riskItemDto.getItem_type() == 2){
							String[] yesnot = conDto.getYes_or_not().split(",");
							for (int i = 0; i < yesnot.length; i++) {
								if(!"".equals(yesnot[i]) && resultDto.getExam_result().indexOf(yesnot[i]) > -1){
									points += conDto.getPoints();
									riskExaminfoitem.setPoint(conDto.getPoints());
									riskExaminfoItemList.add(riskExaminfoitem);
									break;
								}
							}
							riskExaminfoitem.setPoint(0);
							riskExaminfoItemList.add(riskExaminfoitem);
						}else if(riskItemDto.getItem_type() == 3){
							if(Double.parseDouble(itemMap.get("SBP").getExam_result()) >= conDto.getMin_value() 
									&& Double.parseDouble(itemMap.get("SBP").getExam_result()) <= conDto.getMax_value() 
										&& Double.parseDouble(itemMap.get("DBP").getExam_result()) >= conDto.getMin_value1() 
											&& Double.parseDouble(itemMap.get("DBP").getExam_result()) <= conDto.getMax_value1()){
								points += conDto.getPoints();
								riskExaminfoitem.setPoint(conDto.getPoints());
								riskExaminfoItemList.add(riskExaminfoitem);
								bpflag = false;
								break;
							}
						}
					}
					if(riskExaminfoitem.getPoint() == 100 && riskItemDto.getItem_code().equals("GLU")){
						is_success = 1;
						cause_failure = "确认为糖尿病，无需做此评估。";
						break;
					}
				}else{
					is_success = 1;
					cause_failure = "健康风险评估失败，项目"+riskItemDto.getItem_name()+"检查结果不存在，不能评估!";
					break;
				}
			}
			
			HealthRiskExaminfoDTO riskExaminfo = new HealthRiskExaminfoDTO();
			riskExaminfo.setExam_num(exam_num);
			riskExaminfo.setHealth_risk_id(riskDto.getId());
			riskExaminfo.setPoints(points);
			riskExaminfo.setCause_failure(cause_failure);
			riskExaminfo.setIs_success(is_success);
			riskExaminfo.setBar_graph_titel(riskDto.getBar_graph_titel());
			riskExaminfo.setDisease_type(riskDto.getDisease_type());
			if(is_success == 0){//评估成功，记录每个项目得分情况
				riskExaminfo.setItemList(riskExaminfoItemList);
				//计算体检人实际发病率
				sql = "select m.id,m.points,m.sex,m.morbidity,m.condition from health_risk_morbidity m where "
						+ "m.health_risk_id = '"+riskDto.getId()+"' and m.sex in ('全部','"+sex+"')";
				List<HealthRiskMorbidityDTO> riskMorbidityList = this.jqm.getList(sql, HealthRiskMorbidityDTO.class);
				for(HealthRiskMorbidityDTO morbidityDTO : riskMorbidityList){
					if("=".equals(morbidityDTO.getCondition())){
						if(points == morbidityDTO.getPoints()){
							riskExaminfo.setReality_morbidity(morbidityDTO.getMorbidity());
						}
					}else if(">=".equals(morbidityDTO.getCondition())){
						if(points >= morbidityDTO.getPoints()){
							riskExaminfo.setReality_morbidity(morbidityDTO.getMorbidity());
						}
					}else if("<=".equals(morbidityDTO.getCondition())){
						if(points <= morbidityDTO.getPoints()){
							riskExaminfo.setReality_morbidity(morbidityDTO.getMorbidity());
						}
					}else{
						if(points == morbidityDTO.getPoints()){
							riskExaminfo.setReality_morbidity(morbidityDTO.getMorbidity());
						}
					}
				}
				//根据年龄匹配平均发病率
				sql = "select h.average_morbidity,h.hard_morbidity,h.low_morbidity from health_risk_assessment_morbidity h "
					+ "where h.sex in ('全部','"+sex+"') and h.min_age <= "+itemMap.get("AGE").getExam_result()+" and "
					+ "h.max_age >= "+itemMap.get("AGE").getExam_result() + "and h.health_risk_id = '"+riskDto.getId()+"'";
				List<HealthRiskMorbidityDTO> list = this.jqm.getList(sql, HealthRiskMorbidityDTO.class);
				if(list.size() > 0){
					riskExaminfo.setAverage_morbidity(list.get(0).getAverage_morbidity());
					riskExaminfo.setHard_morbidity(list.get(0).getHard_morbidity());
					riskExaminfo.setLow_morbidity(list.get(0).getLow_morbidity());
				}
			}else{
				//评估失败，分数设定-1000
				riskExaminfo.setPoints(-1000);
			}
			riskExaminfoList.add(riskExaminfo);
		}
		return riskExaminfoList;
	}

	@Override
	public ExamInfoDTO getexaminfobyExamnum(String exam_num) throws ServiceException {
		String sql = "select e.exam_num,c.sex from exam_info e,customer_info c where e.customer_id = c.id and e.exam_num = '"+exam_num+"' and e.is_Active = 'Y'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String saveHealthRiskExaminfo(String exam_num,List<HealthRiskExaminfoDTO> riskExaminfoList, UserDTO user)
			throws ServiceException {
		List<HealthRiskExaminfo> list = this.qm.find("from HealthRiskExaminfo where exam_num = '"+exam_num+"'");
		for(HealthRiskExaminfo info : list){
			info.setIs_active("N");
			this.pm.update(info);
		}
		
		for(HealthRiskExaminfoDTO risExamDto : riskExaminfoList){
			HealthRiskExaminfo riskExaminfo = new HealthRiskExaminfo();
			
			riskExaminfo.setExam_num(risExamDto.getExam_num());
			riskExaminfo.setHealth_risk_id(risExamDto.getHealth_risk_id());
			riskExaminfo.setPoints(risExamDto.getPoints());
			riskExaminfo.setCause_failure(risExamDto.getCause_failure());
			riskExaminfo.setIs_success(risExamDto.getIs_success());
			riskExaminfo.setAverage_morbidity(risExamDto.getAverage_morbidity());
			riskExaminfo.setReality_morbidity(risExamDto.getReality_morbidity());
			riskExaminfo.setPicture_path(risExamDto.getPicture_path());
			riskExaminfo.setCreater(user.getUserid());
			riskExaminfo.setCreate_time(DateTimeUtil.parse());
			riskExaminfo.setIs_active("Y");
			riskExaminfo.setLow_morbidity(risExamDto.getLow_morbidity());
			riskExaminfo.setHard_morbidity(risExamDto.getHard_morbidity());
			
			this.pm.save(riskExaminfo);
			
			List<HealthRiskExaminfoItemDTO> itemList = risExamDto.getItemList();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
			if(itemList != null){
				for(HealthRiskExaminfoItemDTO itemDto : itemList){
					HealthRiskExaminfoItem riskitem = new HealthRiskExaminfoItem();
					
					riskitem.setPoint(itemDto.getPoint());
					riskitem.setRisk_examinfo_id(riskExaminfo.getId());
					riskitem.setRisk_item_code(itemDto.getRisk_item_code());
					
					this.pm.save(riskitem);
				}
			}
		}
		return "ok-保存成功!";
	}

	@Override
	public List<HealthRiskExaminfoDTO> getHealthRiskAssessmentReport(String exam_num) throws ServiceException {
		String sql = "select h.points,h.picture_path,h.is_success,h.cause_failure,h.reality_morbidity,h.average_morbidity, "
				+ "h.hard_morbidity,h.low_morbidity,u.chi_name as creater,h.create_time,a.disease_name,a.disease_type_name "
				+ "from health_risk_examinfo h,health_risk_assessment a,user_usr u where h.exam_num = '"+exam_num+"' and "
				+ "h.is_active = 'Y' and a.id = health_risk_id and h.creater = u.id ";
		List<HealthRiskExaminfoDTO> list = this.jqm.getList(sql, HealthRiskExaminfoDTO.class);
		return list;
	}
	
	
}
