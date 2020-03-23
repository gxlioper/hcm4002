package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.Customer_Type;
import com.hjw.wst.service.Customer_TypeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class Customer_TypeServiceImpl implements Customer_TypeService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	

	public QueryManager getQueryManager() {
		return qm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}


	@Override
	public Customer_Type addCustomer(Customer_Type ctms) throws ServiceException {
		this.pm.save(ctms);
		return ctms;
	}

	@Override
	public void deleteCustomer(Customer_Type ctms) throws ServiceException {
		this.pm.remove(ctms);

	}

	@Override
	public Customer_Type updateCustomer(Customer_Type ctms) throws ServiceException {
		this.pm.update(ctms);
		return ctms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer_TypeDTO> queryallCustomer() throws ServiceException {
		String sql="";
		List<Customer_TypeDTO> list=this.jqm.getList(sql,Customer_TypeDTO.class);
		return list;
	}

	@Override
	public Customer_Type loadCustomer(long id) throws ServiceException {
		
		return (Customer_Type)this.qm.get(Customer_Type.class, id);
	}

	@Override
	public Customer_Type queryByNum(String type_code) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageReturnDTO queryPageCustomer(String type_code, String type_name, int pageno, int pagesize) {
		String sql=" select c.id,c.type_code,c.type_name,c.type_comment, u.chi_name as creater , "
+" c .create_time,u1.chi_name as updater, c.update_time "
+" from customer_type c left join user_usr u on c.creater = u.id "
+" left join user_usr u1 on c.updater = u1.id  "
+" where type_code like '%"+type_code+"%' and type_name like '%"+type_name+"%' ";
		PageSupport map = this.jqm.getList(sql,pagesize, pageno, Customer_TypeDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;					
	}

}
