package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybOccuindustryDTO;
import com.hjw.zyb.domain.ZybCheckcriterion;
import com.hjw.zyb.domain.ZybOccuindustry;
import com.hjw.zyb.domain.ZybOccutypeofwork;
import com.hjw.zyb.domain.ZyboccuhazardfactorsCheckcriterion;
import com.hjw.zyb.model.ZybOccuindustryModel;
import com.hjw.zyb.service.ZybOccuindustryService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOccuindustryServiceImpl implements ZybOccuindustryService {
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
	
	
	
	
	
	//-----------------------------------------------从业行业----------------------------------------------------
	
	
	
	/**
	 * 从业行业添加
	     * <p>Title: addScale</p>   
	     * <p>Description: </p>   
	     * @param e
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#addScale(com.hjw.zyb.domain.ZybOccuindustry)
	 */
	@Override
	public ZybOccuindustry addOccuindustry(ZybOccuindustry e) throws ServiceException {
		this.pm.save(e);
		return e;
	}

	
	/**
	 *从业行业删除
	     * <p>Title: deleteOccuInd</p>   
	     * <p>Description: </p>   
	     * @param e
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#deleteOccuInd(com.hjw.zyb.domain.ZybOccuindustry)
	 */
	@Override
	public void deleteOccuindustry(ZybOccuindustry e) throws ServiceException {
		this.pm.remove(e);

	}

	
	/**
	 * 从业行业更新
	     * <p>Title: updateOccuInd</p>   
	     * <p>Description: </p>   
	     * @param e
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#updateOccuInd(com.hjw.zyb.domain.ZybOccuindustry)
	 */
	@Override
	public ZybOccuindustry updateOccuindustry(ZybOccuindustry e)throws ServiceException {
		this.pm.update(e);
		return e;
	}

	
	/**
	 * 从业行业id查询
	     * <p>Title: queryByID</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryByID(java.lang.String)
	 */
	@Override
	public ZybOccuindustry queryByID(String id) throws ServiceException {
		
		return (ZybOccuindustry) this.qm.get(ZybOccuindustry.class, id);
	}

	
	
	/**
	 * 从业行业分页查询
	     * <p>Title: queryByOccuIndustry</p>   
	     * <p>Description: </p>   
	     * @param industry_code
	     * @param industry_name
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryByOccuIndustry(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public PageReturnDTO queryByOccuIndustry(String industry_code,String industry_name, int pagesize, int pageno ,String center_num)
			throws ServiceException {
		String sql=" select e.industryID,e.industry_code,e.industry_name,e.phyexeperiod,e.trainperiod,e.showorder, "
				+" s.set_name as package_name,s.set_num as exam_set_code from  zyb_occuindustry e left join  exam_set  s on e.packageID=s.id "
   				+" where industry_code like '%"+industry_code+"%' and e.center_num='"+center_num+"' and industry_name like '%"+industry_name+"%' ";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybOccuindustryDTO.class);
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
	 * 从业行业套餐名称获取
	     * <p>Title: getAllExam_set</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#getAllExam_set()
	 */
	@Override
	public List<ZybOccuindustryDTO> getAllExam_set(String center_num) throws ServiceException {
		String sql="  select s.id as s_id,s.set_name,s.set_num from  exam_set s  WHERE s.is_Active='Y' and s.center_num='"+center_num+"'";
		List<ZybOccuindustryDTO> list=this.jqm.getList(sql, ZybOccuindustryDTO.class);
		return list;
	}

	
	/**
	 * 从业行业编码验证
	     * <p>Title: getByOcuindustrycode</p>   
	     * <p>Description: </p>   
	     * @param code
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#getByOcuindustrycode(java.lang.String)
	 */
	@Override
	public ZybOccuindustry getByOcuindustrycode(String code,UserDTO user)throws ServiceException {
		String hql="from ZybOccuindustry where industry_code='"+code+"'  AND center_num ='"+user.getCenter_num()+"'";
		List<ZybOccuindustry> list=qm.find(hql);
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}
	
	
	
	
	
	//---------------------------------------------从业工种-------------------------------------------------
	
	
	
	
	
	/**
	 *从业工种添加
	     * <p>Title: addOccugzh</p>   
	     * <p>Description: </p>   
	     * @param z
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#addOccugzh(com.hjw.zyb.domain.ZybOccutypeofwork)
	 */
	@Override
	public ZybOccutypeofwork addOccugzh(ZybOccutypeofwork z)throws ServiceException {
		this.pm.save(z);
		return z;
	}

	
	
	/**
	 * 从业工种删除
	     * <p>Title: deleteOccugzh</p>   
	     * <p>Description: </p>   
	     * @param z
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#deleteOccugzh(com.hjw.zyb.domain.ZybOccutypeofwork)
	 */
	@Override
	public void deleteOccugzh(ZybOccutypeofwork z) throws ServiceException {
		this.pm.remove(z);
		
	}

	
	
	/**
	 *从业工种删除
	     * <p>Title: updateOccugzh</p>   
	     * <p>Description: </p>   
	     * @param z
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#updateOccugzh(com.hjw.zyb.domain.ZybOccutypeofwork)
	 */
	@Override
	public ZybOccutypeofwork updateOccugzh(ZybOccutypeofwork z)throws ServiceException {
		this.pm.update(z);
		return z;
	}

	
	/**
	 * 从业工种id查询
	     * <p>Title: queryByOccugzhid</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryByOccugzhid(java.lang.String)
	 */
	@Override
	public ZybOccutypeofwork queryByOccugzhid(String id) throws ServiceException {
		
		return (ZybOccutypeofwork) this.qm.get(ZybOccutypeofwork.class, id);
	}

	
	/**
	 * 从业工行分页查询
	     * <p>Title: queryByOccugzh</p>   
	     * <p>Description: </p>   
	     * @param typeofwork_code
	     * @param typeofwork_name
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryByOccugzh(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public PageReturnDTO queryByOccugzh(String typeofwork_code,String typeofwork_name, int pagesize, int pageno)
			throws ServiceException {
		String sql=" select z.typeofworkID,z.typeofwork_code,z.typeofwork_name "
				+" from   zyb_occutypeofwork  z "
 				+" where typeofwork_code like '%"+typeofwork_code+"%'  and typeofwork_name like '%"+typeofwork_name+"%'";
		PageSupport map=this.jqm.getList(sql, pageno, pagesize, ZybOccuindustryDTO.class);
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
	 * 从业工种编码查询
	     * <p>Title: queryBycode</p>   
	     * <p>Description: </p>   
	     * @param code
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryBycode(java.lang.String)
	 */
	public ZybOccutypeofwork queryBycode(String code) throws ServiceException {
		String sql = "from ZybOccutypeofwork where typeofwork_code = '"+code.trim()+"'";
		List<ZybOccutypeofwork> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	
	
	
	
	//-----------------------------------------检查依据----------------------------------------------------------
	
	
	
	
	
	
	
	/**
	 * 检查依据添加
	     * <p>Title: addCheckyj</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#addCheckyj(com.hjw.zyb.domain.ZybCheckcriterion)
	 */
	@Override
	public ZybCheckcriterion addCheckyj(ZybOccuindustryModel model)throws ServiceException {
		
		ZybCheckcriterion c=new ZybCheckcriterion();
		c.setCriterion_name(model.getCriterion_name());
		this.pm.save(c);
		return c;
	}

	
	/**
	 * 检查依据删除
	     * <p>Title: deleteCheckyj</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#deleteCheckyj(com.hjw.zyb.domain.ZybCheckcriterion)
	 */
	@Override
	public void deleteCheckyj(ZybCheckcriterion c) throws ServiceException {
		this.pm.remove(c);
		
	}

	
	/**
	 * 检查依据批量删除
	     * <p>Title: deletejcyjs</p>   
	     * <p>Description: </p>   
	     * @param ids
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#deletejcyjs(java.lang.String)
	 */
	@Override
	public void deletejcyjs(String ids) throws ServiceException {
		String hql="from ZybCheckcriterion  where criterionID in("+ids+")";
		List<ZybCheckcriterion> l=qm.find(hql);
		for (ZybCheckcriterion zybCheckcriterion : l) {
			pm.remove(zybCheckcriterion);
		}
	}

	
	
	/**
	 * 检查依据更新
	     * <p>Title: updateCheckyj</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#updateCheckyj(com.hjw.zyb.domain.ZybCheckcriterion)
	 */
	@Override
	public ZybCheckcriterion updateCheckyj(ZybOccuindustryModel model)throws ServiceException {
		ZybCheckcriterion c=this.queryBycheckyjID(model.getCriterionID());
		c.setCriterion_name(model.getCriterion_name());
		this.pm.update(c);
		return c;
	}

	
	/**
	 * 检查依据id查询
	     * <p>Title: queryBycheckyjID</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryBycheckyjID(java.lang.String)
	 */
	@Override
	public ZybCheckcriterion queryBycheckyjID(String id) throws ServiceException {
		
		return (ZybCheckcriterion) this.qm.get(ZybCheckcriterion.class, id);
	}

	
	
	/**
	 * 检查依据分页查询
	     * <p>Title: queryBycheckyjPage</p>   
	     * <p>Description: </p>   
	     * @param criterion_name
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuindustryService#queryBycheckyjPage(java.lang.String, int, int)
	 */
	@Override
	public PageReturnDTO queryBycheckyjPage(ZybOccuindustryModel model, int pagesize,int pageno) throws ServiceException {
		String sql = " SELECT ch.criterionID,ch.criterion_name FROM    zyb_checkcriterion ch  WHERE  1=1 ";
					if(model.getCriterion_name()!=null && !"".equals(model.getCriterion_name())){
					sql+="   AND  ch.criterion_name like '%"+model.getCriterion_name()+"%'   ";	
					}
		PageSupport map=this.jqm.getList(sql, pageno,pagesize, ZybOccuindustryDTO.class);
		PageReturnDTO p=new PageReturnDTO();
		p.setPage(pageno);
		p.setRp(pagesize);
		if((map!=null) && (map.getList()!=null))
			p.setTotal(map.getRecTotal());
			p.setRows(map.getList());
		return p;
	}

	@Override
	public ZybOccuindustryDTO queryByOccuindustryID(String id,String center_num)throws ServiceException {
		String sql=" select  c.industry_code,c.industry_name,e.set_num,e.set_name as package_name,c.packageID,c.trainperiod,c.phyexeperiod "
				+" from dbo.zyb_occuindustry  c left join exam_set  e  on c.packageID=e.id where  c.center_num='"+center_num+"' ";
		List<ZybOccuindustryDTO> l=this.jqm.getList(sql, ZybOccuindustryDTO.class);
		return l.get(0);
	}

	@Override
	public List<ZybOccuindustryDTO> getLbcriterionManagerComxobox(
			ZybOccuindustryModel model) throws ServiceException {
		String sql =" SELECT  ch.criterionID,ch.criterion_name FROM    zyb_checkcriterion ch   ";
		return this.jqm.getList(sql, ZybOccuindustryDTO.class);
	}

	@Override
	public void saveLbcriterionManager(ZybOccuindustryModel model)
			throws ServiceException {
		ZyboccuhazardfactorsCheckcriterion o = new ZyboccuhazardfactorsCheckcriterion();
		o.setCriterionID(model.getCriterionID());
		o.setDISORDER(model.getDISORDER());
		o.setHazardfactorsID(model.getHazardfactorsID());
		o.setOccuphyexaclassID(model.getOccuphyexaclassID());
		o.setRID(model.getRID());
		this.pm.save(o);
	}

	@Override
	public void updateLbcriterionManager(ZybOccuindustryModel model)
			throws ServiceException {
		ZyboccuhazardfactorsCheckcriterion z = (ZyboccuhazardfactorsCheckcriterion) this.qm.get(ZyboccuhazardfactorsCheckcriterion.class,model.getRID());
		z.setCriterionID(model.getCriterionID());
		z.setDISORDER(model.getDISORDER());
		this.pm.update(z);
		
	}

	@Override
	public void deleteLbcriterionManager(ZybOccuindustryModel model)
			throws ServiceException {
		ZyboccuhazardfactorsCheckcriterion z = (ZyboccuhazardfactorsCheckcriterion) this.qm.get(ZyboccuhazardfactorsCheckcriterion.class,model.getRID());
		this.pm.remove(z);
	}

	@Override
	public PageReturnDTO queryLbcriterionManager(ZybOccuindustryModel model,int pagesize, int pageno)
			throws ServiceException {
		String sql = " SELECT    zo.RID,zo.DISORDER,ch.criterion_name,cl.occuphyexaclass_name,dr.hazard_name  from zyb_occuhazardfactors_checkcriterion  zo,zyb_checkcriterion ch,"
				+ "zyb_occuhazardfactors  dr,zyb_occuphyexaclass cl"
				+ "  WHERE  ch.criterionID = zo.criterionID"
				+ "   	AND  dr.hazardfactorsID = zo.hazardfactorsID "
				+"  AND   zo.occuphyexaclassID = cl.occuphyexaclassID "
					+" AND    zo.hazardfactorsID in ("+model.getHazardfactorsID()+")  ";
					if(model.getOccuphyexaclassID()!=null && !"".equals(model.getOccuphyexaclassID())){
				    sql += "  AND  zo.occuphyexaclassID='"+model.getOccuphyexaclassID()+"'";	
					}
					if(model.getCriterion_name()!=null && !"".equals(model.getCriterion_name())){
					sql+="  and ch.criterion_name  like '%"+model.getCriterion_name().trim()+"%' ";	
					}
					sql +=" order   by    zo.DISORDER desc";
		PageSupport map=this.jqm.getList(sql, pageno,pagesize, ZybOccuindustryDTO.class);
		PageReturnDTO p=new PageReturnDTO();
		p.setPage(pageno);
		p.setRp(pagesize);
		if((map!=null) && (map.getList()!=null))
			p.setTotal(map.getRecTotal());
			p.setRows(map.getList());
		return p;
	}

	@Override
	public ZyboccuhazardfactorsCheckcriterion getLBZybOccuindustryDTO(ZybOccuindustryModel model)
			throws ServiceException {
		return (ZyboccuhazardfactorsCheckcriterion) this.qm.get(ZyboccuhazardfactorsCheckcriterion.class,model.getRID());
	}
}
