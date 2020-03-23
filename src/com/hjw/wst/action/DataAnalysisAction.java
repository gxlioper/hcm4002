package com.hjw.wst.action;

import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DataAnalysisConditionDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.DataAnalysisModel;
import com.hjw.wst.service.DataAnalysisService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

public class DataAnalysisAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 8217814910220881058L;
	private DataAnalysisModel model = new DataAnalysisModel();
	private int rows = 15; // easyui每页显示条数
	private String sort;
	private String order;
	private DataAnalysisService dataAnalysisService;
	
	public void setDataAnalysisService(DataAnalysisService dataAnalysisService) {
		this.dataAnalysisService = dataAnalysisService;
	}
    
	/**
	 * 1754获取科研数据页面
	     * @Title: getResearchDataPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getResearchDataPage() throws WebException{
		this.model.setTime1(DateTimeUtil.getPastDate(365));
		this.model.setTime2(DateTimeUtil.getDate2());
		return SUCCESS;
	}
	
	/**
	 *1755 根据查询条件查询科研数据
	     * @Title: getResearchDataList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getResearchDataList() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		JSONArray conditions = JSONArray.fromObject(this.model.getConditions());
		List<DataAnalysisConditionDTO> condition = (List<DataAnalysisConditionDTO>) JSONArray.toCollection(conditions,DataAnalysisConditionDTO.class);
		this.model.setCondition(condition);
		PageReturnDTO page = this.dataAnalysisService.getResearchDataList(model, user, this.rows, this.getPage(),this.sort,this.order);
		this.outJsonResult(page);
		return NONE;
	}
	
	/**
	 * 1756 查询收费项目列表
	     * @Title: getChargingItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingItemListByq() throws WebException{
		List<ChargingItemDTO> list = this.dataAnalysisService.getChargingItemListByq(this.model.getQ(),this.model.getAdd_i());
		this.outJsonResult(list);
		return NONE;
	}
	/**
	 * 1757 查询检查项目列表
	     * @Title: getExaminationItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminationItemListByq() throws WebException{
		List<ExaminationItemDTO> list = this.dataAnalysisService.getExaminationItemListByq(this.model.getCharging_id(), this.model.getQ(),this.model.getAdd_i());
		this.outJsonResult(list);
		return NONE;
	}
	
	public DataAnalysisModel getModel() {
		return model;
	}
	public void setModel(DataAnalysisModel model) {
		this.model = model;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
