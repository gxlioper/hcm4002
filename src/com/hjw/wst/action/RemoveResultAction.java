package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.RemoveResulModel;
import com.hjw.wst.service.RemoveResultService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年2月8日 下午3:59:32   
     * @version V2.0.0.0
 */
public class RemoveResultAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 8217814910220881058L;
	
	private RemoveResulModel model = new RemoveResulModel();
	private RemoveResultService removeResultService;
	private SyslogService syslogService;
	
	public void setRemoveResultService(RemoveResultService removeResultService) {
		this.removeResultService = removeResultService;
	}
	public RemoveResulModel getModel() {
		return model;
	}
	public void setModel(RemoveResulModel model) {
		this.model = model;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	/**
	 * 清除结果功能页面 730
	     * @Title: getRemoveResultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getRemoveResultPage()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("730");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 按体检号查询已检项目信息  731
	     * @Title: getRemoveResultItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getRemoveResultItemList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = this.removeResultService.getCheckedExamChargingItem(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 按项目清除结果信息 732
	     * @Title: saveRemoveResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String saveRemoveResult() throws WebException,SQLException{
		JSONArray itemarray = JSONArray.fromObject(this.model.getItemLists());
		List<ExaminfoChargingItemDTO> listitem = (List<ExaminfoChargingItemDTO>) JSONArray.toCollection(itemarray,ExaminfoChargingItemDTO.class);
		this.model.setItemList(listitem);
		
		ExamInfo examInfo = this.removeResultService.getExamInfo(this.model.getId());
		
		if(examInfo.getExam_num().equals(this.model.getExam_num())){
			this.message = this.removeResultService.removeResult(model);
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("732");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.message = "请先查询,再清除结果!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
