package com.hjw.wst.service;


import java.util.List;

import com.hjw.wst.DTO.ChargingInvoiceSingleTTDTO;
import com.hjw.wst.DTO.DjtTotalDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.CompanyAccountDTO;
import com.hjw.wst.DTO.CompanyAccountDetailDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TeamAccountAdditionalDTO;
import com.hjw.wst.DTO.TeamAccountDTO;
import com.hjw.wst.DTO.TeamAccountExamListDTO;
import com.hjw.wst.DTO.TeamExaminfoChargingCEDTO;
import com.hjw.wst.DTO.TeamExaminfoChargingItemDTO;
import com.hjw.wst.DTO.TeamWayDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingSummaryGroup;
import com.hjw.wst.domain.ChargingWayGroup;
import com.hjw.wst.domain.ChargingWaySingle;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.TeamAccount;
import com.hjw.wst.model.StatementsModel;
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
public interface TeamAccountService {
	
	/**
	 * 
	     * @Title: getExamFlowForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public PageReturnDTO getTeamAccountForBatch(long batchid,String center_num,int pageno, int pagesize) throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamAccountForFp   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param account_num
	     * @param: @param center_num
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTeamAccountForFp(String account_num,String center_num,int pageno, int pagesize) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: teamAccountExamListShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param center_num
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO teamAccountExamListShow(String acc_num,String center_num,int pageno, int pagesize,String sort,String order, UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: loadTeamAccount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccount      
	     * @throws
	 */
	public TeamAccount loadTeamAccount(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveTeamAccount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ta
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccount      
	     * @throws
	 */
	public TeamAccount saveTeamAccount(TeamAccount ta)throws ServiceException;
	
	/**
	 * 
	     * @Title: updateTeamAccount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ta
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccount      
	     * @throws
	 */
	public TeamAccount updateTeamAccount(TeamAccount ta)throws ServiceException;
	
	/**
	 * 
	     * @Title: findTeamAccountByAccNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param accnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccount      
	     * @throws
	 */
	public TeamAccount findTeamAccountByAccNum(String accnum,String centernum)throws ServiceException;
	
	/**
	 * 
	     * @Title: findTeamAccountByAccNumAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param accnum
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccountDTO      
	     * @throws
	 */
    public ChargingInvoiceSingleTTDTO findTeamAccountByAccNumAll(long barchid,String accnum,String centernum) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getTermExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_num
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTermExamInfoUserList(long com_id,long batchid,String exam_num,long groupid,String time1,String time2,String status,String sex,String username, long set_id,String rylb,int isnotpay,String levels,String tjlx,long userid,String centernum, int pagesize, int pageno,String sort,String order, UserDTO user,String center_num) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: setTTermExamInfoUserListAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_id
	     * @param: @param rylb
	     * @param: @param isnotpay
	     * @param: @param levels
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int setTTermExamInfoUserListAll(String acc_num,long com_id,long batchid,String exam_num,long groupid,String time1,String time2,String status,String sex,String username, long set_id,String rylb,int isnotpay,String levels,String tjlx,long userid,String centernum) throws ServiceException;
	/**
	 * 
	     * @Title: setTeamAccountList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param eulist
	     * @param: @param user
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccountList(String acc_num, List<ExamInfoUserDTO> eulist, long user, String centernum)throws ServiceException;
	
	/**
	 * 
	     * @Title: setTeamAccountList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param eulist
	     * @param: @param user
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccounttsList(String acc_num, List<ExamInfoUserDTO> eulist,List<ExaminfoChargingItemDTO> listitem, long user, String centernum)throws ServiceException;
	
	/**
	 * 
	     * @Title: TermItemCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DjtTotalDTO      
	     * @throws
	 */
	public DjtTotalDTO termItemCount(String acc_num,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: setTeamAccountcountdo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param yjje
	     * @param: @param sjje
	     * @param: @param jmje
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String setTeamAccountcountdo(String acc_num,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamAccountForAccNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccountDTO      
	     * @throws
	 */
	public TeamAccountDTO getTeamAccountForAccNum(String acc_num,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: delTeamAccountForAccNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String delTeamAccountForAccNum(String acc_num,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: delTeamExamForAccNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param ids
	     * @param: @param user
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String delTeamExamForAccNum(String acc_num, String ids, String centernum)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingItemDTO> getExaminfoChargingItemforExamNum(String acc_num,String examnum,String centernum)throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamItemCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DjtTotalDTO      
	     * @throws
	 */
	public DjtTotalDTO getTeamItemCount(String acc_num,String exam_num,String centernum) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforC   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getExaminfoChargingItemforC(String acc_num,String centernum,String sort,String order)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforE   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public PageReturnDTO getExaminfoChargingItemforE(String acc_num,String centernum,String sort,String order,int pageno,int pagesize)throws ServiceException;
	
	/**
	 * 
	     * @Title: searchteamdefamtC   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param listset
	     * @param: @param acc_num
	     * @param: @param decamt
	     * @param: @param center_num
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void searchteamdefamtC(List<TeamExaminfoChargingCEDTO> listset, String acc_num, double decamt,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: searchteamdefamtE   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param listset
	     * @param: @param acc_num
	     * @param: @param decamt
	     * @param: @param center_num
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void searchteamdefamtE(List<TeamExaminfoChargingCEDTO> listset, String acc_num, double decamt,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: teamAccountExamway   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamAccountWayDTO>      
	     * @throws
	 */
	public PageReturnDTO teamAccountExamway(String acc_num, int pageno, int pagesize)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: saveTeamAccountWay   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ta
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccountWay      
	     * @throws
	 */
	public String saveTeamAccountGroup(long id,String account_num,String ids[],String amts[],long userid)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveTeamAccountWay   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ta
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccountWay      
	     * @throws
	 */
	public ChargingWayGroup loadTeamAccountGroup(long id)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveTeamAccountWay   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ta
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccountWay      
	     * @throws
	 */
	public void delTeamAccountWay(ChargingWayGroup ta)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforE   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getnoExaminfoforE(long comid,long batchid,String centernum)throws ServiceException;
	
	/**
	 * 
	     * @Title: getnoExaminfoforC   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getnoExaminfoforC(long comid,long batchid,String centernum)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: getnoExaminfoforCExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param chargeitemid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getnoExaminfoforCExamInfo(long comid, long batchid,long chargeitemid,String centernum)
			throws ServiceException ;
	
	/**
	 * 
	     * @Title: nottermaEChargItemList   
	     * @Description: 未检查人员对应项目列表  
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param chargeitemid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> nottermaEChargItemList(long comid, long batchid,String examnum,String centernum)
			throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamAccountExamListforExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamAccountExamListDTO      
	     * @throws
	 */
	public TeamAccountExamListDTO getTeamAccountExamListforExamNum(String examnum)throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamAccountinvoidForBatch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @param center_num
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTeamAccountinvoidForBatch(long batchid,String center_num,int pageno, int pagesize) throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamAccountForBatchjs   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @param center_num
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTeamAccountForBatchjs(long batchid,String center_num,int pageno, int pagesize) throws ServiceException;
	
	/**
	 * 
	     * @Title: getTeamWayCount   
	     * @Description: 获取支付方式余额   
	     * @param: @param account_num
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: double      
	     * @throws
	 */
	public double getTeamWayCount(long barchid,String account_num,String centernum) throws ServiceException;
	
	/**
	 * 
	     * @Title: getTermExamInfoUserListts   
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
	     * @param: @param isnotpay
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getTermExamInfoUserListts(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,int isnotpay,String levels,String tjlx,long userid,String centernum, int pagesize, int pageno,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: gettrjnTermExamInfoUserList   
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
	public PageReturnDTO gettrjnTermExamInfoUserList(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,String time1,String time2,String levels,long userid,String centernum, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: gettrjndo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public String savetrjndo(String ids,long userid,String center_num) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoFor   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return      
	     * @return: ExamInfo      
	     * @throws
	 */
  public ExamInfo getExamInfoForNum(String exam_num) throws ServiceException;
  
  /**
   * 
       * @Title: geCustomerInfoForId   
       * @Description: TODO(这里用一句话描述这个方法的作用)   
       * @param: @param cust_id
       * @param: @return      
       * @return: CustomerInfo      
       * @throws
   */
  public CustomerInfo getCustomerInfoForId(long cust_id) throws ServiceException;
  
  /**
   * 
       * @Title: gettrjnTermExamInfoUsershow   
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
       * @param: @param accsdate
       * @param: @param accedate
       * @param: @param isprint
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
  public PageReturnDTO gettrjnTermExamInfoUsershow(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,String joinsdate,String joinedate,String accsdate,String accedate,String isprint,String levels,long userid,String centernum, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException;
  
  
  /**
	 * 
	     * @Title: updatetrjndo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updatetrjndo(String ids,long userid,String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: searchteamdefamtP   
	     * @Description: 平均减免  
	     * @param: @param listset
	     * @param: @param acc_num
	     * @param: @param decamt
	     * @param: @param center_num
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void searchteamdefamtP(List<TeamExaminfoChargingCEDTO> listset, String acc_num, double totaldecamt,String center_num) throws ServiceException;
	
	/**
	 * 根据结算单号查询附加费用明细
	     * @Title: getteamaccountaddlist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param acc_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamAccountAdditional>      
	     * @throws
	 */
	public List<TeamAccountAdditionalDTO> getteamaccountaddlist(String acc_num,String itemids) throws ServiceException;
	
	/**
	 * 保存附加费用信息
	     * @Title: saveteamaccountadd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param teamaddlist
	     * @param: @param acc_num
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveteamaccountadd(List<TeamAccountAdditionalDTO> teamaddlist,String acc_num,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveSingleInviockTT   
	     * @Description: 团体打印发票   
	     * @param: @param model
	     * @param: @param userId
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
    public void saveSingleGroupTT(long barchid,String ids,long userId,String center_num,double add_amount) throws ServiceException;
    
    /**
	 * 
	     * @Title: delaccountGroupTT   
	     * @Description: 删除结帐单号
	     * @param: @param barchid
	     * @param: @param account_num
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	 public String delaccountGroupTT(long barchid,String account_num,String userid,String center_num) throws ServiceException;
	 
	 /**
		 * 
		     * @Title: delaccountGroupTT   
		     * @Description: 删除结帐单号
		     * @param: @param barchid
		     * @param: @param account_num
		     * @param: @param userid
		     * @param: @param center_num
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: String      
		     * @throws
		 */
		 public String delHisaccountGroupTT(long barchid,String account_num,String userid,String center_num) throws ServiceException;
	 
	 /**
		 * 
		     * @Title: getChargingSummaryGroupOfaccountnum   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param account_num
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: ChargingSummaryGroup      
		     * @throws
		 */
		public ChargingSummaryGroup getChargingSummaryGroupOfaccountnum(String account_num) throws ServiceException;
		
		 /**
		 * 
		     * @Title: getChargingSummaryGroupOfaccountnum   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param account_num
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: ChargingSummaryGroup      
		     * @throws
		 */
		public ChargingSummaryGroup getChargingSummaryGroupOfId(long id) throws ServiceException;
		
		/**
		 * 
		     * @Title: qrfkaccountGroupTT   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param barchid
		     * @param: @param id
		     * @param: @param userid
		     * @param: @param center_num
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: String      
		     * @throws
		 */
		public String qrfkaccountGroupTT(long barchid,long id,long userid,String center_num) throws ServiceException;
		
		/**
		 * 
		     * @Title: hisSendaccountGroupTT   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param barchid
		     * @param: @param id
		     * @param: @param userid
		     * @param: @param center_num
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: String      
		     * @throws
		 */
		public String hisSendaccountGroupTT(long barchid,long id,long userid,String center_num) throws ServiceException;
		
		/**
		 * 
		     * @Title: teamwayList   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: String      
		     * @throws
		 */
		public List<TeamWayDTO> teamwayList(long id) throws ServiceException;
		
		/**
		 * 
		     * @Title: getExaminfoChargingItemforExamNumId   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param exam_num
		     * @param: @param chargitemid
		     * @param: @param centernum
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: TeamExaminfoChargingItemDTO      
		     * @throws
		 */
		public TeamExaminfoChargingItemDTO getExaminfoChargingItemforExamNumId(String exam_num, String charging_item_code,
				String centernum) throws ServiceException;
		
		/**
		 * 
		     * @Title: queryTeamAccountExamListFromExamid   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param Exam_id
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: TeamAccountDTO      
		     * @throws
		 */
		public TeamAccountExamListDTO queryTeamAccountExamListFromExamid(long exam_id,String exam_num) throws ServiceException;
		 /**
		 * 
		     * @Title: delaccountGroupTT   
		     * @Description: 删除结帐单号
		     * @param: @param barchid
		     * @param: @param account_num
		     * @param: @param userid
		     * @param: @param center_num
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: String      
		     * @throws
		 */
		 public String unHisaccountGroupTT(String account_num,String userid,String center_num) throws ServiceException;
		//团体划账获取商户余额已开发票金额
		 public CompanyAccountDTO getCompanyAccountAmountByComId(StatementsModel model)throws ServiceException;
		//saveDrawAccounts  保存团体划账信息
		 public String saveDrawAccounts(StatementsModel model, UserDTO user)throws ServiceException;
		 
		//团体结算删除结算方式为 商户划账 记录流水 
		public String delDrawAccounts(ChargingSummaryGroup csg, UserDTO user, long id)throws ServiceException;

		public List<ChargingWayGroup> getChargingWayByAccountNum(String account_num)throws ServiceException;

		public com.hjw.wst.domain.DataDictionary getDataDictionaryById(long id)throws ServiceException;
		
		public String checkTteamAccountExamway(String acc_num, int pageno, int pagesize) throws ServiceException;
}
	
