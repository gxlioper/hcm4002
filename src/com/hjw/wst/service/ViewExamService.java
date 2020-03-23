package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.ViewCommonWordsDTO;
import com.hjw.wst.DTO.ViewExamDetailDTO;
import com.hjw.wst.domain.ViewExamDetail;
import com.hjw.wst.model.ViewExamModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 影像科室检查结果功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年11月29日 上午10:05:53   
     * @version V2.0.0.0
 */
public interface ViewExamService {

	/**
	 * 查询体检人本科室的检查项
	     * @Title: getViewExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param depId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ViewExamDetailDTO>      
	     * @throws
	 */
	public List<ViewExamDetailDTO> getViewExamDetail(String exam_num,long depId, String center_num,String app_type) throws ServiceException ;
	
	/**
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
	public PageReturnDTO getExamInfoList(ViewExamModel model,UserDTO user, int rows, int page,String sort,String order) throws ServiceException ;
	
	/**
	 * 保存影像科室检查结果 
	     * @Title: saveViewExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveViewExamDetail(ViewExamModel model,UserDTO user) throws ServiceException;
	
	/**
	 * 根据样本ID查询科室常用词
	     * @Title: getViewExamWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sample_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ViewCommonWordsDTO>      
	     * @throws
	 */
	public List<ViewCommonWordsDTO> getViewExamWords(long sample_id,String cyc) throws ServiceException;
	
	
	/**
	 * 查询所有影像科室体检信息
	     * @Title: getAllViewExamInfoList   
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
	public PageReturnDTO getAllViewExamInfoList(ViewExamModel model,UserDTO user, int rows, int page,String sort,String order) throws ServiceException ;

	/**
	 * 影像科室检查结果更新
	     * @Title: updateViewexamdetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param v
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateViewexamdetail(ViewExamDetail v)throws ServiceException;

	public DepartmentDepDTO getDepartMentDtoByid(long dep_id);

	public String saveViewExamDetailDepNum(ViewExamModel model, UserDTO user);
	
	public List<DepExamResultDTO> getExamResultDTOList(long pacs_id); 

	public String saveViewExamDetailjdbc(ViewExamModel model, UserDTO user) throws ServiceException;
	
	public String saveViewExamDetailDepNumjdbc(ViewExamModel model, UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: queryDeptCharingItemMsg   
	     * @Description: TODO(查询体检人员在该科室下是否有体检信息)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public String queryDeptCharingItemMsg(ViewExamModel model,UserDTO user) throws ServiceException ;
}
