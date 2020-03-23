package com.hjw.charge.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.charge.DTO.CashierDailyAccDTO;
import com.hjw.charge.DTO.ChargingDetailSingleDTO;
import com.hjw.charge.DTO.ChargingInvoiceSingleDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.CollectFeesResult;
import com.hjw.charge.DTO.PosDailySummaryDTO;
import com.hjw.charge.DTO.WebCityQuerySendDTO;
import com.hjw.charge.DTO.WebCityReconciliationSendDTO;
import com.hjw.charge.domain.InsureAccountCharge;
import com.hjw.charge.model.CashierDailyAccModel;
import com.hjw.wst.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.wst.DTO.CashierDailyAccPaywayDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface CashierDailyAccChargeService {

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
	public List<CashierDailyAccListDTO> getCashierDailyListTu(long user_id) throws ServiceException;
	
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
	public PageReturnDTO getCashierDailyAcc(String daily_acc_date1, String daily_acc_date2, long user_id,int rows,int page) throws ServiceException;
	
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
	public List<CashierDailyAccListDTO> getCashierDailyAccDetail(String daily_acc_num,String daily_acc_class) throws ServiceException;
	
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
	
	/**
	 * 2015 pos 已日结总记录
	     * @Title: getendposdailySummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public List<PosDailySummaryDTO> getendposdailySummary(CashierDailyAccModel model, UserDTO user)throws ServiceException;
	/**
	 * 2021 收费员查询市医保个人收费
	     * @Title: getPersionCityReconciliation   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO getPersionCityReconciliation(CashierDailyAccModel model, UserDTO user, int rows, int page)throws ServiceException;
	/**
	 * 2022 收费员查询会员卡收费
	     * @Title: getCardCityReconciliation   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
//	public PageReturnDTO getCardCityReconciliation(CashierDailyAccModel model, UserDTO user, int rows, int page)throws ServiceException;
	/**
	 *2023 收费员查询市医保个人收费明细
	     * @Title: getPersionCityReconciliationDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public List<ChargingDetailSingleDTO> getPersionCityReconciliationDetailList(CashierDailyAccModel model,
			UserDTO user)throws ServiceException;
	/**
	 *2024 收费员查询市医保会员卡收费明细
	     * @Title: getCardCityReconciliationDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
//	public List<CardSaleDetailDTO> getCardCityReconciliationDetailList(CashierDailyAccModel model, UserDTO user)throws ServiceException;
	/**
	 *2025 查询市医保收费总额
	     * @Title: getCityReconciliationSummaryAmount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
//	public CardSaleDetailDTO getCityReconciliationSummaryAmount(CashierDailyAccModel model, UserDTO user)throws ServiceException;

	public String  getCityReconciliationQuery(CashierDailyAccModel model, UserDTO user)throws SQLException;

	public InsureAccountCharge getCityReconciliationQueryDetail(CashierDailyAccModel model, UserDTO user)throws ServiceException;
	/**
	 *2028 医保对账明细列表
	     * @Title: getInsureVerifyAccountDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO getInsureVerifyAccountDetailList(CashierDailyAccModel model, UserDTO user, int rows, int page) throws ServiceException;
	/**
	 *2029 医保校验交易是否能冲正
	     * @Title: checkCorrectTransaction   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String checkCorrectTransaction(CashierDailyAccModel model, UserDTO user) throws ServiceException;
	
	/**
	 *2030 医保  系统内对账查询
	     * @Title: getInsureAccountList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO getInsureAccountList(CashierDailyAccModel model, UserDTO user, int rows, int page) throws ServiceException;
	
	/**
	 *2031 个人退费信息列表
	     * @Title: getPersionRefundlist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO getPersionRefundlist(CashierDailyAccModel model, UserDTO user, int rows, int page)throws ServiceException;


	public CashierDailyAccDTO getCityReconciliationSummaryAmount(CashierDailyAccModel model, UserDTO user) throws ServiceException;

	public InsureAccountCharge getCityReconciliationSummaryAmountCharge(CashierDailyAccModel model, UserDTO user)throws ServiceException;
}
