package com.hjw.wst.service.impl;


import com.hjw.wst.DTO.FinalRejectionDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.FinalRejection;
import com.hjw.wst.service.FinalRejectionService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class FinalRejectionServiceImpl implements  FinalRejectionService{
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

	@Override
	public FinalRejection addFinalRejection(FinalRejection reject) throws ServiceException {
		this.pm.save(reject);
		return reject;
	}

	@Override
	public void deleteFinalRejection(FinalRejection reject) throws ServiceException {
		this.pm.remove(reject);
	}

	@Override
	public FinalRejection updateFinalRejection(FinalRejection reject) throws ServiceException {
		this.pm.update(reject);
		return reject;
	}

@Override
public PageReturnDTO finalRejectionList(String reject_context, int pagesize, int pageno) {
	String sql = "select e.id,e.reject_context,e.is_active,u.chi_name as creater,e.create_time,u1.chi_name as updater,e.update_time  from exam_summary_reject_lib e left join user_usr u on e.creater=u.id left join user_usr u1 on e.updater=u1.id  where 1=1 ";
	if(!"".equals(reject_context)){
		sql += " and e.reject_context like '%"+reject_context+"%' ";
	}
	sql += " and e.is_active='Y' ";
	PageSupport map = this.jqm.getList(sql, pageno, pagesize, FinalRejectionDTO.class);
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
public FinalRejection serchRejectionById(long id) throws ServiceException {
	return (FinalRejection)this.qm.get(FinalRejection.class, id);
}
	
}
