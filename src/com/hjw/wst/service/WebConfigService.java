package com.hjw.wst.service;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.WebConfig;

public interface WebConfigService
{

	PageReturnDTO findConfigList(int page, int rp);

	WebConfig findWebConfig(String code);

	void updateConfig(WebConfig termInfoConfig);

}
