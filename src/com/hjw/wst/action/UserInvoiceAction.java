package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.UserInvoiceDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.model.UserInvoiceModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInvoiceService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  用户发票号段维护
     * @author: dangqi     
     * @date:   2017年10月31日 下午12:04:35   
     * @version V2.0.0.0
 */
public class UserInvoiceAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 8217814910220881058L;
	
	private UserInvoiceModel model = new UserInvoiceModel();
	private UserInvoiceService userInvoiceService;
	private CustomerInfoService customerInfoService;
	private SyslogService syslogService;
	
	private int rows = 15; // easyui每页显示条数
	
	public UserInvoiceModel getModel() {
		return model;
	}
	public void setModel(UserInvoiceModel model) {
		this.model = model;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setUserInvoiceService(UserInvoiceService userInvoiceService) {
		this.userInvoiceService = userInvoiceService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	/**
	 * 1103 发票号段维护页面
	     * @Title: getUserInvoiceManagePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getUserInvoiceManagePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setUser_invoice_num_length(this.customerInfoService.getCenterconfigByKey("USER_INVOICE_NUM_LENGTH", user.getCenter_num()).getConfig_value().trim());
		this.model.setIs_use_all(this.userInvoiceService.getInvoiceIsUseAll());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1103");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1104 获取发票号段列表
	     * @Title: getUserInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getUserInvoiceList() throws WebException,SQLException{
		List<UserInvoiceDTO> list = this.userInvoiceService.getUserInvoiceList(this.model.getIs_use_all());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1105 获取发票号段编辑页面
	     * @Title: getUserInvoiceEditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getUserInvoiceEditPage() throws WebException{
		this.model.setInvoice_class(this.userInvoiceService.getInvoiceClass());
		return SUCCESS;
	}
	
	/**
	 * 1106 保存发票号段信息
	     * @Title: saveUserInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserInvoiceInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoice userInvoice = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getUser_id(), this.model.getInvoice_class());
		if(userInvoice == null){
			userInvoice = new UserInvoice();
			
			userInvoice.setUser_id(this.model.getUser_id());
			userInvoice.setInvoice_num_min(this.model.getInvoice_num_min());
			userInvoice.setInvoice_num_max(this.model.getInvoice_num_max());
			userInvoice.setCreater(user.getUserid());
			userInvoice.setCreate_time(DateTimeUtil.parse());
			userInvoice.setUpdater(user.getUserid());
			userInvoice.setUpdate_time(DateTimeUtil.parse());
			userInvoice.setIs_use_all(this.model.getIs_use_all());
			userInvoice.setInvoice_class(this.model.getInvoice_class());
			userInvoice.setIs_active(this.model.getIs_active());
			this.userInvoiceService.saveUserInvoice(userInvoice);
		}else{
			userInvoice.setUser_id(this.model.getUser_id());
			userInvoice.setInvoice_num_min(this.model.getInvoice_num_min());
			userInvoice.setInvoice_num_max(this.model.getInvoice_num_max());
			userInvoice.setInvoice_num_used(null);
			userInvoice.setUpdater(user.getUserid());
			userInvoice.setUpdate_time(DateTimeUtil.parse());
			
			this.userInvoiceService.updateUserInvoice(userInvoice);
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1106");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.message = "保存成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1107 保存发票类型的启用
	     * @Title: saveUserInvoiceClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserInvoiceClass() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.userInvoiceService.saveUserInvoiceClass(this.model.getInvoice_class());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1107");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.message = "发票类型启用成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1108 保存设置发票号段共用或发票号段按人使用
	     * @Title: saveUserInvoiceUseAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserInvoiceUseAll() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.userInvoiceService.saveUserInvoiceUseAll(this.model.getIs_use_all());
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1108");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		if("Y".equals(this.model.getIs_use_all())){
			this.message = "发票号段共用模式启用成功!";
		}else{
			this.message = "发票号段按人使用模式启用成功!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *1109  通过用户id和发票类型查询已维护发票号段信息
	     * @Title: getUserInvoiceByUserIdAndClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getUserInvoiceByUserIdAndClass() throws WebException{
		UserInvoice userInvoice = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getUser_id(), this.model.getInvoice_class());
		UserInvoiceDTO dto = null;
		if(userInvoice != null){
			dto = new UserInvoiceDTO();
			dto.setInvoice_num_min(userInvoice.getInvoice_num_min());
			dto.setInvoice_num_max(userInvoice.getInvoice_num_max());
			dto.setInvoice_num_used(userInvoice.getInvoice_num_used());
		}
		this.outJsonResult(dto);
		return NONE;
	}
	
	/**
	 * 1110 查询收费员列表
	     * @Title: getCashierList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCashierList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<UserInfoDTO> list = this.userInvoiceService.getCashierList(user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 1107 删除发票维护
	     * @Title: saveUserInvoiceClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateInvoice() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.userInvoiceService.deleteInvoice(this.model.getId()+"");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.message = "ok-删除成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
