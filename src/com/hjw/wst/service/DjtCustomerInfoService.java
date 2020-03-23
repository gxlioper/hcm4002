package com.hjw.wst.service;

import java.sql.Connection;
import java.util.List;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.DjtTotalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.PacsCountDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.BatchProPlan;
import com.hjw.wst.domain.Contract;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamInfoSign;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoSet;
import com.hjw.wst.domain.GroupInfo;
import com.hjw.wst.model.RegisterModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
 * <菜单、功能、角色操作> <功能详细描述>
 * 
 * @author yangm
 * @version [3.0.0, Nov 4, 2011]
 * @see [相关类/方法]
 * @since [一卡通网站查询系统]
 */
public interface DjtCustomerInfoService {
	

	/**
	 * 
	     * <p>Title: getExamInfoOne</p>   
	     * <p>Description: 单独体检人员信息录入</p>   
	     * @param comids
	     * @param batchids
	     * @param userid
	     * @param centerid
	     * @param eu
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getExamInfoOne(long, long, long, java.lang.String, com.hjw.wst.DTO.ExamInfoUserDTO)
	 */
	public String getDjtExamInfoOne(long userid,String centerid,ExamInfoUserDTO eu)throws ServiceException;
	
	/**
	 * 
	     * @Title: getGroupSetList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param group_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamSetList(String exam_num, int pagesize, int pageno,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: addIDSExamInfoChangItem  
	     * @Description:登记台增加个人人员收费项目
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String djtaddExamInfoDiscountChangItem(String exam_num, List<ExaminfoChargingItemDTO> listitem,String indicator,String IS_CHARGINGWAY_ZERO,long userid,String username,String centerNum) throws ServiceException;

	/**
	 * 
	     * @Title: addIDSExamInfoChangItem  
	     * @Description:团体登记台增加个人人员收费项目
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String djtTaddExamInfoDiscountChangItem(String exam_num, List<ExaminfoChargingItemDTO> listitem,String indicator,String IS_CHARGINGWAY_ZERO,long userid,String username,String centerNum) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: addIDSExamInfoChangItem  
	     * @Description:登记台增加个人人员收费项目
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String djtaddExamInfoGChangItem(ExaminfoSetDTO esdto, List<GroupChargingItemDTO> listitem,List<ExamSetDTO> listset,String indicator,String IS_CHARGINGWAY_ZERO,long userid,String username,String centerNum) throws ServiceException;
	
	/**
	 * 
	     * @Title: djtdelExamInfoGChangItem   
	     * @Description: 登记台减项
	     * @param: @param exam_id
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param username
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtdelExamInfoGChangItem(String exam_num,String ids,long userid, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: djtdelExamInfoGChangItem   
	     * @Description: 登记台减项
	     * @param: @param exam_id
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param username
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtdelExamInfoGChangSet(String exam_num,String ids,long userid,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: djtdelRegister   
	     * @Description: 取消报到 
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtdelRegister(String ids,long userid,String centerid) throws ServiceException;
	
	/**
	 * 
	     * @Title: loadExaminfoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItem      
	     * @throws
	 */
	public ExaminfoChargingItem loadExaminfoChargingItem(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItem>      
	     * @throws
	 */
	public List<ExaminfoChargingItem> getExaminfoChargingItemForId(String exam_num, String center_num)throws ServiceException;
	
	public ExamInfo loadExamInfo(String exam_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: loadExamInfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoSet      
	     * @throws
	 */
	public ExaminfoSet loadExamInfoSet(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getIsnewaddedExamInfoItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getIsnewaddedExamInfoItemForId(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getIsnewaddedExamInfoSetForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getIsnewaddedExamInfoSetForId(long exam_id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoSetForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoSet>      
	     * @throws
	 */
	public List<ExaminfoSet> getExamInfoSetForId(String exam_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: updateItemExam_indicator   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @param cif
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void updateItemExam_indicator(Connection connection, ExamInfoDTO cif) throws Exception;
	
	/**
	 * 
	     * @Title: examInfoStatusEdit   
	     * @Description: 登记台报到
	     * @param: @param examnum
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String examInfoStatusEdit(String examnum,long userid,String centerid) throws ServiceException;
	
	/**
	 * 
	     * @Title: updatevipflag   设置
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid      
	     * @return: void      
	     * @throws
	 */
	public void updatevipflag(String exam_num, String center_num);
	
	/**
	 * 更新外检车信息
	     * @Title: updateOutRegFlag   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param center_num
	     * @param: @param examInfo
	     * @param: @param userid      
	     * @return: void      
	     * @throws
	 */
	public void updateOutRegFlag(String exam_num,String center_num,ExamInfoDTO examInfo,long userid);
	
	/**
	 * 
	     * @Title: getPacsCountList   
	     * @Description: 获取lis 请求   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getPacsCountList(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getLisCountList   
	     * @Description: 获取lis 请求   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getLisCountList(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: djtGdellissamp   
	     * @Description: 删除lis  
	     * @param: @param exam_id
	     * @param: @param sample_exam_detail_id
	     * @param: @param userid
	     * @param: @param centerNum      
	     * @return: void      
	     * @throws
	 */
	public void djtGdellissamp(String exam_num,long sample_exam_detail_id,long userid,String centerNum)throws ServiceException;
	
	/**
	 * 
	     * @Title: getlisitemcode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public String getlisitemcode(String exam_num,String ids, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getdjtTItemCount   
	     * @Description: 登记台团体统计 
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DjtTotalDTO      
	     * @throws
	 */
	public DjtTotalDTO getdjtTItemCount(String exam_num, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getdjtGItemCount   
	     * @Description: 登记台个人统计   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DjtTotalDTO      
	     * @throws
	 */
	public DjtTotalDTO getdjtGItemCount(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getDjtTExamInfoOne   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param centerid
	     * @param: @param eu
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getDjtTExamInfoOne(long userid,String centerid,ExamInfoUserDTO eu)throws ServiceException;
	
	/**
	 * 
	     * @Title: djtChargeExamInfoGChangItem  
	     * @Description:登记台换项
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String djtChargeExamInfoGChangItem(ExaminfoSetDTO esdto,List<GroupChargingItemDTO> oldlistitem, List<GroupChargingItemDTO> listitem,String ypayitemcode,String indicator,long userid,String username,String centerNum) throws ServiceException;

	/**
	 * 
	     * @Title: getIsGroupExamInfoSetForId   
	     * @Description: 获取 已经收费、已经发送申请的项目数量  
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getIsGroupExamInfoSetForId(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForMcNo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mc_no
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfo>      
	     * @throws
	 */
	public List<ExamInfo> getExamInfoForMcNo(String mc_no)throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingSummarySingle   
	     * @Description: 通过体检编号获取未交付人员项目个数   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getChargingSummarySinglecount(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForIdNum   
	     * @Description: 身份证获取未终检的信息   
	     * @param: @param idnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfo>      
	     * @throws
	 */
	public List<ExamInfo> getExamInfoForIdNum(String idnum)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoSign   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoSign      
	     * @throws
	 */
	public ExamInfoSign getExamInfoSign(String examnum) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveExamInfoSign   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoSign      
	     * @throws
	 */
	public ExamInfoSign saveExamInfoSign(ExamInfoSign eis) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateExamInfoSign   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoSign      
	     * @throws
	 */
	public ExamInfoSign updateExamInfoSign(ExamInfoSign eis) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo updateExamInfo(ExamInfo ei)throws ServiceException;
	
	/**
	 * 
	     * @Title: insertexamrepeat   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param newexamid
	     * @param: @param oldexamid
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: int      
	     * @throws
	 */
	public void insertexamrepeat(String newexam_num,String oldexam_num,long userid,String exam_num) throws Exception;
	
	/**
	 * 
	     * @Title: getexamrepeatFornewid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param newexam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public String getexamrepeatFornewid(String newexam_num)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getExamInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfoForId(long examid)throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemDTO> getChargingItemForId(String exam_num, String center_num)throws ServiceException;

	/**
	 * 根据id 判断该信息是否需要同步
	     * @Title: isSynchroExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfoDTO      
	     * @throws
	 */
	public int queryIsUploadSynach(Connection connection,String exam_num) throws ServiceException;
	
	
	/**
	 * 是否存在延期项目
	     * @Title: djtcharingitemYanqi   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtExaminfoBuzuo(String ids, String exam_num, UserDTO user) throws ServiceException;
	
	public List<ExaminfoChargingItem> getBuzuoItem(String oldexam_num,String ids, String center_num)throws ServiceException;
	
	public void buzuoAdditem(String exam_num,String oldexam_num,List<ExaminfoChargingItem> olditemList,UserDTO user)throws ServiceException;

	
	
	public String queryReplaceUser(RegisterModel model) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveReplaceUserData   
	     * @Description: TODO(替检者信息)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveReplaceUserData(RegisterModel model,UserDTO user) throws ServiceException;
	public List<PacsCountDTO> getPacsItemApplicationList(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: djtTpanelG2TItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtTpanelG2TItem(String ids,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: djtTpanelG2TItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtTpanelT2GItem(String ids,long userid) throws ServiceException;
	
	public String changeExaminfoExamType(String exam_num,long exam_id,String exam_type,long company_id,String comname,long batch_id,long group_id,long gtot_exam) throws ServiceException;
	/**
	 *
	 * @Title: getExamInfoForId
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param examid
	 * @param: @return
	 * @param: @throws ServiceException
	 * @return: ExamInfo
	 * @throws
	 */
	public ExamInfo findExamInfoForId(long examid)throws ServiceException;

	public ExamInfo saveExamInfo(ExamInfo eis) throws ServiceException;


}
