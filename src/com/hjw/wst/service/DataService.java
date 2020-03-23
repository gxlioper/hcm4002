package com.hjw.wst.service;

import java.util.List;

import com.hjw.webService.client.sxwn.Bean.TableBean;
import com.hjw.webService.client.sxwn.Bean.TableItemBean;
import com.hjw.webService.service.Databean.Dept;
import com.hjw.webService.service.Databean.DiagnosisItem;
import com.hjw.webService.service.Databean.DiagnosisPrice;
import com.hjw.webService.service.Databean.Price;
import com.hjw.webService.service.Databean.User;
import com.hjw.webService.service.bean.ResultSerBody;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.ProcListResult;
import com.hjw.wst.DTO.ProcPacsResult;
import com.hjw.wst.DTO.ThridLisClassDTO;
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
public interface DataService {
	
	/**
	 * 保存收费信息
	     * @Title: saveFeesResult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
//	public String saveFeesResult(ResultSerBody rb) throws ServiceException;
	
	/**
	 * 
	     * @Title: doproc_Lis_result   
	     * @Description: 执行lis结果插入   
	     * @param: @param pr
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int doproc_Lis_result(ProcListResult pr)throws ServiceException;
	
	/**
	 * 
	     * @Title: proc_pacs_report_dbgj   
	     * @Description: 执行pacs插入   
	     * @param: @param pr
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int proc_pacs_report_dbgj(ProcPacsResult pr)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDepNumForPacs   
	     * @Description: 获取pacs科室编码   
	     * @param: @param pacs_req_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getDepNumForPacs(String pacs_req_code)throws ServiceException;
	
	/**
	 * 
	     * @Title: getDatadis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param data_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: JobDTO      
	     * @throws
	 */
	public JobDTO getDatadis(String data_code)throws ServiceException;
	
	/**
	 * 保存HIS执行科室信息
	     * @Title: saveHisDept   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param depList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveHisDept(List<Dept> depList) throws ServiceException;
	
	/**
	 * 保存HIS价表信息
	     * @Title: savePrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param priceList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String savePrice(List<Price> priceList) throws ServiceException;
	
	/**
	 * 保存HIS临床诊疗项目与价表项目对照表
	     * @Title: saveAcceptDate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param diagnList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveAcceptDate(List<DiagnosisPrice> diagnList) throws ServiceException;
	
	/**
	 * 保存HIS诊疗项目表
	     * @Title: saveDiagnosisItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param diagnosisItemList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDiagnosisItem(List<DiagnosisItem> diagnosisItemList) throws ServiceException;


	/**
	 * 
	     * @Title: saveUserData   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param users
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserData(List<User> users) throws ServiceException;
	
	public void updateSampleExamDetailByExamid(String examnum, long samp_id, String status)
			throws ServiceException;
	
	/**
	 * 
	     * @Title: saveUserData   
	     * @Description: 保存lis 检查项目信息   
	     * @param: @param users
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public boolean saveLisClass(TableBean tb) throws ServiceException;
	
	/**
	 * 
	     * @Title: saveUserData   
	     * @Description: 保存lis 检查项目细项   
	     * @param: @param users
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public void saveLisClassItem(String lisclassid,List<TableItemBean> tblist) throws ServiceException;
	
	/**
	 * 
	     * @Title: getLisClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridLisClassDTO      
	     * @throws
	 */
	public ThridLisClassDTO getLisClass(String id) throws ServiceException;
}
