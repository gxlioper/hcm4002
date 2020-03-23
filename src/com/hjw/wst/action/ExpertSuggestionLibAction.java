package com.hjw.wst.action;

import java.sql.SQLException;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExpertSuggestionLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExpertSuggestionLib;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ExpertSuggestionLibModel;
import com.hjw.wst.service.BatchService;
import com.hjw.wst.service.ExpertSuggestionLibService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:专科建议知识库
     * @author: zr     
     * @date:   2016年11月2日 下午3:21:21   
     * @version V2.0.0.0
 */
public class ExpertSuggestionLibAction   extends BaseAction implements ModelDriven {
	private ExpertSuggestionLibModel model=new ExpertSuggestionLibModel();
	private ExpertSuggestionLibService  expertSuggestionLibService;
	private BatchService batchService;
	private int page=1;
	private int rows=15;
	private String ids;
	private SyslogService syslogService;  
	
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}


	public BatchService getBatchService() {
		return batchService;
	}


	public void setBatchService(BatchService batchService) {
		this.batchService = batchService;
	}


	public ExpertSuggestionLibService getExpertSuggestionLibService() {
		return expertSuggestionLibService;
	}


	public void setExpertSuggestionLibService(
			ExpertSuggestionLibService expertSuggestionLibService) {
		this.expertSuggestionLibService = expertSuggestionLibService;
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


	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public void setModel(ExpertSuggestionLibModel model) {
		this.model = model;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 
	     * @Title: queryExpertSuggestionLib   
	     * @Description: TODO(专家建议列表514)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryExpertSuggestionLib() throws  WebException, SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: getExpertSuggestionLib   
	     * @Description: TODO(专家建议知识库列表515)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExpertSuggestionLib() throws  WebException, SQLException{
		ExpertSuggestionLibDTO li = new ExpertSuggestionLibDTO();
		li.setDep_id(model.getDep_id());
		li.setSugg_word(model.getSugg_word());
		PageReturnDTO dto = this.expertSuggestionLibService.queryExpertSuggestionLib(li,rows,page);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: deletExpertSuggestionLib   
	     * @Description: TODO(专家建议知识库删除516)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deletExpertSuggestionLib() throws  WebException, SQLException{
		this.expertSuggestionLibService.deleteExpertSuggestionLib(ids);
		this.message="删除成功";
		this.outJsonStrResult(this.message);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("516");//子功能id 必须填写
		sl.setExplain("专家建议知识库删除");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 
	     * @Title: addExpertSuggestionLibPage   
	     * @Description: TODO(专家知识库新增页面517)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addExpertSuggestionLibPage() throws   WebException, SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: addExpertSuggestionLib   
	     * @Description: TODO(添加专家建议知识库518)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addExpertSuggestionLib() throws  WebException, SQLException{
		if(model.getId()>0){//修改专家建议
			ExpertSuggestionLib   ti =this.expertSuggestionLibService.findExpertSuggestionLib(model.getId());
			ti.setDep_id(model.getDep_id());
			ti.setDep_id(model.getDep_id());//科室
			ti.setSugg_content(model.getSugg_content());//建议内容
			ti.setSugg_word(model.getSugg_word());//建议名词
			//exp.setIs_Active("Y");
			//String num=batchService.GetCreateID("disease_no");
			//ti.setSugg_num(num);
			UserDTO user=(UserDTO) session.get("username");
			ti.setUpdater(user.getUserid());
			ti.setUpdate_time(DateTimeUtil.parse());
			this.expertSuggestionLibService.updateExpertSuggestionLib(ti);
			this.message="修改成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("518");//子功能id 必须填写
			sl.setExplain("修改专科建议");//操作说明
			syslogService.saveSysLog(sl);
			
		}else{//添加专家建议
			ExpertSuggestionLib exp = new ExpertSuggestionLib();
			exp.setDep_id(model.getDep_id());
			exp.setSugg_content(model.getSugg_content());//建议内容
			exp.setSugg_word(model.getSugg_word());//建议名词
			exp.setIs_Active("Y");
			UserDTO user=(UserDTO) session.get("username");
			String num=batchService.GetCreateID("disease_no", user.getCenter_num());
			exp.setSugg_num(num);
			exp.setCreater(user.getUserid());
			exp.setCreate_time(DateTimeUtil.parse());
			this.expertSuggestionLibService.addExpertSuggestionLib(exp);
			this.message="添加成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("518");//子功能id 必须填写
			sl.setExplain("添加专科建议");//操作说明
			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateExpertSuggestionLibPage   
	     * @Description: TODO(专家建议修改页面519)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateExpertSuggestionLibPage() throws WebException, SQLException{
		ExpertSuggestionLib   ti =this.expertSuggestionLibService.findExpertSuggestionLib(model.getId());
		model.setId(ti.getId());
		model.setDep_id(ti.getDep_id());
		model.setSugg_content(ti.getSugg_content());
		model.setSugg_word(ti.getSugg_word());
		return SUCCESS;
	}
}
