package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.IsPrivateCenterConfig;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CardMemberDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.CloseCard;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.model.CardMemberModel;
import com.hjw.wst.service.CardManageService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;
import com.synjones.framework.support.PageSupport;

/**
 * 	会员卡管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年7月18日 下午2:06:24   
     * @version V2.0.0.0
 */
public class CardManageServiceImpl implements CardManageService {
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

	/**
	 * 	获取会员信息列表
	     * <p>Title: cardMemberList</p>   
	     * <p>Description: </p>   
	     * @param pagesize
	     * @param pageno
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CardManageService#cardMemberList(int, int)
	 */
	@Override
	public PageReturnDTO cardMemberList(String username,String id_num,String phone,long level,int pagesize, int pageno,UserDTO user) throws ServiceException {
		String isprivateflag = IsPrivateCenterConfig.isprivateflag(user);
		String sql = "select cm.id,ci.arch_num,ci.id,dbo.fun_CustomerToStar(ci.id,'name',ci.user_name,'"+isprivateflag+"') as user_name,d1.data_name as level,cm.integral,dbo.fun_CustomerToStar(ci.id,'id_num',ci.id_num,'"+isprivateflag+"') as id_num,"
				   + "ci.sex,CONVERT(varchar(100),ci.birthday,23) as birthday,dbo.fun_CustomerToStar(ci.id,'phone',ci.phone,'"+isprivateflag+"') as phone,ci.email,ci.address,cm.totalamt,cm.totaltimes,"
				   + "d3.data_name as prelevel,cm.preintegral,CONVERT(varchar(100),cm.integeraltime,23) as integeraltime,CONVERT(varchar(100),cm.leveltime,23) as leveltime "
				   + "From customer_info ci,customer_member_info cm left join data_dictionary d1 on d1.id = cm.level "
				   + "left join data_dictionary d3 on d3.id = cm.prelevel "
				   + "Where cm.arch_num = ci.arch_num";
		if(username != null && !username.equals("")){
			sql += " and ci.user_name like '"+username.trim()+"%'";
		}
		if(id_num != null && !id_num.equals("")){
			sql += " and ci.id_num = '"+id_num.trim()+"'";
		}
		if(phone != null && !phone.equals("")){
			sql += " and ci.phone = '"+phone.trim()+"'";
		}
		if(level > 0){
			sql += " and cm.level = '"+level+"'";
		}
		PageSupport map = this.jqm.getList(sql, pageno, pagesize, CardMemberDTO.class);
		List mapList = map.getList();
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto.setPage(pageno);
		pageDto.setRp(pagesize);
		pageDto.setTotal(map.getRecTotal());
		pageDto.setRows(map.getList());
		return pageDto;
	}

	/**
	 * 	根据身份证查询会员信息
	     * <p>Title: getCardMemberByIdNum</p>   
	     * <p>Description: </p>   
	     * @param idNum
	     * @return
	     * @throws ServiceException   
	     * @see com.hjw.wst.service.CardManageService#getCardMemberByIdNum(java.lang.String)
	 */
	@Override
	public CustomerMemberInfo getCardMemberByIdNum(String idNum) throws ServiceException {
		String sql = "select cm.id,ci.id as customer_id,ci.user_name,cm.level,cm.integral From "
				   + "customer_member_info cm,customer_info ci Where cm.arch_num = ci.arch_num and ci.id_num ='"+idNum.trim()+"'";
		List ls = jqm.getList(sql, CustomerMemberInfo.class);
		if(ls.size()>0)
			return (CustomerMemberInfo) ls.get(0);
		else
			return null;
	}
	
	@Override
	public CustomerMemberInfo getCardMemberBymId(String id) throws ServiceException{
		List ls = this.qm.find("from CustomerMemberInfo where arch_num = '"+id+"'");
		if(ls.size()>0)
			return (CustomerMemberInfo) ls.get(0);
		else
			return null;
	}
	
	@Override
	public CardMemberModel getCardMemberById(String id) throws ServiceException {
		List ls = jqm.getList("select cm.id,ci.id as customer_id,ci.arch_num,ci.user_name,cm.level,cm.integral,ci.id_num,ci.sex,ci.birthday,ci.phone,ci.email,ci.nation,ci.address From customer_member_info cm,customer_info ci Where cm.arch_num = ci.arch_num and cm.id ='"+id.trim()+"'", CardMemberModel.class);
		if(ls.size()>0)
			return (CardMemberModel) ls.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String deleteCardInfo(String card_nums,UserDTO user) throws ServiceException {
		
		List<CardInfo> list = this.qm.find("from CardInfo c where c.card_num in ("+card_nums+")");
		
		for(CardInfo cardInfo : list){
			CloseCard closeCard = new CloseCard();
			
			closeCard.setCard_num(cardInfo.getCard_num());
			closeCard.setCard_pwd(cardInfo.getCard_pwd());
			closeCard.setAmount(cardInfo.getAmount());
			closeCard.setFace_amount(cardInfo.getFace_amount());
			closeCard.setCard_level(cardInfo.getCard_level());
			closeCard.setCard_type(cardInfo.getCard_type());
			closeCard.setDeadline(cardInfo.getDeadline());
			closeCard.setMember_id(cardInfo.getMember_id());
			closeCard.setRemark(cardInfo.getRemark());
			closeCard.setCreate_time(DateTimeUtil.parse());
			closeCard.setCreater(user.getUserid());
			
			this.pm.save(closeCard);
			
			this.pm.remove(cardInfo);
		}
		
		return "删除卡信息成功!";
	}
	
	@Override
	public CustomerMemberInfo saveCardMember(CustomerMemberInfo customerMemberInfo) throws ServiceException {
		this.pm.save(customerMemberInfo);
		return customerMemberInfo;
	}

	@Override
	public CustomerMemberInfo updateCardMember(CustomerMemberInfo customerMemberInfo) throws ServiceException {
		this.pm.update(customerMemberInfo);
		return customerMemberInfo;
	}

	@Override
	public CustomerInfo saveCustomerInfo(CustomerInfo customerInfo) throws ServiceException {
		this.pm.save(customerInfo);
		return customerInfo;
	}

	@Override
	public CustomerInfo updateCustomerInfo(CustomerInfo customerInfo) throws ServiceException {
		this.pm.update(customerInfo);
		return customerInfo;
	}

	@Override
	public CustomerInfo getCustomerInfoByIdNum(String idNum) throws ServiceException {
		List ls = qm.find("from CustomerInfo where id_num = '"+idNum.trim()+"' and is_Active = 'Y'");
		if(ls.size()>0)
			return (CustomerInfo) ls.get(0);
		else
			return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<CardInfoDTO> getCardInfoByMerId(String memberId) throws ServiceException{
		String sql = "select c.id,c.card_num,c.card_type,c.status,c.amount,c.card_count,convert(varchar(50),c.limit_card_count) as limit_card_count,CONVERT(varchar(100),c.deadline,23) as deadline,d.data_name as card_level,c.remark from card_info c "
				+ "left join data_dictionary d on d.id = c.card_level where c.member_id = (select c.arch_num from customer_member_info c where c.id = '"+memberId+"')";
		List<CardInfoDTO> list = this.jqm.getList(sql, CardInfoDTO.class);
		return list;
	}
}
