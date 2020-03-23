package com.hjw.wst.service.impl;


import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.NoteData;
import com.hjw.wst.domain.Scheduling;
import com.hjw.wst.model.DepartmentModel;
import com.hjw.wst.service.DepartmentService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
import com.twelvemonkeys.lang.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {
    private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;
	

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
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
	public DepartmentDep addDepart(DepartmentDep dep) throws ServiceException {
		this.pm.save(dep);
		return dep;
	}

	@Override
	public void deleteDept(DepartmentDep dep) throws ServiceException {
		this.pm.remove(dep);
	}

	@Override
	public DepartmentDep updateDept(DepartmentDep dep) throws ServiceException {
		this.pm.update(dep);
		return dep;
	}
/**
 * 查询全部
     * <p>Title: queryAllDepartmentDep</p>   
     * <p>Description: </p>   
     * @return
     * @throws ServiceException   
     * @see com.hjw.wst.service.DepartmentService#queryAllDepartmentDep()
 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DepartmentDepDTO> queryAllDepartmentDep( String center_num ) throws ServiceException {
		String sql = " select dd.id,dep_num, dep_name,dt.data_name as dep_category,sex,dd.seq_code,dd.remark,dep_link,"
				+ "dd.isActive,u.chi_name as creater_name ,u1.chi_name as updater_name, dd.create_time, dd.update_time,"
				+ "de.isPrint_Barcode as isPrint_Barcode_c,de.dep_inter_num "
+" from data_dictionary dt,user_usr u,department_vs_center de, department_dep dd left join user_usr u1 on dd.updater = u1.id "
+" where dd.dep_category=dt.id  and de.dep_id = dd.id  and dd.creater=u.id  and de.center_num ='"+center_num+"' and  dd.isActive='Y' ";
		List<DepartmentDepDTO> list = jqm.getList(sql, DepartmentDepDTO.class);		
		return list;
	}
/**
 * id查询
     * <p>Title: loadDepartmentDep</p>   
     * <p>Description: </p>   
     * @param deptid
     * @return
     * @throws ServiceException   
     * @see com.hjw.wst.service.DepartmentService#loadDepartmentDep(int)
 */
	@Override
	public DepartmentDep loadDepartmentDep(long depid) throws ServiceException {
		return (DepartmentDep)this.qm.get(DepartmentDep.class, depid);
	}
	/**
	 * 编码查询
	     * <p>Title: queryByNum</p>   
	     * <p>Description: </p>   
	     * @param dep_num
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.DepartmentService#queryByNum(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DepartmentDep queryByNum(String dep_num) throws ServiceException {
		String sql = "from DepartmentDep where dep_num = '"+dep_num.trim()+"'";
		List<DepartmentDep> list = this.qm.find(sql);
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}
/**
 * 
 * 分页查询(排班)
     * <p>Title: queryPageDepartmentDep</p>   
     * <p>Description: </p>   
     * @param pageno
     * @param pagesize
     * @return   
     * @see com.hjw.wst.service.DepartmentService#queryPageDepartmentDep(int, int)
 */
	@Override
	public PageReturnDTO queryPageDepartmentDep(int pageno, int pagesize, String center_num ) {
		String sql = "select d.id,d.dep_num,d.dep_name,d.dep_category,d.sex,seq_code,d.remark,"
				+ "d.dep_link,d.isActive,d.creater,d.create_time,d.updater,d.update_time,"
				+ "d.isPrint_Barcode,d.dep_inter_num From  Department_Dep d left join department_vs_center de on  de.dep_id = d.id where de.center_num ='"+center_num+"'   order by d.seq_code ASC";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, DepartmentDepDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	/**
	 * 
	 * 分页查询(科室)
	     * <p>Title: queryPageDepartmentDep</p>   
	     * <p>Description: </p>   
	     * @param pageno
	     * @param pagesize
	     * @return   
	     * @see com.hjw.wst.service.DepartmentService#queryPageDepartmentDep(int, int)
	 */
		@Override
		public PageReturnDTO departmentList(String dep_name,String dep_num,String dep_category,String sex,String centerNum,int pagesize, int pageno) {
			String sql = " select dc.remark1,dd.id,dep_num, dep_name,dt.data_name as dep_category,sex,dd.seq_code,dc.dep_address,dep_link,dd.isActive,"
					+ "u.chi_name as creater_name,dd.create_time,u1.chi_name as updater_name,dd.update_time,dc.isPrint_Barcode as isPrint_Barcode_c,dd.dep_inter_num,dc.calculation_rate " 
              +" from department_dep dd "
              + " left join data_dictionary dt on dd.dep_category=dt.id "
              + " left join user_usr u on dd.creater=u.id "
              + " left join user_usr u1 on dd.updater = u1.id "
              + " ,department_vs_center dc "
              +" where dd.id=dc.dep_id  and dd.dep_name like '%"+dep_name+"%' and dd.dep_num like '%"+dep_num+"%' ";
			if(!StringUtil.isEmpty(centerNum)){
				sql += " and dc.center_num='"+centerNum+"'   ";
			}
			if(!"全部".equals(sex)){
				sql += " and dd.sex= '"+sex+"'";
			}
			if(!"".equals(dep_category)){
				sql += " and  dd.dep_category='"+dep_category+"' ";
			}
			sql += " order by dd.seq_code ASC ";
			PageSupport map = this.jqm.getList(sql, pageno, pagesize, DepartmentDepDTO.class);
			PageReturnDTO webrole = new PageReturnDTO();
			webrole.setPage(pageno);
			webrole.setRp(pagesize);
			if ((map != null) && (map.getList() != null)) {
				List<DepartmentDepDTO> newList=new ArrayList<DepartmentDepDTO>();
				for(Object od:map.getList()){
					DepartmentDepDTO dd= (DepartmentDepDTO) od;
					Connection connection = null;
					try {
						connection = this.jqm.getConnection();
						String searchCenter="select dd.center_num,dd.center_name from department_vs_center dc,examinatioin_center dd where  dd.center_num=dc.center_num and dc.dep_id='"+dd.getId()+"' ";
						ResultSet rs = connection.createStatement().executeQuery(searchCenter);
						String centername="";
						while (rs.next()) {
							centername=centername+"--"+rs.getString("center_name");
						}
						rs.close();
						if(centername.length()>0){
							centername=centername.substring(2,centername.length());
							dd.setCenternames(centername);
						}
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
					newList.add(dd);
				}
				map.setList(newList);
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
			}
			return webrole;
		}
		
		public PageReturnDTO departmentListAll(String dep_name,String dep_num,String dep_category,String sex,int pagesize, int pageno,DepartmentModel model) {
			String sql = " select dd.id,dep_num, dep_name,dt.data_name as dep_category,sex,dd.seq_code,dd.remark,dep_link,dd.isActive,"
					+ "u.chi_name as creater_name,dd.create_time,u1.chi_name as updater_name,dd.update_time,isPrint_Barcode,dep_inter_num,dd.calculation_rate " 
              +" from department_dep dd "
              + " left join data_dictionary dt on dd.dep_category=dt.id "
              + " left join user_usr u on dd.creater=u.id "
              + "left join user_usr u1 on dd.updater = u1.id "
              +" where dep_name like '%"+dep_name+"%' and dep_num like '%"+dep_num+"%' ";
			if(!"全部".equals(sex)){
				sql += " and dd.sex= '"+sex+"'";
			}
			if(!StringUtil.isEmpty(model.getIsActive())){
				sql += " and dd.isActive= 'Y'  ";
			}
			if(!"".equals(dep_category)){
				sql += " and  dd.dep_category='"+dep_category+"' ";
			}
			sql += " order by dd.seq_code ASC ";
			PageSupport map = this.jqm.getList(sql, pageno, pagesize, DepartmentDepDTO.class);
			PageReturnDTO webrole = new PageReturnDTO();
			webrole.setPage(pageno);
			webrole.setRp(pagesize);
			if ((map != null) && (map.getList() != null)) {
				List<DepartmentDepDTO> newList=new ArrayList<DepartmentDepDTO>();
				for(Object od:map.getList()){
					DepartmentDepDTO dd= (DepartmentDepDTO) od;
					Connection connection = null;
					try {
						connection = this.jqm.getConnection();
						String searchCenter="select dd.center_num,dd.center_name from department_vs_center dc,examinatioin_center dd where  dd.center_num=dc.center_num and dc.dep_id='"+dd.getId()+"' ";
						ResultSet rs = connection.createStatement().executeQuery(searchCenter);
						String centername="";
						while (rs.next()) {
							centername=centername+"--"+rs.getString("center_name");
						}
						rs.close();
						if(centername.length()>0){
							centername=centername.substring(2,centername.length());
							dd.setCenternames(centername);
						}
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
					newList.add(dd);
				}
				map.setList(newList);
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
			}
			return webrole;
		}
	
	/**
	 * 
	     * @Title: schedulinglist   
	     * @Description: 排班列表(这里用一句话描述这个方法的作用)   
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return      
	     * @return: PageSupport      
	     * @throws
	 */
	public PageReturnDTO schedulinglist(String user_name,int pageno, int pagesize){
		String sql = " select u.id as user_id,chi_name,convert(varchar(100),w.working_date,23) working_date,t.update_time "
		+" FROM user_usr u left join (select MAX(working_date) as working_date,user_id from "
		+ " work_arrangement group by user_id) w on w.user_id = u.id "
		+" left join  work_arrangement  t on t.working_date=w.working_date  "
		+ " where  u.chi_name like '%"+user_name+"%' and u.is_active = 'Y' ";
		PageSupport map = this.jqm.getList(sql,  pagesize,pageno, SchedulingDTO.class);
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
	public Scheduling saveschedu(Scheduling scheduling)throws ServiceException {
		this.pm.save(scheduling);
		return scheduling;
	}

	@Override
	public List<SchedulingDTO> getScheduledetails(long user_id)throws ServiceException {
		String sql = " select  user_id,u.chi_name ,working_date "
				+" from work_arrangement w,user_usr u  "
				+" where w.user_id=u.id  and user_id =" + user_id ;
		List<SchedulingDTO> list = this.jqm.getList(sql, SchedulingDTO.class);
		return list;
	}

	@Override
	public List<SchedulingDTO> querypaibanList(long userid,UserDTO user,long m_id,long y_id)throws ServiceException {
		String sql=" select w.user_id,w.working_date from  work_arrangement  w "
				  + " where  w.user_id='"+user.getUserid()+"'  and datepart(yy,working_date)='"+y_id+"'  and datepart(m,working_date)= "+m_id;
		List<SchedulingDTO> list = this.jqm.getList(sql,SchedulingDTO.class);
		return  list;
	}

	/**
	 * 添加备忘录
	     * <p>Title: addNoteData</p>   
	     * <p>Description: </p>   
	     * @param n
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.DepartmentService#addNoteData(com.hjw.wst.domain.NoteData)
	 */
	@Override
	public NoteData addNoteData(NoteData n) throws ServiceException {
		this.pm.save(n);
		return n;
	}

	/**
	 * 加载一周备忘信息
	     * <p>Title: querybeiwangList</p>   
	     * <p>Description: </p>   
	     * @param userid
	     * @param user
	     * @param wk_id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.DepartmentService#querybeiwangList(long, com.hjw.wst.DTO.UserDTO, long)
	 */
	@Override
	public List<SchedulingDTO> querybeiwangList(long userid, UserDTO user,long wk_id) throws ServiceException {
		String sql=" select convert(varchar(50),n.notes_date,23) as notes_date,n.notes_content from notes_data n "
				  + " where  n.user_id='"+user.getUserid()+"' and datepart(WK,notes_date)= "+wk_id;
		List<SchedulingDTO> list = this.jqm.getList(sql,SchedulingDTO.class);
		return  list;
	}

	@Override
	public NoteData updateNote(NoteData n) throws ServiceException {
		this.pm.update(n);
		return n;
	}

	@Override
	public NoteData queryDate(String d) throws ServiceException {
		List<NoteData> list = this.qm.find("from NoteData n where n.notes_date ='"+d+"'");
		if(list.size() != 0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void deleteNote(NoteData n) throws ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NoteData loadNote(long n_id) throws ServiceException {
		return (NoteData)this.qm.get(SchedulingDTO.class, n_id);
	}

	@Override
	public void deleteSched(String u_id,String d_m,String d_y) throws ServiceException {
		Connection con = null;
		String sql=" delete  work_arrangement   where user_id='"+u_id+"' and datepart(yy,working_date)='"+d_y+"'  and datepart(m,working_date)='"+d_m+"' ";
		try {
			con = this.jqm.getConnection();
			int rs = con.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(con!=null){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}

	@Override
	public List<SchedulingDTO> chakanpaiabn(long userid, long m_id,long y_id)throws ServiceException {
		String sql=" select w.user_id,CONVERT(varchar(100),w.working_date, 23) working_date from  work_arrangement  w "
				  + " where  w.user_id='"+userid+"'  and datepart(yy,working_date)='"+y_id+"'  and datepart(m,working_date)= "+m_id;
		List<SchedulingDTO> list = this.jqm.getList(sql,SchedulingDTO.class);
		return  list;
	}
	
	/**
	 * 
	     * @Title: updateCIRate   
	     * @Description: 更新收费项目 利润率和利润金额   
	     * @param: @param deptid
	     * @param: @param rate
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateCIRate(long deptid,long rate) throws ServiceException{
			Connection connection = null;
			try {
				connection = this.jqm.getConnection();
				connection.createStatement().executeUpdate("update charging_item set calculation_rate='"+rate+"',calculation_amount=cast(round(amount*"+rate+"/100,2) as numeric(8,2)) where isActive='Y' and dep_id='"+deptid+"'");
			} catch (SQLException ex) {
				ex.printStackTrace();
				try{
				    connection.rollback();
				}catch(SQLException sqlex){
					sqlex.printStackTrace();
				}
			} finally {
				try {
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException sqle4) {
					sqle4.printStackTrace();
				}
			}
		}

	/**
	 * 
	     * @Title: getCenterforDepid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param depid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getCenterforDepid(String depid,String centerNum)throws ServiceException{
		Connection connection = null;
		String center_num="";
		try {
			connection = this.jqm.getConnection();
			String searchCenter="select dc.center_num from department_vs_center dc where dc.center_num='"+centerNum+"' and dc.dep_id='"+depid+"' ";
			ResultSet rs = connection.createStatement().executeQuery(searchCenter);
			if (rs.next()) {
				center_num=rs.getString("center_num");
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
		return center_num;
	}
	
	/**
	 * 
	     * @Title: saveDepartmentCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param depid
	     * @param: @param centers
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveDepartmentCenter(String depid,String centers,long userid)throws ServiceException{
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			String sql="delete from department_vs_center where dep_id='"+depid+"'";
			connection.createStatement().executeUpdate(sql);
			if(centers!=null&& centers.length()>=0){
				String[] centernums=centers.split(",");
				for(int i=0;i<centernums.length;i++){
					sql="insert into department_vs_center(dep_id,center_num,creater,create_time) values ('"+depid+"','"+centernums[i]+"','"+userid+"',GETDATE())";
					connection.createStatement().executeUpdate(sql);
				}
			}

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
	

	public List<DepartmentDepDTO> getCenterDepartmentList(UserDTO user) throws ServiceException {
		String sql = " SELECT ep.id,ep.dep_name  FROM department_dep ep,department_vs_center ep_c "
					+" WHERE  ep.isActive = 'Y'  AND  ep_c.dep_id = ep.id AND ep_c.center_num = '"+user.getCenter_num()+"' ";
		List<DepartmentDepDTO> li = jqm.getList(sql,DepartmentDepDTO.class);
		return li;
	}

	@SuppressWarnings("resource")
	@Override
	public String saveDepartmentBachCenter(String dep_id, String centers, long userid) throws ServiceException {
		String sql = " delete from department_vs_center WHERE  dep_id  in("+dep_id+") ";
		Connection connection = null;
		Statement statement = null;
		try {
			connection = this.jqm.getConnection();
			connection.createStatement();
			statement = connection.createStatement();
			statement.execute(sql);
			statement = connection.createStatement();
			String[] ids = dep_id.split(",");
			String[] center = centers.split(",");
			String sql_save = "";
			for(int i = 0 ; i < ids.length; i++){
				for(int j = 0 ; j < center.length ; j++){
					sql_save  = " insert into department_vs_center(dep_id,center_num,creater,create_time) "
							 +" values("+ids[i]+",'"+center[j]+"',"+userid+",GETDATE())"; 
					statement.addBatch(sql_save);
				}
			}
			statement.executeBatch();
		} catch (SQLException ex) {
			ex.printStackTrace();						
		} finally {
			try {
				if(statement!=null){
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String updateBatcahDepCenter(DepartmentModel model, UserDTO user) {
		String sql = " select dep_id from department_vs_center d WHERE d.center_num='"+user.getCenter_num()+"' AND d.dep_id in("+model.getDep_ids()+")";
		//this.jpm.executeSql(sql); //修改项目状态
		List<DepartmentDepDTO> li_vs_dep = this.jqm.getList(sql,DepartmentDepDTO.class);
		
		String sql_li_dep = " SELECT * FROM department_dep WHERE id in("+model.getDep_ids()+")";
		List<DepartmentDepDTO> li_dep = this.jqm.getList(sql_li_dep,DepartmentDepDTO.class);
		boolean fal = true;
		for(int i = 0 ; i < li_dep.size() ; i++){
			fal = true;
			for(int j = 0 ; j < li_vs_dep.size() ; j++){
				if(li_dep.get(i).getId()==li_vs_dep.get(j).getDep_id()){
					fal = false;
				}
			}
			if(fal){
				String update_vs = "insert into department_vs_center(dep_id,center_num,creater,create_time,isprint_barcode," 
						+" calculation_rate,dep_address)" 
						+" values" 
						+" ("+li_dep.get(i).getId()+",'"+user.getCenter_num()+"',"+user.getUserid()+",getDate(),'"+li_dep.get(i).getIsPrint_Barcode()+"'"
						+ ",'"+li_dep.get(i).getCalculation_rate()+"','"+li_dep.get(i).getRemark()+"')"; 
				int res = this.jpm.execSql(update_vs);
			}
		}
		return "";
	}
	@Override
	public String deleteCenterDep(DepartmentModel model, UserDTO user) throws ServiceException {
		String  delete_sql = "delete from department_vs_center  WHERE  dep_id in("+model.getDep_ids()+") AND center_num = '"+user.getCenter_num()+"'";
		int  res= this.jpm.execSql(delete_sql);
		if(res>0){
			return "ok";
		} else {
			return "error";
		}
	}
	@Override
	public DepartmentDepDTO getCenterDep(DepartmentModel model, UserDTO user) throws ServiceException {
		String sql = " SELECT vs.synchrodate,vs.dep_id,de.dep_num,de.dep_name,de.dep_inter_num,de.seq_code,de.dep_category, "
					+" de.sex,vs.isprint_barcode as isPrint_Barcode_c,vs.calculation_rate,vs.view_result_type,vs.dep_address,vs.remark1 "
					+" FROM department_dep de,department_vs_center vs "
					+" WHERE de.id = vs.dep_id  AND de.isActive = 'Y' AND  "
					+" vs.center_num = '"+user.getCenter_num()+"' AND vs.dep_id = '"+model.getId()+"' ";
		List<DepartmentDepDTO> li =	this.jqm.getList(sql, DepartmentDepDTO.class);
		if(li!=null && li.size()>0){
			return li.get(0);
		}
		return null;
	}
	@Override
	public int updateCenterDep(DepartmentModel model, UserDTO user) throws ServiceException {
		String sql = " UPDATE department_vs_center set dep_inter_num='"+model.getDep_inter_num()+"',isprint_barcode='"+model.getIsPrint_Barcode()+"',"
				+"calculation_rate='"+model.getCalculation_rate()+"',view_result_type='"+model.getView_result_type()+"',"
				+ "remark1='"+model.getRemark1()+"',dep_address='"+model.getDep_address()+"'  WHERE  "
				+" dep_id = '"+model.getId()+"' AND center_num = '"+user.getCenter_num()+"' ";
		return this.jpm.execSql(sql);
	}
	@Override
	public void centerupdateCIRate(long deptid, long rate, UserDTO user, DepartmentModel model)
			throws ServiceException {
		Connection connection = null;
		try { 
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String  sql =" update charging_item_vs_center set calculation_rate='"+rate+"',calculation_amount=cast(round(price*"+rate+"/100,2) as numeric(8,2))   "
					+" WHERE charging_item_code = (SELECT c.item_code FROM charging_item c,charging_item_vs_center vs "
					+" WHERE c.item_code = vs.charging_item_code AND c.isActive='Y' AND c.dep_id='"+deptid+"' AND vs.center_num='"+user.getCenter_num()+"') "
					+" AND center_num='"+user.getCenter_num()+"' ";
			connection.createStatement().executeUpdate(sql);
			connection.commit();
		} catch (SQLException ex) {
			ex.printStackTrace();
			try{
			    connection.rollback();
			}catch(SQLException sqlex){
				sqlex.printStackTrace();
			}
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}

	@Override
	public List<DepartmentDepDTO> getDepartmentDepListByExamnum(DepartmentModel model, UserDTO user)
			throws ServiceException {
		String sql = " with cte_child(dep_name,id,item_id,seq_code,item_code) as ("
				+" select   (case when eci.exam_status = 'Y' then ei.item_name+' (已检)' else ei.item_name end) as dep_name ,ei.dep_id as id ,ei.id as item_id,ei.item_seq as seq_code ,ei.item_code from  charging_item ei, examinfo_charging_item  eci , exam_info e  where eci.exam_num = e.exam_num"
				+" and e.exam_num = '"+model.getExam_num()+"'   and eci.isActive = 'Y'   and ei.id = eci.charge_item_id"
				+" and eci.pay_status <> 'M' and (e.is_after_pay = 'Y' or eci.pay_status in ('Y','R'))"
				+" union all"
				+" select d.dep_name ,d.id , 0 as item_id ,d.seq_code, '' as item_code "
				+" from   examinfo_charging_item  eci , exam_info e,charging_item ei"
				+" inner join  "
				+" department_dep  d    "
				+" on ei.dep_id = d.id "
				+" where eci.exam_num = e.exam_num"
				+" and e.exam_num = '"+model.getExam_num()+"'   and eci.isActive = 'Y'   and ei.id = eci.charge_item_id  "  ;

		if(!"Y".equals(model.getRemark())){ //  功能页面查询所有的科室
			sql += "  and  d.id in ( select  distinct dep_id from dep_user where  user_id = "+user.getUserid()+" )";
		}
		sql +=" and eci.pay_status <> 'M' and (e.is_after_pay = 'Y' or eci.pay_status in ('Y','R'))) select distinct * from cte_child order by seq_code ";

		List<DepartmentDepDTO> list = this.jqm.getList(sql, DepartmentDepDTO.class);
		return list;
	}


	@Override
	public ExamDepResultDTO getDepartmentResultById(DepartmentModel departmentModel, UserDTO user)
			throws ServiceException {
		String sql = " select   distinct    dd.dep_category ,  ed.exam_doctor , ed.exam_result_summary ,ed.suggestion, ed.update_time ,ed.create_time from   department_dep dd   "
				+" left  join  exam_dep_result  ed   on ed.dep_id = dd.id and ed.exam_num = '"+departmentModel.getExam_num()+"'"
				+"  where dd.id = '"+departmentModel.getId()+"'";
		List<ExamDepResultDTO> list = this.jqm.getList(sql, ExamDepResultDTO.class);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

    @Override
    public String saveDepCenter(DepartmentModel model,UserDTO user) throws ServiceException {
	    StringBuffer sql = new StringBuffer("  insert into department_vs_center values( ");
        model.setDep_address( model.getRemark());
        sql.append(model.getId()+",");
        sql.append("'"+model.getCenter_num()+"',");
        sql.append(user.getUserid()+",");
        sql.append("GETDATE(),");
        sql.append("'"+ model.getDep_address()+"',");
        sql.append("'"+model.getDep_inter_num()+"',");
        sql.append("'"+model.getIsPrint_Barcode()+"',");
        sql.append("NULL,");
        sql.append("NULL,");
        sql.append(model.getCalculation_rate()+",");
        sql.append(model.getView_result_type()+",");
        if(!StringUtils.isEmpty(model.getRemark1())){
            sql.append("'"+model.getRemark1()+"',");
        } else {
            sql.append("NULL,");
        }
        if(!StringUtils.isEmpty(model.getRemark2())){
            sql.append("'"+model.getRemark2()+"'");
        } else {
            sql.append("NULL");
        }
        sql.append(")");
        this.jpm.execSql(sql.toString());
        return null;
    }
}
