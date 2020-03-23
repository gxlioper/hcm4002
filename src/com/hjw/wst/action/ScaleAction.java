package com.hjw.wst.action;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hjw.util.DateTimeUtil;
import com.hjw.wst.DTO.QuestDictListDTO;
import com.hjw.wst.DTO.QuestionOptionDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.domain.QuestExamRecord;
import com.hjw.wst.domain.QuestDictListExt;
import com.hjw.wst.domain.ScaleDictAppraise;
import com.hjw.wst.domain.ScaleResultList;
import com.hjw.wst.domain.SysLog;
import com.hjw.wst.model.ScaleModel;
import com.hjw.wst.service.ScaleService;
import com.hjw.wst.service.SyslogService;
import com.hjw.wst.service.UserInfoService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.action
 * @author: zwx
 * @version V2.0.0.0
 */
@SuppressWarnings("rawtypes")
public class ScaleAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private Log log = LogFactory.getLog(this.getClass());
	@SuppressWarnings("unused")
	private ScaleModel model = new ScaleModel();
	private int page = 1; // 当前页
	private int rows = 15; // easyui每页显示条数
    private SyslogService syslogService;
    private ScaleService scaleService;
    private UserInfoService userInfoService;
    
	public void setSyslogService(SyslogService syslogService) {
   		this.syslogService = syslogService;
   	}
	
	public void setScaleService(ScaleService scaleService) {
		this.scaleService = scaleService;
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
	public ScaleModel getModel() {
		return model;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/**
	 * 量表管理答题界面1341
	     * @Title: scale_answer   
	     * @return: String      
	     * @throws WebException 
	 */
	public String scale_answer() throws WebException {
		String scale_code = this.model.getScale_code();
		String exam_num = this.model.getExam_num();
		
		QuestDictListExt questDictListExt = this.scaleService.loadQuestDictListExt(scale_code);
		this.model.setNameCn(questDictListExt.getNameCn());
		this.model.setNameEn(questDictListExt.getNameEn());
		this.model.setContent(questDictListExt.getContent());
		
		List<QuestionOptionDTO> questionOptionList = this.scaleService.getQuestionOptionList(scale_code, exam_num);
		this.model.setQuestionOptionList(questionOptionList);
		
		List<ScaleDictAppraise> appraiseList = this.scaleService.getAppraiseList(scale_code);
		this.model.setAppraiseList(appraiseList);
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1341");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}

	/**
	 * 保存量表问卷结果-1342
	     * @Title: saveScaleResult   
	     * @return: String      
	     * @throws WebException 
	 */
	public String saveScaleResult() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		
		QuestExamRecord record = this.scaleService.queryQuestExamRecord(this.model.getScale_code(), this.model.getExam_num());
		if(record.getRecId() == 0) {
			record.setPeId(this.model.getExam_num());
			record.setQuest_sub_code(this.model.getScale_code());
			record.setScore(this.model.getScore());
			record.setInDate(DateTimeUtil.parse());
			record.setTraner(user.getUserid());
			if(this.model.isFinish()) {
				record.setTranDate(DateTimeUtil.parse());
				record.setTranFlag("1");
			} else {
				record.setTranFlag("0");
			}
			ScaleDictAppraise appraise = this.scaleService.queryScaleDictAppraise(this.model.getScale_code(), this.model.getScore());
			record.setAppraise(appraise.getContent());
			this.scaleService.saveQuestExamRecord(record);
		} else {
			record.setScore(this.model.getScore());
			record.setTraner(user.getUserid());
			if(this.model.isFinish()) {
				record.setTranDate(DateTimeUtil.parse());
				record.setTranFlag("1");
			} else {
				record.setTranFlag("0");
			}
			ScaleDictAppraise appraise = this.scaleService.queryScaleDictAppraise(this.model.getScale_code(), this.model.getScore());
			record.setAppraise(appraise.getContent());
			this.scaleService.updateQuestExamRecord(record);
		}
		
		JSONArray ja = new JSONArray(this.model.getScale_result_list());
		for(int i=0;i<ja.length();i++) {
			JSONObject jo = ja.optJSONObject(i);
			int questionID = jo.optInt("questionID");
			ScaleResultList detail = this.scaleService.queryScaleResultList(questionID, this.model.getExam_num());
			if(detail.getId() == 0) {
				detail.setExam_num(this.model.getExam_num());
				detail.setQuestionID(questionID);
				detail.setAnswerID(jo.optInt("answerID"));
				detail.setScore(jo.optInt("score"));
				detail.setRecordID(record.getRecId());
				this.scaleService.saveScaleResultList(detail);
			} else {
				detail.setAnswerID(jo.optInt("answerID"));
				detail.setScore(jo.optInt("score"));
				this.scaleService.updateScaleResultList(detail);
			}
		}
		
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("1");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1342");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		this.message = "ok-保存问卷结果成功！";
		
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 问卷管理展示界面1343
	     * @Title: quest_show   
	     * @return: String      
	     * @throws WebException 
	 */
	public String quest_show() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: 获取一级菜单-1344
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getFirstMenu_questshow()throws WebException, SQLException {
		List<QuestDictListDTO> list = this.scaleService.getQuestDictList(this.model.getQuest_code(), this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
	

	/**
	 * 量表管理结果展示界面1345
	 * @Title: scale_show   
	 * @return: String      
	 * @throws WebException 
	 */
	public String scale_show() throws WebException {
		String scale_code = this.model.getScale_code();
		String exam_num = this.model.getExam_num();
		
		QuestDictListExt questDictListExt = this.scaleService.loadQuestDictListExt(scale_code);
		this.model.setNameCn(questDictListExt.getNameCn());
		this.model.setNameEn(questDictListExt.getNameEn());
		this.model.setContent(questDictListExt.getContent());
		this.model.setFull_score(questDictListExt.getFull_score());
		
		List<QuestionOptionDTO> questionOptionList = this.scaleService.getQuestionOptionList(scale_code, exam_num);
		this.model.setQuestionOptionList(questionOptionList);
		
		List<ScaleDictAppraise> appraiseList = this.scaleService.getAppraiseList(scale_code);
		this.model.setAppraiseList(appraiseList);
		
		QuestExamRecord record = this.scaleService.queryQuestExamRecord(this.model.getScale_code(), this.model.getExam_num());
		this.model.setScore(record.getScore());
		this.model.setCreater(userInfoService.loadUserInfo(record.getTraner()).getChi_Name());
		
		UserDTO user = (UserDTO) session.get("username");
		SysLog sl = new SysLog();
		sl.setCenter_num(user.getCenter_num());
		sl.setUserid(user.getUserid()+"");
		sl.setOper_type("0");//0 查询，1插入 2修改 3 删除 4导入 5，登录  9 其他 
		sl.setXtgnid("");//可不填写
		sl.setXtgnid2("1345");//子功能id 必须填写
		sl.setExplain("");//操作说明
		syslogService.saveSysLog(sl);
		return SUCCESS;
	}
	
	/**
	 * 问卷管理答题界面1346
	     * @Title: quest_answer   
	     * @return: String      
	     * @throws WebException 
	 */
	public String quest_answer() throws WebException {
		return SUCCESS;
	}
	
	/**
	 * 
	     * @Title: 获取问卷答题一级菜单-1347
	     * @param: @throws WebException
	     * @param: @throws SQLException      
	     * @return: String      
	     * @throws
	 */
	public String getFirstMenu_questanswer()throws WebException {
		List<QuestDictListDTO> list = this.scaleService.getQuestDictList(this.model.getQuest_code());
		this.outJsonResult(list);
		return NONE;
	}
}