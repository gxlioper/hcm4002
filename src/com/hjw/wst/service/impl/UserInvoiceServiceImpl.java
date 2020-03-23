package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.UserInvoiceDTO;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.service.UserInvoiceService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class UserInvoiceServiceImpl implements UserInvoiceService {
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
	public UserInvoiceDTO getUserInvoiceByUserId(long userId) throws ServiceException{
		String sql = "select u.id,u.user_id,u.invoice_class,d.data_name as invoice_classs,u.invoice_num_max,"
				+ "u.invoice_num_min,u.invoice_num_used,u.is_use_all from user_invoice u "
				+ "left join data_dictionary d  on u.invoice_class = d.id where is_active = 'Y' and"
				+" ((u.user_id = "+userId+" and is_use_all = 'N') or (u.user_id = 0 and is_use_all = 'Y'))";
		List<UserInvoiceDTO> list = this.jqm.getList(sql, UserInvoiceDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public UserInvoice saveUserInvoice(UserInvoice userInvoice) throws ServiceException{
		this.pm.save(userInvoice);
		return userInvoice;
	}

	@Override
	public UserInvoice updateUserInvoice(UserInvoice userInvoice) throws ServiceException{
		this.pm.update(userInvoice);
		return userInvoice;
	}

	@Override
	@SuppressWarnings("unchecked")
	public String getInvoiceIsUseAll() throws ServiceException {
		List<UserInvoice> list = this.qm.find("from UserInvoice");
		if(list.size() != 0){
			return list.get(0).getIs_use_all();
		}
		return "N";
	}

	@Override
	public List<UserInvoiceDTO> getUserInvoiceList(String isUseAll) throws ServiceException {
		String sql = "select u.id,u.invoice_class,d.data_name as invoice_classs,"
				+" u.invoice_num_max,u.invoice_num_min,u.invoice_num_used,u.is_active,"
				+" u.user_id,uu.chi_name as user_name,"
				+" u1.chi_name as updater,CONVERT(varchar(50),u.update_time,23) as update_time "
				+" from user_invoice u left join data_dictionary d on u.invoice_class = d.id"
				+" left join user_usr uu on u.user_id = uu.id "
				+" left join user_usr u1 on u.updater = u1.id  where u.is_active='Y' ";
		if("N".equals(isUseAll)){
			sql += " and u.user_id <> 0";
		}else{
			sql += " and u.user_id = 0";
		}
		sql += " order by u.is_active desc,u.invoice_class";
		List<UserInvoiceDTO> list = this.jqm.getList(sql, UserInvoiceDTO.class);
		return list;
	}

	@Override
	public UserInvoice getUserInvoiceByUserIdAndClass(long user_id, String invoice_class) throws ServiceException {
		List<UserInvoice> list = this.qm.find("from UserInvoice u where u.user_id = "+user_id+" and u.invoice_class ='"+invoice_class+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveUserInvoiceClass(String invoice_class) throws ServiceException {
		String sql = "update user_invoice set is_active = 'N'";
		this.jpm.execSql(sql);
		
		String sql2 = "update user_invoice set is_active = 'Y' where invoice_class = '"+invoice_class+"'"; 
		this.jpm.execSql(sql2);
	}

	@Override
	public void saveUserInvoiceUseAll(String is_use_all) throws ServiceException { 
		if("Y".equals(is_use_all)){
			String sql = "update user_invoice set is_use_all = 'Y'";
			this.jpm.execSql(sql);
		}else{
			String sql = "update user_invoice set is_use_all = 'N'";
			this.jpm.execSql(sql);
		}
	}

	@Override
	public String getInvoiceClass() throws ServiceException {
		String sql = "select u.invoice_class from user_invoice u where u.is_active = 'Y'";
		List<UserInvoiceDTO> list = this.jqm.getList(sql, UserInvoiceDTO.class);
		if(list.size() > 0){
			return list.get(0).getInvoice_class();
		}
		return null;
	}

	@Override
	public List<UserInfoDTO> getCashierList( String center_num ) throws ServiceException {
		String sql = "select distinct u.id,u.chi_name from user_usr u,dep_user du,department_dep d,department_vs_center de,exam_user eu "
				+" where u.id = du.user_id and eu.user_id=u.id  and de.dep_id = d.id   and du.dep_id = d.id and d.dep_num = 'sfc' and u.is_active='Y' and eu.center_num ='"+center_num+"' group by u.id,u.chi_name";
		List<UserInfoDTO> list = this.jqm.getList(sql, UserInfoDTO.class);
		return list;
	}
	@Override
	public void deleteInvoice(String id) throws ServiceException {
		String sql = "update user_invoice set is_active = 'N' where id='"+id+"'";
		this.jpm.execSql(sql);
	}
}
