package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.hjw.zyb.DTO.ZybOccuhazardfactorsDTO;
import com.hjw.zyb.domain.ZybOccuhazardfactors;
import com.hjw.zyb.model.ZybOccuhazardfactorsModel;
import com.hjw.zyb.service.ZybOccuhazardfactorsService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 职业危害因素管理
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月1日 上午11:51:39   
     * @version V2.0.0.0
 */
public class ZybOccuhazardfactorsAction extends BaseAction  implements   ModelDriven{
	private ZybOccuhazardfactorsModel model = new ZybOccuhazardfactorsModel();
	private ZybOccuhazardfactorsService  ZybOccuhazardfactorsService;
	private SyslogService syslogService; 
	private  int page=1;
	private  int pageSize=15;
	private String ids="";
	
	
	
	
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public int getPage() {
		return page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	

	public void setZybOccuhazardfactorsService(ZybOccuhazardfactorsService zybOccuhazardfactorsService) {
		ZybOccuhazardfactorsService = zybOccuhazardfactorsService;
	}

	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 职业因素管理页面----zyb315
	     * @Title: getOccuhazardfactorsPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuhazardfactorsPage() throws WebException,SQLException{
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("zyb315");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}
	/**
	 * 职业类别列表----zyb316
	     * @Title: getOccuhazardfactorsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String  getOccuhazardfactorsList()throws WebException,SQLException{
		List<ZybOccuhazardfactorsDTO>  li = this.ZybOccuhazardfactorsService.getOccuhazardfactorsList(model);
		this.outJsonResult( li );
		return NONE;
	}
	/**
	 * 职业危害因素列表----zyb317
	     * @Title: getOccuHazardFactorsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getOccuHazardFactorsList()throws WebException,SQLException{
		this.outJsonResult( this.ZybOccuhazardfactorsService.getOccuHazardFactorsList(model,page,pageSize));
		return NONE;
	}
	/**
	 * 删除职业危害因素----zyb318
	     * @Title: deledtOccuHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deledtOccuHazardFactors()  throws WebException,SQLException{
			this.ZybOccuhazardfactorsService.deledtOccuHazardFactors(ids);
			this.outJsonStrResult( this.message="删除成功" );
			
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb318");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
		return NONE;
	}
	/**
	 * 职业危害因素添加页面----zyb319
	     * @Title: addOccuHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addOccuHazardFactorsPage() throws WebException,SQLException{
		int  si = this.ZybOccuhazardfactorsService.getOrderMax()+1;
		this.model.setOrder2(si+"");
		return SUCCESS;
	}
	/**
	 * 职业因素验证编码唯一----zyb320
	     * @Title: getHazardFactorsCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getHazardFactorsCode()  throws WebException,SQLException{	
		int fla = this.ZybOccuhazardfactorsService.getOccuHazardFactorsCode(model);
		this.outJsonStrResult(fla+"");
		return NONE;
	}
	/**
	 * 职业危害因素保存修改----zyb321
	     * @Title: saveHazardFactors   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public  String saveHazardFactors()  throws WebException,SQLException{	
		if( model.getHazardfactorsID()!=null && !"".equals( model.getHazardfactorsID() )){
			this.ZybOccuhazardfactorsService.updateOccuHazardFactors(model);
			
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb321");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
			this.message="修改成功";
		}else{
			ZybOccuhazardfactors s = new ZybOccuhazardfactors();
			s.setHazardclassID(model.getHazardclassID());
			s.setHazard_code(model.getHazard_code());
			s.setHazard_name(model.getHazard_name());
			s.setHazard_year(model.getHazard_year());
			s.setHazard_desc(model.getHazard_desc());
			s.setOrder(model.getOrder());
			s.setDeffect(model.getDeffect());
			s.setRemark(model.getRemark());
			s.setPycode(model.getPycode());
			this.ZybOccuhazardfactorsService.addOccuHazardFactors(s);
			this.message="添加成功";
			
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("zyb321");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 职业危害因素管理修改页面----zyb322
	     * @Title: addOccuHazardFactorsPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateOccuHazardFactorsPage() throws WebException,SQLException{
		ZybOccuhazardfactors s = this.ZybOccuhazardfactorsService.getOccuHazardFactors( model.getHazardfactorsID() );
		model.setHazardclassID(s.getHazardclassID());
		model.setHazard_code(s.getHazard_code());
		model.setHazard_name(s.getHazard_name());
		model.setHazard_year(s.getHazard_year());
		model.setHazard_desc(s.getHazard_desc());
		model.setOrder(s.getOrder());
		model.setDeffect(s.getDeffect());
		model.setRemark(s.getRemark());
		model.setPycode(s.getPycode());
		return SUCCESS;
	}
}
