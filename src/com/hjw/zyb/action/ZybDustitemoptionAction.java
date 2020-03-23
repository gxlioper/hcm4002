package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;


import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybDustitemoptionDTO;
import com.hjw.zyb.domain.ZybDustitemoption;
import com.hjw.zyb.model.ZybdustitemoptionModel;
import com.hjw.zyb.service.ZybDustitemoptionService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.action
 * @Description:
 * @author: ZT
 * @date: 2016年7月16日 下午8:32:41
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybDustitemoptionAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;
	private ZybDustitemoptionService zybDustitemoptionService;
	private ZybdustitemoptionModel model = new ZybdustitemoptionModel();
	
	private SyslogService syslogService;    

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public ZybDustitemoptionService getZybDustitemoptionService() {
		return zybDustitemoptionService;
	}

	public void setZybDustitemoptionService(ZybDustitemoptionService zybDustitemoptionService) {
		this.zybDustitemoptionService = zybDustitemoptionService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setModel(ZybdustitemoptionModel model) {
		this.model = model;
	}

	public void setSyslogService(SyslogService syslogService) {
	   	this.syslogService = syslogService;
	}

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


	/**
	 * 粉尘胸片参数管理zyb265
	     * @Title: ZybDustitemoptionManger   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String ZybDustitemoptionManger() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb265");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 粉尘胸片参数数据加载zyb266
	     * @Title: getdustitemoptionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getdustitemoptionList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list=this.zybDustitemoptionService.querydustitemoptionList(model, this.rows,this.getPage(),user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 粉尘胸片类别数据加载zyb267
	     * @Title: getdustitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getdustitemList()throws WebException,SQLException{
		List<ZybDustitemoptionDTO> list=this.zybDustitemoptionService.getdustitemList();
		this.outJsonResult(list);
		return NONE;
	}
	
	
	
	/**
	 * 粉尘胸片参数添加页面zyb268
	     * @Title: addDustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String addDustitemoption() throws WebException,SQLException{
		
		return SUCCESS;
	}
	
	
	/**
	 * 粉尘胸片参数修改页面zyb269
	     * @Title: updateDustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updDustitemoption()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ZybDustitemoptionDTO d=this.zybDustitemoptionService.getone(this.model.getOptionID(),user);
		this.model.setDustitem_name(d.getDustitem_name());
		this.model.setOption_value(d.getOption_value());
		this.model.setShoworder(d.getShoworder());
		return SUCCESS;
	}
	
	
	/**
	 * 粉尘胸片参数删除zyb270
	     * @Title: deletedustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deletedustitemoption()throws WebException,SQLException{
		ZybDustitemoption o=this.zybDustitemoptionService.queryByID(this.model.getOptionID());
		this.zybDustitemoptionService.deletedustitemoption(o);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb270");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 粉尘胸片参数保存zyb271
	     * @Title: saveDustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveDustitemoption()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(!"".equals(this.model.getOptionID()) && this.model.getOptionID()!=null){
			ZybDustitemoption o=this.zybDustitemoptionService.queryByID(this.model.getOptionID());
			o.setDustID(this.model.getDustID());
			o.setOption_value(this.model.getOption_value());
			o.setShoworder(this.model.getShoworder());
			o.setCenter_num(user.getCenter_num());
			this.zybDustitemoptionService.updatedustitemoption(o);
			this.message="修改成功!";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb271");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			ZybDustitemoption o=new ZybDustitemoption();
			o.setDustID(this.model.getDustID());
			o.setOption_value(this.model.getOption_value());
			o.setShoworder(this.model.getShoworder());
			o.setCenter_num(user.getCenter_num());
			this.zybDustitemoptionService.adddustitemoption(o);
			this.message="添加成功!";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb271");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 粉尘胸片参数批量删除zyb272
	     * @Title: deletesDustitemoption   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deletesDustitemoption()throws WebException,SQLException{
		this.zybDustitemoptionService.deletes(this.model.getIds());
		this.message="删除成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
}