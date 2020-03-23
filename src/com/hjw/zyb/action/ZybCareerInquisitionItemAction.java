package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.domain.ZybCareerInquisitionItem;
import com.hjw.zyb.model.ZybCareerInquisitionItemModel;
import com.hjw.zyb.service.ZybCareerInquisitionItemService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 职业病问诊项目管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zr     
     * @date:   2017年4月10日 上午11:10:28   
     * @version V2.0.0.0
 */
public class ZybCareerInquisitionItemAction  extends  BaseAction implements   ModelDriven{
	private ZybCareerInquisitionItemService  zybCareerInquisitionItemService;
	private ZybCareerInquisitionItemModel model = new ZybCareerInquisitionItemModel();
	private int page=1;
	private int pageSize=15;
	private String ids;
	private String zt;
    private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	public String getZt() {
		return zt;
	}


	public void setZt(String zt) {
		this.zt = zt;
	}


	public String getIds() {
		return ids;
	}


	public int getPage() {
		return page;
	}


	public int getPageSize() {
		return pageSize;
	}


	public void setZybCareerInquisitionItemService(ZybCareerInquisitionItemService zybCareerInquisitionItemService) {
		this.zybCareerInquisitionItemService = zybCareerInquisitionItemService;
	}


	public void setModel(ZybCareerInquisitionItemModel model) {
		this.model = model;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**问诊项目页面----zyb323
	 * 
	     * @Title: getZybCareerInquisitionItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybCareerInquisitionItemPage() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb323");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 问诊项目列表----zyb324
	     * @Title: getZybCareerInquisitionItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybCareerInquisitionItemList() throws  WebException,SQLException{
		PageReturnDTO  dto =  this.zybCareerInquisitionItemService.queryZybCareerInquisitionItemList(model, page, pageSize);
		this.outJsonResult( dto );
		return NONE;
	}
	/**
	 * 问诊项目删除----zyb325
	     * @Title: deleteZybCareerInquisitionItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZybCareerInquisitionItem() throws  WebException,SQLException{
		this.zybCareerInquisitionItemService.deletequeryZybCareerInquisitionItem(this.getIds());
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb325");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 *  问诊项目修改页面----zyb326
	     * @Title: getUpdateZybCareerInquisitionItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getUpdateZybCareerInquisitionItemPage() throws  WebException,SQLException{
		ZybCareerInquisitionItem item = this.zybCareerInquisitionItemService.getZybCareerInquisitionItem(model.getItem_id());
		model.setItem_id(item.getItem_id());
		model.setItem_code(item.getItem_code());
		model.setItem_name(item.getItem_name());
		model.setSex(item.getSex());
		model.setOrder(item.getOrder());
		model.setIsshow(item.getIsshow());
		return SUCCESS;
	}
	/**
	 *  问诊项目添加页面----zyb327
	 * @Title: getUpdateZybCareerInquisitionItemPage   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException      
	 * @return: String      
	 * @throws
	 */
	public String addZybCareerInquisitionItemPage() throws  WebException,SQLException{
		model.setOrder(this.zybCareerInquisitionItemService.getZybCareerInquisitionItemCodeOrder());
		return SUCCESS;
	}
	/**
	 *  验证编码唯一一----zyb328
	 * @Title: getUpdateZybCareerInquisitionItemPage   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException      
	 * @return: String      
	 * @throws
	 */
	public String getZybCareerInquisitionItemCode() throws  WebException,SQLException{
		int a = this.zybCareerInquisitionItemService.getZybCareerInquisitionItemCode(model.getItem_code());
		this.outJsonStrResult(a+"");
		return NONE;
	}
	/**
	 *  保存问诊项目----zyb329
	 * @Title: getUpdateZybCareerInquisitionItemPage   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException      
	 * @return: String      
	 * @throws
	 */
	public String saveZybCareerInquisitionItemCode() throws  WebException,SQLException{
		if( zt!=null && !"".equals(zt)){
			ZybCareerInquisitionItem z = this.zybCareerInquisitionItemService.getZybCareerInquisitionItem(model.getItem_id());
			z.setIsshow(model.getIsshow());
			this.zybCareerInquisitionItemService.updateZybCareerInquisitionItem(z);
		} else{
				if ( model.getItem_id()!=null && !"".equals(model.getItem_id())){
					ZybCareerInquisitionItem z = this.zybCareerInquisitionItemService.getZybCareerInquisitionItem(model.getItem_id());
					z.setItem_id(model.getItem_id());
					z.setItem_code(model.getItem_code());
					z.setItem_name(model.getItem_name());
					z.setSex(model.getSex());
					z.setOrder(model.getOrder());
					z.setIsshow(model.getIsshow());
					this.zybCareerInquisitionItemService.updateZybCareerInquisitionItem(z);
					this.message = "修改成功";
					UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb329");//子功能id 必须填写
					sl.setExplain("");//操作说明
					syslogService.saveSysLog(sl);
				} else {
					ZybCareerInquisitionItem z  = new ZybCareerInquisitionItem();
					z.setItem_id(model.getItem_id());
					z.setItem_code(model.getItem_code());
					z.setItem_name(model.getItem_name());
					z.setSex(model.getSex());
					z.setOrder(model.getOrder());
					z.setIsshow(model.getIsshow());
					this.zybCareerInquisitionItemService.addZybCareerInquisitionItem(z);
					this.message = "添加成功";
					UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb329");//子功能id 必须填写
					sl.setExplain("");//操作说明
					syslogService.saveSysLog(sl);
				}
		}
		
		this.outJsonStrResult( this.message );
		return NONE;
	}
}
