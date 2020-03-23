package com.hjw.charge.action;

import java.sql.SQLException;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hjw.charge.DTO.ChargingDetailSingleDTO;
import com.hjw.charge.DTO.ChargingInvoiceSingleDTO;
import com.hjw.charge.DTO.ChargingSummarySingleDTO;
import com.hjw.charge.DTO.ChargingWayDTO;
import com.hjw.charge.DTO.CollectFeesResult;
import com.hjw.charge.DTO.IdentityAuthenticationDTO;
import com.hjw.charge.DTO.InsureAccountDTO;
import com.hjw.charge.DTO.InsureAccountDmDTO;
import com.hjw.charge.DTO.InvoiceInformationDTO;
import com.hjw.charge.DTO.PosTransListDTO;
import com.hjw.charge.DTO.UserInvoiceDTO;
import com.hjw.charge.domain.ChargingInvoiceSingleCharge;
import com.hjw.charge.domain.ChargingSummarySingleCharge;
import com.hjw.charge.domain.InsureAccountCharge;
import com.hjw.charge.domain.UserInvoiceCharge;
import com.hjw.charge.model.CollectFeesModel;
import com.hjw.charge.service.CollectFeesChargeService;
import com.hjw.charge.service.PosTransListChargeService;
import com.hjw.charge.service.UserInvoiceChargeService;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamInfoRecyclingGuidService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

@SuppressWarnings("rawtypes")
public class CollectFeesAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private CollectFeesChargeService collectFeesService;
	private CustomerInfoService customerInfoService;
	private LisPacsApplicationService lisPacsApplicationService;
	private UserInvoiceChargeService userInvoiceService;
	private ExamInfoRecyclingGuidService examInfoRecyclingGuidService;
	private SyslogService syslogService;
	private String webResource_value;
	private String card_reader_code;
	private String card_reader_ocx;
	private PosTransListChargeService posTransListService;
	public void setExamInfoRecyclingGuidService(ExamInfoRecyclingGuidService examInfoRecyclingGuidService) {
		this.examInfoRecyclingGuidService = examInfoRecyclingGuidService;
	}
	
	private CollectFeesModel model = new CollectFeesModel();

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

	public String getWebResource_value() {
		return webResource_value;
	}

	public void setWebResource_value(String webResource_value) {
		this.webResource_value = webResource_value;
	}


	public CollectFeesChargeService getCollectFeesService() {
		return collectFeesService;
	}

	public void setCollectFeesService(CollectFeesChargeService collectFeesService) {
		this.collectFeesService = collectFeesService;
	}

	public PosTransListChargeService getPosTransListService() {
		return posTransListService;
	}

	public void setPosTransListService(PosTransListChargeService posTransListService) {
		this.posTransListService = posTransListService;
	}

	public void setUserInvoiceService(UserInvoiceChargeService userInvoiceService) {
		this.userInvoiceService = userInvoiceService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}


	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	/**
	 * 325 收费页面
	     * @Title: CollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCollectFeesCharge() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS009")){
					this.setWebResource_value(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		
		String isChargingWayZero = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO",user.getCenter_name()).getConfig_value().trim();
		this.model.setIsPrintRecepit(isChargingWayZero);
		String isShowInvoicePage = this.customerInfoService.getCenterconfigByKey("IS_SHOW_INVOICE_PAGE",user.getCenter_name()).getConfig_value().trim();
		this.model.setIsShowInvoicePage(isShowInvoicePage);
		this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE",user.getCenter_name()).getConfig_value().trim());
		this.model.setIsFeesBaodao(this.customerInfoService.getCenterconfigByKey("IS_FEES_BAODAO",user.getCenter_name()).getConfig_value().trim());
		String INVOICE_REPEAT_TYPE = "Y";
		try {
			INVOICE_REPEAT_TYPE = this.customerInfoService.getCenterconfigByKey("INVOICE_REPEAT_TYPE",user.getCenter_name()).getConfig_value().trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.model.setInvoiceRepeatType(INVOICE_REPEAT_TYPE);
		String FEES_MX_POINT = this.customerInfoService.getCenterconfigByKey("FEES_MX_POINT",user.getCenter_name()).getConfig_value();
		if(FEES_MX_POINT != null){
			this.model.setFees_mx_point(FEES_MX_POINT.trim());
		}
		String IS_FEES_MX_POINT_CHECKED = this.customerInfoService.getCenterconfigByKey("IS_FEES_MX_POINT_CHECKED",user.getCenter_name()).getConfig_value();
		if(IS_FEES_MX_POINT_CHECKED != null){
			this.model.setIs_fees_mx_point_checked(IS_FEES_MX_POINT_CHECKED.trim());
		}
		String COLLECT_FEES_WHOLE = this.customerInfoService.getCenterconfigByKey("COLLECT_FEES_WHOLE",user.getCenter_name()).getConfig_value();
		if(COLLECT_FEES_WHOLE != null){
			this.model.setCollect_fees_whole(COLLECT_FEES_WHOLE);
		}
		String IS_REPEAT_INVOICE="1";
		String PAID_PAGE_MODEL = "1";
		try {
			IS_REPEAT_INVOICE = this.customerInfoService.getCenterconfigByKey("IS_REPEAT_INVOICE",user.getCenter_name()).getConfig_value().trim();
			PAID_PAGE_MODEL = this.customerInfoService.getCenterconfigByKey("PAID_PAGE_MODEL",user.getCenter_name()).getConfig_value();
			this.model.setIs_repeat_invoice(IS_REPEAT_INVOICE);
		} catch (Exception e) {
			
		}
		this.model.setCenter_id(user.getUserid()+"");
		this.model.setWork_other_num(user.getWork_other_num());
		this.model.setName(user.getName());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("325");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if(PAID_PAGE_MODEL != null && "2".equals(PAID_PAGE_MODEL.trim())){
			return "success_pos";
		}
		return SUCCESS;
	}
	
	/**
	 * 326 查询未收费项目列表
	     * @Title: getwitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getwitemListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String is_fees_qijian = this.customerInfoService.getCenterconfigByKey("IS_FEES_QIJIAN",user.getCenter_num()).getConfig_value().trim();
		List list = this.collectFeesService.getwitemList(this.model.getExam_num(),is_fees_qijian,user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 327 查询已收费项目列表
	     * @Title: getyitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getyitemListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List list = this.collectFeesService.getyitemList(this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 328 查询人员基本信息
	     * @Title: getCustomerInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCustomerInfoCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String examEndShowItemid = this.customerInfoService.getCenterconfigByKey("EXAM_END_SHOW_ITEMID",user.getCenter_name()).getConfig_value();
		CustomerExamDTO customerInfo = this.collectFeesService.getCustomerInfo(this.model.getExam_num(),user);
		if(customerInfo == null) {
			CollectFeesResult result = new CollectFeesResult();
			result.setFlag("error");
			result.setInfo("体检编号错误，请重新输入!");
			this.outJsonResult(result);
			return NONE;
		}
		if(!"".equals(examEndShowItemid)){
			List<ExamDepResultDTO> list = examInfoRecyclingGuidService.getExamResultList(customerInfo.getExam_num(), examEndShowItemid,user.getCenter_num());
			customerInfo.setExam_dep(list);
		}
		
		
		this.outJsonResult(customerInfo);
		return NONE;
	}
	
	/**
	 * 329 收费保存信息
	     * @Title: saveCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String saveCollectFeesCharge() throws   WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		ExamInfo examinfo = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
		if(!examinfo.getExam_num().equals(model.getExam_num())){
			result.setFlag("error");
			result.setInfo("输入档案号,请先查询!");
		}else{
		
			JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
			List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
			this.model.setExamInfoCharingItem(listitem);
			
			JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
			List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
			this.model.setCharingWay(listway);
			
			JSONArray cardInfo = JSONArray.fromObject(this.model.getCardInfos());
			List<CardInfoDTO> listcard = (List<CardInfoDTO>) JSONArray.toCollection(cardInfo,CardInfoDTO.class);
			this.model.setCardInfo(listcard);
			
			if("Y".equals(this.model.getIsPrintRecepit())){
				UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(this.model.getRcpt_print_flag(),this.model.getBill_type());
				if(userInvoice == null){
					result.setFlag("error");
					result.setInfo("抱歉,您并没有被分配票据号段,您不能打发票!");
				}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
					result.setFlag("error");
					result.setInfo("发票号码超出您的票据段,请重新领票!");
				}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
					result.setFlag("error");
					result.setInfo("票据编号已经存在!");
				}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class(),user.getCenter_num()) != null){
					result.setFlag("error");
					result.setInfo("票据编号已经作废!");
				}else{
					if("2".equals(this.model.getBill_type())){
						//获取 第三方发票接口数据,需要和第三方系统对接
						this.model.setTax_invoices_num("");
					}
					result =  this.collectFeesService.saveCollectFees(this.model,user,userInvoice.getInvoice_class());
					
					if(("00").equals(model.getChargeType())||("01").equals(model.getChargeType())){
					
					}else{
						UserInvoiceCharge userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getRcpt_print_flag(), userInvoice.getInvoice_class(),this.model.getBill_type());
						userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
						userinvoiceused.setBill_type(this.model.getBill_type());
						this.userInvoiceService.updateUserInvoice(userinvoiceused);
					}
					
				}
			}else{
				result = this.collectFeesService.saveCollectFees(this.model,user,"");
			}
			String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE",user.getCenter_name()).getConfig_value().trim();
			String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING",user.getCenter_name()).getConfig_value().trim();
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE",user.getCenter_name()).getConfig_value().trim();
			
			
			if("ok".equals(result.getFlag())){
				try {
					if ("Y".equals(lis) && "N".equals(lis_bang)) {
						String IS_HIS_LIS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_LIS_CHECK",user.getCenter_name()).getConfig_value()
								.trim();
						this.lisPacsApplicationService.lisSend(model.getExam_num(), model.getChargingIds(), user, false,lis_bang,IS_HIS_LIS_CHECK);
					}
				} catch (ServiceException e) {
					result.setFlag("error");
					result.setInfo(e.getMessage());
					e.printStackTrace();
				}
				try {
					if ("Y".equals(pacs)) {
						String IS_HIS_PACS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_PACS_CHECK",user.getCenter_name()).getConfig_value()
								.trim();
						this.lisPacsApplicationService.pacsSend(model.getExam_num(), model.getChargingIds(), user, false,IS_HIS_PACS_CHECK);
					}
				} catch (ServiceException e) {
					
				}	
				//是否启用排队接口
//				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_name()).getConfig_value().trim();
//				if("Y".equals(is_queue_check)){
//					QueueAddBean bean = new QueueAddBean();
//					bean.setExam_id(model.getExam_id());
//					QueueAddToSendMessage message = new QueueAddToSendMessage();
//					QueueResBody rb = message.Send(bean);
//					if("AA".equals(rb.getRescode())){
//							
//					}else{
//						this.message = rb.getRestext();
//					}
//				}
			}
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("329");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonResult(result);
		return NONE;
	}
	

	
	/**
	 * 330  收费显示会员卡页面
	     * @Title: showCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String showCardInfoCharge() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.card_reader_code = this.customerInfoService.getCenterconfigByKey("CARD_READER_INFO",user.getCenter_name()).getConfig_value().trim();
		this.card_reader_ocx = this.customerInfoService.getDiversforByCode(this.card_reader_code).getCom_ocx_name();
		return SUCCESS;
	}
	
	/**
	 * 331 根据体检信息ID查询绑定卡信息
	     * @Title: getCardListByexamId   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
//	public String getCardListByexamIdCharge() throws  WebException,SQLException{
//		List<CardInfoDTO> list = this.collectFeesService.getCardListByexamId(this.model.getExam_id());
//		this.outJsonResult(list);
//		return NONE;
//	}
	
	/**
	 * 332 根据卡号查询卡信息
	     * @Title: getCardInfoByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
//	public String getCardInfoByCardNumCharge() throws  WebException,SQLException{
//		CardInfoDTO cardInfoDTO = this.collectFeesService.getCardInfoByNum(this.model.getCard_num());
//		if(cardInfoDTO == null){
//			this.outJsonStrResult("no");
//		}else{
//			this.outJsonResult(cardInfoDTO);
//		}
//		return NONE;
//	}
	
	/**
	 *333  获取收费记录总表数据
	     * @Title: getChargingSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getChargingSummaryListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingSummarySingleDTO> list = this.collectFeesService.getChargingSummary(this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 334 获取交易明细列表
	     * @Title: getChargingDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getChargingDetailListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingDetailSingleDTO> list = this.collectFeesService.getChargingDetail(this.model.getSummary_id(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 335 获取收费方式明细列表
	     * @Title: getChargintWayList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getChargintWayListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingWayDTO> list = this.collectFeesService.getChargingWay(this.model.getSummary_id(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 336  显示收费明细页面
	     * @Title: showCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String showDetailCollectFeesCharge() throws  WebException{
		return SUCCESS;
	}
	
	/**
	 * 337 显示退费页面
	     * @Title: showCollectFeesRefuncd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String showCollectFeesRefuncdCharge() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		String patNo="";
		String inter_class="";
		List<ChargingSummarySingleDTO> customerList=this.collectFeesService.selectPatno(model.getPeis_trade_code());
		if(customerList!=null&&customerList.size()>0){
			patNo=	customerList.get(0).getMedical_insurance_card();
			inter_class=customerList.get(0).getHealth_type();
		}
		List<InsureAccountDTO> list =collectFeesService.queryWater(model.getPeis_trade_code());
		if(list.size()>0){
			this.model.setMedical_charge_req_num(list.get(0).getMedical_charge_req_num());
			this.model.setMedical_req_num(list.get(0).getMedical_req_num());
//			this.model.setReq_nums(list.get(0).getHis_req_num());
		}
		this.model.setCenter_id(user.getUserid()+"");
		this.model.setWork_other_num(user.getWork_other_num());
		this.model.setName(user.getName());
		this.model.setPatNo(patNo);
		this.model.setInter_class(inter_class);
		List<PosTransListDTO> posTransList= posTransListService.getPeisTradeCode(this.model.getPeis_trade_code());
		if(posTransList.size()>0){
			this.model.setTrade_no(posTransList.get(0).getTrade_no());
			this.model.setVoucher_no(posTransList.get(0).getVoucher_no());
			this.model.setTrade_date(posTransList.get(0).getOriginal_trade_date());
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("337");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 338 显示各个收费方式的缴费金额
	     * @Title: getChargingWayByExamNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingWayByExamNumCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingWayDTO> list = this.collectFeesService.getChargingWayByExamNum(this.model.getExam_num(),this.model.getReq_nums(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 339 退费保存信息
	     * @Title: save_tuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String save_tuifeiCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
		this.model.setExamInfoCharingItem(listitem);
		
		JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
		List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
		this.model.setCharingWay(listway);
		
//		List<CardInfoDTO> list = this.collectFeesService.getCardInfo(this.model.getExam_id());
		
//		if(list.size() != 0){
//			this.message = "存在会员卡消费,不能退费!";
//		}else{
		this.message = this.collectFeesService.saveTuifei(this.model,user);
		//是否启用排队接口
//		String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_name()).getConfig_value().trim();
//		if("Y".equals(is_queue_check)){
//			QueueCustomerBean bean = new QueueCustomerBean();
//			bean.setExam_id(model.getExam_id());
//			QueueCustomSendMessage message = new QueueCustomSendMessage();
//			QueueResBody rb = message.Send(bean);
//			if("AA".equals(rb.getRescode())){
//			}else{
//				this.message = rb.getRestext();
//			}
//		}			
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("339");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
//		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 339 退费保存信息
	     * @Title: save_tuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String save_tuifeiDXCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
		this.model.setExamInfoCharingItem(listitem);
		JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
		List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
		this.model.setCharingWay(listway);
		this.message = this.collectFeesService.saveTuifeiDanXiang(this.model,user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("339");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
//		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	@SuppressWarnings("unchecked")
	public String deleteTuifeiCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
		this.model.setExamInfoCharingItem(listitem);
		this.message = this.collectFeesService.deleteRefund(this.model,user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("339");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 获取最大发票号 355
	     * @Title: getMaxInvoiceNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getMaxInvoiceNumCharge() throws  WebException,SQLException{
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(model.getUser_id(),this.model.getBill_type());
		if(userInvoice != null){
			if(userInvoice.getInvoice_num_used() == null){
				this.message = userInvoice.getInvoice_num_min();
			}else{
				this.message = StringUtil.addOne(userInvoice.getInvoice_num_used());
			}
		}else{
			this.message = "";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 根据退费项目信息获取已开发票信息 356
	     * @Title: getSingleInvoiceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSingleInvoiceListCharge() throws   WebException,SQLException{
		List<ChargingInvoiceSingleDTO> list = this.collectFeesService.getsingleInvoice(this.model.getExamInfoCharingItems(),this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 弹出补打发票页面  357
	     * @Title: chargingSingleInvoickShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String chargingSingleInvoickShowCharge() throws  WebException{
		if("1".equals(this.model.getBill_type())){
			return SUCCESS;//收据
		}else{
			return "success2";//发票
		}
	}
	
	/**
	 * 退费页面弹出补打发票页面  357
	     * @Title: chargingSingleInvoickShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String rechargingSingleInvoickShowCharge() throws  WebException{
		if("1".equals(this.model.getBill_type())){
			return SUCCESS;//收据
		}else{
			return "success2";//发票
		}
	}
	
	/**
	 * 获取需要打印发票的申请单列表 358 
	     * @Title: getChargingSingleInvoickList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingSingleInvoickListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingSummarySingleDTO> list = this.collectFeesService.getChargingSingleInvoickList(this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 补打发票保存信息 359
	     * @Title: saveSingleInviockBu   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveSingleInviockBuCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(this.model.getRcpt_print_flag(),this.model.getBill_type());
		if(userInvoice == null){
			result.setFlag("error");
			result.setInfo("抱歉,您并没有被分配票据号段,您不能打发票");
		}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
			result.setFlag("error");
			result.setInfo("票据号码超出您的发票段,请重新领票");
		}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
			result.setFlag("error");
			result.setInfo("票据编号已经存在");
		}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class(),user.getCenter_num()) != null){
			result.setFlag("error");
			result.setInfo("票据编号已经作废");
		}else{
			result = this.collectFeesService.saveSingleInviockBu(model, user,userInvoice.getInvoice_class());
			UserInvoiceCharge userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getRcpt_print_flag(), userInvoice.getInvoice_class(),this.model.getBill_type());
			userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
			this.userInvoiceService.updateUserInvoice(userinvoiceused);
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("359");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 退费页面 361
	     * @Title: refundFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String refundFeesCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE",user.getCenter_name()).getConfig_value().trim());
		this.model.setCenter_id(user.getUserid()+"");
		this.model.setWork_other_num(user.getWork_other_num());
		this.model.setName(user.getName());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("361");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 查询已开发票项目列表  362
	     * @Title: getykfpItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getykfpItemListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List list = this.collectFeesService.getykfpItemList(this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 查询已开收据列表 363
	     * @Title: getyksjItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getyksjItemListCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List list = this.collectFeesService.getyksjItemList(this.model.getExam_num(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 撤销LIS和PACS申请 382
	     * @Title: delLisAndPacsApplication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delLisAndPacsApplicationCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE",user.getCenter_name()).getConfig_value().trim();
		String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE",user.getCenter_name()).getConfig_value().trim();
		try {
			if("Y".equals(lis)){
				this.message = this.lisPacsApplicationService.lisDel(this.model.getExam_num(), this.model.getExamInfoCharingItems(), user);
			}
			
			if("Y".equals(pacs)){
				this.message = this.lisPacsApplicationService.pacsDel(this.model.getExam_num(), this.model.getExamInfoCharingItems(), user);
			}
			this.message = "ok-申请撤销成功!";
		} catch (ServiceException e) {
			this.message = "error-"+e.getMessage();
			e.printStackTrace();
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("382");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 撤销HIS申请  727
	     * @Title: delHisApplication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delHisApplicationCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String his = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE",user.getCenter_name()).getConfig_value().trim();
		try {
			if("Y".equals(his)){
				String str = this.lisPacsApplicationService.delFeeSend(this.model.getExam_num(), this.model.getChargingIds(), user);
				if(!"no".equals(str)){
					this.message = str;
				}
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("727");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
			}else{
				this.message = "error-未启用HIS申请接口,不需要撤销申请!";
			}
		} catch (ServiceException e) {
			this.message = "error-"+e.getMessage();
			e.printStackTrace();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 健康卡收费页面 739
	     * @Title: getHealthCardChargePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHealthCardChargePageCharge() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("739");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 用户发票号段功能页面 760
	     * @Title: getUserInvoicePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getUserInvoicePageCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(this.model.getRcpt_print_flag(),this.model.getBill_type());
		this.model.setChi_name(user.getName());
		if(userInvoice != null){
			this.model.setInvoice_num_min(userInvoice.getInvoice_num_min());
			this.model.setInvoice_num_max(userInvoice.getInvoice_num_max());
			this.model.setInvoice_num_used(userInvoice.getInvoice_num_used());
			if(userInvoice.getInvoice_num_used() == null){
				this.model.setInvoice_num(userInvoice.getInvoice_num_min());
			}else{
				this.model.setInvoice_num(StringUtil.addOne(userInvoice.getInvoice_num_used()));
			}
			this.model.setInvoice_class(userInvoice.getInvoice_class());
			this.model.setInvoice_classs(userInvoice.getInvoice_classs());
			this.model.setIs_use_all(userInvoice.getIs_use_all());
			this.model.setUser_id(userInvoice.getUser_id());
		}
		return SUCCESS;
	}
	
	/**
	 * 保存用户发票号段信息 761
	     * @Title: saveUserInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserInvoiceCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceCharge userInvoice = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getUser_id(), this.model.getInvoice_class(),this.model.getBill_type());
		if(userInvoice == null){
			userInvoice = new UserInvoiceCharge();
			
			userInvoice.setUser_id(user.getUserid());
			userInvoice.setInvoice_num_min(this.model.getInvoice_num_min());
			userInvoice.setInvoice_num_max(this.model.getInvoice_num_max());
			userInvoice.setCreater(user.getUserid());
			userInvoice.setCreate_time(DateTimeUtil.parse());
			userInvoice.setUpdater(user.getUserid());
			userInvoice.setUpdate_time(DateTimeUtil.parse());
			this.userInvoiceService.saveUserInvoice(userInvoice);
		}else{
			userInvoice.setUser_id(user.getUserid());
			userInvoice.setInvoice_num_min(this.model.getInvoice_num_min());
			userInvoice.setInvoice_num_max(this.model.getInvoice_num_max());
			userInvoice.setInvoice_num_used(null);
			userInvoice.setUpdater(user.getUserid());
			userInvoice.setUpdate_time(DateTimeUtil.parse());
			
			this.userInvoiceService.updateUserInvoice(userInvoice);
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("761");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.message = "保存成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 个人发票作废  763
	     * @Title: invalidInvoiceGe   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String invalidInvoiceGeCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.collectFeesService.invalidInvoiceGe(this.model.getInvoice_num(), user,model.getInvoice_class());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("763");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	} 
	
	/**
	 * 作废无效发票928
	     * @Title: getWuxaiofapiaozuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
    public String getWuxaiofapiaozuifeiCharge() throws  WebException,SQLException{
    	
    	UserDTO user = (UserDTO) session.get("username");    	
    	this.collectFeesService.saveWuxaiofapiaozuifei(model, user);
    	SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("928");//子功能id 必须填写
		sl.setExplain("作废无效发票");//操作说明
		syslogService.saveSysLog(sl);
		String num_used = StringUtil.addOne(model.getInvoice_num_used());
		this.outJsonStrResult("{messager:'发票已作废',num_used:'"+num_used+"',s_num_used:'"+model.getInvoice_num_used()+"'}");
    	return NONE;
    }
    
    
    /**
     * 1198 根据体检号查询需要收费的信息
         * @Title: getMergeChargeByExamNum   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String getMergeChargeByExamNumCharge() throws  WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	ExamInfoUserDTO info = this.collectFeesService.getMergeChargeByExamNum(model.getExam_num(),user.getCenter_num());
    	this.outJsonResult(info);
    	return NONE;
    }
    
  
//	/**
//	 * 995 退费保存信息
//	     * @Title: savePricingRefund   
//	     * @Description: TODO(这里用一句话描述这个方法的作用)   
//	     * @param: @return
//	     * @param: @throws WebException
//	     * @param: @throws SQLException      
//	     * @return: String      
//	     * @throws
//	 */
//	@SuppressWarnings("unchecked")
//	public String savePricingRefundCharge() throws  WebException,SQLException{
//		UserDTO user = (UserDTO) session.get("username");
//		
//		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
//		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
//		this.model.setExamInfoCharingItem(listitem);
//		
//		JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
//		List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
//		this.model.setCharingWay(listway);
//		
//		this.message = this.collectFeesService.savePricingRefund(this.model,user);
//		//是否启用排队接口
//		String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_name()).getConfig_value().trim();
//		if("Y".equals(is_queue_check)){
//			QueueCustomerBean bean = new QueueCustomerBean();
//			bean.setExam_id(model.getExam_id());
//			QueueCustomSendMessage message = new QueueCustomSendMessage();
//			QueueResBody rb = message.Send(bean);
//			if("AA".equals(rb.getRescode())){
//			}else{
//				this.message = rb.getRestext();
//			}
//		}		
//		this.outJsonStrResult(this.message);
//		return NONE;
//	}
//	
//	
	/**
	 * 弹出重打发票页面  357
	     * @Title: repeatInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String repeatInvoiceCharge() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid(),this.model.getBill_type());
		if(userInvoice != null){
			if(userInvoice.getInvoice_num_used() != null){
				this.model.setInvoice_num(userInvoice.getInvoice_num_used());
			}
		}
		if("2".equals(this.model.getBill_type())){
			return "success1";
		}
		return SUCCESS;
	}
	
	/**
	 * <!-- 医保是否关联医保项目-->
	     * @Title: saveRecharge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
//	public  String signAssociatedCharge(){
//		CollectFeesResult result = new CollectFeesResult();
//		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
//		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
//		
//		String unlink_items="" ;
//		result= this.collectFeesService.healthCareAssociation(this.model.getPeis_trade_code(),this.model.getPay_class(),this.model.getNsurance_class(),listitem); 
//		if("error".equals(result.getFlag())){
//			unlink_items=result.getInfo();
//		}else{
//			unlink_items="ok";;
//		}
//		this.outJsonStrResult(unlink_items);
//		return  NONE;
//	}

	public String getPrescriptionCharge() {
		List<JobDTO> list  = collectFeesService.getPrescription();
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 医保登记，医保收费失败入库失败时删除数据
	     * @Title: deleteCharge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteChargeCharge() throws   WebException,SQLException{
			String	msg=collectFeesService.deleteCharge(this.model.getInvoice_num(),this.model.getReq_nums());
			if("ok".equals(msg)){
				this.message = "ok-删除成功！！";
			}else{
				this.message = "error-删除失败！！";
			}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 医保收费成功入库失败时重新入库
	     * @Title: deleteCharge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String putStorageCharge() throws   WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String type=this.collectFeesService.updateCharge(model.getReq_nums(),model.getBill_type(), user.getUserid(),model.getInvoice_num(),model.getTrade_no(),model.getVoucher_no());
			if("0".equals(type)){
				this.message = "ok-入库成功！！";
			}else{
				this.message = "error-入库失败！！";
			}
			if("Y".equals(this.model.getIsPrintRecepit())){
				try {
					//发票收据
					UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(model.getRcpt_print_flag(),this.model.getBill_type());
					UserInvoiceCharge userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getRcpt_print_flag(), userInvoice.getInvoice_class(),this.model.getBill_type());
					userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
					userinvoiceused.setBill_type(this.model.getBill_type());
					this.userInvoiceService.updateUserInvoice(userinvoiceused);	
				} catch (Exception e) {
					this.message = "error-入库失败,发票失败";
				}
			}
			//发申请
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE",user.getCenter_name()).getConfig_value().trim();
			try {
				if ("Y".equals(pacs)) {
					String IS_HIS_PACS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_PACS_CHECK",user.getCenter_name()).getConfig_value()
							.trim();
					this.lisPacsApplicationService.pacsSend(model.getExam_num(), model.getChargingIds(), user, false,IS_HIS_PACS_CHECK);
				}
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 329 医保对照
	     * @Title: saveCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String insuranceComparisonCharge() throws   WebException,SQLException{
		CollectFeesResult result = new CollectFeesResult();
		//医保项目是否对照
			String message = collectFeesService.verifyMedicalItem(model.getExamInfoCharingItems(), model.getInter_class());	
			if("error".equals(message.split("-")[0])){
				result.setFlag("error");
				result.setInfo(message.split("-")[1]);
			}else{
				result.setFlag("ok");
				result.setInfo(message.split("-")[1]);
			}
		this.outJsonResult(result);
		return NONE;
	}
	
	/**
	 * 329 医保对照
	     * @Title: saveCollectFees   查询  InsureAccountDTO
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getInsureAccountCharge() throws   WebException,SQLException{
		CollectFeesResult result = new CollectFeesResult();
		JSONArray itemarray = JSONArray.fromObject("["+this.model.getInsureAccount()+"]");
		List<InsureAccountDmDTO> listitem = (List<InsureAccountDmDTO>) JSONArray.toCollection(itemarray,InsureAccountDmDTO.class);
		UserDTO user = (UserDTO) session.get("username");
		String message = collectFeesService.saveInsureAccount(listitem,this.model,user);
		if(!"ok".equals(message)){
			result.setFlag("error");
		}else{
			List<InsureAccountDTO> list =collectFeesService.queryWater(model.getReq_nums());
			if(list.size()>0&&list!=null){
				double total_amount=list.get(0).getAmount_medical();
				double account_pay=list.get(0).getIndividual_account();
				double cash_pay=list.get(0).getPersonal_cash();
				String req_num=list.get(0).getHis_req_num();
				result.setFlag("ok");
				result.setReq_num(req_num);
				result.setInfo(total_amount+"-"+account_pay+"-"+cash_pay);
			}else{
				result.setFlag("error");
			}
		}
		this.outJsonResult(result);
		return NONE;
	}
	/**
	 * 医保退费成功入库
	     * @Title: deleteCharge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String putRefundCharge() throws   WebException, SQLException {
		if(this.model.getInsureAccount()!=null&&!"".equals(this.model.getInsureAccount())){
			JSONArray itemarrayt = JSONArray.fromObject("["+this.model.getInsureAccount()+"]");
			List<InsureAccountDmDTO> list = (List<InsureAccountDmDTO>) JSONArray.toCollection(itemarrayt,InsureAccountDmDTO.class);
			UserDTO user = (UserDTO) session.get("username");
			String message = collectFeesService.saveInsureAccount(list,this.model,user);
			if(!"ok".equals(message)){
				this.message = "error-入库失败！！";
			}else{	
				JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
				List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
				String type=this.collectFeesService.updateChargeRefund(model.getReq_nums(),listitem,model.getSingleRefund());
				if("0".equals(type)){
					this.message = "ok-入库成功！！";
				}else{
					this.message = "error-入库失败！！";
				}
			}
			this.outJsonStrResult(this.message);
		}else{
			JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
			List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
			String type=this.collectFeesService.updateChargeRefund(model.getReq_nums(),listitem,model.getSingleRefund());
			if("0".equals(type)){
				this.message = "ok-入库成功！！";
			}else{
				this.message = "error-入库失败！！";
			}
			this.outJsonStrResult(this.message);
		}
		return NONE;
	}
	
	/**
	 * 更新发票信息
	     * @Title: setChargingInvoiceInfonumber   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String setChargingInvoiceInfonumberCharge() throws   WebException {
		if(this.model.getInvoice_id()>0){
			collectFeesService.updateCIS(model.getInvoice_id(),this.model.getInvoice_num(),this.model.getInvoice_infoNumber());
			}
			return NONE;
	}
	
	/**
	 * 通过申请单号获取开票金额
	     * @Title: getamtForAccountNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getamtForCISAccountnumCharge() throws  WebException, SQLException {
		ChargingInvoiceSingleCharge cs= new ChargingInvoiceSingleCharge();
		message="0#0#0#0#0#0";
		cs=this.collectFeesService.getCISForAccountnum(this.model.getAccount_num());
		if(cs!=null&& (!StringUtil.nullOrBlank(cs.getAccount_num()))){
			message=cs.getAccount_num()+"#"+cs.getId()+"#"+cs.getInvoice_amount()+"#"+cs.getInvoice_num()+"#"+cs.getTax_invoices_num()+"#"+cs.getBill_type();
		}else{
			message="error";
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 通过申请单号获取开票金额
	     * @Title: getamtForAccountNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getamtForCHARGSSReqNumCharge() throws  WebException, SQLException {
		ChargingSummarySingleCharge cs= new ChargingSummarySingleCharge();
		message="0#0#0";
		cs=this.collectFeesService.getChargingSummarySingle(this.model.getReq_nums());
		if(cs!=null&& (!StringUtil.nullOrBlank(cs.getReq_num()))){
			message=cs.getReq_num()+"#"+cs.getInvoice_id()+"#"+cs.getAmount2();
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 339 读卡后保存医保卡信息
	     * @Title: save_tuifei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String save_insuranceCharge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
//		JSONArray itemarray = JSONArray.fromObject(this.model.getIdentityAuthentications());
//		List<IdentityAuthenticationDTO> listitem = (List<IdentityAuthenticationDTO>) JSONArray.toCollection(itemarray,IdentityAuthenticationDTO.class);
		this.message = this.collectFeesService.saveInsurance(this.model,user);
		//是否启用排队接口
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("339");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
//		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 增加开票公司信息
	     * @Title: setChargingInvoiceInfonumber   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String billingCompanyInformationCharge() throws   WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.collectFeesService.saveCompanyInformation(this.model,user);
		//是否启用排队接口
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("339");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 1110 查询开票公司信息
	     * @Title: getCashierList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getInformationCharge() throws  WebException{
		List<InvoiceInformationDTO> list = this.collectFeesService.getInformation(this.model.getInfoClientName());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 写日志
	     * @Title: InsuranceLog_insert   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String insertInsuranceLogCharge()throws WebException{
		TranLogTxt.liswriteEror_to_txt(this.model.getLogFileName(),this.model.getLogData());
		return NONE;
	}
	
	/**
	 * 1110 查询开票公司信息
	     * @Title: getCashierList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String save_uploadDetailCharge() throws  WebException{
		List<String> list = this.collectFeesService.uploadDetail(this.model.getReq_nums(),this.model.getPrescription_name(),this.model.getPrescription_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 医保手动退费2221
	     * @Title: chargingSingleInvoickShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHealthCareManual() throws  WebException{
			return SUCCESS;
	}
	@Override
	public Object getModel() {
		return model;
	}

	public void setModel(CollectFeesModel model) {
		this.model = model;
	}
}
