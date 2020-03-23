package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemExamItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ChargingItemExamItem;
import com.hjw.wst.domain.LimitChargingItem;
import com.hjw.wst.model.DiseaseLogicModel;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.DiseaseDepService;
import com.hjw.wst.service.WebSynchroService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

import net.sf.json.JSONArray;

public class DiseaseDepServiceImpl implements DiseaseDepService {

	
	private QueryManager qm; 
	private JdbcQueryManager jqm;
	private PersistenceManager pm; 
	private WebSynchroService webSynchroService;
	private ChargingItemService chargingItemService;
	
	
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
	
	public WebSynchroService getWebSynchroService() {
		return webSynchroService;
	}

	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}

	public List<DepartmentDepDTO> getDepartmentDepDTO(DiseaseLogicModel model, UserDTO user,String app_type) throws ServiceException {
		String sql ="";
		if("1".equals( model.getWeb_Resource())){
			sql = "SELECT    ep.id,ep.dep_name   FROM department_dep ep,department_vs_center vs  where  ep.isActive='Y'"
			    +" AND vs.dep_id = ep.id AND vs.center_num = '"+user.getCenter_num()+"' and ep.dep_category in (21,131,17) order by ep.seq_code "; 
		}  else {
			 sql= "	SELECT ep.id,ep.dep_name  FROM dep_user d,department_dep ep,user_usr  u,department_vs_center vs   "
				        +" where  d.dep_id=ep.id  and  ep.isActive='Y'  and  vs.dep_id = ep.id AND vs.center_num = '"+user.getCenter_num()+"' "
				        +" AND u.id=d.user_id and d.apptype = '"+app_type+"'  and  u.is_active='Y'  and   u.id='"+user.getUserid()+"' and ep.dep_category in (21,131,17) order by ep.seq_code ";
			 
		}
	    
		List<DepartmentDepDTO> li = jqm.getList(sql,DepartmentDepDTO.class);
		return li;
	}
	public List<DiseaseLogicDTO> getDepDiseaseKnowloedge(DiseaseLogicModel model, String center_num ) throws ServiceException {
		String sql = " select li.id,li.disease_name,li.disease_num from  disease_dep de,department_dep  dee,department_vs_center ded,disease_knowloedge_lib li "
		     +" where  de.dep_id=dee.id   and ded.dep_id = dee.id   and   dee.isActive='Y'  and   li.id=de.disease_id and de.center_num ='"+center_num+"'  and  dee.id in("+model.getDep_id()+")    ";
		     if( model.getDisease_name()!=null && !"".equals(model.getDisease_name())){
		    sql+="   and   (li.disease_name  like '%"+model.getDisease_name().trim()+"%'   or   li.disease_pinyin   like  '%"+model.getDisease_pinyin()+"%')  ";	 
		     }
		     
     		sql += "and  li.disease_type='"+model.getDisease_type()+"'";
		@SuppressWarnings("unchecked")
		List<DiseaseLogicDTO>  li = jqm.getList(sql,DiseaseLogicDTO.class);
		return  li;
	}
	public PageReturnDTO queryDiseaseDep(DiseaseLogicModel model) throws ServiceException {
		return null;
	}
	
	@Override
	public ChargingItem updateItemList(ChargingItem item,String itemList,long limitCount,UserDTO user) throws ServiceException {
		String hql = " FROM  ChargingItemExamItem  WHERE  charging_item_id = '"+item.getId()+"'";
		List<ChargingItemExamItem> li = this.qm.find(hql);
		for (int i = 0 ; i < li.size() ; i++) {
			this.pm.remove(li.get(i));
		}
		this.webSynchroService.updateWebSynchro(item.getId()+"",'1');
		updatelimitChargingItem(item.getId(),limitCount,user);
		
		JSONArray json = JSONArray.fromObject(itemList);
		List<ChargingItemExamItemDTO> bm = (List<ChargingItemExamItemDTO>) JSONArray.toCollection(json,ChargingItemExamItemDTO.class);

		ChargingItem chargingItem = chargingItemService.findChargingItem(item.getId());
        for(int o=0;o<bm.size();o++){
    		ChargingItemExamItem  am = new ChargingItemExamItem();
    		am.setCreater(user.getUserid());//收费项目关系表--创建人
    		am.setCreate_time(DateTimeUtil.parse());//检查项目关系表--创建时间
    		am.setCharging_item_id(item.getId());
    		am.setUpdater(user.getUserid());
    		am.setUpdate_time(DateTimeUtil.parse());
    		am.setExam_item_id(bm.get(o).getExam_item_id());
    		am.setSeq_code(bm.get(o).getSeq_code());
    		am.setCharging_item_code(item.getItem_code());
    		am.setItem_code(bm.get(o).getItem_num());
    		am.setCharging_item_code(chargingItem.getItem_code());
    		this.pm.save(am);
    		//updateSeq_code(bm.get(o).getSeq_code(),am.getExam_item_id());
        }
        
        return item;
		
	}
	public void updatelimitChargingItem(long id, long limit_count, UserDTO user) throws ServiceException {
		String sql = "SELECT * FROM  limit_charging_item   WHERE  charging_item_id='" + id + "'";
		List<LimitChargingItem> li = jqm.getList(sql, LimitChargingItem.class);
		if (li.size() > 0) {
			li.get(0).setCenter_num(user.getCenter_num());
			li.get(0).setCharging_item_id(id);
			li.get(0).setLimit_count(limit_count);
			li.get(0).setUpdater(user.getUserid());
			li.get(0).setUpdate_time(DateTimeUtil.parse());
			this.pm.update(li.get(0));
		} else {
			LimitChargingItem it = new LimitChargingItem();
			it.setCenter_num(user.getCenter_num());
			it.setCharging_item_id(id);
			it.setLimit_count(limit_count);
			it.setUpdater(user.getUserid());
			it.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(it);
		}
	}

}
