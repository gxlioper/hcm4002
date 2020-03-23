package com.hjw.zyb.action;

import com.hjw.config.Logincheck;
import com.hjw.ireport.DTO.DjdbeanList;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.webService.client.Bean.QueueAddBean;
import com.hjw.webService.client.Bean.QueueCustomerBean;
import com.hjw.webService.client.PutReportMessage;
import com.hjw.webService.client.QueueAddToSendMessage;
import com.hjw.webService.client.QueueCustomSendMessage;
import com.hjw.webService.client.body.QueueResBody;
import com.hjw.webService.client.body.ResultHeader;
import com.hjw.webService.client.jianqiao.PutReportControlJQ;
import com.hjw.webService.client.jianqiao.bean.req.BodyJQ;
import com.hjw.webService.client.wuwei.PutReportControlWW;
import com.hjw.webService.client.wuwei.bean.company.TB_TJ_CRPT;
import com.hjw.webService.client.wuwei.bean.req.PERSON;
import com.hjw.wst.DTO.*;
import com.hjw.wst.domain.*;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.service.*;
import com.hjw.zyb.DTO.ExamInfoExtDTO;
import com.hjw.zyb.DTO.ZybExamInfoUserDTO;
import com.hjw.zyb.DTO.ZybExamOccuhazardfactorsDTO;
import com.hjw.zyb.DTO.ZybOccuHisDTO;
import com.hjw.zyb.domain.ZybDiseaseHistory;
import com.hjw.zyb.domain.ZybExamOccuhazardfactors;
import com.hjw.zyb.domain.ZybOccuHis;
import com.hjw.zyb.model.ZybImpCustomerInfoModel;
import com.hjw.zyb.service.ZybCustomerInfoService;
import com.hjw.zyb.service.ZybOccuhisService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.hsqldb.lib.StringUtil;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
public class ZybCustomerInfoAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private BatchService batchService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	private	String sort="";//排序--字段
	private String  order="";//排序--排序方式
	private String web_Resource;
	private String idss = "";
	
	private CompanyService companyService;
	private ZybImpCustomerInfoModel model = new ZybImpCustomerInfoModel();
	private CustomerInfoService customerInfoService;
    private DjtCustomerInfoService djtCustomerInfoService;   
	private String sfz_div_ocx;// 身份证读卡器ocx
	private String sfz_div_code;// 身份证厂家代码	
	private SyslogService syslogService; 
	private ZybCustomerInfoService zybCustomerInfoService;		
    private TeamAccountService teamAccountService;   
    private LisPacsApplicationService lisPacsApplicationService;  
    
	private ZybOccuhisService zybOccuhisService;
	private examInfoService examInfoService;
	
	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
	}

	public String getIdss() {
		return idss;
	}

	public void setIdss(String idss) {
		this.idss = idss;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setZybOccuhisService(ZybOccuhisService zybOccuhisService) {
		this.zybOccuhisService = zybOccuhisService;
	}

	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}

	public void setTeamAccountService(TeamAccountService teamAccountService) {
		this.teamAccountService = teamAccountService;
	}

	public String getWeb_Resource() {
		return web_Resource;
	}

	public void setWeb_Resource(String web_Resource) {
		this.web_Resource = web_Resource;
	}

	public void setZybCustomerInfoService(ZybCustomerInfoService zybCustomerInfoService) {
		this.zybCustomerInfoService = zybCustomerInfoService;
	}

	public void setSyslogService(SyslogService syslogService) {
	   this.syslogService = syslogService;
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

	public void setModel(ZybImpCustomerInfoModel model) {
		this.model = model;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	/**
	 *   zyb10 职业病团体人员显示
	     * @Title: zybusergroupmanager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybusergroupmanager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value().trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setTime1(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(
				this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_barcode_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb10");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	

	/**
	 * 
	     * @Title: getExamInfoUserList   
	     * @Description: zyb11 职业病团体查询列表   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybgetExamInfoUserList() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.customerInfoService.getExamInfoUserList(this.model.getCompany_id(),this.model.getBatch_id(), this.model.getDept_id(),
				this.model.getGroup_id(), this.model.getId_num(), this.model.getArch_num(), this.model.getCustname(),
				this.model.getTel(),this.model.getStatus(),this.model.getBirthday(),this.model.getStartdate(),this.model.getEnddate(),
				this.model.getEmployeeID(),this.model.getSex(),this.model.getIs_marriage(),this.model.getCustomer_type_id(),null,centernum,"2", this.rows, this.getPage(),this.sort,this.order,user);
		this.outJsonResult(contractlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zybimpusershow   
	     * @Description: zyb12 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybimpusershow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb12");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @Title: zybimpusershow_set   
	 * @Description: zyb508 导入临时表操作界面(关联自选套餐)
	 * @param: @return
	 * @param: @throws WebException
	 * @param: @throws SQLException      
	 * @return: String      
	 * @throws
	 */
	public String zybimpusershow_set() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb508");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	     * @Title: zybimpusershowlist   
	     * @Description: 职业病导入表查询列表 zyb13   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybimpusershowlist() throws WebException, SQLException {
		PageReturnDTO contractlist = new PageReturnDTO();
		UserDTO user = (UserDTO) session.get("username");
		if (rows <= 15) {
			rows = 250;
		}
		contractlist = this.customerInfoService.getImpCustomerInfoList(this.model.getBatch_id(),
				this.model.getCompany_id(), user.getUserid(), InfoType.ZYB, this.rows, this.getPage());
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	     * @Title: zybsaveCustomerTmplist   
	     * @Description:保存导入的临时表数据 zyb15   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */

	public String zybsaveCustomerTmplist() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getId() <= 0) {
			this.message = "error-记录编号无效";
		} else {
			ImpCustomerInfo ci = new ImpCustomerInfo();
			ci = this.customerInfoService.loadImpCustomerInfo(this.model.getId());
			ci.set_level(this.model.get_level());
			ci.setAge(this.model.getAge());
			ci.setVisit_no(this.model.getVisit_no());
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
			ci.setOccusector(this.model.getOccusector());
			ci.setOccutypeofwork(this.model.getOccutypeofwork());
			ci.setDamage(this.model.getDamage());
			ci.setEmployeeage(this.model.getEmployeeage());
			ci.setJoinDatetime(this.model.getJoinDatetime());
			ci.setSets(this.model.getSets());
			ci.setZywhys(this.model.getZywhys());
			this.customerInfoService.updateImpCustomerInfo(ci);
			this.message = "ok-修改导入数据成功";
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb15");//子功能id 必须填写
			sl.setExplain("数据导入 "+ci.getCustname()+" "+ci.getBatch_id());//操作说明
			syslogService.saveSysLog(sl);
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zybCustomerHislist   
	     * @Description: 职业病单位人员管理获取职业病历史zyb21  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybCustomerHislist() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
			PageReturnDTO list=this.zybCustomerInfoService.getCustOccuHisList(this.model.getIsradiation(),this.model.getExam_num(),rows, this.getPage());
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb21");//子功能id 必须填写
			sl.setExplain(this.model.getExam_num());//操作说明
			syslogService.saveSysLog(sl);
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 
	     * @Title: zybdelCustomerTmplist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybdelCustomerTmplist() throws WebException, SQLException {
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
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb17");//子功能id 必须填写
				sl.setExplain("删除临时数据 "+ci.getCustname());//操作说明
				syslogService.saveSysLog(sl);
			}

			this.message = "ok-删除导入的数据成功";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: zyboccwhyslbadddo   
	     * @Description: zyb32 职业病保存危害类别
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zyboccwhyslbadddo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if(StringUtil.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效体检人员无效";
		} else if(StringUtil.isEmpty(model.getHazard_list())){
			this.message = "error-职业危害因素无效";
		} else {
			JSONArray hazard_array = JSONArray.fromObject(model.getHazard_list());
			List<ZybExamOccuhazardfactorsDTO> hazard_list = (List<ZybExamOccuhazardfactorsDTO>) JSONArray.toCollection(hazard_array, ZybExamOccuhazardfactorsDTO.class);
			boolean flags = false;
			for (int j = 0; j < hazard_list.size(); j++) {
				List<ZybExamOccuhazardfactorsDTO> occ = new ArrayList<ZybExamOccuhazardfactorsDTO>();
				occ = this.zybCustomerInfoService.getexamOcchazardfactors(this.model.getExam_num());
				for (ZybExamOccuhazardfactorsDTO docc : occ) {
					if (docc.getHazard_code().equals(hazard_list.get(j).getHazard_code()) &&
							docc.getOccuphyexaclassid().equals(hazard_list.get(j).getOccuphyexaclassid())) {
						flags = true;
						this.message = "error-"+hazard_list.get(j).getHazard_name()+"----"+hazard_list.get(j).getOccuphyexaclass_name();
						break;
					}
				}
				if (flags) {
					//this.message = "error-相同的记录已经存在，新增失败";
				}
			}
			if (!flags) {
				for (int j = 0; j < hazard_list.size(); j++) {
					ZybExamOccuhazardfactors zybwh = new ZybExamOccuhazardfactors();
					zybwh.setArch_num(this.model.getArch_num());
					zybwh.setCreate_time(Timeutils.getNowDate());
					zybwh.setCreater(user.getUserid());
					zybwh.setExam_num(this.model.getExam_num());
					zybwh.setHazard_code(hazard_list.get(j).getHazard_code());
					zybwh.setHazard_year(hazard_list.get(j).getHazard_year());
					zybwh.setIsActive("Y");
					zybwh.setOccuphyexaclassid(hazard_list.get(j).getOccuphyexaclassid());
					zybwh.setRemark("");
					this.defaultapp = (SystemType) session.get("defaultapp");
					this.zybCustomerInfoService.saveZybExamOccuhazardfactors(zybwh, user, defaultapp.getComid(), user.getCenter_num());
				}
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("zyb32");// 子功能id 必须填写
				sl.setExplain(this.model.getExam_num() + " " + this.model.getZywhyslb() + " " + this.model.getZytjlb());// 操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-保存成功";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zywhyslbdeldo   
	     * @Description: zyb34删除职业危害因素类型  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zywhyslbdeldo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if ((StringUtil.isEmpty(this.model.getExam_num())) && (StringUtil.isEmpty(this.model.getId_num()))
				&& (StringUtil.isEmpty(this.model.getArch_num()))) {
			this.message = "error-无效体检信息";
		} else if (StringUtil.isEmpty(this.model.getIds())) {
			this.message = "error-职业危害因素无效";
		} else {
			
			ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if (eu.getFreeze() == 1) {
				this.message = "error-体检信息已冻结，操作不能继续";
			} else {
				
				//删除体检人和职业危害因素关系
				ZybExamOccuhazardfactors zybwh = new ZybExamOccuhazardfactors();
				zybwh = this.zybCustomerInfoService.longZybExamOccuhazardfactors(this.model.getIds());
				this.zybCustomerInfoService.delZybExamOccuhazardfactors(zybwh);
				this.message = "ok-删除成功!";
				//删除套餐
				ExamSetDTO se = this.zybCustomerInfoService.getOccuhazardfactorsExamSet(zybwh.getHazard_code(), zybwh.getOccuphyexaclassid(),user.getCenter_num());
				if(se != null){
					this.message = this.djtCustomerInfoService.djtdelExamInfoGChangSet(model.getExam_num(),se.getSet_num()+"",user.getUserid(),user.getCenter_num());
				}
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("3");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("zyb34");// 子功能id 必须填写
				sl.setExplain(this.model.getExam_num() + " " + zybwh.getHazard_code() + " " + zybwh.getOccuphyexaclassid());// 操作说明
				syslogService.saveSysLog(sl);
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	 * @Title: impuserAllToExaminfodo @Description: 执行批量导入
	 * 251 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @param: @throws Exception @return: String @throws
	 */

	public String impuserAllToExaminfodo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			this.customerInfoService.checkArch_num(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid());
			this.customerInfoService.checkIn_num(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid());
			if((this.model.getCompanybatch_id()==null)||(this.model.getCompanybatch_id().trim().length()<=0)||(this.model.getCompanybatch_id().trim().split("-").length!=2))
			{
				this.message = "error-无效单位或者体检任务，执行批量导入失败。";
			}else{
				long rescomid=Long.valueOf(this.model.getCompanybatch_id().trim().split("-")[0]);
			    this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), rescomid, user.getUserid(),user.getCenter_id());
			    
			    String apptype = (String) session.get("defaultapp");
			    customerInfoService.getExamInfoForFlagList(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid(),apptype,user.getCenter_num());
			    //UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("251");//子功能id 必须填写
				sl.setExplain("批量导入 "+this.model.getCompany_id()+" "+this.model.getBatch_id());//操作说明
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
	     * @Title: zybimpuserToExaminfodo   
	     * @Description: zyb16 职业病部分进入正式库 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybimpuserToExaminfodo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			this.customerInfoService.checkArch_num(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid());
			this.customerInfoService.checkIn_num(this.model.getCompany_id(), this.model.getBatch_id(),
					user.getUserid());
			if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
				this.model.setIds(this.model.getIds().trim().substring(1, this.model.getIds().trim().length()));
				if((this.model.getCompanybatch_id()==null)||(this.model.getCompanybatch_id().trim().length()<=0)||(this.model.getCompanybatch_id().trim().split("-").length!=2))
				{
					this.message = "error-无效单位或者体检任务，执行批量导入失败。";
				}else{
					long rescomid=Long.valueOf(this.model.getCompanybatch_id().trim().split("-")[0]);
				    this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), rescomid, user.getUserid(),user.getCenter_id());
				    
				    this.defaultapp = (SystemType) session.get("defaultapp");	
				    customerInfoService.getExamInfoForFlagList(this.model.getCompany_id(), this.model.getBatch_id(),
						user.getUserid(), this.model.getIds(),this.defaultapp.getComid(),user.getCenter_num());
				    //UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb16");//子功能id 必须填写
					sl.setExplain("部分导入 "+this.model.getCompany_id()+" "+this.model.getBatch_id()+" "+this.model.getIds());//操作说明
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
	     * @Title: zybocchisdeldo   
	     * @Description: 职业病删除职业史 zyb26  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybocchisdeldo()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		/*if (this.model.getBatch_id() <= 0){
			this.message = "error-请选择体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {*/
			
			if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
				this.model.setIds(this.model.getIds().trim().substring(1, this.model.getIds().trim().length()));
				String ids[]= this.model.getIds().split(",");
					for(int i=0;i<ids.length;i++)
					{
						String hisid = ids[i];
						ZybOccuHis zybhis= new ZybOccuHis();
						zybhis=this.zybCustomerInfoService.longZybOccuHis(hisid);
						this.zybCustomerInfoService.delZybOccuHis(zybhis);
					}
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb26");//子功能id 必须填写
					sl.setExplain(this.model.getIds());//操作说明
					syslogService.saveSysLog(sl);
				this.message = "ok-操作完成。";
			} else {
				this.message = "error-无效记录。";
			}
		//}
		//}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	
	/**
	 * 
	     * @Title: addcustomer   
	     * @Description: zyb18  执行新增操作  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybaddcustomer() throws WebException, SQLException, Exception {
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
		if (this.model.getBatch_id() <= 0) {
			this.message = "体检任务无效";
			return "commerror";
		} else {
			Batch bt = new Batch();
			bt = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(bt.getBatch_name());
			this.model.setBatch_num(bt.getBatch_num());
			if (bt.getId() > 0) {
				if (this.model.getCompany_id() <= 0) {
					CompanyInfo cif = new CompanyInfo();
					cif = companyService.findComByID(bt.getCompany_id());
					this.model.setComname(cif.getCom_Name());
					this.model.setCompany_id(bt.getCompany_id());
				} else {
					CompanyInfo cif = new CompanyInfo();
					cif = companyService.findComByID(this.model.getCompany_id());
					this.model.setComname(cif.getCom_Name());
				}
				if (this.model.getId() > 0) {
					ExamInfo ei = new ExamInfo();
					ei = this.customerInfoService.getExamInfoForExamNum(this.model.getExam_num());
					this.model.setExam_num(ei.getExam_num());
				}
			}
			// 获取身份证厂家代码
			String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
			this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
			this.sfz_div_code = sfz_div_code;
			return SUCCESS;
		}
	}
	
	
	/**
	 * 
	     * @Title: addcustomerdo   
	     * @Description: 职业病单独体检人员信息录入 zyb22
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybaddcustomerdo()  throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch bt = new Batch();
			bt = this.batchService.getBatchByID(this.model.getBatch_id());
			
			if ("1".equals(bt.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			if(bt.getId()>0){			
			this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), bt.getCompany_id(), user.getUserid(),user.getCenter_id());
			ZybExamInfoUserDTO imp =new ZybExamInfoUserDTO();
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
            imp.setOccusector(this.model.getOccusector());
            imp.setOccusectorid(this.model.getOccusectorid());
            imp.setOccutypeofwork(this.model.getOccutypeofwork());
            imp.setOccutypeofworkid(this.model.getOccutypeofworkid());
            imp.setDamage(this.model.getDamage());
            imp.setEmployeeage(this.model.getEmployeeage());
            imp.setJoinDatetime(this.model.getJoinDatetime());
            imp.setZyb_set_source(this.model.getZyb_set_source());
            imp.setVisit_no(this.model.getVisit_no());
            imp.setIdtype(this.model.getIdtype());
			this.message = this.zybCustomerInfoService.getExamInfoOne(this.model.getCompany_id(), this.model.getBatch_id(), user.getUserid(), user.getCenter_num(),imp);
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb22");//子功能id 必须填写
			sl.setExplain("新增体检人员 "+imp.getUser_name());//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.message = "error-体检任务无效！";
		}
		}
		}
		String[] msg = this.message.split("-");
		if("ok".equals(msg[0])) {
			ExamInfo examinfo = this.examInfoService.findExamInfo(msg[1]);
			this.message = "ok-"+msg[1]+"-"+examinfo.getExam_num();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}		
	
	/**
	 * 
	 * @Title: getDjtExamOneShow @Description: 职业病查询体检编号对应信息zyb23
	 *         292 @param: @return @param: @throws WebException @param: @throws
	 *         SQLException @param: @throws Exception @return: String @throws
	 */
	public String getzybDjtExamOneShow() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		ZybExamInfoUserDTO eu = new ZybExamInfoUserDTO();
		if (!StringUtils.isEmpty(model.getExam_num())) {
			eu = this.zybCustomerInfoService.getExamInfoUserForExamId(model.getExam_num(),user.getCenter_num());
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
		sl.setXtgnid2("zyb23");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonResult(eu);
		return NONE;
	}
	
    /**
     * 
         * @Title: zybocchisadd   
         * @Description: zyb24 职业病维护职业病史页面
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String zybocchisadd() throws WebException{
		return SUCCESS;
	}
	public String zybocchisaupdate() throws WebException{
		ZybOccuHisDTO z = this.zybCustomerInfoService.getZybOccuHisFinID(model);
		model.setZybOccuHisDTO(z);
		return SUCCESS;
	}
	public String zybFsocchisupdate() throws WebException{
		ZybOccuHisDTO z = this.zybCustomerInfoService.getZybOccuHisFinID(model);
		model.setZybOccuHisDTO(z);
		return SUCCESS;
	}
    /**
     * 
         * @Title: zybFsocchisadd   
         * @Description: zyb392 放射史维护页面
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String zybFsocchisadd() throws WebException{
		return SUCCESS;
	}
	
	 /**
     * 
         * @Title: zybocchisadd   
         * @Description: zyb28 增加体检职业病危害因素与套餐
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
	public String zyboccwhystcadd() throws WebException,Exception{
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: zybocchisadddo   
	     * @Description: 职业病职业病史保存操作 zyb25 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybocchisadddo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(model.getZyb_id()!=null && !"".equals(model.getZyb_id())){
			//修改操作	
			this.zybCustomerInfoService.updateZybOccuHis(model,user);
			this.message="ok-记录保存成功";
			
		} else {
			ZybOccuHis zybhis=new ZybOccuHis();
			zybhis.setArch_num(this.model.getArch_num());
			zybhis.setCompany(this.model.getComname());
			zybhis.setConcentrations(this.model.getConcentrations());
			zybhis.setStartdate(this.model.getTime1());
			zybhis.setEnddate(this.model.getTime2());
			zybhis.setCreate_date(DateTimeUtil.getDateTime());
			zybhis.setCreater(user.getUserid());
			zybhis.setExam_num(this.model.getExam_num());
			zybhis.setHarmname(this.model.getHarmname());
			zybhis.setId_num(this.model.getId_num());
			zybhis.setIsradiation(this.model.getIsradiation());
			zybhis.setMeasure(this.model.getMeasure());
			zybhis.setWorkshop(this.model.get_level());
			zybhis.setWorktype(this.model.getOccutypeofwork());
			zybhis.setRadiation(this.model.getRadiation());
			zybhis.setMan_haur(this.model.getMan_haur());
			zybhis.setCumulative_exposure(this.model.getCumulative_exposure());
			zybhis.setHistory_excessive(this.model.getHistory_excessive());
			zybhis.setRemark(this.model.getRemark());
			this.zybCustomerInfoService.saveZybOccuHis(zybhis);
			this.message="ok-记录保存成功";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb25");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
			
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getzywhlbshow   
	     * @Description: 获取职业危害类别列表zyb29   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getzywhlbshow() throws WebException, Exception {		
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.zybCustomerInfoService.getzywhlblist();
		this.outJsonResult(tr);
		return NONE;
		
	}
	
	/**
	 * 
	     * @Title: getzytjlbshow   
	     * @Description: zyb33   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getzytjlbshow() throws WebException, Exception {		
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.zybCustomerInfoService.getzybphysicalexaminationclasslist();
		this.outJsonResult(tr);
		return NONE;
		
	}
	/**
	 * 
	     * @Title: getzywhyslbshow   
	     * @Description: 获取职业危害因素类别zyb30
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getzywhyslbshow() throws WebException, Exception {		
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		this.model.setSex(URLDecoder.decode(this.model.getSex(), "UTF-8"));
		tr = this.zybCustomerInfoService.getzywhyslblist(this.model.getZywhlb());
		this.outJsonResult(tr);
		return NONE;
		
	}
	
	 /**
     * 
         * @Title: zybAllcustchangitemlist   
         * @Description: 职业病获取体检人员体检项目 zyb35 
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
    public String zybAllcustchangitemlist()throws WebException {
    	UserDTO user = (UserDTO) session.get("username");
       List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
       list= this.zybCustomerInfoService.getZybExaminfoChargingItemAllforExamId(this.model.getExam_num(), user.getCenter_num());
	   this.outJsonResult(list);
	   return NONE;	
    }
    
	
    /**
	 * 
	 * @Title: exam_tclistshow @Description:人员套餐信息
	 *         296 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String zybexamtclistshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.zybCustomerInfoService.getZybExamSetAllList(this.model.getExam_num(), 1000, 1,user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zybcustomeritemaddshow   
	     * @Description: 职业病增加体检项目 zyb37 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybcustomeritemaddshow() throws WebException, Exception {
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
		sl.setXtgnid2("zyb37");//子功能id 必须填写
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
	public String zybexam_tclistshow() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO batchlist = new PageReturnDTO();
		batchlist = this.zybCustomerInfoService.getZybExamSetList(this.model.getExam_num(),this.model.getApp_type(), 1000, 1,user.getCenter_num());
		this.outJsonResult(batchlist);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: zybcustGSaveItemSet   
	     * @Description: 职业病团体保存体检项目 zyb41   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybcustGSaveItemSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		
		ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		boolean teamaccflag = false;
		TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
		ta = this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
		if ((ta != null) && (ta.getId() > 0)) {
			teamaccflag = true;
		}

		String accountflagAndOverMsg = "ok";
		accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(eu.getBatch_id());
		if (!"ok".equals(accountflagAndOverMsg)) {
			this.message = accountflagAndOverMsg;
		} else if (teamaccflag) {
			this.message = "error-体检信息已经结算，操作不能继续";
		} else {
		
		ExaminfoSetDTO bc = new ExaminfoSetDTO();
		bc.setAmount(model.getAmount());
		bc.setExaminfo_id(model.getExam_id());
		bc.setDiscount(model.getDiscount());
		bc.setExam_num(model.getExam_num());
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
            	bd = new BigDecimal(gci.getItem_amount()*gci.getItemnum() * gci.getDiscount() / 10);
    			amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }else{
            	amount=tamt + pamt;
            }
			if (java.lang.Math.abs(amount-(tamt + pamt))>10) {
				fxitemflag = false;
				break;
			}
		}

		if (StringUtil.isEmpty(model.getExam_num())) {
			this.message = "error-无效人员，不能加项！";
            this.outJsonStrResult(this.message);
            return NONE;
		}
		if(listset!=null && listset.size()>0){
            String m = this.zybCustomerInfoService.cheCkOccuphyexaclass(model.getExam_num(),listset);
		    if(!StringUtils.isEmpty(m)){
                this.message = "error-"+m;
                this.outJsonStrResult(this.message);
                return NONE;
            }
        }

			String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
			this.message = this.zybCustomerInfoService.zybaddExamInfoChangItem(bc, listitem, listset, "T",IS_CHARGINGWAY_ZERO,
					user.getUserid(), user.getName(), user.getCenter_num());
			
			String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num())
					.getConfig_value().trim();
			if ("Y".equals(is_queue_check)) {
				QueueCustomerBean bean = new QueueCustomerBean();
				bean.setExam_num(model.getExam_num());
				QueueCustomSendMessage message = new QueueCustomSendMessage();
				QueueResBody rb = message.Send(bean);
				if ("AA".equals(rb.getRescode())) {

				} else {
					this.message = rb.getRestext();
				}
				if(!"Y".equals(eu.getStatus())){
					QueueAddBean bn = new QueueAddBean();
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
			sl.setXtgnid2("zyb41");//子功能id 必须填写
			sl.setExplain(bc.getExaminfo_id()+" "+chargids);//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
     * 
         * @Title: zybcustchangitemlist   
         * @Description: 获取体检人员的体检项目  
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
    public String zybcustchangitemlist()throws WebException {
    	UserDTO user = (UserDTO) session.get("username");
       List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
       list= this.customerInfoService.getExaminfoChargingItemforExamId(this.model.getExam_num(),this.model.getApp_type(), user.getCenter_num());
	   this.outJsonResult(list);
	   return NONE;	
    }
	
    /**
     * 
         * @Title: djtcustomeritemdelshow   
         * @Description: 职业病减项 zyb45
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
	public String zybcustomeritemdelshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能进行减项！";
		} else if ((this.model.getIds() == null) || this.model.getIds().trim().length() <= 0) {
			this.message = "error-无效体检项目，不能进行减项！";
		} else {
			ZybExamInfoUserDTO eu = this.zybCustomerInfoService.getExamInfoUserForExamId2(this.model.getExam_num(),user.getCenter_num());
			boolean taoverflag=false;
			boolean teamaccflag=false;
			if("T".equals(eu.getExam_type())){
				Batch taover = new Batch();
				taover = this.batchService.getBatchByID(eu.getBatch_id());
				if ("1".equals(taover.getOverflag())) {
					taoverflag=true;
				}else{
					TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
					ta=this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
					if((ta!=null)&&(ta.getId()>0)){
						teamaccflag=true;
					}
				}				
			}			
			if (taoverflag) {
				this.message = "error-批次任务已经封帐，操作不能继续";
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
				sl.setXtgnid2("zyb45");//子功能id 必须填写
				sl.setExplain(model.getExam_id()+"  "+this.model.getIds());//操作说明
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
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zybcustomersetdelshow   
	     * @Description: 职业病删除套餐 zyb46  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybcustomersetdelshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (StringUtil.isEmpty(this.model.getExam_num())) {
			this.message = "error-无效人员，不能删除套餐！";
		} else if ((this.model.getIds() == null) || this.model.getIds().trim().length() <= 0) {
			this.message = "error-无效体检项目，不能删除套餐！";
		} else {
			// this.message = this.batchService.saveGroup(bc, listitem,
			// listset);
			this.message = this.djtCustomerInfoService.djtdelExamInfoGChangSet(model.getExam_num(), this.model.getIds(),
					user.getUserid(),user.getCenter_num());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb46");//子功能id 必须填写
			sl.setExplain(model.getExam_id()+"  "+this.model.getIds());//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: zybTGcustomeritemaddshow   
	     * @Description: 职业病团体个人加项zyb47 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybTGcustomeritemaddshow() throws WebException, Exception {
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
		sl.setXtgnid2("zyb47");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	     * @Title: zybTTcustomeritemaddshow   
	     * @Description: 职业病团体个人加项 zyb48
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybTTcustomeritemaddshow() throws WebException, Exception {
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
		sl.setXtgnid2("zyb48");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: zybTGcustSaveItemSet   
	     * @Description: 职业病团体个人加项保存  zyb49   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybTGcustSaveItemSet() throws WebException {
		UserDTO user = (UserDTO) session.get("username");

			ExaminfoSetDTO bc = new ExaminfoSetDTO();
			bc.setAmount(model.getAmount());
			bc.setExaminfo_id(model.getExam_id());
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

			if (StringUtils.isEmpty(model.getExam_num())) {
				this.message = "error-无效人员，不能加项！";
			}

				// this.message = this.batchService.saveGroup(bc, listitem,
				// listset);
				String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
				this.message = this.djtCustomerInfoService.djtaddExamInfoGChangItem(bc, listitem, listset, "T",IS_CHARGINGWAY_ZERO,
						user.getUserid(), user.getName(), user.getCenter_num());
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());		
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("418");//子功能id 必须填写
				sl.setExplain(bc.getExaminfo_id()+" "+chargitems);//操作说明
				syslogService.saveSysLog(sl);
			
			try {/*
				if ("Y".equals(this.customerInfoService.getCenterconfigByKey("IS_TGCUSTOM_GH", user.getCenter_num()).getConfig_value())) {
					String ress[] = this.message.split("-");
					if ("ok".equals(ress[0])) {
						if (hisflag) {
							long exam_id = model.getExam_id();

							ExamInfoDTO ei = this.customerInfoService.getExamInfoForexamId(exam_id);
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
								st.setVISIT_DEPT(companyService.getDatadis("SQKS").get(0).getRemark());
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
									wcd = webserviceConfigurationService.getWebServiceConfig("CUST_APPLICATION");
									String web_url = wcd.getConfig_url().trim();
									String web_meth = wcd.getConfig_method().trim();
									fr = csm.customSend(web_url, web_meth, true);
									if ("AA".equals(fr.getResultHeader().getTypeCode())) {
										ExamInfo eiold = new ExamInfo();
										eiold = this.customerInfoService.getExamInfoForId(exam_id);
										eiold.setVisit_date(fr.getControlActProcess().getLIST().get(0).getVISIT_DATE());
										eiold.setVisit_no(fr.getControlActProcess().getLIST().get(0).getVISIT_NO());
										eiold.setClinic_no(fr.getControlActProcess().getLIST().get(0).getCLINIC_NO());
										eiold.setPatient_id(fr.getControlActProcess().getLIST().get(0).getPATIENT_ID());
										this.customerInfoService.updateExamInfo(eiold);
										SysLog sl =  new SysLog();
										sl.setCenter_num(user.getCenter_num());		
										sl.setUserid(user.getUserid()+"");
										sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
										sl.setXtgnid("");//可不填写
										sl.setXtgnid2("zyb49");//子功能id 必须填写
										sl.setExplain(eiold.getExam_num()+" "+eiold.getPatient_id()+" "+eiold.getVisit_no()+" "+eiold.getVisit_date()+" "+eiold.getClinic_no());//操作说明
										syslogService.saveSysLog(sl);
									}
								}
							}
						}
					}

				}
			*/} catch (Exception ex) {
				ex.printStackTrace();
			}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zybTTcustSaveItemSet   
	     * @Description:职业病团体团体加项保存  zyb50
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybTTcustSaveItemSet() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		
		ExamInfoUserDTO eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
		boolean teamaccflag = false;
		TeamAccountExamListDTO ta = new TeamAccountExamListDTO();
		ta = this.teamAccountService.getTeamAccountExamListforExamNum(eu.getExam_num());
		if ((ta != null) && (ta.getId() > 0)) {
			teamaccflag = true;
		}
		String accountflagAndOverMsg = this.batchService.checkAccountflagAndOverflag(eu.getBatch_id());
		if (!"ok".equals(accountflagAndOverMsg)) {
			this.message = accountflagAndOverMsg;
		} else if (teamaccflag) {
			this.message = "error-体检信息已经结算，操作不能继续";
		} else {
		
		ExaminfoSetDTO bc = new ExaminfoSetDTO();
		bc.setAmount(model.getAmount());
		bc.setExaminfo_id(model.getExam_id());
		bc.setDiscount(model.getDiscount());
		bc.setExam_num(model.getExam_num());

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
            	bd = new BigDecimal(gci.getItem_amount() * gci.getItemnum() * gci.getDiscount() / 10);
    			amount = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            }else{
            	amount=tamt + pamt;
            }
			if (java.lang.Math.abs(amount-(tamt + pamt))>10) {
				fxitemflag = false;
				break;
			}
		}

		if (StringUtils.isEmpty(model.getExam_num())) {
			this.message = "error-无效人员，不能加项！";
		}

			String IS_CHARGINGWAY_ZERO = this.customerInfoService.getCenterconfigByKey("IS_CHARGINGWAY_ZERO", user.getCenter_num()).getConfig_value().trim();
			this.message = this.djtCustomerInfoService.djtaddExamInfoGChangItem(bc, listitem, listset, "T",IS_CHARGINGWAY_ZERO,
					user.getUserid(), user.getName(), user.getCenter_num());
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());		
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb50");//子功能id 必须填写
			sl.setExplain(bc.getExaminfo_id()+" "+chargids);//操作说明
			syslogService.saveSysLog(sl);

		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: zybcustomerAllitemaddshow   
	     * @Description: 职业病团体批量加项  zyb39   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybcustomerAllitemaddshow()throws WebException {
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
    	if((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)){
    		this.message="未选择人员，操作不能继续。";
    		return "commerror";
    	}else{
    		this.message = this.zybCustomerInfoService.zybcheckExamInfotime(this.model.getIds());
    		if((this.message==null)||(this.message.indexOf("ok")<0)){    			
    			return "commerror";
    		}else{
    			this.model.setAge(this.model.getIds().trim().split(",").length);
    			String sex = this.customerInfoService.getIDSExamInfoSex(this.model.getIds());
    			this.model.setSex(sex);
    			return SUCCESS;
    		}
    	}
    }
	
	/**
	 * 
	     * @Title: zybcustomeritemadddo   
	     * @Description: 职业病执行团体加项zyb51   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	 public String zybcustomeritemadddo()throws WebException {
	    	UserDTO user = (UserDTO) session.get("username");
	    	if(StringUtils.isEmpty(model.getExam_nums())){
	    		this.message="error-无效体检用户";
	    	}else{
	    		Batch taover = new Batch();
	    		taover = this.batchService.getBatchByID(this.model.getBatch_id());
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
	    		
	    		String items="";
	    		for(GroupChargingItemDTO gci:listitem){
	    			items=items+""+gci.getItem_code()+",";
	    		}
	    		
	    		String centernum = user.getCenter_num();
	    		
	    		String[] exam_nums =this.model.getExam_nums().split(",");
	    		
	    		for(int i=0;i<exam_nums.length;i++)
	    		{
	    			//long examid = Long.valueOf(idss[i]);	
	    			String itemcodes = this.customerInfoService.getItemCodesforExamId(exam_nums[i], user.getCenter_num()); 	
	    			if(StringUtil.isEmpty(this.model.getExam_indicator())) {
	    				this.model.setExam_indicator("T");
	    			}
	    		    this.zybCustomerInfoService.addIDSExamInfoChangItem(exam_nums[i],listitem,itemcodes,this.model.getExam_indicator(),user.getUserid(),user.getName(),centernum);

					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb51");//子功能id 必须填写
					sl.setExplain("体检人员添加项目 "+exam_nums[i]+" "+items);//操作说明
					syslogService.saveSysLog(sl);
	    		}
	    		
	    		this.message="ok-操作结束！";
	    		}
	    	}
	    	this.outJsonStrResult(this.message);
	    	return NONE;
	    }


	 /**
	     * 
	         * @Title: zybcustomerallitemdelshow zyb52 
	         * @Description: TODO(这里用一句话描述这个方法的作用)   
	         * @param: @return
	         * @param: @throws WebException      
	         * @return: String      
	         * @throws
	     */
	    public String zybcustomerallitemdelshow()throws WebException {
	    	if((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)){
	    		this.message="";
	    		return "commerror";
	    	}else{
	    		message = this.zybCustomerInfoService.zybcheckExamInfotime(this.model.getIds());
	    		if((message==null)||(message.indexOf("ok")<0)){
	    			return "commerror";
	    		}else{
	    			this.model.setAge(this.model.getIds().trim().split(",").length);
	    			String sex = this.customerInfoService.getIDSExamInfoSex(this.model.getIds());
	    			this.model.setSex(sex);
	    			return SUCCESS;
	    		}
	    	}
	    }
	
	    /**
	     * 
	         * @Title: zybdeletechargitemOnedo   
	         * @Description: 职业病执行减项操作 zyb53   
	         * @param: @return
	         * @param: @throws WebException      
	         * @return: String      
	         * @throws
	     */
	    public String zybdeletechargitemOnedo()throws WebException {
	    	UserDTO user = (UserDTO) session.get("username");
	    	Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
	    	if((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)){
	    		this.message="error-无效体检用户";
	    	}else{
	    		if(this.model.getId()<=0)
	    		{
	    			this.message="error-无效收费项目";
	    		}else{
	    		String[] idss=this.model.getIds().split(",");
	    		
	    		for(int i=0;i<idss.length;i++)
	    		{
	    			String exam_num = idss[i];//人员id
	    			long chargingId=this.model.getId();
	    		    this.zybCustomerInfoService.delZybIDSExamInfoChangItem(exam_num,chargingId,user.getUserid(), user.getCenter_num());
	    		    //UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb53");//子功能id 必须填写
					sl.setExplain("体检人员减项 "+exam_num+" "+chargingId);//操作说明
					syslogService.saveSysLog(sl);
	    		}
	    		this.message="ok-操作结束！";
	    		}
	    	}
	    	}
	    	this.outJsonStrResult(this.message);
	    	return NONE;
	    }
	    
   /**
    * 
        * @Title: getoneExamExtinfo   
        * @Description: 职业病获取单人的体检扩展信息 zyb54   
        * @param: @return
        * @param: @throws WebException      
        * @return: String      
        * @throws
    */
   public String getoneExamExtinfo()throws WebException {
	   ExamInfoExtDTO ee= new ExamInfoExtDTO();
	   ee=this.zybCustomerInfoService.getExamInfoExtForExamNum(this.model.getExam_num());
	   this.outJsonResult(ee);
	   return NONE;
   }
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 
	     * @Title: djdprintdo   
	     * @Description: 翰思打印导检单 266 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String djdprintdo() throws WebException, SQLException {
		return SUCCESS;
	}
	
	/**
	 * 职业危害因素与套餐列表zyb27
	     * @Title: examoccuhazardfactorslist   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examoccuhazardfactorslist()throws WebException{
		PageReturnDTO contractlist = new PageReturnDTO();
		contractlist = this.zybCustomerInfoService.getexamOcchazardfactors(this.model.getExam_num(), this.rows, this.getPage());
		this.outJsonResult(contractlist);
		return NONE;
	}

	/**
	 * 
	 * @Title: getCompanForDept @Description:
	 * 255 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String getCompanForDept() throws WebException, SQLException {
		List<CompanyDepartment> getbatchList = new ArrayList<CompanyDepartment>();
		getbatchList = this.companyService.getCompanyDepartmentByComid(this.model.getCompany_id());
		List<CompanyDepartment> getbatchList1 = new ArrayList<CompanyDepartment>();
		CompanyDepartment dcd = new CompanyDepartment();
		dcd.setId(0);
		dcd.setDep_Name("可选择部门");
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
	 * 256批次得到分组带空行 @param: @return @param: @throws WebException @param: @throws
	 * SQLException @return: String @throws
	 */
	public String getBatchForGroup() throws WebException, SQLException {
		List<GroupInfoDTO> getbatchList = new ArrayList<GroupInfoDTO>();
		getbatchList = this.batchService.getGroupListForBatchId(this.model.getBatch_id());
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
	     * @Title: doAllUserGroupuser   
	     * @Description: 自动分组 258 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String doAllUserGroupuser() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
            int flags=0;
            flags=this.customerInfoService.doAllUserGroup(this.model.getBatch_id(), user.getUserid()+"", centernum);
            if(flags==1){
            	this.message = "ok-自动分组执行完成";	
            	//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("258");//子功能id 必须填写
				sl.setExplain("自动分组 "+this.model.getBatch_id());//操作说明
				syslogService.saveSysLog(sl);
            }else{
            	this.message = "error-自动分组执行返回失败";	
            }	
		}
		}
        this.outJsonStrResult(this.message);
        return 	NONE;
	}
	
	/**
	 * 
	     * @Title: doUserGroupuser   
	     * @Description: 259 强制分组   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String doUserGroupuser() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		String centernum = user.getCenter_num();
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else if ((this.model.getIds() == null) || (this.model.getIds().trim()).length() <= 1|| (this.model.getIds().trim()).split(",").length<=0) {
			this.message = "error-记录编号无效";
		}else if(this.model.getGroup_id()<=0){
			this.message = "error-分组编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			String ids[] = this.model.getIds().trim().split(",");
			for (int i = 0; i < ids.length; i++) {
				int statcont = this.djtCustomerInfoService.getIsGroupExamInfoSetForId(ids[i], user.getCenter_num());
				if (statcont <= 0) {
					this.customerInfoService.dotUserGroup(this.model.getGroup_id(), user.getUserid() + "", centernum,
							ids[i]);
					//UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("258");//子功能id 必须填写
					sl.setExplain("强制分组 "+this.model.getGroup_id()+" "+ids[i]);//操作说明
					syslogService.saveSysLog(sl);
				}
			}
            this.message = "ok-强制分组完成";	    
		}
		}
        this.outJsonStrResult(this.message);
        return 	NONE;
	}
	
	/**
	 * 
	     * @Title: deluserrow   
	     * @Description: 删除人员及其分组、项目   260
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String deluserrow() throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) ||(this.model.getBatch_id() <= 0)) {
			this.message = "error-记录分组编号无效";
		} else if ((this.model.getIds() == null) || (this.model.getIds().trim()).length() <= 1) {
			this.message = "error-记录编号无效";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
			this.message=this.customerInfoService.delExamInfoForIds(ids,user.getUserid());  
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("260");//子功能id 必须填写
			sl.setExplain("删除体检人员 "+ids);//操作说明
			syslogService.saveSysLog(sl);
			}
		}
        this.outJsonStrResult(this.message);
        return 	NONE;
	}
	
	/**
	 * 
	     * @Title: editcustomer   
	     * @Description: 修改提交人员信息 410 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String editcustomer() throws WebException, SQLException, Exception {
			UserDTO user = (UserDTO) session.get("username");
		if (this.model.getCompany_id() <= 0) {
			this.message ="单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		if (this.model.getBatch_id() <= 0) {
			this.message ="体检任务无效";
			return "commerror";
		} else {
			Batch bt = new Batch();
			bt = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(bt.getBatch_name());
			this.model.setBatch_num(bt.getBatch_num());
		if(this.model.getId()>0){
			ExamInfoUserDTO eu = new ExamInfoUserDTO();
			eu = this.customerInfoService.getExamInfoUserForExamId(this.model.getExam_num(),user);
			if((eu==null)||(eu.getId()<=0)){
				this.message = "error-记录编号无效,无法编辑！";
				return "commerror";
			}else{
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
				this.model.setVisit_no(eu.getVisit_no());
				this.model.setIdtype(eu.getIdtype());
				// 获取身份证厂家代码
				String sfz_div_code = this.customerInfoService.getCenterconfigByKey("SFZ_CARD", user.getCenter_num()).getConfig_value().trim();
				this.sfz_div_ocx = this.customerInfoService.getDiversforByCode(sfz_div_code).getCom_ocx_name();
				this.sfz_div_code = sfz_div_code;
				return SUCCESS;
			}
		}else{
			return SUCCESS;
		}
		}
		}
		
		}
	
	/**
	 * 
	     * @Title: editcustomerdo   
	     * @Description: 修改提交人员信息 411
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String editcustomerdo()  throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			ExamInfoUserDTO imp =new ExamInfoUserDTO();
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
            imp.setIdtype(this.model.getIdtype());
			this.message = this.customerInfoService.getExamInfoOne(this.model.getCompany_id(), this.model.getBatch_id(), user.getUserid(), user.getCenter_num(),imp);
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("411");//子功能id 必须填写
			sl.setExplain("修改体检人员 "+imp.getUser_name());//操作说明
			syslogService.saveSysLog(sl);
		}
	}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: addcustomerdo   
	     * @Description: 单独体检人员信息录入 262
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String addcustomerdo()  throws WebException, SQLException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
			this.message = "error-请选择单位和体检任务";
		} else {
			Batch bt = new Batch();
			bt = this.batchService.getBatchByID(this.model.getBatch_id());
			
			if ("1".equals(bt.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			if(bt.getId()>0){			
			this.companyService.getcompanyParsentNodeById(this.model.getCompany_id(), bt.getCompany_id(), user.getUserid(),user.getCenter_id());
			ExamInfoUserDTO imp =new ExamInfoUserDTO();
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
            imp.setIdtype(this.model.getIdtype());
			this.message = this.customerInfoService.getExamInfoOne(this.model.getCompany_id(), this.model.getBatch_id(), user.getUserid(), user.getCenter_num(),imp);
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("262");//子功能id 必须填写
			sl.setExplain("新增体检人员 "+imp.getUser_name());//操作说明
			syslogService.saveSysLog(sl);
		}else{
			this.message = "error-体检任务无效！";
		}
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
		
	
	/**
	 * 
	     * @Title: customeroneshow   
	     * @Description: 单独体检人员信息录入
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String customeroneshow()  throws WebException, SQLException, Exception {
			UserDTO user = (UserDTO) session.get("username");
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());			
		
			Batch bt = new Batch();
			bt = this.batchService.getBatchByID(this.model.getBatch_id());
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
				if((eu.getBirthday()!=null)&&(eu.getBirthday().trim().length()>10)){					
					eu.setBirthday(eu.getBirthday().trim().substring(0, 10));
					if("1900-01-01".equals(eu.getBirthday()))
					{
						this.model.setBirthday("");
					}else{
						this.model.setBirthday(eu.getBirthday());
					}
				}else{
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
				List<ExaminfoSetDTO> eslist =this.customerInfoService.getExamSetForExamNum(this.model.getExam_num(),user.getCenter_num());
				this.model.setHansidjdflag("");
				if((eslist!=null)&&(eslist.size()>0)){
					for(int i=0;i<eslist.size();i++)
					{
						ExaminfoSetDTO es =eslist.get(i);
					   this.model.setHansidjdflag(this.model.getHansidjdflag()+","+es.getSet_name());
					}
				}
				
				try{//获取部门
					if(eu.get_level()!=null){
						long leid= Long.valueOf(eu.get_level().trim());
					    CompanyDepartment cd =this.companyService.getCompanyDepartmentById(leid);
					    this.model.set_level(cd.getDep_Name());
					}
				}catch(Exception ex){
					this.model.set_level("");
				}
				
				try{//获取民族
					if(eu.getNation()!=null){
						DataDictionary  dd=this.companyService.getDataDictionaryByid(eu.getNation());
					    this.model.setNation(dd.getData_name());
					}
				}catch(Exception ex){
					this.model.setNation("");
				}
				
				try{//体检类型
					if(eu.getCustomer_type()!=null){
						DataDictionary  dd=this.companyService.getDataDictionaryByid(eu.getCustomer_type());
					    this.model.setCustomer_type(dd.getData_name());
					}
				}catch(Exception ex){
					this.model.setCustomer_type("");
				}
				 
				try{//收费类型
					if(eu.getChargingType()!=null){
						DataDictionary  dd=this.companyService.getDataDictionaryByid(eu.getChargingType());
					    this.model.setChargingType(dd.getData_name());
					}
				}catch(Exception ex){
					this.model.setChargingType("");
				}
				
				try{//人员类别
					if(eu.getCustomer_type_id()>0){
						CustomerTypeDTO  dd=this.customerInfoService.getcustomerTypeId(eu.getCustomer_type_id());
					    this.model.setTime1(dd.getType_name());
					}
				}catch(Exception ex){
					this.model.setTime1("");
				}
				
				return SUCCESS;
	}
	
	
	/**
	 * 
	     * @Title: doUserGrouplistshow   
	     * @Description: 强制分组选择分组信息 263  
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
     public String  doUserGrouplistshow() throws WebException, SQLException, Exception {
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
    			cif = this.batchService.getBatchByID(this.model.getBatch_id());
    			this.model.setBatch_name(cif.getBatch_name());
    			this.model.setBatch_num(cif.getBatch_num());
    		}
    		if ((this.model.getIds() != null) &&(this.model.getIds().trim()).length()>1) {
    			String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
    			this.model.setIds(ids);
    		}
    		return SUCCESS;    	 
     }
     
    /**
     * 
         * @Title: doSendApplyChargeItem   
         * @Description:  执行批量申请 264 
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException
         * @param: @throws Exception      
         * @return: String      
         * @throws
     */
    public String doSendApplyChargeItem() throws WebException, SQLException, Exception {
 		UserDTO user = (UserDTO) session.get("username");
 		if ((this.model.getCompany_id() <= 0) || (this.model.getBatch_id() <= 0)) {
 			this.message = "error-记录分组编号无效";
 		} else {
 			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
 			if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
 				String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
            String resstr = this.customerInfoService.sendApplyChargeItem(this.model.getBatch_id(), user.getName(),ids.split(","), user.getCenter_num());
            //UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("264");//子功能id 必须填写
			sl.setExplain("团体批量申请 "+this.model.getBatch_id()+" "+ids);//操作说明
			syslogService.saveSysLog(sl);
            if("ok".equals(resstr)){
            	this.message = "ok-批量申请执行完成";	  
            }else{
            	this.message=resstr;
            }   
 			}else{
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
			taover = this.batchService.getBatchByID(this.model.getBatch_id());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
				String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
	            this.customerInfoService.batchSigndo(ids.split(","),user.getUserid(),user.getCenter_num()); 
	            //UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("265");//子功能id 必须填写
				sl.setExplain("团体批量签到 "+this.model.getBatch_id()+" "+ids);//操作说明
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
         * @Title: getDjDpdf   
         * @Description: 打印导检单-翰思专用  
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException
         * @param: @throws Exception      
         * @return: String      
         * @throws
     */
    public String getDjDpdf() throws WebException, SQLException,Exception {
    	if ((!StringUtil.isEmpty(this.model.getIds())) && (this.model.getIds().trim().length() > 1)) {
    		 List<DjdbeanList> list = new ArrayList<DjdbeanList>();
    		 UserDTO user = (UserDTO) session.get("username");
    		 String hsurl = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_URL", user.getCenter_num()).getConfig_value().trim();
             list=customerInfoService.getPrintDjd(this.model.getIds(), hsurl, user.getUserid());
    		String fileName = "/hsdjdts/djd.jasper";//报表文件名称
    		String mdfile = ServletActionContext.getServletContext().getRealPath(fileName);
    		
    		JRDataSource dataSource = new JRBeanCollectionDataSource(list);              
            JasperReport jasperReport =(JasperReport) JRLoader.loadObjectFromFile(mdfile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);
            
            String url="/djdpdf/"+DateTimeUtil.getDateTimes()+"("+user.getUserid()+").pdf";
            String pdfpath = mdfile.substring(0,mdfile.length()-19)+url;    		
    		JRPdfExporter exporter = new JRPdfExporter();  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,pdfpath);    
            exporter.exportReport();    
            String hsweburl=this.customerInfoService.getCenterconfigByKey("IS_OWN_URL", user.getCenter_num()).getConfig_value().trim();
            hsweburl = hsweburl+url;
			this.message = "ok-"+hsweburl;
		} else {
			this.message = "error-无效记录。";
		}	
    	this.outJsonStrResult(message);
    	return NONE;
	}
    
    /**
     * 
         * @Title: custappointmentshow   
         * @Description: 体检人员预约管理主界面 267   
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String custappointmentshow() throws WebException, SQLException {
    	UserDTO user = (UserDTO) session.get("username");
    	String hansidjdflag = this.customerInfoService.getCenterconfigByKey("IS_HANSIDJD_TYPE", user.getCenter_num()).getConfig_value().trim();
		this.model.setHansidjdflag(hansidjdflag);
		this.model.setTime1(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("DJD_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(
				this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_barcode_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("267");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
    
    /**
     * 
         * @Title: setcusttime   
         * @Description:设置体检时间  
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
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
			cif = this.batchService.getBatchByID(this.model.getBatch_id());
			this.model.setBatch_name(cif.getBatch_name());
			this.model.setBatch_num(cif.getBatch_num());
		}
		if ((this.model.getIds() != null) &&(this.model.getIds().trim()).length()>1) {
			String ids = this.model.getIds().trim().substring(1, this.model.getIds().trim().length());
			this.model.setIds(ids);
		}
		this.model.setTime1(this.customerInfoService.getCenterconfigByKey("IS_PLAN_STARTTIME", user.getCenter_num()).getConfig_value().trim());
		this.model.setTime2(this.customerInfoService.getCenterconfigByKey("IS_PLAN_ENDTIME", user.getCenter_num()).getConfig_value().trim());
		return SUCCESS;
	}
    
    /**
     * 
         * @Title: setcusttimedo   
         * @Description: 保存体检时间 269  
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String setcusttimedo() throws WebException, SQLException {
    	UserDTO user = (UserDTO) session.get("username");
    	Batch taover = new Batch();
		taover = this.batchService.getBatchByID(this.model.getBatch_id());
		if ("1".equals(taover.getOverflag())) {
			this.message = "error-批次任务已经封帐";
		} else {
		if ((this.model.getIds() != null) &&(this.model.getIds().trim()).length()>1) {
			String t1=this.model.getTime1().replaceAll(":", "");
			String t2=this.model.getTime2().replaceAll(":", "");
			if(Integer.valueOf(t1).intValue()>=Integer.valueOf(t2).intValue()){
				this.message = "error-时间段设置错误！";
			}else{
				this.customerInfoService.setExamInfotime(this.model.getIds(), this.model.getBirthday(), this.model.getTime1(), this.model.getTime2(), user.getUserid());
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("269");//子功能id 必须填写
				sl.setExplain("设置体检人员预约时间 "+this.model.getIds()+" "+this.model.getBirthday()+" "+ this.model.getTime1()+" "+ this.model.getTime2());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-体检时间设置完成。";
			}
			
		}else{
			this.message = "error-无效人员信息。";
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
    
    /**
     * 
         * @Title: delcusttimedo   
         * @Description: 删除体检时间 270 
         * @param: @return
         * @param: @throws WebException
         * @param: @throws SQLException      
         * @return: String      
         * @throws
     */
    public String delcusttimedo() throws WebException, SQLException {
    	UserDTO user = (UserDTO) session.get("username");
    	Batch taover = new Batch();
		taover = this.batchService.getBatchByID(this.model.getBatch_id());
		if ("1".equals(taover.getOverflag())) {
			this.message = "error-批次任务已经封帐";
		} else {
		if ((this.model.getIds() != null) &&(this.model.getIds().trim()).length()>1) {
			
				this.customerInfoService.delExamInfotime(this.model.getIds(), user.getUserid());
				//UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("270");//子功能id 必须填写
				sl.setExplain("删除体检人员预约时间 "+this.model.getIds());//操作说明
				syslogService.saveSysLog(sl);
				this.message = "ok-体检时间删除完成。";
			
		}else{
			this.message = "error-无效人员信息。";
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
    
    /**
     * 
         * @Title: countExamInfotime   
         * @Description: 统计时间段    271
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
    public String countExamInfotime()throws WebException {
    	UserDTO user = (UserDTO) session.get("username");
    	List<CustmTimeCountDTO> list = new ArrayList<CustmTimeCountDTO>();
    	list =this.customerInfoService.countExamInfotime(this.model.getCompany_id(),this.model.getBatch_id(),user.getCenter_num()+"",this.defaultapp.getComid());
    	this.outJsonResult(list);
    	return NONE;	
    }
    
    
    /**
     * 
         * @Title: customeritemshow   
         * @Description: 显示加项目主界面    276
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
    public String customeritemaddshow()throws WebException {
    	if((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)){
    		this.message="未选择人员，操作不能继续。";
    		return "commerror";
    	}else{
    		this.message = this.customerInfoService.checkExamInfotime(this.model.getIds());
    		if((this.message==null)||(this.message.indexOf("ok")<0)){    			
    			return "commerror";
    		}else{
    			this.model.setAge(this.model.getIds().trim().split(",").length);
    			String sex = this.customerInfoService.getIDSExamInfoSex(this.model.getIds());
    			this.model.setSex(sex);
    			return SUCCESS;
    		}
    	}
    }
    
    
  
    
   
    
    
    
    /**
     * 
         * @Title: custchangitemdellist   
         * @Description: 通过多个体检编号获取体检项目   
         * @param: @return
         * @param: @throws WebException      
         * @return: String      
         * @throws
     */
    public String custchangitemdellist() throws WebException {
    	UserDTO user = (UserDTO) session.get("username");
    	List<ExaminfoChargingItemDTO> list = new ArrayList<ExaminfoChargingItemDTO>();
        list= this.customerInfoService.getExaminfoChargingItemforExamIds(this.model.getIds(), user.getCenter_num());
 	   this.outJsonResult(list);
 	   return NONE;	
    }
    


    /**
     * 
         * @Title: getComByBatchList   
         * @Description: 通过单位名称获取体检任务    283
         * @param: @return
         * @param: @throws WebException
         * @param: @throws Exception      
         * @return: String      
         * @throws
     */
	public String getComByBatchList() throws WebException, Exception {		
		this.defaultapp = (SystemType)session.get("defaultapp");
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
		
		List<TreeDTO> tr = new ArrayList<TreeDTO>();		
		if(StringUtil.isEmpty(model.getComname())){
			model.setComname("");
		}
		tr=this.batchService.getCompanyForNameBatch(model.getComname(),this.model.getRemark(),defaultapp.getComid(),user);
		this.outJsonResult(tr);
		return NONE;
	}
	
	 /**
     * 
         * @Title: getComByBatchList   
         * @Description: 根据单位id获取所有的子单位 284
         * @param: @return
         * @param: @throws WebException
         * @param: @throws Exception      
         * @return: String      
         * @throws
     */
	public String getCompersonByIdList() throws WebException, Exception {		
		List<TreeDTO> tr = new ArrayList<TreeDTO>();		
		if(this.model.getCompany_id()>0){
			tr=this.companyService.getCompanyPersonForId(this.model.getCompany_id());
		}		
		this.outJsonResult(tr);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getbatchcompanyshow   
	     * @Description: 通过体检计划获取计划内部门  285 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getbatchcompanyshow() throws WebException, Exception {		
		List<TreeDTO> tr = new ArrayList<TreeDTO>();
		tr = this.companyService.getCompanyForBatchComId(model.getCompany_id());
		this.outJsonResult(tr);
		return NONE;
		
	}

	/**
	 *  更新导检单打印状态 764
	     * @Title: updateGuidePrintStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String updateGuidePrintStatus() throws WebException, Exception{
		this.message = this.customerInfoService.updateGuidePrintStatus(this.model.getIds());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *  更新条码打印状态 765
	     * @Title: updateBarcodePrintStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String updateBarcodePrintStatus() throws WebException, Exception{
		this.message = this.customerInfoService.updateBarcodePrintStatus(this.model.getIds());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 既往史列表zyb393
	     * @Title: getDiseaseHistoryTable   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseHistoryTable() throws WebException, Exception{
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb393");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonResult(zybOccuhisService.getDiseaseHistoryTable(model, page,rows));
		return NONE;
	}
	/**
	 * 既往史新增页面zyb394
	     * @Title: zybDiseaseHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String zybDiseaseHistory() throws WebException, Exception{
		return SUCCESS;
	}
	/**
	 * 新增职业史zyb395
	     * @Title: savezybDiseaseHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String savezybDiseaseHistory() throws WebException, Exception{
		UserDTO user = (UserDTO) session.get("username");
		ZybDiseaseHistory  z = new ZybDiseaseHistory();
		z.setArch_num(this.model.getArch_num());
		z.setExam_num(this.model.getExam_num());
		z.setId_num(this.model.getId_num());
		z.setDiagnosisdate(this.model.getDiagnosisdate());
		z.setDiagnosiscom(this.model.getDiagnosiscom());
		z.setDiagnosisnotice(this.model.getDiagnosisnotice());
		z.setDiseases(this.model.getDiseases());
		z.setDiseasereturn(this.model.getDiseasereturn());
		z.setCreater(user.getUserid());
		z.setCreate_time(DateTimeUtil.parse());
		zybOccuhisService.saveDiseaseHistoryadd(z);
		
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb395");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult("保存成功!");
		return NONE;
	}
	/**
	 * 删除既往史zyb396
	     * @Title: deletezybDiseaseHistory   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: String      
	     * @throws
	 */
	public String deletezybDiseaseHistory() throws WebException, Exception{
		zybOccuhisService.deleteDiseaseHistory(idss);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb396");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult("操作成功!");
		return NONE;
	}
	
	
	
	/**
	 *  宝马岗前体检
	     * @Title: zybHazardBMW   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String zybHazardBMW() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if(StringUtil.isEmpty(this.model.getExam_num())){
			this.message = "error-无效体检人员无效";
		}else {
			boolean flags=false;
			List<ZybExamOccuhazardfactorsDTO> occ= new ArrayList<ZybExamOccuhazardfactorsDTO>();
			occ=this.zybCustomerInfoService.getexamOcchazardfactors(this.model.getExam_num());
			for(ZybExamOccuhazardfactorsDTO docc:occ){
				if((docc.getHazard_code().equals(this.model.getZywhyslb()))&&(docc.getOccuphyexaclassid().equals(this.model.getZytjlb()))){
					flags=true;
					break;
				}
			}
			if(flags){
				this.message = "error-相同的记录已经存在，新增失败";
			}else{
			ZybExamOccuhazardfactors zybwh = new ZybExamOccuhazardfactors();
			zybwh.setArch_num(this.model.getArch_num());
			zybwh.setCreate_time(Timeutils.getNowDate());
			zybwh.setCreater(user.getUserid());
			zybwh.setExam_num(this.model.getExam_num());
			//zybwh.setHazard_code(this.model.getZywhyslb());
			zybwh.setIsActive("Y");
			//zybwh.setOccuphyexaclassid("a9859e756edc4f5da40f7c0499a66d58"); //上岗前
			zybwh.setRemark("");
			
			this.defaultapp = (SystemType)session.get("defaultapp");	
			this.zybCustomerInfoService.savezybHazardBMWOccuhazard(this.model.getTiJianType(), zybwh,user,defaultapp.getComid());
			
			SysLog sl = new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid() + "");
			sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
			sl.setXtgnid("");// 可不填写
			sl.setXtgnid2("zyb1540");// 子功能id 必须填写
			sl.setExplain(this.model.getExam_num() + " " + this.model.getZywhyslb() + " " + this.model.getZytjlb());// 操作说明
			syslogService.saveSysLog(sl);
			this.message = "ok-保存成功";
			}
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public String zybUploadData() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		this.model.setTime1(DateTimeUtil.getDate2());
		this.model.setTime2(DateTimeUtil.getDate2());
		this.model.setTime3(DateTimeUtil.getDate2());
		this.model.setTime4(DateTimeUtil.getDate2());
		
		String ZYB_UPLOAD_DATA_PAGE_STYLE = this.customerInfoService.getCenterconfigByKey("ZYB_UPLOAD_DATA_PAGE_STYLE", user.getCenter_num()).getConfig_value();
		if("jq".equals(ZYB_UPLOAD_DATA_PAGE_STYLE)) {
			return "success_jq";
		} else if("ww".equals(ZYB_UPLOAD_DATA_PAGE_STYLE)) {
			return "success_ww";
		}
		return SUCCESS;
	}
	
	public String getZybUserList() throws WebException {
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
			if(this.model.getChkItem().indexOf("yuyue_date") < 0){
				this.model.setYuyue_date1("");
				this.model.setYuyue_date2("");
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
			if (this.model.getChkItem().trim().indexOf("conn_rylb") < 0) {
				this.model.setRen_type(0);
			}
			if (this.model.getChkItem().trim().indexOf("set_id") < 0) {
				this.model.setSet_id(0);
			}
			
			if (this.model.getChkItem().trim().indexOf("data_source") < 0) {
				this.model.setData_source("");
			}
			if(this.model.getChkItem().trim().indexOf("batch_Name") < 0){
				this.model.setBatch_id(0);
			}
			if(this.model.getChkItem().trim().indexOf("sex") < 0){
				this.model.setSex("");
			}
			if (this.model.getChkItem().trim().indexOf("levels") < 0) {
				this.model.setLevels("");
			}
			if (this.model.getChkItem().trim().indexOf("tjlx") < 0) {
				this.model.setCustomer_type("");
			}
			if (this.model.getChkItem().trim().indexOf("djdstatus") < 0) {
				this.model.setDjdstatuss("");
			}
			if(this.model.getChkItem().trim().indexOf("creater") < 0){
				this.model.setCreater(0);
			}
			contractlist = this.zybCustomerInfoService.getZybUserList(this.model.getData_source(),this.model.getCompany_id(),this.model.getSet_id(),
					this.model.getRen_type(), this.model.getArch_num(), this.model.getExam_num(),
					this.model.getEmployeeID(), this.model.getStatus(), this.model.getId_num(),
					this.model.getCustname(), this.model.getTime1(), this.model.getTime2(), this.model.getTime3(),
					this.model.getTime4(), this.model.getCreate_time(), this.model.getExam_type(),this.model.getDjdstatuss(),
					user.getUserid() + "", centernum,this.model, this.rows, this.getPage(), this.sort,
					this.order,user,user.getCenter_num());

		}

		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("286");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);

		this.outJsonResult(contractlist);
		return NONE;
	}
	
	public String uploadZybData() throws WebException {
		String logName = "uploadZybData";
		UserDTO user = (UserDTO) session.get("username");
		String[] exam_nums = this.model.getIds().split(",");
		int success = 0;
		String ZYB_UPLOAD_DATA_PAGE_STYLE = this.customerInfoService.getCenterconfigByKey("ZYB_UPLOAD_DATA_PAGE_STYLE", user.getCenter_num()).getConfig_value();
		for(String exam_num : exam_nums) {
			PutReportMessage putReportMessage = new PutReportMessage();
			if("jq".equals(ZYB_UPLOAD_DATA_PAGE_STYLE)) {
				BodyJQ bodyJQ = PutReportControlJQ.getBodyJQ(exam_num, logName);
				String xml = PutReportControlJQ.getMessage(bodyJQ, logName);
				putReportMessage.setMessage(xml);
			} else if("ww".equals(ZYB_UPLOAD_DATA_PAGE_STYLE)) {
				PERSON person = PutReportControlWW.getPERSON(exam_num, logName);
				if(1 != person.getCompany().getReadFlag()) {
					String xml = PutReportControlWW.getMessage(person.getCompany(), logName);
					putReportMessage.setMessage(xml);
					putReportMessage.sendMessage(person.getCompany().getINSTITUTION_CODE(), user.getCenter_num());
				}
				String xml = PutReportControlWW.getMessage(person, logName);
				putReportMessage.setMessage(xml);
			}
			ResultHeader sendMessage = putReportMessage.sendMessage(exam_num, user.getCenter_num());
			if("AA".equals(sendMessage.getTypeCode())) {
				success++;
			}
		}
		this.message = "ok-发送中，共"+success+"条";
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * zyb登记台职业危害维护2542
	 * @return
	 * @throws WebException
	 */
	public String zybCustomerHazard() throws WebException {
		UserDTO user = (UserDTO)session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid() + "");
		sl.setOper_type("0");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
		sl.setXtgnid("");// 可不填写
		sl.setXtgnid2("2542");// 子功能id 必须填写
		sl.setExplain("");// 操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

}