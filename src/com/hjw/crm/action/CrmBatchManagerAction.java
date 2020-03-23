package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.hsqldb.lib.StringUtil;

import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.domain.CrmBatchCheck;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.service.CrmBatchCheckService;
import com.hjw.crm.service.CrmSignBillPlanService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.GroupChargingItem;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.domain.GroupSet;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.BatchModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.BeanUtil;
import com.synjones.framework.web.action.BaseAction;

public class CrmBatchManagerAction extends BaseAction implements ModelDriven {
	private SyslogService syslogService;
	private BatchModel model = new BatchModel();
	private CompanyService companyService;
	private BatchService batchService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CustomerInfoService customerInfoService;
	private UserInfoService userInfoService;
	private CrmBatchCheckService crmBatchCheckService;
	private CrmSignBillPlanService crmSignBillPlanService;

	public CrmSignBillPlanService getCrmSignBillPlanService() {
		return crmSignBillPlanService;
	}

	public void setCrmSignBillPlanService(CrmSignBillPlanService crmSignBillPlanService) {
		this.crmSignBillPlanService = crmSignBillPlanService;
	}

	public CrmBatchCheckService getCrmBatchCheckService() {
		return crmBatchCheckService;
	}

	public void setCrmBatchCheckService(CrmBatchCheckService crmBatchCheckService) {
		this.crmBatchCheckService = crmBatchCheckService;
	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public BatchService getBatchService() {
		return batchService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public void setModel(BatchModel model) {
		this.model = model;
	}
	/**
	 * 
	     * @Title: crm160crm的体检任务页面显示   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws UnsupportedEncodingException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatchshow() throws WebException, SQLException, UnsupportedEncodingException {
		if(this.model.getSign_num()!=null&&this.model.getSign_num().length()>0){
			this.model.setSign_num(this.model.getSign_num());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm160");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm161crm的体检分组页面显示   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmgroupmanager() throws WebException, SQLException {
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
			this.model.setComname(cif.getCom_Name());
			this.model.setSign_num(this.model.getSign_num());
		}

		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
			this.model.setApptype(cif.getApptype());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm161");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm162crm人员计划显示   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatchproshow() throws WebException, SQLException {
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
			this.model.setComname(cif.getCom_Name());
			this.model.setSign_num(this.model.getSign_num());
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
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm162");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm163体检任务编辑页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatchedit() throws WebException, SQLException {
		if (this.model.getSign_num() == null || this.model.getSign_num().length() == 0) {
			this.message = "选择签单计划无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
			this.model.setComname(cif.getCom_Name());
			this.model.setCompany_ids(cif.getId());
			if (this.model.getId() > 0) {
				Batch batch = new Batch();
				batch = this.batchService.getBatchByID(this.model.getId());
				if(batch.getIs_Active().equals("T")){
					this.message = "该体检任务已提交审核，不能修改";
					return "commerror";
				}else{
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
						this.model.setEnd_date(batch.getExam_date_end());
					} else {
						this.model.setEnd_date("");
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
					model.setApptype(batch.getApptype());
					List<CrmBatchCheck> crmBatchChecklist=(List<CrmBatchCheck>) this.crmBatchCheckService.getCrmBatchCheck(String.valueOf(batch.getId()));
					List<String> strList=new ArrayList<String>();
					if(crmBatchChecklist!=null&&crmBatchChecklist.size()>0){
						for(int i=0;i<crmBatchChecklist.size();i++){
							strList.add(String.valueOf(crmBatchChecklist.get(i).getCheck_type()));
						}
					}
					if(strList!=null&&strList.size()>0){
						model.setCheck_type(StringUtils.join(strList.toArray(),","));
					}
				}
			} else {
				Batch batch = new Batch();
				batch.setBatch_num(DateTimeUtil.getDate() + "-");
				CrmSignBillPlan crmSignBillPlan=new CrmSignBillPlan();
				if (model.getSign_num() != null && model.getSign_num().length() > 0) {
					this.model.setSign_nums(model.getSign_num());
					crmSignBillPlan=this.crmSignBillPlanService.getSignBillPlanByNum(model.getSign_num());
				}
				BeanUtil.copy(model, batch);
				this.model.setContact_name(cif.getContact_Name());
				this.model.setPhone(cif.getContact_Phone());
				if(crmSignBillPlan!=null){
					if(crmSignBillPlan.getSign_persion()!=null){
						this.model.setExam_number(crmSignBillPlan.getSign_persion());	
					}
					if(crmSignBillPlan.getSign_amount()!=null){
						this.model.setExam_fee(String.valueOf(crmSignBillPlan.getSign_amount()));
					}else{
						this.model.setExam_fee("0");
					}
					if(crmSignBillPlan.getSign_date()!=null){
						this.model.setExam_date(DateTimeUtil.shortFmt2(crmSignBillPlan.getSign_date()));
					}
					if(crmSignBillPlan.getEnd_date()!=null){
						this.model.setEnd_date(DateTimeUtil.shortFmt2(crmSignBillPlan.getEnd_date()));
					}
					if(crmSignBillPlan.getCreater()!=0){
						WebUserInfo web=this.userInfoService.loadUserInfo(crmSignBillPlan.getCreater());
						this.model.setSales_name(web.getChi_Name());
					}
				}
				
			}
		}

		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm163");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm133crm系统的体检任务维护功能   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws UnsupportedEncodingException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmBatchManagerPage() throws WebException, SQLException, UnsupportedEncodingException {
		if(model.getSign_num()!=null&&model.getSign_num().length()>0){
			this.model.setSign_num(this.model.getSign_num());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm133");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm169体检任务列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatchlistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.batchService.getbatchListSignNum(this.model.getSign_num(),model.getApptype(), this.rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(batchlist);
//		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm169");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}

	/**
	 * 
	     * @Title: crm170体检任务编辑   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatcheditdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getId() > 0) {
			BatchDTO bdot = new BatchDTO();
			bdot = crmBatchCheckService.getbatchForcomidandname(this.model.getId(), this.model.getBatch_name(), this.model.getSign_num(),user.getCenter_num());
			if ((bdot != null) && (bdot.getId() != this.model.getId())) {
				this.message = "error-体检任务名称重复，不能修改！";
			} else {
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
						bc.setIs_Active("C");
						bc.setPay_way(model.getPay_way());
						bc.setPhone(model.getPhone());
						bc.setQian_remark(model.getQian_remark());
						bc.setRemark(model.getRemark());
						bc.setSales_name(model.getSales_name());
						bc.setId(this.model.getId());
						bc.setUpdater(user.getUserid());
						bc.setUpdate_time(DateTimeUtil.parse());
						bc.setApptype(this.model.getTijiantype());
						bc = this.batchService.updateBatch(bc);
						this.crmBatchCheckService.deleteCrmBatchCheck(this.model.getId());
						if(!StringUtil.isEmpty(this.model.getCheck_type())){
							String[] strs=this.model.getCheck_type().split(",");
							for(int i=0;i<strs.length;i++){
								CrmBatchCheck crmBatchCheck=new CrmBatchCheck();
								crmBatchCheck.setBatch_id(this.model.getId());
								crmBatchCheck.setCheck_status("0");
								crmBatchCheck.setCheck_type(strs[i]);
								this.crmBatchCheckService.addCrmBatchCheck(crmBatchCheck);	
							}
						}
						BatchPlanLog log=new BatchPlanLog();
						log.setCreater(String.valueOf(user.getUserid()));
						log.setCreater_time(new Date());
						log.setProject_id(String.valueOf(bc.getId()));
						log.setProject_name(bc.getBatch_name());
						log.setProject_status("修改体检任务");
						log.setProject_type("7");
						log.setType("2");
						this.crmSignBillPlanService.saveBatchPlanLog(log);
						this.message = "ok-体检任务修改成功！";

						user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("crm170");// 子功能id 必须填写
						sl.setExplain("修改批次任务 " + bc.getBatch_num() + " " + bc.getBatch_name());// 操作说明
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
			bc.setIs_Active("C");
			bc.setPay_way(model.getPay_way());
			bc.setPhone(model.getPhone());
			bc.setQian_remark(model.getQian_remark());
			bc.setRemark(model.getRemark());
			bc.setSales_name(model.getSales_name());
			bc.setSign_num(model.getSign_num());
			bc.setApptype(model.getTijiantype());
			bdot = this.batchService.getbatchForcomidandname(model.getCompany_id(), model.getBatch_name(),user.getCenter_num(),0);
			if ((bdot != null) && (bdot.getId() > 0)) {
				this.message = "error-体检任务名称重复，不能新增！";
			} else {
				bc.setChecktype(0);
				bc.setCheckuser(user.getUserid());
				bc.setCreater(user.getUserid());
				bc.setCreate_time(DateTimeUtil.parse());
				bc.setUpdater(user.getUserid());
				bc.setUpdate_time(DateTimeUtil.parse());
				bc = this.batchService.saveBatch(bc);
				user = (UserDTO) session.get("username");
				if(!StringUtil.isEmpty(this.model.getCheck_type())){
					String[] strs=this.model.getCheck_type().split(",");
					for(int i=0;i<strs.length;i++){
						CrmBatchCheck crmBatchCheck=new CrmBatchCheck();
						crmBatchCheck.setBatch_id(bc.getId());
						crmBatchCheck.setCheck_status("0");
						crmBatchCheck.setCheck_type(strs[i]);
						this.crmBatchCheckService.addCrmBatchCheck(crmBatchCheck);	
					}
				}
				CrmSignBillPlan crmSignBillPlan=this.crmSignBillPlanService.getSignBillPlanByNum(model.getSign_num());
				if(crmSignBillPlan.getTrack_progress().equals("2")){
					crmSignBillPlan.setTrack_progress("3");
					crmSignBillPlan.setTrack_time(new Date());
					this.crmSignBillPlanService.updateSignBillPlan(crmSignBillPlan);
				}
				BatchPlanLog log=new BatchPlanLog();
				log.setCreater(String.valueOf(user.getUserid()));
				log.setCreater_time(new Date());
				log.setProject_id(String.valueOf(bc.getId()));
				log.setProject_name(bc.getBatch_name());
				log.setProject_status("新增体检任务");
				log.setProject_type("8");
				log.setType("2");
				this.crmSignBillPlanService.saveBatchPlanLog(log);
				BatchPlanLog log1=new BatchPlanLog();
				log1.setCreater(String.valueOf(user.getUserid()));
				log1.setCreater_time(new Date());
				log1.setProject_id(model.getSign_num());
				log1.setProject_name(crmSignBillPlan.getSign_name());
				log1.setProject_status("开始制作方案");
				log1.setProject_type("19");
				log1.setType("1");
				this.crmSignBillPlanService.saveBatchPlanLog(log1);
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("crm170");// 子功能id 必须填写
				sl.setExplain("新增批次任务信息 " + bc.getBatch_num() + " " + bc.getBatch_name());// 操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-体检任务增加成功！";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: crm164体检任务查看   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatchoneshow() throws WebException, SQLException {
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.message = "选择签单计划无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
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

			if ((batch.getExam_date() != null) && (batch.getExam_date().trim().length() > 10)) {

				batch.setExam_date(batch.getExam_date().trim().substring(0, 10));
				if ("1900-01-01".equals(batch.getExam_date())) {
					model.setExam_date("");
				} else {
					model.setExam_date(batch.getExam_date());
				}
			} else {
				model.setExam_date("");
			}

			if ((batch.getExam_date_end() != null) && (batch.getExam_date_end().trim().length() > 10)) {

				batch.setExam_date_end(batch.getExam_date_end().trim().substring(0, 10));
				if ("1900-01-01".equals(batch.getExam_date_end())) {
					model.setExam_date_end("");
				} else {
					model.setExam_date_end(batch.getExam_date_end());
				}
			} else {
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
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm164");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	     * @Title: crm165分组编辑   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmgroupedit() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.message = "选择签单计划无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
			this.model.setComname(cif.getCom_Name());
			String sign_name=this.crmBatchCheckService.getSign_name(this.model.getSign_num());
			this.model.setSign_name(sign_name);
		}
		if (this.model.getBatch_id() <= 0) {
			this.message = "选择体检任务无效";
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
				model.setApptype(this.model.getApptype());
				this.model.setItem_amount(this.batchService.getGroupcharItemAmt(this.model.getId()));
		} else {
			model.setMax_age(100);
			model.setMin_age(1);
			model.setAmount(0);
			model.setExam_indicator("T");
			model.setDiscount(10.00);
			model.setItem_amount(0);
			model.setApptype(this.model.getApptype());
		}
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("crm001")){
					model.setWebResource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}	
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm165");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	     * @Title: crm166复制分组   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmcopygroupmanager() throws WebException, SQLException {
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.message = "选择签单计划无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
			this.model.setCompany_id(cif.getId());
			this.model.setComname(cif.getCom_Name());
			String sign_name=this.crmBatchCheckService.getSign_name(this.model.getSign_num());
			this.model.setSign_name(sign_name);
		}

		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("所选体检任务无效");
		} else {
			Batch cif = new Batch();
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm166");// 子功能id 必须填写
		sl.setExplain("要复制的批次 " + model.getBatch_num() + " " + model.getBatch_name());// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: crm167体检人员计划编辑   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmbatchproplanedit() throws WebException, SQLException {
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
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
			this.model.setExam_date(cif.getExam_date());
			this.model.setExam_date_end(cif.getExam_date_end());
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
		sl.setXtgnid2("crm167");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: crm168分组查看   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmgroupOneshow() throws WebException, SQLException {
		if (StringUtil.isEmpty(this.model.getSign_num())) {
			this.message = "选择签单无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComBySignNum(this.model.getSign_num());
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
		UserDTO user = (UserDTO) session.get("username");
    	SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm168");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: crm171crm的合同管理   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmcontractManager() throws WebException, SQLException {
    	UserDTO user = (UserDTO) session.get("username");
    	SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm171");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: crm172crm单独合同显示界面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmcontractonecheckshow() throws WebException, SQLException {
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
//		UserDTO user = (UserDTO) session.get("username");
    	SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm172");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: crm173crm合同审核界面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmcontractcheckedit() throws WebException, SQLException {
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
        		sl.setXtgnid2("crm173");//子功能id 必须填写
        		sl.setExplain("");//操作说明
        		syslogService.saveSysLog(sl);
            	return SUCCESS;
            }
		}		
		
	}
	
	/**
	 * 
	     * @Title: crm174crm获取体检任务下拉列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String crmBatch() throws WebException, SQLException {
			UserDTO user = (UserDTO) session.get("username");
			List list = this.crmBatchCheckService.getBatch(this.model.getSign_num(),user.getCenter_num());
			this.outJsonResult( list );
//			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm145");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
	}
	
	/**
	 * 
	     * @Title: crm175crm合同管理列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	  public String crmcontractlistshow() throws WebException, SQLException { 
		  UserDTO user = (UserDTO) session.get("username");
	    	PageReturnDTO contractlist = new PageReturnDTO();
	    	contractlist = this.crmBatchCheckService.getcrmBatchContract(this.model,this.rows, this.getPage(),user.getCenter_num());
			this.outJsonResult(contractlist);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm175");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
	  /**
	   * 获取体检任务复制页面
	       * @Title: crmbatcheditpage   
	       * @Description: TODO(这里用一句话描述这个方法的作用)   
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String crmbatcheditpage()throws WebException,SQLException{
		  model.setSign_num(this.model.getSign_num());
		  UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm145");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
	  }
	  /**
	   * 
	       * @Title: getSignBillPlan   
	       * @Description: 获取签单计划喝体检任务   
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String getSignBillPlan() throws WebException,SQLException{
		  UserDTO user = (UserDTO) session.get("username");
		  List<CrmSignBillPlanDTO> list=this.crmBatchCheckService.getSignBillPlan(model.getSign_name(),user.getUserid(), this.rows,this.getPage(),user.getCenter_num());
			this.outJsonResult(list);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm175");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		  return NONE;
	  }
	  /**
	   * 获取添加体检任务名称页面
	       * @Title: getSignPlanPage   
	       * @Description: TODO(这里用一句话描述这个方法的作用)   
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String getSignPlanPage()throws WebException,SQLException{
		  String id=String.valueOf(model.getId());
		  model.setId(model.getId());
		  model.setSign_num(model.getSign_num());
		  BatchDTO batch=this.batchService.getBatchDTOById(id);
		  if(batch!=null){
			  this.model.setComname(batch.getCom_name());
			  this.model.setSign_name(batch.getSign_name());
			  this.model.setCreate_time(batch.getCreate_time());
			  this.model.setBatch_name(batch.getBatch_name());
		  }
		  UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm175");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		  return SUCCESS;
	  }
	  /**
	   * 
	       * @Title: copyCrmBatch   
	       * @Description: 复制体检任务   
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String copyCrmBatch()throws WebException,SQLException{
		  UserDTO user = (UserDTO) session.get("username");
		  Batch oldBatch=new Batch();
		  oldBatch=this.crmBatchCheckService.getBatchById(String.valueOf(model.getId()));
		  Batch newBatch=new Batch();
		  newBatch.setBatch_name(model.getBatch_name());
		  newBatch.setId(0);
		  newBatch.setCompany_id(oldBatch.getCompany_id());
		  newBatch.setBatch_num(model.getBatch_name());
		  newBatch.setPay_way(oldBatch.getPay_way());
		  newBatch.setCreater(user.getUserid());
		  newBatch.setCreate_time(new Date());
		  newBatch.setIs_Active("C");
		  newBatch.setExam_item(oldBatch.getExam_item());
		  newBatch.setExam_number(oldBatch.getExam_number());
		  newBatch.setExam_date(oldBatch.getExam_date());
		  newBatch.setCharge_type(oldBatch.getCharge_type());
		  newBatch.setContact_name(oldBatch.getContact_name());
		  newBatch.setSales_name(oldBatch.getSales_name());
		  newBatch.setIntroducer_name(oldBatch.getIntroducer_name());
		  newBatch.setAccommodation(oldBatch.getAccommodation());
		  newBatch.setDine(oldBatch.getDine());
		  newBatch.setExam_fee(oldBatch.getExam_fee());
		  newBatch.setRemark(oldBatch.getRemark());
		  newBatch.setPhone(oldBatch.getPhone());
		  newBatch.setInvoice_title(oldBatch.getInvoice_title());
		  newBatch.setBatch_address(oldBatch.getBatch_address());
		  newBatch.setQian_remark(oldBatch.getQian_remark());
		  newBatch.setSettlement(oldBatch.getSettlement());
		  newBatch.setChecktype(oldBatch.getChecktype());//审核这块需要讨论
		  newBatch.setCheckuser(oldBatch.getCheckuser());
		  newBatch.setCheckdate(oldBatch.getCheckdate());
		  newBatch.setChecknotice(oldBatch.getChecknotice());
		  newBatch.setOverflag(oldBatch.getOverflag());
		  newBatch.setExam_date_end(oldBatch.getExam_date_end());
		  newBatch.setApptype(oldBatch.getApptype());
		  newBatch.setSign_num(model.getSign_num());
		  newBatch = this.batchService.saveBatch(newBatch);
		  //体检任务审核表
		  List<CrmBatchCheck> list=this.crmBatchCheckService.getCrmBatchCheck(String.valueOf(oldBatch.getId()));
		  if(list!=null&&list.size()>0){
			  for(CrmBatchCheck crmBatchCheck:list){
				  CrmBatchCheck crm=new CrmBatchCheck();
				  crm.setBatch_id(newBatch.getId());
				  crm.setCheck_status("0");
				  crm.setCheck_type(crmBatchCheck.getCheck_type());
				  crm.setCheckdate(new Date());
				  crm.setChecknotice(crmBatchCheck.getChecknotice());
				  crm.setCheckuser(user.getUserid());
				  this.crmBatchCheckService.addCrmBatchCheck(crm);
			  }
		  }
		  //分组
		  List<GroupInfo> list1=this.crmBatchCheckService.getGroupInfoByBatchId(String.valueOf(oldBatch.getId()));
		  if(list1!=null&&list1.size()>0){
			  for(GroupInfo groupInfo:list1){
				  GroupInfo group=new GroupInfo();
				  group.setBatch_id(newBatch.getId());
				  group.setGroup_num(groupInfo.getGroup_num());
				  group.setGroup_name(groupInfo.getGroup_name());
				  group.setStart_date(groupInfo.getStart_date());
				  group.setEnd_date(groupInfo.getEnd_date());
				  group.setSex(groupInfo.getSex());
				  group.setMin_age(groupInfo.getMin_age());
				  group.setMax_age(groupInfo.getMax_age());
				  group.setIs_Marriage(groupInfo.getIs_Marriage());
				  group.setPosttion(groupInfo.getPosttion());
				  group.setDiscount(groupInfo.getDiscount());
				  group.setAmount(groupInfo.getAmount());
				  group.setGroup_index(groupInfo.getGroup_index());
				  group.setIsActive(groupInfo.getIsActive());
				  group.setCreate_time(new Date());
				  group.setCreater(user.getUserid());
				  group.setUpdater(groupInfo.getUpdater());
				  group.setUpdate_time(groupInfo.getUpdate_time());
				  group.setPerson_team_amount(groupInfo.getPerson_team_amount());
				  group.setGroup_settlement_type(groupInfo.getGroup_settlement_type());
				  group.setExam_indicator(groupInfo.getExam_indicator());
				  group.setCust_type_id(groupInfo.getCust_type_id());
				  group.setCust_type_id(groupInfo.getCust_type_id());
				  this.crmBatchCheckService.addGroupInfo(group);
				  //group_set
				  List<GroupSet> listSet=this.crmBatchCheckService.getGroupSetByGroupId(String.valueOf(groupInfo.getId()));
				  if(listSet!=null&&listSet.size()>0){
					  for(GroupSet groupSet:listSet){
						  GroupSet set=new GroupSet();
						  set.setGroup_id(group.getId());
						  set.setExam_set_id(groupSet.getExam_set_id());
						  set.setDiscount(groupSet.getDiscount());
						  set.setAmount(groupSet.getAmount());
						  set.setIsActive(groupSet.getIsActive());
						  set.setFinal_exam_date(groupSet.getFinal_exam_date());
						  set.setCreater(user.getUserid());
						  this.crmBatchCheckService.addGroupSet(set);
					  }
				  }
				  
				  //Group_Charging_Item
				  List<GroupChargingItem> listItem=this.crmBatchCheckService.getGroupChargingItemByBatchId(String.valueOf(groupInfo.getId()));
				  if(listItem!=null&&listItem.size()>0){
					  for(GroupChargingItem groupChargingItem:listItem){
						  GroupChargingItem set=new GroupChargingItem();
						 set.setCharge_item_id(groupChargingItem.getCharge_item_id());
						 set.setGroup_id(group.getId());
						 set.setItem_amount(groupChargingItem.getItem_amount());
						 set.setDiscount(groupChargingItem.getDiscount());
						 set.setAmount(groupChargingItem.getAmount());
						 set.setIsActive(groupChargingItem.getIsActive());
						 set.setFinal_exam_date(groupChargingItem.getFinal_exam_date());
						 set.setCreater(user.getUserid());
						 set.setCreate_time(DateTimeUtil.getDateTime());
						 set.setUpdater(groupChargingItem.getUpdater());
						 set.setItemnum(groupChargingItem.getItemnum());
						 this.crmBatchCheckService.addGroupChargingItem(set);
					  }
				  }
			  }
		  }
		  
		  List<BatchProPlan> list2=this.crmBatchCheckService.getBatchProPlanByBatchId(String.valueOf(oldBatch.getId()));
		  if(list2!=null&&list2.size()>0){
			  for(BatchProPlan batchProPlan:list2){
				  BatchProPlan b=new BatchProPlan();
				  b.setBatch_id(newBatch.getId());
				  b.setPlandate(DateTimeUtil.shortFmt3(new Date()));
				  b.setPer_num(batchProPlan.getPer_num());
				  b.setCreater(user.getUserid());
				  b.setCreater_date(DateTimeUtil.getDateTime());
				  b.setRemark(batchProPlan.getRemark());
				  this.crmBatchCheckService.addBatchProPlan(b);
			  }
		  }
		  this.message = "复制成功";
		  this.outJsonStrResult(this.message);
		  	SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm175");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		  return NONE;
	  }
	  /**
	   * 
	       * @Title: tijiaoBatch   
	       * @Description: 提交审核   
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String tijiaoBatch()throws WebException,SQLException{
		  UserDTO user = (UserDTO) session.get("username");
		  List<Batch> batchList=this.crmBatchCheckService.getBatchByShenhe(model.getSign_num(),model.getApptype(),user.getCenter_num());
		  if(batchList!=null&&batchList.size()>0){
			 Batch batch=new Batch();
			 batch=batchList.get(0);
			 this.outJsonStrResult(this.message="提交失败，该签单计划下同体检类型的体检任务"+batch.getBatch_name()+"已提交审核");
		  }else{
			  String yi=this.crmBatchCheckService.tijiaoShenhe(String.valueOf(model.getId()));
				if(yi=="1"){
					this.outJsonStrResult(this.message="提交成功");
					Batch bc=this.crmBatchCheckService.getBatchById(String.valueOf(model.getId()));
					BatchPlanLog log=new BatchPlanLog();
					log.setCreater(String.valueOf(user.getUserid()));
					log.setCreater_time(new Date());
					log.setProject_id(String.valueOf(bc.getId()));
					log.setProject_name(bc.getBatch_name());
					log.setProject_status("体检任务提交审核");
					log.setProject_type("9");
					log.setType("2");
					this.crmSignBillPlanService.saveBatchPlanLog(log);
				}else{
					this.outJsonStrResult(this.message="提交失败");
				}
		  }
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm106");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
	  }
	  /**
	   * 
	       * @Title: quxiaoBatch   
	       * @Description: 取消审核
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String quxiaoBatch()throws WebException,SQLException{
			UserDTO user = (UserDTO) session.get("username");
		  List<CrmBatchCheck> list=this.crmBatchCheckService.getCrmBatchCheck(String.valueOf(this.model.getId()));
		  int i=0;
		  if(list!=null&&list.size()>0){
			  for(CrmBatchCheck crm:list){
				  if(crm.getCheck_status().equals("2")){
					 i=i+1;
				  }
			  }
		  }
		  List<Contract> con=this.crmBatchCheckService.getContractByBatchId(String.valueOf(this.model.getId()));
		  int type=0;
		  if(con!=null&&con.size()>0){
			  type=con.get(0).getTypes();
		  }
		  if(i==list.size()){
			  if(type==0){
				  this.outJsonStrResult(this.message="取消提交审核失败，部门已经全部审核完成通过，合同尚未审核");
			  }else if(type==1){
				  String yi=this.crmBatchCheckService.quxiaoShenhe(String.valueOf(model.getId()));
					if(yi=="1"){
						this.outJsonStrResult(this.message="取消成功");	
						Batch bc=this.crmBatchCheckService.getBatchById(String.valueOf(model.getId()));
						List<CrmBatchCheck> checkList= this.crmBatchCheckService.getCrmBatchCheck(String.valueOf(model.getId()));
						if(checkList!=null&&checkList.size()>0){
							for(CrmBatchCheck check:checkList){
								check.setCheck_status("0");
								this.crmBatchCheckService.updateBatchCheck(check);
							}
						}
						BatchPlanLog log=new BatchPlanLog();
						log.setCreater(String.valueOf(user.getUserid()));
						log.setCreater_time(new Date());
						log.setProject_id(String.valueOf(bc.getId()));
						log.setProject_name(bc.getBatch_name());
						log.setProject_status("体检任务取消审核");
						log.setProject_type("10");
						log.setType("2");
						this.crmSignBillPlanService.saveBatchPlanLog(log);
					}else{
						this.outJsonStrResult(this.message="取消失败");
					}
			  }else{
				  this.outJsonStrResult(this.message="取消提交审核失败");
			  }
		  }else{
			  String yi=this.crmBatchCheckService.quxiaoShenhe(String.valueOf(model.getId()));
				if(yi=="1"){
					this.outJsonStrResult(this.message="取消成功");	
					Batch bc=this.crmBatchCheckService.getBatchById(String.valueOf(model.getId()));
					List<CrmBatchCheck> checkList= this.crmBatchCheckService.getCrmBatchCheck(String.valueOf(model.getId()));
					if(checkList!=null&&checkList.size()>0){
						for(CrmBatchCheck check:checkList){
							check.setCheck_status("0");
							this.crmBatchCheckService.updateBatchCheck(check);
						}
					}
					BatchPlanLog log=new BatchPlanLog();
					log.setCreater(String.valueOf(user.getUserid()));
					log.setCreater_time(new Date());
					log.setProject_id(String.valueOf(bc.getId()));
					log.setProject_name(bc.getBatch_name());
					log.setProject_status("体检任务取消审核");
					log.setProject_type("10");
					log.setType("2");
					this.crmSignBillPlanService.saveBatchPlanLog(log);
				}else{
					this.outJsonStrResult(this.message="取消失败");
				}
		  }
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm106");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
	  }

	  /**
	   * 
	       * @Title: checkBatchStatus   
	       * @Description: 判断体检任务是否已经提交审核   
	       * @param: @return
	       * @param: @throws WebException
	       * @param: @throws SQLException      
	       * @return: String      
	       * @throws
	   */
	  public String checkBatchStatus()throws WebException,SQLException{
		  Batch batch=this.crmBatchCheckService.getBatchById(String.valueOf(this.model.getBatch_id()));
		  if(batch!=null){
			  if(batch.getIs_Active().equals("T")){
				  this.outJsonStrResult(this.message="已提交");
			  }else{
				  this.outJsonStrResult(this.message="未提交");
			  }
		  }else{
			  this.outJsonStrResult(this.message="无该体检任务");
		  }
		  return NONE;
	  }
	  public String getBatchBySignNum()throws WebException,SQLException{
		  UserDTO user = (UserDTO) session.get("username");
		  List<BatchDTO> list= this.crmBatchCheckService.getBatchCrm(model.getSign_num(),user.getCenter_num());
		  if(list!=null&&list.size()>0){
			  this.outJsonResult(list);
		  }
		  return NONE;
	  }
}
