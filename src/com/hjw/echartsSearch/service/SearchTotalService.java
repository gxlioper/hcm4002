package com.hjw.echartsSearch.service;

import java.util.List;

import com.hjw.echartsSearch.DTO.MonthlyAmt;
import com.hjw.echartsSearch.DTO.MonthlyAmtAll;
import com.hjw.echartsSearch.DTO.SearchTotalAllDTO;
import com.hjw.echartsSearch.DTO.SearchTotalDTO;
import com.synjones.framework.exception.ServiceException;

public interface SearchTotalService {

	public List<SearchTotalDTO> getInspection_statistics_today(String types,String sttime,String entime) throws ServiceException;
	
	/**
	 * 导检单回收实时统计
	     * @Title: examination_gride_statistics   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public List<SearchTotalDTO> examination_gride_statistics(String types,String sttime,String entime) throws ServiceException;
	
	public List<SearchTotalDTO> examination_status_statistics(String types,String sttime,String entime) throws ServiceException;
	
	/**
	 * 当日体检人员类别统计
	     * <p>Title: examination_status_statistics</p>   
	     * <p>Description: </p>   
	     * @param types
	     * @param sttime
	     * @param entime
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.echartsSearch.service.SearchTotalService#examination_status_statistics(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<SearchTotalDTO> exam_info_Composition_today(String types,String sttime,String entime) throws ServiceException;
	
	/**
	 * 月度营业统计
	     * <p>Title: examination_status_statistics</p>   
	     * <p>Description: </p>   
	     * @param types
	     * @param sttime
	     * @param entime
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.echartsSearch.service.SearchTotalService#examination_status_statistics(java.lang.String, java.lang.String, java.lang.String)
	 */
	public MonthlyAmtAll monthly_business_statistics(String types,String sttime,String entime) throws ServiceException;
	
	/**
	 * 
	     * @Title: exam_info_type_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public List<SearchTotalDTO> exam_info_type_today(String types,String sttime,String entime) throws ServiceException;
	
	/**
	 * 实施流量分析
	     * @Title: real_time_traffic_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SearchTotalDTO      
	     * @throws
	 */
	public SearchTotalDTO real_time_traffic_today(String types,String sttime,String entime,String sttimeall,String entimeall) throws ServiceException;
	
	/**
	 * 实施获取sqlserver连接数
	     * @Title: real_time_traffic_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SearchTotalDTO      
	     * @throws
	 */
	public SearchTotalDTO sql_conn_count(String entime) throws ServiceException;

	/**
	 * 当日流量趋势
	     * @Title: real_time_traffic_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SearchTotalDTO      
	     * @throws
	 */
	public List<MonthlyAmt> flow_trend_chart(String types) throws ServiceException;
	
	/**
	 * 年度单位组成
	     * @Title: Annual_unit_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: MonthlyAmtAll      
	     * @throws
	 */
	public List<SearchTotalDTO> annual_unit_growth() throws ServiceException;
	
	/**
	 * 年度人员组成
	     * @Title: Annual_personnel_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: MonthlyAmtAll      
	     * @throws
	 */
	public List<SearchTotalDTO> annual_personnel_growth() throws ServiceException;

	/**
	 * 月度总检条数
	     * @Title: count_number_checks   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public List<SearchTotalDTO> count_number_checks(String stime,String etime) throws ServiceException;
	
	/**
	 * 人员次数组成
	     * @Title: annual_personnel_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public List<SearchTotalDTO> statistics_personnel_times() throws ServiceException;
	
	/**
	 * 年龄段疾病组成
	     * @Title: age_group_disease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public SearchTotalAllDTO age_group_disease() throws ServiceException;
	
}
