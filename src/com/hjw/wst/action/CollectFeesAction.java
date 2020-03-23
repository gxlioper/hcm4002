package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.webService.client.QueueAddToSendMessage;
import com.hjw.webService.client.QueueCustomSendMessage;
import com.hjw.webService.client.Bean.QueueAddBean;
import com.hjw.webService.client.Bean.QueueCustomerBean;
import com.hjw.webService.client.body.QueueResBody;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.ChargingDetailSingleDTO;
import com.hjw.wst.DTO.ChargingInvoiceSingleDTO;
import com.hjw.wst.DTO.ChargingSummarySingleDTO;
import com.hjw.wst.DTO.ChargingWayDTO;
import com.hjw.wst.DTO.CollectFeesResult;
import com.hjw.wst.DTO.CompanyAccountDTO;
import com.hjw.wst.DTO.CompanyAccountDetailDTO;
import com.hjw.wst.DTO.CustomerExamDTO;
import com.hjw.wst.DTO.ExamDepResultDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamResultChargingItemDTO;
import com.hjw.wst.DTO.MenuDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInvoiceDTO;
import com.hjw.wst.domain.ChargingDetailSingle;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.UserInvoice;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.CollectFeesModel;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamInfoRecyclingGuidService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInvoiceService;
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
	private CollectFeesService collectFeesService;
	private CustomerInfoService customerInfoService;
	private LisPacsApplicationService lisPacsApplicationService;
	private UserInvoiceService userInvoiceService;
	private SyslogService syslogService;
	private ExamInfoRecyclingGuidService examInfoRecyclingGuidService;
	private String webResource_value;
	private String card_reader_code;
	private String card_reader_ocx;
	
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

	public void setCollectFeesService(CollectFeesService collectFeesService) {
		this.collectFeesService = collectFeesService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}

	public void setUserInvoiceService(UserInvoiceService userInvoiceService) {
		this.userInvoiceService = userInvoiceService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setExamInfoRecyclingGuidService(ExamInfoRecyclingGuidService examInfoRecyclingGuidService) {
		this.examInfoRecyclingGuidService = examInfoRecyclingGuidService;
	}

	/**
	 * 325 收费页面
	     * @Title: CollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCollectFees() throws WebException{
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
		
		String isChargingWayZero = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO",user.getCenter_num()).getConfig_value().trim();
		this.model.setIsPrintRecepit(isChargingWayZero);
		String isShowInvoicePage = this.customerInfoService.getCenterconfigByKey("IS_SHOW_INVOICE_PAGE",user.getCenter_num()).getConfig_value().trim();
		this.model.setIsShowInvoicePage(isShowInvoicePage);
		this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE",user.getCenter_num()).getConfig_value().trim());
		this.model.setIsFeesBaodao(this.customerInfoService.getCenterconfigByKey("IS_FEES_BAODAO",user.getCenter_num()).getConfig_value().trim());
		String INVOICE_REPEAT_TYPE = "Y";
		try {
			INVOICE_REPEAT_TYPE = this.customerInfoService.getCenterconfigByKey("INVOICE_REPEAT_TYPE",user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
			// TODO: handle exception
		}
		this.model.setInvoiceRepeatType(INVOICE_REPEAT_TYPE);
		String FEES_MX_POINT = this.customerInfoService.getCenterconfigByKey("FEES_MX_POINT",user.getCenter_num()).getConfig_value();
		if(FEES_MX_POINT != null){
			this.model.setFees_mx_point(FEES_MX_POINT.trim());
		}
		String IS_FEES_MX_POINT_CHECKED = this.customerInfoService.getCenterconfigByKey("IS_FEES_MX_POINT_CHECKED",user.getCenter_num()).getConfig_value();
		if(IS_FEES_MX_POINT_CHECKED != null){
			this.model.setIs_fees_mx_point_checked(IS_FEES_MX_POINT_CHECKED.trim());
		}
		String COLLECT_FEES_WHOLE = this.customerInfoService.getCenterconfigByKey("COLLECT_FEES_WHOLE",user.getCenter_num()).getConfig_value();
		if(COLLECT_FEES_WHOLE != null){
			this.model.setCollect_fees_whole(COLLECT_FEES_WHOLE);
		}
		String IS_REPEAT_INVOICE="1";
		try {
			IS_REPEAT_INVOICE = this.customerInfoService.getCenterconfigByKey("IS_REPEAT_INVOICE",user.getCenter_num()).getConfig_value().trim();
			this.model.setIs_repeat_invoice(IS_REPEAT_INVOICE);
		} catch (Exception e) {
		}
		String charge ="1";
		try {
			charge = this.customerInfoService.getCenterconfigByKey("IS_MEDICAL_INSURANCE_CHARGING",user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("325");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if("2".equals(charge)){
			return "success_charge";
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
	public String getwitemList() throws WebException,SQLException{
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
	public String getyitemList() throws WebException,SQLException{
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
	public String getCustomerInfo() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String examEndShowItemid = this.customerInfoService.getCenterconfigByKey("EXAM_END_SHOW_ITEMID",user.getCenter_num()).getConfig_value();
		CustomerExamDTO customerInfo = this.collectFeesService.getCustomerInfo(this.model.getExam_num(),user);
		if(customerInfo == null) {
			CollectFeesResult result = new CollectFeesResult();
			result.setFlag("error");
			result.setInfo("体检编号错误，请重新输入!");
			this.outJsonResult(result);
			return NONE;
		}
		if(!"".equals(examEndShowItemid)){
			List<ExamDepResultDTO> list = this.examInfoRecyclingGuidService.getExamResultList(customerInfo.getExam_num(), examEndShowItemid, user.getCenter_num());
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
	public String saveCollectFees()throws WebException,SQLException{
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
					result =  this.collectFeesService.saveCollectFees(this.model,user,userInvoice.getInvoice_class());
					UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
					userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
					this.userInvoiceService.updateUserInvoice(userinvoiceused);
				}
			}else{
				result = this.collectFeesService.saveCollectFees(this.model,user,"");
			}
			String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
			String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING",user.getCenter_num()).getConfig_value().trim();
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
			
			
			if("ok".equals(result.getFlag())){
				try {
					if ("Y".equals(lis) && "N".equals(lis_bang)) {
						String IS_HIS_LIS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_LIS_CHECK",user.getCenter_num()).getConfig_value()
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
						String IS_HIS_PACS_CHECK = this.customerInfoService.getCenterconfigByKey("IS_HIS_PACS_CHECK",user.getCenter_num()).getConfig_value()
								.trim();
						this.lisPacsApplicationService.pacsSend(model.getExam_num(), model.getChargingIds(), user, false,IS_HIS_PACS_CHECK);
					}
				} catch (ServiceException e) {
					result.setFlag("error");
					result.setInfo(e.getMessage());
					e.printStackTrace();
				}	
				//是否启用排队接口
				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_num()).getConfig_value().trim();
				if("Y".equals(is_queue_check)){
					QueueAddBean bean = new QueueAddBean();
					bean.setCenter_num(user.getCenter_num());
					bean.setExam_num(model.getExam_num());
					QueueAddToSendMessage message = new QueueAddToSendMessage();
					QueueResBody rb = message.Send(bean);
					if("AA".equals(rb.getRescode())){
							
					}else{
						this.message = rb.getRestext();
					}
				}
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
	 * 329 结算添加订单
	     * @Title: saveCollectFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String settlement()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		ExamInfo examinfo = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
		if(!examinfo.getExam_num().equals(model.getExam_num())){
			result.setFlag("error");
			result.setInfo("输入档案号,请先查询!");
		}else{
			this.collectFeesService.delSummaryId(model.getExam_id());
			
			JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
			List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
			this.model.setExamInfoCharingItem(listitem);
			
			JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
			List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
			this.model.setCharingWay(listway);
			
			JSONArray cardInfo = JSONArray.fromObject(this.model.getCardInfos());
			List<CardInfoDTO> listcard = (List<CardInfoDTO>) JSONArray.toCollection(cardInfo,CardInfoDTO.class);
			this.model.setCardInfo(listcard);
			
//			if("Y".equals(this.model.getIsPrintRecepit())){
//				UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
//				result =  this.collectFeesService.saveSettlements(this.model,user,userInvoice.getInvoice_class());
//			}else{
				result = this.collectFeesService.saveSettlements(this.model,user,"");
//			}
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
	 * 弹出结算列表
	     * @Title: chargingSingleInvoickShow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String settlementShow() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 获取订单中收费项目列表 
	     * @Title: getChargingSingleInvoickList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
//	public String getChargingSingleSettlement() throws WebException,SQLException{
//		List<ChargingDetailSingleDTO>  list = this.collectFeesService.getsettlementList(this.model.getSummary_id());
//		this.outJsonResult(list);
//		return NONE;
//	}
	
	/**
	 * 330  收费显示会员卡页面
	     * @Title: showCardInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String showCardInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.card_reader_code = this.customerInfoService.getCenterconfigByKey("CARD_READER_INFO",user.getCenter_num()).getConfig_value().trim();
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
	public String getCardListByexamId() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<CardInfoDTO> list = this.collectFeesService.getCardListByexamId(this.model.getExam_id(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 332 根据卡号查询卡信息
	     * @Title: getCardInfoByNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCardInfoByCardNum() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		CardInfoDTO cardInfoDTO = this.collectFeesService.getCardInfoByNum(this.model.getCard_num(),user);
		if(cardInfoDTO == null){
			this.outJsonStrResult("no");
		}else{
			this.outJsonResult(cardInfoDTO);
		}
		return NONE;
	}
	
	/**
	 *333  获取收费记录总表数据
	     * @Title: getChargingSummary   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getChargingSummaryList() throws WebException,SQLException{
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
	public String getChargingDetailList() throws WebException,SQLException{
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
	public String getChargintWayList() throws WebException,SQLException{
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
	public String showDetailCollectFees() throws WebException{
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
	public String showCollectFeesRefuncd() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
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
	public String getChargingWayByExamNum() throws WebException,SQLException{
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
	public String save_tuifei() throws WebException,SQLException{
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
		String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_num()).getConfig_value().trim();
		if("Y".equals(is_queue_check)){
			QueueCustomerBean bean = new QueueCustomerBean();
			bean.setCenter_num(user.getCenter_num());
			bean.setExam_num(model.getExam_num());
			QueueCustomSendMessage message = new QueueCustomSendMessage();
			QueueResBody rb = message.Send(bean);
			if("AA".equals(rb.getRescode())){
			}else{
				this.message = rb.getRestext();
			}
		}			
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
	 * 获取最大发票号 355
	     * @Title: getMaxInvoiceNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getMaxInvoiceNum() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
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
	public String getSingleInvoiceList() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingInvoiceSingleDTO> list = this.collectFeesService.getsingleInvoice(this.model.getExamInfoCharingItems(),this.model.getExam_num(),user.getCenter_num());
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
	public String chargingSingleInvoickShow() throws WebException{
		return SUCCESS;
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
	public String getChargingSingleInvoickList() throws WebException,SQLException{
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
	public String saveSingleInviockBu() throws WebException,SQLException{
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
			result = this.collectFeesService.saveSingleInviockBu(model, user,userInvoice.getInvoice_class());
			UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
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
	 * 	
	     * @Title: updateSingleInviockTT   
	     * @Description: 团体发票更新信息 812   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateSingleInviockTT() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		MenuDTO m = new MenuDTO();
		if(userInvoice == null){
			m.setValue("error");
			m.setText("抱歉,您并没有被分配发票号段,您不能打发票");
		}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
			m.setValue("error");
			m.setText("发票号码超出您的发票段,请重新领票");
		}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
			m.setText("发票编号已经存在");
			m.setValue("error");
		}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class(),user.getCenter_num()) != null){
			this.message = "error-发票编号已经作废";
		}else if(this.model.getBatchid()<=0){
			m.setValue("error");
			m.setText("批次任务编号不存在");
		}else if(this.model.getId()<=0){
			m.setValue("error");
			m.setText("无效结算单号");
		}else{
			m = this.collectFeesService.updateSingleInviockTT(this.model.getBatchid(),this.model.getId(),this.model.getInvoice_num(),this.model.getTitle_info(),this.model.getInvoice_type(),this.model.getAmount2(),userInvoice.getInvoice_class(), user.getUserid(),user.getCenter_num());
            if("ok".equals(m.getValue())){
            	UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
				userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
				this.userInvoiceService.updateUserInvoice(userinvoiceused);
            } 
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("803");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonResult(m);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: delSingleInviockTT   
	     * @Description: 团体发票作废操作 804 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delSingleInviockTT() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getBatchid()<=0){
			this.message = "error-抱歉,批次任务不能为空";
		}else if(this.model.getInvoice_id()<=0){
			this.message = "error-抱歉,发票号不能为空";
		}else{
			this.message=this.collectFeesService.delSingleInviockTT(this.model.getBatchid(),model.getInvoice_id(), user.getUserid(),user.getCenter_num());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("804");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: delrecordSingleInviockTT   
	     * @Description: 团体发票记录删除操作 804 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	/*public String delrecordSingleInviockTT() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		//UserInvoice userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		if((this.model.getAccount_num() == null)||(this.model.getAccount_num().length()<=0)){
			this.message = "error-抱歉,发票记录号不能为空";
		}else{
			this.message=this.collectFeesService.delrecordSingleInviockTT(model.getAccount_num(), user.getUserid());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("821");//子功能id 必须填写
			sl.setExplain("删除发票记录编号："+this.model.getAccount_num());//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}*/
	
	/**
	 * 发送缴费申请  360
	     * @Title: paymentApplication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String paymentApplication() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_HIS_INTERFACE = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
		if("N".equals(IS_HIS_INTERFACE)){
			this.message = "error-未启用HIS接口,不能发送缴费申请!";
		}else{
			
			try {
				this.message = this.collectFeesService.paymentApplication(this.model.getExam_num(), user);
			} catch (ServiceException e) {
				this.message = "error-"+e.getMessage();
				e.printStackTrace();
			}
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("360");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
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
	public String refundFees() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE",user.getCenter_num()).getConfig_value().trim());
		String charge ="1";
		try {
			charge = this.customerInfoService.getCenterconfigByKey("IS_MEDICAL_INSURANCE_CHARGING",user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("361");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if("2".equals(charge)){
			return "success_charge";
		}
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
	public String getykfpItemList() throws WebException,SQLException{
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
	public String getyksjItemList() throws WebException,SQLException{
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
	public String delLisAndPacsApplication() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
		String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
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
	public String delHisApplication() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String his = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
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
	public String getHealthCardChargePage() throws WebException{
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
	public String getUserInvoicePage() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
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
	public String saveUserInvoice() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoice userInvoice = this.userInvoiceService.getUserInvoiceByUserIdAndClass(this.model.getUser_id(), this.model.getInvoice_class());
		if(userInvoice == null){
			userInvoice = new UserInvoice();
			
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
	public String invalidInvoiceGe() throws WebException,SQLException{
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
	 * 个人撤销收费 766
	     * @Title: chexiaoshoufei   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String chexiaoshoufei() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.collectFeesService.chexiaoshoufei(model, user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("766");//子功能id 必须填写
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
    public String getWuxaiofapiaozuifei() throws WebException,SQLException{
    	
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
     * 1197 合并收费功能页（浙医2）
         * @Title: getMergeChargeZy2   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String getMergeChargeZy2() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE",user.getCenter_num()).getConfig_value().trim());
    	return SUCCESS;
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
    public String getMergeChargeByExamNum() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
    	ExamInfoUserDTO info = this.collectFeesService.getMergeChargeByExamNum(model.getExam_num(), user.getCenter_num());
    	this.outJsonResult(info);
    	return NONE;
    }
    
    /**
     * 1199 保存合并收费信息
         * @Title: saveMergeChargeInfo   
         * @Description: TODO(这里用一句话描述这个方法的作用)   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String saveMergeChargeInfo() throws WebException,SQLException{
    	UserDTO user = (UserDTO) session.get("username");
		CollectFeesResult result = new CollectFeesResult();
		JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
		List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
		this.model.setCharingWay(listway);
		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
		this.model.setExamInfoCharingItem(listitem);
			
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
				result =  this.collectFeesService.saveMergeChargeInfo(this.model,user,userInvoice.getInvoice_class());
				UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
				userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
				this.userInvoiceService.updateUserInvoice(userinvoiceused);
			}
		}else{
			result = this.collectFeesService.saveMergeChargeInfo(this.model,user,"");
		}
			
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1199");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(result);
		return NONE;
    }
    
	 /**
     * 991
         * @Title: getPricing   
         * @Description: TODO(划价页面 )   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
	public String getPricing(){
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
		
		String isChargingWayZero = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO",user.getCenter_num()).getConfig_value().trim();
		this.model.setIsPrintRecepit(isChargingWayZero);
		String isShowInvoicePage = this.customerInfoService.getCenterconfigByKey("IS_SHOW_INVOICE_PAGE",user.getCenter_num()).getConfig_value().trim();
		this.model.setIsShowInvoicePage(isShowInvoicePage);
		this.model.setInvoiceprinttype(this.customerInfoService.getCenterconfigByKey("INVOICE_PRINT_TYPE",user.getCenter_num()).getConfig_value().trim());
		this.model.setIsFeesBaodao(this.customerInfoService.getCenterconfigByKey("IS_FEES_BAODAO",user.getCenter_num()).getConfig_value().trim());
		return  SUCCESS;
	}
	
	 /**
     * 992
         * @Title: getPricing   
         * @Description: TODO(体检划价 )   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
	public  String savePricing(){
		UserDTO user = (UserDTO) session.get("username");
		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
		this.model.setExamInfoCharingItem(listitem);
		this.message =  this.collectFeesService.savePricing(this.model,user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
     * 993
         * @Title: cancel_pricing   
         * @Description: TODO(取消划价)   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
	public  String cancel_pricing(){
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.collectFeesService.cancel_pricing(this.model,user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
     * 994  划价 收费
         * @Title: cancel_pricing   
         * @Description: TODO(取消划价)   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
	public  String  savePricingCollectFees(){

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
					result =  this.collectFeesService.savePricingCollectFees(this.model,user,userInvoice.getInvoice_class());
					UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
					userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
					this.userInvoiceService.updateUserInvoice(userinvoiceused);
				}
			}else{
				result = this.collectFeesService.savePricingCollectFees(this.model,user,"");
			}
			String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
			String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING",user.getCenter_num()).getConfig_value().trim();
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
			
			try {
				if("ok".equals(result.getFlag())){
					if ("Y".equals(lis) && "N".equals(lis_bang)) {
						this.lisPacsApplicationService.lisSend(model.getExam_num(), model.getChargingIds(), user, false,lis_bang,"N");
					}
					if ("Y".equals(pacs)) {
						this.lisPacsApplicationService.pacsSend(model.getExam_num(), model.getChargingIds(), user, false,"N");
					}
				}
				//是否启用排队接口
				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_num()).getConfig_value().trim();
				if("Y".equals(is_queue_check)){
					QueueAddBean bean = new QueueAddBean();
					bean.setCenter_num(user.getCenter_num());
					bean.setExam_num(model.getExam_num());
					QueueAddToSendMessage message = new QueueAddToSendMessage();
					QueueResBody rb = message.Send(bean);
					if("AA".equals(rb.getRescode())){
						
					}else{
						this.message = rb.getRestext();
					}
				}
			} catch (ServiceException e) {
				result.setFlag("error");
				result.setInfo(e.getMessage());
				e.printStackTrace();
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
	 * 995 划价 查询未收费项目列表
	     * @Title: getwitemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getPricingWitemList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		String is_fees_qijian = this.customerInfoService.getCenterconfigByKey("IS_FEES_QIJIAN",user.getCenter_num()).getConfig_value().trim();
		List list = this.collectFeesService.getPricingWitemList(this.model.getExam_num(),is_fees_qijian,user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 995 退费保存信息
	     * @Title: savePricingRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String savePricingRefund() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray itemarray = JSONArray.fromObject(this.model.getExamInfoCharingItems());
		List<ExamResultChargingItemDTO> listitem = (List<ExamResultChargingItemDTO>) JSONArray.toCollection(itemarray,ExamResultChargingItemDTO.class);
		this.model.setExamInfoCharingItem(listitem);
		
		JSONArray wayarray = JSONArray.fromObject(this.model.getCharingWays());
		List<ChargingWayDTO> listway = (List<ChargingWayDTO>) JSONArray.toCollection(wayarray,ChargingWayDTO.class);
		this.model.setCharingWay(listway);
		
		this.message = this.collectFeesService.savePricingRefund(this.model,user);
		//是否启用排队接口
		String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK",user.getCenter_num()).getConfig_value().trim();
		if("Y".equals(is_queue_check)){
			QueueCustomerBean bean = new QueueCustomerBean();
			bean.setCenter_num(user.getCenter_num());
			bean.setExam_num(model.getExam_num());
			QueueCustomSendMessage message = new QueueCustomSendMessage();
			QueueResBody rb = message.Send(bean);
			if("AA".equals(rb.getRescode())){
			}else{
				this.message = rb.getRestext();
			}
		}		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 997 划价查询收费方式
	     * @Title: savePricingRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String getChargingWayByItemIds(){
		List<ChargingWayDTO> list = this.collectFeesService.getChargingWayByItemIds(this.model.getExam_num(),this.model.getItem_ids());
		this.outJsonResult(list);
		return NONE;
	}
	//划价 发送缴费申请 发送已经划价的所有his 收费的收费项目
	public String pricingPaymentApplication() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
			String IS_HIS_INTERFACE = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE",user.getCenter_num()).getConfig_value().trim();
			if("N".equals(IS_HIS_INTERFACE)){
				this.message = "error-未启用HIS接口,不能发送缴费申请!";
			}else{
				try {
					List<ExaminfoChargingItem>  list = collectFeesService.getHisApplicationItemList(this.model.getExam_num(), user.getCenter_num());
					if (list.size() <= 0 ) {
						this.message = "error-没有需要发送缴费申请的收费项目!!!";
					}else{
						this.message = this.collectFeesService.pricingPaymentApplication(list,this.model.getExam_num(), user);
					}
					
				} catch (ServiceException e) {
					this.message = "error-"+e.getMessage();
					e.printStackTrace();
				}
				
			}
			this.outJsonStrResult(this.message);
			return NONE;
		}
	/**
	 * 998 团体预付费管理页面
	     * @Title: savePricingRefund   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String  getTeamPrepayment(){
		
		UserDTO user = (UserDTO) session.get("username");
		this.model.setInvoiceRepeatType(this.customerInfoService.getCenterconfigByKey("INVOICE_REPEAT_TYPE",user.getCenter_num()).getConfig_value().trim());
		this.model.setRechargeResources("N");
		this.model.setFrozenResources("N");
		try {
			List<WebResrelAtionship> wrs = user.getWebResource();
			if (wrs.size() > 0) {
				for (WebResrelAtionship wr : wrs) {
					if ("RS046".equals(wr.getRes_code())) {// 充值 资源
						if ("1".equals(wr.getDatavalue())) {
							this.model.setRechargeResources("Y");
						}
					} else if ("RS047".equals(wr.getRes_code())) {// 冻结 资源
						if ("1".equals(wr.getDatavalue())) {
							this.model.setFrozenResources("Y");
						}
					}
				}
			}
		}catch(Exception ex){}
		return SUCCESS;
	} 
	/**
	 * 999 按照单位获取商户信息
	     * @Title: getMerchantInfoByCom_id   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String  getMerchantInfoByCom_id(){
		CompanyAccountDTO  companyAccount =   new CompanyAccountDTO();
		if (this.model.getCom_id() > 0) {
	    	companyAccount = this.collectFeesService.getCompanyAccounthantInfoByCom_id(this.model.getCom_id());
		}
		this.outJsonResult(companyAccount);
		return  NONE;
	}
	
	/**
	 * 1000 保存商户信息
	     * @Title: saveMerchantInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	
	public  String saveMerchantInfo(){
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.collectFeesService.saveMerchantInfo(this.model.getCom_id(),user);
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	
	/**
	 * 1001 商户充值
	     * @Title: saveRecharge   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String saveRecharge(){
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.collectFeesService.saveRecharge(this.model,user);
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	/**
	 * 1001 商户流水信息
	     * @Title: getTransactionFlowList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String getTransactionFlowList(){
		UserDTO user = (UserDTO) session.get("username"); 
		List<CompanyAccountDetailDTO> list = this.collectFeesService.getTransactionFlowList(this.model.getCom_num(),user);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1003 商户流水信息开发票
	     * @Title: saveInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveInvoiceInfo(){
		UserDTO user = (UserDTO) session.get("username"); 
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		if(userInvoice == null){
			this.message = "error-抱歉,您并没有被分配发票号段,您不能打发票!";
		}else if(this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_min()) < 0 || this.model.getInvoice_num().compareTo(userInvoice.getInvoice_num_max()) > 0){
			this.message = "error-发票号码超出您的发票段,请重新领票!";
		}else if(this.collectFeesService.getChargingInvoiceSingleByNum(this.model.getInvoice_num(),userInvoice.getInvoice_class()) != null){
			this.message = "error-发票编号已经存在!";
		}else if(this.collectFeesService.getNullifyInvoice(this.model.getInvoice_num(), userInvoice.getInvoice_class(),user.getCenter_num()) != null){
			this.message = "error-发票编号已经作废!";
		}else{
			this.model.setInvoice_class(userInvoice.getInvoice_class());
			this.message = this.collectFeesService.saveInvoiceInfo(this.model,user);
			UserInvoice userinvoiceused = this.userInvoiceService.getUserInvoiceByUserIdAndClass(userInvoice.getUser_id(), userInvoice.getInvoice_class());
			userinvoiceused.setInvoice_num_used(this.model.getInvoice_num());
			this.userInvoiceService.updateUserInvoice(userinvoiceused);
		}
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	/**
	 * 1004 商户发票信息
	     * @Title: getInvoiceInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String getInvoiceInfoList(){
		UserDTO user = (UserDTO) session.get("username");
		List<ChargingInvoiceSingleDTO> list = this.collectFeesService.getInvoiceInfoList(this.model,user);
		this.outJsonResult(list);
		return  NONE;
	}
	/**
	 * 100 商户作废发票信息
	     * @Title: deleteInvoiceInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteInvoiceInfo(){
		UserDTO user = (UserDTO) session.get("username"); 
		this.message = this.collectFeesService.deleteInvoiceInfo(this.model,user);
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	
	/**
	 * 1006 商户冻结/解冻
	     * @Title: editCompanyAccount   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String editCompanyAccount(){
		UserDTO user = (UserDTO) session.get("username"); 
		if(this.model.getType().equals("0") || this.model.getType().equals("1")){
			this.message =  this.collectFeesService.editCompanyAccount(this.model,user);
		}else{
			this.message = "error-更改类型错误！！";
		}
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	
	/**
	 * 弹出重打发票页面  357
	     * @Title: repeatInvoice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String repeatInvoice() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		UserInvoiceDTO userInvoice = this.userInvoiceService.getUserInvoiceByUserId(user.getUserid());
		if(userInvoice != null){
			if(userInvoice.getInvoice_num_used() != null){
				this.model.setInvoice_num(userInvoice.getInvoice_num_used());
			}
		}
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
