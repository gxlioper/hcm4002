package com.hjw.wst.service;

import java.util.List;

import com.hjw.crm.DTO.CrmVisitRecordDTO;
import com.hjw.wst.DTO.CriticalDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Critical;
import com.hjw.wst.domain.ExamCriticalLog;
import com.hjw.wst.model.CriticalModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zt     
     * @date:   2016年12月8日 下午2:23:21   
     * @version V2.0.0.0
 */
public interface CriticalService {
	
	public Critical queryById(long id) throws ServiceException;
	
	public PageReturnDTO getCriticalLis(CriticalModel model, int pageno, int pagesize, UserDTO user)throws ServiceException;

	public Critical updateCritical(Critical c) throws ServiceException;

	public void  updaterCritical(long id,UserDTO user) throws ServiceException;

	public String addCritical(Long examinfo_id, Long item_id, String dep_category, long userid, String center_num);

	public String delCritical(Long examinfo_id, Long item_id, String dep_category, long userid);

	public void saveExamCriticalLog(ExamCriticalLog criticalLog)throws ServiceException;

	public PageReturnDTO criticalLogList(CriticalModel model, int rows, int page)throws ServiceException;
	
	public long getgetCriticalNotice() throws ServiceException;
	
	public void saveCritical(Critical c) throws ServiceException;
	/**
	 * 2001东北国际收到添加危机值内容
	     * @Title: getCriticalDBGJPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addCriticalDBGJ(UserDTO user, CriticalModel model)throws ServiceException;
	/**
	 * 东北国际获取历史危急值信息2002
	     * @Title: getCriticalListByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public List<Critical> getCriticalListByExamNum(String exam_num)throws ServiceException;

	public PageReturnDTO getCriticalList(CriticalModel model, int rows, int page, UserDTO user)throws ServiceException;

	public List<Critical> getCriticalByExamNum(String exam_num)throws ServiceException;

	public PageReturnDTO getCriticalHandleListByExamNum(CriticalModel model, int rows, int page)throws ServiceException;

	public String delCriticalById(long id,UserDTO user)throws ServiceException;

	List<CrmVisitRecordDTO> getCriticalHandleListByExamNum(String exam_num, String critical_tactics_num)
			throws ServiceException;
	
	public int getExamIdByExamNum(String exam_num)throws ServiceException;
	
	public Critical queryCritialById(int id)throws ServiceException;
	
	public void delCriticalDetail(Critical critical)throws ServiceException;
	
	public int getCountByClass(String exam_num, long critical_class_parent_id, long critical_class_id, long critical_class_level,long critical_id)throws ServiceException;
	
	public PageReturnDTO queryPageCritrical(CriticalModel model,int pageno, int pagesize,String sort,String order);
	
	public CriticalDTO getCritricalById(long id) throws ServiceException;
	/**
	 * 危急值处理
	     * @Title: updateCriticalVisitPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateCriticalVisitPlan(CriticalModel model,UserDTO user) throws ServiceException;
	/**
	 * 人员基本信息
	     * @Title: getCritricalExamInfoById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CriticalDTO      
	     * @throws
	 */
	public CriticalDTO getCritricalExamInfoById(CriticalModel model)  throws ServiceException;
	
}
