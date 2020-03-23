package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.DepLogicDTO;
import com.hjw.wst.DTO.DepLogicExamItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DepLogic;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DepLogicModel;
import com.hjw.wst.service.DepLogicService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  科室逻辑
     * @author: zr     
     * @date:   2016年12月13日 下午12:47:59   
     * @version V2.0.0.0
 */
public class DepLogicAction   extends BaseAction implements ModelDriven{
	private  DepLogicModel model = new  DepLogicModel();
	private  int page=1;
	private  int rows=15;
	private  DepLogicService  depLogicService;
	private SyslogService syslogService;
	
	@Override
	public Object getModel() {
		return model;
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

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public DepLogicService getDepLogicService() {
		return depLogicService;
	}

	public void setDepLogicService(DepLogicService depLogicService) {
		this.depLogicService = depLogicService;
	}

	public void setModel(DepLogicModel model) {
		this.model = model;
	}
	/**
	 * 
	     * @Title: getDepLogicPage   
	     * @Description: TODO(科室逻辑管理页面546)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepLogicPage() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("546");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getDepLogic   
	     * @Description: TODO(科室逻辑列表547)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getDepLogic() throws WebException, SQLException{
		UserDTO  user = (UserDTO) session.get("username");
		PageReturnDTO	dto = this.depLogicService.getDepLogic(model, user, page,rows);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: deleteDepLogic   
	     * @Description: TODO(删除科室逻辑548)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteDepLogic() throws WebException, SQLException{
			this.depLogicService.deleteDepLogic(model);
			this.message="删除成功";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("548");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateDepLogicPage   
	     * @Description: TODO(科室逻辑修改页面549)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateDepLogicPage() throws WebException, SQLException{
		DepLogic   dto  = this.depLogicService.getDepLogicShou(model);
		model.setId(dto.getId());
		model.setDep_id(dto.getDep_id());
		model.setConclusion_word(dto.getConclusion_word());
		model.setSex(dto.getSex());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: addDepLogicPage   
	     * @Description: TODO(新增科室逻辑页面550)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addDepLogicPage()throws WebException, SQLException{
		return  SUCCESS;
	}
	/**
	 * 
	     * @Title: getDepLogicExamItem   
	     * @Description: TODO(科室逻辑获取所有检查项目551)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getDepLogicExamItem()throws WebException, SQLException{
		List<DepLogicExamItemDTO>    li = this.depLogicService.getDepLogicExamItem(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: addDepLogic   
	     * @Description: TODO(添加科室逻辑552)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addDepLogic() throws WebException, SQLException{
		UserDTO  user = (UserDTO) session.get("username");
		if(model.getId()>0){
			this.depLogicService.updateDepLogic(model,user);
			this.message="修改成功";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("552");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.depLogicService.addDepLogic(model,user);
			this.message="添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("552");//子功能id 必须填写
			sl.setExplain("插入");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getDepLogicShou   
	     * @Description: TODO(科室逻辑获取检查项目细项553)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepLogicExamItemShow() throws WebException, SQLException{
		this.outJsonResult(this.depLogicService.getDepLogicExamItemShow(model));
		return NONE;
	}
	/**
	 * 
	     * @Title: getDepLogixDepartmentDep   
	     * @Description: TODO(获取一般科室)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepLogixDepartmentDep()  throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.depLogicService.getDepartmentDep(user.getCenter_num()));
		return NONE;
	}
}
