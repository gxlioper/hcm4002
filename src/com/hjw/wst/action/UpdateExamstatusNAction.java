package com.hjw.wst.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UpdateExamStatusNService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class UpdateExamstatusNAction extends BaseAction implements  ModelDriven{
	private UpdateExamStatusNService updateExamStatusNService;
	private int page=1;
	private int pageSize=15;
	private String exam_num="";
	private String ids="";
	private SyslogService syslogService;
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
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
	public String getExam_num() {
		return exam_num;
	}
	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}
	public UpdateExamStatusNService getUpdateExamStatusNService() {
		return updateExamStatusNService;
	}
	public void setUpdateExamStatusNService(UpdateExamStatusNService updateExamStatusNService) {
		this.updateExamStatusNService = updateExamStatusNService;
	}
	@Override
	public Object getModel() {
		return null;
	}
	/**
	 * 项目状态改为未检功能页599
	     * @Title: updateExamStatrsNPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamStatrsNPage() throws  WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("599");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 获取人员体检项目600
	     * @Title: getExamInfoItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoItemList() throws  WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto = this.updateExamStatusNService.getExamInfoItem(this.getExam_num().trim(), user.getCenter_num(), page,this.pagesize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 修改项目登记状态为未检N901
	     * @Title: updateStatusN   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateStatusN() throws  WebException, SQLException {
		this.updateExamStatusNService.saveItemexamStatus(ids);
		this.outJsonStrResult(this.message="已取消登记");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("901");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}
