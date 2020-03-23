package com.hjw.crm.action;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.domain.CrmBatchCheck;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.service.CrmBatchCheckService;
import com.hjw.crm.service.CrmSignBillPlanService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.BatchModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmBatchChechAction extends BaseAction implements ModelDriven{

	private BatchModel model = new BatchModel();
	private CrmBatchCheckService crmBatchCheckService;
	private CompanyService companyService;
	private BatchService batchService;
	private CustomerInfoService customerInfoService;
	private SyslogService syslogService;
	private CrmSignBillPlanService crmSignBillPlanService;

	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	@Override
	public Object getModel() {
		return model;
	}
	public void setModel(BatchModel model) {
		this.model = model;
	}
	public void setCrmBatchCheckService(CrmBatchCheckService crmBatchCheckService) {
		this.crmBatchCheckService = crmBatchCheckService;
	}
	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
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
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * crm210 体检任务财务审核管理页面
	     * @Title: crmBatchCheckManagerPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String crmBatchCheckManagerPageCw() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setCheck_type("2");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm210");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * crm213体检任务医生审核管理页面
	     * @Title: crmBatchCheckManagerPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String crmBatchCheckManagerPageYs() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setCheck_type("1");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm213");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * crm214 体检任务上级部门审核管理页面
	     * @Title: crmBatchCheckManagerPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String crmBatchCheckManagerPageSj() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setCheck_type("3");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm214");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * crm215 查询需要审核的体检任务列表
	     * @Title: crmBatchCheckManageList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String crmBatchCheckManageList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.crmBatchCheckService.getcrmBatchCheckManageList(model, this.rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * crm211 crm体检任务审核页面
	     * @Title: crmBatcheckeditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String crmBatcheckeditPage() throws WebException{
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
				model.setBatch_name(batch.getBatch_name());
				model.setBatch_num(batch.getBatch_num());
				model.setCompany_id(batch.getCompany_id());
				model.setContact_name(batch.getContact_name());
			}
		}
		CrmBatchCheck check = this.crmBatchCheckService.getcrmBatchCheck(model.getId(),model.getCheck_type());
		if(check != null){
			model.setCheck_status(check.getCheck_status());
			if(!"0".equals(check.getCheck_status())){
				model.setCheckdate(DateTimeUtil.shortFmt3(check.getCheckdate()));
			}
			model.setChecknotice(check.getChecknotice());
		}
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm211");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * crm212 保存批次任务审核信息
	     * @Title: saveCrmBatchCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmBatchCheck() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ContractDTO cd1 = this.batchService.getContractForBrachId(this.model.getBatch_id());
		if(cd1 == null || cd1.getBatch_id() == 0){
			CompanyInfo cif = companyService.findComByID(this.model.getCompany_id());
			//修改审核状态
			CrmBatchCheck check = this.crmBatchCheckService.getcrmBatchCheck(model.getBatch_id(),model.getCheck_type());
			if(check != null){
				check.setCheck_status(model.getCheck_status());
				check.setChecknotice(model.getChecknotice());
				check.setCheckuser(user.getUserid());
				check.setCheckdate(DateTimeUtil.parse());
				this.crmBatchCheckService.updateBatchCheck(check);
				Batch bc=this.crmBatchCheckService.getBatchById(String.valueOf(model.getBatch_id()));
				BatchPlanLog log =new BatchPlanLog();
				log.setCreater(String.valueOf(user.getUserid()));
				log.setCreater_time(new Date());
				log.setProject_id(String.valueOf(bc.getId()));
				log.setProject_name(bc.getBatch_name());
				log.setType("2");
				if(model.getCheck_type().equals("1")){
					if(model.getCheck_status().equals("1")){
						log.setProject_status("医生审核不通过");
						log.setProject_type("11");
						log.setProject_reson(model.getChecknotice());
					}else{
						log.setProject_status("医生审核通过");
						log.setProject_type("12");
						log.setProject_reson(model.getChecknotice());
					}
				}else if(model.getCheck_type().equals("2")){
					if(model.getCheck_status().equals("1")){
						log.setProject_status("财务审核不通过");
						log.setProject_type("13");
						log.setProject_reson(model.getChecknotice());
					}else{
						log.setProject_status("财务审核通过");
						log.setProject_type("14");
						log.setProject_reson(model.getChecknotice());
					}
				}else if(model.getCheck_type().equals("3")){
					if(model.getCheck_status().equals("1")){
						log.setProject_status("上级部门审核不通过");
						log.setProject_type("15");
						log.setProject_reson(model.getChecknotice());
					}else{
						log.setProject_status("上级部门审核通过");
						log.setProject_type("16");
						log.setProject_reson(model.getChecknotice());
					}
				}
				this.crmSignBillPlanService.saveBatchPlanLog(log);
				
			}
			//判断全部审核信息审核完成 ，就可以修改审核状态，生成合同
			List<CrmBatchCheck> list = this.crmBatchCheckService.getCrmBatchCheck(model.getBatch_id()+"");
			int count = 0;
			for(CrmBatchCheck cc : list){
				if("2".equals(cc.getCheck_status())){
					count ++ ;
				}
			}
			if(count == list.size()){//全部审核完成，生成合同
				Batch batch = this.batchService.getBatchByID(this.model.getBatch_id());
			
				batch.setCheckdate(Timeutils.getNowDate());
				batch.setChecknotice(model.getChecknotice());
				batch.setChecktype(2);
				batch.setCheckuser(user.getUserid());
				this.batchService.updateBatch(batch);
				
				ContractDTO cd = this.batchService.getContractForBrachId(this.model.getBatch_id());
				if(cd == null || cd.getBatch_id() == 0){
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
					
					String checktypes = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK", user.getCenter_num()).getConfig_value().trim();
					if ("0".equals(checktypes)) {
						ct.setTypes(0);
						
						CrmSignBillPlan signPlan = this.crmSignBillPlanService.getSignBillPlanByNum(batch.getSign_num());
						signPlan.setTrack_progress("4");
						signPlan.setTrack_time(DateTimeUtil.parse());
						BatchPlanLog log =new BatchPlanLog();
						log.setCreater(String.valueOf(user.getUserid()));
						log.setCreater_time(new Date());
						log.setProject_id(signPlan.getSign_num());
						log.setProject_name(signPlan.getSign_name());
						log.setType("1");
						log.setProject_status("签单计划生成合同");
						log.setProject_type("17");
						this.crmSignBillPlanService.saveBatchPlanLog(log);
						this.crmSignBillPlanService.updateSignBillPlan(signPlan);
					} else if ("1".equals(checktypes)) {
						ct.setTypes(2);
						ct.setChecknotice("系统自动发起合同审核");
						
						batch.setIs_Active("Y");
						this.batchService.updateBatch(batch);//合同审核完成，批次任务启用
						BatchPlanLog log =new BatchPlanLog();
						log.setCreater(String.valueOf(user.getUserid()));
						log.setCreater_time(new Date());
						log.setProject_id(String.valueOf(batch.getId()));
						log.setProject_name(batch.getBatch_name());
						log.setType("2");
						log.setProject_status("体检任务已启用");
						log.setProject_type("19");
						this.crmSignBillPlanService.saveBatchPlanLog(log);
						CrmSignBillPlan signPlan = this.crmSignBillPlanService.getSignBillPlanByNum(batch.getSign_num());
						signPlan.setTrack_progress("8");
						signPlan.setTrack_time(DateTimeUtil.parse());
						BatchPlanLog log1 =new BatchPlanLog();
						log1.setCreater(String.valueOf(user.getUserid()));
						log1.setCreater_time(new Date());
						log1.setProject_id(signPlan.getSign_num());
						log1.setProject_name(signPlan.getSign_name());
						log1.setType("1");
						log1.setProject_reson(model.getChecknotice());
						log1.setProject_status("签单计划合同审核通过");
						log1.setProject_type("18");
						this.crmSignBillPlanService.saveBatchPlanLog(log1);
						this.crmSignBillPlanService.updateSignBillPlan(signPlan);
					}
					this.batchService.saveContract(ct);
				}
			}
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm212");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
			this.message = "ok-审核成功!";
			this.outJsonStrResult(this.message);
		}else{
			this.message="合同已生成,无法进行再审核";
			this.outJsonStrResult(this.message);
		}
		return NONE;
	}
	
	/**
	 * crm216 合同信息审核保存
	     * @Title: crmContractCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String crmContractCheck() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getContract_num()==null)||(this.model.getContract_num().trim().length()<=0)) {
			this.message = "error-合同无效！";
		} else {
			ContractDTO cd = new ContractDTO();
			cd = this.batchService.getContractForContNum(this.model.getContract_num().trim());
			if (cd==null){
				this.message = "error-无效的合同编号！";
			}else if(cd.getTypes() == 2){
				this.message = "error-合同已审核,不能再次审核！";
			}else {
				Batch batch = this.batchService.getBatchByID(cd.getBatch_id());
				Contract ct = new Contract();
				ct = this.batchService.loadContract(cd.getId());
				ct.setCheckdate(Timeutils.getNowDate());
				ct.setCheckuser(user.getUserid());
				ct.setChecknotice(model.getChecknotice());
				ct.setTypes(this.model.getChecktype());			
				this.batchService.updateContract(ct);
				CrmSignBillPlan signPlan = this.crmSignBillPlanService.getSignBillPlanByNum(batch.getSign_num());
				if(2 == model.getChecktype()){ //合同审核通过
					batch.setIs_Active("Y");//合同审核完成，启用批次任务
					this.batchService.updateBatch(batch);
					BatchPlanLog log =new BatchPlanLog();
					log.setCreater(String.valueOf(user.getUserid()));
					log.setCreater_time(new Date());
					log.setProject_id(String.valueOf(batch.getId()));
					log.setProject_name(batch.getBatch_name());
					log.setType("2");
					log.setProject_status("体检任务已启用");
					log.setProject_type("19");
					this.crmSignBillPlanService.saveBatchPlanLog(log);
					BatchPlanLog log2 =new BatchPlanLog();
					log2.setCreater(String.valueOf(user.getUserid()));
					log2.setCreater_time(new Date());
					log2.setProject_id(String.valueOf(batch.getId()));
					log2.setProject_name(batch.getBatch_name());
					log2.setType("2");
					log2.setProject_status("体检任务所属合同已经审核");
					log2.setProject_reson(model.getChecknotice());
					log2.setProject_type("19");
					this.crmSignBillPlanService.saveBatchPlanLog(log2);
					
					signPlan.setTrack_progress("8");
					signPlan.setTrack_time(DateTimeUtil.parse());
					BatchPlanLog log1 =new BatchPlanLog();
					log1.setCreater(String.valueOf(user.getUserid()));
					log1.setCreater_time(new Date());
					log1.setProject_id(signPlan.getSign_num());
					log1.setProject_name(signPlan.getSign_name());
					log1.setType("1");
					log1.setProject_status("签单计划合同审核通过");
					log1.setProject_reson(model.getChecknotice());
					log1.setProject_type("18");
					this.crmSignBillPlanService.saveBatchPlanLog(log1);
					this.crmSignBillPlanService.updateSignBillPlan(signPlan);
				}else{
					BatchPlanLog log4 =new BatchPlanLog();
					log4.setCreater(String.valueOf(user.getUserid()));
					log4.setCreater_time(new Date());
					log4.setProject_id(String.valueOf(batch.getId()));
					log4.setProject_name(batch.getBatch_name());
					log4.setType("2");
					log4.setProject_status("体检任务所属合同已经审核");
					log4.setProject_reson(model.getChecknotice());
					log4.setProject_type("19");
					this.crmSignBillPlanService.saveBatchPlanLog(log4);
					BatchPlanLog log3 =new BatchPlanLog();
					log3.setCreater(String.valueOf(user.getUserid()));
					log3.setCreater_time(new Date());
					log3.setProject_id(signPlan.getSign_num());
					log3.setProject_name(signPlan.getSign_name());
					log3.setType("1");
					log3.setProject_status("签单计划合同审核未通过");
					log3.setProject_reson(model.getChecknotice());
					log3.setProject_type("20");
					this.crmSignBillPlanService.saveBatchPlanLog(log3);
				}
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("crm216");//子功能id 必须填写
				sl.setExplain("审核合同 "+ct.getContract_num()+" "+ct.getCompany_name());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-合同审核完成！";
			}
		}
		this.outJsonStrResult(message);
		return NONE;
	}
}
