package com.hjw.wst.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DirectorDecisionService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import org.apache.poi.hssf.usermodel.*;

/**
 * 主任决策模块查询
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年2月16日 上午9:51:26   
     * @version V2.0.0.0
 */
public class DirectorDecisionAction extends BaseAction implements ModelDriven{

	private AcceptanceCheckModel model = new AcceptanceCheckModel();
	private DirectorDecisionService directorDecisionService;
	private SyslogService syslogService;    
	private CustomerInfoService customerInfoService;
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
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
	@Override
	public Object getModel() {
		return model;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setModel(AcceptanceCheckModel model) {
		this.model = model;
	}

	public void setDirectorDecisionService(DirectorDecisionService directorDecisionService) {
		this.directorDecisionService = directorDecisionService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	/**
	 * 体检信息查询页面 749
	     * @Title: getExamInfoPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorExamInfoPage() throws WebException{
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("749");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 根据条件查询体检信息列表 750
	     * @Title: getDirectorExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorExamInfoList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.directorDecisionService.getExamInfoList(model,this.rows, this.getPage(),this.sort,this.order,user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 根据体检ID查询体检项目 751
	     * @Title: getDirectorItemStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorItemStatus() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = this.directorDecisionService.getDirectorItemStatus(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 项目检查结果页面 752
	     * @Title: getDirectorItemResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorItemResult() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 项目完成情况查询页面 753
	     * @Title: getExamItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorExamItemPage() throws WebException{
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("753");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询体检套餐列表  754
	     * @Title: getDirectorExamSetList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorExamSetList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamSetDTO> list = this.directorDecisionService.getDirectorExamSetList(user.getCenter_num());
		ExamSetDTO examSet = new ExamSetDTO();
		examSet.setId(0);
		examSet.setSet_name("不限");
		list.add(0,examSet);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询科室列表 755
	     * @Title: getDirectorDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorDepList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepartmentDepDTO> list = this.directorDecisionService.getDirectorDepList(user.getCenter_num());
		DepartmentDepDTO dep = new DepartmentDepDTO();
		dep.setId(0);
		dep.setDep_name("不限");
		list.add(0,dep);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询收费项目列表   756
	     * @Title: getDirectorItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorItemList() throws WebException,SQLException{
		List<ChargingItemDTO> list = this.directorDecisionService.getDirectorItemList(this.model.getDep_id());
		ChargingItemDTO item = new ChargingItemDTO();
		item.setId(0);
		item.setItem_name("不限");
		list.add(0,item);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 根据条件查询体检项目信息  757
	     * @Title: getDirectorExamItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDirectorExamItemList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.directorDecisionService.getDirectorExamItemList(model,this.rows, this.getPage(),user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 每日体检者构成页面  758
	     * @Title: getDailyExamInfoPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDailyExamInfoPage() throws WebException{
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("758");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询每日体检者列表 759
	     * @Title: getDilyExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDilyExamInfoList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<DailyExamInfoDTO> list = this.directorDecisionService.getDilyExamInfoList(this.model, user.getCenter_num());
		PageReturnDTO page = new PageReturnDTO();
		page.setRows(list);
		page.setTotal(list.size());
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 团体人员结算综合统计 794
	     * @Title: teamSettlementStatisticsPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String teamSettlementStatisticsPage() throws WebException{
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 按条件查询团体结算信息 795
	     * @Title: getTeamSettlementStatisticsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getTeamSettlementStatisticsList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.directorDecisionService.getTeamAccountList(model, user.getCenter_num(),this.rows, this.getPage());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 按批次ID查询该批次下的体检者信息 796
	     * @Title: getTeamSettlementExamList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getTeamSettlementExamList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.directorDecisionService.getTeamSettlementExamList(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1174 获取体检信息综合查询页面
	     * @Title: getExamComprehenPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamComprehenPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		this.model.setReport_print_type(
				this.customerInfoService.getCenterconfigByKey("REPORT_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}
	
	/**
	 * 1175 根据条件查询体检信息列表
	     * @Title: getExamComprehenList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamComprehenList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list= null;
		if(model.getCustomer_id() == 1){//按条件查询
			list = this.directorDecisionService.getExamComprehenList(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 5){//无需总检查询
			list = this.directorDecisionService.getExamComprehenListWX(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 2){//查询三天未导体检信息列表
			list = this.directorDecisionService.getExamComprehenListWD(model, user.getCenter_num(),-3,this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 3){//查询两天未派体检信息列表
			list = this.directorDecisionService.getExamComprehenListWP(model, user.getCenter_num(),2,this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 4){//查询5天未出报告体检信息列表
			list = this.directorDecisionService.getExamComprehenListWC(model, user.getCenter_num(),-5,this.rows, this.getPage(),this.sort,this.order);
		}else{
			list = this.directorDecisionService.getExamComprehenList(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		}
		
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 根据体检ID查询打印流水 1364
	     * @Title: printflowlist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String printflowlist() throws WebException,SQLException{
		List<ExamInfoDTO> list = this.directorDecisionService.printflowlist(this.model.getExaminfo_id());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 
	     * @Title: getHisExamResultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws ParseException      
	     * @return: String      
	     * @throws
	 */
	public String getHisExamResultPage() throws WebException, ParseException{
		model.setE_join_date(DateTimeUtil.getdateAddDay(14,model.getS_join_date()));
		return SUCCESS;
	}
	/**
	 * lis结果
	     * @Title: getPatLabSamplePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPatLabSamplePage() throws WebException{
		model.setE_join_date(DateTimeUtil.getdateAddDay(14,model.getS_join_date()));
		return SUCCESS;
	}
	/**
	 * 获取pacs结果
	     * @Title: getPacsExamResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getPacsExamResultList(){
		List<ChargingItemDTO> list = directorDecisionService.getPacsExamResultList(this.model.getExam_num(),this.model.getS_join_date(),this.model.getE_join_date());
		this.outJsonResult(list);
		return NONE;
	}
	public  String getLisItemList(){
		List<DepExamResultDTO> list = directorDecisionService.getLisItemList(this.model.getExam_num(),this.model.getS_join_date(),this.model.getE_join_date());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 117报告预警
	     * @Title: getExamComprehenList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getReportWarningList()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list= null;
		if(model.getCustomer_id() == 1){//按条件查询
			list = this.directorDecisionService.getExamComprehenList(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 5){//无需总检查询
			list = this.directorDecisionService.getExamComprehenListWX(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 2){//查询三天未导体检信息列表
			list = this.directorDecisionService.getExamComprehenListWD(model, user.getCenter_num(),-3,this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 3){//查询两天未派体检信息列表
			list = this.directorDecisionService.getExamComprehenListWP(model, user.getCenter_num(),2,this.rows, this.getPage(),this.sort,this.order);
		}else if(model.getCustomer_id() == 4){//查询5天未出报告体检信息列表
			list = this.directorDecisionService.getExamComprehenListWC(model, user.getCenter_num(),-5,this.rows, this.getPage(),this.sort,this.order);
		}else{
			list = this.directorDecisionService.getExamComprehenList(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		}
		
		this.outJsonResult(list);
		return NONE;
	}


    /**
     *
     * @Title: djdUserExportExcel
     * @Description: TODO(登记台人员管理 导出excel)
     * @param: @return
     * @return: String
     * @throws
     */
    public String dailyExamInfoExportExcel() {
        try {
            //第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet
            HSSFSheet sheet = wb.createSheet("体检人员信息");

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
            cell.setCellValue("身份证号");
            cell.setCellStyle(style);

            cell = row.createCell(3);
            cell.setCellValue("体检类型");
            cell.setCellStyle(style);

            cell = row.createCell(4);
            cell.setCellValue("姓名");
            cell.setCellStyle(style);

            cell = row.createCell(5);
            cell.setCellValue("性别");
            cell.setCellStyle(style);

            cell = row.createCell(6);
            cell.setCellValue("年龄");
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue("电话");
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue("地址");
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue("单位");
            cell.setCellStyle(style);

            cell = row.createCell(10);
            cell.setCellValue("套餐");
            cell.setCellStyle(style);

            cell = row.createCell(11);
            cell.setCellValue("登记日期");
            cell.setCellStyle(style);

            cell = row.createCell(12);
            cell.setCellValue("体检日期");
            cell.setCellStyle(style);

            cell = row.createCell(13);
            cell.setCellValue("个人金额");
            cell.setCellStyle(style);

            cell = row.createCell(14);
            cell.setCellValue("团体金额");
            cell.setCellStyle(style);

            cell = row.createCell(15);
            cell.setCellValue("已收费");
            cell.setCellStyle(style);

            cell = row.createCell(16);
            cell.setCellValue("未收费");
            cell.setCellStyle(style);

            cell = row.createCell(17);
            cell.setCellValue("介绍人");
            cell.setCellStyle(style);

            //第六步，写入实体数据，实际应用中这些数据从数据库得到
            UserDTO user = (UserDTO) session.get("username");
            PageReturnDTO map = this.directorDecisionService.getExamInfoList(model,100000, 1,this.sort,this.order,user);

            if ((map != null) && (map.getRows() != null) && (map.getRows().size() > 0)) {
                for(int i=0;i<map.getRows().size();i++){
                    ExamInfoUserDTO eu = (ExamInfoUserDTO) map.getRows().get(i);

                    row = sheet.createRow(i+1);

                    row.createCell(0).setCellValue(eu.getArch_num());
                    row.createCell(1).setCellValue(eu.getExam_num());
                    row.createCell(2).setCellValue(eu.getId_num());
                    row.createCell(3).setCellValue(eu.getExam_types());
                    row.createCell(4).setCellValue(eu.getUser_name());
                    row.createCell(5).setCellValue(eu.getSex());
                    row.createCell(6).setCellValue(eu.getAge());
                    row.createCell(7).setCellValue(eu.getPhone());
                    row.createCell(8).setCellValue(eu.getAddress());
                    row.createCell(9).setCellValue(eu.getCompany());
                    row.createCell(10).setCellValue(eu.getSet_name());
                    row.createCell(11).setCellValue(eu.getRegister_date());
                    row.createCell(12).setCellValue(eu.getJoin_date());
                    row.createCell(13).setCellValue(eu.getPersonal_pay());
                    row.createCell(14).setCellValue(eu.getTeam_pay());
                    row.createCell(15).setCellValue(eu.getPay_amount());
                    row.createCell(16).setCellValue(eu.getNo_pay_amount());
                    row.createCell(17).setCellValue(eu.getIntroducer());
                }
            }
            //第七步，将文件存到流中
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] fileContent = os.toByteArray();
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);

            excelStream = is;             //文件流
            excelFileName = "report.xls"; //设置下载的文件名
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    //-------------------------------------------------------------
    private InputStream excelStream;  //输出流变量
    private String excelFileName; //下载文件名

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
}
