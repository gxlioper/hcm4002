package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.domain.WebSynchro;
import com.hjw.wst.service.WebSynchroService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class WebSynchroServiceImpl implements WebSynchroService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	/*@Override
	public void addWebSynchro(WebSynchro ws) throws ServiceException {
		this.pm.save(ws);

	}*/

	/*@Override
	public void deleteWebSynchro(String id,char datatype) throws ServiceException {
		String[] ids=id.split(",");
		StringBuffer str=new StringBuffer();
		for(int i=0;i<ids.length;i++){
			if(i==ids.length-1){
				str.append("'"+ids[i]+"'");
			}else{
				str.append("'"+ids[i]+"'"+",");
			}
		}
		Connection connection = null;
		try {
			String sql="delete web_synchro  where dataid in("+id+") and datatype='"+datatype+"' ";
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
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
	

	}*/

	@Override
	public void updateWebSynchro(String id,char datatype) throws ServiceException {
		String[] ids=id.split(",");
		Connection con = null;
		try {
			con = this.jqm.getConnection();
			for(int i=0;i<ids.length;i++){
				
				String synstr="delete web_synchro where dataid ='"+ids[i]+"' and datatype='"+datatype+"'";
			    con.createStatement().executeUpdate(synstr);
			    synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('"+datatype+"','"+ids[i]+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
			    con.createStatement().executeUpdate(synstr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		
		

	}

	/*@Override
	public WebSynchro queryByWebSynchro(String id,char datatype) throws ServiceException {
		String hql=" from WebSynchro where dataid='"+id.trim()+"' and datatype='"+datatype+"' ";
		List<WebSynchro> list=this.qm.find(hql);
		if(list.size()!=0){
			WebSynchro ws=list.get(0);
			return ws;
		}else{
			return null;
		}
		
	}*/

}
