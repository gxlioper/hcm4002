package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.domain.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Timeutils;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.BatchProPlanDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.ExamSetDTO;
import com.hjw.wst.DTO.FangAnShowDTO;
import com.hjw.wst.DTO.GroupChargingItemDTO;
import com.hjw.wst.DTO.GroupInfoDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.config.GetNumContral;
import com.hjw.wst.model.BatchModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.BeanUtil;
import com.synjones.framework.web.action.BaseAction;

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
public class ZybBatchAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private BatchService batchService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CompanyService companyService;
	private BatchModel model = new BatchModel();
	private UserInfoService userInfoService;
    private CustomerInfoService customerInfoService;
    private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
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

	public void setModel(BatchModel model) {
		this.model = model;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	/**
	 * 方案管理 zyb4 主界面
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String zybbatchManager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb4");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 方案管理 zyb5 主界面
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	public String zybbatchshow() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");

		List<WebResrelAtionship> web = user.getWebResource();
		if(web!=null){
			for (int i = 0; i < web.size(); i++) {
				if(web.get(i).getRes_code().equals("RS059")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsaccounttype(1);
					}
				}
				if(web.get(i).getRes_code().equals("RS060")){
					if("1".equals(web.get(i).getDatavalue())){
						this.model.setIsunaccounttype(1);
					}
				}
			}
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	
	/**
	 * 
	 * @Title: batchedit @Description: 编辑方案 zyb6 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String zybbatchedit() throws WebException, SQLException {
		if (this.model.getCompany_id() <= 0) {
			this.message = "选择单位无效";
			return "commerror";
		} else {
			CompanyInfo cif = new CompanyInfo();
			cif = companyService.findComByID(this.model.getCompany_id());
			this.model.setComname(cif.getCom_Name());
		if (this.model.getId() > 0) {
			Batch batch = new Batch();
			batch = this.batchService.getBatchByID(this.model.getId());
			model.setAccommodation(batch.getAccommodation());
			model.setBatch_address(batch.getBatch_address());
			model.setBatch_name(batch.getBatch_name());
			model.setBatch_num(batch.getBatch_num());
			model.setCharge_type(batch.getCharge_type());
			model.setCompany_id(batch.getCompany_id());
			model.setContact_name(batch.getContact_name());
			model.setDine(batch.getDine());
			 if ((batch.getExam_date() != null) && (batch.getExam_date().indexOf("1900-01-01") < 0)) {
			        this.model.setExam_date(batch.getExam_date());
			      } else {
			        this.model.setExam_date("");
			      }
			model.setExam_date(batch.getExam_date());
			model.setExam_fee(batch.getExam_fee());
			model.setExam_item(batch.getExam_item());
			model.setExam_number(batch.getExam_number());
			model.setId(batch.getId());
			model.setIntroducer_name(batch.getIntroducer_name());
			model.setInvoice_title(batch.getInvoice_title());
			model.setIs_Active("Y");
			model.setPay_way(batch.getPay_way());
			model.setPhone(batch.getPhone());
			model.setQian_remark(batch.getQian_remark());
			model.setRemark(batch.getRemark());
			model.setSales_name(batch.getSales_name());
		} else {
			Batch batch = new Batch();
			batch.setBatch_num(DateTimeUtil.getDate() + "-");
			BeanUtil.copy(model, batch);
			this.model.setExam_fee("0");
			this.model.setContact_name(cif.getContact_Name());
			this.model.setPhone(cif.getContact_Phone());
		}
		}
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb6");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}


	/**
	 * 
	 * @Title: tatchedit @Description: 编辑方案 zyb7 @param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String zybbatcheditdo() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		
		if (this.model.getId() > 0) {
			BatchDTO bdot = new BatchDTO();
			bdot = this.batchService.getbatchForcomidandname(model.getCompany_id(), model.getBatch_name(),user.getCenter_num(),model.getId());
			if(bdot.getId()>0){
				this.message = "error-体检任务名称重复，不能修改！";
			}else {
				/*boolean userbatchflag = false;
				userbatchflag = this.batchService.useredBatch(this.model.getId());
				if (userbatchflag) {
					this.message = "error-体检任务已经被使用，不能修改！";
				} else */
				{
					Batch bc = new Batch();
					bc = this.batchService.getBatchByID(this.model.getId());
					if ("1".equals(bc.getOverflag())) {
						this.message = "error-批次任务已经封帐";
					} else {
						bc.setAccommodation(model.getAccommodation());
						bc.setBatch_address(model.getBatch_address());
						bc.setBatch_name(model.getBatch_name());
						bc.setBatch_num(model.getBatch_num());
						bc.setCharge_type(model.getCharge_type());
						bc.setCompany_id(model.getCompany_id());
						bc.setContact_name(model.getContact_name());
						bc.setDine(model.getDine());
						bc.setExam_date(model.getExam_date());
						bc.setExam_fee(model.getExam_fee());
						bc.setExam_item(model.getExam_item());
						bc.setExam_number(model.getExam_number());
						bc.setId(model.getId());
						bc.setIntroducer_name(model.getIntroducer_name());
						bc.setInvoice_title(model.getInvoice_title());
						bc.setIs_Active("Y");
						bc.setPay_way(model.getPay_way());
						bc.setPhone(model.getPhone());
						bc.setQian_remark(model.getQian_remark());
						bc.setRemark(model.getRemark());
						bc.setSales_name(model.getSales_name());
						bc.setApptype("2");
						bc.setChecktype(2);
					bc.setId(this.model.getId());
					bc.setUpdater(user.getUserid());
					bc.setUpdate_time(DateTimeUtil.parse());
					bc = this.batchService.updateBatch(bc);
					this.message = "ok-体检任务修改成功！";
					
					user = (UserDTO) session.get("username");
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("zyb7");//子功能id 必须填写
					sl.setExplain("修改批次任务 "+bc.getBatch_num()+" "+bc.getBatch_name());//操作说明
					syslogService.saveSysLog(sl);
					
					}
				}
			}
		} else {
			BatchDTO bdot = new BatchDTO();

			Batch bc = new Batch();
			bc.setAccommodation(model.getAccommodation());
			bc.setBatch_address(model.getBatch_address());
			bc.setBatch_name(model.getBatch_name());
			bc.setBatch_num(model.getBatch_num());
			bc.setCharge_type(model.getCharge_type());
			bc.setCompany_id(model.getCompany_id());
			bc.setContact_name(model.getContact_name());
			bc.setDine(model.getDine());
			bc.setExam_date(model.getExam_date());
			bc.setExam_fee(model.getExam_fee());
			bc.setExam_item(model.getExam_item());
			bc.setExam_number(model.getExam_number());
			bc.setId(model.getId());
			bc.setIntroducer_name(model.getIntroducer_name());
			bc.setInvoice_title(model.getInvoice_title());
			bc.setIs_Active("Y");
			bc.setPay_way(model.getPay_way());
			bc.setPhone(model.getPhone());
			bc.setQian_remark(model.getQian_remark());
			bc.setRemark(model.getRemark());
			bc.setApptype("2");
			bc.setSales_name(model.getSales_name());
			bdot = this.batchService.getbatchForcomidandname(model.getCompany_id(), model.getBatch_name(),user.getCenter_num(),0);
			if ((bdot != null) && (bdot.getId() > 0)) {
				this.message = "error-体检任务名称重复，不能新增！";
			} else {
				
					bc.setChecktype(2);
				
				bc.setCheckuser(user.getUserid());
				bc.setCreater(user.getUserid());
				bc.setCreate_time(DateTimeUtil.parse());
				bc.setUpdater(user.getUserid());
				bc.setCenter_num(user.getCenter_num());
				bc.setUpdate_time(DateTimeUtil.parse());
				bc = this.batchService.saveBatch(bc);
				user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb7");//子功能id 必须填写
				sl.setExplain("新增批次任务信息 "+bc.getBatch_num()+" "+bc.getBatch_name());//操作说明
				syslogService.saveSysLog(sl);
				CompanyInfo cif = new CompanyInfo();
				cif = companyService.findComByID(bc.getCompany_id());
				// 合同审核
				ContractDTO cd = new ContractDTO();
				cd = this.batchService.getContractForBrachId(bc.getId());
				if ((cd == null) || (cd.getId() <= 0)) {// 新增合同
					Contract ct = new Contract();
					ct.setBatch_id(bc.getId());
					ct.setBatch_name(bc.getBatch_name());
					ct.setCompany_id(bc.getCompany_id());
					ct.setCompany_name(cif.getCom_Name());
					String connum = customerInfoService.getCenterconfigByKey("IS_CONTRACT_NO", user.getCenter_num()).getConfig_value();
					connum = connum + GetNumContral.getInstance().getParamNum("contract", user.getCenter_num());
					ct.setContract_num(connum);
					ct.setCreater(user.getUserid());
					ct.setCreate_time(DateTimeUtil.parse());
					ct.setUpdater(user.getUserid());
					ct.setUpdate_time(DateTimeUtil.parse());
					ct.setLinkman(bc.getContact_name());
					ct.setTel(bc.getPhone());
					String addday = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_EXADATE",user.getCenter_num())
							.getConfig_value().trim();
					String vdate = DateTimeUtil.DateAdd(Integer.valueOf(addday));
					ct.setValidity_date(vdate);
					ct.setCheckdate(Timeutils.getNowDate());
					ct.setCheckuser(user.getUserid());
					if (bc.getChecktype() == 2) {
						String checktypesss = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK",user.getCenter_num())
								.getConfig_value().trim();
						if ("0".equals(checktypesss)) {
							ct.setTypes(0);
						} else if ("1".equals(checktypesss)) {
							ct.setTypes(2);
							ct.setChecknotice("系统自动发起合同审核");
						}
					}
					this.batchService.saveContract(ct);
					user = (UserDTO) session.get("username");
					sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("219");//子功能id 必须填写
					sl.setExplain("新增合同信息 "+ct.getContract_num()+" "+ct.getCompany_name());//操作说明
					syslogService.saveSysLog(sl);
				} else if (cd.getTypes() != 2) {
					Contract ct = new Contract();
					ct = this.batchService.loadContract(cd.getId());
					ct.setCheckdate(Timeutils.getNowDate());
					ct.setCheckuser(user.getUserid());
					if (bc.getChecktype() == 2) {
						String checktypesss = this.customerInfoService.getCenterconfigByKey("IS_CONTRACT_CHECK",user.getCenter_num())
								.getConfig_value().trim();
						if ("0".equals(checktypesss)) {
							ct.setTypes(0);
						} else if ("1".equals(checktypesss)) {
							ct.setTypes(2);
							ct.setChecknotice("系统自动发起合同审核");
						}
						this.batchService.updateContract(ct);
						sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("219");//子功能id 必须填写
						sl.setExplain("修改合同信息 "+ct.getContract_num()+" "+ct.getCompany_name());//操作说明
						syslogService.saveSysLog(sl);
					}
				}
				this.message = "ok-体检任务增加成功！";				
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: zybbatchdelete   
	     * @Description: 删除批次方案 zyb8   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybbatchdelete() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId()<=0){
			this.message="error-无效的体检任务编号！";
		}else{
			Batch taover = new Batch();
			taover = this.batchService.getBatchByID(this.model.getId());
			if ("1".equals(taover.getOverflag())) {
				this.message = "error-批次任务已经封帐";
			} else {
			boolean userbatchflag=false;
			userbatchflag=this.batchService.useredBatch(this.model.getId(),user.getCenter_num());
			if(userbatchflag){
				this.message="error-体检任务已经被使用，不能删除！";
			}else{
				Batch bc = new Batch();
				bc = this.batchService.getBatchByID(this.model.getId());
				if ("Y".equals(bc.getIs_Active())) {
					bc.setIs_Active("N");
				} else if ("N".equals(bc.getIs_Active())) {
					bc.setIs_Active("Y");
				} else {
					bc.setIs_Active("Y");
				}
				bc.setUpdater(user.getUserid());
				bc.setUpdate_time(DateTimeUtil.parse());
				bc = this.batchService.updateBatch(bc);
				this.message = "ok-删除体检任务成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb8");//子功能id 必须填写
				sl.setExplain("删除批次方案 "+bc.getBatch_num()+" "+bc.getBatch_name());//操作说明
				syslogService.saveSysLog(sl);
			}
		}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}	
	
	/**
	 * 
	     * @Title: zybbatchUserManager   
	     * @Description: 职业病单位人员管理 zyb9   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	 public String zybbatchUserManager()throws WebException, SQLException { 
	    	UserDTO user = (UserDTO) session.get("username");
	    	SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb9");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
	    }
}