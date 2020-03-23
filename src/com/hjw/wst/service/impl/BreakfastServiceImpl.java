package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.model.ExaminfoChargingItemModel;
import com.hjw.wst.service.BreakfastService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class BreakfastServiceImpl implements BreakfastService {
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

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerExamDTO> queryItemList(String exam_num,UserDTO user)throws ServiceException {
		String sql=" select distinct c.id ,i.item_code,i.item_name,c.exam_status,c.exam_date,c.exam_doctor_name "
				  +" from examinfo_charging_item c ,charging_item  i, exam_info e  "
				  +" where c.charge_item_id=i.id and  e.exam_num=c.exam_num and e.is_Active='Y' and c.isActive = 'Y' and c.pay_status <> 'M' "
				  +" and e.exam_num='"+exam_num+"' and c.center_num = '"+user.getCenter_num()+"' "
				  +" and i.dep_id='"+user.getDep_id()+"'";
		List<CustomerExamDTO> list = this.jqm.getList(sql,CustomerExamDTO.class);
		return  list;
	}

	@Override
	public ExaminfoChargingItem queryById(long id) throws ServiceException {
		return (ExaminfoChargingItem)this.qm.get(ExaminfoChargingItem.class, id);
	}

	@Override
	public void updateBreakStatus(String ids,UserDTO user) throws ServiceException {
		String sql="";
		Connection connection = null;
		try {
			sql+="update examinfo_charging_item set exam_status='Y',exam_date=getdate(),exam_doctor_name='"+user.getName()+"',exam_doctor_id="+user.getUserid()+",exam_center_num='"+user.getCenter_num()+"' where id in("+ids+")";
			connection = this.jqm.getConnection();
			int rs = connection.createStatement().executeUpdate(sql);
		} catch (SQLException e){
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
	public PageReturnDTO getExamInfoList(ExaminfoChargingItemModel model, UserDTO user, int rows, int page)
			throws ServiceException {
		int count = 0;
		String sql =" select distinct  e.status,e.id,c.arch_num,c.id_num,e.exam_num,e.age,c.user_name,c.sex,e.phone,"
				   +" ec.exam_status,e.freeze, "
				   +" convert(varchar(50),e.join_date,23) as join_date,e.exam_type,e.join_date as d, "
				   +" (case when e.status = 'Z' then e.final_doctor else '' end) as final_doctor, "
				   +" (case when es.approve_status = 'A' then u.chi_name else '' end) as check_doctor, "
				   +" dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company, "
				   +" dbo.GetExamDoctorNameByExamId(e.exam_num,"+user.getDep_id()+") as exam_times"
				   +" from customer_info c,examinfo_charging_item ec,charging_item ci,exam_info e "
				   +" left join exam_summary es on e.exam_num = es.exam_num  and  es.center_num='"+user.getCenter_num()+"' "
				   +" left join user_usr u on u.id = es.check_doc "
				   + " left join company_info v on v.id=e.company_id "
				   +" where e.customer_id = c.id and e.exam_num = ec.exam_num and ec.charge_item_id = ci.id "
				   +" and ec.isActive = 'Y' and (e.is_after_pay = 'Y' or ec.pay_status in ('Y','R')) "
				   +" and e.is_Active = 'Y' and ec.pay_status <> 'M' and ec.exam_status <> 'G' and ec.center_num = '"+user.getCenter_num()+"'"
				   +" and ci.dep_id = "+user.getDep_id();
		if (model.getArch_num() != null && !"".equals(model.getArch_num())) {// 档案号
			sql += " and c.arch_num='" + model.getArch_num().trim() + "'";
			count ++;
		}
		if (model.getId_num() != null && !"".equals(model.getId_num())) {// 身份证
			sql += " and c.id_num='" + model.getId_num() + "'";
			count ++;
		}  
		if (model.getExam_num() != null && !"".equals(model.getExam_num())) {// 体检号
			sql += " and e.exam_num='" + model.getExam_num().trim() + "'";
			count ++;
		}
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
			count ++;
		}  
		if (model.getTime1() != null && !"".equals(model.getTime1())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getTime1() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getTime2() != null && !"".equals(model.getTime2())) {// 体检结束日期
			sql += " and e.join_date < '" + model.getTime2() + " 23:59:59.999'";
			count ++;
		} 
		if (model.getEmployeeID() != null && "".equals(model.getEmployeeID())) {// 工号
			sql += " and e.employeeID='" + model.getEmployeeID().trim() + "'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getExam_status() != null && !"".equals(model.getExam_status())){
			sql += " and ec.exam_status = '"+model.getExam_status()+"' ";
		}
		sql += " order by ec.exam_status,e.join_date desc";
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		if(count == 0){
			return webrole;
		}
		PageSupport map = this.jqm.getList(sql,page, rows,ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
}
