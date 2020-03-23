package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybSourcecareerclassDTO;
import com.hjw.zyb.domain.ZybSourcecareerclass;
import com.hjw.zyb.model.ZybSourcecareerclassModel;
import com.hjw.zyb.service.ZybSourcecareerclassService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: zt     
     * @date:   2017年4月18日 上午10:38:48   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybSourcecareerclassAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;
	private ZybSourcecareerclassService zybSourcecareerclassService;
	private ZybSourcecareerclassModel model = new ZybSourcecareerclassModel();
	private SyslogService syslogService;
	

	public ZybSourcecareerclassService getZybSourcecareerclassService() {
		return zybSourcecareerclassService;
	}

	public void setZybSourcecareerclassService(
			ZybSourcecareerclassService zybSourcecareerclassService) {
		this.zybSourcecareerclassService = zybSourcecareerclassService;
	}

	public ZybSourcecareerclassModel getModel() {
		return model;
	}

	public void setModel(ZybSourcecareerclassModel model) {
		this.model = model;
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

	
	/**
	 * 照射源分类 管理zyb257
	     * @Title: sourceclassManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String sourceclassManager()throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb257");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	
	/**
	 * 照射源分类添加
	     * @Title: addSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addSourcecareerclass() throws WebException,SQLException{
		
		return SUCCESS;
	}
	
	
	/**
	 *照射源分类删除
	 *zyb261
	     * @Title: deleteSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteSourcecareerclass() throws WebException,SQLException{
		ZybSourcecareerclass c=this.zybSourcecareerclassService.queryByid(this.model.getSc_classid());
		this.zybSourcecareerclassService.deleteSourcecareerclass(c);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb261");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 照射源分类更新
	 * zyb260
	     * @Title: updateSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateSourcecareerclass() throws WebException,SQLException{
		ZybSourcecareerclassDTO c=this.zybSourcecareerclassService.queryByID(this.model.getSc_classid());
		this.model.setSourceid(c.getSourceid());
		this.model.setSc_classcode(c.getSc_classcode());
		this.model.setSc_classname(c.getSc_classname());
		this.model.setSource_name(c.getSource_name());
		return SUCCESS;
	}
	
	
	/**
	 * 照射源分类Listzyb258
	     * @Title: getSourcecareerclassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSourcecareerclassList() throws WebException,SQLException{
		String sc_classcode=this.model.getSc_classcode();
		String sc_classname=this.model.getSc_classname();
		PageReturnDTO l=this.zybSourcecareerclassService.queryBySourcecareerclasspage(sc_classcode, sc_classname, this.rows, this.getPage());
		this.outJsonResult(l);
		return NONE;
	}
	
	
	/**
	 * 照射源分类  编码验证
	 * zyb264
	     * @Title: volidate   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String volidate() throws WebException, SQLException{
		ZybSourcecareerclass l=this.zybSourcecareerclassService.queryBycode(this.model.getSc_classcode(), this.model.getSc_classid());
		if(l==null){
			this.message="ok";
		}else{
			this.message="no";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 照射源分类保存
	 * zyb263
	     * @Title: save_sc   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveSourcecareerclass() throws WebException,SQLException{
		if(!"".equals(this.model.getSc_classid())&& this.model.getSc_classid()!=null){
			ZybSourcecareerclass c=this.zybSourcecareerclassService.queryByid(this.model.getSc_classid());
				c.setSc_classcode(this.model.getSc_classcode());
				c.setSc_classname(this.model.getSc_classname());
				c.setSourceid(this.model.getSourceid());
				this.zybSourcecareerclassService.updateSourcecareerclass(c);
				this.message="修改成功!";
				UserDTO user = (UserDTO) session.get("username");
				SysLog sl =  new SysLog();
				sl.setCenter_num(user.getCenter_num());
				sl.setUserid(user.getUserid()+"");
				sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
				sl.setXtgnid("");//可不填写
				sl.setXtgnid2("zyb263");//子功能id 必须填写
				sl.setExplain("");//操作说明
				syslogService.saveSysLog(sl);
		}else{
			ZybSourcecareerclass c=new ZybSourcecareerclass();
			c.setSc_classcode(this.model.getSc_classcode());
			c.setSc_classname(this.model.getSc_classname());
			c.setSourceid(this.model.getSourceid());
			this.zybSourcecareerclassService.addSourcecareerclass(c);
			this.message="添加成功!";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb263");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 照射源获取
	 * zyb262
	     * @Title: getSourcecareerclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getSourcecareerclass() throws WebException,SQLException{
		List<ZybSourcecareerclassDTO> s=this.zybSourcecareerclassService.getS_name();
		this.outJsonResult(s);
		return NONE;
	}
}