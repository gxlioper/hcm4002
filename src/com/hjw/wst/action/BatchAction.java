package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;

import com.hjw.config.Logincheck;
import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.service.CrmSignBillPlanService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.BatchProPlanDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.GroupItemSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchFlowLog;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.BatchModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.wst.service.WebSynchroService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.BeanUtil;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.action
 * @Description:
 * @author: yangm
 * @date: 2016年7月16日 下午8:32:41
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class BatchAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private BatchService batchService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CompanyService companyService;
	private BatchModel model = new BatchModel();
	private UserInfoService userInfoService;
    private CustomerInfoService customerInfoService;
    private SyslogService syslogService;    
    private CrmSignBillPlanService crmSignBillPlanService;
    private WebSynchroService webSynchroService;
    
   	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}
	public CrmSignBillPlanService getCrmSignBillPlanService() {
		return crmSignBillPlanService;
	}

	public void setCrmSignBillPlanService(CrmSignBillPlanService crmSignBillPlanService) {
		this.crmSignBillPlanService = crmSignBillPlanService;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
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

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(BatchModel model) {
		this.model = model;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	/**
	 * 方案管理 212 主界面
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String batchManager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("212");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 方案管理 216 主界面
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String batchshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("216");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 方案管理 217 显示方案列表
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String batchlistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getbatchList(this.model.getCompany_id(),this.defaultapp.getComid(), this.rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: groupmanager @Description:
	 *         显示分组主界面 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String groupmanager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 方案管理 222 显示批次列表
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String grouplistshow() throws WebException, SQLException {
		PageReturnDTO batchlist = new PageReturnDTO();
		UserDTO user = (UserDTO) session.get("username");
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		batchlist = this.batchService.getGroupList(this.model.getBatch_id(), this.rows, this.getPage());
		this.outJsonResult(batchlist);
		return NONE;
	}
	
	public String crmgrouplistshow() throws WebException, SQLException {
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getCrmGroupList(this.model.getBatch_id(), this.rows, this.getPage());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 229 显示分组对应体检项目
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String groupchargitemlistshow() throws WebException, SQLException {
		PageReturnDTO batchlist = new PageReturnDTO();
		UserDTO user = (UserDTO) session.get("username");
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		batchlist = this.batchService.getGroupchargItemList(this.model.getGroup_id(), 1000, 1,user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 方案管理 228 显示分组对应套餐
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String groupsetlistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getGroupSetList(this.model.getGroup_id(), 1000, 1,user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: groupOneshow @Description:
	 *         显示一条方案详细信息 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String groupOneshow() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getBatch_id() <= 0) {
			this.message = "选择体检任务无效";
			return "commerror";
		} else {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getBatch_id());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
		}
		if (this.model.getId() <= 0) {
			this.message = "选择分组无效";
			return "commerror";
		} else {
			GroupInfoDTO gi = new GroupInfoDTO();			
			gi = this.batchService.getGroupListForId(this.model.getId());
			model.setAmount(gi.getAmount());
			model.setGroup_index(gi.getGroup_index());
			model.setGroup_name(gi.getGroup_name());
			model.setGroup_num(gi.getGroup_num());
			model.setBatch_id(gi.getBatch_id());
			model.setIs_Marriage(gi.getIs_Marriage());
			model.setPosttion(gi.getPosttion());
			model.setSex(gi.getSex());
			model.setExam_item(gi.getType_name());
			model.setDiscount(gi.getDiscount());
			model.setMax_age(gi.getMax_age());
			model.setMin_age(gi.getMin_age());
			if ("Y".equals(gi.getIsActive())) {
				model.setIs_Active("有效");
			} else {
				model.setIs_Active("无效");
			}
			WebUserInfo wb = new WebUserInfo();
			try {
				wb = userInfoService.loadUserInfo(gi.getCreater());
				model.setCreaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			try {
				wb = new WebUserInfo();
				wb = userInfoService.loadUserInfo(gi.getUpdater());
				model.setUpdaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			if (gi.getCreate_time() != null) {
				model.setCreate_time(gi.getCreate_time());
			}
			if (gi.getUpdate_time() != null) {
				model.setUpdate_time(gi.getUpdate_time());
			}
			this.model.setItem_amount(this.batchService.getGroupcharItemAmt(this.model.getId()));
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: batchoneshow @Description:
	 *         显示一条方案详细信息 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batchoneshow() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getId() <= 0) {
			this.message = "选择体检任务无效";
			return "commerror";
		} else {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getId());
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			DataDictionary dd = new DataDictionary();
			try {
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getCharge_type()));
				model.setCharge_type(dd.getData_name());
			} catch (Exception ex) {
			}
			try {
				dd = new DataDictionary();
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getPay_way()));
				model.setPay_way(dd.getData_name());
			} catch (Exception ex) {
			}
			WebUserInfo wb = new WebUserInfo();
			try {
				wb = userInfoService.loadUserInfo(batch.getCreater());
				model.setCreaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			try {
				wb = new WebUserInfo();
				wb = userInfoService.loadUserInfo(batch.getUpdater());
				model.setUpdaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			if (batch.getCreate_time() != null) {
				model.setCreate_time(Timeutils.dateToStr(batch.getCreate_time()));
			}
			if (batch.getUpdate_time() != null) {
				model.setUpdate_time(Timeutils.dateToStr(batch.getUpdate_time()));
			}
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			
			if((batch.getExam_date()!=null)&&(batch.getExam_date().trim().length()>10)){
				
				batch.setExam_date(batch.getExam_date().trim().substring(0, 10));
				if("1900-01-01".equals(batch.getExam_date()))
				{
					model.setExam_date("");
				}else{
					model.setExam_date(batch.getExam_date());
				}
			}else{
				model.setExam_date("");
			}
			
			 if((batch.getExam_date_end()!=null)&&(batch.getExam_date_end().trim().length()>10)){
					
					batch.setExam_date_end(batch.getExam_date_end().trim().substring(0, 10));
					if("1900-01-01".equals(batch.getExam_date_end()))
					{
						model.setExam_date_end("");
					}else{
						model.setExam_date_end(batch.getExam_date_end());
					}
				}else{
					model.setExam_date_end("");
				}
			
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());

			if ("Y".equals(batch.getIs_Active())) {
				model.setIs_Active("有效");
			} else {
				model.setIs_Active("无效");
			}
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: groupedit @Description: 分组信息维护 224 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String groupedit() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getBatch_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		if (this.model.getId() > 0) {
			GroupInfo gi = new GroupInfo();
			gi = this.batchService.getGroupByID(this.model.getId());
			model.setAmount(gi.getAmount());
			model.setGroup_index(gi.getGroup_index());
			model.setGroup_name(gi.getGroup_name());
			model.setGroup_num(gi.getGroup_num());
			model.setBatch_id(gi.getBatch_id());
			model.setCust_type_id(gi.getCust_type_id());
			model.setIs_Marriage(gi.getIs_Marriage());
			model.setPosttion(gi.getPosttion());
			model.setSex(gi.getSex());
			model.setExam_indicator(gi.getExam_indicator());
			model.setDiscount(gi.getDiscount());
			model.setMax_age(gi.getMax_age());
			model.setMin_age(gi.getMin_age());
			model.setIs_Active("Y");
			model.setMaximum_amount(gi.getMaximum_amount()); //设定最大金额限制
			this.model.setItem_amount(this.batchService.getGroupcharItemAmt(this.model.getId()));
		} else {
			model.setMax_age(100);
			model.setMin_age(1);
			model.setAmount(0);
			model.setExam_indicator("T");
			model.setDiscount(10.00);
			model.setItem_amount(0);
		}
		
		UserDTO user = (UserDTO) session.get("username");
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("224");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		CenterConfigurationDTO ccf = new CenterConfigurationDTO();
		ccf = this.customerInfoService.getCenterconfigByKey("DJT_ADD_TIEM_TYPE", user.getCenter_num());// 新增分组样式
		String djttype = ccf.getConfig_value().trim();// 2标示180样式
		if ("2".equals(djttype.toUpperCase())) {
			return "success2";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * 
	 * @Title: batchedit @Description: 编辑方案 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batchedit() throws WebException, SQLException {
		
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		if (this.model.getId() > 0) {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getId());
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			model.setCharge_type(batch.getCharge_type());
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			 if ((batch.getExam_date() != null) && (batch.getExam_date().indexOf("1900-01-01") < 0)) {
			        this.model.setExam_date(batch.getExam_date());
			      } else {
			        this.model.setExam_date(DateTimeUtil.getDate2());
			      }
			 if ((batch.getExam_date_end() != null) && (batch.getExam_date_end().indexOf("1900-01-01") < 0)) {
			        this.model.setExam_date_end(batch.getExam_date_end());
			      } else {
			        this.model.setExam_date_end(DateTimeUtil.getDate2());
			      }
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());
			model.setIs_Active("Y");
			model.setPay_way(batch.getPay_way());
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
		} else {
			Batch batch = new Batch();
			batch.setBatch_num(DateTimeUtil.getDate() + "-");
			BeanUtil.copy(model, batch);
			this.model.setExam_fee("0");
			this.model.setContact_name(cif.getContact_Name());
			this.model.setPhone(cif.getContact_Phone());
		}
		}
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("218");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}


	/**
	 * 
	 * @Title: tatchedit @Description: 编辑方案 219 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batcheditdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		
		if (this.model.getId() > 0) {
			BatchDTO bdot = new BatchDTO();
			bdot = this.batchService.getbatchForcomidandname(model.getCompany_id(), model.getBatch_name(),user.getCenter_num(),this.model.getId());
			if ((bdot != null) &&(bdot.getId()>0)&& (bdot.getId() != this.model.getId())) {
				this.message = "error-体检任务名称重复，不能修改！";
			} else {
				//boolean userbatchflag = false;
				//userbatchflag = this.batchService.useredBatch(this.model.getId());
				//if (userbatchflag) {
				//	this.message = "error-体检任务已经被使用，不能修改！";
				//} else 
					{
					Batch bc = new Batch();
					bc = this.batchService.getBatchByID(this.model.getId());
					if ("1".equals(bc.getOverflag())) {
						this.message = "error-批次任务已经封帐";
					} else {
						bc.setAccommodation(model.getAccommodation());
						bc.setBatch_address(model.getBatch_address());
						bc.setBatch_name(model.getBatch_name());
						bc.setBatch_num(model.getBatch_num());
						bc.setCharge_type(model.getCharge_type());
						bc.setCompany_id(model.getCompany_id());
						bc.setContact_name(model.getContact_name());
						bc.setDine(model.getDine());
						bc.setExam_date(model.getExam_date());
						bc.setExam_date_end(model.getExam_date_end());
						bc.setExam_fee(model.getExam_fee());
						bc.setExam_item(model.getExam_item());
						bc.setExam_number(model.getExam_number());
						bc.setId(model.getId());
						bc.setIntroducer_name(model.getIntroducer_name());
						bc.setInvoice_title(model.getInvoice_title());
						bc.setIs_Active("Y");
						bc.setPay_way(model.getPay_way());
						bc.setPhone(model.getPhone());
						bc.setQian_remark(model.getQian_remark());
						bc.setRemark(model.getRemark());
						bc.setSales_name(model.getSales_name());
						bc.setCenter_num(user.getCenter_num());
					bc.setId(this.model.getId());
					bc.setUpdater(user.getUserid());
					bc.setUpdate_time(DateTimeUtil.parse());
					bc = this.batchService.updateBatch(bc);
					//webSynchroService.updateWebSynchro(bc.getId()+"", '7');
					this.message = "ok-体检任务修改成功！";
					
					user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("219");//子功能id 必须填写
					sl.setExplain("修改批次任务 "+bc.getBatch_num()+" "+bc.getBatch_name());//操作说明
					syslogService.saveSysLog(sl);
					
					}
				}
			}
		} else {
			BatchDTO bdot = new BatchDTO();

			Batch bc = new Batch();
			bc.setAccommodation(model.getAccommodation());
			bc.setBatch_address(model.getBatch_address());
			bc.setBatch_name(model.getBatch_name());
			bc.setBatch_num(model.getBatch_num());
			bc.setCharge_type(model.getCharge_type());
			bc.setCompany_id(model.getCompany_id());
			bc.setContact_name(model.getContact_name());
			bc.setDine(model.getDine());
			bc.setExam_date(model.getExam_date());
			bc.setExam_date_end(model.getExam_date_end());
			bc.setExam_fee(model.getExam_fee());
			bc.setExam_item(model.getExam_item());
			bc.setExam_number(model.getExam_number());
			bc.setId(model.getId());
			bc.setIntroducer_name(model.getIntroducer_name());
			bc.setInvoice_title(model.getInvoice_title());
			bc.setIs_Active("Y");
			bc.setPay_way(model.getPay_way());
			bc.setPhone(model.getPhone());
			bc.setQian_remark(model.getQian_remark());
			bc.setRemark(model.getRemark());
			bc.setSales_name(model.getSales_name());
			bc.setCenter_num(user.getCenter_num());
			bdot = this.batchService.getbatchForcomidandname(model.getCompany_id(), model.getBatch_name(),user.getCenter_num(),0);
			if ((bdot != null) && (bdot.getId() > 0)) {
				this.message = "error-体检任务名称重复，不能新增！";
			} else {
				String checktypes = this.customerInfoService.getCenterconfigByKey("IS_BATCH_CHECK", user.getCenter_num()).getConfig_value()
						.trim();
				if ("0".equals(checktypes)) {
					bc.setChecktype(0);
				} else if ("1".equals(checktypes)) {
					bc.setChecktype(2);
					bc.setChecknotice("系统自动发起体检任务审核");
				}
				bc.setCheckuser(user.getUserid());
				bc.setCreater(user.getUserid());
				bc.setCreate_time(DateTimeUtil.parse());
				bc.setUpdater(user.getUserid());
				bc.setUpdate_time(DateTimeUtil.parse());
				bc = this.batchService.saveBatch(bc);
				//webSynchroService.updateWebSynchro(bc.getId()+"", '7');
				user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("219");//子功能id 必须填写
				sl.setExplain("新增批次任务信息 "+bc.getBatch_num()+" "+bc.getBatch_name());//操作说明
				syslogService.saveSysLog(sl);
				CompanyInfo cif = new CompanyInfo();
				cif = companyService.findComByID(bc.getCompany_id());
				// 合同审核
				ContractDTO cd = new ContractDTO();
				cd = this.batchService.getContractForBrachId(bc.getId());
				if ((cd == null) || (cd.getId() <= 0)) {// 新增合同
					Contract ct = new Contract();
					ct.setBatch_id(bc.getId());
					ct.setBatch_name(bc.getBatch_name());
					ct.setCompany_id(bc.getCompany_id());
					ct.setCompany_name(cif.getCom_Name());
					String connum = customerInfoService.getCenterconfigByKey("IS_CONTRACT_NO", user.getCenter_num()).getConfig_value();
					connum = connum + GetNumContral.getInstance().getParamNum("contract", user.getCenter_num());
					ct.setContract_num(connum);
					ct.setCreater(user.getUserid());
					ct.setCreate_time(DateTimeUtil.parse());
					ct.setUpdater(user.getUserid());
					ct.setUpdate_time(DateTimeUtil.parse());
					ct.setLinkman(bc.getContact_name());
					ct.setTel(bc.getPhone());
					String addday = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_EXADATE", user.getCenter_num())
							.getConfig_value().trim();
					String vdate = DateTimeUtil.DateAdd(Integer.valueOf(addday));
					ct.setValidity_date(vdate);
					ct.setCheckdate(Timeutils.getNowDate());
					ct.setCheckuser(user.getUserid());
					if (bc.getChecktype() == 2) {
						String checktypesss = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK", user.getCenter_num())
								.getConfig_value().trim();
						if ("0".equals(checktypesss)) {
							ct.setTypes(0);
						} else if ("1".equals(checktypesss)) {
							ct.setTypes(2);
							ct.setChecknotice("系统自动发起合同审核");
						}
					}
					this.batchService.saveContract(ct);
					user = (UserDTO) session.get("username");
					sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("219");//子功能id 必须填写
					sl.setExplain("新增合同信息 "+ct.getContract_num()+" "+ct.getCompany_name());//操作说明
					syslogService.saveSysLog(sl);
				} else if (cd.getTypes() != 2) {
					Contract ct = new Contract();
					ct = this.batchService.loadContract(cd.getId());
					ct.setCheckdate(Timeutils.getNowDate());
					ct.setCheckuser(user.getUserid());
					if (bc.getChecktype() == 2) {
						String checktypesss = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK", user.getCenter_num())
								.getConfig_value().trim();
						if ("0".equals(checktypesss)) {
							ct.setTypes(0);
						} else if ("1".equals(checktypesss)) {
							ct.setTypes(2);
							ct.setChecknotice("系统自动发起合同审核");
						}
						this.batchService.updateContract(ct);
						sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("219");//子功能id 必须填写
						sl.setExplain("修改合同信息 "+ct.getContract_num()+" "+ct.getCompany_name());//操作说明
						syslogService.saveSysLog(sl);
					}
				}
				this.message = "ok-体检任务增加成功！";
				
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: groupeditdo @Description: 分组信息保存 225 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String groupeditdo() throws WebException, SQLException {
		System.out.println(model.getCust_type_id());
		UserDTO user = (UserDTO) session.get("username");
		GroupInfo bc = new GroupInfo();
		bc.setAmount(model.getAmount());
		bc.setBatch_id(model.getBatch_id());
		bc.setDiscount(model.getDiscount());
		bc.setGroup_index(model.getGroup_index());
		bc.setGroup_name(model.getGroup_name());
		bc.setGroup_num(model.getGroup_num());
		bc.setCust_type_id(model.getCust_type_id());
		bc.setId(model.getId());
		bc.setIs_Marriage(model.getIs_Marriage());
		bc.setIsActive("Y");
		bc.setExam_indicator(model.getExam_indicator());
		bc.setMax_age(model.getMax_age());
		bc.setMin_age(model.getMin_age());
		bc.setPosttion(model.getPosttion());
		bc.setSex(model.getSex());
		bc.setStart_date(DateTimeUtil.parse());
		bc.setEnd_date(DateTimeUtil.parse());
		bc.setMaximum_amount(model.getMaximum_amount());  //最大金额限制
		Batch taover = this.batchService.getBatchByID(this.model.getBatch_id());
		if(taover.getAccountflag()==1) {
			this.message = "error-批次任务已经锁定";
			this.outJsonStrResult(this.message);
			return NONE;
		}
		
		if ("1".equals(taover.getOverflag())) {
			this.message = "error-批次任务已经结帐";
			this.outJsonStrResult(this.message);
			return NONE;
		} else {

		// 获取体检项目
		String itementities = this.model.getItementities();
		itementities = itementities.replace("}{", "},{");
		itementities = "[" + itementities + "]";
		JSONArray itemarray = JSONArray.fromObject(itementities);
		@SuppressWarnings("unchecked")
		List<GroupChargingItemDTO> listitem = (List<GroupChargingItemDTO>) JSONArray.toCollection(itemarray,
				GroupChargingItemDTO.class);
		String usersex = this.model.getSex();
		String itemchars="";
		boolean sexflag = false;
		
		if((listitem==null)||(listitem.size()<=0)){
			this.message="error-未选择收费项目，操作不能继续";
			this.outJsonStrResult(this.message);
			return NONE;
		}
		
		for(GroupChargingItemDTO gcd:listitem){
			itemchars=itemchars+""+gcd.getItem_code()+",";			
			ChargingItem ci= new ChargingItem();
			ci=this.batchService.getChargingItemBycode(gcd.getItem_code());
			if(ci==null){
				this.message="error-"+gcd.getItem_code()+" "+gcd.getItem_name()+"不存在";
				sexflag = false;
				break;
			}else if(!"Y".equals(ci.getIsActive())){
				this.message="error-"+gcd.getItem_code()+" "+gcd.getItem_name()+"已经被删除，操作不能继续";
				sexflag = false;
				break;
			}else{			
				if(StringUtil.isEmpty(usersex)){
					sexflag=true;
			    } else if ("全部".equals(ci.getSex())) {
					sexflag = true;
				} else if (usersex.equals(ci.getSex())) {
					sexflag = true;
				}else{
					sexflag = false;
				}
			    if(!sexflag){
			    	this.message="error-"+gcd.getItem_code()+" "+gcd.getItem_name()+"性别不一致，操作不能继续";
			    	break;
			    }
			}			
		}
		if(!sexflag){
			this.outJsonStrResult(this.message);
			return NONE;
		}
		
		// 获取体检套餐
		String setentities = model.getSetentities();
		setentities = setentities.replace("}{", "},{");
		setentities = "[" + setentities + "]";
		JSONArray setarray = JSONArray.fromObject(setentities);
		@SuppressWarnings("unchecked")
		List<ExamSetDTO> listset = (List<ExamSetDTO>) JSONArray.toCollection(setarray, ExamSetDTO.class);
		String sets="";
			boolean setsexflag = false;

			if ((listset != null) && (listset.size() > 0)) {
				for (ExamSetDTO gcd : listset) {
					itemchars = itemchars + "" + gcd.getSet_num() + ",";
					ExamSet es = new ExamSet();
					es = this.batchService.getExamSetByNum(gcd.getSet_num(),user.getCenter_num());
					if (es == null) {
						this.message = "error-" + gcd.getSet_num() + " " + gcd.getSet_name() + "不存在";
						setsexflag = false;
						break;
					} else if (!"Y".equals(es.getIs_Active())) {
						this.message = "error-" + gcd.getSet_num() + " " + gcd.getSet_name() + "已经被删除，操作不能继续";
						setsexflag = false;
						break;
					} else {
						if (StringUtil.isEmpty(usersex)) {
							setsexflag = true;
						} else if ("全部".equals(es.getSex())) {
							setsexflag = true;
						} else if (usersex.equals(es.getSex())) {
							setsexflag = true;
						} else {
							setsexflag = false;
						}
						if (!setsexflag) {
							this.message = "error-" + gcd.getSet_num() + " " + gcd.getSet_name() + "性别不一致，操作不能继续";
							break;
						}
					}
				}
			} else {
				setsexflag = true;
			}

			if (!setsexflag) {
				this.outJsonStrResult(this.message);
				return NONE;
			}
			
		if ((listitem != null) && (listitem.size() > 0)) {
			if (this.model.getId() > 0) {
				GroupInfoDTO gi = this.batchService.getGroupForbatidandgroupname(bc.getBatch_id(), bc.getGroup_name());
				if ((gi != null) &&(gi.getId()>0)&&(gi.getId() != this.model.getId())) {
					this.message = "error-分组名称被使用，不能修改分组！";
				} else {
					boolean userbatchflag = false;
					userbatchflag = this.batchService.useredGroup(this.model.getId());
					boolean groupusedflag=false;
					try{
					  String IS_GROUP_USERED_EDIT = this.customerInfoService.getCenterconfigByKey("IS_GROUP_USERED_EDIT", user.getCenter_num()).getConfig_value()
							.trim();
					
					if ("Y".equals(IS_GROUP_USERED_EDIT)) {
						groupusedflag=true;
					}
					}catch(Exception ex){
						
					}
					if ((userbatchflag)&&(!groupusedflag)) {
						this.message = "error-分组已经被使用，不能修改分组！";
					} else {
						bc.setCreater(user.getUserid());
						bc.setCreate_time(DateTimeUtil.parse());
						bc.setUpdater(user.getUserid());
						bc.setUpdate_time(DateTimeUtil.parse());
						
						long connum=0;
						if((bc.getCust_type_id()!=null)&&(bc.getCust_type_id().trim().length()>0))
						{
							connum+=1;	
						}
						if((bc.getExam_indicator()!=null)&&(bc.getExam_indicator().trim().length()>0)){
							connum+=1;	
						}
						if((bc.getGroup_settlement_type()!=null)&&(bc.getGroup_settlement_type().trim().length()>0)){
							connum+=1;	
						}
						if((bc.getIs_Marriage()!=null)&&(bc.getIs_Marriage().trim().length()>0)){
							connum+=1;	
						}
						if((bc.getSex()!=null)&&(bc.getSex().trim().length()>0)){
							connum+=1;	
						}
						if((bc.getGroup_index()!=null)&&(bc.getGroup_index().trim().length()>0)){
							connum+=1;	
						}
						if((bc.getMax_age()>0)&&(bc.getMin_age()>0)){
							connum+=1;	
						}
						if((bc.getPosttion()!=null)&&(bc.getPosttion().trim().length()>0)){
							connum+=1;	
						}
						bc.setCon_num(connum);
						boolean editflag=false;
						if(this.model.getIsgroupitemflag()==1){
							editflag=true;
							/*
						PageReturnDTO batchlist = new PageReturnDTO();
						batchlist = this.batchService.getGroupchargItemList(this.model.getId(), 1000, 1);						
							if (batchlist != null && batchlist.getRows() != null && batchlist.getRows().size() > 0) {
								for (GroupChargingItemDTO gci : listitem) {
									boolean editflag1 = false;
									for (Object obj : batchlist.getRows()) {
										GroupChargingItemDTO gciold = new GroupChargingItemDTO();
										gciold = (GroupChargingItemDTO) obj;
										if (gciold.getItem_code().equals(gci.getItem_code())) {
											editflag1 = true;
											break;
										}
									}
									if (!editflag1) {
										editflag = true;
										break;
									}
								}

								if (!editflag) {
									for (Object obj : batchlist.getRows()) {
										boolean editflag1 = false;
										GroupChargingItemDTO gciold = new GroupChargingItemDTO();
										gciold = (GroupChargingItemDTO) obj;
										for (GroupChargingItemDTO gci : listitem) {											
											if (gciold.getItem_code().equals(gci.getItem_code())) {
												editflag1 = true;
												break;
											}
										}
										if (!editflag1) {
											editflag = true;
											break;
										}
									}
								}
							}*/		
						}
						//保存分组
						this.message = this.batchService.updateGroup(bc, listitem, listset,user.getCenter_num());
						
						if(this.message.indexOf("0")==0){
							if(editflag){  //是否修改预约人员标识
								List<ExamInfoDTO> examlist = new ArrayList<ExamInfoDTO>();
								examlist=this.batchService.getExaminfoYforGroupId(this.model.getBatch_id(), this.model.getId());
								if(examlist!=null && examlist.size()>0){
									for(ExamInfoDTO ei:examlist){
										this.customerInfoService.dotUserGroup(this.model.getId(), user.getUserid() + "",
												user.getCenter_num(),ei.getExam_num()+"");
									}
								}
							}
						}
						
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("225");//子功能id 必须填写
						sl.setExplain("修改分组信息 "+sets+" "+itemchars);//操作说明
						syslogService.saveSysLog(sl);
					}
				}
			} else {
				GroupInfoDTO gi = this.batchService.getGroupForbatidandgroupname(bc.getBatch_id(), bc.getGroup_name());
				if ((gi != null) &&(gi.getId()>0)) {
					this.message = "error-分组名称被使用，不能新增分组！";
				} else {
					bc.setCreater(user.getUserid());
					bc.setCreate_time(DateTimeUtil.parse());
					bc.setUpdater(user.getUserid());
					bc.setUpdate_time(DateTimeUtil.parse());
					long connum=0;
					if((bc.getCust_type_id()!=null)&&(bc.getCust_type_id().trim().length()>0))
					{
						connum+=1;	
					}
					if((bc.getExam_indicator()!=null)&&(bc.getExam_indicator().trim().length()>0)){
						connum+=1;	
					}
					if((bc.getGroup_settlement_type()!=null)&&(bc.getGroup_settlement_type().trim().length()>0)){
						connum+=1;	
					}
					if((bc.getIs_Marriage()!=null)&&(bc.getIs_Marriage().trim().length()>0)){
						connum+=1;	
					}
					if((bc.getSex()!=null)&&(bc.getSex().trim().length()>0)){
						connum+=1;	
					}
					if((bc.getGroup_index()!=null)&&(bc.getGroup_index().trim().length()>0)){
						connum+=1;	
					}
					if((bc.getMax_age()>0)&&(bc.getMin_age()>0)){
						connum+=1;	
					}
					if((bc.getPosttion()!=null)&&(bc.getPosttion().trim().length()>0)){
						connum+=1;	
					}
					bc.setCon_num(connum);
					this.message = this.batchService.saveGroup(bc, listitem, listset,user.getCenter_num());
					
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("225");//子功能id 必须填写
					sl.setExplain("新增分组信息 "+sets+" "+itemchars);//操作说明
					syslogService.saveSysLog(sl);
				}
			}
		} else {
			this.message = "error-分组里面缺少项目，不能操作！";
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	 * @Title: groupdelete  226 @Description: @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String groupdelete() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId()<=0){
			this.message="error-无效的分组编号！";
		}else{
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			boolean userbatchflag=false;
			userbatchflag=this.batchService.useredGroup(this.model.getId());
			if(userbatchflag){
				this.message="error-分组已经被使用，不能删除分组！";
			}else{
				GroupInfo bc = new GroupInfo();
				bc = this.batchService.getGroupByID(this.model.getId());
				if ("Y".equals(bc.getIsActive())) {
					bc.setIsActive("N");
				} else if ("N".equals(bc.getIsActive())) {
					bc.setIsActive("Y");
				} else {
					bc.setIsActive("Y");
				}
				bc.setUpdater(user.getUserid());
				bc.setUpdate_time(DateTimeUtil.parse());
				bc = this.batchService.updateGroup(bc);
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("226");//子功能id 必须填写
				sl.setExplain("删除分组信息 "+bc.getGroup_num()+" "+bc.getGroup_num());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-删除分组成功！";
			}
			}
		}		
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: batchdelete @Description: 220
	 *         是否有效 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String batchdelete() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId()<=0){
			this.message="error-无效的体检任务编号！";
		}else{
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getId());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			boolean userbatchflag=false;
			userbatchflag=this.batchService.useredBatch(this.model.getId(),user.getCenter_num());
			if(userbatchflag){
				this.message="error-体检任务已经被使用，不能删除！";
			}else{
				Batch bc = new Batch();
				bc = this.batchService.getBatchByID(this.model.getId());
				if ("Y".equals(bc.getIs_Active())) {
					bc.setIs_Active("N");
				} else if ("N".equals(bc.getIs_Active())) {
					bc.setIs_Active("Y");
				} else {
					bc.setIs_Active("Y");
				}
				bc.setUpdater(user.getUserid());
				bc.setUpdate_time(DateTimeUtil.parse());
				bc = this.batchService.updateBatch(bc);
				//webSynchroService.updateWebSynchro(bc.getId()+"", '7');
				this.message = "ok-删除体检任务成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("220");//子功能id 必须填写
				sl.setExplain("删除批次方案 "+bc.getBatch_num()+" "+bc.getBatch_name());//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 方案管理 228 显示分组对应套餐
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String satlistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		list = this.batchService.getSetList(this.model.getSetname(),this.model.getSex(),"1",user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	public String crmsatlistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		list = this.batchService.getSetList(this.model.getSetname(),this.model.getSex(),this.model.getApptype(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 方案管理 zyb31 职业病显示分组对应套餐
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String zybsatlistshow() throws WebException,Exception {
		UserDTO user = (UserDTO) session.get("username");
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		//this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		list = this.batchService.getSetList(this.model.getSetname(),this.model.getSex(),this.model.getApp_type(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 231 id对应的套餐
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String getExamOneShow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		ExamSetDTO list = new ExamSetDTO();
		list = this.batchService.findSetById(this.model.getExam_set_id(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}


	/**
	 * 
	 * @Title: changitemlist @Description: 323
	 *         获取收费项目信息表 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String changitemlist() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");	
		PageReturnDTO batchlist = new PageReturnDTO();
		String at = this.defaultapp.getComid();
		//职业病套餐维护，次标识99   可选择普通收费项目
		if("99".equals(model.getApp_type())) {
			at = "99";
		}
		batchlist = this.batchService.getchargItemList(at,this.model.getSetname(),this.model.getSex(),this.model.getDep_id(), user.getCenter_num(), this.rows, this.getPage());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: changitemlist @Description: 323
	 *         获取收费项目信息表 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String setforchangitemlist() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<GroupChargingItemDTO> batchlist = new ArrayList<GroupChargingItemDTO>();
		batchlist = this.batchService.getsetForchargItemListBysetnum(this.model.getSet_num(),user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: batchproShow @Description: 人员计划信息显示
	 *         234 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String batchproShow() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("234");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: getbatchproList 235 @Description:
	 *         获取方案人员计划信息 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String getbatchproList() throws WebException, SQLException {
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getbatchproList(this.model.getBatch_id(), this.rows, this.getPage());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: batchproplanedit @Description:
	 *         获取人员计划列表 236 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batchproplanedit() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
			this.model.setExam_number(0);
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
			try {
				this.model.setExam_number(Long.valueOf(cif.getExam_number()));
			} catch (Exception ex) {
				ex.printStackTrace();
				this.model.setExam_number(0);
			}
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("236");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: batchproplaneditdo @Description:
	 *         保存人员计划列表 237 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batchproplaneditdo() throws WebException, SQLException {
		if (this.model.getBatch_id() <= 0) {
			this.message = "error-获取体检任务错误！";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			UserDTO user = (UserDTO) session.get("username");
			// 获取体检项目
			String itementities = this.model.getItementities();
			itementities = itementities.replace("}{", "},{");
			itementities = "[" + itementities + "]";
			JSONArray itemarray = JSONArray.fromObject(itementities);
			@SuppressWarnings("unchecked")
			List<BatchProPlanDTO> listitem = (List<BatchProPlanDTO>) JSONArray.toCollection(itemarray,
					BatchProPlanDTO.class);
			if (listitem.size() > 0) {
				this.batchService.delBatchProPlanForBatchId(this.model.getBatch_id());
				BatchPlanLog log=new BatchPlanLog();
				log.setCreater(String.valueOf(user.getUserid()));
				log.setCreater_time(new Date());
				log.setProject_id(String.valueOf(taover.getId()));
				log.setProject_name(taover.getBatch_name());
				log.setProject_status("删除人员计划");
				log.setProject_type("25");
				log.setType("2");
				this.crmSignBillPlanService.saveBatchPlanLog(log);
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("237");//子功能id 必须填写
				sl.setExplain("删除人员计划 "+this.model.getBatch_id());//操作说明
				syslogService.saveSysLog(sl);
				for (int i = 0; i < listitem.size(); i++) {
					BatchProPlanDTO bpp = listitem.get(i);
					BatchProPlan bp = new BatchProPlan();
					bp.setBatch_id(this.model.getBatch_id());
					bp.setCreater(user.getUserid());
					bp.setCreater_date(Timeutils.getNowDate());
					bp.setPer_num(bpp.getPer_num());
					bp.setPlandate(bpp.getPlandate());
					this.batchService.saveBatchProPlan(bp);
					BatchPlanLog log1=new BatchPlanLog();
					log1.setCreater(String.valueOf(user.getUserid()));
					log1.setCreater_time(new Date());
					log1.setProject_id(String.valueOf(taover.getId()));
					log1.setProject_name(taover.getBatch_name());
					log1.setProject_status("保存人员计划"+bp.getPer_num());
					log1.setProject_type("26");
					log1.setType("2");
					this.crmSignBillPlanService.saveBatchPlanLog(log1);
					sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("237");//子功能id 必须填写
					sl.setExplain("保存人员计划 "+bp.getPer_num());//操作说明
					syslogService.saveSysLog(sl);
				}
				this.message = "ok-设置成功！";
			} else {
				this.message = "error-无效数据！";
			}
			}
		}
		this.outJsonStrResult(message);
		return NONE;
	}

	/**
	 * 
	 * @Title: batchCheckManager @Description: 方案审核主界面
	 *         238 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String batchCheckManager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("238");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: batchonecheckshow 238 @Description:
	 *         显示整个方案明细报表 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batchonecheckshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getId() <= 0) {
			this.message = "选择体检任务无效";
			return "commerror";
		} else {
			
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getId());
			if ("1".equals(batch.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			DataDictionary dd = new DataDictionary();
			try {
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getCharge_type()));
				model.setCharge_type(dd.getData_name());
			} catch (Exception ex) {
			}
			try {
				dd = new DataDictionary();
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getPay_way()));
				model.setPay_way(dd.getData_name());
			} catch (Exception ex) {
			}
			WebUserInfo wb = new WebUserInfo();
			try {
				wb = userInfoService.loadUserInfo(batch.getCreater());
				model.setCreaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			try {
				wb = new WebUserInfo();
				wb = userInfoService.loadUserInfo(batch.getUpdater());
				model.setUpdaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			if (batch.getCreate_time() != null) {
				model.setCreate_time(Timeutils.dateToStr(batch.getCreate_time()));
			}
			if (batch.getUpdate_time() != null) {
				model.setUpdate_time(Timeutils.dateToStr(batch.getUpdate_time()));
			}
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			model.setExam_date(batch.getExam_date());
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());

			if ("Y".equals(batch.getIs_Active())) {
				model.setIs_Active("有效");
			} else {
				model.setIs_Active("无效");
			}
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
			FangAnShowDTO fa = new FangAnShowDTO();
			fa = this.batchService.getFangAnShow(this.model.getId(),user.getCenter_num());
			model.setGroupItemList(fa.getGroupItemList());
			model.setGroupSetList(fa.getGroupSetList());
			model.setItemList(fa.getItemList());
			model.setBppList(fa.getBppList());
		}
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: 方案审核 239 @Description: 显示方案审核界面 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batcheckedit() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getId() <= 0) {
			this.message = "选择体检任务无效";
			return "commerror";
		} else {
			
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getId());
			if ("1".equals(batch.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			DataDictionary dd = new DataDictionary();
			try {
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getCharge_type()));
				model.setCharge_type(dd.getData_name());
			} catch (Exception ex) {
			}
			try {
				dd = new DataDictionary();
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getPay_way()));
				model.setPay_way(dd.getData_name());
			} catch (Exception ex) {
			}
			WebUserInfo wb = new WebUserInfo();
			try {
				wb = userInfoService.loadUserInfo(batch.getCreater());
				model.setCreaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			try {
				wb = new WebUserInfo();
				wb = userInfoService.loadUserInfo(batch.getUpdater());
				model.setUpdaters(wb.getLog_Name());
			} catch (Exception ex) {
			}

			wb = new WebUserInfo();
			try {
				wb = userInfoService.loadUserInfo(batch.getCheckuser());
				model.setCheckuser(wb.getLog_Name());
			} catch (Exception ex) {
			}

			if (batch.getCreate_time() != null) {
				model.setCreate_time(Timeutils.dateToStr(batch.getCreate_time()));
			}
			if (batch.getUpdate_time() != null) {
				model.setUpdate_time(Timeutils.dateToStr(batch.getUpdate_time()));
			}
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			model.setExam_date(batch.getExam_date());
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());

			if ("Y".equals(batch.getIs_Active())) {
				model.setIs_Active("有效");
			} else {
				model.setIs_Active("无效");
			}
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
			model.setCheckdate(batch.getCheckdate());
			model.setChecknotice(batch.getChecknotice());
			model.setChecktype(batch.getChecktype());
		}
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("239");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: 方案审核 240 @Description: 执行方案审核 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String batcheckeditdo() throws WebException, SQLException {
		CompanyInfo cif = new CompanyInfo();
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.message = "error-选择单位无效";
		} else {
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getBatch_id() <= 0) {
			this.message = "error-选择体检任务无效";
		} else {
			ContractDTO cd = new ContractDTO();
			cd = this.batchService.getContractForBrachId(this.model.getBatch_id());
			if ((cd!=null)&&(cd.getTypes() == 2)){
				this.message = "error-选择体检任务已经形成合同，不能再次操作！";
			} else {
				Batch batch = new Batch();
				batch = this.batchService.getBatchByID(this.model.getBatch_id());
				
				if ("1".equals(batch.getOverflag())) {
					this.message = "error-批次任务已经封帐";
				} else {
					batch.setCheckdate(Timeutils.getNowDate());
					batch.setChecknotice(model.getChecknotice());
					batch.setChecktype(model.getChecktype());
					batch.setCheckuser(user.getUserid());
					this.batchService.updateBatch(batch);
					
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("240");//子功能id 必须填写
					sl.setExplain("批次方案审核 "+batch.getBatch_num()+" "+batch.getChecktype()+" "+batch.getChecknotice());//操作说明
					syslogService.saveSysLog(sl);
					if (cd.getId() > 0) {
						if(this.model.getChecktype()==2){
						Contract ct = new Contract();
						ct = this.batchService.loadContract(cd.getId());
						ct.setCheckdate(Timeutils.getNowDate());
						ct.setCheckuser(user.getUserid());						
												
						  String checktypes = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK", user.getCenter_num()).getConfig_value().trim();
						  if ("0".equals(checktypes)) {
							  ct.setTypes(0);
						  } else if ("1".equals(checktypes)) {
							  ct.setTypes(2);
							  ct.setChecknotice("系统自动发起合同审核");
						  }				
						  this.batchService.updateContract(ct);	

							sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("236");//子功能id 必须填写
							sl.setExplain("合同审核 "+ct.getContract_num()+" "+ct.getTypes()+" "+ct.getChecknotice());//操作说明
							syslogService.saveSysLog(sl);
						}
					} else {
						Contract ct = new Contract();
						ct.setBatch_id(batch.getId());
						ct.setBatch_name(batch.getBatch_name());
						ct.setCompany_id(cif.getId());
						ct.setCompany_name(cif.getCom_Name()); 
						String connum =customerInfoService.getCenterconfigByKey("IS_CONTRACT_NO", user.getCenter_num()).getConfig_value();
						connum = connum +GetNumContral.getInstance().getParamNum("contract", user.getCenter_num());
						ct.setContract_num(connum);
						ct.setCreater(user.getUserid());
						ct.setCreate_time(DateTimeUtil.parse());
						ct.setUpdater(user.getUserid());
						ct.setUpdate_time(DateTimeUtil.parse());
						ct.setLinkman(batch.getContact_name());
						ct.setTel(batch.getPhone());
						String addday = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_EXADATE", user.getCenter_num()).getConfig_value().trim();
						String vdate = DateTimeUtil.DateAdd(Integer.valueOf(addday));
						ct.setValidity_date(vdate);
						ct.setCheckdate(Timeutils.getNowDate());
						ct.setCheckuser(user.getUserid());
						
						if(this.model.getChecktype()==2){
							String checktypes = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK", user.getCenter_num()).getConfig_value().trim();
						    if ("0".equals(checktypes)) {
							   ct.setTypes(0);
						    } else if ("1".equals(checktypes)) {
							   ct.setTypes(2);
							   ct.setChecknotice("系统自动发起合同审核");
						    }
						}else{
							ct.setTypes(0);
						}
						this.batchService.saveContract(ct);
						sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("240");//子功能id 必须填写
						sl.setExplain("合同审核 "+ct.getContract_num()+" "+ct.getTypes()+" "+ct.getChecknotice());//操作说明
						syslogService.saveSysLog(sl);
					}
					this.message = "ok-体检任务审核完成！";
				}
			}
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: contractManager   
	     * @Description: 215 合同主界面页面  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
    public String contractManager() throws WebException, SQLException {
    	UserDTO user = (UserDTO) session.get("username");
    	SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("215");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
    
    /**
     * 
         * @Title: contractlistshow 241  
         * @Description: 显示合同列表  
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: void      
         * @throws
     */
    public String contractlistshow() throws WebException, SQLException { 
    	UserDTO user = (UserDTO) session.get("username");
    	PageReturnDTO contractlist = new PageReturnDTO();
    	contractlist = this.batchService.contractlistshow(this.model.getCompany_id(),this.model.getBatch_id(),this.rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(contractlist);
		return NONE;
	}
    
    /**
     * 
         * @Title: batchUserManager   
         * @Description: 显示人员管理主控制界面 214 
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String batchUserManager()throws WebException, SQLException { 
    	UserDTO user = (UserDTO) session.get("username");
    	SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("214");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
    }
    
    
    
    /**
     * 
         * @Title: getCompanForBatch   
         * @Description: 部门获取方案   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String getCompanForBatch() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	this.defaultapp = (SystemType)session.get("defaultapp");
    	List<BatchDTO> getbatchList = new ArrayList<BatchDTO>();
    	getbatchList = this.batchService.getbatchList(this.model.getCompany_id(),user.getCenter_num(),this.defaultapp.getComid());
    	this.outJsonResult(getbatchList);
    	return NONE;
    }
    
    /**
     * 
         * @Title: getCompanForBatch   
         * @Description: 部门获取方案   带空行
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String getCompanForBatch2() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	this.defaultapp = (SystemType)session.get("defaultapp");
    	List<BatchDTO> getbatchList = new ArrayList<BatchDTO>();
    	getbatchList = this.batchService.getbatchList(this.model.getCompany_id(),user.getCenter_num(),this.defaultapp.getComid());
    	List<BatchDTO> getbatchList1 = new ArrayList<BatchDTO>();
    	BatchDTO dcd = new BatchDTO();
    	dcd.setId(0);
    	dcd.setBatch_name("请选择体检任务");
    	getbatchList1.add(dcd);
    	for(BatchDTO bd :getbatchList){
    		getbatchList1.add(bd);
    	}
    	this.outJsonResult(getbatchList1);
    	return NONE;
    }
    
    /**
     * 
         * @Title: impuserfile   
         * @Description: 上传文件界面   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String impuserfile()throws WebException, SQLException { 
    	UserDTO user = (UserDTO) session.get("username");
    	if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getBatch_id() <= 0) {
			this.message = "选择体检任务无效";
			return "commerror";
		} else {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getBatch_id());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
		}
		this.model.setCreater(user.getUserid());
		return SUCCESS;
    }
    
    /**
     * 
         * @Title: impuserfile   
         * @Description: 上传文件界面   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String zybimpuserfile()throws WebException, SQLException { 
    	UserDTO user = (UserDTO) session.get("username");
    	if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (this.model.getBatch_id() <= 0) {
			this.message = "选择体检任务无效";
			return "commerror";
		} else {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getBatch_id());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
		}
		this.model.setCreater(user.getUserid());
		return SUCCESS;
    }
    
    /**
	 * 
	 * @Title: batchonecheckshowcontractonecheckshow 272 @Description:
	 *         显示整个方案明细报表 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String contractonecheckshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if((this.model.getContract_num()==null)||(this.model.getContract_num().trim().length()<=0)){
			this.message = "合同编号无效";
			return "commerror";
		}else{
			ContractDTO ct = new ContractDTO();
			ct =this.batchService.contractForContractNum(this.model.getContract_num());
		if (ct.getCompany_id() <= 0) {
			this.message = "合同单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(ct.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
		if (ct.getBatch_id() <= 0) {
			this.message = "合同体检任务无效";
			return "commerror";
		} else {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(ct.getBatch_id());			
			if ("1".equals(batch.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			DataDictionary dd = new DataDictionary();
			try {
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getCharge_type()));
				model.setCharge_type(dd.getData_name());
			} catch (Exception ex) {
			}
			try {
				dd = new DataDictionary();
				dd = this.companyService.getDatadisForId(Long.valueOf(batch.getPay_way()));
				model.setPay_way(dd.getData_name());
			} catch (Exception ex) {
			}
			WebUserInfo wb = new WebUserInfo();
			try {
				wb = userInfoService.loadUserInfo(batch.getCreater());
				model.setCreaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			try {
				wb = new WebUserInfo();
				wb = userInfoService.loadUserInfo(batch.getUpdater());
				model.setUpdaters(wb.getLog_Name());
			} catch (Exception ex) {
			}
			if (batch.getCreate_time() != null) {
				model.setCreate_time(Timeutils.dateToStr(batch.getCreate_time()));
			}
			if (batch.getUpdate_time() != null) {
				model.setUpdate_time(Timeutils.dateToStr(batch.getUpdate_time()));
			}
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			model.setExam_date(batch.getExam_date());
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());

			if ("Y".equals(batch.getIs_Active())) {
				model.setIs_Active("有效");
			} else {
				model.setIs_Active("无效");
			}
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
			FangAnShowDTO fa = new FangAnShowDTO();
			fa = this.batchService.getFangAnShow(this.model.getId(),user.getCenter_num());
			model.setGroupItemList(fa.getGroupItemList());
			model.setGroupSetList(fa.getGroupSetList());
			model.setItemList(fa.getItemList());
			model.setBppList(fa.getBppList());
		}
		}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: contractcheckedit 
	     * @Description: 审核方合同  273
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String contractcheckedit() throws WebException, SQLException {
		if ((this.model.getContract_num()==null)||(this.model.getContract_num().trim().length()<=0)) {
			this.message = "合同编号无效";
			return "commerror";
		} else {
			ContractDTO cd = new ContractDTO();
			cd = this.batchService.getContractForContNum(this.model.getContract_num().trim());
            if((cd==null)||(cd.getId()<=0)){
            	this.message = "查询合同编号无效";
    			return "commerror";
            }else{
            	this.model.setComname(cd.getCompany_name());
            	this.model.setChecktype(cd.getTypes());
            	this.model.setChecknotice(cd.getChecknotice());
            	this.model.setCheckdate(cd.getCheckdate());
            	UserDTO user = (UserDTO) session.get("username");
            	SysLog sl =  new SysLog();
        		sl.setCenter_num(user.getCenter_num());
        		sl.setUserid(user.getUserid()+"");
        		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
        		sl.setXtgnid("");//可不填写
        		sl.setXtgnid2("273");//子功能id 必须填写
        		sl.setExplain("");//操作说明
        		syslogService.saveSysLog(sl);
            	return SUCCESS;
            }
		}		
		
	}
	/**
	 * 
	     * @Title: contractcheckeditdo   
	     * @Description: 审核合同 274
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String contractcheckeditdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getContract_num()==null)||(this.model.getContract_num().trim().length()<=0)) {
			this.message = "error-合同无效！";
		} else {
			ContractDTO cd = new ContractDTO();
			cd = this.batchService.getContractForContNum(this.model.getContract_num().trim());
			if (cd==null){
				this.message = "error-无效的合同编号！";
			}/*else if (cd.getTypes() == 2){
				this.message = "error-选择合同已经审核，不能再次操作！";
			}*/
			else {
				Batch batch = new Batch();
				batch = this.batchService.getBatchByID(cd.getBatch_id());
				if ("1".equals(batch.getOverflag())) {
					this.message = "error-批次任务已经封帐";
				} else {
					if(this.model.getChecktype()==2){
					   batch.setCheckdate(Timeutils.getNowDate());
					   batch.setChecknotice("合同审核返回："+model.getChecknotice());
					   batch.setChecktype(2);
					   batch.setCheckuser(user.getUserid());
					   this.batchService.updateBatch(batch);

				    	SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("274");//子功能id 必须填写
						sl.setExplain("审核方案 "+batch.getBatch_num()+" "+batch.getBatch_name());//操作说明
						syslogService.saveSysLog(sl);
					   
					}
					Contract ct = new Contract();
					ct = this.batchService.loadContract(cd.getId());
					ct.setCheckdate(Timeutils.getNowDate());
					ct.setCheckuser(user.getUserid());
					ct.setChecknotice(model.getChecknotice());
					ct.setTypes(this.model.getChecktype());			
					this.batchService.updateContract(ct);
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("274");//子功能id 必须填写
					sl.setExplain("审核合同 "+ct.getContract_num()+" "+ct.getCompany_name());//操作说明
					syslogService.saveSysLog(sl);
					this.message = "ok-合同审核完成！";
				}
			}
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: checkitemflag   
	     * @Description:判断缴费项目是否冲突
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkitemflag() throws WebException, SQLException {	
		UserDTO user = (UserDTO) session.get("username");
		if((this.model.getChecknotice()==null)||(this.model.getChecknotice().trim().length()<=1)){
			message="ok";
		}else if((this.model.getExam_item()==null)||(this.model.getExam_item().trim().length()<=0)){
			message="ok";
		}else{
			this.model.setChecknotice(this.model.getChecknotice().trim().substring(1, this.model.getChecknotice().length()));
			boolean flagss = this.batchService.checkitemflag(this.model.getChecknotice(), this.model.getExam_item(),user.getCenter_num());
			if(flagss){
				message="error";
			}else{
				message="ok";
			}
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: copygroupmanager   
	     * @Description:查询分组复制   817
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String copygroupmanager() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}
	
		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("817");//子功能id 必须填写
		sl.setExplain("要复制的批次 "+model.getBatch_num()+" "+model.getBatch_name());//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: copygroupdo   
	     * @Description: 818 执行分组复制 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String copygroupdo() throws WebException, SQLException {
		if (this.model.getBatch_id() <= 0) {
			message="error-批次方案未知";
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
			
			if(StringUtil.isEmpty(this.model.getIds())){
				message="error-选择分组无效";
			}else{
				UserDTO user = (UserDTO) session.get("username");
				this.batchService.copyGroup(this.model.getBatch_id(), this.model.getIds(), user.getUserid());	
				BatchPlanLog log=new BatchPlanLog();
				log.setCreater(String.valueOf(user.getUserid()));
				log.setCreater_time(new Date());
				log.setProject_id(String.valueOf(cif.getId()));
				log.setProject_name(cif.getBatch_name());
				log.setProject_status("复制分组信息");
				log.setProject_type("24");
				log.setType("2");
				this.crmSignBillPlanService.saveBatchPlanLog(log);
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("818");//子功能id 必须填写
				sl.setExplain("要复制的批次 "+model.getBatch_num()+" "+model.getBatch_name()+" "+this.model.getIds());//操作说明
				syslogService.saveSysLog(sl);
				message="ok-复制分组完成";
			}			
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: groupcopedo   
	     * @Description: 819 执行分组复制
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String groupcopydo() throws WebException, SQLException {		
		// 获取体检项目
		String itementities = this.model.getItementities();
		itementities = itementities.replace("}{", "},{");
		itementities = "[" + itementities + "]";
		JSONArray itemarray = JSONArray.fromObject(itementities);
		@SuppressWarnings("unchecked")
		List<GroupChargingItemDTO> listitem = (List<GroupChargingItemDTO>) JSONArray.toCollection(itemarray,
				GroupChargingItemDTO.class);
		
		this.session.remove("listitem");
		this.session.put("listitem",listitem);
		
		// 获取体检套餐
		String setentities = model.getSetentities();
		setentities = setentities.replace("}{", "},{");
		setentities = "[" + setentities + "]";
		JSONArray setarray = JSONArray.fromObject(setentities);
		@SuppressWarnings("unchecked")
		List<ExamSetDTO> listset = (List<ExamSetDTO>) JSONArray.toCollection(setarray, ExamSetDTO.class);
		this.session.remove("listset");
		this.session.put("listset",listset);
		this.message="ok-复制成功";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: grouppostdo
	     * @Description: 820 粘贴套餐和项目   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String grouppostdo() throws WebException, SQLException {	
		UserDTO user = (UserDTO) session.get("username");
		// 获取体检项目
		List<GroupChargingItemDTO> listitem  = new ArrayList<GroupChargingItemDTO>();
		List<GroupChargingItemDTO> listitemnew  = new ArrayList<GroupChargingItemDTO>();
		listitem = (List)this.session.get("listitem");
		if((listitem!=null)&&(listitem.size()>0)){
		for(GroupChargingItemDTO item:listitem){
			ChargingItem ci= new ChargingItem();
			ci=this.batchService.getChargingItemBycode(item.getItem_code());
			if((ci!=null)&&("Y".equals(ci.getIsActive()))){
				listitemnew.add(item);
			}
		}
		}
		
		// 获取体检套餐
		List<ExamSetDTO> listset= new ArrayList<ExamSetDTO>();
		List<ExamSetDTO> listsetnew= new ArrayList<ExamSetDTO>();
		listset = (List)this.session.get("listset");
		if((listset!=null)&&(listset.size()>0)){
			for(ExamSetDTO esd:listset){
				ExamSet es = new ExamSet();
				es=this.batchService.getExamSetByNum(esd.getSet_num(),user.getCenter_num());
				if((es!=null)&&("Y".equals(es.getIs_Active()))){
					listsetnew.add(esd);
				}
			}
		}
		
		GroupItemSetDTO gisd=new GroupItemSetDTO();
		gisd.setListitem(listitemnew);
		gisd.setListset(listsetnew);
		gisd.setMessage("ok-ok");
		this.outJsonResult(gisd);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: cusbatchedit   
	     * @Description: TODO(新增体检任务)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cusbatchedit() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		if (this.model.getId() > 0) {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getId());
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			model.setCharge_type(batch.getCharge_type());
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			 if ((batch.getExam_date() != null) && (batch.getExam_date().indexOf("1900-01-01") < 0)) {
			        this.model.setExam_date(batch.getExam_date());
			      } else {
			        this.model.setExam_date("");
			      }
			 if ((batch.getExam_date_end() != null) && (batch.getExam_date_end().indexOf("1900-01-01") < 0)) {
			        this.model.setExam_date_end(batch.getExam_date_end());
			      } else {
			        this.model.setExam_date_end("");
			      }
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());
			model.setIs_Active("Y");
			model.setPay_way(batch.getPay_way());
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
			model.setIs_showamount(batch.getIs_showamount());
			model.setIs_showseal(batch.getIs_showseal());
		} else {
			Batch batch = new Batch();
			batch.setBatch_num(this.batchService.GetCreateID("batch_num", user.getCenter_num()));
			BeanUtil.copy(model, batch);
			this.model.setExam_fee("0");
			this.model.setContact_name(cif.getContact_Name());
			this.model.setPhone(cif.getContact_Phone());
		}
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1241");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 复制批次-选择源批次1242
	     * @Title: docoptbatchfrom   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String docoptbatchfrom() throws WebException, SQLException {
		if (this.model.getBatch_id()<= 0) {
			this.message = "error-批次无效";
		} else {
			this.session.remove("batchcopy");
			this.session.put("batchcopy", this.model.getBatch_id());
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("1242");//子功能id 必须填写
			sl.setExplain("选择要复制的批次号"+this.model.getBatch_id());//操作说明
			syslogService.saveSysLog(sl);
			this.message="ok-选择要复制的批次成功。";
		}
		this.outJsonStrResult(message);
		return NONE;
	}	
	
	/**
	 * 复制批次-复制到目标单位下面1243
	     * @Title: docoptbatchto   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String docoptbatchto() throws WebException {
		if (this.model.getCompany_id() <= 0) {
			this.message = "error-选择单位无效";
		} else {
			try{
				UserDTO user = (UserDTO) session.get("username");
				//long batch_id=(Long)this.session.get("batchcopy");
				long batch_id = this.model.getBatch_id();
				int group_order=this.batchService.getGroupForbatidOrder(batch_id);
				this.message = this.batchService.setCopybatchToCom(this.model.getCompany_id(),batch_id,group_order,user.getUserid(),user.getCenter_num());				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1243");//子功能id 必须填写
				sl.setExplain("批次号"+this.model.getBatch_id()+"复制到单位"+this.model.getCompany_id()+"成功");//操作说明
				syslogService.saveSysLog(sl);
			}catch(Exception ex){
				this.message = "error-操作失败";
			}
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: saveGroupOrder   
	     * @Description: 保存排序   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveGroupOrder() throws WebException, SQLException {
		if(this.model.getIds()!=null && this.model.getIds().length()>0){
		String[] ids = this.model.getIds().split(",");
		int group_order=0;
		for(int i=0;i<ids.length;i++){
			try{
			group_order++;			
			GroupInfo gi = this.batchService.getGroupByID(Long.valueOf(ids[i]).longValue());
			gi.setGroup_order(group_order);
			this.batchService.updateGroup(gi);
			}catch(Exception ex){}
		}
		}
		this.outJsonStrResult("ok");
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateDjtPanelStopOrStart   
	     * @Description: TODO(修改)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateDjtPanelStopOrStart()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		try { 
			GroupInfo eti = this.batchService.getGroupByID(Long.valueOf(model.getIds()));
			if(eti.getIsActive().equals("Y")){
				eti.setIsActive("N");
				eti.setUpdate_time(DateTimeUtil.parse());;
				eti.setUpdater(user.getUserid());
			}else if(eti.getIsActive().equals("N")){
				eti.setIsActive("Y");
				eti.setUpdate_time(DateTimeUtil.parse());
				eti.setUpdater(user.getUserid());
			}
			this.batchService.updateGroupInfo(eti);
			this.message="ok-成功";
		} catch (Exception e) {
			this.message="error-"+e.toString();
			e.printStackTrace();
		}
	
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	/**
	 * 
	     * @Title: setforchangitemlistNoECI   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String setforchangitemlistNoECI() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<GroupChargingItemDTO> batchlist = new ArrayList<GroupChargingItemDTO>();
		//this.model.getTijiantype() 保存T或者从前端传递过来
		batchlist = this.batchService.getsetForchargItemListBysetnumNOECI(this.model.getSet_num(),this.model.getTijiantype(),this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}
	
	
	

	/**
	 * 
	     * @Title: dooverbatchto   
	     * @Description: 对批次进行封帐操作 1800 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String dooverbatchto() throws WebException {
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatch_id()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatch_id());
			if("1".equals(ta.getOverflag())) {
				this.message = "error-批次已经结帐，操作不能继续";
			}else if("1".equals(ta.getOverflag())){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getOvercreater());
				this.message = "error-批次由【"+wu.getChi_Name()+"】结帐，操作不能继续";
			}else{
				UserDTO user = (UserDTO) session.get("username");
				ta.setOvercreater(user.getUserid());
				if(StringUtil.isEmpty(this.model.getUpdate_time())){
					ta.setOverdate(DateTimeUtil.getDateTime());
				}else{
					ta.setOverdate(this.model.getUpdate_time().trim());
				}
				ta.setOverflag("1");//封帐
				this.batchService.updateBatch(ta);
				this.message = "ok-执行结帐操作成功";
				
				BatchFlowLog bfl = new BatchFlowLog();
				bfl.setBatch_id(this.model.getBatch_id());
				bfl.setCenter_num(user.getCenter_num());
				bfl.setCreatedate(DateTimeUtil.getDateTime());
				bfl.setFlow_type(3);
				bfl.setCreater(user.getUserid());
				bfl.setGroup_id(0);
				bfl.setNotes("");
				bfl.setRemark("");
				this.batchService.saveBatchFlowLog(bfl);
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1800");//子功能id 必须填写
				sl.setExplain(this.model.getBatch_id()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	
	/**
	 * 
	     * @Title: queryGroupInfoAmount   
	     * @Description: TODO(根据批次查询返回最大金额)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryGroupInfoAmount() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.message = "ok-NoAmount";
		//是否有最大金额限制
		String IS_HAVE_MAX_AMOUNT = this.customerInfoService.getCenterconfigByKey("IS_HAVE_MAX_AMOUNT", user.getCenter_num()).getConfig_value();
		if(!"".equals(IS_HAVE_MAX_AMOUNT) && IS_HAVE_MAX_AMOUNT!=null){
			if("Y".equalsIgnoreCase(IS_HAVE_MAX_AMOUNT)) {
				if (this.model.getGroup_id()!=0) {
					GroupInfo gi = batchService.getGroupByID(model.getGroup_id());
						if(gi.getBatch_id()>0) {
							this.message = "ok-"+gi.getMaximum_amount();
					}
				}else{
					this.message = "error-无效分组ID";
				}
			}
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	public String getBatchById(){
		BatchDTO batch = batchService.getBatchDTOById(this.model.getBatch_id()+"");
		this.outJsonResult(batch);
		return NONE;
	}
	
	
	public String getBathByGroupid(){
		UserDTO user = (UserDTO) session.get("username");
		BatchDTO batch = batchService.getBathByGroupid(this.model.getGroup_id(),user.getCenter_num());
		this.outJsonResult(batch);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: dooverbatchtoshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String doaccountbatchtoshow() throws WebException {
		this.model.setUpdate_time(DateTimeUtil.getDate2());
		return SUCCESS;
	}
    /**
     * 
         * @Title: dooverbatchto   
         * @Description: 对批次进行锁定 1267  
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String doaccountbatchto() throws WebException {
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatch_id()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatch_id());
			if("1".equals(ta.getOverflag())) {
				this.message = "error-批次已经结帐，操作不能继续";
			}else if("2".equals(ta.getOverflag())) {
				this.message = "error-批次已经预结帐，操作不能继续";
			}else if(ta.getAccountflag()==1){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getCheckuser());
				this.message = "error-批次由【"+wu.getChi_Name()+"】锁定，操作不能继续";
			}else{
				UserDTO user = (UserDTO) session.get("username");
				ta.setAccountcreater(user.getUserid());
				if(StringUtil.isEmpty(this.model.getUpdate_time())){
					ta.setAccountdate(DateTimeUtil.getDateTime());
				}else{
					ta.setAccountdate(this.model.getUpdate_time().trim());
				}
				ta.setAccountflag(1);
				this.batchService.updateBatch(ta);
				this.message = "ok-执行锁定操作成功";
				
				BatchFlowLog bfl = new BatchFlowLog();
				bfl.setBatch_id(this.model.getBatch_id());
				bfl.setCenter_num(user.getCenter_num());
				bfl.setCreatedate(DateTimeUtil.getDateTime());
				bfl.setFlow_type(3);
				bfl.setCreater(user.getUserid());
				bfl.setGroup_id(0);
				bfl.setNotes("");
				bfl.setRemark("");
				this.batchService.saveBatchFlowLog(bfl);
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1267");//子功能id 必须填写
				sl.setExplain(this.model.getBatch_id()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	 /**
     * 
         * @Title: dooverbatchto   
         * @Description: 对批次解除锁定 1268 
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String doaccountbatchtoren() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatch_id()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatch_id());
			if("1".equals(ta.getOverflag())) {
				this.message = "error-批次已经结帐，操作不能继续";
			}else if("2".equals(ta.getOverflag())) {
				this.message = "error-批次已经预结帐，操作不能继续";
			}else if(ta.getAccountflag()==0){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getAccountcreater());
				this.message = "error-批次由【"+wu.getChi_Name()+"】解除锁定，操作不能继续";
			}else if(ta.getAccountcreater()!=user.getUserid()){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getAccountcreater());
				this.message = "error-批次只能由【"+wu.getChi_Name()+"】解除锁定，操作不能继续";
			}else{				
				ta.setAccountcreater(user.getUserid());
				ta.setAccountdate(DateTimeUtil.getDateTime());
				ta.setAccountflag(0);
				
				this.batchService.updateBatch(ta);
				this.message = "ok-执行解除锁定成功";
				
				
				BatchFlowLog bfl = new BatchFlowLog();
				bfl.setBatch_id(this.model.getBatch_id());
				bfl.setCenter_num(user.getCenter_num());
				bfl.setCreatedate(DateTimeUtil.getDateTime());
				bfl.setFlow_type(4);
				bfl.setCreater(user.getUserid());
				bfl.setGroup_id(0);
				bfl.setNotes("");
				bfl.setRemark("");
				this.batchService.saveBatchFlowLog(bfl);
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1268");//子功能id 必须填写
				sl.setExplain(this.model.getBatch_id()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	


	 /**
     * 
         * @Title: dooverbatchto   
         * @Description: 对批次解除预结账1801 
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String dooverbatchtoren() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatch_id()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatch_id());
			if("0".equals(ta.getOverflag())){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getOvercreater());
				this.message = "error-批次由【"+wu.getChi_Name()+"】解除结帐，操作不能继续";
			}else if("1".equals(ta.getOverflag())){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getOvercreater());
				this.message = "error-批次已经结帐，操作不能继续";
			}else if(ta.getOvercreater()!=user.getUserid()){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getOvercreater());
				this.message = "error-批次只能由【"+wu.getChi_Name()+"】解除结帐，操作不能继续";
			}else{				
				ta.setOvercreater(user.getUserid());
				ta.setOverdate(DateTimeUtil.getDateTime());
				ta.setOverflag("0");
				
				this.batchService.updateBatch(ta);
				this.message = "ok-执行解除结帐成功";
				
				
				BatchFlowLog bfl = new BatchFlowLog();
				bfl.setBatch_id(this.model.getBatch_id());
				bfl.setCenter_num(user.getCenter_num());
				bfl.setCreatedate(DateTimeUtil.getDateTime());
				bfl.setFlow_type(4);
				bfl.setCreater(user.getUserid());
				bfl.setGroup_id(0);
				bfl.setNotes("");
				bfl.setRemark("");
				this.batchService.saveBatchFlowLog(bfl);
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1801");//子功能id 必须填写
				sl.setExplain(this.model.getBatch_id()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: docheckbatchto   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String docheckbatchto() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatch_id()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatch_id());
			if(ta.getChecktype()==2){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getCheckuser());
				this.message = "error-批次只能由【"+wu.getChi_Name()+"】审核，操作不能继续";
			}else if(("1".equals(ta.getOverflag()))) {
				this.message = "error-批次已经结帐，操作不能继续";
			}else if(("2".equals(ta.getOverflag()))) {
				this.message = "error-批次已经预结帐，操作不能继续";
			}else if(ta.getAccountflag()==1){
				this.message = "error-批次已经锁定，操作不能继续";
			}else{
				ta.setChecktype(2);
				ta.setCheckuser(user.getUserid());
				ta.setCheckdate(DateTimeUtil.getDateTime());
				this.batchService.updateBatch(ta);
				this.message = "ok-执行审核操作成功";
				
				BatchFlowLog bfl = new BatchFlowLog();
				bfl.setBatch_id(this.model.getBatch_id());
				bfl.setCenter_num(user.getCenter_num());
				bfl.setCreatedate(DateTimeUtil.getDateTime());
				bfl.setFlow_type(1);
				bfl.setCreater(user.getUserid());
				bfl.setGroup_id(0);
				bfl.setNotes("");
				bfl.setRemark("");
				this.batchService.saveBatchFlowLog(bfl);
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1269");//子功能id 必须填写
				sl.setExplain(this.model.getBatch_id()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	 /**
     * 
         * @Title: docheckbatchtoren   
         * @Description: 对批次解除审核 1270
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String docheckbatchtoren() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id()<=0) {
			this.message = "error-无效单位";
		}else if (this.model.getBatch_id()<=0) {
			this.message = "error-无效批次任务";
		}else{
			Batch ta=new Batch();			
			ta = this.batchService.getBatchByID(this.model.getBatch_id());
			if(ta.getChecktype()==0){
				WebUserInfo wu= this.userInfoService.getUserInfo(ta.getCheckuser());
				this.message = "error-批次由【"+wu.getChi_Name()+"】解除审核，操作不能继续";
			}else if("1".equals(ta.getOverflag())) {
				this.message = "error-批次已经结帐,操作不能继续";
			}else if("2".equals(ta.getOverflag())) {
				this.message = "error-批次已经预结帐,操作不能继续";
			}else if(ta.getAccountflag()==1){
				this.message = "error-批次已经锁定，操作不能继续";
			}else{				
				ta.setChecktype(0);
				ta.setCheckuser(user.getUserid());
				ta.setCheckdate(DateTimeUtil.getDateTime());
				this.batchService.updateBatch(ta);
				this.message = "ok-执行解除审核成功";			
				
				BatchFlowLog bfl = new BatchFlowLog();
				bfl.setBatch_id(this.model.getBatch_id());
				bfl.setCenter_num(user.getCenter_num());
				bfl.setCreatedate(DateTimeUtil.getDateTime());
				bfl.setFlow_type(2);
				bfl.setCreater(user.getUserid());
				bfl.setGroup_id(0);
				bfl.setNotes("");
				bfl.setRemark("");
				this.batchService.saveBatchFlowLog(bfl);
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("1270");//子功能id 必须填写
				sl.setExplain(this.model.getBatch_id()+"");//操作说明
				syslogService.saveSysLog(sl);
			}			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateGroupStatusById   
	     * @Description: TODO(修改分组状态)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public  String updateGroupStatusById(){
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.batchService.updateGroupStatusById(this.model,user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
}