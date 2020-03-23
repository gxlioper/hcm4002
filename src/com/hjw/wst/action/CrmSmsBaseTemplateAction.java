package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.MSGSendMessage;
import com.hjw.webService.client.Bean.MSGSendBean;
import com.hjw.webService.client.body.MSGResBody;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CrmSmsBaseTemplate;
import com.hjw.wst.domain.CrmSmsSend;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CrmSmsBaseTemplateModel;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.CrmSmsBaseTemplateService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmSmsBaseTemplateAction extends  BaseAction implements  ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -1825471794441793314L;
	private CrmSmsBaseTemplateModel model = new CrmSmsBaseTemplateModel();
	private int page = 1;
	private int pageSize = 15;
	private int rows = 15;
	private CrmSmsBaseTemplateService crmSmsBaseTemplateService;
	private SyslogService syslogService;    
	private CustomerInfoService customerInfoService;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}


	public void setCrmSmsBaseTemplateService(
			CrmSmsBaseTemplateService crmSmsBaseTemplateService) {
		this.crmSmsBaseTemplateService = crmSmsBaseTemplateService;
	}


	public SyslogService getSyslogService() {
		return syslogService;
	}


	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}


	public int getPage() {
		return page;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public void setModel(CrmSmsBaseTemplateModel model) {
		this.model = model;
	}


	public int getRows() {
		return rows;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 短信模板管理页面1400
	     * @Title: getCrmSmsBaseTemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSmsBaseTemplatePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1400");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 短信模板列表1401
	     * @Title: queryCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryCrmSmsBaseTemplate() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1401");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(this.crmSmsBaseTemplateService.queryCrmSmsBaseTemplate(model, page, pageSize));
		return NONE;
	}
	
	/**
	 * 
	     * @Title: queryCrmSmsBaseTemplateall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryCrmSmsBaseTemplateall() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1401");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(this.crmSmsBaseTemplateService.queryCrmSmsBaseTemplateAll());
		return NONE;
	}
	/**
	 * 删除短信模板1402
	     * @Title: deleteCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCrmSmsBaseTemplate() throws WebException{
		this.crmSmsBaseTemplateService.deleteCrmSmsBaseTemplate(model);
		this.outJsonStrResult("删除成功！");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1402");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 保存短信模板1403
	     * @Title: saveCrmSmsBaseTemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmSmsBaseTemplate() throws WebException{
		String type="";
		UserDTO user = (UserDTO) session.get("username");
		if(model.getId()!=null && !"".equals(model.getId())){
			this.crmSmsBaseTemplateService.updateCrmSmsBaseTemplate(model, user);
			type="2";
			this.message="修改成功！";
		} else {
			this.crmSmsBaseTemplateService.svaeCrmSmsBaseTemplate(model, user);
			type="1";
			this.message="保存成功！";
		}
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type(type);//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1403");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 修改短信模板页面1404
	     * @Title: updateCrmSmsBaseTemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateCrmSmsBaseTemplatePage() throws WebException{
		CrmSmsBaseTemplate crmSmsBaseTemplate= this.crmSmsBaseTemplateService.getCrmSmsBaseTemplate(model.getId());
		model.setM(crmSmsBaseTemplate);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1404");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 新增短信模板页面1405
	     * @Title: addCrmSmsBaseTemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCrmSmsBaseTemplatePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1405");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	 * 
	 * ========================================短信发送记录===============================
	 * 
	 * 
	 * 
	 */
	/**
	 * 
	     * @Title: 短信发送页面1406
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSmsSendPage() throws WebException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	/**
	 * 短信发送记录列表1407
	     * @Title: getCrmSmsSendPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryCrmSmsSendList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(crmSmsBaseTemplateService.queryCrmSmsSend(model, user.getCenter_num(), page, pageSize));
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1407");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 新增要发送的短信1408
	     * @Title: getCrmSmsSendPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCrmSmsSend() throws WebException{
		
		
		this.model.setTime1(DateTimeUtil.getDate2());
        this.model.setTime2(DateTimeUtil.getDate2());
        this.model.setTime3(DateTimeUtil.getDate2());
        this.model.setTime4(DateTimeUtil.getDate2());
        this.model.setMin_age("1");
        this.model.setMax_age("100");
        
		return SUCCESS;
	}
	/**
	 * 获取所有人员1409
	     * @Title: getUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getUser() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO  pageReturnDTO =  new PageReturnDTO();
		//体检用户
		if("1".equals(model.getUser_type())){
			RegisterModel mo= new RegisterModel();
			mo.setDisease_id(this.model.getDisease_id());
			mo.setIsphone(this.model.getSms_phone());
			mo.setBatch_id(this.model.getBatch_id());
			mo.setSms_status(this.model.getSms_status());
			pageReturnDTO = this.customerInfoService.getDjtExamInfoUserListCopyItem("",this.model.getPhone(),this.model.getCustomer_type(),
					this.model.getCompany_id(), this.model.getRen_type(), this.model.getArch_num(),
					this.model.getExam_num(), this.model.getEmployeeID(), this.model.getStatus(),
					this.model.getId_num(), this.model.getCustname(), this.model.getTime1(), this.model.getTime2(),
					this.model.getTime3(), this.model.getTime4(), this.model.getCreate_time(),
					this.model.getExam_type(), this.model.getDjdstatuss(), user.getUserid() + "", centernum,mo,
					this.rows, this.getPage(),"","",user.getCenter_num(),this.model.getTime5(),this.model.getSex_type(),this.model.getIs_print(),this.model.getMin_age(),this.model.getMax_age());

		} else {
		//工作人员
			pageReturnDTO = crmSmsBaseTemplateService.queryUser(model, page, pageSize);
			
		}
		this.outJsonResult(pageReturnDTO);
		return NONE;
	}
	/**
	 * 发送短信1410
	     * @Title: addCrmSmsSend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmSmsSend() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
			String uid = crmSmsBaseTemplateService.saveCrmSmsSend(model, user);
			MSGSendMessage mSGSendMessage = new MSGSendMessage();
			MSGSendBean eu = new  MSGSendBean();
			eu.setCenter_num(user.getCenter_num());
			eu.setBatchCode(uid);
			
			if(model.getSms_type()==1){
				MSGResBody mb = mSGSendMessage.Send(eu);
				
				if("AA".equals(mb.getRescode())){
					this.message = "ok-操作成功！";
				} else {
					this.message = "error-操作失败";
				}
			}else{
				this.message = "ok-延迟发送数据插入成功！";
			}
			
			
		
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: handsmssend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String handsmssend() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getIds()==null&& this.model.getIds().trim().length()<=0){
			this.message = "error-无效记录！";
		}else{
			String[] sids= this.model.getIds().split(",");
			for(int i=0;i<sids.length;i++){
				CrmSmsBaseTemplateModel cbm = new CrmSmsBaseTemplateModel();
				cbm.setId(sids[i]);
				String bath = crmSmsBaseTemplateService.saveCrmSmsSendCHONGFA(cbm, user);
				MSGSendMessage mSGSendMessage = new MSGSendMessage();
				MSGSendBean eu = new  MSGSendBean();
				eu.setCenter_num(user.getCenter_num());
				eu.setBatchCode(bath);
				mSGSendMessage.Send(eu);
			}
			this.message = "ok-操作完成！";
		}				
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: saveCrmSmsSendCHONGFA   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmSmsSendCHONGFA() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
			String bath = crmSmsBaseTemplateService.saveCrmSmsSendCHONGFA(model, user);
			MSGSendMessage mSGSendMessage = new MSGSendMessage();
			MSGSendBean eu = new  MSGSendBean();
			eu.setCenter_num(user.getCenter_num());
			eu.setBatchCode(bath);
			MSGResBody mb = mSGSendMessage.Send(eu);
			if(!"AA".equals(mb.getRescode())){
				this.message = "error-操作失败";
			} else {
				this.message = "ok-操作成功！";
			}
		this.outJsonStrResult(message);
		return NONE;
	}
	  /**
     * 
         * @Title: getCompanForBatch   
         * @Description: 获取全部批次任务   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String getBatch() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	List<BatchDTO> getbatchList = new ArrayList<BatchDTO>();
    	getbatchList = this.customerInfoService.getbatch(user.getCenter_num());
    	this.outJsonResult(getbatchList);
    	return NONE;
    }
}
