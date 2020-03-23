package com.hjw.zyb.action;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybAskDiagnosisSampleDTO;
import com.hjw.zyb.domain.ZybAskDiagnosisSample;
import com.hjw.zyb.model.ZybAskDiagnosisSampleModel;
import com.hjw.zyb.service.ZybAskDiagnosisSampleService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 问诊项目维护
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: dangqi     
     * @date:   2017年12月6日 上午10:42:02   
     * @version V2.0.0.0
 */
public class ZybAskDiagnosisSampleAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private ZybAskDiagnosisSampleModel model = new ZybAskDiagnosisSampleModel();
	private ZybAskDiagnosisSampleService zybAskDiagnosisSampleService;
	private SyslogService syslogService;
	
	public ZybAskDiagnosisSampleModel getModel() {
		return model;
	}
	public void setModel(ZybAskDiagnosisSampleModel model) {
		this.model = model;
	}
	public void setZybAskDiagnosisSampleService(ZybAskDiagnosisSampleService zybAskDiagnosisSampleService) {
		this.zybAskDiagnosisSampleService = zybAskDiagnosisSampleService;
	}
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	
	/**
	 * zyb413 问诊项目维护管理页面
	     * @Title: getZybAskDiagnosisSamplePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybAskDiagnosisSamplePage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb413");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * zyb414 获取问诊项目列表
	     * @Title: getZybAskDiagnosisSampleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybAskDiagnosisSampleList() throws WebException{
		List<ZybAskDiagnosisSampleDTO> list = this.zybAskDiagnosisSampleService.getZybAskDiagnosisSampleList(model);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * zyb415 问诊项目编辑页面
	     * @Title: getZybAskDiagnosisSampleEditPage 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybAskDiagnosisSampleEditPage() throws WebException{
		if(this.model.getId() > 0){
			ZybAskDiagnosisSample sample = this.zybAskDiagnosisSampleService.getZybAskDiagnosisSampleById(this.model.getId());
			model.setName(sample.getName());
			model.setSub_name(sample.getSub_name());
			model.setSeq_code(sample.getSeq_code());
			model.setTemp_content(sample.getTemp_content());
			model.setType(sample.getType());
			model.setSex(sample.getSex());
		}else{
			model.setType("1");
		}
		return SUCCESS;
	}
	
	/**
	 * zyb416 保存问诊项目信息
	     * @Title: saveZybAskDiagnosisSample   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybAskDiagnosisSample() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){//修改
			ZybAskDiagnosisSample sample = this.zybAskDiagnosisSampleService.getZybAskDiagnosisSampleById(this.model.getId());
			sample.setName(model.getName());
			sample.setSub_name(model.getSub_name());
			sample.setSeq_code(model.getSeq_code());
			sample.setTemp_content(model.getTemp_content());
			sample.setType(model.getType());
			sample.setUpdater(user.getUserid());
			sample.setUpdate_time(DateTimeUtil.parse());
			sample.setSex(model.getSex());
			this.zybAskDiagnosisSampleService.updateZybAskDiagnosisSample(sample);
			this.message = "修改成功!";
		}else{//新增
			ZybAskDiagnosisSample sample = new ZybAskDiagnosisSample();
			sample.setName(model.getName());
			sample.setSub_name(model.getSub_name());
			sample.setSeq_code(model.getSeq_code());
			sample.setTemp_content(model.getTemp_content());
			sample.setType(model.getType());
			sample.setIs_active("Y");
			sample.setCreater(user.getUserid());
			sample.setCreare_time(DateTimeUtil.parse());
			sample.setUpdater(user.getUserid());
			sample.setUpdate_time(DateTimeUtil.parse());
			sample.setSex(model.getSex());
			this.zybAskDiagnosisSampleService.saveZybAskDiagnosisSample(sample);
			this.message = "新增成功!";
		}
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb416");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * zyb417 删除问诊项目信息
	     * @Title: deleteZybAskDiagnosisSample   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZybAskDiagnosisSample() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		ZybAskDiagnosisSample sample = this.zybAskDiagnosisSampleService.getZybAskDiagnosisSampleById(this.model.getId());
		sample.setIs_active("N");
		sample.setUpdater(user.getUserid());
		sample.setUpdate_time(DateTimeUtil.parse());
		this.zybAskDiagnosisSampleService.updateZybAskDiagnosisSample(sample);
		this.message = "删除成功!";
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
