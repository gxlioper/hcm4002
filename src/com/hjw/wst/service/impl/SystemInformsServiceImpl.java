package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SystemInformsDTO;
import com.hjw.wst.DTO.SystemInformsUserDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebRoleDTO;
import com.hjw.wst.domain.SystemInforms;
import com.hjw.wst.domain.SystemInforms_user;
import com.hjw.wst.model.SystemInforsModel;
import com.hjw.wst.service.SystemInformsService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class SystemInformsServiceImpl implements SystemInformsService {
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	public QueryManager getQueryManager() {
		return qm;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jqm;
	}

	public PersistenceManager getPersistenceManager() {
		return pm;
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
	

	@Override
	public SystemInforms addSystemInfo(SystemInforms s) throws ServiceException {
		this.pm.save(s);
		return s;
	}

	@Override
	public SystemInforms updateSysInfo(SystemInforms s)throws ServiceException {
		this.pm.update(s);
		return s;
	}

	@Override
	public SystemInforms queryById(long id) throws ServiceException {
		return (SystemInforms)this.qm.load(SystemInforms.class,id);
	}

	@Override
	public PageReturnDTO getSystemInformsLis(SystemInforsModel model,UserDTO user, int pageno, int pagesize) throws ServiceException {
		
		String sql=" select s.id,s.inform_content,CONVERT(varchar(50),valid_date,23) as valid_date ,s.is_active, "
				 +" u.chi_name as creater,uu.chi_name as updater ,s.update_time,s.create_time "
				 +" from system_informs s left join user_usr u on  s.creater=u.id left join  user_usr uu  on  s.updater=uu.id  ";
		if (model.getStartDate() != null && !"".equals(model.getStartDate())) {// 开始日期
			sql += " where s.valid_date >= '" + model.getStartDate() + " 00:00:00.000' " ;
		} 
		if (model.getEndDate() != null && !"".equals(model.getEndDate())) {// 截至日期
			sql += " and s.valid_date < '" + model.getEndDate() + " 23:59:59.999' " ;
		}
		if (model.getFirstTime() != null && !"".equals(model.getFirstTime())) {// 开始日期
			sql += " where s.create_time >= '" + model.getFirstTime() + " 00:00:00.000' " ;
		} 
		if (model.getLastTime() != null && !"".equals(model.getLastTime() )) {// 截至日期
			sql += " and s.create_time < '" + model.getLastTime()  + " 23:59:59.999' " ;
		}
		sql += " order by s.valid_date desc";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		PageSupport map = (PageSupport) this.jqm.getList(sql,pageno,pagesize,SystemInformsDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SystemInformsUserDTO> queryAlluser()throws ServiceException {
		String sql=" select u.id as user_id,u.chi_name From user_usr  u";
		List<SystemInformsUserDTO> list=jqm.getList(sql,SystemInformsUserDTO.class);
		return list;
	}

	@Override
	public SystemInforms_user saveEmpower(SystemInforms_user us)throws ServiceException {
		this.pm.save(us);
		return us;
	}

	@Override
	public void deleteSysinformUser(long informs_id)throws ServiceException {
		String sql = "delete  from system_informs_user where informs_id='" + informs_id + "' ";
		this.jpm.execSql(sql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SystemInforms_user> findSysteminforms_user(long user_id, long informs_id) throws ServiceException {
		String sql=" select * From system_informs_user  where user_id='" + user_id + "' and informs_id=" + informs_id;
		return jqm.getList(sql,SystemInforms_user.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<WebRoleDTO> getAllRole() throws ServiceException {
		List list = jqm.getList(" select ID as r_id,ROLENAME from WEB_ROLE ", WebRoleDTO.class);
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<SystemInformsUserDTO> queryByRid(String role_id) throws ServiceException {
		List  list = this.jqm.getList("SELECT user_id FROM Web_Userjsb WHERE role_id='"+role_id+"'",SystemInformsUserDTO.class);
		return list;
	}
	@Override
	public String deleteInforms(SystemInforms s) throws ServiceException {
		String sql = " select su.* from system_informs as sf left join system_informs_user as su on sf.id=su.informs_id where su.reader_flag='1' and sf.id='" + s.getId() + "' ";
		String sql2 = " delete  from system_informs where id='" + s.getId() + "' ";		
		List<SystemInforms> list = jqm.getList(sql, SystemInforms.class);
				if(list.size()==0 || list==null){
					this.jpm.execSql(sql2);
				}else{
					return "此消息已读,不能删除！";
				}
				return "删除成功！";
		
	}

	/**
	 * 
	     * @Title: sendSysInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param inform_content
	     * @param: @param fromuserid
	     * @param: @param touserids
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void sendSysInfo(String inform_content, long fromuserid, String touserids) throws ServiceException {
		SystemInforms informs = new SystemInforms();
		informs.setInform_content(inform_content);
		informs.setIs_active("Y");
		informs.setCreater(fromuserid);
		informs.setCreate_time(DateTimeUtil.parse());
		informs.setUpdater(fromuserid);
		informs.setUpdate_time(DateTimeUtil.parse());
		this.pm.save(informs);
		String[] userids = touserids.split(",");
		for (int i = 0; i < userids.length; i++) {
			SystemInforms_user informs_user = new SystemInforms_user();
			informs_user.setInforms_id(informs.getId());
			informs_user.setUser_id(Long.valueOf(userids[i]));
			informs_user.setReader_flag(0);
			informs_user.setCreater(fromuserid);
			informs_user.setCreate_time(DateTimeUtil.parse());
			informs_user.setUpdater(fromuserid);
			informs_user.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(informs_user);
		}
	}
	
}
