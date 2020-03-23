package com.hjw.charge.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.charge.DTO.DictCityClinicItemPeisDTO;
import com.hjw.charge.DTO.DictInsuranceClinicItemDTO;
import com.hjw.charge.DTO.DictProvClinicItemPeisDTO;
import com.hjw.charge.DTO.DictProvInsuranceClinicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.charge.model.ItemBindInsuranceModel;
import com.hjw.charge.service.ItemBindInsuranceChargeService;
import com.hjw.wst.service.ChargingItemService;
import com.hjw.wst.service.DepLogicService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

public class ItemBindInsuranceAction  extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 1L;
	
	private ItemBindInsuranceModel model = new ItemBindInsuranceModel();
	private int page=1;
	private int rows=15;
	
	private SyslogService syslogService; 
	private ItemBindInsuranceChargeService itemBindInsuranceService;
	private  DepLogicService  depLogicService;
	private ChargingItemService chargingItemService;
	
	/**
	 * 
	     * @Title: getitemBindInsurancePage   
	     * @Description: 医保项目维护功能页  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getitemBindInsurancePageCharge() throws WebException{
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getChargItemList   
	     * @Description: 获取收费项目列表  （省医保）
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargItemListForProvCharge() throws WebException{
		
		PageReturnDTO dto = this.itemBindInsuranceService.getChargingItemListForProv(model, rows, page);
		this.outJsonResult(dto);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getChargItemListForCity   
	     * @Description: 获取收费项目列表（市医保）   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargItemListForCityCharge() throws WebException{
		
		PageReturnDTO dto = this.itemBindInsuranceService.getChargingItemListForCity(model, rows, page);
		this.outJsonResult(dto);
		return NONE;
	}
	
//	/**
//	 * 
//	     * @Title: getDepartmentList   
//	     * @Description: 获取科室列表 
//	     * @param: @return
//	     * @param: @throws WebException
//	     * @param: @throws SQLException      
//	     * @return: String      
//	     * @throws
//	 */
//	public String getDepartmentListCharge() throws WebException, SQLException{
//		this.outJsonResult(this.depLogicService.getDepartmentList());
//		return NONE;
//	}
	
	
	/**
	 * 
	     * @Title: getItemClinicCityPage   
	     * @Description: TODO(关联市医保项目弹窗)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemClinicCityPageCharge() throws WebException{
		this.model.setItem_code(this.model.getItem_code());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getCityInsuranceItem   
	     * @Description: 获取市医保项目列表 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getCityInsuranceItemCharge() throws WebException{
		DictInsuranceClinicItemDTO  dto = new DictInsuranceClinicItemDTO();
		dto.setItem_code_city(this.model.getItem_code_city());
		dto.setItem_name_city(this.model.getItem_name_city());
		PageReturnDTO cityItem =this.itemBindInsuranceService.getCityInsuranceItemList(dto,page,rows);
		this.outJsonResult(cityItem);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getClinicedItemCity   
	     * @Description: 根据收费项目获取已关联的市医保项目  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getClinicedItemCityCharge() throws WebException{
		List<DictCityClinicItemPeisDTO> dtoList = this.itemBindInsuranceService.getCityItemList(model);
		this.outJsonResult(dtoList);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: saveDictCityClinicItemPeis   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveDictCityClinicItemPeisCharge() throws WebException{
		
		JSONArray cityArray = JSONArray.fromObject(this.model.getDictCityClinicItemPeis());
		List<DictCityClinicItemPeisDTO> listCity = (List<DictCityClinicItemPeisDTO>) JSONArray.toCollection(cityArray,DictCityClinicItemPeisDTO.class);
		String item_code = this.model.getItem_code();
		
		List<DictCityClinicItemPeisDTO> list = new ArrayList<DictCityClinicItemPeisDTO>();
		for(int i=0;i<listCity.size();i++) {
			DictCityClinicItemPeisDTO cityClinic = new DictCityClinicItemPeisDTO();
			cityClinic.setPeis_item_code(item_code);
			cityClinic.setClinic_item_code(listCity.get(i).getClinic_item_code());
			cityClinic.setItem_num(listCity.get(i).getItem_num());
			cityClinic.setItem_price(listCity.get(i).getItem_price());
			list.add(cityClinic);
		}
		list = this.itemBindInsuranceService.saveDictCityClinicItemPeis(item_code, list);
		this.message = "ok-操作成功！";
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("");//子功能id 必须填写
		sl.setExplain("市医保关系维护");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getItemClinicProvPage   
	     * @Description: TODO(关联省医保项目弹窗)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemClinicProvPageCharge() throws WebException{
		this.model.setItem_code(this.model.getItem_code());
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: getProvInsuranceItem   
	     * @Description: 获取省医保项目列表 
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getProvInsuranceItemCharge() throws WebException{
		DictProvInsuranceClinicItemDTO  dto = new DictProvInsuranceClinicItemDTO();
		dto.setItem_code_prov(this.model.getItem_code_prov());
		dto.setItem_name_prov(this.model.getItem_name_prov());
		PageReturnDTO cityItem =this.itemBindInsuranceService.getProvInsuranceItemList(dto,page,rows);
		this.outJsonResult(cityItem);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: getClinicedItemProv   
	     * @Description: 根据收费项目获取已关联的省医保项目  
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getClinicedItemProvCharge() throws WebException{
		String peis_item_code = this.model.getItem_code();
		List<DictProvClinicItemPeisDTO> dtoList = this.itemBindInsuranceService.getProvItemList(peis_item_code);
		this.outJsonResult(dtoList);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: saveDictProvClinicItemPeis   
	     * @Description: 收费项目与省医保对应关系维护
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveDictProvClinicItemPeisCharge() throws WebException{
		
		JSONArray cityArray = JSONArray.fromObject(this.model.getDictProvClinicItemPeis());
		List<DictProvClinicItemPeisDTO> listProv = (List<DictProvClinicItemPeisDTO>) JSONArray.toCollection(cityArray,DictProvClinicItemPeisDTO.class);
		String item_code = this.model.getItem_code();
		
		List<DictProvClinicItemPeisDTO> list = new ArrayList<DictProvClinicItemPeisDTO>();
		for(int i=0;i<listProv.size();i++) {
			DictProvClinicItemPeisDTO provClinic = new DictProvClinicItemPeisDTO();
			provClinic.setPeis_item_code(item_code);
			provClinic.setClinic_item_code(listProv.get(i).getClinic_item_code());
			provClinic.setItem_num(listProv.get(i).getItem_num());
			provClinic.setItem_price(listProv.get(i).getItem_price());
			list.add(provClinic);
		}
		list = this.itemBindInsuranceService.saveDictProvClinicItemPeis(item_code, list);
		this.message = "ok-操作成功！";
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("");//子功能id 必须填写
		sl.setExplain("省医保关系维护");//操作说明
		syslogService.saveSysLog(sl);
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	

	@Override
	public Object getModel() {
		return model;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setModel(ItemBindInsuranceModel model) {
		this.model = model;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public int getRows() {
		return rows;
	}

	public SyslogService getSyslogService() {
		return syslogService;
	}

	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setItemBindInsuranceService(ItemBindInsuranceChargeService itemBindInsuranceService) {
		this.itemBindInsuranceService = itemBindInsuranceService;
	}

	public void setDepLogicService(DepLogicService depLogicService) {
		this.depLogicService = depLogicService;
	}

	public void setChargingItemService(ChargingItemService chargingItemService) {
		this.chargingItemService = chargingItemService;
	}
	
	
}
