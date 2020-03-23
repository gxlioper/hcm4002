package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.hjw.wst.DTO.CardInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.CustomerMemberInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CardMemberModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CardManageService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.BeanUtil;
import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("rawtypes")
public class CardManageAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private CardManageService cardManageService;
	private CardMemberModel model = new CardMemberModel();
	private int rows = 15; // easyui每页显示条数
	private BatchService batchService;
	private SyslogService syslogService;
	private CustomerInfoService customerInfoService;
	
	private String card_reader_code;
	private String card_reader_ocx;
	
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

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 会员卡管理对应功能 300
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String cardManage() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		
		this.card_reader_code = this.customerInfoService.getCenterconfigByKey("CARD_READER_INFO", user.getCenter_num()).getConfig_value().trim();
		this.card_reader_ocx = this.customerInfoService.getDiversforByCode(this.card_reader_code).getCom_ocx_name();
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("300");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return "cardManage";
	}
	
	/**
	 *   查询所有会员信息  301
	     * @Title: cardMemberList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cardMemberList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		Language.setLanguage(this.language);
		PageReturnDTO pageDto = new PageReturnDTO();
		pageDto = this.cardManageService.cardMemberList(this.model.getUser_name(),this.model.getId_num(),this.model.getPhone(),this.model.getLevel(),this.rows, this.getPage(),user);
		this.outJsonResult(pageDto);
		return NONE;
	}
	
	/**
	 * 会员信息编辑 302
	     * @Title: cardMemberEdit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String cardMemberEdit() throws WebException{
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		return "cardMemberEdit";
	}
	
	/**
	 *   验证身份证号是否存在 303
	     * @Title: checkCardMemberIdNum   
	     * @Description: TODO()   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String checkCardMemberIdNum() throws WebException,SQLException{
		CustomerMemberInfo caMember = this.cardManageService.getCardMemberByIdNum(this.model.getId_num());
		if(caMember == null || caMember.getId().equals(model.getId())){
			this.message = "ok";
		}else{
			this.message = "no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 加载单个会员信息 305
	     * @Title: getCardMemberOne   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCardMemberOne() throws WebException,SQLException{
		if(this.model.getId().length() == 0){
			this.message = "会员信息无效";
			return "cardMemberError";
		}else{
			CardMemberModel cardMember = this.cardManageService.getCardMemberById(this.model.getId());
			BeanUtil.copy(model, cardMember);
		}
		return SUCCESS;
	}
	
	/**
	 * 新增，修改会员信息  304
	     * @Title: saveCardMember   
	     * @Description: TODO(新增，修改会员信息)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveCardMember() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		CustomerInfo customerInfo = this.cardManageService.getCustomerInfoByIdNum(this.model.getId_num());
		if(customerInfo != null){
			customerInfo.setUser_name(this.model.getUser_name());
			customerInfo.setAddress(this.model.getAddress());
			customerInfo.setBirthday(this.model.getBirthday());
			customerInfo.setEmail(this.model.getEmail());
			customerInfo.setNation(this.model.getNation());
			customerInfo.setSex(this.model.getSex());
			customerInfo.setPhone(this.model.getPhone());
			customerInfo.setUpdater(user.getUserid());
			customerInfo.setUpdate_time(DateTimeUtil.parse());
			customerInfo = this.cardManageService.updateCustomerInfo(customerInfo);
		}else{
			customerInfo = new CustomerInfo();
			customerInfo.setArch_num(this.batchService.GetCreateID("vipno", user.getCenter_num()));
			customerInfo.setUser_name(this.model.getUser_name());
			customerInfo.setId_num(this.model.getId_num());
			customerInfo.setAddress(this.model.getAddress());
			customerInfo.setBirthday(this.model.getBirthday());
			customerInfo.setEmail(this.model.getEmail());
			customerInfo.setNation(this.model.getNation());
			customerInfo.setSex(this.model.getSex());
			customerInfo.setPhone(this.model.getPhone());
			customerInfo.setIs_Active("Y");
			customerInfo.setCreate_time(DateTimeUtil.parse());
			customerInfo.setCreater(user.getUserid());
			customerInfo.setUpdater(user.getUserid());
			customerInfo.setUpdate_time(DateTimeUtil.parse());
			customerInfo.setFlag("0");
			customerInfo.setCenter_num(user.getCenter_num());
			customerInfo = this.cardManageService.saveCustomerInfo(customerInfo);
		}
		CustomerMemberInfo cardMember = this.cardManageService.getCardMemberBymId(this.model.getId());
		if(cardMember != null){
			cardMember.setArch_num(customerInfo.getArch_num());
			cardMember.setPrelevel(cardMember.getLevel());
			cardMember.setLeveltime(DateTimeUtil.parse());
			cardMember.setLevel(model.getLevel());
			
			cardMember = this.cardManageService.updateCardMember(cardMember);
			this.message = "修改会员信息成功-"+cardMember.getId();
		
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("304");//子功能id 必须填写
			sl.setExplain("修改会员信息");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			cardMember = new CustomerMemberInfo();
			cardMember.setArch_num(customerInfo.getArch_num());
			cardMember.setLevel(model.getLevel());
			cardMember.setTotalamt(0.0);
			cardMember.setIntegral(0);
			cardMember.setTotaltimes(0);
			
			cardMember = this.cardManageService.saveCardMember(cardMember);
			this.message = "保存会员信息成功-"+cardMember.getId();
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("304");//子功能id 必须填写
			sl.setExplain("新增会员信息");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 *  个人的卡信息 306
	     * @Title: cardmerkxx   
	     * @Description:   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String cardmerkxx() throws WebException{
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		return SUCCESS;
	}
	
	/**
	 * 	根据会员ID查询卡信息  307
	     * @Title: getCardInfoByMerId   
	     * @Description:   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCardInfoByMerId() throws WebException,SQLException{
		List<CardInfoDTO> list = this.cardManageService.getCardInfoByMerId(this.model.getId());
		this.outJsonResult(list);
		return NONE;
	}
	
	@Override
	public Object getModel() {
		return model;
	}
	
	public void setModel(CardMemberModel model) {
		this.model = model;
	}

	public void setCardManageService(CardManageService cardManageService) {
		this.cardManageService = cardManageService;
	}
}
