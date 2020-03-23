package com.hjw.wst.action;

import java.util.List;

import org.hibernate.service.spi.ServiceException;

import com.hjw.wst.DTO.ExamSetTypeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamSetType;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExamSetTypeModel;
import com.hjw.wst.service.ExamSetTypeService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 套餐类别维护功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年10月27日 上午11:19:22   
     * @version V2.0.0.0
 */
public class ExamSetTypeAction extends  BaseAction implements  ModelDriven{
	public ExamSetTypeModel model = new ExamSetTypeModel();
	public int page = 1;
	public int pageSize = 15;
	public ExamSetTypeService examSetTypeService;
	private SyslogService syslogService; 
	
	
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public ExamSetTypeService getExamSetTypeService() {
		return examSetTypeService;
	}

	public void setExamSetTypeService(ExamSetTypeService examSetTypeService) {
		this.examSetTypeService = examSetTypeService;
	}

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

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setModel(ExamSetTypeModel model) {
		this.model = model;
	}
	/**
	 * 套餐类别列表938
	     * @Title: getqueryExamSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getqueryExamSetTypeList() throws  WebException{
		PageReturnDTO dto = this.examSetTypeService.getqueryExamSetTypeList(model, pageSize, page);
		this.outJsonResult(dto);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("938");//子功能id 必须填写
		sl.setExplain("类别列表");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 新增类别939
	     * @Title: saveExamSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSetType() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId()>0){
			this.examSetTypeService.updateSetTypeList(model, user);
			this.message = "修改成功！";
		} else {
			this.examSetTypeService.saveExamSetTypeList(model, user);
			this.message = "添加成功";
		}
		this.outJsonStrResult(this.message);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("939");//子功能id 必须填写
		sl.setExplain("新增类别");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 删除类别941
	     * @Title: deleteExamSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteExamSetTypeList() throws   WebException{
		this.examSetTypeService.deleteExamSetTypeList(model);
		this.outJsonStrResult("删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("941");//子功能id 必须填写
		sl.setExplain("删除类别");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 类别添加页面940
	     * @Title: updateSetTypeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveSetTypePage() throws  WebException{
		if(this.model.getId()>0){
			ExamSetTypeDTO dto = this.examSetTypeService.getExamSetTypeId(model);
			this.model.setEx(dto);
			this.model.setSet_type_name(dto.getSet_type_name());
			this.model.setId(dto.getId());
			this.model.setSet_class(dto.getSet_class());
		}
		return SUCCESS;
	}
	/**
	 * 套餐类别管理页面942
	     * @Title: examSetTypePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examSetTypePage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 套餐获取类别下拉框列表943
	     * @Title: getExamSet_typeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSet_typeList() throws WebException{
		List<ExamSetTypeDTO> li = this.examSetTypeService.getExamSet_typeList();
		this.outJsonResult(li);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("943");//子功能id 必须填写
		sl.setExplain("套餐获取类别下拉框列表943");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
}
