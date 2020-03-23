package com.hjw.zyb.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;






import org.hsqldb.lib.StringUtil;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.zyb.DTO.ZybEconomicTreeDTO;
import com.hjw.zyb.domain.ZybAreaCode;
import com.hjw.zyb.domain.ZybEconomicIndustry;
import com.hjw.zyb.domain.ZybPhyexaclass;
import com.hjw.zyb.domain.ZybTypeOfWork;
import com.hjw.zyb.service.ZybOccuService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOccuServiceimpl implements ZybOccuService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
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
	
	
//-------------------------------------------经济行业--------------------------------------------------------
	
	
	
	
	/**
	 * 获取父级单位名称
	     * <p>Title: getp_name</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#getp_name(java.lang.String)
	 */
	@Override
	public ZybEconomicIndustry getp_name(String id) throws ServiceException {
		String sql=" select e.industryID,e.industry_name from zyb_economicindustry e where e.industryID='"+id+"' ";
		List<ZybEconomicIndustry> l=this.jqm.getList(sql, ZybEconomicIndustry.class);
		return l.get(0);
	}

	/**
	 * 经济行业id查询
	     * @Title: findZybEconomicIndustryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybEconomicIndustry      
	     * @throws
	 */
	public ZybEconomicIndustry findZybEconomicIndustryById(String id) throws ServiceException {
		String sql=" select industryID,industry_code,industry_name,parentID from zyb_economicindustry e where e.industryID='"+id+"' ";
		List<ZybEconomicIndustry> l=this.jqm.getList(sql, ZybEconomicIndustry.class);
		return l.get(0);
	}
	
	
	
	/**
	 * 经济行业新增
	     * <p>Title: addIndustry</p>   
	     * <p>Description: </p>   
	     * @param ei
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#addIndustry(com.hjw.zyb.domain.ZybEconomicIndustry)
	 */
	@Override
	public ZybEconomicIndustry addIndustry(ZybEconomicIndustry ei) throws ServiceException {
		this.pm.save(ei);
		return ei;
	}

	
	/**
	 * 经济行业删除
	     * <p>Title: deleteIndustryc</p>   
	     * <p>Description: </p>   
	     * @param ei
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#deleteIndustryc(com.hjw.zyb.domain.ZybEconomicIndustry)
	 */
	@Override
	public void deleteIndustryc(ZybEconomicIndustry ei) throws ServiceException {
		this.pm.remove(ei);
		
	}

	
	
	/**
	 * 经济行业更新 
	     * <p>Title: updateIndustry</p>   
	     * <p>Description: </p>   
	     * @param ei
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#updateIndustry(com.hjw.zyb.domain.ZybEconomicIndustry)
	 */
	@Override
	public ZybEconomicIndustry updateIndustry(ZybEconomicIndustry ei) throws ServiceException {
		this.pm.update(ei);
		return ei;
	}
	
	
	/**
	 * 行业管理zyb203
	     * <p>Title: queryByIndustry</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#queryByIndustry()
	 */
//	@Override
//	public PageReturnDTO queryByIndustry(String industry_code,String industry_name,int pagesize, int pageno) throws ServiceException {
//		String sql = "select z.industryID ,z.industry_code,z.industry_name from zyb_economicindustry z  "
//				+ " where  industry_name like '%"+industry_name+"%' and industry_code like '%"+industry_code+"%' and z.parentID = '' or z.parentID is null ";
//		
//		//List<EconomicTreeDTO> list = this.jqm.getList(sql, EconomicTreeDTO.class);
//		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybEconomicTreeDTO.class);
//		PageReturnDTO webrole = new PageReturnDTO();
//		
//		List<ZybEconomicTreeDTO> li = map.getList();
//		for(ZybEconomicTreeDTO economicTreeDTO :li){
//			String sql1= "select z.industryID ,z.industry_code,z.industry_name from zyb_economicindustry z where z.parentID = '"+economicTreeDTO.getIndustryID()+"'";
//			List<ZybEconomicTreeDTO> list1 = this.jqm.getList(sql1, ZybEconomicTreeDTO.class);
//			economicTreeDTO.setChildren(list1);
//		}
//		webrole.setPage(pageno);
//		webrole.setRp(pagesize);
//		if ((map != null) && (map.getList() != null)) {
//			webrole.setTotal(map.getRecTotal());
//			webrole.setRows(li);
//		}
//		return webrole;
//	}
	@Override
	public PageReturnDTO queryByIndustry(String industry_code,String industry_name,int industry_type,int pagesize, int pageno) throws ServiceException {
		String sql = "select z.industryID ,z.industry_code,z.industry_name from zyb_economicindustry z  "
				+ " where  industry_name like '%"+industry_name+"%' and industry_code like '%"+industry_code+"%' and z.industry_type = "+industry_type+" and (z.parentID = '' or z.parentID is null) ";
		
		//List<EconomicTreeDTO> list = this.jqm.getList(sql, EconomicTreeDTO.class);
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybEconomicTreeDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		
		List<ZybEconomicTreeDTO> li = map.getList();
		for(ZybEconomicTreeDTO economicTreeDTO :li){
			String sql1= "select z.industryID ,z.industry_code,z.industry_name from zyb_economicindustry z where z.parentID = '"+economicTreeDTO.getIndustryID()+"' and z.industry_type = " + industry_type;
			List<ZybEconomicTreeDTO> list1 = this.jqm.getList(sql1, ZybEconomicTreeDTO.class);
			economicTreeDTO.setChildren(list1);
		}
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(li);
		}
		return webrole;
	}

	
	
	/**
	 * 查询全部
	     * <p>Title: getIndAll</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#getIndAll()
	 */
//	@Override
//    public List<ZybEconomicTreeDTO> getIndustryAll() throws ServiceException{
//    	String sql="select z.industry_name as industry_name_f ,z.industryID,z.industry_code,z.parentID as _parentId,z.industryID as id, "
//    			+ " z.industry_name as industry_name_i from  zyb_economicindustry z "
//    			+" where z.parentID='' or z.parentID is null or z.parentID='0' ";
//    	List<ZybEconomicTreeDTO> list = this.jqm.getList(sql, ZybEconomicTreeDTO.class);
//    	return list;
//    }
	@Override
    public List<ZybEconomicTreeDTO> getIndustryAllByType(int industry_type) throws ServiceException{
    	String sql="select z.industry_name as industry_name_f ,z.industryID,z.industry_code,z.parentID as _parentId,z.industryID as id, "
    			+ " z.industry_name as industry_name_i from  zyb_economicindustry z "
    			+" where (z.parentID='' or z.parentID is null or z.parentID='0') and industry_type = " + industry_type;
    	List<ZybEconomicTreeDTO> list = this.jqm.getList(sql, ZybEconomicTreeDTO.class);
    	return list;
    }
    
    /**
     * id查询
         * <p>Title: queryById_EI</p>   
         * <p>Description: </p>   
         * @param id
         * @return
         * @throws ServiceException   
         * @see com.hjw.zyb.service.ZybOccuService#queryById_EI(java.lang.String)
     */
	@Override
	public ZybEconomicIndustry queryById_EI(String id) throws ServiceException {
		return (ZybEconomicIndustry) this.qm.get(ZybEconomicIndustry.class, id);
	}

	public List<ZybEconomicTreeDTO> getind(String id) throws ServiceException{
		String sql = " select * from zyb_economicindustry z where z.parentID = '"+id+"' ";
		List<ZybEconomicTreeDTO> list = this.jqm.getList(sql, ZybEconomicTreeDTO.class);
		return list;
	}
	
	
	/**
	 * 编码验证
	     * <p>Title: getIndustrycode</p>   
	     * <p>Description: </p>   
	     * @param code
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#getIndustrycode(java.lang.String)
	 */
	@Override
	public ZybEconomicIndustry getIndustrycode(String code)throws ServiceException {
		String hql="from ZybEconomicIndustry  where industry_code = '"+code+"' ";
		List<ZybEconomicIndustry> list=this.qm.find(hql);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	
	
	//--------------------------------------------工种管理------------------------------------------------------------------
	
	
	
	
	
	/**
	 * 工种添加
	     * <p>Title: addTypeOfWork</p>   
	     * <p>Description: </p>   
	     * @param tow
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#addTypeOfWork(com.hjw.zyb.domain.ZybTypeOfWork)
	 */
	@Override
	public ZybTypeOfWork addTypeOfWork(ZybTypeOfWork tow)throws ServiceException {
		this.pm.save(tow);
		return tow;
	}

	
	/**
	 * 工种删除
	     * <p>Title: deleteTypeOfWork</p>   
	     * <p>Description: </p>   
	     * @param tow
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#deleteTypeOfWork(com.hjw.zyb.domain.ZybTypeOfWork)
	 */
	@Override
	public void deleteTypeOfWork(ZybTypeOfWork tow) throws ServiceException {
		this.pm.remove(tow);
		
	}

	

	/**
	 * 工种更新
	     * <p>Title: updateTypeOfWork</p>   
	     * <p>Description: </p>   
	     * @param tow
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#updateTypeOfWork(com.hjw.zyb.domain.ZybTypeOfWork)
	 */
	@Override
	public ZybTypeOfWork updateTypeOfWork(ZybTypeOfWork tow) throws ServiceException {
		this.pm.update(tow);
		return tow;
	}

	
	
	/**
	 * 工种分页查询
	     * <p>Title: queryByAll_tow</p>   
	     * <p>Description: </p>   
	     * @param typeofwork_name
	     * @param typeofwork_code
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#queryByAll_tow(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public PageReturnDTO queryByAllTypeOfWork(String typeofwork_name,String typeofwork_code,int pagesize, int pageno) throws ServiceException {
		String sql = " select t.typeofworkID,t.typeofwork_code,t.typeofwork_name from zyb_typeofwork  t "
					+" where typeofwork_name like '%"+typeofwork_name+"%' and typeofwork_code like '%"+typeofwork_code+"%' ";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybEconomicTreeDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	

	
	/**
	 * 工种id查询
	     * <p>Title: queryById_tow</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuService#queryById_tow(java.lang.String)
	 */
	@Override
	public ZybTypeOfWork queryByIdTypeOfWork(String id) throws ServiceException {
		return (ZybTypeOfWork) this.qm.get(ZybTypeOfWork.class, id);
	}
	
	
	

	
	//---------------------------------------------体检类别管理----------------------------------------------------
	
	
	
	
		/**
		 * 体检类别添加
		     * <p>Title: addPhy</p>   
		     * <p>Description: </p>   
		     * @param p
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#addPhy(com.hjw.zyb.domain.ZybPhyexaclass)
		 */
		@Override
		public ZybPhyexaclass addPhyexaclass(ZybPhyexaclass p) throws ServiceException {
			this.pm.save(p);
			return p;
		}
		
		
		/**
		 * 体检类别删除
		     * <p>Title: deletePhy</p>   
		     * <p>Description: </p>   
		     * @param p
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#deletePhy(com.hjw.zyb.domain.ZybPhyexaclass)
		 */
		@Override
		public void deletePhyexaclass(ZybPhyexaclass p) throws ServiceException {
			this.pm.remove(p);
			
		}
		
		
		/**
		 * 体检类别更新
		     * <p>Title: updatePhy</p>   
		     * <p>Description: </p>   
		     * @param p
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#updatePhy(com.hjw.zyb.domain.ZybPhyexaclass)
		 */
		@Override
		public ZybPhyexaclass updatePhyexaclass(ZybPhyexaclass p) throws ServiceException {
			this.pm.update(p);
			return p;
		}
		
		
		
		/**
		 * 体检类别查询全部
		     * <p>Title: queryAllPhy</p>   
		     * <p>Description: </p>   
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#queryAllPhy()
		 */
		@Override
		public List<ZybEconomicTreeDTO> queryAllPhyexaclass() throws ServiceException {
			// TODO Auto-generated method stub
			return null;
		}
		
		
		/**
		 * 体检类别id查询
		     * <p>Title: queryByPhy_id</p>   
		     * <p>Description: </p>   
		     * @param id
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#queryByPhy_id(java.lang.String)
		 */
		@Override
		public ZybPhyexaclass queryByPhyexaclassid(String id) throws ServiceException {
			
			return (ZybPhyexaclass) this.qm.get(ZybPhyexaclass.class, id);
		}
		
		
		
		/**
		 * 体检类别分页查询
		     * <p>Title: queryByPage</p>   
		     * <p>Description: </p>   
		     * @param phyexaclass_code
		     * @param phyexaclass_name
		     * @param pagesize
		     * @param pageno
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#queryByPage(java.lang.String, java.lang.String, int, int)
		 */
		@Override
		public PageReturnDTO queryByPagePhyexaclass(String phyexaclass_code,String phyexaclass_name, int pagesize, int pageno)
				throws ServiceException {
			String sql = " select p.center_num,p.isprintcard,p.isupload,p.phyexaclassID,p.phyexaclass_code,p.phyexaclass_name,p.remark,p.showorder "
					+ " from zyb_physicalexaminationclass  p  "
					+" where p.phyexaclass_code like '%"+phyexaclass_code+"%'  and  p.phyexaclass_name like '%"+phyexaclass_name+"%' ";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybEconomicTreeDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
		}

		
		/**
		 * 体检类别编码验证
		     * <p>Title: getByphyexacode</p>   
		     * <p>Description: </p>   
		     * @param code
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#getByphyexacode(java.lang.String)
		 */
		@Override
		public ZybPhyexaclass getByphyexacode(String code)throws ServiceException {
			String hql="from ZybPhyexaclass where phyexaclass_code='"+code+"'";
			List<ZybPhyexaclass> list=this.qm.find(hql);
			if(list.size()!=0){
				return list.get(0);
			}
			return null;
		}
		
		/**
		 * 
		     * @Title: queryByAll   
		     * @Description: 获取从业工种列表  
		     * @param: @param typeofwork_name
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<ZybEconomicTreeDTO>      
		     * @throws
		 */
				
		public List<TreeDTO> queryByAllTypOfWork(String name)throws ServiceException{
			StringBuffer sqltext= new StringBuffer();
            String sql="select top 50 t.typeofworkID,t.typeofwork_code,t.typeofwork_name from zyb_typeofwork t  order by t.typeofwork_code";
			if(!StringUtil.isEmpty(name)){
				sql = "select top 50 t.typeofworkID,t.typeofwork_code,t.typeofwork_name from zyb_typeofwork t where"
						+ " (t.typeofwork_name like '%"+name+"%' or t.typeofwork_code like '%"+name+"%')  order by t.typeofwork_code";
			}	
			
			Connection connection = null;
			Statement statement = null;
			List<TreeDTO> list = new ArrayList<TreeDTO>();
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				while (rs.next()) {
					TreeDTO jd =new TreeDTO();
					jd.setId(rs.getString("typeofworkID"));
					jd.setText(rs.getString("typeofwork_code")+""+rs.getString("typeofwork_name"));
					jd.setAttributes("0");
					list.add(jd);
				}
				rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqle4) {
					sqle4.printStackTrace();
				}
			}
			return list;
		}
		
		/**
		 * 
		     * @Title: getCyhyList   
		     * @Description: 获取从业行业   
		     * @param: @param name
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<TreeDTO>      
		     * @throws
		 */
//		@Override
//		public List<TreeDTO> getCyhyList(String name)throws ServiceException {
//			String sqltext="with cte as (select Industryid,parentID,industry_name,industry_code from zyb_economicindustry where parentID='' or parentID is null"
//					+ "  union all select d.Industryid,d.parentID,d.industry_name,d.industry_code"
//							+ " from cte c inner join zyb_economicindustry d on c.Industryid = d.parentID) "
//							+ "select * from cte order by industry_code ";
//			
//			if(!StringUtil.isEmpty(name)){
//				sqltext="with cte as (select Industryid,parentID,industry_name,industry_code from zyb_economicindustry "
//						+ " where (industry_name like '%"+name+"%' or industry_code like '%"+name+"%') union all select d.Industryid,d.parentID,d.industry_name,d.industry_code"
//								+ " from cte c inner join zyb_economicindustry d on c.Industryid = d.parentID) "
//								+ "select * from cte order by industry_code ";
//			}
//			
//			Connection connection = null;
//			Statement statement = null;
//			int count = 0;
//			List<TreeDTO> list = new ArrayList<TreeDTO>();
//			try {
//				// 读取记录数
//				connection = this.jqm.getConnection();
//				statement = connection.createStatement();
//				ResultSet rs = statement.executeQuery(sqltext);
//				while (rs.next()) {
//					TreeDTO jd =new TreeDTO();
//					jd.setId(rs.getString("Industryid"));
//					jd.setText(rs.getString("industry_code")+""+rs.getString("industry_name"));
//					jd.setAttributes(rs.getString("parentID"));
//					list.add(jd);
//				}
//				rs.close();
//			} catch (SQLException ex) {
//				ex.printStackTrace();
//			} finally {
//				try {
//					if (statement != null) {
//						statement.close();
//					}
//					if (connection != null) {
//						connection.close();
//					}
//				} catch (SQLException sqle4) {
//					sqle4.printStackTrace();
//				}
//			}
//			return list;
//		}
		public List<TreeDTO> getCyhyList(String name, int industry_type)throws ServiceException {
			String sqltext="with cte as (select Industryid,parentID,industry_name,industry_code from zyb_economicindustry where parentID='' or parentID is null and industry_type = "+industry_type
					+ "  union all select d.Industryid,d.parentID,d.industry_name,d.industry_code"
							+ " from cte c inner join zyb_economicindustry d on c.Industryid = d.parentID and d.industry_type = "+industry_type+") "
							+ "select * from cte order by industry_code ";
			
			if(!StringUtil.isEmpty(name)){
				sqltext="with cte as (select Industryid,parentID,industry_name,industry_code from zyb_economicindustry "
						+ " where (industry_name like '%"+name+"%' or industry_code like '%"+name+"%') and industry_type = "+industry_type+" union all select d.Industryid,d.parentID,d.industry_name,d.industry_code"
								+ " from cte c inner join zyb_economicindustry d on c.Industryid = d.parentID and industry_type = "+industry_type+") "
								+ "select * from cte order by industry_code ";
			}
			
			Connection connection = null;
			Statement statement = null;
			int count = 0;
			List<TreeDTO> list = new ArrayList<TreeDTO>();
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sqltext);
				while (rs.next()) {
					TreeDTO jd =new TreeDTO();
					jd.setId(rs.getString("Industryid"));
					jd.setText(rs.getString("industry_code")+""+rs.getString("industry_name"));
					jd.setAttributes(rs.getString("parentID"));
					list.add(jd);
				}
				rs.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqle4) {
					sqle4.printStackTrace();
				}
			}
			return list;
		}

		
		/**
		 * 工种管理批量 删除
		     * <p>Title: deletes</p>   
		     * <p>Description: </p>   
		     * @param ids
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#deletes(java.lang.String)
		 */
		@Override
		public void deletes(String ids) throws ServiceException {
			String hql="from ZybTypeOfWork where typeofworkID in("+ids+")";
			List<ZybTypeOfWork> l=qm.find(hql);
			for (ZybTypeOfWork zybTypeOfWork : l) {
				pm.remove(zybTypeOfWork);
			}
			
		}
		
		
		/**
		 * 工种编码验证
		     * <p>Title: getBycode</p>   
		     * <p>Description: </p>   
		     * @param code
		     * @return
		     * @throws ServiceException   
		     * @see com.hjw.zyb.service.ZybOccuService#getBycode(java.lang.String)
		 */
		@Override
		public ZybTypeOfWork getBycode(String code) throws ServiceException {
			String hql=" from ZybTypeOfWork where typeofwork_code='"+code+"'";
			List<ZybTypeOfWork> t=qm.find(hql);
			if(t.size()!=0){
				return t.get(0);
			}
			return null;
		}

		@Override
		public List<ZybEconomicTreeDTO> getIndustryAll() throws ServiceException {
			String sql="select z.industry_name as industry_name_f ,z.industryID,z.industry_code,z.parentID as _parentId,z.industryID as id, "
	    			+ " z.industry_name as industry_name_i from  zyb_economicindustry z "
	    			+" where z.parentID='' or z.parentID is null or z.parentID='0' ";
	    	List<ZybEconomicTreeDTO> list = this.jqm.getList(sql, ZybEconomicTreeDTO.class);
	    	return list;
		}

		
}
