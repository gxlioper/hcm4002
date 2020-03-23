package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybOccuhazardrelatorsinfoDTO;
import com.hjw.zyb.domain.ZybOccuhazardrelatorsinfo;
import com.hjw.zyb.model.ZybOccuhazardrelatorsinfoModel;
import com.hjw.zyb.service.ZybOccuhazardrelatorsinfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;


/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.action
 * @Description:
 * @author: ZT
 * @date: 2016年7月16日 下午8:32:41
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ZybOccuhazardrelatorsinfoAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	//private Logincheck logincheck;
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	private String ids;
	private ZybOccuhazardrelatorsinfoService zybOccuhazardrelatorsinfoService;
	private ZybOccuhazardrelatorsinfoModel model = new ZybOccuhazardrelatorsinfoModel();
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

	public ZybOccuhazardrelatorsinfoService getZybOccuhazardrelatorsinfoService() {
		return zybOccuhazardrelatorsinfoService;
	}

	public void setZybOccuhazardrelatorsinfoService(
			ZybOccuhazardrelatorsinfoService zybOccuhazardrelatorsinfoService) {
		this.zybOccuhazardrelatorsinfoService = zybOccuhazardrelatorsinfoService;
	}


	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setModel(ZybOccuhazardrelatorsinfoModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return model;
	}    

	
	/**
	 * 职业危害相关信息管理zyb245
	     * @Title: OccuhazardrelatorsinfoManager   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String OccuhazardrelatorsinfoManager() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("9");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb245");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 添加zyb248
	     * @Title: addOccuhazardrelatorsinfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addOccuhazardrelatorsinfo() throws WebException,SQLException{
		
		return SUCCESS;
	}
	
	/**
	 * 删除zyb254
	     * @Title: deleteOccuhazardrelatorsinfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteOccuhazardrelatorsinfo() throws WebException,SQLException{
		ZybOccuhazardrelatorsinfo r=this.zybOccuhazardrelatorsinfoService.queryById(this.model.getId());
		this.zybOccuhazardrelatorsinfoService.deleteOccuwhxg(r);
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb254");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * zyb249
	     * @Title: updateOccu_hri   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateOccuhazardrelatorsinfo() throws WebException,SQLException{
		//ZybOccuhazardrelatorsinfo r=this.zybOccuhazardrelatorsinfoService.queryById(this.model.getId());
		ZybOccuhazardrelatorsinfoDTO r=this.zybOccuhazardrelatorsinfoService.getByid(this.model.getId());
		this.model.setCheckcontent(r.getCheckcontent());
		this.model.setCheckcriterion(r.getCheckcriterion());
		this.model.setExamperiod(r.getExamperiod());
		this.model.setRemark(r.getRemark());
		this.model.setDiseaseandtaboo(r.getDiseaseandtaboo());
		this.model.setFollowdisease(r.getFollowdisease());
		this.model.setHazard_name(r.getHazard_name());
		this.model.setHazardclass_name(r.getHazardclass_name());
		this.model.setOccuphyexaclass_name(r.getOccuphyexaclass_name());
		
		this.model.setHazardclassID(r.getHazardclassID());//危害因素类别
		this.model.setOccuphyexaclassID(r.getOccuphyexaclassID());//职业体检类别
		this.model.setHazardfactorsID(r.getHazardfactorsID());//危害因素
		return SUCCESS;
	}
	
	
	/**
	 * 查看zyb255
	     * @Title: lookOccuhazardrelatorsinfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  lookOccuhazardrelatorsinfo()throws WebException,SQLException{
		ZybOccuhazardrelatorsinfoDTO r=this.zybOccuhazardrelatorsinfoService.getByid(this.model.getId());
		this.model.setCheckcontent(r.getCheckcontent());
		this.model.setCheckcriterion(r.getCheckcriterion());
		this.model.setExamperiod(r.getExamperiod());
		this.model.setRemark(r.getRemark());
		this.model.setDiseaseandtaboo(r.getDiseaseandtaboo());
		this.model.setFollowdisease(r.getFollowdisease());
		this.model.setHazard_name(r.getHazard_name());
		this.model.setHazardclass_name(r.getHazardclass_name());
		this.model.setOccuphyexaclass_name(r.getOccuphyexaclass_name());
		return SUCCESS;
	}
	
	/**
	 * 职业危害相关信息list zyb246
	     * @Title: getOccuhazardrelatorsinfoList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuhazardrelatorsinfoList() throws WebException,SQLException{
		PageReturnDTO l=this.zybOccuhazardrelatorsinfoService.queryByOccuwhxg(model, this.rows,this.getPage());
		this.outJsonResult(l);
		return NONE;
	}
	
	/**
	 * 职业危害类别tree247
	     * @Title: getOccuhazardclasstree   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuhazardclasstree() throws WebException,SQLException{
		List<ZybOccuhazardrelatorsinfoDTO> l=this.zybOccuhazardrelatorsinfoService.getOccuwhlbtree();
		this.outJsonResult(l);
		return NONE;
	}
	
	
	/**
	 * 职业危害相关信息保存253
	     * @Title: saveOccuhazardrelatorsinfo   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveOccuhazardrelatorsinfo() throws WebException,SQLException{
		if(!"".equals(this.model.getId()) && this.model.getId()!=null){
			ZybOccuhazardrelatorsinfo r=this.zybOccuhazardrelatorsinfoService.queryById(this.model.getId());
			r.setCheckcontent(this.model.getCheckcontent());
			r.setCheckcriterion(this.model.getCheckcriterion());
			r.setDiseaseandtaboo(this.model.getDiseaseandtaboo());
			r.setExamperiod(this.model.getExamperiod());
			r.setFollowdisease(this.model.getFollowdisease());
			r.setRemark(this.model.getRemark());
			//r.setOccuphyexaclassID(this.model.getOccuphyexaclass_name());
			//r.setHazardfactorsID(this.model.getHazard_name());
			r.setOccuphyexaclassID(this.model.getOccuphyexaclassID());
			r.setHazardfactorsID(this.model.getHazardfactorsID());
			r.setHazardclassID(this.model.getHazardclassID());
			this.zybOccuhazardrelatorsinfoService.updateOccuwhxg(r);
			this.message="修改成功!";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb253");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}else{
			ZybOccuhazardrelatorsinfo r=new ZybOccuhazardrelatorsinfo();
			r.setCheckcontent(this.model.getCheckcontent());
			r.setCheckcriterion(this.model.getCheckcriterion());
			r.setDiseaseandtaboo(this.model.getDiseaseandtaboo());
			r.setExamperiod(this.model.getExamperiod());
			r.setFollowdisease(this.model.getFollowdisease());
			r.setRemark(this.model.getRemark());
			r.setOccuphyexaclassID(this.model.getOccuphyexaclassID());
			r.setHazardfactorsID(this.model.getHazardfactorsID());
			r.setHazardclassID(this.model.getHazardclassID());
			this.zybOccuhazardrelatorsinfoService.addOccuwhxg(r);
			this.message="添加成功!";
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb253");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	
	/**
	 * 职业危害类别Listzyb250
	     * @Title: getOccuhazardclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuhazardclass()throws WebException,SQLException{
		List<ZybOccuhazardrelatorsinfoDTO> list=this.zybOccuhazardrelatorsinfoService.getOccuwhlbList();
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 职业危害因素251
	     * @Title: getgetOccuhazardfactor   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getgetOccuhazardfactor()throws WebException,SQLException{
		List<ZybOccuhazardrelatorsinfoDTO> list=this.zybOccuhazardrelatorsinfoService.getOccuwhys();
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 职业体检类别252
	     * @Title: getOccuphyclass   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuphyclass()throws WebException,SQLException{
		List<ZybOccuhazardrelatorsinfoDTO> list=this.zybOccuhazardrelatorsinfoService.getOccuphyclass();
		this.outJsonResult(list);
		return NONE;
	}
	
	
	/**
	 * 批量删除zyb256
	     * @Title: delete_s   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  delete_s()throws WebException,SQLException{
		this.zybOccuhazardrelatorsinfoService.deletes(this.model.getIds());
		this.message="删除成功!";
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb256");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		
		return NONE;
	}
}