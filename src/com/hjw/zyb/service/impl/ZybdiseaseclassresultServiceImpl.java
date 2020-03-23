package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybdiseaseclassDTO;
import com.hjw.zyb.DTO.ZybdiseaseclassresultDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.DTO.ZyboccucontraindicationDTO;
import com.hjw.zyb.domain.Zybdiseaseclassresult;
import com.hjw.zyb.model.ZybdiseaseclassresultModel;
import com.hjw.zyb.service.ZybdiseaseclassresultService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;


public class ZybdiseaseclassresultServiceImpl implements
		ZybdiseaseclassresultService {

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
	public PageReturnDTO queryDiseaseclassresulList(
			ZybdiseaseclassresultModel model, int page, int pageSize)
			throws SQLException, ServiceException {
		String sql = "SELECT  e.diseaseclassresultID,c.diseaseclass_name,a.result_name FROM  zyb_diseaseclass  c  "
					+" RIGHT  join   zyb_diseaseclassresult   e  on  c.diseaseclassID=e.diseaseclassID"
					+" left join   zyb_examinationresult   a on  e.resultID=a.resultID";
		PageSupport map = jqm.getList(sql, page, pageSize,
				ZybdiseaseclassresultDTO.class);
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
	public void deleteDiseaseclassresulList(String ids) throws SQLException,
			ServiceException {
		String hql = "FROM  Zybdiseaseclassresult z   WHERE    z.diseaseclassresultID  in("+ids+")";
		List<Zybdiseaseclassresult>  li = qm.find(hql);
		for (Zybdiseaseclassresult zybdiseaseclassresult : li) {
			pm.remove(zybdiseaseclassresult);
		}
	}

	@Override
	public Zybdiseaseclassresult getDiseaseclassresulList(ZybdiseaseclassresultModel model) throws SQLException,ServiceException {
		String hql = "FROM  Zybdiseaseclassresult z   WHERE    z.diseaseclassresultID='"+model.getDiseaseclassresultID()+"'";
		List<Zybdiseaseclassresult>  li = qm.find(hql);
		return li.get(0);
	}

	@Override
	public void addDiseaseclassresulList(Zybdiseaseclassresult zy)
			throws SQLException, ServiceException {
			pm.save(zy);
	}

	@Override
	public void updateDiseaseclassresulList(Zybdiseaseclassresult zy)
			throws SQLException, ServiceException {
		pm.save(zy);
	}

	@Override
	public List<ZybdiseaseclassDTO> getZybdiseaseclassSelect()
			throws SQLException, ServiceException {
		String sql = "select z.diseaseclassID,z.diseaseclass_name from ZYB_diseaseclass  z";
		List<ZybdiseaseclassDTO>  li = jqm.getList(sql, ZybdiseaseclassDTO.class);
		return li;
	}

	@Override
	public List<ZybexaminationresultDTO> getexaminationresultSelect()
			throws SQLException, ServiceException {
		String sql = "select z.resultID,z.result_name from  zyb_examinationresult  z";
		List<ZybexaminationresultDTO>  li = jqm.getList(sql, ZybexaminationresultDTO.class);
		return li;
	}
}
