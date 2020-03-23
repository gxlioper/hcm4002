package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.service.CrmMemberManageService;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CardMemberModel;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmMemberManagerAction  extends BaseAction implements ModelDriven{
	private SyslogService syslogService;
	private static final long serialVersionUID = 1L;
	private CrmMemberManageService crmMemberManageService;
	private CardMemberModel model = new CardMemberModel();
	
	
	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public CrmMemberManageService getCrmMemberManageService() {
		return crmMemberManageService;
	}

	public void setCrmMemberManageService(CrmMemberManageService crmMemberManageService) {
		this.crmMemberManageService = crmMemberManageService;
	}

	public void setModel(CardMemberModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	/**
	 * 
	     * @Title: crm135获取会员等级设置页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws UnsupportedEncodingException      
	     * @return: String      
	     * @throws
	 */
	public String getHuiYuanDengjiSheZhi()throws WebException, SQLException, UnsupportedEncodingException {
		CustomerMemberInfo c=new CustomerMemberInfo();
		c=this.crmMemberManageService.getUpdatePageMmember(model.getArch_nums());
		String user_names1= new String(model.getUser_names().getBytes("ISO8859-1"),"UTF-8");
		model.setArch_num(model.getArch_nums());
		model.setUser_name(user_names1);
		String levelname=this.crmMemberManageService.getLevelName(String.valueOf(c.getLevel()));
		model.setLevel_name(levelname);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm135");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm143更新会员信息   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateCrmMemberManager()throws WebException, SQLException{
		String str=this.crmMemberManageService.updateCustomerMemberInfo(model.getArch_num(),String.valueOf(model.getLevel()));
		String strs="";
		if(str=="1"){strs = "修改成功";}
		else{strs="修改失败";}
		this.outJsonStrResult( strs );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm143");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm145获取会员等级   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLevel()throws WebException, SQLException{
		List list=this.crmMemberManageService.getLevel();
		this.outJsonResult( list );
		UserDTO user = (UserDTO) session.get("username");
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
	     * @Title: crm146积分清零   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateJiFenLing()throws WebException, SQLException{
		String str=this.crmMemberManageService.updateJiFenLing(model.getArchnumber());
		String strs="";
		if(str=="1"){strs = "清零成功";}
		else{strs="清零失败";}
		this.outJsonStrResult( strs );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm146");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}
