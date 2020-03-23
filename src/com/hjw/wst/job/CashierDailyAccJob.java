package com.hjw.wst.job;

import java.util.ArrayList;
import java.util.List;

import com.hjw.util.TranLogTxt;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.service.CashierDailyAccService;
import com.hjw.wst.service.CustomerInfoService;

public class CashierDailyAccJob {

	private CashierDailyAccService cashierDailyAccService;
	private CustomerInfoService customerInfoService;

	public void setCashierDailyAccService(CashierDailyAccService cashierDailyAccService) {
		this.cashierDailyAccService = cashierDailyAccService;
	}
	public void setCustomerInfoService(CustomerInfoService customerInfoService) {
		this.customerInfoService = customerInfoService;
	}
	
	public void automaticCashierDaily(){
		String isAutomaticCashierDaily = this.customerInfoService.getCenterconfigByKey("IS_AUTOMATIC_CASHIER_DAILY", null).getConfig_value().trim();
		if("Y".equals(isAutomaticCashierDaily)){
			TranLogTxt.liswriteEror_to_txt("automaticCashierDaily","res:开始自动日结收费信息");
			try {
				TranLogTxt.liswriteEror_to_txt("automaticCashierDaily","res:查询需要自动日结的收费员信息");
				List<UserDTO> list = this.cashierDailyAccService.getCashierDailyAllAmountJob();
				for (UserDTO user : list) {
					this.cashierDailyAccService.saveCashierDailyacc(user);
				}
				TranLogTxt.liswriteEror_to_txt("automaticCashierDaily","res:自动日结收费信息入库成功!");
			} catch (Exception e) {
				e.printStackTrace();
				TranLogTxt.liswriteEror_to_txt("automaticCashierDaily","res:日结收费信息入库出错!--"+e.getMessage());
			}
		}else{
			TranLogTxt.liswriteEror_to_txt("automaticCashierDaily","res:未启用自动收费员日结功能!");
		}
	}
}
