package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SurveyExamInfoDTO;
import com.hjw.wst.DTO.SysQuestionItemsDTO;
import com.hjw.wst.DTO.SysQuestionOptionsDTO;
import com.hjw.wst.DTO.SysSurveyOptionsListDTO;
import com.hjw.wst.DTO.SysSurveyQuestionDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.SysSurveyQuestionModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  问卷题目
     * @author: yangm     
     * @date:   2017年1月17日 下午3:12:01   
     * @version V2.0.0.0
 */
public interface SysSurveyService {
	/**
	 * 
	     * @Title: getSysSurveyQuestionList   
	     * @Description: TODO(获取问卷题目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SysSurveyQuestionDTO>      
	     * @throws
	 */
	public PageReturnDTO  getSysSurveyQuestionList(SysSurveyQuestionModel model,int page,int pageSize) throws ServiceException;
	/**
	 * 
	     * @Title: getSysSurveyChargItemList   
	     * @Description: TODO(获取收费项目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO  getSysSurveyChargItemList(String code,String name,int page,int pageSize) throws ServiceException;
	/**
	 * 
	     * @Title: addSysSurvey   
	     * @Description: TODO(保存问卷问题)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  addSysSurvey(SysSurveyQuestionModel model,UserDTO  user) throws ServiceException;
	/**
	 * 
	     * @Title: getSysSurveyQuestionDTO   
	     * @Description: TODO(修改页面获取数据)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SysSurveyQuestionDTO      
	     * @throws
	 */
	public SysSurveyQuestionDTO  getSysSurveyQuestionDTO(SysSurveyQuestionModel  model) throws ServiceException;
	/**
	 * 
	     * @Title: getSysQuestionItemsDTO   
	     * @Description: TODO(修改页面获取选项数据)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SysQuestionItemsDTO>      
	     * @throws
	 */
	public List<SysQuestionOptionsDTO>  getSysQuestionItemsDTO(SysSurveyQuestionModel  model) throws ServiceException;
	/**
	 * 
	     * @Title: getSysQuestionItemsDTOList   
	     * @Description: TODO(修改页面获取项目数据)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SysQuestionItemsDTO>      
	     * @throws
	 */
	public List<SysQuestionItemsDTO>  getSysQuestionItemsDTOList(String  id)throws ServiceException;
	/**
	 * 
	     * @Title: updateSysSurvey   
	     * @Description: TODO(问卷问题修改方法)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSysSurvey(SysSurveyQuestionModel model,UserDTO  user) throws ServiceException;
	/**
	 * 
	     * @Title: deleteSysSurvey   
	     * @Description: TODO(删除问卷)   
	     * @param: @param model
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSysSurvey(String ids,UserDTO  user) throws ServiceException;
	/**
	 * 
	     * @Title: getSurveyExamInfoDTO   
	     * @Description: TODO(问卷获取体检用户信息)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SurveyExamInfoDTO>      
	     * @throws
	 */
	public  SurveyExamInfoDTO  getSurveyExamInfoDTO(String  exam_num,String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getSysSurveyList   
	     * @Description: TODO(获取问卷问题)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<>      
	     * @throws
	 */
	public  SysSurveyOptionsListDTO  getSysSurveyList(SysSurveyQuestionModel model) throws ServiceException;
	/**
	 * 
	     * @Title: saveQuestionnaireSurvey   
	     * @Description: TODO(提交问卷)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public  void  saveQuestionnaireSurvey(SysSurveyQuestionModel model,UserDTO  user) throws ServiceException;
	
}
