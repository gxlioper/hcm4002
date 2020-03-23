package com.hjw.wst.service;

import java.util.List;

import com.hjw.webService.service.bean.ResultSerBody;
import com.hjw.webService.service.bean.SgtZMsg;
import com.hjw.webService.service.bean.SgtZRequestInMsg;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.MultiSignCollector;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ProcListResult;
import com.hjw.wst.DTO.ProcPacsResult;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserFeeDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.GroupInfo;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface CommService {
	
	/**
	 * 
	     * @Title: getExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoForNum(String exam_num) throws ServiceException;
	
	/**
	 * 保存收费信息
	     * @Title: saveFeesResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public UserFeeDTO saveFeesResult(ResultSerBody rb, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: sendPacsLis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userList
	     * @param: @param req_nums      
	     * @return: void      
	     * @throws
	 */
	public void sendPacsLis(UserFeeDTO uf) throws ServiceException;
	
	/**
	 * 
	     * @Title: doproc_Lis_result   
	     * @Description: 执行lis结果插入   
	     * @param: @param pr
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int doproc_Lis_result(ProcListResult pr)throws ServiceException;
	
	/**
	 * 
	     * @Title: proc_pacs_report_dbgj   
	     * @Description: 执行pacs插入   
	     * @param: @param pr
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int proc_pacs_report_dbgj(ProcPacsResult pr)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDepNumForPacs   
	     * @Description: 获取pacs科室编码   
	     * @param: @param pacs_req_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getDepNumForPacs(String pacs_req_code)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO getDatadis(String data_code)throws ServiceException;
	
	/**
	 * 
	     * @Title: setExamInfoChargeItemStatus   
	     * @Description: pacs 更新状态   
	     * @param: @param req_num
	     * @param: @param exam_num
	     * @param: @param status
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void setExamInfoChargeItemPacsStatus(List<String> req_nums,String exam_num,String status, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: setExamInfoChargeItemLisStatus   
	     * @Description: lis 状态更改   
	     * @param: @param req_num
	     * @param: @param exam_num
	     * @param: @param status
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void setExamInfoChargeItemLisStatus(List<String> req_nums,String exam_num,String status,String samstatus, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: itemTotal   
	     * @Description: 诊疗项目查询统计   
	     * @param: @param datetime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
		public List<JobDTO> itemTotal(String datetime) throws ServiceException;
		
		/**
		 * 
		     * @Title: priceTotal   
		     * @Description: 诊疗项目查询统计   
		     * @param: @param datetime
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<JobDTO>      
		     * @throws
		 */
		public List<JobDTO> priceTotal(String datetime) throws ServiceException;
		
		/**
		 * 
		     * @Title: priceShow   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param datetime
		     * @param: @param pageno
		     * @param: @param pagesize
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: PageReturnDTO      
		     * @throws
		 */
		public PageReturnDTO priceShow(String datetime, int pageno, int pagesize) throws ServiceException;
		
		/**
		 * 
		     * @Title: itemshow   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param datetime
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<JobDTO>      
		     * @throws
		 */
	public PageReturnDTO itemshow(String datetime, int pageno, int pagesize) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCustExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getCustExamInfoForNum(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: sgtzImpdo   
	     * @Description: 身高体重入库   
	     * @param: @param sgtzmsg
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public SgtZMsg sgtzImpdo(SgtZRequestInMsg sgtzmsg, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: thirdPRRSave   
	     * @Description: 多体征采集仪信息入库
	     * @param: @param model      
	     * @return: void      
	     * @throws
	 */
	public String thirdPRRSave(MultiSignCollector model,String center_num) throws ServiceException;
	
	/**
	 * 
	 * @Title: getExamInfoForBarcode   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param sample_barcode
	 * @param: @return
	 * @param: @throws ServiceException      
	 * @return: ExamInfoUserDTO      
	 * @throws
	 */
	public ExamInfoUserDTO getExamInfoForBarcode(String sample_barcode) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForReqNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param req_nums
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoForReqNum(String req_nums) throws ServiceException;
}
