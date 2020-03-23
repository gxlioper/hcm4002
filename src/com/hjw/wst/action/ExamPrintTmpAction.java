package com.hjw.wst.action;

import java.util.List;

import com.hjw.wst.DTO.ExamPrintTmpDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.ExamPrintTmpModel;
import com.hjw.wst.service.ExamPrintTmpService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

import net.sf.json.JSONArray;

public class ExamPrintTmpAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;

	private ExamPrintTmpModel model = new ExamPrintTmpModel();
	private ExamPrintTmpService examPrintTmpService;

	public ExamPrintTmpModel getModel() {
		return model;
	}
	public void setModel(ExamPrintTmpModel model) {
		this.model = model;
	}
	public void setExamPrintTmpService(ExamPrintTmpService examPrintTmpService) {
		this.examPrintTmpService = examPrintTmpService;
	}
	
	/**
	 * 1153 保存预览打印临时数据
	     * @Title: saveExamPrintTmp   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String saveExamPrintTmp() throws WebException {
		if ((this.model.getExamPrintTmpLists() == null) || (this.model.getExamPrintTmpLists().length() <= 0)) {
			this.message = "error-记录无效";
		} else {
			try {
				JSONArray printlist = JSONArray.fromObject(this.model.getExamPrintTmpLists());
				List<ExamPrintTmpDTO> print = (List<ExamPrintTmpDTO>) JSONArray.toCollection(printlist,
						ExamPrintTmpDTO.class);
				if ((print == null) || (print.size() <= 0)) {
					this.message = "error-记录无效";
				} else {
					UserDTO user = (UserDTO) session.get("username");
					this.model.setExamPrintTmpList(print);
					this.message = this.examPrintTmpService.saveExamPrintTmp(model, user);
				}
			} catch (Exception ex) {
				this.message = "error-操作失败";
			}
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
}
