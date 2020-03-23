package com.hjw.crm.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.CrmSignBillPlanDTO;
import com.hjw.crm.domain.CrmBatchCheck;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.GroupChargingItem;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.domain.GroupSet;
import com.hjw.wst.model.BatchModel;
import com.synjones.framework.exception.ServiceException;

public interface CrmBatchCheckService {
	public CrmBatchCheck getCrmBatchCheck(String batch_num,String check_type) throws ServiceException;
	public void addCrmBatchCheck(CrmBatchCheck crmBatchCheck)throws ServiceException;
	public BatchDTO getbatchForcomidandname(long id,String batchname,String sign_num,String getSignBillPlan) throws ServiceException ;
	public List<CrmBatchCheck> getCrmBatchCheck(String batch_id) throws ServiceException;
	public void deleteCrmBatchCheck(long batch_id)throws ServiceException;
	public List<BatchDTO> getBatch(String sign_num,String center_num)throws ServiceException, SQLException;
	public List<BatchDTO> getBatchCrm(String sign_num,String getSignBillPlan)throws ServiceException, SQLException;
	public PageReturnDTO crmcontractlistshow(long sign_num,long batchid,int pagesize, int pageno,String center_num) throws ServiceException;
	public String getSign_name(String sign_num)throws ServiceException;
	public List<CrmSignBillPlanDTO> getSignBillPlan(String sign_name,long creater,int pagesize, int pageno,String getSignBillPlan)throws ServiceException, SQLException;
	public String getSign_num(String id)throws ServiceException, SQLException;
	public Batch getBatchById(String id)throws ServiceException, SQLException;
	public List<GroupInfo> getGroupInfoByBatchId(String batch_id)throws ServiceException, SQLException;
	public void addGroupInfo(GroupInfo groupInfo)throws ServiceException;
	public List<GroupSet> getGroupSetByGroupId(String group_id)throws ServiceException, SQLException;
	public void addGroupSet(GroupSet groupSet)throws ServiceException;
	public List<BatchProPlan> getBatchProPlanByBatchId(String batch_id)throws ServiceException, SQLException;
	public void addBatchProPlan(BatchProPlan batchProPlan)throws ServiceException;
	public List<GroupChargingItem> getGroupChargingItemByBatchId(String batch_id)throws ServiceException;
	public void addGroupChargingItem(GroupChargingItem groupChargingItem)throws ServiceException;
	public String tijiaoShenhe(String id)throws ServiceException;
	public String quxiaoShenhe(String id)throws ServiceException;
	public List<Batch> getBatchByShenhe(String sign_num,String apptype,String getSignBillPlan)throws ServiceException;
	public List<Contract> getContractByBatchId(String batch_id)throws ServiceException;
	/**
	 * 根据批次任务ID和审核类型查询审核信息
	     * @Title: getcrmBatchCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param checktype
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CrmBatchCheck      
	     * @throws
	 */
	public CrmBatchCheck getcrmBatchCheck(long batch_id,String checktype) throws ServiceException;
	
	/**
	 * 更新审核信息
	     * @Title: updateBatchCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param crmBatchCheck
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CrmBatchCheck      
	     * @throws
	 */
	public CrmBatchCheck updateBatchCheck(CrmBatchCheck crmBatchCheck) throws ServiceException;
	
	/**
	 * 根据条件查询批次任务列表
	     * @Title: getcrmBatchCheckManageList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getcrmBatchCheckManageList(BatchModel model, int pagesize, int pageno,String getSignBillPlan) throws ServiceException;
	
	/**
	 * 根据条件查询合同信息
	     * @Title: getcrmBatchContract   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getcrmBatchContract(BatchModel model, int pagesize, int pageno,String getSignBillPlan) throws ServiceException;
}
