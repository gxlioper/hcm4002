package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybEconomicclassDTO;
import com.hjw.zyb.domain.ZybEconomicClass;
import com.hjw.zyb.model.ZybEconomicclassModel;
import com.hjw.zyb.service.ZybEconomicclassService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybEconomicclassServiceImpl implements ZybEconomicclassService {

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
	 * 经济类型插入
	     * <p>Title: addEconomic</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#addEconomic(com.hjw.zyb.domain.ZybEconomicClass)
	 */
	@Override
	public void addEconomic(ZybEconomicClass c)throws ServiceException {
		pm.save(c);
	}

	/**
	 * 经济类型删除
	     * <p>Title: deleteEconomic</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#deleteEconomic(com.hjw.zyb.domain.ZybEconomicClass)
	 */
	@Override
	public void deleteEconomic(ZybEconomicClass c) throws ServiceException {
		pm.remove(c);

	}

	/**
	 * 经济类型更新
	     * <p>Title: updateEconomic</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#updateEconomic(com.hjw.zyb.domain.ZybEconomicClass)
	 */
	@Override
	public void updateEconomic(ZybEconomicClass c)throws ServiceException {
		pm.update(c);
	}

	
	/**
	 * 经济类型查询全部
	     * <p>Title: queryByAll</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#queryByAll()
	 */
	@Override
	public List<ZybEconomicclassDTO> queryByAll() throws ServiceException {
		String sql=" select c.economicID,c.economicclass_name "
				+" from zyb_economicclass c  where c.parentID = '' or c.parentID is null ";
		List<ZybEconomicclassDTO> list=jqm.getList(sql, ZybEconomicclassDTO.class);
		return list;
	}

	/**
	 * id查询返回一个对象
	     * <p>Title: queryById</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#queryById(java.lang.String)
	 */
	@Override
	public ZybEconomicClass queryById(String id) throws ServiceException {
		
		return (ZybEconomicClass) qm.get(ZybEconomicClass.class, id);
	}

	
	/**
	 * 经济类型编码查询
	     * <p>Title: queryByNum</p>   
	     * <p>Description: </p>   
	     * @param code
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#queryByNum(java.lang.String)
	 */
	@Override
	public ZybEconomicClass queryByNum(String code)throws ServiceException {
		String sql = "from ZybEconomicClass where economicclass_code = '"+code.trim()+"'";
		List<ZybEconomicClass> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	
	/**
	 * 经济类型分页查询
	     * <p>Title: queryPageEconomicclass</p>   
	     * <p>Description: </p>   
	     * @param model
	     * @param pageno
	     * @param pagesize
	     * @return   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#queryPageEconomicclass(com.hjw.zyb.model.ZybEconomicclassModel, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageReturnDTO queryPageEconomicclass(ZybEconomicclassModel model, int pageno,int pagesize) {
		String sql = "select c.economicID,c.economicclass_code,c.economicclass_name,c.parentID from zyb_economicclass c  "
				+ " where  economicclass_name like '%"+model.getEconomicclass_name()+"%' and economicclass_code like '%"+model.getEconomicclass_code()+"%' "
				+" and c.parentID = '' or c.parentID is null ";
				sql+=" order by parentID asc " ;
		PageSupport map = this.jqm.getList(sql, pagesize,pageno, ZybEconomicclassDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		List<ZybEconomicclassDTO> li = map.getList();
		for(ZybEconomicclassDTO economicclassDTO :li){
			String sql1= "  select c.economicID,c.economicclass_code,c.economicclass_name from zyb_economicclass c where c.parentID = '"+economicclassDTO.getEconomicID()+"'";
			sql1+=" order by economicclass_code asc " ;
			List<ZybEconomicclassDTO> list1 = this.jqm.getList(sql1, ZybEconomicclassDTO.class);
			economicclassDTO.setChildren(list1);
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
	 * 经济类型批量删除
	     * <p>Title: deletes</p>   
	     * <p>Description: </p>   
	     * @param ids
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#deletes(java.lang.String)
	 */
	@Override
	public void deletes(String ids) throws ServiceException {
		String hql="from ZybEconomicClass where economicID in("+ids+")";
		List<ZybEconomicClass> list=this.qm.find(hql);
		for (ZybEconomicClass zybEconomicClass : list) {
			pm.remove(zybEconomicClass);
		}
	}

	/**
	 * id查询返回多个对象
	     * <p>Title: getByidList</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#getByidList(java.lang.String)
	 */
	@Override
	public List<ZybEconomicClass> getByidList(String id)throws ServiceException {
		String sql=" select * from zyb_economicclass where parentID='"+id+"'";
		List<ZybEconomicClass> list=this.jqm.getList(sql, ZybEconomicClass.class);
		return list;
	}

	/**
	 * 获取父级单位
	     * <p>Title: getfujidanwei</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybEconomicclassService#getfujidanwei()
	 */
	@Override
	public ZybEconomicClass getfujidanwei(String id) throws ServiceException {
		String sql=" select e.economicclass_name from zyb_economicclass e where economicID='"+id+"' ";
		List<ZybEconomicClass> l=this.jqm.getList(sql,ZybEconomicClass.class);
		return l.get(0);
	}

}
