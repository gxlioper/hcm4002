package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.ViewCommonWordsDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.DTO.ViewExamItemDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.ViewExamModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepInspectService;
import com.hjw.wst.service.NewDiseaseLogicService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.ViewExamService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

public class ViewExamAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	
	private ViewExamModel model = new ViewExamModel();
	private ViewExamService viewExamService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	private NewDiseaseLogicService newDiseaseLogicService;
	private DepInspectService depInspectService;
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setDepInspectService(DepInspectService depInspectService) {
		this.depInspectService = depInspectService;
	}
	public void setNewDiseaseLogicService(NewDiseaseLogicService newDiseaseLogicService) {
		this.newDiseaseLogicService = newDiseaseLogicService;
	}
	public void setViewExamService(ViewExamService viewExamService) {
		this.viewExamService = viewExamService;
	}

	public void setModel(ViewExamModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	/**
	 * 影像科室检查结果录入页面  713
	     * @Title: getViewExamPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamPage() throws WebException{
		UserDTO user =(UserDTO) session.get("username");
		this.model.setApp_type("1");
		String isViewExamImageShow = this.customerInfoService.getCenterconfigByKey("IS_VIEW_EXAM_IMAGE_SHOW", user.getCenter_num()).getConfig_value();
		this.model.setIsViewExamImageShow(isViewExamImageShow);
		
		//资源
		List<WebResrelAtionship>  web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS054")){
					this.model.setIs_update_critical(web.get(i).getDatavalue());
					continue;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("713");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询本科需要检查的项目与检查结果 714
	     * @Title: getViewExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamDetail() throws WebException,SQLException{
		UserDTO user = (UserDTO) this.session.get("username");
		long dep_id = user.getDep_id();
		if("comm_all".equals(this.model.getExam_type())){
			user.setDep_id(this.model.getDep_id());
		}
		List<ViewExamDetailDTO> list = this.viewExamService.getViewExamDetail(this.model.getExam_num(), user.getDep_id(), user.getCenter_num(),model.getApp_type());
		this.outJsonResult(list);
		user.setDep_id(dep_id);
		return NONE;
	}
	
	/**
	 * 影像科室首页  715
	     * @Title: getViewExamIndex   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamIndex() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setExam_date1(DateTimeUtil.getDate2());
		this.model.setExam_date2(DateTimeUtil.getDate2());
		this.model.setReport_print_type(this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_report_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		String is_custom_identification = "";
		try {
			is_custom_identification = this.customerInfoService.getCenterconfigByKey("IS_CUSTOM_IDENTIFICATION", user.getCenter_num()).getConfig_value();
		} catch (Exception e) {
			System.out.println("缺少IS_CUSTOM_IDENTIFICATION 配置");
		}
		this.model.setIs_custom_identification(is_custom_identification);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("715");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询影像科室 人员列表 716
	     * @Title: getViewExamIndexList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamIndexList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.viewExamService.getExamInfoList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 保存影像科室检查结果 717
	     * @Title: saveViewExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String saveViewExamDetail() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String userName = user.getName();
		long userId = user.getUserid();
		if("Y".equals(this.model.getExam_type())){ //多科室录入 检查医生收动选择
			user.setName(this.model.getExam_doctor());
			user.setUserid(this.model.getInputter());
			this.model.setInputter(userId);
		}
		JSONArray viewExam = JSONArray.fromObject(this.model.getViewExams());
		List<ViewExamDetailDTO> listviewExam = (List<ViewExamDetailDTO>) JSONArray.toCollection(viewExam,ViewExamDetailDTO.class);
		
		for(int i=0;i< listviewExam.size();i++){
			ViewExamDetailDTO view = listviewExam.get(i);
			JSONArray viewItem = JSONArray.fromObject(viewExam.getJSONObject(i).get("viewItem"));
			List<ViewExamItemDTO> listviewItem = (List<ViewExamItemDTO>) JSONArray.toCollection(viewItem,ViewExamItemDTO.class);
			view.setViewItem(listviewItem);
		}
		this.model.setViewExamList(listviewExam);
		if(user != null){
			DepartmentDepDTO dto = this.viewExamService.getDepartMentDtoByid(model.getViewExamList().get(0).getDep_id());
			ExamInfoDTO examinfo = this.customerInfoService.getCustExamInfoForexamId(model.getViewExamList().get(0).getExam_num());
			
			if(dto.getSeq_code() == 1){
				this.message = this.viewExamService.saveViewExamDetailDepNumjdbc(model, user);
				DepExamResultDTO examResult = new DepExamResultDTO();
				examResult.setDep_id(model.getViewExamList().get(0).getDep_id());
				examResult.setDep_num(model.getViewExamList().get(0).getDep_num());
				examResult.setExam_result(model.getViewExamList().get(0).getExam_result());
				this.newDiseaseLogicService.createDepLogicSingleDiseaseDep(examinfo, examResult,user.getUserid());
			}else{
				this.message = this.viewExamService.saveViewExamDetailjdbc(model, user);
				List<DepExamResultDTO> list = this.viewExamService.getExamResultDTOList(model.getViewExamList().get(0).getPacs_id());
				for (DepExamResultDTO examResult : list) {
					examResult.setDep_id(model.getViewExamList().get(0).getDep_id());
					examResult.setDep_num(model.getViewExamList().get(0).getDep_num());
					examResult.setExam_result(model.getViewExamList().get(0).getExam_result());
					examResult.setResult_type(2);
					
					/*******************自动生成单项阳性词条*****************/
					this.newDiseaseLogicService.createDepLogicSingleDisease(examinfo, examResult,user.getUserid());
					/*******************自动生成危急值*****************/
					this.depInspectService.createExamCritical(examinfo.getExam_num(), examResult.getItem_code(), examResult.getItem_num());
				}
			}
			user.setName(userName);
			user.setUserid(userId);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("717");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.message = "error-请重新登录!";
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 根据样本ID查询影像科室常用词  718
	     * @Title: getViewExamWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamWords() throws WebException,SQLException{
		List<ViewCommonWordsDTO> list = this.viewExamService.getViewExamWords(this.model.getSample_id(),this.model.getExam_result());
		List<ViewCommonWordsDTO> lidto= new ArrayList<ViewCommonWordsDTO>();
		if(list.size()!=0){
			int count = 0;
			ViewCommonWordsDTO dto = null;
			for(int i=0;i<list.size();i++){
				if(count == 0){
					dto = new ViewCommonWordsDTO();
				}
				if(i%2==0){
					dto.setExam_desc(list.get(i).getExam_desc());
					dto.setExam_result(list.get(i).getExam_result());
					count ++;
				}else{
					dto.setExam_descs(list.get(i).getExam_desc());
					dto.setExam_results(list.get(i).getExam_result());
					count ++;
				}
				if(count == 2||i==list.size()-1){
					lidto.add(dto);
					count = 0;
				}
				
			}
		}
		this.outJsonResult(lidto);
		return NONE;
	}
	
	/**
	 * 影像科室获取常用词页面 719
	     * @Title: getViewExamWordsPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getViewExamWordsPage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 影像科室检查结果查看页
	     * @Title: positiveResultsPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String positiveResultsPage()throws WebException{
		return SUCCESS;
	}
	
	public String getAllViewExamList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.viewExamService.getAllViewExamInfoList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	public String imagingResultsDetail() throws WebException,SQLException{
		
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: queryDeptCharingItem   
	     * @Description: TODO(查询体检人员在该科室下是否有体检信息)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryDeptCharingItemMsg() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.viewExamService.queryDeptCharingItemMsg(model, user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
