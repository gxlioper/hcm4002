package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CardSaleDetailDTO;
import com.hjw.wst.DTO.CardSaleWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInvoiceDTO;
import com.hjw.wst.domain.CardInfo;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.model.CardSaleModel;
import com.hjw.wst.service.CardInfoService;
import com.hjw.wst.service.CardSaleService;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInvoiceService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import org.apache.commons.lang3.StringUtils;

/**
 * 卡销售管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dq     
     * @date:   2019年4月1日 下午10:03:01   
     * @version V2.0.0.0
 */
public class CardSaleAction extends BaseAction implements ModelDriven {

	private CardSaleModel model = new CardSaleModel();
	private CardSaleService cardSaleService;
	private CardInfoService cardInfoService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	private UserInvoiceService userInvoiceService;
	private CollectFeesService collectFeesService;
	private int rows = 15; // easyui每页显示条数

	public void setCardSaleService(CardSaleService cardSaleService) {
		this.cardSaleService = cardSaleService;
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
	
	public void setCardInfoService(CardInfoService cardInfoService) {
		this.cardInfoService = cardInfoService;
	}
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public CardSaleModel getModel() {
		return model;
	}

	public void setModel(CardSaleModel model) {
		this.model = model;
	}
	
	/**
	 * 1746卡销售主页面
	     * @Title: cardSaleManagePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cardSaleManagePage() throws WebException, SQLException {
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
		String INVOICE_REPEAT_TYPE = "Y";
		try {
			INVOICE_REPEAT_TYPE = this.customerInfoService.getCenterconfigByKey("INVOICE_REPEAT_TYPE", user.getCenter_num()).getConfig_value().trim();
		}catch (Exception e) {
			// TODO: handle exception
		}
		this.model.setInvoiceRepeatType(INVOICE_REPEAT_TYPE);
		this.model.setInvoiceprinttype(invoiceprinttype.trim());
		this.model.setStart_date(DateTimeUtil.getDate2());
		this.model.setEnd_date(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 * 1747根据条件查询未销售卡列表
	     * @Title: getNotSaleCardInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getNotSaleCardInfoList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO pageDto = this.cardSaleService.getNotSaleCardInfoList(this.model.getCard_num(),this.model.getCard_num_s(),
				this.model.getCard_num_e(),this.model.getStart_date(),this.model.getEnd_date(),this.model.getCompany(),this.rows, this.getPage(),user);
		this.outJsonResult(pageDto);
		return NONE;
	}
	
	/**
	 * 1748保存售卡或预售卡信息
	     * @Title: saveCardSaleInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardSaleInfo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		JSONArray cardsaledetailarray = JSONArray.fromObject(this.model.getCardSaleDetails());
		List<CardSaleDetailDTO> listcarddetail = (List<CardSaleDetailDTO>) JSONArray.toCollection(cardsaledetailarray,CardSaleDetailDTO.class);
		this.model.setCardSaleDetail(listcarddetail);
		
		if(this.model.getSale_status() == 1){
			JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
			List<CardSaleWayDTO> listway = (List<CardSaleWayDTO>) JSONArray.toCollection(wayarray,CardSaleWayDTO.class);
			this.model.setCharingWay(listway);
		}else{
			this.model.setCharingWay(new ArrayList<CardSaleWayDTO>());
		}
		
		if("Y".equals(this.model.getIsPrintRecepit())){
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
				result =  this.cardSaleService.saveCardSaleInfo(this.model,user);
				UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
				userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
				this.userInvoiceService.updateUserInvoice(userinvoiceused);
			}
		}else{
			result =this.cardSaleService.saveCardSaleInfo(this.model,user);
		}
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 保存预售卡信息
	     * @Title: saveAdvCardSaleInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveAdvCardSaleInfo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		JSONArray cardsaledetailarray = JSONArray.fromObject(this.model.getCardSaleDetails());
		List<CardSaleDetailDTO> listcarddetail = (List<CardSaleDetailDTO>) JSONArray.toCollection(cardsaledetailarray,CardSaleDetailDTO.class);
		this.model.setCardSaleDetail(listcarddetail);
		
		if(this.model.getSale_status() == 1){
			JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
			List<CardSaleWayDTO> listway = (List<CardSaleWayDTO>) JSONArray.toCollection(wayarray,CardSaleWayDTO.class);
			this.model.setCharingWay(listway);
		}else{
			this.model.setCharingWay(new ArrayList<CardSaleWayDTO>());
		}
		
//		if("Y".equals(this.model.getIsPrintRecepit())){
//			UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
//			if(userInvoice == null){
//				result.setFlag("error");
//				result.setInfo("抱歉,您并没有被分配发票号段,您不能打发票!");
//			}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
//				result.setFlag("error");
//				result.setInfo("发票号码超出您的发票段,请重新领票!");
//			}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
//				result.setFlag("error");
//				result.setInfo("发票编号已经存在!");
//			}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class()) != null){
//				result.setFlag("error");
//				result.setInfo("发票编号已经作废!");
//			}else{
//				this.model.setInvoice_class(userInvoice.getInvoice_class());
//				result =  this.cardSaleService.saveCardSaleInfo(this.model,user);
//				UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
//				userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
//				this.userInvoiceService.updateUserInvoice(userinvoiceused);
//			}
//		}else{
			result =this.cardSaleService.saveAdvCardSaleInfo(this.model,user);
//		}
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 1749预售信息确认售卡
	     * @Title: saveAdvanceCardSale   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveAdvanceCardSale() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		JSONArray cardsaledetailarray = JSONArray.fromObject(this.model.getCardSaleDetails());
		List<CardSaleDetailDTO> listcarddetail = (List<CardSaleDetailDTO>) JSONArray.toCollection(cardsaledetailarray,CardSaleDetailDTO.class);
		this.model.setCardSaleDetail(listcarddetail);
		
		if(this.model.getSale_status() == 1){
			JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
			List<CardSaleWayDTO> listway = (List<CardSaleWayDTO>) JSONArray.toCollection(wayarray,CardSaleWayDTO.class);
			this.model.setCharingWay(listway);
		}else{
			this.model.setCharingWay(new ArrayList<CardSaleWayDTO>());
		}
		
		if("Y".equals(this.model.getIsPrintRecepit())){
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
				result =  this.cardSaleService.saveAdvanceCardSale(this.model,user);
				UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
				userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
				this.userInvoiceService.updateUserInvoice(userinvoiceused);
			}
		}else{
			result =this.cardSaleService.saveAdvanceCardSale(this.model,user);
		}
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 1750获取已售卡或预售卡信息列表
	     * @Title: getCardSaleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardSaleList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO pageDto = this.cardSaleService.getCardSaleList(this.model.getSale_status(),this.model.getStart_date(),
				this.model.getEnd_date(),this.model.getSale_trade_num(),this.model.getIsPrintRecepit(),this.rows, this.getPage(),user);
		this.outJsonResult(pageDto);
		return NONE;
	}
	
	/**
	 * 1751 根据售卡流水号查询卡明细列表
	     * @Title: getCardSaleDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardSaleDetailList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<CardInfoDTO> list = this.cardSaleService.getCardSaleDetailList(model.getSale_trade_num(),user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1752售卡信息补开发票
	     * @Title: saveCardSaleInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardSaleInvoice() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		if(userInvoice == null){
			result.setFlag("error");
			result.setInfo("抱歉,您并没有被分配发票号段,您不能打发票");
		}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
			result.setFlag("error");
			result.setInfo("发票号码超出您的发票段,请重新领票");
		}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
			result.setFlag("error");
			result.setInfo("发票编号已经存在");
		}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class(),user.getCenter_num()) != null){
			result.setFlag("error");
			result.setInfo("发票编号已经作废");
		}else{
			model.setInvoice_class(userInvoice.getInvoice_class());
			result = this.cardSaleService.saveCardSaleInvoice(model, user);
			UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
			userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
			this.userInvoiceService.updateUserInvoice(userinvoiceused);
		}
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 1753售卡信息作废发票
	     * @Title: invalidCardSaleInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String invalidCardSaleInvoice() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.cardSaleService.invalidCardSaleInvoice(model.getSale_trade_num(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 获取单位信息列表
	     * @Title: getCompanyList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCompanyList() throws WebException, SQLException {
		List<CompanyInfo> compantList = this.cardSaleService.getCompanyList();
		String jsonString = JSONSerializer.toJSON(compantList).toString();
		this.outJsonStrResult(jsonString);
		return NONE;
	}
	
	/**
	 * 售卡流水绑定单位
	     * @Title: bindCompany   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String bindCompany() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String saleTradeNum = this.model.getSale_trade_num();
		String[] saleTradeNums = saleTradeNum.split(",");
		String company_id = this.model.getCompany_id();
		String company = this.model.getCompany();
		if(saleTradeNums.length>0) {
			if(!"".equals(company_id)&&company_id!=null) {
				for(int i=0;i<saleTradeNums.length;i++) {
					String sale_trade_num = saleTradeNums[i];
					List<CardInfoDTO> cardInfoList = this.cardSaleService.getCardCompany(sale_trade_num);
					if(cardInfoList.size()>0) {
						for (CardInfoDTO cardInfoDTO : cardInfoList) {
							CardInfo cardInfo = this.cardInfoService.getCardInfoById(cardInfoDTO.getId());
							if(cardInfo!=null) {
								cardInfo.setCompany_id(Long.parseLong(company_id));
								cardInfo.setCompany(company);
								cardInfo.setUpdater(user.getUserid());
								cardInfo.setUpdate_time(DateTimeUtil.parse());
								this.cardInfoService.updateCardInfo(cardInfo);
							}
						
						}
					}
				}
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("");//子功能id 必须填写
				sl.setExplain("修改卡绑定单位信息");//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok";
			}else {
				this.message = "error-请选择单位！";
			}
		}else {
			this.message = "error-请选择需要操作的信息！";
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 根据售卡流水号查询卡明细列表
	     * @Title: getCardSaleDetailList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardSaleDetailList2() throws WebException, SQLException {
		String saleTradeNum = this.model.getSale_trade_num();
		String saleTradeNums  = "'"+saleTradeNum.replace(",", "','")+"'";
		List<CardInfoDTO> list = this.cardSaleService.getCardSaleDetailList2(saleTradeNums);
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 售卡流水绑定单位 校验是否重复绑定
	 * @Title: isCheckBindCompany
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException
	 * @return: String
	 * @throws
	 */
	public String isCheckBindCompany(){
		if(!StringUtils.isEmpty(this.model.getSale_trade_num()) && this.model.getSale_trade_num().length() > 0){
			String[] split = this.model.getSale_trade_num().split(",");
			for (String sale_trade_num:split) {
				List<CardInfoDTO> cardInfoList = this.cardSaleService.getCardCompany(sale_trade_num);
				if(cardInfoList.size() > 0){
					for (CardInfoDTO c: cardInfoList ) {
						if(c.getCompany_id() > 0){// 已经绑定过单位
							this.outJsonStrResult("error-流水号："+sale_trade_num+" 已经绑定过单位。。");
							return NONE;
						}
					}
				}
			}
		}
		this.outJsonStrResult("ok-成功");
		return NONE;
	}
}
