package com.hjw.wst.action;

import com.hjw.wst.DTO.ExamFlowRemakDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamFlowModel;
import com.hjw.wst.service.ExamFlowService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 流程备注
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: yangm     
     * @date:   2018年3月22日 下午6:32:12   
     * @version V2.0.0.0
 */
@SuppressWarnings("serial")
public class FlowRemakAction  extends BaseAction implements ModelDriven{
	private ExamFlowModel model = new ExamFlowModel();
	private int page = 1;
	private int pageSize = 15;
	private ExamFlowService  examFlowService;
	/**
	 * 流程备注页面1441
	     * @Title: RemakPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String remakPage() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExamFlowRemakDTO dto =	this.examFlowService.getFlowRemakJilu(model);
		model.setId(dto.getId());
		model.setRemark(dto.getRemark());
		return SUCCESS;
	}
	/**
	 * 流程备注列表1442
	     * @Title: queryFlowRemak   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryFlowRemak() throws  WebException{
		PageReturnDTO dto =	this.examFlowService.queryFlowRemak(model,pageSize,page);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 通过体检号获取人员信息 1443
	     * @Title: queryFlowRemak   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getNumExamInfo() throws  WebException{
		ExamInfoDTO dto =	this.examFlowService.getNumExamInfo(model);
		if(dto!=null && dto.getId()>0){
			this.message="1";//人员信息有效
		} else {
			this.message="2";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 保存流程备注1444
	     * @Title: saveFlowRemak   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveFlowRemak() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		model.setUser_id(user.getUserid());
		if(model.getId()>0){
			examFlowService.updateFlowRemak(model);
			this.message = "修改成功！";
		} else {
			examFlowService.saveFlowRemak(model);
			this.message = "保存成功！";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public ExamFlowService getExamFlowService() {
		return examFlowService;
	}
	public void setExamFlowService(ExamFlowService examFlowService) {
		this.examFlowService = examFlowService;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	@Override
	public Object getModel() {
		return model;
	}
	public void setModel(ExamFlowModel model) {
		this.model = model;
	}

}
