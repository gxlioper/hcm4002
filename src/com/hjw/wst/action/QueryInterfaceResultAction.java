package com.hjw.wst.action;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.hjw.crm.DTO.JianKzDTO;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleDemo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.QueryInterfaceResultModel;
import com.hjw.wst.service.QueryInterfaceResultService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class QueryInterfaceResultAction extends BaseAction implements ModelDriven {
	private static final long serialVersionUID = 1L;
	private SyslogService syslogService;
	private QueryInterfaceResultService queryInterfaceResultService;
	private QueryInterfaceResultModel model = new QueryInterfaceResultModel();
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数

	public QueryInterfaceResultService getQueryInterfaceResultService() {
		return queryInterfaceResultService;
	}

	public void setQueryInterfaceResultService(QueryInterfaceResultService queryInterfaceResultService) {
		this.queryInterfaceResultService = queryInterfaceResultService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
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

	public QueryInterfaceResultModel getModel() {
		return model;
	}

	public void setModel(QueryInterfaceResultModel model) {
		this.model = model;
	}

	public String queryInterfaceIndex() throws WebException, SQLException {
		model.setJoin_datestart(DateTimeUtil.getDate2());
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		model.setJoin_dateend(dateString);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("600");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	public String getInterResultList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto = this.queryInterfaceResultService.getInterResultList(model, user.getCenter_num(), page, rows);
		this.outJsonResult(dto);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm154");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}


	// 重发lispacs
	public String againApply() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String str = this.queryInterfaceResultService.againApply(model,user);
		this.outJsonStrResult(str);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm154");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	public String againApplyApp() throws WebException, SQLException {
		UserDTO user =new UserDTO();
			user.setUserid(Long.valueOf(model.getUser_id()));
			String cent=queryInterfaceResultService.queryCenter(model.getUser_id());
			user.setCenter_id(Long.valueOf(cent));
		String str = this.queryInterfaceResultService.againApply(model,user);
		this.outJsonStrResult(str);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm154");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	// 重发lispacs
	public String againAll() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String str = this.queryInterfaceResultService.againAll(model,user);
		this.outJsonStrResult(str);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm154");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String getSampleType()throws  WebException,SQLException{
		List<SampleDemoDTO> list=this.queryInterfaceResultService.getSampleTypeList();
		this.outJsonResult( list );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm108");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	
	public String getSampleResultList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto = this.queryInterfaceResultService.getSampleResultList(model, user.getCenter_num(), page, rows);
		this.outJsonResult(dto);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("crm154");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 采样统计数量查询
	     * @Title: getSampleResultListShuliang   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSampleResultListShuliang() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String rst = this.queryInterfaceResultService.getSampleResultListShuliang(model, user.getCenter_num(), page, rows);
		this.outJsonStrResult(rst);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("961");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String querySampleResultIndex() throws WebException, SQLException {
		model.setSample_datestart(DateTimeUtil.getDate2());
		Date date = new Date();// 取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		date = calendar.getTime(); // 这个时间就是日期往后推一天的结果
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(date);
		model.setSample_dateend(dateString);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("600");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	

}
