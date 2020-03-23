package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybDustitemoptionDTO;
import com.hjw.zyb.domain.ZybDustitemoption;
import com.hjw.zyb.model.ZybdustitemoptionModel;
import com.hjw.zyb.service.ZybDustitemoptionService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybDustitemoptionServiceImpl implements ZybDustitemoptionService {

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
	 * 粉尘胸片参数id 查询
	     * <p>Title: getone</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#getone(java.lang.String)
	 */
	@Override
	public ZybDustitemoptionDTO getone(String id,UserDTO user) throws ServiceException {
		String sql=" select o.optionID,d.dustitem_name,o.option_value ,o.showorder from zyb_dustitemoption o "
			+" left join zyb_dustitem d on o.dustID=d.dustitem_id "
			+" where optionID='"+id+"' AND center_num = '"+user.getCenter_num()+"'   " 
			+" order by o.showorder "; 
		List<ZybDustitemoptionDTO> list=this.jqm.getList(sql, ZybDustitemoptionDTO.class);
		return list.get(0);
	}

	
	/**
	 * 粉尘胸片参数添加
	     * <p>Title: adddustitemoption</p>   
	     * <p>Description: </p>   
	     * @param o
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#adddustitemoption(com.hjw.zyb.domain.ZybDustitemoption)
	 */
	@Override
	public ZybDustitemoption adddustitemoption(ZybDustitemoption o)throws ServiceException {
		this.pm.save(o);
		return o;
	}

	
	/**
	 * 粉尘胸片参数删除
	     * <p>Title: deletedustitemoption</p>   
	     * <p>Description: </p>   
	     * @param o
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#deletedustitemoption(com.hjw.zyb.domain.ZybDustitemoption)
	 */
	@Override
	public void deletedustitemoption(ZybDustitemoption o)throws ServiceException {
		this.pm.remove(o);
		
	}

	/**
	 * 粉尘胸片参数修改
	     * <p>Title: updatedustitemoption</p>   
	     * <p>Description: </p>   
	     * @param o
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#updatedustitemoption(com.hjw.zyb.domain.ZybDustitemoption)
	 */
	@Override
	public ZybDustitemoption updatedustitemoption(ZybDustitemoption o)throws ServiceException {
		this.pm.update(o);
		return o;
	}

	/**
	 * 粉尘胸片参数主键查询
	     * <p>Title: queryByID</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#queryByID(java.lang.String)
	 */
	@Override
	public ZybDustitemoption queryByID(String id) throws ServiceException {
		
		return (ZybDustitemoption) this.qm.get(ZybDustitemoption.class, id);
	}
	

	
	/**
	 * 粉尘胸片参数分页查询
	     * <p>Title: querydustitemoptionList</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#querydustitemoptionList(com.hjw.zyb.model.ZybdustitemoptionModel, int, int)
	 */
	@Override
	public PageReturnDTO querydustitemoptionList(ZybdustitemoptionModel model,int pagesize, int pageno,UserDTO user) throws ServiceException {
		String sql=" select o.optionID,d.dustitem_name,o.option_value ,o.showorder from zyb_dustitemoption o "
				+" left join zyb_dustitem d on o.dustID=d.dustitem_id where  1=1 AND o.center_num = '"+user.getCenter_num()+"' ";
				if(!"".equals(model.getDustID()) && model.getDustID()!=null){
					sql+="and dustID='"+model.getDustID()+"' ";
				}
				sql+=" order by o.showorder ";
		PageSupport p=this.jqm.getList(sql, pageno, pagesize, ZybDustitemoptionDTO.class);
		PageReturnDTO d=new PageReturnDTO();
		d.setPage(pageno);
		d.setRp(pagesize);
		if((p!=null)&&(p.getList()!=null)){
			d.setTotal(p.getRecTotal());
			d.setRows(p.getList());
		}
		return d;
	}

	
	/**
	 * 粉尘胸片查询全部
	     * <p>Title: getdustitemList</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#getdustitemList()
	 */
	@Override
	public List<ZybDustitemoptionDTO> getdustitemList() throws ServiceException {
		String sql=" select d.dustitem_id,d.dustitem_name from zyb_dustitem d ";
		List<ZybDustitemoptionDTO> list=this.jqm.getList(sql, ZybDustitemoptionDTO.class);
		return list;
	}

	
	/**
	 * 粉尘胸片批量删除
	     * <p>Title: deletes</p>   
	     * <p>Description: </p>   
	     * @param ids
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybDustitemoptionService#deletes(java.lang.String)
	 */
	@Override
	public void deletes(String ids) throws ServiceException {
		String hql=" from ZybDustitemoption where optionID in("+ids+")";
		List<ZybDustitemoption> list=this.qm.find(hql);
		for (ZybDustitemoption zybDustitemoption : list) {
			this.pm.remove(zybDustitemoption);
		}
	}

}
