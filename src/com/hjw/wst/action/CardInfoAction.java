package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CardSaleWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInvoiceDTO;
import com.hjw.config.Logincheck;
import com.hjw.wst.domain.CardDeal;
import com.hjw.wst.domain.CardExamSet;
import com.hjw.wst.domain.CardExamSetItem;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.CardInfoModel;
import com.hjw.wst.model.CardMemberModel;
import com.hjw.wst.service.CardDealService;
import com.hjw.wst.service.CardInfoService;
import com.hjw.wst.service.CardManageService;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInvoiceService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

@SuppressWarnings("rawtypes")
public class CardInfoAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private CardInfoService cardInfoService;
	private CardManageService cardManageService;
	private CardDealService cardDealService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	private UserInvoiceService userInvoiceService;
	private CollectFeesService collectFeesService;
	
	private String card_reader_code;
	private String card_reader_ocx;
	
	private CardInfoModel model = new CardInfoModel();
	private int rows = 15; // easyui每页显示条数
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getCard_reader_code() {
		return card_reader_code;
	}

	public void setCard_reader_code(String card_reader_code) {
		this.card_reader_code = card_reader_code;
	}

	public String getCard_reader_ocx() {
		return card_reader_ocx;
	}

	public void setCard_reader_ocx(String card_reader_ocx) {
		this.card_reader_ocx = card_reader_ocx;
	}

	public void setCardInfoService(CardInfoService cardInfoService) {
		this.cardInfoService = cardInfoService;
	}

	public void setCardManageService(CardManageService cardManageService) {
		this.cardManageService = cardManageService;
	}

	public void setCardDealService(CardDealService cardDealService) {
		this.cardDealService = cardDealService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setUserInvoiceService(UserInvoiceService userInvoiceService) {
		this.userInvoiceService = userInvoiceService;
	}

	public void setCollectFeesService(CollectFeesService collectFeesService) {
		this.collectFeesService = collectFeesService;
	}

	/**
	 * 会员页面新增卡信息页面  308
	     * @Title: cardInfoadd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String cardInfoadd() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		CardMemberModel cardMember = this.cardManageService.getCardMemberById(this.model.getMember_id());
		this.model.setUser_name(cardMember.getUser_name());
		this.model.setArch_num(cardMember.getArch_num());
		this.model.setIs_start_card_reader(this.customerInfoService.getCenterconfigByKey("IS_START_CARD_READER", user.getCenter_num()).getConfig_value());
		return SUCCESS;
	}
	/*查询科室下的所有体检套餐
	 * getijiantaocan
	     * <p>Title: getModel</p>   
	     * <p>Description: </p>   
	     * @return   
	     * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public  String  getijiantaocan() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
//		this.defaultapp = (SystemType)session.get("defaultapp");
		List<ExamSetDTO>  list =   cardInfoService.getijiantaocanlist(user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 *  验证卡号是否存在  309
	     * @Title: checkCardNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkCardNum() throws WebException,SQLException{
		CardInfo card = this.cardInfoService.getCardInfoByCarNum(this.model.getCard_num());
		if(card == null){
			this.message = "ok";
		}else{
			this.message = "no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 新增，修改卡信息  310
	     * @Title: saveCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardInfo() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		CardInfo cardInfo = new CardInfo();
		if(this.model.getCard_id().length() > 0){
			cardInfo = this.cardInfoService.getCardInfoById(this.model.getCard_id());
		}
		cardInfo.setId(this.model.getCard_id());
		cardInfo.setPhysical_num(this.model.getPhysical_num());
		cardInfo.setCard_pwd(this.model.getCard_pwd());
		cardInfo.setCard_num(this.model.getCard_num());
		cardInfo.setMember_id(this.model.getArch_num());
		cardInfo.setCard_type(this.model.getCard_type());
		cardInfo.setAmount(this.model.getAmount());
		cardInfo.setLimit_card_count(this.model.getLimit_card_count());
		cardInfo.setCard_level(this.model.getCard_level());
		cardInfo.setDeadline(DateTimeUtil.parse2(this.model.getDeadline()));
		cardInfo.setRemark(this.model.getCard_remark());
		cardInfo.setDiscount(this.model.getDiscount());
		cardInfo.setCompany(this.model.getCompany());
		cardInfo.setSale_amount(this.model.getSale_amount());
		cardInfo.setStatus("1");
		cardInfo.setIs_set_status(0l);
//		if(this.model.getTijiantaocan_id().equals("")) {
//			cardInfo.setIs_set_status(0l);
//		}else {
//			cardInfo.setIs_set_status(1l);
//			//保存卡信息对应套餐信息表
//			List<ExamSetDTO>  list =   cardInfoService.getijiantaocanlists(this.model.getTijiantaocan_id(),user.getCenter_num());
//			CardExamSet cardExamSet = new CardExamSet();
//			cardExamSet.setCard_num(this.model.getCard_num());
//			cardExamSet.setSet_num(list.get(0).getSet_num());
//			cardExamSet.setSet_amount(list.get(0).getSet_amount());
//			cardExamSet.setSet_name(list.get(0).getSet_name());
//			cardExamSet.setSet_discount(list.get(0).getSet_discount());
//			cardExamSet.setCenter_num(this.model.getExam_num());
//			cardExamSet.setCreater(user.getUserid());
//			cardExamSet.setCreate_time(DateTimeUtil.parse());
//			this.cardInfoService.saveCardExamSet(cardExamSet);
//			//保存卡信息对应套餐项目信息表
//			List<ExamSetDTO>  lists =   cardInfoService.getijiantaocanlistss(this.model.getTijiantaocan_id(),user.getCenter_num());
//			for (int i = 0; i < lists.size(); i++) {
//				CardExamSetItem cardExamSetItem = new CardExamSetItem();
//				cardExamSetItem.setCard_num(this.model.getCard_num());
//				cardExamSetItem.setCharging_item_code(lists.get(i).getItem_code());
//				cardExamSetItem.setSet_num(lists.get(i).getSet_num());
//				cardExamSetItem.setItemnum(lists.get(i).getItemnum());
//				cardExamSetItem.setDiscount(lists.get(i).getDiscount());
//				cardExamSetItem.setAmount(lists.get(i).getAmount());
//				cardExamSetItem.setItem_amount(lists.get(i).getItem_amount());
//				cardExamSetItem.setCreate_time(DateTimeUtil.parse());
//				cardExamSetItem.setCreater(user.getUserid());
//				this.cardInfoService.saveCardExamSetItem(cardExamSetItem);
//			}
//			
//		}
		if(cardInfo.getCard_type().equals("2")){
			cardInfo.setFace_amount(this.model.getAmount());
		}
		if(cardInfo.getId().length() > 0){
			cardInfo.setUpdater(user.getUserid());
			cardInfo.setUpdate_time(DateTimeUtil.parse());
			this.cardInfoService.updateCardInfo(cardInfo);
			this.message = "绑定卡信息成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("310");//子功能id 必须填写
			sl.setExplain("修改卡信息");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			cardInfo.setHair_card_status(0);
			cardInfo.setCreater(user.getUserid());
			cardInfo.setCreate_time(DateTimeUtil.parse());
			cardInfo.setUpdater(user.getUserid());
			cardInfo.setUpdate_time(DateTimeUtil.parse());
			cardInfo.setCenter_num(user.getCenter_num()); //体检中心编码
			cardInfo.setFace_amount(this.model.getAmount());
			this.cardInfoService.saveCardInfo(cardInfo);
			CardDeal cardDeal = new CardDeal();
			
			cardDeal.setCard_num(cardInfo.getCard_num());
			cardDeal.setDeal_type("5");
			cardDeal.setAmount(cardInfo.getAmount());
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setOld_amount(0.00);
			cardDeal.setTrancode("001");
			this.cardDealService.saveCardDeal(cardDeal);
			this.message = "保存卡信息成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("310");//子功能id 必须填写
			sl.setExplain("新增卡信息");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 解除绑定的卡信息 311
	     * @Title: removeBinding   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String removeBinding() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CardInfo cardInfo = this.cardInfoService.getCardInfoById(this.model.getCard_id());
		
		cardInfo.setMember_id(null);
		cardInfo.setCard_type("2");
		cardInfo.setUpdater(user.getUserid());
		cardInfo.setUpdate_time(DateTimeUtil.parse());
		
		this.cardInfoService.updateCardInfo(cardInfo);
		this.message = "解除绑定成功";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 卡信息管理页面  312
	     * @Title: cardinfomanage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String cardinfomanage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		List<WebResrelAtionship> webResource = user.getWebResource();
		for (WebResrelAtionship wb : webResource) {
			if(wb.getRes_code() != null && wb.getRes_code().equals("RS041") ){
				this.model.setStatus("Y");
			}
		}
		this.card_reader_code = this.customerInfoService.getCenterconfigByKey("CARD_READER_INFO", user.getCenter_num()).getConfig_value().trim();
		this.card_reader_ocx = this.customerInfoService.getDiversforByCode(this.card_reader_code).getCom_ocx_name();
		String IS_CARD_USER_CHOOSE = "N";
//		try {
//			IS_CARD_USER_CHOOSE = this.customerInfoService.getCenterconfigByKey("IS_CARD_USER_CHOOSE", user.getCenter_num()).getConfig_value().trim();
//		} catch (ServiceException e) {
//			System.out.println("缺少配置。。IS_CARD_USER_CHOOSE，系统默认 N");
//		}
		this.model.setIsCardUserChoose(IS_CARD_USER_CHOOSE);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("312");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询卡信息列表  313
	     * @Title: getCardInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCardInfoList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		Language.setLanguage(this.language);
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto = this.cardInfoService.cardInfoList(user,this.model.getCard_num(),this.model.getStatus(),
				this.model.getCard_type(),this.model.getCard_level(),this.model.getTime1(),this.model.getTime2()
				,this.model.getCompany(),this.model.getHair_card_status(),this.model.getHair_card_creater(),this.rows, this.getPage());
		this.outJsonResult(pageDto);
		return NONE;
	}
	
	/**
	 * 卡信息管理页面 增加卡信息  314
	     * @Title: cardinfomanage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String cardinfosave() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setIs_start_card_reader(customerInfoService.getCenterconfigByKey("IS_START_CARD_READER", user.getCenter_num()).getConfig_value());
		this.model.setLimit_card_count(1L);
		return SUCCESS;
	}
	
	public String plupdatecard() throws WebException{
//		this.model.setIs_start_card_reader(this.cardInfoService.getCardConfige("IS_START_CARD_READER"));
//		this.model.setLimit_card_count(1L);
		return SUCCESS;
	}
	
	
	
	/**
	 * 挂失、锁定、作废、恢复卡信息 315
	     * @Title: cardInfoUpdate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String cardInfoUpdate() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CardInfo cardInfo = this.cardInfoService.getCardInfoById(this.model.getCard_id());
		
		if(cardInfo == null){
			this.message = "卡信息无效";
		}else{
			String status = this.model.getStatus();
			cardInfo.setStatus(status);
			cardInfo.setUpdater(user.getUserid());
			cardInfo.setUpdate_time(DateTimeUtil.parse());
			this.cardInfoService.updateCardInfo(cardInfo);
			CardDeal cardDeal = new CardDeal();
			cardDeal.setCard_num(cardInfo.getCard_num());
			cardDeal.setDeal_type(status);
			cardDeal.setCreater(user.getUserid());
			cardDeal.setDeal_date(DateTimeUtil.getDate2());
			cardDeal.setDeal_time(DateTimeUtil.parse());
			cardDeal.setTrancode("001");
			cardDeal.setExam_num(this.model.getExam_num());
			this.cardDealService.saveCardDeal(cardDeal);
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("315");//子功能id 必须填写
			
			if(status.equals("1")){
				sl.setExplain("恢复卡信息");//操作说明
				this.message = "恢复卡信息成功";
			}else if(status.equals("2")){
				sl.setExplain("锁定卡信息");//操作说明
				this.message = "锁定卡信息成功";
			}else if(status.equals("3")){
				sl.setExplain("挂失卡信息");//操作说明
				this.message = "挂失卡信息成功";
			}else if(status.equals("4")){
				sl.setExplain("作废卡信息");//操作说明
				this.message = "作废卡信息成功";
			}
			
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 延期页面  316
	     * @Title: cardinfomanage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String cardinfoyanqi() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 延期保存 317
	     * @Title: cardInfoEdityanqi   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String cardInfoEdityanqi() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CardInfo cardInfo = this.cardInfoService.getCardInfoById(this.model.getCard_id());
		if(cardInfo == null){
			this.message = "卡信息无效";
		}else{
			cardInfo.setDeadline(DateTimeUtil.parse2(this.model.getDeadline()));
			cardInfo.setUpdater(user.getUserid());
			cardInfo.setUpdate_time(DateTimeUtil.parse());
			this.cardInfoService.updateCardInfo(cardInfo);
			this.message = "延期成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("317");//子功能id 必须填写
			sl.setExplain("卡延期");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 卡充值页面 318
	     * @Title: cardPerpaid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String cardPerpaid() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String isShowInvoicePage = this.customerInfoService.getCenterconfigByKey("IS_SHOW_INVOICE_PAGE", user.getCenter_num()).getConfig_value().trim();
		if(isShowInvoicePage == null || "".equals(isShowInvoicePage)){
			isShowInvoicePage = "N";
		}
		this.model.setIsShowInvoicePage(isShowInvoicePage.trim());
		String invoiceprinttype = this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE", user.getCenter_num()).getConfig_value();
		if(invoiceprinttype == null || "".equals(invoiceprinttype)){
			invoiceprinttype = "1";
		}
		this.model.setInvoiceprinttype(invoiceprinttype.trim());
		this.card_reader_code = this.customerInfoService.getCenterconfigByKey("CARD_READER_INFO", user.getCenter_num()).getConfig_value().trim();
		this.card_reader_ocx = this.customerInfoService.getDiversforByCode(this.card_reader_code).getCom_ocx_name();

		String  IS_CHARGE_BEARER_CARD = "N";//不记名卡是否可以充值
		try{
			IS_CHARGE_BEARER_CARD =this.customerInfoService.getCenterconfigByKey("IS_CHARGE_BEARER_CARD", user.getCenter_num()).getConfig_value().trim();
		}catch (Exception e){
			System.out.println("缺少配置 -->不记名卡是否能充值 IS_CHARGE_BEARER_CARD 。。。。 默认值 N ");
		}
		this.model.setIs_charge_bearer_card(IS_CHARGE_BEARER_CARD);
		return SUCCESS;
	}
	
	/**
	 * 根据卡号查询信息 319
	     * @Title: getCardInfoByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardInfoByNum() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CardInfoDTO cardInfoDTO = this.cardInfoService.getCardInfoByCardNum(user,this.model.getCard_num());
		this.outJsonResult(cardInfoDTO);
		return NONE;
	}
	
	/**
	 *  保存充值信息 320
	     * @Title: saveCardPerpaid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardPerpaid() throws WebException,SQLException{
//		UserDTO user = (UserDTO) session.get("username");
//		CustomerMemberInfo cardMember = this.cardManageService.getCardMemberBymId(this.model.getMember_id());
//		
//		cardMember.setPreintegral(cardMember.getIntegral());
//		cardMember.setIntegeraltime(DateTimeUtil.parse());
//		cardMember.setIntegral(cardMember.getIntegral()+this.model.getC_points());
//		
//		this.cardManageService.updateCardMember(cardMember);
//		
//		CardInfo cardInfo = this.cardInfoService.getCardInfoById(this.model.getCard_id());
//		cardInfo.setAmount(this.model.getAmount()+this.model.getC_amount());
//		cardInfo.setFace_amount(cardInfo.getFace_amount() + this.model.getC_amount());
//		cardInfo.setUpdater(user.getUserid());
//		cardInfo.setUpdate_time(DateTimeUtil.parse());
//		
//		this.cardInfoService.updateCardInfo(cardInfo);
//		
//		CardDeal cardDeal = new CardDeal();
//		
//		cardDeal.setCard_num(this.model.getCard_num());
//		cardDeal.setDeal_type("5");
//		cardDeal.setAmount(this.model.getC_amount());
//		cardDeal.setCreater(user.getUserid());
//		cardDeal.setRemark(this.model.getCard_remark());
//		cardDeal.setDeal_date(DateTimeUtil.getDate2());
//		cardDeal.setDeal_time(DateTimeUtil.parse());
//		cardDeal.setOld_amount(this.model.getAmount());
//		cardDeal.setTrancode("001");
//		cardDeal.setExam_num(this.model.getExam_num());
//		this.cardDealService.saveCardDeal(cardDeal);
//		this.message = "充值成功！";
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
		List<CardSaleWayDTO> listway = (List<CardSaleWayDTO>) JSONArray.toCollection(wayarray,CardSaleWayDTO.class);
		this.model.setCharingWay(listway);
		
		if("Y".equals(this.model.getIsPrintRecepit())){//是否开发票
			UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
			if(userInvoice == null){
				result.setFlag("error");
				result.setInfo("抱歉,您并没有被分配发票号段,您不能打发票!");
			}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
				result.setFlag("error");
				result.setInfo("发票号码超出您的发票段,请重新领票!");
			}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
				result.setFlag("error");
				result.setInfo("发票编号已经存在!");
			}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class(),user.getCenter_num()) != null){
				result.setFlag("error");
				result.setInfo("发票编号已经作废!");
			}else{
				this.model.setInvoice_class(userInvoice.getInvoice_class());
				result =  this.cardInfoService.saveRechargeInfo(this.model,user);
				UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
				userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
				this.userInvoiceService.updateUserInvoice(userinvoiceused);
			}
		}else{
			result = this.cardInfoService.saveRechargeInfo(this.model,user);
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("320");//子功能id 必须填写
		sl.setExplain("卡充值");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 批量制卡 321
	     * @Title: cardBatch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String cardBatch() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setIs_start_card_reader(this.customerInfoService.getCenterconfigByKey("IS_START_CARD_READER", user.getCenter_num()).getConfig_value());
		this.model.setLimit_card_count(1L);
		if("N".equals(this.model.getIs_start_card_reader())){
			return "cardBatch";
		}else{
			return SUCCESS;
		}
	}
	
	/**
	 * 批量制卡保存卡信息 322
	     * @Title: saveCardBatch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardBatch() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String CARD_INFO_PREFIX = this.customerInfoService.getCenterconfigByKey("CARD_INFO_PREFIX",user.getCenter_num()).getConfig_value();
		String s_card_num = this.model.getS_card_num();
		String e_card_num = this.model.getE_card_num();
		if(CARD_INFO_PREFIX != null && "2".equals(CARD_INFO_PREFIX)){
			String pix = this.cardInfoService.getCardInfoPREFIX(this.model.getCard_level());
			s_card_num = pix + DateTimeUtil.getDate() + s_card_num;
			e_card_num = pix + DateTimeUtil.getDate() + e_card_num;
			this.model.setS_card_num(s_card_num);
			this.model.setE_card_num(e_card_num);
		}
		this.message = this.cardInfoService.saveCardBatch(this.model, user,user.getCenter_num());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("322");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 验证物理卡号是否存在 343
	     * @Title: getCardInfoByPhyNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardInfoByPhyNum() throws WebException,SQLException{
		CardInfo card = this.cardInfoService.getCardInfoByPhyNum(this.model.getPhysical_num());
		if(card == null){
			this.message = "ok";
		}else{
			this.message = "no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *  344 个人绑定卡信息  
	     * @Title: getboundCard   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getboundCard() throws WebException {
		CardMemberModel cardMember = this.cardManageService.getCardMemberById(this.model.getMember_id());
		this.model.setUser_name(cardMember.getUser_name());
		this.model.setArch_num(cardMember.getArch_num());
		return SUCCESS;
	}
	
	/**
	 * 345 卡信息管理 读卡
	     * @Title: getReaderCard   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getReaderCard() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * 删除卡信息  736
	     * @Title: deleteCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCardInfo() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.cardManageService.deleteCardInfo(this.model.getCard_num(), user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("736");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 查询删除的卡信息列表 737
	     * @Title: getDeleteCardList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDeleteCardList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto = this.cardInfoService.getDeleteCardList(user,this.model.getStar_date(),this.model.getEnd_date(),this.rows, this.getPage());
		this.outJsonResult(pageDto);
		return NONE;
	}
	
	/**
	 * 查询删除的卡信息页面 738
	     * @Title: getDeleteCardPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDeleteCardPage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 卡信息管理修改页面926
	     * @Title: updateCardPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateCardPage() throws WebException{
		CardInfo card = this.cardInfoService.getCardInfoByCarNum(this.model.getCard_num());
		this.model.setCard_num(card.getCard_num());
		this.model.setCard_pwd(card.getCard_pwd());
		this.model.setAmount(card.getAmount());
		if(card.getDeadline() != null){
			this.model.setDeadline(DateTimeUtil.shortFmt3(card.getDeadline()));
		}
		this.model.setLimit_card_count(card.getLimit_card_count());
		this.model.setCard_level(card.getCard_level());
		this.model.setDiscount(card.getDiscount());
		this.model.setCompany(card.getCompany());
		this.model.setCard_remark(card.getRemark());
		this.model.setSale_amount(card.getSale_amount());
		return SUCCESS;
	}
	/**
	 * 修改卡信息927
	     * @Title: updateCard   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String updateCard() throws WebException{
		this.cardInfoService.updateCard(this.model);
		this.outJsonStrResult("修改成功！");
		return NONE;
	}
	
	/**
	 * 保存发卡信息
	     * @Title: saveHairCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveHairCardInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getHair_card_creater() <= 0){
			this.model.setHair_card_creater(user.getUserid());
		}
		this.message = this.cardInfoService.saveHairCardInfo(this.model.getCard_num(), this.model.getHair_card_creater());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 撤销发卡
	     * @Title: saveHairCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	
	public String delHairCardInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.cardInfoService.delHairCardInfo(this.model.getCard_num(), user.getUserid());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updCardExamSet  批量修改卡信息页面  
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updCardExamSet() throws WebException{
		this.model.setCard_num(this.model.getCard_num());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: updCardExamSetItem    批量修改卡对应套餐-项目信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updCardExamSetItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String cardNumList = this.model.getCard_num();
		if(cardNumList!=null&&!"".equals(cardNumList)) {
			String[] card_numList = cardNumList.split(",");
//			String exam_set = this.model.getExam_set();
			// 获取体检项目
			String itementities = this.model.getItementities();
			itementities = itementities.replace("}{", "},{");
			itementities = "[" + itementities + "]";
			JSONArray itemarray = JSONArray.fromObject(itementities);
			@SuppressWarnings("unchecked")
			List<GroupChargingItemDTO> listitem = (List<GroupChargingItemDTO>) JSONArray.toCollection(itemarray,
					GroupChargingItemDTO.class);

			// 获取体检套餐
			String setentities = model.getSetentities();
			setentities = setentities.replace("}{", "},{");
			setentities = "[" + setentities + "]";
			JSONArray setarray = JSONArray.fromObject(setentities);
			@SuppressWarnings("unchecked")
			List<ExamSetDTO> listset = (List<ExamSetDTO>) JSONArray.toCollection(setarray, ExamSetDTO.class);
			
			for(int i=0;i<card_numList.length;i++) {
				
				String card_num = card_numList[i];
				//修改卡对应套餐信息表
				if(listset.size()==1) {
					List<CardExamSet> cardExamSetDelList = this.cardInfoService.selCardExamSetList(card_num);
					for (CardExamSet cardExamSetdel : cardExamSetDelList) {
						this.cardInfoService.delCardExamSet(cardExamSetdel);
					}
					CardExamSet cardExamSetadd = new CardExamSet();
					ExamSetDTO examSetDTO = listset.get(0);
					cardExamSetadd.setCard_num(card_num);
					cardExamSetadd.setSet_num(examSetDTO.getSet_num());
					cardExamSetadd.setSet_name(examSetDTO.getSet_name());
					cardExamSetadd.setSet_amount(examSetDTO.getSet_amount());
					cardExamSetadd.setSet_discount(examSetDTO.getSet_discount());
					cardExamSetadd.setCreate_time(DateTimeUtil.parse());
					cardExamSetadd.setCreater(user.getUserid());
					cardExamSetadd.setCenter_num(user.getCenter_num());
					this.cardInfoService.saveCardExamSet(cardExamSetadd);
					
					//修改卡绑定套餐状态
					CardInfo cardInfo = this.cardInfoService.getCardInfoByCarNum(card_num);
					if(cardInfo!=null&&cardInfo.getIs_set_status()==0) {
						cardInfo.setIs_set_status(1L);
						this.cardInfoService.updateCardInfo(cardInfo);
					}
					
					//修改卡对应套餐项目信息表
					if(listitem.size()>0) {
						String set_num = examSetDTO.getSet_num();
						List<CardExamSetItem> cardExamSetItemDel = this.cardInfoService.selCardExamSetItemList(card_num);
						for (CardExamSetItem cardExamSetItemdel : cardExamSetItemDel) {
							this.cardInfoService.delCardExamSetItem(cardExamSetItemdel);
						}
						for(int si=0; si<listitem.size();si++) {
							CardExamSetItem cardExamSetItemadd = new CardExamSetItem();
							GroupChargingItemDTO groupChargingItemDTO = listitem.get(si);
							cardExamSetItemadd.setCard_num(card_num);
							cardExamSetItemadd.setSet_num(set_num);
							cardExamSetItemadd.setCharging_item_code(groupChargingItemDTO.getItem_code());
							cardExamSetItemadd.setItemnum(groupChargingItemDTO.getItemnum());
							cardExamSetItemadd.setDiscount(groupChargingItemDTO.getDiscount());
							cardExamSetItemadd.setAmount(groupChargingItemDTO.getAmount());
							cardExamSetItemadd.setItem_amount(groupChargingItemDTO.getItem_amount());
							cardExamSetItemadd.setCreater(user.getUserid());
							cardExamSetItemadd.setCreate_time(DateTimeUtil.parse());
							this.cardInfoService.saveCardExamSetItem(cardExamSetItemadd);
							
						}
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("2119");// 子功能id 必须填写
						syslogService.saveSysLog(sl);
						this.message = "ok";
					}else {
						this.message = "error-无效的项目!";
					}
					
				}else {
					this.message = "error-无效的套餐!";
				}
			}
		}else {
			this.message = "error-请选择卡!";
		}
		
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 1717根据规则生成新卡号
	     * @Title: getAddPrefixCardNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getAddPrefixCardNum() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String CARD_INFO_PREFIX = this.customerInfoService.getCenterconfigByKey("CARD_INFO_PREFIX", user.getCenter_num()).getConfig_value();
		String card_num = this.model.getCard_num();
		if(CARD_INFO_PREFIX != null && "2".equals(CARD_INFO_PREFIX)){
			String pix = this.cardInfoService.getCardInfoPREFIX(this.model.getCard_level());
			card_num = pix + DateTimeUtil.getDate() + card_num;
		}
		this.outJsonStrResult("ok-"+card_num);
		return NONE;
	}
	@Override
	public Object getModel() {
		return model;
	}

	public void setModel(CardInfoModel model) {
		this.model = model;
	}
	public String djtchenkcardnum() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		List<BatchDTO> cardBatchList = null;
		if(this.model.getCard_num()!=""&&this.model.getCard_num()!=null){
			cardBatchList =cardInfoService.getCardBatchlists(this.model.getCard_num(),user.getCenter_num());
		}
		this.outJsonResult(cardBatchList);
//		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("291");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);

		return NONE;
	}
}