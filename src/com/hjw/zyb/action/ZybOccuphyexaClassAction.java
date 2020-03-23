package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.model.ZybOccuphyexaClassModel;
import com.hjw.zyb.service.ZybOccuphyexaClassService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import com.hjw.wst.domain.*;
import com.hjw.wst.DTO.*;
import com.hjw.zyb.domain.*;
import com.hjw.zyb.DTO.*;

/**
 * 职业体检类别
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年3月28日 上午11:31:51   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybOccuphyexaClassAction extends  BaseAction  implements  ModelDriven{
	private   ZybOccuphyexaClassModel   model = new  ZybOccuphyexaClassModel();
	private   ZybOccuphyexaClassService	ZybOccuphyexaClassService;
	private   int page=1;
	private   int pageSize=15;
	private   String ids="";
	private SyslogService syslogService;    

	
	
	public SyslogService getSyslogService() {
		return syslogService;
	}


	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public String getIds() {
		return ids;
	}


	

	public void setZybOccuphyexaClassService(ZybOccuphyexaClassService zybOccuphyexaClassService) {
		ZybOccuphyexaClassService = zybOccuphyexaClassService;
	}


	public void setModel(ZybOccuphyexaClassModel model) {
		this.model = model;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 职业体检类别管理页面----zyb301
	     * @Title: getZYB_OccuphyexaPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public String  getZYB_OccuphyexaPage() throws  WebException,SQLException{
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb201");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}
	/**
	 * 职业体检类别列表----zyb302
	     * @Title: getZYB_OccuphyexaList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getZYB_OccuphyexaList()  throws  WebException,SQLException{
		PageReturnDTO  dto = this.ZybOccuphyexaClassService.queryOccuphyexaClass(model, page, pageSize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 职业体检类别新增----zyb303
	     * @Title: addZYB_Occuphyexa   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZYB_Occuphyexa()  throws  WebException,SQLException{
		
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		
		if(model.getOccuphyexaclassID()!=null && !"".equals(model.getOccuphyexaclassID())){
			
			ZybOccuphyexaClass oc = new ZybOccuphyexaClass();
			oc.setOccuphyexaclass_name(model.getOccuphyexaclass_name());
			oc.setOrder(model.getOrder());
			oc.setRemark(model.getRemark());
			oc.setOccuphyexaclassID(model.getOccuphyexaclassID());
			this.ZybOccuphyexaClassService.updateOccuphyexaClass(oc);
			this.message="修改成功";
			
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			
		}else{
			
			ZybOccuphyexaClass oc = new ZybOccuphyexaClass();
			oc.setOccuphyexaclass_name(model.getOccuphyexaclass_name());
			oc.setOrder(model.getOrder());
			oc.setRemark(model.getRemark());
			this.ZybOccuphyexaClassService.saveOccuphyexaClass(oc);
			this.message="添加成功";
			
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			
		}
		this.outJsonStrResult(this.message);
		

		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb203");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 职业体检类别修改----zyb304
	     * @Title: updateZYB_Occuphyexa   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateZYB_Occuphyexa()   throws  WebException,SQLException{
		return NONE;
	}
	/**
	 * 职业体检类别删除----zyb305
	     * @Title: deleteZYB_Occuphyexa   
	     * @Description: TODO   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  deleteZYB_Occuphyexa()   throws  WebException,SQLException{
		this.ZybOccuphyexaClassService.deleteOccuphyexaClass(this.getIds());
		this.outJsonStrResult(this.message="删除成功!");
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb205");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 新增体检类别页面----zyb306
	     * @Title: addZYB_OccuphyexaPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZYB_OccuphyexaPage()  throws  WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 职业体检修改页面----zyb307
	     * @Title: updateZYB_OccuphyexaPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateZYB_OccuphyexaPage()  throws  WebException,SQLException{
		ZybOccuphyexaClassDTO  dto = this.ZybOccuphyexaClassService.getOccuphyexaClass(model.getOccuphyexaclassID());
		
		model.setRemark(dto.getRemark());
		model.setOrder(dto.getOrder());
		model.setOccuphyexaclassID(dto.getOccuphyexaclassID());
		model.setOccuphyexaclass_name(dto.getOccuphyexaclass_name());
		
		return SUCCESS;
	}
}
