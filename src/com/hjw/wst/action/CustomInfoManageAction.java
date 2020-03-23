package com.hjw.wst.action;

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StringUtil;

import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CustomerInfoManageModel;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.service.CustomerInfoManageService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.examInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 * @param <Department>
 */
@SuppressWarnings("rawtypes")
public class CustomInfoManageAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private CustomerInfoManageModel model = new CustomerInfoManageModel();
	private CustomerInfoManageService customerInfoManageService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SyslogService syslogService;
	
	private String sort;
	private String order;
	private examInfoService examInfoService;
	
	
	public examInfoService getExamInfoService() {
		return examInfoService;
	}
	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
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
	
	
	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
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
	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}
	public CustomerInfoManageModel getModel() {
		return model;
	}

	public void setModel(CustomerInfoManageModel model) {
		this.model = model;
	}

	public CustomerInfoManageService getCustomerInfoManageService() {
		return customerInfoManageService;
	}

	public void setCustomerInfoManageService(
			CustomerInfoManageService customerInfoManageService) {
		this.customerInfoManageService = customerInfoManageService;
	}

	/**
	 * 档案管理页面
	     * @Title: customerManage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String customerManage() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("607");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String customadd() throws WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 档案信息列表
	     * @Title: customeListshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String customeListshow()  throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = new PageReturnDTO();
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 2))  {
			if (this.model.getChkItem().trim().indexOf("arch_num") < 0) {
				this.model.setArch_num("");
			}
			if (this.model.getChkItem().trim().indexOf("user_name") < 0) {
				this.model.setUser_name("");
			}
			if (this.model.getChkItem().trim().indexOf("id_num") < 0) {
				this.model.setId_num("");
			}
			
			if (this.model.getChkItem().trim().indexOf("birthday") < 0) {
				this.model.setTime1("");
				this.model.setTime2("");
			}	
			
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}
		
		 list = this.customerInfoManageService.queryPageCustome(this.model, rows, this.getPage(),this.sort,this.order,user);
		}
		 this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 更新
	     * @Title: updaterCustome   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String upCustom() throws WebException{
		CustomerInfo ci=this.customerInfoManageService.loadCustomer(this.model.getId());
		this.model.setArch_num(ci.getArch_num());
		this.model.setUser_name(ci.getUser_name());
		this.model.setId_num(ci.getId_num());
		this.model.setBirthday(DateTimeUtil.shortFmt3(ci.getBirthday()));
		this.model.setSex(ci.getSex());
		this.model.setIs_Active(ci.getIs_Active());
		this.model.setNation(ci.getNation());
		return SUCCESS;
}
	
	/**
	 * 添加
	     * @Title: addCustome   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCustome() throws WebException{
		return SUCCESS;
	}
	    
	public String saveCustom() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			CustomerInfo ci=this.customerInfoManageService.loadCustomer(this.model.getId());
//			ci.setArch_num(this.model.getArch_num());
			if(!"".equals(model.getId_num())){
				CustomerInfo cio = this.customerInfoManageService.getCustomerInfoByIdNum(model.getId_num(),user.getCenter_num());
				if(cio != null && model.getId() != cio.getId()){
					this.message = "error-该身份证号已存在!档案号为"+cio.getArch_num()+"。";
				}else{
					ci.setUser_name(this.model.getUser_name());
					ci.setId_num(this.model.getId_num());
					ci.setBirthday(DateTimeUtil.parse2(this.model.getBirthday()));
					ci.setSex(this.model.getSex());
					ci.setIs_Active(this.model.getIs_Active());
					if(!"".equals(model.getNation())){
						ci.setNation(this.model.getNation());
					}else{
						ci.setNation(null);
					}
					ci.setUpdater(user.getUserid());
					ci.setUpdate_time(DateTimeUtil.parse());
					this.customerInfoManageService.updateCustomer(ci);
					this.message="ok-修改成功！";
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("611");//子功能id 必须填写
					sl.setExplain("修改");//操作说明
					syslogService.saveSysLog(sl);
				}
			}else{
				ci.setUser_name(this.model.getUser_name());
				ci.setId_num(this.model.getId_num());
				ci.setBirthday(DateTimeUtil.parse2(this.model.getBirthday()));
				ci.setSex(this.model.getSex());
				ci.setIs_Active(this.model.getIs_Active());
				if(!"".equals(model.getNation())){
					ci.setNation(this.model.getNation());
				}else{
					ci.setNation(null);
				}
				ci.setUpdater(user.getUserid());
				ci.setUpdate_time(DateTimeUtil.parse());
				this.customerInfoManageService.updateCustomer(ci);
				this.message="ok-修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("611");//子功能id 必须填写
				sl.setExplain("修改");//操作说明
				syslogService.saveSysLog(sl);
			}
		}else{
			CustomerInfo ci = new CustomerInfo();
			if(!"".equals(model.getId_num())){
				CustomerInfo cio = this.customerInfoManageService.getCustomerInfoByIdNum(model.getId_num(),user.getCenter_num());
				if(cio != null){
					this.message = "error-该身份证号已存在!档案号为"+cio.getArch_num()+"。";
				}else{
					ci.setArch_num(GetNumContral.getInstance().getParamNum("vipno", user.getCenter_num()));
					ci.setUser_name(this.model.getUser_name());
					ci.setId_num(this.model.getId_num());
					ci.setBirthday(DateTimeUtil.parse2(this.model.getBirthday()));
					ci.setSex(this.model.getSex());
					ci.setIs_Active(this.model.getIs_Active());
					if(!"".equals(model.getNation())){
						ci.setNation(this.model.getNation());
					}else{
						ci.setNation(null);
					}
					ci.setCreater(user.getUserid());
					ci.setCreate_time(DateTimeUtil.parse());
					ci.setUpdater(user.getUserid());
					ci.setUpdate_time(DateTimeUtil.parse());
					ci.setCenter_num(user.getCenter_num());
					this.customerInfoManageService.addCustomer(ci);
					this.message = "ok-添加成功！";
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("611");//子功能id 必须填写
					sl.setExplain("添加");//操作说明
					syslogService.saveSysLog(sl);
				}
			}else{
				ci.setArch_num(GetNumContral.getInstance().getParamNum("vipno", user.getCenter_num()));
				ci.setUser_name(this.model.getUser_name());
				ci.setId_num(this.model.getId_num());
				ci.setBirthday(DateTimeUtil.parse2(this.model.getBirthday()));
				ci.setSex(this.model.getSex());
				ci.setIs_Active(this.model.getIs_Active());
				if(!"".equals(model.getNation())){
					ci.setNation(this.model.getNation());
				}else{
					ci.setNation(null);
				}
				ci.setCreater(user.getUserid());
				ci.setCreate_time(DateTimeUtil.parse());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_time(DateTimeUtil.parse());
				ci.setCenter_num(user.getCenter_num());
				this.customerInfoManageService.addCustomer(ci);
				this.message = "ok-添加成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("611");//子功能id 必须填写
				sl.setExplain("添加");//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 验证编码唯一性
	     * @Title: isUnique   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String isUnique() throws WebException{
		CustomerInfo ci = this.customerInfoManageService.queryByNum(model.getArch_num());
		if(ci==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 绑定人员
	     * @Title: bindPerson   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String bindPerson() throws WebException{
		return SUCCESS;
	}
	/**
	 * 人员绑定档案号
	     * @Title: bindPersonUpdate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String bindPersonUpdate() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String msg = customerInfoManageService.bindPersonUpdate(model, user);
		this.outJsonStrResult(msg);
		return NONE;
	}
	
	
}