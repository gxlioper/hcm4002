package com.hjw.zyb.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZyboccudiseaseclassDTO;
import com.hjw.zyb.DTO.ZyboccuhazardfactorsOccudiseaseDTO;
import com.hjw.zyb.domain.Zyboccudisease;
import com.hjw.zyb.model.ZyboccudiseaseModel;
import com.hjw.zyb.service.ZyboccudiseaseService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 职业病管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月11日 下午4:47:36   
     * @version V2.0.0.0
 */
public class ZyboccudiseaseAction  extends  BaseAction  implements  ModelDriven{
	private ZyboccudiseaseService zyboccudiseaseService;
	private ZyboccudiseaseModel  model= new ZyboccudiseaseModel();
	private String ids;
	private int page=1;
	private int pagesize=15;
	private SyslogService syslogService;
	private String lbname;
	private String ysname;

	
	
	
	public String getLbname() {
		return lbname;
	}


	public void setLbname(String lbname) {
		this.lbname = lbname;
	}


	public String getYsname() {
		return ysname;
	}


	public void setYsname(String ysname) {
		this.ysname = ysname;
	}


	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}


	public String getIds() {
		return ids;
	}


	public int getPage() {
		return page;
	}


	public int getPagesize() {
		return pagesize;
	}


	public ZyboccudiseaseService getZyboccudiseaseService() {
		return zyboccudiseaseService;
	}


	public void setModel(ZyboccudiseaseModel model) {
		this.model = model;
	}


	public void setZyboccudiseaseService(ZyboccudiseaseService zyboccudiseaseService) {
		this.zyboccudiseaseService = zyboccudiseaseService;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 职业病管理页面----zyb330
	     * @Title: getZyboccudiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZyboccudiseasePage() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb330");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 获取职业病类别树列表----zyb331
	     * @Title: getZyboccudiseaseClassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZyboccudiseaseClassList() throws  WebException,SQLException{
		List<ZyboccudiseaseclassDTO>  li = this.zyboccudiseaseService.queryZyboccudiseaseListclass(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 获取职业病列表----zyb332
	     * @Title: getZyboccudiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZyboccudiseaseList() throws  WebException,SQLException{
		PageReturnDTO dto =	this.zyboccudiseaseService.queryZyboccudiseaseList(model,this.getPage(),this.getPagesize());
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * s职业病删除----zyb333
	     * @Title: deleteZyboccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZyboccudisease () throws  WebException,SQLException{
		this.zyboccudiseaseService.deleteZyboccudisease(ids);
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb333");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 职业病新增页面----zyb334
	     * @Title: addZyboccudiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZyboccudiseasePage() throws  WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 职业病修改页面----zyb335
	     * @Title: updateZyboccudiseasePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateZyboccudiseasePage() throws  WebException,SQLException{
		Zyboccudisease z = this.zyboccudiseaseService.getZyboccudisease(model);
		model.setOccudiseaseID(z.getOccudiseaseID());
		model.setOccudisease_name(z.getOccudisease_name());
		model.setDiseaseclassID(z.getDiseaseclassID());
		return SUCCESS;
	}
	/**
	 * 保存职业病----zyb336
	     * @Title: saveZyboccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveZyboccudisease () throws  WebException,SQLException{
		if( model.getOccudiseaseID()!=null && !"".equals(model.getOccudiseaseID())){
			
			Zyboccudisease z = this.zyboccudiseaseService.getZyboccudisease(model);
			z.setOccudiseaseID(model.getOccudiseaseID());
			z.setOccudisease_name(model.getOccudisease_name());
			z.setDiseaseclassID(model.getDiseaseclassID());
			this.zyboccudiseaseService.updateZyboccudisease(z);
			this.outJsonStrResult(this.message="修改成功");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb336");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			Zyboccudisease z = new Zyboccudisease();
			z.setOccudiseaseID(model.getOccudiseaseID());
			z.setOccudisease_name(model.getOccudisease_name());
			z.setDiseaseclassID(model.getDiseaseclassID());
			this.zyboccudiseaseService.addZyboccudiseaseList(z);
			this.outJsonStrResult(this.message="添加成功");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb336");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		return  NONE;
	}
	/**
	 * 
	 * ---------------------------------------因素和职业病关系维护----------------------------------
	 * 
	 */
	/**
	 * 因素职业病关系管理页面zyb397
	     * @Title: getZyboccudiseaseYinsuPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZyboccudiseaseYinsuPage() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb397");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**因素职业病关系列表zyb398
	 * 
	     * @Title: getZyboccudiseaseYinsuTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZyboccudiseaseYinsuTable() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb398");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(this.zyboccudiseaseService.getZyboccudiseaseYinsuTable(model, page, pagesize));
		return NONE;
	}
	/**
	 * zyb399因素职业病关系删除
	     * @Title: getDeleteZyboccudiseaseYinsu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDeleteZyboccudiseaseYinsu() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb399");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.zyboccudiseaseService.deleteZyboccudiseaseYinsu(ids);
		this.outJsonStrResult("删除成功!");
		return NONE;
	}
	/**
	 * 因素职业病关系保存zyb600
	     * @Title: getSaveZyboccudiseaseYinsu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSaveZyboccudiseaseYinsu() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb399");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if(model.getRID()!=null && !"".equals(model.getRID())){
			this.zyboccudiseaseService.updateZyboccudiseaseYinsuList(model);
			this.message = "修改成功！";
		} else {
			this.zyboccudiseaseService.saveZyboccudiseaseYinsu(model);
			this.message = "保存成功！";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 因素和职业病关系新增页面zyb601
	     * @Title: getAddZyboccudiseaseYinsuPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getAddZyboccudiseaseYinsuPage() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb397");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 因素和职业病关系修改页面zyb602
	     * @Title: getupdateZyboccudiseaseYinsuPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getupdateZyboccudiseaseYinsuPage() throws  WebException,SQLException{
		ZyboccuhazardfactorsOccudiseaseDTO   dto  = this.zyboccudiseaseService.getFindIDZyboccudiseaseYinsuList(model);
		model.setOccuphyexaclass_name(dto.getOccuphyexaclass_name());
		model.setHazard_name(dto.getHazard_name());
		model.setDISORDER(dto.getDISORDER());
		model.setTjlb(dto.getOccuphyexaclassid());
		model.setOccudiseaseID(dto.getOccudiseaseID());
		model.setHazardfactorsID(dto.getHazardfactorsID());
		model.setRID(dto.getRID());
		return SUCCESS;
	}
	/**
	 * 因素列表zyb603
	     * @Title: getYinsuList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getYinsuList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb399");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(this.zyboccudiseaseService.getZyboccudiseaseYinsuList(model));
		return NONE;
	}
}
