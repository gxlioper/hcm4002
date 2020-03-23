package com.hjw.wst.action;

import net.sf.json.JSONSerializer;

import com.hjw.util.ConstStandardEdition;
import com.hjw.wst.DTO.LogItem;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.domain.WebConfig;
import com.hjw.wst.service.WebConfigService;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  圈存查询一体机系统3.0   
     * @Package com.synjones.wst.action   
     * @Description:    终端配置  
     * @author: yujia     
     * @date:   2015-8-5 下午3:35:12   
     * @version V3.0.0.0
 */
public class WebConfigAction extends BaseAction
{

	private static final long serialVersionUID = 1L;
	private WebConfigService webConfigService;
	private LogItem logItem = new LogItem();
	private WebConfig webConfig;
	private int page = 1;
	private int rp = 10;
	private String code;
	private int rows=15; //easyui每页显示条数

	public int getRows()
	{
		return rows;
	}

	public void setRows(int rows)
	{
		this.rows = rows;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}


	public WebConfig getWebConfig()
	{
		return webConfig;
	}

	public void setWebConfig(WebConfig webConfig)
	{
		this.webConfig = webConfig;
	}

	public WebConfigService getWebConfigService()
	{
		return webConfigService;
	}

	public void setWebConfigService(WebConfigService webConfigService)
	{
		this.webConfigService = webConfigService;
	}

	public LogItem getLogItem()
	{
		return logItem;
	}

	public void setLogItem(LogItem logItem)
	{
		this.logItem = logItem;
	}

	public int getPage()
	{
		return page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getRp()
	{
		return rp;
	}

	public void setRp(int rp)
	{
		this.rp = rp;
	}

	/**
	 * 
	     * @Title: toEditConfigName   
	     * @Description: 加载数据   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String termConfigmainshow() throws WebException, Exception
	{
        PageReturnDTO webconfig= new PageReturnDTO();
        webconfig = webConfigService.findConfigList(page,rows);
        String jsonObject = JSONSerializer.toJSON(webconfig).toString();
        this.outJsonStrResult(jsonObject);
        return null;
	}
	/**
	 * 
	 * @Title: toEditConfigName   
	 * @Description: 跳转到配置管理页面
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public String toInfoConfig()
	{
		logItem.setFlag(true);
		logItem.setOperId("");
		logItem.setOperName("");
		return "toInfoConfig";
	}
	/**
	 * 
	     * @Title: toEditConfigName   
	     * @Description: 跳转修改类型   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String toChangeType() throws WebException, Exception
	{
        webConfig = webConfigService.findWebConfig(code);
		return "toChangeType";
	}

 	/**
 	 * 
 	     * @Title: toEditConfigName   
 	     * @Description: 更新配置
 	     * @param: @return      
 	     * @return: String      
 	     * @throws
 	 */
	public String updateConfig()
	{
		String types = request.getParameter("types")==null?"":request.getParameter("types");
		try
		{
			webConfig = webConfigService.findWebConfig(code);
			if(null==webConfig)
			{
				message="更新失败,code不存在";
				this.outJsonStrResult(message);
			}
			webConfig.setTypes(types);
			webConfigService.updateConfig(webConfig);
			message=ConstStandardEdition.UPDATE_SUCCESS;
		}
		catch(Exception e)
		{
			message=ConstStandardEdition.UPDATE_FAIL;
			e.printStackTrace();
		}
		logItem.setFlag(true);
		logItem.setOperId("");
		logItem.setOperName("");
		this.outJsonStrResult(message);
        return null;
	}
}
