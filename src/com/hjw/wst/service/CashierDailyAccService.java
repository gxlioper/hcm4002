package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.CashierDailyAccDTO;
import com.hjw.wst.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.wst.DTO.CashierDailyAccPaywayDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.PosDailySummaryDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.CashierDailyAccModel;
import com.synjones.framework.exception.ServiceException;

public interface CashierDailyAccService {

	/**
	 * 查询个人收费明细
	     * @Title: getCashierDailyAcclist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccListDTO>      
	     * @throws
	 */
	public List<CashierDailyAccListDTO> getCashierDailyAcclist(long user_id) throws ServiceException;
	
	/**
	 * 查询已收费的收费方式
	     * @Title: getChargingWay   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingWayDTO>      
	     * @throws
	 */
	public List<ChargingWayDTO> getChargingWay(long user_id) throws ServiceException;
	
	/**
	 * 查询收费员开发票信息列表
	     * @Title: getCashierInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingInvoiceSingleDTO>      
	     * @throws
	 */
	public List<ChargingInvoiceSingleDTO> getCashierInvoiceList(long user_id) throws ServiceException;
	
	/**
	 * 保存收费员日结信息
	     * @Title: saveCashierDailyacc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String saveCashierDailyacc(UserDTO user) throws ServiceException;
	
	/**
	 * 查询团体收费明细
	     * @Title: getCashierDailyListTu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccListDTO>      
	     * @throws
	 */
	public List<CashierDailyAccListDTO> getCashierDailyListTu(long user_id,String center_num) throws ServiceException;
	
	/**
	 * 查询收费员已日结信息
	     * @Title: getCashierDailyAccList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getCashierDailyAcc(String daily_acc_date1, String daily_acc_date2, long user_id,String center_num,int rows,int page) throws ServiceException;
	
	/**
	 * 根据日结号查询收费方式明细列表
	     * @Title: getCashierDailyAccPayway   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param daily_acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccPaywayDTO>      
	     * @throws
	 */
	public List<CashierDailyAccDTO> getCashierDailyAccPayway(String daily_acc_num) throws ServiceException;
	
	/**
	 * 根据日结号查询发票信息列表
	     * @Title: getCashierDailyAccInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param daily_acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccInvoiceDTO>      
	     * @throws
	 */
	public List<CashierDailyAccInvoiceDTO> getCashierDailyAccInvoice(String daily_acc_num) throws ServiceException;
	
	/**
	 * 根据日结号查询收费明细列表
	     * @Title: getCashierDailyAccDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param daily_acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccListDTO>      
	     * @throws
	 */
	public List<CashierDailyAccListDTO> getCashierDailyAccDetail(String daily_acc_num,String daily_acc_class,String center_num) throws ServiceException;
	
	/**
	 * 查询收费员未日收费员信息
	     * @Title: getCashierDailyAllAmountJob   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccDTO>      
	     * @throws
	 */
	public List<UserDTO> getCashierDailyAllAmountJob() throws ServiceException;
	
	/**
	 * 查询收费员未日结收费方式金额汇总
	     * @Title: getCashierDailyPaywayAmountJob   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccPaywayDTO>      
	     * @throws
	 */
	public List<CashierDailyAccPaywayDTO> getCashierDailyPaywayAmountJob() throws ServiceException;
	
	
	//pos日结汇总
	public List<PosDailySummaryDTO> getPosDailySummary(CashierDailyAccModel model,UserDTO user) throws ServiceException;
	
//	收费员pos日结明细
	public PageReturnDTO getposDailyDetailList(CashierDailyAccModel model, UserDTO user,int rows,int page) throws ServiceException;
	
	//保存日结
	public CollectFeesResult savePosDailyInfo(CashierDailyAccModel model, UserDTO user)throws ServiceException;
	/**
	 * 2014 pos 已日结明细
	     * @Title: getEndPosDailyDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO getEndPosDailyDetailList(CashierDailyAccModel model, UserDTO user, int rows, int page)throws ServiceException;


}
