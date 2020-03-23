package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.RowSet;

import com.hjw.interfaces.util.StringUtil;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.ExaminationItemVSLisDTO;
import com.hjw.wst.DTO.FinalRejectionDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.ItemResultLib;
import com.hjw.wst.model.ExaminationItemModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.ExaminationItemService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ExaminationItemImpl implements ExaminationItemService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private CustomerInfoService customerInfoService;
	private DepartmentService departmentService;
	public QueryManager getQueryManager() {
		return qm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
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

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}
	
	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Override
	public PageReturnDTO queryExaminationItem(ExaminationItem exa,int page,int pagesize,String rt,long charging_item_id,String startStop, String center_num) {
		PageReturnDTO webrole = new PageReturnDTO();
		String sql = "";
		if("XJCXM".equals(exa.getRemark())){
			sql+="SELECT  (SELECT COUNT(item_code) FROM disease_logic_single WHERE  " +
                    " item_code=e.item_num AND isActive='Y') as  disease_count,"
                    +"cte.id  as  cteid,"
					+ "cte.exam_item_id  as  exam_itemid,"
					+ " cte.charging_item_id,"
					+ "cte.item_class_id,e.id,"
					+ "icl.item_class_name,"
					+" e.item_num,"
					+" e.item_name,"
					+" e.item_pinyin,"
					+" e.item_eng_name,"
					+" e.item_unit,"
					+" vs.exam_num,"
					+" e.view_num,"
					+" e.item_category,"
					+" e.is_print,"
					+" e.seq_code,"
					+" e.item_description,"
					+" e.remark,"
					+" e.ref_Mmax,"
					+" e.ref_Mmin,"
					+" e.ref_Fmin,"
					+" e.ref_Fmax,"
					+" e.dang_Fmax,"
					+" e.dang_Fmin,"
					+" e.dang_Mmax,"
					+" e.dang_Mmin,"
					+" e.is_Active,"
					+" e.create_time,"
					+" e.update_time,"
					+" e.default_value,"
					+" data_dictionary.data_name as dataName,"
					+" u.chi_name as creatername,"
					+" us.chi_name as updatername"
					+" ,e.sex"
					+" FROM examination_item e"
					+" LEFT JOIN (SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b GROUP BY item_code) vs on e.item_num = vs.item_code "
					+" LEFT JOIN charging_item_exam_item cte  on  e.item_num = cte.item_code  "
					+" LEFT JOIN   data_dictionary on e.item_type=data_dictionary.id " 
					+" LEFT JOIN   user_usr u on e.creater=u.id "
					+" LEFT JOIN   user_usr us on e.updater=us.id "
					+" LEFT JOIN   department_dep  de  on  de.id=e.dep_id "
					+ " LEFT JOIN charging_item_class icl on cte.item_class_id = icl.item_class_id "
		 			+" where 1=1";
			
				   if (startStop != null && !"".equals(startStop)) {// 收费编号
						if(startStop.length()==1) {
							sql += " and e.is_Active IN ('"+startStop+"') ";
						}else {
							sql += " and e.is_Active IN ('Y','N') ";
						}
					}
		 			
		 			sql+= "  and  cte.charging_item_id="+charging_item_id;
		 			if(exa.getItem_class_id()>0){
		 			sql+="  and  cte.item_class_id="+exa.getItem_class_id();	
		 			}
		 			if(exa.getDep_id()!=-1) {
		 				sql+="  and e.dep_id = "+exa.getDep_id();
		 			}
		 			if(exa.getExam_num()!=null && !"".equals(exa.getExam_num())){
				sql+="  and  	vs.exam_num='"+exa.getExam_num().trim()+"'";	
		 			}
		 			if(exa.getView_num()!=null && !"".equals(exa.getView_num())){
				sql+="  and  	e.view_num='"+exa.getView_num().trim()+"'";			
		 			}
					if(exa.getItem_num()!=null&&!exa.getItem_num().equals("")){
				sql+=" and  e.item_num='"+exa.getItem_num().trim()+"'";
					}
					if(exa.getItem_name()!=null&&!exa.getItem_name().equals("")){
				sql+=" and  (e.item_name  like'%"+exa.getItem_name().trim()+"%'"
				    +" or  e.item_pinyin  like'%"+exa.getItem_name().trim()+"%')";			
					}
					sql+=" order by e.seq_code ";
					PageSupport map = this.jqm.getList(sql,pagesize,page,ExaminationItemDTO.class);
					webrole.setPage(page);
					webrole.setRp(pagesize);
					if ((map != null) && (map.getList() != null)) {
						List<ExaminationItemDTO> li = map.getList();
						webrole.setTotal(map.getRecTotal());
						webrole.setRows(map.getList());
					}
		} else{
            sql+="SELECT  (SELECT COUNT(item_code) FROM disease_logic_single WHERE  "
                    +" item_code=e.item_num AND isActive='Y' ) as  disease_count,"
						+" e.id,"
						+" e.item_num,"
						+" e.item_name,"
						+" e.item_pinyin,"
						+" e.item_eng_name,"
						+" e.item_unit,"
						+" vs.exam_num,"
						+" e.view_num,"
						+" e.item_category,"
						+" e.is_print,"
						+" e.seq_code,"
						+" e.item_description,"
						+" e.remark,"
						+" e.ref_Mmax,"
						+" e.ref_Mmin,"
						+" e.ref_Fmin,"
						+" e.ref_Fmax,"
						+" e.dang_Fmax,"
						+" e.dang_Fmin,"
						+" e.dang_Mmax,"
						+" e.dang_Mmin,"
						+" e.is_Active,"
						+" e.create_time,"
						+" e.update_time,"
						+" e.default_value,"
						+" data_dictionary.data_name as dataName,"
						+" u.chi_name as creatername,"
						+" us.chi_name as updatername"
						+" ,e.sex"
						+" FROM examination_item e"
						+" LEFT JOIN (SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b GROUP BY item_code) vs on e.item_num = vs.item_code "
						+" LEFT JOIN   data_dictionary on e.item_type=data_dictionary.id" 
						+" LEFT JOIN   user_usr u on e.creater=u.id"
						+" LEFT JOIN   user_usr us on e.updater=us.id"
						+" LEFT JOIN   department_dep  de  on  de.id=e.dep_id"
			 			+" where 1=1  ";
						
						if (startStop != null && !"".equals(startStop)) {// 是否启停
							if(startStop.length()==1) {
								sql += " and e.is_Active IN ('"+startStop+"') ";
							}else {
								sql += " and e.is_Active IN ('Y','N') ";
							}
						}
			 
			 			if(exa.getExam_num()!=null && !"".equals(exa.getExam_num())){
					sql+="  and  	vs.exam_num='"+exa.getExam_num().trim()+"'";	
			 			}
			 			if(exa.getDep_id()!=-1) {
			 				sql+="  and e.dep_id = "+exa.getDep_id();
			 			}
			 			if(exa.getView_num()!=null && !"".equals(exa.getView_num())){
					sql+="  and  	e.view_num='"+exa.getView_num().trim()+"'";			
			 			}
						if(exa.getItem_num()!=null&&!exa.getItem_num().equals("")){
					sql+=" and  e.item_num='"+exa.getItem_num().trim()+"'";
						}
						if(exa.getItem_name()!=null&&!exa.getItem_name().equals("")){
					sql+=" and  (e.item_name  like'%"+exa.getItem_name().trim()+"%'"
					    +" or  e.item_pinyin  like'%"+exa.getItem_name().trim()+"%')";			
						}
						if(rt.equals("名称重复")){
					sql+="  and e.item_name   in   (select   e.item_name   from"
							+ "  examination_item e  where  e.is_Active='Y'  group   by   e.item_name having count(e.item_name)>1)"
							+ " 	ORDER BY   e.item_name";		
						}else{
					sql+="   order by e.seq_code ";
						}
						PageSupport map = this.jqm.getList(sql,pagesize,page,ExaminationItemDTO.class);
						webrole.setPage(page);
						webrole.setRp(pagesize);
						if ((map != null) && (map.getList() != null)) {
							webrole.setTotal(map.getRecTotal());
							webrole.setRows(map.getList());
						}
		}
	return webrole;
}

	@Override
	public void deleteExaminationItem(String id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="update examination_item set is_Active='N' where id in("+id+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}

	@Override
	public ExaminationItem findClass(long id, String center_num) {
		ExaminationItem exa = (ExaminationItem) this.qm.get(ExaminationItem.class,id);
		try {
			String sql = " SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b where b.item_code = '"+exa.getItem_num()+"' GROUP BY item_code ";
			RowSet rowSet = jqm.getRowSet(sql);
			if(rowSet.next()) {
				String exam_num = rowSet.getString("exam_num");
				exa.setExam_num(exam_num);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return exa;
	}

	@Override
	public void updateExaminationItem(ExaminationItem exa, String center_num) throws ServiceException {
		String exam_num = exa.getExam_num();
		exa.setExam_num("");
		this.pm.update(exa);
		ExaminationItemVSLisDTO evsl = new ExaminationItemVSLisDTO();
		evsl.setCenter_num(center_num);
		evsl.setExam_item_id(exa.getId());
		evsl.setItem_code(exa.getItem_num());
		evsl.setLis_item_id(exam_num);
		saveOrUpdateExaminationItemVSLis(evsl);
	}

	@Override
	public ExaminationItem addExaminationItem(ExaminationItem exa, String center_num) throws ServiceException {
		String exam_num = exa.getExam_num();
		exa.setExam_num("");
		this.pm.save(exa);
		ExaminationItemVSLisDTO evsl = new ExaminationItemVSLisDTO();
		evsl.setCenter_num(center_num);
		evsl.setExam_item_id(exa.getId());
		evsl.setItem_code(exa.getItem_num());
		evsl.setLis_item_id(exam_num);
		saveOrUpdateExaminationItemVSLis(evsl);
		return exa;
	}
	
	public ExaminationItem getexam_num(String exam_num) throws ServiceException {
		List ls = qm.find("From ExaminationItem Where  exam_num ='" + exam_num.trim() + "'");
			if (ls.size() > 0)
				return (ExaminationItem) ls.get(0);
			else
				return null;
	}
    public ExaminationItem getExaminationItemByItem_num(String item_num) throws ServiceException {
        List ls = qm.find("From ExaminationItem Where  item_num ='" + item_num.trim() + "'");
        if (ls.size() > 0)
            return (ExaminationItem) ls.get(0);
        else
            return null;
    }
	
	
	
	public int queryDefaultid(Long defaultid)  throws ServiceException {
		String sql="";
		int a=0;
			sql+="SELECT * from 	item_result_lib  l "
					+ "  left join examination_item   e  on   l.item_code=e.item_num   and  l.isActive='Y'"
					+ "WHERE   e.is_Active='Y'  AND  l.id="+defaultid+"";
			List<ExaminationItem>  li = jqm.getList(sql,ExaminationItem.class);
			if(li.size()>0){
				if(li.get(0).getDefault_value()!=null){
					if(li.get(0).getDefault_value().equals(defaultid)){
						a=1;
					}
				}
			}
		return a;
	}
	public ExaminationItem getviewnum(String view_num) throws ServiceException{
		List ls = qm.find("From ExaminationItem Where  view_num ='" + view_num.trim() + "'");
		if (ls.size() > 0)
			return (ExaminationItem) ls.get(0);
		else
			return null;
	}
	public List<ThridLisItemDTO> getThridLisItemList(ExaminationItemModel model) throws ServiceException {
		String sql = " SELECT * FROM    thrid_lis_item l	"
					+" where   1 = 1  "; 
					if(model.getLisid()!=null  && !"".equals(model.getLisid())){
						sql+=" and  l.lisitemid  in('"+model.getLisid()+"') ";
					}
					if(model.getLisname()!=null && !"".equals( model.getLisname())){
						sql+=" and  l.lisitemname  like '%"+model.getLisname().trim()+"%'";
					}
					sql+=" order by  l.create_time desc,update_time desc";
		List<ThridLisItemDTO>  li = this.jqm.getList(sql,ThridLisItemDTO.class);
		return li;
	}
	public PageReturnDTO getThridLisItemTable(ExaminationItemModel model,int page,int pagesize) throws ServiceException {
		String sql = " SELECT l.lisitemid,l.lisitemname FROM    thrid_lis_item l	"
					+" where   1 = 1  "; 
					if(model.getLisid()!=null  && !"".equals(model.getLisid())){
						sql+=" and  l.lisitemid  in('"+model.getLisid()+"') ";
					}
					if(model.getLisname()!=null && !"".equals( model.getLisname())){
						sql+=" and  l.lisitemname  like '%"+model.getLisname().trim()+"%'";
					}
					sql+=" order by  l.lisitemid  desc";
		PageSupport map = this.jqm.getList(sql,pagesize,page,ThridLisItemDTO.class);
 		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(page);
 		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	@Override
	public PageReturnDTO queryExaminationItemByConfig(ExaminationItem exa, int page, int pagesize, String rt,
			long charging_item_id, String center_num) throws ServiceException {
		PageReturnDTO webrole = new PageReturnDTO();
		String sql = "";
		if("XJCXM".equals(exa.getRemark())){
			sql+="SELECT  cte.id  as  cteid,"
					+ "cte.exam_item_id  as  exam_itemid,"
					+ " cte.charging_item_id,"
					+ "cte.item_class_id,e.id,"
					+ "icl.item_class_name,"
					+" e.item_num,"
					+" e.item_name,"
					+" e.item_pinyin,"
					+" e.item_eng_name,"
					+" e.item_unit,"
					+" vs.exam_num,"
					+" e.view_num,"
					+" e.item_category,"
					+" e.is_print,"
					+" e.seq_code,"
					+" e.item_description,"
					+" e.remark,"
					+" e.ref_Mmax,"
					+" e.ref_Mmin,"
					+" e.ref_Fmin,"
					+" e.ref_Fmax,"
					+" e.dang_Fmax,"
					+" e.dang_Fmin,"
					+" e.dang_Mmax,"
					+" e.dang_Mmin,"
					+" e.is_Active,"
					+" e.create_time,"
					+" e.update_time,"
					+" e.default_value,"
					+" data_dictionary.data_name as dataName,"
					+" u.chi_name as creatername,"
					+" us.chi_name as updatername"
					+" ,e.sex"
					+" FROM examination_item e"
					+" LEFT JOIN (SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b GROUP BY item_code) vs on e.item_num = vs.item_code "
					+ " LEFT JOIN charging_item_exam_item cte  on  e.item_num = cte.item_code  "
					+" LEFT JOIN   data_dictionary on e.item_type=data_dictionary.id " 
					+" LEFT JOIN   user_usr u on e.creater=u.id "
					+" LEFT JOIN   user_usr us on e.updater=us.id "
					+" LEFT JOIN   department_dep  de  on  de.id=e.dep_id "
					+ " LEFT JOIN charging_item_class icl on cte.item_class_id = icl.item_class_id "
		 			+" where e.is_Active='Y'      "
		 			+"  and  cte.charging_item_id="+charging_item_id;
			
					String IS_ALL_DEPT_ITEM = this.customerInfoService.getCenterconfigByKey("IS_ALL_DEPT_ITEM", center_num).getConfig_value().trim();
					 if("N".equals(IS_ALL_DEPT_ITEM)) {
						 if(exa.getDep_id()!= 0){
								sql+="  and  e.dep_id='"+exa.getDep_id()+"'";	
						 }
					 }
		 			if(exa.getItem_class_id()>0){
		 			sql+="  and  cte.item_class_id="+exa.getItem_class_id();	
		 			}
		 			
		 			if(exa.getExam_num()!=null && !"".equals(exa.getExam_num())){
				sql+="  and  	vs.exam_num='"+exa.getExam_num().trim()+"'";	
		 			}
		 			if(exa.getView_num()!=null && !"".equals(exa.getView_num())){
				sql+="  and  	e.view_num='"+exa.getView_num().trim()+"'";			
		 			}
					if(exa.getItem_num()!=null&&!exa.getItem_num().equals("")){
				sql+=" and  e.item_num='"+exa.getItem_num().trim()+"'";
					}
					if(exa.getItem_name()!=null&&!exa.getItem_name().equals("")){
				sql+=" and  (e.item_name  like'%"+exa.getItem_name().trim()+"%'"
				    +" or  e.item_pinyin  like'%"+exa.getItem_name().trim()+"%')";			
					}
					sql+=" order by e.seq_code ";
					PageSupport map = this.jqm.getList(sql,pagesize,page,ExaminationItemDTO.class);
					webrole.setPage(page);
					webrole.setRp(pagesize);
					if ((map != null) && (map.getList() != null)) {
						List<ExaminationItemDTO> li = map.getList();
						webrole.setTotal(map.getRecTotal());
						webrole.setRows(map.getList());
					}
		} else{
			 sql ="SELECT  "
						+" e.id,"
						+" e.item_num,"
						+" e.item_name,"
						+" e.item_pinyin,"
						+" e.item_eng_name,"
						+" e.item_unit,"
						+" vs.exam_num,"
						+" e.view_num,"
						+" e.item_category,"
						+" e.is_print,"
						+" e.seq_code,"
						+" e.item_description,"
						+" e.remark,"
						+" e.ref_Mmax,"
						+" e.ref_Mmin,"
						+" e.ref_Fmin,"
						+" e.ref_Fmax,"
						+" e.dang_Fmax,"
						+" e.dang_Fmin,"
						+" e.dang_Mmax,"
						+" e.dang_Mmin,"
						+" e.is_Active,"
						+" e.create_time,"
						+" e.update_time,"
						+" e.default_value,"
						+" data_dictionary.data_name as dataName,"
						+" u.chi_name as creatername,"
						+" us.chi_name as updatername"
						+" ,e.sex"
						+" FROM examination_item e"
						+" LEFT JOIN (SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b GROUP BY item_code) vs on e.item_num = vs.item_code "
						+" LEFT JOIN   data_dictionary on e.item_type=data_dictionary.id" 
						+" LEFT JOIN   user_usr u on e.creater=u.id"
						+" LEFT JOIN   user_usr us on e.updater=us.id"
						+" LEFT JOIN   department_dep  de  on  de.id=e.dep_id"
			 			+" where e.is_Active='Y'  ";
			 
					 String IS_ALL_DEPT_ITEM = this.customerInfoService.getCenterconfigByKey("IS_ALL_DEPT_ITEM", center_num).getConfig_value().trim();
					 if("N".equals(IS_ALL_DEPT_ITEM)) {
						 if(exa.getDep_id()!= 0){
								sql+="  and  e.dep_id='"+exa.getDep_id()+"'";	
						 }
					 }
			 
			 			if(exa.getExam_num()!=null && !"".equals(exa.getExam_num())){
					sql+="  and  	vs.exam_num='"+exa.getExam_num().trim()+"'";	
			 			}
			 			
			 			if(exa.getView_num()!=null && !"".equals(exa.getView_num())){
					sql+="  and  	e.view_num='"+exa.getView_num().trim()+"'";			
			 			}
						if(exa.getItem_num()!=null&&!exa.getItem_num().equals("")){
					sql+=" and  e.item_num='"+exa.getItem_num().trim()+"'";
						}
						if(exa.getItem_name()!=null&&!exa.getItem_name().equals("")){
					sql+=" and  (e.item_name  like'%"+exa.getItem_name().trim()+"%'"
					    +" or  e.item_pinyin  like'%"+exa.getItem_name().trim()+"%')";			
						}
						if(rt.equals("名称重复")){
					sql+="  and e.item_name   in   (select   e.item_name   from"
							+ "  examination_item e  where  e.is_Active='Y'  group   by   e.item_name having count(e.item_name)>1)"
							+ " 	ORDER BY   e.item_name";		
						}else{
					sql+="   order by e.seq_code ";
						}
						PageSupport map = this.jqm.getList(sql,pagesize,page,ExaminationItemDTO.class);
						webrole.setPage(page);
						webrole.setRp(pagesize);
						if ((map != null) && (map.getList() != null)) {
							webrole.setTotal(map.getRecTotal());
							webrole.setRows(map.getList());
						}
		}
	return webrole;
}

	@Override
	public PageReturnDTO queryExamItemByOwnDept(ExaminationItem exa, int page, int pagesize, String rt,
			long charging_item_id, String center_num) throws ServiceException {
		PageReturnDTO webrole = new PageReturnDTO();
		String sql = "";
		if("XJCXM".equals(exa.getRemark())){
			sql+="SELECT  cte.id  as  cteid,"
					+ "cte.exam_item_id  as  exam_itemid,"
					+ " cte.charging_item_id,"
					+ "cte.item_class_id,e.id,"
					+ "icl.item_class_name,"
					+" e.item_num,"
					+" e.item_name,"
					+" e.item_pinyin,"
					+" e.item_eng_name,"
					+" e.item_unit,"
					+" vs.exam_num,"
					+" e.view_num,"
					+" e.item_category,"
					+" e.is_print,"
					+" e.seq_code,"
					+" e.item_description,"
					+" e.remark,"
					+" e.ref_Mmax,"
					+" e.ref_Mmin,"
					+" e.ref_Fmin,"
					+" e.ref_Fmax,"
					+" e.dang_Fmax,"
					+" e.dang_Fmin,"
					+" e.dang_Mmax,"
					+" e.dang_Mmin,"
					+" e.is_Active,"
					+" e.create_time,"
					+" e.update_time,"
					+" e.default_value,"
					+" data_dictionary.data_name as dataName,"
					+" u.chi_name as creatername,"
					+" us.chi_name as updatername"
					+" ,e.sex,e.item_result_type"
					+" FROM examination_item e"
					+" LEFT JOIN (SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b GROUP BY item_code) vs on e.item_num = vs.item_code "
					+ " LEFT JOIN charging_item_exam_item cte  on  e.item_num = cte.item_code  "
					+" LEFT JOIN   data_dictionary on e.item_type=data_dictionary.id " 
					+" LEFT JOIN   user_usr u on e.creater=u.id "
					+" LEFT JOIN   user_usr us on e.updater=us.id "
					+" LEFT JOIN   department_dep  de  on  de.id=e.dep_id "
					+ " LEFT JOIN charging_item_class icl on cte.item_class_id = icl.item_class_id "
		 			+" where e.is_Active='Y'      "
		 			+"  and  cte.charging_item_id="+charging_item_id;
		 			if(exa.getItem_class_id()>0){
		 			sql+="  and  cte.item_class_id="+exa.getItem_class_id();	
		 			}
		 			
		 			String IS_ALL_DEPT_ITEM = this.customerInfoService.getCenterconfigByKey("IS_ALL_DEPT_ITEM", center_num).getConfig_value().trim();
					if("N".equals(IS_ALL_DEPT_ITEM)) {
						 if(exa.getDep_id()!= 0){
							 sql+="  and  e.dep_id='"+exa.getDep_id()+"' ";
						 }
					}
		 			
		 			if(exa.getExam_num()!=null && !"".equals(exa.getExam_num())){
				sql+="  and  	vs.exam_num='"+exa.getExam_num().trim()+"'";	
		 			}
		 			if(exa.getView_num()!=null && !"".equals(exa.getView_num())){
				sql+="  and  	e.view_num='"+exa.getView_num().trim()+"'";			
		 			}
					if(exa.getItem_num()!=null&&!exa.getItem_num().equals("")){
				sql+=" and  e.item_num='"+exa.getItem_num().trim()+"'";
					}
					if(exa.getItem_name()!=null&&!exa.getItem_name().equals("")){
				sql+=" and  (e.item_name  like'%"+exa.getItem_name().trim()+"%'"
				    +" or  e.item_pinyin  like'%"+exa.getItem_name().trim()+"%')";			
					}
					sql+=" order by e.seq_code ";
					PageSupport map = this.jqm.getList(sql,pagesize,page,ExaminationItemDTO.class);
					webrole.setPage(page);
					webrole.setRp(pagesize);
					if ((map != null) && (map.getList() != null)) {
						List<ExaminationItemDTO> li = map.getList();
						webrole.setTotal(map.getRecTotal());
						webrole.setRows(map.getList());
					}
		} else{
			 sql ="SELECT  "
						+" e.id,"
						+" e.item_num,"
						+" e.item_name,"
						+" e.item_pinyin,"
						+" e.item_eng_name,"
						+" e.item_unit,"
						+" vs.exam_num,"
						+" e.view_num,"
						+" e.item_category,"
						+" e.is_print,"
						+" e.seq_code,"
						+" e.item_description,"
						+" e.remark,"
						+" e.ref_Mmax,"
						+" e.ref_Mmin,"
						+" e.ref_Fmin,"
						+" e.ref_Fmax,"
						+" e.dang_Fmax,"
						+" e.dang_Fmin,"
						+" e.dang_Mmax,"
						+" e.dang_Mmin,"
						+" e.is_Active,"
						+" e.create_time,"
						+" e.update_time,"
						+" e.default_value,"
						+" data_dictionary.data_name as dataName,"
						+" u.chi_name as creatername,"
						+" us.chi_name as updatername"
						+" ,e.sex,e.item_result_type"
						+" FROM examination_item e"
						+" LEFT JOIN (SELECT item_code, STUFF((SELECT '+'+ lis_item_id FROM examination_item_vs_lis a WHERE a.item_code = b.item_code and a.center_num = '"+center_num+"' FOR XML PATH('')),1 ,1, '') as exam_num FROM examination_item_vs_lis b GROUP BY item_code) vs on e.item_num = vs.item_code "
						+" LEFT JOIN   data_dictionary on e.item_type=data_dictionary.id" 
						+" LEFT JOIN   user_usr u on e.creater=u.id"
						+" LEFT JOIN   user_usr us on e.updater=us.id"
						+" LEFT JOIN   department_dep  de  on  de.id=e.dep_id"
			 			+" where e.is_Active='Y'  ";
			 
			 			if(exa.getExam_num()!=null && !"".equals(exa.getExam_num())){
					sql+="  and  	vs.exam_num='"+exa.getExam_num().trim()+"'";	
			 			}
			 			
			 			String IS_ALL_DEPT_ITEM = this.customerInfoService.getCenterconfigByKey("IS_ALL_DEPT_ITEM", center_num).getConfig_value().trim();
						if("N".equals(IS_ALL_DEPT_ITEM)) {
							if(exa.getDep_id()!= 0){
								 sql+="  and  e.dep_id='"+exa.getDep_id()+"' ";
							 }
						}
			 			
			 			if(exa.getView_num()!=null && !"".equals(exa.getView_num())){
					sql+="  and  	e.view_num='"+exa.getView_num().trim()+"'";			
			 			}
						if(exa.getItem_num()!=null&&!exa.getItem_num().equals("")){
					sql+=" and  e.item_num='"+exa.getItem_num().trim()+"'";
						}
						if(exa.getItem_name()!=null&&!exa.getItem_name().equals("")){
					sql+=" and  (e.item_name  like'%"+exa.getItem_name().trim()+"%'"
					    +" or  e.item_pinyin  like'%"+exa.getItem_name().trim()+"%')";			
						}
						if(rt.equals("名称重复")){
					sql+="  and e.item_name   in   (select   e.item_name   from"
							+ "  examination_item e  where  e.is_Active='Y'  group   by   e.item_name having count(e.item_name)>1)"
							+ " 	ORDER BY   e.item_name";		
						}else{
					sql+="   order by e.seq_code ";
						}
						PageSupport map = this.jqm.getList(sql,pagesize,page,ExaminationItemDTO.class);
						webrole.setPage(page);
						webrole.setRp(pagesize);
						if ((map != null) && (map.getList() != null)) {
							webrole.setTotal(map.getRecTotal());
							webrole.setRows(map.getList());
						}
		}
	return webrole;
	}
	
	@Override
	public String getExamCodeById(long id)  throws ServiceException {
		String sql="";
		String item_code="";
		Statement statement = null;
		Connection connection = null;
			try {
				sql="SELECT item_num from examination_item where id= "+id;
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql.toString());
				while (rs.next()) {
					item_code=rs.getString("item_num");
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqle4) {
					sqle4.printStackTrace();
				}
			}
		return item_code;
	}
	
	private boolean saveOrUpdateExaminationItemVSLis(ExaminationItemVSLisDTO evsl)  throws ServiceException {
		if(StringUtil.isEmpty(evsl.getLis_item_id())) {
			return true;
		}
		boolean ret = false;
		Connection connection = null;
		Statement statement = null;
		try {
			String sql = "delete FROM examination_item_vs_lis where center_num = '"+evsl.getCenter_num()+"' and item_code = '"+evsl.getItem_code()+"' ";
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			
			for(String lis_item_id : evsl.getLis_item_id().split("\\+")) {
				sql = "INSERT INTO [examination_item_vs_lis] ([exam_item_id],[lis_item_id],[item_code],[center_num]) VALUES "
						+ " ("+evsl.getExam_item_id()+",'"+lis_item_id+"','"+evsl.getItem_code()+"','"+evsl.getCenter_num()+"') ";
				statement = connection.createStatement();
				statement.executeUpdate(sql);
			}
			ret = true;
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return ret;
	}

	
}
