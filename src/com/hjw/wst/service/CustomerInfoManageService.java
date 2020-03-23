package com.hjw.wst.service;


import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.model.CustomerInfoManageModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface CustomerInfoManageService {
	/**
	 * 添加
	     * @Title: addCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ci
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo addCustomer(CustomerInfo ci) throws ServiceException;;
		

	/**
	 * 更新
	     * @Title: updateCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ci
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo updateCustomer(CustomerInfo ci) throws ServiceException;

	/**
	 * 查询全部
	     * @Title: queryallCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CustomerInfo>      
	     * @throws
	 */
	public List<CustomerInfo> queryallCustomer() throws ServiceException;
	
	/**
	 * id查询
	     * @Title: loadCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo loadCustomer(long id) throws ServiceException;

	/**
	 * 编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param arch_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public  CustomerInfo queryByNum(String arch_num) throws ServiceException;
	
	/**
	 * @param user 
	 * 分页查询
	     * @Title: queryPageCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_name
	     * @param: @param arch_num
	     * @param: @param id_num
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryPageCustome(CustomerInfoManageModel model,int pageno, int pagesize,String sort,String order, UserDTO user);
	
	/**
	 * 根据身份证号查询档案信息
	     * @Title: getCustomerInfoByIdNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo getCustomerInfoByIdNum(String id_num,String center_num) throws ServiceException;
	/**
	 * 档案绑定人员
	     * @Title: bindPersonUpdate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String bindPersonUpdate(CustomerInfoManageModel model,UserDTO user)  throws ServiceException;
}
	
