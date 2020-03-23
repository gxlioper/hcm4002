package com.hjw.wst.service;

import java.sql.SQLException;
import java.util.List;

import com.hjw.wst.DTO.ChargingItemDTO;
import com.hjw.wst.DTO.DepartmentDepDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.HisClinicItemPriceListDTO;
import com.hjw.wst.DTO.HisDictDeptDTO;
import com.hjw.wst.DTO.JobDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.SampleReportDemoDto;
import com.hjw.wst.DTO.ThridLisClassDTO;
import com.hjw.wst.DTO.ThridLisItemDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ChargingItem;
import com.hjw.wst.domain.ChargingItemExamItem;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.HisDictDept;
import com.hjw.wst.domain.LimitChargingItem;
import com.hjw.wst.model.ChargingItemModel;
import com.synjones.framework.exception.ServiceException;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  收费项目
     * @author: 张瑞     
     * @date:   2016年9月25日 下午4:10:02   
     * @version V2.0.0.0
 */
public interface ChargingItemService {
	/**
	 * 
	     * @Title: queryChargingItem   
	     * @Description: TODO(获取所有收费项目列表&&检索)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageDTO      
	     * @throws
	 */
	public PageReturnDTO queryChargingItem(ChargingItemDTO cha, int pagesize, int page,String ks,String  exam_item_id, String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getDepartmentDep   
	     * @Description: TODO(收费----获取所有科室)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDep>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getDepartmentDep( String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: getHisDictDept   
	     * @Description: TODO(收费--获取所有his执行科室)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<HisDictDeptDTO>      
	     * @throws
	 */
	public List<HisDictDeptDTO> getHisDictDept() throws ServiceException;
	/**
	 * 
	     * @Title: updateChargingItemDel   
	     * @Description: TODO(删除收费项目)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateChargingItemDel(String id) throws ServiceException;
	/**
	 * 
	     * @Title: getItemId   
	     * @Description: TODO(验证是否已收费)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItemExamItem      
	     * @throws
	 */
	public ChargingItemExamItem getChargingIteId(long id)  throws ServiceException;
	/**
	 * 
	     * @Title: getItemSampleDemo   
	     * @Description: TODO(获取所有检验样本)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SampleDemoDTO>      
	     * @throws
	 */
	public List<SampleDemoDTO> getItemSampleDemo() throws ServiceException;

    /**
     *根据样本id获取样本
     * @param demoType
     * @return
     * @throws ServiceException
     */
    public List<SampleDemoDTO> getItemSampleDemoByDemoType(int demoType) throws ServiceException;
    /**
	 * 
	     * @Title: getItemSampleReportDemo   
	     * @Description: TODO(获取所有报告样本)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<SampleReportDemoDto>      
	     * @throws
	 */
	public List<SampleReportDemoDto> getItemSampleReportDemo() throws  ServiceException;
	/**
	 * 
	     * @Title: addChargingIte   
	     * @Description: TODO(添加收费项目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ChargingItem addChargingIte(ChargingItem item,ChargingItemModel model,UserDTO user) throws ServiceException;
	/**
	 * 
	     * @Title: addChargingItemExamItem   
	     * @Description: TODO(添加收费项目到关系表)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addChargingItemExamItem(ChargingItemExamItem item) throws ServiceException;
	/**
	 * 
	     * @Title: findChargingItemExamItem   
	     * @Description: TODO(根据id获取收费项目)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItemExamItem      
	     * @throws
	 */
	public ChargingItem findChargingItem(long id) throws ServiceException;
	/**
	 * 
	     * @Title: getChargingItemExamItem   
	     * @Description: TODO(获取已选择检查项目清单)   
	     * @param: @param item
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemExamItemDTO>      
	     * @throws
	 */
	public List<ExaminationItemDTO> getChargingItemExamItem(long num) throws ServiceException;
	/**
	 * 
	     * @Title: delChargingItemExamItem   
	     * @Description: TODO(收费项目修改>先把已选择的检查项目删除---然后重新添加)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public void delChargingItemExamItem(long id) throws ServiceException;
	/**
	 * 
	     * @Title: updateChargingItem   
	     * @Description: TODO(修改收费项目)   
	     * @param: @param item
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public ChargingItem updateChargingItem(ChargingItem item,ChargingItemModel model,UserDTO user) throws ServiceException;
	
	public ChargingItem updateSetChargingItem(ChargingItem item) throws ServiceException;
	/**
	 * 
	     * @Title: findClass   
	     * @Description: TODO(查找实体类)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ChargingItem findClass(long id) throws ServiceException;
	/**
	 * 
	     * @Title: getDep_categoryId   
	     * @Description: TODO(科室下拉框和类型联动)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public DepartmentDep getDep_categoryId(long id) throws ServiceException;
	/**
	 * 
	     * @Title: getBMYZ   
	     * @Description: TODO(his系统，检验系统，影像，关联码验证)   
	     * @param: @param bianma
	     * @param: @param zhi
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: long      
	     * @throws
	 */
	public long getBMYZ(String bianma,String zhi) throws ServiceException;
	public long centerBMYZ(String bianma,String zhi,String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: gethis_clinic_item   
	     * @Description: TODO(获取HIS诊疗项目列表)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO getHisClinic(HisClinicItemPriceListDTO dto,int page,int pageSize) throws ServiceException;
	/**
	 * 
	     * @Title: getHisClinic   
	     * @Description: TODO(HIS价格表)   
	     * @param: @param dto
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public List<HisClinicItemPriceListDTO> getHisjg(HisClinicItemPriceListDTO dto) throws ServiceException;
	/**
	 * 
	     * @Title: getPrice   
	     * @Description: TODO(获取HIS诊疗项目总价)   
	     * @param: @param item_code
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: Double      
	     * @throws
	 */
	public double getHisPriceS(String item_code,String item_class_c)throws ServiceException;
	/**
	 * 
	     * @Title: getkbld   
	     * @Description: TODO(收费项目新增---科室和统计科室联动)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: DepartmentDepDTO      
	     * @throws
	 */
	public  List<DepartmentDepDTO> getkbld(long id) throws ServiceException;
	/**
	 * 
	     * @Title: savelimitChargingItem   
	     * @Description: TODO(收费项目添加上限)   
	     * @param: @param id
	     * @param: @param limit_count
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  savelimitChargingItem(long  id,long  limit_count,UserDTO   user,String item_code)   throws ServiceException;
	/**
	 * 
	     * @Title: savelimitChargingItem   
	     * @Description: TODO(收费项目上限修改)   
	     * @param: @param id
	     * @param: @param limit_count
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void  updatelimitChargingItem(long  id,long  limit_count,UserDTO   user)   throws ServiceException;
	/**
	 * 
	     * @Title: getlimitChargingItem   
	     * @Description: TODO(修改页面获取上限)   
	     * @param: @param id
	     * @param: @param limit_count
	     * @param: @param user
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public LimitChargingItem  getlimitChargingItem(long id,ChargingItem item,UserDTO user)   throws ServiceException;
	//----------------------山西人民Lis对照--------------------------------------
	/**
	 * 获取检验项目主表
	     * @Title: getThridLisClassList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ThridLisClassDTO>      
	     * @throws
	 */
	public PageReturnDTO getThridLisClassList(ChargingItemModel model,int page,int pageSize)  throws ServiceException;
	/**
	 * 获取检验项目细项
	     * @Title: getThridLisItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ThridLisClassDTO>      
	     * @throws
	 */
	public List<ThridLisItemDTO> getThridLisItemList(ChargingItemModel model)  throws ServiceException;
	/**
	 * his价表自动同步
	 * 
	 */
	public boolean updateHIsPriceSynchro(ChargingItemModel model)  throws ServiceException;
	/**
	 * 项目排期查询数据
	     * @Title: getItemSchedule   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ChargingItemDTO>      
	     * @throws
	 */
	public PageReturnDTO  getItemSchedule(ChargingItemModel model, String center_num,int page,int pagesize) throws ServiceException;
	/**
	 * 保存排期
	     * @Title: saveItemSchedule   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void saveItemSchedule(String schedule_time, String ids,UserDTO user,long Schedule_number) throws ServiceException;
	/**
	 * 刪除排期
	     * @Title: deleteItemSchedule   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param schedule_time
	     * @param: @param ids
	     * @param: @param user
	     * @param: @param Schedule_number
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void deleteItemSchedule(ChargingItemModel model) throws ServiceException;
	/**
	 * 修改項目排期排期
	     * @Title: updateItemSchedule   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param schedule_time
	     * @param: @param ids
	     * @param: @param user
	     * @param: @param Schedule_number
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateItemSchedule(ChargingItemModel model,UserDTO user) throws ServiceException;
	/**
	 * 可以打印的科室
	     * @Title: getDepartmentDepBarCode   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<DepartmentDepDTO>      
	     * @throws
	 */
	public List<DepartmentDepDTO> getDepartmentDepBarCode( String center_num) throws ServiceException;
	/**
	 * 
	     * @Title: updateChargingItemStopOrStart   
	     * @Description: TODO(启用停用该收费项目)   
	     * @param: @param ci
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public ChargingItem updateChargingItemStopOrStart(ChargingItem ci) throws ServiceException;
	
	public String getItemClassName(String item_class, String string)throws ServiceException;
	
	public List<ChargingItemDTO> getChargingItemLb(long id) throws ServiceException;
	
	/**
	 * 
	     * @Title: updateHisCharginigItem   
	     * @Description: 更新his关联码   
	     * @param: @param dto
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String updateHisCharginigItem(HisClinicItemPriceListDTO dto,long userid) throws ServiceException;
	
	/**
	 * 
	     * @Title: getHisPriceList   
	     * @Description: 获取价表列表  
	     * @param: @param dto
	     * @param: @param page
	     * @param: @param pageSize
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<HisClinicItemPriceListDTO>      
	     * @throws
	 */
	public PageReturnDTO getHisPriceList(HisClinicItemPriceListDTO dto, int page, int pageSize) throws ServiceException;
	
	/*查询科室下的所有收费项目
	 * getChargingItemListByDepId
	     * <p>Title: getModel</p>   
	     * <p>Description: </p>   
	     * @return   
	     * @see com.opensymphony.xwork2.ModelDriven#getModel()
	 */
	public List<ChargingItemDTO>  getChargingItemListByDepId(long dep_id,String com_id)throws ServiceException;
	
	public ChargingItem getItemNameCheck(ChargingItemModel model)throws ServiceException;
	/**
	 * 选择收费项目
	     * @Title: getSeleteItem   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ChargingItem      
	     * @throws
	 */
	public String saveSeleteItem(ChargingItemModel model,UserDTO user) throws ServiceException; 
	/**
	 * 多体检中心-收费项目分页列表
	     * @Title: queryChargingItemCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param cha
	     * @param: @param pagesize
	     * @param: @param page
	     * @param: @param ks
	     * @param: @param exam_item_id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: PageReturnDTO      
	     * @throws
	 */
	public PageReturnDTO queryChargingItemCenter(ChargingItemDTO cha, int pagesize, int page,String ks,String  exam_item_id,UserDTO user) throws ServiceException;
	/**
	 * 多体检中心-获取收费项目信息
	     * @Title: getchargingItemVsCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user      
	     * @return: void      
	     * @throws
	 */
	public ChargingItemDTO getchargingItemVsCenter(ChargingItemModel model,UserDTO user)  throws ServiceException;
	/**
	 * @throws SQLException 
	 * 多体检中心-修改本体检中心项目
	     * @Title: updateChargingItemCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean updateChargingItemCenter(ChargingItemModel model,UserDTO user) throws ServiceException;
	/**
	 * 多体检中心-删除本体检中心项目
	     * @Title: deleteChargingItemCenter   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: boolean      
	     * @throws
	 */
	public boolean deleteChargingItemCenter(ChargingItemModel model,UserDTO user) throws ServiceException;
}
