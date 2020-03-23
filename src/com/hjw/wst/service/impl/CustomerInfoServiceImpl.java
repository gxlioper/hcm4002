package com.hjw.wst.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.sql.RowSet;

import org.apache.log4j.Logger;
import org.codehaus.xfire.client.Client;
import org.codehaus.xfire.transport.http.CommonsHttpMessageSender;
import org.hsqldb.lib.StringUtil;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hjw.ireport.DTO.Djdbardode;
import com.hjw.ireport.DTO.Djdbean;
import com.hjw.ireport.DTO.DjdbeanList;
import com.hjw.ireport.DTO.Djdqrcode;
import com.hjw.ireport.DTO.Djduser;
import com.hjw.util.CommSet;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.Timeutils;
import com.hjw.util.TranLogTxt;
import com.hjw.util.ValidateUtil;
import com.hjw.webService.client.DAHSendMessage;
import com.hjw.webService.client.Bean.DAHCustomerBean;
import com.hjw.webService.client.body.DAHResBody;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ChargingItemConsumablesItemDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CopyItemDTO;
import com.hjw.wst.DTO.CustmTimeCountDTO;
import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.CustomerJsonDTO;
import com.hjw.wst.DTO.CustomerTypeDTO;
import com.hjw.wst.DTO.CustomerVisitCardDTO;
import com.hjw.wst.DTO.DJTItemDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.DiversInfoDTO;
import com.hjw.wst.DTO.ECIAmountDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoOrderDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.ImpCustomerInfoDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.LisUserDTO;
import com.hjw.wst.DTO.PacsCountDTO;
import com.hjw.wst.DTO.PacsSummaryDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.UserUseDTO;

import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerVisitCard;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoSet;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.domain.Introducer;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.domain.SetChargingItem;
import com.hjw.wst.domain.TeamAccountItemList;
import com.hjw.wst.domain.WebSynchro;
import com.hjw.wst.model.ExamInfoModel;
import com.hjw.wst.model.ImpCustomerInfoModel;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.DjtCustomerInfoService;
import com.hjw.wst.service.WebConfigService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybOccuindustryDTO;
import com.hjw.zyb.DTO.ZybOccuphyexaClassDTO;
import com.hjw.zyb.domain.ExamInfoExt;
import com.hjw.zyb.domain.Impoccuhis;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.hjw.zyb.service.ZybOccuphyexaClassService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

import net.sf.json.JSONObject;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: yangm
 * @date: 2016年7月4日 上午9:37:08
 * @version V2.0.0.0
 */
public class CustomerInfoServiceImpl implements CustomerInfoService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private CompanyService companyService;
	private BatchService batchService;
	private WebConfigService webConfigService;
	private DepartmentService departmentService;
    private WebserviceConfigurationService webserviceConfigurationService;   
    private CustomerInfoService  customerInfoService;
    private ZybOccuphyexaClassService zybOccuphyexaClassService;
    private ZybCustomerInfoService   zybCustomerInfoService;
    
    private DjtCustomerInfoService djtCustomerInfoService;
    private ChargingItemService chargingItemService;
    
    
    
	public ChargingItemService getChargingItemService() {
		return chargingItemService;
	}

	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}

	public DjtCustomerInfoService getDjtCustomerInfoService() {
		return djtCustomerInfoService;
	}

	public void setDjtCustomerInfoService(DjtCustomerInfoService djtCustomerInfoService) {
		this.djtCustomerInfoService = djtCustomerInfoService;
	}

	public void setZybCustomerInfoService(
			ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	Logger logger = Logger.getLogger(this.getClass());

	public void setWebConfigService(WebConfigService webConfigService) {
		this.webConfigService = webConfigService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setZybOccuphyexaClassService(ZybOccuphyexaClassService zybOccuphyexaClassService) {
		this.zybOccuphyexaClassService = zybOccuphyexaClassService;
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
	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	
	/**
	 * 
	     * @Title: getExamInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfoForId(long id) throws ServiceException {
		return (ExamInfo) qm.load(ExamInfo.class, id);
	}
	public ExamInfo getExamInfoForExamNum(String exam_num) throws ServiceException {
		List<ExamInfo> li = this.qm.find("FROM ExamInfo WHERE exam_num='"+exam_num+"'");
		if(li!=null && li.size()>0) {
			return li.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	     * @Title: getExamInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo SaveExamInfo(ExamInfo ei) throws ServiceException{
		this.pm.save(ei);
		return ei;
	}
	
	/**
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo updateExamInfo(ExamInfo ei) throws ServiceException{
		this.pm.update(ei);
		return ei;
	}

	/**
	 * 
	 * @Title: getExamInfoForNameList @Description: 通过姓名获取人员 @param: @param
	 * examname @param: @param pagesize @param: @param
	 * pageno @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO getExamInfoForNameList(String examname, int pagesize, int pageno) throws ServiceException {
		String sql = "select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,a.order_id,b.user_name,b.sex,b.arch_num,b.id_num "
				+ " From  exam_info a,customer_info b where a.customer_id=b.id and b.user_name like '%" + examname
				+ "%' " + "order by a.join_date desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ExamInfoDTO.class);
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
	     * @Title: getExamInfoForNameIdmunExamNumList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_Num
	     * @param: @param id_num
	     * @param: @param cusrnum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getExamInfoForNameIdmunExamNumList(String exam_Num,String id_num,String cusrnum,long com_id,String arch_num,String sex,String tel, int pagesize, int pageno) throws ServiceException {
		StringBuffer sb = new StringBuffer("select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,a.order_id,b.user_name,b.sex,b.arch_num,b.id_num "
				+ " From  exam_info a,customer_info b where a.customer_id=b.id and a.is_Active='Y' ");
		       if (!StringUtil.isEmpty(exam_Num)) {
					sb.append(" and a.exam_num = '" + exam_Num + "' ");
				}
		       
		       if (com_id>0) {
					sb.append(" and a.company_id = " + com_id + " ");
				}
		       
		       if (!StringUtil.isEmpty(arch_num)) {
					sb.append(" and b.arch_num = '" + arch_num + "' ");
				}
		       
		       if (!StringUtil.isEmpty(sex)) {
					sb.append(" and b.sex = '" + sex + "' ");
				}
		       
		       if (!StringUtil.isEmpty(tel)) {
					sb.append(" and a.phone = '" + tel + "' ");
				}

				if (!StringUtil.isEmpty(cusrnum)) {
					sb.append(" and b.user_name like '%" + cusrnum + "%' ");
				}

				if (!StringUtil.isEmpty(id_num)) {
					sb.append(" and b.id_num like '%" + id_num + "%' ");
				}
				sb.append( "order by a.join_date desc");
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoDTO.class);
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
	     * @Title: getExamInfoForNameIdmunExamNumList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_Num
	     * @param: @param id_num
	     * @param: @param cusrnum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public String getCountExamInfoForNameIdmunExamNumList(String exam_Num,String id_num,String cusrnum) throws ServiceException {
		StringBuffer sb = new StringBuffer("select a.id,a.exam_num From  exam_info a,customer_info b where a.customer_id=b.id  and a.is_Active='Y' ");
		       if (!StringUtil.isEmpty(exam_Num)) {
					sb.append(" and a.exam_num = '" + exam_Num + "' ");
				}

				if (!StringUtil.isEmpty(cusrnum)) {
					sb.append(" and b.user_name like '%" + cusrnum + "%' ");
				}

				if (!StringUtil.isEmpty(id_num)) {
					sb.append(" and b.id_num like '%" + id_num + "%' ");
				}
				Connection connection = null;
				Statement statement = null;
				int flags=0;
				String exam_num="查询失败";
				try {
					connection = this.jqm.getConnection();
					statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(sb.toString());			
					while (rs.next()) {
						exam_num=rs.getLong("exam_num")+"";
						flags=flags+1;
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
				return flags+"-"+exam_num;
	}

	/**
	 * 
	     * @Title: getCountExamInfoForNameIdmunList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getCountExamInfoForNameIdmunList(String id_num) throws ServiceException {
		StringBuffer sb = new StringBuffer("select id From  customer_info where id_num = '" + id_num + "' and is_Active='Y' order by create_time,update_time desc ");
				Connection connection = null;
				Statement statement = null;
				int flags=0;
				String customer_id="查询失败";
				try {
					connection = this.jqm.getConnection();
					statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(sb.toString());			
					if (rs.next()) {
						customer_id=rs.getLong("id")+"";
						flags=flags+1;
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
				return flags+"-"+customer_id;
	}

	/**
	 * 
	 * @Title:
	 * getExamInfoForarchnumList @Description:通过arch_num获取人员基本信息 @param: @param
	 * arch_num @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public ExamInfoDTO getCustExamInfoForexamId(String exam_num) throws ServiceException {
		String sql = "select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.employeeID,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,"
				+ "a.order_id,b.user_name,b.sex,b.arch_num,b.id_num,b.birthday "
				+ " From  exam_info a,customer_info b where a.customer_id=b.id and a.exam_num ='" + exam_num + "' "
				+ "order by b.create_time desc";

		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}

		return webrole;
	}
	
	
	
	/**
	 * 
	     * @Title: getExamInfoForexamNumD   
	     * @Description: 获取需要删除报到体检信息 
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfoDTO>      
	     * @throws
	 */
	public ExamInfoDTO getExamInfoForexamNumD(String examNum,String centernum) throws ServiceException{
		String sql = "select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,"
				+ "a.order_id,b.user_name,b.sex,b.arch_num,b.id_num,a.freeze "
				+ " From  exam_info a,customer_info b where a.customer_id=b.id and status='D' and a.is_Active='Y' and a.center_num='"+centernum+"' ";
			sql=sql+" and a.exam_num ='" + examNum + "' ";
        sql=sql+" order by b.create_time desc ";
		
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO)map.getList().get(0);
		}
		return webrole;
	}
	
	/**
	 * 
	 * @Title:只查询是预约状态的人员，然后进行登记
	 * getExamInfoForarchnumList @Description:通过arch_num获取人员基本信息 @param: @param
	 * arch_num @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public List<ExamInfoDTO> getExamInfoForexamNum(String examNum,String idnum,String centernum) throws ServiceException {
		String sql = "select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,"
				+ "a.order_id,a.batch_id,b.user_name,b.sex,b.arch_num,b.id_num,a.freeze "
				+ " From  exam_info a,customer_info b where a.customer_id=b.id and status='Y' and a.center_num='"+centernum+"' and a.is_Active='Y' ";

		if ((examNum!=null)&&(examNum.trim().length()>0)){
			sql=sql+" and a.exam_num ='" + examNum + "' ";
		}
		
        if ((idnum!=null)&&(idnum.trim().length()>0)){
        	sql=sql+" and b.id_num ='" + idnum + "' ";
		}
        
        sql=sql+" order by a.updater desc,a.create_time desc ";
		System.out.println("-------------"+sql);
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		List<ExamInfoDTO> webrole = new ArrayList<ExamInfoDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = map.getList();
		}

		return webrole;
	}

	/**
	 * 
	     * @Title: getPacsSummaryDTOforExamid   
	     * @Description: 获取pacs申请主表信息   
	     * @param: @param examinfo_num
	     * @param: @param status
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PacsSummaryDTO      
	     * @throws
	 */
	public PacsSummary getPacsSummaryDTOforExamid(String examinfo_num,long sam_id,String status) throws ServiceException{
		String hql = " from PacsSummary g where g.examinfo_num='"+examinfo_num+"' and g.exam_status='"+status+"' and g.examinfo_sampleId="+sam_id+"";
		List<PacsSummary> list = qm.find(hql);
		if (list != null && list.size() > 0){
			return list.get(0);		
		}else{
			return null;
		}	
	}
	/**
	 * 
	     * @Title: getSampleExamDetailforExamid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetailDTO      
	     * @throws
	 */
	public SampleExamDetail getSampleExamDetailforExamid(String  exam_num,long samp_id,String status) throws ServiceException{
		String hql = " from SampleExamDetail g where g.exam_num='"+exam_num+"' and g.status='"+status+"' and g.sample_id="+samp_id+"";
		List<SampleExamDetail> list = qm.find(hql);
		if (list != null && list.size() > 0){
			return list.get(0);		
		}else{
			return null;
		}		
	}
	
	/**
	 * 
	     * @Title: getSampleExamDetailforCharingId   
	     * @Description: 通过收费编码获取SampleExamDetail  
	     * @param: @param charingid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetailDTO      
	     * @throws
	 */
	public List<SampleExamDetailDTO> getSampleExamDetailforCharingId(String exam_num,String charingCode) throws ServiceException{
		
		String sql = "select a.id,a.status,a.sample_barcode from sample_exam_detail a,charging_item b, examResult_chargingItem er "
				+ " where a.sample_id=b.sam_demo_id and a.sample_barcode = er.bar_code and er.charging_item_code = b.item_code and b.item_code='"+charingCode+"'  and a.status='W' and a.exam_num='"+exam_num+"'";
		PageSupport map = this.jqm.getList(sql, 1, 1000, SampleExamDetailDTO.class);
		List<SampleExamDetailDTO> webrole = new ArrayList<SampleExamDetailDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = map.getList();
		}
		return webrole;
	}
	
	
	/**
	 * 
	     * @Title: getPacsSummaryforexamNum   
	     * @Description: 体检编号获取pscs信息   
	     * @param: @param examNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<PacsSummaryDTO>      
	     * @throws
	 */
	public List<PacsSummaryDTO> getPacsSummaryforexamNum(String examNum,String chargItemCode) throws ServiceException{
		String sql = "select a.id,a.exam_status,a.pacs_req_code from pacs_summary a,charging_item b "
				+ " where a.examinfo_sampleId=b.sam_demo_id and a.examinfo_num='"+examNum+"' and b.item_code='"+chargItemCode+"'";
		PageSupport map = this.jqm.getList(sql, 1, 1000, PacsSummaryDTO.class);
		List<PacsSummaryDTO> webrole = new ArrayList<PacsSummaryDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = map.getList();
		}
		return webrole;
	}
	/**
	 * 
	 * @Title: checkIn_num @Description: 判断重复的证件号码 @param: @param
	 * comid @param: @param batchid @param: @param userid @param: @throws
	 * ServiceException @return: void @throws
	 */
	public void checkIn_num(long comid, long batchid, long userid) throws ServiceException {
		StringBuffer sql = new StringBuffer("update impcustomerInfo  set flags=1,notices='身份证重复'," + "create_time='"
				+ Timeutils.getNowDate() + "' where id in(" + "select b.id from impcustomerInfo b,"
				+ "(select a.id_num,COUNT(*) as counts from impcustomerInfo a " + "where a.company_id=" + comid
				+ " and a.batch_id=" + batchid + " " + "and a.creater=" + userid
				+ " and a.id_num<>'' and a.id_num is not null group by a.id_num) c "
				+ "where b.id_num=c.id_num and c.counts>1)");
		Connection connection = null;

		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql.toString());
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
	}

	/**
	 * 
	 * @Title: checkArch_num @Description: 判断档案号重复 @param: @param
	 * comid @param: @param batchid @param: @param userid @param: @throws
	 * ServiceException @return: void @throws
	 */
	public void checkArch_num(long comid, long batchid, long userid) throws ServiceException {
		StringBuffer sql = new StringBuffer("update impcustomerInfo  set flags=1,notices='档案号重复'," + "create_time='"
				+ Timeutils.getNowDate() + "' where id in(" + "select b.id from impcustomerInfo b,"
				+ "(select a.arch_num,COUNT(*) as counts from impcustomerInfo a " + "where a.company_id=" + comid
				+ " and a.batch_id=" + batchid + " " + "and a.creater=" + userid
				+ " and a.arch_num<>'' and a.arch_num is not null group by a.arch_num) c "
				+ "where b.arch_num=c.arch_num and c.counts>1)");
		Connection connection = null;

		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql.toString());
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
	}
	
	public void checkVisit_no(long comid,long batchid,long userid) throws ServiceException{
		StringBuffer sql = new StringBuffer("update impcustomerInfo  set flags=1,notices='就诊卡号重复'," + "create_time='"
				+ Timeutils.getNowDate() + "' where id in(" + "select b.id from impcustomerInfo b,"
				+ "(select a.visit_no,COUNT(*) as counts from impcustomerInfo a " + "where a.company_id=" + comid
				+ " and a.batch_id=" + batchid + " " + "and a.creater=" + userid
				+ " and a.visit_no<>'' and a.visit_no is not null group by a.visit_no) c "
				+ "where b.visit_no=c.visit_no and c.counts>1)");
		Connection connection = null;

		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql.toString());
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
	}

	/**
	 * 
	 * @Title: getImpCustomerInfoList @Description: 获取临时表导入的内容 @param: @param
	 * group_id @param: @param com_id @param: @param pagesize @param: @param
	 * pageno @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO getImpCustomerInfoList(long batch_id, long com_id, long userid, InfoType infoType, int pagesize, int pageno)
			throws ServiceException {
		String sql = "select id,company_id,batch_id,contract_id,arch_num,id_num,sex,"
				+ "birthday,custname,age,is_marriage,position,_level,tel,remark,"
				+ "customer_type,others,flags,notices,creater,create_time,exam_type,employeeID,visit_no,introducer,idtypename,"
				+ "sets,zywhys,joinDatetime,employeeage,damage,occusector,occutypeofwork"
				+ ",info_type,born_address,nation,address,zip,degreeOfedu,sc_class,exam_indicator,exam_class" 
				+ " From  impcustomerInfo where batch_id="
				+ batch_id + " and company_id=" + com_id + " and creater=" + userid + " and info_type="
				+ infoType.getCode() + " order by create_time desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ImpCustomerInfoDTO.class);
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
	     * @Title: getExamSetforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamSetDTO>      
	     * @throws
	 */
//	public List<ExamSetDTO> getExamSetforExamId(long examid,String center_num)throws ServiceException {
//		String sql = "select a.id,a.company_id,a.set_num,a.set_name,a.set_pinyin,a.sex,"
//				+ "a.set_discount,a.set_amount,"
//				+ "a.survey_minScore,a.survey_maxScore,a.disease_name,"
//				+ "a.is_Active,a.creater,a.create_time,"
//				+ "a.updater,a.update_time from exam_set a,examinfo_set b where "
//				+ " a.id=b.exam_set_id and a.is_Active='Y' and b.isActive='Y' and a.center_num='"+center_num+"' "
//				+ "and b.examinfo_id="+examid;
//		PageSupport map = this.jqm.getList(sql, 1, 10000, ExamSetDTO.class);
//		List<ExamSetDTO> es = new ArrayList<ExamSetDTO>();
//		if ((map != null) && (map.getList() != null)) {
//			es=map.getList();
//		}
//		return es;
//	}

	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemforExamId(String exam_num,String apptype, String center_num)throws ServiceException {
		String sql = "select a.introducer,uu.chi_name as introducer_name,a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,a.updater,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.app_type,a.itemnum,b.item_type,b.item_discount "
				+ "from examinfo_charging_item a left join user_usr uu   on uu.id= a.introducer ,charging_item b,department_dep c  "
				+ "where a.charging_item_code=b.item_code and a.isActive='Y' and b.dep_id=c.id " //and a.app_type='"+apptype+"'
				+ "and a.pay_status<>'M' and a.exam_num ='"+exam_num+"' and a.center_num = '"+center_num+"' order by b.item_seq,a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> es = new ArrayList<ExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size()>0)) {
			for(int i=0;i<map.getList().size();i++){
				ExaminfoChargingItemDTO eci = new ExaminfoChargingItemDTO();
				eci=(ExaminfoChargingItemDTO)map.getList().get(i);
				if("G".equals(eci.getExam_indicator())){
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					}else if("N".equals(eci.getPay_status())){
						eci.setPay_statuss("未付费");
					}
				} else if ("T".equals(eci.getExam_indicator())) {
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					} else if("R".equals(eci.getPay_status())){
						eci.setPay_statuss("预付费");
					}
				} else if ("M".equals(eci.getExam_indicator())) {
					eci.setPay_statuss("免费");
				} else if ("GT".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					}else if("N".equals(eci.getPay_status())){
						eci.setPay_statuss("未付费");
					}				
				}
				es.add(eci);	
			}
			
		}
		return es;
	}
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamIdIds   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemforExamIdIds(String examnum,String ids, String center_num)throws ServiceException {
		String[] idsss=ids.split(",");
		String mids = "";
		for(int i=0;i<idsss.length;i++){
			mids=mids+",'"+idsss[i]+"'";
		}
		if(mids.length()>0) mids=mids.substring(1, mids.length());
		String sql = "select a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,a.updater,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name "
				+ "from examinfo_charging_item a,charging_item b,department_dep c  "
				+ "where a.charge_item_id=b.id and a.isActive='Y' and b.dep_id=c.id "
				+ " and a.pay_status<>'M' and a.exam_status<>'G' and a.exam_num ='"+examnum+"' and a.charging_item_code in ("+mids+")  and a.center_num = '"+center_num+"' order by a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> es = new ArrayList<ExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size()>0)) {
			es=map.getList();
		}
		return es;
	}
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemforExamIds(String examnums, String center_num)throws ServiceException {
		String sql = "select b.id,b.dep_id,b.item_code,b.item_name,c.dep_name "
				+ "from charging_item b,department_dep c where b.dep_id=c.id and b.id in ("
				+ "select  m.id from (select b.id from examinfo_charging_item a,charging_item b "
				+ "where a.charge_item_id=b.id and a.isActive='Y'  and a.pay_status<>'M' and a.exam_status<>'G' and b.isActive='Y' "
				+ " and a.exam_num in('"+examnums.replaceAll(",", "','")+"') and a.center_num = '"+center_num+"' group by b.id ) m) order by b.id";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> es = new ArrayList<ExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size()>0)) {
			es=map.getList();
		}
		return es;
	}
	/**
	 * 
	 * @Title: saveImpCustomerInfoForList @Description: 批量保存
	 * ImpCustomerInfo @param: @param listDTO @param: @throws
	 * ServiceException @return: void @throws
	 */
	public void saveImpCustomerInfoForList(List<ImpCustomerInfo> listDTO) throws ServiceException {
		for (ImpCustomerInfo ii : listDTO) {
			this.pm.save(ii);
		}
	}

	/**
	 * 
	 * @Title: updateImpCustomerInfo @Description: @param: @param
	 * ii @param: @throws ServiceException @return: void @throws
	 */
	public void updateImpCustomerInfo(ImpCustomerInfo ii) throws ServiceException {
		this.pm.update(ii);
	}

	/**
	 * 
	 * @Title: delImpCustomerInfo @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param ii @param: @throws
	 * ServiceException @return: void @throws
	 */
	public void delImpCustomerInfo(ImpCustomerInfo ii) throws ServiceException {
		this.pm.remove(ii);
	}

	/**
	 * 
	 * @Title: loadCustomerInfo @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param id @param: @return @param: @throws
	 * ServiceException @return: ImpCustomerInfo @throws
	 */
	public ImpCustomerInfo loadImpCustomerInfo(long id) throws ServiceException {
		return (ImpCustomerInfo) this.qm.get(ImpCustomerInfo.class, id);
	}

	/**
	 * 
	 * @Title: getExamInfoForFlagList @Description: 获取所有待导入人员信息 @param: @param
	 * comid @param: @param batchid @param: @param
	 * userid @param: @return @param: @throws ServiceException @return:
	 * List<ImpCustomerInfoDTO> @throws
	 */
	public void getExamInfoForFlagList(long comids, long batchids, long userid,String apptype,String center_num) throws ServiceException {
		String sql = "select id,company_id,batch_id,contract_id,arch_num,id_num,sex,"
				+ "birthday,custname,age,is_marriage,exam_type,position,_level,tel,remark,"
				+ "customer_type,others,flags,notices,creater,create_time,employeeID,visit_no,introducer,idtypename"
				+ ",sets,zywhys,joinDatetime,employeeage,damage,occusector,occutypeofwork"
				+ ",info_type,born_address,nation,address,zip,degreeOfedu,sc_class,exam_indicator,exam_class,employeemonth,dammonth " 
				+" From  impcustomerInfo where batch_id="
				+ batchids + " and company_id=" + comids + " and creater=" + userid
				+ " and flags=0 order by create_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 50000, ImpCustomerInfoDTO.class);
		List<ImpCustomerInfoDTO> list = new ArrayList<ImpCustomerInfoDTO>();
		if ((map != null) && (map.getList() != null)) {
			list = map.getList();
			getExamInfoForFlagList(list, userid,batchids,comids,true,apptype, center_num);
		}
	}

	/**
	 * 
	     * <p>Title: getExamInfoOne</p>   
	     * <p>Description: 单独体检人员信息录入</p>   
	     * @param comids
	     * @param batchids
	     * @param userid
	     * @param centerid
	     * @param eu
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getExamInfoOne(long, long, long, java.lang.String, com.hjw.wst.DTO.ExamInfoUserDTO)
	 */
	public String getExamInfoOne(long comids, long batchids, long userid,String centernum,ExamInfoUserDTO eu)throws ServiceException {
		String message = "error-保存失败";
		message = getExamInfoForFlagList(eu,userid,centernum,batchids,comids);
		if (message.indexOf("ok") < 0) {
			message = "error-" + message;
		}
		return message;
	}

	/**
	 * 
	 * @Title: getExamInfoOne @Description:单独体检人员信息录入 @param: @param
	 * comids @param: @param batchids @param: @param userid @param: @param
	 * impCustomerInfoDTO @param: @throws ServiceException @return: void @throws
	 */
//	public String getExamInfoOne(long comids, long batchids, long userid,ImpCustomerInfoDTO impCustomerInfoDTO,String center_num)
//			throws ServiceException {
//		List<ImpCustomerInfoDTO> list = new ArrayList<ImpCustomerInfoDTO>();
//		list.add(impCustomerInfoDTO);
//		String message = "error-保存失败";
//		message = getExamInfoForFlagList(list, userid,batchids, comids,false,null,center_num);
//		if (message.indexOf("ok") < 0) {
//			message = "error-" + message;
//		} else {
//			message = "ok-体检人员保存完成。";
//		}
//		return message;
//	}
	/**
	 * 
	 * @Title: getExamInfoForFlagList @Description: 获取所有待导入人员信息 @param: @param
	 * comid @param: @param batchid @param: @param
	 * userid @param: @return @param: @throws ServiceException @return:
	 * List<ImpCustomerInfoDTO> @throws
	 */
	@SuppressWarnings("unchecked")
	public void getExamInfoForFlagList(long comids, long batchids, long userid, String ids, String apptype,String center_num) throws ServiceException {
		String sql = "select id,company_id,batch_id,contract_id,arch_num,id_num,sex,"
				+ "birthday,custname,age,is_marriage,position,_level,tel,remark,"
				+ "customer_type,others,flags,notices,creater,create_time,exam_type,employeeID,visit_no,introducer,sets,zywhys,joinDatetime,employeeage,damage,occusector,occutypeofwork"
				+ ",info_type,born_address,nation,address,zip,degreeOfedu,sc_class,idtype,idtypename,exam_indicator,exam_class,employeemonth,dammonth "
				+ " From  impcustomerInfo where batch_id="
				+ batchids + " and company_id=" + comids + " and creater=" + userid + " and flags=0 and id in (" + ids
				+ ") order by create_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 50000, ImpCustomerInfoDTO.class);
		List<ImpCustomerInfoDTO> list = new ArrayList<ImpCustomerInfoDTO>();
		if ((map != null) && (map.getList() != null)) {
			list = map.getList();
			getExamInfoForFlagList(list, userid,batchids,comids,true,apptype, center_num);
		}
	}
	
	/**
	 * 
	     * @Title: zybgetExamInfoForFlagList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comids
	     * @param: @param batchids
	     * @param: @param userid
	     * @param: @param ids
	     * @param: @param centernum
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void zybgetExamInfoForFlagList(long comids, long batchids, long userid, String ids,String centernum) throws ServiceException {
		String sql = "select id,company_id,batch_id,contract_id,arch_num,id_num,sex,"
				+ "birthday,custname,age,is_marriage,position,_level,tel,remark,"
				+ "customer_type,others,flags,notices,creater,create_time,exam_type,employeeID,"
				+ "sets,zywhys,joinDatetime,employeeage,damage,occusector,occutypeofwork,idtype,exam_indicator,exam_class" 
				+ " From  impcustomerInfo where batch_id="
				+ batchids + " and company_id=" + comids + " and creater=" + userid + " and flags=0 and id in (" + ids
				+ ") order by create_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 50000, ImpCustomerInfoDTO.class);
		List<ImpCustomerInfoDTO> list = new ArrayList<ImpCustomerInfoDTO>();
		if ((map != null) && (map.getList() != null)) {
			list = map.getList();
			zybgetExamInfoForFlagList(list, userid,centernum,batchids,comids,true);
		}
	}
	
	/**
	 * 
	     * @Title: zybgetExamInfoForFlagList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param list
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param batch_id
	     * @param: @param com_id
	     * @param: @param deleteflag
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	private String zybgetExamInfoForFlagList(List<ImpCustomerInfoDTO> list, long userid,String centernum,long batch_id,long com_id, boolean deleteflag)
			throws ServiceException {
		String message = "ok";

		String idnum_notnull = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", centernum).getConfig_value();
		String djttype = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", centernum).getConfig_value();
		for (ImpCustomerInfoDTO ii : list) {
			boolean insertflag = true;
			if(ii.getIdtypename()!=null && !"".equals(ii.getIdtypename())){
				String imsql = " SELECT id FROM  data_dictionary  where  data_name = '"+ii.getIdtypename().trim()+"' and isActive='Y' ";
				List<DataDictionaryDTO> dli = this.jqm.getList(imsql, DataDictionaryDTO.class);
				if(dli.size()>0){
					ii.setIdtype((int)dli.get(0).getId());
				} else {
					message = "证件类型无效";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}
			} else {
				String imsql = " SELECT id FROM  data_dictionary  where  data_name = '身份证'  and   isActive='Y' ";
				List<DataDictionaryDTO> dli = this.jqm.getList(imsql, DataDictionaryDTO.class);
				if(dli.size()>0){
					ii.setIdtype((int)dli.get(0).getId());
				} else {
					message = "证件类型无效";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}
			}
			if("Y".equals(idnum_notnull) && "身份证".equals(ii.getIdtypename()) && StringUtil.isEmpty(ii.getId_num())){
				message = "证件号码不存在";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			String idnum_num = ValidateUtil.IDCardValidate(ii.getId_num().trim());
			if(!StringUtil.isEmpty(ii.getId_num()) && !"".equals(idnum_num) && "身份证".equals(ii.getIdtypename())){
				message = idnum_num;
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			if ("Y".equals(idnum_notnull) && (!"".equals(idnum_num)) && "身份证".equals(ii.getIdtypename().trim())) {
				message = "证件号码不存在";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			if (((ii.getId_num() == null) || (!"".equals(idnum_num))) && (ii.getAge() <= 0)
					&& ((ii.getBirthday() == null) || (ii.getBirthday().trim().length() <= 0)) && "身份证".equals(ii.getIdtypename().trim())) {
				message = "证件号码、生日、年龄都不存在";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}

			if ((ii.getCustname() == null) || (ii.getCustname().trim().length() <= 0)) {
				message = "姓名不能为空";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			CustomerInfoDTO cus = null;
			if (!StringUtil.isEmpty(ii.getVisit_no())) {
				cus = getCustomerInfoByVisitNo(ii.getVisit_no());
			}

			if (ii.getId_num() != null && !"".equals(ii.getId_num()) ) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForIdNum(ii.getId_num().trim());
				if ((!StringUtil.isEmpty(ei.getArch_num())) && (!StringUtil.isEmpty(ii.getArch_num()))
						&& (!ei.getArch_num().trim().equals(ii.getArch_num().trim()))) {
					message = "档案号不一致，档案库为：" + ei.getArch_num().trim();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} /*else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(ii.getCustname()))
						&& (!ei.getUser_name().trim().equals(ii.getCustname()))) {
					message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(ii.getSex()))
						&& (!ei.getSex().trim().equals(ii.getSex()))) {
					message = "性别不一致，档案库为：" + ei.getSex().trim();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}*/ else if (cus != null && cus.getId() != ei.getId()) {
					message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} else {
					if (ei.getId() > 0) {
						ii.setCustomerid(ei.getId());
						insertflag = false;
					}
				}
			} else if ((ii.getArch_num() != null) && (ii.getArch_num().trim().length() > 0)) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForArchIdList(ii.getArch_num().trim());
				if ((ei == null) || (ei.getId() <= 0)) {
					insertflag = true;
				} else {
					/*if ((!StringUtil.isEmpty(ei.getId_num())) && (!StringUtil.isEmpty(ii.getId_num()))
							&& (!ei.getId_num().trim().equals(ii.getId_num().trim()))) {
						message = "证件号不一致，档案库为：" + ei.getId_num().trim();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} /*else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(ii.getCustname()))
							&& (!ei.getUser_name().trim().equals(ii.getCustname()))) {
						message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(ii.getSex()))
							&& (!ei.getSex().trim().equals(ii.getSex()))) {
						message = "性别不一致，档案库为：" + ei.getSex().trim();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} else if (cus != null && cus.getId() != ei.getId()) {
						message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					}*/{
						ii.setCustomerid(ei.getId());
						insertflag = false;
					}
				}
			} else if (cus != null) {
				message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}

			if (StringUtil.isEmpty(ii.getSex())) {
				message = "性别不能为空";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}

			if (!StringUtil.isEmpty(ii.getId_num()) && "身份证".equals(ii.getIdtypename())) {
				if (!"".equals(idnum_num)) {
					message = "身份证不存在";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} else if ("".equals(idnum_num)) {
					if(ii.getId_num().length()==18){
						String sexflag = "";
						if (Integer.parseInt(ii.getId_num().substring(16).substring(0, 1)) % 2 == 0) {// 判断性别
							sexflag = "女";
						} else {
							sexflag = "男";
						}
						if (!sexflag.equals(ii.getSex())) {
							message = "身份证号与性别不符";
							updateImpCustomer(ii.getId(), 1, deleteflag, message);
							continue;
						} else {
							String bird = ii.getId_num().trim().substring(6, 14);
							Date da = Timeutils.strToDateLong2(bird);
							bird = DateTimeUtil.shortFmt3(da);
							ii.setBirthday(bird);
							ii.setAge(Timeutils.getDayForOld(bird));
						}
					} else if(ii.getId_num().length()==15){
						String sexflag = "";
						if (Integer.parseInt(ii.getId_num().substring(14).substring(0, 1)) % 2 == 0) {// 判断性别
							sexflag = "女";
						} else {
							sexflag = "男";
						}
						if (!sexflag.equals(ii.getSex())) {
							message = "身份证号与性别不符";
							updateImpCustomer(ii.getId(), 1, deleteflag, message);
							continue;
						} else {
							String bird = 19+ii.getId_num().trim().substring(6,12);
							Date da = Timeutils.strToDateLong2(bird);
							bird = DateTimeUtil.shortFmt3(da);
							ii.setBirthday(bird);
							ii.setAge(Timeutils.getDayForOld(bird));
						}
					}
				} else {
					message = "身份证无效，请核实";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}
			} else if (!StringUtil.isEmpty(ii.getBirthday())) {
				if (ii.getBirthday().trim().length() != 10) {
					if (ii.getBirthday().trim().length() == 8) {
						String bird = ii.getBirthday().trim();
						Date da = Timeutils.strToDateLong2(bird);
						bird = DateTimeUtil.shortFmt3(da);
						ii.setBirthday(bird);
					} else {
						message = "出生日期格式错误";
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					}
				}
				if (ii.getBirthday().trim().length() == 10) {
					String bird = ii.getBirthday().trim().replace("-", "");
					ii.setAge(Timeutils.getDayForOld(bird));
				}
			} else if (ii.getAge() > 0) {
				if (StringUtil.isEmpty(ii.getBirthday())) {
					ii.setBirthday(DateTimeUtil.DateDiff2((int) (ii.getAge() * 365)));
				}
			}

			if ((ii.getAge() <= 0) || (ii.getAge() > 120)) {
				message = "年龄无效";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			
			if ((ii.getAge() < 18)) {
				message = "未满18岁不得进行职业病体检";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}

			if (deleteflag) {
				JobDTO jbnew = new JobDTO();
				jbnew = this.companyService.getDatadisForChildrenCode("TJLX", "JKJC");
				if ((jbnew == null)||(jbnew.getId()==null) || (jbnew.getId().trim().length() <= 0)) {
					jbnew.setId(0+"");
				}

				if ((ii.getExam_type() != null) && (ii.getExam_type().trim().length() > 0)) {
					JobDTO jb = new JobDTO();
					jb = this.companyService.getDatadisForName("TJLX", ii.getExam_type().trim());
					if ((jb == null) || (jb.getId() == null) || (jb.getId().trim().length() <= 0)) {
						ii.setExam_type(jbnew.getId());
					} else {
						ii.setExam_type(jb.getId());
					}
				} else {
					ii.setExam_type(jbnew.getId());
				}

				if ((ii.getCustomer_type() != null) && (ii.getCustomer_type().trim().length() > 0)) {
					CustomerTypeDTO ct = new CustomerTypeDTO();
					ct = this.getcustomerTypeName(ii.getCustomer_type().trim());
					if ((ct == null) || (ct.getId() <= 0)) {
						message = "人员类型无效";
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} else {
						ii.setCustomer_type(ct.getId() + "");
					}
				} else {
					ii.setCustomer_type(1 + "");
				}

			}
			Connection connection = null;
			// 插入数据库
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				if (deleteflag) {			
					if (!StringUtil.isEmpty(ii.get_level())) {
						CompanyDepartment cd = new CompanyDepartment();
						cd = companyService.getCompanyDepartmentByid(ii.getCompany_id(), ii.get_level().trim());
						if ((cd == null) || (cd.getDep_Name() == null)) {
							String sqlcd = "insert into Company_Department (company_id,dep_name,creater,"
									+ "create_time,updater,update_time) values (" + ii.getCompany_id() + ",'"
									+ ii.get_level().trim() + "'," + userid + ",'" + DateTimeUtil.getDateTime() + "',"
									+ userid + ",'" + DateTimeUtil.getDateTime() + "')";
							PreparedStatement preparedStatement = null;
							preparedStatement = connection.prepareStatement(sqlcd, Statement.RETURN_GENERATED_KEYS);
							preparedStatement.executeUpdate();
							ResultSet rs = null;
							rs = preparedStatement.getGeneratedKeys();
							int retId = 0;
							if (rs.next())
								retId = rs.getInt(1);

							rs.close();
							preparedStatement.close();
							ii.set_level(retId + "");
						} else {
							ii.set_level(cd.getId() + "");
						}
					} else {
						ii.set_level("");
					}
				}

				String comname = "";
				CompanyInfo cinfo = this.companyService.getComByID(ii.getCompany_id());
				if ((cinfo != null) && (cinfo.getId() > 0)) {
					comname = cinfo.getCom_Name();
				}
				// 档案处理
				CustomerInfoDTO ci = new CustomerInfoDTO();
				ExamInfoDTO ei = new ExamInfoDTO();

				if ((ii.getBirthday() == null) || ("null".equals(ii.getBirthday())))
					ii.setBirthday("");
				
				if("TJ117".equals(djttype) && ii.getExam_type().equals("42")){
					insertflag = true;
				}
				if (insertflag) {
					String dah = "";
					String cusflag = "0";
					if (!StringUtil.isEmpty(ii.getId_num())) {
						String isdah = this.getCenterconfigByKey("IS_DAH_INTERFACE", centernum).getConfig_value().trim();
						String shencheng = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE_SHENCHENG", centernum).getConfig_value().trim();
						if (("Y".equals(isdah)) && (!StringUtil.isEmpty(ii.getId_num()))) {
							dah = batchService.GetCreateID("vipno", centernum);
							DAHSendMessage dahsend = new DAHSendMessage();
							DAHCustomerBean dahcu = new DAHCustomerBean();
							dahcu.setCenter_num(centernum);
							dahcu.setBrid(ii.getBirthday());
							dahcu.setId_num(ii.getId_num());
							dahcu.setName(ii.getCustname());
							dahcu.setSex(ii.getSex());
							DAHResBody tr = new DAHResBody();
							tr = dahsend.dahSend(dahcu, dah);
							if ("ok".equals(tr.getRescode())) {
								dah = tr.getIdnumber();
								cusflag = "1";
							} else {
								if("Y".equals(shencheng)){
									
								} else {
									message = "档案号无效";
									return message;
								}
								cusflag = "0";
							}

						} 
					}
					// 插入档案库
					if("TJ117".equals(djttype) && ii.getExam_type().equals("42")){
						ii.setId_num("");
						dah = batchService.GetCreateID("arch_num_rz", centernum);
					}else if("".equals(dah)){
						dah = batchService.GetCreateID("vipno", centernum);
					}
					if (ii.getArch_num() != null && !"".equals(ii.getArch_num())) {
						if (ii.getArch_num().startsWith("TJ") || "1".equals(cusflag)) {
							ii.setArch_num(dah);
						}
					} else {
						ii.setArch_num(dah);
					}
					ci.setArch_num(ii.getArch_num());// 获取体检编号
					ci.setFlag(cusflag);
					ci.setBirthday(ii.getBirthday());
					ci.setCreater(userid);
					ci.setId_num(ii.getId_num());
					ci.setIs_Active("Y");
					ci.setNation("");
					ci.setSex(ii.getSex().trim());
					ci.setUpdater(userid);
					ci.setUser_name(ii.getCustname());
					ci.setPhone(ii.getTel());
					ci.setAddress("");
					ci.setIdtype(ii.getIdtype());
					long custmerid = insertCustomerInfo(connection, ci);
					ii.setCustomerid(custmerid);
					message = message + "档案库新增加记录";
				} else {
					// 修改档案库
					ci.setArch_num(ii.getArch_num());
					ci.setBirthday(ii.getBirthday());
					ci.setCreater(userid);
					ci.setId_num(ii.getId_num());
					ci.setIs_Active("Y");
					ci.setNation("");
					ci.setSex(ii.getSex().trim());
					ci.setUpdater(userid);
					ci.setUser_name(ii.getCustname());
					ci.setId(ii.getCustomerid());
					ci.setPhone(ii.getTel());
					ci.setAddress("");
					ci.setIdtype(ii.getIdtype());
					updateCustomerInfo(connection, ci);
					message = message + "档案库修改了记录";
				}
				
				if(ii.getVisit_no()!=null && !"".equals(ii.getVisit_no()) && cus == null){
					String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
							+"	VALUES("+ii.getCustomerid()+",'"+ii.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
							+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
					connection.createStatement().executeUpdate(ssq);
				}
				// 体检信息表 插入
				ei.setExam_num(this.batchService.GetCreateID("exam_no", centernum));
				ei.setCustomer_id(ii.getCustomerid());
				ei.setCenter_num(centernum);
				ei.setIs_marriage(ii.getIs_marriage());
				ei.setAge(ii.getAge());
				ei.setStatus("Y");
				ei.setExam_type("T");
				ei.setIs_Active("Y");
				ei.setAddress("");
				ei.setPhone(ii.getTel());
				ei.setPicture_path("");
				String ispay = this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", centernum).getConfig_value().trim();
				ei.setIs_after_pay(ispay);
				ei.setAppointment("N");
				ei.setCompany(comname);
				ei.setCreater(userid);
				ei.setUpdater(userid);
				ei.setData_source("001");
				ei.setIs_sampled_directly("N");
				ei.setGetReportWay("");
				ei.setOrder_id("");
				ei.setGroup_index(ii.getOthers());
				ei.set_level(ii.get_level());
				ei.setPosition(ii.getPosition());
				ei.setCustomer_type_id(Long.valueOf(ii.getCustomer_type()));
				ei.setCustomer_type(ii.getExam_type());
				ei.setRemarke(ii.getRemark());
				ei.setOthers("");
				ei.setChargingType("164");
				ei.setReportAddress("");
				ei.setCompany_id(com_id);
				ei.setBatch_id(batch_id);
				ei.setEmployeeID(ii.getEmployeeID());
				ei.setIntroducer(ii.getIntroducer());
				ei.setVisit_no(ii.getVisit_no());
				int id = insertExamInfo(connection, ei);
				ei.setId(id);

				message = message + "、体检库新增加了记录";
				// }
				if (deleteflag) {
					String sql = "delete  from impcustomerInfo where id=" + ii.getId();
					connection.createStatement().executeUpdate(sql);
				}
				connection.commit();
			} catch (Exception ex) {
				try {
					connection.rollback();
					updateImpCustomer(ii.getId(), 1, deleteflag, ex.toString());

				} catch (SQLException e) {

				}
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

		}
		return message;
	}
	

	/**
	 * 
	 * @Title: delExamInfoForIds @Description: 批量删除 @param: @param
	 * comids @param: @param batchids @param: @param userid @param: @param
	 * ids @param: @throws ServiceException @return: void @throws
	 */
	public String delExamInfoForIds(String ids, long userid) throws ServiceException {
		String sql = "";
		Connection connection = null;
		String str="error-删除失败！";
		try {
			connection = this.jqm.getConnection();
			sql  = "select count(*) as num from exam_info  where exam_num in ('" + ids.replaceAll(",", "','") + "') and is_Active='Y' and status<>'Y'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			int counts =0;
			if(rs.next()){
				// 读取记录数
				counts=rs.getInt("num");					
			}
			rs.close();
			if(counts<=0){
			sql = "update exam_info set is_Active='N',updater=" + userid + ",update_time='"
					+ DateTimeUtil.getDateTime() + "' where exam_num in ('" + ids.replaceAll(",", "','") + "') and status='Y' ";
		           connection.createStatement().executeUpdate(sql);
		           
		           String[]  idss=ids.split(",");
		           for(int i=0;i<idss.length;i++){
		        	   String idm=idss[i];
		        	   String id = idm.substring(1, idm.length()-1);
		        	   String synstr="delete web_synchro where dataid ='"+id+"' and datatype='5'";
		   		    connection.createStatement().executeUpdate(synstr);
		   		    synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+id+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
		   		    connection.createStatement().executeUpdate(synstr);
		           }
		           
		           str="ok-删除成功！";			
		    }else{
				str="error-不能删除已经登记、正在体检和已经体检完的数据！";
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
		return str;
	}
	// ----------------------------------------私有方法开始-------------------------------------------------
		/**
		 * 
		 * @Title: getExamInfoForFlagList @Description: 获取所有待导入人员信息 @param: @param
		 * comid @param: @param batchid @param: @param
		 * userid @param: @return @param: @throws ServiceException @return:
		 * List<ImpCustomerInfoDTO> @throws
		 */
		private String getExamInfoForFlagList(ExamInfoUserDTO eu, long userid,String centernum,long batch_id,long com_id) throws ServiceException {
			String message = "ok";
			CompanyInfo cinfo = this.companyService.getComByID(eu.getCompany_id());
			if ((cinfo == null) || (cinfo.getId() <= 0)) {
				message = "单位无效。";
				return message;
			} else {
				eu.setCompany(cinfo.getCom_Name());
			}

			if (eu.getBatch_id() <= 0) {
				message = "批次无效。";
				return message;
			}
			String djttype = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", centernum).getConfig_value();
			String idnum_notnull = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", centernum).getConfig_value();
			if("Y".equals(idnum_notnull) && "身份证".equals(eu.getIdtypename()) && StringUtil.isEmpty(eu.getId_num())){
				message = "证件号码不存在";
				return message;
			}
			String idnum_num = ValidateUtil.IDCardValidate(eu.getId_num().trim());
			if(!StringUtil.isEmpty(eu.getId_num()) && !"".equals(idnum_num) && "身份证".equals(eu.getIdtypename())){
				message = idnum_num;
				return message;
			}
			if (((eu.getId_num() == null) || (eu.getId_num().trim().length() != 18)) && (eu.getAge() <= 0)
					&& ((eu.getBirthday() == null) || (eu.getBirthday().trim().length() <= 0))) {
				message = "证件号码、生日、年龄都不存在";
				return message;
			}
			if (eu.getId_num() != null && !"".equals(eu.getId_num().trim())) {
				message = ValidateUtil.IDCardValidate(eu.getId_num().trim());
				if(!"".equals(message)){
					return message;
				}
			}
			if ((eu.getUser_name() == null) || (eu.getUser_name().trim().length() <= 0)) {
				message = "姓名不能为空";
				return message;
			}
			CustomerInfoDTO cus = null;
			if (!StringUtil.isEmpty(eu.getVisit_no())) {
				cus = getCustomerInfoByVisitNo(eu.getVisit_no());
			}
			if (eu.getId_num() != null && !"".equals(eu.getId_num()) ) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForIdNum(eu.getId_num().trim());
				if ((!StringUtil.isEmpty(ei.getArch_num())) && (!StringUtil.isEmpty(eu.getArch_num()))
						&& (!ei.getArch_num().trim().equals(eu.getArch_num().trim()))) {
					message = "档案号不一致，档案库为：" + ei.getArch_num().trim();
					return message;
				}/* else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
						&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
					message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
					return message;
				} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
						&& (!ei.getSex().trim().equals(eu.getSex()))) {
					message = "性别不一致，档案库为：" + ei.getSex().trim();
					return message;
				} */else if (cus != null && cus.getId() != ei.getId()) {
					message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
					return message;
				} else {
					if (ei.getId() > 0) {
						eu.setCustomer_id(ei.getId());
					}
				}
			} else if ((eu.getArch_num() != null) && (eu.getArch_num().trim().length() > 0)) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForArchIdList(eu.getArch_num().trim());
				if ((ei == null) || (ei.getId() <= 0)) {
					
				} else {
					/*if ((!StringUtil.isEmpty(ei.getId_num())) && (!StringUtil.isEmpty(eu.getId_num()))
							&& (!ei.getId_num().trim().equals(eu.getId_num().trim()))) {
						message = "证件号不一致，档案库为：" + ei.getId_num().trim();
						return message;
					} else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
							&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
						message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
						return message;
					} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
							&& (!ei.getSex().trim().equals(eu.getSex()))) {
						message = "性别不一致，档案库为：" + ei.getSex().trim();
						return message;
					} else if (cus != null && cus.getId() != ei.getId()) {
						message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
						return message;
					} else */{
						eu.setCustomer_id(ei.getId());
					}
				}
			} else if (cus != null) {
				message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
				return message;
			}

			if ((eu.getAge() <= 0) || (eu.getAge() > 120)) {
				message = "年龄无效";
				return message;
			}

			ExamInfoDTO ei = new ExamInfoDTO();
			if (eu.getId() > 0) {
				ei = this.getExamInfoForexamnum(eu.getExam_num());
			}else if (eu.getId_num() != null && eu.getId_num().trim().length()>0) {
				List<ExamInfoDTO> eilist = new ArrayList<ExamInfoDTO>();
				eilist = getExamInfoForIdNumList(eu.getId_num().trim());
				if ((eilist != null) && (eilist.size() > 0)) {
					ExamInfoDTO eidto=eilist.get(0);
					eu.setCustomer_id(eidto.getId());
				}
			}
			Connection connection = null;
			// 插入数据库
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);

				// 档案处理
				CustomerInfoDTO ci = new CustomerInfoDTO();

				if ((eu.getBirthday() == null) || ("null".equals(eu.getBirthday())))
					eu.setBirthday("");
				if("TJ117".equals(djttype) && eu.getCustomer_type().equals("42")){
					eu.setCustomer_id(0);
				}
				if (eu.getCustomer_id() <= 0) {
					// 插入档案库

					String dah = "";
					String cusflag = "0";
					if (!StringUtil.isEmpty(eu.getId_num())) {
						String isdah = this.getCenterconfigByKey("IS_DAH_INTERFACE", centernum).getConfig_value().trim();
						String shengcheng = this.getCenterconfigByKey("IS_DAH_INTERFACE_SHENCHENG", centernum).getConfig_value().trim();
						if (("Y".equals(isdah)) && (!StringUtil.isEmpty(eu.getId_num()))) {
							dah = batchService.GetCreateID("vipno", centernum);
							DAHSendMessage dahsend = new DAHSendMessage();
							DAHCustomerBean dahcu = new DAHCustomerBean();
							dahcu.setCenter_num(centernum);
							dahcu.setBrid(eu.getBirthday());
							dahcu.setId_num(eu.getId_num());
							dahcu.setName(eu.getU_name());
							dahcu.setSex(eu.getSex());
							DAHResBody tr = new DAHResBody();
							tr = dahsend.dahSend(dahcu, dah);
							if ("ok".equals(tr.getRescode())) {
								dah = tr.getIdnumber();
								cusflag = "1";
							} else {
								if("Y".equals(shengcheng)){
									
								} else {
									message = "档案号无效";
									return message;
								}
								cusflag = "0";
							}
						}
					}
					if("TJ117".equals(djttype) && eu.getCustomer_type().equals("42")){
						eu.setId_num("");
						dah = batchService.GetCreateID("arch_num_rz", centernum);
					}else if("".equals(dah)){
						dah = batchService.GetCreateID("vipno", centernum);
					}
					if (eu.getArch_num() != null && !"".equals(eu.getArch_num())) {
						if (eu.getArch_num().startsWith("TJ") || "1".equals(cusflag)) {
							eu.setArch_num(dah);
						}
					} else {
						eu.setArch_num(dah);
					}
					ci.setArch_num(eu.getArch_num());// 获取体检编号
					ci.setFlag(cusflag);
					ci.setBirthday(eu.getBirthday());
					ci.setCreater(userid);
					ci.setId_num(eu.getId_num());
					ci.setIs_Active("Y");
					ci.setNation(eu.getNation());
					ci.setSex(eu.getSex().trim());
					ci.setUpdater(userid);
					ci.setUser_name(eu.getUser_name());
					ci.setPhone(eu.getPhone());
					ci.setAddress(eu.getAddress());
					ci.setIdtype(eu.getIdtype());
					long custmerid = insertCustomerInfo(connection, ci);
					eu.setCustomer_id(custmerid);
				} else {
					// 修改档案库
					ci.setArch_num(eu.getArch_num());// 获取体检编号
					ci.setBirthday(eu.getBirthday());
					ci.setCreater(userid);
					ci.setId_num(eu.getId_num());
					ci.setIs_Active("Y");
					ci.setNation(eu.getNation());
					ci.setSex(eu.getSex().trim());
					ci.setUpdater(userid);
					ci.setUser_name(eu.getUser_name());
					ci.setPhone(eu.getPhone());
					ci.setAddress(eu.getAddress());
					ci.setId(eu.getCustomer_id());
					ci.setIdtype(eu.getIdtype());
					updateCustomerInfo(connection, ci);
				}
				String exam_num_new = "";
				if (eu.getId() <= 0) {
					exam_num_new = this.batchService.GetCreateID("exam_no", centernum);
					// 体检信息表 插入
					ei.setExam_num(exam_num_new);
					ei.setCustomer_id(eu.getCustomer_id());
					ei.setCenter_num(centernum);
					ei.setIs_marriage(eu.getIs_marriage());
					ei.setAge(eu.getAge());
					ei.setStatus("Y");
					ei.setExam_type("T");
					ei.setIs_Active("Y");
					ei.setAddress(eu.getAddress());
					ei.setPhone(eu.getPhone());
					ei.setPicture_path("");
					ei.setIs_after_pay(eu.getIs_after_pay());
					ei.setAppointment("N");
					ei.setCompany(eu.getCompany());
					ei.setCreater(userid);
					ei.setUpdater(userid);
					ei.setData_source("001");
					ei.setIs_sampled_directly("N");
					ei.setGetReportWay("");
					ei.setOrder_id("");
					ei.set_level(eu.get_level());
					ei.setPosition(eu.getPosition());
					ei.setCustomer_type_id(eu.getCustomer_type_id());
					ei.setCustomer_type(eu.getCustomer_type());
					ei.setRemarke(eu.getRemarke());
					ei.setOthers(eu.getOthers());
					ei.setGroup_index(eu.getGroup_index());
					ei.setChargingType(eu.getChargingType());
					ei.setReportAddress(eu.getAddress());
					ei.setEmail(eu.getEmail());
					ei.setCompany_id(com_id);
					ei.setBatch_id(batch_id);
					ei.setEmployeeID(eu.getEmployeeID());
					ei.setRegister_date("");
					ei.setJoin_date("");
					ei.setGroup_id(eu.getGroup_id());
					ei.setVisit_no(eu.getVisit_no());
					ei.setWuxuzongjian(eu.getWuxuzongjian());
					ei.setExam_indicator("T");
					int id = insertExamInfo(connection, ei);
					ei.setId(id);
					message = "ok-" + ei.getExam_num();
					

				} else {
					ei.setCustomer_id(eu.getCustomer_id());
					ei.setCenter_num(centernum);
					ei.setIs_marriage(eu.getIs_marriage());
					ei.setAge(eu.getAge());
					ei.setIs_Active("Y");
					ei.setAddress(eu.getAddress());
					ei.setPhone(eu.getPhone());
					ei.setCompany(eu.getCompany());
					ei.setCreater(userid);
					ei.setUpdater(userid);
					ei.setData_source("001");
					ei.set_level(eu.get_level());
					ei.setPosition(eu.getPosition());
					ei.setCustomer_type_id(eu.getCustomer_type_id());
					ei.setCustomer_type(eu.getCustomer_type());
					ei.setRemarke(eu.getRemarke());
					ei.setOthers(eu.getOthers());
					ei.setGroup_index(eu.getGroup_index());
					ei.setChargingType(eu.getChargingType());
					ei.setReportAddress(eu.getAddress());
					ei.setEmail(eu.getEmail());
					ei.setEmployeeID(eu.getEmployeeID());
					ei.setCompany_id(com_id);
					ei.setBatch_id(batch_id);
					ei.setRegister_date("");
					ei.setJoin_date("");
					ei.setGroup_id(eu.getGroup_id());
					ei.setVisit_no(eu.getVisit_no());
					ei.setWuxuzongjian(eu.getWuxuzongjian());
					ei.setExam_indicator("T");
					updateExamInfo(connection, ei);
					message = "ok-" + ei.getExam_num();

					
				}
				if(eu.getVisit_no()!=null && !"".equals(eu.getVisit_no()) && cus == null){
					String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
							+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
							+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
					connection.createStatement().executeUpdate(ssq);
				}
				String sql = "update pacs_summary set examinfo_name='" + ci.getUser_name() + "',examinfo_sex='"
						+ ci.getSex() + "',examinfo_birthday='" + ci.getBirthday() + "' where examinfo_num='"
						+ ei.getExam_num() + "'";
				connection.createStatement().executeUpdate(sql);
				connection.commit();
			} catch (Exception ex) {
				try {
					connection.rollback();
				} catch (SQLException e) {
				}
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
			return message;
		}
	// ----------------------------------------私有方法开始-------------------------------------------------
	/**
	 * 
	 * @Title: getExamInfoForFlagList @Description: 获取所有待导入人员信息 @param: @param
	 * comid @param: @param batchid @param: @param
	 * userid @param: @return @param: @throws ServiceException @return:
	 * List<ImpCustomerInfoDTO> @throws
	 */
	@SuppressWarnings({ "null", "null" })
	private String getExamInfoForFlagList(List<ImpCustomerInfoDTO> list, long userid,long batch_id,long com_id, boolean deleteflag,String apptype,String center_num)
			throws ServiceException {
		String message = "ok";
		CenterConfigurationDTO cen = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", center_num);
		String djt = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", center_num).getConfig_value();
		String user_sql = " SELECT chi_name  as   name FROM   user_usr  where  id='"+userid+"' ";
		List<UserDTO> u_li = this.jqm.getList(user_sql, UserDTO.class);
		String chi_name =u_li.get(0).getName();
		
		String idnum_notnull = "";
		if( cen!=null && !"".equals(cen)){
			if(cen.getConfig_value()!=null && !"".equals(cen.getConfig_value())){
				idnum_notnull = cen.getConfig_value().trim();
			}
		}
		for (ImpCustomerInfoDTO ii : list) {
			boolean insertflag = true;
			if(ii.getIdtypename()!=null && ii.getIdtypename().trim().length()>0){
				String imsql = " SELECT id FROM  data_dictionary  where  data_name = '"+ii.getIdtypename().trim()+"' and isActive='Y' ";
				List<DataDictionaryDTO> dli = this.jqm.getList(imsql, DataDictionaryDTO.class);
				if(dli.size()>0){
					ii.setIdtype((int)dli.get(0).getId());
				} else {
					message = "证件类型无效";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}
			} else {
				String imsql = " SELECT id FROM  data_dictionary  where  data_name = '身份证'  and   isActive='Y' ";
				List<DataDictionaryDTO> dli = this.jqm.getList(imsql, DataDictionaryDTO.class);
				if(dli.size()>0){
					ii.setIdtype((int)dli.get(0).getId());
				} else {
					message = "证件类型无效";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}
			}
			if("Y".equals(idnum_notnull) && "身份证".equals(ii.getIdtypename()) && StringUtil.isEmpty(ii.getId_num())){
				message = "证件号码不存在";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			String idnum_num = ValidateUtil.IDCardValidate(ii.getId_num().trim());
			if(!StringUtil.isEmpty(ii.getId_num()) && !"".equals(idnum_num) && "身份证".equals(ii.getIdtypename())){
				message = idnum_num;
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			if ("Y".equals(idnum_notnull) && (!"".equals(idnum_num)) && "身份证".equals(ii.getIdtypename().trim())) {
				message = "证件号码不存在";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			if (((ii.getId_num() == null) || (!"".equals(idnum_num))) && (ii.getAge() <= 0)
					&& ((ii.getBirthday() == null) || (ii.getBirthday().trim().length() <= 0)) && "身份证".equals(ii.getIdtypename().trim())) {
				message = "证件号码、生日、年龄都不存在";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}

			if ((ii.getCustname() == null) || (ii.getCustname().trim().length() <= 0)) {
				message = "姓名不能为空";
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}
			CustomerInfoDTO cus = null;
			if (!StringUtil.isEmpty(ii.getVisit_no())) {
				cus = getCustomerInfoByVisitNo(ii.getVisit_no());
			}

			if (ii.getId_num() != null && !"".equals(ii.getId_num()) ) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForIdNum(ii.getId_num().trim());
				if ((!StringUtil.isEmpty(ei.getArch_num())) && (!StringUtil.isEmpty(ii.getArch_num()))
						&& (!ei.getArch_num().trim().equals(ii.getArch_num().trim()))) {
					message = "档案号不一致，档案库为：" + ei.getArch_num().trim();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} /*else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(ii.getCustname()))
						&& (!ei.getUser_name().trim().equals(ii.getCustname()))) {
					message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(ii.getSex()))
						&& (!ei.getSex().trim().equals(ii.getSex()))) {
					message = "性别不一致，档案库为：" + ei.getSex().trim();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}*/ else if (cus != null && cus.getId() != ei.getId()) {
					message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				} else {
					if (ei.getId() > 0) {
						ii.setCustomerid(ei.getId());
						insertflag = false;
					}
				}
			} else if ((ii.getArch_num() != null) && (ii.getArch_num().trim().length() > 0)) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForArchIdList(ii.getArch_num().trim());
				if ((ei == null) || (ei.getId() <= 0)) {
					insertflag = true;
				} else {
					/*if ((!StringUtil.isEmpty(ei.getId_num())) && (!StringUtil.isEmpty(ii.getId_num()))
							&& (!ei.getId_num().trim().equals(ii.getId_num().trim()))) {
						message = "证件号不一致，档案库为：" + ei.getId_num().trim();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(ii.getCustname()))
							&& (!ei.getUser_name().trim().equals(ii.getCustname()))) {
						message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(ii.getSex()))
							&& (!ei.getSex().trim().equals(ii.getSex()))) {
						message = "性别不一致，档案库为：" + ei.getSex().trim();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					}else if (cus != null && cus.getId() != ei.getId()) {
						message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
						updateImpCustomer(ii.getId(), 1, deleteflag, message);
						continue;
					} else*/  {
						ii.setCustomerid(ei.getId());
						insertflag = false;
					}
				}
			} else if (cus != null) {
				message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
				updateImpCustomer(ii.getId(), 1, deleteflag, message);
				continue;
			}

			if (StringUtil.isEmpty(ii.getSex())) {
				message = "性别不能为空";
				updateImpCustomer(ii.getId(), 1,deleteflag, message);
				continue;
			}		

			if (!StringUtil.isEmpty(ii.getId_num()) && !"/".equals(ii.getId_num())) {
				if (ii.getId_num().trim().length() != 18) {
					message = "身份证号位数不对";
					updateImpCustomer(ii.getId(), 1,deleteflag, message);
					continue;
				} else {

					String sexflag="";
					if (Integer.parseInt(ii.getId_num().substring(16).substring(0, 1)) % 2 == 0) {// 判断性别  
						sexflag = "女";  
			        } else {  
			        	sexflag = "男";  
			        }  
					if(!sexflag.equals(ii.getSex())){
						message = "身份证号与性别不符";
						updateImpCustomer(ii.getId(), 1,deleteflag, message);
						continue;
					}else{
						String bird = ii.getId_num().trim().substring(6, 14);
						Date da = Timeutils.strToDateLong2(bird);
						bird = DateTimeUtil.shortFmt3(da);
						ii.setBirthday(bird);
						ii.setAge(Timeutils.getDayForOld(bird));
					}
				}
			} else if (!StringUtil.isEmpty(ii.getBirthday())) {
				if (ii.getBirthday().trim().length() != 10) {
					if (ii.getBirthday().trim().length() == 8) {
						String bird = ii.getBirthday().trim();
						Date da = Timeutils.strToDateLong2(bird);
						bird = DateTimeUtil.shortFmt3(da);
						ii.setBirthday(bird);
					} else {
						message = "出生日期格式错误";
						updateImpCustomer(ii.getId(), 1,deleteflag, message);
						continue;
					}
				}
                if (ii.getBirthday().trim().length() == 10) {
						String bird = ii.getBirthday().trim().replace("-", "");
						ii.setAge(Timeutils.getDayForOld(bird));
				}
			} else if (ii.getAge() > 0) {
				if (StringUtil.isEmpty(ii.getBirthday())) {
					ii.setBirthday(DateTimeUtil.DateDiff2((int) (ii.getAge() * 365)));
				}
			}

			if ((ii.getAge() <= 0) || (ii.getAge() > 120)) {
				message = "年龄无效";
				updateImpCustomer(ii.getId(), 1,deleteflag, message);
				continue;
			}
			
			if ((ii.getCustomer_type() != null) && (ii.getCustomer_type().trim().length() > 0)) {
				CustomerTypeDTO ct = new CustomerTypeDTO();
				ct = this.getcustomerTypeName(ii.getCustomer_type().trim());
				if ((ct == null) || (ct.getId()<= 0)) {
					message = "人员类型无效";
					updateImpCustomer(ii.getId(), 1,deleteflag, message);
					continue;
				} else {
					ii.setCustomer_type_id(ct.getId());
				}
			}else{
				ii.setCustomer_type_id(1);
			}
			
			if (deleteflag) {
				if("1".equals(apptype)) {
					JobDTO jbnew = new JobDTO();
					jbnew = this.companyService.getDatadisForName("TJLX", "健康体检");
					if ((jbnew.getId() == null) || (jbnew.getId().trim().length() <= 0)) {
						jbnew.setId(0+"");
					}
					
					if ((ii.getExam_type()!= null) && (ii.getExam_type().trim().length() > 0)) {
						JobDTO jb = new JobDTO();
						jb = this.companyService.getDatadisForName("TJLX", ii.getExam_type().trim());
						if ((jb.getId() == null)|| (jb.getId().trim().length() <= 0)) {
							ii.setCustomer_type(jbnew.getId());
						} else {
							ii.setCustomer_type(jb.getId());
						}
					} else {
						ii.setCustomer_type(jbnew.getId());
					}	
				}

			}
			
			String industry_code = "";
			String typeofwork_code = "";
			List<ZybOccuhazardfactorsDTO> zybOccuhazardfactorsList = new ArrayList<>();
			List<String> sc_classList = new ArrayList<>();
			if("2".equals(apptype)){
				//职业体检类别
				if ((ii.getAge() < 18)) {
					message = "未满18岁不得进行职业病体检";
					updateImpCustomer(ii.getId(), 1, deleteflag, message);
					continue;
				}
				
				ZybOccuphyexaClassDTO zybOccuphyexaClass = this.zybOccuphyexaClassService.getOccuphyexaClass(ii.getExam_type());
				if(zybOccuphyexaClass != null) {
					ii.setExam_type(zybOccuphyexaClass.getOccuphyexaclassID());
				} else {
					message = "职业体检类别无效";
					updateImpCustomer(ii.getId(), 1,deleteflag, message);
					continue;
				}
				
				if(InfoType.ZYB.getCode() == ii.getInfo_type()) {
					JobDTO jbnew = this.companyService.getDatadisForChildrenCode("TJLX", "ZYJKJC");
					if ((ii.getExam_class()!= null) && (ii.getExam_class().trim().length() > 0)) {
						JobDTO jb = this.companyService.getDatadisForName("TJLX", ii.getExam_class().trim());
						if ((jb.getId() == null)|| (jb.getId().trim().length() <= 0)) {
							ii.setCustomer_type(jbnew.getId());
						} else {
							ii.setCustomer_type(jb.getId());
						}
					}
					
					//-1进厂日期   修改为可以为空
					/*if(ii.getJoinDatetime()==null || "".equals(ii.getJoinDatetime())){
						message = "进厂日期无效";
						updateImpCustomer(ii.getId(), 1,deleteflag, message);
						continue;
					}*/
					
					//2.检索因素
					if(ii.getZywhys()!=null && ii.getZywhys().trim().length()>0){
						boolean jumpFlag = false;
						for(String zywhys : ii.getZywhys().trim().replaceAll("，", ",").split(",")) {
							String sqls = "SELECT z.hazardfactorsID,z.hazard_name,z.hazard_code FROM zyb_occuhazardfactors z  where   z.hazard_name='"+zywhys+"'  or  z.hazard_code='"+zywhys+"'";
							List<ZybOccuhazardfactorsDTO> li = this.jqm.getList(sqls,ZybOccuhazardfactorsDTO.class);
							if(li!=null && li.size()>0){
								ZybOccuhazardfactorsDTO yinsu = li.get(0);
								zybOccuhazardfactorsList.add(yinsu);
							} else {
								message = "危害因素无效："+zywhys;
								jumpFlag = true;
								break;
							}
						}
						if(jumpFlag) {
							updateImpCustomer(ii.getId(), 1,deleteflag, message);
							continue;
						}
					} else if(ii.getSets()!=null && !"".equals(ii.getSets())) {
						String zo_sql = " select zof.* from set_occuhazardfactors so, exam_set s,zyb_occuhazardfactors zof "
								+ " where so.set_id = s.id and so.hazard_code = zof.hazard_code and s.set_name = '"+ii.getSets().trim()+"' and s.center_num='"+center_num+"' and s.is_active = 'Y' ";
						List<ZybOccuhazardfactorsDTO> li = this.jqm.getList(zo_sql, ZybOccuhazardfactorsDTO.class);
						if(li==null || li.size()==0) {
							message = "危害因素未录入";
							updateImpCustomer(ii.getId(), 1,deleteflag, message);
							continue;
						}
						for(ZybOccuhazardfactorsDTO yinsu : li) {
							zybOccuhazardfactorsList.add(yinsu);
						}
					} else {
						message = "危害因素无效";
						updateImpCustomer(ii.getId(), 1,deleteflag, message);
						continue;
					}
				} else if(InfoType.FSXZYB.getCode() == ii.getInfo_type()) {
					JobDTO jb = this.companyService.getDatadisForChildrenCode("TJLX", "FSJKJC");
					ii.setCustomer_type(jb.getId());
					//出生地、民族、职位(职务/职称)、家庭地址、家庭邮编、电话
					
					jb = this.companyService.getDatadisForName("MZLX", ii.getNation().trim());
					if(jb == null || jb.getId() == null ) {
						jb = this.companyService.getDatadisForName("MZLX", ii.getNation().trim() + "族");
					}
					ii.setNation(jb.getId());
					
					//文化程度
					if(ii.getDegreeOfedu()==null || "".equals(ii.getDegreeOfedu())){
//						message = "文化程度无效";
//						updateImpCustomer(ii.getId(), 1,deleteflag, message);
//						continue;
					} else {
						String sqlset = "SELECT edudegree_code FROM  degreeOfedu  where edudegree_name='"+ii.getDegreeOfedu().trim()+"'";
						RowSet rowSet = this.jqm.getRowSet(sqlset);
						try {
							if(rowSet.next()) {
								ii.setDegreeOfedu(rowSet.getString("edudegree_code"));
							}
						} catch (SQLException e) {
						}
					}
					//职业照射种类
					if(ii.getSc_class()==null || "".equals(ii.getSc_class())){
//						message = "职业照射种类无效";
//						updateImpCustomer(ii.getId(), 1,deleteflag, message);
//						continue;
					} else {
						boolean jumpFlag = false;
						
						for(String sc_class : ii.getSc_class().trim().replaceAll("，", ",").toUpperCase().split(",")) {
							String sqlset = "SELECT sc_classid FROM  zyb_source_career_class  where sc_classcode='"+sc_class
									+"' or sc_classname ='"+sc_class+"'";
							RowSet rowSet = this.jqm.getRowSet(sqlset);
							try {
								if(rowSet.next()) {
									sc_classList.add(rowSet.getString("sc_classid"));
								} else {
									message = "职业照射种类无效："+sc_class;
									jumpFlag = true;
									break;
								}
							} catch (SQLException e) {
							}
						}
						if(jumpFlag) {
							updateImpCustomer(ii.getId(), 1,deleteflag, message);
							continue;
						}
					}
				}
				
				//3.行业
				if(ii.getOccusector()!=null && !"".equals(ii.getOccusector())){
					String sqle = " SELECT z.industryID FROM  zyb_economicindustry z  where  z.industry_code='"+ii.getOccusector()+"'   or  z.industry_name='"+ii.getOccusector()+"' ";
					List<ZybOccuindustryDTO> li = this.jqm.getList(sqle,ZybOccuindustryDTO.class);
					if(li!=null && li.size()>0){
						industry_code = li.get(0).getIndustryID();
					} else {
						message = "行业无效";
						updateImpCustomer(ii.getId(), 1,deleteflag, message);
						continue;
					}
				} else {
//					message = "行业无效";
//					updateImpCustomer(ii.getId(), 1,deleteflag, message);
//					continue;
				}
				
				//4.工种
				if(ii.getOccutypeofwork()!=null && !"".equals(ii.getOccutypeofwork())){
					String sql2 = " SELECT z.typeofworkID FROM zyb_typeofwork  z  where   z.typeofwork_name='"+ii.getOccutypeofwork()+"'  or z.typeofwork_code='"+ii.getOccutypeofwork()+"'  ";
					List<ZybOccuindustryDTO> li = this.jqm.getList(sql2,ZybOccuindustryDTO.class);
					if(li!=null && li.size()>0){
						typeofwork_code = li.get(0).getTypeofworkID();
					} else {
						try{
							typeofwork_code = zybCustomerInfoService.addZybOccutypeofwork(ii.getOccutypeofwork().trim());
						}catch (Exception e){
							message = "工种无效";
							updateImpCustomer(ii.getId(), 1,deleteflag, message);
							continue;
						}
					}
				} else {
//					message = "工种无效";
//					updateImpCustomer(ii.getId(), 1,deleteflag, message);
//					continue;
				}
				
				//5.套餐
				if(ii.getSets()!=null && !"".equals(ii.getSets())){
					if (ii.getSets().indexOf(',')>=0 || ii.getSets().indexOf('，')>=0 ) {
						message = "套餐暂不支持多个,套餐名不支持逗号";
						updateImpCustomer(ii.getId(), 1,deleteflag, message);
						continue;
					}
					String zo_sql = " select zoc.* from set_occuhazardfactors so, exam_set s,zyb_occuphyexaclass zoc "
							+ " where so.set_id = s.id and so.occuphyexaclassID = zoc.occuphyexaclassID and s.set_name = '"+ii.getSets().trim()+"' and s.center_num='"+center_num+"' and s.is_active = 'Y' ";
					List<ZybOccuphyexaClassDTO> li = this.jqm.getList(zo_sql, ZybOccuphyexaClassDTO.class);
					if(li!=null && li.size()>0) {
						if(!li.get(0).getOccuphyexaclassID().equals(ii.getExam_type()) && !li.get(0).getOccuphyexaclass_name().equals(ii.getExam_type())) {
							message = "此套餐应该对应"+li.get(0).getOccuphyexaclass_name();
							updateImpCustomer(ii.getId(), 1,deleteflag, message);
							continue;
						}
					}
				}
			}
			
			Connection connection = null;
			// 插入数据库
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				if (deleteflag) {
					if (!StringUtil.isEmpty(ii.get_level())) {
						String cd_sql = " select id from company_department  where company_Id=" + ii.getCompany_id() + "and dep_Name='"+ii.get_level().trim()+"'  order by id desc";
						List<CompanyDepartment> cd_list = this.jqm.getList(cd_sql,CompanyDepartment.class);
						if (cd_list==null || cd_list.size()==0) {
							String sqlcd = "insert into Company_Department (company_id,dep_name,creater,"
									+ "create_time,updater,update_time) values (" + ii.getCompany_id() + ",'"
									+ ii.get_level().trim() + "'," + userid + ",'" + DateTimeUtil.getDateTime() + "',"
									+ userid + ",'" + DateTimeUtil.getDateTime() + "')";
							PreparedStatement preparedStatement = null;
							preparedStatement = connection.prepareStatement(sqlcd, Statement.RETURN_GENERATED_KEYS);
							preparedStatement.executeUpdate();
							ResultSet rs = null;
							rs = preparedStatement.getGeneratedKeys();
							int retId = 0;
							if (rs.next())
								retId = rs.getInt(1);

							rs.close();
							preparedStatement.close();
							ii.set_level(retId + "");
						} else {
							CompanyDepartment cd = cd_list.get(0);
							ii.set_level(cd.getId() + "");
						}
					}else{
						ii.set_level("");
					}
				}

				String comname = "";
				CompanyInfo cinfo = this.companyService.getComByID(ii.getCompany_id());
				if ((cinfo != null) && (cinfo.getId() > 0)) {
					comname = cinfo.getCom_Name();
				}
				// 档案处理
				CustomerInfoDTO ci = new CustomerInfoDTO();
				
				if ((ii.getBirthday() == null) || ("null".equals(ii.getBirthday())))
					ii.setBirthday("");
				if (insertflag) {
					// 插入档案库
					String dah="";
					String cusflag="0";
						if(!StringUtil.isEmpty(ii.getId_num())){
						String isdah = this.getCenterconfigByKey("IS_DAH_INTERFACE", center_num).getConfig_value().trim();
						if(("Y".equals(isdah))&&(!StringUtil.isEmpty(ii.getId_num()))){	
							dah=batchService.GetCreateID("vipno", center_num);
							DAHSendMessage dahsend= new DAHSendMessage();
							DAHCustomerBean dahcu = new DAHCustomerBean();
							dahcu.setCenter_num(center_num);
							dahcu.setBrid(ii.getBirthday());
							dahcu.setId_num(ii.getId_num());
							dahcu.setName(ii.getCustname());
							dahcu.setSex(ii.getSex());
							DAHResBody tr=new DAHResBody();
							tr=dahsend.dahSend(dahcu,dah);
							if("ok".equals(tr.getRescode())){
								dah=tr.getIdnumber();
									cusflag="1";
								}else{
									dah=this.batchService.GetCreateID("vipno", center_num);
									cusflag="0";
								}
						
						}else{
							dah=this.batchService.GetCreateID("vipno", center_num);
						}
					}else{
						dah=this.batchService.GetCreateID("vipno", center_num);
					}
					
					// 插入档案库
					ii.setArch_num(dah);
					ci.setFlag(cusflag);
					ci.setArch_num(ii.getArch_num());// 获取体检编号
					ci.setBirthday(ii.getBirthday());
					ci.setCreater(userid);
					ci.setId_num(ii.getId_num());
					ci.setIs_Active("Y");
					ci.setNation(ii.getNation());
					ci.setSex(ii.getSex().trim());
					ci.setUpdater(userid);
					ci.setUser_name(ii.getCustname());
					ci.setPhone(ii.getTel());
					ci.setAddress("");
					ci.setBorn_address(ii.getBorn_address());
					ci.setCenter_num(center_num);
					ci.setIdtype(ii.getIdtype());
					long custmerid = insertCustomerInfo(connection, ci);
					ii.setCustomerid(custmerid);
					message = message + "档案库新增加记录";
				} else {
					// 修改档案库
					ci.setArch_num(ii.getArch_num());
					ci.setBirthday(ii.getBirthday());
					ci.setCreater(userid);
					ci.setId_num(ii.getId_num());
					ci.setIs_Active("Y");
					ci.setNation(ii.getNation());
					ci.setSex(ii.getSex().trim());
					ci.setUpdater(userid);
					ci.setUser_name(ii.getCustname());
					ci.setId(ii.getCustomerid());
					ci.setPhone(ii.getTel());
					ci.setAddress("");
					ci.setBorn_address(ii.getBorn_address());
					ci.setIdtype(ii.getIdtype());
					updateCustomerInfo(connection, ci);
					message = message + "档案库修改了记录";
				}
				if(ii.getVisit_no()!=null && !"".equals(ii.getVisit_no()) && cus == null){
					String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
							+"	VALUES("+ii.getCustomerid()+",'"+ii.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
							+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
					connection.createStatement().executeUpdate(ssq);
				}
				
				String IS_CHECK_NO_SUMMARY = getCenterconfigByKey("IS_CHECK_NO_SUMMARY", center_num).getConfig_value();// 是否启用通过体检类型设置无需总检
				String IS_CHECK_NOSUMMARY_EXAMTYPE = getCenterconfigByKey("IS_CHECK_NOSUMMARY_EXAMTYPE", center_num)
						.getConfig_value();// 设置无需总检的体检类型，以逗号隔开
				int wxzj = 0;
				if ("Y".equals(IS_CHECK_NO_SUMMARY)) {
					boolean chflag = com.hjw.util.StringUtil.checkString(IS_CHECK_NOSUMMARY_EXAMTYPE, ",",
							ii.getExam_type());
					if (chflag) {
						wxzj = 1;
					}
				}
				
				// 体检信息表 插入
				ExamInfoDTO ei = new ExamInfoDTO();
			
				String imsql = " SELECT id FROM  data_dictionary  where  data_name = '健康体检' and isActive='Y' ";
				List<DataDictionaryDTO> dli2 = this.jqm.getList(imsql, DataDictionaryDTO.class);
				if(dli2.size()>0){
					ei.setComon_report_type((int)dli2.get(0).getId());
				} 
				
				ei.setExam_num(this.batchService.GetCreateID("exam_no", center_num));
				ei.setCustomer_id(ii.getCustomerid());
				ei.setCenter_num(center_num);
				ei.setIs_marriage(ii.getIs_marriage());
				ei.setAge(ii.getAge());
				ei.setStatus("Y");
				ei.setExam_type("T");
				ei.setIs_Active("Y");
				ei.setAddress(ii.getAddress());
				ei.setZip(ii.getZip());
				ei.setPhone(ii.getTel());
				ei.setPicture_path("");
				String ispay = this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", center_num).getConfig_value().trim();
				ei.setIs_after_pay(ispay);
				ei.setAppointment("N");
				ei.setCompany(comname);
				ei.setCreater(userid);
				ei.setUpdater(userid);
				ei.setData_source("001");
				ei.setIs_sampled_directly("N");
				ei.setApptype(apptype);
				ei.setGetReportWay("");
				ei.setOrder_id("");
				ei.setGroup_index(ii.getOthers());
				ei.set_level(ii.get_level());
				ei.setPosition(ii.getPosition());
				ei.setCustomer_type_id(ii.getCustomer_type_id());
			    ei.setCustomer_type(ii.getCustomer_type());
//			    ei.setCustomer_type(ii.getExam_type());
				ei.setRemarke(ii.getRemark());
				ei.setOthers("");
				ei.setChargingType("164");
				ei.setReportAddress("");
				ei.setCompany_id(com_id);
				ei.setBatch_id(batch_id);
				ei.setEmployeeID(ii.getEmployeeID());
				ei.setDegreeOfedu(ii.getDegreeOfedu());
				ei.setVisit_no(ii.getVisit_no());
				ei.setWuxuzongjian(wxzj);
				ei.setExam_center_num(center_num);
				if(ii.getSets()!=null && !"".equals(ii.getSets())) {
					ei.setZyb_set_source(1);//1表示关联自选套餐
				}
				int id = insertExamInfo(connection, ei);
				ei.setId(id);

				message = message + "、体检库新增加了记录";
				if (deleteflag) {
					String sql = "delete  from impcustomerInfo where id=" + ii.getId();
					connection.createStatement().executeUpdate(sql);
				}
				PreparedStatement pre = null;  
				if("2".equals(apptype)){
					//5.插入职业病体检信息
					String sql = "insert into exam_ext_info(exam_num,arch_num,occutypeofworkid,occusectorid,"
							+ "occusector,occutypeofwork,employeeage,damage,remark,creater,create_time,employeemonth,dammonth,joinDatetime) "
							+ "values ('" + ei.getExam_num() + "','" + ci.getArch_num() + "','" + typeofwork_code
							+ "','" + industry_code + "','" + ii.getOccusector() + "','" + ii.getOccutypeofwork() + "',"
							+ ii.getEmployeeage() + "," + ii.getDamage() + ",'"+ii.getRemark()+"'"
							+ ",'"+userid+"','"+DateTimeUtil.getDate3()+"',"+ii.getEmployeemonth()+","+ii.getDammonth();

							if(ii.getJoinDatetime() == null || "".equals(ii.getJoinDatetime())){
							sql += ", null )";
							}else{
						     sql += ",'"+ii.getJoinDatetime()+"')";
					        }

					connection.createStatement().executeUpdate(sql);
					if(InfoType.ZYB.getCode() == ii.getInfo_type()) {
						
						//6.插入职业病体检和职业危害因素关系表（含职业体检类别）    调整1
						String  zsql = "";
						UUID uuid = null;
						String u_id = "";
						 zsql = " INSERT into  Zyb_Exam_Occuhazardfactors(id,exam_num,arch_num,hazard_code,occuphyexaclassid,isActive,creater,create_time,remark)"
								   +" VALUES(?,?,?,?,?,?,?,?,?) ";
						 
						for(ZybOccuhazardfactorsDTO zybOccuhazardfactor : zybOccuhazardfactorsList) {
							
							uuid = UUID.randomUUID();
							u_id = uuid.toString().replaceAll("-","");
							
							pre = connection.prepareStatement(zsql);
							pre.setString(1,u_id);
							pre.setString(2,ei.getExam_num());
							pre.setString(3,ci.getArch_num());
							pre.setString(4,zybOccuhazardfactor.getHazard_code());
							pre.setString(5,ii.getExam_type());
							pre.setString(6,"Y");
							pre.setLong(7,userid);
							pre.setString(8,DateTimeUtil.getDate3());
							pre.setString(9,zybOccuhazardfactor.getHazard_name());
							pre.executeUpdate();
							pre.close();
							
						}
						String set_sql = "";
						if(ii.getSets()!=null && !"".equals(ii.getSets())) {
							//6.1直接从excel关联自选套餐
							set_sql = " SELECT * FROM  exam_set s  WHERE  s.set_name = '"+ii.getSets().trim()+"' and s.center_num='"+center_num+"' ";
						} else {
							//6.1通过体检类别和因素获取套餐
							String yinsuHazardfactorsID="";
							for (int i = 0; i < zybOccuhazardfactorsList.size(); i++) {
								 yinsuHazardfactorsID += "'"+zybOccuhazardfactorsList.get(i).getHazardfactorsID()+"',";
							}
							set_sql = " SELECT * FROM  exam_set s  WHERE  s.hazardfactorsid in("+yinsuHazardfactorsID.substring(0, yinsuHazardfactorsID.length()-1)+") AND   s.occuphyexaclassid='"+ii.getExam_type()+"'";
							//set_sql = " SELECT * FROM  exam_set s  WHERE  s.hazardfactorsid = '"+yinsu.getHazardfactorsID()+"' AND   s.occuphyexaclassid='"+ii.getExam_type()+"' and s.center_num='"+center_num+"'";
						}
						@SuppressWarnings("unchecked")
						List<ExamSetDTO> taocan = this.jqm.getList(set_sql,ExamSetDTO.class);
						
						if(taocan!=null & taocan.size()>0){
							for (int i = 0; i < taocan.size(); i++) {
								
								String esi = " SELECT e.id  from examinfo_set  e where   e.exam_num = '"+ei.getExam_num()+"'  AND  e.exam_set_id = '"+taocan.get(i).getId()+"' and  app_type='2' ";
								@SuppressWarnings("unchecked")
								List<ExaminfoSet> seset = this.jqm.getList(esi,ExaminfoSet.class);
								if(seset!=null && seset.size()>0){
									System.out.println("套餐冲突");
								} else {
									//6.2插入职业病体检和套餐
									/*String efs_sql = " insert INTO   examinfo_set(examinfo_id,exam_set_id,exam_indicator,discount,amount,isActive,final_exam_date,"
											+ "is_new_added,creater,create_time,updater,update_time,app_type,exam_num)  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
									
									pre = connection.prepareStatement(efs_sql);
									pre.setLong(1,ei.getId());
									pre.setLong(2,taocan.get(0).getId());
									pre.setString(3,"T");
									pre.setDouble(4,taocan.get(0).getSet_discount());
									pre.setDouble(5,taocan.get(0).getSet_amount());
									pre.setString(6,"Y");
									pre.setString(7,DateTimeUtil.getDate3());
									pre.setLong(8,0);
									pre.setLong(9,userid);
									pre.setString(10,DateTimeUtil.getDate3());
									pre.setLong(11,userid);
									pre.setString(12,DateTimeUtil.getDate3());
									pre.setString(13,apptype);
									pre.setString(14,ei.getExam_num());
									pre.executeUpdate();
									pre.close();*/
									
									
									String efs_sql = " insert INTO   examinfo_set(examinfo_id,exam_set_id,exam_indicator,discount,amount,isActive,final_exam_date,"
											+ "is_new_added,creater,create_time,updater,update_time,app_type,exam_num)  VALUES('"+ei.getId()+"','"+taocan.get(i).getId()+"',"
													+ " 'T',"+taocan.get(i).getSet_discount()+","+taocan.get(i).getSet_amount()+",'Y','"+DateTimeUtil.getDate3()+"',"
															+ " "+0+","+userid+",'"+DateTimeUtil.getDate3()+"',"+userid+",'"+DateTimeUtil.getDate3()+"','"+apptype+"','"+ei.getExam_num()+"')";

									jpm.executeSql(efs_sql);
									
									//6.3通过套餐获取项目，插入人员和项目关系
									String item_hql = " SELECT *  FROM set_charging_item  s  where   s.exam_set_id = '"+taocan.get(i).getId()+"'";
									System.out.println("通过套餐获取收费项目"+item_hql);
									@SuppressWarnings("unchecked")
									List<SetChargingItem>  li =  this.jqm.getList(item_hql, SetChargingItem.class);
									
									for (SetChargingItem s : li) {
										
//										String esich = " SELECT id from examinfo_charging_item  e where   e.examinfo_id = '"+ei.getId()+"'  AND  e.charge_item_id = '"+s.getCharging_item_id()+"' and  app_type='2' ";
//										@SuppressWarnings("unchecked")
//										List<ExaminfoChargingItem> se = this.jqm.getList(esich, ExaminfoChargingItem.class);
//										List<SetChargingItemDTO> esili = new ArrayList<SetChargingItemDTO>();
//										if(se!=null && se.size()>0){
//											//项目已存在不添加
//											System.out.println("项目冲突");
//										} else {
											/*String it_sql = " insert into  examinfo_charging_item"
													+ "(amount,app_type,change_item,charge_item_id"
													+ ",check_status,create_time"
													+ ",creater,discount,exam_indicator,exam_status"
													+ ",examinfo_id,final_exam_date,his_req_status"
													+ ",is_application,is_new_added,isActive"
													+ ",item_amount,team_pay"
													+ ",itemnum,pay_status,update_time,updater,exam_num,charging_item_code,center_num) "
													+ "  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
											pre = connection.prepareStatement(it_sql);  
											pre.setDouble(1,s.getAmount());
											//pre.setString(2,apptype);
											pre.setString(2,s.getApptype());
											pre.setString(3,"N");
											pre.setLong(4,s.getCharging_item_id());
											pre.setLong(5,0);
											pre.setString(6,DateTimeUtil.getDate3());
											pre.setLong(7,userid);
											pre.setDouble(8,s.getDiscount());
											pre.setString(9,"T");
											pre.setString(10,"N");
											pre.setLong(11,ei.getId());
											pre.setString(12,DateTimeUtil.getDate3());
											pre.setString(13,"N");
											pre.setString(14,"N");
											pre.setLong(15,0);
											pre.setString(16,"Y");
											pre.setDouble(17,s.getItem_amount());
											pre.setDouble(18,s.getAmount());
											pre.setDouble(19,1);
											pre.setString(20,"R");
											pre.setString(21,DateTimeUtil.getDate3());
											pre.setLong(22,userid);
											pre.setString(23,ei.getExam_num());
											pre.setString(24,s.getCharging_item_code());
											pre.setString(25,center_num);
											pre.executeUpdate();
											pre.close();*/
											
											String exam_indicator = "";
											String pay_status = "";
											double team_pay = 0.0;
											double personal_pay = 0.0;
											if("团费".equals(ii.getExam_indicator())) {
												exam_indicator = "T";
												pay_status = "R";
												team_pay = s.getAmount();
											} else if("个费".equals(ii.getExam_indicator())) {
												exam_indicator = "G";
												pay_status = "N";
												personal_pay = s.getAmount();
											}
											
											String it_sql = " insert into  examinfo_charging_item"
													+ "(amount,app_type,change_item,charge_item_id"
													+ ",check_status,create_time"
													+ ",creater,discount,exam_indicator,exam_status"
													+ ",examinfo_id,final_exam_date,his_req_status"
													+ ",is_application,is_new_added,isActive"
													+ ",item_amount,team_pay,personal_pay"
													+ ",itemnum,pay_status,update_time,updater,exam_num,charging_item_code,center_num) "
													+ "  values("+s.getAmount()+","+taocan.get(i).getApp_type()+",'N','"+s.getCharging_item_id()+"',"+0+",'"+DateTimeUtil.getDate3()+"',"
													+ " "+userid+","+s.getDiscount()+",'"+exam_indicator+"','N',"+ei.getId()+",'"+DateTimeUtil.getDate3()+"','N','N',"+0+",'Y',"
													+ " "+s.getItem_amount()+","+team_pay+","+personal_pay+","+1+",'"+pay_status+"','"+DateTimeUtil.getDate3()+"',"+userid+",'"+ei.getExam_num()+"','"+s.getCharging_item_code()+"','"+center_num+"')";
											
											jpm.executeSql(it_sql);
											
											/**
											 * *
											 * *6.3.0//项目写入lis申请表
											 * 
											 */
											//6.3.1获取收费项目
											StringBuffer ci_sql = new StringBuffer("select id,dep_id,sam_demo_id,sam_report_demo_id,"
													+ "item_code,item_name,item_pinyin,item_category,sex,amount,dep_category,"
													+ "isOnlyApplyOrReport,item_seq,guide_category,his_num,exam_num,view_num,"
													+ "isActive,creater,create_time,updater,update_time,calculation_amount,"
													+ "interface_flag,item_type,charge_inter_num,item_abbreviation "
													+ "From charging_item where "
													+ "  id = '"+s.getCharging_item_id()+"' ");
											ci_sql.append("  order by update_time desc");
											@SuppressWarnings("unchecked")
											List<ChargingItemDTO> ciili = this.jqm.getList(ci_sql.toString(),ChargingItemDTO.class);
											ChargingItemDTO cii = ciili.get(0);
											
											//6.3.2判断是否是lis检查项目
											int lisnum = zybCustomerInfoService.checkZybListItem(cii.getItem_code(),center_num);
											if (lisnum > 0) {
												String sed_sql = "SELECT *  from sample_exam_detail g where g.exam_num = '"+ei.getExam_num()+"' and g.status='W' and g.sample_id="+ cii.getSam_demo_id()+"";
												ResultSet sed_rs = connection.createStatement().executeQuery(sed_sql);
												long samexam_id = 0;
												String sampBarcode = "";
												if (!sed_rs.next()) {
													sampBarcode = this.batchService.GetCreateID("barcode", center_num);
													String samp_sql = " insert into  sample_exam_detail(exam_info_id"
															+ ",sample_id,sample_barcode,status,pic_path"
															+ ",center_num,creater,create_time,updater,update_time,exam_num)"
															+ "  values(?,?,?,?,?,?,?,?,?,?,?)";
													//PreparedStatement samp_sta = connection.prepareStatement(samp_sql, Statement.RETURN_GENERATED_KEYS);
													pre = connection.prepareStatement(samp_sql, Statement.RETURN_GENERATED_KEYS);
													pre.setLong(1,ei.getId());
													pre.setLong(2,cii.getSam_demo_id());
													pre.setString(3,sampBarcode);
													pre.setString(4,"W");
													pre.setString(5,"");
													pre.setString(6,center_num);
													pre.setLong(7,userid);
													pre.setString(8,DateTimeUtil.getDate3());
													pre.setLong(9,userid);
													pre.setString(10,DateTimeUtil.getDate3());
													pre.setString(11,ei.getExam_num());
													pre.executeUpdate();
													ResultSet rs = pre.getGeneratedKeys();
													if (rs.next()) {
														samexam_id = rs.getLong(1);   
													}  
													pre.close();
													
												} else {
													sampBarcode = sed_rs.getString("sample_barcode");
													samexam_id = sed_rs.getLong("id");
												}
												sed_rs.close();
												
												String exam_sql = " insert  into  examResult_chargingItem(charging_id,exam_id"
														+ ",result_type,isActive,creater,create_time,updater,update_time,charging_item_code,bar_code)"
														+ "  values(?,?,?,?,?,?,?,?,?,?)";
												//ChargingItem chargingItem = chargingItemService.findChargingItem(s.getCharging_item_id());
												String charingsql = "select *  from  charging_item  c where c.id='"+s.getCharging_item_id()+"' ";
												List<ChargingItem> chargingItem = this.jqm.getList(charingsql, ChargingItem.class);
												pre = connection.prepareStatement(exam_sql);
												pre.setLong(1,s.getCharging_item_id());
												pre.setLong(2,samexam_id);
												pre.setString(3,CommSet.lis_sample);
												pre.setString(4,"Y");
												pre.setLong(5,userid);
												pre.setString(6,DateTimeUtil.getDate3());
												pre.setLong(7,userid);
												pre.setString(8,DateTimeUtil.getDate3());
												pre.setString(9, chargingItem.get(0).getItem_code());
												pre.setString(10, sampBarcode);										
												pre.executeUpdate();
												pre.close();
											}
											/**
											 * 
											 * 6.4.0项目写入pass申请表
											 *
											 */
											//6.4.1//判断是否是pacs项目
											int pacsnum = zybCustomerInfoService.checkZybPacsItem(cii.getItem_code(),center_num);
											if (pacsnum > 0) {
												long summary_id = 0;
												String pacsReqCode = "";
												String psd_sql = "SELECT *  from pacs_summary g where g.examinfo_num='"+ei.getExam_num()+"' and g.exam_status='N' and g.examinfo_sampleId="+cii.getSam_demo_id()+"";
												ResultSet psd_rs = connection.createStatement().executeQuery(psd_sql);
												//6.4.2写入pass申请主表
												if (!psd_rs.next()) {
													pacsReqCode = this.batchService.GetCreateID("pacs_req_num", center_num);
													String pass_sql = " insert into  pacs_summary(examinfo_num,examinfo_name"
															+ ",examinfo_sex,examinfo_birthday,apply_person,apply_date,exam_status"
															+ ",creater,create_time,updater,update_time,examinfo_sampleId,pacs_req_code)"
															+  "  values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
													pre = connection.prepareStatement(pass_sql,Statement.RETURN_GENERATED_KEYS);
													pre.setString(1,ei.getExam_num());
													pre.setString(2,ci.getUser_name());
													pre.setString(3,ci.getSex());
													pre.setString(4,ci.getBirthday().trim());
													pre.setString(5,chi_name);
													pre.setString(6,DateTimeUtil.getDate3());
													pre.setString(7,"N");
													pre.setLong(8,userid);
													pre.setString(9,DateTimeUtil.getDate3());
													pre.setLong(10,userid);
													pre.setString(11,DateTimeUtil.getDate3());
													pre.setLong(12,cii.getSam_demo_id());
													pre.setString(13,pacsReqCode);
													pre.executeUpdate();
													ResultSet rs = pre.getGeneratedKeys();
													if (rs.next()) {
														summary_id = rs.getLong(1);   
													}  
													pre.close();
												} else {
													pacsReqCode = psd_rs.getString("pacs_req_code");
													summary_id = psd_rs.getLong("id");
												}
												psd_rs.close();
												String dd_sql = " SELECT dep_num,dep_name FROM  department_dep  d   where  d.id="+cii.getDep_id();
												ResultSet dd_rs = connection.createStatement().executeQuery(dd_sql);
												dd_rs.next();
												String dep_num = dd_rs.getString("dep_num");
												String dep_name = dd_rs.getString("dep_name");
												dd_rs.close();
												
												String pd_sql = " insert  into  Pacs_detail(examinfo_num,summary_id,chargingItem_num"
														+ ",chargingItem_name,dep_num,dep_name,examinfo_sampleId,is_need_return"
														+ ",creater,create_time,updater,update_time,pacs_req_code) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
												pre = connection.prepareStatement(pd_sql);
												pre.setString(1,ei.getExam_num());
												pre.setLong(2,summary_id);
												pre.setString(3,cii.getItem_code());
												pre.setString(4,cii.getItem_name());
												pre.setString(5,dep_num);
												pre.setString(6,dep_name);
												pre.setLong(7,cii.getSam_demo_id());
												pre.setString(8,"Y");
												pre.setLong(9,userid);
												pre.setString(10,DateTimeUtil.getDate3());
												pre.setLong(11,userid);
												pre.setString(12,DateTimeUtil.getDate3());
												pre.setString(13,pacsReqCode);
												pre.executeUpdate();
												pre.close();
											}
//										}
									}
								}
							}
							
							
							
						}     
					} else if(InfoType.FSXZYB.getCode() == ii.getInfo_type()) {
						//7.放射性体检
						//插入职业病体检和职业危害因素关系表（含职业体检类别）
						String zo_sql = "SELECT z.hazardfactorsID,z.hazard_name,z.hazard_code FROM zyb_occuhazardfactors z  where   z.remark='FSJKJC'";
						ResultSet zo_rs = connection.createStatement().executeQuery(zo_sql);
						ZybOccuhazardfactorsDTO zybOccuhazardfactor = new ZybOccuhazardfactorsDTO();
						if(zo_rs.next()) {
							zybOccuhazardfactor.setHazardfactorsID(zo_rs.getString("hazardfactorsID"));
							zybOccuhazardfactor.setHazard_code(zo_rs.getString("hazard_code"));
							zybOccuhazardfactor.setHazard_name(zo_rs.getString("hazard_name"));
						}
						zo_rs.close();
						String sqlz = "INSERT INTO [zyb_exam_occuhazardfactors] ([id],[exam_num],[arch_num],[hazard_code],[occuphyexaclassid]"
								+ ",[isActive],[creater],[create_time],[remark]) VALUES (?,?,?,?,?,?,?,?,?)";
						pre = connection.prepareStatement(sqlz);
						UUID uuid = UUID.randomUUID();
						String u_id = uuid.toString().replaceAll("-","");
						pre.setString(1,u_id);//id
						pre.setString(2,ei.getExam_num());//exam_num
						pre.setString(3,ci.getArch_num());//arch_num
						pre.setString(4,zybOccuhazardfactor.getHazard_code());//hazard_code
						pre.setString(5,ii.getExam_type());//occuphyexaclassid
						pre.setString(6,"Y");//isActive
						pre.setLong(7,userid);//creater
						pre.setString(8,DateTimeUtil.getDate3());//create_time
						pre.setString(9,zybOccuhazardfactor.getHazard_name());//remark
						pre.executeUpdate();
						pre.close();
						
						String set_sql="";
						if(ii.getSets()!=null && !"".equals(ii.getSets())) {
							//直接从excel关联自选套餐
							set_sql = " SELECT * FROM  exam_set s  WHERE  s.set_name = '"+ii.getSets().trim()+"' and s.center_num='"+center_num+"'";
						} else{
							set_sql = " SELECT * FROM  exam_set s  WHERE  s.hazardfactorsid = '"+zybOccuhazardfactor.getHazardfactorsID()+"' AND   s.occuphyexaclassid='"+ii.getExam_type()+"' and is_Active = 'Y' and s.center_num='"+center_num+"'";
						}
						@SuppressWarnings("unchecked")
						List<ExamSetDTO> taocan = this.jqm.getList(set_sql,ExamSetDTO.class);
						
						if(taocan!=null & taocan.size()>0){
							
							String esi = " SELECT e.id  from examinfo_set  e where   e.exam_num = '"+ei.getExam_num()+"'  AND  e.exam_set_id = '"+taocan.get(0).getId()+"' and  app_type='2' ";
							@SuppressWarnings("unchecked")
							List<ExaminfoSet> seset = this.jqm.getList(esi,ExaminfoSet.class);
							if(seset!=null && seset.size()>0){
								System.out.println("套餐冲突");
							} else {
								//6.2插入职业病体检和套餐
								String efs_sql = " insert INTO   examinfo_set(examinfo_id,exam_set_id,exam_indicator,discount,amount,isActive,final_exam_date,"
										+ "is_new_added,creater,create_time,updater,update_time,app_type,exam_num)  VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
								
								pre = connection.prepareStatement(efs_sql);
								pre.setLong(1,ei.getId());
								pre.setLong(2,taocan.get(0).getId());
								pre.setString(3,"T");
								pre.setDouble(4,taocan.get(0).getSet_discount());
								pre.setDouble(5,taocan.get(0).getSet_amount());
								pre.setString(6,"Y");
								pre.setString(7,DateTimeUtil.getDate3());
								pre.setLong(8,0);
								pre.setLong(9,userid);
								pre.setString(10,DateTimeUtil.getDate3());
								pre.setLong(11,userid);
								pre.setString(12,DateTimeUtil.getDate3());
								pre.setString(13,apptype);
								pre.setString(14,ei.getExam_num());
								pre.executeUpdate();
								pre.close();
							}
							
							//6.3通过套餐获取项目，插入人员和项目关系
							
							String item_hql = " SELECT *  FROM set_charging_item  s  where   s.exam_set_id = '"+taocan.get(0).getId()+"'";
							System.out.println("套餐获取项目"+item_hql);
							@SuppressWarnings("unchecked")
							List<SetChargingItem>  li =  this.jqm.getList(item_hql, SetChargingItem.class);
							
							for (SetChargingItem s : li) {
								
//								String esich = " SELECT id from examinfo_charging_item  e where   e.examinfo_id = '"+ei.getId()+"'  AND  e.charge_item_id = '"+s.getCharging_item_id()+"' and  app_type='2' ";
//								@SuppressWarnings("unchecked")
//								List<ExaminfoChargingItem> se = this.jqm.getList(esich, ExaminfoChargingItem.class);
//								List<SetChargingItemDTO> esili = new ArrayList<SetChargingItemDTO>();
//								if(se!=null && se.size()>0){
//									//项目已存在不添加
//									System.out.println("项目冲突");
//								} else {
									ExaminfoChargingItem it = new  ExaminfoChargingItem();
									String it_sql = " insert into  examinfo_charging_item"
											+ "(amount,app_type,change_item,charge_item_id"
											+ ",check_status,create_time"
											+ ",creater,discount,exam_indicator,exam_status"
											+ ",examinfo_id,final_exam_date,his_req_status"
											+ ",is_application,is_new_added,isActive"
											+ ",item_amount,team_pay"
											+ ",itemnum,pay_status,update_time,updater,charging_item_code,exam_num,center_num) "
											+ "  values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
									pre = connection.prepareStatement(it_sql);  
									pre.setDouble(1,s.getAmount());
									//pre.setString(2,apptype);
									pre.setString(2,s.getApptype());
									pre.setString(3,"N");
									pre.setLong(4,s.getCharging_item_id());
									pre.setLong(5,0);
									pre.setString(6,DateTimeUtil.getDate3());
									pre.setLong(7,userid);
									pre.setDouble(8,s.getDiscount());
									pre.setString(9,"T");
									pre.setString(10,"N");
									pre.setLong(11,ei.getId());
									pre.setString(12,DateTimeUtil.getDate3());
									pre.setString(13,"N");
									pre.setString(14,"N");
									pre.setLong(15,0);
									pre.setString(16,"Y");
									pre.setDouble(17,s.getItem_amount());
									pre.setDouble(18,s.getAmount());
									pre.setDouble(19,1);
									pre.setString(20,"R");
									pre.setString(21,DateTimeUtil.getDate3());
									pre.setLong(22,userid);
									pre.setString(23,s.getCharging_item_code());
									pre.setString(24,ei.getExam_num());
									pre.setString(25,center_num);
									pre.executeUpdate();
									pre.close();
									
									/**
									 * *
									 * *6.3.0//项目写入lis申请表
									 * 
									 */
									//6.3.1获取收费项目
									StringBuffer ci_sql = new StringBuffer("select id,dep_id,sam_demo_id,sam_report_demo_id,"
											+ "item_code,item_name,item_pinyin,item_category,sex,amount,dep_category,"
											+ "isOnlyApplyOrReport,item_seq,guide_category,his_num,exam_num,view_num,"
											+ "isActive,creater,create_time,updater,update_time,calculation_amount,"
											+ "interface_flag,item_type,charge_inter_num,item_abbreviation "
											+ "From charging_item where "
											+ "  id = '"+s.getCharging_item_id()+"' ");
									ci_sql.append("  order by update_time desc");
									@SuppressWarnings("unchecked")
									List<ChargingItemDTO> ciili = this.jqm.getList(ci_sql.toString(),ChargingItemDTO.class);
									ChargingItemDTO cii = ciili.get(0);
									
									//6.3.2判断是否是lis检查项目
									int lisnum = zybCustomerInfoService.checkZybListItem(cii.getItem_code(),center_num);
									if (lisnum > 0) {
										String sed_sql = "SELECT *  from sample_exam_detail g where g.exam_num ='"+ei.getExam_num()+"' and g.status='W' and g.sample_id="+ cii.getSam_demo_id()+"";
										ResultSet sed_rs = connection.createStatement().executeQuery(sed_sql);
										long samexam_id = 0;
										String sampBarcode = "";
										if (!sed_rs.next()) {
											sampBarcode = this.batchService.GetCreateID("barcode", center_num);
											String samp_sql = " insert into  sample_exam_detail(exam_info_id"
													+ ",sample_id,sample_barcode,status,pic_path"
													+ ",center_num,creater,create_time,updater,update_time,exam_num)"
													+ "  values(?,?,?,?,?,?,?,?,?,?,?)";
											pre = connection.prepareStatement(samp_sql, Statement.RETURN_GENERATED_KEYS);
											pre.setLong(1,ei.getId());
											pre.setLong(2,cii.getSam_demo_id());
											pre.setString(3,sampBarcode);
											pre.setString(4,"W");
											pre.setString(5,"");
											pre.setString(6,center_num);
											pre.setLong(7,userid);
											pre.setString(8,DateTimeUtil.getDate3());
											pre.setLong(9,userid);
											pre.setString(10,DateTimeUtil.getDate3());
											pre.setString(11,ei.getExam_num());
											pre.executeUpdate();
											ResultSet rs = pre.getGeneratedKeys();
											if (rs.next()) {
												samexam_id = rs.getLong(1);   
											}  
											pre.close();
											
										} else {
											sampBarcode = sed_rs.getString("sample_barcode");
											samexam_id = sed_rs.getLong("id");
										}
										sed_rs.close();
										String exam_sql = " insert  into  examResult_chargingItem(charging_id,exam_id"
												+ ",result_type,isActive,creater,create_time,updater,update_time,bar_code,charging_item_code)"
												+ "  values(?,?,?,?,?,?,?,?,?,?)";
										
										pre = connection.prepareStatement(exam_sql);
										pre.setLong(1,s.getCharging_item_id());
										pre.setLong(2,samexam_id);
										pre.setString(3,CommSet.lis_sample);
										pre.setString(4,"Y");
										pre.setLong(5,userid);
										pre.setString(6,DateTimeUtil.getDate3());
										pre.setLong(7,userid);
										pre.setString(8,DateTimeUtil.getDate3());
										pre.setString(9,sampBarcode);
										pre.setString(10,s.getCharging_item_code());
										pre.executeUpdate();
										pre.close();
									}
									/**
									 * 
									 * 6.4.0项目写入pass申请表
									 *
									 */
									//6.4.1//判断是否是pacs项目
									int pacsnum = zybCustomerInfoService.checkZybPacsItem(cii.getItem_code(),center_num);
									if (pacsnum > 0) {
										long summary_id = 0;
										String pacsReqCode = "";
										String psd_sql = "SELECT *  from pacs_summary g where g.examinfo_num='"+ei.getExam_num()+"' and g.exam_status='N' and g.examinfo_sampleId="+cii.getSam_demo_id()+"";
										ResultSet psd_rs = connection.createStatement().executeQuery(psd_sql);
										//6.4.2写入pass申请主表
										if (!psd_rs.next()) {
											pacsReqCode = this.batchService.GetCreateID("pacs_req_num", center_num);
											String pass_sql = " insert into  pacs_summary(examinfo_num,examinfo_name"
													+ ",examinfo_sex,examinfo_birthday,apply_person,apply_date,exam_status"
													+ ",creater,create_time,updater,update_time,examinfo_sampleId,pacs_req_code)"
													+  "  values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
											pre = connection.prepareStatement(pass_sql,Statement.RETURN_GENERATED_KEYS);
											pre.setString(1,ei.getExam_num());
											pre.setString(2,ci.getUser_name());
											pre.setString(3,ci.getSex());
											pre.setString(4,ci.getBirthday().trim());
											pre.setString(5,chi_name);
											pre.setString(6,DateTimeUtil.getDate3());
											pre.setString(7,"N");
											pre.setLong(8,userid);
											pre.setString(9,DateTimeUtil.getDate3());
											pre.setLong(10,userid);
											pre.setString(11,DateTimeUtil.getDate3());
											pre.setLong(12,cii.getSam_demo_id());
											pre.setString(13,pacsReqCode);
											pre.executeUpdate();
											ResultSet rs = pre.getGeneratedKeys();
											if (rs.next()) {
												summary_id = rs.getLong(1);   
											}  
											pre.close();
										} else {
											pacsReqCode = psd_rs.getString("pacs_req_code");
											summary_id = psd_rs.getLong("id");
										}
										psd_rs.close();
										String dd_sql = " SELECT dep_num,dep_name FROM  department_dep  d   where  d.id="+cii.getDep_id();
										ResultSet dd_rs = connection.createStatement().executeQuery(dd_sql);
										dd_rs.next();
										String dep_num = dd_rs.getString("dep_num");
										String dep_name = dd_rs.getString("dep_name");
										dd_rs.close();
										
										String pd_sql = " insert  into  Pacs_detail(examinfo_num,summary_id,chargingItem_num"
												+ ",chargingItem_name,dep_num,dep_name,examinfo_sampleId,is_need_return"
												+ ",creater,create_time,updater,update_time,pacs_req_code) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
										pre = connection.prepareStatement(pd_sql);
										pre.setString(1,ei.getExam_num());
										pre.setLong(2,summary_id);
										pre.setString(3,cii.getItem_code());
										pre.setString(4,cii.getItem_name());
										pre.setString(5,dep_num);
										pre.setString(6,dep_name);
										pre.setLong(7,cii.getSam_demo_id());
										pre.setString(8,"Y");
										pre.setLong(9,userid);
										pre.setString(10,DateTimeUtil.getDate3());
										pre.setLong(11,userid);
										pre.setString(12,DateTimeUtil.getDate3());
										pre.setString(13,pacsReqCode);
										
										pre.executeUpdate();
										pre.close();
									}
//								}
							}
						}
						String sc_sql = " insert  into  exam_ext_typeofocc(id,arch_num,exam_num,sc_classcode) "
								+ " values(?,?,?,?)" ;
						//8.插入职业照射种类
						for(String sc_classcode : sc_classList) {
							uuid = UUID.randomUUID();
							u_id = uuid.toString().replaceAll("-","");
							pre = connection.prepareStatement(sc_sql);
							pre.setString(1,u_id);
							pre.setString(2,ci.getArch_num());
							pre.setString(3,ei.getExam_num());
							pre.setString(4,sc_classcode);
							pre.executeUpdate();
							pre.close();
							
						}
					}
					
					
					System.out.println("===apptype===="+apptype+"---InfoType.FSXZYB.getCode()---"+InfoType.FSXZYB.getCode()+"===ii.getInfo_type()==="+ii.getInfo_type());
					
					//插入职业史
					int resultZys = insertSaveOccuhisZys(connection, ii.getId_num(),userid, ii.getInfo_type());
					/*if(resultZys!=1) {
						updateImpCustomer(ii.getId(), 1,deleteflag, "职业史入正式库错误");
						continue;
					}*/
					//插入既往史
					int resultJws = insertSaveOccuhisJws(connection, ii.getId_num(),userid, ii.getInfo_type());
					/*if(resultJws!=1) {
						updateImpCustomer(ii.getId(), 1,deleteflag, "既往史入正式库错误");
						continue;
					}*/
				}
				
				connection.commit();
			} catch (Exception ex) {
				try {
					connection.rollback();
					updateImpCustomer(ii.getId(), 1,deleteflag, ex.toString());

				} catch (SQLException e) {

				}
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

		}
		return message;
	}
	

	public ExamInfoDTO getExamInfoForexamnum(String exam_num) throws ServiceException {
		String sql = "select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,"
				+ "a.order_id,a.exam_times,a.is_report_print,a.company_id,a.batch_id,a.patient_id,"
				+ "a.employeeID,a.mc_no,a.visit_date,a.visit_no,a.clinic_no,a.exam_indicator,b.arch_num,b.user_name,b.id_num,"
				+ "b.sex,b.birthday,b.nation,b.phone,b.address,b.email,a.freeze "
				+ " From  exam_info a,customer_info b where  a.exam_num ='" + exam_num + "' and a.customer_id=b.id  ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getExamInfoForexamNum   
	     * @Description: 按照体检编号获取体检信息  
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoDTO      
	     * @throws
	 */
	public ExamInfoDTO getExamInfoForexamNum(String exam_num) throws ServiceException {
		String sql = "select a.id,a.group_id,a.customer_id,a.exam_num,a.status,a.register_date,a.join_date,"
				+ "a.final_date,a.final_doctor,a.exam_type,a.customer_type,a.is_sampled_directly,"
				+ "a.is_adjusted,a.center_num,a.getReportWay,a.reportAddress,a.chargingType,"
				+ "a.customerType,a.group_index,a.is_Active,a.creater,a.create_time,a.updater,"
				+ "a.update_time,a.is_guide_back,a.company_check_status,a.customer_type_id,"
				+ "a.is_marriage,a.age,a.address,a.email,a.phone,a.company,a.position,_level,"
				+ "a.picture_path,a.is_after_pay,a.past_medical_history,a.remarke,a.introducer,"
				+ "a.counter_check,a.guide_nurse,a.appointment,a.data_source,a.others,"
				+ "a.order_id,a.exam_times,a.is_report_print,a.company_id,a.batch_id,a.patient_id,"
				+ "a.employeeID,a.mc_no,a.visit_date,a.visit_no,a.clinic_no,a.exam_indicator,b.arch_num,b.user_name,b.id_num,"
				+ "b.sex,b.birthday,b.nation,b.phone,b.address,b.email "
				+ " From  exam_info a,customer_info b where  a.exam_num ='" + exam_num + "' and a.customer_id=b.id  ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}
		return webrole;
	}
	
	/**
	 * 插入档案表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public int insertCustomerInfo(Connection connection, CustomerInfoDTO cif) throws Exception {
		if("".equals(cif.getNation())){
			cif.setNation(null);
		}
		String sql = "insert into customer_info(arch_num,"
				+ "user_name,id_num,sex,birthday,nation,is_Active,creater,create_time,updater,update_time,membership_card,medical_insurance_card,phone,address,flag,idtype,born_address,center_num) "
				+ "values ('" + cif.getArch_num() + "',N'" + cif.getUser_name() + "','" + cif.getId_num() + "','"
				+ cif.getSex() + "','" + cif.getBirthday() + "'," + cif.getNation() + ",'" + cif.getIs_Active() + "',"
				+ cif.getCreater() + ",'" + DateTimeUtil.getDateTime() + "'," + cif.getUpdater() + ",'"
				+ DateTimeUtil.getDateTime() + "','" + cif.getMembership_card() + "','"
				+ cif.getMedical_insurance_card() + "','"+cif.getPhone()+"','"+cif.getAddress()+ "','"
				+ cif.getFlag() + "','"+cif.getIdtype()+"','"+cif.getBorn_address()+"','"+cif.getCenter_num()+"')";

		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		ResultSet rs = null;
		rs = preparedStatement.getGeneratedKeys();
		int retId = 0;
		if (rs.next())
			retId = rs.getInt(1);
		else
			throw new Exception("insert or generate keys failed..");
		rs.close();
		preparedStatement.close();
		return retId;
	}

	// 插入就诊卡信息表
	public int insertCustomerVisitNo(Connection connection, CustomerInfoDTO cif) throws Exception {
		String sql = "insert into customer_visit_card(customer_id,"
				+ "visit_no,visit_status,creater,create_time,updater,update_time) " + "values ('" + cif.getId() + "','"
				+ cif.getVisit_no() + "','Y'," + cif.getCreater() + ",'" + DateTimeUtil.getDateTime() + "',"
				+ cif.getUpdater() + ",'" + DateTimeUtil.getDateTime() + "')";

		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		ResultSet rs = null;
		rs = preparedStatement.getGeneratedKeys();
		int retId = 0;
		if (rs.next())
			retId = rs.getInt(1);
		else
			throw new Exception("insert or generate keys failed..");
		rs.close();
		preparedStatement.close();
		return retId;
	}

	/**
	 * 修改档案表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public void updateCustomerInfo(Connection connection, CustomerInfoDTO cif) throws Exception {
		if("".equals(cif.getNation())){
			cif.setNation(null);
		}
		String sql = "update customer_info set user_name=N'" + cif.getUser_name() + "',sex='" + cif.getSex()
				+ "',birthday='" + cif.getBirthday() + "',nation=" + cif.getNation() + ",is_Active='"
				+ cif.getIs_Active() + "',id_num='" + cif.getId_num() + "',phone='" + cif.getPhone() + "',address='"
				+ cif.getAddress() + "',creater=" + cif.getCreater() + ",create_time='" + DateTimeUtil.getDateTime()
				+ "',updater=" + cif.getUpdater() + ",update_time='" + DateTimeUtil.getDateTime() + "',idtype='"+cif.getIdtype()+"'" 
				+ "  where id='"+cif.getId() + "'";
		connection.createStatement().executeUpdate(sql);
	}

	/**
	 * 插入体检表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public int insertExamInfo(Connection connection, ExamInfoDTO cif) throws Exception {
		String sql = "insert into exam_info(group_id,customer_id,exam_num,"
				+ "exam_type,is_Active,center_num,is_marriage,age,address,phone,register_date,"
				+ "is_after_pay,appointment,data_source,creater,create_time,updater,"
				+ "update_time,status,is_sampled_directly,getReportWay,"
				+ "_level,Position,Customer_type,Remarke,Others,group_index,company,customer_type_id,"
				+ "chargingType,reportAddress,email,company_id,batch_id,past_medical_history,picture_path,"
				+ "employeeID,introducer,visit_no,visit_date,apptype,zip,degreeOfedu,zyb_set_source,comon_report_type,exam_indicator,wuxuzongjian) " + "values (" + cif.getGroup_id() + ","
				+ cif.getCustomer_id() + ",'" + cif.getExam_num() + "','" + cif.getExam_type() + "','"
				+ cif.getIs_Active() + "','" + cif.getCenter_num() + "','" + cif.getIs_marriage() + "'," + cif.getAge()
				+ ",'" + cif.getAddress() + "','" + cif.getPhone() + "','" +DateTimeUtil.getDateTime() + "','" + cif.getIs_after_pay() + "','"
				+ cif.getAppointment() + "','" + cif.getData_source() + "'," + cif.getCreater() + ",'"
				+ DateTimeUtil.getDateTime() + "'," + cif.getUpdater() + ",'" + DateTimeUtil.getDateTime() + "','"
				+ cif.getStatus() + "','" + cif.getIs_sampled_directly() + "','"
				+ cif.getGetReportWay() + "'" + ",'" + cif.get_level() + "','" + cif.getPosition() + "','"
				+ cif.getCustomer_type() + "','" + cif.getRemarke() + "','" + cif.getOthers() + "','" + cif.getGroup_index() + "','" + cif.getCompany() + "',"
				+ cif.getCustomer_type_id()+",'" + cif.getChargingType() + "','" + cif.getReportAddress() 
				+ "','"+cif.getEmail()+"',"+cif.getCompany_id()+","+cif.getBatch_id()+",'"+cif.getPast_medical_history()+"','"+cif.getPicture_path()+"','"
				+cif.getEmployeeID()+"','"+cif.getIntroducer()+"','"+cif.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','"+cif.getApptype()+"','"+cif.getZip()+"','"+cif.getDegreeOfedu()+"','"+cif.getZyb_set_source()+"','"+cif.getComon_report_type()+"','"+cif.getExam_indicator()+"',"+cif.getWuxuzongjian()+")";

		PreparedStatement preparedStatement = null;
		preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.executeUpdate();
		ResultSet rs = null;
		rs = preparedStatement.getGeneratedKeys();
		int retId = 0;
		if (rs.next()){
			retId = rs.getInt(1);
		    String synstr="delete web_synchro where dataid ='"+cif.getExam_num()+"' and datatype='5'";
		    connection.createStatement().executeUpdate(synstr);
		    synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+cif.getExam_num()+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
		    connection.createStatement().executeUpdate(synstr);
		}else
			throw new Exception("insert or generate keys failed..");
		rs.close();
		preparedStatement.close();
		return retId;
	}
	
	/**
	 * 插入体检表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public void updateExamInfo(Connection connection, ExamInfoDTO cif) throws Exception {
		String sql = "update exam_info set customer_id="
				+ cif.getCustomer_id() + ",exam_num='" + cif.getExam_num() + "',"
				+ "exam_type='" + cif.getExam_type() + "',is_Active='"
				+ cif.getIs_Active() + "',center_num='" + cif.getCenter_num() + "',is_marriage='" + cif.getIs_marriage() + "',age=" + 
				cif.getAge()
				+ ",address='" + cif.getAddress() + "',phone='" + cif.getPhone() + "',"
				+ "is_after_pay='" + cif.getIs_after_pay() + "',appointment='"
				+ cif.getAppointment() + "',data_source='" + cif.getData_source() + "',creater=" + cif.getCreater() + ",create_time='"
				+ DateTimeUtil.getDateTime() + "',updater=" + cif.getUpdater() + ","
				+ "update_time='" + DateTimeUtil.getDateTime() + "',status='"
				+ cif.getStatus() + "',is_sampled_directly='" 
				+ cif.getIs_sampled_directly() + "',getReportWay='"+ cif.getGetReportWay() + "',"
				+ "_level='" + cif.get_level() + "',Position='" + cif.getPosition() + "',Customer_type='"
				+ cif.getCustomer_type() + "',Remarke='" + cif.getRemarke() + "',Others='" + cif.getOthers() + "',group_index='"+cif.getGroup_index()+"'"
				+ ",customer_type_id="+cif.getCustomer_type_id()+",chargingType='" 
				+ cif.getChargingType() + "',reportAddress='" + cif.getReportAddress() + "',email='"+cif.getEmail()+"'"
				+ ",past_medical_history='"+cif.getPast_medical_history()+"',picture_path='"+cif.getPicture_path()+"',employeeID='"+cif.getEmployeeID()+"',wuxuzongjian=" + cif.getWuxuzongjian() +" where id="+cif.getId();

		connection.createStatement().executeUpdate(sql);
		
		int isUpload = djtCustomerInfoService.queryIsUploadSynach(connection,cif.getExam_num());
		if(isUpload==1) {
			String synstr="delete web_synchro where dataid ='"+cif.getId()+"' and datatype='5'";
			connection.createStatement().executeUpdate(synstr);
			synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+cif.getId()+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
			connection.createStatement().executeUpdate(synstr);
		}
		
		
		
	}

	

	private void updateImpCustomer(long id, int flags, boolean updateflag, String notices) throws ServiceException {
		if (updateflag) {
			StringBuffer sql = new StringBuffer("update impcustomerInfo  set flags=" + flags + ",notices='" + notices
					+ "'," + "create_time='" + Timeutils.getNowDate() + "' where id=" + id + " ");
			Connection connection = null;
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.createStatement().executeUpdate(sql.toString());
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
		}
	}

	private ExamInfoDTO getExamInfoForArchIdList(String arch_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where arch_num ='" + arch_num
				+ "'  and is_Active='Y'  ";
		System.out.println(sql);
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}

		return webrole;
	}

	private ExamInfoDTO getExamInfoForIdNum(String id_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where id_num ='" + id_num
				+ "' and is_Active='Y' ";
		System.out.println(sql);
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}

		return webrole;
	}

	public List<ExamInfoDTO> getExamInfoForIdNumList(String id_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where id_num ='" + id_num
				+ "'  and is_Active='Y'  ";
		List<ExamInfoDTO> list = new ArrayList<ExamInfoDTO>();
		System.out.println(sql);
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			list = map.getList();
		}

		return list;
	}

	/**
	 * 
	 * @Title: getcustomerTypeIdName @Description: 根据名称获取人员类型id @param: @param
	 * id_num @param: @return @param: @throws ServiceException @return:
	 * CustomerTypeDTO @throws
	 */
	public CustomerTypeDTO getcustomerTypeName(String names) throws ServiceException {
		String sql = "SELECT id,type_name,type_code,type_comment,"
				+ "creater,create_time,updater,update_time FROM customer_type where type_name='" + names
				+ "'";
		PageSupport map = this.jqm.getList(sql, 1, 1000, CustomerTypeDTO.class);
		System.out.println(sql);
		CustomerTypeDTO webrole = new CustomerTypeDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (CustomerTypeDTO) map.getList().get(0);
		}
		return webrole;
	}
	
	
	/**
	 * 
	     * @Title: getChargingItemForId   
	     * @Description: id获取ChargingItemDTO  
	     * @param: @param chargingid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItemDTO      
	     * @throws
	 */
	public ChargingItemDTO getChargingItemForId(String chargingCode) throws ServiceException{
		String sql = "select id,dep_id,sam_demo_id,sam_report_demo_id,item_code,item_name,"
				+ "item_pinyin,item_category,sex,amount,dep_category,isOnlyApplyOrReport,"
				+ "item_seq,guide_category,his_num,exam_num,view_num,isActive,creater,"
				+ "create_time,updater,update_time,calculation_amount,interface_flag,"
				+ "item_type,charge_inter_num,item_abbreviation,notices from charging_item where item_code='"+chargingCode+"'";
		PageSupport map = this.jqm.getList(sql, 1, 1000, ChargingItemDTO.class);
		ChargingItemDTO webrole = new ChargingItemDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ChargingItemDTO) map.getList().get(0);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getcustomerTypeIdName   
	     * @Description: 根据名称获取人员类型id   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerTypeDTO      
	     * @throws
	 */
	public CustomerTypeDTO getcustomerTypeId(String id) throws ServiceException {
		String sql = "SELECT id,type_name,type_code,type_comment,"
				+ "creater,create_time,updater,update_time FROM customer_type where id='"+id+"' ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, CustomerTypeDTO.class);
		CustomerTypeDTO webrole = new CustomerTypeDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (CustomerTypeDTO) map.getList().get(0);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getcustomerTypeId   
	     * @Description: 根据名称获取人员类型id   
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: CustomerTypeDTO      
	     * @throws
	 */
	public CustomerTypeDTO getcustomerTypeId(long id) throws ServiceException {
		String sql = "SELECT id,type_name,type_code,type_comment,"
				+ "creater,create_time,updater,update_time FROM customer_type where id="+id+" ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, CustomerTypeDTO.class);
		CustomerTypeDTO webrole = new CustomerTypeDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (CustomerTypeDTO) map.getList().get(0);
		}
		return webrole;
	}

	/**
	 * 
	 * @Title:
	 * getcustomerType @Description:获取人员类型的的list @param: @return @param: @throws
	 * ServiceException @return: List<ImpCustomerInfoDTO> @throws
	 */
	@SuppressWarnings("unchecked")
	public List<JobDTO> getcustomerType() throws ServiceException {
		List<JobDTO> webrole = new ArrayList<JobDTO>();
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			String sql = "select id,type_name FROM customer_type";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				JobDTO jb = new JobDTO();
				jb.setId(rs.getString("id"));
				jb.setName(rs.getString("type_name"));
				webrole.add(jb);
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
		return webrole;
	}

	
	
	// ----------------------------------------私有方法结束-------------------------------------------------

	/**
	 * 
	 * @Title: getExamInfoUserList @Description: 综合查询人员、分组信息 @param: @param
	 *         company_Id @param: @param batch @param: @param
	 *         deptid @param: @param group_id @param: @param
	 *         id_num @param: @param art_num @param: @param
	 *         username @param: @param tel @param: @param
	 *         pagesize @param: @param pageno @param: @return @param: @throws
	 *         ServiceException @return: PageReturnDTO @throws
	 */
	public PageReturnDTO getExamInfoUserList(long com_id,long batch, long deptid, long group_id, String id_num, String art_num,
			String username, String tel,String statas,String regdate, String startDate,String endDate,String employeeID,String sex,String is_marriage,long rylb,String djdstatus,String centernum,String apptype, int pagesize, int pageno,String sort,String order,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append("select da.data_name,c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,c.is_upload,c.is_report_upload,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.employeeID,c.visit_no");
		//sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 21)) as wpacs ");
		//sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 21)) as ypacs ");
		//sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 131)) as wlis ");
		//sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 131)) as ylis ");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,"
				+ "c.status,n.group_name,m.dep_name,"
				+ "c.register_date,c.exam_times,x.type_name as customer_type_name,("
				+ "select sum(eci.personal_pay) from examinfo_charging_item eci where eci.isActive='Y' "
				+ "  and (eci.exam_indicator='G' or  eci.exam_indicator='GT')  and eci.pay_status<>'M' and eci.examinfo_id=c.id) as personal_pay,("
				+ "select sum(eci.team_pay) from examinfo_charging_item eci where eci.isActive='Y' "
				+ " and (eci.exam_indicator='T' or  eci.exam_indicator='GT')  and eci.pay_status<>'M' and eci.examinfo_id=c.id) as team_pay  ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join customer_type x on x.id=c.customer_type_id ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join data_dictionary  da  on  da.id = c.customer_type   and  data_code='TJLX'    ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.apptype='"+apptype+"' ");
		sb.append(" and  c.company_id=" + com_id + " ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		if(batch<=0){
			sb.append(" and  c.batch_id=-100 ");
		}else{
			sb.append(" and  c.batch_id="+batch+" ");
		}
		
		if (group_id > 0) {
			sb.append(" and c.group_id=" + group_id + " ");
		}
		if (deptid > 0) {
			sb.append(" and c._level=" + deptid + " ");
		}
		
		if(!StringUtil.isEmpty(employeeID)){
			sb.append(" and c.employeeID like '%" + employeeID + "%' ");
		}
		
		if(!StringUtil.isEmpty(statas)){
			sb.append(" and c.status in ('" + statas + "') ");
		}
		
		if(!StringUtil.isEmpty(djdstatus)){
			if("Y".equals(djdstatus)){
				sb.append(" and c.is_need_guide = 'Y' ");
			}else{
				sb.append(" and (c.is_need_guide='N' or c.is_need_guide is null) ");
			}
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(id_num)) {
			sb.append(" and a.id_num like '%" + id_num + "%' ");
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
		}
		
		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}		

		if (!StringUtil.isEmpty(tel)) {
			sb.append(" and c.phone= '" + tel + "' ");
		}
		
		if(!StringUtil.isEmpty(is_marriage)){			
			sb.append(" and c.is_marriage = '" + is_marriage + "' ");
		}
		
		if (rylb>0) {
			sb.append(" and c.customer_type_id= '" + rylb + "' ");
		}
		
		if (startDate != null && !"".equals(startDate)) {// 体检开始日期
			sb.append(" and  c.join_date  >= '" + startDate+"'" );
		} 
		if (endDate != null && !"".equals(endDate)) {// 体检结束日期
			sb.append(" and  c.join_date < '" + endDate +"'");
		} 
		
		if (regdate != null && !"".equals(regdate)) {
			sb.append(" and c.register_date= '" + regdate + " 23:59:59' ");
		}
		
		
		if( null != sort && order!=null && !"".equals(sort) && !"".equals(order)){
			if("group_name".equals(sort)){
				sb.append(" order by n.group_name "+order);
			}else{
				sb.append(" order by "+sort+" "+order);
			}
		}else{
			sb.append(" order by c.exam_num desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist=new ArrayList<ExaminfoSetDTO>();
				batchlist=this.getExamSetForExamNum(eu.getExam_num(),centernum);
				if ((batchlist != null) && (batchlist.size())>0){
					for (ExaminfoSetDTO es:batchlist) {						
						setnums = setnums + ","+ es.getSet_name();
					}
					if(setnums.length()>1) setnums=setnums.trim().substring(1,setnums.trim().length());
					eu.setSet_name(setnums);
				}
				
				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){			
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if("1900-01-01".equals(eu.getRegister_date()))
					{
						eu.setRegister_date("");
					}
				}                
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				eu.setLis(eu.getYlis()+"/"+eu.getWlis());
				eu.setPacs(eu.getYpacs()+"/"+eu.getWpacs());				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoForNum(String exam_num,UserDTO user) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,m.dep_name,"
				+ "c.register_date,c.join_date,c.exam_times,c.batch_id,c.freeze ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_num = c.exam_num and j.center_num = '"+user.getCenter_num()+"'  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");		
		sb.append(" and c.exam_num = '" + exam_num + "' ");	

		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, ExamInfoUserDTO.class);
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if((map!=null)&&(map.getList().size()>0)){
			eu= (ExamInfoUserDTO)map.getList().get(0);
			if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
				eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
				if(eu.getRegister_date().indexOf("1900-01-01")>=0)
				{
					eu.setRegister_date("");
				}
			}
			
			if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
				eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
				if(eu.getJoin_date().indexOf("1900-01-01")>=0)
				{
					eu.setJoin_date("");
				}
			}
		}
		return eu;
	}
	
	
	/**
	 * 
	     * @Title: getDjtExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param art_num
	     * @param: @param exam_num
	     * @param: @param username
	     * @param: @param starttime
	     * @param: @param enddate
	     * @param: @param centerid
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfoUserList(String data_source,long com_id,long set_id,int  ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String jdstatuss,String apptype,String userid,String centernum,RegisterModel model, int pagesize, int pageno,String sort,String order,UserDTO user,String center_num) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if(pageno<=0)
			pageno=1;
		int startindex=pagesize*(pageno-1);
		int endindex=pagesize*(pageno);		
				
		sb.append("SELECT * FROM(SELECT TOP "+endindex+" ROW_NUMBER() OVER(");
		
		if(sort != null && !"".equals(sort)){
			sb.append(" order by "+sort+" "+order);
		}else{
			sb.append(" order by update_time desc ");
		}
		
		sb.append(") AS ROWID,* FROM (");		
		
		sb.append(" select c.wuxuzongjian,c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,"
				+ "a.arch_num,dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage, c.is_upload, c.is_report_upload,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active,j.check_time"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.final_date,c.create_time,c.freeze,"
//				+ " ( select sum(eci.personal_pay) from examinfo_charging_item eci where eci.isActive='Y' "
//				+ " and eci.exam_indicator like 'G%' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as personal_pay,("
//				+ "select sum(eci.team_pay) from examinfo_charging_item eci where eci.isActive='Y' "
//				+ "and eci.exam_indicator like '%T' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as team_pay ,"
				+ "dbo.GetPersonalPayByExamId(c.exam_num) as personal_pay,dbo.GetTeamPayByExamId(c.exam_num) as team_pay,dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,c.batch_id,"
				+ "c.register_date,c.join_date,x.chi_name,c.is_guide_back,c.update_time,c.introducer ");
		sb.append(" ,(select max(eci.exam_date) from examinfo_charging_item eci where eci.exam_num = c.exam_num and "
				+ "eci.exam_status = 'Y' and eci.isActive = 'Y')as exam_times,"
				+ "dd.data_name as customer_type_name ,uu.chi_name as join_operatorName,c.customer_type_id ");
		
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join user_usr uu on uu.id=c.join_operator ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_num = c.exam_num  and j.app_type=c.apptype and  j.center_num = '"+center_num+"'  ");
		sb.append(" left join data_dictionary dd on dd.id = c.customer_type  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.apptype='"+apptype+"' ");
		sb.append(" and  c.center_num='" + centernum + "' ");		
		sb1.append("select count(*) as counts from customer_info a ,exam_info c");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y'  and c.apptype='"+apptype+"'");
		sb1.append(" and  c.center_num='" + centernum + "' ");	
		
		if (com_id > 0) {
			sb.append(" and c.company_id=" + com_id + " ");
			sb1.append(" and c.company_id=" + com_id + " ");
		}
		
		if(model.getBatch_id() >0){
			sb.append(" and c.batch_id=" + model.getBatch_id());
			sb1.append(" and c.batch_id=" + model.getBatch_id());
		}
		
		if (set_id > 0) {
			sb.append(" and " + set_id + " in (select exam_set_id from examinfo_set where examinfo_id = c.id) ");
		}
		
		if (!StringUtil.isEmpty(model.getLevels())) {
			sb.append(" and c._level in(" + model.getLevels() + ") ");
			sb1.append(" and c._level in(" + model.getLevels() + ") ");
		}
		
		if (!StringUtil.isEmpty(jdstatuss)) {
			if ("Y".equals(jdstatuss)) {
				sb.append(" and c.is_guide_back = 'Y' ");
				sb1.append(" and c.is_guide_back = 'Y' ");
			} else {
				sb.append(" and (c.is_guide_back='N' or c.is_need_guide is null) ");
				sb1.append(" and (c.is_guide_back='N' or c.is_need_guide is null) ");
			}
		}
		
		if (!StringUtil.isEmpty(model.getCustomer_type())) {
			sb.append(" and c.customer_type = '" + model.getCustomer_type() + "' ");
			sb1.append(" and c.customer_type = '" + model.getCustomer_type() + "' ");
		}
		
		//数据来源
		if(!StringUtil.isEmpty(data_source)){
			sb.append(" and c.data_source = '" + data_source + "' ");
			sb1.append(" and c.data_source = '" + data_source + "' ");
		}
		
		if(!StringUtil.isEmpty(exam_num)){
			sb.append(" and c.exam_num = '" + exam_num + "' ");
			sb1.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if(!StringUtil.isEmpty(id_num)){
			sb.append(" and a.id_num = '" + id_num + "' ");
			sb1.append(" and a.id_num = '" + id_num + "' ");
		}
		
		if (!StringUtil.isEmpty(employeeID)) {
			sb.append(" and c.employeeID = '" + employeeID + "' ");
			sb1.append(" and c.employeeID = '" + employeeID + "' ");
		}
		//获取姓名是否需要精确查询，N表示模糊，Y表示精确
		String is_jq = "";
		try {
			is_jq = this.customerInfoService.getCenterconfigByKey("TEAM_DJD_NAME_QUERY", centernum).getConfig_value().trim();
		} catch (Exception e) {
			System.out.println("缺少配置TEAM_DJD_NAME_QUERY");
			e.printStackTrace();
		}
		if (!StringUtil.isEmpty(username)) {
			if(is_jq.equals("Y") || is_jq=="Y"){
				sb.append(" and a.user_name = '" + username + "' ");
				sb1.append(" and a.user_name = '" + username + "' ");
			}else{
				sb.append(" and a.user_name like '%" + username + "%' ");
				sb1.append(" and a.user_name like '%" + username + "%' ");
			}
			
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
			sb1.append(" and a.arch_num = '" + art_num + "' ");
		}

		if ((!StringUtil.isEmpty(starttime))&&(!StringUtil.isEmpty(enddate))) {
			sb.append(" and c.join_date >= '" + starttime + " 00:00:00' ");
			sb.append(" and c.join_date <= '" + enddate + " 23:59:59' ");
			
			sb1.append(" and c.join_date >= '" + starttime + " 00:00:00' ");
			sb1.append(" and c.join_date <= '" + enddate + " 23:59:59' ");
		}
		
		if(!StringUtil.isEmpty(model.getYuyue_date1())&&!StringUtil.isEmpty(model.getYuyue_date2())){
			sb.append(" and c.register_date  >= '" + model.getYuyue_date1() + " 00:00:00' ");
			sb.append(" and c.register_date <= '" + model.getYuyue_date2() + " 23:59:59' ");
			
			sb1.append(" and c.register_date >= '" + model.getYuyue_date1() + " 00:00:00' ");
			sb1.append(" and c.register_date <= '" + model.getYuyue_date2() + " 23:59:59' ");
		}
		
		if ((!StringUtil.isEmpty(time3))&&(!StringUtil.isEmpty(time4))) {
			sb.append(" and c.final_date >= '" + time3 + " 00:00:00' ");
			sb.append(" and c.final_date <= '" + time4 + " 23:59:59' ");
			sb.append(" and c.status = 'Z' ");
			
			sb1.append(" and c.final_date >= '" + time3 + " 00:00:00' ");
			sb1.append(" and c.final_date <= '" + time4 + " 23:59:59' ");
			sb1.append(" and c.status = 'Z' ");
		}else{
			if (!StringUtil.isEmpty(status)) {
				sb.append(" and c.status = '" + status + "' ");
				sb1.append(" and c.status = '" + status + "' ");
			}
		}
		
		if(!StringUtil.isEmpty(createtime)){
			sb.append(" and c.create_time = '" + createtime + " 23:59:59' ");
			sb1.append(" and c.create_time = '" + createtime + " 23:59:59' ");
		}
		
		if(model.getCreater() > 0){
			sb.append(" and c.creater = '" + model.getCreater()+"'");
			sb1.append(" and c.creater = '" + model.getCreater()+"'");
		}
		
		if(!StringUtil.isEmpty(examtype)){
			sb.append(" and c.exam_type = '" + examtype + "' ");
			sb1.append(" and c.exam_type = '" + examtype + "' ");
		}
		if(ren_type>0){
			sb.append(" and c.customer_type_id = '" + ren_type + "' ");			
			sb1.append(" and c.customer_type_id = '" + ren_type + "' ");
		}
		if (model.getBatch_id() > 0) {
			sb.append(" and   c.batch_id = "+model.getBatch_id());
			sb1.append(" and   c.batch_id = "+model.getBatch_id());
		}
		if (!StringUtil.isEmpty(model.getSex())) {
        	sb.append(" and  a.sex = '"+model.getSex()+"'");
        	sb1.append(" and  a.sex = '"+model.getSex()+"'");
		}
		if (model.getWuxuzongjian() == 1) {
        	sb.append(" and  c.wuxuzongjian = '"+model.getWuxuzongjian()+"'");
        	sb1.append(" and  c.wuxuzongjian = '"+model.getWuxuzongjian()+"'");
        }
		
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>"+startindex+"");
		PageSupport map = this.jqm.getList(sb1.toString(),sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("登记台首页-体检人员列表", sb.toString());
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist=new ArrayList<ExaminfoSetDTO>();
				batchlist=this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size())>0){
					for (ExaminfoSetDTO es:batchlist) {						
						setnums = setnums + ","+ es.getSet_name();
					}
					if(setnums.length()>1) setnums=setnums.trim().substring(1,setnums.trim().length());
					eu.setSet_name(setnums);
				}
				
				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if(eu.getRegister_date().indexOf("1900-01-01")>=0)
					{
						eu.setRegister_date("");
					}
				}
				
				if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if(eu.getJoin_date().indexOf("1900-01-01")>=0)
					{
						eu.setJoin_date("");
					}
				}
               
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				
				eu.setLis(eu.getYlis()+"/"+eu.getWlis());
				eu.setPacs(eu.getYpacs()+"/"+eu.getWpacs());				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	
	/**
	 * 
	     * @Title: getDjtExamInfoUserListall   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param art_num
	     * @param: @param exam_num
	     * @param: @param username
	     * @param: @param starttime
	     * @param: @param enddate
	     * @param: @param centerid
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfoUserListall(long com_id,long batch_id,int ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String apptype,String userid,String centernum, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
//		sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 21)) as wpacs ");
//		sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 21)) as ypacs ");
//		sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 131)) as wlis ");
//		sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 131)) as ylis ");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,m.dep_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.apptype,c.customer_type_id ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and  j.center_num = '"+center_num+"' ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and  c.center_num='" + centernum + "' ");		
		
		if(!StringUtil.isEmpty(apptype)){
				sb.append(" and c.apptype = '" + apptype + "' ");
			}
			
		if (com_id > 0) {
			sb.append(" and c.company_id=" + com_id + " ");
		}
		
		if(batch_id > 0){
			sb.append(" and c.batch_id=" + batch_id + " ");
		}
		
		if(!StringUtil.isEmpty(exam_num)){
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if(!StringUtil.isEmpty(id_num)){
			sb.append(" and a.id_num = '" + id_num + "' ");
		}
		
		if (!StringUtil.isEmpty(employeeID)) {
			sb.append(" and c.employeeID like '%" + employeeID + "%' ");
		}
		
		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
		}
		if(ren_type>0){
			sb.append(" and c.customer_type_id='"+ren_type+"'");
		}
		if ((!StringUtil.isEmpty(starttime))&&(!StringUtil.isEmpty(enddate))) {
			sb.append(" and c.join_date >= '" + starttime + " 00:00:00' ");
			sb.append(" and c.join_date <= '" + enddate + " 23:59:59' ");
		}
		
		if ((!StringUtil.isEmpty(time3))&&(!StringUtil.isEmpty(time4))) {
			sb.append(" and c.final_date >= '" + time3 + " 00:00:00' ");
			sb.append(" and c.final_date <= '" + time4 + " 23:59:59' ");
			sb.append(" and c.status = 'Z' ");
		}else{
			if (!StringUtil.isEmpty(status)) {
				sb.append(" and c.status = '" + status + "' ");
			}
		}
		
		if(!StringUtil.isEmpty(createtime)){
			sb.append(" and c.create_tim = '" + createtime + " 23:59:59' ");
		}
		
		if(!StringUtil.isEmpty(examtype)){
			sb.append(" and c.exam_type = '" + examtype + "' ");
		}
		
		if(sort != null && !"".equals(sort)){
			sb.append(" order by "+sort+" "+order);
		}else{
			sb.append(" order by c.update_time desc ");
		}
		
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist=new ArrayList<ExaminfoSetDTO>();
				batchlist=this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size())>0){
					for (ExaminfoSetDTO es:batchlist) {						
						setnums = setnums + ","+ es.getSet_name();
					}
					if(setnums.length()>1) setnums=setnums.trim().substring(1,setnums.trim().length());
					eu.setSet_name(setnums);
				}
				
				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if(eu.getRegister_date().indexOf("1900-01-01")>=0)
					{
						eu.setRegister_date("");
					}
				}
				
				if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if(eu.getJoin_date().indexOf("1900-01-01")>=0)
					{
						eu.setJoin_date("");
					}
				}
               
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				
				eu.setLis(eu.getYlis()+"/"+eu.getWlis());
				eu.setPacs(eu.getYpacs()+"/"+eu.getWpacs());				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getExamSetForExamid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoSetDTO>      
	     * @throws
	 */
    public List<ExaminfoSetDTO> getExamSetForExamNum(String exam_num,String center_num) throws ServiceException {
    	List<ExaminfoSetDTO> list=new ArrayList<ExaminfoSetDTO>();
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT a.id,a.examinfo_id,a.exam_set_id,a.exam_indicator,a.discount,a.amount,a.isActive,a.final_exam_date");
    	sb.append(",a.is_new_added,a.creater,a.create_time,a.updater,a.update_time,x.set_num,x.set_name");
    	sb.append(" FROM examinfo_set a");
    	sb.append(" left join exam_set x on x.id=a.exam_set_id");
    	sb.append(" where a.isActive='Y' and a.exam_num= '"+exam_num+"' and x.center_num='"+center_num+"'");
		list=this.jqm.getList(sb.toString(),ExaminfoSetDTO.class);
		return list;
    }
	
	/**
	 * 
	     * @Title: getDjtExamInfobaodaoList   
	     * @Description: 按照身份证号查询未报到人员列表  
	     * @param: @param in_num
	     * @param: @param centerid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getDjtExamInfobaodaoList(String in_num,String centernum) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,c.status,n.group_name,m.dep_name,"
				+ "c.register_date,c.update_time,c.exam_times ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and j.center_num = '"+centernum+"'");
		sb.append(" where c.customer_id=a.id  ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and c.status='Y' ");
		sb.append(" and c.center_num='" + centernum + "' ");		
		sb.append(" and a.id_num='" + in_num + "' ");
		sb.append(" order by c.update_time desc ");
		
		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(1);
		webrole.setRp(10000);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);

				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if("1900-01-01".equals(eu.getRegister_date()))
					{
						eu.setRegister_date("");
					}
				}
			
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: doAllUserGroup @Description: 批量执行存储过程 @param: @param
	 * batchid @param: @param user_id @param: @param
	 * center_id @param: @return @return: int @throws
	 */
	public int doAllUserGroup(long batchid, String user_id, String centernum) throws ServiceException {
		int typeid = GetNumContral.getInstance().pro_exam_autoGroup(batchid, user_id, centernum);
		return typeid;
	}

	/**
	 * 
	 * @Title: dotUserGroup @Description: 部分人员执行存储过程 @param: @param
	 * group_id @param: @param user_id @param: @param
	 * center_id @param: @return @return: int @throws
	 */
	public int dotUserGroup(long group_id, String user_id, String centernum, String examNums) throws ServiceException {
		int typeid = GetNumContral.getInstance().pro_exam_enforceGroup(group_id, user_id, centernum, examNums);		
		return typeid;
	}

	/**
	 * 
	     * @Title: getDiversforByCode   
	     * @Description: 获取设备编号  
	     * @param: @param code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DiversInfoDTO      
	     * @throws
	 */
	public DiversInfoDTO getDiversforByCode(String code) throws ServiceException {
		String sql = "SELECT code,com_name,com_type,com_type_name,com_ocx_name,is_active,remark"
				+ "  FROM driver_info where code='" + code + "' and is_active='Y' ";
		PageSupport map = this.jqm.getList(sql, 1, 50000, DiversInfoDTO.class);
		DiversInfoDTO list = new DiversInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			list = (DiversInfoDTO) map.getList().get(0);
		}
		return list;
	}
	
	/**
	 * 根据config_key查询到多条时的获取顺序：
	 * 1，center_configuration表中center_num和入参中center_num匹配的
	 * 2，center_configuration表中center_num为000的
	 * 3，center_configuration表中center_num为空串或null的
	 */
	public CenterConfigurationDTO getCenterconfigByKey(String config_key, String center_num) throws ServiceException {
		String sql = "SELECT * FROM center_configuration where config_key='" + config_key + "' and is_active='Y' order by center_num desc ";
		List<CenterConfigurationDTO> list = this.jqm.getList(sql, CenterConfigurationDTO.class);
		if(list == null || list.size() == 0) {
			System.out.println("配置库 center_configuration 缺少 "+config_key+" 配置！！！");
			return null;
		}
		if(center_num == null){
			return list.get(0);
		}
		for(CenterConfigurationDTO ccd : list) {
			if(center_num.equals(ccd.getCenter_num())) {
				return ccd;
			} else if("000".equals(ccd.getCenter_num())) {
				return ccd;
			} else if(StringUtil.isEmpty(ccd.getCenter_num())) {
				return ccd;
			}
		}
		System.out.println("配置库 center_configuration "+config_key+" 配置center_num错误！！！");
		return null;
	}

	/**
	 * 
	 * @Title: SendApplyChargeItem @Description: 发送申请-lis -pacs @param: @param
	 * batch_id @param: @param username @param: @throws
	 * ServiceException @return: void @throws
	 */
	public String sendApplyChargeItem(long batch_id, String username, String[] ides, String center_num) throws ServiceException {
		
		String lisflag = getCenterconfigByKey("IS_LIS_INTERFACE", center_num).getConfig_value().trim();
		String pacsflag = getCenterconfigByKey("IS_PACS_INTERFACE", center_num).getConfig_value().trim();
		if(("Y".equals(lisflag))||("Y".equals(pacsflag))){
		String webUrl = getCenterconfigByKey("WEB_URL", center_num).getConfig_value().trim();
		Client _client = null;
		HttpURLConnection httpConnection = null;
		try {

			URL _url = new URL(webUrl);
			httpConnection = (HttpURLConnection) _url.openConnection();
			httpConnection.setConnectTimeout(CommSet.webservice_timeout);
			;// 设置http连接的读超时,单位是毫秒
			httpConnection.connect();
			_client = new Client(httpConnection.getInputStream(), null);
			_client.setProperty(CommonsHttpMessageSender.HTTP_TIMEOUT, String.valueOf(CommSet.webservice_timeout));// 设置发送的超时限制,单位是毫秒;
			_client.setProperty(CommonsHttpMessageSender.DISABLE_KEEP_ALIVE, "true");
			_client.setProperty(CommonsHttpMessageSender.DISABLE_EXPECT_CONTINUE, "true");

			Connection connection = null;
			Statement statement = null;
			for (int i = 0; i < ides.length; i++) {
				long exam_info_id = Long.valueOf(ides[i]);

				try {
					// 读取记录数
					StringBuffer sb = new StringBuffer();
					sb.append(
							"select n.id,a.user_name,a.arch_num,a.id_num,a.sex,a.birthday,n.age,n.exam_num,n.is_marriage,a.phone,a.address"
									+ " from customer_info a,exam_info n where  " + " n.id=" + exam_info_id
									+ " and n.is_Active='Y' and n.customer_id=a.id and a.is_Active='Y' ");
					connection = this.jqm.getConnection();
					statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(sb.toString());
					if (rs.next()) {
						LisUserDTO lis = new LisUserDTO();
						lis.setExam_info_id(rs.getLong("id"));
						lis.setAge(rs.getString("age"));
						lis.setArch_num(rs.getString("arch_num"));
						lis.setBirthday(rs.getString("birthday"));
						lis.setExam_num(rs.getString("exam_num"));
						lis.setId_num(rs.getString("id_num"));
						lis.setSex(rs.getString("sex"));
						lis.setUser_name(rs.getString("user_name"));
						lis.setAddress(rs.getString("address"));
						lis.setPhone(rs.getString("phone"));
						if("Y".equals(lisflag)){
							getLisInterface(lis, username, _client, false, center_num);
						}
						if("Y".equals(pacsflag)){
							getPacsInterface(lis, username, _client, false, center_num);
						}
					}
					rs.close();
				} catch (Exception ex) {
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

			}
			return "ok";
		} catch (Exception ex) {
			return "error-webservice:" + ex.toString();
		} finally {
			if (_client != null) {
				_client.close();
			}
			if (httpConnection != null) {
				httpConnection.disconnect();
			}
		}
		}else{
			return "error-接口设置无效不能调用lis或者pacs";
		}
	}

	/**
	 * 
	 * @Title: getLisInterface @Description: 调用接口LIS申请 @param: @param
	 * lis @param: @param username @param: @param is_appflag
	 * true表示is_application＝Y 时候继续部分，false
	 * 表示is_application=Y的时候不在发送， @param: @return @param: @throws
	 * Exception @return: boolean @throws
	 */
	private void getLisInterface(LisUserDTO lis, String username, Client _client, boolean is_appflag, String center_num) throws Exception {
		JSONObject person = new JSONObject();
		person.put("exam_num", lis.getExam_num());
		person.put("arch_num", lis.getArch_num());
		person.put("name", lis.getUser_name());
		person.put("sex", lis.getSex());
		person.put("birthday", lis.getBirthday().substring(0, 10));
		person.put("age", lis.getAge());
		person.put("id_num", lis.getId_num());
		person.put("card_no", "");
		person.put("user_code", "");
		person.put("user_name", username);
		person.put("examinfo_id", lis.getExam_info_id());

		Connection connection1 = null;
		Statement statement1 = null;
		try {
			connection1 = this.jqm.getConnection();
			statement1 = connection1.createStatement();

			StringBuffer sbt = new StringBuffer();
			sbt.append("select c.id,b.item_code,b.item_name,m.demo_num,m.demo_name,n.data_name as sample_type "
					+ "from sample_exam_detail c"
					+ ",examResult_chargingItem d,sample_demo m,charging_item b,Data_Dictionary n where c.exam_info_id="
					+ lis.getExam_info_id() + "" + " and c.sample_barcode = d.bar_code and d.charging_item_code=b.item_code and d.result_type='"
					+ CommSet.lis_sample + "' and d.isActive='Y' "
					+ " and c.sample_id=m.id and m.demo_category=n.id");
			ResultSet rs1 = statement1.executeQuery(sbt.toString());
			while (rs1.next()) {
				JSONObject oders = new JSONObject();
				oders.put("sample_num", rs1.getString("demo_num"));
				oders.put("sample_name", rs1.getString("demo_name"));
				oders.put("sample_type", rs1.getString("sample_type"));
				String item_code = rs1.getString("item_code");
				String item_name = rs1.getString("item_name");
				long sample_exam_detail_id = rs1.getLong("id");
				Connection connection2 = null;
				Statement statement2 = null;
				try {
					connection2 = this.jqm.getConnection();
					statement2 = connection2.createStatement();

					StringBuffer sbt2 = new StringBuffer();
					sbt2.append("select a.id,a.is_application ,dep_category c ,b.exam_num as item_lis_code,"
							+ "a.amount,a.item_amount,b.item_code,b.item_name " + "from examinfo_charging_item a"
							+ ",charging_item b where a.exam_num=" + lis.getExam_num()
							+ " and a.pay_status<>'M' and a.exam_status<>'G' and a.charge_item_id=b.id and a.center_num = '"+center_num+"' ");
					if (!is_appflag) {
						sbt2.append(" and a.is_application='N' ");
					}
					sbt2.append(" and a.isActive='Y' and b.item_code='" + item_code + "'");
					ResultSet rs2 = statement2.executeQuery(sbt2.toString());
					List<JSONObject> list = new ArrayList<JSONObject>();
					boolean falags = false;
					while (rs2.next()) {
						JSONObject items = new JSONObject();
						items.put("item_id", item_code);
						items.put("item_name", item_name);
						items.put("item_lis_code", rs2.getString("item_lis_code"));
						items.put("price", rs2.getString("item_amount"));
						items.put("charge", rs2.getString("amount"));
						list.add(items);
						falags = true;
					}
					oders.put("items", list);
					rs2.close();

					if (falags) {
						JSONObject json = new JSONObject();
						json.put("person", person);
						json.put("orders", oders);
						Date date = new Date(System.currentTimeMillis());

						logger.error("开始发送lis申请" + date.getTime());

						Object[] o = new Object[1];
						o[0] = json.toString();
						Object[] results = _client.invoke("LisRequest", o);
						JSONObject result = JSONObject.fromObject(results[0]);

						logger.error("LIS申请发送完毕" + date.getTime() + ",返回标志：" + result.getString("return_code"));
						// 0表示请求成功，更新编码，更新申请标志
						if ("0".equals(result.getString("return_code"))) {
							if ("1".equals(result.getString("isupdate"))) {
								// 更新样本表
								String sql = "update sample_exam_detail " + " set sample_barcode='"
										+ result.getString("bar_code") + "',status='Y' " + " where id="
										+ sample_exam_detail_id + "";
								connection2.createStatement().executeUpdate(sql);
							}
							String sql1 = "update examinfo_charging_item set is_application='Y' where "
									+ " id in( select a.id from examinfo_charging_item a,charging_item b where "
									+ " a.exam_num=" + lis.getExam_num()
									+ " and a.pay_status<>'M' and a.exam_status<>'G' and a.center_num = '"+center_num+"' and a.charge_item_id=b.id and a.is_application='N' " + " and b.item_code='"
									+ item_code + "')";
							connection2.createStatement().executeUpdate(sql1);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement2 != null) {
							statement2.close();
						}
						if (connection2 != null) {
							connection2.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
			}
			rs1.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement1 != null) {
					statement1.close();
				}
				if (connection1 != null) {
					connection1.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: getPacsInterface @Description: 调用接口LIS申请 @param: @param
	 * lis @param: @param username @param: @param is_appflag
	 * true表示is_application＝Y 时候继续部分，false
	 * 表示is_application=Y的时候不在发送， @param: @return @param: @throws
	 * Exception @return: boolean @throws
	 */
	private void getPacsInterface(LisUserDTO pacs, String username, Client _client, boolean is_appflag, String center_num)
			throws Exception {
		JSONObject person = new JSONObject();
		person.put("exam_num", pacs.getExam_num());
		person.put("arch_num", pacs.getArch_num());
		person.put("name", pacs.getUser_name());
		person.put("sex", pacs.getSex());
		person.put("birthday", pacs.getBirthday().substring(0, 10));
		person.put("age", pacs.getAge());
		person.put("id_num", pacs.getId_num());
		person.put("card_no", "");
		person.put("user_code", "");
		person.put("user_name", username);
		person.put("address", pacs.getAddress() == null ? "" : pacs.getAddress());
		person.put("phone", pacs.getPhone() == null ? "" : pacs.getPhone());
		Connection connection1 = null;
		Statement statement1 = null;
		try {
			connection1 = this.jqm.getConnection();
			statement1 = connection1.createStatement();

			StringBuffer sbt = new StringBuffer();
			sbt.append("select c.id as sumarry_id,a.dep_name,a.chargingItem_num,a.chargingItem_name "
					+ "from pacs_summary c,Pacs_Detail a where c.examinfo_num='" + pacs.getExam_num()
					+ "' and c.exam_Status='N' " + "and c.id=a.summary_id");
			ResultSet rs1 = statement1.executeQuery(sbt.toString());
			while (rs1.next()) {
				JSONObject oders = new JSONObject();
				oders.put("sumarry_id", rs1.getInt("id"));
				oders.put("sample_name", "");
				oders.put("dept_num", rs1.getString("dep_name"));
				String chargingItem_num = rs1.getString("chargingItem_num");
				Connection connection2 = null;
				Statement statement2 = null;
				try {
					connection2 = this.jqm.getConnection();
					statement2 = connection2.createStatement();

					StringBuffer sbt2 = new StringBuffer();
					sbt2.append(
							"select a.id,b.item_code,b.item_name,b.view_num as item_pacs_code,a.amount,a.item_amount"
									+ " from examinfo_charging_item a,charging_item b where a.exam_num='"
									+ pacs.getExam_num() + "'" + " and a.isActive='Y' and a.pay_status<>'M' and a.exam_status<>'G' and a.center_num = '"+center_num+"' "
									+ " and a.charge_item_id=b.id");
					if (!is_appflag) {
						sbt2.append(" and a.is_application='N' ");
					}
					sbt2.append(" and b.item_code='" + chargingItem_num + "'");
					ResultSet rs2 = statement2.executeQuery(sbt2.toString());
					List<JSONObject> list = new ArrayList<JSONObject>();
					boolean falags = false;
					while (rs2.next()) {
						JSONObject items = new JSONObject();
						items.put("item_id", rs2.getString("item_code"));
						items.put("item_name", rs2.getString("item_name"));
						items.put("item_pacs_code", rs2.getString("item_pacs_code"));
						items.put("price", rs2.getString("item_amount"));
						items.put("charge", rs2.getString("amount"));
						list.add(items);
						falags = true;
					}
					oders.put("items", list);
					rs2.close();

					if (falags) {

						JSONObject json = new JSONObject();
						json.put("person", person);
						json.put("orders", oders);
						Date date = new Date(System.currentTimeMillis());
						logger.error("开始发送PACS申请" + date.getTime());
						Object[] o = new Object[1];
						o[0] = json.toString();
						Object[] results = _client.invoke("PacsReqestInfo", o);
						Integer result = (Integer) results[0];
						logger.error("PACS申请发送完毕" + date.getTime() + ",返回标志：" + result);
						if (result == 0) {
							String sql1 = "update examinfo_charging_item set is_application='Y' where "
									+ " id in( select a.id from examinfo_charging_item a,charging_item b "
									+ " where a.exam_num='" + pacs.getExam_num() + "'"
									+ " and a.pay_status<>'M' and a.exam_status<>'G' and a.isActive='Y' and a.center_num = '"+center_num+"' and a.charge_item_id=b.id "
									+ "and a.is_application='N' and b.item_code='" + chargingItem_num + "')";
							connection2.createStatement().executeUpdate(sql1);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					try {
						if (statement2 != null) {
							statement2.close();
						}
						if (connection2 != null) {
							connection2.close();
						}
					} catch (SQLException sqle4) {
						sqle4.printStackTrace();
					}
				}
			}
			rs1.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (statement1 != null) {
					statement1.close();
				}
				if (connection1 != null) {
					connection1.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: batchSigndo @Description: 执行签到 @param: @param
	 * exam_ids @param: @param userid @param: @throws ServiceException @return:
	 * void @throws
	 */
	public void batchSigndo(String[] exam_nums, long userid,String center_num) throws ServiceException {
		String sql = "";
		for (int i = 0; i < exam_nums.length; i++) {
			ExamInfoDTO examinfo = this.getExamInfoForexamNum(exam_nums[i]);
			if(examinfo != null && examinfo.getId() > 0){
				sql = "update exam_info set status='D',join_date='" + DateTimeUtil.getDateTime() + "',updater=" + userid
					  + ",update_time='" + DateTimeUtil.getDateTime() + "',exam_center_num='"+center_num+"' where exam_num='" + exam_nums[i] + "' and status='Y' ";
				this.jpm.executeSql(sql);
				this.djtCustomerInfoService.updatevipflag(examinfo.getExam_num(), center_num);
	        	this.djtCustomerInfoService.updateOutRegFlag(examinfo.getExam_num(), center_num,examinfo,userid);
			}
		}
	}

	/**
	 * 
	     * @Title: getPrintDjd   
	     * @Description:  翰思打印导检单   
	     * @param: @param ides
	     * @param: @param djdurl
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DjdbeanList>      
	     * @throws
	 */
	public List<DjdbeanList> getPrintDjd(String examnums, String djdurl, long userid) throws ServiceException {
		List<DjdbeanList> list = new ArrayList<DjdbeanList>();
		Connection connection = null;
		Statement statement = null;
		String[] idess = examnums.split(",");
		for (int i = 0; i < idess.length; i++) {
			try {
				DjdbeanList alertDto = new DjdbeanList();
				boolean falags = false;
				String exam_num = idess[i];
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
				StringBuffer sbt = new StringBuffer();
				sbt.append("select b.id,a.user_name,a.sex,b.age,b.company,b.register_date,b.exam_times "
						+ "from customer_info a,exam_info b where a.id=b.customer_id " + " and b.exam_num='" + exam_num
						+ "'");
				ResultSet rs = statement.executeQuery(sbt.toString());
				if (rs.next()) {
					long exam_id = rs.getLong("id");
					Timestamp nowdate = rs.getTimestamp("register_date");
					if (nowdate != null) {
						String datess = DateTimeUtil.setTimestamp(nowdate);
						Djduser duser = new Djduser();
						duser.setAge(rs.getString("age"));
						duser.setDatas(datess.substring(5, 10));
						duser.setDempname(rs.getString("company"));
						duser.setName(rs.getString("user_name"));
						duser.setSex(rs.getString("sex"));
						duser.setTimes(rs.getString("exam_times"));
						duser.setXq(DateTimeUtil.getWeek(datess));
						duser.setYears(datess.substring(0, 4));
						alertDto.setDuser(duser);

						alertDto.setNotices("采血、B超需空腹");
						alertDto.setNotices1("请到前台领取  尿杯！");
						Djdqrcode qrarcode = new Djdqrcode();
						qrarcode.setUrl(djdurl);
						qrarcode.setNoteices("微信扫一扫");
						qrarcode.setNotices1("实时查看报告");
						alertDto.setDqrcode(qrarcode);

						Djdbardode dbarcode = new Djdbardode();
						dbarcode.setExtamid(exam_num + "");
						alertDto.setDbarcode(dbarcode);
						falags = true;
						Connection connection1 = null;
						Statement statement1 = null;
						try {
							connection1 = this.jqm.getConnection();
							statement1 = connection1.createStatement();

							StringBuffer sbtt = new StringBuffer();
							sbtt.append(
									"select d.dep_name,d.dep_num,d.remark from exam_info a,examinfo_charging_item b,"
											+ "charging_item c,department_dep d where a.exam_num='" + exam_num
											+ "' and a.id=b.examinfo_id "
											+ " and a.is_Active='Y'  and b.charge_item_id=c.id and c.dep_id=d.id and c.isActive='Y' "
											+ " and b.pay_status<>'M' and b.exam_status<>'G' order by d.seq_code,c.item_seq");
							ResultSet rs1 = statement1.executeQuery(sbtt.toString());
							int j = 1;
							while (rs1.next()) {
								Djdbean dd = new Djdbean();
								dd = new Djdbean();
								dd.setRoomname(rs1.getString("remark"));
								dd.setDeptname(rs1.getString("dep_name"));
								if (j == 1) {
									alertDto.setDjdbean1(dd);
								} else if (j == 2) {
									alertDto.setDjdbean2(dd);
								} else if (j == 3) {
									alertDto.setDjdbean3(dd);
								} else if (j == 4) {
									alertDto.setDjdbean4(dd);
								} else if (j == 5) {
									alertDto.setDjdbean5(dd);
								} else if (j == 6) {
									alertDto.setDjdbean6(dd);
								} else if (j == 7) {
									alertDto.setDjdbean7(dd);
								} else if (j == 8) {
									alertDto.setDjdbean8(dd);
								} else if (j == 9) {
									alertDto.setDjdbean9(dd);
								} else if (j == 10) {
									alertDto.setDjdbean10(dd);
								} else if (j == 11) {
									alertDto.setDjdbean11(dd);
								} else if (j == 12) {
									alertDto.setDjdbean12(dd);
								} else if (j == 13) {
									alertDto.setDjdbean13(dd);
								} else if (j == 14) {
									alertDto.setDjdbean14(dd);
								} else if (j == 15) {
									alertDto.setDjdbean15(dd);
								} else if (j == 16) {
									alertDto.setDjdbean16(dd);
								} else if (j == 17) {
									alertDto.setDjdbean17(dd);
								} else if (j == 18) {
									alertDto.setDjdbean18(dd);
								}
								j++;
							}
							rs1.close();
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

					}
					if (falags) {
						list.add(alertDto);
						Connection connection2 = null;
						try {
							// 读取记录数
							connection2 = this.jqm.getConnection();
							String sql = "update exam_info set is_need_guide='Y',updater=" + userid
									+ ",update_time='" + DateTimeUtil.getDateTime() + "' where id=" + exam_id
									+ "";
							connection2.createStatement().executeUpdate(sql);

						} catch (SQLException ex) {
							ex.printStackTrace();
						} finally {
							try {

								if (connection2 != null) {
									connection2.close();
								}
							} catch (SQLException sqle4) {
								sqle4.printStackTrace();
							}
						}
					}
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
		}
		return list;
	}
	
	
	/**
	 * 
	     * @Title: setExamInfotime   
	     * @Description: 设置体检有效日期  
	     * @param: @param ides
	     * @param: @param dates
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void setExamInfotime(String ides,String dates,String time1,String time2, long userid) throws ServiceException {
		String[] idess = ides.split(",");
		for (int i = 0; i < idess.length; i++) {
				String exam_num = idess[i];
				ExamInfoDTO ed =  getExamInfoForexamnum(exam_num);
				 if((ed!=null)&&(ed.getId()>=0)&&("Y".equals(ed.getStatus())||("D".equals(ed.getStatus())))&&(ed.getFreeze()==0)){
						Connection connection2 = null;
						try {
							// 读取记录数
							connection2 = this.jqm.getConnection();
							String times = time1+"-"+time2;
							String sql = "update exam_info set  register_date='"+dates+"',exam_times='"+times+"',updater=" + userid
									+ ",update_time='" + DateTimeUtil.getDateTime() + "' where exam_num='" + exam_num+ "'";
							connection2.createStatement().executeUpdate(sql);
							String synstr="delete web_synchro where dataid ='"+exam_num+"' and datatype='5'";
							connection2.createStatement().executeUpdate(synstr);
				   		    synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+exam_num+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
				   		    connection2.createStatement().executeUpdate(synstr);
						} catch (SQLException ex) {
							ex.printStackTrace();
						} finally {
							try {

								if (connection2 != null) {
									connection2.close();
								}
							} catch (SQLException sqle4) {
								sqle4.printStackTrace();
							}
						}
				 }
		}
	}
	
	
	/**
	 * 
	     * @Title: setExamInfotime   
	     * @Description: 删除体检有效日期  
	     * @param: @param ides
	     * @param: @param dates
	     * @param: @param time1
	     * @param: @param time2
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delExamInfotime(String ides, long userid) throws ServiceException {
		String[] idess = ides.split(",");
		for (int i = 0; i < idess.length; i++) {
//			long exam_id = Long.valueOf(idess[i]);
						Connection connection2 = null;
						try {
							// 读取记录数
							connection2 = this.jqm.getConnection();
							String sql = "update exam_info set register_date='',exam_times='',updater=" + userid
									+ ",update_time='" + DateTimeUtil.getDateTime() + "' where exam_num='" + idess[i]+ "'";
							connection2.createStatement().executeUpdate(sql);

						} catch (SQLException ex) {
							ex.printStackTrace();
						} finally {
							try {

								if (connection2 != null) {
									connection2.close();
								}
							} catch (SQLException sqle4) {
								sqle4.printStackTrace();
							}
						}
			
		}
	}
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description: 统计时间日期设置人数 
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public List<CustmTimeCountDTO> countExamInfotime(long com_id,long batch,String centernum,String apptype) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		List<CustmTimeCountDTO> list = new ArrayList<CustmTimeCountDTO>();
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			sbt.append("select CONVERT(varchar(50),a.register_date,23) as register_date,a.exam_times,count(*) as num from exam_info a "
					+ ",customer_info c,batch d where a.company_id="+com_id+" and a.batch_id="+batch+" "
					+ "and a.customer_id=c.id and c.is_Active='Y'  "
					+ " and a.is_Active='Y' and d.is_Active='Y' and a.center_num='"+centernum+"' and a.apptype='"+apptype+"' "
					+ " group by CONVERT(varchar(50),a.register_date,23),a.exam_times "
					+ "order by register_date,a.exam_times  ");	
			ResultSet rs = statement.executeQuery(sbt.toString());
			while (rs.next()) {
				CustmTimeCountDTO ct = new CustmTimeCountDTO();
				ct.setDatetime(rs.getString("register_date"));
				ct.setNums(rs.getLong("num"));
				ct.setTimes(rs.getString("exam_times"));
				if((ct.getDatetime()!=null)&&(ct.getDatetime().trim().length()>=10)){
					if("1900-01-01".equals(ct.getDatetime().trim().substring(0, 10))){
						ct.setDatetime("");
					}else{
						ct.setDatetime(ct.getDatetime().trim().substring(0, 10));
					}
				}else{
					ct.setDatetime("");
				}
				
				if((ct.getDatetime()==null)||(ct.getDatetime().trim().length()<10)){
					ct.setStates("未设置体检日期");
				}else if(ct.getTimes()==null){
					ct.setStates("未设置体检时间");
				}else{
					ct.setStates("设置完成");
				}
				list.add(ct);
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
		return list;
	}
	
	/**
	 * 
	     * @Title: getExamInfoUserForExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoUserForExamNum(String exam_num,String center_num)throws ServiceException {
		ExamInfoUserDTO ei = new ExamInfoUserDTO();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.id as customer_id,a.nation,a.user_name,a.arch_num,a.id_num,"
				+ "a.sex,c.age,c.exam_num,c.is_marriage,c.exam_type,"
				+ "c.position,c._level,c.group_id,c.email,c.chargingType,c.remarke,"
				+ "c.others,c.status,c.phone,c.customer_type,c.customer_type_id,a.address");
		sb.append(",c.group_id,a.birthday,c._level,c.status,c.register_date"
				+ ",c.join_date,c.exam_times,c.exam_num,c.company,c.past_medical_history,"
				+ "c.picture_Path,c.is_after_pay,c.batch_id,n.batch_name,"
				+ "c.employeeID,c.mc_no,c.visit_date,c.visit_no,c.clinic_no,c.exam_indicator,c.batch_id,"
				+ "c.company_id,m.group_name,c.freeze  ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join batch  n on n.id=c.batch_id ");
		sb.append(" left join group_info  m on m.id=c.group_id ");
		sb.append(" where c.customer_id=a.id  ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and a.is_Active='Y' ");
		sb.append(" and c.exam_num='"+exam_num+"' and n.center_num='"+center_num+"' ");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 1000, ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			ei = (ExamInfoUserDTO) map.getList().get(0);
			ei.setBatch_name(ei.getBatch_name()+"("+ei.getCompany()+")");
			if((ei.getRegister_date()!=null)&&(ei.getRegister_date().trim().length()>10)) ei.setRegister_date(ei.getRegister_date().substring(0, 10));
		}
		return ei;
	}
	
	/**
	 * 
	     * @Title: getExamInfoUserForExamId   
	     * @Description: 获取身份信息 
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoUserForExamId(String exam_num,UserDTO user)throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		ExamInfoUserDTO ei = new ExamInfoUserDTO();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.apptype,c.wuxuzongjian,c.getReportWay, c.degreeOfedu, eei.political_status, da.data_name,c.id,a.id as customer_id,a.idtype,a.nation,"
				+ "dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,c.patient_id,"
				+ "a.sex,c.age,c.exam_num,c.is_marriage,c.exam_type,"
				+ "c.position,c._level,c.group_id,c.email,c.chargingType,c.remarke,"
				+ "c.others,c.status,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,c.customer_type,c.customer_type_id,a.address");
		sb.append(",c.group_id,c.group_index,a.birthday,c._level,c.status,c.register_date"
				+ ",c.join_date,c.exam_times,c.exam_num,c.past_medical_history,"
				+ "c.picture_Path,c.is_after_pay,c.batch_id,n.batch_name,c.is_Active,"
				+ "c.employeeID,c.patient_id,c.mc_no,c.visit_date,c.visit_no,c.clinic_no,c.exam_indicator,c.batch_id,"
				+ "c.company_id,m.group_name,c.freeze,c.company,c.budget_amount,c.introducer,c.zyb_set_source ");
		sb.append(" from customer_info a,exam_info c ");
		sb.append(" left join batch  n on n.id=c.batch_id ");
		sb.append(" left join group_info  m on m.id=c.group_id ");
		sb.append(" left join data_dictionary  da on c.customer_type=da.id   ");
		sb.append(" LEFT JOIN exam_ext_info eei ON c.exam_num = eei.exam_num  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and a.is_Active='Y' ");
		sb.append(" and c.exam_num='"+exam_num+"' ");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 1000, ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			ei = (ExamInfoUserDTO) map.getList().get(0);
			
			ei.setBatch_name(ei.getBatch_name()+"("+ei.getCompany()+")");
			if((ei.getRegister_date()!=null)&&(ei.getRegister_date().trim().length()>10)) ei.setRegister_date(ei.getRegister_date().substring(0, 10));
			ei.setExamcount(getExamInfotime(ei.getCustomer_id()));
			
			Connection connection = null;
			Statement statement = null;
			String occusectorid = "";
			try {
				connection = this.jqm.getConnection();
				statement = connection.createStatement();
			
			StringBuffer sbt = new StringBuffer();
			sbt.append("select occusectorid from exam_ext_info  where exam_num='" + ei.getExam_num()+ "'");
			ResultSet rs = statement.executeQuery(sbt.toString());
			if (rs.next()) {
				occusectorid = rs.getString("occusectorid");
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
			ei.setOccusectorid(occusectorid);
			
		}
		return ei;
	}
	
	/**
	 * 
	     * @Title: getExamInfoUserForCustomerId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoUserForCustomerId(long customerId)throws ServiceException {
		ExamInfoUserDTO ei = new ExamInfoUserDTO();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.id as customer_id,a.nation,a.user_name,a.arch_num,a.id_num,"
				+ "a.sex,a.address");
		sb.append(",a.birthday ");
		sb.append(" from customer_info a ");
		sb.append(" where a.id='"+customerId+"'  ");
		sb.append(" and a.is_Active='Y' ");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 1000, ExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			ei = (ExamInfoUserDTO) map.getList().get(0);			
		}
		return ei;
	}
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description: 统计时间日期设置人数 
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String checkExamInfotime(String ids) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		String flags="ok";
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			sbt.append("select count(*) as num from exam_info a where (a.status ='J' or a.status ='Z') and id in ("+ids+") ");	
			ResultSet rs = statement.executeQuery(sbt.toString());			
			if (rs.next()) {
				if(rs.getLong("num")>0){
					rs.close();
					flags="error-只能对预约用户进行加减项处理，操作不能继续！";
				}else{
					rs.close();
					sbt = new StringBuffer();
					sbt.append("select count(*) as num from exam_info a where (a.group_id is null or a.group_id<=0) and a.id in ("+ids+") ");	
					rs = statement.executeQuery(sbt.toString());
					if (rs.next()) {
						if(rs.getLong("num")>0){
							rs.close();
							flags="error-存在未分组人员，所以不能加减项目！";
						}else{
							rs.close();
							sbt = new StringBuffer();
							sbt.append("select count(*) as num from (select b.sex from exam_info a,customer_info b where b.is_Active='Y' "
									+ "and a.customer_id=b.id and a.id in ("+ids+") group by b.sex) m ");	
							rs = statement.executeQuery(sbt.toString());
							if (rs.next()) {
								if(rs.getLong("num")>1){									
									flags="error-所选人员性别不一致，所以不能加减项目！";
								}
							}
							rs.close();
						}
					}else{
						rs.close();
					}
					
				}
			}else{
				rs.close();
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
		return flags;
	}
	
	/**
	 * 
	     * @Title: countExamInfotime   
	     * @Description:获取选择人员的性别
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public String getIDSExamInfoSex(String ids) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		String flags="男";
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			String newExamNum = "";
			if(ids.contains(",")){
                newExamNum = ids.replaceAll(",","','");
            } else {
                newExamNum = ids;
            }
			sbt.append("select b.sex from exam_info a,customer_info b where a.is_Active='Y' and b.is_Active='Y' "
					+ "and a.customer_id=b.id and a.exam_num in ('"+newExamNum+"') group by b.sex");
			ResultSet rs = statement.executeQuery(sbt.toString());			
			if (rs.next()) {
				flags=rs.getString("sex");
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
		return flags;
	}
	
	/**
	 * 
	     * @Title: checkListItem   
	     * @Description: 判断是否有 lis 检查项目 
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
   public int checkListItem(String item_code, String center_num ) throws ServiceException{
		int countnum=0;
		StringBuffer sql = new StringBuffer("select count(*) as num from (select a.item_code,b.item_num from charging_item a,"
				+ "examination_item b,charging_item_exam_item c,department_dep d,department_vs_center de where a.item_code=c.charging_item_code "
				+ "and de.dep_id = d.id  and b.item_num=c.item_code and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id "
				+ "and d.dep_category='131' and de.center_num ='"+center_num+"'  and a.item_code ='"+item_code+"') m ");
		//System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		List<String> item_numold=new ArrayList<String>();
		List<String> item_numnew=new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				countnum=rs.getInt("num");
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
		return countnum;
   }
	
  
   /**
    * 
        * @Title: checkPacsItem   
        * @Description: 检查pacs
        * @param: @param item_code
        * @param: @return
        * @param: @throws ServiceException      
        * @return: int      
        * @throws
    */
  public int checkPacsItem(String item_code, String center_num ) throws ServiceException{
		int countnum=0;
		StringBuffer sql = new StringBuffer("select count(*) as num from (select a.item_code,b.item_num from charging_item a,"
				+ "examination_item b,charging_item_exam_item c,department_dep d ,department_vs_center de where a.item_code=c.charging_item_code "
				+ "and b.item_num=c.item_code  and de.dep_id = d.id   and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id "
				+ "and d.dep_category='21'  and de.center_num ='"+center_num+"' and a.item_code ='"+item_code+"') m ");
		//System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
		List<String> item_numold=new ArrayList<String>();
		List<String> item_numnew=new ArrayList<String>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql.toString());
			if (rs.next()) {
				countnum=rs.getInt("num");
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
		return countnum;
  }

  
  /**
   * 
       * @Title: getIsnewaddedExamInfoItemForId   
       * @Description: TODO(这里用一句话描述这个方法的作用)   
       * @param: @param exam_id
       * @param: @return
       * @param: @throws ServiceException      
       * @return: int      
       * @throws
   */
  public int getIsnewaddedExamInfoItemForId(String exam_num, String center_num)throws ServiceException {
			List ls = qm.find("From ExaminfoChargingItem where isActive='Y' and pay_status<>'M' and exam_status<>'G' and center_num = '"+center_num+"' and exam_num='"+exam_num+"'");
			if((ls!=null)&&(ls.size()>0))
				return ls.size()+1;
			else
				return 0;

	}
  
	/**
	 * 
	     * @Title: addIDSExamInfoChangItem  
	     * @Description:增加人员收费项目
	     * @param: @param batch
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
  public void addIDSExamInfoChangItem(String exam_num, List<GroupChargingItemDTO> listitem,String itemcodes,String exam_indicator,long userid,String username,String centerNum) throws ServiceException {
	    ExamInfoDTO ed = this.getCustExamInfoForexamId(exam_num);
	    boolean synflag=false;
		for (int j = 0; j < listitem.size(); j++) {				
			GroupChargingItemDTO es = new GroupChargingItemDTO();
			es = listitem.get(j);
			ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());
			es.setCharge_item_id(cd.getId());
			if(itemcodes.toUpperCase().indexOf(es.getItem_code().toUpperCase())<0){
		    boolean ctcheckflag= this.batchService.checkitemflag(itemcodes, es.getItem_code(),centerNum);
		    int isnew=getIsnewaddedExamInfoItemForId(exam_num, centerNum);
		    if(!ctcheckflag){//没有冲突项目
		      //新增 examinfo_charging_item、
		    	ExaminfoChargingItem eci =  new ExaminfoChargingItem();
             // eci.setExaminfo_id(examid);
              eci.setExam_num(exam_num);
              eci.setCharge_item_id(cd.getId());
              if("G".equals(exam_indicator)){
              	eci.setExam_indicator("G");
              	eci.setPay_status("N");
              	eci.setTeam_pay(0);
              	eci.setItemnum(eci.getItemnum());
                eci.setPersonal_pay(es.getAmount());                        
              	eci.setTeam_pay_status("Y");
              	eci.setApp_type("1");
              }else{
              	eci.setExam_indicator("T");
              	eci.setPay_status("R");
              	eci.setTeam_pay_status("N");
              	eci.setTeam_pay(es.getAmount());
              	eci.setAmount(0);
                eci.setPersonal_pay(0);
                eci.setItemnum(eci.getItemnum());
                eci.setApp_type("1");
              }
              
              eci.setItem_amount(es.getItem_amount());
              eci.setDiscount(es.getDiscount());
              eci.setAmount(es.getAmount());
              eci.setIsActive("Y");
              eci.setFinal_exam_date(DateTimeUtil.parse());                    
              eci.setExam_status("N");
              eci.setIs_new_added(isnew);
              eci.setCreater(userid);
              eci.setCreate_time(DateTimeUtil.parse());
              eci.setUpdater(userid);
              eci.setUpdate_time(DateTimeUtil.parse());
              eci.setCheck_status(0);
              eci.setCalculation_amount(es.getAmount());
              eci.setIs_application("N");
              eci.setChange_item("N");
              eci.setItemnum(es.getItemnum());
              eci.setExam_num(ed.getExam_num());
              eci.setHis_req_status("N");
              eci.setCharging_item_code(es.getItem_code());
              eci.setCenter_num(centerNum);
              this.pm.save(eci);
              synflag=true;
              ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());
              int lisnum=checkListItem(es.getItem_code(),centerNum);
              if(lisnum>0){
                 SampleExamDetail sd = new SampleExamDetail();
                 sd=this.getSampleExamDetailforExamid(exam_num,ci.getSam_demo_id(),"W");
                 long samexam_id=0;
                 String sample_barcode = "";
                 if((sd==null)||(sd.getId()<=0)){   
                	 sample_barcode = this.batchService.GetCreateID("barcode", centerNum);
              	   SampleExamDetail sed = new SampleExamDetail();
              	   //sed.setExam_info_id(examid);  
              	   sed.setExam_num(exam_num);
              	   sed.setSample_id(ci.getSam_demo_id());
              	   sed.setSample_barcode(sample_barcode);
              	   sed.setStatus("W");
              	   sed.setCenter_num(centerNum);
              	   sed.setCreater(userid);
              	   sed.setCreate_time(DateTimeUtil.parse());
              	   sed.setUpdater(userid);
              	   sed.setUpdate_time(DateTimeUtil.parse());
              	   sed.setPic_path("");
              	   sed.setExam_num(ed.getExam_num());
              	   this.pm.save(sed);
              	   samexam_id=sed.getId();
                 }else{
              	   samexam_id=sd.getId(); 
              	 sample_barcode = sd.getSample_barcode();
                 }
              	 
                 ExamResultChargingItem erci =new ExamResultChargingItem();
                 erci.setCharging_id(cd.getId());
                 erci.setExam_id(samexam_id);
                 erci.setResult_type(CommSet.lis_sample);
                 erci.setIsActive("Y");
                 erci.setCreater(userid);
                 erci.setCreate_time(DateTimeUtil.parse());
                 erci.setUpdater(userid);
                 erci.setUpdate_time(DateTimeUtil.parse());
                 erci.setBar_code(sample_barcode);
                 erci.setCharging_item_code(ci.getItem_code());
                 this.pm.save(erci);
              }
              
              int pacsnum=this.checkPacsItem(es.getItem_code(),centerNum);
              if (pacsnum>0){
              	PacsSummary psd = new PacsSummary();
              	psd=getPacsSummaryDTOforExamid(ed.getExam_num(),ci.getSam_demo_id(),"N");
              	long summary_id=0;
              	if((psd==null)||(psd.getId()<=0)){
              		PacsSummary ps =new PacsSummary();
              		ps.setExaminfo_num(ed.getExam_num());
              		ps.setExaminfo_name(ed.getUser_name());
              		ps.setExaminfo_sex(ed.getSex());
             		
              		if ((!StringUtil.isEmpty(ed.getBirthday()))&&(ed.getBirthday().trim().length() == 10)){
              			Date da = Timeutils.strToDateLong2(ed.getBirthday().trim().replaceAll("-", ""));
							ps.setExaminfo_birthday(da);
						}else if ((!StringUtil.isEmpty(ed.getBirthday()))&&(ed.getBirthday().trim().length() == 8)){
              			Date da = Timeutils.strToDateLong2(ed.getBirthday().trim());
							ps.setExaminfo_birthday(da);
						}                    		
              		ps.setApply_person(username);
              		ps.setApply_date(DateTimeUtil.parse());
              		ps.setExam_status("N");
              		ps.setCreater(userid);
              		ps.setCreate_time(DateTimeUtil.parse());
              		ps.setUpdater(userid);
              		ps.setUpdate_time(DateTimeUtil.parse());
              		ps.setExaminfo_sampleId(ci.getSam_demo_id());
              		ps.setPacs_req_code(this.batchService.GetCreateID("pacs_req_num", centerNum));
					ps.setSample_status("N");
              		this.pm.save(ps);
              		summary_id=ps.getId();
              	}else{
              		summary_id=psd.getId();
              	}
              	Pacsdetail pd =new Pacsdetail();
              	pd.setExaminfo_num(ed.getExam_num());
              	pd.setSummary_id(summary_id);
              	pd.setChargingItem_num(es.getItem_code());
              	pd.setChargingItem_name(es.getItem_name());
              	DepartmentDep dd = departmentService.loadDepartmentDep(cd.getDep_id());
              	pd.setDep_num(dd.getDep_num());
              	pd.setDep_name(dd.getDep_name());
              	pd.setExaminfo_sampleId(ci.getSam_demo_id());
              	pd.setIs_need_return("Y");
              	pd.setCreater(userid);
              	pd.setCreate_time(DateTimeUtil.parse());
              	pd.setUpdater(userid);
              	pd.setUpdate_time(DateTimeUtil.parse());
              	pd.setPacs_req_code(this.batchService.GetCreateID("pacs_req_num", centerNum));
              	this.pm.save(pd);
              }          
		    }
		}
	}
		if(synflag){
			this.InsertWebSynchro('5', exam_num+"");
		}
}
	
	/**
	 * 
	     * @Title: delIDSExamInfoChangItem   
	     * @Description: 根据体检id和收费项目id删除 
	     * @param: @param examid
	     * @param: @param chargingId
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delIDSExamInfoChangItem(String exam_num,String chargingCode,long userid,UserDTO user) throws ServiceException {
		ExamInfoUserDTO ei = getExamInfoUserForExamId(exam_num,user);
		ChargingItemDTO ci = getChargingItemForId(chargingCode);
		List<SampleExamDetailDTO> webrole = new ArrayList<SampleExamDetailDTO>();
		webrole = getSampleExamDetailforCharingId(exam_num, chargingCode);
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			boolean lisdelfalg=true;
			boolean pacsdelfalg=true;
			for (SampleExamDetailDTO sd : webrole) {
				if (("Y".equals(sd.getStatus())) && (sd.getSample_barcode() != null)&& (sd.getSample_barcode().trim().length() > 0)) {
					// 发送lis去掉接口
					lisdelfalg=false;
				} else if ((sd.getSample_barcode() != null) && (sd.getSample_barcode().trim().length() > 0)) {
					String sql = "delete examResult_chargingItem where bar_code='" + sd.getSample_barcode() + "' and charging_item_code='"+ chargingCode + "' and result_type='sample'";
					connection.createStatement().executeUpdate(sql);
					int counts = 0;
					sql = "select COUNT(*) as counts from examResult_chargingItem where bar_code='" + sd.getSample_barcode()
							+ "' and result_type='sample' and isActive='Y'";
					ResultSet rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						counts = rs.getInt("counts");
					}
					rs.close();
					if (counts <= 0) {
						sql = "delete sample_exam_detail where sample_barcode='" + sd.getSample_barcode()+"'";
						connection.createStatement().executeUpdate(sql);
					} else {
						String barcode = this.batchService.GetCreateID("barcode", user.getCenter_num());
//						sql = "select b.BarCode_Class from sample_exam_detail a,sample_demo b where a.id=" + sd.getId()
//								+ " and a.sample_id=b.id and b.isActive='Y'";
//						ResultSet rs1 = connection.createStatement().executeQuery(sql);
//						if (rs1.next()) {
//							int barcodeclass = rs1.getInt("BarCode_Class");
//							if (barcodeclass == 0) {
//								sql = "update sample_exam_detail set sample_barcode='"
//										+ this.batchService.GetCreateID("barcode") + "' where id=" + sd.getId();
//								connection.createStatement().executeUpdate(sql);
//							}
//						}
//						rs1.close();
						sql = "update sample_exam_detail set sample_barcode = '"+barcode+"' where sample_barcode = '"+sd.getSample_barcode()+"'";
						connection.createStatement().executeUpdate(sql);
						sql = "update examResult_chargingItem set bar_code = '"+barcode+"' where bar_code='" + sd.getSample_barcode()
							+ "' and result_type='sample' and isActive='Y'";
						connection.createStatement().executeUpdate(sql);
					}

				}
			}

			List<PacsSummaryDTO> pacslist = new ArrayList<PacsSummaryDTO>();
			pacslist = getPacsSummaryforexamNum(ei.getExam_num(), chargingCode);
			for (PacsSummaryDTO sd : pacslist) {
				if ((sd.getPacs_req_code() != null)&& (sd.getPacs_req_code().trim().length() > 0)) {
					// 发送pacs去掉接口
						// 执行pacs_summary删除操作
						String sqlpacs = "delete pacs_detail where " + "pacs_req_code='" + sd.getPacs_req_code()
								+ "' and examinfo_num='" + ei.getExam_num() + "'" + " and chargingItem_num='"
								+ ci.getItem_code() + "'";
						connection.createStatement().executeUpdate(sqlpacs);
						int counts = 0;
						sqlpacs = "select COUNT(*) as counts from pacs_detail where pacs_req_code='" + sd.getPacs_req_code() + "'";
						ResultSet rs = connection.createStatement().executeQuery(sqlpacs);
						if (rs.next()) {
							counts = rs.getInt("counts");
						}
						rs.close();
						if (counts <= 0) {
							sqlpacs = "delete pacs_summary  where pacs_req_code ='" + sd.getPacs_req_code()+"'";
							connection.createStatement().executeUpdate(sqlpacs);
						} else {
							String barcode = this.batchService.GetCreateID("pacs_req_num", user.getCenter_num());
							sqlpacs = "update pacs_summary set pacs_req_code='"+barcode+"' where pacs_req_code='"+sd.getPacs_req_code()+"'";
							connection.createStatement().executeUpdate(sqlpacs);
							sqlpacs = "update pacs_detail set pacs_req_code = '"+barcode+"' where pacs_req_code = '"+sd.getPacs_req_code()+"'";
							connection.createStatement().executeUpdate(sqlpacs);
						}
				}else{
					pacsdelfalg=false;
				}				
			}
			
			if(pacsdelfalg&&lisdelfalg){
				String examss = "update examinfo_charging_item set isActive='N',updater=" + userid + ",update_time='"
						+ DateTimeUtil.getDateTime() + "' where exam_num='" + exam_num + "' " + "and charging_item_code='"
						+ chargingCode + "'";
				connection.createStatement().executeUpdate(examss);
				String synstr="delete web_synchro where dataid ='"+exam_num+"' and datatype='5'";
			    connection.createStatement().executeUpdate(synstr);
			    synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+exam_num+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
			    connection.createStatement().executeUpdate(synstr);
				
				connection.commit();	
			}else{
				connection.rollback();
			}
			
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
			}
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
	}
	
	/**
	 * 
	     * @Title: getNoPacsSummaryforexamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examNum
	     * @param: @param chargitemnum
	     * @param: @param sampleid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	private int getNoPacsSummaryforexamNum(Connection connection,String exam_num, String chargitemnum, long sampleid)
			throws ServiceException {
		Statement statement = null;
		int count = 0;
		try {
			// 读取记录数
			String sql = "select count(*) as num from exam_info m,"
					+ "examinfo_charging_item o,pacs_summary a,charging_item b,pacs_detail c  "
					+ "where m.exam_num=o.exam_num "
					+ "and o.isActive='Y' "
					+ "and m.exam_num='"+exam_num+"' "
					+ "and a.examinfo_num=m.exam_num "
					+ "and a.examinfo_sampleId=b.sam_demo_id "
					+ "and o.charge_item_id=b.id "
					+ "and c.chargingItem_num=b.item_code "
					+ "and c.examinfo_num=a.examinfo_num "
					+ "and c.chargingItem_num<>'" + chargitemnum + "'"
					+ "and c.examinfo_sampleId='" + sampleid + "' ";
				
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt("num");
			}
			rs.close();

		} catch (SQLException ex) {			
			ex.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}				
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return count;
	}
	
	/**
	 * 
	     * @Title: updateSampleExamEetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleExamEetail(long examid, long userid) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String sql = "select m.sample_id,m.exam_num from sample_exam_detail m where m.exam_info_id='" + examid
					+ "' and m.status='W'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				String sample_id = rs.getString("sample_id");
				String exam_num = rs.getString("exam_num");
				sql = "select b.BarCode_Class from sample_demo b where b.id='" + sample_id + "' and b.isActive='Y'";
				ResultSet rs1 = connection.createStatement().executeQuery(sql);
				if (rs1.next()) {
					int barcodeclass = rs1.getInt("BarCode_Class");
					if (barcodeclass == 0) {
						sql = "update sample_exam_detail set status='Y' where exam_num='" + exam_num+"'";
						connection.createStatement().executeUpdate(sql);
					}
				}
				rs1.close();
			}
			rs.close();

			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
			}
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
	}
	
	/**
	 * 
	     * @Title: updateSampleExamEetaillis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param sample_exam_detailid
	     * @param: @param userid
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateSampleExamEetaillis(long sample_exam_detailid, long userid) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String sql = "select m.sample_id,m.exam_num from sample_exam_detail m where m.id='" + sample_exam_detailid
					+ "' and m.status='W'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
				String sample_id = rs.getString("sample_id");
				String exam_num = rs.getString("exam_num");
				sql = "select b.BarCode_Class from sample_demo b where b.id='" + sample_id + "' and b.isActive='Y'";
				ResultSet rs1 = connection.createStatement().executeQuery(sql);
				if (rs1.next()) {
					int barcodeclass = rs1.getInt("BarCode_Class");
					if (barcodeclass == 0) {
						sql = "update sample_exam_detail set status='Y' where exam_num='" + exam_num+"'";
						connection.createStatement().executeUpdate(sql);
					}
				}
				rs1.close();
			}
			rs.close();

			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
			}
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
	}
	/**
	 * 
	     * @Title: getItemCodesforExamId   
	     * @Description: 获取所有的itemcode   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  getItemCodesforExamId(String exam_num, String center_num) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		String strs ="";
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			sbt.append("select b.item_code from examinfo_charging_item a ,charging_item b where "
					+ "a.charge_item_id=b.id and a.isActive='Y' and a.pay_status<>'M' and a.exam_status<>'G' and a.exam_num='"+exam_num+"' and a.center_num = '"+center_num+"'");	
			ResultSet rs = statement.executeQuery(sbt.toString());	
			
			while (rs.next()) {
				strs=strs+",'"+rs.getString("item_code")+"'";
			}
			rs.close();	
			if(strs.length()>0) strs=strs.substring(1, strs.length());
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
		return strs;
	}
	
	public ExaminfoSet updateExaminfoSet(ExaminfoSet eis)throws ServiceException {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);
		this.pm.update(eis);
		txManager.commit(txStatus);
		return eis;
	}
	
	public ExaminfoSet saveExaminfoSet(ExaminfoSet eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
		this.pm.save(eis);
		//txManager.commit(txStatus);
		return eis;
	}
	
	public ExaminfoChargingItem updateExaminfoChargingItem(ExaminfoChargingItem eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
			this.pm.update(eis);
		//txManager.commit(txStatus);
		return eis;
	}
	
	public ExaminfoChargingItem saveExaminfoChargingItem(ExaminfoChargingItem eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
		this.pm.save(eis);
		//txManager.commit(txStatus);
		return eis;
	}

	public SampleExamDetail saveSampleExamDetail(SampleExamDetail eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
		this.pm.save(eis);
		//txManager.commit(txStatus);
		return eis;
	}
	
	public ExamResultChargingItem saveExamResultChargingItem(ExamResultChargingItem eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
		this.pm.save(eis);
		//txManager.commit(txStatus);
		return eis;
	}
	
	public PacsSummary savePacsSummary(PacsSummary eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
		this.pm.save(eis);
		//txManager.commit(txStatus);
		return eis;
	}
	
	public Pacsdetail savePacsdetail(Pacsdetail eis)throws ServiceException {
		/*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);*/
		this.pm.save(eis);
		//txManager.commit(txStatus);
		return eis;
	}
	
	public List<ExamInfoUserDTO> getExaminfoFreezeList(String exam_nums) throws ServiceException{
		String sql = "select e.id from exam_info e where e.is_Active = 'Y' and e.exam_num in ("+exam_nums+") and e.freeze = 1";
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		return list;
	}

	@Override
	public String updateGuidePrintStatus(String exam_nums) throws ServiceException {
		String sql = "update exam_info set is_need_guide = 'Y' where exam_num in ("+exam_nums+")";
		this.jpm.executeSql(sql);
		return "ok-更新成功!";
	}

	@Override
	public String updateBarcodePrintStatus(String exam_nums) throws ServiceException {
		String sql = "update exam_info set is_need_barcode = 'Y' where exam_num in ("+exam_nums+")";
		this.jpm.executeSql(sql);
		return "ok-更新成功!";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PacsCountDTO> getPrintItemByExamids(String ids, String center_num) throws ServiceException {
		String sql = " select distinct c.id as charging_id,c.item_name,c.item_code,d.dep_name,c.item_seq,d.seq_code from examinfo_charging_item ec,"
				   + " charging_item c,department_dep d,sample_demo s where ec.charge_item_id = c.id and c.dep_id = d.id and s.id = c.sam_demo_id and"
				   + " ec.exam_num in ('"+ids.replaceAll(",", "','")+"') and ec.center_num = '"+center_num+"' and ec.isActive = 'Y' and d.isPrint_Barcode = '0' and s.isPrint_BarCode = '1' "
				   + " order by d.seq_code,c.item_seq";
		
		List<PacsCountDTO> list = this.jqm.getList(sql, PacsCountDTO.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getExamInfotime   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public int getExamInfotime(long custid) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		int flags=0;
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			sbt.append("select count(*) as num from exam_info where customer_id='"+custid+"' and is_Active='Y' ");	
			ResultSet rs = statement.executeQuery(sbt.toString());			
			if (rs.next()) {
				flags=rs.getInt("num");
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
		return flags;
	}
	
	
	/**
	 * 
	     * <p>Title: getCustomerInfoForId</p>   
	     * <p>Description: </p>   
	     * @param id
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getCustomerInfoForId(long)
	 */
	public CustomerInfo getCustomerInfoForId(long id) throws ServiceException {
		return (CustomerInfo) qm.load(CustomerInfo.class, id);
	}
	
	/**
	 * 
	     * @Title: updateCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ei
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateCustomerInfo(CustomerInfo ei) throws ServiceException {
		this.pm.update(ei);
	}
	
	
	/**
	 * 
	     * <p>Title: getfreeBzExamInfoUserList</p>   
	     * <p>Description: </p>   
	     * @param com_id
	     * @param batchid
	     * @param exam_num
	     * @param groupid
	     * @param status
	     * @param sex
	     * @param username
	     * @param set_id
	     * @param rylb
	     * @param time1
	     * @param time2
	     * @param levels
	     * @param freebzstatus
	     * @param userid
	     * @param centernum
	     * @param pagesize
	     * @param pageno
	     * @param sort
	     * @param order
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getfreeBzExamInfoUserList(long, long, java.lang.String, long, java.lang.String, java.lang.String, java.lang.String, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, long, java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	public PageReturnDTO getfreeBzExamInfoUserList(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,String time1,String time2,String levels,String freebzstatus,long userid,String centernum, int pagesize, int pageno,String sort,String order,UserDTO user,String center_num) throws ServiceException{
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID,dept.dep_name");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,n.group_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num");
		sb.append(" left join company_department dept on dept.id=c._level ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.freeze='0' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		if (com_id>0){
			sb.append(" and c.company_id=" + com_id + " ");
		}
		if(batchid>0){
			sb.append(" and c.batch_id = " + batchid + " ");
		}
		
		if(!StringUtil.isEmpty(exam_num)){
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if(!StringUtil.isEmpty(rylb)){
			sb.append(" and c.customer_type_id = '" + rylb + "' ");
		}
		
		if(groupid>0){
			sb.append(" and c.group_id = '" + groupid + "' ");
		}		
		
		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}
		
		if (!StringUtil.isEmpty(levels)){
			sb.append(" and c._level='" + levels + "' ");
		}
		
		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status = '" + status + "' ");
		}

		if (set_id>0) {
			sb.append(" and m.exam_set_id= '" + set_id + "' ");			
		}
		
		if(!StringUtil.isEmpty(time1)&&(!StringUtil.isEmpty(time2))){
			sb.append(" and c.join_date>= '" + time1 + " 00:00:00' and c.join_date<= '" + time2 + " 23:59:59' ");
		}else{
			//sb.append(" and CONVERT(varchar(50),c.join_date,23)>= '" + DateTimeUtil.getDate2() + "' and CONVERT(varchar(50),c.join_date,23)<= '" + DateTimeUtil.getDate2() + "' ");
		}
		
		if(!StringUtil.isEmpty(freebzstatus))
		{
			if("Y".equals(freebzstatus)){
				sb.append(" and exists (select * from team_account_exam_list l where l.exam_num=c.exam_num ) ");
			}else if("N".equals(freebzstatus)){
				sb.append(" and not exists (select * from team_account_exam_list l where l.exam_num=c.exam_num ) ");
			}
		}
		
		
		if(sort != null && !"".equals(sort)){
			sb.append(" order by "+sort+" "+order);
		}else{
			sb.append(" order by c.update_time desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist=new ArrayList<ExaminfoSetDTO>();
				batchlist=this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size())>0){
					for (ExaminfoSetDTO es:batchlist) {						
						setnums = setnums + "," + es.getSet_name();
					}
					if(setnums.length()>1) setnums=setnums.trim().substring(1,setnums.trim().length());
					eu.setSet_name(setnums);
				}
				
				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if(eu.getRegister_date().indexOf("1900-01-01")>=0)
					{
						eu.setRegister_date("");
					}
				}
				
				if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if(eu.getJoin_date().indexOf("1900-01-01")>=0)
					{
						eu.setJoin_date("");
					}
				}
               
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				
				eu.setLis(eu.getYlis()+"/"+eu.getWlis());
				eu.setPacs(eu.getYpacs()+"/"+eu.getWpacs());				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: updateExamInfofreezeDo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamInfofreezeDo(String ids, long userid, String center_num) throws ServiceException {
		String[] idslist = ids.split(",");
		for (String exam_num : idslist) {
			Connection connection = null;
			try {
				connection = this.jqm.getConnection();
				String sql = "update exam_info set freeze=1,updater="+userid+",update_time='" + DateTimeUtil.getDateTime()
						+ "' where exam_num = '" + exam_num + "' ";
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
		}
		return "ok-处理完成";
	}
	
	/**
	 * 
	     * @Title: updateExamInfofUnreezeDo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamInfofUnreezeDo(String ids, long userid, String center_num) throws ServiceException {
		String[] idslist = ids.split(",");
		for (String exam_num : idslist) {
			Connection connection = null;
			try {
				connection = this.jqm.getConnection();
				String sql = "update exam_info set freeze=0,updater="+userid+",update_time='" + DateTimeUtil.getDateTime()
						+ "' where exam_num = '" + exam_num + "' ";
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
		}
		return "ok-处理完成";
	}
	
	/**
	 * 
	     * @Title: getFreezeTermExamInfoshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param exam_num
	     * @param: @param groupid
	     * @param: @param status
	     * @param: @param sex
	     * @param: @param username
	     * @param: @param set_id
	     * @param: @param rylb
	     * @param: @param joinsdate
	     * @param: @param joinedate
	     * @param: @param update
	     * @param: @param levels
	     * @param: @param userid
	     * @param: @param centernum
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getFreezeTermExamInfoshow(long com_id,long batchid,String exam_num,long groupid,String status,String sex,String username, long set_id,String rylb,String joinsdate,String joinedate,String updates,String updatee,String levels,long userid,String centernum, int pagesize, int pageno,String sort,String order,UserDTO user,String center_num) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限 Y有N无
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID,dept.dep_name");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,n.group_name,"
				+ "c.register_date,c.join_date,c.update_time,c.exam_times,x.chi_name,c.is_guide_back ");
		sb.append(" from customer_info a,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.updater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num");
		sb.append(" left join company_department dept on dept.id=c._level ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.freeze='1'  ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		
		if(com_id>0){
			sb.append(" and c.company_id=" + com_id + " ");
		}
		
		if(batchid>0){
			sb.append(" and c.batch_id = '" + batchid + "' ");
		}		
		
		if(!StringUtil.isEmpty(exam_num)){
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if(!StringUtil.isEmpty(rylb)){
			sb.append(" and c.customer_type_id = '" + rylb + "' ");
		}
		
		if(groupid>0){
			sb.append(" and c.group_id = '" + groupid + "' ");
		}		
		
		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}
		
		if (!StringUtil.isEmpty(levels)){
			sb.append(" and c._level='" + levels + "' ");
		}
		
		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status = '" + status + "' ");
		}

		if (set_id>0) {
			sb.append(" and m.exam_set_id= '" + set_id + "' ");
			
		}
			
		if(!StringUtil.isEmpty(joinsdate)&&(!StringUtil.isEmpty(joinedate))){
			sb.append(" and c.join_date>= '" + joinsdate + " 00:00:00' and c.join_date<= '" + joinedate + " 23:59:59' ");
		}else{
			//sb.append(" and CONVERT(varchar(50),c.join_date,23)>= '" + DateTimeUtil.getDate2() + "' and CONVERT(varchar(50),c.join_date,23)<= '" + DateTimeUtil.getDate2() + "' ");
		}

		if(!StringUtil.isEmpty(updates)&&(!StringUtil.isEmpty(updatee))){
			sb.append(" and c.update_time>= '" + updates + " 00:00:00' and c.update_time<= '" + updatee + " 23:59:59' ");
		}else{
			//sb.append(" and CONVERT(varchar(50),c.join_date,23)>= '" + DateTimeUtil.getDate2() + "' and CONVERT(varchar(50),c.join_date,23)<= '" + DateTimeUtil.getDate2() + "' ");
		}
		
		if(sort != null && !"".equals(sort)){
			sb.append(" order by "+sort+" "+order);
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist=new ArrayList<ExaminfoSetDTO>();
				batchlist=this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size())>0){
					for (ExaminfoSetDTO es:batchlist) {						
						setnums = setnums + ","+ es.getSet_name();
					}
					if(setnums.length()>1) setnums=setnums.trim().substring(1,setnums.trim().length());
					eu.setSet_name(setnums);
				}
				
				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if(eu.getRegister_date().indexOf("1900-01-01")>=0)
					{
						eu.setRegister_date("");
					}
				}
				if((eu.getUpdate_time()!=null)&&(eu.getUpdate_time().trim().length()>10)){					
					eu.setUpdate_time(eu.getUpdate_time().trim().substring(0, 10));
					if(eu.getUpdate_time().indexOf("1900-01-01")>=0)
					{
						eu.setUpdate_time("");
					}
				}
				
				if((eu.getPrintdate()!=null)&&(eu.getPrintdate().trim().length()>10)){					
					if(eu.getPrintdate().indexOf("1900-01-01")>=0)
					{
						eu.setPrintdate("");
					}else{
						eu.setPrintdate(eu.getPrintdate());
					}
				}
				
				if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if(eu.getJoin_date().indexOf("1900-01-01")>=0)
					{
						eu.setJoin_date("");
					}
				}
             
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				
				eu.setLis(eu.getYlis()+"/"+eu.getWlis());
				eu.setPacs(eu.getYpacs()+"/"+eu.getWpacs());				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getcustomerType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String  getcustomerTypeForName(String ids) throws ServiceException {		
		Connection connection = null;
		String name="";
		try {
			connection = this.jqm.getConnection();
			String sql  = "select id,type_name FROM customer_type where id in ("+ids+")";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				name=name+rs.getString("type_name")+",";
			}
			rs.close();
				
		} catch (SQLException ex) {
			
		} finally {
			try {

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return name;
	}
	
	/**
	 * 
	     * @Title: insertExamInfoExt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @param cif
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void insertExamInfoExt(Connection connection, ExamInfoExt cif) throws Exception,ServiceException {
		String sql = "insert into exam_ext_info(exam_num,arch_num,occutypeofworkid,occusectorid,"
				+ "occusector,occutypeofwork,joinDatetime,employeeage,damage,remark,creater,create_time,updater,update_time) "
				+ "values ('" + cif.getExam_num() + "','" + cif.getArch_num() + "','" + cif.getOccutypeofworkid()
				+ "','" + cif.getOccusectorid() + "','" + cif.getOccusector() + "','" + cif.getOccutypeofwork() + "','"
				+ cif.getJoinDatetime() + "'," + cif.getEmployeeage() + "," + cif.getDamage() + ",'',"
				+ cif.getCreater() + ",'" + cif.getCreate_time() + "'," + cif.getUpdater() + ",'" + cif.getUpdate_time()
				+ "')";
		connection.createStatement().executeUpdate(sql);
	}

	/**
	 * 
	     * @Title: updateExamInfoExt   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param connection
	     * @param: @param cif
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void updateExamInfoExt(Connection connection, ExamInfoExt cif) throws Exception,ServiceException {
		String sql = "update exam_ext_info set arch_num='" + cif.getArch_num() + "',occutypeofworkid='"
				+ cif.getOccutypeofworkid() + "',occusectorid='" + cif.getOccusectorid() + "',occusector='"
				+ cif.getOccusector() + "',occutypeofwork='" + cif.getOccutypeofwork() + "',joinDatetime='"
				+ cif.getJoinDatetime() + "',employeeage=" + cif.getEmployeeage() + ",damage=" + cif.getDamage()
				+ ",remark='',creater=" + cif.getCreater() + ",create_time='" + cif.getCreate_time() + "',updater="
				+ cif.getUpdater() + ",update_time='" + cif.getUpdate_time() + "' where exam_num='" + cif.getExam_num()
				+ "'";
		connection.createStatement().executeUpdate(sql);
	}
	
	/**
	 * 
	     * @Title: setExamInfoHYByComID   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param comdid
	     * @param: @param hy_code
	     * @param: @param userid      
	     * @return: void      
	     * @throws
	 */
	public void setExamInfoHYByComID(long comdid,String hy_code,long userid) throws ServiceException{
		String depSql = "SELECT a.exam_num,b.arch_num FROM exam_info a,customer_info b where a.customer_id=b.id and a.is_Active='Y' and b.is_Active='Y' and a.company_id = '"+comdid+"' ";
		List<ExamInfoDTO> depList = this.jqm.getList(depSql, ExamInfoDTO.class);
		for(ExamInfoDTO ei:depList){
			String sql = "select exam_num,arch_num,occutypeofworkid,occusectorid,occusector,"
					+ "occutypeofwork,joinDatetime,employeeage,damage,remark,creater,create_time,"
					+ "updater,update_time FROM exam_ext_info where exam_num='" + ei.getExam_num() + "'  ";
			List<ExamInfoExt> hyList= this.jqm.getList(sql, ExamInfoExt.class);			
			if ((hyList != null) &&(hyList.size() > 0)) {
				String sqlhy = "update exam_ext_info set arch_num='" + ei.getArch_num() + "',occusectorid='" 
			    + hy_code + "',updater="+ userid + ",update_time='" + DateTimeUtil.getDateTime() + "' where exam_num='" + ei.getExam_num()+ "'";
                this.jpm.executeSql(sqlhy);
			}else{
				String sqlhy = "insert into exam_ext_info(exam_num,arch_num,occusectorid,creater,create_time,updater,update_time) "
						+ "values ('" + ei.getExam_num() + "','" + ei.getArch_num() + "','" + hy_code + "',"
						+ userid + ",'" + DateTimeUtil.getDateTime()  + "'," + userid + ",'" + DateTimeUtil.getDateTime() 
						+ "')";
				 this.jpm.executeSql(sqlhy);
			}
		}
	}

	@Override
	public ExamInfoExt updatebunk(ExamInfoExt ex) throws ServiceException {
		this.pm.update(ex);
		return ex;
	}

	@Override
	public void savebunk(ExamInfoExt ex) throws ServiceException {
		String sql=" insert into exam_ext_info(exam_num,arch_num,bunk,allocationdate,creater) values('"+ex.getExam_num()+"','"+ex.getArch_num()+"','"+ex.getBunk()+"',getDate(),'"+ex.getCreater()+"') ";
		Connection con=null;
		try {
			con=this.jqm.getConnection();
			con.createStatement().executeUpdate(sql);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		
	}

	@Override
	public ExamInfoExt querybyexam_num(String exam_num) throws ServiceException {
		String hql="from ExamInfoExt where exam_num='"+exam_num+"' ";
		List<ExamInfoExt> list=this.qm.find(hql);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageReturnDTO getbunkExamInfoUserList(long com_id, String art_num,
			String exam_num, String employeeID, String status, String id_num,
			String username, String starttime, String enddate, String time3,
			String time4, String createtime, String examtype, String apptype,
			String userid, String centerid, int pagesize, int pageno,
			String sort, String order,String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,ex.bunk,convert(varchar(100),ex.allocationdate,23) as allocationdate,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		//sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 21)) as wpacs ");
		//sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 21)) as ypacs ");
		//sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 131)) as wlis ");
		//sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 131)) as ylis ");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,m.dep_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.apptype ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join exam_ext_info ex  on c.exam_num=ex.exam_num  ");
		sb.append(" left join user_usr x on x.id=ex.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id  and j.center_num = '"+center_num+"'");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and  c.center_num='" + centerid + "' ");		
		
		if(!StringUtil.isEmpty(apptype)){
				sb.append(" and c.apptype = '" + apptype + "' ");
			}
			
		if (com_id > 0) {
			sb.append(" and c.company_id=" + com_id + " ");
		}
		
		if(!StringUtil.isEmpty(exam_num)){
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if(!StringUtil.isEmpty(id_num)){
			sb.append(" and a.id_num = '" + id_num + "' ");
		}
		
		if (!StringUtil.isEmpty(employeeID)) {
			sb.append(" and c.employeeID like '%" + employeeID + "%' ");
		}
		
		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
		}

		if ((!StringUtil.isEmpty(starttime))&&(!StringUtil.isEmpty(enddate))) {
			sb.append(" and c.join_date >= '" + starttime + " 00:00:00' ");
			sb.append(" and c.join_date <= '" + enddate + " 23:59:59' ");
		}
		
		if ((!StringUtil.isEmpty(time3))&&(!StringUtil.isEmpty(time4))) {
			sb.append(" and c.final_date >= '" + time3 + " 00:00:00' ");
			sb.append(" and c.final_date <= '" + time4 + " 23:59:59' ");
			sb.append(" and c.status = 'Z' ");
		}else{
			if (!StringUtil.isEmpty(status)) {
				sb.append(" and c.status = '" + status + "' ");
			}
		}
		
		if(!StringUtil.isEmpty(createtime)){
			sb.append(" and c.create_time = '" + createtime + " 23:59:59' ");
		}
		
		if(!StringUtil.isEmpty(examtype)){
			sb.append(" and c.exam_type = '" + examtype + "' ");
		}
		
		if(sort != null && !"".equals(sort)){
			sb.append(" order by "+sort+" "+order);
		}else{
			sb.append(" order by c.update_time desc ");
		}
		
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist=new ArrayList<ExaminfoSetDTO>();
				batchlist=this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size())>0){
					for (ExaminfoSetDTO es:batchlist) {						
						setnums = setnums + ","+ es.getSet_name();
					}
					if(setnums.length()>1) setnums=setnums.trim().substring(1,setnums.trim().length());
					eu.setSet_name(setnums);
				}
				
				if((eu.getRegister_date()!=null)&&(eu.getRegister_date().trim().length()>10)){					
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if(eu.getRegister_date().indexOf("1900-01-01")>=0)
					{
						eu.setRegister_date("");
					}
				}
				
				if((eu.getJoin_date()!=null)&&(eu.getJoin_date().trim().length()>10)){					
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if(eu.getJoin_date().indexOf("1900-01-01")>=0)
					{
						eu.setJoin_date("");
					}
				}
               
				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());
				
				eu.setLis(eu.getYlis()+"/"+eu.getWlis());
				eu.setPacs(eu.getYpacs()+"/"+eu.getWpacs());				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}
	public CustomerJsonDTO getExaminfoSet(RegisterModel model) throws ServiceException {
		CustomerJsonDTO c = new CustomerJsonDTO();
		if(model.getExam_id()>0){
			
				Connection con = null;
			    String sql = "";
			    int cont = 0;
			    try {
					con = this.jqm.getConnection();
					sql = " select  case  when (select count(*) from examinfo_set where  isActive='Y'  and examinfo_id='"+model.getExam_id()+"')>0 "
			    			+" or  (SELECT count(*) FROM  examinfo_charging_item  where  isActive='Y'  and examinfo_id='"+model.getExam_id()+"' )>0 "
			    			+" then 1 else 0  end ";
					ResultSet rs  = con.createStatement().executeQuery(sql);
					if(rs.next()){
						cont = rs.getInt(1);
						System.out.println(cont);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					 try {
						  if(con!=null){
							  con.close();
						  }
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			    if(cont==0){
			    	   sql ="SELECT e.id,ci.user_name,ci.arch_num,ci.sex,ci.id_num,ci.phone,ci.address,ci.birthday,e.exam_num, "
							+" e.age,e.is_marriage,e.email,e.exam_type,com.com_name as company,e.budget_amount budget "	  
							+" FROM customer_info ci,exam_info  e	"
							+" left join company_info com on  com.id=e.company_id 	"
							+" where  e.is_Active='Y'  and  ci.is_Active='Y'	 and ci.id=e.customer_id  and  e.id='"+model.getExam_id()+"' ";
			    	   List<CustomerJsonDTO> li  = this.jqm.getList(sql, CustomerJsonDTO.class);
			    	   c = li.get(0);
			    }
		}
		return c;
	}

	public int updatePatientId(String pid,String exam_num) throws ServiceException {
		Connection con = null;
		String sql = "";
		int st = 0;
		try {
			sql = "update    exam_info    set   patient_id = '"+pid+"'   where  exam_num='"+exam_num+"'";
			con = jqm.getConnection();
			int re = con.createStatement().executeUpdate(sql);
			if(re>0){
				st = 1;
			}
		} catch (SQLException e) {
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
		return st;
	}

	public int getPatientId(long exam_info_id) throws ServiceException {
		int fal = 0 ;
		String sql = " SELECT patient_id FROM exam_info  where  id='"+exam_info_id+"'";
		List<ExamInfoDTO> li = this.jqm.getList(sql,ExamInfoDTO.class);
		if(li.get(0).getPatient_id()!=null && !"".equals(li.get(0).getPatient_id())){
			fal = 1;
		}
		return fal;
	}
	
	
	/**
	 * 
	     * @Title: InsertWebSynchro   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param type
	     * @param: @param disesId      
	     * @return: void      
	     * @throws
	 */
	public void InsertWebSynchro(char type,String dataid) throws ServiceException {
		List<WebSynchro> list = this.qm.find("from WebSynchro where datatype = '"+ type+"' and dataid='"+dataid+"'");
		if((list!=null)&&(list.size()>0)){
			WebSynchro ws= new WebSynchro();
			ws=(WebSynchro)list.get(0);
			this.pm.remove(ws);
		}
		WebSynchro ws= new WebSynchro();
		ws.setCreate_time(DateTimeUtil.parse());
		ws.setDataid(dataid);
		ws.setDatatype(type);
		ws.setSynchro('0');
		ws.setSynchrodate(DateTimeUtil.parse());
		this.pm.save(ws);
	}
	
	/**
	 * 
	     * @Title: getDjtExamInfoUserList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param art_num
	     * @param: @param exam_num
	     * @param: @param username
	     * @param: @param starttime
	     * @param: @param enddate
	     * @param: @param centerid
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getyuyueOrderList(String exam_num,String status,String username, String starttime,String enddate,String examtype,String orderid,String phone,int pagesize, int pageno) throws ServiceException{
		StringBuffer sb = new StringBuffer();
				
		sb.append(" select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,m.create_time,m.amount as budget_amount,m.status as orderstatus");

		sb.append(" ,c.is_need_guide,c.phone,c.company,c.order_id,"
				+ "c.register_date,c.join_date,c.exam_times,c.is_guide_back,c.update_time ");
		sb.append(" from customer_info a ,exam_info c,trjn m ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and m.exam_num=c.exam_num ");	
		if(!StringUtil.isEmpty(exam_num)){
		   sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		if(!StringUtil.isEmpty(orderid)){
			sb.append(" and c.order_id = '" + orderid + "' ");
		}
			
		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if ((!StringUtil.isEmpty(starttime))&&(!StringUtil.isEmpty(enddate))) {
			sb.append(" and m.create_time >= '" + starttime + " 00:00:00' ");
			sb.append(" and m.create_time <= '" + enddate + " 23:59:59' ");
		}
		
		if (!StringUtil.isEmpty(status)) {
			sb.append(" and m.status = '" + status + "' ");
		}
				
		if(!StringUtil.isEmpty(examtype)){
			sb.append(" and c.exam_type = '" + examtype + "' ");
		}
		
		if(!StringUtil.isEmpty(phone)){
			sb.append(" and c.phone = '" + phone + "' ");
		}
			
		System.out.println("----"+sb.toString());
		PageSupport map = this.jqm.getList(sb.toString(),sb.toString(), pageno, pagesize, ExamInfoOrderDTO.class);
		//List<ExamInfoOrderDTO> list = new ArrayList<ExamInfoOrderDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if((map!=null)&&(map.getList()!=null)){
		webrole.setRows(map.getList());
		}
		return webrole;
	}
	
	/**
	 * 
	     * <p>Title: getExamNumGetExamInfoUserDTO</p>   
	     * <p>Description: </p>   
	     * @param exam_num
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getExamNumGetExamInfoUserDTO(java.lang.String)
	 */
	public ExamInfoUserDTO getExamNumGetExamInfoUserDTO(String exam_num) throws ServiceException {
	String sql = "	SELECT  e.id ,e.group_id ,e.customer_id ,e.exam_num ,e.status ,e.register_date ,e.join_date ,e.final_date  "
	+"	,e.final_doctor ,e.exam_type ,e.customer_type ,e.is_sampled_directly ,e.is_adjusted ,e.center_num ,e.getReportWay  "
	+"	,e.reportAddress ,e.chargingType ,e.customerType ,e.group_index ,e.is_Active ,e.creater ,e.create_time ,e.updater "
	+"	,e.update_time ,e.is_guide_back ,e.company_check_status ,e.customer_type_id ,e.is_marriage  ,e.age ,e.address ,e.email "
	+"	 ,e.phone ,e.company ,e.position ,e._level ,e.picture_path ,e.is_after_pay ,e.past_medical_history ,e.remarke ,e.introducer ,  "
	+"	 e.counter_check ,e.guide_nurse ,e.appointment ,e.data_source ,e.others ,e.order_id ,e.exam_times ,e.company_id ,e.batch_id ,  "
	+"	 e.is_report_print ,e.patient_id ,e.exam_indicator ,e.clinic_no ,e.employeeID ,e.mc_no ,e.visit_date ,e.visit_no ,  "
	+"	 e.is_need_guide ,e.is_need_barcode ,e.freeze ,e.is_report_tidy ,e.report_tidy_user ,e.report_tidy_time ,e.budget_amount, "
	+"	 c.arch_num ,c.user_name ,c.id_num ,c.sex ,c.birthday ,c.nation ,c.is_Active ,c.creater ,c.create_time ,c.updater, "
	+"	c.update_time ,c.membership_card ,c.medical_insurance_card ,c.phone ,c.address ,c.email ,c.flag  "
	+"	  FROM exam_info  e,customer_info c where  c.id = e.customer_id   and   e.exam_num = '"+exam_num+"'"; 
	//System.out.println(sql);
		List<ExamInfoUserDTO> li = this.jqm.getList(sql, ExamInfoUserDTO.class);
		return li.get(0);
	}
	public CustomerInfoDTO getCustomerInfoByVisitNo(String visit_no) throws ServiceException{
		String sql = "select c.id,c.arch_num,c.user_name from customer_info c,customer_visit_card v "
				+ "where c.id = v.customer_id and v.visit_no = '"+visit_no+"'";
		List<CustomerInfoDTO> list = this.jqm.getList(sql, CustomerInfoDTO.class);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	public CopyItemDTO getcopaitem(RegisterModel model,String center_num) throws ServiceException {
		CopyItemDTO co = new CopyItemDTO();
		String sql = "  SELECT eci.examinfo_id,ci.id,ci.item_code,ci.item_name,ci.sex,ci.isActive,ci.item_type, "
				+ " d.dep_name,eci.amount,eci.discount,eci.item_amount FROM   "
				+ "  examinfo_charging_item eci,charging_item  ci,department_dep d ,department_vs_center de  WHERE  "
				+ " eci.isActive='Y'  and de.dep_id = d.id   AND   ci.isActive='Y'    AND  d.isActive='Y'  AND   "
				+ "  ci.item_code=eci.charging_item_code  and de.center_num ='"+center_num+"'  AND   ci.dep_id=d.id   " + "  AND "
				+ "  eci.exam_num= (SELECT  TOP 1 ai.exam_num FROM (SELECT  top 2 ei.id,ei.exam_num FROM  customer_info  ci,exam_info ei where  "
				+ " ci.id=ei.customer_id  AND  ci.is_Active='Y'  AND  ci.id='" + model.getCustomer_id()
				+ "'  AND  ei.is_Active='Y'  " + "  ORDER  BY  ei.register_date  desc) AS  ai   order  by  ai.id)  ";
		List<GroupChargingItemDTO> li = this.jqm.getList(sql, GroupChargingItemDTO.class);
		List<ExamSetDTO> se = new ArrayList<ExamSetDTO>();

		if (li.size() > 0) {

			sql = "  SELECT et.id,et.set_num,et.set_name,et.sex,et.set_discount,et.set_amount FROM examinfo_set es,exam_set  et  WHERE   "
					+ "  es.isActive='Y'  AND   et.is_Active='Y'  and et.center_num='"+center_num+"' AND es.exam_set_id = et.id AND  "
					+ " es.exam_num=(SELECT  TOP 1 ai.exam_num FROM (SELECT  top 2 ei.id,ei.exam_num FROM  customer_info  ci,exam_info ei where  "
					+ " ci.id=ei.customer_id  AND  ci.is_Active='Y'  AND  ci.id='" + model.getCustomer_id()
					+ "'  AND  ei.is_Active='Y'  "
					+ "  ORDER  BY  ei.register_date  desc) AS  ai   order  by  ai.id)  ";
			se = this.jqm.getList(sql, ExamSetDTO.class);
		}
		String s_id = "";
		for (int j = 1; j < se.size() + 1; j++) {
			if (j % 2 == 0) {
				s_id += "," + se.get(j - 1).getId();
			} else {
				s_id += se.get(j - 1).getId() + "";
			}
		}
		if(s_id !=null && !"".equals(s_id) ){
			sql = " SELECT se.set_num,si.charging_item_id  as  id FROM  set_charging_item si "
					+ "  left  join  exam_set se  on  se.id = si.exam_set_id   AND  se.is_Active='Y'  "
					+ "   where se.center_num='"+center_num+"' and exam_set_id in (" + s_id + ") ";
			List<GroupChargingItemDTO> s_li = this.jqm.getList(sql, GroupChargingItemDTO.class);
			for (int i = 0; i < li.size(); i++) {
				for (int o = 0; o < s_li.size(); o++) {
					if (li.get(i).getId() == s_li.get(o).getId()) {
						li.get(i).setSet_num(s_li.get(o).getSet_num());
						break;
					}
				}
			}
		}
		co.setLi(li);
		co.setSe(se);

		return co;
	}

	public CopyItemDTO getcopaitemOther(RegisterModel model,String center_num) throws ServiceException {
		CopyItemDTO co = new CopyItemDTO();
		String sql = " SELECT eci.examinfo_id,ci.id,ci.item_code,ci.item_name,ci.sex,ci.isActive,ci.item_type, "
				+ " d.dep_name,eci.amount,eci.discount,eci.item_amount FROM   "
				+ "  examinfo_charging_item eci,charging_item  ci,department_dep d ,department_vs_center de  WHERE  "
				+ " eci.isActive='Y'  and de.dep_id = d.id  and de.center_num ='"+center_num+"'   AND   ci.isActive='Y'    AND  d.isActive='Y'  AND   "
				+ "  ci.item_code=eci.charging_item_code  AND   ci.dep_id=d.id   " + "  AND " + "  eci.exam_num= '"
				+ model.getExam_num() + "' ";
		List<GroupChargingItemDTO> li = this.jqm.getList(sql, GroupChargingItemDTO.class);
		List<ExamSetDTO> se = new ArrayList<ExamSetDTO>();

		if (li.size() > 0) {
			sql = "  SELECT et.id,et.set_num,et.set_name,et.sex,et.set_discount,et.set_amount FROM examinfo_set es,exam_set  et  WHERE   "
					+ "  es.isActive='Y'  and et.center_num='"+center_num+"' AND   et.is_Active='Y'   AND es.exam_set_id = et.id AND  "
					+ " es.exam_num='" + model.getExam_num() + "' ";
			se = this.jqm.getList(sql, ExamSetDTO.class);
		}
		String s_id = "";
		for (int j = 1; j < se.size() + 1; j++) {
			if (j % 2 == 0) {
				s_id += "," + se.get(j - 1).getId();
			} else {
				s_id += se.get(j - 1).getId() + "";
			}
		}
		if(s_id!=null && !"".equals(s_id)){
			sql = " SELECT se.set_num,si.charging_item_id  as  id FROM  set_charging_item si "
				+ "  left  join  exam_set se  on  se.id = si.exam_set_id   AND  se.is_Active='Y'  "
				+ "   where se.center_num='"+center_num+"' and exam_set_id in (" + s_id + ") ";
			List<GroupChargingItemDTO> s_li = this.jqm.getList(sql, GroupChargingItemDTO.class);
			for (int i = 0; i < li.size(); i++) {
				for (int o = 0; o < s_li.size(); o++) {
					if (li.get(i).getId() == s_li.get(o).getId()) {
						li.get(i).setSet_num(s_li.get(o).getSet_num());
						break;
					}
				}
			}
		}
		co.setLi(li);
		co.setSe(se);
		return co;
	}
	public DiversInfoDTO getjiuzhenkaCard(String center_num) throws ServiceException {
		String cof = "";
		try {
			 cof = this.customerInfoService.getCenterconfigByKey("IS_JIUZHENKA_DUKAQI", center_num).getConfig_value().trim();
		} catch (Exception e) {
			System.out.println("缺少就诊卡配置IS_JIUZHENKA_DUKAQI");
			e.printStackTrace();
		}
		String sql = "SELECT code,com_name,com_type,com_type_name,com_ocx_name,is_active,remark"
				+ "  FROM driver_info where code='" + cof + "' and is_active='Y'  and  com_type='3' ";
		List<DiversInfoDTO> li = this.jqm.getList(sql,DiversInfoDTO.class);
		DiversInfoDTO d = new DiversInfoDTO();
		if(li !=null && li.size() > 0){
			d = li.get(0);
		}
		return d;
	}
	@Override
	public List<CustomerVisitCardDTO> getjiuzhenka(long custerm_id,int pageSize,int page) throws ServiceException {
		String sql = " SELECT c.id,c.visit_no,u.chi_name,c.update_time FROM  customer_visit_card c,user_usr u  WHERE c.updater = u.id   and  customer_id = '" + custerm_id
				+ "'  order  BY  c.id   desc";
		List<CustomerVisitCardDTO> li = this.jqm.getList(sql, CustomerVisitCardDTO.class);
		return li;
	}

	@Override
	public void savejiuzhenka(RegisterModel model, UserDTO user) throws ServiceException {
		CustomerVisitCard c = new CustomerVisitCard();
		c.setCreate_time(DateTimeUtil.parse());
		c.setCreater(user.getUserid());
		c.setUpdater(user.getUserid());
		c.setUpdate_time(DateTimeUtil.parse());
		c.setVisit_no(model.getVisit_no());
		c.setVisit_status("Y");
		c.setCustomer_id(model.getCustomer_id());
		this.pm.save(c);
	}

	@Override
	public int deletejiuzhenka(long id) throws ServiceException {
		Connection con = null;
		String sql = "";
		Statement st = null;
		int result = 0;
		try {
			con = this.jqm.getConnection();
			// 3.创建语句
			st = con.createStatement();
			sql = " delete customer_visit_card  where  id in (" + id + ")";
			result = st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int getvalidatevilit(RegisterModel model) throws ServiceException {
		String sql = " select id from customer_visit_card  where visit_no='" + model.getVisit_no().trim() + "'"
				+ "   and customer_id = '" + model.getCustomer_id() + "'";
		List<CustomerVisitCardDTO> li = this.jqm.getList(sql, CustomerVisitCardDTO.class);
		if (li.size() > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	/**
	 * 
	     * @Title: getImpCustomerInfoErrorList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param com_id
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	public List<ImpCustomerInfoDTO> getImpCustomerInfoErrorList(long batch_id, long com_id,long userid)
			throws ServiceException {
		String sql = "select id,company_id,batch_id,contract_id,arch_num,id_num,sex,"
				+ "birthday,custname,age,is_marriage,position,_level,tel,remark,"
				+ "customer_type,others,flags,notices,creater,create_time,exam_type,employeeID,visit_no,introducer,idtypename,exam_indicator,exam_class"
				+ " From  impcustomerInfo where batch_id=" + batch_id + " and company_id=" + com_id + " and creater="
				+ userid + " and flags<>'0' order by create_time desc";
		 List<ImpCustomerInfoDTO> list = new ArrayList<ImpCustomerInfoDTO>();
		 list= this.jqm.getList(sql,ImpCustomerInfoDTO.class);		
		return list;
	}
	
	/**
	 * 
	     * @Title: getBatchAndCom   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @param comid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: MenuDTO      
	     * @throws
	 */
	public BatchDTO getBatchAndCom(long batchid,long comid) throws ServiceException {
		String sql = " select b.batch_name,a.com_name as contact_name from batch b  left join company_info a on a.id=b.company_id "
					+ "where b.id='"+batchid+"' and b.company_id='"+comid+"'";
		List<BatchDTO> hyList = this.jqm.getList(sql, BatchDTO.class);
		BatchDTO b= new BatchDTO();
		if((hyList!=null)&&(hyList.size()>0)){
			b=hyList.get(0);
		}
		return b;
	}
	
	/**
	 * 
	     * @Title: getExaminfoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examinfo
	     * @param: @param chargitemid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItemDTO      
	     * @throws
	 */
	public ExaminfoChargingItemDTO getExaminfoChargingItem(String exam_num, String chargitemCode) throws ServiceException {
		String sql = " select id,examinfo_id,charge_item_id,exam_indicator,item_amount,discount,amount,"
				+ "isActive,final_exam_date,pay_status,exam_status,is_new_added,exam_date,creater,create_time,"
				+ "updater,update_time,check_status,exam_doctor_id,exam_doctor_name,add_status,calculation_amount,"
				+ "is_application,change_item,team_pay,personal_pay,team_pay_status,his_req_status,itemnum from examinfo_charging_item "
				+ "where isActive='Y' and exam_num='" + exam_num + "' and charging_item_code='" + chargitemCode + "'";
		List<ExaminfoChargingItemDTO> hyList = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		ExaminfoChargingItemDTO b = new ExaminfoChargingItemDTO();
		if ((hyList != null) && (hyList.size() > 0)) {
			b = hyList.get(0);
		}
		return b;
	}
	
	/**
	 * 
	     * @Title: countUserGroup   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batchid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String countUserGroup(long batchid)throws ServiceException {
		Connection connection = null;
		long totalcount=0;
		long okcount=0;
		try {
			connection = this.jqm.getConnection();
			String sql = "select count(*) as totalcount from exam_info a where a.group_id>0 and a.is_Active='Y' and a.batch_id='"+batchid+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				totalcount=rs.getLong("totalcount");
			}
			rs.close();
			
			sql = "select count(*) as totalcount from exam_info a where a.is_Active='Y' and a.group_id<=0 and a.batch_id='"+batchid+"'";
			rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				okcount=rs.getLong("totalcount");
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
		return totalcount+"-"+okcount;
	}

	public CustomerInfoDTO getCustomerinfoByArchNum(String arch_num) throws ServiceException{
		String sql = "select c.arch_num,c.user_name,c.birthday,c.id_num,c.nation,c.sex from "
				+ "customer_info c where c.arch_num = '"+arch_num+"' and c.is_Active = 'Y'";
		List<CustomerInfoDTO> list = this.jqm.getList(sql, CustomerInfoDTO.class);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 
	     * @Title: getImpCustomerInfoErrorList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param batch_id
	     * @param: @param com_id
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ImpCustomerInfoDTO>      
	     * @throws
	 */
	public void delImpCustomerInfoErrorList(long batch_id, long com_id,long userid)	throws ServiceException{
		String sql = "delete From  impcustomerInfo where batch_id=" + batch_id + " and company_id=" + com_id + " and creater="
				+ userid + " and flags<>'0' ";
		this.jpm.executeSql(sql);
	}

	@Override
	public String isSynchroExamInfo(String ids) throws ServiceException {
		String sql = "";
		Connection connection = null;
		String str = "error-同步修改失败！";
		try {
			connection = this.jqm.getConnection();
			sql = "update exam_info set is_upload= 1 ,update_time='"+ DateTimeUtil.getDateTime() + "' where id in ('" + ids.replaceAll("'", "','") + "') ";
			connection.createStatement().executeUpdate(sql);
			
			String synstr="delete web_synchro where dataid in('" + ids.replaceAll("'", "','") + "') and datatype='5'";
			connection.createStatement().executeUpdate(synstr);
			String [] arr = ids.split(",");
			for(int i=0;i<arr.length;i++){
				synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+arr[i]+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
				connection.createStatement().executeUpdate(synstr);
			}
			
			str = " ok-同步成功！";
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
		return str;
	}
	
	
	@Override
	public String weiXinIsSynach(String exam_num) throws ServiceException {
		String str = "error-同步失败！";
		Connection connection = null;
		try {
			connection = jqm.getConnection();
			//如果为1 同步此次修改信息
			int isUpload = djtCustomerInfoService.queryIsUploadSynach(connection, exam_num);
			if(isUpload==1) {
				String synstr="delete web_synchro where dataid ='"+exam_num+"' and datatype='8'";
				connection.createStatement().executeUpdate(synstr);
				synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('8','"+exam_num+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
				connection.createStatement().executeUpdate(synstr);
			}
			str = "ok-同步成功";
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
		return str;
	}

	@Override
	public Introducer saveIntroducer(Introducer introducer) throws ServiceException {
		this.pm.save(introducer);
		return introducer;
	}

	@Override
	public List<Introducer> queryIntroducerList(String introducer) throws ServiceException {
		String sql = "select * from introducer ";
		if(!StringUtil.isEmpty(introducer)) {
			sql += " where name = '"+introducer+"'";
		}
		List<Introducer> list = jqm.getList(sql, Introducer.class);
		return list;
	}
	
	
	@Override
	public String reportUploadExamInfo(String ids) throws ServiceException {
		String sql = "";
		Connection connection = null;
		String str = "error-同步修改失败！";
		try {
			connection = this.jqm.getConnection();
			sql = "update exam_info set is_report_upload= 1 ,update_time='"+ DateTimeUtil.getDateTime() + "' where id in (" + ids + ") ";
			connection.createStatement().executeUpdate(sql);
			
			str = " ok-同步成功！";
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
		return str;
	}
	
	@Override
	public String updateCompanyNameInExamInfo(String companyName, long company_id) throws ServiceException {
		String sql = "";
		Connection connection = null;
		String str = "error-同步修改失败！";
		try {
			connection = this.jqm.getConnection();
			sql = "update exam_info set company = '"+companyName+"' where company_id = " + company_id;
			connection.createStatement().executeUpdate(sql);
			
			str = " ok-同步成功！";
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
		return str;
	}

	@Override
	public List<ExaminfoChargingItemDTO> djdBuZuoGetList(String exam_num, String center_num) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select ec.id,e.exam_num,c.id as charge_item_id,c.item_code,c.item_name,d.dep_name,ec.exam_status, "
                +" CONVERT(varchar(100),ec.final_exam_date,23) as create_time,c.sam_demo_id as sample_id,ec.examinfo_id,d.dep_category,"
                + "ec.calculation_rate,ec.calculation_amount "
                +" from exam_info e,charging_item c,department_dep d,examinfo_charging_item ec left join user_usr u on u.id = ec.creater "
				+" where ec.exam_num = e.exam_num and ec.charge_item_id = c.id   and   c.item_category <> '耗材类型'     and   c.dep_id = d.id and ec.pay_status <> 'M'  "
				+" AND (ec.exam_status = 'D' ");
		//加配置 是否需要查询废弃的项目 默认不显示
		String IS_NEED_SHOW_ABANDON = this.customerInfoService.getCenterconfigByKey("IS_NEED_SHOW_ABANDON", center_num).getConfig_value();
		if(IS_NEED_SHOW_ABANDON!=null && !"".equals(IS_NEED_SHOW_ABANDON)) {
			if("Y".equalsIgnoreCase(IS_NEED_SHOW_ABANDON)) {
				buffer.append(" OR ec.exam_status = 'G' ");
			}
		}
		buffer.append("  ) and ec.isActive = 'Y' and ec.change_item <> 'C' and ec.center_num = '"+center_num+"' and e.is_Active='Y' and e.exam_num = '"+exam_num+"' "
				+" order by d.seq_code,c.item_seq ");
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(buffer.toString(), ExaminfoChargingItemDTO.class);
		
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

	@Override
	public List<UserUseDTO> getUserListByWeb_xtgnid(String xtgn_id) throws ServiceException {
		String sql = "select * from user_usr where id in (select user_id from web_userjsb where role_id in (select role_id from web_rolemenu where GNCD_ID = (select id from web_xtgncd where xtgn_id = '"
				+ xtgn_id + "')))";
		List<UserUseDTO> li = this.jqm.getList(sql, UserUseDTO.class);
		return li;
	}
	
	@Override
	public List<UserUseDTO> getUserListByRSCode(String rscode) throws ServiceException {
		String sql = "select * from user_usr where id in (select distinct userorroleid from WEB_RESRELATIONSHIP where res_code = '"
				+ rscode + "')";
		List<UserUseDTO> li = this.jqm.getList(sql, UserUseDTO.class);
		return li;
	}

	@Override
	public PageReturnDTO getExamInfoYdAddItemList(long com_id, long batch, String center_num, int pagesize, int pageno,
			String sort, String order) throws ServiceException {

		StringBuffer sb = new StringBuffer();
		PageReturnDTO webrole = new PageReturnDTO();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.employeeID,c.visit_no");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,c.status,n.group_name,m.dep_name,"
				+ "c.register_date,c.exam_times,x.type_name as customer_type_name,c.wuxuzongjian,c.is_Active  ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join customer_type x on x.id=c.customer_type_id ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
        sb.append(" and c.id in (select eci.examinfo_id from examinfo_charging_item eci where eci.isActive='Y' and eci.pay_status<>'M' and eci.is_new_added>0  and c.exam_num=eci.exam_num ) ");
		sb.append(" and  c.company_id=" + com_id + " ");
		sb.append(" and  c.center_num='" + center_num + "' ");
		if (batch > 0) {
			sb.append(" and  c.batch_id=" + batch + " ");
		

		if (null != sort && order != null && !"".equals(sort) && !"".equals(order)) {
			if ("group_name".equals(sort)) {
				sb.append(" order by n.group_name " + order);
			} else {
				sb.append(" order by " + sort + " " + order);
			}
		} else {
			sb.append(" order by c.exam_num desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if ("1900-01-01".equals(eu.getRegister_date())) {
						eu.setRegister_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		}
		return webrole;
	
	}

	@Override
	public List<ExaminfoChargingItemDTO> getExaminfoDelChargingItemforExamId(String exam_num) throws ServiceException {

		String sql = "select a.introducer,u.chi_name as jieshaoren,a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,up.chi_name,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,a.his_req_status,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.itemnum,b.item_type,a.calculation_rate,a.isActive "
				+ "from examinfo_charging_item a"
				+ " left join user_usr  u on u.id = a.introducer "
				+ " left join user_usr  up on up.id = a.updater "
				+ " ,charging_item b,department_dep c  "
				+ "where a.charge_item_id=b.id and b.dep_id=c.id  "
				+ "and b.isActive='Y' and a.isActive='N' and a.pay_status<>'M' and a.exam_num ='" + exam_num
				+ "' order by a.is_new_added desc,b.item_seq,a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> es = new ArrayList<ExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			for (int i = 0; i < map.getList().size(); i++) {
				ExaminfoChargingItemDTO eci = new ExaminfoChargingItemDTO();
				eci = (ExaminfoChargingItemDTO) map.getList().get(i);
				if ("G".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					} else if ("N".equals(eci.getPay_status())) {
						eci.setPay_statuss("未付费");
					}
				} else if ("T".equals(eci.getExam_indicator())) {
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					} else if("R".equals(eci.getPay_status())){
						eci.setPay_statuss("预付费");
					}
				} else if ("M".equals(eci.getExam_indicator())) {
					eci.setPay_statuss("免费");
				} else if ("GT".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					} else if ("N".equals(eci.getPay_status())) {
						eci.setPay_statuss("未付费");
					}
				}
				es.add(eci);
			}

		}
		return es;
	
	}
	@Override
	public UserInfoDTO getUserByUserName(String name) throws ServiceException {
		String sql = "select *  from user_usr Where log_name ='"+name.trim()+"'";
		@SuppressWarnings("unchecked")
		List<UserInfoDTO> ls = this.jqm.getList(sql,UserInfoDTO.class);
		if(ls.size()>0)
			return (UserInfoDTO) ls.get(0);
		else{
			return null;
		}
	}
	
	@Override
	public PageReturnDTO getExamInfoYdUserList(String customer_type, long com_id, long batch, long deptid,
			long group_id, String id_num, String art_num, String username, String tel, String statas, String regdate,
			String employeeID, String sex, String is_marriage, long rylb, String djdstatus, String center_num,
			int pagesize, int pageno, String sort, String order,UserDTO user) throws ServiceException {
		
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		PageReturnDTO webrole = new PageReturnDTO();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.employeeID,c.visit_no");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,c.status,n.group_name,m.dep_name,"
				+ "c.register_date,c.exam_times,x.type_name as customer_type_name,c.wuxuzongjian,c.is_Active,up.chi_name,a.update_time   ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join customer_type x on x.id=c.customer_type_id ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join user_usr  up on up.id = c.updater " );
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='N' ");

		sb.append(" and  c.company_id=" + com_id + " ");
		sb.append(" and  c.center_num='" + center_num + "' ");
		if (batch > 0) {
			sb.append(" and  c.batch_id=" + batch + " ");
		

		if (group_id > 0) {
			sb.append(" and c.group_id=" + group_id + " ");
		}
		if (deptid > 0) {
			sb.append(" and c._level=" + deptid + " ");
		}

		if (!StringUtil.isEmpty(djdstatus)) {
			if ("Y".equals(djdstatus)) {
				sb.append(" and c.is_need_guide = 'Y' ");
			} else {
				sb.append(" and (c.is_need_guide='N' or c.is_need_guide is null) ");
			}
		}
		if (customer_type != null && !"".equals(customer_type)) {
			sb.append(" and c.customer_type='" + customer_type + "'");
		}
		if (!StringUtil.isEmpty(employeeID)) {
			sb.append(" and c.employeeID like '%" + employeeID + "%' ");
		}

		if (!StringUtil.isEmpty(statas)) {
			sb.append(" and c.status in (" + statas + ") ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(id_num)) {
			sb.append(" and a.id_num like '%" + id_num + "%' ");
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(is_marriage)) {
			sb.append(" and c.is_marriage = '" + is_marriage + "' ");
		}

		if (!StringUtil.isEmpty(tel)) {
			sb.append(" and c.phone= '" + tel + "' ");
		}

		if (rylb > 0) {
			sb.append(" and c.customer_type_id= '" + rylb + "' ");
		}

		if (!StringUtil.isEmpty(regdate)) {
			sb.append(" and c.register_date= '" + regdate + " 23:59:59' ");
		}
		if (null != sort && order != null && !"".equals(sort) && !"".equals(order)) {
			if ("group_name".equals(sort)) {
				sb.append(" order by n.group_name " + order);
			} else {
				sb.append(" order by " + sort + " " + order);
			}
		} else {
			sb.append(" order by c.exam_num desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if ("1900-01-01".equals(eu.getRegister_date())) {
						eu.setRegister_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		}
		return webrole;
	
	}

	@Override
	public PageReturnDTO getExamInfoYdDelItemList(long com_id, long batch, String center_num, int pagesize, int pageno,
			String sort, String order) throws ServiceException {

		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.employeeID,c.visit_no");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,c.status,n.group_name,m.dep_name,"
				+ "c.register_date,c.exam_times,x.type_name as customer_type_name,c.wuxuzongjian,c.is_Active  ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join customer_type x on x.id=c.customer_type_id ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
        sb.append(" and c.exam_num in (select eci.exam_num from examinfo_charging_item eci where eci.isActive='N' and eci.pay_status<>'M') ");
		sb.append(" and  c.company_id=" + com_id + " ");
		sb.append(" and  c.center_num='" + center_num + "' ");
		if (batch > 0) {
			sb.append(" and  c.batch_id=" + batch + " ");
		}

		if (null != sort && order != null && !"".equals(sort) && !"".equals(order)) {
			if ("group_name".equals(sort)) {
				sb.append(" order by n.group_name " + order);
			} else {
				sb.append(" order by " + sort + " " + order);
			}
		} else {
			sb.append(" order by c.exam_num desc ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if ("1900-01-01".equals(eu.getRegister_date())) {
						eu.setRegister_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	
	}

	@Override
	public List<ExaminfoChargingItemDTO> getExaminfoYdChargingItemforExamId(String examnum) throws ServiceException {
		String sql = "select a.introducer,u.chi_name as jieshaoren,a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,a.updater,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,a.his_req_status,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.itemnum,b.item_type,a.calculation_rate,a.isActive "
				+ "from examinfo_charging_item a"
				+ " left join user_usr  u on u.id = a.introducer "
				+ " ,charging_item b,department_dep c  "
				+ "where a.charge_item_id=b.id and b.dep_id=c.id  "
				+ "and b.isActive='Y' and a.pay_status<>'M' and a.exam_num ='" + examnum
				+ "' order by a.is_new_added desc,b.item_seq,a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> es = new ArrayList<ExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			for (int i = 0; i < map.getList().size(); i++) {
				ExaminfoChargingItemDTO eci = new ExaminfoChargingItemDTO();
				eci = (ExaminfoChargingItemDTO) map.getList().get(i);
				if ("G".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					} else if ("N".equals(eci.getPay_status())) {
						eci.setPay_statuss("未付费");
					}
				} else if ("T".equals(eci.getExam_indicator())) {
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					} else if("R".equals(eci.getPay_status())){
						eci.setPay_statuss("预付费");
					}
				} else if ("M".equals(eci.getExam_indicator())) {
					eci.setPay_statuss("免费");
				} else if ("GT".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					} else if ("N".equals(eci.getPay_status())) {
						eci.setPay_statuss("未付费");
					}
				}
				es.add(eci);
			}

		}
		return es;
	}

	@Override
	public List<ExaminfoChargingItemDTO> getExaminfoAddChargingItemforExamId(String examnum) throws ServiceException {
		String sql = "select a.introducer,u.chi_name as jieshaoren,a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,up.chi_name,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,a.his_req_status,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.itemnum,b.item_type,a.calculation_rate,a.isActive "
				+ "from examinfo_charging_item a"
				+ " left join user_usr  u on u.id = a.introducer "
				+ " left join user_usr  up on up.id = a.updater "
				+ " ,charging_item b,department_dep c  "
				+ "where a.charge_item_id=b.id and b.dep_id=c.id  "
				+ "and b.isActive='Y' and a.is_new_added>0 and a.pay_status<>'M' and a.exam_num ='" + examnum
				+ "' order by a.is_new_added desc,b.item_seq,a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> es = new ArrayList<ExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			for (int i = 0; i < map.getList().size(); i++) {
				ExaminfoChargingItemDTO eci = new ExaminfoChargingItemDTO();
				eci = (ExaminfoChargingItemDTO) map.getList().get(i);
				if ("G".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					} else if ("N".equals(eci.getPay_status())) {
						eci.setPay_statuss("未付费");
					}
				} else if ("T".equals(eci.getExam_indicator())) {
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					} else if("R".equals(eci.getPay_status())){
						eci.setPay_statuss("预付费");
					}
				} else if ("M".equals(eci.getExam_indicator())) {
					eci.setPay_statuss("免费");
				} else if ("GT".equals(eci.getExam_indicator())) {
					if ("Y".equals(eci.getPay_status())) {
						eci.setPay_statuss("已付费");
					} else if ("N".equals(eci.getPay_status())) {
						eci.setPay_statuss("未付费");
					}
				}
				es.add(eci);
			}

		}
		return es;
	}

	@Override
	public PageReturnDTO getExamInfoUserListDeptAll(String customer_type, long com_id, long batch, String dept_ids,
			long group_id, String id_num, String art_num, String username, String tel, String statas, String regdate,
			String employeeID, String sex, String is_marriage, long rylb, String djdstatus, int minage, int maxage,
			String centernum, int pagesize, int pageno, String sort, String order,UserDTO user,String center_num) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,"
				+ "dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage, c.is_upload,c.is_report_upload, "
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.employeeID,c.visit_no");
		// sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 21)) as
		// wpacs ");
		// sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 21))
		// as ypacs ");
		// sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 131))
		// as wlis ");
		// sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 131))
		// as ylis ");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,c.status,n.group_name,m.dep_name,"
				+ "c.register_date,convert(varchar(100),c.join_date,23) join_date,c.exam_times,x.type_name as customer_type_name,c.wuxuzongjian,("
				+ "select sum(eci.personal_pay) from examinfo_charging_item eci where eci.isActive='Y' "
				+ " and eci.exam_indicator='G' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as personal_pay,("
				+ "select sum(eci.team_pay) from examinfo_charging_item eci where eci.isActive='Y' "
				+ "and eci.exam_indicator='T' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as team_pay ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join customer_type x on x.id=c.customer_type_id ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");

		sb.append(" and  c.company_id=" + com_id + " ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		if (batch <= 0) {
			sb.append(" and  c.batch_id=-100 ");
		} else {
			sb.append(" and  c.batch_id=" + batch + " ");
		}
		
		if(minage>0 && maxage<=120){
			sb.append( " and c.age>="+minage+" and  c.age<="+maxage+" " );
		}

		if (group_id == 0) {
			sb.append(" and (c.group_id=" + group_id + " or c.group_id is null) ");
		}else if (group_id > 0) {
			sb.append(" and c.group_id=" + group_id + " ");
		}
		if (!StringUtil.isEmpty(dept_ids)) {
			sb.append(" and c._level in (" + dept_ids + ") ");
		}

		if (!StringUtil.isEmpty(djdstatus)) {
			if ("Y".equals(djdstatus)) {
				sb.append(" and c.is_need_guide = 'Y' ");
			} else {
				sb.append(" and (c.is_need_guide='N' or c.is_need_guide is null) ");
			}
		}
		if (customer_type != null && !"".equals(customer_type)) {
			sb.append(" and c.customer_type='" + customer_type + "'");
		}
		if (!StringUtil.isEmpty(employeeID)) {
			sb.append(" and c.employeeID like '%" + employeeID + "%' ");
		}

		if (!StringUtil.isEmpty(statas)) {
			sb.append(" and c.status in (" + statas + ") ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(id_num)) {
			sb.append(" and a.id_num like '%" + id_num + "%' ");
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(is_marriage)) {
			sb.append(" and c.is_marriage = '" + is_marriage + "' ");
		}

		if (!StringUtil.isEmpty(tel)) {
			sb.append(" and c.phone= '" + tel + "' ");
		}

		if (rylb > 0) {
			sb.append(" and c.customer_type_id= '" + rylb + "' ");
		}

		if (!StringUtil.isEmpty(regdate)) {
			sb.append(" and c.register_date= '" + regdate + " 23:59:59' ");
		}
		if (null != sort && order != null && !"".equals(sort) && !"".equals(order)) {
			if ("group_name".equals(sort)) {
				sb.append(" order by n.group_name " + order);
			} else if ("swuxuzongjian".equals(sort)) {
				sb.append(" order by c.wuxuzongjian " + order);
			} else if ("statuss".equals(sort)) {
				sb.append(" order by c.status " + order);
			} else if ("isaffixedcodes".equals(sort)){ 
				sb.append(" order by c.isaffixedcode " + order);
			} else if ("isshowtables".equals(sort)){ 
				sb.append(" order by c.isshowtable " + order);
			} else {
				sb.append(" order by " + sort + " " + order);
			}
		} else {
			sb.append(" order by c.exam_num desc ");
		}
		TranLogTxt.liswriteEror_to_txt("团体备单操作台-团体人员管理-人员列表", sb.toString());
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					    eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if ("1900-01-01".equals(eu.getRegister_date())) {
						eu.setRegister_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}

	@Override
	public PageReturnDTO getDjtExamInfoUserListCopyItem(String data_source, String phone, String customer_type,
			long com_id, int ren_type, String art_num, String exam_num, String employeeID, String status, String id_num,
			String username, String starttime, String enddate, String time3, String time4, String createtime,
			String examtype, String jdstatuss, String userid, String centernum, RegisterModel model, int pagesize,
			int pageno, String sort, String order,String center_num,String time5,String sex_type,String is_print,String min_age,String max_age) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		if (pageno <= 0)
			pageno = 1;
		int startindex = pagesize * (pageno - 1);
		int endindex = pagesize * (pageno);

		sb.append("SELECT * FROM(SELECT TOP " + endindex + " ROW_NUMBER() OVER(");

		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
			sb.append(" order by update_time desc ");
		}

		sb.append(") AS ROWID,* FROM (");

		sb.append(" select distinct c.exam_num,c.id,CONVERT(varchar(100),a.birthday,23) as birthday,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.is_marriage, c.is_upload, c.is_report_upload,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,"
//				+ " ( select sum(eci.personal_pay) from examinfo_charging_item eci where eci.isActive='Y' "
//				+ " and eci.exam_indicator like 'G%' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as personal_pay,("
//				+ "select sum(eci.team_pay) from examinfo_charging_item eci where eci.isActive='Y' "
//				+ "and eci.exam_indicator like '%T' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as team_pay ,"
				+ "dbo.GetPersonalPayByExamId(c.exam_num) as personal_pay,dbo.GetTeamPayByExamId(c.exam_num) as team_pay,dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,x.chi_name,c.is_guide_back,c.update_time,c.introducer ");
		sb.append(" ,(select max(eci.exam_date) from examinfo_charging_item eci where eci.exam_num = c.exam_num and eci.exam_status = 'Y' and eci.isActive = 'Y')as exam_times");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join examinfo_print_report epr  on c.id=epr.examinfo_id ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_info_id = c.id and j.center_num='"+center_num+"' ");
		sb.append(" left join crm_sms_send ss on c.id=ss.user_id  ");
		if (model.getDisease_id() != null && !"".equals(model.getDisease_id())) {
			sb.append(" left join examinfo_disease ed on c.id= ed.exam_info_id   ");
		}
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and  c.center_num='" + centernum + "' ");

		sb1.append("select count(*) as counts from customer_info a ,exam_info c");
		sb1.append(" left join crm_sms_send ss on c.id=ss.user_id  ");
		if (model.getDisease_id() != null && !"".equals(model.getDisease_id())) {
			sb1.append(" left join examinfo_disease ed on c.id= ed.exam_info_id   ");
		}
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y' ");
		sb1.append(" and  c.center_num='" + centernum + "' ");
		if (model.getDisease_id() != null && !"".equals(model.getDisease_id())) {
			sb.append(" and ed.disease_id = " + model.getDisease_id() + " ");
			sb1.append(" and ed.disease_id = " + model.getDisease_id() + " ");
		}

		//是否有电话号码
		if (model.getIsphone() != null && !"".equals(model.getIsphone())) {
			if("1".equals(model.getIsphone())){
				sb.append(" and c.phone  is not null and c.phone !='' ");
				sb1.append(" and c.phone  is not null and c.phone !=''  ");
			}else{
				sb.append(" and (c.phone  is  null or c.phone ='') ");
				sb1.append(" and (c.phone  is  null or c.phone ='' ) ");
			}
			
		}
		
		if (model.getSms_status() != null && !"".equals(model.getSms_status())) {
			if("0".equals(model.getSms_status())){
				sb.append(" and (ss.sms_status = " + model.getSms_status() + " or ss.sms_status = ''  or ss.sms_status is null )");
				sb1.append(" and (ss.sms_status = " + model.getSms_status() + " or ss.sms_status = '' or ss.sms_status is null) ");
			}else{
				sb.append(" and ss.sms_status = " + model.getSms_status() + " ");
				sb1.append(" and ss.sms_status = " + model.getSms_status() + " ");
			}
			
		}
		
		if (model.getBatch_id() != 0) {
			sb.append(" and c.batch_id = " + model.getBatch_id() + " ");
			sb1.append(" and c.batch_id = " + model.getBatch_id() + " ");
		}
		
		if (customer_type != null && !"".equals(customer_type)) {
			sb.append(" and c.customer_type = '" + customer_type + "' ");
			sb1.append(" and c.customer_type = '" + customer_type + "' ");
		}
		
		if (com_id > 0) {
			sb.append(" and c.company_id=" + com_id + " ");
			sb1.append(" and c.company_id=" + com_id + " ");
		}

		if (!StringUtil.isEmpty(exam_num)) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
			sb1.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if(sex_type != null && !"".equals(sex_type)){
			if(sex_type.equals("1")){
				sb.append(" and a.sex = '男'");
				sb1.append(" and a.sex = '男'");
			}else{
				sb.append(" and a.sex = '女'");
				sb1.append(" and a.sex = '女'");
			}
		}
		
		if(min_age!= null && !"".equals(min_age)){
			sb.append(" and c.age >= '"+min_age+"'");
			sb1.append(" and c.age >= '"+min_age+"'");
		}
		
		if(max_age!= null && !"".equals(max_age)){
			sb.append(" and c.age <= '"+max_age+"'");
			sb1.append(" and c.age <= '"+max_age+"'");
		}
		if (is_print != null && !"".equals(is_print)) {
			if(is_print.equals("Y")){
				
				sb.append(" and c.is_report_print = 'Y' ");
				sb1.append(" and c.is_report_print = 'Y' ");
			}else{
				sb.append(" and c.is_report_print = 'N' ");
				sb1.append(" and c.is_report_print = 'N' ");
			}
		}
		if ((!StringUtil.isEmpty(time5))) {
			sb.append(" and a.birthday = '" + time5 + " 00:00:00' ");
			sb1.append(" and a.birthday = '" + time5 + " 00:00:00' ");
		}
		if(phone != null && !"".equals(phone)){
			sb.append(" and c.phone = '"+phone+"'");
			sb1.append(" and c.phone = '"+phone+"'");
		}
		//数据来源
		if(data_source != null && !"".equals(data_source)){
			sb.append(" and c.data_source = '"+data_source+"'");
			sb1.append(" and c.data_source = '"+data_source+"'");
		}
		
		if (!StringUtil.isEmpty(jdstatuss)) {
			if ("Y".equals(jdstatuss)) {
				sb.append(" and c.is_need_guide = 'Y' ");
				sb1.append(" and c.is_need_guide = 'Y' ");
			} else {
				sb.append(" and (c.is_need_guide='N' or c.is_need_guide is null) ");
				sb1.append(" and (c.is_need_guide='N' or c.is_need_guide is null) ");
			}
		}

		if (!StringUtil.isEmpty(id_num)) {
			sb.append(" and a.id_num = '" + id_num + "' ");
			sb1.append(" and a.id_num = '" + id_num + "' ");
		}

		if (!StringUtil.isEmpty(employeeID)) {
			sb.append(" and c.employeeID like '%" + employeeID + "%' ");
			sb1.append(" and c.employeeID like '%" + employeeID + "%' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
			sb1.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(art_num)) {
			sb.append(" and a.arch_num = '" + art_num + "' ");
			sb1.append(" and a.arch_num = '" + art_num + "' ");
		}

		if ((!StringUtil.isEmpty(starttime)) && (!StringUtil.isEmpty(enddate))) {
			sb.append(" and c.join_date >= '" + starttime + " 00:00:00' ");
			sb.append(" and c.join_date <= '" + enddate + " 23:59:59' ");

			sb1.append(" and c.join_date >= '" + starttime + " 00:00:00' ");
			sb1.append(" and c.join_date <= '" + enddate + " 23:59:59' ");
		}

		if (!StringUtil.isEmpty(model.getYuyue_date1()) && !StringUtil.isEmpty(model.getYuyue_date2())) {
			sb.append(" and c.register_date  >= '" + model.getYuyue_date1() + " 00:00:00' ");
			sb.append(" and c.register_date <= '" + model.getYuyue_date2() + " 23:59:59' ");

			sb1.append(" and c.register_date >= '" + model.getYuyue_date1() + " 00:00:00' ");
			sb1.append(" and c.register_date <= '" + model.getYuyue_date2() + " 23:59:59' ");
		}

		
		if ((!StringUtil.isEmpty(time3)) && (!StringUtil.isEmpty(time4))) {
			sb.append(" and c.final_date >= '" + time3 + " 00:00:00' ");
			sb.append(" and c.final_date <= '" + time4 + " 23:59:59' ");
			sb.append(" and c.status = 'Z' ");

			sb1.append(" and c.final_date >= '" + time3 + " 00:00:00' ");
			sb1.append(" and c.final_date <= '" + time4 + " 23:59:59' ");
			sb1.append(" and c.status = 'Z' ");
		} else {
			if (!StringUtil.isEmpty(status)) {
				sb.append(" and c.status in (" + status + ") ");
				sb1.append(" and c.status in (" + status + ") ");
			}
		}

		if (!StringUtil.isEmpty(createtime)) {
			sb.append(" and c.create_time = '" + createtime + " 23:59:59' ");
			sb1.append(" and c.create_time = '" + createtime + " 23:59:59' ");
		}

		if (!StringUtil.isEmpty(examtype)) {
			sb.append(" and c.exam_type = '" + examtype + "' ");
			sb1.append(" and c.exam_type = '" + examtype + "' ");
		}
		if (ren_type > 0) {
			sb.append(" and c.customer_type_id = '" + ren_type + "' ");
			sb1.append(" and c.customer_type_id = '" + ren_type + "' ");
		}

		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>" + startindex + "");

		PageSupport map = this.jqm.getList(sb1.toString(), sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		List<ExamInfoUserDTO> list = new ArrayList<ExamInfoUserDTO>();
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			for (int i = 0; i < map.getList().size(); i++) {
				ExamInfoUserDTO eu = new ExamInfoUserDTO();
				eu = (ExamInfoUserDTO) map.getList().get(i);
				String setnums = "";
				List<ExaminfoSetDTO> batchlist = new ArrayList<ExaminfoSetDTO>();
				batchlist = this.getExamSetForExamNum(eu.getExam_num(),center_num);
				if ((batchlist != null) && (batchlist.size()) > 0) {
					for (ExaminfoSetDTO es : batchlist) {
						setnums = setnums + "," + es.getSet_name();
					}
					if (setnums.length() > 1)
						setnums = setnums.trim().substring(1, setnums.trim().length());
					eu.setSet_name(setnums);
				}

				if ((eu.getRegister_date() != null) && (eu.getRegister_date().trim().length() > 10)) {
					eu.setRegister_date(eu.getRegister_date().trim().substring(0, 10));
					if (eu.getRegister_date().indexOf("1900-01-01") >= 0) {
						eu.setRegister_date("");
					}
				}

				if ((eu.getJoin_date() != null) && (eu.getJoin_date().trim().length() > 10)) {
					eu.setJoin_date(eu.getJoin_date().trim().substring(0, 10));
					if (eu.getJoin_date().indexOf("1900-01-01") >= 0) {
						eu.setJoin_date("");
					}
				}

				eu.setRemark1(eu.getIs_need_guides() + "/" + eu.getIs_need_barcodes() + "/" + eu.getStatuss());

				eu.setLis(eu.getYlis() + "/" + eu.getWlis());
				eu.setPacs(eu.getYpacs() + "/" + eu.getWpacs());
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}

	@Override
	public String djtExaminfoBuzuo(String ids, String exam_num, UserDTO user) {
		
		//是否需要总检才能补做
		String IS_END_EXAM = this.customerInfoService.getCenterconfigByKey("IS_END_EXAM", user.getCenter_num()).getConfig_value().trim();
		if(IS_END_EXAM!=null && !"".equals(IS_END_EXAM)) {
			if("Y".equalsIgnoreCase(IS_END_EXAM)) {
				String hql = "From ExamInfo where exam_num = '"+exam_num+"'";
				List<ExamInfo>   examDto = qm.find(hql);
				if( examDto.size()>0 && examDto!=null ){
					if(!"Z".equalsIgnoreCase(examDto.get(0).getStatus())) {
						return "error-未总检人员，不需要补做登记!";
					}
				}
			}
		}
		
		
		String bufferSql ="select ec.* FROM examinfo_charging_item ec WHERE ec.exam_num = ( SELECT exam_num FROM exam_info WHERE exam_num = '"+exam_num+"' ) AND ec.isActive = 'Y' AND ec.charge_item_id IN ("+ids+")";
		List<ExaminfoChargingItem>  olditemList = 	this.jqm.getList(bufferSql,ExaminfoChargingItem.class);
		if(olditemList.size() == 0){
			return "error-该体检人员不存在延期项目，不能补做登记!";
		}
		//查询该人员是否已经补做
		List<ExamInfo> examBuZuo = this.qm.find("from ExamInfo e where e.counter_check ='B' and e.old_exam_num='"+exam_num+"'");
		if(examBuZuo.size() > 0){
			return "error-该体检人员已补做登记，不能再次补做登记!";
		}
		
		//判断该体检号已为补做项目
		List<ExamInfo> examBuZuoAlread = this.qm.find("from ExamInfo e where e.counter_check ='B' and e.exam_num='"+exam_num+"'");
		if(examBuZuoAlread.size() > 0){
			return "error-该体检信息已是补做信息，不能再次补做登记!";
		}
		
		ExamInfo oldexaminfo = (ExamInfo) this.qm.load(ExamInfo.class, olditemList.get(0).getExaminfo_id());
		/*if("Y".equals(oldexaminfo.getCounter_check())){
			return "error-该体检人员已补做登记，不能再次补做登记!";
		}*/
		CustomerInfo customerInfo = (CustomerInfo) this.qm.load(CustomerInfo.class, oldexaminfo.getCustomer_id());
		
		//后台获取体检号的方式
		String IS_NEED_NEW_EXAMNUM = this.customerInfoService.getCenterconfigByKey("IS_NEED_NEW_EXAMNUM", user.getCenter_num()).getConfig_value().trim();
		String new_examnum = "";
		if(IS_NEED_NEW_EXAMNUM==null || "".equals(IS_NEED_NEW_EXAMNUM)) {
			new_examnum = "B"+oldexaminfo.getExam_num();
		}else {
			if("Y".equalsIgnoreCase(IS_NEED_NEW_EXAMNUM)) {
				new_examnum = this.batchService.GetCreateID("exam_no", user.getCenter_num());
			}else {
				new_examnum = "B"+oldexaminfo.getExam_num();
			}
		}
		
		/*List<ExamInfo> examList = this.qm.find("from ExamInfo e where e.exam_num='"+new_examnum+"'");
		if(examList.size() > 0){
			return "error-该体检人员已补做登记，不能再次补做登记!";
		}*/
		
		ExamInfo examinfo = new ExamInfo();
		examinfo.setGroup_id(oldexaminfo.getGroup_id());
		examinfo.setCustomer_id(oldexaminfo.getCustomer_id());
		examinfo.setExam_num(new_examnum);
		examinfo.setStatus("Y");
		examinfo.setRegister_date(DateTimeUtil.getDateTime());
		examinfo.setJoin_date(DateTimeUtil.getDateTime());
		examinfo.setExam_type(oldexaminfo.getExam_type());
		examinfo.setCustomer_type(oldexaminfo.getCustomer_type());
		examinfo.setIs_sampled_directly(oldexaminfo.getIs_sampled_directly());
		examinfo.setIs_adjusted(oldexaminfo.getIs_adjusted());
		examinfo.setCenter_num(oldexaminfo.getCenter_num());
		examinfo.setGetReportWay(oldexaminfo.getGetReportWay());
		examinfo.setReportAddress(oldexaminfo.getReportAddress());
		examinfo.setChargingType(oldexaminfo.getChargingType());
		examinfo.setCustomerType(oldexaminfo.getCustomerType());
		examinfo.setGroup_index(oldexaminfo.getGroup_index());
		examinfo.setIs_Active(oldexaminfo.getIs_Active());
		examinfo.setCreater(user.getUserid());
		examinfo.setCreate_time(DateTimeUtil.getDateTime());
		examinfo.setUpdater(user.getUserid());
		examinfo.setUpdate_time(DateTimeUtil.getDateTime());
		examinfo.setIs_guide_back("N");
		examinfo.setCompany_check_status(oldexaminfo.getCompany_check_status());
		examinfo.setCustomer_type_id(oldexaminfo.getCustomer_type_id());
		examinfo.setIs_marriage(oldexaminfo.getIs_marriage());
		examinfo.setAge(oldexaminfo.getAge());
		examinfo.setAddress(oldexaminfo.getAddress());
		examinfo.setEmail(oldexaminfo.getEmail());
		examinfo.setPhone(oldexaminfo.getPhone());
		examinfo.setCompany(oldexaminfo.getCompany());
		examinfo.setPosition(oldexaminfo.getPosition());
		examinfo.set_level(oldexaminfo.get_level());
		examinfo.setPicture_path(oldexaminfo.getPicture_path());
		examinfo.setIs_after_pay(oldexaminfo.getIs_after_pay());
		examinfo.setPast_medical_history(oldexaminfo.getPast_medical_history());
		examinfo.setRemarke(oldexaminfo.getRemarke());
		examinfo.setIntroducer(oldexaminfo.getIntroducer());
		examinfo.setCounter_check("B");
		examinfo.setGuide_nurse(oldexaminfo.getGuide_nurse());
		examinfo.setAppointment(oldexaminfo.getAppointment());
		examinfo.setData_source(oldexaminfo.getData_source());
		examinfo.setOthers(oldexaminfo.getOthers());
		examinfo.setOrder_id(oldexaminfo.getOrder_id());
		examinfo.setExam_times(oldexaminfo.getExam_times());
		examinfo.setCompany_id(oldexaminfo.getCompany_id());
		examinfo.setBatch_id(oldexaminfo.getBatch_id());
		examinfo.setIs_report_print("N");
		examinfo.setPatient_id(oldexaminfo.getPatient_id());
		examinfo.setExam_indicator(oldexaminfo.getExam_indicator());
		examinfo.setClinic_no(oldexaminfo.getClinic_no());
		examinfo.setEmployeeID(oldexaminfo.getEmployeeID());
		examinfo.setMc_no(oldexaminfo.getMc_no());
		examinfo.setVisit_date(oldexaminfo.getVisit_date());
		examinfo.setVisit_no(oldexaminfo.getVisit_no());
		examinfo.setIs_need_barcode("N");
		examinfo.setIs_need_guide("N");
		examinfo.setFreeze(oldexaminfo.getFreeze());
		examinfo.setIs_report_tidy("N");
		examinfo.setBudget_amount(oldexaminfo.getBudget_amount());
		examinfo.setMarriage_age(oldexaminfo.getMarriage_age());
		examinfo.setWuxuzongjian(oldexaminfo.getWuxuzongjian());
		examinfo.setIs_upload(0);
		examinfo.setOld_exam_num(oldexaminfo.getExam_num());  //设置旧的体检号
		this.pm.save(examinfo);
		
		for (ExaminfoChargingItem oldChargingItem : olditemList) {
			ExaminfoChargingItem examinfoChargingItem = new ExaminfoChargingItem();
			examinfoChargingItem.setExaminfo_id(examinfo.getId());
			examinfoChargingItem.setCharge_item_id(oldChargingItem.getCharge_item_id());
			examinfoChargingItem.setExam_indicator(oldChargingItem.getExam_indicator());
			examinfoChargingItem.setAmount(oldChargingItem.getAmount());
			examinfoChargingItem.setItem_amount(oldChargingItem.getItem_amount());
			examinfoChargingItem.setDiscount(oldChargingItem.getDiscount());
			examinfoChargingItem.setIsActive(oldChargingItem.getIsActive());
			examinfoChargingItem.setFinal_exam_date(oldChargingItem.getFinal_exam_date());
			examinfoChargingItem.setPay_status(oldChargingItem.getPay_status());
			examinfoChargingItem.setExam_status("N");
			examinfoChargingItem.setIs_new_added(oldChargingItem.getIs_new_added());
			examinfoChargingItem.setCreater(user.getUserid());
			examinfoChargingItem.setCreate_time(DateTimeUtil.parse());
			examinfoChargingItem.setUpdater(user.getUserid());
			examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());
			examinfoChargingItem.setCheck_status(oldChargingItem.getCheck_status());
			examinfoChargingItem.setAdd_status(oldChargingItem.getAdd_status());
			examinfoChargingItem.setCalculation_amount(oldChargingItem.getCalculation_amount());
			examinfoChargingItem.setIs_application("N");
			examinfoChargingItem.setChange_item(oldChargingItem.getChange_item());
			examinfoChargingItem.setTeam_pay(oldChargingItem.getTeam_pay());
			examinfoChargingItem.setPersonal_pay(oldChargingItem.getPersonal_pay());
			examinfoChargingItem.setTeam_pay_status(oldChargingItem.getTeam_pay_status());
			examinfoChargingItem.setHis_req_status(oldChargingItem.getHis_req_status());
			examinfoChargingItem.setItemnum(oldChargingItem.getItemnum());
			examinfoChargingItem.setIntroducer(oldChargingItem.getIntroducer());
			examinfoChargingItem.setCalculation_rate(oldChargingItem.getCalculation_rate());
			examinfoChargingItem.setExam_num(oldChargingItem.getExam_num());
			
			this.pm.save(examinfoChargingItem);
			
			ChargingItem item = (ChargingItem) this.qm.load(ChargingItem.class, examinfoChargingItem.getCharge_item_id());
			if(item.getSam_demo_id() != null){
				if("131".equals(item.getDep_category())){//检验
					List<SampleExamDetail> samlist = this.qm.find("from SampleExamDetail s where s.sample_id = "+item.getSam_demo_id() + " and s.exam_num = "+examinfo.getExam_num());
					SampleExamDetail sampleExamDetail = null;
					if(samlist.size() == 0){
						sampleExamDetail = new SampleExamDetail();
						sampleExamDetail.setExam_info_id(examinfo.getId());                    	   
						sampleExamDetail.setSample_id(item.getSam_demo_id());
						sampleExamDetail.setSample_barcode(this.batchService.GetCreateID("barcode", user.getCenter_num()));
						sampleExamDetail.setStatus("W");
						sampleExamDetail.setPic_path("");
						sampleExamDetail.setCenter_num(user.getCenter_num());
						sampleExamDetail.setCreater(user.getUserid());
						sampleExamDetail.setCreate_time(DateTimeUtil.parse());
						sampleExamDetail.setUpdater(user.getUserid());
						sampleExamDetail.setUpdate_time(DateTimeUtil.parse());
						sampleExamDetail.setExam_num(item.getExam_num());
						this.pm.save(sampleExamDetail);
					}else{
						sampleExamDetail = samlist.get(0);
					}
					
					ExamResultChargingItem erci =new ExamResultChargingItem();
                    erci.setCharging_id(item.getId());
                    erci.setExam_id(sampleExamDetail.getId());
                    erci.setResult_type(CommSet.lis_sample);
                    erci.setIsActive("Y");
                    erci.setCreater(user.getUserid());
                    erci.setCreate_time(DateTimeUtil.parse());
                    erci.setUpdater(user.getUserid());
                    erci.setUpdate_time(DateTimeUtil.parse());
                    erci.setBar_code(sampleExamDetail.getSample_barcode());
                    erci.setCharging_item_code(item.getItem_code());
                    this.pm.save(erci);
                    
                    String sql = "INSERT INTO zl_req_item(exam_info_id,charging_item_id,zl_pat_id,lis_item_id,req_id,lis_req_code,createdate,exam_num) "
       				     +" VALUES('"+examinfo.getId()+"','"+item.getId()+"',null,'"+item.getHis_num()+"','"+getlisitemid(Long.valueOf(olditemList.get(0).getExaminfo_id()),item.getId())+"',"
       				     		+ "'"+sampleExamDetail.getSample_barcode()+"','"+DateTimeUtil.getDateTime()+"','"+item.getExam_num()+"')  ";
                    this.jpm.executeSql(sql);
				}else if("21".equals(item.getDep_category())){//影像
					PacsSummary ps = this.customerInfoService.getPacsSummaryDTOforExamid(examinfo.getExam_num(),item.getSam_demo_id(),"N");
                	if((ps==null)||(ps.getId()<=0)){
                		ps =new PacsSummary();
                		
                		ps.setExaminfo_num(examinfo.getExam_num());
                		ps.setExaminfo_name(customerInfo.getUser_name());
                		ps.setExaminfo_sex(customerInfo.getSex());
                		ps.setExaminfo_birthday(customerInfo.getBirthday());
                		ps.setApply_date(DateTimeUtil.parse());
                		ps.setApply_person(user.getUsername());
                		ps.setExam_status("N");
                		ps.setCreater(user.getUserid());
                		ps.setCreate_time(DateTimeUtil.parse());
                		ps.setUpdater(user.getUserid());
                		ps.setUpdate_time(DateTimeUtil.parse());
                		ps.setExaminfo_sampleId(item.getSam_demo_id());
                		ps.setPacs_req_code(this.batchService.GetCreateID("pacs_req_num", user.getCenter_num()));
                		ps.setSample_status("N");
                		this.pm.save(ps);	                    		
                	}
                	Pacsdetail pd =new Pacsdetail();
                	pd.setExaminfo_num(examinfo.getExam_num());
                	pd.setSummary_id(ps.getId());
                	pd.setChargingItem_num(item.getItem_code());
                	pd.setChargingItem_name(item.getItem_name());
                	DepartmentDep dd = departmentService.loadDepartmentDep(item.getDep_id());
                	pd.setDep_num(dd.getDep_num());
                	pd.setDep_name(dd.getDep_name());
                	pd.setExaminfo_sampleId(item.getSam_demo_id());
                	pd.setIs_need_return("Y");
                	pd.setCreater(user.getUserid());
                	pd.setCreate_time(DateTimeUtil.parse());
                	pd.setUpdater(user.getUserid());
                	pd.setUpdate_time(DateTimeUtil.parse());
                	pd.setPacs_req_code(ps.getPacs_req_code());
                	this.customerInfoService.savePacsdetail(pd);
                	
                	String sql = "INSERT INTO zl_req_pacs_item(exam_info_id,pacs_req_code,charging_item_ids,zl_pat_id,zl_pacs_id,req_id,remark1,remark2,remark3,createdate,exam_num) "
          				     +" VALUES('"+examinfo.getId()+"','"+ps.getPacs_req_code()+"','"+item.getItem_code()+"','"+item.getHis_num()+"','"+item.getItem_code()+"',"
          				     		+ "'"+getExamNumByReq(examinfo.getId(),item.getItem_code())+"','"+DateTimeUtil.getDate2()+"','"+dd.getDep_num()+"','"+dd.getDep_num()+"','"+DateTimeUtil.getDateTime()+"','"+item.getExam_num()+"')  ";
                    this.jpm.executeSql(sql);
				}
			}
		}
		return "ok-"+examinfo.getId()+"-"+new_examnum;
	}
	
	
	
	public String getlisitemid(long exam_info_id,long chargitem_id){
		Connection tjtmpconnect = null;
		String lisitemid = "";
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "select exam_info_id,charging_item_id,zl_pat_id,lis_item_id,"
					+ "req_id from zl_req_item where exam_info_id='"
					+ exam_info_id + "' and charging_item_id='"+chargitem_id+"'";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			if (rs1.next()) {
				lisitemid = rs1.getString("req_id");
			}
			rs1.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return lisitemid;
	}
	
	
	public String getExamNumByReq(long exam_info_id,String chargitem_code){
		Connection tjtmpconnect = null;
		String examNo = "";
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "select exam_info_id,pacs_req_code,charging_item_ids,"
					+ "req_id from zl_req_pacs_item where exam_info_id='"
					+ exam_info_id + "' and charging_item_ids='"+chargitem_code+"'";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			if (rs1.next()) {
				examNo = rs1.getString("req_id");
			} 
			rs1.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return examNo;
	}
	
	@Override
	public String updateExaminfoIsVisit(String exam_num, String center_num) throws ServiceException {
		String sql = "select e.id,e.exam_type,e.exam_num,e.counter_check,e.wuxuzongjian,d.data_code_children as customer_type,"
				+ "e.company_id,(dbo.GetTeamPayByExamId(e.exam_num) + dbo.GetPersonalPayByExamId(e.exam_num)) personal_pay from exam_info e "
				+ "left join data_dictionary d on d.id = e.customer_type where e.exam_num = '" + exam_num + "'";
		List<ExamInfoUserDTO> list = this.jqm.getList(sql, ExamInfoUserDTO.class);
		String IS_REPORT_DEP_FLOW = this.customerInfoService.getCenterconfigByKey("IS_REPORT_DEP_FLOW", center_num).getConfig_value();
		if(IS_REPORT_DEP_FLOW != null && "2".equals(IS_REPORT_DEP_FLOW)){//东北国际判断vip标记
			if(list.size() > 0){
				ExamInfoUserDTO examinfo = list.get(0);
				String IS_VISIT_AMOUNT = this.customerInfoService.getCenterconfigByKey("IS_VISIT_AMOUNT", center_num).getConfig_value().trim();
				String IS_VIP_CHARGITEM = this.customerInfoService.getCenterconfigByKey("IS_VIP_CHARGITEM", center_num).getConfig_value().trim();
				if (checkedVipItem(examinfo.getExam_num(), IS_VIP_CHARGITEM)) {// 包含配置贵宾项目
					String upsql = "update exam_info set vipflag = 1,isvisit='1' where id = " + examinfo.getId();
					this.jpm.executeSql(upsql);
				}else if(examinfo.getPersonal_pay() >= Double.valueOf(IS_VISIT_AMOUNT)){
					String upsql = "update exam_info set vipflag = 1,isvisit='1' where id = " + examinfo.getId();
					this.jpm.executeSql(upsql);
				}
			}
		}else{
			if (list.size() > 0) {
				int flag = 1;// 1回访 0不回访
				ExamInfoUserDTO examinfo = list.get(0);
	
				if (!"N".equals(examinfo.getCounter_check())) {// N 普通体检 Y 复查 B 补做
																// 不回访
					flag = 0;
				} else if (examinfo.getWuxuzongjian() == 1) {// 1无需总检 不回访
					flag = 0;
				} else if ("ZGTJ".equals(examinfo.getCustomer_type())) {// 招工体检 不回访
					flag = 0;
				} else //if ("T".equals(examinfo.getExam_type())) 
				{// 单位体检
					String QUEUE_COMPANY_G = this.customerInfoService.getCenterconfigByKey("SENDREPORT_MSG_COMPANY", center_num)
							.getConfig_value().trim();
					String IS_NOT_VISIT_COMPANY = this.customerInfoService.getCenterconfigByKey("IS_NOT_VISIT_COMPANY", center_num)
							.getConfig_value().trim();
					String IS_VISIT_AMOUNT = this.customerInfoService.getCenterconfigByKey("IS_VISIT_AMOUNT", center_num).getConfig_value()
							.trim();
					String IS_VIP_CHARGITEM = this.customerInfoService.getCenterconfigByKey("IS_VIP_CHARGITEM", center_num)
							.getConfig_value().trim();
	
					if (com.hjw.util.StringUtil.checkString(QUEUE_COMPANY_G, ",", examinfo.getCompany_id() + "")) {// 需要回访单位
						flag = 1;
					} else if (com.hjw.util.StringUtil.checkString(IS_NOT_VISIT_COMPANY, ",",
							examinfo.getCompany_id() + "")) {// 处级单位 不需要回访单位
						flag = 0;
					} else if (examinfo.getPersonal_pay() >= Double.valueOf(IS_VISIT_AMOUNT)) {// 金额
																								// >=
																								// 配置金额
																								// 需要回访
						flag = 1;
					} else if (checkedVipItem(examinfo.getExam_num(), IS_VIP_CHARGITEM)) {// 包含配置贵宾项目
																					// 需要回访
						flag = 1;
					} else {// 所有单位不回访
						flag = 0;
					}
				}/* else {// 所有个人都回访
					flag = 1;
				}*/
				// 更新是否回访状态
				String upsql = "update exam_info set isvisit = " + flag + " where id = " + examinfo.getId();
				this.jpm.executeSql(upsql);
			}
		}
		return "ok-更新是否回访状态成功!";
	}
	
	private boolean checkedVipItem(String exam_num, String IS_VIP_CHARGITEM) throws ServiceException {
		String[] itemids = IS_VIP_CHARGITEM.split(",");
		for (int i = 0; i < itemids.length; i++) {
			String sql = "select e.id from examinfo_charging_item e where e.exam_num = '" + exam_num + "' "
					+ "and e.charge_item_id = '" + itemids[i] + "' and e.isActive = 'Y' and e.pay_status <> 'M'";
			List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
			if (list.size() > 0) {
				return true;
			}
		}
		return false;
	}
	
	public List<TreeDTO> getDepTree(ImpCustomerInfoModel model, String center_num) throws ServiceException {
		// String sql = " SELECT DISTINCT convert(varchar(20),dep_id) as
		// id,de.dep_name as text,de.seq_code FROM department_dep
		// de,charging_item ci WHERE "
		// +" de.id=ci.dep_id AND ci.isActive='Y' AND de.isActive='Y' and
		// ci.dep_category in ('21','17','131') "
		// + " and de.sex in('"+model.getSex()+"','全部') and
		// de.dep_category='"++"' order by de.seq_code ";
		// List<TreeDTO> li = this.jqm.getList(sql, TreeDTO.class);
		// TreeDTO tr = new TreeDTO();
		// tr.setText("所有科室");
		// tr.setState("open");
		// tr.setChildren(li);
		// List<TreeDTO> lis = new ArrayList<TreeDTO>();
		// lis.add(tr);

		String di = this.customerInfoService.getCenterconfigByKey("DJT_ADD_DEP", center_num).getConfig_value();
		String[] ids = di.split(",");
		String c_id = "";
		for (int i = 0; i < ids.length; i++) {
			c_id += ids[i] + ",";
		}
		String cc_id = c_id.substring(0, c_id.length() - 1);
		// 科室类型
		String dep_sql = " SELECT d.data_name as  text,convert(varchar(20),id) as id,d.seq_code,'1'  as depttype FROM  data_dictionary  d  where "
				+ " d.data_code='KSLX' and   d.isActive='Y'   and   d.id  in(" + cc_id + ") "
				+ "   order  by   d.seq_code  ";
		@SuppressWarnings("unchecked")
		List<TreeDTO> d_li = this.jqm.getList(dep_sql, TreeDTO.class);

		// 科室
		for (int i = 0; i < d_li.size(); i++) {
			String sql = " SELECT  DISTINCT convert(varchar(20),dep_id)   as id,de.dep_name as text,de.seq_code FROM   department_dep  de,department_vs_center ded,charging_item  ci   WHERE   "
					+ "  de.id=ci.dep_id  and ded.dep_id = de.id and ded.center_num ='"+center_num+"'   AND   ci.isActive='Y'  AND   de.isActive='Y' and  ci.dep_category in ('21','17','131') ";
			if (!StringUtil.isEmpty(model.getSex())) {
				sql += " and de.sex  in('" + model.getSex() + "','全部')";
			}
			sql += " and de.dep_category='" + d_li.get(i).getId() + "' order  by de.seq_code ";
			List<TreeDTO> li = this.jqm.getList(sql, TreeDTO.class);
			d_li.get(i).setChildren(li);
		}
		TreeDTO d = new TreeDTO();
		d.setId("0");
		d.setText("所有科室");
		d.setDepttype("9");
		d.setChildren(d_li);
		List<TreeDTO> dlist = new ArrayList<TreeDTO>();
		dlist.add(d);
		return dlist;
	}

	@Override
	public List<DJTItemDTO> getDJTItem(ImpCustomerInfoModel model, String center_num) throws ServiceException {
		List<DJTItemDTO> li = new ArrayList<DJTItemDTO>();
		String sql = "";
		if (model.getDep_c() == 1) {
			// 科室
			sql += "  SELECT  DISTINCT convert(varchar(20),dep_id)   as dep_id,de.dep_name,de.seq_code FROM   department_dep  de,department_vs_center ded,charging_item  ci   WHERE   "
					+ " de.id=ci.dep_id and ded.dep_id = de.id and ded.center_num ='"+center_num+"' AND   ci.isActive='Y'  AND   de.isActive='Y' and  ci.dep_category in ('21','17','131')  ";
			if (!StringUtil.isEmpty(model.getSex())) {
				sql += " and de.sex  in('" + model.getSex() + "','全部')";
			}
			sql += " and de.dep_category='" + model.getDep_id() + "' order by de.seq_code ";
			li = this.jqm.getList(sql, DJTItemDTO.class);

			// 项目
			for (int i = 0; i < li.size(); i++) {
				sql = " SELECT  c.id,c.item_code,c.item_name,c.amount,c.item_name,c.sex,c.item_type FROM   department_dep  de,department_vs_center ded,charging_item c "
						+ "  WHERE  c.isActive='Y' and ded.dep_id = de.id and ded.center_num ='"+center_num+"' ";
				if (model.getItem_name() != null && !"".equals(model.getItem_name())) {
					sql += " and  ( c.item_name  like  '%" + model.getItem_name().trim()
							+ "%'   or   c.item_pinyin like   '%" + model.getItem_name().trim() + "%' )  ";
				}
				if (!StringUtil.isEmpty(model.getSex())) {
					sql += (" and (c.sex ='" + model.getSex() + "' or c.sex='全部') ");
				}
				sql += " AND   de.id = c.dep_id  and  de.id='" + li.get(i).getDep_id() + "' ORDER  BY   c.item_seq ";
				List<ChargingItemDTO> item = this.jqm.getList(sql, ChargingItemDTO.class);
				if (item != null && item.size() > 0) {
					li.get(i).setLi(item);
				} else {
					li.remove(i);
				}
			}
		} else if (model.getDep_c() == 9) {
			String di = this.customerInfoService.getCenterconfigByKey("DJT_ADD_DEP", center_num).getConfig_value();
			String[] ids = di.split(",");
			String c_id = "";
			for (int i = 0; i < ids.length; i++) {
				c_id += ids[i] + ",";
			}
			String cc_id = c_id.substring(0, c_id.length() - 1);
			// 科室类型
			String dep_sql = " SELECT d.data_name as  text,convert(varchar(20),id) as id,d.seq_code,'1'  as depttype FROM  data_dictionary  d  where "
					+ " d.data_code='KSLX' and   d.isActive='Y'   and   d.id  in(" + cc_id + ") "
					+ "   order  by   d.seq_code  ";
			@SuppressWarnings("unchecked")
			List<TreeDTO> d_li = this.jqm.getList(dep_sql, TreeDTO.class);
			for (int j = 0; j < d_li.size(); j++) {

				// 科室
				String keshi_sql = "  SELECT  DISTINCT convert(varchar(20),dep_id)   as dep_id,de.dep_name,de.seq_code FROM   department_dep  de,department_vs_center ded,charging_item  ci   WHERE   "
						+ " de.id=ci.dep_id   and ded.dep_id = de.id and ded.center_num ='"+center_num+"' AND  ci.isActive='Y'  AND   de.isActive='Y' and  de.dep_category='"
						+ d_li.get(j).getId() + "'  ";
				if (!StringUtil.isEmpty(model.getSex())) {
					keshi_sql += " and de.sex in('" + model.getSex() + "','全部') ";
				}
				keshi_sql += " order by de.seq_code ";
				List<DJTItemDTO> lii = this.jqm.getList(keshi_sql, DJTItemDTO.class);

				// 项目
				List<ChargingItemDTO> item = new ArrayList<ChargingItemDTO>();
				for (int i = 0; i < lii.size(); i++) {
					String xmsql = " SELECT  c.id,c.item_code,c.item_name,c.amount,c.item_name,c.sex,c.item_type FROM   department_dep  de,department_vs_center ded,charging_item c "
							+ "  WHERE  c.isActive='Y'  and ded.dep_id = de.id and ded.center_num ='"+center_num+"' and ded.dep_id = de.id and ded.center_num ='"+center_num+"' ";
					if (model.getItem_name() != null && !"".equals(model.getItem_name())) {
						xmsql += " and  ( c.item_name  like  '%" + model.getItem_name().trim()
								+ "%'   or   c.item_pinyin like   '%" + model.getItem_name().trim() + "%' )  ";
					}
					if (!StringUtil.isEmpty(model.getSex())) {
						xmsql += (" and (c.sex ='" + model.getSex() + "' or c.sex='全部') ");
					}
					xmsql += " AND   de.id = c.dep_id  and  de.id='" + lii.get(i).getDep_id() + "' ORDER  BY   c.item_seq ";
					item = this.jqm.getList(xmsql, ChargingItemDTO.class);
					if (item != null && item.size() > 0) {
						lii.get(i).setLi(item);
					} else {
						lii.remove(i);
						i--;
					}
				}
				li.addAll(lii);
			}
		} else {
			// 项目
			sql = " SELECT  c.id,c.item_code,c.item_name,c.amount,c.item_name,c.sex,c.item_type FROM   department_dep  de,department_vs_center ded,charging_item c "
					+ "  WHERE  c.isActive='Y'   and ded.dep_id = de.id and ded.center_num ='"+center_num+"' " + " AND   de.id = c.dep_id ";
			if (model.getDep_id() > 0) {
				sql += "and  de.id='" + model.getDep_id() + "'    ";
			}
			if (!StringUtil.isEmpty(model.getSex())) {
				sql += (" and (c.sex ='" + model.getSex() + "' or c.sex='全部') ");
			}
			if (model.getItem_name() != null && !"".equals(model.getItem_name())) {
				sql += "  and   ( c.item_name  like  '%" + model.getItem_name().trim()
						+ "%'   or   c.item_pinyin like   '%" + model.getItem_name().trim() + "%' )  ";
			}
			sql += " ORDER  BY   c.item_seq   ";
			List<ChargingItemDTO> item = this.jqm.getList(sql, ChargingItemDTO.class);

			if (model.getDep_id() > 0) {
				String dep_sql = " SELECT d.id,d.dep_name FROM  department_dep  d  WHERE  d.id='" + model.getDep_id()
						+ "'  AND    d.isActive='Y'  ";
				List<DJTItemDTO> d_li = this.jqm.getList(dep_sql, DJTItemDTO.class);
				if (d_li != null && d_li.size() > 0) {
					d_li.get(0).setLi(item);
					li.add(d_li.get(0));
				}
			} else {
				DJTItemDTO t = new DJTItemDTO();
				t.setLi(item);
				li.add(t);
			}
		}
		return li;
	}

	@Override
	public List<ChargingItemDTO> getSetidChagingitem(ImpCustomerInfoModel model,String center_num) throws ServiceException {
		String sql = " SELECT s.charging_item_id  as   id FROM  set_charging_item s,exam_set  es   where   es.set_num='"
				+ model.getSet_num() + "'   and   es.id = s.exam_set_id and es.center_num='"+center_num+"'";
		List<ChargingItemDTO> li = this.jqm.getList(sql, ChargingItemDTO.class);
		return li;
	}

	@Override
	public List<ChargingItemConsumablesItemDTO> getChargingItemConsumables(ImpCustomerInfoModel model, String center_num )
			throws ServiceException {
		String sql = " SELECT bh.id,bh.item_code,bh.item_name,bh.amount,bh.item_seq,dep.dep_name  FROM   "
				+ " charging_item_consumables_item  cs,charging_item   ch,charging_item bh,department_dep   dep ,department_vs_center ded  "
				+ "  WHERE   dep.id = bh.dep_id  and ded.dep_id = dep.id   and   cs.item_id = ch.id  and   cs.consumables_id = bh.id   and de.center_num ='"+center_num+"'  "
				+ " AND   ch.item_code='" + model.getItem_code() + "' ";
		List<ChargingItemConsumablesItemDTO> li = this.jqm.getList(sql, ChargingItemConsumablesItemDTO.class);
		return li;
	}

	/**
	 * 获取已经加的项目
	 * <p>
	 * Title: getExaminfoChargingItemforExamId
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param examid
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.CustomerInfoService#getExaminfoChargingItemforExamId(long)
	 */
	public List<ExaminfoChargingItemDTO> getExaminfoChargingItemadd(ImpCustomerInfoModel model, String center_num)
			throws ServiceException {
		String sql = "select a.introducer,u.chi_name as jieshaoren,a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,a.updater,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.calculation_rate,a.is_application,a.his_req_status,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.itemnum,b.item_type " + "from examinfo_charging_item a"
				+ " left join user_usr  u on u.id = a.introducer " + " ,charging_item b,department_dep c,department_vs_center de  "
				+ "where a.charge_item_id=b.id and a.isActive='Y'  and de.dep_id = c.id  and b.dep_id=c.id and b.isActive='Y' "
				+ "and a.pay_status<>'M' and a.center_num = '"+center_num+"'  and de.center_num ='"+center_num+"'  "
				+ "and a.exam_num ='"+ model.getExam_num()+"'";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		List<ExaminfoChargingItemDTO> list1 = new ArrayList<ExaminfoChargingItemDTO>();
		for (int i = 0; i < list.size(); i++) {
			ExaminfoChargingItemDTO eci = list.get(i);
			if ("G".equals(eci.getExam_indicator())) {
				if ("Y".equals(eci.getPay_status())) {
					eci.setPay_statuss("已付费");
				} else if ("N".equals(eci.getPay_status())) {
					eci.setPay_statuss("未付费");
				}
			} else if ("T".equals(eci.getExam_indicator())) {
				if ("Y".equals(eci.getPay_status())) {
					eci.setPay_statuss("已付费");
				} else if ("R".equals(eci.getPay_status())) {
					eci.setPay_statuss("预付费");
				}
			} else if ("M".equals(eci.getExam_indicator())) {
				eci.setPay_statuss("免费");
			} else if ("GT".equals(eci.getExam_indicator())) {
				if ("Y".equals(eci.getPay_status())) {
					eci.setPay_statuss("已付费");
				} else if ("N".equals(eci.getPay_status())) {
					eci.setPay_statuss("未付费");
				}
			}
			list1.add(eci);
		}
		return list1;
	}
	
	/**
	 * 
	     * @Title: checkExamInfoRepeat   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param com_id
	     * @param: @param batchid
	     * @param: @param arch_num
	     * @param: @param id_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public long checkExamInfoRepeat(long com_id,long batchid,String arch_num,String id_num) throws ServiceException{
		Connection tjtmpconnect = null;
		long counts = 0;
		if((!StringUtil.isEmpty(arch_num))|| (!StringUtil.isEmpty(id_num))){
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "select  COUNT(*) as counts from exam_info ei,customer_info ci where "
					+ "ei.company_id='"+com_id+"' "
					+ "and ei.batch_id='"+batchid+"' "
					+ "and ei.is_Active='Y' "
					+ "and ci.is_Active='Y' "
					+ "and ei.customer_id=ci.id ";
			if (!StringUtil.isEmpty(arch_num)){
				sb1=sb1+ "and ci.arch_num='"+arch_num.trim()+"' ";
			}else if (!StringUtil.isEmpty(id_num)){
				sb1=sb1+ "and ci.id_num='"+id_num.trim().toUpperCase()+"' ";
			}
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			if (rs1.next()) {
				counts = rs1.getLong("counts");
			} 
			rs1.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
	}
		return counts;
	}
	
	public void update_exam_summary_read_status(String exam_num,UserDTO user){
		String sql="";
		Connection connection = null;
		try {
		    connection = this.jqm.getConnection();
			sql ="update exam_summary  set read_status='0' where center_num = '"+user.getCenter_num()+"' and exam_num='"+exam_num+"'";
			int rs = connection.createStatement().executeUpdate(sql);
			System.out.println(sql);
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
	public List<ExamInfoDTO> getexamnumByArchnum(String arch_num) throws ServiceException {
		String  sql = "select e.id, e.exam_num from customer_info  c , exam_info  e  where e.customer_id = c.id and  e.is_Active = 'Y'  and  c.arch_num = '"+arch_num+"' order by e.join_date";
		List<ExamInfoDTO> list = jqm.getList(sql, ExamInfoDTO.class);
		return list;
	}

	@Override
	public int addDJDimage(long userid, String djd_path, String exam_num) throws ServiceException {
		String sql="";
		int rs= 0;
		Connection connection = null;
		try {
		    connection = this.jqm.getConnection();
			sql =" update  exam_info  set DJD_path = '"+djd_path+"' , DJD_image_creater = "+userid+" where exam_num = '"+exam_num+"'";
			 rs = connection.createStatement().executeUpdate(sql);
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
		return rs;
	}
	
	public String setDjtItemTtoG(String exam_num, long userid,String charging_item_codes) throws ServiceException {
        String mess = "error-操作失败";
	    if(StringUtils.isEmpty(charging_item_codes)){
            mess = "error-请选择项目";
        }
        String sql = "update examinfo_charging_item set pay_status='N',exam_indicator='G',personal_pay=team_pay,team_pay=0,team_pay_status='',update_time='"
                + DateTimeUtil.getDateTime() + "',updater='" + userid + "' where exam_num='" + exam_num
                + "' and pay_status='R' and amount > 0 and exam_indicator='T' and isActive='Y'" +
                " and charging_item_code in ("+charging_item_codes+")";
        String sql1 = "update examinfo_charging_item set pay_status='Y',exam_indicator='G',personal_pay=team_pay,team_pay=0,team_pay_status='',update_time='"
                + DateTimeUtil.getDateTime() + "',updater='" + userid + "' where exam_num='" + exam_num
                + "' and pay_status='R' and amount = 0 and exam_indicator='T' and isActive='Y'" +
                " and charging_item_code in ("+charging_item_codes+")";
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
			mess = "ok-操作成功";
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
//		if(mess.indexOf("ok")==0){
//			ExamUpdateMessageBody body = new ExamUpdateMessageBody();
//			body.setExam_id(examid);
//			ExamUpdateMessage msg = new ExamUpdateMessage(body);
//			ResultHeader rh = msg.send();
//
//			ExamItemUpdateMessageBody itembody = new ExamItemUpdateMessageBody();
//			itembody.setExam_id(examid);
//			ExamItemUpdateMessage itemmsg = new ExamItemUpdateMessage(itembody);
//			ResultHeader itemrh = itemmsg.send();
//		}
		return mess;
	}

	/**
	 * 
	 * <p>
	 * Title: setDjtItemTtoG
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param examid
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.CustomerInfoService#setDjtItemTtoG(long)
	 */
	public String setDjtItemGtoT(String exam_num, long userid,String charging_item_codes) throws ServiceException {
        String mess = "error-操作失败";
	    if(StringUtils.isEmpty(charging_item_codes)){
             mess = "error-请选择收费项目";
        }
        String sql = "update examinfo_charging_item set team_pay_status='R',exam_indicator='T',team_pay=personal_pay,personal_pay=0,pay_status='R',update_time='"
                + DateTimeUtil.getDateTime() + "',updater='" + userid + "' where exam_num='" + exam_num
                + "' and (pay_status='N' or amount = 0) and exam_indicator='G' and  exam_indicator!='T' and isActive='Y'"
                +" and charging_item_code in ("+charging_item_codes+")";
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
			mess = "ok-操作成功";
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
		
//		if(mess.indexOf("ok")==0){
//			ExamUpdateMessageBody body = new ExamUpdateMessageBody();
//			body.setExam_id(examid);
//			ExamUpdateMessage msg = new ExamUpdateMessage(body);
//			ResultHeader rh = msg.send();
//
//			ExamItemUpdateMessageBody itembody = new ExamItemUpdateMessageBody();
//			itembody.setExam_id(examid);
//			ExamItemUpdateMessage itemmsg = new ExamItemUpdateMessage(itembody);
//			ResultHeader itemrh = itemmsg.send();
//		}
		
		return mess;
	}
	
	/**
	 * 
	 * @Title: delExamInfoForIds @Description: 批量无需总监 @param: @param
	 * comids @param: @param batchids @param: @param userid @param: @param
	 * ids @param: @throws ServiceException @return: void @throws
	 */
	public String wxzjExamInfoForIds(String ids, long userid) throws ServiceException {
		String sql = "";
		Connection connection = null;
		String str = "error-无需总检设置失败！";
		try {
			connection = this.jqm.getConnection();
			sql = "update exam_info set wuxuzongjian=(CASE wuxuzongjian WHEN 0 THEN 1 ELSE 0 END),updater=" + userid
					+ ",update_time='" + DateTimeUtil.getDateTime() + "' where id in (" + ids + ") and is_Active='Y' ";
			connection.createStatement().executeUpdate(sql);
			str = "ok-无需总检设置完成！";
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
		return str;
	}

	@Override
	public void saveImpoccuhisInfoForList(List<Impoccuhis> listDTO, long userid, int occuType,int histype) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
			//根据身份证删除已有的信息  重新插入
			try {
				connection = this.jqm.getConnection();
				for (Impoccuhis ii : listDTO) {
					String deleteSQL = "delete  from impoccuhis where id_num ='" + ii.getId_num() +"' AND histype='"+histype+"' AND occuType = '"+occuType+"'";
					connection.createStatement().executeUpdate(deleteSQL);
					String uuid = UUID.randomUUID().toString().replaceAll("-", ""); 
					//执行插入语句
					String insertSQL = "insert into impoccuhis (id,id_num,company,workshop,worktype,startdate,enddate,measure,harmname,concentrations,creater,create_time,occuType,histype) "
							+ "values ('"+uuid+"','"+ii.getId_num()+"','"+ii.getCompany()+"','"+ii.getWorkshop()+"','"+ii.getWorktype()+"','"+ii.getStartdate()+"','"+ii.getEnddate()+"','"+ii.getMeasure()+"','"+ii.getHarmname()+"','"+ii.getConcentrations()+"','"+userid+"','"+ Timeutils.getNowDate()+"','"+occuType+"','"+histype+"')";
					connection.createStatement().executeUpdate(insertSQL);
				}
			} catch (SQLException e) {
				e.printStackTrace();
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
			
		}

	@Override
	public void saveImpoccuhisInfoForListJWS(List<Impoccuhis> listDTO, long userid) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		//根据身份证删除已有的信息  重新插入
		try {
			connection = this.jqm.getConnection();
			for (Impoccuhis ii : listDTO) {
				String deleteSQL = "delete  from impoccuhis where id_num ='" + ii.getId_num()+"' AND histype='1'";
				connection.createStatement().executeUpdate(deleteSQL);
				String uuid = UUID.randomUUID().toString().replaceAll("-", ""); 
				//执行插入语句
				String insertSQL = "insert into impoccuhis (id,id_num,diseases,diagnosisdate,diagnosiscom,diagnosisnotice,diseasereturn,creater,create_time,occuType,histype) "
						+ "values ('"+uuid+"','"+ii.getId_num()+"','"+ii.getDiseases()+"','"+ii.getDiagnosisdate()+"','"+ii.getDiagnosiscom()+"','"+ii.getDiagnosisnotice()+"','"+ii.getDiseasereturn()+"','"+userid+"','"+ Timeutils.getNowDate()+"','2','1')";
				connection.createStatement().executeUpdate(insertSQL);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			
	}
	
	/**
	 * 
	     * @Title: insertSaveOccuhisZys   
	     * @Description: TODO(插入职业史)   
	     * @param: @param connection
	     * @param: @param id_num
	     * @param: @return      
	     * @return: int      
	     * @throws
	 */
	private int insertSaveOccuhisZys(Connection connection, String id_num , long userid, int occuType) {
		
		int retId = 0;
		if(!"".equals(id_num)){
			String sql = "SELECT c.id_num,c.arch_num,e.exam_num  FROM   customer_info  c,exam_info  e"
					+" where   c.is_Active='Y'  and   e.is_Active='Y' and e.apptype=2  and  id_num in('"+id_num+"')  and   c.id=e.customer_id   order  by   c.id  desc, e.create_time desc ";
			
			String zysHql = " SELECT s.company,s.workshop,s.worktype,s.startdate,s.enddate,s.measure,s.harmname,s.concentrations "
					+ "FROM impoccuhis  s  where  s.occuType = '"+occuType+"' AND s.histype = 0 AND s.id_num = '"+id_num+"'";
			
			ResultSet psd_rs = null;
			ResultSet zys_rs = null;
			try {
				psd_rs = connection.createStatement().executeQuery(sql);
				zys_rs = connection.createStatement().executeQuery(zysHql);
				while(psd_rs.next()){ 
					while(zys_rs.next()){ 
						String uuid = UUID.randomUUID().toString().replaceAll("-", "");
						String insertSql = "insert into zyb_occuhis (id,id_num,arch_num,exam_num,company,workshop,worktype,"
							+ "startdate,enddate,measure,harmname,concentrations,isradiation,creater,create_date) "
							+ "values ('"+uuid+"','"+id_num+"','"+psd_rs.getString(2)+"','"+psd_rs.getString(3)+"','"+zys_rs.getString(1)+"',"
									+ "'"+zys_rs.getString(2)+"','"+zys_rs.getString(3)+"','"+zys_rs.getString(4)+"','"+zys_rs.getString(5)+"',"
									+ "'"+zys_rs.getString(6)+"','"+zys_rs.getString(7)+"','"+zys_rs.getString(8)+"','0','"+userid+"','"+DateTimeUtil.getDateTime()+"')";
						try {
							connection.createStatement().execute(insertSql);
						} catch (SQLException e) {
							e.printStackTrace();
							//return 0;
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			//删除导入的数据
			try {
				connection.createStatement().executeUpdate("delete from impoccuhis  where  occuType = '"+occuType+"' AND  histype = '0' AND  id_num = '"+id_num+ "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			retId = 1;
		}
		return retId;
	}
	
	
	/**
	 * 
	     * @Title: insertSaveOccuhisJws   
	     * @Description: TODO(插入既往史)   
	     * @param: @param connection
	     * @param: @param id_num
	     * @param: @return      
	     * @return: int      
	     * @throws
	 */
	private int insertSaveOccuhisJws(Connection connection, String id_num , long userid, int occuType) {
		
		int retId = 0;
		if(!"".equals(id_num)){
			String sql = "SELECT c.id_num,c.arch_num,e.exam_num FROM   customer_info  c,exam_info  e"
					+" where   c.is_Active='Y'  and   e.is_Active='Y' and e.apptype=2  and  id_num in('"+id_num+"')  and   c.id=e.customer_id   order  by   c.id  desc, e.create_time desc ";
			
			String zysHql = " SELECT  s.diseases,s.diagnosisdate,s.diagnosiscom,s.diagnosisnotice,s.diseasereturn "
					+ "FROM impoccuhis  s  where  s.occuType = '"+occuType+"' AND s.histype = 1 AND s.id_num = '"+id_num+"'";
			
			ResultSet psd_rs = null;
			ResultSet zys_rs = null;
			try {
				psd_rs = connection.createStatement().executeQuery(sql);
				zys_rs = connection.createStatement().executeQuery(zysHql);
				while(psd_rs.next()){ 
					while(zys_rs.next()){ 
						String uuid = UUID.randomUUID().toString().replaceAll("-", "");
						String insertSql = "insert into Zyb_disease_History (id,id_num,arch_num,exam_num,diseases,"
								+ "diagnosisdate,diagnosiscom,diagnosisnotice,diseasereturn,creater,create_time ) "
								+ "values ('"+uuid+"','"+id_num+"','"+psd_rs.getString(2)+"','"+psd_rs.getString(3)+"','"+zys_rs.getString(1)+"',"
								+ "'"+zys_rs.getString(2)+"','"+zys_rs.getString(3)+"','"+zys_rs.getString(4)+"','"+zys_rs.getString(5)+"' ,'"+userid+"','"+DateTimeUtil.getDateTime()+"')";
						try {
							connection.createStatement().execute(insertSql);
						} catch (SQLException e) {
							e.printStackTrace();
							//return 0;
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			//删除导入的数据
			try {
				connection.createStatement().executeUpdate("delete from impoccuhis  where  occuType = '"+occuType+"' AND  histype = '1' AND  id_num = '"+id_num+ "'");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			retId = 1;
		}
		return retId;
		
	}
	
	/**
	 * 
	 * @Title: updateSampleExamEetail @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param examid @param: @param
	 * userid @param: @throws ServiceException @return: void @throws
	 */
	public void updateSampleExamEetail(long examid, long userid,String user_name) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String sql = "select m.sample_id,id from sample_exam_detail m where m.exam_info_id='" + examid
					+ "' and m.status='W'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while (rs.next()) {
//				String sample_id = rs.getString("sample_id");
				String detailid = rs.getString("id");
//				sql = "select b.BarCode_Class from sample_demo b where b.id='" + sample_id + "' and b.isActive='Y'";
//				ResultSet rs1 = connection.createStatement().executeQuery(sql);
//				if (rs1.next()) {
//					int barcodeclass = rs1.getInt("BarCode_Class");
//					if (barcodeclass == 0) {
						sql = "update sample_exam_detail set status='Y',check_id="+userid+",check_doctor='"+user_name+"',check_date='"+DateTimeUtil.getDateTime()+"'  where id=" + detailid;
						connection.createStatement().executeUpdate(sql);
//					}
//				}
//				rs1.close();
			}
			rs.close();

			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
			}
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
	}

	@Override
	public CustomerInfo getCustomerinfoByExamNum(String exam_num) throws ServiceException {
		String sql = "select  c.* from customer_info  c ,exam_info e where e.customer_id = c.id  and e.exam_num = '"+exam_num+"'";
		List<CustomerInfo> list = this.jqm.getList(sql, CustomerInfo.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public String delExamInfoCheck(ExamInfoModel model) throws ServiceException {
		String sql = " select  eci.is_application,eci.pay_status ,eci.exam_status,eci.his_req_status ,ci.item_name "
					+" from  examinfo_charging_item  eci,charging_item ci     where  eci.charging_item_code = ci.item_code and  eci.exam_num = '"+model.getExam_num()+"'  and eci.isActive ='Y' ";
		List<ExaminfoChargingItemDTO> list = this.jqm.getList(sql, ExaminfoChargingItemDTO.class);
		if(list.size() > 0){
			for (ExaminfoChargingItemDTO examinfoChargingItem : list) {
				if ("Y".equals(examinfoChargingItem.getExam_status())) {//已检
					return "error-【 "+examinfoChargingItem.getItem_name()+" 】 已检";
				}
				if ("Y".equals(examinfoChargingItem.getPay_status())) {//付费状态
					return "error-【 "+examinfoChargingItem.getItem_name()+" 】 已付费";
				}
				if ("Y".equals(examinfoChargingItem.getIs_application())) {//已发申请
					return "error-【 "+examinfoChargingItem.getItem_name()+" 】 已发申请";
				}
				if ("Y".equals(examinfoChargingItem.getHis_req_status())) {//已发申请
					return "error-【 "+examinfoChargingItem.getItem_name()+" 】 已发申请";
				}
			}
		}
		return "ok- ";
	}
	/**
	 * 
	 * @Title: getbatchList @Description: 得到方案列表 @param: @param
	 *         company_Id @param: @param pagesize @param: @param
	 *         pageno @param: @return @param: @throws ServiceException @return:
	 *         PageReturnDTO @throws
	 */
	@SuppressWarnings("unchecked")
	public List<BatchDTO> getbatch(String center_num) throws ServiceException {
		String sql = "select a.id,a.company_id,a.batch_num,a.batch_name,a.pay_way,"
				+ "a.creater,a.create_time,a.updater,a.update_time,a.is_Active,"
				+ "a.exam_item,a.exam_number,a.exam_date,a.exam_date_end,a.charge_type,"
				+ "a.contact_name,a.sales_name,a.introducer_name,a.accommodation,"
				+ "a.dine,a.exam_fee,a.remark,a.phone,a.invoice_title,a.batch_address,"
				+ "a.qian_remark,a.settlement,b.data_name,a.checktype,a.checkuser,a.checkdate,a.checknotice"
				+ ",(select count(id) from exam_info e where e.batch_id = a.id and e.is_Active = 'Y') exam_count"
				+ " From  batch a  left join data_dictionary b on a.pay_way=b.id where a.is_Active='Y' and a.center_num='"+center_num+"' "
				+ "  order by a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 1000, BatchDTO.class);
		List<BatchDTO> webrole = new ArrayList<BatchDTO>();
		
		if((map!=null)&&(map.getList()!=null)){
			webrole = map.getList();
		}

		return webrole;
	}
	
	/**
	 * 
	 * @Title: getChargingItemForId @Description:
	 * id获取ChargingItemDTO @param: @param
	 * chargingid @param: @return @param: @throws ServiceException @return:
	 * ChargingItemDTO @throws
	 */
	public ChargingItemDTO getChargingItemForId(long chargingid) throws ServiceException {
		String sql = "select id,dep_id,sam_demo_id,sam_report_demo_id,item_code,item_name,"
				+ "item_pinyin,item_category,sex,amount,dep_category,isOnlyApplyOrReport,"
				+ "item_seq,guide_category,his_num,exam_num,view_num,isActive,creater,"
				+ "create_time,updater,update_time,calculation_amount,interface_flag,"
				+ "item_type,charge_inter_num,item_abbreviation,notices from charging_item where id=" + chargingid;
		PageSupport map = this.jqm.getList(sql, 1, 1000, ChargingItemDTO.class);
		ChargingItemDTO webrole = new ChargingItemDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ChargingItemDTO) map.getList().get(0);
		}
		return webrole;
	}

	public List<GroupChargingItemDTO> getRelistitem(String exam_num,String apptype) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		List<GroupChargingItemDTO> list = new ArrayList<GroupChargingItemDTO>();
		sb.append("select ci.id,ci.item_code,ci.item_name,ci.amount as item_amount,"
				+ "ci.amount from exam_summary_review_item esri,charging_item ci where "
				+ "ci.isActive='Y' and ci.id=esri.charging_item_id and esri.exam_num='" + exam_num + "' and esri.apptype = '"+apptype+"'");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, GroupChargingItemDTO.class);
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if ((map != null) && (map.getList().size() > 0)) {
			list = map.getList();
		}
		return list;
	}

	public String addZYBRecheckChargeItem(String oldExam_num,String exam_num,UserDTO user,String appyype)throws ServiceException{
		List<GroupChargingItemDTO> relistitem = this.getRelistitem(oldExam_num, appyype);
		if(relistitem.size() <= 0 ){//总检室 没有添加项目
			return "ok-总检室 没有添加项目";
		}
		ExamInfoDTO ed = this.getExamInfoForexamNum(exam_num);
		if(ed == null ){
			return "error-体检信息无效。";
		}
		String indicator = ed.getExam_type();
		String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();

		List<ExaminfoChargingItem> itemlist = new ArrayList<ExaminfoChargingItem>();
		itemlist = zybCustomerInfoService.getZybExaminfoChargingItemForId(exam_num,user.getCenter_num());
		int isaddcount = zybCustomerInfoService.getZybIsnewaddedExamInfoItemForId(exam_num,user.getCenter_num());

		for (int j = 0; j < relistitem.size(); j++) {
			GroupChargingItemDTO es = new GroupChargingItemDTO();
			es = relistitem.get(j);
			ChargingItemDTO cd = this.batchService.findChargeItemById(es.getItem_code());
			int flagitem = 0;

			for (ExaminfoChargingItem oldExaminfoChargingItem : itemlist) {
				if (oldExaminfoChargingItem.getCharge_item_id() == cd.getId()) {
					flagitem = 1;
					break;
				}
			}
			if (flagitem == 0) {
				if ("G".equals(indicator)) {
					es.setPersonal_pay(es.getAmount());
					es.setTeam_pay(0);
					es.setExam_indicators(indicator);
				}else if("T".equals(indicator)){
					es.setPersonal_pay(0);
					es.setTeam_pay(es.getAmount());
					es.setExam_indicators(indicator);
				}

				es.setCharge_item_id(cd.getId());
				// 新增 examinfo_charging_item、
				ExaminfoChargingItem eci = new ExaminfoChargingItem();
				eci.setExaminfo_id(ed.getId());
				eci.setCharge_item_id(cd.getId());
				eci.setExam_num(ed.getExam_num());
				/*
				 * if((ed.getChargingType()!=null)&&("165".equals(ed.
				 * getChargingType()))){ eci.setExam_indicator("M"); }else{
				 * eci.setExam_indicator(es.getExam_indicators()); }
				 */
				eci.setItem_amount(es.getItem_amount());
				eci.setDiscount(es.getDiscount());
				eci.setAmount(es.getAmount());
				eci.setIsActive("Y");
				eci.setCharging_item_code(es.getItem_code());
				eci.setFinal_exam_date(DateTimeUtil.parse());
				eci.setExam_indicator(es.getExam_indicators());
				eci.setItemnum(es.getItemnum());
				eci.setPersonal_pay(es.getPersonal_pay());
				eci.setTeam_pay(es.getTeam_pay());

				if("T".equals(indicator))
				{
					eci.setPay_status("R");
				}else{
					if(es.getPersonal_pay()==0)
					{
						if("Y".equals(IS_CHARGINGWAY_ZERO)){
							eci.setPay_status("N");
						}else{
							eci.setPay_status("Y");
						}
					}else{
						eci.setPay_status("N");
					}
				}

				eci.setTeam_pay_status("R");
				/*
				 * if(es.getTeam_pay()==0) { eci.setTeam_pay_status("Y");
				 * }else{ eci.setTeam_pay_status("N"); }
				 */

				eci.setExam_status("N");
				eci.setIs_new_added(isaddcount);
				eci.setCreater(user.getUserid());
				eci.setCreate_time(DateTimeUtil.parse());
				eci.setUpdater(user.getUserid());
				eci.setUpdate_time(DateTimeUtil.parse());
				eci.setCheck_status(0);
				eci.setCalculation_amount(es.getAmount());
				eci.setIs_application("N");
				eci.setChange_item("N");
				eci.setTeam_pay(es.getTeam_pay());
				eci.setPersonal_pay(es.getPersonal_pay());
				eci.setHis_req_status("N");
				//eci.setApp_type("2");
				if(1==cd.getApp_type()) {
					eci.setApp_type("1");
				} else {
					eci.setApp_type("2");
				}
				eci.setCharging_item_code(es.getItem_code());
				eci.setCenter_num(user.getCenter_num());
				eci.setExam_indicator(indicator);
				zybCustomerInfoService.saveZybExaminfoChargingItem(eci);
				// System.out.println(esdto.getExaminfo_id()+" 1 "+" "+j);
				ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());

				//判断是否是lis检查项目
				int lisnum = zybCustomerInfoService.checkZybListItem(es.getItem_code(),user.getCenter_num());
				if (lisnum > 0) {
					SampleExamDetail sd = new SampleExamDetail();
					sd = zybCustomerInfoService.getZybSampleExamDetailforExamid(ed.getExam_num(), ci.getSam_demo_id(), "W");
					long samexam_id = 0;
					String sample_barcode = "";
					if ((sd == null) || (sd.getId() <= 0)) {
						sample_barcode = this.batchService.GetCreateID("barcode", user.getCenter_num());
						SampleExamDetail sed = new SampleExamDetail();
						sed.setExam_info_id(ed.getId());
						sed.setSample_id(ci.getSam_demo_id());
						sed.setSample_barcode(sample_barcode);
						sed.setStatus("W");
						sed.setPic_path("");
						sed.setCenter_num(user.getCenter_num());
						sed.setCreater(user.getUserid());
						sed.setCreate_time(DateTimeUtil.parse());
						sed.setUpdater(user.getUserid());
						sed.setUpdate_time(DateTimeUtil.parse());
						sed.setExam_num(ed.getExam_num());
						zybCustomerInfoService.saveZybSampleExamDetail(sed);
						samexam_id = sed.getId();
						// System.out.println(esdto.getExaminfo_id()+" 2 "+"
						// "+j);
					} else {
						samexam_id = sd.getId();
						sample_barcode = sd.getSample_barcode();
					}
					// System.out.println(esdto.getExaminfo_id()+" 3 "+"
					// "+j);
					ExamResultChargingItem erci = new ExamResultChargingItem();
					erci.setCharging_id(cd.getId());
					erci.setCharging_item_code(cd.getItem_code());
					erci.setExam_id(samexam_id);
					erci.setResult_type(CommSet.lis_sample);
					erci.setIsActive("Y");
					erci.setCreater(user.getUserid());
					erci.setCreate_time(DateTimeUtil.parse());
					erci.setUpdater(user.getUserid());
					erci.setUpdate_time(DateTimeUtil.parse());
					erci.setBar_code(sample_barcode);
					erci.setCharging_item_code(ci.getItem_code());
					zybCustomerInfoService.saveZybExamResultChargingItem(erci);
					// System.out.println(esdto.getExaminfo_id()+" 4 "+"
					// "+j);
				}

				//判断是否是pass项目
				int pacsnum = zybCustomerInfoService.checkZybPacsItem(es.getItem_code(),user.getCenter_num());
				// System.out.println(esdto.getExaminfo_id()+" 5 "+" "+j);
				if (pacsnum > 0) {
					long summary_id = 0;
					PacsSummary psd = new PacsSummary();
					psd = zybCustomerInfoService.getZybPacsSummaryDTOforExamid(ed.getExam_num(), ci.getSam_demo_id(), "N");
					String pacs_req_code="";
					if ((psd == null) || (psd.getId() <= 0)) {
						pacs_req_code=this.batchService.GetCreateID("pacs_req_num", user.getCenter_num());
						PacsSummary ps = new PacsSummary();
						ps.setExaminfo_num(ed.getExam_num());
						ps.setExaminfo_name(ed.getUser_name());
						ps.setExaminfo_sex(ed.getSex());

						if ((!StringUtil.isEmpty(ed.getBirthday())) && (ed.getBirthday().trim().length() == 10)) {
							Date da = Timeutils.strToDateLong2(ed.getBirthday().trim().replaceAll("-", ""));
							ps.setExaminfo_birthday(da);
						} else if ((!StringUtil.isEmpty(ed.getBirthday()))
								&& (ed.getBirthday().trim().length() == 8)) {
							Date da = Timeutils.strToDateLong2(ed.getBirthday().trim());
							ps.setExaminfo_birthday(da);
						}
						ps.setApply_person(user.getName());
						ps.setApply_date(DateTimeUtil.parse());
						ps.setExam_status("N");
						ps.setCreater(user.getUserid());
						ps.setCreate_time(DateTimeUtil.parse());
						ps.setUpdater(user.getUserid());
						ps.setUpdate_time(DateTimeUtil.parse());
						ps.setExaminfo_sampleId(ci.getSam_demo_id());
						ps.setPacs_req_code(pacs_req_code);
						zybCustomerInfoService.saveZybPacsSummary(ps);
						summary_id = ps.getId();
					} else {
						summary_id = psd.getId();
						pacs_req_code=psd.getPacs_req_code();
					}
					// System.out.println(esdto.getExaminfo_id()+" 8 "+"
					// "+j);
					Pacsdetail pd = new Pacsdetail();
					pd.setExaminfo_num(ed.getExam_num());
					pd.setSummary_id(summary_id);
					pd.setChargingItem_num(es.getItem_code());
					pd.setChargingItem_name(es.getItem_name());
					DepartmentDep dd = departmentService.loadDepartmentDep(cd.getDep_id());
					pd.setDep_num(dd.getDep_num());
					pd.setDep_name(dd.getDep_name());
					pd.setExaminfo_sampleId(ci.getSam_demo_id());
					pd.setIs_need_return("Y");
					pd.setCreater(user.getUserid());
					pd.setCreate_time(DateTimeUtil.parse());
					pd.setUpdater(user.getUserid());
					pd.setUpdate_time(DateTimeUtil.parse());
					pd.setPacs_req_code(pacs_req_code);
					zybCustomerInfoService.saveZybPacsdetail(pd);
					// System.out.println(esdto.getExaminfo_id()+" 9 "+"
					// "+j);
				}
			}
		}
		return "ok-添加成功。";
	}
    @Override
    public ExamInfoUserDTO getNumberOfpeople(RegisterModel model,String app_type,UserDTO user) throws ServiceException {
        String sql = " SELECT count(*) as join_num  FROM exam_info e WHERE  e.is_Active='Y' "
                +"   and e.status in('Z','D','J')  AND exam_center_num = '"+user.getCenter_num()+"' and is_Active='Y' and apptype = '"+app_type+"' ";
        if(!StringUtils.isEmpty(model.getTime1())){
            sql += "  AND e.join_date >='"+model.getTime1()+"' ";
        }
        if(!StringUtils.isEmpty(model.getTime2())){
            sql += "  AND  e.join_date <= '"+model.getTime2()+" 23:59:59.000' ";
        }
        List<ExamInfoUserDTO> li = this.jqm.getList(sql,ExamInfoUserDTO.class);
        return li != null && li.size() > 0 ? li.get(0) : null;
    }
    public List<ExaminfoChargingItem> getExaminfoChargingItemByExamNum(String exam_num)throws ServiceException {
        String hql = " from ExaminfoChargingItem g where g.exam_num=" + exam_num + " and g.exam_status<>'G' and g.pay_status<>'M' and isActive='Y' ";
        List<ExaminfoChargingItem> list= new ArrayList<ExaminfoChargingItem>();
        list = qm.find(hql);
        return list;
    }
    /**
     *
     * @Title: djtChargeExamInfoGChangItem
     * @Description:登记台换项
     * @param: @param batch
     * @param: @throws ServiceException
     * @return: void
     * @throws
     */
    public String djtChargeExamInfoGChangItem(ExaminfoSetDTO esdto,List<GroupChargingItemDTO> oldlistitem, List<GroupChargingItemDTO> listitem,String indicator,long userid,String username,String centerNum) throws ServiceException {
        ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(esdto.getExam_num());
        String message = "error-";
        String account_num = "";
        if ((ed == null) || (ed.getId() <= 0)) {
            message = "error-无效体检人员";
        } else {
		        /*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");
		        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务
		        TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态
                */

            //删除原来的项目开始
            List<ExaminfoChargingItem> olditemlist = new ArrayList<ExaminfoChargingItem>();
            olditemlist = this.getExaminfoChargingItemByExamNum(esdto.getExam_num());
            for (GroupChargingItemDTO es : oldlistitem) {
                ChargingItemDTO cd = this.batchService.findChargeItemById(es.getItem_code());
                for (ExaminfoChargingItem oldExaminfoChargingItem : olditemlist) {
                    if (oldExaminfoChargingItem.getCharge_item_id() == cd.getId()) {
                        oldExaminfoChargingItem.setCreater(userid);
                        oldExaminfoChargingItem.setCreate_time(DateTimeUtil.parse());
                        oldExaminfoChargingItem.setUpdater(userid);
                        oldExaminfoChargingItem.setUpdate_time(DateTimeUtil.parse());
                        oldExaminfoChargingItem.setIsActive("N");
                        this.pm.update(oldExaminfoChargingItem);

                        List<TeamAccountItemList> cardlist = this.qm.find(
                                "from TeamAccountItemList c where c.exam_num = '" + ed.getExam_num() + "' and charging_item_id='" + cd.getId() + "'");
                        for (TeamAccountItemList ti : cardlist) {
                            account_num = ti.getAcc_num();
                            this.pm.remove(ti);
                        }
                    }

                }
            }

            //删除原来的项目结束

            //List<ExaminfoChargingItem> itemlist= new ArrayList<ExaminfoChargingItem>();
            //itemlist = this.getExaminfoChargingItemForId(esdto.getExaminfo_id());
            //int isaddcount=getIsnewaddedExamInfoItemForId(esdto.getExaminfo_id());

            for (int j = 0; j < listitem.size(); j++) {
                GroupChargingItemDTO es = new GroupChargingItemDTO();
                es = listitem.get(j);
                ChargingItemDTO cd = this.batchService.findChargeItemById(es.getItem_code());
                if ("G".equals(indicator)) {
                    es.setPersonal_pay(es.getAmount());
                    es.setTeam_pay(0);
                }
                es.setCharge_item_id(cd.getId());
                //新增 examinfo_charging_item、
                ExaminfoChargingItem eci = new ExaminfoChargingItem();
                eci.setExaminfo_id(esdto.getExaminfo_id());
                eci.setCharge_item_id(cd.getId());
	                    /*if((ed.getChargingType()!=null)&&("165".equals(ed.getChargingType()))){
	                    	eci.setExam_indicator("M");
						}else{
							eci.setExam_indicator(es.getExam_indicators());
						}*/
                eci.setExam_indicator(es.getExam_indicators());
                eci.setItem_amount(es.getItem_amount());
                eci.setDiscount(es.getDiscount());
                eci.setAmount(es.getAmount());
                eci.setIsActive("Y");
                eci.setFinal_exam_date(DateTimeUtil.parse());
                if ("G".equals(es.getExam_indicators())) {
                    eci.setPay_status("N");
                    eci.setTeam_pay_status("Y");
                } else if ("GT".equals(es.getExam_indicators())) {
                    eci.setPay_status("N");
                    eci.setTeam_pay_status("R");
                } else {
                    eci.setPay_status("R");
                    eci.setTeam_pay_status("R");
                }

                eci.setExam_status("N");
                eci.setIs_new_added(9);
                eci.setCreater(userid);
                eci.setCreate_time(DateTimeUtil.parse());
                eci.setUpdater(userid);
                eci.setUpdate_time(DateTimeUtil.parse());
                eci.setCheck_status(0);
                eci.setCalculation_amount(es.getAmount());
                eci.setIs_application("N");
                eci.setChange_item("N");
                eci.setHis_req_status("N");
                eci.setExam_num(ed.getExam_num());
                eci.setTj_charge_status("N");
                eci.setTj_charge_amount(0);
                eci.setCharging_item_code(cd.getItem_code());
                eci.setItemnum(es.getItemnum());
                eci.setTeam_pay(es.getTeam_pay());
                eci.setPersonal_pay(es.getPersonal_pay());
                eci.setExam_num(es.getExam_num());
                this.pm.save(eci);
                //System.out.println(esdto.getExaminfo_id()+"   1   "+"   "+j);
                ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());
                int lisnum = this.customerInfoService.checkListItem(es.getItem_code(),centerNum);
                if (lisnum > 0) {
                    SampleExamDetail sd = new SampleExamDetail();
                    sd = this.customerInfoService.getSampleExamDetailforExamid(esdto.getExam_num(), ci.getSam_demo_id(), "W");
                    long samexam_id = 0;
                    String sample_barcode = "";
                    if ((sd == null) || (sd.getId() <= 0)) {
                        sample_barcode = this.batchService.GetCreateID("barcode",centerNum);
                        SampleExamDetail sed = new SampleExamDetail();
                        sed.setExam_info_id(esdto.getExaminfo_id());
                        sed.setSample_id(ci.getSam_demo_id());
                        sed.setSample_barcode(sample_barcode);
                        sed.setStatus("W");
                        sed.setPic_path("");
                        sed.setCenter_num(centerNum);
                        sed.setCreater(userid);
                        sed.setCreate_time(DateTimeUtil.parse());
                        sed.setUpdater(userid);
                        sed.setUpdate_time(DateTimeUtil.parse());
                        sed.setExam_num(sd.getExam_num());
                        this.pm.save(sed);
                        samexam_id = sed.getId();
                        //System.out.println(esdto.getExaminfo_id()+"   2   "+"   "+j);
                    } else {
                        samexam_id = sd.getId();
                        sample_barcode = sd.getSample_barcode();
                    }
                    //System.out.println(esdto.getExaminfo_id()+"   3   "+"   "+j);
                    ExamResultChargingItem erci = new ExamResultChargingItem();
                    erci.setCharging_id(cd.getId());
                    erci.setExam_id(samexam_id);
                    erci.setResult_type(CommSet.lis_sample);
                    erci.setIsActive("Y");
                    erci.setCreater(userid);
                    erci.setCreate_time(DateTimeUtil.parse());
                    erci.setUpdater(userid);
                    erci.setUpdate_time(DateTimeUtil.parse());
                    erci.setBar_code(sample_barcode);
                    erci.setCharging_item_code(ci.getItem_code());
                    this.pm.save(erci);
                    //System.out.println(esdto.getExaminfo_id()+"   4   "+"   "+j);
                }

                int pacsnum = this.customerInfoService.checkPacsItem(es.getItem_code(),centerNum);
                //System.out.println(esdto.getExaminfo_id()+"   5   "+"   "+j);
                if (pacsnum > 0) {
                    long summary_id = 0;
                    PacsSummary psd = new PacsSummary();
                    psd = this.customerInfoService.getPacsSummaryDTOforExamid(ed.getExam_num(), ci.getSam_demo_id(), "N");
                    if ((psd == null) || (psd.getId() <= 0)) {
                        psd = new PacsSummary();
                        psd.setExaminfo_num(ed.getExam_num());
                        psd.setExaminfo_name(ed.getUser_name());
                        psd.setExaminfo_sex(ed.getSex());
                        if ((!StringUtil.isEmpty(ed.getBirthday())) && (ed.getBirthday().trim().length() == 10)) {
                            Date da = Timeutils.strToDateLong2(ed.getBirthday().trim().replaceAll("-", ""));
                            psd.setExaminfo_birthday(da);
                        } else if ((!StringUtil.isEmpty(ed.getBirthday())) && (ed.getBirthday().trim().length() == 8)) {
                            Date da = Timeutils.strToDateLong2(ed.getBirthday().trim());
                            psd.setExaminfo_birthday(da);
                        }
                        psd.setApply_person(username);
                        psd.setApply_date(DateTimeUtil.parse());
                        psd.setExam_status("N");
                        psd.setCreater(userid);
                        psd.setCreate_time(DateTimeUtil.parse());
                        psd.setUpdater(userid);
                        psd.setUpdate_time(DateTimeUtil.parse());
                        psd.setExaminfo_sampleId(ci.getSam_demo_id());
                        psd.setPacs_req_code(this.batchService.GetCreateID("pacs_req_num",centerNum));
                        psd.setSample_status("N");
                        this.pm.save(psd);
                        summary_id = psd.getId();
                        //System.out.println(esdto.getExaminfo_id()+"   7   "+"   "+j);
                    } else {
                        summary_id = psd.getId();
                    }
                    //System.out.println(esdto.getExaminfo_id()+"   8   "+"   "+j);
                    Pacsdetail pd = new Pacsdetail();
                    pd.setExaminfo_num(ed.getExam_num());
                    pd.setSummary_id(summary_id);
                    pd.setChargingItem_num(es.getItem_code());
                    pd.setChargingItem_name(es.getItem_name());
                    DepartmentDep dd = departmentService.loadDepartmentDep(cd.getDep_id());
                    pd.setDep_num(dd.getDep_num());
                    pd.setDep_name(dd.getDep_name());
                    pd.setExaminfo_sampleId(ci.getSam_demo_id());
                    pd.setIs_need_return("Y");
                    pd.setCreater(userid);
                    pd.setCreate_time(DateTimeUtil.parse());
                    pd.setUpdater(userid);
                    pd.setUpdate_time(DateTimeUtil.parse());
                    pd.setPacs_req_code(psd.getPacs_req_code());
                    this.pm.save(pd);
                    //System.out.println(esdto.getExaminfo_id()+"   9   "+"   "+j);
                }
                if (account_num.length() > 0) {
                    TeamAccountItemList ti = new TeamAccountItemList();
                    ti.setAcc_num(account_num);
                    ti.setAcc_charge(eci.getTeam_pay());
                    ti.setCenter_num("001");
                    ti.setCharging_item_id(eci.getCharge_item_id());
                    ti.setCreater(userid);
                    ti.setDiscard("N");
                    ti.setExam_num(ed.getExam_num());
                    ti.setIn_date(DateTimeUtil.parse());
                    ti.setPersonal_pay(eci.getPersonal_pay());
                    ti.setPrePay("N");
                    ti.setPrice(eci.getAmount());
                    ti.setRate(Float.valueOf(eci.getDiscount() + ""));
                    this.pm.save(ti);
                }

            }
            message = "ok-体检人员交费信息处理完成";
        }
        return message;
    }
    /**
     * 获取缴付项目的个人和团体金额
     * @Title: getExamInfoAmtForCIid
     * @Description: TODO(这里用一句话描述这个方法的作用)
     * @param: @param examid
     * @param: @param ciids
     * @param: @return
     * @param: @throws ServiceException
     * @return: String
     * @throws
     */
    public ECIAmountDTO getExamInfoAmtForCIid(long examid,String ciids)throws ServiceException{
        Connection connection = null;
        ECIAmountDTO amts=new ECIAmountDTO();
        try {
            // 读取记录数
            connection = this.jqm.getConnection();
            String sql = "select COALESCE(sum(eci.personal_pay),0) as personal_pay,COALESCE(sum(eci.team_pay),0) as team_pay "
                    + "from examinfo_charging_item eci where eci.isActive='Y' and eci.examinfo_id='"+examid+"' "
                    + "and eci.charge_item_id in('"+ciids+"')";
            ResultSet rs = connection.createStatement().executeQuery(sql);
            if (rs.next()) {
                amts.setTeam_amt(rs.getDouble("team_pay"));
                amts.setPers_amt(rs.getDouble("personal_pay"));
            }
            rs.close();
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
            }
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
        return amts;
    }
}
