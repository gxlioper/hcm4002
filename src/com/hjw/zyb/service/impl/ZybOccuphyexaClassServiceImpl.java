package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuphyexaClassDTO;
import com.hjw.zyb.domain.ZybOccuphyexaClass;
import com.hjw.zyb.model.ZybOccuphyexaClassModel;
import com.hjw.zyb.service.ZybOccuphyexaClassService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOccuphyexaClassServiceImpl implements ZybOccuphyexaClassService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
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

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	
	@Override
	public PageReturnDTO queryOccuphyexaClass(ZybOccuphyexaClassModel model, int Page, int PageSize)
			throws ServiceException {
		
			String sql="select * from   zyb_occuphyexaclass  where   1=1  ";
						if(model.getOccuphyexaclass_name() !=null && !"".equals(model.getOccuphyexaclass_name())){
					sql+="   and  occuphyexaclass_name  like '%"+model.getOccuphyexaclass_name().trim()+"%'";
						}
					sql+=" order  by  'order'";
			PageSupport  map= jqm.getList(sql,Page, PageSize,ZybOccuphyexaClassDTO.class);
	 		PageReturnDTO webrole = new PageReturnDTO();
	 		webrole.setPage(Page);
	 		webrole.setRp(PageSize);
			if ((map != null) && (map.getList() != null)) {
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
			}
		return webrole;
		
	}

	@Override
	public void saveOccuphyexaClass(ZybOccuphyexaClass	occuphyexaClass) throws ServiceException {
		pm.save(occuphyexaClass);
	}

	@Override
	public void updateOccuphyexaClass(ZybOccuphyexaClass	occ) throws ServiceException {
		String sql = "FROM  ZybOccuphyexaClass  s   where	s.occuphyexaclassID='"+occ.getOccuphyexaclassID()+"'  ";
		
		List<ZybOccuphyexaClass>  dto = qm.find(sql);
			dto.get(0).setOccuphyexaclass_name(occ.getOccuphyexaclass_name());
			dto.get(0).setOccuphyexaclassID(occ.getOccuphyexaclassID());
			dto.get(0).setOrder(occ.getOrder());
			dto.get(0).setRemark(occ.getRemark());
			pm.update(dto.get(0));
			
	}

	@Override
	public void deleteOccuphyexaClass(String	occ) throws ServiceException {
		// s  where   s.occuphyexaclassID   in('"+occ+"')
		String sql = "FROM  ZybOccuphyexaClass  s   where	s.occuphyexaclassID   in("+occ+")";
		List<ZybOccuphyexaClass>  occuphyexaClass = qm.find(sql);
		
		if(occuphyexaClass.size()>0){
			for (ZybOccuphyexaClass occuph : occuphyexaClass) {
				pm.remove(occuph);
			}
		}
	}
	@SuppressWarnings("unchecked")
	public ZybOccuphyexaClassDTO getOccuphyexaClass(String idOrName) throws ServiceException {
		String sql="select * from  zyb_occuphyexaclass    where    occuphyexaclassID='"+idOrName+"' or occuphyexaclass_name='"+idOrName+"'";
		List<ZybOccuphyexaClassDTO>  dto = jqm.getList(sql,ZybOccuphyexaClassDTO.class);
		
		if (dto.size() > 0) {
			ZybOccuphyexaClassDTO classDTO = dto.get(0);
			return classDTO;
		}else{
			return null;
		}
		
	}
	
}
