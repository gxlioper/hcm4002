package com.hjw.wst.action;

import java.sql.SQLException;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamSummaryGuidanceLib;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExamSummaryGuidanceLibModel;
import com.hjw.wst.service.ExamSummaryGuidanceLibService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 体检综述（健康指导建议）action
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dq     
     * @date:   2017年7月13日 下午4:56:08   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ExamSummaryGuidanceLibAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 1L;
	private ExamSummaryGuidanceLibModel model = new ExamSummaryGuidanceLibModel();
	private ExamSummaryGuidanceLibService examSummaryGuidanceLibService;
	private SyslogService syslogService;
	
	private int page=1; // 当前页
	private int rows=15; // easyui每页显示条数
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setExamSummaryGuidanceLibService(ExamSummaryGuidanceLibService examSummaryGuidanceLibService) {
		this.examSummaryGuidanceLibService = examSummaryGuidanceLibService;
	}
	public ExamSummaryGuidanceLibModel getModel() {
		return model;
	}
	public void setModel(ExamSummaryGuidanceLibModel model) {
		this.model = model;
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
	 * 779 体检综述（健康指导建议）管理页面
	     * @Title: getExamSummaryGuidanceLibPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryGuidanceLibPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("779");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 780 获取体检综述（健康指导建议）信息列表
	     * @Title: getExamSummaryGuidanceLibList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryGuidanceLibList() throws WebException, SQLException{
		PageReturnDTO page = this.examSummaryGuidanceLibService.getExamSummaryGuidanceLibList(model, this.page, this.rows);
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 781获取体检综述（健康指导建议）编辑页面
	     * @Title: getEditExamSummaryGuidanceLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getEditExamSummaryGuidanceLib() throws WebException{
		if(model.getId() > 0){
			ExamSummaryGuidanceLib lib = this.examSummaryGuidanceLibService.getExamSummaryGuidanceLib(this.model.getId());
			
			this.model.setGuidance_word(lib.getGuidance_word());
			this.model.setGuidance_pinyin(lib.getGuidance_pinyin());
			this.model.setGuidance_content(lib.getGuidance_content());
		}
		return SUCCESS;
	}
	
	/**
	 * 782保存体检综述（健康指导建议）信息
	     * @Title: saveExamSummaryGuidanceLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryGuidanceLib() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(model.getId() > 0){
			ExamSummaryGuidanceLib lib = this.examSummaryGuidanceLibService.getExamSummaryGuidanceLib(this.model.getId());
			lib.setGuidance_word(model.getGuidance_word());
			lib.setGuidance_pinyin(model.getGuidance_pinyin());
			lib.setGuidance_content(model.getGuidance_content());
			lib.setUpdater(user.getUserid());
			lib.setUpdate_time(DateTimeUtil.parse());
			this.examSummaryGuidanceLibService.updateExamSummaryGuidanceLib(lib);
			this.message = "ok-修改保存成功!";
		}else{
			ExamSummaryGuidanceLib lib = new ExamSummaryGuidanceLib();
			lib.setGuidance_word(model.getGuidance_word());
			lib.setGuidance_pinyin(model.getGuidance_pinyin());
			lib.setGuidance_content(model.getGuidance_content());
			lib.setIs_active("Y");
			lib.setCreater(user.getUserid());
			lib.setCreate_time(DateTimeUtil.parse());
			lib.setUpdater(user.getUserid());
			lib.setUpdate_time(DateTimeUtil.parse());
			this.examSummaryGuidanceLibService.saveExamSummaryGuidanceLib(lib);
			this.message = "ok-新增保存成功!";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("782");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 783 删除体检综述（健康指导建议）信息
	     * @Title: delExamSummaryGuidanceLib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delExamSummaryGuidanceLib() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ExamSummaryGuidanceLib lib = this.examSummaryGuidanceLibService.getExamSummaryGuidanceLib(this.model.getId());
		if(lib == null){
			this.message = "error-需要删除的信息不存在,删除失败!";
		}else{
			lib.setIs_active("N");
			lib.setUpdater(user.getUserid());
			lib.setUpdate_time(DateTimeUtil.parse());
			this.examSummaryGuidanceLibService.updateExamSummaryGuidanceLib(lib);
			this.message = "ok-删除成功!";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("783");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
