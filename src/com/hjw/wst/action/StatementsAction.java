package com.hjw.wst.action;

import java.net.URLDecoder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleTTDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.DjtTotalDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.GroupSetDTO;
import com.hjw.wst.DTO.CompanyAccountDTO;
import com.hjw.wst.DTO.CompanyAccountDetailDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TeamAccountAdditionalDTO;
import com.hjw.wst.DTO.TeamAccountDTO;
import com.hjw.wst.DTO.TeamExaminfoChargingCEDTO;
import com.hjw.wst.DTO.TeamExaminfoChargingItemDTO;
import com.hjw.wst.DTO.TeamWayDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInvoiceDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.ChargingSummaryGroup;
import com.hjw.wst.domain.ChargingWayGroup;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.TeamAccount;
import com.hjw.wst.domain.TeamAccountAdditional;
import com.hjw.wst.domain.ChargingWaySingle;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.StatementsModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.SystemInformsService;
import com.hjw.wst.service.TeamAccountService;
import com.hjw.wst.service.UserInvoiceService;
import com.opensymphony.xwork2.ModelDriven;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.FEETermResMessage;
import com.hjw.webService.client.body.ResultHeader;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

@SuppressWarnings("rawtypes")
public class StatementsAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private CompanyService companyService;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private StatementsModel model = new StatementsModel();
	private CustomerInfoService customerInfoService;
	private BatchService batchService;
	private TeamAccountService teamAccountService;
    private ExamInfoUserDTO examInfoUser=new ExamInfoUserDTO();   
    private TeamAccountDTO teamAccount=new TeamAccountDTO(); 
    private String teamaccounturl="";   
    private SyslogService syslogService;
    private UserInvoiceService userInvoiceService;
    private ChargingInvoiceSingleTTDTO chargingInvoiceSingleTTDTO;    
    private SystemInformsService systemInformsService;
    private String sort;
	private String order;
    private String feeresourceflag="0";
	
	public void setSystemInformsService(SystemInformsService systemInformsService) {
		this.systemInformsService = systemInformsService;
	}
	public String getFeeresourceflag() {
		return feeresourceflag;
	}
	public void setFeeresourceflag(String feeresourceflag) {
		this.feeresourceflag = feeresourceflag;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public ChargingInvoiceSingleTTDTO getChargingInvoiceSingleTTDTO() {
		return chargingInvoiceSingleTTDTO;
	}
	public void setChargingInvoiceSingleTTDTO(ChargingInvoiceSingleTTDTO chargingInvoiceSingleTTDTO) {
		this.chargingInvoiceSingleTTDTO = chargingInvoiceSingleTTDTO;
	}

	public void setUserInvoiceService(UserInvoiceService userInvoiceService) {
		this.userInvoiceService = userInvoiceService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public String getTeamaccounturl() {
		return teamaccounturl;
	}

	public void setTeamaccounturl(String teamaccounturl) {
		this.teamaccounturl = teamaccounturl;
	}

	public ExamInfoUserDTO getExamInfoUser() {
		return examInfoUser;
	}

	public void setExamInfoUserDTO(ExamInfoUserDTO examInfoUser) {
		this.examInfoUser = examInfoUser;
	}

	public TeamAccountDTO getTeamAccount() {
		return teamAccount;
	}

	public void setTeamAccountDTO(TeamAccountDTO teamAccount) {
		this.teamAccount = teamAccount;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setTeamAccountService(TeamAccountService teamAccountService) {
		this.teamAccountService = teamAccountService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(StatementsModel model) {
		this.model = model;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * 
	 * @Title: examflowManager @Description: 10
	 * 团体结算主界面 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String statementsMain() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.setTeamaccounturl(this.customerInfoService.getCenterconfigByKey("TEAMACCOUNT_URL", user.getCenter_num()).getConfig_value().trim());
		
		try {
			this.model.setIs_show_team_settlement_toolbar(this.customerInfoService.getCenterconfigByKey("IS_SHOW_TEAM_SETTLEMENT_TOOLBAR", user.getCenter_num()).getConfig_value().trim());
		} catch (Exception e) {
			this.model.setIs_show_team_settlement_toolbar("N");
		}
		List<WebResrelAtionship> web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS026")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsovertype(1);
					}
				}
				if(web.get(i).getRes_code().equals("RS058")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsunovertype(1);
					}
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("10");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: statementswayMain   
	     * @Description: 团体结算结算发票维护 11   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String statementswayMain() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		String INVOICE_REPEAT_TYPE = "Y";
		try {
			INVOICE_REPEAT_TYPE = this.customerInfoService.getCenterconfigByKey("INVOICE_REPEAT_TYPE", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.model.setInvoiceRepeatType(INVOICE_REPEAT_TYPE);
		this.setTeamaccounturl(this.customerInfoService.getCenterconfigByKey("TEAMACCOUNT_URL", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("11");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		String IS_HIS_TERM_INTERFACE= this.customerInfoService.getCenterconfigByKey("IS_HIS_TERM_INTERFACE", user.getCenter_num()).getConfig_value().trim();
		if("Y".equals(IS_HIS_TERM_INTERFACE.toUpperCase())){
			return "hissuccess";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * 
	 * @Title: getTeamAccountForBatch @Description: 团体结算主表显示
	 * 456 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String getTeamAccountForBatch() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO pr = this.teamAccountService.getTeamAccountForBatch(this.model.getBatchid(), centernum,this.getPage(), 
				this.rows);
		this.outJsonResult(pr);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termaccountoneshow   
	     * @Description:团体结算的查询 488   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termaccountoneshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatchid() <= 0) {
			this.model.setBatch_name("未知");
		} else {			
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatchid());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
			this.chargingInvoiceSingleTTDTO=this.teamAccountService.findTeamAccountByAccNumAll(this.model.getBatchid(),this.model.getAcc_num(), user.getCenter_num());
		}
		//UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("488");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	
	/**
	 * 
	     * @Title: termaccountListshowshow   
	     * @Description:结算单查询 481   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termaccountListshowshow() throws WebException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());

		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatchid() <= 0) {
			this.model.setBatch_name("未知");
		} else {			
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatchid());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
			TeamAccount ta = new TeamAccount();
			ta = this.teamAccountService.loadTeamAccount(this.model.getId());
			this.model.setAcc_num(ta.getAcc_num());
			this.model.setAcc_date(ta.getAudit_date());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("481");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	 * @Title: termaccountListshow @Description: 团体结算细项列表页
	 * 457 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String termaccountListshow() throws WebException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		UserDTO user = (UserDTO) session.get("username");
		try {
		List<WebResrelAtionship> wrs=user.getWebResource();
		if(wrs!=null &&wrs.size()>0){
		for(WebResrelAtionship wr :wrs){
			if("RS024".equals(wr.getRes_code())){
				this.feeresourceflag="1";
				break;
			}
		}
		}
		}catch(Exception ex){
			
		}
		this.setTeamaccounturl(this.customerInfoService.getCenterconfigByKey("TEAMACCOUNT_URL", user.getCenter_num()).getConfig_value().trim());
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatchid() <= 0) {
			this.model.setBatch_name("未知");
		} else {			
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatchid());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
			TeamAccount ta = new TeamAccount();
			ta = this.teamAccountService.loadTeamAccount(this.model.getId());
			this.model.setAcc_num(ta.getAcc_num());
			this.model.setAcc_date(ta.getAudit_date());
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("457");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: termaccountadd   
	     * @Description: 团体结算增加结算页面 470   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termaccountadd() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.message = "error-未知单位";
		} else if (this.model.getBatchid() <= 0) {
			this.message = "error-未知批次任务";
		} else {
			Batch taover=new Batch();			
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if("1".equals(taover.getOverflag())){
				this.message = "error-批次任务已经封帐";
			}else{
			String acc_num = this.batchService.GetCreateID("balance_num", user.getCenter_num());
			TeamAccountDTO oldta = new TeamAccountDTO();
			oldta = this.teamAccountService.getTeamAccountForAccNum(acc_num, user.getCenter_num());
			if ((oldta != null) && (oldta.getAcc_num().length() > 0)) {
				this.message = "error-结算单号重复，操作不能继续";
			} else {
				String newdate = DateTimeUtil.getDateTime();
				TeamAccount ta = new TeamAccount();
				ta.setAcc_date(newdate);
				ta.setBatchid(this.model.getBatchid());
				ta.setAcc_num(acc_num);
				ta.setAcc_operator(user.getUserid());
				ta.setCenter_num(user.getCenter_num());
				ta.setAcc_stauts("N");
				ta.setBalance_status("N");
				ta.setAcc_name("普通结算");
				this.teamAccountService.saveTeamAccount(ta);
				this.model.setAcc_num(ta.getAcc_num());
				this.model.setAcc_date(newdate);
				message = "ok-结算单生成成功";
			}
			}
		}
		this.outJsonStrResult(message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("470");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	     * @Title: termaccountusershow   
	     * @Description: 显示人员查询列表页面 459 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termaccountusershow() throws WebException {
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
			return "commerror";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
				return "commerror";
			} else {
				UserDTO user = (UserDTO) session.get("username");
				TeamAccountDTO ta = new TeamAccountDTO();
				ta = this.teamAccountService.getTeamAccountForAccNum(this.model.getAcc_num(), user.getCenter_num());
				
				if ((ta != null) && (ta.getAcc_num().length() > 0)) {
					if ("Y".equals(ta.getAcc_stauts())) {
						this.message = "error-结算单号已经审核，操作失败";
						return "commerror";
					} else if ("Y".equals(ta.getBalance_status())) {
						this.message = "error-结算单号已经结帐，操作失败";
						return "commerror";
					} else {
						this.model.setTime1(DateTimeUtil.getDate2());
						this.model.setTime2(DateTimeUtil.getDate2());

						if (this.model.getCompany_id() <= 0) {
							this.model.setComname("未知");
						} else {
							CompanyInfo cif = new CompanyInfo();
							cif = companyService.findComByID(this.model.getCompany_id());
							this.model.setComname(cif.getCom_Name());
						}

						if (this.model.getBatchid() <= 0) {
							this.model.setBatch_name("未知");
						} else {
							Batch cif = new Batch();
							cif = this.batchService.getBatchByID(this.model.getBatchid());
							this.model.setBatch_name(cif.getBatch_name());
							this.model.setBatch_num(cif.getBatch_num());
						}
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("459");// 子功能id 必须填写
						sl.setExplain("");// 操作说明
						syslogService.saveSysLog(sl);
						return SUCCESS;
					}
				} else {
					message = "error-结算单号无效，操作失败";
					return "commerror";
				}
			}
		}
	}

	/**
	 * 
	 * @Title: teamAccountExamListShow @Description:
	 * 457 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String teamAccountExamListShow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO pr = this.teamAccountService.teamAccountExamListShow(this.model.getAcc_num(), centernum,
				this.getPage(),this.rows, this.sort,this.order,user);
		this.outJsonResult(pr);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termgrouplistshow   
	     * @Description: 团体结算获取分组信息 461 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termgrouplistshow() throws WebException, SQLException {
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getGroupList(this.model.getBatchid(), 10000,1);
		List<GroupInfoDTO> giList = new ArrayList<GroupInfoDTO>();
		if((batchlist!=null)&&(batchlist.getRows()!=null)&&(batchlist.getRows().size()>0)){
			for(int i=0;i<batchlist.getRows().size();i++){
				GroupInfoDTO gi = new GroupInfoDTO();
				gi = (GroupInfoDTO)batchlist.getRows().get(i);
				giList.add(gi);
			}
		}
		this.outJsonResult(giList);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termSetlistshow   
	     * @Description:termSetlistshow 462   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termSetlistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getGroupSetList(this.model.getGroup_id(), 10000,1,user.getCenter_num());
		List<GroupSetDTO> giList = new ArrayList<GroupSetDTO>();
		if((batchlist!=null)&&(batchlist.getRows()!=null)&&(batchlist.getRows().size()>0)){
			for(int i=0;i<batchlist.getRows().size();i++){
				GroupSetDTO gi = new GroupSetDTO();
				gi = (GroupSetDTO)batchlist.getRows().get(i);
				giList.add(gi);
			}
		}
		this.outJsonResult(giList);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termexamchargingitem   
	     * @Description: 团体结算获取体检人收费项目信息页面 460   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termexamchargingitem() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		examInfoUser=this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("460");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: termexamitemlistshow   
	     * @Description:团体结算人员结算细项明细页面 472   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termexamitemlistshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		examInfoUser=this.customerInfoService.getExamInfoUserForExamNum(this.model.getExam_num(),user.getCenter_num());		
//		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("472");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: teamaccountwayadd   
	     * @Description:团体结算支付方式增加 484  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String teamaccountwayadd() throws WebException {	
		UserDTO user = (UserDTO) session.get("username");
		this.model.setCharges(this.teamAccountService.getTeamWayCount(this.model.getBatchid(),this.model.getAccount_num(), user.getCenter_num()));
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("484");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	     * @Title: termExamInfoUserList   
	     * @Description: 团体结算获取体检人员信息 463
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termExamInfoUserList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		int isnotpay=1;//包含弃检所有   0不包含弃检项目所有项目，1表示包含弃检，含已检查项目  2表示只包含已检项目 3 包含弃检和已检的项目
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 5)) {
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}
			
			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}
			
			if (this.model.getChkItem().trim().indexOf("com_Name") < 0) {
				this.model.setCompany_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setCustomer_type_id("");
			}
			
			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}
			
			if (this.model.getChkItem().trim().indexOf("tjlx") < 0) {
				this.model.setTjlxs("");
			}
			
			if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
				this.model.setTime1("");
				this.model.setTime2("");
			}
			
			if ((this.model.getChkItem().trim().indexOf("isnotpay") < 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") < 0)) {
				isnotpay=0;// 不包含弃检项目所有项目
			}
			
			if ((this.model.getChkItem().trim().indexOf("isnotpay") >= 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") < 0)) {
				isnotpay=1;// 包含弃检项目所有项目
			}
			
			if ((this.model.getChkItem().trim().indexOf("isnotpay") < 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") >= 0)) {
				isnotpay=2;// 不包含弃检+已检
			}
			
			if ((this.model.getChkItem().trim().indexOf("isnotpay") >= 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") >= 0)) {
				isnotpay=3;// 包含弃检+已检
			}
			contractlist = this.teamAccountService.getTermExamInfoUserList(this.model.getCompany_id(), this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(),this.model.getTime1(),this.model.getTime2(), this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),this.model.getCustomer_type_id(),isnotpay,this.model.getLevels(),this.model.getTjlxs(), user.getUserid(), centernum, 
					this.rows,this.getPage(),this.sort,this.order,user,user.getCenter_num());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: setTeamAccountListall   
	     * @Description: 团体结算保存所有满足条件的信息 844   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccountListall() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			int isnotpay = 1;
			if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 0)) {
				if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
					this.model.setExam_num("");
				}
				if (this.model.getChkItem().trim().indexOf("custname") < 0) {
					this.model.setCustname("");
				}
				if (this.model.getChkItem().trim().indexOf("sex") < 0) {
					this.model.setSex("");
				}

				if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
					this.model.setGroup_id(0);
				}

				if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
					this.model.setSet_id(0);
				}

				if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
					this.model.setExam_status("");
				}

				if (this.model.getChkItem().trim().indexOf("com_Name") < 0) {
					this.model.setCompany_id(0);
				}

				if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
					this.model.setCustomer_type_id("");
				}

				if (this.model.getChkItem().trim().indexOf("levels") < 0) {
					this.model.setLevels("");
				}
				
				if (this.model.getChkItem().trim().indexOf("tjlx") < 0) {
					this.model.setTjlxs("");
				}
				
				if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
					this.model.setTime1("");
					this.model.setTime2("");
				}

				if ((this.model.getChkItem().trim().indexOf("isnotpay") < 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") < 0)) {
					isnotpay=0;// 不包含弃检项目所有项目
				}
				
				if ((this.model.getChkItem().trim().indexOf("isnotpay") >= 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") < 0)) {
					isnotpay=1;// 包含弃检项目所有项目
				}
				
				if ((this.model.getChkItem().trim().indexOf("isnotpay") < 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") >= 0)) {
					isnotpay=2;// 不包含弃检+已检
				}
				
				if ((this.model.getChkItem().trim().indexOf("isnotpay") >= 0)&&(this.model.getChkItem().trim().indexOf("isyesitem") >= 0)) {
					isnotpay=3;// 包含弃检+已检
				}
				
				int results = this.teamAccountService.setTTermExamInfoUserListAll(this.model.getAcc_num(),
						this.model.getCompany_id(), this.model.getBatchid(), this.model.getExam_num(),
						this.model.getGroup_id(),this.model.getTime1(),this.model.getTime2(), this.model.getExam_status(), this.model.getSex(),
						this.model.getCustname(), this.model.getSet_id(), this.model.getCustomer_type_id(), isnotpay,
						this.model.getLevels(),this.model.getTjlxs(), user.getUserid(), centernum);
				if (results == 1) {
					this.message = "ok-存储过程执行成功";
				} else {
					this.message = "error-存储过程执行失败";
				}
			} else {
				this.message = "error-无效查询条件";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termExamInfoUserListts   
	     * @Description: 团体结算获取体检人员信息 特殊 816
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termExamInfoUserListts() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		int isnotpay=1;
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 5)) {
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}
			
			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}
			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}
			
			if (this.model.getChkItem().trim().indexOf("com_Name") < 0) {
				this.model.setCompany_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setCustomer_type_id("");
			}
			
			if (this.model.getChkItem().trim().indexOf("tjlx") < 0) {
				this.model.setTjlxs("");
			}
			
			if (this.model.getChkItem().trim().indexOf("isnotpay") < 0) {
				isnotpay=0;
			}
			contractlist = this.teamAccountService.getTermExamInfoUserListts(this.model.getCompany_id(), this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(), this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),this.model.getCustomer_type_id(),isnotpay,this.model.getLevels(),this.model.getTjlxs(), user.getUserid(), centernum, 10000, 1,user.getCenter_num());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: setTeamAccountList   
	     * @Description: 团体结算插入明细 464   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccountList() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				// 获取体检套餐
				String setentities = model.getSetentities();
				setentities = setentities.replace("}{", "},{");
				setentities = "[" + setentities + "]";
				JSONArray setarray = JSONArray.fromObject(setentities);
								
				@SuppressWarnings("unchecked")
				List<ExamInfoUserDTO> listset = (List<ExamInfoUserDTO>) JSONArray.toCollection(setarray,
						ExamInfoUserDTO.class);
				this.message = this.teamAccountService.setTeamAccountList(this.model.getAcc_num(), listset,
						user.getUserid(), user.getCenter_num());
				this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(), user.getCenter_num());
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("464");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-操作完成";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: setTeamAccounttsList   
	     * @Description: 团体特殊人员结算 806   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccounttsList() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				// 获取体检套餐				
				if(StringUtil.isEmpty(model.getSetentities()))
				{
					this.message = "error-无效人员";
				}else{
					if(StringUtil.isEmpty(model.getChargentities()))
					{
						this.message = "error-无效缴费项目";
					} else {
						String chargitems = this.model.getChargentities();
						chargitems = chargitems.replace("}{", "},{");
						chargitems = "[" + chargitems + "]";
						JSONArray setarrayitem = JSONArray.fromObject(chargitems);
						@SuppressWarnings("unchecked")
						List<ExaminfoChargingItemDTO> listitem = (List<ExaminfoChargingItemDTO>) JSONArray.toCollection(setarrayitem,
								ExaminfoChargingItemDTO.class);						
						
						String setentities = model.getSetentities();
						setentities = setentities.replace("}{", "},{");
						setentities = "[" + setentities + "]";
						JSONArray setarray = JSONArray.fromObject(setentities);
						@SuppressWarnings("unchecked")
						List<ExamInfoUserDTO> listset = (List<ExamInfoUserDTO>) JSONArray.toCollection(setarray,
								ExamInfoUserDTO.class);
						this.message = this.teamAccountService.setTeamAccounttsList(this.model.getAcc_num(), listset,listitem,
								user.getUserid(), user.getCenter_num());
						this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(), user.getCenter_num());
						// UserDTO user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("464");// 子功能id 必须填写
						sl.setExplain(this.model.getAcc_num());// 操作说明
						syslogService.saveSysLog(sl);
						this.message = "ok-操作完成";
					}
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: teamItemCount   
	     * @Description: 团体结算统计 467 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String teamItemCount() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		DjtTotalDTO dd = new DjtTotalDTO();
	    dd = this.teamAccountService.termItemCount(this.model.getAcc_num(),user.getCenter_num());		
		this.outJsonResult(dd);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: setTeamAccountcountdo   
	     * @Description: 团体结算统计入账 468   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccountcountdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				this.message = this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(),
						user.getCenter_num());
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("468");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num());//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: delTeamAccountForAccNumdo   
	     * @Description: 删除结算单 469   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String delTeamAccountForAccNumdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				this.message = this.teamAccountService.delTeamAccountForAccNum(this.model.getAcc_num(),
						user.getCenter_num());
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("469");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num());//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
    /**
     * 
         * @Title: teamdelexamnumdo   
         * @Description: 团体结算删除结算人员 471   
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String teamdelexamnumdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效体检编号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				this.message = this.teamAccountService.delTeamExamForAccNum(this.model.getAcc_num(),
						this.model.getIds(), user.getCenter_num());
				this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(), user.getCenter_num());
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("471");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num());//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamNum   
	     * @Description:团体结算人员结算细项明细 473   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminfoChargingItemforExamNum()throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<TeamExaminfoChargingItemDTO> es = new ArrayList<TeamExaminfoChargingItemDTO>();
		es = this.teamAccountService.getExaminfoChargingItemforExamNum(this.model.getAcc_num(),this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(es);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: TeamItemCount   
	     * @Description: 团体结算人员结算细项明细统计 474   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String teamexamItemCount() throws WebException, SQLException {
		DjtTotalDTO dd = new DjtTotalDTO();
		UserDTO user = (UserDTO) session.get("username");
		dd = this.teamAccountService.getTeamItemCount(this.model.getAcc_num(),this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(dd);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termdecchargesshow   
	     * @Description: 团体结算减免操作页面 475   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termdecchargesshow() throws WebException, SQLException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());

		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatchid() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatchid());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("475");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: teamnoexamshow   
	     * @Description: 未检查人员查询页面 489  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String teamnoexamshow() throws WebException, SQLException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());

		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatchid() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatchid());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("489");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: gettermatotleClistGrid   
	     * @Description: 团体结算减免按照项目减免统计 476   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String gettermatotleClistGrid() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		es = this.teamAccountService.getExaminfoChargingItemforC(this.model.getAcc_num(),user.getCenter_num(),this.sort,this.order);
		//UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("476");//子功能id 必须填写
		sl.setExplain(this.model.getAcc_num());//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(es);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: gettermatotleClistGrid   
	     * @Description: 未检查项目列表491
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String nottermaCList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		es = this.teamAccountService.getnoExaminfoforC(this.model.getCompany_id(),this.model.getBatchid(),user.getCenter_num());
		this.outJsonResult(es); 
		return NONE; 
	}

	/**
	 * 
	     * @Title: gettermatotleClistGrid   
	     * @Description: 未检查项目对应人员列表1280
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String nottermaCExamInfoList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		es = this.teamAccountService.getnoExaminfoforCExamInfo(this.model.getCompany_id(),this.model.getBatchid(),this.model.getChargitemid(),user.getCenter_num());
		this.outJsonResult(es); 
		return NONE; 
	}
	
	/**
	 * 
	     * @Title: gettermatotleClistGrid   
	     * @Description: 未检查人员列表490 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String nottermaEList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		es = this.teamAccountService.getnoExaminfoforE(this.model.getCompany_id(),this.model.getBatchid(),user.getCenter_num());
		this.outJsonResult(es); 
		return NONE; 
	}
	
	/**
	 * 
	     * @Title: gettermatotleClistGrid   
	     * @Description: 未检查人员对应项目列表1281
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String nottermaEChargItemList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		es = this.teamAccountService.nottermaEChargItemList(this.model.getCompany_id(),this.model.getBatchid(),this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(es); 
		return NONE; 
	}
	
	/**
	 * 
	     * @Title: gettermatotleClistGrid   
	     * @Description: 团体结算减免按照人员减免统计 477 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String gettermatotleElistGrid() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO webrole = new PageReturnDTO();
		webrole = this.teamAccountService.getExaminfoChargingItemforE(this.model.getAcc_num(),user.getCenter_num(),this.sort,this.order,this.getPage(), 
				this.rows);
		this.outJsonResult(webrole);
		return NONE;
	}

	/**
	 * 
	     * @Title: termdecamtCEdo   
	     * @Description: 团体结算减免执行 478
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termdecamtCEdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getExam_status())) {
			this.message = "error-无效操作";
		} else if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else if (this.model.getDeccharges() <= 0) {
			this.message = "error-无效减免金额";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				TeamAccountDTO tad = new TeamAccountDTO();
				tad = this.teamAccountService.getTeamAccountForAccNum(this.model.getAcc_num(), user.getCenter_num());
				if (tad == null) {
					this.message = "error-无效结算单号";
				} else if ("Y".equals(tad.getAcc_stauts())) {
					this.message = "error-结算单号已审,不能操作";
				} else if ("Y".equals(tad.getBalance_status())) {
					this.message = "error-结算单号已结账,不能操作";
				} else {
					String setentities = model.getSetentities();
					setentities = setentities.replace("}{", "},{");
					setentities = "[" + setentities + "]";
					JSONArray setarray = JSONArray.fromObject(setentities);
					@SuppressWarnings("unchecked")
					List<TeamExaminfoChargingCEDTO> listset = (List<TeamExaminfoChargingCEDTO>) JSONArray
							.toCollection(setarray, TeamExaminfoChargingCEDTO.class);
					if ("C".equals(this.model.getExam_status())) {
						double totalamt = 0;
						for (TeamExaminfoChargingCEDTO tc : listset) {
							totalamt = totalamt + tc.getAmount();
						}
						if (totalamt < this.model.getDeccharges()) {
							this.message = "error-所选项目金额不足";
						} else {
							this.teamAccountService.searchteamdefamtC(listset, this.model.getAcc_num(),
									this.model.getDeccharges(), user.getCenter_num());
							this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(),
									user.getCenter_num());
							this.message = "ok-操作完成";
						}
					} else if ("E".equals(this.model.getExam_status())) {
						double totalamt = 0;
						for (TeamExaminfoChargingCEDTO tc : listset) {
							totalamt = totalamt + tc.getAmount();
						}
						if (totalamt < this.model.getDeccharges()) {
							this.message = "error-所选项目金额不足";
						} else {
							this.teamAccountService.searchteamdefamtE(listset, this.model.getAcc_num(),
									this.model.getDeccharges(), user.getCenter_num());
							this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(),
									user.getCenter_num());
							//UserDTO user = (UserDTO) session.get("username");
							SysLog sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("478");//子功能id 必须填写
							sl.setExplain(this.model.getAcc_num());//操作说明
							syslogService.saveSysLog(sl);
							this.message = "ok-操作完成";
						}
					}else if ("P".equals(this.model.getExam_status())) {
						boolean totalamtflag = true;
						String  amtstr="";
						for (TeamExaminfoChargingCEDTO tc : listset) {
							if(tc.getAmount()<this.model.getDeccharges()){
								totalamtflag=false;
								amtstr=tc.getExam_num()+"金额不够减免，请重新选择";
								break;
							}
						}
						if (!totalamtflag) {
							this.message = "error-"+amtstr;
						} else {
							this.teamAccountService.searchteamdefamtP(listset, this.model.getAcc_num(),
									this.model.getDeccharges(), user.getCenter_num());
							this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(),
									user.getCenter_num());
							//UserDTO user = (UserDTO) session.get("username");
							SysLog sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("478");//子功能id 必须填写
							sl.setExplain(this.model.getAcc_num());//操作说明
							syslogService.saveSysLog(sl);
							this.message = "ok-操作完成";
						}
					} else {
						this.message = "error-操作无效";
					}
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: checkTeamAccountForAccNumdo   
	     * @Description: 团体结算结算单审核状态修改 479   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String checkTeamAccountForAccNumdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			TeamAccountDTO tad = new TeamAccountDTO();
			tad = this.teamAccountService.getTeamAccountForAccNum(this.model.getAcc_num(), user.getCenter_num());
			if (tad == null) {
				this.message = "error-无效结算单号";
			} else if ("Y".equals(tad.getBalance_status())) {
				this.message = "error-结算单号已结账,不能操作";
			} else {
				this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(), user.getCenter_num());
				TeamAccount ta = this.teamAccountService.findTeamAccountByAccNum(this.model.getAcc_num(),user.getCenter_num());
				if ("Y".equals(ta.getAcc_stauts())) {
					ta.setAcc_stauts("N");
					ta.setAcc_operator(user.getUserid());
					ta.setAcc_date(DateTimeUtil.getDateTime());
					this.teamAccountService.updateTeamAccount(ta);
					String userids="0";
					try{						
						userids = customerInfoService.getCenterconfigByKey("TEAMACC_SENDMESSAGE_USERIDS","000").getConfig_value();
					}catch(Exception ex){}
					if(!"0".equals(userids)){
						 BatchDTO b= batchService.getBatchDTOById(this.model.getBatchid()+"");
						String notices="【"+b.getCom_num()+"】"+b.getCom_name()+"---【"+b.getBatch_num()+"】"+b.getBatch_name()+"结算单号："+ta.getAcc_num()+"已经取消审核，无需结帐！";
						systemInformsService.sendSysInfo(notices,user.getUserid(),userids);
					}
				} else if ("N".equals(ta.getAcc_stauts())) {
					ta.setAcc_stauts("Y");
					ta.setAcc_operator(user.getUserid());
					ta.setAcc_date(DateTimeUtil.getDateTime());
					this.teamAccountService.updateTeamAccount(ta);
					String userids="0";
					try{						
						userids = customerInfoService.getCenterconfigByKey("TEAMACC_SENDMESSAGE_USERIDS","000").getConfig_value();
					}catch(Exception ex){}
					if(!"0".equals(userids)){
						 BatchDTO b= batchService.getBatchDTOById(this.model.getBatchid()+"");
						String notices="【"+b.getCom_num()+"】"+b.getCom_name()+"---【"+b.getBatch_num()+"】"+b.getBatch_name()+"结算单号："+ta.getAcc_num()+"已经审核，可以结帐！";
						systemInformsService.sendSysInfo(notices,user.getUserid(),userids);
					}					
				}
				
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("479");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-操作完成";
			}
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateteamaccountdo   
	     * @Description:团体结算保存发票 487   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateteamaccountdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			TeamAccountDTO tad = new TeamAccountDTO();
			tad = this.teamAccountService.getTeamAccountForAccNum(this.model.getAcc_num(), user.getCenter_num());
			if (tad == null) {
				this.message = "error-无效结算单号";
			} else if ("Y".equals(tad.getBalance_status())) {
				this.message = "error-结算单号已结账,不能操作";
			} else {
				TeamAccount ta = this.teamAccountService.findTeamAccountByAccNum(this.model.getAcc_num(),user.getCenter_num());
					ta.setInvoice_name(this.model.getInvoice_name());
					ta.setInvoice_no(this.model.getInvoice_no());
					ta.setLinker(this.model.getLinker());
					ta.setPhone(this.model.getPhone());
					ta.setAcc_date(DateTimeUtil.getDateTime());
				this.teamAccountService.updateTeamAccount(ta);
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("487");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-操作完成";
			}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: overTeamAccountForAccNumdo   
	     * @Description:团体结算结算单结算状态修改 480   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String overTeamAccountForAccNumdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			TeamAccountDTO tad = new TeamAccountDTO();
			tad = this.teamAccountService.getTeamAccountForAccNum(this.model.getAcc_num(), user.getCenter_num());
			if (tad == null) {
				this.message = "error-无效结算单号";
			} else {
				this.teamAccountService.setTeamAccountcountdo(this.model.getAcc_num(), user.getCenter_num());
				TeamAccount ta = this.teamAccountService.findTeamAccountByAccNum(this.model.getAcc_num(),user.getCenter_num());
				if ("Y".equals(ta.getBalance_status())) {
					ta.setBalance_status("N");
					ta.setBalancer((int) user.getUserid());
					ta.setBalance_date(DateTimeUtil.getDateTime());
					this.teamAccountService.updateTeamAccount(ta);
					//UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("480");//子功能id 必须填写
					sl.setExplain(this.model.getAcc_num());//操作说明
					syslogService.saveSysLog(sl);
					this.message = "ok-操作完成";
				} else if ("N".equals(ta.getBalance_status())) {
					if (!"Y".equals(ta.getAcc_stauts())) {
						this.message = "error-结算单号未结算审核，不能结账";
					} else {
						ta.setBalance_status("Y");
						ta.setBalancer((int) user.getUserid());
						ta.setBalance_date(DateTimeUtil.getDateTime());
						this.teamAccountService.updateTeamAccount(ta);
						//UserDTO user = (UserDTO) session.get("username");
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("480");//子功能id 必须填写
						sl.setExplain(this.model.getAcc_num());//操作说明
						syslogService.saveSysLog(sl);
						this.message = "ok-操作完成";
					}

				}
			}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: teamAccountExamwayListShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String teamAccountExamwayListShow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO webrole = new PageReturnDTO();
		webrole = this.teamAccountService.teamAccountExamway(this.model.getAcc_num(),1,100000);
		this.outJsonResult(webrole);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: teamaccountwayadddo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String teamaccountwayadddo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getId() <= 0) {
			this.message = "error-无效结帐单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				if((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)||(this.model.getNote()==null)||(this.model.getNote().trim().length()<=0))
				{
					this.message = "error-收费方式和金额为空";
				}else{
					String[] ids=this.model.getIds().split(",");
					String[] amts=this.model.getNote().split(",");
					if(ids.length!=amts.length){
						this.message = "error-收费方式和金额个数不匹配";
					}else{
						this.message=this.teamAccountService.saveTeamAccountGroup(this.model.getId(),this.model.getAccount_num(),ids,amts,user.getUserid());
						//UserDTO user = (UserDTO) session.get("username");
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("483");//子功能id 必须填写
						sl.setExplain(this.model.getAcc_num());//操作说明
						syslogService.saveSysLog(sl);
					}
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: teamaccountfpadddo   
	     * @Description: 团体结算支付方式删除 486   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String teamaccountwaydel() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getId() <= 0) {
			this.message = "error-无效支付记录";
		} else {
			ChargingSummaryGroup csg = this.teamAccountService
					.getChargingSummaryGroupOfaccountnum(this.model.getAccount_num());
			if (csg == null) {
				this.message = "error-无效记录";
			} else if (csg.getReceiv_status() == 1) {
				this.message = "error-已经确认付款，不能删除结帐方式";
			} else {
				ChargingWayGroup ta = new ChargingWayGroup();
				ta = this.teamAccountService.loadTeamAccountGroup(this.model.getId());
				if (ta.getId() <= 0) {
					this.message = "error-无效支付记录";
				} else if (ta.getId() <= 0) {
					this.message = "error-无效支付记录";
				} else {
					Batch taover = new Batch();
					taover = this.batchService.getBatchByID(this.model.getBatchid());
					if ("1".equals(taover.getOverflag())) {
						this.message = "error-批次任务已经封帐";
					} else {
						DataDictionary dataDictionary =	this.teamAccountService.getDataDictionaryById(Long.parseLong(ta.getCharging_way()));
						if(dataDictionary != null && "372".equals(dataDictionary.getData_code_children())){
							String mes = 	this.teamAccountService.delDrawAccounts(csg,user,ta.getId());
							if(mes.split("-")[0].equals("ok")){
								this.teamAccountService.delTeamAccountWay(ta);
								this.message = "ok-删除成功";
							}else{
								this.message = mes;
							}
						}else{
							this.teamAccountService.delTeamAccountWay(ta);
							this.message = "ok-删除成功";
						}
						
						
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("486");// 子功能id 必须填写
						sl.setExplain(this.model.getId() + "");// 操作说明
						syslogService.saveSysLog(sl);
					}
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: overTeamAccountForbatch   
	     * @Description: 团体结算封装操作 492   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String overTeamAccountForbatch() throws WebException {
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatchid()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatchid());
			if("1".equals(ta.getOverflag())){
				this.message = "error-批次已经被封帐";
			}else{
				ta.setOverflag("1");
				ta.setAccountflag(1);
				this.batchService.updateBatch(ta);
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("492");//子功能id 必须填写
				sl.setExplain(this.model.getBatchid()+"");//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-执行封帐操作成功";
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: overTeamAccountForbatch   
	     * @Description: 团体结算解除封装操作 493   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String unoverTeamAccountForbatch() throws WebException {
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatchid()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatchid());
			if("0".equals(ta.getOverflag())){
				this.message = "error-批次已经被解除封帐";
			}else{
				ta.setOverflag("0");
				ta.setAccountflag(0);
				this.batchService.updateBatch(ta);
				this.message = "ok-执行解除封帐操作成功";
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("493");//子功能id 必须填写
				sl.setExplain(this.model.getBatchid()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: statemengprintfa   
	     * @Description: 团体结算打印发票主页面  802
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String statemengprintfa() throws WebException {	
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		if(userInvoice != null){
			String num_min=userInvoice.getInvoice_num_min();
			String num_max=userInvoice.getInvoice_num_max();
			if(userInvoice.getInvoice_num_used() == null){
				this.model.setInvoice_no(userInvoice.getInvoice_num_min());
			}else{
				this.model.setInvoice_no(com.hjw.util.StringUtil.addOne(userInvoice.getInvoice_num_used()));
			}
		}
		Batch batch = new Batch();
		batch = this.batchService.getBatchByID(this.model.getBatchid());
    	if((batch!=null)&&(batch.getId()>0)){
    		ContractDTO ct = new ContractDTO();
    		this.model.setComname(batch.getInvoice_title());
    	}
    	ChargingSummaryGroup cis= new ChargingSummaryGroup();
    	cis=this.teamAccountService.getChargingSummaryGroupOfaccountnum(this.model.getAccount_num());
    	double amount = cis.getAmount2()+cis.getAdditional();
    	this.model.setCharges(amount);
    	this.model.setLinker(user.getUsername());
    	this.model.setId(cis.getId());
    	this.model.setBatchid(cis.getBatch_id());    	
    	DecimalFormat   df   =   new   DecimalFormat( "#,##0.00 ");
    	//保留两位小数且不用科学计数法，并使用千分位
    	String str=   df.format(amount);
    	this.model.setChargentities(str);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("802");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
		
	}
	
	/**
	 * 
	     * @Title: termaccountusertsshow   
	     * @Description: 团体结算打印发票主页面 805 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termaccountusertsshow() throws WebException {
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
			return "commerror";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
				this.outJsonStrResult(this.message);
				return "commerror";
			} else {
				UserDTO user = (UserDTO) session.get("username");
				TeamAccountDTO ta = new TeamAccountDTO();
				ta = this.teamAccountService.getTeamAccountForAccNum(this.model.getAcc_num(), user.getCenter_num());
				if ((ta != null) && (ta.getAcc_num().length() > 0)) {
					if ("Y".equals(ta.getAcc_stauts())) {
						message = "error-结算单号已经审核，操作失败";
						return "commerror";
					} else if ("Y".equals(ta.getBalance_status())) {
						message = "error-结算单号已经结帐，操作失败";
						return "commerror";
					} else {

						this.model.setTime1(DateTimeUtil.getDate2());
						this.model.setTime2(DateTimeUtil.getDate2());

						if (this.model.getCompany_id() <= 0) {
							this.model.setComname("未知");
						} else {
							CompanyInfo cif = new CompanyInfo();
							cif = companyService.findComByID(this.model.getCompany_id());
							this.model.setComname(cif.getCom_Name());
						}

						if (this.model.getBatchid() <= 0) {
							this.model.setBatch_name("未知");
						} else {
							Batch cif = new Batch();
							cif = this.batchService.getBatchByID(this.model.getBatchid());
							this.model.setBatch_name(cif.getBatch_name());
							this.model.setBatch_num(cif.getBatch_num());
						}
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("459");// 子功能id 必须填写
						sl.setExplain("");// 操作说明
						syslogService.saveSysLog(sl);
						return SUCCESS;
					}
				} else {
					message = "error-结算单号无效，操作失败";
					return "commerror";
				}
			}
		}
	}
	
	/**
	 * 
	     * @Title: termaccountinvoidadd   
	     * @Description: 团体登记发票结算 807  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 *//*
	public String termaccountinvoidadd() throws WebException {
		if(this.model.getBatchid()<=0){
			this.message = "error-批次任务编号不存在";
		}else if((this.model.getIds()==null)||(this.model.getIds().length()<=0)){
			this.message = "error-无效结算单号";
		}else{
			Batch taover=new Batch();			
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if("1".equals(taover.getOverflag())){
				this.message = "error-批次任务已经封帐";
			}else{			
			UserDTO user = (UserDTO) session.get("username");
			this.collectFeesService.saveSingleInviockTT(this.model.getBatchid(),this.model.getIds(),user.getUserid(),user.getCenter_num(),model.getPrices());
				message = "ok-结算单生成功";
			}
		}
		this.outJsonStrResult(message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("807");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}*/
	
	/**
	 * 
	     * @Title: getTeamAccountinvoidForBatch   
	     * @Description: 批次获取发票信息 808 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getTeamAccountinvoidForBatch() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO pr = this.teamAccountService.getTeamAccountinvoidForBatch(this.model.getBatchid(), centernum,this.getPage(), 
				this.rows);
		this.outJsonResult(pr);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: statementjsdlist   
	     * @Description: 选择结算单 809   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String statementjsdlist() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: statementjsdlistshow   
	     * @Description: statementjsdlistshow 810   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String statementjsdlistshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO pr = this.teamAccountService.getTeamAccountForBatchjs(this.model.getBatchid(), centernum,1,100000);
		this.outJsonResult(pr);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getTeamAccountForBatchfplist   
	     * @Description: 团体结算发票管理结算单 811   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getTeamAccountForBatchfplist() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO pr = this.teamAccountService.getTeamAccountForFp(this.model.getAccount_num(), centernum,this.getPage(), 
				this.rows);
		this.outJsonResult(pr);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateTeamAccountForIddo   
	     * @Description: 团体结算修改说明 815   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateTeamAccountForIddo() throws WebException,Exception {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getAcc_num())) {
			this.message = "error-无效结算单号";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatchid());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			}else if(StringUtil.isEmpty(this.model.getAcc_name())){
				this.message = "error-结算单说明不能为空";
			}else if(this.model.getId()<=0){
				this.message = "error-结算单无效";
			} else {
				TeamAccount ta=new TeamAccount();
				ta=this.teamAccountService.loadTeamAccount(this.model.getId());
				this.model.setAcc_name(URLDecoder.decode(this.model.getAcc_name(),"UTF-8"));
				ta.setAcc_name(this.model.getAcc_name());
				this.teamAccountService.updateTeamAccount(ta);
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("815");//子功能id 必须填写
				sl.setExplain(this.model.getAcc_num()+"  "+this.model.getAcc_name());//操作说明
				syslogService.saveSysLog(sl);
				this.message="ok-ok";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: trjnUserManager   
	     * @Description: 团体手工日结828   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String trjnUserManager() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: trjnusergroupmanager   
	     * @Description: 团体手工日结操作页面 829  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String trjnusergroupmanager() throws WebException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: trjnteamlistshow   
	     * @Description: 团体手工日结查询   830
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String trjnteamlistshow() throws WebException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: gettrjnExamInfoUserList   
	     * @Description: 团体日结获取体检信息 831   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String gettrjnExamInfoUserList() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		if (this.model.getChkItem() != null){
			
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}
			
			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}
					
			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setCustomer_type_id("");
			}
			
			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}
			
			if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
				this.model.setTime3("");
				this.model.setTime4("");
			}
			contractlist = this.teamAccountService.gettrjnTermExamInfoUserList(this.model.getCompany_id(), this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(), this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),this.model.getCustomer_type_id(),this.model.getTime3(),this.model.getTime4(),this.model.getLevels(), user.getUserid(), centernum, this.rows, this.getPage(),this.sort,this.order,user.getCenter_num());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termtrjndo   
	     * @Description: 团体执行日结 832  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termtrjndo()throws WebException {
		if(StringUtil.isEmpty(this.model.getIds())){
			this.message="error-无效体检编号";
		}else{
			UserDTO user = (UserDTO) session.get("username");
			this.message=this.teamAccountService.savetrjndo(this.model.getIds(), user.getUserid(), user.getCenter_num());
					
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("832");//子功能id 必须填写
			sl.setExplain(this.model.getAcc_num()+"  "+this.model.getAcc_name());//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: gettrjnExamInfoUsershow   
	     * @Description: 团体日结查询 834  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String gettrjnExamInfoUsershow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		if ((this.model.getChkItem() != null)&&(this.model.getChkItem().length()>0)){	
			
			if (this.model.getChkItem().trim().indexOf("tjrw") < 0) {
				this.model.setBatchid(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("com_name") < 0) {
				this.model.setCompany_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}
			
			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}
					
			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setCustomer_type_id("");
			}
			
			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}
			
			if (this.model.getChkItem().trim().indexOf("isprint") < 0) {
				this.model.setIsprint("");
			}			
			
			if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
				this.model.setTime3("");
				this.model.setTime4("");
			}
			
			if (this.model.getChkItem().trim().indexOf("acc_date") < 0) {
				this.model.setTime1("");
				this.model.setTime2("");
			}
			
			contractlist = this.teamAccountService.gettrjnTermExamInfoUsershow(this.model.getCompany_id(), this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(), this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),this.model.getCustomer_type_id(),this.model.getTime3(),this.model.getTime4(),this.model.getTime1(),this.model.getTime2(),this.model.getIsprint(),this.model.getLevels(), user.getUserid(), centernum, this.rows, this.getPage(),this.sort,this.order,user.getCenter_num());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}
	

	/**
	 * 
	     * @Title: termtrjndeldo   
	     * @Description: 团体结算取消日结 835  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String termtrjndeldo()throws WebException {
		if(StringUtil.isEmpty(this.model.getIds())){
			this.message="error-无效体检编号";
		}else{
			UserDTO user = (UserDTO) session.get("username");
			this.message=this.teamAccountService.updatetrjndo(this.model.getIds(), user.getUserid(), user.getCenter_num());
					
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("835");//子功能id 必须填写
			sl.setExplain(this.model.getAcc_num()+"  "+this.model.getAcc_name());//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 根据结算单号查询附加费用明细 1100
	     * @Title: getteamaccountaddlist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getteamaccountaddlist() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String itemids = this.customerInfoService.getCenterconfigByKey("TEAM_ACCOUNT_ADD_ITEMIDS", user.getCenter_num()).getConfig_value().trim();
		List<TeamAccountAdditionalDTO> list = this.teamAccountService.getteamaccountaddlist(model.getAcc_num(), itemids);
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 获取团体附加费用维护页面 1101
	     * @Title: getteamaccountaddpage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getteamaccountaddpage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 修改保存团体附加费用 1102
	     * @Title: saveteamaccountadd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveteamaccountadd() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray teamadds = JSONArray.fromObject(this.model.getTeamadds());
		List<TeamAccountAdditionalDTO> teamaddlist = (List<TeamAccountAdditionalDTO>) JSONArray.toCollection(teamadds,TeamAccountAdditionalDTO.class);
		this.teamAccountService.saveteamaccountadd(teamaddlist, model.getAcc_num(), user.getUserid());
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1102");//子功能id 必须填写
		sl.setExplain("结算单号为："+this.model.getAcc_num());//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult("保存成功!");
		return NONE;
	}
	
	/**
	 * 
	     * @Title: termaccountgroupadd   
	     * @Description: 团体结帐 增加结帐单 890   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String termaccountgroupadd() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		/*UserInvoice userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		if(userInvoice == null){
			this.message = "error-抱歉,您并没有被分配发票号段,您不能打发票";
		}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
			this.message = "error-发票号码超出您的发票段,请重新领票";
		}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num()) != null){
			this.message = "error-发票编号已经存在";
		}*/
        if(this.model.getBatchid()<=0){
			this.message = "error-批次任务编号不存在";
		}else if((this.model.getIds()==null)||(this.model.getIds().length()<=0)){
			this.message = "error-无效结算单号";
		}else{
			this.teamAccountService.saveSingleGroupTT(this.model.getBatchid(),this.model.getIds(), user.getUserid(),user.getCenter_num(),this.model.getAdd_amount());
			this.message = "ok-ok"; 

			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("890");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	
	/**
	 * 
	     * @Title: delaccountGroupTT   
	     * @Description: 团体结帐 作废结帐单 891 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delaccountGroupTT() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");		
        if(this.model.getBatchid()<=0){
			this.message = "error-批次任务编号不存在";
		}else if((this.model.getAccount_num()==null)||(this.model.getAccount_num().length()<=0)){
			this.message = "error-无效结算单号";
		}else{
			String IS_HIS_TERM_INTERFACE= this.customerInfoService.getCenterconfigByKey("IS_HIS_TERM_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			List<ChargingWayGroup> list = this.teamAccountService.getChargingWayByAccountNum(this.model.getAccount_num());//判断结账单号是否已经划过账 
			if(list.size() > 0){
				this.message = "error-这笔结账单下存在划账信息，请先删除所有收费方式，然后进行删除操作。。";
			}else{
				if("Y".equals(IS_HIS_TERM_INTERFACE.toUpperCase())){	
					this.message=this.teamAccountService.delHisaccountGroupTT(this.model.getBatchid(),this.model.getAccount_num(),user.getUserid()+"",user.getCenter_num());
				}else{
					this.message=this.teamAccountService.delaccountGroupTT(this.model.getBatchid(),this.model.getAccount_num(),user.getUserid()+"",user.getCenter_num());
				}
			}
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("891");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: qrfkaccountGroupTT   
	     * @Description: 团体结帐确认付款 892  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String qrfkaccountGroupTT() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");		
        if(this.model.getBatchid()<=0){
			this.message = "error-批次任务编号不存在";
		}else if(this.model.getId()<=0){
			this.message = "error-无效结帐单号";
		}else{
			String IS_HIS_TERM_INTERFACE= this.customerInfoService.getCenterconfigByKey("IS_HIS_TERM_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			if("Y".equals(IS_HIS_TERM_INTERFACE.toUpperCase())){				
				this.message=this.teamAccountService.hisSendaccountGroupTT(this.model.getBatchid(),this.model.getId(), user.getUserid(),user.getCenter_num());
			}else{
			    this.message=this.teamAccountService.qrfkaccountGroupTT(this.model.getBatchid(),this.model.getId(), user.getUserid(),user.getCenter_num());
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("892");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: teamwayList   
	     * @Description: 团体结帐付款方式维护 893   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String teamwayList() throws WebException{
		List<TeamWayDTO> list= new ArrayList<TeamWayDTO>();
		list=this.teamAccountService.teamwayList(this.model.getId());			
		this.outJsonResult(list);
		return NONE;
	}
	
	public String getHisTermFeesResult() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if((this.model.getAccount_num()==null)||(this.model.getAccount_num().trim().length()<=0)){
			this.message = "error-无效结帐单号";
		}else{
			FEETermResMessage fee= new FEETermResMessage();
			ResultHeader rh=fee.getMessage(this.model.getAccount_num(), user.getCenter_num());
            if("AA".equals(rh.getTypeCode())){
            	this.message = "ok-操作完成";
            }else{
            	this.message = "ok-"+rh.getText();
            }
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	//团体划账获取商户余额已开发票金额getMercaccAmountByComId
	public String  getCompanyAccountAmountByComId(){
		CompanyAccountDTO dto =  this.teamAccountService.getCompanyAccountAmountByComId(this.model);
		this.outJsonResult(dto);
		return NONE;
	}
	
	//saveDrawAccounts  保存团体划账信息
	public String  saveDrawAccounts(){
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getHz_amount() > 0){
			this.message = this.teamAccountService.saveDrawAccounts(this.model,user);
		}else{
			this.message = "error-划账金额不能为0。。。";
		}
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	

	/**
	 * 
	     * @Title: checkTteamAccountExamway   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String checkTteamAccountExamway() {
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.teamAccountService.checkTteamAccountExamway(this.model.getAcc_num(), 1, 100000);
		this.outJsonStrResult(this.message);
		return NONE;
	}
}