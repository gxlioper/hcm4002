package com.hjw.wst.action;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.GroupSchedulingDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.GroupScheduling;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.GroupSchedulingModel;
import com.hjw.wst.service.GroupSchedulingService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class GroupSchedulingAction  extends BaseAction implements ModelDriven{
	GroupSchedulingModel model = new GroupSchedulingModel();
	private GroupSchedulingService groupSchedulingService;
	
	public void setGroupSchedulingService(GroupSchedulingService groupSchedulingService) {
		this.groupSchedulingService = groupSchedulingService;
	}
	@Override
	public Object getModel() {
		return model;
	}
	
	public void setModel(GroupSchedulingModel model) {
		this.model = model;
	}
	/**
	 * 排期页面902
	     * @Title: getSchedulePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSchedulePage() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			//排期页面编辑功能资源
			for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS056")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setSignificance("1");
						break;
					}
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 获取排期数据903
	     * @Title: getSchedulePageList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSchedulePageList() throws  WebException{
		List<GroupSchedulingDTO>  li = groupSchedulingService.getGroupSchedulingList(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 修改排期904
	     * @Title: updateSchedulePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateSchedulePage() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.groupSchedulingService.updateGroupScheduling(model, user);
		this.outJsonStrResult(this.message="修改成功");
		return NONE;
	}
	/**
	 * 删除排期905
	     * @Title: deleteSchedulePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteSchedulePage() throws  WebException{
		this.groupSchedulingService.deleteGroupSchedulingList(model.getId());
		this.outJsonStrResult(this.message="删除成功");
		return NONE;
	}
	/**
	 * 新增排期906
	     * @Title: saveSchedulePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveSchedulePage() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		GroupScheduling  g = new GroupScheduling();
		g.setScheduling_content(model.getScheduling_content());
		g.setScheduling_time(DateTimeUtil.parse0(model.getScheduling_time()));
		g.setScheduling_end_time(DateTimeUtil.parse0(model.getScheduling_end_time()));
		g.setTitle(model.getTitle());
		g.setSignificance(model.getSignificance());
		g.setCreater(user.getUserid());
		g.setCreate_time(DateTimeUtil.parse());
		GroupScheduling  c = this.groupSchedulingService.addGroupScheduling(g);
		this.outJsonStrResult(this.message=c.getId()+"");
		return NONE;
	}
	

}
