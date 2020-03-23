package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleReportDemoDto;
import com.hjw.wst.domain.SampleReportDemo;
import com.hjw.wst.service.SampleReportDemoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class SampleReportDemoImpl implements SampleReportDemoService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
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

	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jpm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public SampleReportDemo findClass(long id) throws ServiceException {
		return (SampleReportDemo) this.qm.get(SampleReportDemo.class, id);

	}

	public PageReturnDTO querySampleReportDemo(SampleReportDemo sample,int page,int pageSize,String startStop) {
		String sql = "select sa.id,sa.demo_name,sa.demo_num,sa.print_seq,sa.remark,"
				+ "u.chi_name,sa.is_Active,us.chi_name as updatename,sa.create_time,"
				+ "sa.update_time from sample_report_demo  sa LEFT JOIN  user_usr"
				+ "  u on sa.creater=u.id LEFT JOIN  user_usr us on sa.updater=us.id " + "where 1=1 ";
		
		if (startStop != null && !"".equals(startStop)) {// 收费编号
			if(startStop.length()==1) {
				sql += " and sa.is_Active IN ('"+startStop+"') ";
			}else {
				sql += " and sa.is_Active IN ('Y','N') ";
			}
		}
		
		if (sample.getDemo_num() != null && !sample.getDemo_num().equals("")) {
			sql += "and sa.demo_num='" +sample.getDemo_num().trim()+"'";
		}
		if (sample.getDemo_name() != null && !sample.getDemo_name().equals("")) {
			sql += "and sa.demo_name like '%" + sample.getDemo_name().trim() + "%'";
		}
		sql += "order by sa.id desc";
		PageSupport map = this.jqm.getList(sql,pageSize,page, SampleReportDemoDto.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	public void deleteSam(SampleReportDemo sa) throws ServiceException {
		pm.update(sa);
	}

	public SampleReportDemo getSampleReportDemoBynum(String demonum) throws ServiceException {
		List ls = qm.find("From SampleReportDemo Where  demo_num ='" + demonum.trim() + "'");
		if (ls.size() > 0)
			return (SampleReportDemo) ls.get(0);
		else
			return new SampleReportDemo();
	}

	public SampleReportDemo getSampleReportDemoByname(String demo_name) throws ServiceException {
		List ls = qm.find("From SampleReportDemo Where is_Active='Y' and demo_name ='" + demo_name.trim() + "'");
		if (ls.size() > 0)
			return (SampleReportDemo) ls.get(0);
		else
			return null;
	}

	public void addSampleReportDemo(SampleReportDemo sam) throws ServiceException {
		this.pm.save(sam);
	}

	public SampleReportDemo findSampleReportDemoForId(long id) throws ServiceException {
		return (SampleReportDemo) qm.load(SampleReportDemo.class, id);
	}

	public void updateSampleReportDemo(SampleReportDemo sd) throws ServiceException {
		this.pm.update(sd);
	}

	public void deleteSampleReportDemo(String id) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jqm.getConnection();
			sql ="update sample_report_demo set is_Active='N' where id in("+id+")";
			int rs = connection.createStatement().executeUpdate(sql);
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
}
