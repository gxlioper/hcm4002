package com.hjw.wst.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hjw.webService.client.CUSTOMEDITSendMessage;
import com.hjw.webService.client.CUSTOMSendMessage;
import com.hjw.webService.client.Bean.Custom;
import com.hjw.webService.client.body.FeeResultBody;
import com.hjw.webService.client.body.ResultBody;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.QueryInterfaceResultDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.domain.SampleDemo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.QueryInterfaceResultModel;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DjtCustomerInfoService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.QueryInterfaceResultService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class QueryInterfaceResultServiceImpl implements QueryInterfaceResultService{
	private QueryManager queryManager;
	private JdbcQueryManager jdbcQueryManager;
	private JdbcPersistenceManager jdbcPersistenceManager;
	private PersistenceManager persistenceManager;
	private CustomerInfoService customerInfoService;
	private LisPacsApplicationService lisPacsApplicationService;
	private SyslogService syslogService;
	private CompanyService companyService;
	private WebserviceConfigurationService webserviceConfigurationService;
	private DjtCustomerInfoService djtCustomerInfoService;
	
	
	public DjtCustomerInfoService getDjtCustomerInfoService() {
		return djtCustomerInfoService;
	}

	public void setDjtCustomerInfoService(DjtCustomerInfoService djtCustomerInfoService) {
		this.djtCustomerInfoService = djtCustomerInfoService;
	}

	public CompanyService getCompanyService() {
		return companyService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public WebserviceConfigurationService getWebserviceConfigurationService() {
		return webserviceConfigurationService;
	}

	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public LisPacsApplicationService getLisPacsApplicationService() {
		return lisPacsApplicationService;
	}

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}

	public CustomerInfoService getCustomerInfoService() {
		return customerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

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
	public PageReturnDTO getInterResultList(QueryInterfaceResultModel model, String center_num, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select convert(varchar,ec.charge_item_id) as charge_item_id,ei.exam_type as exam_type,eif.com_name as company_name,ei.exam_num as exam_num,cu.user_name as user_name,ci.item_name as item_name ,ec.is_application as send_flag,dd.dep_name as dep_name "
				+ ",CONVERT(varchar(50),ei.join_date,23) as join_date from examinfo_charging_item ec "
				+ "left join exam_info ei on ec.exam_num=ei.exam_num "
				+ "left join charging_item ci on ec.charge_item_id=ci.id  "
				+ "left join customer_info cu on ei.customer_id=cu.id "
				+ "left join department_dep dd on ci.dep_id=dd.id "
				+ "left join department_vs_center de on ci.dep_id=de.dep_id "
				+ "left join company_info eif on ei.company_id=eif.id ";
		List<String> strlist=new ArrayList<String>();
		List<String> strlist1=new ArrayList<String>();
		if(model.getExam_num()!=null&&model.getExam_num().length()>0){
			strlist.add(" ei.exam_num='"+model.getExam_num()+"' ");
			strlist1.add(" ei.exam_num='"+model.getExam_num()+"' ");
		}
		if(model.getUser_name()!=null&&model.getUser_name().length()>0){
			strlist.add(" cu.user_name like '%"+model.getUser_name()+"%' ");
			strlist1.add(" cu.user_name like '%"+model.getUser_name()+"%' ");
		}
		if(model.getJoin_datestart()!=null&&model.getJoin_datestart().length()>0){
			strlist.add(" ei.join_date>='"+model.getJoin_datestart()+" 00:00:00' ");
			strlist1.add(" ei.join_date>='"+model.getJoin_datestart()+" 00:00:00' ");
		}
		if(model.getJoin_dateend()!=null&&model.getJoin_dateend().length()>0){
			strlist.add(" ei.join_date<='"+model.getJoin_dateend()+" 23:59:59' ");
			strlist.add(" ei.join_date<='"+model.getJoin_dateend()+" 23:59:59' ");
		}
		if(model.getSendflag()!=null&&model.getSendflag().length()>0){
			if(model.getSendflag().equals("0")){
				
			}else if(model.getSendflag().equals("1")){
				strlist.add(" ec.is_application='Y' ");
				strlist1.add(" ei.patient_id is not null and LEN(ei.patient_id)>0 ");
			}else if(model.getSendflag().equals("2")){
				strlist.add("  ec.is_application<>'Y' ");
				strlist1.add(" ei.patient_id is null ");
			}
		}
		if(!model.getCompany_id().equals("0")){
			strlist.add(" ei.company_id='"+model.getCompany_id()+"' ");
			strlist1.add(" ei.company_id='"+model.getCompany_id()+"' ");
		}
		if(strlist!=null&&strlist.size()>0){
			sql+=" where  ci.interface_flag='2' and  ei.is_Active='Y' and ec.pay_status!='M'  and de.center_num ='"+center_num+"'  and ec.center_num = '"+center_num+"' and (ei.is_after_pay='Y' or ec.pay_status IN ('Y', 'R'))  and ec.isActive='Y'  and ec.exam_status !='G' and";
			if(strlist.size()>1){
				sql+=strlist.get(0);
				for(int i =1;i<=strlist.size()-1;i++){
					sql+=" and "+strlist.get(i);
				}
			}else{
				sql+=strlist.get(0);
			}
		}
		CenterConfigurationDTO d=new CenterConfigurationDTO();
		d=customerInfoService.getCenterconfigByKey("IS_GCUSTOM_GH", center_num);
		if(d!=null&&d.getConfig_value().equals("Y")){
			sql+="  union all  select '0' as charge_item_id,ei.exam_type as exam_type,eif.com_name as company_name,ei.exam_num as exam_num,"
					+ "cu.user_name as user_name,'挂号信息' as item_name "
					+ ",ei.patient_id as send_flag,'' as dep_name ,CONVERT(varchar(50),ei.join_date,23) as join_date from exam_info ei "
					+ " left join customer_info cu on ei.customer_id=cu.id "
					+ " left join company_info eif on ei.company_id=eif.id ";
			if(strlist1!=null&&strlist1.size()>0){
				sql+=" where ";
				if(strlist1.size()>1){
					sql+=strlist1.get(0);
					for(int i =1;i<=strlist1.size()-1;i++){
						sql+=" and "+strlist1.get(i);
					}
				}else{
					sql+=strlist1.get(0);
				}
			}
		}
		sql+=" order by join_date desc";
		System.out.println(sql);
		
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,QueryInterfaceResultDTO.class);
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(rows);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	//挂号
	@Override
	public String againApply(QueryInterfaceResultModel model,UserDTO user) throws ServiceException, SQLException {
		String list=model.getList();
		String[] str=new String[]{};
		str=list.split(",");
		for(int i=0;i<str.length;i++){
			String[] strdata=new String[]{};
			strdata=str[i].split("!!");
			if(strdata[1].equals("0")){
				//挂号
				againGuahao(strdata[0],user);
			}else{
				//申请
				againLisPacs(strdata[0],strdata[1],user);
			}
		}
		return "处理完成";
	}
	private String queryExamId(String exam_num){
		String exam_id="";
		String sql="select e.id as exam_id from exam_info e where e.exam_num='"+exam_num+"' ";
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				exam_id=rs.getString("exam_id");
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
		return exam_id;
	}
	private void againLisPacs(String exam_num,String charge_item_id,UserDTO user){
		SysLog sl = new SysLog();
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
		String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value()
				.trim();
		String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value()
				.trim();
			if ("Y".equals(lis) && "N".equals(lis_bang)) {
				this.lisPacsApplicationService.lisSend(exam_num, null, user, false, lis_bang,"N");
				sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("404");// 子功能id 必须填写
				sl.setExplain("发送lis申请：" + exam_num);// 操作说明
				syslogService.saveSysLog(sl);
			}

			if ("Y".equals(pacs)) {
				this.lisPacsApplicationService.pacsSend(exam_num, null, user, false,"N");
				sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("404");// 子功能id 必须填写
				sl.setExplain("发送pacs申请：" + exam_num);// 操作说明
				syslogService.saveSysLog(sl);
			}
	}
	
	public String againGuahao(String exam_num,UserDTO user){
		SysLog sl = new SysLog();
		String result="";
		String exam_id=queryExamId(exam_num);
		RegisterModel model = new RegisterModel();
		if (Integer.valueOf(exam_id) > 0) {
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(exam_num,user);
			model.setPatient_id(eu.getPatient_id());
			if (eu.getFreeze() == 1) {
				result = "error-体检信息已冻结，操作不能继续";
				return result;
			}
		}
		if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_GCUSTOM_GH", user.getCenter_num()).getConfig_value())) {
						ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(exam_num);
						if ("Y".equals(ei.getIs_Active())) {
							ExamInfoUserDTO imp = new ExamInfoUserDTO();
							imp=queryExam(exam_id);
							Custom st = new Custom();
							st.setCenter_num(user.getCenter_num());
							st.setPATIENT_ID(imp.getPatient_id());
							st.setEXAM_NUM(imp.getExam_num());
							st.setNAME(imp.getUser_name());
							st.setNAME_PHONETIC("");
							st.setSEX(imp.getSex());// 男 女
							st.setDATE_OF_BIRTH(imp.getBirthday());
							st.setBIRTH_PLACE("");
							st.setNATION("");
							st.setCITIZENSHIP("");
							st.setID_NO(imp.getId_num());
							st.setIDENTITY("一般人员");
							st.setUNIT_IN_CONTRACT("");
							st.setMAILING_ADDRESS("");
							st.setZIP_CODE("");
							st.setPHONE_NUMBER_BUSINESS("");
							st.setPHONE_NUMBER_HOME("");
							st.setNEXT_OF_KIN("");
							st.setRELATIONSHIP("");
							st.setNEXT_OF_KIN_ADDR("");
							st.setNEXT_OF_KIN_PHONE("");
							st.setNEXT_OF_KIN_ZIP_CODE("");
							st.setOPERATOR(user.getUserid() + "");
							st.setBUSINESS_ZIP_CODE("");
							st.setPHOTO("");
							st.setPATIENT_CLASS("");
							st.setDEGREE("");
							st.setE_NAME("");
							st.setOCCUPATION("");
							st.setNATIVE_PLACE("");
							st.setMAILING_ADDRESS_CODE("");
							st.setMAILING_STREET_CODE("");
							st.setALERGY("");
							st.setMARITAL_STATUS(imp.getIs_marriage());
							st.setNEXT_OF_SEX("");
							st.setVISIT_DEPT(companyService.getDatadis("SQKS","1").get(0).getRemark());
							st.setOPERATORS("");
							st.setCARD_NAME("");
							st.setCARD_NO("1");// 1-非院内卡挂号，院内卡挂号就传卡号
							st.setINVOICE_NO("");
							st.setCLINIC_NO(ei.getClinic_no());
							st.setCLINIC_DATE_SCHEDULED("");
							st.setCHARGE_TYPE("自费");
							st.setAGE(imp.getAge() + "");
							st.setNEXT_OF_BATH("");
							if (StringUtils.isEmpty(imp.getPatient_id()) || StringUtils.isEmpty(imp.getClinic_no())
									|| StringUtils.isEmpty(imp.getVisit_date()) || StringUtils.isEmpty(imp.getVisit_no())) {
								WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
								wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION", user.getCenter_num());
								String web_url = wcd.getConfig_url().trim();
								String web_meth = wcd.getConfig_method().trim();
								CUSTOMSendMessage csm = new CUSTOMSendMessage(st);
								ResultBody fr = new ResultBody();
								fr = csm.customSend(web_url, web_meth, true);
								if ("AA".equals(fr.getResultHeader().getTypeCode())) {
									imp.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
									imp.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
									imp.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
									imp.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
									this.djtCustomerInfoService.getDjtExamInfoOne(user.getUserid(),
											user.getCenter_num(), imp);
									sl = new SysLog();
									sl.setCenter_num(user.getCenter_num());
									sl.setUserid(user.getUserid() + "");
									sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除
															// 4导入 9 其他
									sl.setXtgnid("");// 可不填写
									sl.setXtgnid2("293");// 子功能id 必须填写
									sl.setExplain("登记台人员挂号 " + ei.getExam_num() + " " + ei.getUser_name() + " "
											+ imp.getPatient_id() + " " + imp.getVisit_no() + " " + imp.getVisit_date()
											+ " " + imp.getClinic_no());// 操作说明
									syslogService.saveSysLog(sl);
								}
							} else {
								WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
								wcd = webserviceConfigurationService.getWebServiceConfig("CUSTEDIT_APPLICATION", user.getCenter_num());
								String web_url = wcd.getConfig_url().trim();
								String web_meth = wcd.getConfig_method().trim();
								CUSTOMEDITSendMessage csm = new CUSTOMEDITSendMessage(st);
								FeeResultBody fr = new FeeResultBody();
								fr = csm.customSend(web_url, web_meth, true);
								sl = new SysLog();
								sl.setCenter_num(user.getCenter_num());
								sl.setUserid(user.getUserid() + "");
								sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9
														// 其他
								sl.setXtgnid("");// 可不填写
								sl.setXtgnid2("414");// 子功能id 必须填写
								sl.setExplain("登记台人员修改 " + ei.getExam_num() + " " + ei.getUser_name());// 操作说明
								syslogService.saveSysLog(sl);
							}
						}
					}
		return "处理完成";
				
	}
	private ExamInfoUserDTO queryExam(String exam_id){
		String sql="select ei.patient_id as patient_id,ei.exam_num as exam_num,ei.age,ci.user_name as user_name,"
				+ "ci.sex as sex ,CONVERT(varchar(50),ci.birthday,23)  as birthday,ci.id_num as id_num,"
				+ "ei.is_marriage as is_marriage,ei.clinic_no as clinic_no,ei.visit_date as visit_date,ei.visit_no as visit_no from exam_info ei left join customer_info ci on ei.customer_id=ci.id where ei.id='"+exam_id+"'";
		Connection connection = null;
		Statement statement = null;
		List<ExamInfoUserDTO> impList=new ArrayList<ExamInfoUserDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				ExamInfoUserDTO imptest=new ExamInfoUserDTO();
				imptest.setPatient_id(rs.getString("patient_id"));
				imptest.setExam_num(rs.getString("exam_num"));
				imptest.setAge(rs.getLong("age"));
				imptest.setUser_name(rs.getString("user_name"));
				imptest.setSex(rs.getString("sex"));
				imptest.setBirthday(rs.getString("birthday"));
				imptest.setId_num(rs.getString("id_num"));
				imptest.setIs_marriage(rs.getString("is_marriage"));
				imptest.setClinic_no(rs.getString("clinic_no"));
				imptest.setVisit_date(rs.getString("visit_date"));
				imptest.setVisit_no(rs.getString("visit_no"));
				impList.add(imptest);
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
		return impList.get(0);
	}
	public String againAll(QueryInterfaceResultModel model,UserDTO user) throws ServiceException, SQLException {
		String sql="select convert(varchar,ec.charge_item_id) as charge_item_id,ei.exam_type as exam_type,ei.company as company_name,ei.exam_num as exam_num,cu.user_name as user_name,ci.item_name as item_name ,ec.is_application as send_flag,dd.dep_name as dep_name "
				+ ",CONVERT(varchar(50),ei.join_date,23) as join_date from examinfo_charging_item ec "
				+ "left join exam_info ei on ec.exam_num=ei.exam_num "
				+ "left join charging_item ci on ec.charge_item_id=ci.id "
				+ "left join customer_info cu on ei.customer_id=cu.id "
				+ "left join department_dep dd on ci.dep_id=dd.id ";
		List<String> strlist=new ArrayList<String>();
		List<String> strlist1=new ArrayList<String>();
		if(model.getExam_num()!=null&&model.getExam_num().length()>0){
			strlist.add(" ei.exam_num='"+model.getExam_num()+"' ");
			strlist1.add(" ei.exam_num='"+model.getExam_num()+"' ");
		}
		if(model.getUser_name()!=null&&model.getUser_name().length()>0){
			strlist.add(" cu.user_name like '%"+model.getUser_name()+"%' ");
			strlist1.add(" cu.user_name like '%"+model.getUser_name()+"%' ");
		}
		if(model.getJoin_datestart()!=null&&model.getJoin_datestart().length()>0){
			strlist.add(" ei.join_date>='"+model.getJoin_datestart()+" 00:00:00' ");
			strlist1.add(" ei.join_date>='"+model.getJoin_datestart()+" 00:00:00' ");
		}
		if(model.getJoin_dateend()!=null&&model.getJoin_dateend().length()>0){
			strlist.add(" ei.join_date<'"+model.getJoin_dateend()+" 23:59:59' ");
			strlist1.add(" ei.join_date<'"+model.getJoin_dateend()+" 23:59:59' ");
		}
		if(model.getSendflag()!=null&&model.getSendflag().length()>0){
			if(model.getSendflag().equals("0")){
				
			}else if(model.getSendflag().equals("1")){
				strlist.add(" ec.is_application='Y' and ec.center_num = '"+user.getCenter_num()+"' ");
				strlist1.add(" ei.patient_id is not null and LEN(ei.patient_id)>0 ");
			}else if(model.getSendflag().equals("2")){
				strlist.add("  ec.is_application<>'Y' and ec.center_num = '"+user.getCenter_num()+"' ");
				strlist1.add(" ei.patient_id is null ");
			}
		}
		if(strlist!=null&&strlist.size()>0){
			sql+=" where ";
			if(strlist.size()>1){
				sql+=strlist.get(0);
				for(int i =1;i<=strlist.size()-1;i++){
					sql+=" and "+strlist.get(i);
				}
			}else{
				sql+=strlist.get(0);
			}
		}
		CenterConfigurationDTO d=new CenterConfigurationDTO();
		d=customerInfoService.getCenterconfigByKey("IS_GCUSTOM_GH", user.getCenter_num());
		if(d!=null&&d.getConfig_value().equals("Y")){
			sql+="  union all  select '0' as charge_item_id,ei.exam_type as exam_type,ei.company as company_name,ei.exam_num as exam_num,cu.user_name as user_name,'挂号信息' as item_name "
					+ ",ei.patient_id as send_flag,'' as dep_name ,CONVERT(varchar(50),ei.join_date,23) as join_date from exam_info ei "
					+ " left join customer_info cu on ei.customer_id=cu.id ";
			if(strlist1!=null&&strlist1.size()>0){
				sql+=" where ";
				if(strlist1.size()>1){
					sql+=strlist1.get(0);
					for(int i =1;i<=strlist1.size()-1;i++){
						sql+=" and "+strlist1.get(i);
					}
				}else{
					sql+=strlist1.get(0);
				}
			}
		}
		sql+=" order by join_date desc";
		List<QueryInterfaceResultDTO> list=new ArrayList<QueryInterfaceResultDTO>();
		list=queryQueryInterfaceResultDTOList(sql);
		for(int i=0;i<list.size();i++){
			if(list.get(i).getCharge_item_id().equals("0")){
				//挂号
				againGuahao(list.get(i).getExam_num(),user);
			}else{
				//申请
				againLisPacs(list.get(i).getExam_num(),list.get(i).getCharge_item_id(),user);
			}
		}
		return "处理完成";
	}
	private List<QueryInterfaceResultDTO> queryQueryInterfaceResultDTOList(String sql){
		List<QueryInterfaceResultDTO> list=new ArrayList<QueryInterfaceResultDTO>();
		Connection connection = null;
		Statement statement = null;
		List<ExamInfoUserDTO> impList=new ArrayList<ExamInfoUserDTO>();
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				QueryInterfaceResultDTO dto=new QueryInterfaceResultDTO();
				dto.setCharge_item_id(rs.getString("charge_item_id"));
				dto.setExam_num(rs.getString("exam_num"));
				list.add(dto);
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
		return list;
	}

	@Override
	public List<SampleDemoDTO> getSampleTypeList() {
		String sql="select s.id as id,s.demo_name as demo_name from sample_demo s where demo_category='28'";
		List<SampleDemoDTO> list=new ArrayList<SampleDemoDTO>();
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				SampleDemoDTO demo=new SampleDemoDTO();
				demo.setId(Long.valueOf(rs.getString("id")));
				demo.setDemo_name(rs.getString("demo_name"));
				list.add(demo);
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
		return list;
	}

	@Override
	public PageReturnDTO getSampleResultList(QueryInterfaceResultModel model, String center_num, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select ei.exam_num as exam_num,cio.user_name as user_name,ci.item_name as item_name,"
				+ "se.sample_barcode sample_applyid,sd.demo_name as sample_type,uu.chi_name as sample_creater,"
				+ "CONVERT(varchar(50),se.update_time,20) as sample_createdate,CONVERT(varchar(50),eci.exam_date,20) as sample_reportdate "
				+ "from sample_exam_detail se "
				+ "left join examResult_chargingItem er on se.sample_barcode=er.bar_code "
				+ "left join charging_item ci on er.charging_item_code=ci.item_code "
				+ "left join exam_info ei on se.exam_num =ei.exam_num "
				+ "left join sample_demo sd on se.sample_id=sd.id "
				+ "left join customer_info cio on ei.customer_id=cio.id "
				+ "left join examinfo_charging_item eci on eci.exam_num=ei.exam_num and eci.charging_item_code=ci.item_code "
				+ "left join user_usr uu on se.updater=uu.id   where  1=1";
//		List<String> strlist=new ArrayList<String>();
//		if(model.getExam_num()!=null&&model.getExam_num().length()>0){
//			strlist.add(" ei.exam_num='"+model.getExam_num()+"' ");
//		}
//		if(model.getUser_name()!=null&&model.getUser_name().length()>0){
//			strlist.add(" cio.user_name like '%"+model.getUser_name()+"%' ");
//		}
//		if(model.getSample_datestart()!=null&&model.getSample_datestart().length()>0){
//			strlist.add(" CONVERT(varchar(50),se.update_time,23)>='"+model.getSample_datestart()+"' ");
//		}
//		if(model.getSample_dateend()!=null&&model.getSample_dateend().length()>0){
//			strlist.add(" CONVERT(varchar(50),se.update_time,23)<'"+model.getSample_dateend()+"' ");
//		}
//		if(model.getSample_type_id()!=null&&model.getSample_type_id().length()>0){
//				strlist.add(" sd.id='"+model.getSample_type_id()+"' ");
//			
//		}
//		if(strlist!=null&&strlist.size()>0){
//			sql+=" where ";
//			if(strlist.size()>1){
//				sql+=strlist.get(0);
//				for(int i =1;i<=strlist.size()-1;i++){
//					sql+=" and "+strlist.get(i);
//				}
//			}else{
//				sql+=strlist.get(0);
//			}
//		}
		
		if(model.getExam_num()!=null&&model.getExam_num().length()>0){
			sql += "  and  ei.exam_num='"+model.getExam_num()+"' ";
		}
		if(model.getUser_name()!=null&&model.getUser_name().length()>0){
			sql +="  and  cio.user_name like '%"+model.getUser_name()+"%' ";
		}
		if(model.getSample_datestart()!=null&&model.getSample_datestart().length()>0){
			sql +=" and se.update_time >'"+model.getSample_datestart()+" 00:00:00' ";
		}
		if(model.getSample_dateend()!=null&&model.getSample_dateend().length()>0){
			sql +="  and   se.update_time<'"+model.getSample_dateend()+" 23:59:59' ";
		}
		if(model.getSample_type_id()!=null&&model.getSample_type_id().length()>0){
			sql +="  and sd.id='"+model.getSample_type_id()+"'  ";
			
		}
		sql+=" and se.status='Y' and eci.center_num = '"+center_num+"' order by sample_createdate desc";
		System.out.println(sql);
		
		PageSupport map = jdbcQueryManager.getList(sql,page,rows,QueryInterfaceResultDTO.class);
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(page);
		webrole.setRp(rows);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		// getSampleResultListShuliang(model,page,rows);
		return webrole;
	}

	public String getSampleResultListShuliang(QueryInterfaceResultModel model, String center_num, int page, int rows)
			throws ServiceException, SQLException {
		String sql="select ei.exam_num,cio.user_name as user_name,ci.item_name as item_name,"
				+ "se.sample_barcode sample_applyid,sd.demo_name as sample_type,uu.chi_name as sample_creater,"
				+ "CONVERT(varchar(50),se.update_time,20) as sample_createdate,CONVERT(varchar(50),eci.exam_date,20) as sample_reportdate "
				+ "from sample_exam_detail se "
				+ "left join examResult_chargingItem er on se.sample_barcode=er.bar_code "
				+ "left join charging_item ci on er.charging_item_code=ci.item_code "
				+ "left join exam_info ei on se.exam_num=ei.exam_num "
				+ "left join sample_demo sd on se.sample_id=sd.id "
				+ "left join customer_info cio on ei.customer_id=cio.id "
				+ "left join examinfo_charging_item eci on eci.exam_num=ei.exam_num and eci.charging_item_code=ci.item_code "
				+ "left join user_usr uu on se.updater=uu.id   where  1=1";
		
				if(model.getExam_num()!=null&&model.getExam_num().length()>0){
					sql += "  and  ei.exam_num='"+model.getExam_num()+"' ";
				}
				if(model.getUser_name()!=null&&model.getUser_name().length()>0){
					sql +="  and  cio.user_name like '%"+model.getUser_name()+"%' ";
				}
				if(model.getSample_datestart()!=null&&model.getSample_datestart().length()>0){
					sql +=" and se.update_time >'"+model.getSample_datestart()+" 00:00:00' ";
				}
				if(model.getSample_dateend()!=null&&model.getSample_dateend().length()>0){
					sql +="  and   se.update_time<'"+model.getSample_dateend()+" 23:59:59' ";
				}
				if(model.getSample_type_id()!=null&&model.getSample_type_id().length()>0){
					sql +="  and sd.id='"+model.getSample_type_id()+"'  ";
					
				}
				sql+="  and se.status='Y' and eci.center_num = '"+center_num+"' ";
				System.out.println(sql);
				//总人数
				List<QueryInterfaceResultDTO> dt = jdbcQueryManager.getList(sql,QueryInterfaceResultDTO.class);
				  Map<String,QueryInterfaceResultDTO> map = new HashMap<String,QueryInterfaceResultDTO>();
				  for (int o = 0; o < dt.size(); o++) {
					 map.put(dt.get(o).getExam_num(), dt.get(o));
				  }
				  System.out.println("总人数"+map.size());
				
				  
				  
					     sql="select ei.exam_num,cio.user_name as user_name,ci.item_name as item_name,"
							+ "se.sample_barcode sample_applyid,sd.demo_name as sample_type,uu.chi_name as sample_creater,"
							+ "CONVERT(varchar(50),se.update_time,20) as sample_createdate,CONVERT(varchar(50),eci.exam_date,20) as sample_reportdate "
							+ "from sample_exam_detail se "
							+ "left join examResult_chargingItem er on se.sample_barcode=er.bar_code "
							+ "left join charging_item ci on er.charging_item_code=ci.item_code "
							+ "left join exam_info ei on se.exam_num=ei.exam_num "
							+ "left join sample_demo sd on se.sample_id=sd.id "
							+ "left join customer_info cio on ei.customer_id=cio.id "
							+ "left join examinfo_charging_item eci on eci.exam_num=ei.exam_num and eci.charging_item_code=ci.item_code "
							+ "left join user_usr uu on se.updater=uu.id   where  1=1"
					        + "   and  sd.demo_category = ( "
					        + " SELECT id FROM   data_dictionary  WHERE  data_code = 'YBLX'  AND   data_code_children = 'YBLX_XY')";
							if(model.getExam_num()!=null&&model.getExam_num().length()>0){
								sql += "  and  ei.exam_num='"+model.getExam_num()+"' ";
							}
							if(model.getUser_name()!=null&&model.getUser_name().length()>0){
								sql +="  and  cio.user_name like '%"+model.getUser_name()+"%' ";
							}
							if(model.getSample_datestart()!=null&&model.getSample_datestart().length()>0){
								sql +=" and se.update_time >'"+model.getSample_datestart()+" 00:00:00' ";
							}
							if(model.getSample_dateend()!=null&&model.getSample_dateend().length()>0){
								sql +="  and   se.update_time<'"+model.getSample_dateend()+" 23:59:59' ";
							}
							if(model.getSample_type_id()!=null&&model.getSample_type_id().length()>0){
								sql +="  and sd.id='"+model.getSample_type_id()+"'  ";
								
							}
							sql+="  and se.status='Y' and eci.center_num = '"+center_num+"' ";
							List<QueryInterfaceResultDTO> tt = jdbcQueryManager.getList(sql,QueryInterfaceResultDTO.class);
							  Map<String,QueryInterfaceResultDTO> map2 = new HashMap<String,QueryInterfaceResultDTO>();
							  for (int o = 0; o < tt.size(); o++) {
								 map2.put(tt.get(o).getExam_num(), tt.get(o));
							  }
							  System.out.println("血样管数："+tt.size());
							  System.out.println("血样人数"+map.size());
							  
							  
							  
								    sql="select ei.exam_num,cio.user_name as user_name,ci.item_name as item_name,"
										+ "se.sample_barcode sample_applyid,sd.demo_name as sample_type,uu.chi_name as sample_creater,"
										+ "CONVERT(varchar(50),se.update_time,20) as sample_createdate,CONVERT(varchar(50),eci.exam_date,20) as sample_reportdate "
										+ "from sample_exam_detail se "
										+ "left join examResult_chargingItem er on on se.sample_barcode=er.bar_code "
										+ "left join charging_item ci on er.charging_item_code=ci.item_code "
										+ "left join exam_info ei on se.exam_num=ei.exam_num "
										+ "left join sample_demo sd on se.sample_id=sd.id "
										+ "left join customer_info cio on ei.customer_id=cio.id "
										+ "left join examinfo_charging_item eci on eci.exam_num=ei.exam_num and eci.charging_item_code=ci.item_code "
										+ "left join user_usr uu on se.updater=uu.id   where  1=1"
										+ "  AND   se.status = 'Y'   ";
								
										if(model.getExam_num()!=null&&model.getExam_num().length()>0){
											sql += "  and  ei.exam_num='"+model.getExam_num()+"' ";
										}
										if(model.getUser_name()!=null&&model.getUser_name().length()>0){
											sql +="  and  cio.user_name like '%"+model.getUser_name()+"%' ";
										}
										if(model.getSample_datestart()!=null&&model.getSample_datestart().length()>0){
											sql +=" and se.update_time >'"+model.getSample_datestart()+" 00:00:00' ";
										}
										if(model.getSample_dateend()!=null&&model.getSample_dateend().length()>0){
											sql +="  and   se.update_time<'"+model.getSample_dateend()+" 23:59:59' ";
										}
										if(model.getSample_type_id()!=null&&model.getSample_type_id().length()>0){
											sql +="  and sd.id='"+model.getSample_type_id()+"'  ";
											
										}
										sql+="  and se.status='Y' and eci.center_num = '"+center_num+"' ";
										System.out.println(sql);
										//已采样总管数
										List<QueryInterfaceResultDTO> dtt = jdbcQueryManager.getList(sql,QueryInterfaceResultDTO.class);
										System.out.println("已采样总管数"+dtt.size()); 
				return "{zrs:'"+map.size()+"',yc:'"+dtt.size()+"',xygs:'"+tt.size()+"',xyrs:'"+map.size()+"'}";
	}

	public String queryCenter(String userid) throws ServiceException, SQLException {
		String sql="select exam_center_id as exam_center_id  from user_usr where id='"+userid+"'";
		List<String> list=new ArrayList<String>();
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jdbcQueryManager.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			System.out.println(sql);
			while (rs.next()) {
				String cen=rs.getString("exam_center_id");
				list.add(cen);
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
		return list.get(0);
	}

	

}
