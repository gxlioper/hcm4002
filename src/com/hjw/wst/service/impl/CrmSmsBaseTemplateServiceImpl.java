package com.hjw.wst.service.impl;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CrmSmsSendDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.CrmSmsBaseTemplateDTO;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
import com.hjw.wst.domain.CrmSmsBaseTemplate;
import com.hjw.wst.domain.CrmSmsSend;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.model.CrmSmsBaseTemplateModel;
import com.hjw.wst.service.CrmSmsBaseTemplateService;
import com.hjw.wst.service.CustomerInfoService;

public class CrmSmsBaseTemplateServiceImpl implements CrmSmsBaseTemplateService {
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private CustomerInfoService customerInfoService;

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
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
	public void deleteCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model) throws ServiceException {
		String sql = "select * from crm_sms_base_template where id='"+model.getId()+"'";
		List<CrmSmsBaseTemplate> list = this.jqm.getList(sql, CrmSmsBaseTemplate.class);
		if(list != null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				String sql2 = "select * from crm_sms_base_template where sms_category='"+list.get(0).getSms_category()+"' and id !='"+model.getId()+"' order by create_time ";
				List<CrmSmsBaseTemplate> list2 = this.jqm.getList(sql2, CrmSmsBaseTemplate.class);
				if(list2 != null && list2.size()>1){
					String sql3 = "update  crm_sms_base_template set sms_state = 'Y' where id = '"+list2.get(0).getId()+"'";
					this.jpm.execSql(sql3);
					
					CrmSmsBaseTemplate cr = getCrmSmsBaseTemplate(model.getId());
					this.pm.remove(cr);
					
				}else{
					CrmSmsBaseTemplate cr = getCrmSmsBaseTemplate(model.getId());
					this.pm.remove(cr);
				}
			}
		}
		
		
		
		
	}

	@Override
	public void svaeCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model,UserDTO user) throws ServiceException {
		 
		String sql = "select sms_state from crm_sms_base_template where sms_category='"+model.getSms_category()+"'";
		List<CrmSmsBaseTemplate> list = this.jqm.getList(sql, CrmSmsBaseTemplate.class);
		
		if(list != null && list.size()>0){// 如果有此类型的短信模板
			//判断 短信模板是否默认
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getSms_state().equals("Y")){//如果是默认  则新增的只能是 非默认模板
					if(model.getSms_state().equals("Y")){
						String sql2 = "update  crm_sms_base_template set sms_state = 'N' where sms_category = '"+model.getSms_category()+"'";
						this.jpm.execSql(sql2);
						model.setSms_state("Y");
					}else{
						model.setSms_state("N");
					}
					
				}else{//如果是非默认模板  则需要判断 新增的是默认还是 费默认
					model.setSms_state("Y");
				}
			}
			
		}else{
			
			model.setSms_state("Y");
		}
		
		CrmSmsBaseTemplate sm = new CrmSmsBaseTemplate(model.getId(),model.getSms_category(),model.getSms_name(),
				model.getSms_note(),user.getUserid(),DateTimeUtil.parse(),user.getUserid(),
				DateTimeUtil.parse(),model.getSms_state());//
		
		this.pm.save(sm);
		
		
		
		
	}

	@Override
	public void updateCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model,UserDTO user) throws ServiceException {
		
		if(model.getSms_state().equals("Y")){//
			String sql2 = "update  crm_sms_base_template set sms_state = 'N' where sms_category = '"+model.getSms_category()+"'";
			this.jpm.execSql(sql2);
			model.setSms_state("Y");
		}else{
			//如果把 此条短信模板改为非默认  需要判断  此类型的短信模板 是否是多条
			String sql = "select * from crm_sms_base_template where sms_category='"+model.getSms_category()+"' order by create_time ";
			List<CrmSmsBaseTemplate> list = this.jqm.getList(sql, CrmSmsBaseTemplate.class);
			//如果此类型短信模板是多条  想要改为非默认模板 则把最近修改的一条改为默认
			if(list != null && list.size()>1){
				
				String sql3 = "update  crm_sms_base_template set sms_state = 'Y' where id = '"+list.get(0).getId()+"'";
				this.jpm.execSql(sql3);
				model.setSms_state("N");
			}else{
				//如果此类型短信模板只有一条 且这一条是默认  则不允许修改(每个类型的短信模板  必须有一个默认)
				model.setSms_state("Y");
			}
			
			
		}
		
		CrmSmsBaseTemplate cs = getCrmSmsBaseTemplate(model.getId());
		cs.setUpdate_time(DateTimeUtil.parse());
		cs.setUpdater(user.getUserid());
		cs.setSms_category(model.getSms_category());
		cs.setSms_name(model.getSms_name());
		cs.setSms_note(model.getSms_note());
		cs.setSms_state(model.getSms_state());
		this.pm.update(cs);
	}
	
	public void updateCrmSmsBaseTemplate(CrmSmsBaseTemplate cmbt) throws ServiceException {
		this.pm.update(cmbt);
	}

	public PageReturnDTO queryCrmSmsBaseTemplate(CrmSmsBaseTemplateModel model,int page,int pageSize) throws ServiceException {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT c.sms_state,u.chi_name  as chi_name_c,up.chi_name  as chi_name_x,d.data_name  as  sms_category,"
				+ "c.id,c.sms_name,sms_note,c.creater,c.create_time,c.updater,c.update_time ");
		sql.append(" FROM crm_sms_base_template  c LEFT JOIN  user_usr  u  ON  u.id=c.creater ");
		sql.append(" LEFT JOIN    user_usr  up ON  up.id=c.updater  ");
		sql.append(" LEFT JOIN   data_dictionary    d   on   d.id=c.sms_category  AND   data_code='DXMBLX'  where    u.is_active='Y' ");
		if(model.getSms_name()!=null && !"".equals(model.getSms_name())){
			sql.append("   AND   c.sms_name  LIKE '%"+model.getSms_name().trim()+"%'  ");
		}
		if(model.getSms_category()!=null && !"".equals(model.getSms_category())){
			sql.append("   AND   c.sms_category='"+model.getSms_category()+"'  ");
		}
		sql.append(" order  by   c.id  desc");
		PageSupport map = this.jqm.getList(sql.toString(),page,pageSize,CrmSmsBaseTemplateDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;	
	}
	
	
	public List<CrmSmsBaseTemplateDTO> queryCrmSmsBaseTemplateAll() throws ServiceException {
		StringBuffer sql = new StringBuffer();
		sql.append("select id,sms_name from crm_sms_base_template where sms_state='Y'");
		List<CrmSmsBaseTemplateDTO> map = this.jqm.getList(sql.toString(),CrmSmsBaseTemplateDTO.class);		
		return map;	
	}

	/**
	 * 
	     * <p>Title: getCrmSmsBaseTemplate</p>   
	     * <p>Description: </p>   
	     * @param temidl
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CrmSmsBaseTemplateService#getCrmSmsBaseTemplate(java.lang.String)
	 */
	public CrmSmsBaseTemplate getCrmSmsBaseTemplate(String temidl) throws ServiceException{
		return (CrmSmsBaseTemplate) this.qm.get(CrmSmsBaseTemplate.class, temidl);
	}

	@Override
	public PageReturnDTO queryCrmSmsSend(CrmSmsBaseTemplateModel model, String center_num,
			int page, int pageSize) throws ServiceException {
		StringBuffer sql1 = new StringBuffer();
		//StringBuffer sql2 = new StringBuffer();
		sql1.append(" SELECT ss.sms_date,ss.id,ei.exam_num,cs.user_name,ss.sms_phone,ss.sms_note,ss.sms_status,ss.user_type,ur.chi_name,ss.sms_type    FROM");
		sql1.append("  exam_info   ei,customer_info  cs,crm_sms_send ss  LEFT JOIN user_usr ur  ON  ur.id=ss.send_user    ");
		sql1.append("WHERE   ei.id=ss.user_id   AND  cs.id=ei.customer_id ");
		//sql2.append(" SELECT ss.sms_date,ss.id,'',ei.chi_name as user_name,ss.sms_phone,ss.sms_note,ss.sms_status,ss.user_type,ur.chi_name,ss.sms_type    FROM  ");
		//sql2.append(" user_usr   ei,crm_sms_send ss  left join user_usr ur  ON  ur.id=ss.send_user   WHERE   ei.id=ss.user_id   ");
		
		if(model.getTemplate_id()!=null && !"".equals(model.getTemplate_id())){
			sql1.append(" AND  ss.template_id ='"+model.getTemplate_id().trim()+"' ");
			//sql2.append(" AND  ei.chi_name like '%"+model.getName().trim()+"%' ");
		}
		if(model.getName()!=null && !"".equals(model.getName())){
			sql1.append(" AND  cs.user_name like '%"+model.getName().trim()+"%' ");
			//sql2.append(" AND  ei.chi_name like '%"+model.getName().trim()+"%' ");
		}
		if(model.getExam_num()!=null && !"".equals(model.getExam_num())){
			sql1.append(" AND  ei.exam_num='"+model.getExam_num().trim()+"' ");
		}
		if(model.getPhone()!=null && !"".equals(model.getPhone())){
			sql1.append(" AND  ss.sms_phone='"+model.getPhone()+"' ");
			//sql2.append(" AND  ss.sms_phone='"+model.getPhone()+"' ");
		}
		if(model.getUser_type()!=null && !"".equals(model.getUser_type())){
			sql1.append(" AND  ss.user_type='"+model.getUser_type()+"' ");
			//sql2.append(" AND  ss.user_type='"+model.getUser_type()+"' ");
		}
		if(model.getSms_type()>0){
			sql1.append(" AND  ss.sms_type='"+model.getSms_type()+"' ");
			//sql2.append(" AND  ss.sms_type='"+model.getSms_type()+"' ");
		}
		if(model.getSms_status()!=null && !"".equals(model.getSms_status())){
			sql1.append(" AND  ss.sms_status='"+model.getSms_status()+"' ");
			//sql2.append(" AND  ss.sms_status='"+model.getSms_status()+"' ");
		}
		if(model.getTime1()!=null && !"".equals(model.getTime1())){
			sql1.append(" AND  ss.sms_date>= '"+model.getTime1()+" 00:00:00'");
			//sql2.append(" AND  ss.sms_date>= '"+model.getTime1()+" 00:00:00'");
		}
		if(model.getSms_date_j()!=null && !"".equals(model.getSms_date_j())){
			sql1.append(" AND  ss.sms_date< '"+model.getSms_date_j()+" 23:59:59' ");
			//sql2.append(" AND  ss.sms_date< '"+model.getSms_date_j()+" 23:59:59' ");
		}
		String SENDREPORT_MSG_COMPANY = this.customerInfoService.getCenterconfigByKey("SENDREPORT_MSG_COMPANY", center_num).getConfig_value().trim();
		String IS_PRINT_SMS_TEMPLATE = this.customerInfoService.getCenterconfigByKey("IS_PRINT_SMS_TEMPLATE", center_num).getConfig_value().trim();
		if (IS_PRINT_SMS_TEMPLATE.equals(model.getTemplate_id())){
		sql1.append(" and ss.user_id in(select id from exam_info ei where ei.exam_type='G' and ei.is_Active='Y' "
				+ "union all select ei.id from exam_info ei,batch b where ei.batch_id=b.id and b.report_sms_notice='1' "
				+ "and b.is_Active='Y' and ei.is_Active='Y' and b.center_num='"+center_num+"'"
				+ "union all select ei.id from exam_info ei where ei.company_id in ("+SENDREPORT_MSG_COMPANY+") and ei.is_Active='Y')");
		}
		String sqlserver=sql1.toString();//+"  UNION ALL "+sql2.toString();
		PageSupport map = this.jqm.getList(sqlserver,page,pageSize,CrmSmsSendDTO.class);
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
	public PageReturnDTO queryUser(CrmSmsBaseTemplateModel model, int page,
			int pageSize) throws ServiceException {
		String sql = " SELECT id,chi_name as user_name,phone_num as phone FROM  user_usr  where  1=1 ";
		if(model.getCustname()!=null && !"".equals(model.getCustname())){
			sql +=" AND   chi_name like  '%"+model.getCustname().trim()+"%'  ";
		}
		if(model.getPhone()!=null && !"".equals(model.getPhone())){
			sql +=" AND   phone_num='"+model.getPhone().trim()+"' ";
		}
		PageSupport map = this.jqm.getList(sql,page,pageSize,ExamInfoUserDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(pageSize);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	/**
	 * 
	     * @Title: saveCrmSmsSend   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param crmSmsSend
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CrmSmsSend      
	     * @throws
	 */
	public CrmSmsSend saveCrmSmsSend(CrmSmsSend crmSmsSend)throws ServiceException {
		this.pm.save(crmSmsSend);
		return crmSmsSend;
	}
	
	public String saveCrmSmsSend(CrmSmsBaseTemplateModel model, UserDTO user){
		JSONArray jsonobject = JSONArray.fromObject(model.getLi());
		List<ExamInfo> e = (List<ExamInfo>) JSONArray.toCollection(jsonobject,ExamInfo.class);
		CrmSmsSend s = null;
		UUID uuid = UUID.randomUUID();
		String uid = uuid.toString().replace("-", "");
		 System.out.println(uuid.toString());
		for (ExamInfo examInfo : e) {
			s = new CrmSmsSend();
			if(examInfo.getExam_num()!=null && !"".equals(examInfo.getExam_num())){
				s.setUser_type(1);
			} else {
				s.setUser_type(0);
			}
			s.setTemplate_id(model.getId());
			s.setSend_user(user.getUserid());
			s.setSms_note(model.getSms_note());
			s.setSms_phone(examInfo.getPhone());
			s.setSms_batch(uid);
			s.setSms_type(model.getSms_type());
			s.setUser_id(examInfo.getId());
			if(model.getSms_type()==1){
				s.setSms_date(DateTimeUtil.parse());
				s.setSms_time(DateTimeUtil.parse0(model.getSms_time()));
			}else{
				
				//延迟发送   (不同短信平台 可能不一定有延迟发送参数  )
				s.setSms_date(DateTimeUtil.parse0(model.getSms_time()));
				s.setSms_time(DateTimeUtil.parse0(model.getSms_time()));
			}
			
			
			this.pm.save(s);
		}
		return uid;
	}

	@Override
	public String saveCrmSmsSendCHONGFA(CrmSmsBaseTemplateModel model,
			UserDTO user) throws ServiceException {
		CrmSmsSend crmSmsSend= (CrmSmsSend) this.qm.get( CrmSmsSend.class,model.getId());
		return crmSmsSend.getSms_batch();
	}
}
