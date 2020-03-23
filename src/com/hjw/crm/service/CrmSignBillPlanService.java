package com.hjw.crm.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import com.hjw.crm.DTO.BatchPlanLogDTO;
import com.hjw.crm.DTO.CompanyInfoDTO;
import com.hjw.crm.DTO.CrmCompanyContactsDTO;
import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.BatchPlanLog;
import com.hjw.crm.domain.CrmSignBillPlan;
import com.hjw.crm.model.CrmSignBillPlanModel;
import com.hjw.crm.model.CrmVisitPlanModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Batch;
import com.synjones.framework.exception.ServiceException;

/**
 * 签单计划功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.crm.service   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年4月26日 上午10:09:11   
     * @version V2.0.0.0
 */
public interface CrmSignBillPlanService {
	
	/**
	 * 获取签单计划列表
	     * @Title: getCrmSignBillPlanList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getCrmSignBillPlanList(CrmSignBillPlanModel model,long userid,int page,int pageSize) throws ServiceException,SQLException;

	/**
	 * 单位联系人列表
	     * @Title: getCompanyContactsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List<CrmCompanyContactsDTO>      
	     * @throws
	 */
	public List<CrmCompanyContactsDTO> getCompanyContactsList(long comid) throws ServiceException,SQLException;
	
	/**
	 * 根据单位名称查询单位列表
	     * @Title: getCompanyInfolist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comname
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List<CompanyInfoDTO>      
	     * @throws
	 */
	public List<CompanyInfoDTO> getCompanyInfolist(String comname) throws ServiceException,SQLException;
	
	/**
	 * @throws ParseException 
	 * 新增保存签单计划
	     * @Title: saveSignBillPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveSignBillPlan(CrmSignBillPlanModel model,UserDTO user) throws ServiceException,SQLException, ParseException;
	
	/**
	 * 根据签单计划名称查询签单计划信息
	     * @Title: getSignBillPlanByName   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sign_name
	     * @param: @param user_id
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List<CrmSignBillPlanDTO>      
	     * @throws
	 */
	public List<CrmSignBillPlanDTO> getSignBillPlanByName(String sign_name,long user_id) throws ServiceException,SQLException;
	
	/**
	 * 根据签单计划编码查询签单计划信息
	     * @Title: getSignBillPlanByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sing_num
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: CrmSignBillPlan      
	     * @throws
	 */
	public CrmSignBillPlan getSignBillPlanByNum(String sing_num) throws ServiceException,SQLException;
	
	/**
	 * 修改签单计划信息
	     * @Title: updateSignBillPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param crmSignBillPlan
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: CrmSignBillPlan      
	     * @throws
	 */
	public CrmSignBillPlan updateSignBillPlan(CrmSignBillPlan crmSignBillPlan) throws ServiceException,SQLException;
	/**
	 * 根据单位ID查询单位信息
	     * @Title: getCompanyInfoById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comId
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public CompanyInfoDTO getCompanyInfoById(long comId) throws ServiceException,SQLException;
	public List<CrmSignBillPlan> getCrmSignBill()throws ServiceException,SQLException;
	public List<CrmSignBillPlan> getCrmSignBill(String id)throws ServiceException,SQLException;
	public String editCreater(String id,String createrid)throws ServiceException,SQLException;
	
	/**
	 * 根据用户ID查询该用户行政部门列表
	     * @Title: getUserDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: List<UserDTO>      
	     * @throws
	 */
	public List<UserDTO> getUserDepList(long userId) throws ServiceException,SQLException;
	/**
	 * 获取签单计划日志跟踪
	     * @Title: getBatchPlanDTOList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @param type
	     * @param: @param page
	     * @param: @param rows
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getBatchPlanDTOList(String id,String type, int page, int rows)
			throws ServiceException, SQLException ;
	/**
	 * 保存签单计划日志跟踪
	     * @Title: saveBatchPlanLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchPlanLog
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveBatchPlanLog(BatchPlanLog batchPlanLog) throws ServiceException,SQLException;
	/**
	 * 删除签单计划
	     * @Title: deleteCrmSignBillPlan   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param crmSignBillPlan
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCrmSignBillPlan(CrmSignBillPlan crmSignBillPlan)throws ServiceException,SQLException;
	/**
	 * 检查是否添加行政部门
	     * @Title: checkExamUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkExamUser(String userid)throws ServiceException,SQLException;
	public PageReturnDTO getCountSignBillPlanList(CrmSignBillPlanModel model,long userid, int page, int rows)
			throws ServiceException, SQLException ;
	public PageReturnDTO countSignBillPLanDetailList(CrmSignBillPlanModel model,long userid, int page, int rows)
			throws ServiceException, SQLException ;
	public List<CrmSignBillPlanDTO> tuSignBillPLanList(CrmSignBillPlanModel model, int page, int rows)
			throws ServiceException, SQLException ;
}
