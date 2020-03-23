package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.fr.report.core.A.c;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOccuhazardClassDTO;
import com.hjw.zyb.DTO.ZybOccuphyexaClassDTO;
import com.hjw.zyb.domain.ZybOccuhazardClass;
import com.hjw.zyb.model.ZybOccuhazardClassModel;
import com.hjw.zyb.service.ZybOccuhazardClassService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOccuhazardClassServiceImpl implements ZybOccuhazardClassService {

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
	public PageReturnDTO queryOccuhazardClassList(ZybOccuhazardClassModel model,int page, int pageSize)
			throws SQLException, ServiceException {
		
		String sql = "select * from   zyb_occuhazardclass  where   1=1  ";
		if (model.getHazardclass_name() != null && !"".equals(model.getHazardclass_name())) {
			sql += "   and  hazardclass_name  like '%" + model.getHazardclass_name().trim() + "%'";
		}
		if(model.getHazardclass_code()!=null && !"".equals(model.getHazardclass_code())){
			sql += "   and  hazardclass_code  like '%" + model.getHazardclass_code().trim() + "%'";
		}
		sql += " order  by  'order'";
		
		PageSupport map = jqm.getList(sql, page, pageSize, ZybOccuhazardClassDTO.class);
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
	public void deleteOccuhazardClass(String ids) throws SQLException, ServiceException {
		String sql = "FROM  ZybOccuhazardClass  s  where   s.hazardclassID  in("+ids+")";
		List<ZybOccuhazardClass> occ = qm.find(sql);
		if(occ.size()>0){
			for (ZybOccuhazardClass c : occ) {
				pm.remove(c);
			}
		}
		
	}

	@Override
	public void saveOccuhazardClass(ZybOccuhazardClass occClass) throws SQLException, ServiceException {
		pm.save(occClass);
	}

	@Override
	public void updateOccuhazardClass(ZybOccuhazardClassModel occClass) throws SQLException, ServiceException {
		ZybOccuhazardClass occ = getZYB_OccuhazardClass(occClass.getHazardclassID());
		occ.setHazardclass_code(occClass.getHazardclass_code());
		occ.setHazardclass_name(occClass.getHazardclass_name());
		occ.setRemark(occClass.getRemark());
		occ.setOrder(occClass.getOrder());
		pm.update(occ);
	}

	@Override
	public ZybOccuhazardClass getZYB_OccuhazardClass(String id) throws SQLException, ServiceException {
		String sql = "FROM  ZybOccuhazardClass  s  where   s.hazardclassID='"+id+"'";
		List<ZybOccuhazardClass> occ = qm.find(sql);
		return occ.get(0);
	}

	@Override
	public int getVerificationHazardclassCode(String Hazardclass_Code) throws SQLException, ServiceException {
		int fla = 0;
		if( Hazardclass_Code!=null && !"".equals( Hazardclass_Code )){
			String sql = " FROM  ZybOccuhazardClass  s  where  s.hazardclass_code='"+Hazardclass_Code.trim()+"'";
			List<ZybOccuhazardClass> c = qm.find(sql);
			
			if( c.size()>0 ){
				fla = 1;
			}
		}
		return fla;
	}

}
