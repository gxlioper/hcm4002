package com.hjw.wst.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.Examinatioin_centerDTO;
import com.hjw.wst.DTO.Examinatioin_center_dept;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.service.Examinatioin_centerService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class Examinatioin_centerImpl implements Examinatioin_centerService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	


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

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	/**
	 * 增加
	     * <p>Title: addExamination</p>   
	     * <p>Description: </p>   
	     * @param examinatioin
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#addExamination(com.hjw.wst.domain.Examinatioin_center)
	 */
	@Override
	public Examinatioin_center addExamination(Examinatioin_center examinatioin) throws ServiceException {
		this.pm.save(examinatioin);
		return examinatioin;
	}

	/**
	 * 删除
	     * <p>Title: delExamination</p>   
	     * <p>Description: </p>   
	     * @param examinatioin
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#delExamination(com.hjw.wst.domain.Examinatioin_center)
	 */
	@Override
	public void delExamination(Examinatioin_center examinatioin) throws ServiceException {
		this.pm.remove(examinatioin);

	}

	/**
	 * 更新
	     * <p>Title: updExamination</p>   
	     * <p>Description: </p>   
	     * @param examinatioin
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#updExamination(com.hjw.wst.domain.Examinatioin_center)
	 */
	@Override
	public Examinatioin_center updExamination(Examinatioin_center examinatioin) throws ServiceException {
		this.pm.update(examinatioin);
		return examinatioin;
	}

	/**
	 * 查询全部
	     * <p>Title: queryAllExaminatioin</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#queryAllExaminatioin()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Examinatioin_centerDTO> queryAllExaminatioin() throws ServiceException {
		String sql="select e.center_name,e.center_num,e.is_active,u.chi_name as creater,e.create_time,u.chi_name as updater,e.update_time,e.photo_function_status "
 +" from examinatioin_center e,user_usr u "
 +" where  u.id=e.id  order by create_time ";
		List<Examinatioin_centerDTO> list=jqm.getList(sql, Examinatioin_centerDTO.class);
		return list;
	}

	/**
	 * id查询
	     * <p>Title: loadExaminatioin</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#loadExaminatioin(long)
	 */
	@Override
	public Examinatioin_center loadExaminatioin(long id) throws ServiceException {
		return (Examinatioin_center) this.qm.get(Examinatioin_center.class,id);
	}

	/**
	 * 编码查询
	     * <p>Title: queryByNum</p>   
	     * <p>Description: </p>   
	     * @param center_num
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#queryByNum(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Examinatioin_center queryByNum(String center_num) throws ServiceException {
		String sql = "from Examinatioin_center where center_num = '"+center_num.trim()+"'";
		List<Examinatioin_center> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	/**
	 * 分页查询
	     * <p>Title: queryPageExaminatioin</p>   
	     * <p>Description: </p>   
	     * @param pageno
	     * @param pagesize
	     * @return  
	     * @see com.hjw.wst.service.Examinatioin_centerService#queryPageExaminatioin(int, int)
	 */
	@Override
	public PageReturnDTO queryPageExaminatioin(String center_num ,String center_name,int pageno, int pagesize) {
		String sql=" select e.limit_count,e.id,e.center_num,e.center_name,e.is_active,u.chi_name as creater,e.create_time,"
					+" u.chi_name as updater,e.update_time,e.photo_function_status "
					+" from examinatioin_center e left join user_usr u on e.creater=u.id left join user_usr ul on e.updater =ul.id "
					+" where  center_num like '%"+center_num+"%' "
					+" and center_name like '%"+center_name+"%' "
					+" order by create_time";
		PageSupport map = this.jqm.getList(sql,pagesize, pageno, Examinatioin_centerDTO.class);
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
	public List<Examinatioin_center_dept> getDept() throws ServiceException {
		String sql = " select  c.id as id,c.center_name as text from examinatioin_center c where c.parent_id='0' ";
		System.out.println(sql);
		List<Examinatioin_center_dept> li = this.jqm.getList(sql, Examinatioin_center_dept.class);
		for(Examinatioin_center_dept bill :li){
			String sql1= " select  c.id as id,c.center_name as text from examinatioin_center c where c.parent_id='"+bill.getId()+"'  ";
			System.out.println(sql1);
			List<Examinatioin_center_dept> list1 = this.jqm.getList(sql1, Examinatioin_center_dept.class);
			bill.setChildren(list1);
		}
		
		return li;
	}

	@Override
	public List<Examinatioin_center> getExaminatioin_centerList(String center_name,String center_num) throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
        	  sqltext.append("with cte as ( select top 200 * from examinatioin_center c where ");
        	  List<String> list=new ArrayList<String>();
        	  if(center_name!=null&&center_name.length()>0){
        		  list.add( " c.center_name like '%"+center_name+"%' ");
        	  }
        	  if(center_num!=null&&center_num.length()>0){
        		  list.add( " c.center_num='"+center_num+"' ");
        	  }
        	  if(list!=null&&list.size()>0){
        		  if(list.size()==2){
        			  sqltext.append("("+list.get(0)+" and "+list.get(1)+") and ");
        		  }else{
        			  sqltext.append("("+list.get(0)+") and ");
        		  }
        	  }
        	  sqltext.append(" c.is_Active='Y' order by c.id desc "
        			  +" union all "
        			  +" select c.* FROM examinatioin_center c,cte b where c.id  = b.parent_id and c.is_Active='Y') "
        			  +" select distinct c.id,c.parent_id as _parentId,c.center_name as center_name,c.center_num as center_num "
        			  +" ,u.chi_name as creater,c.create_time as create_time,ul.chi_name as updater,c.update_time as update_time,c.photo_function_status,"
        			  + "c.limit_count as limit_count "
        			  +" from cte c left join user_usr u on c.creater=u.id left join user_usr ul on c.updater =ul.id ");
        System.out.println(sqltext.toString());
        @SuppressWarnings("unchecked")
        List<Examinatioin_center> list1 = this.jqm.getList(sqltext.toString(), Examinatioin_centerDTO.class);
		return list1;
	}

	@Override
	public List<Examinatioin_center> getExaminatioin_centerListByParentId(String id) throws ServiceException {
		String sql = "from Examinatioin_center where parent_id = '"+id+"'";
		List<Examinatioin_center> list = this.qm.find(sql);
		if(list.size() != 0){
			return list;
		}else{
			return null;
		}
	}
	
	@Override
	/**
	 * 获取体检中心列表
	     * <p>Title: getExaminatioin_centerList</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.Examinatioin_centerService#getExaminatioin_centerList(java.lang.Long)
	 */
	public List<Examinatioin_center> getExaminatioin_centerList(Long id) throws ServiceException {
		String sql = "from Examinatioin_center where id = '"+id+"'";
		List<Examinatioin_center> list = this.qm.find(sql);
		if(list.size() != 0){
			return list;
		}
		return null;
	}

	@Override
	public List<Examinatioin_center> getExaminatioin_centerListByParent(String parent_name) throws ServiceException {
		String sql = "from Examinatioin_center where center_name = '"+parent_name+"'";
		List<Examinatioin_center> list = this.qm.find(sql);
		if(list.size() != 0){
			return list;
		}
		return null;
	}

	@Override
	public List<Exam_user> getExam_userById(String id) throws ServiceException {
		String sql = "from Exam_user where user_id = '"+id+"'";
		List<Exam_user> list = this.qm.find(sql);
		if(list.size() != 0){
			return list;
		}
		return null;
	}

	@Override
	public List<Examinatioin_center_dept> getExaminatioin_centerListDTO(long center_id,String userid) throws ServiceException {
		List<Exam_user> examList=this.getExam_userById(userid);
		String sql ="select  c.id as id,c.center_name as center_name from examinatioin_center c where c.parent_id='0' ";
		if(center_id>0){
			sql = " select  c.id as id,c.center_name as center_name from examinatioin_center c where c.id='"+center_id+"' and c.parent_id='0' ";
		}		
		List<Examinatioin_center_dept> li = this.jqm.getList(sql, Examinatioin_center_dept.class);
		for(Examinatioin_center_dept bill :li){
			String sql1= " select  c.id as id,c.center_name as center_name from examinatioin_center c where c.parent_id='"+bill.getId()+"'  ";
			List<Examinatioin_center_dept> list1 = this.jqm.getList(sql1, Examinatioin_center_dept.class);
			if(examList!=null&&examList.size()>0){
				for(Exam_user exam_user:examList){
					if(bill.getId()==exam_user.getExam_center_id()){
						bill.setCheck("1");
					}
				}
			}
			bill.setChildren(list1);
			for(Examinatioin_center_dept bill1:list1){
				String sql2= " select  c.id as id,c.center_name as center_name from examinatioin_center c where c.parent_id='"+bill1.getId()+"'  ";
				List<Examinatioin_center_dept> list2 = this.jqm.getList(sql2, Examinatioin_center_dept.class);
				if(examList!=null&&examList.size()>0){
					for(Exam_user exam_users:examList){
						if(bill1.getId()==exam_users.getExam_center_id()){
							bill1.setCheck("1");
						}
					}
				}
				bill1.setChildren(list2);
				for(Examinatioin_center_dept bill2:list2){
					if(examList!=null&&examList.size()>0){
						for(Exam_user exam_users:examList){
							if(bill2.getId()==exam_users.getExam_center_id()){
								bill2.setCheck("1");
							}
						}
					}					
				}
				
			}
			
		}
		return li;
	}

}
