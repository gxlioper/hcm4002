package com.hjw.wst.action;




import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.sf.json.JSONSerializer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.Language;
import com.hjw.wst.model.SystemInforsModel;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.SystemInformsService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SystemInformsUserDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebRoleDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.SystemInforms;
import com.hjw.wst.domain.SystemInforms_user;
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
 * @param <Department>
 */
@SuppressWarnings("rawtypes")
public class SystemInformsAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(this.getClass());
	private Logincheck logincheck;
	private SystemInforsModel model = new SystemInforsModel();
	private SystemInformsService systemInformsService;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;
	private String r_ids;
	private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public String getR_ids() {
		return r_ids;
	}

	public void setR_ids(String r_ids) {
		this.r_ids = r_ids;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}
	
	public SystemInformsService getSystemInformsService() {
			return systemInformsService;
		}
	
	public void setSystemInformsService(SystemInformsService systemInformsService) {
			this.systemInformsService = systemInformsService;
		}
	
	public SystemInforsModel getModel() {
			return model;
		}
	
	public void setModel(SystemInforsModel model) {
			this.model = model;
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
	/**
	 * 站内通知页面
	     * @Title: systeminformshow   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String systeminformshow() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("623");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	public String addSysInfo() throws WebException{
		return SUCCESS;
	}
	
	public String systemInformsList()throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list=this.systemInformsService.getSystemInformsLis(model, user, pageno, pagesize);
		this.outJsonResult(list);
		return NONE;
	}
	
	public String updateSysInform() throws WebException{
		SystemInforms s=this.systemInformsService.queryById(this.model.getId());
        this.model.setInform_content(s.getInform_content());
		this.model.setIs_active(s.getIs_active());
		if(s.getValid_date()!=null && !"".equals(s.getValid_date())){
			this.model.setValid_date(DateTimeUtil.shortFmt3(s.getValid_date()));
		}
		return SUCCESS;
	}
	
	//授权
	public String empowerInforms() throws WebException, Exception {

		return SUCCESS;
	}
	
	public String saveSystemInforms() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		
		if(this.model.getInform_content()==null || this.model.getInform_content().length()<=0){
			this.message="errot-没有消息内容,不能操作";
		}else{
			if((this.model.getIds()==null || this.model.getIds().trim().length()<=0) && (this.model.getR_ids()==null || this.model.getR_ids().trim().length()<=0)){
				this.message="errot-没有选择消息发送者,不能操作";
			}else{
				ArrayList<String> arrayList = new ArrayList<String>();
				ArrayList<String> arrayList2 = new ArrayList<String>();
				if(this.model.getR_ids()!=null || this.model.getR_ids().trim().length()>0){
					String[] r_ids = this.model.getR_ids().split(",");
					
					for (int i = 0; i <r_ids.length; i++) {
						List<SystemInformsUserDTO> list=this.systemInformsService.queryByRid(r_ids[i].trim());
						for (int j=0;j<list.size();j++){
							arrayList.add(list.get(j).getUser_id()+"");
						}
					} 
				}
				if(this.model.getIds()!=null || this.model.getIds().trim().length()>0){
					String[] u_id = this.model.getIds().split(",");
					for (int i = 0; i <u_id.length; i++) {
						if(u_id[i]!=null && u_id[i].trim().length()>0){
							arrayList.add(u_id[i]);
						}
					}
				}
				Iterator<String> it = arrayList.iterator();
				while(it.hasNext()) { 
					String obj = it.next();                
			      if(!arrayList2.contains(obj)) {            
			    	  arrayList2.add(obj);                
			      }
			    }
				SystemInforms s = new SystemInforms();
				
				s.setInform_content(this.model.getInform_content());
				s.setUpdate_time(DateTimeUtil.parse());
				s.setUpdater(user.getUserid());
				s.setIs_active(model.getIs_active());
				s.setValid_date(DateTimeUtil.parse2(model.getValid_date()));
				s.setCreate_time(DateTimeUtil.parse());
				s.setCreater(user.getUserid());
				
				if(this.model.getId()>0){
					//修改消息
					s.setId(model.getId());
					this.systemInformsService.updateSysInfo(s);
					this.systemInformsService.deleteSysinformUser(this.model.getId());
					
					for (int i = 0; i < arrayList2.size(); i++) {
						SystemInforms_user su = new SystemInforms_user();
						su.setUser_id(Integer.parseInt((String) arrayList2.get(i)));
						su.setInforms_id(s.getId());
						su.setUpdater(user.getUserid());
						su.setUpdate_time(DateTimeUtil.parse());
						su.setCreater(s.getCreater());
						su.setCreate_time(s.getCreate_time());
						this.systemInformsService.saveEmpower(su);
					}
					
					this.message="ok-编辑成功！";
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("625");//子功能id 必须填写
					sl.setExplain("修改");//操作说明
					syslogService.saveSysLog(sl);
				}else{
					//新增消息
				
					this.systemInformsService.addSystemInfo(s);
					
					for (int i = 0; i < arrayList2.size(); i++) {
						SystemInforms_user su = new SystemInforms_user();
						su.setUser_id(Integer.parseInt((String) arrayList2.get(i)));
						su.setInforms_id(s.getId());
						su.setUpdater(user.getUserid());
						su.setUpdate_time(DateTimeUtil.parse());
						su.setCreater(s.getCreater());
						su.setCreate_time(s.getCreate_time());
						this.systemInformsService.saveEmpower(su);
					}
					
					this.message = "ok-添加成功！";
					SysLog sl =  new SysLog();
					sl.setCenter_num(user.getCenter_num());
					sl.setUserid(user.getUserid()+"");
					sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
					sl.setXtgnid("");//可不填写
					sl.setXtgnid2("626");//子功能id 必须填写
					sl.setExplain("插入");//操作说明
					syslogService.saveSysLog(sl);
				}
				
				}
			
			}
	
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	public void up_off_on() throws WebException, Exception {
		Language.setLanguage(this.language);
		try {
			SystemInforms s=this.systemInformsService.queryById(this.model.getId());
			if(s.getIs_active().equals("Y"))
				s.setIs_active("N");
			else if(s.getIs_active().equals("N"))
				s.setIs_active("Y");
			this.systemInformsService.updateSysInfo(s);
			this.message="success";
		} catch (Exception e) {
			this.message="error";
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("627");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
	}
	//角色
	public String roleList() throws WebException, Exception {
		List<WebRoleDTO> list=this.systemInformsService.getAllRole();
		this.outJsonResult(list);
		return NONE;
	}
	
	//默认选中用户
	public String userListSystemInforms()throws WebException, Exception {
		
		List<SystemInformsUserDTO> list = this.systemInformsService.queryAlluser();
		List<SystemInformsUserDTO> sid = new ArrayList<SystemInformsUserDTO>();
		for (int i = 0; i < list.size(); i++) {
			SystemInformsUserDTO sdt = (SystemInformsUserDTO) list.get(i);
			List userList = this.systemInformsService.findSysteminforms_user(sdt.getUser_id(), this.model.getId());
			//System.out.println(sdt.getUser_id()+","+this.model.getId());
			if ((userList != null) && (userList.size() > 0)) {
				sdt.setSelected("checked");
			}
			sid.add(sdt);
		}
		String jsonObject = JSONSerializer.toJSON(sid).toString();
		this.outJsonResult(jsonObject);
		return NONE;
	}
	
	
	public String saveEmpowerSysInf() throws WebException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		//用户授权
		/*if ((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)) {
			String jsonObject = "没有选择人员，操作不能继续";
			this.outJsonStrResult(jsonObject);
			return NONE;
		}*/
		this.systemInformsService.deleteSysinformUser(this.model.getId());
		if ((this.model.getIds()==null)||(this.model.getIds().trim().length()<=0)) {
			//角色授权用户
			if((this.model.getR_ids()==null)||(this.model.getR_ids().trim().length()<=0)){
				String jsonObject = "没有选择任何角色或用户，操作不能继续";
				this.outJsonStrResult(jsonObject);
				return NONE;
			}
			String[] r_ids = this.model.getR_ids().split(",");
			for (int i = 0; i <r_ids.length; i++) {
				List<SystemInformsUserDTO> list=this.systemInformsService.queryByRid(r_ids[i].trim());
				for (int j=0;j<list.size();j++){
					SystemInforms_user us=new SystemInforms_user();
					us.setInforms_id(this.model.getId());
					us.setUser_id(list.get(j).getUser_id());
					us.setUpdater(user.getUserid());
					us.setUpdate_time(DateTimeUtil.parse());
					this.systemInformsService.saveEmpower(us);
				}
			}
		
		}else{
			
			String[] u_id = this.model.getIds().split(",");
			
			for (int i = 0; i <u_id.length; i++) {
				SystemInforms_user us=new SystemInforms_user();
				us.setInforms_id(this.model.getId());
				us.setUser_id(Integer.parseInt(u_id[i]));
				us.setUpdater(user.getUserid());
				us.setUpdate_time(DateTimeUtil.parse());
				this.systemInformsService.saveEmpower(us);
			}
		}

		String jsonObject = "授权成功";
		this.outJsonStrResult(jsonObject);
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("631");//子功能id 必须填写
		sl.setExplain("授权");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	public String deleteInforms() throws WebException{
		SystemInforms s=this.systemInformsService.queryById(this.model.getId());
		String deletemess = this.systemInformsService.deleteInforms(s);
		this.message=deletemess;
		this.outJsonStrResult(this.message);
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("633");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
}