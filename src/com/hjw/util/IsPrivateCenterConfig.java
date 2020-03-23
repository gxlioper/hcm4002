package com.hjw.util;

import java.util.List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.service.CustomerInfoService;

public class IsPrivateCenterConfig {

	private static CustomerInfoService customerInfoService;
	static {
		init();
	}
	public static void init() {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		customerInfoService = (CustomerInfoService) wac.getBean("customerInfoService");
	}

	public static String isprivateflag(UserDTO user){		
		String config_value = customerInfoService.getCenterconfigByKey("IS_PRIVATE", user.getCenter_num()).getConfig_value();
		String privateflag = "Y";
		if(config_value != null && "Y".equals(config_value)){
			List<WebResrelAtionship> webResource = user.getWebResource();
			if(webResource!=null && webResource.size()>0){
				for (WebResrelAtionship wrs : webResource) {
					if(wrs.getRes_code().equals( "RS048")){
						if(wrs.getDatavalue().equals("1")){
							privateflag = "N";
							break;
						}
						
					}
				}
			}
		}
		return privateflag;
	}
}
