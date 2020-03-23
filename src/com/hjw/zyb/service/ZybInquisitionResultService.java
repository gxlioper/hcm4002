package com.hjw.zyb.service;

import java.util.List;

import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.zyb.DTO.ZybAskDiagnosisSampleDTO;
import com.hjw.zyb.model.ZybInquisitionResultModel;
import com.synjones.framework.exception.ServiceException;

public interface ZybInquisitionResultService {

	/**
	 * 查询问诊科室人员列表
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
	public PageReturnDTO getExamInfoList(ZybInquisitionResultModel model,UserDTO user, int rows, int page,String sort,String order) throws ServiceException;
	
	/**
	 * 查询人员问诊项目列表
	     * @Title: getZybInquisitionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybInquisitionResultDTO>      
	     * @throws
	 */
	public List<ZybAskDiagnosisSampleDTO> getZybInquisitionList(long examinfo_id) throws ServiceException;
	
	/**
	 * 保存问诊项目结果信息
	     * @Title: saveZybInquisitionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybInquisitionList(ZybInquisitionResultModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 查询体检信息
	     * @Title: getExaminfoById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExaminfoById(long examinfo_id) throws ServiceException;
}
