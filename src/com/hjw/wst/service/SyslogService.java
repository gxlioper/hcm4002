package com.hjw.wst.service;

import java.io.IOException;
import java.util.List;

import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamSummaryLogDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.WebXtgnbDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SyslogModel;
import com.synjones.framework.exception.ServiceException;

public interface SyslogService {
	public void saveSysLog(SysLog sysLog);
	public void delSysLog(SysLog sysLog);
	/**
	 * 
	     * @Title: findList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param xtgnid
	     * @param: @param xtgnid2
	     * @param: @param oper_type
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param starttime
	     * @param: @param endtime
	     * @param: @param currentPage
	     * @param: @param pageSize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO findList(String xtgnid,String xtgnid2,String oper_type,String userid,String centernum,String starttime,String endtime,int currentPage, int pageSize);
	
	/**
	 * 按条件备份日志数据
	     * @Title: backupSyslog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param xtgnid
	     * @param: @param xtgnid2
	     * @param: @param oper_type
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param starttime
	     * @param: @param endtime
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String backupSyslog(String xtgnid,String xtgnid2,String oper_type,String userid,String centernum,String starttime,String endtime,String path) throws IOException;

	/**
	 * 查询操作人员列表
	     * @Title: getSyslogUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<UserInfoDTO>      
	     * @throws
	 */
	public List<UserInfoDTO> getSyslogUserList(String center_num) throws ServiceException;
	
	/**
	 * 查询系统功能列表
	     * @Title: getSyslogXtgnList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebXtgncdDTO>      
	     * @throws
	 */
	public List<WebXtgnbDTO> getSyslogXtgnList() throws ServiceException;
	
	/**
	 * 审计查询体检信息列表
	     * @Title: getExamInfoAuditListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoAuditListLog(SyslogModel model, String center_num, int rows, int page,String sort,String order) throws ServiceException ;
	
	/**
	 * 审计查看科室检查次数日志树
	     * @Title: getExamDepResultCountLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamDepResultDTO>      
	     * @throws
	 */
	public List<ExamDepResultDTO> getExamDepResultCountLog(String exam_num, String center_num) throws ServiceException ;
	
	/**
	 * 审计查询科室检查历次结果日志列表
	     * @Title: getCommonExamDetailListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param dep_id
	     * @param: @param row
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamDepResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getCommonExamDetailListLog(long examinfo_id,long dep_id,long row, String center_num,String exam_num) throws ServiceException ;
	
	/**
	 * 审计查询总检审核复审次数日志树
	     * @Title: getExamSummaryCountLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamSummaryLogDTO>      
	     * @throws
	 */
	public List<ExamSummaryLogDTO> getExamSummaryCountLog(String exam_num) throws ServiceException ;
	
	/**
	 * 审计查询总检审核复审阳性发现日志列表
	     * @Title: getExamExamDiseaseListLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param operation_type
	     * @param: @param row
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> getExamExamDiseaseListLog(long id) throws ServiceException ;
}
