package com.hjw.wst.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.CommonExamDetailDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExamSuggestionLogDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamSuggestionLog;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.synjones.framework.exception.ServiceException;

public interface AcceptanceCheckService {

	/**
	 * @param user 
	 * 根据条件查询体检信息
	     * @Title: getExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoList(AcceptanceCheckModel model,int pagesize, int pageno,String sort,String order, UserDTO user) throws ServiceException;
	
	/**
	 * 合并查询
	     * @Title: getselectedarch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public List<ExamInfoDTO>  getselectedarch(String ids,String center_num)throws ServiceException;
	
	/**
	 * 合并档案保存
	     * @Title: updateArchCombine   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param arch_combine_ids
	     * @param: @param create_time
	     * @param: @param arch_num
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateArchCombine(String arch_combine_ids,String arch_num,long customer_id,UserDTO user)throws ServiceException, SQLException;
	
	public void updateExam_ext_info(Connection con,ExamInfoDTO dahb)throws ServiceException;
	
	public void addExamInfoExt(Connection connection, ExamInfoDTO dahb) throws Exception,ServiceException;
	
	public void editExam_ext_info(String arch_combine_ids,String str_arch_num,UserDTO user)throws Exception,ServiceException;
	
	
	public ExamInfoDTO queryexamInfomations(String arch_num)throws Exception,ServiceException;
	
	/**
	 * 根据人员ID查询检查科室与项目
	     * @Title: getDepAndItemTree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getDepAndItemTree(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 查询普通科室检查结果
	     * @Title: getPtResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param c_item_id
	     * @param: @return      
	     * @return: List<CommonExamDetailDTO>      
	     * @throws
	 */
	public List<CommonExamDetailDTO> getPtResultList(String exam_num,String item_code) throws ServiceException;
	
	/**
	 * 查询检验科检查结果
	     * @Title: getHyResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param c_item_id
	     * @param: @return      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public List<ExamResultDetailDTO> getHyResultList(String exam_num,String item_code) throws ServiceException;
	
	/**
	 * 查询影像科检查结果
	     * @Title: getYxResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param c_item_id
	     * @param: @return      
	     * @return: List<ViewExamDetailDTO>      
	     * @throws
	 */
	public List<ViewExamDetailDTO> getYxResultList(String exam_num,String item_code) throws ServiceException;
	
	/**
	 * 查询科室检查医生与科室结论
	     * @Title: getExamDoctorAndDepResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param c_item_id
	     * @param: @return      
	     * @return: ExamDepResultDTO      
	     * @throws
	 */
	public ExamDepResultDTO getExamDoctorAndDepResult(String exam_num,String item_code) throws ServiceException;
	
	/**
	 * 获取总检结论
	     * @Title: getFinalSummaryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSummaryDTO      
	     * @throws
	 */
	public ExamSummaryDTO getFinalSummaryResult(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 获取总检疾病与建议
	     * @Title: getExamDiseaseResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> getExamDiseaseResult(String exam_num) throws ServiceException;
	
	/**
	 * 获取影像科室图片路径
	     * @Title: getViewExamImagePath   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param view_detail_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ViewExamDetailDTO>      
	     * @throws
	 */
	public List<ViewExamDetailDTO> getViewExamImagePath(String pacs_req_code,String exam_num) throws ServiceException;
	
	/**
	 * 保存核收与取消核收信息
	     * @Title: saveAcceptanceExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveAcceptanceExamInfo(AcceptanceCheckModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 核收界面报告预览意见
	     * @Title: serchExamSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSuggestionLog      
	     * @throws
	 */
	public ExamSuggestionLog serchExamSuggestionInfo(String exam_num, long userId) throws ServiceException;
	
	/**
	 * 核收界面报告预览意见驳回判断是否总检 2108
	     * @Title: serchExamSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String validateZJ(String exam_num) throws ServiceException;
	
	/**
	 * 核收界面报告预览意见查询
	     * @Title: serchExamSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSuggestionLog      
	     * @throws
	 */
	public List<ExamSuggestionLogDTO> serchExamSuggestionInfo(String exam_num) throws ServiceException;
	
	/**
	 * 核收界面报告预览意见删除
	     * @Title: serchExamSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return:       
	     * @throws
	 */
	public void delExamSuggestion(long id) throws ServiceException;
	
	/**
	 * 核收界面报告预览意见保存
	     * @Title: saveExamSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSuggestionLog      
	     * @throws
	 */
	public ExamSuggestionLog saveExamSuggestionInfo(ExamSuggestionLog examSuggestionLog) throws ServiceException;
	
	/**
	 * 核收界面报告预览意见修改
	     * @Title: editExamSuggestionInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return:       
	     * @throws
	 */
	public void editExamSuggestionInfo(ExamSuggestionLog examSuggestionLog,long id) throws ServiceException;
	
	/**
	 * 查询个科室检查结果
	     * @Title: getAcceptanceItemResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getAcceptanceItemResult(String exam_num,String IS_EXAM_RESULT_CITEM) throws ServiceException;
	
	/**
	 * 查询体检信息
	     * @Title: getExamInfoByid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfoByid(String exam_num) throws ServiceException;
}
