package com.hjw.wst.service.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CashierDailyAccDTO;
import com.hjw.wst.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.wst.DTO.CashierDailyAccListDTO;
import com.hjw.wst.DTO.CashierDailyAccPaywayDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.PosDailySummaryDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebSocketPosSendDTO;
import com.hjw.wst.model.CashierDailyAccModel;
import com.hjw.wst.service.CashierDailyAccService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CashierDailyAccServiceImpl implements CashierDailyAccService{

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
			CallableStatement c = con.prepareCall("{call pro_cashier_daily(?,?,?,?)}");
			// 注册存储过程的第一个参数 用户ID
			c.setLong(1, user.getUserid());
			// 注册存储过程的第二个参数 体检中心编码
			c.setString(2, user.getCenter_num());
			// 注册存储过程的第三个参数 执行结束 0成功，1失败
			c.registerOutParameter(3, java.sql.Types.INTEGER);
			// 注册存储过程的第四个参数 失败原因
			c.registerOutParameter(4, java.sql.Types.VARCHAR);
			// 执行存储过程
			c.executeUpdate();
			// 得到存储过程的输出参数值
			if(c.getInt(3) == 0){
				result = "ok-"+c.getString(4);
			}else{
				result = "error-"+c.getString(4);
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
	public List<CashierDailyAccListDTO> getCashierDailyListTu(long user_id,String center_num) throws ServiceException {
		String sql = "select c.com_name,b.id,b.batch_name,cs.amount1,cs.amount2,d.data_name as charging_way,"
				+ " w.amount as way_amount,d.id as charging_way_id from charging_summary_group cs,"
				+ " charging_way_group w left join data_dictionary d on w.charging_way = d.id,batch b,company_info c "
				+ " where cs.batch_id = b.id and b.company_id = c.id and w.summary_id = cs.id and "
				+ " cs.charging_status = 'Y' and cs.daily_status = '0' and cs.receiv_status = '1' and cs.is_active = 'Y' and b.center_num='"+center_num+"' "
				+ " and cs.cashier = "+user_id +" order by b.id";
		List<CashierDailyAccListDTO> list = this.jqm.getList(sql, CashierDailyAccListDTO.class);
		return list;
	}
	@Override
	public PageReturnDTO getCashierDailyAcc(String daily_acc_date1, String daily_acc_date2, long user_id,String center_num, int rows,
			int page) throws ServiceException {
		String sql = "select c.daily_acc_num,c.daily_acc_amount,c.daily_acc_date,c.userId,u.chi_name user_name "
				+ ",(select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) from cashier_daily_acc_invoice i where i.daily_acc_num = c.daily_acc_num) invoice_amount"
				+ ",c.daily_status,way.charging_way,way.amount,d.data_name from cashier_daily_acc c left join user_usr u on u.id = c.userId"
				+ " ,v_daily_cashier_acc_way way left join data_dictionary d on d.id = way.charging_way "
				+ " where c.daily_acc_date >= '"+daily_acc_date1+" 00:00:00.000' and c.daily_acc_date < '"+daily_acc_date2+" 23:59:59.999' "
				+ " and c.is_Active = 'Y' and way.daily_acc_num = c.daily_acc_num and way.amount != 0 and c.userId = "+user_id +" and c.center_num='"+center_num+"' order by c.daily_acc_date desc";
		
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
	public List<CashierDailyAccListDTO> getCashierDailyAccDetail(String daily_acc_num,String daily_acc_class,String center_num) throws ServiceException {
		String sql = "select ci.user_name,e.exam_num,s.charging_status,s.cash_date,c.amount,s.req_num "
				+ "from cashier_daily_acc_list c,charging_summary_single s,exam_info e,customer_info ci "
				+ "where s.exam_id = e.id and e.customer_id = ci.id and c.summary_id = s.id "
				+ "and c.daily_acc_class_num = '"+daily_acc_num+"' order by s.cash_date";
		if("001".equals(daily_acc_class)){
			
		}else if("002".equals(daily_acc_class)){
			sql = "select ci.com_name,b.batch_name,s.charging_status,s.cash_date,s.amount2 as amount,s.account_num as req_num "
				+ "from cashier_daily_acc_list c,charging_summary_group s,batch b,company_info ci "
				+ "where c.summary_id = s.id and s.batch_id = b.id and b.company_id = ci.id "
				+ "and c.daily_acc_class_num = '"+daily_acc_num+"' and b.center_num='"+center_num+"' order by s.cash_date";
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
		String sql = "select distinct a.cashier as userid,a.center_num from ("
				+ "select c.cashier,c.center_num from charging_summary_single c where c.daily_status = 0 "
				+ "union all "
				+ "select cs.cashier,cs.center_num from charging_summary_group cs where cs.charging_status = 'Y' "
				+ "and cs.daily_status = '0' and cs.receiv_status = '1' and cs.is_active = 'Y' "
				+ "union all "
				+ "select c.sale_user,c.center_num from card_sale_summary c where c.sale_status = 1 and c.daily_status = 0 "
				+ "union all "
				+ "select c.creater,c.center_num from company_account_detail c where c.trancode in ('001','003') and c.daily_status = 0 ) a";
		List<UserDTO> list = this.jqm.getList(sql, UserDTO.class);
		return list;
	}
	@Override
	public List<CashierDailyAccPaywayDTO> getCashierDailyPaywayAmountJob() throws ServiceException {
		String sql = "select sum(case when a.charging_status = 'Y' then a.amount else -a.amount end) amonut,a.creater id,convert(int,a.charging_way) charging_way from ("
				+ "select w.amount,w.charging_way,w.creater,c.charging_status from charging_summary_single c,charging_way_single w "
				+ "where c.daily_status = '0' and w.summary_id = c.id "
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
     			  	+" where pd.peis_trade_code = ptl.peis_trade_code  and dd.id = pd.pay_way  and pd.daily_status = '0' and ptl.UserId =  '"+user.getUserid()+"'  and  ptl.pos_code ='"+model.getPos_code()+"'"
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
					+"  and pd.daily_status = '0' and ptl.UserId = "+user.getUserid();
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
						+"  and pd.daily_status = '0' and ptl.pos_code='"+model.getPos_code()+"' and ptl.UserId = "+user.getUserid();
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
				 String insql ="insert  into pos_daily_summary(amount,creater,creater_time)  values('"+amount+"','"+user.getUserid()+"','"+DateTimeUtil.getDate3()+"')";
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
			 
			WebSocketPosSendDTO	dto = new WebSocketPosSendDTO();
		 	dto.setInter_class("01");//接口类型(01:银联POS通;02:市医保;03:省医保)
			dto.setTrade_class("01");  //交易类型(00:银行卡;01:POS通)
			dto.setTrade_op_code("06"); //POS交易操作类型(05:签到)
			dto.setPay_class("02"); //支付种类(01:个检缴费;02:卡充值;03:团体预付费;04:团体缴费)
			dto.setPeis_trade_code(""); //体检交易流水号
			dto.setOperator_id(user.getUserid()); //操作员ID
			dto.setOperator_code(user.getUserid()+"");
			dto.setAmount(model.getDaily_acc_amount()); //金额
			dto.setTrade_date(DateTimeUtil.shortFmt4(new Date()));  //日期  yyyymmdd格式
			 
			 collectFeesResult.setFlag("ok");
			 collectFeesResult.setInfo("日结成功。。");
			 collectFeesResult.setWebSocketPosSendDTO(dto);
			 
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
				     +" from  pos_detail pd  ,data_dictionary dd , pos_trans_list  ptl left join user_usr u on u.id = ptl.UserId "
					 +" where  pd.peis_trade_code = ptl.peis_trade_code  "
					 +" and  pd.daily_status = '1' and dd.id = pd.pay_way  and  ptl.UserId  = '"+user.getUserid()+"'  and pd.daily_time >= '"+model.getDaily_acc_date1()+" 00:00:00.000' and pd.daily_time <= '"+model.getDaily_acc_date2()+" 23:59:59.999'"
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
}
