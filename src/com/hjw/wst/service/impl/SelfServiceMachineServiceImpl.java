package com.hjw.wst.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.DataDictionaryDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.ChargingDetailSingle;
import com.hjw.wst.domain.ChargingSummarySingle;
import com.hjw.wst.domain.ChargingWaySingle;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.service.SelfServiceMachineService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

public class SelfServiceMachineServiceImpl implements SelfServiceMachineService{
	
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}
	
	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}
	
	@Override
	public String selfServiceMachineFees(String exam_num, Double amount, String charging_way, String card_num,String is_fees_qijian,long userid, String center_num)
			throws ServiceException {
		String logName = "fees_self";
		TranLogTxt.liswriteEror_to_txt(logName, "------------------交易开始------------------");
		String log = "传入参数 exam_num:"+exam_num+",amount:"+amount+",charging_way:"+charging_way+",card_num:"+card_num+",userid:"+userid;
		TranLogTxt.liswriteEror_to_txt(logName, log);
		String infoSql = "select e.id,e.exam_num from exam_info e where e.exam_num = '"+exam_num+"' and e.is_Active = 'Y'";
		List<ExamInfoUserDTO> examinfolist = this.jqm.getList(infoSql, ExamInfoUserDTO.class);
		ExamInfoUserDTO examinfo = null;
		if(examinfolist.size() > 0){
			examinfo = examinfolist.get(0);
		}else{
			return "error-该体检信息不存在!";
		}
		String itemsql = "select ec.id,c.id as item_id,c.item_code,c.item_name,ec.exam_status,ec.item_amount,ec.discount,"
				+ "ec.amount,ec.itemnum,ec.personal_pay,ec.team_pay,ec.his_req_status "
				+ "from examinfo_charging_item ec,charging_item c "
				+ "where ec.exam_num = '"+exam_num+"' and ec.pay_status = 'N' and ec.charge_item_id = c.id "
				+ "and ec.isActive = 'Y' and ec.change_item <> 'C' and ec.center_num = '"+center_num+"'";
		if("N".equals(is_fees_qijian)){
			itemsql += " and ec.exam_status <> 'G'";
		}
		TranLogTxt.liswriteEror_to_txt(logName, "查询未收费项目sql:"+itemsql);
		List<ExamResultChargingItemDTO> itemlist = this.jqm.getList(itemsql, ExamResultChargingItemDTO.class);
		if(itemlist.size() <= 0){
			return "error-没有需要收费的项目!";
		}
		Double shihsou = 0.0,yuanjia=0.0,yingshou=0.0;
		for (ExamResultChargingItemDTO examResultChargingItemDTO : itemlist) {
			yuanjia += examResultChargingItemDTO.getItem_amount() * examResultChargingItemDTO.getItemnum();
			shihsou += examResultChargingItemDTO.getPersonal_pay();
			yingshou += examResultChargingItemDTO.getAmount();
		}
		TranLogTxt.liswriteEror_to_txt(logName, "系统计算 金额为"+shihsou);
		TranLogTxt.liswriteEror_to_txt(logName, "自助机传的 金额为"+amount);
		if(!shihsou.equals(amount)){
			return "error-金额不一致，请重新缴费!";
		}
		String waySql = " select d.id,d.data_code_children from data_dictionary d where d.id = '"+charging_way+"'";
		List<DataDictionaryDTO> wayList = this.jqm.getList(waySql,DataDictionaryDTO.class);
		DataDictionaryDTO way = null;
		if(wayList.size() > 0){
			way = wayList.get(0);
		}else{
			return "error-请选择收费方式!";
		}
		CardInfoDTO cardinfo = null;
		if("CARD_WAY".equals(way.getData_code_children())){//会员卡缴费
			if(card_num == null || "".equals(card_num)){
				return "error-会员卡缴费，请先刷卡!";
			}
			String cardSql = " select c.card_num,c.amount,CONVERT(varchar(50),c.limit_card_count) as limit_card_count,"
					+ "CONVERT(varchar(100),c.deadline,23) as deadline,c.card_count,"
					+ "c.status,c.send_status from card_info c where c.card_num = '"+card_num+"'";
			List<CardInfoDTO> cardlist = this.jqm.getList(cardSql, CardInfoDTO.class);
			if(cardlist.size() > 0){
				cardinfo = cardlist.get(0);
				if(!cardinfo.getStatus().equals("1")){
					return "error-该卡已经"+cardinfo.getStatus_y();
				}else if(cardinfo.getLimit_card_count() != null && Long.valueOf(cardinfo.getLimit_card_count()) <= cardinfo.getCard_count()){
					return "error-该卡使用次数已经用完！";
				}else if(cardinfo.getDeadline() != null && cardinfo.getDeadline().compareTo(DateTimeUtil.getDate2()) > 0){
					return "error-该卡已超出有效期，不能使用！";
				}else if(cardinfo.getAmount() < amount){
					return "error-卡余额不足，卡内剩余金额为"+cardinfo.getAmount()+"元。";
				}
			}else{
				return "error-该会员卡不存在，请检查!";
			}
		}
		DecimalFormat df = new DecimalFormat("#.00");
		//保存收费主表记录
		ChargingSummarySingle chargingSummarySingle = new ChargingSummarySingle();
		//1收费总记录
		chargingSummarySingle.setExam_id(examinfo.getId());
		chargingSummarySingle.setReq_num(GetNumContral.getInstance().getParamNum("rcpt_num", center_num));
		chargingSummarySingle.setCharging_status("Y");
		chargingSummarySingle.setAmount1(yuanjia);
		chargingSummarySingle.setAmount2(shihsou);
		chargingSummarySingle.setDiscount(Double.valueOf(df.format(yingshou/yuanjia*10)));
		chargingSummarySingle.setCashier(userid);
		chargingSummarySingle.setCash_date(DateTimeUtil.parse());
		chargingSummarySingle.setIs_print_recepit("N");
		chargingSummarySingle.setIs_active("Y");
		chargingSummarySingle.setCreater(userid);
		chargingSummarySingle.setCreate_time(DateTimeUtil.parse());
		chargingSummarySingle.setUpdater(userid);
		chargingSummarySingle.setUpdate_time(DateTimeUtil.parse());
		chargingSummarySingle.setDaily_status("0");
		chargingSummarySingle.setExam_num(exam_num);
		
		this.pm.save(chargingSummarySingle);  //保存收费总表
		//2保存结算明细
		for(ExamResultChargingItemDTO item : itemlist){
			ChargingDetailSingle chargingDetailSingle = new ChargingDetailSingle();
			
			chargingDetailSingle.setSummary_id(chargingSummarySingle.getId());
			chargingDetailSingle.setAmount(item.getItem_amount());
			chargingDetailSingle.setAmount1(item.getPersonal_pay());
			chargingDetailSingle.setCharging_item_id(item.getItem_id());
			chargingDetailSingle.setDiscount(item.getDiscount());
			chargingDetailSingle.setCreater(userid);
			chargingDetailSingle.setCreate_time(DateTimeUtil.parse());
			chargingDetailSingle.setUpdater(userid);
			chargingDetailSingle.setUpdate_time(DateTimeUtil.parse());
			
			this.pm.save(chargingDetailSingle);  //保存结算明细
			
			//3修改项目状态
			ExaminfoChargingItem ec = (ExaminfoChargingItem) this.qm.load(ExaminfoChargingItem.class, item.getId());
			ec.setPay_status("Y");
			this.pm.update(ec);//修改项目状态
		}
		ChargingWaySingle chargingWaySingle = new ChargingWaySingle();
		
		chargingWaySingle.setSummary_id(chargingSummarySingle.getId());
		chargingWaySingle.setAmount(amount);
		chargingWaySingle.setCharging_way(way.getId()+"");
		chargingWaySingle.setCreater(userid);
		chargingWaySingle.setCreate_time(DateTimeUtil.parse());
		chargingWaySingle.setUpdater(userid);
		chargingWaySingle.setUpdate_time(DateTimeUtil.parse());
		this.pm.save(chargingWaySingle);
		if("CARD_WAY".equals(way.getData_code_children())){//会员卡缴费
			List<CardInfo> cardlist = this.qm.find("from CardInfo c where c.card_num = '"+card_num+"'");
			if(cardlist.size() > 0){
				CardInfo cardifo = cardlist.get(0);
				
				CardDeal cardDeal = new CardDeal();
				cardDeal.setCard_num(cardifo.getCard_num());
				cardDeal.setAmount(amount);
				cardDeal.setCard_count(cardifo.getCard_count()+1);
				cardDeal.setSummary_id(chargingSummarySingle.getId());
				cardDeal.setExaminfo_id(examinfo.getId());
				cardDeal.setDeal_type("6");
				cardDeal.setExam_num(exam_num);
				cardDeal.setCreater(userid);
				cardDeal.setDeal_date(DateTimeUtil.getDate2());
				cardDeal.setDeal_time(DateTimeUtil.parse());
				cardDeal.setOld_amount(cardifo.getAmount());
				cardDeal.setTrancode("001");
				this.pm.save(cardDeal);
				
				cardifo.setAmount(cardifo.getAmount() - amount);
				cardifo.setCard_count(cardifo.getCard_count()+1);
				this.pm.update(cardifo);
			}
			
		}
		return "ok-自助缴费成功!";
	}
}
