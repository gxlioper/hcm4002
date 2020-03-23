package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.model.CardSaleModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 卡销售管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dq     
     * @date:   2019年4月1日 下午9:50:05   
     * @version V2.0.0.0
 */
public interface CardSaleService {

	/**
	 * 根据条件查询未售卡信息列表
	     * @Title: getNotSaleCardInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param card_num
	     * @param: @param card_num_s
	     * @param: @param card_num_e
	     * @param: @param start_time
	     * @param: @param end_time
	     * @param: @param company
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getNotSaleCardInfoList(String card_num,String card_num_s,String card_num_e,String start_time,String end_time,String company,int pagesize, int pageno,UserDTO user) throws ServiceException;
	
	/**
	 * 保存售卡信息
	     * @Title: saveCardSaleInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CollectFeesResult      
	     * @throws
	 */
	public CollectFeesResult saveCardSaleInfo(CardSaleModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 保存预售卡信息
	     * @Title: saveCardSaleInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CollectFeesResult      
	     * @throws
	 */
	public CollectFeesResult saveAdvCardSaleInfo(CardSaleModel model,UserDTO user) throws ServiceException;
	
	
	/**
	 * 预售卡确认收费保存
	     * @Title: saveAdvanceCardSale   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CollectFeesResult      
	     * @throws
	 */
	public CollectFeesResult saveAdvanceCardSale(CardSaleModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 已售卡或预售卡信息列表
	     * @Title: getCardSaleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sale_status
	     * @param: @param start_time
	     * @param: @param end_time
	     * @param: @param isPrintRecepit
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getCardSaleList(long sale_status,String start_time,String end_time,String sale_trade_num,String isPrintRecepit,int pagesize, int pageno,UserDTO user) throws ServiceException;
	
	/**
	 * 根据售卡流水号查询卡明细列表
	     * @Title: getCardSaleDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sale_trade_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CardInfoDTO>      
	     * @throws
	 */
	public List<CardInfoDTO> getCardSaleDetailList(String sale_trade_num,UserDTO user) throws ServiceException;
	
	/**
	 * 售卡作废发票
	     * @Title: invalidCardSaleInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sale_trade_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String invalidCardSaleInvoice(String sale_trade_num,UserDTO user) throws ServiceException;
	
	/**
	 * 售卡信息补开发票
	     * @Title: saveCardSaleInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public CollectFeesResult saveCardSaleInvoice(CardSaleModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 获取公司信息列表
	     * @Title: getCompanyList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CompanyInfo>      
	     * @throws
	 */
	public List<CompanyInfo> getCompanyList() throws ServiceException;
	
	/**
	 * 根据卡交易流水获取卡信息
	     * @Title: getCardCompany   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sale_tread_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CardInfoDTO>      
	     * @throws
	 */
	public List<CardInfoDTO> getCardCompany(String sale_trade_num) throws ServiceException;
	
	/**
	 * 根据售卡流水号查询卡明细列表
	     * @Title: getCardSaleDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sale_trade_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CardInfoDTO>      
	     * @throws
	 */
	public List<CardInfoDTO> getCardSaleDetailList2(String sale_trade_nums) throws ServiceException;
	
}
