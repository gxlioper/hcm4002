package com.hjw.charge.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hjw.charge.DTO.ChargingDetailSingleDTO;
import com.hjw.charge.DTO.ChargingInvoiceSingleDTO;
import com.hjw.charge.DTO.ChargingSummarySingleDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.CollectFeesResult;
import com.hjw.charge.DTO.InsuranceFeeDetailDTO;
import com.hjw.charge.DTO.InsuranceOpSignStatusDTO;
import com.hjw.charge.DTO.InsureAccountDTO;
import com.hjw.charge.DTO.InsureAccountDmDTO;
import com.hjw.charge.DTO.InvoiceInformationDTO;
import com.hjw.charge.DTO.PosTransListDTO;
import com.hjw.charge.domain.ChargingInvoiceSingleCharge;
import com.hjw.charge.domain.ChargingSummarySingleCharge;
import com.hjw.charge.domain.InsurancePayerInfoCharge;
import com.hjw.charge.domain.InvoiceInformationCharge;
import com.hjw.charge.model.CollectFeesModel;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExaminfoChargingItem;
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
public interface CollectFeesChargeService {
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
//	public List<CardInfoDTO> getCardListByexamId(long exam_id,String center_num) throws ServiceException;
	
	
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
	public List<ChargingInvoiceSingleDTO> getsingleInvoice(String chargingIds,String examNum) throws ServiceException;
	
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
	     * @Title: getChargingInvoiceSingleOfaccountnum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param account_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingInvoiceSingle      
	     * @throws
	 */
	public ChargingInvoiceSingleCharge getChargingInvoiceSingleOfaccountnum(String account_num,String center_num) throws ServiceException;
	
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
	

	public List<ChargingWayDTO> getChargingWayByItemIds(String exam_num, String item_ids)throws ServiceException;
	//获取当前用户所有需要发送his申请的项目
	public List<ExaminfoChargingItem> getHisApplicationItemList(String exam_num, String center_num)throws ServiceException;
	
	public CollectFeesResult saveMergeChargeInfo(CollectFeesModel model,UserDTO user,String invoice_class) throws ServiceException;
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
	public void delSummaryId(long exam_id)throws ServiceException;
	/**
	 * 查询发票信息
	     * @Title: saveTuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<InvoiceInformationDTO> getInformation(String infoClientName) throws ServiceException ;
	public String verifyMedicalItem(String item_codes, String medical_type) throws ServiceException ;
	/**
	 * 保存发票信息
	     * @Title: saveTuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveCompanyInformation(CollectFeesModel model, UserDTO user) throws ServiceException;
	public String updateCharge(String req_num,String bill_type,long userid,String invoice_num , String trade_no,String voucher_no ) throws ServiceException ;


	public String saveDaily(CollectFeesModel model, UserDTO user) throws ServiceException;
	/**
	 * 退费
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  updateChargeRefund(String req_num,List<ExamResultChargingItemDTO> listItem,String singleRefund) throws ServiceException ;
	/**
	 * 1003 查询该工作人员是否签到
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public List<InsuranceOpSignStatusDTO> findSignIn(long operatorId)throws ServiceException;
	/**
	 * 医保是否关联项目
	     * @Title: healthCareAssociation   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param peis_req_code
	     * @param: @param pay_class
	     * @param: @param nsurance_class
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardExamSetItemDTO      
	     * @throws
	 */
	public CollectFeesResult  healthCareAssociation(String peis_req_code,int pay_class,String nsurance_class,List<ExamResultChargingItemDTO> listitem  ) throws ServiceException ;
	/**
	 * 查询可以开处方的医生
	     * @Title: healthCareAssociation   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param peis_req_code
	     * @param: @param pay_class
	     * @param: @param nsurance_class
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CardExamSetItemDTO      
	     * @throws
	 */
	public List<JobDTO> getPrescription() throws ServiceException ;
	//重新签到后修改凭证号
	public String  updateCredentials(String req_num,UserDTO user,String patNo, String inter_class ) throws ServiceException ;
	/**
	 * 医保登记，医保收费成功入库失败时删除数据
	     * @Title: deleteCharge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  deleteCharge(String invoice_num,String req_nums);
	/**
	 * 查询是否经过市医保省医保收费
	     * @Title: findexamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<ChargingSummarySingleDTO> findexamSummary(String exam_num)throws ServiceException;
	/**
	 * 加流水表时查询数据
	     * @Title: selectPatno   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param req_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<InsuranceFeeDetailDTO> runningWater(String item_code,String inter_class)throws ServiceException;
	/**
	 * 查询patno
	     * @Title: selectPatno   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param req_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<ChargingSummarySingleDTO> selectPatno(String req_num) throws ServiceException;
	public List<InsureAccountDTO> queryWater(String req_num) throws ServiceException ;
	
	/**
	 * 更想发票表
	     * @Title: updateCIS   
	     * @Description: 
	     * @param: @param req_num
	     * @param: @param user
	     * @param: @param patNo
	     * @param: @param inter_class
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  updateCIS(long invoice_id,String invoice_num,String tax_invoices_num) throws ServiceException ;
	
	/**
	 * 
	     * @Title: getcardSaleSummary   
	     * @Description: 
	     * @param: @param account_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingInvoiceSingle      
	     * @throws
	 */
	public ChargingInvoiceSingleCharge getCISForAccountnum(String account_num)throws ServiceException ;
	/**
	 * 退费失败删除信息
	     * @Title: deleteRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String deleteRefund(CollectFeesModel model, UserDTO user)throws ServiceException;
	/**
	 * 保存发票信息
	     * @Title: saveTuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public ChargingSummarySingleCharge getChargingSummarySingle(String account_num)throws ServiceException;
	/**
	 * 读卡后保存医保卡信息
	     * @Title: saveTuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveInsurance(CollectFeesModel model, UserDTO user) throws ServiceException;
	/**
	 * 1003 查询个人社保编号
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String findPatNo(String exam_num)throws ServiceException;
	/**
	 * 1003 查询民族编号
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public long findNation(String data_name)throws ServiceException;
	
	/**
	 * 单项退费
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveTuifeiDanXiang(CollectFeesModel model, UserDTO user) throws ServiceException;
		
	/**
	 * 医保明细上传
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public List<String> uploadDetail(String req_num,String name,String cardNum)throws ServiceException;
	/**
	 * 保存收费表
	     * @Title: saveInsureAccount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param listitem
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  saveInsureAccount(List<InsureAccountDmDTO> listitem,CollectFeesModel model, UserDTO user) throws ServiceException ;
}
