package com.hjw.crm.service.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.CrmSalesCostTrDTO;
import com.hjw.crm.service.CrmMemberManageService;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.model.ExamSummaryModel;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class CrmMemberManageServiceImpl implements CrmMemberManageService{
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private PersistenceManager persistenceManager;
	
	public QueryManager getQueryManager() {
		return queryManager;
	}
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}
	public JdbcQueryManager getJdbcQueryManager() {
		return jdbcQueryManager;
	}
	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}
	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jdbcPersistenceManager;
	}
	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}
	public PersistenceManager getPersistenceManager() {
		return persistenceManager;
	}
	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}
	//程序自动升级会员，修改积分
	/*@Override
	public String saveCrmMemberStore(ExamSummaryModel model) {
		String flag="";
		String message="";
		BigDecimal yiqian=new BigDecimal(1000);
		BigDecimal liangqian=new BigDecimal(2000);
		BigDecimal sanqian=new BigDecimal(3000);
		BigDecimal siqian=new BigDecimal(4000);
		BigDecimal wuqian=new BigDecimal(5000);
		flag=getCrmExamSummaryFlag(model.getExam_num());
		if(flag!=null&&flag.trim().length()>0){
		String[] strs=new String[]{};
		strs=flag.split("!");
		String arch_num=strs[1];
		String exam_info_id=strs[2];
		if(!strs[0].equals("1")){
			String money=getExamMoney(model.getExam_num());
			if(money==null){
				money="0";
			}
			Double d=Double.valueOf(money);
			int m=Double.valueOf(money).intValue();
			CustomerMemberInfo customerMemberInfo=new CustomerMemberInfo();
			List<CustomerMemberInfo> list=getCrmMoneyLevel(arch_num);
			if(list!=null&&list.size()>0){
				customerMemberInfo=list.get(0);
				Double amount=customerMemberInfo.getTotalamt();
				if(amount==null){
					amount=(double) 0;
				}
				BigDecimal x = new BigDecimal(d);
				BigDecimal y = new BigDecimal(amount);
				BigDecimal amounts = x.add(y);
				customerMemberInfo.setTotalamt(amounts.doubleValue());
				String jine="0";
				if(amounts.compareTo(yiqian)==-1){
					jine="0";
				}else if(amounts.compareTo(yiqian)==0){
					jine="1";
				}else if(amounts.compareTo(yiqian)==1&&amounts.compareTo(liangqian)==-1){
					jine="1";
				}else if(amounts.compareTo(liangqian)==0){
					jine="2";
				}else if(amounts.compareTo(liangqian)==1&&amounts.compareTo(sanqian)==-1){
					jine="2";
				}else if(amounts.compareTo(sanqian)==0){
					jine="3";
				}else if(amounts.compareTo(sanqian)==1&&amounts.compareTo(siqian)==-1){
					jine="3";
				}else if(amounts.compareTo(siqian)==0){
					jine="4";
				}else if(amounts.compareTo(siqian)==1&&amounts.compareTo(wuqian)==-1){
					jine="4";
				}else if(amounts.compareTo(wuqian)==0){
					jine="5";
				}else if(amounts.compareTo(wuqian)==1){
					jine="5";
				}
				String tijiancishu=getExamCount(model.getExam_num());
				customerMemberInfo.setTotaltimes(Long.valueOf(tijiancishu));
				String jine1="0";
				int cishuint=Integer.parseInt(tijiancishu);
				
				if(cishuint<11&&cishuint==2){
					jine1="1";
				}else if(cishuint<11&&cishuint==4){
					jine1="2";
				}else if(cishuint<11&&cishuint==6){
					jine1="3";
				}else if(cishuint<11&&cishuint==8){
					jine1="4";
				}else if(cishuint<11&&cishuint==10){
					jine1="5";
				}else if(cishuint>=11){
					jine1="5";
				}else{
					jine1="0";
				}
				int jineint=0;
				jineint=Integer.parseInt(jine)+Integer.parseInt(jine1);
				Date date=new Date();
				customerMemberInfo.setPrelevel(customerMemberInfo.getLevel());
				customerMemberInfo.setLeveltime(date);
				customerMemberInfo.setIntegeraltime(date);
				long hyjbid=this.getIdByhyjb();
				long hid=Long.valueOf(hyjbid);
				if(jineint<6&&(customerMemberInfo.getLevel()-hid)<=jineint){
					
					customerMemberInfo.setLevel(jineint+hid);
					message="会员等级为"+getHuiYuanLevel(jineint);
				}else{
					customerMemberInfo.setLevel(hid);
					message="会员等级为五星";
				}
				customerMemberInfo.setPreintegral(customerMemberInfo.getIntegral());
				customerMemberInfo.setIntegral(customerMemberInfo.getIntegral()+m);
				message=message+",积分为"+(customerMemberInfo.getIntegral());
				updateFlag(exam_info_id);
			}else{
				message="该体检者还未成为会员";
			}
		}else{
			message="该会员信息已经记录";
		}
		}else{
			message="没有该体检者记录";
		}
		return message;
	}*/
	private long getIdByhyjb(){
		String sql="from DataDictionary d where d.data_code='HYJB' and d.data_name='普通会员'";
		List<DataDictionary> list=queryManager.find(sql);
		if(list.size() != 0){
			return list.get(0).getId();
		}
		return 0;
		
	}
	private void updateFlag(String exam_num){
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update exam_summary  set flag='1' where exam_info_id='"+exam_num+"'";
			int rs = connection.createStatement().executeUpdate(sql);
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	private String getHuiYuanLevel(int level){
		String levelChi="";
		String levelstr=String.valueOf(level);
		switch (levelstr) {
		case "0":
			levelChi="普通会员";
			break;
		case "1":
			levelChi="一星会员";
			break;
		case "2":
			levelChi="二星会员";
			break;
		case "3":
			levelChi="三星会员";
			break;
		case "4":
			levelChi="四星会员";
			break;
		case "5":
			levelChi="五星会员";
			break;
		}
		return levelChi;
	}
	//获取之前金额和等级
	private List<CustomerMemberInfo> getCrmMoneyLevel(String arch_num){
		String sql="from CustomerMemberInfo c where c.arch_num='"+arch_num+"'";
		List<CustomerMemberInfo> list=queryManager.find(sql);
		return list;
	}
	//获取根据总检表记录是否已经升级会员
	private String getCrmExamSummaryFlag(String exam_num){
		String sql="select  s.flag,c.arch_num,e.id as strs from  exam_summary s "
				+ " left join exam_info e on s.exam_num=e.exam_num "
				+ " left join  customer_info c on c.id=e.customer_id"
				+ " where  e.exam_num='"+exam_num+"' ";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1)+"!"+rs.getString(2)+"!"+rs.getString(3);
				list.add(str);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list.get(0);
	}
	//查询体检次数
	private String getExamCount(String exam_num){
		String sql=" select count(*) as counts from exam_info e left join customer_info c on c.id=e.customer_id "
				+ " where  e.exam_num='"+exam_num+"' ";
		List<String> list=getConnectionString(sql);
		return list.get(0);
	}
	//查询金额
	/*private String getExamMoney(String exam_num){
		String sql=" select sum(e.amount) as sums from examinfo_charging_item e left join exam_info s on s.exam_num=e.exam_num"
				+ " where  s.exam_num='"+exam_num+"' and e.isActive='Y' and e.pay_status='Y' and e.center_num = '"+center_num+"' ";
		List<String> list=getConnectionString(sql);
		return list.get(0);
	}*/
	private List<String> getConnectionString(String sql){
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1);
				list.add(str);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	@Override
	public CustomerMemberInfo getUpdatePageMmember(String arch_num) {
		String sql="from CustomerMemberInfo c where c.arch_num='"+arch_num+"'";
		List<CustomerMemberInfo> list=queryManager.find(sql);
		CustomerMemberInfo c =new CustomerMemberInfo();
		c=list.get(0);
		return c;
	}
	@Override
	public String updateCustomerMemberInfo(String arch_num,String level) {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update customer_member_info set level='"+level+"' , leveltime=getdate() where arch_num='"+arch_num+"'";
			System.out.println(sql);
			int rs = connection.createStatement().executeUpdate(sql);
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	@Override
	public List<CrmSalesCostTrDTO> getLevel() throws ServiceException, SQLException {
		String sql=" select d.id,d.data_name from data_dictionary d where d.data_code='HYJB'";
		Connection connection = null;
		Statement statement = null;
		List<CrmSalesCostTrDTO> list = new ArrayList<CrmSalesCostTrDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				CrmSalesCostTrDTO jd = new CrmSalesCostTrDTO();
				jd.setLevel_num(rs.getString("id"));
				jd.setLevel_name(rs.getString("data_name"));
				list.add(jd);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return list;
	}
	@Override
	public String updateJiFenLing(String arch_num) {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jdbcQueryManager.getConnection();
			sql ="update customer_member_info set integral='0' , integeraltime=getdate() where arch_num='"+arch_num+"'";
			System.out.println(sql);
			int rs = connection.createStatement().executeUpdate(sql);
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}finally{
			try {
				if(connection!=null){
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
	}
	@Override
	public String getLevelName(String level) throws ServiceException, SQLException {
		String sql="select d.data_name from data_dictionary d where d.id='"+level+"'";
		Connection connection = null;
		Statement statement = null;
		List<String> list = new ArrayList<String>();
		String flag="0";
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String str=new String();
				str=rs.getString(1);
				list.add(str);
				flag="1";
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		if(flag=="1"){
			return list.get(0);
		}else{
			return null;
		}
	}

}
