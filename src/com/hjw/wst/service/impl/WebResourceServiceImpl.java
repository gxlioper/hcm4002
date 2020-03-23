package com.hjw.wst.service.impl;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.DTO.WebResourceDTO;
import com.hjw.wst.domain.DiseaseLogicExamItem;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.WebResourceModel;
import com.hjw.wst.service.WebResourceService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcPersistenceManager;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

import net.sf.json.JSONArray;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service.impl   
     * @Description:  资源库列表
     * @author: zr     
     * @date:   2016年12月15日 下午4:55:05   
     * @version V2.0.0.0
 */
public class WebResourceServiceImpl implements WebResourceService {

	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;
	private JdbcPersistenceManager jpm;

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public void setJdbcPersistenceManager(JdbcPersistenceManager jpm) {
		this.jpm = jpm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WebResourceDTO> getWebResourceTable(WebResourceModel  model) throws ServiceException {
		String sql="select wa.id,w.name,w.notice,w.data_type,wa.datavalue from WEB_RESRELATIONSHIP"
				+ "   wa,WEB_RESOURCE  w where  wa.res_code=w.code   and    wa.res_type=w.type  and  wa.res_type='"+model.getType()+"' "
				+ "   and   wa.userorroleid='"+model.getIid()+"'";
			List<WebResourceDTO>  c = jqm.getList(sql,WebResourceDTO.class);
		return c;
	}

	@Override
	public void addWebResource(WebResourceModel model,UserDTO  user) throws ServiceException {
		String hql=" FROM  WebResrelAtionship   WHERE   userorroleid='"+model.getIid()+"'   and  "
				+ " res_type='"+model.getRes_type()+"'  and    res_code='"+model.getRes_code()+"'";
		List<WebResrelAtionship>   	webResrelAtionship = qm.find(hql);
		/*if(webResrelAtionship.size()==0){*/
			WebResrelAtionship  we = new WebResrelAtionship();
			we.setUserorroleid(model.getIid());
			we.setRes_code(model.getRes_code());
			we.setDatavalue(model.getDatavalue());
			we.setRes_type(model.getRes_type());
			we.setCreater(user.getUserid());
			we.setCreate_time(DateTimeUtil.parse());
			we.setUpdate_time(DateTimeUtil.parse());
			we.setUpdater(user.getUserid());
			this.pm.save(we);
		/*}*/
	}

	@Override
	public List<WebResourceDTO> updataWebResourceSelect(WebResourceModel model) throws ServiceException {
		String  sql="select code,name,notice,data_type   from "
				+ " WEB_RESOURCE   where  type='"+model.getType()+"' and is_active = 'Y' ";
		List<WebResourceDTO>  li  =	this.jqm.getList(sql,WebResourceDTO.class);
		return li;
	}

	@Override
	public void deleteWebResourceSelect(WebResourceModel model) throws ServiceException {
		WebResrelAtionship we = new WebResrelAtionship();
		we.setId(model.getId());
		this.pm.remove(we);
	}

	@Override
	public WebResrelAtionship getWebResource(WebResourceModel model) throws ServiceException {
		return (WebResrelAtionship) this.qm.get(WebResrelAtionship.class,model.getId());
	}

	@Override
	public void updateWebResource(WebResourceModel model,UserDTO user) throws ServiceException {
		WebResrelAtionship  res = (WebResrelAtionship) this.qm.get(WebResrelAtionship.class,model.getId());
		WebResrelAtionship  res1 = (WebResrelAtionship) this.qm.get(WebResrelAtionship.class,model.getId());
		res.setRes_code(model.getRes_code());
		res.setUserorroleid(model.getIid());
		res.setRes_code(model.getRes_code());
		res.setDatavalue(model.getDatavalue());
		res.setRes_type(model.getRes_type());
		res.setCreater(user.getUserid());
		res.setCreate_time(DateTimeUtil.parse());
		res.setUpdate_time(DateTimeUtil.parse());
		res.setUpdater(user.getUserid());
		this.pm.update(res);
	}

	@Override
	public int getWebResourcePD(WebResourceModel model) throws ServiceException {
		String sql="SELECT web.id  FROM "
				+ "  WEB_RESOURCE  we,WEB_RESRELATIONSHIP   web  where   we.code=web.res_code   "
				+ "  and   userorroleid='"+model.getIid()+"'   and    web.res_type=we.type   and    web.res_type='"+model.getRes_type()+"'"
				+ "  and   web.res_code='"+model.getRes_code()+"'    and   we.data_type='"+model.getData_type()+"'";
			List li = this.jqm.getList(sql,WebResourceDTO.class);
			int a = 0;
			if(li.size()>0){
				a = 1;
			}
		return a;
	}

	@Override
	public WebResourceDTO getWebResourceDTO(WebResourceModel model) throws ServiceException {
		String sql ="SELECT notice,example,examplenote   FROM WEB_RESOURCE  WHERE  code='"+model.getCode()+"'  and  type='"+model.getType()+"'";
		List<WebResourceDTO> li = this.jqm.getList(sql,WebResourceDTO.class);
		return li.size()>0?li.get(0):null;
	}
}
