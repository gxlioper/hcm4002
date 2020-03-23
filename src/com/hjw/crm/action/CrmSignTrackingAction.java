package com.hjw.crm.action;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.hjw.crm.domain.CrmSignTracking;
import com.hjw.crm.model.CrmSignTrackingModel;
import com.hjw.crm.service.CrmSignTrackingService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmSignTrackingAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 1L;
	private CrmSignTrackingModel model=new CrmSignTrackingModel();
	private CrmSignTrackingService crmSignTrackingService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    
	
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

	public CrmSignTrackingService getCrmSignTrackingService() {
		return crmSignTrackingService;
	}

	public void setCrmSignTrackingService(CrmSignTrackingService crmSignTrackingService) {
		this.crmSignTrackingService = crmSignTrackingService;
	}

	public void setModel(CrmSignTrackingModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	/**
	 * 
	     * @Title: crm130获取签单跟踪列表页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignTrackingListPage()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm130");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: 获取签单跟踪列表crm141   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignTrackingList()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.crmSignTrackingService.getCrmSignTrackingList(model,user.getUserid(), page, rows);
		this.outJsonResult(dto);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm141");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm144获取签单跟踪列表的时间   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSignTrackingTime()throws  WebException,SQLException{
		List<String> list=this.crmSignTrackingService.getCrmSignTrackingTime();
		StringBuffer sb=new StringBuffer();
		boolean flag=false;
		for(String item:list){
			if (flag) {
				sb.append(",");
            }else {
                flag=true;
            }
			if(item.contains("-0")){
				item=item.replace("-0", "-");
			}
			sb.append(item);
		}
		String str=sb.toString();
		this.outJsonStrResult( str );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm144");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: crm134新增签单跟踪页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCrmSignTrackingPage()throws  WebException{
		this.model.setTracking_date(DateTimeUtil.getDateTime());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm134");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: crm142保存签单跟踪   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws ParseException      
	     * @return: String      
	     * @throws
	 */
	public String saveCrmSignTracking() throws  WebException,SQLException, ParseException{
		UserDTO user = (UserDTO) session.get("username");
		CrmSignTracking c = new CrmSignTracking();
		c.setSign_num(model.getSign_num());
		c.setTracking_content(model.getTracking_content());
		c.setPhone(model.getPhone());
		c.setContact_name(model.getContact_name());
		c.setRemark(model.getRemark());
		c.setTracking_date(DateTimeUtil.parse(model.getTracking_date()));
		this.crmSignTrackingService.addCrmSignTracking(c);
		this.message = "添加成功";
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm134");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult( this.message );
		return NONE;
	}
}
