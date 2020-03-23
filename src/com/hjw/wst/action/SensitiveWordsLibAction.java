package com.hjw.wst.action;

import java.sql.SQLException;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SensitiveWordsLib;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SensitiveWordsLibModel;
import com.hjw.wst.service.SensitiveWordsLibService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class SensitiveWordsLibAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	private SensitiveWordsLibModel model = new SensitiveWordsLibModel();
	private SensitiveWordsLibService sensitiveWordsLibService;
	private SyslogService syslogService;
	
	private int rows = 15; // easyui每页显示条数
	public SensitiveWordsLibModel getModel() {
		return model;
	}
	public void setModel(SensitiveWordsLibModel model) {
		this.model = model;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setSensitiveWordsLibService(SensitiveWordsLibService sensitiveWordsLibService) {
		this.sensitiveWordsLibService = sensitiveWordsLibService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	/**
	 *787 获取敏感词管理页面
	     * @Title: getSensitiveWordsLibPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSensitiveWordsLibPage() throws WebException{
		UserDTO user =(UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("787");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 788 获取敏感词列表信息
	     * @Title: getSensitiveWordsLibList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSensitiveWordsLibList() throws WebException,SQLException{
		PageReturnDTO page = this.sensitiveWordsLibService.getSensitiveWordsLibList(this.model, rows, this.getPage());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 789 获取敏感词编辑页面
	     * @Title: getEditSensitiveWordsLibPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getEditSensitiveWordsLibPage() throws WebException{
		if(this.model.getId() != null){
			SensitiveWordsLib lib = this.sensitiveWordsLibService.getSensitiveWordsLib(this.model.getId());
			this.model.setSensitive_sex(lib.getSensitive_sex());
			this.model.setSensitive_type(lib.getSensitive_type());
			this.model.setSensitive_content(lib.getSensitive_content());
		}
		return SUCCESS;
	}
	
	/**
	 * 790 保存敏感词信息
	     * @Title: editSensitiveWordsLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String editSensitiveWordsLib() throws WebException,SQLException{
		UserDTO user =(UserDTO) session.get("username");
		if(this.model.getId().length() > 0){
			SensitiveWordsLib lib = this.sensitiveWordsLibService.getSensitiveWordsLib(this.model.getId());
			lib.setSensitive_content(this.model.getSensitive_content());
			lib.setSensitive_type(this.model.getSensitive_type());
			lib.setSensitive_sex(this.model.getSensitive_sex());
			lib.setUpdater(user.getUserid());
			lib.setUpdate_time(DateTimeUtil.parse());
			this.sensitiveWordsLibService.updateSensitiveWordsLib(lib);
			this.message = "修改成功!";
		}else{
			SensitiveWordsLib lib = new SensitiveWordsLib();
			lib.setSensitive_content(this.model.getSensitive_content());
			lib.setSensitive_type(this.model.getSensitive_type());
			lib.setSensitive_sex(this.model.getSensitive_sex());
			lib.setIs_active("Y");
			lib.setCreater(user.getUserid());
			lib.setCreate_time(DateTimeUtil.parse());
			lib.setUpdater(user.getUserid());
			lib.setUpdate_time(DateTimeUtil.parse());
			this.sensitiveWordsLibService.saveSensitiveWordsLib(lib);
			this.message = "新增成功!";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("790");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 791 删除敏感词信息
	     * @Title: delSensitiveWordsLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delSensitiveWordsLib() throws WebException,SQLException{
		if(this.model.getId().length() > 0){
			SensitiveWordsLib lib = this.sensitiveWordsLibService.getSensitiveWordsLib(this.model.getId());
			lib.setIs_active("N");
			this.sensitiveWordsLibService.updateSensitiveWordsLib(lib);
			this.message = "ok-删除成功!";	
		}else{
			this.message = "error-要删除的敏感词信息不存在!";
		}
		UserDTO user =(UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("791");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 792 校验性别敏感词
	     * @Title: checkSensitiveWordsSex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkSensitiveWordsSex() throws WebException,SQLException{
		this.message = this.sensitiveWordsLibService.checkSensitiveWordsSex(model);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 793 校验特殊词敏感词
	     * @Title: checkSensitiveWord   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkSensitiveWord() throws WebException,SQLException{
		this.message = this.sensitiveWordsLibService.checkSensitiveWord(model);
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
