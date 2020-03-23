package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.GroupSchedulingDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.GroupScheduling;
import com.hjw.wst.model.GroupSchedulingModel;
import com.hjw.wst.service.GroupSchedulingService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class GroupSchedulingServiceImpl implements GroupSchedulingService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
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

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	
	@Override
	public List<GroupSchedulingDTO> getGroupSchedulingList(GroupSchedulingModel model) throws ServiceException {
		String sql = ""
				  + "SELECT  id,"
			      +"scheduling_content  as  title,"
			      +"creater,"
			      +"create_time,"
			      +"updater,"
			      +"update_time,"
			      +"scheduling_time  as  start,"
			      +"scheduling_end_time  as   [end],"
			      + "significance  as  confname"
			     // + "title as"
			      +" 	FROM group_scheduling    where   scheduling_time >='"+model.getScheduling_time()+"'  and  '"+model.getScheduling_end_time()+"'>=scheduling_end_time";
		List<GroupSchedulingDTO>    li  = this.jqm.getList(sql, GroupSchedulingDTO.class);
		return li;
	}

	@Override
	public void updateGroupScheduling(GroupSchedulingModel model,UserDTO user) throws ServiceException {
		String hql = " FROM  GroupScheduling   where  id="+model.getId();
		List<GroupScheduling> li  = this.qm.find(hql);
		li.get(0).setScheduling_content(model.getScheduling_content());
		li.get(0).setScheduling_time(DateTimeUtil.parse0(model.getScheduling_time()));
		li.get(0).setScheduling_end_time(DateTimeUtil.parse0(model.getScheduling_end_time()));
		li.get(0).setTitle(model.getTitle());
		li.get(0).setSignificance(model.getSignificance());
		li.get(0).setUpdate_time(DateTimeUtil.parse());
		li.get(0).setUpdater(user.getUserid());
		li.get(0).setId(model.getId());
		this.pm.update(li.get(0));
	}

	@Override
	public GroupScheduling addGroupScheduling(GroupScheduling g) throws ServiceException {
		this.pm.save(g);
		return g;
	}

	@Override
	public void deleteGroupSchedulingList(long id) throws ServiceException {
		String hql = " FROM  GroupScheduling  where  id="+id;
		List<GroupScheduling> li  = this.qm.find(hql);
		if(li.size()>0){
			this.pm.remove(li.get(0));
		}
	}

}
