package com.hjw.wst.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hsqldb.lib.StringUtil;

import com.hjw.config.Logincheck;
import com.hjw.crm.domain.CrmVisitPlan;
import com.hjw.crm.domain.CrmVisitRecord;
import com.hjw.crm.service.CrmVisitPlanService;
import com.hjw.crm.service.CrmVisitRecordService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.model.CriticalModel;
import com.hjw.wst.service.CriticalService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamSummaryService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.Critical;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamCriticalLog;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
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
public class CriticalAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private CriticalModel model = new CriticalModel();
	private CriticalService criticalService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private int id;
	private SyslogService syslogService; 
	private InputStream excelStream;  //输出流变量  
    private String excelFileName; //下载文件名  
    private CrmVisitRecordService crmVisitRecordService;
	private String sort;
	private String order;
    private CustomerInfoService customerInfoService;
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
    private CrmVisitPlanService crmVisitPlanService;


   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getPage() {
		return page;
	}

	public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

	public CriticalService getCriticalService() {
			return criticalService;
		}

		public void setCriticalService(CriticalService criticalService) {
			this.criticalService = criticalService;
		}

	public CriticalModel getModel() {
			return model;
		}

		public void setModel(CriticalModel model) {
			this.model = model;
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
	public InputStream getExcelStream() {
		return excelStream;
	}
	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	
	public void setCrmVisitRecordService(CrmVisitRecordService crmVisitRecordService) {
		this.crmVisitRecordService = crmVisitRecordService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	public void setCrmVisitPlanService(CrmVisitPlanService crmVisitPlanService) {
		this.crmVisitPlanService = crmVisitPlanService;
	}
	
	/**
	 * 危急值页面
	     * @Title: customer_type   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String critical() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		model.setStart_date(DateTimeUtil.getDate2());
		model.setEnd_dta(DateTimeUtil.getDate2());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("616");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	public String updateCri() throws WebException, SQLException {
		if(!StringUtils.isEmpty(model.getIds())) {
			String[] ids = model.getIds().split(",");
			model.setId(Integer.parseInt(ids[0]));
			CriticalDTO c = criticalService.getCritricalExamInfoById(model);
//			this.model.setNote(c.getNote());
//			this.model.setDone_flag(c.getDone_flag());
//			this.model.setGive_notice_type(c.getGive_notice_type());
			this.model.setUser_name(c.getUser_name());
			this.model.setPhone(c.getPhone());
			this.model.setSex(c.getSex());
			this.model.setAge(c.getAge());
			this.model.setId(0);
			this.model.setIds(model.getIds());
			this.model.setExam_num(c.getExam_num());
			return "batch";
		} else {
			CriticalDTO c = criticalService.getCritricalExamInfoById(model);
			this.model.setNote(c.getNote());
			this.model.setDone_flag(c.getDone_flag());
			this.model.setGive_notice_type(c.getGive_notice_type());
			this.model.setUser_name(c.getUser_name());
			this.model.setPhone(c.getPhone());
			this.model.setSex(c.getSex());
			this.model.setAge(c.getAge());
			this.model.setId(c.getId());
			this.model.setExam_num(c.getExam_num());
			return SUCCESS;
		}
	}
	/**
	 * 信息列表
	     * @Title: customer_typeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String criticalList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.criticalService.getCriticalLis(model, rows, this.getPage(),user);
		this.outJsonResult(list);
		return NONE;
	}
	

	public String saveCritical() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String msg = this.criticalService.updateCriticalVisitPlan(model,user);
		this.outJsonStrResult("处理成功！");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("619");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	//手动添加危急值
	public  String addCritical(){
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getItem_id() != 0  && this.model.getExaminfo_id() != 0  && this.model.getDep_category() != null && !"".equals(this.model.getDep_category()) ) {
			this.message = this.criticalService.addCritical(this.model.getExaminfo_id(),this.model.getItem_id(),this.model.getDep_category(),user.getUserid(), user.getCenter_num());
		}else{
			this.message = "error-操作错误";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	//撤销手动添加的危急值
	public  String  delCritical(){
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getItem_id() != 0  && this.model.getExaminfo_id() != 0  && this.model.getDep_category() != null && !"".equals(this.model.getDep_category()) ) {
			this.message = this.criticalService.delCritical(this.model.getExaminfo_id(),this.model.getItem_id(),this.model.getDep_category(),user.getUserid());
		}else{
			this.message = "error-操作错误";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	//查询危急值处理记录
	public String criticalLogList(){
		PageReturnDTO list = this.criticalService.criticalLogList(model, rows, this.getPage());
		this.outJsonResult(list);
		return NONE;
	}
	
	public  String criticalUserExportExcel() throws Exception{
		UserDTO user = (UserDTO) session.get("username");
//		String check_doctor = new String(request.getParameter("check_doctor").getBytes("ISO8859-1"),"utf-8");
//		this.model.setCheck_doctor(check_doctor);
		try {
            //第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet 
            HSSFSheet sheet = null;
        	sheet = wb.createSheet("危机值导出");  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0);  
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
  
            cell = row.createCell(0);  
            cell.setCellValue("档案号");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(1);  
            cell.setCellValue("体检号");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(2);  
            cell.setCellValue("姓名");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(3);  
            cell.setCellValue("性别");  
            cell.setCellStyle(style);
            
            cell = row.createCell(4);  
            cell.setCellValue("年龄");  
            cell.setCellStyle(style);
            
            cell = row.createCell(5);  
            cell.setCellValue("电话");  
            cell.setCellStyle(style);
            
            cell = row.createCell(6);  
            cell.setCellValue("单位");  
            cell.setCellStyle(style);
            
            cell = row.createCell(7);  
            cell.setCellValue("科室");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(8);  
            cell.setCellValue("检查项目");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(9);  
            cell.setCellValue("细项");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(10);  
            cell.setCellValue("检查结果");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(11);  
            cell.setCellValue("体检日期");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(12);  
            cell.setCellValue("状态");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(13);  
            cell.setCellValue("处理医生");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(14);  
            cell.setCellValue("处理日期");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(15);  
            cell.setCellValue("处理内容");  
            cell.setCellStyle(style); 
            
            cell = row.createCell(16);  
            cell.setCellValue("创建人");  
            cell.setCellStyle(style);
            
            cell = row.createCell(17);  
            cell.setCellValue("创建时间");  
            cell.setCellStyle(style);
            
            PageReturnDTO list = this.criticalService.getCriticalLis(this.model, rows, this.getPage(),user);
            if ((list != null) && (list.getRows() != null) && (list.getRows().size() > 0)) {
            	for (int i = 0; i < list.getRows().size(); i++) {
            		CriticalDTO Critical = (com.hjw.wst.DTO.CriticalDTO) list.getRows().get(i);
            		row = sheet.createRow(i+1);
            		row.createCell(0).setCellValue(Critical.getArch_num());
            		row.createCell(1).setCellValue(Critical.getExam_num());
            		row.createCell(2).setCellValue(Critical.getUser_name());
            		row.createCell(3).setCellValue(Critical.getSex());
            		row.createCell(4).setCellValue(Critical.getAge());
            		row.createCell(5).setCellValue(Critical.getPhone());
            		row.createCell(6).setCellValue(Critical.getCompany());
            		row.createCell(7).setCellValue(Critical.getDep_name());
            		row.createCell(8).setCellValue(Critical.getItem_name());
            		row.createCell(9).setCellValue(Critical.getExamination_item_name());
            		row.createCell(10).setCellValue(Critical.getExam_result());
            		row.createCell(11).setCellValue(Critical.getCheck_date());
            		row.createCell(12).setCellValue(Critical.getDone_flag_s());
            		row.createCell(13).setCellValue(Critical.getCheck_doctor());
            		row.createCell(14).setCellValue(Critical.getDone_date());
            		row.createCell(15).setCellValue(Critical.getNote());
            		row.createCell(16).setCellValue(Critical.getCreater());
            		row.createCell(17).setCellValue(Critical.getCreate_time());
				}
				
			}
          //第七步，将文件存到流中  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
  
            excelStream = is;             //文件流  
            excelFileName = "report.xls"; //设置下载的文件名  
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
	

	/**
	 * 1197查询未处理的危机值信息
	     * @Title: getCriticalNotice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalNotice() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			List<WebResrelAtionship> web = user.getWebResource();
			boolean flag = false;
			if(web!=null){
				for (int i = 0; i < web.size(); i++) {
					if(web.get(i).getRes_code().equals("RS021")){
						if("1".equals(web.get(i).getDatavalue())){
							flag = true;
							break;
						}
					}
				}
			}
			if(flag){
				long count = this.criticalService.getgetCriticalNotice();
				this.message = count + "";
			}else{
				this.message = "0";
			}
		}else{
			this.message = "0";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 2000添加危急值首页
	     * @Title: getCriticalDBGJPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public  String getCriticalDBGJPage(){
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("5");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2000");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 2001东北国际收到添加危机值内容
	     * @Title: getCriticalDBGJPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCriticalDBGJ(){
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.criticalService.addCriticalDBGJ(user,this.model);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2001");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 东北国际获取历史危急值信息2002
	     * @Title: getCriticalListByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalListByExamNum(){
	List<Critical>	 list = this.criticalService.getCriticalListByExamNum(this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 东北国际获取历史已处理危急值信息2003
	     * @Title: getCriticalHandleListByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalHandleListByExamNum(){
		UserDTO user = (UserDTO) session.get("username");
		String critical_tactics_num = "";
		try {
			critical_tactics_num = this.customerInfoService.getCenterconfigByKey("CRITICAL_TACTICS_NUM", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.model.setCritical_tactics_num(critical_tactics_num);
		PageReturnDTO list = this.criticalService.getCriticalHandleListByExamNum(model, rows, this.getPage());
		this.outJsonResult(list);
		return NONE;
	}	
	/**
	 * 2005处理危急值首页
	     * @Title: getCriticalHandlePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public  String getCriticalHandlePage(){
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("5");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2005");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 2006危急值列表
	     * @Title: getCriticalList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCriticalList(){
		UserDTO user = (UserDTO) session.get("username");
		String critical_tactics_num = "";
		try {
			critical_tactics_num = this.customerInfoService.getCenterconfigByKey("CRITICAL_TACTICS_NUM", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.model.setCritical_tactics_num(critical_tactics_num);
		PageReturnDTO list = this.criticalService.getCriticalList(this.model, rows, this.getPage(),user);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 2007危急值删除
	     * @Title: delCriticalById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String delCriticalById()throws ServiceException{
		UserDTO user = (UserDTO) session.get("username");
		this.message =  this.criticalService.delCriticalById(this.model.getId(),user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 总检显示新增危急值 2302
	     * @Title: showNewExamCritical   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String showNewExamCritical() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		Critical cri=this.criticalService.queryCritialById(Integer.parseInt(this.model.getCritical_id1()+""));
		this.model.setCritical_id1(this.model.getCritical_id1());
		this.model.setCritical_class_parent_name(cri.getCritical_class_parent_id());
		this.model.setCritical_class_name(cri.getCritical_class_id());
		this.model.setCritical_class_level(cri.getCritical_class_level());
		this.model.setExam_result(cri.getExam_result());
		this.model.setDisease_id(cri.getDisease_id());
		this.model.setDisease_num(cri.getDisease_num());
		this.model.setCritical_suggestion(cri.getCritical_suggestion());
		String IS_SHOW_ZJORKS=this.model.getType();
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2302");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		if("KS".endsWith(IS_SHOW_ZJORKS)){
			return "success1";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	 * 主检室及科室手动保存危急值
	     * @Title: showNewExamCritical   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveCriticalDetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		Critical cri = new Critical();
		
		if (this.model.getCritical_id1() > 0) {
			cri.setId(this.model.getCritical_id1());
			String exam_num = this.model.getExam_num();
			cri.setExam_info_id(this.criticalService.getExamIdByExamNum(exam_num));
			cri.setExam_result(this.model.getExam_result());
			cri.setDisease_id(this.model.getDisease_id());
			cri.setIs_active("Y");
			cri.setData_source(1);
			cri.setCreater(user.getUserid());
			cri.setCreate_time(new Date());
			cri.setCritical_class_parent_id(this.model.getCritical_class_parent_name());
			cri.setCritical_class_id(this.model.getCritical_class_name());
			cri.setCritical_class_level(this.model.getCritical_class_level());
			cri.setDisease_num(this.model.getDisease_num());
			cri.setCritical_suggestion(this.model.getCritical_suggestion());
			cri.setExam_num(exam_num);
			int cou = this.criticalService.getCountByClass(exam_num, this.model.getCritical_class_parent_name(), this.model.getCritical_class_name(), this.model.getCritical_class_level(),this.model.getCritical_id1());
			if(cou>0){
				this.message="同一个大类、小类、等级的危机值只能存在一条数据！";
			}else{
				this.criticalService.updateCritical(cri);
				this.message="修改成功！";
			}
		}else{
			
			String exam_num = this.model.getExam_num();
			cri.setExam_info_id(this.criticalService.getExamIdByExamNum(exam_num));
			cri.setExam_result(this.model.getExam_result());
			cri.setDisease_id(this.model.getDisease_id());
			cri.setIs_active("Y");
			cri.setData_source(1);
			cri.setCreater(user.getUserid());
			cri.setCreate_time(new Date());
			cri.setCritical_class_parent_id(this.model.getCritical_class_parent_name());
			cri.setCritical_class_id(this.model.getCritical_class_name());
			cri.setCritical_class_level(this.model.getCritical_class_level());
			cri.setDisease_num(this.model.getDisease_num());
			cri.setCritical_suggestion(this.model.getCritical_suggestion());
			cri.setExam_num(exam_num);
			int cou = this.criticalService.getCountByClass(exam_num, this.model.getCritical_class_parent_name(), this.model.getCritical_class_name(), this.model.getCritical_class_level(),0);
			if(cou>0){
				this.message="同一个大类、小类、等级的危机值只能存在一条数据！";
			}else{
				this.criticalService.saveCritical(cri);
				this.message="保存成功！";
			}
		}
		
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2305");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	/**
	 * 总检删除危急值 2307
	     * @Title: delCriticalDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String delCriticalDetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		Critical cri=this.criticalService.queryCritialById(Integer.parseInt(this.model.getCritical_id1()+""));
		this.criticalService.delCriticalDetail(cri);
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2307");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 跳转选择检查项目 2306
	     * @Title: toSelectResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String toSelectResult() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2306");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 危急值处理统计页面
	     * @Title: criticalhandle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String criticalhandle() throws WebException, SQLException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2309");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 危急值处理统计列表
	     * @Title: criticalhandleShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String criticalhandleShow()  throws WebException, SQLException {
		PageReturnDTO list = new PageReturnDTO();
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 2))  {
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");;
			}
			if (this.model.getChkItem().trim().indexOf("user_name") < 0) {
				this.model.setUser_name("");
			}
			if (this.model.getChkItem().trim().indexOf("done_date") < 0) {
				this.model.setTime1("");
				this.model.setTime2("");
			}	
			if (this.model.getChkItem().trim().indexOf("done_flag") < 0) {
				this.model.setDone_flag(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("create_time") < 0) {
				this.model.setTime3("");
				this.model.setTime4("");
			}
		
		 list = this.criticalService.queryPageCritrical(this.model, rows, this.getPage(),this.sort,this.order);
		}
		 this.outJsonResult(list);
		 UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("2311");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 科室跳转危急值页面 2312
	     * @Title: depCriticalPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String depCriticalPage() throws WebException{
		return SUCCESS;
	}
	}