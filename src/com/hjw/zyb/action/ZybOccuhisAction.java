package com.hjw.zyb.action;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.Batch;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 职业史
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年7月19日 上午10:31:39   
     * @version V2.0.0.0
 */
public class ZybOccuhisAction extends BaseAction implements ModelDriven{
	private SyslogService syslogService;
 	private String creater;
 	private String uploadURL;
 	
 	
 	
	public String getUploadURL() {
		return uploadURL;
	}

	public void setUploadURL(String uploadURL) {
		this.uploadURL = uploadURL;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	/**
	 * 职业史上传页面zyb363
	     * @Title: getOccuhisUploadPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuhisUploadPage() throws  WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb363");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.setCreater(user.getUserid()+"");
		return SUCCESS;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return null;
	}
}
