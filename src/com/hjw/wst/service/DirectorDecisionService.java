package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DailyExamInfoDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 主任决策
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年2月16日 上午9:53:52   
     * @version V2.0.0.0
 */
public interface DirectorDecisionService {

	
	/**
	 * @param user 
	 * 查询影像科室人员列表
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
	public PageReturnDTO getExamInfoList(AcceptanceCheckModel model, int rows, int page,String sort,String order, UserDTO user) throws ServiceException ;
	
	/**
	 * 查询体检项目列表
	     * @Title: getDirectorItemStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getDirectorItemStatus(String exam_num, String center_num) throws ServiceException ;
	
	/**
	 * 查询体检套餐列表 
	     * @Title: getDirectorExamSetList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: List<ExaminfoSetDTO>      
	     * @throws
	 */
	public List<ExamSetDTO> getDirectorExamSetList(String center_num) throws ServiceException ;
	
	/**
	 * 查询科室列表
	     * @Title: getDirectorDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getDirectorDepList( String center_num ) throws ServiceException ;
	
	/**
	 * 查询收费项目列表
	     * @Title: getDirectorItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemDTO> getDirectorItemList(long dep_id) throws ServiceException ;
	
	/**
	 * @param user 
	 *  查询体检人项目列表
	     * @Title: getDirectorExamItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDirectorExamItemList(AcceptanceCheckModel model,int rows, int page, UserDTO user) throws ServiceException ;
	
	/**
	 * 查询每日体检者数量与金额列表
	     * @Title: getDilyExamInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DailyExamInfoDTO>      
	     * @throws
	 */
	public List<DailyExamInfoDTO> getDilyExamInfoList(AcceptanceCheckModel model, String center_num) throws ServiceException;
	
	/**
	 * 查询一段检查时间里体检单位结算情况列表
	     * @Title: getTeamAccountList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DailyExamInfoDTO>      
	     * @throws
	 */
	public PageReturnDTO getTeamAccountList(AcceptanceCheckModel model, String center_num,int rows, int page) throws ServiceException;
	
	/**
	 * 按批次ID查询该批次下的体检者信息 
	     * @Title: getTeamSettlementExamList   
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
	public PageReturnDTO getTeamSettlementExamList(AcceptanceCheckModel model, String center_num,int rows, int page,String sort,String order) throws ServiceException;
	
	public PageReturnDTO getExamComprehenList(AcceptanceCheckModel model, String center_num,int rows, int page,String sort,String order) throws ServiceException;
	/**
	 * 根据条件查询体检信息列表-无需总检
	     * @Title: getExamComprehenList   
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
	public PageReturnDTO getExamComprehenListWX(AcceptanceCheckModel model, String center_num,int rows, int page,String sort,String order) throws ServiceException;
	/**
	 * 查询三天未导体检信息列表
	     * @Title: getExamComprehenList   
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
	public PageReturnDTO getExamComprehenListWD(AcceptanceCheckModel model, String center_num,int days,int rows, int page,String sort,String order) throws ServiceException;
	/**
	 * 查询两天未派体检信息列表
	     * @Title: getExamComprehenList   
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
	public PageReturnDTO getExamComprehenListWP(AcceptanceCheckModel model, String center_num,int days,int rows, int page,String sort,String order) throws ServiceException;
	/**
	 * 查询5天未出报告体检信息列表
	     * @Title: getExamComprehenList   
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
	public PageReturnDTO getExamComprehenListWC(AcceptanceCheckModel model, String center_num,int days,int rows, int page,String sort,String order) throws ServiceException;
	/**
	 * 打印流水
	     * @Title: printflowlist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoDTO>      
	     * @throws
	 */
	public List<ExamInfoDTO> printflowlist(long examinfo_id)throws ServiceException;

	public List<ChargingItemDTO> getPacsExamResultList(String exam_num, String s_join_date, String e_join_date)throws ServiceException;

	public List<DepExamResultDTO> getLisItemList(String exam_num, String s_join_date, String e_join_date)throws ServiceException;
}
