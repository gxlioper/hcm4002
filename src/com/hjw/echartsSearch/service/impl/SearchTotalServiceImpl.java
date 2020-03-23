package com.hjw.echartsSearch.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hjw.echartsSearch.DTO.MonthlyAmt;
import com.hjw.echartsSearch.DTO.MonthlyAmtAll;
import com.hjw.echartsSearch.DTO.MonthlyULAmt;
import com.hjw.echartsSearch.DTO.SearchTotalAllDTO;
import com.hjw.echartsSearch.DTO.SearchTotalDTO;
import com.hjw.echartsSearch.service.SearchTotalService;
import com.hjw.util.Configuration;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.config.GetNumContral;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;

public class SearchTotalServiceImpl implements SearchTotalService{
	private JdbcQueryManager jqm;
	
	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}
	
	public static Connection getConnection(){
        try {
            String url ="jdbc:sqlserver://localhost:1433;DatabaseName=peis_dbgj_190510";
            String userName = "sa";
            String password = "HUOjianwa010";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connection = DriverManager.getConnection(url,userName,password);
            return connection;
        }catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                 e.printStackTrace();
             }
        return null;
    }
	
	
	/**
	 * 导检单回收
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
	public List<SearchTotalDTO> examination_gride_statistics(String types,String sttime,String entime) throws ServiceException{
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select ei.is_guide_back,count(*) as counts from exam_info ei "
						+ "where ei.is_Active='Y' "
						+ "and ei.join_date>='"+sttime+"' "
						+ "and ei.join_date<='"+entime+"' "
						+ "group by ei.is_guide_back");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "当日体检状态统计:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("is_guide_back"));//--N未回收 Y已回收
				if ("N".equals(s.getName().toUpperCase())){
					s.setName("未回收");
				}else if ("Y".equals(s.getName().toUpperCase())){
					s.setName("已回收");
				}else {
					s.setName("未知");
				}
				slist.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return slist;
	}
	
	/**
	 * 当日检查情况统计
	     * <p>Title: getInspection_statistics_today</p>   
	     * <p>Description: </p>   
	     * @param sttime1
	     * @param entime2
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.echartsSearch.service.SearchTotalService#getInspection_statistics_today(java.lang.String, java.lang.String)
	 */
	public List<SearchTotalDTO> getInspection_statistics_today(String types,String sttime1,String entime2) throws ServiceException{
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			if("2".equals(types)){
				sqlstr.append(" select eci.exam_status,count(*) as counts from examinfo_charging_item eci,exam_info ei,charging_item ci "
						+ "where ei.exam_num= eci.exam_num and ei.is_Active='Y' and eci.isActive='Y' and ci.item_code=eci.charging_item_code "
						+ " and ci.item_category !='耗材类型' "
						+ "and ei.join_date>='"+sttime1+"' "
						+ "and ei.join_date<='"+entime2+"' "
						+ "group by eci.exam_status");
			}else{
			sqlstr.append(" select eci.exam_status,count(*) as counts from examinfo_charging_item eci,exam_info ei,charging_item ci "
					+ "where ei.id=eci.examinfo_id and ei.is_Active='Y' and eci.isActive='Y' "
					+ "and ci.id=eci.charge_item_id and ci.item_category !='耗材类型' "
					+ "and ei.join_date>='"+sttime1+"' "
					+ "and ei.join_date<='"+entime2+"' "
					+ "group by eci.exam_status");
			}
			 TranLogTxt.liswriteEror_to_txt("searchtotal", "当日检查情况统计:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("exam_status"));//--N未检查 Y已检查 G弃检 C已登记，D延期
				if ("N".equals(s.getName().toUpperCase())){
					s.setName("未检");
				}else if ("Y".equals(s.getName().toUpperCase())){
					s.setName("已检");
				}else if ("G".equals(s.getName().toUpperCase())){
					s.setName("弃检");
				}else if ("C".equals(s.getName().toUpperCase())){
					s.setName("登记");
				}else if ("D".equals(s.getName().toUpperCase())){
					s.setName("延期");
				}else {
					s.setName("未知");
				}
				slist.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return slist;
	}
	
	/**
	 * 当日体检状态统计
	     * <p>Title: examination_status_statistics</p>   
	     * <p>Description: </p>   
	     * @param types
	     * @param sttime
	     * @param entime
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.echartsSearch.service.SearchTotalService#examination_status_statistics(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<SearchTotalDTO> examination_status_statistics(String types,String sttime,String entime) throws ServiceException{
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append(" select ei.status,count(*) as counts from exam_info ei "
						+ "where ei.is_Active='Y' and ((CONVERT(varchar(100), ei.create_time, 23)='"+sttime+"') "
						+ "or (CONVERT(varchar(100), ei.join_date, 23)='"+sttime+"')) "
						+ " group by ei.status");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "当日体检状态统计:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("status"));//--N未检查 Y已检查 G弃检 C已登记，D延期
				if ("Z".equals(s.getName().toUpperCase())){
					s.setName("总检");
				}else if ("J".equals(s.getName().toUpperCase())){
					s.setName("检查中");
				}else if ("Y".equals(s.getName().toUpperCase())){
					s.setName("预约");
				}else if ("D".equals(s.getName().toUpperCase())){
					s.setName("登记");
				}else {
					s.setName("未知");
				}
				slist.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return slist;
	}
	
	
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
	public List<SearchTotalDTO> exam_info_Composition_today(String types,String sttime,String entime) throws ServiceException{
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select dd.type_name,mm.counts from ("
						+ " select ei.customer_type_id,count(*) as counts from exam_info ei "
						+ "where ei.is_Active='Y' and ((CONVERT(varchar(100), ei.create_time, 23)='"+sttime+"') "
						+ "or (CONVERT(varchar(100), ei.join_date, 23)='"+sttime+"')) "
						+ " group by ei.customer_type_id) mm "
						+ "  left join customer_type dd on dd.id=mm.customer_type_id");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "当日体检人员类别统计:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("type_name"));				
				slist.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return slist;
	}
	
	/**
	 * 当日体检类型统计
	     * <p>Title: examination_status_statistics</p>   
	     * <p>Description: </p>   
	     * @param types
	     * @param sttime
	     * @param entime
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.echartsSearch.service.SearchTotalService#examination_status_statistics(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<SearchTotalDTO> exam_info_type_today(String types,String sttime,String entime) throws ServiceException{
		List<SearchTotalDTO> slist= new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select dd.data_name,mm.counts from ("
						+ " select ei.customer_type,count(*) as counts from exam_info ei "
						+ "where ei.is_Active='Y' and ((CONVERT(varchar(100), ei.create_time, 23)='"+sttime+"') "
						+ "or (CONVERT(varchar(100), ei.join_date, 23)='"+sttime+"')) "
						+ " group by ei.customer_type "
						+ " ) mm left join data_dictionary dd on dd.id=mm.customer_type");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "当日体检类型统计:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("data_name"));				
				slist.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return slist;
	}
	
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
	public MonthlyAmtAll monthly_business_statistics(String types,String sttime,String entime) throws ServiceException{
		MonthlyAmtAll ma=new MonthlyAmtAll();
		List<MonthlyAmt> list = new ArrayList<MonthlyAmt>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select tt.gmounths,tt.amt,tt.tamt from ( "
						+ " select gsearche.gmounths,gsearche.amt,tsearch.tamt from ( "
						+ " select CONVERT(varchar(7) ,create_time, 120) as gmounths,sum(amount2) as amt from charging_summary_single c "
						+ " where c.is_active='Y' "
						+ " and create_time>='"+sttime+"' and create_time<='"+entime+"' "
						+ "  group by  CONVERT(varchar(7) ,create_time, 120)) gsearche "
						+ "    JOIN "
						+ "  (select CONVERT(varchar(7) ,create_time, 120) as tmounths,sum(eci.team_pay) as tamt from examinfo_charging_item eci "
						+ "   where eci.isActive='Y' and eci.exam_status<>'N' and eci.exam_indicator='T' "
						+ "  and create_time>='"+sttime+"' and create_time<='"+entime+"' "
						+ "   group by  CONVERT(varchar(7) ,create_time, 120)) tsearch "
						+ "   on gsearche.gmounths=tsearch.tmounths "
						+ ") tt order by tt.gmounths ");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "月度营业统计:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			MonthlyULAmt gulamt=new MonthlyULAmt();
			MonthlyULAmt tulamt=new MonthlyULAmt();
			String gmonth="";
			String tmonth="";
			double glamt=-1;
			double guamt=-1;
			double tlamt=-1;
			double tuamt=-1;
			while (rs.next()) {
				MonthlyAmt s= new MonthlyAmt();
				double amt=rs.getDouble("amt");
				if(glamt==-1){
					glamt=amt;
				}else if(glamt<=amt){
					glamt=amt;
				}
				
				 if(guamt<=amt){
					 guamt=amt;
				}
				 
				 double tamt=rs.getDouble("tamt");
					if(tlamt==-1){
						tlamt=tamt;
					}else if(tlamt<=tamt){
						tlamt=tamt;
					}
					
					 if(tuamt<=tamt){
						 tuamt=tamt;
					}
				
				s.setValue(rs.getString("amt"));
				s.setValue1(rs.getString("tamt"));
				s.setName(rs.getString("gmounths"));				
				list.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		ma.setList(list);
		return ma;
	}
	
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
	public SearchTotalDTO sql_conn_count(String entime) throws ServiceException{
		Connection connection = null;
		SearchTotalDTO s= new SearchTotalDTO();
		s.setName("体检率");
	    s.setValue("0");
		try {
			connection = this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			Map<String,String> map = System.getenv();  
	        String comname=map.get("COMPUTERNAME").trim();//获取计算机名                                                                                   
				sqlstr.append("SELECT count(*) counts FROM master.dbo.sysprocesses WHERE dbid IN("
						+ "SELECT dbid FROM master.dbo.sysdatabases "
						+ "WHERE NAME='hjwpeis4.0.0.2' and program_name='Microsoft SQL Server JDBC Driver' "
						+ "and hostname='"+comname.trim()+"') ");
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "实时流量分析:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			if (rs.next()) {
				double counts = rs.getDouble("counts");
				String mm = GetNumContral.class.getClassLoader().getResource("").getPath();
		        String syspath = mm.substring(0, mm.length() - 8)+ "config/pro.properties";
		        Configuration rc = new Configuration(syspath);
		        long fmcounts = Long.valueOf(rc.getValue("jdbc.maxPoolSize"));
				BigDecimal bd = new BigDecimal(counts/fmcounts*100);
				double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				s.setValue(tamt+"");
				String insert="delete from searchtotal_realtime_traffic where today<>'"+entime.substring(0,10)+"' and types='002'";
				connection.createStatement().execute(insert);
				insert="insert into searchtotal_realtime_traffic(today,datetimes,counts,counts1,types) values ('"+entime.substring(0,10)+"','"+entime.substring(11,entime.length())+"','"+counts+"','"+tamt+"','002')";
				connection.createStatement().execute(insert);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	/**
	 * 实时流量分析
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
	public SearchTotalDTO real_time_traffic_today(String types,String sttime,String entime,String sttimeall,String entimeall) throws ServiceException{
		Connection connection = null;
		SearchTotalDTO s= new SearchTotalDTO();
		s.setName("体检率");
	    s.setValue("0");
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			if("2".equals(types)){                                                                                                     
				sqlstr.append("select sum(aa.counts) as counts from ");  
				sqlstr.append("(");  
				sqlstr.append("select rr.item_code,count(*) as counts from ");  
				sqlstr.append("(");  
				sqlstr.append("select ci.item_code,'登记台' as deptname from exam_info ei,examinfo_charging_item eci ");  
				sqlstr.append("left join charging_item ci on ci.id=eci.charge_item_id ");  
				sqlstr.append(" where ei.is_Active='Y' and ei.join_date>='"+sttime+"' ");  
				sqlstr.append("and ei.join_date<='"+entime+"' ");  
				sqlstr.append("and eci.exam_num=ei.exam_num ");  
				sqlstr.append("UNION all ");  
				sqlstr.append("select ci.item_code,'收费处' as deptname from charging_summary_single css,charging_detail_single cds ");  
				sqlstr.append("left join charging_item ci on ci.item_code=cds.charging_item_code  ");  
				sqlstr.append(" where css.create_time>='"+sttime+"' ");  
				sqlstr.append("and css.create_time<='"+entime+"' ");  
				sqlstr.append("and cds.summary_id=css.id ");  
				sqlstr.append("UNION all ");  
				sqlstr.append("select ci.item_code,'采血室' as deptname from sample_exam_detail sed,examResult_chargingItem erc ");  
				sqlstr.append("left join charging_item ci on ci.item_code=erc.charging_item_code ");  
				sqlstr.append(" where sed.status='Y' ");  
				sqlstr.append("and erc.result_type='sample' ");  
				sqlstr.append("and sed.id=erc.exam_id ");  
				sqlstr.append("and  sed.update_time>='"+sttime+"' ");  
				sqlstr.append("and sed.update_time<='"+entime+"' ");  
				sqlstr.append("UNION all ");  
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from common_exam_detail ced,charging_item ci ");  
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id ");  
				sqlstr.append(" where  ced.create_time>='"+sttime+"' ");  
				sqlstr.append("and ced.create_time<='"+entime+"' and ci.item_code=ced.charging_item_code ");  
				sqlstr.append("UNION all ");  
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from view_exam_detail ved, pacs_detail pd,charging_item ci ");  
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id ");  
				sqlstr.append("where ved.pacs_id=pd.id and pd.chargingItem_num=ci.item_code ");  
				sqlstr.append("and ved.create_time>='"+sttime+"' ");  
				sqlstr.append("and ved.create_time<='"+entime+"' ");  
				sqlstr.append("union all ");  
				sqlstr.append("select es.disease_name  as item_code,'总检室' as deptname from examinfo_disease es where ");  
				sqlstr.append(" es.create_time>='"+sttime+"' ");  
				sqlstr.append("and es.create_time<='"+entime+"' ");  
				sqlstr.append(") rr  ");  
				sqlstr.append("group by rr.item_code ");  
				sqlstr.append(") aa ");
			}else{
				sqlstr.append("select sum(aa.counts) as counts from ");
				sqlstr.append(" (");
				sqlstr.append("select rr.item_code,count(*) as counts from ");
				sqlstr.append("( ");
				sqlstr.append("select ci.item_code,'登记台' as deptname from exam_info ei,examinfo_charging_item eci ");
				sqlstr.append("left join charging_item ci on ci.id=eci.charge_item_id ");
				sqlstr.append(" where ei.is_Active='Y' and ei.join_date>='"+sttime+"' ");
				sqlstr.append("and ei.join_date<='"+entime+"' ");
				sqlstr.append("and eci.examinfo_id=ei.id ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,'收费处' as deptname from charging_summary_single css,charging_detail_single cds ");
				sqlstr.append("left join charging_item ci on ci.id=cds.charging_item_id ");
				sqlstr.append(" where css.create_time>='"+sttime+"' ");
				sqlstr.append("and css.create_time<='"+entime+"' ");
				sqlstr.append("and cds.summary_id=css.id ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,'采血室' as deptname from sample_exam_detail sed,examResult_chargingItem erc ");
				sqlstr.append("left join charging_item ci on ci.id=erc.charging_id ");
				sqlstr.append(" where sed.status='Y' ");
				sqlstr.append("and erc.result_type='sample' ");
				sqlstr.append("and sed.id=erc.exam_id ");
				sqlstr.append("and  sed.update_time>='"+sttime+"' ");
				sqlstr.append("and sed.update_time<='"+entime+"' ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from common_exam_detail ced,charging_item ci ");
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id ");
				sqlstr.append(" where  ced.create_time>='"+sttime+"' ");
				sqlstr.append("and ced.create_time<='"+entime+"' and ci.id=ced.charging_item_id ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from view_exam_detail ved, pacs_detail pd,charging_item ci  ");
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id ");
				sqlstr.append("where ved.pacs_id=pd.id and pd.chargingItem_num=ci.item_code ");
				sqlstr.append("and ved.create_time>='"+sttime+"' ");
				sqlstr.append("and ved.create_time<='"+entime+"' ");
				sqlstr.append("union all ");
				sqlstr.append("select es.disease_name  as item_code,'总检室' as deptname from examinfo_disease es where ");
				sqlstr.append("es.create_time>='"+sttime+"' ");
				sqlstr.append("and es.create_time<='"+entime+"' ");
				sqlstr.append(") rr ");
				sqlstr.append("group by rr.item_code ");
				sqlstr.append(") aa");
			}
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "实时流量分析:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			if (rs.next()) {
				double counts = rs.getDouble("counts");
				double fmcounts=real_time_traffic_todayall(types,sttimeall,entimeall);
				BigDecimal bd = new BigDecimal(counts/fmcounts*100*40);
				double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				s.setValue(tamt+"");
				String insert="delete from searchtotal_realtime_traffic where today<>'"+entime.substring(0,10)+"' and types='001'";
				connection.createStatement().execute(insert);
				insert="insert into searchtotal_realtime_traffic(today,datetimes,counts,counts1,types) values ('"+entime.substring(0,10)+"','"+entime.substring(11,entime.length())+"','"+counts+"','"+tamt+"','001')";
				connection.createStatement().execute(insert);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	/**
	 * 全天统计
	     * @Title: real_time_traffic_todayall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param types
	     * @param: @param sttime
	     * @param: @param entime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SearchTotalDTO      
	     * @throws
	 */
	private double real_time_traffic_todayall(String types,String sttime,String entime) throws ServiceException{
		Connection connection = null;
		double s= 100;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			if("2".equals(types)){
				sqlstr.append("select sum(aa.counts) as counts from ");
				sqlstr.append(" ( "); 
				sqlstr.append("select rr.item_code,count(*) as counts from  "); 
				sqlstr.append("( "); 
				sqlstr.append("select ci.item_code,'登记台' as deptname from exam_info ei,examinfo_charging_item eci "); 
				sqlstr.append("left join charging_item ci on ci.id=eci.charge_item_id "); 
				sqlstr.append(" where ei.is_Active='Y' and ei.join_date>='"+sttime+"' "); 
				sqlstr.append("and ei.join_date<='"+entime+"' "); 
				sqlstr.append("and eci.exam_num=ei.exam_num "); 
				sqlstr.append("UNION all "); 
				sqlstr.append("select ci.item_code,'收费处' as deptname from charging_summary_single css,charging_detail_single cds "); 
				sqlstr.append("left join charging_item ci on ci.item_code=cds.charging_item_code "); 
				sqlstr.append(" where css.create_time>='"+sttime+"' "); 
				sqlstr.append("and css.create_time<='"+entime+"' "); 
				sqlstr.append("and cds.summary_id=css.id "); 
				sqlstr.append("UNION all "); 
				sqlstr.append("select ci.item_code,'采血室' as deptname from sample_exam_detail sed,examResult_chargingItem erc "); 
				sqlstr.append("left join charging_item ci on ci.item_code=erc.charging_item_code "); 
				sqlstr.append(" where "); 
				sqlstr.append("  erc.result_type='sample' "); 
				sqlstr.append("and sed.id=erc.exam_id "); 
				sqlstr.append("and  sed.update_time>='"+sttime+"' "); 
				sqlstr.append("and sed.update_time<='"+entime+"' "); 
				sqlstr.append("UNION all "); 
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from common_exam_detail ced,charging_item ci "); 
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id "); 
				sqlstr.append(" where  ced.create_time>='"+sttime+"' "); 
				sqlstr.append("and ced.create_time<='"+entime+"' and ci.item_code=ced.charging_item_code "); 
				sqlstr.append("UNION all "); 
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from view_exam_detail ved, pacs_detail pd,charging_item ci "); 
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id "); 
				sqlstr.append("where ved.pacs_id=pd.id and pd.chargingItem_num=ci.item_code "); 
				sqlstr.append("and ved.create_time>='"+sttime+"' "); 
				sqlstr.append("and ved.create_time<='"+entime+"' "); 
				sqlstr.append("union all "); 
				sqlstr.append("select es.disease_name  as item_code,'总检室' as deptname from examinfo_disease es where "); 
			   sqlstr.append("es.create_time>='"+sttime+"' "); 
				sqlstr.append("and es.create_time<='"+entime+"' "); 
				sqlstr.append(") rr "); 
				sqlstr.append("group by rr.item_code "); 
				sqlstr.append(") aa "); 
			}else{                                                                                                                      
				sqlstr.append("select sum(aa.counts) as counts from ");
				sqlstr.append(" ( ");
				sqlstr.append("select rr.item_code,count(*) as counts from ");
				sqlstr.append("( ");
				sqlstr.append("select ci.item_code,'登记台' as deptname from exam_info ei,examinfo_charging_item eci ");
				sqlstr.append("left join charging_item ci on ci.id=eci.charge_item_id ");
				sqlstr.append(" where ei.is_Active='Y' and ei.join_date>='"+sttime+"' ");
				sqlstr.append("and ei.join_date<='"+entime+"' ");
				sqlstr.append("and eci.examinfo_id=ei.id ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,'收费处' as deptname from charging_summary_single css,charging_detail_single cds ");
				sqlstr.append("left join charging_item ci on ci.id=cds.charging_item_id ");
				sqlstr.append(" where css.create_time>='"+sttime+"' ");
				sqlstr.append("and css.create_time<='"+entime+"' ");
				sqlstr.append("and cds.summary_id=css.id ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,'采血室' as deptname from sample_exam_detail sed,examResult_chargingItem erc ");
				sqlstr.append("left join charging_item ci on ci.id=erc.charging_id ");
				sqlstr.append(" where erc.result_type='sample' ");
				sqlstr.append("and sed.id=erc.exam_id ");
				sqlstr.append("and  sed.update_time>='"+sttime+"' ");
				sqlstr.append("and sed.update_time<='"+entime+"' ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from common_exam_detail ced,charging_item ci ");
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id ");
				sqlstr.append(" where  ced.create_time>='"+sttime+"' ");
				sqlstr.append("and ced.create_time<='"+entime+"' and ci.id=ced.charging_item_id ");
				sqlstr.append("UNION all ");
				sqlstr.append("select ci.item_code,dd.dep_name as deptname from view_exam_detail ved, pacs_detail pd,charging_item ci ");
				sqlstr.append("left join department_dep dd on dd.id=ci.dep_id ");
				sqlstr.append("where ved.pacs_id=pd.id and pd.chargingItem_num=ci.item_code ");
				sqlstr.append("and ved.create_time>='"+sttime+"' ");
				sqlstr.append("and ved.create_time<='"+entime+"' ");
				sqlstr.append("union all ");
				sqlstr.append("select es.disease_name  as item_code,'总检室' as deptname from examinfo_disease es where ");
				sqlstr.append(" es.create_time>='"+sttime+"' ");
				sqlstr.append("and es.create_time<='"+entime+"' ");
				sqlstr.append(") rr ");
				sqlstr.append("group by rr.item_code ");
				sqlstr.append(") aa ");
			}
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "全天实时流量分析:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			if (rs.next()) {
				s = rs.getDouble("counts");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
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
	public List<MonthlyAmt> flow_trend_chart(String types) throws ServiceException{
		List<MonthlyAmt> slist=new ArrayList<MonthlyAmt>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select datetimes,counts,counts1 from searchtotal_realtime_traffic where types='"+types+"' order by datetimes ");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "当日流量趋势:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				MonthlyAmt s= new MonthlyAmt();
				s.setValue(rs.getString("counts"));
				s.setValue1(rs.getString("counts1"));
				s.setName(rs.getString("datetimes"));				
				slist.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return slist;
	}
	
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
	public List<SearchTotalDTO> annual_unit_growth() throws ServiceException {
		List<SearchTotalDTO> list = new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();// this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
			sqlstr.append("select mm.years,mm.counts from ("
					+ "select DATEPART(yyyy,create_time) as years,count(*) as counts from company_info ci "
					+ " where ci.is_Active='Y' " + " group by DATEPART(yyyy,create_time) "
					+ ") mm order by  mm.years ");

			TranLogTxt.liswriteEror_to_txt("综合查询分析", "年度单位组成:" + sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s = new SearchTotalDTO();

				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("years") + "年");
				list.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
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
	public List<SearchTotalDTO> annual_personnel_growth() throws ServiceException{		
		List<SearchTotalDTO> list = new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select mm.years,mm.counts from ("
						+ "select DATEPART(yyyy,create_time) as years,count(*) as counts from customer_info ci "
						+ "where ci.is_Active='Y' "
						+ "group by DATEPART(yyyy,create_time) "
						+ ") mm order by  mm.years ");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "年度人员组成:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("years")+"年");				
				list.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 月度总检条数
	     * @Title: count_number_checks   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public List<SearchTotalDTO> count_number_checks(String stime,String etime) throws ServiceException{
		List<SearchTotalDTO> list = new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select mm.months,mm.counts from ("
						+ "select CONVERT(varchar(7) ,ed.create_time, 120) as months,count(*) as counts "
						+ "from examinfo_disease ed where ed.isActive='Y' "
						+ "and ed.create_time>='"+stime+"' "
						+ "and ed.create_time<='"+etime+"' "
						+ "group by CONVERT(varchar(7) ,ed.create_time, 120) "
						+ ")mm order by mm.months ");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "月度总检条数:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();
				
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("months"));				
				list.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 人员次数组成
	     * @Title: annual_personnel_growth   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public List<SearchTotalDTO> statistics_personnel_times() throws ServiceException{
		List<SearchTotalDTO> list = new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select mm.counts as names,count(*) counts from ( "
						+ "select ci.arch_num,count(*) counts from customer_info ci,exam_info ei "
						+ "where ci.is_Active='Y' and ei.customer_id=ci.id and ei.is_Active='Y' "
						+ "group by ci.arch_num "
						+ ")mm where mm.counts<=10 group by mm.counts ");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "年度人员组成:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();				
				s.setValue(rs.getString("counts"));
				s.setName(rs.getString("names")+"次");				
				list.add(s);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 年龄段疾病组成
	     * @Title: age_group_disease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SearchTotalDTO>      
	     * @throws
	 */
	public SearchTotalAllDTO age_group_disease() throws ServiceException{
		List<SearchTotalDTO> list = new ArrayList<SearchTotalDTO>();
		List<SearchTotalDTO> list1 = new ArrayList<SearchTotalDTO>();
		Connection connection = null;
		try {
			connection = getConnection();//this.jqm.getConnection();
			StringBuilder sqlstr = new StringBuilder();
				sqlstr.append("select mm.sex,mm.ages,sum(mm.counts) as counts "
						+ "from ("
						+ "select ci.sex,Case When ei.age <=20 Then '20以下'"
						+ "  When ei.age Between 21 And 25 Then '21-25'"
						+ "  When ei.age Between 26 And 30 Then '26-30'"
						+ "  When ei.age Between 31 And 35 Then '31-35'"
						+ "  When ei.age Between 36 And 40 Then '36-40'"
						+ "  When ei.age Between 41 And 45 Then '41-45'"
						+ "  When ei.age Between 46 And 50 Then '46-50岁'"
						+ "  When ei.age Between 51 And 55 Then '51-55岁'"
						+ "  When ei.age Between 56 And 60 Then '56-60岁'"
						+ "  When ei.age Between 61 And 65 Then '61-65岁'"
						+ "  When ei.age Between 66 And 70 Then '66-70岁'"
						+ "  When ei.age Between 71 And 75 Then '71-75岁'"
						+ "  When ei.age Between 76 And 80 Then '71-80岁'"
						+ "  else '80岁以上'"
						+ "  end as ages,count(*) counts "
						+ "from exam_info ei,customer_info ci,examinfo_disease ed where ei.is_Active='Y' "
						+ "and ed.isActive='Y' "
						+ "and ci.id=ei.customer_id "
						+ "and ei.id=ed.exam_info_id "
						+ "group by ci.sex,ei.age "
						+ ") mm group by mm.sex,mm.ages ");
			
			 TranLogTxt.liswriteEror_to_txt("综合查询分析", "年龄段疾病组成:"+sqlstr.toString());
			ResultSet rs = connection.createStatement().executeQuery(sqlstr.toString());
			
			while (rs.next()) {
				SearchTotalDTO s= new SearchTotalDTO();	
				String sex=rs.getString("sex");
				if("男".equals(sex)){
					s.setName(rs.getString("ages"));
					s.setValue(rs.getLong("counts")+"");
					list.add(s);
				}if("女".equals(sex)){
					s.setName(rs.getString("ages"));
					s.setValue(rs.getLong("counts")+"");
					list1.add(s);
				}
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		SearchTotalAllDTO s= new SearchTotalAllDTO();
		s.setSlist(list);
		s.setSlist1(list1);
		return s;
	}
	
}


