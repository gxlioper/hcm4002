package com.hjw.zyb.action;

import java.sql.SQLException;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.domain.ZybExaminationresult;
import com.hjw.zyb.model.ZybExaminationresultModel;
import com.hjw.zyb.service.ZybexaminationresultService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 体检结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zt    
     * @date:   2017年4月21日 上午10:43:03   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybexamresultAction extends BaseAction implements ModelDriven {
	
	private static final long serialVersionUID = 1L;
	private ZybexaminationresultService zybexaminationresultService;
	private ZybExaminationresultModel  model= new ZybExaminationresultModel();
	private String ids;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SyslogService syslogService;    

	
	
	
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
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


	public SyslogService getSyslogService() {
		return syslogService;
	}


	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}


	public ZybExaminationresultModel getModel() {
		return model;
	}


	public ZybexaminationresultService getZybexaminationresultService() {
		return zybexaminationresultService;
	}


	public void setZybexaminationresultService(
			ZybexaminationresultService zybexaminationresultService) {
		this.zybexaminationresultService = zybexaminationresultService;
	}

	public void setModel(ZybExaminationresultModel model) {
		this.model = model;
	}

	/**
	 * 体检结论管理zyb273
	     * @Title: getZybExamresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String ZybExamresultManager() throws  WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb273");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	
	/**
	 *体检结论数据加载zyb274
	     * @Title: getZybexamresultList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybexamresultList()throws  WebException,SQLException{
		String result_name=this.model.getResult_name();
		PageReturnDTO list=this.zybexaminationresultService.queryZybexaminationresultList(result_name, this.rows,this.getPage());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 体检结论批量删除zyb278
	     * @Title: deleteZybExamresult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZybExamresult() throws  WebException,SQLException{
		this.zybexaminationresultService.deleteZybexamresul(this.model.getIds());
		this.outJsonStrResult(this.message="删除成功");
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb278");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 体检结论删除zyb277
	     * @Title: deleteExamresultone   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteExamresultone() throws  WebException,SQLException{
		ZybExaminationresult r=this.zybexaminationresultService.queryByID(this.model.getResultID());
		this.zybexaminationresultService.deleteZybexamresulone(r);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb277");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message="删除成功!");
		return NONE;
	}
	/**
	 * 体检结论添加zyb275
	     * @Title: addZybExamresult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZybExamresult() throws  WebException,SQLException{
		return SUCCESS;
	}
	
	/**
	 * 体检结论更新zyb276
	     * @Title: updateZybExamresult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateZybExamresult() throws  WebException,SQLException{
		ZybExaminationresult r=this.zybexaminationresultService.queryByID(this.model.getResultID());
		this.model.setResult_name(r.getResult_name());
		this.model.setSeq_code(r.getSeq_code());
		return SUCCESS;
	}
	
	/**
	 * 体检结论保存zyb279
	     * @Title: saveZyboccudisease   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybExamresult() throws  WebException,SQLException{
		if(!"".equals(this.model.getResultID())&& this.model.getResultID()!=null){
			ZybExaminationresult r=this.zybexaminationresultService.queryByID(this.model.getResultID());
			r.setResult_name(this.model.getResult_name());
			r.setSeq_code(model.getSeq_code());
			this.zybexaminationresultService.updateZybexamresul(r);
			this.outJsonStrResult(this.message="修改成功!");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb279");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			ZybExaminationresult r=new ZybExaminationresult();
			r.setResult_name(this.model.getResult_name());
			r.setSeq_code(model.getSeq_code());
			this.zybexaminationresultService.addZybexamresul(r);
			this.outJsonStrResult(this.message="添加成功!");
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zybz79");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		return  NONE;
	}
}
