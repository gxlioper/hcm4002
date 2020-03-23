package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.model.CrmSignBillPlanModel;
import com.hjw.crm.service.CrmSignBillPlanService;
import com.hjw.crm.service.CrmSignBillPlanZhuangdanService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.Examinatioin_center;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.Examinatioin_centerService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmSignBillPlanZhuangDanAction extends BaseAction implements ModelDriven{
	private CrmSignBillPlanModel model = new CrmSignBillPlanModel();
	private static final long serialVersionUID = 1L;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    private CrmSignBillPlanZhuangdanService crmSignBillPlanZhuangdanService;
    private Examinatioin_centerService examinatioin_centerService;
    private CrmSignBillPlanService crmSignBillPlanService;
    
	public CrmSignBillPlanService getCrmSignBillPlanService() {
		return crmSignBillPlanService;
	}

	public void setCrmSignBillPlanService(CrmSignBillPlanService crmSignBillPlanService) {
		this.crmSignBillPlanService = crmSignBillPlanService;
	}

	public Examinatioin_centerService getExaminatioin_centerService() {
		return examinatioin_centerService;
	}

	public void setExaminatioin_centerService(Examinatioin_centerService examinatioin_centerService) {
		this.examinatioin_centerService = examinatioin_centerService;
	}

	public CrmSignBillPlanZhuangdanService getCrmSignBillPlanZhuangdanService() {
		return crmSignBillPlanZhuangdanService;
	}

	public void setCrmSignBillPlanZhuangdanService(CrmSignBillPlanZhuangdanService crmSignBillPlanZhuangdanService) {
		this.crmSignBillPlanZhuangdanService = crmSignBillPlanZhuangdanService;
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

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	
	public void setModel(CrmSignBillPlanModel model) {
		this.model = model;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	     * @Title: crm139获取签单计划修改撞单的页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignBillPlanZhuangdanPage()throws  WebException,SQLException, UnsupportedEncodingException{
		UserDTO user = (UserDTO) session.get("username");
		List<Examinatioin_center> parlist=this.examinatioin_centerService.getExaminatioin_centerListByParentId("0");
		List<Exam_user> list=this.examinatioin_centerService.getExam_userById(String.valueOf(user.getUserid()));
		int index=0;
		if(list!=null&&list.size()>0){
			for(Exam_user exam:list){
				List<Examinatioin_center> examList=this.examinatioin_centerService.getExaminatioin_centerList(exam.getExam_center_id());
				if(examList!=null&&examList.size()>0){
					if(examList.get(0).getParent_id()==parlist.get(0).getId()||examList.get(0).getParent_id()==0){
						index=index+1;
					}
				}
			}
		}
		if(index>0){
			String status="";
			String track_process="";
			List<CrmSignBillPlan> listcr= this.crmSignBillPlanService.getCrmSignBill(model.getIds());
			
			if(listcr!=null&&listcr.size()>0){
				//status=listcr.get(0).getSign_status();
				track_process=listcr.get(0).getTrack_progress();
			}
			if(track_process.equals("1")||track_process.equals("2")||track_process.equals("7")){
				model.setId(model.getIds());
				String sign_names=new String(model.getSign_names().getBytes("ISO-8859-1"),"UTF-8");
				model.setSign_name(sign_names);	
				model.setCompany_id(model.getCompany_id());
			}else{
				this.message="由于该签单计划的跟踪状态，不允许排除撞单";
				return "commerror";
			}
		}else{
			this.message="您没有权限排除撞单，需要上级部门操作";
			return "commerror";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm139");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * @throws UnsupportedEncodingException 
	 * @throws ServiceException 
	 * 
	     * @Title: crm152获取签单计划撞单列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSignBillPlanZhuangDanList() throws WebException, SQLException, ServiceException, UnsupportedEncodingException {
		PageReturnDTO pageReturn = this.crmSignBillPlanZhuangdanService.getSignBillPlanZhuangDanList(model,page, rows);
		this.outJsonResult(pageReturn);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm152");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm155获取全部签单计划撞单列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getAllSignBillPlanZhuangDanList() throws WebException, SQLException {
		PageReturnDTO pageReturn = this.crmSignBillPlanZhuangdanService.getAllSignBillPlanZhuangDanList(model,page, rows);
		this.outJsonResult(pageReturn);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm155");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: crm153更新签单计划撞单状态   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateSignBillPlanZhuangDan()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<CrmSignBillPlan> list=this.crmSignBillPlanService.getCrmSignBill(model.getIds());
		if(list.get(0).getSign_status()!=null&&list.get(0).getSign_status().equals("2")&&this.model.getUpdatestatus().equals("2")){
			this.outJsonStrResult("该签单计划之前已排撞单，本次设置已撞单无效");
		}else if(list.get(0).getSign_status()!=null&&list.get(0).getSign_status().equals("3")&&this.model.getUpdatestatus().equals("3")){
			this.outJsonStrResult("该签单计划之前已设置为未撞单，本次设置未撞单无效");
		}else{
			String yi=this.crmSignBillPlanZhuangdanService.updateSignBillPlanZhuangDan(model.getIds(),model.getUpdatestatus());
			String zi="";
			String xi="";
			if(yi=="1"){
				BatchPlanLog batchPlanLog=new BatchPlanLog();
				batchPlanLog.setCreater(String.valueOf(user.getUserid()));
				batchPlanLog.setCreater_time(new Date());
				batchPlanLog.setProject_id(list.get(0).getSign_num());
				batchPlanLog.setProject_name(list.get(0).getSign_name());
				batchPlanLog.setProject_type("2");
				batchPlanLog.setType("1");
				if(model.getUpdatestatus().equals("3")){
					//batchPlanLog.setProject_reson("签单计划未撞单原因为："+model.getProject_reson());
					batchPlanLog.setProject_status("签单计划判断为未撞单");
					this.crmSignBillPlanService.saveBatchPlanLog(batchPlanLog);
					zi=this.crmSignBillPlanZhuangdanService.updateSignBillPlanZhuangDanProcess(model.getIds(), "2");
					BatchPlanLog batchPlanLog1=new BatchPlanLog();
					batchPlanLog1.setCreater(String.valueOf(user.getUserid()));
					batchPlanLog1.setCreater_time(new Date());
					batchPlanLog1.setProject_id(list.get(0).getSign_num());
					batchPlanLog1.setProject_name(list.get(0).getSign_name());
					batchPlanLog1.setProject_type("3");
					batchPlanLog1.setProject_status("签单计划开始跟踪");
					batchPlanLog1.setType("1");
					this.crmSignBillPlanService.saveBatchPlanLog(batchPlanLog1);
					xi="判断为未撞单，状态更新成功，";
				}else if(model.getUpdatestatus().equals("2")){
					batchPlanLog.setProject_reson("签单计划撞单原因为："+model.getProject_reson());
					batchPlanLog.setProject_status("签单计划判断为已撞单");
					this.crmSignBillPlanService.saveBatchPlanLog(batchPlanLog);
					zi=this.crmSignBillPlanZhuangdanService.updateSignBillPlanZhuangDanProcess(model.getIds(), "7");
					BatchPlanLog batchPlanLog1=new BatchPlanLog();
					batchPlanLog1.setCreater(String.valueOf(user.getUserid()));
					batchPlanLog1.setCreater_time(new Date());
					batchPlanLog1.setProject_id(list.get(0).getSign_num());
					batchPlanLog1.setProject_name(list.get(0).getSign_name());
					batchPlanLog1.setProject_type("4");
					batchPlanLog1.setProject_status("签单计划已排除撞单");
					batchPlanLog1.setType("1");
					this.crmSignBillPlanService.saveBatchPlanLog(batchPlanLog1);
					xi="判断为已撞单，状态更新成功，";
				}
				this.outJsonStrResult(this.message=xi+zi);	
			}else{
				this.outJsonStrResult(this.message="状态更新失败");
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm153");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}	
	/**
	 * 
	     * @Title: crm156更新健康计划跟踪状态   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateSignBillPlanZhuangDanProcess()throws WebException,SQLException{
		String zi=this.crmSignBillPlanZhuangdanService.updateSignBillPlanZhuangDanProcess(model.getIds(), model.getTrack_progress());
		this.outJsonStrResult(this.message=zi);	
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm156");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}	
	
	public String getCompanyInfoFromZhuangdan()throws WebException,SQLException{
		List<CompanyInfo> list=this.crmSignBillPlanZhuangdanService.getCompanyInfoFromZhuangdan(String.valueOf(model.getCompany_id()));
		if(list!=null&&list.size()>0){
			this.outJsonResult(list);
		}else{
			this.outJsonResult(null);
		}
		return NONE;
	}
	
	public String getCrmSignBillPlanFromZhuangdan()throws WebException,SQLException{
		List<CrmSignBillPlanDTO> list=this.crmSignBillPlanZhuangdanService.getCrmSignBillPlanFromZhuangdan(model.getIds());
		if(list!=null&&list.size()>0){
			this.outJsonResult(list);
		}else{
			this.outJsonResult(null);
		}
		return NONE;
	}
	
	public String updateAbortDate()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String crm=this.crmSignBillPlanZhuangdanService.updateAbortDate(model.getIds(), model.getAbort_date());
		List<CrmSignBillPlan> list=this.crmSignBillPlanService.getCrmSignBill(model.getIds());
		BatchPlanLog batchPlanLog=new BatchPlanLog();
		batchPlanLog.setCreater(String.valueOf(user.getUserid()));
		batchPlanLog.setCreater_time(new Date());
		batchPlanLog.setProject_id(list.get(0).getSign_num());
		batchPlanLog.setProject_name(list.get(0).getSign_name());
		batchPlanLog.setProject_status("签单计划修改了保护截止日期");
		batchPlanLog.setProject_type("1");
		batchPlanLog.setType("1");
		this.crmSignBillPlanService.saveBatchPlanLog(batchPlanLog);
		this.outJsonStrResult(this.message=crm);	
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm156");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}

}
