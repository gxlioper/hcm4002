package com.hjw.wst.action;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fr.general.Inter;
import com.fr.third.org.hsqldb.lib.StringUtil;
import com.hjw.util.DateTimeUtil;
import com.hjw.webService.client.HISDataSynchronizingMessage;
import com.hjw.webService.client.body.ResultHisBody;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.HisClinicItemPriceListDTO;
import com.hjw.wst.DTO.HisDictDeptDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.SampleReportDemoDto;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebserviceConfigurationDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ChargingItemExamItem;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.LimitChargingItem;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebSynchro;
import com.hjw.wst.model.ChargingItemModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebSynchroService;
import com.hjw.wst.service.WebserviceConfigurationService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: 收费项目     
     * @date:   2016年9月25日 下午7:12:37   queryChargingItem
     * @version V2.0.0.0
 */
public class ChargingItemAction  extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 1L;
	private ChargingItemModel model = new ChargingItemModel();
	private ChargingItemService chargingItemService;
	private BatchService batchService;
	private int page=1;
	private int rows=15;
	private String ids;
	//private String jid;//已选择收费项目id
	private String bianma;//编码验证，编码
	private String zhi;//编码验证值
	private SyslogService syslogService; 
	private String ks="";
	private String exam_item_id="";
	private String addhisnum = "";
	private WebSynchroService webSynchroService;
	private WebserviceConfigurationService webserviceConfigurationService;
    private DepartmentService departmentService;
    private CustomerInfoService customerInfoService;
	
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	public String getAddhisnum() {
		return addhisnum;
	}
	public void setAddhisnum(String addhisnum) {
		this.addhisnum = addhisnum;
	}
	public WebSynchroService getWebSynchroService() {
		return webSynchroService;
	}
	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}
	public String getExam_item_id() {
		return exam_item_id;
	}
	public void setExam_item_id(String exam_item_id) {
		this.exam_item_id = exam_item_id;
	}
	public String getKs() {
		return ks;
	}
	public void setKs(String ks) {
		this.ks = ks;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setWebserviceConfigurationService(WebserviceConfigurationService webserviceConfigurationService) {
		this.webserviceConfigurationService = webserviceConfigurationService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	/**
	 * 
	     * @Title: queryChargingItemPage   
	     * @Description: TODO(收费项目管理页面178)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String queryChargingItemPage() throws WebException{
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("178");//子功能id 必须填写
		sl.setExplain("收费项目tab");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: queryChargingItem   
	     * @Description: TODO(收费项目列表&&检索179)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String queryChargingItem() throws WebException{
		ChargingItemDTO cha = new ChargingItemDTO();
		cha.setItem_code(model.getItem_code());//收费项目ID
		cha.setItem_name(model.getItem_name());//收费项目名称
		cha.setView_num(model.getView_num());//影像编码
		cha.setExam_num(model.getExam_num());//检验编码
		cha.setHis_num(model.getHis_num());//his编码
		cha.setDep_id(model.getDep_id());//所属科室
		cha.setPerform_dept(model.getPerform_dept());;//执行科室id
		cha.setInterface_flag(model.getInterface_flag());//接口标识
		cha.setGuide_category(model.getGuide_category());//导引单分类
		cha.setMccf(model.getMccf());//名称重复
		cha.setBaogaochongfu(model.getBaogaochongfu());//报告重复
		cha.setItem_pinyin(model.getItem_pinyin());
		cha.setItem_name_s(model.getItem_name_s());
		this.defaultapp = (SystemType)session.get("defaultapp");			
		cha.setD_app(model.getD_app());
		cha.setStartStop(model.getStartStop());
		cha.setDemo_type(model.getDemo_type());
		cha.setIsActive(model.getIsActive());
//		System.out.println(cha.getD_app());
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO	pageDTO = this.chargingItemService.queryChargingItem(cha,rows,page,this.getKs(),this.exam_item_id,user.getCenter_num());
		this.outJsonResult(pageDTO);
		return NONE;
	}
	/**
	 * 
	     * @Title: getDepartment_dep   
	     * @Description: TODO(获取所有科室)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getDepartment_dep() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		List<DepartmentDepDTO> li=this.chargingItemService.getDepartmentDep(user.getCenter_num());
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: getHisDictDept   
	     * @Description: TODO(获得执行科室下拉框)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getHisDictDept() throws WebException{
		List<HisDictDeptDTO> li=this.chargingItemService.getHisDictDept();
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: updateChargingItemDel   
	     * @Description: TODO(删除收费项目182)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateChargingItemDel() throws WebException, ServiceException, SQLException{
		this.chargingItemService.updateChargingItemDel(this.getIds());
		this.webSynchroService.updateWebSynchro(this.getIds(),'1');
		this.message="删除成功";
		this.outJsonStrResult(this.message);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("182");//子功能id 必须填写
		sl.setExplain("删除收费项目");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 
	     * @Title: getChargingIteId   
	     * @Description: TODO(验证是否已收费183)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getChargingIteId() throws WebException{
		ChargingItemExamItem t=this.chargingItemService.getChargingIteId(model.getId());
		if(t==null){
			this.message="ok";//可删除
		}else{
			this.message="on";//不可删除
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: addChargingItePage   
	     * @Description: TODO(收费项目添加界面)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String addChargingItePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		getCenterConfiguration();
		model.setItem_code(this.batchService.GetCreateID("charge_item_code", user.getCenter_num()));
		this.defaultapp = (SystemType)session.get("defaultapp");
		model.setD_app(this.defaultapp.getComid());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: choiceChargingIte   
	     * @Description: TODO(已选择检查项目)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("unchecked")
	public String choiceChargingIte() throws WebException{
		model.setId(model.getId());;
		model.setItem_name(model.getItem_code());
		model.setItem_code(model.getItem_code());
		if(model!=null){
			@SuppressWarnings("rawtypes")
			Map session = ActionContext.getContext().getSession();
		       session.put("item",model);
		       ChargingItemModel   model=(ChargingItemModel) session.get("itme");
		       this.outJsonResult(model);
		}
		return NONE;
	}
	/**
	 * 
	     * @Title: getItemSampleDemo   
	     * @Description: TODO(获取所有检验样本185)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getItemSampleDemo() throws WebException{
		List<SampleDemoDTO> demo=this.chargingItemService.getItemSampleDemoByDemoType(model.getDemo_type());
		List<SampleDemoDTO> li=new ArrayList<SampleDemoDTO>();
		SampleDemoDTO sa = new SampleDemoDTO();
		sa.setDemo_name("请选择");
		sa.setId(0L);
		li.add(sa);
		for (SampleDemoDTO d : demo) {
			SampleDemoDTO dto = new SampleDemoDTO();
			dto.setId(d.getId());
			dto.setDemo_name(d.getDemo_name());
			li.add(dto);
		}
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: getItemSampleReportDemo   
	     * @Description: TODO(获取所有报告样本186)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getItemSampleReportDemo() throws WebException{
		List<SampleReportDemoDto> li = this.chargingItemService.getItemSampleReportDemo();//获取所有报告样本
		List<SampleReportDemoDto> ss = new ArrayList<SampleReportDemoDto>();
		SampleReportDemoDto dto = new SampleReportDemoDto();
		dto.setDemo_name("无");
		ss.add(dto);
		for (SampleReportDemoDto d : li) {
			SampleReportDemoDto tt = new SampleReportDemoDto();
			tt.setId(d.getId());
			tt.setDemo_name(d.getDemo_name());
			ss.add(tt);
		}
		this.outJsonResult(ss);
		return NONE;
	}
	/**
	 * @throws SQLException 
	 * @throws ServiceException 
	 * 
	     * @Title: addChargingItem   
	     * @Description: TODO(添加收费项目187)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String addChargingItem() throws WebException, ServiceException, SQLException{
		if(model.getId()>0){
		ChargingItem i=this.chargingItemService.findClass(model.getId());
			i.setId(model.getId());//收费项目id
			i.setItem_code(model.getItem_code());//收费项目编码
			i.setItem_name(model.getItem_name());//收费项目名称
			i.setItem_pinyin(model.getItem_pinyin());//收费项目拼音
			i.setItem_category(model.getItem_category());//收费项目类型--普通/耗材类型
			i.setGuide_category(model.getGuide_category());//导引单
			i.setSex(model.getSex());//收费项目试用性别---性别
			i.setAmount(model.getAmount());//金额
			i.setHis_num(model.getHis_num());//his系统编码
			i.setCalculation_amount(model.getCalculation_amount());//核算金额
			i.setCalculation_rate(model.getCalculation_rate());
			i.setExam_num(model.getExam_num());//检验系统编码
			i.setItem_abbreviation(model.getItem_abbreviation());//简称
			i.setItem_seq(model.getItem_seq());//顺序码
			i.setView_num(model.getView_num());//影像系统编码
			i.setDep_id(model.getDep_id());//所属科室
			i.setDep_category(model.getDep_category());//所属统计科室
			i.setIsOnlyApplyOrReport(model.getIsOnlyApplyOrReport());//生成独立报告
			i.setRemark(model.getRemark());
			i.setFinance_class(model.getFinance_class());//财务类别
			i.setCharging_item_number(model.getCharging_item_number());
			if(model.getCalculation_rate()>100){
				this.message="error-利润率不能大于100";
				this.outJsonStrResult(message);
				return NONE;
			}else{
				if(model.getCalculation_rate()==0){
					DepartmentDep dep = this.departmentService.loadDepartmentDep(this.model.getDep_id());
					i.setCalculation_rate(dep.getCalculation_rate());
				}else{
					i.setCalculation_rate(model.getCalculation_rate());
				}
			}
			if(model.getCalculation_amount() == null || model.getCalculation_amount()<=0){
				BigDecimal bd = new BigDecimal(0);
				bd = new BigDecimal(model.getAmount()*i.getCalculation_rate()/100);
				double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				i.setCalculation_amount(tamt);
			}
//			i.setOn_off_schedule(model.getOn_off_schedule());
//			if("Y".equals(model.getOn_off_schedule())){
//				i.setSchedule_start_time(DateTimeUtil.parse2(model.getSchedule_start_time()));
//				i.setSchedule_end_time(DateTimeUtil.parse2(model.getSchedule_end_time()));
//				i.setSchedule_number(model.getSchedule_number());
//			} 
			if(model.getSam_demo_id() > 0){
				i.setSam_demo_id(model.getSam_demo_id());//所属检验样本
			}else{
				long a= 0;
				i.setSam_demo_id(a);
			}
			if(model.getSam_report_demo_id()>0){
				i.setSam_report_demo_id(model.getSam_report_demo_id());//所属报告样本
			}else{
				Long b=null;
				i.setSam_report_demo_id(b);//所属报告样本
			}
			i.setInterface_flag(model.getInterface_flag());//接口标识
			i.setItem_type(model.getItem_type());//项目类别
			i.setCharge_inter_num(model.getCharge_inter_num());//系统外编码
			i.setPerform_dept(model.getPerform_dept());//His执行科室编码
			i.setItem_note(model.getItem_note());//项目描述
			i.setIsActive("Y");//状态
			UserDTO user=(UserDTO) session.get("username");//获取用户
			i.setUpdater(user.getUserid());//修改人
			i.setUpdate_time(DateTimeUtil.parse());//修改时间
			i.setItem_class(model.getItem_class());
			i.setItem_discount(model.getItem_discount()); //项目折扣率
			this.chargingItemService.updateChargingItem(i,model,user);
			String IS_CHANGE_SET_PRICE = this.customerInfoService.getCenterconfigByKey("IS_CHANGE_SET_PRICE", user.getCenter_num()).getConfig_value();
			if("Y".equals(IS_CHANGE_SET_PRICE)) {
				this.chargingItemService.updateSetChargingItem(i);
			}
			this.message="ok-修改成功";
			this.outJsonStrResult(message);
			this.webSynchroService.updateWebSynchro(model.getId()+"",'1');
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("187");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
		}else{
			ChargingItem i = new ChargingItem();
			i.setItem_code(model.getItem_code());//收费项目编码
			i.setItem_name(model.getItem_name());//收费项目名称
			i.setItem_pinyin(model.getItem_pinyin());//收费项目拼音
			i.setItem_category(model.getItem_category());//收费项目类型--普通/耗材类型
			i.setGuide_category(model.getGuide_category());//导引单
			i.setSex(model.getSex());//收费项目试用性别---性别
			i.setAmount(model.getAmount());//金额
			i.setHis_num(model.getHis_num());//his系统编码
			i.setCalculation_amount(model.getCalculation_amount());//核算金额
			i.setCalculation_rate(model.getCalculation_rate());
			i.setExam_num(model.getExam_num());//检验系统编码
			i.setItem_abbreviation(model.getItem_abbreviation());//简称
			i.setItem_seq(model.getItem_seq());//顺序码
			i.setView_num(model.getView_num());//影像系统编码
			i.setDep_id(model.getDep_id());//所属科室
			i.setDep_category(model.getDep_category());//所属统计科室
			i.setIsOnlyApplyOrReport(model.getIsOnlyApplyOrReport());//生成独立报告
			i.setRemark(model.getRemark());
			i.setFinance_class(model.getFinance_class());
			if(model.getCalculation_rate()>100){
				this.message="error-利润率不能大于100";
				this.outJsonStrResult(message);
				return NONE;
			}else{
				if(model.getCalculation_rate()==0){
					DepartmentDep dep = this.departmentService.loadDepartmentDep(this.model.getDep_id());
					i.setCalculation_rate(dep.getCalculation_rate());
				}else{
					i.setCalculation_rate(model.getCalculation_rate());
				}
			}
			if(model.getCalculation_amount() == null || model.getCalculation_amount()<=0){
				BigDecimal bd = new BigDecimal(0);
				bd = new BigDecimal(model.getAmount()*i.getCalculation_rate()/100);
				double tamt = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				i.setCalculation_amount(tamt);
			}
//			i.setOn_off_schedule(model.getOn_off_schedule());
//			if("Y".equals(model.getOn_off_schedule())){
//				i.setSchedule_start_time(DateTimeUtil.parse2(model.getSchedule_end_time()));
//				i.setSchedule_end_time(DateTimeUtil.parse2(model.getSchedule_start_time()));
//				i.setSchedule_number(model.getSchedule_number());
//			} 
			if(model.getSam_demo_id()>0){
				i.setSam_demo_id(model.getSam_demo_id());//所属检验样本
			}else{
				Long f=null;
				i.setSam_demo_id(f);
			}
			if(model.getSam_report_demo_id()>0){
				i.setSam_report_demo_id(model.getSam_report_demo_id());//所属报告样本
			}else{
				Long g=null;
				i.setSam_report_demo_id(g);
			}
			i.setInterface_flag(model.getInterface_flag());//接口标识
			i.setItem_type(model.getItem_type());//项目类别
			i.setCharge_inter_num(model.getCharge_inter_num());//系统外编码
			i.setPerform_dept(model.getPerform_dept());//His执行科室编码
			i.setItem_note(model.getItem_note());//项目描述
			i.setIsActive("Y");//状态
			UserDTO user=(UserDTO) session.get("username");//获取用户
			i.setCreater(user.getUserid());//创建人
			i.setCreate_time(DateTimeUtil.parse());//创建时间
			i.setItem_class(model.getItem_class());//HIS诊疗项目类别
			i.setCharging_item_number(model.getCharging_item_number());//限制次数
			i.setItem_discount(model.getItem_discount()); //项目折扣率
			
			this.chargingItemService.addChargingIte(i,model,user);//执行收费添加
			this.webSynchroService.updateWebSynchro(i.getId()+"",'1');
			this.message="ok-添加成功";
			this.outJsonStrResult(message);
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("187");//子功能id 必须填写
			sl.setExplain("添加收费项目");//操作说明
			syslogService.saveSysLog(sl);
			
		}
		return NONE;
	}
	/**
	 * 
	     * @Title: updateChargingItemPge   
	     * @Description: TODO(修改页面188)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String updateChargingItemPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ChargingItem  it =this.chargingItemService.findChargingItem(model.getId());
		this.setPage(this.getPage());
		model.setId(it.getId());
		model.setItem_code(it.getItem_code());//编码
		model.setItem_name(it.getItem_name());//名称
		model.setItem_pinyin(it.getItem_pinyin());//拼音
		model.setItem_category(it.getItem_category());//项目类型，普通，耗材
		model.setGuide_category(it.getGuide_category());//导引单
		model.setSex(it.getSex());//性别
		model.setAmount(it.getAmount());//金额
		model.setHis_num(it.getHis_num());//his系统编码
		model.setCalculation_amount(it.getCalculation_amount());//核算金额
		model.setCalculation_rate(it.getCalculation_rate());
		model.setExam_num(it.getExam_num());//检验系统编码
		model.setItem_abbreviation(it.getItem_abbreviation());//项目简称
		model.setItem_seq(it.getItem_seq());//顺序码
		model.setView_num(it.getView_num());//影像系统编码
		model.setDep_id(it.getDep_id());//所属科室
		model.setDep_category(it.getDep_category());//所属统计科室
		model.setIsOnlyApplyOrReport(it.getIsOnlyApplyOrReport());//产生独立的检验样本及报告
		model.setSam_demo_id((it.getSam_demo_id()==null) ? (0) : (it.getSam_demo_id()));//检验样本
		model.setSam_report_demo_id(it.getSam_report_demo_id());//报告样本
		model.setInterface_flag(it.getInterface_flag());//接口标识
		model.setItem_type(it.getItem_type());//项目类别
		model.setCharge_inter_num(it.getCharge_inter_num());//系统外编码
		model.setPerform_dept(it.getPerform_dept());//his执行科室编码
		model.setItem_note(it.getItem_note());//收费项目描述
		model.setRemark(it.getRemark());
		model.setFinance_class(it.getFinance_class());//财务类别
		model.setCharging_item_number(it.getCharging_item_number());
//		model.setOn_off_schedule(it.getOn_off_schedule());
//		if("Y".equals(it.getOn_off_schedule())){
//			model.setSchedule_end_time(DateTimeUtil.shortFmt3(it.getSchedule_end_time()));
//			model.setSchedule_start_time(DateTimeUtil.shortFmt3(it.getSchedule_start_time()));
//			model.setSchedule_number(it.getSchedule_number());
//		}
		String hisitemname=it.getHis_num();//his系统编码
		this.model.setHis_num_show(hisitemname);
		
		LimitChargingItem li = this.chargingItemService.getlimitChargingItem(it.getId(),it,user);
		if( li!=null){
			model.setLimit_count_s(li.getLimit_count()+"");
		}

		String item_class_cs="";
		
		if("1".equals(it.getHiscodeClass())){
			item_class_cs=this.chargingItemService.getItemClassName(it.getItem_class(),"HISJBLB");
			item_class_cs="价-"+item_class_cs;
		}else if("2".equals(it.getHiscodeClass())){
			item_class_cs=this.chargingItemService.getItemClassName(it.getItem_class(),"HISZLLB");
			item_class_cs="诊-"+item_class_cs;
		}
		model.setItem_class_cs(item_class_cs);//显示
		model.setItem_class_c(it.getItem_class());//类别A,B,C,D
		model.setItem_discount(it.getItem_discount());
		getCenterConfiguration();
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getChargingItemExamItem   
	     * @Description: TODO(根据收费项目编码获取已选择检查项目)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingItemExamItem() throws WebException{
		List<ExaminationItemDTO> li= new ArrayList<ExaminationItemDTO>();
		if(model.getId()>0){
			li =this.chargingItemService.getChargingItemExamItem(model.getId());			
		}
		this.outJsonResult(li);
		return  NONE;
	}
	/**
	 * 
	     * @Title: lianDong   
	     * @Description: TODO(科室和类型联动)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String lianDong()  throws WebException{
		DepartmentDep dep = this.chargingItemService.getDep_categoryId(model.getId());
		String st="";
		if(dep!=null && dep.getId()>0){
		   st=dep.getDep_category();
		}
		this.outJsonStrResult(st);
		return NONE;
	}

	public String getBMYZ()  throws WebException{
		if(!zhi.equals("")&&zhi!=null){
			long a = this.chargingItemService.getBMYZ(bianma,zhi);
			//编码1可用，0不可用
			if(a==1){
				this.message="ok";
			}else{
				this.message="no";
			}
		}else{
			this.message="ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	public String centerBMYZ()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(!zhi.equals("")&&zhi!=null){
			long a = this.chargingItemService.centerBMYZ(bianma,zhi,user.getCenter_num());
			//编码1可用，0不可用
			if(a==1){
				this.message="ok";
			}else{
				this.message="no";
			}
		}else{
			this.message="ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	public String CenterBMYZ()  throws WebException{
		if(!zhi.equals("")&&zhi!=null){
			long a = this.chargingItemService.getBMYZ(bianma,zhi);
			//编码1可用，0不可用
			if(a==1){
				this.message="ok";
			}else{
				this.message="no";
			}
		}else{
			this.message="ok";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: getHisClinicItemVPricelist   
	     * @Description: TODO(HIs获取诊疗项目表520)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHisClinicItemVPricelist()  throws WebException{
		HisClinicItemPriceListDTO  dto = new HisClinicItemPriceListDTO();
		dto.setCharge_item_code(model.getCharge_item_code());
		dto.setItem_code_c(model.getItem_code_c());
		dto.setItem_name_c(model.getItem_name_c());
		PageReturnDTO his =this.chargingItemService.getHisClinic(dto,page,rows);
		this.outJsonResult(his);
		return NONE;
	}
	/**
	 * 
	     * @Title: getHisPreci   
	     * @Description: TODO(获取HIS诊疗价表522)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHisPreci()  throws WebException{
		HisClinicItemPriceListDTO  dto = new HisClinicItemPriceListDTO();
		dto.setClinic_item_code(model.getCharge_item_code());
		dto.setSystemdate(DateTimeUtil.getDate());//系统时间
		List<HisClinicItemPriceListDTO> his =this.chargingItemService.getHisjg(dto);
		this.outJsonResult(his);
		return  NONE;
	}
	/**
	 * 
	     * @Title: getHisClinicItemVPricelistPage   
	     * @Description: TODO(His诊疗项目价表页面521)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHisClinicItemVPricelistPage()   throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.setAddhisnum(this.getAddhisnum());
		String IS_HIS_UNRELATED_SHOW="Y";
        try{
        	
        	CenterConfigurationDTO cf= new CenterConfigurationDTO();
    		cf = this.customerInfoService.getCenterconfigByKey("IS_HIS_UNRELATED_SHOW", user.getCenter_num());
    		IS_HIS_UNRELATED_SHOW=cf.getConfig_value().trim().toUpperCase();
        }catch(Exception ex){}
		
		if("Y".equals(IS_HIS_UNRELATED_SHOW.trim().toUpperCase())){
				return "success_comm";
			}else{
				return "success_unrelated";
			}
	}
	/**
	 * 
	     * @Title: getPriceZJ   
	     * @Description: TODO(计算his价表项目对应总价523)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPriceZJ()   throws WebException{
		double a= this.chargingItemService.getHisPriceS(model.getItem_code_c(),model.getItem_class_c());
		String f=""+a;
		this.outJsonStrResult(f);
		return NONE;
	}
	/**
	 * 
	     * @Title: get   
	     * @Description: TODO(收费项目新增-科室联动524)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getKSLD() throws WebException{
		List<DepartmentDepDTO>	dto=this.chargingItemService.getkbld(model.getId());
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: getds   
	     * @Description: TODO(获取HIS,lis,pacs,接口发送申请状态525)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCenterConfiguration() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		model.setLisStatus(this.customerInfoService.getCenterconfigByKey("IS_LIS_INTERFACE", user.getCenter_num()).getConfig_value());
		model.setHisStatus(this.customerInfoService.getCenterconfigByKey("IS_HIS_INTERFACE", user.getCenter_num()).getConfig_value());
		model.setPacsStatus(this.customerInfoService.getCenterconfigByKey("IS_PACS_INTERFACE", user.getCenter_num()).getConfig_value());
		return NONE;
	}
	/**
	 * Lis检验项目页面910
	     * @Title: getThridLis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getThridLis() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("910");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return SUCCESS;
	}
	/**
	 * 检验项目911
	     * @Title: getThridLisClassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getThridLisClassList() throws WebException{
		PageReturnDTO  dto = this.chargingItemService.getThridLisClassList(model, page, pagesize);
		this.outJsonResult(dto);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("911");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("0");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 检验项目细项912
	     * @Title: getThridLisItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getThridLisItemList() throws WebException{
		List<ThridLisItemDTO>  li = this.chargingItemService.getThridLisItemList(model);
		this.outJsonResult(li);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("912");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 
	 * 同步his价格表913
	     * @Title: updateHIsPriceSynchro   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateHIsPriceSynchro()  throws WebException{
	
		if(	this.chargingItemService.updateHIsPriceSynchro(model)){
			this.message = "操作失败";
		} else {
			this.message = "同步成功";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * HIS数据字典同步
	     * @Title: hisDataSynchronizing   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String hisDataSynchronizing() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		WebserviceConfigurationDTO wcf=new WebserviceConfigurationDTO();
		wcf=this.webserviceConfigurationService.getWebServiceConfig("HIS_DATA_APPLICATION", user.getCenter_num());
		String web_url = wcf.getConfig_url().trim();
		String web_meth= wcf.getConfig_method().trim();
		HISDataSynchronizingMessage hisData = new HISDataSynchronizingMessage();
		ResultHisBody rb = hisData.dataSend(web_url, web_meth, false);
		if("AA".equals(rb.getResultHeader().getTypeCode())){
			this.message = "ok-"+rb.getResultHeader().getText();
		}else{
			this.message = "error-"+rb.getResultHeader().getText();
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 项目排期查询数据945
	     * @Title: getItemScheduleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemScheduleList() throws WebException{
		if(model.getSchedule_time()==null || "".equals(model.getSchedule_time())){
			model.setSchedule_time(DateTimeUtil.getDate2());
		}
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto  = this.chargingItemService.getItemSchedule(model, user.getCenter_num(), page, this.pagesize);
		this.outJsonResult(dto);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("945");//子功能id 必须填写
		sl.setExplain("项目排期查询");//操作说明
		return NONE;
	}
	public String queryChargingItemPageTab() throws WebException{
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("178");//子功能id 必须填写
		sl.setExplain("收费项目");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getItemschdeulChajian   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemschdeulChajian() throws WebException {
		setIds(this.getIds());
		model.setSchedule_time(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	/**
	 * 保存项目排期947
	     * @Title: getItemschdeulChajian   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveItemschdeul() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.chargingItemService.saveItemSchedule(this.model.getSchedule_time(),this.getIds(), user, this.model.getSchedule_number());
		this.outJsonStrResult("保存成功！");
		return NONE;
	}
	/**
	 * 修改排期948
	     * @Title: updateItemschdeul   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateItemschdeul() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.chargingItemService.updateItemSchedule(model,user);
		this.outJsonStrResult("修改成功");
		return NONE;
	}
	/**
	 * 单个排期修改页面949
	 * @Title: updateItemschdeul   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @return
	 * @param: @throws WebException      
	 * @return: String      
	 * @throws
	 */
	public String updateItemschdeulPage() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.model.setId(this.model.getId());
		this.model.setSchedule_time(this.model.getSchedule_time());
		this.model.setSchedule_number(this.model.getSchedule_number());
		return SUCCESS;
	}
	/**
	 * 删除排期项目950
	     * @Title: deleteItemschdeulPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteItemschdeulPage() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		this.chargingItemService.deleteItemSchedule(this.model);
		this.outJsonStrResult("删除成功！");
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updateChargingItemStopOrStart   
	     * @Description: TODO(启用停用该收费项目)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateChargingItemStopOrStart()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		try {
			ChargingItem am = this.chargingItemService.findChargingItem(Long.valueOf(ids));
			if(am.getIsActive().equals("Y")){
				am.setIsActive("N");
			    am.setUpdate_time(DateTimeUtil.parse());
			    am.setUpdater(user.getUserid());
			}else if(am.getIsActive().equals("N")){
				am.setIsActive("Y");
				am.setUpdate_time(DateTimeUtil.parse());
				am.setUpdater(user.getUserid());
			}
			this.chargingItemService.updateChargingItemStopOrStart(am);
			this.webSynchroService.updateWebSynchro(ids+"",'1');
			this.message="ok-成功";
		} catch (Exception e) {
			this.message="error-"+e.toString();
			e.printStackTrace();
		}

		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getPriceChingItem   
	     * @Description: 更新收费项目his价表   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getPriceChingItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
			if (this.model.getId() <= 0) {
				message = "error-无效收费项目";
			} else if (StringUtil.isEmpty(this.model.getItem_code_c())) {
				message = "error-无效His项目类型";
			} else if (this.model.getAmount() < 0) {
				message = "error-无效His项目价格";
			} else if (StringUtil.isEmpty(this.model.getRemark())) {
				message = "error-无效His类别代码";
			} else {
				
				HisClinicItemPriceListDTO dto = new HisClinicItemPriceListDTO();
				dto.setId(this.model.getId());
				dto.setDamount(this.model.getAmount());
				dto.setCharge_item_class(this.model.getItem_class_c());
				dto.setCharge_item_code(this.model.getItem_code_c());
				dto.setHisCode_class(this.model.getRemark());
				message = this.chargingItemService.updateHisCharginigItem(dto, user.getUserid());
			}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getHisClinicItemVPricelist   
	     * @Description: TODO(HIs获取诊疗项目表520)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHisPricelist()throws WebException{
		HisClinicItemPriceListDTO  dto = new HisClinicItemPriceListDTO();
		dto.setCharge_item_code(model.getCharge_item_code());
		dto.setCharge_item_class(model.getItem_class_c());
		dto.setItem_code_c(model.getItem_code_c());
		dto.setItem_name_c(model.getItem_name_c());
		PageReturnDTO his =this.chargingItemService.getHisPriceList(dto,page,rows);
		this.outJsonResult(his);
		return NONE;
	}
	
	
	/*查询科室下的所有收费项目
	 * getChargingItemListByDepId
	     * <p>Title: getModel</p>   
	     * <p>Description: </p>   
	     * @return   
	     * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public  String  getChargingItemListByDepId(){
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp = (SystemType)session.get("defaultapp");
		List<ChargingItemDTO>  list =   chargingItemService.getChargingItemListByDepId(user.getDep_id(),this.defaultapp.getComid());
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 收费项目验证名称是否重复
	     * @Title: getItemNameCheck   
	     * @Description: TODO(HIs获取诊疗项目表520)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemNameCheck(){
		ChargingItem chargingItem = chargingItemService.getItemNameCheck(this.model);
		this.outJsonResult(chargingItem);
		return NONE;
	}
	/**
	 * -------------------------------------------多体检中心-----------------------------
	 * 多体检中心-收费项目维护2500
	     * @Title: ChargingItemVScenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String centerChargingItem(){
		return SUCCESS;
	}
	/**2501
	 * 多体检中心--科室关联收费项目页面
	     * @Title: centerDepItemCharging   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String centerDepItemCharging(){
		return SUCCESS;
	}
	/**
	 * 多体检中心-科室收费项目细项 2502
	     * @Title: centerDepItemChargingDetail   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String centerDepItemChargingDetail(){
		return SUCCESS;
	}
	/**
	 * 多体检中心-选择收费项目2503
	     * @Title: centerSeleteItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String centerSeleteItem(){
		UserDTO user = (UserDTO) session.get("username");
		String ok = chargingItemService.saveSeleteItem(model, user);
		this.outJsonStrResult(ok);
		return NONE;
	}
	/**
	 * 多体检中心-收费项目分页表格2504
	     * @Title: centerQueryChargingItem     2054
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String centerQueryChargingItem() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ChargingItemDTO cha = new ChargingItemDTO();
		cha.setItem_code(model.getItem_code());//收费项目ID
		cha.setItem_name(model.getItem_name());//收费项目名称
		cha.setView_num(model.getView_num());//影像编码
		cha.setExam_num(model.getExam_num());//检验编码
		cha.setHis_num(model.getHis_num());//his编码
		cha.setDep_id(model.getDep_id());//所属科室
		cha.setPerform_dept(model.getPerform_dept());;//执行科室id
		cha.setInterface_flag(model.getInterface_flag());//接口标识
		cha.setGuide_category(model.getGuide_category());//导引单分类
		cha.setMccf(model.getMccf());//名称重复
		cha.setBaogaochongfu(model.getBaogaochongfu());//报告重复
		cha.setItem_pinyin(model.getItem_pinyin());
		cha.setItem_name_s(model.getItem_name_s());
		this.defaultapp = (SystemType)session.get("defaultapp");			
		cha.setD_app(model.getD_app());
		cha.setStartStop(model.getStartStop());
		cha.setDemo_type(model.getDemo_type());
//		System.out.println(cha.getD_app());
		PageReturnDTO	pageDTO = this.chargingItemService.queryChargingItemCenter(cha,rows,page,this.getKs(),this.exam_item_id,user);
		this.outJsonResult(pageDTO);
		return NONE;
	}
	/**
	 * 多体检中心获取科室2505
	     * @Title: getCenterDepartmentList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getCenterDepartmentList(){
		UserDTO user = (UserDTO) session.get("username");
		List<DepartmentDepDTO> li = departmentService.getCenterDepartmentList(user);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 *  多体检中心收费项目2506
	     * @Title: bCenterCharging   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String bCenterCharging(){
		return SUCCESS;
	}
	
	/**
	 * 2507
	     * @Title: centerupdateChargingItemPage   
	     * @Description: TODO
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String centerupdateChargingItemPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ChargingItem  it =this.chargingItemService.findChargingItem(model.getId());
		model.setItem_code(it.getItem_code());
		ChargingItemDTO chargingItemVsCenter = chargingItemService.getchargingItemVsCenter(model,user);
		this.setPage(this.getPage());
		model.setPrice(chargingItemVsCenter.getPrice());
		model.setId(it.getId());
		model.setItem_code(it.getItem_code());//编码
		model.setItem_name(it.getItem_name());//名称
		model.setItem_pinyin(it.getItem_pinyin());//拼音
		model.setItem_category(it.getItem_category());//项目类型，普通，耗材
		model.setGuide_category(it.getGuide_category());//导引单
		model.setSex(it.getSex());//性别
		model.setAmount(chargingItemVsCenter.getCenter_price());//金额
		model.setHis_num(chargingItemVsCenter.getHis_num());//his系统编码
		model.setCalculation_amount(chargingItemVsCenter.getCalculation_amount());//核算金额
		model.setCalculation_rate(chargingItemVsCenter.getCalculation_rate());
		model.setExam_num(chargingItemVsCenter.getExam_num());//检验系统编码
		model.setItem_abbreviation(it.getItem_abbreviation());//项目简称
		model.setItem_seq(it.getItem_seq());//顺序码
		model.setView_num(chargingItemVsCenter.getView_num());//影像系统编码
		model.setDep_id(it.getDep_id());//所属科室
		model.setDep_category(it.getDep_category());//所属统计科室
		model.setIsOnlyApplyOrReport(it.getIsOnlyApplyOrReport());//产生独立的检验样本及报告
		model.setSam_demo_id((it.getSam_demo_id()==null) ? (0) : (it.getSam_demo_id()));//检验样本
		model.setSam_report_demo_id(it.getSam_report_demo_id());//报告样本
		model.setInterface_flag(chargingItemVsCenter.getInterface_flag());//接口标识
		model.setItem_type(it.getItem_type());//项目类别
		model.setCharge_inter_num(chargingItemVsCenter.getCharge_inter_num());//系统外编码
		model.setPerform_dept(chargingItemVsCenter.getPerform_dept());//his执行科室编码
		model.setItem_note(it.getItem_note());//收费项目描述
		model.setRemark(it.getRemark());
		model.setFinance_class(it.getFinance_class());//财务类别
		model.setCharging_item_number(chargingItemVsCenter.getCharging_item_number());
//		model.setOn_off_schedule(it.getOn_off_schedule());
//		if("Y".equals(it.getOn_off_schedule())){
//			model.setSchedule_end_time(DateTimeUtil.shortFmt3(it.getSchedule_end_time()));
//			model.setSchedule_start_time(DateTimeUtil.shortFmt3(it.getSchedule_start_time()));
//			model.setSchedule_number(it.getSchedule_number());
//		}
		String hisitemname=it.getHis_num();//his系统编码
		this.model.setHis_num_show(hisitemname);
		
		LimitChargingItem li = this.chargingItemService.getlimitChargingItem(it.getId(),it,user);
		if( li!=null){
			model.setLimit_count_s(li.getLimit_count()+"");
		}

		String item_class_cs="";
		
		if("1".equals(it.getHiscodeClass())){
			item_class_cs=this.chargingItemService.getItemClassName(it.getItem_class(),"HISJBLB");
			item_class_cs="价-"+item_class_cs;
		}else if("2".equals(it.getHiscodeClass())){
			item_class_cs=this.chargingItemService.getItemClassName(it.getItem_class(),"HISZLLB");
			item_class_cs="诊-"+item_class_cs;
		}
		model.setItem_class_cs(item_class_cs);//显示
		model.setItem_class_c(it.getItem_class());//类别A,B,C,D
		model.setItem_discount(chargingItemVsCenter.getItem_discount());
		getCenterConfiguration();
		return SUCCESS;
	}
	/**
	 * 多体检中心--本体检中心项目修改2508
	     * @Title: updateChargingItemCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateChargingItemCenter() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.chargingItemService.updateChargingItemCenter(model, user);
		this.outJsonStrResult("保存成功");
		return NONE;
	}
	/**
	 * 多体检-删除体检项目
	     * @Title: deleteChargingItemCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteChargingItemCenter() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.chargingItemService.deleteChargingItemCenter(model, user);
		this.outJsonStrResult("删除成功");
		return NONE;
	}
	
	@Override
	public Object getModel() {
		return model;
	}
	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
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
	public void setModel(ChargingItemModel model) {
		this.model = model;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}
	public String getZhi() {
		return zhi;
	}
	public void setZhi(String zhi) {
		this.zhi = zhi;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBianma() {
		return bianma;
	}
	public void setBianma(String bianma) {
		this.bianma = bianma;
	}
	
}
