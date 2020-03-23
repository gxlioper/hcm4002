package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DiseaseKnowloedgeDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DiseaseMerge;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.DiseaseMergeModel;
import com.hjw.wst.service.DiseaseMergeService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 合并疾病
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: zr     
     * @date:   2017年5月5日 下午3:20:51   
     * @version V2.0.0.0
 */
public class DiseaseMergeAction extends  BaseAction  implements  ModelDriven{
	private DiseaseMergeModel model = new DiseaseMergeModel();
	private DiseaseMergeService diseaseMergeService;
	private int page=1;
	private int pageSize=15;
	private String ids;
	private String q;
	
	
    public String getQ() {
		return q;
	}


	public void setQ(String q) {
		this.q = q;
	}
	private SyslogService syslogService;    
	
   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	
	public void setIds(String ids) {
		this.ids = ids;
	}


	public void setModel(DiseaseMergeModel model) {
		this.model = model;
	}


	public void setDiseaseMergeService(DiseaseMergeService diseaseMergeService) {
		this.diseaseMergeService = diseaseMergeService;
	}


	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 合并疾病管理----586
	     * @Title: diseaseMergePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseMergePage() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("586");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 合并疾病列表----587
	     * @Title: querytMergePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String querytMergePage() throws  WebException,SQLException{
		PageReturnDTO  dto = this.diseaseMergeService.queryDiseaseMerge(model, page, pageSize);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 删除合并疾病----588
	     * @Title: deleteMergePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteMerge() throws  WebException,SQLException{
		this.diseaseMergeService.deleteDiseaseMerge(ids);
		this.outJsonStrResult(this.message="删除成功");
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("588");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	/**
	 * 合并疾病新增页面----589
	     * @Title: addMergePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addMergePage() throws  WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 合并疾病修改页面----590
	     * @Title: updateMergePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateMergePage() throws  WebException,SQLException{
		DiseaseMerge  d = this.diseaseMergeService.getDiseaseMerge(model.getId());
		model.setId( d.getId() );
		model.setLater_disease_id( d.getLater_disease_id() );
		return SUCCESS;
	}
	/**
	 * 合并疾病获取所有疾病下拉框----zyb591
	     * @Title: getPageDklodegcombobox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getPageDklodegcombobox() throws  WebException,SQLException{
		
		 List<DiseaseKnowloedgeDTO>  li = new ArrayList<>();
		 if(this.q==null && "".equals(this.q)){
			  li = this.diseaseMergeService.getPageDklodegcombobox(model.getDisease_name());
		 } else {
			  li = this.diseaseMergeService.getPageDklodegcombobox(q);
		 }
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 保存合并疾病----592
	     * @Title: getPageDklodegcombobox   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String savediseaseMerge() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if ( model.getId()>0 ) {
			this.diseaseMergeService.updateDiseaseMerge(model, user);
			this.message="修改成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("592");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		} else {
			DiseaseMerge d = new DiseaseMerge();
		    d.setId( model.getId() );
		    d.setBefore_disease_id( model.getBefore_disease_id() );
		    d.setLater_disease_id( model.getLater_disease_id() );
		    d.setName( model.getName() );
		    d.setCreater( user.getUserid() );
		    d.setCreate_time( DateTimeUtil.parse());
			this.diseaseMergeService.addDiseaseMerge(d);
			this.message="添加成功";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("592");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 修改时获取已合并疾病---593
	     * @Title: get   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String updateDiseaseKnowloedgeDTOList() throws  WebException,SQLException{
		List<DiseaseKnowloedgeDTO>  li = this.diseaseMergeService.getqueryPageDklodeg(model);
		this.outJsonResult(li);
		return NONE;
	}
}
