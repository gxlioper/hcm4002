package com.hjw.wst.service.impl;

import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.WebRoleDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.DepUser;
import com.hjw.wst.domain.DepUserPrint;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.WebRole;
import com.hjw.wst.domain.WebRoleMenu;
import com.hjw.wst.domain.WebRoleReport;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.domain.WebXtgnb;
import com.hjw.wst.domain.WebXtgncd;
import com.hjw.wst.service.MenuGnService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MenuGnServiceImpl implements MenuGnService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;

	public MenuGnServiceImpl() {
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	@SuppressWarnings("unchecked")
	public WebXtgnb getXtgnByUrl(String url) throws ServiceException {
		String hql = " from WebXtgnb g where g.url='" + url + "'";
		List<WebXtgnb> list = qm.find(hql);
		if (list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

	public WebUserjsb saveWebRyjsb(WebUserjsb wg) throws ServiceException {
		this.pm.save(wg);
		return wg;
	}

	/**
	 * 
	 * @Title: findWebUserjsByUserid @Description: 获取人员的角色 @param: @param
	 * userid @param: @return @param: @throws ServiceException @return:
	 * List @throws
	 */
	@SuppressWarnings("unchecked")
	public List<WebUserjsb> findWebUserjsByUserid(String userid) throws ServiceException {
		return jqm.getList("select * From Web_Userjsb a where a.user_id='" + userid + "'", WebUserjsb.class);
	}

	public WebXtgnb loadWebXtgnb(String id) throws ServiceException {
		return (WebXtgnb) qm.load(WebXtgnb.class, id);
	}

	/**
	 * 
	 * <p>
	 * Title: delUserRole
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param userid
	 * @throws ServiceException
	 * @see com.hjw.wst.service.MenuGnService#delUserRole(java.lang.String)
	 */
	public void delUserRole(String userid,String apptype) throws ServiceException {
		String sql = "delete  from web_userjsb where apptype='"+apptype+"' and user_id='" + userid + "' ";
		this.jpm.execSql(sql);
	}

	@SuppressWarnings("rawtypes")
	public List getXtgn(String type,String apps) throws ServiceException {
		return qm.find("  from WebXtgnb where type='" + type + "' and apptype in ("+apps+") order by name");
	}

	/**
	 * 
	 * @Title: findWebXtgnbById @Description: 获取功能实体类 @param: @param
	 * id @param: @return @param: @throws ServiceException @return:
	 * WebXtgnb @throws
	 */
	public WebXtgnb findWebXtgnbById(String id) throws ServiceException {
		return (WebXtgnb) qm.get(WebXtgnb.class, id);
	}

	@SuppressWarnings("rawtypes")
	public List getMenuGn(String father) throws ServiceException {
		return qm.find("  from WebXtgncd a where a.father_id='" + father + "' order by indexid asc");
	}

	public WebXtgncd saveWebXtgncd(WebXtgncd ws) throws ServiceException {
		this.pm.save(ws);
		return ws;
	}

	public WebXtgncd updateWebXtgncd(WebXtgncd ws) throws ServiceException {
		this.pm.update(ws);
		return ws;
	}

	public WebXtgncd loadWebXtgncd(String id) throws ServiceException {
		return (WebXtgncd) qm.get(WebXtgncd.class, id);
	}

	public WebRole saveWebRole(WebRole wr) throws ServiceException {
		this.pm.save(wr);
		return wr;
	}

	public WebRole updateWebRole(WebRole wr) throws ServiceException {
		this.pm.update(wr);
		return wr;
	}

	public WebRole loadWebRole(String id) throws ServiceException {
		return (WebRole) qm.get(WebRole.class, id);
	}

	public void delWebRole(WebRole wr) throws ServiceException {
		pm.remove(wr);
	}

	public void delWebXtgncd(String id) throws ServiceException {
		WebXtgncd wb = (WebXtgncd) qm.get(WebXtgncd.class, id);
		this.pm.remove(wb);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebXtgncd> findWebXtgncdByUsertype(String usertype) throws ServiceException {
		String str = "select id,xtgn_id,name,father_id,urltype,icon_url,datetime,ry,"
				+ "remark,levels,usertype,other_url,indexid,ispop from Web_Xtgncd a where a.usertype='" + usertype
				+ "'";
		Connection connection = null;
		Statement statement = null;
		List<WebXtgncd> wx = new ArrayList();
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(str);
			while (rs.next()) {
				WebXtgncd w = new WebXtgncd();
				w.setDatetime(rs.getString("datetime"));
				w.setFather_id(rs.getString("father_id"));
				w.setIcon_url(rs.getString("icon_url"));
				w.setId(rs.getString("id"));
				w.setIndexid(rs.getString("indexid"));
				w.setIspop(rs.getLong("ispop"));
				w.setLevels(rs.getLong("levels"));
				w.setName(rs.getString("name"));
				w.setOther_url(rs.getString("other_url"));
				w.setRemark(rs.getString("remark"));
				w.setRy(rs.getString("ry"));
				w.setUrl_type(rs.getString("urltype"));
				w.setUsertype(rs.getString("usertype"));
				w.setXtgn_id(rs.getString("xtgn_id"));
				wx.add(w);
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
		return wx;
	}

	@SuppressWarnings("unchecked")
	public List<WebRoleMenu> fingWebRoleMenu2RoleId(String role_id) throws ServiceException {
		return jqm.getList("select a.ID,a.ROLE_ID,a.GNCD_ID,a.REMARK,a.apptype from Web_RoleMenu a where a.role_id='" + role_id + "'", WebRoleMenu.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<WebRoleReport> fingWebRoleReport2RoleId(String role_id) throws ServiceException {
		return jqm.getList("select * from web_rolereport a where a.role_id='" + role_id + "'", WebRoleReport.class);
	}


	@SuppressWarnings("unchecked")
	public List<WebRole> getWebRule(String apptype) throws ServiceException {
		// return qm.find(" from WebRole ");
		return jqm.getList("select *  from Web_Role where apptype ='"+apptype+"' ", WebRole.class);
	}

	public WebRoleMenu saveWebRoleMenu(WebRoleMenu wrm) throws ServiceException {
		pm.save(wrm);
		return wrm;
	}
	
	/**
	 * 
	     * @Title: saveWebRoleReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param wrm
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebRoleReport      
	     * @throws
	 */
	public WebRoleReport saveWebRoleReport(WebRoleReport wrm) throws ServiceException {
		pm.save(wrm);
		return wrm;
	}

	public void delWebRoleMenu(String rule_id,String apptype) throws ServiceException {
		String sql = "delete  from web_rolemenu where role_id='" + rule_id + "' and apptype= '"+apptype+"'";
		this.jpm.execSql(sql);
	}
	
	/**
	 * 
	     * @Title: delWebRoleReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param rule_id
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delWebRoleReport(String rule_id) throws ServiceException {
		String sql = "delete  from WEB_ROLEREPORT where role_id='" + rule_id + "' ";
		this.jpm.execSql(sql);
	}

	/**
	 * 
	 * @Title: findWebUserjsbByUserid @Description:获取用户角色 @param: @param
	 * userid @param: @param rule_id @param: @return @param: @throws
	 * ServiceException @return: List @throws
	 */
	@SuppressWarnings("unchecked")
	public List<WebUserjsb> findWebUserjsbByUserid(String userid, String rule_id,String apptype) throws ServiceException {
		return jqm.getList(
				"select * From Web_Userjsb a ,WEB_ROLE w where a.user_id='" + userid + "' and a.role_id='" + rule_id + "' and w.ID =a.role_id and w.apptype ='"+apptype+"'",
				WebUserjsb.class);
	}

	/**
	 * 
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public PageReturnDTO getRoleList(int pagesize, int pageno,String apptype) throws ServiceException {
		String sql = "select id,rolename,remark,ry,datetime From  web_role where apptype='"+apptype+"'";
		sql += "order by datetime desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, WebRole.class);
		List mapList = map.getList();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pagesize);
		webrole.setRp(pageno);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}

	/**
	 * 获取功能列表
	 * 
	 * @param joblist
	 * @param userType
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	 */

	public List<WebXtgncdDTO> findWebgnList(String rolestr, String xtgn_ids, String path,String app) throws ServiceException {
		String sqlfather="select id from web_xtgncd where father_id='0' and xtgn_id='0'";
		if("zyb".equals(app)){
			sqlfather="select id from web_xtgncd where father_id='1' and xtgn_id='0'";
		}else if("crm".equals(app)){
			sqlfather="select id from web_xtgncd where father_id='3' and xtgn_id='0'";
		}
		Connection connection = null;
		Statement statement = null;
		List<WebXtgncdDTO> gnlist_new = new ArrayList<WebXtgncdDTO>();
		String fatherid="";
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqlfather);
			System.out.println("获取菜单语句：=" + sqlfather);
			if (rs.next()) {
				fatherid=rs.getString("id");
			}
		rs.close();
		
		
		String sqltext = "with cte_root(Id,father_id,xtgn_id) as "
				+ "(select k.ID,k.father_id,cast(j.GNCD_ID as varchar(50)) as xtgn_id from web_xtgncd k,web_rolemenu j where j.role_id in (" + rolestr
				+ ") and k.id=j.gncd_id group by k.id,k.father_id,j.gncd_id union all "
				+ "select a.ID,a.father_id,cast(a.xtgn_id as varchar(50)) as xtgn_id from  web_xtgncd  AS a inner join cte_root AS b on a.id=b.father_id ) "
				+ "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.icon_url,t.ispop,t.levels,t.usertype,t.other_url "
				+ "from web_xtgncd t,(select ad.id,ad.xtgn_id  from cte_root ad group by ad.id,ad.xtgn_id) md "
				+ "where  t.father_id='"+fatherid+"' and t.id=md.id and t.xtgn_id=md.xtgn_id and t.levels<>1 order by t.INDEXID";
	
		  List<WebXtgncdDTO> gnlist = new ArrayList<WebXtgncdDTO>();
			// 读取记录数
			rs = statement.executeQuery(sqltext);
			System.out.println("获取菜单语句：=" + sqltext);
			while (rs.next()) {
				WebXtgncdDTO xt = new WebXtgncdDTO();
				xt.setFather_id(rs.getString("father_id"));
				xt.setIcon_url(rs.getString("icon_url"));
				xt.setId(rs.getString("id"));
				xt.setLevels(rs.getLong("levels"));
				xt.setName(rs.getString("name"));
				xt.setOther_url(rs.getString("other_url"));
				xt.setUrl_type(rs.getString("urltype"));
				xt.setXtgn_id(rs.getString("xtgn_id"));
				xt.setIspop(rs.getLong("ispop"));
				/*
				 * if ("1".equals(xt.getUrl_type())) { Statement statement1 =
				 * connection.createStatement(); ResultSet rs1 = statement1
				 * .executeQuery("select url from web_xtgnb t where id='" +
				 * xt.getXtgn_id() + "'"); if (rs1.next()) {
				 * xt.setOther_url(path + "" + (rs1.getString("url"))); } else {
				 * xt.setOther_url("#"); } rs1.close(); }
				 */
				gnlist.add(xt);
			}
			rs.close();

			for (int i = 0; i < gnlist.size(); i++) {
				WebXtgncdDTO xt = (WebXtgncdDTO) gnlist.get(i);

				List<WebXtgncdDTO> list = new ArrayList<WebXtgncdDTO>();
				String sql = "select t.id,t.xtgn_id,t.name,t.father_id,m.url,t.urltype,t.ispop,t.icon_url,"
						+ "t.levels,t.usertype,t.other_url from web_xtgncd t,web_xtgnb m " + "where t.xtgn_id in ("
						+ xtgn_ids + ") and  t.father_id='" + xt.getId() + "' and t.xtgn_id=m.id order by t.INDEXID ";
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					WebXtgncdDTO wx = new WebXtgncdDTO();
					wx.setLevels(rs.getLong("levels"));
					wx.setFather_id(rs.getString("father_id"));
					wx.setIcon_url(rs.getString("icon_url"));
					wx.setId(rs.getString("id"));
					wx.setLevels(rs.getLong("levels"));
					wx.setName(rs.getString("name"));
					wx.setOther_url(rs.getString("other_url"));
					wx.setUrl_type(rs.getString("urltype"));
					wx.setXtgn_id(rs.getString("xtgn_id"));
					wx.setIspop(rs.getLong("ispop"));
					if ("1".equals(xt.getUrl_type())) {
						if (rs.getString("url").trim().length() > 5) {
							wx.setOther_url(path + "" + (rs.getString("url")));
						} else {
							wx.setOther_url("#");
						}
					} else {
						wx.setOther_url("#");
					}
					list.add(wx);
				}
				rs.close();
				xt.setList(list);

				gnlist_new.add(xt);

			}

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
				System.out.println("close connection exception: " + sqle4.getMessage());
			}
		}
		return gnlist_new;
	}
	
	/**
	 * 
	     * @Title: findWebReportList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param rolestr
	     * @param: @param reprotmen_ids
	     * @param: @param path
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebXtgncdDTO>      
	     * @throws
	 */
	public List<WebXtgncdDTO> findWebReportList(String rolestr, String reprotmen_ids, String path) throws ServiceException {
		String sqltext="with cte_root(id,parent_id) as "
				+ " (select k.ID,k.parent_id from report_menu k,web_rolereport j "
				+ " where j.role_id in (" + rolestr	+ ") "
				+ " and k.id=j.ROLEMENU_ID group by k.id,k.parent_id,j.ROLEMENU_ID union all "
				+ " select a.id,a.parent_id from  report_menu  AS a inner join cte_root AS b on a.id=b.parent_id ) "
				+ "select t.id,t.parent_id,t.report_name,t.parent_id,t.report_type,t.report_address from  report_menu t,"
				+ " (select ad.id,ad.parent_id  from cte_root ad group by ad.id,ad.parent_id) md "
				+ " where t.id=md.id  order by t.seq_code";
		Connection connection = null;
		Statement statement = null;
		List<WebXtgncdDTO> gnlist = new ArrayList<WebXtgncdDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			System.out.println("获取菜单语句：=" + sqltext);
			while (rs.next()) {
				WebXtgncdDTO xt = new WebXtgncdDTO();
				xt.setFather_id(rs.getString("parent_id"));
				//xt.setIcon_url(rs.getString("icon_url"));
				xt.setId(rs.getString("id"));
				//xt.setLevels(rs.getLong("levels"));
				xt.setName(rs.getString("report_name"));
				xt.setOther_url(rs.getString("report_address"));
				xt.setUrl_type(rs.getString("report_type"));				
				gnlist.add(xt);
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
				System.out.println("close connection exception: " + sqle4.getMessage());
			}
		}
		return gnlist;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<WebXtgncd> findCardWebgnList(String userType, String path) throws ServiceException {
		String sqltext = "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.icon_url,t.ispop,t.levels,t.usertype,t.other_url from web_xtgncd t,"
				+ "(select u.id,u.xtgn_id from web_xtgncd u,(select j.gncd_id as gnid from web_rolemenu j where "
				+ " j.role_id='1' group by j.gncd_id) q "
				+ "start with u.id=q.gnid connect by prior u.father_id=u.id and u.usertype='" + userType
				+ "' and u.levels<>1 group by u.xtgn_id,u.id) bb "
				+ "where t.id=bb.id and bb.xtgn_id=t.xtgn_id and t.usertype='" + userType
				+ "' and t.levels<>1 order by t.indexid";
		Connection connection = null;
		Statement statement = null;
		List<WebXtgncd> gnlist = new ArrayList();
		List<WebXtgncd> gnlist_1 = new ArrayList();
		List<WebXtgncd> gnlist_new = new ArrayList();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			System.out.println("获取普通持卡人菜单语句：=" + sqltext);
			while (rs.next()) {
				WebXtgncd xt = new WebXtgncd();
				xt.setFather_id(rs.getString("father_id"));
				xt.setIcon_url(rs.getString("icon_url"));
				xt.setId(rs.getString("id"));
				xt.setLevels(rs.getLong("levels"));
				xt.setName(rs.getString("name"));
				xt.setOther_url(rs.getString("other_url"));
				xt.setUrl_type(rs.getString("urltype"));
				xt.setXtgn_id(rs.getString("xtgn_id"));
				xt.setIspop(rs.getLong("ispop"));
				if ("1".equals(xt.getUrl_type())) {
					Statement statement1 = connection.createStatement();
					ResultSet rs1 = statement1
							.executeQuery("select url from web_xtgnb t where id='" + xt.getXtgn_id() + "'");
					if (rs1.next()) {
						xt.setOther_url(path + "" + (rs1.getString("url")));
					} else {
						xt.setOther_url("#");
					}
					rs1.close();
				}
				gnlist.add(xt);
				gnlist_1.add(xt);
				gnlist_new.add(xt);
			}
			rs.close();

			for (int i = 0; i < gnlist.size(); i++) {
				WebXtgncd xt = new WebXtgncd();
				xt = (WebXtgncd) gnlist.get(i);
				long level = xt.getLevels();
				String father_id = xt.getFather_id();
				try {
					while ((!"0".equals(father_id)) || (level > 1)) {
						String sql = "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.ispop,t.icon_url,"
								+ "t.levels,t.usertype,t.other_url from web_xtgncd t " + "where t.id='" + father_id
								+ "'";
						rs = statement.executeQuery(sql);
						while (rs.next()) {
							father_id = rs.getString("father_id");
							level = rs.getLong("levels");
							if (level > 1) {
								boolean f = false;
								for (int j = 0; j < gnlist_1.size(); j++) {
									xt = (WebXtgncd) gnlist_1.get(j);
									if (rs.getString("id").equals(xt.getId())) {
										f = true;
										break;
									}
								}
								if (!f) {

									xt.setFather_id(rs.getString("father_id"));
									xt.setIcon_url(rs.getString("icon_url"));
									xt.setId(rs.getString("id"));
									xt.setLevels(rs.getLong("levels"));
									xt.setName(rs.getString("name"));
									xt.setOther_url(rs.getString("other_url"));
									xt.setUrl_type(rs.getString("urltype"));
									xt.setXtgn_id(rs.getString("xtgn_id"));
									xt.setIspop(rs.getLong("ispop"));
									if ("1".equals(xt.getUrl_type())) {
										Statement statement1 = connection.createStatement();
										ResultSet rs1 = statement1.executeQuery(
												"select url from web_xtgnb t where id='" + xt.getXtgn_id() + "'");
										if (rs1.next()) {
											xt.setOther_url(path + "" + (rs1.getString("url")));
										} else {
											xt.setOther_url("#");
										}
										rs1.close();
									}

									gnlist_new.add(xt);
									gnlist_1.add(xt);
								}
							}
						}
						rs.close();
					}
				} catch (Exception ex) {
					System.out.println(ex.toString());
				}

			}

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
				System.out.println("close connection exception: " + sqle4.getMessage());
			}
		}
		return gnlist_new;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WebXtgncd> findMercWebgnList(String userType, String path) throws ServiceException {
		String sqltext = "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.icon_url,t.ispop,t.levels,t.usertype,t.other_url from web_xtgncd t,"
				+ "(select u.id,u.xtgn_id from web_xtgncd u,(select j.gncd_id as gnid from web_rolemenu j where "
				+ " j.role_id='3' group by j.gncd_id) q "
				+ "start with u.id=q.gnid connect by prior u.father_id=u.id and u.usertype='" + userType
				+ "' and u.levels<>1 group by u.xtgn_id,u.id) bb "
				+ "where t.id=bb.id and bb.xtgn_id=t.xtgn_id and t.usertype='" + userType + "' and t.levels<>1";
		Connection connection = null;
		Statement statement = null;
		List<WebXtgncd> gnlist = new ArrayList();
		List<WebXtgncd> gnlist_1 = new ArrayList();
		List<WebXtgncd> gnlist_new = new ArrayList();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			System.out.println("获取商户默认菜单语句：=" + sqltext);
			while (rs.next()) {
				WebXtgncd xt = new WebXtgncd();
				xt.setFather_id(rs.getString("father_id"));
				xt.setIcon_url(rs.getString("icon_url"));
				xt.setId(rs.getString("id"));
				xt.setLevels(rs.getLong("levels"));
				xt.setName(rs.getString("name"));
				xt.setOther_url(rs.getString("other_url"));
				xt.setUrl_type(rs.getString("urltype"));
				xt.setXtgn_id(rs.getString("xtgn_id"));
				xt.setIspop(rs.getLong("ispop"));
				if ("1".equals(xt.getUrl_type())) {
					Statement statement1 = connection.createStatement();
					ResultSet rs1 = statement1
							.executeQuery("select url from web_xtgnb t where id='" + xt.getXtgn_id() + "'");
					if (rs1.next()) {
						xt.setOther_url(path + "" + (rs1.getString("url")));
					} else {
						xt.setOther_url("#");
					}
					rs1.close();
				}
				gnlist.add(xt);
				gnlist_1.add(xt);
				gnlist_new.add(xt);
			}
			rs.close();

			for (int i = 0; i < gnlist.size(); i++) {
				WebXtgncd xt = new WebXtgncd();
				xt = (WebXtgncd) gnlist.get(i);
				long level = xt.getLevels();
				String father_id = xt.getFather_id();
				try {
					while ((!"0".equals(father_id)) || (level > 1)) {
						String sql = "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.ispop,t.icon_url,"
								+ "t.levels,t.usertype,t.other_url from web_xtgncd t " + "where t.id='" + father_id
								+ "'";
						rs = statement.executeQuery(sql);
						while (rs.next()) {
							father_id = rs.getString("father_id");
							level = rs.getLong("levels");
							if (level > 1) {
								boolean f = false;
								for (int j = 0; j < gnlist_1.size(); j++) {
									xt = (WebXtgncd) gnlist_1.get(j);
									if (rs.getString("id").equals(xt.getId())) {
										f = true;
										break;
									}
								}
								if (!f) {

									xt.setFather_id(rs.getString("father_id"));
									xt.setIcon_url(rs.getString("icon_url"));
									xt.setId(rs.getString("id"));
									xt.setLevels(rs.getLong("levels"));
									xt.setName(rs.getString("name"));
									xt.setOther_url(rs.getString("other_url"));
									xt.setUrl_type(rs.getString("urltype"));
									xt.setXtgn_id(rs.getString("xtgn_id"));
									xt.setIspop(rs.getLong("ispop"));
									if ("1".equals(xt.getUrl_type())) {
										Statement statement1 = connection.createStatement();
										ResultSet rs1 = statement1.executeQuery(
												"select url from web_xtgnb t where id='" + xt.getXtgn_id() + "'");
										if (rs1.next()) {
											xt.setOther_url(path + "" + (rs1.getString("url")));
										} else {
											xt.setOther_url("#");
										}
										rs1.close();
									}

									gnlist_new.add(xt);
									gnlist_1.add(xt);
								}
							}
						}
						rs.close();
					}
				} catch (Exception ex) {
					System.out.println(ex.toString());
				}

			}

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
				System.out.println("close connection exception: " + sqle4.getMessage());
			}
		}
		return gnlist_new;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<WebXtgncd> findUserWebgnList(boolean flag, String userid, String userType, String path)
			throws ServiceException {
		String sqltext = "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.icon_url,t.ispop,t.levels,t.usertype,t.other_url from web_xtgncd t,"
				+ "(select u.id,u.xtgn_id from web_xtgncd u,(select j.gncd_id as gnid from web_userjsb m,web_rolemenu j where "
				+ " m.user_id in (" + userid + ") and m.role_id=j.role_id group by j.gncd_id) q "
				+ "start with u.id=q.gnid connect by prior u.father_id=u.id and u.usertype='" + userType
				+ "' and u.levels<>1 group by u.xtgn_id,u.id) bb "
				+ "where t.id=bb.id and bb.xtgn_id=t.xtgn_id and t.usertype='" + userType + "' and t.levels<>1";
		Connection connection = null;
		Statement statement = null;
		List<WebXtgncd> gnlist = new ArrayList();
		List<WebXtgncd> gnlist_1 = new ArrayList();
		List<WebXtgncd> gnlist_new = new ArrayList();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			System.out.println("获取菜单语句：=" + sqltext);
			while (rs.next()) {
				WebXtgncd xt = new WebXtgncd();
				xt.setFather_id(rs.getString("father_id"));
				xt.setIcon_url(rs.getString("icon_url"));
				xt.setId(rs.getString("id"));
				xt.setLevels(rs.getLong("levels"));
				xt.setName(rs.getString("name"));
				xt.setOther_url(rs.getString("other_url"));
				xt.setUrl_type(rs.getString("urltype"));
				xt.setXtgn_id(rs.getString("xtgn_id"));
				xt.setIspop(rs.getLong("ispop"));
				if ("1".equals(xt.getUrl_type())) {
					Statement statement1 = connection.createStatement();
					ResultSet rs1 = statement1
							.executeQuery("select url from web_xtgnb t where id='" + xt.getXtgn_id() + "'");
					if (rs1.next()) {
						xt.setOther_url(path + "" + (rs1.getString("url")));
					} else {
						xt.setOther_url("#");
					}
					rs1.close();
				}
				gnlist.add(xt);
				gnlist_1.add(xt);
				gnlist_new.add(xt);
			}
			rs.close();

			for (int i = 0; i < gnlist.size(); i++) {
				WebXtgncd xt = new WebXtgncd();
				xt = (WebXtgncd) gnlist.get(i);
				long level = xt.getLevels();
				String father_id = xt.getFather_id();
				try {
					while ((!"0".equals(father_id)) || (level > 1)) {
						String sql = "select t.id,t.xtgn_id,t.name,t.father_id,t.urltype,t.ispop,t.icon_url,"
								+ "t.levels,t.usertype,t.other_url from web_xtgncd t " + "where t.id='" + father_id
								+ "'";
						rs = statement.executeQuery(sql);
						while (rs.next()) {
							father_id = rs.getString("father_id");
							level = rs.getLong("levels");
							if (level > 1) {
								boolean f = false;
								for (int j = 0; j < gnlist_1.size(); j++) {
									xt = (WebXtgncd) gnlist_1.get(j);
									if (rs.getString("id").equals(xt.getId())) {
										f = true;
										break;
									}
								}
								if (!f) {

									xt.setFather_id(rs.getString("father_id"));
									xt.setIcon_url(rs.getString("icon_url"));
									xt.setId(rs.getString("id"));
									xt.setLevels(rs.getLong("levels"));
									xt.setName(rs.getString("name"));
									xt.setOther_url(rs.getString("other_url"));
									xt.setUrl_type(rs.getString("urltype"));
									xt.setXtgn_id(rs.getString("xtgn_id"));
									xt.setIspop(rs.getLong("ispop"));
									if ("1".equals(xt.getUrl_type())) {
										Statement statement1 = connection.createStatement();
										ResultSet rs1 = statement1.executeQuery(
												"select url from web_xtgnb t where id='" + xt.getXtgn_id() + "'");
										if (rs1.next()) {
											xt.setOther_url(path + "" + (rs1.getString("url")));
										} else {
											xt.setOther_url("#");
										}
										rs1.close();
									}

									gnlist_new.add(xt);
									gnlist_1.add(xt);
								}
							}
						}
						rs.close();
					}
				} catch (Exception ex) {
					System.out.println(ex.toString());
				}

			}

			if (flag) {
				ResultSet rs1 = statement
						.executeQuery("select id,url,name from web_xtgnb t where t.type=1 and t.admintype=1");

				while (rs1.next()) {
					WebXtgncd xt = new WebXtgncd();
					boolean f = false;
					for (int i = 0; i < gnlist_1.size(); i++) {
						xt = (WebXtgncd) gnlist_1.get(i);
						if (rs1.getString("id").equals(xt.getXtgn_id())) {
							f = true;
							break;
						}
					}
					if (!f) {
						xt.setId(rs1.getString("id"));
						xt.setFather_id("0");
						xt.setIspop(1);
						xt.setName(rs1.getString("name"));
						xt.setOther_url(path + "" + (rs1.getString("url")));
						xt.setLevels(2);
						gnlist_new.add(xt);
					}
				}
				rs1.close();
			}

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
				System.out.println("close connection exception: " + sqle4.getMessage());
			}
		}
		return gnlist_new;
	}

	public int fingWebRoleMenu2RoleId(String role_id, String gn_id) throws ServiceException {
		String sqltext = "select count(*) count from web_rolemenu t,web_xtgncd m where t.role_id='" + role_id
				+ "' and t.gncd_id=m.id and m.father_id='" + gn_id + "'";
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				count = rs.getInt("count");
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
		return count;
	}
	
	/**
	 * 
	     * @Title: fingWebReportMenu2RoleId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param role_id
	     * @param: @param repmid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int fingWebReportMenu2RoleId(String role_id, long repmid) throws ServiceException {
		String sqltext = "select count(*) count from WEB_ROLEREPORT t,report_menu m where t.role_id='" + role_id
				+ "' and t.ROLEMENU_ID=m.id and m.parent_id='" + repmid + "'";
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				count = rs.getInt("count");
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
		return count;
	}

	public int fingWebXtgncdfaId(String gn_id) throws ServiceException {
		String sqltext = "select count(*) count from web_xtgncd m where  m.father_id='" + gn_id + "'";
		Connection connection = null;
		Statement statement = null;

		int count = 0;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			while (rs.next()) {
				count = rs.getInt("count");
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
		return count;
	}

	@Override
	public void delUserDep(String apptype,String userid,String center_num) throws ServiceException {
		if (center_num != null && center_num.length() > 0) {
			String sql = "delete dep_user where apptype='" + apptype + "' and user_id = '" + userid
					+ "' and center_num='" + center_num + "'";
			this.jpm.execSql(sql);
		}else{
			String sql = "delete dep_user where apptype='"+apptype+"' and user_id = '"+userid+"' ";
			this.jpm.execSql(sql);
		}
	}

	@Override
	public DepUser saveDepUser(DepUser depUser) throws ServiceException {
		this.pm.save(depUser);
		return depUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebRoleDTO> queryAllWebRole() throws ServiceException {
		String sql=" select w.ID,w.ROLENAME,w.RY,w.REMARK,w.DATETIME  From  web_role w  ";
		List<WebRoleDTO> list = jqm.getList(sql, WebRoleDTO.class);		
		return list;
	}

	@Override
	public void delCenter(long userid,String center_num) throws ServiceException {
		if(center_num!=null && center_num.length()>0){
		String sql = "delete exam_user where center_num = '"+center_num+"' and user_id='"+userid+"'";
		this.jpm.execSql(sql);
		}else{
			String sql = "delete exam_user where user_id='"+userid+"'";
			this.jpm.execSql(sql);
		}
		
	}

	@Override
	public Exam_user saveCenter(Exam_user exam_user) throws ServiceException {
		this.pm.save(exam_user);
		return exam_user;
	}

	@Override
	public void delPrintDep(String userid) throws ServiceException {
		String sql = "delete dep_user_print where user_id = "+userid;
		this.jpm.execSql(sql);
		
	}

	@Override
	public DepUserPrint saveDepUserPrint(DepUserPrint depUser)
			throws ServiceException {
		this.pm.save(depUser);
		return depUser;
	
	}
}
