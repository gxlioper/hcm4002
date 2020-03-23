package com.hjw.wst.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.DiseaseMergeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DiseaseKnowloedge;
import com.hjw.wst.domain.DiseaseMerge;
import com.hjw.wst.model.DiseaseMergeModel;
import com.hjw.wst.service.DiseaseMergeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class DiseaseMergeSericeImpl  implements  DiseaseMergeService{

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
	public PageReturnDTO queryDiseaseMerge(DiseaseMergeModel model, int page,
			int pageSize) throws ServiceException, SQLException {
		
String sql =" SELECT  l.disease_num,l.disease_name,d.id,d.before_disease_id,d.later_disease_id,d.name,d.create_time,u.chi_name  as   creater_name"
		   +" FROM  disease_merge d   left join   user_usr  u  on  d.creater=u.id "
		   +" left join  disease_knowloedge_lib  l  on  l.id=d.later_disease_id   and   l.isActive='Y' "
		   +"  WHERE  1=1 ";
		   if(model.getDisease_name()!=null && !"".equals(model.getDisease_name())){
		   sql +="   and  l.disease_name  like '%"+model.getDisease_name().trim()+"%'" ;
		   }
		   sql+=" order by d.id  desc";
		   
		PageSupport map = this.jqm.getList(sql,page,pageSize,DiseaseMergeDTO.class);
 		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(page);
 		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public void deleteDiseaseMerge(String ids) throws ServiceException,
			SQLException {
		String hql =" FROM  DiseaseMerge e  where  e.id  in("+ids+")";
		List<DiseaseMerge> li = qm.find(hql);
		for (DiseaseMerge diseaseMerge : li) {
			pm.remove(diseaseMerge);
		}
		
	}

	@Override
	public DiseaseMerge getDiseaseMerge(long id) throws ServiceException,
			SQLException {
		String hql =" FROM  DiseaseMerge e  where  e.id='"+id+"'";
		List<DiseaseMerge> li = qm.find(hql);
		return li.get(0);
	}

	@Override
	public void addDiseaseMerge(DiseaseMerge me) throws ServiceException,
			SQLException {
		pm.save(me);
	}

	public void updateDiseaseMerge(DiseaseMergeModel model,UserDTO user)
			throws ServiceException, SQLException {
		String hql =" FROM  DiseaseMerge e  where  e.id='"+model.getId()+"'";
		List<DiseaseMerge> li = qm.find(hql);
		DiseaseMerge d = li.get(0);
		d.setBefore_disease_id(model.getBefore_disease_id());
		d.setCreate_time(DateTimeUtil.parse());
		d.setCreater(user.getUserid());
		d.setName(model.getName());
		d.setLater_disease_id(model.getLater_disease_id());
		d.setId(model.getId());
		pm.update(d);
	}

	@Override
	public List<DiseaseKnowloedge> getDiseaseKnowloedge(String name)
			throws ServiceException, SQLException {
		String hql = " FROM  DiseaseKnowloedge  d  where  1=1  ";
				if(name!=null &&!"".equals(name)){
			   hql +="d.disease_name  like '%"+name.trim()+"%'";
				}
			  List<DiseaseKnowloedge> li = qm.find(hql);
		return li;
	}

	public List<DiseaseKnowloedgeDTO> getqueryPageDklodeg(DiseaseMergeModel model)
			throws ServiceException, SQLException {
		List<DiseaseKnowloedgeDTO>  d = new ArrayList<DiseaseKnowloedgeDTO>();
		if(model.getId()>0){
			
			String sql="SELECT m.before_disease_id FROM disease_merge  m  where   m.id='"+model.getId()+"'";
			List<DiseaseMergeDTO> li=this.jqm.getList(sql,DiseaseMergeDTO.class);
			
			sql="SELECT d.id,d.disease_name,d.disease_num  FROM disease_knowloedge_lib d  where d.isActive='Y'  and  d.id  in("+li.get(0).getBefore_disease_id()+")";
			d = this.jqm.getList(sql,DiseaseKnowloedgeDTO.class) ;
		}
		 	return d;
	}

	@Override
	public List<DiseaseKnowloedgeDTO> getPageDklodegcombobox(String name)
			throws ServiceException, SQLException {
		String sql="select d.id,d.disease_name,d.disease_num,d.disease_pinyin  "
	            +" FROM disease_knowloedge_lib d  WHERE  d.isActive='Y'";
				if(name!=null && !"".equals(name)){
			   sql+=" and  (d.disease_name like '%"+name.trim()+"%'  or  d.disease_pinyin  like  '%"+name.trim()+"%')"	;
				}
		List<DiseaseKnowloedgeDTO>  li = this.jqm.getList(sql,DiseaseKnowloedgeDTO.class) ;
		return li;
	}

}
