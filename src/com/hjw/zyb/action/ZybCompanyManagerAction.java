package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CompanyInfoModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("rawtypes")
public class ZybCompanyManagerAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private CompanyService companyService;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CompanyInfoModel model = new CompanyInfoModel();
    private CustomerInfoService customerInfoService;   
    private BatchService batchService;
    private CommService commService; 
    private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setCommService(CommService commService) {
		this.commService = commService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
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

	public Object getModel() {
		return model;
	}

	public void setModel(CompanyInfoModel model) {
		this.model = model;
	}

	public void setCompanyService(CompanyService companyService) {
		this.companyService = companyService;
	}

	public String TermAccount() throws WebException, SQLException {
		Language.setLanguage(this.language);
		return "TermAccount";
	}

	/**
	 * 
	     * @Title: zybdeptmanager   
	     * @Description: 职业病单位管理主页面  zyb1  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybdeptmanager() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb1");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 
	 * @Title: companyedit @Description:
	 *         体检单位新增和删除-zyb1 
	 *@param: @return @param: @throws
	 *         WebException @param: @throws SQLException @return: String @throws
	 */
	public String zybcompanyedit() throws WebException, SQLException {
		CompanyInfo cinf = new CompanyInfo();
		if (this.model.getId() <= 0) {
			model.setDep_Name("顶级部门");
			model.setCompany_Id(0);
		} else {
			cinf = this.companyService.getComByID(this.model.getId());
			this.model.setAddress(cinf.getAddress());
			this.model.setCom_Level(cinf.getCom_Level());
			this.model.setCom_Name(cinf.getCom_Name());
			this.model.setCom_Num(cinf.getCom_Num());
			this.model.setCom_Type(cinf.getCom_Type());
			this.model.setContact_Name(cinf.getContact_Name());
			this.model.setContact_Phone(cinf.getContact_Phone());
			this.model.setCreate_Time(cinf.getCreate_Time());
			this.model.setCreater(cinf.getCreater());
			this.model.setEmail(cinf.getEmail());
			this.model.setId(cinf.getId());
			this.model.setIs_Active(cinf.getIs_Active());
			this.model.setParent_com_id(cinf.getParent_com_id());
			this.model.setRemark(cinf.getRemark());
			this.model.setUpdate_Time(cinf.getUpdate_Time());
			this.model.setUpdater(cinf.getUpdater());
			this.model.setName_pinyin(cinf.getName_pinyin());
			this.model.setAreacode(cinf.getAreacode());
			this.model.setIndustrycode(cinf.getIndustrycode());
			this.model.setEconomiccode(cinf.getEconomiccode());
			this.model.setComsizecode(cinf.getComsizecode());
			this.model.setCom_zip(cinf.getCom_zip());
			this.model.setCom_phone(cinf.getCom_phone());
			if ((this.model.getParent_com_id() == null) || (this.model.getParent_com_id() <= 0)) {
				model.setDep_Name("根目录（一级单位）");
				model.setCompany_Id(0);
				model.setFather_con_num("000");
			} else {
				CompanyInfo cfath = this.companyService.getComByID(cinf.getParent_com_id());
				model.setDep_Name(cfath.getCom_Name());
				model.setCompany_Id(cfath.getId());
				model.setFather_con_num(cfath.getCom_Num());
			}
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb2");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 单位信息维护 zyb3
	     * @Title: editcompany   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String zybeditcompany() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getEdittype() <= 0) {
			this.message = "error-无效请求！";
		}else{
		 if((this.model.getCom_Level()==0)&&(this.model.getEdittype()!=4)){
			this.model.setEdittype(2);
			this.model.setCom_Level(1);
		}
		
		if (this.model.getEdittype() == 1) {// 本级单位下创建新单位
			if (model.getCom_Level() >= 5) {
				this.message = "error-不能在本级单位下创建新单位，因为只支持5级单位！";
			} else {
				
				CompanyInfo ciold = new CompanyInfo();
				ciold = this.companyService.getComByName(model.getId(),this.model.getCom_Name().trim(),user);
				if((ciold!=null)&&(ciold.getId()>0)){
					this.message = "error-相同的单位名称已经存在！";
				}else{
					
				CompanyInfo ci = new CompanyInfo();
				ci.setAddress(model.getAddress());
				ci.setParent_com_id(model.getId());
				ci.setCom_Num(this.batchService.GetCreateID("com_num", user.getCenter_num()));
				ci.setCom_Level(model.getCom_Level() + 1);
				ci.setCom_Name(model.getCom_Name());
				//ci.setCom_Num(model.getCom_Num());
				ci.setCom_Type(model.getCom_Type());
				ci.setAreacode(model.getAreacode());
				ci.setComsizecode(model.getComsizecode());
				ci.setEconomicidcode(model.getEconomiccode());
				ci.setIndustrycode(model.getIndustrycode());
				ci.setContact_Name(model.getContact_Name());
				ci.setContact_Phone(model.getContact_Phone());
				ci.setCreater(user.getUserid());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_Time(DateTimeUtil.parse());
				ci.setCreate_Time(DateTimeUtil.parse());
				ci.setEmail(model.getEmail());
				ci.setIs_Active(model.getIs_Active());
				ci.setRemark(model.getRemark());
				ci.setName_pinyin(model.getName_pinyin());
				ci.setCom_zip(model.getCom_zip());
				ci.setCom_phone(model.getCom_phone());
				this.companyService.saveCompanyInfo(ci,user);
				this.message = "ok-单位创建成功！";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb3");//子功能id 必须填写
				sl.setExplain("单位创建 "+ci.getId()+" "+ci.getCom_Name());//操作说明
				syslogService.saveSysLog(sl);
			}
			}

		} else if (this.model.getEdittype() == 2) {// 上级单位下创建新单位
			CompanyInfo ciold = new CompanyInfo();
			ciold = this.companyService.getComByName(model.getCompany_Id(),this.model.getCom_Name().trim(),user);
			if((ciold!=null)&&(ciold.getId()>0)){
				this.message = "error-相同的单位名称已经存在！";
			}else{
				CompanyInfo ci = new CompanyInfo();
				ci.setAddress(model.getAddress());
				ci.setCom_Num(this.batchService.GetCreateID("com_num", user.getCenter_num()));
				ci.setParent_com_id(model.getCompany_Id());
				//ci.setCom_Num(model.getCom_Num());
				ci.setCom_Level(model.getCom_Level());
				ci.setCom_Name(model.getCom_Name());
				ci.setCom_Type(model.getCom_Type());
				ci.setContact_Name(model.getContact_Name());
				ci.setContact_Phone(model.getContact_Phone());
				ci.setCreater(user.getUserid());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_Time(DateTimeUtil.parse());
				ci.setCreate_Time(DateTimeUtil.parse());
				ci.setEmail(model.getEmail());
				ci.setIs_Active(model.getIs_Active());
				ci.setRemark(model.getRemark());
				ci.setAreacode(model.getAreacode());
				ci.setComsizecode(model.getComsizecode());
				ci.setEconomicidcode(model.getEconomiccode());
				ci.setIndustrycode(model.getIndustrycode());
				ci.setName_pinyin(model.getName_pinyin());
				ci.setCom_zip(model.getCom_zip());
				ci.setCom_phone(model.getCom_phone());
				this.companyService.saveCompanyInfo(ci,user);
				this.message = "ok-单位创建成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb3");//子功能id 必须填写
				sl.setExplain("单位创建 "+ci.getId()+" "+ci.getCom_Name());//操作说明
				syslogService.saveSysLog(sl);
		}
		} else if (this.model.getEdittype() == 3) {// 修改
			CompanyInfo ci = new CompanyInfo();
			ci = this.companyService.findComByID(this.model.getId());
			if ((ci == null) || (ci.getCom_Level() <= 0)) {
				this.message = "error-单位不存在，无法修改！";
			} else {
				
				CompanyInfo ciold = new CompanyInfo();
				ciold = this.companyService.getComByName(model.getCompany_Id(),model.getCom_Name().trim(),user);
				if((ciold!=null)&&(ciold.getId()>0)&&(ciold.getId()!=this.model.getId())){
					this.message = "error-相同的单位名称已经存在！";
				}else{
				
				ci.setAddress(model.getAddress());
				ci.setParent_com_id(model.getCompany_Id());
				ci.setCom_Level(model.getCom_Level());
				ci.setCom_Name(model.getCom_Name().trim());
				ci.setCom_Type(model.getCom_Type());
				ci.setContact_Name(model.getContact_Name());
				ci.setContact_Phone(model.getContact_Phone());
				ci.setCom_Num(model.getCom_Num());
				ci.setUpdater(user.getUserid());
				ci.setUpdate_Time(DateTimeUtil.parse());
				ci.setEmail(model.getEmail());
				ci.setIs_Active(model.getIs_Active());
				ci.setName_pinyin(model.getName_pinyin());
				ci.setAreacode(model.getAreacode());
				ci.setComsizecode(model.getComsizecode());
				ci.setEconomicidcode(model.getEconomiccode());
				ci.setIndustrycode(model.getIndustrycode());
				ci.setRemark(model.getRemark());
				ci.setCom_zip(model.getCom_zip());
				ci.setCom_phone(model.getCom_phone());
				if (ci.getParent_com_id() == 1)
					ci.setParent_com_id(null);
				this.companyService.updateCompanyInfo(ci,user);
				this.message = "ok-单位修改成功！";
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb3");//子功能id 必须填写
				sl.setExplain("单位修改 "+ci.getId()+" "+ci.getCom_Name());//操作说明
				syslogService.saveSysLog(sl);
				}
			}
		} else if (this.model.getEdittype() == 4) {// 删除
			CompanyInfo ci = new CompanyInfo();
			ci = this.companyService.findComByID(this.model.getId());
			if ((ci == null) || (ci.getCom_Level() <= 0)) {
				this.message = "error-单位不存在，无法删除！";
			} else {
				List<CompanyDepartment> list = new ArrayList<CompanyDepartment>();
				list = this.companyService.getCompanyDepartmentByComid(this.model.getId());
				if((list!=null)&&(list.size()>0)){
					this.message = "error-单位下面存在部门，无法删除！";
				}else{
					List<BatchDTO> listb=new ArrayList<BatchDTO>();
					listb =this.batchService.getbatchList(this.model.getId(),user.getCenter_num(),"");
					if((listb!=null)&&(listb.size()>0)){
						this.message = "error-单位下面存在体检任务，无法删除！";
					}else{
						 List<ContractDTO> listc=new ArrayList<ContractDTO>();
						 listc =this.batchService.contractlistshow(this.model.getId());
						 if((listc!=null)&&(listc.size()>0)){
								this.message = "error-单位下面存在合同，无法删除！";
							}else{
								List<TreeDTO>  comlist=new ArrayList<TreeDTO>();
								comlist=this.companyService.getCompanyForAll(this.model.getId());
								boolean delflag=false;
								for(TreeDTO tree:comlist){
									int counts = this.batchService.examcountforcomid(Long.valueOf(tree.getId()),user);
									if(counts>0){
										delflag=true;
										break;
									}
								}
								if(delflag){
									this.message = "error-单位下面存在人员，无法删除！";
								}else{
								ci.setIs_Active("N");
								ci.setUpdater(user.getUserid());
								ci.setUpdate_Time(DateTimeUtil.parse());
								this.companyService.updateCompanyInfo(ci,user);
								this.message = "ok-单位删除成功！";
								SysLog sl =  new SysLog();
								sl.setCenter_num(user.getCenter_num());
								sl.setUserid(user.getUserid()+"");
								sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
								sl.setXtgnid("");//可不填写
								sl.setXtgnid2("zyb3");//子功能id 必须填写
								sl.setExplain("单位创建 "+ci.getId()+" "+ci.getCom_Name());//操作说明
								syslogService.saveSysLog(sl);
							}
							}					
					}					
				}				
			}
		} else {
			this.message = "error-无效参数！";
		}
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
}