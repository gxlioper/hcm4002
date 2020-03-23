package com.hjw.charge.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.charge.domain.InsureAccountCharge;
import com.hjw.util.DateTimeUtil;
//import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.charge.DTO.CashierDailyAccDTO;
import com.hjw.wst.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.charge.DTO.ChargingDetailSingleDTO;
import com.hjw.charge.DTO.ChargingInvoiceSingleDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.charge.DTO.PosDailySummaryDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.charge.DTO.WebCityQuerySendDTO;
import com.hjw.charge.DTO.WebCityReconciliationSendDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.charge.model.CashierDailyAccModel;
import com.hjw.charge.service.CashierDailyAccChargeService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


@SuppressWarnings("rawtypes")
public class CashierDailyAccAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private CashierDailyAccModel model = new CashierDailyAccModel();
	private CashierDailyAccChargeService cashierDailyAccService;
	private SyslogService syslogService;

	private int rows = 15; // easyui每页显示条数
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public CashierDailyAccModel getModel() {
		return model;
	}
	public void setModel(CashierDailyAccModel model) {
		this.model = model;
	}
	
	public CashierDailyAccChargeService getCashierDailyAccService() {
		return cashierDailyAccService;
	}
	public void setCashierDailyAccService(CashierDailyAccChargeService cashierDailyAccService) {
		this.cashierDailyAccService = cashierDailyAccService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	/**
	 * 1111 收费员日结功能页面
	     * @Title: getCashierDailyPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyPageCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setDaily_acc_date1(DateTimeUtil.getDate2());
		this.model.setDaily_acc_date2(DateTimeUtil.getDate2());
		this.model.setUserId(user.getUserid());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1111");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1112 查询收费员个人收费明细
	     * @Title: getCashierDailyAcclist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAcclistCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<CashierDailyAccListDTO> list = this.cashierDailyAccService.getCashierDailyAcclist(user.getUserid());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1113 查询收费员当天使用的收费方式
	     * @Title: getCashierChargingWay   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierChargingWayCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingWayDTO> list = this.cashierDailyAccService.getChargingWay(user.getUserid());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1114 查询收费员当天开票信息
	     * @Title: getCashierInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierInvoiceListCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingInvoiceSingleDTO> list = this.cashierDailyAccService.getCashierInvoiceList(user.getUserid());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1115 收费员日结保存信息
	     * @Title: saveCashierDailyacc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCashierDailyaccCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.cashierDailyAccService.saveCashierDailyacc(user);
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1115");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 1116 查询收费员团体未日结明细列表
	     * @Title: getCashierDailyListTu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyListTuCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<CashierDailyAccListDTO> list = this.cashierDailyAccService.getCashierDailyListTu(user.getUserid());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1117 根据日结日期查询收费员日结信息
	     * @Title: getCashierDailyAcc
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAccCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.cashierDailyAccService.getCashierDailyAcc(this.model.getDaily_acc_date1(), this.model.getDaily_acc_date2(), user.getUserid(), rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1118 根据日结号查询收费类型明细列表
	     * @Title: getCashierDailyAccPayway   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAccPaywayCharge() throws WebException{
		List<CashierDailyAccDTO> list = this.cashierDailyAccService.getCashierDailyAccPayway(this.model.getDaily_acc_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1119 根据日结号查询发票信息列表
	     * @Title: getCashierDailyAccInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAccInvoiceCharge() throws WebException{
		List<CashierDailyAccInvoiceDTO> list = this.cashierDailyAccService.getCashierDailyAccInvoice(this.model.getDaily_acc_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1120 根据日结号查询收费明细列表
	     * @Title: getCashierDailyAccDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAccDetailCharge() throws WebException{
		List<CashierDailyAccListDTO> list = this.cashierDailyAccService.getCashierDailyAccDetail(this.model.getDaily_acc_num(),this.model.getIs_Active());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 2010 收费员pos日结主页main
	     * @Title: posDailyPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String posDailyPageCharge(){
		
		UserDTO user = (UserDTO) session.get("username");
		this.model.setDaily_acc_date1(DateTimeUtil.getDate2());
		this.model.setDaily_acc_date2(DateTimeUtil.getDate2());
		this.model.setUserId(user.getUserid());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2010");//子功能id 必须填写
		sl.setExplain("pos 日结首页");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	/**
	 * 2011   收费员pos日结汇总
	     * @Title: getPosDailySummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getPosDailySummaryCharge(){
		UserDTO user = (UserDTO) session.get("username");
		List<PosDailySummaryDTO> list  = this.cashierDailyAccService.getPosDailySummary(this.model,user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 2012   收费员pos日结明细
	     * @Title: getposDailyDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getposDailyDetailListCharge(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page  = this.cashierDailyAccService.getposDailyDetailList(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 2013 保存pos日结信息
	     * @Title: savePosDailyInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String savePosDailyInfoCharge(){
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		result = this.cashierDailyAccService.savePosDailyInfo(this.model,user);
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 2014 pos 已日结明细
	     * @Title: getEndPosDailyDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getEndPosDailyDetailListCharge(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page  = this.cashierDailyAccService.getEndPosDailyDetailList(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}	
	
	/**
	 * 2015 pos 已日结总记录
	     * @Title: getendposdailySummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getendposdailySummaryCharge(){
		UserDTO user = (UserDTO) session.get("username");
		List<PosDailySummaryDTO> list = this.cashierDailyAccService.getendposdailySummary(this.model,user);
		this.outJsonResult(list);
		return  NONE;
	}
	/**
	 * 2020 市医保 对账
	     * @Title: cityReconciliationPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String cityReconciliationPageCharge(){
		
		UserDTO user = (UserDTO) session.get("username");
		this.model.setDaily_acc_date1(DateTimeUtil.getDate2());
		this.model.setDaily_acc_date2(DateTimeUtil.getDate2()+" 23:59:59.000");
		this.model.setUserId(user.getUserid());
		this.model.setUsername(user.getName());
		this.model.setWork_other_num(user.getWork_other_num());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2020");//子功能id 必须填写
		sl.setExplain("2市医保 对账");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 2021 收费员查询市医保个人收费
	     * @Title: getPersionCityReconciliation   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getPersionCityReconciliationCharge(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page  = this.cashierDailyAccService.getPersionCityReconciliation(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}	
//	/**
//	 * 2022 收费员查询会员卡收费
//	     * @Title: getCardCityReconciliation   
//	     * @Description: TODO(这里用一句话描述这个方法的作用)   
//	     * @param: @return
//	     * @param: @throws WebException      
//	     * @return: String      
//	     * @throws
//	 */
//	public String  getCardCityReconciliation(){
//		UserDTO user = (UserDTO) session.get("username");
//		PageReturnDTO page  = this.cashierDailyAccService.getCardCityReconciliation(this.model,user, rows, this.getPage());
//		this.outJsonResult(page);
//		return NONE;
//	}	
	/**
	 *2023 收费员查询市医保个人收费明细
	     * @Title: getPersionCityReconciliationDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getPersionCityReconciliationDetailListCharge(){
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingDetailSingleDTO> list = this.cashierDailyAccService.getPersionCityReconciliationDetailList(this.model,user);
		this.outJsonResult(list);
		return NONE;
	}	
	
	/**
	 *2024 收费员查询市医保会员卡收费明细
	     * @Title: getCardCityReconciliationDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
//	public String  getCardCityReconciliationDetailList(){
//		UserDTO user = (UserDTO) session.get("username");
//		List<CardSaleDetailDTO> list = this.cashierDailyAccService.getCardCityReconciliationDetailList(this.model,user);
//		this.outJsonResult(list);
//		return NONE;
//	}	
	
	/**
	 *2025 查询市医保收费总额
	     * @Title: getCityReconciliationSummaryAmount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public  String getCityReconciliationSummaryAmount(){
		UserDTO user = (UserDTO) session.get("username");
		CashierDailyAccDTO cashierDailyAccDTO = this.cashierDailyAccService.getCityReconciliationSummaryAmount(this.model,user);
		this.outJsonResult(cashierDailyAccDTO);
		return NONE;
	}
	
	
	/**
	 *2026 医保对账
	     * @Title: getCityReconciliationQuery   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public  String getCityReconciliationQueryCharge()throws SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String   str = this.cashierDailyAccService.getCityReconciliationQuery(this.model,user);
		this.outJsonStrResult(str);
		return NONE;
	}
	
	/**
	 *2027 医保对账明细申请
	     * @Title: getCityReconciliationQuery   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public  String getCityReconciliationQueryDetailCharge(){
		UserDTO user = (UserDTO) session.get("username");
		InsureAccountCharge insureAccountCharge = this.cashierDailyAccService.getCityReconciliationQueryDetail(this.model,user);
		this.outJsonResult(insureAccountCharge);
		return NONE;
	}
	
	/**
	 *2028 医保对账明细列表
	     * @Title: getInsureVerifyAccountDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public String getInsureVerifyAccountDetailListCharge(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page  = this.cashierDailyAccService.getInsureVerifyAccountDetailList(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
		
	}
	
	/**
	 *2029 医保校验交易是否能冲正
	     * @Title: checkCorrectTransaction   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String checkCorrectTransactionCharge(){
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.cashierDailyAccService.checkCorrectTransaction(this.model,user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *2030 医保  系统内对账查询
	     * @Title: getInsureAccountList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */

	public String getInsureAccountListCharge(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page =  this.cashierDailyAccService.getInsureAccountList(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 *2031 个人退费信息列表
	     * @Title: getPersionRefundlist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public String getPersionRefundlistCharge(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page =  this.cashierDailyAccService.getPersionRefundlist(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}

	//医保总额对账
	public String  getCityReconciliationSummaryAmountCharge(){
		UserDTO user = (UserDTO) session.get("username");
		InsureAccountCharge in =  this.cashierDailyAccService.getCityReconciliationSummaryAmountCharge(this.model,user);
		this.outJsonResult(in);
		return NONE;
	}
	
}
