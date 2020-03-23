package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybOcctemplateDTO;
import com.hjw.zyb.DTO.ZybOccuindustryDTO;
import com.hjw.zyb.DTO.ZybSourcecareerclassDTO;
import com.hjw.zyb.domain.ZybCheckcriterion;
import com.hjw.zyb.domain.ZybOcctemplate;
import com.hjw.zyb.domain.ZybOccuindustry;
import com.hjw.zyb.domain.ZybOccutypeofwork;
import com.hjw.zyb.domain.ZybSourcecareerclass;
import com.hjw.zyb.domain.ZyboccuhazardfactorsCheckcriterion;
import com.hjw.zyb.model.ZybOcctemplateModel;
import com.hjw.zyb.service.ZybOcctemplateService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zwx
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybOcctemplateAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	private ZybOcctemplateService zybOcctemplateService;
	private ZybOcctemplateModel model = new ZybOcctemplateModel();
	private SyslogService syslogService;    

	public ZybOcctemplateService getZybOcctemplateService() {
		return zybOcctemplateService;
	}

	//---------------------------------------------------从业行业-------------------------------------------------
	
	
	public void setZybOcctemplateService(
			ZybOcctemplateService ZybOcctemplateService) {
		this.zybOcctemplateService = ZybOcctemplateService;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setModel(ZybOcctemplateModel model) {
		this.model = model;
	}

	public void setSyslogService(SyslogService syslogService) {
	   	this.syslogService = syslogService;
	}

	public Object getModel() {
		return model;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}

	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
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

	/**
	 * 从业工种管理zyb501
	     * @Title: occtemplateManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String occtemplateManager() throws WebException,SQLException{
		 UserDTO user = (UserDTO) session.get("username");
		 SysLog sl =  new SysLog();
		 sl.setCenter_num(user.getCenter_num());
		 sl.setUserid(user.getUserid()+"");
		 sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		 sl.setXtgnid("");//可不填写
		 sl.setXtgnid2("zyb501");//子功能id 必须填写
		 sl.setExplain("");//操作说明
		 syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 职业病团报模板添加页面zyb502
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addOcctemplate()throws WebException,SQLException{
		return SUCCESS;
	}
	
	/**
	 * 职业病团报模板分页查询zyb504
	     * @Title: getOcctemplateList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOcctemplateList() throws WebException,SQLException{
		String exam_type_name=this.model.getExam_type_name().trim();
		String occuphyexaclass_name=this.model.getOccuphyexaclass_name().trim();
		PageReturnDTO z=this.zybOcctemplateService.queryByOccuphyexaclass(exam_type_name, occuphyexaclass_name, this.rows, this.getPage());
		this.outJsonResult(z);
		return NONE;
	}
	
	/**
	 * 职业病团报模板保存
	 * zyb505
     * @Description: TODO(这里用一句话描述这个方法的作用)   
     * @param: @return
     * @param: @throws WebException
     * @param: @throws SQLException      
     * @return: String      
     * @throws
	 */
	public String saveOcctemplate() throws WebException,SQLException{
		if(this.model.getId()!=null && !"".equals(this.model.getId())){
			ZybOcctemplate zybOcctemplate=this.zybOcctemplateService.queryByid(this.model.getId());
				zybOcctemplate.setExam_type(this.model.getExam_type());
				zybOcctemplate.setOccuphyexaclassid(this.model.getOccuphyexaclassid());
				zybOcctemplate.setTemplate(this.model.getTemplate());
				zybOcctemplate.setRemark(this.model.getRemark());
				this.zybOcctemplateService.updateOcctemplate(zybOcctemplate);
				this.message="修改成功!";
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb505");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
		}else{
			ZybOcctemplate zybOcctemplate=new ZybOcctemplate();
			zybOcctemplate.setId(this.model.getId());
			zybOcctemplate.setExam_type(this.model.getExam_type());
			zybOcctemplate.setOccuphyexaclassid(this.model.getOccuphyexaclassid());
			zybOcctemplate.setTemplate(this.model.getTemplate());
			zybOcctemplate.setRemark(this.model.getRemark());
			this.zybOcctemplateService.addOcctemplate(zybOcctemplate);
			this.message="添加成功!";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb505");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 *职业病团报模板删除
	 *zyb506
	     * @Title: deleteOcctemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteOcctemplate() throws WebException,SQLException {
		ZybOcctemplate c=this.zybOcctemplateService.queryByid(this.model.getId());
		this.zybOcctemplateService.deleteZybOcctemplate(c);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb506");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 职业病团报模板更新
	 * zyb503
	     * @Title: updateOcctemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateOcctemplate() throws WebException,SQLException{
		ZybOcctemplateDTO c=this.zybOcctemplateService.queryByID(this.model.getId());
		this.model.setId(c.getId());
		this.model.setExam_type_name(c.getExam_type_name());
		this.model.setExam_type(c.getExam_type());
		this.model.setOccuphyexaclass_name(c.getOccuphyexaclass_name());
		this.model.setOccuphyexaclassid(c.getOccuphyexaclassid());
		this.model.setTemplate(c.getTemplate());
		this.model.setRemark(c.getRemark());
		return SUCCESS;
	}
	
	/**
	 * 职业病团报模板 大类别、小类别联合重复验证
	 * zyb507
	     * @Title: validateOcctemplate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String validateOcctemplate() throws WebException, SQLException{
		int exam_type = this.model.getExam_type();
		String occuphyexaclassid = this.model.getOccuphyexaclassid();
		String id = this.model.getId();
		ZybOcctemplate l=this.zybOcctemplateService.queryByExam_typeAndOccuphyexaclassid(exam_type, occuphyexaclassid, id);
		if(l==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
}