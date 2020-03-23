package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.wst.DTO.CardDealDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.service.CardDealService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

public class CardDealServiceImpl implements CardDealService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private JdbcPersistenceManager jpm;
	private PersistenceManager pm;
	
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
	
	@Override
	public CardDeal saveCardDeal(CardDeal cardDeal) throws ServiceException {
		this.pm.save(cardDeal);
		return cardDeal;
	}

	@Override
	public PageReturnDTO getcardDealList(String card_num, String deal_type, String creater, String deal_date,
			int pagesize, int pageno ,String user_name,String exam_num,UserDTO user) throws ServiceException {
		
		String sql = "select  c.deal_type,c.deal_date,c.card_num ,c.amount,c.card_count,c.examinfo_id,c.remark,"
				+" u.chi_name as creater , ci.user_name  ,e.exam_num "
				+" from card_info cai, card_deal c left join exam_info  e  on e.id = c.examinfo_id "
				+" left join user_usr u on u.id = c.creater "
				+" left join  customer_info ci on ci.id = e.customer_id "
				+" where cai.card_num = c.card_num and cai.center_num = '"+user.getCenter_num()+"' and c.trancode = '001' ";
			
		if(card_num != null && !card_num.equals("")){
			sql += " and c.card_num like '%"+card_num.trim()+"%'";
		}
		if (exam_num != null && !"".equals(exam_num)) {
			sql+= " and e.exam_num = '"+exam_num+"'";
		}
		if(deal_type != null && !deal_type.equals("")){
			sql += " and c.deal_type = '"+deal_type+"'";
		}
		if(creater != null && !creater.equals("")){
			sql += " and u.chi_name = '"+creater+"'";
		}
		if(deal_date != null && !deal_date.equals("")){
			sql += " and c.deal_date = '"+deal_date+"'";
		}
		if (user_name != null && !user_name.equals("")) {
			sql += " and ci.user_name like '"+user_name+"%'";
		}
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, CardDealDTO.class);
		List mapList = map.getList();
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

}
