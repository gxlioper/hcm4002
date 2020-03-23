package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ReportMenuDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ReportMenu;
import com.hjw.wst.service.ReportMenuService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ReportMenuServiceImpl implements ReportMenuService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	

	public QueryManager getQueryManager() {
		return qm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}


	/**
	 * 
	     * @Title: saveReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportMenu      
	     * @throws
	 */
	public ReportMenu saveReportMenu(ReportMenu ctms) throws ServiceException {
		this.pm.save(ctms);
		return ctms;
	}
	
	/**
	 * 
	     * @Title: updateReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportMenu      
	     * @throws
	 */
	public ReportMenu updateReportMenu(ReportMenu ctms) throws ServiceException {
		this.pm.update(ctms);
		return ctms;
	}
	/**
	 * 
	     * @Title: delReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ctms
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delReportMenu(ReportMenu ctms) throws ServiceException {
		this.pm.remove(ctms);
	}
	
	
	/**
	 * 
	     * @Title: loadReportMenu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ReportMenu      
	     * @throws
	 */
	public ReportMenu loadReportMenu(long id) throws ServiceException {
		return (ReportMenu) qm.load(ReportMenu.class, id);
	}

	/**
	 * 
	     * @Title: getReportMenuForFatherIdCount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getReportMenuForFatherIdCount(long id) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select count(*) as counts FROM report_menu a ");
		sb.append(" where a.is_active='Y' and parent_id='"+id+"' ");
		Connection connection = null;
		Statement statement = null;
		int flags=0;
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sb.toString());			
			if (rs.next()) {
				flags=rs.getInt("counts");
			}
			rs.close();	
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return flags;
	}	
	
	/**
	 * 
	     * @Title: getReportMenuForFatherId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param fatherid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ReportMenuDTO>      
	     * @throws
	 */
	public List<ReportMenuDTO> getReportMenuForFatherId(Integer fatherid) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select a.id,a.parent_id,a.report_name,a.report_address,a.is_active,a.creater,fromusr.chi_name as creaters,"
				+ "a.create_time,a.updater,tousr.chi_name as updaters,a.update_time,a.seq_code,a.report_type FROM report_menu a ");
		sb.append(" left join user_usr fromusr on fromusr.id=a.creater ");
		sb.append(" left join user_usr tousr on tousr.id=a.updater  ");
		sb.append(" where a.is_active='Y' ");
        if((fatherid!=null)&&(fatherid.intValue()>0)){
        	sb.append( " and a.parent_id="+fatherid+" " );
        }else{
        	sb.append( " and (a.parent_id is null or a.parent_id=0) " );
        }
        sb.append("  order by a.id ");	
		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, ReportMenuDTO.class);
		List<ReportMenuDTO> webrole = new ArrayList<ReportMenuDTO>();
		List<ReportMenuDTO> webrolenew = new ArrayList<ReportMenuDTO>();
		if((map!=null)&&(map.getList()!=null)&&(map.getList().size()>0)){
			webrole=map.getList();
			for(ReportMenuDTO rm :webrole){
				if((rm.getParent_id()==null)||(rm.getParent_id()<=0)){
					rm.setIconCls("icon-close");
					rm.setState("closed");					
					webrolenew.add(rm);
				}else{
					rm.setIconCls("icon-open");
					rm.setState("open");					
					webrolenew.add(rm);
				}
			}
		}
		return webrolenew;
	}	

}