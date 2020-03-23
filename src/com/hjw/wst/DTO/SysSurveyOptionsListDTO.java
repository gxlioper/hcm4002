package com.hjw.wst.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
     * @Title:  火箭蛙体检管理系统   
     * @Package com.hjw.wst.DTO   
     * @Description: 问卷显示页面获取数据
     * @author:zr      
     * @date:   2017年2月14日 下午2:59:30   
     * @version V2.0.0.0
 */
public class SysSurveyOptionsListDTO  implements  Serializable{

	/**   
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)   
	 */ 
	private static final long serialVersionUID = -566074760179953206L;
	private  List<SysSurveyQuestionDTO>   SysSurveyQuestionList = new ArrayList<SysSurveyQuestionDTO>();//问题
	private  List<SysQuestionOptionsDTO>  SysQuestionOptionsList  = new ArrayList<SysQuestionOptionsDTO>();//选项
	private  List<sysSurveyQuestionRelationDTO>  surverList = new ArrayList<sysSurveyQuestionRelationDTO>();//获取用户选择的选项
	
	public List<SysSurveyQuestionDTO> getSysSurveyQuestionList() {
		return SysSurveyQuestionList;
	}
	public List<SysQuestionOptionsDTO> getSysQuestionOptionsList() {
		return SysQuestionOptionsList;
	}
	public void setSysSurveyQuestionList(List<SysSurveyQuestionDTO> sysSurveyQuestionList) {
		SysSurveyQuestionList = sysSurveyQuestionList;
	}
	public void setSysQuestionOptionsList(List<SysQuestionOptionsDTO> sysQuestionOptionsList) {
		SysQuestionOptionsList = sysQuestionOptionsList;
	}
	public List<sysSurveyQuestionRelationDTO> getSurverList() {
		return surverList;
	}
	public void setSurverList(List<sysSurveyQuestionRelationDTO> surverList) {
		this.surverList = surverList;
	}
}
