package com.hjw.wst.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.loader.GeneratedCollectionAliases;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.EncryptUtils;
import com.hjw.util.Timeutils;
import com.hjw.webService.client.sxwn.Bean.TableBean;
import com.hjw.webService.client.sxwn.Bean.TableItemBean;
import com.hjw.webService.service.Databean.Dept;
import com.hjw.webService.service.Databean.DiagnosisItem;
import com.hjw.webService.service.Databean.DiagnosisPrice;
import com.hjw.webService.service.Databean.Price;
import com.hjw.webService.service.Databean.User;
import com.hjw.webService.service.bean.ResultSerBody;
import com.hjw.webService.service.bean.RetCharges;
import com.hjw.webService.service.bean.RetReqNo;
import com.hjw.webService.service.lisbean.ChargeType;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.HisPriceItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.ProcListResult;
import com.hjw.wst.DTO.ProcPacsResult;
import com.hjw.wst.DTO.ThridLisClassDTO;
import com.hjw.wst.DTO.ThridLisClassItemDTO;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.ChargingSummarySingle;
import com.hjw.wst.domain.ChargingWaySingle;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.HisChargingWaySingle;
import com.hjw.wst.domain.HisClinicItem;
import com.hjw.wst.domain.HisDictDept;
import com.hjw.wst.domain.HisPriceList;
import com.hjw.wst.service.DataService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: yangm
 * @date: 2016年7月4日 上午9:37:08
 * @version V2.0.0.0
 */
public class DataServiceImpl implements DataService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;

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
	 * @Title: doproc_Lis_result @Description: 执行lis结果插入 @param: @param
	 *         pr @param: @return @param: @throws ServiceException @return:
	 *         int @throws
	 */
	public int doproc_Lis_result(ProcListResult pr) throws ServiceException {
		// 信息入正式库
		int typeid = 1;
		Connection conn = null;
		try {

			/*
			 * @bar_code varchar(20), --条码号
			 * 
			 * @exam_num String, --体检Num
			 * 
			 * @lis_item_code varchar(20), --LIS收费项目关联码
			 * 
			 * @lis_rep_item_code varchar(20), --LIS细项编码
			 * 
			 * @exam_doctor varchar(20), --检验（报告）医生
			 * 
			 * @exam_date varchar(20), --检验日期
			 * 
			 * @exam_result varchar(100), --检验结果
			 * 
			 * @ref_value varchar(400), --参考范围
			 * 
			 * @item_unit varchar(80), --单位
			 * 
			 * @ref_indicator varchar(4), --高低标识（0：正常；1：高；2：低；3：阳性；4：危急）
			 * 
			 * @approver varchar(20), --审核医生
			 * 
			 * @approve_date varchar(20), --审核日期
			 * 
			 * @error int out --返回值(0:成功) proc_Lis_result()
			 */

			conn = this.jqm.getConnection();
			CallableStatement c = conn.prepareCall("{call proc_Lis_result(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			c.setString(1, pr.getBar_code());
			c.setString(2, pr.getExam_num());
			c.setString(3, pr.getLis_item_code());
			c.setString(4, pr.getLis_rep_item_code());
			c.setString(5, pr.getExam_doctor());
			c.setString(6, pr.getExam_date());
			c.setString(7, pr.getExam_result());
			c.setString(8, pr.getRef_value());
			c.setString(9, pr.getItem_unit());
			c.setString(10, pr.getRef_indicator());
			c.setString(11, pr.getApprover());
			c.setString(12, pr.getApprove_date());
			c.registerOutParameter(13, java.sql.Types.INTEGER);
			// 执行存储过程
			c.execute();
			// 得到存储过程的输出参数值
			typeid = c.getInt(13);
			System.out.println("doproc_Lis_result 返回=" + typeid);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return typeid;
	}

	/**
	 * 
	 * @Title: proc_pacs_report_dbgj @Description: 执行pacs插入 @param: @param
	 *         pr @param: @return @param: @throws ServiceException @return:
	 *         int @throws
	 */
	public int proc_pacs_report_dbgj(ProcPacsResult pr) throws ServiceException {
		// proc_pacs_report_dbgj
		/*********************************************
		 * 东北国际医院（平台PACS接口结果回传）
		 *********************************************/
		/*
		 * @pacs_req_code varchar(20), --申请单编号（必填）
		 * 
		 * @exam_num varchar(20), --体检号
		 * 
		 * @check_date varchar(20), --检查时间（必填）
		 * 
		 * @check_doct varchar(20), --检查医生（必填）
		 * 
		 * @audit_date varchar(20), --审核时间（必填）
		 * 
		 * @audit_doct varchar(20), --审核医生（必填）
		 * 
		 * @exam_result varchar(1000), --诊断结果（必填）
		 * 
		 * @exam_desc varchar(2000), --影像表现(可空)
		 * 
		 * @BodyPart varchar(200), --检查部位(可空)
		 * 
		 * @ExamMethod varchar(500), --检查方法(可空)
		 * 
		 * @img_file varchar(2000)
		 * --图像文件(多个用;隔开，文件格式：/pacs_img/20161027/ET/T16A270010/T16A270010.jpg)
		 */

		// 信息入正式库
		int typeid = 1;
		Connection conn = null;
		try {
			conn = this.jqm.getConnection();
			CallableStatement c = conn.prepareCall("{call proc_pacs_report_dbgj(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			c.setString(1, pr.getPacs_req_code());
			c.setString(2, pr.getExam_num());
			c.setString(3, pr.getCheck_date());
			c.setString(4, pr.getCheck_doct());
			c.setString(5, pr.getAudit_date());
			c.setString(6, pr.getAudit_doct());
			c.setString(7, pr.getExam_result());
			c.setString(8, pr.getExam_desc());
			c.setString(9, pr.getBodyPart());
			c.setString(10, pr.getExamMethod());
			c.setString(11, pr.getImg_file());
			c.registerOutParameter(12, java.sql.Types.INTEGER);
			c.registerOutParameter(13, java.sql.Types.VARCHAR);
			// 执行存储过程
			c.execute();
			// 得到存储过程的输出参数值
			typeid = c.getInt(12);
			System.out.println("proc_pacs_report_dbgj 返回=" + typeid + "-" + c.getInt(13));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException sqle4) {
				sqle4.printStackTrace();
			}
		}
		return typeid;
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public String saveFeesResult(ResultSerBody rb) throws ServiceException {

		// String sql = "select e.id,e.exam_num,e.patient_id,c.user_name from
		// exam_info e,customer_info c "
		// + "where c.id = e.customer_id and exam_num = '"+rb.getEXAM_NUM()+"'";
		// List<ExamInfoDTO> examList = this.jqm.getList(sql,
		// ExamInfoDTO.class);
		// if(examList.size() == 0){
		// return "error-根据体检号找不出匹配的体检信息!";
		// }
		// ExamInfoDTO examInfo = examList.get(0);
		// if(!examInfo.getUser_name().equals(rb.getNAME())){
		// return "error-体检人员姓名与体检号不匹配!";
		// }
		RetCharges charges = rb.getCHARGES();
		if ("".equals(charges.getRCPT_NO())) {
			return "error-收据号不能为空!";
		}
		if ("".equals(charges.getOPERATOR_NO())) {
			return "error-收费员编号不能为空!";
		}
		Double amount = 0.0;
		try {
			amount = Double.valueOf(charges.getCHARGE());
		} catch (NumberFormatException e) {
			return "error-收费总金额格式错误!";
		}

		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(charges.getCHARGE_DATE());
		} catch (ParseException ex) {
			return "error-收费日期格式错误!";
		}
		String sql1 = "select u.id,u.chi_name from user_usr u where u.work_num = '" + charges.getOPERATOR_NO() + "'";

		List<UserInfoDTO> userList = this.jqm.getList(sql1, UserInfoDTO.class);
		if (userList.size() == 0) {
			return "error-未匹配到收费员信息!";
		}
		List<RetReqNo> reqNumList = charges.getREQ_NOS().getITEM();
		List<ChargingSummarySingle> summarySingleList = new ArrayList<ChargingSummarySingle>();
		Double amount2 = 0.0;
		for (RetReqNo reqNum : reqNumList) {
			String sql2 = "from ChargingSummarySingle c where c.req_num = '" + reqNum.getREQ_NO() + "'";

			List<ChargingSummarySingle> summaryList = this.qm.find(sql2, ChargingSummarySingle.class);
			if (summaryList.size() == 0) {
				return "error-申请单号未匹配的正确的信息!";
			}
			ChargingSummarySingle chargingSummarySingle = summaryList.get(0);
			amount2 += chargingSummarySingle.getAmount2();
			chargingSummarySingle.setCharging_status("Y");
			chargingSummarySingle.setIs_print_recepit("N");
			chargingSummarySingle.setCashier(Long.valueOf(userList.get(0).getId()));
			chargingSummarySingle.setCash_date(date);
			chargingSummarySingle.setUpdate_time(date);
			chargingSummarySingle.setUpdater(Long.valueOf(userList.get(0).getId()));

			summarySingleList.add(chargingSummarySingle);
		}
		if (amount != amount2) {
			return "error-总金额不相同,请检查HIS价表是否同步最新的数据!";
		}

		List<ChargeType> chargeTypeList = charges.getCHARGE_TYPES().getCHARGE_TYPE();

		List<HisChargingWaySingle> hisChargingWayList = new ArrayList<HisChargingWaySingle>();
		for (ChargeType chargeType : chargeTypeList) {
			String sql3 = "select d.id from data_dictionary d where "
					+ "d.data_code = 'SFFSLX' and d.isActive = 'Y' and d.remark = '" + chargeType.getCHARGE_TYPE_CODE()
					+ "'";

			List<DataDictionaryDTO> dicList = this.jqm.getList(sql3, DataDictionaryDTO.class);

			if (dicList.size() == 0) {
				return "error-未匹配到收费方式,请检查收费方式编码是否对应";
			}
			HisChargingWaySingle hisChargingWaySingle = new HisChargingWaySingle();

			hisChargingWaySingle.setAccount_num(charges.getRCPT_NO());
			hisChargingWaySingle.setCharging_way(dicList.get(0).getId() + "");
			try {
				hisChargingWaySingle.setAmount(Double.parseDouble(chargeType.getAMOUNT()));
			} catch (NumberFormatException e) {
				return "error-收费方式金额格式错误!";
			}
			hisChargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
			hisChargingWaySingle.setCreate_time(date);
			hisChargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
			hisChargingWaySingle.setUpdate_time(date);

			hisChargingWayList.add(hisChargingWaySingle);
		}
		// ------------开始保存数据
//		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();
//
//		chargingInvoiceSingle.setTitle_info("");
//		chargingInvoiceSingle.setInvoice_type("");
//		chargingInvoiceSingle.setInvoice_num(charges.getINVOICE_NO());
//		chargingInvoiceSingle.setInvoice_amount(amount);
//		chargingInvoiceSingle.setInvoice_status("Y");
//		chargingInvoiceSingle.setInvoice_maker(Long.valueOf(userList.get(0).getId()));
//		chargingInvoiceSingle.setInvoice_time(date);
//		chargingInvoiceSingle.setCreate_time(date);
//		chargingInvoiceSingle.setCreater(Long.valueOf(userList.get(0).getId()));
//		chargingInvoiceSingle.setUpdate_time(date);
//		chargingInvoiceSingle.setUpdater(Long.valueOf(userList.get(0).getId()));
//		chargingInvoiceSingle.setAccount_num(charges.getRCPT_NO());
//
//		this.pm.save(chargingInvoiceSingle);

		String req_nums = "";
		for (ChargingSummarySingle chargingSummarySingle : summarySingleList) {
			req_nums += "'" + chargingSummarySingle.getReq_num() + "',";
//			chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
//			this.pm.update(chargingSummarySingle);

			ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
			chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
			chargingWaySingle.setCharging_way("0");
			chargingWaySingle.setAmount(chargingSummarySingle.getAmount2());
			chargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
			chargingWaySingle.setCreate_time(date);
			chargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
			chargingWaySingle.setUpdate_time(date);
			this.pm.save(chargingWaySingle);
		}

		for (HisChargingWaySingle hisChargingWaySingle : hisChargingWayList) {
			this.pm.save(hisChargingWaySingle);
		}

		String sql4 = " select ec.* from examinfo_charging_item ec,charging_summary_single cs,charging_detail_single cd"
				+ " where ec.examinfo_id = cs.exam_id and cs.id = cd.summary_id and ec.charging_item_code = cd.charging_item_code and ec.center_num = '"+center_num+"' "
				+ " and cs.req_num in(" + req_nums.substring(0, req_nums.length() - 1) + ")";
		List<ExaminfoChargingItem> iteList = this.jqm.getList(sql4, ExaminfoChargingItem.class);

		for (ExaminfoChargingItem examinfoChargingItem : iteList) {
			examinfoChargingItem.setPay_status("Y");
			this.pm.update(examinfoChargingItem);
		}
		return "ok-保存成功";
	}*/

	/**
	 * 
	 * @Title: getDepNumForPacs @Description: 获取pacs科室编码 @param: @param
	 *         pacs_req_code @param: @return @param: @throws
	 *         ServiceException @return: String @throws
	 */
	public String getDepNumForPacs(String pacs_req_code) throws ServiceException {
		Connection connection = null;
		Statement statement = null;
		String depnum = "";
		try {
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			StringBuffer sbt = new StringBuffer();
			sbt.append(
					"select b.dep_num from pacs_summary a,pacs_detail b  where a.pacs_req_code=12 and a.id=b.summary_id");
			ResultSet rs = statement.executeQuery(sbt.toString());
			if (rs.next()) {
				depnum = rs.getString("dep_num");
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
		return depnum;
	}

	/**
	 * 
	 * @Title: getDatadis @Description: TODO(这里用一句话描述这个方法的作用) @param: @param
	 *         data_code @param: @return @param: @throws
	 *         ServiceException @return: JobDTO @throws
	 */
	public JobDTO getDatadis(String data_code) throws ServiceException {
		String sqltext = "select id,data_name from data_dictionary where data_code='" + data_code
				+ "' and isActive='Y' ";
		Connection connection = null;
		Statement statement = null;
		JobDTO jd = new JobDTO();
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqltext);
			if (rs.next()) {
				jd.setId(rs.getString("id"));
				jd.setName(rs.getString("data_name"));
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
		return jd;
	}

	/**
	 * * @Title: insertHisdept @Description: 新插入 @param: @param
	 * hisdep @param: @throws ServiceException @return: void @throws
	 */
	private void insertHisdept(HisDictDept hisdep) throws ServiceException {
		String sql = "insert into his_dict_dept (dept_code,dept_name,dept_class,input_code) values('"
				+ hisdep.getDept_code() + "','" + hisdep.getDept_name() + "','" + hisdep.getDept_class() + "','"
				+ hisdep.getInput_code() + "')";
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (Exception ex) {
			/*
			 * try { connection.rollback(); } catch (SQLException e) {
			 * 
			 * }
			 */
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
	 * @Title: updateHisdept @Description: 更新 @param: @param
	 *         hisdep @param: @throws ServiceException @return: void @throws
	 */
	private void updateHisdept(HisDictDept hisdep) throws ServiceException {
		String sql = "update his_dict_dept set dept_code='" + hisdep.getDept_code() + "',dept_name='"
				+ hisdep.getDept_name() + "',dept_class='" + hisdep.getDept_class() + "',input_code='"
				+ hisdep.getInput_code() + "' where dept_code='" + hisdep.getDept_code() + "'";
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (Exception ex) {
			/*
			 * try { connection.rollback(); } catch (SQLException e) {
			 * 
			 * }
			 */
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
	 * @Title: updateHisdept @Description: 更新 @param: @param
	 *         hisdep @param: @throws ServiceException @return: void @throws
	 */
	private List<HisDictDept> gettHisdept(String deptcode) throws ServiceException {
		List<HisDictDept> hisDictDeptList = new ArrayList<HisDictDept>();
		String sql = "select dept_code,dept_name,dept_class,input_code from  his_dict_dept where dept_code='" + deptcode
				+ "'";
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			// 处理结果
			while (rs.next()) {
				HisDictDept hd = new HisDictDept();
				hd.setDept_class(rs.getString("dept_class"));
				hd.setDept_code(rs.getString("dept_code"));
				hd.setDept_name(rs.getString("dept_name"));
				hd.setInput_code(rs.getString("input_code"));
				hisDictDeptList.add(hd);
			}
			rs.close();
		} catch (Exception ex) {
			/*
			 * try { connection.rollback(); } catch (SQLException e) {
			 * 
			 * }
			 */
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
		return hisDictDeptList;
	}

	/**
	 * 
	 * <p>
	 * Title: saveHisDept
	 * </p>
	 * 保存执行科室
	 * 
	 * @param depList
	 * @return
	 * @throws ServiceException
	 * @see com.hjw.wst.service.DataService#saveHisDept(java.util.List)
	 */
	@Override
	public String saveHisDept(List<Dept> depList) throws ServiceException {

		for (Dept dept : depList) {
			if ("0".equals(dept.getAction())) {// 删除
				List<HisDictDept> hisDictDeptList = this.gettHisdept(dept.getDept_code());
				if (hisDictDeptList.size() != 0) {
					HisDictDept hisdep = hisDictDeptList.get(0);
					hisdep.setDept_class("N");
					this.updateHisdept(hisdep);
				}
			} else if ("1".equals(dept.getAction())) {// 插入
				List<HisDictDept> hisDictDeptList = this.gettHisdept(dept.getDept_code());
				if (hisDictDeptList.size() == 0) {
					HisDictDept hisdep = new HisDictDept();
					hisdep.setDept_code(dept.getDept_code());
					hisdep.setDept_class("Y");
					hisdep.setDept_name(dept.getDept_name());
					hisdep.setInput_code(dept.getInput_code());
					this.insertHisdept(hisdep);
				} else {
					HisDictDept hisdep = hisDictDeptList.get(0);
					hisdep.setDept_code(dept.getDept_code());
					hisdep.setDept_class("Y");
					hisdep.setDept_name(dept.getDept_name());
					hisdep.setInput_code(dept.getInput_code());
					this.updateHisdept(hisdep);
				}
			} else if ("2".equals(dept.getAction())) {// 更新
				List<HisDictDept> hisDictDeptList = this.gettHisdept(dept.getDept_code());
				if (hisDictDeptList.size() == 0) {
					HisDictDept hisdep = new HisDictDept();
					hisdep.setDept_code(dept.getDept_code());
					hisdep.setDept_class("Y");
					hisdep.setDept_name(dept.getDept_name());
					hisdep.setInput_code(dept.getInput_code());
					this.insertHisdept(hisdep);
				} else {
					HisDictDept hisdep = hisDictDeptList.get(0);
					hisdep.setDept_code(dept.getDept_code());
					hisdep.setDept_class("Y");
					hisdep.setDept_name(dept.getDept_name());
					hisdep.setInput_code(dept.getInput_code());
					this.updateHisdept(hisdep);
				}
			}
		}
		return "ok-保存成功!";
	}

	/**
	 * 
	     * @Title: savePrice   
	     * @Description: 同步价表  
	     * @param: @param priceList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String savePrice(List<Price> priceList) throws ServiceException {        
		for (Price price : priceList) {
			try{
				if((price.getStop_date()==null)||(price.getStop_date().trim().length()<=0)){
			    	price.setStop_date(DateTimeUtil.DateAdd2(365*100));
			    }
			if(price.getItem_name().indexOf("'")>=0){
				price.setItem_name(price.getItem_name().replaceAll("'", "''"));
			}
			
			if(price.getMemo().indexOf("'")>=0){
				price.setMemo(price.getMemo().replaceAll("'", "''"));
			}
			
			if(price.getMemo().indexOf("'")>=0){
				price.setMemo(price.getMemo().replaceAll("'", "''"));
			}
			
			if ("0".equals(price.getAction())) {// 删除
				String startdate =DateTimeUtil.shortFmt2(DateTimeUtil.parse(price.getStart_date()));
                price.setStart_date(startdate);
				StringBuffer sb = new StringBuffer();
				sb.append("select item_class,item_code,item_name,item_spec,units,price,prefer_price,"
						+ "performed_by,input_code,class_on_inp_rcpt,class_on_outp_rcpt,class_on_reckoning,"
						+ "subj_code,memo,start_date,stop_date from his_price_list where item_class='"
						+price.getItem_class()+"' and item_code='"+price.getItem_code()
						+"' and item_spec='"+price.getItem_spec()+"' and units='"+price.getUnits()
						+"' and start_date='"+startdate+" 23:59:59' ");
				System.out.println(sb.toString());
				List<HisPriceList> hisPriceList = this.jqm.getList(sb.toString(), HisPriceList.class);
			if (hisPriceList.size() == 1) {
				String sql = "update his_price_list set item_name = '"
						+ price.getItem_name() + "',price = " + price.getPrice() + "," + "prefer_price = "
						+ price.getPrefer_price() + ",performed_by = '" + price.getPerformed_by()
						+ "',input_code = '" + price.getInput_code() + "'," + "class_on_inp_rcpt = '"
						+ price.getClass_on_inp_rcpt() + "',class_on_outp_rcpt = '" + price.getClass_on_outp_rcpt()
						+ "'," + "class_on_reckoning = '" + price.getClass_on_reckoning() + "',subj_code = '"
						+ price.getSubj_code() + "',memo = '" + price.getMemo() + "'," + "start_date='"
						+ price.getStart_date() + "',stop_date = '" + DateDiff(1) + "',update_date='"
						+DateTimeUtil.getDateTime()+"' where item_class='"
						+price.getItem_class()+"' and item_code='"+price.getItem_code()
						+"' and item_spec='"+price.getItem_spec()+"' and units='"
						+price.getUnits()+"' and start_date='"+startdate+" 23:59:59' ";
				System.out.println(sql);
				this.jpm.executeSql(sql);	
				 }
			} else {// 插入 item_class、item_code、item_spec、units 
                    String startdate =DateTimeUtil.shortFmt2(DateTimeUtil.parse(price.getStart_date()));
                    price.setStart_date(startdate);
			    	StringBuffer sb = new StringBuffer();
					sb.append("select item_class,item_code,item_name,item_spec,units,price,prefer_price,"
							+ "performed_by,input_code,class_on_inp_rcpt,class_on_outp_rcpt,class_on_reckoning,"
							+ "subj_code,memo,start_date,stop_date from his_price_list where item_class='"
							+price.getItem_class()+"' and item_code='"+price.getItem_code()
							+"' and item_spec='"+price.getItem_spec()+"' and units='"+price.getUnits()
							+"' and start_date='"+startdate+" 23:59:59' ");
					System.out.println(sb.toString());
					List<HisPriceList> hisPriceList = this.jqm.getList(sb.toString(), HisPriceList.class);
				if (hisPriceList.size() == 1) {
					String sql = "update his_price_list set item_name = '"
							+ price.getItem_name() + "',price = " + price.getPrice() + "," + "prefer_price = "
							+ price.getPrefer_price() + ",performed_by = '" + price.getPerformed_by()
							+ "',input_code = '" + price.getInput_code() + "'," + "class_on_inp_rcpt = '"
							+ price.getClass_on_inp_rcpt() + "',class_on_outp_rcpt = '" + price.getClass_on_outp_rcpt()
							+ "'," + "class_on_reckoning = '" + price.getClass_on_reckoning() + "',subj_code = '"
							+ price.getSubj_code() + "',memo = '" + price.getMemo() + "'," + "start_date='"
							+ price.getStart_date() + "',stop_date = '" + price.getStop_date() + "',update_date='"
							+DateTimeUtil.getDateTime()+"' where item_class='"
							+price.getItem_class()+"' and item_code='"+price.getItem_code()
							+"' and item_spec='"+price.getItem_spec()+"' and units='"
							+price.getUnits()+"' and start_date='"+startdate+" 23:59:59' ";
					System.out.println(sql);
					this.jpm.executeSql(sql);					
					hislog(price,hisPriceList.get(0));			
				} else if (hisPriceList.size() == 0){
					String sql = "insert into his_price_list(item_class,item_code,item_name,"
							+ "item_spec,units,price,prefer_price,performed_by,input_code,"
							+ "class_on_inp_rcpt,class_on_outp_rcpt,class_on_reckoning,"
							+ "subj_code,memo,start_date,stop_date,create_date,update_date) values('" + price.getItem_class() + "','"
							+ price.getItem_code() + "'" + ",'" + price.getItem_name() + "','" + price.getItem_spec()
							+ "','" + price.getUnits() + "'," + price.getPrice() + "," + price.getPrefer_price() + ",'"
							+ price.getPerformed_by() + "','" + price.getInput_code() + "'" + ",'"
							+ price.getClass_on_inp_rcpt() + "','" + price.getClass_on_outp_rcpt() + "','"
							+ price.getClass_on_reckoning() + "'" + ",'" + price.getSubj_code() + "','"
							+ price.getMemo() + "','" + startdate + "','" + price.getStop_date() + "','"
							+DateTimeUtil.getDateTime()+"','"+DateTimeUtil.getDateTime()+"')";
					System.out.println(sql);
					this.jpm.executeSql(sql);
				}			
			}
			}catch(Exception ex){
				ex.toString();
				ex.printStackTrace();
			}
			}
		return "ok-保存成功!";
	}

	/**
	 * 
	     * @Title: hislog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param newp
	     * @param: @param oldp      
	     * @return: void      
	     * @throws
	 */
	private void hislog(Price newp,HisPriceList oldp){
		try{
			String nowTime=DateTimeUtil.getDateTime();
			int newflag = DateTimeUtil.compare_date(nowTime, newp.getStop_date());
			String oldendtime= DateTimeUtil.shortFmt2(oldp.getStop_date());
			int oldflag = DateTimeUtil.compare_date(nowTime,oldendtime);
			if((oldflag==-1)&&(newflag==1)){//有效变无效
				insertHisLog(newp,1);
			}else if((oldflag==1)&&(newflag==-1)){ //无效变有效
				insertHisLog(newp,2);
			}else{
				if(oldp.getPrice()!=newp.getPrice()){
					//价表变化
					insertHisLog(newp,3);
				}
			}			
		}catch(Exception ex){
			ex.toString();
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	     * @Title: insertHisLog   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param newp
	     * @param: @param types
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	private void insertHisLog(Price newp, int types) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" select m.item_code,m.item_name,n.clinic_item_code,clinic_item_class from charging_item m,"
				+ "(select distinct a.clinic_item_code,a.clinic_item_class from his_clinic_item_v_price_list a "
				+ "where a.charge_item_class='" + newp.getItem_class() + "' and a.charge_item_code='"
				+ newp.getItem_code() + "' and a.charge_item_spec='" + newp.getItem_spec() + "' and a.units='"
				+ newp.getUnits() + "' group by a.clinic_item_code,a.clinic_item_class) n where "
						+ "n.clinic_item_code=m.his_num and n.clinic_item_class=m.item_class");
		System.out.println(sb.toString());
		List<HisPriceItemDTO> hisPriceList = new ArrayList<HisPriceItemDTO>();
		hisPriceList = this.jqm.getList(sb.toString(), HisPriceItemDTO.class);
		if ((hisPriceList != null) && (hisPriceList.size() > 0)) {
			String memo = "";
			for (HisPriceItemDTO hd : hisPriceList) {
				memo = memo + hd.getItem_code() + "(" + hd.getItem_name() + ")-" + hd.getClinic_item_code() + "("
						+ hd.getClinic_item_class() + ");";
			}
			if(memo.length()>300) memo=memo.substring(0, 300);
			String sql = "insert into his_price_list_log(item_class,item_code,item_name,"
					+ "item_spec,units,price,start_date,stop_date,types,memo,create_date ) values('"
					+ newp.getItem_class() + "','" + newp.getItem_code() + "'" + ",'" + newp.getItem_name() + "','"
					+ newp.getItem_spec() + "','" + newp.getUnits() + "'," + newp.getPrice() + ",'"
					+ newp.getStart_date() + "','" + newp.getStop_date() + "','" + types + "','" + memo + "'" + ",'"
					+ DateTimeUtil.getDateTime() + "')";
			System.out.println(sql);
			this.jpm.executeSql(sql);
		}
	}
	
	/**
	 * 
	     * @Title: saveAcceptDate   
	     * @Description: 同步诊疗项目 与价表关系
	     * @param: @param diagnList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveAcceptDate(List<DiagnosisPrice> diagnList) throws ServiceException {

		for (DiagnosisPrice price : diagnList) {
			try{
			if ("0".equals(price.getAction())) {// 删除
				// List<HisClinicItemPrice> hisPriceList = this.qm.find("from
				// HisClinicItemPrice where charge_item_code =
				// '"+price.getCharge_item_code()+"'");
				// if(hisPriceList.size() != 0){
				// HisClinicItemPrice hisClinoc = hisPriceList.get(0);
				// hisClinoc.setIs_actives("N");
				// this.pm.update(hisClinoc);
				// }
			} else {// 插入
				
				StringBuffer sb = new StringBuffer();
				sb.append("select clinic_item_class,clinic_item_code,charge_item_no,"
						+ "charge_item_class,charge_item_code,charge_item_spec,amount,units,backbill_rule"
						+ " from his_clinic_item_v_price_list where charge_item_no='"+price.getCharge_item_no()
						+"' and clinic_item_class='"+price.getClinic_item_class()+"' and clinic_item_code='"
						+price.getClinic_item_code()+"' ");
				System.out.println(sb.toString());
				List<DiagnosisPrice> hisPriceList = this.jqm.getList(sb.toString(), DiagnosisPrice.class);
				if (hisPriceList.size() ==1) {
					String sql = "update his_clinic_item_v_price_list set clinic_item_class = '"
							+ price.getClinic_item_class() + "'" + ",clinic_item_code = '" + price.getClinic_item_code()
							+ "',charge_item_no = '" + price.getCharge_item_no() + "'" + ",charge_item_class = '"
							+ price.getCharge_item_class() + "',charge_item_code='"+price.getCharge_item_code()
							+"',charge_item_spec='" + price.getCharge_item_spec() + "'"
							+ ",amount = " + price.getAmount() + ",units = '" + price.getUnits() + "',backbill_rule='"
							+ price.getBackbill_rule() + "',update_date='"+DateTimeUtil.getDateTime()+"' "
							+ "where charge_item_no='"+price.getCharge_item_no()
						   +"' and clinic_item_class='"+price.getClinic_item_class()+"' and clinic_item_code='"
						+price.getClinic_item_code()+"'";
					System.out.println(sql);
					this.jpm.executeSql(sql);
				} else if (hisPriceList.size()<=0){
					String sql = "insert into his_clinic_item_v_price_list("
							+ "clinic_item_class,clinic_item_code,charge_item_class,charge_item_code,charge_item_no,"
							+ "charge_item_spec,amount,units,backbill_rule,create_date,update_date) values('" + price.getClinic_item_class()
							+ "','" + price.getClinic_item_code() + "','" + price.getCharge_item_class() + "','" 
							+ price.getCharge_item_code() + "','" + price.getCharge_item_no() + "','"
							+ price.getCharge_item_spec() + "','" + price.getAmount() + "','" + price.getUnits() + "','"
							+ price.getBackbill_rule() + "','"+DateTimeUtil.getDateTime()+"','"+DateTimeUtil.getDateTime()+"')";
					System.out.println(sql);
					this.jpm.executeSql(sql);
				}
			}
			}catch(Exception ex){
				ex.toString();
				ex.printStackTrace();
			}
		}
		return "ok-保存成功!";
	}

	/**
	 * 
	     * @Title: saveDiagnosisItem   
	     * @Description: 同步诊疗项目  
	     * @param: @param diagnosisItemList
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveDiagnosisItem(List<DiagnosisItem> diagnosisItemList) throws ServiceException {

		for (DiagnosisItem diagnosisItem : diagnosisItemList) {
			try{
			if(diagnosisItem.getItem_name().indexOf("'")>=0){
				diagnosisItem.setItem_name(diagnosisItem.getItem_name().replaceAll("'", "''"));
			}
			
			if(diagnosisItem.getExpand1().indexOf("'")>=0){
				diagnosisItem.setExpand1(diagnosisItem.getExpand1().replaceAll("'", "''"));
			}
			if(diagnosisItem.getExpand2().indexOf("'")>=0){
				diagnosisItem.setExpand2(diagnosisItem.getExpand2().replaceAll("'", "''"));
			}
			if(diagnosisItem.getExpand3().indexOf("'")>=0){
				diagnosisItem.setExpand3(diagnosisItem.getExpand3().replaceAll("'", "''"));
			}
			if ("0".equals(diagnosisItem.getAction())) {// 删除
				// List<HisClinicItem> hisPriceList = this.qm.find("from
				// HisClinicItem where item_code =
				// '"+diagnosisItem.getItem_code()+"'");
				// if(hisPriceList.size() != 0){
				// HisClinicItem hisClinicItem = hisPriceList.get(0);
				// hisClinicItem.setIs_active("N");
				// this.pm.update(hisClinicItem);
				// }
			} else {// 插入  ITEM_CLASS, ITEM_CODE
				StringBuffer sb = new StringBuffer();
				sb.append("select item_class,item_code,item_name,input_code,expand1,expand2,expand3,item_status"
						+ " from his_clinic_item where item_class='"
						+diagnosisItem.getItem_class()+"' and item_code='"+diagnosisItem.getItem_code()+"'");
				System.out.println(sb.toString());
				List<HisClinicItem> hisClinicItem = this.jqm.getList(sb.toString(), HisClinicItem.class);
				
				if (hisClinicItem.size() ==1) {
					String sql = "update his_clinic_item set item_class = '" + diagnosisItem.getItem_class()
							+ "',item_name = '" + diagnosisItem.getItem_name() + "'" + ",input_code = '"
							+ diagnosisItem.getInput_code() + "',expand1 = '" + diagnosisItem.getExpand1() + "'"
							+ ",expand2 = '" + diagnosisItem.getExpand2() + "',expand3 = '" + diagnosisItem.getExpand3()
							+ "'" + ",item_status = '" + diagnosisItem.getItem_status() + "',update_date='"+DateTimeUtil.getDateTime()+"' where item_class='"
						+diagnosisItem.getItem_class()+"' and item_code='"+diagnosisItem.getItem_code()
						+"'";
					System.out.println(sql);
					this.jpm.executeSql(sql);
				} else if (hisClinicItem.size() ==0) {
					String sql = "insert into his_clinic_item (item_class,item_code,item_name,input_code,expand1,expand2,"
							+ "expand3,item_status,create_date,update_date) values('" + diagnosisItem.getItem_class() + "','"
							+ diagnosisItem.getItem_code() + "'" + ",'" + diagnosisItem.getItem_name() + "','"
							+ diagnosisItem.getInput_code() + "','" + diagnosisItem.getExpand1() + "'" + ",'"
							+ diagnosisItem.getExpand2() + "','" + diagnosisItem.getExpand3() + "','"
							+ diagnosisItem.getItem_status() + "','"+DateTimeUtil.getDateTime()+"','"+DateTimeUtil.getDateTime()+"')";
					System.out.println(sql);
					this.jpm.executeSql(sql);
				}
			}
			}catch(Exception ex){
				ex.toString();
				ex.printStackTrace();
			}
		}
		return "ok-保存成功!";
	}

	/**
	 * 
	     * @Title: saveUserData   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param users
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserData(List<User> users) throws ServiceException {
		for (User user : users) {
			try {
				if ((user.getPASSWD().trim().length() > 0)&&(user.getSTAFF_CODE().trim().length()>0)&&(user.getACTION_TYPE().trim().length()>0)) {
				if(StringUtils.isNumeric(user.getSTAFF_CODE())){
				if("0".equals(user.getACTION_TYPE().trim())){//1新增2更新0删除
					long user_id =getUser(user.getSTAFF_CODE().trim());
	                   if(user_id>0){//插入
	                	   user.setSTATUS("1");
	                	   updateUser(user_id,user);  
	                   }  
				}else{//1新增2更新0删除
					long user_id =getUser(user.getSTAFF_CODE().trim());
	                   if(user_id>0){//插入
	                	   updateUser(user_id,user);  
	                   }/*else{
	                	   this.insertUser(user);
	                   }*/
				}  
				}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return "ok-保存成功!";
	}
	
	/**
	 * * @Title: insertHisdept @Description: 新插入 @param: @param
	 * hisdep @param: @throws ServiceException @return: void @throws
	 */
	private void insertUser(User user) throws ServiceException {
		long center_id=this.getexaminatioin_center("TJZX1001");
		String newpasswd=EncryptUtils.encryptMD5(user.getPASSWD()).toLowerCase();
		String is_active="Y";
		if("0".equals(user.getSTATUS())){
			is_active="Y";
		}else{
			is_active="N";
		}
		String sql = "insert into user_usr(exam_center_id,chi_name,log_name,pwd_encrypted,is_active,"
				+ "email,phone_num,creater,create_time,updater,update_time) values('"
				+ center_id + "','" + user.getNAME() + "','" +user.getSTAFF_CODE() + "','"
				+ newpasswd + "','"+is_active+"','"+user.getE_MAIL()+"','"
				+ user.getPHONE()+"',null,'" + DateTimeUtil.getDateTime() + "',null,'" + DateTimeUtil.getDateTime() + "')";
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (Exception ex) {
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
	 * @Title: updateHisdept @Description: 更新 @param: @param
	 *         hisdep @param: @throws ServiceException @return: void @throws
	 */
	private void updateUser(long userId,User user) throws ServiceException {		
		//long center_id=this.getexaminatioin_center("TJZX1001");
		String newpasswd=EncryptUtils.encryptMD5(user.getPASSWD()).toLowerCase();
		String is_active="Y";
		if("0".equals(user.getSTATUS())){
			is_active="Y";
		}else{
			is_active="N";
		}		
		String sql = "update user_usr set chi_name='" + user.getNAME() + "',log_name='"	
		+ user.getSTAFF_CODE() + "',pwd_encrypted='" + newpasswd + "',is_active='"
				+ is_active + "',update_time='" + DateTimeUtil.getDateTime() + "' where id='" + userId + "'";
		Connection connection = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (Exception ex) {
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
	 * @Title: updateHisdept @Description: 更新 @param: @param
	 *         hisdep @param: @throws ServiceException @return: void @throws
	 */
	private long getUser(String log_name) throws ServiceException {
		String sql = "select id from  user_usr where work_num='" + log_name	+ "'";
		Connection connection = null;
		Statement statement = null;
		long id=0;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			// 处理结果
			if (rs.next()) {
				id=rs.getLong("id");
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
		return id;
	}
	
	
	/**
	 * 
	     * @Title: getexaminatioin_center   
	     * @Description:获取体检中心编码   
	     * @param: @param log_name
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws  
	 */
	private long getexaminatioin_center(String center_num) throws ServiceException {
		String sql = "select id from  examinatioin_center where center_num='" + center_num	+ "' and is_active='Y'";
		Connection connection = null;
		Statement statement = null;
		long id=0;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			// 处理结果
			if (rs.next()) {
				id=rs.getLong("id");
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
		return id;
	}
	
	public static String DateDiff(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)
				- days);// 让日期加1
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dt = sdf.format(calendar.getTime());
		return dt;
	}
	
	public void updateSampleExamDetailByExamid(String examnum, long samp_id, String status)
			throws ServiceException {
		String sql = "update sample_exam_detail set status = '"+status+"' where exam_num=" + examnum + " and sample_id=" + samp_id + "";
		System.out.println(sql);
		this.jpm.executeSql(sql);
	}
	
	/**
	 * 
	     * @Title: saveUserData   
	     * @Description: 保存lis 检查项目信息   
	     * @param: @param users
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public boolean saveLisClass(TableBean tb) throws ServiceException {
		boolean flag = false;
		System.out.println("dd");
		try {
			tb.setName(com.hjw.util.StringUtil.escapeExprSpecialWord(tb.getName()));
			StringBuffer sb = new StringBuffer();
			sb.append("select lisid,lisclassname,create_time,update_time  FROM thrid_lis_class where lisid='"
					+ tb.getNum() + "'");
			System.out.println(sb.toString());
			List<ThridLisClassDTO> hisPriceList = this.jqm.getList(sb.toString(), ThridLisClassDTO.class);
			if (hisPriceList.size() == 1) {
				String sql = "update thrid_lis_class set lisid = '" + tb.getNum() + "'" + ",lisclassname = '"
						+ tb.getName() + "',update_time = '" + DateTimeUtil.getDateTime() + "',iSampleId='"
						+tb.getiSampleId()+"',testtubeName='"+tb.getTesttubeName()+"',tubeID='"+tb.getTubeID()+"' " 
						+ "where lisid='"
						+ tb.getNum() + "'";
				System.out.println(sql);
				this.jpm.executeSql(sql);
			} else if (hisPriceList.size() <= 0) {
				String sql = "insert into thrid_lis_class(lisid,lisclassname,update_time,create_time,iSampleId,testtubeName,tubeID) values('"
						+ tb.getNum() + "','" + tb.getName() + "','" + DateTimeUtil.getDateTime() + "','"
						+ DateTimeUtil.getDateTime() + "','"+tb.getiSampleId()+"','"+tb.getTesttubeName()+"','"+tb.getTubeID()+"')";
				System.out.println(sql);
				this.jpm.executeSql(sql);
			}
			flag = true;
		} catch (Exception ex) {
			ex.toString();
			ex.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 
	     * @Title: saveUserData   
	     * @Description: 保存lis 检查项目细项   
	     * @param: @param users
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public void saveLisClassItem(String lisclassid,List<TableItemBean> tblist) throws ServiceException{
		boolean flag = false;
		try {
			for(TableItemBean tb:tblist){
			StringBuffer sb = new StringBuffer();
			tb.setName(com.hjw.util.StringUtil.escapeExprSpecialWord(tb.getName()));
			sb.append("select lisitemid,lisitemname,create_time,update_time FROM thrid_lis_item where lisitemid='"
					+ tb.getNum() + "'");
			System.out.println(sb.toString());
			List<ThridLisItemDTO> hisPriceList = this.jqm.getList(sb.toString(), ThridLisItemDTO.class);
			if (hisPriceList.size() == 1) {
				String sql = "update thrid_lis_item set lisitemid = '" + tb.getNum() + "'" + ",lisitemname = '"
						+ tb.getName() + "',update_time = '" + DateTimeUtil.getDateTime() + "' " + "where lisitemid='"
						+ tb.getNum() + "'";
				System.out.println(sql);
				this.jpm.executeSql(sql);
			} else if (hisPriceList.size() <= 0) {
				String sql = "insert into thrid_lis_item(lisitemid,lisitemname,create_time,update_time) values('"
						+ tb.getNum() + "','" + tb.getName() + "','" + DateTimeUtil.getDateTime() + "','"
						+ DateTimeUtil.getDateTime() + "')";
				System.out.println(sql);
				this.jpm.executeSql(sql);
			}
			
			
			sb = new StringBuffer();
			sb.append("select lisclassid,lisitemid,create_time,update_time FROM thrid_lis_class_item where lisitemid='"
					+ tb.getNum() + "' and lisclassid='"+lisclassid+"'");
			System.out.println(sb.toString());
			List<ThridLisClassItemDTO> itemclass = this.jqm.getList(sb.toString(), ThridLisClassItemDTO.class);
			if (itemclass.size() == 1) {
				String sql = "update thrid_lis_class_item set lisitemid = '" + tb.getNum() + "'" + ",lisclassid = '"
						+ lisclassid + "',update_time = '" + DateTimeUtil.getDateTime() + "' " + "where lisitemid='"
					+ tb.getNum() + "' and lisclassid='"+lisclassid+"'";
				System.out.println(sql);
				this.jpm.executeSql(sql);
			} else if (itemclass.size() <= 0) {
				String sql = "insert into thrid_lis_class_item(lisclassid,lisitemid,create_time,update_time) values('" 
			+ lisclassid + "','"+ tb.getNum() + "','" + DateTimeUtil.getDateTime() + "','"+ DateTimeUtil.getDateTime() + "')";
				System.out.println(sql);
				this.jpm.executeSql(sql);
			}
			}
			
		} catch (Exception ex) {
			ex.toString();
			ex.printStackTrace();
		}
	}
	
	/**
	 * 
	     * @Title: getLisClass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ThridLisClassDTO      
	     * @throws
	 */
	public ThridLisClassDTO getLisClass(String id) throws ServiceException {
		ThridLisClassDTO flag = new ThridLisClassDTO();
			StringBuffer sb = new StringBuffer();
			sb.append("select lisid,lisclassname,iSampleId,testtubeName,tubeID FROM thrid_lis_class where lisid='"	+ id + "'");
			System.out.println(sb.toString());
			List<ThridLisClassDTO> hisPriceList = this.jqm.getList(sb.toString(), ThridLisClassDTO.class);
			if (hisPriceList.size() >= 1) {
				flag=hisPriceList.get(0);
			}	
		return flag;
	}
}
