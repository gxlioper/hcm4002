package com.hjw.wst.action;

import java.util.ArrayList;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.interfaces.util.DateUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DiseaseKnowloedge;
import com.hjw.wst.domain.ExamCriticalClass;
import com.hjw.wst.domain.ExamCriticalLogic;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExamCriticalClassModel;
import com.hjw.wst.service.DiseaseKnowloedgeService;
import com.hjw.wst.service.ExamCriticalClasslService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  危急值类别维护
     * @author: zr     
     * @date:   2019年7月12日 上午11:23:45   
     * @version V2.0.0.0
 */
public class ExamCriticalClassAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 5542846447345614848L;
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private ExamCriticalClassModel model = new ExamCriticalClassModel();
	private int page=1;
	private int pageSize=15;
	private ExamCriticalClasslService examCriticalClasslService;
	private long iid;
	private DiseaseKnowloedgeService diseaseKnowloedgeService; 

	
	public DiseaseKnowloedgeService getDiseaseKnowloedgeService() {
		return diseaseKnowloedgeService;
	}

	public void setDiseaseKnowloedgeService(DiseaseKnowloedgeService diseaseKnowloedgeService) {
		this.diseaseKnowloedgeService = diseaseKnowloedgeService;
	}

	public long getIid() {
		return iid;
	}

	public void setIid(long iid) {
		this.iid = iid;
	}

	public ExamCriticalClasslService getExamCriticalClasslService() {
		return examCriticalClasslService;
	}

	public void setExamCriticalClasslService(ExamCriticalClasslService examCriticalClasslService) {
		this.examCriticalClasslService = examCriticalClasslService;
	}

	@Override
	public Object getModel() {
		return model;
	}

	public void setModel(ExamCriticalClassModel model) {
		this.model = model;
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
	/**
	 * 
	     * @Title: save-2040  
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String saveCriticalClass(){
		UserDTO user = (UserDTO) session.get("username");
		ExamCriticalClass entity = new ExamCriticalClass();
		entity.setCreater(user.getUserid());
		entity.setCritical_class_level(model.getCritical_class_level());
		entity.setCritical_class_name(model.getCritical_class_name());
		entity.setParent_id(model.getParent_id());
		entity.setCreate_time(DateUtil.parse());
		entity.setSeq_code(model.getSeq_code());
		entity.setRemark(model.getRemark());
		examCriticalClasslService.save(entity);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2400");//子功能id 必须填写
		sl.setExplain("");//操作说明
		this.outJsonStrResult("保存成功");
		return NONE;
	}
	/**
	 * 
	     * @Title: update-2041  
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateCriticalClass()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExamCriticalClass entity = new ExamCriticalClass();
		entity.setId(model.getId());
		entity.setCreater(user.getUserid());
		entity.setCritical_class_level(model.getCritical_class_level());
		entity.setCritical_class_name(model.getCritical_class_name());
		entity.setParent_id(model.getParent_id());
		entity.setSeq_code(model.getSeq_code());
		entity.setRemark(model.getRemark());
		this.examCriticalClasslService.update(entity);
		this.outJsonStrResult("修改成功");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2401");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 
	     * @Title: remove-2042   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String removeCriticalClass() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExamCriticalClass entity = new ExamCriticalClass();
		entity.setId(model.getId());
		entity.setCritical_class_level(model.getCritical_class_level());
		entity.setParent_id(model.getParent_id());
		this.examCriticalClasslService.remove(entity);
		this.outJsonStrResult("删除成功");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2402");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 大类分页查询-2043
	     * @Title: query   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryCriticalClass() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO pageReturnDTO = this.examCriticalClasslService.queryExamCriticalClass(model, page, pageSize, user);
		this.outJsonResult(pageReturnDTO);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2403");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 
	     * @Title: list-2044   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String criticalClasslist() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.examCriticalClasslService.list(model, user));
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2404");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 危急值类别维护页面-2045
	     * @Title: examCriticalClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examCriticalClassPage()throws WebException{
		return SUCCESS;
	}
	/**
	 * 危急值类别新增页面-2046
	     * @Title: addCriticalClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addexamCriticalClassPage()throws WebException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: addexamCriticalClassPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String editexamCriticalClassPage()throws WebException{
		ExamCriticalClass e = this.examCriticalClasslService.getExamCriticalClassById(model.getId());
		model.setParent_id(e.getParent_id());
		model.setId(e.getId());
		model.setCritical_class_name(e.getCritical_class_name());
		model.setCritical_class_level(e.getCritical_class_level());
		model.setRemark(e.getRemark());
		model.setSeq_code(e.getSeq_code());
		return SUCCESS;
	}
	/***********************************************危急值逻辑维护页面2048************************************
	 * 危急值逻辑维护
	     * @Title: "examCriticalLog"   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examCriticalLog()throws WebException{
		return SUCCESS;
	}
	/**
	 * 危急值逻辑维护新增页面2049
	     * @Title: examCriticalLogicadd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examCriticalLogicadd() throws WebException{
		if(!StringUtil.isEmpty(model.getEcl_id())) {
			ExamCriticalLogic elc = this.examCriticalClasslService.getExamCriticalLogic(model);
			DiseaseKnowloedge disase = this.diseaseKnowloedgeService.getDiseaseKnowloedgeByDisaseNum(elc.getDisease_num());
			if(disase!=null) {
				model.setDisease_name(disase.getDisease_name());
				model.setDisease_type(disase.getDisease_type());
			}
			model.setCritical_class_parent_id(elc.getCritical_class_parent_id());
			model.setEcl_id(elc.getId());
			model.setCritical_suggestion(elc.getCritical_suggestion());
			model.setCritical_class_id(elc.getCritical_class_id());
			model.setCritical_class_level_g(elc.getCritical_class_level());
			model.setDisease_num(elc.getDisease_num());
			model.setAge_min(elc.getAge_min());
			model.setAge_max(elc.getAge_max());
			model.setSex(elc.getSex());
		} else {
			model.setAge_min(1);
			model.setAge_max(100);
		}
		return SUCCESS;
	}
	/**
	 * 保存危急值维护大类子类级别规则2050
	     * @Title: saveExamCriticalLogi   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamCriticalLogic() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String msg = this.examCriticalClasslService.saveExamCriticalLogic(model, user);
		this.outJsonStrResult(msg);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2050");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 类别危急值逻辑列表2051
	     * @Title: queryExamCriticalLogic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryExamCriticalLogic()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(	this.examCriticalClasslService.queryCriticalLogic(model,page,pageSize,user));
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2051");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 类别危急值删除2052
	     * @Title: removeExamCriticalLogic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String removeExamCriticalLogic()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.examCriticalClasslService.removeExamCriticalLogic(model);
		this.outJsonStrResult("删除成功");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2052");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 类别危急值维护启用停用2053
	     * @Title: updateExamCriticalLogicIsAcive   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamCriticalLogicIsAcive()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.examCriticalClasslService.updateExamCriticalLogicIsAcive(model, user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2053");//子功能id 必须填写
		sl.setExplain("");//
		return NONE;
	}
	/**
	 * 类别危急值维护获取条件以及条件值2054
	     * @Title: getExamCriticalLogicItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String	getExamCriticalLogicItemList() throws WebException{
		this.outJsonResult(	this.examCriticalClasslService.getExamCriticalLogicItemList(model));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2054");//子功能id 必须填写
		sl.setExplain("");//
		return NONE;
	}
}
