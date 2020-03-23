package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.model.CardMemberModel;
import com.synjones.framework.exception.ServiceException;

/**
 *   会员卡管理(会员信息管理)
     * @Title:  火箭蛙体检管理系统    
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月18日 下午2:01:59   
     * @version V2.0.0.0
 */
public interface CardManageService {

	/**
	 * @param user 
	 *  
	     * @Title: cardMemberList   
	     * @Description: TODO(查询会员列表信息)   
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO cardMemberList(String username,String id_num,String phone,long level,int pagesize, int pageno, UserDTO user) throws ServiceException;
	
	/**
	 * 	
	     * @Title: getCardMemberByIdNum   
	     * @Description: TODO(根据身份证查询会员信息)   
	     * @param: @param idNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardMember      
	     * @throws
	 */
	public CustomerMemberInfo getCardMemberByIdNum(String idNum) throws ServiceException;
	
	/**
	 * 根据id查询会员信息
	     * @Title: getCardMemberBymId   
	     * @Description: TODO()   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardMember      
	     * @throws
	 */
	public CustomerMemberInfo getCardMemberBymId(String id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCardMemberById   
	     * @Description: TODO(根据ID查询会员信息)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardMember      
	     * @throws
	 */
	public CardMemberModel getCardMemberById(String id) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCardMember   
	     * @Description: TODO(新增保存会员信息)   
	     * @param: @param cardMember
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardMember      
	     * @throws
	 */
	public CustomerMemberInfo saveCardMember(CustomerMemberInfo customerMemberInfo) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateCardMember   
	     * @Description: TODO(修改会员信息)   
	     * @param: @param cardMember
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardMember      
	     * @throws
	 */
	public CustomerMemberInfo updateCardMember(CustomerMemberInfo customerMemberInfo) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCustomerInfoByIdNum   
	     * @Description: TODO(根据身份证号查询档案信息)   
	     * @param: @param idNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo getCustomerInfoByIdNum(String idNum) throws ServiceException;
	/**
	 * 
	     * @Title: saveCustomerInfo   
	     * @Description: TODO(新增保存档案信息)   
	     * @param: @param customerInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo saveCustomerInfo(CustomerInfo customerInfo) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateCustomerInfo   
	     * @Description: TODO(修改会员信息)   
	     * @param: @param customerInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfo      
	     * @throws
	 */
	public CustomerInfo updateCustomerInfo(CustomerInfo customerInfo) throws ServiceException;

	
	/**
	 * 
	     * @Title: getCardInfoBymemberId   
	     * @Description: TODO(根据会员ID查询卡信息)   
	     * @param: @return      
	     * @return: List<CardInfo>      
	     * @throws
	 */
	public List<CardInfoDTO> getCardInfoByMerId(String memberId) throws ServiceException;
	
	/**
	 * 删除卡信息
	     * @Title: deleteCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param card_nums
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCardInfo(String card_nums,UserDTO user) throws ServiceException; 
}
