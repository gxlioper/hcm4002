package com.hjw.crm.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.CrmMemberPrivateDoctorDTO;
import com.hjw.crm.domain.CrmMemberPrivateDoctor;
import com.hjw.crm.model.CrmMemberPrivateDoctorModel;
import com.hjw.crm.service.CrmMemberPrivateDoctorService;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CrmMemberPrivateDoctorServiceImpl implements CrmMemberPrivateDoctorService{
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private PersistenceManager persistenceManager;
	
	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public JdbcQueryManager getJdbcQueryManager() {
		return jdbcQueryManager;
	}

	public void setJdbcQueryManager(JdbcQueryManager jdbcQueryManager) {
		this.jdbcQueryManager = jdbcQueryManager;
	}

	public JdbcPersistenceManager getJdbcPersistenceManager() {
		return jdbcPersistenceManager;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jdbcPersistenceManager) {
		this.jdbcPersistenceManager = jdbcPersistenceManager;
	}

	public PersistenceManager getPersistenceManager() {
		return persistenceManager;
	}

	public void setPersistenceManager(PersistenceManager persistenceManager) {
		this.persistenceManager = persistenceManager;
	}

	@Override
	public PageReturnDTO getCrmMemberPrivateDoctorList(CrmMemberPrivateDoctorModel model, int page, int pageSize,UserDTO user)
			throws ServiceException, SQLException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私资源 Y有 N无
		int count = 0;

		String sql="select c.id as c_id,c.arch_num,e.exam_num,e.id,dbo.fun_CharToStar(e.exam_num,'name',c.user_name,'"+isprivateflag+"') as user_name,dbo.fun_CharToStar(e.exam_num,'id_num',c.id_num,'"+isprivateflag+"') as id_num,e.age,e.status,c.sex,dbo.fun_CharToStar(e.exam_num,'phone',e.phone,'"+isprivateflag+"') as phone,"
				+ " convert(varchar(50),e.join_date,23) as join_date,e.exam_type,dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,"
				+ " dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay "
				+ " ,d.doctor_id,u.chi_name as doctorname,d.allot_person,d.allot_date ,s.chi_name as allot_person_name"
				+ " from customer_info c,exam_info e "
				+ " left join company_info v on v.id=e.company_id "
				+ " left join crm_member_private_doctor d on e.exam_num = d.exam_num "
				+ " left join user_usr u on u.id=d.doctor_id "
				+ " left join user_usr s on d.allot_person=s.id"
				+ " left join examinfo_set es on e.exam_num = es.exam_num"
				+ " where e.customer_id=c.id ";
		
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
		
		if (model.getIsvisit() != null && model.getIsvisit().trim().length()>0){
			sql += " and e.isvisit = '"+model.getIsvisit()+"' ";
			count ++;
		}
		
		if (model.getUser_name() != null && !"".equals(model.getUser_name())) {// 体检姓名
			sql += " and c.user_name   like '" + model.getUser_name().trim() + "%'";
			count ++;
		}
		if (model.getS_join_date() != null && !"".equals(model.getS_join_date())) {// 体检开始日期
			sql += " and e.join_date >= '" + model.getS_join_date() + " 00:00:00.000'";
			count ++;
		} 
		if (model.getE_join_date() != null && !"".equals(model.getE_join_date())) {// 体检结束日期
			sql += " and e.join_date < '" + model.getE_join_date() + " 23:59:59.999'";
			count ++;
		} 
		if (model.getCompany_id() > 0) {
			sql += " and e.company_id='" + model.getCompany_id() + "'";
			count ++;
		}
		if (model.getExam_type() != null && !"".equals(model.getExam_type())){
			sql += " and e.exam_type ='" + model.getExam_type() + "'";
		}
		if (model.getPhone() != null && !"".equals(model.getPhone())){
			sql += " and e.phone = '" + model.getPhone() +"'";
			count ++;
		}
		if (model.getStatus() != null && !"".equals(model.getStatus())){
			sql += " and e.status = '"+model.getStatus()+"'";
		}
		if (model.getSex() != null && !"".equals(model.getSex())){
			sql += " and c.sex = '"+model.getSex()+"' ";
		}
		
		if("Y".equals(model.getAllot_status())){
			sql += " and doctor_id is not null";	
		}else if("N".equals(model.getAllot_status())){
			sql += " and doctor_id is null";
		}
		if(model.getSet_id() > 0){
			sql += " and es.exam_set_id = "+ model.getSet_id();
			count ++;
		}
		sql = "select * from ("+sql+") e where 1=1";
		if(model.getAmount1() != null && !"".equals(model.getAmount1())){
			sql += " and e.personal_pay >= '"+model.getAmount1()+"'";
			count ++;
		}
		if(model.getAmount2() != null && !"".equals(model.getAmount2())){
			sql += " and e.personal_pay <= '"+model.getAmount2()+"'";
			count ++;
		}
		sql += " order by e.join_date desc";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if(count == 0){
			return webrole;
		}
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmMemberPrivateDoctorDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	@Override
	public CrmMemberPrivateDoctor getCrmMemberPrivateDoctor(String id) throws ServiceException {
		String sql="From CrmMemberPrivateDoctor c where c.id='"+id+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (CrmMemberPrivateDoctor) list.get(0);
		}else{
			return null;
		}
	}
	public CrmMemberPrivateDoctor getCrmMemberPrivateDoctor(String exam_num,String arch_num,String userid) throws ServiceException {
		String sql="From CrmMemberPrivateDoctor c where c.exam_num='"+exam_num+"' and c.arch_num='"+arch_num+"' and c.doctor_id='"+userid+"'";
		List list=queryManager.find(sql);
		if(list.size()>0){
			return (CrmMemberPrivateDoctor) list.get(0);
		}else{
			return null;
		}
	}


	@Override
	public String mergeCrmMemberPrivateDoctor(CrmMemberPrivateDoctorModel model) throws ServiceException {
		String[] exam_nums = model.getExam_num().split(",");
		String[] arch_nums = model.getArch_num().split(",");
		for (int i = 0; i < exam_nums.length; i++) {
			List<CrmMemberPrivateDoctor> pdoctor = this.queryManager.find("from CrmMemberPrivateDoctor c where c.exam_num = '"+exam_nums[i]+"'");
			if(pdoctor.size() != 0){
				CrmMemberPrivateDoctor pd = pdoctor.get(0);
				pd.setDoctor_id(model.getDoctor_id());
				pd.setAllot_date(DateTimeUtil.parse());
				pd.setAllot_person(model.getAllot_person());
				pd.setFlag("0");
				this.persistenceManager.update(pd);
			}else{
				CrmMemberPrivateDoctor pd = new CrmMemberPrivateDoctor();
				pd.setArch_num(arch_nums[i]);
				pd.setExam_num(exam_nums[i]);
				pd.setDoctor_id(model.getDoctor_id());
				pd.setAllot_date(DateTimeUtil.parse());
				pd.setAllot_person(model.getAllot_person());
				pd.setFlag("0");
				this.persistenceManager.save(pd);
			}
		}
		return "分配成功";
	}

	@Override
	public PageReturnDTO getCrmDoctorMemberList(CrmMemberPrivateDoctorModel model, int page, int pageSize)
			throws ServiceException, SQLException {
		String sql="select c.id as c_id,c.arch_num,e.exam_num,e.id,c.user_name,c.id_num,e.age,e.status,c.sex,e.phone,"
				+ " convert(varchar(50),e.join_date,23) as join_date,e.exam_type,dbo.GetExamSetNameByExamID(e.exam_num) as set_name,v.com_name as company,"
				+ " dbo.GetTeamPayByExamId(e.exam_num) as team_pay,dbo.GetPersonalPayByExamId(e.exam_num) as personal_pay "
				+ " ,d.doctor_id,u.chi_name as doctorname,d.allot_person,d.allot_date ,s.chi_name as allot_person_name,"
				+" (select count(*) from crm_visit_plan cvp where cvp.exam_num = e.exam_num)as cvp_count,"
				+"  (select  count (*) from crm_visit_record cvr where cvr.exam_num = e.exam_num) as cvr_count"	
				+ " from customer_info c,exam_info e "
				+ " left join company_info v on v.id=e.company_id "
				+ " left join crm_member_private_doctor d on e.exam_num = d.exam_num "
				+ " left join user_usr u on u.id=d.doctor_id "
				+ " left join user_usr s on d.allot_person=s.id"
				+ " left join examinfo_set es on e.exam_num = es.exam_num"
				+ " where e.customer_id=c.id and d.doctor_id='"+model.getDoctor_id()+"' ";
		if(model.getArch_num()!=null&&model.getArch_num().length()!=0){
			sql+=" and c.arch_num='"+model.getArch_num()+"'";
		}
		if(model.getExam_num()!=null&&model.getExam_num().length()!=0){
			sql+=" and e.exam_num='"+model.getExam_num()+"'";
		}
		if(model.getUser_name()!=null&&model.getUser_name().length()!=0){
			sql+=" and c.user_name='"+model.getUser_name()+"'";
		}
		if(model.getAllot_date()!=null&&model.getAllot_date().length()!=0){
			sql+=" and d.allot_date<'"+model.getAllot_date()+" 23:59:59' and d.allot_date>='"+model.getAllot_date()+" 00:00:00'";
		}
		if(model.getFlag()!=null&&model.getFlag().length()>0){
			sql+=" and d.flag='"+model.getFlag()+"'";
		}
		PageSupport map = jdbcQueryManager.getList(sql,page,pageSize,CrmMemberPrivateDoctorDTO.class);
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
	public String updateCrmMemberPrivateDoctor(CrmMemberPrivateDoctor crmMemberPrivateDoctor) throws ServiceException {
		try {
			this.persistenceManager.update(crmMemberPrivateDoctor);
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}
	@Override
	public String saveCrmMemberPrivateDoctor(CrmMemberPrivateDoctor crmMemberPrivateDoctor) throws ServiceException {
		try {
			this.persistenceManager.save(crmMemberPrivateDoctor);
			return "1";
		} catch (Exception e) {
			return "0";
		}
	}
}
