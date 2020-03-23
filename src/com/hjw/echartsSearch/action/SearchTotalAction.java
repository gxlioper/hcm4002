package com.hjw.echartsSearch.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hjw.echartsSearch.DTO.MonthlyAmt;
import com.hjw.echartsSearch.DTO.MonthlyAmtAll;
import com.hjw.echartsSearch.DTO.SearchTotalAllDTO;
import com.hjw.echartsSearch.DTO.SearchTotalDTO;
import com.hjw.echartsSearch.service.SearchTotalService;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.service.CustomerInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("rawtypes")
public class SearchTotalAction  extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 1L;
	private Object model = new Object();
	
	private SearchTotalService searchTotalService;	
	private CustomerInfoService customerInfoService;	
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setSearchTotalService(SearchTotalService searchTotalService) {
		this.searchTotalService = searchTotalService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(Object model) {
		this.model = model;
	}
	
	/**
	 * 当日检查情况统计
	     * @Title: inspection_statistics_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String inspection_statistics_today() throws WebException {
		String sdate=getDate2()+" 00:00:01";
		String edate=getDate2()+" 23:59:59";
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		slist=this.searchTotalService.getInspection_statistics_today(IS_SEARCHTOTAL_TYPE,sdate, edate);
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 导检单回收实时统计
	     * @Title: examination_gride_statistics   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examination_gride_statistics() throws WebException {
		String sdate=getDate2()+" 00:00:01";
		String edate=getDate2()+" 23:59:59";
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		slist=this.searchTotalService.examination_gride_statistics(IS_SEARCHTOTAL_TYPE,sdate, edate);
		this.outJsonResult(slist);
		return NONE;
	}
	
	
	/**
	 * 当日体检状态统计
	     * @Title: examination_status_statistics   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examination_status_statistics() throws WebException {
		String sdate=getDate2();
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		slist=this.searchTotalService.examination_status_statistics(IS_SEARCHTOTAL_TYPE,sdate, "");
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 当日体检人员组成
	     * @Title: exam_info_Composition_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String exam_info_Composition_today() throws WebException {
		String sdate=getDate2();
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		slist=this.searchTotalService.exam_info_Composition_today(IS_SEARCHTOTAL_TYPE,sdate, "");
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 当日体检类型组成
	     * @Title: exam_info_Composition_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String exam_info_type_today() throws WebException {
		String sdate=getDate2();
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		slist=this.searchTotalService.exam_info_type_today(IS_SEARCHTOTAL_TYPE,sdate, "");
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 月度营业统计
	     * @Title: monthly_business_statistics   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String monthly_business_statistics() throws WebException {
		String sdate=getMonthfirstDay()+" 00:00:01";
		String edate=getMonthLastDay()+" 23:59:59";

		MonthlyAmtAll m= new MonthlyAmtAll();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		m=this.searchTotalService.monthly_business_statistics(IS_SEARCHTOTAL_TYPE,sdate, edate);
		this.outJsonResult(m);
		return NONE;
	}
	
	/**
	 * 当日实时流量分析
	     * @Title: real_time_traffic_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String real_time_traffic_today() throws WebException {
		String sdateall=getDate2().substring(0,10)+" 00:00:01";
		String edateall=getDate2().substring(0,10)+" 23:59:59";
		String sdate=getCurrentTime(-5);
		String edate=getCurrentTime(0);

		SearchTotalDTO m= new SearchTotalDTO();
		String IS_SEARCHTOTAL_TYPE="1";
		try{
			IS_SEARCHTOTAL_TYPE = this.customerInfoService.getCenterconfigByKey("IS_SEARCHTOTAL_TYPE", null).getConfig_value().trim();
		}catch(Exception ex){}
		m=this.searchTotalService.real_time_traffic_today(IS_SEARCHTOTAL_TYPE,sdate, edate,sdateall,edateall);
		this.outJsonResult(m);
		
		return NONE;
	}
	
	/**
	 * 当日实时流量分析
	     * @Title: real_time_traffic_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String sql_conn_count() throws WebException {		
		SearchTotalDTO m= new SearchTotalDTO();
		String edateall=DateTimeUtil.getDateTime();		
		m=this.searchTotalService.sql_conn_count(edateall);
		this.outJsonResult(m);
		return NONE;
	}
	
	/**
	 * 连接数趋势图
	     * @Title: flow_sql_conn_count   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_sql_conn_count() throws WebException {
		List<MonthlyAmt> slist = new ArrayList<MonthlyAmt>();
		slist = this.searchTotalService.flow_trend_chart("002");
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 当日流量趋势图
	     * @Title: real_time_traffic_today   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String flow_trend_chart() throws WebException {
		List<MonthlyAmt> slist = new ArrayList<MonthlyAmt>();
		slist = this.searchTotalService.flow_trend_chart("001");
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 年度单位增长
	     * @Title: Annual_unit_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String annual_unit_growth() throws WebException {
		List<SearchTotalDTO> slist = new ArrayList<SearchTotalDTO>();
		slist = this.searchTotalService.annual_unit_growth();
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 年度人员增长
	     * @Title: Annual_personnel_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String annual_personnel_growth() throws WebException {
		List<SearchTotalDTO> slist = new ArrayList<SearchTotalDTO>();
		slist = this.searchTotalService.annual_personnel_growth();
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 月度总检条数
	     * @Title: count_number_checks   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String count_number_checks() throws WebException {
		String sdate=getMonthfirstDay()+" 00:00:01";
		String edate=getMonthLastDay()+" 23:59:59";
		List<SearchTotalDTO> slist = new ArrayList<SearchTotalDTO>();
		slist = this.searchTotalService.count_number_checks(sdate,edate);
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 多次检查情况统计
	     * @Title: annual_personnel_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String statistics_personnel_times() throws WebException {
		List<SearchTotalDTO> slist = new ArrayList<SearchTotalDTO>();
		slist = this.searchTotalService.statistics_personnel_times();
		this.outJsonResult(slist);
		return NONE;
	}
	
	/**
	 * 年龄段疾病组成
	     * @Title: age_group_disease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String age_group_disease() throws WebException {
		SearchTotalAllDTO slist = new SearchTotalAllDTO();
		slist = this.searchTotalService.age_group_disease();
		this.outJsonResult(slist);
		return NONE;
	}
	
	public static String getDate2() {
		Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.YEAR, -1);
    	
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dt = sdf.format(calendar.getTime());		
		return dt;
	}
	  /**  
     * 得到本月的最后一天   
     */ 
    public static String getMonthLastDay() {   
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());     
        
        calendar.add(Calendar.YEAR, -1);
        
        calendar.set(Calendar.DAY_OF_MONTH, calendar   
                .getActualMaximum(Calendar.DAY_OF_MONTH));
//        calendar.set( Calendar.DATE, 1);
//        calendar.roll(Calendar.DATE, - 1);
        SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
        return simpleFormate.format(calendar.getTime());   
    }
    
    /**  
     * 得到本月的最后一天   
     */ 
    public static String getMonthfirstDay() {   
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(new Date());
    	calendar.add(Calendar.YEAR, -2);
		Date y = calendar.getTime();	
		
        calendar.setTime(y);        
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
//        calendar.set( Calendar.DATE, 1);
//        calendar.roll(Calendar.DATE, - 1);
        SimpleDateFormat simpleFormate = new SimpleDateFormat("yyyy-MM-dd");
        return simpleFormate.format(calendar.getTime());   
    }
    
    /**
    * 获取当前时间前N分钟  n=负数 表示当前时间前N 分钟，n=正数，表示当前时间后N分钟
    * @param stuff
    * @return
    */
    public String getCurrentTime(int n){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Calendar beforeTime = Calendar.getInstance();
       
       beforeTime.add(Calendar.YEAR, -1);
       
       beforeTime.add(Calendar.MINUTE, n);
       Date beforeD = beforeTime.getTime();
       String time = sdf.format(beforeD);
    return time; 
    }
}
