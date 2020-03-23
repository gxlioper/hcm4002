package com.hjw.wst.action;

import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.util.Base64;
import com.hjw.util.DateTimeUtil;
import com.hjw.util.EncryptUtils;
import com.hjw.util.Language;
import com.hjw.util.UserInfoName;
import com.hjw.util.ValidateTime;
import com.hjw.wst.DTO.DepUserDTO;
import com.hjw.wst.DTO.GridListDTO;
import com.hjw.wst.DTO.LogItem;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.UserInfoDTO;
import com.hjw.wst.DTO.WebXtgncdDTO;
import com.hjw.wst.domain.ExamInfo;
import com.hjw.wst.domain.Exam_user;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.WebUserInfo;
import com.hjw.wst.model.UserInfoModel;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.MenuGnService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.hjw.wst.service.WebConfigService;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;
import com.synjones.framework.aop.SystemType;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.util.BeanUtil;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


@SuppressWarnings("rawtypes")
public class UserManagerAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
    private Logincheck logincheck;
    private UserInfoService userInfoService;
    private MenuGnService menuGnService;
    private UserInfoModel model=new UserInfoModel();
    private String area1;
    private String area2;
    private int rp=15;  //每页显示的条数
    private int page=1; //当前页
    private int rows=15; //easyui每页显示条数
    private String jobListJson;
    private LogItem logItem = new LogItem();
	private WebConfigService webConfigService;
	private CommService commService;
	private SyslogService syslogService;
	private String sjson;
	
	
	 public String getSjson() {
		return sjson;
	}
	public void setSjson(String sjson) {
		this.sjson = sjson;
	}
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	public void setCommService(CommService commService) {
		this.commService = commService;
	}
	public void setWebConfigService(WebConfigService webConfigService) {
		this.webConfigService = webConfigService;
	}
	public LogItem getLogItem() {
		return logItem;
	}
	public void setLogItem(LogItem logItem) {
		this.logItem = logItem;
	}
	@Override
	public int getRp() {
		return rp;
	}

	@Override
	public void setRp(int rp) {
		this.rp = rp;
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
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
	 * 显示用户个人信息，不做权限限制
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String oneusershow() throws WebException
	{
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		UserDTO user = (UserDTO) session.get("username");
		if (user.getUserid() >0) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(user.getUserid());
			BeanUtil.copy(model, wr);
			model.setSusertype(UserInfoName.getUserTypeName(wr.getChi_Name()));
		}
		return SUCCESS;		
	}
	/**
	 * 用户修改个人信息，不做权限限制
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("unchecked")
	public String oneuseredit() throws WebException
	{
		Language.setLanguage(this.language);
		this.session.remove("language");
		this.session.put("language", language);
		UserDTO user = (UserDTO) session.get("username");
		if (user.getUserid() >0) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(user.getUserid());
			BeanUtil.copy(model, wr);
			model.setSusertype(UserInfoName.getUserTypeName(wr.getChi_Name()));
		}
		return SUCCESS;		
	}
	
	/**
	 * 执行修改或者增加用戶信息
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public String oneusereditdo() throws WebException, Exception {
		UserDTO user = (UserDTO) session.get("username");
		if (user.getUserid()>0) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(user.getUserid());
			wr.setEmail(this.model.getEmail());
			wr.setLog_Name(model.getUsername());
			wr.setChi_Name(model.getName());
			wr.setEmail(model.getEmail());
			wr.setPhone_num(model.getTel1());
			wr.setExam_center_id(user.getCenter_id());
			wr.setPwd_encrypted(model.getPasswd());
			wr.setCreater(user.getUserid());
			wr.setUpdater(user.getUserid());
			wr.setUpdate_Time(DateTimeUtil.parse());
			wr.setIs_active("Y");
			this.userInfoService.updateUserInfo(wr);
			String jsonObject = "修改信息操作成功！";
			this.outJsonStrResult(jsonObject);
			return NONE;
		} else {
			String jsonObject = "无此用户！";
			this.outJsonStrResult(jsonObject);
			return NONE;
		}

	}

	/**
	 * 获取菜单 功能编码：
	 * 
	 * @return
	 * @throws WebException
	 * @throws SQLException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String menu() throws WebException, SQLException {
		UserDTO user = (UserDTO)session.get("username");
		List<WebXtgncdDTO> ls = new ArrayList<WebXtgncdDTO>();
		List<WebXtgncdDTO> lsnew = new ArrayList<WebXtgncdDTO>();

			String sPath = request.getContextPath();
			ls = this.menuGnService.findWebgnList(user.getRolestr(), user.getXtgn(), sPath,"");

			for (int i = 0; i < ls.size(); i++) {
				WebXtgncdDTO wx = ls.get(i);
				long le = wx.getLevels() - 1;
				long pop = wx.getIspop() - 1;
				if ((wx.getOther_url() == null) || ("".equals(wx.getOther_url()))
						|| ("null".equals(wx.getOther_url()))) {
					wx.setOther_url("#");
				}
				lsnew.add(wx);
			}
			String jsonString = JSONSerializer.toJSON(lsnew).toString();
			this.outJsonStrResult(jsonString);
			return NONE;
	}

	/**
	 * 显示角色列表 角色管理，对应功能15
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	public String usermainshow() throws WebException, Exception {
	
		Language.setLanguage(this.language);
		UserDTO user = (UserDTO)session.get("username");
		PageReturnDTO usermain = new PageReturnDTO();
		String chi_name=this.model.getChi_Name();
		String log_name=this.model.getLog_Name();
		String phone_num=this.model.getPhone_num();
		String dep_name=this.model.getDep_name();
		String role_name=this.model.getRole_name();
		String startStop=this.model.getStartStop();
		usermain = this.userInfoService.getUserInfoList(chi_name,log_name,  phone_num,dep_name,role_name,this.rows, this.getPage(),startStop,user.getCenter_num());
		List mapList = usermain.getRows();
		List rows = new ArrayList();
		for (int i = 0; i < mapList.size(); i++) {
			UserInfoDTO wr = new UserInfoDTO();
			wr = (UserInfoDTO) mapList.get(i);
			rows.add(wr);
		}
		usermain.setRows(rows);
		String jsonObject = JSONSerializer.toJSON(usermain).toString();
		this.outJsonStrResult(jsonObject);
		return NONE;
	}
	
	/**
	 * 锁定人员
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public void lockuser() throws WebException, Exception {
		Language.setLanguage(this.language);
		try {
			WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));

			if(wr.getIs_active().equals("Y"))
				wr.setIs_active("N");
			else if(wr.getIs_active().equals("N"))
				wr.setIs_active("Y");
			this.userInfoService.updateUserInfo(wr);
			this.message="success";
		} catch (Exception e) {
			this.message="error";
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		
	}
	
	/**
	 * 删除人员
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public void deluser() throws WebException, Exception {
		this.defaultapp=(SystemType) session.get("defaultapp");
		try {
			if(Integer.valueOf(this.model.getId())==1){//管理员
				this.message="管理员用户，不能删除！";
			}else{
			    WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
				this.menuGnService.delUserRole(this.model.getId(),this.defaultapp.getComid());
				this.userInfoService.delUserInfo(wr);
				this.message="删除用户成功！";
				 //初始化日志对象
			}
		} catch (Exception e) {
			this.message="删除用户失败！";
			e.printStackTrace();
		}
			
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
	}
	//添加/修改页面跳转（原）
	public String useredit() throws WebException{
			Language.setLanguage(this.language);
			try {
				if ((this.model.getId() != null) && (!"".equals(this.model.getId()))) {
					WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
					if(this.logincheck.getAdminUserId()==wr.getId())
					{
						this.message = "不允许对管理员用户操作。";
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().print(new Gson().toJson(this.message));
						
					}
						
						model.setUsername(wr.getLog_Name());
						model.setEmail(wr.getEmail());
						model.setId(wr.getId()+"");
						model.setName(wr.getChi_Name());
						model.setTel1(wr.getPhone_num());
						model.setWork_num(wr.getWork_num());
						UserDTO user = (UserDTO)session.get("username");
						wr.setUpdater(user.getUserid());
						wr.setUpdate_Time(DateTimeUtil.parse());
						this.userInfoService.updateUserInfo(wr);
						this.message="修改个人信息成功！";					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return SUCCESS;
			
	}
//修改页面跳转（新）
	public String usereditupdate() throws WebException{
		Language.setLanguage(this.language);
		try {
			if ((this.model.getId() != null) && (!"".equals(this.model.getId()))) {
				WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
				if(this.logincheck.getAdminUserId()==wr.getId())
				{
					this.message = "不允许对管理员用户操作。";
					response.setContentType("text/html;charset=utf-8");
					response.getWriter().print(new Gson().toJson(this.message));
					
				}
					
					model.setUsername(wr.getLog_Name());
					model.setEmail(wr.getEmail());
					model.setId(wr.getId()+"");
					model.setName(wr.getChi_Name());
					model.setTel1(wr.getPhone_num());
					model.setWork_num(wr.getWork_num());
					model.setUser_notices(wr.getUser_notices());
					model.setUser_pic_path(wr.getUser_pic_path());
					UserDTO user = (UserDTO)session.get("username");
					wr.setUpdater(user.getUserid());
					wr.setUpdate_Time(DateTimeUtil.parse());
					this.userInfoService.updateUserInfo(wr);
					this.message="修改个人信息成功！";					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
		
}
    //修改密码zt
	
	public String updateuserpwd() throws WebException, SQLException {
		return SUCCESS;
	}
	//保存修改密码
	public void usereditpwd() throws WebException, Exception {
		
		Language.setLanguage(this.language);
		UserDTO user = (UserDTO)session.get("username");

		if ((this.model.getId() != null) && (!"".equals(this.model.getId()))) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
			try {
				wr.setEmail(this.model.getEmail());
				String newpasswd=EncryptUtils.encryptMD5(model.getPasswd().trim()).toLowerCase();
				wr.setPwd_encrypted(newpasswd);
				wr.setUpdater(user.getUserid());
				wr.setUpdate_Time(DateTimeUtil.parse());
				this.userInfoService.updateUserInfo(wr);
				this.message="修改密码成功！";
				
				 //初始化日志对象
		        logItem.setFlag(true);
		        logItem.setOperId(wr.getId()+"");
		        logItem.setOperName(wr.getChi_Name());
			} catch (Exception e) {
				this.message="修改用户失败！";
				e.printStackTrace();
			}		
		} 
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
	}
	
	
	
	/**
	 * 修改个人密码
	 */
	public String updatePasswd(){
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String updatePasswddo() throws IOException{
		UserDTO user = (UserDTO)session.get("username");
		WebUserInfo wr = this.userInfoService.loadUserInfo(user.getUserid());
		try{
			String oldpasswd=EncryptUtils.encryptMD5(this.model.getUsername()).toLowerCase();
			if(!oldpasswd.equals(wr.getPwd_encrypted().toLowerCase()))
			{
				this.message="修改密码失败：原密码错误！";
			}else if (StringUtils.isBlank(this.model.getPasswd())){
				this.message="修改密码失败：新密码不能为空！";
			}else{
				String newpasswd=EncryptUtils.encryptMD5(this.model.getPasswd()).toLowerCase();
			    wr.setPwd_encrypted(newpasswd);			
			   this.userInfoService.updateUserInfo(wr);	
			   this.message="修改密码成功！";
			}
		} catch (Exception e) {
			this.message="修改修改密码失败！";
			e.printStackTrace();
		}		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 修改个人信息
	 */
	public String updatePerson(){
		UserDTO user = (UserDTO)session.get("username");
		model.setUsername(user.getUsername());
		model.setEmail(user.getEmail());
		model.setRemark1(user.getName());
		model.setUsertype(Integer.valueOf(user.getUsertype()));
		model.setTel1(user.getTel());
		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String updatePersondo() throws IOException{
		UserDTO user = (UserDTO)session.get("username");
		WebUserInfo wr = this.userInfoService.loadUserInfo(user.getUserid());
			wr.setEmail(this.model.getEmail());
			wr.setEmail(model.getEmail());
			wr.setPhone_num(model.getTel1());
			wr.setIs_active("Y");
			wr.setChi_Name(model.getRemark1());
			wr.setUpdater(user.getUserid());
			wr.setUpdate_Time(DateTimeUtil.parse());
			this.userInfoService.updateUserInfo(wr);
			
			user.setName(wr.getChi_Name());
			user.setUsername(wr.getLog_Name());
			user.setTel(wr.getPhone_num());
			user.setEmail(wr.getEmail());
			this.message="修改个人信息成功！";
			this.session.remove("username");
			this.session.put("username", user);
			
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 判断增加的登录名称是否已存在
	 * @throws IOException 
	 */
	public void checkUsername() throws IOException{
		WebUserInfo urtest = this.userInfoService.getUserInfoByname(model.getUsername());
		if(urtest==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
		return;
		
	}
	
	/**
	 * 执行修改或者增加用戶信息
	 * 保存添加 /修改信息
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	public void usereditdo() throws WebException, Exception {		
		Language.setLanguage(this.language);
		UserDTO user = (UserDTO)session.get("username");
		if ((this.model.getId() != null) && (!"".equals(this.model.getId()))) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
			try {
				String pas = this.model.getPasswd().replaceAll(" ", "");
				if (!"".equals(pas) && pas!= null ) {
					String newpasswd=EncryptUtils.encryptMD5(pas).toLowerCase();
					wr.setPwd_encrypted(newpasswd);
				}
				wr.setEmail(this.model.getEmail());
				//String newpasswd=EncryptUtils.encryptMD5(model.getPasswd().trim()).toLowerCase();
				//wr.setPwd_encrypted(newpasswd);
				wr.setChi_Name(model.getName());
				wr.setLog_Name(model.getUsername());
				wr.setEmail(model.getEmail());
				wr.setPhone_num(model.getTel1());
				wr.setExam_center_id(user.getCenter_id());
				wr.setIs_active("Y");
				wr.setUpdater(user.getUserid());
				wr.setUpdate_Time(DateTimeUtil.parse());
				//wr.setWork_num(model.getWork_num());
				wr.setUser_notices(this.model.getUser_notices());
				wr.setUser_pic_path(this.model.getUser_pic_path_edit());
				this.userInfoService.updateUserInfo(wr);
				this.message="修改用户成功！";
				
				 //初始化日志对象
		        logItem.setFlag(true);
		        logItem.setOperId(wr.getId()+"");
		        logItem.setOperName(wr.getChi_Name());
			} catch (Exception e) {
				this.message="修改用户失败！";
				e.printStackTrace();
			}		
			
		} else {
			WebUserInfo wr = new WebUserInfo();
			wr.setEmail(this.model.getEmail());
			String newpasswd=EncryptUtils.encryptMD5(model.getPasswd().trim()).toLowerCase();
			wr.setPwd_encrypted(newpasswd);
			wr.setChi_Name(model.getName());
			wr.setLog_Name(model.getUsername());
			wr.setEmail(model.getEmail());
			wr.setExam_center_id(user.getCenter_id());
			wr.setPhone_num(model.getTel1());
			wr.setIs_active("Y");
			wr.setCreater(user.getUserid());
			wr.setCreate_Time(DateTimeUtil.parse());
			wr.setUpdater(user.getUserid());
			wr.setUpdate_Time(DateTimeUtil.parse());
			wr.setWork_num(model.getWork_num());
			wr.setUser_notices(this.model.getUser_notices());
			wr.setUser_pic_path(model.getUser_pic_path());
			WebUserInfo urtest = this.userInfoService.getUserInfoByname(model.getUsername());
			if(urtest!=null)
			{
				this.message="该登录名称已存在！";
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(new Gson().toJson(this.message));
				return;
				
			}else{
				try {
					wr = this.userInfoService.saveUserInfo(wr);
					Exam_user eu=new Exam_user();
					eu.setCenter_num(user.getCenter_num());
					eu.setExam_center_id(user.getCenter_id());
					eu.setIs_default("0");
					eu.setUser_id(wr.getId());
					this.userInfoService.saveExamUser(eu);
					this.message="新增用户成功！";
					 //初始化日志对象
			        logItem.setFlag(true);
			        logItem.setOperId(wr.getId()+"");
			        logItem.setOperName(wr.getChi_Name());
				} catch (Exception e) {
					this.message="新增用户失败！";
					e.printStackTrace();
				}
			}
		}
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
	}
	

	/**
	 * 获得用户头信息 
	 * 
	 * @return
	 * @throws WebException
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings({ "unchecked" })
	public String usermainheader() throws WebException {
		List<GridListDTO> strlist = new ArrayList();
		GridListDTO ls = new GridListDTO();
		ls.setTitle("登录名称");
		ls.setField("username");
		ls.setWidth(80);
		ls.setSortable(true);
		ls = new GridListDTO();
		ls.setTitle("姓名");
		ls.setField("name");
		ls.setWidth(80);
		ls.setSortable(true);
		ls.setAlign("left");
		strlist.add(ls);

		ls = new GridListDTO();
		ls.setTitle("电话1");
		ls.setField("tel1");
		ls.setWidth(100);
		ls.setSortable(true);
		ls.setAlign("right");
		strlist.add(ls);

		ls = new GridListDTO();
		ls.setTitle("电话2");
		ls.setField("tel1");
		ls.setWidth(90);
		ls.setSortable(true);
		ls.setAlign("right");
		strlist.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("Email");
		ls.setField("email");
		ls.setWidth(90);
		ls.setSortable(true);
		ls.setAlign("right");
		strlist.add(ls);
		
		
		ls = new GridListDTO();
		ls.setTitle("授权");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("授权商户");
		ls.setField("publishdate");
		ls.setWidth(45);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("启用/停用");
		ls.setField("publishdate");
		ls.setWidth(65);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("修改");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);
		
		ls = new GridListDTO();
		ls.setTitle("删除");
		ls.setField("publishdate");
		ls.setWidth(25);
		ls.setSortable(true);
		ls.setAlign("center");
		strlist.add(ls);
		
		String jsonString = JSONSerializer.toJSON(strlist).toString();
		this.outJsonStrResult(jsonString);
		return NONE;
	}
	/**
	 * 员工编码验证
	     * @Title: isUniqueUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String isUniqueUser() throws WebException{
		WebUserInfo wr = this.userInfoService.queryByNum(model.getWork_num());
		if(wr==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 查询科室列表和用户科室信息  17
	     * @Title: getAllDepAndDepUser   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getAllDepAndDepUser(){
		UserDTO user = (UserDTO) session.get("username");
		this.defaultapp =(SystemType) session.get("defaultapp");
		UserInfoDTO utdo= new UserInfoDTO();
		utdo=this.userInfoService.getUsername(user.getUsername());
		List<DepUserDTO> list= new ArrayList<DepUserDTO>();
		if(utdo.getExam_center_id()==0){
			list = this.userInfoService.getAllDepartmenterDep(user.getCenter_num());
		}else{
			list = this.userInfoService.getDepartmenterDepForUserId(user.getUserid(),user.getCenter_num());
		}
		for(DepUserDTO depuser : list){
			DepUserDTO du = this.userInfoService.getDepUser(this.model.getId(), depuser.getDep_id(),this.defaultapp.getComid());
			if(du == null){
				depuser.setChecked(false);
			}else{
				depuser.setChecked(true);
			}
		}
		this.outJsonResult(list);
		return NONE;
	}
	
	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}

	public void setMenuGnService(MenuGnService menuGnService) {
		this.menuGnService = menuGnService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(UserInfoModel model) {
		this.model = model;
	}


	public String getArea1() {
		return area1;
	}

	public void setArea1(String area1) {
		this.area1 = area1;
	}

	public String getArea2() {
		return area2;
	}

	public void setArea2(String area2) {
		this.area2 = area2;
	}

	public String getJobListJson() {
		return jobListJson;
	}

	public void setJobListJson(String jobListJson) {
		this.jobListJson = jobListJson;
	}
	public void usereditdo_w() throws WebException, Exception {
		Language.setLanguage(this.language);
		UserDTO user = (UserDTO)session.get("username");

		if ((this.model.getId() != null) && (!"".equals(this.model.getId()))) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
			try {
				wr.setEmail(this.model.getEmail());
				//String newpasswd=EncryptUtils.encryptMD5(model.getPasswd().trim()).toLowerCase();
				//wr.setPwd_encrypted(newpasswd);
				/*wr.setChi_Name(model.getName());
				wr.setLog_Name(model.getUsername());
				wr.setEmail(model.getEmail());
				wr.setPhone_num(model.getTel1());
				wr.setExam_center_id(user.getCenter_id());
				wr.setIs_active("Y");
				wr.setUpdater(user.getUserid());
				wr.setUpdate_Time(DateTimeUtil.parse());*/
				wr.setWork_num(model.getWork_num());
				this.userInfoService.updateUserInfo(wr);
				this.message="编辑成功！";
				
				 //初始化日志对象
		        logItem.setFlag(true);
		        logItem.setOperId(wr.getId()+"");
		        logItem.setOperName(wr.getChi_Name());
			} catch (Exception e) {
				this.message="编辑失败！";
				e.printStackTrace();
			}		
			
		} 
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
	}
	
	
	/**
	 * 
	     * @Title: managersignupdate   
	     * @Description: 用户签名保存 823  
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String managersignupdate() throws WebException, SQLException {
		String picpath = saveuserPic(this.model.getId(),this.model.getUser_signature(), this.model.getRemark1());
		this.outJsonStrResult(picpath);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: saveExamInfoPic   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param examid
	     * @param: @param picstring
	     * @param: @param flags
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	private String saveuserPic(String userid,String signstr, String picstring) {
		String filepath = "";
		//System.out.println("0000:="+picstring);
		if ((picstring != null) && (picstring.length() > 100)) {
			String bmpfiledata = picstring;			
			try {
						byte[] bmpfiledata64 = Base64.base64Decode(bmpfiledata);
						String picpath = this.commService.getDatadis("TPLJ").getName();
						String path = picpath + "\\";
						filepath = "usersign-picture/" + userid;
						
						File f = new File(path);
						if (!f.exists() && !f.isDirectory())
							f.mkdir();
						f = new File(path+"\\usersign-picture");

						if (!f.exists() && !f.isDirectory())
							f.mkdir();
						path = path + filepath;						
						
						f = new File(path+".gif");
						if (f.exists() && f.isFile())
							f.delete();
						
						FileOutputStream fos = new FileOutputStream(path+".gif");
						// 用FileOutputStream 的write方法写入字节数组
						fos.write(bmpfiledata64);
						System.out.println("写入成功");
						fos.close();
						
						
						 JPGOptions options = new JPGOptions();  
				         options.setQuality(75);  
				         ImageProducer image = Jimi.getImageProducer(path+".gif"); 				         
				         JimiWriter writer = Jimi.createJimiWriter(path+".jpg");  
				         writer.setSource(image);  
				         writer.setOptions(options);  
				         writer.putImage(path+".jpg"); 
						
				         f = new File(path+".gif");
						 if (f.exists() && f.isFile())
						 f.delete();
						
						WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
						wr.setUser_signature(signstr);
						wr.setSignpicpath(filepath+".jpg");
						System.out.println(signstr.length());
						this.userInfoService.saveUserInfo(wr);
						UserDTO user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("2");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("823");// 子功能id 必须填写
						sl.setExplain(wr.getChi_Name()+" "+wr.getLog_Name());// 操作说明
						syslogService.saveSysLog(sl);
						return "ok-"+this.model.getId()+"-签名信息保存成功";
			} catch (Exception e) {
				e.printStackTrace();
				return "error-保存文件失败";
			}
		}else{
			return "error-签名图片不存在";
		}
	}
	
	/**
	 * 
	     * @Title: getuserPhoto   
	     * @Description: 获取用户签名图片 824   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getuserPhoto() throws WebException {
		if ((this.model.getId() != null) && (this.model.getId().trim().length() > 0)) {
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\usersign-picture\\"+this.model.getId().trim()+".jpg";
				File f = new File(path);
				if (f.exists()) {
					FileInputStream fileInput = new FileInputStream(path);
					int i = fileInput.available();
					byte[] content = new byte[i];
					fileInput.read(content);
					HttpServletResponse response = ServletActionContext.getResponse();
					response.reset();
					response.setContentType("image/jpeg");
					ServletOutputStream sos = response.getOutputStream();
					sos.write(content);
					sos.flush();
					sos.close();
				}else{
					String dbXmlPath = ServletActionContext.getServletContext().getRealPath("/sign/images/none.gif");
					f = new File(dbXmlPath);
					if (f.exists()) {
						FileInputStream fileInput = new FileInputStream(dbXmlPath);
						int i = fileInput.available();
						byte[] content = new byte[i];
						fileInput.read(content);
						HttpServletResponse response = ServletActionContext.getResponse();
						response.reset();
						response.setContentType("image/jpeg");
						ServletOutputStream sos = response.getOutputStream();
						sos.write(content);
						sos.flush();
						sos.close();
					}
				}
			} catch (Exception ex) {

			}
		}
		return NONE;
	}
	
	public String usereditdept() throws WebException, UnsupportedEncodingException{
		String name = new String(model.getChi_Name().getBytes("ISO-8859-1"), "utf-8");
		model.setChi_Name(name);
		model.setId(model.getId());
		return SUCCESS;
		}
	/**
	 * 
	     * @Title: saveUserExamDept   
	     * @Description: 给用户分配部门   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveUserExamDept()throws WebException{
		String id=model.getId();
		String[] strs={};
		strs=id.split(",");
		for(int i=0;i<strs.length;){
			this.userInfoService.saveUserExamDept(strs[i], strs[i+1]);
			i=i+2;
		}
		this.outJsonStrResult(this.message="分配成功");	
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm106");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
		}
	
	/**
	 * 科室打印
	     * @Title: getdep_user_print   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getdep_user_print(){
		UserDTO user = (UserDTO) session.get("username");
		List<DepUserDTO> list = this.userInfoService.getAllDepartmenterDep(user.getCenter_num());
		for(DepUserDTO depuser : list){
			DepUserDTO du = this.userInfoService.getDepUserPrint(this.model.getId(), depuser.getDep_id(),user.getCenter_num());
			if(du == null){
				depuser.setChecked(false);
			}else{
				depuser.setChecked(true);
			}
		}
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: managersignshow   
	     * @Description: 用户签名页面 822   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String managersignshow() throws WebException{ 
		if ((this.model.getId() != null) && (!"".equals(this.model.getId()))) {
			WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
			try {
				this.model.setChi_Name(wr.getChi_Name());
				this.model.setEmail(wr.getEmail());
				model.setUsername(wr.getLog_Name());
				model.setTel1(wr.getPhone_num());
				model.setUser_signature(wr.getUser_signature());
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl = new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid() + "");
				sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 5，登录 9 其他
				sl.setXtgnid("");// 可不填写
				sl.setXtgnid2("822");// 子功能id 必须填写
				sl.setExplain(wr.getChi_Name()+" "+wr.getLog_Name());// 操作说明
				syslogService.saveSysLog(sl);
				return SUCCESS;
			} catch (Exception e) {
				this.message="用户查询失败！";
				return ERROR;
			}		
			
		}else{
			this.message="用户查询失败！";
			return ERROR;
		}		
	}
	//上传电子签名
	public  String  saveUserSign(){
		String formatName = "jpg";
		response.setContentType("text/html;charset=UTF-8");
		String filepath = "";
		JSONObject json = new JSONObject();
		try {
			String picpath = this.commService.getDatadis("TPLJ").getName();
			String path = picpath + "\\";
			File f_dir = new File(path);
			if (!f_dir.exists()) {
				f_dir.mkdirs();
			}
			f_dir = new File(path + "usersign-picture\\");
			if (!f_dir.exists()) {
				f_dir.mkdirs();
			}

			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
			UploadedFile[] files = multiWrapper.getFiles("file");

			if ((files != null) && (files.length > 0)) {
				InputStream sis1 = new FileInputStream(files[0].getAbsolutePath());
				filepath = "usersign-picture/" + this.model.getId()+".jpg";
				path = path + filepath;
				File file = new File(path);
				file.createNewFile();
				BufferedImage image = ImageIO.read(sis1);
				
					boolean writerExists = ImageIO.write(image, formatName, file);
					if (writerExists) {
							if (this.model.getId() !=  null && !"".equals(this.model.getId())) {
								WebUserInfo wr = this.userInfoService.loadUserInfo(Integer.valueOf(this.model.getId()));
								wr.setSignpicpath(filepath);
								this.userInfoService.saveUserInfo(wr);
							}
						json.put("msg", "上传保存成功");
						json.put("state", "Y");
					} else {
						json.put("msg", "相片文件保存");
						json.put("state", "N");
					}
				
			} else {
				json.put("msg", "相片文件不存在");
				json.put("state", "N");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			json.put("msg", "相片上传失败");
			json.put("state", "N");
		}
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}
	
	
	/**
	 * 
	 * @Title: djtcutsavePicture @Description:用户摄像头剪切保存图片和上传图片
	 *         424 @param: @return @param: @throws WebException @return:
	 *         String @throws
	 */
	public String userCutSavePicture() throws WebException {
		String formatName = "jpg";
		response.setContentType("text/html;charset=UTF-8");
		String filepath = "";
		message = "error&照片保存出错";
		if ("ok".equals(this.model.getOthers())) {
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\";
				File f_dir = new File(path);
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				f_dir = new File(path + "user-picture\\");
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				byte[] bmpfiledata64 = Base64.base64Decode(this.sjson);
				
				filepath = "user-picture/" + ValidateTime.getDayTimeAll() + ".jpg";
				path = path + filepath;
				File file = new File(path);
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(path);
				// System.out.println("33333="+path);
				fos.write(bmpfiledata64);
				System.out.println("写入成功");
				fos.close();
				boolean writerExists=true;
				if (writerExists) {
				/*	if (this.model.getExam_id() > 0) {
						ExamInfo ei = this.customerInfoService.getExamInfoForId(this.model.getExam_id());
						ei.setPicture_path(filepath);
						this.customerInfoService.SaveExamInfo(ei);
						UserDTO user = (UserDTO) session.get("username");
						SysLog sl = new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid() + "");
						sl.setOper_type("1");// 0 查询，1插入 2修改 3 删除 4导入 9 其他
						sl.setXtgnid("");// 可不填写
						sl.setXtgnid2("424");// 子功能id 必须填写
						sl.setExplain(this.model.getExam_id() + "");// 操作说明
						syslogService.saveSysLog(sl);
					}*/
					this.message = "ok&" + filepath;
				} else {
					this.message = "error&相片上传失败";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				this.message = "error&相片上传失败";
			}
			this.outJsonStrResult(this.message);
			return NONE;
		} else {
			JSONObject json = new JSONObject();
			try {
				String picpath = this.commService.getDatadis("TPLJ").getName();
				String path = picpath + "\\";
				File f_dir = new File(path);
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}

				f_dir = new File(path + "user-picture\\");
				if (!f_dir.exists()) {
					f_dir.mkdirs();
				}
				MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
				UploadedFile[] files = multiWrapper.getFiles("customImageInfoImport");
				if ((files != null) && (files.length > 0)) {
					InputStream sis1 = new FileInputStream(files[0].getAbsolutePath());
					filepath = "user-picture/" + ValidateTime.getDayTimeAll() + ".jpg";
					path = path + filepath;
					File file = new File(path);
					file.createNewFile();
					BufferedImage image = ImageIO.read(sis1);

					int hight = image.getHeight();
					int width = image.getWidth();

					if (hight != 240 && width != 200) {
						json.put("msg", "上传头像图片尺寸应为200x240");
						json.put("state", "N");
					} else {
						boolean writerExists = ImageIO.write(image, formatName, file);
						if (writerExists) {
				/*			if (this.model.getExam_id() > 0) {
								ExamInfo ei = this.customerInfoService.getExamInfoForId(this.model.getExam_id());
								ei.setPicture_path(filepath);
								this.customerInfoService.SaveExamInfo(ei);
							}*/
							json.put("msg", filepath);
							json.put("state", "Y");
						} else {
							json.put("msg", "相片文件保存");
							json.put("state", "N");
						}
					}
				} else {
					json.put("msg", "相片文件不存在");
					json.put("state", "N");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				json.put("msg", "相片上传失败");
				json.put("state", "N");
			}
			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return NONE;
		}
	}
	

	/**
	 * 科室条码打印功能页
	     * @Title: printfunctionPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String printfunctionPage()throws WebException{
		 
		return SUCCESS;
	}
	
	public String getPrintListShow()throws WebException, SQLException{
		PageReturnDTO list = new PageReturnDTO();
		list =  this.userInfoService.getLisPrint(this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}

}