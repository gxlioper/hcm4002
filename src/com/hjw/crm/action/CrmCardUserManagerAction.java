package com.hjw.crm.action;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.web.action.BaseAction;

public class CrmCardUserManagerAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private SyslogService syslogService;
	
	public SyslogService getSyslogService() {
		return syslogService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	@Override
	public Object getModel() {
		return null;
	}
	/**
	 * 
	     * @Title: 会员管理标签页crm131   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCardUserManagerPage(){
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm131");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm136获取会员名单导入页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCrmMingDanDaoRuPage(){
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm136");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

}
