package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.wst.DTO.BatchDTO;
import com.hjw.wst.DTO.CenterConfigurationDTO;
import com.hjw.wst.DTO.ContractDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.TreeDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.CompanyDepartment;
import com.hjw.wst.domain.CompanyInfo;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.CompanyInfoModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.CompanyService;
import com.hjw.wst.service.CustomerInfoService;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybEconomicTreeDTO;
import com.hjw.zyb.service.ZybOccuService;
import com.opensymphony.xwork2.ModelDriven;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

@SuppressWarnings("rawtypes")
public class ZybCommonAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private CompanyInfoModel model = new CompanyInfoModel();
    private SyslogService syslogService;    
    private ZybOccuService zybOccuService;    
    
    public void setZybOccuService(ZybOccuService zybOccuService) {
		this.zybOccuService = zybOccuService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(CompanyInfoModel model) {
		this.model = model;
	}
	
   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * 
	     * @Title: getzybCyhy   
	     * @Description: 职业病 获取从业工种数据字典zyb20   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getzybCyhyList() throws WebException, SQLException {
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		SystemType app_type = (SystemType) session.get("defaultapp");
		int industry_type = 0;
		if("2".equals(app_type.getComid())) {//职业病
			industry_type = 1;
		}
		list = this.zybOccuService.getCyhyList(this.model.getCom_Name(), industry_type);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getzybCygzList   
	     * @Description: 职业病 获取从业工种数据字典zyb19   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getzybCygzList() throws WebException, SQLException {
		List<TreeDTO> list = new ArrayList<TreeDTO>();
		list = this.zybOccuService.queryByAllTypOfWork(this.model.getCom_Name());
		this.outJsonResult(list);
		return NONE;
	}

}