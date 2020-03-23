package com.hjw.crm.action;

import java.sql.SQLException;
import java.util.Date;

import com.hjw.crm.domain.HuoVsHop;
import com.hjw.crm.model.HuoVsHopModel;
import com.hjw.crm.service.HuoVsHopService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class HuoVsHopAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private HuoVsHopModel model=new HuoVsHopModel();
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    private HuoVsHopService huoVsHopService;
    
	public HuoVsHopService getHuoVsHopService() {
		return huoVsHopService;
	}
	public void setHuoVsHopService(HuoVsHopService huoVsHopService) {
		this.huoVsHopService = huoVsHopService;
	}
	@Override
	public Object getModel() {
		return model;
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
	public void setModel(HuoVsHopModel model) {
		this.model = model;
	}
	public String getHuoVsHopListPage() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm100");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	public String getAddHuoVsHosPage() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm100");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	public String getHuoVsHopList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.huoVsHopService.getHuoVsHopList(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm104");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String getHuoLisProject() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.huoVsHopService.getHuoLisProject(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm104");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String getHuoPacsProject() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.huoVsHopService.getHuoPacsProject(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm104");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String getHuoCommonProject() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.huoVsHopService.getHuoCommonProject(model, page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm104");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String saveHuoVsHos()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if ( model.getId()!=null && !"".equals(model.getId())){
			HuoVsHop z = this.huoVsHopService.getHuoVsHop(model.getId());
			z.setHos_data_name(model.getHos_data_name());
			z.setHuo_data_code(model.getHuo_data_code());
			z.setHuo_data_name(model.getHuo_data_name());
			z.setData_type(model.getData_type());
			z.setUpdater(user.getUserid());
			z.setUpdate_time(new Date());
			String str=this.huoVsHopService.updateHuoVsHop(z);
			if(str=="0"){
				str="修改失败";
			}else{
				str="修改成功";
			}
			this.message = str;
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm105");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			HuoVsHop c = new HuoVsHop();
			String datestr=DateTimeUtil.getDateTimes();
			c.setHos_data_code("jk"+datestr);
			c.setHuo_data_name(model.getHuo_data_name());
			c.setHos_data_name(model.getHos_data_name());
			c.setHos_piny(model.getHos_piny());
			c.setHuo_data_code(model.getHuo_data_code());
			c.setData_type(model.getData_type());
			c.setCreate_time(new Date());
			c.setCreater(user.getUserid());
			c.setUpdate_time(new Date());
			c.setUpdater(user.getUserid());
			String str=this.huoVsHopService.saveHuoVsHop(c);
			if(str=="0"){
				str="添加失败";
			}else{
				str="添加成功";
			}
			this.message = str;
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm105");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult( this.message );
		return NONE;
	}
	public String deleteHuoVsHop() throws  WebException,SQLException{
		String yi=this.huoVsHopService.deleteHuoVsHop(model.getId());
		if(yi=="1"){
			this.outJsonStrResult(this.message="删除成功");	
		}else{
			this.outJsonStrResult(this.message="删除失败");
		}
		UserDTO user = (UserDTO) session.get("username");
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
	public String getUpdateHuoVsHosPage() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String id=model.getId();
		HuoVsHop data=this.huoVsHopService.getHuoVsHop(id);
		model.setData_type(data.getData_type());
		model.setHos_data_name(data.getHos_data_name());
		model.setHuo_data_code(data.getHuo_data_code());
		model.setHuo_data_name(data.getHuo_data_name());
		model.setHos_piny(data.getHos_piny());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm100");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	
}
