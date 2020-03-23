package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.DepUserDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ECenterDTO;
import com.hjw.wst.DTO.ExamInfoCountDTO;
import com.hjw.wst.DTO.Examinatioin_centerDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PacsCountDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SysVersionDTO;
import com.hjw.wst.DTO.SysVersionListDTO;
import com.hjw.wst.DTO.SystemInformsDTO;
import com.hjw.wst.DTO.UserCenterDepListDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.domain.WebUserjsb;
import com.hjw.wst.service.UserInfoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
import com.ymhjw.Util.CenterBean;
import com.ymhjw.Util.CenterBeans;
import com.ymhjw.security.HjwInfo;

public class UserInfoServiceImpl implements UserInfoService {
	private JdbcPersistenceManager jdbcPersistenceManager;
	private JdbcQueryManager jdbcQueryManager;
	private PersistenceManager persistenceManager;
	private QueryManager queryManager;
	static final Log log = LogFactory.getLog(UserInfoServiceImpl.class);
	public void setJdbcPersistenceManager(
			JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}
	
	 /**
	  * <分页加载用户列表>
	 * @param pagesize
	 * @param pageno
	 * @return
	 * @throws ServiceException
	 * @see [类、类#方法、类#成员]
	  */
	public PageReturnDTO getUserInfoList(int pagesize, int pageno)
			throws ServiceException
			{
		String sql = "select id,exam_center_id,chi_Name,log_Name,pwd_encrypted,is_active, " +
				"email," +"phone_num,user_pic_path,user_signature,invoice_num_min,"
						+ "invoice_num_max,creater,create_Time,updater,update_Time from user_usr";
		PageSupport map = this.jdbcQueryManager.getList(sql, pageno, pagesize, UserInfoDTO.class);
		PageReturnDTO user = new PageReturnDTO();
		user.setPage(pageno);
		user.setRp(pagesize);
		user.setTotal(map.getRecTotal());
		user.setRows(map.getList());
		return user;
			}

	public WebUserInfo saveUserInfo(WebUserInfo wr) throws ServiceException {
		this.persistenceManager.save(wr);
		return wr;
	}
	
	public Exam_user saveExamUser(Exam_user eu) throws ServiceException{
		this.persistenceManager.save(eu);
		return eu;
	}

	public WebUserInfo updateUserInfo(WebUserInfo wr) throws ServiceException {
		this.persistenceManager.update(wr);
		return wr;
	}
	
	public WebUserInfo delUserInfo(WebUserInfo wr) throws ServiceException{
		this.persistenceManager.remove(wr);
		return wr;
	}

	
	public WebUserInfo loadUserInfo(long id) throws ServiceException {
		return (WebUserInfo) queryManager.get(WebUserInfo.class, id);
	}

	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}
	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public WebUserInfo getUserInfoByname(String name) throws ServiceException {
		List ls = queryManager.find("From WebUserInfo Where log_Name ='"+name.trim()+"'");
		if(ls.size()>0)
			return (WebUserInfo) ls.get(0);
		else
			return null;
	}
	
	
	/**
	 * 
	     * @Title: getUserInfoLike   
	     * @Description: 用户模糊查询(这里用一句话描述这个方法的作用)   
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<WebUserInfo>      
	     * @throws
	 */
	public List<WebUserInfo> getUserInfoLike(String name) throws ServiceException {
		List ls = queryManager.find("From WebUserInfo Where like '%"+name+"%'");
		return ls;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<DepUserDTO> getAllDepartmenterDep(String centernum) throws ServiceException {
		String sql ="select d.id as dep_id,d.dep_name from department_dep d,department_vs_center dc where dc.center_num='"+centernum+"' and dc.dep_id=d.id and d.isActive = 'Y'";
		List<DepUserDTO> list = this.jdbcQueryManager.getList(sql, DepUserDTO.class);
		return list;
	}
		
	/**
	 * 
	     * @Title: getDepartmenterDepForUserId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepUserDTO>      
	     * @throws
	 */
	public List<DepUserDTO> getDepartmenterDepForUserId(long userid,String centerNum) throws ServiceException{
		//String sql ="select d.id as dep_id,d.dep_name from department_dep d,exam_user eu where eu.user_id='"+userid+"' and eu.center_num=d.center_num and d.isActive = 'Y'";
		String sql ="select d.id as dep_id,d.dep_name from department_dep d,department_vs_center dc,exam_user eu where eu.user_id='"+userid+"' and dc.center_num='"+centerNum+"' and eu.center_num=dc.center_num and d.id=dc.dep_id and d.isActive = 'Y'";
		List<DepUserDTO> list = this.jdbcQueryManager.getList(sql, DepUserDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public DepUserDTO getDepUser(String user_id, long dep_id,String apptype) throws ServiceException {
		String sql = "select d.id from dep_user d where d.user_id = "+user_id+" and apptype='"+apptype+"' and d.dep_id = "+dep_id;
		List<DepUserDTO> list = this.jdbcQueryManager.getList(sql, DepUserDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DepUserDTO> getDepUser(String userName,String apptype,String centerNum,String sort) throws ServiceException {
		String sql = "select du.id,du.dep_id,du.user_id,d.dep_name from dep_user du,user_usr u,department_dep d where du.center_num='"+centerNum+"' and du.user_id = u.id and d.isActive='Y' and du.dep_id = d.id and u.is_active = 'Y' and u.log_name = '"+userName.trim()+"'  and du.apptype='"+apptype+"' order by d."+sort;
		List<DepUserDTO> list = this.jdbcQueryManager.getList(sql, DepUserDTO.class);
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserCenterDepListDTO> getCenterByUserName(String userName) throws ServiceException {
		String sql = "select e.id as center_id,e.center_name,e.center_num,eu.is_default from user_usr u,examinatioin_center e,exam_user eu where u.id = eu.user_id and eu.center_num=e.center_num and e.is_active = 'Y' and u.is_active = 'Y' and u.log_name = '"+userName.trim()+"'";
		List<UserCenterDepListDTO> list = this.jdbcQueryManager.getList(sql, UserCenterDepListDTO.class);
		return list;
	}

	public List<UserCenterDepListDTO> getCenterByUserId(String userid) throws ServiceException {
		String sql = "select e.id as center_id,e.center_name,e.center_num,eu.is_default from examinatioin_center e,exam_user eu where eu.user_id='"+userid+"' and eu.center_num=e.center_num and e.is_active = 'Y' ";
		List<UserCenterDepListDTO> list = this.jdbcQueryManager.getList(sql, UserCenterDepListDTO.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getCenterByCenterNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getCenterByCenterNum(String centerNum) throws ServiceException {
		String sql = "select dd.id,dd.dep_name,dd.dep_num from department_vs_center dv,department_dep dd where dd.isActive='Y' and dv.dep_id=dd.id and dv.center_num='"+centerNum+"'";
		List<DepartmentDepDTO> list = this.jdbcQueryManager.getList(sql, DepartmentDepDTO.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getCenterByCenterNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param apptype
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getCenterByCenterNum(String userid,String centernum,String apptype) throws ServiceException {
		String sql = "select dd.id,dd.dep_name,dd.dep_num from dep_user du,department_dep dd where dd.isActive='Y' and du.dep_id=dd.id and du.user_id='"+userid+"' and du.apptype='"+apptype+"' and du.center_num='"+centernum+"'";
		List<DepartmentDepDTO> list = this.jdbcQueryManager.getList(sql, DepartmentDepDTO.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getCenterByCenId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param centerid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public UserCenterDepListDTO getCenterByCenId(String centerid) throws ServiceException {
		String sql = "select e.id as center_id,e.center_name,e.center_num from examinatioin_center e where e.id='"+centerid+"' ";
		List<UserCenterDepListDTO> list = this.jdbcQueryManager.getList(sql, UserCenterDepListDTO.class);
		if(list!=null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	/**
	 *   获取所有体检中心信息
	     * @Title: getCenterAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException   
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public List<UserCenterDepListDTO> getCenterAll() throws ServiceException {
		String sql = "select e.id as center_id,e.center_name,e.center_num from examinatioin_center e  where e.is_active='Y'";
		List<UserCenterDepListDTO> list = this.jdbcQueryManager.getList(sql, UserCenterDepListDTO.class);
		return list;
	}
	
	/**
	 *   获取所有体检中心信息
	     * @Title: getCenterAll   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException   
	     * @return: UserCenterDepListDTO      
	     * @throws
	 */
	public List<ECenterDTO> getCheckCenterAll() throws ServiceException {
		Connection connection = null;
		List<ECenterDTO> ecenterList=new ArrayList<ECenterDTO>();
		try {
			connection = this.jdbcQueryManager.getConnection();
			String sql = "select id,center_num,center_name,is_active,notices from examinatioin_center";
			connection = this.jdbcQueryManager.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				
				Examinatioin_centerDTO ec= new Examinatioin_centerDTO();
				ec.setCenter_name(rs.getString("center_name"));
				ec.setCenter_num(rs.getString("center_num"));
				ec.setIs_active(rs.getString("is_active"));
				try{
				CenterBeans cbs=new CenterBeans();
				cbs.getcenters();
				CenterBean cb=cbs.getCenter(rs.getString("center_num"));
				ec.setId(rs.getLong("id"));
				
					if(cb.getXmessage()==null || cb.getXmessage().trim().length()<=0){
						connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error1' where id='"+ec.getId()+"'");
					}else if(cb.getYmessage()==null || cb.getYmessage().trim().length()<=0){
						connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error2' where id='"+ec.getId()+"'");
					}else{
						String centerkey=com.ymhjw.security.StringUtil.getCompId();
						if(!com.ymhjw.security.StringUtil.setYmessage(cb.getXmessage(),centerkey,cb.getYmessage())){
							connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error3' where id='"+ec.getId()+"'");
						}else{
							HjwInfo  dm=com.ymhjw.security.StringUtil.getString(cb.getXmessage(),centerkey);
							if(!dm.getCenter_num().trim().toUpperCase().equals(ec.getCenter_num().trim().toUpperCase())){
								connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error4' where id='"+ec.getId()+"'");	
							}else if(!dm.getCenter_name().trim().toUpperCase().equals(ec.getCenter_name().trim().toUpperCase())){
								connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error5' where id='"+ec.getId()+"'");	
							}else{
								Date enddata=DateTimeUtil.parse1(dm.getValid_date());
								Date newdata= new Date();
								int datess=DateTimeUtil.riqixiangjianhuoqutianshu(newdata,enddata);
								if(datess>10){
									connection.createStatement().executeUpdate("update examinatioin_center set is_active='Y',notices='' where id='"+ec.getId()+"'");	
								}else if (datess<=10&&datess>=0){
									connection.createStatement().executeUpdate("update examinatioin_center set is_active='Y',notices='主系统还有"+datess+"天到期，请联系火箭蛙销售人员处理' where id='"+ec.getId()+"'");
								}else if (datess<0){
									connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error6' where id='"+ec.getId()+"'");	
								}
							}
						}
					}
				}catch(Exception ex){
					connection.createStatement().executeUpdate("update examinatioin_center set is_active='N',notices='error7' where id='"+ec.getId()+"'");
				}
			}
			rs.close();
			
			sql = "select id,center_num,center_name from examinatioin_center where is_active='Y' ";
			rs = connection.createStatement().executeQuery(sql);			
			while (rs.next()) {
				ECenterDTO ec= new ECenterDTO();
				ec.setCenter_name(rs.getString("center_name"));
				ec.setCenter_num(rs.getString("center_num"));
				ec.setId(rs.getLong("id"));
				ecenterList.add(ec);
			}
			rs.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}catch (Exception ex) {
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
		return ecenterList;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserInfoDTO> autoGetUsername(String userName,String center_num) throws ServiceException {
//		String sql = "select u.log_name,u.chi_name from user_usr u where  u.log_name like '" + userName + "%' and u.is_active = 'Y'";
		String sql = "select u.log_name,u.chi_name from user_usr u,exam_user eu where  u.log_name like '" + userName + "%' and u.is_active = 'Y' and eu.user_id=u.id and eu.center_num='"+center_num+"'";
		List<UserInfoDTO> list = this.jdbcQueryManager.getList(sql, UserInfoDTO.class);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public UserInfoDTO getUsername(String userName) throws ServiceException {
		UserInfoDTO ud=new UserInfoDTO();
		String sql = "select u.log_name,u.chi_name,u.exam_center_id,u.id from user_usr u where  u.log_name ='" + userName.trim() + "' and u.is_active = 'Y'";
		List<UserInfoDTO> list = this.jdbcQueryManager.getList(sql, UserInfoDTO.class);
		if(list!=null && list.size()>0)
		{
			ud= list.get(0);
		}
		return ud;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DepUserDTO> getDepUserByID(long user_id,String apptype, String center_num , String sort) throws ServiceException {
			String sql =" select du.id,du.dep_id,du.user_id,d.dep_name from dep_user du,user_usr u, department_dep d,department_vs_center de "
				+ " where de.dep_id = d.id and du.user_id = u.id and du.center_num=de.center_num and du.dep_id = d.id and d.isActive='Y' "
				+ " and u.is_active = 'Y' and u.id ='"+user_id +"'  and de.center_num ='"+center_num+"' and du.apptype='"+apptype+"' order by d."+sort;
		List<DepUserDTO> list = this.jdbcQueryManager.getList(sql, DepUserDTO.class);
		return list;
	}
	/**
	 * 获取系统通知内容
	     * <p>Title: getSystemInfomsDTO</p>   
	     * <p>Description: </p>   
	     * @param user_id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.UserInfoService#getSystemInfomsDTO(long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SystemInformsDTO getSystemInfomsDTO(long user_id) throws ServiceException{
		String sql = " select top 1 su.id as user_id,s.inform_content,u.chi_name " 
				   + " from system_informs_user su,system_informs s,user_usr u "
				   + " where s.creater = u.id and s.id = su.informs_id and su.user_id = "+user_id
				   + " and s.is_active = 'Y' and su.reader_flag='0' "
				   		+ "order by s.create_time desc";
		SystemInformsDTO si= new SystemInformsDTO();
		List<SystemInformsDTO> list = this.jdbcQueryManager.getList(sql, SystemInformsDTO.class);
		if((list!=null)&&(list.size()>0)){
			si=(SystemInformsDTO)list.get(0);
		}
		return si;
	}
	
	/**
	 * 站内信更新
	     * @Title: getSystemInfomsDTO   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SystemInformsDTO>      
	     * @throws
	 */
	public String updateUserSystemInfroms(long messid,long user_id) throws ServiceException{
		Connection connection = null;
		List<JobDTO> list = new ArrayList<JobDTO>();
		try {
			connection = this.jdbcQueryManager.getConnection();
			String sql = "update system_informs_user set reader_flag='1',reader_time='"
			+DateTimeUtil.getDateTime()+"',updater='"+user_id+"',update_time='"+DateTimeUtil.getDateTime()
			+"' where id='"+messid+"'";
			connection.createStatement().executeUpdate(sql);
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
		return "";
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserCenterDepListDTO getCenterByUserIdAndDepId(long userId, long depId) throws ServiceException {
		String sql = "select u.chi_name,d.dep_name from user_usr u,department_dep d where u.id = "+userId+" and d.id = "+ depId;
		List<UserCenterDepListDTO> list = this.jdbcQueryManager.getList(sql, UserCenterDepListDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ExamInfoCountDTO> getCheckedCustomerCount(long dep_id, String center_num, String date) throws ServiceException {
		String sql = "select COUNT(a.exam_num) as num,a.exam_status from ("
				   + "select distinct e.exam_num,eci.exam_status from exam_info e,examinfo_charging_item eci,charging_item c"
				   + " where e.exam_num = eci.exam_num and eci.charge_item_id = c.id and eci.isActive = 'Y' and e.is_Active = 'Y'"
				   + " and eci.change_item <> 'C' and eci.exam_status <> 'G' and e.is_after_pay = 'Y' and eci.pay_status <> 'M' and eci.center_num = '"+center_num+"' "
				   + " and c.dep_id = "+dep_id+" and e.join_date = '"+date+" 23:59:59'"
				   + " union all"
				   + " select distinct e.exam_num,eci.exam_status from exam_info e,examinfo_charging_item eci,charging_item c"
				   + " where e.exam_num = eci.exam_num and eci.charge_item_id = c.id and eci.isActive = 'Y' and e.is_Active = 'Y'"
				   + " and eci.change_item <> 'C' and eci.exam_status <> 'G' and e.is_after_pay = 'N' and eci.pay_status in ('Y','R') and eci.center_num = '"+center_num+"' "
				   + " and c.dep_id = "+dep_id+" and e.join_date = '"+date+" 23:59:59'"
				   + ") as a group by a.exam_status";
		List<ExamInfoCountDTO> list = this.jdbcQueryManager.getList(sql, ExamInfoCountDTO.class);
		return list;
	}

	@Override
	public PageReturnDTO getUserInfoList(String chi_name, String log_name,String phone_num,String dep_name,String role_name, int pagesize, int pageno, String startStop,String center_num) throws ServiceException {
		String sql = " select distinct  u.id ,u.chi_name,u.log_name,u.phone_num,ec.center_name,u.email, "
				 +"  u.invoice_num_min,u.pwd_encrypted,u.user_pic_path,u.work_num , "
				 +" us.chi_name as creaters,uu.chi_name as updaters,u.is_active "
				 +" from user_usr u left join dep_user du on u.id=du.user_id "
				 +" left join Web_USERJSB ur on ur.user_id=u.id  "
				 +" left join examinatioin_center ec on u.exam_center_id=ec.id "
				 +" left join user_usr us on us.id=u.creater  left join user_usr uu on uu.id=u.updater "
				 +",exam_user eu where  eu.center_num='"+center_num+"' and eu.user_id=u.id  "
                 +" and u.exam_center_id<>0 ";
                if(!StringUtils.isEmpty(chi_name)){
                 sql += " and u.chi_name like '%"+chi_name+"%'  ";
                }
                if(!StringUtils.isEmpty(log_name)){
                sql +=" and  u.log_name like '%"+log_name+"%' ";
                }
				if(phone_num!=null && !"".equals(phone_num)){
					//sql += " and  phone_num='"+phone_num+"' ";等值查询
					sql+=" and u.phone_num like '%"+phone_num+"%' ";//模糊查询
				}
				if(dep_name != null && !"".equals(dep_name)){
					sql+=" and du.dep_id = " + dep_name ;
				}
				if(role_name != null && !"".equals(role_name)){
					sql+=" and ur.role_id = '" + role_name +"'";
				}
				if(startStop != null && !"".equals(startStop)){
					if(startStop.length()==1) {
						sql += "  and u.is_active IN ('"+startStop+"') ";
					}else {
						sql += "  and u.is_active IN ('Y','N') ";
					}
				}
				PageSupport map = this.jdbcQueryManager.getList(sql, pageno, pagesize, UserInfoDTO.class);
				PageReturnDTO webrole = new PageReturnDTO();
				webrole.setPage(pageno);
				webrole.setRp(pagesize);
				if ((map != null) && (map.getList() != null)) {
					webrole.setTotal(map.getRecTotal());
					webrole.setRows(map.getList());
				}
				return webrole;
	}

	@Override
	public WebUserInfo queryByNum(String work_num) throws ServiceException {
		String sql = "from WebUserInfo where work_num = '"+work_num.trim()+"'";
		List<WebUserInfo> list = this.queryManager.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<WebResrelAtionship>  getWebResource(long user_id) throws ServiceException {
		
		//获取角色
		String hql = " From  WebUserjsb  where  user_id='"+user_id+"'";
		@SuppressWarnings("unchecked")
		List<WebUserjsb>  list = this.queryManager.find(hql);
		String role_id="";
		if(list.size()>0){
			for(int i=0; i<list.size(); i++){
				role_id+="'"+list.get(i).getRole_id()+"',";
			}
		}
		role_id+="'"+user_id+"'";
		hql = " From  WebResrelAtionship   where   userorroleid  in("+role_id+")";
		@SuppressWarnings("unchecked")
		//通过用户和角色获取资源
		List<WebResrelAtionship> li = this.queryManager.find(hql);
		String  datavalue = "";
			for(int i=0; i<li.size()-1; i++){
				for(int j=i+1; j<li.size(); j++){
					if(li.get(i).getRes_code().equals(li.get(j).getRes_code())){
						if(Integer.parseInt(li.get(i).getDatavalue())>Integer.parseInt(li.get(j).getDatavalue())){
							li.remove(j);
						}else{
							li.remove(i);
						}
					}
				}
			}
			
		return li.size()>0?li:null;
	}

	@Override
	public void saveUserExamDept(String user_id, String exam_center_parent_id) throws ServiceException {
		Exam_user exam_user=new Exam_user();
		exam_user.setUser_id(Long.valueOf(user_id));
		exam_user.setExam_center_id(Long.valueOf(exam_center_parent_id));
		this.persistenceManager.save(exam_user);
	}

	@Override
	public DepUserDTO getDepUserPrint(String user_id, long dep_id,String centernum)
			throws ServiceException {
		String sql = "select d.id from dep_user_print d where d.user_id = "+user_id+" and d.dep_id = '"+dep_id+"' and d.center_num='"+centernum+"'";
		List<DepUserDTO> list = this.jdbcQueryManager.getList(sql, DepUserDTO.class);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	/*@Override
	public PageReturnDTO getLisPrint(String  exam_num) throws ServiceException {
		String sql = "select a.id as charging_id,d.dep_name,b.item_code,b.item_name "
				+" from exam_info e,examinfo_charging_item a,charging_item b,sample_demo m,dep_user_print p,department_dep d "
				+" where  e.exam_num='"+exam_num+"' "
				+" and a.exam_num=e.exam_num "
				+" and a.isActive='Y' "
				+" and a.pay_status<>'M' "
				+" and a.exam_status<>'G' and a.center_num = '"+center_num+"' "
				+" and a.charge_item_id=b.id "
				+" and b.dep_category in ('21','17') "
				+" and b.dep_id=p.dep_id "
				+" and p.dep_id=d.id "
				+" and m.isPrint_BarCode='1'  "
				+" and b.sam_demo_id=m.id "
				+" and d.isPrint_Barcode = '0'  "
				+" order by d.seq_code,b.item_seq ";
		PageSupport map = this.jdbcQueryManager.getList(sql, 1, 1000, PacsCountDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}*/

	/**
	 * 
	     * @Title: getSysVersion   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getSysVersion() throws ServiceException{
		Connection connection = null;
		Statement statement = null;
		String version="未知版本";
		try {
			String sql = "select wv.version,CONVERT(varchar(100), wv.createtime, 23) as dt from web_version wv order by wv.seqid desc";
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				version=rs.getString("version")+"("+rs.getString("dt")+")";
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
		return version;
	}
	
	/**
	 * 
	     * @Title: getSysVersionList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<SysVersionDTO> getSysVersionList() throws ServiceException{
		Connection connection = null;
		List<SysVersionDTO> sysVersionList = new ArrayList<SysVersionDTO>();
		try {
			String sql = "select wv.version,CONVERT(varchar(100), wv.createtime, 23) as dt from web_version wv order by wv.seqid desc";
			connection = this.jdbcQueryManager.getConnection();
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while(rs.next()) {
				SysVersionDTO sv= new SysVersionDTO();
				sv.setCreatetime(rs.getString("dt"));
				sv.setVersion(rs.getString("version"));
				try {
					String sqllist = "select CONVERT(varchar(100), wvl.createtime, 23) as dt,wvl.notices from web_version_list wvl where wvl.version='"+rs.getString("version")+"' order by id ";
					ResultSet rslist = connection.createStatement().executeQuery(sqllist);
					while(rslist.next()) {
						SysVersionListDTO svl= new SysVersionListDTO();
						svl.setCreatetime(rslist.getString("dt"));
						svl.setNotices(rslist.getString("notices"));
						sv.getVersionlist().add(svl);
					}
					rslist.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}				
				sysVersionList.add(sv);
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
		return sysVersionList;
	}

	public WebUserInfo getUserInfo(long id) throws ServiceException {
		return (WebUserInfo) queryManager.get(WebUserInfo.class, id);
	}
	
	/**
	 * 
	     * @Title: getUserInfoByWorkNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: WebUserInfo      
	     * @throws
	 */
	public WebUserInfo getUserInfoByWorkNum(String name) throws ServiceException {
		List ls = queryManager.find("From WebUserInfo Where work_num ='"+name.trim()+"' ");
		if(ls.size()>0)
			return (WebUserInfo) ls.get(0);
		else
			return null;
	}
	
	@Override
	public PageReturnDTO getLisPrint(String  exam_num) throws ServiceException {
		String sql = "select a.id as charging_id,d.dep_name,b.item_code,b.item_name "
				+" from exam_info e,examinfo_charging_item a,charging_item b,sample_demo m,dep_user_print p,department_dep d "
				+" where  e.exam_num='"+exam_num+"' "
				+" and a.exam_num=e.exam_num  "
				+" and a.isActive='Y' "
				+" and a.pay_status<>'M' "
				+" and a.exam_status<>'G' "
				+" and a.charge_item_id=b.id "
				+" and b.dep_category in ('21','17') "
				+" and b.dep_id=p.dep_id "
				+" and p.dep_id=d.id "
				+" and m.isPrint_BarCode='1'  "
				+" and b.sam_demo_id=m.id "
				+" and d.isPrint_Barcode = '0'  "
				+" order by d.seq_code,b.item_seq ";
		TranLogTxt.liswriteEror_to_txt_single("科室条码打印sql", sql);
		PageSupport map = this.jdbcQueryManager.getList(sql, 1, 1000, PacsCountDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}

	
}
