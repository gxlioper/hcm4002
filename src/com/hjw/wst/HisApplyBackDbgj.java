package com.hjw.wst;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;


import com.hjw.interfaces.DBServer.OracleDatabaseSource;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.ChargingSummarySingleDTO;
import com.hjw.wst.DTO.UserFeeDTO;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CommService;

/**
 * 东北国际HIS收费回写
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年3月13日 下午5:14:45   
     * @version V2.0.0.0
 */
public class HisApplyBackDbgj {

	private SqlConnect sqlConnect;
	
	public void setSqlConnect(SqlConnect sqlConnect) {
		this.sqlConnect = sqlConnect;
	}
	
	private CommService commService;
	
	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	private CollectFeesService collectFeesService;
	
	public void setCollectFeesService(CollectFeesService collectFeesService) {
		this.collectFeesService = collectFeesService;
	}


	public void readerWriteHisApply(){
		try {
			Connection connection = null;
			Statement st = null;
			
			List<ChargingSummarySingleDTO> list = collectFeesService.getChargingSummary();
			TranLogTxt.liswriteEror_to_txt("yibaoka_log","res:预交费申请单查询成功，共"+list.size()+"个申请单。");
			if(list.size() > 0 ){
				try {
					connection = OracleDatabaseSource.getConnection(sqlConnect.getIp(), sqlConnect.getUser(), sqlConnect.getPassword());
					//3.获得操作数据库声明
			        st=connection.createStatement();//Statement声明   createStatement创建声明
					for(ChargingSummarySingleDTO chargingSummarySingle : list){
						ResultSet rs=st.executeQuery("select * from tjpt.v_tj_upload_detail c where c.申请单号 = '"+chargingSummarySingle.getReq_num()+"'");
						
						//遍历结果集   遍历肯定是个循环
				        //next() 判断是否存在下一条记录，如果存在就移动指针到下一条记录上
				        if(rs.next())
				        {
				        	TranLogTxt.liswriteEror_to_txt("yibaoka_log","res:查询HIS视图成功,申请单号为"+chargingSummarySingle.getReq_num()+"数据存在!");
				            //读取数据
				            String patient_id=rs.getString("PATIENT_ID");
				            String name=rs.getString("NAME");  //姓名
				            String rcpt=rs.getString("RCPT_NO"); //收据号
				            String operator=rs.getString("OPERATOR_NO");  //收费员
				            Timestamp visit_date=rs.getTimestamp("VISIT_DATE"); //收费日期
				            String invoice_num=rs.getString("发票编号");    //发票编号
				            String req_num=rs.getString("申请单号");     //申请单号
				            Double amount=rs.getDouble("TOTAL_COSTS");  //总金额
				            Double amount2=rs.getDouble("TOTAL_CHARGES"); //应收金额
				            String charging_way=rs.getString("收费方式"); //收费方式
				            
				            UserFeeDTO uf = collectFeesService.saveCollectFeesYiBao(rcpt, operator, visit_date, invoice_num, req_num, amount2,chargingSummarySingle.getId());
				            
				            if(uf.isFlags()){
				            	TranLogTxt.liswriteEror_to_txt("yibaoka_log","res:医保收费明细回写成功！申请单号为"+req_num);
								this.commService.sendPacsLis(uf);
							}else{
								TranLogTxt.liswriteEror_to_txt("yibaoka_log","err:医保收费明细回写失败！申请单号为"+req_num+",失败原因："+uf.getError());
							}
				        }else{
				        	TranLogTxt.liswriteEror_to_txt("yibaoka_log","res:查询HIS视图成功,申请单号为"+chargingSummarySingle.getReq_num()+"数据不存在!");
				        }
						
				        rs.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					TranLogTxt.liswriteEror_to_txt("yibaoka_log","err:"+e.getMessage());
				}finally {
					try {
						if (st != null) {
							st.close();
						}
						if (connection != null) {
							connection.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			TranLogTxt.liswriteEror_to_txt("yibaoka_log","err:"+e.getMessage());
		}
	}
}
