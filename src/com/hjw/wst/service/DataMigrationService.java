package com.hjw.wst.service;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.hjw.util.PdfFtpHelper;
import com.hjw.wst.DTO.DataBaseConfigDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.AcceptanceCheckModel;
import com.hjw.wst.model.DataMigrationModel;
import com.synjones.framework.exception.ServiceException;

public interface DataMigrationService {
	
	/**
	 * 获取数据库/ftp 配置信息
	     * @Title: getDataBaseConfigList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DataBaseConfigDTO>      
	     * @throws
	 */
	public List<DataBaseConfigDTO> getDataBaseConfigList() throws ServiceException;
	
	/**
	 * 保存数据库/ftp配置信息
	     * @Title: saveDataBaseConfig   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param dto
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDataBaseConfig(DataBaseConfigDTO dto) throws ServiceException;
	
	public DataBaseConfigDTO getDataBaseConfigOne(long type) throws ServiceException;
	
	/**
	 * 获取体检车需要导出的单位、批次、分组信息
	     * @Title: getDataMigrationCompany   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_nums
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<Map<String,Object>>      
	     * @throws
	 */
	public List<Map<String,Object>> getDataMigrationCompany(String exam_nums,String center_num) throws ServiceException;
	
	public List<Map<String,Object>> getDataMigrationCompanyByCon(Connection connection) throws ServiceException;

	/**
	 * 获取体检车需要导出的数据
	     * @Title: getNeedDataMigration   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Map<String,List<T>>      
	     * @throws
	 */
	public List<Map<String,Object>> getNeedDataMigration(String exam_nums,String center_num) throws ServiceException;
	
	public List<Map<String,Object>> getNeedDataMigrationByCon(Connection connection) throws ServiceException;
	
	/**
	 * 单位、批次、分组数据入正式库(直连入库)
	     * @Title: saveDataMigrationCom   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param map
	     * @param: @param user
	     * @param: @param connection
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean saveDataMigrationCom(Map<String,Object> map,UserDTO user,Connection connection,DataBaseConfigDTO dto) throws ServiceException;
	
	/**
	 * 单位、批次、分组数据入正式库(入库离线数据库文件)
	     * @Title: saveDataMigrationComDb   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param map
	     * @param: @param user
	     * @param: @param connection
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean saveDataMigrationComDb(Map<String,Object> map,UserDTO user,Connection connection) throws ServiceException;
	
	/**
	 * 体检数据入正式库(直连入库)
	     * @Title: saveDataMigration   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param map
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public boolean saveDataMigration(Map<String,Object> map,UserDTO user,Connection connection,DataBaseConfigDTO dto) throws ServiceException;
	
	/**
	 * 体检数据入正式库(入库离线数据库文件)
	     * @Title: saveDataMigrationDb   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param map
	     * @param: @param user
	     * @param: @param connection
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean saveDataMigrationDb(Map<String,Object> map,UserDTO user,Connection connection) throws ServiceException;
	
	/**
	 * 查询需要导出导出的人员列表
	     * @Title: getDataMigrationList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @param sort
	     * @param: @param order
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDataMigrationList(DataMigrationModel model, int rows, int page,String sort,String order,UserDTO user)throws ServiceException;
	
	/**
	 * 通过文件导入导出 创建数据库表
	     * @Title: createDataMigrationTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean createDataMigrationTable(Connection connection) throws ServiceException;
	
	/**
	 * 保存导出记录信息
	     * @Title: saveExportExaminfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveExportExaminfo(String exam_num) throws ServiceException;
	
	/**
	 * 验证导出数据文件是否能导入
	     * @Title: vieryExportDb   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @param is_out_reg
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String vieryExportDb(Connection connection,String is_out_reg,String center_num) throws ServiceException;
}
