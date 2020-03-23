package com.hjw.zyb.service.impl;

import com.hjw.util.CommSet;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.util.Timeutils;
import com.hjw.util.TranLogTxt;
import com.hjw.util.ValidateUtil;
import com.hjw.webService.client.jianqiao.PutReportControlJQ;
import com.hjw.webService.client.jianqiao.bean.req.BodyJQ;
import com.hjw.webService.client.wuwei.PutReportControlWW;
import com.hjw.webService.client.wuwei.bean.req.PERSON;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoBatchDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.PacsSummaryDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.SetChargingItemDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoSet;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.domain.SetChargingItem;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.WebConfigService;
import com.hjw.zyb.DTO.*;
import com.hjw.zyb.domain.ExamInfoExt;
import com.hjw.zyb.domain.SetOccuhazardfactors;
import com.hjw.zyb.domain.ZybEconomicIndustry;
import com.hjw.zyb.domain.ZybExamOccuhazardfactors;
import com.hjw.zyb.domain.ZybOccuHis;
import com.hjw.zyb.domain.ZybOccuhazardfactors;
import com.hjw.zyb.domain.ZybTypeOfWork;
import com.hjw.zyb.model.ZybImpCustomerInfoModel;
import com.hjw.zyb.model.ZybInquisitionResultModel;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.hjw.zyb.service.ZybOccuService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringUtil;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: yangm
 * @date: 2016年7月4日 上午9:37:08
 * @version V2.0.0.0
 */
public class ZybCustomerInfoServiceImpl implements ZybCustomerInfoService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	private CompanyService companyService;
	private BatchService batchService;
	private WebConfigService webConfigService;
	private DepartmentService departmentService;
    private ZybOccuService zyb_occuService;  
    private ZybCustomerInfoService zybCustomerInfoService;
    private CustomerInfoService  customerInfoService;
	  
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setZybCustomerInfoService(
			ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}

	public void setZyb_occuService(ZybOccuService zyb_occuService) {
		this.zyb_occuService = zyb_occuService;
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
	     * @Title: getCustOccuHisList   
	     * @Description: 获取职业病历史   
	     * @param: @param exam_num
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getCustOccuHisList(String isradiation,String exam_num,int pagesize, int pageno)
			throws ServiceException {
		String sql = "select radiation,cumulative_exposure,History_excessive,Remark,man_haur as man_haur,id,id_num,arch_num,exam_num,company,workshop,worktype,startdate,enddate,"
				+ "measure,harmname,concentrations,isradiation,creater,create_date,remark1,remark2,remark3" 
				+ " From  zyb_occuhis where exam_num='"+ exam_num + "' and  isradiation='"+isradiation+"' order by startdate,enddate";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybOccuHisDTO.class);
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
	     * <p>Title: getExamInfoOne</p>   
	     * <p>Description: 职业病单独体检人员信息录入</p>   
	     * @param comids
	     * @param batchids
	     * @param userid
	     * @param eu
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CustomerInfoService#getExamInfoOne(long, long, long, java.lang.String, com.hjw.wst.DTO.ExamInfoUserDTO)
	 */
	public String getExamInfoOne(long comids, long batchids, long userid,String centernum,ZybExamInfoUserDTO eu)throws ServiceException {
		String message = "error-保存失败";
		message = getExamInfoForFlagList(eu, userid, centernum, batchids, comids);
		if (message.indexOf("ok") < 0) {
			message = "error-" + message;
		}
		return message;
	}

	// ----------------------------------------私有方法开始-------------------------------------------------
	/**
	 * 
	 * @Title: getExamInfoForFlagList @Description: 获取所有待导入人员信息 @param: @param
	 *         comid @param: @param batchid @param: @param
	 *         userid @param: @return @param: @throws ServiceException @return:
	 *         List<ImpCustomerInfoDTO> @throws
	 */
	private String getExamInfoForFlagList(ZybExamInfoUserDTO eu, long userid, String centernum, long batch_id,
			long com_id) throws ServiceException {
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
			} else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
					&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
				message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
				return message;
			} /*else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
					&& (!ei.getSex().trim().equals(eu.getSex()))) {
				message = "性别不一致，档案库为：" + ei.getSex().trim();
				return message;
			}*/ else if (cus != null && cus.getId() != ei.getId()) {
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

		if ((eu.getAge() < 18)) {
			message = "未满18岁不得进行职业病体检";
			return message;
		}

		if ((eu.getEmployeeage() <= 0) || (eu.getEmployeeage() > 65)) {
			message = "error-工龄无效";
			return message;
		}

		if ((eu.getDamage() <= 0) || (eu.getDamage() > 65)) {
			message = "error-接害工龄无效";
			return message;
		}

		if (StringUtil.isEmpty(eu.getJoinDatetime())) {
			message = "error-进厂日期无效";
			return message;
		}

		ExamInfoDTO ei = new ExamInfoDTO();
		if (eu.getId() > 0) {
			ei = this.getExamInfoForexamId(eu.getId());
		}else if (eu.getId_num() != null && !"".equals(eu.getId_num())) {
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
			if (eu.getCustomer_id() <= 0) {
				// 插入档案库
				eu.setArch_num(this.batchService.GetCreateID("vipno", centernum));
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
				ci.setIdtype(eu.getIdtype());
				long custmerid = insertCustomerInfo(connection, ci,centernum);
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
			if (eu.getId() <= 0) {

				// 体检信息表 插入
				ei.setExam_num(this.batchService.GetCreateID("exam_no", centernum));
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
				ei.setApptype("2");
				ei.setIs_need_barcode("N");
				ei.setIs_need_guide("N");
				ei.setZyb_set_source(eu.getZyb_set_source());
				ei.setVisit_no(eu.getVisit_no());
				int id = insertExamInfo(connection, ei);
				ei.setId(id);

				
				ExamInfoExt eie = new ExamInfoExt();
				eie.setArch_num(eu.getArch_num());
				eie.setCreate_time(DateTimeUtil.getDateTime());
				eie.setCreater(userid);
				eie.setDamage(eu.getDamage());
				eie.setEmployeeage(eu.getEmployeeage());
				eie.setExam_num(ei.getExam_num());
				eie.setJoinDatetime(eu.getJoinDatetime());
				eie.setOccusector(eu.getOccusector());
				eie.setOccusectorid(eu.getOccusectorid());
				eie.setOccutypeofwork(eu.getOccutypeofwork());
				eie.setOccutypeofworkid(eu.getOccutypeofworkid());
				eie.setUpdate_time(DateTimeUtil.getDateTime());
				eie.setUpdater(userid);
				insertExamInfoExt(connection, eie);
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
				ei.setApptype("2");
				ei.setIs_need_barcode(eu.getIs_need_barcode());
				ei.setIs_need_guide(eu.getIs_need_guide());
				ei.setVisit_no(eu.getVisit_no());
				updateExamInfo(connection, ei);
				
				ExamInfoExt eie = new ExamInfoExt();
				eie.setArch_num(eu.getArch_num());
				eie.setCreate_time(DateTimeUtil.getDateTime());
				eie.setCreater(userid);
				eie.setDamage(eu.getDamage());
				eie.setEmployeeage(eu.getEmployeeage());
				eie.setExam_num(ei.getExam_num());
				eie.setJoinDatetime(eu.getJoinDatetime());
				eie.setOccusector(eu.getOccusector());
				eie.setOccusectorid(eu.getOccusectorid());
				eie.setOccutypeofwork(eu.getOccutypeofwork());
				eie.setOccutypeofworkid(eu.getOccutypeofworkid());
				eie.setUpdate_time(DateTimeUtil.getDateTime());
				eie.setUpdater(userid);
				
				
				ExamInfoExtDTO eieold= new ExamInfoExtDTO();
				eieold = getExamInfoExtForExamNum(ei.getExam_num());
				if((eieold==null)||(StringUtil.isEmpty(eieold.getExam_num()))){
					insertExamInfoExt(connection, eie);
				}else{
					updateExamInfoExt(connection, eie);
				}
			
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
			message="ok-"+ei.getExam_num();
		} catch (Exception ex) {
			try {
				connection.rollback();
                message="error-事物被撤销";
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
	
	/**
	 * 
	     * @Title: saveOrUpdateExamInfoExt   
	     * @Description: 保存体检扩展信息表   
	     * @param: @param eu
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveOrUpdateExamInfoExt(ZybExamInfoUserDTO eu, long userid) throws ServiceException {
		Connection connection = null;
		String message = "error-保存失败";
		// 插入数据库
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			ExamInfoExt eie = new ExamInfoExt();
			eie.setArch_num(eu.getArch_num());
			eie.setCreate_time(DateTimeUtil.getDateTime());
			eie.setCreater(userid);
			eie.setDamage(eu.getDamage());
			eie.setEmployeeage(eu.getEmployeeage());
			eie.setExam_num(eu.getExam_num());
			eie.setJoinDatetime(eu.getJoinDatetime());
			eie.setOccusector(eu.getOccusector());
			eie.setOccusectorid(eu.getOccusectorid());
			eie.setOccutypeofwork(eu.getOccutypeofwork());
			eie.setOccutypeofworkid(eu.getOccutypeofworkid());
			eie.setUpdate_time(DateTimeUtil.getDateTime());
			eie.setUpdater(userid);
			eie.setEmployeemonth(eu.getEmployeemonth());
			eie.setDammonth(eu.getDammonth());
			ExamInfoExtDTO eieold = new ExamInfoExtDTO();
			eieold = getExamInfoExtForExamNum(eu.getExam_num());
			if ((eieold == null) || (StringUtil.isEmpty(eieold.getExam_num()))) {
				insertExamInfoExt(connection, eie);
				message="ok-保存成功";
			} else {
				updateExamInfoExt(connection, eie);
				message="ok-修改成功";
			}
			connection.commit();
		} catch (Exception ex) {
			try {
				connection.rollback();
				message = "error-事物被撤销";
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

	public List<ExamInfoDTO> getExamInfoForIdNumList(String id_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where id_num ='" + id_num + "' ";
		List<ExamInfoDTO> list = new ArrayList<ExamInfoDTO>();
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			list = map.getList();
		}

		return list;
	}

	public ExamInfoDTO getExamInfoForexamId(long id) throws ServiceException {
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
				+ " From  exam_info a,customer_info b where  a.id =" + id + " and a.customer_id=b.id  ";
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
	public int insertCustomerInfo(Connection connection, CustomerInfoDTO cif, String center_num) throws Exception {
		if("".equals(cif.getNation())){
			cif.setNation(null);
		}
		String sql = "insert into customer_info(arch_num,"
				+ "user_name,id_num,sex,birthday,nation,is_Active,creater,create_time,updater,update_time,membership_card,medical_insurance_card,phone,address,idtype,center_num) "
				+ "values ('" + cif.getArch_num() + "','" + cif.getUser_name() + "','" + cif.getId_num() + "','"
				+ cif.getSex() + "','" + cif.getBirthday() + "'," + cif.getNation() + ",'" + cif.getIs_Active() + "',"
				+ cif.getCreater() + ",'" + DateTimeUtil.getDateTime() + "'," + cif.getUpdater() + ",'"
				+ DateTimeUtil.getDateTime() + "','" + cif.getMembership_card() + "','"
				+ cif.getMedical_insurance_card() + "','" + cif.getPhone() + "','" + cif.getAddress() + "',"+cif.getIdtype()+",'"+center_num+"')";

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
		String sql = "update customer_info set user_name='" + cif.getUser_name() + "',sex='" + cif.getSex()
				+ "',birthday='" + cif.getBirthday() + "',nation=" + cif.getNation() + ",is_Active='"
				+ cif.getIs_Active() + "',id_num='" + cif.getId_num() + "',phone='" + cif.getPhone() + "',address='"
				+ cif.getAddress() + "',creater=" + cif.getCreater() + ",create_time='" + DateTimeUtil.getDateTime()
				+ "',updater=" + cif.getUpdater() + ",update_time='" + DateTimeUtil.getDateTime() + "',idtype="+cif.getIdtype()+" where id="
				+ cif.getId() + "";
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
				+ "employeeID,apptype,is_need_barcode,is_need_guide,zyb_set_source,visit_no,visit_date) "
				+ "values (" + cif.getGroup_id() + "," + cif.getCustomer_id() + ",'" + cif.getExam_num() + "','"
				+ cif.getExam_type() + "','" + cif.getIs_Active() + "','" + cif.getCenter_num() + "','"
				+ cif.getIs_marriage() + "'," + cif.getAge() + ",'" + cif.getAddress() + "','" + cif.getPhone() + "','"
				+ DateTimeUtil.getDateTime() + "','" + cif.getIs_after_pay() + "','" + cif.getAppointment() + "','"
				+ cif.getData_source() + "'," + cif.getCreater() + ",'" + DateTimeUtil.getDateTime() + "',"
				+ cif.getUpdater() + ",'" + DateTimeUtil.getDateTime() + "','" + cif.getStatus() + "','"
				+ cif.getIs_sampled_directly() + "','" + cif.getGetReportWay() + "'" + ",'" + cif.get_level() + "','"
				+ cif.getPosition() + "','" + cif.getCustomer_type() + "','" + cif.getRemarke() + "','"
				+ cif.getOthers() + "','" + cif.getGroup_index() + "','" + cif.getCompany() + "',"
				+ cif.getCustomer_type_id() + ",'" + cif.getChargingType() + "','" + cif.getReportAddress() + "','"
				+ cif.getEmail() + "'," + cif.getCompany_id() + "," + cif.getBatch_id() + ",'"
				+ cif.getPast_medical_history() + "','" + cif.getPicture_path() + "','" + cif.getEmployeeID() + "','" 
				+ cif.getApptype() + "','" + cif.getIs_need_barcode() + "','" + cif.getIs_need_guide() + "','" + cif.getZyb_set_source() + "',"
				+ "'"+cif.getVisit_no()+"','"+DateTimeUtil.getDate3()+"')";

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
	 * 更新体检表
	 * 
	 * @param cif
	 * @throws Exception
	 */
	public void updateExamInfo(Connection connection, ExamInfoDTO cif) throws Exception {
		String sql = "update exam_info set group_id=" + cif.getGroup_id() + ",customer_id=" + cif.getCustomer_id()
				+ ",exam_num='" + cif.getExam_num() + "'," + "exam_type='" + cif.getExam_type() + "',is_Active='"
				+ cif.getIs_Active() + "',center_num='" + cif.getCenter_num() + "',is_marriage='" + cif.getIs_marriage()
				+ "',age=" + cif.getAge() + ",address='" + cif.getAddress() + "',phone='" + cif.getPhone() + "',"
				+ "is_after_pay='" + cif.getIs_after_pay() + "',appointment='" + cif.getAppointment()
				+ "',data_source='" + cif.getData_source() + "',creater=" + cif.getCreater() + ",create_time='"
				+ DateTimeUtil.getDateTime() + "',updater=" + cif.getUpdater() + "," + "update_time='"
				+ DateTimeUtil.getDateTime() + "',status='" + cif.getStatus() + "',is_sampled_directly='"
				+ cif.getIs_sampled_directly() + "',getReportWay='" + cif.getGetReportWay() + "'," + "_level='"
				+ cif.get_level() + "',Position='" + cif.getPosition() + "',Customer_type='" + cif.getCustomer_type()
				+ "',Remarke='" + cif.getRemarke() + "',Others='" + cif.getOthers() + "',group_index='"
				+ cif.getGroup_index() + "',customer_type_id="
				+ cif.getCustomer_type_id() + ",chargingType='" + cif.getChargingType() + "',reportAddress='"
				+ cif.getReportAddress() + "',email='" + cif.getEmail() 
				+ "',past_medical_history='" + cif.getPast_medical_history()
				+ "',picture_path='" + cif.getPicture_path() + "',employeeID='" + cif.getEmployeeID() 
				+ "',apptype='"+cif.getApptype()+"',is_need_barcode='"+cif.getIs_need_barcode()
				+"',is_need_guide='"+cif.getIs_need_guide()+"',visit_no='"+cif.getVisit_no()+"',visit_date='"+DateTimeUtil.getDate3()+"' where id="
				+ cif.getId();
		
		connection.createStatement().executeUpdate(sql);
	}

	public void insertExamInfoExt(Connection connection, ExamInfoExt cif) throws Exception {
		String sql = "insert into exam_ext_info(exam_num,arch_num,occutypeofworkid,occusectorid,"
				+ "occusector,occutypeofwork,joinDatetime,employeeage,damage,remark,creater,create_time,updater,update_time,employeemonth,dammonth) "
				+ "values ('" + cif.getExam_num() + "','" + cif.getArch_num() + "','" + cif.getOccutypeofworkid()
				+ "','" + cif.getOccusectorid() + "','" + cif.getOccusector() + "','" + cif.getOccutypeofwork()  ;
				
				if("".equals(cif.getJoinDatetime())){
					sql += "',null," ;	
				}else{
					sql += "','"+cif.getJoinDatetime() + "'," ;	
				}
			sql	+= cif.getEmployeeage() + "," + cif.getDamage() + ",'',"
				+ cif.getCreater() + ",'" + cif.getCreate_time() + "'," + cif.getUpdater() + ",'" + cif.getUpdate_time()
				+ "'," + cif.getEmployeemonth() + ",'" + cif.getDammonth()
				+ "')";
		connection.createStatement().executeUpdate(sql);
	}

	public void updateExamInfoExt(Connection connection, ExamInfoExt cif) throws Exception {
		String sql = "update exam_ext_info set arch_num='" + cif.getArch_num() + "',occutypeofworkid='"
				+ cif.getOccutypeofworkid() + "',occusectorid='" + cif.getOccusectorid() + "',occusector='"
				+ cif.getOccusector() + "',occutypeofwork='" + cif.getOccutypeofwork() ;
				
				if("".equals(cif.getJoinDatetime())){
					sql += "',joinDatetime= null , ";
				}else{
					sql += "',joinDatetime='"+ cif.getJoinDatetime() + "',"	;
				}
				
		    sql+= "employeeage=" + cif.getEmployeeage() + ",damage=" + cif.getDamage()
				+ ",remark='',creater=" + cif.getCreater() + ",create_time='" + cif.getCreate_time() + "',updater="
				+ cif.getUpdater() + ",update_time='" + cif.getUpdate_time() +"',employeemonth='" + cif.getEmployeemonth()+"',dammonth='" + cif.getDammonth()
				+ "' where exam_num='" + cif.getExam_num()
				+ "'";
		connection.createStatement().executeUpdate(sql);
	}


	/**
	 * 
	     * @Title: getExamInfoExtForExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoExt      
	     * @throws
	 */
	public ExamInfoExtDTO getExamInfoExtForExamNum(String examnum) throws ServiceException {
		String sql = "SELECT a.exam_num,a.arch_num,a.occutypeofworkid,a.occusectorid,a.occusector,"
				+ "a.occutypeofwork,a.joinDatetime,a.employeeage,a.damage,a.remark,a.creater,"
				+ "a.create_time,a.updater,a.update_time,b.industry_name,c.typeofwork_name,a.dammonth,a.employeemonth "
				+ "  FROM exam_ext_info a"
				+ "  left join zyb_economicindustry b on b.industryID=a.occusectorid"
				+ "  left join zyb_typeofwork c on c.typeofworkID=a.occutypeofworkid "
				+ "where " + " exam_num='" + examnum + "' ";

		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoExtDTO.class);
		ExamInfoExtDTO webrole = new ExamInfoExtDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoExtDTO) map.getList().get(0);
			if((!StringUtil.isEmpty(webrole.getJoinDatetime())&&(webrole.getJoinDatetime().trim().length()>10))){
				webrole.setJoinDatetime(webrole.getJoinDatetime().trim().substring(0,10));
			}
		}

		return webrole;
	}

	/**
	 * 
	     * @Title: getExamInfoUserForExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybExamInfoUserDTO      
	     * @throws
	 */
	public ZybExamInfoUserDTO getExamInfoUserForExamId(String exam_num,String center_num)throws ServiceException {
		ZybExamInfoUserDTO ei = new ZybExamInfoUserDTO();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.id as customer_id,a.nation,a.user_name,a.arch_num,a.id_num,"
				+ "a.sex,c.age,c.exam_num,c.is_marriage,c.exam_type,"
				+ "c.position,c._level,c.group_id,c.email,c.chargingType,c.remarke,"
				+ "c.others,c.status,c.phone,c.customer_type,c.customer_type_id,a.address");
		sb.append(",c.group_id,c.group_index,a.birthday,c._level,c.status,c.register_date"
				+ ",c.join_date,c.exam_times,c.exam_num,c.company,c.past_medical_history,"
				+ "c.picture_Path,c.is_after_pay,c.batch_id,n.batch_name,"
				+ "c.employeeID,c.mc_no,c.visit_date,c.visit_no,c.clinic_no,c.exam_indicator,c.batch_id,"
				+ "c.company_id,m.damage,m.employeeage,m.occusector,m.occusectorid,"
				+ "m.occutypeofwork,m.occutypeofworkid,m.joinDatetime,c.zyb_set_source,m.employeemonth,m.dammonth  ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join batch  n on n.id=c.batch_id ");
		sb.append(" left join exam_ext_info  m on m.exam_num=c.exam_num ");
		sb.append(" where c.customer_id=a.id  ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and a.is_Active='Y' ");
		sb.append(" and c.exam_num='"+exam_num+"' and c.center_num='"+center_num+"' ");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 1000, ZybExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			ei = (ZybExamInfoUserDTO) map.getList().get(0);
			ei.setBatch_name(ei.getBatch_name() + "(" + ei.getCompany() + ")");
			if ((ei.getRegister_date() != null) && (ei.getRegister_date().trim().length() > 10))
				ei.setRegister_date(ei.getRegister_date().substring(0, 10));
			ei.setExamcount(getExamInfotime(ei.getCustomer_id()));
			if (!StringUtil.isEmpty(ei.getOccutypeofworkid())) {
				ZybTypeOfWork gz = new ZybTypeOfWork();
				gz = zyb_occuService.queryByIdTypeOfWork(ei.getOccutypeofworkid());
				if (gz != null)
					ei.setCygzname(gz.getTypeofwork_name());
			}
			if (!StringUtil.isEmpty(ei.getOccusectorid())) {
				ZybEconomicIndustry hy = new ZybEconomicIndustry();
				hy = zyb_occuService.findZybEconomicIndustryById(ei.getOccusectorid());
				if (hy != null)
					ei.setCyhyname(hy.getIndustry_name());
			}
		}
		return ei;
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
	     * @Title: saveZybOccuHis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public ZybOccuHis saveZybOccuHis(ZybOccuHis zybhis)throws ServiceException{
		this.pm.save(zybhis);
		return zybhis;
	}
	
	/**
	 * 
	     * @Title: longZybOccuHis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public ZybOccuHis longZybOccuHis(String id)throws ServiceException{
		return (ZybOccuHis)qm.load(ZybOccuHis.class, id);
	}
	
	/**
	 * 
	     * @Title: delZybOccuHis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void delZybOccuHis(ZybOccuHis zybhis)throws ServiceException{
		this.pm.remove(zybhis);
	}
	
	/**
	 * 
	     * @Title: getexamOcchazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ZybExamOccuhazardfactorsDTO>      
	     * @throws
	 */
	public List<ZybExamOccuhazardfactorsDTO> getexamOcchazardfactors(String exam_num)throws ServiceException{
		String sql = "select a.id,a.exam_num,a.arch_num,a.hazard_code,n.hazardfactorsID,a.occuphyexaclassid,"
				+ "a.isActive,a.creater,a.create_time,a.remark,n.hazard_name,"
				+ "m.occuphyexaclass_name,n.hazard_year,n.hazard_desc,n.deffect "
				+ "from zyb_exam_occuhazardfactors a"
				+ "  left join zyb_occuhazardfactors  n on n.hazard_code=a.hazard_code "
				+ "  left join  zyb_occuphyexaclass  m on m.occuphyexaclassid=a.occuphyexaclassid "
				+ " where a.exam_num='" + exam_num	+ "' and a.isActive ='Y' ";

		PageSupport map = this.jqm.getList(sql, 1, 1000, ZybExamOccuhazardfactorsDTO.class);
		List<ZybExamOccuhazardfactorsDTO> webrole = new ArrayList<ZybExamOccuhazardfactorsDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole =  map.getList();
		}
		return webrole;
	}
	
	public PageReturnDTO getexamOcchazardfactors(String exam_num, int pagesize, int pageno)throws ServiceException{
		String sql = "select a.id,a.exam_num,a.arch_num,a.hazard_code,n.hazardfactorsID,a.occuphyexaclassid,"
				+ "a.isActive,a.creater,a.create_time,a.remark,n.hazard_name,"
				+ "m.occuphyexaclass_name,a.hazard_year,n.hazard_desc,n.deffect "
				+ "from zyb_exam_occuhazardfactors a"
				+ "  left join zyb_occuhazardfactors  n on n.hazard_code=a.hazard_code "
				+ "  left join  zyb_occuphyexaclass  m on m.occuphyexaclassid=a.occuphyexaclassid "
				+ " where a.exam_num='" + exam_num	+ "' and a.isActive ='Y' ";
		
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ZybExamOccuhazardfactorsDTO.class);
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
	     * @Title: getzywhlblist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getzywhlblist()throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("select hazardclassID,hazardclass_code,hazardclass_name"
				+ " from zyb_occuhazardclass order by [order]  ");
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("hazardclassID"));
				jd.setText(rs.getString("hazardclass_name"));
				jd.setAttributes(rs.getString("hazardclass_code"));
				list.add(jd);
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
	
	
	/**
	 * 
	     * @Title: getzybphysicalexaminationclasslist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getzybphysicalexaminationclasslist()throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("select occuphyexaclassid,occuphyexaclass_name"
				+ " from zyb_occuphyexaclass order by [order]  ");
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("occuphyexaclassid"));
				jd.setText(rs.getString("occuphyexaclass_name"));
				jd.setAttributes(rs.getString("occuphyexaclassid"));
				list.add(jd);
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
	/**
	 * 
	     * @Title: getzywhlblist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TreeDTO>      
	     * @throws
	 */
	public List<TreeDTO> getzywhyslblist(String hazardclassID)throws ServiceException {
		StringBuffer sqltext= new StringBuffer();
		sqltext.append("select hazardfactorsID,hazard_code,hazard_name"
				+ " from zyb_occuhazardfactors where hazardclassID='"+hazardclassID+"' order by [order] ");
		Connection connection = null;
		Statement statement = null;
		int count = 0;
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext.toString());
			while (rs.next()) {
				TreeDTO jd =new TreeDTO();
				jd.setId(rs.getString("hazard_code"));
				jd.setText(rs.getString("hazard_name"));
				jd.setAttributes(rs.getString("hazardfactorsID"));
				list.add(jd);
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
	
	/**
	 * 
	     * @Title: saveZybExamOccuhazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public ZybExamOccuhazardfactors saveZybExamOccuhazardfactors(ZybExamOccuhazardfactors zybhis,UserDTO user,String app_type,String center_num)throws ServiceException{
		//this.pm.save(zybhis);
		saveWeihaiDaiTaocan(zybhis,user,app_type,center_num);
		return zybhis;
	}
	public void saveWeihaiDaiTaocan(ZybExamOccuhazardfactors ex,UserDTO user,String apptype,String center_num)throws ServiceException{
		String exam_info_sql = " SELECT * FROM  exam_info where  exam_num='"+ex.getExam_num()+"'";
		List<ExamInfoDTO> examinf = this.jqm.getList(exam_info_sql,ExamInfoDTO.class);
		ExamInfoDTO exam = new ExamInfoDTO();
		
		if(examinf!=null && examinf.size()>0){
			exam = examinf.get(0);
		}
		
		ZybExamOccuhazardfactors z = new ZybExamOccuhazardfactors();
		z.setArch_num(ex.getArch_num());
		z.setExam_num(ex.getExam_num());
		z.setHazard_year(ex.getHazard_year());
		z.setHazard_code(ex.getHazard_code());
		z.setOccuphyexaclassid(ex.getOccuphyexaclassid());
		z.setIsActive("Y");
		z.setCreate_time(DateTimeUtil.getDate3());
		z.setCreater(user.getUserid());
		z.setRemark("");
		this.pm.save(z);
		
		if(exam.getZyb_set_source() == 1) {
			return;
		}
		//7.1放射性健康检查
		//7.1类别因素关系获取套餐
		//ii.getExam_type();体检类别
		//zybOccuhazardfactor.getId();//放射因素ID
		String ss_sql = "select hazardfactorsID,hazard_code,hazard_name from zyb_occuhazardfactors  where hazard_code='"+ex.getHazard_code()+"'";
		List<ZybOccuhazardfactorsDTO> lis = this.jqm.getList(ss_sql, ZybOccuhazardfactorsDTO.class);
		
		String set_sql = " SELECT s.id FROM  exam_set s  WHERE  s.hazardfactorsid = '"+lis.get(0).getHazardfactorsID()+"' AND   s.occuphyexaclassid='"+ex.getOccuphyexaclassid()+"' and s.center_num='"+center_num+"' ";
		List<ExamSetDTO> set_li = this.jqm.getList(set_sql,ExamSetDTO.class);
		
		ExamSetDTO  se = new ExamSetDTO();
		if(set_li!=null && set_li.size()>0){
			//7.2插入套餐
			for (int i = 0 ; i < set_li.size() ; i ++ ) {
				String essql = "  FROM ExaminfoSet  e where   e.exam_num = '"+exam.getExam_num()+"'  AND    isActive='Y'  AND  e.exam_set_id = '"+set_li.get(i).getId()+"'  and app_type='2' ";
				List<ExaminfoSet> setli = this.qm.find(essql);
				if(setli!=null && setli.size()>0){
					System.out.println("套餐冲突!");
				} else {
					ExaminfoSet et  = new ExaminfoSet();
					et.setExaminfo_id(exam.getId());
					et.setExam_set_id(set_li.get(i).getId());
					et.setExam_indicator(exam.getExam_type());
					et.setDiscount(set_li.get(i).getSet_discount());
					et.setAmount(set_li.get(i).getSet_amount());
					et.setIsActive("Y");
					et.setFinal_exam_date(DateTimeUtil.parse());
					et.setIs_new_added(0);
					et.setCreater(user.getUserid());
					et.setCreate_time(DateTimeUtil.parse());
					et.setUpdate_time(DateTimeUtil.parse());
					et.setUpdater(user.getUserid());
					et.setApp_type(apptype);
					et.setExam_num(exam.getExam_num());
					this.pm.save(et);
				}
			}
			
			//7.3
			//插入项目
			StringBuffer bf = new StringBuffer();
			String item_hql = "  FROM SetChargingItem  s  where   s.exam_set_id='"+set_li.get(0).getId()+"'";
			List<SetChargingItem>  lll =  this.qm.find(item_hql);

			  
			for (SetChargingItem s : lll) {
				String esi = "  FROM ExaminfoChargingItem  e where   e.exam_num = '"+exam.getExam_num()+"'  and e.center_num = '"+user.getCenter_num()+"' AND    isActive='Y'  AND  e.charging_item_code = '"+s.getCharging_item_code()+"'  and app_type='2' ";
				List<ExaminfoChargingItem> ei_set = this.qm.find(esi);
				List<SetChargingItemDTO> esili = new ArrayList<SetChargingItemDTO>();
				if(ei_set!=null && ei_set.size()>0){
					//项目已存在不添加
				} else {
					ExaminfoChargingItem it = new  ExaminfoChargingItem();
					it.setAmount(s.getAmount());
					//it.setApp_type(apptype);
					it.setChange_item("N");
					it.setApp_type(s.getApptype());
					it.setCharge_item_id(s.getCharging_item_id());
					it.setCheck_status(0);
					it.setCreate_time(DateTimeUtil.parse());
					it.setCreater(user.getUserid());
					it.setDiscount(s.getDiscount());
					it.setExam_indicator(exam.getExam_type());
					it.setExam_status("N");
					it.setExaminfo_id(exam.getId());
					it.setFinal_exam_date(DateTimeUtil.parse());
					it.setHis_req_status("N");
					/*it.setId(id);*/
					it.setIs_application("N");
					it.setIs_new_added(0);
					it.setIsActive("Y");
					it.setItem_amount(it.getAmount());
					it.setTeam_pay(it.getAmount());
					it.setItemnum(1);
					it.setPay_status("R");
					it.setUpdate_time(DateTimeUtil.parse());
					it.setUpdater(user.getUserid());
					it.setExam_num(exam.getExam_num());
					it.setCharging_item_code(s.getCharging_item_code());
					it.setCenter_num(user.getCenter_num());
					this.pm.save(it);
					
					/**
					 * *
					 * *6.3.0//项目写入lis申请表
					 * 
					 */
					//6.3.1获取收费项目
					List<ChargingItemDTO> c_li = jqm.getList("SELECT item_code FROM  charging_item   where  item_code = '"+s.getCharging_item_code()+"'", ChargingItemDTO.class);
					ChargingItemDTO cii = this.batchService.findChargeItemById(c_li.get(0).getItem_code());
					//6.3.2判断是否是lis检查项目
					int lisnum = zybCustomerInfoService.checkZybListItem(cii.getItem_code(), user.getCenter_num());
					if (lisnum > 0) {
						SampleExamDetail sd = new SampleExamDetail();
						sd = zybCustomerInfoService.getZybSampleExamDetailforExamid(ex.getExam_num(), cii.getSam_demo_id(), "W");
						long samexam_id = 0;
						String sample_barcode = "";
						if ((sd == null) || (sd.getId() <= 0)) {
							sample_barcode = this.batchService.GetCreateID("barcode", user.getCenter_num());
							SampleExamDetail sed = new SampleExamDetail();
							sed.setExam_num(exam.getExam_num());
							sed.setExam_info_id(exam.getId());
							sed.setSample_id(cii.getSam_demo_id());
							sed.setSample_barcode(sample_barcode);
							sed.setStatus("W");
							sed.setPic_path("");
							sed.setCenter_num(user.getCenter_num());
							sed.setCreater(user.getUserid());
							sed.setCreate_time(DateTimeUtil.parse());
							sed.setUpdater(user.getUserid());
							sed.setUpdate_time(DateTimeUtil.parse());
							sed.setExam_num(ex.getExam_num());
							zybCustomerInfoService.saveZybSampleExamDetail(sed);
							samexam_id = sed.getId();
						} else {
							samexam_id = sd.getId();
							sample_barcode = sd.getSample_barcode();
						}
						ExamResultChargingItem erci = new ExamResultChargingItem();
						erci.setCharging_id(s.getCharging_item_id());
						erci.setExam_id(samexam_id);
						erci.setResult_type(CommSet.lis_sample);
						erci.setIsActive("Y");
						erci.setCreater(user.getUserid());
						erci.setCreate_time(DateTimeUtil.parse());
						erci.setUpdater(user.getUserid());
						erci.setUpdate_time(DateTimeUtil.parse());
						erci.setBar_code(sample_barcode);
						erci.setCharging_item_code(cii.getItem_code());
						zybCustomerInfoService.saveZybExamResultChargingItem(erci);
					}
					
					/**
					 * 
					 * 6.4.0项目写入pass申请表
					 *
					 */
					//6.4.1//判断是否是pass项目
					int pacsnum = zybCustomerInfoService.checkZybPacsItem(c_li.get(0).getItem_code(), user.getCenter_num());
					if (pacsnum > 0) {
						long summary_id = 0;
						PacsSummary psd = new PacsSummary();
						psd = zybCustomerInfoService.getZybPacsSummaryDTOforExamid(exam.getExam_num(), cii.getSam_demo_id(), "N");
						//6.4.2写入pass申请主表
						String pacs_req_num="";
						if ((psd == null) || (psd.getId() <= 0)) {
							PacsSummary ps = new PacsSummary();
							ps.setExaminfo_num(exam.getExam_num());
							ps.setExaminfo_name(exam.getUser_name());
							ps.setExaminfo_sex(exam.getSex());

							if ((!StringUtil.isEmpty(exam.getBirthday())) && (exam.getBirthday().trim().length() == 10)) {
								Date da = Timeutils.strToDateLong2(exam.getBirthday().trim().replaceAll("-", ""));
								ps.setExaminfo_birthday(da);
							} else if ((!StringUtil.isEmpty(exam.getBirthday()))
									&& (exam.getBirthday().trim().length() == 8)) {
								Date da = Timeutils.strToDateLong2(exam.getBirthday().trim());
								ps.setExaminfo_birthday(da);
							}
							
							ps.setApply_person(user.getUsername());
							ps.setApply_date(DateTimeUtil.parse());
							ps.setExam_status("N");
							ps.setCreater(user.getUserid());
							ps.setCreate_time(DateTimeUtil.parse());
							ps.setUpdater(user.getUserid());
							ps.setUpdate_time(DateTimeUtil.parse());
							ps.setExaminfo_sampleId(cii.getSam_demo_id());
							pacs_req_num=this.batchService.GetCreateID("pacs_req_num", user.getCenter_num());
							ps.setPacs_req_code(pacs_req_num);
							zybCustomerInfoService.saveZybPacsSummary(ps);
							summary_id = ps.getId();
						} else {
							summary_id = psd.getId();
							pacs_req_num=psd.getPacs_req_code();
						}
						Pacsdetail pd = new Pacsdetail();
						pd.setExaminfo_num(exam.getExam_num());
						pd.setSummary_id(summary_id);
						pd.setChargingItem_num(cii.getItem_code());
						pd.setChargingItem_name(cii.getItem_name());
						DepartmentDep dd = departmentService.loadDepartmentDep(cii.getDep_id());
						pd.setDep_num(dd.getDep_num());
						pd.setDep_name(dd.getDep_name());
						pd.setExaminfo_sampleId(cii.getSam_demo_id());
						pd.setIs_need_return("Y");
						pd.setCreater(user.getUserid());
						pd.setCreate_time(DateTimeUtil.parse());
						pd.setUpdater(user.getUserid());
						pd.setUpdate_time(DateTimeUtil.parse());
						pd.setPacs_req_code(pacs_req_num);
						zybCustomerInfoService.saveZybPacsdetail(pd);
					}
					
				}
			}
			
		}
	}
	
	/**
	 * 
	     * @Title: delZybExamOccuhazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param zybhis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybOccuHis      
	     * @throws
	 */
	public void delZybExamOccuhazardfactors(ZybExamOccuhazardfactors zybhis)throws ServiceException{
		this.pm.remove(zybhis);
	}
	
	/**
	 * 
	     * @Title: longZybExamOccuhazardfactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybExamOccuhazardfactors      
	     * @throws
	 */
	public ZybExamOccuhazardfactors longZybExamOccuhazardfactors(String id)throws ServiceException{
		return (ZybExamOccuhazardfactors)qm.load(ZybExamOccuhazardfactors.class, id);
	}
	
	public ExamSetDTO getOccuhazardfactorsExamSet (String weihai,String tjlx,String center_num) throws ServiceException{
		String sql = " SELECT hazardfactorsID  FROM  zyb_occuhazardfactors  where   hazard_code='"+weihai+"' ";
		List<ZybOccuhazardfactors> li = this.jqm.getList(sql,ZybOccuhazardfactors.class);
		String set_sql = " SELECT s.id,s.set_num FROM  exam_set s  WHERE  s.hazardfactorsid = '"+li.get(0).getHazardfactorsID()+"' AND   s.occuphyexaclassid='"+tjlx+"' and center_num='"+center_num+"' ";
		List<ExamSetDTO> set_li = this.jqm.getList(set_sql,ExamSetDTO.class);
		if(set_li.size() > 0){
			return set_li.get(0);
		}
		return null;
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
	public List<ExaminfoChargingItemDTO> getZybExaminfoChargingItemAllforExamId(String exam_num, String center_num)throws ServiceException {
		String sql = "select a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,a.updater,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,a.itemnum,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.app_type,a.his_req_status "
				+ "from examinfo_charging_item a,charging_item b,department_dep c  "
				+ "where a.charging_item_code=b.item_code and a.isActive='Y' and b.dep_id=c.id "
				+ "and a.pay_status<>'M' and a.center_num = '"+center_num+"' and a.exam_num ='"+exam_num+"' "
						+ "order by a.app_type desc,a.exam_indicator,a.is_new_added desc,b.item_seq,a.update_time desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExaminfoChargingItemDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("登记台登记页面-收费项目列表", sql);
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
				}else if("T".equals(eci.getExam_indicator())){
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					} else if("R".equals(eci.getPay_status())){
						eci.setPay_statuss("预付费");
					}					
				}else if("M".equals(eci.getExam_indicator())){
					eci.setPay_statuss("免费");					
				}else if("GT".equals(eci.getExam_indicator())){
					if("Y".equals(eci.getPay_status())){
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
	     * <p>Title: getZybExaminfoChargingItemforExamId</p>   
	     * <p>Description: </p>   
	     * @param
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.zyb.service.ZybCustomerInfoService-getZybExaminfoChargingItemforExamId(long)
	 */
	/*public List<ExaminfoChargingItemDTO> getZybExaminfoChargingItemforExamId(long examid)throws ServiceException {
		String sql = "select a.id,a.examinfo_id,a.charge_item_id,a.exam_indicator,"
				+ "a.item_amount,a.discount,a.amount,a.isActive,a.final_exam_date,"
				+ "a.pay_status,a.exam_status,a.is_new_added,a.exam_date,a.creater,"
				+ "a.create_time,a.updater,a.update_time,a.check_status,a.exam_doctor_id,"
				+ "a.exam_doctor_name,a.add_status,a.calculation_amount,a.is_application,"
				+ "a.change_item,a.team_pay,a.personal_pay,a.team_pay_status,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name,a.app_type "
				+ "from examinfo_charging_item a,charging_item b,department_dep c  "
				+ "where a.charge_item_id=b.id and a.isActive='Y' and a.app_type='2' and b.dep_id=c.id "
				+ "and a.pay_status<>'M' and a.center_num = '"+center_num+"' and a.examinfo_id ="+examid+" order by a.update_time desc";
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
				}else if("T".equals(eci.getExam_indicator())){
					eci.setPay_statuss("预付费");					
				}else if("M".equals(eci.getExam_indicator())){
					eci.setPay_statuss("免费");					
				}else if("GT".equals(eci.getExam_indicator())){
					if("Y".equals(eci.getPay_status())){
						eci.setPay_statuss("已付费");
					}else if("N".equals(eci.getPay_status())){
						eci.setPay_statuss("未付费");
					}				
				}
				es.add(eci);	
			}
			
		}
		return es;
	}*/
	
	public PageReturnDTO getZybExamSetAllList(String exam_num, int pagesize, int pageno,String center_num) throws ServiceException {
		String sql = "select a.set_num,a.set_name,a.set_pinyin,a.sex,"
				+ "a.set_discount,a.set_amount,b.id,b.examinfo_id,b.exam_set_id,"
				+ "b.discount,b.amount,b.isActive,b.final_exam_date,b.creater,"
				+ "b.create_time,b.updater,b.update_time,b.app_type"
				+ " From  exam_set a,examinfo_set b where a.is_Active='Y' and a.center_num='"+center_num+"' and b.isActive='Y' "
				+ "  and a.id=b.exam_set_id and b.exam_num='"+exam_num+"' "
				+ "and a.id=b.exam_set_id order by b.is_new_added,b.update_time desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ExaminfoSetDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getGroupSetList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param group_id
	     * @param: @param pagesize
	     * @param: @param pageno
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getZybExamSetList(String exam_num,String app_type, int pagesize, int pageno,String center_num) throws ServiceException {
		String sql = "select b.exam_indicator,b.exam_indicator as exam_indicators,a.set_num,a.set_name,a.set_pinyin,a.sex,"
				+ "a.set_discount,a.set_amount,b.id,b.examinfo_id,b.exam_set_id,"
				+ "b.discount,b.amount,b.isActive,b.final_exam_date,b.creater,"
				+ "b.create_time,b.updater,b.update_time,b.app_type"
				+ " From  exam_set a,examinfo_set b where a.is_Active='Y' and a.center_num='"+center_num+"' and b.isActive='Y' and b.app_type='"+app_type+"' "
				+ "  and a.id=b.exam_set_id and b.exam_num='"+exam_num+"' "
				+ "and a.id=b.exam_set_id order by b.is_new_added,b.update_time desc";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, ExaminfoSetDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setTotal(map.getRecTotal());
		webrole.setRows(map.getList());
		return webrole;
	}
	
	/**
	 * 
	 * @Title:
	 * getExamInfoForarchnumList @Description:通过arch_num获取人员基本信息 @param: @param
	 * arch_num @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public ExamInfoDTO getZybCustExamInfoForexamId(String exam_num) throws ServiceException {
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
	     * @Title: getExamInfoSetForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoSet>      
	     * @throws
	 */
	public List<ExaminfoSet> getZybExamInfoSetForId(String exam_num)throws ServiceException {
		String hql = " from ExaminfoSet g where g.exam_num='" + exam_num + "' and isActive='Y' ";
		List<ExaminfoSet> list= new ArrayList<ExaminfoSet>();
		list = qm.find(hql);
		return list;
	}
	
	/**
	 * 
	     * @Title: getIsnewaddedExamInfoSetForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getZybIsnewaddedExamInfoSetForId(long exam_id)throws ServiceException {
		Connection connection = null;
		int newadd =0;
		try {
			connection = this.jqm.getConnection();
			String sql  = "select MAX(a.is_new_added) as maxcount from examinfo_set a where a.isActive='Y' and a.examinfo_id="+exam_id;
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				String con = rs.getString("maxcount");
				if((con==null)||("null".equals(con))){
					newadd=0;
				}else{
					newadd=rs.getInt("maxcount")+1;
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
		return newadd;
	}
	
	/**
	 * 
	     * @Title: saveExaminfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoSet      
	     * @throws
	 */
	public ExaminfoSet saveZybExaminfoSet(ExaminfoSet eis)throws ServiceException {
		this.pm.save(eis);
		return eis;
	}
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItem>      
	     * @throws
	 */
	public List<ExaminfoChargingItem> getZybExaminfoChargingItemForId(String exam_num, String center_num)throws ServiceException {
		String hql = " from ExaminfoChargingItem g where g.exam_num='" + exam_num + "' and g.exam_status<>'G' and g.center_num = '"+center_num+"' and g.pay_status<>'M' and isActive='Y' ";
		List<ExaminfoChargingItem> list= new ArrayList<ExaminfoChargingItem>();
		list = qm.find(hql);
		return list;
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
	public int getZybIsnewaddedExamInfoItemForId(String exam_num, String center_num)throws ServiceException {
		Connection connection = null;
		int newadd =0;
		try {
			connection = this.jqm.getConnection();
			String sql  = "select MAX(is_new_added) as maxcount from examinfo_charging_item a "
					+ "where a.isActive='Y' and a.pay_status<>'M' and a.exam_status<>'G' and a.center_num = '"+center_num+"' "
							+ "and a.exam_num='"+exam_num+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				String con = rs.getString("maxcount");
				if((con==null)||("null".equals(con))){
					newadd=0;
				}else{
					newadd=rs.getInt("maxcount")+1;
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
		return newadd;
	}
	
	/**
	 * 
	     * @Title: saveExaminfoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItem      
	     * @throws
	 */
	public ExaminfoChargingItem saveZybExaminfoChargingItem(ExaminfoChargingItem eis)throws ServiceException {
		this.pm.save(eis);
		return eis;
	}

	/**
	 * 
	     * @Title: saveSampleExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetail      
	     * @throws
	 */
	public SampleExamDetail saveZybSampleExamDetail(SampleExamDetail eis)throws ServiceException {
		this.pm.save(eis);
		return eis;
	}
	
	/**
	 * 
	     * @Title: saveZybExamResultChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamResultChargingItem      
	     * @throws
	 */
	public ExamResultChargingItem saveZybExamResultChargingItem(ExamResultChargingItem eis)throws ServiceException {
		this.pm.save(eis);
		return eis;
	}
	
	/**
	 * 
	     * @Title: saveZybPacsSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PacsSummary      
	     * @throws
	 */
	public PacsSummary saveZybPacsSummary(PacsSummary eis)throws ServiceException {
		this.pm.save(eis);
		return eis;
	}
	
	/**
	 * 
	     * @Title: saveZybPacsdetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Pacsdetail      
	     * @throws
	 */
	public Pacsdetail saveZybPacsdetail(Pacsdetail eis)throws ServiceException {
		this.pm.save(eis);
		return eis;
	}
	
	/**
	 * 
	     * @Title: checkZybListItem   
	     * @Description: 判断是否有 lis 检查项目 
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
   public int checkZybListItem(String item_code, String center_num ) throws ServiceException{
		int countnum=0;
		StringBuffer sql = new StringBuffer("select count(*) as num from (select a.item_code,b.item_num from charging_item a,"
				+ "examination_item b,charging_item_exam_item c,department_dep d,department_vs_center de where a.item_code=c.charging_item_code "
				+ " and de.dep_id = d.id  and b.item_num=c.item_code and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id "
				+ "and de.center_num ='"+center_num+"'  and d.dep_category='131' and a.item_code ='"+item_code+"') m ");
		//System.out.println(sql.toString());
		Connection connection = null;
		Statement statement = null;
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
        * @Title: checkZybPacsItem   
        * @Description: 检查pacs
        * @param: @param item_code
        * @param: @return
        * @param: @throws ServiceException      
        * @return: int      
        * @throws
    */
  public int checkZybPacsItem(String item_code, String center_num ) throws ServiceException{
		int countnum=0;
		StringBuffer sql = new StringBuffer("select count(*) as num from (select a.item_code,b.item_num from charging_item a,"
				+ "examination_item b,charging_item_exam_item c,department_dep d,department_vs_center de where a.item_code=c.charging_item_code "
				+ " and de.dep_id = d.id   and b.item_num=c.item_code and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id "
				+ "and de.center_num ='"+center_num+"'  and d.dep_category='21' and a.item_code ='"+item_code+"' ) m ");
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
	     * @Title: getZybSampleExamDetailforExamid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: SampleExamDetailDTO      
	     * @throws
	 */
	public SampleExamDetail getZybSampleExamDetailforExamid(String exam_num,long samp_id,String status) throws ServiceException{
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
	     * @Title: getZybPacsSummaryDTOforExamid   
	     * @Description: 获取pacs申请主表信息   
	     * @param: @param examinfo_num
	     * @param: @param status
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PacsSummaryDTO      
	     * @throws
	 */
	public PacsSummary getZybPacsSummaryDTOforExamid(String examinfo_num,long sam_id,String status) throws ServiceException{
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
	     * @Title: zybaddExamInfoChangItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param esdto
	     * @param: @param listitem
	     * @param: @param listset
	     * @param: @param indicator
	     * @param: @param userid
	     * @param: @param username
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String zybaddExamInfoChangItem(ExaminfoSetDTO esdto, List<GroupChargingItemDTO> listitem,
			List<ExamSetDTO> listset, String indicator,String IS_CHARGINGWAY_ZERO, long userid, String username, String centerNum)
			throws ServiceException {
		ExamInfoDTO ed = this.getZybCustExamInfoForexamId(esdto.getExam_num());
		String message = "error-";
		if (StringUtil.isEmpty(esdto.getExam_num())) {
			message = "error-无效体检人员";
		}else{
			// 删除人员对应套餐
			List<ExaminfoSet> list = new ArrayList<ExaminfoSet>();
			list = this.getZybExamInfoSetForId(esdto.getExam_num());
			int isaddsetcount = getZybIsnewaddedExamInfoSetForId(esdto.getExaminfo_id());

			// txManager.commit(txStatus);//事务处理成功，提交当前事务
			// 增加人员对应套餐
			for (int j = 0; j < listset.size(); j++) {
				ExamSetDTO es = new ExamSetDTO();
				es = listset.get(j);
				ExamSetDTO oldExamSetDTO = this.batchService.findSetById(es.getSet_num(),centerNum);
				int flagset = 0;
				for (ExaminfoSet oldExaminfoSet : list) {
					if (oldExamSetDTO.getId() == oldExaminfoSet.getExam_set_id()) {
						flagset = 1;
						break;
					}
				}

				if (flagset == 0) {
					// 新增 ExaminfoSet、
					ExaminfoSet newExaminfoSet = new ExaminfoSet();
					newExaminfoSet.setExaminfo_id(esdto.getExaminfo_id());
					newExaminfoSet.setExam_set_id(oldExamSetDTO.getId());
					newExaminfoSet.setAmount(es.getSet_amount());
					newExaminfoSet.setExam_indicator(indicator);
					newExaminfoSet.setCreater(userid);
					newExaminfoSet.setExam_set_id(oldExamSetDTO.getId());
					newExaminfoSet.setCreate_time(DateTimeUtil.parse());
					newExaminfoSet.setDiscount(es.getSet_discount());
					// newExaminfoSet.setExam_set_id(es.getId());
					newExaminfoSet.setIsActive("Y");
					newExaminfoSet.setFinal_exam_date(DateTimeUtil.parse());
					newExaminfoSet.setIs_new_added(isaddsetcount);
					newExaminfoSet.setUpdater(userid);
					newExaminfoSet.setApp_type("2");
					newExaminfoSet.setExam_num(esdto.getExam_num());
					newExaminfoSet.setUpdate_time(DateTimeUtil.parse());
					this.saveZybExaminfoSet(newExaminfoSet);
					
					List<SetOccuhazardfactors> listocc = this.qm.find("from SetOccuhazardfactors s where s.set_id = '"+oldExamSetDTO.getId()+"'");
					for (SetOccuhazardfactors setOccuhazardfactors : listocc) {
						List<ZybExamOccuhazardfactors> listexamocc = this.qm.find("from ZybExamOccuhazardfactors z where z.exam_num = '"+ed.getExam_num()+"' and z.hazard_code = '"+setOccuhazardfactors.getHazard_code()+"'");
						if(listexamocc.size() <= 0){
							ZybExamOccuhazardfactors examOcc = new ZybExamOccuhazardfactors();
							
							examOcc.setArch_num(ed.getArch_num());
							examOcc.setExam_num(ed.getExam_num());
							examOcc.setHazard_code(setOccuhazardfactors.getHazard_code());
							examOcc.setHazard_year(setOccuhazardfactors.getHazard_year());
							examOcc.setOccuphyexaclassid(setOccuhazardfactors.getOccuphyexaclassID());
							examOcc.setIsActive("Y");
							examOcc.setCreater(userid);
							examOcc.setCreate_time(DateTimeUtil.getDate3());
							this.pm.save(examOcc);
						}
					}
				}
			}
			// 人员套餐维护完毕

			List<ExaminfoChargingItem> itemlist = new ArrayList<ExaminfoChargingItem>();
			itemlist = this.getZybExaminfoChargingItemForId(esdto.getExam_num(), centerNum);
			int isaddcount = getZybIsnewaddedExamInfoItemForId(esdto.getExam_num(), centerNum);

			for (int j = 0; j < listitem.size(); j++) {
				GroupChargingItemDTO es = new GroupChargingItemDTO();
				es = listitem.get(j);
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
										
					if("T".equals(es.getExam_indicators()))
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
					eci.setCreater(userid);
					eci.setCreate_time(DateTimeUtil.parse());
					eci.setUpdater(userid);
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
					eci.setCenter_num(centerNum);
					this.saveZybExaminfoChargingItem(eci);
					// System.out.println(esdto.getExaminfo_id()+" 1 "+" "+j);
					ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());
					
					//判断是否是lis检查项目
					int lisnum = this.checkZybListItem(es.getItem_code(),centerNum);
					if (lisnum > 0) {
						SampleExamDetail sd = new SampleExamDetail();
						sd = this.getZybSampleExamDetailforExamid(esdto.getExam_num(), ci.getSam_demo_id(), "W");
						long samexam_id = 0;
						String sample_barcode = "";
						if ((sd == null) || (sd.getId() <= 0)) {
							sample_barcode = this.batchService.GetCreateID("barcode", centerNum);
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
							sed.setExam_num(ed.getExam_num());
							this.saveZybSampleExamDetail(sed);
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
						erci.setCreater(userid);
						erci.setCreate_time(DateTimeUtil.parse());
						erci.setUpdater(userid);
						erci.setUpdate_time(DateTimeUtil.parse());
						erci.setBar_code(sample_barcode);
						erci.setCharging_item_code(ci.getItem_code());
						this.saveZybExamResultChargingItem(erci);
						// System.out.println(esdto.getExaminfo_id()+" 4 "+"
						// "+j);
					}

					//判断是否是pass项目
					int pacsnum = this.checkZybPacsItem(es.getItem_code(),centerNum);
					// System.out.println(esdto.getExaminfo_id()+" 5 "+" "+j);
					if (pacsnum > 0) {
						long summary_id = 0;
						PacsSummary psd = new PacsSummary();
						psd = this.getZybPacsSummaryDTOforExamid(ed.getExam_num(), ci.getSam_demo_id(), "N");
						String pacs_req_code="";
						if ((psd == null) || (psd.getId() <= 0)) {
							pacs_req_code=this.batchService.GetCreateID("pacs_req_num", centerNum);
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
							ps.setApply_person(username);
							ps.setApply_date(DateTimeUtil.parse());
							ps.setExam_status("N");
							ps.setCreater(userid);
							ps.setCreate_time(DateTimeUtil.parse());
							ps.setUpdater(userid);
							ps.setUpdate_time(DateTimeUtil.parse());
							ps.setExaminfo_sampleId(ci.getSam_demo_id());
							ps.setPacs_req_code(pacs_req_code);
							this.saveZybPacsSummary(ps);
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
						pd.setCreater(userid);
						pd.setCreate_time(DateTimeUtil.parse());
						pd.setUpdater(userid);
						pd.setUpdate_time(DateTimeUtil.parse());
						pd.setPacs_req_code(pacs_req_code);
						this.saveZybPacsdetail(pd);
						// System.out.println(esdto.getExaminfo_id()+" 9 "+"
						// "+j);
					}
				}
			}
			message = "ok-体检人员交费项目处理完成";
		}
		return message;
	}
	
	/**
	 * 
	     * @Title: getExamInfoUserForExamId2   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ZybExamInfoUserDTO      
	     * @throws
	 */
	public ZybExamInfoUserDTO getExamInfoUserForExamId2(String exam_num,String center_num)throws ServiceException {
		ZybExamInfoUserDTO ei = new ZybExamInfoUserDTO();
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.id as customer_id,a.nation,a.user_name,a.arch_num,a.id_num,"
				+ "a.sex,c.age,c.exam_num,c.is_marriage,c.exam_type,"
				+ "c.position,c._level,c.group_id,c.email,c.chargingType,c.remarke,"
				+ "c.others,c.status,c.phone,c.customer_type,c.customer_type_id,a.address");
		sb.append(",c.group_id,c.group_index,a.birthday,c._level,c.status,c.register_date"
				+ ",c.join_date,c.exam_times,c.exam_num,c.company,c.past_medical_history,"
				+ "c.picture_Path,c.is_after_pay,c.batch_id,n.batch_name,"
				+ "c.employeeID,c.mc_no,c.visit_date,c.visit_no,c.clinic_no,c.exam_indicator,c.batch_id,"
				+ "c.company_id,m.group_name  ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join batch  n on n.id=c.batch_id ");
		sb.append(" left join group_info  m on m.id=c.group_id ");
		sb.append(" where c.customer_id=a.id  ");
		sb.append(" and c.is_Active='Y' ");
		sb.append(" and a.is_Active='Y' ");
		sb.append(" and c.exam_num='"+exam_num+"' and n.center_num='"+center_num+"' ");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 1000, ZybExamInfoUserDTO.class);
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			ei = (ZybExamInfoUserDTO) map.getList().get(0);
			ei.setBatch_name(ei.getBatch_name()+"("+ei.getCompany()+")");
			if((ei.getRegister_date()!=null)&&(ei.getRegister_date().trim().length()>10)) ei.setRegister_date(ei.getRegister_date().substring(0, 10));
			ei.setExamcount(getExamInfotime(ei.getCustomer_id()));
		}
		return ei;
	}

	/**
	 * 
	     * @Title: updateZybExaminfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoSet      
	     * @throws
	 */
	public ExaminfoSet updateZybExaminfoSet(ExaminfoSet eis)throws ServiceException {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
        TransactionStatus txStatus = txManager.getTransaction(def);
		this.pm.update(eis);
		txManager.commit(txStatus);
		return eis;
	}
	/**
	 * 
	     * @Title: djtdelExamInfoGChangItem   
	     * @Description: 登记台减项
	     * @param: @param exam_id
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @param username
	     * @param: @param centerNum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String zybdelExamInfoGChangSet(String exam_num,String ids,long userid,String centerNum) throws ServiceException {
		    ExamInfoDTO ed = this.getZybCustExamInfoForexamId(exam_num);
		    String message = "error-";
		    if((ed==null)||(ed.getId()<=0)){
		    	message="error-无效体检人员";
		    }else if((!"Y".equals(ed.getStatus()))&&(!"D".equals(ed.getStatus()))){
		    	message="error-体检人员状态下不允许删除套餐";
		    }else{
		        
		        List<ExaminfoSet> list= new ArrayList<ExaminfoSet>();
		    	list=this.getZybExamInfoSetForId(exam_num);
		    	for(ExaminfoSet oldExaminfoSet:list){		    		
		    		
						ExamSetDTO oldExamSetDTO = this.batchService.findSetById(ids,centerNum);
						if(oldExamSetDTO.getId()==oldExaminfoSet.getExam_set_id()){
							oldExaminfoSet.setIsActive("N");
							oldExaminfoSet.setUpdate_time(DateTimeUtil.parse());
							oldExaminfoSet.setUpdater(userid);
			    		    this.updateZybExaminfoSet(oldExaminfoSet);
						}								
		    	
		    	}
		message="ok-体检人员交费信息减项完成"; 
		}
	return message;
	}
	
	/**
	 * 
	     * @Title: zybcheckExamInfotime   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String zybcheckExamInfotime(String ids) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		String flags = "ok";
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			sbt.append("select count(*) as num from exam_info a where (a.status ='J' or a.status ='Z') and id in ("
					+ ids + ") ");
			ResultSet rs = statement.executeQuery(sbt.toString());
			if (rs.next()) {
				if (rs.getLong("num") > 0) {
					rs.close();
					flags = "error-只能对预约用户进行加减项处理，操作不能继续！";
				} else {
					rs.close();

					sbt = new StringBuffer();
					sbt.append(
							"select count(*) as num from (select b.sex from exam_info a,customer_info b where b.is_Active='Y' "
									+ "and a.customer_id=b.id and a.id in (" + ids + ") group by b.sex) m ");
					rs = statement.executeQuery(sbt.toString());
					if (rs.next()) {
						if (rs.getLong("num") > 1) {
							flags = "error-所选人员性别不一致，所以不能加减项目！";
						}
					} else {
						rs.close();
					}
				}
			} else {
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
	       * @Title: getIsnewaddedExamInfoItemForId   
	       * @Description: TODO(这里用一句话描述这个方法的作用)   
	       * @param: @param exam_id
	       * @param: @return
	       * @param: @throws ServiceException      
	       * @return: int      
	       * @throws
	   */
	  public int getIsnewaddedExamInfoItemForId(String exam_num, String center_num)throws ServiceException {
				List ls = qm.find("From ExaminfoChargingItem where isActive='Y' and pay_status<>'M' and exam_status<>'G' "
						+ "and center_num = '"+center_num+"' and exam_num='"+exam_num+"'");
				if((ls!=null)&&(ls.size()>0))
					return ls.size()+1;
				else
					return 0;

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
					+ "examination_item b,charging_item_exam_item c,department_dep d where a.item_code=c.charging_item_code "
					+ " and de.dep_id = d.id  and b.item_num=c.item_code and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id "
					+ "and de.center_num ='"+center_num+"'  and d.dep_category='131' and a.item_code ='"+item_code+"') m ");
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
		     * @Title: getSampleExamDetailforExamid   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param examid
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: SampleExamDetailDTO      
		     * @throws
		 */
		public SampleExamDetail getSampleExamDetailforExamid(String exam_num,long samp_id,String status) throws ServiceException{
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
						+ "examination_item b,charging_item_exam_item c,department_dep d,department_vs_center de where a.item_code=c.charging_item_code "
						+ " and de.dep_id = d.id  and de.center_num ='"+center_num+"'  and b.item_num=c.item_code and a.isActive='Y' and b.is_Active='Y' and a.dep_id=d.id "
						+ "and d.dep_category='21' and a.item_code ='"+item_code+"') m ");
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
			     * @Title: getPacsSummaryDTOforExamid   
			     * @Description: 获取pacs申请主表信息   
			     * @param: @param examinfo_num
			     * @param: @param status
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: PacsSummaryDTO      
			     * @throws
			 */
	public PacsSummary getPacsSummaryDTOforExamid(String examinfo_num, long sam_id, String status)
			throws ServiceException {
		String hql = " from PacsSummary g where g.examinfo_num='" + examinfo_num + "' and g.exam_status='" + status
				+ "' and g.examinfo_sampleId=" + sam_id + "";
		List<PacsSummary> list = qm.find(hql);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	  /**
	   * 
	       * @Title: addIDSExamInfoChangItem   
	       * @Description: TODO(这里用一句话描述这个方法的作用)   
	       * @param: @param examid
	       * @param: @param listitem
	       * @param: @param itemcodes
	       * @param: @param exam_indicator
	       * @param: @param userid
	       * @param: @param username
	       * @param: @param centerNum
	       * @param: @throws ServiceException      
	       * @return: void      
	       * @throws
	   */
	 public void addIDSExamInfoChangItem(String exam_num, List<GroupChargingItemDTO> listitem,String itemcodes,String exam_indicator,long userid,String username,String centerNum) throws ServiceException {
		    ExamInfoDTO ed = this.getZybCustExamInfoForexamId(exam_num);
			for (int j = 0; j < listitem.size(); j++) {				
				GroupChargingItemDTO es = new GroupChargingItemDTO();
				es = listitem.get(j);
				ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());
				es.setCharge_item_id(cd.getId());
				if(itemcodes.toUpperCase().indexOf(es.getItem_code().toUpperCase())<0){
			    boolean ctcheckflag= this.batchService.checkitemflag(itemcodes, es.getItem_code(),centerNum);
			    int isnew=getIsnewaddedExamInfoItemForId(exam_num, centerNum);
				if (!ctcheckflag) {// 没有冲突项目
					// 新增 examinfo_charging_item、
					ExaminfoChargingItem eci = new ExaminfoChargingItem();
					//eci.setExaminfo_id(examid);
					eci.setCharge_item_id(cd.getId());
					eci.setExam_num(exam_num);
					if ("Z".equals(exam_indicator)) {
						eci.setExam_indicator("T");
						eci.setPay_status("R");
						eci.setItemnum(eci.getItemnum());
						eci.setTeam_pay_status("N");
						eci.setTeam_pay(es.getAmount());
						eci.setPersonal_pay(0);
						eci.setAmount(0);
						eci.setApp_type("2");
					} else if ("G".equals(exam_indicator)) {
						eci.setExam_indicator("G");
						eci.setPay_status("N");
						eci.setTeam_pay(0);
						eci.setAmount(0);
						eci.setItemnum(eci.getItemnum());
						eci.setPersonal_pay(es.getAmount());
						eci.setTeam_pay_status("Y");
						eci.setApp_type("1");
					} else {
						eci.setExam_indicator("T");
						eci.setPay_status("R");
						eci.setItemnum(eci.getItemnum());
						eci.setTeam_pay_status("N");
						eci.setTeam_pay(es.getAmount());
						eci.setPersonal_pay(0);
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

					ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());
					int lisnum = checkListItem(es.getItem_code(),centerNum);
					if (lisnum > 0) {
						SampleExamDetail sd = new SampleExamDetail();
						sd = this.getSampleExamDetailforExamid(exam_num, ci.getSam_demo_id(), "W");
						long samexam_id = 0;
						String sample_barcode = "";
						if ((sd == null) || (sd.getId() <= 0)) {
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
							samexam_id = sed.getId();
						} else {
							samexam_id = sd.getId();
							sample_barcode = sd.getSample_barcode();
						}

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
					}

					int pacsnum = this.checkPacsItem(es.getItem_code(),centerNum);
					if (pacsnum > 0) {
						PacsSummary psd = new PacsSummary();
						psd = getPacsSummaryDTOforExamid(ed.getExam_num(), ci.getSam_demo_id(), "N");
						long summary_id = 0;
						if ((psd == null) || (psd.getId() <= 0)) {
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
							ps.setApply_person(username);
							ps.setApply_date(DateTimeUtil.parse());
							ps.setExam_status("N");
							ps.setCreater(userid);
							ps.setCreate_time(DateTimeUtil.parse());
							ps.setUpdater(userid);
							ps.setUpdate_time(DateTimeUtil.parse());
							ps.setExaminfo_sampleId(ci.getSam_demo_id());
							ps.setPacs_req_code(this.batchService.GetCreateID("pacs_req_num", centerNum));
							this.pm.save(ps);
							summary_id = ps.getId();
						} else {
							summary_id = psd.getId();
						}
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
						pd.setPacs_req_code(this.batchService.GetCreateID("pacs_req_num", centerNum));
						this.pm.save(pd);
					}

				}
			}
		}
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
		public ExamInfoUserDTO getExamInfoallUserForExamId(String exam_num,String center_num)throws ServiceException {
			ExamInfoUserDTO ei = new ExamInfoUserDTO();
			StringBuffer sb = new StringBuffer();
			sb.append("select c.id,a.id as customer_id,a.nation,a.user_name,a.arch_num,a.id_num,"
					+ "a.sex,c.age,c.exam_num,c.is_marriage,c.exam_type,"
					+ "c.position,c._level,c.group_id,c.email,c.chargingType,c.remarke,"
					+ "c.others,c.status,c.phone,c.customer_type,c.customer_type_id,a.address");
			sb.append(",c.group_id,c.group_index,a.birthday,c._level,c.status,c.register_date"
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
			sb.append(" and c.exam_num='"+exam_num+"' and n.center_num='"+center_num+"'");
			PageSupport map = this.jqm.getList(sb.toString(), 1, 1000, ExamInfoUserDTO.class);
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				ei = (ExamInfoUserDTO) map.getList().get(0);
				ei.setBatch_name(ei.getBatch_name()+"("+ei.getCompany()+")");
				if((ei.getRegister_date()!=null)&&(ei.getRegister_date().trim().length()>10)) ei.setRegister_date(ei.getRegister_date().substring(0, 10));
				ei.setExamcount(getExamInfotime(ei.getCustomer_id()));
			}
			return ei;
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
		public ChargingItemDTO getChargingItemForId(long chargingid) throws ServiceException{
			String sql = "select id,dep_id,sam_demo_id,sam_report_demo_id,item_code,item_name,"
					+ "item_pinyin,item_category,sex,amount,dep_category,isOnlyApplyOrReport,"
					+ "item_seq,guide_category,his_num,exam_num,view_num,isActive,creater,"
					+ "create_time,updater,update_time,calculation_amount,interface_flag,"
					+ "item_type,charge_inter_num,item_abbreviation,notices from charging_item where id="+chargingid;
			PageSupport map = this.jqm.getList(sql, 1, 1000, ChargingItemDTO.class);
			ChargingItemDTO webrole = new ChargingItemDTO();
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				webrole = (ChargingItemDTO) map.getList().get(0);
			}
			return webrole;
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
		public List<SampleExamDetailDTO> getSampleExamDetailforCharingId(String examnum,long charingid) throws ServiceException{
			
			String sql = "select a.id,a.exam_num,a.status,a.sample_barcode from sample_exam_detail a,charging_item b "
					+ "where a.sample_id=b.sam_demo_id and b.id="+charingid+" and a.status='W' and a.exam_num='"+examnum+"'";
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
		public List<PacsSummaryDTO> getPacsSummaryforexamNum(String examNum,long chargid) throws ServiceException{
			String sql = "select a.id,a.exam_status from pacs_summary a,charging_item b "
					+ " where a.examinfo_sampleId=b.sam_demo_id and a.examinfo_num='"+examNum+"' and b.id='"+chargid+"'";
			PageSupport map = this.jqm.getList(sql, 1, 1000, PacsSummaryDTO.class);
			List<PacsSummaryDTO> webrole = new ArrayList<PacsSummaryDTO>();
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				webrole = map.getList();
			}
			return webrole;
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
		private int getNoPacsSummaryforexamNum(Connection connection,String exam_num, String chargitemnum, long sampleid, String center_num)
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
						+ "and o.charge_item_id=b.id and o.center_num = '"+center_num+"' "
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
		       * @Title: checkExamChargItem   
		       * @Description: TODO(这里用一句话描述这个方法的作用)   
		       * @param: @param examid
		       * @param: @param chargitemid
		       * @param: @param type
		       * @param: @return
		       * @param: @throws ServiceException      
		       * @return: int      
		       * @throws
		   */
		  public int checkExamChargItem(String exam_num,long chargitemid, String center_num) throws ServiceException{
				int countnum=0;
				StringBuffer sql = new StringBuffer("select count(*) as num from examinfo_charging_item where isActive='Y' and center_num = '"+center_num+"' ");
				sql.append(" and exam_num='" + exam_num + "' and charge_item_id=" + chargitemid + " ");
				
				//System.out.println(sql.toString());
				Connection connection = null;
				Statement statement = null;
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
	      * @Title: delZybIDSExamInfoChangItem   
	      * @Description: TODO(这里用一句话描述这个方法的作用)   
	      * @param: @param examid
	      * @param: @param chargingId
	      * @param: @param types
	      * @param: @param userid
	      * @param: @throws ServiceException      
	      * @return: void      
	      * @throws
	  */
	public void delZybIDSExamInfoChangItem(String exam_num, long chargingId, long userid, String center_num)
			throws ServiceException {
		ExamInfoUserDTO ei = getExamInfoallUserForExamId(exam_num,center_num);
		ChargingItemDTO ci = getChargingItemForId(chargingId);
		List<SampleExamDetailDTO> webrole = new ArrayList<SampleExamDetailDTO>();
		webrole = getSampleExamDetailforCharingId(exam_num, chargingId);
		int examchingitemcount = checkExamChargItem(exam_num, chargingId, center_num);
		if (examchingitemcount > 0) {
			Connection connection = null;
			Statement statement = null;
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				boolean lisdelfalg = true;
				boolean pacsdelfalg = true;
				for (SampleExamDetailDTO sd : webrole) {
					if (("Y".equals(sd.getStatus())) && (sd.getSample_barcode() != null)
							&& (sd.getSample_barcode().trim().length() > 0)) {
						// 发送lis去掉接口
						lisdelfalg = false;
					} else if ((sd.getSample_barcode() != null) && (sd.getSample_barcode().trim().length() > 0)) {
						String sql = "delete examResult_chargingItem where bar_code='" + sd.getSample_barcode() + "' and charging_id="
								+ chargingId + "";
						connection.createStatement().executeUpdate(sql);
						int counts = 0;
						sql = "select COUNT(*) as counts from examResult_chargingItem where bar_code='" + sd.getSample_barcode() + "'"
								+ " and result_type='sample' and isActive='Y'";
						ResultSet rs = connection.createStatement().executeQuery(sql);
						if (rs.next()) {
							counts = rs.getInt("counts");
						}
						rs.close();
						if (counts <= 0) {
							sql = "delete sample_exam_detail where exam_num='" + sd.getExam_num()+"'";
							connection.createStatement().executeUpdate(sql);
						} else {
							sql = "select b.BarCode_Class from sample_exam_detail a,sample_demo b where a.exam_num='"
									+ sd.getExam_num() + "' and a.sample_id=b.id and b.isActive='Y'";
							ResultSet rs1 = connection.createStatement().executeQuery(sql);
							if (rs1.next()) {
								int barcodeclass = rs1.getInt("BarCode_Class");
								if (barcodeclass == 0) {
									sql = "update sample_exam_detail set sample_barcode='"
											+ this.batchService.GetCreateID("barcode", center_num) + "' where exam_num='" + sd.getExam_num()+"'";
									connection.createStatement().executeUpdate(sql);
								}
							}
							rs1.close();
						}

					}
				}

				List<PacsSummaryDTO> pacslist = new ArrayList<PacsSummaryDTO>();
				pacslist = getPacsSummaryforexamNum(ei.getExam_num(), chargingId);
				for (PacsSummaryDTO sd : pacslist) {
					if ("N".equals(sd.getExam_status())) {
						// 发送pacs去掉接口
						// 执行pacs_summary删除操作
						String sqlpacs = "delete pacs_detail where " + "summary_id=" + sd.getId()
								+ " and examinfo_num='" + ei.getExam_num() + "'" + " and chargingItem_num='"
								+ ci.getItem_code() + "'";
						connection.createStatement().executeUpdate(sqlpacs);
						int counts = getNoPacsSummaryforexamNum(connection, exam_num, ci.getItem_code(),
								sd.getExaminfo_sampleId(),center_num);
						if (counts <= 0) {
							sqlpacs = "delete pacs_summary  where id =" + sd.getId();
							connection.createStatement().executeUpdate(sqlpacs);
						}
					} else {
						pacsdelfalg = false;
					}
				}

				if (pacsdelfalg && lisdelfalg) {
					String examss = "update examinfo_charging_item set isActive='N',updater=" + userid
							+ ",update_time='" + DateTimeUtil.getDateTime() + "' where exam_num='" + exam_num + "' "
							+ "and charge_item_id=" + chargingId + "";
					connection.createStatement().executeUpdate(examss);
					connection.commit();
				} else {
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
    public List<ExaminfoSetDTO> getExamSetForExamid(String exam_num,String center_num) throws ServiceException {
    	List<ExaminfoSetDTO> list=new ArrayList<ExaminfoSetDTO>();
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT a.id,a.examinfo_id,a.exam_set_id,a.exam_indicator,a.discount,a.amount,a.isActive,a.final_exam_date");
    	sb.append(",a.is_new_added,a.creater,a.create_time,a.updater,a.update_time,x.set_num,x.set_name");
    	sb.append(" FROM examinfo_set a");
    	sb.append(" left join exam_set x on x.id=a.exam_set_id");
    	sb.append(" where a.isActive='Y' and a.exam_num= '"+exam_num+"' and center_num= '"+center_num+"'");
		list=this.jqm.getList(sb.toString(),ExaminfoSetDTO.class);
		return list;
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
	public PageReturnDTO getDjtExamInfoUserList(long com_id,long batch_id,long set_id,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String apptype,String userid,String centernum, int pagesize, int pageno,String sort,String order,String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select da.data_name,c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.create_time,c.freeze,c.batch_id,"
				+ "dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		//sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 21)) as wpacs ");
		//sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 21)) as ypacs ");
		//sb.append(" ,(select count(*) from dbo.fun_GetUnReqItem(c.id, 131)) as wlis ");
		//sb.append(" ,(select count(*) from dbo.fun_GetSuccReqItem(c.id, 131)) as ylis ");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,m.dep_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,c.introducer,c.customer_type_id ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_num = c.exam_num and j.app_type='"+apptype+"' and j.center_num='"+centernum+"'  ");
		sb.append(" left join data_dictionary  da   on da.id = c.customer_type    and  da.data_code='TJLX'  ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y'  and c.apptype='"+apptype+"' ");
		sb.append(" and  c.center_num='" + centernum + "' ");		
		
		if (com_id > 0) {
			sb.append(" and c.company_id=" + com_id + " ");
		}
		
		if (batch_id > 0) {
			sb.append(" and c.batch_id=" + batch_id + " ");
		}
		
		if (set_id > 0) {
			sb.append(" and " + set_id + " in (select exam_set_id from examinfo_set where exam_num = c.exam_num) ");
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
				batchlist=this.getExamSetForExamid(eu.getExam_num(),center_num);
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

	@Override
	public ExamInfoDTO getExamInfoFindExamNum(ZybInquisitionResultModel model)
			throws ServiceException {
		String sql = " SELECT ex.exam_num,da.data_code_children,cu.id_num FROM  customer_info cu,exam_info ex "
					+"	left JOIN  data_dictionary  da  ON   ex.customer_type=da.id  "
					+"	WHERE  ex.customer_id=cu.id  AND   cu.is_Active='Y'  AND   ex.is_Active='Y'  "
					+"	AND   ex.exam_num='"+model.getExam_num()+"'  ";
			List<ExamInfoDTO> li = this.jqm.getList(sql,ExamInfoDTO.class);
		return li.get(0);
	}

	@Override
	public ZybOccuHis updateZybOccuHis(ZybImpCustomerInfoModel model,UserDTO user)
			throws ServiceException {
		ZybOccuHis zybhis = (ZybOccuHis) this.qm.get(ZybOccuHis.class,model.getZyb_id());
		zybhis.setCompany(model.getComname());
		zybhis.setConcentrations(model.getConcentrations());
		zybhis.setStartdate(model.getTime1());
		zybhis.setEnddate(model.getTime2());
		zybhis.setCreate_date(DateTimeUtil.getDateTime());
		zybhis.setCreater(user.getUserid());
		zybhis.setHarmname(model.getHarmname());
		zybhis.setIsradiation(model.getIsradiation());
		zybhis.setMeasure(model.getMeasure());
		zybhis.setWorkshop(model.get_level());
		zybhis.setWorktype(model.getOccutypeofwork());
		zybhis.setRadiation(model.getRadiation());
		zybhis.setMan_haur(model.getMan_haur());
		zybhis.setCumulative_exposure(model.getCumulative_exposure());
		zybhis.setHistory_excessive(model.getHistory_excessive());
		zybhis.setRemark(model.getRemark());
		
		this.pm.update(zybhis);
		return zybhis;
	}

	@Override
	public ZybOccuHisDTO getZybOccuHisFinID(ZybImpCustomerInfoModel model)
			throws ServiceException {
		String sql = " SELECT * FROM   zyb_occuhis  s  WHERE  s.id='"+model.getZyb_id()+"'  ";
		List<ZybOccuHisDTO> li =  this.jqm.getList(sql, ZybOccuHisDTO.class);
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
	private ExamInfoDTO getExamInfoForIdNum(String id_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where id_num ='" + id_num
				+ "' and is_Active='Y' ";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if (list.size() > 0) {
			webrole = list.get(0);
		}
		return webrole;
	}
	private ExamInfoDTO getExamInfoForArchIdList(String arch_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where arch_num ='" + arch_num
				+ "'  and is_Active='Y'  ";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if (list.size() > 0) {
			webrole = list.get(0);
		}
		return webrole;
	}

	@Override
	public ZybExamOccuhazardfactors savezybHazardBMWOccuhazard(String tiJianType, ZybExamOccuhazardfactors zybhis, UserDTO user,
			String app) throws ServiceException {
		
		String sql = "SELECT zoc.hazardclassID, zoc.hazardfactorsID, zoc.hazard_code, zoc.hazard_name, zoc.hazard_year FROM data_dictionary da, zyb_occuhazardfactors zoc WHERE data_code = 'BMGQ' AND da.data_code_children = zoc.hazard_code ";
		List<ZybOccuhazardfactorsDTO> zybocc = this.jqm.getList(sql,ZybOccuhazardfactorsDTO.class);
		if(zybocc!=null && zybocc.size()>0){
			for (int i = 0;i<zybocc.size();i++) {
				ZybExamOccuhazardfactors z = new ZybExamOccuhazardfactors();
				z.setArch_num(zybhis.getArch_num());
				z.setExam_num(zybhis.getExam_num());
				z.setHazard_code(zybocc.get(i).getHazard_code());
				if("after".equals(tiJianType)) {
					z.setOccuphyexaclassid("a9859e756edc4f5da40f7c0499a66d58"); //上岗前
				}else if("later".equals(tiJianType)) {
					z.setOccuphyexaclassid("64a3e7fd6aef46ada48ed6d03f5ddb21"); //离岗时
				}
				z.setIsActive("Y");
				z.setCreate_time(DateTimeUtil.getDate3());
				z.setCreater(user.getUserid());
				z.setRemark("");
				this.pm.save(z);
			}
			
		}
		
		return zybhis;
	}

	public PageReturnDTO getZybUserList(String data_source,long com_id,long set_id,int  ren_type,String art_num,String exam_num,String employeeID,String status,String id_num,String username, String starttime,String enddate,String time3,String time4,String createtime,String examtype,String jdstatuss,String userid,String centernum,ZybImpCustomerInfoModel model, int pagesize, int pageno,String sort,String order,UserDTO user,String center_num) throws ServiceException {
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
		
		sb.append(" select case when tr.readFlag is null then '未确认' when tr.readFlag = '0' then '上传中' when tr.readFlag = '1' then '已上传' when tr.readFlag = '2' then '上传失败' end as readFlags,tr.readFlag, tr.notices, "
				+ " case when tr1.readFlag is null then '未确认' when tr1.readFlag = '0' then '上传中' when tr1.readFlag = '1' then '已上传' when tr1.readFlag = '2' then '上传失败' end as readFlags1,tr1.readFlag as readFlag1, tr1.notices as notices1, "
				+ " c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage, c.is_upload, c.is_report_upload,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active,j.check_time"
				+ ",c.employeeID,dbo.GetUserNameByUserID(j.exam_doctor_id) as final_doctor,c.final_date,c.create_time,c.freeze,"
//				+ " ( select sum(eci.personal_pay) from examinfo_charging_item eci where eci.isActive='Y' "
//				+ " and eci.exam_indicator like 'G%' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as personal_pay,("
//				+ "select sum(eci.team_pay) from examinfo_charging_item eci where eci.isActive='Y' "
//				+ "and eci.exam_indicator like '%T' and eci.pay_status<>'M' and eci.exam_num=c.exam_num) as team_pay ,"
				+ "dbo.GetPersonalPayByExamId(c.exam_num) as personal_pay,dbo.GetTeamPayByExamId(c.exam_num) as team_pay,dbo.GetUserNameByUserID(j.check_doc) as check_doctor");
		
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,m.dep_name,c.company,"
				+ "c.register_date,c.join_date,x.chi_name,c.is_guide_back,c.update_time,c.introducer ");
		sb.append(" ,(select max(eci.exam_date) from examinfo_charging_item eci where eci.exam_num = c.exam_num and "
				+ "eci.exam_status = 'Y' and eci.isActive = 'Y')as exam_times,"
				+ "dd.data_name as customer_type_name ,uu.chi_name as join_operatorName,c.customer_type_id ");		
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join user_usr uu on uu.id=c.join_operator ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_num = c.exam_num  and j.app_type=c.apptype and  j.center_num = c.center_num  ");
		sb.append(" left join data_dictionary dd on dd.id = c.customer_type  ");
		sb.append(" left join thrid_req tr on tr.exam_num = c.exam_num and tr.ser_config_key = 'REPORT_SEND' ");
		sb.append(" left join thrid_req tr1 on tr1.exam_num = (select com_num from company_info where id=c.company_id and is_Active = 'Y') and tr1.ser_config_key = 'REPORT_SEND' ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.apptype='2' ");
		sb.append(" and  c.center_num='" + centernum + "' ");		
		sb1.append("select count(*) as counts from customer_info a ,exam_info c");
		sb1.append(" where c.customer_id=a.id ");
		sb1.append(" and c.is_Active='Y'  and c.apptype='2'");
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
			sb1.append(" and " + set_id + " in (select exam_set_id from examinfo_set where examinfo_id = c.id) ");
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
		
		sb.append(" ) as LeaveWordView ) as TEMP1 WHERE ROWID>"+startindex+"");
		PageSupport map = this.jqm.getList(sb1.toString(),sb.toString(), pageno, pagesize, ExamInfoUserDTO.class);
		TranLogTxt.liswriteEror_to_txt_single("职业病数据上传-人员列表", sb.toString());
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
				batchlist=this.customerInfoService.getExamSetForExamNum(eu.getExam_num(),center_num);
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
				
				String ZYB_UPLOAD_DATA_PAGE_STYLE = this.customerInfoService.getCenterconfigByKey("ZYB_UPLOAD_DATA_PAGE_STYLE", user.getCenter_num()).getConfig_value();
				String logname = "getZybUserList";
				if("jq".equals(ZYB_UPLOAD_DATA_PAGE_STYLE)) {
					BodyJQ body = PutReportControlJQ.getBodyJQ(eu.getExam_num(), logname);
					eu.setBody(body);
				} else if("ww".equals(ZYB_UPLOAD_DATA_PAGE_STYLE)) {
					PERSON person = PutReportControlWW.getPERSON(eu.getExam_num(), logname);
					eu.setPerson(person);
				}
				
				list.add(eu);
			}
			webrole.setRows(list);
		}
		return webrole;
	}

	@Override
	public String addZybOccutypeofwork(String occutypeofworkName) throws ServiceException {
		String sql = "select  z.typeofworkID,z.typeofwork_code,z.typeofwork_name  " +
				   "   from zyb_typeofwork z  where  z.typeofwork_name = '"+occutypeofworkName.trim()+"'";
		List<ZybTypeOfWork> list = this.jqm.getList(sql, ZybTypeOfWork.class);
		if(list.size() > 0 && list.get(0)!= null) {
			return list.get(0).getTypeofworkID();
		}
		String code = "";
		String sql1 = " select top 1 z.typeofworkID,z.typeofwork_code,z.typeofwork_name  " +
				     " from zyb_typeofwork z  where   z.typeofwork_code like 'tj-%'" +
				     " order by z.typeofwork_code desc ";
		List<ZybTypeOfWork> list1 = this.jqm.getList(sql1, ZybTypeOfWork.class);
		if(list1.size() > 0 && list1.get(0) != null){
			String typeofwork_code = list1.get(0).getTypeofwork_code();
			AtomicInteger atomic = new AtomicInteger(Integer.parseInt(typeofwork_code.split("-")[1]));
			code = "tj-"+String.format("%03d", atomic.incrementAndGet());
		}else{
			code = "tj-001";
		}
		String typeofworkID = UUID.randomUUID().toString().replaceAll("-","");
		String insert = "insert   into zyb_typeofwork(typeofworkID,typeofwork_code,typeofwork_name) values('"+typeofworkID+"','"+code+"','"+occutypeofworkName+"')";
		this.jpm.execSql(insert);
		return typeofworkID;
	}
    @Override
    public String cheCkOccuphyexaclass(String exam_num,List<ExamSetDTO> listset) throws ServiceException {
        StringBuffer msg = new StringBuffer();
	    StringBuffer exam_set_ids = new StringBuffer();
	    if(listset!=null && listset.size()>0){
	        for(ExamSetDTO li:listset){
                exam_set_ids.append("'"+li.getSet_num()+"',");
            }
        }
	    String sql = "   SELECT  distinct hy.occuphyexaclassID,hy.occuphyexaclass_name,s.set_name FROM " +
                " exam_set s,zyb_occuphyexaclass hy " +
                "  where s.occuphyexaclassid=hy.occuphyexaclassID and s.set_num in  " +
                " ("+exam_set_ids.substring(0,exam_set_ids.length()-1)+")  " +
                "  union "+
                "   SELECT distinct hy.occuphyexaclassID,hy.occuphyexaclass_name,s.set_name FROM set_occuhazardfactors so," +
                "zyb_occuphyexaclass hy,exam_set s " +
                "   WHERE  hy.occuphyexaclassID = so.occuphyexaclassID and s.id = so.set_id" +
                "  and s.set_num in " +
                " ("+exam_set_ids.substring(0,exam_set_ids.length()-1)+")  ";
        List<ZybOccuphyexaClassDTO> set_hy = this.jqm.getList(sql,ZybOccuphyexaClassDTO.class);

        String yexa_sql = "   SELECT distinct zyb_ocy.occuphyexaclassID,zyb_ocy.occuphyexaclass_name FROM " +
                " zyb_exam_occuhazardfactors zyb_eoh,zyb_occuphyexaclass zyb_ocy "+
                "  WHERE  zyb_eoh.occuphyexaclassid = zyb_ocy.occuphyexaclassID "+
                "  AND zyb_eoh.isActive='Y' AND exam_num='"+exam_num+"'";
        List<ZybOccuphyexaClassDTO> yexa = this.jqm.getList(yexa_sql, ZybOccuphyexaClassDTO.class);

        boolean flag = true;
        //判断当前人员与套餐职业体检类别是否一致
        if(set_hy!=null && set_hy.size()> 0 && yexa!=null && yexa.size()>0){
            msg.append("当前人员职业体检类别："+yexa.get(0).getOccuphyexaclass_name()+"</br>");
            msg.append("套餐名称>>>>>体检类别</br>");
            for(ZybOccuphyexaClassDTO li:set_hy){
                if(!li.getOccuphyexaclassID().equals(yexa.get(0).getOccuphyexaclassID()) && !"".equals(li.getOccuphyexaclass_name())){
                    msg.append(li.getSet_name()+">>>>>"+li.getOccuphyexaclass_name()+"</br>");
                    flag = false;
                }
            }
            msg.append("注：体检人员只能选择一种体检类别，套餐体检类别与人员不一样");
        }
        boolean f = true;
        //判断多个套餐职业体检类别是否一致
        if(set_hy!=null && set_hy.size()> 0){
            for(int i=0; i<set_hy.size(); i++){
                if(i < set_hy.size()-1 &&
                        !set_hy.get(i).getOccuphyexaclassID().equals(set_hy.get(i+1).getOccuphyexaclassID())){
                    msg.append(set_hy.get(i).getSet_name()+">>>>>"+set_hy.get(i).getOccuphyexaclass_name()+"</br>");
                    f = false;
                }
                if(i==set_hy.size()-2 &&
                        !set_hy.get(i).getOccuphyexaclassID().equals(set_hy.get(i+1).getOccuphyexaclassID())){
                    msg.append(set_hy.get(i+1).getSet_name()+">>>>>"+set_hy.get(i+1).getOccuphyexaclass_name()+"</br>");
                    f = false;
                }
            }
            if(!f){
                msg = new StringBuffer();
                msg.append("套餐名称>>>>>体检类别</br>");
                for(int i=0; i<set_hy.size(); i++){
                    msg.append(set_hy.get(i).getSet_name()+">>>>>"+set_hy.get(i).getOccuphyexaclass_name()+"</br>");
                }
                msg.append("注：体检人员只能选择一种体检类别，套餐存在多种体检类别");
            }
        }
        if(flag && f){
            msg = new StringBuffer();
        }
        return msg.toString();
    }
}
