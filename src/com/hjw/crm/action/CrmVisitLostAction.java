package com.hjw.crm.action;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.CrmPlanTacticsDTO;
import com.hjw.crm.domain.CrmMemberPrivateDoctor;
import com.hjw.crm.domain.CrmPlanTactics;
import com.hjw.crm.domain.CrmPlanTacticsDetail;
import com.hjw.crm.domain.CrmVisitLost;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.model.CrmVisitLostModel;
import com.hjw.crm.service.CrmVisitLostService;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.ExamSummaryService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

public class CrmVisitLostAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private CrmVisitLostModel model=new CrmVisitLostModel();
	private CrmVisitLostService crmVisitLostService;
	private CrmVisitPlanService crmVisitPlanService;
	private ExamSummaryService examSummaryService;
	private CrmVisitRecordService crmVisitRecordService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
    private PersistenceManager persistenceManager;
	@Override
	public Object getModel() {
		return model;
	}
	public CrmVisitLostService getCrmVisitLostService() {
		return crmVisitLostService;
	}
	public void setCrmVisitLostService(CrmVisitLostService crmVisitLostService) {
		this.crmVisitLostService = crmVisitLostService;
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
	public SyslogService getSyslogService() {
		return syslogService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setModel(CrmVisitLostModel model) {
		this.model = model;
	}
	
	public void setCrmVisitRecordService(CrmVisitRecordService crmVisitRecordService) {
		this.crmVisitRecordService = crmVisitRecordService;
	}
	public void setExamSummaryService(ExamSummaryService examSummaryService) {
		this.examSummaryService = examSummaryService;
	}
	public void setCrmVisitPlanService(CrmVisitPlanService crmVisitPlanService) {
		this.crmVisitPlanService = crmVisitPlanService;
	}
	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}
	public String getCrmVisitLostListPage()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getCreate_time()!=null&&this.model.getCreate_time().length()>0){
			this.model.setCreate_time(this.model.getCreate_time());
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm001");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String getCrmVisitLostList()throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto =  this.crmVisitLostService.getCrmVisitLostList(model, user.getUserid(), page, rows);
		this.outJsonResult( dto );
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm154");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String saveCrmVisitLost() throws  WebException,SQLException, ParseException{
		UserDTO user = (UserDTO) session.get("username");
			CrmVisitLost c = new CrmVisitLost();
			c.setArch_num(model.getArch_num());
			c.setExam_num(model.getExam_num());
			c.setCreate_time(new Date());
			c.setDoctor_id(user.getUserid());
			c.setCvr_id(this.model.getCvr_id());
			c.setFlag((long) 0);
			String str=this.crmVisitLostService.addCrmVisitLost(c);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm105");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		if(str=="1"){
			this.outJsonStrResult( "失访记录添加成功" );
		}else{
			this.outJsonStrResult( "失访记录添加失败" );
		}
		return NONE;
	}
	/**
	 * 保存策略信息
	 * addPlanTactics
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	public String savePlanTactics() throws ServiceException, SQLException{
		if(this.model.getTactics_num() != "" &&  this.model.getTactics_num() != null){
			UserDTO user = (UserDTO) session.get("username");
			List<CrmPlanTactics> list =  this.crmVisitLostService.getPlanTacticsByNum(this.model.getTactics_num());
			if(list.size() > 0){
				if(this.model.getTactics_id() > 0){
					CrmPlanTactics crmPlanTactics  = list.get(0);
					crmPlanTactics.setTactics_num(this.model.getTactics_num());
					crmPlanTactics.setTactics_type(this.model.getTactics_type());
					crmPlanTactics.setNotices(this.model.getNotices());
					crmPlanTactics.setRmark(this.model.getRmark());
					crmPlanTactics.setUpdater(user.getUserid());
					crmPlanTactics.setUpdate_date(DateTimeUtil.parse());
					this.message = this.crmVisitLostService.updatePlanTactics(crmPlanTactics);
				}else{
					this.message = "error-编码重复不能新增。。";
				}
			}else{
				CrmPlanTactics crmPlanTactics  = new CrmPlanTactics();
				crmPlanTactics.setTactics_num(this.model.getTactics_num());
				crmPlanTactics.setTactics_type(this.model.getTactics_type());
				crmPlanTactics.setNotices(this.model.getNotices());
				crmPlanTactics.setRmark(this.model.getRmark());
				crmPlanTactics.setCreater(user.getUserid());
				crmPlanTactics.setCreate_date(DateTimeUtil.parse());
				crmPlanTactics.setUpdater(user.getUserid());
				crmPlanTactics.setUpdate_date(DateTimeUtil.parse());
				this.message = this.crmVisitLostService.savePlanTactics(crmPlanTactics);
			}
		}else{
			this.message = "error-编码为空不能新增。。" ;
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	//获取策略编码
	public String getTacticsNum(){
		UserDTO user = (UserDTO) session.get("username");
		String paramNum = GetNumContral.getInstance().getParamNum("sign_num", user.getCenter_num());
		this.outJsonStrResult(paramNum);
		return NONE;
	}
	
	/**获取策略列表
	 * getPlantacTicsList
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	public  String getPlantacTicsList() throws ServiceException, SQLException{
		PageReturnDTO  dto = this.crmVisitLostService.getPlantacTicsList(this.model,page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	/**crm314 保存策略明细信息 
	 * savePlanTacticsDetail
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	public String savePlanTacticsDetail() throws ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getTactics_num() != "" &&  this.model.getTactics_num() != null){
			List<CrmPlanTactics> list =  this.crmVisitLostService.getPlanTacticsByNum(this.model.getTactics_num());
			if(list.size() > 0){
				if(!"".equals(this.model.getTactics_detail_id()) && this.model.getTactics_detail_id() > 0){//修改
					CrmPlanTacticsDetail crmPlan = this.crmVisitLostService.getPlanTacticsDetailById(this.model.getTactics_detail_id());
					crmPlan.setPlan_doctor_id(this.model.getPlan_doctor_id());
					crmPlan.setNotices(this.model.getNotices());
					crmPlan.setDistancedate(this.model.getDistancedate());
					crmPlan.setUpdater(user.getUserid());
					crmPlan.setUpdate_date(DateTimeUtil.parse());
					this.message = this.crmVisitLostService.updatePlanTacticsDetail(crmPlan);
				}else{//新增
					CrmPlanTacticsDetail crmPlanTacticsDetail = new CrmPlanTacticsDetail();
					crmPlanTacticsDetail.setTactics_num(this.model.getTactics_num());
					crmPlanTacticsDetail.setPlan_doctor_id(this.model.getPlan_doctor_id());
					crmPlanTacticsDetail.setNotices(this.model.getNotices());
					crmPlanTacticsDetail.setDistancedate(this.model.getDistancedate());
					crmPlanTacticsDetail.setCreater(user.getUserid());
					crmPlanTacticsDetail.setCreate_date(DateTimeUtil.parse());
					crmPlanTacticsDetail.setUpdater(user.getUserid());
					crmPlanTacticsDetail.setUpdate_date(DateTimeUtil.parse());
					this.message = this.crmVisitLostService.savePlanTacticsDetail(crmPlanTacticsDetail);
				}
				
			}else{
				this.message = "error-策略编码不存在。。" ;
			}
		}else{
			this.message = "error-策略编码为空不能新增。。" ;
		}
		this.outJsonStrResult(this.message);
		return NONE;
	} 
	/**getPlantacTicsDetailList
	 * crm315 策略明细信息列表
	 * @throws SQLException 
	 */
	public String getPlantacTicsDetailList() throws SQLException{
		PageReturnDTO dto = new PageReturnDTO();
		if("".equals(this.model.getTactics_num()) ||this.model.getTactics_num() == null){
			this.outJsonResult(dto);
		}else{
			dto = this.crmVisitLostService.getPlantacTicsDetailList(this.model,page, rows);
			this.outJsonResult(dto);
		}
		return NONE;
	}
	/**crm316 策略明细信息删除 
	 * delPlanTacticsDetail
	 * @throws SQLException 
	 */
	public String delPlanTacticsDetail() throws SQLException{
		this.message = this.crmVisitLostService.delPlanTacticsDetail(this.model.getTactics_detail_ids());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**crm317 策略删除 
	 * delPlanTactics
	 * @throws SQLException 
	 */
	public String delPlanTactics() throws SQLException{
		this.message = this.crmVisitLostService.delPlanTactics(this.model.getTactics_nums());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**crm318 查询存在的策略状态
	 * getCrmVisitPlanTacticsType
	 * @throws SQLException 
	 */
	public  String getCrmVisitPlanTacticsType() throws SQLException{
		List<CrmPlanTacticsDTO> list = this.crmVisitLostService.getCrmVisitPlanTacticsType();
		this.outJsonResult(list);
		return NONE;
	}
	
	/**crm319 策略状态查询所有的策略
	 * getTacticsNoticesList
	 * @throws SQLException 
	 */
	public String getTacticsNoticesList() throws SQLException{
		List<CrmPlanTacticsDTO> list = this.crmVisitLostService.getTacticsNoticesList(this.model.getTactics_type());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**查询策略
	 * getPlanTacticsByNum
	 * @throws SQLException 
	 * @throws ServiceException 
	 */
	public String getPlanTacticsByNum() throws ServiceException, SQLException{
		List<CrmPlanTactics> list =this.crmVisitLostService.getPlanTacticsByNum(this.model.getTactics_num());
		CrmPlanTactics crmPlanTactics = new CrmPlanTactics();
		if(list.size() > 0){
			crmPlanTactics = list.get(0);
		}
		this.outJsonResult(crmPlanTactics);
		return NONE;
	}
	
    /** crm321 添加健康计划
     * addCrmVisitPlanList
     * @throws ParseException 
     */
	public String  addCrmVisitPlanList()throws Exception{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray list = JSONArray.fromObject(this.model.getTacticsDetailList());
		List<CrmPlanTacticsDetail> listdetail = (List<CrmPlanTacticsDetail>) JSONArray.toCollection(list,CrmPlanTacticsDetail.class);
		
		String visit_num = GetNumContral.getInstance().getParamNum("visit_num", user.getCenter_num());
		if(listdetail.size() > 0){
			CrmVisitPlan crmVisitPlan = new CrmVisitPlan();//计划主表
			crmVisitPlan.setArch_num(model.getArch_num());
			crmVisitPlan.setExam_num(model.getExam_num());
			crmVisitPlan.setCreate_time(DateTimeUtil.parse());
			crmVisitPlan.setCreater(user.getUserid());
			crmVisitPlan.setVisit_status(model.getVisit_status());
			crmVisitPlan.setTactics_num(model.getTactics_num());
			crmVisitPlan.setVisit_num(visit_num);
			crmVisitPlan.setPlan_doctor_id(0L);
			crmVisitPlan.setPlan_visit_date(new Date());
			crmVisitPlan.setVisit_content("");
			crmVisitPlan.setVisit_important(this.model.getVisit_important());
			this.crmVisitPlanService.addCrmVisitPlan(crmVisitPlan);
			ExamInfo examInfo = this.examSummaryService.getExamInfoByExamNum(this.model.getExam_num());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date  currdate = format.parse(examInfo.getJoin_date());
			for (CrmPlanTacticsDetail crm : listdetail) {
				Calendar ca = Calendar.getInstance();
				ca.setTime(currdate);
		         ca.add(Calendar.DATE, crm.getDistancedate());// num为增加的天数，可以改变的
		         currdate = ca.getTime();
		         
				CrmVisitRecord crmVisitRecord = new CrmVisitRecord();
				crmVisitRecord.setArch_num(this.model.getArch_num());
				crmVisitRecord.setExam_num(this.model.getExam_num());
				crmVisitRecord.setVisit_num(visit_num);
				crmVisitRecord.setVisit_type("");
				crmVisitRecord.setVisit_notices(crm.getNotices());
				crmVisitRecord.setVisit_doctor_id(crm.getPlan_doctor_id());
				crmVisitRecord.setVisit_date(currdate);
				crmVisitRecord.setTactics_detail_id(crm.getId());
				crmVisitRecord.setHealth_suggest("");
				crmVisitRecord.setCustomer_feedback("");
				crmVisitRecord.setRecord_status("0");
				this.crmVisitRecordService.addCrmVisitRecord(crmVisitRecord);
			}
		}
		this.message= "ok-保存成功。。。。。。";
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	/**delVisitRecordById
	 * 撤销健康计划
	     * @Title: delVisitRecordById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String  delVisitRecordById()throws Exception{
		this.message = "error-操作错误!";
		if(!"".equals(this.model.getCvr_id()) && this.model.getCvr_id() != null){
			CrmVisitRecord crmVisitRecord = this.crmVisitRecordService.getCrmVisitRecord(this.model.getCvr_id());
			if(crmVisitRecord != null && "0".equals(crmVisitRecord.getRecord_status())){
				this.crmVisitRecordService.deleteCrmVisitRecord("'"+crmVisitRecord.getId()+"'");
				List<CrmVisitRecord> list = this.crmVisitRecordService.getCrmVisitRecordListByVisitNum(crmVisitRecord.getVisit_num());
				if(list.size() < 1 ){
					this.crmVisitPlanService.deleteCrmVisitPlanByNum(crmVisitRecord.getVisit_num());
				}
				this.message = "ok-删除计划成功。。。";
			}
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * @throws SQLException 
	 * @throws ServiceException "getVisitDoctorList"
	 * crm323查询所有回访医生
	     * @Title: getVisitDoctorList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public  String  getVisitDoctorList() throws ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		 List<UserDTO> list = this.crmVisitPlanService.getVisitDoctorList();
		 user.setId(0);
		 user.setUsername("不选择");
		 list.add(0,user);
		this.outJsonResult(list);
		return NONE;
	}
}
