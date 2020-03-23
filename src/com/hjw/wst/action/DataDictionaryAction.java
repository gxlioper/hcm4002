package com.hjw.wst.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DataDictionaryModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DataDictionaryService;
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
public class DataDictionaryAction<Department> extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private DataDictionaryModel model = new DataDictionaryModel();
	private DataDictionaryService dataDictionaryService;
	

	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SyslogService syslogService;

	private CustomerInfoService customerInfoService;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
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

	public DataDictionaryModel getModel() {
		return model;
	}

	public void setModel(DataDictionaryModel model) {
		this.model = model;
	}

	
	public DataDictionaryService getDataDictionaryService() {
		return dataDictionaryService;
	}

	public void setDataDictionaryService(DataDictionaryService dataDictionaryService) {
		this.dataDictionaryService = dataDictionaryService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 数据字典管理
	     * @Title: DataDictionary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String datadictionary() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("120");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String datadictionaryManage() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1300");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public void update_dictionary_off_on() throws WebException, Exception {
		try {
			DataDictionary ddt=this.dataDictionaryService.loadDaDt(this.model.getId());
			if(ddt.getIsActive().equals("Y"))
				ddt.setIsActive("N");
			else if(ddt.getIsActive().equals("N"))
				ddt.setIsActive("Y");
			this.dataDictionaryService.updateDaDt(ddt);
			this.message="success";
		} catch (Exception e) {
			this.message="error";
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1304");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
	}
	
	/**
	 * 数据show
	     * @Title: DataDictionaryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String datadictionaryList()  throws WebException {
		String data_type=this.model.getData_type();
		String data_code=this.model.getData_code();
		String data_name=this.model.getData_name();
		PageReturnDTO list = this.dataDictionaryService.queryPageDaDt(data_code, data_name, data_type, rows, this.getPage());
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
	public String queryAllDaDt()throws WebException{
		List<DataDictionaryDTO> list=this.dataDictionaryService.queryallDaDt();
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 更新
	     * @Title: updaterDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updaterDaDt() throws WebException{
		DataDictionary ddt=this.dataDictionaryService.loadDaDt(this.model.getId());
		this.model.setData_type(ddt.getData_type());
		this.model.setData_code(ddt.getData_code());
		this.model.setData_name(ddt.getData_name());
		this.model.setRemark(ddt.getRemark());
		this.model.setUpdater(ddt.getUpdater());
		this.model.setUpdate_time(ddt.getUpdate_time());
		this.model.setSeq_code(ddt.getSeq_code());
		this.model.setData_code_children(ddt.getData_code_children());
		this.model.setData_class(ddt.getData_class());
		return SUCCESS;
}
	/**
	 * 删除
	     * @Title: deleteDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteDaDt() throws WebException{
		DataDictionary ddt = this.dataDictionaryService.loadDaDt(this.model.getId());
		ddt.setIsActive("N");
		this.dataDictionaryService.deleteDaDt(ddt);
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("125");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 添加
	     * @Title: addDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addDaDt() throws WebException{
		return SUCCESS;
	}
	
	public String dataTypeAndItemAdd() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1301");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String dataItemAdd() throws WebException{
		DataDictionary dataDictionary = this.dataDictionaryService.queryByNum(this.model.getData_code());
		this.model.setData_type(dataDictionary.getData_type());
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1302");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String data_type_validate() throws WebException, SQLException{
		DataDictionary dataDictionary = this.dataDictionaryService.getDataCode(this.model.getData_type());
		if(dataDictionary==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String data_code_validate() throws WebException, SQLException{
		DataDictionary dataDictionary = this.dataDictionaryService.queryByNum(this.model.getData_code());
		if(dataDictionary==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String data_name_validate() throws WebException, SQLException{
		String data_type = this.model.getData_type();
		String data_code = this.model.getData_code();
		String data_name = this.model.getData_name();
		long id = this.model.getId();
		DataDictionary dataDictionary = this.dataDictionaryService.queryByData_name(data_code, data_type, data_name, id);
		if(dataDictionary==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String saveDaDt() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			DataDictionary ddt = this.dataDictionaryService.loadDaDt(this.model.getId());
			ddt.setData_type(model.getData_type());
			ddt.setData_code(model.getData_code());
			ddt.setData_name(model.getData_name());
			ddt.setRemark(model.getRemark());
			ddt.setUpdater(user.getUserid());
			ddt.setUpdate_time(DateTimeUtil.parse());
			ddt.setSeq_code(model.getSeq_code());
			ddt.setData_code_children(model.getData_code_children());
			ddt.setData_class(model.getData_class());
			this.dataDictionaryService.updateDaDt(ddt);
			this.message="修改成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("126");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			DataDictionary ddt = new DataDictionary();
			ddt.setData_type(model.getData_type());
			ddt.setData_code(model.getData_code());
			ddt.setData_name(model.getData_name());
			ddt.setRemark(model.getRemark());
			ddt.setCreater(user.getUserid());
			ddt.setCreate_time(DateTimeUtil.parse());
			ddt.setSeq_code(model.getSeq_code());
			ddt.setData_code_children(model.getData_code_children());
			ddt.setData_class(model.getData_class());
			ddt.setIsActive("Y");
			this.dataDictionaryService.addDaDt(ddt);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("126");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	/**
	 *  验证科室编码唯一性
	     * @Title: isUnique   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dep_num
	     * @param: @return      
	     * @return: boolean      
	     * @throws
	 */
	public String isUnique() throws WebException{
		DataDictionary ddt = this.dataDictionaryService.queryByNum(model.getData_code());
		if(ddt==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String getDataCode()throws WebException{
		DataDictionary d=this.dataDictionaryService.getDataCode(this.model.getData_type());
		this.outJsonResult(d);
		return NONE;
	}
	
	/**
	 * 数据字典管理
	     * @Title: DataDictionary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getcenterconfig() throws WebException, SQLException {
		/*UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1650");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);*/
		return SUCCESS;
	}
	
	
	/**
	 * 添加
	     * @Title: addDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addcenterconfig() throws WebException{
		return SUCCESS;
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * 更新
	     * @Title: updaterDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updatecenterconfig() throws WebException, UnsupportedEncodingException{
		UserDTO user = (UserDTO) session.get("username");
		String config_key = URLDecoder.decode(this.model.getConfig_key(),"utf-8");
		
		CenterConfigurationDTO dto = this.customerInfoService.getCenterconfigByKey(config_key, user.getCenter_num());
		this.model.setCenter_name(dto.getCenter_name());
		this.model.setConfig_key(dto.getConfig_key());
		this.model.setConfig_value(dto.getConfig_value());
		/*if(dto.getIs_active().equals("Y")){
			this.model.setIs_active("有效");
		}else{
			this.model.setIs_active("无效");
		}*/
		this.model.setIs_active(dto.getIs_active());
		this.model.setCommon(dto.getCommon());
		this.model.setCenter_type(dto.getCenter_type()+"");
		/*if((dto.getCenter_type()+"").equals("1")){
			this.model.setCenter_type("前台");
		}else if((dto.getCenter_type()+"").equals("2")){
			this.model.setCenter_type("科室");
		}else if((dto.getCenter_type()+"").equals("3")){
			this.model.setCenter_type("总检");
		}else if((dto.getCenter_type()+"").equals("4")){
			this.model.setCenter_type("打印");
		}else if((dto.getCenter_type()+"").equals("5")){
			this.model.setCenter_type("接口");
		}*/
		this.model.setCenter_type(dto.getConfig_key());
		return SUCCESS;
}
	
	/**
	 * 数据show
	     * @Title: DataDictionaryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String centerConfigList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String center_type = this.model.getCenter_type();
		String common=this.model.getCenter_common();
		PageReturnDTO list = this.dataDictionaryService.centerConfigList(center_type, common, user.getCenter_num(),rows, this.getPage());
		this.outJsonResult(list);
	
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1653");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	public String savacenterconfig() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		String center_name = this.model.getCenter_name();
		String config_key = this.model.getConfig_key();
		String config_value = this.model.getConfig_value();
		String is_active = this.model.getIs_active();
		String common = this.model.getCommon();
		String center_type = this.model.getCenter_type();
		
		boolean centerflag=false;
		 centerflag = this.dataDictionaryService.savacenterconfig(center_name,config_key,config_value,is_active,common,center_type, user.getCenter_num());
		if(centerflag){
			this.message="ok-新增成功";
		}else{
			this.message="error-操作失败";
		}
		
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1654");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		//System.err.println(message);
		this.outJsonStrResult(this.message);
		
		
		return NONE;
	}
	
public String updateCenterConfigEdit() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
	
		String center_name = this.model.getCenter_name();
		String config_key = this.model.getConfig_key();
		String config_value = this.model.getConfig_value();
		String is_active = this.model.getIs_active();
		String common = this.model.getCommon();
		String center_type = this.model.getCenter_type();
		
		
		boolean centerflag=false;
		 centerflag = this.dataDictionaryService.updateCenterConfigEdit(center_name,config_key,config_value,is_active,common,center_type, user.getCenter_num());
		if(centerflag){
			this.message="ok-修改成功";
		}else{
			this.message="error-操作失败";
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1655");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		//System.err.println(message);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
}