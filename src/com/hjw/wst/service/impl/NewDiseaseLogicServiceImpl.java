package com.hjw.wst.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.DiseaseLogicItemConditionDTO;
import com.hjw.wst.DTO.DiseaseLogicItemDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DiseaseLogicComposite;
import com.hjw.wst.domain.DiseaseLogicCompositeItem;
import com.hjw.wst.domain.DiseaseLogicCompositeItemCondition;
import com.hjw.wst.domain.DiseaseLogicSingle;
import com.hjw.wst.domain.DiseaseLogicSingleItem;
import com.hjw.wst.domain.DiseaseLogicSingleItemCondition;
import com.hjw.wst.model.DiseaseLogicModel;
import com.hjw.wst.service.NewDiseaseLogicService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;

public class NewDiseaseLogicServiceImpl implements NewDiseaseLogicService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
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

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	@Override
	public PageReturnDTO getDiseaseLogicSingleList(String logic_name,String item_name, long depid, long logic_class, String isActive, int page, int pagesize, String center_num,String itemNum) throws ServiceException {
		String sql = "";
		if(logic_class == 1){//单项阳性科室结论逻辑
			sql = "SELECT us.chi_name,c.update_time,c.id as ids,c.logic_name,c.disease_num,c.item_code item_num,e.dep_name item_name,"
					+ "c.sex,convert(varchar(100),c.age_min)+'-'+convert(varchar(100),c.age_max) as ageMinMax,c.isActive,d.dep_id  "
					+ "FROM disease_logic_single c left join user_usr us on us.id = c.updater,"
					+ "disease_knowloedge_lib d,department_dep e,department_vs_center de where c.disease_num = d.disease_num  and de.dep_id = e.id  "
					+ " and de.center_num ='"+center_num+"'  and c.item_code = e.dep_num and c.logic_class = 1 ";
			if (logic_name != null && !"".equals(logic_name)) {
				sql += " and (c.logic_name like '%"+logic_name.trim()+"%' or d.disease_pinyin like '%"+logic_name.trim()+"%')";
			}
			if(depid > 0){
    			sql +=" and d.dep_id = '"+depid+"'";
    		}
			if(!StringUtils.isEmpty(isActive)){
				sql += " and c.isActive = '"+isActive+"'";
			}
			sql += " order by d.dep_id,c.item_code";
		}else{
			sql = "SELECT us.chi_name,c.update_time,c.id as ids,c.logic_name,c.disease_num,c.item_code item_num,e.item_name,"
					+ "c.sex,convert(varchar(100),c.age_min)+'-'+convert(varchar(100),c.age_max) as ageMinMax,c.isActive,d.dep_id  "
					+ "FROM disease_logic_single c left join user_usr us on us.id = c.updater,"
					+ "disease_knowloedge_lib d,examination_item e where c.disease_num = d.disease_num "
					+ "and c.item_code = e.item_num and c.logic_class = 0 ";
			if(!StringUtils.isEmpty(itemNum)){
	            sql += " and c.item_code='"+itemNum+"' ";
	        }else{
	        	if(depid > 0){
	    			sql +=" and d.dep_id = '"+depid+"'";
	    		}
	        }
			if (logic_name != null && !"".equals(logic_name)) {
				sql += " and (c.logic_name like '%"+logic_name.trim()+"%' or d.disease_pinyin like '%"+logic_name.trim()+"%')";
			}
			if (item_name != null && !"".equals(item_name)) {
				sql += " and (e.item_name like '%"+item_name.trim()+"%' or e.item_pinyin like '%"+item_name.trim()+"%')";
			}
			if(!StringUtils.isEmpty(isActive)){
				sql += " and c.isActive = '"+isActive+"'";
			}
			sql +=" union all "
					+ "SELECT us.chi_name,c.update_time,c.id as ids,c.logic_name,c.disease_num,c.item_code item_num,e.item_name,"
					+ "c.sex,convert(varchar(100),c.age_min)+'-'+convert(varchar(100),c.age_max) as ageMinMax,c.isActive,d.dep_id "
					+ "FROM disease_logic_single c left join user_usr us on us.id = c.updater,disease_knowloedge_lib d,charging_item e "
					+ "where c.disease_num = d.disease_num and c.item_code = e.item_code and c.logic_class = 2";
			if(!StringUtils.isEmpty(itemNum)){
	            sql += " and c.item_code='"+itemNum+"' ";
	        }else{
	        	if(depid > 0){
	    			sql +=" and d.dep_id = '"+depid+"'";
	    		}
	        }
			if (logic_name != null && !"".equals(logic_name)) {
				sql += " and (c.logic_name like '%"+logic_name.trim()+"%' or d.disease_pinyin like '%"+logic_name.trim()+"%')";
			}
			if (item_name != null && !"".equals(item_name)) {
				sql += " and (e.item_name like '%"+item_name.trim()+"%' or e.item_pinyin like '%"+item_name.trim()+"%')";
			}
			if(!StringUtils.isEmpty(isActive)){
				sql += " and c.isActive = '"+isActive+"'";
			}
			sql += " order by dep_id,item_num";
		}
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, DiseaseLogicDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			List<DiseaseLogicDTO> li = map.getList();
			for (DiseaseLogicDTO diseaseLogicDTO : li) {
				String tiaojian = "";
				String itemsql = "select d.logic_item_name,d.critical_flag,d.logic_index,d.id from disease_logic_single_item d "
						+ "where d.logic_single_id = '"+diseaseLogicDTO.getIds()+"' and d.isActive = 'Y' order by d.logic_index";
				List<DiseaseLogicItemDTO> itemlist = this.jqm.getList(itemsql, DiseaseLogicItemDTO.class);
				for (DiseaseLogicItemDTO diseaseLogicItemDTO : itemlist) {
					String consql = "select d.condition,d.condition_value from disease_logic_single_item_condition d "
							+ "where d.logic_single_item_id = '"+diseaseLogicItemDTO.getId()+"' order by d.logic_index";
					List<DiseaseLogicItemConditionDTO> conlist = this.jqm.getList(consql, DiseaseLogicItemConditionDTO.class);
					
					if(diseaseLogicItemDTO.getCritical_flag() == 1){
						tiaojian += diseaseLogicItemDTO.getLogic_item_name()+"【危机】：";
					}else{
						tiaojian += diseaseLogicItemDTO.getLogic_item_name()+"：";
					}
					for (int i = 0; i < conlist.size(); i++) {
						if (i == conlist.size() - 1) {
							tiaojian += diseaseLogicDTO.getItem_name() + conlist.get(i).getCondition() + conlist.get(i).getCondition_value();
						}else{
							tiaojian += diseaseLogicDTO.getItem_name() + conlist.get(i).getCondition() + conlist.get(i).getCondition_value() + "&nbsp;and&nbsp;";
						}
					}
					tiaojian += "<br/>";
				}
				diseaseLogicDTO.setTiaojian(tiaojian);
			}
			webrole.setRows(li);
		}
		return webrole;
	}

	@Override
	public void saveDiseaseLogicSingle(DiseaseLogicModel model, UserDTO user) throws ServiceException {
		if(model.getIds() != null && !"".equals(model.getIds())){//修改
			List<DiseaseLogicSingleItem> itemlist = this.qm.find("from DiseaseLogicSingleItem where logic_single_id ='"+model.getIds()+"'");
			for (DiseaseLogicSingleItem diseaseLogicSingleItem : itemlist) {
				List<DiseaseLogicSingleItemCondition> condList = this.qm.find("from DiseaseLogicSingleItemCondition where logic_single_item_id ='"+diseaseLogicSingleItem.getId()+"'");
				for (DiseaseLogicSingleItemCondition diseaseLogicSingleItemCondition : condList) {
					this.pm.remove(diseaseLogicSingleItemCondition);
				}
				this.pm.remove(diseaseLogicSingleItem);
			}
			
			List<DiseaseLogicSingle> list = this.qm.find("from DiseaseLogicSingle where id = '"+model.getIds()+"'");
			if(list.size() > 0){
				DiseaseLogicSingle diseaseLogicSingle = list.get(0);
				diseaseLogicSingle.setDisease_num(model.getDisease_num());
				diseaseLogicSingle.setLogic_name(model.getLogic_name());
				diseaseLogicSingle.setItem_code(model.getItem_num());
				diseaseLogicSingle.setSex(model.getSex());
				diseaseLogicSingle.setAge_min(Integer.parseInt(model.getAge_min()));
				diseaseLogicSingle.setAge_max(Integer.parseInt(model.getAge_max()));
				diseaseLogicSingle.setUpdater(user.getUserid());
				diseaseLogicSingle.setUpdate_time(DateTimeUtil.parse());
				diseaseLogicSingle.setLogic_class(model.getLogic_class());
				this.pm.update(diseaseLogicSingle);
				
				JSONArray liArry = JSONArray.fromObject(model.getLi());
				@SuppressWarnings("unchecked")
				List<DiseaseLogicItemDTO> lis = (List<DiseaseLogicItemDTO>) JSONArray.toCollection(liArry,DiseaseLogicItemDTO.class);
				for (DiseaseLogicItemDTO diseaseLogicItemDTO : lis) {
					DiseaseLogicSingleItem diseaseLogicSingleItem = new DiseaseLogicSingleItem();
					diseaseLogicSingleItem.setLogic_single_id(diseaseLogicSingle.getId());
					diseaseLogicSingleItem.setCritical_flag(diseaseLogicItemDTO.getCritical_flag());
					diseaseLogicSingleItem.setIsActive("Y");
					diseaseLogicSingleItem.setLogic_index(diseaseLogicItemDTO.getLogic_index());
					diseaseLogicSingleItem.setLogic_item_name(diseaseLogicItemDTO.getLogic_item_name());
					diseaseLogicSingleItem.setCreater(user.getUserid());
					diseaseLogicSingleItem.setCreate_time(DateTimeUtil.parse());
					diseaseLogicSingleItem.setUpdater(user.getUserid());
					diseaseLogicSingleItem.setUpdate_time(DateTimeUtil.parse());
					this.pm.save(diseaseLogicSingleItem);
					
					JSONArray conArry = JSONArray.fromObject(diseaseLogicItemDTO.getItemConditions());
					@SuppressWarnings("unchecked")
					List<DiseaseLogicItemConditionDTO> conlist = (List<DiseaseLogicItemConditionDTO>) JSONArray.toCollection(conArry,DiseaseLogicItemConditionDTO.class);
					
					for (DiseaseLogicItemConditionDTO diseaseLogicItemConditionDTO : conlist) {
						DiseaseLogicSingleItemCondition diseaseLogicSingleItemCondition = new DiseaseLogicSingleItemCondition();
						diseaseLogicSingleItemCondition.setLogic_single_item_id(diseaseLogicSingleItem.getId());
						diseaseLogicSingleItemCondition.setLogic_index(diseaseLogicItemConditionDTO.getLogic_index());
						diseaseLogicSingleItemCondition.setCondition(diseaseLogicItemConditionDTO.getCondition());
						diseaseLogicSingleItemCondition.setCondition_value(diseaseLogicItemConditionDTO.getCondition_value());
						this.pm.save(diseaseLogicSingleItemCondition);
					}
				}
			}
		}else{//新增
			DiseaseLogicSingle diseaseLogicSingle = new DiseaseLogicSingle();
			diseaseLogicSingle.setDisease_num(model.getDisease_num());
			diseaseLogicSingle.setLogic_name(model.getLogic_name());
			diseaseLogicSingle.setItem_code(model.getItem_num());
			diseaseLogicSingle.setSex(model.getSex());
			diseaseLogicSingle.setAge_min(Integer.parseInt(model.getAge_min()));
			diseaseLogicSingle.setAge_max(Integer.parseInt(model.getAge_max()));
			diseaseLogicSingle.setIsActive("Y");
			diseaseLogicSingle.setLogic_class(model.getLogic_class());
			diseaseLogicSingle.setCreater(user.getUserid());
			diseaseLogicSingle.setCreate_time(DateTimeUtil.parse());
			diseaseLogicSingle.setUpdater(user.getUserid());
			diseaseLogicSingle.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(diseaseLogicSingle);
			
			JSONArray liArry = JSONArray.fromObject(model.getLi());
			@SuppressWarnings("unchecked")
			List<DiseaseLogicItemDTO> lis = (List<DiseaseLogicItemDTO>) JSONArray.toCollection(liArry,DiseaseLogicItemDTO.class);
			for (DiseaseLogicItemDTO diseaseLogicItemDTO : lis) {
				DiseaseLogicSingleItem diseaseLogicSingleItem = new DiseaseLogicSingleItem();
				diseaseLogicSingleItem.setLogic_single_id(diseaseLogicSingle.getId());
				diseaseLogicSingleItem.setCritical_flag(diseaseLogicItemDTO.getCritical_flag());
				diseaseLogicSingleItem.setIsActive("Y");
				diseaseLogicSingleItem.setLogic_index(diseaseLogicItemDTO.getLogic_index());
				diseaseLogicSingleItem.setLogic_item_name(diseaseLogicItemDTO.getLogic_item_name());
				diseaseLogicSingleItem.setCreater(user.getUserid());
				diseaseLogicSingleItem.setCreate_time(DateTimeUtil.parse());
				diseaseLogicSingleItem.setUpdater(user.getUserid());
				diseaseLogicSingleItem.setUpdate_time(DateTimeUtil.parse());
				this.pm.save(diseaseLogicSingleItem);
				
				@SuppressWarnings("unchecked")
				List<DiseaseLogicItemConditionDTO> conlist = (List<DiseaseLogicItemConditionDTO>) JSONArray.toCollection(diseaseLogicItemDTO.getItemConditions(),DiseaseLogicItemConditionDTO.class);
				
				for (DiseaseLogicItemConditionDTO diseaseLogicItemConditionDTO : conlist) {
					DiseaseLogicSingleItemCondition diseaseLogicSingleItemCondition = new DiseaseLogicSingleItemCondition();
					diseaseLogicSingleItemCondition.setLogic_single_item_id(diseaseLogicSingleItem.getId());
					diseaseLogicSingleItemCondition.setLogic_index(diseaseLogicItemConditionDTO.getLogic_index());
					diseaseLogicSingleItemCondition.setCondition(diseaseLogicItemConditionDTO.getCondition());
					diseaseLogicSingleItemCondition.setCondition_value(diseaseLogicItemConditionDTO.getCondition_value());
					this.pm.save(diseaseLogicSingleItemCondition);
				}
			}
		}
	}

	@Override
	public DiseaseLogicDTO getDiseaseLogicSingleById(String id, long logic_class, String center_num ) throws ServiceException {
		String sql = "SELECT c.id as ids,c.logic_name,c.disease_num,c.item_code item_num,e.item_name,c.sex,"
				+ "convert(varchar(100),c.age_min) as age_min,convert(varchar(100),c.age_max) as age_max,c.isActive,c.logic_class "
				+ "FROM disease_logic_single c,disease_knowloedge_lib d,examination_item e "
				+ "where c.disease_num = d.disease_num and c.item_code = e.item_num and c.id = '"+id+"'";
		if(logic_class == 1){
			sql = "SELECT c.id as ids,c.logic_name,c.disease_num,c.item_code item_num,e.dep_name item_name,c.sex,"
				+ "convert(varchar(100),c.age_min) as age_min,convert(varchar(100),c.age_max) as age_max,c.isActive,c.logic_class "
				+ "FROM disease_logic_single c,disease_knowloedge_lib d,department_dep e ,department_vs_center de "
				+ "where c.disease_num = d.disease_num and de.dep_id = e.id   and c.item_code = e.dep_num and c.id = '"+id+"' and de.center_num ='"+center_num+"' ";
		}else if(logic_class == 2){
			sql = "SELECT c.id as ids,c.logic_name,c.disease_num,c.item_code item_num,e.item_name,c.sex,"
					+ "convert(varchar(100),c.age_min) as age_min,convert(varchar(100),c.age_max) as age_max,c.isActive,c.logic_class "
					+ "FROM disease_logic_single c,disease_knowloedge_lib d,charging_item e "
					+ "where c.disease_num = d.disease_num and c.item_code = e.item_code and c.id = '"+id+"'";
		}
		List<DiseaseLogicDTO> list = this.jqm.getList(sql, DiseaseLogicDTO.class);
		if(list.size() > 0){
			DiseaseLogicDTO diseaseLogicDTO = list.get(0);
			String itemsql = "select d.logic_item_name,d.critical_flag,d.logic_index,d.id from disease_logic_single_item d "
					+ "where d.logic_single_id = '"+diseaseLogicDTO.getIds()+"' and d.isActive = 'Y' order by d.logic_index";
			List<DiseaseLogicItemDTO> itemlist = this.jqm.getList(itemsql, DiseaseLogicItemDTO.class);
			for (DiseaseLogicItemDTO diseaseLogicItemDTO : itemlist) {
				String consql = "select d.condition,d.condition_value from disease_logic_single_item_condition d "
						+ "where d.logic_single_item_id = '"+diseaseLogicItemDTO.getId()+"' order by d.logic_index";
				List<DiseaseLogicItemConditionDTO> conlist = this.jqm.getList(consql, DiseaseLogicItemConditionDTO.class);
				diseaseLogicItemDTO.setItemCondition(conlist);
			}
			diseaseLogicDTO.setLogicItem(itemlist);
			return diseaseLogicDTO;
		}
		return null;
	}

	@Override
	public String diseaseLogicSingleStartOrEnd(String ids,String isActive,UserDTO user) throws ServiceException {
		List<DiseaseLogicSingle> list = this.qm.find("from DiseaseLogicSingle where id = '"+ids+"'");
		if(list.size() > 0){
			DiseaseLogicSingle diseaseLogicSingle = list.get(0);
			diseaseLogicSingle.setIsActive(isActive);
			diseaseLogicSingle.setUpdater(user.getUserid());
			diseaseLogicSingle.setUpdate_time(DateTimeUtil.parse());
			this.pm.update(diseaseLogicSingle);
			if("Y".equals(isActive)){
				return "ok-启用单项阳性逻辑成功!";
			}else{
				return "ok-停用单项阳性逻辑成功!";
			}
		}
		return "error-单项阳性逻辑不存在!";
	}

	@Override
	public String delDiseaseLogicSingle(String ids) throws ServiceException {
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			List<DiseaseLogicSingleItem> itemlist = this.qm.find("from DiseaseLogicSingleItem where logic_single_id ='"+id[i]+"'");
			for (DiseaseLogicSingleItem diseaseLogicSingleItem : itemlist) {
				List<DiseaseLogicSingleItemCondition> condList = this.qm.find("from DiseaseLogicSingleItemCondition where logic_single_item_id ='"+diseaseLogicSingleItem.getId()+"'");
				for (DiseaseLogicSingleItemCondition diseaseLogicSingleItemCondition : condList) {
					this.pm.remove(diseaseLogicSingleItemCondition);
				}
				this.pm.remove(diseaseLogicSingleItem);
			}
			List<DiseaseLogicSingle> list = this.qm.find("from DiseaseLogicSingle where id = '"+id[i]+"'");
			if(list.size() > 0){
				this.pm.remove(list.get(0));
			}
		}
		return "ok-删除成功!";
	}

	@Override
	public PageReturnDTO getDiseaseLogicCompositeList(String logic_name, long depid, String isActive, int page,
			int pagesize) throws ServiceException {
		String sql = "SELECT us.chi_name,c.update_time,c.id as ids,c.logic_name,c.disease_num,"
				+ "c.sex,convert(varchar(100),c.age_min)+'-'+convert(varchar(100),c.age_max) as ageMinMax,c.isActive "
				+ "FROM disease_logic_composite c left join user_usr us on us.id = c.updater,"
				+ "disease_knowloedge_lib d where c.disease_num = d.disease_num ";

		if (logic_name != null && !"".equals(logic_name)) {
			sql += " and (c.logic_name like '%"+logic_name.trim()+"%' or d.disease_pinyin like '%"+logic_name.trim()+"%')";
		}
		if(depid > 0){
			sql +=" and d.dep_id = '"+depid+"'";
		}else if(depid == 0){
			sql += " and (d.dep_id = '0' or d.dep_id is null)";
		}
		if(isActive != null && !"".equals(isActive)){
			sql += " and c.isActive = '"+isActive+"'";
		}
		sql += " order by d.dep_id";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, DiseaseLogicDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			List<DiseaseLogicDTO> li = map.getList();
			for (DiseaseLogicDTO diseaseLogicDTO : li) {
				String tiaojian = "";
				String itemsql = "select d.logic_item_name,d.critical_flag,d.logic_index,d.id from disease_logic_composite_item d "
						+ "where d.logic_composite_id = '"+diseaseLogicDTO.getIds()+"' and d.isActive = 'Y' order by d.logic_index";
				List<DiseaseLogicItemDTO> itemlist = this.jqm.getList(itemsql, DiseaseLogicItemDTO.class);
				for (DiseaseLogicItemDTO diseaseLogicItemDTO : itemlist) {
					String consql = "select d.condition,d.condition_value,d.condition_type,d.diseaseOrItem_num,"
							+ "(case when d.condition_type = 0 then (select l.disease_name from disease_knowloedge_lib l where l.disease_num = d.diseaseOrItem_num) "
							+ "else (select l.item_name from examination_item l where l.item_num = d.diseaseOrItem_num) end) as item_name"
							+ " from disease_logic_composite_item_condition d "
							+ "where d.logic_composite_item_id = '"+diseaseLogicItemDTO.getId()+"' order by d.logic_index";
					List<DiseaseLogicItemConditionDTO> conlist = this.jqm.getList(consql, DiseaseLogicItemConditionDTO.class);
					
					if(diseaseLogicItemDTO.getCritical_flag() == 1){
						tiaojian += diseaseLogicItemDTO.getLogic_item_name()+"【危机】：";
					}else{
						tiaojian += diseaseLogicItemDTO.getLogic_item_name()+"：";
					}
					for (int i = 0; i < conlist.size(); i++) {
						if (i == conlist.size() - 1) {
							if(conlist.get(i).getCondition_type() == 1){
								tiaojian += conlist.get(i).getItem_name() + conlist.get(i).getCondition() + conlist.get(i).getCondition_value();
							}else{
								tiaojian += "存在阳性-"+conlist.get(i).getItem_name();
							}
						}else{
							if(conlist.get(i).getCondition_type() == 1){
								tiaojian += conlist.get(i).getItem_name() + conlist.get(i).getCondition() + conlist.get(i).getCondition_value() + "&nbsp;and&nbsp;";
							}else{
								tiaojian += "存在阳性-"+conlist.get(i).getItem_name()+ "&nbsp;and&nbsp;";
							}
						}
					}
					tiaojian += "<br/>";
				}
				diseaseLogicDTO.setTiaojian(tiaojian);
			}
			webrole.setRows(li);
		}
		return webrole;
	}

	@Override
	public String saveDiseaseLogicComposite(DiseaseLogicModel model, UserDTO user) throws ServiceException {
		if(model.getIds() != null && !"".equals(model.getIds())){//修改
			List<DiseaseLogicCompositeItem> itemlist = this.qm.find("from DiseaseLogicCompositeItem where logic_composite_id ='"+model.getIds()+"'");
			for (DiseaseLogicCompositeItem diseaseLogicCompositeItem : itemlist) {
				List<DiseaseLogicCompositeItemCondition> condList = this.qm.find("from DiseaseLogicCompositeItemCondition where logic_composite_item_id ='"+diseaseLogicCompositeItem.getId()+"'");
				for (DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition : condList) {
					this.pm.remove(diseaseLogicCompositeItemCondition);
				}
				this.pm.remove(diseaseLogicCompositeItem);
			}
			
			List<DiseaseLogicComposite> list = this.qm.find("from DiseaseLogicComposite where id = '"+model.getIds()+"'");
			if(list.size() > 0){
				DiseaseLogicComposite diseaseLogicComposite = list.get(0);
				diseaseLogicComposite.setDisease_num(model.getDisease_num());
				diseaseLogicComposite.setLogic_name(model.getLogic_name());
				diseaseLogicComposite.setSex(model.getSex());
				diseaseLogicComposite.setAge_min(Integer.parseInt(model.getAge_min()));
				diseaseLogicComposite.setAge_max(Integer.parseInt(model.getAge_max()));
				diseaseLogicComposite.setUpdater(user.getUserid());
				diseaseLogicComposite.setUpdate_time(DateTimeUtil.parse());
				this.pm.update(diseaseLogicComposite);
				
				JSONArray liArry = JSONArray.fromObject(model.getLi());
				@SuppressWarnings("unchecked")
				List<DiseaseLogicItemDTO> lis = (List<DiseaseLogicItemDTO>) JSONArray.toCollection(liArry,DiseaseLogicItemDTO.class);
				for (DiseaseLogicItemDTO diseaseLogicItemDTO : lis) {
					DiseaseLogicCompositeItem diseaseLogicCompositeItem = new DiseaseLogicCompositeItem();
					diseaseLogicCompositeItem.setLogic_composite_id(diseaseLogicComposite.getId());
					diseaseLogicCompositeItem.setCritical_flag(diseaseLogicItemDTO.getCritical_flag());
					diseaseLogicCompositeItem.setIsActive("Y");
					diseaseLogicCompositeItem.setLogic_index(diseaseLogicItemDTO.getLogic_index());
					diseaseLogicCompositeItem.setLogic_item_name(diseaseLogicItemDTO.getLogic_item_name());
					diseaseLogicCompositeItem.setCreater(user.getUserid());
					diseaseLogicCompositeItem.setCreate_time(DateTimeUtil.parse());
					diseaseLogicCompositeItem.setUpdater(user.getUserid());
					diseaseLogicCompositeItem.setUpdate_time(DateTimeUtil.parse());
					this.pm.save(diseaseLogicCompositeItem);
					
					JSONArray conArry = JSONArray.fromObject(diseaseLogicItemDTO.getItemConditions());
					@SuppressWarnings("unchecked")
					List<DiseaseLogicItemConditionDTO> conlist = (List<DiseaseLogicItemConditionDTO>) JSONArray.toCollection(conArry,DiseaseLogicItemConditionDTO.class);
					
					for (DiseaseLogicItemConditionDTO diseaseLogicItemConditionDTO : conlist) {
						DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition = new DiseaseLogicCompositeItemCondition();
						diseaseLogicCompositeItemCondition.setLogic_composite_item_id(diseaseLogicCompositeItem.getId());
						diseaseLogicCompositeItemCondition.setDiseaseOrItem_num(diseaseLogicItemConditionDTO.getDiseaseOrItem_num());
						diseaseLogicCompositeItemCondition.setCondition_type(diseaseLogicItemConditionDTO.getCondition_type());
						diseaseLogicCompositeItemCondition.setLogic_index(diseaseLogicItemConditionDTO.getLogic_index());
						diseaseLogicCompositeItemCondition.setCondition(diseaseLogicItemConditionDTO.getCondition());
						diseaseLogicCompositeItemCondition.setCondition_value(diseaseLogicItemConditionDTO.getCondition_value());
						this.pm.save(diseaseLogicCompositeItemCondition);
					}
				}
			}
		}else{//新增
			DiseaseLogicComposite diseaseLogicComposite = new DiseaseLogicComposite();
			diseaseLogicComposite.setDisease_num(model.getDisease_num());
			diseaseLogicComposite.setLogic_name(model.getLogic_name());
			diseaseLogicComposite.setSex(model.getSex());
			diseaseLogicComposite.setAge_min(Integer.parseInt(model.getAge_min()));
			diseaseLogicComposite.setAge_max(Integer.parseInt(model.getAge_max()));
			diseaseLogicComposite.setIsActive("Y");
			diseaseLogicComposite.setCreater(user.getUserid());
			diseaseLogicComposite.setCreate_time(DateTimeUtil.parse());
			diseaseLogicComposite.setUpdater(user.getUserid());
			diseaseLogicComposite.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(diseaseLogicComposite);
			
			JSONArray liArry = JSONArray.fromObject(model.getLi());
			@SuppressWarnings("unchecked")
			List<DiseaseLogicItemDTO> lis = (List<DiseaseLogicItemDTO>) JSONArray.toCollection(liArry,DiseaseLogicItemDTO.class);
			for (DiseaseLogicItemDTO diseaseLogicItemDTO : lis) {
				DiseaseLogicCompositeItem diseaseLogicCompositeItem = new DiseaseLogicCompositeItem();
				diseaseLogicCompositeItem.setLogic_composite_id(diseaseLogicComposite.getId());
				diseaseLogicCompositeItem.setCritical_flag(diseaseLogicItemDTO.getCritical_flag());
				diseaseLogicCompositeItem.setIsActive("Y");
				diseaseLogicCompositeItem.setLogic_index(diseaseLogicItemDTO.getLogic_index());
				diseaseLogicCompositeItem.setLogic_item_name(diseaseLogicItemDTO.getLogic_item_name());
				diseaseLogicCompositeItem.setCreater(user.getUserid());
				diseaseLogicCompositeItem.setCreate_time(DateTimeUtil.parse());
				diseaseLogicCompositeItem.setUpdater(user.getUserid());
				diseaseLogicCompositeItem.setUpdate_time(DateTimeUtil.parse());
				this.pm.save(diseaseLogicCompositeItem);
				
				JSONArray conArry = JSONArray.fromObject(diseaseLogicItemDTO.getItemConditions());
				@SuppressWarnings("unchecked")
				List<DiseaseLogicItemConditionDTO> conlist = (List<DiseaseLogicItemConditionDTO>) JSONArray.toCollection(conArry,DiseaseLogicItemConditionDTO.class);
				
				for (DiseaseLogicItemConditionDTO diseaseLogicItemConditionDTO : conlist) {
					DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition = new DiseaseLogicCompositeItemCondition();
					diseaseLogicCompositeItemCondition.setLogic_composite_item_id(diseaseLogicCompositeItem.getId());
					diseaseLogicCompositeItemCondition.setDiseaseOrItem_num(diseaseLogicItemConditionDTO.getDiseaseOrItem_num());
					diseaseLogicCompositeItemCondition.setCondition_type(diseaseLogicItemConditionDTO.getCondition_type());
					diseaseLogicCompositeItemCondition.setLogic_index(diseaseLogicItemConditionDTO.getLogic_index());
					diseaseLogicCompositeItemCondition.setCondition(diseaseLogicItemConditionDTO.getCondition());
					diseaseLogicCompositeItemCondition.setCondition_value(diseaseLogicItemConditionDTO.getCondition_value());
					this.pm.save(diseaseLogicCompositeItemCondition);
				}
			}
		}
		return "ok-保存复合疾病逻辑信息成功!";
	}

	@Override
	public DiseaseLogicDTO getDiseaseLogicCompositeById(String id) throws ServiceException {
		String sql = "SELECT c.id as ids,c.logic_name,c.disease_num,c.sex,"
				+ "convert(varchar(100),c.age_min) as age_min,convert(varchar(100),c.age_max) as age_max,c.isActive "
				+ "FROM disease_logic_composite c,disease_knowloedge_lib d "
				+ "where c.disease_num = d.disease_num and c.id = '"+id+"'";
		List<DiseaseLogicDTO> list = this.jqm.getList(sql, DiseaseLogicDTO.class);
		if(list.size() > 0){
			DiseaseLogicDTO diseaseLogicDTO = list.get(0);
			String itemsql = "select d.logic_item_name,d.critical_flag,d.logic_index,d.id from disease_logic_composite_item d "
					+ "where d.logic_composite_id = '"+diseaseLogicDTO.getIds()+"' and d.isActive = 'Y' order by d.logic_index";
			List<DiseaseLogicItemDTO> itemlist = this.jqm.getList(itemsql, DiseaseLogicItemDTO.class);
			for (DiseaseLogicItemDTO diseaseLogicItemDTO : itemlist) {
				String consql = "select d.condition,d.condition_value,d.condition_type,d.diseaseOrItem_num,"
						+ "(case when d.condition_type = 0 then (select l.disease_name from disease_knowloedge_lib l where l.disease_num = d.diseaseOrItem_num) "
						+ "else (select l.item_name from examination_item l where l.item_num = d.diseaseOrItem_num) end) as item_name"
						+ " from disease_logic_composite_item_condition d "
						+ "where d.logic_composite_item_id = '"+diseaseLogicItemDTO.getId()+"' order by d.logic_index";
				List<DiseaseLogicItemConditionDTO> conlist = this.jqm.getList(consql, DiseaseLogicItemConditionDTO.class);
				diseaseLogicItemDTO.setItemCondition(conlist);
			}
			diseaseLogicDTO.setLogicItem(itemlist);
			return diseaseLogicDTO;
		}
		return null;
	}

	@Override
	public String diseaseLogicCompositeStartOrEnd(String ids, String isActive, UserDTO user) throws ServiceException {
		List<DiseaseLogicComposite> list = this.qm.find("from DiseaseLogicComposite where id = '"+ids+"'");
		if(list.size() > 0){
			DiseaseLogicComposite diseaseLogicComposite = list.get(0);
			diseaseLogicComposite.setIsActive(isActive);
			diseaseLogicComposite.setUpdater(user.getUserid());
			diseaseLogicComposite.setUpdate_time(DateTimeUtil.parse());
			this.pm.update(diseaseLogicComposite);
			if("Y".equals(isActive)){
				return "ok-启用单项阳性逻辑成功!";
			}else{
				return "ok-停用单项阳性逻辑成功!";
			}
		}
		return "error-单项阳性逻辑不存在!";
	}

	@Override
	public String delDiseaseLogicComposite(String ids) throws ServiceException {
		String[] id = ids.split(",");
		for (int i = 0; i < id.length; i++) {
			List<DiseaseLogicCompositeItem> itemlist = this.qm.find("from DiseaseLogicCompositeItem where logic_composite_id ='"+id[i]+"'");
			for (DiseaseLogicCompositeItem diseaseLogicCompositeItem : itemlist) {
				List<DiseaseLogicCompositeItemCondition> condList = this.qm.find("from DiseaseLogicCompositeItemCondition where logic_composite_item_id ='"+diseaseLogicCompositeItem.getId()+"'");
				for (DiseaseLogicCompositeItemCondition diseaseLogicCompositeItemCondition : condList) {
					this.pm.remove(diseaseLogicCompositeItemCondition);
				}
				this.pm.remove(diseaseLogicCompositeItem);
			}
			List<DiseaseLogicComposite> list = this.qm.find("from DiseaseLogicComposite where id = '"+id[i]+"'");
			if(list.size() > 0){
				this.pm.remove(list.get(0));
			}
		}
		return "ok-删除成功!";
	}

	@Override
	public List<ExaminationItemDTO> getDiseaseLogicSingleDepList(String dep_name, String dep_id, String center_num )
			throws ServiceException {
		String sql = "select d.id,d.dep_name item_name,d.dep_num item_num from department_dep d left join department_vs_center de on de.dep_id = d.id  "
				   + "where  de.center_num ='"+center_num+"' and d.dep_category in ('21','17') and d.isActive = 'Y' ";
		if(dep_name != null && !"".equals(dep_name)){
			sql +=" and d.dep_name like '%"+dep_name+"%'";
		}
		if(dep_id != null && !"".equals(dep_id)){
			sql += " and d.id = "+dep_id;
		}
		sql += " order by d.seq_code";
		List<ExaminationItemDTO> list = this.jqm.getList(sql, ExaminationItemDTO.class);
		return list;
	}

	@Override
	public String createDepLogicSingleDisease(ExamInfoDTO examinfo,DepExamResultDTO examResult,long userid) {
		//删除已经生成的危机和单项阳性
//		String delcSql = "delete exam_Critical_detail where exam_num = '"+examinfo.getExam_num()+"' and charging_item_code = '"+examResult.getItem_code()+"' "
//				+ "and item_code = '"+examResult.getItem_num()+"' and disease_num is not null";
//		this.jpm.executeSql(delcSql);
		String delsSql = "delete examinfo_disease_single where exam_num = '"+examinfo.getExam_num()+"' and charging_item_code = '"+examResult.getItem_code()+"' ";
		
		String item_code = examResult.getItem_code();
		if(examResult.getResult_type() == 0){
			delsSql += "and item_code = '"+examResult.getItem_num()+"'";
			
			item_code = examResult.getItem_num();
		}
		this.jpm.executeSql(delsSql);
		//生成新的危机和单项阳性
		String logicsql = "select d.disease_num,l.id disease_id,d.id ids,l.disease_name from disease_logic_single d,disease_knowloedge_lib l where "
				+ "d.isActive = 'Y' and d.disease_num = l.disease_num and d.logic_class = "+examResult.getResult_type()+" and d.item_code = '"+item_code+"' "
				+ "and d.sex in ('全部','"+examinfo.getSex()+"') and d.age_min <= '"+examinfo.getAge()+"' and d.age_max >= '"+examinfo.getAge()+"'";
		List<DiseaseLogicDTO> logiclist = this.jqm.getList(logicsql, DiseaseLogicDTO.class);
		
		Map<String,DiseaseLogicDTO> singleDiseaseMap = new HashMap<String, DiseaseLogicDTO>();//单项阳性（包含危机条件的阳性）
//		Map<String,DiseaseLogicDTO> criticalDiseaseMap = new HashMap<String, DiseaseLogicDTO>();//危机阳性
		
		for (DiseaseLogicDTO diseaseLogicDTO : logiclist) {
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
					if(convertLogic(examResult,diseaseLogicItemConditionDTO)){
						count ++;
					}
				}
				if(count > 0 && count == conlist.size()){//满足条件生成的疾病放入MAP中，结束该逻辑循环，进行下一条逻辑循环
//					if(diseaseLogicItemDTO.getCritical_flag() == 1){
//						criticalDiseaseMap.put(diseaseLogicDTO.getDisease_num(), diseaseLogicDTO);
//					}
					singleDiseaseMap.put(diseaseLogicDTO.getDisease_num(), diseaseLogicDTO);
					continue;
				}
			}
		}
		
//		//将自动生成的危机阳性插入到危急值表中
//		for (Map.Entry<String, DiseaseLogicDTO> m : criticalDiseaseMap.entrySet()) {
//			String sql = "select c.id from exam_Critical_detail c where c.exam_num = '"+examinfo.getExam_num()+"' and c.item_code = '"+examResult.getItem_num() +"' and c.charging_item_code = '"+examResult.getItem_code()+"'";
//			List<CriticalDTO>  list = this.jqm.getList(sql, CriticalDTO.class);
//			if(list.size() > 0){
//				String criinsql = "update exam_Critical_detail set disease_id = '"+m.getValue().getDisease_id()+"',disease_num='"+m.getValue().getDisease_num()+"' where id = '"+list.get(0).getId()+"'";
//				this.jpm.executeSql(criinsql);
//			}else{
//				String criinsql = "insert into exam_Critical_detail(exam_info_id,dept_id,charging_item_id,exam_item_id,exam_result,check_date,is_active,creater,create_time,exam_num,item_code,charging_item_code,disease_id,disease_num) "
//						+ "values('"+examinfo.getId()+"','"+examResult.getDep_id()+"','"+examResult.getCharging_item_id()+"','"+examResult.getExam_item_id()+"','"+examResult.getExam_result()+"'"
//								+ ",'"+DateTimeUtil.getDateTime()+"','Y','"+userid+"','"+DateTimeUtil.getDateTime()+"','"+examinfo.getExam_num()+"','"+examResult.getItem_num()+"','"+examResult.getItem_code()+"','"+m.getValue().getDisease_id()+"','"+m.getValue().getDisease_num()+"')";
//				this.jpm.executeSql(criinsql);
//			}
//		}
		//将自动生成的单项阳性插入到单项阳性表中
		for (Map.Entry<String, DiseaseLogicDTO> m : singleDiseaseMap.entrySet()) {
			String singlesql = "insert into examinfo_disease_single(id,exam_num,dep_num,charging_item_code,disease_num,item_code,disease_name,creater,create_time) "
					+ "values('"+UUID.randomUUID()+"','"+examinfo.getExam_num()+"','"+examResult.getDep_num()+"','"+examResult.getItem_code()+"'"
					+ ",'"+m.getValue().getDisease_num()+"','"+examResult.getItem_num()+"','"+m.getValue().getDisease_name()+"','"+userid+"','"+DateTimeUtil.getDateTime()+"')";
			this.jpm.executeSql(singlesql);
		}
		return "ok-自动生成阳性成功!";
	}
	
	@Override
	public String createDepLogicSingleDiseaseDep(ExamInfoDTO examinfo,DepExamResultDTO examResult,long userid) {
//		//删除已经生成的危机和单项阳性
//		String delcSql = "delete exam_Critical_detail where exam_num = '"+examinfo.getExam_num()+"' and dept_id = '"+examResult.getDep_id()+"' ";
//		this.jpm.executeSql(delcSql);
		String delsSql = "delete examinfo_disease_single where exam_num = '"+examinfo.getExam_num()+"' and dep_num = '"+examResult.getDep_num()+"' ";
		this.jpm.executeSql(delsSql);
		//生成新的危机和单项阳性
		String logicsql = "select d.disease_num,l.id disease_id,d.id ids,l.disease_name from disease_logic_single d,disease_knowloedge_lib l where "
				+ "d.isActive = 'Y' and d.disease_num = l.disease_num and d.logic_class = 1 and d.item_code = '"+examResult.getDep_num()+"' "
				+ "and d.sex in ('全部','"+examinfo.getSex()+"') and d.age_min <= '"+examinfo.getAge()+"' and d.age_max >= '"+examinfo.getAge()+"'";
		List<DiseaseLogicDTO> logiclist = this.jqm.getList(logicsql, DiseaseLogicDTO.class);
		
		Map<String,DiseaseLogicDTO> singleDiseaseMap = new HashMap<String, DiseaseLogicDTO>();//单项阳性（包含危机条件的阳性）
		Map<String,DiseaseLogicDTO> criticalDiseaseMap = new HashMap<String, DiseaseLogicDTO>();//危机阳性
		
		for (DiseaseLogicDTO diseaseLogicDTO : logiclist) {
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
					if(convertLogic(examResult,diseaseLogicItemConditionDTO)){
						count ++;
					}
				}
				if(count > 0 && count == conlist.size()){//满足条件生成的疾病放入MAP中，结束该逻辑循环，进行下一条逻辑循环
					if(diseaseLogicItemDTO.getCritical_flag() == 1){
						criticalDiseaseMap.put(diseaseLogicDTO.getDisease_num(), diseaseLogicDTO);
					}
					singleDiseaseMap.put(diseaseLogicDTO.getDisease_num(), diseaseLogicDTO);
					continue;
				}
			}
		}
		
//		//将自动生成的危机阳性插入到危急值表中
//		for (Map.Entry<String, DiseaseLogicDTO> m : criticalDiseaseMap.entrySet()) {
//			String criinsql = "insert into exam_Critical_detail(exam_info_id,dept_id,charging_item_id,exam_item_id,exam_result,check_date,is_active,creater,create_time,exam_num,item_code,charging_item_code,disease_id,disease_num) "
//					+ "values('"+examinfo.getId()+"','"+examResult.getDep_id()+"','0','0','"+examResult.getExam_result()+"'"
//							+ ",'"+DateTimeUtil.getDateTime()+"','Y','"+userid+"','"+DateTimeUtil.getDateTime()+"','"+examinfo.getExam_num()+"','','','"+m.getValue().getDisease_id()+"','"+m.getValue().getDisease_num()+"')";
//			this.jpm.executeSql(criinsql);
//		}
		//将自动生成的单项阳性插入到单项阳性表中
		for (Map.Entry<String, DiseaseLogicDTO> m : singleDiseaseMap.entrySet()) {
			String singlesql = "insert into examinfo_disease_single(id,exam_num,dep_num,charging_item_code,disease_num,item_code,disease_name,creater,create_time) "
					+ "values('"+UUID.randomUUID()+"','"+examinfo.getExam_num()+"','"+examResult.getDep_num()+"',''"
					+ ",'"+m.getValue().getDisease_num()+"','','"+m.getValue().getDisease_name()+"','"+userid+"','"+DateTimeUtil.getDateTime()+"')";
			this.jpm.executeSql(singlesql);
		}
		return "ok-自动生成阳性成功!";
	}
	
	private boolean convertLogic(DepExamResultDTO resultDto2,DiseaseLogicItemConditionDTO logicItem){
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
}
