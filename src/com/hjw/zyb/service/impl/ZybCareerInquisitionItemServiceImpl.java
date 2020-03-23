package com.hjw.zyb.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybCareerInquisitionItemDTO;
import com.hjw.zyb.domain.ZybCareerInquisitionItem;
import com.hjw.zyb.model.ZybCareerInquisitionItemModel;
import com.hjw.zyb.service.ZybCareerInquisitionItemService;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ZybCareerInquisitionItemServiceImpl implements  ZybCareerInquisitionItemService{
	
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
	public PageReturnDTO queryZybCareerInquisitionItemList(ZybCareerInquisitionItemModel model, int page, int pageSize)
			throws ServiceException, SQLException {
		String sql = "select z.item_id,z.item_code,z.item_name,z.sex,z.isshow,z.[order]  from zyb_career_inquisition_item z  where  1=1 ";
				    if( model.getItem_code()!=null && !"".equals(model.getItem_code())){
				sql += "  and   z.item_code='"+model.getItem_code().trim()+"'";    	
				    }
				    if( model.getItem_name()!=null && !"".equals(model.getItem_name())){
				sql+="   and  z.item_name  like '%"+model.getItem_name()+"%'";   	
				    }
				sql+="  order by [order]";
		PageSupport map = jqm.getList(sql,page,pageSize,ZybCareerInquisitionItemDTO.class);
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
	public void deletequeryZybCareerInquisitionItem(String ids) throws ServiceException, SQLException {
		String hql = "From ZybCareerInquisitionItem  where  item_id in("+ids+")";
		List<ZybCareerInquisitionItem> li = qm.find(hql);
		for (ZybCareerInquisitionItem zybCareerInquisitionItem : li) {
			pm.remove(zybCareerInquisitionItem);
		}
	}

	@Override
	public ZybCareerInquisitionItem getZybCareerInquisitionItem(String id) throws ServiceException, SQLException {
		String hql = "From ZybCareerInquisitionItem  where  item_id='"+id+"'";
		List<ZybCareerInquisitionItem> li = qm.find(hql);
		return li.get(0);
	}

	@Override
	public void addZybCareerInquisitionItem(ZybCareerInquisitionItem item) throws ServiceException, SQLException {
		pm.save(item);
	}

	@Override
	public void updateZybCareerInquisitionItem(ZybCareerInquisitionItem item) throws ServiceException, SQLException {
		pm.update(item);
	}

	@Override
	public int getZybCareerInquisitionItemCode(String code) throws ServiceException, SQLException {
		String hql = "From ZybCareerInquisitionItem  where  item_code='"+code.trim()+"'";
		List<ZybCareerInquisitionItem> li = qm.find(hql);
		int a;
		if( li.size()>0 ){
			a=1;
		}else{
			a=0;
		}
		return a;
	}

	@Override
	public int getZybCareerInquisitionItemCodeOrder() throws ServiceException, SQLException {
		String sql = "select max([order]) as [order] from  zyb_career_inquisition_item";
		List<ZybCareerInquisitionItemDTO> li = jqm.getList(sql,ZybCareerInquisitionItemDTO.class);
		return li.get(0).getOrder()+1;
	}
}
