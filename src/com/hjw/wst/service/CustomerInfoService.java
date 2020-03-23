package com.hjw.wst.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.hjw.ireport.DTO.DjdbeanList;
import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoSet;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.domain.Introducer;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.ExamInfoModel;
import com.hjw.wst.model.ImpCustomerInfoModel;
import com.hjw.wst.model.RegisterModel;
import com.hjw.zyb.domain.ExamInfoExt;
import com.hjw.zyb.domain.Impoccuhis;
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
public interface CustomerInfoService {
	
	/**
	 * 
	     * @Title: getDiversforByCode   
	     * @Description: 获取设备编号  
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiversInfoDTO      
	     * @throws
	 */
	public DiversInfoDTO getDiversforByCode(String code) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
//	public ExamInfo getExamInfoForId(long id) throws ServiceException;
	public ExamInfo getExamInfoForExamNum(String exam_num) throws ServiceException;
	/**
	 * 
	     * @Title: getExamInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo SaveExamInfo(ExamInfo ei) throws ServiceException;
	/**
	 * 
	     * @Title: getExamInfoForNameList   
	     * @Description: 通过姓名获取人员   
	     * @param: @param examname
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoForNameList(String examname,int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForarchnumList   
	     * @Description:通过arch_num获取人员基本信息   
	     * @param: @param arch_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public ExamInfoDTO getCustExamInfoForexamId(String exam_num) throws ServiceException;
	

	/**
	 * 
	     * @Title: checkIn_num   
	     * @Description: 判断重复的证件号码  
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void checkIn_num(long comid,long batchid,long userid) throws ServiceException;
	/**
	 * 
	     * @Title: checkArch_num   
	     * @Description: 判断档案号重复   
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void checkArch_num(long comid,long batchid,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: checkVisit_no   
	     * @Description: 判断就诊卡号重复，或就诊卡号已存在 
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void checkVisit_no(long comid,long batchid,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getImpCustomerInfoList   
	     * @Description: 获取临时表导入的内容  
	     * @param: @param group_id
	     * @param: @param com_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getImpCustomerInfoList(long batch_id,long com_id,long userid,InfoType info_type,int pagesize,int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveImpCustomerInfoForList   
	     * @Description: 批量保存 ImpCustomerInfo
	     * @param: @param listDTO
	     * @param: @throws ServiceException      
	     * @return: void       
	     * @throws
	 */
	public void saveImpCustomerInfoForList(List<ImpCustomerInfo> listDTO)throws ServiceException;
	
	/**
	 *  
	     * @Title: updateImpCustomerInfo   
	     * @Description:   
	     * @param: @param ii
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateImpCustomerInfo(ImpCustomerInfo ii)throws ServiceException;
	
	/**
	 * 
	     * @Title: delImpCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ii
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delImpCustomerInfo(ImpCustomerInfo ii)throws ServiceException;
	
	/**
	 * 
	     * @Title: loadCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ImpCustomerInfo      
	     * @throws
	 */
	public ImpCustomerInfo loadImpCustomerInfo(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForFlagList   
	     * @Description: 获取所有待导入人员信息   
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public void getExamInfoForFlagList(long comid,long batchid,long userid,String apptype,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForFlagList   
	     * @Description: 选择多个人员导入   
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public void getExamInfoForFlagList(long comids,long batchids,long userid,String ids,String apptype,String center_num) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getExamInfoUserList   
	     * @Description: 综合查询人员、分组信息 
	     * @param: @param company_Id
	     * @param: @param batch
	     * @param: @param deptid
	     * @param: @param group_id
	     * @param: @param id_num
	     * @param: @param art_num
	     * @param: @param username
	     * @param: @param tel
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoUserList(long com_id,long batch,long deptid,long group_id,String id_num,String art_num,String username,String tel,String statas,String regdate,String startDate,String endDate,String employeeID,String sex,String is_marriage,long rylb,String djdstatuss,String center_num,String appytpe,int pagesize, int pageno,String sort,String order, UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo updateExamInfo(ExamInfo ei) throws ServiceException;
	/**
	 * 
	     * @Title: getDjtExamInfobaodaoList   
	     * @Description: 按照身份证号查询未报到人员列表  
	     * @param: @param in_num
	     * @param: @param centerid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfobaodaoList(String in_num,String centerid) throws ServiceException;
	/**
	 * @param user 
	 * 
	     * @Title: getDjtExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param art_num
	     * @param: @param exam_num
	     * @param: @param username
	     * @param: @param starttime
	     * @param: @param enddate
	     * @param: @param centerid
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfoUserList(String data_source, long com_id,long set_id,int ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String jdstatuss,String apptype,String userid,String centerid,RegisterModel model, int pagesize, int pageno,String sort,String order, UserDTO user,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getDjtExamInfoUserListall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param art_num
	     * @param: @param exam_num
	     * @param: @param employeeID
	     * @param: @param status
	     * @param: @param id_num
	     * @param: @param username
	     * @param: @param starttime
	     * @param: @param enddate
	     * @param: @param time3
	     * @param: @param time4
	     * @param: @param createtime
	     * @param: @param examtype
	     * @param: @param apptype
	     * @param: @param userid
	     * @param: @param centerid
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfoUserListall(long com_id,long batch_id,int ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String apptype,String userid,String centerid, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: doAllUserGroup   
	     * @Description: 批量执行存储过程   
	     * @param: @param batchid
	     * @param: @param user_id
	     * @param: @param center_id
	     * @param: @return      
	     * @return: int      
	     * @throws
	 */
	public int doAllUserGroup(long batchid,String user_id,String center_id )throws ServiceException;
	/**
	 * 
	     * @Title: dotUserGroup   
	     * @Description: 部分人员执行存储过程   
	     * @param: @param group_id
	     * @param: @param user_id
	     * @param: @param center_id
	     * @param: @return      
	     * @return: int      
	     * @throws
	 */
	public int dotUserGroup(long group_id,String user_id,String center_id,String examids)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoOne   
	     * @Description:单独体检人员信息录入
	     * @param: @param comids
	     * @param: @param batchids
	     * @param: @param userid
	     * @param: @param impCustomerInfoDTO
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
//	public String getExamInfoOne(long comids,long batchids,long userid,ImpCustomerInfoDTO impCustomerInfoDTO,String center_num) throws ServiceException;
	
	/**
	 * 
	 * @Title: getExamInfoOne @Description:单独体检人员信息录入 @param: @param
	 * comids @param: @param batchids @param: @param userid @param: @param
	 * impCustomerInfoDTO @param: @throws ServiceException @return: void @throws
	 */
	public String getExamInfoOne(long comids, long batchids, long userid,String centerId,ExamInfoUserDTO eu) throws ServiceException;
	/**
	 * 
	     * @Title: delExamInfoForIds   
	     * @Description: 批量删除   
	     * @param: @param comids
	     * @param: @param batchids
	     * @param: @param userid
	     * @param: @param ids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String delExamInfoForIds(String ids,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCenterconfigByKey   
	     * @Description: 通过kye获取   CenterConfigurationDTO 配置
	     * @param: @param keys
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CenterConfigurationDTO      
	     * @throws
	 */
	public CenterConfigurationDTO getCenterconfigByKey(String keys, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: SendApplyChargeItem   
	     * @Description: 发送申请-lis -pacs   
	     * @param: @param batch_id
	     * @param: @param username
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String sendApplyChargeItem(long batch_id,String username,String[] ides, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: batchSigndo   
	     * @Description: 执行签到   
	     * @param: @param exam_ids
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void batchSigndo(String[] exam_ids,long userid,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getPrintDjd   
	     * @Description:  翰思打印导检单   
	     * @param: @param ides
	     * @param: @param djdurl
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DjdbeanList>      
	     * @throws
	 */
	public List<DjdbeanList> getPrintDjd(String ides, String djdurl, long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: setExamInfotime   
	     * @Description: 设置体检有效日期  
	     * @param: @param ides
	     * @param: @param dates
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void setExamInfotime(String ides,String dates,String time1,String time2, long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: setExamInfotime   
	     * @Description: 删除体检有效日期  
	     * @param: @param ides
	     * @param: @param dates
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delExamInfotime(String ides, long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description: 统计时间日期设置人数 
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<CustmTimeCountDTO> countExamInfotime(long com_id,long batch,String center_id,String apptype) throws ServiceException;
	
	/**
	 * 
	     * @Title: getcustomerTypeIdName   
	     * @Description: 根据名称获取人员类型id   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerTypeDTO      
	     * @throws
	 */
	public CustomerTypeDTO getcustomerTypeName(String names) throws ServiceException;
	
	/**
	 * 
	     * @Title: getcustomerTypeIdName   
	     * @Description: 根据名称获取人员类型id   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerTypeDTO      
	     * @throws
	 */
	public CustomerTypeDTO getcustomerTypeId(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getcustomerType   
	     * @Description:获取人员类型的的list   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	public List<JobDTO> getcustomerType() throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getExamInfoUserForExamId   
	     * @Description: 获取身份信息 
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoUserForExamId(String exam_num, UserDTO user)throws ServiceException;
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description: 统计时间日期设置人数 
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String checkExamInfotime(String ids) throws ServiceException;
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description:获取选择人员的性别
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String getIDSExamInfoSex(String ids) throws ServiceException;
	

	/**
	 * 
	     * @Title: getSampleExamDetailforExamid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetailDTO      
	     * @throws
	 */
	public SampleExamDetail getSampleExamDetailforExamid(String exam_num,long sam_id,String status) throws ServiceException;
	
	/**
	 * 
	     * @Title: checkListItem   
	     * @Description: 判断是否有 lis 检查项目 
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
   public int checkListItem(String item_code,String center_num) throws ServiceException;
   
   /**
    * 
        * @Title: checkPacsItem   
        * @Description: 检查pacs
        * @param: @param item_code
        * @param: @return
        * @param: @throws ServiceException      
        * @return: int      
        * @throws
    */
  public int checkPacsItem(String item_code,String center_num) throws ServiceException;
  
  /**
	 * 
	     * @Title: getPacsSummaryDTOforExamid   
	     * @Description: 获取pacs申请主表信息   
	     * @param: @param examinfo_num
	     * @param: @param status
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PacsSummaryDTO      
	     * @throws
	 */
	public PacsSummary getPacsSummaryDTOforExamid(String examinfo_num,long sam_id,String status) throws ServiceException;
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description:获取选择人员的性别
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addIDSExamInfoChangItem(String exam_num, List<GroupChargingItemDTO> listitem,String itemcodes,String exam_indicator,long userid,String username,String centerNum) throws ServiceException;
	
	/**
	 * 
	     * @Title: getItemCodesforExamId   
	     * @Description: 获取所有的itemcode   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  getItemCodesforExamId(String exam_num, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamSetforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamSetDTO>      
	     * @throws
	 */
//	public List<ExamSetDTO> getExamSetforExamId(long examid,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getcustomerTypeIdName   
	     * @Description: 根据名称获取人员类型id   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerTypeDTO      
	     * @throws
	 */
	public CustomerTypeDTO getcustomerTypeId(String id) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemforExamId(String exam_num,String apptype, String center_num)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemforExamIds(String examid, String center_num)throws ServiceException;

	/**
	 * 
	     * @Title: getSampleExamDetailforCharingId   
	     * @Description: 通过收费编码获取SampleExamDetail  
	     * @param: @param charingid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetailDTO      
	     * @throws
	 */
	public List<SampleExamDetailDTO> getSampleExamDetailforCharingId(String examid,String charingCode) throws ServiceException;
	
	/**
	 * 
	     * @Title: getChargingItemForId   
	     * @Description: id获取ChargingItemDTO  
	     * @param: @param chargingid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItemDTO      
	     * @throws
	 */
	public ChargingItemDTO getChargingItemForId(String charingCode) throws ServiceException;
	public ChargingItemDTO getChargingItemForId(long chargingid) throws ServiceException;
	/**
	 * 
	     * @Title: getPacsSummaryforexamNum   
	     * @Description: 体检编号获取pscs信息   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<PacsSummaryDTO>      
	     * @throws
	 */
	public List<PacsSummaryDTO> getPacsSummaryforexamNum(String examNum,String chargItemCode) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: delIDSExamInfoChangItem   
	     * @Description: 根据体检id和收费项目id删除 
	     * @param: @param examid
	     * @param: @param chargingId
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delIDSExamInfoChangItem(String exam_num, String chargingCode,long userid, UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForNameIdmunExamNumList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_Num
	     * @param: @param id_num
	     * @param: @param cusrnum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoForNameIdmunExamNumList(String exam_Num,String id_num,String cusrnum,long com_id,String arch_num,String sex,String tel, int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForNameIdmunExamNumList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_Num
	     * @param: @param id_num
	     * @param: @param cusrnum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public String getCountExamInfoForNameIdmunExamNumList(String exam_Num,String id_num,String cusrnum) throws ServiceException;

	public List<ExamInfoDTO> getExamInfoForIdNumList(String id_num) throws ServiceException;
	
	/**
	 * 插入档案表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public int insertCustomerInfo(Connection connection, CustomerInfoDTO cif) throws Exception;
	
	/**
	 * 修改档案表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public void updateCustomerInfo(Connection connection, CustomerInfoDTO cif) throws Exception;
	
	/**
	 * 插入体检表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public int insertExamInfo(Connection connection, ExamInfoDTO cif) throws Exception;
	
	/**
	 * 插入体检表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public void updateExamInfo(Connection connection, ExamInfoDTO cif) throws Exception;
	
	/**
	 * 
	     * @Title: getExamInfoForexamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public ExamInfoDTO getExamInfoForexamnum(String examnum) throws ServiceException;
		
	public ExaminfoSet updateExaminfoSet(ExaminfoSet eis)throws ServiceException;
	
	public ExaminfoSet saveExaminfoSet(ExaminfoSet eis)throws ServiceException;
	
	public ExaminfoChargingItem updateExaminfoChargingItem(ExaminfoChargingItem eis)throws ServiceException;
	
	public ExaminfoChargingItem saveExaminfoChargingItem(ExaminfoChargingItem eis)throws ServiceException;
	
	public SampleExamDetail saveSampleExamDetail(SampleExamDetail eis)throws ServiceException;
	
	public ExamResultChargingItem saveExamResultChargingItem(ExamResultChargingItem eis)throws ServiceException;
	
	public PacsSummary savePacsSummary(PacsSummary eis)throws ServiceException;
	
	public Pacsdetail savePacsdetail(Pacsdetail eis)throws ServiceException;
	
	/**
	 * 
	 * @Title:
	 * getExamInfoForarchnumList @Description:通过arch_num获取人员基本信息 @param: @param
	 * arch_num @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public List<ExamInfoDTO> getExamInfoForexamNum(String examNum,String id_num,String centerid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForexamNumD   
	     * @Description: 获取需要删除报到体检信息 
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoDTO>      
	     * @throws
	 */
	public ExamInfoDTO getExamInfoForexamNumD(String examNum,String centerid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamIdIds   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemforExamIdIds(String exam_num,String ids, String center_num)throws ServiceException;

	/**
	 * 
	     * @Title: getExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoForNum(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoUserForExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoUserForExamNum(String exam_num,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoForexamNum   
	     * @Description: 按照体检编号获取体检信息  
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public ExamInfoDTO getExamInfoForexamNum(String exam_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateSampleExamEetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleExamEetail(long examid, long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateSampleExamEetaillis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sample_exam_detailid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleExamEetaillis(long sample_exam_detailid, long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getCountExamInfoForNameIdmunList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getCountExamInfoForNameIdmunList(String id_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoUserForCustomerId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoUserForCustomerId(long customerId)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoFreezeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public List<ExamInfoUserDTO> getExaminfoFreezeList(String exam_nums) throws ServiceException;
	/**
	 * 更新导检单打印状态
	     * @Title: updateGuidePrintStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_nums
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateGuidePrintStatus(String exam_nums) throws ServiceException;
	
	/**
	 * 更新条码打印状态
	     * @Title: updateBarcodePrintStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_nums
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateBarcodePrintStatus(String exam_nums) throws ServiceException;
	
	/**
	 * 根据体检信息ID查询需要打印条码的体检项目
	     * @Title: getPrintItemByExamids   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<PacsCountDTO>      
	     * @throws
	 */
	public List<PacsCountDTO> getPrintItemByExamids(String ids, String center_num) throws ServiceException;

	/**
	 * 
	     * @Title: getCustomerInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public CustomerInfo getCustomerInfoForId(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateCustomerInfo(CustomerInfo ei) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getfreeBzExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_id
	     * @param: @param rylb
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param levels
	     * @param: @param freebzstatus
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getfreeBzExamInfoUserList(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,String time1,String time2,String levels,String freebzstatus,long userid,String centernum, int pagesize, int pageno,String sort,String order, UserDTO user,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateExamInfofreezeDo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamInfofreezeDo(String ids, long userid, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateExamInfofUnreezeDo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamInfofUnreezeDo(String ids, long userid, String center_num) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getFreezeTermExamInfoshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_id
	     * @param: @param rylb
	     * @param: @param joinsdate
	     * @param: @param joinedate
	     * @param: @param update
	     * @param: @param levels
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFreezeTermExamInfoshow(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,String joinsdate,String joinedate,String updates,String updatee,String levels,long userid,String centernum, int pagesize, int pageno,String sort,String order, UserDTO user,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getcustomerType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  getcustomerTypeForName(String ids) throws ServiceException;
	
	/**
	 * 
	     * @Title: insertExamInfoExt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @param cif
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void insertExamInfoExt(Connection connection, ExamInfoExt cif) throws Exception,ServiceException;
	
	/**
	 * 
	     * @Title: updateExamInfoExt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @param cif
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void updateExamInfoExt(Connection connection, ExamInfoExt cif) throws Exception,ServiceException;
	
	/**
	 * 
	     * @Title: setExamInfoHYByComID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comdid
	     * @param: @param hy_code
	     * @param: @param userid      
	     * @return: void      
	     * @throws
	 */
	public void setExamInfoHYByComID(long comdid,String hy_code,long userid) throws ServiceException;
	
	/**
	 * 床位分配
	     * @Title: updatebunk   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ex
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoExt      
	     * @throws
	 */
	public ExamInfoExt updatebunk(ExamInfoExt ex)throws ServiceException;
	
	public void savebunk(ExamInfoExt ex)throws ServiceException;
	
	public ExamInfoExt querybyexam_num(String exam_num)throws ServiceException;
	
	public PageReturnDTO getbunkExamInfoUserList(long com_id,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String apptype,String userid,String centerid, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException;
	/**
	 * 判断体检用户是否有套餐或者项目
	     * @Title: getExaminfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public CustomerJsonDTO getExaminfoSet(RegisterModel model)  throws ServiceException;
	/**
	 * 给评测卡上体检信息，返回参数写入病人id
	 */
	public int updatePatientId(String pid, String exam_num) throws ServiceException;
	/**
	 * 
	     * @Title: updatePatientId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param pid
	     * @param: @param exam_info_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getPatientId(long exam_info_id) throws ServiceException;	
	
	/**
	 * 
	     * @Title: InsertWebSynchro   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param type
	     * @param: @param disesId      
	     * @return: void      
	     * @throws
	 */
	public void InsertWebSynchro(char type,String dataid) throws ServiceException;


	public PageReturnDTO getyuyueOrderList(String exam_num,String status,String username, String starttime,String enddate,String examtype,String orderid,String phone,int pagesize, int pageno) throws ServiceException;
	/**
	 * 获取体检信息
	     * @Title: getExamNumGetExamInfoUserDTO   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamNumGetExamInfoUserDTO(String exam_num)  throws ServiceException;
	/**
	 * 个人加项复制个人套餐项目查询
	     * @Title: getcopaitem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupChargingItemDTO>      
	     * @throws
	 */
	public CopyItemDTO  getcopaitem(RegisterModel model,String center_num) throws ServiceException;
	/**
	 * 个人加项复制其他人套餐项目查询
	     * @Title: getcopaitem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<GroupChargingItemDTO>      
	     * @throws
	 */
	public CopyItemDTO  getcopaitemOther(RegisterModel model,String center_num) throws ServiceException;
	
	/**
	 * 获取就诊卡设备
	     * @Title: getDiversforByCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiversInfoDTO      
	     * @throws
	 */
	public DiversInfoDTO getjiuzhenkaCard(String center_num) throws ServiceException;
	/**
	 * 就诊卡号列表
	     * @Title: getjiuzhenka   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param custerm_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	public  List<CustomerVisitCardDTO>  getjiuzhenka(long custerm_id,int page,int pagesize) throws ServiceException;
	/**
	 * 新增就诊卡号
	     * @Title: savejiuzhenka   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param custerm_id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void savejiuzhenka(RegisterModel model,UserDTO user) throws ServiceException;
	/**
	 * 删除就诊卡号
	     * @Title: getjiuzhenka   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param custerm_id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public int deletejiuzhenka(long id) throws ServiceException;
	/*
	 * 
	 */
	public int getvalidatevilit(RegisterModel model) throws ServiceException;
	
	/**
	 * 
	     * @Title: getImpCustomerInfoErrorList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param com_id
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	public List<ImpCustomerInfoDTO> getImpCustomerInfoErrorList(long batch_id, long com_id,long userid)	throws ServiceException;
	
	/**
	 * 
	     * @Title: getBatchAndCom   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: MenuDTO      
	     * @throws
	 */
	public BatchDTO getBatchAndCom(long batchid,long comid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo
	     * @param: @param chargitemid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItemDTO      
	     * @throws
	 */
	public ExaminfoChargingItemDTO getExaminfoChargingItem(String exam_num,String chargitemCode) throws ServiceException;
	
	/**
	 * 
	     * @Title: countUserGroup   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String countUserGroup(long batchid)throws ServiceException;
	
	/**
	 * 根据体检号查询档案信息
	     * @Title: getCustomerinfoByArchNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param arch_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfoDTO      
	     * @throws
	 */
	public CustomerInfoDTO getCustomerinfoByArchNum(String arch_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getImpCustomerInfoErrorList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param com_id
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	public void delImpCustomerInfoErrorList(long batch_id, long com_id,long userid)	throws ServiceException;

	
	/**
	 * 同步信息
	     * @Title: isSynchroExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerInfoDTO      
	     * @throws
	 */
	public String isSynchroExamInfo(String ids) throws ServiceException;
	
	
	/**
	 * 根据id 判断该信息是否需要同步
	     * @Title: queryIsUploadSynach   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public String weiXinIsSynach(String id) throws ServiceException;

	public Introducer saveIntroducer(Introducer introducer) throws ServiceException;
	
	public List<Introducer> queryIntroducerList(String introducer) throws ServiceException;
	
	
	/**
	 * 
	     * @Title: reportUploadExamInfo   
	     * @Description: TODO(同步上传报告)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String reportUploadExamInfo(String ids) throws ServiceException;
	
	public String updateCompanyNameInExamInfo(String companyName, long company_id) throws ServiceException;
	
	/**
	 * 
	     * @Title: djdBuZuoGetList   
	     * @Description: TODO(补做登记获得项目列表)   
	     * @param: @param exam_num
	     * @param: @return      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> djdBuZuoGetList(String exam_num, String center_num);

	public List<UserUseDTO> getUserListByWeb_xtgnid(String xtgn_id)throws ServiceException;

	public List<UserUseDTO> getUserListByRSCode(String rscode)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoYdAddItemList   
	     * @Description: TODO(人员异动新增项目)   
	     * @param: @param com_id
	     * @param: @param batch
	     * @param: @param center_num
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoYdAddItemList(long com_id,long batch,String center_num,int pagesize, int pageno,String sort,String order) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoDelChargingItemforExamId   
	     * @Description: TODO(人员异动)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoDelChargingItemforExamId(String exam_num)throws ServiceException;

	/**
	 * @param user 
	 * 
	     * @Title: getExamInfoYdUserList   
	     * @Description: TODO(人员异动删除)   
	     * @param: @param customer_type
	     * @param: @param com_id
	     * @param: @param batch
	     * @param: @param deptid
	     * @param: @param group_id
	     * @param: @param id_num
	     * @param: @param art_num
	     * @param: @param username
	     * @param: @param tel
	     * @param: @param statas
	     * @param: @param regdate
	     * @param: @param employeeID
	     * @param: @param sex
	     * @param: @param is_marriage
	     * @param: @param rylb
	     * @param: @param djdstatus
	     * @param: @param center_num
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoYdUserList(String customer_type,long com_id,long batch,long deptid,long group_id,String id_num,String art_num,String username,String tel,String statas,String regdate,String employeeID,String sex,String is_marriage,long rylb,String djdstatus,String center_num,int pagesize, int pageno,String sort,String order, UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: getExamInfoYdDelItemList   
	     * @Description: TODO(人员异动 删除体检)   
	     * @param: @param com_id
	     * @param: @param batch
	     * @param: @param center_num
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoYdDelItemList(long com_id,long batch,String center_num,int pagesize, int pageno,String sort,String order) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoYdChargingItemforExamId(String examnum)throws ServiceException;

	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoAddChargingItemforExamId(String examnum)throws ServiceException;

	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebUserInfo     
	     * @throws
	 */
	public UserInfoDTO getUserByUserName(String loginname)throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getExamInfoUserListT   
	     * @Description: TODO(多部门查询)   
	     * @param: @param customer_type
	     * @param: @param com_id
	     * @param: @param batch
	     * @param: @param deptid
	     * @param: @param group_id
	     * @param: @param id_num
	     * @param: @param art_num
	     * @param: @param username
	     * @param: @param tel
	     * @param: @param statas
	     * @param: @param regdate
	     * @param: @param employeeID
	     * @param: @param sex
	     * @param: @param is_marriage
	     * @param: @param rylb
	     * @param: @param djdstatus
	     * @param: @param minage
	     * @param: @param maxage
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoUserListDeptAll(String customer_type, long com_id, long batch, String dept_ids, long group_id,
			String id_num, String art_num,String username, String tel, String statas, String regdate,
			String employeeID, String sex, String is_marriage, long rylb, String djdstatus, int minage,int maxage,String centernum,
			int pagesize, int pageno, String sort, String order, UserDTO user,String center_num) throws ServiceException;
	
	
	
	/**
	 * @param max_age 
	 * @param min_age 
	 * @param is_print 
	 * @param sex_type 
	 * @param time5 
	 * 
	     * @Title: getDjtExamInfoUserListCopyItem   
	     * @Description: TODO()   
	     * @param: @param data_source
	     * @param: @param phone
	     * @param: @param customer_type
	     * @param: @param com_id
	     * @param: @param ren_type
	     * @param: @param art_num
	     * @param: @param exam_num
	     * @param: @param employeeID
	     * @param: @param status
	     * @param: @param id_num
	     * @param: @param username
	     * @param: @param starttime
	     * @param: @param enddate
	     * @param: @param time3
	     * @param: @param time4
	     * @param: @param createtime
	     * @param: @param examtype
	     * @param: @param jdstatuss
	     * @param: @param userid
	     * @param: @param centerid
	     * @param: @param model
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfoUserListCopyItem(String data_source,String phone,String customer_type,long com_id,int ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String jdstatuss,String userid,String centerid,RegisterModel model, int pagesize, int pageno,String sort,String order,String center_num, String time5, String sex_type, String is_print, String min_age, String max_age) throws ServiceException;

	/**
	 * 
	     * @Title: djtExaminfoBuzuo   
	     * @Description: TODO(登记台补做登记)   
	     * @param: @param ids
	     * @param: @param exam_num
	     * @param: @param user
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String djtExaminfoBuzuo(String ids, String exam_num,UserDTO user);
	
	public List<ExaminfoSetDTO> getExamSetForExamNum(String exam_num,String center_num) throws ServiceException;
	
	/**
	 * 判断是否回访
	     * @Title: updateExaminfoIsVisit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateExaminfoIsVisit(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 科室树
	     * @Title: getDepTree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TreeDTO      
	     * @throws
	 */
	public List<TreeDTO> getDepTree(ImpCustomerInfoModel model, String center_num)throws ServiceException;
	/**
	 * 登记台2加项目加项项目显示
	     * @Title: getDJTItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DJTItemDTO>      
	     * @throws
	 */
	public List<DJTItemDTO> getDJTItem(ImpCustomerInfoModel model, String center_num)throws ServiceException;
	/**
	 * 套餐id获取收费项目
	     * @Title: getSetidChagingitem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemDTO> getSetidChagingitem(ImpCustomerInfoModel model,String center_num)throws ServiceException;
	/**
	 * 收费项目id带出查询耗材项目
	     * @Title: getChargingItemConsumables   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemConsumablesItemDTO> getChargingItemConsumables(ImpCustomerInfoModel model,String center_num)throws ServiceException;
	/**
	 * 获取已经加的项目
	     * @Title: getExaminfoChargingItemadd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemadd(ImpCustomerInfoModel model, String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: checkExamInfoRepeat   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param arch_num
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public long checkExamInfoRepeat(long com_id,long batchid,String arch_num,String id_num) throws ServiceException;

	public void update_exam_summary_read_status(String exam_num,UserDTO user);

	public List<ExamInfoDTO> getexamnumByArchnum(String arch_num)throws ServiceException;

	public int addDJDimage(long userid, String djd_path, String exam_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveImpoccuhisInfoForList   
	     * @Description: TODO(插入临时表数据库)   
	     * @param: @param listDTO
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveImpoccuhisInfoForList(List<Impoccuhis> listDTO,long userid,int occuType,int histype)throws ServiceException;
	
	
	
	public void saveImpoccuhisInfoForListJWS(List<Impoccuhis> listDTO,long userid)throws ServiceException;

	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public String setDjtItemTtoG(String exam_num,long userid,String charging_item_codes)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public String setDjtItemGtoT(String exam_num,long userid,String charging_item_codes)throws ServiceException;
	
	/**
	 * 
	     * @Title: updateSampleExamEetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleExamEetail(long examid, long userid,String user_name) throws ServiceException;

	public CustomerInfo getCustomerinfoByExamNum(String exam_num)  throws ServiceException;

	/**
	 * 人员删除之前验证
	     * @Title: delExamInfoCheck   
	     * @Description: TODO()   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String delExamInfoCheck(ExamInfoModel model)throws ServiceException;
	public List<BatchDTO> getbatch(String center_num) throws ServiceException;
	public List<GroupChargingItemDTO> getRelistitem(String exam_num,String apptype) throws ServiceException;

	public String addZYBRecheckChargeItem(String oldExam_num,String exam_num,UserDTO user,String appyype)throws ServiceException;
    /**
     *
     * @param model
     * @return
     * @throws ServiceException
     */
    public ExamInfoUserDTO getNumberOfpeople(RegisterModel model,String app_type,UserDTO user)throws ServiceException;


    public List<ExaminfoChargingItem> getExaminfoChargingItemByExamNum(String exam_num)throws ServiceException;

    /**
     *
     * @Title: djtChargeExamInfoGChangItem
     * @Description:登记台换项
     * @param: @param batch
     * @param: @throws ServiceException
     * @return: void
     * @throws
     */
    public String djtChargeExamInfoGChangItem(ExaminfoSetDTO esdto,List<GroupChargingItemDTO> oldlistitem, List<GroupChargingItemDTO> listitem,String indicator,long userid,String username,String centerNum) throws ServiceException;

    /**
     * 获取缴付项目的个人和团体金额
     * @Title: getExamInfoAmtForCIid
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param examid
     * @param: @param ciids
     * @param: @return
     * @param: @throws ServiceException
     * @return: String
     * @throws
     */
    public ECIAmountDTO getExamInfoAmtForCIid(long examid, String ciids)throws ServiceException;

}


