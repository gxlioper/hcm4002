package com.hjw.wst.action;

import java.util.ArrayList;
import java.util.List;

import com.hjw.util.DateTimeUtil;
import com.hjw.util.ImageUtils;
import com.hjw.util.SummaryImage;
import com.hjw.wst.DTO.ExamInfoDTO;
import com.hjw.wst.DTO.HealthRiskExaminfoDTO;
import com.hjw.wst.DTO.HealthRiskItemExamresultDTO;
import com.hjw.wst.DTO.UserDTO;
import com.hjw.wst.model.HealthRiskAssessmentModel;
import com.hjw.wst.service.CommService;
import com.hjw.wst.service.HealthRiskAssessmentService;
import com.opensymphony.xwork2.ModelDriven;
import com.synjones.framework.exception.WebException;
import com.synjones.framework.web.action.BaseAction;

public class HealthRiskAssessmentAction extends BaseAction implements ModelDriven{

	private static final long serialVersionUID = 8217814910220881058L;
	private HealthRiskAssessmentService healthRiskAssessmentService;
	private CommService commService;
	private HealthRiskAssessmentModel model= new HealthRiskAssessmentModel();
	
	public HealthRiskAssessmentModel getModel() {
		return model;
	}
	public void setModel(HealthRiskAssessmentModel model) {
		this.model = model;
	}
	public void setHealthRiskAssessmentService(HealthRiskAssessmentService healthRiskAssessmentService) {
		this.healthRiskAssessmentService = healthRiskAssessmentService;
	}
	
	public void setCommService(CommService commService) {
		this.commService = commService;
	}
	/**
	 * 1726生成健康风险评估报告
	     * @Title: createHealthRiskAssessmentReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String createHealthRiskAssessmentReport() throws WebException{
		UserDTO user = (UserDTO) session.get("username");
		String picpath = this.commService.getDatadis("TPLJ").getName();
		//查询体检信息
		ExamInfoDTO examinfo = this.healthRiskAssessmentService.getexaminfobyExamnum(this.model.getExam_num());
		if(examinfo != null){
			//1.提取评估项目的检查结果
			List<HealthRiskItemExamresultDTO> itemlist = this.healthRiskAssessmentService.saveHealthRiskItemExamresult(examinfo.getExam_num());
		
			//2.计算风险评估项目的分数
			List<HealthRiskExaminfoDTO> riskExaminfoList = this.healthRiskAssessmentService.createHealthRiskExaminfo(examinfo.getExam_num(), examinfo.getSex(), itemlist);
		
			//3.生成评估柱状图
			for(HealthRiskExaminfoDTO dto : riskExaminfoList){
				if(dto.getIs_success() == 0){//只有评估成功的才出对比图
					List<SummaryImage> list = new ArrayList<>();
					list.add(new SummaryImage("实际患病率(%)", Double.valueOf(dto.getReality_morbidity())));
					if(dto.getDisease_type().equals("CHD")){//心血管包含平均风险、硬终点（指心肌梗死等，不算心绞痛）风险、低风险（即比较理想的生活方式下的风险）
						list.add(new SummaryImage("平均患病率(%)", Double.valueOf(dto.getAverage_morbidity())));
						list.add(new SummaryImage("硬终点患病率(%)", Double.valueOf(dto.getHard_morbidity())));
						list.add(new SummaryImage("低风险患病率(%)", Double.valueOf(dto.getLow_morbidity())));
					}else if(dto.getDisease_type().equals("STROKE")){//卒中只有平均风险
						list.add(new SummaryImage("平均患病率(%)", Double.valueOf(dto.getAverage_morbidity())));
					}
					String filename = "/healthRiskAss"+"/"+dto.getDisease_type()+"/"+DateTimeUtil.getDate2()+"/"+examinfo.getExam_num()+"/"+examinfo.getExam_num()+"_"+dto.getHealth_risk_id()+".jpg";
					String title = dto.getBar_graph_titel();
					String str = ImageUtils.createImage(1684, 1190, list, picpath + filename, title);
					if(str.split("-")[0].equals("ok")){
						dto.setPicture_path(filename);
					}
				}
			}
			
			//4.保存风险评估数据
			this.message = this.healthRiskAssessmentService.saveHealthRiskExaminfo(examinfo.getExam_num(),riskExaminfoList, user);
		}else{
			this.message = "error-该体检信息不存在!";
		}
		this.outJsonStrResult(this.message);
		return NONE;
	}
	
	/**
	 * 1727 获取已生成健康风险评估疾病列表
	     * @Title: getHealthRiskAssessmentReport   
	     * @Description: TODO(这里用一句话描述这个方法的作用)   
	     * @param: @return
	     * @param: @throws WebException      
	     * @return: String      
	     * @throws
	 */
	public String getHealthRiskAssessmentReport() throws WebException{
		List<HealthRiskExaminfoDTO> list = this.healthRiskAssessmentService.getHealthRiskAssessmentReport(this.model.getExam_num());
		this.outJsonResult(list);
		return NONE;
	}
}
