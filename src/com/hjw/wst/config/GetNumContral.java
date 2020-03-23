package com.hjw.wst.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hjw.util.Configuration;


/**
 * Title: SchoolConfig.java Description:
 * Copyright: Copyright (c)
 * 2009 Company: Syntong
 * 
 * @author yangm
 * @date Mar 23, 2009
 * @version 2.6.0.0
 */
public class GetNumContral{

	volatile private static GetNumContral instance = null;
	
	/**
	 * Description: Title: getInstance
	 * 
	 * @author yangm
	 * @param  
	 * @date Mar 24, 2009
	 * @return
	 */
	public static GetNumContral getInstance() {
		try {    
			if(instance != null){//懒汉式   
	                  
			}else{  
				//创建实例之前可能会有一些准备性的耗时工作   
				Thread.sleep(300);  
				synchronized (GetNumContral.class) {  
					if(instance == null){//二次检查  
						instance = new GetNumContral();  
					}  
				}  
			}   
		} catch (InterruptedException e) {   
			e.printStackTrace();
		}  
		return instance; 
	}

	/**
	 * sys_param表中的param_id的值(exam_no:体检号；barcode：条码号；vipno：档案号; lis_order_no:lis用117);
                               pacs:117PACS序号; studyid: 117医院pacs库studyid; code:字典表编码; arch_num_rz：省公务员入职体检档案号
                               contract:合同编号
    */
	public synchronized String getParamNum(String mtypes, String center_num) {
		
		String mm = GetNumContral.class.getClassLoader().getResource("").getPath();
        String syspath = mm.substring(0, mm.length() - 8)+ "config/pro.properties";
        Configuration rc = new Configuration(syspath);
        String url = rc.getValue("jdbc.url");
        String password = rc.getValue("jdbc.password").trim();
        String username = rc.getValue("jdbc.username").trim();
        String sqldriver=rc.getValue("jdbc.driverClassName").trim();
        Connection con=null;
        String value="";
		try
		 {
			Class.forName(sqldriver);
		    con=DriverManager.getConnection(url,username,password);
		    CallableStatement c = con.prepareCall("{call P_GetSysParam(?,?,?)}");
			c.setString(1, mtypes);
			c.registerOutParameter(2, java.sql.Types.NVARCHAR);
			c.setString(3, center_num);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			value = c.getString(2);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally {
				try {
					
					if (con != null) {
						con.close();
					}
				} catch (SQLException sqle4) {
					System.out.println("close connection exception: " + sqle4.getMessage());
				}
			}
		return value;
	}

	/**
	 * 
	     * @Title: pro_exam_autoGroup   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @param user_id
	     * @param: @param centernum
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
    public synchronized int pro_exam_autoGroup(long batchid, String user_id, String centernum) {		
    	int typeid = 0;
		String mm = GetNumContral.class.getClassLoader().getResource("").getPath();
        String syspath = mm.substring(0, mm.length() - 8)+ "config/pro.properties";
        Configuration rc = new Configuration(syspath);
        String url = rc.getValue("jdbc.url");
        String password = rc.getValue("jdbc.password").trim();
        String username = rc.getValue("jdbc.username").trim();
        String sqldriver=rc.getValue("jdbc.driverClassName").trim();
        Connection con=null;
		try
		 {
			Class.forName(sqldriver);
		    con=DriverManager.getConnection(url,username,password);
		    System.out.println("调用存储过程：pro_exam_autoGroup6(" + batchid + "," + user_id + "," + centernum + ")");
			// 获取档案号
			CallableStatement c = con.prepareCall("{call pro_exam_autoGroup6(?,?,?,?)}");
			c.setString(1, batchid + "");
			c.setString(2, user_id);
			c.setString(3, centernum);
			// 注册存储过程的第二个参数
			c.registerOutParameter(4, java.sql.Types.INTEGER);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			typeid = c.getInt(4);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally {
				try {					
					if (con != null) {
						con.close();
					}
				} catch (SQLException sqle4) {
					System.out.println("close connection exception: " + sqle4.getMessage());
				}
			}
		return typeid;
	}
    
    /**
     * 
         * @Title: pro_exam_enforceGroup   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @param group_id
         * @param: @param user_id
         * @param: @param centernum
         * @param: @param examids
         * @param: @return      
         * @return: int      
         * @throws
     */
    public synchronized int pro_exam_enforceGroup(long group_id, String user_id, String centernum, String examNums) {		
    	int typeid = 0;
		String mm = GetNumContral.class.getClassLoader().getResource("").getPath();
        String syspath = mm.substring(0, mm.length() - 8)+ "config/pro.properties";
        Configuration rc = new Configuration(syspath);
        String url = rc.getValue("jdbc.url");
        String password = rc.getValue("jdbc.password").trim();
        String username = rc.getValue("jdbc.username").trim();
        String sqldriver=rc.getValue("jdbc.driverClassName").trim();
        Connection con=null;
		try
		 {
			Class.forName(sqldriver);
		    con=DriverManager.getConnection(url,username,password);
		    System.out.println(
					"调用存储过程：pro_exam_enforceGroup6(" + group_id + "," + examNums + "," + user_id + "," + centernum + ")");
			// 获取档案号
			CallableStatement c = con.prepareCall("{call pro_exam_enforceGroup6(?,?,?,?,?)}");
			c.setString(1, group_id + "");
			c.setString(2, examNums);
			c.setString(3, user_id);
			c.setString(4, centernum);
			// 注册存储过程的第二个参数
			c.registerOutParameter(5, java.sql.Types.INTEGER);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			typeid = c.getInt(5);

			System.out.println("调用存储过程：pro_exam_enforceGroup6(" + group_id + "," + examNums + "," + user_id + ","
					+ centernum + ") 返回：" + typeid);
		 }catch(Exception ex){
			 ex.printStackTrace();
		 }finally {
				try {					
					if (con != null) {
						con.close();
					}
				} catch (SQLException sqle4) {
					System.out.println("close connection exception: " + sqle4.getMessage());
				}
			}
		return typeid;
	}
}
