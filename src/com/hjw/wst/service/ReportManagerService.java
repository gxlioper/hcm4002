package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ReportReceiveDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ReportReceive;
import com.hjw.wst.model.ReportManagerModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 检后报告管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年8月31日 下午2:27:39   
     * @version V2.0.0.0
 */
public interface ReportManagerService {

	/**
	 * @param user 
	 * 根据条件查询体检人员信息
	     * @Title: getExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: List<ExamInfoDTO>      
	     * @throws
	 */
	public PageReturnDTO getExamInfoList(ReportManagerModel model,int pagesize, int pageno,String sort,String order, UserDTO user) throws ServiceException;
	
	/**
	 * 根据体检编号查询 体检信息
	     * @Title: getExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfoByExamNum(String examNum) throws ServiceException;

	/**
	 * 修改体检信息
	     * @Title: updateExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examInfo
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo updateExamInfo(ExamInfo examInfo) throws ServiceException;
	
	/**
	 * 根据体检编码查询 邮寄与自己取报告 默认值
	     * @Title: getInitializeReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportReceive      
	     * @throws
	 */
	public ReportReceive getInitializeReport(String exam_num) throws ServiceException;
	
	/**
	 * 保存 报告发送方式
	     * @Title: saveReportRecive   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param reportReceive
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportReceive      
	     * @throws
	 */
	public ReportReceive saveReportRecive(ReportReceive reportReceive) throws ServiceException;
	
	/**
	 * 修改报告发送信息
	     * @Title: updateReportRemarke   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param reportReceive
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportReceive      
	     * @throws
	 */
	public String updateReportRemarke(String exam_num,String remarke,UserDTO user) throws ServiceException;
	
	/**
	 * 查询 报告发送信息
	     * @Title: getReportReceive   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportReceive      
	     * @throws
	 */
	public ReportReceiveDTO getReportReceive(String exam_num) throws ServiceException;
	
	/**
	 * 删除报告处理信息
	     * @Title: deleteReportReceive   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param reportReceive
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportReceive      
	     * @throws
	 */
	public void deleteReportReceive(String exam_nums) throws ServiceException;
	
	/**
	 * 
	     * @Title: GetzocforcomBatch   
	     * @Description: 通过单位批次获取职业体检类型  
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> GetzocforcomBatch(long comid,long batchid) throws ServiceException;
	
	/**
	 * 
	     * @Title: GetzofforcomBatch   
	     * @Description: 通过单位批次获取职业危害因素类型
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
	public List<JobDTO> GetzofforcomBatch(long comid,long batchid) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getTermReportUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_id
	     * @param: @param rylb
	     * @param: @param levels
	     * @param: @param tjlx
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTermReportUserList(long com_id, long batchid, String exam_num, long groupid,
			String status, String sex, String username, long set_id, String rylb, String levels,String tjlx,
			long userid, String centernum,String chkItem, int pagesize, int pageno, String sort, String order, UserDTO user,String center_num)
			throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getTermReportUserZybList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param zytjlx
	     * @param: @param status
	     * @param: @param username
	     * @param: @param zywhys
	     * @param: @param levels
	     * @param: @param tjlx
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTermReportUserZybList(long com_id, long batchid, String comon_report_type, String exam_num, String zytjlx,
			String status,String is_report_print,String username, String zywhys, String levels,String tjlx,
			long userid, String centernum,String chkItem, int pagesize, int pageno, String sort, String order, UserDTO user,String center_num)
			throws ServiceException;
	
	/**
	 * 修改报告类型
	     * @Title: updateReportEdit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @param comon_report_type
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateReportEdit(String ids, String comon_report_type) throws ServiceException;

	
}
