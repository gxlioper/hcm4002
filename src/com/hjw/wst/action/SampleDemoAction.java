package com.hjw.wst.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.SampleDemo;
import com.hjw.wst.model.SampleDemoModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.SampleDemoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  检验样本
     * @author: 张瑞    
     * @date:   2016年9月23日 下午2:38:09   
     * @version V2.0.0.0
 */
public class SampleDemoAction extends BaseAction  implements ModelDriven{

	private static final long serialVersionUID = 9066731173191201583L;
	private SampleDemoModel model = new SampleDemoModel();
	private SampleDemoService sampleDemoService;
	private BatchService batchService;
	private  ChargingItemService  chargingItemService;
	private int page = 1; // 当前页
	private int rows =15; // easyui每页显示条数
	private String ids; 
	private SyslogService syslogService;  
	
	
	
	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}
	/**
	 * @throws SQLException 
	 * @throws WebException 
	 * @throws ServiceException 
	 * 
	     * @Title: querySampleDemo   
	     * @Description: TODO(检验样本列表&&检索161)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String querySampleDemo() throws ServiceException, WebException, SQLException{
		SampleDemo sampleDemo = new SampleDemo();
		sampleDemo.setDemo_name(model.getDemo_name());
		sampleDemo.setDemo_num(model.getDemo_num());
		sampleDemo.setDemo_category(model.getDemo_category());
		sampleDemo.setDemo_type(model.getDemo_type());
		PageReturnDTO pageReturnDTO=this.sampleDemoService.querySampleDemo(sampleDemo,rows,page,this.model.getStartStop());
		this.outJsonResult(pageReturnDTO);
		return this.NONE;
	}
	/**
	 * 
	     * @Title: SampleDemo   
	     * @Description: TODO(检查样本页面160)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String SampleDemo(){

		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("160");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return this.SUCCESS;
	}
	
	/**
	 * 
	     * @Title: deleteSampleDemo   
	     * @Description: TODO(删除检验样本162)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String deleteSampleDemo(){
		this.sampleDemoService.deleteSampleDemo(ids);
		this.message="删除成功";
		this.outJsonStrResult(message);

		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("162");//子功能id 必须填写
		sl.setExplain("删除检验样本");//操作说明
		syslogService.saveSysLog(sl);
		
		return this.NONE;
	}
	/**
	 * 
	     * @Title: addSampleDemo   
	     * @Description: TODO(检验样本添加页面163)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String addSampleDemo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		model.setDemo_num(this.batchService.GetCreateID("sample_no", user.getCenter_num()));
		return this.SUCCESS;
	}
	/**
	 * 
	     * @Title: getSampleDemoBynum   
	     * @Description: TODO(验证检验样本编号唯一164)   
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: String      
	     * @throws
	 */
	public String getSampleDemoBynum() throws IOException{
		SampleDemo sam = this.sampleDemoService.getSampleDemoBynum(model.getDemo_num());
		if(sam==null){
			//报告编号可用
			this.message="ok";
		}else{
			//找到了不可用
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: addSampleDemoMethod   
	     * @Description: TODO(添加检验样本165)   
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: String      
	     * @throws
	 */
	public String addSampleDemoMethod() throws IOException{
		if(model.getId()>0){
			SampleDemo sample = this.sampleDemoService.findClass(model.getId());
			if(sample!=null){
				//sample.setDemo_num(model.getDemo_num());				//样本编码
				sample.setDemo_name(model.getDemo_name());				//样本名称
				sample.setDemo_category(model.getDemo_category());		//样本类型
				sample.setDemo_indicator(model.getDemo_indicator());	//样本标志
				sample.setDemo_color(model.getDemo_color());			//样本颜色
				sample.setBarCode(model.getBarCode());   				//条码类型
				sample.setIsPrint_BarCode(model.getIsPrint_BarCode());  //是否打印条码1打印，0不打印
				sample.setPrint_seq(model.getPrint_seq());  			//打印顺序
				sample.setPrint_copy(model.getPrint_copy());   			//打印份数
				sample.setRemark(model.getRemark());   					//备注
				sample.setPrint_dep(model.getPrint_dep());
				UserDTO user=(UserDTO) session.get("username");			//获取当前用户
				sample.setUpdater(user.getUserid());
				sample.setUpdate_time(DateTimeUtil.parse());			//当前时间
				sample.setDemo_type(model.getDemo_type());               //所属分类
				//sample.setIsActive("Y");         						//状态Y激活
				sample.setReq_print_num(model.getReq_print_num());
				sample.setIsPrint_req(model.getIsPrint_req());
				this.sampleDemoService.updateSampleDemo(sample);
				this.message = "修改成功";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("165");//子功能id 必须填写
				sl.setExplain("修改检验样本");//操作说明
				syslogService.saveSysLog(sl);
		
			}
		}else{
			SampleDemo sample = new SampleDemo();
			sample.setDemo_num(model.getDemo_num());				//样本编码
			sample.setDemo_name(model.getDemo_name());				//样本名称
			sample.setDemo_category(model.getDemo_category());		//样本类型
			sample.setDemo_indicator(model.getDemo_indicator());	//样本标志
			sample.setDemo_color(model.getDemo_color());			//样本颜色
			sample.setBarCode(model.getBarCode());   				//条码类型
			sample.setIsPrint_BarCode(model.getIsPrint_BarCode());  //是否打印条码
			sample.setPrint_seq(model.getPrint_seq());  			//打印顺序
			sample.setPrint_copy(model.getPrint_copy());   			//打印份数
			sample.setPrint_dep(model.getPrint_dep());
			sample.setRemark(model.getRemark());   					//备注
			UserDTO user=(UserDTO) session.get("username");			//获取当前用户
			sample.setCreater(user.getUserid());
			sample.setCreate_time(DateTimeUtil.parse());			//当前时间
			sample.setIsActive("Y");         						//状态Y激活
			sample.setDemo_type(model.getDemo_type());               //所属分类
			sample.setReq_print_num(model.getReq_print_num());
			sample.setIsPrint_req(model.getIsPrint_req());
			this.sampleDemoService.addSampleDemo(sample);
			this.message = "添加成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("165");//子功能id 必须填写
			sl.setExplain("添加检验样本");//操作说明
			syslogService.saveSysLog(sl);
			
		}
		this.outJsonStrResult(message);
		return this.NONE;
	}
	/**
	 * 
	     * @Title: updateSampleDemo   
	     * @Description: TODO(修改检验样本页面166)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String updateSampleDemo(){
		SampleDemo sample = this.sampleDemoService.findClass(model.getId());
		if(sample!=null){
			model.setId(sample.getId());
			model.setDemo_num(sample.getDemo_num());				//样本编码
			model.setDemo_name(sample.getDemo_name());				//样本名称
			model.setDemo_category(sample.getDemo_category());		//样本类型
			model.setDemo_indicator(sample.getDemo_indicator());	//样本标志
			model.setBarCode(sample.getBarCode());					//条码类型
			model.setIsPrint_BarCode(sample.getIsPrint_BarCode());	//是否打印条码；1是，0否
			model.setDemo_color(sample.getDemo_color());			//样本颜色
			model.setPrint_seq(sample.getPrint_seq());  			//打印顺序
			model.setPrint_copy(sample.getPrint_copy());   			//打印份数
			model.setRemark(sample.getRemark()); 					//备注
			model.setPrint_dep(sample.getPrint_dep());
			model.setDemo_type(sample.getDemo_type());		//所属分类
			model.setReq_print_num(sample.getReq_print_num());//打印申请单次数
			model.setIsPrint_req(sample.getIsPrint_req());//是否打印申请单
		}
		return this.SUCCESS;
	}
	//-----------------------样本和项目关系维护------------------------------
	/**
	 * 项目样本关系维护页面952
	     * @Title: getSampleDemoChargingItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSampleDemoChargingItemPage()  throws WebException{
		
		this.model.setId(model.getId());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("952");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 获取关系数据953
	     * @Title: getSampleDemoChargingItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getSampleDemoChargingItemList()  throws WebException{
		this.outJsonResult(this.sampleDemoService.getSampleDemoChargingItemList(model));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("953");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 删除关系954
	     * @Title: deleteSampleDemoChargingItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteSampleDemoChargingItem()  throws WebException{
		this.sampleDemoService.deleteSampleDemoChargingItem(model);
		this.outJsonStrResult("删除成功！");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("954");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 添加关系955
	     * @Title: saveSampleDemoChargingItemPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveSampleDemoChargingItemPage()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.sampleDemoService.addSampleDemoChargingItem(model, user);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("160");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult("保存成功！");
		return NONE;
	}
	/**
	 * 获取收费项目956
	     * @Title: getjianyanChagingitem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getjianyanChagingitem()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto = this.sampleDemoService.getChagingitem(model,this.page, this.rows);
		this.outJsonResult(dto);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("956");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/*
	 * 收费项目查询样本项目关系页面957
	 */
	public String getItemSampleDemoPage()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setId(model.getId());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("957");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/*
	 * 收费项目查询样本项目关系958
	 */
	public String getSampleDemoListShow()  throws WebException{
		List<SampleDemoDTO>  li = this.sampleDemoService.getItemSampleDemoList(model);
		this.outJsonResult(li);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("958");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 获取可以打印的科室959
	     * @Title: getDepartmentDepBarCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDepartmentDepBarCode()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.outJsonResult(chargingItemService.getDepartmentDepBarCode(user.getCenter_num()));
		return NONE;
	}
	
	public String updateYanSampleStopOrStart()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		try {
			SampleDemo eti = this.sampleDemoService.findClass(Long.valueOf(this.model.getIds()));
			if(eti.getIsActive().equals("Y")){
				eti.setIsActive("N");
				eti.setUpdate_time(DateTimeUtil.parse());;
				eti.setUpdater(user.getUserid());
			}else if(eti.getIsActive().equals("N")){
				eti.setIsActive("Y");
				eti.setUpdate_time(DateTimeUtil.parse());
				eti.setUpdater(user.getUserid());
			}
			this.sampleDemoService.updateSampleDemo(eti);
			this.message="ok-成功";
		} catch (Exception e) {
			this.message="error-"+e.toString();
			e.printStackTrace();
		}

		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
	public String addDemoTypePage()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.model.setIds(model.getIds());
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1580");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: saveDemoTypePage   
	     * @Description: TODO(保存项目类别)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveDemoTypePage()throws WebException{
		
		this.message = sampleDemoService.saveDemoTypePage(this.model.getIds(),this.model.getDemo_type());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	@Override
	public Object getModel() {
		return this.model;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setModel(SampleDemoModel model) {
		this.model = model;
	}
	public SampleDemoService getSampleDemoService() {
		return sampleDemoService;
	}
	public void setSampleDemoService(SampleDemoService sampleDemoService) {
		this.sampleDemoService = sampleDemoService;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
}
