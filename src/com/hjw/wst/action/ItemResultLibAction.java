package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.ExaminationItemDTO;
import com.hjw.wst.DTO.ItemResultLibDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.ExaminationItem;
import com.hjw.wst.domain.ItemResultLib;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ItemResultLibModel;
import com.hjw.wst.service.ExaminationItemService;
import com.hjw.wst.service.ItemResultLibService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.action   
     * @Description: 项目结果知识库
     * @author: zr     
     * @date:   2016年10月26日 下午12:07:46   
     * @version V2.0.0.0
 */
@SuppressWarnings("serial")
public class ItemResultLibAction   extends BaseAction implements ModelDriven{
	private ItemResultLibModel model = new ItemResultLibModel();
	private ExaminationItemService  examinationItem;	
	private int page=1;
	private int rows=15;
	private  String ids="";
	private  ItemResultLibService itemResultLibService;
	private SyslogService syslogService;  
	
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}

	public void setExaminationItemService(ExaminationItemService examinationItem) {
		this.examinationItem = examinationItem;
	}

	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public ItemResultLibService getItemResultLibService() {
		return itemResultLibService;
	}
	public Object getModel() {
		return model;
	}
	public void setItemResultLibService(ItemResultLibService itemResultLibService) {
		this.itemResultLibService = itemResultLibService;
	}
	public int getPage() {
		return page;
	}
	
	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getRows() {
		return rows;
	}
	public void setModel(ItemResultLibModel model) {
		this.model = model;
	}
	/**
	 * 
	     * @Title: ItemResultLib   
	     * @Description: TODO(结果知识库页面506)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String itemResultLib() throws  WebException, SQLException{
		

		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("506");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: queryItemResultLib   
	     * @Description: TODO(项目结果知识库列表507)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  queryItemResultLib() throws  WebException, SQLException{
		ItemResultLibDTO item = new ItemResultLibDTO();
		item.setDep_id(model.getDep_id());
		item.setExam_item_id(model.getExam_item_id());
		item.setExam_result(model.getExam_result());
		UserDTO user = (UserDTO) session.get("username");
		PageReturnDTO dto = this.itemResultLibService.queryItemResultLib(item, page,rows,user.getCenter_num());
		this.outJsonResult(dto);
		return NONE;
	}
	/**
	 * 
	     * @Title: getExaminationItem   
	     * @Description: TODO(检查项目下拉框508)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getExaminationItem() throws WebException,SQLException{
		List<ExaminationItemDTO> li = this.itemResultLibService.getExaminationItem(model.getDep_id());
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: addItemResultLib   
	     * @Description: TODO(项目结果知识添加页面509)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addItemResultLib() throws WebException,SQLException{
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: addItemResultLibMethod   
	     * @Description: TODO(添加项目结果知识/修改510)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String addItemResultLibMethod() throws WebException,SQLException{
		if(model.getId()>0){//修改方法
			ItemResultLib  items = this.itemResultLibService.findItemResultLib(model.getId());
			items.setId(model.getId());
			items.setDep_id(model.getDep_id());
			items.setExam_item_id(model.getExam_item_id());
			items.setExam_result(model.getExam_result());
			items.setResult_num(model.getResult_num());
			items.setSeq_code(model.getSeq_code());
			items.setCommon_words(model.getCommon_words());
			items.setExam_conclusion(model.getExam_conclusion());
			UserDTO user=(UserDTO) session.get("username");
			items.setUpdater(user.getUserid());
			items.setUpdate_time(DateTimeUtil.parse());
			items.setItem_code(this.examinationItem.getExamCodeById(model.getExam_item_id()));//检查项目编码
			this.itemResultLibService.updateItemResultLib(items);
			if(model.getDefault_value().equals("Y")){//是
				ExaminationItem examinationI= this.itemResultLibService.getExaminationItemResult(model.getId()+"");
				if(examinationI!=null){
						if(!"数字型".equals(examinationI.getItem_category())){
							//examinationI.setDefault_value(so.getId());
							examinationI.setDefault_value(model.getId());
							itemResultLibService.updateExaminationItems_up(examinationI);
						}
				}
			}else{//否
				int fal = this.itemResultLibService.getExaminationItem_default(model.getId());
				if( fal > 0){
					ExaminationItem examinationI= this.itemResultLibService.getExaminationItemResult(model.getId()+"");
					if(examinationI!=null){
						examinationI.setDefault_value(null);
						itemResultLibService.updateExaminationItems_up(examinationI);
					}
				}
			}
			this.message="修改成功";
			

		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("510");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		}else{//添加方法
			ItemResultLib item = new ItemResultLib();
			item.setDep_id(model.getDep_id());//科室
			item.setExam_item_id(model.getExam_item_id());//检查项目
			item.setExam_result(model.getExam_result());//结果内容
			item.setResult_num(model.getResult_num());//拼音
			item.setSeq_code(model.getSeq_code());//顺序码
			item.setCommon_words(model.getCommon_words());//常用词  是/否
			item.setIsActive("Y");//添加状态
			item.setExam_conclusion(model.getExam_conclusion());
			UserDTO user=(UserDTO) session.get("username");
			item.setCreater(user.getUserid());
			item.setCreate_time(DateTimeUtil.parse());
			item.setItem_code(this.examinationItem.getExamCodeById(model.getExam_item_id()));//检查项目编码
			item= this.itemResultLibService.addItemResultLib(item);//添加结果知识返回id
			if(model.getDefault_value().equals("Y")){
				ExaminationItem examinationI= this.examinationItem.findClass(model.getExam_item_id(), user.getCenter_num());
				if(!"数字型".equals(examinationI.getItem_category())){
					examinationI.setDefault_value(item.getId());
					examinationItem.updateExaminationItem(examinationI, user.getCenter_num());
					
				}
			}
			this.message="添加成功";
			
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("510");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	/**
	 * 
	     * @Title: updateItemResultLibPage   
	     * @Description: TODO(项目知识库修改页面511)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String  updateItemResultLibPage() throws WebException,SQLException{
		ItemResultLib  item = this.itemResultLibService.findItemResultLib(model.getId());
		model.setId(item.getId());
		model.setDep_id(item.getDep_id());
		model.setExam_item_id(item.getExam_item_id());
		model.setExam_result(item.getExam_result());
		model.setResult_num(item.getResult_num());
		//是否默认值
		int  eiitem = this.examinationItem.queryDefaultid(model.getId());
		if(eiitem>0){
			model.setDefault_value("Y");
		}else{
			model.setDefault_value("N");
		}
		model.setSeq_code(item.getSeq_code());
		model.setCommon_words(item.getCommon_words());
		model.setExam_conclusion(item.getExam_conclusion());
		return SUCCESS;
	}
	/**
	 * 
	     * @Title: delItemResultLib   
	     * @Description: TODO(删除项目结果512)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delItemResultLib() throws WebException,SQLException{
		this.itemResultLibService.delItemResultLib(ids);
		this.message="删除成功";
		this.outJsonStrResult(this.message);

		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("512");//子功能id 必须填写
		sl.setExplain("删除项目结果");//操作说明
		syslogService.saveSysLog(sl);
		
		return NONE;
	}
	/**
	 * 
	     * @Title: depItemResultLib   
	     * @Description: TODO(科室下拉框和检查项目下拉框联动513)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String depItemResultLib() throws WebException,SQLException{
		ItemResultLibDTO dto = new ItemResultLibDTO();
		dto.setDep_id(model.getDep_id());
		UserDTO user = (UserDTO) session.get("username");
		List<ExaminationItemDTO> li = this.itemResultLibService.getDepItemResultLib(dto,user.getCenter_num());
		this.outJsonResult(li);
		return NONE;
	}
}
