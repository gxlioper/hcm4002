package com.hjw.wst.config;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.hjw.interfaces.DBServer.SqlServerDatabaseSource;
import com.hjw.wst.DTO.DataBaseConfigDTO;

public class GetOtherNumContral {

	volatile private static GetOtherNumContral instance = null;
	
	/**
	 * Description: Title: getInstance
	 * 
	 * @author yangm
	 * @param  
	 * @date Mar 24, 2009
	 * @return
	 */
	public static GetOtherNumContral getInstance() {
		try {    
			if(instance != null){//懒汉式   
	                  
			}else{  
				//创建实例之前可能会有一些准备性的耗时工作   
				Thread.sleep(300);  
				synchronized (GetOtherNumContral.class) {  
					if(instance == null){//二次检查  
						instance = new GetOtherNumContral();  
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
	public synchronized String getParamNum(String mtypes, String center_num,DataBaseConfigDTO dto) {
		
		String url = "jdbc:sqlserver://"+dto.getDatabase_url()+":"+dto.getDatabase_port()+";DatabaseName="+dto.getDatabase_uame();
        Connection con=null;
        String value="";
		try
		 {
		    con= SqlServerDatabaseSource.getConnection(url, dto.getUsername(), dto.getPassword());
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
}
