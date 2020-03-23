package com.hjw.zyb.service;

import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.PacsSummaryDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoSet;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.model.RegisterModel;
import com.hjw.zyb.DTO.ExamInfoExtDTO;
import com.hjw.zyb.DTO.ZybExamInfoUserDTO;
import com.hjw.zyb.DTO.ZybExamOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybOccuHisDTO;
import com.hjw.zyb.domain.ZybExamOccuhazardfactors;
import com.hjw.zyb.domain.ZybOccuHis;
import com.hjw.zyb.model.ZybImpCustomerInfoModel;
import com.hjw.zyb.model.ZybInquisitionResultModel;
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
public interface ZybCustomerInfoService {
	
	/**
	 * 
	     * @Title: getCustOccuHisList   
	     * @Description: 获取职业病历史   
	     * @param: @param exam_num
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getCustOccuHisList(String isradiation,String exam_num,int pagesize, int pageno) throws ServiceException;
	
	/**
	 * 
	     * <p>Title: getExamInfoOne</p>   
	     * <p>Description: 职业病单独体检人员信息录入</p>   
	     * @param comids
	     * @param batchids
	     * @param userid
	     * @param centerid
	     * @param eu
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getExamInfoOne(long, long, long, java.lang.String, com.hjw.wst.DTO.ExamInfoUserDTO)
	 */
	public String getExamInfoOne(long comids, long batchids, long userid,String centernum,ZybExamInfoUserDTO eu)throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoUserForExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybExamInfoUserDTO      
	     * @throws
	 */
	public ZybExamInfoUserDTO getExamInfoUserForExamId(String exam_num,String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybOccuHis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public ZybOccuHis saveZybOccuHis(ZybOccuHis zybhis)throws ServiceException;
	public ZybOccuHis updateZybOccuHis(ZybImpCustomerInfoModel model,UserDTO user)throws ServiceException;
	public ZybOccuHisDTO getZybOccuHisFinID(ZybImpCustomerInfoModel model)throws ServiceException;
	
	/**
	 * 
	     * @Title: longZybOccuHis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public ZybOccuHis longZybOccuHis(String id)throws ServiceException;
	
	/**
	 * 
	     * @Title: delZybOccuHis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delZybOccuHis(ZybOccuHis zybhis)throws ServiceException;
	
	/**
	 * 
	     * @Title: getexamOcchazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybExamOccuhazardfactorsDTO>      
	     * @throws
	 */
	public List<ZybExamOccuhazardfactorsDTO> getexamOcchazardfactors(String exam_num)throws ServiceException;
	
	PageReturnDTO getexamOcchazardfactors(String exam_num, int pagesize, int pageno)throws ServiceException;
	
	/**
	 * 
	     * @Title: getzywhlblist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getzywhlblist()throws ServiceException;
	
	/**
	 * 
	     * @Title: getzywhlblist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getzywhyslblist(String hazardclassID)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybExamOccuhazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public ZybExamOccuhazardfactors saveZybExamOccuhazardfactors(ZybExamOccuhazardfactors zybhis,UserDTO user,String app,String center_num)throws ServiceException;
	
	
	/**
	 * 
	     * @Title: delZybExamOccuhazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public void delZybExamOccuhazardfactors(ZybExamOccuhazardfactors zybhis)throws ServiceException;
	
	/**
	 * 
	     * @Title: getzybphysicalexaminationclasslist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getzybphysicalexaminationclasslist()throws ServiceException;
	
	/**
	 * 
	     * @Title: longZybExamOccuhazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybExamOccuhazardfactors      
	     * @throws
	 */
	public ZybExamOccuhazardfactors longZybExamOccuhazardfactors(String id)throws ServiceException;
	
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
	public List<ExaminfoChargingItemDTO> getZybExaminfoChargingItemAllforExamId(String exam_num, String center_num)throws ServiceException;
	
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
//	public List<ExaminfoChargingItemDTO> getZybExaminfoChargingItemforExamId(long examid)throws ServiceException;
	
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
	public PageReturnDTO getZybExamSetAllList(String exam_num, int pagesize, int pageno,String center_num) throws ServiceException;
	
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
	public PageReturnDTO getZybExamSetList(String exam_num,String app_type, int pagesize, int pageno,String center_num) throws ServiceException;
	
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
	public ExamInfoDTO getZybCustExamInfoForexamId(String exam_num) throws ServiceException;
	
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
	public List<ExaminfoSet> getZybExamInfoSetForId(String exam_num)throws ServiceException;
	
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
	public int getZybIsnewaddedExamInfoSetForId(long exam_id)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybExaminfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoSet      
	     * @throws
	 */
	public ExaminfoSet saveZybExaminfoSet(ExaminfoSet eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: getZybExaminfoChargingItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItem>      
	     * @throws
	 */
	public List<ExaminfoChargingItem> getZybExaminfoChargingItemForId(String exam_num, String center_num)throws ServiceException;
	
	/**
	 * 
	     * @Title: getZybIsnewaddedExamInfoItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getZybIsnewaddedExamInfoItemForId(String exam_num, String center_numd)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybExaminfoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItem      
	     * @throws
	 */
	public ExaminfoChargingItem saveZybExaminfoChargingItem(ExaminfoChargingItem eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveSampleExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetail      
	     * @throws
	 */
	public SampleExamDetail saveZybSampleExamDetail(SampleExamDetail eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybExamResultChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamResultChargingItem      
	     * @throws
	 */
	public ExamResultChargingItem saveZybExamResultChargingItem(ExamResultChargingItem eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybPacsSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PacsSummary      
	     * @throws
	 */
	public PacsSummary saveZybPacsSummary(PacsSummary eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: saveZybPacsdetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Pacsdetail      
	     * @throws
	 */
	public Pacsdetail saveZybPacsdetail(Pacsdetail eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: checkZybListItem   
	     * @Description: 判断是否有 lis 检查项目 
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
   public int checkZybListItem(String item_code,String center_num ) throws ServiceException;
   
   /**
    * 
        * @Title: checkZybPacsItem   
        * @Description: 检查pacs
        * @param: @param item_code
        * @param: @return
        * @param: @throws ServiceException      
        * @return: int      
        * @throws
    */
  public int checkZybPacsItem(String item_code,String center_num ) throws ServiceException;
  
  /**
	 * 
	     * @Title: getZybSampleExamDetailforExamid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetailDTO      
	     * @throws
	 */
	public SampleExamDetail getZybSampleExamDetailforExamid(String exam_num,long samp_id,String status) throws ServiceException;
	
	/**
	 * 
	     * @Title: getZybPacsSummaryDTOforExamid   
	     * @Description: 获取pacs申请主表信息   
	     * @param: @param examinfo_num
	     * @param: @param status
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PacsSummaryDTO      
	     * @throws
	 */
	public PacsSummary getZybPacsSummaryDTOforExamid(String examinfo_num,long sam_id,String status) throws ServiceException;
	
	/**
	 * 
	     * @Title: zybaddExamInfoChangItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param esdto
	     * @param: @param listitem
	     * @param: @param listset
	     * @param: @param indicator
	     * @param: @param userid
	     * @param: @param username
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String zybaddExamInfoChangItem(ExaminfoSetDTO esdto, List<GroupChargingItemDTO> listitem,
			List<ExamSetDTO> listset, String indicator,String IS_CHARGINGWAY_ZERO, long userid, String username, String centerNum)
			throws ServiceException;
	
	/**
	 * 
	     * @Title: getExamInfoUserForExamId2   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybExamInfoUserDTO      
	     * @throws
	 */
	public ZybExamInfoUserDTO getExamInfoUserForExamId2(String exam_num,String center_num)throws ServiceException;
	
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
	public String zybdelExamInfoGChangSet(String exam_num,String ids,long userid,String centerNum) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateZybExaminfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoSet      
	     * @throws
	 */
	public ExaminfoSet updateZybExaminfoSet(ExaminfoSet eis)throws ServiceException;
	
	/**
	 * 
	     * @Title: zybcheckExamInfotime   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String zybcheckExamInfotime(String ids) throws ServiceException;
	
	 /**
	   * 
	       * @Title: addIDSExamInfoChangItem   
	       * @Description: TODO(这里用一句话描述这个方法的作用)   
	       * @param: @param examid
	       * @param: @param listitem
	       * @param: @param itemcodes
	       * @param: @param exam_indicator
	       * @param: @param userid
	       * @param: @param username
	       * @param: @param centerNum
	       * @param: @throws ServiceException      
	       * @return: void      
	       * @throws
	   */
	 public void addIDSExamInfoChangItem(String exam_num, List<GroupChargingItemDTO> listitem,String itemcodes,String exam_indicator,long userid,String username,String centerNum) throws ServiceException;
	 
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
    public PacsSummary getPacsSummaryDTOforExamid(String examinfo_num, long sam_id, String status)throws ServiceException;
    
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
	  public int checkPacsItem(String item_code,String center_num ) throws ServiceException;
	  
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
		public SampleExamDetail getSampleExamDetailforExamid(String exam_num,long samp_id,String status) throws ServiceException;
		
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
	   public int checkListItem(String item_code,String center_num ) throws ServiceException;
	   
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
		      * @Title: delZybIDSExamInfoChangItem   
		      * @Description: TODO(这里用一句话描述这个方法的作用)   
		      * @param: @param examid
		      * @param: @param chargingId
		      * @param: @param types
		      * @param: @param userid
		      * @param: @throws ServiceException      
		      * @return: void      
		      * @throws
		  */
		public void delZybIDSExamInfoChangItem(String examnum, long chargingId, long userid, String center_num)
				throws ServiceException;
		
		/**
		   * 
		       * @Title: checkExamChargItem   
		       * @Description: TODO(这里用一句话描述这个方法的作用)   
		       * @param: @param examid
		       * @param: @param chargitemid
		       * @param: @param type
		       * @param: @return
		       * @param: @throws ServiceException      
		       * @return: int      
		       * @throws
		   */
		  public int checkExamChargItem(String examnum,long chargitemid, String center_num) throws ServiceException;
		  
		 	
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
			public List<PacsSummaryDTO> getPacsSummaryforexamNum(String examNum,long chargid) throws ServiceException;
			
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
			public List<SampleExamDetailDTO> getSampleExamDetailforCharingId(String examnum,long charingid) throws ServiceException;
			
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
			public ChargingItemDTO getChargingItemForId(long chargingid) throws ServiceException;
			
			/**
			 * 
			     * @Title: getExamInfoUserForExamId   
			     * @Description: 获取身份信息 
			     * @param: @param examid
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: ExamInfoUserDTO      
			     * @throws
			 */
			public ExamInfoUserDTO getExamInfoallUserForExamId(String examnum,String center_num )throws ServiceException;
			
			/**
			 * 
			     * @Title: getExamInfoExtForExamNum   
			     * @Description: TODO(这里用一句话描述这个方法的作用)   
			     * @param: @param examnum
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: ExamInfoExt      
			     * @throws
			 */
			public ExamInfoExtDTO getExamInfoExtForExamNum(String examnum) throws ServiceException;
			
			/**
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
			public PageReturnDTO getDjtExamInfoUserList(long com_id,long batch_id,long set_id,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String apptype,String userid,String centernum, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException;
			
			/**
			 * 
			     * @Title: saveOrUpdateExamInfoExt   
			     * @Description: 保存体检扩展信息表   
			     * @param: @param eu
			     * @param: @param userid
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: String      
			     * @throws
			 */
			public String saveOrUpdateExamInfoExt(ZybExamInfoUserDTO eu, long userid) throws ServiceException;
			/**
			 * 通过危害因素和职业体检类型获取套餐
			     * @Title: getOccuhazardfactorsExamSet   
			     * @Description: TODO(这里用一句话描述这个方法的作用)   
			     * @param: @param weihai
			     * @param: @param tjlx
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: ExamSetDTO      
			     * @throws
			 */
			public ExamSetDTO getOccuhazardfactorsExamSet (String weihai,String tjlx,String center_num) throws ServiceException;
			/**
			 * 体检号检索人员信息
			     * @Title: getExamInfoFindExamNum   
			     * @Description: TODO(这里用一句话描述这个方法的作用)   
			     * @param: @param model
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: ExamInfoDTO      
			     * @throws
			 */
			public ExamInfoDTO getExamInfoFindExamNum (ZybInquisitionResultModel model) throws ServiceException;
			
			
			/**
			 * 宝马岗前体检信息插入
			     * @Title: savezybHazardBMWOccuhazard   
			     * @Description: TODO(这里用一句话描述这个方法的作用)   
			     * @param: @param zybhis
			     * @param: @param user
			     * @param: @param app
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: ZybExamOccuhazardfactors      
			     * @throws
			 */
			public ZybExamOccuhazardfactors savezybHazardBMWOccuhazard(String tiJianType,ZybExamOccuhazardfactors zybhis,UserDTO user,String app)throws ServiceException;
			
			public PageReturnDTO getZybUserList(String data_source,long com_id,long set_id,int  ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String jdstatuss,String userid,String centernum,ZybImpCustomerInfoModel model, int pagesize, int pageno,String sort,String order,UserDTO user,String center_num) throws ServiceException;

			//职业病新增人员工种系统没有自动新增
			public String addZybOccutypeofwork(String occutypeofworkName)throws ServiceException;

    public  String  cheCkOccuphyexaclass(String exam_num,List<ExamSetDTO> listset) throws ServiceException;
}
