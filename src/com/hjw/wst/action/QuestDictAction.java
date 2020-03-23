package com.hjw.wst.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;
import com.hjw.config.Logincheck;
import com.hjw.wst.DTO.QuestExamDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.QuestDictTitle;
import com.hjw.wst.domain.QuestDictItem;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.QuestModel;
import com.hjw.wst.service.QuestDictService;
import com.hjw.wst.service.SyslogService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class QuestDictAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Logincheck logincheck;
	private int rp = 15; // 每页显示的条数
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
	
	private QuestDictService questDictService;
	private QuestModel model = new QuestModel();
	private QuestDictItem modelInfo = new QuestDictItem();
	private SyslogService syslogService;    

   	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	public Logincheck getLogincheck() {
		return logincheck;
	}

	public void setLogincheck(Logincheck logincheck) {
		this.logincheck = logincheck;
	}

	public int getRp() {
		return rp;
	}

	public void setRp(int rp) {
		this.rp = rp;
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

	public void setQuestDictService(QuestDictService questDictService) {
		this.questDictService = questDictService;
	}

	public QuestModel getModel() {
		return model;
	}

	public void setModel(QuestModel model) {
		this.model = model;
	}

	public QuestDictItem getModelInfo() {
		return modelInfo;
	}

	public void setModelInfo(QuestDictItem modelInfo) {
		this.modelInfo = modelInfo;
	}


	/**
	 * 
	     * @Title: brainExaminMsg   页面跳转
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String brainExaminMsg() throws WebException, SQLException {
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl =  new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1500");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	
	/**
	 * 
	     * @Title: titleAddEdit   新增/修改跳转页面
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String titleAddEdit() throws WebException, SQLException {
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	     * @Title: projectAddEdit   新增/修改跳转页面
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String projectAddEdit() throws WebException, SQLException {
		
		return SUCCESS;
	}
	
	
	/**
	 * 
	     * @Title: queryBrainTitle   分页查询脑健康title信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryBrainTitle() throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		questExamDTO.setTitleName(model.getTitleName());  //搜索查询
		questExamDTO.setQuest_sub_code(model.getQuest_sub_code()); //设置类别
		PageReturnDTO dto = this.questDictService.queryQuestTitle(questExamDTO, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: queryProjectTwoGrid   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryProjectTwoGrid() throws WebException, SQLException {
		QuestExamDTO questExamDTO = new QuestExamDTO();
		questExamDTO.setTitleID(model.getTitleID());;  //查询
		questExamDTO.setTitleName(model.getTitleName());  //搜索查询
		PageReturnDTO dto = this.questDictService.queryProjectTwoGrid(questExamDTO, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	
	
	
	/**
	 * 
	     * @Title: queryModuleList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryModuleList()throws WebException, SQLException {
		QuestExamDTO questExam = new QuestExamDTO();
		questExam.setTitleName(model.getTitleName());  //搜索查询
		PageReturnDTO dto = this.questDictService.queryModuleList(questExam, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	
	
	/**
	 * 
	     * @Title: queryStyleList  样式信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String queryStyleList()throws WebException, SQLException {
		QuestExamDTO brain = new QuestExamDTO();
		brain.setTitleName(model.getTitleName());  //搜索查询
		PageReturnDTO dto = this.questDictService.queryStyleList(brain, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: brainTitleAdd   title新增/修改信息
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void brainTitleAdd()throws WebException, SQLException {
		
		if ((this.model.getTitleID() != 0) && (!"".equals(this.model.getTitleID()))) {
			QuestDictTitle questDictTitle = new QuestDictTitle();
			questDictTitle.setTitleid(model.getTitleID());
			questDictTitle.setTitleName(model.getTitleName());
			questDictTitle.setSupID(model.getSupID());
			questDictTitle.setLevel(model.getLevel());
			questDictTitle.setSeqNo(model.getSeqNo());
			questDictTitle.setIsVisable(model.getIsVisable());
			questDictTitle.setQuest_sub_code(model.getQuest_sub_code());
			questDictTitle.setTitleColumn(model.getTitleColumn());
			try {
				this.questDictService.editQuestTitle(questDictTitle);
				this.message="修改成功！";
			} catch (Exception e) {
				this.message="修改失败！";
				e.printStackTrace();
			}
		}else{
			QuestDictTitle questDictTitle = new QuestDictTitle();
			questDictTitle.setTitleName(model.getTitleName());
			questDictTitle.setSupID(model.getSupID());
			questDictTitle.setLevel(model.getLevel());
			questDictTitle.setSeqNo(model.getSeqNo());
			questDictTitle.setIsVisable(model.getIsVisable());
			questDictTitle.setQuest_sub_code(model.getQuest_sub_code());
			questDictTitle.setTitleColumn(model.getTitleColumn());
			
			try {
				this.questDictService.saveQuestTitle(questDictTitle);
				this.message="新增成功！";
			} catch (Exception e) {
				this.message="新增失败！";
				e.printStackTrace();
			}
			
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	     * @Title: deleteBrainTitle   删除标题
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void deleteBrainTitle() throws WebException, Exception {
	
		try {
			this.questDictService.deleteQuestTitle(this.model.getTitleID());
			this.message="删除标题成功！";
		} catch (Exception e) {
			this.message="删除标题失败！";
			e.printStackTrace();
		}
			
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
	}
	
	/**
	 * 
	     * @Title: getQuestionGrid   脑健康子项目问题查询
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getQuestionGrid()throws WebException, SQLException {
		QuestExamDTO questExam = new QuestExamDTO();
		if("0".equals(model.getSupItemId())){
			questExam.setSupItemId("");
		}else{
			questExam.setSupItemId(model.getSupItemId());
		}
		questExam.setTitleID(model.getTitleID());  
		questExam.setItemlevel(model.getItemlevel()); 
		questExam.setTitleName(model.getTitleName()); 
		PageReturnDTO dto = this.questDictService.getQuestionGrid(questExam, page, rows);
		this.outJsonResult(dto);
		return NONE;
	}
	
	/**
	 * 
	     * @Title: deleProjectQuest   删除项目
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws Exception      
	     * @return: void      
	     * @throws
	 */
	public void deleProjectQuest() throws WebException, Exception {

		try {
			this.questDictService.deleteProjectQuest(this.model.getItemId());
			this.message="删除项目成功！";
		} catch (Exception e) {
			this.message="删除项目失败！";
			e.printStackTrace();
		}
			
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(this.message));
	}
	
	
	
	/**
	 * 
	     * @Title: brainQuestionAdd   项目的修改/增加
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: void      
	     * @throws
	 */
	public void brainQuestionAdd()throws WebException, SQLException {
		
		if ((this.model.getItemId() != 0) && (!"".equals(this.model.getItemId()))) {
			QuestDictItem questItem = new QuestDictItem();
			
			questItem.setItemId(model.getItemId());//
			questItem.setItemName(model.getItemName());//
			questItem.setSupItemId(model.getSupItemId());//
			questItem.setItemlevel(model.getItemlevel());//
			questItem.setSeqNo(model.getSeqNo());//
			questItem.setTitleId(model.getTitleID());
			questItem.setIsVisable(model.getIsVisable());//
			questItem.setIsMulSel(model.getIsMulSel());//
			questItem.setLinkNo(model.getLinkNo());
			questItem.setSex(model.getSex());
			questItem.setIsDefault(0);
			questItem.setLinkItem("0");
			questItem.setLinksubItem("0");
			questItem.setItemCode(model.getItemCode());
			questItem.setTextUnit(model.getTextUnit());
			questItem.setIsDefault(model.getIsDefault());
			questItem.setDefaultResult(model.getDefaultResult());
			/*if("1".equals(model.getIsDefault())) {
				//为父类设置了默认值
				if("2".equals(model.getIsMulSel())) {
					//为输入框
					
				}else if("0".equals(model.getIsMulSel())) {
					//为单选
				}
			}*/
			
			
			
			try {
				this.questDictService.editQuestItem(questItem);
				this.message="修改成功！";
			} catch (Exception e) {
				this.message="修改失败！";
				e.printStackTrace();
			}
		}else{
			QuestDictItem questItem = new QuestDictItem();
			questItem.setItemName(model.getItemName());//
			questItem.setSupItemId(model.getSupItemId());
			questItem.setItemlevel(model.getItemlevel());//
			questItem.setSeqNo(model.getSeqNo());//
			questItem.setTitleId(model.getTitleID());
			questItem.setIsVisable(model.getIsVisable());//
			questItem.setIsMulSel(model.getIsMulSel());//
			questItem.setTextUnit(model.getTextUnit());
			questItem.setLinkItem("0");
			questItem.setLinkNo(model.getLinkNo());//
			questItem.setLinksubItem("0");
			questItem.setItemCode(model.getItemCode());
			questItem.setSex(model.getSex());
			questItem.setIsDefault(model.getIsDefault());
			questItem.setDefaultResult(model.getDefaultResult());
			try {
				this.questDictService.saveQuestItem(questItem);
				this.message="新增成功！";
			} catch (Exception e) {
				this.message="新增失败！";
				e.printStackTrace();
			}
			
		}
		
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(new Gson().toJson(this.message));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	     * @Title: querySelectType   查询问卷类型
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String querySelectType()throws WebException, SQLException {
		QuestExamDTO brain = new QuestExamDTO();
		//brain.setTitleName(model.getTitleName());  //搜索查询
		List<QuestExamDTO> list = this.questDictService.querySelectType(brain);
		this.outJsonResult(list);
		return NONE;
	}
}
