package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuhazardClassDTO;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybOccuphyexaClassDTO;
import com.hjw.zyb.domain.ZybOccuhazardfactors;
import com.hjw.zyb.model.ZybOccuhazardClassModel;
import com.hjw.zyb.model.ZybOccuhazardfactorsModel;
import com.hjw.zyb.service.ZybOccuhazardfactorsService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
import org.apache.commons.lang3.StringUtils;

public class ZybOccuhazardfactorsServiceImpl implements ZybOccuhazardfactorsService {

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
	
	public List<ZybOccuhazardfactorsDTO> getOccuhazardfactorsList(ZybOccuhazardfactorsModel  model) throws ServiceException {
		String sql = "select * from  dbo.zyb_occuhazardclass s   where   1=1 ";
				if( model.getHazardclass_name()!=null && !"".equals(model.getHazardclass_name())){
				sql +="   and  hazardclass_name  like '%"+model.getHazardclass_name()+"%'";
				}
				if(model.getHazard_name() != null && !"".equals(model.getHazard_name())){
					sql += " and hazard_name like '%"+model.getHazard_name()+"%'";
				}
		List<ZybOccuhazardfactorsDTO> li = jqm.getList(sql,ZybOccuhazardClassDTO.class);
		return li;
	}

	@Override
	public PageReturnDTO  getOccuHazardFactorsList(ZybOccuhazardfactorsModel model,int page,int pageSize) throws ServiceException {
		String sql = "select * from zyb_occuhazardfactors  where   1=1 ";
				if( model.getHazardclassID()!=null && !"".equals(model.getHazardclassID()) && !"qb".equals(model.getHazardclassID())){
					sql +="  and   hazardclassID='"+model.getHazardclassID()+"'";
				}
				if(!StringUtils.isEmpty(model.getHazard_name())){
					sql +="  and     hazard_name like'%"+model.getHazard_name()+"%'";
				}
				sql+=" order by 'order'";
			//List li = jqm.getList(sql,ZYB_OccuhazardfactorsDTO.class);
			PageSupport  map= jqm.getList(sql,page, pageSize,ZybOccuhazardfactorsDTO.class);
	 		PageReturnDTO webrole = new PageReturnDTO();
	 		webrole.setPage(page);
	 		webrole.setRp(pageSize);
			if ((map != null) && (map.getList() != null)) {
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
			}
		return webrole;
	}

	@Override
	public void deledtOccuHazardFactors(String ids) throws ServiceException {
		String sql =" From  ZybOccuhazardfactors  where  hazardfactorsID  in("+ids+")";
		List<ZybOccuhazardfactors>  li = qm.find(sql);
		for (ZybOccuhazardfactors d: li) {
			pm.remove(d);
		}
	}

	@Override
	public ZybOccuhazardfactors getOccuHazardFactors(String id) throws ServiceException {
		String sql = "From  ZybOccuhazardfactors  where   hazardfactorsID="+id;
		List<ZybOccuhazardfactors>  li = qm.find(sql);
		return li.get(0);
	}

	@Override
	public int getOccuHazardFactorsCode(ZybOccuhazardfactorsModel model) throws ServiceException {
		String sql = "select s.hazard_code  from zyb_occuhazardfactors  s  where  s.hazard_code='"+model.getHazard_code().trim()+"'";
		List<ZybOccuhazardfactorsDTO> li = jqm.getList(sql, ZybOccuhazardfactorsDTO.class);
		if(li.size()>0){
			return 1;
		}else{
			return 0;
		}
	}

	@Override
	public void addOccuHazardFactors(ZybOccuhazardfactors to) throws ServiceException {
		this.pm.save(to);
	}

	@Override
	public void updateOccuHazardFactors(ZybOccuhazardfactorsModel model) throws ServiceException {
		ZybOccuhazardfactors s = getOccuHazardFactors(model.getHazardfactorsID());
		s.setHazardclassID(model.getHazardclassID());
		s.setHazard_code(model.getHazard_code());
		s.setHazard_name(model.getHazard_name());
		s.setHazard_year(model.getHazard_year());
		s.setHazard_desc(model.getHazard_desc());
		s.setOrder(model.getOrder());
		s.setDeffect(model.getDeffect());
		s.setRemark(model.getRemark());
		s.setPycode(model.getPycode());
		this.pm.update(s);
	}

	@Override
	public int getOrderMax() throws ServiceException {
		String  sql = "select max([order]) as order2 from zyb_occuhazardfactors;";
		List<ZybOccuhazardfactorsDTO>  dto = this.jqm.getList(sql,ZybOccuhazardfactorsDTO.class);
		return dto.get(0).getOrder2();
	}
}
