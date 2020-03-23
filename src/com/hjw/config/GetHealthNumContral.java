package com.hjw.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.hjw.util.Configuration;

public class GetHealthNumContral {
	volatile private static GetHealthNumContral instance = null;
	
	/**
	 * Description: Title: getInstance
	 * 
	 * @author yangm
	 * @param  
	 * @date Mar 24, 2009
	 * @return
	 */
	public static GetHealthNumContral getInstance() {
		try {    
			if(instance != null){//懒汉式   
	                  
			}else{  
				//创建实例之前可能会有一些准备性的耗时工作   
				Thread.sleep(300);  
				synchronized (GetHealthNumContral.class) {  
					if(instance == null){//二次检查  
						instance = new GetHealthNumContral();  
					}  
				}  
			}   
		} catch (InterruptedException e) {   
			e.printStackTrace();
		}  
		return instance; 
	}

	/**
	 * 
    */
	public synchronized String getHealthNum(String param_code) {	
		
		String mm = GetHealthNumContral.class.getClassLoader().getResource("").getPath();
        String syspath = mm.substring(0, mm.length() - 8)+ "config/pro.properties";
        Configuration rc = new Configuration(syspath);
        String url = rc.getValue("jdbc.url");
        String password = rc.getValue("jdbc.password").trim();
        String username = rc.getValue("jdbc.username").trim();
        String sqldriver=rc.getValue("jdbc.driverClassName").trim();
        Connection con=null;
        String getHealthNum="";
		try
		 {
			Class.forName(sqldriver);
		    con=DriverManager.getConnection(url,username,password);
		    CallableStatement c = con.prepareCall("{call pro_get_health_no(?,?)}");
			c.setString(1, param_code);
			// 注册存储过程的第二个参数
			c.registerOutParameter(2, java.sql.Types.NVARCHAR);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			getHealthNum = c.getString(2);
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
		return getHealthNum;
	}
}
