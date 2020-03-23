package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.domain.ExamInfoRecyclingGuid;
import com.hjw.wst.service.ExamInfoRecyclingGuidService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class ExamInfoRecyclingGuidImpl implements  ExamInfoRecyclingGuidService{
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
	public void addExamInfoRecyclingGuidService(ExamInfoRecyclingGuid exam) throws ServiceException {
		this.pm.save(exam);
	}

	@Override
	public List<ExamDepResultDTO> getExamResultList(String exam_num, String itemids, String center_num) throws ServiceException {
		String sql = " select e.item_name,v.exam_result from examinfo_charging_item ec,charging_item_exam_item ce,examination_item e "
				   + " left join v_exam_result v on e.id = v.exam_item_id and v.exam_num = '"+exam_num+"'"
				   + " where e.id in ("+itemids+") and ec.exam_num = '"+exam_num+"' and ec.isActive = 'Y' and ec.pay_status <> 'M' and ec.center_num = '"+center_num+"' "
				   + " and ec.charging_item_code = ce.charging_item_code and ce.item_code = e.item_num";
		List<ExamDepResultDTO> list = this.jqm.getList(sql, ExamDepResultDTO.class);
		return list;
	}
}
