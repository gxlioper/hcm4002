package com.hjw.wst.service.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.webService.client.FEETermSendMessage;
import com.hjw.webService.client.UNFEESendMessage;
import com.hjw.webService.client.Bean.ReqUnNo;
import com.hjw.webService.client.body.FeeReqBody;
import com.hjw.webService.client.body.FeeResultBody;
import com.hjw.webService.client.body.UnFeeMessage;
import com.hjw.wst.DTO.ChargingInvoiceSingleTTDTO;
import com.hjw.wst.DTO.ChargingSummaryGroupDTO;
import com.hjw.wst.DTO.CompanyAccountDTO;
import com.hjw.wst.DTO.DjtTotalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.GroupSetDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TeamAccountAdditionalDTO;
import com.hjw.wst.DTO.TeamAccountDTO;
import com.hjw.wst.DTO.TeamAccountExamListDTO;
import com.hjw.wst.DTO.TeamAccountItemListDTO;
import com.hjw.wst.DTO.TeamAccountWayDTO;
import com.hjw.wst.DTO.TeamAccountWayFootDTO;
import com.hjw.wst.DTO.TeamExaminfoChargingCEDTO;
import com.hjw.wst.DTO.TeamExaminfoChargingItemDTO;
import com.hjw.wst.DTO.TeamWayDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ChargingSummaryGroup;
import com.hjw.wst.domain.ChargingWayGroup;
import com.hjw.wst.domain.CompanyAccount;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.CompanyAccountDetail;
import com.hjw.wst.domain.TeamAccount;
import com.hjw.wst.domain.TeamAccountAdditional;
import com.hjw.wst.domain.TeamAccountTrjn;
import com.hjw.wst.domain.TeamInvoiceAccount;
import com.hjw.wst.model.StatementsModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.TeamAccountService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class TeamAccountServiceImpl implements TeamAccountService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private BatchService batchService;
	private JdbcPersistenceManager jpm;
	private CompanyService companyService;
	private CustomerInfoService customerInfoService;
	private WebserviceConfigurationService webserviceConfigurationService;
	private ChargingItemService chargingItemService;
	

	public ChargingItemService getChargingItemService() {
		return chargingItemService;
	}

	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
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

	public PersistenceManager getPersistenceManager() {
		return pm;
	}

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	
	public PageReturnDTO getTeamAccountForBatch(long batchid, String center_num, int pageno, int pagesize)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT a.id,a.acc_num,a.batchid,a.acc_date,a.acc_operator,a.prices,a.charges as trueamt,a.dec_charges,a.acc_stauts,"
						+ "a.invoice_no,a.invoice_name,a.linker,a.phone,a.auditor,a.audit_date,a.balance_status,a.balancer,"
						+ "a.balance_date,a.note,a.center_num,b.contract_num,a.acc_name "
						+ ",(select SUM(taa.amount) from team_account_additional taa where taa.acc_num = a.acc_num) as additional "
						+ "FROM team_account a" + " left join contract b on b.batch_id=a.batchid where a.batchid='"
						+ batchid + "' and a.center_num='" + center_num + "' order by a.acc_date desc");

		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, TeamAccountDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
			List<TeamAccountDTO> talist = new ArrayList<TeamAccountDTO>();
			for (Object ob : map.getList()) {
				TeamAccountDTO ta = new TeamAccountDTO();
				ta = (TeamAccountDTO) ob;
				Connection connection = null;
				try {
					connection = this.jqm.getConnection();
					// 总个数
					String sql = "SELECT COUNT(*) as totalexam FROM team_account_exam_list where acc_num='"
							+ ta.getAcc_num() + "' and center_num='" + center_num + "'";
					ResultSet rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						String con = rs.getString("totalexam");
						if ((con != null) && (!"null".equals(con))) {
							ta.setTotalexam(rs.getInt("totalexam"));
						}
					}
					rs.close();

					sql = "select count(*) as totalcust from (select DISTINCT(a.id) from customer_info a,exam_info b,team_account_exam_list c"
							+ " where a.id=b.customer_id and b.exam_num=c.exam_num and c.acc_num='" + ta.getAcc_num()
							+ "' and c.center_num='" + center_num + "' group by a.id ) mm";
					rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						String con = rs.getString("totalcust");
						if ((con != null) && (!"null".equals(con))) {
							ta.setTotalcustume(rs.getInt("totalcust"));
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
				talist.add(ta);
			}
			webrole.setRows(talist);
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: getTeamAccountForFp @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param account_num @param: @param
	 * center_num @param: @param pageno @param: @param
	 * pagesize @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO getTeamAccountForFp(String account_num, String center_num, int pageno, int pagesize)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT a.id,a.acc_num,a.batchid,a.acc_date,a.acc_operator,a.prices,a.charges as trueamt,a.dec_charges,a.acc_stauts,"
						+ "a.invoice_no,a.invoice_name,a.linker,a.phone,a.auditor,a.audit_date,a.balance_status,a.balancer,"
						+ "a.balance_date,a.note,a.center_num,b.contract_num "
						+ " ,(select sum(taa.amount) from team_account_additional taa where taa.acc_num = a.acc_num) as additional "
						+ "FROM  team_invoice_account c,team_account a "
						+ " left join contract b on b.batch_id=a.batchid where c.account_num='" + account_num
						+ "' and c.acc_num=a.acc_num and a.center_num='" + center_num + "' order by a.audit_date desc");
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, TeamAccountDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
			List<TeamAccountDTO> talist = new ArrayList<TeamAccountDTO>();
			for (Object ob : map.getList()) {
				TeamAccountDTO ta = new TeamAccountDTO();
				ta = (TeamAccountDTO) ob;
				Connection connection = null;
				try {
					connection = this.jqm.getConnection();
					// 总个数
					String sql = "SELECT COUNT(*) as totalexam FROM team_account_exam_list where acc_num='"
							+ ta.getAcc_num() + "' and center_num='" + center_num + "'";
					ResultSet rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						String con = rs.getString("totalexam");
						if ((con != null) && (!"null".equals(con))) {
							ta.setTotalexam(rs.getInt("totalexam"));
						}
					}
					rs.close();

					sql = "select count(*) as totalcust from (select DISTINCT(a.id) from customer_info a,exam_info b,team_account_exam_list c"
							+ " where a.id=b.customer_id and b.exam_num=c.exam_num and c.acc_num='" + ta.getAcc_num()
							+ "' and c.center_num='" + center_num + "' group by a.id ) mm";
					rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						String con = rs.getString("totalcust");
						if ((con != null) && (!"null".equals(con))) {
							ta.setTotalcustume(rs.getInt("totalcust"));
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
				talist.add(ta);
			}
			webrole.setRows(talist);
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: getTeamAccountForBatchjs @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param batchid @param: @param
	 * center_num @param: @param pageno @param: @param
	 * pagesize @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO getTeamAccountForBatchjs(long batchid, String center_num, int pageno, int pagesize)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT a.id,a.acc_num,a.batchid,a.acc_date,a.acc_operator,a.prices,a.charges as trueamt,a.dec_charges,a.acc_stauts,"
						+ "a.invoice_no,a.invoice_name,a.linker,a.phone,a.auditor,a.audit_date,a.balance_status,a.balancer,"
						+ "a.balance_date,a.note,a.center_num,b.contract_num "
						+ ",(select sum(taa.amount) from team_account_additional taa where taa.acc_num = a.acc_num) as additional "
						+ "FROM team_account a" + " left join contract b on b.batch_id=a.batchid where a.batchid='"
						+ batchid + "' and a.center_num='" + center_num
						+ "' and a.acc_stauts='Y' and a.balance_status='N' order by a.audit_date desc");

		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, TeamAccountDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
			List<TeamAccountDTO> talist = new ArrayList<TeamAccountDTO>();
			for (Object ob : map.getList()) {
				TeamAccountDTO ta = new TeamAccountDTO();
				ta = (TeamAccountDTO) ob;
				Connection connection = null;
				try {
					connection = this.jqm.getConnection();
					// 总个数
					String sql = "SELECT COUNT(*) as totalexam FROM team_account_exam_list where acc_num='"
							+ ta.getAcc_num() + "' and center_num='" + center_num + "'";
					ResultSet rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						String con = rs.getString("totalexam");
						if ((con != null) && (!"null".equals(con))) {
							ta.setTotalexam(rs.getInt("totalexam"));
						}
					}
					rs.close();

					sql = "select count(*) as totalcust from (select DISTINCT(a.id) from customer_info a,exam_info b,team_account_exam_list c"
							+ " where a.id=b.customer_id and b.exam_num=c.exam_num and c.acc_num='" + ta.getAcc_num()
							+ "' and c.center_num='" + center_num + "' group by a.id ) mm";
					rs = connection.createStatement().executeQuery(sql);
					if (rs.next()) {
						String con = rs.getString("totalcust");
						if ((con != null) && (!"null".equals(con))) {
							ta.setTotalcustume(rs.getInt("totalcust"));
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
				talist.add(ta);
			}
			webrole.setRows(talist);
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: getTeamAccountinvoidForBatch @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param batchid @param: @param
	 * center_num @param: @param pageno @param: @param
	 * pagesize @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO getTeamAccountinvoidForBatch(long batchid, String center_num, int pageno, int pagesize)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT c.id,c.invoice_id,a.title_info,a.invoice_num,c.amount1,c.amount2,c.charging_status,c.is_print_recepit,"
						+ "c.cashier,c.cash_date,c.creater,c.create_time,c.updater,c.update_time,c.account_num,"
						+ "c.batch_id,b.contract_num,c.additional,c.receiv_status,c.receiv_date,c.receiver "
						+ "FROM charging_summary_group c"
						+ " left join charging_invoice_single a on a.id=c.invoice_id  and a.center_num='" + center_num
						+ "' and a.exam_type='T' and a.invoice_status='Y'"
						+ " left join contract b on b.batch_id=c.batch_id where c.batch_id='" + batchid
						+ "' and c.is_active='Y' and c.center_num='"+center_num+"' order by c.update_time desc");
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, ChargingSummaryGroupDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: teamAccountExamListShow @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * center_num @param: @param pageno @param: @param
	 * pagesize @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO teamAccountExamListShow(String acc_num, String center_num, int pageno, int pagesize,
			String sort, String order,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私权限  Y有 N 无
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT c.exam_num,mm.id,d.acc_num,mm.isPrePay,mm.isnotPay,mm.createtime,mm.center_num,mm.totalamt,dbo.fun_CharToStar(c.exam_num,'id_num',b.id_num,'"+isprivateflag+"') as id_num,");
		sb.append("dbo.fun_CharToStar(c.exam_num,'name',b.user_name,'"+isprivateflag+"') as user_name,b.sex,c.age,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,c.join_date,c.status,dept.dep_name,t.data_name as tjlx ");
		sb.append(
				"FROM customer_info b,exam_info c");
		sb.append(" left join company_department dept on dept.id=c._level ");
		sb.append(" left join data_dictionary t on t.id=c.customer_type "); 
		sb.append(" ,team_account_exam_list d ");
		sb.append(
				"left join (select a.id,a.acc_num,a.exam_num,a.isPrePay,a.isnotPay,a.createtime,a.center_num,sum(m.acc_charge ) as totalamt");
		sb.append(" from team_account_exam_list a,team_account_item_list m where a.acc_num='" + acc_num
				+ "' and a.center_num='" + center_num + "'");
		sb.append(" and m.acc_num=a.acc_num and m.exam_num=a.exam_num group by a.id,a.acc_num,a.exam_num,a.isPrePay,");
		sb.append(" a.isnotPay,a.createtime,a.center_num) mm on mm.exam_num=d.exam_num ");
		sb.append(" where c.customer_id=b.id  and c.center_num='" + center_num
				+ "' and c.exam_num=d.exam_num and d.acc_num='" + acc_num + "'  ");
		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
			sb.append(" order by c.exam_num ");
		}
		PageSupport map = this.jqm.getList(sb.toString(), pageno, pagesize, TeamAccountExamListDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: saveTeamAccount @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param ta @param: @return @param: @throws
	 * ServiceException @return: TeamAccount @throws
	 */
	public TeamAccount saveTeamAccount(TeamAccount ta) throws ServiceException {
		this.pm.save(ta);
		return ta;
	}

	/**
	 * 
	 * @Title: saveTeamAccountWay @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param ta @param: @return @param: @throws
	 * ServiceException @return: TeamAccountWay @throws
	 */
	public ChargingWayGroup loadTeamAccountGroup(long id) throws ServiceException {
		return (ChargingWayGroup) qm.load(ChargingWayGroup.class, id);
	}

	/**
	 * 
	 * @Title: saveTeamAccountWay @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param ta @param: @return @param: @throws
	 * ServiceException @return: TeamAccountWay @throws
	 */
	public void delTeamAccountWay(ChargingWayGroup ta) throws ServiceException {
		this.pm.remove(ta);
	}

	/**
	 * 
	 * @Title: saveTeamAccountWay @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param ta @param: @return @param: @throws
	 * ServiceException @return: TeamAccountWay @throws
	 */
	public String saveTeamAccountGroup(long id,String account_num,String ids[],String amts[],long userid) throws ServiceException {
		String mesage = "";
		ChargingSummaryGroup chargingSummaryGroup = new ChargingSummaryGroup();
		chargingSummaryGroup = (ChargingSummaryGroup) this.qm.load(ChargingSummaryGroup.class, id);
		if ((chargingSummaryGroup == null) || (chargingSummaryGroup.getId() <= 0)) {
			mesage = "error-结帐单不存在，操作失败";
		} else if ("1".equals(chargingSummaryGroup.getDaily_status())) {
			mesage = "error-结帐单已经日结，操作失败";
		} else if (chargingSummaryGroup.getReceiv_status() == 1) {
			mesage = "error-结帐单已经确认付费，操作失败";
		} else {
			
			double accountamt = 0;
			accountamt=(chargingSummaryGroup.getAdditional()+chargingSummaryGroup.getAmount2());
			double newamt = 0;
            for(int i=0;i<amts.length;i++){
            	newamt+=Double.valueOf(amts[i]).doubleValue();
            }
            
            BigDecimal bgold = new BigDecimal(accountamt);
            double f1 = bgold.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println(f1);
            
            BigDecimal bgnew = new BigDecimal(newamt);
            double f2 = bgnew.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            System.out.println(f2);
            
			if (f1 != f2) {
				mesage = "error-支付方式金额不等于[" + accountamt + "元]，操作失败";
			} else {
				String sqldel = "from ChargingWayGroup c where c.summary_id = '" + id + "'";
				List<ChargingWayGroup> listdel = this.qm.find(sqldel);
				for (ChargingWayGroup cwg : listdel) {
					this.pm.remove(cwg);
				}
				for(int i=0;i<ids.length;i++){
					ChargingWayGroup ta = new ChargingWayGroup();
					ta.setAccount_num(account_num);
					ta.setSummary_id(id);
					BigDecimal bgnewamount = new BigDecimal(Double.valueOf(amts[i]).doubleValue());
			        double famount = bgnewamount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					ta.setAmount(famount);
					ta.setCharging_way(ids[i]);
					ta.setCreater(userid);
					ta.setCreate_time(DateTimeUtil.parse());
					ta.setUpdate_time(DateTimeUtil.parse());
					ta.setUpdater(userid);
					this.pm.save(ta);
				}
				mesage = "ok-操作成功";
			}		
		}
		return mesage;
	}

	/**
	 * 
	 * @Title: loadTeamAccount @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param id @param: @return @param: @throws
	 * ServiceException @return: TeamAccount @throws
	 */
	public TeamAccount loadTeamAccount(long id) throws ServiceException {
		TeamAccount ta = new TeamAccount();
		ta = (TeamAccount) this.qm.load(TeamAccount.class, id);
		return ta;
	}

	/**
	 * 
	 * @Title: findTeamAccountByAccNum @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param
	 * accnum @param: @return @param: @throws ServiceException @return:
	 * TeamAccount @throws
	 */
	public TeamAccount findTeamAccountByAccNum(String accnum, String centernum) throws ServiceException {
		TeamAccount ta = new TeamAccount();
		String hql = " from TeamAccount g where g.acc_num='" + accnum + "' and g.center_num='" + centernum + "'";
		List<TeamAccount> list = qm.find(hql);
		if (list != null && list.size() > 0) {
			ta = list.get(0);
		}
		return ta;
	}

	/**
	 * 
	 * @Title: findTeamAccountByAccNumAll @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param accnum @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * TeamAccountDTO @throws
	 */
	public ChargingInvoiceSingleTTDTO findTeamAccountByAccNumAll(long batchid, String accnum, String centernum)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT a.id,a.title_info,a.invoice_type,a.invoice_num,a.invoice_amount,a.invoice_status,a.invoice_maker,"
						+ "a.invoice_time,a.canceller,a.cancel_time,a.creater,a.create_time,a.updater,a.update_time,a.account_num,"
						+ "a.exam_type,a.barchid as barch_id,a.center_num "
						+ "FROM charging_invoice_single a" + " where a.barchid='" + batchid + "' and a.center_num='"
						+ centernum + "' and a.account_num='" + accnum + "'  order by a.update_time desc");
		PageSupport map = this.jqm.getList(sb.toString(), 1, 10000, ChargingInvoiceSingleTTDTO.class);
		ChargingInvoiceSingleTTDTO cis = new ChargingInvoiceSingleTTDTO();
		if ((map != null) && (map.getList().size() > 0)) {
			cis = (ChargingInvoiceSingleTTDTO) map.getList().get(0);
		}
		return cis;
	}

	/**
	 * 
	 * @Title: updateTeamAccount @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param ta @param: @return @param: @throws
	 * ServiceException @return: TeamAccount @throws
	 */
	public TeamAccount updateTeamAccount(TeamAccount ta) throws ServiceException {
		this.pm.update(ta);
		return ta;
	}

	/**
	 * 
	 * @Title: setTTermExamInfoUserListAll @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * com_id @param: @param batchid @param: @param exam_num @param: @param
	 * groupid @param: @param status @param: @param sex @param: @param
	 * username @param: @param set_id @param: @param rylb @param: @param
	 * isnotpay @param: @param levels @param: @param userid @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * int @throws
	 */
	public int setTTermExamInfoUserListAll(String acc_num, long com_id, long batchid, String exam_num, long groupid,String time1,String time2,
			String status, String sex, String username, long set_id, String rylb, int isnotpay, String levels,String tjlx,
			long userid, String centernum) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id  as exam_id,c.exam_num,c.status as examstatus ");
		sb.append(" from customer_info a ,exam_info c ");
//		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num and m.isActive='Y'");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		sb.append(" and c.company_id=" + com_id + " ");
		sb.append(" and c.batch_id = '" + batchid + "' ");
		sb.append(" and dbo.fun_GetExaminfoChargingItemCount(c.id,c.exam_num,"+isnotpay+")>0 ");
		if (!StringUtil.isEmpty(exam_num)) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}		
		
		if ((!StringUtil.isEmpty(time1)) && (!StringUtil.isEmpty(time2))) {
			sb.append(" and c.join_date >= '" + time1 + " 00:00:00' ");
			sb.append(" and c.join_date <= '" + time2 + " 23:59:59' ");
		}
		
		if (!StringUtil.isEmpty(tjlx)) {
			sb.append(" and c.customer_type in (" + tjlx + ") ");
		}

		if (!StringUtil.isEmpty(rylb)) {
			sb.append(" and c.customer_type_id in (" + rylb + ") ");
		}

		if (groupid > 0) {
			sb.append(" and c.group_id = '" + groupid + "' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level in(" + levels + ") ");
		}

		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status in (" + status + ") ");
		}

		if (set_id > 0) {
//			sb.append(" and m.exam_set_id= '" + set_id + "' ");
			sb.append("and c.id in (select es.examinfo_id from examinfo_set es where es.exam_set_id = '"+set_id+"' and es.examinfo_id = c.id and es.isActive='Y')");
		}
		
		int typeid = 0;
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			System.out.println("调用存储过程：pro_termexamaccount(" + isnotpay + "," + sb.toString() + "," + acc_num + ","
					+ userid + "," + centernum + ")");
			// 获取档案号
			CallableStatement c = connection.prepareCall("{call pro_termexamaccount(?,?,?,?,?,?)}");
			c.setInt(1, isnotpay);
			c.setString(2, sb.toString());
			c.setString(3, acc_num);
			c.setLong(4, userid);
			c.setString(5, centernum);
			// 注册存储过程的第二个参数
			c.registerOutParameter(6, java.sql.Types.INTEGER);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			typeid = c.getInt(6);
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
		return typeid;

	}

	/**
	 * 
	 * @Title: getTermExamInfoUserList @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param com_id @param: @param
	 * batchid @param: @param exam_num @param: @param groupid @param: @param
	 * status @param: @param sex @param: @param username @param: @param
	 * set_num @param: @param userid @param: @param centernum @param: @param
	 * pagesize @param: @param pageno @param: @return @param: @throws
	 * ServiceException @return: PageReturnDTO @throws
	 */
	public PageReturnDTO getTermExamInfoUserList(long com_id, long batchid, String exam_num, long groupid,String time1,String time2,
			String status, String sex, String username, long set_id, String rylb, int isnotpay, String levels,String tjlx,
			long userid, String centernum, int pagesize, int pageno, String sort, String order,UserDTO user,String center_num)
			throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);//判断用户是否有隐私资源 Y有 N无
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,dbo.fun_CharToStar(c.exam_num,'name',a.user_name,'"+isprivateflag+"') as user_name,a.arch_num,dbo.fun_CharToStar(c.exam_num,'id_num',a.id_num,'"+isprivateflag+"') as id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,dbo.fun_CharToStar(c.exam_num,'phone',c.phone,'"+isprivateflag+"') as phone,n.group_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,t.data_name as tjlx,u.dep_name as deptname ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join company_department u on u.id=c._level" );
		sb.append(" left join data_dictionary t on t.id=c.customer_type ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
//		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num and m.isActive='Y' ");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		sb.append(" and c.company_id=" + com_id + " ");
		sb.append(" and c.batch_id = '" + batchid + "' ");
		sb.append(" and dbo.fun_GetExaminfoChargingItemCount(c.id,c.exam_num,"+isnotpay+")>0 ");
		if (!StringUtil.isEmpty(exam_num)) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if ((!StringUtil.isEmpty(time1)) && (!StringUtil.isEmpty(time2))) {
			sb.append(" and c.join_date >= '" + time1 + " 00:00:00' ");
			sb.append(" and c.join_date <= '" + time2 + " 23:59:59' ");
		}
		
		if (!StringUtil.isEmpty(tjlx)) {
			sb.append(" and c.customer_type in (" + tjlx + ") ");
		}

		if (!StringUtil.isEmpty(rylb)) {
			sb.append(" and c.customer_type_id in (" + rylb + ") ");
		}

		if (groupid > 0) {
			sb.append(" and c.group_id = '" + groupid + "' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level in(" + levels + ") ");
		}

		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status in (" + status + ") ");
		}

		if (set_id > 0) {
//			sb.append(" and m.exam_set_id= '" + set_id + "' ");
			sb.append("and c.id in (select es.examinfo_id from examinfo_set es where es.exam_set_id = '"+set_id+"' and es.examinfo_id = c.id and es.isActive='Y')");
		}
		
		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
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
				eu.setIsnotpay(isnotpay);////包含弃检所有   0不包含弃检项目所有项目，1表示包含弃检，含已检查项目  2表示只包含已检项目 3 包含弃检和已检的项目
				if ("Z".equals(eu.getStatus())) {
					eu.setIsprepay(0);
				} else {
					eu.setIsprepay(1);
				}
				String setnums = "";
				PageReturnDTO batchlist = this.batchService.getGroupSetList(eu.getGroup_id(), 1000, 1,center_num);
				if ((batchlist != null) && (batchlist.getRows() != null) && (batchlist.getRows().size() > 0)) {
					for (int j = 0; j < batchlist.getRows().size(); j++) {
						GroupSetDTO gs = new GroupSetDTO();
						gs = (GroupSetDTO) batchlist.getRows().get(j);
						setnums = setnums + "," + gs.getSet_name();
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

	/**
	 * 
	 * @Title: getTermExamInfoUserListts @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param com_id @param: @param
	 * batchid @param: @param exam_num @param: @param groupid @param: @param
	 * status @param: @param sex @param: @param username @param: @param
	 * set_id @param: @param rylb @param: @param isnotpay @param: @param
	 * userid @param: @param centernum @param: @param pagesize @param: @param
	 * pageno @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public PageReturnDTO getTermExamInfoUserListts(long com_id, long batchid, String exam_num, long groupid,
			String status, String sex, String username, long set_id, String rylb, int isnotpay, String levels,String tjlx,
			long userid, String centernum, int pagesize, int pageno,String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,t.data_name as tjlx,u.dep_name as deptname ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join company_department u on u.id=c._level" );
		sb.append(" left join data_dictionary t on t.id=c.customer_type ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
//		sb.append(" left join examinfo_set m on m.examinfo_id=c.id");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		sb.append(" and c.company_id=" + com_id + " ");
		sb.append(" and c.batch_id = '" + batchid + "' ");

		if (!StringUtil.isEmpty(exam_num)) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}
		
		if (!StringUtil.isEmpty(tjlx)) {
			sb.append(" and c.customer_type in (" + tjlx + ") ");
		}

		if (!StringUtil.isEmpty(rylb)) {
			String srylb = "";
			for (int i = 0; i < rylb.split(",").length; i++) {
				srylb = srylb + ",'" + rylb.split(",")[i] + "'";
			}
			if (srylb.length() > 1) {
				srylb = srylb.substring(1, srylb.length());
			}
			sb.append(" and c.customer_type_id in (" + srylb + ") ");
		}

		if (groupid > 0) {
			sb.append(" and c.group_id = '" + groupid + "' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level in(" + levels + ") ");
		}

		if (!StringUtil.isEmpty(status)) {
			String sstatus = "";
			for (int i = 0; i < status.split(",").length; i++) {
				sstatus = sstatus + ",'" + status.split(",")[i] + "'";
			}
			if (sstatus.length() > 1) {
				sstatus = sstatus.substring(1, sstatus.length());
			}
			sb.append(" and c.status in (" + sstatus + ") ");
		}

		if (set_id > 0) {
//			sb.append(" and m.exam_set_id= '" + set_id + "' ");
			sb.append("and c.id in (select es.examinfo_id from examinfo_set es where es.exam_set_id = '"+set_id+"' and es.examinfo_id = c.id and es.isActive='Y')");
		}
		// sb.append(" and not exists (select * from team_account_exam_list l
		// where l.exam_num=c.exam_num) ");
		sb.append(" order by c.update_time desc ");
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
				// eu.setIsnotpay(isnotpay);
				if (isnotpay == 1) {
					eu.setIsnotpay(1);
				} else {
					eu.setIsnotpay(0);
				}
				if ("Z".equals(eu.getStatus())) {
					eu.setIsprepay(0);
				} else {
					eu.setIsprepay(1);
				}
				String setnums = "";
				PageReturnDTO batchlist = this.batchService.getGroupSetList(eu.getGroup_id(), 1000, 1,center_num);
				if ((batchlist != null) && (batchlist.getRows() != null) && (batchlist.getRows().size() > 0)) {
					for (int j = 0; j < batchlist.getRows().size(); j++) {
						GroupSetDTO gs = new GroupSetDTO();
						gs = (GroupSetDTO) batchlist.getRows().get(j);
						setnums = setnums + "," + gs.getSet_name();
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

	/**
	 * 
	 * @Title: delTeamExamForAccNum @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * ids @param: @param user @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String delTeamExamForAccNum(String acc_num, String ids, String centernum) throws ServiceException {

		TeamAccountDTO ta = new TeamAccountDTO();
		ta = getTeamAccountForAccNum(acc_num, centernum);
		String message = "ok-";
		if ((ta != null) && (ta.getAcc_num().length() > 0)) {
			if ("Y".equals(ta.getAcc_stauts())) {
				message = "error-结算单号已经审核，操作失败";
			} else if ("Y".equals(ta.getBalance_status())) {
				message = "error-结算单号已经结帐，操作失败";
			} else {
				String[] idss = ids.split(",");
				for (int i = 0; i < idss.length; i++) {
					try {
						String examnum = idss[i];
						StringBuffer sb = new StringBuffer();
						sb.append("delete from team_account_item_list where acc_num='" + acc_num + "' and exam_num='"
								+ examnum + "'");
						this.jpm.executeSql(sb.toString());
						sb = new StringBuffer();
						sb.append("delete from team_account_exam_list where acc_num='" + acc_num + "' and exam_num='"
								+ examnum + "'");
						this.jpm.executeSql(sb.toString());

					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				message = "ok-操作完成";
			}
		} else {
			message = "error-无效结算编号，操作失败";
		}
		return message;
	}

	/**
	 * 
	 * @Title: setTeamAccountList @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * eulist @param: @param user @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String setTeamAccountList(String acc_num, List<ExamInfoUserDTO> eulist, long user, String centernum)
			throws ServiceException {
		for (ExamInfoUserDTO eu : eulist) {
			try {
				String examnum = eu.getExam_num();
				//int isprepay = eu.getIsprepay();
				//int isnotpay = eu.getIsnotpay();

				StringBuffer sb = new StringBuffer();
				sb.append("select id,group_id,customer_id,exam_num,status,register_date,join_date,"
						+ "final_date,final_doctor,exam_type,customer_type,is_sampled_directly,is_adjusted,center_num,getReportWay,"
						+ "reportAddress,chargingType,customerType,group_index,is_Active,creater,create_time,updater,update_time,"
						+ "is_guide_back,company_check_status,customer_type_id,is_marriage,age,address,email,phone,company,position,_level,"
						+ "picture_path,is_after_pay,past_medical_history,remarke,introducer,counter_check,guide_nurse,appointment,data_source,"
						+ "others,order_id,exam_times,company_id,batch_id,is_report_print,patient_id,exam_indicator,clinic_no,employeeID,mc_no,"
						+ "visit_date,visit_no from exam_info where exam_num='" + examnum + "'");
				List<ExamInfoDTO> eiList = this.jqm.getList(sb.toString(), ExamInfoDTO.class);
				if ((eiList != null) && (eiList.size() > 0)) {
					ExamInfoDTO ei = new ExamInfoDTO();
					ei = eiList.get(0);

					if ((!"Z".equals(ei.getStatus())) && (eu.getIsprepay() == 0)) {

					} else {
						// PrePay N:非预结； Y:预结
						String PrePay = "N";
						if ("Z".equals(ei.getStatus())) {
							PrePay = "N";
						} else {
							PrePay = "Y";
						}

						sb = new StringBuffer();
						sb.append("delete from team_account_item_list where acc_num='" + acc_num + "' and exam_num='"
								+ examnum + "'");
						this.jpm.executeSql(sb.toString());
						sb = new StringBuffer();
						sb.append("delete from team_account_exam_list where acc_num='" + acc_num + "' and exam_num='"
								+ examnum + "'");
						this.jpm.executeSql(sb.toString());

						sb = new StringBuffer();
						
						sb.append(
								"select a.id,a.examinfo_id,a.charge_item_id,a.item_amount,a.team_pay,a.personal_pay,a.exam_status,a.discount , a.charging_item_code "
								+ "from examinfo_charging_item a "
										+ " where a.isActive='Y' " + "and a.exam_indicator in ('T','GT') "
										+ "and a.pay_status<>'M' and a.center_num = '"+centernum+"' and a.exam_num='" + ei.getExam_num() + "' "
										+ " and not exists (select * from team_account_item_list l where l.exam_num='"+ei.getExam_num()+"' "
										+ "and a.charging_item_code=l.charging_item_code) ");
						if (eu.getIsnotpay() == 0) {//包含弃检所有   0不包含弃检项目所有项目，
							                         //           1表示包含弃检，含已检查项目  
							                         //           2表示只包含已检项目 
							                         //           3 包含弃检和已检的项目
							sb.append("and a.exam_status<>'G'");
						}else if (eu.getIsnotpay() == 1) {
							
						}else if (eu.getIsnotpay() == 2) {
							sb.append("and a.exam_status='Y'");
						}else if (eu.getIsnotpay() == 3) {
							sb.append("and a.exam_status in ('Y','G')");
						}
						/*
						 * sb.append(
						 * "select acc_num,exam_num from team_account_item_list where acc_num='"
						 * + acc_num + "' and exam_num='" + examnum + "' ");
						 */
						System.out.println(sb.toString());
						List<ExaminfoChargingItemDTO> ecilist = this.jqm.getList(sb.toString(),
								ExaminfoChargingItemDTO.class);
						if ((ecilist != null) && (ecilist.size() > 0)) {
							boolean isnotpayflag = false;
							for (ExaminfoChargingItemDTO eci : ecilist) {//
								// discard N:非弃检项目 Y：弃检项目
								String discard = "N";
								if ("G".equals(eci.getExam_status())) {
									discard = "Y";
									isnotpayflag = true;
								} else {
									discard = "N";
								}
								String sql = "insert into team_account_item_list(acc_num,exam_num,charging_item_id,price,"
										+ "acc_charge,personal_pay,PrePay,discard,in_date,creater,rate,center_num,charging_item_code) values('"
										+ acc_num + "','" + examnum + "'" + "," + eci.getCharge_item_id() + ","
										+ eci.getItem_amount() + "," + eci.getTeam_pay() + "," + eci.getPersonal_pay()
										+ ",'" + PrePay + "','" + discard + "','" + DateTimeUtil.getDateTime() + "',"
										+ user + "" + "," + eci.getDiscount() + ",'" + centernum + "','"+eci.getCharging_item_code()+"')";
								System.out.println(sql);
								this.jpm.executeSql(sql);
							}
							String isnotpays = "N";
							if (isnotpayflag) {
								isnotpays = "Y";
							} 
							String sql = "insert into team_account_exam_list(acc_num,exam_num,isPrePay,isnotPay,createtime,center_num) values('"
									+ acc_num + "','" + examnum + "'" + ",'" + PrePay + "','" + isnotpays + "','"
									+ DateTimeUtil.getDateTime() + "','" + centernum + "')";
							System.out.println(sql);
							this.jpm.executeSql(sql);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * 
	 * @Title: setTeamAccountList @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * eulist @param: @param user @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String setTeamAccounttsList(String acc_num, List<ExamInfoUserDTO> eulist,
			List<ExaminfoChargingItemDTO> listitem, long user, String centernum) throws ServiceException {
		for (ExamInfoUserDTO eu : eulist) {
			try {
				String examnum = eu.getExam_num();
				int isprepay = eu.getIsprepay();
				int isnotpay = eu.getIsnotpay();

				StringBuffer sb = new StringBuffer();
				sb.append("select id,group_id,customer_id,exam_num,status,register_date,join_date,"
						+ "final_date,final_doctor,exam_type,customer_type,is_sampled_directly,is_adjusted,center_num,getReportWay,"
						+ "reportAddress,chargingType,customerType,group_index,is_Active,creater,create_time,updater,update_time,"
						+ "is_guide_back,company_check_status,customer_type_id,is_marriage,age,address,email,phone,company,position,_level,"
						+ "picture_path,is_after_pay,past_medical_history,remarke,introducer,counter_check,guide_nurse,appointment,data_source,"
						+ "others,order_id,exam_times,company_id,batch_id,is_report_print,patient_id,exam_indicator,clinic_no,employeeID,mc_no,"
						+ "visit_date,visit_no from exam_info where exam_num='" + examnum + "'");
				List<ExamInfoDTO> eiList = this.jqm.getList(sb.toString(), ExamInfoDTO.class);
				if ((eiList != null) && (eiList.size() > 0)) {
					ExamInfoDTO ei = new ExamInfoDTO();
					ei = eiList.get(0);

					if ((!"Z".equals(ei.getStatus())) && (eu.getIsprepay() == 0)) {

					} else {
						// PrePay N:非预结； Y:预结
						String PrePay = "N";
						if ("Z".equals(ei.getStatus())) {
							PrePay = "N";
						} else {
							PrePay = "Y";
						}

						// 获取能结算的项目
						sb = new StringBuffer();
						sb.append(
								"select a.id,a.examinfo_id,a.charge_item_id,a.item_amount,a.team_pay,a.personal_pay,a.exam_status,a.discount "
								+ "from examinfo_charging_item a "
										+ " where a.isActive='Y' " + "and a.exam_indicator in ('T','GT') "
										+ "and a.pay_status<>'M' and a.center_num = '"+centernum+"' and a.exam_num='" + ei.getExam_num() + "' ");
						if (eu.getIsnotpay() == 0) {
							sb.append("and a.exam_status<>'G' ");
						}
						/*
						 * sb.append(
						 * "select acc_num,exam_num from team_account_item_list where acc_num='"
						 * + acc_num + "' and exam_num='" + examnum + "' ");
						 */
						System.out.println(sb.toString());
						List<ExaminfoChargingItemDTO> ecilist = this.jqm.getList(sb.toString(),
								ExaminfoChargingItemDTO.class);
						if ((ecilist != null) && (ecilist.size() > 0)) {
							boolean isnotpayflag = false;
							for (ExaminfoChargingItemDTO eci : ecilist) {//
								// discard N:非弃检项目 Y：弃检项目
								String discard = "N";
								if ("G".equals(eci.getExam_status())) {
									discard = "Y";
									isnotpayflag = true;
								} else {
									discard = "N";
								}

								boolean insflag = false;
								// 判断是否在选择的要结算的项目之内
								for (ExaminfoChargingItemDTO eciss : listitem) {
									if (eciss.getId() == eci.getCharge_item_id()) {
										insflag = true;
										// 判断项目是否已经结算完成
										StringBuffer sb11 = new StringBuffer();
										sb11.append(
												"select acc_num,exam_num from team_account_item_list where charging_item_id='"
														+ eci.getCharge_item_id() + "' and exam_num='" + examnum
														+ "' ");
										System.out.println(sb11.toString());
										List<TeamAccountItemListDTO> hisPriceList = this.jqm.getList(sb11.toString(),
												TeamAccountItemListDTO.class);
										if ((hisPriceList != null) && (hisPriceList.size() > 0)) {
											insflag = false;
										} else {
											insflag = true;
										}
									}
								}
								
								if (insflag) {
									ChargingItem chargingItem = chargingItemService.findChargingItem(eci.getCharge_item_id());
									
									
									String sql = "insert into team_account_item_list(acc_num,exam_num,charging_item_id,price,"
											+ "acc_charge,personal_pay,PrePay,discard,in_date,creater,rate,center_num,charging_item_code) values('"
											+ acc_num + "','" + examnum + "'" + "," + eci.getCharge_item_id() + ","
											+ eci.getItem_amount() + "," + eci.getTeam_pay() + ","
											+ eci.getPersonal_pay() + ",'" + PrePay + "','" + discard + "','"
											+ DateTimeUtil.getDateTime() + "'," + user + "" + ",'" + chargingItem.getItem_code() + "'," + eci.getDiscount()
											+ ",'" + centernum + "','" + chargingItem.getItem_code() + "')";
									System.out.println(sql);
									this.jpm.executeSql(sql);
								}
							}
							String isnotpays = "N";
							if (isnotpayflag) {
								isnotpays = "Y";
							} else {
								isnotpays = "N";
							}

						}
					}
				}

				boolean insflag = false;
				StringBuffer sb11 = new StringBuffer();
				sb11.append("select acc_num,exam_num from team_account_item_list where acc_num='" + acc_num
						+ "' and exam_num='" + examnum + "' ");
				System.out.println(sb11.toString());
				List<TeamAccountItemListDTO> hisPriceList = this.jqm.getList(sb11.toString(),
						TeamAccountItemListDTO.class);
				if ((hisPriceList != null) && (hisPriceList.size() > 0)) {
					insflag = true;
				}

				if (insflag) {
					StringBuffer sb22 = new StringBuffer();
					sb22.append("select acc_num,exam_num from team_account_exam_list where acc_num='" + acc_num
							+ "' and exam_num='" + examnum + "' ");
					System.out.println(sb22.toString());
					List<TeamAccountItemListDTO> hisPriceList22 = this.jqm.getList(sb22.toString(),
							TeamAccountItemListDTO.class);
					if ((hisPriceList22 != null) && (hisPriceList22.size() > 0)) {
						;
					} else {
						// 完成结算，然后判断是否真的在此结算单进行了结算，如果真的结算，需要写 团体结算体检人员明细表
						String sql = "insert into team_account_exam_list(acc_num,exam_num,isPrePay,isnotPay,createtime,center_num) values('"
								+ acc_num + "','" + examnum + "'" + ",'N','N','" + DateTimeUtil.getDateTime() + "','"
								+ centernum + "')";
						System.out.println(sql);
						this.jpm.executeSql(sql);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

		return null;
	}

	/**
	 * 
	 * @Title: TermItemCount @Description: TODO(这里用一句话描述这个方法的作用) @param: @param
	 * acc_num @param: @param center_num @param: @return @param: @throws
	 * ServiceException @return: DjtTotalDTO @throws
	 */
	public DjtTotalDTO termItemCount(String acc_num, String center_num) throws ServiceException {
		DjtTotalDTO intArray = new DjtTotalDTO();
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			// 总个数
			String sql = "select sum(a.price-a.personal_pay) as price," + "sum(a.acc_charge) as acc_charge,"
					+ "sum(dec_charges) as jmje " + "from team_account_item_list a  where a.acc_num='" + acc_num
					+ "' and a.center_num='" + center_num + "'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("price");
				if ((con == null) || ("null".equals(con))) {
					intArray.setTotalAmt(0);
				} else {
					intArray.setTotalAmt(rs.getDouble("price"));
				}
				con = rs.getString("acc_charge");
				if ((con == null) || ("null".equals(con))) {
					intArray.setTermAmt(0);
				} else {
					intArray.setTermAmt(rs.getDouble("acc_charge"));
				}

				con = rs.getString("jmje");
				if ((con == null) || ("null".equals(con))) {
					intArray.setPersonAmt(0);
				} else {
					intArray.setPersonAmt(rs.getDouble("jmje"));
				}
			}
			rs.close();

			sql = "SELECT COUNT(*) as totalexam FROM team_account_exam_list where acc_num='" + acc_num
					+ "' and center_num='" + center_num + "'";
			rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("totalexam");
				if ((con != null) && (!"null".equals(con))) {
					intArray.setTotalexam(rs.getInt("totalexam"));
				}
			}
			rs.close();

			sql = "select count(*) as totalcust from (select DISTINCT(a.id) from customer_info a,exam_info b,team_account_exam_list c"
					+ " where a.id=b.customer_id and b.exam_num=c.exam_num and c.acc_num='" + acc_num
					+ "' and c.center_num='" + center_num + "' group by a.id ) mm";
			rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("totalcust");
				if ((con != null) && (!"null".equals(con))) {
					intArray.setTotalcustume(rs.getInt("totalcust"));
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
		return intArray;
	}

	/**
	 * 
	 * @Title: setTeamAccountcountdo @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * yjje @param: @param sjje @param: @param jmje @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String setTeamAccountcountdo(String acc_num, String center_num) throws ServiceException {
		DjtTotalDTO intArray = new DjtTotalDTO();
		String message = "";
		Connection connection = null;
		try {
			StringBuffer sb = new StringBuffer();
			TeamAccountDTO ta = new TeamAccountDTO();
			ta = getTeamAccountForAccNum(acc_num, center_num);
			if ((ta != null) && (ta.getAcc_num().length() > 0)) {
				if ("Y".equals(ta.getAcc_stauts())) {
					message = "error-结算单号已经审核，操作失败";
				} else if ("Y".equals(ta.getBalance_status())) {
					message = "error-结算单号已经结帐，操作失败";
				} else {
					intArray = termItemCount(acc_num, center_num);

					String sql = "update team_account set prices = " + intArray.getTotalAmt() + ",charges = "
							+ intArray.getTermAmt() + "," + "dec_charges = " + intArray.getPersonAmt()
							+ " where acc_num='" + acc_num + "' and center_num='" + center_num + "' ";
					System.out.println(sql);
					this.jpm.executeSql(sql);
					message = "ok-操作成功";
				}
			} else {
				message = "error-无效结算单号";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			message = "error-操作失败";
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
	 * @Title: getTeamAccountForAccNum @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * TeamAccountDTO @throws
	 */
	public TeamAccountDTO getTeamAccountForAccNum(String acc_num, String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		TeamAccountDTO ta = new TeamAccountDTO();
		sb.append("SELECT ID,acc_num,batchid,acc_date,acc_operator,prices,charges,dec_charges,"
				+ "acc_stauts,invoice_no,invoice_name,linker,phone,auditor,audit_date,"
				+ "balance_status,balancer,balance_date,note,center_num" + "  FROM team_account where acc_num='"
				+ acc_num + "' and center_num='" + center_num + "'");
		List<TeamAccountDTO> eiList = this.jqm.getList(sb.toString(), TeamAccountDTO.class);
		if ((eiList != null) && (eiList.size() > 0)) {
			ta = eiList.get(0);
		}
		return ta;
	}

	/**
	 * 
	 * @Title: delTeamAccountForAccNum @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String delTeamAccountForAccNum(String acc_num, String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		TeamAccountDTO ta = new TeamAccountDTO();
		ta = getTeamAccountForAccNum(acc_num, center_num);
		String message = "ok-";
		if ((ta != null) && (ta.getAcc_num().length() > 0)) {
			if ("Y".equals(ta.getAcc_stauts())) {
				message = "error-结算单号已经审核，操作失败";
			} else if ("Y".equals(ta.getBalance_status())) {
				message = "error-结算单号已经结帐，操作失败";
			} else {
				try {
					sb.append("delete team_account_item_list where  acc_num='" + acc_num + "'");
					System.out.println(sb.toString());
					this.jpm.executeSql(sb.toString());
					sb.append("delete team_account_exam_list where  acc_num='" + acc_num + "'");
					System.out.println(sb.toString());
					this.jpm.executeSql(sb.toString());
					sb.append(
							"delete team_account where acc_num='" + acc_num + "' and center_num='" + center_num + "'");
					System.out.println(sb.toString());
					this.jpm.executeSql(sb.toString());
					message = "ok-删除成功";
				} catch (Exception ex) {
					message = "error-操作失败";
					ex.printStackTrace();
				}
			}
		} else {
			message = "error-结算单号无效，操作失败";
		}
		return message;
	}

	/**
	 * 
	 * @Title: getExaminfoChargingItemforExamId @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param
	 * examnum @param: @return @param: @throws ServiceException @return:
	 * List<TeamExaminfoChargingItemDTO> @throws
	 */
	public List<TeamExaminfoChargingItemDTO> getExaminfoChargingItemforExamNum(String acc_num, String examnum,
			String centernum) throws ServiceException {
		String sql = "select a.id,a.charging_item_id,a.prePay,a.acc_charge,"
				+ "a.acc_num,a.center_num,a.creater,a.discard,a.exam_num," + "a.in_date,a.personal_pay,a.price,a.rate,"
				+ "b.dep_id,c.dep_name,b.item_code,b.item_name "
				+ "from team_account_item_list a,charging_item b,department_dep c  "
				+ "where a.charging_item_code=b.item_code and b.dep_id=c.id " + " and a.acc_num='" + acc_num
				+ "' and a.exam_num ='" + examnum + "' and a.center_num='" + centernum
				+ "' order by b.item_seq,b.item_name desc";
		PageSupport map = this.jqm.getList(sql, 1, 10000, TeamExaminfoChargingItemDTO.class);
		List<TeamExaminfoChargingItemDTO> es = new ArrayList<TeamExaminfoChargingItemDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			es = map.getList();
		}
		return es;
	}

	/**
	 * 
	 * @Title: getTeamAccountExamListforExamNum @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param
	 * examnum @param: @return @param: @throws ServiceException @return:
	 * TeamAccountExamListDTO @throws
	 */
	public TeamAccountExamListDTO getTeamAccountExamListforExamNum(String examnum) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.id,a.acc_num,a.exam_num,a.isPrePay,a.isnotPay,a.createtime,a.center_num "
				+ " FROM team_account_exam_list a" + " where a.exam_num='" + examnum + "' ");
		TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
		PageSupport map = this.jqm.getList(sb.toString(), 1, 100000, TeamAccountExamListDTO.class);
		if ((map != null) && (map.getList().size() > 0)) {
			ta = (TeamAccountExamListDTO) map.getList().get(0);
		}
		return ta;
	}

	/**
	 * 
	 * @Title: getTeamItemCount @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param
	 * exam_num @param: @return @param: @throws ServiceException @return:
	 * DjtTotalDTO @throws
	 */
	public DjtTotalDTO getTeamItemCount(String acc_num, String exam_num, String centernum) throws ServiceException {
		DjtTotalDTO intArray = new DjtTotalDTO();
		Connection connection = null;
		try {
			connection = this.jqm.getConnection();
			// 总个数
			String sql = "select count(*) as counts from team_account_item_list b where b.exam_num='" + exam_num
					+ "' and b.acc_num='"+acc_num+"' and b.center_num='" + centernum + "'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("counts");
				if ((con == null) || ("null".equals(con))) {
					intArray.setCounts(0);
				} else {
					intArray.setCounts(rs.getInt("counts"));
				}
			}
			rs.close();

			// 应付费
			sql = "select sum(price) as amounts,count(*) from team_account_item_list b " + " where b.exam_num='"
					+ exam_num + "' and b.acc_num='"+acc_num+"' and b.center_num='" + centernum + "'";
			rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("amounts");
				if ((con == null) || ("null".equals(con))) {
					intArray.setTotalAmt(0);
				} else {
					intArray.setTotalAmt(rs.getDouble("amounts"));
				}
			}
			rs.close();

			// 结算金额
			sql = "select sum(b.acc_charge) as amount,count(*) from team_account_item_list b " + "where b.exam_num='"
					+ exam_num + "' and b.acc_num='"+acc_num+"'  and b.center_num='" + centernum + "'";
			rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("amount");
				if ((con == null) || ("null".equals(con))) {
					intArray.setPersonAmt(0);
				} else {
					intArray.setPersonAmt(rs.getDouble("amount"));
				}
			}
			rs.close();

			// 个人付费
			sql = "select sum(b.personal_pay) as amount,count(*) from team_account_item_list b " + "where b.exam_num='"
					+ exam_num + "' and b.acc_num='"+acc_num+"'  and b.center_num='" + centernum + "'";
			rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("amount");
				if ((con == null) || ("null".equals(con))) {
					intArray.setQfAmt(0);
				} else {
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
		return intArray;
	}

	/**
	 * 
	 * @Title: getExaminfoChargingItemforC @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * List<TeamExaminfoChargingCEDTO> @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getExaminfoChargingItemforC(String acc_num, String centernum, String sort,
			String order) throws ServiceException {
		String sql = "select b.item_code,b.item_name,m.charging_item_id,m.chargingcount,m.amount from charging_item b,("
				+ " select a.charging_item_id,a.charging_item_code,COUNT(*) as chargingcount,sum(a.acc_charge) as amount from team_account_item_list a"
				+ " where a.acc_num='" + acc_num + "' and a.center_num='" + centernum
				+ "' group by a.charging_item_id,a.charging_item_code) m " + "where m.charging_item_code=b.item_code";
		if (sort != null && !"".equals(sort)) {
			sql += " order by " + sort + " " + order;
		} else {
			sql += " order by amount ";
		}
		PageSupport map = this.jqm.getList(sql, 1, 10000, TeamExaminfoChargingCEDTO.class);
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			es = map.getList();
		}
		return es;
	}

	/**
	 * 
	 * @Title: getExaminfoChargingItemforE @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * List<TeamExaminfoChargingCEDTO> @throws
	 */
	public PageReturnDTO getExaminfoChargingItemforE(String acc_num, String centernum, String sort, String order,
			int pageno, int pagesize) throws ServiceException {
		String sql = " select b.user_name,b.id_num,b.sex,c.age,m.chargingcount,m.amount,m.exam_num from customer_info b,exam_info c,"
				+ "( select a.exam_num,COUNT(*) as chargingcount,sum(a.acc_charge) as amount from team_account_item_list a "
				+ " where a.acc_num='" + acc_num + "' and a.center_num='" + centernum + "' "
				+ "group by a.exam_num) m where c.exam_num=m.exam_num and c.customer_id=b.id";
		if (sort != null && !"".equals(sort)) {
			sql += " order by " + sort + " " + order;
		} else {
			sql += " order by amount ";
		}
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, TeamExaminfoChargingCEDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}

	/**
	 * 
	 * @Title: getExaminfoChargingItemforE @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param comid @param: @param
	 * batchid @param: @param centernum @param: @return @param: @throws
	 * ServiceException @return: List<TeamExaminfoChargingCEDTO> @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getnoExaminfoforE(long comid, long batchid, String centernum)
			throws ServiceException {
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.companyService.getCompanyForAll(comid);
		String comids = "";
		for (TreeDTO t : tr) {
			comids = comids + t.getId() + ",";
		}
		if (comids.length() > 0) {
			comids = comids.substring(0, comids.length() - 1);
		String sql = "  select b.user_name,b.id_num,b.sex,c.age,m.com_name,c.id,c.exam_num from customer_info b,exam_info c "
				+ "left join company_info m on m.id=c.company_id,(select a.examinfo_id from charging_item b,examinfo_charging_item a,exam_info c "
				+ " where b.isActive='Y' and a.isActive='Y' and c.is_Active='Y' and a.exam_status='N' and a.center_num = '"+centernum+"' "
				+ " and c.exam_num=a.exam_num and c.batch_id='"+batchid+"' and a.pay_status<>'M' and c.company_id  in(" + comids + ") "
				+ " and c.center_num='"+centernum+"' and a.charge_item_id=b.id group by a.examinfo_id) md "
				+ " where c.id=md.examinfo_id and b.id=c.customer_id order by m.com_name,b.user_name desc";
		es= this.jqm.getList(sql,TeamExaminfoChargingCEDTO.class);
	   }
		return es;
	}

	/**
	 * 
	 * @Title: getnoExaminfoforC @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param comid @param: @param
	 * batchid @param: @param centernum @param: @return @param: @throws
	 * ServiceException @return: List<TeamExaminfoChargingCEDTO> @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getnoExaminfoforC(long comid, long batchid, String centernum)
			throws ServiceException {
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.companyService.getCompanyForAll(comid);
		String comids = "";
		for (TreeDTO t : tr) {
			comids = comids + t.getId() + ",";
		}
		if (comids.length() > 0) {
			comids = comids.substring(0, comids.length() - 1);
		String sql = "select DISTINCT b.id,b.item_code,b.item_name from charging_item b,examinfo_charging_item a,exam_info c "
				+ "where b.isActive='Y' and a.isActive='Y' and c.is_Active='Y' and a.exam_status='N' and a.center_num = '"+centernum+"' "
				+ "and c.exam_num=a.exam_num and a.pay_status<>'M' and c.batch_id='"+batchid+"' and c.company_id  in(" + comids + ")  "
				+ "and c.center_num='"+centernum+"' and a.charge_item_id=b.id order by b.item_code";
		es = this.jqm.getList(sql,TeamExaminfoChargingCEDTO.class);
		}
		return es;
	}
	
	/**
	 * 
	     * @Title: getnoExaminfoforCExamInfo   
	     * @Description: 未检出项目对应人员
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param chargeitemid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> getnoExaminfoforCExamInfo(long comid, long batchid,long chargeitemid,String centernum)
			throws ServiceException {
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.companyService.getCompanyForAll(comid);
		String comids = "";
		for (TreeDTO t : tr) {
			comids = comids + t.getId() + ",";
		}
		if (comids.length() > 0) {
			comids = comids.substring(0, comids.length() - 1);
		
		String sql = "  select b.user_name,b.id_num,b.sex,c.age,m.com_name,c.id,c.exam_num from customer_info b,exam_info c "
				+ "  left join company_info m on m.id=c.company_id,"
				+ "(select a.examinfo_id from charging_item b,examinfo_charging_item a,exam_info c "
				+ "where b.isActive='Y' and a.isActive='Y' and c.is_Active='Y' and a.exam_status='N' and a.center_num = '"+centernum+"' "
				+ "and c.exam_num=a.exam_num and c.batch_id='"+batchid+"' and a.pay_status<>'M' and c.company_id in("+comids+") "
				+ "and c.center_num='"+centernum+"' and a.charge_item_id=b.id and a.charge_item_id="+chargeitemid+" "
				+ "group by a.examinfo_id) md where c.id=md.examinfo_id and b.id=c.customer_id "
				+ "order by m.com_name,b.user_name desc";
		es = this.jqm.getList(sql, TeamExaminfoChargingCEDTO.class);
		}
		return es;
	}
	
	/**
	 * 
	     * @Title: nottermaEChargItemList   
	     * @Description: 未检查人员对应项目列表  
	     * @param: @param comid
	     * @param: @param batchid
	     * @param: @param chargeitemid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<TeamExaminfoChargingCEDTO>      
	     * @throws
	 */
	public List<TeamExaminfoChargingCEDTO> nottermaEChargItemList(long comid, long batchid,String exam_num,String centernum)
			throws ServiceException {
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		List<TeamExaminfoChargingCEDTO> es = new ArrayList<TeamExaminfoChargingCEDTO>();
		tr = this.companyService.getCompanyForAll(comid);
		String comids = "";
		for (TreeDTO t : tr) {
			comids = comids + t.getId() + ",";
		}
		if (comids.length() > 0) {
			comids = comids.substring(0, comids.length() - 1);
		}
		String sql = "select b.id,b.item_code,b.item_name from charging_item b,examinfo_charging_item a,exam_info c "
				+ " where a.isActive='Y' and c.is_Active='Y'  and a.pay_status<>'M' and a.center_num = '"+centernum+"' "
				+ "and a.exam_status='N' and c.exam_num=a.exam_num "
				+ " and c.center_num='"+centernum+"' and a.charge_item_id=b.id and c.exam_num='"+exam_num+"' order by b.item_code";
		es = this.jqm.getList(sql,TeamExaminfoChargingCEDTO.class);
		return es;
	}

	/**
	 * 
	 * @Title: searchteamdefamtC @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * charging_item_id @param: @param amt @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * List<TeamExaminfoChargingItemDTO> @throws
	 */
	public void searchteamdefamtC(List<TeamExaminfoChargingCEDTO> listset, String acc_num, double decamt,
			String center_num) throws ServiceException {
		boolean updateflag = false;
		for (TeamExaminfoChargingCEDTO tc : listset) {
			String sql = "select id,acc_num,exam_num,charging_item_id,price,acc_charge,PrePay,"
					+ "discard,in_date,creater,rate,center_num,personal_pay,dec_charges from team_account_item_list"
					+ " where acc_num='" + acc_num + "' and charging_item_id='" + tc.getCharging_item_id()
					+ "' and center_num='" + center_num + "'";
			PageSupport map = this.jqm.getList(sql, 1, 10000, TeamExaminfoChargingItemDTO.class);
			List<TeamExaminfoChargingItemDTO> eslist = new ArrayList<TeamExaminfoChargingItemDTO>();
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				eslist = map.getList();
			}
			for (TeamExaminfoChargingItemDTO tcone : eslist) {
				if (decamt >= tcone.getAcc_charge()) {
					double dec_charges = tcone.getDec_charges() + tcone.getAcc_charge();
					String sql1 = " update team_account_item_list set acc_charge=0,dec_charges=(case when dec_charges IS NULL then 0 else dec_charges end)+"
							+ dec_charges + " where acc_num='" + acc_num + "' and charging_item_id='"
							+ tc.getCharging_item_id() + "' and exam_num='" + tcone.getExam_num() + "' and center_num='"
							+ center_num + "'";
					System.out.println(sql1);
					this.jpm.executeSql(sql1);
					decamt = decamt - tcone.getAcc_charge();
				} else if ((decamt > 0) && (decamt < tcone.getAcc_charge())) {
					double mamt = tcone.getAcc_charge() - decamt;
					double dec_charges = tcone.getDec_charges() + decamt;
					String sql1 = " update team_account_item_list set acc_charge=" + mamt
							+ ",dec_charges=(case when dec_charges IS NULL then 0 else dec_charges end)+" + dec_charges
							+ " where acc_num='" + acc_num + "' and charging_item_id='" + tc.getCharging_item_id()
							+ "' and exam_num='" + tcone.getExam_num() + "' and center_num='" + center_num + "'";
					System.out.println(sql1);
					this.jpm.executeSql(sql1);
					decamt = 0;
					updateflag = true;
				}
				if (updateflag) {
					break;
				}
			}
			if (updateflag) {
				break;
			}
		}
	}

	/**
	 * 
	 * @Title: searchteamdefamtE @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param listset @param: @param
	 * acc_num @param: @param decamt @param: @param center_num @param: @throws
	 * ServiceException @return: void @throws
	 */
	public void searchteamdefamtE(List<TeamExaminfoChargingCEDTO> listset, String acc_num, double decamt,
			String center_num) throws ServiceException {
		boolean updateflag = false;
		for (TeamExaminfoChargingCEDTO tc : listset) {
			String sql = "select id,acc_num,exam_num,charging_item_id,price,acc_charge,PrePay,"
					+ "discard,in_date,creater,rate,center_num,personal_pay,dec_charges from team_account_item_list"
					+ " where acc_num='" + acc_num + "' and exam_num='" + tc.getExam_num() + "' and center_num='"
					+ center_num + "'";
			PageSupport map = this.jqm.getList(sql, 1, 10000, TeamExaminfoChargingItemDTO.class);
			List<TeamExaminfoChargingItemDTO> eslist = new ArrayList<TeamExaminfoChargingItemDTO>();
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				eslist = map.getList();
			}
			for (TeamExaminfoChargingItemDTO tcone : eslist) {
				if (decamt >= tcone.getAcc_charge()) {
					double dec_charges = tcone.getDec_charges() + tcone.getAcc_charge();
					String sql1 = " update team_account_item_list set acc_charge=0,dec_charges=(case when dec_charges IS NULL then 0 else dec_charges end)+"
							+ dec_charges + " where acc_num='" + acc_num + "' and exam_num='" + tc.getExam_num()
							+ "' and charging_item_id='" + tcone.getCharging_item_id() + "' and center_num='"
							+ center_num + "'";
					System.out.println(sql1);
					this.jpm.executeSql(sql1);
					decamt = decamt - tcone.getAcc_charge();
				} else if ((decamt > 0) && (decamt < tcone.getAcc_charge())) {
					double mamt = tcone.getAcc_charge() - decamt;
					double dec_charges = tcone.getDec_charges() + decamt;
					String sql1 = " update team_account_item_list set acc_charge=" + mamt
							+ ",dec_charges=(case when dec_charges IS NULL then 0 else dec_charges end)+" + dec_charges
							+ " where acc_num='" + acc_num + "' and exam_num='" + tc.getExam_num()
							+ "'  and charging_item_id='" + tcone.getCharging_item_id() + "' and center_num='"
							+ center_num + "'";
					System.out.println(sql1);
					this.jpm.executeSql(sql1);
					decamt = 0;
					updateflag = true;
				}
				if (updateflag) {
					break;
				}
			}
			if (updateflag) {
				break;
			}
		}
	}

	/**
	 * 
	 * @Title: searchteamdefamtP @Description: 平均减免 @param: @param
	 * listset @param: @param acc_num @param: @param decamt @param: @param
	 * center_num @param: @throws ServiceException @return: void @throws
	 */
	public void searchteamdefamtP(List<TeamExaminfoChargingCEDTO> listset, String acc_num, double totaldecamt,
			String center_num) throws ServiceException {

		for (TeamExaminfoChargingCEDTO tc : listset) {
			String sql = "select id,acc_num,exam_num,charging_item_id,price,acc_charge,PrePay,"
					+ "discard,in_date,creater,rate,center_num,personal_pay,dec_charges from team_account_item_list"
					+ " where acc_num='" + acc_num + "' and exam_num='" + tc.getExam_num() + "' and center_num='"
					+ center_num + "'";
			PageSupport map = this.jqm.getList(sql, 1, 10000, TeamExaminfoChargingItemDTO.class);
			List<TeamExaminfoChargingItemDTO> eslist = new ArrayList<TeamExaminfoChargingItemDTO>();
			if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
				eslist = map.getList();
			}
			double decamt = totaldecamt;
			boolean updateflag = false;
			for (TeamExaminfoChargingItemDTO tcone : eslist) {
				if (decamt >= tcone.getAcc_charge()) {
					double dec_charges = tcone.getDec_charges() + tcone.getAcc_charge();
					String sql1 = " update team_account_item_list set acc_charge=0,dec_charges=(case when dec_charges IS NULL then 0 else dec_charges end)+"
							+ dec_charges + " where acc_num='" + acc_num + "' and exam_num='" + tc.getExam_num()
							+ "' and charging_item_id='" + tcone.getCharging_item_id() + "' and center_num='"
							+ center_num + "'";
					System.out.println(sql1);
					this.jpm.executeSql(sql1);
					decamt = decamt - tcone.getAcc_charge();
				} else if ((decamt > 0) && (decamt < tcone.getAcc_charge())) {
					double mamt = tcone.getAcc_charge() - decamt;
					double dec_charges = tcone.getDec_charges() + decamt;
					String sql1 = " update team_account_item_list set acc_charge=" + mamt
							+ ",dec_charges=(case when dec_charges IS NULL then 0 else dec_charges end)+" + dec_charges
							+ " where acc_num='" + acc_num + "' and exam_num='" + tc.getExam_num()
							+ "'  and charging_item_id='" + tcone.getCharging_item_id() + "' and center_num='"
							+ center_num + "'";
					System.out.println(sql1);
					this.jpm.executeSql(sql1);
					decamt = 0;
					updateflag = true;
				}
				if (updateflag) {
					break;
				}
			}

		}
	}

	/**
	 * 
	 * @Title: teamAccountExamway @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param acc_num @param: @param
	 * centernum @param: @return @param: @throws ServiceException @return:
	 * List<TeamAccountWayDTO> @throws
	 */
	public PageReturnDTO teamAccountExamway(String acc_num, int pageno, int pagesize) throws ServiceException {
		String sql = " select a.id,a.account_num,a.charging_way,amount,a.update_time,b.data_name as chargingwayname FROM charging_way_group a "
				+ " left join data_dictionary b on a.charging_way=b.id where a.account_num='" + acc_num + "' ";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, TeamAccountWayDTO.class);
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(pageno);
		webrole.setRp(pagesize);
		if ((map != null) && (map.getList().size() > 0)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
			double amttotal = 0;
			for (int i = 0; i < map.getList().size(); i++) {
				TeamAccountWayDTO ta = new TeamAccountWayDTO();
				ta = (TeamAccountWayDTO) map.getList().get(i);
				amttotal = amttotal + ta.getAmount();
			}

			List footer = new ArrayList();
			TeamAccountWayFootDTO taf = new TeamAccountWayFootDTO();
			taf.setAmount(amttotal);
			taf.setAccount_num("合计金额");
			footer.add(taf);
			webrole.setFooter(footer);
		}

		return webrole;
	}

	/**
	 * 
	 * @Title: getTeamWayCount @Description: 获取支付方式余额 @param: @param
	 * account_num @param: @param centernum @param: @return @param: @throws
	 * ServiceException @return: double @throws
	 */
	public double getTeamWayCount(long barchid, String account_num, String centernum) throws ServiceException {
		Connection connection = null;
		double countamt = 0;
		try {
			connection = this.jqm.getConnection();
			// 总个数
			String sql = "SELECT SUM(amount2+additional) as amt FROM charging_summary_group where account_num='"
					+ account_num + "' and center_num='"+centernum+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			if (rs.next()) {
				String con = rs.getString("amt");
				if ((con == null) || ("null".equals(con))) {
					countamt = 0;
				} else {
					countamt = (rs.getDouble("amt"));
				}
			}
			rs.close();
			if (countamt < 0) {
				countamt = 0;
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
		return countamt;
	}

	/**
	 * 
	 * @Title: gettrjnTermExamInfoUserList @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param com_id @param: @param
	 * batchid @param: @param exam_num @param: @param groupid @param: @param
	 * status @param: @param sex @param: @param username @param: @param
	 * set_id @param: @param rylb @param: @param time1 @param: @param
	 * time2 @param: @param levels @param: @param userid @param: @param
	 * centernum @param: @param pagesize @param: @param pageno @param: @param
	 * sort @param: @param order @param: @return @param: @throws
	 * ServiceException @return: PageReturnDTO @throws
	 */
	public PageReturnDTO gettrjnTermExamInfoUserList(long com_id, long batchid, String exam_num, long groupid,
			String status, String sex, String username, long set_id, String rylb, String time1, String time2,
			String levels, long userid, String centernum, int pagesize, int pageno, String sort, String order,String center_num)
			throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back ");
		sb.append(" from customer_info a ,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
//		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' ");
		sb.append(" and  c.center_num='" + centernum + "' ");
		sb.append(" and c.company_id=" + com_id + " ");
		sb.append(" and c.batch_id = '" + batchid + "' ");

		if (!StringUtil.isEmpty(exam_num)) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}

		if (!StringUtil.isEmpty(rylb)) {
			sb.append(" and c.customer_type_id = '" + rylb + "' ");
		}

		if (groupid > 0) {
			sb.append(" and c.group_id = '" + groupid + "' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level='" + levels + "' ");
		}

		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status = '" + status + "' ");
		}

		if (set_id > 0) {
//			sb.append(" and m.exam_set_id= '" + set_id + "' ");
			sb.append("and c.id in (select es.examinfo_id from examinfo_set es where es.exam_set_id = '"+set_id+"' and es.examinfo_id = c.id and es.isActive='Y')");
		}

		if (!StringUtil.isEmpty(time1) && (!StringUtil.isEmpty(time2))) {
			sb.append(" and c.join_date>= '" + time1
					+ " 00:00:00' and c.join_date<= '" + time2 + " 23:59:59' ");
		} else {
			// sb.append(" and CONVERT(varchar(50),c.join_date,23)>= '" +
			// DateTimeUtil.getDate2() + "' and
			// CONVERT(varchar(50),c.join_date,23)<= '" +
			// DateTimeUtil.getDate2() + "' ");
		}

		sb.append(
				" and not exists (select * from team_account_trjn l where l.exam_num=c.exam_num and is_active='Y' ) ");

		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
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
				PageReturnDTO batchlist = this.batchService.getGroupSetList(eu.getGroup_id(), 1000, 1,center_num);
				if ((batchlist != null) && (batchlist.getRows() != null) && (batchlist.getRows().size() > 0)) {
					for (int j = 0; j < batchlist.getRows().size(); j++) {
						GroupSetDTO gs = new GroupSetDTO();
						gs = (GroupSetDTO) batchlist.getRows().get(j);
						setnums = setnums + "," + gs.getSet_name();
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

	/**
	 * 
	 * @Title: gettrjndo @Description: TODO(这里用一句话描述这个方法的作用) @param: @param
	 * ids @param: @param userid @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * PageReturnDTO @throws
	 */
	public String savetrjndo(String ids, long userid, String center_num) throws ServiceException {
		String[] idslist = ids.split(",");
		for (String exam_num : idslist) {

			List<TeamAccountTrjn> tatrjn = new ArrayList<TeamAccountTrjn>();
			tatrjn = qm.find("  from TeamAccountTrjn  where is_active='Y' and exam_num='" + exam_num + "'");

			if (tatrjn.size() <= 0) {

				ExamInfo ei = new ExamInfo();
				ei = getExamInfoForNum(exam_num);
				if (ei != null) {
					String arch_num = "";
					long batch_id = ei.getBatch_id();
					// 获取 arch_num
					CustomerInfo ci = new CustomerInfo();
					ci = this.getCustomerInfoForId(ei.getCustomer_id());
					if (ci != null) {
						arch_num = ci.getArch_num();
					}

					// 插入日结表
					String acc_num = this.batchService.GetCreateID("rcpt_num", center_num);
					TeamAccountTrjn tat = new TeamAccountTrjn();
					tat.setAcc_num(acc_num);
					tat.setArch_num(arch_num);
					tat.setBatchid(batch_id);
					tat.setCenter_num(center_num);
					tat.setCreatedate(DateTimeUtil.getDateTime());
					tat.setCreater(userid);
					tat.setUpdatedate(DateTimeUtil.getDateTime());
					tat.setUpdater(userid);
					tat.setExam_num(exam_num);
					tat.setIs_active("Y");
					tat.setPrintflag("N");
					this.pm.save(tat);

					// 保存流水表
					CardDeal cd = new CardDeal();
					cd.setCard_num(acc_num);
					cd.setCreater(userid);
					cd.setDeal_date(DateTimeUtil.getDate2());
					cd.setDeal_time(DateTimeUtil.parse());
					cd.setDeal_type("0");
					cd.setExaminfo_id(ei.getId());
					cd.setTrancode("003");
					this.pm.save(cd);
				}
			}
		}
		return "ok-处理完成";
	}

	/**
	 * 
	 * @Title: updatetrjndo @Description: TODO(这里用一句话描述这个方法的作用) @param: @param
	 * ids @param: @param userid @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String updatetrjndo(String ids, long userid, String center_num) throws ServiceException {
		String[] idslist = ids.split(",");
		for (String acc_num : idslist) {
			List<TeamAccountTrjn> tatrjn = new ArrayList<TeamAccountTrjn>();
			tatrjn = qm.find("  from TeamAccountTrjn  where acc_num='" + acc_num + "'");

			for (TeamAccountTrjn tat : tatrjn) {

				tat.setIs_active("N");
				tat.setUpdatedate(DateTimeUtil.getDateTime());
				tat.setUpdater(userid);
				this.pm.update(tat);

				ExamInfo ei = new ExamInfo();
				ei = getExamInfoForNum(tat.getExam_num());

				// 保存流水表
				CardDeal cd = new CardDeal();
				cd.setCard_num(acc_num);
				cd.setCreater(userid);
				cd.setDeal_date(DateTimeUtil.getDate2());
				cd.setDeal_time(DateTimeUtil.parse());
				cd.setDeal_type("1");
				cd.setExaminfo_id(ei.getId());
				cd.setTrancode("003");
				this.pm.save(cd);
			}
		}
		return "ok-处理完成";
	}

	/**
	 * 
	 * @Title: getExamInfoFor @Description: TODO(这里用一句话描述这个方法的作用) @param: @param
	 * exam_num @param: @return @return: ExamInfo @throws
	 */
	public ExamInfo getExamInfoForNum(String exam_num) throws ServiceException {
		List<ExamInfo> eilist = new ArrayList<ExamInfo>();
		ExamInfo ei = new ExamInfo();
		eilist = qm.find("  from ExamInfo  where exam_num='" + exam_num + "'");
		if (eilist.size() > 0) {
			return eilist.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: geCustomerInfoForId @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param cust_id @param: @return @return:
	 * CustomerInfo @throws
	 */
	public CustomerInfo getCustomerInfoForId(long cust_id) throws ServiceException {
		List<CustomerInfo> eilist = new ArrayList<CustomerInfo>();
		CustomerInfo ei = new CustomerInfo();
		eilist = qm.find("  from CustomerInfo  where  id=" + cust_id + "");
		if (eilist.size() > 0) {
			return eilist.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @Title: gettrjnTermExamInfoUsershow @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param com_id @param: @param
	 * batchid @param: @param exam_num @param: @param groupid @param: @param
	 * status @param: @param sex @param: @param username @param: @param
	 * set_id @param: @param rylb @param: @param joinsdate @param: @param
	 * joinedate @param: @param accsdate @param: @param accedate @param: @param
	 * isprint @param: @param levels @param: @param userid @param: @param
	 * centernum @param: @param pagesize @param: @param pageno @param: @param
	 * sort @param: @param order @param: @return @param: @throws
	 * ServiceException @return: PageReturnDTO @throws
	 */
	public PageReturnDTO gettrjnTermExamInfoUsershow(long com_id, long batchid, String exam_num, long groupid,
			String status, String sex, String username, long set_id, String rylb, String joinsdate, String joinedate,
			String accsdate, String accedate, String isprint, String levels, long userid, String centernum,
			int pagesize, int pageno, String sort, String order,String center_num) throws ServiceException {
		StringBuffer sb = new StringBuffer();
		sb.append("select c.id,a.user_name,a.arch_num,a.id_num,a.sex,c.age,c.exam_num,c.is_marriage,"
				+ "c.position,c._level,c.group_id,n.group_name,c.remarke,c.others,c.status,c.exam_type"
				+ ",c.employeeID");
		sb.append(" ,c.is_need_barcode,c.is_need_guide,c.phone,n.group_name,"
				+ "c.register_date,c.join_date,c.exam_times,x.chi_name,c.is_guide_back,"
				+ "e.acc_num,e.createdate,e.printflag,e.printer,e.printdate ");
		sb.append(" from customer_info a,team_account_trjn e,exam_info c ");
		sb.append(" left join user_usr x on x.id=c.creater ");
		sb.append(" left join group_info  n on n.id=c.group_id ");
//		sb.append(" left join examinfo_set m on m.exam_num=c.exam_num");
		sb.append(" where c.customer_id=a.id ");
		sb.append(" and c.is_Active='Y' and c.exam_type='T' and c.exam_num=e.exam_num and e.is_active='Y' ");
		sb.append(" and  c.center_num='" + centernum + "' ");

		if (com_id > 0) {
			sb.append(" and c.company_id=" + com_id + " ");
		}

		if (batchid > 0) {
			sb.append(" and c.batch_id = '" + batchid + "' ");
		}

		if (!StringUtil.isEmpty(exam_num)) {
			sb.append(" and c.exam_num = '" + exam_num + "' ");
		}

		if (!StringUtil.isEmpty(rylb)) {
			sb.append(" and c.customer_type_id = '" + rylb + "' ");
		}

		if (groupid > 0) {
			sb.append(" and c.group_id = '" + groupid + "' ");
		}

		if (!StringUtil.isEmpty(username)) {
			sb.append(" and a.user_name like '%" + username + "%' ");
		}

		if (!StringUtil.isEmpty(sex)) {
			sb.append(" and a.sex = '" + sex + "' ");
		}

		if (!StringUtil.isEmpty(levels)) {
			sb.append(" and c._level='" + levels + "' ");
		}

		if (!StringUtil.isEmpty(status)) {
			sb.append(" and c.status = '" + status + "' ");
		}

		if (set_id > 0) {
//			sb.append(" and m.exam_set_id= '" + set_id + "' ");
			sb.append("and c.id in (select es.examinfo_id from examinfo_set es where es.exam_set_id = '"+set_id+"' and es.examinfo_id = c.id and es.isActive='Y')");
		}

		if (!StringUtil.isEmpty(isprint)) {
			sb.append(" and e.printflag= '" + isprint + "' ");

		}

		if (!StringUtil.isEmpty(joinsdate) && (!StringUtil.isEmpty(joinedate))) {
			sb.append(" and c.join_date>= '" + joinsdate
					+ " 00:00:00' and c.join_date<= '" + joinedate + " 23:59:59' ");
		} else {
			// sb.append(" and CONVERT(varchar(50),c.join_date,23)>= '" +
			// DateTimeUtil.getDate2() + "' and
			// CONVERT(varchar(50),c.join_date,23)<= '" +
			// DateTimeUtil.getDate2() + "' ");
		}

		if (!StringUtil.isEmpty(accsdate) && (!StringUtil.isEmpty(accedate))) {
			sb.append(" and e.createdate>= '" + accsdate
					+ " 00:00:00' and e.createdate<= '" + accedate + " 23:59:59' ");
		} else {
			// sb.append(" and CONVERT(varchar(50),c.join_date,23)>= '" +
			// DateTimeUtil.getDate2() + "' and
			// CONVERT(varchar(50),c.join_date,23)<= '" +
			// DateTimeUtil.getDate2() + "' ");
		}

		if (sort != null && !"".equals(sort)) {
			sb.append(" order by " + sort + " " + order);
		} else {
			sb.append(" order by e.acc_num ");
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
				PageReturnDTO batchlist = this.batchService.getGroupSetList(eu.getGroup_id(), 1000, 1,center_num);
				if ((batchlist != null) && (batchlist.getRows() != null) && (batchlist.getRows().size() > 0)) {
					for (int j = 0; j < batchlist.getRows().size(); j++) {
						GroupSetDTO gs = new GroupSetDTO();
						gs = (GroupSetDTO) batchlist.getRows().get(j);
						setnums = setnums + "," + gs.getSet_name();
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

				if ((eu.getPrintdate() != null) && (eu.getPrintdate().trim().length() > 10)) {
					if (eu.getPrintdate().indexOf("1900-01-01") >= 0) {
						eu.setPrintdate("");
					} else {
						eu.setPrintdate(eu.getPrintdate());
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
	public List<TeamAccountAdditionalDTO> getteamaccountaddlist(String acc_num, String itemids)
			throws ServiceException {
		if ("".equals(itemids)) {
			itemids = "0";
		}
		// String sql = "select a.*,taa.amount from "
		// +"(select c.item_name,c.id as item_id,t.acc_num from group_info
		// g,group_charging_item gc,charging_item c,team_account t "
		// +"where g.id = gc.group_id and gc.charge_item_id = c.id and t.acc_num
		// = '"+acc_num+"' and t.batchid = g.batch_id "
		// +"and gc.charge_item_id in ("+itemids+") and gc.isActive = 'Y' "
		// +") a left join team_account_additional taa on a.acc_num =
		// taa.acc_num and a.item_id = taa.item_id";
		String sql = "select a.*,taa.amount from (select c.item_name,c.id as item_id from charging_item c "
				+ "where c.id in (" + itemids + ")) a left join team_account_additional taa on " + "taa.acc_num = '"
				+ acc_num + "' and a.item_id = taa.item_id";
		List<TeamAccountAdditionalDTO> list = this.jqm.getList(sql, TeamAccountAdditionalDTO.class);
		return list;
	}

	@Override
	public void saveteamaccountadd(List<TeamAccountAdditionalDTO> teamaddlist, String acc_num, long userid)
			throws ServiceException {
		List<TeamAccountAdditional> list = this.qm
				.find("from TeamAccountAdditional t where t.acc_num = '" + acc_num + "'");
		for (TeamAccountAdditional team : list) {
			this.pm.remove(team);
		}
		for (TeamAccountAdditionalDTO teamdto : teamaddlist) {
			TeamAccountAdditional team = new TeamAccountAdditional();

			team.setAcc_num(acc_num);
			team.setItem_id(teamdto.getItem_id());
			team.setItem_name(teamdto.getItem_name());
			team.setAmount(teamdto.getAmount());
			team.setCreater(userid);
			team.setCreate_time(DateTimeUtil.parse());
			team.setUpdater(userid);
			team.setUpdate_time(DateTimeUtil.parse());
			this.pm.save(team);
		}
	}

	/**
	 * 
	 * @Title: getChargingSummaryGroupOfaccountnum @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param
	 * account_num @param: @return @param: @throws ServiceException @return:
	 * ChargingSummaryGroup @throws
	 */
	public ChargingSummaryGroup getChargingSummaryGroupOfaccountnum(String account_num) throws ServiceException {
		List<ChargingSummaryGroup> invoiceList = this.qm
				.find("from ChargingSummaryGroup where account_num = '" + account_num + "'");
		ChargingSummaryGroup cis = new ChargingSummaryGroup();
		if (invoiceList.size() != 0) {
			cis = invoiceList.get(0);
		}
		return cis;
	}
	
	public ChargingSummaryGroup getChargingSummaryGroupOfId(long id) throws ServiceException {
		List<ChargingSummaryGroup> invoiceList = this.qm
				.find("from ChargingSummaryGroup where id = '" + id + "'");
		ChargingSummaryGroup cis = new ChargingSummaryGroup();
		if (invoiceList.size() != 0) {
			cis = invoiceList.get(0);
		}
		return cis;
	}
	
	
	
	 /**
	 * 
	     * @Title: delaccountGroupTT   
	     * @Description: 删除结帐单号
	     * @param: @param barchid
	     * @param: @param account_num
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	 public String delHisaccountGroupTT(long barchid,String account_num,String userid,String center_num) throws ServiceException{
			Connection connection = null;
			String message = "error-error";			
			try {
				connection = this.jqm.getConnection();
				connection.setAutoCommit(false);
				// 总个数
				String sql = "SELECT invoice_id,daily_status,receiv_status FROM charging_summary_group where account_num='" + account_num
						+ "' and is_active='Y' and batch_id='" + barchid + "' and center_num='"+center_num+"'";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				if (rs.next()) {				
					int receiv_status = rs.getInt("receiv_status");
					rs.close();					
					if(receiv_status==1){
						message = "error-结帐单已经结帐，操作不能继续";
					} else if(receiv_status==2){
						message = "error-结帐单已经发送his申请，操作不能继续";
					} else {					
						String sqlupdate = "update charging_summary_group set is_active='N',updater=" + userid
								+ ",update_time='" + DateTimeUtil.getDateTime() + "' where account_num='" + account_num
								+ "' and is_active='Y' and batch_id='" + barchid + "' and center_num='"+center_num+"'";
						connection.createStatement().executeUpdate(sqlupdate);

						sqlupdate = "update team_account set balance_status='N',balancer=" + userid + ",balance_date='"
								+ DateTimeUtil.getDateTime() + "' where acc_num in"
								+ " (select acc_num from team_invoice_account where account_num='" + account_num
								+ "' and center_num='" + center_num + "' and batchid='" + barchid + "') "
								+ "and center_num='" + center_num + "' and batchid='" + barchid + "'";
						connection.createStatement().executeUpdate(sqlupdate);
						connection.commit();
						message = "ok-操作成功";
					}
				} else {
					rs.close();
					message = "error-无效结帐记录";
					connection.commit();
				}

			} catch (SQLException ex) {
				message = "error-操作失败";
				ex.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException exe) {

				}
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
	     * @Title: delaccountGroupTT   
	     * @Description: 删除结帐单号
	     * @param: @param barchid
	     * @param: @param account_num
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	 public String unHisaccountGroupTT(String account_num,String userid,String center_num) throws ServiceException{
			Connection connection = null;
			String message = "error-error";			
			try {
				connection = this.jqm.getConnection();
				// 总个数
				String sql = "SELECT invoice_id,daily_status,receiv_status FROM charging_summary_group where account_num='" + account_num
						+ "' and is_active='Y' and center_num='"+center_num+"'";
				ResultSet rs = connection.createStatement().executeQuery(sql);
				System.out.println("1x.0");
				System.out.println(sql);
				if (rs.next()) {
					System.out.println("1x.1");
					int receiv_status = rs.getInt("receiv_status");		
					System.out.println("1x.2");
					System.out.println(receiv_status);
					if(receiv_status==1){
						WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
						wcf=this.webserviceConfigurationService.getWebServiceConfig("REFUND_APPLICATION", center_num);
						UnFeeMessage fm = new UnFeeMessage();						
						ReqUnNo rrn=new ReqUnNo();
						List<String> REQ_NO= new ArrayList<String>();// 申请单号，处理成功后返回
						REQ_NO.add(account_num);
						rrn.setREQ_NO(REQ_NO);
						fm.setREQ_NOS(rrn);
						fm.setEXAM_NUM(this.getTTPersonId(account_num));
						fm.setCenter_num(center_num);
						UNFEESendMessage fsm= new UNFEESendMessage(fm);		
						FeeReqBody rb = new FeeReqBody();
						rb = fsm.feeSend(wcf.getConfig_url(), wcf.getConfig_method(), true);
						if("AA".equals(rb.getResultHeader().getTypeCode())){
							String sqlupdate = "update charging_summary_group set receiv_status=0,updater=" + userid
									+ ",update_time='" + DateTimeUtil.getDateTime() + "' where account_num='" + account_num
									+ "' and is_active='Y' and center_num='"+center_num+"' ";
							connection.createStatement().executeUpdate(sqlupdate);
							message = "ok-操作成功";
						}else{
							message = "error-"+rb.getResultHeader().getText();
						}
					} else {		
						System.out.println("1x.3");
						String sqlupdate = "update charging_summary_group set receiv_status=0,updater=" + userid
								+ ",update_time='" + DateTimeUtil.getDateTime() + "' where account_num='" + account_num
								+ "' and is_active='Y' and center_num='"+center_num+"'";
						connection.createStatement().executeUpdate(sqlupdate);
						System.out.println(sqlupdate);
						System.out.println("1x.4");
						message = "ok-操作成功";
					}
				} else {
					System.out.println("1x.5");
					message = "error-无效结帐记录";
				}
				rs.close();	
			} catch (SQLException ex) {
				message = "error-操作失败";
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
	 * @Title: delaccountGroupTT @Description: 删除结帐单号 @param: @param
	 * barchid @param: @param account_num @param: @param userid @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String delaccountGroupTT(long barchid, String account_num, String userid, String center_num)
			throws ServiceException {
		Connection connection = null;
		String message = "error-error";
		try {
			connection = this.jqm.getConnection();
			connection.setAutoCommit(false);
			// 总个数
			String sql = "SELECT invoice_id,daily_status FROM charging_summary_group where account_num='" + account_num
					+ "' and is_active='Y' and batch_id='" + barchid + "' and center_num='"+center_num+"'";
			ResultSet rs = connection.createStatement().executeQuery(sql);
			long invid = 0;
			String daily_status = "1";
			if (rs.next()) {				
				daily_status = rs.getString("daily_status");
				invid = rs.getLong("invoice_id");
				rs.close();
				if ("1".equals(daily_status)) {
					message = "error-结帐单号已经日结，不能删除";
				} else if (invid > 0) {
					message = "error-结帐单号已经关联发票，不能删除";
				} else {
					String sqlupdate = "update charging_summary_group set is_active='N',updater=" + userid
							+ ",update_time='" + DateTimeUtil.getDateTime() + "' where account_num='" + account_num
							+ "' and is_active='Y' and batch_id='" + barchid + "' and center_num='"+center_num+"'";
					connection.createStatement().executeUpdate(sqlupdate);

					sqlupdate = "update team_account set balance_status='N',balancer=" + userid + ",balance_date='"
							+ DateTimeUtil.getDateTime() + "' where acc_num in"
							+ " (select acc_num from team_invoice_account where account_num='" + account_num
							+ "' and center_num='" + center_num + "' and batchid='" + barchid + "') "
							+ "and center_num='" + center_num + "' and batchid='" + barchid + "'";
					connection.createStatement().executeUpdate(sqlupdate);
					connection.commit();
					message = "ok-操作成功";
				}
			} else {
				rs.close();
				message = "error-无效结帐记录";
				connection.commit();
			}

		} catch (SQLException ex) {
			message = "error-操作失败";
			ex.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException exe) {

			}
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
	 * @Title: saveSingleInviockTT @Description: 团体打印发票 @param: @param
	 * model @param: @param userId @param: @return @param: @throws
	 * ServiceException @return: String @throws
	 */
	public void saveSingleGroupTT(long barchid, String ids, long userId, String center_num, double add_amount)
			throws ServiceException {
		double amt = 0;
		double prices = 0;
		double additional = add_amount;
		String[] req_nums = ids.split(",");
		for (int i = 0; i < req_nums.length; i++) {
			String sql = "from TeamAccount c where c.acc_num = '" + req_nums[i] + "'";
			List<TeamAccount> list = this.qm.find(sql);
			if ((list != null) && (list.size() > 0)) {
				TeamAccount ta = new TeamAccount();
				ta = list.get(0);
				amt = amt + ta.getCharges();
				prices += ta.getPrices();
			}
		}

		// amt += add_amount;//不计算附加费到总金额

		ChargingSummaryGroup chargingSummaryGroup = new ChargingSummaryGroup();
		String paramnum = GetNumContral.getInstance().getParamNum("rcpt_num", center_num);
		chargingSummaryGroup.setAccount_num(paramnum);
		chargingSummaryGroup.setAdditional(additional);
		chargingSummaryGroup.setAmount1(prices);
		chargingSummaryGroup.setAmount2(amt);
		chargingSummaryGroup.setBatch_id(barchid);
		chargingSummaryGroup.setCash_date(DateTimeUtil.parse());
		chargingSummaryGroup.setCashier(userId);
		chargingSummaryGroup.setCreater(userId);
		chargingSummaryGroup.setCreate_time(DateTimeUtil.parse());
		chargingSummaryGroup.setUpdater(userId);
		chargingSummaryGroup.setUpdate_time(DateTimeUtil.parse());
		chargingSummaryGroup.setCharging_status("Y");
		chargingSummaryGroup.setDaily_status("0");
		chargingSummaryGroup.setInvoice_id(Long.parseLong(0+""));
		chargingSummaryGroup.setIs_print_recepit("N");
		chargingSummaryGroup.setIs_active("Y");
		chargingSummaryGroup.setIs_print_recepit("N");
		chargingSummaryGroup.setCenter_num(center_num);
		this.pm.save(chargingSummaryGroup);
		for (int i = 0; i < req_nums.length; i++) {
			TeamInvoiceAccount tia = new TeamInvoiceAccount();
			tia.setAcc_num(req_nums[i]);
			tia.setAccount_num(paramnum);
			tia.setCenter_num(center_num);
			tia.setAuditor(userId);
			tia.setAudit_date(DateTimeUtil.parse());
			tia.setBatchid(barchid);
			this.pm.save(tia);

			String sql = "from TeamAccount c where c.acc_num = '" + req_nums[i] + "'";
			List<TeamAccount> list = this.qm.find(sql);
			if ((list != null) && (list.size() > 0)) {
				TeamAccount ta = new TeamAccount();
				ta = list.get(0);
				ta.setBalance_status("Y");
				this.pm.update(ta);
			}
		}
	}
	
	/**
	 * 
	     * @Title: getTTPersonId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param accountnum
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getTTPersonId(String accountnum) {
		Connection tjtmpconnect = null;
		String req_id = "未知单位"+accountnum;
		try {
			tjtmpconnect = this.jqm.getConnection();
			String sb1 = "select f.id,f.com_name from charging_summary_group a  "
					+ "left join  (select b.id,c.com_name from batch b,company_info c "
					+ "where b.company_id=c.id) f on f.id=a.batch_id "
					+ "where  a.account_num='" + accountnum + "'";
			ResultSet rs1 = tjtmpconnect.createStatement().executeQuery(sb1);
			if (rs1.next()) {
				req_id = rs1.getString("com_name");
			}
			rs1.close();
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
		return req_id;
	}
	/**
	 * 
	     * @Title: hisSendaccountGroupTT   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param barchid
	     * @param: @param id
	     * @param: @param userid
	     * @param: @param center_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String hisSendaccountGroupTT(long barchid,long id,long userid,String center_num) throws ServiceException{
		String mesage = "";
		ChargingSummaryGroup chargingSummaryGroup = new ChargingSummaryGroup();
		chargingSummaryGroup = (ChargingSummaryGroup) this.qm.load(ChargingSummaryGroup.class, id);
		if ((chargingSummaryGroup == null) || (chargingSummaryGroup.getId() <= 0)) {
			mesage = "error-结帐单不存在";
		}else if(chargingSummaryGroup.getReceiv_status()==1){
			mesage = "error-结帐单已经结帐，操作不能继续";
		} else if(chargingSummaryGroup.getReceiv_status()==2){
			mesage = "error-结帐单已经发送his申请，操作不能继续";
		} else {
			FEETermSendMessage ft = new FEETermSendMessage(getTTPersonId(chargingSummaryGroup.getAccount_num()),chargingSummaryGroup.getAccount_num(), center_num,userid);
			FeeResultBody fb = ft.feeSend();
			if("AA".equals(fb.getResultHeader().getTypeCode())){				
				chargingSummaryGroup.setReceiv_date(DateTimeUtil.parse());
				chargingSummaryGroup.setReceiver(userid);
				chargingSummaryGroup.setReceiv_status(2);
				this.pm.save(chargingSummaryGroup);
				mesage = "ok-his请求发送成功";
			}			
		}
		return mesage;
	}

	/**
	 * 
	 * @Title: qrfkaccountGroupTT @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @param barchid @param: @param
	 * id @param: @param userid @param: @param
	 * center_num @param: @return @param: @throws ServiceException @return:
	 * String @throws
	 */
	public String qrfkaccountGroupTT(long barchid, long id, long userid, String center_num) throws ServiceException {
		String mesage = "";
		ChargingSummaryGroup chargingSummaryGroup = new ChargingSummaryGroup();
		chargingSummaryGroup = (ChargingSummaryGroup) this.qm.load(ChargingSummaryGroup.class, id);
		if ((chargingSummaryGroup == null) || (chargingSummaryGroup.getId() <= 0)) {
			mesage = "error-结帐单不存在";
		} else {
			String sql = "from ChargingWayGroup c where c.summary_id = '" + id + "'";
			List<ChargingWayGroup> list = this.qm.find(sql);
			double accountamt = 0;
			for (ChargingWayGroup cwg : list) {
				accountamt += cwg.getAmount().doubleValue();
			}

			double oldamt = chargingSummaryGroup.getAmount2() + chargingSummaryGroup.getAdditional();
			if (accountamt != oldamt) {
				mesage = "error-支付方式维护金额为[" + accountamt + "元]，实际结帐单金额为[" + oldamt + "元]，两个金额不一致，操作失败";
			} else {
				chargingSummaryGroup.setReceiv_date(DateTimeUtil.parse());
				chargingSummaryGroup.setReceiver(userid);
				chargingSummaryGroup.setReceiv_status(1);
				this.pm.save(chargingSummaryGroup);
				mesage = "ok-操作成功";
			}			
		}
		return mesage;
	}
	
	/**
	 * 
	     * @Title: teamwayList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<TeamWayDTO> teamwayList(long id) throws ServiceException{
		String sql = "select a.id as wayid,a.data_name as wayname,a.remark,c.amount,a.data_code_children from data_dictionary a "
				+ "left join (select b.charging_way,sum(b.amount) as amount from charging_way_group b where "
				+ "b.summary_id='"+id+"' group by b.charging_way) c on c.charging_way=a.id "
				+ "where data_code='SFFSLX' and isActive='Y' order by seq_code";
		List<TeamWayDTO> list = this.jqm.getList(sql, TeamWayDTO.class);
		return list;
	}
	
	/**
	 * 
	     * @Title: getExaminfoChargingItemforExamNumId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @param chargitemid
	     * @param: @param centernum
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: TeamExaminfoChargingItemDTO      
	     * @throws
	 */
	public TeamExaminfoChargingItemDTO getExaminfoChargingItemforExamNumId(String exam_num, String charging_item_code,
			String centernum) throws ServiceException {
		String sql = "select a.id,a.charging_item_id,a.prePay,a.acc_charge,"
				+ "a.acc_num,a.center_num,a.creater,a.discard,a.exam_num,a.in_date,a.personal_pay,a.price,a.rate"
				+ " from team_account_item_list a  "
				+ " where  a.charging_item_code='" + charging_item_code+ "' and a.exam_num ='"
				+ exam_num + "' and a.center_num='" + centernum
				+ "'";
		PageSupport map = this.jqm.getList(sql, 1, 10000, TeamExaminfoChargingItemDTO.class);
		TeamExaminfoChargingItemDTO es = new TeamExaminfoChargingItemDTO();
		if ((map != null) && (map.getList() != null) && (map.getList().size() > 0)) {
			es = (TeamExaminfoChargingItemDTO)map.getList().get(0);
		}
		return es;
	}
	
	@Override
	public TeamAccountExamListDTO queryTeamAccountExamListFromExamid(long exam_id,String exam_num) throws ServiceException {
		String sql = "";
		if(exam_id==0 && exam_num!=null) {
			sql = "SELECT a.id, a.acc_num, a.exam_num, a.isPrePay, a.isnotPay, a.createtime, a.center_num FROM "
					+ "team_account_exam_list a WHERE a.exam_num = '"+exam_num+"'";
		}else if(exam_id!=0 && exam_num==null) {
			sql = "SELECT a.id, a.acc_num, a.exam_num, a.isPrePay, a.isnotPay, a.createtime, a.center_num FROM "
					+ "team_account_exam_list a WHERE a.exam_num = ( "
					+ "SELECT ei.exam_num FROM exam_info ei "
					+ "WHERE 1 = 1 AND ei.exam_num = '"+exam_num+"' )";
		}
		List<TeamAccountExamListDTO> list = this.jqm.getList(sql,TeamAccountExamListDTO.class);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	@SuppressWarnings("all")
	public CompanyAccountDTO getCompanyAccountAmountByComId(StatementsModel model) throws ServiceException {
		String sql = " select  m.balance ,(select  sum(mm.trantmt) from company_account_detail mm where mm.com_num = c.com_num and mm.invaioce_type = '1' "
					+" and invoice_id > 0  group by mm.com_num) as amount ,m.com_type "
					+" from company_account m , company_info  c where c.com_num = m.com_num  and c.id = "+model.getCompany_id();
		List<CompanyAccountDTO> list = this.jqm.getList(sql, CompanyAccountDTO.class);
		CompanyAccountDTO dto = new CompanyAccountDTO();
		if(list.size() > 0){
			dto= list.get(0);
		}
		return dto;
	}
	
	
	//团体划账操作
	@Override
	@SuppressWarnings("all")
	public String saveDrawAccounts(StatementsModel model, UserDTO user) throws ServiceException {
		String war_sql = "select * from data_dictionary   where  data_code = 'SFFSLX'	 and data_code_children = '372'   and isActive = 'Y'	";
		List<DataDictionary> dataDictionaryList = this.jqm.getList(war_sql, DataDictionary.class); 
		DataDictionary dataDictionary = new DataDictionary();
		if(dataDictionaryList.size() == 1){
			dataDictionary = dataDictionaryList.get(0);
		}else if(dataDictionaryList.size() > 1){
			return "error-子编码为【 372 】的收费方式大于2个，请先确定一个收费方式为商户划账。。。";
		}else{
			return "error-系统找不到子编码为【 372 】的收费方式，请联系管理员添加收费方式为商户划账且子编码为 372";
		}
		
		String  sum_sql = "select  * from  charging_summary_group  where  account_num = '"+model.getAccount_num()+"'  and is_active = 'Y'";
		List<ChargingSummaryGroup> slist = this.jqm.getList(sum_sql, ChargingSummaryGroup.class);
		if(slist.size() < 1){
			return "error-结账单错误。。。";
		}
		
		ChargingSummaryGroup chargingSummaryGroup = slist.get(0);//结账单信息
		String    sql = "select  m.* from  company_info  c , company_account  m  where m.com_num = c.com_Num  and c.id = "+model.getCompany_id();
		List<CompanyAccount> list = this.jqm.getList(sql, CompanyAccount.class);
		if(list.size() == 1){
			CompanyAccount companyAccount = list.get(0);//获取商户信息
			if (companyAccount.getCom_type() == 0) {//商户状态正常  记录流水信息
				String sq = "select  * from company_account_detail where com_num = '"+companyAccount.getCom_num()+"'  order by jndatetime desc";  //查询 上比流水
				List<CompanyAccountDetail> mlist = this.jqm.getList(sq, CompanyAccountDetail.class);
				if(mlist.size() > 0){
					CompanyAccountDetail companyAccountDetail = mlist.get(0);
					int usednum = companyAccountDetail.getUsednum() + 1;//使用次数+1
					double balance = companyAccount.getBalance() - model.getHz_amount();//账户余额
					if(balance < 0){
						return "error-划账金额不能大于商户余额。。。";
					}
					String jnnumber  = GetNumContral.getInstance().getParamNum("sign_num", user.getCenter_num());//流水号
					String  insert = "insert into  company_account_detail (logicdate,com_num,jnnumber,prejnnumber,account_num,creater,trancode,jnstatus,jndatetime,balance,oldbalance,usednum,trantmt,chargingway,invaioce_type,center_num) "
							+" values("+"'"+DateTimeUtil.getDateTime()+"','"+companyAccountDetail.getCom_num()+"','"+jnnumber+"','"+companyAccountDetail.getJnnumber()+"','"+model.getAccount_num()+"','"+user.getUserid()+"','002',"
									+ "'"+1+"','"+DateTimeUtil.getDateTime()+"','"+balance+"','"+companyAccountDetail.getOldbalance()+"','"+usednum+"','"+model.getHz_amount()+"','"+dataDictionary.getId()+"','','"+user.getCenter_num()+"')";
					this.jpm.execSql(insert);
					
					//保存收费方式 写死 id=372 团体划账
					String  insertWay = " insert  into  charging_way_group  (summary_id,charging_way,amount,creater,create_time,updater,update_time,account_num) "
										+" values("+"'"+chargingSummaryGroup.getId()+"','"+dataDictionary.getId()+"','"+model.getHz_amount()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+user.getUserid()+"','"+DateTimeUtil.getDateTime()+"','"+chargingSummaryGroup.getAccount_num()+"')";
					this.jpm.execSql(insertWay);
					//修改商户余额金额
					String update = "update  company_account  set balance = '"+balance+"' , updater = "+user.getUserid()+" , update_date = '"+DateTimeUtil.getDateTime()+"' where  com_num = '"+companyAccount.getCom_num()+"'";
					this.jpm.execSql(update);
					return "ok-划账成功。。";
					
				}else{
					return "error-商户上笔交易不存在，请先为商户充值。。。";
				}
				
			}else if(companyAccount.getCom_type() == 1){
				
				return "error-商户被冻结, 操作不能继续。。";
			}else if(companyAccount.getCom_type() == 2){
				
				return "error-商户已销户, 操作不能继续。。";
			}else{
				
				return "error-商户状态不明确。。";
			}
						
		}else{
			return "error-单位信息错误，请先给商户开户。。";
		}
	}

	
	//撤销划账记录
	@Override
	@SuppressWarnings("all")
	public String delDrawAccounts(ChargingSummaryGroup csg, UserDTO user, long id) throws ServiceException {
		String sql = "select distinct  me.* from  company_account_detail m, company_account me where  m.com_num = me.com_num and m.account_num = '"+csg.getAccount_num()+"'  and m.trancode = '002' ";
		List<CompanyAccount> list = this.jqm.getList(sql, CompanyAccount.class);
		if(list.size() > 0){
			CompanyAccount companyAccount = list.get(0);
			if("".equals(companyAccount.getCom_num()) || companyAccount.getCom_num() == null ){
				return "error-商户编码为空，操作错误。。";
			}
			if(companyAccount.getCom_type() == 0){//商户状态正常
				String waysql = "select  * from charging_way_group  where id =  "+id;
				List<ChargingWayGroup> wlist = this.jqm.getList(waysql, ChargingWayGroup.class);
				
				if(wlist.size() != 1){
					return "error-交易类型错误。。。";
				}
				ChargingWayGroup chargingWayGroup = wlist.get(0);//收费方式 以及收费金额
				
				String sq = "select  * from company_account_detail where com_num = '"+companyAccount.getCom_num()+"'  order by jndatetime desc";  //查询 上比流水
				List<CompanyAccountDetail> mlist = this.jqm.getList(sq, CompanyAccountDetail.class);
				if(mlist.size() > 0){
					CompanyAccountDetail companyAccountDetail = mlist.get(0);
					Double balance = companyAccount.getBalance() + chargingWayGroup.getAmount();
					String jnnumber  = GetNumContral.getInstance().getParamNum("sign_num", user.getCenter_num());//流水号
					String  insert = "insert into  company_account_detail (logicdate,com_num,jnnumber,prejnnumber,account_num,creater,trancode,jnstatus,jndatetime,balance,oldbalance,usednum,trantmt,chargingway,invaioce_type,center_num) "
							+" values("+"'"+DateTimeUtil.getDateTime()+"','"+companyAccountDetail.getCom_num()+"','"+jnnumber+"','"+companyAccountDetail.getJnnumber()+"','"+csg.getAccount_num()+"','"+user.getUserid()+"','004',"
									+ "'"+1+"','"+DateTimeUtil.getDateTime()+"','"+balance+"','"+companyAccountDetail.getOldbalance()+"','"+companyAccountDetail.getUsednum()+"','"+chargingWayGroup.getAmount()+"','"+companyAccountDetail.getChargingway()+"','','"+user.getCenter_num()+"')";
					this.jpm.execSql(insert);
					//保存商户金额
					String update = "update  company_account  set balance = '"+balance+"' , updater = "+user.getUserid()+" , update_date = '"+DateTimeUtil.getDateTime()+"' where  com_num = '"+companyAccount.getCom_num()+"'";
					this.jpm.execSql(update);
					return "ok-撤销成功。。。";
				}else{
					return "error-商户上笔交易不存在，请先为商户充值。。。";
				}
				
			}else if(companyAccount.getCom_type() == 1){
				
				return "error-商户被冻结, 操作不能继续。。";
			}else if(companyAccount.getCom_type() == 2){
				
				return "error-商户已销户, 操作不能继续。。";
			}else{
				
				return "error-商户状态不明确。。";
			}
			
		}else{
			return "error-商户流水信息不存在，操作错误。。";
		}
		
	}

	@Override
	public List<ChargingWayGroup> getChargingWayByAccountNum(String account_num) throws ServiceException {
		String sql = "select cw.*  from  charging_way_group  cw ,  charging_summary_group  cs where  cs.account_num = '"+account_num+"' and (cs.id = cw.id or cs.account_num = cw.account_num)";
		List<ChargingWayGroup> list = this.jqm.getList(sql, ChargingWayGroup.class);
		return list;
	}

	@Override
	public DataDictionary getDataDictionaryById(long id) throws ServiceException {
		DataDictionary dataDictionary = new DataDictionary();
		String  sql = "select  data_code_children from data_dictionary  where   id = "+id;
		List<DataDictionary> list = this.jqm.getList(sql, DataDictionary.class);
		if(list.size() > 0){
			dataDictionary=list.get(0);
		}
		return dataDictionary;
	}
	
	@Override
	public String checkTteamAccountExamway(String acc_num, int pageno, int pagesize) throws ServiceException {
		String sql = " select a.id,a.account_num,a.charging_way,amount,a.update_time,b.data_name as chargingwayname,b.data_code_children AS datacodechildren FROM charging_way_group a "
				+ " left join data_dictionary b on a.charging_way=b.id where a.account_num='" + acc_num + "' ";
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, TeamAccountWayDTO.class);
		String resChildrenCode = "ok";
		if ((map != null) && (map.getList().size() > 0)) {
			for (int i = 0; i < map.getList().size(); i++) {
				TeamAccountWayDTO ta = new TeamAccountWayDTO();
				ta = (TeamAccountWayDTO) map.getList().get(i);
				if("372".equals(ta.getDatacodechildren())||"122".equals(ta.getDatacodechildren())) {
					resChildrenCode = "error";
					break;
				}
				
			}

		}

		return resChildrenCode;
	}

}
