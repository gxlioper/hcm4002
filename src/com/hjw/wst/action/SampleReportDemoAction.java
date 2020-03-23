package com.hjw.wst.action;

import java.io.IOException;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SampleReportDemo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SampleReportDemoModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.SampleReportDemoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  报告样本
     * @author: 张瑞     
     * @date:   2016年8月25日 上午11:28:04   
     * @version V2.0.0.0
 */
public class SampleReportDemoAction extends BaseAction implements ModelDriven{
	private static final long serialVersionUID = 8217814910220881058L;

	private SampleReportDemoModel model = new SampleReportDemoModel();
	private SampleReportDemoService sampleReportDemoService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;//批量删除ID
	private BatchService batchService;
	private SyslogService syslogService;  
	
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}

	/**
	 * 
	     * @Title: SampleReportMenu   
	     * @Description: TODO(报告样本管理页面-150--)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String SampleReportMenu(){
		

		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("536");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return this.SUCCESS;
	}
	
	/**
	 * 
	 * @Title: shouSampleReport   
	 * @Description: TODO(报告样本列表&&检索--151---)   
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String shouSampleReport(){
		SampleReportDemo sample = new SampleReportDemo();
		sample.setDemo_num(model.getDemo_num());
		sample.setDemo_name(model.getDemo_name());
		PageReturnDTO pageReturnDTO= sampleReportDemoService.querySampleReportDemo(sample,rows,page,this.model.getStartStop());
		this.outJsonResult(pageReturnDTO);
		return this.NONE;
	}
	
	/**
	 * 
	     * @Title: SampleReportDelet   
	     * @Description: TODO(删除报告样本--152--)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String SampleReportDelet(){
		SampleReportDemo demo = this.sampleReportDemoService.findSampleReportDemoForId(this.model.getId());
		if(demo==null){
			 this.message="删除失败";
		}else{
			demo.setIs_Active("N");
			this.sampleReportDemoService.updateSampleReportDemo(demo);
			this.message="删除成功";
		}
		this.outJsonStrResult(this.message);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("152");//子功能id 必须填写
		sl.setExplain("删除报告样本");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 
	     * @Title: deleteSampleReportDemo   
	     * @Description: TODO(批量删除报告样本159)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String deleteSampleReportDemo(){
		this.sampleReportDemoService.deleteSampleReportDemo(this.ids);
		this.message="删除成功";
		this.outJsonStrResult(message);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("159");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 
	     * @Title: addSampleReportDelet   
	     * @Description: TODO(新增报告样本页面--153)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("static-access")
	public String addSampleReportDelet(){
		UserDTO user = (UserDTO) session.get("username");
		model.setDemo_num(this.batchService.GetCreateID("sample_no", user.getCenter_num()));
		return this.SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getSampleReportDemoBynum   
	     * @Description: TODO(新增报告样本验证样本编号是否存在--154)   
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: String      
	     * @throws
	 */
	public String getSampleReportDemoBynum() throws IOException{
		SampleReportDemo sam = this.sampleReportDemoService.getSampleReportDemoBynum(model.getDemo_num());
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
	     * @Title: getSampleReportDemoByname   
	     * @Description: TODO(验证报告样本名称是否存在)   
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: String      
	     * @throws
	 */
	public String getSampleReportDemoByname() throws IOException{
		SampleReportDemo sam = this.sampleReportDemoService.getSampleReportDemoByname(model.getDemo_name());
		if(sam==null){
			//报告样本名称可用
			this.message="ok";
		}else{
			//报告样本名称找到了不可用
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: addSampleReportDemo   
	     * @Description: TODO(添加报告样本156)   
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: String      
	     * @throws
	 */
	public String addSampleReportDemo() throws IOException{
		if(model.getId()>0){//修改报告样本
			SampleReportDemo sam = this.sampleReportDemoService.findClass(model.getId());
			if(sam!=null){
				sam.setDemo_name(model.getDemo_name());
				//sam.setDemo_num(model.getDemo_num());
				//sam.setBarCode(model.getBarCode());
				sam.setPrint_seq(model.getPrint_seq());
				sam.setRemark(model.getRemark());
				sam.setUpdate_time(DateTimeUtil.parse());//修改时间
				sam.setIs_Active("Y");//添加激活状态
				UserDTO user=(UserDTO) session.get("username");//获取修改人
				sam.setUpdater(user.getUserid());//修改人
				this.sampleReportDemoService.deleteSam(sam);
				this.message="修改成功";
				
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("156");//子功能id 必须填写
				sl.setExplain("修改报告样本");//操作说明
				syslogService.saveSysLog(sl);
				
			}
		}else{//添加报告样本
			UserDTO user=(UserDTO) session.get("username");
			SampleReportDemo sample = new SampleReportDemo();
			sample.setDemo_name(model.getDemo_name());//名称
			sample.setDemo_num(model.getDemo_num());//编号
			sample.setPrint_seq(model.getPrint_seq());//打印顺序
			//sample.setBarCode(model.getBarCode());
			sample.setRemark(model.getRemark());
			sample.setIs_Active("Y");//添加激活状态
			sample.setCreater(user.getUserid());//创建人
			sample.setCreate_time(DateTimeUtil.parse());
			this.sampleReportDemoService.addSampleReportDemo(sample);
			this.message="添加成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("156");//子功能id 必须填写
			sl.setExplain("添加报告样本");//操作说明
			
		}
		this.outJsonStrResult(message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: updataSampleReportDemo   
	     * @Description: TODO(修改页面--157)   
	     * @param: @return
	     * @param: @throws IOException      
	     * @return: String      
	     * @throws
	 */
	@SuppressWarnings("static-access")
	public String updataSampleReportDemoPage() throws IOException{
			SampleReportDemo sam = this.sampleReportDemoService.findClass(model.getId());
			if(sam!=null){
				model.setId(sam.getId());
				model.setDemo_num(sam.getDemo_num());
				model.setDemo_name(sam.getDemo_name());
				model.setPrint_seq(sam.getPrint_seq());
				model.setRemark(sam.getRemark());
			}
		return this.SUCCESS;
	}
	
	//报告样本 启/停
	public String updateSampleStopOrStart()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		try {
			SampleReportDemo eti = this.sampleReportDemoService.findSampleReportDemoForId(Long.valueOf(ids));
			if(eti.getIs_Active().equals("Y")){
				eti.setIs_Active("N");
				eti.setUpdate_time(DateTimeUtil.parse());;
				eti.setUpdater(user.getUserid());
			}else if(eti.getIs_Active().equals("N")){
				eti.setIs_Active("Y");
				eti.setUpdate_time(DateTimeUtil.parse());
				eti.setUpdater(user.getUserid());
			}
			this.sampleReportDemoService.updateSampleReportDemo(eti);
			this.message="ok-成功";
		} catch (Exception e) {
			this.message="error-"+e.toString();
			e.printStackTrace();
		}

		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	public Object getModel() {
		return model;
	}

	public SampleReportDemoService getSampleReportDemoService() {
		return sampleReportDemoService;
	}

	public void setSampleReportDemoService(SampleReportDemoService sampleReportDemoService) {
		this.sampleReportDemoService = sampleReportDemoService;
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

	public void setModel(SampleReportDemoModel model) {
		this.model = model;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
