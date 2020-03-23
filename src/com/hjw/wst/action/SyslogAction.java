package com.hjw.wst.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.FileUtils;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamSummaryLogDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SyslogFileDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.WebXtgnbDTO;
import com.hjw.wst.model.SyslogModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 系统日志功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年2月9日 上午10:08:43   
     * @version V2.0.0.0
 */
public class SyslogAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	
	private SyslogModel model = new SyslogModel();
	
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
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
	public void setModel(SyslogModel model) {
		this.model = model;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
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
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	/**
	 * 系统日志查询页面 740 
	     * @Title: getSysLogPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSysLogPage() throws WebException{
		this.model.setStarttime(DateTimeUtil.getDate2());
		this.model.setEndtime(DateTimeUtil.getDate2());
		return SUCCESS;
	}

	/**
	 * 根据条件查询系统日志信息 741
	     * @Title: getSyslogList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSyslogList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.syslogService.findList(model.getXtgnid(), model.getXtgnid2(),
				model.getOper_type(), model.getUserid(), user.getCenter_num(), 
				model.getStarttime(), model.getEndtime(), this.page,this.rows);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取操作人员列表  742
	     * @Title: getSysLogUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSysLogUserList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<UserInfoDTO> list = this.syslogService.getSyslogUserList(user.getCenter_num());
		if(!"1".equals(model.getOper_type())){
			UserInfoDTO users = new UserInfoDTO();
			users.setChi_Name("全部");
			users.setId(0);
			list.add(0,users);
		}
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 获取操作功能列表 743
	     * @Title: getSysLogXtgnList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSysLogXtgnList() throws WebException{
		List<WebXtgnbDTO> list = this.syslogService.getSyslogXtgnList();
		WebXtgnbDTO web = new WebXtgnbDTO();
		web.setID("");
		web.setNAME("全部");
		list.add(0, web);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 系统日志备份文件页面 744
	     * @Title: getSyslogFilePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSyslogFilePage() throws WebException{
		this.model.setStarttime(DateTimeUtil.getDate2());
		this.model.setEndtime(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 查询系统日志备份文件列表 745
	     * @Title: getSyslogFileList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSyslogFileList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String path = this.customerInfoService.getCenterconfigByKey("IS_SYSLOG_PATH", user.getCenter_num()).getConfig_value().trim();
		List<SyslogFileDTO> list = findSysLogFile(path);
		this.outJsonResult(list);
		return NONE;
	}
	
	public List<SyslogFileDTO> findSysLogFile(String path){
		List<SyslogFileDTO> list = new ArrayList<SyslogFileDTO>();
		Calendar cal = Calendar.getInstance();  
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    File[] arr=FileUtils.findSysFile(path);
	    if(arr == null){
	    	return list;
	    }
	    for(int i=0;i<arr.length;i++){  
	    	//判断是否是文件夹，如果是的话，再调用一下find方法  
	        if(arr[i].isDirectory()){  
	        }else{
	        	File f = arr[i];
	            if(f.getName().endsWith(".zip")){
		            SyslogFileDTO sysLog = new SyslogFileDTO();
		            	
		            sysLog.setFilePath(f.getAbsolutePath());
		            sysLog.setFileName(f.getName());
		            sysLog.setFileSize(FileUtils.getPrintSize(f.length()));
		            cal.setTimeInMillis(f.lastModified());
		            sysLog.setFileDate(formatter.format(cal.getTime()));
		            	
		            list.add(sysLog);
	            }
	        }  
	        //根据正则表达式，寻找匹配的文件  
	        //这个getAbsolutePath()方法返回一个String的文件绝对路径  
	    }
        return list;
    }
  
	/**
	 * 通过条件备份生成日志文件 746
	     * @Title: backupSyslog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String backupSyslog() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String path = this.customerInfoService.getCenterconfigByKey("IS_SYSLOG_PATH", user.getCenter_num()).getConfig_value().trim();
		try {
			this.message = this.syslogService.backupSyslog(model.getXtgnid(), model.getXtgnid2(),
					model.getOper_type(), model.getUserid(), user.getCenter_num(), 
					model.getStarttime(), model.getEndtime(),path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 下载日志文件 747
	     * @Title: exportSyslogFile   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String exportSyslogFile(){
		UserDTO user = (UserDTO) session.get("username");
		String path = this.customerInfoService.getCenterconfigByKey("IS_SYSLOG_PATH", user.getCenter_num()).getConfig_value().trim();
		File file = new File(path + "\\"+model.getFilePath());
		try {
			fileName = file.getName();
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	private InputStream fileInputStream;  //输出流变量  
    private String fileName; //下载文件名  
    
	public InputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
	/**
	 * 删除系统日志备份文件 748
	     * @Title: deleteSyslogFile   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteSyslogFile() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String path = this.customerInfoService.getCenterconfigByKey("IS_SYSLOG_PATH", user.getCenter_num()).getConfig_value().trim();
		File file = new File(path + "\\"+model.getFilePath());
		boolean stuas =  file.delete();
		if(stuas){
			this.message = "删除文件成功!";
		}else{
			this.message = "删除文件失败!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1702 检查结果、总检结果审计页面
	     * @Title: getExamInfoAuditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoAuditPageLog() throws WebException{
		this.model.setS_join_date(DateTimeUtil.getDate2());
		this.model.setE_join_date(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 1703  审计查询体检信息列表
	     * @Title: getExamInfoAuditListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoAuditListLog() throws WebException{
		UserDTO user = (UserDTO) session.get("username");

		PageReturnDTO list = this.syslogService.getExamInfoAuditListLog(model, user.getCenter_num(),this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1704 审计查看普通科室日志页面
	     * @Title: getExamDepResultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamDepResultPageLog() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 1705 审计查看科室检查次数日志树
	     * @Title: getExamDepResultListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamDepResultCountLog() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExamDepResultDTO> list = this.syslogService.getExamDepResultCountLog(model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1706 审计查询科室检查历次结果日志列表
	     * @Title: getCommonExamDetailListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCommonExamDetailListLog() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepExamResultDTO> list = this.syslogService.getCommonExamDetailListLog(model.getId(), model.getDep_id(), model.getCheck_count(), user.getCenter_num(),model.getExam_num());
		if(model.getCheck_count() > 1){
			List<DepExamResultDTO> lastlist = this.syslogService.getCommonExamDetailListLog(model.getId(), model.getDep_id(), model.getCheck_count()-1, user.getCenter_num(),model.getExam_num());
			for (DepExamResultDTO depdto : list){
				for (DepExamResultDTO lastdto : lastlist){
					if(depdto.getItem_id() == lastdto.getItem_id()){
						if(!depdto.getExam_result().equals(lastdto.getExam_result())){
							depdto.setExam_status("XG");
						}
						depdto.setExam_desc(lastdto.getExam_result());
					}
				}
			}
		}
		
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1707 审计查询总检审核复审日志页面
	     * @Title: getExamSummaryPageLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryPageLog()throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 1708 审计查询总检审核复审次数日志树
	     * @Title: getExamSummaryCountLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamSummaryCountLog() throws WebException{
		List<ExamSummaryLogDTO> list = this.syslogService.getExamSummaryCountLog(model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1709 审计查询总检审核复审阳性发现日志列表
	     * @Title: getExamExamDiseaseListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExamExamDiseaseListLog() throws WebException{
		List<ExaminfoDiseaseDTO> diseaselist = this.syslogService.getExamExamDiseaseListLog(model.getId());
		this.outJsonResult(diseaselist);
		return NONE;
	}
}
