package com.hjw.zyb.service.impl;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybOcctemplateDTO;
import com.hjw.zyb.domain.ZybOcctemplate;
import com.hjw.zyb.service.ZybOcctemplateService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybOcctemplateServiceImpl implements ZybOcctemplateService {

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
	public PageReturnDTO queryByOccuphyexaclass(String exam_type_name, String occuphyexaclass_name, int pagesize,
			int pageno) {
		String sql="select ot.id, dd.data_name as exam_type_name, zo.occuphyexaclass_name, ot.template,ot.remark from occtemplate ot join data_dictionary dd on ot.exam_type = dd.id "
				+ " left join zyb_occuphyexaclass zo on ot.occuphyexaclassid = zo.occuphyexaclassID "
				+ "where dd.data_name like '%" + exam_type_name + "%' and zo.occuphyexaclass_name like '%" + occuphyexaclass_name + "%' order by ot.exam_type, zo.[order]";
		PageSupport map=this.jqm.getList(sql, pageno, pagesize, ZybOcctemplateDTO.class);
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
	public ZybOcctemplate updateOcctemplate(ZybOcctemplate c) {
		this.pm.update(c);
		return c;
	}

	@Override
	public ZybOcctemplate addOcctemplate(ZybOcctemplate c) throws ServiceException {
		this.pm.save(c);
		return c;
	}

	@Override
	public void deleteZybOcctemplate(ZybOcctemplate c) throws ServiceException {
		this.pm.remove(c);
	}

	@Override
	public ZybOcctemplate queryByid(String id) throws ServiceException {
		String sql=" select * from occtemplate where id='"+id+"'";
		List<ZybOcctemplate> l=this.jqm.getList(sql, ZybOcctemplate.class);
		return l.get(0);
	}

	@Override
	public ZybOcctemplateDTO queryByID(String id) {
		String sql="select ot.id, dd.data_name as exam_type_name, dd.id as exam_type, zo.occuphyexaclass_name, zo.occuphyexaclassid, ot.template,ot.remark from occtemplate ot join data_dictionary dd on ot.exam_type = dd.id "
				+ "join zyb_occuphyexaclass zo on ot.occuphyexaclassid = zo.occuphyexaclassID "
				+ "where ot.id = '" + id + "'";
		List<ZybOcctemplateDTO> l=this.jqm.getList(sql, ZybOcctemplateDTO.class);
		return l.get(0);
	}

	@Override
	public ZybOcctemplate queryByExam_typeAndOccuphyexaclassid(int exam_type, String occuphyexaclassid, String id) {
		String sql="select * from occtemplate where exam_type = " + exam_type + " and occuphyexaclassid = '" + occuphyexaclassid + "' and id != '" + id + "'";
		List<ZybOcctemplate> l=this.jqm.getList(sql, ZybOcctemplate.class);
		if(l.isEmpty()) {
			return null;
		}
		return l.get(0);
	}
}
