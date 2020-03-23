package com.hjw.charge.service.impl;

import java.util.List;

import com.hjw.charge.DTO.UserInvoiceDTO;
import com.hjw.charge.domain.UserInvoiceCharge;
import com.hjw.charge.service.UserInvoiceChargeService;
import com.hjw.wst.DTO.UserInfoDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class UserInvoiceChargeServiceImpl implements UserInvoiceChargeService {
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
	@SuppressWarnings("unchecked")
	public UserInvoiceDTO getUserInvoiceByUserId(long userId,String bill_type) throws ServiceException{
		String sql = "select u.id,u.user_id,u.invoice_class,d.data_name as invoice_classs,u.invoice_num_max,"
				+ "u.invoice_num_min,u.invoice_num_used,u.is_use_all from user_invoice u "
				+ "left join data_dictionary d  on u.invoice_class = d.id where is_active = 'Y' and bill_type='"+bill_type+"' and"
				+" ((u.user_id = '"+userId+"' and is_use_all = 'N') or (u.user_id = 0 and is_use_all = 'Y')) ";
		List<UserInvoiceDTO> list = this.jqm.getList(sql, UserInvoiceDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public UserInvoiceCharge saveUserInvoice(UserInvoiceCharge userInvoice) throws ServiceException{
		this.pm.save(userInvoice);
		return userInvoice;
	}

	@Override
	public UserInvoiceCharge updateUserInvoice(UserInvoiceCharge userInvoice) throws ServiceException{
		this.pm.update(userInvoice);
		return userInvoice;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getInvoiceIsUseAll(String bill_type) throws ServiceException {
		List<UserInvoiceCharge> list = this.qm.find("from UserInvoiceCharge where bill_type='"+bill_type+"'");
		if(list.size() != 0){
			return list.get(0).getIs_use_all();
		}
		return "N";
	}

	@Override
	public List<UserInvoiceDTO> getUserInvoiceList(String isUseAll,String bill_type) throws ServiceException {
		String sql = "select u.id,u.invoice_class,u.bill_type,d.data_name as invoice_classs,"
				+" u.invoice_num_max,u.invoice_num_min,u.invoice_num_used,u.is_active,"
				+" u.user_id,uu.chi_name as user_name,"
				+" u1.chi_name as updater,CONVERT(varchar(50),u.update_time,23) as update_time "
				+" from user_invoice u left join data_dictionary d on u.invoice_class = d.id"
				+" left join user_usr uu on u.user_id = uu.id "
				+" left join user_usr u1 on u.updater = u1.id";
		if("N".equals(isUseAll)){
			sql += " where u.user_id <> 0 and u.bill_type='"+bill_type+"' ";
		}else{
			sql += " where u.user_id = 0 and u.bill_type='"+bill_type+"' ";
		}
		sql += " order by u.is_active desc,u.invoice_class";
		List<UserInvoiceDTO> list = this.jqm.getList(sql, UserInvoiceDTO.class);
		return list;
	}

	@Override
	public UserInvoiceCharge getUserInvoiceByUserIdAndClass(long user_id, String invoice_class,String bill_type) throws ServiceException {
		List<UserInvoiceCharge> list = this.qm.find("from UserInvoiceCharge u where u.user_id = "+user_id+" and u.invoice_class ='"+invoice_class+"' and bill_type='"+bill_type+"' ");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveUserInvoiceClass(String invoice_class,String bill_type) throws ServiceException {
		String sql = "update user_invoice set is_active = 'N' where bill_type='"+bill_type+"'";
		this.jpm.execSql(sql);
		
		String sql2 = "update user_invoice set is_active = 'Y' where invoice_class = '"+invoice_class+"' and bill_type='"+bill_type+"'";
		this.jpm.execSql(sql2);
	}

	@Override
	public void saveUserInvoiceUseAll(String is_use_all,String bill_type) throws ServiceException { 
		if("Y".equals(is_use_all)){
			String sql = "update user_invoice set is_use_all = 'Y' where bill_type='"+bill_type+"'";
			this.jpm.execSql(sql);
		}else{
			String sql = "update user_invoice set is_use_all = 'N' where bill_type='"+bill_type+"'";
			this.jpm.execSql(sql);
		}
	}

	@Override
	public String getInvoiceClass(String bill_type) throws ServiceException {
		String sql = "select u.invoice_class from user_invoice u where u.is_active = 'Y' and bill_type='"+bill_type+"'";
		List<UserInvoiceDTO> list = this.jqm.getList(sql, UserInvoiceDTO.class);
		if(list.size() > 0){
			return list.get(0).getInvoice_class();
		}
		return null;
	}

	@Override
	public List<UserInfoDTO> getCashierList() throws ServiceException {
		String sql = "select distinct  u.id,u.chi_name from user_usr u,dep_user du,department_dep d "
				+" where u.id = du.user_id and u.is_active='Y' and du.dep_id = d.id and d.dep_num = 'sfc'";
		List<UserInfoDTO> list = this.jqm.getList(sql, UserInfoDTO.class);
		return list;
	}
}
