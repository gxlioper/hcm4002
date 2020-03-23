package com.hjw.wst.service;


import java.util.List;

import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.Customer_Type;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface Customer_TypeService {
	/**
	 * 添加
	     * @Title: addCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Customer_Type      
	     * @throws
	 */
	public Customer_Type addCustomer(Customer_Type ctms) throws ServiceException;;
		/**
		 * 删除
		     * @Title: deleteCustomer   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param ctms
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	public void deleteCustomer(Customer_Type ctms) throws ServiceException;;

	/**
	 * 更新
	     * @Title: updateCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Customer_Type      
	     * @throws
	 */
	public Customer_Type updateCustomer(Customer_Type ctms) throws ServiceException;

	/**
	 *查询全部
	     * @Title: queryallCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public List<Customer_TypeDTO> queryallCustomer() throws ServiceException;
	
	/**
	 * id查询
	     * @Title: loadCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Customer_Type      
	     * @throws
	 */
	
	public Customer_Type loadCustomer(long id) throws ServiceException;

	/**
	 * 编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Customer_Type      
	     * @throws
	 */
	public  Customer_Type queryByNum(String type_code) throws ServiceException;
	
	/**
	 * 分页查询
	     * @Title: queryPageCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param type_code
	     * @param: @param type_name
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryPageCustomer(String type_code,String type_name,int pageno, int pagesize);
	
}
	
