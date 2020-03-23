package com.hjw.wst.service;

import com.hjw.wst.DTO.ExamSummaryReviewDTO;
import com.hjw.wst.DTO.ExaminfoChargingItemDTO;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamSummaryReviewModel;
import com.synjones.framework.exception.ServiceException;

import java.util.List;

public interface ExamSummaryReviewService {

	/**
	 * 保存复查设定信息
	     * @Title: saveExamSummaryReview   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryReview(ExamSummaryReviewModel model, UserDTO user,String apptype) throws ServiceException;

	/**
	 * 通过体检号查询复查设定信息
	     * @Title: getExamSummaryReview
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException
	     * @return: ExamSummaryReviewDTO
	     * @throws
	 */
	public ExamSummaryReviewDTO getExamSummaryReview(String exam_num,String apptype) throws ServiceException;

	/**
	 * 查询总检复查列表信息
	     * @Title: getExamSummaryReviewList
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param: @param model
	     * @param: @param rows
	     * @param: @param page
	     * @param: @param sort
	     * @param: @param order
	     * @param: @return
	     * @param: @throws ServiceException
	     * @return: PageReturnDTO
	     * @throws
	 */
	public PageReturnDTO getExamSummaryReviewList(ExamSummaryReviewModel model, int rows, int page, String sort, String order) throws ServiceException;

	/**
	 * 作废，通知复查信息
	     * @Title: updateExamSummaryReview
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException
	     * @return: String
	     * @throws
	 */
//	public String updateExamSummaryReview(ExamSummaryReviewModel model,UserDTO user) throws ServiceException;
	public String updateExamSummaryReview(String review_status, String notice_type, String sms_note, ExamSummaryReviewDTO esr, UserDTO user) throws ServiceException;

	/**
	 * 取消恢复复查信息
	     * @Title: updateExamSummaryReview
	     * @Description: TODO(这里用一句话描述这个方法的作用)
	     * @param: @param model
	     * @param: @param user
	     * @param: @return
	     * @param: @throws ServiceException
	     * @return: String
	     * @throws
	 */
	public String saveCancelReview(ExamSummaryReviewModel model, UserDTO user) throws ServiceException;
	
	/**
	 * 查询已添加的复查项目
	     * @Title: getExamSummaryReviewItemList   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param exam_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminfoChargingItemDTO>      
	     * @throws
	 */
	public List<ExaminfoChargingItemDTO> getExamSummaryReviewItemList(String exam_num) throws ServiceException;
	
}
