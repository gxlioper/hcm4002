package com.hjw.zyb.action;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.hjw.util.BeanUtilExt;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;
import org.hsqldb.lib.StringUtil;
import com.hjw.util.Base64;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.ValidateTime;
import com.hjw.webService.client.CUSTOMEDITSendMessage;
import com.hjw.webService.client.CUSTOMSendMessage;
import com.hjw.webService.client.DAHSendMessage;
import com.hjw.webService.client.QueueAddToSendMessage;
import com.hjw.webService.client.QueueCustomSendMessage;
import com.hjw.webService.client.YBCustomSendMessage;
import com.hjw.webService.client.body.DAHResBody;
import com.hjw.webService.client.body.FeeResultBody;
import com.hjw.webService.client.body.QueueResBody;
import com.hjw.webService.client.body.ResultBody;
import com.hjw.webService.client.body.YbCustomMessage;
import com.hjw.webService.client.body.YbCustomResultBody;
import com.hjw.webService.client.hghis.JiuzhenkaInfo;
import com.hjw.webService.client.Bean.Custom;
import com.hjw.webService.client.Bean.DAHCustomerBean;
import com.hjw.webService.client.Bean.QueueAddBean;
import com.hjw.webService.client.Bean.QueueCustomerBean;
import com.hjw.webService.client.Bean.YbCustom;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.DiversInfoDTO;
import com.hjw.wst.DTO.DjtTotalDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TeamAccountExamListDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.config.Logincheck;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CustomerInfo;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamInfoSign;
import com.hjw.wst.domain.Introducer;
import com.hjw.wst.domain.ReportReceive;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.RegisterModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DjtCustomerInfoService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.ReportReceiveService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.TeamAccountService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.wst.service.WebConfigService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.hjw.zyb.DTO.ZybExamInfoUserDTO;
import com.hjw.zyb.model.ZybImpCustomerInfoModel;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.action
 * @Description:
 * @author: yangm
 * @date: 2016年7月16日 下午8:32:41
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybRegisterDeskAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private BatchService batchService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CompanyService companyService;
	private RegisterModel model = new RegisterModel();
	private UserInfoService userInfoService;
	private CustomerInfoService customerInfoService;
	private WebConfigService webConfigService;
	private DjtCustomerInfoService djtCustomerInfoService;
	private String sfz_div_ocx;// 身份证读卡器ocx
	private String sfz_div_code;// 身份证厂家代码
	private WebserviceConfigurationService webserviceConfigurationService;
	private LisPacsApplicationService lisPacsApplicationService;
	private CollectFeesService collectFeesService;
    private CommService commService;    
    private TeamAccountService teamAccountService;  
    private String  web_Resource;
    private SyslogService syslogService;    
	private ReportReceiveService  reportReceiveService;//报告领取方式
	private  String e_num;
	private ZybCustomerInfoService zybCustomerInfoService;
	private String sort;
	private String order;	
	 private String feeresourceflag = "0";//
	
	public void setZybCustomerInfoService(ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}
	public String getFeeresourceflag() {
		return feeresourceflag;
	}
	public void setFeeresourceflag(String feeresourceflag) {
		this.feeresourceflag = feeresourceflag;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
	public void setE_num(String e_num) {
		this.e_num = e_num;
	}

	public void setReportReceiveService(ReportReceiveService reportReceiveService) {
		this.reportReceiveService = reportReceiveService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public String getWeb_Resource() {
		return web_Resource;
	}

	public void setWeb_Resource(String web_Resource) {
		this.web_Resource = web_Resource;
	}

	public void setTeamAccountService(TeamAccountService teamAccountService) {
		this.teamAccountService = teamAccountService;
	}

	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}

	public String getSfz_div_ocx() {
		return sfz_div_ocx;
	}

	public void setSfz_div_ocx(String sfz_div_ocx) {
		this.sfz_div_ocx = sfz_div_ocx;
	}

	public String getSfz_div_code() {
		return sfz_div_code;
	}

	public void setSfz_div_code(String sfz_div_code) {
		this.sfz_div_code = sfz_div_code;
	}

	public void setDjtCustomerInfoService(DjtCustomerInfoService djtCustomerInfoService) {
		this.djtCustomerInfoService = djtCustomerInfoService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setWebConfigService(WebConfigService webConfigService) {
		this.webConfigService = webConfigService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(RegisterModel model) {
		this.model = model;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}

	public void setCollectFeesService(CollectFeesService collectFeesService) {
		this.collectFeesService = collectFeesService;
	}

	/**
	 * 
	     * @Title: zybregisterDesk   
	     * @Description: 职业病登记台 zyb55  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybregisterDesk() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		this.model.setUpdate_time(DateTimeUtil.getDate2());		
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setDjd_code_url(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setReport_url(this.customerInfoService.getCenterconfigByKey("REPORT_URL", user.getCenter_num()).getConfig_value().trim());
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("zyb55");//可不填写
		sl.setXtgnid2("2");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getZybDjtExamInfoUserList   
	     * @Description: 职业病登记台查询人员界面 zyb56   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybDjtExamInfoUserList() throws WebException{
		this.defaultapp = (SystemType)session.get("defaultapp");
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().trim().length() > 5)) {
			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("register_date") < 0) {
				this.model.setTime1("");
				this.model.setTime2("");
			}
			
			if (this.model.getChkItem().trim().indexOf("over_date") < 0) {
				this.model.setTime3("");
				this.model.setTime4("");
			}	
			
			if (this.model.getChkItem().trim().indexOf("create_time") < 0) {
				this.model.setCreate_time("");
			}
			
			if (this.model.getChkItem().trim().indexOf("exam_type") < 0) {
				this.model.setExam_type("");
			}
			if (this.model.getChkItem().trim().indexOf("arch_num") < 0) {
				this.model.setArch_num("");
			}
			if (this.model.getChkItem().trim().indexOf("com_Name") < 0) {
				this.model.setCompany_id(0);
			}

			if (this.model.getChkItem().trim().indexOf("employeeID") < 0) {
				this.model.setEmployeeID("");
			}
			
			if (this.model.getChkItem().trim().indexOf("status") < 0) {
				this.model.setStatus("");
			}

			if (this.model.getChkItem().trim().indexOf("id_num") < 0) {
				this.model.setId_num("");
			}
			if (this.model.getChkItem().trim().indexOf("batch_id") < 0) {
				this.model.setBatch_id(0);
			}
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			contractlist = this.zybCustomerInfoService.getDjtExamInfoUserList(this.model.getCompany_id(),this.model.getBatch_id(),this.model.getSet_id(),
					this.model.getArch_num(), this.model.getExam_num(), this.model.getEmployeeID(),this.model.getStatus(),
					this.model.getId_num(), this.model.getCustname(), this.model.getTime1(), this.model.getTime2(), this.model.getTime3(), this.model.getTime4(),
					this.model.getCreate_time(),this.model.getExam_type(),this.defaultapp.getComid(),user.getUserid()+"",centernum, this.rows, this.getPage(),this.sort,this.order,user.getCenter_num());

		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("286");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	     * @Title: getDjtRegisterGList   
	     * @Description: 职业病个人登记页面 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDjtRegisterGList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb57");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getDjtRegisterTList   
	     * @Description: 职业病团体登记页面 zyb58 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDjtRegisterTList() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb58");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 职业病团体复检登记台
	     * @Title: getZybDjtRegisterTListRecheck   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybDjtRegisterTListRecheck() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
			for (WebResrelAtionship wr : wrs) {
				if ("RS023".equals(wr.getRes_code())) {
					this.feeresourceflag = "1";
					break;
				}
			}
			//登记台收费
			for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS057")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setGtjf(1);
						break;
					}
				}
			}

			//团体项目转个人项目资源
			for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS043")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setTtog(1);
						break;
					}
				}
			}

			//个人项目转团体项目资源
			for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS042")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setGtot(1);
						break;
					}
				}
			}

		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb58");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getZybDjtRegisterExtEdit   
	     * @Description: 职业病扩展信息管理页面 zyb64   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
    public String getZybDjtRegisterExtEdit() throws WebException, SQLException {
    	UserDTO user = (UserDTO) session.get("username");
		String exam_num = (String) request.getSession().getAttribute("exam_num");
		if (!StringUtil.isEmpty(exam_num)) {
			ExamInfoUserDTO eu= new ExamInfoUserDTO();
			eu = this.customerInfoService.getExamInfoUserForExamId(exam_num,user);
			this.model.setData_name(eu.getData_name());//职业体检类别
			this.model.setZybxz_exam_num(exam_num);
		}		

		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getDjtRegisterGEdit   
	     * @Description: 职业病个人管理页面 zyb59  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDjtRegisterGEdit() throws WebException, SQLException {
		//职业病複檢登記
		this.defaultapp = (SystemType) session.get("defaultapp");
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getRen_type() == 1) {
			if (this.model.getExam_num() != "" && this.model.getExam_num() != null) {
				try {
					String exam_num = this.batchService.GetCreateID("exam_no",user.getCenter_num());
					ExamInfo ei = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
					ExamInfo newei = new ExamInfo();
					BeanUtilExt.copyProperties(newei, ei);
					newei.setCounter_check("Y");
					newei.setStatus("D");
					newei.setCompany("");
					newei.setCompany_id(0);
					newei.setGroup_id(0);
					newei.setBatch_id(0);
					newei.setExam_num(exam_num);
					newei.setJoin_date(null);
					newei.setRegister_date(DateTimeUtil.getDateTime());
//					newei.setKaidanren(0);
					newei.setExam_type("G");
//					newei.setGuid_print_time(null);
//					newei.setGuid_printer(0);
//					newei.setHis_req_status("N");
					newei.setFreeze(0);
					newei.setApptype(this.defaultapp.getComid());
//					newei.setVipflag("0");
//                    newei.setId(0);
					newei = this.djtCustomerInfoService.saveExamInfo(newei);

					this.model.setExam_id(newei.getId());
					this.model.setExam_num(exam_num);
					List<GroupChargingItemDTO> listitem = this.customerInfoService.getRelistitem(ei.getExam_num(), this.defaultapp.getComid());
					String items = "";
					List<GroupChargingItemDTO> listitemnew = new ArrayList<GroupChargingItemDTO>();
					for (GroupChargingItemDTO gci : listitem) {
						items = items + "" + gci.getItem_code() + ",";
						gci.setDiscount(10);
						gci.setItemnum(1);
						listitemnew.add(gci);
					}

					listitem = listitemnew;
					long examid = newei.getId();
					String itemcodes = "";
					itemcodes = this.customerInfoService.getItemCodesforExamId(exam_num, this.defaultapp.getComid());
					if (StringUtil.isEmpty(this.model.getExam_indicator())) {
						this.model.setExam_indicator("G");
					}
					this.customerInfoService.addIDSExamInfoChangItem(exam_num, listitem, itemcodes,
							"G", user.getUserid(), user.getName(), user.getCenter_num());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}else{
			String exam_num = (String) request.getSession().getAttribute("exam_num");
			if (!StringUtil.isEmpty(exam_num)) {
				this.model.setExam_num(exam_num);
			}
		}
		this.model.setRegister_date(DateTimeUtil.getDate2());// 预约日期
		this.model.setJoin_date(DateTimeUtil.getDate2());// 报道日期，体检日期
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setIs_after_pay(this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", user.getCenter_num()).getConfig_value().trim());
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setDjd_code_url(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		
		//根据用户资源判断是否展示折扣率跟折扣后金额
        List<WebResrelAtionship> wrs = user.getWebResource();
        if (wrs != null && wrs.size() > 0) {
            for (WebResrelAtionship wr : wrs) {
                if ("RS049".equals(wr.getRes_code())) {
                    this.model.setIs_show_discount(1);
                    break;
                }
            }
            for (WebResrelAtionship wr : wrs) {
                if ("RS023".equals(wr.getRes_code())) {
                    this.feeresourceflag = "1";
                    break;
                }
            }
            //登记台收费
            for (int i = 0; i < wrs.size(); i++) {
                if (wrs.get(i).getRes_code().equals("RS057")) {
                    if ("1".equals(wrs.get(i).getDatavalue())) {
                        this.model.setGtjf(1);
                        break;
                    }
                }
            }
        }
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb59");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		String djttype = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num()).getConfig_value();// 获取登记台样式
		if("JIUZHENKA".equals(djttype.toUpperCase())){
			DiversInfoDTO  di = this.customerInfoService.getjiuzhenkaCard(user.getCenter_num());
			this.model.setRead_jiuzhenka(di.getCode().trim());
			this.model.setRead_jiuzhenka_ocx(di.getCom_ocx_name().trim());
			return "success_jiuzhenka";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * 
	     * @Title: getDjtRegisterTEdit   
	     * @Description: 职业病团体管理页面 zyb60 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDjtRegisterTEdit() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
//		String exam_num = (String) request.getSession().getAttribute("exam_num");
//		if (!StringUtils.isEmpty(exam_num)) {
//			this.model.setExam_num(exam_num);
//		}
		this.model.setRegister_date(DateTimeUtil.getDate2());
		this.model.setJoin_date(DateTimeUtil.getDate2());
		this.model.setIs_after_pay(this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", user.getCenter_num()).getConfig_value().trim());
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setDjd_code_url(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
            for (WebResrelAtionship wr : wrs) {
                if ("RS023".equals(wr.getRes_code())) {
                    this.feeresourceflag = "1";
                    break;
                }
            }
            //登记台收费
            for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS057")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setGtjf(1);
						break;
					}
				}
			}

			//团体项目转个人项目资源
			for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS043")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setTtog(1);
						break;
					}
				}
			}

			//个人项目转团体项目资源
			for (int i = 0; i < wrs.size(); i++) {
				if (wrs.get(i).getRes_code().equals("RS042")) {
					if ("1".equals(wrs.get(i).getDatavalue())) {
						this.model.setGtot(1);
						break;
					}
				}
			}

		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb60");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		String djttype = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num()).getConfig_value();// 获取登记台样式
		if("JIUZHENKA".equals(djttype.toUpperCase())){
			DiversInfoDTO  di = this.customerInfoService.getjiuzhenkaCard(user.getCenter_num());
			this.model.setRead_jiuzhenka(di.getCode().trim());
			this.model.setRead_jiuzhenka_ocx(di.getCom_ocx_name().trim());
			return "success_jiuzhenka";
		}else{
			return SUCCESS;
		}
	}


	/**
	 * 
	     * @Title: djteditcustomerdo   
	     * @Description: 职业病个人保存 zyb61  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String djteditcustomerdo() throws WebException{
		this.defaultapp = (SystemType)session.get("defaultapp");
		UserDTO user = (UserDTO) session.get("username");
		if(!StringUtils.isEmpty(this.model.getExam_num())){
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if(eu.getFreeze()==1){
				this.message = "error-体检信息已冻结，操作不能继续";
				this.outJsonStrResult(this.message);
				return NONE;
			}
			}
		ExamInfoUserDTO imp = new ExamInfoUserDTO();
		imp.set_level(this.model.get_level());
		imp.setAge(this.model.getAge());
		imp.setCustomer_id(this.model.getCustomer_id());
		imp.setUser_name(this.model.getCustname());
		imp.setBatch_id(this.model.getBatch_id());
		imp.setBirthday(this.model.getBirthday());
		imp.setCustomer_type(this.model.getCustomer_type());
		imp.setCustomer_type_id(this.model.getCustomer_type_id());
		imp.setCompany_id(this.model.getCompany_id());
		imp.setId_num(this.model.getId_num());
		imp.setIs_marriage(this.model.getIs_marriage());
		imp.setExam_num(this.model.getExam_num());
		imp.setArch_num(this.model.getArch_num());
		imp.setOthers(this.model.getOthers());
		imp.setPosition(this.model.getPosition());
		imp.setRemarke(this.model.getRemark());
		imp.setIntroducer(this.model.getIntroducer());
		imp.setSex(this.model.getSex());
		imp.setPhone(this.model.getTel());
		imp.setExam_type("G");
		imp.set_level(this.model.get_level());
		imp.setEmail(this.model.getEmail());
		imp.setNation(this.model.getNation());
		imp.setChargingType(this.model.getChargingType());
		imp.setAddress(this.model.getAddress());
		imp.setIs_after_pay(this.model.getIs_after_pay());
		imp.setCompany(this.model.getCompany());
		imp.setPast_medical_history(this.model.getPast_medical_history());
		imp.setPicture_Path(this.model.getPicture_Path());
		imp.setPatient_id(this.model.getPatient_id());
		imp.setEmployeeID(this.model.getEmployeeID());
		imp.setMc_no(this.model.getMc_no());
		imp.setVisit_date(this.model.getVisit_date());
		imp.setVisit_no(this.model.getVisit_no());
		imp.setClinic_no(this.model.getClinic_no());
        imp.setApptype("2");
        imp.setIdtype(model.getIdtype());
		imp.setIdtypename(model.getIdtypename());
		imp.setCenter_num(user.getCenter_num());
		if (this.model.getBudget_amount() != null && !"".equals(this.model.getBudget_amount())) {
			imp.setBudget_amount(Double.valueOf(this.model.getBudget_amount()));
		}
		if (this.model.getFlags() == 0) {
			imp.setRegister_date(DateTimeUtil.getDateTime());
			String time11 = (this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num()).getConfig_value()
					.trim());
			String time22 = (this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num()).getConfig_value().trim());
			time22 = time11 + "-" + time22;
			//time22 = time22.replaceAll(":", "");
			imp.setExam_times(time22);
			imp.setJoin_date(DateTimeUtil.getDateTime());
			imp.setStatus("D");
			imp.setExam_center_num(user.getCenter_num());  // 报到体检中心编码 D 状态插入
		} else if (this.model.getFlags() == 1) {
			imp.setStatus("Y");
			imp.setRegister_date(this.model.getRegister_date());
			imp.setJoin_date(null);
			imp.setExam_times(this.model.getSetimes());
		}
		imp.setCustomerType(this.model.getCustomerType());
		String exam_times = this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num()).getConfig_value().trim();
		exam_times = exam_times + "-"
				+ this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num()).getConfig_value().trim();
		imp.setExam_times(exam_times);

		String is_djt_type = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num()).getConfig_value().trim();
		imp.setIs_djt_type(is_djt_type);
		
		String is_hg_his_jiuzhenka = this.customerInfoService.getCenterconfigByKey("IS_HG_WRITEJIUZHENKA", user.getCenter_num()).getConfig_value().trim();
		if("Y".equals(is_hg_his_jiuzhenka)){
			String url = this.webserviceConfigurationService.getWebServiceConfig("PAYMENT_APPLICATION", user.getCenter_num()).getConfig_url();
			
			JiuzhenkaInfo info = new JiuzhenkaInfo();
			ExamInfoDTO ex = new ExamInfoDTO();
			if(imp.getVisit_no() == null || "".equals(imp.getVisit_no())){
				this.message = "error-就诊卡号不能为空!";
			}else{
				ex.setaOutpatientNo(Long.parseLong(imp.getVisit_no()));
				ex.setaPatName(imp.getUser_name());
				if("男".equals(imp.getSex())){
					ex.setaSex(1);
				} else {
					ex.setaSex(2);
				}
				ex.setaBirthday(DateTimeUtil.parse0(imp.getBirthday()));
				ex.setBirthday(imp.getBirthday());
				ex.setaAddres(imp.getAddress());
				ex.setaIDCard(imp.getId_num());
				ex.setaTel(imp.getPhone());
				ex.setaOperator(user.getUserid());
				ex.setaPrice(0);
				
				String re = info.setHisCardInfo(url,"reqjiuzhenkahis", ex);
//				if(re!=null && !"".equals(re)){
					this.message = this.djtCustomerInfoService.getDjtExamInfoOne(user.getUserid(),
							user.getCenter_num(), imp);
//				}else{
//					this.message = "error-新增调用HIS就诊卡接口出错，"+re;
//				}
			}
		}else{
			this.message = this.djtCustomerInfoService.getDjtExamInfoOne(user.getUserid(), user.getCenter_num(), imp);
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb61");//子功能id 必须填写
		sl.setExplain("职业病登记台人员信息维护 "+imp.getExam_num()+" "+imp.getUser_name());//操作说明
		syslogService.saveSysLog(sl);
		try {
			String ress[] = this.message.split("-");
			if ("ok".equals(ress[0])) {
				String exam_num = ress[1];
				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
						.getConfig_value().trim();
				if ("Y".equals(is_queue_check)) {
					QueueCustomerBean bean = new QueueCustomerBean();
					bean.setCenter_num(user.getCenter_num());
					bean.setExam_num(exam_num);
					QueueCustomSendMessage message = new QueueCustomSendMessage();
					QueueResBody rb = message.Send(bean);
					if ("AA".equals(rb.getRescode())) {

					} else {
						this.message = rb.getRestext();
					}
					System.out.println("1.5");
					/*System.out.println(this.model.getFlags());
					if (this.model.getFlags() == 0) {
						System.out.println("1.6");
						QueueAddBean bn = new QueueAddBean();
						bn.setCenter_num(user.getCenter_num());
						bn.setExam_id(Long.valueOf(exam_ids));
						QueueAddToSendMessage mess = new QueueAddToSendMessage();
						rb = mess.Send(bn);
						System.out.println("1.7");
						if ("AA".equals(rb.getRescode())) {

						} else {
							this.message = rb.getRestext();
						}
						System.out.println("1.8");
					}*/
				}
			}
			if (this.model.getFlags() != 1) {
			if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_GCUSTOM_GH", user.getCenter_num()).getConfig_value())) {
				//String ress[] = this.message.split("-");
				if ("ok".equals(ress[0])) {
					ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(imp.getExam_num());
					if("Y".equals(ei.getIs_Active())){
					imp.setExam_num(ei.getExam_num());
					Custom st = new Custom();
					st.setCenter_num(user.getCenter_num());
					st.setPATIENT_ID(imp.getExam_num());
					st.setEXAM_NUM(imp.getExam_num());
					st.setNAME(imp.getUser_name());
					st.setNAME_PHONETIC("");
					st.setSEX(imp.getSex());// 男 女
					st.setDATE_OF_BIRTH(imp.getBirthday());
					st.setBIRTH_PLACE("");
					st.setNATION("");
					st.setCITIZENSHIP("");
					st.setID_NO(imp.getId_num());
					st.setIDENTITY("一般人员");
					st.setUNIT_IN_CONTRACT("");
					st.setMAILING_ADDRESS("");
					st.setZIP_CODE("");
					st.setPHONE_NUMBER_BUSINESS("");
					st.setPHONE_NUMBER_HOME("");
					st.setNEXT_OF_KIN("");
					st.setRELATIONSHIP("");
					st.setNEXT_OF_KIN_ADDR("");
					st.setNEXT_OF_KIN_PHONE("");
					st.setNEXT_OF_KIN_ZIP_CODE("");
					st.setOPERATOR(user.getUserid() + "");
					st.setBUSINESS_ZIP_CODE("");
					st.setPHOTO("");
					st.setPATIENT_CLASS("");
					st.setDEGREE("");
					st.setE_NAME("");
					st.setOCCUPATION("");
					st.setNATIVE_PLACE("");
					st.setMAILING_ADDRESS_CODE("");
					st.setMAILING_STREET_CODE("");
					st.setALERGY("");
					st.setMARITAL_STATUS(imp.getIs_marriage());
					st.setNEXT_OF_SEX("");
					st.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
					st.setOPERATORS("");
					st.setCARD_NAME("");
					st.setCARD_NO("1");// 1-非院内卡挂号，院内卡挂号就传卡号
					st.setINVOICE_NO("");
					st.setCLINIC_NO(ei.getClinic_no());
					st.setCLINIC_DATE_SCHEDULED("");
					st.setCHARGE_TYPE("自费");
					st.setAGE(imp.getAge() + "");
					st.setNEXT_OF_BATH("");
					if ((ei.getPatient_id().length() <= 0) ||(ei.getClinic_no().length() <= 0) || (ei.getVisit_date().length() < 8)
							|| (this.model.getVisit_no().length() < 0)) {
						WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
						wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION", user.getCenter_num());									
						String web_url = wcd.getConfig_url().trim();
						String web_meth=wcd.getConfig_method().trim();
						CUSTOMSendMessage csm = new CUSTOMSendMessage(st);
						ResultBody fr = new ResultBody();
						fr = csm.customSend(web_url,web_meth, true);
						if ("AA".equals(fr.getResultHeader().getTypeCode())) {
							imp.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
							imp.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
							imp.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
							imp.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
							String msg = this.djtCustomerInfoService.getDjtExamInfoOne(user.getUserid(), user.getCenter_num(),
									imp);
							System.out.println(msg);
							
							sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());		
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("293");//子功能id 必须填写
							sl.setExplain("登记台人员挂号 "+ei.getExam_num()+" "+ei.getUser_name()+" "+imp.getPatient_id()+" "+imp.getVisit_no()+" "+imp.getVisit_date()+" "+imp.getClinic_no());//操作说明
							syslogService.saveSysLog(sl);
						}
					} else {
						WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
						wcd = webserviceConfigurationService.getWebServiceConfig("CUSTEDIT_APPLICATION", user.getCenter_num());									
						String web_url = wcd.getConfig_url().trim();
						String web_meth=wcd.getConfig_method().trim();
						CUSTOMEDITSendMessage csm = new CUSTOMEDITSendMessage(st);
						FeeResultBody fr = new FeeResultBody();
						fr = csm.customSend(web_url,web_meth, true);
						sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());		
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("414");//子功能id 必须填写
						sl.setExplain("登记台人员修改 "+ei.getExam_num()+" "+ei.getUser_name());//操作说明
						syslogService.saveSysLog(sl);
					}
				}
			}
			}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djteditTcustomerdo   
	     * @Description: 职业病团体保存 zyb62
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String djteditTcustomerdo() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");
		if(!StringUtil.isEmpty(model.getExam_num())){
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if(eu.getFreeze()==1){
				this.message = "error-体检信息已冻结，操作不能继续";
				this.outJsonStrResult(this.message);
				return NONE;
			}
		}
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch bt = new Batch();
			bt = this.batchService.getBatchByID(this.model.getBatch_id());
			if (bt.getId() > 0) {
					this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), bt.getCompany_id(),
							user.getUserid(), user.getCenter_id());

					ExamInfoDTO eiold = new ExamInfoDTO();
					boolean teamaccflag=false;
					boolean reexaminsert=false;
					if (!StringUtil.isEmpty(model.getExam_num())) {
						ExamInfo eif = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
						eiold = this.customerInfoService.getExamInfoForexamNum(eif.getExam_num());

						TeamAccountExamListDTO tae =  new TeamAccountExamListDTO();
						tae = teamAccountService.getTeamAccountExamListforExamNum(eiold.getExam_num());
						if((tae!=null)&&(tae.getId()>0)){
							teamaccflag=true;
						}
					}else{
						if("Y".equals(this.model.getReexam_type())){
							reexaminsert=true;
						}
					}

				String accountflagAndOverMsg = "ok";
				if (StringUtils.isEmpty(this.model.getExam_num()) || this.model.getGroup_id()!=eiold.getGroup_id()) {
					accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(this.model.getBatch_id());
				}

				if (!"ok".equals(accountflagAndOverMsg)) {
					this.message = accountflagAndOverMsg;
				} else{
						ExamInfoUserDTO imp = new ExamInfoUserDTO();
						imp.setGroup_id(0);
						imp.set_level(this.model.get_level());
						imp.setAge(this.model.getAge());
						imp.setCustomer_id(this.model.getCustomer_id());
						imp.setUser_name(this.model.getCustname());
						imp.setBatch_id(this.model.getBatch_id());
						imp.setBirthday(this.model.getBirthday());
						imp.setCustomer_type(this.model.getCustomer_type());
						imp.setCustomer_type_id(this.model.getCustomer_type_id());
						imp.setCompany_id(this.model.getCompany_id());
						imp.setId_num(this.model.getId_num());
						imp.setIs_marriage(this.model.getIs_marriage());
						imp.setExam_num(this.model.getExam_num());
						imp.setArch_num(this.model.getArch_num());
						imp.setOthers(this.model.getOthers());
						imp.setPosition(this.model.getPosition());
						imp.setRemarke(this.model.getRemark());
						imp.setIntroducer(this.model.getIntroducer());
						imp.setSex(this.model.getSex());
						imp.setPhone(this.model.getTel());
						imp.setExam_type("T");
						imp.setIs_after_pay(this.model.getIs_after_pay());
						imp.setPicture_Path(this.model.getPicture_Path());
						imp.setEmail(this.model.getEmail());
						imp.setNation(this.model.getNation());
						imp.setChargingType(this.model.getChargingType());
						imp.setAddress(this.model.getAddress());
						imp.setEmployeeID(this.model.getEmployeeID());
						imp.setPast_medical_history(this.model.getPast_medical_history());
						imp.setApptype("2");
						imp.setZyb_set_source(this.model.getZyb_set_source());
						imp.setIdtype(this.model.getIdtype());
						imp.setIdtypename(this.model.getIdtypename());
						imp.setVisit_no(this.model.getVisit_no());
						imp.setVisit_date(this.model.getVisit_date());
						imp.setCustomerType(this.model.getCustomerType());
						if (this.model.getBudget_amount() != null && !"".equals(this.model.getBudget_amount())) {
							imp.setBudget_amount(Double.valueOf(this.model.getBudget_amount()));
						}
						if (this.model.getFlags() == 0) {//新增
							imp.setRegister_date(DateTimeUtil.getDateTime());
							String time11 = (this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num())
									.getConfig_value().trim());
							String time22 = (this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num())
									.getConfig_value().trim());
							time22 = time11 + "-" + time22;
							//time22 = time22.replaceAll(":", "");
							imp.setExam_times(time22);
							imp.setJoin_date(DateTimeUtil.getDateTime());
							imp.setStatus("D");
						} else if (this.model.getFlags() == 1) {//预约
							imp.setStatus("Y");
							imp.setRegister_date(this.model.getRegister_date());
							imp.setJoin_date(null);
							imp.setExam_times(this.model.getSetimes());
						}else if (this.model.getFlags() == 2) {//修改
							imp.setJoin_date(this.model.getJoin_date());
							imp.setRegister_date(this.model.getRegister_date());
						}

						String is_djt_type = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num()).getConfig_value().trim();
						imp.setIs_djt_type(is_djt_type);

						if(!StringUtil.isEmpty(this.model.getIntroducer())) {//新的介绍人存进数据库
							List<Introducer> list = customerInfoService.queryIntroducerList(this.model.getIntroducer());
							if(list==null || list.size()==0) {
								Introducer introducer = new Introducer();
								introducer.setName(this.model.getIntroducer());
								customerInfoService.saveIntroducer(introducer);
							}
						}
						if(!StringUtil.isEmpty(this.model.get_level_name())) {//新的部门存进数据库
							CompanyDepartment department = companyService.getCompanyDepartmentByid(this.model.getCompany_id(),this.model.get_level_name());
							if(department==null || department.getId()==0) {
								department = new CompanyDepartment();
								department.setDep_Name(this.model.get_level_name());
								department.setCompany_Id(this.model.getCompany_id());
								department.setCreater(user.getUserid());
								department.setUpdater(user.getUserid());
								department.setUpdate_Time(DateTimeUtil.parse());
								department.setCreate_Time(DateTimeUtil.parse());
								department = companyService.saveCompanyDepartment(department);
								imp.set_level(""+department.getId());
							}
						}

						String is_hg_his_jiuzhenka = this.customerInfoService.getCenterconfigByKey("IS_HG_WRITEJIUZHENKA", user.getCenter_num()).getConfig_value().trim();
						if("Y".equals(is_hg_his_jiuzhenka)){
							String url = this.webserviceConfigurationService.getWebServiceConfig("PAYMENT_APPLICATION", user.getCenter_num()).getConfig_url();

							if(imp.getVisit_no() == null || "".equals(imp.getVisit_no())){
								this.message = "error-就诊卡号不能为空!";
							}else{
								JiuzhenkaInfo info = new JiuzhenkaInfo();
								ExamInfoDTO ex = new ExamInfoDTO();
								ex.setaOutpatientNo(Long.parseLong(imp.getVisit_no()));
								ex.setaPatName(imp.getUser_name());
								if("男".equals(imp.getSex())){
									ex.setaSex(1);
								} else {
									ex.setaSex(2);
								}
								ex.setaBirthday(DateTimeUtil.parse0(imp.getBirthday()));
								ex.setBirthday(imp.getBirthday());
								ex.setaAddres(imp.getAddress());
								ex.setaIDCard(imp.getId_num());
								ex.setaTel(imp.getPhone());
								ex.setaOperator(user.getUserid());
								ex.setaPrice(0);

								String re = info.setHisCardInfo(url,"reqjiuzhenkahis", ex);
//							if(re!=null && !"".equals(re)){
								this.message = this.djtCustomerInfoService.getDjtTExamInfoOne(user.getUserid(),
										user.getCenter_num(), imp);
//							}else{
//								this.message = "error-新增调用HIS就诊卡接口出错，"+re;
//							}
							}
						}else{
							this.message = this.djtCustomerInfoService.getDjtTExamInfoOne(user.getUserid(),
									user.getCenter_num(), imp);
						}
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("zyb62");//子功能id 必须填写
						sl.setExplain("职业病登记台人员信息维护 "+imp.getExam_num()+" "+imp.getUser_name());//操作说明
						syslogService.saveSysLog(sl);
						if ("ok".equals(this.message.split("-")[0])) {
							if(!StringUtil.isEmpty(this.model.getTime4())){
								String exam_num = this.message.split("-")[1];
								ExamInfo einew = this.djtCustomerInfoService.loadExamInfo(exam_num);
								einew.setExam_indicator(this.model.getTime4());
								einew.setCounter_check("Y");
								this.djtCustomerInfoService.updateExamInfo(einew);
							}

							if((reexaminsert)&&(!StringUtils.isEmpty(this.model.getOldexam_num()))){
								String exam_numnew=this.message.split("-")[1];
								String olddataexam_num=this.djtCustomerInfoService.getexamrepeatFornewid(this.model.getExam_num());
								if(StringUtils.isEmpty(olddataexam_num)){
									olddataexam_num=this.model.getOldexam_num();
								}
								this.djtCustomerInfoService.insertexamrepeat(exam_numnew, olddataexam_num, user.getUserid(),this.model.getExam_num());
							}

							sl =  new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid()+"");
							sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他
							sl.setXtgnid("");//可不填写
							sl.setXtgnid2("293");//子功能id 必须填写
							sl.setExplain("登记台操作人员 "+imp.getUser_name());//操作说明
//						syslogService.saveSysLog(sl);

							String exam_num = this.message.split("-")[1];
						}

						try {
							String ress[] = this.message.split("-");
							if ("ok".equals(ress[0])) {
								String exam_num = this.message.split("-")[1];
								String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
										.getConfig_value().trim();
								if ("Y".equals(is_queue_check)) {
									QueueCustomerBean bean = new QueueCustomerBean();
									bean.setCenter_num(user.getCenter_num());
									bean.setExam_num(exam_num);
									QueueCustomSendMessage message = new QueueCustomSendMessage();
									QueueResBody rb = message.Send(bean);
									if ("AA".equals(rb.getRescode())) {

									} else {
										this.message = rb.getRestext();
									}
									System.out.println(this.model.getFlags());
									if (this.model.getFlags() == 0) {
										QueueAddBean bn = new QueueAddBean();
										bn.setCenter_num(user.getCenter_num());
										bn.setExam_num(exam_num);
										QueueAddToSendMessage mess = new QueueAddToSendMessage();
										rb = mess.Send(bn);
										if ("AA".equals(rb.getRescode())) {

										} else {
											this.message = rb.getRestext();
										}
									}
								}
							}
							if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_TCUSTOM_GH", user.getCenter_num())
									.getConfig_value())) {
								if (this.model.getFlags() != 1) {//团体 不是预约的时候 可以发挂号申请
									ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(imp.getExam_num());
									if ("Y".equals(ei.getIs_Active())) {
										imp.setExam_num(ei.getExam_num());
										Custom st = new Custom();
										st.setCenter_num(user.getCenter_num());
										st.setPATIENT_ID(ei.getExam_num());
										st.setEXAM_NUM(ei.getExam_num());
										st.setNAME(ei.getUser_name());
										st.setNAME_PHONETIC("");
										st.setSEX(ei.getSex());// 男 女
										st.setDATE_OF_BIRTH(ei.getBirthday());
										st.setBIRTH_PLACE("");
										st.setNATION("");
										st.setCITIZENSHIP("");
										st.setID_NO(ei.getId_num());
										st.setIDENTITY("一般人员");
										st.setUNIT_IN_CONTRACT("");
										st.setMAILING_ADDRESS("");
										st.setZIP_CODE("");
										st.setPHONE_NUMBER_BUSINESS("");
										st.setPHONE_NUMBER_HOME("");
										st.setNEXT_OF_KIN("");
										st.setRELATIONSHIP("");
										st.setNEXT_OF_KIN_ADDR("");
										st.setNEXT_OF_KIN_PHONE("");
										st.setNEXT_OF_KIN_ZIP_CODE("");
										st.setOPERATOR(user.getUserid() + "");
										st.setBUSINESS_ZIP_CODE("");
										st.setPHOTO("");
										st.setPATIENT_CLASS("");
										st.setDEGREE("");
										st.setE_NAME("");
										st.setOCCUPATION("");
										st.setNATIVE_PLACE("");
										st.setMAILING_ADDRESS_CODE("");
										st.setMAILING_STREET_CODE("");
										st.setALERGY("");
										st.setMARITAL_STATUS(ei.getIs_marriage());
										st.setNEXT_OF_SEX("");
										st.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
										st.setOPERATORS("");
										st.setCARD_NAME("");
										st.setCARD_NO("1");// 1-非院内卡挂号，院内卡挂号就传卡号
										st.setINVOICE_NO("");
										st.setCLINIC_NO(ei.getClinic_no());
										st.setCLINIC_DATE_SCHEDULED("");
										st.setCHARGE_TYPE("自费");
										st.setAGE(ei.getAge() + "");
										st.setNEXT_OF_BATH("");
										if ((ei.getPatient_id().length() <= 0) ||(ei.getClinic_no().length() <= 0) || (ei.getVisit_date().length() < 8)
												|| (this.model.getVisit_no().length() < 0)) {
											WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
											wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION", user.getCenter_num());
											String web_url = wcd.getConfig_url().trim();
											String web_meth=wcd.getConfig_method().trim();
											CUSTOMSendMessage csm = new CUSTOMSendMessage(st);
											ResultBody fr = new ResultBody();
											fr = csm.customSend(web_url,web_meth, true);
											if ("AA".equals(fr.getResultHeader().getTypeCode())) {

												imp.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
												imp.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
												imp.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
												imp.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
												this.djtCustomerInfoService.getDjtExamInfoOne(user.getUserid(), user.getCenter_num(),
														imp);
												sl =  new SysLog();
												sl.setCenter_num(user.getCenter_num());
												sl.setUserid(user.getUserid()+"");
												sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他
												sl.setXtgnid("");//可不填写
												sl.setXtgnid2("414");//子功能id 必须填写
												sl.setExplain("登记台人员挂号 "+ei.getExam_num()+" "+ei.getUser_name()+" "+imp.getPatient_id()+" "+imp.getVisit_no()+" "+imp.getVisit_date()+" "+imp.getClinic_no());//操作说明
//											syslogService.saveSysLog(sl);
											}
										} else {
											WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
											wcd = webserviceConfigurationService.getWebServiceConfig("CUSTEDIT_APPLICATION", user.getCenter_num());
											String web_url = wcd.getConfig_url().trim();
											String web_meth=wcd.getConfig_method().trim();
											CUSTOMEDITSendMessage csm = new CUSTOMEDITSendMessage(st);
											FeeResultBody fr = new FeeResultBody();
											fr = csm.customSend(web_url,web_meth, true);

											sl =  new SysLog();
											sl.setCenter_num(user.getCenter_num());
											sl.setUserid(user.getUserid()+"");
											sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他
											sl.setXtgnid("");//可不填写
											sl.setXtgnid2("414");//子功能id 必须填写
											sl.setExplain("登记台人员人员信息更新 "+ei.getExam_num()+" "+ei.getUser_name());//操作说明
//										syslogService.saveSysLog(sl);
										}
									}
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					String ress[] = this.message.split("-");
					if ("ok".equals(ress[0])) {
						ExamInfoDTO edto = this.customerInfoService.getExamInfoForexamNum(ress[1]);
						this.message = "ok-"+edto.getExam_num()+"-"+ress[2];
					}
			} else {
				this.message = "error-体检任务无效！";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: zybdjtaddcustomerdo   
	     * @Description: 职业病保存扩展信息 zyb65
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybdjtaddcustomerdo() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if(StringUtils.isEmpty(model.getExam_num())){
			this.message = "error-无效体检编号";
		}else if (StringUtil.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效体检编号";
		} else {
			ZybExamInfoUserDTO imp = new ZybExamInfoUserDTO();
			imp.setId_num(this.model.getId_num());
			imp.setExam_num(this.model.getExam_num());
			imp.setArch_num(this.model.getArch_num());
			imp.setOccusector(this.model.getOccusector());
			imp.setOccusectorid(this.model.getOccusectorid());
			imp.setOccutypeofwork(this.model.getOccutypeofwork());
//			imp.setOccutypeofworkid(this.model.getOccutypeofworkid());
			imp.setDamage(this.model.getDamage());
			imp.setEmployeeage(this.model.getEmployeeage());
			imp.setJoinDatetime(this.model.getJoinDatetime());
			imp.setEmployeemonth(this.model.getEmployeemonth());
			imp.setDammonth(this.model.getDammonth());
			// 页面未选择系统已有的工种
			if(StringUtils.isEmpty(this.model.getOccutypeofworkid()) && !StringUtils.isEmpty(this.model.getTypeofwork_name().trim())){
				String str = zybCustomerInfoService.addZybOccutypeofwork(this.model.getTypeofwork_name().trim());
				imp.setOccutypeofworkid(str);
			}else{
				imp.setOccutypeofworkid(this.model.getOccutypeofworkid());
			}

			this.message = this.zybCustomerInfoService.saveOrUpdateExamInfoExt(imp, user.getUserid());
			if(this.message.indexOf("ok")==0){
				this.message="ok-"+this.model.getExam_num();
			}
			// UserDTO user = (UserDTO) session.get("username");
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("zyb65");// 子功能id 必须填写
			sl.setExplain("体检人员 " + imp.getExam_num());// 操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}	
	
	
	/**
	 * 
	     * @Title: zybdjtcustomeritemaddshow   
	     * @Description: 登记台增加职业病 zyb66   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybdjtcustomerGitemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb66");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	
	/**
	 * 
	     * @Title: zybdjtcustomeritemaddshow   
	     * @Description: 个人增加职业病项目页面 zyb69   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybdjtcustomeritemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb69");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: zybTTcustomeritemaddshow   
	     * @Description:登记台增加团检项目 zyb67  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybdjtTTcustomeritemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb67");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: zybdjtTGcustomeritemaddshow   
	     * @Description:登记台增加个检项目 zyb68
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybdjtTGcustomeritemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		//根据用户资源判断是否展示折扣率跟折扣后金额
		List<WebResrelAtionship> wrs=user.getWebResource();
		if (wrs != null && wrs.size() > 0) {
			for (WebResrelAtionship wr : wrs) {
				if ("RS049".equals(wr.getRes_code())) {
					this.model.setIs_show_discount(1);
					break;
				}
			}
		}

		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb68");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	
	
	/**
	 * 
	     * @Title: zybcustGSaveItemSet   
	     * @Description:职业病团体保存体检项目 zyb70  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybcustSaveItemSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");		
		ExaminfoSetDTO bc = new ExaminfoSetDTO();
		bc.setAmount(model.getAmount());
		bc.setExam_num(model.getExam_num());
		bc.setExam_num(model.getExam_num());
		bc.setDiscount(model.getDiscount());
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
        String chargids="";
		boolean fxitemflag = true; // 分项金额判断
		for (GroupChargingItemDTO gci : listitem) {
			chargids=chargids+""+gci.getItem_code()+",";
			BigDecimal bd = new BigDecimal(0);
			bd = new BigDecimal(gci.getAmount());
			double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			double amount=0;
            if(gci.getDiscount()>0){
            	bd = new BigDecimal(gci.getItem_amount()*gci.getItemnum() * gci.getDiscount() / 10);
    			amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }else{
            	amount=tamt;
            }
			if (java.lang.Math.abs(amount-(tamt))>10) {
				fxitemflag = false;
				break;
			}
		}

		if (StringUtils.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能加项！";
			this.outJsonStrResult(message);
			return NONE;
		}
		//判断职业体检类别与套餐是否一致
        if(listset!=null && listset.size()>0){
            String m = this.zybCustomerInfoService.cheCkOccuphyexaclass(model.getExam_num(),listset);
            if(!StringUtils.isEmpty(m)){
                this.outJsonStrResult("error-"+m);
                return NONE;
            }

        }
			String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
			this.message = this.zybCustomerInfoService.zybaddExamInfoChangItem(bc, listitem, listset, "G",IS_CHARGINGWAY_ZERO,
					user.getUserid(), user.getName(), user.getCenter_num());
			String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
					.getConfig_value().trim();
			if ("Y".equals(is_queue_check)) {
				QueueCustomerBean bean = new QueueCustomerBean();
				bean.setCenter_num(user.getCenter_num());
				bean.setExam_num(model.getExam_num());
				QueueCustomSendMessage message = new QueueCustomSendMessage();
				QueueResBody rb = message.Send(bean);
				if ("AA".equals(rb.getRescode())) {

				} else {
					this.message = rb.getRestext();
				}
				/*if(!"Y".equals(eu.getStatus())){
				QueueAddBean bn = new QueueAddBean();
				bn.setCenter_num(user.getCenter_num());
				bn.setExam_num(model.getExam_num());
				QueueAddToSendMessage mess = new QueueAddToSendMessage();
				rb = mess.Send(bn);
				if ("AA".equals(rb.getRescode())) {

				} else {
					this.message = rb.getRestext();
				}
				}*/
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb70");//子功能id 必须填写
			sl.setExplain(bc.getExaminfo_id()+" "+chargids);//操作说明
			syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	

	/**
	 * 
	 * @Title: getDjtRegisterHistrjn @Description: 登记台历史对比
	 *         413 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String getDjtRegisterHistrjn() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("413");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	



	/**
	 * 
	 * @Title: djtexambaodaolistshow @Description: 身份证查询多条体检信息报道列表
	 *         426 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtexambaodaolistshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getDjtExamInfobaodaoList(this.model.getId_num(),
				user.getCenter_num() + "");
		this.outJsonResult(contractlist);
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("426");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}

	/**
	 * 
	 * @Title: findcustOneDingwei @Description:
	 *         定位 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @param: @throws Exception @return: String @throws
	 */
	public String findcustOneDingwei() throws WebException, SQLException, Exception {
		this.model.setCustname(URLDecoder.decode(this.model.getCustname(), "UTF-8"));
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: findcustOneDingweishow @Description: 定位显示人员列表
	 *         290 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @param: @throws Exception @return: String @throws
	 */
	public String findcustOneDingweishow() throws WebException, SQLException, Exception {
		PageReturnDTO contractlist = new PageReturnDTO();
		if (this.model.getCustname() != null) {
			this.model.setCustname(URLDecoder.decode(this.model.getCustname(), "UTF-8"));
			contractlist = this.customerInfoService.getExamInfoForNameIdmunExamNumList(this.model.getExam_num(),
					this.model.getId_num(), this.model.getCustname(), this.model.getCompany_id(),
					this.model.getArch_num(), this.model.getSex(), this.model.getTel(), this.rows, this.getPage());
		}
		this.outJsonResult(contractlist);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("290");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}

	/**
	 * 
	 * @Title: findcustOneDingweishow @Description:
	 *         按照查询条件统计人员数291 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @param: @throws
	 *         Exception @return: String @throws
	 */
	public String countcustomer() throws WebException, SQLException, Exception {
		String contractlist = "0-查询失败";
		if (this.model.getCustname() != null) {
			contractlist = this.customerInfoService.getCountExamInfoForNameIdmunExamNumList(this.model.getExam_num(),
					this.model.getId_num(), this.model.getCustname()) + "";
		}
		this.outJsonStrResult(contractlist);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("291");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	
	
	/**
	 * 
	 * @Title: findcustOneDingweishow @Description:
	 *         按照查询条件统计人员数497 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @param: @throws
	 *         Exception @return: String @throws
	 */
	public String countcustomerForidnum() throws WebException, SQLException, Exception {
		String contractlist = "0-查询失败";
		if (this.model.getCustname() != null) {
			contractlist = this.customerInfoService.getCountExamInfoForNameIdmunList(this.model.getId_num());
		}
		this.outJsonStrResult(contractlist);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("497");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}

	/**
	 * 
	 * @Title: getDjtExamOneShow @Description: 登记台显示人员信息
	 *         292 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @param: @throws Exception @return: String @throws
	 */
	public String getDjtExamOneShow() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if (!StringUtils.isEmpty(this.model.getExam_num())) {
			eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if (eu.getJoin_date() != null) {
				if (eu.getJoin_date().indexOf("1900") > -1) {
					eu.setJoin_date("");
				}
			}
			if (eu.getRegister_date() != null) {
				if (eu.getRegister_date().indexOf("1900") > -1) {
					eu.setRegister_date("");
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("292");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(eu);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getDjtExamOneShowidnum   
	     * @Description: 498   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getDjtExamOneShowidnum() throws WebException, SQLException, Exception {
		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		if (this.model.getCustomer_id() > 0) {
			eu = this.customerInfoService.getExamInfoUserForCustomerId(this.model.getCustomer_id());			
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("498");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(eu);
		return NONE;
	}
	/**
	 * 
	 * @Title: djteditTcustomerdo @Description:
	 *         登记台团体增加人员 414 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @param: @throws
	 *         Exception @return: String @throws
	 */
	

	/**
	 * 
	     * @Title: djtGcustomerguahao   
	     * @Description: 手工挂号 430   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtGcustomerguahao() throws WebException {
		
		UserDTO user = (UserDTO) session.get("username");
		if(StringUtils.isEmpty(this.model.getExam_num())){
			this.message="error-无效的体检编号";
		}else{
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if(eu.getFreeze()==1){
				this.message = "error-体检信息已冻结，操作不能继续";
			}else{
			try {
				if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_GCUSTOM_GH", user.getCenter_num()).getConfig_value())) {
						WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
						wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION", user.getCenter_num());									
						String web_url = wcd.getConfig_url().trim();
						String web_meth=wcd.getConfig_method().trim();
						ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(this.model.getExam_num());
						if("Y".equals(ei.getIs_Active())){
						Custom st = new Custom();
						st.setCenter_num(user.getCenter_num());
						st.setPATIENT_ID(ei.getExam_num());
						st.setEXAM_NUM(ei.getExam_num());
						st.setNAME(ei.getUser_name());
						st.setNAME_PHONETIC("");
						st.setSEX(ei.getSex());// 男 女
						st.setDATE_OF_BIRTH(ei.getBirthday());
						st.setBIRTH_PLACE("");
						st.setNATION("");
						st.setCITIZENSHIP("");
						st.setID_NO(ei.getId_num());
						st.setIDENTITY("一般人员");
						st.setUNIT_IN_CONTRACT("");
						st.setMAILING_ADDRESS("");
						st.setZIP_CODE("");
						st.setPHONE_NUMBER_BUSINESS("");
						st.setPHONE_NUMBER_HOME("");
						st.setNEXT_OF_KIN("");
						st.setRELATIONSHIP("");
						st.setNEXT_OF_KIN_ADDR("");
						st.setNEXT_OF_KIN_PHONE("");
						st.setNEXT_OF_KIN_ZIP_CODE("");
						st.setOPERATOR(user.getUserid() + "");
						st.setBUSINESS_ZIP_CODE("");
						st.setPHOTO("");
						st.setPATIENT_CLASS("");
						st.setDEGREE("");
						st.setE_NAME("");
						st.setOCCUPATION("");
						st.setNATIVE_PLACE("");
						st.setMAILING_ADDRESS_CODE("");
						st.setMAILING_STREET_CODE("");
						st.setALERGY("");
						st.setMARITAL_STATUS(ei.getIs_marriage());
						st.setNEXT_OF_SEX("");
						st.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
						st.setOPERATORS("");
						st.setCARD_NAME("");
						st.setCARD_NO("1");// 1-非院内卡挂号，院内卡挂号就传卡号
						st.setINVOICE_NO("");
						st.setCLINIC_NO("");
						st.setCLINIC_DATE_SCHEDULED("");
						st.setCHARGE_TYPE("自费");
						st.setAGE(ei.getAge() + "");
						st.setNEXT_OF_BATH("");
							CUSTOMSendMessage csm = new CUSTOMSendMessage(st);
							ResultBody fr = new ResultBody();
							fr = csm.customSend(web_url,web_meth, true);
							if ("AA".equals(fr.getResultHeader().getTypeCode())) {
								ExamInfo einold=new ExamInfo();
								einold= this.customerInfoService.getExamInfoForExamNum(ei.getExam_num());								
								einold.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());								
								einold.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
								einold.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
								einold.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
								einold.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
								this.customerInfoService.updateExamInfo(einold);
								SysLog sl =  new SysLog();
								sl.setCenter_num(user.getCenter_num());		
								sl.setUserid(user.getUserid()+"");
								sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
								sl.setXtgnid("");//可不填写
								sl.setXtgnid2("430");//子功能id 必须填写
								sl.setExplain("登记台人员挂号 "+ei.getExam_num()+" "+ei.getUser_name()+" "+einold.getPatient_id()+" "+einold.getVisit_no()+" "+einold.getVisit_date()+" "+einold.getClinic_no());//操作说明
								syslogService.saveSysLog(sl);
								this.message="ok-"+ei.getId();
							}else{
								this.message="error-"+fr.getResultHeader().getText();
							}
						}else{
							this.message="error-体检信息状态无效！";
						}
				}else{
					this.message="error-字典配置无需调用接口";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				this.message="error-调用挂号接口错误";
			}
		}
		}	
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: zybdjtcustchangitemlist   
	     * @Description: 职业病登记台获取收费项目列表 zyb63   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybdjtcustchangitemlist() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
		if (!StringUtil.isEmpty(model.getExam_num())) {
			list = this.zybCustomerInfoService.getZybExaminfoChargingItemAllforExamId(this.model.getExam_num(), user.getCenter_num());
		}
		this.outJsonResult(list);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("294");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtcustomeritemaddshow @Description: 登记台个人加项页面
	 *         295 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String djtcustomeritemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("295");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: djtcustomeritemaddshow @Description: 登记台个人加项页面
	 *         415 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String djtTTcustomeritemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("415");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: exam_tclistshow @Description:人员套餐信息
	 *         296 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String exam_tclistshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.djtCustomerInfoService.getExamSetList(this.model.getExam_num(), 1000, 1,user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGcustSaveItemSet @Description:
	 *         登记台个人保存套餐和收费项目 297 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String djtGcustSaveItemSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		if (eu.getFreeze() == 1) {
			this.message = "error-体检信息已冻结，不能进行操作！";
		} else {
			ExaminfoSetDTO bc = new ExaminfoSetDTO();
			bc.setAmount(model.getAmount());
			bc.setExam_num(model.getExam_num());
			bc.setDiscount(model.getDiscount());
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
			String chargids = "";
			boolean fxitemflag = true; // 分项金额判断
			for (GroupChargingItemDTO gci : listitem) {
				chargids = chargids + "" + gci.getCharge_item_id() + ",";
				BigDecimal bd = new BigDecimal(0);
				bd = new BigDecimal(gci.getTeam_pay());
				double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				bd = new BigDecimal(gci.getAmount());
				double pamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				double amount = 0;
				if (gci.getDiscount() > 0) {
					bd = new BigDecimal(gci.getItem_amount()*gci.getItemnum() * gci.getDiscount() / 10);
					amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				} else {
					amount = tamt + pamt;
				}
				if (java.lang.Math.abs(amount - (tamt + pamt)) > 10) {
					fxitemflag = false;
					break;
				}
			}

			if (StringUtils.isEmpty(this.model.getExam_num())) {
				this.message = "error-无效人员，不能加项！";
			}

				// this.message = this.batchService.saveGroup(bc, listitem,
				// listset);
				String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
				this.message = this.djtCustomerInfoService.djtaddExamInfoGChangItem(bc, listitem, listset, "G",IS_CHARGINGWAY_ZERO,
						user.getUserid(), user.getName(), user.getCenter_num());
				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
						.getConfig_value().trim();
				if ("Y".equals(is_queue_check)) {
					QueueCustomerBean bean = new QueueCustomerBean();
					bean.setCenter_num(user.getCenter_num());
					bean.setExam_num(model.getExam_num());
					QueueCustomSendMessage message = new QueueCustomSendMessage();
					QueueResBody rb = message.Send(bean);
					if ("AA".equals(rb.getRescode())) {

					} else {
						this.message = rb.getRestext();
					}
					/*if(!"Y".equals(eu.getStatus())){
					QueueAddBean bn = new QueueAddBean();
					bn.setCenter_num(user.getCenter_num());
					bn.setExam_num(model.getExam_num());
					QueueAddToSendMessage mess = new QueueAddToSendMessage();
					rb = mess.Send(bn);
					if ("AA".equals(rb.getRescode())) {

					} else {
						this.message = rb.getRestext();
					}
					}*/
				}
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("297");// 子功能id 必须填写
				sl.setExplain(bc.getExaminfo_id() + "  " + chargids);// 操作说明
				syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateSampleExamEetaillis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateSampleExamEetaillis() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效人员，不能进行操作！";
		} else {
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if (eu.getFreeze() == 1) {
				this.message = "error-体检信息已冻结，不能进行操作！";
			} else {
				boolean taoverflag = false;
				boolean teamaccflag = false;
				if ("T".equals(eu.getExam_type())) {
					Batch taover = new Batch();
					taover = this.batchService.getBatchByID(eu.getBatch_id());
					if ("1".equals(taover.getOverflag())) {
						taoverflag = true;
					} else {
						TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
						ta = this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
						if ((ta != null) && (ta.getId() > 0)) {
							teamaccflag = true;
						}
					}
				}
				if (taoverflag) {
					this.message = "error-批次任务已经封帐，操作不能继续";
				} else if (teamaccflag) {
					this.message = "error-体检信息已经结算，操作不能继续";
				} else {
					String[] sample_exam_details = this.model.getIds().split(",");
					for (int i = 0; i < sample_exam_details.length; i++) {
						String sample_exam_detail = sample_exam_details[i];
						String lisstatus = this.customerInfoService.getCenterconfigByKey("IS_SAMPLE_STATUS", user.getCenter_num())
								.getConfig_value().trim();
						if ("Y".equals(lisstatus)) {
							this.customerInfoService.updateSampleExamEetaillis(
									Long.valueOf(sample_exam_detail).longValue(), user.getUserid());
						}
						this.message = "ok-操作完成！";
					}
				}
			}
		}
		this.outJsonStrResult("ok-操作完成");
		return NONE;
	}

	
	/**
	 * 
	     * @Title: updateSampleExamEetail   
	     * @Description: 打印条码直接采样   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String updateSampleExamEetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效人员，不能进行操作！";
		} else {
			String[] exam_nums=this.model.getIds().split("[$]");
			for(int i=0;i<exam_nums.length;i++){
				String exam_num=exam_nums[i];

			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamNum(exam_num,user.getCenter_num());
			boolean taoverflag = false;
			boolean teamaccflag = false;
			if(eu.getFreeze()==1){
				this.message = "error-体检信息已冻结，操作不能继续";
				break;
			}else if ("T".equals(eu.getExam_type())) {
				Batch taover = new Batch();
				taover = this.batchService.getBatchByID(eu.getBatch_id());
				if ("1".equals(taover.getOverflag())) {
					taoverflag = true;
				} else {
					TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
					ta = this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
					if ((ta != null) && (ta.getId() > 0)) {
						teamaccflag = true;
					}
				}
			}
			if (taoverflag) {
				this.message = "error-批次任务已经封帐，操作不能继续";
				break;
			} else if (teamaccflag) {
				this.message = "error-体检信息已经结算，操作不能继续";
				break;
			} else {
				String lisstatus = this.customerInfoService.getCenterconfigByKey("IS_SAMPLE_STATUS", user.getCenter_num()).getConfig_value()
						.trim();
				if ("Y".equals(lisstatus)) {
					this.customerInfoService.updateSampleExamEetail(eu.getId(), user.getUserid());
				}
				this.message = "ok-操作完成！";
			}
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtcustomeritemdelshow @Description: 登记台减项
	 *         298 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtcustomeritemdelshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtils.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能进行减项！";
		} else if ((this.model.getIds() == null) || this.model.getIds().trim().length() <= 0) {
			this.message = "error-无效体检项目，不能进行减项！";
		} else {
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if(eu.getFreeze()==1){
				this.message = "error-体检信息被冻结，不能进行减项！";
			}else if("Z".equals(eu.getStatus())){
				this.message = "error-体检信息已总检，操作不能继续";
		    }else{
				String accountflagAndOverMsg = "ok";
				boolean teamaccflag=false;
				if("T".equals(eu.getExam_type())){
					accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(eu.getBatch_id());
					Batch taover = new Batch();
					taover = this.batchService.getBatchByID(eu.getBatch_id());
					TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
					ta=this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
					if((ta!=null)&&(ta.getId()>0)){
						teamaccflag=true;
					}
				}
			if (!"ok".equals(accountflagAndOverMsg)) {
				this.message = accountflagAndOverMsg;
			} else if (teamaccflag) {
				this.message = "error-体检信息已经结算，操作不能继续";
			} else {
			if((this.model.getIds()==null)||(this.model.getIds().length()<=0))
			{
				this.message = "error-无效项目";	
			}else{
			/*this.message = this.djtCustomerInfoService.djtdelExamInfoGChangItem(model.getExam_id(), this.model.getIds(),
					user.getUserid());*/
			String[] idslist=this.model.getIds().trim().split(",");
			for(int i=0;i<idslist.length;i++){
				//long item_id=Long.valueOf(idslist[i]);
				String item_code = idslist[i];
				this.customerInfoService.delIDSExamInfoChangItem(this.model.getExam_num(),item_code,user.getUserid(),user);
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());		
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("298");//子功能id 必须填写
				sl.setExplain(model.getExam_num()+"  "+this.model.getIds());//操作说明
				syslogService.saveSysLog(sl);
			}
			String his = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();			
			try {
				if("Y".equals(his)){
					String str = this.lisPacsApplicationService.delFeeSend(eu.getExam_num(), this.model.getOthers(), user);
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());		
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("298");//子功能id 必须填写
					sl.setExplain(model.getExam_num()+"  "+this.model.getOthers());//操作说明
					syslogService.saveSysLog(sl);
					if(!"no".equals(str)){
						this.message = str;
					}
				}
			} catch (ServiceException e) {
				this.message = "error-"+e.getMessage();
				e.printStackTrace();
			}
			this.message = "ok-操作完成！";
			}
		}
		}
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtcustomersetdelshow @Description: 登记台删除套餐信息
	 *         299 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtcustomersetdelshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtils.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能删除套餐！";
		} else if ((this.model.getIds() == null) || this.model.getIds().trim().length() <= 0) {
			this.message = "error-无效体检项目，不能删除套餐！";
		} else {
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if (eu.getFreeze() == 1) {
				this.message = "error-体检信息已冻结，操作不能继续";
			}else if("Z".equals(eu.getStatus())){
				this.message = "error-体检信息已总检，操作不能继续";
		    }else {
				this.message = this.djtCustomerInfoService.djtdelExamInfoGChangSet(model.getExam_num(),
						this.model.getIds(), user.getUserid(),user.getCenter_num());
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("299");// 子功能id 必须填写
				sl.setExplain(model.getExam_num() + "  " + this.model.getIds());// 操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtdelregisterdo @Description: 取消报到
	 *         400 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtdelregisterdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getIds() == null) || (this.model.getIds().length() <= 0)) {
			this.message = "error-无效人员，不能取消报到！";
		} else {
			this.message = this.djtCustomerInfoService.djtdelRegister(this.model.getIds(), user.getUserid(),
					user.getCenter_num());
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("400");//子功能id 必须填写
			sl.setExplain(this.model.getIds());//操作说明
			syslogService.saveSysLog(sl);
			
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			String his = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			
			try {
				if("Y".equals(lis) && "N".equals(lis_bang)){
					this.message = this.lisPacsApplicationService.lisDel(eu.getExam_num(), null, user);
					
					sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());		
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("400");//子功能id 必须填写
					sl.setExplain("删除lis申请"+eu.getExam_num());//操作说明
					syslogService.saveSysLog(sl);
					
				}
				
				if("Y".equals(pacs)){
					this.message = this.lisPacsApplicationService.pacsDel(eu.getExam_num(), null, user);
					sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());		
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("400");//子功能id 必须填写
					sl.setExplain("删除pacs申请"+eu.getExam_num());//操作说明
					syslogService.saveSysLog(sl);
				}
				if("Y".equals(his)){
					this.message = this.lisPacsApplicationService.delFeeSend(eu.getExam_num(), null, user);
					sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());		
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("400");//子功能id 必须填写
					sl.setExplain("删除fee"+eu.getExam_num());//操作说明
					syslogService.saveSysLog(sl);
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
	 * 
	 * @Title: editregisterdatedo @Description: 登记台预约改期
	 *         401 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String editregisterdatedo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getIds() != null) && (this.model.getIds().trim()).length() > 1) {
			String t1 = this.model.getTime1().replaceAll(":", "");
			String t2 = this.model.getTime2().replaceAll(":", "");
			if (Integer.valueOf(t1).intValue() >= Integer.valueOf(t2).intValue()) {
				this.message = "error-时间段设置错误！";
			} else {
				this.customerInfoService.setExamInfotime(this.model.getIds(), this.model.getBirthday(),
						this.model.getTime1(), this.model.getTime2(), user.getUserid());
				this.message = "ok-体检时间设置完成。";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());		
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("401");//子功能id 必须填写
				sl.setExplain(this.model.getIds()+" "+this.model.getTime1()+" "+this.model.getTime2());//操作说明
				syslogService.saveSysLog(sl);
			}

		} else {
			this.message = "error-无效人员信息。";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: editregisterdateshow @Description: 登记台预约改期显示
	 *         402 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String editregisterdateshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model
				.setTime1(this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("402");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: saveExamInfoPic @Description: 保存图片 @param: @param
	 *         examid @param: @param picstring @param: @param
	 *         flags @param: @return @return: String @throws
	 */
	private String saveExamInfoPic(String exam_num, String picstring, boolean flags) {
		String filepath = "";
		//System.out.println("0000:="+picstring);
		if ((picstring != null) && (picstring.length() > 500)) {
			String bmpfiledata = picstring;
			
			try {
				if (flags) {
					ExamInfo ei = new ExamInfo();
					ei = this.customerInfoService.getExamInfoForExamNum(exam_num);
					if ((ei.getPicture_path() == null) || (ei.getPicture_path().trim().length() <= 10)) {
						byte[] bmpfiledata64 = Base64.base64Decode(bmpfiledata);

						String picpath = this.commService.getDatadis("TPLJ").getName();
						String path = picpath + "\\";
						filepath = "customer-picture/" + ValidateTime.getDayTimeAll() + ".jpg";
						File f = new File(path);
						if (!f.exists() && !f.isDirectory())
							f.mkdir();
						f = new File(path+"\\customer-picture");
						if (!f.exists() && !f.isDirectory())
							f.mkdir();
						path = path + filepath;
						FileOutputStream fos = new FileOutputStream(path);
						// 用FileOutputStream 的write方法写入字节数组
						fos.write(bmpfiledata64);
						System.out.println("写入成功");
						fos.close();
						ei.setPicture_path(filepath);
						this.customerInfoService.SaveExamInfo(ei);
					}
				} else {
					//System.out.println("0000000="+bmpfiledata);
					byte[] bmpfiledata64 = Base64.base64Decode(bmpfiledata);
					String picpath = this.commService.getDatadis("TPLJ").getName();
					String path = picpath + "\\";
					//System.out.println("111111="+bmpfiledata);
					File f = new File(path);
					if (!f.exists() && !f.isDirectory())
						f.mkdir();
					path = path+"\\customer-picture";
					f = new File(path+"\\customer-picture");
					if (!f.exists() && !f.isDirectory())
						f.mkdir();
					String picfilename=ValidateTime.getDayTimeAll();
					filepath = "customer-picture/" + picfilename + ".jpg";
					//System.out.println("22222="+filepath);
					path = path + "/" + picfilename + ".jpg";;
					FileOutputStream fos = new FileOutputStream(path);
					//System.out.println("33333="+path);
					fos.write(bmpfiledata64);
					System.out.println("写入成功");
					fos.close();

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return filepath;
	}

	/**
	 * 
	 * @Title: djtcutsavePicture @Description:摄像头剪切保存图片和上传图片
	 *         424 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String djtcutsavePicture() throws WebException {
		String formatName = "jpg";
		response.setContentType("text/html;charset=UTF-8");
		String filepath = "";
		message = "error&照片保存出差";

		if ("ok".equals(this.model.getOthers())) {
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\";
				File f_dir = new File(path);
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}

				f_dir = new File(path+"customer-picture\\");
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				
				InputStream sis = null;
				ImageInputStream iis = null;
				Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(formatName);
				ImageReader reader = readers.next();
				sis = request.getInputStream();
				iis = ImageIO.createImageInputStream(sis);
				reader.setInput(iis, true);
				ImageReadParam param = reader.getDefaultReadParam();
				Rectangle rect = new Rectangle(60, 0, 200, 240);
				param.setSourceRegion(rect);
				BufferedImage bi = reader.read(0, param);
				filepath = "customer-picture/" + ValidateTime.getDayTimeAll() + ".jpg";
				path = path + filepath;
				File file = new File(path);
				file.createNewFile();
				boolean writerExists = ImageIO.write(bi, formatName, file);
				if (writerExists) {
					if (!StringUtils.isEmpty(this.model.getExam_num())) {
						ExamInfo ei = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
						ei.setPicture_path(filepath);
						this.customerInfoService.SaveExamInfo(ei);
						UserDTO user = (UserDTO) session.get("username");
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());		
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("424");//子功能id 必须填写
						sl.setExplain(this.model.getExam_num()+"");//操作说明
						syslogService.saveSysLog(sl);
					}
					this.message = "ok&" + filepath;
				} else {
					this.message = "error&相片上传失败";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				this.message = "error&相片上传失败";
			}
			this.outJsonStrResult(this.message);
			return NONE;
		} else {
			JSONObject json = new JSONObject();
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\";
				File f_dir = new File(path);
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				
				f_dir = new File(path+"customer-picture\\");
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				
				
				MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();

				UploadedFile[] files = multiWrapper.getFiles("customImageInfoImport");

				if ((files != null) && (files.length > 0)) {
					InputStream sis1 = new FileInputStream(files[0].getAbsolutePath());

					filepath = "customer-picture/" + ValidateTime.getDayTimeAll() + ".jpg";
					path = path + filepath;
					File file = new File(path);
					file.createNewFile();
					BufferedImage image = ImageIO.read(sis1);

					int hight = image.getHeight();
					int width = image.getWidth();

					if (hight != 240 && width != 200) {
						json.put("msg", "上传头像图片尺寸应为200x240");
						json.put("state", "N");
					} else {
						boolean writerExists = ImageIO.write(image, formatName, file);
						if (writerExists) {
							if (!StringUtils.isEmpty(this.model.getExam_num())) {
								ExamInfo ei = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
								ei.setPicture_path(filepath);
								this.customerInfoService.SaveExamInfo(ei);
							}
							json.put("msg", filepath);
							json.put("state", "Y");
						} else {
							json.put("msg", "相片文件保存");
							json.put("state", "N");
						}
					}
				} else {
					json.put("msg", "相片文件不存在");
					json.put("state", "N");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				json.put("msg", "相片上传失败");
				json.put("state", "N");
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
		}

	}

	/**
	 * 
	 * @Title: getdjtexamPhoto 423 @Description:
	 *         显示照片 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String getdjtexamPhoto() throws WebException {
		if ((this.model.getOthers() != null) && (this.model.getOthers().trim().length() > 0)) {
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\";
				String filepath = this.model.getOthers();
				path = path + filepath;
				File f = new File(path);
				if (f.exists()) {
					FileInputStream fileInput = new FileInputStream(path);
					int i = fileInput.available();
					byte[] content = new byte[i];
					fileInput.read(content);
					HttpServletResponse response = ServletActionContext.getResponse();
					response.reset();
					response.setContentType("image/jpeg");
					ServletOutputStream sos = response.getOutputStream();
					sos.write(content);
					sos.flush();
					sos.close();
				}
			} catch (Exception ex) {

			}
		}
		return NONE;
	}

	/**
	 * 
	 * @Title: djtuploadPicexamInfo @Description: 读取身份证保存图片
	 *         422 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtuploadPicexamInfo() throws WebException, SQLException {
		String picpath = saveExamInfoPic(this.model.getExam_num(), this.model.getOthers(), false);
		this.outJsonStrResult("ok&" + picpath);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtcheckexamInfoStatus @Description:
	 *         TODO(这里用一句话描述这个方法的作用) 403 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String djtcheckexamInfoStatus() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		List<ExamInfoDTO> webrole = new ArrayList<ExamInfoDTO>();
		webrole = this.customerInfoService.getExamInfoForexamNum(this.model.getExam_num(), this.model.getId_num(),
				user.getCenter_num());
		if ((webrole != null) && (webrole.size() == 1)) {
			ExamInfoDTO ed = new ExamInfoDTO();
			ed = webrole.get(0);
			saveExamInfoPic(ed.getExam_num(), this.model.getOthers(), true);
			this.message = "ok-" + ed.getExam_num();
		} else if ((webrole != null) && (webrole.size() > 1)) {
			this.message = "okmore-0";
		} else {
			this.message = "error-无报到信息";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: djtexambaodaoshow   
	     * @Description: 身份证查询多条体检信息报到 425
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String djtexambaodaoshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("425");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: djtexamInfoStatusdo @Description: 登记台报到
	 *         404 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtexamInfoStatusdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoUserDTO ei= new ExamInfoUserDTO();
		ei=this.customerInfoService.getExamInfoForNum(this.model.getExam_num(),user);
//		boolean taoverflag=false;
//		boolean teamaccflag=false;
		String accountflagAndOverMsg = "ok";
		if("T".equals(ei.getExam_type())){
			accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(ei.getBatch_id());
		}

		if (!"ok".equals(accountflagAndOverMsg)) {
			this.message = accountflagAndOverMsg;
		} else if(ei.getFreeze()==1){
			this.message = "error-体检信息被冻结，操作不能继续";
		}else{
//		if("T".equals(ei.getExam_type())){
//			Batch taover = new Batch();
//			taover = this.batchService.getBatchByID(ei.getBatch_id());
//			if ("1".equals(taover.getOverflag())) {
//				taoverflag=true;
//			}else{
//				TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
//				ta=this.teamAccountService.getTeamAccountExamListforExamNum(ei.getExam_num());
//				if((ta!=null)&&(ta.getId()>0)&&("N".equals(ta.getIsPrePay()))){  //Y 表示预结算，N表示非预结算
//					teamaccflag=true;
//				}
//			}				
//		}			
//		if (taoverflag) {
//			this.message = "error-批次任务已经封帐，操作不能继续";
//		} else if (teamaccflag) {
//			this.message = "error-体检信息已经结算，操作不能继续";
//		} else {
		this.message = this.djtCustomerInfoService.examInfoStatusEdit(this.model.getExam_num(), user.getUserid(),
				user.getCenter_num());	
		if (this.message.indexOf("ok") == 0) {
			try {
				if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE", user.getCenter_num()).getConfig_value())) {
					if (("Y".equals(ei.getIs_Active())) && (!StringUtil.isEmpty(ei.getId_num()))
							&& (("0").equals(ei.getFlag())) && (ei.getCustomer_id() > 0)) {

						String dah = ei.getArch_num();
						String cusflag = "0";
						DAHSendMessage dahsend= new DAHSendMessage();
						DAHCustomerBean dahcu = new DAHCustomerBean();
						dahcu.setCenter_num(user.getCenter_num());
						dahcu.setBrid(ei.getBirthday());
						dahcu.setId_num(ei.getId_num());
						dahcu.setName(ei.getUser_name());
						dahcu.setSex(ei.getSex());
						DAHResBody tr=new DAHResBody();
						tr=dahsend.dahSend(dahcu,dah);
						if("ok".equals(tr.getRescode())){
							dah=tr.getIdnumber();
								cusflag="1";
							}else{
								dah=this.batchService.GetCreateID("vipno", user.getCenter_num());
								cusflag="0";
							}

						CustomerInfo cti = new CustomerInfo();
						cti = this.customerInfoService.getCustomerInfoForId(ei.getCustomer_id());
						cti.setArch_num(dah);
						cti.setFlag(cusflag);

						this.customerInfoService.updateCustomerInfo(cti);
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("830");// 子功能id 必须填写
						sl.setExplain(
								"登记台同步档案号 " + ei.getExam_num() + " " + ei.getArch_num() + " " + cti.getArch_num());// 操作说明
						syslogService.saveSysLog(sl);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		

			// 报道发送挂号请求
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("404");//子功能id 必须填写
			sl.setExplain(this.model.getExam_num());//操作说明
			syslogService.saveSysLog(sl);
			try {
				//ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(this.model.getExam_num());
				if ("Y".equals(ei.getIs_Active())) {
					boolean ghflags = false;
					if (("G".equals(ei.getExam_type())) && ("Y".equals(
							this.customerInfoService.getCenterconfigByKey("IS_GCUSTOM_GH", user.getCenter_num()).getConfig_value()))) {
						ghflags = true;
					}
					if (("T".equals(ei.getExam_type())) && ("Y".equals(
							this.customerInfoService.getCenterconfigByKey("IS_TCUSTOM_GH", user.getCenter_num()).getConfig_value()))) {
						ghflags = true;
					}
					
					if (ghflags) {
						Custom st = new Custom();
						st.setCenter_num(user.getCenter_num());
						st.setPATIENT_ID(ei.getExam_num());
						st.setEXAM_NUM(ei.getExam_num());
						st.setNAME(ei.getUser_name());
						st.setNAME_PHONETIC("");
						st.setSEX(ei.getSex());// 男 女
						st.setDATE_OF_BIRTH(ei.getBirthday());
						st.setBIRTH_PLACE("");
						st.setNATION("");
						st.setCITIZENSHIP("");
						st.setID_NO(ei.getId_num());
						st.setIDENTITY("一般人员");
						st.setUNIT_IN_CONTRACT("");
						st.setMAILING_ADDRESS("");
						st.setZIP_CODE("");
						st.setPHONE_NUMBER_BUSINESS("");
						st.setPHONE_NUMBER_HOME("");
						st.setNEXT_OF_KIN("");
						st.setRELATIONSHIP("");
						st.setNEXT_OF_KIN_ADDR("");
						st.setNEXT_OF_KIN_PHONE("");
						st.setNEXT_OF_KIN_ZIP_CODE("");
						st.setOPERATOR(user.getUserid() + "");
						st.setBUSINESS_ZIP_CODE("");
						st.setPHOTO("");
						st.setPATIENT_CLASS("");
						st.setDEGREE("");
						st.setE_NAME("");
						st.setOCCUPATION("");
						st.setNATIVE_PLACE("");
						st.setMAILING_ADDRESS_CODE("");
						st.setMAILING_STREET_CODE("");
						st.setALERGY("");
						st.setMARITAL_STATUS(ei.getIs_marriage());
						st.setNEXT_OF_SEX("");
						st.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
						st.setOPERATORS("");
						st.setCARD_NAME("");
						st.setCARD_NO("1");// 1-非院内卡挂号，院内卡挂号就传卡号
						st.setINVOICE_NO("");
						st.setCLINIC_NO(ei.getClinic_no());
						st.setCLINIC_DATE_SCHEDULED("");
						st.setCHARGE_TYPE("自费");
						st.setAGE(ei.getAge() + "");
						st.setNEXT_OF_BATH("");
						if ((ei.getPatient_id().length() <= 0) || (ei.getClinic_no().length() <= 0)
								|| (ei.getVisit_date().length() < 8) || (this.model.getVisit_no().length() < 0)) {
							WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
							wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION", user.getCenter_num());
							String web_url = wcd.getConfig_url().trim();
							String web_meth = wcd.getConfig_method().trim();
							CUSTOMSendMessage csm = new CUSTOMSendMessage(st);
							ResultBody fr = new ResultBody();
							fr = csm.customSend(web_url, web_meth, true);
							if ("AA".equals(fr.getResultHeader().getTypeCode())) {
								ExamInfo einew = new ExamInfo();
								einew = this.customerInfoService.getExamInfoForExamNum(ei.getExam_num());
								einew.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
								einew.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
								einew.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
								einew.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
								this.customerInfoService.updateExamInfo(einew);
								sl =  new SysLog();
								sl.setCenter_num(user.getCenter_num());		
								sl.setUserid(user.getUserid()+"");
								sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
								sl.setXtgnid("");//可不填写
								sl.setXtgnid2("404");//子功能id 必须填写
								sl.setExplain(einew.getExam_num()+" "+einew.getPatient_id()+" "+einew.getVisit_no()+" "+einew.getVisit_date()+" "+einew.getClinic_no());//操作说明
								syslogService.saveSysLog(sl);
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			
			String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value().trim();

			try {
				if ("Y".equals(lis) && "N".equals(lis_bang)) {
					 this.lisPacsApplicationService.lisSend(this.model.getExam_num(), null, user, false,lis_bang,"N");
					 sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());		
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("404");//子功能id 必须填写
						sl.setExplain("发送lis申请："+this.model.getExam_num());//操作说明
						syslogService.saveSysLog(sl);
				}

				if ("Y".equals(pacs)) {
					 this.lisPacsApplicationService.pacsSend(this.model.getExam_num(), null, user, false,"N");
					 sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());		
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("404");//子功能id 必须填写
						sl.setExplain("发送pacs申请："+this.model.getExam_num());//操作说明
						syslogService.saveSysLog(sl);
				}
				// 发送申请 服务
				// this.message = "ok-发送申请完成";
			} catch (ServiceException e) {
				// this.message = "error-"+e.getMessage();
			}
		}
		
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGprintbarshow @Description:
	 *         登记台打印个人条码 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String djtGprintbarshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setDjd_code_url(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: djtGlextitemlshow @Description: 登记台个人list条码显示
	 *         406 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtGlextitemlshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = new PageReturnDTO();
		list = this.djtCustomerInfoService.getLisCountList(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGpacsitemlshow @Description: 登记台个人pacs条码显示
	 *         407 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtGpacsitemlshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = new PageReturnDTO();
		list = this.djtCustomerInfoService.getPacsCountList(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGdellissamp @Description: 登记台删除采样
	 *         408 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtGdellissamp() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		if (eu.getFreeze() == 1) {
			this.message = "error-体检信息已冻结，操作不能继续";
		} else {
			this.djtCustomerInfoService.djtGdellissamp(this.model.getExam_num(), this.model.getId(), user.getUserid(),
					user.getCenter_num());
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("408");// 子功能id 必须填写
			sl.setExplain(this.model.getExam_num() + "");// 操作说明
			syslogService.saveSysLog(sl);
			message = "ok-操作完成";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGgetlisitemcode @Description:
	 *         TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String djtGgetlisitemcode() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		message="";
		if(!StringUtil.isEmpty(this.model.getIds())){
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if(eu.getFreeze()==1){
				this.message = "error-体检信息已冻结，操作不能继续";
			}else{
			    message = this.djtCustomerInfoService.getlisitemcode(this.model.getExam_num(), this.model.getIds(), user.getCenter_num());
			    message = "ok-" + message;
			}
		}else{
			this.message = "ok-ym999";
		}		
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGItemCount @Description: 登记台收费项目统计
	 *         412 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtGItemCount() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		DjtTotalDTO dd = new DjtTotalDTO();
		if ("G".equals(this.model.getExam_type())) {
			dd = this.djtCustomerInfoService.getdjtGItemCount(this.model.getExam_num(), user.getCenter_num());
		} else {
			dd = this.djtCustomerInfoService.getdjtTItemCount(this.model.getExam_num(), user.getCenter_num());
		}
		this.outJsonResult(dd);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtGcustSaveItemSet @Description:
	 *         登记台团体加项保存套餐和收费项目 416 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String djtTTcustSaveItemSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		
		ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		if(eu.getFreeze()==1){
			this.message = "error-体检信息已冻结，操作不能继续";
		}else{
		boolean taoverflag = false;
		boolean teamaccflag = false;
		Batch taover = new Batch();
		taover = this.batchService.getBatchByID(eu.getBatch_id());
		if ("1".equals(taover.getOverflag())) {
			taoverflag = true;
		} else {
			TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
			ta = this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
			if ((ta != null) && (ta.getId() > 0)) {
				teamaccflag = true;
			}
		}
		String accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(eu.getBatch_id());
		if (!"ok".equals(accountflagAndOverMsg)) {
			this.message = accountflagAndOverMsg;
		} else if (teamaccflag) {
			this.message = "error-体检信息已经结算，操作不能继续";
		} else {
		
		ExaminfoSetDTO bc = new ExaminfoSetDTO();
		bc.setAmount(model.getAmount());
		bc.setExam_num(model.getExam_num());
		bc.setDiscount(model.getDiscount());

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
        String chargids="";
		boolean fxitemflag = true; // 分项金额判断
		for (GroupChargingItemDTO gci : listitem) {
			chargids=chargids+""+gci.getItem_code()+",";
			BigDecimal bd = new BigDecimal(0);
			bd = new BigDecimal(gci.getTeam_pay());
			double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			bd = new BigDecimal(gci.getPersonal_pay());
			double pamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			double amount=0;
            if(gci.getDiscount()>0){
            	bd = new BigDecimal(gci.getItem_amount()*gci.getItemnum()* gci.getDiscount() / 10);
    			amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }else{
            	amount=tamt + pamt;
            }
			if (java.lang.Math.abs(amount-(tamt + pamt))>10) {
				fxitemflag = false;
				break;
			}
		}

		if (StringUtils.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能加项！";
		}
			String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
			this.message = this.djtCustomerInfoService.djtaddExamInfoGChangItem(bc, listitem, listset, "T",IS_CHARGINGWAY_ZERO,
					user.getUserid(), user.getName(), user.getCenter_num());
			String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
					.getConfig_value().trim();
			if ("Y".equals(is_queue_check)) {
				QueueCustomerBean bean = new QueueCustomerBean();
				bean.setCenter_num(user.getCenter_num());
				bean.setExam_num(model.getExam_num());
				QueueCustomSendMessage message = new QueueCustomSendMessage();
				QueueResBody rb = message.Send(bean);
				if ("AA".equals(rb.getRescode())) {

				} else {
					this.message = rb.getRestext();
				}
				if(!"Y".equals(eu.getStatus())){
					QueueAddBean bn = new QueueAddBean();
					bn.setCenter_num(user.getCenter_num());
					bn.setExam_num(model.getExam_num());
					QueueAddToSendMessage mess = new QueueAddToSendMessage();
					rb = mess.Send(bn);
					if ("AA".equals(rb.getRescode())) {

					} else {
						this.message = rb.getRestext();
					}
					}
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("416");//子功能id 必须填写
			sl.setExplain(bc.getExaminfo_id()+" "+chargids);//操作说明
			syslogService.saveSysLog(sl);
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtTGcustomeritemaddshow @Description: 登记台个人加项页面
	 *         417 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String djtTGcustomeritemaddshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		@SuppressWarnings("unused")
		List<WebResrelAtionship> webResource = user.getWebResource();
		if( webResource!=null){
			for (int i = 0; i < webResource.size(); i++) {
				if(webResource.get(i).getRes_code().equals("RS003")){
					this.setWeb_Resource(webResource.get(i).getDatavalue());
					break;
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("417");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: djtTGcustSaveItemSet @Description:
	 *         登记台团体加项保存套餐和收费项目 418 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String djtTGcustSaveItemSet() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		if (eu.getFreeze() == 1) {
			this.message = "error-体检信息已冻结，不能进行操作！";
		}else{
			ExaminfoSetDTO bc = new ExaminfoSetDTO();
			bc.setAmount(model.getAmount());
			bc.setExam_num(model.getExam_num());
			bc.setDiscount(model.getDiscount());

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
			boolean hisflag = false;
			boolean fxitemflag = true; // 分项金额判断
			String chargitems="";
			for (GroupChargingItemDTO gci : listitem) {
				chargitems = chargitems+""+gci.getItem_code()+",";
				if (gci.getPersonal_pay() > 0) {
					hisflag = true;
				}
				BigDecimal bd = new BigDecimal(0);
				bd = new BigDecimal(gci.getTeam_pay());
				double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				bd = new BigDecimal(gci.getPersonal_pay());
				double pamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

				double amount = 0;
				if (gci.getDiscount() > 0) {
					bd = new BigDecimal(gci.getItem_amount()*gci.getItemnum() * gci.getDiscount() / 10);
					amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				} else {
					amount = tamt + pamt;
				}

				if ((!"M".equals(gci.getExam_indicators())) && (java.lang.Math.abs(amount-(tamt + pamt))>10)) {
					fxitemflag = false;
					break;
				}
			}

			if (StringUtils.isEmpty(this.model.getExam_num())) {
				this.message = "error-无效人员，不能加项！";
			}

				// this.message = this.batchService.saveGroup(bc, listitem,
				// listset);
				String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
				this.message = this.djtCustomerInfoService.djtaddExamInfoGChangItem(bc, listitem, listset, "T",IS_CHARGINGWAY_ZERO,
						user.getUserid(), user.getName(), user.getCenter_num());
				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
						.getConfig_value().trim();
				if ("Y".equals(is_queue_check)) {
					QueueCustomerBean bean = new QueueCustomerBean();
					bean.setCenter_num(user.getCenter_num());
					bean.setExam_num(model.getExam_num());
					QueueCustomSendMessage message = new QueueCustomSendMessage();
					QueueResBody rb = message.Send(bean);
					if ("AA".equals(rb.getRescode())) {

					} else {
						this.message = rb.getRestext();
					}
					/*if(!"Y".equals(eu.getStatus())){
						QueueAddBean bn = new QueueAddBean();
						bn.setCenter_num(user.getCenter_num());
						bn.setExam_num(model.getExam_num());
						QueueAddToSendMessage mess = new QueueAddToSendMessage();
						rb = mess.Send(bn);
						if ("AA".equals(rb.getRescode())) {

						} else {
							this.message = rb.getRestext();
						}
						}*/
				}
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());		
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("418");//子功能id 必须填写
				sl.setExplain(bc.getExaminfo_id()+" "+chargitems);//操作说明
				syslogService.saveSysLog(sl);
			try {
				if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_TGCUSTOM_GH", user.getCenter_num()).getConfig_value())) {
					String ress[] = this.message.split("-");
					if ("ok".equals(ress[0])) {
						if (hisflag) {
							String exam_num = model.getExam_num();

							ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(exam_num);
							if ("Y".equals(ei.getIs_Active())) {
								Custom st = new Custom();
								st.setCenter_num(user.getCenter_num());
								st.setPATIENT_ID(ei.getExam_num());
								st.setEXAM_NUM(ei.getExam_num());
								st.setNAME(ei.getUser_name());
								st.setNAME_PHONETIC("");
								st.setSEX(ei.getSex());// 男 女
								st.setDATE_OF_BIRTH(ei.getBirthday());
								st.setBIRTH_PLACE("");
								st.setNATION("");
								st.setCITIZENSHIP("");
								st.setID_NO(ei.getId_num());
								st.setIDENTITY("一般人员");
								st.setUNIT_IN_CONTRACT("");
								st.setMAILING_ADDRESS("");
								st.setZIP_CODE("");
								st.setPHONE_NUMBER_BUSINESS("");
								st.setPHONE_NUMBER_HOME("");
								st.setNEXT_OF_KIN("");
								st.setRELATIONSHIP("");
								st.setNEXT_OF_KIN_ADDR("");
								st.setNEXT_OF_KIN_PHONE("");
								st.setNEXT_OF_KIN_ZIP_CODE("");
								st.setOPERATOR(user.getUserid() + "");
								st.setBUSINESS_ZIP_CODE("");
								st.setPHOTO("");
								st.setPATIENT_CLASS("");
								st.setDEGREE("");
								st.setE_NAME("");
								st.setOCCUPATION("");
								st.setNATIVE_PLACE("");
								st.setMAILING_ADDRESS_CODE("");
								st.setMAILING_STREET_CODE("");
								st.setALERGY("");
								st.setMARITAL_STATUS(ei.getIs_marriage());
								st.setNEXT_OF_SEX("");
								st.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
								st.setOPERATORS("");
								st.setCARD_NAME("");
								st.setCARD_NO("1");// 1-非院内卡挂号，院内卡挂号就传卡号
								st.setINVOICE_NO("");
								st.setCLINIC_NO("");
								st.setCLINIC_DATE_SCHEDULED("");
								st.setCHARGE_TYPE("自费");
								st.setAGE(ei.getAge() + "");
								st.setNEXT_OF_BATH("");
								if ((ei.getClinic_no().length() <= 0) || (ei.getVisit_date().length() < 8)
										|| (this.model.getVisit_no().length() < 0)) {
									CUSTOMSendMessage csm = new CUSTOMSendMessage(st);
									ResultBody fr = new ResultBody();
									WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
									wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION", user.getCenter_num());
									String web_url = wcd.getConfig_url().trim();
									String web_meth = wcd.getConfig_method().trim();
									fr = csm.customSend(web_url, web_meth, true);
									if ("AA".equals(fr.getResultHeader().getTypeCode())) {
										ExamInfo eiold = new ExamInfo();
										eiold = this.customerInfoService.getExamInfoForExamNum(exam_num);
										eiold.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
										eiold.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
										eiold.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
										eiold.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
										this.customerInfoService.updateExamInfo(eiold);
										sl =  new SysLog();
										sl.setCenter_num(user.getCenter_num());		
										sl.setUserid(user.getUserid()+"");
										sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
										sl.setXtgnid("");//可不填写
										sl.setXtgnid2("418");//子功能id 必须填写
										sl.setExplain(eiold.getExam_num()+" "+eiold.getPatient_id()+" "+eiold.getVisit_no()+" "+eiold.getVisit_date()+" "+eiold.getClinic_no());//操作说明
										syslogService.saveSysLog(sl);
									}
								}
							}
						}
					}

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtTTcustomeritemchargeshow @Description: 登记台团体换项
	 *         419 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String djtTTcustomeritemchargeshow() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("419");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: djtTTcustchangitemlist @Description: 登记台团体换项查询
	 *         420 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String djtTTcustchangitemlist() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
		if (!StringUtils.isEmpty(this.model.getExam_num())) {
			list = this.customerInfoService.getExaminfoChargingItemforExamIdIds(this.model.getExam_num(),
					this.model.getIds(), user.getCenter_num());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("420");//子功能id 必须填写
			sl.setExplain(this.model.getExam_num()+" "+this.model.getIds());//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	 * @Title: djtTTcustchangSaveItemSet @Description: 登记台团体执行换项
	 *         421 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String djtTTcustchangSaveItemSet() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		ExaminfoSetDTO bc = new ExaminfoSetDTO();
		bc.setAmount(model.getAmount());
		bc.setExam_num(model.getExam_num());
		bc.setDiscount(model.getDiscount());

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
		List<GroupChargingItemDTO> oldlistitem = (List<GroupChargingItemDTO>) JSONArray.toCollection(setarray,
				GroupChargingItemDTO.class);

		double ttamt = 0;
		double gramt = 0;
		double gryamt = 0;
		String olditems="";
		for (GroupChargingItemDTO oldgci : oldlistitem) {
			olditems=olditems+""+oldgci.getItem_code()+",";
			ttamt = ttamt + oldgci.getTeam_pay();
			if ("Y".equals(oldgci.getPay_status())) {
				gryamt = gryamt + oldgci.getPersonal_pay();
			} else {
				gramt = gramt + oldgci.getPersonal_pay();
			}
		}

		boolean fxitemflag = true; // 分项金额判断
		String newitems="";
		for (GroupChargingItemDTO gci : listitem) {
			newitems=newitems+""+gci.getItem_code()+",";
			BigDecimal bd = new BigDecimal(0);
			bd = new BigDecimal(gci.getTeam_pay());
			double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			bd = new BigDecimal(gci.getAmount());
			double pamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			bd = new BigDecimal( gci.getItem_amount()*gci.getDiscount()/ 10);
			double amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			if (java.lang.Math.abs(amount-(tamt + pamt))>10) {
				fxitemflag = false;
				break;
			}
		}

		boolean zjeitemflag = true;
		double zje = 0;
		for (GroupChargingItemDTO gci : listitem) {
			BigDecimal bd = new BigDecimal(gci.getTeam_pay());
			zje = zje + bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		if (zje != ttamt) {
			zjeitemflag = false;
		}

		boolean grramtflags = false;
		double grramt = 0;
		String gryitemcode = "";
		if (gryamt > 0) {
			BigDecimal bd = new BigDecimal(gryamt);
			gryamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			for (GroupChargingItemDTO gci : listitem) {
				bd = new BigDecimal(gci.getPersonal_pay());
				grramt = grramt + bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				gryitemcode = gryitemcode + "," + gci.getItem_code();
				if (grramt == gryamt) {
					grramtflags = true;// 总金额不一致
					break;
				}
			}
		} else {
			grramtflags = true;
		}

		if (StringUtils.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能加项！";
		} else if (!zjeitemflag) {
			this.message = "error-所选项目团体金额不一致！";
		} else if (!grramtflags) {
			this.message = "error-所选项目个人已交付金额不能凑整！";
		} else {
			// this.message = this.batchService.saveGroup(bc, listitem,
			// listset);
			this.message = this.djtCustomerInfoService.djtChargeExamInfoGChangItem(bc, oldlistitem, listitem,
					gryitemcode, "T", user.getUserid(), user.getName(), user.getCenter_num());

			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("421");//子功能id 必须填写
			sl.setExplain(bc.getExaminfo_id()+" "+olditems+" "+newitems);//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: getdjtregisterdataShow @Description:单独人员设置预约日期
	 *         427 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String getdjtregisterdataShow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		this.model
				.setTime1(this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num()).getConfig_value().trim());
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("427");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: getdjtTgroupInfoDo @Description: 获取人员的分组信息
	 *         428 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String getdjtTgroupInfoDo() throws WebException, SQLException {
		List<GroupInfoDTO> list = new ArrayList<GroupInfoDTO>();
		list = this.batchService.getGroupListForBatchId(this.model.getBatch_id());
		long olds = this.model.getAge();
		String sex = this.model.getSex();
		String is_marriage = this.model.getIs_marriage();
		String others = this.model.getOthers();
		String addposition = this.model.getPosition();// 职务
		List<GroupInfoDTO> list_new = new ArrayList<GroupInfoDTO>();
		if (sex != null) {
			for (GroupInfoDTO gi : list) {
				if ((StringUtil.isEmpty(gi.getSex()))||(sex.equals(gi.getSex()))) {
					list_new.add(gi);
				}
			}
			if (list_new.size() > 0) {
				list = list_new;
			}
		}
		list_new = new ArrayList<GroupInfoDTO>();
		if (is_marriage != null) {
			for (GroupInfoDTO gi : list) {
				if (is_marriage.equals(gi.getIs_Marriage())) {
					list_new.add(gi);
				}
			}
			if (list_new.size() > 0) {
				list = list_new;
			}
		}

		list_new = new ArrayList<GroupInfoDTO>();
		if (olds > 0) {
			for (GroupInfoDTO gi : list) {
				if ((olds >= gi.getMin_age()) && (olds <= gi.getMax_age())) {
					list_new.add(gi);
				}
			}
			if (list_new.size() > 0) {
				list = list_new;
			}
		}
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	     * @Title: djtreApplydo   
	     * @Description: 登记台补发申请  429  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtreApplydo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("429");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		if (!StringUtils.isEmpty(this.model.getExam_num())) {
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			String accountflagAndOverMsg = "ok";
			if("T".equals(eu.getExam_type())){
				accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(eu.getBatch_id());
			}
			if (!"ok".equals(accountflagAndOverMsg)) {
				this.message = accountflagAndOverMsg;
			} else if(eu.getFreeze()==1){
				this.message = "error-体检信息已冻结,不需要发送申请!";
			}else{
			String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
			String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
			
			try {
				
				if("N".equals(lis) && "N".equals(pacs)){
					this.message = "error-LIS与PACS接口都未启用,不需要发送申请!";
				}else{
					String lismsg = "";
					if("Y".equals(lis)){
						lismsg = this.lisPacsApplicationService.lisSend(eu.getExam_num(), null, user, false,lis_bang,"N");
						sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());		
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("429");//子功能id 必须填写
						sl.setExplain("补发lis申请 "+eu.getExam_num());//操作说明
						syslogService.saveSysLog(sl);
					}
					String pacsmsg = "";
					if("Y".equals(pacs)){
						pacsmsg = this.lisPacsApplicationService.pacsSend(eu.getExam_num(), null, user, false,"N");
						sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());		
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("429");//子功能id 必须填写
						sl.setExplain("补发pacs申请 "+eu.getExam_num());//操作说明
						syslogService.saveSysLog(sl);
					}
					if(lismsg.indexOf("ok") == 0 || pacsmsg.indexOf("ok") == 0){
						this.message = "ok-补发申请成功!";
					}else{
						this.message = "error-补发申请失败!";
					}
				}
				
			} catch (ServiceException e) {
				this.message = "error-"+e.getMessage();
			}
		}
		} else {
			this.message = "error-体检编号无效";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djtrehisdo   
	     * @Description: 登记台补发his申请    431
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtrehisdo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (!StringUtils.isEmpty(this.model.getExam_num())) {
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if (eu.getFreeze() == 1) {
				this.message = "error-体检信息已冻结,不需要发送HIS申请";
			} else {
				String his = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();

				try {
					if ("Y".equals(his)) {
						this.message = this.collectFeesService.paymentApplication(eu.getExam_num(), user);
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("431");// 子功能id 必须填写
						sl.setExplain("补发收费申请 " + eu.getExam_num());// 操作说明
						syslogService.saveSysLog(sl);
					} else {
						this.message = "HIS接口未启用,不需要发送HIS申请";
					}
				} catch (ServiceException e) {
					this.message = "error-" + e.getMessage();
					e.printStackTrace();
				}
			}
		} else {
			this.message = "error-体检编号无效";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djtjzkinfodo   
	     * @Description: 登记台重新发送申请 432   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtjzkinfodo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getMc_no().length() > 0) {
			
			List<ExamInfo> list= new ArrayList<ExamInfo>();
			if(this.model.getMc_no().length()==18){
				//身份证获取体检信息
				list = this.djtCustomerInfoService.getExamInfoForIdNum(this.model.getMc_no().trim());
			}else{
				list = this.djtCustomerInfoService.getExamInfoForMcNo(this.model.getMc_no().trim());
			}
			String exam_num="";
			for(ExamInfo ei:list){
				int summcount=this.djtCustomerInfoService.getChargingSummarySinglecount(ei.getExam_num(), user.getCenter_num());
				if(summcount>0){
					exam_num=ei.getExam_num();
					break;
				}
			}
			
			if(exam_num.length()>0){
				this.message = "errorexam-"+exam_num;
				this.outJsonStrResult(this.message);
			}else{
			
			WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
			wcd = webserviceConfigurationService.getWebServiceConfig("JZKCUST_APPLICATION", user.getCenter_num());									
			String web_url = wcd.getConfig_url().trim();
			String web_meth=wcd.getConfig_method().trim();
			YbCustomMessage fm = new YbCustomMessage();
			fm.setMC_NO(this.model.getMc_no());
			fm.setCARD_CLASS("0");// 医保卡和就诊卡类别，医保卡为1，就诊卡为0
			fm.setVISIT_DATE(DateTimeUtil.getDate2());
			fm.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
			YBCustomSendMessage fsm = new YBCustomSendMessage(fm);
			YbCustomResultBody fr = fsm.customSend(web_url,web_meth, true);
			if ("AA".equals(fr.getResultHeader().getTypeCode())) {
				if (fr.getControlActProcess().getLIST().getITEMS().getITEM().size() <= 0) {
					this.message = "error-无挂号信息！";
					this.outJsonStrResult(this.message);
				} else {
					boolean ybcflag = false;
					YbCustom ybcustom = new YbCustom();
					for (YbCustom ybc : fr.getControlActProcess().getLIST().getITEMS().getITEM()) {
						if ("体检".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}else if ("市医保".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}else if ("省医保".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}else if ("铁路医保".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}
					}
					if (!ybcflag) {
						this.message = "error-挂号信息无效,请确定是否体检挂号！";
						this.outJsonStrResult(this.message);
					} else {
						if ((ybcustom == null) || (ybcustom.getVISIT_DATE() == null)) {
							this.message = "error-挂号信息无效！";
							this.outJsonStrResult(this.message);
						} else {
							if (ybcustom.getVISIT_DATE().trim().length() < 10) {
								this.message = "error-挂号信息（" + ybcustom.getVISIT_DATE().trim() + "）日期无效！";
								this.outJsonStrResult(this.message);
							} else {
								String vdate = ybcustom.getVISIT_DATE().substring(0, 10);
								if (!DateTimeUtil.getDate2().equals(vdate)) {
									this.message = "error-挂号信息（" + vdate + "）日期无效！";
									this.outJsonStrResult(this.message);
								} else {
									ybcustom.setVISIT_DATE(vdate);
									this.outJsonResult(ybcustom);
								}
							}
						}
					}
				}
			} else {
				this.message = "error-" + fr.getResultHeader().getText();
				this.outJsonStrResult(this.message);
			}
		}
		} else {
			this.message = "error-体检编号无效";
			this.outJsonStrResult(this.message);
		}

		return NONE;
	}
	
	public String djtexamInfojson() throws WebException {
		System.out.println(this.model.getExam_num());
		JSONArray array = JSONArray.fromObject(this.model.getExam_num());
		  JSONObject o=(JSONObject) array.get(0);
		System.out.println(o.get("bmpFileData"));
		return NONE;
	} 
	
	/**
	 * 
	     * @Title: djtexamInfoforIdNum   
	     * @Description: 登记台判断身份证号 455  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtexamInfoforIdNum() throws WebException {
		
		String res="1";
		if (StringUtil.isEmpty(this.model.getId_num())){
			res="9";//身份证为空
		}else if(this.model.getCustomer_id()<=0){
			res="8";//身份id=0
		}else{
			List<ExamInfoDTO> list =  new ArrayList<ExamInfoDTO>();
			list = this.customerInfoService.getExamInfoForIdNumList(this.model.getId_num().trim());
			res="1";
			boolean flagss=true;
			for(ExamInfoDTO ei:list){
				if(ei.getId()!=this.model.getCustomer_id()){
					flagss=false;
					break;
				}
			}
			if(flagss){
				res="1";//可以正常覆盖
			}else{
				res="2";//提示是否需要覆盖
			}			
		}
		this.outJsonStrResult(res);
		return NONE;
	} 
	/**
	 * 
	     * @Title: getIndividualReportReceive   
	     * @Description: TODO(个人报告领取方式tab--583)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getIndividualReportReceive() throws WebException {
		return SUCCESS;
	}
	/**
	 * @throws SQLException 
	 * 
	     * @Title: getReportReceive   
	     * @Description: TODO(获取报告领取方式584)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  getReportReceive()throws WebException {
		ExamInfoDTO re = reportReceiveService.getReportReceive(e_num);
			this.outJsonResult(re);
		return NONE;
	}
	/**
	 * 
	     * @Title: saveReportReceive   
	     * @Description: TODO(保存报告领取方式585)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveReportReceive()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
			this.reportReceiveService.saveReportReceive(model, user);
			this.message="保存成功";
			this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getdjtregisterghShow 800
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getdjtregisterghShow() throws WebException{		
		UserDTO user = (UserDTO) session.get("username");
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("800");//子功能id 必须填写
		sl.setExplain("进入就诊卡挂号操作界面");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: djtjzkinfodoreg   
	     * @Description: 执行就诊卡或者身份证（医保挂号） 补充挂号 801 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtjzkinfodoreg() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getMc_no().length() > 0) {			
			List<ExamInfo> list= new ArrayList<ExamInfo>();
			if(this.model.getMc_no().length()==18){
				//身份证获取体检信息
				list = this.djtCustomerInfoService.getExamInfoForIdNum(this.model.getMc_no().trim());
			}else{
				list = this.djtCustomerInfoService.getExamInfoForMcNo(this.model.getMc_no().trim());
			}
			String exam_num="";
			for(ExamInfo ei:list){
				int summcount=this.djtCustomerInfoService.getChargingSummarySinglecount(ei.getExam_num(), user.getCenter_num());
				if(summcount>0){
					exam_num=ei.getExam_num();
					break;
				}
			}
			
			if(exam_num.length()>0){
				this.message = "errorexam-"+exam_num;
				this.outJsonStrResult(this.message);
			}else{
			
			WebserviceConfigurationDTO wcd = new WebserviceConfigurationDTO();
			wcd = webserviceConfigurationService.getWebServiceConfig("JZKCUST_APPLICATION", user.getCenter_num());									
			String web_url = wcd.getConfig_url().trim();
			String web_meth=wcd.getConfig_method().trim();
			YbCustomMessage fm = new YbCustomMessage();
			fm.setMC_NO(this.model.getMc_no());
			fm.setCARD_CLASS("0");// 医保卡和就诊卡类别，医保卡为1，就诊卡为0
			fm.setVISIT_DATE(DateTimeUtil.getDate2());
			fm.setVISIT_DEPT(companyService.getDatadis("SQKS","0").get(0).getRemark());
			YBCustomSendMessage fsm = new YBCustomSendMessage(fm);
			YbCustomResultBody fr = fsm.customSend(web_url,web_meth, true);
			if ("AA".equals(fr.getResultHeader().getTypeCode())) {
				if (fr.getControlActProcess().getLIST().getITEMS().getITEM().size() <= 0) {
					this.message = "error-无挂号信息！";
					this.outJsonStrResult(this.message);
				} else {
					boolean ybcflag = false;
					YbCustom ybcustom = new YbCustom();
					for (YbCustom ybc : fr.getControlActProcess().getLIST().getITEMS().getITEM()) {
						if ("体检".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}else if ("市医保".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}else if ("省医保".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}else if ("铁路医保".equals(ybc.getCHARGE_TYPE())) {
							ybcustom = ybc;
							ybcflag = true;
							break;
						}
					}
					if (!ybcflag) {
						this.message = "error-挂号信息无效,请确定是否体检挂号！";
						this.outJsonStrResult(this.message);
					} else {
						if ((ybcustom == null) || (ybcustom.getVISIT_DATE() == null)) {
							this.message = "error-挂号信息无效！";
							this.outJsonStrResult(this.message);
						} else {
							if (ybcustom.getVISIT_DATE().trim().length() < 10) {
								this.message = "error-挂号信息（" + ybcustom.getVISIT_DATE().trim() + "）日期无效！";
								this.outJsonStrResult(this.message);
							} else {
								String vdate = ybcustom.getVISIT_DATE().substring(0, 10);
								if (!DateTimeUtil.getDate2().equals(vdate)) {
									this.message = "error-挂号信息（" + vdate + "）日期无效！";
									this.outJsonStrResult(this.message);
								} else {									
									ybcustom.setVISIT_DATE(vdate);
									ExamInfo ei= new ExamInfo();
									ei=this.djtCustomerInfoService.loadExamInfo(this.model.getExam_num());
									ei.setVisit_date(ybcustom.getVISIT_DATE());
									ei.setVisit_no(ybcustom.getVISIT_NO());
									ei.setPatient_id(ybcustom.getPATIENT_ID());
									ei.setClinic_no(ybcustom.getCLINIC_NO());
									ei.setMc_no(ybcustom.getCARD_NO());
									this.customerInfoService.updateExamInfo(ei);
									this.outJsonResult(ybcustom);
								}
							}
						}
					}
				}
			} else {
				this.message = "error-" + fr.getResultHeader().getText();
				this.outJsonStrResult(this.message);
			}
		}
		} else {
			this.message = "error-体检编号无效";
			this.outJsonStrResult(this.message);
		}

		return NONE;
	}
	
	/**
	 * 
	     * @Title: regetDjtRegisterGEdit   
	     * @Description: 登记台个人复检 813 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String regetDjtRegisterGEdit() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String exam_num = (String) request.getSession().getAttribute("exam_num");
		model.setExam_num(exam_num);
		this.model.setIs_after_pay(this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", user.getCenter_num()).getConfig_value().trim());
		this.model.setRegister_date(DateTimeUtil.getDate2());// 预约日期
		this.model.setJoin_date(DateTimeUtil.getDate2());// 报道日期，体检日期
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setDjd_code_url(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("813");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: regetDjtRegisterTEdit @Description:
	 *         团体体检维护814  @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String regetDjtRegisterTEdit() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String exam_num = (String) request.getSession().getAttribute("exam_num");
		model.setExam_num(exam_num);
		this.model.setRegister_date(DateTimeUtil.getDate2());
		this.model.setJoin_date(DateTimeUtil.getDate2());
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setIs_after_pay(this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", user.getCenter_num()).getConfig_value().trim());
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setDjd_code_url(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		// 获取身份证厂家代码
		String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
		this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
		this.sfz_div_code = sfz_div_code;
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());		
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("814");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String examinfosignshow() throws WebException{ 
		if (!StringUtils.isEmpty(this.model.getExam_num())) {
			ExamInfoDTO wr = this.customerInfoService.getCustExamInfoForexamId(this.model.getExam_num());
			try {
				this.model.setCustname(wr.getUser_name());
				this.model.setEmail(wr.getEmail());
				model.setExam_num(wr.getExam_num());
				model.setTel(wr.getPhone());
				ExamInfoSign eis = this.djtCustomerInfoService.getExamInfoSign(wr.getExam_num());
				if(eis!=null){
					model.setItementities(eis.getExamsign());
				}else{
					model.setItementities("");
				}
				
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("825");// 子功能id 必须填写
				sl.setExplain(wr.getUser_name()+" "+wr.getExam_num());// 操作说明
				syslogService.saveSysLog(sl);
				return SUCCESS;
			} catch (Exception e) {
				this.message="体检用户查询失败！";
				return ERROR;
			}			
		}else{
			this.message="体检用户查询失败！";
			return ERROR;
		}		
	}
	
	/**
	 * 
	     * @Title: managersignupdate   
	     * @Description: 用户签名保存 823  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String examsignupdate() throws WebException, SQLException {
		String picpath = saveusersignPic(this.model.getExam_num(), this.model.getBatch_name(),this.model.getAddress());
		this.outJsonStrResult(picpath);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: saveExamInfoPic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param picstring
	     * @param: @param flags
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	private String saveusersignPic(String examnum,String signstr, String picstring) {
		String filepath = "";
		//System.out.println("0000:="+picstring);
		if ((picstring != null) && (picstring.length() > 100)) {
			String bmpfiledata = picstring;			
			try {
						byte[] bmpfiledata64 = Base64.base64Decode(bmpfiledata);
						String picpath = this.commService.getDatadis("TPLJ").getName();
						String path = picpath + "\\";
						filepath = "examsign-picture/" + examnum;
						File f = new File(path);
						if (!f.exists() && !f.isDirectory())
							f.mkdir();
						f = new File(path+"\\examsign-picture");

						if (!f.exists() && !f.isDirectory())
							f.mkdir();
						path = path + filepath;
						
						f = new File(path+".gif");
						if (f.exists() && f.isFile())
							f.delete();
						
						FileOutputStream fos = new FileOutputStream(path+".gif");
						// 用FileOutputStream 的write方法写入字节数组
						fos.write(bmpfiledata64);
						System.out.println("写入成功");
						fos.close();
						
						
						 JPGOptions options = new JPGOptions();  
				         options.setQuality(75);  
				         ImageProducer image = Jimi.getImageProducer(path+".gif"); 				         
				         JimiWriter writer = Jimi.createJimiWriter(path+".jpg");  
				         writer.setSource(image);  
				         writer.setOptions(options);  
				         writer.putImage(path+".jpg"); 
						
				         f = new File(path+".gif");
						 if (f.exists() && f.isFile())
						 f.delete();
						ExamInfoSign eis =this.djtCustomerInfoService.getExamInfoSign(examnum);
						UserDTO user = (UserDTO) session.get("username");
						if(eis==null){
							ExamInfoSign eisnew = new ExamInfoSign();
							eisnew.setCreater(user.getUserid());
							eisnew.setCreate_time(DateTimeUtil.parse());
							eisnew.setUpdater(user.getUserid());
							eisnew.setUpdate_time(DateTimeUtil.parse());
							eisnew.setExam_num(examnum);
							eisnew.setExamsign(signstr);
							eisnew.setExamsignpicpath(filepath+".jpg");
							this.djtCustomerInfoService.saveExamInfoSign(eisnew);
							
							SysLog sl = new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid() + "");
							sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
							sl.setXtgnid("");// 可不填写
							sl.setXtgnid2("826");// 子功能id 必须填写
							sl.setExplain(examnum);// 操作说明
							syslogService.saveSysLog(sl);
						}else{
							eis.setUpdater(user.getUserid());
							eis.setUpdate_time(DateTimeUtil.parse());
							eis.setExamsign(signstr);
							eis.setExamsignpicpath(filepath+".jpg");
							this.djtCustomerInfoService.updateExamInfoSign(eis);
							SysLog sl = new SysLog();
							sl.setCenter_num(user.getCenter_num());
							sl.setUserid(user.getUserid() + "");
							sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
							sl.setXtgnid("");// 可不填写
							sl.setXtgnid2("826");// 子功能id 必须填写
							sl.setExplain(examnum);// 操作说明
							syslogService.saveSysLog(sl);
						}
						return "ok-"+examnum+"-签名信息保存成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "error-保存文件失败";
			}
		}else{
			return "error-签名图片不存在";
		}
	}
	
	/**
	 * 
	     * @Title: getuserPhoto   
	     * @Description: 获取用户签名图片 824   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getexamPhotosgin() throws WebException {
		if ((this.model.getExam_num() != null) && (this.model.getExam_num().trim().length() > 0)) {
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\examsign-picture\\"+this.model.getExam_num().trim()+".jpg";
				File f = new File(path);
				if (f.exists()) {
					FileInputStream fileInput = new FileInputStream(path);
					int i = fileInput.available();
					byte[] content = new byte[i];
					fileInput.read(content);
					HttpServletResponse response = ServletActionContext.getResponse();
					response.reset();
					response.setContentType("image/jpeg");
					ServletOutputStream sos = response.getOutputStream();
					sos.write(content);
					sos.flush();
					sos.close();
				}else{
					String dbXmlPath = ServletActionContext.getServletContext().getRealPath("/sign/images/none.gif");
					f = new File(dbXmlPath);
					if (f.exists()) {
						FileInputStream fileInput = new FileInputStream(dbXmlPath);
						int i = fileInput.available();
						byte[] content = new byte[i];
						fileInput.read(content);
						HttpServletResponse response = ServletActionContext.getResponse();
						response.reset();
						response.setContentType("image/jpeg");
						ServletOutputStream sos = response.getOutputStream();
						sos.write(content);
						sos.flush();
						sos.close();
					}
				}
			} catch (Exception ex) {

			}
		}
		return NONE;
	}

	
	/**
	 * 
	     * @Title: djtGcustomertbbah   
	     * @Description: 同步档案  836
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String djtGcustomertbbah() throws WebException {
		if(StringUtils.isEmpty(this.model.getExam_num())){
			this.message="error-无效的体检编号";
		}else{			
			UserDTO user = (UserDTO) session.get("username");
			try {
				if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_DAH_INTERFACE", user.getCenter_num()).getConfig_value())) {
					ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamNum(this.model.getExam_num());
					if (("Y".equals(ei.getIs_Active())) && (!StringUtil.isEmpty(ei.getId_num()))
							&& (("0").equals(ei.getFlag())) && (ei.getCustomer_id() > 0)) {
						String dah = ei.getArch_num();
						String cusflag = "0";
						DAHSendMessage dahsend= new DAHSendMessage();
						DAHCustomerBean dahcu = new DAHCustomerBean();
						dahcu.setCenter_num(user.getCenter_num());
						dahcu.setBrid(ei.getBirthday());
						dahcu.setId_num(ei.getId_num());
						dahcu.setName(ei.getUser_name());
						dahcu.setSex(ei.getSex());
						DAHResBody tr=new DAHResBody();
						tr=dahsend.dahSend(dahcu,dah);
						if("ok".equals(tr.getRescode())){
							dah=tr.getIdnumber();
								cusflag="1";
							}else{
								dah=this.batchService.GetCreateID("vipno", user.getCenter_num());
								cusflag="0";
							}
						CustomerInfo cti = new CustomerInfo();
						cti = this.customerInfoService.getCustomerInfoForId(ei.getCustomer_id());
						cti.setArch_num(cusflag);
						cti.setFlag(cusflag);
						this.customerInfoService.updateCustomerInfo(cti);
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("830");// 子功能id 必须填写
						sl.setExplain(
								"登记台同步档案号 " + ei.getExam_num() + " " + ei.getArch_num() + " " + cti.getArch_num());// 操作说明
						syslogService.saveSysLog(sl);
						this.message = "ok-" + ei.getId();

					} else {
						this.message = "error-无需同步！";
					}
				} else {
					this.message = "error-无需调用接口";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				this.message="error-调用接口错误";
			}
		}	
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 登记台放射史导入页面390
	     * @Title: getZybFsDjtRegisterExtEdit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybFsDjtRegisterExtEdit() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String exam_num = (String) request.getSession().getAttribute("exam_num");
		if ((exam_num != null) && (exam_num.trim().length() > 0)) {
			this.model.setExam_num(exam_num);
			ExamInfoUserDTO eu= new ExamInfoUserDTO();
			eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			this.model.setExam_num(eu.getExam_num());
		}		
		return SUCCESS;
	}
	/**
	 * 既往史391
	     * @Title: getZybJWSDjtRegisterExtEdit   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybJWSDjtRegisterExtEdit() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String exam_num = (String) request.getSession().getAttribute("exam_num");
		this.model.setExam_num(exam_num);
		return SUCCESS;
	}
	/**
	 * 
	 * @Title: djtdiscountadd @Description: 职业病团体登记台折扣率设置
	 *         2106 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String zybdjtTdiscountadd() throws WebException, Exception {
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		this.model.setCustomer_id(this.model.getCustomer_id());
		UserDTO user = (UserDTO) session.get("username");
		this.model.setLogin_name(user.getName());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("2110");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	//复检登记添加总检室添加的项目
	public String addZYBRecheckChargeItem(){
		this.defaultapp = (SystemType)session.get("defaultapp");
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.customerInfoService.addZYBRecheckChargeItem(this.model.getOldexam_num(),this.model.getExam_num(),user,this.defaultapp.getComid());
		this.outJsonStrResult(this.message);
		return NONE;
	}
}