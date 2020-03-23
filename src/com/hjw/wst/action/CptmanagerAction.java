package com.hjw.wst.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.dispatcher.multipart.UploadedFile;

import com.hjw.config.Logincheck;
import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ReportMenuDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ReportMenu;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ReportMenuModel;
import com.hjw.wst.service.ReportMenuService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONObject;

@SuppressWarnings("rawtypes")
public class CptmanagerAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private ReportMenuModel model = new ReportMenuModel();
    private ReportMenuService reportMenuService;  
    private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	public void setReportMenuService(ReportMenuService reportMenuService) {
		this.reportMenuService = reportMenuService;
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

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public Object getModel() {
		return model;
	}

	public void setModel(ReportMenuModel model) {
		this.model = model;
	}

	/**
	 * 
	     * @Title: examflowManager   
	     * @Description: 9 报表菜单管理
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String cptmanager() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("9");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: cptmanagerList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String cptmanagerList()throws WebException{
		List<ReportMenuDTO> eulist = this.reportMenuService.getReportMenuForFatherId(this.model.getParent_id());
		this.outJsonResult(eulist);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: reportmanageradd   
	     * @Description: 新增报表 447  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportmanageradd() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("447");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: reportmanageredit   
	     * @Description: 修改报表模板 450  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportmanageredit() throws WebException{
		ReportMenu rm = new ReportMenu();
		rm=this.reportMenuService.loadReportMenu(this.model.getId());
		this.model.setParent_id(rm.getParent_id());
		this.model.setReport_address(rm.getReport_address());
		this.model.setReport_name(rm.getReport_name());
		this.model.setReport_type(rm.getReport_type());
		this.model.setSeq_code(rm.getSeq_code());
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("450");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: reportmanagerdel   
	     * @Description:删除报表模板 451   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportmanagerdel() throws WebException{
		int counts = this.reportMenuService.getReportMenuForFatherIdCount(this.model.getId());
		if(counts>0){
			this.message="error-存在子模板，不能删除";
		}else{
			ReportMenu rm = new ReportMenu();
			rm=this.reportMenuService.loadReportMenu(this.model.getId());
			this.reportMenuService.delReportMenu(rm);
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("451");//子功能id 必须填写
			sl.setExplain("删除模板 "+rm.getId()+" "+rm.getReport_address());//操作说明
			syslogService.saveSysLog(sl);
			this.message="ok-模板删除成功";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}

	/**
	 * 
	     * @Title: reportmanageradddo   
	     * @Description: 执行报表新增 448  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportmanageradddo() throws WebException {
		UserDTO user = (UserDTO) session.get("username");
		if (this.model.getId() <= 0) {
			ReportMenu rm = new ReportMenu();
			rm.setCreater(user.getUserid());
			rm.setCreate_time(DateTimeUtil.parse());
			rm.setUpdater(user.getUserid());
			rm.setUpdate_time(DateTimeUtil.parse());
			rm.setIs_active("Y");
			rm.setParent_id(this.model.getParent_id());
			rm.setReport_address(this.model.getReport_address());
			rm.setReport_name(this.model.getReport_name());
			rm.setReport_type(this.model.getReport_type());
			rm.setSeq_code(this.model.getSeq_code());
			this.reportMenuService.saveReportMenu(rm);
			this.message = "ok-保存成功";
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("448");//子功能id 必须填写
			sl.setExplain("新增报表 "+rm.getReport_address());//操作说明
			syslogService.saveSysLog(sl);
		} else {
			ReportMenu rm = new ReportMenu();
			rm = this.reportMenuService.loadReportMenu(this.model.getId());
			rm.setUpdater(user.getUserid());
			rm.setUpdate_time(DateTimeUtil.parse());
			rm.setIs_active("Y");
			rm.setParent_id(this.model.getParent_id());
			rm.setReport_address(this.model.getReport_address());
			rm.setReport_name(this.model.getReport_name());
			rm.setReport_type(this.model.getReport_type());
			rm.setSeq_code(this.model.getSeq_code());
			this.reportMenuService.updateReportMenu(rm);
			this.message = "ok-修改成功";
			//UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("2");//子功能id 必须填写
			sl.setExplain("修改报表模板 "+rm.getReport_address());//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: reportimportdo   
	     * @Description: 报表模板上传 449   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String reportimportdo() throws WebException {
		String formatName = ".cpt.frm.cpt&view";
		response.setContentType("text/html;charset=UTF-8");
		message = "error-文件保存错误";
		JSONObject json = new JSONObject();
		//FileWriter fw = null;
		InputStream in = null;
		OutputStream out1 = null;
		try {
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) ServletActionContext.getRequest();
			UploadedFile[] files = multiWrapper.getFiles("reportImport");
			String[] fileNames = multiWrapper.getFileNames("reportImport");
			ServletContext s = ServletActionContext.getServletContext();
			String cToPath = s.getRealPath("WEB-INF/reportlets/");
			String filename = "";
			if ((files != null) && (files.length > 0)) {
				in = new FileInputStream(files[0].getAbsolutePath());

				filename = fileNames[0];
				String fileexe = filename.substring(filename.indexOf("."), filename.length()).trim();
				if (formatName.indexOf(fileexe.toLowerCase()) >= 0) {
					cToPath = cToPath + "\\" + filename;
					File filenew = new File(cToPath);
					if (filenew.exists()) {
						filenew.createNewFile();
					}
					out1 = new FileOutputStream(cToPath);
					byte[] buffer = new byte[1024];
					int byteread = 0; // 读取的字节数
					while ((byteread = in.read(buffer)) != -1) {
						out1.write(buffer, 0, byteread);
					}
				}
			}

			if (filename.length() > 0) {

				json.put("msg", filename);
				json.put("state", "Y");
			} else {
				json.put("msg", "文件格式错误");
				json.put("state", "N");
			}

		} catch (Exception ex) {

			ex.printStackTrace();
			json.put("msg", "上传失败");
			json.put("state", "N");
		} finally {
			try {
				if (out1 != null)
					out1.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
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