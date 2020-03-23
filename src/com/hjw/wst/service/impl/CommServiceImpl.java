package com.hjw.wst.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.util.TranLogTxt;
import com.hjw.webService.service.bean.ResultSerBody;
import com.hjw.webService.service.bean.RetCharges;
import com.hjw.webService.service.bean.RetReqNo;
import com.hjw.webService.service.bean.SgtZMsg;
import com.hjw.webService.service.bean.SgtZRequestInMsg;
import com.hjw.webService.service.lisbean.ChargeType;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.DepExamResultDTO;
import com.hjw.wst.DTO.DepInspectExamIntionDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.HisClinicItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.MultiSignCollector;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.ProcListResult;
import com.hjw.wst.DTO.ProcPacsResult;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserFeeDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.domain.ChargingInvoiceSingle;
import com.hjw.wst.domain.ChargingSummarySingle;
import com.hjw.wst.domain.ChargingWaySingle;
import com.hjw.wst.domain.CommonExamDetail;
import com.hjw.wst.domain.ExamdepResult;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.HisChargingWaySingle;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DepInspectModel;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepInspectService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: yangm
 * @date: 2016年7月4日 上午9:37:08
 * @version V2.0.0.0
 */
public class CommServiceImpl implements CommService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private CustomerInfoService customerInfoService;
	private LisPacsApplicationService lisPacsApplicationService;
	private DepInspectService depInspectService;

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setDepInspectService(DepInspectService depInspectService) {
		this.depInspectService = depInspectService;
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
	public ExamInfoUserDTO getExamInfoForNum(String exam_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,c.age,c.exam_num,c.status,c.exam_type"
				+ ",c.register_date,c.join_date,c.exam_times ");
		sb.append(" from exam_info c ");
		sb.append(" where c.is_Active='Y' ");		
		sb.append(" and c.exam_num = '" + exam_num + "' ");	
		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, ExamInfoUserDTO.class);
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if((map!=null)&&(map.getList().size()>0)){
			eu= (ExamInfoUserDTO)map.getList().get(0);			
		}
		return eu;
	} 

	/**
	 * 
	 * @Title: setExamInfoChargeItemStatus @Description: pacs
	 *         更新状态 @param: @param req_num @param: @param
	 *         exam_num @param: @param status @param: @throws
	 *         ServiceException @return: void @throws
	 */
	public void setExamInfoChargeItemPacsStatus(List<String> req_nums, String exam_num, String status, String center_num)
			throws ServiceException {
		// 插入数据库
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			// connection.setAutoCommit(false);
			for (String pac_no : req_nums) {
				String sql = " select a.id,a.exam_status from examinfo_charging_item a,exam_info m"
						+ ",pacs_summary b,pacs_detail c,charging_item d" + " where b.pacs_req_code='" + pac_no
						+ "' and b.examinfo_num='" + exam_num + "' "
						+ "and c.summary_id=b.id and c.chargingItem_num=d.item_code "
						+ "and d.id=a.charge_item_id and a.exam_num=m.exam_num "
						+ "and m.exam_num=b.examinfo_num and a.isActive='Y'";
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				// 处理结果
				while (rs.next()) {
					long exam_id = rs.getLong("id");
					String exam_status = rs.getString("exam_status");
					if (!"Y".equals(exam_status)) {
						sql = "update examinfo_charging_item set exam_status='" + status + "',ec.exam_center_num = '"+center_num+"' where id=" + exam_id + "";
						connection.createStatement().executeUpdate(sql);
					}
				}
				rs.close();
			}
			// connection.commit();
		} catch (Exception ex) {
			
//			 try { connection.rollback(); } catch (SQLException e) {
//			 
//			 }
			 
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
	 * @Title: setExamInfoChargeItemLisStatus @Description: lis
	 *         状态更改 @param: @param req_num @param: @param
	 *         exam_num @param: @param status @param: @throws
	 *         ServiceException @return: void @throws
	 */
	public void setExamInfoChargeItemLisStatus(List<String> req_nums, String exam_num, String status, String samstatus, String center_num)
			throws ServiceException {
		// 插入数据库
		Connection connection = null;
		Statement statement = null;
		try {
			// 读取记录数
			connection = this.jqm.getConnection();
			// connection.setAutoCommit(false);
			for (String pac_no : req_nums) {
				String sql = "select e.id as examid,e.exam_status as examstau,a.id as samid,a.status as samsta "
						+ "from sample_exam_detail a," 
						+ "examResult_chargingItem c,charging_item d,"
						+ "examinfo_charging_item e  where a.sample_barcode='" + pac_no + "' "
						+ "and a.exam_num='" + exam_num + "' "
						+ "and a.sample_barcode = c.bar_code and c.result_type='sample' and c.charging_item_code=d.item_code "
						+ "and e.exam_num=a.exam_num and e.charge_item_id=d.id";
				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				// 处理结果
				while (rs.next()) {
					long exam_id = rs.getLong("examid");
					String exam_status = rs.getString("examstau");
					if (!"Y".equals(exam_status)) {
						sql = "update examinfo_charging_item set exam_status='" + status + "',ec.exam_center_num = '"+center_num+"' where id=" + exam_id + "";
						connection.createStatement().executeUpdate(sql);

						long samid = rs.getLong("samid");
						// String samsta = rs.getString("samsta");
						sql = "update sample_exam_detail set status='" + samstatus + "' where id=" + samid + "";
						connection.createStatement().executeUpdate(sql);
					}
				}
				rs.close();
			}
			// connection.commit();
		} catch (Exception ex) {
			
//			 try { connection.rollback(); } catch (SQLException e) {
//			 
//			 }
			 
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

			// 执行存储过程啊闪光灯
			c.execute();
			// 得到存储过程的输出参数值
			typeid = c.getInt(13);
			
			StringBuffer sb= new StringBuffer();
			sb.append("  declare @res int ");
			sb.append("exec proc_Lis_result '"+pr.getBar_code()+"','"+pr.getExam_num()+"','"
			+pr.getLis_item_code()+"','"+pr.getLis_rep_item_code()+"','"+pr.getExam_doctor()+"','"
			+pr.getExam_date()+"','"+pr.getExam_result()+"','"+pr.getRef_value()+"','"
			+pr.getItem_unit()+"','"+pr.getRef_indicator()+"','"+pr.getApprover()+"','"
			+pr.getApprove_date()+"', @res output  ");
			if(typeid!=0){
				TranLogTxt.liswriteEror_to_txt("lisproc_error", "res:" + sb.toString());
			}else{
				TranLogTxt.liswriteEror_to_txt("lisproc_success", "res:" + sb.toString());
			}
			
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
			StringBuffer sb= new StringBuffer();
			sb.append("  declare @res int,@res1 varchar(200) ");
			sb.append(" exec proc_pacs_report_dbgj '"+pr.getPacs_req_code()+"','"+pr.getExam_num()+"','"
			+pr.getCheck_date()+"','"+pr.getCheck_doct()+"','"+pr.getAudit_date()+"','"
			+pr.getAudit_doct()+"','"+pr.getExam_result()+"','"+pr.getExam_desc()+"','"
			+pr.getBodyPart()+"','"+pr.getExamMethod()+"','"+pr.getImg_file()+"',@res output,@res1 output");
			TranLogTxt.liswriteEror_to_txt("pacsproc_error", "res:" + sb.toString());
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
			
			if(typeid!=0){
				sb.append("         "+c.getString(13));
				TranLogTxt.liswriteEror_to_txt("pacsproc_error", "res:" + sb.toString());
			}else{
				TranLogTxt.liswriteEror_to_txt("pacsproc_success", "res:" + sb.toString());
			}
		} catch (Exception ex) {
			TranLogTxt.liswriteEror_to_txt("pacsproc_error", "res:" + ex.getMessage());
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

	@SuppressWarnings("unchecked")
	@Override
	public UserFeeDTO saveFeesResult(ResultSerBody rb, String center_num) throws ServiceException {
		UserFeeDTO uf=new UserFeeDTO();
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
			uf.setFlags(false);
			uf.setError("收据号不能为空");
            return uf;
		}
		if ("".equals(charges.getOPERATOR_NO())) {
			uf.setFlags(false);
			uf.setError("收费员编号不能为空!");
            return uf;
		}
		Double amount = 0.0;
		try {
			DecimalFormat df = new DecimalFormat("#.00");
			String temp = df.format(Double.valueOf(charges.getCHARGE()));
			amount = Double.valueOf(temp);
		} catch (NumberFormatException e) {
			uf.setFlags(false);
			uf.setError("收费总金额格式错误!");
            return uf;
		}

		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(charges.getCHARGE_DATE());
		} catch (ParseException ex) {
			uf.setFlags(false);
			uf.setError("收费日期格式错误!");
            return uf;
		}

		String sql3 = "select d.id from data_dictionary d where "
				+ "d.data_code = 'SFFSLX' and d.isActive = 'Y' and d.remark = 'his收费'";
		List<DataDictionaryDTO> dicList = this.jqm.getList(sql3, DataDictionaryDTO.class);
		if (dicList.size() == 0) {
			uf.setFlags(false);
			uf.setError("未匹配到His收费方式,请检查His收费方式编码是否对应");
            return uf;
		}
		long sffslx = dicList.get(0).getId();
		String sql1 = "select u.id,u.chi_name,u.work_num from user_usr u where u.work_num = '"
				+ charges.getOPERATOR_NO() + "'";
		List<UserInfoDTO> userList = this.jqm.getList(sql1, UserInfoDTO.class);
		if (userList.size() == 0) {
			uf.setFlags(false);
			uf.setError("未匹配到收费员信息!");
            return uf;
		}

		List<RetReqNo> reqNumList = charges.getREQ_NOS().getITEM();
		List<ChargingSummarySingle> summarySingleList = new ArrayList<ChargingSummarySingle>();
		double amount2 = 0.0;
		for (RetReqNo reqNum : reqNumList) {
			String sql2 = "from ChargingSummarySingle c where c.req_num = '" + reqNum.getREQ_NO() + "'";

			List<ChargingSummarySingle> summaryList = this.qm.find(sql2);
			if (summaryList.size() == 0) {
				uf.setFlags(false);
				uf.setError("申请单号未匹配的正确的信息!");
	            return uf;
			}
			ChargingSummarySingle chargingSummarySingle = summaryList.get(0);
			amount2 += chargingSummarySingle.getAmount2().doubleValue();
			chargingSummarySingle.setCharging_status("Y");
			chargingSummarySingle.setIs_print_recepit("Y");
			chargingSummarySingle.setCashier(Long.valueOf(userList.get(0).getId()));
			chargingSummarySingle.setCash_date(date);
			chargingSummarySingle.setUpdate_time(date);
			chargingSummarySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
			chargingSummarySingle.setExam_num(summaryList.get(0).getExam_num());

			summarySingleList.add(chargingSummarySingle);
		}
		
		try {
			DecimalFormat df = new DecimalFormat("#.00");
			String temp = df.format(Double.valueOf(amount2));
			amount2 = Double.valueOf(temp).doubleValue();
		} catch (NumberFormatException e) {
			uf.setFlags(false);
			uf.setError("收费总金额格式错误!");
            return uf;
		}
		
		if (amount.doubleValue() != amount2) {
			uf.setFlags(false);
			uf.setError("总金额不相同,请检查HIS价表是否同步最新的数据!HIS金额："+amount+",体检金额:"+amount2);
            return uf;
		}

		List<ChargeType> chargeTypeList = charges.getCHARGE_TYPES().getCHARGE_TYPE();

		List<HisChargingWaySingle> hisChargingWayList = new ArrayList<HisChargingWaySingle>();
		for (ChargeType chargeType : chargeTypeList) {
			
//			 * String sql4 = "select d.id from data_dictionary d where " +
//			 * "d.data_code = 'SFFSLX' and d.isActive = 'Y' and d.remark = '" +
//			 * chargeType.getCHARGE_TYPE() + "'"; List<DataDictionaryDTO>
//			 * dicList1 = this.jqm.getList(sql4, DataDictionaryDTO.class); if
//			 * (dicList1.size() == 0) { return "error-未匹配到收费方式,请检查收费方式编码是否对应"; }
			 
			HisChargingWaySingle hisChargingWaySingle = new HisChargingWaySingle();

			hisChargingWaySingle.setAccount_num(charges.getRCPT_NO());
			hisChargingWaySingle.setCharging_way(dicList.get(0).getId() + "");
			try {
				hisChargingWaySingle.setAmount(Double.parseDouble(chargeType.getAMOUNT()));
			} catch (NumberFormatException e) {
				uf.setFlags(false);
				uf.setError("收费方式金额格式错误!");
	            return uf;
			}
			hisChargingWaySingle.setCreater(Long.valueOf(userList.get(0).getId()));
			hisChargingWaySingle.setCreate_time(date);
			hisChargingWaySingle.setUpdater(Long.valueOf(userList.get(0).getId()));
			hisChargingWaySingle.setUpdate_time(date);

			hisChargingWayList.add(hisChargingWaySingle);
		}
		// ------------开始保存数据
		ChargingInvoiceSingle chargingInvoiceSingle = new ChargingInvoiceSingle();

		chargingInvoiceSingle.setTitle_info("");
		chargingInvoiceSingle.setInvoice_type("");
		chargingInvoiceSingle.setInvoice_num(charges.getINVOICE_NO());
		chargingInvoiceSingle.setInvoice_amount(amount);
		chargingInvoiceSingle.setInvoice_status("Y");
		chargingInvoiceSingle.setInvoice_maker(Long.valueOf(userList.get(0).getId()));
		chargingInvoiceSingle.setInvoice_time(date);
		chargingInvoiceSingle.setCreate_time(date);
		chargingInvoiceSingle.setCreater(Long.valueOf(userList.get(0).getId()));
		chargingInvoiceSingle.setUpdate_time(date);
		chargingInvoiceSingle.setDaily_status("0");
		chargingInvoiceSingle.setUpdater(Long.valueOf(userList.get(0).getId()));
		chargingInvoiceSingle.setAccount_num(charges.getRCPT_NO());
		chargingInvoiceSingle.setExam_type("G");
		this.pm.save(chargingInvoiceSingle);

		String req_nums = "";
		for (ChargingSummarySingle chargingSummarySingle : summarySingleList) {
			req_nums += "'" + chargingSummarySingle.getReq_num() + "',";
			chargingSummarySingle.setInvoice_id(chargingInvoiceSingle.getId());
			this.pm.update(chargingSummarySingle);

			ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
			chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
			chargingWaySingle.setCharging_way(sffslx + "");
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

		String sql4 = " select cs.exam_num, cd.charging_item_id" + " from ChargingSummarySingle cs,"
				+ "ChargingDetailSingle cd where cd.summary_id=cs.id " + " and cs.req_num in ("
				+ req_nums.substring(0, req_nums.length() - 1) + ")";
		List iteList = new ArrayList();
		iteList = this.qm.find(sql4);
		for (int j = 0; j < iteList.size(); j++) {
			Object[] object = (Object[]) iteList.get(j);
			String exam_num = (String) object[0];
			long ciid = (long) object[1];
			String sql2 = "from ExaminfoChargingItem c where exam_num = '" + exam_num + "' and charge_item_id=" + ciid
					+ " and pay_status='N' and center_num = '"+center_num+"' and isActive='Y'";
			List<ExaminfoChargingItem> eciList = this.qm.find(sql2);
			if (eciList.size() > 0) {
				ExaminfoChargingItem ecinew = new ExaminfoChargingItem();
				ecinew = eciList.get(0);
				ecinew.setPay_status("Y");
				this.pm.update(ecinew);
			}
		}

		try{
			uf.setUsername(userList.get(0).getChi_Name());
			uf.setWork_num(userList.get(0).getWork_num());
			req_nums = req_nums.substring(0, req_nums.length() - 1);
			uf.setReq_nums(req_nums);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		uf.setFlags(true);
		uf.setError("保存成功");
        return uf;
	}
	
	/**
	 * 
	     * @Title: sendPacsLis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param userList
	     * @param: @param req_nums      
	     * @return: void      
	     * @throws
	 */
	public void sendPacsLis(UserFeeDTO uf) throws ServiceException {
		try{
			// 发送lis/pacs申请
			UserDTO user = new UserDTO();
			user.setName(uf.getUsername());
			user.setWork_num(uf.getWork_num());

			String sql5 = " select distinct ei.exam_num " + " from ChargingSummarySingle cs,"
					+ "ChargingDetailSingle cd,ExamInfo ei where cd.summary_id=cs.id and ei.id=cs.exam_id "
					+ " and cs.req_num in (" + uf.getReq_nums()+ ")";
			List iteList5 = new ArrayList();
			iteList5 = this.qm.find(sql5);
			for (int j = 0; j < iteList5.size(); j++) {
				//Object[] object = (Object[]) iteList5.get(j);
				String examnum = (String) iteList5.get(j);
				String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
				String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
				String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
				String IS_HIS_LIS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_LIS_CHECK", user.getCenter_num()).getConfig_value()
						.trim();
				String IS_HIS_PACS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_PACS_CHECK", user.getCenter_num()).getConfig_value()
						.trim();
				try {
					if ("Y".equals(lis) && "N".equals(lis_bang)) {
						String s= this.lisPacsApplicationService.lisSend(examnum, null, user, false,lis_bang,IS_HIS_LIS_CHECK);
						System.out.println("收费成功以后发送lis 返回："+s);
					}

					if ("Y".equals(pacs)) {
						String s= this.lisPacsApplicationService.pacsSend(examnum, null, user, false,IS_HIS_PACS_CHECK);
						System.out.println("收费成功以后发送lis 返回："+s);
					}
				} catch (ServiceException e) {
					e.printStackTrace();
				}		
			}
		}catch (ServiceException e) {
			e.printStackTrace();
		}	
	}

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
			sbt.append("select b.dep_num from pacs_summary a,pacs_detail b  where a.pacs_req_code='" + pacs_req_code
					+ "' and a.id=b.summary_id");
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
	 * 
	     * @Title: itemTotal   
	     * @Description: 诊疗项目查询统计   
	     * @param: @param datetime
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<JobDTO>      
	     * @throws
	 */
		public List<JobDTO> itemTotal(String datetime) throws ServiceException {
			Connection connection = null;
			List<JobDTO> list = new ArrayList<JobDTO>();
			try {
				connection = this.jqm.getConnection();
				String sql = "select TOP 3 item_code,item_name from his_clinic_item where update_date >= '"+datetime+" 00:00:00' ";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				while (rs.next()) {
					JobDTO jb = new JobDTO();
					jb.setId(rs.getString("item_code"));
					jb.setName(rs.getString("item_name"));
					list.add(jb);
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
		     * @Title: priceTotal   
		     * @Description: 诊疗项目查询统计   
		     * @param: @param datetime
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<JobDTO>      
		     * @throws
		 */
		public List<JobDTO> priceTotal(String datetime) throws ServiceException {
			Connection connection = null;
			List<JobDTO> list = new ArrayList<JobDTO>();
			try {
				connection = this.jqm.getConnection();
				String sql = "select TOP 1 item_code,item_name from his_price_list_log where create_date >= '"+datetime+" 00:00:00' ";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				while (rs.next()) {
					JobDTO jb = new JobDTO();
					jb.setId(rs.getString("item_code"));
					jb.setName(rs.getString("item_name"));
					list.add(jb);
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
		     * @Title: priceShow   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param datetime
		     * @param: @param pageno
		     * @param: @param pagesize
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: PageReturnDTO      
		     * @throws
		 */
		public PageReturnDTO priceShow(String datetime, int pageno, int pagesize) throws ServiceException {
			String sql = "select id,item_class,item_code,item_name,item_spec,units,price,start_date,"
					+ "stop_date,types,memo,create_date "
					+ "from his_price_list_log where create_date >= '" + datetime + " 00:00:00' ";
			PageSupport map = this.jqm.getList(sql.toString(), pageno, pagesize, HisClinicItemDTO.class);
			PageReturnDTO webrole = new PageReturnDTO();
			webrole.setPage(pageno);
			webrole.setRp(pagesize);
			webrole.setRows(map.getList());
			webrole.setTotal(map.getRecTotal());
			return webrole;
		}
		
		/**
		 * 
		     * @Title: itemshow   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @param datetime
		     * @param: @return
		     * @param: @throws ServiceException      
		     * @return: List<JobDTO>      
		     * @throws
		 */
	public PageReturnDTO itemshow(String datetime, int pageno, int pagesize) throws ServiceException {
		String sql = "select item_class,item_code,item_name,item_spec,units,price,start_date,"
				+ "stop_date,types,memo,create_date,id,update_date from his_clinic_item "
				+ "where update_date >= '" + datetime + " 00:00:00' ";
		PageSupport map = this.jqm.getList(sql.toString(), pageno, pagesize, HisClinicItemDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		webrole.setRows(map.getList());
		webrole.setTotal(map.getRecTotal());
		return webrole;
	}
	
	/**
	 * 
	     * @Title: getCustExamInfoForNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getCustExamInfoForNum(String exam_num,UserDTO user) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.birthday,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type,c.is_Active"
				+ ",c.employeeID,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,m.dep_name,"
				+ "c.register_date,c.join_date,c.exam_times,c.batch_id,a.flag ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
		sb.append(" left join company_department  m on m.id = c._level  ");
		sb.append(" left join exam_summary  j on j.exam_num = c.exam_num AND j.center_num = '"+user.getCenter_num()+"' ");
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
			
			if((eu.getBirthday()!=null)&&(eu.getBirthday().trim().length()>10)){			
				eu.setBirthday(eu.getBirthday().trim().substring(0, 10));
				if(eu.getBirthday().indexOf("1900-01-01")>=0)
				{
					eu.setBirthday("");
				}else{
					eu.setBirthday(eu.getBirthday().trim().replaceAll("-", "/"));					
				}
			}
		}
		return eu;
	}
	
	/**
	 * 
	     * @Title: sgtzImpdo   
	     * @Description: 身高体重入库   
	     * @param: @param sgtzmsg
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public SgtZMsg sgtzImpdo(SgtZRequestInMsg sgtzmsg, String center_num) throws ServiceException {
		SgtZMsg msg = new SgtZMsg();
		
		String sql = "select e.id,e.exam_num,c.sex from exam_info e,customer_info c where c.id = e.customer_id and e.exam_num = '"+ sgtzmsg.getScanID() + "'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		
		ExamInfoDTO examInfo = null;
		if(list.size() == 0){
			msg.setStatus("ERROR");
			msg.setMsg("该体检号："+sgtzmsg.getScanID()+",体检信息不存在!");
			TranLogTxt.liswriteEror_to_txt("sgtzlog","req:体检信息不存在!");
			return msg;
		}else{
			examInfo = list.get(0);
		}
		
		String depSql = "select id from department_dep left join department_vs_center de on de.dep_id = department_dep.id  where dep_num = '007'  and de.center_num ='"+center_num+"'";
		List<DepartmentDepDTO> depList = this.jqm.getList(depSql, DepartmentDepDTO.class);
		DepartmentDepDTO dep = null;
		if(list.size() == 0){
			msg.setStatus("ERROR");
			msg.setMsg("一般科室检查的项目编码为007!");
			TranLogTxt.liswriteEror_to_txt("sgtzlog","req:一般科室检查的项目编码为007!");
			return msg;
		}else{
			dep = depList.get(0);
		}
		
		String sgTz_ExamDoctor_id = this.customerInfoService.getCenterconfigByKey("SGTZ_EXAMDOCTOR_ID", center_num).getConfig_value().trim();
		
		String userSql = "select u.id userid,c.center_num,u.chi_name name from user_usr u,examinatioin_center c where u.exam_center_id = c.id and u.id="+sgTz_ExamDoctor_id;
		List<UserDTO> userList = this.jqm.getList(userSql, UserDTO.class);
		UserDTO user = null;
		if(userList.size() == 0){
			msg.setStatus("ERROR");
			msg.setMsg("检查医生未配置正确!");
			TranLogTxt.liswriteEror_to_txt("sgtzlog","req:检查医生未配置正确!");
			return msg;
		}else{
			user = userList.get(0);
		}
		
		List<DepInspectExamIntionDTO> comList = this.depInspectService.getDepInspectExamIntion(examInfo.getId(),dep.getId(),"1",sgtzmsg.getScanID(), center_num);
		
		List<DepExamResultDTO> tempList = new ArrayList<DepExamResultDTO>();
		for(DepInspectExamIntionDTO com : comList){
			if("WL001".equals(com.getItem_num())){//身高
				com.setExam_result(sgtzmsg.getHeight()+"");
			}else if("WL002".equals(com.getItem_num())){//体重
				com.setExam_result(sgtzmsg.getWeight()+"");
			}else if("WL003".equals(com.getItem_num())){//体重指数
				com.setExam_result(sgtzmsg.getBMI()+"");
			}else if("WL301".equals(com.getItem_num())){//体温
				com.setExam_result(sgtzmsg.getTemperatrue());
			}
			
			com.setHealth_level("Z");
			if("1".equals(com.getBrief_mark()) && "0".equals(com.getBrief())){
				DepInspectModel dto = new DepInspectModel();
				dto.setId(com.getId());
				dto.setItem_category(com.getItem_category());
				dto.setExam_result(com.getExam_result());
				dto.setSex(sgtzmsg.getGender());
				
				int f = 1;
				if(com.getExam_result() != null){
					f=this.depInspectService.getItemintionReference(dto);
				}
				if(f == 3){
					com.setHealth_level("Y");
				}else if( f == 2){
					com.setHealth_level("W");
				}else{
					com.setHealth_level("Z");
				}
				
			}else if("1".equals(com.getBrief_mark()) && "1".equals(com.getBrief())){
				DepExamResultDTO dto = new DepExamResultDTO();
				dto.setItem_id(com.getId());
				dto.setExam_result(com.getExam_result());
				tempList.add(dto);
			}
		}
		
		List<DepExamResultDTO> listLogic = this.depInspectService.getMateDepLogic(tempList, dep.getId(),examInfo.getSex());
		TranLogTxt.liswriteEror_to_txt("sgtzlog","req:生成科室疾病!");
		for(DepInspectExamIntionDTO com : comList){
			if(listLogic != null){
				for(DepExamResultDTO depExam : listLogic){
					if(com.getId() == depExam.getItem_id()){
						com.setConclusion_word(depExam.getExam_result());
						com.setHealth_level("Y");
					}
				}
			}
		}
		
		String dep_result = "";
		String old_conclusion_word = "";
		int count = 1;
		String c_ids = ""; 
		for(DepInspectExamIntionDTO com : comList){
			List<CommonExamDetail> examli = this.depInspectService.findCommonExamDetail(examInfo.getExam_num(),com.getId());
			CommonExamDetail ced = null;
			if(examli.size()!= 0){
				ced = examli.get(0);
				if("".equals(com.getExam_result())){
					this.depInspectService.deleteCommonExamDetail(ced);
				}else{
					ced.setExam_info_id(examInfo.getId());//体检信息
					ced.setExam_item_id(com.getId());//检查项目
					ced.setExam_doctor(user.getName());//检查医生
					ced.setExam_date(DateTimeUtil.parse3(sgtzmsg.getCheckDate()));//检查时间
					ced.setExam_result(com.getExam_result());//检查结果
					ced.setHealth_level(com.getHealth_level());//健康指数
					ced.setCenter_num(user.getCenter_num());//体检中心
					ced.setUpdater(user.getUserid());//修改
					ced.setUpdate_time(DateTimeUtil.parse());//修改时间
					this.depInspectService.updateCommonExamDetail(ced);
					c_ids += com.getC_id()+",";
				}
			}else{
				if(com.getExam_result() != null && !"".equals(com.getExam_result())){
					ced = new CommonExamDetail();
					ced.setExam_info_id(examInfo.getId());//体检信息
					ced.setExam_item_id(com.getId());//检查项目
					ced.setExam_doctor(user.getName());//检查医生
					ced.setExam_date(DateTimeUtil.parse3(sgtzmsg.getCheckDate()));//检查时间
					ced.setExam_result(com.getExam_result());//检查结果
					ced.setHealth_level(com.getHealth_level());//健康指数
					ced.setCenter_num(user.getCenter_num());//体检中心
					ced.setCreater(user.getUserid());//创建人
					ced.setCreate_time(DateTimeUtil.parse());//创建时间
					ced.setUpdater(user.getUserid());//修改
					ced.setUpdate_time(DateTimeUtil.parse());//修改时间
					ced.setExam_num(examInfo.getExam_num());
					this.depInspectService.addCommonExamDetail(ced);
					c_ids += com.getC_id()+",";
				}
			}
			
			if(!com.getHealth_level().equals("Z")){
				if(com.getConclusion_word() != null && !"".equals(com.getConclusion_word())){
					if(!old_conclusion_word.equals(com.getConclusion_word())){
						dep_result +="("+count+")"+com.getConclusion_word()+"\n";
						old_conclusion_word = com.getConclusion_word();
						count ++;
					}
				}else{
					dep_result +="("+count+")"+com.getItem_name()+":"+com.getExam_result()+"\n";
					count ++;
				}
			}
		}
		TranLogTxt.liswriteEror_to_txt("sgtzlog","req:保存检查项目结果!");
		List<ExamdepResult> exm = this.depInspectService.findExamDepResultDTO(examInfo.getExam_num(),Long.valueOf(dep.getId()),"1");
		
		String isDepResultShow = this.customerInfoService.getCenterconfigByKey("IS_DEPRESULT_SHOW", user.getCenter_num()).getConfig_value().trim();
		if(dep_result.equals("")){
			dep_result = isDepResultShow;
		}
		if(exm.size()>0){//修改
			exm.get(0).setExam_info_id(examInfo.getId());//体检信息Id
			exm.get(0).setExam_doctor(user.getName());//检查医生
			exm.get(0).setDep_id(dep.getId());//检查科室
			exm.get(0).setExam_result_summary(dep_result);//科室检查结果
			exm.get(0).setSuggestion("");//科室建议
			exm.get(0).setCenter_num(user.getCenter_num());//体检中心
			exm.get(0).setSpecial_setup("");//特殊设置
			exm.get(0).setUpdater(user.getUserid());//修改人
			exm.get(0).setUpdate_time(DateTimeUtil.parse());//修改时间
			this.depInspectService.updateExamDepResult(exm.get(0));
	  }else{
			//保存结论
		   ExamdepResult  m = new ExamdepResult();
			m.setExam_info_id(examInfo.getId());//体检信息Id
			m.setExam_doctor(user.getName());//检查医生
			m.setDep_id(dep.getId());//检查科室
			m.setExam_result_summary(dep_result);//科室检查结果
			m.setSuggestion("");//科室建议
			m.setCenter_num(user.getCenter_num());//体检中心
			m.setSpecial_setup("");//特殊设置
			m.setCreater(user.getUserid());//创建人
			m.setCreate_time(DateTimeUtil.parse());//创建时间
			m.setUpdater(user.getUserid());//修改人
			m.setUpdate_time(DateTimeUtil.parse());//修改时间
			this.depInspectService.addDepInspect(m);
		}
		TranLogTxt.liswriteEror_to_txt("sgtzlog","req:保存科室检查结果!");
		//修改检查状态
		this.depInspectService.updateExamInfo(examInfo.getId());
		this.depInspectService.updateExaminfoChargingItem(examInfo.getExam_num(),c_ids.substring(0,c_ids.length()-1),user);
		
		msg.setStatus("SUCCESS");
		msg.setMsg("结果已成功写入!");
		return msg;
	}

	public String thirdPRRSave(MultiSignCollector model,String center_num ) throws ServiceException {
		
		TranLogTxt.liswriteEror_to_txt("dtzcjlog","req:"+model.getSignType());		
    	
    	String sql = "select e.id,e.exam_num,c.sex from exam_info e,customer_info c where c.id = e.customer_id and e.exam_num = '"+ model.getTag() + "'";
		List<ExamInfoDTO> list = this.jqm.getList(sql, ExamInfoDTO.class);
		
		ExamInfoDTO examInfo = null;
		if(list.size() == 0){
			String res="{error:\"体检信息不存在!\",status:\"ERROR\",msg:\"\"}";
			return res;
		}else{
			examInfo = list.get(0);
		}
		
		String depSql = "select id from department_dep left join department_vs_center de on de.dep_id = department_dep.id  where dep_num = '007' and de.center_num ='"+center_num+"'";
		List<DepartmentDepDTO> depList = this.jqm.getList(depSql, DepartmentDepDTO.class);
		DepartmentDepDTO dep = null;
		if(list.size() == 0){
			String res="{error:\"一般科室检查的项目编码为007!\",status:\"ERROR\",msg:\"\"}";
			return res;
		}else{
			dep = depList.get(0);
		}
		
		String sgTz_ExamDoctor_id = this.customerInfoService.getCenterconfigByKey("SGTZ_EXAMDOCTOR_ID", null).getConfig_value().trim();
		String userSql = "select u.id userid,c.center_num,u.chi_name name from user_usr u,examinatioin_center c where u.exam_center_id = c.id and u.id="+sgTz_ExamDoctor_id;
		List<UserDTO> userList = this.jqm.getList(userSql, UserDTO.class);
		UserDTO user = null;
		if(userList.size() == 0){
			String res="{error:\"检查医生未配置正确!\",status:\"ERROR\",msg:\"\"}";
			return res;
		}else{
			user = userList.get(0);
		}
		
		List<DepInspectExamIntionDTO> comList = this.depInspectService.getDepInspectExamIntion(examInfo.getId(),dep.getId(),"1",model.getTag(),"001");
		
		List<DepExamResultDTO> tempList = new ArrayList<DepExamResultDTO>();
		for(DepInspectExamIntionDTO com : comList){
			
			if("bp".equals(model.getSignType())){//血压=bp,血糖 bg，血氧 spo2 体温 temp
				if("WL004".equals(com.getItem_num())){//收缩压
					com.setExam_result(model.getSystolic().intValue()+"");
				}else if("WL005".equals(com.getItem_num())){//舒张压
					com.setExam_result(model.getDiastolic().intValue()+"");
				}else if("WL012".equals(com.getItem_num())){//心率
					com.setExam_result(model.getHeartrate().intValue()+"");
				}else if("WL006".equals(com.getItem_num())){//脉搏
					com.setExam_result(model.getPulserate());
				}
	    	}else if("bg".equals(model.getSignType())){//血压=bp,血糖 bg，血氧 spo2 体温 temp
	    		if("WL302".equals(com.getItem_num())){//血糖测量值
					com.setExam_result(model.getGlucose().floatValue()+"");
				}else if("WL303".equals(com.getItem_num())){//血糖矫正码
					com.setExam_result(model.getGlucoseCode().intValue()+"");
				}
	    	}else if("spo2".equals(model.getSignType())){//血压=bp,血糖 bg，血氧 spo2 体温 temp
	    		if("WL304".equals(com.getItem_num())){//血氧关注指数
					com.setExam_result(model.getOximetryPI().floatValue()+"");
				}else if("WL305".equals(com.getItem_num())){//血氧测量值
					com.setExam_result(model.getOximetry().floatValue()+"");
				}
	    	}else if("temp".equals(model.getSignType())){//血压=bp,血糖 bg，血氧 spo2 体温 temp
	    		if("WL301".equals(com.getItem_num())){//体温
					com.setExam_result(model.getTemperature().floatValue()+"");
				}
	    	}
			com.setHealth_level("Z");
			if("1".equals(com.getBrief_mark()) && "0".equals(com.getBrief())){
				DepInspectModel dto = new DepInspectModel();
				dto.setId(com.getId());
				dto.setItem_category(com.getItem_category());
				dto.setExam_result(com.getExam_result());
				dto.setSex(examInfo.getSex());
				
				int f = 1;
				if(com.getExam_result() != null){
					f=this.depInspectService.getItemintionReference(dto);
				}
				if(f == 3){
					com.setHealth_level("Y");
				}else if( f == 2){
					com.setHealth_level("W");
				}else{
					com.setHealth_level("Z");
				}
				
			}else if("1".equals(com.getBrief_mark()) && "1".equals(com.getBrief())){
				DepExamResultDTO dto = new DepExamResultDTO();
				dto.setItem_id(com.getId());
				dto.setExam_result(com.getExam_result());
				tempList.add(dto);
			}
		}
		
		List<DepExamResultDTO> listLogic = this.depInspectService.getMateDepLogic(tempList, dep.getId(),examInfo.getSex());
		for(DepInspectExamIntionDTO com : comList){
			if(listLogic != null){
				for(DepExamResultDTO depExam : listLogic){
					if(com.getId() == depExam.getItem_id()){
						com.setConclusion_word(depExam.getExam_result());
						com.setHealth_level("Y");
					}
				}
			}
		}
		
		String dep_result = "";
		String old_conclusion_word = "";
		int count = 1;
		String c_ids = ""; 
		for(DepInspectExamIntionDTO com : comList){
			List<CommonExamDetail> examli = this.depInspectService.findCommonExamDetail(examInfo.getExam_num(),com.getId());
			CommonExamDetail ced = null;
			if(examli.size()!= 0){
				ced = examli.get(0);
				if("".equals(com.getExam_result())){
					this.depInspectService.deleteCommonExamDetail(ced);
				}else{
					ced.setExam_info_id(examInfo.getId());//体检信息
					ced.setExam_item_id(com.getId());//检查项目
					ced.setExam_doctor(user.getName());//检查医生
					ced.setExam_date(DateTimeUtil.parse4(model.getTimestamp()));//检查时间
					ced.setExam_result(com.getExam_result());//检查结果
					ced.setHealth_level(com.getHealth_level());//健康指数
					ced.setCenter_num(user.getCenter_num());//体检中心
					ced.setUpdater(user.getUserid());//修改
					ced.setUpdate_time(DateTimeUtil.parse());//修改时间
					this.depInspectService.updateCommonExamDetail(ced);
					c_ids += com.getC_id()+",";
				}
			}else{
				if(com.getExam_result() != null && !"".equals(com.getExam_result())){
					ced = new CommonExamDetail();
					ced.setExam_info_id(examInfo.getId());//体检信息
					ced.setExam_item_id(com.getId());//检查项目
					ced.setExam_doctor(user.getName());//检查医生
					ced.setExam_date(DateTimeUtil.parse4(model.getTimestamp()));//检查时间
					ced.setExam_result(com.getExam_result());//检查结果
					ced.setHealth_level(com.getHealth_level());//健康指数
					ced.setCenter_num(user.getCenter_num());//体检中心
					ced.setCreater(user.getUserid());//创建人
					ced.setCreate_time(DateTimeUtil.parse());//创建时间
					ced.setUpdater(user.getUserid());//修改
					ced.setUpdate_time(DateTimeUtil.parse());//修改时间
					ced.setExam_num(examInfo.getExam_num());
					this.depInspectService.addCommonExamDetail(ced);
					c_ids += com.getC_id()+",";
				}
			}
			
			if(!com.getHealth_level().equals("Z")){
				if(com.getConclusion_word() != null && !"".equals(com.getConclusion_word())){
					if(!old_conclusion_word.equals(com.getConclusion_word())){
						dep_result +="("+count+")"+com.getConclusion_word()+"\n";
						old_conclusion_word = com.getConclusion_word();
						count ++;
					}
				}else{
					dep_result +="("+count+")"+com.getItem_name()+":"+com.getExam_result()+"\n";
					count ++;
				}
			}
		}
		
		List<ExamdepResult> exm = this.depInspectService.findExamDepResultDTO(examInfo.getExam_num(),Long.valueOf(dep.getId()),"1");
		
		String isDepResultShow = this.customerInfoService.getCenterconfigByKey("IS_DEPRESULT_SHOW", user.getCenter_num()).getConfig_value().trim();
		if(dep_result.equals("")){
			dep_result = isDepResultShow;
		}
		if (exm.size() > 0) {// 修改
			exm.get(0).setExam_info_id(examInfo.getId());// 体检信息Id
			exm.get(0).setExam_doctor(user.getName());// 检查医生
			exm.get(0).setDep_id(dep.getId());// 检查科室
			exm.get(0).setExam_result_summary(dep_result);// 科室检查结果
			exm.get(0).setSuggestion("");// 科室建议
			exm.get(0).setCenter_num(user.getCenter_num());// 体检中心
			exm.get(0).setSpecial_setup("");// 特殊设置
			exm.get(0).setUpdater(user.getUserid());// 修改人
			exm.get(0).setUpdate_time(DateTimeUtil.parse());// 修改时间
			this.depInspectService.updateExamDepResult(exm.get(0));
		} else {
			// 保存结论
			ExamdepResult m = new ExamdepResult();
			m.setExam_info_id(examInfo.getId());// 体检信息Id
			m.setExam_doctor(user.getName());// 检查医生
			m.setDep_id(dep.getId());// 检查科室
			m.setExam_result_summary(dep_result);// 科室检查结果
			m.setSuggestion("");// 科室建议
			m.setCenter_num(user.getCenter_num());// 体检中心
			m.setSpecial_setup("");// 特殊设置
			m.setCreater(user.getUserid());// 创建人
			m.setCreate_time(DateTimeUtil.parse());// 创建时间
			m.setUpdater(user.getUserid());// 修改人
			m.setUpdate_time(DateTimeUtil.parse());// 修改时间
			this.depInspectService.addDepInspect(m);
		}
		//修改检查状态
		this.depInspectService.updateExamInfo(examInfo.getId());
		this.depInspectService.updateExaminfoChargingItem(examInfo.getExam_num(),c_ids.substring(0,c_ids.length()-1),user);
		
		String res="{error:\"结果已成功写入!\",status:\"SUCCESS\",msg:\"\"}";
		return res;
	}
	
	/**
	 * 
	 * @Title: getExamInfoForBarcode   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param sample_barcode
	 * @param: @return
	 * @param: @throws ServiceException      
	 * @return: ExamInfoUserDTO      
	 * @throws
	 */
	public ExamInfoUserDTO getExamInfoForBarcode(String sample_barcode) throws ServiceException{
		String sql = " select ci.user_name,ci.id_num,ci.sex,ci.birthday,ci.phone, "
				+ " ei.id,ei.age,ei.exam_num,ei.status,ei.exam_type,ei.register_date,ei.join_date,ei.exam_times "
				+ " from sample_exam_detail sed,exam_info ei, customer_info ci "
				+ " where sed.sample_barcode='" + sample_barcode+ "' and sed.exam_num=ei.exam_num and ei.customer_id = ci.id";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExamInfoUserDTO.class);
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if((map!=null)&&(map.getList().size()>0)){
			eu= (ExamInfoUserDTO)map.getList().get(0);			
		}
		return eu;
	}
	
	/**
	 * 
	     * @Title: getExamInfoForReqNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param req_nums
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamInfoUserDTO      
	     * @throws
	 */
	public ExamInfoUserDTO getExamInfoForReqNum(String req_nums) throws ServiceException{
		String sql = " select m.id,m.age,m.exam_num,m.status,m.exam_type "
				+ " ,m.register_date,m.join_date,m.exam_times "
				+ " ,n.user_name,n.id_num,n.sex,n.birthday,n.phone "
				+ " from examinfo_charging_item a,exam_info m,customer_info n "
				+ " ,pacs_summary b,pacs_detail c,charging_item d" + " where b.pacs_req_code='" + req_nums
				+ "' and c.summary_id=b.id and c.chargingItem_num=d.item_code "
				+ " and d.id=a.charge_item_id and a.exam_num=m.exam_num "
				+ " and m.exam_num=b.examinfo_num and a.isActive='Y' "
				+ " and m.customer_id = n.id";
		PageSupport map = this.jqm.getList(sql, 1, 10000, ExamInfoUserDTO.class);
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if((map!=null)&&(map.getList().size()>0)){
			eu= (ExamInfoUserDTO)map.getList().get(0);			
		}
		return eu;
	}
	
}
