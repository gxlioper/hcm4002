package com.hjw.wst.service;

import java.util.Date;
import java.util.List;

import com.hjw.wst.DTO.CardExamSetItemDTO;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.ChargingDetailSingleDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleDTO;
import com.hjw.wst.DTO.ChargingSummarySingleDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.CompanyAccountDTO;
import com.hjw.wst.DTO.CompanyAccountDetailDTO;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.DTO.MenuDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserFeeDTO;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.ChargingDetailSingle;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.model.CollectFeesModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 登记台收费
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月26日 下午10:00:36   
     * @version V2.0.0.0
 */
public interface CollectFeesService {

	/**
	 * 查询未收费项目列表
	     * @Title: getwitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return      
	     * @return: List<ExamInfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExamResultChargingItemDTO> getwitemList(String examNum,String is_fees_qijian,String center_num) throws ServiceException;
	
	/**
	 * 查询已收费项目列表
	     * @Title: getyitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return      
	     * @return: List<ExamInfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExamResultChargingItemDTO> getyitemList(String examNum,String center_num) throws ServiceException;
	
	/**
	 * @param user 
	 * 查询人员体检信息
	     * @Title: getCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfoDTO      
	     * @throws
	 */
	public CustomerExamDTO getCustomerInfo(String examNum, UserDTO user) throws ServiceException;
	
	/**
	 * 收费保存信息
	     * @Title: saveCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public CollectFeesResult saveCollectFees(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException;
	
	/**
	 * 根据体检信息ID查询绑定卡信息
	     * @Title: getCardListByexamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<CardInfoDTO>      
	     * @throws
	 */
	public List<CardInfoDTO> getCardListByexamId(long exam_id,String center_num) throws ServiceException;
	
	/**
	 * 根据卡号查询卡信息
	     * @Title: getCardInfoByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param card_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardInfoDTO      
	     * @throws
	 */
	public CardInfoDTO getCardInfoByNum(String card_num,UserDTO user) throws ServiceException;
	
	/**
	 * 根据体检号查询结算总表列表
	     * @Title: getChargingSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingSummarySingleDTO>      
	     * @throws
	 */
	public List<ChargingSummarySingleDTO> getChargingSummary(String exam_num,String center_num) throws ServiceException;
	
	/**
	 * 根据总表ID查询结算明细
	     * @Title: getChargingDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param summaryId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingDetailSingleDTO>      
	     * @throws
	 */
	public List<ChargingDetailSingleDTO> getChargingDetail(long summaryId,String center_num) throws ServiceException;
	
	/**
	 * 根据总表ID查询收费方式明细
	     * @Title: getChargingWay   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param summaryId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingWayDTO>      
	     * @throws
	 */
	public List<ChargingWayDTO> getChargingWay(long summaryId,String center_num)throws ServiceException;
	
	/**
	 * 根据体检号查询 各收费方式的缴费金额
	     * @Title: getChargingWayByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingWayDTO>      
	     * @throws
	 */
	public List<ChargingWayDTO> getChargingWayByExamNum(String exam_num,String req_nums,String center_num) throws ServiceException;
	
	/**
	 * 保存退费信息
	     * @Title: saveTuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveTuifei(CollectFeesModel model,UserDTO user) throws ServiceException;
	
	public List<CardInfoDTO> getCardInfo(long exam_id) throws ServiceException;
	/**
	 * 获取个人发票最大号
	 	 * @Title: getMaxFaPiaoHaoByUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getMaxFaPiaoHaoByUser(long userId) throws ServiceException;
	
	/**
	 * 查询发票号是否存在
	     * @Title: getChargingInvoiceSingleByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param invoiceNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingInvoiceSingleByNum (String invoiceNum,String invoice_class) throws ServiceException;
	
	/**
	 * 查询发票是否已作废
	     * @Title: getNullifyInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param invoiceNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getNullifyInvoice(String invoiceNum,String invoice_class,String center_num) throws ServiceException;
	/**
	 * 根据退费项目信息获取已开发票信息
	     * @Title: getsingleInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param chargingIds
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingInvoiceSingleDTO>      
	     * @throws
	 */
	public List<ChargingInvoiceSingleDTO> getsingleInvoice(String chargingIds,String examNum,String center_num) throws ServiceException;
	
	/**
	 * 获取需要打印发票的申请单列表
	     * @Title: getChargingSingleInvoickList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingSummarySingleDTO>      
	     * @throws
	 */
	public List<ChargingSummarySingleDTO> getChargingSingleInvoickList(String examNum,String center_num) throws ServiceException;
	
	/**
	 * 保存补打发票信息
	     * @Title: saveSingleInviockBu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public CollectFeesResult saveSingleInviockBu(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException;
	
	/**
	 * 发送缴费申请
	     * @Title: paymentApplication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String paymentApplication(String examNum,UserDTO user) throws ServiceException;
	
	/**
	 * 查询已开发票的项目列表
	     * @Title: getykfpItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamResultChargingItemDTO>      
	     * @throws
	 */
	public List<ExamResultChargingItemDTO> getykfpItemList(String examNum,String center_num) throws ServiceException;
	
	/**
	 * 查询已开收据列表
	     * @Title: getyksjItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamResultChargingItemDTO>      
	     * @throws
	 */
	public List<ExamResultChargingItemDTO> getyksjItemList(String examNum,String center_num) throws ServiceException;
	
    
    /**
	 * 
	 * @Title: delSingleInviockTT @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param invoice_num @param: @param
	 * userId @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String delSingleInviockTT(long batchid,long invoice_id, long userId,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingInvoiceSingleOfaccountnum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param account_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingInvoiceSingle      
	     * @throws
	 */
	public ChargingInvoiceSingle getChargingInvoiceSingleOfaccountnum(String account_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateSingleInviockTT   
	     * @Description: 团体打印发票   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
    public MenuDTO updateSingleInviockTT(long batchid,long id,String invoid_num,String title,String invoidtype,double amt,String invoiceclass,long userId,String center_num) throws ServiceException;

	
	/**
	 * 
	     * @Title: getChargingSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingSummarySingle>      
	     * @throws
	 */
	public List<ChargingSummarySingleDTO> getChargingSummary() throws ServiceException;
	
	/**
	 * 保存HIS收费信息
	     * @Title: saveCollectFeesYiBao   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param operator
	     * @param: @param visit_date
	     * @param: @param invoice_num
	     * @param: @param req_num
	     * @param: @param amount
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public UserFeeDTO saveCollectFeesYiBao(String rcpt,String operator,Date visit_date,String invoice_num,String req_num,Double amount,long summaryId) throws ServiceException;

	/**
	 * 发票作废个人
	     * @Title: invalidInvoiceGe   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param invoice_nums
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String invalidInvoiceGe(String invoice_nums,UserDTO user,String invoice_class) throws ServiceException;
	
	/**
	 * 撤销收费
	     * @Title: chexiaoshoufei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String chexiaoshoufei(CollectFeesModel model,UserDTO user) throws ServiceException;

	/**
	 * 无效发票作废
	     * @Title: getWuxaiofapiaozuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */

	public void saveWuxaiofapiaozuifei(CollectFeesModel model, UserDTO user) throws ServiceException;
	
	/**
	 * 根据体检号查询需要收费的信息
	     * @Title: getMergeChargeByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getMergeChargeByExamNum(String exam_num, String center_num) throws ServiceException;
	
	/**
	 *保存合并收费信息
	     * @Title: saveCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<CollectFeesResult> queryPrintNum(String exam_num)throws ServiceException;
	/**
	 * 划价
	     * @Title: savePricing   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String savePricing(CollectFeesModel model, UserDTO user)throws ServiceException;
	/**
	 * 取消划价
	     * @Title: cancel_pricing   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String cancel_pricing(CollectFeesModel model, UserDTO user)throws ServiceException;
	/**
	 * 划价收费
	     * @Title: cancel_pricing   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public CollectFeesResult savePricingCollectFees(CollectFeesModel model, UserDTO user, String invoice_class)throws ServiceException;
	/**
	 * 划价收费发送缴费申请
	     * @Title: pricingPaymentApplication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public  String pricingPaymentApplication(List<ExaminfoChargingItem> list,String examNum,UserDTO user) throws ServiceException;
	/**
	 * 划价查询项目列表
	     * @Title: getPricingWitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List getPricingWitemList(String exam_num, String is_fees_qijian,String center_num)throws ServiceException;
	/**
	 * 划价退费
	     * @Title: savePricingRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String savePricingRefund(CollectFeesModel model, UserDTO user)throws ServiceException;

	public List<ChargingWayDTO> getChargingWayByItemIds(String exam_num, String item_ids)throws ServiceException;
	//获取当前用户所有需要发送his申请的项目
	public List<ExaminfoChargingItem> getHisApplicationItemList(String exam_num, String center_num)throws ServiceException;
	
	public CollectFeesResult saveMergeChargeInfo(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException;
	/**
	 * 999 按照单位获取商户信息
	     * @Title: savePricingRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public CompanyAccountDTO getCompanyAccounthantInfoByCom_id(long com_id)throws ServiceException;
	/**
	 * 1000 保存商户信息
	     * @Title: savePricingRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveMerchantInfo(long com_id,UserDTO user)throws ServiceException;
	//商户充值
	public String saveRecharge(CollectFeesModel model, UserDTO user)throws ServiceException;
	//商户流水信息
	public List<CompanyAccountDetailDTO> getTransactionFlowList(String com_num,UserDTO user);
	/**
	 * 1003 商户流水信息开发票
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveInvoiceInfo(CollectFeesModel model, UserDTO user)throws ServiceException;
	/**
	 * 1004 商户发票信息
	     * @Title: getInvoiceInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public List<ChargingInvoiceSingleDTO> getInvoiceInfoList(CollectFeesModel model,UserDTO user)throws ServiceException;
	/**
	 * 100 商户作废发票信息
	     * @Title: deleteInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteInvoiceInfo(CollectFeesModel model, UserDTO user)throws ServiceException;
	//商户冻结/解冻
	public String editCompanyAccount(CollectFeesModel model, UserDTO user)throws ServiceException;
	
	//根据卡号获取套餐信息
	public CardExamSetItemDTO getCardExamSet(String card_num,String center_num)throws ServiceException;
	
	//根据卡号获取套餐项目信息
	public List<CardExamSetItemDTO> getCardExamCharing(String card_num,String center_num)throws ServiceException;
	
	//体检信息绑定会员卡
	public String updExamForCardnum(String card_num,String exam_num)throws ServiceException;
	
	public List<CardInfo> getCardInfo(String card_num)throws ServiceException;
	/**
	 * 收费保存信息
	     * @Title: saveCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public CollectFeesResult saveSettlements(CollectFeesModel model, UserDTO user, String invoice_class)
			throws ServiceException;
//	/**
//	 * 订单中项目信息
//	     * @Title: saveCollectFees   
//	     * @Description: TODO(这里用一句话描述这个方法的作用)   
//	     * @param: @param model
//	     * @param: @return
//	     * @param: @throws ServiceException      
//	     * @return: String      
//	     * @throws
//	 */
//	public List<ChargingDetailSingleDTO> getsettlementList(long summary_id);
	
	
	public void delSummaryId(long exam_id)throws ServiceException;
}
