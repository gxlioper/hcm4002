package com.hjw.charge.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonObject;
import com.hjw.charge.domain.InsureAccountCharge;
import com.hjw.util.DateTimeUtil;
import com.hjw.charge.DTO.CashierDailyAccDTO;
import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.wst.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.wst.DTO.CashierDailyAccPaywayDTO;
import com.hjw.charge.DTO.ChargingDetailSingleDTO;
import com.hjw.charge.DTO.ChargingInvoiceSingleDTO;
import com.hjw.charge.DTO.ChargingSummarySingleDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.CollectFeesResult;
import com.hjw.charge.DTO.InsureAccountDTO;
import com.hjw.charge.DTO.InsureVerifyAccountDetailDTD;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.charge.DTO.PosDailySummaryDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.charge.DTO.WebCityQuerySendDTO;
import com.hjw.charge.DTO.WebCityReconciliationSendDTO;
import com.hjw.charge.DTO.WebSocketPosSendDTO;
import com.hjw.charge.model.CashierDailyAccModel;
import com.hjw.charge.service.CashierDailyAccChargeService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.StringUtils;

public class CashierDailyAccChargeServiceImpl implements CashierDailyAccChargeService{

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public QueryManager getQueryManager(){
		return this.qm;
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
	

	
	@Override
	public List<CashierDailyAccListDTO> getCashierDailyAcclist(long user_id) throws ServiceException {
		String sql = "select c.user_name,c.sex,c.id_num,e.exam_num,e.id,e.age,e.phone,e.address,v.amount1,"
				+ "v.amount2,d.data_name as charging_way,w.amount as way_amount,d.id as charging_way_id "
				+ "from customer_info c,exam_info e,v_daily_cashier_summary v,v_daily_cashier_way w "
				+ "left join data_dictionary d on w.charging_way = d.id where e.id = v.exam_id and c.id = e.customer_id "
				+ "and v.cashier = "+user_id+" and e.id = w.exam_id and w.cashier = "+user_id +" order by e.id";
		List<CashierDailyAccListDTO> list = this.jqm.getList(sql, CashierDailyAccListDTO.class);
		return list;
	}
	
	@Override
	public List<ChargingWayDTO> getChargingWay(long user_id) throws ServiceException {
		String sql = " select distinct * from ( select d.id,d.data_name charging_way from v_daily_cashier_way v left join "
				+ " data_dictionary d on d.id = v.charging_way where v.cashier = "+ user_id
				+ " union all"
				+ " select d.id,d.data_name charging_way from charging_summary_group c,charging_way_group w left join"
				+ " data_dictionary d on d.id = w.charging_way where c.id = w.summary_id"
				+ " and c.daily_status = '0' and c.receiv_status = '1' and c.is_active = 'Y' and c.cashier = "+user_id+" ) a";
		List<ChargingWayDTO> list = this.jqm.getList(sql, ChargingWayDTO.class);
		return list;
	}
	@Override
	public List<ChargingInvoiceSingleDTO> getCashierInvoiceList(long user_id) throws ServiceException {
		String sql = "select * from( select c.invoice_num,(case when c.invoice_status = 'N' then -c.invoice_amount "
				+ " else c.invoice_amount end) invoice_amount,c.invoice_status,c.invoice_time,d.data_name as invoice_class,c.exam_type "
				+ " from charging_invoice_single c left join data_dictionary d on d.id = c.invoice_class "
				+ " where c.daily_status = '0' and c.invoice_maker = "+user_id
				+ " union all "
				+ " select n.invoice_num,(case when n.invoice_status = 'N' then -c.invoice_amount "
				+ " else c.invoice_amount end) invoice_amount,n.invoice_status,n.create_time invoice_time,d.data_name as invoice_class,c.exam_type "
				+ " from nullify_invoice n left join charging_invoice_single c on c.invoice_num = n.invoice_num "
				+ " and c.invoice_class = n.invoice_class left join data_dictionary d on d.id = n.invoice_class "
				+ " where n.daily_status = '0' and n.creater = "+user_id+") "
				+ " a order by a.invoice_time";
		List<ChargingInvoiceSingleDTO> list = this.jqm.getList(sql, ChargingInvoiceSingleDTO.class);
		return list;
	}
	@Override
	public String saveCashierDailyacc(UserDTO user) throws ServiceException {
		Connection con = null;
		String result;
		try {
			con = this.jqm.getConnection();
			CallableStatement c = con.prepareCall("{call pro_cashier_daily(?,?,?)}");
			c.setLong(1, user.getUserid());
			// 注册存储过程的第二个参数 执行结束 0成功，1失败
			c.registerOutParameter(2, java.sql.Types.INTEGER);
			// 注册存储过程的第三个参数 失败原因
			c.registerOutParameter(3, java.sql.Types.VARCHAR);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			if(c.getInt(2) == 0){
				result = "ok-"+c.getString(3);
			}else{
				result = "error-"+c.getString(3);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result = "error-"+ex.getMessage();
		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException sqle4) {
				System.out.println("close connection exception: " + sqle4.getMessage());
				result = "error-"+ sqle4.getMessage();
			}
		}
		return result;
	}
	@Override
	public List<CashierDailyAccListDTO> getCashierDailyListTu(long user_id) throws ServiceException {
		String sql = "select c.com_name,b.id,b.batch_name,cs.amount1,cs.amount2,d.data_name as charging_way,"
				+ " w.amount as way_amount,d.id as charging_way_id from charging_summary_group cs,"
				+ " charging_way_group w left join data_dictionary d on w.charging_way = d.id,batch b,company_info c "
				+ " where cs.batch_id = b.id and b.company_id = c.id and w.summary_id = cs.id and "
				+ " cs.charging_status = 'Y' and cs.daily_status = '0' and cs.receiv_status = '1' and cs.is_active = 'Y' "
				+ " and cs.cashier = "+user_id +" order by b.id";
		List<CashierDailyAccListDTO> list = this.jqm.getList(sql, CashierDailyAccListDTO.class);
		return list;
	}
	@Override
	public PageReturnDTO getCashierDailyAcc(String daily_acc_date1, String daily_acc_date2, long user_id, int rows,
			int page) throws ServiceException {
		String sql = "select c.daily_acc_num,c.daily_acc_amount,c.daily_acc_date,c.userId,u.chi_name user_name "
				+ ",(select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) from cashier_daily_acc_invoice i where i.daily_acc_num = c.daily_acc_num) invoice_amount"
				+ ",c.daily_status,way.charging_way,way.amount,d.data_name from cashier_daily_acc c left join user_usr u on u.id = c.userId"
				+ " ,v_daily_cashier_acc_way way left join data_dictionary d on d.id = way.charging_way "
				+ " where c.daily_acc_date >= '"+daily_acc_date1+" 00:00:00.000' and c.daily_acc_date < '"+daily_acc_date2+" 23:59:59.999' "
				+ " and c.is_Active = 'Y' and way.daily_acc_num = c.daily_acc_num and way.amount != 0 and c.userId = "+user_id +" order by c.daily_acc_date desc";
		
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows,CashierDailyAccDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public List<CashierDailyAccDTO> getCashierDailyAccPayway(String daily_acc_num) throws ServiceException {
		String sql = "select c.daily_acc_class_num,c.daily_acc_class,c.tran_code,c.daily_acc_class_amount,d.data_name,w.amonut as amount,w.charging_way "
				+ "from cashier_daily_acc_class c,cashier_daily_acc_payway w left join data_dictionary d on d.id = w.charging_way "
				+ "where c.daily_acc_class_num = w.daily_acc_class_num and c.daily_acc_num = '"+daily_acc_num+"'";
		List<CashierDailyAccDTO> list = this.jqm.getList(sql, CashierDailyAccDTO.class);
		return list;
	}
	@Override
	public List<CashierDailyAccInvoiceDTO> getCashierDailyAccInvoice(String daily_acc_num) throws ServiceException {
		String sql = "select c.invoice_num,c.charging_way_id,d.data_name as charging_way,c.invoice_status,"
				+ " case when c.invoice_status = 'N' then -c.invoice_amount else c.invoice_amount end invoice_amount,i.title_info"
				+ " from cashier_daily_acc_invoice c left join charging_invoice_single i on i.invoice_num = c.invoice_num and i.invoice_class = c.charging_way_id"
				+ " left join data_dictionary d on c.charging_way_id = d.id" 
				+ " where c.daily_acc_num = '"+daily_acc_num+"'";
		List<CashierDailyAccInvoiceDTO> list = this.jqm.getList(sql, CashierDailyAccInvoiceDTO.class);
		return list;
	}
	@Override
	public List<CashierDailyAccListDTO> getCashierDailyAccDetail(String daily_acc_num,String daily_acc_class) throws ServiceException {
		String sql = "select ci.user_name,e.exam_num,s.charging_status,s.cash_date,c.amount,s.req_num "
				+ "from cashier_daily_acc_list c,charging_summary_single s,exam_info e,customer_info ci "
				+ "where s.exam_id = e.id and e.customer_id = ci.id and c.summary_id = s.id "
				+ "and c.daily_acc_class_num = '"+daily_acc_num+"' order by s.cash_date";
		if("001".equals(daily_acc_class)){
			
		}else if("002".equals(daily_acc_class)){
			sql = "select ci.com_name,b.batch_name,s.charging_status,s.cash_date,s.amount2 as amount,s.account_num as req_num "
				+ "from cashier_daily_acc_list c,charging_summary_group s,batch b,company_info ci "
				+ "where c.summary_id = s.id and s.batch_id = b.id and b.company_id = ci.id "
				+ "and c.daily_acc_class_num = '"+daily_acc_num+"' order by s.cash_date";
		}else if("003".equals(daily_acc_class)){
			sql = "select * from(select 'Y' as charging_status,s.sale_time as cash_date,c.amount,s.sale_trade_num as req_num,"
				+ "'' as exam_num from cashier_daily_acc_list c,card_sale_summary s where c.team_acc_num = s.sale_trade_num and "
				+ "c.daily_acc_class_num = '"+daily_acc_num+"' "
				+ "union all "
				+ "select s.deal_type as charging_status,cs.cash_date,c.amount,cs.req_num,s.card_num as exam_num from "
				+ "cashier_daily_acc_list c,card_deal s,charging_summary_single cs where c.team_acc_num = s.id and "
				+ "cs.id = s.summary_id and c.daily_acc_class_num = '"+daily_acc_num+"') a order by a.cash_date";
		}else if("004".equals(daily_acc_class)){
			sql = "select d.trancode as charging_status,d.jndatetime as cash_date,c.amount,c.team_acc_num as req_num,ci.com_name from cashier_daily_acc_list c,"
				+ "company_account_detail d,company_info ci where c.team_acc_num = d.jnnumber and d.com_num = ci.com_Num "
				+ "and c.daily_acc_class_num = '"+daily_acc_num+"' order by d.jndatetime";
		}
		List<CashierDailyAccListDTO> list = this.jqm.getList(sql, CashierDailyAccListDTO.class);
		return list;
	}
	@Override
	public List<UserDTO> getCashierDailyAllAmountJob() throws ServiceException {
		String sql = "select distinct a.cashier as userid from ("
				+ "select c.cashier from charging_summary_single c where c.daily_status = 0 and c.is_active <> 'C' "
				+ "union all "
				+ "select cs.cashier from charging_summary_group cs where cs.charging_status = 'Y' "
				+ "and cs.daily_status = '0' and cs.receiv_status = '1' and cs.is_active = 'Y' "
				+ "union all "
				+ "select c.sale_user from card_sale_summary c where c.sale_status = 1 and c.daily_status = 0 "
				+ "union all "
				+ "select c.creater from company_account_detail c where c.trancode in ('001','003') and c.daily_status = 0 ) a";
		List<UserDTO> list = this.jqm.getList(sql, UserDTO.class);
		return list;
	}
	@Override
	public List<CashierDailyAccPaywayDTO> getCashierDailyPaywayAmountJob() throws ServiceException {
		String sql = "select sum(case when a.charging_status = 'Y' then a.amount else -a.amount end) amonut,a.creater id,convert(int,a.charging_way) charging_way from ("
				+ "select w.amount,w.charging_way,w.creater,c.charging_status from charging_summary_single c,charging_way_single w "
				+ "where c.daily_status = '0' and c.is_active <> 'C' and w.summary_id = c.id "
				+ "union all "
				+ "select w.amount,w.charging_way,w.creater,c.charging_status from charging_summary_group c,charging_way_group w "
				+ "where c.daily_status = '0' and w.summary_id = c.id and c.receiv_status = '1' and c.is_active = 'Y' "
				+ ") a group by a.creater,a.charging_way";
		List<CashierDailyAccPaywayDTO> list = this.jqm.getList(sql, CashierDailyAccPaywayDTO.class);
		return list;
	}
	@Override
	public List<PosDailySummaryDTO> getPosDailySummary(CashierDailyAccModel model,UserDTO user) throws ServiceException {
		String  sql = " select ptl.pos_code, ptl.trans_code,pd.pay_way, dd.data_name, sum(pd.amount) as pos_charge_amount from pos_detail  pd  , pos_trans_list ptl  , data_dictionary dd "
     			  	+" where pd.peis_trade_code = ptl.peis_trade_code  and dd.id = pd.pay_way and pd.trans_code = ptl.trans_code and pd.amount = ptl.trade_amount "
     			  	+" and ptl.voucher_no = pd.voucher_no and ptl.trade_no = pd.trade_no and pd.daily_status = '0' and pd.trans_code = ptl.trans_code  and ptl.UserId =  '"+user.getUserid()+"'  and  ptl.pos_code ='"+model.getPos_code()+"'"
				  	+" group by ptl.pos_code, ptl.trans_code  ,pd.pay_way,  dd.data_name";
		List<PosDailySummaryDTO> list = this.jqm.getList(sql, PosDailySummaryDTO.class); 
		return list;
	}
	
	@Override
	public PageReturnDTO getposDailyDetailList(CashierDailyAccModel model, UserDTO user,int rows,int page) throws ServiceException {
		boolean flag = false;
		String sql = " select distinct  ptl.peis_trade_code,ptl.trans_code,pd.amount as pos_charge_amount,CONVERT(varchar(20),ptl.create_time,20) as create_time,ptl.trade_no,ptl.voucher_no ,u.chi_name   as creater  ,ptl.pay_class"
					+"  from  pos_detail  pd ,pos_trans_list ptl "
				    +"  left join  user_usr u on u.id = ptl.UserId "
					+ " where pd.peis_trade_code = ptl.peis_trade_code "
					+"  and ptl.voucher_no = pd.voucher_no and ptl.trade_no = pd.trade_no and  pd.daily_status = '0' and pd.trans_code = ptl.trans_code and  pd.amount = ptl.trade_amount "
					+ " and ptl.trans_code = pd.trans_code and ptl.UserId = "+user.getUserid();
		         if (model != null && !"".equals(model.getPay_way()) && model.getPay_way() != null) {
						sql += " and pd.pay_way = '"+model.getPay_way()+"'";
						flag= true;
					   }
		         if (model != null && !"".equals(model.getTrans_code()) && model.getTrans_code() != null) {
						sql += " and ptl.trans_code = '"+model.getTrans_code()+"'";
						flag= true;
					   }
		         if (model != null && !"".equals(model.getPos_code()) && model.getPos_code() != null) {
						sql += " and pos_code =  '"+model.getPos_code()+"'";
						flag= true;
					   }
		         PageReturnDTO webrole = new PageReturnDTO();
		         if (flag) {
		        	webrole.setPage(rows);
			 		webrole.setRp(page);
			 		PageSupport map = this.jqm.getList(sql, page, rows,PosDailySummaryDTO.class);
			 		if ((map != null) && (map.getList() != null)) {
			 			webrole.setTotal(map.getRecTotal());
			 			webrole.setRows(map.getList());
			 		}    
				}else{
					webrole = null;
				}
		         
		return webrole;
	}
	
	@Override
	public CollectFeesResult savePosDailyInfo(CashierDailyAccModel model, UserDTO user) throws ServiceException {
		CollectFeesResult collectFeesResult = new CollectFeesResult();
		double amount = 0;
		 try {
			 String sql =  " select  pd.peis_trade_code ,pd.amount as pos_charge_amount,ptl.trans_code "
						+"  from  pos_detail  pd ,pos_trans_list ptl where pd.peis_trade_code = ptl.peis_trade_code "
						+"  and pd.daily_status = '0' and pd.trans_code = ptl.trans_code and ptl.pos_code='"+model.getPos_code()+"' and ptl.UserId = "+user.getUserid();
			 List<PosDailySummaryDTO> list = this.jqm.getList(sql, PosDailySummaryDTO.class);
			
				
			 if (list.size() > 0) {
				 for (PosDailySummaryDTO posDetail : list) {
					 if("00".equals(posDetail.getTrans_code())){
						 amount += posDetail.getPos_charge_amount();
					 }else if("01".equals(posDetail.getTrans_code()) || "02".equals(posDetail.getTrans_code())){
						 amount -= posDetail.getPos_charge_amount();
					 }
					 
				}
			 }
			 
			 Connection connection = null;
			 PreparedStatement ps = null;
			 ResultSet rs = null;
			 long id = 0;
			 try {
				  connection = this.jqm.getConnection();
				 String insql ="insert  into pos_daily_summary(amount,creater,creater_time,trade_count)  values('"+amount+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"','"+list.size()+"')";
				  PreparedStatement prepareStatement = connection.prepareStatement(insql,Statement.RETURN_GENERATED_KEYS);
				  prepareStatement.executeUpdate();
				  rs = prepareStatement.getGeneratedKeys();
				  if (rs.next()) {
					 id  = rs.getLong(1);
				  }
				  rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					if (connection != null) {
						connection.close();
					}
					if (ps != null) {
						ps.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			 
			 if (list.size() > 0) {
				 for (PosDailySummaryDTO posDetail : list) {
					String sq = "update  pos_detail set daily_status = '1' ,pos_daily_summary_id = '"+id+"' , daily_time = '"+DateTimeUtil.getDateTime()+"' where peis_trade_code = '"+posDetail.getPeis_trade_code()+"'  and daily_status = '0'";
				    this.jpm.execSql(sq);
				 }
			}
			 
//			WebSocketPosSendDTO	dto = new WebSocketPosSendDTO();
//		 	dto.setInter_class("01");//接口类型(01:银联POS通;02:市医保;03:省医保)
//			dto.setTrade_class("01");  //交易类型(00:银行卡;01:POS通)
//			dto.setTrade_op_code("06"); //POS交易操作类型(05:签到)
//			dto.setPay_class("02"); //支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
//			dto.setPeis_trade_code(""); //体检交易流水号
//			dto.setOperator_id(user.getUserid()); //操作员ID
//			dto.setOperator_code(user.getUserid()+"");
//			dto.setAmount(model.getDaily_acc_amount()); //金额
//			dto.setTrade_date(DateTimeUtil.shortFmt4(new Date()));  //日期  yyyymmdd格式
//			 
//			 collectFeesResult.setFlag("ok");
//			 collectFeesResult.setInfo("日结成功。。");
//			 collectFeesResult.setWebSocketPosSendDTO(dto);
			 
		} catch (Exception e) {
		
			collectFeesResult.setFlag("error");
			collectFeesResult.setInfo("操作错误，日结失败。。");
			
		}
		return collectFeesResult;
	}
	
	
	@Override
	public PageReturnDTO getEndPosDailyDetailList(CashierDailyAccModel model, UserDTO user, int rows, int page)
			throws ServiceException {
		String sql = " select   ptl.peis_trade_code,ptl.trans_code,ptl.trade_amount,CONVERT(varchar(20),ptl.create_time,20) as create_time,ptl.trade_no,ptl.voucher_no ,dd.data_name , ptl.pay_class ,  u.chi_name as creater "
				     +" from pos_daily_summary ps , pos_detail pd  ,data_dictionary dd , pos_trans_list  ptl left join user_usr u on u.id = ptl.UserId "
					 +" where ps.id = pd.pos_daily_summary_id and pd.peis_trade_code = ptl.peis_trade_code  "
					 +" and  pd.daily_status = '1' and dd.id = pd.pay_way  and  ps.id = "+model.getId()+"  and   pd.trans_code = ptl.trans_code and  ptl.UserId  = "+user.getUserid()
//					 +"'  and pd.daily_time >= '"+model.getDaily_acc_date1()+" 00:00:00.000' and pd.daily_time <= '"+model.getDaily_acc_date2()+" 23:59:59.999'"
					 +" order by  pd.daily_time  , pd.pay_way ,ptl.trans_code";
		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(rows);
 		webrole.setRp(page);
 		PageSupport map = this.jqm.getList(sql, page, rows,PosDailySummaryDTO.class);
 		if ((map != null) && (map.getList() != null)) {
 			webrole.setTotal(map.getRecTotal());
 			webrole.setRows(map.getList());
 		}    
	         
	   return webrole;
		
	}
	@Override
	public List<PosDailySummaryDTO> getendposdailySummary(CashierDailyAccModel model, UserDTO user)
			throws ServiceException {
		String sql = "select   ps.id,ps.amount ,u.chi_name as creater ,CONVERT(varchar(20),ps.creater_time,20) as create_time   "
					+" , ps.trade_count as daily_count "
					+" from  pos_daily_summary  ps  ,user_usr u where u.id = ps.creater    "
					+"  and ps.creater =  "+user.getUserid();
		if(model.getDaily_acc_date1()!= null && !"".equals(model.getDaily_acc_date1())){
			sql += " and ps.creater_time >= '"+model.getDaily_acc_date1()+" 00:00:00.000'";
		}
		if(model.getDaily_acc_date2()!= null && !"".equals(model.getDaily_acc_date2())){
			sql += " and ps.creater_time <= '"+model.getDaily_acc_date2()+" 23:59:59.999'";
		}
		List<PosDailySummaryDTO> list = this.jqm.getList(sql, PosDailySummaryDTO.class);
		
		return list;
	}
	@Override
	public PageReturnDTO getPersionCityReconciliation(CashierDailyAccModel model, UserDTO user, int rows, int page)
			throws ServiceException {
		String  sql = " select distinct css.id, css.req_num ,css.charging_status,css.amount2 as amount, CONVERT(varchar(20), css.create_time ,20) as create_time, e.exam_num ,ci.user_name ,css.city_cycle_code  "
				     + " from charging_way_single   cws , data_dictionary dd , charging_summary_single css "
					 +" left  join exam_info e  on e.id = css.exam_id "
					 +"	left  join customer_info ci  on ci.id = e.customer_id "
					 +"	where    cws.summary_id = css.id and cws.charging_way = dd.id and  dd.data_code_children = '"+model.getPay_way()+"' "
					 +"	 and  css.daily_status = '0' and css.is_active = 'Y'  and css.charging_status = 'Y' ";
		if (model.getDaily_acc_date1() != null && !"".equals(model.getDaily_acc_date1())) {
			sql += " and  cws.create_time >= '"+model.getDaily_acc_date1()+" 00:00:00'";
		}
		if (model.getDaily_acc_date2() != null && !"".equals(model.getDaily_acc_date2())) {
			sql += " and  cws.create_time <= '"+model.getDaily_acc_date2()+" 23:59:59'";
		}

        System.out.println("收费查询sql "+sql);

		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(rows);
 		webrole.setRp(page);
 		PageSupport map = this.jqm.getList(sql, page, rows,ChargingSummarySingleDTO.class);
 		if ((map != null) && (map.getList() != null)) {
 			webrole.setTotal(map.getRecTotal());
 			webrole.setRows(map.getList());
 		}    
	         
	   return webrole;
	}
//	@Override
//	public PageReturnDTO getCardCityReconciliation(CashierDailyAccModel model, UserDTO user, int rows, int page)
//			throws ServiceException {
//		String status = "";
//		if("103".equals(model.getPay_way())){  // 市医保
//			status = " and  ios.city_sign_status = '1' and ios.city_cycle_code = css.city_cycle_code  and css.health_type = '02' ";
//		}else if("104".equals(model.getPay_way())){// 省医保
//			status = " and  ios.prov_sign_status = '1'  and ios.prov_cycle_code = css.prov_cycle_code  and css.health_type = '03' ";
//		}
//		
//		String sql = " select  css.sale_trade_num,css.sale_amount,CONVERT(varchar(20),css.sale_time,20) as sale_time  from   card_sale_way  csw , card_sale_summary  css ,  data_dictionary  dd ,insurance_op_signStatus  ios  "
//                    +" where  csw.sale_trade_num = css.sale_trade_num   and  dd.id = csw.charging_way"
//                    +"  and dd.data_code_children = '"+model.getPay_way()+"'  and   css.is_Active = 'Y'  and  css.sale_type  = '1'  and  css.daily_status = '0' and  css.sale_user = '"+user.getUserid()+"'"
//                    +" and ios.operator_id = csw.creater " + status;
//		if (model.getDaily_acc_date1() != null && !"".equals(model.getDaily_acc_date1())) {
//			sql += " and  css.sale_time >= '"+model.getDaily_acc_date1()+"'";
//		}
//		if (model.getDaily_acc_date2() != null && !"".equals(model.getDaily_acc_date2())) {
//			sql += " and  css.sale_time <= '"+model.getDaily_acc_date2()+"'";
//		}  
//		PageReturnDTO webrole = new PageReturnDTO();
// 		webrole.setPage(rows);
// 		webrole.setRp(page);
// 		PageSupport map = this.jqm.getList(sql, page, rows,CardSaleSummaryDTO.class);
// 		if ((map != null) && (map.getList() != null)) {
// 			webrole.setTotal(map.getRecTotal());
// 			webrole.setRows(map.getList());
// 		}    
//		return webrole;
//	}
	@Override
	public List<ChargingDetailSingleDTO> getPersionCityReconciliationDetailList(CashierDailyAccModel model,
			UserDTO user) throws ServiceException {
		String sql = " select  distinct ci.item_name, cds.amount1  as item_amount ,cds.amount,cds.discount ,  CONVERT(varchar(20),cds.create_time ,20) as create_time ,u.chi_name as user_name from   charging_detail_single   cds , charging_item  ci ,user_usr  u "
					+" where ci.id = cds.charging_item_id   and u.id = cds.creater and  cds.summary_id = '"+model.getId()+"'";
		List<ChargingDetailSingleDTO> list = this.jqm.getList(sql, ChargingDetailSingleDTO.class);
		return list;
	}
	

	@Override
	public String  getCityReconciliationQuery(CashierDailyAccModel model, UserDTO user)  throws SQLException {
		String sql = " select  sum(case when i.medical_type = '02' then -i.amount_medical else i.amount_medical end ) as amount from insure_account i  "
				   + " where  i.reconciliation_state = 'N' and  i.create_time >= '"+model.getDaily_acc_date1()+" 00:00:00.000' "
				   + " and  i.create_time <=  '"+model.getDaily_acc_date2()+" 23:59:59.000' ";

		String listSql = " select  (case when i.medical_type = '02' then -i.amount_medical else i.amount_medical end ) as amount_medical ,"
						+" (case when i.medical_type = '02' then i.sterilisation_req_num else i.medical_charge_req_num end ) as medical_charge_req_num"
						+" from  insure_account i where"
						+" i.reconciliation_state = 'N' and  i.create_time >= '"+model.getDaily_acc_date1()+" 00:00:00.000' "
						+" and   i.create_time <=  '"+model.getDaily_acc_date2()+" 23:59:59.000' ";
        Connection connection = null;
        Statement statement = null;
        double amount = 0;
        try {
            // 读取记录数
            connection = this.jqm.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                amount = rs.getDouble("amount");
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
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        List<InsureAccountCharge> list = this.jqm.getList(listSql, InsureAccountCharge.class);
        JSONObject s=new JSONObject();
        s.put("aka078","10");
        try {
            s.put("ake007",df.format(df1.parse(model.getDaily_acc_date1())));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        s.put("akb065",amount);
        //明细
        List lis = new ArrayList();

        if(list.size() > 0){
            for ( int i = 0;i<list.size() ;i++){
                JSONObject jymxlist=new JSONObject();
                jymxlist.put("aae072",list.get(i).getMedical_charge_req_num());
                jymxlist.put("akc227",list.get(i).getAmount_medical());
                lis.add(jymxlist.toString());
            }
        }
        System.out.println("                     jymxlist                  "+lis.toString());
        s.put("jymxlist",lis);
        return s.toString();
	}
	
	@Override
	public InsureAccountCharge getCityReconciliationQueryDetail(CashierDailyAccModel model, UserDTO user)
			throws ServiceException {
		String sql = "select  * from  insure_account where his_req_num = '"+model.getReq_num()+"'";
		List<InsureAccountCharge> list = this.jqm.getList(sql, InsureAccountCharge.class);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	@Override
	public PageReturnDTO getInsureVerifyAccountDetailList(CashierDailyAccModel model, UserDTO user, int rows, int page)
			throws ServiceException {
		String status = "";
		if ("104".equals(model.getPay_way())) {//省医保
			status = " and ios.prov_sign_status = '1' and iv.center_cycle_code = ios.prov_cycle_code ";
		}else { //市医保
			status = " and iv.center_cycle_code = ios.city_cycle_code and ios.city_sign_status = '1' ";
		}
		
		String sql = " select iv.rec_id , iv.trade_code,iv.center_cycle_code,iv.PatNo,iv.peis_req_code,iv.trade_name,iv.peis_trade_code,"
					+" iv.center_cycle_code,iv.total_amount,iv.account_pay,iv.cash_pay,iv.plan_pay,iv.bailout_fund,iv.person_fund_pay,iv.min_living_pay,iv.Enterprise_fund,iv.civil_worker_pay "  
					+" ,iv.disabled_pay,iv.acc_date,iv.soft_company,iv.pat_class,iv.medical_treatment,iv.solving_flag,iv.create_time "
					+" from insure_verify_account_detail  iv   , insurance_op_signStatus ios  where    ios.operator_id = "+user.getUserid()+ status+ " order by iv.create_time  asc";
		
		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(rows);
 		webrole.setRp(page);
 		PageSupport map = this.jqm.getList(sql, page, rows,InsureVerifyAccountDetailDTD.class);
 		if ((map != null) && (map.getList() != null)) {
 			webrole.setTotal(map.getRecTotal());
 			webrole.setRows(map.getList());
 		}    
		return webrole;
	}
	@Override
	public String checkCorrectTransaction(CashierDailyAccModel model, UserDTO user) throws ServiceException {
		String  persionSql = " select  cs.exam_id,cs.charging_status,cs.is_active from charging_summary_single cs ,insure_account  ia  where  ia.his_req_num = cs.req_num and  cs.is_active  = 'Y' and cs.charging_status='Y'  and ia.his_req_num = '"+model.getReq_num()+"'";
		List<ChargingSummarySingleDTO> persionList = this.jqm.getList(persionSql, ChargingSummarySingleDTO.class);
		
//		String  cardSql = "  select  cs.is_Active,cs.sale_trade_num,cs.sale_status from   card_sale_summary cs ,insure_account  ia where  ia.peis_req_code = cs.sale_trade_num and cs.is_Active = 'Y'  and  ia.peis_trade_code = '"+model.getReq_num()+"'";
//		List<CardSaleSummaryDTO> cardList = this.jqm.getList(cardSql, CardSaleSummaryDTO.class);
		
		if(persionList.size() > 0 ){
			return "error-此交易不能冲正！,请做退费操作。";
		}else{
			return "ok-检验成功可以冲正！";
		}
		
	}
	
	
	
	// 医保流水明细
	@Override
	public PageReturnDTO getInsureAccountList(CashierDailyAccModel model, UserDTO user, int rows, int page)
			throws ServiceException {
		//个人收费记录
		Set<String> set = new HashSet<>();
		boolean falg = false;
		if(!"T".equals(model.getType()) && !"C".equals(model.getType())){
			String pstatus = "";
			if("103".equals(model.getPay_way())){  // 市医保
				pstatus = "  and css.health_type = '02' ";
			}else if("104".equals(model.getPay_way())){// 省医保
				pstatus = " and css.health_type = '03' ";
			}
			
			String  psql = " select css.id, css.req_num ,css.charging_status,css.amount2 as amount, CONVERT(varchar(20), css.create_time ,20) as create_time, e.exam_num ,ci.user_name ,css.city_cycle_code  "
					     + " from charging_way_single   cws , data_dictionary dd ,charging_summary_single css " 
						 +" left  join exam_info e  on e.id = css.exam_id "
						 +"	left  join customer_info ci  on ci.id = e.customer_id "
						 +"	where    cws.summary_id = css.id and cws.charging_way = dd.id and  dd.data_code_children = '"+model.getPay_way()+"' "
						 +"	and  cws.creater = '"+user.getUserid()+"' and  css.daily_status = '0' and css.is_active = 'Y'  and css.charging_status = 'Y' ";
			if (model.getDaily_acc_date1() != null && !"".equals(model.getDaily_acc_date1())) {
				psql += " and  cws.create_time >= '"+model.getDaily_acc_date1()+"'";
			}
			if (model.getDaily_acc_date2() != null && !"".equals(model.getDaily_acc_date2())) {
				psql += " and  cws.create_time <= '"+model.getDaily_acc_date2()+"'";
			}  
			
			List<ChargingSummarySingleDTO> plist = this.jqm.getList(psql, ChargingSummarySingleDTO.class);
			
			falg = true;
		}
		
		String trade_code = " and ia.trade_code = '2410'";
		if("T".equals(model.getType())){ //退费
			trade_code = " and ia.trade_code = '2430' ";
		}else if("C".equals(model.getType())){//冲正
			trade_code = " and ia.trade_code = '2421' ";
		}
		
//		String status = "";
//		if ("104".equals(model.getPay_way())) {//省医保
//			status = " and ios.prov_sign_status = '1' and ia.center_cycle_code = ios.prov_cycle_code ";
//		}else { //市医保
//			status = " and ia.center_cycle_code = ios.city_cycle_code and ios.city_sign_status = '1' ";
//		}
		
		String sql = "  select  ia.center_cycle_code , ia.trade_code ,ia.operator_code,ia.insurance_class,ia.peis_req_code "
					+" ,ia.peis_trade_code,ia.center_trade_code,ia.total_amount,ia.account_pay,ia.cash_pay,ia.plan_pay,ia.create_time from  insure_account  ia , insurance_op_signStatus ios "
					+" where      ia.create_time >= '"+model.getDaily_acc_date1()+"' and ia.create_time <='"+model.getDaily_acc_date2()+"' " + trade_code
					+ "order by ia.create_time asc ";
		
		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(rows);
 		webrole.setRp(page);
 		PageSupport map = this.jqm.getList(sql, page, rows,InsureAccountDTO.class);
 		List<InsureAccountDTO> list = map.getList();
 		List<InsureAccountDTO> list1 =  new ArrayList<>();;
//		if ((map != null) && (list != null)) {
//				for (int i = 0 ; i < list.size() ; i++) {
//					InsureAccountDTO insureAccountDTD = list.get(i);
//						list1.add(insureAccountDTD);
//						String ins = " select  * from  insure_account ia where  ia.peis_req_code = '"+insureAccountDTD.getPeis_req_code()+"'";
//						List<InsureAccountDTO> inlist = this.jqm.getList(ins, InsureAccountDTO.class);
//						if(inlist != null && inlist.size() > 0){
//							for (InsureAccountDTO insureAccountDTD2 : inlist) {
//								if(falg && !"2410".equals(insureAccountDTD2.getTrade_code())){
//									list.remove(i);
//									i--;
//								}
//								if("T".equals(model.getType()) && !"2430".equals(insureAccountDTD2.getTrade_code())){
//									list1.add(insureAccountDTD2);
//								}
//								
//								if("C".equals(model.getType()) &&  !"2421".equals(insureAccountDTD2.getTrade_code())){
//									list1.add(insureAccountDTD2);
//								}
//							}
//						}
//						
//						if(falg){//收费tab
//							if(!set.contains(insureAccountDTD.getPeis_req_code())){
//								insureAccountDTD.setType("1");//不包含标记为1 可以冲正
//							}
//						}
//				}
//			
//		
// 			
// 			if(falg){
// 				webrole.setRows(list);
// 				webrole.setTotal(list.size());
// 			}else{
// 				webrole.setRows(list1);
// 				webrole.setTotal(list1.size());
// 			}
// 			
// 		}    
		return webrole;
	}
	
	
	@Override
	public PageReturnDTO getPersionRefundlist(CashierDailyAccModel model, UserDTO user, int rows, int page)
			throws ServiceException {
		//个人收费记录
		String  psql = " select css.id, css.old_req_num as req_num  ,css.charging_status,css.amount2 as amount, CONVERT(varchar(20), css.create_time ,20) as create_time, e.exam_num ,ci.user_name ,css.city_cycle_code  "
				     + " from charging_way_single   cws , data_dictionary dd , charging_summary_single css "
					 +" left  join exam_info e  on e.id = css.exam_id "
					 +"	left  join customer_info ci  on ci.id = e.customer_id "
					 +"	where    cws.summary_id = css.id and cws.charging_way = dd.id and  dd.data_code_children = '"+model.getPay_way()+"' "
					 +"	 and  css.daily_status = '0' and css.is_active = 'N'  and css.charging_status = 'M' ";
		if (model.getDaily_acc_date1() != null && !"".equals(model.getDaily_acc_date1())) {
			psql += " and  cws.create_time >= '"+model.getDaily_acc_date1()+" 00:00:00'";
		}
		if (model.getDaily_acc_date2() != null && !"".equals(model.getDaily_acc_date2())) {
			psql += " and  cws.create_time <= '"+model.getDaily_acc_date2()+" 23:59:59'";
		}  
		psql += " order by  css.create_time";
        System.out.println("退费 sql "+psql);
		PageReturnDTO webrole = new PageReturnDTO();
 		webrole.setPage(rows);
 		webrole.setRp(page);
 		PageSupport map = this.jqm.getList(psql, page, rows,ChargingSummarySingleDTO.class);
 		if ((map != null) && (map.getList() != null)) {
 			webrole.setTotal(map.getRecTotal());
 			webrole.setRows(map.getList());
 		}   
 		
		return webrole;
	} 
	@Override
	public CashierDailyAccDTO getCityReconciliationSummaryAmount(CashierDailyAccModel model, UserDTO user) throws ServiceException {
		String status = "";
		String insure_account="";//流水表
		if("103".equals(model.getPay_way())){  // 市医保
			status = " and  ios.city_sign_status = '1' and ios.city_cycle_code = css.city_cycle_code  and css.health_type = '02' ";
			insure_account = " and ios.city_cycle_code = ia.center_cycle_code and ios.city_sign_status = '1' ";
		}else if("104".equals(model.getPay_way())){// 省医保
			status = " and  ios.prov_sign_status = '1' and ios.prov_cycle_code = css.prov_cycle_code  and css.health_type = '03' ";
			insure_account = " and  ios.prov_cycle_code = ia.center_cycle_code and ios.prov_sign_status = '1' ";
		}
		
		Connection connection = null;
		Statement statement = null;
		CashierDailyAccDTO  cashierDailyAccDTO = new CashierDailyAccDTO(); 
		try {
			//个人 收费统计
			String  sql = " select sum(cws.amount) as summ  ,count(*) as cou "
				     + " from charging_way_single   cws , data_dictionary dd ,insurance_op_signStatus  ios ,charging_summary_single css " 
					 +" left  join exam_info e  on e.id = css.exam_id "
					 +"	left  join customer_info ci  on ci.id = e.customer_id "
					 +"	where    cws.summary_id = css.id and cws.charging_way = dd.id and  dd.data_code_children = '"+model.getPay_way()+"' "
					 +"	and  cws.creater = '"+user.getUserid()+"' and  css.daily_status = '0' and css.is_active = 'Y'  and css.charging_status = 'Y' "
                     +" and  cws.create_time >= '"+model.getDaily_acc_date1()+"'"
					 +" and  cws.create_time <= '"+model.getDaily_acc_date2()+"'"
					 +" and ios.operator_id = cws.creater "+status;

			connection = this.jqm.getConnection();
			statement = connection.createStatement();
			ResultSet rs1 = statement.executeQuery(sql);
			if (rs1.next()) {
				double summ = rs1.getDouble("summ");
				 int  count = rs1.getInt("cou");
				 cashierDailyAccDTO.setAmount(summ);
				 cashierDailyAccDTO.setPersion_count(count);
			}
			//医保收费流程统计
			String  sql3  = " select  sum(ia.account_pay) as sun , COUNT(*) as cou ,ia.trade_code  from  insure_account  ia  , insurance_op_signStatus ios  "
					      + " where   ia.operator_code = ios.op_work_num  "+insure_account
					      + " and  ios.operator_id = "+user.getUserid()+" and   ia.create_time >='"+model.getDaily_acc_date1()+"'  and ia.create_time <= '"+model.getDaily_acc_date2()+"'"
					      + " group by trade_code";
			ResultSet st = statement.executeQuery(sql3);
			while (st.next()) {
				double amount = st.getDouble("sun");
				int count =st.getInt("cou");
				String trade_code = st.getString("trade_code");
				if("2410".equals(trade_code)){//结算
					cashierDailyAccDTO.setSettlement_amount(amount);
					cashierDailyAccDTO.setSettlement_count(count);
				}else if("2430".equals(trade_code)){// 撤销收费
					cashierDailyAccDTO.setRevoke_amount(amount);
					cashierDailyAccDTO.setRevoke_count(count);
				}else if("2421".equals(trade_code)){// 冲正交易
					cashierDailyAccDTO.setCorrect_amount(amount);
					cashierDailyAccDTO.setCorrect_count(count);
				}
			}
					
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
			if(statement!=null){
				statement.close();
			}
			if(connection!=null){
				connection.close();
			}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return cashierDailyAccDTO;
	}

	@Override
	public InsureAccountCharge getCityReconciliationSummaryAmountCharge(CashierDailyAccModel model, UserDTO user) throws ServiceException {
		String sql = " select  sum( CASE  WHEN i.medical_type ='01' THEN i.amount_medical ELSE -i.amount_medical END) as amount_medical, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.personal_cash ELSE -i.personal_cash END) as personal_cash, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.individual_account ELSE -i.individual_account END) as individual_account, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.fund ELSE -i.fund END) as fund," +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.whole_fund ELSE -i.whole_fund END) as whole_fund, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.commercial_fund ELSE -i.commercial_fund END) as commercial_fund, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.civil_servants ELSE -i.civil_servants END) as civil_servants, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.poverty_alleviation ELSE -i.poverty_alleviation END) as poverty_alleviation, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.bailout_fund ELSE -i.bailout_fund END) as bailout_fund, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.other_fund ELSE -i.other_fund END) as other_fund, " +
					" sum( CASE  WHEN i.medical_type ='01' THEN i.enterprise_complement ELSE -i.enterprise_complement END) as enterprise_complement " +
					" from  insure_account  i where 1=1 ";
				if(!StringUtils.isEmpty(model.getDaily_acc_date1())){
					sql += " and i.create_time >='"+model.getDaily_acc_date1()+" 00:00:00'";
				}
				if(!StringUtils.isEmpty(model.getDaily_acc_date2())){
					sql += " and i.create_time <='"+model.getDaily_acc_date2()+" 23:59:59'";
				}
		List<InsureAccountCharge> list = this.jqm.getList(sql, InsureAccountCharge.class);
		InsureAccountCharge insureAccountCharge = new InsureAccountCharge();
		if(list.size() > 0){
			insureAccountCharge = list.get(0);
		}
		return insureAccountCharge;
	}
}
