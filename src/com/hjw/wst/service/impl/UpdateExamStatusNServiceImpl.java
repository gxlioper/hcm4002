package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.model.ExamInfoModel;
import com.hjw.wst.service.UpdateExamStatusNService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class UpdateExamStatusNServiceImpl implements UpdateExamStatusNService {
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
	

	public PageReturnDTO getExamInfoItem(String exam_num, String center_num,int page, int PageSize) throws ServiceException {
		String sql = " select e.id  as  eid,e.exam_status,c.item_code,c.item_name,c.item_category,c.sex,c.amount "
				+ "from  examinfo_charging_item  e,charging_item c,exam_info f"
		          +" where   c.id=e.charge_item_id and  e.isActive='Y' and e.center_num = '"+center_num+"'  "
		          		+ "and   f.exam_num=e.exam_num  and  f.is_Active='Y'  "
		          +" and   e.exam_status='C'  and  f.exam_num='"+exam_num.trim()+"'  ";
		PageSupport map = this.jqm.getList(sql, page, PageSize,ChargingItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(PageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public void saveItemexamStatus(String ids) throws ServiceException {
		String hql = "FROM  ExaminfoChargingItem  WHERE  id in("+ids+")";
			List<ExaminfoChargingItem> li = this.qm.find(hql);
			for (ExaminfoChargingItem a : li) {
				a.setExam_status("N");
				this.pm.update(a);
			}
		
	}

}
