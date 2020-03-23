package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.DiseaseDep;
import com.hjw.wst.domain.DiseaseKnowloedge;
import com.hjw.wst.domain.DiseaseSuggestion;
import com.hjw.wst.service.DiseaseKnowloedgeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class DiseaseKnowloedgeServiceImpl implements DiseaseKnowloedgeService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	

	public QueryManager getQueryManager() {
		return qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
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
	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jpm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	
	@Override
	public DiseaseKnowloedge addDklodeg(DiseaseKnowloedge dklg)throws ServiceException {
		this.pm.save(dklg);
		return dklg;
	}

	@Override
	public void deleteDklodeg(String id) throws ServiceException {
		String deldsql = "delete from disease_knowloedge_lib where id in("+id+")";
		this.jpm.executeSql(deldsql);
		String delssql = "delete from disease_suggestion_lib where disease_id in ("+id+")";
		this.jpm.executeSql(delssql);
	}

	@Override
	public DiseaseKnowloedge updateDklodeg(DiseaseKnowloedge dklg)throws ServiceException {
		this.pm.update(dklg);
		return dklg;
	}

	@Override
	public DiseaseKnowloedge loadDklodeg(long id) throws ServiceException {
		return (DiseaseKnowloedge)this.qm.get(DiseaseKnowloedge.class, id);
	}

	@Override
	public void deleteDSugge(DiseaseSuggestion dsuge) throws ServiceException {
		this.pm.remove(dsuge);
		
	}
	
	public List<DiseaseKnowloedgeDTO> getSuggtionByDiseaseid(long diseaseid,String center_num,String sug_center) throws ServiceException{
		String sql = " select ds.id as sug_id,ds.sex,ds.minAge,ds.maxAge,ds.disease_suggestion as health_suggestion,ds.default_value,ds.is_active "
	               +" from disease_suggestion_lib ds where ds.disease_id = "+ diseaseid;
		if("Y".equals(sug_center)){
			sql += " and ds.center_num = '"+center_num+"'";
		}
		List<DiseaseKnowloedgeDTO> list = this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return list;
	}
	
	

	/**
	 * 建议表列表
	     * <p>Title: queryallSuggestion</p>   
	     * <p>Description: </p>   
	     * @param pageno
	     * @param pagesize
	     * @return   
	     * @see com.hjw.wst.service.DiseaseKnowloedgeService#queryallSuggestion(int, int)
	 */
	@Override
	public PageReturnDTO queryallSuggestion(long diseaseid,int pageno, int pagesize,String center_num,String sug_center) {
		String sql =" select ds.id,ds.sex,ds.minAge,ds.maxAge,ds.disease_suggestion,ds.default_value,ds.is_active "
	               +" from disease_suggestion_lib ds where ds.disease_id = "+ diseaseid;
		if("Y".equals(sug_center)){
			sql += " and ds.center_num = '"+center_num+"'";
		}
		PageSupport map=this.jqm.getList(sql, pagesize,  pageno,DiseaseKnowloedgeDTO.class) ;
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public DiseaseSuggestion loadSuggestion(long id) throws ServiceException {
		DiseaseSuggestion diseaseSuggestion = (DiseaseSuggestion) this.qm.load(DiseaseSuggestion.class,id);
		return diseaseSuggestion;
	}

	@Override
	public DiseaseSuggestion addSug(DiseaseSuggestion ds)throws ServiceException {
		this.pm.save(ds);
		return ds;
	}

	@Override
	public DiseaseSuggestion updateSug(DiseaseSuggestion ds)throws ServiceException {
		this.pm.update(ds);
		return ds;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DiseaseSuggestion> getSuggestionList(long disesId,String center_num,String sug_center){
		String sql = "from DiseaseSuggestion where disease_id = "+ disesId;
		
		if("Y".equals(sug_center)){
			sql += " and center_num = '"+center_num+"'";
		}
		List<DiseaseSuggestion> list = this.qm.find(sql);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DiseaseKnowloedge queryByNum(String disease_num)throws ServiceException {
		String sql = "from DiseaseKnowloedge where disease_num = '"+disease_num.trim()+"'";
		List<DiseaseKnowloedge> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<DiseaseKnowloedgeDTO> getdepname( String center_num ) throws ServiceException {
		String sql=" select dep.id as dep_id,dep.dep_name from  department_dep  dep left join department_vs_center de on de.dep_id = dep.id  WHERE DEP.isActive='Y' and de.center_num ='"+center_num+"'  "
				+" order by dep_name ";
		List<DiseaseKnowloedgeDTO> list=this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return list;
	}

	@Override
	public List<DiseaseKnowloedgeDTO> getitemname() throws ServiceException {
		String sql=" select i.id as charging_item_ids,i.item_name from charging_item  i where i.isActive='Y'  ";
		List<DiseaseKnowloedgeDTO> list=this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return list;
	}
	
	@Override
	public List<DiseaseKnowloedgeDTO> getByitem_id(long id)throws ServiceException{
		String sql=" select i.id,i.item_name from charging_item  i where i.isActive='Y' " ;
				if(id!=0){
					sql+= " and i.dep_id='"+id+"' ";
				}
				sql+=" order by item_seq ";
		List<DiseaseKnowloedgeDTO> l=this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return l;
	};
	
	@Override
	public List<DiseaseKnowloedgeDTO> getcheckitemname(long id)throws ServiceException {
		String sql="select e.id as exam_item_ids,e.item_name as checkitem_name from  examination_item e where e.is_Active='Y'";
		List<DiseaseKnowloedgeDTO> list=this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return list;
	}

	@Override
	public DiseaseKnowloedgeDTO getByDiseaseDepId(long id) throws ServiceException {
		String sql=" ";
		List<DiseaseKnowloedgeDTO> diseasedep=this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return diseasedep.get(0);
	}

	
	/**
	 * 根据收费项目检索检查项目
	     * <p>Title: getBycharginItemid</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.DiseaseKnowloedgeService#getBycharginItemid(java.lang.String)
	 */
	@Override
	public List<DiseaseKnowloedgeDTO> getBycharginItemid(String id,long dep_id)
			throws ServiceException {
		String sql=" select distinct e.item_name as checkitem_name,e.id,e.seq_code from examination_item e,charging_item_exam_item ce,charging_item c "
				+" where e.is_Active='Y' and e.item_num=ce.item_code and c.item_code=ce.charging_item_code  " ;
				if(dep_id!=0){
					sql+=" and c.dep_id ='"+dep_id+"' ";
				}
				if(!"".equals(id) && id!=null){
				sql+=" and ce.charging_item_id in("+id+")  ";
				}
				sql+=" order by seq_code ";
		List<DiseaseKnowloedgeDTO> list=jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		return list;
	}

	@Override
	public void insertDiseaseDep(DiseaseDep dd) throws ServiceException {
		this.pm.save(dd);
	}
	
	/**
	 * 
	     * @Title: queryPageDklodegDep   
	     * @Description: 按照疾病知识库表部门现实树 
	     * @param: @param dep_id
	     * @param: @param disease_type
	     * @param: @param disease_name
	     * @param: @param disease_level
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryPageDklodegDep(long dep_id,String disease_type,String disease_name,String disease_level,String isActive,int pageno, int pagesize, String center_num ){
		StringBuffer  buffer = new StringBuffer();
		buffer.append("select dk.id,dk.disease_type,dk.disease_level,dk.disease_name,dk.disease_pinyin,dk.disease_num, "
				+ "dk.disease_description,dk.disease_evendice,dk.disease_suggestion,dk.icd_10,dk.isActive, "
				+ "dl.data_name as disease_level_d,dc.data_name as disease_classification_d,dk.dep_id,d.dep_name,"
				+ "dk.disease_system,ds.data_name as disease_systems,dk.disease_statistics,dd.data_name as disease_statisticss,"
				+ "dk.disease_report,dk.disease_team_show,dk.update_time ,u.chi_name as updater "
				+ "from disease_knowloedge_lib dk left join department_dep d on d.id = dk.dep_id "
				+ "left join department_vs_center de on dk.dep_id = de.dep_id and de.center_num ='"+center_num+"' "
				+ "left join data_dictionary dl on dk.disease_level = dl.id "
				+ "left join data_dictionary dc on dk.disease_classification = dc.id "
				+ "left join data_dictionary ds on dk.disease_system = ds.id "
				+ "left join data_dictionary dd on dk.disease_statistics = dd.id left join  user_usr u on dk.updater = u.id where 1=1 ");
		if(dep_id > 0){
			buffer.append(" and dk.dep_id = '"+dep_id+"'");
		}else if(dep_id == 0){
			buffer.append(" and (dk.dep_id = '0' or dk.dep_id is null)");
		}
		if(disease_name != null && !"".equals(disease_name)){
			buffer.append(" and (dk.disease_name like '%"+disease_name+"%' or dk.disease_pinyin like '%"+disease_name+"%')"); 
		}
		if(disease_level != null && !"".equals(disease_level)){
			buffer.append(" and dk.disease_level ='"+disease_level+"'");
		}
		if(disease_type != null && !"".equals(disease_type)){
			buffer.append(" and dk.disease_type = '"+disease_type+"'");
		}
		if(isActive != null && !"".equals(isActive)){
			buffer.append(" and dk.isActive = '"+isActive+"'");
		}
		buffer.append("  order by dk.id");
	 	PageSupport map=this.jqm.getList(buffer.toString(), pagesize,  pageno,DiseaseKnowloedgeDTO.class) ;
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	public DiseaseKnowloedgeDTO loadDkloaddept(long id) throws ServiceException {
		String sql = "select dk.id,dk.disease_type,dk.disease_level,dk.disease_name,dk.disease_pinyin,dk.disease_num, "
				+ "dk.disease_description,dk.disease_evendice,dk.disease_suggestion,dk.icd_10, dl.data_name as disease_level_d,"
				+ "dc.data_name as disease_classification_d,dk.dep_id,d.dep_name,dk.disease_system,ds.data_name as disease_systems,"
				+ "dk.disease_statistics,dd.data_name as disease_statisticss,dk.disease_report,dk.disease_team_show "
				+ "from disease_knowloedge_lib dk "
				+ "left join department_dep d on d.id = dk.dep_id "
				+ "left join data_dictionary dl on dk.disease_level = dl.id "
				+ "left join data_dictionary dc on dk.disease_classification = dc.id "
				+ "left join data_dictionary ds on dk.disease_system = ds.id "
				+ "left join data_dictionary dd on dk.disease_statistics = dd.id where dk.id = "+id;
		List<DiseaseKnowloedgeDTO> list = this.jqm.getList(sql, DiseaseKnowloedgeDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateDiseaseDep(DiseaseDep dd) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DiseaseDep getDiseaseDepByDisId(long dis_id) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	} 
	
	@Override
	public List<CriticalDTO> getcriticalD() throws ServiceException {
		String sql=" select ecc.id,ecc.critical_class_name from exam_Critical_class ecc where ecc.parent_id =0 ";
		List<CriticalDTO> list=this.jqm.getList(sql, CriticalDTO.class);
		return list;
	}
	
	@Override
	public List<CriticalDTO> getcritical(long id) throws ServiceException {
		String sql=" select ecc.id,ecc.critical_class_name from exam_Critical_class ecc where ecc.parent_id = "+id+" ";
		List<CriticalDTO> list=this.jqm.getList(sql, CriticalDTO.class);
		return list;
	}
	
	@Override
	public List<CriticalDTO> getcriticalLevel() throws ServiceException {
		String sql=" select dd.id,dd.data_name as critical_class_level from data_dictionary dd where dd.data_code ='WJZDJ' and dd.isActive = 'Y' ";
		List<CriticalDTO> list=this.jqm.getList(sql, CriticalDTO.class);
		return list;
	}
	
	@Override
	public List<CriticalDTO> getDisease() throws ServiceException {
		String sql="  select id,disease_name from disease_knowloedge_lib where isActive ='Y' ";
		List<CriticalDTO> list=this.jqm.getList(sql, CriticalDTO.class);
		return list;
	}

	@Override
	public DiseaseKnowloedge getDiseaseKnowloedgeByDisaseNum(String disaseNum) throws ServiceException {
		String hql = " FROM DiseaseKnowloedge WHERE  disease_num='"+disaseNum+"' AND isActive='Y'";
		List<DiseaseKnowloedge> li = this.qm.find(hql);
		if(li!=null && li.size()>0) {
			return li.get(0);
		} else {
			return null;
		}
	}
	@Override
	public List<DiseaseKnowloedgeDTO>  queryDiseaseList(String disease_name) throws ServiceException{
		
		StringBuffer  buffer = new StringBuffer();
		buffer.append("select d.id,d.icd_10,d.disease_pinyin,disease_type,disease_name,disease_num,d.disease_description, d.disease_evendice,d.disease_suggestion,");
		buffer.append("d.update_time FROM disease_knowloedge_lib d where d.isActive='Y' ");
		if(!"".equals(disease_name)&&disease_name!=null&&!"0".equals(disease_name)){
			buffer.append("and d.disease_name = '"+disease_name+"' ");
		}
		buffer.append(" order by d.id ");
//	List<DiseaseKnowloedgeDTO> list = this.jqm.getList(buffer.toString(), DiseaseKnowloedgeDTO.class);
	PageSupport map = this.jqm.getList(buffer.toString(), 1, 10000, DiseaseKnowloedgeDTO.class);
	List<DiseaseKnowloedgeDTO> webrole = new ArrayList<DiseaseKnowloedgeDTO>();
	
	if((map!=null)&&(map.getList()!=null)){
		webrole = map.getList();
	}

	return webrole;
}
}
