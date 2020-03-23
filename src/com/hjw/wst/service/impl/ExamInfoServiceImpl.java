package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.axis.utils.StringUtils;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.Examinatioin_centerDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamInfoRecyclingGuid;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.service.examInfoService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class ExamInfoServiceImpl implements examInfoService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

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
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	//未检项目查询
	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoChargingItemDTO> queryWjxmExamInfo(String examNum, String center_num) throws ServiceException {
		String sql =" select ec.id,e.exam_num,c.id as charge_item_id,c.item_code,c.item_name,d.dep_name,ec.exam_status, "
                    +" CONVERT(varchar(100),ec.final_exam_date,23) as create_time,c.sam_demo_id as sample_id,ec.examinfo_id,d.dep_category "
                    +" from exam_info e,charging_item c,department_dep d,examinfo_charging_item ec left join user_usr u on u.id = ec.creater "
					+" where ec.exam_num = e.exam_num and ec.charge_item_id = c.id   and   c.item_category <> '耗材类型'     and   c.dep_id = d.id and ec.pay_status <> 'M'  "
					+" and ec.exam_status <> 'G'  and ec.exam_status <> 'Y'  and ec.isActive = 'Y' and ec.change_item <> 'C' and ec.center_num = '"+center_num+"' and e.is_Active='Y' and e.exam_num = '"+examNum+"' "
					+" order by d.seq_code,c.item_seq ";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		
		for(ExaminfoChargingItemDTO examinfoitem : list){
			if("131".equals(examinfoitem.getDep_category())){
				String sql1="select * from sample_exam_detail s where s.exam_num = '"+examinfoitem.getExam_num()+"' and s.sample_id = "+examinfoitem.getSample_id();
				List<SampleExamDetailDTO> listsample = this.jqm.getList(sql1, SampleExamDetailDTO.class);
				if(listsample.size() > 0){
					examinfoitem.setSample_status(listsample.get(0).getStatus());
					examinfoitem.setSample_statuss(listsample.get(0).getStatus_y());
				}
			}
		}
		return list;
	}
	/**
	 * 查找实体类
	     * <p>Title: loadExamInfo</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.examInfoService#loadExamInfo(long)
	 */
	@Override
	public ExaminfoChargingItem loadExamInfo(long id) throws ServiceException {
			return (ExaminfoChargingItem)this.qm.get(ExaminfoChargingItem.class, id);
	}

	@Override
	public PageReturnDTO queryPageExamInfo(String exam_num, int pageno, int pagesize) {
		String sql=" ";
		PageSupport map = this.jqm.getList(sql,pagesize, pageno, Examinatioin_centerDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	

	//弃项列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ExaminfoChargingItemDTO> queryQjxmExamInfo(String examNum, String center_num) throws ServiceException {
		String sql = " select ec.id,c.id as charge_item_id,c.item_code,c.item_name,d.dep_name "
					+" from exam_info e,charging_item c,department_dep d,examinfo_charging_item ec left join user_usr u on u.id = ec.creater "
					+" where ec.exam_num = e.exam_num   and    c.item_category <>  '耗材类型'    and ec.charge_item_id = c.id and c.dep_id = d.id and ec.pay_status <> 'M'  "
					+" and ec.exam_status = 'G' and ec.isActive = 'Y' and ec.change_item <> 'C' "
					+ "and ec.center_num = '"+center_num+"' and e.is_Active='Y'  and e.exam_num = '"+examNum+"' "
					+" order by ec.is_new_added desc,d.seq_code,c.item_seq ";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		return list;
	}

	//恢复
	@Override
	public ExaminfoChargingItem updateExam(ExaminfoChargingItem examInfo) throws ServiceException {
		this.pm.update(examInfo);
		return examInfo;
	}
	
	//放弃  
	@Override
	public ExaminfoChargingItem deleteExam(ExaminfoChargingItem examInfo) throws ServiceException {
		this.pm.update(examInfo);
		return examInfo;
	}

	@Override
	public void receiveExamInfo(ExamInfo examInfo) throws ServiceException {
		this.pm.update(examInfo);
	}

	@Override
	public ExamInfo findExamInfo(String exam_num) throws ServiceException {
		String hql = " from ExamInfo where exam_num = '"+exam_num+"'";
		List<ExamInfo> li = this.qm.find(hql);
		if(li!=null && li.size()>0){
			return li.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void updateBatchDeletExamInfo(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="update examinfo_charging_item set exam_status='G' where id in("+ids+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void plhf(String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="update examinfo_charging_item set exam_status='N' where id in("+ids+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void updateplyq(String date, String ids) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="update examinfo_charging_item set exam_status='D',final_exam_date='"+date+"' where id in("+ids+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@Override
	public PageReturnDTO getExamInfo(ExamInfoDTO info, int pagesize,int pageno,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		if(pageno<=0)
			pageno=1;
		int startindex=pagesize*(pageno-1);
		int endindex=pagesize*(pageno);
		StringBuffer sql2 = new StringBuffer();
		String sqlbianliang = "";
		String sql=" 	SELECT * FROM(SELECT TOP "+endindex+" ROW_NUMBER() OVER( order by update_time desc ) AS ROWID,* FROM ("
				+ "SELECT e.id,c.arch_num,e.exam_num,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,c.sex,e.age,"
				+"dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.company,"
				+ "dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,e.exam_type,e.register_date,"
				+ "e.is_Active,u.chi_name as c_name,uu.chi_name as u_name,e.update_time  FROM exam_info e "
				+"  LEFT JOIN  customer_info c ON e.customer_id=c.id "
				+ " LEFT JOIN  user_usr u  on  u.id = e.creater  AND u.is_active = 'Y' "
				+ " LEFT JOIN  user_usr uu  on  uu.id = e.updater  AND uu.is_active = 'Y' "
				+ " WHERE (e.is_Active='Y' or  e.is_Active='N')  AND e.center_num = '"+user.getCenter_num()+"'  ";
		
				sql2.append("select count(*) as counts from customer_info c ,exam_info e");
				sql2.append(" where e.customer_id=c.id  AND e.center_num = '"+user.getCenter_num()+"' ");
				sql2.append(" and c.is_Active='Y' ");
				
				if(info.getStart_date()!=null&&!info.getStart_date().equals("")){
					  sqlbianliang=" and e.join_date>='"+info.getStart_date().trim()+" 00:00:00'";
							  	sql+=sqlbianliang;
							  	sql2.append(sqlbianliang);
					}
				
				if(info.getEnd_date()!=null&&!info.getEnd_date().equals("")){
					  sqlbianliang=" and e.join_date<='"+info.getEnd_date().trim()+" 23:59:59'";
							  	sql+=sqlbianliang;
							  	sql2.append(sqlbianliang);
					}
				if(info.getArch_num()!=null&&!info.getArch_num().equals("")){
				  sqlbianliang=" and c.arch_num='"+info.getArch_num().trim()+"'";
						  	sql+=sqlbianliang;
						  	sql2.append(sqlbianliang);
				}
				if(info.getIs_Active()!=null&&!info.getIs_Active().equals("")){
					  sqlbianliang=" and e.is_Active='"+info.getIs_Active().trim()+"'";
							  	sql+=sqlbianliang;
							  	sql2.append(sqlbianliang);
					}
				if(info.getExam_num()!=null&&!info.getExam_num().equals("")){
					sqlbianliang=" and  e.exam_num='"+info.getExam_num().trim()+"'";
					 	sql+=sqlbianliang;
						sql2.append(sqlbianliang);
				}
				if(info.getUser_name()!=null&&!info.getUser_name().equals("")){
					sqlbianliang=" and c.user_name  like'%"+info.getUser_name().trim()+"%'";	
						  	sql+=sqlbianliang;
							sql2.append(sqlbianliang);
				}
				if(info.getPhone()!=null&&!info.getPhone().equals("")){
					sqlbianliang=" and   e.phone='"+info.getPhone().trim()+"'";	
						  	sql+=sqlbianliang;
							sql2.append(sqlbianliang);
				}
				if(info.getId_num()!=null&&!info.getId_num().equals("")){
					sqlbianliang=" and   c.id_num='"+info.getId_num()+"'";	
						  	sql+=sqlbianliang;
							sql2.append(sqlbianliang);
				}
				if(info.getCompany()!=null&&!info.getCompany().equals("")){
					sqlbianliang=" and   e.company_id ='"+info.getCompany().trim()+"'";	
							sql+=sqlbianliang;
							sql2.append(sqlbianliang);
				}
				if(info.getRen_type()>0){
					sqlbianliang=" and e.customer_type_id='"+info.getRen_type()+"'  ";	
					  	sql+=sqlbianliang;
						sql2.append(sqlbianliang);
				}
			 // sql+=" order by e.id desc ";
			  sql+=" ) as LeaveWordView ) as TEMP1 WHERE ROWID>"+startindex+" ";	
			  PageSupport map = this.jqm.getList(sql2.toString(),sql,pageno,pagesize,ExamInfoDTO.class);
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
	public int updateExamInfo(ExamInfoDTO dto) throws ServiceException {
		int rs = 0 ;
		Connection connection = null;
		try {
			String sql="UPDATE exam_info set is_Active='"+dto.getIs_Active()+"',updater='"+dto.getUpdater()+"',update_time='"+DateTimeUtil.getDateTime()+"'  where exam_num ='"+dto.getExam_num()+"'";
			connection = this.jqm.getConnection();
			rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return rs;
	}

	@Override
	public void revocationDYD(String exam_num) throws ServiceException {
		if(!StringUtils.isEmpty(exam_num)){
			ExamInfo examinfo = this.findExamInfo(exam_num);
			examinfo.setIs_guide_back("N");
			this.pm.update(examinfo);
			
			String hql = "FROM  ExamInfoRecyclingGuid   WHERE  exam_num='"+exam_num+"' ";
			List<ExamInfoRecyclingGuid>   list = qm.find(hql);
			if( list.size()>0 && list!=null ){
				for (ExamInfoRecyclingGuid examInfoRecyclingGuid : list) {
					pm.remove(examInfoRecyclingGuid);
				}
			}
			
		}
		
	}

	@Override
	public void saveExamInfoReportType(long examinfo_id, long report_type) throws ServiceException {
		String sql = "update exam_info set report_type = "+report_type+" where id = "+examinfo_id;
		this.jpm.executeSql(sql);
	}

	@Override
	public void updateExamInfo(ExamInfo examinfo) throws ServiceException {
		this.pm.update(examinfo);
	}
	
}
