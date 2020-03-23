package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.domain.ZybExaminationresult;
import com.hjw.zyb.service.ZybexaminationresultService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybexaminationresultServiceImpl implements ZybexaminationresultService {

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
	 * 体检结论分页查询
	     * <p>Title: queryZybexaminationresultList</p>   
	     * <p>Description: </p>   
	     * @param result_name
	     * @param pageno
	     * @param pageSize
	     * @return
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#queryZybexaminationresultList(java.lang.String, int, int)
	 */
	@Override
	public PageReturnDTO queryZybexaminationresultList(String result_name,int pageno, int pageSize) throws SQLException, ServiceException {
		String sql=" select r.resultID,r.result_name,convert(varchar(50),r.seq_code) as seq_code from zyb_examinationresult r  where result_name like '%"+result_name+"%' ";
		PageSupport p=this.jqm.getList(sql, pageSize, pageno, ZybexaminationresultDTO.class);
		PageReturnDTO d=new PageReturnDTO();
		d.setPage(pageno);
		d.setRp(pageSize);
		if((p!=null)&&(p.getList()!=null)){
			d.setTotal(p.getRecTotal());
			d.setRows(p.getList());
		}
		return d;
	}

	
	/**
	 * 体检结论插入
	     * <p>Title: addZybexamresul</p>   
	     * <p>Description: </p>   
	     * @param r
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#addZybexamresul(com.hjw.zyb.domain.ZybExaminationresult)
	 */
	@Override
	public void addZybexamresul(ZybExaminationresult r) throws SQLException,ServiceException {
		this.pm.save(r);
	}

	
	/**
	 * 体检结论批量删除
	     * <p>Title: deleteZybexamresul</p>   
	     * <p>Description: </p>   
	     * @param ids
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#deleteZybexamresul(java.lang.String)
	 */
	@Override
	public void deleteZybexamresul(String ids) throws SQLException,ServiceException {
		String hql=" from ZybExaminationresult where resultID in("+ids+") ";
		List<ZybExaminationresult> list=this.qm.find(hql);
		for (ZybExaminationresult zybExaminationresult : list) {
			pm.remove(zybExaminationresult);
		}
	}

	
	/**
	 * 体检结论更新
	     * <p>Title: updateZybexamresul</p>   
	     * <p>Description: </p>   
	     * @param r
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#updateZybexamresul(com.hjw.zyb.domain.ZybExaminationresult)
	 */
	@Override
	public void updateZybexamresul(ZybExaminationresult r) throws SQLException,ServiceException {
		this.pm.update(r);

	}

	
	/**
	 * 体检结论id查询
	     * <p>Title: queryByID</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#queryByID(java.lang.String)
	 */
	@Override
	public ZybExaminationresult queryByID(String id) throws SQLException,ServiceException {
		return (ZybExaminationresult) this.qm.get(ZybExaminationresult.class, id);
	}

	
	/**
	 * 体检结论查询全部
	     * <p>Title: queryByAll</p>   
	     * <p>Description: </p>   
	     * @return
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#queryByAll()
	 */
	@Override
	public List<ZybExaminationresult> queryByAll() throws SQLException,ServiceException {
		String sql=" select r.resultID,r.resultID from  zyb_examinationresult r ";
		List<ZybExaminationresult> list=this.jqm.getList(sql, ZybexaminationresultDTO.class);
		return list;
	}

	
	/**
	 * 体检结论删除
	     * <p>Title: deleteZybexamresulone</p>   
	     * <p>Description: </p>   
	     * @param r
	     * @throws SQLException
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybexaminationresultService#deleteZybexamresulone(com.hjw.zyb.domain.ZybExaminationresult)
	 */
	@Override
	public void deleteZybexamresulone(ZybExaminationresult r) throws SQLException,ServiceException {
		this.pm.remove(r);
		
	}

}
