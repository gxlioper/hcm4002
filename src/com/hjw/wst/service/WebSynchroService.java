package com.hjw.wst.service;



import java.sql.SQLException;

import com.hjw.wst.domain.WebSynchro;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  
     * @author: zt     
     * @date:   2017年8月10日 上午9:05:51   
     * @version V2.0.0.0
 */
public interface WebSynchroService {
	/**
	 * 添加
	     * @Title: addWebSynchro   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ws
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	//public void addWebSynchro(WebSynchro ws) throws ServiceException;;
		/**
		 * 删除
		     * @Title: deleteWebSynchro   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param ws
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	//public void deleteWebSynchro(String id,char datatype) throws ServiceException;;

	/**
	 * 更新
	     * @Title: updateWebSynchro   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ws
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateWebSynchro(String id,char datatrpe) throws ServiceException ;

	/**
	 *查询全部
	     * @Title: queryallDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ddt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List      
	     * @throws
	 */
	//public List queryallDaDt() throws ServiceException;
	
	/**
	 * id 查询
	     * @Title: queryByWebSynchro   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebSynchro      
	     * @throws
	 */
	//public WebSynchro queryByWebSynchro(String id,char datatype) throws ServiceException;

	
	
}
	
