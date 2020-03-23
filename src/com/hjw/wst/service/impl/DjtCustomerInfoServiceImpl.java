package com.hjw.wst.service.impl;

import com.hjw.util.CommSet;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.util.TranLogTxt;
import com.hjw.util.ValidateUtil;
import com.hjw.webService.client.DAHSendMessage;
import com.hjw.webService.client.Bean.DAHCustomerBean;
import com.hjw.webService.client.body.DAHResBody;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.DjtTotalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoExtDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoBatchDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.LiscountDTO;
import com.hjw.wst.DTO.PacsCountDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamInfoSign;
import com.hjw.wst.domain.ExamResultChargingItem;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.ExaminfoSet;
import com.hjw.wst.domain.PacsSummary;
import com.hjw.wst.domain.Pacsdetail;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.DjtCustomerInfoService;
import com.hjw.wst.service.examInfoService;
import com.hjw.zyb.domain.ExamInfoExt;
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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hsqldb.lib.StringUtil;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;


/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: yangm
 * @date: 2016年7月4日 上午9:37:08
 * @version V2.0.0.0
 */

public class DjtCustomerInfoServiceImpl extends HibernateDaoSupport implements DjtCustomerInfoService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
    private CompanyService companyService;  
    private CustomerInfoService customerInfoService;    
    private BatchService batchService; 
    private DepartmentService departmentService;
    private JdbcPersistenceManager jpm;
    private examInfoService examInfoService;
    public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
  		this.jpm = jpm;
  	}
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
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

	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
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
	public String getDjtExamInfoOne(long userid,String centernum,ExamInfoUserDTO eu)throws ServiceException {
		String message = "error-保存失败";
		if("COMM".equals(eu.getIs_djt_type())){
			message = getExamInfoForFlagList_COMM(eu,userid,centernum);
		} else  {
			message = getExamInfoForFlagList_djt(eu,userid,centernum);
		}
		//message = getExamInfoForFlagList_djt(eu,userid,centernum);
		if (message.indexOf("ok") < 0) {
			message = "error-" + message;
		} 
		return message;
	}
	
	/**
	 * 
	     * @Title: getDjtTExamInfoOne   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userid
	     * @param: @param centerid
	     * @param: @param eu
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String getDjtTExamInfoOne(long userid,String centernum,ExamInfoUserDTO eu)throws ServiceException {
		String message = "error-保存失败";
		if("COMM".equals(eu.getIs_djt_type())){
			message = getExamInfoTForFlagList_COMM(eu,userid,centernum);
		} else {
			message = getExamInfoTForFlagList_djt(eu,userid,centernum);
		}
		if (message.indexOf("ok") < 0) { 
			message = "error-" + message;
		} 
		return message;
	}
	
	private String getExamInfoTForFlagList_djt(ExamInfoUserDTO eu, long userid,String centernum) throws ServiceException {
		String message = "ok-0-0";
		CompanyInfo cinfo = this.companyService.getComByID(eu.getCompany_id());
		if((cinfo==null)||(cinfo.getId()<=0)){
			message = "单位无效。";
			return message;
		}else{
			eu.setCompany(cinfo.getCom_Name());
		}
		
		if(eu.getBatch_id()<=0){
			message = "批次无效。";
			return message;
		}
		String idnum_notnull = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", centernum).getConfig_value();
		if("Y".equals(idnum_notnull) && "身份证".equals(eu.getIdtypename()) && StringUtil.isEmpty(eu.getId_num())){
			message = "证件号码不存在";
			return message;
		}
		String djttype="N";
		String idnum_num="";
		try {
			 djttype = this.customerInfoService.getCenterconfigByKey("IS_ID_CARD", centernum).getConfig_value();// 体检人员操作控制, Y 不可以同事操作、N 可以同事操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("Y".equals(djttype)){
			 idnum_num = ValidateUtil.IDCardValidatechenk(eu.getId_num().trim());
		}else{
			 idnum_num = ValidateUtil.IDCardValidate(eu.getId_num().trim());
		}
		if(!StringUtil.isEmpty(eu.getId_num()) && !"".equals(idnum_num) && "身份证".equals(eu.getIdtypename())){
			message = idnum_num;
			return message;
		}
		if (((eu.getId_num() == null) || (!"".equals(idnum_num))) && (eu.getAge() <= 0)
				&& ((eu.getBirthday() == null) || (eu.getBirthday().trim().length() <= 0))) {
			message = "证件号码、生日、年龄都不存在";
			return message;
		}

		if ((eu.getUser_name() == null) || (eu.getUser_name().trim().length() <= 0)) {
			message = "姓名不能为空";
			return message;
		}
//
//			if ((eu.getId_num() != null) && (eu.getId_num().trim().length() == 18)) {
//				List<ExamInfoDTO> eilist = new ArrayList<ExamInfoDTO>();
//				eilist = this.customerInfoService.getExamInfoForIdNumList(eu.getId_num().trim());
//				if((eilist!=null)&&(eilist.size()>0)){
//					ExamInfoDTO cusinfoold= eilist.get(0);
//					eu.setCustomer_id(cusinfoold.getId());
//						String bird = eu.getId_num().trim().substring(6, 14);
//						Date da = Timeutils.strToDateLong2(bird);
//						bird = DateTimeUtil.shortFmt3(da);
//						eu.setBirthday(bird);
//						eu.setAge(Timeutils.getDayForOld(bird));
//					
//				}
//			}else if((eu.getId_num() != null) && (eu.getId_num().trim().length() == 15)){
//				List<ExamInfoDTO> eilist = new ArrayList<ExamInfoDTO>();
//				eilist = this.customerInfoService.getExamInfoForIdNumList(eu.getId_num().trim());
//				if((eilist!=null)&&(eilist.size()>0)){
//					ExamInfoDTO cusinfoold= eilist.get(0);
//					eu.setCustomer_id(cusinfoold.getId());
//						String bird = "19"+eu.getId_num().trim().substring(6,12);
//						Date da = Timeutils.strToDateLong2(bird);
//						bird = DateTimeUtil.shortFmt3(da);
//						eu.setBirthday(bird);
//						eu.setAge(Timeutils.getDayForOld(bird));
//				}          
//			}else if ((eu.getId_num() != null) && (eu.getId_num().length()>0) && (eu.getId_num().trim().length() != 18)&&(eu.getId_num().trim().length() != 15)) {
//				message = "身份证号位数不对";
//				return message;
//			} else if (!StringUtil.isEmpty(eu.getBirthday())) {
//				if (eu.getBirthday().trim().length() != 10) {
//					if (eu.getBirthday().trim().length() == 8) {
//						String bird = eu.getBirthday().trim();
//						Date da = Timeutils.strToDateLong2(bird);
//						bird = DateTimeUtil.shortFmt3(da);
//						eu.setBirthday(bird);
//					}else {
//						message = "出生日期格式错误";
//						return message;
//					}
//				}
//                if (eu.getBirthday().trim().length() == 10) {						
//						String bird = eu.getBirthday().trim().replace("-", "");
//						eu.setAge(Timeutils.getDayForOld(bird));
//
//				} 
//			} else if (eu.getAge() > 0) {
//				if (StringUtil.isEmpty(eu.getBirthday())) {
//					eu.setBirthday(DateTimeUtil.DateDiff2((int) (eu.getAge() * 365)));
//				}
//			}
			CustomerInfoDTO cus = null;
			if (!StringUtil.isEmpty(eu.getVisit_no())) {
				cus = getCustomerInfoByVisitNo(eu.getVisit_no());
			}
			 if ((eu.getArch_num() != null) && (eu.getArch_num().trim().length() > 0)) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForArchIdList(eu.getArch_num().trim());
				if (StringUtil.isEmpty(ei.getExam_num())) {
					
				} else {
					/*if ((!StringUtil.isEmpty(ei.getId_num())) && (!StringUtil.isEmpty(eu.getId_num()))
							&& (!ei.getId_num().trim().equals(eu.getId_num().trim()))) {
						message = "证件号不一致，档案库为：" + ei.getId_num().trim();
						return message;
					} else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
							&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
						message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
						return message;
					}*/ /*else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
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
			} else if (eu.getId_num() != null && !"".equals(eu.getId_num())) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForIdNums(eu.getId_num().trim());
				if ((!StringUtil.isEmpty(ei.getArch_num())) && (!StringUtil.isEmpty(eu.getArch_num()))
						&& (!ei.getArch_num().trim().equals(eu.getArch_num().trim()))) {
					message = "档案号不一致，档案库为：" + ei.getArch_num().trim();
					return message;
				} /*else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
						&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
					message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
					return message;
				} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
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
			} else if (cus != null) {
				message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
				return message;
			}
			if ((eu.getAge() <= 0) || (eu.getAge() > 120)) {
				message = "年龄无效";
				return message;
			}
			if("2".equals(eu.getApptype()) && eu.getAge()<18 ) {
				message = "未满18岁不得进行职业病体检";
				return message;
			}
			
			Connection connection = null;
			ExamInfoDTO eiold=new ExamInfoDTO();
			if(!StringUtils.isEmpty(eu.getExam_num())){
				eiold =  this.customerInfoService.getExamInfoForexamNum(eu.getExam_num());
			}
			// 插入数据库
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);				
				// 档案处理
				CustomerInfoDTO ci = new CustomerInfoDTO();
				long custmerid = 0 ;
				if ((eu.getBirthday() == null) || ("null".equals(eu.getBirthday())))
					eu.setBirthday("");
				if (eu.getCustomer_id()<=0) {
					// 插入档案库					
					String dah="";
					String cusflag="0";
						if(!StringUtil.isEmpty(eu.getId_num())){
						String isdah = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE", centernum).getConfig_value().trim();
						String shencheng = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE_SHENCHENG", centernum).getConfig_value().trim();
						if(("Y".equals(isdah))&&(!StringUtil.isEmpty(eu.getId_num()))){							
							DAHSendMessage dahsend= new DAHSendMessage();
							DAHCustomerBean dahcu = new DAHCustomerBean();
							dahcu.setCenter_num(centernum);
							dahcu.setBrid(eu.getBirthday());
							dahcu.setId_num(eu.getId_num());
							dahcu.setName(eu.getUser_name());
							dahcu.setSex(eu.getSex());
							DAHResBody tr=new DAHResBody();
							dah=batchService.GetCreateID("vipno", centernum);
							tr=dahsend.dahSend(dahcu,dah);
							if("ok".equals(tr.getRescode())){
								dah=tr.getIdnumber();
									cusflag="1";
								}else{
									if("Y".equals(shencheng)){
										dah=this.batchService.GetCreateID("vipno", centernum);
									} else {
										message = "档案号无效";
										return message;
									}
									cusflag="0";
								}
						}else{
							dah=this.batchService.GetCreateID("vipno", centernum);
						}
					}else{
						dah=this.batchService.GetCreateID("vipno", centernum);
					}
					
					if(StringUtil.isEmpty(eu.getArch_num())){
						eu.setArch_num(dah);
					}else{
						if(eu.getArch_num().startsWith("TJ")){
							eu.setArch_num(dah);
						}
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
					ci.setMembership_card(eu.getMembership_card());
					ci.setCenter_num(centernum);
					custmerid = this.customerInfoService.insertCustomerInfo(connection, ci);
					eu.setCustomer_id(custmerid);
					//message = message + "档案库新增加记录";
				
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
					ci.setMembership_card(eu.getMembership_card());
					this.customerInfoService.updateCustomerInfo(connection, ci);
			}
			if(StringUtils.isEmpty(eu.getExam_num())){
				// 体检信息表 插入
				ExamInfoDTO ei = new ExamInfoDTO();
				ei.setExam_num(this.batchService.GetCreateID("exam_no", centernum));
				ei.setCustomer_id(eu.getCustomer_id());
				ei.setCenter_num(centernum);
				ei.setIs_marriage(eu.getIs_marriage());
				ei.setAge(eu.getAge());
				ei.setStatus(eu.getStatus());
				ei.setExam_type("T");
				ei.setExam_indicator("T");
				ei.setIs_Active("Y");
				ei.setAddress(eu.getAddress());
				ei.setPhone(eu.getPhone());
				ei.setPicture_path(eu.getPicture_Path());
				ei.setExam_center_num(centernum);
				if(StringUtil.isEmpty(eu.getIs_after_pay()))
				{
					ei.setIs_after_pay("N");
				}else{
					ei.setIs_after_pay(eu.getIs_after_pay());
				}
				//ei.setIs_after_pay("N");
				ei.setAppointment("N");
				ei.setCompany(eu.getCompany());
				ei.setCreater(userid);
				ei.setUpdater(userid);
				if(null==eu.getData_source()||"".equals(eu.getData_source())){
					ei.setData_source("001");
				}else{
					ei.setData_source(eu.getData_source());
				}
				ei.setIs_sampled_directly("N");
				ei.setGetReportWay("");
				ei.setOrder_id("");
				ei.set_level(eu.get_level());
				ei.setPosition(eu.getPosition());
				ei.setCustomer_type_id(eu.getCustomer_type_id());
			    ei.setCustomer_type(eu.getCustomer_type());
				ei.setRemarke(eu.getRemarke());
				ei.setIntroducer(eu.getIntroducer());
				ei.setDegreeOfedu(eu.getDegreeOfedu());
				ei.setOthers(eu.getOthers());
				ei.setGroup_index(eu.getOthers());
				ei.setChargingType(eu.getChargingType());
				ei.setReportAddress(eu.getAddress());
				ei.setEmail(eu.getEmail());
				ei.setCompany_id(eu.getCompany_id());
				ei.setBatch_id(eu.getBatch_id());
				ei.setRegister_date(eu.getRegister_date());
				ei.setJoin_date(eu.getJoin_date());
				ei.setExam_times(eu.getExam_times());
				ei.setGroup_id(eu.getGroup_id());
				ei.setEmployeeID(eu.getEmployeeID());
				ei.setPast_medical_history(eu.getPast_medical_history());
				ei.setBudget_amount(eu.getBudget_amount());
				ei.setVisit_no(eu.getVisit_no());
				ei.setCustomerType(eu.getCustomerType());
				ei.setJoin_operator(eu.getJoin_operator());
				ei.setApptype(eu.getApptype());
				ei.setGetReportWay(eu.getGetReportWay());
//				ei.setExam_center_num(eu.getExam_center_num());
				int id = insertExamInfo(connection, ei);
			
				ei.setId(id);
				message = "ok-"+ei.getExam_num()+"-"+ei.getCompany_id();	  
				
				//message = message + "、体检库新增加了记录";
				
			    if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
					ExamInfoExt eie = new ExamInfoExt();
						eie.setArch_num(ci.getArch_num());
						eie.setCreater(userid);
						eie.setCreate_time(DateTimeUtil.getDateTime());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(ei.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
					   this.customerInfoService.insertExamInfoExt(connection, eie);
					}
			 }else{				 
				 eiold.setCustomer_id(eu.getCustomer_id());
				 eiold.setCenter_num(centernum);
				 eiold.setIs_marriage(eu.getIs_marriage());
				 eiold.setAge(eu.getAge());
				 eiold.setIs_Active("Y");
				 eiold.setAddress(eu.getAddress());
				 eiold.setPhone(eu.getPhone());
				 eiold.setCompany(eu.getCompany());
				 if(StringUtil.isEmpty(eu.getIs_after_pay()))
					{
					    eiold.setIs_after_pay("N");
					}else{
						eiold.setIs_after_pay(eu.getIs_after_pay());
					}
				 eiold.setUpdater(userid);
				 if(null==eu.getData_source()||"".equals(eu.getData_source())){
					 eiold.setData_source("001");
					}else{
					eiold.setData_source(eu.getData_source());
					}
				 eiold.set_level(eu.get_level());
				 eiold.setPosition(eu.getPosition());
				 eiold.setCustomer_type_id(eu.getCustomer_type_id());
				 eiold.setCustomer_type(eu.getCustomer_type());
				 eiold.setRemarke(eu.getRemarke());
				 eiold.setIntroducer(eu.getIntroducer());
				 eiold.setDegreeOfedu(eu.getDegreeOfedu());
				 eiold.setOthers(eu.getOthers());
				 eiold.setGroup_index(eu.getOthers());
				 eiold.setChargingType(eu.getChargingType());
				 eiold.setReportAddress(eu.getAddress());
				 eiold.setEmail(eu.getEmail());
				 eiold.setCompany_id(eu.getCompany_id());
				 eiold.setBatch_id(eu.getBatch_id());
				 eiold.setRegister_date(eu.getRegister_date());
				 eiold.setJoin_date(eu.getJoin_date());
				 eiold.setGroup_id(eu.getGroup_id());
				 eiold.setEmployeeID(eu.getEmployeeID());
				 eiold.setPicture_path(eu.getPicture_Path());
				 eiold.setPast_medical_history(eu.getPast_medical_history());
				 eiold.setBudget_amount(eu.getBudget_amount());
				 eiold.setVisit_no(eu.getVisit_no());
				 eiold.setCustomerType(eu.getCustomerType());
				 eiold.setGetReportWay(eu.getGetReportWay());
				 updateExamInfo(connection, eiold);
				 message = "ok-"+eiold.getExam_num()+"-"+eiold.getCompany_id();	 
				
					if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
					ExamInfoExt eie = new ExamInfoExt();
					eie=this.getExamInfoExtForNum(eu.getExam_num());
					if((eie==null)||(StringUtil.isEmpty(eie.getExam_num()))){
						eie.setArch_num(ci.getArch_num());
						eie.setCreater(userid);
						eie.setCreate_time(DateTimeUtil.getDateTime());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(eu.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
					   this.customerInfoService.insertExamInfoExt(connection, eie);
					}else{
						eie.setArch_num(ci.getArch_num());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(eu.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
						this.customerInfoService.updateExamInfoExt(connection, eie);							 
					}
					}
					if(!StringUtil.isEmpty(eu.getVisit_no()) && cus == null){
						String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
								+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
								+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
						connection.createStatement().executeUpdate(ssq);
					}
					String sql = "update pacs_summary set examinfo_name='"
							+ ci.getUser_name() + "',examinfo_sex='" +ci.getSex() + "',examinfo_birthday='" 
							+ ci.getBirthday() + "' where examinfo_num='"+eiold.getExam_num()+"'";
				    connection.createStatement().executeUpdate(sql);
			 }
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
	
	private String getExamInfoTForFlagList_COMM(ExamInfoUserDTO eu, long userid,String centernum) throws ServiceException {
		String message = "ok-0-0";
		CompanyInfo cinfo = this.companyService.getComByID(eu.getCompany_id());
		if((cinfo==null)||(cinfo.getId()<=0)){
			message = "单位无效。";
			return message;
		}else{
			eu.setCompany(cinfo.getCom_Name());
		}
		
		if(eu.getBatch_id()<=0){
			message = "批次无效。";
			return message;
		}
		String idnum_notnull = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", centernum).getConfig_value();
		if("Y".equals(idnum_notnull) && "身份证".equals(eu.getIdtypename()) && StringUtil.isEmpty(eu.getId_num())){
			message = "证件号码不存在";
			return message;
		}
		String djttype="N";
		String idnum_num="";
		try {
			 djttype = this.customerInfoService.getCenterconfigByKey("IS_ID_CARD", centernum).getConfig_value();// 体检人员操作控制, Y 不可以同事操作、N 可以同事操作
		} catch (Exception e) {
//			e.printStackTrace();
		}
		if("Y".equals(djttype)){
			 idnum_num = ValidateUtil.IDCardValidatechenk(eu.getId_num().trim());
		}else{
			 idnum_num = ValidateUtil.IDCardValidate(eu.getId_num().trim());
		}
		if(!StringUtil.isEmpty(eu.getId_num()) && !"".equals(idnum_num) && "身份证".equals(eu.getIdtypename())){
			message = idnum_num;
			return message;
		}
		if ( "身份证".equals(eu.getIdtypename()) && ((eu.getId_num() == null) || (!"".equals(idnum_num))) && (eu.getAge() <= 0)
				&& ((eu.getBirthday() == null) || (eu.getBirthday().trim().length() <= 0))) {
			message = "证件号码、生日、年龄都不存在";
			return message;
		}

		if ((eu.getUser_name() == null) || (eu.getUser_name().trim().length() <= 0)) {
			message = "姓名不能为空";
			return message;
		}

		if("2".equals(eu.getApptype()) && eu.getAge()<18 ) {
			message = "未满18岁不得进行职业病体检";
			return message;
		}
//			if ((eu.getId_num() != null) && (eu.getId_num().trim().length() == 18) && "身份证".equals(eu.getIdtypename())) {
//				List<ExamInfoDTO> eilist = new ArrayList<ExamInfoDTO>();
//				eilist = this.customerInfoService.getExamInfoForIdNumList(eu.getId_num().trim());
//				if((eilist!=null)&&(eilist.size()>0)){
//					ExamInfoDTO cusinfoold= eilist.get(0);
//					eu.setCustomer_id(cusinfoold.getId());
//						String bird = eu.getId_num().trim().substring(6, 14);
//						Date da = Timeutils.strToDateLong2(bird);
//						bird = DateTimeUtil.shortFmt3(da);
//						eu.setBirthday(bird);
//						eu.setAge(Timeutils.getDayForOld(bird));
//					
//				}
//			}else if((eu.getId_num() != null) && (eu.getId_num().trim().length() == 15) && "身份证".equals(eu.getIdtypename())){
//				List<ExamInfoDTO> eilist = new ArrayList<ExamInfoDTO>();
//				eilist = this.customerInfoService.getExamInfoForIdNumList(eu.getId_num().trim());
//				if((eilist!=null)&&(eilist.size()>0)){
//					ExamInfoDTO cusinfoold= eilist.get(0);
//					eu.setCustomer_id(cusinfoold.getId());
//						String bird = "19"+eu.getId_num().trim().substring(6,12);
//						Date da = Timeutils.strToDateLong2(bird);
//						bird = DateTimeUtil.shortFmt3(da);
//						eu.setBirthday(bird);
//						eu.setAge(Timeutils.getDayForOld(bird));
//				}          
//			}else if ( "身份证".equals(eu.getIdtypename())&&(eu.getId_num() != null) && (eu.getId_num().length()>0) && (eu.getId_num().trim().length() != 18)&&(eu.getId_num().trim().length() != 15)) {
//				message = "身份证号位数不对";
//				return message;
//			} else if (!StringUtil.isEmpty(eu.getBirthday())) {
//				if (eu.getBirthday().trim().length() != 10) {
//					if (eu.getBirthday().trim().length() == 8) {
//						String bird = eu.getBirthday().trim();
//						Date da = Timeutils.strToDateLong2(bird);
//						bird = DateTimeUtil.shortFmt3(da);
//						eu.setBirthday(bird);
//					}else {
//						message = "出生日期格式错误";
//						return message;
//					}
//				}
//                if (eu.getBirthday().trim().length() == 10) {						
//						String bird = eu.getBirthday().trim().replace("-", "");
//						eu.setAge(Timeutils.getDayForOld(bird));
//
//				} 
//			} else if (eu.getAge() > 0) {
//				if (StringUtil.isEmpty(eu.getBirthday())) {
//					eu.setBirthday(DateTimeUtil.DateDiff2((int) (eu.getAge() * 365)));
//				}
//			}
			CustomerInfoDTO cus = null;
			if (!StringUtil.isEmpty(eu.getVisit_no())) {
				cus = getCustomerInfoByVisitNo(eu.getVisit_no());
			}
			if ((eu.getArch_num() != null) && (eu.getArch_num().trim().length() > 0)) {
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
					}*/ /*else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
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
			} else if (eu.getId_num() != null && !"".equals(eu.getId_num())) {
				ExamInfoDTO ei = new ExamInfoDTO();
				ei = getExamInfoForIdNums(eu.getId_num().trim());
				if ((!StringUtil.isEmpty(ei.getArch_num())) && (!StringUtil.isEmpty(eu.getArch_num()))
						&& (!ei.getArch_num().trim().equals(eu.getArch_num().trim()))) {
					message = "档案号不一致，档案库为：" + ei.getArch_num().trim();
					return message;
				} /*else if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
						&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
					message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
					return message;
				} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
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
			} else  if (cus != null) {
				message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
				return message;
			}

			if ((eu.getAge() <= 0) || (eu.getAge() > 120)) {
				message = "年龄无效";
				return message;
			}			
			
			Connection connection = null;
			ExamInfoDTO eiold=new ExamInfoDTO();
			if(!StringUtils.isEmpty(eu.getExam_num())){
				eiold =  this.customerInfoService.getExamInfoForexamNum(eu.getExam_num());
			}
			// 插入数据库
			try {
				// 读取记录数
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);				
				// 档案处理
				CustomerInfoDTO ci = new CustomerInfoDTO();
				long custmerid = 0 ;
				if ((eu.getBirthday() == null) || ("null".equals(eu.getBirthday())))
					eu.setBirthday("");
				if (eu.getCustomer_id()<=0) {
					// 插入档案库
					
					String dah="";
					String cusflag="0";
					if(!StringUtil.isEmpty(eu.getId_num())){
						String isdah = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE", centernum).getConfig_value().trim();
						String shengchen = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE_SHENCHENG", centernum).getConfig_value().trim();
						if(("Y".equals(isdah))&&(!StringUtil.isEmpty(eu.getId_num()))){							
							DAHSendMessage dahsend= new DAHSendMessage();
							DAHCustomerBean dahcu = new DAHCustomerBean();
							dahcu.setCenter_num(centernum);
							dahcu.setBrid(eu.getBirthday());
							dahcu.setId_num(eu.getId_num());
							dahcu.setName(eu.getUser_name());
							dahcu.setSex(eu.getSex());
							DAHResBody tr=new DAHResBody();
							dah=batchService.GetCreateID("vipno", centernum);
							tr=dahsend.dahSend(dahcu,dah);
							if("ok".equals(tr.getRescode())){
								dah=tr.getIdnumber();
									cusflag="1";
								}else{
									if("Y".equals(shengchen)){
										dah=this.batchService.GetCreateID("vipno", centernum);
									} else {
										message = "档案号无效";
										return message;
									}
									cusflag="0";
								}
						}else{
							dah=this.batchService.GetCreateID("vipno", centernum);
						}
					}else{
						dah=this.batchService.GetCreateID("vipno", centernum);
					}
					if(StringUtil.isEmpty(eu.getArch_num())){
						eu.setArch_num(dah);
					}else{
						if(eu.getArch_num().startsWith("TJ")){
							eu.setArch_num(dah);
						}
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
					ci.setCenter_num(centernum);
					custmerid = this.customerInfoService.insertCustomerInfo(connection, ci);
					eu.setCustomer_id(custmerid);
					//message = message + "档案库新增加记录";
				
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
					this.customerInfoService.updateCustomerInfo(connection, ci);
				}
				
				 if(StringUtils.isEmpty(eu.getExam_num())){
				// 体检信息表 插入
				ExamInfoDTO ei = new ExamInfoDTO();
				ei.setExam_num(this.batchService.GetCreateID("exam_no", centernum));
				ei.setCustomer_id(eu.getCustomer_id());
				ei.setCenter_num(centernum);
				ei.setIs_marriage(eu.getIs_marriage());
				ei.setAge(eu.getAge());
				ei.setStatus(eu.getStatus());
				ei.setExam_type("T");
				ei.setExam_indicator("T");
				ei.setIs_Active("Y");
				ei.setAddress(eu.getAddress());
				ei.setPhone(eu.getPhone());
				ei.setPicture_path(eu.getPicture_Path());
				ei.setExam_center_num(centernum);
				if(StringUtil.isEmpty(eu.getIs_after_pay()))
				{
					ei.setIs_after_pay("N");
				}else{
					ei.setIs_after_pay(eu.getIs_after_pay());
				}
				//ei.setIs_after_pay("N");
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
				ei.setIntroducer(eu.getIntroducer());
				ei.setDegreeOfedu(eu.getDegreeOfedu());
				ei.setOthers(eu.getOthers());
				ei.setGroup_index(eu.getOthers());
				ei.setChargingType(eu.getChargingType());
				ei.setReportAddress(eu.getAddress());
				ei.setEmail(eu.getEmail());
				ei.setCompany_id(eu.getCompany_id());
				ei.setBatch_id(eu.getBatch_id());
				ei.setRegister_date(eu.getRegister_date());
				ei.setJoin_date(eu.getJoin_date());
				ei.setExam_times(eu.getExam_times());
				ei.setGroup_id(eu.getGroup_id());
				ei.setEmployeeID(eu.getEmployeeID());
				ei.setPast_medical_history(eu.getPast_medical_history());
				ei.setBudget_amount(eu.getBudget_amount());
				ei.setVisit_no(eu.getVisit_no());
				ei.setCustomerType(eu.getCustomerType());
				ei.setJoin_operator(eu.getJoin_operator());
				ei.setZyb_set_source(eu.getZyb_set_source());
				ei.setApptype(eu.getApptype());
//				ei.setExam_center_num(eu.getExam_center_num());
				int id = insertExamInfo(connection, ei);
			
				ei.setId(id);
				if (id > 0) {
					String  sql = "select  * from exam_info where id = "+id;
					ResultSet executeQuery = connection.createStatement().executeQuery(sql);
					if (executeQuery.next()) {
						eu.setExam_num(executeQuery.getString("exam_num"));
					}
				}
				
				message = "ok-"+ei.getExam_num()+"-"+ei.getCompany_id();	  
				
				//message = message + "、体检库新增加了记录";
				
			    if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
					ExamInfoExt eie = new ExamInfoExt();
						eie.setArch_num(ci.getArch_num());
						eie.setCreater(userid);
						eie.setCreate_time(DateTimeUtil.getDateTime());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(ei.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
					   this.customerInfoService.insertExamInfoExt(connection, eie);
					}
			    
			 }else{				 
				 eiold.setCustomer_id(eu.getCustomer_id());
				 eiold.setCenter_num(centernum);
				 eiold.setIs_marriage(eu.getIs_marriage());
				 eiold.setAge(eu.getAge());
				 eiold.setIs_Active("Y");
				 eiold.setAddress(eu.getAddress());
				 eiold.setPhone(eu.getPhone());
				 eiold.setCompany(eu.getCompany());
				 if(StringUtil.isEmpty(eu.getIs_after_pay()))
					{
					    eiold.setIs_after_pay("N");
					}else{
						eiold.setIs_after_pay(eu.getIs_after_pay());
					}
				 eiold.setUpdater(userid);
				 eiold.setData_source("001");
				 eiold.set_level(eu.get_level());
				 eiold.setPosition(eu.getPosition());
				 eiold.setCustomer_type_id(eu.getCustomer_type_id());
				 eiold.setCustomer_type(eu.getCustomer_type());
				 eiold.setRemarke(eu.getRemarke());
				 eiold.setIntroducer(eu.getIntroducer());
				 eiold.setDegreeOfedu(eu.getDegreeOfedu());
				 eiold.setOthers(eu.getOthers());
				 eiold.setGroup_index(eu.getOthers());
				 eiold.setChargingType(eu.getChargingType());
				 eiold.setReportAddress(eu.getAddress());
				 eiold.setEmail(eu.getEmail());
				 eiold.setCompany_id(eu.getCompany_id());
				 eiold.setBatch_id(eu.getBatch_id());
				 eiold.setRegister_date(eu.getRegister_date());
				 eiold.setJoin_date(eu.getJoin_date());
				 eiold.setGroup_id(eu.getGroup_id());
				 eiold.setEmployeeID(eu.getEmployeeID());
				 eiold.setPicture_path(eu.getPicture_Path());
				 eiold.setPast_medical_history(eu.getPast_medical_history());
				 eiold.setBudget_amount(eu.getBudget_amount());
				 eiold.setVisit_no(eu.getVisit_no());
				 eiold.setCustomerType(eu.getCustomerType());
				 eiold.setZyb_set_source(eu.getZyb_set_source());
				 updateExamInfo(connection, eiold);
				 
				 message = "ok-"+eiold.getExam_num()+"-"+eiold.getCompany_id();	 
				 					
					if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
					ExamInfoExt eie = new ExamInfoExt();
					eie=this.getExamInfoExtForNum(eu.getExam_num());
					if((eie==null)||(StringUtil.isEmpty(eie.getExam_num()))){
						eie.setArch_num(ci.getArch_num());
						eie.setCreater(userid);
						eie.setCreate_time(DateTimeUtil.getDateTime());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(eu.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
					   this.customerInfoService.insertExamInfoExt(connection, eie);
					}else{
						eie.setArch_num(ci.getArch_num());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(eu.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
						this.customerInfoService.updateExamInfoExt(connection, eie);							 
					}
					}
					if(!StringUtil.isEmpty(eu.getVisit_no()) && cus == null){
						String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
								+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
								+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
						connection.createStatement().executeUpdate(ssq);
					}
					String sql = "update pacs_summary set examinfo_name='"
							+ ci.getUser_name() + "',examinfo_sex='" +ci.getSex() + "',examinfo_birthday='" 
							+ ci.getBirthday() + "' where examinfo_num='"+eiold.getExam_num()+"'";
				    connection.createStatement().executeUpdate(sql);
			 }
				 
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
	 * @throws ParseException 
	 * 
	     * @Title: getExamInfoForFlagList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eu
	     * @param: @param userid
	     * @param: @param centerid
	     * @param: @param batch_id
	     * @param: @param com_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	private String getExamInfoForFlagList_djt(ExamInfoUserDTO eu, long userid,String centernum) throws ServiceException{
		String message = "ok-0";			
		String idnum_notnull = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", centernum).getConfig_value();
		if("Y".equals(idnum_notnull) && "身份证".equals(eu.getIdtypename()) && StringUtil.isEmpty(eu.getId_num())){
			message = "证件号码不存在";
			return message;
		}
		String djttype="N";
		String idnum_num="";
		try {
			 djttype = this.customerInfoService.getCenterconfigByKey("IS_ID_CARD", centernum).getConfig_value();// 体检人员操作控制, Y 不可以同事操作、N 可以同事操作
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("Y".equals(djttype)){
			 idnum_num = ValidateUtil.IDCardValidatechenk(eu.getId_num().trim());
		}else{
			 idnum_num = ValidateUtil.IDCardValidate(eu.getId_num().trim());
		}
		if(!StringUtil.isEmpty(eu.getId_num()) && !"".equals(idnum_num) && "身份证".equals(eu.getIdtypename())){
			message = idnum_num;
			return message;
		}
		if (((eu.getId_num() == null) || (eu.getId_num().trim().length() != 18 && eu.getId_num().trim().length()!=15)) && (eu.getAge() <= 0)
				&& ((eu.getBirthday() == null) || (eu.getBirthday().trim().length() <= 0))) {
			message = "证件号码、生日、年龄都不存在";
			return message;
		}

		if ((eu.getUser_name() == null) || (eu.getUser_name().trim().length() <= 0)) {
			message = "姓名不能为空";
			return message;
		}

		CustomerInfoDTO cus = null;
		if (!StringUtil.isEmpty(eu.getVisit_no())) {
			cus = getCustomerInfoByVisitNo(eu.getVisit_no());
		}
		if ((eu.getArch_num() != null) && (eu.getArch_num().trim().length() > 0)) {
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
				}else if (cus != null && cus.getId() != ei.getId()) {
					message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
					return message;
				} else */ {
					eu.setCustomer_id(ei.getId());
				}
			}
		} else if (eu.getId_num() != null && !"".equals(eu.getId_num()) ) {
			ExamInfoDTO ei = new ExamInfoDTO();
			ei = getExamInfoForIdNums(eu.getId_num().trim());
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
			}*/ else if (cus != null && cus.getId() != ei.getId()) {
				message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
				return message;
			} else {
				if (ei.getId() > 0) {
					eu.setCustomer_id(ei.getId());
				}
			}
		} else  if (cus != null) {
			message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
			return message;
		}

		if ((eu.getAge() <= 0) || (eu.getAge() > 120)) {
			message = "年龄无效";
			return message;
		}
		if("2".equals(eu.getApptype()) && eu.getAge()<18 ) {
			message = "未满18岁不得进行职业病体检";
			return message;
		}
		
		 ExamInfoDTO eiold= new ExamInfoDTO();
		 if(!StringUtils.isEmpty(eu.getExam_num())){
			eiold =  this.customerInfoService.getExamInfoForexamNum(eu.getExam_num());
		}
		
		Connection connection = null;
		// 插入数据库
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			long custmerid = 0;
			// 档案处理
			CustomerInfoDTO ci = new CustomerInfoDTO();						
			if ((eu.getBirthday() == null) || ("null".equals(eu.getBirthday())))
				eu.setBirthday("");
			if (eu.getCustomer_id()<=0) {
				// 插入档案库
				String dah="";
				String cusflag="0";
					if(!StringUtil.isEmpty(eu.getId_num())){
					String isdah = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE", centernum).getConfig_value().trim();
					String shencheng = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE_SHENCHENG", centernum).getConfig_value().trim();
					if(("Y".equals(isdah))&&(!StringUtil.isEmpty(eu.getId_num()))){							
						DAHSendMessage dahsend= new DAHSendMessage();
						DAHCustomerBean dahcu = new DAHCustomerBean();
						dahcu.setCenter_num(centernum);
						dahcu.setBrid(eu.getBirthday());
						dahcu.setId_num(eu.getId_num());
						dahcu.setName(eu.getUser_name());
						dahcu.setSex(eu.getSex());
						DAHResBody tr=new DAHResBody();
						dah=batchService.GetCreateID("vipno", centernum);
						tr=dahsend.dahSend(dahcu,dah);
						if("ok".equals(tr.getRescode())){
							dah=tr.getIdnumber();
								cusflag="1";
							}else{
								if("Y".equals(shencheng)){
									dah=this.batchService.GetCreateID("vipno", centernum);
								} else {
									message = "档案号无效";
									return message;
								}
								cusflag="0";
							}
					}else{
						dah=this.batchService.GetCreateID("vipno", centernum);
					}
				}else{
					dah=this.batchService.GetCreateID("vipno", centernum);
				}
				
				if(StringUtil.isEmpty(eu.getArch_num())){
					eu.setArch_num(dah);
				}else{
					if(eu.getArch_num().startsWith("TJ")){
						eu.setArch_num(dah);
					}
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
				ci.setMembership_card(eu.getMembership_card());
				ci.setCenter_num(eu.getExam_center_num());
				custmerid = this.customerInfoService.insertCustomerInfo(connection, ci);
				eu.setCustomer_id(custmerid);
				//message = message + "档案库新增加记录";
			} else {
				// 修改档案库
				ci.setIdtype(eu.getIdtype());
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
				ci.setMembership_card(eu.getMembership_card());
				this.customerInfoService.updateCustomerInfo(connection, ci);
				//message = message + "档案库修改了记录";
			}

		if(StringUtils.isEmpty(eu.getExam_num())){
			// 体检信息表 插入
			ExamInfoDTO ei = new ExamInfoDTO();
			ei.setExam_num(this.batchService.GetCreateID("exam_no", centernum));
			ei.setCustomer_id(eu.getCustomer_id());
			ei.setCenter_num(centernum);
			ei.setIs_marriage(eu.getIs_marriage());
			ei.setAge(eu.getAge());
			ei.setStatus(eu.getStatus());
			ei.setExam_type(eu.getExam_type());
			ei.setExam_indicator("G");
			ei.setIs_Active("Y");
			ei.setAddress(eu.getAddress());
			ei.setPhone(eu.getPhone());
			ei.setPicture_path(eu.getPicture_Path());						
			ei.setAppointment("N");						
			ei.setCreater(userid);
			ei.setUpdater(userid);
			ei.setApptype(eu.getApptype());
			if(null==eu.getData_source()||"".equals(eu.getData_source())){
				ei.setData_source("001");
			}else{
				ei.setData_source(eu.getData_source());
			}
			ei.setIs_sampled_directly("N");
			ei.setGetReportWay("");
			ei.setOrder_id("");
			ei.set_level(eu.get_level());
			ei.setPosition(eu.getPosition());
			ei.setCustomer_type_id(eu.getCustomer_type_id());
		    ei.setCustomer_type(eu.getCustomer_type());
			ei.setRemarke(eu.getRemarke());
			ei.setIntroducer(eu.getIntroducer());
			ei.setDegreeOfedu(eu.getDegreeOfedu());
			ei.setOthers(eu.getOthers());
			ei.setGroup_index(eu.getOthers());
			ei.setChargingType(eu.getChargingType());
			ei.setReportAddress(eu.getAddress());
			ei.setEmail(eu.getEmail());
			ei.setCompany_id(eu.getCompany_id());
			ei.setBatch_id(eu.getBatch_id());
			ei.setPicture_path(eu.getPicture_Path());
			ei.setPast_medical_history(eu.getPast_medical_history());
			ei.setCompany(eu.getCompany());
			ei.setIs_after_pay(eu.getIs_after_pay());
			ei.setRegister_date(eu.getRegister_date());
			ei.setJoin_date(eu.getJoin_date());
			ei.setExam_times(eu.getExam_times());
			ei.setCustomerType(eu.getCustomerType());						
			
			ei.setPatient_id(eu.getPatient_id());
			ei.setVisit_date(eu.getVisit_date());
			ei.setVisit_no(eu.getVisit_no());
			ei.setClinic_no(eu.getClinic_no());
			ei.setEmployeeID(eu.getEmployeeID());
			ei.setMc_no(eu.getMc_no());
			ei.setBudget_amount(eu.getBudget_amount());
			ei.setGetReportWay(eu.getGetReportWay());
			ei.setExam_center_num(eu.getExam_center_num());						
			int id = this.insertExamInfo(connection, ei);
			
			eu.setExam_num(ei.getExam_num());
			ei.setId(id);
			//updateItemExam_indicator(connection,ei);
			message = "ok-"+ei.getExam_num();	                        
			
			//message = message + "、体检库新增加了记录";
		    
		    if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
				ExamInfoExt eie = new ExamInfoExt();
					eie.setArch_num(ci.getArch_num());
					eie.setCreater(userid);
					eie.setCreate_time(DateTimeUtil.getDateTime());
					eie.setDamage(0);
					eie.setEmployeeage(0);
					eie.setExam_num(ei.getExam_num());
					eie.setJoinDatetime("");
					eie.setOccusector("");
					eie.setOccusectorid(eu.getOccusectorid());
					eie.setOccutypeofwork("");
					eie.setOccutypeofworkid("");
					eie.setRemark("");
					eie.setPolitical_status(eu.getPolitical_status());
					eie.setUpdate_time(DateTimeUtil.getDateTime());
					eie.setUpdater(userid);
				   this.customerInfoService.insertExamInfoExt(connection, eie);
			}
		 }else{
			 eiold.setCustomer_id(eu.getCustomer_id());
			 eiold.setCenter_num(centernum);
			 eiold.setIs_marriage(eu.getIs_marriage());
			 eiold.setAge(eu.getAge());
			 eiold.setExam_type(eu.getExam_type());
			 eiold.setIs_Active("Y");
			 eiold.setAddress(eu.getAddress());
			 eiold.setPhone(eu.getPhone());
			 eiold.setCreater(userid);
			 eiold.setUpdater(userid);
			 if(null==eu.getData_source()||"".equals(eu.getData_source())){
				 eiold.setData_source("001");
				}else{
				 eiold.setData_source(eu.getData_source());
				}
			 eiold.set_level(eu.get_level());
			 eiold.setPosition(eu.getPosition());
			 eiold.setCustomer_type_id(eu.getCustomer_type_id());
			 eiold.setCustomer_type(eu.getCustomer_type());
			 eiold.setRemarke(eu.getRemarke());
			 eiold.setIntroducer(eu.getIntroducer());
			 eiold.setDegreeOfedu(eu.getDegreeOfedu());
			 eiold.setOthers(eu.getOthers());
			 eiold.setGroup_index(eu.getOthers());
			 eiold.setChargingType(eu.getChargingType());
			 eiold.setReportAddress(eu.getAddress());
			 eiold.setEmail(eu.getEmail());
			 eiold.setCompany_id(eu.getCompany_id());
			 eiold.setBatch_id(eu.getBatch_id());
			 eiold.setPicture_path(eu.getPicture_Path());
			 eiold.setPast_medical_history(eu.getPast_medical_history());
			 eiold.setCompany(eu.getCompany());
			 eiold.setIs_after_pay(eu.getIs_after_pay());
				//ei.setRegister_date(eu.getRegister_date());
				//ei.setJoin_date(eu.getJoin_date());
				//ei.setExam_times(eu.getExam_times());
			 eiold.setCustomerType(eu.getCustomerType());
			 eiold.setPatient_id(eu.getPatient_id());
			 eiold.setVisit_date(eu.getVisit_date());
			 eiold.setVisit_no(eu.getVisit_no());
			 eiold.setClinic_no(eu.getClinic_no());
			 eiold.setEmployeeID(eu.getEmployeeID());
			 eiold.setMc_no(eu.getMc_no());
			 eiold.setCustomerType(eu.getCustomerType());
			 eiold.setBudget_amount(eu.getBudget_amount());
			 eiold.setGetReportWay(eu.getGetReportWay());
				this.updateExamInfo(connection, eiold);
				//updateItemExam_indicator(connection,ei);
				
				
				message = "ok-"+eiold.getExam_num();
				
				
				if(!StringUtil.isEmpty(eu.getVisit_no()) && cus == null){
					String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
							+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
							+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
					connection.createStatement().executeUpdate(ssq);
				}
				String sql = "update pacs_summary set examinfo_name='"
						+ ci.getUser_name() + "',examinfo_sex='" +ci.getSex() + "',examinfo_birthday='" 
						+ ci.getBirthday() + "' where examinfo_num='"+eiold.getExam_num()+"'";
			    connection.createStatement().executeUpdate(sql);
			    
			    if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
					ExamInfoExt eie = new ExamInfoExt();
					eie=this.getExamInfoExtForNum(eu.getExam_num());
					if((eie==null)||(StringUtil.isEmpty(eie.getExam_num()))){
						eie.setArch_num(ci.getArch_num());
						eie.setCreater(userid);
						eie.setCreate_time(DateTimeUtil.getDateTime());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(eu.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
					   this.customerInfoService.insertExamInfoExt(connection, eie);
					}else{
						eie.setArch_num(ci.getArch_num());
						eie.setDamage(0);
						eie.setEmployeeage(0);
						eie.setExam_num(eu.getExam_num());
						eie.setJoinDatetime("");
						eie.setOccusector("");
						eie.setOccusectorid(eu.getOccusectorid());
						eie.setOccutypeofwork("");
						eie.setOccutypeofworkid("");
						eie.setRemark("");
						eie.setPolitical_status(eu.getPolitical_status());
						eie.setUpdate_time(DateTimeUtil.getDateTime());
						eie.setUpdater(userid);
						this.customerInfoService.updateExamInfoExt(connection, eie);							 
					}
					}
		 }
		
//			//
//			//就诊卡
//			//
//		   if(eu.getVisit_no()!=null && !"".equals(eu.getVisit_no())){
//				//1.就诊卡唯一
//			    String sql_wy = " SELECT ci.id,ci.id_num,cv.visit_no FROM   customer_info ci,customer_visit_card cv  "
//			    		+" WHERE  ci.id=cv.customer_id  and   visit_status='Y'  AND  cv.visit_no='"+eu.getVisit_no().trim()+"'   ";
//			    ResultSet rw = connection.createStatement().executeQuery(sql_wy);
//			    //就诊卡表有就诊卡
//			    if(rw.next()){
//			    	String id_num = rw.getString("id_num");
//			    	rw.close();
//			    	if(!(id_num.equals(eu.getId_num().trim()))){
//			    		//message = "就诊卡号冲突";
//			    	} else {
//			    		String vsql = "SELECT id,visit_no  FROM  customer_visit_card   where   customer_id='"+eu.getCustomer_id()+"'  AND   visit_no='"+eu.getVisit_no()+"'";
//						ResultSet res = connection.createStatement().executeQuery(vsql);
//						//根据档案号和就诊卡号查不到记录就新增就诊卡
//						if(!res.next()){
//							String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
//									+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
//									+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
//							connection.createStatement().executeUpdate(ssq);
//						} else {
//						//根据档案号和就诊卡号可以查到记录就更新修改时间
//							String update_s = " update customer_visit_card  set  update_time='"+DateTimeUtil.getDateTime()+"'  where   id ="+res.getLong("id");
//							connection.createStatement().executeUpdate(update_s);
//						}
//						res.close();
//			    	}
//			    //就诊卡表没有就诊卡号直接添加
//			    } else {
//			    	String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
//							+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
//							+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
//					connection.createStatement().executeUpdate(ssq);
//			    }
//			}
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
			/**
			 * COMM
			 */
			private String getExamInfoForFlagList_COMM(ExamInfoUserDTO eu, long userid,String centernum) throws ServiceException {
				String message = "ok-0";			
				
				String idnum_notnull = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", centernum).getConfig_value();
				if("Y".equals(idnum_notnull) && "身份证".equals(eu.getIdtypename()) && StringUtil.isEmpty(eu.getId_num())){
					message = "证件号码不存在";
					return message;
				}
				String djttype="N";
				String idnum_num="";
				try {
					 djttype = this.customerInfoService.getCenterconfigByKey("IS_ID_CARD", centernum).getConfig_value();// 体检人员操作控制, Y 不可以同事操作、N 可以同事操作
				} catch (Exception e) {
//					e.printStackTrace();
				}
				if("Y".equals(djttype)){
					 idnum_num = ValidateUtil.IDCardValidatechenk(eu.getId_num().trim());
				}else{
					 idnum_num = ValidateUtil.IDCardValidate(eu.getId_num().trim());
				}
				if(!StringUtil.isEmpty(eu.getId_num()) && !"".equals(idnum_num) && "身份证".equals(eu.getIdtypename())){
					message = idnum_num;
					return message;
				}
				
					if (((eu.getId_num() == null) || (eu.getId_num().trim().length() != 18 && eu.getId_num().trim().length()!=15)) && (eu.getAge() <= 0)
							&& ((eu.getBirthday() == null) || (eu.getBirthday().trim().length() <= 0))) {
						message = "证件号码、生日、年龄都不存在";
						return message;
					}

					if ((eu.getUser_name() == null) || (eu.getUser_name().trim().length() <= 0)) {
						message = "姓名不能为空";
						return message;
					}
					CustomerInfoDTO cus = null;
					if (!StringUtil.isEmpty(eu.getVisit_no())) {
						cus = getCustomerInfoByVisitNo(eu.getVisit_no());
					}
				String isEdit = "";
				CenterConfigurationDTO ce = this.customerInfoService.getCenterconfigByKey("IS_DJT_EDIT_ARCHINFO", centernum);
				if(ce != null){
					isEdit = ce.getConfig_value();
				}
				if ((eu.getArch_num() != null) && (eu.getArch_num().trim().length() > 0)) {
						ExamInfoDTO ei = new ExamInfoDTO();
						ei = getExamInfoForArchIdList(eu.getArch_num().trim());
						if ((ei == null) || (ei.getId() <= 0)) {
							
						} else {
							if(!"Y".equals(isEdit)) {
								if ((!StringUtil.isEmpty(ei.getId_num())) && (!StringUtil.isEmpty(eu.getId_num()))
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
								}
							}
							eu.setCustomer_id(ei.getId());
						}
					} else if (eu.getId_num() != null && !"".equals(eu.getId_num()) ) {
						ExamInfoDTO ei = new ExamInfoDTO();
						ei = getExamInfoForIdNums(eu.getId_num().trim());
						if(!"Y".equals(isEdit)) {
							if ((!StringUtil.isEmpty(ei.getUser_name())) && (!StringUtil.isEmpty(eu.getUser_name()))
									&& (!ei.getUser_name().trim().equals(eu.getUser_name()))) {
								message = "姓名不一致，档案库为：" + ei.getUser_name().trim();
								return message;
							} else if ((!StringUtil.isEmpty(ei.getSex())) && (!StringUtil.isEmpty(eu.getSex()))
									&& (!ei.getSex().trim().equals(eu.getSex()))) {
								message = "性别不一致，档案库为：" + ei.getSex().trim();
								return message;
							}
						}
						if ((!StringUtil.isEmpty(ei.getArch_num())) && (!StringUtil.isEmpty(eu.getArch_num()))
								&& (!ei.getArch_num().trim().equals(eu.getArch_num().trim()))) {
							message = "档案号不一致，档案库为：" + ei.getArch_num().trim();
							return message;
						} else if (cus != null && cus.getId() != ei.getId()) {
							message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
							return message;
						} else {
							if (ei.getId() > 0) {
								eu.setCustomer_id(ei.getId());
							}
						}
					} else  if (cus != null) {
						message = "就诊卡号已存在，档案库就诊卡号对应档案号为：" + cus.getArch_num() + ",姓名：" + cus.getUser_name();
						return message;
					}

					if ((eu.getAge() <= 0) || (eu.getAge() > 120)) {
						message = "年龄无效";
						return message;
					}
					if("2".equals(eu.getApptype()) && eu.getAge()<18 ) {
						message = "未满18岁不得进行职业病体检";
						return message;
					}
					
					 ExamInfoDTO eiold= new ExamInfoDTO();
					 if(!StringUtils.isEmpty(eu.getExam_num())){
						eiold =  this.customerInfoService.getExamInfoForexamNum(eu.getExam_num());
					}
					
					Connection connection = null;
					// 插入数据库
					try {
						// 读取记录数
						connection = this.jqm.getConnection();
						connection.setAutoCommit(false);
						long custmerid = 0;
						// 档案处理
						CustomerInfoDTO ci = new CustomerInfoDTO();						
						if ((eu.getBirthday() == null) || ("null".equals(eu.getBirthday())))
							eu.setBirthday("");
						if (eu.getCustomer_id()<=0) {
							// 插入档案库
							String dah="";
							String cusflag="0";
							if(!StringUtil.isEmpty(eu.getId_num())){
								String isdah = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE", centernum).getConfig_value().trim();
								String shencheng = this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE_SHENCHENG", centernum).getConfig_value().trim();
								if(("Y".equals(isdah))&&(!StringUtil.isEmpty(eu.getId_num()))){							
									DAHSendMessage dahsend= new DAHSendMessage();
									DAHCustomerBean dahcu = new DAHCustomerBean();
									dahcu.setCenter_num(centernum);
									dahcu.setBrid(eu.getBirthday());
									dahcu.setId_num(eu.getId_num());
									dahcu.setName(eu.getUser_name());
									dahcu.setSex(eu.getSex());
									DAHResBody tr=new DAHResBody();
									dah=batchService.GetCreateID("vipno", centernum);
									tr=dahsend.dahSend(dahcu,dah);
									if("ok".equals(tr.getRescode())){
										dah=tr.getIdnumber();
											cusflag="1";
										}else{
											if("Y".equals(shencheng)){
												dah=this.batchService.GetCreateID("vipno", centernum);
											} else {
												message = "档案号无效";
												return message;
											}
											cusflag="0";
										}
								}else{
									dah=this.batchService.GetCreateID("vipno", centernum);
								}
							}else{
								dah=this.batchService.GetCreateID("vipno", centernum);
							}
							if(StringUtil.isEmpty(eu.getArch_num())){
								eu.setArch_num(dah);
							}else{
								if(eu.getArch_num().startsWith("TJ")){
									eu.setArch_num(dah);
								}
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
							ci.setCenter_num(eu.getExam_center_num());
							custmerid = this.customerInfoService.insertCustomerInfo(connection, ci);
							eu.setCustomer_id(custmerid);
							//message = message + "档案库新增加记录";
						} else {
							// 修改档案库
							ci.setIdtype(eu.getIdtype());
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
							this.customerInfoService.updateCustomerInfo(connection, ci);
							//message = message + "档案库修改了记录";
						}

					if(StringUtils.isEmpty(eu.getExam_num())){
						// 体检信息表 插入
						ExamInfoDTO ei = new ExamInfoDTO();
						ei.setExam_num(this.batchService.GetCreateID("exam_no", centernum));
						ei.setCustomer_id(eu.getCustomer_id());
						ei.setCenter_num(centernum);
						ei.setIs_marriage(eu.getIs_marriage());
						ei.setAge(eu.getAge());
						ei.setStatus(eu.getStatus());
						ei.setExam_type(eu.getExam_type());
						ei.setExam_indicator("G");
						ei.setIs_Active("Y");
						ei.setAddress(eu.getAddress());
						ei.setPhone(eu.getPhone());
						ei.setPicture_path(eu.getPicture_Path());						
						ei.setAppointment("N");						
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
						ei.setIntroducer(eu.getIntroducer());
						ei.setDegreeOfedu(eu.getDegreeOfedu());
						ei.setOthers(eu.getOthers());
						ei.setGroup_index(eu.getOthers());
						ei.setChargingType(eu.getChargingType());
						ei.setReportAddress(eu.getAddress());
						ei.setEmail(eu.getEmail());
						ei.setCompany_id(eu.getCompany_id());
						ei.setBatch_id(eu.getBatch_id());
						ei.setPicture_path(eu.getPicture_Path());
						ei.setPast_medical_history(eu.getPast_medical_history());
						ei.setCompany(eu.getCompany());
						ei.setIs_after_pay(eu.getIs_after_pay());
						ei.setRegister_date(eu.getRegister_date());
						ei.setJoin_date(eu.getJoin_date());
						ei.setExam_times(eu.getExam_times());
						ei.setCustomerType(eu.getCustomerType());						
						
						ei.setPatient_id(eu.getPatient_id());
						ei.setVisit_date(eu.getVisit_date());
						ei.setVisit_no(eu.getVisit_no());
						ei.setClinic_no(eu.getClinic_no());
						ei.setEmployeeID(eu.getEmployeeID());
						ei.setMc_no(eu.getMc_no());
						ei.setBudget_amount(eu.getBudget_amount());
						ei.setGetReportWay(eu.getGetReportWay());
						ei.setExam_center_num(eu.getExam_center_num());
						ei.setApptype(eu.getApptype());
						int id = this.insertExamInfo(connection, ei);
						eu.setExam_num(ei.getExam_num());
						ei.setId(id);
						//updateItemExam_indicator(connection,ei);
						message = "ok-"+ei.getExam_num();	                        
						
						//message = message + "、体检库新增加了记录";
					    
					    if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
							ExamInfoExt eie = new ExamInfoExt();
								eie.setArch_num(ci.getArch_num());
								eie.setCreater(userid);
								eie.setCreate_time(DateTimeUtil.getDateTime());
								eie.setDamage(0);
								eie.setEmployeeage(0);
								eie.setExam_num(ei.getExam_num());
								eie.setJoinDatetime("");
								eie.setOccusector("");
								eie.setOccusectorid(eu.getOccusectorid());
								eie.setOccutypeofwork("");
								eie.setOccutypeofworkid("");
								eie.setRemark("");
								eie.setPolitical_status(eu.getPolitical_status());
								eie.setUpdate_time(DateTimeUtil.getDateTime());
								eie.setUpdater(userid);
							   this.customerInfoService.insertExamInfoExt(connection, eie);
						}
					 }else{
						 eiold.setCustomer_id(eu.getCustomer_id());
						 eiold.setCenter_num(centernum);
						 eiold.setIs_marriage(eu.getIs_marriage());
						 eiold.setAge(eu.getAge());
						 eiold.setExam_type(eu.getExam_type());
						 eiold.setIs_Active("Y");
						 eiold.setAddress(eu.getAddress());
						 eiold.setPhone(eu.getPhone());
						 eiold.setCreater(userid);
						 eiold.setUpdater(userid);
						 eiold.setData_source("001");
						 eiold.set_level(eu.get_level());
						 eiold.setPosition(eu.getPosition());
						 eiold.setCustomer_type_id(eu.getCustomer_type_id());
						 eiold.setCustomer_type(eu.getCustomer_type());
						 eiold.setRemarke(eu.getRemarke());
						 eiold.setIntroducer(eu.getIntroducer());
						 eiold.setDegreeOfedu(eu.getDegreeOfedu());
						 eiold.setOthers(eu.getOthers());
						 eiold.setGroup_index(eu.getOthers());
						 eiold.setChargingType(eu.getChargingType());
						 eiold.setReportAddress(eu.getAddress());
						 eiold.setEmail(eu.getEmail());
						 eiold.setCompany_id(eu.getCompany_id());
						 eiold.setBatch_id(eu.getBatch_id());
						 eiold.setPicture_path(eu.getPicture_Path());
						 eiold.setPast_medical_history(eu.getPast_medical_history());
						 eiold.setCompany(eu.getCompany());
						 eiold.setIs_after_pay(eu.getIs_after_pay());
							//ei.setRegister_date(eu.getRegister_date());
							//ei.setJoin_date(eu.getJoin_date());
							//ei.setExam_times(eu.getExam_times());
						 eiold.setCustomerType(eu.getCustomerType());
						 eiold.setPatient_id(eu.getPatient_id());
						 eiold.setVisit_date(eu.getVisit_date());
						 eiold.setVisit_no(eu.getVisit_no());
						 eiold.setClinic_no(eu.getClinic_no());
						 eiold.setEmployeeID(eu.getEmployeeID());
						 eiold.setMc_no(eu.getMc_no());
						 eiold.setBudget_amount(eu.getBudget_amount());
						 eiold.setGetReportWay(eu.getGetReportWay());
						 eiold.setaAddres(eu.getApptype());
							this.updateExamInfo(connection, eiold);
							//updateItemExam_indicator(connection,ei);
								
							message = "ok-"+eiold.getExam_num()+"-"+eiold.getExam_num();
							
														
							if(!StringUtil.isEmpty(eu.getVisit_no()) && cus == null){
								String ssq = "insert INTO  customer_visit_card(customer_id,visit_no,visit_date,visit_status,creater,create_time,updater,update_time)"
										+"	VALUES("+eu.getCustomer_id()+",'"+eu.getVisit_no()+"','"+DateTimeUtil.getDateTime()+"','Y',"+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"',"
										+ ""+ci.getCreater()+",'"+DateTimeUtil.getDateTime()+"'); ";
								connection.createStatement().executeUpdate(ssq);
							}
							String sql = "update pacs_summary set examinfo_name='"
									+ ci.getUser_name() + "',examinfo_sex='" +ci.getSex() + "',examinfo_birthday='" 
									+ ci.getBirthday() + "' where examinfo_num='"+eiold.getExam_num()+"'";
						    connection.createStatement().executeUpdate(sql);
						    
						    if(!StringUtil.isEmpty(eu.getOccusectorid()) || eu.getPolitical_status()!=0){
								ExamInfoExt eie = new ExamInfoExt();
								eie=this.getExamInfoExtForNum(eu.getExam_num());
								if((eie==null)||(StringUtil.isEmpty(eie.getExam_num()))){
									eie.setArch_num(ci.getArch_num());
									eie.setCreater(userid);
									eie.setCreate_time(DateTimeUtil.getDateTime());
									eie.setDamage(0);
									eie.setEmployeeage(0);
									eie.setExam_num(eu.getExam_num());
									eie.setJoinDatetime("");
									eie.setOccusector("");
									eie.setOccusectorid(eu.getOccusectorid());
									eie.setOccutypeofwork("");
									eie.setOccutypeofworkid("");
									eie.setRemark("");
									eie.setPolitical_status(eu.getPolitical_status());
									eie.setUpdate_time(DateTimeUtil.getDateTime());
									eie.setUpdater(userid);
								   this.customerInfoService.insertExamInfoExt(connection, eie);
								}else{
									eie.setArch_num(ci.getArch_num());
									eie.setDamage(0);
									eie.setEmployeeage(0);
									eie.setExam_num(eu.getExam_num());
									eie.setJoinDatetime("");
									eie.setOccusector("");
									eie.setOccusectorid(eu.getOccusectorid());
									eie.setOccutypeofwork("");
									eie.setOccutypeofworkid("");
									eie.setRemark("");
									eie.setPolitical_status(eu.getPolitical_status());
									eie.setUpdate_time(DateTimeUtil.getDateTime());
									eie.setUpdater(userid);
									this.customerInfoService.updateExamInfoExt(connection, eie);							 
								}
							}
					 }
						
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
			
			/**
			 * 插入体检表
			 * 
			 * @param cif
			 * @throws Exception
			 */
			public int insertExamInfo(Connection connection, ExamInfoDTO cif) throws Exception {
				
				String sql = "insert into exam_info(group_id,customer_id,exam_num,"
						+ "exam_type,is_Active,center_num,is_marriage,age,address,phone,"
						+ "is_after_pay,appointment,data_source,creater,create_time,updater,"
						+ "update_time,status,register_date,join_date,is_sampled_directly,getReportWay,"
						+ "_level,Position,Customer_type,Remarke,Others,company,customer_type_id,"
						+ "chargingType,reportAddress,email,company_id,batch_id,past_medical_history,picture_path,exam_times,customerType,"
						+ "patient_id,visit_date,visit_no,clinic_no,employeeID,mc_no,budget_amount,introducer,apptype,zyb_set_source,comon_report_type,exam_center_num) " + "values (" + cif.getGroup_id() + ","
						+ cif.getCustomer_id() + ",'" + cif.getExam_num() + "','" + cif.getExam_type() + "','"
						+ cif.getIs_Active() + "','" + cif.getCenter_num() + "','" + cif.getIs_marriage() + "'," + cif.getAge()
						+ ",'" + cif.getAddress() + "','" + cif.getPhone() + "','" + cif.getIs_after_pay() + "','"
						+ cif.getAppointment() + "','" + cif.getData_source() + "'," + cif.getCreater() + ",'"
						+ DateTimeUtil.getDateTime() + "'," + cif.getUpdater() + ",'" + DateTimeUtil.getDateTime() + "','"
						+ cif.getStatus() + "',";
				if(StringUtil.isEmpty(cif.getRegister_date()))
				{
					sql=sql+"null,";
				}else{
					sql=sql+"'"+cif.getRegister_date()+"',";
				}
				if(StringUtil.isEmpty(cif.getJoin_date()))
				{
					sql=sql+"null,";
				}else{
					sql=sql+"'"+cif.getJoin_date()+"',";
				}
				 sql=sql+"'" + cif.getIs_sampled_directly() + "','"
						+ cif.getGetReportWay() + "'" + ",'" + cif.get_level() + "','" + cif.getPosition() + "','"
						+ cif.getCustomer_type() + "','" + cif.getRemarke() + "','" + cif.getOthers() + "','" + cif.getCompany() + "',"
						+ cif.getCustomer_type_id()+",'" + cif.getChargingType() + "','" + cif.getReportAddress() 
						+ "','"+cif.getEmail()+"',"+cif.getCompany_id()+","+cif.getBatch_id()+",'"
						+cif.getPast_medical_history()+"','"+cif.getPicture_path()+"','"+cif.getExam_times()+"','"+cif.getCustomerType()+"','"+
						cif.getPatient_id()+"','"+DateTimeUtil.getDateTime()+"','"+cif.getVisit_no()+"','"+cif.getClinic_no()+"','"
						+cif.getEmployeeID()+"','"+cif.getMc_no()+"'";
					if(cif.getBudget_amount() == null){
						sql += ",null";
					}else{
						sql += ",'"+cif.getBudget_amount()+"'";
					}			
					sql += ",'"+cif.getIntroducer()+"','"+cif.getApptype()+"','"+cif.getZyb_set_source()+"','"+cif.getComon_report_type()+" ','"+cif.getExam_center_num()+"')";

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
						+ cif.getCustomer_id() + ",exam_num='" + cif.getExam_num() + "'," + "degreeOfedu='" + cif.getDegreeOfedu() + "',"
						+ "exam_type='" + cif.getExam_type() + "',is_Active='"
						+ cif.getIs_Active() + "',center_num='" + cif.getCenter_num() + "',is_marriage='" + cif.getIs_marriage() + "',age=" + 
						cif.getAge()
						+ ",address='" + cif.getAddress() + "',phone='" + cif.getPhone() + "',"
						+ "is_after_pay='" + cif.getIs_after_pay() + "',zyb_set_source=" + cif.getZyb_set_source() + ",appointment='"
						+ cif.getAppointment() + "',data_source='" + cif.getData_source() + "',updater=" + cif.getUpdater() + ","
						+ "update_time='" + DateTimeUtil.getDateTime() + "',status='"
						+ cif.getStatus() + "',register_date=";
						
				if(StringUtil.isEmpty(cif.getRegister_date()))
				{
					sql=sql+"null";
				}else{
					sql=sql+"'"+cif.getRegister_date()+"'";
				}
				if(StringUtil.isEmpty(cif.getJoin_date()))
				{
					sql=sql+",join_date=null";
				}else{
					sql=sql+",join_date ='"+cif.getJoin_date()+"'";
				}
						
						sql = sql + ",is_sampled_directly='" 
						+ cif.getIs_sampled_directly() + "',getReportWay='"+ cif.getGetReportWay() + "',"
						+ "_level='" + cif.get_level() + "',Position='" + cif.getPosition() + "',Customer_type='"
						+ cif.getCustomer_type() + "',Remarke='" + cif.getRemarke() + "',Others='" + cif.getOthers() + "'"
						+ ",customer_type_id="+cif.getCustomer_type_id()+",chargingType='" 
						+ cif.getChargingType() + "',reportAddress='" + cif.getReportAddress() + "',email='"+cif.getEmail()+"'"
						+ ",past_medical_history='"+cif.getPast_medical_history()
						+"',picture_path='"+cif.getPicture_path()+"',exam_times='"+cif.getExam_times()+"',customerType='"
						+cif.getCustomerType()+"',patient_id='"+cif.getPatient_id()+"',visit_date='"+DateTimeUtil.getDateTime()
						+"',visit_no='"+cif.getVisit_no()+"',clinic_no='"+cif.getClinic_no()+"',employeeID='"+cif.getEmployeeID()
						+"',mc_no='"+cif.getMc_no()+"',company='"+cif.getCompany()+"',introducer='"+cif.getIntroducer()+"' ,comon_report_type='"+cif.getComon_report_type()+"' ";
				if(cif.getBudget_amount() == null){
					sql += ",budget_amount=null ";
				}else{
					sql += ",budget_amount='"+cif.getBudget_amount()+"'";
				}		
					sql += " where exam_num='"+cif.getExam_num()+"'";
				//System.out.println(sql);
				connection.createStatement().executeUpdate(sql);
				
			    
			    int isUpload = queryIsUploadSynach(connection,cif.getExam_num());
				if(isUpload==1) {
					String synstr="delete web_synchro where dataid ='"+cif.getExam_num()+"' and datatype='5'";
					connection.createStatement().executeUpdate(synstr);
					synstr="insert web_synchro(datatype,dataid,create_time,synchro,synchrodate) values ('5','"+cif.getExam_num()+"','" + DateTimeUtil.getDateTime() + "','0','" + DateTimeUtil.getDateTime() + "')";
					connection.createStatement().executeUpdate(synstr);
				}
			}

			/**
			 * 
			     * @Title: updateItemExam_indicator   
			     * @Description: TODO(这里用一句话描述这个方法的作用)   
			     * @param: @param connection
			     * @param: @param cif
			     * @param: @throws Exception      
			     * @return: void      
			     * @throws
			 */
			public void updateItemExam_indicator(Connection connection, ExamInfoDTO cif) throws Exception {
				if((cif!=null)&&(cif.getChargingType()!=null)&&("165".equals(cif.getChargingType()))){
					String sql = "update examinfo_charging_item set exam_indicator='M' where isActive='Y' and exam_num="+cif.getExam_num();
					connection.createStatement().executeUpdate(sql);
				}else{
					String sql = "update examinfo_charging_item set exam_indicator='G' where isActive='Y' and exam_num="+cif.getExam_num();
					connection.createStatement().executeUpdate(sql);
				}
				
				
				
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
			public PageReturnDTO getExamSetList(String exam_num, int pagesize, int pageno,String center_num) throws ServiceException {
				String sql = "select a.set_num,a.set_name,a.set_pinyin,a.sex,"
						+ "a.set_discount,a.set_amount,b.id,b.examinfo_id,b.exam_set_id,"
						+ "b.discount,b.amount,b.isActive,b.final_exam_date,b.creater,"
						+ "b.create_time,b.updater,b.update_time,b.app_type"
						+ " From  exam_set a,examinfo_set b where a.is_Active='Y' and b.isActive='Y' "
						+ "  and a.id=b.exam_set_id and b.exam_num='"+exam_num+"' and a.center_num='"+center_num+"' "
						+ "and a.id=b.exam_set_id order by b.is_new_added,a.set_seq,b.update_time desc";
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
			     * @Title: getLisCountList   
			     * @Description: 获取lis 请求   
			     * @param: @param exam_id
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: PageReturnDTO      
			     * @throws
			 */
			public PageReturnDTO getLisCountList(String exam_num, String center_num) throws ServiceException {
/*				String sql = "select c.id,c.exam_num,m.demo_name,c.sample_barcode,b.item_code,b.item_name,c.status "
						+ "from examinfo_charging_item a,charging_item b,sample_exam_detail c,"
						+ "sample_demo m,examResult_chargingItem n "
						+ "where a.exam_num='"+exam_num+"' and a.isActive='Y' "
						+ " and a.pay_status<>'M' "
						+ " and a.charging_item_code=b.item_code "
						+ " and a.exam_num = c.exam_num "
						+ " and n.exam_id=c.id and n.charging_item_code=b.item_code "
						+ "  and a.exam_status<>'G' "
						+ "  and n.result_type='sample'"
						+ "   and b.sam_demo_id=c.sample_id and b.dep_category='131'"
						+ " and m.id=c.sample_id order by b.sam_demo_id,c.sample_barcode,c.status";*/
//				String sql = "select sd.id, sd.exam_num, d.demo_name, sd.sample_barcode, ci.item_code, ci.item_name, sd.status"
//						+ " from sample_exam_detail sd"
//						+ " inner join sample_demo d on d.id=sd.sample_id "
//						+ " inner join examResult_chargingItem eri on eri.bar_code=sd.sample_barcode"
//						+ " inner join examinfo_charging_item eci on eci.exam_num=sd.exam_num and eci.charging_item_code=eri.charging_item_code and eci.isActive='Y' "
//						+ " and eci.pay_status<>'M' "
//						+ " inner join charging_item ci on ci.item_code=eri.charging_item_code "
//						+ " where sd.exam_num='"+exam_num+"' "
//						+ " order by d.id, sd.sample_barcode, sd.status";
				
				
				String sql = "select sd.id, sd.exam_num, d.demo_name, sd.sample_barcode, ci.item_code, ci.item_name, sd.status"
						+ " from sample_exam_detail sd"
						+ " inner join sample_demo d on d.id=sd.sample_id "
						+ " inner join examResult_chargingItem eri on sd.sample_barcode = eri.bar_code "
						+ " inner join examinfo_charging_item eci on eci.exam_num=sd.exam_num and eci.charging_item_code=eri.charging_item_code and eci.isActive='Y' "
						+ " and eci.pay_status<>'M' and eci.center_num = '"+center_num+"' "
						+ " inner join charging_item ci on ci.item_code=eri.charging_item_code "
						+ " where sd.exam_num='"+exam_num+"' "
						+ " order by d.id, sd.sample_barcode, sd.status";
				
				PageSupport map = this.jqm.getList(sql, 1, 1000, LiscountDTO.class);
				TranLogTxt.liswriteEror_to_txt_single("登记台登记页面-打条码按钮查询条码信息-检验", sql);
				PageReturnDTO webrole = new PageReturnDTO();
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
				return webrole;
			}
			
			/**
			 * 
			     * @Title: getPacsCountList   
			     * @Description: 获取lis 请求   
			     * @param: @param exam_id
			     * @param: @return
			     * @param: @throws ServiceException      
			     * @return: PageReturnDTO      
			     * @throws
			 */
			public PageReturnDTO getPacsCountList(String exam_num, String center_num) throws ServiceException {
				String sql = "select a.id as charging_id,d.dep_name,b.item_code,b.item_name "
						+ "from examinfo_charging_item a,charging_item b,sample_demo m,department_dep d "
						+ "where a.exam_num='"+exam_num+"' "
						+ "and a.isActive='Y' "
						+ "and a.pay_status<>'M' "
						+ "and a.exam_status<>'G' and a.center_num = '"+center_num+"'"
						+ "and a.charging_item_code=b.item_code "
						+ "and b.dep_category in ('21','17') "
						+ "and b.dep_id=d.id and m.isPrint_BarCode='1' and  b.sam_demo_id=m.id and d.isPrint_Barcode = '0' order by d.seq_code,b.item_seq";
				PageSupport map = this.jqm.getList(sql, 1, 1000, PacsCountDTO.class);
				TranLogTxt.liswriteEror_to_txt_single("登记台登记页面-打条码按钮查询条码信息-检查", sql);
				PageReturnDTO webrole = new PageReturnDTO();
				webrole.setTotal(map.getRecTotal());
				webrole.setRows(map.getList());
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
	public List<ExaminfoSet> getExamInfoSetForId(String exam_num)throws ServiceException {
		String hql = " from ExaminfoSet g where g.exam_num='" + exam_num + "' and isActive='Y' ";
		List<ExaminfoSet> list= new ArrayList<ExaminfoSet>();
		list = qm.find(hql);
		return list;
	}
	
	/**
	 * 
	     * @Title: djtGdellissamp   
	     * @Description: 删除lis  
	     * @param: @param exam_id
	     * @param: @param sample_exam_detail_id
	     * @param: @param userid
	     * @param: @param centerNum      
	     * @return: void      
	     * @throws
	 */
	@SuppressWarnings("null")
	public void djtGdellissamp(String exam_num,long sample_exam_detail_id,long userid,String centerNum)throws ServiceException{
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			String sql  = "select a.id as chargid,c.id,c.sample_id,b.id as chargingid,c.sample_barcode,b.item_code,b.item_name,c.status "
					+ "from examinfo_charging_item a,charging_item b,sample_exam_detail c "
					+ "where a.exam_num='"+exam_num+"' "
					+ "and a.isActive='Y' "
					+ "and a.pay_status<>'M' "
					+ "and a.exam_status<>'G' and a.center_num = '"+centerNum+"'"
					+ "and a.charging_item_code=b.item_code "
					+ "and c.exam_num = a.exam_num "
					+ "and c.id="+sample_exam_detail_id+" "
					+ "and b.sam_demo_id=c.sample_id "
					+ "and c.status='Y'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			//long samexam_idold=sample_exam_detail_id;
			long sample_id=0;
			//long sample_idnew=0;
			List<Long> chargingids=new ArrayList<Long>();
			List<Long> chargids=new ArrayList<Long>();
			String sample_barcode="";
			while (rs.next()){
				chargids.add(rs.getLong("chargid"));
				sample_barcode = rs.getString("sample_barcode");
				sample_id = rs.getLong("sample_id");
				chargingids.add(rs.getLong("chargingid"));				
				String selsql="select c.id,c.sample_barcode,c.sample_id,c.status from sample_exam_detail c where c.id="+sample_exam_detail_id+" and c.status='Y' and c.sample_id="+sample_id+"";
				ResultSet rss = connection.createStatement().executeQuery(selsql);
				if (rss.next()) {
						sql = "select b.BarCode_Class from sample_demo b where b.id='" + sample_id
								+ "' and b.isActive='Y'";
						ResultSet rs1 = connection.createStatement().executeQuery(sql);
						if (rs1.next()) {
							int barcodeclass = rs1.getInt("BarCode_Class");
							if (barcodeclass == 0) {
								sql = "update sample_exam_detail set status='W' where id=" + sample_exam_detail_id;
								connection.createStatement().executeUpdate(sql);
							}
						}
						rs1.close();
				}
				rss.close();
			}
			rs.close();
			
			
			String selsql = "select c.id,c.sample_barcode,c.sample_id,c.status,c.exam_info_id from sample_exam_detail c where c.exam_num='"
					+ exam_num + "' and c.status='W' and c.sample_id=" + sample_id + " and c.sample_barcode<>'"+sample_barcode+"'";
			ResultSet rss = connection.createStatement().executeQuery(selsql);
			if (rss.next()) {
				//String sample_barcodenew= rss.getString("sample_barcode");
				long samexam_idnew = rss.getLong("id");
			    for(long chargingid:chargingids){		   
				   updateExamResultChargingItem(connection, chargingid, sample_exam_detail_id, samexam_idnew, userid);
				 
			    }
			    sql = "delete sample_exam_detail where id=" + sample_exam_detail_id;
				connection.createStatement().executeUpdate(sql); 
			} else {
			
					//String sample_barcodenew = this.batchService.GetCreateID("barcode", centerNum);
					//updateSampleExamDetail(connection, sample_exam_detail_id, sample_barcodenew, userid);
			}
         
			connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			try{
			  connection.rollback();
			}catch(Exception exd){}
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
	
	private void updateExamResultChargingItem(Connection connection,long chargingid,long samexam_id,long newsamexam_id,long userid) throws Exception{	
		String sql  = "update examResult_chargingItem set exam_id="+newsamexam_id+" ,creater=" 
		+userid + ",create_time='" 
		+ DateTimeUtil.getDateTime()
		+ "',updater=" + userid + ",update_time='" + DateTimeUtil.getDateTime() + "' where exam_id="
		+samexam_id+" and charging_id="+chargingid+"";
		connection.createStatement().executeUpdate(sql);
	}
	
	
	private void updateSampleExamDetail(Connection connection,long samexam_id,String sample_barcode,long userid) throws Exception{	
		String sql = "update sample_exam_detail set Sample_barcode='" + 
				sample_barcode + "',Status='W',creater=" +userid + ",create_time='" 
		+ DateTimeUtil.getDateTime()
		+ "',updater=" + userid + ",update_time='" + DateTimeUtil.getDateTime() + "' where id="	+ samexam_id + "";
        connection.createStatement().executeUpdate(sql);
	}
	
	private void delSampleExamDetail(Connection connection,long samexam_id) throws Exception{
		String sql = "delete sample_exam_detail  where id="	+ samexam_id + "";
        connection.createStatement().executeUpdate(sql);
	}
	

	/**
	 * 
	     * @Title: getdjtTItemCount   
	     * @Description: 登记台团体统计 
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DjtTotalDTO      
	     * @throws
	 */
	public DjtTotalDTO getdjtTItemCount(String exam_num, String center_num) throws ServiceException {
		DjtTotalDTO intArray =new DjtTotalDTO();
		if(!StringUtil.isEmpty(exam_num)) {
			ExamInfo eif = this.customerInfoService.getExamInfoForExamNum(exam_num);
			intArray.setExam_num(eif.getExam_num());
			Connection connection = null;
			try {
				connection = this.jqm.getConnection();
				//总个数
				String sql  = "select count(*) as counts from examinfo_charging_item b,charging_item c where b.isActive='Y' "
						+ "and b.center_num = '"+center_num+"'  and c.item_code=b.charging_item_code and b.pay_status<>'M' "
								+ "and  b.exam_num='"+exam_num+"'";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("counts");
					if((con==null)||("null".equals(con))){
						intArray.setCounts(0);
					}else{
						intArray.setCounts(rs.getInt("counts"));
					}				
				}
				rs.close();
				
				//团体应付费
				sql  = "select sum(b.team_pay) as team_pay,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y'  and c.item_code=b.charging_item_code and b.pay_status<>'M' "
						+ "and b.center_num = '"+center_num+"' and  b.exam_num='"+exam_num+"' and b.exam_indicator<>'M'";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("team_pay");
					if((con==null)||("null".equals(con))){
						intArray.setTermAmt(0);
					}else{
						intArray.setTermAmt(rs.getDouble("team_pay"));
					}				
				}
				rs.close();
				
				//个人应付费
				sql  = "select sum(b.personal_pay) as personal_pay,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y' and b.pay_status<>'M' and c.item_code=b.charging_item_code and  b.exam_num='"+exam_num+"' "
								+ "and b.exam_indicator<>'M' and b.center_num = '"+center_num+"' ";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("personal_pay");
					if((con==null)||("null".equals(con))){
						intArray.setPersonAmt(0);
					}else{
						intArray.setPersonAmt(rs.getDouble("personal_pay"));
					}				
				}
				rs.close();
				
				
				//个人已付费
				sql  = "select sum(b.personal_pay) as personal_pay,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y' and b.pay_status<>'M' and pay_status='Y'  and c.item_code=b.charging_item_code and  "
						+ "b.exam_num='"+exam_num+"' and b.exam_indicator<>'M' and b.center_num = '"+center_num+"'";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("personal_pay");
					if((con==null)||("null".equals(con))){
						intArray.setPersonYfAmt(0);
					}else{
						intArray.setPersonYfAmt(rs.getDouble("personal_pay"));
					}				
				}
				rs.close();
				
				//个人未付费
				sql  = "select sum(b.personal_pay) as personal_pay,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y' and b.pay_status<>'M' and c.item_code=b.charging_item_code and pay_status='N' "
						+ "and  b.exam_num='"+exam_num+"' and b.exam_indicator<>'M' and b.center_num = '"+center_num+"' ";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("personal_pay");
					if((con==null)||("null".equals(con))){
						intArray.setQfAmt(0);
					}else{
						intArray.setQfAmt(rs.getDouble("personal_pay"));
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
		return intArray;
	}


	/**
	 * 
	     * @Title: getdjtGItemCount   
	     * @Description: 登记台个人统计  
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DjtTotalDTO      
	     * @throws
	 */
	public DjtTotalDTO getdjtGItemCount(String exam_num, String center_num) throws ServiceException {
		DjtTotalDTO intArray =new DjtTotalDTO();
		if(!StringUtils.isEmpty(exam_num)) {
			ExamInfo eif = this.customerInfoService.getExamInfoForExamNum(exam_num);
			intArray.setExam_num(eif.getExam_num());
			Connection connection = null;
			try {
				connection = this.jqm.getConnection();
				//总个数
				String sql  = "select count(*) as counts from examinfo_charging_item b,charging_item c where b.isActive='Y'  "
						+ "and c.id=b.charge_item_id and b.pay_status<>'M' and  b.exam_num='"+exam_num+"' and b.center_num = '"+center_num+"'";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("counts");
					if((con==null)||("null".equals(con))){
						intArray.setCounts(0);
					}else{
						intArray.setCounts(rs.getInt("counts"));
					}				
				}
				rs.close();
				
				//应付费
				sql  = "select sum(b.amount) as amounts,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y' and b.pay_status<>'M' and c.item_code=b.charging_item_code and  "
						+ "b.exam_num='"+exam_num+"' and b.exam_indicator<>'M' and b.center_num = '"+center_num+"' ";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("amounts");
					if((con==null)||("null".equals(con))){
						intArray.setTotalAmt(0);
					}else{
						intArray.setTotalAmt(rs.getDouble("amounts"));
					}				
				}
				rs.close();
				
				//个人已付费
				sql  = "select sum(b.amount) as amount,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y' and b.pay_status<>'M' and c.item_code=b.charging_item_code and pay_status='Y' "
						+ "and  b.exam_num='"+exam_num+"' and b.exam_indicator<>'M' and b.center_num = '"+center_num+"'";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("amount");
					if((con==null)||("null".equals(con))){
						intArray.setPersonAmt(0);
					}else{
						intArray.setPersonAmt(rs.getDouble("amount"));
					}				
				}
				rs.close();
				
				//个人未付费
				sql  = "select sum(b.amount) as amount,count(*) from examinfo_charging_item b,charging_item c "
						+ "where b.isActive='Y' and b.pay_status<>'M' and c.item_code=b.charging_item_code and pay_status='N' "
						+ "and  b.exam_num='"+exam_num+"' and b.exam_indicator<>'M' and b.center_num = '"+center_num+"'";
				rs = connection.createStatement().executeQuery(sql);
				if(rs.next()){
					String con = rs.getString("amount");
					if((con==null)||("null".equals(con))){
						intArray.setQfAmt(0);
					}else{
						intArray.setQfAmt(rs.getDouble("amount"));
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
		return intArray;
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
	public int getIsnewaddedExamInfoSetForId(long exam_id)throws ServiceException {
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
	     * @Title: getIsGroupExamInfoSetForId   
	     * @Description: 获取 已经收费、已经发送申请的项目数量  
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getIsGroupExamInfoSetForId(String exam_num, String center_num)throws ServiceException {
		Connection connection = null;
		int newadd =0;
		try {
			connection = this.jqm.getConnection();
			String sql  = "select COUNT(*) as maxcount from examinfo_charging_item b where b.exam_num='"
			+exam_num+"' and ((b.pay_status='Y' and b.amount !=0.0) or b.is_application='Y' or exam_status='Y' or b.his_req_status='Y') "
					+ "and b.isActive='Y' and b.center_num = '"+center_num+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				String con = rs.getString("maxcount");
				if((con!=null)&&(!"null".equals(con))){
				   newadd=rs.getInt("maxcount");
				}				
			}
			rs.close();
			
			String sql1 = "select COUNT(*) as maxcountss from exam_info a where a.exam_num='" + exam_num
					+ "' and a.is_Active='Y' and a.status!='Y'";
			ResultSet rs1 = connection.createStatement().executeQuery(sql1);
			if (rs1.next()) {
				String con = rs1.getString("maxcountss");
				if((con!=null)&&(!"null".equals(con))){
					newadd = rs1.getInt("maxcountss")+newadd;
				}
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
		return newadd;
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
		Connection connection = null;
		int newadd =0;
		try {
			connection = this.jqm.getConnection();
			String sql  = "select MAX(is_new_added) as maxcount from examinfo_charging_item a where a.isActive='Y' "
					+ "and a.pay_status<>'M' and a.exam_status<>'G' and a.center_num = '"+center_num+"' and a.exam_num='"+exam_num+"'";
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
	     * @Title: getlisitemcode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @param ids
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public String getlisitemcode(String exam_num,String ids, String center_num)throws ServiceException {
		Connection connection = null;
		String itemcode="";
		try {
			connection = this.jqm.getConnection();
			String sql  = "select c.id,c.sample_id,b.id as chargingid,c.sample_barcode,b.item_code,"
					+ "b.item_name,c.status "
					+ "from examinfo_charging_item a,charging_item b,sample_exam_detail c "
					+ "where a.exam_num='"+exam_num+"' and a.isActive='Y' "
					+ "and a.charge_item_id=b.id and "
					+ "a.pay_status<>'M' "
					+ "and a.exam_status<>'G' and a.center_num = '"+center_num+"' "
					+ " and c.exam_num=a.exam_num "
					+ "and c.id in ("+ids+") "
					+ "and b.sam_demo_id=c.sample_id ";
					//+ "and c.status='W'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			
			while(rs.next()){
				itemcode =itemcode+","+ rs.getString("item_code")+"";				
			}
			rs.close();
			
			//if(itemcode.trim().length()>1) itemcode=itemcode.substring(1,itemcode.length());
				
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
		return itemcode;
	}
	/**
	 * 
	     * @Title: loadExamInfoSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoSet      
	     * @throws
	 */
	public ExaminfoSet loadExamInfoSet(long id)throws ServiceException {
		return  (ExaminfoSet)qm.load(ExaminfoSet.class,id);
	}
	
	public ExamInfo loadExamInfo(String exam_num)throws ServiceException {
		List<ExamInfo> list = this.qm.find("from ExamInfo e where e.exam_num = '"+exam_num+"'");
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo updateExamInfo(ExamInfo ei)throws ServiceException {
		pm.update(ei);
		return  ei;
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
	public List<ExaminfoChargingItem> getExaminfoChargingItemForId(String exam_num, String center_num)throws ServiceException {
		String hql = " FROM ExaminfoChargingItem g where g.exam_num='" + exam_num + "' and g.exam_status<>'G' and g.pay_status<>'M' "
				+ "and isActive='Y' and g.center_num = '"+center_num+"' ";
		List<ExaminfoChargingItem> list= new ArrayList<ExaminfoChargingItem>();
		list = qm.find(hql);
		return list;
	}
	
	/**
	 * 
	     * @Title: getExamInfoForMcNo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param mc_no
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfo>      
	     * @throws
	 */
	public List<ExamInfo> getExamInfoForMcNo(String mc_no)throws ServiceException {
		String hql = " from ExamInfo g where g.mc_no='" + mc_no + "' and g.status<>'Z' and g.is_Active='Y' order by g.id desc";
		List<ExamInfo> list= new ArrayList<ExamInfo>();
		list = qm.find(hql);
		return list;
	}
	
	
	/**
	 * 
	     * @Title: getExamInfoForIdNum   
	     * @Description: 身份证获取未终检的信息   
	     * @param: @param idnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExamInfo>      
	     * @throws
	 */
	public List<ExamInfo> getExamInfoForIdNum(String idnum)throws ServiceException {
		Connection connection = null;
		List<ExamInfo> list= new ArrayList<ExamInfo>();
		try {
			connection = this.jqm.getConnection();
			//总个数
			String sql  = "select a.id,a.exam_num from exam_info a,customer_info b where b.id_num='"+idnum+"'"
					+ " and b.is_Active='Y' and a.customer_id=b.id and a.is_Active='Y' and a.status<>'Z' order by a.id" ;
			ResultSet rs = connection.createStatement().executeQuery(sql);
			while(rs.next()){
				ExamInfo ei= new ExamInfo();
				ei.setId(rs.getLong("id"));
				ei.setExam_num(rs.getString("exam_num"));
				list.add(ei);
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
	     * @Title: getExamInfoForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfo      
	     * @throws
	 */
	public ExamInfo getExamInfoForId(long examid)throws ServiceException {
		String sql = "SELECT id,group_id,customer_id,exam_num,status,register_date,join_date,final_date,"
				+ "final_doctor,exam_type,customer_type,is_sampled_directly,is_adjusted,center_num,"
				+ "getReportWay,reportAddress,chargingType,customerType,group_index,is_Active,creater,"
				+ "create_time,updater,update_time,is_guide_back,company_check_status,customer_type_id,"
				+ "is_marriage,age,address,email,phone,company,"
				+ "position,_level,picture_path,is_after_pay,past_medical_history,remarke,introducer,"
				+ "counter_check,guide_nurse,appointment,data_source,others,order_id,exam_times,"
				+ "company_id,batch_id,is_report_print,patient_id,exam_indicator,clinic_no,employeeID,mc_no,visit_no,"
				+ "is_need_guide,is_need_barcode  FROM exam_info WHERE id='"+examid+"'";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExamInfo.class);
		ExamInfo ei= new ExamInfo();
		if((map.getList()!=null)&&(map.getList().size()>0)){
			ei=(ExamInfo)map.getList().get(0);
		}		
		return ei;
	}
	
	/**
	 * 
	     * @Title: getChargingSummarySingle   
	     * @Description: 通过体检编号获取未交付人员项目个数   
	     * @param: @param examid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int getChargingSummarySinglecount(String exam_num, String center_num)throws ServiceException {
		Connection connection = null;
		int summcount=0;
		try {
			connection = this.jqm.getConnection();
			//总个数
			String sql  = "select count(*) as counts from examinfo_charging_item b where b.isActive='Y'  and b.pay_status='N' "
					+ "and b.center_num = '"+center_num+"' and b.exam_num='"+exam_num+"'" ;
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if(rs.next()){
				String con = rs.getString("counts");
				if((con==null)||("null".equals(con))){
					summcount=0;
				}else{
					summcount=rs.getInt("counts");
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
		return summcount;
	}
	
	/**
	 * 
	     * @Title: loadExaminfoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExaminfoChargingItem      
	     * @throws
	 */
	public ExaminfoChargingItem loadExaminfoChargingItem(long id)throws ServiceException {
		return  (ExaminfoChargingItem)qm.load(ExaminfoChargingItem.class,id);
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
	public String djtChargeExamInfoGChangItem(ExaminfoSetDTO esdto,List<GroupChargingItemDTO> oldlistitem, List<GroupChargingItemDTO> listitem,String ypayitemcode,String indicator,long userid,String username,String centerNum) throws ServiceException {
		    ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(esdto.getExam_num());
		    String message = "error-";
		    if((ed==null)||(ed.getId()<=0)){
		    	message="error-无效体检人员";
		    }/*else if(!"Y".equals(ed.getStatus())){
		    	message="error-体检人员交费信息不能修改";
		    }*/else{
		        /*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
		        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
		        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
		        TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态  
                */		
		    	
		    	//删除原来的项目开始
		    	List<ExaminfoChargingItem> olditemlist= new ArrayList<ExaminfoChargingItem>();
		    	olditemlist = this.getExaminfoChargingItemForId(esdto.getExam_num(), centerNum);
		    	for (GroupChargingItemDTO es: oldlistitem) {
					ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());				    		

		    		for(ExaminfoChargingItem oldExaminfoChargingItem:olditemlist){
		    			if(oldExaminfoChargingItem.getCharge_item_id()==cd.getId()){
		    				oldExaminfoChargingItem.setCreater(userid);
		    				oldExaminfoChargingItem.setCreate_time(DateTimeUtil.parse());
		    				oldExaminfoChargingItem.setUpdater(userid);
		    				oldExaminfoChargingItem.setUpdate_time(DateTimeUtil.parse());
		    				oldExaminfoChargingItem.setIsActive("N");
		    				this.customerInfoService.updateExaminfoChargingItem(oldExaminfoChargingItem);
						}
		    		
		    		}
		        }
		    	
		    	//删除原来的项目结束
		    	
		    	//List<ExaminfoChargingItem> itemlist= new ArrayList<ExaminfoChargingItem>();
		    	//itemlist = this.getExaminfoChargingItemForId(esdto.getExaminfo_id());
		    	int isaddcount=getIsnewaddedExamInfoItemForId(esdto.getExam_num(), centerNum);
		    	
		    	for (int j = 0; j < listitem.size(); j++) {
		    		GroupChargingItemDTO es = new GroupChargingItemDTO();
					es = listitem.get(j);
					ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());
		    			if("G".equals(indicator)){
		    				es.setPersonal_pay(es.getAmount());
		    				es.setTeam_pay(0);
		    			}		    			
					    es.setCharge_item_id(cd.getId());
				      //新增 examinfo_charging_item、
				    	ExaminfoChargingItem eci =  new ExaminfoChargingItem();
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
	                    if("G".equals(es.getExam_indicators())){
	                    	eci.setPay_status("N");
	                    	eci.setTeam_pay_status("Y");
	                    }else if("GT".equals(es.getExam_indicators())){
	                    	eci.setPay_status("N");
	                    	eci.setTeam_pay_status("N");
	                    }else{
	                    	eci.setPay_status("R");
	                    	eci.setTeam_pay_status("N");
	                    }
	                    
	                    if(ypayitemcode.indexOf(es.getItem_code().trim())>-1)
	                    {
	                    	eci.setPay_status("Y");
	                    }
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
	                    eci.setItemnum(es.getItemnum());
	                    eci.setTeam_pay(es.getTeam_pay());
	                    eci.setPersonal_pay(es.getPersonal_pay());
	                    eci.setExam_num(es.getExam_num());
	                    eci.setCenter_num(centerNum);
	                    this.customerInfoService.saveExaminfoChargingItem(eci);
	                    //System.out.println(esdto.getExaminfo_id()+"   1   "+"   "+j);
	                    ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());
	                    int lisnum=this.customerInfoService.checkListItem(es.getItem_code(),centerNum);
	                    if(lisnum>0){
	                       SampleExamDetail sd = new SampleExamDetail();
	                       sd=this.customerInfoService.getSampleExamDetailforExamid(esdto.getExam_num(),ci.getSam_demo_id(),"W");
	                       long samexam_id=0;
	                       String sample_barcode = "";
	                       if((sd==null)||(sd.getId()<=0)){
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
	                    	   sed.setExam_num(sd.getExam_num());
	                    	   this.customerInfoService.saveSampleExamDetail(sed);
	                    	   samexam_id=sed.getId();
	                    	   //System.out.println(esdto.getExaminfo_id()+"   2   "+"   "+j);
	                       }else{
	                    	   samexam_id=sd.getId();
	                    	   sample_barcode = sd.getSample_barcode();
	                       }
	                       //System.out.println(esdto.getExaminfo_id()+"   3   "+"   "+j);
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
	                       this.customerInfoService.saveExamResultChargingItem(erci);
	                       //System.out.println(esdto.getExaminfo_id()+"   4   "+"   "+j);
	                    }
	                    
	                    int pacsnum=this.customerInfoService.checkPacsItem(es.getItem_code(),centerNum);
	                    //System.out.println(esdto.getExaminfo_id()+"   5   "+"   "+j);
	                    if (pacsnum>0){
	                    	long summary_id=0;
	                    	PacsSummary psd = new PacsSummary();
	                    	psd=this.customerInfoService.getPacsSummaryDTOforExamid(ed.getExam_num(),ci.getSam_demo_id(),"N");
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
	                    		this.customerInfoService.savePacsSummary(ps);
	                    		summary_id=ps.getId();
	                    		//System.out.println(esdto.getExaminfo_id()+"   7   "+"   "+j);
	                    	}else{
	                    		summary_id=psd.getId();
	                    	}
	                    	//System.out.println(esdto.getExaminfo_id()+"   8   "+"   "+j);
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
	                    	pd.setPacs_req_code(psd.getPacs_req_code());
	                    	this.customerInfoService.savePacsdetail(pd);
	                    	//System.out.println(esdto.getExaminfo_id()+"   9   "+"   "+j);
	                    }
	              }
		message="ok-体检人员交费信息处理完成"; 
		}
	return message;
	}
	
			/**
			 * 
			     * @Title: addIDSExamInfoChangItem  
			     * @Description:登记台增加个人人员收费项目
			     * @param: @param batch
			     * @param: @throws ServiceException      
			     * @return: void      
			     * @throws
			 */
			public String djtaddExamInfoGChangItem(ExaminfoSetDTO esdto, List<GroupChargingItemDTO> listitem,List<ExamSetDTO> listset,String indicator,String IS_CHARGINGWAY_ZERO,long userid,String username,String centerNum) throws ServiceException {
				    ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(esdto.getExam_num());
				    String message = "error-";
				    if(StringUtil.isEmpty(esdto.getExam_num())){
				    	message="error-无效体检人员";
				    }/*else if(!"Y".equals(ed.getStatus())){
				    	message="error-体检人员交费信息不能修改";
				    }*/else{
				        /*WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
				        HibernateTransactionManager txManager = (HibernateTransactionManager) wac.getBean("txManager");  
				        DefaultTransactionDefinition def = new DefaultTransactionDefinition();  
				        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);// 事物隔离级别，开启新事务  
				        TransactionStatus txStatus = txManager.getTransaction(def);// 获得事务状态  
                        */				    
				        // 删除人员对应套餐
				    	List<ExaminfoSet> list= new ArrayList<ExaminfoSet>();
				    	list=this.getExamInfoSetForId(esdto.getExam_num());
				    	int isaddsetcount = getIsnewaddedExamInfoSetForId(esdto.getExaminfo_id());

				    	//txManager.commit(txStatus);//事务处理成功，提交当前事务  
					    //增加人员对应套餐
				    	for (int j = 0; j < listset.size(); j++) {
				    		ExamSetDTO es = new ExamSetDTO();
							es = listset.get(j);
							ExamSetDTO oldExamSetDTO = this.batchService.findSetById(es.getSet_num(),centerNum);
				    		int flagset=0;
				    		for(ExaminfoSet oldExaminfoSet:list){
				    			if(oldExamSetDTO.getId()==oldExaminfoSet.getExam_set_id()){
									flagset=1;
									break;
								}	
				    		}
				    		
				    		if(flagset==0)
				    		{				    		
						      //新增 ExaminfoSet、
							ExaminfoSet newExaminfoSet =  new ExaminfoSet();
							newExaminfoSet.setExaminfo_id(esdto.getExaminfo_id());
							newExaminfoSet.setExam_set_id(oldExamSetDTO.getId());
							newExaminfoSet.setAmount(es.getSet_amount());
							newExaminfoSet.setExam_indicator(es.getExam_indicators());						
							newExaminfoSet.setCreater(userid);
							newExaminfoSet.setExam_set_id(oldExamSetDTO.getId());
							newExaminfoSet.setCreate_time(DateTimeUtil.parse());
							newExaminfoSet.setDiscount(es.getSet_discount());
							//newExaminfoSet.setExam_set_id(es.getId());
							newExaminfoSet.setIsActive("Y");
							newExaminfoSet.setFinal_exam_date(DateTimeUtil.parse());
							newExaminfoSet.setIs_new_added(isaddsetcount);
							newExaminfoSet.setUpdater(userid);
							newExaminfoSet.setUpdate_time(DateTimeUtil.parse());
							newExaminfoSet.setApp_type("1");
							newExaminfoSet.setExam_num(ed.getExam_num());
							this.customerInfoService.saveExaminfoSet(newExaminfoSet); 	
				    		}
					}
				   //人员套餐维护完毕 
				    	
				    	List<ExaminfoChargingItem> itemlist= new ArrayList<ExaminfoChargingItem>();
				    	itemlist = this.getExaminfoChargingItemForId(esdto.getExam_num(), centerNum);
				    	int isaddcount=getIsnewaddedExamInfoItemForId(esdto.getExam_num(), centerNum);
				    	                    	
				    	for (int j = 0; j < listitem.size(); j++) {
				    		GroupChargingItemDTO es = new GroupChargingItemDTO();
							es = listitem.get(j);
							ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());				    		
				    		int flagitem=0;

				    		for(ExaminfoChargingItem oldExaminfoChargingItem:itemlist){
				    			if(oldExaminfoChargingItem.getCharge_item_id()==cd.getId()){
				    				flagitem=1;
				    				break;
								}
				    		}
				    		
				    		if(flagitem==0){
				    			
				    			if("G".equals(indicator)){
				    				es.setPersonal_pay(es.getAmount());
				    				es.setTeam_pay(0);
				    				es.setExam_indicators(indicator);
				    			}
				    			
							    es.setCharge_item_id(cd.getId());
						      //新增 examinfo_charging_item、
						    	ExaminfoChargingItem eci =  new ExaminfoChargingItem();
			                    eci.setExaminfo_id(esdto.getExaminfo_id());
			                    eci.setCharge_item_id(cd.getId());
			                    /*if((ed.getChargingType()!=null)&&("165".equals(ed.getChargingType()))){
			                    	eci.setExam_indicator("M");
								}else{
									eci.setExam_indicator(es.getExam_indicators());
								}*/	
			                    eci.setItemnum(es.getItemnum());
			                    eci.setExam_indicator(es.getExam_indicators());
			                    eci.setItem_amount(es.getItem_amount());
			                    eci.setDiscount(es.getDiscount());
			                    eci.setAmount(es.getAmount());
			                    eci.setIsActive("Y");
			                    eci.setFinal_exam_date(DateTimeUtil.parse());
			                    eci.setExam_indicator(es.getExam_indicators());
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
			                    	/*if(es.getTeam_pay()==0)
			                    	{
			                    		eci.setTeam_pay_status("Y");
			                    	}else{
			                    	    eci.setTeam_pay_status("N");
			                    	}*/
			                   	                    
			                    eci.setExam_status("N");
			                    eci.setIntroducer(es.getIntroducer());
			                    eci.setIs_new_added(isaddcount);
			                    eci.setCreater(userid);
			                    eci.setCreate_time(DateTimeUtil.parse());
			                    eci.setUpdater(userid);
			                    eci.setUpdate_time(DateTimeUtil.parse());
			                    eci.setCheck_status(0);
			                    eci.setCalculation_amount(es.getAmount());
			                    eci.setItemnum(es.getItemnum());
			                    eci.setIs_application("N");
			                    eci.setChange_item("N");
			                    eci.setTeam_pay(es.getTeam_pay());
			                    eci.setPersonal_pay(es.getPersonal_pay());
			                    eci.setHis_req_status("N");
			                    eci.setApp_type("1");
			                    eci.setExam_num(ed.getExam_num());
			                    eci.setHis_charge_status("N");
			                    eci.setTj_charge_status("N");
			                    eci.setCharging_item_code(es.getItem_code());
			                    eci.setCenter_num(centerNum);
			                    this.customerInfoService.saveExaminfoChargingItem(eci);
			                    //System.out.println(esdto.getExaminfo_id()+"   1   "+"   "+j);
			                    ChargingItemDTO ci = this.batchService.findChargeItemById(es.getItem_code());
			                    int lisnum=this.customerInfoService.checkListItem(es.getItem_code(),centerNum);
			                    if(lisnum>0){
			                       SampleExamDetail sd = new SampleExamDetail();
			                       sd=this.customerInfoService.getSampleExamDetailforExamid(esdto.getExam_num(),ci.getSam_demo_id(),"W");
			                       long samexam_id=0;
			                       String sample_barcode = "";
			                       if((sd==null)||(sd.getId()<=0)){
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
			                    	   this.customerInfoService.saveSampleExamDetail(sed);
			                    	   samexam_id=sed.getId();
			                    	   //System.out.println(esdto.getExaminfo_id()+"   2   "+"   "+j);
			                       }else{
			                    	   samexam_id=sd.getId(); 
			                    	   sample_barcode = sd.getSample_barcode();
			                       }
			                       //System.out.println(esdto.getExaminfo_id()+"   3   "+"   "+j);
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
			                       this.customerInfoService.saveExamResultChargingItem(erci);
			                       //System.out.println(esdto.getExaminfo_id()+"   4   "+"   "+j);
			                    }
			                    
			                    int pacsnum=this.customerInfoService.checkPacsItem(es.getItem_code(),centerNum);
			                    //System.out.println(esdto.getExaminfo_id()+"   5   "+"   "+j);
			                    if (pacsnum>0){			                    	
			                    	long summary_id=0;
			                    	String pacsReqCode =  this.batchService.GetCreateID("pacs_req_num", centerNum);
			                    	PacsSummary psd = new PacsSummary();
			                    	psd=this.customerInfoService.getPacsSummaryDTOforExamid(ed.getExam_num(),ci.getSam_demo_id(),"N");
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
			                    		ps.setPacs_req_code(pacsReqCode);
			                    		ps.setSample_status("N");
			                    		this.customerInfoService.savePacsSummary(ps);			                    		
			                    		summary_id=ps.getId();
			                    	}else{
			                    		summary_id=psd.getId();
			                    		pacsReqCode = psd.getPacs_req_code();
			                    	}
			                    	//System.out.println(esdto.getExaminfo_id()+"   8   "+"   "+j);
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
			                    	pd.setPacs_req_code(pacsReqCode);
			                    	this.customerInfoService.savePacsdetail(pd);
			                    	//System.out.println(esdto.getExaminfo_id()+"   9   "+"   "+j);
			                    }	
				    		}
				    }
				this.customerInfoService.InsertWebSynchro('5', ed.getExam_num());
				message="ok-体检人员交费信息处理完成"; 
				}
			return message;
			}
			
			/**
			 * 
			     * @Title: addIDSExamInfoChangItem  
			     * @Description:登记台增加个人人员收费项目
			     * @param: @param batch
			     * @param: @throws ServiceException      
			     * @return: void      
			     * @throws
			 */
			public String djtTaddExamInfoDiscountChangItem(String exam_num,List<ExaminfoChargingItemDTO> listitem,String indicator,String IS_CHARGINGWAY_ZERO,long userid,String username,String centerNum) throws ServiceException {
				    ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(exam_num);
				    String message = "error-";
				    if((ed==null)||(ed.getId()<=0)){
				    	message="error-无效体检人员";
				    }/*else if(!"Y".equals(ed.getStatus())){
				    	message="error-体检人员交费信息不能修改";
				    }*/else{
				    	for (int j = 0; j < listitem.size(); j++) {
				    		ExaminfoChargingItemDTO es = new ExaminfoChargingItemDTO();
							es = listitem.get(j);
							ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());				    		
							String pay_Statuss=es.getPay_statuss();
							if(pay_Statuss.equals("未付费") || pay_Statuss=="预付费" ||pay_Statuss.equals("预付费")){
				    			es.setCharge_item_id(cd.getId());
							    //新增 examinfo_charging_item、
								Connection connection = null;
								try {
									connection = this.jqm.getConnection();
									//总个数
									String sql  = "select * from examinfo_charging_item where id="+es.getId();
									ResultSet rs = connection.createStatement().executeQuery(sql);
									if(rs.next()){
										double item_amount=es.getItem_amount();
										double amount=es.getAmount();
										int itemnum=es.getItemnum();
										double discount=es.getDiscount();
										double team_pay=es.getTeam_pay();
										double personal_pay=es.getPersonal_pay();
										String sql1="update  examinfo_charging_item set item_amount='"+item_amount+"',discount='"+discount+"',amount='"+amount+"',itemnum='"+itemnum+"',team_pay='"+team_pay+"',personal_pay='"+personal_pay+"' where id='"+es.getId()+"'";
					                    this.jpm.executeSql(sql1);
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
							}else{
								return message="error-已付费项目，设置折扣失败！";
							}
							message="ok-折扣设置完成"; 
				    	}
				    }
			return message;
			}
			
			/**
			 * 
			     * @Title: addIDSExamInfoChangItem  
			     * @Description:登记台增加个人人员收费项目
			     * @param: @param batch
			     * @param: @throws ServiceException      
			     * @return: void      
			     * @throws
			 */
			public String djtaddExamInfoDiscountChangItem(String exam_num, List<ExaminfoChargingItemDTO> listitem,String indicator,String IS_CHARGINGWAY_ZERO,long userid,String username,String centerNum) throws ServiceException {
				    ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(exam_num);
				    String message = "error-";
				    if((ed==null)||(ed.getId()<=0)){
				    	message="error-无效体检人员";
				    }else{
				    	for (int j = 0; j < listitem.size(); j++) {
				    		ExaminfoChargingItemDTO es = new ExaminfoChargingItemDTO();
							es = listitem.get(j);
							ChargingItemDTO cd =this.batchService.findChargeItemById(es.getItem_code());				    		
							String pay_Statuss=es.getPay_statuss();
							if(pay_Statuss.equals("未付费") ||pay_Statuss=="未付费"){
				    			if("G".equals(indicator)){
				    				es.setPersonal_pay(es.getAmount());
				    				es.setTeam_pay(0);
				    				es.setExam_indicators(indicator);
				    			}
							    es.setCharge_item_id(cd.getId());
						      //新增 examinfo_charging_item、
								Connection connection = null;
								try {
									connection = this.jqm.getConnection();
									//总个数
									String sql  = "select * from examinfo_charging_item where id="+es.getId();
									ResultSet rs = connection.createStatement().executeQuery(sql);
									if(rs.next()){
										/*ExaminfoChargingItem eci =  new ExaminfoChargingItem();
								    	eci.setId(rs.getLong("id"));
								    	eci.setItem_amount(es.getItem_amount());
								    	eci.setAmount(es.getAmount());
								    	eci.setItemnum(es.getItemnum());
								    	eci.setDiscount(es.getDiscount());
								    	eci.setExam_indicator(rs.getString("exam_indicator"));
								    	eci.setExam_status(rs.getString("exam_status"));
								    	eci.setExaminfo_id(rs.getLong("examinfo_id"));
								    	eci.setCharge_item_id(rs.getLong("charge_item_id"));
								    	eci.setIsActive(rs.getString("isActive"));
								    	eci.setFinal_exam_date(rs.getDate("final_exam_date"));
								    	eci.setCreater(userid);
						                eci.setCreate_time(DateTimeUtil.parse());
					                    eci.setUpdater(userid);
					                    eci.setUpdate_time(DateTimeUtil.parse());
					                    eci.setPay_status(rs.getString("pay_status"));
					                    eci.setExam_status(rs.getString("exam_status"));
					                    eci.setChange_item(rs.getString("change_item"));
					     
					                    this.pm.update(eci);	*/
										double item_amount=es.getItem_amount();
										double amount=es.getAmount();
										int itemnum=es.getItemnum();
										double discount=es.getDiscount();
										double team_pay=es.getTeam_pay();
										double personal_pay=es.getPersonal_pay();
										String sql1="update  examinfo_charging_item set item_amount='"+item_amount+"',discount='"+discount+"',amount='"+amount+"',itemnum='"+itemnum+"',team_pay='"+team_pay+"',personal_pay='"+personal_pay+"' where id='"+es.getId()+"'";
					                    this.jpm.executeSql(sql1);
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
				    }else{
				    	return message="error-已付费项目，设置折扣失败！";
				    }
				message="ok-折扣设置完成"; 
				
				    	}
				    }
			return message;
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
			public String djtdelExamInfoGChangSet(String exam_num,String ids,long userid,String center_num) throws ServiceException {
				    ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(exam_num);
				    String message = "error-";
				    if((ed==null)||(ed.getId()<=0)){
				    	message="error-无效体检人员";
				    }else if((!"Y".equals(ed.getStatus()))&&(!"D".equals(ed.getStatus()))){
				    	message="error-体检人员状态下不允许删除套餐";
				    }else{
				        
				        List<ExaminfoSet> list= new ArrayList<ExaminfoSet>();
				    	list=this.getExamInfoSetForId(exam_num);
				    	for(ExaminfoSet oldExaminfoSet:list){		    		
				    		
								ExamSetDTO oldExamSetDTO = this.batchService.findSetById(ids,center_num);
								if(oldExamSetDTO.getId()==oldExaminfoSet.getExam_set_id()){
									oldExaminfoSet.setIsActive("N");
									oldExaminfoSet.setUpdate_time(DateTimeUtil.parse());
									oldExaminfoSet.setUpdater(userid);
					    		    this.customerInfoService.updateExaminfoSet(oldExaminfoSet);
								}								
				    	
				    	}
				message="ok-体检人员交费信息减项完成"; 
				}
			return message;
			}
	
	/**
	 * 
	     * @Title: djtdelExamInfoGChangSet   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
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
	public String djtdelExamInfoGChangItem(String exam_num,String ids,long userid, String center_num) throws ServiceException {
	    ExamInfoDTO ed = this.customerInfoService.getCustExamInfoForexamId(exam_num);
	    String message = "error-";
	    if((ed==null)||(ed.getId()<=0)){
	    	message="error-无效体检人员";
	    }else if("Z".equals(ed.getStatus())){
	    	message="error-体检人员不能删除缴费项目";
	    }else{
	        String[] idslist=ids.trim().split(",");
	    	List<ExaminfoChargingItem> itemlist= new ArrayList<ExaminfoChargingItem>();
	    	itemlist = this.getExaminfoChargingItemForId(exam_num, center_num);
	    	for(ExaminfoChargingItem oldExaminfoChargingItem:itemlist){
	    		for (int j = 0; j < idslist.length; j++) {
					String item_code = idslist[j];
					ChargingItemDTO cd =this.batchService.findChargeItemById(item_code);
					if(oldExaminfoChargingItem.getCharge_item_id()==cd.getId()){
						if("N".equals(oldExaminfoChargingItem.getIs_application())){
							if((!"Y".equals(oldExaminfoChargingItem.getExam_status()))&&(!"Y".equals(oldExaminfoChargingItem.getPay_status()))){
						oldExaminfoChargingItem.setIsActive("N");
						oldExaminfoChargingItem.setUpdater(userid);
						oldExaminfoChargingItem.setUpdate_time(DateTimeUtil.parse());
		    		    this.customerInfoService.updateExaminfoChargingItem(oldExaminfoChargingItem);
							}
						}
					}
	    		}

	    }
	message="ok-体检人员缴费项目减项完成"; 
	}
  return message;
  }
	
	/**
	 * 
	     * @Title: djtdelRegister   
	     * @Description: 取消报到 
	     * @param: @param ids
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtdelRegister(String ids,long userid,String centernum) throws ServiceException {
		String[] idslist =ids.split(",");
		for (int j = 0; j < idslist.length; j++) {
			String examNum = idslist[j];		
	        ExamInfoDTO ed = this.customerInfoService.getExamInfoForexamNumD(examNum,centernum);
	        if((ed!=null)&&(!StringUtils.isEmpty(ed.getExam_num()))&&(ed.getFreeze()==0)){		    
	           ExamInfo ei =  this.loadExamInfo(ed.getExam_num());
	           ei.setStatus("Y");
	           ei.setJoin_date(null);
			   ei.setUpdater(userid);
			   ei.setUpdate_time(DateTimeUtil.getDateTime());
			   ei.setExam_center_num(null);
		       this.pm.update(ei);
			}
		}
	String message="ok-体检人员取消报到处理完成"; 
    return message;
  }
	
	/**
	 * 
	     * @Title: updatevipflag   设置
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid      
	     * @return: void      
	     * @throws
	 */
	public void updatevipflag(String exam_num, String center_num) {
		Connection connection = null;
		double vipamt = 5000;
		try {
			String IS_VISIT_AMOUNT = this.customerInfoService.getCenterconfigByKey("IS_VISIT_AMOUNT", center_num).getConfig_value()
					.trim();
			vipamt = Double.valueOf(IS_VISIT_AMOUNT);
		} catch (Exception ex) {

		}
		double amt = 0;
		try {
			connection = this.jqm.getConnection();
			if (StringUtil.isEmpty(exam_num)) {
				// 总个数
				String sql = "select sum(eci.personal_pay+eci.team_pay) as amt from examinfo_charging_item eci where "
						+ "eci.exam_num='" + exam_num + "' and eci.isActive='Y' and eci.pay_status<>'M' and eci.center_num = '"+center_num+"' ";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if (rs.next()) {
					amt = rs.getDouble("amt");
				}
				rs.close();
				if (amt >= vipamt) {
					connection.createStatement().execute("update exam_info set vipflag='1',isvisit='1' where exam_num='" + exam_num + "' ");
				}
			} else {
				String sql = "select sum(eci.personal_pay+eci.team_pay) as amt from examinfo_charging_item eci,exam_info ei "
						+ " where ei.exam_num='" + exam_num + "' and ei.exam_num=eci.exam_num and eci.isActive='Y' "
						+ "and eci.pay_status<>'M' and eci.center_num = '"+center_num+"' ";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if (rs.next()) {
					amt = rs.getDouble("amt");
				}
				rs.close();
				if (amt >= vipamt) {
					connection.createStatement()
							.execute("update exam_info set vipflag='1',isvisit='1' where exam_num='" + exam_num + "' ");
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
	}
	
	public void updateOutRegFlag(String exam_num,String center_num,ExamInfoDTO examInfo,long userid){
		String IS_OUT_REG = "N";
		try {
			IS_OUT_REG = this.customerInfoService.getCenterconfigByKey("IS_OUT_REG", center_num).getConfig_value().trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String sql = "select d.deviceCode as config_value,d.deviceName as center_name from dict_out_bus d where d.is_used = 1";
		List<CenterConfigurationDTO> list = this.jqm.getList(sql, CenterConfigurationDTO.class);
		String deviceCode = "";
		if(list.size() > 0){
			deviceCode = list.get(0).getConfig_value();
		}
		ExamInfoExt eie = this.getExamInfoExtForNum(exam_num);
		if(eie == null || eie.getExam_num() == null){
			String sql1 = "";
	  	    if("Y".equals(IS_OUT_REG)){
	  	    	sql1  ="insert into exam_ext_info(exam_num,arch_num,creater,create_time,ti_age,out_reg_flag,out_reg_deviceCode) "
	  	    		  + "values('"+exam_num+"','"+examInfo.getArch_num()+"','"+userid+"','"+DateTimeUtil.getDate3()+"','0','1','"+deviceCode+"')";
	  	    }else{
	  	    	sql1  ="insert into exam_ext_info(exam_num,arch_num,creater,create_time,ti_age,out_reg_flag,out_reg_deviceCode) "
		  	    	  + "values('"+exam_num+"','"+examInfo.getArch_num()+"','"+userid+"','"+DateTimeUtil.getDate3()+"','0','0','')";
	  	    }
	  	    this.jpm.executeSql(sql1);
		}else{
			String sql1 = "";
	  	    if("Y".equals(IS_OUT_REG)){
	  	    	sql1  ="update exam_ext_info set out_reg_flag = '1',out_reg_deviceCode='"+deviceCode+"' where exam_num ='" + exam_num + "' ";
	  	    }else{
	  	    	sql1  ="update exam_ext_info set out_reg_flag = '0',out_reg_deviceCode='' where exam_num ='" + exam_num + "' ";
	  	    }
	  	    this.jpm.executeSql(sql1);
		}
	}
	/**
	 * 
	     * @Title: examInfoStatusEdit   
	     * @Description: 登记台报到
	     * @param: @param examnum
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String examInfoStatusEdit(String examnum,long userid,String centernum) throws ServiceException {	
		String message="ok-体检人员报到完成";
		List<ExamInfoDTO> edto = new ArrayList<ExamInfoDTO>();
		edto = this.customerInfoService.getExamInfoForexamNum(examnum,"",centernum);
	        if(edto==null){
	        	message="error-体检人员查询失败";
	        }else if((edto==null)||(edto.size()<=0)){
	        	message="error-体检人员无需报到";
	        }else {
	           ExamInfoDTO eidto=edto.get(0);
	           if("Y".equals(eidto.getStatus())){
	        	   String sql  ="update exam_info set status='D'"
	   	        	   		+ ",join_date='"+DateTimeUtil.getDateTime()+"',updater='"+userid+"',"
	   	        	   		+ "update_time='"+DateTimeUtil.getDateTime()+"',exam_center_num='"+centernum+"' where exam_num ='" + examnum + "' ";
	        	   this.jpm.executeSql(sql);
	        	   updatevipflag(examnum, centernum);
	        	   updateOutRegFlag(examnum, centernum,eidto,userid);
			       message="ok-体检人员报到处理完成";  
	           }else{
	        	   message="error-体检人员不能取消报到";  
	           }	           
	        }
    return message;
  }
	
	/**
	 * 
	     * @Title: getExamInfoSign   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examnum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoSign      
	     * @throws
	 */
	public ExamInfoSign getExamInfoSign(String examnum) throws ServiceException {	
			List ls = qm.find("From ExamInfoSign Where exam_num ='"+examnum.trim()+"'");
			if(ls.size()>0)
				return (ExamInfoSign) ls.get(0);
			else
				return null;
	}
	
	/**
	 * 
	     * @Title: saveExamInfoSign   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoSign      
	     * @throws
	 */
	public ExamInfoSign saveExamInfoSign(ExamInfoSign eis) throws ServiceException {	
		pm.save(eis);
		return eis;
    }
	
	/**
	 * 
	     * @Title: updateExamInfoSign   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param eis
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoSign      
	     * @throws
	 */
	public ExamInfoSign updateExamInfoSign(ExamInfoSign eis) throws ServiceException {	
		pm.update(eis);
		return eis;
    }
	
	/**
	 * 
	     * @Title: getexamrepeatFornewid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param newexam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public String getexamrepeatFornewid(String newexam_num)throws ServiceException {
	 Connection connection = null;
	 String oldexam_num="";
	try {
		connection = this.jqm.getConnection();
		//总个数
		String sql  = "SELECT newexam_num,oldexam_num FROM exam_info_repeat where newexam_num='"+newexam_num+"' " ;
		ResultSet rs = connection.createStatement().executeQuery(sql);
		while(rs.next()){
			oldexam_num=rs.getString("oldexam_num");
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
	return oldexam_num;
	}
	
	/**
	 * 
	     * @Title: insertexamrepeat   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param newexamid
	     * @param: @param oldexamid
	     * @param: @param userid
	     * @param: @return
	     * @param: @throws Exception      
	     * @return: int      
	     * @throws
	 */
	public void insertexamrepeat(String newexam_num,String oldexam_num,long userid,String exam_num) throws Exception {
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			String sql = "insert into exam_info_repeat(newexam_num,oldexam_num,creater,create_time,remark,exam_num) " 
			           + "values (" + newexam_num + ","+ oldexam_num + "," + userid + ",'" + DateTimeUtil.getDateTime() + "','','"+exam_num+"')";
			connection.createStatement().executeUpdate(sql);
			
			sql = "update exam_info set counter_check='Y' where id= '" + newexam_num + "'";
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
	
	
	/**
	 * 
	     * @Title: getExamInfoForBatchIdandExamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoExt      
	     * @throws
	 */
	public ExamInfoExt getExamInfoExtForNum(String exam_num) throws ServiceException {
		String sql = "select exam_num,arch_num,occutypeofworkid,occusectorid,occusector,"
				+ "occutypeofwork,joinDatetime,employeeage,damage,remark,creater,create_time,"
				+ "updater,update_time FROM exam_ext_info where exam_num='" + exam_num + "'  ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoExt.class);
		ExamInfoExt webrole = new ExamInfoExt();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoExt) map.getList().get(0);
		}

		return webrole;
	}
	
	/**
	 * 
	     * @Title: getChargingItemForId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public List<ChargingItemDTO> getChargingItemForId(String exam_num, String center_num)throws ServiceException {
		String hql = "select a.id,a.item_code,a.item_name from Charging_Item a,Examinfo_Charging_Item g where a.item_code=g.charging_item_code "
				+ "and g.exam_num='" + exam_num + "' "
				+ "and g.exam_status<>'G' and g.pay_status<>'M' and g.isActive='Y' and g.center_num = '"+center_num+"' ";
		List<ChargingItemDTO> list= new ArrayList<ChargingItemDTO>();
		PageSupport map = this.jqm.getList(hql, 1, 1000, ChargingItemDTO.class);
		
		if((map!=null)&&(map.getList()!=null)){
			list=map.getList();
		}
		return list;
	}
	
	private CustomerInfoDTO getCustomerInfoByVisitNo(String visit_no) throws ServiceException {
		String sql = "select c.id,c.arch_num,c.user_name from customer_info c,customer_visit_card v "
				+ "where c.id = v.customer_id and v.visit_no = '" + visit_no + "'";
		List<CustomerInfoDTO> list = this.jqm.getList(sql, CustomerInfoDTO.class);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	private ExamInfoDTO getExamInfoForIdNums(String id_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where id_num ='" + id_num
				+ "' and is_Active='Y' ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}
		return webrole;
	}
	private ExamInfoDTO getExamInfoForArchIdList(String arch_num) throws ServiceException {
		String sql = "select id,user_name,sex,arch_num,id_num " + " From customer_info where arch_num ='" + arch_num
				+ "'  and is_Active='Y'  ";
		PageSupport map = this.jqm.getList(sql, 1, 1000, ExamInfoDTO.class);
		ExamInfoDTO webrole = new ExamInfoDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			webrole = (ExamInfoDTO) map.getList().get(0);
		}
		return webrole;
	}
	
	
	@Override
	public int queryIsUploadSynach(Connection connection, String exam_num) throws ServiceException {
		int isUpload = 0;
		try {
			String sql = "select is_upload from exam_info where exam_num = '"+exam_num+"' ";

			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				isUpload= rs.getInt("is_upload");
			}
			rs.close();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} 
		return isUpload;
		
	}
	@Override
	public String djtExaminfoBuzuo(String ids, String exam_num, UserDTO user) throws ServiceException {
		//是否需要总检才能补做
		String hql = "select e.* from  Exam_Info e where e.exam_num = '"+exam_num+"'";
		List<ExamInfo>   examDto = this.jqm.getList(hql,ExamInfo.class);
		ExamInfo ei= new ExamInfo();
		if(examDto!=null && examDto.size()>0){
			if(!"Z".equalsIgnoreCase(examDto.get(0).getStatus())) {
						return "error-未总检人员，不需要补做登记!";
			}
			ei=examDto.get(0);
		}
		/*if(olditemList.size() == 0){
			return "error-该体检人员不存在延期项目，不能补做登记!";
		}*/
		
		//查询该人员是否已经补做
		List<ExamInfo> examBuZuo = this.jqm.getList("select e.* from Exam_Info e where e.counter_check ='B' "
				+ "and e.exam_num='"+exam_num+"'",ExamInfo.class);
		if(examBuZuo!=null&&examBuZuo.size() > 0){
				return "error-该体检人员已补做登记，不能再次补做登记!";
		}		
		
		String bufferSql ="select ec.* FROM examinfo_charging_item ec WHERE ec.exam_num = ( SELECT exam_num FROM exam_info "
				+ "WHERE exam_num = '"+exam_num+"' ) AND ec.isActive = 'Y' and ec.pay_status<>'M' AND ec.charge_item_id IN ("+ids+") and ec.center_num = '"+user.getCenter_num()+"'";
		List<ExaminfoChargingItem>  olditemList = 	this.jqm.getList(bufferSql,ExaminfoChargingItem.class);
		
		String new_examnum = "B"+exam_num;		
		List<ExamInfo> examList = this.jqm.getList("select e.* from Exam_Info e where e.exam_num='"+new_examnum+"'",ExamInfo.class);
		ExamInfo bei = new ExamInfo();
		if(examList.size() > 0){
			bei =examList.get(0);
			if(ei.getStatus().equals('J')){
				return "error-该体检人员已补做检查，不能再次补做登记!";
			}else if(ei.getStatus().equals('Z')){
				return "error-该体检人员已补做总检，不能再次补做登记!";
			}else{
				 this.jpm.executeSql("delete from pacs_detail where examinfo_num='"+bei.getExam_num()+"'");
				 this.jpm.executeSql("delete from pacs_summary where examinfo_num='"+bei.getExam_num()+"'");
				  this.jpm.executeSql("delete from examResult_chargingItem where bar_code in ("
				  		+ "select sample_barcode from sample_exam_detail where exam_num ='"+bei.getExam_num()+"') and result_type='sample'");
				  this.jpm.executeSql("delete from sample_exam_detail where exam_num ='"+bei.getExam_num()+"'");      
				  this.jpm.executeSql("delete from examinfo_charging_item where exam_num ='"+bei.getExam_num()+"' and center_num = '"+user.getCenter_num()+"'");   
				  this.jpm.executeSql("delete from exam_info where exam_num ='"+bei.getExam_num()+"'");   
				
			}
		}
		Connection connection = null;
		long examid=0;
		String message="";
		// 插入数据库
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
		ExamInfoDTO examinfo = new ExamInfoDTO();
		examinfo.setGroup_id(0);
		examinfo.setCustomer_id(ei.getCustomer_id());
		examinfo.setExam_num(new_examnum);
		examinfo.setStatus("D");
		examinfo.setRegister_date(DateTimeUtil.getDateTime());
		examinfo.setJoin_date(DateTimeUtil.getDateTime());
		examinfo.setExam_type(ei.getExam_type());
		examinfo.setCustomer_type(ei.getCustomer_type());
		examinfo.setIs_sampled_directly(ei.getIs_sampled_directly());
		examinfo.setIs_adjusted(ei.getIs_adjusted());
		examinfo.setCenter_num(ei.getCenter_num());
		examinfo.setGetReportWay(ei.getGetReportWay());
		examinfo.setReportAddress(ei.getReportAddress());
		examinfo.setChargingType(ei.getChargingType());
		examinfo.setCustomerType(ei.getCustomerType());
		examinfo.setGroup_index(ei.getGroup_index());
		examinfo.setIs_Active(ei.getIs_Active());
		examinfo.setCreater(user.getUserid());
		examinfo.setCreate_time(DateTimeUtil.getDateTime());
		examinfo.setUpdater(user.getUserid());
		examinfo.setUpdate_time(DateTimeUtil.getDateTime());
		examinfo.setIs_guide_back("N");
		examinfo.setCompany_check_status(ei.getCompany_check_status());
		examinfo.setCustomer_type_id(ei.getCustomer_type_id());
		examinfo.setIs_marriage(ei.getIs_marriage());
		examinfo.setAge(ei.getAge());
		examinfo.setAddress(ei.getAddress());
		examinfo.setEmail(ei.getEmail());
		examinfo.setPhone(ei.getPhone());
		examinfo.setCompany(ei.getCompany());
		examinfo.setPosition(ei.getPosition());
		examinfo.set_level(ei.get_level());
		examinfo.setPicture_path(ei.getPicture_path());
		examinfo.setIs_after_pay(ei.getIs_after_pay());
		examinfo.setPast_medical_history(ei.getPast_medical_history());
		examinfo.setRemarke(ei.getRemarke());
		examinfo.setIntroducer(ei.getIntroducer());
		examinfo.setCounter_check("B");
		examinfo.setGuide_nurse(ei.getGuide_nurse());
		examinfo.setAppointment(ei.getAppointment());
		examinfo.setData_source(ei.getData_source());
		examinfo.setOthers(ei.getOthers());
		examinfo.setOrder_id(ei.getOrder_id());
		examinfo.setExam_times(ei.getExam_times());
		examinfo.setCompany_id(ei.getCompany_id());
		examinfo.setBatch_id(ei.getBatch_id());
		examinfo.setIs_report_print("N");
		examinfo.setPatient_id(ei.getPatient_id());
		examinfo.setExam_indicator(ei.getExam_indicator());
		examinfo.setClinic_no(ei.getClinic_no());
		examinfo.setEmployeeID(ei.getEmployeeID());
		examinfo.setMc_no(ei.getMc_no());
		examinfo.setVisit_date(ei.getVisit_date());
		examinfo.setVisit_no(ei.getVisit_no());
		examinfo.setIs_need_barcode("N");
		examinfo.setIs_need_guide("N");
		examinfo.setFreeze(ei.getFreeze());
		examinfo.setIs_report_tidy("N");
		examinfo.setBudget_amount(ei.getBudget_amount());
		examinfo.setMarriage_age(ei.getMarriage_age());
		examinfo.setWuxuzongjian(ei.getWuxuzongjian());
		examinfo.setOld_exam_num(ei.getExam_num()); //旧的体检号
		examinfo.setExam_center_num(user.getCenter_num());
		this.insertExamInfo(connection, examinfo);
		message="ok-"+new_examnum;
		}catch(Exception ex){
			ex.printStackTrace();
			message="error-操作失敗";
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
	@Override
	public List<ExaminfoChargingItem> getBuzuoItem(String oldexam_num, String ids, String center_num) throws ServiceException {
		String bufferSql ="select ec.* FROM examinfo_charging_item ec WHERE ec.exam_num = ( SELECT exam_num FROM exam_info WHERE exam_num = '"+oldexam_num+"' ) "
				+ "AND ec.isActive = 'Y' and ec.pay_status<>'M' AND ec.charge_item_id IN ("+ids+") and ec.center_num = '"+center_num+"'";
		List<ExaminfoChargingItem>  olditemList = 	this.jqm.getList(bufferSql,ExaminfoChargingItem.class);
		return olditemList;
	}
	@Override
	public void buzuoAdditem(String exam_num, String oldexam_num, List<ExaminfoChargingItem> olditemList, UserDTO user)
			throws ServiceException {
		ExamInfoDTO ei = this.customerInfoService.getCustExamInfoForexamId(exam_num);
		List<ExaminfoChargingItem> itemlist= new ArrayList<ExaminfoChargingItem>();
		int isaddcount=0;
		for (ExaminfoChargingItem examinfoChargingItem : olditemList) {
				//ChargingItemDTO cd =this.batchService.findChargeItemById(examinfoChargingItem.getCharge_item_id());			    		
	    		long oldexamid=examinfoChargingItem.getExaminfo_id();
			   // examinfoChargingItem.setExaminfo_id(exam_num);
	    		examinfoChargingItem.setExam_num(exam_num);
				examinfoChargingItem.setExam_status("N");
				examinfoChargingItem.setIs_new_added(0);
				examinfoChargingItem.setCreater(user.getUserid());
				examinfoChargingItem.setCreate_time(DateTimeUtil.parse());
				examinfoChargingItem.setUpdater(user.getUserid());
				examinfoChargingItem.setUpdate_time(DateTimeUtil.parse());				
				examinfoChargingItem.setIs_application("N");
				examinfoChargingItem.setExam_num(ei.getExam_num());
                   this.customerInfoService.saveExaminfoChargingItem(examinfoChargingItem);
                   //System.out.println(esdto.getExaminfo_id()+"   1   "+"   "+j);
                   ChargingItemDTO ci = this.batchService.findChargeItemById(examinfoChargingItem.getCharge_item_id());
                   int lisnum=this.customerInfoService.checkListItem(ci.getItem_code(),user.getCenter_num());
                   if(lisnum>0){
                      SampleExamDetail sd = new SampleExamDetail();
                      sd=this.customerInfoService.getSampleExamDetailforExamid(exam_num,ci.getSam_demo_id(),"W");
                      long samexam_id=0;
                      String sample_barcode="";
                      if((sd==null)||(sd.getId()<=0)){          	   
                   	   SampleExamDetail sed = new SampleExamDetail();
                   	   //sed.setExam_info_id(exam_num);      
                   	   sed.setExam_num(exam_num);
                   	   sed.setSample_id(ci.getSam_demo_id());
                   	   sample_barcode=this.batchService.GetCreateID("barcode", user.getCenter_num());
                   	   sed.setSample_barcode(sample_barcode);
                   	   sed.setStatus("W");
                   	   sed.setPic_path("");
                   	   sed.setCenter_num(user.getCenter_num());
                   	   sed.setCreater(user.getUserid());
                   	   sed.setCreate_time(DateTimeUtil.parse());
                   	   sed.setUpdater(user.getUserid());
                   	   sed.setUpdate_time(DateTimeUtil.parse());
//                   	   sed.setExam_num(sd.getExam_num());
                   	   this.customerInfoService.saveSampleExamDetail(sed);
                   	   samexam_id=sed.getId();
                   	   //System.out.println(esdto.getExaminfo_id()+"   2   "+"   "+j);
                      }else{
                   	   samexam_id=sd.getId(); 
                   	   sample_barcode=sd.getSample_barcode();
                      }
                      //System.out.println(esdto.getExaminfo_id()+"   3   "+"   "+j);
                      ExamResultChargingItem erci =new ExamResultChargingItem();
                      erci.setCharging_id(ci.getId());
                      erci.setExam_id(samexam_id);
                      erci.setResult_type(CommSet.lis_sample);
                      erci.setIsActive("Y");
                      erci.setCreater(user.getUserid());
                      erci.setCreate_time(DateTimeUtil.parse());
                      erci.setUpdater(user.getUserid());
                      erci.setUpdate_time(DateTimeUtil.parse());
                      erci.setBar_code(sample_barcode);
                      erci.setCharging_item_code(ci.getItem_code());
                      this.customerInfoService.saveExamResultChargingItem(erci);                       
                   
                   }
                   
                   int pacsnum=this.customerInfoService.checkPacsItem(ci.getItem_code(),user.getCenter_num());
                   //System.out.println(esdto.getExaminfo_id()+"   5   "+"   "+j);
                   if (pacsnum>0){			                    	
                   	long summary_id=0;
                   	String pacs_req_code="";
                   	PacsSummary psd = new PacsSummary();
                   	psd=this.customerInfoService.getPacsSummaryDTOforExamid(ei.getExam_num(),ci.getSam_demo_id(),"N");
                   	if((psd==null)||(psd.getId()<=0)){
                   		PacsSummary ps =new PacsSummary();
                   		ps.setExaminfo_num(ei.getExam_num());
                   		ps.setExaminfo_name(ei.getUser_name());
                   		ps.setExaminfo_sex(ei.getSex());
                   		
                   		if ((!StringUtil.isEmpty(ei.getBirthday()))&&(ei.getBirthday().trim().length() == 10)){
                   			Date da = Timeutils.strToDateLong2(ei.getBirthday().trim().replaceAll("-", ""));
   							ps.setExaminfo_birthday(da);
   						}else if ((!StringUtil.isEmpty(ei.getBirthday()))&&(ei.getBirthday().trim().length() == 8)){
                   			Date da = Timeutils.strToDateLong2(ei.getBirthday().trim());
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
                   		pacs_req_code=this.batchService.GetCreateID("pacs_req_num", user.getCenter_num());
                   		ps.setPacs_req_code(pacs_req_code);
                   		ps.setSample_status("N");
                   		this.customerInfoService.savePacsSummary(ps);			                    		
                   		summary_id=ps.getId();
                   	}else{
                   		summary_id=psd.getId();
                   		pacs_req_code=psd.getPacs_req_code();
                   	}
                   	//System.out.println(esdto.getExaminfo_id()+"   8   "+"   "+j);
                   	Pacsdetail pd =new Pacsdetail();
                   	pd.setExaminfo_num(ei.getExam_num());
                   	pd.setSummary_id(summary_id);
                   	pd.setChargingItem_num(ci.getItem_code());
                   	pd.setChargingItem_name(ci.getItem_name());
                   	DepartmentDep dd = departmentService.loadDepartmentDep(ci.getDep_id());
                   	pd.setDep_num(dd.getDep_num());
                   	pd.setDep_name(dd.getDep_name());
                   	pd.setExaminfo_sampleId(ci.getSam_demo_id());
                   	pd.setIs_need_return("Y");
                   	pd.setCreater(user.getUserid());
                   	pd.setCreate_time(DateTimeUtil.parse());
                   	pd.setUpdater(user.getUserid());
                   	pd.setUpdate_time(DateTimeUtil.parse());
                   	pd.setPacs_req_code(pacs_req_code);
                   	this.customerInfoService.savePacsdetail(pd);
                   	
                   }	
	    		}
	}
	@Override
	public String queryReplaceUser(RegisterModel model) throws ServiceException {
		String result = "error-系统返回数据错误";
		String sql = "select ti_name,ti_sex,ti_age,ti_id_num from exam_ext_info where exam_num = '"+model.getExam_num()+"' ";
		List<ExamInfoExtDTO> list = this.jqm.getList(sql, ExamInfoExtDTO.class);
		if(list.size() > 0){
			if(list.get(0).getTi_name()==null) {
				list.get(0).setTi_name("");
			}
			if(list.get(0).getTi_sex()==null || "".equals(list.get(0).getTi_sex())) {
				list.get(0).setTi_sex("男");
			}
			if(list.get(0).getTi_id_num()==null) {
				list.get(0).setTi_id_num("");
			}
			result = "ok-"+list.get(0).getTi_name()+"-"+list.get(0).getTi_sex()+"-"
					+list.get(0).getTi_age()+"-"+list.get(0).getTi_id_num();
		}
		return result;
	}
	@Override
	public String saveReplaceUserData(RegisterModel model, UserDTO user) throws ServiceException {
		
		String message = "error-系统错误";
		
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			
			String querySql = "select exam_num from exam_ext_info where exam_num = '"+model.getExam_num()+"' ";
			List<ChargingItemDTO> list = this.jqm.getList(querySql, ChargingItemDTO.class);
			if(list.size() > 0){
				String sql = "update exam_ext_info set ti_name='"+model.getTi_name()+"',ti_sex='"+model.getTi_sex()+"',"
						+ "ti_age='"+model.getTi_age()+"',ti_id_num='"+model.getTi_id_num()+"',update_time='"+DateTimeUtil.getDateTime()+"'"
						+ " where exam_num = '"+model.getExam_num()+"' ";
				connection.createStatement().executeUpdate(sql);
				message = "ok-修改替检人员成功";
			}else {
				String sql = "insert into exam_ext_info(exam_num,arch_num,ti_name,ti_sex,ti_age,ti_id_num,creater,create_time,update_time) " 
				           + "values ('"+model.getExam_num()+"','"+model.getArch_num()+"','"+model.getTi_name()+"','"+model.getTi_sex()+"',"
				           		+ " '"+model.getTi_age()+"', '"+model.getTi_id_num()+"','"+user.getUserid()+"', '"+ DateTimeUtil.getDateTime()+"','"+ DateTimeUtil.getDateTime()+"')";
				connection.createStatement().executeUpdate(sql);
				message = "ok-新增替检人员成功";
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
		
		
		return message;
	}
	@Override
	public List<PacsCountDTO> getPacsItemApplicationList(String exam_num, String center_num) throws ServiceException {
		String sql = "select a.id as charging_id,d.dep_name,b.item_code,b.item_name,case when b.isOnlyApplyOrReport = 'Y' then b.item_code "
				+"else CONVERT(varchar(10),a.print_apply_num)+m.demo_num end code "
				+"from examinfo_charging_item a,charging_item b,sample_demo m,department_dep d "
				+"where a.exam_num = '"+exam_num+"'"
				+" and a.isActive='Y' "
				+"and a.pay_status<>'M' "
				+"and a.exam_status<>'G' and a.center_num = '"+center_num+"' "
				+"and a.charging_item_code=b.item_code "
				+"and b.dep_id=d.id and m.isPrint_req='1' and  b.sam_demo_id=m.id order by d.seq_code,code";
	List<PacsCountDTO> list = this.jqm.getList(sql, PacsCountDTO.class);
	return list;
	}
	
	/**
	 * 
	     * @Title: djtTpanelG2TItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String djtTpanelG2TItem(String ids,long userid) throws ServiceException {
		Connection tjtmpconnect = null;
		String message = "error-操作失败";
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "  select COUNT(*) as counts from exam_info where exam_num in ('" + ids.replaceAll(",", "','")
					+ "') and status<>'Y' and is_Active='Y'";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			long customtypeid = 0;
			if (rs1.next()) {
				customtypeid = (rs1.getLong("counts"));
			}
			rs1.close();
			if (customtypeid > 0) {
				message = "error-所选体检信息不是预约状态，操作不能继续";
			} else {
				sb1 = "   update examinfo_charging_item set pay_status='R', exam_indicator ='T',"
						+ "team_pay=personal_pay+team_pay,personal_pay=0,team_pay_status='R',"
						+ "updater='"+userid+"',update_time='"+DateTimeUtil.getDateTime()+"' "
						+ "where exam_num in ('"+ids.replaceAll(",", "','")+"') and pay_status='N' and exam_indicator='G' and exam_status='N' "
						+ "and isActive='Y' ";
				tjtmpconnect.createStatement().executeUpdate(sb1);				
				message = "ok-操作完成";
			}
		} catch (SQLException ex) {

		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return message;}
	
	/**
	 * 
	     * <p>Title: djtTpanelT2GItem</p>   
	     * <p>Description: </p>   
	     * @param ids
	     * @param userid
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.DjtCustomerInfoService#djtTpanelT2GItem(java.lang.String, long)
	 */
	public String djtTpanelT2GItem(String ids,long userid)throws ServiceException{
		Connection tjtmpconnect = null;
		String message="error-操作失败";		
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "  select COUNT(*) as counts from exam_info where exam_num in ('"+ids.replaceAll(",", "','")+"') and status<>'Y' and is_Active='Y'";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			long customtypeid= 0;
			if (rs1.next()) {
				customtypeid=(rs1.getLong("counts"));
			}
			rs1.close();
			if(customtypeid > 0){
				message="error-所选体检信息不是预约状态，操作不能继续";		
			}else{
				sb1 = " update examinfo_charging_item set pay_status='N', exam_indicator ='G',"
						+ "personal_pay=personal_pay+team_pay,team_pay=0,team_pay_status=null,"
						+ "updater='"+userid+"',update_time='"+DateTimeUtil.getDateTime()+"' "
						+ " where exam_num in ('"+ids.replaceAll(",", "','")+"') and pay_status='R' and exam_indicator='T' "
						+ "and exam_status='N' and pay_status<>'M' and isActive='Y' ";
				tjtmpconnect.createStatement().executeUpdate(sb1);
				
				sb1 = " update examinfo_charging_item set pay_status='Y',"
						+ "updater='"+userid+"',update_time='"+DateTimeUtil.getDateTime()+"' "
						+ " where exam_num in ('"+ids.replaceAll(",", "','")+"') and pay_status='N' and personal_pay=0 and exam_indicator ='G' "
						+ "and exam_status='N' and pay_status<>'M' and isActive='Y' ";
				tjtmpconnect.createStatement().executeUpdate(sb1);
				message="ok-操作完成";		
			}
		} catch (SQLException ex) {
					
		} finally {
			try {
				if (tjtmpconnect != null) {
					tjtmpconnect.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return message;
	}
	@Override
	public String changeExaminfoExamType(String exam_num,long exam_id, String exam_type, long company_id, String comname, long batch_id,
			long group_id, long gtot_exam) throws ServiceException {
		String sql = "select ec.id from examinfo_charging_item ec where ec.exam_num = '"+exam_num+"' and ec.isActive = 'Y' and ec.pay_status = 'Y'";
		List<ChargingItemDTO> list = this.jqm.getList(sql, ChargingItemDTO.class);
		if(list.size() > 0 && gtot_exam != 2){
			return "error-存在已收费项目,不能转换体检类型!";
		}
		if("G".equals(exam_type)){//团体转个人
			sql = "update exam_info set group_id = 0,company = '',company_id = 0,batch_id = 0,exam_type = 'G' where exam_num = '"+exam_num+"'";
			this.jpm.executeSql(sql);
			sql = "update examinfo_charging_item set exam_indicator= 'G',pay_status = 'N',personal_pay = amount,team_pay = 0 "
					+ "where exam_num = '"+exam_num+"' and pay_status <> 'M'";
			this.jpm.executeSql(sql);
			
		}else{//个人转团体
			sql = "update exam_info set group_id = "+group_id+",company = '"+comname+"',company_id = "+company_id+","
					+ "batch_id = "+batch_id+",exam_type = 'T' where exam_num = '"+exam_num+"'";
			this.jpm.executeSql(sql);
			if(gtot_exam == 1){
				sql = "update examinfo_charging_item set exam_indicator= 'T',pay_status = 'R',team_pay = amount,personal_pay = 0 "
						+ "where exam_num = '"+exam_num+"' and pay_status <> 'M'";
				this.jpm.executeSql(sql);
			}
			
		}
		return "ok-体检类型转换成功!";
	}

	public ExamInfo findExamInfoForId(long examid)throws ServiceException{
		String hql = " from ExamInfo g where g.id=" + examid + "";
		List<ExamInfo> list= new ArrayList<ExamInfo>();
		list = qm.find(hql);
		ExamInfo ei= new ExamInfo();
		if(list!=null && list.size()>0){
			ei=list.get(0);
		}
		return ei;
	}

	public ExamInfo saveExamInfo(ExamInfo eis) throws ServiceException {
		pm.save(eis);
		return eis;
	}
}
