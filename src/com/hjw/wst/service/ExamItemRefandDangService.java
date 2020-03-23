package com.hjw.wst.service;

import java.util.List;

import com.hjw.wst.domain.ExamItemRefandDang;
import com.synjones.framework.exception.ServiceException;
/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.service   
     * @Description:  检查项目文本
     * @author: 张瑞    
     * @date:   2016年9月21日 上午10:12:21   
     * @version V2.0.0.0
 */
public interface ExamItemRefandDangService {
	/**
	 * 
	     * @Title: addexamItemRefandDang   
	     * @Description: TODO(添加检查项目文本)   
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void addexamItemRefandDang(ExamItemRefandDang dang) throws ServiceException;
	/**
	 * 
	     * @Title: findClass   
	     * @Description: TODO(查找实体类)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: ExamItemRefandDang      
	     * @throws
	 */
	public ExamItemRefandDang findClass(long id) throws ServiceException;
	/**
	 * 
	     * @Title: getExaminationItem   
	     * @Description: TODO(通过检查项目ID获取文本内容)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: List<ExaminationItem>      
	     * @throws
	 */
	public List<ExamItemRefandDang> getExamItemRefandDanga(long id) throws ServiceException;
	/**
	 * 
	     * @Title: updateExamItemRefandDang   
	     * @Description: TODO(修改检查项目文本)   
	     * @param: @param dang
	     * @param: @throws ServiceException      
	     * @return: void      
	     * @throws
	 */
	public void updateExamItemRefandDang(ExamItemRefandDang dang) throws ServiceException;
	/**
	 * 
	     * @Title: deletExamItemRefandDang   
	     * @Description: TODO(根据id删除文本)   
	     * @param: @param id
	     * @param: @return
	     * @param: @throws ServiceException      
	     * @return: int      
	     * @throws
	 */
	public int deletExamItemRefandDang(long id) throws ServiceException;
}
