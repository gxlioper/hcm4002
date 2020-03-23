package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List; 




import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybRecheckItemDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseclassDTO;
import com.hjw.zyb.DTO.ZybrecheckExclDiseaseDTO;
import com.hjw.zyb.domain.ZybRecheckItem;
import com.hjw.zyb.domain.Zyboccudisease;
import com.hjw.zyb.domain.ZybrecheckExclDisease;
import com.hjw.zyb.model.ZyboccudiseaseModel;
import com.hjw.zyb.model.ZybrecheckExclDiseaseModel;
import com.hjw.zyb.service.ZybcheckItemDiseaseService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

/**
 * 复查项目/及要求/排除目标疾病
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.service.impl   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月20日 上午9:32:14   
     * @version V2.0.0.0
 */
public class ZybcheckItemDiseaseServiceImpl implements ZybcheckItemDiseaseService{
	
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
	
	public PageReturnDTO queryCheckItem(ZybrecheckExclDiseaseModel model,int page, int pageSize) throws SQLException,ServiceException {
		String sql = "select z.center_num,z.check_item_ask,z.check_item_id from zyb_recheck_item z  where   z.center_num='"+model.getCenter_num()+"'";
		PageSupport map = jqm.getList(sql,page,pageSize,ZybRecheckItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	public void deleteCheckItem(String ids) throws SQLException,
	ServiceException {
		String hql = "From  ZybRecheckItem z  where   z.check_item_id  in("+ids+")";
		List<ZybRecheckItem>  li = qm.find(hql);
		for (ZybRecheckItem zybRecheckItem : li) {
			pm.remove(zybRecheckItem);
		}
	}

	public ZybRecheckItem getCheckItem(ZybrecheckExclDiseaseModel model)
			throws SQLException, ServiceException {
		String hql = "From  ZybRecheckItem z  where   z.check_item_id='"+model.getCheck_item_id()+"'";
		List<ZybRecheckItem>  li = qm.find(hql);
		return li.get(0);
	}

	public void addcheckItem(ZybRecheckItem item) throws SQLException,
	ServiceException {
		this.pm.save(item);

	}

	public void updatecheckItem(ZybRecheckItem item) throws SQLException,
	ServiceException {
		pm.update(item);
	}

	public PageReturnDTO queryRecheckExclDisease(
			ZybrecheckExclDiseaseModel model, int page, int pageSize)
			throws SQLException, ServiceException {
		String sql = "select d.check_disease_name,d.check_disease_id from zyb_recheck_excl_disease  d";
		PageSupport map = jqm.getList(sql,page,pageSize,ZybrecheckExclDiseaseDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	public void deleteRecheckExclDisease(String ids) throws SQLException,
	ServiceException {
		String  hql = "From  ZybrecheckExclDisease  z   where   z.check_disease_id  in("+ids+")";
		List<ZybrecheckExclDisease> li = qm.find(hql);
		for (ZybrecheckExclDisease zybrecheckExclDisease : li) {
			pm.remove(zybrecheckExclDisease);
		}

	}

	public ZybrecheckExclDisease getRecheckExclDisease(ZybrecheckExclDiseaseModel model)
			throws SQLException, ServiceException {
		String  hql = "From  ZybrecheckExclDisease  z   where   z.check_disease_id='"+model.getCheck_disease_id()+"'";
		List<ZybrecheckExclDisease> li = qm.find(hql);
		return li.get(0);
	}

	public void addRecheckExclDisease(ZybrecheckExclDisease disease) throws SQLException,
	ServiceException {
		pm.save(disease);

	}

	public void updateRecheckExclDisease(ZybrecheckExclDisease disease)
			throws SQLException, ServiceException {
		pm.update(disease);
	}


}
