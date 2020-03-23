package com.hjw.wst.action;

import java.util.List;

import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.HeaderDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExamResultDetailModel;
import com.hjw.wst.service.CheckResultStatisticsService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 检查结果统计
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: zr     
     * @date:   2017年7月11日 下午3:00:55   
     * @version V2.0.0.0
 */
public class CheckResultStatisticsAction extends  BaseAction   implements    ModelDriven{
	
	ExamResultDetailModel mode= new ExamResultDetailModel();
	private CheckResultStatisticsService checkResultStatisticsService;
	private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	public void setCheckResultStatisticsService(CheckResultStatisticsService checkResultStatisticsService) {
		this.checkResultStatisticsService = checkResultStatisticsService;
	}
	
	public void setMode(ExamResultDetailModel mode) {
		this.mode = mode;
	}

	@Override
	public Object getModel() {
		return mode;
	}
	/**
	 * 检验科检查结果统计页面907
	     * @Title: getExamResultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultPage() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("907");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 检验科项目908
	     * @Title: getExamItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamItemList() throws  WebException{
		 List<ExaminationItemDTO>    li = checkResultStatisticsService.getExaminationItemList();
		 this.outJsonResult(li);
		 
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("908");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 获取检查结果列表909
	     * @Title: getExamResultDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamResultDetaillist() throws  WebException{
		HeaderDTO    dto = checkResultStatisticsService.getExamResultDetail(this.mode);
		this.outJsonResult(dto);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("909");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}
