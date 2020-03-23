package com.hjw.crm.action;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hjw.crm.DTO.CrmSalesCostDTO;
import com.hjw.crm.domain.CrmSalesCost;
import com.hjw.crm.model.CrmSalesCostModel;
import com.hjw.crm.service.CrmSalesCostService;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class CrmSalesCostAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 1L;
	private CrmSalesCostService crmSalesCostService;
	private int page=1;
	private int rows=15;
    private SyslogService syslogService;
	private CrmSalesCostModel model=new CrmSalesCostModel();

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public CrmSalesCostService getCrmSalesCostService() {
		return crmSalesCostService;
	}

	public void setCrmSalesCostService(CrmSalesCostService crmSalesCostService) {
		this.crmSalesCostService = crmSalesCostService;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setModel(CrmSalesCostModel model) {
		this.model = model;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	/**
	 * 
	     * @Title: crm125获取销售管理列表   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSalesCostList() throws  WebException,SQLException{
		PageReturnDTO  dto =  this.crmSalesCostService.getCrmSalesCostList(model, page, rows);
		this.outJsonResult( dto );
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm125");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: crm122获取销售管理表页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCrmSalesCostListPage() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("crm122");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;	
	}
	//删除
	/**
	 * 
	     * @Title: crm126删除销售管理   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteCrmSalesCost() throws  WebException,SQLException{
			String yi=this.crmSalesCostService.deleteCrmSalesCost(model.getIds());
			if(yi=="1"){
				this.outJsonStrResult(this.message="删除成功");	
			}else{
				this.outJsonStrResult(this.message="删除失败");
			}
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm126");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
	
	//更新页面
	/**
	 * 
	     * @Title: crm123获取销售管理更新页面   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException
	     * @param: @throws UnsupportedEncodingException      
	     * @return: String      
	     * @throws
	 */
		public String getUpdateCrmSalesCostPage() throws  WebException,SQLException, UnsupportedEncodingException{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			CrmSalesCost crm = this.crmSalesCostService.getCrmSalesCost(model.getCode());
			model.setId(model.getCode());
			String c= new String(model.getCosts().getBytes("ISO8859-1"),"UTF-8");
			model.setSales_name(model.getBatch_names());
			model.setCost_amount(crm.getCost_amount());
			model.setCost_date(df.format(crm.getCost_date()));
			model.setCost_type(crm.getCost_type());
			model.setCost_typename(c);
			model.setPayment_type(crm.getPayment_type());
			model.setPayment_type_name(model.getBaoxiao());
			model.setRemark(crm.getRemark());
			model.setSales_id(crm.getSales_id());
			model.setBatch_num(crm.getBatch_num());
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm123");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
		}
		
		//新增页面
		/**
		 * 
		     * @Title: crm124获取销售管理新增页面   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException      
		     * @return: String      
		     * @throws
		 */
		public String addJieCrmSalesCostPage()throws  WebException{
			UserDTO user = (UserDTO) session.get("username");
			model.setSales_id(user.getUserid());
			model.setSales_name(user.getUsername());
			model.setCost_type(model.getCost_types());
			Date date=new Date();
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.setCost_date(df.format(date));
			if(model.getCost_types().equals("1")){
				model.setCost_typename("借款类型");
			}else if(model.getCost_types().equals("2")){
				model.setCost_typename("还款类型");
			}
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm124");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return SUCCESS;
		}
		

		/**
		 * 
		     * @Title: crm127更新保存销售管理   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException
		     * @param: @throws ParseException      
		     * @return: String      
		     * @throws
		 */
		public String saveCrmSalesCost() throws  WebException,SQLException, ParseException{
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			UserDTO user = (UserDTO) session.get("username");
					if ( model.getId()!=null && !"".equals(model.getId())){
						CrmSalesCost z = this.crmSalesCostService.getCrmSalesCost(model.getId());
						z.setCost_amount(model.getCost_amount());
						z.setCost_date(df.parse(model.getCost_date()));
						z.setCost_type(model.getCost_type());
						z.setPayment_type(model.getPayment_type());
						z.setRemark(model.getRemark());
						z.setSales_id(model.getSales_id());
						z.setBatch_num(model.getBatch_num());
						this.crmSalesCostService.updateCrmSalesCost(z);
						this.message = "修改成功";
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("crm127");//子功能id 必须填写
						sl.setExplain("");//操作说明
						syslogService.saveSysLog(sl);
					} else {
						CrmSalesCost c = new CrmSalesCost();
						c.setCost_amount(model.getCost_amount());
						c.setCost_date(df.parse(model.getCost_date()));
						c.setCost_type(model.getCost_type());
						c.setPayment_type(model.getPayment_type());
						c.setRemark(model.getRemark());
						c.setSales_id(model.getSales_id());
						c.setBatch_num(model.getBatch_num());
						this.crmSalesCostService.addCrmSalesCost(c);
						this.message = "添加成功";
						SysLog sl =  new SysLog();
						sl.setCenter_num(user.getCenter_num());
						sl.setUserid(user.getUserid()+"");
						sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
						sl.setXtgnid("");//可不填写
						sl.setXtgnid2("crm127");//子功能id 必须填写
						sl.setExplain("");//操作说明
						syslogService.saveSysLog(sl);
					}
			
			
			this.outJsonStrResult( this.message );
			return NONE;
		}
		/**
		 * 
		     * @Title: crm128获取体检任务   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String getCrmSalesCostTr()throws  WebException,SQLException{
			UserDTO user = (UserDTO) session.get("username");
			List list=this.crmSalesCostService.getCrmSalesCostTr(user.getCenter_num());
			this.outJsonResult( list );
//			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm128");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}
		/**
		 * 
		     * @Title: crm129获取数据字典的报销费   
		     * @Description: TODO(这里用一句话描述这个方法的作用)   
		     * @param: @return
		     * @param: @throws WebException
		     * @param: @throws SQLException      
		     * @return: String      
		     * @throws
		 */
		public String getCrmSalesCostBX()throws  WebException,SQLException{
			List list=this.crmSalesCostService.getCrmSalesCostBX();
			this.outJsonResult( list );
			UserDTO user = (UserDTO) session.get("username");
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("crm129");//子功能id 必须填写
			sl.setExplain("");//操作说明
			syslogService.saveSysLog(sl);
			return NONE;
		}

}
