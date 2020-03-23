package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.DTO.QuestDictListDTO;
import com.hjw.wst.DTO.QuestionOptionDTO;
import com.hjw.wst.domain.QuestExamRecord;
import com.hjw.wst.domain.QuestDictListExt;
import com.hjw.wst.domain.ScaleDictAppraise;
import com.hjw.wst.domain.ScaleDictOption;
import com.hjw.wst.domain.ScaleDictQuestion;
import com.hjw.wst.domain.ScaleQuestionOption;
import com.hjw.wst.domain.ScaleResultList;
import com.synjones.framework.exception.ServiceException;

public interface ScaleService {
	
	public ScaleDictQuestion loadScaleDictQuestion(long id) throws ServiceException;

	public ScaleDictQuestion saveScaleDictQuestion(ScaleDictQuestion sdq) throws ServiceException;

	public ScaleDictQuestion updateScaleDictQuestion(ScaleDictQuestion sdq) throws ServiceException;
	
	public ScaleDictOption loadScaleDictOption(long id) throws ServiceException;
	
	public ScaleDictOption saveScaleDictOption(ScaleDictOption sdo) throws ServiceException;
	
	public ScaleDictOption updateScaleDictOption(ScaleDictOption sdo) throws ServiceException;
	
	public ScaleQuestionOption loadScaleQuestionOption(long id) throws ServiceException;
	
	public ScaleQuestionOption saveScaleQuestionOption(ScaleQuestionOption sqo) throws ServiceException;
	
	public ScaleQuestionOption updateScaleQuestionOption(ScaleQuestionOption sqo) throws ServiceException;
	
	public ScaleDictAppraise loadScaleDictAppraise(long id) throws ServiceException;
	
	public ScaleDictAppraise saveScaleDictAppraise(ScaleDictAppraise sqa) throws ServiceException;
	
	public ScaleDictAppraise updateScaleDictAppraise(ScaleDictAppraise sqa) throws ServiceException;
	
	public QuestDictListExt loadQuestDictListExt(String scale_code) throws ServiceException;
	
	public QuestDictListExt saveQuestDictListExt(QuestDictListExt qdle) throws ServiceException;
	
	public QuestDictListExt updateQuestDictListExt(QuestDictListExt qdle) throws ServiceException;
	
	public QuestExamRecord loadQuestExamRecord(String scale_code) throws ServiceException;
	
	public QuestExamRecord saveQuestExamRecord(QuestExamRecord bri) throws ServiceException;
	
	public QuestExamRecord updateQuestExamRecord(QuestExamRecord bri) throws ServiceException;
	
	public ScaleResultList loadScaleResultList(String scale_code) throws ServiceException;
	
	public ScaleResultList saveScaleResultList(ScaleResultList srl) throws ServiceException;
	
	public ScaleResultList updateScaleResultList(ScaleResultList srl) throws ServiceException;
	
	public List<QuestionOptionDTO> getQuestionOptionList(String scale_code, String exam_num) throws ServiceException;
	
	public List<ScaleDictAppraise> getAppraiseList(String scale_code) throws ServiceException;
	
	public ScaleDictAppraise queryScaleDictAppraise(String scale_code, int score) throws ServiceException;
	
	public QuestExamRecord queryQuestExamRecord(String scale_code, String exam_num) throws ServiceException;
	
	public ScaleResultList queryScaleResultList(int questionID, String exam_num) throws ServiceException;
	
	public List<QuestDictListDTO> getQuestDictList(String quest_code, String exam_num) throws ServiceException;

	public List<QuestDictListDTO> getQuestDictList(String quest_code) throws ServiceException;
}