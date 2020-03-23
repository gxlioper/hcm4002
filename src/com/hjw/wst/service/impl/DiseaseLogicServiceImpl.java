package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.DiseaseLogicExamItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.DiseaseLogic;
import com.hjw.wst.domain.DiseaseLogicExamItem;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.model.DiseaseLogicModel;
import com.hjw.wst.service.DiseaseLogicService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;

public class DiseaseLogicServiceImpl implements DiseaseLogicService {
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
	public PageReturnDTO queryDiseaseLogic(DiseaseLogicModel model, int page, int pagesize, UserDTO user)
			throws ServiceException {
		String sql = "";

		if (model.getDep_id() != null && !"".equals(model.getDep_id())) {
			sql = " SELECT c.update_time,c.id,c.logic_name,c.logic_type,c.logic_num,"
					+ "(SELECT us.chi_name FROM user_usr us  where  us.id = c.updater )  as  chi_name"
					+ " FROM disease_logic c,disease_knowloedge_lib di, "
					+ "disease_dep ep  where  di.id=ep.disease_id  and  di.isActive='Y' and c.disease_id=di.id  and  c.isActive='Y'"
					+ " and ep.dep_id in(" + model.getDep_id() + ")  ";
		} else {
			sql = "SELECT  us.chi_name,c.update_time,c.id,c.logic_name,c.logic_type,c.logic_num FROM disease_logic c left join user_usr us  on  us.id = c.updater "
					+ "  where c.isActive='Y'    ";
		}
		if (null != model.getLogic_name() && !"".equals(model.getLogic_name())) {
			sql += "   and   c.logic_name   like '%" + model.getLogic_name().trim() + "%'";
		}
		if (null != model.getLogic_num() && !"".equals(model.getLogic_num())) {
			sql += "   and   li.disease_num='" + model.getLogic_num().trim() + "'";
		}
		if (null != model.getLogic_type() && !"".equals(model.getLogic_type())) {
			sql += "   and   c.logic_type='" + model.getLogic_type() + "'";
		}
		sql += "   order by  c.id  desc";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, DiseaseLogicDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			List<DiseaseLogicDTO> li = map.getList();
			String tiaojian = "";
			for (DiseaseLogicDTO d : li) {
				DiseaseLogicDTO dto = new DiseaseLogicDTO();
				String sql2 = "SELECT   s.disease_logic_id,ex.item_name,s.condition,s.condition_value,s.andOrNo as  tiaojian "
						+ "FROM disease_logic_exam_item  s,examination_item  ex WHERE   "
						+ " s.item_code=ex.item_num  and   ex.is_Active='Y'   and  s.disease_logic_id='" + d.getId() + "'";
				List<DiseaseLogicDTO> list = this.jqm.getList(sql2, DiseaseLogicDTO.class);
				if (list.size() > 0) {
					tiaojian = "";
					for (int i = 0; i < list.size(); i++) {
						if (i == list.size() - 1) {
							tiaojian += list.get(i).getItem_name() + list.get(i).getCondition()
									+ list.get(i).getCondition_value();
						} else {
							tiaojian += list.get(i).getItem_name() + list.get(i).getCondition()
									+ list.get(i).getCondition_value() + list.get(i).getTiaojian();
						}
					}
				} else {
					tiaojian = "";
				}
				d.setTiaojian(tiaojian);
			}
			webrole.setRows(li);
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO queryDiseaseLogic_all(DiseaseLogicModel model, int page, int pagesize, UserDTO user)
			throws ServiceException {
		String sql = "";
		
		if (model.getDep_id() != null && !"".equals(model.getDep_id())) {
			sql = " SELECT c.update_time,c.id,c.logic_name,c.logic_type,c.logic_num,c.isActive,"
					+ "(SELECT us.chi_name FROM user_usr us  where  us.id = c.updater )  as  chi_name,c.critical_flag, c.sex, (c.age_min +'-'+ age_max) as ageMinMax "
					+ " FROM disease_logic c,disease_knowloedge_lib di, "
					+ "disease_dep ep  where  di.id=ep.disease_id  and  di.isActive='Y' and c.disease_id=di.id "// and  c.isActive='Y'
					+ " and ep.dep_id in(" + model.getDep_id() + ")  ";
		} else {
			sql = "SELECT  us.chi_name,c.update_time,c.id,c.logic_name,c.logic_type,c.logic_num,c.isActive ,c.critical_flag, c.sex, (c.age_min +'-'+ age_max) as ageMinMax "
					+ "FROM disease_logic c left join user_usr us  on  us.id = c.updater "
					+ "  where 1=1    ";//c.isActive='Y'
		}
		if (null != model.getLogic_name() && !"".equals(model.getLogic_name())) {
			sql += "   and   c.logic_name   like '%" + model.getLogic_name().trim() + "%'";
		}
		if (null != model.getLogic_num() && !"".equals(model.getLogic_num())) {
			sql += "   and   li.disease_num='" + model.getLogic_num().trim() + "'";
		}
		if (null != model.getLogic_type() && !"".equals(model.getLogic_type())) {
			sql += "   and   c.logic_type='" + model.getLogic_type() + "'";
		}
		sql += "   order by  c.id  desc";
		PageSupport map = (PageSupport) this.jqm.getList(sql, page, pagesize, DiseaseLogicDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			List<DiseaseLogicDTO> li = map.getList();
			String tiaojian = "";
			for (DiseaseLogicDTO d : li) {
				DiseaseLogicDTO dto = new DiseaseLogicDTO();
				String sql2 = "SELECT   s.disease_logic_id,ex.item_name,s.condition,s.condition_value,s.andOrNo as  tiaojian "
						+ "FROM disease_logic_exam_item  s,examination_item  ex WHERE   "
						+ " s.item_code=ex.item_num  and   ex.is_Active='Y'   and  s.disease_logic_id='" + d.getId() + "'";
				List<DiseaseLogicDTO> list = this.jqm.getList(sql2, DiseaseLogicDTO.class);
				if (list.size() > 0) {
					tiaojian = "";
					for (int i = 0; i < list.size(); i++) {
						if (i == list.size() - 1) {
							tiaojian += list.get(i).getItem_name() + list.get(i).getCondition()
									+ list.get(i).getCondition_value();
						} else {
							tiaojian += list.get(i).getItem_name() + list.get(i).getCondition()
									+ list.get(i).getCondition_value() + list.get(i).getTiaojian();
						}
					}
				} else {
					tiaojian = "";
				}
				d.setTiaojian(tiaojian);
			}
			webrole.setRows(li);
		}
		return webrole;
	}

	@Override
	public void deletDiseaseLogic(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			String hql = " FROM DiseaseLogicExamItem   WHERE    disease_logic_id   in(" + ids + ")";
			List<DiseaseLogicExamItem> li = this.qm.find(hql);
			for (DiseaseLogicExamItem diseaseLogicExamItem : li) {
				this.pm.remove(diseaseLogicExamItem);
			}
			
		    connection = this.jqm.getConnection();
		    sql = "delete from disease_logic  where  isActive='N'  and  id in(" + ids + ")";
		    int rs = connection.createStatement() .executeUpdate(sql);
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
	public void update_diseaseLogic_off(String ids) throws ServiceException {
		String sql = "update disease_logic set isActive = 'N' where id in ("+ids+")";
		this.jpm.executeSql(sql);
	}
	
	@Override
	public void update_diseaseLogic_on(String ids) throws ServiceException {
		String sql = "update disease_logic set isActive = 'Y' where id in ("+ids+")";
		this.jpm.executeSql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChargingItemDTO> getChargingItem(DiseaseLogicModel model) throws ServiceException {
		String sql = "SELECT id,item_name,item_code,dep_category FROM charging_item  where  isActive='Y'  and   " + " (item_name  like  '%"
				+ model.getC_item_name().trim() + "%'   or   item_pinyin    like   '%" + model.getC_item_name().trim()+ "%') ";
				if(model.getDep_id() != null && !"".equals(model.getDep_id())){
					sql += " and dep_id = "+model.getDep_id();
				}
				sql += "order  by   item_name";
		return jqm.getList(sql, ChargingItemDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminationItemDTO> getExaminationItem(DiseaseLogicModel model) throws ServiceException {
		String sql = "SELECT  DISTINCT  e.id,e.item_name,e.item_num FROM examination_item  e,charging_item c,charging_item_exam_item  ce  where  e.is_Active='Y'  "
				+ "	 and c.isActive='Y' and   e.item_num=ce.item_code and c.item_code=ce.charging_item_code"
				+ "	and	 (e.item_name like '%" + model.getE_item_name().trim() + "%' or e.item_pinyin like  '%"
				+ model.getE_item_name().trim() + "%')	";
		if (!"".equals(model.getC_item_id()) && model.getC_item_id() != null) {
			sql += " and c.id='" + model.getC_item_id() + "' ";
		}
		if(model.getDep_id() != null && !"".equals(model.getDep_id())){
			sql += " and c.dep_id = "+model.getDep_id();
		}
		if(!"".equals(model.getCharging_item_code()) && model.getCharging_item_code() != null){
			sql += " and c.item_code='" + model.getCharging_item_code() + "'";
		}
		sql += " order by e.item_name";
		return jqm.getList(sql, ExaminationItemDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiseaseLogicDTO> getDiseaseKnowloedge(DiseaseLogicModel model) throws ServiceException {
		String hql = "select id,disease_num,disease_name from disease_knowloedge_lib   where  isActive='Y'" + " and (disease_name like '%"
				+ model.getDisease_name().trim() + "%' or disease_pinyin like '"+model.getDisease_name().trim()+"') and disease_type='" + model.getDisease_type() + "'";
		if(model.getDep_id() != null && !"".equals(model.getDep_id())){
			hql += " and dep_id = "+model.getDep_id();
		}
		List<DiseaseLogicDTO> list = this.jqm.getList(hql, DiseaseLogicDTO.class);
		return list;
	}

	@Override
	public void addDiseaseKnowloedge(DiseaseLogicModel model, UserDTO dto) throws ServiceException {

		String li = model.getLi();
		DiseaseLogic dise = new DiseaseLogic();
		dise.setLogic_type(model.getLogic_type());
		dise.setDisease_id(model.getDisease_id());
		dise.setLogic_name(model.getLogic_name());
		dise.setCritical_flag(model.getCritical_flag());
		dise.setSex(model.getSex());
		dise.setAge_max(model.getAge_max());
		dise.setAge_min(model.getAge_min());
		dise.setCreater(dto.getUserid());
		dise.setIsActive("Y");
		dise.setCreate_time(DateTimeUtil.parse());
		this.pm.save(dise);

		JSONArray liArry = JSONArray.fromObject(li);
		@SuppressWarnings("unchecked")
		List<DiseaseLogicExamItem> lis = (List<DiseaseLogicExamItem>) JSONArray.toCollection(liArry,
				DiseaseLogicExamItem.class);
		for (int i = 0; i < lis.size(); i++) {
			DiseaseLogicExamItem tem = new DiseaseLogicExamItem();
			tem.setExam_item_id(lis.get(i).getExam_item_id());
			tem.setCharging_item_id(lis.get(i).getCharging_item_id());
			tem.setDisease_logic_id(dise.getId());
			tem.setLogic_index(lis.get(i).getLogic_index());
			tem.setCondition(lis.get(i).getCondition());
			tem.setCondition_value(lis.get(i).getCondition_value());
			tem.setAndOrNo(lis.get(i).getAndOrNo());
			tem.setCreater(dto.getUserid());
			tem.setCreate_time(DateTimeUtil.parse());
			if(lis.get(i).getCharging_item_id() > 0){
				ChargingItem chargingitem = (ChargingItem) this.qm.load(ChargingItem.class, lis.get(i).getCharging_item_id());
				tem.setCharging_item_code(chargingitem.getItem_code());
			}
			ExaminationItem examitem = (ExaminationItem) this.qm.load(ExaminationItem.class, lis.get(i).getExam_item_id());
			tem.setItem_code(examitem.getItem_num());
			pm.save(tem);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public DiseaseLogicDTO getDiseaseLogic(long id) throws ServiceException {
		String sql = " SELECT  d.id,d.logic_type,d.age_max,d.age_min,d.sex,d.critical_flag,d.isActive,li.id  as   disease_id,li.disease_name  FROM"
				+ " disease_logic  d    left join  disease_knowloedge_lib  li on	li.id=d.disease_id   and	  li.isActive='Y'  "
				+ "  where   1=1   and  d.id='" + id + "'";	//d.isActive='Y'
		List<DiseaseLogicDTO> dto = this.jqm.getList(sql, DiseaseLogicDTO.class);
		return dto.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DiseaseLogicExamItemDTO> getDiseaseKnowloedge(long id) throws ServiceException {
		String sql = "select ch.id as  ch_id,ch.item_name  as  ch_name,exam.id   as  e_id,exam.item_name  as  e_name,"
				+ "di.condition,di.condition_value,di.andOrNo " + "	from disease_logic_exam_item  di"
				+ "	LEFT JOIN  examination_item  exam   on   di.item_code=exam.item_num   and  exam.is_Active='Y' "
				+ "	LEFT JOIN  charging_item  ch  on    di.charging_item_code=ch.item_code   and   ch.isActive='Y'"
				+ "	where    di.disease_logic_id='" + id + "'   order  by   di.logic_index";
		return this.jqm.getList(sql, DiseaseLogicExamItemDTO.class);
	}

	@Override
	public void updateDiseaseKnowloedge(DiseaseLogicModel model, UserDTO dto) throws ServiceException {

		String hql = "  FROM  DiseaseLogicExamItem   WHERE   disease_logic_id='" + model.getId() + "'";
		List<DiseaseLogicExamItem> dise = this.qm.find(hql);
		if (dise != null && dise.size() > 0) {
			for (DiseaseLogicExamItem diseaseLogicExamItem : dise) {
				this.pm.remove(diseaseLogicExamItem);
			}
		}

		hql = "  FROM  DiseaseLogic   WHERE   id='" + model.getId() + "'";
		List<DiseaseLogic> l = this.qm.find(hql);
		l.get(0).setLogic_name(model.getLogic_name());
		l.get(0).setLogic_type(model.getLogic_type());
		l.get(0).setCritical_flag(model.getCritical_flag());
		l.get(0).setDisease_id(model.getDisease_id());
		l.get(0).setSex(model.getSex());
		l.get(0).setAge_max(model.getAge_max());
		l.get(0).setAge_min(model.getAge_min());
		l.get(0).setUpdater(dto.getUserid());
		l.get(0).setUpdate_time(DateTimeUtil.parse());
		this.pm.update(l.get(0));

		JSONArray liArry = JSONArray.fromObject(model.getLi());
		@SuppressWarnings("unchecked")
		List<DiseaseLogicExamItem> lis = (List<DiseaseLogicExamItem>) JSONArray.toCollection(liArry,
				DiseaseLogicExamItem.class);
		for (int i = 0; i < lis.size(); i++) {
			DiseaseLogicExamItem tem = new DiseaseLogicExamItem();
			tem.setExam_item_id(lis.get(i).getExam_item_id());
			tem.setCharging_item_id(lis.get(i).getCharging_item_id());
			tem.setDisease_logic_id(model.getId());
			tem.setLogic_index(lis.get(i).getLogic_index());
			tem.setCondition(lis.get(i).getCondition());
			tem.setCondition_value(lis.get(i).getCondition_value());
			tem.setAndOrNo(lis.get(i).getAndOrNo());
			tem.setUpdater(dto.getUserid());
			tem.setUpdate_time(DateTimeUtil.parse());
			if(lis.get(i).getCharging_item_id() > 0){
				ChargingItem chargingitem = (ChargingItem) this.qm.load(ChargingItem.class, lis.get(i).getCharging_item_id());
				tem.setCharging_item_code(chargingitem.getItem_code());
			}
			ExaminationItem examitem = (ExaminationItem) this.qm.load(ExaminationItem.class, lis.get(i).getExam_item_id());
			tem.setItem_code(examitem.getItem_num());
			pm.save(tem);
		}

	}
}
