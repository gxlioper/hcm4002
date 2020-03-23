package com.hjw.zyb.service;

import java.util.List;

import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.ExaminfoDiseaseDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamSummary;
import com.hjw.wst.model.ExamSummaryModel;
import com.hjw.zyb.DTO.ZybCheckcriterionDTO;
import com.hjw.zyb.DTO.ZybExamSummaryResultDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.DTO.ZyboccucontraindicationDTO;
import com.hjw.zyb.DTO.ZyboccudiseaseDTO;
import com.synjones.framework.exception.ServiceException;

public interface ZybExamSummaryService {
	
	/**
	 * 查看人员信息
	     * @Title: getCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerExamDTO      
	     * @throws
	 */
	public CustomerExamDTO getCustomerInfo(String examNum,UserDTO user) throws ServiceException;
	
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
	public List<ExaminfoDiseaseDTO> createZybExamInfoDiseaseDep(String exam_num, String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 通过疾病逻辑生成阳性发现列表
	     * @Title: createExamInfoDiseaseDep   
	     * @Description: 通过疾病逻辑与疾病归类科室生成阳性发现列表   
	     * @param: @param exam_num
	     * @param: @param siExamDiseaseSeq
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoDiseaseDTO>      
	     * @throws
	 */
	public List<ExaminfoDiseaseDTO> createZybExamInfoDisease(String exam_num, String isExamSuggest, String center_num,String sug_center) throws ServiceException;
	
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
	public List<ExaminfoDiseaseDTO> createNewZybExamInfoDiseaseSingle(String exam_num,String isExamSuggest, String center_num,String sug_center) throws ServiceException;
	
	/**
	 * 查询体检结论列表
	     * @Title: getZybExaminationResultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybexaminationresultDTO>      
	     * @throws
	 */
	public List<ZybexaminationresultDTO> getZybExaminationResultList(String result) throws ServiceException;
	
	/**
	 * 根据职业危害因素查询职业禁忌症列表
	     * @Title: getExamSummaryOccucontraindicationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZyboccucontraindicationDTO>      
	     * @throws
	 */
	public List<ZyboccucontraindicationDTO> getExamSummaryOccucontraindicationList(ExamSummaryModel model) throws ServiceException;
	
	/**
	 *  根据职业危害因素查询职业病信息列表
	     * @Title: getExamSummaryOccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZyboccudiseaseDTO>      
	     * @throws
	 */
	public List<ZyboccudiseaseDTO> getExamSummaryOccudisease(ExamSummaryModel model) throws ServiceException;
	
	/**
	 * 根据职业危害因素查询检查依据信息
	     * @Title: getExamSummaryCheckcriterionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybCheckcriterionDTO>      
	     * @throws
	 */
	public List<ZybCheckcriterionDTO> getExamSummaryCheckcriterionList(ExamSummaryModel model) throws ServiceException;
	
	/**
	 * 总检保存职业病检查结果
	     * @Title: saveZybExamSummaryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybExamSummaryResult(ExamSummaryModel model,long userid) throws ServiceException;
	
	/**
	 * 获取总检保存的职业病检查结果信息
	     * @Title: getZybExamSummaryResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public ZybExamSummaryResultDTO getZybExamSummaryResult(String exam_num) throws ServiceException;
	
	/**
	 * 职业病总检保存
	     * @Title: saveExamSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param examSummary
	     * @param: @param user
	     * @param: @param flag
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybExamSummary(ExamSummaryModel model,ExamSummary examSummary,UserDTO user,String flag) throws ServiceException;
}
