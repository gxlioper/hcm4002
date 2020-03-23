package com.hjw.wst.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.SelfServiceMachineModel;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SelfServiceMachineService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 自助机调用服务类
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: dq     
     * @date:   2019年3月26日 下午2:20:24   
     * @version V2.0.0.0
 */
public class SelfServiceMachineAction extends BaseAction implements ModelDriven{

	private SelfServiceMachineModel model = new SelfServiceMachineModel();
	private SelfServiceMachineService selfServiceMachineService;
	private CustomerInfoService customerInfoService;
	
	public void setSelfServiceMachineService(SelfServiceMachineService selfServiceMachineService) {
		this.selfServiceMachineService = selfServiceMachineService;
	}

	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}

	public SelfServiceMachineModel getModel() {
		return model;
	}

	public void setModel(SelfServiceMachineModel model) {
		this.model = model;
	}
	
	/**
	 * 自助机自助缴费服务
	     * @Title: selfServiceMachineFees   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String selfServiceMachineFees() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		String is_fees_qijian = this.customerInfoService.getCenterconfigByKey("IS_FEES_QIJIAN", user.getCenter_num()).getConfig_value().trim();
		this.message = this.selfServiceMachineService.selfServiceMachineFees(model.getExam_num(), model.getAmount(), model.getCharging_way(), model.getCard_num(),is_fees_qijian,model.getUserid(), user.getCenter_num());
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
