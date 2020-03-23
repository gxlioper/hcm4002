package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.DiseaseLogicExamItemDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.DiseaseLogicModel;
import com.hjw.wst.service.DiseaseDepService;
import com.hjw.wst.service.DiseaseLogicService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:疾病逻辑管理  
     * @author: zr     
     * @date:   2016年12月8日 上午9:50:08   
     * @version V2.0.0.0
 */
@SuppressWarnings("serial")
public class DiseaseLogicAction extends BaseAction implements ModelDriven{
	private DiseaseLogicModel model = new DiseaseLogicModel();
	private DiseaseLogicService diseaseLogicService;
	private int page=1;
	private int rows=15;
	private SyslogService syslogService;  
	private DiseaseDepService  diseaseDepService;
	private String s="";
	
	
	
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public DiseaseDepService getDiseaseDepService() {
		return diseaseDepService;
	}

	public void setDiseaseDepService(DiseaseDepService diseaseDepService) {
		this.diseaseDepService = diseaseDepService;
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
	public DiseaseLogicService getdiseaseLogicService() {
		return diseaseLogicService;
	}
	public void setdiseaseLogicService(DiseaseLogicService diseaseLogicService) {
		this.diseaseLogicService = diseaseLogicService;
	}
	@Override
	public Object getModel() {
		return model;
	}
	public void setModel(DiseaseLogicModel model) {
		this.model = model;
	}
	/**
	 * 
	     * @Title: getDiseaseLogicPage   
	     * @Description: TODO(疾病逻辑管理页面536)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicPage() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("536");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getDiseaseLogic   
	     * @Description: TODO(疾病逻辑列表537)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogic() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto =	this.diseaseLogicService.queryDiseaseLogic(model, page,this.rows,user);
		this.outJsonResult(dto);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("537");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: getDiseaseLogic_all   
	     * @Description: TODO 疾病逻辑列表1347（包含已停用的）   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogic_all() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto =	this.diseaseLogicService.queryDiseaseLogic_all(model, page,this.rows,user);
		this.outJsonResult(dto);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1347");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: deletDiseaseLogic   
	     * @Description: TODO(删除疾病逻辑538)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deletDiseaseLogic()throws WebException, SQLException{
		this.diseaseLogicService.deletDiseaseLogic(model.getIds());
		this.message="删除成功！";
		this.outJsonStrResult(message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("538");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String update_diseaseLogic_off() throws WebException, Exception {
		try {
			this.diseaseLogicService.update_diseaseLogic_off(model.getIds());
			this.message="success";
		} catch (Exception e) {
			this.message="error";
			e.printStackTrace();
		}
	
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1345");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String update_diseaseLogic_on() throws WebException, Exception {
		try {
			this.diseaseLogicService.update_diseaseLogic_on(model.getIds());
			this.message="success";
		} catch (Exception e) {
			this.message="error";
			e.printStackTrace();
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1346");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: deletDiseaseLogic   
	     * @Description: TODO(疾病逻辑新增页面539)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addDiseaseLogicPage()throws WebException, SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: get   
	     * @Description: TODO(获取所有收费项目540)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseChargingItem()throws WebException, SQLException{
		List<ChargingItemDTO> li =	this.diseaseLogicService.getChargingItem(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: get   
	     * @Description: TODO(获取所有检查项目541)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseExaminationItem()throws WebException, SQLException{
		List<ExaminationItemDTO>  li = this.diseaseLogicService.getExaminationItem(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: getDiseaseKnowloedge   
	     * @Description: TODO(获取所有疾病542)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseKnowloedge() throws WebException, SQLException{
		List<DiseaseLogicDTO> li =	this.diseaseLogicService.getDiseaseKnowloedge(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: addDiseaseKnowloedge   
	     * @Description: TODO(新增疾病逻辑543)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addDiseaseKnowloedge() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
			if(model.getId()>0){
				this.diseaseLogicService.updateDiseaseKnowloedge(model, user);
				this.message = "修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("543");//子功能id 必须填写
				sl.setExplain("修改疾病逻辑");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				this.diseaseLogicService.addDiseaseKnowloedge(model,user);
				this.message = "添加成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("543");//子功能id 必须填写
				sl.setExplain("新增疾病逻辑");//操作说明
				syslogService.saveSysLog(sl);
			}
			this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getDiseaseLogic   
	     * @Description: TODO(修改页面544)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateDiseaseLogicPage() throws WebException, SQLException{
		DiseaseLogicDTO dto = this.diseaseLogicService.getDiseaseLogic(model.getId());
		model.setId(dto.getId());
		model.setLogic_type(dto.getLogic_type());
		model.setSex(dto.getSex());
		model.setAge_max(dto.getAge_max());
		model.setAge_min(dto.getAge_min());
		model.setCritical_flag(dto.getCritical_flag());
		
		model.setDisease_name(dto.getDisease_name());
		model.setDisease_id(dto.getDisease_id());
		
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getDiseaseKnowloedge1   
	     * @Description: TODO(疾病逻辑修改，获取检查项目545)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseKnowloedge1()throws WebException, SQLException{
		List<DiseaseLogicExamItemDTO>  dto  =	this.diseaseLogicService.getDiseaseKnowloedge(model.getId());
		this.outJsonResult(dto);
		return NONE;
	}
	//---------------------------------科室疾病逻辑----------------
	/**
	 * 科室疾病逻辑----594
	     * @Title: getdiseaseDepPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getdiseaseDepPage() throws  WebException,ServiceException{
		UserDTO user = (UserDTO) session.get("username");
		List<WebResrelAtionship> ls = user.getWebResource();
		if( ls!=null){
			for (int i = 0; i < ls.size(); i++) {
				if(ls.get(i).getRes_code().equals("RS008")){
					this.model.setWeb_Resource(ls.get(i).getDatavalue());
					break;
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 获取所有科室----595
	     * @Title: getDepartmentDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getDepartmentDepList() throws  WebException,ServiceException{
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		List<DepartmentDepDTO>   li = this.diseaseDepService.getDepartmentDepDTO(model, user,this.defaultapp.getComid());
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 根据科室过滤疾病----596
	     * @Title: getDepDiseaseKnowloedge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getDepDiseaseKnowloedge()throws  WebException,ServiceException{
		UserDTO user = (UserDTO) session.get("username");
		List<DiseaseLogicDTO>  li   = this.diseaseDepService.getDepDiseaseKnowloedge(model,user.getCenter_num());
		 this.outJsonResult(li);
		return  NONE;
	}
	/**
	 * 科室疾病逻辑新增页面----597
	     * @Title: getDepDiseaseLogic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  getDepDiseaseLogic() throws  WebException,ServiceException{
		return SUCCESS;
	}
	/**
	 * 科室逻辑修改页面
	     * @Title: updateDepDiseaseLogicPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateDepDiseaseLogicPage() throws  WebException,ServiceException{
		DiseaseLogicDTO dto = this.diseaseLogicService.getDiseaseLogic(model.getId());
		model.setId(dto.getId());
		model.setLogic_type(dto.getLogic_type());
		model.setSex(dto.getSex());
		model.setAge_max(dto.getAge_max());
		model.setAge_min(dto.getAge_min());
		model.setCritical_flag(dto.getCritical_flag());
		model.setDisease_name(dto.getDisease_name());
		model.setDisease_id(dto.getDisease_id());
		model.setDep_id(model.getDep_id());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getDepItemDetail   
	     * @Description: TODO(科室 项目 细项关联总页面)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepItemDetail() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1540");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getDepItemCharging   
	     * @Description: TODO(收费项目page)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepItemCharging() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1541");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getDepItemChargingDetail   
	     * @Description: TODO(收费项目细项明细)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDepItemChargingDetail() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1542");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getExamItemAddSave   
	     * @Description: TODO(项目细项的修改)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamItemAddSave() throws WebException, SQLException{
		ChargingItem item = new ChargingItem();
		item.setId(model.getItemId());//收费项目id
		UserDTO user=(UserDTO) session.get("username");//获取用户
		diseaseDepService.updateItemList(item, model.getItemList(),model.getLimitCount(), user);
		this.message="修改成功";
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
