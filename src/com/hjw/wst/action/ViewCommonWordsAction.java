package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.SampleDemoDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.domain.ViewCommonWords;
import com.hjw.wst.model.ViewCommonWordsModel;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.ViewCommonWordsService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class ViewCommonWordsAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	
	private ViewCommonWordsModel model = new ViewCommonWordsModel();
	private ViewCommonWordsService viewCommonWordsService;
	private SyslogService syslogService;
	
	private int rows = 15; // easyui每页显示条数
	@Override
	public Object getModel() {
		return model;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public void setModel(ViewCommonWordsModel model) {
		this.model = model;
	}
	public void setViewCommonWordsService(ViewCommonWordsService viewCommonWordsService) {
		this.viewCommonWordsService = viewCommonWordsService;
	}
	
	public void setSyslogService(SyslogService syslogService) {
		this.syslogService = syslogService;
	}
	/**
	 * 影像科室常用词页面 383
	     * @Title: getViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getViewCommonWords() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("383");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 获取常用词列表 384
	     * @Title: getViewCommonWordsList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return      
	     * @return: String      
	     * @throws
	 */
	public String getViewCommonWordsList() throws WebException,SQLException{
		PageReturnDTO map = this.viewCommonWordsService.getViewCommonWordsList(this.model.getSample_id(), this.rows, this.getPage());
		this.outJsonResult(map);
		return NONE;
	}
	
	/**
	 * 新增修改保存影像科室常用词 385
	     * @Title: saveViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String saveViewCommonWords() throws WebException,SQLException{
		UserDTO user = (UserDTO) session.get("username");
		ViewCommonWords viewCommonWords = null;
		if(this.model.getId() > 0){//修改
			viewCommonWords = this.viewCommonWordsService.getViewCommonWords(this.model.getId());
			viewCommonWords.setSample_id(this.model.getSample_id());
			viewCommonWords.setExam_desc(this.model.getExam_desc());
			viewCommonWords.setExam_result(this.model.getExam_result());
			viewCommonWords.setIsActive("Y");
			viewCommonWords.setSeq_code(this.model.getSeq_code());
			viewCommonWords.setUpdater(user.getUserid());
			viewCommonWords.setUpdate_time(DateTimeUtil.parse());
			viewCommonWords.setIs_default(this.model.getIs_default());
			if(viewCommonWords.getIs_default() == 1){
				ViewCommonWords defaultViewCommonWords = this.viewCommonWordsService.getDefaultViewCommonWords(this.model.getSample_id());
				if(defaultViewCommonWords != null){
					defaultViewCommonWords.setIs_default(0);
					this.viewCommonWordsService.updateViewCommonWords(defaultViewCommonWords);
				}
			}
			
			this.viewCommonWordsService.updateViewCommonWords(viewCommonWords);
			
			this.message = "ok-修改成功!";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("2");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("385");//子功能id 必须填写
			sl.setExplain("修改");//操作说明
			syslogService.saveSysLog(sl);
		}else{//新增
			viewCommonWords = new ViewCommonWords();
			
			viewCommonWords.setSample_id(this.model.getSample_id());
			viewCommonWords.setExam_desc(this.model.getExam_desc());
			viewCommonWords.setExam_result(this.model.getExam_result());
			viewCommonWords.setIsActive("Y");
			viewCommonWords.setSeq_code(this.model.getSeq_code());
			viewCommonWords.setCreate_time(DateTimeUtil.parse());
			viewCommonWords.setCreater(user.getUserid());
			viewCommonWords.setUpdater(user.getUserid());
			viewCommonWords.setUpdate_time(DateTimeUtil.parse());
			viewCommonWords.setIs_default(this.model.getIs_default());
			if(viewCommonWords.getIs_default() == 1){
				ViewCommonWords defaultViewCommonWords = this.viewCommonWordsService.getDefaultViewCommonWords(this.model.getSample_id());
				if(defaultViewCommonWords != null){
					defaultViewCommonWords.setIs_default(0);
					this.viewCommonWordsService.updateViewCommonWords(defaultViewCommonWords);
				}
			}
			
			this.viewCommonWordsService.saveViewCommonWords(viewCommonWords);
			
			this.message = "ok-保存成功!";
			
			SysLog sl =  new SysLog();
			sl.setCenter_num(user.getCenter_num());
			sl.setUserid(user.getUserid()+"");
			sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
			sl.setXtgnid("");//可不填写
			sl.setXtgnid2("385");//子功能id 必须填写
			sl.setExplain("新增");//操作说明
			syslogService.saveSysLog(sl);
		}
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 根据样本名称加载样本列表   386
	     * @Title: getViewCommonSampleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getViewCommonSampleList() throws WebException,SQLException{
		List<SampleDemoDTO> list = this.viewCommonWordsService.getSampleList();
		this.outJsonResult(list);
		return NONE;
	}
	
	/**
	 * 显示新增修改常用词页面 387
	     * @Title: editViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String editViewCommonWords() throws WebException,SQLException{
		if(this.model.getId() != 0){
			ViewCommonWords viewCommonWords = this.viewCommonWordsService.getViewCommonWords(this.model.getId());
			this.model.setId(viewCommonWords.getId());
			this.model.setSample_id(viewCommonWords.getSample_id());
			this.model.setExam_desc(viewCommonWords.getExam_desc());
			this.model.setExam_result(viewCommonWords.getExam_result());
			this.model.setSeq_code(viewCommonWords.getSeq_code());
			this.model.setIs_default(viewCommonWords.getIs_default());
		}
		return SUCCESS;
	}
	
	/**
	 * 删除常用词信息 388
	     * @Title: delViewCommonWords   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String delViewCommonWords() throws WebException,SQLException{
		if(this.model.getId() == 0){
			this.message = "error-删除失败!";
		}else{
			ViewCommonWords viewCommonWords = this.viewCommonWordsService.getViewCommonWords(this.model.getId());
			viewCommonWords.setIsActive("N");
			this.viewCommonWordsService.updateViewCommonWords(viewCommonWords);
			this.message = "ok-删除成功!";
		}
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("3");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("388");//子功能id 必须填写
		sl.setExplain("修改");//操作说明
		syslogService.saveSysLog(sl);
		this.outJsonStrResult(this.message);
		return NONE;
	}

}
