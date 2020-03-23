package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DepLogicDTO;
import com.hjw.wst.DTO.DepLogicExamItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DepLogic;
import com.hjw.wst.domain.DepLogicExamItem;
import com.hjw.wst.domain.DiseaseLogicExamItem;
import com.hjw.wst.model.DepLogicModel;
import com.hjw.wst.service.DepLogicService;
import com.hjw.wst.service.ExaminationItemService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONArray;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  科室疾病
     * @author: zr     
     * @date:   2016年12月13日 上午9:39:17   
     * @version V2.0.0.0
 */
public class DepLogicServiceImpl implements DepLogicService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	private ExaminationItemService examinationItem;
	
	public ExaminationItemService getExaminationItem() {
		return examinationItem;
	}

	public void setExaminationItem(ExaminationItemService examinationItem) {
		this.examinationItem = examinationItem;
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

	@Override
	public PageReturnDTO getDepLogic(DepLogicModel model, UserDTO user, int pageno, int pagesize)
			throws ServiceException {
			String  sql="SELECT lo.id,lo.dep_id,lo.conclusion_word,de.dep_name,lo.sex  FROM dep_logic lo,department_dep de,department_vs_center ded"
					+ "   where  de.id=lo.dep_id   and ded.dep_id = de.id   and ded.center_num ='"+user.getCenter_num()+"'  and    de.isActive='Y' ";
					if(model.getConclusion_word()!=null && !"".equals(model.getConclusion_word())){
					sql+="   and   lo.conclusion_word   like  '%"+model.getConclusion_word()+"%'";	
					}
					if(model.getDep_id()>0){
					sql+="   and   lo.dep_id='"+model.getDep_id()+"'";		
					}
					sql+="	  order    by  lo.update_time  desc";
			PageSupport map = this.jqm.getList(sql,pageno,pagesize,DepLogicDTO.class);
			PageReturnDTO webrole = new PageReturnDTO();
			webrole.setPage(pageno);
			webrole.setRp(pagesize);
			if ((map != null) && (map.getList() != null) && map.getList().size()>0) {
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
							List<DepLogicDTO> li = map.getList();
						String dep_logic_id = "";
						for (DepLogicDTO d : li) {
							dep_logic_id+=d.getId()+",";
						}
						
						dep_logic_id = dep_logic_id.substring(0,dep_logic_id.length()-1);
						sql="SELECT dep.id,dep.logic_id,ex.item_name,dep.condition,dep.condition_value,dep.andOrNo FROM "
							+"	dep_logic_exam_item dep,examination_item  ex"
							+"	where  ex.id=dep.exam_item_id  and   ex.is_Active='Y'  and   dep.logic_id  in("+dep_logic_id+")"
							+ "   order by   dep.logic_id";
				List<DepLogicExamItemDTO> dep_li = this.jqm.getList(sql,DepLogicExamItemDTO.class);
				String guanxi="";
				int s=1;
				if(dep_li != null && dep_li.size()>0){
					for (int i=0 ; i<li.size(); i++) {
						String tiaojian="";
						for(int j=0; j<dep_li.size(); j++){
							if(li.get(i).getId()==dep_li.get(j).getLogic_id()){
								if(s==1){
									tiaojian+=dep_li.get(j).getItem_name()+dep_li.get(j).getCondition()+dep_li.get(j).getCondition_value();
									s=2;
								}else{
									tiaojian+=dep_li.get(j-1).getAndOrNo()+dep_li.get(j).getItem_name()+dep_li.get(j).getCondition()+dep_li.get(j).getCondition_value();
								}
							}
						}
						s=1;
						li.get(i).setTiaojian(tiaojian);
					}
					
				}
				webrole.setRows(li);
			}
			return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteDepLogic(DepLogicModel model) throws ServiceException {
		List<DepLogic>	depLogic=jqm.getList(" select  *   from   dep_logic     where   id   in("+model.getIds()+")",DepLogic.class);
			for (DepLogic de : depLogic) {
				this.pm.remove(de);
			}
			List<DepLogicExamItem>	depLogicExamItem=jqm.getList(" select  *   from   dep_logic_exam_item   where   logic_id   in("+model.getIds()+")",DepLogicExamItem.class);	
			for (DepLogicExamItem d : depLogicExamItem) {
				this.pm.remove(d);
			}
	}

	@Override
	public List<DepLogicExamItemDTO> getDepLogicExamItem(DepLogicModel model) throws ServiceException {
		String sql="select   distinct ex.id,ex.item_name FROM examination_item ex,charging_item_exam_item  ci,charging_item  c "
				+"	where     ex.is_Active='Y'   and    c.isActive='Y'    and  ex.item_num=ci.item_code   and   c.item_code=ci.charging_item_code ";
				if(model.getDep_id()>0){
			  sql+="	and   c.dep_id='"+model.getDep_id()+"'";	
				}
				if(model.getE_item_name()!=null && !"".equals(model.getE_item_name())){
			  sql+="   and    ( ex.item_name   like   '%"+model.getE_item_name().trim()+"%'    or    ex.item_pinyin   like  '%"+model.getE_item_name().trim()+"%')  ";
				}
			  sql+=" order by  ex.item_name";
		return jqm.getList(sql,DepLogicExamItemDTO.class);
	}

	@Override
	public void addDepLogic(DepLogicModel model,UserDTO  user) throws ServiceException {
		DepLogic  depLogic = new DepLogic();
		depLogic.setDep_id(model.getDep_id());
		depLogic.setConclusion_word(model.getConclusion_word());
		depLogic.setCreater(user.getUserid());
		depLogic.setCreate_time(DateTimeUtil.parse());
		depLogic.setSex(model.getSex());
		this.pm.save(depLogic);
		
		JSONArray liArry = JSONArray.fromObject(model.getLi());
		@SuppressWarnings("unchecked")
		List<DepLogicExamItem> lis = (List<DepLogicExamItem>) JSONArray
				.toCollection(liArry,DepLogicExamItem.class);
		
		for (DepLogicExamItem dl : lis) {
			String itemnum = this.examinationItem.getExamCodeById(dl.getExam_item_id());
			dl.setLogic_id(depLogic.getId());
			dl.setCreater(user.getUserid());
			dl.setCreate_time(DateTimeUtil.parse());
			dl.setItem_code(itemnum);
			this.pm.save(dl);
		}
	}

	@Override
	public List<DepLogicExamItemDTO> getDepLogicExamItemShow(DepLogicModel model) throws ServiceException {
		String sql="SELECT ex.item_name as  e_name,ex.id  as   e_id,dl.condition,dl.condition_value,dl.andOrNo FROM " 
				   +"	dep_logic_exam_item  dl,examination_item  ex     where   dl.item_code=ex.item_num   and   ex.is_Active='Y'"
				   +"	and dl.logic_id='"+model.getLogic_id()+"'   order   by   dl.logic_index";
		return jqm.getList(sql,DepLogicExamItemDTO.class);
	}

	@Override
	public DepLogic getDepLogicShou(DepLogicModel model) throws ServiceException {
		return (DepLogic) this.qm.get(DepLogic.class,model.getId());
	}

	@Override
	public void updateDepLogic(DepLogicModel model,UserDTO user) throws ServiceException {
		DepLogic  d = (DepLogic) this.qm.get(DepLogic.class,model.getId());
			d.setId(model.getId());
			d.setDep_id(model.getDep_id());
			d.setConclusion_word(model.getConclusion_word());
			d.setUpdater(user.getUserid());;
			d.setUpdate_time(DateTimeUtil.parse());
			d.setSex(model.getSex());
			this.pm.update(d);
			
			String hql=" From  DepLogicExamItem  where   logic_id='"+model.getId()+"'";
			List<DepLogicExamItem>  item = qm.find(hql);
			
			if(item.size()>0){
				for (DepLogicExamItem depLogicExamItem : item) {
					this.pm.remove(depLogicExamItem);
				}
			}
			
			JSONArray liArry = JSONArray.fromObject(model.getLi());
			@SuppressWarnings("unchecked")
			List<DepLogicExamItem> lis = (List<DepLogicExamItem>) JSONArray
					.toCollection(liArry,DepLogicExamItem.class);
			
			for (DepLogicExamItem dl : lis) {
				String itemnum = this.examinationItem.getExamCodeById(dl.getExam_item_id());
				dl.setLogic_id(model.getId());
				dl.setUpdater(user.getUserid());
				dl.setUpdate_time(DateTimeUtil.parse());
				dl.setItem_code(itemnum);
				this.pm.save(dl);
			}
		
	}

	@Override
	public List<DepartmentDepDTO> getDepartmentDep( String center_num ) throws ServiceException {
			String  sql=" select d.id,d.dep_name from department_dep  d left join department_vs_center de on   de.dep_id = d.id  where   d.dep_category='17'  and de.center_num ='"+center_num+"' ";
		return jqm.getList(sql,DepartmentDepDTO.class);
	}
}
