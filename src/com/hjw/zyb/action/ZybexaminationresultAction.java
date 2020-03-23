package com.hjw.zyb.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.zyb.DTO.ZybdiseaseclassDTO;
import com.hjw.zyb.DTO.ZybexaminationresultDTO;
import com.hjw.zyb.domain.Zybdiseaseclassresult;
import com.hjw.zyb.model.ZybdiseaseclassresultModel;
import com.hjw.zyb.service.ZybdiseaseclassresultService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 疾病分类对应结论
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.zyb.action   
     * @Description:  
     * @author: yangm     
     * @date:   2017年4月13日 下午4:07:28   
     * @version V2.0.0.0
 */
public class ZybexaminationresultAction extends  BaseAction implements  ModelDriven{
	private  ZybdiseaseclassresultService zybdiseaseclassresultService;
	private ZybdiseaseclassresultModel model = new ZybdiseaseclassresultModel();
	private int page=1;
	private int pageSize=15;
	private String ids;
	
	
	
	
	public int getPage() {
		return page;
	}




	public void setPage(int page) {
		this.page = page;
	}




	public int getPageSize() {
		return pageSize;
	}




	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}




	public String getIds() {
		return ids;
	}




	public void setIds(String ids) {
		this.ids = ids;
	}




	public void setZybdiseaseclassresultService(
			ZybdiseaseclassresultService zybdiseaseclassresultService) {
		this.zybdiseaseclassresultService = zybdiseaseclassresultService;
	}




	public void setModel(ZybdiseaseclassresultModel model) {
		this.model = model;
	}




	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 疾病分类对应结论管理页面----zyb343
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybdiseaseclassresultPage() throws  WebException,SQLException{
		return SUCCESS;
	}
	
	/**
	 * 疾病分类对应结论List----zyb344
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getZybdiseaseclassresultList() throws  WebException,SQLException{
		PageReturnDTO  li = this.zybdiseaseclassresultService.queryDiseaseclassresulList(model, page, pageSize);
		this.outJsonResult(li);
		return NONE;
	}
	
	/**
	 * 删除疾病分类对应结论----zyb345
	     * @Title: deleteZybdiseaseclassresult   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String deleteZybdiseaseclassresult() throws  WebException,SQLException{
		this.zybdiseaseclassresultService.deleteDiseaseclassresulList(ids);
		this.outJsonStrResult(this.message="删除成功");
		return NONE;
	}
	
	/**
	 * 疾病分类对应结论新增页面----zyb346
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addZybdiseaseclassresultPage() throws  WebException,SQLException{
		return SUCCESS;
	}
	
	/**
	 * 疾病分类对应结论修改页面----zyb347
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String updateZybdiseaseclassresult() throws  WebException,SQLException{
		Zybdiseaseclassresult z = this.zybdiseaseclassresultService.getDiseaseclassresulList(model);
		model.setDiseaseclassID(z.getDiseaseclassID());
		model.setDiseaseclassresultID(z.getDiseaseclassresultID());
		model.setResultID(z.getResultID());
		return SUCCESS;
	}
	
	/**
	 * 疾病分类对应结论保存----zyb348
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveZybdiseaseclassresult() throws  WebException,SQLException{
		if (model.getDiseaseclassresultID()!=null && !"".equals( model.getDiseaseclassresultID()) ) {
			Zybdiseaseclassresult z = this.zybdiseaseclassresultService.getDiseaseclassresulList(model);
			z.setDiseaseclassID(model.getDiseaseclassID());
			z.setDiseaseclassresultID(model.getDiseaseclassresultID());
			z.setResultID(model.getResultID());
			this.zybdiseaseclassresultService.updateDiseaseclassresulList(z);
			this.outJsonStrResult(this.message="修改成功");
		} else {
			
			Zybdiseaseclassresult z = new  Zybdiseaseclassresult();
			z.setDiseaseclassID(model.getDiseaseclassID());
			z.setDiseaseclassresultID(model.getDiseaseclassresultID());
			z.setResultID(model.getResultID());
			this.zybdiseaseclassresultService.addDiseaseclassresulList(z);
			this.outJsonStrResult(this.message="添加成功");
		}
		return NONE;
	}
	
	/**
	 * 疾病分类List----zyb349
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseclassSelect() throws  WebException,SQLException{
			List<ZybdiseaseclassDTO> li = this.zybdiseaseclassresultService.getZybdiseaseclassSelect();
			this.outJsonResult( li );
		return NONE;
	}
	/**
	 *  获取结论List----zyb350
	     * @Title: getZybdiseaseclassresultPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	
	public String getExaminationresultSelect() throws  WebException,SQLException{
		List<ZybexaminationresultDTO>  li =	this.zybdiseaseclassresultService.getexaminationresultSelect();
		this.outJsonResult( li );
		return NONE;
	}
	
	
}
