package com.hjw.wst.service;


import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.FinalRejection;
import com.synjones.framework.exception.ServiceException;
	/**
	 * 
	 * <菜单、功能、角色操作> <功能详细描述>
	 * 
	 * @author zt
	 * @version [3.0.0, Nov 4, 2011]
	 * @see [相关类/方法]
	 * @since [一卡通网站查询系统]
	 */
	public interface FinalRejectionService {
		/**
		 * 
		     * @Title: addFinalRejection   
		     * @Description: 增加驳回意见(这里用一句话描述这个方法的作用)   
		     * @param: @param dept
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: boolean      
		     * @throws
		 */
		public FinalRejection addFinalRejection(FinalRejection reject) throws ServiceException;
			/**
			 * 
			     * @Title: deleteFinalRejection   
			     * @Description: 删除驳回意见(这里用一句话描述这个方法的作用)   
			     * @param: @param deptid
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: boolean      
			     * @throws
			 */
		public void deleteFinalRejection(FinalRejection reject) throws ServiceException;

		/**
		 * 
		     * @Title: updateFinalRejection   
		     * @Description: 驳回意见更新(这里用一句话描述这个方法的作用)   
		     * @param: @param dep
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: boolean      
		     * @throws
		 */
		public FinalRejection updateFinalRejection(FinalRejection reject) throws ServiceException;

		
		/**
		 * 
		     * @Title: finalRejectionList   
		     * @Description: 驳回意见列表分页(这里用一句话描述这个方法的作用)   
		     * @param: @param dept
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: boolean      
		     * @throws
		 */
		
		public PageReturnDTO finalRejectionList(String reject_context, int pageno, int pagesize);
		
		/**
		 * 
		     * @Title: serchRejectionById   
		     * @Description: 根据id查驳回意见(这里用一句话描述这个方法的作用)   
		     * @param: @param 
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: boolean      
		     * @throws
		 */
		public FinalRejection serchRejectionById(long id) throws ServiceException;
		

}
