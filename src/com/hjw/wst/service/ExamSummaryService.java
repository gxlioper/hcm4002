package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.ExamFlowConfig;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamSummary;
import com.hjw.wst.domain.ReportPdf;
import com.hjw.wst.model.ExamSummaryModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 总检室功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年11月15日 上午9:24:33   
     * @version V2.0.0.0
 */
public interface ExamSummaryService {

	/**
	 * 查询普通科室检查结果
	     * @Title: getPtResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getPtResultList(String exam_num,String app_type, String center_num) throws ServiceException;
	
	/**
	 * 查询检验科室检查结果
	     * @Title: getHyResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getHyResultList(String exam_num,String examResultStyle,String app_type, String center_num) throws ServiceException;
	
	/**
	 * 查询影像科室检查结果
	     * @Title: getYxResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getYxResultList(String exam_num,String app_type,String isShowSamplename, String center_num) throws ServiceException;
	
	/**
	 * 查询体检人员的阳性发现列表
	     * @Title: getExamInfoDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> getExamInfoDisease(String exam_num,String app_type) throws ServiceException;
	
	/**
	 * 通过疾病逻辑生成阳性发现列表
	     * @Title: createExamInfoDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> createExamInfoDisease(String exam_num,String isExamSuggest, String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 通过疾病逻辑与疾病归类科室生成阳性发现列表
	     * @Title: createExamInfoDiseaseDep   
	     * @Description: 通过疾病逻辑与疾病归类科室生成阳性发现列表   
	     * @param: @param exam_num
	     * @param: @param siExamDiseaseSeq
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> createExamInfoDiseaseDep(String exam_num,String siExamDiseaseSeq,String isExamDiseaseItem,String isExamSuggest, String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 新疾病逻辑判断生成阳性发现列表
	     * @Title: createNewExamInfoDisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> createNewExamInfoDisease(String exam_num,String isExamSuggest,String isUseCompositeLogic, String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 根据单项阳性逻辑生成阳性发现
	     * @Title: createNewExamInfoDiseaseSingle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param isExamSuggest
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> createNewExamInfoDiseaseSingle(String exam_num,String isExamSuggest, String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 汇总总检结论 (普通)
	     * @Title: createFinalExamResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String createFinalExamResult(String exam_num,String isExamSummary,String isShowRefvalue,String isShowSamplename,String examResultShowItemId,String examSummaryResultIn,String app_type, String center_num) throws ServiceException;
	
	/**
	 * 汇总总检结论 (ZY2)
	     * @Title: createFinalExamResultZY2   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param isExamSummary
	     * @param: @param isShowRefvalue
	     * @param: @param isShowSamplename
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String createFinalExamResultZY2(String exam_num,String isExamSummary,String isShowRefvalue,String isShowSamplename,String examResultShowItemId,String examSummaryResultIn,String app_type, String center_num) throws ServiceException;
	
	/**
	 * 汇总总检结论 (江苏省机关医院)
	     * @Title: createFinalExamResultZY2   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param isExamSummary
	     * @param: @param isShowRefvalue
	     * @param: @param isShowSamplename
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String createFinalExamResultJGYY(String exam_num,String isExamSummary,String isShowRefvalue,String isShowSamplename,String examResultShowItemId,String examSummaryResultIn,String app_type, String center_num) throws ServiceException;
	/**
	 * 查询总检结论，与总检状态
	     * @Title: getFinalExamResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSummaryDTO      
	     * @throws
	 */
	public ExamSummaryDTO getFinalExamResult(String exam_num,String app_type,UserDTO user) throws ServiceException;
	
	/**
	 * 根据条件 查询 阳性发现列表  
	     * @Title: serchDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param disease_name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<DiseaseKnowloedgeDTO> serchDiseaseList(String disease_name) throws ServiceException;
	
	/**
	 * 根据阳性发现ID查询健康建议
	     * @Title: serchDiseaseSuggestionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param disease_id
	     * @param: @param age
	     * @param: @param sex
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DiseaseKnowloedgeDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> serchDiseaseSuggestionList(long disease_id,long age,String sex,String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 保存总检信息
	     * @Title: saveExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummary(ExamSummaryModel model,ExamSummary examSummary,UserDTO user,String flag) throws ServiceException;
	
	/**
	 * 取消总检
	     * @Title: delExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public ExamInfo updateExamInfo(ExamInfo examinfo) throws ServiceException;
	
	/**
	 * 取消审核，与审核
	     * @Title: updateExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examSummary
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSummary      
	     * @throws
	 */
	public ExamSummary updateExamSummary(ExamSummary examSummary) throws ServiceException;
	public void saveReportPdf(ReportPdfDTO rp, UserDTO user, String app_type) throws ServiceException;
	/**
	 * 通过ID查询总检记录
	     * @Title: getExamSummaryById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSummary      
	     * @throws
	 */
	public ExamSummary getExamSummaryById(String exam_num,String app_type) throws ServiceException;
	
	/**
	 * 通过体检号查询体检信息
	     * @Title: getExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfoByExamNum(String exam_num) throws ServiceException;
	
	/**
	 * 分页查询体检信息
	     * @Title: getExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoList(ExamSummaryModel model,UserDTO user,String apptype,int rows,int page,String sort,String order)throws ServiceException;
	
	/**
	 * 分页查询可以总检的体检信息
	     * @Title: getExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoList2(ExamSummaryModel model,String app_type,UserDTO user,int rows,int page,String sort,String order)throws ServiceException;
	/**
	 * 查询未检收费项目
	     * @Title: getNotExamItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemDTO> getNotExamItem(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 获取往年历史结果
	     * @Title: getResultsHistoryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamSummaryDTO>      
	     * @throws
	 */
	public List<ExamSummaryDTO> getResultsHistoryList(String exam_num,String app_type,UserDTO user) throws ServiceException;
	
	/**
	 * 获取普通科  项目往年 历史结果
	     * @Title: getPtItemResultsHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_info_id
	     * @param: @param item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getPtItemResultsHistory(String exam_num,long item_id) throws ServiceException;
	
	/**
	 * 获取检验科项目 往年历史结果
	     * @Title: getHyItemResultsHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_info_id
	     * @param: @param item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getHyItemResultsHistory(String exam_num,long item_id) throws ServiceException;
	
	/**
	 * 获取 影像科 项目往年历史结果
	     * @Title: getYxItemResultsHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_info_id
	     * @param: @param sample_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getYxItemResultsHistory(String exam_num,long sample_id) throws ServiceException;
	
	/**
	 * 查询体检者体检类型
	     * @Title: getExamInfoAppType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_info_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoAppType(String exam_num, String center_num) throws ServiceException;
	/**
	 * 批量审核
	     * @Title: getBatchAudit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getBatchAudit(ExamSummaryModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 保存阳性发现列表
	     * @Title: saveExamDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamDiseaseList(String exam_num,String app_type,List<ExaminfoDiseaseDTO> listdisease,UserDTO user) throws ServiceException;
	
	/**
	 * 查询问诊症状和既往史列表
	     * @Title: getSymptomsAndHistoryList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepExamResultDTO>      
	     * @throws
	 */
	public List<DepExamResultDTO> getSymptomsAndHistoryList(String exam_num) throws ServiceException;
   /**
    * 
        * @Title: getExamSummaryRejectList   
        * @Description: 获取总检医生的被驳回列表  
        * @param: @param model
        * @param: @param user
        * @param: @param rows
        * @param: @param page
        * @param: @param sort
        * @param: @param order
        * @param: @return
        * @param: @throws ServiceException      
        * @return: PageReturnDTO      
        * @throws
    */
	public PageReturnDTO getExamSummaryRejectList(ExamSummaryModel model, UserDTO user, int rows, int page, String sort,String order)throws ServiceException;
	/**
	 * 
	     * @Title: getFinalDoctorList   
	     * @Description: 获取总检 审核 复审 医生列表  
	     * @param: @param operation_type
	     * @param: @return      
	     * @return: List<UserDTO>      
	     * @throws
	 */
    public List<UserDTO> getFinalDoctorList(String operation_type, String center_num )throws ServiceException;
    /**
	 *  查询总检医生已总检人员列表
	     * @Title: getHasFinalExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO getHasFinalExamInfoList(ExamSummaryModel model, UserDTO user, int rows, int page, String sort,String order)throws ServiceException;
	/**
	 *  已总检获取的体检人信息列表
	     * @Title: getFinalExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public List<ExamInfoUserDTO> getFinalExamInfoList(UserDTO user)throws ServiceException;
	/**
	 *  查询可以下总检并且未总检人数
	     * @Title: getNotFinalCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public ExamSummaryDTO getNotFinalCount(String operation_type, UserDTO user, String iS_EXAM_RESULT_CANFINAL)throws ServiceException;
	/**
	 *  总检获取体检人信息 180医院
	     * @Title: canFinalExamInfo
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfo(String num, UserDTO user) throws ServiceException;

	/**
	 * 总检获取体检人信息 交大附二医院
	     * @Title: canFinalExamInfo2   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param num
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfo2(String num, UserDTO user) throws ServiceException;
	/**
	 * 总检获取体检人信息 东北国际医院
	     * @Title: canFinalExamInfoDbgj   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param num
	     * @param: @param app_type
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfoDbgj(String num, UserDTO user) throws ServiceException;
	
	/**
	 * 查询体检人已获取总检且未主键人数
	     * @Title: finalGainCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public long finalGainCount(UserDTO user) throws ServiceException;
	/**
	 * 汇总总检结论 (西苑医院)
	     * @Title: createFinalExamResultXYYY  
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param isExamSummary
	     * @param: @param isShowRefvalue
	     * @param: @param isShowSamplename
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String createFinalExamResultXYYY(String exam_num, String isExamSummary, String isShowRefvalue,String isShowSamplename, String examResultShowItemId, String examSummaryResultIn, String app_type, String center_num) throws ServiceException;
	/**
	 * 汇总总检结论 (华南附一)
	     * @Title: createFinalExamResultNHFY   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param isExamSummary
	     * @param: @param isShowRefvalue
	     * @param: @param isShowSamplename
	     * @param: @param examResultShowItemId
	     * @param: @param examSummaryResultIn
	     * @param: @param app_type
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String createFinalExamResultNHFY(String exam_num, String isExamSummary, String isShowRefvalue,
			String isShowSamplename, String examResultShowItemId, String examSummaryResultIn, String app_type, String center_num)throws ServiceException;
	/**
	 * 汇总总检结论(交通大学第二附属医院)
	     * @Title: createFinalExamResultERYUAN   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param isExamSummary
	     * @param: @param isShowRefvalue
	     * @param: @param isShowSamplename
	     * @param: @param examResultShowItemId
	     * @param: @param examSummaryResultIn
	     * @param: @param app_type
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String createFinalExamResultERYUAN(String exam_num, String isExamSummary, String isShowRefvalue,
			String isShowSamplename, String examResultShowItemId, String examSummaryResultIn, String app_type, String center_num)throws ServiceException;
	/**
	 * 总检取消
	     * @Title: cancelExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examInfo
	     * @param: @param examSummary
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String cancelExamSummary(ExamInfo examInfo, ExamSummary examSummary, UserDTO user)throws ServiceException;
	/**
	 * 体检号获取总检信息
	     * @Title: canFinalExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfoByExamNum(ExamSummaryModel model, UserDTO user)throws ServiceException;
	/**
	 * 体检号获取总检信息
	     * @Title: canFinalExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canFinalExamInfoByExamNumDbgj(ExamSummaryModel model, UserDTO user)throws ServiceException;
	/**
	 * 审核室随机获取体检信息
	     * @Title: canExaminedExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param num
	     * @param: @param user
	     * @param: @param iS_EXAM_RESULT_CANFINAL
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canExaminedExamInfo(String num, UserDTO user)throws ServiceException;
	/**
	 * 审核室查询已经获取的体检信息
	     * @Title: getExaminedExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public List<ExamInfoUserDTO> getExaminedExamInfoList(String exam_num,UserDTO user,String sort,String order)throws ServiceException;
	/**
	 * 审核室体检号获取体检信息
	     * @Title: canExaminedExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canExaminedExamInfoByExamNum(ExamSummaryModel model, UserDTO user)throws ServiceException;
	
	/**
	 * 审核室体检号获取体检信息
	     * @Title: canExaminedExamInfoByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canExaminedExamInfoByExamNumDbgj(ExamSummaryModel model, UserDTO user)throws ServiceException;
	/**
	 *  查询已审核人员列表
	     * @Title: getExamSummaryApproveList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamSummaryApproveList(ExamSummaryModel model, UserDTO user, int rows, int page,String sort, String order)throws ServiceException;

	public ExamFlowConfig getExamFlogConfig(String exam_num)throws ServiceException;

	public String isCensoringExamSummary(ExamFlowConfig config, UserDTO user)throws ServiceException;

	public String approveExamSummary(ExamSummaryModel model, ExamSummary examSummary, UserDTO user)throws ServiceException;
	/**
	 * 审核保存、终审保存阳性发现列表
	     * @Title: saveExamInfoDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param examSummary
	     * @param: @param user
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String saveExamInfoDiseaseList(ExamSummaryModel model, ExamSummary examSummary, UserDTO user)throws ServiceException;
	/**
	 * 查询未复审列表
	     * @Title: getExamSummaryCensoringWList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamSummaryCensoringWList(ExamSummaryModel model, String is_exam_result_canfinal,UserDTO user, int rows, int page,String sort, String order)throws ServiceException;
	/**
	 * 查询已审核列表
	     * @Title: getExamSummaryCensoringList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamSummaryCensoringList(ExamSummaryModel model, UserDTO user, int rows, int page,
			String sort, String order)throws ServiceException;
	/**
	 * 终审 / 取消
	     * @Title: censoringExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param examSummary
	     * @param: @param user
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String censoringExamSummary(ExamSummaryModel model, ExamSummary examSummary, UserDTO user)throws ServiceException;

	/**
	 *1700 总检一键取消恢复
	     * @Title: saveExamSummaryCancel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryCancel(long id, String exam_num, long cancel_type, UserDTO user)throws ServiceException;

	/**
	 * 查询默认疾病ID
	     * @Title: getDefalutDiseaseList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param defalutDiseaseId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> getDefalutDiseaseList(String exam_num,String isExamSuggest,String defalutDiseaseId,String center_num,String sug_center) throws ServiceException;
	//总检驳回
	public String saveExamSummaryRehectInfo(ExamSummaryModel model, UserDTO user)throws ServiceException;

	/**
	 * 获取体检报告图片地址400
	     * @Title: getYxResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public List<ExamInfoDTO>   gettjbgList(String exam_num,String app_type,String isShowSamplename) throws ServiceException;
	
	/**
	 * 
	        * @Title: getAlreadyRejectList   
	        * @Description: 获取已驳回总检列表
	        * @param: @param model
	        * @param: @param user
	        * @param: @param rows
	        * @param: @param page
	        * @param: @param sort
	        * @param: @param order
	        * @param: @return
	        * @param: @throws ServiceException      
	        * @return: PageReturnDTO      
	        * @throws   
	*/
	public PageReturnDTO getAlreadyRejectList(ExamSummaryModel model, UserDTO user, int rows, int page, String sort,String order)throws ServiceException;
		
	public PageReturnDTO getExamSummaryDistributionList(ExamSummaryModel model,UserDTO user, int rows, int page, String sort,String order) throws ServiceException;
	
	public String saveExamSummaryDistribution(String exam_nums,long doctorid,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getRejectList   
	     * @Description: 得到驳回意见列表  
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TreeDTO      
	     * @throws
	 */
   	public List<FinalRejectionDTO> getRejectList() throws ServiceException;
   	
   	public String saveExamSummaryDistributionByDoctor(String userids,long count,UserDTO user) throws ServiceException;

	public List<ExaminfoDiseaseDTO> createExamInfoCompositeDisease(String exam_num,String center_num,String sug_center)throws ServiceException;
	
	/**
	 * 主检室跟科室显示危急值列表 2301
	     * @Title: getExamCriticalList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param defalutDiseaseId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<CriticalDTO> getExamCriticalList(String exam_num) throws ServiceException;

	public String getBatchRetrial(String exam_num, UserDTO user,String apptype)throws ServiceException;
}
