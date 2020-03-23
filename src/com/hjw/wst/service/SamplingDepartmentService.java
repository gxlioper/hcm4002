package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.model.SamplingDepartmentModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 采样室功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年10月16日 下午8:41:51   
     * @version V2.0.0.0
 */
public interface SamplingDepartmentService {

	/**
	 * 获取采样项目列表
	     * @Title: getSamplingDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SampleExamDetailDTO>      
	     * @throws
	 */
	public List<SampleExamDetailDTO> getSamplingDetailList(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 根据条码号查询采样结果表
	     * @Title: getSampleDetailByBarcode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param barCode
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetail      
	     * @throws
	 */
	public SampleExamDetail getSampleDetailByBarcode(String barCode) throws ServiceException;
	
	/**
	 * 采样
	     * @Title: samplingSample   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String samplingSample(SamplingDepartmentModel model,UserDTO user)throws ServiceException;
	
	/**
	 * 取消采样
	     * @Title: canlSampleExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canlSampleExamDetail(SamplingDepartmentModel model,UserDTO user)throws ServiceException;
	
	/**
	 * 根据条码号查询人员信息
	     * @Title: getExamInfoByBarCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param barCode
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public ExamInfoDTO getExamInfoByBarCode(String barCode) throws ServiceException;
	
	/**
	 * 强制合并样本
	     * @Title: mergeSampleDemo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String mergeSampleDemo(SamplingDepartmentModel model) throws ServiceException;
	
	/**
	 * 验证获取用户的打印资源
	     * @Title: verifyUserPrintBarcode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String verifyUserPrintBarcode(SamplingDepartmentModel model,UserDTO user)throws ServiceException;
	
	/**
	 * 拆分样本
	     * @Title: splitUpSampleExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String splitUpSampleExamDetail(SamplingDepartmentModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 获取所选团体人员检验科项目列表(并集)
	     * @Title: getTeamSampleListItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SampleExamDetailDTO>      
	     * @throws
	 */
	public List<SampleExamDetailDTO> getTeamSampleListItem(String ids, String center_num) throws ServiceException;
	
	/**
	 * 保存团体人员管理批量合并样本数据
	     * @Title: saveTeamSampleListItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveTeamSampleListItem(SamplingDepartmentModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 获取本科室需采用和打印条码的项目
	     * @Title: getDepPrintSanpleItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param dep_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SampleExamDetailDTO>      
	     * @throws
	 */
	public List<SampleExamDetailDTO> getDepPrintSanpleItem(String exam_num,long dep_id, String center_num) throws ServiceException;
	
	/**
	 * 科室采样保存
	     * @Title: saveDepSampleItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveDepSampleItem(SamplingDepartmentModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 取消采样 不重新生成条码
	     * @Title: canlSampleLisPacsStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @param type
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String canlSampleLisPacsStatus(long id,String type,UserDTO user)throws ServiceException;
	
}
