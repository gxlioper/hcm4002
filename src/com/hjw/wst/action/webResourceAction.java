package com.hjw.wst.action;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebResourceDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.WebResourceModel;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebResourceService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  资源管理
     * @author: zr     
     * @date:   2016年12月15日 下午3:05:41   
     * @version V2.0.0.0
 */
public class webResourceAction  extends BaseAction implements ModelDriven{
	private WebResourceModel  model  = new WebResourceModel();
	private WebResourceService  webResourceService;
	private SyslogService syslogService;  
	
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public WebResourceService getWebResourceService() {
		return webResourceService;
	}

	public void setWebResourceService(WebResourceService webResourceService) {
		this.webResourceService = webResourceService;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	public void setModel(WebResourceModel model) {
		this.model = model;
	}

	/**
	 * 
	     * @Title: getwebResource   
	     * @Description: TODO(资源管理页面555)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String  getwebResourceZYGL() throws WebException{
		model.setIid(model.getIid());
		model.setType(model.getType());
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("555");//子功能id 必须填写
		sl.setExplain("资源管理页面");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getWebResourceTable   
	     * @Description: TODO(获取资源列表556)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getWebResourceTable() throws WebException{
			this.outJsonResult(this.webResourceService.getWebResourceTable(model));
		return NONE;
	}
	/**
	 * 
	     * @Title: addWebResource   
	     * @Description: TODO(资源授权557)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addWebResource() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		   if( model.getId()>0 ){
				this.webResourceService.updateWebResource(model, user);
				message="修改成功";
			
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("557");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
				
				
			}else{
				this.webResourceService.addWebResource(model,user);
				message="添加成功";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("557");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
				
			}
			this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: addWebResourcePage   
	     * @Description: TODO(新增资源页面559)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addWebResourcePage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: updateWebResourcePage   
	     * @Description: TODO(修改资源页面560)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateWebResourcePage()throws WebException{
		WebResrelAtionship   web = this.webResourceService.getWebResource(model);
		model.setRes_code(web.getRes_code());
		model.setDatavalue(web.getDatavalue());
		model.setId(web.getId());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: WebResourcePagecombox   
	     * @Description: TODO(资源下拉框561)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String WebResourcePagecombox()throws WebException{
		this.outJsonResult(this.webResourceService.updataWebResourceSelect(model));
		return NONE;
	}
	/**
	 * 
	     * @Title: deleteWebResource   
	     * @Description: TODO(删除资源562)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteWebResource()throws WebException{
		this.webResourceService.deleteWebResourceSelect(model);
			this.message="删除成功";
			this.outJsonStrResult(message);
			
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("562");//子功能id 必须填写
			sl.setExplain("删除资源");//操作说明
			syslogService.saveSysLog(sl);
			
		return NONE;
	}
	/**
	 * 
	     * @Title: webResourcePD   
	     * @Description: TODO(新增验证资源重复563)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  webResourcePD()throws WebException{
		int a = this.webResourceService.getWebResourcePD(model);
		if(a==1){
			this.message = "no";
		}else{
			this.message = "ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getWebResourceDTO   
	     * @Description: TODO(获取资源描述564)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getWebResourceDTO() throws WebException{
		WebResourceDTO web = this.webResourceService.getWebResourceDTO(model);
			if( web != null ){
				this.outJsonResult(web);
			}
		return NONE;
	}
}
