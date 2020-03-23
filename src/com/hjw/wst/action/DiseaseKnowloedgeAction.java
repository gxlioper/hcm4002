package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.hjw.wst.domain.DiseaseDep;
import com.hjw.wst.domain.DiseaseKnowloedge;
import com.hjw.wst.domain.DiseaseSuggestion;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DiseaseKnowloedgeModel;
import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DiseaseKnowloedgeService;
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
public class DiseaseKnowloedgeAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private DiseaseKnowloedgeModel model = new DiseaseKnowloedgeModel();
	private DiseaseKnowloedgeService diseaseKnowloedgeService;
	private CustomerInfoService customerInfoService;
	private String ids;//批量删除ID
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SyslogService syslogService;    
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

	public DiseaseKnowloedgeModel getModel() {
		return model;
	}

	public void setModel(DiseaseKnowloedgeModel model) {
		this.model = model;
	}

	public DiseaseKnowloedgeService getDiseaseKnowloedgeService() {
		return diseaseKnowloedgeService;
	}

	public void setDiseaseKnowloedgeService(
			DiseaseKnowloedgeService diseaseKnowloedgeService) {
		this.diseaseKnowloedgeService = diseaseKnowloedgeService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	/**
	 * 疾病知识库管理界面
	     * @Title: getDiseaseKnowloedgeShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseKnowloedgeShow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("134");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: diseaseKnowloedge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseKnowloedge() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("149");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	//查看页面
	public String lookDKLdgelibshow() throws WebException, SQLException {
		DiseaseKnowloedgeDTO dklg = this.diseaseKnowloedgeService.loadDkloaddept(this.model.getId());
		if(dklg != null){
			this.model.setDisease_type(dklg.getDisease_type());
			this.model.setDisease_level(dklg.getDisease_level());
			this.model.setDisease_name(dklg.getDisease_name());
			this.model.setDisease_pinyin(dklg.getDisease_pinyin());
			this.model.setDisease_num(dklg.getDisease_num());
			this.model.setIcd_10(dklg.getIcd_10());
			this.model.setDisease_description(dklg.getDisease_description());
			this.model.setDisease_evendice(dklg.getDisease_evendice());
			this.model.setDisease_suggestion(dklg.getDisease_suggestion());
			this.model.setDisease_classification_d(dklg.getDisease_classification_d());
			this.model.setDisease_type_d(dklg.getDisease_type_d());
			this.model.setDisease_level_d(dklg.getDisease_level_d());
			this.model.setDep_id(dklg.getDep_id());
			this.model.setId(dklg.getId());
			this.model.setDep_name(dklg.getDep_name());
			this.model.setDisease_system(dklg.getDisease_system());
			this.model.setDisease_systems(dklg.getDisease_systems());
			this.model.setDisease_statistics(dklg.getDisease_statistics());
			this.model.setDisease_statisticss(dklg.getDisease_statisticss());
			this.model.setDisease_report(dklg.getDisease_report());
			this.model.setDisease_reports(dklg.getDisease_reports());
			this.model.setDisease_team_show(dklg.getDisease_team_show());
			this.model.setDisease_team_shows(dklg.getDisease_team_shows());
		}
		return SUCCESS;
	}
	
	
	
	/**
	 * 疾病信息列表
	     * @Title: diseaseKnowloedgeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.diseaseKnowloedgeService.queryPageDklodegDep(this.model.getDep_id(),this.model.getDisease_type(), 
				this.model.getDisease_name(), this.model.getDisease_level(),this.model.getIs_active(), this.rows, this.getPage(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 专家建议查询
	     * @Title: lookDKLdgelib   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String lookDKLdgelib()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_DISEASE_SUG_CENTER = "N";
		try {
			IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		List<DiseaseKnowloedgeDTO> list = this.diseaseKnowloedgeService.getSuggtionByDiseaseid(this.model.getId(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
		this.outJsonResult(list);
		return NONE;
	} 
	
	
	/**
	 * 编辑
	     * @Title: updaterDKLdge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateDKLdge() throws WebException{
		DiseaseKnowloedge dklg=this.diseaseKnowloedgeService.loadDklodeg(this.model.getId());
		this.model.setDisease_type(dklg.getDisease_type());
		this.model.setDisease_level(dklg.getDisease_level());
		this.model.setDisease_name(dklg.getDisease_name());
		this.model.setDisease_pinyin(dklg.getDisease_pinyin());
		this.model.setDisease_num(dklg.getDisease_num());
		this.model.setIcd_10(dklg.getIcd_10());
		this.model.setIsActive(dklg.getIsActive());
		this.model.setDisease_description(dklg.getDisease_description());
		this.model.setDisease_evendice(dklg.getDisease_evendice());
		this.model.setDisease_suggestion(dklg.getDisease_suggestion());
		this.model.setDisease_classification(dklg.getDisease_classification());
		this.model.setDep_id(dklg.getDep_id());
		this.model.setDisease_system(dklg.getDisease_system());
		this.model.setDisease_statistics(dklg.getDisease_statistics());
		this.model.setDisease_report(dklg.getDisease_report());
		this.model.setDisease_team_show(dklg.getDisease_team_show());
		return SUCCESS;
}
	/**
	 * 批量删除
	     * @Title: deleteDklodeg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteDklodeg() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.diseaseKnowloedgeService.deleteDklodeg(this.ids);
		this.message="删除成功";
		this.outJsonStrResult(message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("140");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	/**
	 * 添加
	     * @Title: addDKLdge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addDKLdge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setDisease_num(GetNumContral.getInstance().getParamNum("disease_no", user.getCenter_num()));
		return SUCCESS;
	}
	
	
	public String saveDiseaseKLg() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			DiseaseKnowloedge dklg=this.diseaseKnowloedgeService.loadDklodeg(this.model.getId());
			dklg.setUpdater(user.getUserid());
			dklg.setUpdate_time(DateTimeUtil.parse());
			dklg.setDisease_type(model.getDisease_type());
			dklg.setDisease_level(model.getDisease_level());
			dklg.setDisease_name(model.getDisease_name());
			dklg.setDisease_pinyin(model.getDisease_pinyin());
			dklg.setDisease_num(model.getDisease_num());
			dklg.setIcd_10(model.getIcd_10());
			dklg.setIsActive("Y");
			dklg.setDisease_description(model.getDisease_description());
			dklg.setDisease_evendice(model.getDisease_evendice());
			dklg.setDisease_suggestion(model.getDisease_suggestion());
			dklg.setHealth_suggestion("");
			dklg.setDisease_classification(model.getDisease_classification());
			dklg.setDep_id(model.getDep_id());
			dklg.setDisease_system(model.getDisease_system());
			dklg.setDisease_statistics(model.getDisease_statistics());
			dklg.setDisease_report(model.getDisease_report());
			dklg.setDisease_team_show(model.getDisease_team_show());
			this.diseaseKnowloedgeService.updateDklodeg(dklg);
			this.message="编辑成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("138");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			DiseaseKnowloedge dklg = new DiseaseKnowloedge();
			dklg.setCreater(user.getUserid());
			dklg.setCreate_time(DateTimeUtil.parse());
			dklg.setUpdater(user.getUserid());
			dklg.setUpdate_time(DateTimeUtil.parse());
			dklg.setDisease_type(model.getDisease_type());
			dklg.setDisease_level(model.getDisease_level());
			dklg.setDisease_name(model.getDisease_name());
			dklg.setDisease_pinyin(model.getDisease_pinyin());
			dklg.setDisease_num(model.getDisease_num());
			dklg.setIcd_10(model.getIcd_10());
			dklg.setIsActive("Y");
			dklg.setDisease_description(model.getDisease_description());
			dklg.setDisease_evendice(model.getDisease_evendice());
			dklg.setDisease_suggestion(model.getDisease_suggestion());
			dklg.setHealth_suggestion("");
			dklg.setDisease_classification(model.getDisease_classification());
			dklg.setDep_id(model.getDep_id());
			dklg.setDisease_system(model.getDisease_system());
			dklg.setDisease_statistics(model.getDisease_statistics());
			dklg.setDisease_report(model.getDisease_report());
			dklg.setDisease_team_show(model.getDisease_team_show());
			this.diseaseKnowloedgeService.addDklodeg(dklg);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("138");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	/**
	 * 疾病信息列表
	     * @Title: diseaseKnowloedgeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getsuggestionList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String IS_DISEASE_SUG_CENTER = "N";
		try {
			IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		PageReturnDTO list = this.diseaseKnowloedgeService.queryallSuggestion(this.model.getDisease_id(),this.rows, this.getPage(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 建议表更新
	     * @Title: updateSuggestion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateSuggestion() throws WebException{
		DiseaseSuggestion dst=this.diseaseKnowloedgeService.loadSuggestion(this.model.getSug_id());
		DiseaseKnowloedgeDTO dklg = this.diseaseKnowloedgeService.loadDkloaddept(dst.getDisease_id());
		this.model.setDisease_name(dklg.getDisease_name());
		this.model.setSex(dst.getSex());
		this.model.setMinAge(dst.getMinAge());
		this.model.setMaxAge(dst.getMaxAge());
		this.model.setDisease_suggestion(dst.getDisease_suggestion());
		this.model.setIs_active(dst.getIs_active());
		this.model.setDefault_value(dst.getDefault_value());
		this.model.setDisease_id(dst.getDisease_id());
		this.model.setDisease_num(dst.getDisease_num());
		return SUCCESS;
}
	/**
	 * 建议表添加
	     * @Title: addSuggestion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addSuggestion() throws WebException{
		DiseaseKnowloedgeDTO dklg = this.diseaseKnowloedgeService.loadDkloaddept(this.model.getDisease_id());
		if(dklg != null){
			this.model.setDisease_num(dklg.getDisease_num());
			this.model.setDisease_name(dklg.getDisease_name());
		}
		this.model.setMinAge(1);
		this.model.setMaxAge(200);
		return SUCCESS;
	}
	
	/**
	 * 建议表删除
	     * @Title: deleteSuggestion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteSuggestion() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		DiseaseSuggestion dst=this.diseaseKnowloedgeService.loadSuggestion(this.model.getSug_id());
		this.diseaseKnowloedgeService.deleteDSugge(dst);
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("143");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String saveSuggestion() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if("Y".equals(model.getDefault_value())){
			String IS_DISEASE_SUG_CENTER = "N";
			try {
				IS_DISEASE_SUG_CENTER = this.customerInfoService.getCenterconfigByKey("IS_DISEASE_SUG_CENTER", user.getCenter_num()).getConfig_value().trim();
			} catch (Exception e) {
			}
			List<DiseaseSuggestion> list = this.diseaseKnowloedgeService.getSuggestionList(model.getDisease_id(),user.getCenter_num(),IS_DISEASE_SUG_CENTER);
			for(DiseaseSuggestion diseaseSuggestion : list){
				diseaseSuggestion.setDefault_value("N");
				this.diseaseKnowloedgeService.updateSug(diseaseSuggestion);
			}
		}
		
		if(this.model.getSug_id() > 0){
			DiseaseSuggestion dst=this.diseaseKnowloedgeService.loadSuggestion(this.model.getSug_id());
			dst.setSex(model.getSex());
			dst.setMinAge(model.getMinAge());
			dst.setMaxAge(model.getMaxAge());
			dst.setIs_active("Y");
			dst.setDefault_value(model.getDefault_value());
			dst.setDisease_suggestion(model.getDisease_suggestion());
			dst.setDisease_id(model.getDisease_id());
			dst.setDisease_num(model.getDisease_num());
			dst.setCenter_num(user.getCenter_num());
			this.diseaseKnowloedgeService.updateSug(dst);
			this.message="编辑成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("144");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			DiseaseSuggestion dst = new DiseaseSuggestion();
			dst.setSex(model.getSex());
			dst.setMinAge(model.getMinAge());
			dst.setMaxAge(model.getMaxAge());
			dst.setIs_active("Y");
			dst.setDefault_value(model.getDefault_value());
			dst.setDisease_suggestion(model.getDisease_suggestion());
			dst.setDisease_id(model.getDisease_id());
			dst.setDisease_num(model.getDisease_num());
			dst.setCenter_num(user.getCenter_num());
			this.diseaseKnowloedgeService.addSug(dst);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("144");//子功能id 必须填写
			sl.setExplain("插入");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 健康建议启停
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String diseaseSuggestionOffOn() throws WebException, Exception {
		Language.setLanguage(this.language);
		try {
			DiseaseSuggestion dst=this.diseaseKnowloedgeService.loadSuggestion(this.model.getSug_id());
			if(dst.getIs_active().equals("Y"))
				dst.setIs_active("N");
			else if(dst.getIs_active().equals("N"))
				dst.setIs_active("Y");
			this.diseaseKnowloedgeService.updateSug(dst);
			this.message="success";
		} catch (Exception e) {
			this.message="error";
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("148");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 疾病信息启停功能
	     * @Title: diseaseLibOffOn   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String diseaseLibOffOn() throws WebException, Exception {
		DiseaseKnowloedge dklg = this.diseaseKnowloedgeService.loadDklodeg(this.model.getId());
		dklg.setIsActive(this.model.getIs_active());
		this.diseaseKnowloedgeService.updateDklodeg(dklg);
		if(this.model.getIs_active().equals("Y")){
			this.message = "ok-疾病信息启用成功!";
		}else{
			this.message = "ok-疾病信息停用成功!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getCriticalClass   
	     * @Description: TODO(获取危急值大类名称)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalClassD()throws WebException, Exception {
		List<CriticalDTO> l=this.diseaseKnowloedgeService.getcriticalD();
		this.outJsonResult(l);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2303");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getCriticalClass   
	     * @Description: TODO(获取危急值子类名称)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalClass()throws WebException, Exception {
		
		List<CriticalDTO> l=this.diseaseKnowloedgeService.getcritical(this.model.getParentId());
		this.outJsonResult(l);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2310");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getCriticalLevel   
	     * @Description: TODO(获取危急值等级)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalLevel()throws WebException, Exception {
		List<CriticalDTO> l=this.diseaseKnowloedgeService.getcriticalLevel();
		this.outJsonResult(l);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2304");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getDisease   
	     * @Description: TODO(获取疾病)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getDisease()throws WebException, Exception {
		List<CriticalDTO> l=this.diseaseKnowloedgeService.getDisease();
		this.outJsonResult(l);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2308");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String huoqudepname()throws WebException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		List<DiseaseKnowloedgeDTO> l=this.diseaseKnowloedgeService.getdepname(user.getCenter_num());
		this.outJsonResult(l);
		return NONE;
	}
	public String crmSmsDiseaseList() throws WebException{
		String disease_name=this.model.getDisease_name();
		List<DiseaseKnowloedgeDTO> list  = this.diseaseKnowloedgeService.queryDiseaseList(disease_name);
		this.outJsonResult(list);
		return NONE;
	}
}