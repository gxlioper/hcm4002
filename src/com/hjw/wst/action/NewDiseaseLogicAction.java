package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.DiseaseLogicDTO;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.DepartmentDep;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.WebResrelAtionship;
import com.hjw.wst.model.DiseaseLogicModel;
import com.hjw.wst.service.DepartmentService;
import com.hjw.wst.service.ExaminationItemService;
import com.hjw.wst.service.NewDiseaseLogicService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description:  新阳性/疾病逻辑管理getDiseaseLogicSingleEditPage
     * @author: dq
     * @date:   2019年2月20日 上午10:45:46   
     * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class NewDiseaseLogicAction extends BaseAction implements ModelDriven{
	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = 1L;
	private DiseaseLogicModel model = new DiseaseLogicModel();
	private NewDiseaseLogicService newDiseaseLogicService;
	private ExaminationItemService examinationItem;
	private DepartmentService departmentService;
	
	private int page=1;
	private int rows=15;

    public ExaminationItemService getExaminationItemService() {
        return examinationItem;
    }

    public void setExaminationItemService(ExaminationItemService examinationItem) {
        this.examinationItem = examinationItem;
    }

    public NewDiseaseLogicService getNewDiseaseLogicService() {
		return newDiseaseLogicService;
	}
	public void setNewDiseaseLogicService(NewDiseaseLogicService newDiseaseLogicService) {
		this.newDiseaseLogicService = newDiseaseLogicService;
	}
	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
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
	@Override
	public Object getModel() {
		return model;
	}
	public void setModel(DiseaseLogicModel model) {
		this.model = model;
	}
	
	/**
	 * 1729 单项阳性逻辑维护页面
	     * @Title: getDiseaseLogicSinglePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSinglePage() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<WebResrelAtionship> ls = user.getWebResource();
		if( ls!=null){
			for (int i = 0; i < ls.size(); i++) {
				if(ls.get(i).getRes_code().equals("RS008")){
					this.model.setWeb_Resource(ls.get(i).getDatavalue());
					break;
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 1730 单项阳性逻辑列表
	     * @Title: getDiseaseLogicSingleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSingleList() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO list = this.newDiseaseLogicService.getDiseaseLogicSingleList(this.model.getLogic_name(),this.model.getE_item_name(), Long.valueOf(this.model.getDep_id()),model.getLogic_class(), model.getIsActive(), page, rows,user.getCenter_num(),this.model.getItem_num());
		this.outJsonResult(list);
		return NONE;
	}

	/**
	 * 1731 编辑单项阳性逻辑页面
	     * @Title: getDiseaseLogicSingleEditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSingleEditPage() throws WebException, SQLException{
		DepartmentDep dep = this.departmentService.loadDepartmentDep(Long.valueOf(model.getDep_id()));
		if(dep.getDep_category().equals("21")){
			return "success_c";
		}
		return SUCCESS;
	}
	
	/**
	 * 1732 保存单项阳性逻辑信息
	     * @Title: saveDiseaseLogicSingle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String saveDiseaseLogicSingle() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.newDiseaseLogicService.saveDiseaseLogicSingle(model, user);
		this.outJsonStrResult("ok-保存单项阳性逻辑成功!");
		return NONE;
	}
	
	/**
	 * 1733 根据ID查询单项阳性逻辑信息
	     * @Title: getDiseaseLogicSingleById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSingleById() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		DiseaseLogicDTO diseaseLogicDTO = this.newDiseaseLogicService.getDiseaseLogicSingleById(this.model.getIds(),model.getLogic_class(),user.getCenter_num());
		this.outJsonResult(diseaseLogicDTO);
		return NONE;
	}
	
	/**
	 * 1734 单项阳性逻辑启用或停用
	     * @Title: diseaseLogicSingleStartOrEnd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseLogicSingleStartOrEnd() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.newDiseaseLogicService.diseaseLogicSingleStartOrEnd(model.getIds(), model.getIsActive(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1735  单项阳性逻辑批量删除
	     * @Title: delDiseaseLogicSingle   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delDiseaseLogicSingle() throws WebException, SQLException{
		this.message = this.newDiseaseLogicService.delDiseaseLogicSingle(model.getIds());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1736 获取复合逻辑维护页面
	     * @Title: getDiseaseLogicCompositePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicCompositePage() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<WebResrelAtionship> ls = user.getWebResource();
		if( ls!=null){
			for (int i = 0; i < ls.size(); i++) {
				if(ls.get(i).getRes_code().equals("RS008")){
					this.model.setWeb_Resource(ls.get(i).getDatavalue());
					break;
				}
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 1737 获取复合逻辑信息列表
	     * @Title: getDiseaseLogicCompositeList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicCompositeList() throws WebException, SQLException{
		PageReturnDTO list = this.newDiseaseLogicService.getDiseaseLogicCompositeList(this.model.getDisease_name(),Long.valueOf(this.model.getDep_id()), model.getIsActive(), page, rows);
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 1738 获取复合逻辑编辑页面
	     * @Title: getDiseaseLogicCompositeEditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicCompositeEditPage() throws WebException, SQLException{
		return SUCCESS;
	}
	
	/**
	 * 1739 保存复合疾病逻辑信息
	     * @Title: saveDiseaseLogicComposite   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveDiseaseLogicComposite() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.newDiseaseLogicService.saveDiseaseLogicComposite(model, user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1740 查询单个复合疾病逻辑信息
	     * @Title: getDiseaseLogicCompositeById   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicCompositeById() throws WebException, SQLException{
		DiseaseLogicDTO diseaseLogicDTO = this.newDiseaseLogicService.getDiseaseLogicCompositeById(this.model.getIds());
		this.outJsonResult(diseaseLogicDTO);
		return NONE;
	}
	
	/**
	 * 1741 复合疾病逻辑启用和停用
	     * @Title: diseaseLogicCompositeStartOrEnd   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String diseaseLogicCompositeStartOrEnd() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		this.message = this.newDiseaseLogicService.diseaseLogicCompositeStartOrEnd(model.getIds(), model.getIsActive(), user);
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1742 批量删除复合疾病逻辑信息
	     * @Title: delDiseaseLogicComposite   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delDiseaseLogicComposite() throws WebException, SQLException{
		this.message = this.newDiseaseLogicService.delDiseaseLogicComposite(model.getIds());
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1743 单项阳性科室结论逻辑维护页面
	     * @Title: getDiseaseLogicSinglePage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSingleDepPage() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<WebResrelAtionship> ls = user.getWebResource();
		if( ls!=null){
			for (int i = 0; i < ls.size(); i++) {
				if(ls.get(i).getRes_code().equals("RS008")){
					this.model.setWeb_Resource(ls.get(i).getDatavalue());
					break;
				}
			}
		}
		return SUCCESS;
	}

	/**
	 * 1744  编辑单项阳性科室结论逻辑页面
	     * @Title: getDiseaseLogicSingleEditPage   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSingleDepEditPage() throws WebException, SQLException{
		return SUCCESS;
	}
	
	/**
	 * 1745获取科室类型为普通科室或影像科室的科室列表
	     * @Title: getDiseaseLogicSingleDepList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getDiseaseLogicSingleDepList() throws WebException, SQLException{
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminationItemDTO> list = this.newDiseaseLogicService.getDiseaseLogicSingleDepList(model.getE_item_name(), model.getDep_id(),user.getCenter_num());
		this.outJsonResult(list);
		return NONE;
	}

    /**
     * 2586 检查项目管理-单项阳性逻辑维护
     * 单项阳性逻辑管理
     * @return
     * @throws WebException
     * @throws SQLException
     */
	public String itemDiseaseLogicSinglePage() throws WebException, SQLException{
        UserDTO user = (UserDTO) session.get("username");
        List<WebResrelAtionship> ls = user.getWebResource();
        if( ls!=null){
            for (int i = 0; i < ls.size(); i++) {
                if(ls.get(i).getRes_code().equals("RS008")){
                    this.model.setWeb_Resource(ls.get(i).getDatavalue());
                    break;
                }
            }
        }
        return SUCCESS;
	}
    public String itemDiseaseLogicSingleEditPage() throws WebException, SQLException{
        UserDTO user = (UserDTO) session.get("username");
        ExaminationItem e = this.examinationItem.getExaminationItemByItem_num(model.getItem_num());
        model.setItem_num(e.getItem_num());
        model.setItem_name(e.getItem_name());
        return SUCCESS;
    }

}
