package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExpertSuggestionLibDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.ExpertSuggestionLib;
import com.hjw.wst.domain.ItemResultLib;
import com.hjw.wst.service.ExpertSuggestionLibService;
import com.hjw.wst.service.ItemResultLibService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description: 专家建议知识库
     * @author: zr     
     * @date:   2016年11月2日 下午2:55:47   
     * @version V2.0.0.0
 */

public class ExpertSuggestionLibImpl implements ExpertSuggestionLibService {
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

	@Override
	public PageReturnDTO queryExpertSuggestionLib(ExpertSuggestionLibDTO li,
			int page, int pageSize) throws ServiceException {
		String sql="SELECT e.id,e.sugg_word,e.sugg_content,dep.dep_name,"
				+ "up.chi_name,e.update_time FROM  expert_suggestion_lib e "
				+ "  LEFT JOIN  department_dep   dep  ON  e.dep_id=dep.id"
				+ "  LEFT JOIN  user_usr   up  ON   e.updater=up.id"
				+ "  where  e.is_active='Y'";
				if(li.getDep_id()>0){
			   sql+="  and   e.dep_id='"+li.getDep_id()+"'";
				}
				if(li.getSugg_word()!=null&&!li.getSugg_word().equals("")){
			   sql+="  and   e.sugg_word  like'%"+li.getSugg_word()+"%'";	
				}
			   sql+="  ORDER BY  e.id  DESC";
			PageSupport map=jqm.getList(sql,pageSize,page,ExpertSuggestionLibDTO.class);
			PageReturnDTO webrole = new PageReturnDTO();
			webrole.setPage(page);
			webrole.setRp(pageSize);
			if ((map != null) && (map.getList() != null)) {
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
			}
		return webrole;
	}

	@Override
	public void deleteExpertSuggestionLib(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="update expert_suggestion_lib set is_active='N' where id in("+ids+")";
			connection = this.jqm.getConnection();
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

	@Override
	public void updateExpertSuggestionLib(ExpertSuggestionLib exp)
			throws ServiceException {
		this.pm.update(exp);
	}

	@Override
	public void addExpertSuggestionLib(ExpertSuggestionLib exp)
			throws ServiceException {
			this.pm.save(exp);
	}

	@Override
	public ExpertSuggestionLib findExpertSuggestionLib(long id)
			throws ServiceException {
		return   (ExpertSuggestionLib) this.qm.get(ExpertSuggestionLib.class,id);
	}
}
