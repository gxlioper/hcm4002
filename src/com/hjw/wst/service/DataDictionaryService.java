package com.hjw.wst.service;


import java.util.List;

import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.DataDictionary;
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
public interface DataDictionaryService {
	/**
	 * 添加
	     * @Title: addDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ddt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DataDictionary      
	     * @throws
	 */
	public DataDictionary addDaDt(DataDictionary ddt) throws ServiceException;;
		/**
		 * 删除
		     * @Title: deleteDaDt   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param ddt
		     * @param: @throws ServiceException      
		     * @return: void      
		     * @throws
		 */
	public void deleteDaDt(DataDictionary ddt) throws ServiceException;;

	/**
	 * 更新
	     * @Title: updateDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ddt
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DataDictionary      
	     * @throws
	 */
	public DataDictionary updateDaDt(DataDictionary ddt) throws ServiceException;

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
	public List queryallDaDt() throws ServiceException;
	
	/**
	 * id查询
	     * @Title: loadDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DataDictionary      
	     * @throws
	 */
	
	public DataDictionary loadDaDt(long id) throws ServiceException;

	/**
	 * 编码查询
	     * @Title: queryByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DataDictionary      
	     * @throws
	 */
	public  DataDictionary queryByNum(String data_code) throws ServiceException;
	
	/**
	 * 分页查询
	     * @Title: queryPageDaDt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param data_code
	     * @param: @param data_name
	     * @param: @param data_type
	     * @param: @param pageno
	     * @param: @param pagesize
	     * @param: @return      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryPageDaDt(String data_code,String data_name,String data_type,int pageno, int pagesize) throws ServiceException;
	
	public DataDictionary getDataCode(String data_type)throws ServiceException;
	
	public DataDictionary queryByData_name(String data_code, String data_type, String data_name, long id) throws ServiceException;
	
	public PageReturnDTO centerConfigList(String id,String data_type, String center_num, int rows, int page) throws ServiceException;
	
	
	public boolean savacenterconfig(String center_name, String config_key, String config_value, String isActive,String common, String center_type, String center_num)throws ServiceException;
	
	public boolean updateCenterConfigEdit(String center_name, String config_key, String config_value, String is_active,String common, String center_type, String center_num);
			
	
}
	
