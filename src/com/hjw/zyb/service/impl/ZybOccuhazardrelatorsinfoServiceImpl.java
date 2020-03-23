package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuhazardrelatorsinfoDTO;
import com.hjw.zyb.domain.ZybOccuhazardrelatorsinfo;
import com.hjw.zyb.model.ZybOccuhazardrelatorsinfoModel;
import com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOccuhazardrelatorsinfoServiceImpl implements ZybOccuhazardrelatorsinfoService {

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
	
	
	
	/**
	 * 职业危害相关信息新增
	     * <p>Title: addOccuwhxg</p>   
	     * <p>Description: </p>   
	     * @param r
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#addOccuwhxg(com.hjw.zyb.domain.ZybOccuhazardrelatorsinfo)
	 */
	@Override
	public ZybOccuhazardrelatorsinfo addOccuwhxg(ZybOccuhazardrelatorsinfo r)throws ServiceException {
		this.pm.save(r);
		return r;
	}

	
	
	/**
	 * 职业危害相关信息删除
	     * <p>Title: deleteOccuwhxg</p>   
	     * <p>Description: </p>   
	     * @param r
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#deleteOccuwhxg(com.hjw.zyb.domain.ZybOccuhazardrelatorsinfo)
	 */
	@Override
	public void deleteOccuwhxg(ZybOccuhazardrelatorsinfo r) throws ServiceException {
		this.pm.remove(r);

	}

	
	
	/**
	 * 职业危害相关信息更新
	     * <p>Title: updateOccuwhxg</p>   
	     * <p>Description: </p>   
	     * @param r
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#updateOccuwhxg(com.hjw.zyb.domain.ZybOccuhazardrelatorsinfo)
	 */
	@Override
	public ZybOccuhazardrelatorsinfo updateOccuwhxg(ZybOccuhazardrelatorsinfo r)throws ServiceException {
		this.pm.update(r);
		return r;
	}

	
	
	/**
	 * 职业危害相关信息id查询
	     * <p>Title: queryById</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#queryById(java.lang.String)
	 */
	@Override
	public ZybOccuhazardrelatorsinfo queryById(String id)throws ServiceException {
		
		return (ZybOccuhazardrelatorsinfo) this.qm.get(ZybOccuhazardrelatorsinfo.class, id);
	}

	
	
	/**
	 * 职业危害相关信分页查询
	     * <p>Title: queryByOccuwhxg</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#queryByOccuwhxg(com.hjw.zyb.model.ZybOccuhazardrelatorsinfoModel, int, int)
	 */
	@Override
	public PageReturnDTO queryByOccuwhxg(ZybOccuhazardrelatorsinfoModel model, int pagesize,int pageno) throws ServiceException {
		String sql=" select r.id,h.hazardclass_name,f.hazard_name,p.occuphyexaclass_name,r.diseaseandtaboo,r.checkcontent,"
			+" r.checkcriterion,r.followdisease,r.examperiod,r.remark from zyb_occuhazardrelatorsinfo r "
			+" left join zyb_occuhazardfactors f on r.hazardfactorsID=f.hazardfactorsID "
			+" left join zyb_occuphyexaclass p on r.occuphyexaclassID=p.occuphyexaclassID "
			+" left join zyb_occuhazardclass h on r.hazardclassID=h.hazardclassID where 1=1" ;
		/*	if(model.getWhlb()!=null && !"".equals(model.getWhlb())){
				sql+=" and r.hazardclassID='"+model.getWhlb()+"'";
			} else {*/
				if(model.getOccuphyexaclassID()!=null && !"".equals(model.getOccuphyexaclassID())){
					
					sql+=" and r.occuphyexaclassID='"+model.getOccuphyexaclassID()+"'";
				}
				if(model.getHazardfactorsID()!=null && !"".equals(model.getHazardfactorsID())){
					
					sql+=" and r.hazardfactorsID in("+model.getHazardfactorsID()+") ";
				}
			/*}*/
			if(model.getHazard_name()!=null && !"".equals(model.getHazard_name())){
				sql+=" and f.hazard_name like '%"+model.getHazard_name().trim()+"%' ";
			}
		PageSupport p=this.jqm.getList(sql, pageno, pagesize, ZybOccuhazardrelatorsinfoDTO.class);
		PageReturnDTO d=new PageReturnDTO();
		d.setPage(pageno);
		d.setRp(pagesize);
		if((p!=null) && (p.getList() !=null)){
			d.setTotal(p.getRecTotal());
			d.setRows(p.getList());
		}
		return d;
	
	}

	
	
	/**
	 * 职业危害类别树加载
	     * <p>Title: getOccuwhlbtree</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#getOccuwhlbtree()
	 */
	@Override
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuwhlbtree()throws ServiceException {
		String sql=" select h.hazardclassID,h.hazardclass_name from zyb_occuhazardclass h ";
		List<ZybOccuhazardrelatorsinfoDTO> list=this.jqm.getList(sql, ZybOccuhazardrelatorsinfoDTO.class);
		return list;
	}

	
	
	/**
	 * 职业危害类别 
	     * <p>Title: getOccuwhlbList</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#getOccuwhlbList()
	 */
	@Override
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuwhlbList() throws ServiceException {
		String sql="  select c.hazardclassID,c.hazardclass_name from  zyb_occuhazardclass c ";
		List<ZybOccuhazardrelatorsinfoDTO> list=this.jqm.getList(sql, ZybOccuhazardrelatorsinfoDTO.class);
		return list;
	}

	
	/**
	 * 危害因素
	     * <p>Title: getOccuwhys</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#getOccuwhys()
	 */
	@Override
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuwhys() throws ServiceException {
		String sql="  select f.hazardfactorsID,f.hazard_name,f.hazard_code,f.hazard_year from  zyb_occuhazardfactors f ";
		List<ZybOccuhazardrelatorsinfoDTO> list=this.jqm.getList(sql, ZybOccuhazardrelatorsinfoDTO.class);
		return list;
	}

	
	
	/**
	 *体检类别
	     * <p>Title: getOccuphyclass</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#getOccuphyclass()
	 */
	@Override
	public List<ZybOccuhazardrelatorsinfoDTO> getOccuphyclass() throws ServiceException {
		String sql=" select p.occuphyexaclassID,p.occuphyexaclass_name from  zyb_occuphyexaclass p ";
		List<ZybOccuhazardrelatorsinfoDTO> list=this.jqm.getList(sql, ZybOccuhazardrelatorsinfoDTO.class);
		return list;
	}

	
	
	/**
	 * id 查询
	     * <p>Title: getByid</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#getByid(java.lang.String)
	 */
	@Override
	public ZybOccuhazardrelatorsinfoDTO getByid(String id)throws ServiceException {
		String sql=" select r.id,h.hazardclassID,f.hazardfactorsID,p.occuphyexaclassID,r.diseaseandtaboo,r.checkcontent, "
			 +" r.checkcriterion,r.followdisease,r.examperiod,r.remark "
			 +" from zyb_occuhazardrelatorsinfo r  "
			 +" left join zyb_occuhazardfactors f on r.hazardfactorsID=f.hazardfactorsID "
			 +" left join zyb_occuphyexaclass p on r.occuphyexaclassID=p.occuphyexaclassID "
			 +" left join zyb_occuhazardclass h on r.hazardclassID=h.hazardclassID "
			 +" where r.id='"+id+"'";
		List<ZybOccuhazardrelatorsinfoDTO> l=this.jqm.getList(sql, ZybOccuhazardrelatorsinfoDTO.class);
		return l.get(0);
	}

	
	
	/**
	 * 批量删除
	     * <p>Title: deletes</p>   
	     * <p>Description: </p>   
	     * @param ids
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService#deletes(java.lang.String)
	 */
	@Override
	public void deletes(String ids) throws ServiceException {
		String fql=" From ZybOccuhazardrelatorsinfo  where id in("+ids+") ";
		List<ZybOccuhazardrelatorsinfo> l=qm.find(fql);
		for (ZybOccuhazardrelatorsinfo z : l) {
			pm.remove(z);
		}
	}
 
}
