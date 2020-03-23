package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.model.ExaminfoChargingItemModel;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.BreakfastService;
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
public class BreakfastAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private ExaminfoChargingItemModel model = new ExaminfoChargingItemModel();
	private BreakfastService breakfastService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;//批量更新
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

	public ExaminfoChargingItemModel getModel() {
			return model;
		}

		public void setModel(ExaminfoChargingItemModel model) {
			this.model = model;
		}

	public BreakfastService getBreakfastService() {
			return breakfastService;
		}

		public void setBreakfastService(BreakfastService breakfastService) {
			this.breakfastService = breakfastService;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 早餐科室页面
	     * @Title: breakfast   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String breakfast() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("613");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 检查项目列表
	     * @Title: breakfastList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String breakfastList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String exam_num=this.model.getExam_num();
		List list = this.breakfastService.queryItemList(exam_num, user);
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 更新体检状态
	     * @Title: updaterBreakStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updaterBreakStatus() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.breakfastService.updateBreakStatus(this.ids,user);
		this.message="已更新成功";
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 早餐科室首页  728
	     * @Title: getBreakFastIndex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getBreakFastIndex() throws WebException{
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("728");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 早餐科室搜索人员信息 729
	     * @Title: getBreakFastIndexList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getBreakFastIndexList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.breakfastService.getExamInfoList(model, user, this.rows, this.getPage());
		this.outJsonResult(list);
		return NONE;
	}
}