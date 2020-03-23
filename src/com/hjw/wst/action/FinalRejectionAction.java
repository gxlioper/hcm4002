package com.hjw.wst.action;
import java.sql.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.FinalRejection;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.FinalRejectionModel;
import com.hjw.wst.service.FinalRejectionService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.WebSynchroService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 * @param <Department>
 */
@SuppressWarnings("rawtypes")
public class FinalRejectionAction  extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	
	private FinalRejectionModel model = new FinalRejectionModel();
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	 private SyslogService syslogService;
	 private WebSynchroService webSynchroService;

	   	public WebSynchroService getWebSynchroService() {
		return webSynchroService;
	}

	public void setWebSynchroService(WebSynchroService webSynchroService) {
		this.webSynchroService = webSynchroService;
	}

	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	private FinalRejectionService finalRejectionService;

	public FinalRejectionService getFinalRejectionService() {
		return finalRejectionService;
	}

	public void setFinalRejectionService(FinalRejectionService finalRejectionService) {
		this.finalRejectionService = finalRejectionService;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}



	public Object getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	
	public void setModel(FinalRejectionModel model) {
		this.model = model;
	}

	/**
	 * 
	     * @Title: finalRejection  
	     * 驳回管理主界面 （100）
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String finalRejection() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username"); 
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2112");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	/**
	 * 
	     * @Title: finalRejectionList  
	     * 驳回意见列表分页 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String finalRejectionList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username"); 
		String reject_context = this.model.getReject_context();
		PageReturnDTO list = this.finalRejectionService.finalRejectionList(reject_context, rows, page);
		this.outJsonResult(list);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2113");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: finalRejectionList  
	     * 驳回意见新增 
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String rejectadd() throws WebException{
		UserDTO user = (UserDTO) session.get("username"); 
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2114");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
		/**
		 * 
	     * @Title: finalRejectionList  
	     * 驳回意见新增保存 、修改
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveRejection() throws WebException, ServiceException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		if(this.model.getId() > 0){
			FinalRejection reject = this.finalRejectionService.serchRejectionById(this.model.getId());
			reject.setReject_context(model.getReject_context());
			reject.setUpdater(user.getUserid());
			reject.setUpdate_time(DateTimeUtil.parse());
			this.finalRejectionService.updateFinalRejection(reject);
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("109");//子功能id 必须填写
			sl.setExplain("修改驳回意见 ");//操作说明
			syslogService.saveSysLog(sl);
			this.message="ok-修改成功！";
		}else{
			FinalRejection reject = new FinalRejection();
			reject.setReject_context(model.getReject_context());
			reject.setCreater(user.getUserid());
			reject.setCreate_time(DateTimeUtil.parse());
			reject.setIs_Active("Y");
			this.finalRejectionService.addFinalRejection(reject);
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("2116");//子功能id 必须填写
			sl.setExplain("添加驳回意见 ");//操作说明
			syslogService.saveSysLog(sl);
			this.message = "ok-添加成功！";
		}
		
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
	
		/**
		 * 
	     * @Title: rejectudater  
	     * 驳回意见修改
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String rejectudater() throws WebException{
		UserDTO user = (UserDTO) session.get("username"); 
		FinalRejection reject = this.finalRejectionService.serchRejectionById(this.model.getId());
		this.model.setReject_context(reject.getReject_context());
		
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2115");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
}
	
		/**
		 * @throws SQLException 
		 * @throws ServiceException 
		 * 
	     * @Title: deleteDept   
	     * @Description: 驳回意见删除(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deletereject() throws WebException, ServiceException, SQLException{
		FinalRejection reject = this.finalRejectionService.serchRejectionById(this.model.getId());
		reject.setIs_Active("N");
		this.finalRejectionService.updateFinalRejection(reject);
		this.message="删除成功！";
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("2117");//子功能id 必须填写
		sl.setExplain("删除驳回意见 ");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}
