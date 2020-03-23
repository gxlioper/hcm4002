package com.hjw.wst.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hjw.wst.DTO.QuestDictListDTO;
import com.hjw.wst.DTO.QuestionOptionDTO;
import com.hjw.wst.DTO.ScaleDictOptionDTO;
import com.hjw.wst.domain.QuestDictListExt;
import com.hjw.wst.domain.ScaleDictAppraise;
import com.hjw.wst.domain.ScaleDictOption;
import com.hjw.wst.domain.ScaleDictQuestion;
import com.hjw.wst.domain.ScaleQuestionOption;
import com.hjw.wst.domain.ScaleResultList;
import com.hjw.wst.domain.QuestExamRecord;
import com.hjw.wst.service.ScaleService;
import com.synjones.framework.exception.ServiceException;
import com.synjones.framework.persistence.JdbcQueryManager;
import com.synjones.framework.persistence.PersistenceManager;
import com.synjones.framework.persistence.QueryManager;

/**
 * 
 * @Title: 火箭蛙体检管理系统
 * @Package com.hjw.wst.service.impl
 * @Description: 体检单位管理
 * @author: zwx
 * @version V2.0.0.0
 */
public class ScaleServiceImpl implements ScaleService {
	private QueryManager qm;
	private JdbcQueryManager jqm;
	private PersistenceManager pm;

	public void setPersistenceManager(PersistenceManager pm) {
		this.pm = pm;
	}

	public void setQueryManager(QueryManager qm) {
		this.qm = qm;
	}

	public void setJdbcQueryManager(JdbcQueryManager jqm) {
		this.jqm = jqm;
	}

	public ScaleDictQuestion loadScaleDictQuestion(long id) throws ServiceException {
		return (ScaleDictQuestion) qm.load(ScaleDictQuestion.class, id);
	}

	public ScaleDictQuestion saveScaleDictQuestion(ScaleDictQuestion sdq) throws ServiceException {
		this.pm.save(sdq);
		return sdq;
	}

	public ScaleDictQuestion updateScaleDictQuestion(ScaleDictQuestion sdq) throws ServiceException {
		this.pm.update(sdq);
		return sdq;
	}
	
	public ScaleDictOption loadScaleDictOption(long id) throws ServiceException {
		return (ScaleDictOption) qm.load(ScaleDictOption.class, id);
	}
	
	public ScaleDictOption saveScaleDictOption(ScaleDictOption sdo) throws ServiceException {
		this.pm.save(sdo);
		return sdo;
	}
	
	public ScaleDictOption updateScaleDictOption(ScaleDictOption sdo) throws ServiceException {
		this.pm.update(sdo);
		return sdo;
	}
	
	public ScaleQuestionOption loadScaleQuestionOption(long id) throws ServiceException {
		return (ScaleQuestionOption) qm.load(ScaleQuestionOption.class, id);
	}
	
	public ScaleQuestionOption saveScaleQuestionOption(ScaleQuestionOption sqo) throws ServiceException {
		this.pm.save(sqo);
		return sqo;
	}
	
	public ScaleQuestionOption updateScaleQuestionOption(ScaleQuestionOption sqo) throws ServiceException {
		this.pm.update(sqo);
		return sqo;
	}
	
	public ScaleDictAppraise loadScaleDictAppraise(long id) throws ServiceException {
		return (ScaleDictAppraise) qm.load(ScaleDictAppraise.class, id);
	}
	
	public ScaleDictAppraise saveScaleDictAppraise(ScaleDictAppraise sqa) throws ServiceException {
		this.pm.save(sqa);
		return sqa;
	}
	
	public ScaleDictAppraise updateScaleDictAppraise(ScaleDictAppraise sqa) throws ServiceException {
		this.pm.update(sqa);
		return sqa;
	}
	
	public QuestDictListExt loadQuestDictListExt(String scale_code) throws ServiceException {
		return (QuestDictListExt) qm.load(QuestDictListExt.class, scale_code);
	}
	
	public QuestDictListExt saveQuestDictListExt(QuestDictListExt qdle) throws ServiceException {
		this.pm.save(qdle);
		return qdle;
	}
	
	public QuestDictListExt updateQuestDictListExt(QuestDictListExt qdle) throws ServiceException {
		this.pm.update(qdle);
		return qdle;
	}
	
	public QuestExamRecord loadQuestExamRecord(String scale_code) throws ServiceException {
		return (QuestExamRecord) qm.load(QuestExamRecord.class, scale_code);
	}
	
	public QuestExamRecord saveQuestExamRecord(QuestExamRecord bri) throws ServiceException {
		this.pm.save(bri);
		return bri;
	}
	
	public QuestExamRecord updateQuestExamRecord(QuestExamRecord bri) throws ServiceException {
		this.pm.update(bri);
		return bri;
	}
	
	public ScaleResultList loadScaleResultList(String scale_code) throws ServiceException {
		return (ScaleResultList) qm.load(ScaleResultList.class, scale_code);
	}
	
	public ScaleResultList saveScaleResultList(ScaleResultList srl) throws ServiceException {
		this.pm.save(srl);
		return srl;
	}
	
	public ScaleResultList updateScaleResultList(ScaleResultList srl) throws ServiceException {
		this.pm.update(srl);
		return srl;
	}
	
	public List<ScaleDictOptionDTO> getOptionList(int questionID, String scale_code, String exam_num) throws ServiceException {
		String sql = "select sdo.*,sqo.questionID,CASE WHEN srl.exam_num is null THEN 0 ELSE 1 END AS selected "
				+ " from scale_dict_option sdo left join scale_question_option sqo on sdo.id = sqo.optionID "
				+ " left join scale_result_list srl on srl.answerID = sqo.optionID and srl.questionID = sqo.questionID and exam_num = '"+exam_num+"' " 
				+ " where 1=1 ";
				sql += " and sdo.scale_code = '"+scale_code+"'";
				if(questionID > 0) {
					sql += "and sqo.questionID = " + questionID;
				}
				sql += " order by sqo.questionID,sdo.seqNo";
				List<ScaleDictOptionDTO> optionList = this.jqm.getList(sql, ScaleDictOptionDTO.class);
				if(optionList == null) {
					optionList = new ArrayList<ScaleDictOptionDTO>();
				}
				return optionList;
	}

	public List<QuestionOptionDTO> getQuestionOptionList(String scale_code, String exam_num) throws ServiceException {
		String sql1 = "select * from scale_dict_question where scale_code = '"+scale_code+"' order by seqNo ";
		List<ScaleDictQuestion> questionList = this.jqm.getList(sql1, ScaleDictQuestion.class);
		
//			String sql2 = "select sdo.*,CASE WHEN srl.exam_num is null THEN 0 ELSE 1 END AS selected "
//						+ " from scale_dict_option sdo"
//						+ " left join scale_question_option sqo on sdo.id = sqo.optionID "
//						+ " left join scale_result_list srl on srl.answerID = sqo.optionID and srl.questionID = sqo.questionID and exam_num = '"+exam_num+"' "
//						+ "	where sqo.questionID = "+question.getId();
		Map<Integer, QuestionOptionDTO> map = new TreeMap<>();
		for(ScaleDictQuestion question : questionList) {
			QuestionOptionDTO questionOptionDTO = new QuestionOptionDTO();
			questionOptionDTO.setQuestion(question);
			questionOptionDTO.setOptions(new ArrayList<ScaleDictOptionDTO>());
			map.put(question.getId(), questionOptionDTO);
		}
		List<ScaleDictOptionDTO> optionListAll = this.getOptionList(0, scale_code, exam_num);
		for(ScaleDictOptionDTO option : optionListAll) {
			QuestionOptionDTO questionOptionDTO = map.get(option.getQuestionID());
			questionOptionDTO.getOptions().add(option);
		}
		List<QuestionOptionDTO> questionOptionList = new ArrayList<>();
		for(Integer i : map.keySet()) {
			questionOptionList.add(map.get(i));
		}
		return questionOptionList;
	}
	
	public List<ScaleDictAppraise> getAppraiseList(String scale_code) throws ServiceException {
		String sql = "select * from scale_dict_appraise where scale_code = '"+scale_code+"' order by fromPoint";
		List<ScaleDictAppraise> appraiseList = this.jqm.getList(sql, ScaleDictAppraise.class);
		return appraiseList;
	}
	
	public ScaleDictAppraise queryScaleDictAppraise(String scale_code, int score) throws ServiceException {
		String sql = "select * from scale_dict_appraise where scale_code = '"+scale_code+"' and fromPoint <= "+score+" and "+score+" <= toPoint";
		List<ScaleDictAppraise> appraiseList = this.jqm.getList(sql, ScaleDictAppraise.class);
		if(appraiseList != null && !appraiseList.isEmpty()) {
			return appraiseList.get(0);
		}
		return new ScaleDictAppraise();
	}
	
	public QuestExamRecord queryQuestExamRecord(String scale_code, String exam_num) throws ServiceException {
		String sql = "select * from quest_exam_record where peId = '"+exam_num+"' and quest_sub_code = '"+scale_code+"'";
		List<QuestExamRecord> appraiseList = this.jqm.getList(sql, QuestExamRecord.class);
		if(appraiseList != null && !appraiseList.isEmpty()) {
			return appraiseList.get(0);
		}
		return new QuestExamRecord();
	}
	
	public ScaleResultList queryScaleResultList(int questionID, String exam_num) throws ServiceException {
		String sql = "select * from scale_result_list where exam_num = '"+exam_num+"' and questionID = "+questionID;
		List<ScaleResultList> appraiseList = this.jqm.getList(sql, ScaleResultList.class);
		if(appraiseList != null && !appraiseList.isEmpty()) {
			return appraiseList.get(0);
		}
		return new ScaleResultList();
	}
	
	public List<QuestDictListDTO> getQuestDictList(String quest_code, String exam_num) throws ServiceException {
		String sql =  "select qdl.* from quest_dict_list qdl "
					+ " join quest_exam_record qer on qdl.quest_sub_code = qer.quest_sub_code "
					+ " where peId = '"+exam_num+"' and quest_code = '"+quest_code+"'";
		List<QuestDictListDTO> qdList = this.jqm.getList(sql, QuestDictListDTO.class);
		return qdList;
	}

	@Override
	public List<QuestDictListDTO> getQuestDictList(String quest_code) throws ServiceException {
		String sql =  "select qdl.* from quest_dict_list qdl where quest_code = '"+quest_code+"'";
		List<QuestDictListDTO> qdList = this.jqm.getList(sql, QuestDictListDTO.class);
		return qdList;
	}
}
