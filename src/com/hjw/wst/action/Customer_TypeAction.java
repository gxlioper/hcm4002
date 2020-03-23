package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.domain.Customer_Type;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.Customer_TypeModel;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.Customer_TypeService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 * @param <Department>
 */
@SuppressWarnings("rawtypes")
public class Customer_TypeAction<Department> extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private Customer_TypeModel model = new Customer_TypeModel();
	private Customer_TypeService customer_TypeService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
    private SyslogService syslogService;    

 
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
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

	
	public Customer_TypeModel getModel() {
		return model;
	}

	public void setModel(Customer_TypeModel model) {
		this.model = model;
	}

	public Customer_TypeService getCustomer_TypeService() {
		return customer_TypeService;
	}

	public void setCustomer_TypeService(Customer_TypeService customer_TypeService) {
		this.customer_TypeService = customer_TypeService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 人员类型管理
	     * @Title: customer_type   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String customer_type() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("127");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 人员信息展示
	     * @Title: customer_typeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String customertypeList()  throws WebException {
		String type_name=this.model.getType_name();
		String type_code=this.model.getType_code();
		PageReturnDTO list = this.customer_TypeService.queryPageCustomer(type_code, type_name, rows, this.getPage());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 数据类型show
	     * @Title: queryAllDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	*/
	public String queryAllCustomer()throws WebException{
		List<Customer_TypeDTO> list=this.customer_TypeService.queryallCustomer();
		this.outJsonResult(list);
		return NONE;
	} 
	
	/**
	 * 更新
	     * @Title: updaterCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updaterCustomer() throws WebException{
		Customer_Type ctms=this.customer_TypeService.loadCustomer(this.model.getId());
		this.model.setType_code(ctms.getType_code());
		this.model.setType_name(ctms.getType_name());
		this.model.setType_comment(ctms.getType_comment());
		this.model.setUpdater(ctms.getUpdater());
		this.model.setUpdate_time(ctms.getUpdate_time());
		return SUCCESS;
}
	/**
	 * 删除
	     * @Title: deleteCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCustomer() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		Customer_Type ctms=this.customer_TypeService.loadCustomer(this.model.getId());
		this.customer_TypeService.deleteCustomer(ctms);
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("130");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 添加
	     * @Title: addCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCustomer() throws WebException{
		return SUCCESS;
	}
	
	public String saveCustomer() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			Customer_Type ctms=this.customer_TypeService.loadCustomer(this.model.getId());
			ctms.setType_code(model.getType_code());
			ctms.setType_name(model.getType_name());
			ctms.setType_comment(model.getType_comment());
			ctms.setUpdater(user.getUserid());
			ctms.setUpdate_time(DateTimeUtil.parse());
			this.customer_TypeService.updateCustomer(ctms);
			this.message="修改成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("132");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			Customer_Type ctms = new Customer_Type();
			ctms.setType_code(model.getType_code());
			ctms.setType_name(model.getType_name());
			ctms.setType_comment(model.getType_comment());
			ctms.setUpdater(user.getUserid());
			ctms.setUpdate_time(DateTimeUtil.parse());
			this.customer_TypeService.addCustomer(ctms);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("132");//子功能id 必须填写
			sl.setExplain("插入");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	/**
	 * 验证编码唯一性
	     * @Title: isUnique   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String isUnique() throws WebException{
		Customer_Type ctms = this.customer_TypeService.queryByNum(model.getType_code());
		if(ctms==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
}