package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.domain.ExamItemRefandDang;
import com.hjw.wst.service.ExamItemRefandDangService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class ExamItemRefandDangImpl implements ExamItemRefandDangService{
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
	
	public void addexamItemRefandDang(ExamItemRefandDang dang) throws ServiceException {
		this.pm.save(dang);
	}

	@Override
	public ExamItemRefandDang findClass(long id) throws ServiceException {
		return  (ExamItemRefandDang) this.qm.get(ExamItemRefandDang.class,id);
	}

	@Override
	public List<ExamItemRefandDang> getExamItemRefandDanga(long id) throws ServiceException {
		String sql="";
		Connection connection = null;
		List<ExamItemRefandDang> li=new ArrayList<ExamItemRefandDang>();
		try {
			sql+="SELECT * FROM exam_item_RefandDang WHERE exam_item_id='"+id+"'";
			connection = this.jqm.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				ExamItemRefandDang dang = new ExamItemRefandDang();
				dang.setId(Integer.parseInt(rs.getString("id")));
				dang.setExam_item_id(Integer.parseInt(rs.getString("exam_item_id")));
				dang.setIs_ReforDang(rs.getString("is_ReforDang"));
				dang.setVal_info(rs.getString("val_info"));
				dang.setVal_index(Integer.parseInt(rs.getString("val_index")));
				//dang.setCreater(Integer.parseInt(rs.getString("creater")));
				//dang.setCreate_time(DateTimeUtil.parse(rs.getString("create_time")));
				//dang.setUpdater(Integer.parseInt(rs.getString("updater")));
				//dang.setUpdate_time(DateTimeUtil.parse(rs.getString("update_time")));
				li.add(dang);
			}
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
		return li;
	}



	@Override
	public void updateExamItemRefandDang(ExamItemRefandDang dang) throws ServiceException {
		this.pm.update(dang);
	}

	@Override
	public int deletExamItemRefandDang(long id) throws ServiceException {
		String sql="";
		int rs=0;
		Connection connection = null;
		try {
			sql+=" DELETE FROM exam_item_RefandDang WHERE  exam_item_RefandDang.exam_item_id ='"+id+"'";
			connection = this.jqm.getConnection();
			 rs = connection.createStatement().executeUpdate(sql);
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
		return rs;
	}
}
