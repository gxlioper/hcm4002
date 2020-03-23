package com.hjw.wst.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SchedulingDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.NoteData;
import com.hjw.wst.domain.Scheduling;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.SchedulingModel;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;



/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @date:   2016年8月22日 下午12:11:35   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class SchedulingAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private SchedulingModel model = new SchedulingModel();
	private DepartmentService departmentService;
	private SyslogService syslogService;   
	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd"); 
   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}


	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(SchedulingModel model) {
		this.model = model;
	}

	/**
	 * 
	     * @Title: scheduling  
	     * 排班管理主界面 （100）
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String scheduling() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("100");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	/**
	 * 备忘录634
	     * @Title: newNotes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String newNotes() throws WebException {
		
		return SUCCESS;
	}	
	
	/**
	 * 个人排班
	     * @Title: schdeulingAchieve   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String schdeulingAchieve() throws WebException {
		this.model.setWorking_date(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	/**
	 * 批量排班
	     * @Title: schedulingBatch   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String schedulingBatch()throws WebException {
		return SUCCESS;
	}
	/**
	 * 查看排班
	     * @Title: lookscheduledetails   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      scheduledetailsList
	     * @throws
	 */
	public String lookscheduledetails()throws WebException {
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: departscheduling   
	     * @Description: 科室下拉列表  103(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String departscheduling()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		List<DepartmentDepDTO> list=this.departmentService.queryAllDepartmentDep(user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * shouye
	     * @Title: schedulinglist   
	     * @Description: 排班列表102(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String schedulinglist()  throws WebException {
		String user_name = this.model.getUser_name();
		PageReturnDTO list=this.departmentService.schedulinglist(user_name,this.rows, this.getPage());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 保存排班日期
	     * @Title: saveScheduling   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveScheduling()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		
		String[] works = this.model.getWorking_date().split(",");
		String[] userids = this.model.getUser_id_s().split(",");
		
		for (int i = 0; i < userids.length; i++) {
			
			departmentService.deleteSched(userids[i], works[0].split("-")[1], works[0].split("-")[0]);
			
			for (int j = 0; j < works.length; j++) {
				
				Scheduling scheduling = new Scheduling();
				scheduling.setUser_id(Integer.parseInt(userids[i]));
				scheduling.setWorking_date(DateTimeUtil.parse2(works[j]));
				scheduling.setWorking_type("值班");
				scheduling.setCreater(user.getUserid());
				scheduling.setCreate_time(DateTimeUtil.parse());
				scheduling.setUpdater(user.getUserid());
				scheduling.setUpdate_time(DateTimeUtil.parse());
				this.departmentService.saveschedu(scheduling);
			}
			
		}
		this.message = "排班成功！";
		this.outJsonStrResult(message);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("111");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String scheduledetailsList()throws WebException{
		//List<SchedulingDTO> list = this.departmentService.getScheduledetails(this.model.getUser_id());
		List<SchedulingDTO> list = this.departmentService.chakanpaiabn(this.model.getUser_id(),this.model.getM_id(),this.model.getY_id());
		this.outJsonResult(list);
		return NONE;
	}
	
	public String paibanList()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			List list = this.departmentService.querypaibanList(user.getUserid(), user,this.model.getM_id(),this.model.getY_id());
			this.outJsonResult(list);
		}else{
			this.outJsonResult(new ArrayList());
		}
		return NONE;
	}
	/**
	 * 保存备忘录
	     * @Title: saveNotes   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveNotes() throws WebException{
		
		UserDTO user = (UserDTO) session.get("username");
		
		NoteData n=this.departmentService.queryDate(this.model.getNotes_date());
		if(n!=null){
			n.setNotes_content(model.getNotes_content());
			n.setNotes_date(DateTimeUtil.parse2(model.getNotes_date()));
			n.setUpdater(user.getUserid());
			n.setUpdate_time(DateTimeUtil.parse());
			this.departmentService.updateNote(n);
			this.message = "编辑成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("635");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			n= new NoteData();
			n.setNotes_content(model.getNotes_content());
			n.setNotes_date( DateTimeUtil.parse2(model.getNotes_date()));
			n.setCreater(user.getUserid());
			n.setUser_id(user.getUserid());
			n.setCreate_time(DateTimeUtil.parse());
			this.departmentService.addNoteData(n);
			this.message = "添加成功！";
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("635");//子功能id 必须填写
			sl.setExplain("插入");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 加载一周排班信息
	     * @Title: yizhoubeiwang   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String yizhoubeiwang()  throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if(user != null){
			List list = this.departmentService.querybeiwangList(user.getUserid(), user,this.model.getWk_id());
			this.outJsonResult(list);
		}else{
			this.outJsonResult(new ArrayList());
		}
		
		return NONE;
	}
	
	public String updaterNotes() throws WebException{
		NoteData nd=this.departmentService.queryDate(this.model.getNotes_date());
		this.model.setNotes_date(DateTimeUtil.shortFmt3(nd.getNotes_date()));
		this.model.setNotes_content(nd.getNotes_content());
		return SUCCESS;
}
	
}