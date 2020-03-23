package com.hjw.zyb.action;

import java.sql.SQLException;

import org.hsqldb.lib.StringUtil;

import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.ImpCustomerInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.ImpCustomerInfo.InfoType;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.model.FsxzybImpCustomerInfoModel;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class FsxzybCustomerInfoAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private int rows = 15; // easyui每页显示条数
	
	private BatchService batchService;
	private CompanyService companyService;
	
	private FsxzybImpCustomerInfoModel model = new FsxzybImpCustomerInfoModel();
	private CustomerInfoService customerInfoService;
	private SyslogService syslogService;
	
	public Object getModel() {
		return model;
	}

	public void setModel(FsxzybImpCustomerInfoModel model) {
		this.model = model;
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

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	/**
	 * 
	     * @Title: fsxzybimpusershow   
	     * @Description: zyb512 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String fsxzybimpusershow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb512");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: fsxzybimpusershowlist   
	     * @Description: 放射性职业病导入表查询列表 zyb513   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String fsxzybimpusershowlist() throws WebException, SQLException {
		PageReturnDTO contractlist = new PageReturnDTO();
		UserDTO user = (UserDTO) session.get("username");
		if (rows <= 15) {
			rows = 250;
		}
		contractlist = this.customerInfoService.getImpCustomerInfoList(this.model.getBatch_id(),
				this.model.getCompany_id(), user.getUserid(), InfoType.FSXZYB, this.rows, this.getPage());
		this.outJsonResult(contractlist);
		return NONE;
	}
	

	/**
	 * 
	     * @Title: fsxzybsaveCustomerTmplist   
	     * @Description:保存导入的临时表数据 zyb515   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */

	public String fsxzybsaveCustomerTmplist() throws WebException, SQLException {
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
			ci.setOccusector(this.model.getOccusector());
			ci.setOccutypeofwork(this.model.getOccutypeofwork());
			ci.setBorn_address(this.model.getBorn_address());
			ci.setNation(this.model.getNation());
			ci.setAddress(this.model.getAddress());
			ci.setZip(this.model.getZip());
			ci.setDegreeOfedu(this.model.getDegreeOfedu());
			ci.setSc_class(this.model.getSc_class());
			ci.setSets(this.model.getSets());
			this.customerInfoService.updateImpCustomerInfo(ci);
			this.message = "ok-修改导入数据成功";
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb515");//子功能id 必须填写
			sl.setExplain("数据导入 "+ci.getCustname()+" "+ci.getBatch_id());//操作说明
			syslogService.saveSysLog(sl);
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}
	

	/**
	 *       
	     * @Title: fsxzybimpuserToExaminfodo   
	     * @Description: zyb516 放射性职业病部分进入正式库 
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String fsxzybimpuserToExaminfodo() throws WebException{
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
						user.getUserid(), this.model.getIds(),user.getCenter_num()+"",this.defaultapp.getComid());
				    //UserDTO user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("4");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb516");//子功能id 必须填写
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
}