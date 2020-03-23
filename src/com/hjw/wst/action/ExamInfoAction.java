package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.StringUtil;
import com.hjw.webService.client.QueueCustomSendMessage;
import com.hjw.webService.client.Bean.QueueCustomerBean;
import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TeamAccountExamListDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.ExamInfoRecyclingGuid;
import com.hjw.wst.domain.ExaminfoChargingItem;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExamInfoModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.ExamInfoRecyclingGuidService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.TeamAccountService;
import com.hjw.wst.service.examInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 * @param <Department>
 */
public class ExamInfoAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private ExamInfoModel model = new ExamInfoModel();
	private ExamInfoRecyclingGuidService   examInfoRecyclingGuidService;
	private examInfoService examInfoService;
	private CustomerInfoService customerInfoService;
	private BatchService batchService;
	private TeamAccountService teamAccountService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;//批量
	private String date;//延期时间
	private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	
	public ExamInfoRecyclingGuidService getExamInfoRecyclingGuidService() {
		return examInfoRecyclingGuidService;
	}

	public void setExamInfoRecyclingGuidService(
			ExamInfoRecyclingGuidService examInfoRecyclingGuidService) {
		this.examInfoRecyclingGuidService = examInfoRecyclingGuidService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}


	public void setTeamAccountService(TeamAccountService teamAccountService) {
		this.teamAccountService = teamAccountService;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}


	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}


	public examInfoService getExamInfoService() {
		return examInfoService;
	}

	public void setExamInfoService(examInfoService examInfoService) {
		this.examInfoService = examInfoService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setModel(ExamInfoModel model) {
		this.model = model;
	}

	/**
	 * 加载结束回收页面
	     * @Title: examInfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	
	public String examInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String isDjdAutoRecover = "";
		try {
			 isDjdAutoRecover = this.customerInfoService.getCenterconfigByKey("IS_DJD_AUTO_RECOVER", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
			System.out.println("缺少 IS_DJD_AUTO_RECOVER 配置。。。。");
		}
		String IS_SAMPLING_DEL="N";
		try {
			IS_SAMPLING_DEL = this.customerInfoService.getCenterconfigByKey("IS_SAMPLING_DEL", user.getCenter_num()).getConfig_value().trim();
		} catch (Exception e) {
			System.out.println("缺少 IS_SAMPLING_DEL 配置。。。。");
		}
		
		
		this.model.setOrder_id(IS_SAMPLING_DEL);
		this.model.setIsDjdAutoRecover(isDjdAutoRecover);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("133");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 分页
	     * @Title: examInfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examInfoList()  throws WebException {
		String exam_num=this.model.getExam_num();
		PageReturnDTO list = this.examInfoService.queryPageExamInfo(exam_num, rows, page);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 未检项目列表
	     * @Title: getWjxmExamList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getWjxmExamList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminfoChargingItemDTO> list = this.examInfoService.queryWjxmExamInfo(this.model.getExam_num(), user.getCenter_num());
		
		String examEndItemSeq = this.customerInfoService.getCenterconfigByKey("EXAM_END_ITEM_SEQ", user.getCenter_num()).getConfig_value();
		String[] ids = examEndItemSeq.split(",");
		List<ExaminfoChargingItemDTO> temp = new ArrayList<ExaminfoChargingItemDTO>();
		for (int i = 0; i < ids.length; i++) {
			if(StringUtil.isDouble(ids[i])){
				for (ExaminfoChargingItemDTO itemDto : list) {
					if(itemDto.getCharge_item_id() == Long.valueOf(ids[i])){
						itemDto.setIsActive("yes");
						temp.add(itemDto);
					}
				}
			}
		}
		list.removeAll(temp);
		temp.addAll(list);
		this.outJsonResult(temp);
		return NONE;
	}
	
	@SuppressWarnings("rawtypes")
	public String getHfqxExamList() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List list = this.examInfoService.queryQjxmExamInfo(this.model.getExam_num(), user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
		
		
		
		
	}
	public String isUniqueExam() throws WebException{
		/*Examinatioin_center exam = this.examinatioin_centerService.queryByNum(model.getCenter_num());
		if(exam==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);*/
		return NONE;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	/**
	 * 
	     * @Title: deleteExam   
	     * @Description: TODO(导检单弃简)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteExam() throws WebException{
		ExaminfoChargingItem exif = this.examInfoService.loadExamInfo(this.model.getId());
		exif.setExam_status("G");
		this.examInfoService.deleteExam(exif);
		this.message="已放弃！";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("632");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateExam   
	     * @Description: TODO(恢复导检单134)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateExam() throws WebException{
		ExaminfoChargingItem exif = this.examInfoService.loadExamInfo(this.model.getId());
		exif.setExam_status("N");
		this.examInfoService.updateExam(exif);
		this.message="已恢复！";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("134");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: receiveExamInfo   
	     * @Description: TODO(回收导引单198)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  receiveExamInfo() throws WebException{
		ExamInfo examinfo = this.examInfoService.findExamInfo(model.getExam_num());
		examinfo.setIs_guide_back("Y");
		if(examinfo.getJoin_date() == null || "".equals(examinfo.getJoin_date())){
			examinfo.setJoin_date(DateTimeUtil.getDateTime());
			examinfo.setStatus("J");
		}
		examInfoService.receiveExamInfo(examinfo);
		this.message="导引单回收成功！";
		ExamInfoRecyclingGuid guid = new ExamInfoRecyclingGuid();//添加导简人
		UserDTO user=(UserDTO) session.get("username");
		guid.setCreater(user.getUserid());//创建人
		guid.setCreate_time(DateTimeUtil.parse());//创建时间
		guid.setExam_info_id(model.getId());//创建id
		guid.setExam_num(model.getExam_num());
		this.examInfoRecyclingGuidService.addExamInfoRecyclingGuidService(guid);;
		this.outJsonStrResult(this.message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("198");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateBatchDeletExamInfo   
	     * @Description: TODO(批量弃检199)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String updateBatchDeletExamInfo() throws WebException{
		this.examInfoService.updateBatchDeletExamInfo(this.ids);
		this.message="已放弃";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("199");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: plhf   
	     * @Description: TODO(批量回复导引单500)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String plhf() throws WebException{
		this.examInfoService.plhf(this.ids);
		this.message="已恢复";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("500");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateyanqi   
	     * @Description: TODO(批量延期501)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateyanqi() throws WebException{
		this.examInfoService.updateplyq(this.date,ids);
		this.message="已延期";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("501");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: getyanqi   
	     * @Description: TODO(延期时间页面)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getyanqi() throws WebException{
		this.setIds(this.getIds());
		return this.SUCCESS;
	}
	/**
	 * 
	     * @Title: examInfoPage   
	     * @Description: TODO(添加修改人员页面503)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String examInfoPage() throws WebException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getexamInfo   
	     * @Description: TODO(人员列表504)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getexamInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ExamInfoDTO info = new ExamInfoDTO();
		info.setArch_num(model.getArch_num());//档案编码
		info.setExam_num(model.getExam_num());//体检编码
		info.setUser_name(model.getUser_name());//名称
		info.setPhone(model.getPhone());//手机
		info.setId_num(model.getId_num());//身份证
		info.setCompany(model.getCompany());//公司
		info.setRen_type(model.getRen_type());
		info.setStart_date(model.getStart_date());
		info.setEnd_date(model.getEnd_date());
		info.setIs_Active(model.getIs_Active());
		PageReturnDTO dto = null;
		if("".equals(model.getArch_num()) && "".equals(model.getExam_num()) && "".equals(model.getIs_Active()) && 
				"".equals(model.getUser_name()) && "".equals(model.getPhone()) && "".equals(model.getId_num())
				 && "".equals(model.getCompany()) && model.getRen_type()<1 && "".equals(model.getStart_date())&& model.getStart_date()!=null&& "".equals(model.getEnd_date())&& model.getEnd_date()!=null){
			
			dto = new PageReturnDTO();
		} else {
			dto =this.examInfoService.getExamInfo(info,rows,page,user);
			
		}
		this.outJsonResult(dto);
	    return NONE;
	}
	/**
	 * 
	     * @Title: updateExamInfo   
	     * @Description: TODO(删除/恢复人员505)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateExamInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		//查询是否封帐
		BatchDTO bdto = this.batchService.queryFromBatchExamnumOrExamid(model.getExam_num(),user.getCenter_num());
		if (bdto != null) {
			if(bdto.getAccountflag()==1) {
				this.message = "error-批次任务已经锁定";
				this.outJsonStrResult(this.message);
				return NONE;
			}
			if ("1".equals(bdto.getOverflag())) {
				this.message = "error-批次任务已经封帐";
				this.outJsonStrResult(this.message);
				return NONE;
			}
		}
		//查询该人员是否结算
		TeamAccountExamListDTO teamDto = teamAccountService.queryTeamAccountExamListFromExamid(model.getId(),model.getExam_num());
		if (teamDto!=null && "Y".equals(teamDto.getIsPrePay())) {
			this.message = "error-该体检人员已结算";
			this.outJsonStrResult(this.message);
			return NONE;
		}
		
		ExamInfoDTO dto = new ExamInfoDTO();
		dto.setId(model.getId());
		dto.setIs_Active(model.getIs_Active());
		dto.setUpdater(user.getUserid());
		dto.setExam_num(model.getExam_num());
		int a = this.examInfoService.updateExamInfo(dto);
		if(a==1){
			if(model.getIs_Active().equals("Y")){
				this.message="ok-已成功恢复";
			}else{
				//是否启用排队接口
				String is_queue_check = this.customerInfoService.getCenterconfigByKey("IS_QUEUE_CHECK", user.getCenter_num()).getConfig_value().trim();
				if("Y".equals(is_queue_check)){
					QueueCustomerBean bean = new QueueCustomerBean();
					bean.setCenter_num(user.getCenter_num());
					bean.setExam_num(model.getExam_num());
					QueueCustomSendMessage message = new QueueCustomSendMessage();
					message.Send(bean);
				}
				this.message="ok-已成功删除";
			}
		}else{
			this.message="error-操作失败";
		}
		this.outJsonStrResult(message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("501");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 
	     * @Title: revocationDYD   
	     * @Description: TODO(撤销导简单)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String revocationDYD() throws WebException{
			this.examInfoService.revocationDYD(model.getExam_num());
			this.message = "撤销成功！";
			this.outJsonStrResult(this.message);
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("558");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 保存选择报告类型
	     * @Title: saveExamInfoReportType   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamInfoReportType() throws WebException{
		this.examInfoService.saveExamInfoReportType(this.model.getId(), this.model.getReprot_type());
		this.message = "保存成功！";
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public  String  addDJDimage(){
		UserDTO user = (UserDTO) session.get("username");
		int st = customerInfoService.addDJDimage(user.getUserid(),this.model.getDJD_path(),this.model.getExam_num());
		this.message=st+"";
		this.outJsonStrResult(this.message);
		return  NONE;
	}
	
	public String showDJDImage(){
		return  SUCCESS;
	}
	
	/**
	 * 人员删除之前验证
	     * @Title: delExamInfoCheck   
	     * @Description: TODO()   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String  delExamInfoCheck(){
		this.message= customerInfoService.delExamInfoCheck(this.model);
		this.outJsonStrResult(this.message);
		return NONE;
	}
}