package com.hjw.charge.action;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.charge.DTO.CashierDailyAccDTO;
import com.hjw.charge.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.charge.DTO.FinanceDepAccDTO;
import com.hjw.charge.DTO.FinanceDepAccPaywayDTO;
import com.hjw.charge.DTO.FinanceVsCashierDailyAccDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.charge.model.FinanceDepAccModel;
import com.hjw.charge.service.FinanceDepAccService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description: 财务部门日结功能类 
     * @author: dangqi     
     * @date:   2017年11月7日 上午11:11:57   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class FinanceDepAccAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private FinanceDepAccModel model = new FinanceDepAccModel();
	private FinanceDepAccService financeDepAccService;
	private SyslogService syslogService;

	private int rows = 15; // easyui每页显示条数
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public FinanceDepAccModel getModel() {
		return model;
	}

	public void setModel(FinanceDepAccModel model) {
		this.model = model;
	}
	public void setFinanceDepAccService(FinanceDepAccService financeDepAccService) {
		this.financeDepAccService = financeDepAccService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	/**
	 * 1121 财务部门日结功能页
	     * @Title: getFinanceDepAccPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFinanceDepAccPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setStart_date(DateTimeUtil.getDate2());
		this.model.setEnd_date(DateTimeUtil.getDate2());
		this.model.setUser_id(user.getUserid());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1121");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1122 查询收费员未财务日结信息列表
	     * @Title: getCashierDailyAccAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAccAll() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<CashierDailyAccDTO> list = this.financeDepAccService.getCashierDailyAccAll(user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1123 查询所有未财务日结的收费员日结总金额
	     * @Title: getCashierDailyAccSum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierDailyAccSum() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<FinanceDepAccDTO> list = this.financeDepAccService.getCashierDailyAccSum(user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1124 保存财务部门日结信息
	     * @Title: saveFinanceDepAcc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveFinanceDepAcc() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray payway = JSONArray.fromObject(this.model.getFinancePayways());
		List<FinanceDepAccPaywayDTO> paywaylist = (List<FinanceDepAccPaywayDTO>) JSONArray.toCollection(payway,FinanceDepAccPaywayDTO.class);
		this.model.setFinancePayway(paywaylist);
		
		JSONArray dailys = JSONArray.fromObject(this.model.getFinancecCashiers());
		List<FinanceVsCashierDailyAccDTO> dailyList = (List<FinanceVsCashierDailyAccDTO>) JSONArray.toCollection(dailys,FinanceVsCashierDailyAccDTO.class);
		this.model.setFinancecCashier(dailyList);
		this.financeDepAccService.saveFinanceDepAcc(model, user);
		this.message = "日结保存成功!";
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1124");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 1125 查询财务部门已日结信息列表
	     * @Title: getFinanceDepAccList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFinanceDepAccList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.financeDepAccService.getFinanceDepAccList(model, user.getCenter_num(),rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1126 根据财务部门日结号查询收费方式信息列表
	     * @Title: getFinanceDepAccPaywayList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFinanceDepAccPaywayList() throws WebException{
		List<FinanceDepAccPaywayDTO> list = this.financeDepAccService.getFinanceDepAccPaywayList(this.model.getFd_acc_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1127 根据财务部门日结号查询财务部门日结收费员日结信息列表
	     * @Title: getFinanceVsCashierDailyAccList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFinanceVsCashierDailyAccList() throws WebException{
		List<FinanceVsCashierDailyAccDTO> list = this.financeDepAccService.getFinanceVsCashierDailyAccList(this.model.getFd_acc_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1128 查询收费员日结发票信息列表
	     * @Title: getFinanceVsCashierInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getFinanceVsCashierInvoiceList() throws WebException{
		List<CashierDailyAccInvoiceDTO> list = this.financeDepAccService.getFinanceVsCashierInvoiceList(this.model.getDaily_status(), this.model.getFd_acc_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1129 收费信息记录实时查询页面
	     * @Title: getCharingSummaryPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCharingSummaryPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setStart_date(DateTimeUtil.getDate2());
		this.model.setEnd_date(DateTimeUtil.getDate2());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1129");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1130 查询实时收费员个人收费明细数据
	     * @Title: getChargingSummarySingleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingSummarySingleList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.financeDepAccService.getChargingSummarySingleList(model, rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1131 查询实时收费员团体收费明细数据
	     * @Title: getChargingSummaryGroupList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingSummaryGroupList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.financeDepAccService.getChargingSummaryGroupList(model, rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1132 查询实时收费员发票信息列表
	     * @Title: getChargingInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingInvoiceList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO page = this.financeDepAccService.getChargingInvoiceList(model, rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1133 查询实时收费员收费金额汇总信息
	     * @Title: getChargingSummarySum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingSummarySum() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<FinanceDepAccDTO> list = this.financeDepAccService.getChargingSummarySum(model,user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1138 财务月结页面
	     * @Title: getMonthlyStatementPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getMonthlyStatementPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setStart_date(DateTimeUtil.getMonthFirstDay(DateTimeUtil.parse()));
		this.model.setEnd_date(DateTimeUtil.getDate2());
		this.model.setUser_id(user.getUserid());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1138");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1139 获取时间段内财务日结汇总
	     * @Title: getMonthlyStatementSum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getMonthlyStatementSum() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<FinanceDepAccDTO> list = this.financeDepAccService.getMonthlyStatementSum(model, user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
}
