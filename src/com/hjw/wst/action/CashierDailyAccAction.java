package com.hjw.wst.action;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CashierDailyAccDTO;
import com.hjw.wst.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.PosDailySummaryDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CashierDailyAccModel;
import com.hjw.wst.service.CashierDailyAccService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


@SuppressWarnings("rawtypes")
public class CashierDailyAccAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private CashierDailyAccModel model = new CashierDailyAccModel();
	private CashierDailyAccService cashierDailyAccService;
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
	public void setCashierDailyAccService(CashierDailyAccService cashierDailyAccService) {
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
	public String getCashierDailyPage() throws WebException{
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
	public String getCashierDailyAcclist() throws WebException{
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
	public String getCashierChargingWay() throws WebException{
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
	public String getCashierInvoiceList() throws WebException{
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
	public String saveCashierDailyacc() throws WebException{
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
	public String getCashierDailyListTu() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<CashierDailyAccListDTO> list = this.cashierDailyAccService.getCashierDailyListTu(user.getUserid(),user.getCenter_num());
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
	public String getCashierDailyAcc() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.cashierDailyAccService.getCashierDailyAcc(this.model.getDaily_acc_date1(), this.model.getDaily_acc_date2(), user.getUserid(),user.getCenter_num(), rows, this.getPage());
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
	public String getCashierDailyAccPayway() throws WebException{
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
	public String getCashierDailyAccInvoice() throws WebException{
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
	public String getCashierDailyAccDetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<CashierDailyAccListDTO> list = this.cashierDailyAccService.getCashierDailyAccDetail(this.model.getDaily_acc_num(),this.model.getIs_Active(),user.getCenter_num());
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
	public String posDailyPage(){
		
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
	public String  getPosDailySummary(){
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
	public String getposDailyDetailList(){
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
	public String savePosDailyInfo(){
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
	public String  getEndPosDailyDetailList(){
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page  = this.cashierDailyAccService.getEndPosDailyDetailList(this.model,user, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}	
	
}
