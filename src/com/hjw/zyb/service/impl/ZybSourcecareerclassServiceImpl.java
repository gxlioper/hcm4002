package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybSourcecareerclassDTO;
import com.hjw.zyb.domain.ZybSourcecareerclass;
import com.hjw.zyb.service.ZybSourcecareerclassService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybSourcecareerclassServiceImpl implements ZybSourcecareerclassService {

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
	 *照射源分类保存
	     * <p>Title: addSourcecareerclass</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#addSourcecareerclass(com.hjw.zyb.domain.ZybSourcecareerclass)
	 */
	@Override
	public ZybSourcecareerclass addSourcecareerclass(ZybSourcecareerclass c) throws ServiceException {
		this.pm.save(c);
		return c;
	}

	
	
	/**
	 * 照射源分类删除
	     * <p>Title: deleteSourcecareerclass</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#deleteSourcecareerclass(com.hjw.zyb.domain.ZybSourcecareerclass)
	 */
	@Override
	public void deleteSourcecareerclass(ZybSourcecareerclass c) throws ServiceException {
		this.pm.remove(c);

	}

	
	/**
	 * 照射源分类更新
	     * <p>Title: updateSourcecareerclass</p>   
	     * <p>Description: </p>   
	     * @param c
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#updateSourcecareerclass(com.hjw.zyb.domain.ZybSourcecareerclass)
	 */
	@Override
	public ZybSourcecareerclass updateSourcecareerclass(ZybSourcecareerclass c) throws ServiceException {
		this.pm.update(c);
		return c;
	}

	
	
	/**
	 * 照射源分类id查询
	     * <p>Title: queryByID</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#queryByID(java.lang.String)
	 */
	@Override
	public ZybSourcecareerclassDTO queryByID(String id) throws ServiceException {
		String sql=" select c.sc_classid ,c.sourceid, c.sc_classcode,c.sc_classname,s.source_name "
			+" from zyb_source_career_class c left join zyb_source s on c.sourceid=s.source_id "
			+"where c.sc_classid='"+id+"'";
		List<ZybSourcecareerclassDTO> l=this.jqm.getList(sql, ZybSourcecareerclassDTO.class);
		return l.get(0);
	}

	
	/**
	 * 照射源分类分页查询
	     * <p>Title: queryBySourcecareerclasspage</p>   
	     * <p>Description: </p>   
	     * @param sc_classcode
	     * @param sc_classname
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#queryBySourcecareerclasspage(java.lang.String, java.lang.String, int, int)
	 */
	@Override
	public PageReturnDTO queryBySourcecareerclasspage(String sc_classcode, String sc_classname,
			int pagesize, int pageno) throws ServiceException {
		String sql=" select c.sc_classid ,c.sc_classcode,c.sc_classname,s.source_name "
			+" from zyb_source_career_class c left join zyb_source s on c.sourceid=s.source_id "
			+" where sc_classcode like '%"+sc_classcode+"%' and sc_classname like '%"+sc_classname+"%' order by c.sc_classcode";
		PageSupport s=this.jqm.getList(sql, pageno, pagesize, ZybSourcecareerclassDTO.class);
		PageReturnDTO p=new PageReturnDTO();
		p.setPage(pageno);
		p.setRp(pagesize);
		if((s!=null) &&(s.getList()!=null)){
			p.setTotal(s.getRecTotal());
			p.setRows(s.getList());
		}
		return p;
	}

	
	/**
	 * 照射源分类编码查询
	     * <p>Title: queryBycode</p>   
	     * <p>Description: </p>   
	     * @param sc_classcode
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#queryBycode(java.lang.String)
	 */
	@Override
	public ZybSourcecareerclass queryBycode(String sc_classcode,String sc_classid)throws ServiceException {
		String fql=" from ZybSourcecareerclass where sc_classcode='"+sc_classcode+"' and sc_classid != '" + sc_classid + "'" ;
		List<ZybSourcecareerclass> l=this.qm.find(fql);
		if(l.size()!=0){
			return l.get(0);
		}
		return null;
	}

	
	
	/**
	 * 照射源获取全部
	     * <p>Title: getS_name</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#getS_name()
	 */
	@Override
	public List<ZybSourcecareerclassDTO> getS_name() throws ServiceException {
		String sql=" select s.source_id,s.source_name from zyb_source s ";
		List<ZybSourcecareerclassDTO> l=this.jqm.getList(sql, ZybSourcecareerclassDTO.class);
		return l;
	}

	
	
	/**
	 * 照射源分类id查询
	     * <p>Title: queryByid</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybSourcecareerclassService#queryByid(java.lang.String)
	 */
	@Override
	public ZybSourcecareerclass queryByid(String id) throws ServiceException {
		
		return (ZybSourcecareerclass) this.qm.get(ZybSourcecareerclass.class, id);
	}

}
