package com.hjw.charge.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.charge.DTO.CashierDailyAccDTO;
import com.hjw.charge.DTO.CashierDailyAccInvoiceDTO;
import com.hjw.charge.DTO.CashierDailyAccListDTO;
import com.hjw.charge.DTO.FinanceDepAccDTO;
import com.hjw.charge.DTO.FinanceDepAccPaywayDTO;
import com.hjw.charge.DTO.FinanceVsCashierDailyAccDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.charge.domain.CashierDailyAcc;
import com.hjw.charge.domain.FinanceDepAcc;
import com.hjw.charge.domain.FinanceDepAccPayway;
import com.hjw.charge.domain.FinanceVsCashierDailyAcc;
import com.hjw.charge.model.FinanceDepAccModel;
import com.hjw.charge.service.FinanceDepAccService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class FinanceDepAccServiceImpl implements FinanceDepAccService{
	
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
	public List<CashierDailyAccDTO> getCashierDailyAccAll(UserDTO user) throws ServiceException {
		String sql = "select c.daily_acc_num,c.daily_acc_date,c.daily_acc_amount,c.userId,u.chi_name user_name,"
				+ "(select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) from cashier_daily_acc_invoice i where i.daily_acc_num = c.daily_acc_num) invoice_amount,"
				+ "w.charging_way,d.data_name,w.amonut as amount from cashier_daily_acc c left join user_usr u "
				+ "on c.userId = u.id,cashier_daily_acc_payway w left join data_dictionary d on w.charging_way = d.id "
				+ "where c.daily_acc_num = w.daily_acc_num and c.daily_status = '0' and c.center_num = '"+user.getCenter_num()+"' order by daily_acc_date";
		List<CashierDailyAccDTO> list = this.jqm.getList(sql, CashierDailyAccDTO.class);
		return list;
	}
	@Override
	public List<FinanceDepAccDTO> getCashierDailyAccSum(UserDTO user) throws ServiceException {
		String sql = "select (select sum(daily_acc_amount) from cashier_daily_acc c where c.daily_status = '0' and c.center_num='"+user.getCenter_num()+"') fd_acc_amount, sum(w.amonut) as way_amount,"
				+ "(select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) from cashier_daily_acc c,"
				+ "cashier_daily_acc_invoice i where c.daily_status = '0' and c.daily_acc_num = i.daily_acc_num and c.center_num='"+user.getCenter_num()+"') invoice_amount,"
				+ "w.charging_way,d.data_name from cashier_daily_acc c,cashier_daily_acc_payway w "
				+ "left join data_dictionary d on w.charging_way = d.id where c.daily_acc_num = w.daily_acc_num "
				+ "and c.daily_status = '0' and c.center_num='"+user.getCenter_num()+"' group by w.charging_way,d.data_name,d.seq_code order by d.seq_code";
		List<FinanceDepAccDTO> list = this.jqm.getList(sql, FinanceDepAccDTO.class);
		return list;
	}
	@Override
	public void saveFinanceDepAcc(FinanceDepAccModel model, UserDTO user) throws ServiceException{
		
		//保存财务部门日结总表
		FinanceDepAcc financeDepAcc = new FinanceDepAcc();
		financeDepAcc.setFd_acc_num(GetNumContral.getInstance().getParamNum("fd_acc_num", user.getCenter_num()));
		financeDepAcc.setFd_acc_amount(model.getFd_acc_amount());
		financeDepAcc.setFd_acc_date(DateTimeUtil.parse());
		financeDepAcc.setUserId(user.getUserid());
		financeDepAcc.setIs_active("Y");
		financeDepAcc.setCenter_num(user.getCenter_num());
		this.pm.save(financeDepAcc);
		
		//保存财务部门日结收费方式表
		List<FinanceDepAccPaywayDTO> paywayList = model.getFinancePayway();
		for (FinanceDepAccPaywayDTO financeDepAccPaywayDTO : paywayList) {
			FinanceDepAccPayway financeDepAccPayway = new FinanceDepAccPayway();
			financeDepAccPayway.setFd_acc_num(financeDepAcc.getFd_acc_num());
			financeDepAccPayway.setCharging_way_id(financeDepAccPaywayDTO.getCharging_way_id());
			financeDepAccPayway.setAmount(financeDepAccPaywayDTO.getAmount());
			
			this.pm.save(financeDepAccPayway);
		}
		//保存财务部门收费员收费明细表
		List<FinanceVsCashierDailyAccDTO> cashierDailyList = model.getFinancecCashier();
		for (FinanceVsCashierDailyAccDTO financeVsCashierDailyAccDTO : cashierDailyList) {
			List<CashierDailyAcc> list = this.qm.find("from CashierDailyAcc c where c.daily_acc_num = '"+financeVsCashierDailyAccDTO.getDaily_acc_num()+"'");
			if(list.size() != 0){
				CashierDailyAcc cashierDailyAcc = list.get(0);
				cashierDailyAcc.setDaily_status("1");
				this.pm.update(cashierDailyAcc);
			}
			FinanceVsCashierDailyAcc financeVsCashierDailyAcc = new FinanceVsCashierDailyAcc();
			financeVsCashierDailyAcc.setFd_acc_num(financeDepAcc.getFd_acc_num());
			financeVsCashierDailyAcc.setDaily_acc_num(financeVsCashierDailyAccDTO.getDaily_acc_num());
			financeVsCashierDailyAcc.setIs_active("Y");
			
			this.pm.save(financeVsCashierDailyAcc);
		}
	}
	@Override
	public PageReturnDTO getFinanceDepAccList(FinanceDepAccModel model,String center_num, int rows, int page) throws ServiceException{
		String sql = "select f.fd_acc_num,f.fd_acc_date,f.fd_acc_amount,f.userId,u.chi_name user_name"
				+ ",(select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) "
				+ " from Finance_vs_cashier_daily_acc fc,cashier_daily_acc_invoice i where i.daily_acc_num = fc.daily_acc_num "
				+ " and fc.fd_acc_num = f.fd_acc_num) invoice_amount"
				+ " from Finance_Dep_acc f "
				+ " left join user_usr u on u.id = f.userId where 1=1 and f.center_num='"+center_num+"' ";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sql += " and f.fd_acc_date >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sql += " and f.fd_acc_date < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		sql += " order by f.fd_acc_date desc";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows,FinanceDepAccDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
		
	}
	
	@Override
	public List<FinanceDepAccPaywayDTO> getFinanceDepAccPaywayList(String fd_acc_num) throws ServiceException{
		String sql = "select w.amount,w.charging_way_id,d.data_name charging_way  from Finance_Dep_acc_payway w "
				+ "left join data_dictionary d on w.charging_way_id = d.id where w.fd_acc_num = '"+fd_acc_num+"' order by d.seq_code";
		List<FinanceDepAccPaywayDTO> list = this.jqm.getList(sql, FinanceDepAccPaywayDTO.class);
		return list;
	}
	@Override
	public List<FinanceVsCashierDailyAccDTO> getFinanceVsCashierDailyAccList(String fd_acc_num) throws ServiceException{
		String sql = "select c.daily_acc_amount,c.daily_acc_date,u.chi_name user_name,c.daily_acc_num"
				+ " ,(select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) from "
				+ " cashier_daily_acc_invoice i where i.daily_acc_num = c.daily_acc_num) invoice_amount"
				+ " from Finance_vs_cashier_daily_acc f,"
				+ "cashier_daily_acc c left join user_usr u on u.id = c.userId where f.daily_acc_num = c.daily_acc_num "
				+ "and f.fd_acc_num = '"+fd_acc_num+"' order by c.daily_acc_num";
		List<FinanceVsCashierDailyAccDTO> list = this.jqm.getList(sql, FinanceVsCashierDailyAccDTO.class);
		return list;
	}
	@Override
	public List<CashierDailyAccInvoiceDTO> getFinanceVsCashierInvoiceList(String daily_status,String fd_acc_num) throws ServiceException{
		String sql = "";
		if("0".equals(daily_status)){
			sql = "select i.charging_way_id,i.invoice_num,i.invoice_status,d.data_name charging_way,case when i.invoice_status = 'N' "
					+ " then -i.invoice_amount else i.invoice_amount end invoice_amount from cashier_daily_acc c,"
					+ " cashier_daily_acc_invoice i left join data_dictionary d on d.id = i.charging_way_id where "
					+ " c.daily_acc_num = i.daily_acc_num and c.daily_status = '0' order by d.seq_code,i.invoice_num";
		}else{
			sql = "select i.charging_way_id,i.invoice_num,i.invoice_status,d.data_name charging_way,case when i.invoice_status = 'N' "
					+ "then -i.invoice_amount else i.invoice_amount end invoice_amount "
					+ "from Finance_vs_cashier_daily_acc f,cashier_daily_acc_invoice i left join data_dictionary d "
					+ "on d.id = i.charging_way_id where f.daily_acc_num = i.daily_acc_num and f.fd_acc_num = '"+fd_acc_num+"' "
					+ "order by d.seq_code,i.invoice_num";
		}
		System.out.println("-----------"+sql);
		List<CashierDailyAccInvoiceDTO> list = this.jqm.getList(sql, CashierDailyAccInvoiceDTO.class);
		return list;
	}
	@Override
	public PageReturnDTO getChargingSummarySingleList(FinanceDepAccModel model, int rows, int page,String center_num) throws ServiceException {
		String sql = "select s.id,c.user_name,c.sex,e.age,e.exam_num,u.chi_name cashier,s.cash_date,s.charging_status,s.daily_status,"
				+ " d.id charging_way_id,d.data_name charging_way,(case when s.charging_status <> 'Y' then -s.amount2 "
				+ " else s.amount2 end) amount2,(case when s.charging_status <> 'Y' then -w.amount else w.amount end) way_amount"
				+ " from exam_info e,customer_info c,charging_summary_single s left join user_usr u on u.id = s.cashier,"
				+ " charging_way_single w left join data_dictionary d on d.id = w.charging_way where e.customer_id = c.id "
				+ " and e.exam_num = s.exam_num and s.id = w.summary_id and s.center_num='"+center_num+"'";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sql += " and s.cash_date >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sql += " and s.cash_date < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			sql += " and s.cashier = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			sql += " and s.daily_status = '"+model.getDaily_status()+"'";
		}
		sql += " order by s.cash_date,d.seq_code";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows,CashierDailyAccListDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public PageReturnDTO getChargingSummaryGroupList(FinanceDepAccModel model, int rows, int page,String center_num) throws ServiceException {
		String sql = "select s.id,e.batch_name,c.com_name,u.chi_name cashier,s.cash_date,s.charging_status,s.daily_status,"
				+ "d.id charging_way_id,d.data_name charging_way,(case when s.charging_status <> 'Y' then -s.amount2 else "
				+ "s.amount2 end) amount2,(case when s.charging_status <> 'Y' then -w.amount else w.amount end) way_amount "
				+ "from batch e,company_info c,charging_summary_group s left join user_usr u on u.id = s.cashier,"
				+ "charging_way_single w left join data_dictionary d on d.id = w.charging_way where e.company_id = c.id "
				+ " and s.center_num='"+center_num+"' and e.id = s.batch_id and s.id = w.summary_id and s.receiv_status = '1'";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sql += " and s.cash_date >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sql += " and s.cash_date < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			sql += " and s.cashier = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			sql += " and s.daily_status = '"+model.getDaily_status()+"'";
		}
		sql += " order by s.cash_date,d.seq_code";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows,CashierDailyAccListDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public PageReturnDTO getChargingInvoiceList(FinanceDepAccModel model, int rows, int page,String center_num) throws ServiceException {
		
		String sql = "select s.invoice_num,d.data_name charging_way,s.invoice_status,u.chi_name user_name,"
				+ " case when s.invoice_status = 'N' then -s.invoice_amount else s.invoice_amount end invoice_amount,"
				+ "s.invoice_time,s.daily_status,d.seq_code from charging_invoice_single s left join data_dictionary d on s.invoice_class = d.id "
				+ "left join user_usr u on s.invoice_maker = u.id where 1=1 and s.center_num = '"+center_num+"'";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sql += " and s.invoice_time >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sql += " and s.invoice_time < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			sql += " and s.invoice_maker = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			sql += " and s.daily_status = '"+model.getDaily_status()+"'";
		}
		sql += "union all "
		+ "select n.invoice_num,d.data_name charging_way,n.invoice_status,u.chi_name user_name"
		+ ",case when n.invoice_status = 'N' then -s.invoice_amount else s.invoice_amount end invoice_amount "
		+ ",n.create_time invoice_time,n.daily_status,d.seq_code from nullify_invoice n left join charging_invoice_single s on s.invoice_num = n.invoice_num "
		+ "and s.invoice_class = n.invoice_class left join data_dictionary d on n.invoice_class = d.id left join user_usr u "
		+ "on n.creater = u.id where 1=1 and n.center_num = '"+center_num+"'";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sql += " and n.create_time >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sql += " and n.create_time < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			sql += " and n.creater = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			sql += " and n.daily_status = '"+model.getDaily_status()+"'";
		}
		
		sql = "select * from ( "+sql+" ) a order by a.seq_code,a.invoice_num";
		PageReturnDTO webrole = new PageReturnDTO();
		webrole.setPage(rows);
		webrole.setRp(page);
		PageSupport map = this.jqm.getList(sql, page, rows,CashierDailyAccInvoiceDTO.class);
		if ((map != null) && (map.getList() != null)) {
			webrole.setTotal(map.getRecTotal());
			webrole.setRows(map.getList());
		}
		return webrole;
	}
	@Override
	public List<FinanceDepAccDTO> getChargingSummarySum(FinanceDepAccModel model,String center_num) throws ServiceException {
		
		String tiaojian = "";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			tiaojian += " and s.cash_date >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			tiaojian += " and s.cash_date < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			tiaojian += " and s.cashier = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			tiaojian += " and s.daily_status = '"+model.getDaily_status()+"'";
		}
		
		String sqls = "select (case when s.charging_status <> 'Y' then -s.amount2 else s.amount2 end) amount2,cashier "
				+ " from charging_summary_single s where 1=1 and s.center_num='"+center_num+"' "+ tiaojian
				+ " union all "
				+ " select (case when s.charging_status <> 'Y' then -s.amount2 else s.amount2 end) amount2,cashier "
				+ " from charging_summary_group s where s.receiv_status = '1' and s.center_num='"+center_num+"' "+ tiaojian;
		
		String sqlw = "select (case when s.charging_status <> 'Y' then -w.amount else w.amount end) way_amount,s.cashier,w.charging_way "
				+ " from charging_summary_single s,charging_way_single w where w.summary_id = s.id and s.center_num='"+center_num+"' " + tiaojian
				+ " union all "
				+ " select (case when s.charging_status <> 'Y' then -w.amount else w.amount end) way_amount,s.cashier,w.charging_way "
				+ " from charging_summary_group s,charging_way_group w where w.summary_id = s.id and s.center_num='"+center_num+"' and s.receiv_status = '1' " + tiaojian;
		
		String sqlce = "select case when s.invoice_status = 'N' then -s.invoice_amount else s.invoice_amount end invoice_amount,"
				+ " s.invoice_maker from charging_invoice_single s where 1=1 and s.center_num='"+center_num+"' ";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sqlce += " and s.invoice_time >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sqlce += " and s.invoice_time < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			sqlce += " and s.invoice_maker = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			sqlce += " and s.daily_status = '"+model.getDaily_status()+"'";
		}
		sqlce += " union all select case when n.invoice_status = 'N' then -s.invoice_amount else s.invoice_amount end invoice_amount, "
			+ " n.creater from nullify_invoice n left join charging_invoice_single s on s.invoice_num = n.invoice_num "
			+ " and s.invoice_class = n.invoice_class where 1=1 and n.center_num='"+center_num+"' ";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			sqlce += " and n.create_time >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			sqlce += " and n.create_time < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		if(model.getUser_id() > 0){
			sqlce += " and n.creater = " + model.getUser_id();
		}
		if(model.getDaily_status() != null && !"".equals(model.getDaily_status())){
			sqlce += " and n.daily_status = '"+model.getDaily_status()+"'";
		}
		
		String sqlz = "select a.fd_acc_amount,b.way_amount,d.data_name,'全部' as user_name,-1 as userId"
				+ " ,(select sum(b.invoice_amount) from ("+sqlce+") b) invoice_amount"
				+ " from (select sum(a.amount2) fd_acc_amount from ( " + sqls
				+ " ) a ) a,( select sum(a.way_amount) way_amount,a.charging_way from ( " + sqlw
				+ " ) a group by a.charging_way ) b left join data_dictionary d on d.id = b.charging_way order by d.seq_code";
		List<FinanceDepAccDTO> list = this.jqm.getList(sqlz, FinanceDepAccDTO.class);
		
		String sqlf = "select a.fd_acc_amount,b.way_amount,d.data_name,u.chi_name user_name,a.cashier as userId,inv.invoice_amount from ("
				+ " select sum(a.amount2) as fd_acc_amount,a.cashier from ( " +sqls
				+ " ) a group by a.cashier) a,(select sum(a.way_amount) way_amount,a.cashier,a.charging_way from ( " + sqlw
				+ " ) a group by a.cashier,a.charging_way ) b left join user_usr u on u.id = b.cashier "
				+ "left join data_dictionary d on d.id = b.charging_way"
				+ ",(select sum(b.invoice_amount) invoice_amount,b.invoice_maker from ("+sqlce+") b group by b.invoice_maker) inv"
				+ " where a.cashier = b.cashier and inv.invoice_maker = b.cashier order by a.cashier,d.seq_code";
		List<FinanceDepAccDTO> listf = this.jqm.getList(sqlf, FinanceDepAccDTO.class);
		list.addAll(listf);
		return list;
	}
	@Override
	public List<FinanceDepAccDTO> getMonthlyStatementSum(FinanceDepAccModel model,String center_num) throws ServiceException {
		
		String tiaojian = "";
		if(model.getStart_date() != null && !"".equals(model.getStart_date())){
			tiaojian += " and f.fd_acc_date >= '"+model.getStart_date()+" 00:00:00.000'";
		}
		if(model.getEnd_date() != null && !"".equals(model.getEnd_date())){
			tiaojian += " and f.fd_acc_date < '"+model.getEnd_date()+" 23:59:59.999'";
		}
		String sql = "select * from"
				+ "(select sum(f.fd_acc_amount) fd_acc_amount from Finance_Dep_acc f where 1=1 and f.center_num='"+center_num+"' "+tiaojian+") a,"
				+ "(select sum(w.amount) way_amount,w.charging_way_id,d.data_name from Finance_Dep_acc f,Finance_Dep_acc_payway w left join data_dictionary d on d.id = w.charging_way_id"
				+ " where f.fd_acc_num = w.fd_acc_num and f.center_num='"+center_num+"' "+tiaojian+" group by w.charging_way_id,d.data_name) b,"
				+ " (select sum(case when i.invoice_status = 'N' then -i.invoice_amount else i.invoice_amount end) invoice_amount from Finance_Dep_acc f,Finance_vs_cashier_daily_acc fc,cashier_daily_acc_invoice i"
				+ " where f.fd_acc_num = fc.fd_acc_num and fc.daily_acc_num = i.daily_acc_num and f.center_num='"+center_num+"' "+tiaojian+") c";
		List<FinanceDepAccDTO> list = this.jqm.getList(sql, FinanceDepAccDTO.class);
		return list;
	}
	
}
