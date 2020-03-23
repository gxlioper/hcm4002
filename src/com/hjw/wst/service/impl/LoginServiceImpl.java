package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.hjw.config.TermConfig;
import javax.sql.RowSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.hjw.wst.service.LoginService;
import com.synjones.framework.exception.DAOException;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.QueryManager;

/*
 * Title:       金龙卡金融化一卡通网站查询、触摸屏查询子系统
 * Description: 新中新集团
 * 创建日期：     Jun 8, 200711:00:02 AM
 * 项目名称：     webschoolcard
 * 所在包名：     com.synjones.wst.service.impl
 *
 * Copyright:   Copyright (c) 2007－2008
 * Company:     syntongs
 * author       yangm
 * version      2.3.1.0
 */

public class LoginServiceImpl implements LoginService {
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	static final Log log = LogFactory.getLog(LoginServiceImpl.class);
	
	/**
	 * @param queryManager
	 *            The queryManager to set.
	 */
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	/**
	 * @return Returns the queryManager.
	 */
	public QueryManager getQueryManager() {
		return queryManager;
	}

	/**
	 * @param jdbcQueryManager
	 *            The jdbcQueryManager to set.
	 */
	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}


	public String getUnifiedauthUrl() throws ServiceException {
		String authUrl = "";
		String sqltext = "select SYS_URL as authUrl from tab_system where id=1";
		try {
			// 读取记录数
			RowSet rs = this.jdbcQueryManager.getRowSet(sqltext);
			if (rs.next()) {
				authUrl = rs.getString("authUrl");
			}
		} catch (Exception e) {
			throw new DAOException(e);
		}
		return authUrl;
	}



	public String getRolegn(int role_id) throws ServiceException
	{
		String sqltext = "select d.xtgn_id xtgnid from web_role b,web_rolemenu c,web_xtgncd d where  b.id=c.role_id " +
				"and d.xtgn_id<>0 " +
				"and b.id="+role_id+" and c.gncd_id=d.id " +
				"group by d.xtgn_id";
	Connection connection = null;
	Statement statement = null;
	String gwgn = "";
	try {
		// 读取记录数
		connection = this.jdbcQueryManager.getConnection();
		statement = connection.createStatement();
		ResultSet rs = statement.executeQuery(sqltext);
		while (rs.next()) {
			gwgn = gwgn + "" + rs.getString("xtgnid") + ",";
		}
		if (gwgn.indexOf(",") > 0)
			gwgn = gwgn.substring(0, gwgn.length() - 1);
		rs.close();
	} catch (SQLException ex) {
		log.error(ex.toString());
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
			log.info("close connection exception: " + sqle4.getMessage());
		}
	}
	return gwgn;
	}

	/**
	 * 获取功能菜单
	* @param gnid
	* @return
	* @throws ServiceException
	 */
	public boolean getXtgncdByGnid(String gnid,String usertype) throws ServiceException
	{
		String sqltext = "select count(*) count from web_xtgncd t where t.usertype="+usertype+" and xtgn_id="+gnid;
		Connection connection = null;
		Statement statement = null;
		Boolean gwgn = false;
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			if (rs.next()) {
				int counts = rs.getInt("count");
				if (counts>0 ) gwgn=true;
			}
			rs.close();
		} catch (SQLException ex) {
			log.error(ex.toString());
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
				log.info("close connection exception: " + sqle4.getMessage());
			}
		}
		return gwgn;
	}
	
	@SuppressWarnings("rawtypes")
	public WebXtgncd getXtgncdByGnid(String gnid) throws ServiceException {
		WebXtgncd wx = new WebXtgncd();
		String sHql = "FROM WebXtgncd wx WHERE wx.xtgn_id = '"+ gnid + "'";
		List resList = queryManager.find(sHql);		
		if (resList.size() > 0) {
			wx= (WebXtgncd) resList.get(0);
		
			
		} 
		return wx;
	}
	
	@SuppressWarnings("rawtypes")
	public WebXtgncd getXtgncdById(String id) throws ServiceException {
		WebXtgncd wx = new WebXtgncd();
		String sHql = "FROM WebXtgncd wx WHERE wx.id = '"+ id + "'";
		List resList = queryManager.find(sHql);
		
		if (resList.size() > 0) {
			wx= (WebXtgncd) resList.get(0);
		} 
		return wx;
	}

	/**
	 * 获取系统功能
	 * 
	 * @param saction
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	 */
	public WebXtgnb getWebXtgnb(String saction,String center_num) throws ServiceException {
		String sqltext = "select id,url,name,type,remark,fatheraction,actiontype,admintype,apptype from Web_Xtgnb t where upper(t.url) like '%"
				+ saction + "%'";
		Connection connection = null;
		Statement statement = null;
		WebXtgnb wx = new WebXtgnb();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			if (rs.next()) {
				wx.setId(rs.getString("id"));
				wx.setActiontype(rs.getString("actiontype"));
				wx.setFatheraction(rs.getString("fatheraction"));
				wx.setName(rs.getString("name"));
				wx.setRemark(rs.getString("remark"));
				wx.setType(rs.getString("type"));
				wx.setUrl(rs.getString("url"));
				wx.setAdmintype(rs.getString("admintype"));
				wx.setApptype(rs.getString("apptype"));
                wx=TermConfig.setWebXtgnb(wx,center_num);				
			}
			rs.close();
		} catch (SQLException ex) {
			log.error(ex.toString());
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
				log.info("close connection exception: " + sqle4.getMessage());
			}
		}
		return wx;
	}

	
    /**
     * 通过商户部门3位 获取商户
    * @param deptcode
    * @return
    * @throws ServiceException
    * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List getCompanyAccountBydept(List companyAccountlist,String deptcode)throws ServiceException
    {
    	String str="select t.account from company_account t where t.deptcode like '"+deptcode+"%' ";
    	Connection connection = null;
		Statement statement = null;
		
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(str);
			while (rs.next()) {
				long companyAccount = rs.getLong("account");
				boolean flag=true;
				for(int i=0;i<companyAccountlist.size();i++)
				{
					long merc = (Long)companyAccountlist.get(i);
					if (companyAccount==(merc))
					{
						flag=false;
						break;
					}
				}
				if (flag) companyAccountlist.add(companyAccount);
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
		return companyAccountlist;
    }
	
}
