package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamResultDetailDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.DTO.UserUseDTO;
import com.hjw.wst.model.ExamResultDetailModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 检验科室功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年12月2日 上午11:13:41   
     * @version V2.0.0.0
 */
public interface ExamResultDetailService {

	/**
	 * 根据体检号 查询体检人的检验科的收费项目
	     * @Title: getCharingItemByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public List<ExamResultDetailDTO> getCharingItemByExamNum(String exam_num,long dep_id,String app_type, String center_num)throws ServiceException;
	
	/**
	 * 根据体检号与收费项目ID查询 检查项目 与检查结果
	     * @Title: getExamResultDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param charing_id
	     * @param: @return      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public List<ExamResultDetailDTO> getExamResultDetail(String exam_num,String item_code)throws ServiceException;
	
	/**
	 * 保存检验结果
	     * @Title: saveExamResultDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String saveExamResultDetail(ExamResultDetailModel model,UserDTO user)throws ServiceException;
	
	/**
	 * 查询检验科室人员列表
	     * @Title: getExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @param rows
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoList(ExamResultDetailModel model,UserDTO user, int rows, int page,String sort,String order) throws ServiceException ;

	/**
	 * 查询体检人收费项目列表
	     * @Title: getInfoItemByIdAndStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @param dep_id
	     * @param: @param exam_status
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getInfoItemByIdAndStatus(String exam_num,long dep_id,String exam_status, String center_num) throws ServiceException ;
	
	/**
	 * 查询检验科室ID
	     * @Title: getDepId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepartmentDepDTO      
	     * @throws
	 */
	public DepartmentDepDTO getDepId( String center_num ) throws ServiceException ;
	
	/**
	 * 影像科室收费项目查询
	     * @Title: getViewExamCharingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public List<ExamResultDetailDTO> getViewExamCharingItem(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 影像科室检查结果查询
	     * @Title: getViewExamResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pace_id
	     * @param: @param charging_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public List<ExamResultDetailDTO> getViewExamResult(long pace_id, long charging_id)throws ServiceException;
	
	/**
	 * 阳性指标查询
	     * @Title: getPositiveFindList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamResultDetailDTO>      
	     * @throws
	 */
	public List<ExamResultDetailDTO> getPositiveFindList()throws ServiceException;
	
	/**
	 * 影像科室检查结果id查询
	     * @Title: getPositiveById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ViewExamDetail      
	     * @throws
	 */
	public ViewExamDetail getPositiveById(long id)throws ServiceException;
	/**
	 * 获取医生检查审核
	     * @Title: getDoctor   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<UserUseDTO>      
	     * @throws
	 */
	public List<UserUseDTO> getDoctor( String center_num ) throws  ServiceException;
}
