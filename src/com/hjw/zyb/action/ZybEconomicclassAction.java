package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybEconomicclassDTO;
import com.hjw.zyb.domain.ZybEconomicClass;
import com.hjw.zyb.model.ZybEconomicclassModel;
import com.hjw.zyb.service.ZybEconomicclassService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 经济类型管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zt     
     * @date:   2017年5月3日 下午3:58:20   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybEconomicclassAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;
	
	private ZybEconomicclassService zybEconomicclassService;
	private ZybEconomicclassModel model = new ZybEconomicclassModel();
	private SyslogService syslogService;    

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setSyslogService(SyslogService syslogService) {
	   	this.syslogService = syslogService;
	}

	public Object getModel() {
		return model;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
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


	public ZybEconomicclassService getZybEconomicclassService() {
		return zybEconomicclassService;
	}

	public void setZybEconomicclassService(
			ZybEconomicclassService zybEconomicclassService) {
		this.zybEconomicclassService = zybEconomicclassService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setModel(ZybEconomicclassModel model) {
		this.model = model;
	}

	/**
	 * 经济类型管理200
	     * @Title: ecnomicclassManger   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String ecnomicclassManger() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb200");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	
	/**
	 * 经济类型数据加载zyb201
	     * @Title: geteconomicclassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String geteconomicclassList() throws WebException, Exception {
		PageReturnDTO i = this.zybEconomicclassService.queryPageEconomicclass(model,this.rows,this.getPage());
		this.outJsonResult(i);
		return NONE;

	}
	
	/**
	 * 编码查询zyb284
	     * @Title: getBycode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getBycode() throws WebException, Exception {
		ZybEconomicClass e=this.zybEconomicclassService.queryByNum(this.model.getEconomicclass_code());
		/*if(e!=null){
			this.message="no";
		}else{
			this.message="ok";
		}
		this.outJsonStrResult(this.message);*/
		this.outJsonResult(e);
		return NONE;
	}
	
	
	/**
	 * 父级新增zyb280
	     * @Title: f_addEcoclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String f_addEcoclass() throws  WebException, Exception {
		return SUCCESS;
	}
	
	
	/**
	 * 经济类型删除zyb285
	     * @Title: deleteEcoclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String deleteEcoclass() throws WebException, Exception {
		ZybEconomicClass e=this.zybEconomicclassService.queryById(this.model.getEconomicID());
		if(!"".equals(e.getParentID()) && e.getParentID()!=null){
			this.zybEconomicclassService.deleteEconomic(e);
			this.outJsonStrResult(this.message="删除成功");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb285");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			List<ZybEconomicClass> list=this.zybEconomicclassService.getByidList(e.getEconomicID());
			if(list.size()==0){
				this.zybEconomicclassService.deleteEconomic(e);
				this.outJsonStrResult(this.message="删除成功");
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb285");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				this.outJsonStrResult(this.message="请先删除子级菜单");
			}
			
		}
		return NONE;
	}
	
	
	/**
	 * 经济类型更新zyb281
	     * @Title: updateEcoclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String updateEcoclass()throws WebException, Exception {
		
		ZybEconomicClass e=this.zybEconomicclassService.queryById(this.model.getEconomicID());
		this.model.setEconomicclass_code(e.getEconomicclass_code());
		this.model.setEconomicclass_name(e.getEconomicclass_name());
		this.model.setParentID(e.getParentID());
		if(!"".equals(e.getParentID()) && e.getParentID() !=null){
			ZybEconomicClass c=this.zybEconomicclassService.getfujidanwei(e.getParentID());
			this.model.setEconomicclass_name_e(c.getEconomicclass_name());
		}
		return SUCCESS;
	}
	
	
	/**
	 * 经济类型父级保存zyb202
	     * @Title: saveEcoclass_f   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public 	String saveEcoclass_f()throws WebException, Exception {
		if(!"".equals(this.model.getEconomicID()) && this.model.getEconomicID() !=null){
			ZybEconomicClass e=this.zybEconomicclassService.queryById(this.model.getEconomicID());
			if(!"".equals(e.getParentID()) && e.getParentID()!=null){
				e.setEconomicclass_code(this.model.getEconomicclass_code());
				e.setEconomicclass_name(this.model.getEconomicclass_name());
				e.setParentID(this.model.getParentID());
				this.zybEconomicclassService.updateEconomic(e);
				this.outJsonStrResult(this.message="子级修改成功");
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb202");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				e.setEconomicclass_code(this.model.getEconomicclass_code());
				e.setEconomicclass_name(this.model.getEconomicclass_name());
				e.setParentID("");
				this.zybEconomicclassService.updateEconomic(e);
				this.outJsonStrResult(this.message="父级修改成功");
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb202");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}
			
		}else{
			ZybEconomicClass e=new ZybEconomicClass();
			e.setEconomicclass_code(this.model.getEconomicclass_code());
			e.setEconomicclass_name(this.model.getEconomicclass_name());
			if(!"".equals(this.model.getParentID()) && this.model.getParentID()!=null){
				e.setParentID(this.model.getParentID());
				this.zybEconomicclassService.addEconomic(e);
				this.outJsonStrResult(this.message="子级添加成功!");
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb202");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				e.setParentID("");
				this.zybEconomicclassService.addEconomic(e);
				this.outJsonStrResult(this.message="父级添加成功!");
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb202");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}
			
			
		}
		
		return NONE;
	}
	
	
	/**
	 * 经济类型查询全部zyb282
	     * @Title: getAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getAll()throws WebException, Exception {
		List<ZybEconomicclassDTO> list=this.zybEconomicclassService.queryByAll();
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 批量删除
	     * @Title: deletes_Ecoclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	
	public String deletes_Ecoclass()throws WebException, Exception {
		this.zybEconomicclassService.deletes(this.model.getIds());
		this.outJsonStrResult(this.message="删除成功!");
		return NONE;
	}
	 */
	
	/**
	 * 经济类型子级新增zybzyb283
	     * @Title: addEcoclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String addEcoclass() throws  WebException, Exception {
		return SUCCESS;
	}
}