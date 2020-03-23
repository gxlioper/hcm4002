package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  导引单
     * @date:   2016年9月26日 上午11:52:37   
     * @version V2.0.0.0
 */
public interface examInfoService {
	
	
	/**
	 * 未检项目查询
	     * @Title: queryWjxmExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> queryWjxmExamInfo(String examNum, String center_num) throws ServiceException;

	/**
	 * 弃项查询
	     * @Title: queryQjxmExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> queryQjxmExamInfo(String examNum, String center_num) throws ServiceException;

	/**
	 * ID查询
	     * @Title: loadExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItem      
	     * @throws
	 */
	public ExaminfoChargingItem loadExamInfo(long id) throws ServiceException;

	/**
	 * 分页查询
	     * @Title: queryPageExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	
	public PageReturnDTO queryPageExamInfo(String exam_num ,int pageno, int pagesize);
	
	/**
	 * 放弃
	     * @Title: deleteExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examInfo
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ExaminfoChargingItem deleteExam(ExaminfoChargingItem examInfo)throws ServiceException;
		
	/**
	 * 恢复
	     * @Title: updateExam   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examInfo
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ExaminfoChargingItem updateExam(ExaminfoChargingItem examInfo)throws ServiceException;
	/**
	 * 
	     * @Title: receiveExamInfo   
	     * @Description: TODO(回收导检单)   
	     * @param: @param examInfo
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void receiveExamInfo(ExamInfo examInfo) throws ServiceException;
	/**
	 * 
	     * @Title: findExamInfo   
	     * @Description: TODO(查找实体类)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo findExamInfo(String exam_num) throws ServiceException;
	/**
	 * 
	     * @Title: updateBatchDeletExamInfo   
	     * @Description: TODO(批量弃检)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateBatchDeletExamInfo(String ids) throws  ServiceException;
	/**
	 * 
	     * @Title: plhf   
	     * @Description: TODO(批量恢复导引单)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void plhf(String ids) throws ServiceException;
	/**
	 * 
	     * @Title: updateplyq   
	     * @Description: TODO(批量延期)   
	     * @param: @param date
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateplyq(String date,String ids) throws  ServiceException;
	/**
	 * @param user 
	 * 
	     * @Title: getExamInfo   
	     * @Description: TODO(体检人员列表)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public PageReturnDTO getExamInfo(ExamInfoDTO info,int page,int pageSize, UserDTO user) throws ServiceException;
	/**
	 * @return 
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(删除/恢复人员)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public int updateExamInfo(ExamInfoDTO dto) throws ServiceException;
	/**
	 * 
	     * @Title: revocationDYD   
	     * @Description: TODO(撤销导引单)   
	     * @param: @param dto
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void   revocationDYD(String exam_num)throws ServiceException;

	/**
	 * 选择报告类型
	     * @Title: saveExamInfoReportType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param report_type
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveExamInfoReportType(long examinfo_id,long report_type) throws ServiceException;
	public void updateExamInfo(ExamInfo examinfo)  throws ServiceException;
}
	
