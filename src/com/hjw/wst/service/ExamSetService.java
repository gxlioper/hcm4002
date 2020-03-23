package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SetChargingItemDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.SetChargingItem;
import com.hjw.wst.model.ExamSetModel;
import com.hjw.zyb.DTO.ZybOccuhazardClassDTO;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  体检套餐
     * @author: 张瑞    
     * @date:   2016年10月10日 上午10:18:37   
     * @version V2.0.0.0
 */
public interface ExamSetService {
	/**
	 * 
	     * @Title: queryExamSet   
	     * @Description: TODO(获取检查套餐列表)   
	     * @param: @return      
	     * @return: PageDTO      
	     * @throws
	 */
	public PageReturnDTO queryExamSet(ExamSetDTO exam,int pageSize,int page,String intss,String center_num) throws ServiceException;
	public PageReturnDTO queryZybExamSet(ExamSetModel model,int pageSize,int page,String intss,String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: deleteExamSet   
	     * @Description: TODO(逻辑删除体检套餐)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteExamSet(String id,String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: addExamSet   
	     * @Description: TODO(添加体检套餐)   
	     * @param: @param exa
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ExamSet addExamSet(ExamSet exa) throws ServiceException;
	/**
	 * 
	     * @Title: addSetChargingItem   
	     * @Description: TODO(添加体检套餐收费项目和检查套餐关系表)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addSetChargingItem(SetChargingItem item) throws ServiceException;
	/**
	 * 
	     * @Title: findChargingItemId   
	     * @Description: TODO(根据id获取修改界面所需数据)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ExamSet findExamSetId(long id) throws ServiceException;
	/**
	 * 
	     * @Title: getsetChargingItem   
	     * @Description: TODO(体检套餐获取已选择项目)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SetChargingItemDTO>      
	     * @throws
	 */
	public List<SetChargingItemDTO> getsetChargingItem(long id,String center_num) throws  ServiceException;
	/**
	 * 
	     * @Title: deleteSetChargingItem   
	     * @Description: TODO(修改体检套餐--删除已选择项目)   
	     * @param: @param id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteSetChargingItem(long id) throws ServiceException;
	/**
	 * 
	     * @Title: findClass   
	     * @Description: TODO(查找实体类)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ExamSet findEaxmSet(long id) throws ServiceException;
	/**
	 * 
	     * @Title: updateEaxmSet   
	     * @Description: TODO(修改体检套餐)   
	     * @param: @param exam
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateEaxmSet(ExamSet exam) throws ServiceException;
	
	public void updateEaxmSetAll(ExamSet exam, String li) throws ServiceException;
	/**
	 * 
	     * @Title: deletSetChargingItemPl   
	     * @Description: TODO(删除体检套餐---删除已选择项目)   
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deletSetChargingItemPl(String ids) throws ServiceException;
	/**
	 * 
	     * @Title: updateEaxmSetS   
	     * @Description: TODO(修改套餐2)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateEaxmSetS(ExamSet  exam,String li) throws ServiceException;
	/**
	 * ------------------------------------------------职业病-------------------------------
	 */
	/**
	 * 职业危害因素树
	     * @Title: getOccuhazardfactorsTree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam
	     * @param: @param li
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<ZybOccuhazardClassDTO>  getOccuhazardfactorsTree(String name) throws ServiceException;

	/**
	 * 
	     * @Title: updateIsSynchro   更新exam_set是否同步
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateIsSynchro(String ids)throws ServiceException;
	
	
	
	/**
	 * 
	     * @Title: updateIsSynchro   根据ID查询是否有同步的状态
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public boolean queryFromIdIsSynchro(String id)throws ServiceException;
	/**
	 * 
	     * @Title: querySelectExamSettree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam
	     * @param: @param pageSize
	     * @param: @param page
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO querySelectExamSettree(ExamSetDTO exam, String center_num,int pageSize,int page) throws ServiceException;
	
}
