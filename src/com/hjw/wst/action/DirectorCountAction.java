package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.service.DirectorCountService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class DirectorCountAction  extends BaseAction implements ModelDriven{
	private AcceptanceCheckModel model = new AcceptanceCheckModel();
	private DirectorCountService directorCountService;
	private SyslogService syslogService;    
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	@Override
	public Object getModel() {
		return model;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setModel(AcceptanceCheckModel model) {
		this.model = model;
	}

	public void setDirectorCountService(DirectorCountService directorCountService) {
		this.directorCountService = directorCountService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public String getDirectorCountPage() throws WebException{
		Date date=new Date();//取时间
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		date=calendar.getTime(); //这个时间就是日期往后推一天的结果
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.shortFmt3(date));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("749");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String getZongjianyisheng() throws WebException{
		List<JobDTO> list = new ArrayList<JobDTO>();
		list=directorCountService.getZongjianyisheng();
		this.outJsonResult(list);
		return NONE;
	}
	
	public String getDirectorCountExamInfoList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.directorCountService.getExamInfoList(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	public String getDirectorDiseaseList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepExamResultDTO> list = this.directorCountService.getDirectorDiseaseList(Long.valueOf(this.model.getExaminfo_id()), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
}
