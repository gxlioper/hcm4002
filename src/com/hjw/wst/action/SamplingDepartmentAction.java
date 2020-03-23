package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.SampleExamDetailDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleExamDetail;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SamplingDepartmentModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.LisPacsApplicationService;
import com.hjw.wst.service.SamplingDepartmentService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

/**
 * 采样室功能
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2016年10月16日 下午8:13:07   
     * @version V2.0.0.0
 */
public class SamplingDepartmentAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 8217814910220881058L;

	private SamplingDepartmentModel model = new SamplingDepartmentModel();
	private SamplingDepartmentService samplingDepartmentService;
	private LisPacsApplicationService lisPacsApplicationService;
	private CustomerInfoService customerInfoService;
	private SyslogService syslogService;
	
	@Override
	public Object getModel() {
		return model;
	}
	public void setModel(SamplingDepartmentModel model) {
		this.model = model;
	}
	public void setSamplingDepartmentService(SamplingDepartmentService samplingDepartmentService) {
		this.samplingDepartmentService = samplingDepartmentService;
	}
	
	public void setLisPacsApplicationService(LisPacsApplicationService lisPacsApplicationService) {
		this.lisPacsApplicationService = lisPacsApplicationService;
	}
	
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	/**
	 * 采样室页面  364
	     * @Title:    getSamplingPage
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSamplingPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		this.model.setZyb_barcode_print_type(this.customerInfoService.getCenterconfigByKey("ZYB_BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("364");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 获取采样项目列表 365
	     * @Title: getsamplingItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getsamplingItemList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<SampleExamDetailDTO> list = this.samplingDepartmentService.getSamplingDetailList(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 验证条码号是否存在 366
	     * @Title: yanzhengBarcode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String yanzhengBarcode() throws WebException,SQLException{
		SampleExamDetail sample = this.samplingDepartmentService.getSampleDetailByBarcode(this.model.getSample_barcode());
		if(sample != null &&  sample.getId() != this.model.getSampleId() && this.model.getSampleId() != 0){
			this.message = "no";
		}else{
			this.message = "ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 采样(绑定预印条码) 367
	     * @Title: samplingSamplExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String samplingSamplExamDetail() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray itemarray = JSONArray.fromObject(this.model.getSampleExamDetails());
		List<SampleExamDetailDTO> sampleExamDetailList = (List<SampleExamDetailDTO>) JSONArray.toCollection(itemarray,SampleExamDetailDTO.class);
		this.model.setSampleExamDetailList(sampleExamDetailList);
		
		this.message = this.samplingDepartmentService.samplingSample(model, user);
		
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
		String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
		try {
			if("Y".equals(lis) && "Y".equals(lis_bang)){
				this.message = this.lisPacsApplicationService.lisSend(model.getExam_num(), model.getCharingids(), user, false,lis_bang,"N");
			}
			this.message = "ok-保存成功!";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("367");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} catch (ServiceException e) {
			this.message = "error-"+e.getMessage();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 取消采样  368
	     * @Title: canlSamplExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String canlSamplExamDetail() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
		try {
			if("Y".equals(lis)){
				this.message = this.lisPacsApplicationService.lisDel(model.getExam_num(), model.getCharingids(), user);
				if("ok".equals(this.message.split("-")[0])){
					this.message = "ok-取消采样成功!";
				}
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("368");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} catch (ServiceException e) {
			this.message = "error-"+e.getMessage();
		}
		
		this.message = this.samplingDepartmentService.canlSampleExamDetail(model, user);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * Lis补发申请 389
	     * @Title: sendLisApplication   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String sendLisApplication() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
		String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
		try {
			if("Y".equals(lis)){
				this.message = this.lisPacsApplicationService.lisSend(model.getExam_num(), null, user,false,lis_bang,"N");
			}else{
				this.message = "error-未启用lis接口，不需要补发申请!";
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("389");//子功能id 必须填写
			sl.setExplain("Lis补发申请");//操作说明
			syslogService.saveSysLog(sl);
		} catch (ServiceException e) {
			this.message = "error-"+e.getMessage();
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 根据条码号反查人员信息  720
	     * @Title: getExamInfoByBarCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExamInfoByBarCode() throws WebException,SQLException{
		ExamInfoDTO examinfo = this.samplingDepartmentService.getExamInfoByBarCode(this.model.getSample_barcode());
		if(examinfo != null){
			this.message = examinfo.getExam_num();
		}else{
			this.message = "no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 强制合并样本 797
	     * @Title: mergeSampleDemo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String mergeSampleExamDetail() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.samplingDepartmentService.mergeSampleDemo(model);
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("797");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 验证获取用户的打印资源 798
	     * @Title: verifyUserPrintBarcode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String verifyUserPrintBarcode() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.samplingDepartmentService.verifyUserPrintBarcode(model, user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 获取可验证用户打印资源的登录页面 799
	     * @Title: getPrintLoginPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPrintLoginPage() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 1134 拆分样本功能
	     * @Title: splitUpSampleExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String splitUpSampleExamDetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.samplingDepartmentService.splitUpSampleExamDetail(model, user);
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1134");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 1135团体批量合并样本页面
	     * @Title: getTeamSampleListPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getTeamSampleListPage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 1136获取所选团体人员检验科项目列表(并集)
	     * @Title: getTeamSampleListItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getTeamSampleListItem()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<SampleExamDetailDTO> list = this.samplingDepartmentService.getTeamSampleListItem(model.getExaminfo_ids(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 1137保存团体人员管理批量合并样本数据
	     * @Title: saveTeamSampleListItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveTeamSampleListItem()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.samplingDepartmentService.saveTeamSampleListItem(model, user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1137");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		this.message = "批量合并样本成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1140 获取科室采样打条码页面
	     * @Title: getDepPrintSamplePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDepPrintSamplePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setBar_code_url(this.customerInfoService.getCenterconfigByKey("BAR_CODE_URL", user.getCenter_num()).getConfig_value().trim());
		this.model.setBarcode_print_type(this.customerInfoService.getCenterconfigByKey("BARCODE_PRINT_TYPE", user.getCenter_num()).getConfig_value().trim());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1140");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 1141 获取本科室需采用和打印条码的项目
	     * @Title: getDepPrintSanpleItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDepPrintSanpleItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<SampleExamDetailDTO> list = this.samplingDepartmentService.getDepPrintSanpleItem(model.getExam_num(), user.getDep_id(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1142 科室采样保存
	     * @Title: saveDepSampleItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveDepSampleItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		JSONArray itemarray = JSONArray.fromObject(this.model.getSampleExamDetails());
		List<SampleExamDetailDTO> sampleExamDetailList = (List<SampleExamDetailDTO>) JSONArray.toCollection(itemarray,SampleExamDetailDTO.class);
		this.model.setSampleExamDetailList(sampleExamDetailList);
		
		this.samplingDepartmentService.saveDepSampleItem(model, user);
		
		String lis = this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value().trim();
		String lis_bang = this.customerInfoService.getCenterconfigByKey("IS_LIS_BANGDING", user.getCenter_num()).getConfig_value().trim();
		try {
			if("Y".equals(lis) && "Y".equals(lis_bang)){
				this.message = this.lisPacsApplicationService.lisSend(model.getExam_num(), model.getCharingids(), user, false,lis_bang,"N");
			}
			this.message = "ok-保存成功!";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("1142");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} catch (ServiceException e) {
			this.message = "error-"+e.getMessage();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *1728 采样室打条码自动采样
	     * @Title: autoUpdateSamplExamDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String autoUpdateSamplExamDetail() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.customerInfoService.updateSampleExamEetail(model.getExaminfo_id(), user.getUserid(),user.getName());
		this.outJsonStrResult("ok-操作成功");
		return NONE;
	}
	
	/**
	 * 取消采样(不重新生成条码)
	     * @Title: canlSampleLisPacsStatus   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String canlSampleLisPacsStatus() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.samplingDepartmentService.canlSampleLisPacsStatus(model.getId(), model.getBarcode_print_type(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
