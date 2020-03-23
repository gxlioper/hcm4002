package com.hjw.wst.service;


import java.text.ParseException;
import java.util.List;

import com.hjw.wst.DTO.Customer_TypeDTO;
import com.hjw.wst.DTO.ExamFlowLogDTO;
import com.hjw.wst.DTO.ExamFlowRemakDTO;
import com.hjw.wst.DTO.ExamFlowTotalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSummaryDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Customer_Type;
import com.hjw.wst.domain.ExamFlow;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.ExamFlowModel;
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
public interface ExamFlowService {
	/**
	 * 添加
	     * @Title: addCustomer   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Customer_Type      
	     * @throws
	 */
	public ExamFlow saveExamFlow(ExamFlow ctms) throws ServiceException;;
	
	/**
	 * @param user 
	 * 
	     * @Title: getExamFlowForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public PageReturnDTO getExamFlowForNum(String exam_num,String username,String time1,String time2,long fromacc,String types,String center_num,int pageno, int pagesize, UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamFlowForNumTotal   
	     * @Description: 统计个人  
	     * @param: @param userid
	     * @param: @param startdate
	     * @param: @param enddate
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamFlowTotalDTO>      
	     * @throws
	 */
	public PageReturnDTO getExamFlowForNumTotal(long userid,String actiontype,String startdate,String enddate,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamFlowForNumTotalall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param startdate
	     * @param: @param enddate
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public int getExamFlowForNumTotalall(long userid,String typess,String startdate,String enddate,UserDTO user) throws ServiceException;
	
	/**
	 * @param user 
	 * 
	     * @Title: getExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamFlowInfoForNum(String exam_num,String typess,long userId, UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: queryExamInfo   
	     * @Description: 获取所有收费项目信息   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
		public List<ExaminfoChargingItemDTO> queryExamInfo(String examNum,String types, String center_num) throws ServiceException;

	/**
	 * 
	     * @Title: flowupdateplyq   
	     * @Description: 导检单回收 延期 
	     * @param: @param examid
	     * @param: @param dates
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowupdateplyq(String exam_num,long itemid, UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: insertExamdelete   
	     * @Description: 弃检   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowupdateExam(String exam_num,long itemid, UserDTO user) throws ServiceException;

	/**
	 * 
	     * @Title: insertExamdelete   
	     * @Description: 弃检   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowdeleteExam(String exam_num,long itemid, UserDTO user) throws ServiceException;

	public List<WebUserInfo> getZDSDoctors(long id, String center_num) throws ServiceException;

	public String saveMessage(long userid, long user_id, String remark)throws ServiceException;

	public String flowupdatetx(String exam_num, long id, UserDTO user)throws ServiceException;

	public String guidewuxuzongjian(String exam_num)throws ServiceException;

	public String guidexuyaozongjian(String exam_num)throws ServiceException;
	/**
	 * 
	     * @Title: upd_getReportWay   
	     * @Description: 更新报告领取方式  
	     * @param: @param model
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void upd_getReportWay(ExamFlowModel model, UserDTO user)throws ServiceException;

	public void upd_reportAddress(ExamFlowModel model, UserDTO user)throws ServiceException;

	public void upd_emailAddress(ExamFlowModel model, UserDTO user)throws ServiceException;

	/**
	 * 
	     * @Title: flowexamLogForExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<ExamFlowLogDTO> flowexamLogForExamNum(String exam_num,UserDTO user)throws ServiceException;

	/**
		 * 
		     * @Title: insertExamflowRecovery   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param examNum
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	public String insertExamflowRecovery(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 
	     * @Title: revocationDYD   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String revocationDYD(String exam_num, UserDTO user) throws ServiceException;

	public ExamInfoDTO getNumExamInfo(ExamFlowModel model);

	public ExamFlowRemakDTO getFlowRemakJilu(ExamFlowModel model);

	public PageReturnDTO queryFlowRemak(ExamFlowModel model, int pageSize, int page);

	public void updateFlowRemak(ExamFlowModel model);

	public void saveFlowRemak(ExamFlowModel model);

	/**
	 * @param user 
	 * 
	     * @Title: getFlowExamInfoUserList   
	     * @Description: 导检单回收上传查询信息  
	     * @param: @param exam_num
	     * @param: @param ptype
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFlowExamInfoUserList(ExamFlowModel model, long userid, String centernum, int pagesize, int pageno, UserDTO user)			throws ServiceException;

	/**
	 * 
	     * @Title: flowexamh1insert   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamh1insert(String ids,UserDTO user)throws ServiceException;

	/**
	 * 
	     * @Title: flowexamh1delete   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamh1delete(String ids,UserDTO user)throws ServiceException;

	public List<UserDTO> getflowUser(ExamFlowModel model, String center_num)throws ServiceException;

	/**
	 * @param user 
	 * 
	     * @Title: getFlowExamInfoUserLists   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param ptype
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFlowExamInfoUserLists(ExamFlowModel model,
			long userid, String centernum, int pagesize, int pageno, UserDTO user)
			throws ServiceException;

	/**
	 * 
	     * @Title: insertExamflows0   
	     * @Description: 整单室接收 
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String insertExamflows0(String exam_num, UserDTO user) throws ServiceException;

	public String getWJitemList(String wjItem_dep, String exam_num, String center_num)throws ServiceException;

	/**
	 * 
	     * @Title: insertExamflows0   
	     * @Description: 整单室取消接收   
	     * @param: @param examid
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String insertExamflows0un(String exam_num, UserDTO user) throws ServiceException;

	public String uploadFlow(UserDTO user, String ids)throws ServiceException;

	public String examUploadFlowDel(UserDTO user, String ids)throws ServiceException;

	public String jieshoukeshangchuan(String exam_num, long userid, String center_num)throws ServiceException;

	public String quxiaoshangchuan(String exam_num, long userid, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: flowexampuploadingLists   
	     * @Description: 整单室 报告上传table  可上传
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public PageReturnDTO flowexampuploadingLists(ExamFlowModel model, long userid, String center_num, int rows,int page)throws ServiceException;
	/**
	 * 
	     * @Title: flowexams1insert   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param user
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String flowexams1insert(String ids, UserDTO user)throws ServiceException;
	/**
	 * 
	     * @Title: flowexamh1delete   
	     * @Description: 整单取消上次信息1215 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flowexams1delete(String ids, UserDTO user)throws ServiceException;

	public String flowexamtransfer(String ids,String code, long userid, long newUserid, String center_num)throws ServiceException;

	public PageReturnDTO getPre_receive_zongjian(ExamFlowModel model, long userid, String center_num, int rows,int page)throws ServiceException;

	public PageReturnDTO getPre_receive_details_zongjian(ExamFlowModel model, long userid, String center_num, int rows,int page)throws ServiceException;
	/**
	 * 
	     * @Title: insertExamflowz   
	     * @Description: 报告室 批量核收导检单 
	     * @param: @param parseLong
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String insertExamflowz(String exam_num, UserDTO user)throws ServiceException;

	public PageReturnDTO getFlowExamInfoUserListz(ExamFlowModel model, long userid, String center_num, int rows,int page, UserDTO user)throws ServiceException;

	
	public PageReturnDTO getFlowExamInfoUserListz_dbgj(ExamFlowModel model, long userid, String center_num, int rows,int page, UserDTO user)throws ServiceException;

	
	public String insertExamflowzun(String exam_num, UserDTO user)throws ServiceException;
	/**
	 * 
	     * @Title: flowexamsendchk   
	     * @Description: 报告发送审核  
	     * @param: @param ids
	     * @param: @param user
	     * @param: @param doctor_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowexamsendchk(String ids, UserDTO user, long c0createrid)throws ServiceException;

	public boolean checkHealthDepId(long id, String hEALTH_GET_DEP, String center_num)throws ServiceException;

	public String insertExamflowp(String exam_num, UserDTO user, String upload_flow)throws ServiceException;

	public String insertExamflowp0(String exam_num, UserDTO user)throws ServiceException;

	public String check_print(String exam_num)throws ServiceException;

	public String insertExamflowp0un(String exam_num, UserDTO user)throws ServiceException;

	public PageReturnDTO getFlowExamInfoUserListp(ExamFlowModel model, long userid, String center_num, int rows,int page, String sort, String order, UserDTO user)throws ServiceException;

	public String flowexamp1insert(String ids, UserDTO user)throws ServiceException;

	public String flowexamp1delete(String ids, UserDTO user)throws ServiceException;

	public PageReturnDTO getPre_receive(ExamFlowModel model, long userid, String center_num, int rows, int page)throws ServiceException;

	public PageReturnDTO getPre_receive_details(ExamFlowModel model, long userid, String center_num, int rows,int page)throws ServiceException;

	public JobDTO insertExamflowe0(String exam_num, UserDTO user)throws ServiceException;
	
	public JobDTO insertExamflowe0_dbgj(String exam_num, UserDTO user)throws ServiceException;

	public String insertExamflowe0un(String exam_num, UserDTO user)throws ServiceException;

	public PageReturnDTO getFlowExamInfoUserListe(ExamFlowModel model, long userid, String center_num, int rows,int page, UserDTO user)throws ServiceException;

	public PageReturnDTO flowexameListe_allflow(ExamFlowModel model, long userid, String center_num, int rows,int page, UserDTO user)throws ServiceException;

	public List<ExamInfoDTO> queryExamInfoHasCTMR(String exam_num, String center_num)throws ServiceException;

	public String saveEdesc(String exam_num, String edesc, UserDTO user)throws ServiceException;

	public String flowexame1insert(String exam_num, UserDTO user)throws ServiceException;

	public String flowexame1delete(String exam_num, UserDTO user)throws ServiceException;
	
	/**
	 * 
	     * @Title: getFlowExamInfoUserListm   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param ptype
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFlowExamInfoUserListm(ExamFlowModel model, 
			long userid, String centernum, int pagesize, int pageno)
			throws ServiceException;
	
	/**
	 * 
	     * @Title: flowmmessageshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param mtype
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowmmessageshow(String exam_num,String mtype,UserDTO user)throws ServiceException;
	
	/**
	 * 
	     * @Title: flowmmessageUpdate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param mtype
	     * @param: @param notes
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowmmessageUpdate(String exam_num,String mtype,String notes,UserDTO user)throws ServiceException;
	
	/**
	 * 
	     * @Title: getFlowExamDoc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param mtype
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<JobDTO> getFlowExamDoc(UserDTO user)throws ServiceException;
	
	/**
	 * 
	     * @Title: flowexameListzjbg   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param docid
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoUserDTO>      
	     * @throws
	 */
	public List<ExamInfoUserDTO> flowexameListzjbg(long docid,String exam_num,long userid, String centernum,UserDTO user)
			throws ServiceException;
	
	public String saveAcceptanceExamInfoFlow(String exam_num, long acceptance_check, UserDTO user)
			throws ServiceException;
	/**
	 * 报告取消撤销审核
	     * @Title: flowexambackchk   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String flowexambackchk(String exam_num,UserDTO user) throws ServiceException;
	
	/**
	 * 预览室工作量查询
	     * @Title: getFlowExamPreviewCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public ExamSummaryDTO getFlowExamPreviewCount(UserDTO user) throws ServiceException;
	//满足条件客户添加回访策略
	public String addTactics(ExamInfo examInfo, UserDTO user, String tactics_num)throws ServiceException, ParseException;
}
	
