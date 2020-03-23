package com.hjw.ipad.action;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hsqldb.lib.StringUtil;

import com.hjw.DTO.MzghBaseInfoDTO;
import com.hjw.config.Logincheck;
import com.hjw.crm.service.CrmSignBillPlanZhuangdanService;
import com.hjw.ipad.service.BatchIpadService;
import com.hjw.ireport.DTO.DjdbeanList;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.webService.client.hghis.JiuzhenkaInfo;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ChargingItemConsumablesItemDTO;
import com.hjw.wst.DTO.CustmTimeCountDTO;
import com.hjw.wst.DTO.CustomerInfoDTO;
import com.hjw.wst.DTO.CustomerTypeDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExamInfoUserDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.ExaminfoSetDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.ImpCustomerInfoDTO;
import com.hjw.wst.DTO.PacsCountDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.DataDictionary;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.model.ImpCustomerInfoModel;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CollectFeesService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DjtCustomerInfoService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.TeamAccountService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.json.JSONArray;

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
public class CustomerInfoIpadAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private BatchIpadService batchIpadService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private TeamAccountService teamAccountService;
	private String sort = "";// 排序--字段
	private String order = "";// 排序--排序方式

	private CompanyService companyService;
	private ImpCustomerInfoModel model = new ImpCustomerInfoModel();
	private CustomerInfoService customerInfoService;
	private DjtCustomerInfoService djtCustomerInfoService;
    private LisPacsApplicationService lisPacsApplicationService;
    private CollectFeesService collectFeesService;
	private String sfz_div_ocx;// 身份证读卡器ocx
	private String sfz_div_code;// 身份证厂家代码
	private ChargingItemService chargingItemService;

	private CrmSignBillPlanZhuangdanService crmSignBillPlanZhuangdanService;
	private WebserviceConfigurationService webserviceConfigurationService;

	
	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}
	
	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}

	public CrmSignBillPlanZhuangdanService getCrmSignBillPlanZhuangdanService() {
		return crmSignBillPlanZhuangdanService;
	}

	public void setCrmSignBillPlanZhuangdanService(CrmSignBillPlanZhuangdanService crmSignBillPlanZhuangdanService) {
		this.crmSignBillPlanZhuangdanService = crmSignBillPlanZhuangdanService;
	}

	public void setTeamAccountService(TeamAccountService teamAccountService) {
		this.teamAccountService = teamAccountService;
	}

	private SyslogService syslogService;

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}

	public void setCollectFeesService(CollectFeesService collectFeesService) {
		this.collectFeesService = collectFeesService;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setOrder(String order) {
		this.order = order;
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

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(ImpCustomerInfoModel model) {
		this.model = model;
	}

	public void setbatchIpadService(BatchIpadService batchIpadService) {
		this.batchIpadService = batchIpadService;
	}

	/**
	 * s
	 * 
	 * @Title: impusershow @Description: 242 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String impusershow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("242");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: impusershowlist @Description: 显示导入的临时表数据
	 *         243 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String impusershowlist() throws WebException, SQLException {
		PageReturnDTO contractlist = new PageReturnDTO();
		UserDTO user = (UserDTO) session.get("username");
		if (rows <= 15) {
			rows = 250;
		}
		contractlist = this.customerInfoService.getImpCustomerInfoList(this.model.getBatch_id(),
				this.model.getCompany_id(), user.getUserid(), InfoType.YBTJ, this.rows, this.getPage());
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: saveCustomerTmplist @Description: 保存导入的临时表数据
	 *         246 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */

	public String saveCustomerTmplist() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getId() <= 0) {
			this.message = "error-记录编号无效";
		} else {
			ImpCustomerInfo ci = new ImpCustomerInfo();
			ci = this.customerInfoService.loadImpCustomerInfo(this.model.getId());
			ci.set_level(this.model.get_level());
			ci.setAge(this.model.getAge());
			ci.setArch_num(this.model.getArch_num());
			ci.setExam_type(this.model.getExam_type());
			ci.setBirthday(this.model.getBirthday());
			ci.setCreate_time(Timeutils.getNowDate());
			ci.setCreater(user.getUserid());
			ci.setCustomer_type(this.model.getCustomer_type());
			ci.setFlags(0);
			ci.setId_num(this.model.getId_num());
			ci.setIs_marriage(this.model.getIs_marriage());
			ci.setCustname(model.getCustname());
			ci.setNotices("");
			ci.setOthers(this.model.getOthers());
			ci.setPosition(this.model.getPosition());
			ci.setRemark(this.model.getRemark());
			ci.setSex(this.model.getSex());
			ci.setTel(this.model.getTel());
			ci.setEmployeeID(this.model.getEmployeeID());
			ci.setVisit_no(this.model.getVisit_no());
			ci.setIntroducer(this.model.getIntroducer());
			ci.setIdtypename(this.model.getIdtypename());
			this.customerInfoService.updateImpCustomerInfo(ci);
			this.message = "ok-修改导入数据成功";
			// UserDTO user = (UserDTO) session.get("username");
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("246");// 子功能id 必须填写
			sl.setExplain("数据导入 " + ci.getCustname() + " " + ci.getBatch_id());// 操作说明
			syslogService.saveSysLog(sl);
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: delCustomerTmplist @Description:
	 *         TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String delCustomerTmplist() throws WebException, SQLException {
		if ((this.model.getIds() == null) || (this.model.getIds().trim()).length() <= 1) {
			this.message = "error-记录编号无效";
		} else {
			String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
			String[] sids = ids.split(",");
			for (int i = 0; i < sids.length; i++) {
				long id = Long.valueOf(sids[i]);
				ImpCustomerInfo ci = new ImpCustomerInfo();
				ci = this.customerInfoService.loadImpCustomerInfo(id);
				this.customerInfoService.delImpCustomerInfo(ci);
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("9");// 子功能id 必须填写
				sl.setExplain("删除临时数据 " + ci.getCustname());// 操作说明
				syslogService.saveSysLog(sl);
			}

			this.message = "ok-删除导入的数据成功";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: impuserusernamedw 248 @Description:
	 *         姓名定位人员列表 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String impuserusernamedw() throws WebException, SQLException, Exception {
		this.model.setCustname(URLDecoder.decode(this.model.getCustname(), "UTF-8"));
		return SUCCESS;
	}

	public String impuserusernamedwshow() throws WebException, SQLException, Exception {
		PageReturnDTO contractlist = new PageReturnDTO();
		if (this.model.getCustname() != null) {
			this.model.setCustname(URLDecoder.decode(this.model.getCustname(), "UTF-8"));
			contractlist = this.customerInfoService.getExamInfoForNameList(this.model.getCustname(), this.rows,
					this.getPage());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: impuserusernamedwdo @Description: 250
	 *         姓名定位人员保存 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @param: @throws
	 *         Exception @return: String @throws
	 */
	public String impuserusernamedwdo() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		ImpCustomerInfo ii = this.customerInfoService.loadImpCustomerInfo(this.model.getId());
		if (ii == null) {
			this.message = "error-导入表数据无效";
		} else {
			ExamInfoDTO ei = new ExamInfoDTO();
			ei = this.customerInfoService.getCustExamInfoForexamId(this.model.getExam_num());
			if (ei == null) {
				this.message = "error-选择体检库信息无效";
			} else {
				ii.set_level(ei.get_level());
				ii.setAge(ei.getAge());
				ii.setArch_num(ei.getArch_num());
				ii.setBirthday(ei.getBirthday());
				if ((ii.getBirthday() != null) && (ii.getBirthday().trim().length() > 10)) {
					ii.setBirthday(ii.getBirthday().substring(0, 10));
				}
				ii.setCreate_time(Timeutils.getNowDate());
				ii.setCreater(user.getUserid());
				ii.setCustname(ei.getUser_name());
				ii.setCompany_id(this.model.getCompany_id());
				ii.setBatch_id(this.model.getBatch_id());

				if (ei.getCustomer_type() != null) {
					DataDictionary dd = new DataDictionary();
					dd = this.companyService.getDatadisForId(Long.valueOf(ei.getCustomer_type()));
					if ((dd != null) && (dd.getId() > 0)) {
						ii.setExam_type(dd.getData_name());
					} else {
						ii.setExam_type("");
					}
				} else {
					ii.setExam_type("");
				}

				ii.setExam_type("健康体检");
				if (ei.getCustomer_type_id() > 0) {
					CustomerTypeDTO ct = new CustomerTypeDTO();
					ct = this.customerInfoService.getcustomerTypeId(ei.getCustomer_type_id());
					if ((ct != null) && (ct.getId() > 0)) {
						ii.setCustomer_type(ct.getType_name());
					} else {
						ii.setCustomer_type("");
					}
				} else {
					ii.setCustomer_type("");
				}

				ii.setFlags(0);
				ii.setId_num(ei.getId_num());
				ii.setIs_marriage(ei.getIs_marriage());
				ii.setNotices("相似性关联");
				ii.setOthers(ei.getOthers());
				ii.setPosition(ei.getPosition());
				ii.setRemark(ei.getRemarke());
				ii.setSex(ei.getSex());
				ii.setTel(ei.getPhone());
				ii.setEmployeeID(ei.getEmployeeID());
				this.customerInfoService.updateImpCustomerInfo(ii);
				this.message = "ok-相似性关联成功";
				// UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("250");// 子功能id 必须填写
				sl.setExplain("关联体检人员 " + ii.getCustname());// 操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: impuserAllToExaminfodo @Description: 执行批量导入
	 *         251 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @param: @throws Exception @return: String @throws
	 */

	public String impuserAllToExaminfodo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				this.customerInfoService.checkArch_num(this.model.getCompany_id(), this.model.getBatch_id(),
						user.getUserid());
				this.customerInfoService.checkIn_num(this.model.getCompany_id(), this.model.getBatch_id(),
						user.getUserid());
				this.customerInfoService.checkVisit_no(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid());
				if ((this.model.getCompanybatch_id() == null) || (this.model.getCompanybatch_id().trim().length() <= 0)
						|| (this.model.getCompanybatch_id().trim().split("-").length != 2)) {
					this.message = "error-无效单位或者体检任务，执行批量导入失败。";
				} else {
					long rescomid = Long.valueOf(this.model.getCompanybatch_id().trim().split("-")[0]);
					this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), rescomid,
							user.getUserid(), user.getCenter_id());

					this.defaultapp = (SystemType) session.get("defaultapp");
					customerInfoService.getExamInfoForFlagList(this.model.getCompany_id(), this.model.getBatch_id(),
							user.getUserid(), this.defaultapp.getComid(),user.getCenter_num());
					// UserDTO user = (UserDTO) session.get("username");
					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("4");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("251");// 子功能id 必须填写
					sl.setExplain("批量导入 " + this.model.getCompany_id() + " " + this.model.getBatch_id());// 操作说明
					syslogService.saveSysLog(sl);
					this.message = "ok-执行批量导入结束。";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: impuserToExaminfodo 252 @Description:
	 *         执行部分导入 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @param: @throws
	 *         Exception @return: String @throws
	 */
	public String impuserToExaminfodo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				this.customerInfoService.checkArch_num(this.model.getCompany_id(), this.model.getBatch_id(),
						user.getUserid());
				this.customerInfoService.checkIn_num(this.model.getCompany_id(), this.model.getBatch_id(),
						user.getUserid());
				this.customerInfoService.checkVisit_no(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid());
				if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
					this.model.setIds(this.model.getIds().trim().substring(1, this.model.getIds().trim().length()));
					if ((this.model.getCompanybatch_id() == null)
							|| (this.model.getCompanybatch_id().trim().length() <= 0)
							|| (this.model.getCompanybatch_id().trim().split("-").length != 2)) {
						this.message = "error-无效单位或者体检任务，执行批量导入失败。";
					} else {
						long rescomid = Long.valueOf(this.model.getCompanybatch_id().trim().split("-")[0]);
						this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), rescomid,
								user.getUserid(), user.getCenter_id());
						this.defaultapp = (SystemType) session.get("defaultapp");
						customerInfoService.getExamInfoForFlagList(this.model.getCompany_id(), this.model.getBatch_id(),
								user.getUserid(), this.model.getIds(), user.getCenter_num() + "",defaultapp.getComid());
						// UserDTO user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("4");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("252");// 子功能id 必须填写
						sl.setExplain("部分导入 " + this.model.getCompany_id() + " " + this.model.getBatch_id() + " "
								+ this.model.getIds());// 操作说明
						syslogService.saveSysLog(sl);
						this.message = "ok-执行部分导入结束。";
					}
				} else {
					this.message = "error-无效记录。";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: usergroupmanager @Description: 253
	 *         人员分组管理 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String usergroupmanager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setTime1(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_barcode_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("253");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		CenterConfigurationDTO ccf = new CenterConfigurationDTO();
		ccf = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num());// 获取登记台样式
		String djttype = ccf.getConfig_value().trim();// 登记台样式COMM表示普通、DBGJ东北国际
		if ("DBGJ".equals(djttype.toUpperCase())) {
			return "success_dbgj";
		}else if("JIUZHENKA".equals(djttype.toUpperCase())){
			return "success_jiuzhenka";
		} else {
			return SUCCESS;
		}
	}

	/**
	 * 
	 * @Title: djdprintdo @Description: 翰思打印导检单
	 * 266 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String djdprintdo() throws WebException, SQLException {
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: getCompanForDept @Description:
	 *         255 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @return: String @throws
	 */
	public String getCompanForDept() throws WebException, SQLException {
		List<CompanyDepartment> getbatchList = new ArrayList<CompanyDepartment>();
		getbatchList = this.companyService.getCompanyDepartmentByComid(this.model.getCompany_id());
		List<CompanyDepartment> getbatchList1 = new ArrayList<CompanyDepartment>();
		CompanyDepartment dcd = new CompanyDepartment();
		dcd.setId(0);
//		dcd.setDep_Name("可选择部门");
		dcd.setDep_Name("");
		getbatchList1.add(dcd);
		for (CompanyDepartment bd : getbatchList) {
			getbatchList1.add(bd);
		}
		this.outJsonResult(getbatchList1);
		return NONE;
	}

	/**
	 * 
	 * @Title: getBatchForGroup @Description:
	 *         256批次得到分组带空行 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String getBatchForGroup() throws WebException, SQLException {
		List<GroupInfoDTO> getbatchList = new ArrayList<GroupInfoDTO>();
		getbatchList = this.batchIpadService.getGroupListForBatchId(this.model.getBatch_id());
		List<GroupInfoDTO> getbatchList1 = new ArrayList<GroupInfoDTO>();
		GroupInfoDTO dcd = new GroupInfoDTO();
		dcd.setId(0);
		dcd.setGroup_name("可选分组");
		getbatchList1.add(dcd);
		for (GroupInfoDTO bd : getbatchList) {
			getbatchList1.add(bd);

		}
		this.outJsonResult(getbatchList1);
		return NONE;
	}

	/**
	 * 
	 * @Title: getExamInfoUserList @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @param: @throws SQLException @param: @throws
	 * Exception @return: String @throws
	 */
	public String getExamInfoUserList() throws WebException, SQLException, Exception {
		this.defaultapp = (SystemType) session.get("defaultapp");
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getExamInfoUserList(this.model.getCompany_id(),
				this.model.getBatch_id(), this.model.getDept_id(), this.model.getGroup_id(), this.model.getId_num(),
				this.model.getArch_num(), this.model.getCustname(), this.model.getTel(), this.model.getStatus(),
				this.model.getBirthday(),this.model.getStartDate(),this.model.getEndDate(),
				this.model.getEmployeeID(), this.model.getSex(), this.model.getIs_marriage(),
				this.model.getCustomer_type_id(), this.model.getDjdstatuss(), centernum, this.defaultapp.getComid(),
				this.rows, this.getPage(), this.sort, this.order,user);
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: doAllUserGroupuser @Description: 自动分组
	 * 258 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String doAllUserGroupuser() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				int flags = 0;
				flags = this.customerInfoService.doAllUserGroup(this.model.getBatch_id(), user.getUserid() + "",
						centernum);
				if (flags == 1) {
					String resstr=this.customerInfoService.countUserGroup(this.model.getBatch_id());
					String[] sresstr=resstr.split("-");
					this.message = "ok-自动分组执行完成,成功【"+sresstr[0]+"】条；失败【"+sresstr[1]+"】条。";
					this.message = "ok-自动分组执行完成";
					// UserDTO user = (UserDTO) session.get("username");
					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("4");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("258");// 子功能id 必须填写
					sl.setExplain("自动分组 " + this.model.getBatch_id());// 操作说明
					syslogService.saveSysLog(sl);
				} else {
					this.message = "error-自动分组执行返回失败";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: doUserGroupuser @Description: 259
	 * 强制分组 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String doUserGroupuser() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else if ((this.model.getIds() == null) || (this.model.getIds().trim()).length() <= 1
				|| (this.model.getIds().trim()).split(",").length <= 0) {
			this.message = "error-记录编号无效";
		} else if (this.model.getGroup_id() <= 0) {
			this.message = "error-分组编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				String ids[] = this.model.getIds().trim().split(",");
				for (int i = 0; i < ids.length; i++) {
					int statcont = this.djtCustomerInfoService.getIsGroupExamInfoSetForId(ids[i], centernum);
					if (statcont <= 0) {
						this.customerInfoService.dotUserGroup(this.model.getGroup_id(), user.getUserid() + "",
								centernum, ids[i]);
						// UserDTO user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("4");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("259");// 子功能id 必须填写
						sl.setExplain("强制分组 " + this.model.getGroup_id() + " " + ids[i]);// 操作说明
						syslogService.saveSysLog(sl);
					}
				}
				this.message = "ok-强制分组完成";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: deluserrow @Description: 删除人员及其分组、项目
	 * 260 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String deluserrow() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else if ((this.model.getIds() == null) || (this.model.getIds().trim()).length() <= 1) {
			this.message = "error-记录编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
				this.message = this.customerInfoService.delExamInfoForIds(ids, user.getUserid());
				// UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("260");// 子功能id 必须填写
				sl.setExplain("删除体检人员 " + ids);// 操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: editcustomer @Description: 修改提交人员信息
	 * 410 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String editcustomer() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.message = "单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
			if (this.model.getBatch_id() <= 0) {
				this.message = "体检任务无效";
				return "commerror";
			} else {
				Batch bt = new Batch();
				bt = this.batchIpadService.getBatchByID(this.model.getBatch_id());
				this.model.setBatch_name(bt.getBatch_name());
				this.model.setBatch_num(bt.getBatch_num());
				if (this.model.getId() > 0) {
					ExamInfoUserDTO eu = new ExamInfoUserDTO();
					eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
					if ((eu == null) || (eu.getId() <= 0)) {
						this.message = "error-记录编号无效,无法编辑！";
						return "commerror";
					} else {
						this.model.set_level(eu.get_level());
						this.model.setAddress(eu.getAddress());
						this.model.setAge(eu.getAge());
						this.model.setExam_id(eu.getId());
						this.model.setCustname(eu.getUser_name());
						this.model.setArch_num(eu.getArch_num());
						this.model.setExam_num(eu.getExam_num());
						this.model.setBirthday(eu.getBirthday());
						this.model.setCustomer_type(eu.getCustomer_type());
						this.model.setCustomer_id(eu.getCustomer_id());
						this.model.setId_num(eu.getId_num());
						this.model.setIs_marriage(eu.getIs_marriage());
						this.model.setOthers(eu.getOthers());
						this.model.setGroup_index(eu.getGroup_index());
						this.model.setEmployeeID(eu.getEmployeeID());
						this.model.setPosition(eu.getPosition());
						this.model.setRemark(eu.getRemarke());
						this.model.setSex(eu.getSex());
						this.model.setTel(eu.getPhone());
						this.model.setExam_type(eu.getExam_type());
						this.model.setNation(eu.getNation());
						this.model.setCustomer_type(eu.getCustomer_type());
						this.model.setCustomer_type_id(eu.getCustomer_type_id());
						this.model.setChargingType(eu.getChargingType());
						this.model.setEmail(eu.getEmail());
						this.model.setRemark(eu.getRemarke());
						this.model.setGroup_id(eu.getGroup_id());
						this.model.setGroup_name(eu.getGroup_name());
						this.model.setVisit_no(eu.getVisit_no());
						// 获取身份证厂家代码
						String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num())
								.getConfig_value().trim();
						this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
						this.sfz_div_code = sfz_div_code;
						CenterConfigurationDTO cen = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", user.getCenter_num());
						if (cen != null) {
							this.model.setIdnum_notnull(cen.getConfig_value());
						}
						CenterConfigurationDTO ccf = new CenterConfigurationDTO();
						ccf = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num());// 获取登记台样式
						if (ccf == null) {
							return SUCCESS;
						} else {
							String djttype = ccf.getConfig_value().trim();// 登记台样式COMM表示普通、DBGJ东北国际
							// ZJTY浙江二院//JIUZHENKA  就诊卡版本
							if("JIUZHENKA".equals(djttype.toUpperCase())){
								return "success_jiuzhenka";
							}else {
								return SUCCESS;
							}
						}
					}
				} else {
					CenterConfigurationDTO ccf = new CenterConfigurationDTO();
					ccf = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num());// 获取登记台样式
					if (ccf == null) {
						return SUCCESS;
					} else {
						String djttype = ccf.getConfig_value().trim();// 登记台样式COMM表示普通、DBGJ东北国际
																		// ZJTY浙江二院//JIUZHENKA  就诊卡版本
						if("JIUZHENKA".equals(djttype.toUpperCase())){
							return "success_jiuzhenka";
						}else {
							return SUCCESS;
						}
					}
					
				}
			}
		}

	}

	/**
	 * 
	 * @Title: editcustomerdo @Description: 修改提交人员信息
	 * 411 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String editcustomerdo() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			/*
			 * Batch taover = new Batch(); taover =
			 * this.batchIpadService.getBatchByID(this.model.getBatch_id()); if
			 * ("1".equals(taover.getOverflag())) { this.message =
			 * "error-批次任务已经封帐"; } else {
			 */
			ExamInfoUserDTO imp = new ExamInfoUserDTO();
			long statcont = 0;
			// boolean teamaccflag=false;
			ExamInfo eiold = new ExamInfo();
			eiold = this.djtCustomerInfoService.getExamInfoForId(this.model.getExam_id());
			long groupid = eiold.getGroup_id();
			if ((this.model.getGroup_id() != groupid) && (this.model.getGroup_id() > 0)) {
				statcont = this.djtCustomerInfoService.getIsGroupExamInfoSetForId(this.model.getExam_num(), user.getCenter_num());
			} else {
				statcont = 1;
			}
			/*
			 * TeamAccountExamListDTO tae = new TeamAccountExamListDTO(); tae =
			 * teamAccountService.getTeamAccountExamListforExamNum(eiold.
			 * getExam_num()); if((tae!=null)&&(tae.getId()>0)){
			 * teamaccflag=true; }
			 */

			/*
			 * if(teamaccflag){ this.message = "error-此体检信息已经结算，操作不能继续";
			 * this.outJsonStrResult(this.message); return NONE; }else{
			 */
			if (statcont <= 0) {
				imp.setGroup_id(this.model.getGroup_id());
			} else {
				imp.setGroup_id(eiold.getGroup_id());
			}
			// }

			imp.set_level(this.model.get_level());
			imp.setAge(this.model.getAge());
			imp.setId(this.model.getExam_id());
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
			imp.setGroup_index(this.model.getGroup_index());
			imp.setPosition(this.model.getPosition());
			imp.setRemarke(this.model.getRemark());
			imp.setSex(this.model.getSex());
			imp.setPhone(this.model.getTel());
			imp.setEmployeeID(this.model.getEmployeeID());
			imp.setExam_type("T");
			imp.setEmail(this.model.getEmail());
			imp.setNation(this.model.getNation());
			imp.setChargingType(this.model.getChargingType());
			imp.setAddress(this.model.getAddress());
			imp.setVisit_no(this.model.getVisit_no());
			this.message = this.customerInfoService.getExamInfoOne(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid(), user.getCenter_num(), imp);

			if ("ok".equals(this.message.split("-")[0])) {
				String exam_num = this.message.split("-")[1];
				if (statcont <= 0) {
					this.customerInfoService.dotUserGroup(this.model.getGroup_id(), user.getUserid() + "",
							user.getCenter_num(), exam_num);
				}
			}

			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("411");// 子功能id 必须填写
			sl.setExplain("修改体检人员 " + imp.getUser_name());// 操作说明
			syslogService.saveSysLog(sl);
			// }
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: addcustomer @Description: 261
	 * 执行新增操作 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String addcustomer() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getBatch_id() <= 0) {
			this.message = "体检任务无效";
			return "commerror";
		} else {

			Batch bt = new Batch();
			bt = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(bt.getBatch_name());
			this.model.setBatch_num(bt.getBatch_num());
			if (bt.getId() > 0) {
				if (this.model.getCompany_id() <= 0) {
					CompanyInfo cif = new CompanyInfo();
					cif = companyService.findComByID(bt.getCompany_id());
					this.model.setComname(cif.getCom_Name());
					this.model.setCompany_id(bt.getCompany_id());
					;
				} else {
					CompanyInfo cif = new CompanyInfo();
					cif = companyService.findComByID(this.model.getCompany_id());
					this.model.setComname(cif.getCom_Name());
				}

			}
			// 获取身份证厂家代码
			String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
			this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
			this.sfz_div_code = sfz_div_code;
			CenterConfigurationDTO cen = this.customerInfoService.getCenterconfigByKey("IS_XIYUAN_IDNUM", user.getCenter_num());
			String idnum_notnull = "";
			if (cen != null && !"".equals(cen)) {
				if (cen.getConfig_value() != null && !"".equals(cen.getConfig_value())) {
					idnum_notnull = cen.getConfig_value().trim();
					this.model.setIdnum_notnull(idnum_notnull);
				}
			}
			CenterConfigurationDTO ccf = new CenterConfigurationDTO();
			ccf = this.customerInfoService.getCenterconfigByKey("IS_DJT_TYPE", user.getCenter_num());// 获取登记台样式
			if (ccf == null) {
				return SUCCESS;
			} else {
				String djttype = ccf.getConfig_value().trim();// 登记台样式COMM表示普通、DBGJ东北国际,jiuzhenka  就诊卡
				if("JIUZHENKA".equals(djttype.toUpperCase())){
					return "success_jiuzhenka";
				}else {
					return SUCCESS;
				}
			}
		}

	}

	/**
	 * 
	 * @Title: addcustomerdo @Description: 单独体检人员信息录入
	 * 262 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String addcustomerdo() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch bt = new Batch();
			bt = this.batchIpadService.getBatchByID(this.model.getBatch_id());

			if ("1".equals(bt.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {

				if (bt.getId() > 0) {
					this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), bt.getCompany_id(),
							user.getUserid(), user.getCenter_id());
					ExamInfoUserDTO imp = new ExamInfoUserDTO();
					if (this.model.getExam_id() <= 0) {
						String ispay = this.customerInfoService.getCenterconfigByKey("IS_AFTER_PAY", user.getCenter_num()).getConfig_value()
								.trim();
						imp.setIs_after_pay(ispay);
					}

					if (this.model.getGroup_id() > 0) {
						imp.setGroup_id(this.model.getGroup_id());
					}

					imp.set_level(this.model.get_level());
					imp.setAge(this.model.getAge());
					imp.setId(this.model.getExam_id());
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
					imp.setGroup_index(this.model.getGroup_index());
					imp.setPosition(this.model.getPosition());
					imp.setRemarke(this.model.getRemark());
					imp.setSex(this.model.getSex());
					imp.setPhone(this.model.getTel());
					imp.setExam_type("T");
					imp.setEmployeeID(this.model.getEmployeeID());
					imp.setEmail(this.model.getEmail());
					imp.setNation(this.model.getNation());
					imp.setChargingType(this.model.getChargingType());
					imp.setAddress(this.model.getAddress());
					imp.setVisit_no(this.model.getVisit_no());
					// imp.setGroup_id(this.model.getGroup_id());
					this.message = this.customerInfoService.getExamInfoOne(this.model.getCompany_id(),
							this.model.getBatch_id(), user.getUserid(), user.getCenter_num(), imp);
					if ("ok".equals(this.message.split("-")[0])) {
						String exam_num = this.message.split("-")[1];
						if (this.model.getGroup_id() > 0) {
							this.customerInfoService.dotUserGroup(this.model.getGroup_id(), user.getUserid() + "",
									user.getCenter_num(), exam_num);
						}
					}
					// UserDTO user = (UserDTO) session.get("username");
					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("262");// 子功能id 必须填写
					sl.setExplain("新增体检人员 " + imp.getUser_name());// 操作说明
					syslogService.saveSysLog(sl);
				} else {
					this.message = "error-体检任务无效！";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: customeroneshow @Description:
	 * 单独体检人员信息录入 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String customeroneshow() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
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

		CompanyInfo cif = new CompanyInfo();
		cif = companyService.findComByID(this.model.getCompany_id());
		this.model.setComname(cif.getCom_Name());

		Batch bt = new Batch();
		bt = this.batchIpadService.getBatchByID(this.model.getBatch_id());
		this.model.setBatch_name(bt.getBatch_name());
		this.model.setBatch_num(bt.getBatch_num());

		ExamInfoUserDTO eu = new ExamInfoUserDTO();
		eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);

		this.model.setAddress(eu.getAddress());
		this.model.setAge(eu.getAge());
		this.model.setExam_id(eu.getId());
		this.model.setCustname(eu.getUser_name());
		this.model.setArch_num(eu.getArch_num());
		this.model.setExam_num(eu.getExam_num());
		this.model.setBirthday(eu.getBirthday());
		if ((eu.getBirthday() != null) && (eu.getBirthday().trim().length() > 10)) {
			eu.setBirthday(eu.getBirthday().trim().substring(0, 10));
			if ("1900-01-01".equals(eu.getBirthday())) {
				this.model.setBirthday("");
			} else {
				this.model.setBirthday(eu.getBirthday());
			}
		} else {
			this.model.setBirthday("");
		}
		this.model.setCustomer_id(eu.getCustomer_id());
		this.model.setId_num(eu.getId_num());
		this.model.setIs_marriage(eu.getIs_marriage());
		this.model.setOthers(eu.getOthers());
		this.model.setPosition(eu.getPosition());
		this.model.setRemark(eu.getRemarke());
		this.model.setSex(eu.getSex());
		this.model.setTel(eu.getPhone());
		this.model.setExam_type(eu.getExam_type());
		this.model.setNation(eu.getNation());
		this.model.setEmployeeID(eu.getEmployeeID());
		this.model.setEmail(eu.getEmail());
		List<ExaminfoSetDTO> eslist = this.customerInfoService.getExamSetForExamNum(this.model.getExam_num(),user.getCenter_num());
		this.model.setHansidjdflag("");
		if ((eslist != null) && (eslist.size() > 0)) {
			for (int i = 0; i < eslist.size(); i++) {
				ExaminfoSetDTO es = eslist.get(i);
				this.model.setHansidjdflag(this.model.getHansidjdflag() + "," + es.getSet_name());
			}
			this.model.setHansidjdflag(this.model.getHansidjdflag().substring(1));
		}

		try {// 获取部门
			if (eu.get_level() != null) {
				long leid = Long.valueOf(eu.get_level().trim());
				CompanyDepartment cd = this.companyService.getCompanyDepartmentById(leid);
				this.model.set_level(cd.getDep_Name());
			}
		} catch (Exception ex) {
			this.model.set_level("");
		}

		try {// 获取民族
			if (eu.getNation() != null) {
				DataDictionary dd = this.companyService.getDataDictionaryByid(eu.getNation());
				this.model.setNation(dd.getData_name());
			}
		} catch (Exception ex) {
			this.model.setNation("");
		}

		try {// 体检类型
			if (eu.getCustomer_type() != null) {
				DataDictionary dd = this.companyService.getDataDictionaryByid(eu.getCustomer_type());
				this.model.setCustomer_type(dd.getData_name());
			}
		} catch (Exception ex) {
			this.model.setCustomer_type("");
		}

		try {// 收费类型
			if (eu.getChargingType() != null) {
				DataDictionary dd = this.companyService.getDataDictionaryByid(eu.getChargingType());
				this.model.setChargingType(dd.getData_name());
			}
		} catch (Exception ex) {
			this.model.setChargingType("");
		}

		try {// 人员类别
			if (eu.getCustomer_type_id() > 0) {
				CustomerTypeDTO dd = this.customerInfoService.getcustomerTypeId(eu.getCustomer_type_id());
				this.model.setTime1(dd.getType_name());
			}
		} catch (Exception ex) {
			this.model.setTime1("");
		}

		return SUCCESS;
	}

	/**
	 * 
	 * @Title: doUserGrouplistshow @Description: 强制分组选择分组信息
	 * 263 @param: @return @return: String @throws
	 */
	public String doUserGrouplistshow() throws WebException, SQLException, Exception {
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		if ((this.model.getIds() != null) && (this.model.getIds().trim()).length() > 1) {
			String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
			this.model.setIds(ids);
		}
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: doSendApplyChargeItem @Description: 执行批量申请
	 * 264 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String doSendApplyChargeItem() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
					String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
					String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
					String pacs = this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
					String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
					String[] exam_nums = ids.split(",");
//					String resstr = this.customerInfoService.sendApplyChargeItem(this.model.getBatch_id(),user.getName(), ids.split(","));
					for (int i = 0; i < exam_nums.length; i++) {
						if("Y".equals(lis) && "N".equals(lis_bang)){
							this.lisPacsApplicationService.lisSend(exam_nums[i], null, user, false,lis_bang,"N");
						}
						if("Y".equals(pacs)){
							this.lisPacsApplicationService.pacsSend(exam_nums[i], null, user, false,"N");
						}
					}
					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("264");// 子功能id 必须填写
					sl.setExplain("团体批量申请 " + this.model.getBatch_id() + " " + ids);// 操作说明
					syslogService.saveSysLog(sl);
					
					this.message = "ok-批量申请执行完成";
				} else {
					this.message = "error-无效记录。";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

    /**
     * 
         * @Title: batchSigndo   
         * @Description: 执行批量签到 265   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */    
    public String batchSigndo() throws WebException, SQLException {

		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				if (!StringUtils.isEmpty(model.getExam_nums())) {
					String exam_nums = this.model.getIds().trim().substring(1, model.getExam_nums().trim().length());
					this.customerInfoService.batchSigndo(exam_nums.split(","), user.getUserid(),user.getCenter_num());
					String IS_HIS_INTERFACE = this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE", user.getCenter_num())
							.getConfig_value().trim();
					String IS_BAODAO_HIS_APPLICATION = this.customerInfoService
							.getCenterconfigByKey("IS_BAODAO_HIS_APPLICATION", user.getCenter_num()).getConfig_value().trim();
					if ("Y".equals(IS_HIS_INTERFACE) && "Y".equals(IS_BAODAO_HIS_APPLICATION)) {
						String[] exam_nums_s = exam_nums.split(",");
						for(int i=0;i<exam_nums_s.length;i++){
							ExamInfoUserDTO examinfo = this.customerInfoService.getExamInfoUserForExamId(exam_nums_s[i],user);
							this.collectFeesService.paymentApplication(examinfo.getExam_num(), user);
						}
					}
					// UserDTO user = (UserDTO) session.get("username");
					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("9");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("265");// 子功能id 必须填写
					sl.setExplain("团体批量签到 " + this.model.getBatch_id());// 操作说明
					syslogService.saveSysLog(sl);
					this.message = "ok-执行部分导入结束。";
				} else {
					this.message = "error-无效记录。";
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: getDjDpdf @Description:
	 * 打印导检单-翰思专用 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String getDjDpdf() throws WebException, SQLException, Exception {
		if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
			List<DjdbeanList> list = new ArrayList<DjdbeanList>();
			UserDTO user = (UserDTO) session.get("username");
			String hsurl = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_URL", user.getCenter_num()).getConfig_value().trim();
			list = customerInfoService.getPrintDjd(this.model.getIds(), hsurl, user.getUserid());
			String fileName = "/hsdjdts/djd.jasper";// 报表文件名称
			String mdfile = ServletActionContext.getServletContext().getRealPath(fileName);

			JRDataSource dataSource = new JRBeanCollectionDataSource(list);
			JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(mdfile);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

			String url = "/djdpdf/" + DateTimeUtil.getDateTimes() + "(" + user.getUserid() + ").pdf";
			String pdfpath = mdfile.substring(0, mdfile.length() - 19) + url;
			JRPdfExporter exporter = new JRPdfExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfpath);
			exporter.exportReport();
			String hsweburl = this.customerInfoService.getCenterconfigByKey("IS_OWN_URL", user.getCenter_num()).getConfig_value().trim();
			hsweburl = hsweburl + url;
			this.message = "ok-" + hsweburl;
		} else {
			this.message = "error-无效记录。";
		}
		this.outJsonStrResult(message);
		return NONE;
	}

	/**
	 * 
	 * @Title: custappointmentshow @Description: 体检人员预约管理主界面
	 * 267 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String custappointmentshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setTime1(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("267");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: setcusttime @Description:设置体检时间 @param: @return @param: @throws
	 * WebException @param: @throws SQLException @return: String @throws
	 */
	public String setcusttime() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.model.setComname("未知");
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		}

		if (this.model.getBatch_id() <= 0) {
			this.model.setBatch_name("未知");
		} else {
			Batch cif = new Batch();
			cif = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		if ((this.model.getIds() != null) && (this.model.getIds().trim()).length() > 1) {
			String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
			this.model.setIds(ids);
		}
		this.model
				.setTime1(this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: setcusttimedo @Description: 保存体检时间
	 * 269 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String setcusttimedo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		Batch taover = new Batch();
		taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
		if ("1".equals(taover.getOverflag())) {
			this.message = "error-批次任务已经封帐";
		} else {
			if ((this.model.getIds() != null) && (this.model.getIds().trim()).length() > 1) {
				String t1 = this.model.getTime1().replaceAll(":", "");
				String t2 = this.model.getTime2().replaceAll(":", "");
				if (Integer.valueOf(t1).intValue() >= Integer.valueOf(t2).intValue()) {
					this.message = "error-时间段设置错误！";
				} else {
					this.customerInfoService.setExamInfotime(this.model.getIds(), this.model.getBirthday(),
							this.model.getTime1(), this.model.getTime2(), user.getUserid());
					// UserDTO user = (UserDTO) session.get("username");
					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("269");// 子功能id 必须填写
					sl.setExplain("设置体检人员预约时间 " + this.model.getIds() + " " + this.model.getBirthday() + " "
							+ this.model.getTime1() + " " + this.model.getTime2());// 操作说明
					syslogService.saveSysLog(sl);
					this.message = "ok-体检时间设置完成。";
				}

			} else {
				this.message = "error-无效人员信息。";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: delcusttimedo @Description: 删除体检时间
	 * 270 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String delcusttimedo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		Batch taover = new Batch();
		taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
		if ("1".equals(taover.getOverflag())) {
			this.message = "error-批次任务已经封帐";
		} else {
			if ((this.model.getIds() != null) && (this.model.getIds().trim()).length() > 1) {

				this.customerInfoService.delExamInfotime(this.model.getIds(), user.getUserid());
				// UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("270");// 子功能id 必须填写
				sl.setExplain("删除体检人员预约时间 " + this.model.getIds());// 操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-体检时间删除完成。";

			} else {
				this.message = "error-无效人员信息。";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: countExamInfotime @Description: 统计时间段
	 * 271 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String countExamInfotime() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType) session.get("defaultapp");
		List<CustmTimeCountDTO> list = new ArrayList<CustmTimeCountDTO>();
		list = this.customerInfoService.countExamInfotime(this.model.getCompany_id(), this.model.getBatch_id(),
				user.getCenter_num() + "", this.defaultapp.getComid());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	 * @Title: customeritemshow @Description: 显示加项目主界面
	 * 276 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String customeritemaddshow() throws WebException {
		if ((this.model.getIds() == null) || (this.model.getIds().trim().length() <= 0)) {
			this.message = "未选择人员，操作不能继续。";
			return "commerror";
		} else {
			this.message = this.customerInfoService.checkExamInfotime(this.model.getIds());
			if ((this.message == null) || (this.message.indexOf("ok") < 0)) {
				return "commerror";
			} else {
				this.model.setAge(this.model.getIds().trim().split(",").length);
				String sex = this.customerInfoService.getIDSExamInfoSex(this.model.getIds());
				this.model.setSex(sex);
				return SUCCESS;
			}
		}
	}

	/**
	 * 
	 * @Title: customeritemdelshow @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @return: String @throws
	 */
	public String customeritemdelshow() throws WebException {
		if ((this.model.getIds() == null) || (this.model.getIds().trim().length() <= 0)) {
			this.message = "";
			return "commerror";
		} else {
			message = this.customerInfoService.checkExamInfotime(this.model.getIds());
			if ((message == null) || (message.indexOf("ok") < 0)) {
				return "commerror";
			} else {
				this.model.setAge(this.model.getIds().trim().split(",").length);
				String sex = this.customerInfoService.getIDSExamInfoSex(this.model.getIds());
				this.model.setSex(sex);
				return SUCCESS;
			}
		}
	}

	/**
	 * 
	 * @Title: customeritemadddo @Description: 人员添加项目
	 * 277 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String customeritemadddo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(model.getExam_nums())) {
			this.message = "error-无效体检用户";
		} else {
			Batch taover = new Batch();
			taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
				// 获取体检项目
				String itementities = this.model.getItementities();
				itementities = itementities.replace("}{", "},{");
				itementities = "[" + itementities + "]";
				JSONArray itemarray = JSONArray.fromObject(itementities);
				@SuppressWarnings("unchecked")
				List<GroupChargingItemDTO> listitem = (List<GroupChargingItemDTO>) JSONArray.toCollection(itemarray,
						GroupChargingItemDTO.class);

				String items = "";
				for (GroupChargingItemDTO gci : listitem) {
					items = items + "" + gci.getItem_code() + ",";
				}

				String centernum = user.getCenter_num();

				String[] exam_nums_s = model.getExam_nums().split(",");

				for (int i = 0; i < exam_nums_s.length; i++) {
					//long examid = Long.valueOf(exam_nums_s[i]);
					String itemcodes = this.customerInfoService.getItemCodesforExamId(exam_nums_s[i], user.getCenter_num());
					if (StringUtil.isEmpty(this.model.getExam_indicator())) {
						this.model.setExam_indicator("T");
					}
					this.customerInfoService.addIDSExamInfoChangItem(exam_nums_s[i], listitem, itemcodes,
							this.model.getExam_indicator(), user.getUserid(), user.getName(), centernum);

					SysLog sl = new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid() + "");
					sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
					sl.setXtgnid("");// 可不填写
					sl.setXtgnid2("277");// 子功能id 必须填写
					sl.setExplain("体检人员添加项目 " + exam_nums_s[i] + " " + items);// 操作说明
					syslogService.saveSysLog(sl);
				}

				this.message = "ok-操作结束！";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: deletechargitemOnedo @Description:执行减项操作
	 * 282 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String deletechargitemOnedo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		Batch taover = new Batch();
		taover = this.batchIpadService.getBatchByID(this.model.getBatch_id());
		if ("1".equals(taover.getOverflag())) {
			this.message = "error-批次任务已经封帐";
		} else {
			if ((this.model.getIds() == null) || (this.model.getIds().trim().length() <= 0)) {
				this.message = "error-无效体检用户";
			} else {
				if (this.model.getId() <= 0) {
					this.message = "error-无效收费项目";
				} else {
					//String[] idss = this.model.getIds().split(",");
					String[] exam_nums = this.model.getExam_nums().split(",");
					boolean md = true;
					String mdmess = "";
					for (int i = 0; i < exam_nums.length; i++) {
						//long examid = Long.valueOf(exam_nums[i]);// 人员id
						long chargingId = this.model.getId();
						ChargingItem chargingItem = chargingItemService.findChargingItem(chargingId);
						ExaminfoChargingItemDTO ec = this.customerInfoService.getExaminfoChargingItem(exam_nums[i],
								chargingItem.getItem_code());
						if ((ec != null) && ("Y".equals(ec.getPay_status()))) {
							mdmess = "所选人员里面有已经收费的情况，操作不能继续";
							md = false;
							break;
						}
					}
					if (!md) {
						this.message = "error-" + mdmess;
					} else {
					for (int i = 0; i < exam_nums.length; i++) {
						long examid = Long.valueOf(exam_nums[i]);// 人员id
						long chargingId = this.model.getId();
						ChargingItem chargingItem = chargingItemService.findChargingItem(chargingId);
						this.customerInfoService.delIDSExamInfoChangItem(exam_nums[i], chargingItem.getItem_code(), user.getUserid(),user);
						// UserDTO user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("278");// 子功能id 必须填写
						sl.setExplain("体检人员添加项目 " + exam_nums[i] + " " + chargingId);// 操作说明
						syslogService.saveSysLog(sl);
					}
					this.message = "ok-操作结束！";
					}
				}
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: custchangitemlist @Description:
	 * 获取体检人员的体检项目 @param: @return @param: @throws WebException @return:
	 * String @throws
	 */
	public String custchangitemlist() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
		list = this.customerInfoService.getExaminfoChargingItemforExamId(this.model.getExam_num(), "1", user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	 * @Title: custchangitemdellist @Description:
	 * 通过多个体检编号获取体检项目 @param: @return @param: @throws WebException @return:
	 * String @throws
	 */
	public String custchangitemdellist() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
		list = this.customerInfoService.getExaminfoChargingItemforExamIds(this.model.getIds(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	 * @Title: getComByBatchIpadList @Description: 通过单位名称获取体检任务
	 * 283 @param: @return @param: @throws WebException @param: @throws
	 * Exception @return: String @throws
	 */
	public String getComByBatchIpadList() throws WebException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String IS_COMPANY_SHOW_POW="N";
		try{
			IS_COMPANY_SHOW_POW=this.customerInfoService.getCenterconfigByKey("IS_COMPANY_SHOW_POW", user.getCenter_num()).getConfig_value().trim();
		}catch(Exception ex){
			
		}
		if("Y".equals(IS_COMPANY_SHOW_POW)){//如果需要权限控制
			this.model.setRemark("Y");
			List<WebResrelAtionship> wrs=user.getWebResource();
	        if(wrs!=null &&wrs.size()>0){
			for(WebResrelAtionship wr :wrs){
				if("RS038".equals(wr.getRes_code())){
					this.model.setRemark("N");
					break;
				}
			}
	        }
		}else{
			this.model.setRemark("N");
		}
		
		this.defaultapp = (SystemType) session.get("defaultapp");
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		if (StringUtil.isEmpty(model.getComname())) {
			model.setComname("");
		}
		String name =model.getComname();
		name= URLDecoder.decode(name, "UTF-8");
		tr = this.batchIpadService.getCompanyForNameBatch(name, this.defaultapp.getComid(),user.getCenter_num());
		this.outJsonResult(tr);
		return NONE;
	}

	/**
	 * 
	 * @Title: getCompersonByIdList @Description: 根据单位id获取所有的子单位
	 * 284 @param: @return @param: @throws WebException @param: @throws
	 * Exception @return: String @throws
	 */
	public String getCompersonByIdList() throws WebException, Exception {
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		if (this.model.getCompany_id() > 0) {
			tr = this.companyService.getCompanyPersonForId(this.model.getCompany_id());
		}
		this.outJsonResult(tr);
		return NONE;
	}

	/**
	 * 
	 * @Title: getbatchcompanyshow @Description: 通过体检计划获取计划内部门
	 * 285 @param: @return @param: @throws WebException @param: @throws
	 * Exception @return: String @throws
	 */
	public String getbatchcompanyshow() throws WebException, Exception {
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.companyService.getCompanyForBatchComId(model.getCompany_id());
		this.outJsonResult(tr);
		return NONE;

	}

	/**
	 * 更新导检单打印状态 764 @Title: updateGuidePrintStatus @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @param: @throws Exception @return: String @throws
	 */
	public String updateGuidePrintStatus() throws WebException, Exception {
		if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效体检编号";
		} else {
			List<ExamInfoUserDTO> list = this.customerInfoService.getExaminfoFreezeList(this.model.getIds());
			if (list.size() > 0) {
				this.message = "error-体检信息已冻结，操作不能继续";
			} else {
				this.message = this.customerInfoService.updateGuidePrintStatus(this.model.getIds());
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 更新条码打印状态 765 @Title: updateBarcodePrintStatus @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @param: @throws Exception @return: String @throws
	 */
	public String updateBarcodePrintStatus() throws WebException, Exception {
		this.message = this.customerInfoService.updateBarcodePrintStatus(this.model.getIds());
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 打印单个条码,显示需要打印的项目 767 @Title: getAllPrintItemByExamids @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @param: @throws Exception @return: String @throws
	 */
	public String getAllPrintItemByExamids() throws WebException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		List<PacsCountDTO> list = this.customerInfoService.getPrintItemByExamids(this.model.getIds(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 团体打印单个条码 768 @Title: getAllorintItemPage @Description:
	 * TODO(这里用一句话描述这个方法的作用) @param: @return @param: @throws
	 * WebException @return: String @throws
	 */
	public String getAllorintItemPage() throws WebException {
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: feeZbUserManager @Description: 体检信息冻结主界面
	 * 837 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String freezeUserManager() throws WebException {
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: feeZbusergroupmanager @Description: 体检信息冻结操作界面
	 * 838 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String freezeusergroupmanager() throws WebException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: feeZbteamlistshow @Description: 体检信息解冻操作界面
	 * 839 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String freezeteamlistshow() throws WebException {
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: getfreezeExamInfoUserList @Description: 冻结页面查询体检信息
	 * 840 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String getfreezeExamInfoUserList() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		if (this.model.getChkItem() != null) {

			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}

			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}

			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}

			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}

			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setAddress("");
			}

			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}

			if (this.model.getChkItem().trim().indexOf("freezb") < 0) {
				this.model.setStatus("");
			}

			if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
				this.model.setTime3("");
				this.model.setTime4("");
			}
			contractlist = this.customerInfoService.getfreeBzExamInfoUserList(this.model.getCompany_id(),
					this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(),
					this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),
					this.model.getAddress(), this.model.getTime3(), this.model.getTime4(), this.model.getLevels(),
					this.model.getStatus(), user.getUserid(), centernum, this.rows, this.getPage(), this.sort,
					this.order,user,user.getCenter_num());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: examinfofreezedo @Description: 执行冻结操作
	 * 841 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String examinfofreezedo() throws WebException {
		if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效体检编号";
		} else {
			UserDTO user = (UserDTO) session.get("username");
			this.message = this.customerInfoService.updateExamInfofreezeDo(this.model.getIds(), user.getUserid(),
					user.getCenter_num());
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("841");// 子功能id 必须填写
			sl.setExplain(this.model.getIds());// 操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: getfreezeExamInfoshow @Description: 执行解冻操作
	 * 842 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String getfreezeExamInfoshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		if ((this.model.getChkItem() != null) && (this.model.getChkItem().length() > 0)) {

			if (this.model.getChkItem().trim().indexOf("tjrw") < 0) {
				this.model.setBatchid(0);
			}

			if (this.model.getChkItem().trim().indexOf("com_name") < 0) {
				this.model.setCompany_id(0);
			}

			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}

			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
				this.model.setExam_num("");
			}
			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
				this.model.setCustname("");
			}
			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
				this.model.setSex("");
			}

			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
				this.model.setGroup_id(0);
			}

			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}

			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
				this.model.setExam_status("");
			}

			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
				this.model.setAddress("");
			}

			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}

			if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
				this.model.setTime3("");
				this.model.setTime4("");
			}

			if (this.model.getChkItem().trim().indexOf("update_date") < 0) {
				this.model.setTime1("");
				this.model.setTime2("");
			}

			contractlist = this.customerInfoService.getFreezeTermExamInfoshow(this.model.getCompany_id(),
					this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(),
					this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),
					this.model.getAddress(), this.model.getTime3(), this.model.getTime4(), this.model.getTime1(),
					this.model.getTime2(), this.model.getLevels(), user.getUserid(), centernum, this.rows,
					this.getPage(), this.sort, this.order,user,user.getCenter_num());
		}
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: examinfounfreezedo @Description: 执行解冻操作
	 * 843 @param: @return @param: @throws WebException @return: String @throws
	 */
	public String examinfounfreezedo() throws WebException {
		if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-无效体检编号";
		} else {
			UserDTO user = (UserDTO) session.get("username");
			this.message = this.customerInfoService.updateExamInfofUnreezeDo(this.model.getIds(), user.getUserid(),
					user.getCenter_num());
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("843");// 子功能id 必须填写
			sl.setExplain(this.model.getIds());// 操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	 * @Title: doAllUserGroupuser @Description: 871
	 * 单位批量设置行业类型 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */
	public String doAllUserhangye() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		if (this.model.getCompany_id() <= 0) {
			this.message = "error-单位编号无效";
		} else {
			CompanyInfo taover = new CompanyInfo();

			taover = this.companyService.findComByID(this.model.getCompany_id());
			if (StringUtil.isEmpty(taover.getIndustrycode())) {
				this.message = "error-请设置当前所选单位的行业信息。";
			} else {
				this.customerInfoService.setExamInfoHYByComID(this.model.getCompany_id(), taover.getIndustrycode(),
						user.getUserid());
				this.message = "ok-执行行业设置完成";
				// UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("4");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("871");// 子功能id 必须填写
				sl.setExplain("执行行业设置 " + this.model.getCompany_id());// 操作说明
				syslogService.saveSysLog(sl);

			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	 * @Title: getCompanForDept @Description:
	 * 255 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String getCompanForDept2() throws WebException, SQLException {
		List<CompanyDepartment> getbatchList = new ArrayList<CompanyDepartment>();
		getbatchList = this.companyService.getCompanyDepartmentByComid(this.model.getCompany_id());
		List<CompanyDepartment> getbatchList1 = new ArrayList<CompanyDepartment>();
		for (CompanyDepartment bd : getbatchList) {
			getbatchList1.add(bd);
		}
		this.outJsonResult(getbatchList1);
		return NONE;
	}
	
	/**
	 * 人员导入界面导出人员到excel
	     * @Title: saveimpData   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveimpData() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<ImpCustomerInfoDTO> list = new ArrayList<ImpCustomerInfoDTO>();
		list = this.customerInfoService.getImpCustomerInfoErrorList(this.model.getBatch_id(),
				this.model.getCompany_id(), user.getUserid());
		BatchDTO b= new BatchDTO();
		b=this.customerInfoService.getBatchAndCom(this.model.getBatch_id(), this.model.getCompany_id());
		HSSFWorkbook  workbook = new HSSFWorkbook();
		HSSFSheet  sheet = workbook.createSheet("Sheet1");
		HSSFRow  row = sheet.createRow(0);
		row.createCell(0).setCellValue("单位名称");
		row.createCell(1).setCellValue(b.getContact_name());
		row.createCell(2).setCellValue("方案名称");
		row.createCell(3).setCellValue(b.getBatch_name());
		row.createCell(4).setCellValue("团检人员信息模板");
		
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("档案号");		    
		row.createCell(1).setCellValue("证件号");		    
		row.createCell(2).setCellValue("姓名");		    
		row.createCell(3).setCellValue("性别");		    
		row.createCell(4).setCellValue("年龄");		    
		row.createCell(5).setCellValue("出生日期");		    
		row.createCell(6).setCellValue("婚否");		    
		row.createCell(7).setCellValue("部门");		    
		row.createCell(8).setCellValue("职务");		    
		row.createCell(9).setCellValue("电话");		    
		row.createCell(10).setCellValue("备注");		    
		row.createCell(11).setCellValue("体检类型");		    
		row.createCell(12).setCellValue("人员类型");		    
		row.createCell(13).setCellValue("其他");		    
		row.createCell(14).setCellValue("工号");		    
		row.createCell(15).setCellValue("就诊卡");		    
		row.createCell(16).setCellValue("介绍人");		    
		row.createCell(17).setCellValue("证件类型");		    
		row.createCell(18).setCellValue("状态");		    
		row.createCell(19).setCellValue("导入错误原因");
		
		int i=1;
		for (ImpCustomerInfoDTO imp:list) {
		i+=1;
		row = sheet.createRow(i);		
		row.createCell(0).setCellValue(imp.getArch_num());
		
		row.createCell(1).setCellValue(imp.getId_num());
		row.createCell(2).setCellValue(imp.getCustname());
		row.createCell(3).setCellValue(imp.getSex());
		row.createCell(4).setCellValue(imp.getAge());
		row.createCell(5).setCellValue(imp.getBirthday());
		row.createCell(6).setCellValue(imp.getIs_marriage());
		row.createCell(7).setCellValue(imp.get_level());
		row.createCell(8).setCellValue(imp.getPosition());
		row.createCell(9).setCellValue(imp.getTel());
		row.createCell(10).setCellValue(imp.getRemark());
		row.createCell(11).setCellValue(imp.getExam_type());
		row.createCell(12).setCellValue(imp.getCustomer_type());
		row.createCell(13).setCellValue(imp.getOthers());
		row.createCell(14).setCellValue(imp.getEmployeeID());
		row.createCell(15).setCellValue(imp.getVisit_no());
		row.createCell(16).setCellValue(imp.getIntroducer());
		row.createCell(17).setCellValue(imp.getIdtypename());
		row.createCell(18).setCellValue(imp.getSflags());
		row.createCell(19).setCellValue(imp.getNotices());
		}
		
		try {
			// 客户端不缓存
			 response.reset();// 清空response  
			 String filename=b.getBatch_name()+"-"+DateTimeUtil.getDateTimes()+".xls";
	         response.addHeader("Content-Disposition", "attachment;filename="+filename);// 设置response的Header   
	         response.setContentType("application/octet-stream");  
	         OutputStream os=null;
	         os = new BufferedOutputStream(response.getOutputStream());//输出流  
	         workbook.write(os);  
	         os.flush();
		} catch (IOException e) {
		e.printStackTrace();
		}
	return NONE;	
	}
	/**
	 * 换岗就诊卡获取人员信息
	     * @Title: getJiuzhenkaInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getJiuzhenkaInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String url = this.webserviceConfigurationService.getWebServiceConfig("PAYMENT_APPLICATION", user.getCenter_num()).getConfig_url();
		JiuzhenkaInfo in = new JiuzhenkaInfo();
		MzghBaseInfoDTO  dto = in.getCardInfo(model.getJiuzhenka(), url,"CardInfo");
		this.outJsonResult(dto);
		return NONE;
	}
	
	/**
	 * 根据档案号查询档案信息
	     * @Title: getCustomerinfoByArchNum   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCustomerinfoByArchNum() throws WebException{
		CustomerInfoDTO cust = this.customerInfoService.getCustomerinfoByArchNum(this.model.getArch_num());
		this.outJsonResult(cust);
		return NONE;
	}
	/**
	 * 加项---根据项目和样本关系带出项目959
	     * @Title: getItemSampleDemo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemSampleDemo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<GroupChargingItemDTO> li  = this.batchIpadService.getItemSampleDemo(model.getId(), user.getCenter_num());
		this.outJsonResult(li);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("959");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: deleteimpuserToExaminfodo   
	     * @Description: 删除当前任务批次下的错误记录   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteimpuserToExaminfodo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.customerInfoService.delImpCustomerInfoErrorList(this.model.getBatch_id(),
				this.model.getCompany_id(), user.getUserid());
		this.message = "ok-处理完成";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 设置普通体检报告类型
	     * @Title: comonReportType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String comonReportType() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1536");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 修改同步信息
	     * @Title: isSynchroExamInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String isSynchroExamInfo() throws WebException, SQLException, Exception {
		String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
		this.message = this.customerInfoService.isSynchroExamInfo(ids);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: reportUploadExamInfo   
	     * @Description: TODO(批量上传报告)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String reportUploadExamInfo() throws WebException, SQLException {
		String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
		this.message = this.customerInfoService.reportUploadExamInfo(ids);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: djtTpanel   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String djtTpanel() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		/*this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());*/
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		this.model.setBarcode_print_type(
				this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value()
				.trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setTime1(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setStatus(this.customerInfoService.getCenterconfigByKey("IS_BATCH_CHECK", user.getCenter_num()).getConfig_value().trim());//自动审核任务标识
		List<WebResrelAtionship> web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS026")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsaccounttype(1);
					}
				}
				
				if(web.get(i).getRes_code().equals("RS034")){//
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsovertype(1);
					}
				}
				
				if(web.get(i).getRes_code().equals("RS027")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIschecktype(1);
					}
				}
				
				/*if(web.get(i).getRes_code().equals("RS032")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsSynchronous(1);

					}
				}*/
			
				if(web.get(i).getRes_code().equals("RS035")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsSMS(1);
					}
				}
			}
		}	
		
		//团体转个人资源
				if (web != null) {
					for (int i = 0; i < web.size(); i++) {
						if (web.get(i).getRes_code().equals("RS041")) {
							if ("1".equals(web.get(i).getDatavalue())) {
								this.model.setTtog(1);
								break;
							}
						}
					}
				}
				
				//个人转团体资源
				if (web != null) {
					for (int i = 0; i < web.size(); i++) {
						if (web.get(i).getRes_code().equals("RS042")) {
							if ("1".equals(web.get(i).getDatavalue())) {
								this.model.setGtot(1);
								break;
							}
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
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1545");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	/**
	 * 
	     * @Title: getExamInfoYditemAddList   
	     * @Description: TODO(人员异动 新增项目)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoYditemAddList() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getExamInfoYdAddItemList(this.model.getCompany_id(), this.model.getBatch_id(), centernum,
				this.rows, this.getPage(), this.sort, this.order);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("1260");// 可不填写
		sl.setXtgnid2("1260");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: ydcustdelchangitemlist   
	     * @Description: TODO(人员异动 新增项目)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String ydcustdelchangitemlist() throws WebException {
		List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
		if (this.model.getExam_id() > 0) {
			list = this.customerInfoService.getExaminfoDelChargingItemforExamId(this.model.getExam_num());
		}
		this.outJsonResult(list);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("1263");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getExamInfoYdUserList   
	     * @Description: TODO(人员异动 删除体检)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoYdUserList() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getExamInfoYdUserList(this.model.getCustomer_type(),
				this.model.getCompany_id(), this.model.getBatch_id(), this.model.getDept_id(), this.model.getGroup_id(),
				this.model.getId_num(), this.model.getArch_num(), this.model.getCustname(), this.model.getTel(),
				this.model.getStatus(), this.model.getBirthday(), this.model.getEmployeeID(), this.model.getSex(),
				this.model.getIs_marriage(), this.model.getCustomer_type_id(), this.model.getDjdstatuss(), centernum,
				this.rows, this.getPage(), this.sort, this.order,user);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("1258");// 可不填写
		sl.setXtgnid2("1258");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getExamInfoYditemdelList   
	     * @Description: TODO(人员体检 删除体检)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoYditemdelList() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getExamInfoYdDelItemList(this.model.getCompany_id(), this.model.getBatch_id(), centernum,
				this.rows, this.getPage(), this.sort, this.order);
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
		sl.setXtgnid("1262");// 可不填写
		sl.setXtgnid2("1262");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: getBatchForGroupTwo   
	     * @Description: TODO(得到批次信息)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getBatchForGroupTwo() throws WebException, SQLException {
		List<GroupInfoDTO> getbatchList = new ArrayList<GroupInfoDTO>();
		getbatchList = this.batchIpadService.getGroupListForBatchId(this.model.getBatch_id());
		List<GroupInfoDTO> getbatchList1 = new ArrayList<GroupInfoDTO>();
		GroupInfoDTO dcd = new GroupInfoDTO();
		dcd.setId(-1);
		dcd.setGroup_name("全部");
		getbatchList1.add(dcd);
		dcd = new GroupInfoDTO();
		dcd.setId(0);
		dcd.setGroup_name("未分组");
		getbatchList1.add(dcd);
		for (GroupInfoDTO bd : getbatchList) {
			getbatchList1.add(bd);

		}
		this.outJsonResult(getbatchList1);
		return NONE;
	}
	
	/**
	 * 科室树
	     * @Title: depTree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String depTree()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.customerInfoService.getDepTree(this.model, user.getCenter_num()));
		return NONE;
	}
	/**
	 * 登记台获取获取项目
	     * @Title: getDJTItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDJTItem()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.customerInfoService.getDJTItem(this.model, user.getCenter_num()));
		return NONE;
	}
	/**
	 * 套餐id获取收费项目
	     * @Title: getSetidChagingitem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSetidChagingitem()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.customerInfoService.getSetidChagingitem(this.model,user.getCenter_num()));
		return NONE;
	}
	/**
	 * 收费项目带出耗材项目
	     * @Title: getChargingItemConsumables   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingItemConsumables()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String IS_ITEM_HC = this.customerInfoService.getCenterconfigByKey("IS_ITEM_HC", user.getCenter_num())
				.getConfig_value().trim();
		List<ChargingItemConsumablesItemDTO>  li = new ArrayList<ChargingItemConsumablesItemDTO>();
		if(IS_ITEM_HC.equals("Y")){
			li = this.customerInfoService.getChargingItemConsumables(this.model,user.getCenter_num());
		}
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 获取人员已加的项目
	     * @Title: getExaminfoChargingItemadd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminfoChargingItemadd()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(this.customerInfoService.getExaminfoChargingItemadd(this.model, user.getCenter_num()));
		return NONE;
	}
	/**
	 * 项目id查询收费项目
	     * @Title: getDJTfindChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDJTfindChargingItem()throws WebException{
		this.outJsonResult(this.chargingItemService.getChargingItemLb(model.getId()));
		return NONE;
	}
	
	
	/**
     *  
         * @Title: teamUserExportExcel   
         * @Description: TODO(团体人员管理 导出excel)   
         * @param: @return      
         * @return: String      
         * @throws
     */
    public String teamUserExportExcel() {  
        try { 
        	CompanyInfo company = this.companyService.findComByID(this.model.getCompany_id());
            //第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet(company.getCom_Name());  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0);  
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
  
            cell = row.createCell(0);  
            cell.setCellValue("档案号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(1);  
            cell.setCellValue("体检号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(2);  
            cell.setCellValue("身份证号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(3);  
            cell.setCellValue("工号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(4);  
            cell.setCellValue("姓名");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(5);  
            cell.setCellValue("性别");  
            cell.setCellStyle(style);
            
            cell = row.createCell(6);  
            cell.setCellValue("婚否");  
            cell.setCellStyle(style);
            
            cell = row.createCell(7);  
            cell.setCellValue("年龄");  
            cell.setCellStyle(style);
            
            cell = row.createCell(8);  
            cell.setCellValue("电话");  
            cell.setCellStyle(style);
            
            cell = row.createCell(9);  
            cell.setCellValue("分组名称");  
            cell.setCellStyle(style);
            
            cell = row.createCell(10);  
            cell.setCellValue("套餐");  
            cell.setCellStyle(style);
            
            cell = row.createCell(11);  
            cell.setCellValue("团费");  
            cell.setCellStyle(style);
            
            cell = row.createCell(12);  
            cell.setCellValue("个费");  
            cell.setCellStyle(style);
            
            cell = row.createCell(13);  
            cell.setCellValue("体检日期");  
            cell.setCellStyle(style);
            
            cell = row.createCell(14);  
            cell.setCellValue("职务");  
            cell.setCellStyle(style);
            
	    	cell = row.createCell(15);  
            cell.setCellValue("部门");  
            cell.setCellStyle(style);
            
            cell = row.createCell(16);  
            cell.setCellValue("预约日期");  
            cell.setCellStyle(style);
            
            cell = row.createCell(17);  
            cell.setCellValue("体检状态");  
            cell.setCellStyle(style);
            
            cell = row.createCell(18);  
            cell.setCellValue("其他");  
            cell.setCellStyle(style);
            
            /*cell = row.createCell(17);  
            cell.setCellValue("体检类别");  
            cell.setCellStyle(style);*/
            
            cell = row.createCell(19);  
            cell.setCellValue("人员类别");  
            cell.setCellStyle(style);
            
            cell = row.createCell(20);  
            cell.setCellValue("开票部门");  
            cell.setCellStyle(style);
            
            cell = row.createCell(21);  
            cell.setCellValue("备注");  
            cell.setCellStyle(style);
            
            //第六步，写入实体数据，实际应用中这些数据从数据库得到  
            UserDTO user = (UserDTO) session.get("username");
    		String centernum = user.getCenter_num();
           /* PageReturnDTO map = this.customerInfoService.getExamInfoUserListT(this.model.getCustomer_type(),
    				this.model.getCompany_id(), this.model.getBatch_id(), this.model.getDept_id(), this.model.getGroup_id(),
    				this.model.getId_num(), this.model.getArch_num(), this.model.getCustname(), this.model.getTel(),
    				this.model.getStatus(), this.model.getBirthday(), this.model.getEmployeeID(), this.model.getSex(),
    				this.model.getIs_marriage(), this.model.getCustomer_type_id(), this.model.getDjdstatuss(), model.getMin_age(),model.getMax_age(),centernum,
    				100000, 1, this.sort, this.order);*/
            
    		PageReturnDTO map =  this.customerInfoService.getExamInfoUserListDeptAll(this.model.getCustomer_type(),
    				this.model.getCompany_id(), this.model.getBatch_id(), this.model.getDept_ids(), this.model.getGroup_id(),
    				this.model.getId_num(), this.model.getArch_num(), this.model.getCustname(), this.model.getTel(),
    				this.model.getStatus(), this.model.getBirthday(), this.model.getEmployeeID(), this.model.getSex(),
    				this.model.getIs_marriage(), this.model.getCustomer_type_id(), this.model.getDjdstatuss(), model.getMin_age(),model.getMax_age(),centernum,
    				100000, 1, this.sort, this.order,user,user.getCenter_num());
            if ((map != null) && (map.getRows() != null) && (map.getRows().size() > 0)) {
            	for(int i=0;i<map.getRows().size();i++){
            		ExamInfoUserDTO eu = (ExamInfoUserDTO) map.getRows().get(i);
            		
            		row = sheet.createRow(i+1);
            		
            		row.createCell(0).setCellValue(eu.getArch_num());  
                    row.createCell(1).setCellValue(eu.getExam_num());  
                    row.createCell(2).setCellValue(eu.getId_num());  
                    row.createCell(3).setCellValue(eu.getEmployeeID());  
                    row.createCell(4).setCellValue(eu.getUser_name());  
                    row.createCell(5).setCellValue(eu.getSex());
                    row.createCell(6).setCellValue(eu.getIs_marriage());
                    row.createCell(7).setCellValue(eu.getAge());
                    row.createCell(8).setCellValue(eu.getPhone());
                    row.createCell(9).setCellValue(eu.getGroup_name());
                    row.createCell(10).setCellValue(eu.getSet_name());
                    
                    row.createCell(11).setCellValue(eu.getTeam_pay());
                    row.createCell(12).setCellValue(eu.getPersonal_pay());
                    
                    row.createCell(13).setCellValue(eu.getRegister_date());
                    row.createCell(14).setCellValue(eu.getPosition());
        	    	row.createCell(15).setCellValue(eu.getDep_name());
        	    	row.createCell(16).setCellValue(eu.getRegister_date());
        	    	if("Y".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(17).setCellValue("预约");
        	    	}else if("D".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(17).setCellValue("登记");
        	    	}else if("J".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(17).setCellValue("检查中");
        	    	}else if("Z".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(17).setCellValue("已总检");
        	    	}
        	    	row.createCell(18).setCellValue(eu.getOthers());
                    //row.createCell(17).setCellValue(eu.getLis());
                    row.createCell(19).setCellValue(eu.getCustomer_type_name()); //人员类别
                    row.createCell(20).setCellValue(eu.getBilldep());
                    row.createCell(21).setCellValue(eu.getRemarke());
            	}
            }
            //第七步，将文件存到流中  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
  
            excelStream = is;             //文件流  
            excelFileName = "report.xls"; //设置下载的文件名  
        }  
        catch(Exception e) {  
            e.printStackTrace();  
        }  
  
        return "success";  
    }
    //团体冻结人员导出Excel
    public String  freezeUserExportExcel(){
    	try {
            //第一步，创建一个webbook，对应一个Excel文件  
            HSSFWorkbook wb = new HSSFWorkbook();  
            //第二步，在webbook中添加一个sheet，对应Excel文件中的 sheet  
            HSSFSheet sheet = wb.createSheet("冻结人员");  
            //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制  
            HSSFRow row = sheet.createRow(0);  
            //第四步，创建单元格样式：居中  
            HSSFCellStyle style = wb.createCellStyle();  
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
            //第五步，创建表头单元格，并设置样式  
            HSSFCell cell;  
  
            cell = row.createCell(0);  
            cell.setCellValue("体检号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(1);  
            cell.setCellValue("身份证号");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(2);  
            cell.setCellValue("姓名");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(3);  
            cell.setCellValue("性别");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(4);  
            cell.setCellValue("年龄");  
            cell.setCellStyle(style);  
  
            cell = row.createCell(5);  
            cell.setCellValue("电话");  
            cell.setCellStyle(style);
            
            cell = row.createCell(6);  
            cell.setCellValue("部门");  
            cell.setCellStyle(style);
            
            cell = row.createCell(7);  
            cell.setCellValue("体检状态");  
            cell.setCellStyle(style);
            
            cell = row.createCell(8);  
            cell.setCellValue("分组名称");  
            cell.setCellStyle(style);
            
            cell = row.createCell(9);  
            cell.setCellValue("套餐名称");  
            cell.setCellStyle(style);
            
            cell = row.createCell(10);  
            cell.setCellValue("操作者");  
            cell.setCellStyle(style);
            
            cell = row.createCell(11);  
            cell.setCellValue("冻结时间");  
            cell.setCellStyle(style);
            
            
            //第六步，写入实体数据，实际应用中这些数据从数据库得到  
            UserDTO user = (UserDTO) session.get("username");
            String centernum = user.getCenter_num();
    		PageReturnDTO map = new PageReturnDTO();
    		if ((this.model.getChkItem() != null) && (this.model.getChkItem().length() > 0)) {

    			if (this.model.getChkItem().trim().indexOf("tjrw") < 0) {
    				this.model.setBatchid(0);
    			}

    			if (this.model.getChkItem().trim().indexOf("com_name") < 0) {
    				this.model.setCompany_id(0);
    			}

    			if (this.model.getChkItem().trim().indexOf("custname") < 0) {
    				this.model.setCustname("");
    			}else{
    				this.model.setCustname(new String(this.model.getCustname().getBytes("iso8859-1"),"UTF-8")); 
    			}
    			if (this.model.getChkItem().trim().indexOf("exam_num") < 0) {
    				this.model.setExam_num("");
    			}
    			if (this.model.getChkItem().trim().indexOf("sex") < 0) {
    				this.model.setSex("");
    			}else{
    				this.model.setSex(new String(this.model.getSex().getBytes("iso8859-1"),"UTF-8")); 
    			}
    			if (this.model.getChkItem().trim().indexOf("group_id") < 0) {
    				this.model.setGroup_id(0);
    			}

    			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
    				this.model.setSet_id(0);
    			}

    			if (this.model.getChkItem().trim().indexOf("exam_status") < 0) {
    				this.model.setExam_status("");
    			}

    			if (this.model.getChkItem().trim().indexOf("rylb") < 0) {
    				this.model.setAddress("");
    			}

    			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
    				this.model.setLevels("");
    			}

    			if (this.model.getChkItem().trim().indexOf("join_date") < 0) {
    				this.model.setTime3("");
    				this.model.setTime4("");
    			}

    			if (this.model.getChkItem().trim().indexOf("update_date") < 0) {
    				this.model.setTime1("");
    				this.model.setTime2("");
    			}
    			map = this.customerInfoService.getFreezeTermExamInfoshow(this.model.getCompany_id(),
    					this.model.getBatchid(), this.model.getExam_num(), this.model.getGroup_id(),
    					this.model.getExam_status(), this.model.getSex(), this.model.getCustname(), this.model.getSet_id(),
    					this.model.getAddress(), this.model.getTime3(), this.model.getTime4(), this.model.getTime1(),
    					this.model.getTime2(), this.model.getLevels(), user.getUserid(), centernum, this.rows,
    					this.getPage(), this.sort, this.order,user,user.getCenter_num());
    		}
    		
            if ((map != null) && (map.getRows() != null) && (map.getRows().size() > 0)) {
            	for(int i=0;i<map.getRows().size();i++){
            		ExamInfoUserDTO eu = (ExamInfoUserDTO) map.getRows().get(i);
            		row = sheet.createRow(i+1);
            		row.createCell(0).setCellValue(eu.getExam_num());  
                    row.createCell(1).setCellValue(eu.getId_num());  
                    row.createCell(2).setCellValue(eu.getUser_name());  
                    row.createCell(3).setCellValue(eu.getSex());  
                    row.createCell(4).setCellValue(eu.getAge());  
                    row.createCell(5).setCellValue(eu.getPhone());
                    row.createCell(6).setCellValue(eu.getDep_name());
                    if("Y".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(7).setCellValue("预约");
        	    	}else if("D".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(7).setCellValue("登记");
        	    	}else if("J".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(7).setCellValue("检查中");
        	    	}else if("Z".equalsIgnoreCase(eu.getStatus())) {
        	    		row.createCell(7).setCellValue("已总检");
        	    	}
                    row.createCell(8).setCellValue(eu.getGroup_name());
                    row.createCell(9).setCellValue(eu.getSet_name());
                    row.createCell(10).setCellValue(eu.getChi_name());
                    
                    row.createCell(11).setCellValue(eu.getUpdate_time());
            	}
            }
            //第七步，将文件存到流中  
            ByteArrayOutputStream os = new ByteArrayOutputStream();  
            wb.write(os);  
            byte[] fileContent = os.toByteArray();  
            ByteArrayInputStream is = new ByteArrayInputStream(fileContent);  
  
            excelStream = is;             //文件流  
            excelFileName = "report.xls"; //设置下载的文件名  
        
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	 return "success";  
    }
    //查询档案号下边的体检号
    public  String getexamnumByArchnum(){
    	if (this.model.getArch_num() != null && !"".equals(this.model.getArch_num())) {
    		List<ExamInfoDTO> list = customerInfoService.getexamnumByArchnum(this.model.getArch_num());
    		this.outJsonResult(list);
		}
    	return NONE;
    }
    
   //生成档案号
    public  String createArch_num(){
    	UserDTO user = (UserDTO) session.get("username");
    	String arch_num = this.batchIpadService.GetCreateID("vipno", user.getCenter_num());
    	this.outJsonStrResult(arch_num);
    	return NONE;
    }
    //导检单存档
    public  String  createDJDImage(){
    	this.model.setExam_num(model.getExam_num());
    	return SUCCESS;
    }
    
    //-------------------------------------------------------------  
    private InputStream excelStream;  //输出流变量  
    private String excelFileName; //下载文件名  
  
    public InputStream getExcelStream() {  
        return excelStream;  
    }  
    public void setExcelStream(InputStream excelStream) {  
        this.excelStream = excelStream;  
    }  
    public String getExcelFileName() {  
        return excelFileName;  
    }  
    public void setExcelFileName(String excelFileName) {  
        this.excelFileName = excelFileName;  
    }

    /**
	 * 
	     * @Title: getExamInfoUserListDeptAll   
	     * @Description: TODO(多部门查询)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoUserListDeptAll() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getExamInfoUserListDeptAll(this.model.getCustomer_type(),
				this.model.getCompany_id(), this.model.getBatch_id(), this.model.getDept_ids(), this.model.getGroup_id(),
				this.model.getId_num(), this.model.getArch_num(), this.model.getCustname(), this.model.getTel(),
				this.model.getStatus(), this.model.getBirthday(), this.model.getEmployeeID(), this.model.getSex(),
				this.model.getIs_marriage(), this.model.getCustomer_type_id(), this.model.getDjdstatuss(), model.getMin_age(),model.getMax_age(),centernum,
				this.rows, this.getPage(), this.sort, this.order,user,user.getCenter_num());
		this.outJsonResult(contractlist);
		return NONE;
	}
	public String padLogin() throws Exception {

		session.remove("userinfo");
		session.remove("username"); 
		return "padLogin";
	}
}