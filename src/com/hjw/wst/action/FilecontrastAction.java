package com.hjw.wst.action;

import java.util.ArrayList;
import java.util.List;

import com.hjw.wst.DTO.CustomerfileinformationqueryDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.CustomerfileinformationqueryModel;
import com.hjw.wst.service.FilecontrastService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class FilecontrastAction  extends BaseAction implements ModelDriven{
	private  FilecontrastService   filecontrastService;
	private  CustomerfileinformationqueryModel  model = new  CustomerfileinformationqueryModel();
	
	@Override
	public Object getModel() {
		return model;
	}
	public FilecontrastService getFilecontrastService() {
		return filecontrastService;
	}
	public void setFilecontrastService(FilecontrastService filecontrastService) {
		this.filecontrastService = filecontrastService;
	}
	public void setModel(CustomerfileinformationqueryModel model) {
		this.model = model;
	}
	/**
	 * 
	     * @Title: queryEaxmInfo   
	     * @Description: TODO(526档案对比--查询档案)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String queryEaxmInfo() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		CustomerfileinformationqueryDTO dto = new  CustomerfileinformationqueryDTO();
		dto.setArch_num(model.getArch_num());//档案号
		dto.setExam_num(model.getExam_num());//体检号
		dto.setUser_name(model.getUser_name());//姓名
		dto.setPhone(model.getPhone());//手机
		dto.setAddress(model.getAddress());//地址
		dto.setCompany(model.getCompany());//单位
		dto.setSex(model.getSex());//性别
		dto.setId_num(model.getId_num());//身份证号
		dto.setId(model.getId());
		List<CustomerfileinformationqueryDTO> li = new ArrayList<CustomerfileinformationqueryDTO>();
		if( model.getId()>0 ){
			 li = this.filecontrastService.queryCustomerInfo(dto, user.getCenter_num());
		}
		this.outJsonResult(li);
		return NONE;
	}
	/**
	 * 
	     * @Title: jkdadb   
	     * @Description: TODO(健康档案527)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String jkdadb() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		CustomerfileinformationqueryDTO dto = new  CustomerfileinformationqueryDTO();
		dto.setId(model.getId());
		dto.setArch_num(model.getArch_num());//档案号
		dto.setExam_num(model.getExam_num());//体检号
		dto=this.filecontrastService.getCustomerInfo(dto,user.getCenter_num());
		model.setNum(dto.getNum());//体积次数
		model.setTj_exam_set(dto.getTj_exam_set());//套餐名称
		model.setExamSet_amount(dto.getExamSet_amount());//金额
		model.setYxfx(dto.getYxfx());//阳性指标
		this.outJsonResult(dto);
		return NONE;
	}
}
