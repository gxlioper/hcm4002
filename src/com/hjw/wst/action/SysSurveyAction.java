package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SurveyExamInfoDTO;
import com.hjw.wst.DTO.SysQuestionItemsDTO;
import com.hjw.wst.DTO.SysQuestionOptionsDTO;
import com.hjw.wst.DTO.SysSurveyOptionsListDTO;
import com.hjw.wst.DTO.SysSurveyQuestionDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SysSurveyQuestionModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SysSurveyService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  问卷
     * @author: zr     
     * @date:   2017年1月18日 上午9:56:02   
     * @version V2.0.0.0
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class SysSurveyAction  extends BaseAction implements ModelDriven{
	private  SysSurveyQuestionModel  model = new  SysSurveyQuestionModel();
	private  SysSurveyService   sysSurveyService;
	private  int page=1;
	private  int rows=15;
	private  String ss_code;
	private  String ss_name;
	private  String ids;
	private  String s_num;
	private  String exam_num="";
	private String quest_sub_code="QS00";
	private SyslogService syslogService;    
    private CustomerInfoService customerInfoService;   
    
   	public String getExam_num() {
		return exam_num;
	}

	public void setExam_num(String exam_num) {
		this.exam_num = exam_num;
	}

	public String getQuest_sub_code() {
		return quest_sub_code;
	}

	public void setQuest_sub_code(String quest_sub_code) {
		this.quest_sub_code = quest_sub_code;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	

	public void setS_num(String s_num) {
		this.s_num = s_num;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getSs_code() {
		return ss_code;
	}

	public void setSs_code(String ss_code) {
		this.ss_code = ss_code;
	}

	public String getSs_name() {
		return ss_name;
	}

	public void setSs_name(String ss_name) {
		this.ss_name = ss_name;
	}

	@Override
	public Object getModel() {
		return model;
	}

	public void setModel(SysSurveyQuestionModel model) {
		this.model = model;
	}

	public void setSysSurveyService(SysSurveyService sysSurveyService) {
		this.sysSurveyService = sysSurveyService;
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

	/**
	 * 
	     * @Title: getSysSurveyPage   
	     * @Description: TODO(问卷管理页面问题565)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSysSurveyPage()   throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("565");//子功能id 必须填写
		sl.setExplain("问卷问题管理页面");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getSysSurveyList   
	     * @Description: TODO(问卷问题列表566)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSysSurveyList()  throws WebException, SQLException{
		PageReturnDTO  dto = this.sysSurveyService.getSysSurveyQuestionList(model, page, rows);
			this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: addSysSurveyPage   
	     * @Description: TODO(问卷问题新增页面567)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addSysSurveyPage()   throws  WebException, SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: addSysSurveyChargItem   
	     * @Description: TODO(问卷添加收费项目页面568)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  addSysSurveyChargItem() throws  WebException, SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getSysSurveyChargItem   
	     * @Description: TODO(问卷收费项目列表569)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getSysSurveyChargItem() throws  WebException, SQLException{
		PageReturnDTO	dto = this.sysSurveyService.getSysSurveyChargItemList(ss_code, ss_name, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: getziTiMuPage   
	     * @Description: TODO(添加子题目页面570)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getziTiMuPage() throws  WebException, SQLException{
		return  SUCCESS;
	}
	/**
	 * 
	     * @Title: getzi_chagr_item   
	     * @Description: TODO(子题目推荐项目571)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getzi_chagr_item()throws  WebException, SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: addSysSurvey   
	     * @Description: TODO(问卷问题保存572)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  addSysSurvey()  throws  WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
			this.message="";
			if( model.getObjectId() > 0){
				this.sysSurveyService.updateSysSurvey(model, user);
				this.message = "修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("572");//子功能id 必须填写
				sl.setExplain("修改问题问卷");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				this.sysSurveyService.addSysSurvey(model, user);
				this.message = "保存成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("572");//子功能id 必须填写
				sl.setExplain("新增问题问卷");//操作说明
				syslogService.saveSysLog(sl);
			}
			this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getSysSurveyQuestionDTO   
	     * @Description: TODO(问题修改页面573)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getSysSurveyQuestionDTO()  throws  WebException, SQLException{
		SysSurveyQuestionDTO  dto =	this.sysSurveyService.getSysSurveyQuestionDTO(model);
		model.setObjectId( dto.getObjectId() );
		model.setCode( dto.getCode() );
		model.setName( dto.getName() );
		model.setQust_type_id(dto.getQust_type_id());
		model.setSex( dto.getSex() );
		model.setAge( dto.getAge() );
		model.setAge_to( dto.getAge_to() );
		model.setMarriageState( dto.getMarriageState() );
		model.setAnswer_type( dto.getAnswer_type() );
		model.setDep_homepage_show(dto.getDep_homepage_show());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getSysQuestionOptions   
	     * @Description: TODO(修改页面获取问题选项获取数据574)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String  getSysQuestionOptions()  throws  WebException, SQLException{
		List<SysQuestionOptionsDTO>  li = this.sysSurveyService.getSysQuestionItemsDTO(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: getSysQuestionItemsDTOList   
	     * @Description: TODO(修改页面获取问题项目数据575)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String getSysQuestionItemsDTOList()  throws  WebException, SQLException{
		List<SysQuestionItemsDTO>  li  = this.sysSurveyService.getSysQuestionItemsDTOList(ids);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: deleteSysSurvey   
	     * @Description: TODO(删除问卷问题582)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String   deleteSysSurvey() throws  WebException, SQLException{
			UserDTO user = (UserDTO) session.get("username");
			this.sysSurveyService.deleteSysSurvey(ids,user);
			this.message = "删除成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("582");//子功能id 必须填写
			sl.setExplain("删除问卷问题");//操作说明
			syslogService.saveSysLog(sl);
			this.outJsonStrResult( this.message );
		return NONE;
	}
	/**
	 * 
	     * @Title:  getQuestionnaireSurveyPage
	     * @Description: TODO(问卷调查页面577)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String   getQuestionnaireSurveyPage() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("577");//子功能id 必须填写
		sl.setExplain("问卷调查页面");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getQuestionnaireSurveyPageShow   
	     * @Description: TODO(问卷调查验证体检号是否有效578)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String   getQuestionnaireSurveyPageShow()  throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SurveyExamInfoDTO  dto = this.sysSurveyService.getSurveyExamInfoDTO(s_num,user.getCenter_num());
		if( dto!=null ){
//			this.model.setS_user_name( dto.getUser_name() );
//			this.model.setS_age( dto.getAge() );
//			this.model.setS_sex( dto.getSex() );
//			this.model.setS_com_name( dto.getCom_name() );
//			this.model.setS_data_name( dto.getData_name() );
//			this.model.setS_phone( dto.getPhone() );
//			this.model.setS_set_name( dto.getSet_name() );
			this.message = "youxiao";
		}else{
			this.message = "wuxiao";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getQuestionnaireSurveyPageYM   
	     * @Description: TODO(问卷问题页面579)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getQuestionnaireSurveyPageYM() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SurveyExamInfoDTO  dto = this.sysSurveyService.getSurveyExamInfoDTO(s_num,user.getCenter_num());
		this.model.setS_exam_num( dto.getExam_num() );
		this.model.setS_user_name( dto.getUser_name() );
		this.model.setS_age( dto.getAge() );
		this.model.setS_sex( dto.getSex() );
		this.model.setS_com_name( dto.getCom_name() );
		this.model.setS_data_name( dto.getData_name() );
		this.model.setS_phone( dto.getPhone() );
		this.model.setS_set_name( dto.getSet_name() );
		this.model.setCustomer_id( dto.getId()+"");
		String IS_QUEST_SURVEYPAGE="1";
		try{
			IS_QUEST_SURVEYPAGE = this.customerInfoService.getCenterconfigByKey("IS_QUEST_SURVEYPAGE", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		if("1".equals(IS_QUEST_SURVEYPAGE)) {			
			return SUCCESS;
		}else if("2".equals(IS_QUEST_SURVEYPAGE)) {
			this.quest_sub_code="QS00";
			this.exam_num=this.model.getS_exam_num();
			return "successother";
		}else{
			return SUCCESS;
		}		
	}
	/**
	 * 
	     * @Title: getQuestionnaireSurveyPageYMShou   
	     * @Description: TODO(问卷问题显示580)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  getQuestionnaireSurveyPageYMShou() throws WebException, SQLException{
		SysSurveyOptionsListDTO  dto = this.sysSurveyService.getSysSurveyList(model);
		this.outJsonResult( dto );
		return NONE;
	}
	/**
	 * 
	     * @Title: saveQuestionnaireSurvey   
	     * @Description: TODO(提交问卷581)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  saveQuestionnaireSurvey()throws WebException, SQLException{
			UserDTO user = (UserDTO) session.get("username");
				this.sysSurveyService.saveQuestionnaireSurvey(model, user);
				this.message="提交成功";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("581");//子功能id 必须填写
				sl.setExplain("提交问卷");//操作说明
				syslogService.saveSysLog(sl);
				
				this.outJsonStrResult(this.message);
		return  NONE;
	}
}
