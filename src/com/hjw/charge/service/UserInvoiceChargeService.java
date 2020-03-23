package com.hjw.charge.service;

import java.util.List;

import com.hjw.charge.DTO.UserInvoiceDTO;
import com.hjw.charge.domain.UserInvoiceCharge;
import com.hjw.wst.DTO.UserInfoDTO;
import com.synjones.framework.exception.ServiceException;
/**
 * 用户发票号段功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dq     
     * @date:   2017年3月2日 上午11:50:27   
     * @version V2.0.0.0
 */
public interface UserInvoiceChargeService {

	/**
	 * 根据用户ID查询用户发票号段信息
	     * @Title: getUserInvoiceByUserId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userId
	     * @param: @return      
	     * @return: UserInvoice      
	     * @throws
	 */
	public UserInvoiceDTO getUserInvoiceByUserId(long userId,String bill_type) throws ServiceException;
	
	/**
	 * 新增保存用户发票号段
	     * @Title: saveUserInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userInvoice
	     * @param: @return      
	     * @return: UserInvoice      
	     * @throws
	 */
	public UserInvoiceCharge saveUserInvoice(UserInvoiceCharge userInvoice) throws ServiceException;
	
	/**
	 * 修改用户发票号段
	     * @Title: updateUserInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userInvoice
	     * @param: @return      
	     * @return: UserInvoice      
	     * @throws
	 */
	public UserInvoiceCharge updateUserInvoice(UserInvoiceCharge userInvoice) throws ServiceException;
	
	/**
	 * 查询是否启用共用发票号段
	     * @Title: getInvoiceIsUseAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getInvoiceIsUseAll(String bill_type) throws ServiceException;
	
	/**
	 * 查询发票号段信息列表
	     * @Title: getUserInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<UserInvoiceDTO>      
	     * @throws
	 */
	public List<UserInvoiceDTO> getUserInvoiceList(String isUseAll,String bill_type) throws ServiceException;
	
	/**
	 * 根据用户ID和发票类型查询发票号段信息
	     * @Title: getUserInvoiceByUserIdAndClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @param invoice_class
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: UserInvoice      
	     * @throws
	 */
	public UserInvoiceCharge getUserInvoiceByUserIdAndClass(long user_id,String invoice_class,String bill_type) throws ServiceException;
	
	/**
	 *  保存发票类型的启用
	     * @Title: saveUserInvoiceClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param invoice_class
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveUserInvoiceClass(String invoice_class,String bill_type) throws ServiceException;
	
	/**
	 * 保存设置发票号段共用或发票号段按人使用
	     * @Title: saveUserInvoiceUseAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param is_use_all
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveUserInvoiceUseAll(String is_use_all,String bill_type) throws ServiceException;
	
	/**
	 * 获取已启用的发票类型
	     * @Title: getInvoiceClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getInvoiceClass(String bill_type) throws ServiceException;
	
	/**
	 * 查询收费员列表
	     * @Title: getCashierList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<UserInfoDTO> getCashierList() throws ServiceException;
}
