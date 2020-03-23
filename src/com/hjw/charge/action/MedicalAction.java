package com.hjw.charge.action;

import java.util.List;

import com.hjw.charge.DTO.DicProvInsuranceClinicItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.charge.domain.MedicalPriceListCharge;
import com.hjw.charge.model.MedicalModel;
import com.hjw.charge.service.MedicalChargeService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 医保
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  
     * @author: zr     
     * @date:   2019年10月30日 上午10:55:25   
     * @version V2.0.0.0
 */
public class MedicalAction extends BaseAction implements ModelDriven{
	private MedicalChargeService medicalService;
	private MedicalModel model = new MedicalModel();
	private int page=1;
	private int rows=15;
	
	
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
	
	
	public MedicalChargeService getMedicalService() {
		return medicalService;
	}
	public void setMedicalService(MedicalChargeService medicalService) {
		this.medicalService = medicalService;
	}
	public void setModel(MedicalModel model) {
		this.model = model;
	}
	@Override
	public Object getModel() {
		return model;
	}
	/**
	 * 价表list
	     * @Title: queryMedicalPriceList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryMedicalPriceListCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO query = this.medicalService.queryMedical(model, user, rows, page);
		this.outJsonResult(query);
		return NONE;
	}
	/**
	 * 项目已选价表列表
	     * @Title: getChargingItemMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getChargingItemMedicalPriceCharge() throws WebException{
		List li = this.medicalService.getChargingItemMedicalPrice(model);
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 保存项目和价表关系
	     * @Title: saveChargingItemMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveChargingItemMedicalPriceCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String res = this.medicalService.saveChargingItemMedicalPrice(model,user);
		this.outJsonStrResult(res);
		return NONE;
	}
	/**
	 * 医保对照关系维护页面
	     * @Title: getmedical   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getmedicalCharge()  throws WebException{
		return  SUCCESS;
	}  
	/**
	 * 价表修改&新增页面
	     * @Title: updatemedicalPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String updatemedicalPageCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		if(model.getId()>0){
			MedicalPriceListCharge me = this.medicalService.getMedicalPriceById(model,user);
			model.setId(me.getId());
			model.setItem_class(me.getItem_class());
			model.setItem_name(me.getItem_name());
			model.setItem_spec(me.getItem_spec());
			model.setUnits(me.getUnits());
			model.setPrice(me.getPrice());
			model.setMemo(me.getMemo());
			model.setInput_code(me.getInput_code());
		}
		return SUCCESS;
	}
	/**
	 * 价表新增修改
	     * @Title: saveMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveMedicalPriceCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.medicalService.saveMedicalPrice(model,user);
		this.outJsonStrResult("保存成功");
		return NONE;
	}
	/**
	 * 删除价表
	     * @Title: deleteMedicalPrice   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String deleteMedicalPriceCharge()  throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		int res = this.medicalService.deleteMedicalPrice(model, user);
		if(res>0){
			this.outJsonStrResult("删除成功！");
		} else {
			this.outJsonStrResult("操作失败！");
		}
		return NONE;
	}
	//===========================价表医保关系=====================
	/**
	 * 价表关联省市医保保存
	     * @Title: saveMedicalPriceMedicalItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveMedicalPriceMedicalItemCharge() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		this.medicalService.saveMedicalPriceMedicalItem(model,user);
		this.outJsonStrResult("保存成功");
		return NONE;
	}
	/**
	 * 价表与省医保维护页面
	     * @Title: getItemClinicCityPageSheng   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getItemClinicCityPageShengCharge()  throws WebException{
		return SUCCESS;
	}
	/**
	 * 省医保分页列表
	     * @Title: getDicProvInsuranceClinicItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryDicProvInsuranceClinicItemCharge()  throws WebException{
		PageReturnDTO dto = this.medicalService.queryDicProvInsuranceClinicItem(model,rows, page);
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 省医保列表
	     * @Title: getDicProvInsuranceClinicItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getDicProvInsuranceClinicItemListCharge()  throws WebException{
		List<DicProvInsuranceClinicItemDTO> li = this.medicalService.getDicProvInsuranceClinicItemList(model);
		this.outJsonResult(li);
		return NONE;
	}
}
