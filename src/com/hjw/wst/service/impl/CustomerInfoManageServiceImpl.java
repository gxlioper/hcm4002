package com.hjw.wst.service.impl;

import java.util.List;

import org.springframework.util.StringUtils;

import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.CustomerInfoManageDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.model.CustomerInfoManageModel;
import com.hjw.wst.service.CustomerInfoManageService;
import com.hjw.wst.service.examInfoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CustomerInfoManageServiceImpl implements CustomerInfoManageService {
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private examInfoService examInfoService;
	
	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
	}

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
	public CustomerInfo addCustomer(CustomerInfo ci) throws ServiceException {
		this.pm.save(ci);
		return ci;
	}

	@Override
	public CustomerInfo updateCustomer(CustomerInfo ci) throws ServiceException {
		this.pm.update(ci);
		return ci;
	}

	@Override
	public List<CustomerInfo> queryallCustomer() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CustomerInfo loadCustomer(long id) throws ServiceException {
		
		return (CustomerInfo)this.qm.get(CustomerInfo.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerInfo queryByNum(String arch_num) throws ServiceException {
		String sql = "from CustomerInfo where arch_num = '"+arch_num.trim()+"'";
		List<CustomerInfo> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageReturnDTO queryPageCustome(CustomerInfoManageModel model, int pageno, int pagesize,String sort,String order,UserDTO user) {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私资源  Y有N无
		String sql= " select c.id,c.arch_num,dbo.fun_CustomerToStar(c.id,'name',c.user_name,'"+isprivateflag+"') as user_name,dbo.fun_CustomerToStar(c.id,'id_num',c.id_num,'"+isprivateflag+"') as id_num,c.sex,convert(varchar(50),c.birthday,23) as birthday,"
				  + " dd.data_name as nation,c.is_Active"  
				  + " from customer_info c left join data_dictionary dd on dd.id=c.nation "
				  + " where c.is_Active = 'Y' and c.center_num='"+user.getCenter_num()+"'";
		if(!"".equals(model.getArch_num())){
			sql += " and c.arch_num = '"+model.getArch_num()+"'";
		}
		if(!"".equals(model.getUser_name())){
			sql += " and c.user_name like '"+model.getUser_name()+"%'";
		}
		if(!"".equals(model.getId_num())){
			sql += " and c.id_num = '"+model.getId_num()+"'";
		}
		if(!"".equals(model.getSex())){
			sql += " and c.sex = '"+model.getSex()+"'";
		}
		if(!"".equals(model.getTime1())){
			sql += " and c.birthday >= '"+model.getTime1()+" 00:00:00.000'";
		}
		if(!"".equals(model.getTime2())){
			sql += " and c.birthday <= '"+model.getTime2()+" 23:59:59.000'";
		}
		if(sort != null && !"".equals(sort)){
			sql += " order by "+sort+" "+order;
		}
		PageSupport map = this.jqm.getList(sql,pagesize, pageno,CustomerInfoManageDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CustomerInfo getCustomerInfoByIdNum(String id_num,String center_num) throws ServiceException {
		List<CustomerInfo> list = this.qm.find("from CustomerInfo c where c.id_num = '"+id_num+"' and c.center_num='"+center_num+"'");
		if(list.size()!=0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String bindPersonUpdate(CustomerInfoManageModel model, UserDTO user) throws ServiceException {
		ExamInfo examinfo = examInfoService.findExamInfo(model.getExam_num().trim());
		if(	StringUtils.isEmpty(examinfo)){
			return "无效体检号";
		}
		if(	StringUtils.isEmpty(model.getCustomer_id())){
			return "无效档案号";
		}
		examinfo.setCustomer_id(model.getCustomer_id());
		examInfoService.updateExamInfo(examinfo);
		return "人员档案绑定成功";
	}
}
