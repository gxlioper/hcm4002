package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.domain.ZybProposetemplate;
import com.hjw.zyb.model.ZybProposetemplateModel;
import com.hjw.zyb.service.ZybProposetemplateService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.DateTime;
import com.synjones.framework.web.action.BaseAction;

/**
 * 职业建议词管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月27日 上午10:20:45   
     * @version V2.0.0.0
 */
public class ZybProposetemplateAction extends  BaseAction implements  ModelDriven {

	private ZybProposetemplateModel model = new ZybProposetemplateModel();
	private int page=1;
	private int pageSize=15;
	private String ids;
	private ZybProposetemplateService  zybProposetemplateService;
	private SyslogService syslogService;    

	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}


	public void setZybProposetemplateService(ZybProposetemplateService zybProposetemplateService) {
		this.zybProposetemplateService = zybProposetemplateService;
	}


	public void setPage(int page) {
		this.page = page;
	}


	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	

	public void setModel(ZybProposetemplateModel model) {
		this.model = model;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 建议词管理----zyb363
	     * @Title: getZybProposetemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getZybProposetemplatePage() throws  SQLException,WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb363");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return SUCCESS;
	}
	/**
	 * 获取结论List----zyb364
	     * @Title: getExaminationresult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminationresult() throws   SQLException,WebException{
		List li = this.zybProposetemplateService.getExaminationresult(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 获取建议词List----zyb365
	     * @Title: queryZybProposetemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryZybProposetemplatePage() throws  SQLException,WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO  dto = this.zybProposetemplateService.queryProposetemplate(model, page, pageSize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 删除建议词----zyb366
	     * @Title: deleteZybProposetemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZybProposetemplatePage() throws  SQLException,WebException{
		model.setTEMPLATEID(ids);
		this.zybProposetemplateService.deleteProposetemplate(model);
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb366");//子功能id 必须填写
		sl.setExplain("");//操作说明
		return NONE;
	}
	/**
	 * 建议词新增页面----zyb367
	     * @Title: addZybProposetemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String addZybProposetemplatePage() throws  SQLException,WebException{
		return SUCCESS;
	}
	/**
	 * 建议词修改页面----zyb368
	     * @Title: updateZybProposetemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateZybProposetemplatePage() throws  SQLException,WebException{
		ZybProposetemplate   z = this.zybProposetemplateService.getProposetemplate(model);
		model.setCONTEXT(z.getCONTEXT());
		model.setENABLE(z.isENABLE());
		model.setISDEFAULT(z.isISDEFAULT());
		model.setOCCUPECLASSID(z.getOCCUPECLASSID());
		model.setOPTIONID(z.getOPTIONID());
		model.setORGID(z.getORGID());
		model.setTEMPLATEID(z.getTEMPLATEID());
		model.setTEMPLATENAME(z.getTEMPLATENAME());
		model.setUPDATER(z.getUPDATER());
		model.setUPDATETIME(DateTimeUtil.shortFmt3(z.getUPDATETIME()));
		return SUCCESS;
	}
	/**
	 * 保存建议词----zyb369
	     * @Title: getZybProposetemplatePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybProposetemplatePage() throws  SQLException,WebException{
		UserDTO  user = (UserDTO) session.get("username");
		if (model.getTEMPLATEID()!=null && !"".endsWith(model.getTEMPLATEID())){
			this.zybProposetemplateService.updateProposetemplate(model,user);
			this.outJsonStrResult(this.message="修改成功");
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb369");//子功能id 必须填写
			sl.setExplain("");//操作说明
			
		}  else {
			ZybProposetemplate  pro = new ZybProposetemplate();
			pro.setCONTEXT(model.getCONTEXT());
			pro.setENABLE(model.isENABLE());
			pro.setISDEFAULT(model.isISDEFAULT());
			pro.setOCCUPECLASSID(model.getOCCUPECLASSID());
			pro.setOPTIONID(model.getOPTIONID());
		
			pro.setORGID(user.getCenter_id());
			pro.setTEMPLATEID(model.getTEMPLATEID());
			pro.setTEMPLATENAME(model.getTEMPLATENAME());
			pro.setUPDATER(user.getUserid());
			pro.setUPDATETIME(DateTimeUtil.parse());
			this.zybProposetemplateService.addProposetemplate(pro);
			this.outJsonStrResult(this.message="添加成功");
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb369");//子功能id 必须填写
			sl.setExplain("");//操作说明
			
		}
		return NONE;
	}
	/**
	 * 修改列表chebox状态----zyb371
	     * @Title: updateProposetemplateListchebox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws SQLException
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updateProposetemplateListchebox() throws   SQLException,WebException{
		this.zybProposetemplateService.updateProposetemplateListchebox(model);
		return NONE;
	}
}
