package com.hjw.crm.service;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import com.hjw.crm.DTO.JianKzDTO;
import com.hjw.crm.model.JianKzModel;
import com.hjw.wst.DTO.PageReturnDTO;
import com.hjw.wst.DTO.UserDTO;
import com.synjones.framework.exception.ServiceException;

public interface JianKzService {
	/**
	 * 总检是否允许发健康证
	     * @Title: saveExamSummaryJianKangZheng   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param model
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamSummaryJianKangZheng(JianKzModel model,UserDTO user)throws ServiceException;
	/**
	 * 获取体检者本年度的健康证号
	     * @Title: getOldHealthNoOneYear   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @param arch_num
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: String      
	     * @throws
	 */
	public List<JianKzDTO> getOldHealthNoOneYear(String arch_num) throws ServiceException;
	public String jianKangZhuangChuanShu(JianKzModel model, String center_num)  throws UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException ;
	public String jianKangZhuangBuFenChuanShu(String exam_nums, String center_num) throws ServiceException,SQLException, UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException ;
	public String jianKangZhuangAllChuanShu(JianKzModel model, String center_num) throws ServiceException,SQLException, UnsupportedEncodingException, IllegalArgumentException, IllegalAccessException, FileNotFoundException ;
	public PageReturnDTO getJKZList(JianKzModel model,int page,int rows, UserDTO user) throws ServiceException,SQLException;
	public JianKzModel getResultJianKzModel(String exam_num, String center_num) throws ServiceException,SQLException;
	public String getExamFlag(String exam_num) throws ServiceException,SQLException;
}
