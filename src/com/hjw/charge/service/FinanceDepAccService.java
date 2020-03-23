package com.hjw.charge.service;

import java.util.List;

import com.hjw.charge.DTO.CashierDailyAccDTO;
import com.hjw.charge.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.charge.DTO.FinanceDepAccDTO;
import com.hjw.charge.DTO.FinanceDepAccPaywayDTO;
import com.hjw.charge.model.FinanceDepAccModel;
import com.hjw.charge.DTO.FinanceVsCashierDailyAccDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface FinanceDepAccService {

	/**
	 * 查询所有财务未日结的收费员日结信息
	     * @Title: getCashierDailyAccAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccDTO>      
	     * @throws
	 */
	public List<CashierDailyAccDTO> getCashierDailyAccAll(UserDTO user) throws ServiceException;
	
	/**
	 * 汇总所有财务未日结的收费员日结信息总金额和个收费方式金额
	     * @Title: getCashierDailyAccSum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<FinanceDepAccDTO>      
	     * @throws
	 */
	public List<FinanceDepAccDTO> getCashierDailyAccSum(UserDTO user) throws ServiceException;
	
	/**
	 * 保存财务部门日结信息
	     * @Title: saveFinanceDepAcc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveFinanceDepAcc(FinanceDepAccModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 查询财务部门已日结信息列表
	     * @Title: getFinanceDepAccList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFinanceDepAccList(FinanceDepAccModel model,String center_num, int rows, int page) throws ServiceException;
	
	/**
	 * 根据财务部门日结号查询收费方式信息列表
	     * @Title: getFinanceDepAccPaywayList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param fd_acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<FinanceDepAccPaywayDTO>      
	     * @throws
	 */
	public List<FinanceDepAccPaywayDTO> getFinanceDepAccPaywayList(String fd_acc_num) throws ServiceException;
	
	/**
	 * 根据财务部门日结号查询收费员日结信息列表
	     * @Title: getFinanceVsCashierDailyAccList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param fd_acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<FinanceVsCashierDailyAccDTO>      
	     * @throws
	 */
	public List<FinanceVsCashierDailyAccDTO> getFinanceVsCashierDailyAccList(String fd_acc_num) throws ServiceException;
	
	/**
	 * 查询收费员日结发票信息列表
	     * @Title: getFinanceVsCashierInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param daily_status
	     * @param: @param fd_acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccInvoiceDTO>      
	     * @throws
	 */
	public List<CashierDailyAccInvoiceDTO> getFinanceVsCashierInvoiceList(String daily_status,String fd_acc_num) throws ServiceException;
	/**
	 * 查询实时收费员个人收费明细数据
	     * @Title: getChargingSummarySingleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CashierDailyAccListDTO>      
	     * @throws
	 */
	public PageReturnDTO getChargingSummarySingleList(FinanceDepAccModel model, int rows, int page,String center_num) throws ServiceException;
	
	/**
	 * 查询实时收费员团体收费明细数据
	     * @Title: getChargingSummaryGroupList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getChargingSummaryGroupList(FinanceDepAccModel model, int rows, int page,String center_num) throws ServiceException;
	
	/**
	 * 查询实时收费员发票信息列表
	     * @Title: getChargingInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getChargingInvoiceList(FinanceDepAccModel model, int rows, int page,String center_num) throws ServiceException;
	
	/**
	 * 查询实时收费员收费金额汇总信息
	     * @Title: getChargingSummarySum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<FinanceDepAccDTO>      
	     * @throws
	 */
	public List<FinanceDepAccDTO> getChargingSummarySum(FinanceDepAccModel model,String center_num) throws ServiceException;
	
	/**
	 *  获取时间段内财务日结汇总
	     * @Title: getChargingSummarySum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<FinanceDepAccDTO>      
	     * @throws
	 */
	public List<FinanceDepAccDTO> getMonthlyStatementSum(FinanceDepAccModel model,String center_num) throws ServiceException;
}
