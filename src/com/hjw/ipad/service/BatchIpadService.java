package com.hjw.ipad.service;

import java.util.List;

import com.hjw.crm.domain.CrmBatchCheck;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchFlowLog;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.ExamSet;
import com.hjw.wst.domain.GroupInfo;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.support.PageSupport;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface BatchIpadService {
	
	/**
	 * 
	     * @Title: GetCreateID   
	     * @Description: 
         * 获取存储过程的id (exam_no:体检号；barcode：条码号；vipno：档案号; lis_order_no:lis用117);
                        pacs:117PACS序号; studyid: 117医院pacs库studyid; code:字典表编码
         * @param types
	     * @return: String      
	     * @throws
	 */
    public String GetCreateID(String types,String center_num);

    /**
	 * 
	     * @Title: getbatchList   
	     * @Description: 得到方案列表  
	     * @param: @param company_Id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getbatchList(long company_Id,String apptype,int pagesize, int pageno,String center_num) throws ServiceException;
	public PageReturnDTO getbatchListSignNum(String sign_num,String apptype,int pagesize, int pageno,String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getbatchList   
	     * @Description: 得到所有方案列表  
	     * @param: @param company_Id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public List<BatchDTO> getbatchList(long company_Id,String center_num) throws ServiceException;

	
	/**
	 * 
	     * @Title: getGroupList   
	     * @Description: 得到分组列表  
	     * @param: @param batch_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getGroupList(long batch_id,int pagesize, int pageno) throws ServiceException;
	
	public PageReturnDTO getCrmGroupList(long batch_id,int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: getGroupListForBatchId   
	     * @Description: 方案获取分组信息  
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupInfoDTO>      
	     * @throws
	 */

	public List<GroupInfoDTO> getGroupListForBatchId(long batch_id) throws ServiceException;
	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过id获取方案 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public Batch getBatchByID(long id) throws ServiceException;
	
	/**
	 * 
	     * <p>Title: getComByFatherID</p>   
	     * <p>通过id获取分组 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public GroupInfo getGroupByID(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveBatch   
	     * @Description:保存方案 
	     * @param: @param batch
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Batch      
	     * @throws
	 */
	public Batch saveBatch(Batch batch) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateBatch   
	     * @Description: 修改方案   
	     * @param: @param batch
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Batch      
	     * @throws
	 */
	public Batch updateBatch(Batch batch) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveGroup   
	     * @Description: 保存分组 
	     * @param: @param gi
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: GroupInfo      
	     * @throws
	 */
	public String saveGroup(GroupInfo gi,List<GroupChargingItemDTO> listitem,List<ExamSetDTO> listset,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateGroup   
	     * @Description: 修改分组  
	     * @param: @param gi
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: GroupInfo      
	     * @throws
	 */
	public String updateGroup(GroupInfo gi,List<GroupChargingItemDTO> listitem,List<ExamSetDTO> listset,String center_num) throws ServiceException;
	
    /**
   	 * 
   	     * @Title: getGroupchargItemList   
   	     * @Description: 得到分组对应体检项目
   	     * @param: @param group_id
   	     * @param: @param pagesize
   	     * @param: @param pageno
   	     * @param: @return
   	     * @param: @throws ServiceException      
   	     * @return: PageReturnDTO      
   	     * @throws
   	 */
   	public PageReturnDTO getGroupchargItemList(long group_id,int pagesize, int pageno) throws ServiceException;
   	
   	/**
   	 * 
   	     * @Title: getGroupList   
   	     * @Description: 得到分组对应套餐
   	     * @param: @param group_id
   	     * @param: @param pagesize
   	     * @param: @param pageno
   	     * @param: @return
   	     * @param: @throws ServiceException      
   	     * @return: PageReturnDTO      
   	     * @throws
   	 */
   	public PageReturnDTO getGroupSetList(long group_id,int pagesize, int pageno,String center_num) throws ServiceException;
   	
   	/**
	 * 
	     * @Title: getGroupList   
	     * @Description: 得到套餐  
	     * @param: @param batch_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
   	public List<TreeDTO> getSetList(String setname,String sex,String appType,String center_num) throws ServiceException;
   	
   	/**
	 * 
	     * <p>Title: getSetByID</p>   
	     * <p>通过id获取套餐 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public ExamSetDTO findSetById(long id,String center_num) throws ServiceException;
    /**
   	 * 
   	     * @Title: getchargItemList   
   	     * @Description: 得到体检项目列表
   	     * @param: @param group_id
   	     * @param: @param pagesize
   	     * @param: @param pageno
   	     * @param: @return
   	     * @param: @throws ServiceException      
   	     * @return: PageReturnDTO      
   	     * @throws
   	 */
//   	public PageReturnDTO getchargItemList(String app_type,String name,String sex,long dep_id ,int pagesize, int pageno) throws ServiceException;
   	
   	/**
	 * 
	     * @Title: getsetForchargItemListBysetnum   
	     * @Description: 通过套餐编号获取收费项目   
	     * @param: @param setnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupChargingItemDTO>      
	     * @throws
	 */
	public List<GroupChargingItemDTO> getsetForchargItemListBysetnum(String setnum,String center_num) throws ServiceException;
	
	/**
	 * 
	     * <p>Title: findSetById</p>   
	     * <p>通过套餐编号获取套餐 </p>   
	     * @param father
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CompanyService#getComByFatherID(java.lang.String)
	 */
	public ExamSetDTO findSetById(String set_num,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: findChargeItemById   
	     * @Description: 收费项目num获取收费项目   
	     * @param: @param item_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSetDTO      
	     * @throws
	 */
	public ChargingItemDTO findChargeItemById(String item_code) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateGroup   
	     * @Description: 修改   
	     * @param: @param gi
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: GroupInfo      
	     * @throws
	 */
	public GroupInfo updateGroup(GroupInfo gi)throws ServiceException;
	
	/**
	 * 
	     * @Title: getbatchproList   
	     * @Description: 获取人员计划信息 
	     * @param: @param barch_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getbatchproList(long barch_id, int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: delBatchProPlanForBatchId   
	     * @Description: batchId 删除方案人员计划
	     * @param: @param batchId
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delBatchProPlanForBatchId(long batchId)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveBatchProPlan   
	     * @Description: 保存   
	     * @param: @param bpp
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveBatchProPlan(BatchProPlan bpp) throws ServiceException;
	
	/**
	 * 
	     * @Title: getFangAnShow   
	     * @Description: 获取方案的所有信息   
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: FangAnShowDTO      
	     * @throws
	 */
	public FangAnShowDTO getFangAnShow(long batch_id,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveContract   
	     * @Description: 保存合同  
	     * @param: @param ct
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Contract      
	     * @throws
	 */
	public Contract saveContract(Contract ct)throws ServiceException;
	
	/**
	 * 
	     * @Title: getContractForBrachId   
	     * @Description: 获取合同信息   
	     * @param: @param barch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ContractDTO      
	     * @throws
	 */
	public ContractDTO getContractForBrachId(long barch_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateContract   
	     * @Description: 修改合同  
	     * @param: @param ct
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Contract      
	     * @throws
	 */
	public Contract updateContract(Contract ct)throws ServiceException;
	
	/**
	 * 
	     * @Title: loadContract   
	     * @Description: 加载合同  
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Contract      
	     * @throws
	 */
	public Contract loadContract(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: contractlistshow   
	     * @Description: 获取合同列表
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO contractlistshow(long comid,long batchid,int pagesize, int pageno) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: contractlistshow   
	     * @Description: 单位id获取合同  
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ContractDTO>      
	     * @throws
	 */
	public List<ContractDTO> contractlistshow(long comid) throws ServiceException;
	
	/**
	 * 
	     * @Title: contractForContractNum   
	     * @Description: 合同编号获取合同
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public ContractDTO contractForContractNum(String contractNum) throws ServiceException;
	
	/**
	 * 
	     * @Title: getContractForContNum   
	     * @Description: 通过合同编号查询合同  
	     * @param: @param barch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ContractDTO      
	     * @throws
	 */
	public ContractDTO getContractForContNum(String contnum) throws ServiceException;
	
	/**
	 * 
	     * @Title: useredBatch   
	     * @Description: 判断批次是否被使用   
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean useredBatch(long batch_id)throws ServiceException;
	
	/**
	 * 
	     * @Title: useredGroup   
	     * @Description: 判断分组是否被使用  
	     * @param: @param group_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean useredGroup(long group_id)throws ServiceException;
	
	/**
	 * 
	     * @Title: checkitemflag   
	     * @Description: 判断是否存在相同的检查项目，检查科室为 131  
	     * @param: @param item_codes
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean checkitemflag(String item_codes,String item_code) throws ServiceException;
	
	/**
	 * 
	     * @Title: getGroupcharItemAmt   
	     * @Description: 获取分组项目总金额   
	     * @param: @param groupid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: double      
	     * @throws
	 */
	public double getGroupcharItemAmt(long groupid)throws ServiceException;
	
	 /**
	 * 
	     * @Title: getCompanyForName   
	     * @Description: 获取有效单位为name的模糊查询
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getCompanyForNameBatch(String name,String apptype,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: examcountforcomid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public int examcountforcomid(long com_id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getbatchForcomidandname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param company_Id
	     * @param: @param batchname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: BatchDTO      
	     * @throws
	 */
	public BatchDTO getbatchForcomidandname(long company_Id,String batchname,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getGroupForbatidandgroupname   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param groupname
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: GroupInfoDTO      
	     * @throws
	 */
	public GroupInfoDTO getGroupForbatidandgroupname(long batch_id,String groupname) throws ServiceException;
	
	/**
	 * 
	     * @Title: copyGroup   
	     * @Description: 拷贝分组   
	     * @param: @param batch_id
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void copyGroup(long batch_id,String ids,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingItemById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ChargingItem getChargingItemById(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoSetById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	/**
	 * 
	     * @Title: getExamSetById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ExamSet getExamSetByNum(String set_num,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingItemBycode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ChargingItem getChargingItemBycode(String item_code) throws ServiceException;
	
	/**
	 * 
	     * @Title: getGroupListForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: GroupInfoDTO      
	     * @throws
	 */
	public GroupInfoDTO getGroupListForId(long batch_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveCrmBatchCheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param crmBatchCheck
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveCrmBatchCheck(CrmBatchCheck crmBatchCheck)throws ServiceException;
	
	public BatchDTO getBatchDTOById(String id)throws ServiceException;
	/**
	 * 根据样本带出项目
	     * @Title: getItemSampleDemo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupChargingItemDTO>      
	     * @throws
	 */
	public List<GroupChargingItemDTO> getItemSampleDemo(long item_id, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: findChargeItemById   
	     * @Description: 收费项目num获取收费项目   
	     * @param: @param item_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamSetDTO      
	     * @throws
	 */
	public ChargingItemDTO findChargeItemById(long item_id) throws ServiceException;
	
	public BatchDTO queryFromBatchExamnumOrExamid(long exam_id, String exam_num,String center_num) throws ServiceException;
	
	public Batch findClass(long id);

	public GroupInfo updateGroupInfo(GroupInfo groupInfo) throws ServiceException;
	
	public int getGroupForbatidOrder(long batch_id) throws ServiceException;
	
	public String setCopybatchToCom(long com_id, long batch_id, int group_order, long userid, String center_num) throws ServiceException;
	
	public BatchDTO getBathByGroupid(long group_id,String center_num) throws ServiceException;
	
	public List<GroupChargingItemDTO> getsetForchargItemListBysetnumNOECI(String setnum,String setTypes,String examnum,String center_num) throws ServiceException;
	
	public BatchFlowLog saveBatchFlowLog(BatchFlowLog bfl) throws ServiceException;
	
	public List<ExamInfoDTO> getExaminfoYforGroupId(long batchid,long groupid) throws ServiceException;
}
