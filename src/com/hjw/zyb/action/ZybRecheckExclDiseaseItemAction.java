package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.domain.ZybRecheckItem;
import com.hjw.zyb.domain.ZybrecheckExclDisease;
import com.hjw.zyb.model.ZybrecheckExclDiseaseModel;
import com.hjw.zyb.service.ZybcheckItemDiseaseService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 复查排除疾病/及要求
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月20日 上午9:59:12   
     * @version V2.0.0.0
 */
public class ZybRecheckExclDiseaseItemAction extends  BaseAction  implements  ModelDriven{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private ZybrecheckExclDiseaseModel model = new ZybrecheckExclDiseaseModel();
	
	private int page=1;
	private int pageSize=15;
	private String ids;
	private ZybcheckItemDiseaseService  zybcheckItemDiseaseService;
	private SyslogService syslogService;    

	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public ZybcheckItemDiseaseService getZybcheckItemDiseaseService() {
		return zybcheckItemDiseaseService;
	}

	public void setZybcheckItemDiseaseService(ZybcheckItemDiseaseService zybcheckItemDiseaseService) {
		this.zybcheckItemDiseaseService = zybcheckItemDiseaseService;
	}

	public void setModel(ZybrecheckExclDiseaseModel model) {
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
	/**
	 * 复查项目及要求List----zyb351
	     * @Title: queryCheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryCheckItem() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		model.setCenter_num(user.getCenter_id());
		PageReturnDTO  dto = this.zybcheckItemDiseaseService.queryCheckItem(model, page, pageSize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 复查项目及要求delete----zyb352
	     * @Title: deleteCheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCheckItem() throws  WebException,SQLException{
		this.zybcheckItemDiseaseService.deleteCheckItem(ids);
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb352");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 复查项目及要求修改页面----zyb353
	     * @Title: updateCheckItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateCheckItemPage() throws  WebException,SQLException{
		ZybRecheckItem  item = this.zybcheckItemDiseaseService.getCheckItem(model);
		model.setCenter_num(item.getCenter_num());
		model.setCheck_item_ask(item.getCheck_item_ask());
		model.setCheck_item_id(item.getCheck_item_id());
		return SUCCESS;
	}
	/**
	 * 复查项目及要求新增页面----zyb354
	     * @Title: addCheckItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addCheckItemPage() throws  WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 保存复查项目及要求----zyb355
	     * @Title: saveCheckItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCheckItem() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if( model.getCheck_item_id()!=null && !"".equals(model.getCheck_item_id())){
			ZybRecheckItem  item = this.zybcheckItemDiseaseService.getCheckItem(model);
			item.setCheck_item_ask(model.getCheck_item_ask());
			item.setCheck_item_id(model.getCheck_item_id());
			this.zybcheckItemDiseaseService.updatecheckItem(item);
			this.message="修改成功";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb355");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			ZybRecheckItem item = new ZybRecheckItem();
			item.setCenter_num(user.getCenter_id());
			item.setCheck_item_ask(model.getCheck_item_ask());
			item.setCheck_item_id(model.getCheck_item_id());
			this.zybcheckItemDiseaseService.addcheckItem(item);
			this.message="添加成功";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb355");//子功能id 必须填写
			sl.setExplain("");//操作说明
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 复查项目及要求管理页面----zyb356
	     * @Title: saveRecheckExclDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCheckItemPage() throws  WebException,SQLException{
		return SUCCESS;
	}
	/**---------------------------------------------复查需排除疾病管理----------------------------------------------
	 * 复查需排除疾病管理----zyb357
	     * @Title: getRecheckExclDiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getRecheckExclDiseasePage() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb357");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 复查需排除疾病List----zyb358
	     * @Title: getRecheckExclDiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getRecheckExclDiseaseList() throws  WebException,SQLException{
		PageReturnDTO dto = this.zybcheckItemDiseaseService.queryRecheckExclDisease(model, page, pageSize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 复查需排除疾病删除----zyb359
	     * @Title: getRecheckExclDiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteRecheckExclDiseaseList() throws  WebException,SQLException{
		this.zybcheckItemDiseaseService.deleteRecheckExclDisease(ids);
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb359");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}	
	/**
	 * 复查需排除疾病修改页面----zyb360
	     * @Title: getRecheckExclDiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateRecheckExclDiseasePage() throws  WebException,SQLException{
		ZybrecheckExclDisease   z = this.zybcheckItemDiseaseService.getRecheckExclDisease(model);
		model.setCheck_disease_id(z.getCheck_disease_id());
		model.setCheck_disease_name(z.getCheck_disease_name());
		return SUCCESS;
	}	
	/**
	 * 复查需排除疾病新增页面----zyb361
	     * @Title: getRecheckExclDiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addRecheckExclDiseasePage() throws  WebException,SQLException{
		return SUCCESS;
	}	
	/**
	 * 复查需排除疾病保存----zyb362
	     * @Title: getRecheckExclDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveRecheckExclDisease() throws  WebException,SQLException{
		if(model.getCheck_disease_id()!=null && !"".equals(model.getCheck_disease_id())){
			ZybrecheckExclDisease  d  = this.zybcheckItemDiseaseService.getRecheckExclDisease(model);
			d.setCheck_disease_id(model.getCheck_disease_id());
			d.setCheck_disease_name(model.getCheck_disease_name());
			this.zybcheckItemDiseaseService.updateRecheckExclDisease(d);
			this.message="修改成功";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb362");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			ZybrecheckExclDisease d = new ZybrecheckExclDisease();
			d.setCheck_disease_id(model.getCheck_disease_id());
			d.setCheck_disease_name(model.getCheck_disease_name());
			this.zybcheckItemDiseaseService.addRecheckExclDisease(d);
			this.message="添加成功";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb362");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}	
	
}
