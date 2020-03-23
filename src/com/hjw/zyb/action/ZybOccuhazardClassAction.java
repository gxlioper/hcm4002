package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.domain.ZybOccuhazardClass;
import com.hjw.zyb.model.ZybOccuhazardClassModel;
import com.hjw.zyb.service.ZybOccuhazardClassService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import com.hjw.wst.domain.*;
import com.hjw.wst.DTO.*;

/**
 * 职业危害类别管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年3月29日 下午5:30:19   
     * @version V2.0.0.0
 */
public class ZybOccuhazardClassAction  extends  BaseAction implements  ModelDriven{
	private int page=1;
	private int pageSize=15;
	private ZybOccuhazardClassService ZybOccuhazardClassService;
	private ZybOccuhazardClassModel model = new ZybOccuhazardClassModel();
	private String ids="";
	private SyslogService syslogService;    

	
	
	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public ZybOccuhazardClassService getZybOccuhazardClassService() {
		return ZybOccuhazardClassService;
	}
	

	public void setZybOccuhazardClassService(ZybOccuhazardClassService zybOccuhazardClassService) {
		ZybOccuhazardClassService = zybOccuhazardClassService;
	}

	public void setModel(ZybOccuhazardClassModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 职业危害类别页面----zyb308
	     * @Title: getZYB_OccuhazardClassPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZYB_OccuhazardClassPage() throws  WebException,SQLException{
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb208");//子功能id 必须填写
		sl.setExplain("职业危害类别管理页面");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}
	/**
	 *  职业危害类别列表----zyb309
	     * @Title: getZYB_OccuhazardClassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZYB_OccuhazardClassList() throws  WebException,SQLException{
		PageReturnDTO  dto = this.ZybOccuhazardClassService.queryOccuhazardClassList(model, page, pageSize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 删除职业危害类别----zyb310
	     * @Title: deleteZYB_OccuhazardClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZYB_OccuhazardClass() throws  WebException,SQLException{
		this.ZybOccuhazardClassService.deleteOccuhazardClass(ids);
		this.outJsonStrResult(this.message="删除成功!");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb210");//子功能id 必须填写
		sl.setExplain("删除职业危害类别");//操作说明
		syslogService.saveSysLog(sl);

		return NONE;
	}
	/**
	 * 职业危害类别添加页面----zyb311
	     * @Title: addZYB_OccuhazardClassPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZYB_OccuhazardClassPage() throws  WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 职业危害类别添加修改----zyb312
	     * @Title: saveZYB_OccuhazardClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveZYB_OccuhazardClass()  throws  WebException,SQLException{
		ZybOccuhazardClass occ = new ZybOccuhazardClass();
		if(model.getHazardclassID()!=null && !"".equals( model.getHazardclassID()) ){
			
			this.ZybOccuhazardClassService.updateOccuhazardClass(model);
			this.message  = "修改成功";
		}else{
			
			occ.setHazardclassID( model.getHazardclassID() );
			occ.setHazardclass_code( model.getHazardclass_code() );
			occ.setHazardclass_name( model.getHazardclass_name() );
			occ.setOrder( model.getOrder() );
			occ.setRemark( model.getRemark() );
			this.ZybOccuhazardClassService.saveOccuhazardClass(occ);
			this.message = "添加成功";
			
		}
		this.outJsonStrResult( this.message );
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		
		if("修改成功".equals(this.message)){
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		}else{
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		}
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb212");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 职业危害类别修改页面----zyb313
	     * @Title: addZYB_OccuhazardClassPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateZYB_OccuhazardClassPage() throws  WebException,SQLException{
		ZybOccuhazardClass occ = this.ZybOccuhazardClassService.getZYB_OccuhazardClass( model.getHazardclassID() );
		
		model.setHazardclassID( occ.getHazardclassID() );
		model.setHazardclass_code( occ.getHazardclass_code() );
		model.setHazardclass_name( occ.getHazardclass_name() );
		model.setOrder( occ.getOrder() );
		model.setRemark( occ.getRemark() );
		
		return SUCCESS;
	}
	/**
	 * 职业危害类别验证编码唯一----zyb314
	     * @Title: getVerificationHazardclassCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getVerificationHazardclassCode() throws  WebException,SQLException{
		this.outJsonStrResult(this.ZybOccuhazardClassService.getVerificationHazardclassCode( model.getHazardclass_code() )+"" );
		
		return NONE;
	}
}
