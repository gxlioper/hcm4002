package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.domain.Zyboccucontraindication;
import com.hjw.zyb.domain.ZyboccuhazardfactorsOccucontraindication;
import com.hjw.zyb.model.ZyboccucontraindicationModel;
import com.hjw.zyb.service.ZyboccucontraindicationService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 职业禁忌症
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月12日 下午4:10:09   
     * @version V2.0.0.0
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class ZyboccucontraindicationAction  extends  BaseAction  implements   ModelDriven{
	private ZyboccucontraindicationModel model = new ZyboccucontraindicationModel();
	private int page=1;
	private int pageSize=15;
	private ZyboccucontraindicationService  zyboccucontraindicationService;
	private String ids;
	private SyslogService syslogService;    

	public void setSyslogService(SyslogService syslogService) {
	   	this.syslogService = syslogService;
	}
	



	public void setZyboccucontraindicationService(
			ZyboccucontraindicationService zyboccucontraindicationService) {
		this.zyboccucontraindicationService = zyboccucontraindicationService;
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



	public void setPage(int page) {
		this.page = page;
	}



	public int getPageSize() {
		return pageSize;
	}



	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	public void setModel(ZyboccucontraindicationModel model) {
		this.model = model;
	}





	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 职业禁忌症管理页面----zyb337
	     * @Title: getOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccucontraindication() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb337");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**禁忌症列表----zyb338
	 * 
	     * @Title: queryOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryOccucontraindicationList() throws  WebException,SQLException{
		PageReturnDTO  li = this.zyboccucontraindicationService.queryOccucontraindication(model, page, pageSize);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 删除职业禁忌症----zyb339
	     * @Title: deleteOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteOccucontraindicationList() throws  WebException,SQLException{
		this.zyboccucontraindicationService.deleteOccucontraindication(ids);
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb339");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 *禁忌症添加页面----zyb340
	     * @Title: addOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addOccucontraindicationList() throws  WebException,SQLException{
		
		return SUCCESS;
	}
	/**禁忌症修改页面----zyb341
	 * 
	     * @Title: updateOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateOccucontraindicationList() throws  WebException,SQLException{
		Zyboccucontraindication  z = this.zyboccucontraindicationService.getOccucontraindication(model);
		model.setContraindication_name(z.getContraindication_name());
		model.setContraindicationID(z.getContraindicationID());
		model.setTremexplain(z.getTremexplain());
		return SUCCESS;
	}
	/**
	 * 保存职业禁忌症----zyb342
	     * @Title: saveOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveOccucontraindicationList() throws  WebException,SQLException{
		if( model.getContraindicationID()!=null && !"".equals(model.getContraindicationID())){
			
			Zyboccucontraindication  z = this.zyboccucontraindicationService.getOccucontraindication(model);
			z.setContraindication_name(model.getContraindication_name());
			z.setContraindicationID(model.getContraindicationID());
			z.setTremexplain(model.getTremexplain());
			
			this.zyboccucontraindicationService.updateOccucontraindication(z);
			this.outJsonStrResult(this.message="修改成功");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb342");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			
			Zyboccucontraindication  z = new Zyboccucontraindication();
			z.setContraindication_name(model.getContraindication_name());
			z.setContraindicationID(model.getContraindicationID());
			z.setTremexplain(model.getTremexplain());
			this.zyboccucontraindicationService.addOccucontraindication(z,model);
			this.outJsonStrResult(this.message="添加成功");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb342");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
		}
		return NONE;
	}
	/**
	 * ---------------------------------------------因素体检类别 维护--------------------------------
	 */
	/**zyb376
	 * 因素体检类别禁忌症维护页面
	     * @Title: getLbOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbOccucontraindicationPage() throws  WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 因素体检类别禁忌症关系列表zyb377
	     * @Title: getLbOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbOccucontraindicationList() throws  WebException,SQLException{
		PageReturnDTO re = 	this.zyboccucontraindicationService.getLbOccucontraindicationList(model, page, pageSize);
		this.outJsonResult(re);
		return NONE;
	}
	/**
	 *因素体检类别禁忌症关系新增页面zyb377
	     * @Title: getLbOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbAddOccucontraindicationPage() throws  WebException,SQLException{
		this.model.setOccuphyexaclassID(this.model.getOccuphyexaclassID());
		this.model.setHazardfactorsID(this.model.getHazardfactorsID());
		return SUCCESS;
	}
	/**
	 * 因素体检类别禁忌证关系保存zyb379
	     * @Title: saveLbOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveLbOccucontraindication() throws  WebException,SQLException{
		if(model.getRID()!=null && !"".equals(model.getRID())){
			this.zyboccucontraindicationService.updateLbOccucontraindication(model);
	
		} else {
			this.zyboccucontraindicationService.addLbOccucontraindication(model);
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb379");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult("保存成功!");
		return NONE;
	}
	/**
	 * 因素体检类别禁忌证关系保存zyb380
	     * @Title: saveLbOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbupdateOccucontraindicationPage() throws  WebException,SQLException{
		Zyboccucontraindication  z = this.zyboccucontraindicationService.getOccucontraindication(model);
		model.setContraindication_name(z.getContraindication_name());
		model.setContraindicationID(z.getContraindicationID());
		model.setTremexplain(z.getTremexplain());
		return SUCCESS;
	}
	/**
	 * zyb387获取检查依据列表
	     * @Title: getLbupdateOccucontraindicationPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getLbcriterionManagerList() throws  WebException,SQLException{
		this.outJsonResult(	this.zyboccucontraindicationService.getLbcriterionManagerList(model));
		return NONE;
	}
	/**因素禁忌症修改页面zyb388
	 * zyb388
	 */
	public String getupdateLbOccucontraindicationPage()  throws  WebException,SQLException{
		ZyboccuhazardfactorsOccucontraindication   z = this.zyboccucontraindicationService.getLbZyboccuhazardfactorsOccucontraindication(model);
		model.setRID(z.getRID());
		model.setContraindicationID(z.getContraindicationID());
		model.setDISORDER(z.getDISORDER());
		model.setHazardfactorsID(z.getHazardfactorsID());
		model.setOccuphyexaclassID(z.getOccuphyexaclassID());
		return SUCCESS;
	}
	/**
	 * 因素禁忌证关系删除zyb389
	     * @Title: deleteOccuhazardfactorsOccucontraindication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteOccuhazardfactorsOccucontraindication()  throws  WebException,SQLException{
		this.zyboccucontraindicationService.deleteOccuhazardfactorsOccucontraindication(model);
		this.outJsonStrResult("删除成功!");
		return NONE;
	}
}
