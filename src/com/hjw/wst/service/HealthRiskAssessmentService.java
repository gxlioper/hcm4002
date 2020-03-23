package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.HealthRiskExaminfoDTO;
import com.hjw.wst.DTO.HealthRiskItemExamresultDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface HealthRiskAssessmentService {
	
	/**
	 * 查询体检信息
	     * @Title: getexaminfobyExamnum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public ExamInfoDTO getexaminfobyExamnum(String exam_num) throws ServiceException;
	/**
	 * 提取保存需要评估项目的检查结果
	     * @Title: saveHealthRiskItemExamresult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<HealthRiskItemExamresultDTO>      
	     * @throws
	 */
	public List<HealthRiskItemExamresultDTO> saveHealthRiskItemExamresult(String exam_num) throws ServiceException;
	
	/**
	 * 计算风险评估项目的分数
	     * @Title: createHealthRiskAssessmentReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<HealthRiskExaminfoDTO> createHealthRiskExaminfo(String exam_num,String sex,List<HealthRiskItemExamresultDTO> itemlist) throws ServiceException;
	
	/**
	 * 保存体检人健康评估信息
	     * @Title: saveHealthRiskExaminfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param riskExaminfoList
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveHealthRiskExaminfo(String exam_num,List<HealthRiskExaminfoDTO> riskExaminfoList,UserDTO user) throws ServiceException;
	
	/**
	 * 获取已生成健康风险评估疾病列表
	     * @Title: getHealthRiskAssessmentReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<HealthRiskExaminfoDTO>      
	     * @throws
	 */
	public List<HealthRiskExaminfoDTO> getHealthRiskAssessmentReport(String exam_num) throws ServiceException;
}
