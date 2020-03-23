package com.hjw.wst.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.ExamCriticalClassDTO;
import com.hjw.wst.DTO.ExamCriticalLogicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamCriticalClass;
import com.hjw.wst.domain.ExamCriticalLogic;
import com.hjw.wst.model.ExamCriticalClassModel;
import com.synjones.framework.exception.ServiceException;
/**
 * 疾病逻辑--大类--子类维护
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zr     
     * @date:   2019年7月12日 下午5:11:08   
     * @version V2.0.0.0
 */
public interface ExamCriticalClasslService {
	/**
	 * 
	     * @Title: save   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examCriticalClass      
	     * @return: void      
	     * @throws
	 */
	 void save(ExamCriticalClass entity) throws ServiceException;
	 /**
	  * 
	      * @Title: update   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model      
	      * @return: void      
	      * @throws
	  */
	 void update(ExamCriticalClass entity) throws ServiceException;
	 /**
	  * 
	      * @Title: remove   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model      
	      * @return: void      
	      * @throws
	  */
	 void remove(ExamCriticalClass entity) throws ServiceException;
	 /**
	  * 大类分页查询列表
	      * @Title: queryExamCriticalClass   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @param page
	      * @param: @param pagesize
	      * @param: @param user
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: PageReturnDTO      
	      * @throws
	  */
	 PageReturnDTO queryExamCriticalClass(ExamCriticalClassModel model,int page,int pagesize,UserDTO user) throws ServiceException;
	 /**
	  * 查询大类或者子&类列表列表
	      * @Title: queryExamCriticalClass   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @param model.critical_class_level   1大类   2子类
	      * @param: @param user
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: List<ExamCriticalClassDTO>      
	      * @throws
	  */
	 List<ExamCriticalClassDTO> list(ExamCriticalClassModel model, UserDTO user) throws ServiceException; 
	 /**
	  * id查询类别
	      * @Title: getExamCriticalClassById   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param id
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: ExamCriticalClass      
	      * @throws
	  */
	 ExamCriticalClass getExamCriticalClassById(long id) throws ServiceException;
	 /****************************************************************类别级别危急值逻辑****************************************
	  * 类别危急值逻辑分页查询
	      * @Title: queryCriticalLogic   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @param user
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: PageReturnDTO      
	      * @throws
	  */
	 PageReturnDTO queryCriticalLogic(ExamCriticalClassModel model,int page,int pagesize,UserDTO user)throws ServiceException;
	 /**
	  * 类别级别危急值维护保存
	      * @Title: saveExamCriticalLogic   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @param user
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: String      
	      * @throws
	  */
	 String saveExamCriticalLogic(ExamCriticalClassModel model,UserDTO user) throws ServiceException;
	 /**
	  * 类别级别危急值删除
	      * @Title: removeExamCriticalLogic   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @throws ServiceException      
	      * @return: void      
	      * @throws
	  */
	 void removeExamCriticalLogic(ExamCriticalClassModel model) throws ServiceException;
	 /**
	  *类别危急值逻辑启用停用
	      * @Title: updateExamCriticalLogicIsAcive   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @param user
	      * @param: @throws ServiceException      
	      * @return: void      
	      * @throws
	  */
	 void updateExamCriticalLogicIsAcive(ExamCriticalClassModel model,UserDTO user) throws ServiceException;
	 /**
	  * 类别危急值修改页面信息条件、规则数据封装
	      * @Title: getExamCriticalLogicItemList   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: List<ExamCriticalLogicItemDTO>      
	      * @throws
	  */
	 List<ExamCriticalLogicItemDTO> getExamCriticalLogicItemList(ExamCriticalClassModel model) throws ServiceException;
	 /**
	  * 获取危急值逻辑主表信息
	      * @Title: getExamCriticalLogic   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param model
	      * @param: @return
	      * @param: @throws ServiceException      
	      * @return: ExamCriticalLogic      
	      * @throws
	  */
	 ExamCriticalLogic getExamCriticalLogic(ExamCriticalClassModel model) throws ServiceException;
	 
	 

}
